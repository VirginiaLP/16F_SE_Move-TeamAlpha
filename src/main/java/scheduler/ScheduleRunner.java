package scheduler;

import org.apache.commons.lang3.SystemUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.*;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by kevin on 11/12/16.
 */
public class ScheduleRunner
{
    private static final Logger LOGGER = Logger.getLogger(ScheduleRunner.class.getPackage().getName());

    public static void main(String... args)
    {
        // check if the user has a data directory
        Path dataDirectory = Paths.get(System.getProperty("user.home"), ".schedule_data");
        boolean reload = args.length == 2;

        Path csvFile = null;
        Path roomsFile = null;

        // read user arguments
        try
        {
            // read general and room CSV files
            if (reload)
            {
                csvFile = Paths.get(args[0]);
                roomsFile = Paths.get(args[1]);
            }

            // unless they are not provided
            if (!(args.length == 0 || reload))
            {
                throw new IllegalArgumentException();
            }
        }
        catch (IllegalArgumentException e)
        {
            System.err.println("Improper number of arguments: Expected 0 or 2, received " + args.length);
            System.exit(1);
        }


        // if the folder does not exist, create it
        if (!Files.exists(dataDirectory))
        {
            try
            {
                if (SystemUtils.IS_OS_WINDOWS)
                    configWindows();

                if (SystemUtils.IS_OS_MAC)
                    configMac();

                if (SystemUtils.IS_OS_LINUX)
                    configLinux();
            }
            catch (IOException e)
            {
                System.err.println("Could not configure USER_HOME/.schedule_data");
                e.printStackTrace();
                System.exit(1);
            }
        }

        // set up log file
        try
        {
            Path logFile = Paths.get(dataDirectory.toString(), "schedule_operations.log");

            if (!Files.exists(logFile))
                Files.createFile(logFile);

            // print to designated log file
            FileHandler fh = new FileHandler(Paths.get(dataDirectory.toString(), "schedule_operations.log").toString());
            fh.setFormatter(new SimpleFormatter());

            LOGGER.addHandler(fh);
            LOGGER.setLevel(Level.ALL);
        }
        catch (IOException e)
        {
            System.err.println("Could not configure logger");
            e.printStackTrace();
            System.exit(1);
        }

        // load in information
        try
        {
            // connect to database
            LOGGER.info("Setting up database path");
            DataManager.setDBPath(dataDirectory);   // set path to database

            LOGGER.info("Setting up database connection in DataManager");
            DataManager.initConnection();           // set up database connection in DataManager

            // load in data from CSVs
            if (reload)
            {
                LOGGER.info("Loading system information from CSV");
                DataLoad.loadGeneral(csvFile);

                LOGGER.info("Loading room information from CSV");
                DataLoad.loadRooms(roomsFile);

                // update database with loaded data
                LOGGER.info("Updating database with system information");
                DataManager.updateDatabase();
            }
            else
                LOGGER.info("No CSVs to reload");
            
            // begin user session
            ConsoleInterface.start();
            
            LOGGER.info("Closing database connection in DataManager");
            DataManager.closeConnection();          // close connection to database in DataManager
        }
        catch (IOException e)
        {
            LOGGER.severe("Exception loading the database");
        }
    }

    public static void configWindows() throws IOException
    {
        // identify user's home directory
        Path dataDirectory = Paths.get(System.getProperty("user.home") + "\\.schedule_data\\");

        // make folder hidden by Windows system
        Files.setAttribute(dataDirectory, "dos:hidden", true);

        // create the data directory
        Files.createDirectory(dataDirectory);
    }

    public static void configMac() throws IOException
    {
        // identify user's home directory
        Path dataDirectory = Paths.get(System.getProperty("user.home") + "/.schedule_data/");

        // create the set of permissions for the directory
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxr-x---");
        FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);

        // create the data directory
        Files.createDirectory(dataDirectory, attr);
    }

    public static void configLinux() throws IOException
    {
        // identify user's home directory
        Path dataDirectory = Paths.get(System.getProperty("user.home") + "/.schedule_data/");

        // create the set of permissions for the directory
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxr-x---");
        FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);

        // create the data directory
        Files.createDirectory(dataDirectory, attr);
    }
}
