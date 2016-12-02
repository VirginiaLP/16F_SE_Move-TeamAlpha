package scheduler;

import java.io.InputStream;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by kevin on 11/8/16.
 */
public class DataManager
{
    private static final Logger LOGGER  = Logger.getLogger(DataManager.class.getPackage().getName());

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
            LOGGER.info("Inserting Room data into the database");
            roomsAffected = DataInsert.insertRooms(DataLoad.rooms);

            LOGGER.info("Inserting Time data into the database");
            timesAffected = DataInsert.insertTimes(DataLoad.times);

            LOGGER.info("Inserting Student data into the database");
            studentsAffected = DataInsert.insertStudents(DataLoad.students);

            LOGGER.info("Inserting Professor data into the database");
            professorsAffected = DataInsert.insertProfessors(DataLoad.professors);

            LOGGER.info("Inserting Section data into the database");
            sectionsAffected = DataInsert.insertSections(DataLoad.sections);

            // build associative entites
            LOGGER.info("Inserting Enroll data into the database");
            DataInsert.insertEnrolls(DataLoad.students);

            LOGGER.info("Inserting Assign data into the database");
            DataInsert.insertAssigns(DataLoad.professors);

            // close connection in DataInsert
            DataInsert.closeConnection();
        }
        catch (SQLException e)
        {
            LOGGER.severe("Data could not be successfully loaded into the database");
        }
    }

    public static void initDatabase() throws SQLException
    {
        // access database initialization file
        ClassLoader loader = DataManager.class.getClassLoader();
        InputStream initScriptStream = loader.getResourceAsStream("config_schedule_db.sql");
        String initScript = "";

        // store the SQL script in a string
        Scanner scan = new Scanner(initScriptStream);

        while (scan.hasNextLine())
            initScript += scan.nextLine() + "\n";

        scan.close();

        // initialize the database
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            stmt.executeUpdate(initScript);
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.SEVERE, "Database schema could not be successfully loaded", e);
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
    
    public static List<String> getAlternatives(int crn) throws SQLException
    {
        // access database initialization file
        ClassLoader loader = DataManager.class.getClassLoader();
        InputStream initScriptStream = loader.getResourceAsStream("schedule_query_1.sql");
        String initScript = "";

        // store the SQL script in a string
        Scanner scan = new Scanner(initScriptStream);

        while (scan.hasNextLine())
            initScript += scan.nextLine() + "\n";

        scan.close();
       
        // run the query
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String> records = new ArrayList<String>();

        try
        {
            stmt = conn.prepareStatement(initScript,
                    ResultSet.TYPE_FORWARD_ONLY, 
                    ResultSet.CONCUR_READ_ONLY);
            
            stmt.setInt(1, crn);
            stmt.setInt(2, crn);
            stmt.setInt(3, crn);
            stmt.setInt(4, crn);
            
            rs = stmt.executeQuery();
            
            int sameOptionCount = 0;
                    
            int startTimeRecord = 0;
            int endTimeRecord = 0;
            String daysRecord = "";
            
            while (rs.next())
            {
                int startTime = rs.getInt("ValidTime.vt_start_time");
                int endTime = rs.getInt("ValidTime.vt_end_time");
                String days = rs.getString("ValidTime.vt_days");
                
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
                
                if (sameOptionCount < 3)
                {
                    String record = "";
                    
                    record += (startTime + "\t");
                    record += (endTime + "\t");
                    record += (days + "\t");
                    record += (rs.getString("ValidRoom.vr_building_code") + "\t");
                    record += (rs.getString("ValidRoom.vr_room_number") + "\t");
                    
                    records.add(record);
                }
            }
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.SEVERE, "ResultSet could not be processed", e);
        }
        finally
        {
            if (stmt != null)
                stmt.close();
        }
        
        return records;
    }
}
