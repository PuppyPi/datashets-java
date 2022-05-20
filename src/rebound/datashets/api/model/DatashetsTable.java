package rebound.datashets.api.model;

import static java.util.Arrays.*;
import static java.util.Objects.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Note: while row indexes match up perfectly with the underlying thing if there is such a thing (after the frozen header rows), column indexes certainly don't generally!!<br>
 * They're only useful here in this object!<br>
 * (I mean there are two different kinds that both start at zero so that could never be XD )<br>
 * 
 * + The {@link String}s in single-value cells are {@link Nullable} but not inside multivalue cell {@link List}s.  The lists are however able to be empty.
 */
public class DatashetsTable
{
	protected final @Nonnull DatashetsSemanticColumns columnsSingleValued;
	protected final @Nonnull DatashetsSemanticColumns columnsMultiValued;
	
	protected List<DatashetsRow> rows;
	
	
	public DatashetsTable(List<String> columnUIDsSingleValued, List<String> columnUIDsMultiValued)
	{
		this(new DatashetsSemanticColumns(columnUIDsSingleValued), new DatashetsSemanticColumns(columnUIDsMultiValued));
	}
	
	public DatashetsTable(DatashetsSemanticColumns columnsSingleValued, DatashetsSemanticColumns columnsMultiValued)
	{
		this(columnsSingleValued, columnsMultiValued, new ArrayList<>());
	}
	
	/**
	 * + Note that there can't be any overlap between the UIDs of single and multi value column-sets!
	 * @param rows  this will be kept as a live reference!  (you can set it to null briefly, but make sure to {@link #setRows(List) set it} to something sensible before it's used!!)
	 */
	public DatashetsTable(@Nonnull DatashetsSemanticColumns columnsSingleValued, @Nonnull DatashetsSemanticColumns columnsMultiValued, List<DatashetsRow> rows)
	{
		requireNonNull(columnsSingleValued);
		requireNonNull(columnsMultiValued);
		
		Set<String> uidOverlap = columnsSingleValued.getUIDOverlapWith(columnsMultiValued);
		if (!uidOverlap.isEmpty())
			throw new IllegalArgumentException("Overlap between single and multi valued columns' UIDs!!: "+uidOverlap);
		
		this.columnsSingleValued = columnsSingleValued;
		this.columnsMultiValued = columnsMultiValued;
		this.rows = rows;
	}
	
	
	
	
	
	
	public DatashetsSemanticColumns getColumnsSingleValued()
	{
		return columnsSingleValued;
	}
	
	public DatashetsSemanticColumns getColumnsMultiValued()
	{
		return columnsMultiValued;
	}
	
	
	public int getNumberOfColumnsSingleValued()
	{
		return columnsSingleValued.size();
	}
	
	public int getNumberOfColumnsMultiValued()
	{
		return columnsMultiValued.size();
	}
	
	
	public int getNumberOfRows()
	{
		return rows.size();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @return it's just a plain list of {@link DatashetsRow}s and nulls, fully writable and {@link #setRows(List) changeable}!
	 */
	public List<DatashetsRow> getRows()
	{
		return rows;
	}
	
	/**
	 * @param rows  (a live reference will be kept to it!)
	 */
	public void setRows(List<DatashetsRow> rows)
	{
		this.rows = rows;
	}
	
	/**
	 * @param rowIndex  starts at 0
	 * @throws IndexOutOfBoundsException  if rowIndex is too small or large
	 */
	public DatashetsUsedRow getUsedRow(int rowIndex) throws IndexOutOfBoundsException, DatashetsUnusedRowException
	{
		DatashetsRow row = rows.get(rowIndex);
		if (row instanceof DatashetsUsedRow)
			return (DatashetsUsedRow)row;
		else
			throw new DatashetsUnusedRowException("at rows["+rowIndex+"]");
	}
	
	
	
	
	
	
	
	
	
	/**
	 * @param columnIndex  starts at 0
	 * @param rowIndex  starts at 0
	 * @throws IndexOutOfBoundsException  if columnIndex or rowIndex is too small or large
	 */
	public @Nullable String getCell(int columnIndex, int rowIndex) throws IndexOutOfBoundsException, DatashetsUnusedRowException
	{
		return getUsedRow(rowIndex).getSingleValuedColumns().get(columnIndex);
	}
	
	/**
	 * @param columnIndex  starts at 0
	 * @param rowIndex  starts at 0
	 * @throws IndexOutOfBoundsException  if columnIndex or rowIndex is too small or large
	 */
	public void setCell(int columnIndex, int rowIndex, @Nullable String value) throws IndexOutOfBoundsException, DatashetsUnusedRowException
	{
		requireNonNull(value);
		getUsedRow(rowIndex).getSingleValuedColumns().set(columnIndex, value);
	}
	
	
	/**
	 * @param columnUID  case insensitive (auto uppercased)
	 * @param rowIndex  starts at 0
	 * @throws DatashetsNoSuchColumnException  if there is no single-valued column by that uid
	 * @throws IndexOutOfBoundsException  if rowIndex is too small or large
	 */
	public @Nullable String getCell(@Nonnull String columnUID, int rowIndex) throws DatashetsNoSuchColumnException, IndexOutOfBoundsException, DatashetsUnusedRowException
	{
		return getCell(columnsSingleValued.requireIndexByUID(columnUID), rowIndex);
	}
	
	/**
	 * @param columnUID  case insensitive (auto uppercased)
	 * @param rowIndex  starts at 0
	 * @throws DatashetsNoSuchColumnException  if there is no single-valued column by that uid
	 * @throws IndexOutOfBoundsException  if rowIndex is too small or large
	 */
	public void setCell(@Nonnull String columnUID, int rowIndex, @Nullable String value) throws DatashetsNoSuchColumnException, IndexOutOfBoundsException, DatashetsUnusedRowException
	{
		setCell(columnsSingleValued.requireIndexByUID(columnUID), rowIndex, value);
	}
	
	
	
	/**
	 * The lists of multivalue "cells" are generally writable and mutable (eg, {@link ArrayList}s not {@link Arrays#asList(Object...)}s)
	 * But it's up to you if you put fixed-length or otherwise readonly implementations in the {@link DatashetsRow}s ofc!  (eg, with {@link #setMultiCell(int, int, List)})
	 * @param columnIndex  starts at 0
	 * @param rowIndex  starts at 0
	 * @throws IndexOutOfBoundsException  if columnIndex or rowIndex is too small or large
	 */
	public @Nonnull List<String> getMultiCell(int columnIndex, int rowIndex) throws IndexOutOfBoundsException
	{
		return getUsedRow(rowIndex).getMultiValuedColumns().get(columnIndex);
	}
	
	/**
	 * @param columnIndex  starts at 0
	 * @param rowIndex  starts at 0
	 * @throws IndexOutOfBoundsException  if columnIndex or rowIndex is too small or large
	 */
	public void setMultiCell(int columnIndex, int rowIndex, @Nonnull List<String> value) throws IndexOutOfBoundsException
	{
		getUsedRow(rowIndex).getMultiValuedColumns().set(columnIndex, value);
	}
	
	
	/**
	 * The lists of multivalue "cells" are generally writable and mutable (eg, {@link ArrayList}s not {@link Arrays#asList(Object...)}s)
	 * But it's up to you if you put fixed-length or otherwise readonly implementations in the {@link DatashetsRow}s ofc!  (eg, with {@link #setMultiCell(String, int, List)})
	 * @param columnUID  case insensitive (auto uppercased)
	 * @param rowIndex  starts at 0
	 * @throws DatashetsNoSuchColumnException  if there is no single-valued column by that uid
	 * @throws IndexOutOfBoundsException  if rowIndex is too small or large
	 */
	public @Nonnull List<String> getMultiCell(@Nonnull String columnUID, int rowIndex) throws DatashetsNoSuchColumnException, IndexOutOfBoundsException
	{
		return getMultiCell(columnsSingleValued.requireIndexByUID(columnUID), rowIndex);
	}
	
	/**
	 * @param columnUID  case insensitive (auto uppercased)
	 * @param rowIndex  starts at 0
	 * @throws DatashetsNoSuchColumnException  if there is no single-valued column by that uid
	 * @throws IndexOutOfBoundsException  if rowIndex is too small or large
	 */
	public void setMultiCell(@Nonnull String columnUID, int rowIndex, List<String> value) throws DatashetsNoSuchColumnException, IndexOutOfBoundsException
	{
		setMultiCell(columnsSingleValued.requireIndexByUID(columnUID), rowIndex, value);
	}
	
	
	
	
	
	
	
	
	
	/**
	 * @param columnIndex  starts at 0
	 * @throws IndexOutOfBoundsException  if columnIndex or row is too small or large
	 */
	public @Nullable String getCell(int columnIndex, DatashetsUsedRow row) throws IndexOutOfBoundsException
	{
		return row.getSingleValuedColumns().get(columnIndex);
	}
	
	/**
	 * @param columnIndex  starts at 0
	 * @throws IndexOutOfBoundsException  if columnIndex or row is too small or large
	 */
	public void setCell(int columnIndex, DatashetsUsedRow row, @Nullable String value) throws IndexOutOfBoundsException
	{
		requireNonNull(value);
		row.getSingleValuedColumns().set(columnIndex, value);
	}
	
	
	/**
	 * @param columnUID  case insensitive (auto uppercased)
	 * @throws DatashetsNoSuchColumnException  if there is no single-valued column by that uid
	 * @throws IndexOutOfBoundsException  if row is too small or large
	 */
	public @Nullable String getCell(@Nonnull String columnUID, DatashetsUsedRow row) throws DatashetsNoSuchColumnException, IndexOutOfBoundsException
	{
		return getCell(columnsSingleValued.requireIndexByUID(columnUID), row);
	}
	
	/**
	 * @param columnUID  case insensitive (auto uppercased)
	 * @throws DatashetsNoSuchColumnException  if there is no single-valued column by that uid
	 * @throws IndexOutOfBoundsException  if row is too small or large
	 */
	public void setCell(@Nonnull String columnUID, DatashetsUsedRow row, @Nullable String value) throws DatashetsNoSuchColumnException, IndexOutOfBoundsException
	{
		setCell(columnsSingleValued.requireIndexByUID(columnUID), row, value);
	}
	
	
	
	/**
	 * The lists of multivalue "cells" are generally writable and mutable (eg, {@link ArrayList}s not {@link Arrays#asList(Object...)}s)
	 * But it's up to you if you put fixed-length or otherwise readonly implementations in the {@link DatashetsUsedRow}s ofc!  (eg, with {@link #setMultiCell(int, int, List)})
	 * @param columnIndex  starts at 0
	 * @throws IndexOutOfBoundsException  if columnIndex or row is too small or large
	 */
	public @Nonnull List<String> getMultiCell(int columnIndex, DatashetsUsedRow row) throws IndexOutOfBoundsException
	{
		return row.getMultiValuedColumns().get(columnIndex);
	}
	
	/**
	 * @param columnIndex  starts at 0
	 * @throws IndexOutOfBoundsException  if columnIndex or row is too small or large
	 */
	public void setMultiCell(int columnIndex, DatashetsUsedRow row, @Nonnull List<String> value) throws IndexOutOfBoundsException
	{
		row.getMultiValuedColumns().set(columnIndex, value);
	}
	
	
	/**
	 * The lists of multivalue "cells" are generally writable and mutable (eg, {@link ArrayList}s not {@link Arrays#asList(Object...)}s)
	 * But it's up to you if you put fixed-length or otherwise readonly implementations in the {@link DatashetsUsedRow}s ofc!  (eg, with {@link #setMultiCell(String, int, List)})
	 * @param columnUID  case insensitive (auto uppercased)
	 * @throws DatashetsNoSuchColumnException  if there is no single-valued column by that uid
	 * @throws IndexOutOfBoundsException  if row is too small or large
	 */
	public @Nonnull List<String> getMultiCell(@Nonnull String columnUID, DatashetsUsedRow row) throws DatashetsNoSuchColumnException, IndexOutOfBoundsException
	{
		return getMultiCell(columnsSingleValued.requireIndexByUID(columnUID), row);
	}
	
	/**
	 * @param columnUID  case insensitive (auto uppercased)
	 * @throws DatashetsNoSuchColumnException  if there is no single-valued column by that uid
	 * @throws IndexOutOfBoundsException  if row is too small or large
	 */
	public void setMultiCell(@Nonnull String columnUID, DatashetsUsedRow row, List<String> value) throws DatashetsNoSuchColumnException, IndexOutOfBoundsException
	{
		setMultiCell(columnsSingleValued.requireIndexByUID(columnUID), row, value);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Adds a new row with all blank (null) cells to the end, returning it in case you want to edit it!<br>
	 * (Its index will be = {@link #getNumberOfRows()} just before this is called :3 )<br>
	 * <br>
	 * The lists in multivalued columns will be writable but (initially) empty lists.<br>
	 */
	public DatashetsUsedRow addUsedRow()
	{
		return addUsedRow(null);
	}
	
	/**
	 * Like {@link #addUsedRow()} but you get to set the value of newly-created cells (single-valued ones; multi-valued ones still start with each their own separate empty mutable list)
	 */
	public DatashetsUsedRow addUsedRow(@Nullable String newSingleValuedCellValues)
	{
		DatashetsUsedRow row = new DatashetsUsedRow();
		
		String[] s = new String[getNumberOfColumnsSingleValued()];
		List<String>[] m = new List[getNumberOfColumnsMultiValued()];
		Arrays.fill(s, newSingleValuedCellValues);
		
		for (int i = 0; i < m.length; i++)
			m[i] = new ArrayList<>();
		
		row.setSingleValuedColumns(asList(s));
		row.setMultiValuedColumns(asList(m));
		
		rows.add(row);
		
		return row;
	}
	
	
	public DatashetsUnusedRow addUnusedRow()
	{
		DatashetsUnusedRow row = new DatashetsUnusedRow();
		rows.add(row);
		return row;
	}
	
	
	
	
	
	/**
	 * Yes you can do this! :&gt;<br>
	 * The columns are quite immutable but you can add and remove rows however you like! :D
	 */
	public DatashetsRow removeRow(int index) throws IndexOutOfBoundsException
	{
		return getRows().remove(index);
	}
}
