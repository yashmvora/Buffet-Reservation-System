public class CmdStartNewDay extends RecordedCommand {
	
	SystemDate s = SystemDate.getInstance();
	String oldDate = s.toString();
	String newDate;

	public void execute(String[] cmdParts)
	{
		try {
			if(cmdParts.length<2)
				throw new ExInsufficientArguments();
			
			s.set(cmdParts[1]);
			newDate = cmdParts[1];
			addUndoCommand(this);
			clearRedoList();
			
			System.out.println("Done.");
		} catch (ExInsufficientArguments e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void undoMe() 
	{
		s.set(oldDate);
		addRedoCommand(this);
	}

	@Override
	public void redoMe() 
	{
		s.set(newDate);
		addUndoCommand(this);
	}
	
}
