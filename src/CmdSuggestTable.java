
public class CmdSuggestTable implements Command {

	BookingOffice bo = BookingOffice.getInstance();
	Reservation r;
	
	@Override
	public void execute(String[] cmdParts) {
		
		try {
			if (cmdParts.length < 3)
				throw new ExInsufficientArguments();
			
			r = bo.findReservation(new Day(cmdParts[1]), Integer.parseInt(cmdParts[2]));
			
			if (r == null)
				throw new ExBookingNotFound();
			else if (r.returnStatus() instanceof RStateTableAllocated)
				throw new ExTableAlreadyAssigned();
			
			bo.suggestTable(r);
			
		} catch (NumberFormatException e) {
			System.out.println("Wrong number format!");
		} catch (ExBookingNotFound e) {
			System.out.println(e.getMessage());
		} catch (ExTableAlreadyAssigned e) {
			System.out.println(e.getMessage());
		} catch (ExInsufficientArguments e) {
			System.out.println(e.getMessage());
		}
		
	}

}
