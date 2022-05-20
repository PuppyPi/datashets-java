package rebound.datashets.api.model;

import java.util.List;

public class DatashetsUsedRow
implements DatashetsRow
{
	protected List<String> singleValuedColumns;
	protected List<List<String>> multiValuedColumns;
	protected int originalDataRowIndex;  //-1, 0, 1, 2, etc.
	
	public DatashetsUsedRow()
	{
		this(null, null);
	}
	
	/**
	 * @param singleValuedColumns  live reference to it is kept!
	 * @param multiValuedColumns  live reference to it is kept!
	 */
	public DatashetsUsedRow(List<String> singleValuedColumns, List<List<String>> multiValuedColumns)
	{
		this(singleValuedColumns, multiValuedColumns, -1);
	}
	
	public DatashetsUsedRow(List<String> singleValuedColumns, List<List<String>> multiValuedColumns, int originalDataRowIndex)
	{
		this.singleValuedColumns = singleValuedColumns;
		this.multiValuedColumns = multiValuedColumns;
		this.setOriginalRowIndex(originalDataRowIndex);
	}
	
	public List<String> getSingleValuedColumns()
	{
		return singleValuedColumns;
	}
	
	public void setSingleValuedColumns(List<String> singleValuedColumns)
	{
		this.singleValuedColumns = singleValuedColumns;
	}
	
	public List<List<String>> getMultiValuedColumns()
	{
		return multiValuedColumns;
	}
	
	public void setMultiValuedColumns(List<List<String>> multiValuedColumns)
	{
		this.multiValuedColumns = multiValuedColumns;
	}
	
	@Override
	public int getOriginalRowIndex()
	{
		return originalDataRowIndex;
	}
	
	@Override
	public void setOriginalRowIndex(int originalDataRowIndex)
	{
		if (originalDataRowIndex < -1)
			throw new IllegalArgumentException();
		
		this.originalDataRowIndex = originalDataRowIndex;
	}
}
