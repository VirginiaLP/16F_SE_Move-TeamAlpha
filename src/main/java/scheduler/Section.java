package scheduler;

/**
 * Created by kevin on 10/29/16.
 */
public class Section
{
    private String subject;
    private String number;
    private String title;
    private String suffix;
    private int timeID;
    private int roomID;


    public Section(String sub, String num, String tit, String suf, int time, int room)
    {
        setSection(sub, num, tit, suf, time, room);
    }

    public void setSection(String sub, String num, String tit, String suf, int time, int room)
    {
        setSubject(sub);
        setNumber(num);
        setTitle(tit);
        setSuffix(suf);
        setTimeID(time);
        setRoomID(room);
    }

    public void setSubject(String sub)
    {
        subject = sub;
    }

    public void setNumber(String num)
    {
        number = num;
    }

    public void setTitle(String tit)
    {
        title = tit;
    }

    public void setSuffix(String suf)
    {
        suffix = suf;
    }

    public void setTimeID(int time)
    {
        timeID = time;
    }

    public void setRoomID(int building)
    {
        roomID = building;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getNumber()
    {
        return number;
    }

    public String getTitle()
    {
        return title;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public int getTimeID()
    {
        return timeID;
    }

    public int getRoomID()
    {
        return roomID;
    }
}
