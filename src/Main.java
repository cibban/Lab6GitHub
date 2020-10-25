import Models.Root;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Scanner;


public class Main {
	public static ObjectMapper om = new ObjectMapper();
	public static Root allTheCocktails;
	public static Scanner input = new Scanner(System.in);
	public static String searchString;


	public static void main(String[] args) {
		readFile();
		searchCocktail();
	}

	public static void searchCocktail(){
		System.out.println("What ingredient do you have?");
		searchString = input.nextLine();
			doSearch();
	}

	public static void readFile() {
		try {
			allTheCocktails = om.readValue(new File("query.json"), Root.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void doSearch() {
		String lastCocktailValue = "";
		int firstCocktailIndex = 0;
		String cocktailValue;
		String link;
		String ingredientValue;

		for (int i = 0; i < allTheCocktails.results.bindings.size(); i++) {
			cocktailValue = allTheCocktails.results.bindings.get(i).cocktailLabel.value;
			link = allTheCocktails.results.bindings.get(i).cocktail.value;
			ingredientValue = allTheCocktails.results.bindings.get(i).ingredientLabel.value;
			if(!lastCocktailValue.equals(cocktailValue)){
				firstCocktailIndex = i;
			}
			if(searchString.equals(ingredientValue)) {
				System.out.print(cocktailValue);
				System.out.println(" (" + link + ")");
				listIngredients(firstCocktailIndex, cocktailValue);
			}


			lastCocktailValue = cocktailValue;
		}
	}
	public static void listIngredients(int cocktailFirstIndex, String cocktailLocalValue) {
		System.out.println("Ingredients:");
		for (int j = cocktailFirstIndex; j > 0 ; j++) {
			if(!cocktailLocalValue.equals(allTheCocktails.results.bindings.get(j).cocktailLabel.value)) break;
			System.out.println("             " + allTheCocktails.results.bindings.get(j).ingredientLabel.value);
		}

	}
}
