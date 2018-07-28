package sender;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class JMSSender {

	private JmsTemplate template;
	private Destination dest;

	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	public void setDest(Destination dest) {
		this.dest = dest;
	}

	public void init() throws Exception {
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
		pool.scheduleAtFixedRate(new Runnable() {
			public void run() {
				template.send(dest, new MessageCreator() {
					public Message createMessage(Session session) throws JMSException {
						TextMessage msg = session.createTextMessage(System.currentTimeMillis()+"");
						return msg;
					}
				});
			}
		}, 1, 2, TimeUnit.SECONDS);
	}



}
