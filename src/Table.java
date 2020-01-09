public class Table implements Comparable<Table> {

	private String code;
	private int size;
	private int ticket;
	
	public Table(String code)
	{
		if (code.charAt(0)=='T')
			this.size = 2;
		else if (code.charAt(0)=='F')
			this.size = 4;
		else
			this.size = 8;
		this.code = code;
	}
	
	public void assignTicket(int ticket)
	{
		this.ticket = ticket;
	}

	public int returnTableSize()
	{
		return this.size;
	}
	
	public String returnTableCode()
	{
		return this.code;
	}
	
	public  int returnTableTicket()
	{
		return this.ticket;
	}
	
	@Override
	public int compareTo(Table anotherTable) {
		
		if (this.size == anotherTable.size)
			return (this.code.compareTo(anotherTable.code));
		else if (this.size > anotherTable.size)
			return 1;
		else if (this.size < anotherTable.size)
			return -1;
		
		return 0;
	}
	
}
