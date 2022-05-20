package rebound.datashets.operation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import rebound.datashets.model.DatashetsTableContents;

@FunctionalInterface
public interface DatashetsOperation
{
	/**
	 * @param data  this is created especially for this invocation and never used by Datashets again (so feel free to keep hold of it after {@link DatashetConnection#perform(boolean, DatashetsOperation)} or similar returns!
	 * @return null meaning don't write anything, otherwise it may be the same Java Object (referencewise identical) as was given to it or not!  (whatever is return is only used briefly, then forgotten once {@link DatashetConnection#perform(boolean, DatashetsOperation)} or similar returns, so feel free to keep hold of it afterward or have brought it in from beforehand!)
	 * @throws RuntimeException  anything thrown by this will be caught and handled properly (maintenance will write its changes, but no client code changes will be written)
	 */
	public @Nullable DatashetsTableContents performInMemory(@Nonnull DatashetsTableContents data) throws RuntimeException;
}
