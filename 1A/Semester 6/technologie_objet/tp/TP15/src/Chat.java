import java.util.*;

public class Chat extends Observable implements Iterable<Message> {

	private List<Message> messages;

	public Chat() {
        this.messages = new ArrayList<Message>();
	}

	public void ajouter(Message m) {
		this.messages.add(m);
		this.notifyObservers(m);
	}

	@Override
	public Iterator<Message> iterator() {
		return messages.iterator();
	}
}
