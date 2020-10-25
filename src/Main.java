import Models.EvilErrorMessage;
import Models.Root;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Scanner;

/**
 * @author
 * Alex & Patrik like cocktails and have built a way to get access to all cocktails you can make, based on what you have at home.
 * Both are programmers in the making, and both also suffer from a bad sense of humor.
 * The result is this very simple app (which was quite complicated for us to make), sprinkled with a pretty rude error message.
 * If you do not enjoy being insulted because of your failures, please use another app.
 */
public class Main {
	public static ObjectMapper om = new ObjectMapper();
	public static Root allTheCocktails;
	public static Scanner input = new Scanner(System.in);
	public static String searchString;

	public static void main(String[] args) {
		readFile();
		askForInput();
	}

	public static void askForInput(){
		System.out.println("Welcome to Patrik & Alex's fantastic database for cocktails!");
		System.out.println("Here we list all cocktails that contain a certain ingredient.");
		System.out.println("Please enter what ingredient you have at home (only one word, in English, correctly spelled!).");
		searchString = input.nextLine();
			doSearch();
	}

	/**readFile()
	 * This method finds cocktails and their ingredients from the "query.json" file that we downloaded from Wikipedia,
	 * as that is an open data-pool that is already well organized and easy to use.
	 */
	public static void readFile() {
		try {
			allTheCocktails = om.readValue(new File("query.json"), Root.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lots of things happen here... A bit too many...
	 */
	public static void doSearch() {
		String lastCocktailValue = "";
		int firstCocktailIndex = 0;
		String cocktailValue;
		String link;
		String ingredientValue;

		boolean foundSomething = false;
		for (int i = 0; i < allTheCocktails.results.bindings.size(); i++) {
			cocktailValue = allTheCocktails.results.bindings.get(i).cocktailLabel.value;
			link = allTheCocktails.results.bindings.get(i).cocktail.value;
			ingredientValue = allTheCocktails.results.bindings.get(i).ingredientLabel.value;
			if(!lastCocktailValue.equals(cocktailValue)){
				firstCocktailIndex = i;
			}
			if(searchString.equals(ingredientValue)) {
				foundSomething = true;
				System.out.print(cocktailValue);
				System.out.println(" (" + link + ")");
				listIngredients(firstCocktailIndex, cocktailValue);
			}
			lastCocktailValue = cocktailValue;
		}
		if (!foundSomething){
			EvilErrorMessage.userInput(searchString);
		}
	}

	/**
	 * The program only enters this section if the user input is the same as one of the ingredients in the querty-file.
	 * @param cocktailFirstIndex Whut...?
	 * @param cocktailLocalValue Whut...?
	 */
	public static void listIngredients(int cocktailFirstIndex, String cocktailLocalValue) {
		System.out.println("Ingredients:");
		for (int j = cocktailFirstIndex; j > 0 ; j++) {
			if(!cocktailLocalValue.equals(allTheCocktails.results.bindings.get(j).cocktailLabel.value)) break;
			System.out.println("             " + allTheCocktails.results.bindings.get(j).ingredientLabel.value);
		}
	}
}
