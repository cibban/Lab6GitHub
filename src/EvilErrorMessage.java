public class EvilErrorMessage {

    public static void userInput(String searchString) {
        System.err.print("Did you seriously think you could use ");
        System.err.print("\"");                                     //Only for extra bullying.
        for (int i = 0; i < searchString.length(); i++) {           //Need the letters' place in the word.
            if (i % 2 == 0) {                                       //Finds every second letter.
                System.err.print(("" + searchString.charAt(i)).toLowerCase());
            } else {
                System.err.print(("" + searchString.charAt(i)).toUpperCase());
            }
        }
        System.err.print("\"");                                     //Only for extra bullying.
        System.err.print(" in a cocktail?? Start the program over and use a normal ingredient, you noob...");
    }
}
