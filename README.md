# Software Engineering Project 2
Our system allows the user to input a section of a class that they 
wish to move to a different time. The program tells them what times 
would be best for the students in the class prioritizing by the length
of time available for the class.  Consequently, the system will propose
bad recommendations before it will offer no recommendations.  

Rooms occupied at a recommended time are excluded. For each time 
recommended, no more than three room pairings are displayed.  

## Starting the System
The system is deployable as a _jar_ file, which can be run from any 
directory on the system (Windows, Mac OS, Linux). Data for the application
is stored in the user's home directory, in a folder entitled 
_.schedule_data_. The _jar_ file is located in the _target_ directory of 
the project folder, and can be kept wherever the user prefers.  

To start the system, navigate to the directory with the _jar_ file.
Then, place the _CSV_ files for student information and room sizes in 
the same directory as the _jar_. The first time the application is run,
all data is loaded into a database. This process will take several minutes,
but need not be repeated in the future. To perform this first operation,
type: `java -jar scheduler-1.0.jar [General Data CSV] [Room Data CSV]`.  

Upon entering the command, you should see the relevant data being 
loaded into the system. The room CSV consists of a header, followed by lines
with the building code, room number, and maximum occupancy.  

Once this completes, you may begin entering commands to query the data. 
For all subsequent uses, you may simply double-click the jar file, or you
may open a console and type: `java -jar scheduler-1.0`. Instructions for
the proper use of the system are below.  

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
program will then output the optimal times for the specified 
class to be moved to.  

`MOVE [CRN]`  

CRN can accept values, such as `10908`, `10001`, and `14812`. Any other 
non-integer values that the user may enter will be ignored.  

**Team Members**
* Kevin Shurtz
* Isaak Ramirez
* Virginia Pettit
