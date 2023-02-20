import java.io.*;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class MenuRecodsTODO {

private Record record;
private RandomAccessFile file;
private double balance;
private Scanner kbd = new Scanner(System.in);

public MenuRecodsTODO() {
	record = new Record();
   try 
   {
      file = new RandomAccessFile( "rand.dat", "rw" );    
   } 
   catch( IOException e ) 
   {
      System.err.println( "File not opened properly\n" + e.toString() );
      System.exit( 1 ); }
   }
public void menu()
{
System.out.printf("%nChoose an Option%n");
System.out.printf("0 - Display All%n");
System.out.printf("1 - Display by Name%n");
System.out.printf("2 - Display by Balance%n");
System.out.printf("3 - Add Record%n");
System.out.printf("4 - Delete Record%n");
System.out.printf("5 - File Dump%n");
System.out.printf("6 - Print to Textfile%n");
System.out.printf("7 - Display Record it Position (0 is first)%n");
System.out.printf("8 - Quit%n");
}
public void runMenu()
{
do {
menu();
String input = kbd.nextLine();
int choice = Integer.parseInt(input);
System.out.printf("%n");
switch(choice)
{
case 0: displayAll(); break;
case 1: displayByName(); break;
case 2: displayByBalance(); break;
case 3: addRecord(); break;
case 4: deleteRecord(); break;
case 5: fileDump(); break;
case 6: printToTextfile(); break;
case 7: displayByPosition(); break;
case 8: return;
}
try { Thread.currentThread().sleep(2000); }
catch (InterruptedException ie) { }
} while (true);
}
public void displayByName()
{
// TODO open a dialog input last name String to search for
	System.out.println("Last name string to search for");
	
   String lName = JOptionPane.showInputDialog("Enter last name to find");
	
   // TODO use the displayAll method as a model to display only records
   // that match the name string you entered; for example
   // An input of name  = "Da"  would match last names "Davis" and "Dante" 
   System.out.printf("%-15s%-15s%-9s%-10s%n","First", "Last", "Account",  "Balance");
      System.out.printf("%-15s%-15s%-9s%-10s%n","-----", "----", "-------", "-------");
      try
      {
      long end = file.length();
      file.seek(0L);
      
      while (file.getFilePointer() != end)
      {
      record.read(file);
      if (!record.getFirstName().contains("VOID") &&
    		  record.getLastName().toLowerCase().startsWith(lName.toLowerCase()))
       System.out.println(record);
      }
      System.out.println("All recoreds displayed");
      }
      catch (IOException ioe) { }   
}
public void displayByPosition()
{
   String pos = JOptionPane.showInputDialog("Enter position (0 is 1st record, 1 is second ...)");
   int position = Integer.parseInt(pos);
   
   // TODO after reading the position determine if a seek to position * Record.size() will
   // not be past the end of file.
   // If not, seek to the position and display the record
   // otherwise, print that the records number does not exist
  
	  try
	  {
	  long end = file.length();
	  file.seek(0L);
	  
	  int filePosition = position*Record.size();
	  if(filePosition < end) {
		  file.seek(filePosition);
		  record.read(file);
		  System.out.printf("%-15s%-15s%-9s%-10s%n","First", "Last", "Account", "Balance");
			 System.out.printf("%-15s%-15s%-9s%-10s%n","-----", "----", "-------", "-------");
			 System.out.println(record);
	  }
	  else
		  System.out.println("Records number does not exist.");
	  
	  }
	  catch (IOException ioe) { }   
   
   
}
public void displayAll()
{ 
   System.out.printf("%-15s%-15s%-9s%-10s%n","First", "Last", "Account", 
"Balance");
   System.out.printf("%-15s%-15s%-9s%-10s%n","-----", "----", "-------", 
"-------");
   try
   {
   long end = file.length();
   file.seek(0L);
   
   while (file.getFilePointer() != end)
   {
   record.read(file);
   if (!record.getFirstName().contains("VOID"))
    System.out.println(record);
   }
   System.out.println("All recoreds displayed");
   }
   catch (IOException ioe) { }   
}
  
public void printToTextfile()
{ 
   PrintWriter out = null;
   String filename = JOptionPane.showInputDialog("Please Enter the Filename");
   try
   {
   FileWriter writer = new FileWriter(filename);
   out = new PrintWriter(filename);
   }
   catch(IOException ioe) { }
   
   out.printf("%-15s%-15s%-9s%-10s%n","First", "Last", "Account", "Balance");
   out.printf("%-15s%-15s%-9s%-10s%n","-----", "----", "-------", "-------");
   try
   {
   long end = file.length();
   file.seek(0L);
   
   while (file.getFilePointer() != end)
   {
   record.read(file);
   if (!record.getFirstName().contains("VOID"))
    out.println(record);
   }
   
   out.println("All records displayed");
   out.close();
   System.out.println("All recoreds written to " + filename + " file");
   }
   catch (IOException ioe) { }   
}
public void fileDump()
{ 
   // TODO this method writes ALL records in the file to the screen. Even deleted records
   // with a VOID in the first name are printed. This shows us ALL contents of the file
   // this will be similar to the method displayAll, but will NOT skip records with VOID
   // in the first name attribute
	System.out.printf("%-15s%-15s%-9s%-10s%n","First", "Last", "Account", "Balance");
	   System.out.printf("%-15s%-15s%-9s%-10s%n","-----", "----", "-------", "-------");
	   try
	   {
	   long end = file.length();
	   file.seek(0L);
	   
	   while (file.getFilePointer() != end)
	   {
	   record.read(file);
	
	    System.out.println(record);
	   }
	   System.out.println("All recoreds displayed");
	   }
	   catch (IOException ioe) { }   
	
	
}
public void displayByBalance()
{
   String bal = JOptionPane.showInputDialog("Enter Maximum Balance");
   double balance = Double.parseDouble(bal);
  
    // TODO this is similar to displayALL, but only records with a balance < balance
    // will be printed
   System.out.printf("%-15s%-15s%-9s%-10s%n","First", "Last", "Account",  "Balance");
   System.out.printf("%-15s%-15s%-9s%-10s%n","-----", "----", "-------", "-------");
   try
   {
   long end = file.length();
   file.seek(0L);
   
   while (file.getFilePointer() != end)
   {
   record.read(file);
   if (!record.getFirstName().contains("VOID") && balance >= record.getBalance())
    System.out.println(record);
   }
   System.out.println("All recoreds displayed");
   }
   catch (IOException ioe) { }   
   
   
   
   
// TODO Print the number of records you printed that had a balance < balance
  
}
public void addRecord()
{
   // TODO create an input dialog four times to read in a new last, fist, account number and balance.
   // Use the JOptionPane.showInputDialog method 
  
  
   String last;
   String first;
   int account;
   double balance;
   
   last = JOptionPane.showInputDialog("Input a last Name.");
   first = JOptionPane.showInputDialog("Input a First Name");
   
   
   
   // TODO construct a default Record object 
   Record defaultRecord = new Record();
   
   
   
   // TODO Using StringBuffer class, prepare first and last names to be length 15
   // as we have done in other programs
   StringBuffer firstNameBuff = new StringBuffer();
   StringBuffer lastNameBuff = new StringBuffer();
   
   firstNameBuff.setLength(15);
   lastNameBuff.setLength(15);
   
   // TODO assign the proper length String last and first names to the last and first local Strings
   defaultRecord.setFirstName(firstNameBuff.toString());
   defaultRecord.setLastName(lastNameBuff.toString());
   // TODO use the set methods of the record class to set the four properties
   
   // TODO call the method findNewRecordLocation to place the file pointer where the first deleted
   // record is located or, if none, at the end of the file
   // TODO write the record to the file and report that the record was added sucessfully
}
// Method returns with file at empty slot or end of the file
// this method simply sets the class attribute RandomAccess file pointer to the 
// correct location for a record insertion
public void findNewRecordLocation()
{
   try
   {
   long end = file.length();
   file.seek(0L);
   
   while (file.getFilePointer() != end)
   {
   long loc = file.getFilePointer();
   record.read(file);
   if (record.getFirstName().contains("VOID"))
   {
    file.seek(loc);
    return;
   }
   }
   }       
   catch (IOException ioe) { }
}
public void deleteRecord()
{
	// TODO use JOptionPane to prompt for a last name to delete
   String Lname = JOptionPane.showInputDialog("Enter a Last name to delete");
   
   // TODO using a similar technique as displayAll method find a record where the last name 
// matches the input you read (ignore the case of the letters)
// if you match a record by last name, change the first name of the record to a String
// "VOID           " (always 15 chars are necessary) and write it back to the file in the 
// same location. 
  
	  try
	  {
	  long end = file.length();
	  file.seek(0L);
	  
	  while (file.getFilePointer() != end)
	  {
		  
		  
		long fileP = file.getFilePointer();
	  record.read(file);
	  
	  if (!record.getFirstName().contains("VOID") 
			  && record.getLastName().toLowerCase().startsWith(Lname.toLowerCase())) { 
		  
		  record.setFirstName("VOID"); 
	
		  file.seek(fileP);
		  
		  record.write(file);
		  
		  System.out.println("Record was deleted!");
	  }  
		  
	  
	  }
	 
	  }
	  catch (IOException ioe) { }   
// TODO Print whether or not you successfully deleted the record
// you cannot delete it it if it was not found
   
  
}
public static void main( String args[] )  
{
   MenuRecodsTODO menuProg = new MenuRecodsTODO(); 
   menuProg.runMenu();
}
}
