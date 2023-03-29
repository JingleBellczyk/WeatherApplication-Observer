import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSITest {
    CSI csi;
    UserApp k1, k2, k3;
    User u1, u2, u3;
    List<Observer> myObserverList1 = new ArrayList<>();
    List<Observer> myObserverList2 = new ArrayList<>();
    List<Observer> observerList = new ArrayList<>();

    @BeforeEach
    void set() {
        csi = new CSI();

        u1 = new User("a", "b", true, "plik1.json");
        k1 = new UserApp(u1, csi);

        u2 = new User("c", "d", false, "plik1.json");
        k2 = new UserApp(u2, csi);

        u3 = new User("c", "d", false, "plik1.json");
        k3 = new UserApp(u3, csi);

        observerList.add(k1);
        observerList.add(k2);
        csi.setObserverList(observerList);

        myObserverList1.add(k2);

        myObserverList2.add(k1);
        myObserverList2.add(k2);
        myObserverList2.add(k3);
    }

    @Test
    void removeObserver() {
        csi.removeObserver(k1);
        assertEquals(myObserverList1, csi.getObserverList());
    }

    @Test
    void registerObserver() {
        csi.registerObserver(k3);
        assertEquals(myObserverList2, csi.getObserverList());
    }
}