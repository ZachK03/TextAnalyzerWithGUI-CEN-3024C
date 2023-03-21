module TextAnalyzerWithGUI {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	requires org.jsoup;
	
	opens application to javafx.graphics, javafx.fxml;
}
