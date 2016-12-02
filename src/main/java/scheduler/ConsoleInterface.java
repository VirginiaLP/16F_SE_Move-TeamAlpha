package scheduler;

import java.util.Scanner;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleInterface
{
    private static final Logger LOGGER = Logger.getLogger(ScheduleRunner.class.getPackage().getName());
    
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
                // help
                showHelp();
            }
            else if (tokens[0].toUpperCase().equals("MOVE"))
            {
                // get current crn
                int crn = Integer.parseInt(tokens[1]);
                System.out.println("Searching for alternative times and rooms...");
                ResultSet rs = null;
                
                try 
                {
                    // query for data
                    rs = DataManager.getAlternatives(crn);
                    
                    int sameOptionCount = 0;
                    
                    int startTimeRecord = 0;
                    int endTimeRecord = 0;
                    String daysRecord = "";
                    
                    System.out.print("Is rs null? ");
                    System.out.println(rs == null ? "YES, IT IS NULL" : "NO, IT'S GOT SOME STUFF");
                    
                    while (rs.next())
                    {
                        int startTime = rs.getInt("ValidTime.start_time");
                        int endTime = rs.getInt("ValidTime.end_time");
                        String days = rs.getString("ValidTime.days_time");
                        
                        if (startTime == startTimeRecord && endTime == endTimeRecord && days.equals(daysRecord))
                            sameOptionCount++;
                        else
                        {
                            // reset record
                            startTimeRecord = startTime;
                            endTimeRecord = endTime;
                            daysRecord = days;
                            
                            // reset sameOptionCount
                            sameOptionCount = 0;
                        }
                        
                        if (sameOptionCount <= 3)
                        {
                            System.out.print(startTime + "\t");
                            System.out.print(endTime + "\t");
                            System.out.print(days + "\t");
                            System.out.print(rs.getString("ValidTime.building_code") + "\t");
                            System.out.print(rs.getString("ValidTime.room_number") + "\t");
                            
                            System.out.println();
                        }
                    }
                }
                catch(SQLException e) 
                {
                    LOGGER.log(Level.SEVERE, "Error processing the ResultSet data", e);
                }
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
		System.out.println("'INFO'\t\trequest the information page");
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