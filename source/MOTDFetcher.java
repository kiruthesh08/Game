import java.io.*;
import java.net.*;

/**
 * Class to retrieve the Message Of The Day
 * @author Elliott
 */
public class MOTDFetcher {
	public static void main(String[] args) {
		System.out.println(MOTDFetcher.getMOTD());
	}
	
	public static char shiftLetter(char ch, int amount) {
		int ascii = (int)ch;
		if (ascii + amount < 65) {
			ascii = 90 + (amount + (ascii - 65) + 1) % 26;
		} else if (ascii + amount > 90) {
			ascii = 64 + (amount + (ascii - 90)) % 26;
		} else {
			ascii += amount;
		}
		return (char)ascii;
	}
	
	public static String getMOTD() {
		try {
			URL urlPuzzle = new URL("http://cswebcat.swansea.ac.uk/puzzle");
			HttpURLConnection connection = (HttpURLConnection) urlPuzzle.openConnection();
			connection.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine = in.readLine();
			char[] letters = inputLine.toCharArray();
			String newString = "";
			for (int i=0; i<inputLine.length(); i++) {
				if ((i+1) % 2 == 0) {
					newString += shiftLetter(letters[i], (i+1));
				} else {
					newString += shiftLetter(letters[i], -(i+1));
				}
			}
			newString = "CS-230" + newString;
			newString += newString.length();
			
			URL urlMessage = new URL("http://cswebcat.swansea.ac.uk/message?solution="+newString);
			HttpURLConnection connection2 = (HttpURLConnection) urlMessage.openConnection();
			connection2.setRequestMethod("GET");
			BufferedReader in2 = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
			String inputLine2 = in2.readLine();
			return inputLine2;
			
		} catch (MalformedURLException e) {
		
		} catch (IOException e) {
			
		}
		
		return null;
	}
}
