package scheduler;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.ca.I;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.HashSet;
import java.util.Objects;

import static org.junit.Assert.assertEquals;



/**
 * Created by Isaak on 11/07/16.
 */
public class RoomSteps
{
    static Room room = null;
    static Object result = null;

    static Room firstRoom = new Room("", "", 0);
    static Room secondRoom = new Room("", "", 0);

    // Building Code
    @Given("^there is a room with the building code \"([^\"]*)\"$")
    public void thereIsARoomWithTheBuildingCode(String buildingCode) throws Throwable
    {
        room = new Room(buildingCode, "", 0);
    }

    @When("^I ask for the room's building code$")
    public void iAskForTheRoomsBuildingCode() throws Throwable
    {
        result = room.getBuildingCode();
    }
	
	// Room Number
    @Given("^there is a room with the room number \"([^\"]*)\"$")
    public void thereIsARoomWithTheRoomNumber(String roomNumber) throws Throwable
    {
        room = new Room("", roomNumber, 0);
    }

    @When("^I ask for the room's room number$")
    public void iAskForTheRoomsRoomNumber() throws Throwable
    {
        result = room.getRoomNumber();
    }

    @Then("^I receive the string \"([^\"]*)\" from the room$")
    public void iReceiveTheStringFromTheRoom(String expected) throws Throwable
    {
        assertEquals(expected, result);
    }
	
	// Max Capacity
    @Given("^there is a room with the max capacity (\\d+)$")
    public void thereIsARoomWithTheMaxCapacity(int maxCapacity) throws Throwable
    {
        room = new Room("", "", maxCapacity);
    }

    @When("^I ask for the room's max capacity$")
    public void iAskForTheRoomsMaxCapacity() throws Throwable
    {
        result = room.getMaxCapacity();
    }

    @Then("^I receive the integer (\\d+) from the room$")
    public void iReceiveTheIntegerFromTheRoom(int expected) throws Throwable
    {
        assertEquals(expected, result);
    }

    // Rooms are equal
    @Given("^the (.*?) room has a building code \"([^\"]*)\"$")
    public void theRoomHasABuildingCode(String which, String code) throws Throwable
    {
        switch (which)
        {
            case "first": firstRoom.setBuildingCode(code);      break;
            case "second": secondRoom.setBuildingCode(code);    break;
        }
    }

    @Given("^the (.*?) room has a room number of \"([^\"]*)\"$")
    public void theSecondRoomHasARoomNumberOf(String which, String number) throws Throwable
    {
        switch (which)
        {
            case "first": firstRoom.setRoomNumber(number);      break;
            case "second": secondRoom.setRoomNumber(number);    break;
        }
    }

    @When("^I ask if the rooms are equal$")
    public void iAskIfTheRoomsAreEqual() throws Throwable {
        result = firstRoom.equals(secondRoom);
    }

    @Then("^I am told that the rooms (.*?) equal$")
    public void iAmToldThatTheRoomsAreNotEqual(String expected) throws Throwable {
        Boolean outcome = null;

        if (expected.equals("are"))
            outcome = true;

        if (expected.equals("are not"))
            outcome = false;

        assertEquals(outcome, (Boolean)result);
    }
}
