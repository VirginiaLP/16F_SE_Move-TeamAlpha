# Software Engineering Project 2
Our system allows the user to input a section of a class that they 
wish to move to a different time. The program tells them what times 
would be best for the students in the class prioritizing by classification.  

## Starting the System
To start the system, enter `java Run`.  Upon entering the command, you 
should see the relevant student, professor, section, etc. data being 
loaded into the system. At this point, you can begin entering specific 
commands for moving sections to different times.  

### Help
If the syntax or list of commands in unclear, after starting the system, 
type `HELP` to see more information.  

### Info
If you want to see information about the creators of this program and the current version, 
type `INFO` to see more information.  

### Case Sensitivity
The system was designed to be case insensitive.  Any keywords entered 
are treated as uppercase characters, irrespective of the actual case of 
the user's input.  Therefore, `HELP` is read the same as `help`.  

## Moving the class
The user can determine which class they want to move by using the 
`MOVE` command, followed by the CRN of the specific course. Our 
program will then output the most optimal times for the specified 
class to be moved to.

`MOVE [CRN]`

CRN can accept values, such as `10908`, `10001`, and `14812`. Any other 
non-integer values that the user may enter will be ignored.  
