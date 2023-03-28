package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class TestTextAnalyzer {

	@Test
	void test() {
		TextAnalyzer test = new TextAnalyzer();
		String[][] output = test.getWordCount(5, "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
		String[][] expected = {
				{"me","9"},
				{"at","8"},
				{"with","8"},
				{"or","8"},
				{"then","8"}
		};
		
		Assert.assertArrayEquals(expected,output);
	}

}
