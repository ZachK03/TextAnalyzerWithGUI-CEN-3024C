package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TextAnalyzer {
	/**
	 * Gets the top X number of words for a given site.
	 * <p>
	 * Uses Jsoup to connect to the site and parses the string. Adds up all of the words and returns a multi-dimensional 
	 * array of strings that holds the word and number of occurrences.
	 * @param numberOfWords The number of words desired for the output
	 * @param site The site you want to get the word occurrences of.
	 * @return A multi-dimensional array of strings in format { {"Word", "Count"} }
	 */
	public String[][] getWordCount(int numberOfWords, String site) {
		//Connect and parse website
		Document doc;
		Connection connection;
		try {
			connection = getConnection();
			createTable(connection);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("Failed to connect to database.");
			return null;
		}
		try {
			doc = Jsoup.connect(site).get();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		//Get all paragraph tags
		Elements paragraphs = doc.select("p");
		
		//Create hashmaps to store value
		HashMap<String, String> map = new HashMap<>();
		//Go through each paragraph
		for(Element p : paragraphs) {
			//Retrieve text and split by characters
			String pText = p.text();
			String[] words = pText.split("[ —“”.,?!;-]");
			//For each word in the paragraph
			for(String s : words) {
				s = s.toLowerCase();
				//Check if there is a value in the map, add if not
				String val = map.getOrDefault(s, null);
				if(val == null) {
					map.put(s, "1");
				} else {
					int temp = Integer.valueOf(val);
					temp++;
					map.put(s, String.valueOf(temp));
				}
			}
		}
		
		//Copy data to ArrayList
		ArrayList<List<String>> list = new ArrayList<>();
		for(Map.Entry<String, String> entry : map.entrySet()) {
			String word = entry.getKey();
			String count = entry.getValue();
			List<String> tempList = new ArrayList<String>();
			if(word == "") {continue;};
			tempList.add(word);
			tempList.add(count);
			try {
				insertToTable(connection,word,Integer.valueOf(count));
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(tempList);
		}
		
		//Use Collections.sort to sort 2D array
		Collections.sort(list, new Comparator<List<String>>() {
			@Override
			public int compare(List<String> o1, List<String> o2) {
				return (Integer.valueOf(o2.get(1))).compareTo(Integer.valueOf(o1.get(1)));
			}
		});
		
		//Oh my goodness we have the sorted list!!
		
		String[][] out = new String[numberOfWords][2];
		for (int i = 0; i < numberOfWords; i++) {
			System.out.println(list.get(i).get(0) + " " + list.get(i).get(1));
			out[i][0] = list.get(i).get(0);
			out[i][1] = list.get(i).get(1);
		}
		
		return out;
	}
	
	/**
	 * Creates a connection to the JDBC database.
	 * @return Connection if connection made, null otherwise.
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		try {
			String url = "jdbc:mysql://localhost/wordoccurences";
			String user = "root";
			String pass = "rootPass123";
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(url,user,pass);
			System.out.println("Connected to database.");
			return connection;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	/**
	 * Creates a table, if it doesn't exist, within the passed connection.
	 * @param connection The specified database connection.
	 * @throws Exception
	 */
	public static void createTable(Connection connection) throws Exception {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS wordOccurence(id int NOT NULL AUTO_INCREMENT, word varChar(255), count int, PRIMARY KEY (id));");
		}catch(Exception e) {System.out.println(e);};
	}
	
	/**
	 * Inserts data into the created table.
	 * @param connection The specified database connection.
	 * @param word The word to insert.
	 * @param count The count of insert.
	 * @throws Exception
	 */
	public static void insertToTable(Connection connection, String word, int count) throws Exception {
		try {
			PreparedStatement post = connection.prepareStatement("INSERT INTO wordOccurence (word,count) VALUES ('"+word+"',"+count+");");
			post.executeUpdate();
		} catch (Exception e) {System.out.println(e);};
	}
}
