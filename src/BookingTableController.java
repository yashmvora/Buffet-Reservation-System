import java.util.*;

public class BookingTableController {

	private ArrayList<Table> tablesAvailable;
	private ArrayList<Table> tablesAssigned;
	private int pendingRequests;
	private int totalPersonsPending;
	
	public BookingTableController()
	{
		tablesAvailable = new ArrayList<>();
		tablesAssigned = new ArrayList<>();
		
		for (int i=1;i<10;i++)
			tablesAvailable.add(new Table("T0"+i));
		tablesAvailable.add(new Table("T10"));
		
		for (int i=1;i<=6;i++)
			tablesAvailable.add(new Table("F0"+i));
		
		for (int i=1;i<=3;i++)
			tablesAvailable.add(new Table("H0"+i));
		
		pendingRequests = 0;
		totalPersonsPending = 0;
	}
	
	public void assignTable(String[] cmdParts) throws ExTableAlreadyAssigned, ExTableCodeNotFound 
	{
		for (int i=3;i<cmdParts.length;i++)
		{
			if (tableAlreadyAssigned(cmdParts[i]))
				throw new ExTableAlreadyAssigned("Table "+cmdParts[i]+" is already reserved by another booking!");

			if (checkTableCode(cmdParts[i])==false)
				throw new ExTableCodeNotFound("Table code "+cmdParts[i]+" not found!");
		}
		
		for (int i=3;i<cmdParts.length;i++)
		{
			for (Table table: tablesAvailable)
			{
				if (table.returnTableCode().equals(cmdParts[i]))
				{
					table.assignTicket(Integer.parseInt(cmdParts[2]));
					tablesAssigned.add(table);
				}
			}
		}
			
		for (Table table: tablesAssigned)
			tablesAvailable.remove(table);
	}
	
	public void undoAssignTable(String[] tables)
	{
		for (int i=3;i<tables.length;i++)
			for (Table table: tablesAssigned)
				if (table.returnTableCode().equals(tables[i]))
					tablesAvailable.add(table);
		
		for (Table table: tablesAvailable)
			for (int i=3;i<tables.length;i++)
				if (table.returnTableCode().equals(tables[i]))
				{
					tablesAssigned.remove(table);
					break;
				}
	}
	
	public void addRequestAndPersons(int persons)
	{
		this.pendingRequests += 1;
		this.totalPersonsPending += persons;
	}
	
	public void removeRequestAndPersons(int persons)
	{
		this.pendingRequests -= 1;
		this.totalPersonsPending -= persons;
	}
	
	public void printAssignedTables()
	{
		System.out.println("Allocated tables:");
		if (tablesAssigned.size() == 0)
			System.out.println("[None]");
		else 
		{
			Collections.sort(tablesAssigned);
			for (Table table: tablesAssigned)
				System.out.printf("%s (Ticket %d)\n", table.returnTableCode(), table.returnTableTicket());
		}
	}
	
	public void printAvailableTables()
	{
		System.out.println("\nAvailable tables:");
		Collections.sort(tablesAvailable);
		for (Table table: tablesAvailable)
			System.out.print(table.returnTableCode() + " ");
		
		System.out.printf("\n\nTotal number of pending requests = %d (Total number of persons = %d)\n", pendingRequests, totalPersonsPending);
	}
	
	public static int returnSizeOfTables(String[] cmdParts)
	{
		int size = 0;
		
		for (int i=3;i<cmdParts.length;i++)
		{
			Table t = new Table(cmdParts[i]);
			size += t.returnTableSize();
		}
		
		return size;
	}
	
	public boolean checkTableCode(String tableCode)
	{
		boolean tableCheck = false;
		for (Table table: tablesAvailable)
			if (table.returnTableCode().equals(tableCode))
				tableCheck = true;
		for (Table table: tablesAssigned)
			if (table.returnTableCode().equals(tableCode))
				tableCheck = true;
		return tableCheck;
	}
	
	public boolean tableAlreadyAssigned(String tableCode) 
	{
		for(Table table: tablesAssigned) {
			if(table.returnTableCode().equals(tableCode))
				return true;
		}
		return false;
	}

	public String suggestTable(int totalPersons) 
	{
		boolean table8Available = true;
		boolean table4Available = true;
		boolean table2Available = true;
		String suggestedTables = "";
		ArrayList<Table> duplicateTableList = new ArrayList<Table>(tablesAvailable);
		Collections.sort(duplicateTableList);
		while(totalPersons>0)
		{
			if (table2Available == false && table4Available == false && table8Available == false)
				return "Not enough tables";
			if (totalPersons<=2 || table4Available == false)
			{
				table2Available = false;
				for (Table table: duplicateTableList)
					if(table.returnTableSize()==2)
					{
						table2Available = true;
						suggestedTables += table.returnTableCode() + " ";
						duplicateTableList.remove(table);
						totalPersons -= 2;
						break;
					}
			}
			else if (totalPersons<=4 || table8Available == false)
			{
				table4Available = false;
				for (Table table: duplicateTableList)
					if(table.returnTableSize()==4)
					{
						table4Available = true;
						suggestedTables += table.returnTableCode() + " ";
						duplicateTableList.remove(table);
						totalPersons -= 4;
						break;
					}
			}
			else
			{
				table8Available = false;
				for (Table table: duplicateTableList)
					if(table.returnTableSize()==8)
					{
						table8Available = true;
						suggestedTables += table.returnTableCode() + " ";
						duplicateTableList.remove(table);
						totalPersons -= 8;
						break;
					}
			}
		}
		return suggestedTables;
	}
	
}
