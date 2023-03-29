import java.util.ArrayList;
import java.util.List;

public class CSI extends Thread implements Subject {

    private List<WeatherStation> weatherStationList = new ArrayList<>();
    private List<Observer> observerList = new ArrayList<>();
    private boolean shouldContinue = true;
    private Object usersSemaphore = new Object();

    @Override
    public void run() {
        while (shouldContinue) {
            takeTheMeasurements();
            notifyObservers();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void takeTheMeasurements() {
        for (WeatherStation weatherStation : weatherStationList)
            weatherStation.updateMeasurements();
    }

    @Override
    public void registerObserver(Observer observer) {
        synchronized (observerList) {
            observerList.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        synchronized (observerList) {
            observerList.remove(observer);
        }
    }

    @Override
    public void notifyObservers() {
        synchronized (observerList) {
            for (Observer observer : observerList)
                observer.update(weatherStationList);
        }
    }

    public void stopObservable() {
        this.setShouldContinue(false);
    }

    public void waitFinish() {
        try {
            this.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopContinue() {
        shouldContinue = false;
    }

    public List<WeatherStation> getWeatherStationList() {
        return weatherStationList;
    }

    public void setWeatherStationList(List<WeatherStation> weatherStationList) {
        this.weatherStationList = weatherStationList;
    }

    public List<Observer> getObserverList() {
        return observerList;
    }

    public void setObserverList(List<Observer> observerList) {
        this.observerList = observerList;
    }

    public boolean isShouldContinue() {
        return shouldContinue;
    }

    public void setShouldContinue(boolean shouldContinue) {
        this.shouldContinue = shouldContinue;
    }

    public Object getUsersSemaphore() {
        return usersSemaphore;
    }

    public void setUsersSemaphore(Object usersSemaphore) {
        this.usersSemaphore = usersSemaphore;
    }

}
