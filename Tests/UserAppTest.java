import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserAppTest {
    CSI csi;
    User u;
    UserApp userApp;
    List<WeatherStation> myList = new ArrayList<>();

    void set() {
        csi = new CSI();
        u = new User("a", "b", true, "name.json"); // when user is created, empty file is created
        u.setFileName("Tests/usersWeatherStationsUserAppTest.json"); //checked file

        userApp = new UserApp(u, csi);
        WeatherStation w1 = new WeatherStation("Wroclaw", 2f, 1f, 1f);
        WeatherStation w2 = new WeatherStation("Poznan", 1f, null, 1f);
        WeatherStation w3 = new WeatherStation("Krakow", 22.9f, null, 1f);
        myList.add(w1);
        myList.add(w2);
        myList.add(w3);
    }

    @Test
    void load() {
        set();
        List<WeatherStation> result = userApp.load();
        //Assertions.assertArrayEquals(new List[]{Arrays.asList(myList)}, new List[]{Arrays.asList(result)});
        //assert (myList.equals(result)) : "not equal";
        //assertEquals(myList.toString(),result.toString());

        assertEquals(myList.getClass(), result.getClass());

        if (myList.size() == result.size()) {
            for (int i = 0; i < myList.size(); i++) {
                //if(!myList.get(i).equals(result.get(i)))
                //    fail("fail");
                //assertEquals(myList.get(i).getLocation(), result.get(i).getLocation());
                //assertEquals(myList.get(i).getHumidity(), result.get(i).getHumidity());
                //assertEquals(myList.get(i).getTemperature(), result.get(i).getTemperature());
                //assertEquals(myList.get(i).getPressure(), result.get(i).getPressure());
            }
        } else {
            fail("not equal");
        }
    }

    @Test
    void saveToJson() {


    }
    /*
    [
  {
    "location": "Wroclaw",
    "temperature": 2,
    "humidity": 1,
    "pressure": 1
  },
  {
    "location": "Poznan",
    "temperature": 1,
    "humidity": null,
    "pressure": 1
  },
  {
    "location": "Krakow",
    "temperature": 22.9,
    "humidity": null,
    "pressure": 1
  }
]
     */

}