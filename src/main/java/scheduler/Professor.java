package scheduler;
import java.util.HashSet;

/**
 * Created by kevin on 10/27/16.
 */
public class Professor
{
    String firstName;
    String lastName;
    HashSet<Integer> sections;

    public Professor(String first, String last)
    {
        setProfessor(first, last);
        sections = new HashSet<Integer>();
    }

    public void setProfessor(String first, String last)
    {
        setFirstName(first);
        setLastName(last);
    }

    public void setFirstName(String first)
    {
        firstName = first;
    }

    public void setLastName(String last)
    {
        lastName = last;
    }

    public void addSection(Integer crn)
    {
        sections.add(new Integer(crn));
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public HashSet<Integer> getSections()
    {
        // deep copy Integers
        HashSet<Integer> copy = new HashSet<Integer>();

        for (Integer value : sections)
            copy.add(new Integer(value));

        return copy;
    }
}