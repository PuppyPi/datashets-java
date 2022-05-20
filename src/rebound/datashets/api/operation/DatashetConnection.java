package rebound.datashets.api.operation;

import java.io.IOException;
import javax.annotation.Nullable;
import rebound.datashets.api.model.DatashetsTableContents;

public interface DatashetConnection
{
	/**
	 * @param performMaintenance  if true, errors and things may be fixed, false it's a truly readonly operation!
	 */
	public default DatashetsTableContents read(boolean performMaintenance) throws DatashetsStructureException, IOException
	{
		DatashetsTableContents[] c = new DatashetsTableContents[1];
		perform(performMaintenance, d -> {c[0] = d; return null;});
		return c[0];
	}
	
	
	public default void write(DatashetsTableContents contents) throws DatashetsStructureException, IOException
	{
		perform(true, d -> contents);
	}
	
	
	public void perform(boolean performMaintenance, @Nullable DatashetsOperation operation) throws DatashetsStructureException, IOException;
}
