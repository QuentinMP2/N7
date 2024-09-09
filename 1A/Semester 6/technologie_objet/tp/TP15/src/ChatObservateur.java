import java.util.Observable;
import java.util.Observer;

public class ChatObservateur implements Observer {
    @Override
    public void update(Observable observable, Object o) {
        System.out.println((String) o);
    }
}
