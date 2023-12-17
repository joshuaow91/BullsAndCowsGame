package bullscows;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int length = 0, range = 0;
        System.out.println("Input the length of the secret code:");
        try {
            length = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Error: \"" + scanner.nextLine() + "\" isn't a valid number.");
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        try {
            range = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Error: \"" + scanner.nextLine() + "\" isn't a valid number.");
            return;
        }

        if (length <= 0 || range <= 0) {
            System.out.println("Error: Length and range must be positive.");
            return;
        }
        if (length > range) {
            System.out.println("Error: it's not possible to generate a code with a length of "
                    + length + " with " + range + " unique symbols.");
            return;
        }
        if (range > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }

        String secretCode = generateSecretCode(length, range);
        System.out.println("The secret is prepared: " + "*".repeat(length) + " (" + getRangeString(range) + ").");
        System.out.println("Okay, let's start a game!");

        int turn = 1;
        boolean guessed = false;

        while (!guessed) {
            System.out.println("Turn " + turn + ":");
            String input = scanner.nextLine();

            guessed = totalBullsCows(input, secretCode);
            turn++;
        }

        System.out.println("Congratulations! You guessed the secret code.");
    }

    public static String generateSecretCode(int length, int range) {
        Random random = new Random();
        StringBuilder secretCode = new StringBuilder();
        HashSet<Character> usedChars = new HashSet<>();

        while (secretCode.length() < length) {
            char ch = (char) (random.nextInt(range) < 10 ? '0' + random.nextInt(10) : 'a' + random.nextInt(range - 10));
            if (usedChars.add(ch)) {
                secretCode.append(ch);
            }
        }

        return secretCode.toString();
    }

    public static String getRangeString(int range) {
        if (range <= 10) {
            return "0-" + (char)('0' + range - 1);
        } else if (range == 11) {
            return "0-9, a";
        } else {
            return "0-9, a-" + (char)('a' + range - 11);
        }
    }

    public static boolean totalBullsCows(String input, String secretCode) {
        if (input.length() != secretCode.length()) {
            System.out.println("Please enter a " + secretCode.length() + "-digit number.");
            return false;
        }

        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == secretCode.charAt(i)) {
                bulls++;
            } else {
                for (int j = 0; j < secretCode.length(); j++) {
                    if (input.charAt(i) == secretCode.charAt(j) && i != j) {
                        cows++;
                        break;
                    }
                }
            }
        }

        System.out.println(bulls + " bulls and " + cows + " cows.");
        return bulls == secretCode.length();
    }
}
