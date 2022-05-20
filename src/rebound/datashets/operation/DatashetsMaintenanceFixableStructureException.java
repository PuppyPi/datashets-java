package rebound.datashets.operation;

/**
 * This is a {@link DatashetsStructureException} that wouldn't have been thrown if "performMaintenance" was true!  (ie, if we were allowed to fix structure errors that can be automatically fixed)
 */
public class DatashetsMaintenanceFixableStructureException
extends DatashetsStructureException
{
	private static final long serialVersionUID = 1l;
	
	public DatashetsMaintenanceFixableStructureException()
	{
		super();
	}
	
	public DatashetsMaintenanceFixableStructureException(String message)
	{
		super(message);
	}
	
	public DatashetsMaintenanceFixableStructureException(Throwable cause)
	{
		super(cause);
	}
	
	public DatashetsMaintenanceFixableStructureException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
