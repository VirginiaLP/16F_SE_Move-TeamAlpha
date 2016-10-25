
import java.util.ArrayList;

/**
 * Parser provides general parsing functionality for other modules.  This module
 * will primarily be used in conjunction with the DataModule, which will handle
 * loading data into the database and recalling data requested by the user.
 *
 * @author Kevin Shurtz
 * @version 1.0
 */
public class Parser
{
    /**
     * Parses a single row from a general file.  This parser does not perfectly
     * adhere to RFC 4180 standards for CSV parsing.  It cannot handle clauses
     * separated across multiple lines in a file.
     *
     * This parse function separates on commas.  A field wrapped in quotes may have commas
     * inside without issue.  As per RFC standards, a quote may be escaped by another quote.
     * For example, "This "" String" would be parsed as 'This " String'.
     *
     * @param line  a line of data to be parsed, separated by commas
     * @return      a String array of tokens parsed from the line
     */
    public static String[] parseRow(String line)
    {
        char[] row = line.toCharArray();                            // the array of characters in the line
        ArrayList<Character> token = new ArrayList<Character>();    // the characters of a single token
        ArrayList<String> result = new ArrayList<String>();         // the final array of tokens
        int startPoint;                                             // the beginning index of a parsable String

        // settings
        boolean delimOn = true;     // whether the delimeter is being recognized
        char delim = ',';           // the character used to separate tokens
        char clause = '"';          // the character used to designate a field, which may include delimeters
        char escape = '"';          // the character used to escape a special character

        // prepare for the first iteration
        startPoint = 0;

        // parse each character in the line
        for (int index = 0; index < row.length; ++index)
        {
            // if we are in a clause, we ignore the delimeter; otherwise, we proceed as usual
            if (!delimOn)
            {
                if (row[index] == escape && (index + 1 < row.length && row[index + 1] == clause))
                {
                    token.add(clause);  // add the escaped clause begin/end character
                    ++index;            // move up two indeces by the end of the iteration
                }
                else if (row[index] == clause && index == row.length - 1)
                {
                    StringBuilder completeToken = new StringBuilder();

                    for (char character : token)
                    {
                        completeToken.append(character);
                    }

                    token.clear();                          // begin a new token
                    result.add(completeToken.toString());   // add the new token
                    startPoint = index + 1;                 // start point for next token

                    delimOn = true;     // end a clause
                }
                else if (row[index] == clause)
                {
                    delimOn = true;     // end a clause
                }
                // handle special characters
                else
                {
                    token.add(row[index]);   // add a character to the token
                }
            }
            else
            {
                // if a clause has been found
                if (row[index] == clause)
                {
                    delimOn = false;    // begin a clause
                }
                else if (row[index] == delim || index == row.length - 1)
                {
                    StringBuilder completeToken = new StringBuilder();

                    for (char character : token)
                    {
                        completeToken.append(character);
                    }

                    token.clear();                          // begin a new token
                    result.add(completeToken.toString());   // add the new token
                    startPoint = index + 1;                 // start point for next token
                }
                else
                {
                    token.add(row[index]);   // add a character to the token
                }
            }
        }

        // return the String array of tokens
        return result.toArray(new String[result.size()]);
    }
    
    /**
     * Parses a single row from a general file with a custom delimeter.  This 
     * parser does not perfectly adhere to RFC 4180 standards for CSV parsing.  
     * It cannot handle clauses separated across multiple lines in a file.
     *
     * This parse function separates on commas.  A field wrapped in quotes may have commas
     * inside without issue.  As per RFC standards, a quote may be escaped by another quote.
     * For example, "This "" String" would be parsed as 'This " String'.
     *
     * @param line      a line of data to be parsed, separated by commas
     * @param delimeter used to separate tokens
     * @return          a String array of tokens parsed from the line
     */
    public static String[] parseRow(String line, char delimeter)
    {
        char[] row = line.toCharArray();                            // the array of characters in the line
        ArrayList<Character> token = new ArrayList<Character>();    // the characters of a single token
        ArrayList<String> result = new ArrayList<String>();         // the final array of tokens
        int startPoint;                                             // the beginning index of a parsable String

        // settings
        boolean delimOn = true;     // whether the delimeter is being recognized
        char delim = delimeter;     // the character used to separate tokens
        char escape = '"';          // the character used to escape a clause character
        char clause = '"';          // the character used to designate a field, which may include delimeters
        
        // prepare for the first iteration
        startPoint = 0;

        // parse each character in the line
        for (int index = 0; index < row.length; ++index)
        {
            // if we are in a clause, we ignore the delimeter; otherwise, we proceed as usual
            if (!delimOn)
            {
                if (row[index] == escape && (index + 1 < row.length && row[index + 1] == clause))
                {
                    token.add(clause);  // add the escaped clause begin/end character
                    ++index;            // move up two indeces by the end of the iteration
                }
                else if (row[index] == clause && index == row.length - 1)
                {
                    StringBuilder completeToken = new StringBuilder();

                    for (char character : token)
                    {
                        completeToken.append(character);
                    }

                    token.clear();                          // begin a new token
                    result.add(completeToken.toString());   // add the new token
                    startPoint = index + 1;                 // start point for next token

                    delimOn = true;     // end a clause
                }
                else if (row[index] == clause)
                {
                    delimOn = true;     // end a clause
                }
                // handle special characters
                else
                {
                    token.add(row[index]);   // add a character to the token
                }
            }
            else
            {
                // if a clause has been found
                if (row[index] == clause)
                {
                    delimOn = false;    // begin a clause
                }
                else if (row[index] == delim || index == row.length - 1)
                {
                    StringBuilder completeToken = new StringBuilder();

                    for (char character : token)
                    {
                        completeToken.append(character);
                    }

                    token.clear();                          // begin a new token
                    result.add(completeToken.toString());   // add the new token
                    startPoint = index + 1;                 // start point for next token
                }
                else
                {
                    token.add(row[index]);   // add a character to the token
                }
            }
        }

        // return the String array of tokens
        return result.toArray(new String[result.size()]);
    }
    
    /**
     * Parses a single row from a general file with a custom delimeter and 
     * escape character.  This parser does not perfectly adhere to RFC 4180 
     * standards for CSV parsing. It cannot handle clauses separated across 
     * multiple lines in a file.
     *
     * This parse function separates on commas.  A field wrapped in quotes may have commas
     * inside without issue.  As per RFC standards, a quote may be escaped by another quote.
     * For example, "This "" String" would be parsed as 'This " String'.
     *
     * @param line      a line of data to be parsed, separated by commas
     * @param delimeter used to separate tokens
     * @param esc       used to prevent the following character from being treated as a clause character 
     * @return          a String array of tokens parsed from the line
     */
    public static String[] parseRow(String line, char delimeter, char esc)
    {
        char[] row = line.toCharArray();                            // the array of characters in the line
        ArrayList<Character> token = new ArrayList<Character>();    // the characters of a single token
        ArrayList<String> result = new ArrayList<String>();         // the final array of tokens
        int startPoint;                                             // the beginning index of a parsable String

        // settings
        boolean delimOn = true;     // whether the delimeter is being recognized
        char delim = delimeter;     // the character used to separate tokens
        char escape = esc;          // the character used to escape a clause character
        char clause = '"';          // the character used to designate a field, which may include delimeters

        // prepare for the first iteration
        startPoint = 0;

        // parse each character in the line
        for (int index = 0; index < row.length; ++index)
        {
            // if we are in a clause, we ignore the delimeter; otherwise, we proceed as usual
            if (!delimOn)
            {
                if (row[index] == escape && (index + 1 < row.length && row[index + 1] == clause))
                {
                    token.add(clause);  // add the escaped clause begin/end character
                    ++index;            // move up two indeces by the end of the iteration
                }
                else if (row[index] == clause && index == row.length - 1)
                {
                    StringBuilder completeToken = new StringBuilder();

                    for (char character : token)
                    {
                        completeToken.append(character);
                    }

                    token.clear();                          // begin a new token
                    result.add(completeToken.toString());   // add the new token
                    startPoint = index + 1;                 // start point for next token

                    delimOn = true;     // end a clause
                }
                else if (row[index] == clause)
                {
                    delimOn = true;     // end a clause
                }
                // handle special characters
                else
                {
                    token.add(row[index]);   // add a character to the token
                }
            }
            else
            {
                // if a clause has been found
                if (row[index] == clause)
                {
                    delimOn = false;    // begin a clause
                }
                else if (row[index] == delim || index == row.length - 1)
                {
                    StringBuilder completeToken = new StringBuilder();

                    for (char character : token)
                    {
                        completeToken.append(character);
                    }

                    token.clear();                          // begin a new token
                    result.add(completeToken.toString());   // add the new token
                    startPoint = index + 1;                 // start point for next token
                }
                else
                {
                    token.add(row[index]);   // add a character to the token
                }
            }
        }

        // return the String array of tokens
        return result.toArray(new String[result.size()]);
    }
    
    /**
     * Parses a single row from a general file with a custom delimeter, escape 
     * character, and clause character.  This parser does not perfectly adhere 
     * to RFC 4180 standards for CSV parsing. It cannot handle clauses separated 
     * across multiple lines in a file.
     *
     * This parse function separates on commas.  A field wrapped in quotes may have commas
     * inside without issue.  As per RFC standards, a quote may be escaped by another quote.
     * For example, "This "" String" would be parsed as 'This " String'.
     *
     * @param line      a line of data to be parsed, separated by commas
     * @param delimeter used to separate tokens
     * @param esc       used to prevent the following character from being treated as a clause character 
     * @param cls       used to enclose a token that contains a delimeter character
     * @return          a String array of tokens parsed from the line
     */
    public static String[] parseRow(String line, char delimeter, char esc, char cls)
    {
        char[] row = line.toCharArray();                            // the array of characters in the line
        ArrayList<Character> token = new ArrayList<Character>();    // the characters of a single token
        ArrayList<String> result = new ArrayList<String>();         // the final array of tokens
        int startPoint;                                             // the beginning index of a parsable String

        // settings
        boolean delimOn = true;     // whether the delimeter is being recognized
        char delim = delimeter;     // the character used to separate tokens
        char escape = esc;          // the character used to escape a clause character
        char clause = cls;          // the character used to designate a field, which may include delimeters
        
        // prepare for the first iteration
        startPoint = 0;

        // parse each character in the line
        for (int index = 0; index < row.length; ++index)
        {
            // if we are in a clause, we ignore the delimeter; otherwise, we proceed as usual
            if (!delimOn)
            {
                if (row[index] == escape && (index + 1 < row.length && row[index + 1] == clause))
                {
                    token.add(clause);  // add the escaped clause begin/end character
                    ++index;            // move up two indeces by the end of the iteration
                }
                else if (row[index] == clause && index == row.length - 1)
                {
                    StringBuilder completeToken = new StringBuilder();

                    for (char character : token)
                    {
                        completeToken.append(character);
                    }

                    token.clear();                          // begin a new token
                    result.add(completeToken.toString());   // add the new token
                    startPoint = index + 1;                 // start point for next token

                    delimOn = true;     // end a clause
                }
                else if (row[index] == clause)
                {
                    delimOn = true;     // end a clause
                }
                // handle special characters
                else
                {
                    token.add(row[index]);   // add a character to the token
                }
            }
            else
            {
                // if a clause has been found
                if (row[index] == clause)
                {
                    delimOn = false;    // begin a clause
                }
                else if (row[index] == delim || index == row.length - 1)
                {
                    StringBuilder completeToken = new StringBuilder();

                    for (char character : token)
                    {
                        completeToken.append(character);
                    }

                    token.clear();                          // begin a new token
                    result.add(completeToken.toString());   // add the new token
                    startPoint = index + 1;                 // start point for next token
                }
                else
                {
                    token.add(row[index]);   // add a character to the token
                }
            }
        }

        // return the String array of tokens
        return result.toArray(new String[result.size()]);
    }
}

