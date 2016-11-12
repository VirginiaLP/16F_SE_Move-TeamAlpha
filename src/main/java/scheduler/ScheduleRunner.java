package scheduler;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * Created by kevin on 11/12/16.
 */
public class ScheduleRunner
{
    public static void main(String... args)
    {
        try
        {
            DataLoad.loadGeneral("data_load_test_1.csv");
            DataManager.updateDatabase();
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e);
        }
        catch (SQLException e)
        {
            System.err.println(e);
        }
    }
}
