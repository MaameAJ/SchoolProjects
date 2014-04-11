*********************************************
CIS2430: Object-Oriented Programming
Maame Apenteng (0802637)
NOV/11/2013
*********************************************

*********************************************
PROGRAM OBJECTIVE:
*********************************************
A program which allows the user to enter and 
search for library information.
*********************************************

*********************************************
LIMITATIONS AND ASSUMPTIONS
*********************************************
The search function ignores the following
punctuation: !,;:?." 
All other punctuation found in a search key
must also exist in a title to return a result.
Also output file must exist in order for
program to run.
If an item is successfully edited, the 
JComboBox is not properly refreshed until you
go to the commands and click edit again. So
any subsequent attempts to edit that particular
reference will cause an error to be output to
the message field.
*********************************************

*********************************************
USER GUIDE
*********************************************
To run the program in the command line:
    the jar - java -jar 'mapenten_A4.jar'
    the program - java LibrarySearchInterface
There is no test program written for this
program so simply enter test input in order
to test the program. The Book, Journal and
Reference Classes can all be tested by running
the individual files. 
*********************************************

*********************************************
BONUS MODIFICATIONS
*********************************************
Upon opening of the program, it prompts for
the input and output file using a File Dialog.
User must select an input and output file at
this time.
Program implements a save and save and quit 
option. Program also checks to make if user 
exits without explicitly saving that that is 
what the user wants. Program also allows users 
to edit references.
*********************************************

*********************************************
TEST PLAN
*********************************************
=============================================
FILE INPUT
=============================================
Program Opens
---------------------------------------------
Open program opening, it prompts for input
file via an Open dialog and then output file
via a Save dialog. If user presses cancel, the
dialogs simply reopen. Not allowing the user
to proceed until both files have been 
successfully selected
---------------------------------------------
---------------------------------------------
Input File does not exist
---------------------------------------------
EXPECTED RESULTS: Program informs the user and
prompts again for input file (and output file)
---------------------------------------------
---------------------------------------------
Output File does not exist
---------------------------------------------
EXPECTED RESULTS: Program creates the file 
and proceeds as normal
---------------------------------------------
---------------------------------------------
Input File is empty
---------------------------------------------
EXPECTED RESULTS: Program proceeds as normal
but library is empty
---------------------------------------------
=============================================
MAIN PROGRAM
=============================================
User clicks on add from the Commands menu
---------------------------------------------
EXPECTED RESULTS: Program proceeds to add 
screen
---------------------------------------------
---------------------------------------------
User clicks on search from the Commands menu
---------------------------------------------
EXPECTED RESULTS: Program proceeds to search 
screen
---------------------------------------------
---------------------------------------------
User clicks quit or the exit button
---------------------------------------------
EXPECTED RESULTS: A message pops up asking the
user if they are sure they want to quit without
saving - if the user selects yes, the program
exits, if the user selects save and quit, the 
program informs the user that the library has
been saved and exits and if the user selects
cancel, the dialog disappears and program 
continues running
---------------------------------------------
---------------------------------------------
User clicks on the RESET button
---------------------------------------------
All fields are cleared.
---------------------------------------------
---------------------------------------------
User clicks on Add/Search button
---------------------------------------------
There is an attempt to add a reference or
search the library respectively.
---------------------------------------------
=============================================
ADD METHOD
=============================================
User selects book on the JChooseBox
---------------------------------------------
The add screen changes to allow for the 
inputting of book information
---------------------------------------------
User selects journal on the JChooseBox
---------------------------------------------
The add screen changes to allow for the 
inputting of journal information
---------------------------------------------
---------------------------------------------
Book with a unique call number and year
---------------------------------------------
EXPECTED RESULT: Book was added. A message 
appears in the messages text area says that 
addition was a success.
---------------------------------------------
---------------------------------------------
Book with a duplicate call number and year
---------------------------------------------
EXPECTED RESULT: A message appears in the
message text area stating the book was not 
added because item already exists.
---------------------------------------------
---------------------------------------------
Book with without a title, year and/or call
number
---------------------------------------------
EXPECTED RESULT: Book is not added. A message 
appears in the message text area explaining 
that missing field is invalid and that book 
was not added.
---------------------------------------------
---------------------------------------------
The library is empty
---------------------------------------------
EXPECTED RESULTS: The reference is 
successfully added and a message appears in 
the message text area stating so
---------------------------------------------
---------------------------------------------
Journal with a unique call number and year
---------------------------------------------
EXPECTED RESULT: Journal is added. A message 
appears in the messages text area says that 
addition was a success.
---------------------------------------------
---------------------------------------------
Journal with a duplicate call number and year
---------------------------------------------
EXPECTED RESULT: A message appears in the 
messages text area stating the journal was not 
added because item already exists.
---------------------------------------------
---------------------------------------------
Journal with a duplicate call number but not 
year
---------------------------------------------
EXPECTED RESULT: Journal is added. Program 
prints that addition was a success.
---------------------------------------------
---------------------------------------------
Journal with without a title, year and/or call
number
---------------------------------------------
EXPECTED RESULT: Journal is not added. Program 
outputs message explaining that journal is 
missing field invalid and that journal was not
added.
----------------------------------------------
==============================================
SEARCH METHOD
==============================================
USING ONLY THE YEAR FIELD:
---------------------------------------------
.............................................
Searching for items that were published 
from some year and after
.............................................
EXPECTED RESULT: Program prints out all books 
and journals that were published from that
year onwards.
.............................................
Searching for items that were published from
some year and before
.............................................
EXPECTED RESULT: Program prints out all books 
and journals that were published up to and
including that year.
.............................................
Searching for items that were published in a
range of years - from some beginning year to
some end year
.............................................
EXPECTED RESULT: Program prints out all books 
and journals that were published between 
beginning year and ending year inclusive.
.............................................
No search year is specified
.............................................
EXPECTED RESULT: Program prints out all the
books and journals contained in the library.
---------------------------------------------
---------------------------------------------
USING ONLY THE CALL NUMBER FIELD
---------------------------------------------
.............................................
Searching a query with multiple matches
.............................................
EXPECTED RESULT: Program prints out all books 
and journals that match the call number.
.............................................
Searching a query with a single result
.............................................
EXPECTED RESULT: Program prints out only the 
item that contains that call number
.............................................
Searching a query with no results
.............................................
EXPECTED RESULT: Program prints out that 0 
matches were found.
.............................................
No call number is specified
.............................................
EXPECTED RESULT: Program prints out all the
books and journals contained in the library.
---------------------------------------------
---------------------------------------------
USING ONLY THE ADDITONAL KEYWORD FIELD
[searches title, publisher/organization and/or
authors]
---------------------------------------------
.............................................
Searching a single word query with multiple 
matches
.............................................
EXPECTED RESULT: Program prints out all books 
and journals that match the query
.............................................
Searching a single word query with a single 
result
.............................................
EXPECTED RESULT: Program prints out only the 
item that matches that query
.............................................
Searching a query with no results
.............................................
EXPECTED RESULT: Program prints out that 0 
matches were found.
.............................................
Searching a multi-word query with multiple 
matches
.............................................
EXPECTED RESULT: Program prints out all books 
and journals that match the query
.............................................
Searching a multi-word query with a single 
result
.............................................
EXPECTED RESULT: Program prints out only the 
item that matches that query
.............................................
Searching a multi-word query with no results
.............................................
EXPECTED RESULT: Program prints out that 0 
matches were found.
.............................................
Partial match to multi-word query
.............................................
EXPECTED RESULT: Program prints out that 0 
matches were found.
.............................................
.............................................
No keywords are specified
.............................................
EXPECTED RESULT: Program prints out all the
books and journals contained in the library.
---------------------------------------------
---------------------------------------------
Multi-field query matches exactly one result
---------------------------------------------
EXPECTED RESULT: Program prints out only the 
item that matches that query
---------------------------------------------
---------------------------------------------
Multi-field query matches multiple results
---------------------------------------------
EXPECTED RESULT: Program prints out all books 
and journals that match the query
---------------------------------------------
---------------------------------------------
Multi-word query matches no results
---------------------------------------------
EXPECTED RESULT: Program prints out that 0 
matches were found.
---------------------------------------------
---------------------------------------------
Partial match to multi-field query
---------------------------------------------
EXPECTED RESULT: Program prints out that 0 
matches were found.
---------------------------------------------
---------------------------------------------
No fields are specified
---------------------------------------------
EXPECTED RESULT: Program prints out all the
books and journals contained in the library.
---------------------------------------------
---------------------------------------------
Search query is executed before the library
contains any items
---------------------------------------------
EXPECTED RESULT: A message stating no results
were found is output to the screen.
---------------------------------------------
=============================================
EDIT METHOD
=============================================
User selects an reference from the JComboBox
---------------------------------------------
The edit form fills with that reference's
information
---------------------------------------------
---------------------------------------------
User edits a field and presses the edit
button
---------------------------------------------
EXPECTED RESULT: Item is successfully edited 
and a message is output to the message field 
stating as much.
---------------------------------------------
User makes call number, title or year field
blank
---------------------------------------------
EXPECTED RESULT: An error message is output
to the message field, informing that the 
respective field is invalid.
---------------------------------------------
---------------------------------------------
User makes no changes and hits the edit
button
---------------------------------------------
EXPECTED RESULT: A message is output to the
messages field informing the user that no
edit was made because no changes have been
made to any of the fields.
---------------------------------------------
*********************************************
*********************************************
POSSIBLE IMPROVEMENTS
*********************************************
I would allow users to delete them.
*********************************************