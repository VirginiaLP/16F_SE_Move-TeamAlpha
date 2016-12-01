package scheduler;

import java.util.Scanner;
import java.io.IOException;

public class ConsoleInterface
{
    public static void start()
    {
        boolean run = true;
        Scanner scan = new Scanner(System.in);

        while (run) {
            String input = "";

            // present input prompt icon
            prompt();

            // receive user input
            input = scan.nextLine();

            // created tokens from user input
            String[] tokens;

            if (!input.contains(" "))
            {
                tokens = new String[1];
                tokens[0] = input;
            }
            else
                tokens = input.split("\\s+");

            if (tokens[0].toUpperCase().equals("HELP"))
            {
                // Help
                showHelp();
            }
            else if (tokens[0].toUpperCase().equals("MOVE"))
            {
                // Get current crn
                String crn = tokens[1];

                System.out.println("\tLooking to move: " + crn);

                // query for data
				DataManager.getAlternatives(crn)
            }
			
			else if (tokens[0].toUpperCase().equals("INFO"))
			{
				// Info
				showInfo();
			}
			
            else if (tokens[0].toUpperCase().equals("EXIT"))
            {
                // Exit
                run = false;
            }
            else
            {
                // Error
                showError("No such query.  Type 'HELP' for help.");
            }
        }
    }

    public static void showInfo()
    {
        System.out.println("Version 1.0");
        System.out.println("Authors:\n" +
                "* Kevin Shurtz\n" +
                "* Isaak Ramirez\n" +
                "* Virginia Pettit\n");
    }

    public static void showHelp()
    {
		// Print out program instructions and all possible commands.
        System.out.println("\nOur system allows the user to input a section of a " + 
		"class that they wish to move to a different time. The program tells them "+
		"what times would be best for the students in the class prioritizing by classification.");

        System.out.println("\nBasic Query Syntax:");
        System.out.println("'MOVE [CRN]'");

        System.out.println("\nGeneral Commands");
        System.out.println("'EXIT'\t\texits application'");
        System.out.println("'HELP'\t\trequest this help page");
		System.out.println("'INFO'\t\trequest the information page")
	}

    public static void showError(String errorDesc)
    {
        // Print error message
        System.out.println("Error: " + errorDesc);
    }

    public static void showError()
    {
        System.out.println("Error: command not recognized");
    }

    private static void prompt()
    {
        System.out.print("> ");
    }
}