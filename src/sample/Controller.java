package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {
	@FXML
	private TextField nameField;

	@FXML
	private Button helloButton;

	@FXML
	private Button byeButton;

	@FXML
	private CheckBox myCheckBox;

	@FXML
	private Label ourLabel;

	@FXML
	public void initialize() {
		helloButton.setDisable(true);
		byeButton.setDisable(true);
	}

	@FXML
	public void onButtonClicked(ActionEvent event) {
		if (event.getSource().equals(helloButton)) {
			System.out.println("Hello, " + nameField.getText());
		} else if (event.getSource().equals(byeButton)) {
			System.out.println("Bye, " + nameField.getText());
		}
		Runnable task = new Runnable() {
			@Override public void run() {
				try {
					String message = Platform.isFxApplicationThread() ? "UI Thread" : "Background Thread";
					System.out.println("I'm going to sleep on the : "+ message);
					Thread.sleep(10000);
					Platform.runLater(new Runnable() {
						@Override public void run() {
							String message = Platform.isFxApplicationThread() ? "UI Thread" : "Background Thread";
							System.out.println("I'm updating the label on the : "+ message);
							ourLabel.setText("We did something");

						}
					});

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		new Thread(task).start();

		if (myCheckBox.isSelected()) {
			nameField.clear();
			helloButton.setDisable(true);
			byeButton.setDisable(true);
		}
	}

	public void handleKeyReleased() {
		String text = nameField.getText();
		boolean disableButton = text.isEmpty() || text.trim().isEmpty();
		helloButton.setDisable(disableButton);
		byeButton.setDisable(disableButton);

	}

	public void handleChange() {
		System.out.println("The checkbox is " + (myCheckBox.isSelected() ? "checked" : "not checked"));
	}

}
