package rebound.datashets.api.model;

public class DatashetsNoSuchColumnException
extends RuntimeException
{
	private static final long serialVersionUID = 1l;
	
	public DatashetsNoSuchColumnException()
	{
		super();
	}
	
	public DatashetsNoSuchColumnException(String message)
	{
		super(message);
	}
	
	public DatashetsNoSuchColumnException(Throwable cause)
	{
		super(cause);
	}
	
	public DatashetsNoSuchColumnException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
