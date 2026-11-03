//asi 6/02/2018

package com.aspose.words.ref;

/**
 * Represents Out/Ref constructions for Boolean primitive type
 */
public class RefBoolean
{
	/**
	 * Mimics Out/Ref parameter initialisation with given Value.
	 * Should be used before calling the method with Out/Ref constructions
	 */
	public RefBoolean(boolean value)
	{
		this.value = value;
	}

	/**
	 * Gets Out/Ref parameter Value
	 * Should be used after calling the method with Out/Ref constructions
	 */
	public boolean get()
	{
		return value;
	}

	/**
	 * Sets Out/Ref parameter Value
	 * Should be used inside the method with Out/Ref constructions
	 */
	public boolean set(boolean anotherValue)
	{
		value = anotherValue;
		return value;
	}

	@Override
	public String toString()
	{
		return Boolean.toString(value);
	}

	private boolean value;
}

