package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Start;
import il.cshaifasweng.OCSFMediatorExample.entities.Update;
import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;


public class SimpleClient extends AbstractClient {
	
	public static SimpleClient client = null;
	public static int port = 3000;
	public static String ip = "127.0.0.1";

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		String message = msg.toString();
		if (msg.getClass().equals(Warning.class)) {
			EventBus.getDefault().post(new WarningEvent((Warning) msg));
		}
		else if (msg.getClass().equals(Start.class)) {
			EventBus.getDefault().post(new StartEvent((Start) msg));
		}
		else if (msg.getClass().equals(Update.class)) {
			EventBus.getDefault().post(new UpdateEvent((Update) msg));
		}
		else{
			System.out.println(message);
		}
	}
	
	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient(ip, port);
		}
		return client;
	}

}
