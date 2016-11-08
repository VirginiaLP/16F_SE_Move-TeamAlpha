package scheduler;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.Objects;

class Room
{
    private String buildingCode;
    private String roomNumber;

    public Room(String building, String room)
    {
        buildingCode = building;
        roomNumber = room;
    }

    public String getBuildingCode() {
        return this.buildingCode;
    }

    public String getRoomNumber() {
        return this.roomNumber;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public boolean equals(Object other)
    {
        Room otherRoom = (Room)other;

        if (!otherRoom.getBuildingCode().equals(buildingCode))
            return false;

        if (!otherRoom.getRoomNumber().equals(roomNumber))
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(buildingCode, roomNumber);
    }
}

class DataModule
{
    private static HashMap<Integer, Student> students = null;
    private static HashMap<Integer, Professor> professors = null;
    private static HashMap<Integer, Section> sections = null;
    private static HashMap<Integer, Room> rooms = null;
    static Integer roomID = 0;

    private static final String CONNECTION_URL =
            "jdbc:sqlite:data/schedule_data.db";

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    public static void update() throws SQLException
    {
        try
        {
            // establish database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // load row's data into database
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);


        }
        catch (SQLException e)
        {
            System.err.println(e);
        }
        finally
        {
            if (rs != null)
                rs.close();

            if (stmt != null)
                stmt.close();

            if (conn != null)
                conn.close();
        }
    }

    public static void load(String csvPath) throws FileNotFoundException
    {
        // open CSV file
        ClassLoader classLoader = DataModule.class.getClassLoader();
        File file = new File(classLoader.getResource(csvPath).getFile());
        Scanner scan = new Scanner(file);

        while (scan.hasNextLine())
        {
            // create potential student, professor, and section objects
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
                String[] profName = tokens[50].split(",");
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

                Integer timeFrame = Integer.parseInt(tokens[2]);
                Integer year = Integer.parseInt(tokens[2].substring(0, 4));
                Integer termCode = Integer.parseInt(tokens[2].substring(4, 6));
                String term = null;

                switch (termCode)
                {
                    case 1: term = "Fall";   break;
                    case 2: term = "Spring"; break;
                    case 3: term = "Summer"; break;
                }

                String termLength = tokens[4].toUpperCase().replace(' ', '_');

                Integer startTime = Integer.parseInt(tokens[66]);
                Integer endTime = Integer.parseInt(tokens[67]);


                section = makeSection(term, termLength, tokens[40], tokens[42], tokens[44], tokens[43],
                        year, startTime, endTime);
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
                    student.addSection(crn);

                if (professor != null)
                    professor.addSection(crn);
            }
        }
    }

    public static void insertStudent(Student student)
    {

    }

    public static void insertProfessor(Professor professor)
    {

    }

    public static void insertSection(Section section)
    {

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
                            String num, String tit, String suf, Integer year, Integer start, Integer end, Integer building)
    {
        Term sectionTerm = null;
        TermLength sectionTermLength = null;

        for (Term term : Term.values())
        {
            if (cTerm.equals(term.name()))
                sectionTerm = term;
        }

        for (TermLength termLength : TermLength.values())
        {
            if (cTermLength.equals(termLength.name()))
                sectionTermLength = termLength;
        }

        if (sectionTerm == null
                || sectionTermLength == null
                || sub == null || sub.equals("")
                || num == null || num.equals("")
                || tit == null || tit.equals("")
                || suf == null || suf.equals("")
                || year == null
                || start == null
                || end == null)
                || building == null)
            return null;

        return new Section(sectionTerm, sectionTermLength, sub, num, tit, suf, year, start, end);
    }
}

public class DataModuleSteps
{
    static String fileName;

    @Given("^the file \"([^\"]*)\" to load$")
    public void theFileToLoad(String file) throws Throwable
    {
        fileName = file;
    }

    @When("^I load the file into the data module$")
    public void iLoadTheFileIntoTheDataModule() throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I request the student with the banner ID (\\d+)$")
    public void iRequestTheStudentWithTheBannerID(int arg1) throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I request the professor with the professor ID (\\d+)$")
    public void iRequestTheProfessorWithTheProfessorID(int professor) throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I request the section with the CRN (\\d+)$")
    public void iRequestTheSectionWithTheCRN(int crn) throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I receive the string (.*?) from the data module$")
    public void iReceiveTheString(String expected) throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}