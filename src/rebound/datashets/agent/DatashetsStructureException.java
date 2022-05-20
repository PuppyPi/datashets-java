package rebound.datashets.agent;

public class DatashetsStructureException
extends RuntimeException
{
	private static final long serialVersionUID = 1l;
	
	public DatashetsStructureException()
	{
		super();
	}
	
	public DatashetsStructureException(String message)
	{
		super(message);
	}
	
	public DatashetsStructureException(Throwable cause)
	{
		super(cause);
	}
	
	public DatashetsStructureException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
