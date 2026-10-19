import java.util.Scanner;

public class CardGame {
    public static void main(String[] args) {
        CardDeck deck = new CardDeck();
        deck.shuffle();

        int playerTokens;
        int betAmount;
        int cardsPlayed = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Card Game!");
        System.out.print("Enter the number of tokens you want to start with: ");

        while (true) {
            if (scanner.hasNextInt()) {
                playerTokens = scanner.nextInt();
                if (playerTokens > 0) {
                    break;
                }
                System.out.print("Please enter a positive number of tokens: ");
            } else {
                System.out.print("Invalid input. Please enter a positive number of tokens: ");
                scanner.next();
            }
        }

        while (playerTokens > 0) {
            System.out.println("\nYou have " + playerTokens + " tokens.");
            System.out.print("Enter your bet amount: ");

            while (true) {
                if (scanner.hasNextInt()) {
                    betAmount = scanner.nextInt();
                    if (betAmount > 0 && betAmount <= playerTokens) {
                        break;
                    }
                    System.out.print("Invalid bet amount. Please enter a positive number up to " + playerTokens + ": ");
                } else {
                    System.out.print("Invalid input. Please enter a positive number up to " + playerTokens + ": ");
                    scanner.next();
                }
            }

            if (cardsPlayed + 3 > CardDeck.NUMCARDS-10) {
                deck.shuffle();
                cardsPlayed = 0;
            }

            ArrayLog<Card> game = new ArrayLog<>("Current hand");
            game.add(deck.nextCard());
            game.add(deck.nextCard());
            game.add(deck.nextCard());
            cardsPlayed += 3;

            Card card1 = game.get(0);
            Card card2 = game.get(1);
            Card card3 = game.get(2);
            System.out.println("Your cards: " + card1 + ", " + card2);
            if (card1.compareTo(card2) == 0) {
                System.out.println("The first two cards are the same. You automatically win this round.");
                playerTokens += betAmount;
                System.out.println("You now have " + playerTokens + " tokens.");
                continue;
            } else if (card1.compareTo(card2) == 1 || card1.compareTo(card2) == -1) {
               System.out.println("The first two cards are one apart. You automatically lose this round.");
                playerTokens -= betAmount;
                System.out.println("You now have " + playerTokens + " tokens.");
            }

            if(betAmount * 2 < playerTokens) {
                System.out.print("Do you want to double your bet?: ");
                String doubleResponse = scanner.next();
                while (!doubleResponse.equalsIgnoreCase("yes")
                        && !doubleResponse.equalsIgnoreCase("no")) {
                    System.out.print("Invalid response. Answer yes or no: ");
                    doubleResponse = scanner.next();
                }

                if (doubleResponse.equalsIgnoreCase("yes")) {
                        betAmount *= 2;
                        System.out.println("Your bet has been doubled to " + betAmount + " tokens.");

                } else {
                    System.out.println("Your bet remains at " + betAmount + " tokens.");
                }
             }

            scanner.nextLine();
            System.out.print("Is the third card between or outside the first two? ");
            String response = scanner.nextLine().trim();
            while (!response.equalsIgnoreCase("between") && !response.equalsIgnoreCase("in between") && !response.equalsIgnoreCase("outside")) {
                System.out.print("Invalid response. Please enter between or outside: ");
                response = scanner.nextLine().trim();
            }
            int firstRank = card1.compareTo(card3);
            int secondRank = card2.compareTo(card3);
            if(response.equalsIgnoreCase("between") || response.equalsIgnoreCase("in between")) {
                if((firstRank < 0 && secondRank > 0) || (firstRank > 0 && secondRank < 0)) {
                    System.out.println(card3.toString());
                    System.out.println("Correct! The third card is between the first two.");
                    playerTokens += betAmount;
                } else {
                    System.out.println(card3.toString());
                    System.out.println("Incorrect! The third card is not between the first two.");
                    playerTokens -= betAmount;
                }
            } else {
                if((firstRank < 0 && secondRank < 0) || (firstRank > 0 && secondRank > 0)) {
                    System.out.println(card3.toString());
                    System.out.println("Correct! The third card is between the first two.");
                    playerTokens += betAmount;
                } else {
                    System.out.println(card3.toString());
                    System.out.println("Incorrect! The third card is not between the first two.");
                    playerTokens -= betAmount;
                }

            }
            System.out.println("You now have " + playerTokens + " tokens.");
        }

        System.out.println("Game over! You ran out of tokens.");
        scanner.close();
    }
}
