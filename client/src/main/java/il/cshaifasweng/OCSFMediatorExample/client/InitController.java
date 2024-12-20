package il.cshaifasweng.OCSFMediatorExample.client;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;


import java.io.IOException;

import static il.cshaifasweng.OCSFMediatorExample.client.SimpleClient.client;

public class InitController {
    @FXML
    private TextField ip;

    @FXML
    private TextField port;


    public void connect() {
        SimpleClient.ip = ip.getText();
        SimpleClient.port = Integer.parseInt(port.getText());
        client = SimpleClient.getClient();
        try {
            client.openConnection();
       }catch (IOException e){
            System.out.println("Connection failed, Enter correct ip and port");
            client = null;
            return;
        }

        try {
            App.setRoot("primary");
        }catch (IOException e){
            System.out.println("Switching failed");
            return;
        }
        try {
            SimpleClient.getClient().sendToServer("add client");
        }catch (IOException e){
            System.out.println("Adding client failed");
            return;
        }

    }

}
