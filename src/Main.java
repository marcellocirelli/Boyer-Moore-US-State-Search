import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Array of states
        String[] states = {
                "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
                "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
                "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan",
                "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada",
                "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina",
                "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island",
                "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
                "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
        };

        // Combined into a single searchable string
        String allStates = String.join(" ", states).toLowerCase();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the State Search Program!");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Display the text");
            System.out.println("2. Search");
            System.out.println("3. Exit program");
            System.out.print("Please enter a choice between 1-3: ");

            String input = scanner.nextLine();

            // Display the text
            if (input.equals("1")) {
                System.out.println("\nList of States");
                for (String state : states) {
                    System.out.println(state);
                }
                System.out.print("Press enter to return to the main menu...");
                scanner.nextLine();

            // Boyer-Moore search
            } else if (input.equals("2")) {
                System.out.print("\nPlease enter a search pattern: ");
                String pattern = scanner.nextLine().toLowerCase();

                int[] matches = boyerMooreSearch(allStates, pattern);

                // If there are no string matches, returns the following message
                if (matches.length == 0) {
                    System.out.println("No matches found.");

                // Displays the match indices if there are matches separated by semicolons
                } else {
                    System.out.print("Match indices: ");
                    for (int index : matches) {
                        System.out.print(index + "; ");
                    }

                    // Also prints out the state text names
                    // I was unsure if just the indices were required, so adding this in for safe measure
                    System.out.print("\nMatching states: ");
                    for (String state : states) {
                        if (state.toLowerCase().contains(pattern)) {
                            System.out.print(state + "; ");
                        }
                    }
                }

                System.out.print("\nPress enter to return to the main menu...");
                scanner.nextLine();

            // Exit program
            } else if (input.equals("3")) {
                System.out.println("Exiting program...");
                break;

            } else {
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    // An array to fill with the last known position of each char in the pattern
    private static int[] buildBadCharTable(String pattern) {
        int[] table = new int[256];
        // Loops through each index, sets value to -1
        for (int i = 0; i < table.length; i++) {
            table[i] = -1;
        }

        // Fill the bad character table
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            table[c] = i;
        }

        return table;

    }

    // Method for the Boyer-Moore search algorithm
    private static int[] boyerMooreSearch(String text, String pattern) {
        int[] badCharTable = buildBadCharTable(pattern);
        List<Integer> matchIndices = new ArrayList<>();

        int textLen = text.length();
        int patLen = pattern.length();
        int shift = 0;

        // Keeps sliding the pattern over the text until it no longer fits
        while (shift <= textLen - patLen) {
            int j = patLen - 1;

            // Compares chars from left to right
            while (j >= 0 && pattern.charAt(j) == text.charAt(shift + j)) {
                j--;
            }

            // If j < 0, records the index
            if (j < 0) {
                matchIndices.add(shift);

                // Shifts the pattern by 1 or more
                shift += (shift + patLen < textLen) ? patLen - badCharTable[text.charAt(shift + patLen)] : 1;

            } else {
                // Mismatch: Uses bad character rule to shift
                char badChar = text.charAt(shift + j);
                int lastSeen = badCharTable[badChar];

                // Figure out how far to shift the pattern forward based on last seen position
                int skip = Math.max(1, j - lastSeen);
                shift += skip;
            }
        }
        // Converts result list into an array
        return matchIndices.stream().mapToInt(i -> i).toArray();
    }
}

