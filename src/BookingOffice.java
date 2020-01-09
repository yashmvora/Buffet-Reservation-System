import java.util.*;

public class BookingOffice {
 
    private ArrayList<Reservation> allReservations;
    
    private static HashMap<Day, BookingTableController> tableController;
    
    private static BookingOffice instance =  new BookingOffice();
    
    private BookingOffice()
    {
        allReservations = new ArrayList<>();
        tableController = new HashMap<>();
    }
    
    public static BookingOffice getInstance()
    {
        return instance;
    }
    
    public Reservation addReservation(String guestName, String phoneNumber, int totPersons, String sDateDine)
    {
        Reservation r = new Reservation(guestName, phoneNumber, totPersons, sDateDine);
        allReservations.add(r);
        Collections.sort(allReservations);
        return r;
    }
    
    public void addReservation(Reservation r)
    {
    	BookingTicketController.addTicket(r.returnDay());
    	BookingTableController tController = tableController.get(r.returnDay());
    	if (tController == null)
    		tableController.put(r.returnDay(), new BookingTableController());
    	tController = tableController.get(r.returnDay());
    	tController.addRequestAndPersons(r.returnTotPersons());
    	
    	allReservations.add(r);
    	Collections.sort(allReservations);
    }
    
    public void removeReservation(Reservation r)
    {
    	BookingTicketController.removeTicket(r.returnDay());
    	allReservations.remove(r);
    }
    
    public void cancelReservation(Reservation r)
    {
    	BookingTableController tController = tableController.get(r.returnDay());
    	if (r.returnStatus() instanceof RStateTableAllocated)
    	{
    		String[] tablesOfReservation = r.returnTables();
	    	tController.undoAssignTable(tablesOfReservation);
    	}
    	if (r.returnStatus() instanceof RStatePending)
    	{
    		tController.removeRequestAndPersons(r.returnTotPersons());
    	}
    	allReservations.remove(r);
    }
    
    public void suggestTable(Reservation r)
    {
    	BookingTableController tController = tableController.get(r.returnDay());
    	String suggestedTables = tController.suggestTable(r.returnTotPersons());
    	System.out.printf("Suggestion for %d persons: %s\n", r.returnTotPersons(), suggestedTables);
    }
    
    public boolean findReservation(String name, Day d)
    {
    	for (Reservation r: allReservations)
    		if (r.returnGuestName().equals(name) && r.returnDay().equals(d))
    			return true;
    	return false;
    }
    
    public Reservation findReservation(Day d, int ticketCode)
    {
    	for (Reservation r: allReservations)
    		if (d.equals(r.returnDay()) && (ticketCode == r.returnTicketCode()))
    			return r;
    	return null;
    }
    
    public void listReservations()
    {
        System.out.println(Reservation.getListingHeader());
        for (Reservation r: allReservations)
            System.out.println(r);
    }
    
    public void listTableAllocations(Day d)
    {
    	BookingTableController tController = tableController.get(d);
    	
    	if (tController == null)
    	{
    		tableController.put(d, new BookingTableController());
    		tController = tableController.get(d);
    	}
    	tController.printAssignedTables();
    	tController.printAvailableTables();
    }
    
	public void assignTables(String[] cmdParts, Reservation r) throws ExTableAlreadyAssigned, ExTableCodeNotFound
	{
		Day d = new Day(cmdParts[1]);
    	BookingTableController tController = tableController.get(d);
    	
    	if (tController == null)
    	{
    		tableController.put(d, new BookingTableController());
    		tController = tableController.get(d);
    	}
		tController.assignTable(cmdParts);
    	tController.removeRequestAndPersons(r.returnTotPersons());
    	r.assignTables(cmdParts);
	}
    
	public void undoAssignTables(Reservation r, String[] tables)
	{
		r.undoAssignTable();
		BookingTableController tController = tableController.get(r.returnDay());
		tController.addRequestAndPersons(r.returnTotPersons());
		tController.undoAssignTable(tables);
	}
     
}