package scheduler;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.ca.I;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.HashSet;
import static org.junit.Assert.assertEquals;

/**
 * Created by Isaak on 11/07/16.
 */
public class RoomSteps
{
    static Room room = null;
    static Object result = null;

    // Building Code
    @Given("^there is a room with the building code \"([^\"]*)\"$")
    public void thereIsARoomWithTheBuildingCode(String building_code) throws Throwable
    {
        room = new Room(building_code, "", "");
    }

    @When("^I ask for the room's building code$")
    public void iAskForTheRoomsBuildingCode() throws Throwable
    {
        result = room.getBuildingCode();
    }

    @Then("^I receive the string \"([^\"]*)\" from the room$")
    public void iReceiveTheStringFromTheRoom(String expected) throws Throwable
    {
        assertEquals(expected, result);
    }
	
	// Room Number
    @Given("^there is a room with the room number \"([^\"]*)\"$")
    public void thereIsARoomWithTheRoomNumber(String room_number) throws Throwable
    {
        room = new Room("", room_number, "");
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
    @Given("^there is a room with the max capacity \"([^\"]*)\"$")
    public void thereIsARoomWithTheMaxCapacity(String max_capacity) throws Throwable
    {
        room = new Room("", "", max_capacity);
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
}
