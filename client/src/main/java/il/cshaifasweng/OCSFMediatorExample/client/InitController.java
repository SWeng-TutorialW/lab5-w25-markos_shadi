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
        client = SimpleClient.getClient();
        SimpleClient.ip = ip.getText();
        SimpleClient.port = Integer.parseInt(port.getText());
        try {
            client.openConnection();
       }catch (IOException e){
            throw new RuntimeException(e);
        }
        try {
            App.setRoot("primary");
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        try {
            SimpleClient.getClient().sendToServer("add client");
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
