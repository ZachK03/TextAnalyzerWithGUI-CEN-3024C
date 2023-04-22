package application;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainSceneController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	@FXML
	public Button closeButton;
	public Button continueButton;
	public Slider slider;
	public TextField sliderNumber;
	
	/**
	 * Initializes the scene. Links the slider to the text field.
	 * 
	 */
	public void initialize() {
		slider.valueProperty().addListener((observable, oldValue, newValue) -> {
			int value = newValue.intValue();
			sliderNumber.setText(String.valueOf(value));
		});
		
		sliderNumber.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!newValue.matches("\\d*")) {
					sliderNumber.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
	}
	
	/**
	 * Handles the close action for the X button.
	 * @param event
	 */
	public void handleCloseButtonAction(ActionEvent event) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Handles adjusting the slider from the text field.
	 * @param event
	 */
	public void handleSliderTextValueChanged(ActionEvent event) {
		String text = sliderNumber.getText();
		int value = Integer.parseInt(text);
		if(value > 50) {
			sliderNumber.setText("50");
			slider.setValue(50.0);
		} else if(value < 10) {
			sliderNumber.setText("10");
			slider.setValue(10.0);
		}
	}
	
	/**
	 * Handles the confirm button and transfers to the next scene.
	 * @param event
	 */
	public void handleConfirmButtonAction(ActionEvent event) {
		int value = (int)slider.getValue();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("WordOccurances.fxml"));
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		WordOccurancesController controller = loader.getController();
		controller.setNumberOfWords(value);
		controller.getWordOccurances();
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
