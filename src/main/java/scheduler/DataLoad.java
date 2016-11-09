package scheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by kevin on 11/8/16.
 */
public class DataLoad
{
    public static HashMap<Integer, Student> students =
            new HashMap<Integer, Student>();
    public static HashMap<Integer, Professor> professors =
            new HashMap<Integer, Professor>();
    public static HashMap<Integer, Section> sections =
            new HashMap<Integer, Section>();
    public static HashMap<Integer, Room> rooms =
            new HashMap<Integer, Room>();

    static Integer roomID = 0;

    public static void loadGeneral(String csvPath) throws FileNotFoundException
    {
        // open CSV file
        ClassLoader classLoader = DataLoad.class.getClassLoader();
        File file = new File(classLoader.getResource(csvPath).getFile());
        Scanner scan = new Scanner(file);

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

            // get string tokens from CSV row
            String[] tokens = Parser.parseRow(scan.nextLine());

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

                section = makeSection(term, termLength, tokens[40], tokens[42], tokens[44], tokens[43],
                        year, startTime, endTime, roomIdNumber);
            }
            catch (RuntimeException e)
            {
                System.err.println(e);
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
                    student.addSection(crn);

                if (professor != null)
                    professor.addSection(crn);
            }
        }
    }

    public static void loadRooms(String csvPath) throws FileNotFoundException
    {
        Scanner scan = new Scanner(new File(csvPath));
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

    private static Integer generateRoomID(Room room)
    {
        // check that room is not null
        if (room == null)
            return null;

        // check to see if a room's identifier has already been generated
        if (rooms.values().contains(room))
        {
            for (int key = 1; key < roomID; ++key)
            {
                if (rooms.get(key).equals(room))
                    return key;
            }
        }

        // increment the roomID; the first ID will be 1
        roomID++;

        // otherwise, create a new room and room ID
        rooms.put(roomID, room);
        return roomID;
    }

    private static Student makeStudent(String firstName, String lastName, String classification)
    {
        if (firstName == null || firstName.equals("")
                || lastName == null || lastName.equals("")
                || classification == null || classification.equals(""))
            return null;

        return new Student(firstName, lastName, classification);
    }


    private static Professor makeProfessor(String firstName, String lastName)
    {
        if (firstName == null || firstName.equals("")
                || lastName == null || lastName.equals(""))
            return null;

        return new Professor(firstName, lastName);
    }

    private static Section makeSection(String cTerm, String cTermLength, String sub,
                                       String num, String tit, String suf, Integer year, Integer start, Integer end, Integer room)
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

        // insure fields are filled
        if (sub == null || sub.equals("")
                || num == null || num.equals("")
                || tit == null || tit.equals("")
                || suf == null || suf.equals("")
                || year == null
                || start == null
                || end == null
                || room == null)
            return null;

        return new Section(sectionTerm, sectionTermLength, sub, num, tit, suf, year, start, end, room);
    }

    private static Room makeRoom(String building, String room)
    {
        if (building == null || building == "" || room == null || room == "")
            return null;

        return new Room(building, room);
    }
}

