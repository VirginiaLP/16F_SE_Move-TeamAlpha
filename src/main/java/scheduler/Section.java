package scheduler;

/**
 * Created by kevin on 10/29/16.
 */
public class Section
{
    private Term term;
    private TermLength termLength;
    private String subject;
    private String number;
    private String title;
    private String suffix;
    private int year;
    private int timeStart;
    private int timeEnd;
    private int buildingID;


    public Section(Term cTerm, TermLength length, String sub, String num, String tit, String suf, int year, int start, int end, int building)
    {
        setSection(cTerm, length, sub, num, tit, suf, year, start, end, building);
    }

    public void setSection(Term cTerm, TermLength length, String sub, String num, String tit, String suf, int year, int start, int end, int building)
    {
        setTerm(cTerm);
        setTermLength(length);
        setSubject(sub);
        setNumber(num);
        setTitle(tit);
        setSuffix(suf);
        setYear(year);
        setTimeStart(start);
        setTimeEnd(end);
        setBuildingID(building);
    }

    public void setTerm(Term cTerm)
    {
        term = cTerm;
    }

    public void setTermLength(TermLength length)
    {
        termLength = length;
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

    public void setYear(int yr)
    {
        year = yr;
    }

    public void setTimeStart(int start)
    {
        timeStart = start;
    }

    public void setTimeEnd(int end)
    {
        timeEnd = end;
    }

    public void setBuildingID(int building)
    {
        buildingID = building;
    }

    public Term getTerm()
    {
        return term;
    }

    public TermLength getTermLength()
    {
        return termLength;
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

    public int getYear()
    {
        return year;
    }

    public int getTimeStart()
    {
        return timeStart;
    }

    public int getTimeEnd()
    {
        return timeEnd;
    }

    public int getBuildingID()
    {
        return buildingID;
    }
}
