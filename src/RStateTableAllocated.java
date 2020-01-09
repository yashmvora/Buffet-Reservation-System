import java.util.*;

public class RStateTableAllocated implements RState {
	
	private ArrayList<Table> tablesAssigned;
	
	public RStateTableAllocated(String[] cmdParts) 
	{
		tablesAssigned = new ArrayList<>();
		
		for (int i=3;i<cmdParts.length;i++)
			tablesAssigned.add(new Table(cmdParts[i]));
	}
	
	public String returnTablesAssigned() 
	{
		String tables = "";
		for(Table table: tablesAssigned) 
			tables += table.returnTableCode() + " ";
		return tables;
	}
	
	@Override
	public String toString() 
	{
		String str = "Table assigned: ";
		
		for(Table table: tablesAssigned) 
			str += table.returnTableCode() + " ";
		
		return str;
	}
	
}
