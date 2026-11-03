//asi 6/02/2018

package com.aspose.words.ref;

import com.aspose.ms.System.Drawing.msSizeF;

/**
 * Represents Out/Ref constructions for SizeF struct
 */
public class RefSizeF
{
	/**
	 * Mimics Out/Ref parameter initialisation with given Value.
	 * Should be used before calling the method with Out/Ref constructions
	 */
	public RefSizeF(long value)
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
		return msSizeF.toJava(value).toString();
	}

	private long value;
}

