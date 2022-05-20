package rebound.datashets.api.model;

/**
 * You tried to access a cell in an {@link DatashetsUnusedRow absent row}!
 */
public class DatashetsUnusedRowException
extends RuntimeException
{
	private static final long serialVersionUID = 1l;
	
	public DatashetsUnusedRowException()
	{
		super();
	}
	
	public DatashetsUnusedRowException(String message)
	{
		super(message);
	}
	
	public DatashetsUnusedRowException(Throwable cause)
	{
		super(cause);
	}
	
	public DatashetsUnusedRowException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
