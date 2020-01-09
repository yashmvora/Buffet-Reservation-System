public class CmdListTableAllocations implements Command {
	
	Day d;
	BookingOffice bo = BookingOffice.getInstance();
	
	public void execute(String[] cmdParts) {

		d = new Day(cmdParts[1]);
		bo.listTableAllocations(d);
	}

}
