
public class CmdCancel extends RecordedCommand {

	BookingOffice bo = BookingOffice.getInstance();
	Reservation r;
	String[] tables;
	
	@Override
	public void execute(String[] cmdParts) {
		
		try {
			
			if (cmdParts.length<3)
				throw new ExInsufficientArguments();
			else if (new Day(cmdParts[1]).hashCode()<SystemDate.getInstance().hashCode())
				throw new ExDateHasAlreadyPassed();
			
			r = bo.findReservation(new Day(cmdParts[1]), Integer.parseInt(cmdParts[2]));
			
			if (r == null)
				throw new ExBookingNotFound();
			
			tables = r.returnTables();
			bo.cancelReservation(r);
			
			addUndoCommand(this);
			clearRedoList();
			
			System.out.println("Done.");
		} catch (NumberFormatException e) {
			System.out.println("Wrong number format!");
		} catch (ExBookingNotFound e) {
			System.out.println(e.getMessage());
		} catch (ExDateHasAlreadyPassed e) {
			System.out.println(e.getMessage());
		} catch (ExInsufficientArguments e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void undoMe() 
	{
		bo.addReservation(r);
		if (r.returnTableAssigned() == true)
		{
			try {
				bo.assignTables(tables, r);
			} catch (ExTableAlreadyAssigned e) {
				System.out.println(e.getMessage());
			} catch (ExTableCodeNotFound e) {
				System.out.println(e.getMessage());
			}
		}
		addRedoCommand(this);
	}

	@Override
	public void redoMe() 
	{	
		bo.cancelReservation(r);
		addUndoCommand(this);
	}

}
