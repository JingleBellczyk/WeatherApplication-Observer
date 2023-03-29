import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RandomWeatherValuesTest {

    private RandomWeatherValues rVM;

    @BeforeEach
    void set() {
        rVM = new RandomWeatherValues();
    }

    @Test
    void randomTemperature() {
        Float result = rVM.randomTemperature();
        assertEquals(result.getClass(), Float.class);
    }

    @Test
    void randomHumidity() {
        Float result = rVM.randomHumidity();
        assertEquals(result.getClass(), Float.class);
    }

    @Test
    void randomPressure() {
        Float result = rVM.randomPressure();
        assertEquals(result.getClass(), Float.class);
    }
}