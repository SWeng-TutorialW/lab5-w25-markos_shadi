package il.cshaifasweng.OCSFMediatorExample.client;


import il.cshaifasweng.OCSFMediatorExample.entities.Update;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


import javafx.scene.text.Text;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;



public class PrimaryController{
	private int playerTurn = 0;
	private int id = -1;
	private String role;

	@FXML
	private Button button1;

	@FXML
	private Button button2;

	@FXML
	private Button button3;

	@FXML
	private Button button4;

	@FXML
	private Button button5;

	@FXML
	private Button button6;

	@FXML
	private Button button7;

	@FXML
	private Button button8;

	@FXML
	private Button button9;

	@FXML
	private Text winnerText;

	ArrayList<Button> buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9));


	public PrimaryController() {
		EventBus.getDefault().register(this);
	}


	@Subscribe
	public void onStartEvent(StartEvent event){
			Platform.runLater(() -> {
				GameStart();
				id = event.getStart().getId();
				role = event.getStart().getRole();
				if((id + playerTurn) % 2 == 0){
					winnerText.setText("Your Turn: " + role);
				}
				else {
					winnerText.setText("Wait for your Turn...");
				}

			});
	}

	public void GameStart() {
		buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9));
		buttons.forEach(button -> button.setDisable(false));
	}

	@Subscribe
	public void onUpdateEvent(UpdateEvent event){
		Platform.runLater(() -> {
			Button button = buttons.get(event.getUpdate().getIndex() - 1);
			button.setText(event.getUpdate().getRole());
			button.setDisable(true);
			playerTurn++;
			if((id + playerTurn) % 2 == 0){
				winnerText.setText("Your Turn: " + role);
			}
			else {
				winnerText.setText("Wait for your Turn...");
			}
			checkIfGameIsOver();

		});
	}

	@FXML
	void setupButton(ActionEvent event) {
		if((id + playerTurn) % 2 == 0 && (id >= 0)){
			Button clickedButton = (Button) event.getSource();
			String buttonId = clickedButton.getId();
			int buttonIndex = Integer.parseInt(buttonId.replaceAll("\\D", ""));
			Update update = new Update(buttonIndex, role);

			try {
				SimpleClient.getClient().sendToServer(update);
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
		else{
			try {
				SimpleClient.getClient().sendToServer("#warning");
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	@FXML
	public void GameEnded() {
		buttons = new ArrayList<>(Arrays.asList(button1,button2,button3,button4,button5,button6,button7,button8,button9));
		buttons.forEach(button -> button.setDisable(true));
	}

	public void checkIfGameIsOver(){
		for (int a = 0; a < 8; a++) {
			String line = switch (a) {
				case 0 -> button1.getText() + button2.getText() + button3.getText();
				case 1 -> button4.getText() + button5.getText() + button6.getText();
				case 2 -> button7.getText() + button8.getText() + button9.getText();
				case 3 -> button1.getText() + button5.getText() + button9.getText();
				case 4 -> button3.getText() + button5.getText() + button7.getText();
				case 5 -> button1.getText() + button4.getText() + button7.getText();
				case 6 -> button2.getText() + button5.getText() + button8.getText();
				case 7 -> button3.getText() + button6.getText() + button9.getText();
				default -> null;
			};

			//X winner
			if (line.equals("XXX")) {
				winnerText.setText("X won!");
				GameEnded();
			}

			//O winner
			else if (line.equals("OOO")) {
				winnerText.setText("O won!");
				GameEnded();
			}
			else if (playerTurn == 9){
				winnerText.setText("Draw");
				GameEnded();
			}
		}
	}
	public void cleanup() {
		EventBus.getDefault().unregister(this); 
	}
}