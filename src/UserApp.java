import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserApp implements Observer {

    private User user;
    boolean printOut = false;
    private Object kupaSemaphore = new Object();

    public UserApp(User user, CSI csi) {
        this.user = user;
        csi.registerObserver(this);
    }

    public void userRegistering(CSI csi, WeatherStation weatherStation) {
        List<WeatherStation> temp = user.getMyWeatherStationList();
        temp.add(weatherStation);
        synchronized (user.getMyWeatherStationList()) {
            user.setMyWeatherStationList(temp);
        }
    }

    public void userUnregistering(CSI csi, WeatherStation weatherStation) {
        List<WeatherStation> temp = user.getMyWeatherStationList();
        temp.remove(weatherStation);
        synchronized (csi.getWeatherStationList()) {
            user.setMyWeatherStationList(temp);
            synchronized (csi.getObserverList()) {
                if (user.getMyWeatherStationList().size() == 0) {
                    csi.removeObserver(this);
                }
            }
        }
    }

    public void userUnregisteringCompletely(CSI csi) {
        csi.removeObserver(this);
    }

    @Override
    public void update(List<WeatherStation> weatherStationList) {
        if (user.isSavingData()) { //if user wanted to save data in a file
            saveToJson();
        }

        // if user decided to print out data
        if (printOut) {
            System.out.println("-----" + user.getName() + " " + user.getSurname());
            for (int i = 0; i < user.getMyWeatherStationList().size(); i++) {
                {
                    System.out.println(i + 1 + ".) " + user.getMyWeatherStationList().get(i).getLocation() + " temp/hum/press ");

                    if (user.getMyWeatherStationList().get(i).getTemperature() != null) {
                        System.out.print(user.getMyWeatherStationList().get(i).getTemperature() + " C' ");
                    } else {
                        System.out.print("UNAVAILABLE ");
                    }
                    if (user.getMyWeatherStationList().get(i).getHumidity() != null) {
                        System.out.print(user.getMyWeatherStationList().get(i).getHumidity() + " % ");
                    } else {
                        System.out.print("UNAVAILABLE ");
                    }
                    if (user.getMyWeatherStationList().get(i).getPressure() != null) {
                        System.out.print(user.getMyWeatherStationList().get(i).getPressure() + " hPa \n");
                    } else {
                        System.out.print("UNAVAILABLE \n");
                    }
                }
            }
        }

    }

    /*
     * loads data from weather stations from the user's file
     */
    public List<WeatherStation> load() {
        List<WeatherStation> weatherStationList = null;
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(user.getFileName()));
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
     * save data from the weather stations to the file
     */
    public void saveToJson() {

        // this was in the file
        List<WeatherStation> oldWeatherStationList = load();

        Writer writer = null;
        try {
            writer = new FileWriter(user.getFileName());
            List<WeatherStation> newWeatherStationList = new ArrayList<>();


            if (oldWeatherStationList != null) {

                newWeatherStationList.addAll(oldWeatherStationList);
                newWeatherStationList.addAll(user.getMyWeatherStationList());
                new Gson().toJson(newWeatherStationList, writer);

            } else {
                new Gson().toJson(user.getMyWeatherStationList(), writer);
            }

            writer.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isPrintOut() {
        return printOut;
    }

    public void setPrintOut(boolean printOut) {
        this.printOut = printOut;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Object getKupaSemaphore() {
        return kupaSemaphore;
    }

    public void setKupaSemaphore(Object kupaSemaphore) {
        this.kupaSemaphore = kupaSemaphore;
    }
}
