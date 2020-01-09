//import java.util.Arrays;

public class Reservation implements Comparable<Reservation> {
	
    private String guestName;
    private String phoneNumber;
    private boolean tableAssigned;
    private String[] tables;
    private int totPersons;
    private int ticketCode;
    private Day dateDine;
    private Day dateRequest;
    private RState status;
    
    public Reservation(String guestName, String phoneNumber, int totPersons, String sDateDine)
    {
        this.guestName = guestName;
        this.phoneNumber = phoneNumber;
        this.totPersons = totPersons;
        this.dateDine = new Day(sDateDine);
        this.dateRequest = SystemDate.getInstance().clone();
        this.ticketCode = BookingTicketController.takeTicket(this.dateDine);
        this.status = new RStatePending();
        this.tableAssigned = false;
    }
    
    public void assignTables(String[] cmdParts)
    {
    	this.status = new RStateTableAllocated(cmdParts);
    	this.tableAssigned = true;
    	tables = cmdParts;
    }
    
    public void undoAssignTable()
    {
    	this.status = new RStatePending();
    	this.tableAssigned = false;
    	tables = null;
    }
    
    public String[] returnTables()
    {
    	return tables;
    }
    
    public boolean returnTableAssigned()
    {
    	return this.tableAssigned;
    }
    
    public int returnTicketCode()
    {
    	return this.ticketCode;
    }
    
    public String returnGuestName()
    {
    	return this.guestName;
    }
    
    public Day returnDay()
    {
    	return this.dateDine;
    }
    
    public RState returnStatus()
    {
    	return this.status;
    }
    
    public int returnTotPersons()
    {
    	return this.totPersons;
    }
    
    @Override
    public int compareTo(Reservation another)
    {
        if (this.guestName.equals(another.guestName)) 
        {
        	if (this.phoneNumber.equals(another.phoneNumber))
        	{
        		if (this.dateDine.hashCode()>another.dateDine.hashCode())
        			return 1;
        		else
        			return -1;
        	}
        	else if (this.phoneNumber.compareTo(another.phoneNumber)>0)
        		return 1;
        	else 
        		return -1;
        }
        else if (this.guestName.compareTo(another.guestName)>0) 
        	return 1;
        else 
        	return -1;
    }
    
    @Override
    public String toString()
    {
        return String.format("%-13s%-11s%-14s%-12s(%-7s%d)%7d\t  %s", this.guestName,this.phoneNumber,this.dateRequest,this.dateDine, "Ticket",this.ticketCode,this.totPersons,this.status);
    }
    
    public static String getListingHeader()
    {
        return String.format("%-13s%-11s%-14s%-25s%s%9s", "Guest Name", "Phone", "Request Date", "Dining Date and Ticket", "#Persons", "Status");
    }
 
}