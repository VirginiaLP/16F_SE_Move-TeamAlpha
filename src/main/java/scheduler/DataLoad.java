package scheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by kevin on 11/8/16.
 */
public class DataLoad
{
    private static final Logger LOGGER = Logger.getLogger(DataLoad.class.getPackage().getName());

    public static HashMap<Integer, Student> students =
            new HashMap<Integer, Student>();
    public static HashMap<Integer, Professor> professors =
            new HashMap<Integer, Professor>();
    public static HashMap<Integer, Section> sections =
            new HashMap<Integer, Section>();
    public static HashMap<Integer, Room> rooms =
            new HashMap<Integer, Room>();
    public static HashMap<Integer, Time> times =
            new HashMap<Integer, Time>();

    static Integer timeID = 0;
    static Integer roomID = 0;

    public static void loadGeneral(Path csvPath) throws IOException
    {
        // open CSV file
        Scanner scan = new Scanner(csvPath);

        // discard first line of headers
        scan.nextLine();

        while (scan.hasNextLine())
        {
            // create potential student, professor, section, and room objects
            Integer bannerID = null;
            Student student = null;

            Integer professorID = null;
            Professor professor = null;

            Integer crn = null;
            Section section = null;

            Integer roomIdNumber = null;
            Room room = null;

            Integer timeIdNumber = null;
            Time time = null;

            // get string tokens from CSV row
            String[] tokens = Parser.parseRow(scan.nextLine());

            // create time
            try
            {
                Integer year = Integer.parseInt(tokens[1].substring(0, 4));
                Integer termCode = Integer.parseInt(tokens[1].substring(4, 6));
                String term = null;

                switch (termCode)
                {
                    case 10: term = "FALL";   break;
                    case 20: term = "SPRING"; break;
                    case 30: term = "SUMMER"; break;
                }

                String termLength = tokens[3].toUpperCase().replace(' ', '_');
                Integer startTime = Integer.parseInt(tokens[66]);
                Integer endTime = Integer.parseInt(tokens[67]);

                String days = "";

                for (int dayIndex = 72; dayIndex < 79; ++dayIndex)
                    days += tokens[dayIndex];

                time = makeTime(startTime, endTime, year, term, termLength, days);
                timeIdNumber = generateTimeID(time);
            }
            catch (RuntimeException e)
            {
                timeIdNumber = null;
                time = null;
            }

            // create room
            try
            {
                room = makeRoom(tokens[68], tokens[70]);
                roomIdNumber = generateRoomID(room);
            }
            catch (RuntimeException e)
            {
                roomIdNumber = null;
                room = null;
            }

            // create student
            try
            {
                bannerID = Integer.parseInt(tokens[56]);
                student = makeStudent(tokens[57], tokens[58], tokens[33]);
            }
            catch (RuntimeException e) {
                bannerID = null;
                student = null;
            }

            // create professor
            try
            {
                professorID = Integer.parseInt(tokens[49]);
                String[] profName = tokens[50].split("\\s*,\\s*");
                professor = makeProfessor(profName[1], profName[0]);
            }
            catch (RuntimeException e)
            {
                professorID = null;
                professor = null;
            }

            // create section
            try
            {
                crn = Integer.parseInt(tokens[35]);

                // include time and room IDs
                section = makeSection(tokens[40], tokens[42], tokens[44], tokens[43], timeIdNumber, roomIdNumber);
            }
            catch (RuntimeException e)
            {
                crn = null;
                section = null;
            }

            if (!students.containsKey(bannerID) && bannerID != null && student != null)
                students.put(bannerID, student);

            if (!professors.containsKey(professorID) && professorID != null && professor != null)
                professors.put(professorID, professor);

            if (!sections.containsKey(crn) && crn != null && section != null)
                sections.put(crn, section);

            if (crn != null)
            {
                if (student != null)
                    students.get(bannerID).addSection(crn);

                if (professor != null)
                    professors.get(professorID).addSection(crn);
            }
        }
    }

    public static void loadRooms(Path csvPath) throws IOException
    {
        Scanner scan = new Scanner(csvPath);

        while (scan.hasNextLine())
        {
            // create string tokens from each line
            String[] tokens = Parser.parseRow(scan.nextLine());

            // We need the CSV to know how this will work.
            // Room room = new Room(tokens values)
            // int maxCapacity = tokens value

            // ... in the meantime
            Room room = new Room("temporary", "to be replaced", 0);
            int maxCapacity = -3;       // to be taken from tokens at some point

            // find all existing rooms
            for (Room existingRoom : rooms.values())
            {
                // set the maximum capacities for all existing rooms
                if (existingRoom.equals(room))
                    existingRoom.setMaxCapacity(maxCapacity);
            }
        }
    }

    private static Integer generateTimeID(Time time)
    {
        // check that time is not null
        if (time == null)
        {
            LOGGER.finer("Time ID not generated - Time was null: " +
                    time.getTimeStart() + " " + time.getTimeEnd() + " " + time.getYear() + " " + time.getTerm() + " " +
                    time.getTermLength() + " " + time.getDays());
            return null;
        }

        // check to see if a time's identifier has already been generated
        if (times.values().contains(time))
        {
            for (int key = 1; key <= timeID; ++key)
            {
                if (times.get(key).equals(time))
                {
                    LOGGER.finer("Time ID not generated - Time was found: " +
                            time.getTimeStart() + " " + time.getTimeEnd() + " " + time.getYear() + " " + time.getTerm() + " " +
                            time.getTermLength() + " " + time.getDays());
                    return key;
                }
            }
        }

        // increment the roomID; the first ID will be 1
        timeID++;

        // otherwise, create a new room and room ID
        LOGGER.finer("Time ID generated - Time did not yet exist: " +
                time.getTimeStart() + " " + time.getTimeEnd() + " " + time.getYear() + " " + time.getTerm() + " " +
                time.getTermLength() + " " + time.getDays() + " (" + timeID + ")");
        times.put(timeID, time);
        return timeID;
    }

    private static Integer generateRoomID(Room room)
    {
        // check that room is not null
        if (room == null)
        {
            LOGGER.finer("Room ID not generated - Room was null: " +
                    room.getBuildingCode() + " " + room.getMaxCapacity());
            return null;
        }

        // check to see if a room's identifier has already been generated
        if (rooms.values().contains(room))
        {
            for (int key = 1; key <= roomID; ++key)
            {
                if (rooms.get(key).equals(room))
                {
                    LOGGER.finer("Room ID not generated - Room was found: " +
                            room.getBuildingCode() + " " + room.getMaxCapacity());
                    return key;
                }
            }
        }

        // increment the roomID; the first ID will be 1
        roomID++;

        // otherwise, create a new room and room ID
        LOGGER.finer("Room ID generated - Room did not yet exist: " +
                room.getBuildingCode() + " " + room.getMaxCapacity() + " (" + roomID + ")");
        rooms.put(roomID, room);
        return roomID;
    }

    private static Student makeStudent(String firstName, String lastName, String classification)
    {
        if (firstName == null || firstName.equals("")
                || lastName == null || lastName.equals("")
                || classification == null || classification.equals(""))
            return null;

        LOGGER.finest("A Student was made: " + firstName + " " + lastName + " " + classification);
        return new Student(firstName, lastName, classification);
    }

    private static Professor makeProfessor(String firstName, String lastName)
    {
        if (firstName == null || firstName.equals("")
                || lastName == null || lastName.equals(""))
            return null;

        LOGGER.finest("A Professor was made: " + firstName + " " + lastName);
        return new Professor(firstName, lastName);
    }

    private static Section makeSection(String sub, String num, String tit, String suf, Integer time, Integer room)
    {
        // insure fields are filled
        if (sub == null || sub.equals("")
                || num == null || num.equals("")
                || tit == null || tit.equals("")
                || suf == null || suf.equals("")
                || time == null
                || room == null)
            return null;

        LOGGER.finest("A Section was made: " + sub + " " + num + " " + tit + " " + suf + " " + time + " " + room);
        return new Section(sub, num, tit, suf, time, room);
    }

    private static Room makeRoom(String building, String number)
    {
        if (building == null || building == "" || number == null || number == "")
            return null;

        LOGGER.finest("A Room was made: " + building + " " + number);
        return new Room(building, number);
    }

    private static Time makeTime(Integer timeStart, Integer timeEnd, Integer year, String cTerm, String cTermLength, String days)
    {
        // insure terms are not null
        if (cTerm == null || cTerm.equals("") || cTermLength == null || cTermLength.equals(""))
            return null;

        // prepare to convert strings to enums
        Term sectionTerm = null;
        TermLength sectionTermLength = null;

        // match course term string to proper term
        for (Term term : Term.values())
        {
            if (cTerm.toUpperCase().equals(term.name().toUpperCase()))
                sectionTerm = term;
        }

        // match course term length ot proper term length
        for (TermLength termLength : TermLength.values())
        {
            if (cTermLength.toUpperCase().equals(termLength.name().toUpperCase()))
                sectionTermLength = termLength;
        }

        if (timeStart == null || timeStart.equals("")
                || timeEnd == null || timeEnd.equals("")
                || year == null || year.equals("")
                || sectionTerm == null
                || sectionTermLength == null
                || days == null || days.equals(""))
            return null;

        LOGGER.finest("A Time was made: " + timeStart + " " + timeEnd +
                " " + year + " " + cTerm + " " + cTermLength + " " + days);
        return new Time(timeStart, timeEnd, year, sectionTerm, sectionTermLength, days);
    }
}
