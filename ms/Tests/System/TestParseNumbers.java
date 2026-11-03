// ported 3/10/2008 by Konstantin Sidorenko
package com.aspose;

import com.aspose.ms.System.ParseNumbers;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * System.ParseNumbers is internal in .Net so we can't do autoporting roundtrip for it.
 */
@Test
public class TestParseNumbers
{
	@Test
	public void TestStringToInt()
	{
		Assert.assertEquals(0, ParseNumbers.stringToInt("0", 10, 0));
		Assert.assertEquals(0, ParseNumbers.stringToInt("-0", 10, 0));
		Assert.assertEquals(1234567890, ParseNumbers.stringToInt("1234567890", 10, 0));
		Assert.assertEquals(-1234567890, ParseNumbers.stringToInt("-1234567890", 10, 0));

		Assert.assertEquals(0x0, ParseNumbers.stringToInt("0", 16, 0));
		Assert.assertEquals(0xA, ParseNumbers.stringToInt("a", 16, 0));
		Assert.assertEquals(0xA, ParseNumbers.stringToInt("A", 16, 0));
		Assert.assertEquals(0x12345678, ParseNumbers.stringToInt("12345678", 16, 0));
		Assert.assertEquals(-0x12345678, ParseNumbers.stringToInt("-12345678", 16, 0));
		Assert.assertEquals(0x9abcdef, ParseNumbers.stringToInt("9abcdef", 16, 0));
		Assert.assertEquals(-0x9abcdef, ParseNumbers.stringToInt("-9abcdef", 16, 0));
		Assert.assertEquals(-0x9abcdef, ParseNumbers.stringToInt("-9ABCDEF", 16, 0));
	}

	@Test
	public void TestStringToIntWithFlag()
	{
		Assert.assertEquals(0, ParseNumbers.stringToInt("0", 10, ParseNumbers.NoSpace));
		Assert.assertEquals(1234567890, ParseNumbers.stringToInt("1234567890", 10, ParseNumbers.NoSpace));
		Assert.assertEquals(-1234567890, ParseNumbers.stringToInt("-1234567890", 10, ParseNumbers.NoSpace));

		Assert.assertEquals(0x0, ParseNumbers.stringToInt("0", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(0xA, ParseNumbers.stringToInt("a", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(0xA, ParseNumbers.stringToInt("A", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(0x12345678, ParseNumbers.stringToInt("12345678", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(-0x12345678, ParseNumbers.stringToInt("-12345678", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(0x9abcdef, ParseNumbers.stringToInt("9abcdef", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(-0x9abcdef, ParseNumbers.stringToInt("-9abcdef", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(-0x9abcdef, ParseNumbers.stringToInt("-9ABCDEF", 16, ParseNumbers.NoSpace));

		Assert.assertEquals(0, ParseNumbers.stringToInt("0 1", 10, ParseNumbers.NoSpace));
		Assert.assertEquals(1234567890, ParseNumbers.stringToInt("1234567890 123", 10, ParseNumbers.NoSpace));
		Assert.assertEquals(-1234567890, ParseNumbers.stringToInt("-1234567890 123", 10, ParseNumbers.NoSpace));

		Assert.assertEquals(0x0, ParseNumbers.stringToInt("0-2", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(0xA, ParseNumbers.stringToInt("a,b", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(0xA, ParseNumbers.stringToInt("A:1", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(0x12345678, ParseNumbers.stringToInt("12345678.5", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(-0x12345678, ParseNumbers.stringToInt("-12345678.6", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(0x9abcdef, ParseNumbers.stringToInt("9abcdef!1", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(-0x9abcdef, ParseNumbers.stringToInt("-9abcdefQ2", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(-0x9abcdef, ParseNumbers.stringToInt("-9ABCDEFs1", 16, ParseNumbers.NoSpace));
	}

	@Test
	public void TestStringToIntWithPos()
	{
		int[] pos = {0};
		int retVal = ParseNumbers.stringToInt("1234567890 345 678", 10, ParseNumbers.NoSpace, pos);
		Assert.assertEquals(1234567890, retVal);
		pos[0]++;
		retVal = ParseNumbers.stringToInt("1234567890 345 678", 10, ParseNumbers.NoSpace, pos);
		Assert.assertEquals(345, retVal);
		pos[0]++;
		retVal = ParseNumbers.stringToInt("1234567890 345 678", 10, ParseNumbers.NoSpace, pos);
		Assert.assertEquals(678, retVal);

		pos[0] = 0;
		retVal = ParseNumbers.stringToInt("12345-67890 abc,def+FEDsABC", 16, ParseNumbers.NoSpace, pos);
		Assert.assertEquals(0x12345, retVal);
		pos[0]++;
		retVal = ParseNumbers.stringToInt("12345-67890 abc,def+FEDsABC", 16, ParseNumbers.NoSpace, pos);
		Assert.assertEquals(0x67890, retVal);
		pos[0]++;
		retVal = ParseNumbers.stringToInt("12345-67890 abc,def+FEDsABC", 16, ParseNumbers.NoSpace, pos);
		Assert.assertEquals(0xabc, retVal);
		pos[0]++;
		retVal = ParseNumbers.stringToInt("12345-67890 abc,def+FEDsABC", 16, ParseNumbers.NoSpace, pos);
		Assert.assertEquals(0xdef, retVal);
		pos[0]++;
		retVal = ParseNumbers.stringToInt("12345-67890 abc,def+FEDsABC", 16, ParseNumbers.NoSpace, pos);
		Assert.assertEquals(0xFED, retVal);
		pos[0]+=3;
		retVal = ParseNumbers.stringToInt("12345-67890 abc,def+FEDsqwABC", 16, ParseNumbers.NoSpace, pos);
		Assert.assertEquals(0xABC, retVal);
	}

	@Test
	public void TestStringToLong()
	{
		Assert.assertEquals(0L, ParseNumbers.stringToLong("0", 10, 0));
		Assert.assertEquals(0L, ParseNumbers.stringToLong("-0", 10, 0));
		Assert.assertEquals(1234567890123L, ParseNumbers.stringToLong("1234567890123", 10, 0));
		Assert.assertEquals(-1234567890123L, ParseNumbers.stringToLong("-1234567890123", 10, 0));

		Assert.assertEquals(0x0L, ParseNumbers.stringToLong("0", 16, 0));
		Assert.assertEquals(0xAL, ParseNumbers.stringToLong("a", 16, 0));
		Assert.assertEquals(0xAL, ParseNumbers.stringToLong("A", 16, 0));
		Assert.assertEquals(0x12345678L, ParseNumbers.stringToLong("12345678", 16, 0));
		Assert.assertEquals(-0x12345678L, ParseNumbers.stringToLong("-12345678", 16, 0));
		Assert.assertEquals(0x9abcdefL, ParseNumbers.stringToLong("9abcdef", 16, 0));
		Assert.assertEquals(-0x9abcdefL, ParseNumbers.stringToLong("-9abcdef", 16, 0));
		Assert.assertEquals(-0x9abcdefL, ParseNumbers.stringToLong("-9ABCDEF", 16, 0));
	}

	@Test
	public void TestStringToLongWithFlag()
	{
		Assert.assertEquals(0, ParseNumbers.stringToLong("0", 10, ParseNumbers.NoSpace));
		Assert.assertEquals(1234567890, ParseNumbers.stringToLong("1234567890", 10, ParseNumbers.NoSpace));
		Assert.assertEquals(-1234567890, ParseNumbers.stringToLong("-1234567890", 10, ParseNumbers.NoSpace));

		Assert.assertEquals(0x0, ParseNumbers.stringToLong("0", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(0xA, ParseNumbers.stringToLong("a", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(0xA, ParseNumbers.stringToLong("A", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(0x12345678, ParseNumbers.stringToLong("12345678", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(-0x12345678, ParseNumbers.stringToLong("-12345678", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(0x9abcdef, ParseNumbers.stringToLong("9abcdef", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(-0x9abcdef, ParseNumbers.stringToLong("-9abcdef", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(-0x9abcdef, ParseNumbers.stringToLong("-9ABCDEF", 16, ParseNumbers.NoSpace));

		Assert.assertEquals(0, ParseNumbers.stringToLong("0 1", 10, ParseNumbers.NoSpace));
		Assert.assertEquals(1234567890, ParseNumbers.stringToLong("1234567890 123", 10, ParseNumbers.NoSpace));
		Assert.assertEquals(-1234567890, ParseNumbers.stringToLong("-1234567890 123", 10, ParseNumbers.NoSpace));

		Assert.assertEquals(0x0, ParseNumbers.stringToLong("0-2", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(0xA, ParseNumbers.stringToLong("a,b", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(0xA, ParseNumbers.stringToLong("A:1", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(0x12345678, ParseNumbers.stringToLong("12345678.5", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(-0x12345678, ParseNumbers.stringToLong("-12345678.6", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(0x9abcdef, ParseNumbers.stringToLong("9abcdef!1", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(-0x9abcdef, ParseNumbers.stringToLong("-9abcdefQ2", 16, ParseNumbers.NoSpace));
		Assert.assertEquals(-0x9abcdef, ParseNumbers.stringToLong("-9ABCDEFs1", 16, ParseNumbers.NoSpace));
	}

	@Test
	public void TestStringToLongWithPos()
	{
		int[] pos = {0};
		long retVal = ParseNumbers.stringToLong("1234567890 345 678", 10, ParseNumbers.NoSpace, pos);
		Assert.assertEquals(1234567890, retVal);
		pos[0]++;
		retVal = ParseNumbers.stringToLong("1234567890 345 678", 10, ParseNumbers.NoSpace, pos);
		Assert.assertEquals(345, retVal);
		pos[0]++;
		retVal = ParseNumbers.stringToLong("1234567890 345 678", 10, ParseNumbers.NoSpace, pos);
		Assert.assertEquals(678, retVal);

		pos[0] = 0;
		retVal = ParseNumbers.stringToLong("12345-67890 abc,def+FEDsABC", 16, ParseNumbers.NoSpace, pos);
		Assert.assertEquals(0x12345, retVal);
		pos[0]++;
		retVal = ParseNumbers.stringToLong("12345-67890 abc,def+FEDsABC", 16, ParseNumbers.NoSpace, pos);
		Assert.assertEquals(0x67890, retVal);
		pos[0]++;
		retVal = ParseNumbers.stringToLong("12345-67890 abc,def+FEDsABC", 16, ParseNumbers.NoSpace, pos);
		Assert.assertEquals(0xabc, retVal);
		pos[0]++;
		retVal = ParseNumbers.stringToLong("12345-67890 abc,def+FEDsABC", 16, ParseNumbers.NoSpace, pos);
		Assert.assertEquals(0xdef, retVal);
		pos[0]++;
		retVal = ParseNumbers.stringToLong("12345-67890 abc,def+FEDsABC", 16, ParseNumbers.NoSpace, pos);
		Assert.assertEquals(0xFED, retVal);
		pos[0]+=3;
		retVal = ParseNumbers.stringToLong("12345-67890 abc,def+FEDsqwABC", 16, ParseNumbers.NoSpace, pos);
		Assert.assertEquals(0xABC, retVal);
	}

	@Test
	public void TestIntToString()
	{
		Assert.assertEquals("1234567890", ParseNumbers.intToString(1234567890, 10, 10, 'x'));
		Assert.assertEquals("-1234567890", ParseNumbers.intToString(-1234567890, 10, 11, 'x'));

		Assert.assertEquals("67890", ParseNumbers.intToString(1234567890, 10, 5, 'x'));
		Assert.assertEquals("xx123", ParseNumbers.intToString(123, 10, 5, 'x'));

		Assert.assertEquals("abcdef", ParseNumbers.intToString(0xabcdef, 16, 6, 'x'));
		Assert.assertEquals("abcdef1", ParseNumbers.intToString(0xabcdef1, 16, 7, 'x'));
		Assert.assertEquals("-abcdef1", ParseNumbers.intToString(-0xabcdef1, 16, 8, 'x'));

		Assert.assertEquals("def", ParseNumbers.intToString(0xabcdef, 16, 3, 'x'));
		Assert.assertEquals("xxxabcdef1", ParseNumbers.intToString(0xabcdef1, 16, 10, 'x'));
		Assert.assertEquals("xx-abcdef1", ParseNumbers.intToString(-0xabcdef1, 16, 10, 'x'));
	}

	@Test
	public void TestLongToString()
	{
		Assert.assertEquals("123456789012345", ParseNumbers.longToString(123456789012345L, 10, 15, 'x'));
		Assert.assertEquals("-12345678901234", ParseNumbers.longToString(-12345678901234L, 10, 15, 'x'));

		Assert.assertEquals("67890", ParseNumbers.longToString(1234567890, 10, 5, 'x'));
		Assert.assertEquals("xx123", ParseNumbers.longToString(123, 10, 5, 'x'));

		Assert.assertEquals("abcdef123", ParseNumbers.longToString(0xabcdef123L, 16, 9, 'x'));
		Assert.assertEquals("abcdef1", ParseNumbers.longToString(0xabcdef1, 16, 7, 'x'));
		Assert.assertEquals("-abcdef123", ParseNumbers.longToString(-0xabcdef123L, 16, 10, 'x'));

		Assert.assertEquals("def", ParseNumbers.longToString(0xabcdef, 16, 3, 'x'));
		Assert.assertEquals("xxxabcdef1", ParseNumbers.longToString(0xabcdef1, 16, 10, 'x'));
		Assert.assertEquals("xx-abcdef1", ParseNumbers.longToString(-0xabcdef1, 16, 10, 'x'));
	}

	@Test
	public void TestIntRoundTrip()
	{
		int max = Integer.MAX_VALUE;
		int min = Integer.MIN_VALUE;
		int test;
		String str;

		str = ParseNumbers.intToString(max, 10, 10, '0');
		test = ParseNumbers.stringToInt(str, 10, 0);
		Assert.assertEquals(max, test);

		str = ParseNumbers.intToString(max, 16, 8, '0');
		test = ParseNumbers.stringToInt(str, 16, 0);
		Assert.assertEquals(max, test);

		str = ParseNumbers.intToString(min, 10, 11, '0');
		test = ParseNumbers.stringToInt(str, 10, 0);
		Assert.assertEquals(min, test);

		str = ParseNumbers.intToString(min, 16, 9, '0');
		test = ParseNumbers.stringToInt(str, 16, 0);
		Assert.assertEquals(min, test);
	}

	@Test
	public void TestLongRoundTrip()
	{
		long max = Long.MAX_VALUE;
		long min = Long.MIN_VALUE;
		long test;
		String str;

		str = ParseNumbers.longToString(max, 10, 19, '0');
		test = ParseNumbers.stringToLong(str, 10, 0);
		Assert.assertEquals(max, test);

		str = ParseNumbers.longToString(max, 16, 16, '0');
		test = ParseNumbers.stringToLong(str, 16, 0);
		Assert.assertEquals(max, test);

		str = ParseNumbers.longToString(min, 10, 20, '0');
		test = ParseNumbers.stringToLong(str, 10, 0);
		Assert.assertEquals(min, test);

		str = ParseNumbers.longToString(min, 16, 17, '0');
		test = ParseNumbers.stringToLong(str, 16, 0);
		Assert.assertEquals(min, test);
	}
}
