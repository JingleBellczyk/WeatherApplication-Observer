import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private String surname;
    private boolean savingData;
    private String fileName;


    private List<WeatherStation> myWeatherStationList = new ArrayList<>();

    public User(String name, String surname, boolean savingData, String fileName) {
        this.name = name;
        this.surname = surname;
        this.savingData = savingData;
        this.fileName = fileName;
        if (savingData) {
            createMyFile();
        }
    }

    public void createMyFile() {
        try {
            FileWriter file = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isSavingData() {
        return savingData;
    }

    public void setSavingData(boolean savingData) {
        this.savingData = savingData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<WeatherStation> getMyWeatherStationList() {
        return myWeatherStationList;
    }

    public void setMyWeatherStationList(List<WeatherStation> myWeatherStationList) {
        this.myWeatherStationList = myWeatherStationList;
    }
}
