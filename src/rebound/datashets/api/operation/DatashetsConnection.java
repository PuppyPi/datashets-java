package rebound.datashets.api.operation;

import java.io.IOException;
import java.util.Date;
import javax.annotation.Nullable;
import rebound.datashets.api.model.DatashetsTable;
import rebound.datashets.api.operation.DatashetsOperation.DatashetsOperationWithDataTimestamp;

public interface DatashetsConnection
{
	/**
	 * @param performMaintenance  if true, errors and things may be fixed, false it's a truly readonly operation!
	 */
	public default DatashetsTable read(boolean performMaintenance) throws DatashetsStructureException, IOException
	{
		DatashetsTable[] c = new DatashetsTable[1];
		perform(performMaintenance, d -> {c[0] = d; return null;});
		return c[0];
	}
	
	
	public default void write(DatashetsTable contents) throws DatashetsStructureException, IOException
	{
		perform(true, d -> contents);  //if reading the sheet is actually unnecessary if not used by the client code (and thus inefficient)..then override write()!  XD  :3
	}
	
	
	/**
	 * @return null if unknown (an exception is thrown like it would be in {@link #read(boolean)} if the data isn't there!)
	 */
	public default @Nullable Date getCurrentLastModifiedTimestamp() throws IOException
	{
		//if reading the sheet is unnecessary if not used by the client code (and thus inefficient)..then override write()!  XD  :3
		Date[] c = new Date[1];
		perform(false, (DatashetsOperationWithDataTimestamp)(d, t) -> {c[0] = t; return null;});
		return c[0];
	}
	
	
	
	public void perform(boolean performMaintenance, @Nullable DatashetsOperation operation) throws DatashetsStructureException, IOException;
}
