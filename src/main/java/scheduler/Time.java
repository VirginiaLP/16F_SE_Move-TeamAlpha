package scheduler;

/**
 * Created by kevin on 11/12/16.
 */
public class Time
{
    private int timeStart;
    private int timeEnd;
    private int year;
    private Term term;
    private TermLength termLength;

    public Time(int sectionTimeStart, int sectionTimeEnd, int sectionYear, Term cTerm, TermLength cTermLength)
    {
        setTime(sectionTimeStart, sectionTimeEnd, sectionYear, cTerm, cTermLength);
    }

    public void setTime(int sectionTimeStart, int sectionTimeEnd, int sectionYear, Term cTerm, TermLength cTermLength)
    {
        setTimeStart(sectionTimeStart);
        setTimeEnd(sectionTimeEnd);
        setYear(sectionYear);
        setTerm(cTerm);
        setTermLength(cTermLength);
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
}
