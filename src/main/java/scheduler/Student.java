package scheduler;

import java.util.HashSet;

/**
 * Created by kevin on 10/27/16.
 */
public class Student
{
    String firstName;
    String lastName;
    String classification;
    HashSet<Integer> sections;

    public Student(String first, String last, String classCode)
    {
        setStudent(first, last, classCode);
        sections = new HashSet<Integer>();
    }

    public void setStudent(String first, String last, String classCode)
    {
        setFirstName(first);
        setLastName(last);
        setClassification(classCode);
    }

    public void setFirstName(String first)
    {
        firstName = first;
    }

    public void setLastName(String last)
    {
        lastName = last;
    }

    public void setClassification(String classCode)
    {
        classification = classCode;
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

    public String getClassification()
    {
        return classification;
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

