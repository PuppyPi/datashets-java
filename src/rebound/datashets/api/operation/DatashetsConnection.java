package rebound.datashets.api.operation;

import java.io.IOException;
import javax.annotation.Nullable;
import rebound.datashets.api.model.DatashetsTable;

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
		perform(true, d -> contents);
	}
	
	
	public void perform(boolean performMaintenance, @Nullable DatashetsOperation operation) throws DatashetsStructureException, IOException;
}
