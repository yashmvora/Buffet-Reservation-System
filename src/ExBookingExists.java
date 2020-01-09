public class ExBookingExists extends Exception {

	private static final long serialVersionUID = 1L;
	public ExBookingExists() { super("Booking by the same person for the dining date already exists!"); }
	public ExBookingExists(String message) { super(message);}
	
}
