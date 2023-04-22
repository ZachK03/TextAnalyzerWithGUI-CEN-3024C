module TextAnalyzerWithGUI {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	requires org.jsoup;
	requires org.junit.jupiter.api;
	requires junit;
	requires org.junit.platform.suite.api;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml;
}
