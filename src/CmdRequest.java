
public class CmdRequest extends RecordedCommand {

	Reservation r;
	BookingOffice bo = BookingOffice.getInstance();
	SystemDate s = SystemDate.getInstance();
	
	public void execute(String[] cmdParts)
	{
		try {
			
			if (cmdParts.length<5)
				throw new ExInsufficientArguments();
			else if (s.hashCode()>(new Day(cmdParts[4]).hashCode()))
				throw new ExDateHasAlreadyPassed();
		
			r = new Reservation(cmdParts[1], cmdParts[2], Integer.parseInt(cmdParts[3]), cmdParts[4]);
		
			if (r.returnTicketCode()>1)
				if (bo.findReservation(cmdParts[1], new Day(cmdParts[4])))
					throw new ExBookingExists();
			
			bo.addReservation(r);
			
			addUndoCommand(this);
			clearRedoList();
			
			System.out.printf("Done. Ticket code for %s: %d\n", cmdParts[4], r.returnTicketCode());
		
		} catch (NumberFormatException e) {
			System.out.println("Wrong number format!");
		} catch (ExInsufficientArguments e) {
			System.out.println(e.getMessage()); 
		} catch (ExDateHasAlreadyPassed e) {
			System.out.println(e.getMessage());
		} catch (ExBookingExists e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void undoMe() 
	{
		bo.removeReservation(r);
		addRedoCommand(this);
	}
	
	@Override
	public void redoMe() 
	{	
		bo.addReservation(r);
		addUndoCommand(this);
	}
	
}
