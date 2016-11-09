package scheduler;

import java.util.Objects;

/**
 * Created by kevin on 11/8/16.
 */
public class Room
{
    private String buildingCode;
    private String roomNumber;
    private int maxCapacity;

    public Room(String building, String room, int max)
    {
        setBuilding(building, room, max);
    }

    public Room(String building, String room)
    {
        setBuilding(building, room, 0);
    }

    public void setBuilding(String building, String room, int max)
    {
        setBuildingCode(building);
        setRoomNumber(room);
        setMaxCapacity(max);
    }

    public String getBuildingCode()
    {
        return buildingCode;
    }

    public String getRoomNumber()
    {
        return roomNumber;
    }

    public int getMaxCapacity()
    {

        return maxCapacity;
    }

    public void setBuildingCode(String building)
    {
        buildingCode = building;
    }

    public void setRoomNumber(String room)
    {
        roomNumber = room;
    }

    public void setMaxCapacity(int max)
    {
        maxCapacity = max;
    }

    @Override
    public boolean equals(Object other)
    {
        Room otherRoom = (Room)other;

        if (!otherRoom.getBuildingCode().equals(getBuildingCode()))
            return false;

        if (!otherRoom.getRoomNumber().equals(getRoomNumber()))
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getBuildingCode(), getRoomNumber());
    }
}
