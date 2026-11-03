//asi 6/02/2018

package com.aspose.words.ref;

/**
 * Represents Out/Ref constructions for Integer primitive type
 */
public class RefInt
{
	/**
	 * Mimics Out/Ref parameter initialisation with given Value.
	 * Should be used before calling the method with Out/Ref constructions
	 */
	public RefInt(int value)
	{
		this.value = value;
	}

	/**
	 * Mimics Out/Ref parameter initialisation with given Value.
	 * Should be used before calling the method with Out/Ref constructions
	 */
	public RefInt(long value)
	{
		this.value = (int) value;
	}

	/**
	 * Gets Out/Ref parameter Value
	 * Should be used after calling the method with Out/Ref constructions
	 */
	public int get()
	{
		return value;
	}

	/**
	 * Sets Out/Ref parameter Value
	 * Should be used inside the method with Out/Ref constructions
	 */
	public int set(int anotherValue)
	{
		value = anotherValue;
		return value;
	}

	/**
	 * Sets Out/Ref parameter Value
	 * Should be used inside the method with Out/Ref constructions
	 */
	public int set(long anotherValue)
	{
		return set((int)anotherValue);
	}

	@Override
	public String toString()
	{
		return Integer.toString(value);
	}

	private int value;
}

