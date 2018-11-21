package ygo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Alec Pierce
 *	<p>
 *	Parses a txt file which contains yugioh cards separated by commas.
 *	</p>
 */
public class randomDeckGenerator {
	public static void main(String[] args) {
		try {
			ArrayList<String> cardList = new ArrayList<String>();
			FileReader file = new FileReader("C:\\ygoCards.txt");
			BufferedReader reader = new BufferedReader(file);
			PrintWriter deck = new PrintWriter("C:\\randomDeck.txt", "UTF-8");
			PrintWriter deck2 = new PrintWriter("C:\\randomDeck2.txt", "UTF-8");
			FileReader commaFile = new FileReader("C:\\commaCards.txt");
			BufferedReader commaReader = new BufferedReader(commaFile);
			
			deck.flush(); // new deck
			deck2.flush(); // new deck2
			
			String input;
			ArrayList<String> commaCards = new ArrayList<String>();
			ArrayList<String> commaRemovedCards = new ArrayList<String>();
			while ((input = commaReader.readLine()) != null) {
				commaCards.add(input.trim());
				commaRemovedCards.add(input.replaceFirst("(\\.*)[,](\\s+\\.*)", "$1$2").trim());
			}
			
			input = "";
			while ((input = reader.readLine()) != null) { 
				if (!input.isEmpty() && input.matches(".+[,]{1}.*")) {
					input = removeComma(input, commaCards, commaRemovedCards);
					String[] cardArray = input.split("[,]");
					for (String card : cardArray) {
						// Keep: Token Stampede, Token Feastevil, Token Sundae, Token Thanksgiving, Oh tokenbaum!
						// Remove: tokens with trailing whitespace and parenthesis in their names, 
							// "token" at the end of card name, and the plain "token" card
						card = card.trim();
						if (card.matches(".*Token.*")) {
							if (!(card.matches("^Token\\s*[(]+.*[)]+$|.+\\s+Token|^Token$"))) {
								cardList.add(card);
							}
						} else {
							cardList.add(card);
						}
					}
				}
			}
			
			// For deck 1
			Random rand = new Random();
			long seed = System.nanoTime();
			System.out.println(seed); // 
			while (seed > cardList.size()) {
				seed = (seed/2);
			}
			rand.setSeed(seed);
			
			// For deck 2
			Random rand2 = new Random();
			long seed2 = System.nanoTime();
			System.out.println(seed2);
			while (seed2 > cardList.size()) {
				seed2 = (seed2/2);
			}
			rand2.setSeed(seed2);
			
			createRandomDecks(cardList, deck, deck2, rand, seed, seed2);
			commaFile.close();
			file.close();
			deck.close();
			deck2.close();
			commaReader.close();
			reader.close();
		} catch (FileNotFoundException fileNotFoundException) {
			fileNotFoundException.printStackTrace();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	/**
	 * Removes the comma from the passed in card, if it contains one
	 * @param input
	 * @param commaCards
	 * @param commaRemovedCards
	 * @return String input
	 */
	private static String removeComma(String input, ArrayList<String> commaCards, ArrayList<String> commaRemovedCards) {
		for (String commaCard : commaCards) {
			if (input.contains(commaCard)) {
				int index = commaCards.indexOf(commaCard);
				String commaRemovedCard = commaRemovedCards.get(index);
				input = input.replace(commaCard, commaRemovedCard);
			}
		}
		return input;
	}

	/**
	 * Creates both of the random decks by grabbing cards using indexes, and writing the retrieved cards to their respective files.
	 * @param cardList
	 * @param deck
	 * @param deck2
	 * @param rand
	 * @param seed
	 * @param seed2
	 */
	private static void createRandomDecks(ArrayList<String> cardList, PrintWriter deck, PrintWriter deck2, Random rand,
			long seed, long seed2) {
		// Creates random deck 1 and 2
		int deckCounter = 0;
		int deckSize = 55;
		while (deckCounter < deckSize) {
			int index = rand.nextInt((int) seed); // creates a pseudo-random index
			String card = cardList.get(index);
			deck.write(card);
			deck.println(); // after adding the card to the deck, terminate the current line
			index = rand.nextInt((int) seed2);
			card = cardList.get(index);
			deck2.write(card);
			deck2.println();
			deckCounter++;
		}
	}
}
