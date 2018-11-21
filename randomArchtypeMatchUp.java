package ygo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Alec Pierce
 * 
 * <p>
 * Takes in a txt file containing the yugioh archetypes
 * and returns rows of archetype match-ups as output. 
 * </p>
 * 
 */
public class randomArchtypeMatchUp {
	public static void main(String[] args) {
		ArrayList<String> archetypeList = new ArrayList<String>();
		String matchUp = "";
		try {
			FileReader file = new FileReader("C:\\ygoArchetypes.txt");
			BufferedReader reader = new BufferedReader(file);
			PrintWriter outputFile = new PrintWriter("C:\\randomYGOArchetypeMatchUp.txt", "UTF-8");
			
			outputFile.flush(); // enables outputFile to have new content after every run
			
			String input = "";
			int archetypeCounter = 0;
			while ((input = reader.readLine()) != null) { 
				archetypeList.add(input);
				archetypeCounter++;
			}
			archetypeCounter = 0; // reset the counter
			
			Random rand = new Random();
			while (!archetypeList.isEmpty()) {
				int index = rand.nextInt(archetypeList.size()); // creates a pseudo-random index
				String archetype = archetypeList.get(index);
				if ((archetypeCounter % 2) == 0) {
					matchUp = matchUp.concat(archetype);
				}
				else if ((archetypeCounter % 2) == 1) {
					matchUp = matchUp.concat(" Vs. " + archetype); // create a match up using two archetypes
					outputFile.write(matchUp);
					outputFile.println(); // after writing to the file, terminate the current line
					matchUp = "";
				}
				archetypeCounter++;
				archetypeList.remove(index); // remove the archetype so it's not chosen for another match up
			}
			file.close();
			outputFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

}
