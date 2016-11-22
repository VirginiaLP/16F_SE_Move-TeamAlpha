package scheduler;

import org.apache.commons.lang3.SystemUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.*;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by kevin on 11/12/16.
 */
public class ScheduleRunner
{
    public static void main(String... args)
    {
        // check if the user has a data directory
        Path dataDirectory = Paths.get(System.getProperty("user.home") + "./.schedule_data/");
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
                System.err.println(e);
            }
        }

        try
        {
            DataLoad.loadGeneral(csvDirectory);
            DataManager.setDBPath(Paths.get("data/schedule_data.db"));
            DataManager.updateDatabase();
        }
        catch (IOException e)
        {
            System.err.println(e);
        }
        catch (SQLException e)
        {
            System.err.println(e);
        }
    }

    public static void configWindows() throws IOException
    {
        // identify user's home directory
        Path dataDirectory = Paths.get(System.getProperty("user.home") + ".\\.schedule_data\\");

        // make folder hidden by Windows system
        Files.setAttribute(dataDirectory, "dos:hidden", true);

        // create the data directory
        Files.createDirectory(dataDirectory);
    }

    public static void configMac() throws IOException
    {
        // identify user's home directory
        Path dataDirectory = Paths.get(System.getProperty("user.home") + "./.schedule_data/");

        // create the set of permissions for the directory
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxr-x---");
        FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);

        // create the data directory
        Files.createDirectory(dataDirectory, attr);
    }

    public static void configLinux() throws IOException
    {
        // identify user's home directory
        Path dataDirectory = Paths.get(System.getProperty("user.home") + "./.schedule_data/");

        // create the set of permissions for the directory
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxr-x---");
        FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);

        // create the data directory
        Files.createDirectory(dataDirectory, attr);
    }
}
