package application;

import java.io.IOException;
import java.util.Arrays;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.stage.Stage;

public class WordOccurancesController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	@FXML
	public Button closeButton;
	public Button restartButton;
	public TableView<String[]> wordsTable;
	
	private int numberOfWords;
	public TextAnalyzer textAnalyzer = new TextAnalyzer();
	
	public void handleCloseButtonAction(ActionEvent event) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
	
	public void setNumberOfWords(int value) {
		numberOfWords = value;
	}
	
	public int getNumberOfWords() {
		return numberOfWords;
	}
	
	public void getWordOccurances() {
		if(numberOfWords <= 0) {
			return;
		}
		
		String[][] result = textAnalyzer.getWordCount(numberOfWords,"https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
		
		ObservableList<String[]> data = FXCollections.observableArrayList();
		String[] labels = {"Word","Count"};
		for(int i = 0; i < 2; i++) {
			TableColumn<String[], String> column = new TableColumn<>(labels[i]);
			final int columnIndex = i;
			column.setCellValueFactory(cellData -> {
				String[] row = cellData.getValue();
				return new SimpleStringProperty(row[columnIndex]);
			});
			wordsTable.getColumns().add(column);
		}
		
		data.addAll(Arrays.asList(result));
		wordsTable.setItems(data);
	}
	
	public void restartApp(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
