package application;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ TestTextAnalyzer.class, TestTextAnalyzer2.class })
public class AllTests {

}
