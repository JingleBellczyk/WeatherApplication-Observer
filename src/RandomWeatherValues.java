import java.util.Random;

public class RandomWeatherValues {
    private static Random random = new Random();
    //private DecimalFormat df = new DecimalFormat("0.00");

    public Float randomTemperature() {
        return new Float(-20.0 + (40.0 - (-20)) * random.nextFloat()); //Celsius
    }

    public Float randomHumidity() {
        //DecimalFormat df = new DecimalFormat("0.00");
        return new Float((100.0) * random.nextFloat()); // %
    }

    public Float randomPressure() {
        //DecimalFormat df = new DecimalFormat("0.00");
        return new Float(960.0 + (1050.0 - 950.0) * random.nextFloat()); //hPa
    }
}
