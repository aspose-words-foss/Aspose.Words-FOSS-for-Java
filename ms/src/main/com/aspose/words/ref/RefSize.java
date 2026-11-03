//asi 6/02/2018

package com.aspose.words.ref;

import com.aspose.ms.System.Drawing.msSize;

/**
 * Represents Out/Ref constructions for Size struct
 */
public class RefSize
{
	/**
	 * Mimics Out/Ref parameter initialisation with given Value.
	 * Should be used before calling the method with Out/Ref constructions
	 */
	public RefSize(long value)
	{
		this.value = value;
	}

	/**
	 * Gets Out/Ref parameter Value
	 * Should be used after calling the method with Out/Ref constructions
	 */
	public long get()
	{
		return value;
	}

	/**
	 * Sets Out/Ref parameter Value
	 * Should be used inside the method with Out/Ref constructions
	 */
	public long set(long anotherValue)
	{
		value = anotherValue;
		return value;
	}

	@Override
	public String toString()
	{
		return msSize.toJava(value).toString();
	}

	private long value;
}

