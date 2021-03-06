package scheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by kevin on 11/8/16.
 */
public class DataInsert
{
    private static Connection conn = null;

    public static void initConnection()
    {
        try
        {
            conn = DataManager.getConnection();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static int insertRooms(HashMap<Integer, Room> roomMap) throws SQLException
    {
        int totalAffected = 0;
        int batchMax = 5000;    // this is arbitrary
        int batchCount = 0;
        
        // create prepared SQL update query
        String insertRoom = "INSERT INTO Room " +
                "(room_id, building_code, room_number, max_capacity) VALUES " +
                "(?, ?, ?, ?);";
        
        PreparedStatement stmt = conn.prepareStatement(insertRoom);

        for (int roomID : roomMap.keySet())
        {
            // gather update parameters
            String buildingCode = roomMap.get(roomID).getBuildingCode();
            String roomNumber = roomMap.get(roomID).getRoomNumber();
            int maxCapacity = roomMap.get(roomID).getMaxCapacity();
            
            // bind arguments
            stmt.setInt(1, roomID);
            stmt.setString(2, buildingCode);
            stmt.setString(3, roomNumber);

            // set max capacity to NULL if left unset
            if (maxCapacity == 0)
                stmt.setNull(4, java.sql.Types.INTEGER);
            else
                stmt.setInt(4, maxCapacity);
            
            // add to the batch
            stmt.addBatch();
            
            // if maximum allotted for batch is exceeded, run batch
            batchCount++;
            
            if (batchCount % batchMax == 0)
                totalAffected += stmt.executeBatch().length;
        }
        
        totalAffected += stmt.executeBatch().length;
        stmt.close();
        return totalAffected;
    }

    public static int insertTimes(HashMap<Integer, Time> timeMap) throws SQLException
    {
        int totalAffected = 0;
        int batchCount = 0;
        int batchMax = 5000;
        
        // create prepared SQL update query
        String insertRoom = "INSERT INTO Time " +
                "(time_id, start_time, end_time, class_year, days, term, term_length) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?);";
        
        PreparedStatement stmt = conn.prepareStatement(insertRoom);
        
        for (int timeID : timeMap.keySet())
        {
            // gather update parameters
            int timeStart = timeMap.get(timeID).getTimeStart();
            int timeEnd = timeMap.get(timeID).getTimeEnd();
            int year = timeMap.get(timeID).getYear();
            String days = timeMap.get(timeID).getDays();
            String term = timeMap.get(timeID).getTerm().name();
            String termLength = timeMap.get(timeID).getTermLength().name();
            
            // bind arguments
            stmt.setInt(1, timeID);
            stmt.setInt(2, timeStart);
            stmt.setInt(3, timeEnd);
            stmt.setInt(4, year);
            stmt.setString(5, days);
            stmt.setString(6, term);
            stmt.setString(7, termLength);
            
            // add to the batch
            stmt.addBatch();
            
            // if maximum allotted for batch is exceeded, run batch
            batchCount++;
            
            if (batchCount % batchMax == 0)
                totalAffected += stmt.executeBatch().length;
        }
        
        totalAffected += stmt.executeBatch().length;
        stmt.close();
        return totalAffected;
    }

    public static int insertStudents(HashMap<Integer, Student> studentMap) throws SQLException
    {
        int totalAffected = 0;
        int batchCount = 0;
        int batchMax = 5000;
        
        String insertStudent = "INSERT INTO Student " +
                    "(banner_id, first_name, last_name, class) VALUES " +
                    "(?, ?, ?, ?);";
        
        PreparedStatement stmt = conn.prepareStatement(insertStudent);;

        for (int bannerID : studentMap.keySet())
        {
            // gather update parameters
            String firstName = studentMap.get(bannerID).getFirstName();
            String lastName = studentMap.get(bannerID).getLastName();
            String classification = studentMap.get(bannerID).getClassification();
            
            // bind arguments
            stmt.setInt(1, bannerID);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, classification);
            
            // add to the batch
            stmt.addBatch();
            
            // if maximum allotted for batch is exceeded, run batch
            batchCount++;
            
            if (batchCount % batchMax == 0)
                totalAffected += stmt.executeBatch().length;
        }
        
        totalAffected += stmt.executeBatch().length;
        stmt.close();
        return totalAffected;
    }

    public static int insertProfessors(HashMap<Integer, Professor> professorMap) throws SQLException
    {
        int totalAffected = 0;
        int batchCount = 0;
        int batchMax = 5000;
        
        String insertProfessor = "INSERT INTO Professor " +
                "(professor_id, first_name, last_name) VALUES " +
                "(?, ?, ?);";
        
        PreparedStatement stmt = conn.prepareStatement(insertProfessor);;
        
        for (int professorID : professorMap.keySet())
        {
            // gather update parameters
            String firstName = professorMap.get(professorID).getFirstName();
            String lastName = professorMap.get(professorID).getLastName();
            
            // bind arguments
            stmt.setInt(1, professorID);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            
            // add to the batch
            stmt.addBatch();
            
            // if maximum allotted for batch is exceeded, run batch
            batchCount++;
            
            if (batchCount % batchMax == 0)
                totalAffected += stmt.executeBatch().length;
        }
        
        totalAffected += stmt.executeBatch().length;
        stmt.close();
        return totalAffected;
    }

    public static int insertSections(HashMap<Integer, Section> sectionMap) throws SQLException
    {
        int totalAffected = 0;
        int batchCount = 0;
        int batchMax = 5000;
        
        String insertSection = "INSERT INTO Section " +
                    "(crn, class_subject, class_number, class_title, class_suffix, time_id, room_id) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement stmt = conn.prepareStatement(insertSection);
        
        for (int crn : sectionMap.keySet())
        {
            // gather update parameters
            String subject = sectionMap.get(crn).getSubject();
            String number = sectionMap.get(crn).getNumber();
            String title = sectionMap.get(crn).getTitle();
            String suffix = sectionMap.get(crn).getSuffix();
            int timeID = sectionMap.get(crn).getTimeID();
            int roomID = sectionMap.get(crn).getRoomID();
            
            // bind arguments
            stmt.setInt(1, crn);
            stmt.setString(2, subject);
            stmt.setString(3, number);
            stmt.setString(4, title);
            stmt.setString(5, suffix);
            stmt.setInt(6, timeID);
            stmt.setInt(7, roomID);
            
            // add to the batch
            stmt.addBatch();
            
            // if maximum allotted for batch is exceeded, run batch
            batchCount++;
            
            if (batchCount % batchMax == 0)
                totalAffected += stmt.executeBatch().length;
        }
        
        totalAffected += stmt.executeBatch().length;
        stmt.close();
        return totalAffected;
    }

    public static int insertEnrolls(HashMap<Integer, Student> studentMap) throws SQLException
    {
        int totalAffected = 0;
        int batchCount = 0;
        int batchMax = 10000; // higher for small queries

        
        String insertEnroll = "INSERT INTO Enroll " +
                "(banner_id, crn) VALUES " +
                "(?, ?)";
        
        PreparedStatement stmt = conn.prepareStatement(insertEnroll);
        
        for (int bannerID : studentMap.keySet())
        {
            HashSet<Integer> crnSet = studentMap.get(bannerID).getSections();

            for (int crn : crnSet)
            {
                stmt.setInt(1, bannerID);
                stmt.setInt(2, crn);
                
                // add to the batch
                stmt.addBatch();
                
                // if maximum allotted for batch is exceeded, run batch
                batchCount++;
                
                if (batchCount % batchMax == 0)
                    totalAffected += stmt.executeBatch().length;
            }
        } // 000474442 - the banner ID of a sample student.
        
        totalAffected += stmt.executeBatch().length;
        stmt.close();
        return totalAffected;
    }

    public static int insertAssigns(HashMap<Integer, Professor> professorMap) throws SQLException
    {
        int totalAffected = 0;
        int batchCount = 0;
        int batchMax = 10000; // higher for small queries
        
        String insertAssign = "INSERT INTO Assign " +
                        "(professor_id, crn) " +
                        "VALUES (?, ?)";
        
        PreparedStatement stmt = conn.prepareStatement(insertAssign);

        for (int professorID : professorMap.keySet())
        {
            HashSet<Integer> crnSet = professorMap.get(professorID).getSections();

            for (int crn : crnSet)
            {
                stmt.setInt(1, professorID);
                stmt.setInt(2, crn);
                
                // add to the batch
                stmt.addBatch();
                
                // if maximum allotted for batch is exceeded, run batch
                batchCount++;
                
                if (batchCount % batchMax == 0)
                    totalAffected += stmt.executeBatch().length;
            }
        }

        totalAffected += stmt.executeBatch().length;
        stmt.close();
        return totalAffected;
    }
}
