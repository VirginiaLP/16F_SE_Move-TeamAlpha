package scheduler;

import java.io.InputStream;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Created by kevin on 11/8/16.
 */
public class DataManager
{
    private static String CONNECTION_URI = null;
    private static Connection conn = null;

    public static void initConnection()
    {
        try
        {
            conn = getConnection();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void setDBPath(Path path)
    {
        CONNECTION_URI = "jdbc:sqlite:" + path.toString() + "/schedule.db";
    }

    public static Connection getConnection() throws SQLException
    {
        if (conn == null)
            conn = DriverManager.getConnection(CONNECTION_URI);

        return conn;
    }

    public static void updateDatabase()
    {
        // maintain information on number of rows affected
        int roomsAffected = 0;
        int timesAffected = 0;
        int studentsAffected = 0;
        int professorsAffected = 0;
        int sectionsAffected = 0;

        try
        {
            // initialize database
            initDatabase();

            // initialze connection in DataInsert
            DataInsert.initConnection();

            // insert entities, count number of inserts
            roomsAffected = DataInsert.insertRooms(DataLoad.rooms);
            timesAffected = DataInsert.insertTimes(DataLoad.times);
            studentsAffected = DataInsert.insertStudents(DataLoad.students);
            professorsAffected = DataInsert.insertProfessors(DataLoad.professors);
            sectionsAffected = DataInsert.insertSections(DataLoad.sections);

            // build associative entites
            DataInsert.insertEnrolls(DataLoad.students);
            DataInsert.insertAssigns(DataLoad.professors);

            // close connection in DataInsert
            DataInsert.closeConnection();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void initDatabase() throws SQLException
    {
        System.out.println("Initializing database");
        // access database initialization file
        ClassLoader loader = DataManager.class.getClassLoader();
        InputStream initScriptStream = loader.getResourceAsStream("config_schedule_db.sql");
        String initScript = "";

        // store the SQL script in a string
        Scanner scan = new Scanner(initScriptStream);

        while (scan.hasNextLine())
            initScript += scan.nextLine() + "\n";

        scan.close();
        System.out.println("Init script built - it reads as follows:");
        System.out.println(initScript);
        // initialize the database
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            stmt.executeUpdate(initScript);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (stmt != null)
                stmt.close();
        }
    }

    public static void closeConnection()
    {
        try
        {
            if (conn != null)
                conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
