import Models.Root;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * @author
 * Alex & Patrik like cocktails and have built a way to get access to all cocktails you can make, based on what you have at home.
 * Both are programmers in the making, and both also suffer from a bad sense of humor.
 * The result is this very simple app (which was quite complicated for us to make), sprinkled with a pretty rude error message.
 * If you do not enjoy being insulted because of your failures, please use another app.
 *
 * Planned future development:
 * - Multi item search.
 * - Omit non-ingredient objects like glasses and ice.
 * - Sounds-like search, whisky/whiskey for instance.
 *
 *  Also, to be able to take this product to the market, we need a buzzword USP. Therefore, we want to sprinkle some Blockchain technology
 *  on top of this application. Exactly how that will happen, we are not sure yet. The future will tell.
 *
 *  Best Regards
 *
 *  Patrik & Alex
 */
public class Main {
	public static ObjectMapper om = new ObjectMapper();
	public static Root allTheCocktails;
	public static Scanner input = new Scanner(System.in);

	public static void askForInput(){
		System.out.println("Welcome to Patrik & Alex's fantastic database for cocktails!");
		System.out.println("Here we list all cocktails that contain a certain ingredient.");
		System.out.println("Please enter what ingredient you have at home (only one word, in English, correctly spelled!).");
			doSearch(input.nextLine());
	}

	/**updateCocktailData()
	 * This method gets updated data from WikiData (Wikipedia) and writes it to a JSON-file.
	 */

	public static void updateCocktailData(String filePath) throws IOException {
		FileWriter outputFileJSON = new FileWriter(filePath);
		URL jqueryURL = new URL("https://query.wikidata.org/sparql?format=json&query=%23Cocktails%0A%23defaultView%3AList%0ASELECT%20%3FcocktailLabel%20%3FingredientLabel%20%3FcocktailArticle%20WHERE%20%7B%0A%20%20%3Fcocktail%20wdt%3AP279%20wd%3AQ134768%3B%0A%20%20wdt%3AP186%20%3Fingredient%20.%0A%20%20SERVICE%20wikibase%3Alabel%20%7B%20bd%3AserviceParam%20wikibase%3Alanguage%20%22en%22.%20%7D%0A%20%20%3FcocktailArticle%20schema%3Aabout%20%3Fcocktail%20.%0A%20%20%3FcocktailArticle%20schema%3AinLanguage%20%22en%22%20.%0A%20%20FILTER%20(SUBSTR(str(%3FcocktailArticle)%2C%201%2C%2025)%20%3D%20%22https%3A%2F%2Fen.wikipedia.org%2F%22)%0A%0A%7D%0AORDER%20BY%20(%3FcocktailLabel)");
		URLConnection wikidataConnection = jqueryURL.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(wikidataConnection.getInputStream()));
		String inputLine;

		System.out.println("Updating database...");

		while ((inputLine = in.readLine()) != null) {
			outputFileJSON.write(inputLine);
		}
		System.out.println("Writing data to file...\nDone!\n\n\n");
		in.close();
		outputFileJSON.close();
	}

	/**readFile()
	 * This method reads cocktails and their ingredients from the "query.json" file that we downloaded from Wikipedia,
	 * as that is an open data-pool that is already well organized and easy to use.
	 */


	public static void readFile(String filePath) {
		try {
			allTheCocktails = om.readValue(new File(filePath), Root.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads the list of cocktails and ingredients and searches for the ingredient the user typed in.
	 * If the search string is found, a function (listIngredients) is called to list all the ingredients.
	 * If search string is not found, the program becomes rude.
	 */
	public static void doSearch(String searchString) {
		String lastCocktailValue = "";
		int firstCocktailIndex = 0;
		String cocktailValue;
		String link;
		String ingredientValue;
		boolean foundSomething = false;

		for (int i = 0; i < allTheCocktails.results.bindings.size(); i++) {
			cocktailValue = allTheCocktails.results.bindings.get(i).cocktailLabel.value;
			link = allTheCocktails.results.bindings.get(i).cocktailArticle.value;
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
	 * The program only enters this section if the user input is the same as one of the ingredients in the query-file.
	 * @param cocktailFirstIndex Used to find the first row of the cocktail so that all ingredients can be listed.
	 * @param cocktailLocalValue Used to break after the last row of the cocktail ingredients.
	 */
	public static void listIngredients(int cocktailFirstIndex, String cocktailLocalValue) {
		System.out.println("Ingredients:");
		for (int j = cocktailFirstIndex; true ; j++) {
			if(!cocktailLocalValue.equals(allTheCocktails.results.bindings.get(j).cocktailLabel.value)) break;
			System.out.println("             " + allTheCocktails.results.bindings.get(j).ingredientLabel.value);
		}
	}

	public static void main(String[] args) throws IOException {
		String filePath = "cocktailData.json";
		updateCocktailData(filePath);
		readFile(filePath);
		askForInput();
	}

}
