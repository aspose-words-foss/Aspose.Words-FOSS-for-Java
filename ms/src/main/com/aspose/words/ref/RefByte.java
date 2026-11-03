//asi 6/02/2018

package com.aspose.words.ref;

/**
 * Represents Out/Ref constructions for Byte primitive type
 */
public class RefByte
{
	/**
	 * Mimics Out/Ref parameter initialisation with given Value.
	 * Should be used before calling the method with Out/Ref constructions
	 */
	public RefByte(byte value)
	{
		this.value = value;
	}

	/**
	 * Gets Out/Ref parameter Value
	 * Should be used after calling the method with Out/Ref constructions
	 */
	public byte get()
	{
		return value;
	}

	/**
	 * Sets Out/Ref parameter Value
	 * Should be used inside the method with Out/Ref constructions
	 */
	public byte set(byte anotherValue)
	{
		value = anotherValue;
		return value;
	}

	@Override
	public String toString()
	{
		return Integer.toString(value);
	}

	private byte value;
}

