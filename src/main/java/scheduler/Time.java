package scheduler;

/**
 * Created by kevin on 11/12/16.
 * Modified by Isaak on 11/12/16.
 */
public class Time
{
    private int timeStart;
    private int timeEnd;
    private int year;
    private Term term;
    private TermLength termLength;
	private String days;

    public Time(int sectionTimeStart, int sectionTimeEnd, int sectionYear, Term cTerm, TermLength cTermLength, String tDays)
    {
        setTime(sectionTimeStart, sectionTimeEnd, sectionYear, cTerm, cTermLength, tDays);
    }

    public void setTime(int sectionTimeStart, int sectionTimeEnd, int sectionYear, Term cTerm, TermLength cTermLength, String tDays)
    {
        setTimeStart(sectionTimeStart);
        setTimeEnd(sectionTimeEnd);
        setYear(sectionYear);
        setTerm(cTerm);
        setTermLength(cTermLength);
		setDays(tDays);
    }

    public void setTimeStart(int sectionTimeStart)
    {
        timeStart = sectionTimeStart;
    }

    public void setTimeEnd(int sectionTimeEnd)
    {
        timeEnd = sectionTimeEnd;
    }

    public void setYear(int sectionYear)
    {
        year = sectionYear;
    }

    public void setTerm(Term cTerm)
    {
        term = cTerm;
    }

    public void setTermLength(TermLength cTermLength)
    {
        termLength = cTermLength;
    }
	
	public void setDays(String sDays)
	{
		days = sDays;
	}

    public int getTimeStart()
    {
        return timeStart;
    }

    public int getTimeEnd()
    {
        return timeEnd;
    }

    public int getYear()
    {
        return year;
    }

    public Term getTerm()
    {
        return term;
    }

    public TermLength getTermLength()
    {
        return termLength;
    }
	
	public String getDays()
	{
		return days;
	}
}
