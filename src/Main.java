import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	public static void main(String [] args) throws FileNotFoundException
	{	
		
		Scanner in = new Scanner(System.in);
		
		System.out.print("Please input the file pathname: ");
		String filepathname = in.nextLine();
		
		Scanner inFile = new Scanner(new File(filepathname));
		
		String cmdLine1 = inFile.nextLine();
		String[] cmdLine1Parts = cmdLine1.split("\\|"); 
		System.out.println("\n> "+cmdLine1);
		SystemDate.createTheInstance(cmdLine1Parts[1]);
		
		while (inFile.hasNext())		
		{
			String cmdLine = inFile.nextLine().trim();
			
			if (cmdLine.equals("")) continue;  

			System.out.println("\n> "+cmdLine);
			
			String[] cmdParts = cmdLine.split("\\|"); 
			
			if (cmdParts[0].equals("request"))
				(new CmdRequest()).execute(cmdParts);
			if (cmdParts[0].equals("listReservations"))
				(new CmdListReservations()).execute(cmdParts);
			if (cmdParts[0].equals("startNewDay"))
				(new CmdStartNewDay()).execute(cmdParts);
			if (cmdParts[0].equals("undo"))
				RecordedCommand.undoOneCommand();
			if (cmdParts[0].equals("redo"))
				RecordedCommand.redoOneCommand();
			if (cmdParts[0].equals("assignTable"))
				(new CmdAssignTable()).execute(cmdParts);
			if (cmdParts[0].equals("listTableAllocations"))
				(new CmdListTableAllocations()).execute(cmdParts);
			if (cmdParts[0].equals("cancel"))
				(new CmdCancel()).execute(cmdParts);
			if (cmdParts[0].equals("suggestTable"))
				(new CmdSuggestTable()).execute(cmdParts);
			
		}
		inFile.close();
			
		in.close();
	}
}
