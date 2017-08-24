package hello;

import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer{

	@Autowired
	private BlockingQueue<Message> messageQueue;

	@Async
	public void start() {
		while (true) {
			Message message;
			try {
				message = messageQueue.take();
				System.out.println(message);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
