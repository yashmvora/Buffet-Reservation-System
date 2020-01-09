public class CmdAssignTable extends RecordedCommand {

	Reservation r;
	Day d;
	String[] tables;
	BookingOffice bo = BookingOffice.getInstance();
	
	public void execute(String[] cmdParts) {
		
		try {
			d = new Day(cmdParts[1]);
			r = bo.findReservation(d, Integer.parseInt(cmdParts[2]));
			
			if (r == null)
				throw new ExBookingNotFound();
			else if (cmdParts.length<4)
				throw new ExInsufficientArguments();
			else if (r.returnTableAssigned() == true)
				throw new ExTableAlreadyAssigned();
			else if (r.returnTotPersons()> BookingTableController.returnSizeOfTables(cmdParts))
				throw new ExNotEnoughSeats();
			else if (new Day(cmdParts[1]).hashCode()<SystemDate.getInstance().hashCode())
				throw new ExDateHasAlreadyPassed();
			
			bo.assignTables(cmdParts,r);
			
			tables = cmdParts;
			
			addUndoCommand(this);
			clearRedoList();
			
			System.out.println("Done.");
			
		} catch (NumberFormatException e) {
			System.out.println("Wrong number format!");
		} catch (ExBookingNotFound e) {
			System.out.println(e.getMessage());
		} catch (ExTableAlreadyAssigned e) {
			System.out.println(e.getMessage());
		} catch (ExInsufficientArguments e) {
			System.out.println(e.getMessage());
		} catch (ExNotEnoughSeats e) {
			System.out.println(e.getMessage());
		} catch (ExDateHasAlreadyPassed e) {
			System.out.println(e.getMessage());
		} catch (ExTableCodeNotFound e) {
			System.out.println(e.getMessage());
		} 

	}

	@Override
	public void undoMe() {
		
		bo.undoAssignTables(r, tables);
		addRedoCommand(this);
	}

	@Override
	public void redoMe() {
		
		try {
			bo.assignTables(tables,r);
		} catch (ExTableAlreadyAssigned e) {
			System.out.println(e.getMessage());
		} catch (ExTableCodeNotFound e) {
			System.out.println(e.getMessage());
		}
		addUndoCommand(this);
	}
}
