package il.cshaifasweng.OCSFMediatorExample.server;


import il.cshaifasweng.OCSFMediatorExample.entities.Start;
import il.cshaifasweng.OCSFMediatorExample.entities.Update;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;



public class SimpleServer extends AbstractServer {
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();
	int[] idCounter = {0,1}; // Counter for unique IDs
	String[] roles = {"X", "O"};


	public SimpleServer(int port) {
		super(port);

	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgString = msg.toString();
		if (msgString.startsWith("#warning")) {
			Warning warning = new Warning("Warning from server!");
			try {
				client.sendToClient(warning);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(msgString.startsWith("add client")){
			SubscribedClient connection = new SubscribedClient(client);
			SubscribersList.add(connection);
			try {
				client.sendToClient("client added successfully");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if (SubscribersList.size() == 2){
				Random random = new Random();
				int randomRoleIndex = random.nextInt(2);
				int randomIdIndex = random.nextInt(2);// Use the instance of Random
				String firstRole = roles[randomRoleIndex];
				String secondRole = roles[1 - randomRoleIndex];
				int firstId = idCounter[randomIdIndex];
				int secondId = idCounter[1 - randomIdIndex];
				Start start1 = new Start(firstId, firstRole);
				Start start2 = new Start(secondId, secondRole);
				try {
					SubscribersList.get(0).getClient().sendToClient(start1);
					SubscribersList.get(1).getClient().sendToClient(start2);
				} catch (IOException e) {
					e.printStackTrace();
				}
				//sendToAllClients("start");
            }
		}
		else if(msgString.startsWith("remove client")){
			if(!SubscribersList.isEmpty()){
				for(SubscribedClient subscribedClient: SubscribersList){
					if(subscribedClient.getClient().equals(client)){
						SubscribersList.remove(subscribedClient);
						break;
					}
				}
			}
		}
		else if(msg.getClass() == Update.class){
			sendToAllClients(msg);
		}

	}
	public void sendToAllClients(String message) {
		try {
			for (SubscribedClient subscribedClient : SubscribersList) {
				subscribedClient.getClient().sendToClient(message);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
