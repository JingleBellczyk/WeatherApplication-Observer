import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class Menu implements Serializable {
    public CSI csi = new CSI();
    UserApp userApp;
    Scanner scanner = new Scanner(System.in);

    /*
     * loads weather stations from a json file
     */
    public static List<WeatherStation> load(String path) {

        List<WeatherStation> weatherStationList = new ArrayList<>();
        Reader reader = null;

        try {
            reader = Files.newBufferedReader(Paths.get(path));
            weatherStationList = new Gson().fromJson(reader, new TypeToken<List<WeatherStation>>() {
            }.getType());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return weatherStationList;
    }

    /*
     * calls method, that loads weather stations from a file
     */
    public void loadWeatherStations(String path) {
        csi.setWeatherStationList(load(path));
    }

    /*
     * checks, if user gave proper input,
     * according to the number of answers
     */
    public static int checkingNumber(int numberOFAnswers) {
        int givenNumber;
        Scanner scanner = new Scanner(System.in);

        try {
            givenNumber = scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            givenNumber = -1;
        }

        while (givenNumber < 0 || givenNumber > numberOFAnswers) {
            try {
                System.out.println("Give correct number");
                givenNumber = scanner.nextInt();
            } catch (InputMismatchException e) {
                givenNumber = -1;
            }
        }

        return givenNumber;
    }

    /*
     * checks if user have been registered in a Weather Station
     */
    public boolean amIRegistered(User user, String location) {
        int i = 0;

        while (i < user.getMyWeatherStationList().size()) {
            if (userApp.getUser().getMyWeatherStationList().get(i).getLocation().equals(location))
                return true;
            i++;
        }

        return false;
    }

    /*
     * method, where user chooses Weather Stations to subscribe
     */
    public void choosingLocations(UserApp userApp) {
        System.out.println("Choose locations");
        System.out.println("press 0 to stop choosing");

        for (int i = 0; i < csi.getWeatherStationList().size(); i++) {
            System.out.println((i + 1) + " " + csi.getWeatherStationList().get(i).getLocation());
        }

        int chosenNumber = checkingNumber(csi.getWeatherStationList().size());

        // 0 - is an ending command, loop also checks if someone has been registered in given weather station
        while (chosenNumber != 0) {
            for (int i = 1; i <= csi.getWeatherStationList().size(); i++) {
                if (chosenNumber == i) {
                    if (amIRegistered(userApp.getUser(), csi.getWeatherStationList().get(i - 1).getLocation())) {
                        System.out.println("You have already been registered to this Weather Station");
                    } else {
                        userApp.userRegistering(csi, csi.getWeatherStationList().get(i - 1));
                    }
                }
            }
            chosenNumber = checkingNumber(csi.getWeatherStationList().size());
        }

    }

    /*
     * creating user and his app - userApp
     * this userApp is set up as default in this class
     */
    public void userCreatingIndeed(String name, String surname) {
        String fileName;

        boolean answerSavingInFile = choosingSavingInFile();
        if (answerSavingInFile) {
            fileName = choosingNameOfFile();
        } else {
            fileName = null;
        }

        UserApp userApp = new UserApp(new User(name, surname, answerSavingInFile, fileName), csi);
        this.userApp = userApp;

        choosingLocations(userApp);
    }

    /*
     * checks if user's name and surname haven't been used already
     */
    public boolean ifRepeatedNameSurname(String name, String surname) {
        int i = 0;
        boolean repeat = false;

        while (!repeat && i < csi.getObserverList().size()) {
            if (csi.getObserverList().get(i).getClass() == UserApp.class) {
                if (((UserApp) csi.getObserverList().get(i)).getUser().getName() == name
                        && ((UserApp) csi.getObserverList().get(i)).getUser().getSurname() == surname) {
                    repeat = true; // there was repeat of name and surname
                }
            }
            i++;
        }

        return repeat;
    }

    /*
     * first phase of creating user, choosing name and surname
     */
    public void userCreating() {
        String name, surname;

        System.out.print("Give your name: ");
        name = scanner.nextLine();
        System.out.print("Give your surname: ");
        surname = scanner.nextLine();

        if (csi.getObserverList().size() == 0) { /* if there is no one to compare with */
            userCreatingIndeed(name, surname);
        } else {                                /* if there are people registered already */
            boolean properAnswer = false;

            while (!properAnswer) {
                if (ifRepeatedNameSurname(name, surname)) {
                    System.out.println("Give another data, there is someone named like you");

                    System.out.print("Give your name: ");
                    name = scanner.nextLine();
                    System.out.print("Give your surname: ");
                    surname = scanner.nextLine();
                } else {
                    properAnswer = true;
                    userCreatingIndeed(name, surname);
                }
            }
        }
    }

    public boolean choosingSavingInFile() {
        System.out.println("Press y, if you want to save data to a file, n - if you don't want to");
        String answer = scanner.nextLine();
        if (answer.equals("y") || answer.equals("Y")) {
            return true;
        } else {
            return false;
        }
    }

    public void changingMindAboutSavingInFileMenu(UserApp userApp) {
        if (choosingSavingInFile()) {
            userApp.getUser().setSavingData(true);
            userApp.getUser().setFileName(choosingNameOfFile());
        } else
            userApp.getUser().setSavingData(false);
    }

    public String choosingNameOfFile() {
        System.out.println("Give a file name, where you want to have your data, please add ''.json'' at the end");
        return scanner.nextLine();
    }

    /*
     * method creates new file to save data to,
     * the old one still exists
     */
    public void changingNameOfOutputFile(UserApp userApp) {
        System.out.println("Give a new file name:");
        userApp.getUser().setFileName(scanner.nextLine());
        userApp.getUser().createMyFile();
    }

    public void checkMyLocations(UserApp userApp) {
        if (userApp.getUser().getMyWeatherStationList().size() != 0) {
            System.out.println("my subscribed locations: ");
            for (int i = 0; i < userApp.getUser().getMyWeatherStationList().size(); i++) {
                System.out.println(i + userApp.getUser().getMyWeatherStationList().get(i).getLocation());
            }
        } else {
            System.out.println("You haven't subscribed any locations yet");
        }
    }

    public void removeMyLocations(UserApp userApp) {
        checkMyLocations(userApp);
        System.out.println("Which location would you like to remove? Choose a number:");
        int answer = checkingNumber(userApp.getUser().getMyWeatherStationList().size());
        userApp.userUnregistering(csi, userApp.getUser().getMyWeatherStationList().get(answer));
    }

    public void usersMenu() {
        System.out.println("Choose number:");
        System.out.println("0 - exit"); // if exit menu start wybierz uzytkownika lub dodaj lub usun
        System.out.println("1 - check my locations");
        System.out.println("2 - add locations");
        System.out.println("3 - remove location");
        System.out.println("4 - change decision about saving in file");
        System.out.println("5 - create new file to save data");
        System.out.println("6 - print weather stations data");

        int n = checkingNumber(6); // number of answers -1
        if (n == 0) {
            mainMenu();
        }
        if (n == 1) {
            checkMyLocations(userApp);
            usersMenu();
        }
        if (n == 2) {
            choosingLocations(userApp);
            usersMenu();
        }
        if (n == 3) {
            removeMyLocations(userApp);
            usersMenu();
        }
        if (n == 4) {
            changingMindAboutSavingInFileMenu(userApp);
            usersMenu();
        }
        if (n == 5) {
            changingNameOfOutputFile(userApp);
            usersMenu();
        }
        if (n == 6) {
            printWeatherStationData(userApp);
            usersMenu();
        }
    }

    public void csiStarting() {

        csi.start();
        while (csi.isShouldContinue()) {
            try {
                mainMenu();
                Thread.sleep(10000);
                csi.waitFinish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void printWeatherStationData(UserApp userApp) {
        userApp.setPrintOut(true);
        System.out.println("CHOOSE 0 TO STOP");
        int n = checkingNumber(0);
        if (n == 0) {
            userApp.setPrintOut(false);
        }
    }

    /*
     * method let us choose or change user, which we use at the moment
     */
    public void chooseUser() {
        if (csi.getObserverList().size() > 0) {
            boolean answer = false;
            String name, surname;

            while (!answer) {
                System.out.println("Give name, then surname");
                name = scanner.nextLine();
                surname = scanner.nextLine();

                for (int i = 0; i < csi.getObserverList().size(); i++) {
                    if (((UserApp) csi.getObserverList().get(i)).getUser().getName() == name
                            && ((UserApp) csi.getObserverList().get(i)).getUser().getSurname() == surname) {
                        this.userApp = (UserApp) csi.getObserverList().get(i);
                        answer = true;
                        usersMenu();
                    } else {
                        System.out.println("That person doesn't exist, try again");
                    }
                }
            }
        } else {
            System.out.println("List of subscribers is empty");
        }

        mainMenu();
    }

    public void mainMenu() {
        System.out.println("Choose number:");
        System.out.println("0 - exit");
        System.out.println("1 - create user");
        System.out.println("2 - delete user");
        System.out.println("3 - choose user");

        int n = checkingNumber(3); // number of answers - 1

        if (n == 0) {
            csi.stopObservable();
            csi.waitFinish();
        }
        if (n == 1) {
            userCreating();
            usersMenu();
        }
        if (n == 2) {
            if (userApp != null) {
                userApp.userUnregisteringCompletely(csi);
            }
            {
                System.out.println("There are no users registered, you can't do this");
            }
            mainMenu();
        }
        if (n == 3) {
            chooseUser();
        }
        end();
    }

    public void end() {
    }

    public void run() {
        loadWeatherStations("src/weatherLocations.json");
        csiStarting();

    }
}
