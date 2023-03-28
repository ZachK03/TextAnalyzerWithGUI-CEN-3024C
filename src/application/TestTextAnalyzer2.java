package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class TestTextAnalyzer2 {

	@Test
	void test() {
		TextAnalyzer test = new TextAnalyzer();
		String[][] output = test.getWordCount(5, "https://allpoetry.com/poem/8509539-Bluebird-by-Charles-Bukowski");
		String[][] expected = {
				{"to","9"},
				{"i","9"},
				{"in","8"},
				{"that","7"},
				{"a","6"}
		};
		
		Assert.assertArrayEquals(expected,output);
	}

}
