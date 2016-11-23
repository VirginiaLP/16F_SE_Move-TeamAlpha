package scheduler;

import org.apache.commons.lang3.SystemUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.*;
import java.util.Set;

/**
 * Created by kevin on 11/12/16.
 */
public class ScheduleRunner
{
    public static void main(String... args)
    {
        // check if the user has a data directory
        Path dataDirectory = Paths.get(System.getProperty("user.home") + "/.schedule_data/");
        System.out.println("User Home: " + System.getProperty("user.home"));
        System.out.println("Data Directory: " + dataDirectory.toString());
        Path csvDirectory = Paths.get(args[0]);

        // if the folder does not exist, create it
        if (!Files.exists(dataDirectory))
        {
            System.out.println("Directory does not exist");
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
        else
            System.out.println("Directory apparently does exist");

        try
        {
            // load in data from CSV
            DataLoad.loadGeneral(csvDirectory);

            // update database
            DataManager.setDBPath(dataDirectory);   // set path to database
            DataManager.initConnection();           // set up database connection in DataManager
            DataManager.updateDatabase();           // update database with loaded data
            DataManager.closeConnection();          // close connection to database in DataManager
        }
        catch (IOException e)
        {
            e.printStackTrace();
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
        System.out.println("Here I am to make the file!");
        // identify user's home directory
        Path dataDirectory = Paths.get(System.getProperty("user.home") + "/.schedule_data/");
        System.out.println("Just made the directory: " + dataDirectory.toString());

        // create the set of permissions for the directory
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxr-x---");
        FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);
        System.out.println("Permissions set");
        // create the data directory
        Files.createDirectory(dataDirectory, attr);
        System.out.println("Directory probably created");
    }
}
