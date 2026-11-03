//asi 6/02/2018

package com.aspose.words.ref;

/**
 * Represents Out/Ref constructions for Long primitive type
 */
public class RefLong
{
	/**
	 * Mimics Out/Ref parameter initialisation with given Value.
	 * Should be used before calling the method with Out/Ref constructions
	 */
	public RefLong(long value)
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
		return Long.toString(value);
	}

	private long value;
}

