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
        Path csvDirectory = Paths.get(args[0]);

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
                e.printStackTrace();
            }
        }

        // set up log file
        try
        {
            Path logFile = Paths.get(dataDirectory.toString(), "schedule_operations.log");
            Files.createFile(logFile);

            // print to designated log file
            FileHandler fh = new FileHandler(Paths.get(dataDirectory.toString(), "schedule_operations.log").toString());
            fh.setFormatter(new SimpleFormatter());

            LOGGER.addHandler(fh);
            LOGGER.setLevel(Level.ALL);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // run the system
        try
        {
            // load in data from CSV
            LOGGER.info("Loading system information from CSV");
            DataLoad.loadGeneral(csvDirectory);

            // update database
            LOGGER.info("Setting up database path");
            DataManager.setDBPath(dataDirectory);   // set path to database

            LOGGER.info("Setting up database connection in DataManager");
            DataManager.initConnection();           // set up database connection in DataManager

            LOGGER.info("Updating database with system information");
            DataManager.updateDatabase();           // update database with loaded data

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
