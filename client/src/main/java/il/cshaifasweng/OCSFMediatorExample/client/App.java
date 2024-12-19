package il.cshaifasweng.OCSFMediatorExample.client;


import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


import static il.cshaifasweng.OCSFMediatorExample.client.SimpleClient.client;

/**
 * JavaFX App
 */

public class App extends Application {

    private static Scene scene;
    private SimpleClient client;
    @Override
    public void start(Stage stage) throws IOException {
    	EventBus.getDefault().register(this);
        scene = new Scene(loadFXML("secondary"), 450, 600);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }



    @Override
    public void stop() throws Exception {
        // Unregister EventBus
        EventBus.getDefault().unregister(this);
        // Check if the client is not null and connected before performing actions
        if (SimpleClient.getClient() != null && SimpleClient.getClient().isConnected()) {
            try {
                SimpleClient.getClient().sendToServer("remove client");
                SimpleClient.getClient().closeConnection();
            } catch (IOException e) {
                System.out.println("Error closing client connection: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Call the superclass stop method
        super.stop();
    }


    @Subscribe
    public void onWarningEvent(WarningEvent event) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.WARNING,
                    String.format("Message: %s\nTimestamp: %s\n",
                            event.getWarning().getMessage(),
                            event.getWarning().getTime().toString())
            );
            alert.show();
        });
    }

	public static void main(String[] args) {
        launch();
    }

}