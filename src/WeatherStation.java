import java.io.Serializable;

public class WeatherStation implements Serializable {
    private String location;
    private Float temperature;
    private Float humidity;
    private Float pressure;

    public WeatherStation(String location, Float temperature, Float humidity, Float pressure) {
        this.location = location;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public void updateMeasurements() {
        RandomWeatherValues rWV = new RandomWeatherValues();

        if (temperature != null) {
            temperature = rWV.randomTemperature();
        }
        if (pressure != null) {
            pressure = rWV.randomPressure();
        }
        if (humidity != null) {
            humidity = rWV.randomHumidity();
        }


    }

    // @Override
    //public boolean equals(Object obj) {
//
    //    //if (obj.getClass().toString().equals(this.getClass().toString())) {
    //        if (this.getLocation() == ((WeatherStation) obj).getLocation() &&
    //                this.getTemperature() == ((WeatherStation) obj).getTemperature() &&
    //                this.getPressure() == ((WeatherStation) obj).getPressure() &&
    //                this.getHumidity() == ((WeatherStation) obj).getHumidity()){
    //            return true;
    //        }
    //        else {
    //            return false;
    //            //    }
    //            //} else
    //            //    System.out.println("inne klasy");
    //            //    return false;
    //        }
    //}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }


}
