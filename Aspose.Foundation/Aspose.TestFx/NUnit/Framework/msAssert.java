//Konstantin Sidorenko 22/01/2011

package com.aspose.ms.NUnit.Framework;

import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msArray;
import com.aspose.ms.System.msString;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class msAssert
{
	public static void fail(String message, /*params*/ Object... args)
	{
		Assert.fail(msString.format(message, args));
	}


	public static void isTrue(boolean condition)
	{
		Assert.assertTrue(condition);
	}

	public static void isTrue(boolean condition, String message)
	{
		Assert.assertTrue(condition, message);
	}

	public static void isTrue(boolean condition, String message, /*params*/ Object... args)
	{
		Assert.assertTrue(condition, msString.format(message, args));
	}

	public static void isFalse(boolean condition)
	{
		Assert.assertFalse(condition);
	}

	public static void isFalse(boolean condition, String message)
	{
		Assert.assertFalse(condition, message);
	}

	public static void isFalse(boolean condition, String message, /*params*/ Object... args)
	{
		Assert.assertFalse(condition, msString.format(message, args));
	}

	public static void isNull(Object anObject)
	{
		Assert.assertNull(anObject);
	}

	public static void isNull(Object anObject, String message)
	{
		Assert.assertNull(anObject, message);
	}

	public static void isNull(Object anObject, String message, /*params*/ Object... args)
	{
		Assert.assertNull(anObject, msString.format(message, args));
	}

	public static void isNullOrEmpty(String value)
	{
		Assert.assertTrue(value == null || "".equals(value));
	}

	public static void isNotNull(Object anObject)
	{
		Assert.assertNotNull(anObject, "");
	}
	public static void isNotNull(Object anObject, String message)
	{
		Assert.assertNotNull(anObject, message);
	}

	public static void isNotNull(Object anObject, String message, /*params*/ Object... args)
	{
		Assert.assertNotNull(anObject, msString.format(message, args));
	}

	public static void isNotNullOrEmpty(String value)
	{
		Assert.assertNotNull(value);
		Assert.assertNotEquals(value, "");
	}

	public static void isEmpty(Iterable collection)
	{
		boolean isEmpty = true;
		if (collection != null && collection.iterator() != null)
			isEmpty = !collection.iterator().hasNext();

		Assert.assertTrue(isEmpty);
	}

	public static void isEmpty(Iterable collection, String message, /*params*/ Object... args)
	{
		boolean isEmpty = true;
		if (collection != null && collection.iterator() != null)
			isEmpty = !collection.iterator().hasNext();

		Assert.assertTrue(isEmpty,msString.format(message, args));
	}

	public static void isEmpty(Object[] array)
	{
		Assert.assertTrue(array == null || array.length == 0);
	}

	public static void isEmpty(byte[] array, String message, /*params*/ Object... args)
	{
		Assert.assertTrue(array == null || array.length == 0,msString.format(message, args));
	}

	public static void isEmpty(String aString, String message, /*params*/ Object... args)
	{
		Assert.assertTrue(aString == null || aString.isEmpty(), msString.format(message, args));
	}

	public static void isEmpty(String aString)
	{
		Assert.assertTrue(aString == null || aString.isEmpty());
	}

	public static void isNotEmpty(byte[] array, String message, /*params*/ Object... args)
	{
		Assert.assertFalse(array == null || array.length == 0, msString.format(message, args));
	}

	public static void isNotEmpty(String aString)
	{
		Assert.assertFalse(aString == null || aString.isEmpty());
	}

	public static void isNotEmpty(Iterable collection)
	{
		boolean isEmpty = true;
		if (collection != null && collection.iterator() != null)
			isEmpty = !collection.iterator().hasNext();

		Assert.assertFalse(isEmpty);
	}

	// Original .net name was Zero()
	public static void isZero(int value)
	{
		Assert.assertTrue(value == 0);
	}

	public static void isZero(double value)
	{
		Assert.assertTrue(value == 0);
	}

	public static void isNotZero(int value)
	{
		Assert.assertTrue(value != 0);
	}

	public static void less(int arg1, int arg2)
	{
		Assert.assertTrue(arg1 < arg2, arg1 + " should be less than " + arg2);
	}

	public static void less(int arg1, int arg2, String message)
	{
		Assert.assertTrue(arg1 < arg2, message + ": " + arg1 + " should be less than " + arg2);
	}

	public static void less(int arg1, int arg2, String message, Object... args)
	{
		Assert.assertTrue(arg1 < arg2, msString.format(message, args));
	}

	public static void less(long arg1, long arg2)
	{
		Assert.assertTrue(arg1 < arg2, arg1 + " should be less than " + arg2);
	}

	public static void less(long arg1, long arg2, String message, Object... args)
	{
		Assert.assertTrue(arg1 < arg2, msString.format(message, args));
	}

	public static void less(float arg1, float arg2)
	{
		Assert.assertTrue(arg1 < arg2, arg1 + " should be less than " + arg2);
	}

	public static void less(double arg1, double arg2)
	{
		Assert.assertTrue(arg1 < arg2, arg1 + " should be less than " + arg2);
	}

	public static void less(double arg1, int arg2, String message)
	{
		Assert.assertTrue(arg1 < arg2, message + ": " + arg1 + " should be less than " + arg2);
	}

	public static void less(double arg1, double arg2, String message)
	{
		Assert.assertTrue(arg1 < arg2, message);
	}

	public static void less(double arg1, double arg2, String message, Object... args)
	{
		Assert.assertTrue(arg1 < arg2, msString.format(message, args));
	}

	public static void less(float arg1, float arg2, String message)
	{
		Assert.assertTrue(arg1 < arg2, message + ": " + arg1 + " should be less than " + arg2);
	}

	public static void less(float arg1, float arg2, String message, Object... args)
	{
		Assert.assertTrue(arg1 < arg2, msString.format(message, args));
	}

	public static void less(long arg1, long arg2, String message)
	{
		Assert.assertTrue(arg1 < arg2, message + ": " + arg1 + " should be less than " + arg2);
	}

	public static void less(long arg1, int arg2, String message)
	{
		Assert.assertTrue(arg1 < arg2, message + ": " + arg1 + " should be less than " + arg2);
	}

	public static void lessOrEqual(int arg1, int arg2)
	{
		Assert.assertTrue(arg1 <= arg2, arg1 + " should be less or equal than " + arg2);
	}

	public static void lessOrEqual(double arg1, double arg2)
	{
		Assert.assertTrue(arg1 <= arg2, arg1 + " should be less or equal than " + arg2);
	}

	public static void lessOrEqual(double arg1, int arg2)
	{
		Assert.assertTrue(arg1 <= arg2, arg1 + " should be less or equal than " + arg2);
	}

	public static void lessOrEqual(double arg1, double arg2, String message)
	{
		Assert.assertTrue(arg1 <= arg2, message + " : " + arg1 + " should be less or equal than " + arg2);
	}

	public static void lessOrEqual(double arg1, int arg2, String message)
	{
		Assert.assertTrue(arg1 <= arg2, message + " : " + arg1 + " should be less or equal than " + arg2);
	}

	public static void greater(int arg1, int arg2)
	{
		Assert.assertTrue(arg1 > arg2, arg1 + " should be greater than " + arg2);
	}

	public static void greater(double arg1, double arg2)
	{
		Assert.assertTrue(arg1 > arg2, arg1 + " should be greater than " + arg2);
	}

	public static void greater(double arg1, double arg2, String message, /*params*/ Object... args)
	{
		Assert.assertTrue(arg1 > arg2, msString.format(message, args));
	}

	public static void greater(long arg1, long arg2)
	{
		Assert.assertTrue(arg1 > arg2, arg1 + " should be greater than " + arg2);
	}

	public static void greater(int arg1, int arg2, String message)
	{
		Assert.assertTrue(arg1 > arg2, message);
	}

	public static void greater(int arg1, int arg2, String message, /*params*/ Object... args)
	{
		Assert.assertTrue(arg1 > arg2, msString.format(message, args));
	}

	public static void greaterOrEqual(int arg1, int arg2)
	{
		Assert.assertTrue(arg1 >= arg2, arg1 + " should be greater or equal than " + arg2);
	}

	public static void greaterOrEqual(int arg1, int arg2, String message)
	{
		Assert.assertTrue(arg1 >= arg2, message);
	}

	public static void greaterOrEqual(int arg1, int arg2, String message, /*params*/ Object... args)
	{
		Assert.assertTrue(arg1 >= arg2, msString.format(message, args));
	}

	public static void greaterOrEqual(double arg1, double arg2)
	{
		Assert.assertTrue(arg1 >= arg2, arg1 + " should be greater or equal than " + arg2);
	}

	public static void greaterOrEqual(double arg1, double arg2, String message)
	{
		Assert.assertTrue(arg1 >= arg2, message);
	}

	public static void greaterOrEqual(double arg1, double arg2, String message, /*params*/ Object... args)
	{
		Assert.assertTrue(arg1 >= arg2, msString.format(message, args));
	}

	//>>>>>>>>>>>>>> NUnit.Framework.Assert.AreEqual(Object,Object)

	//Note: TestNG Assert.assertEquals() doesn't work for primitive array types. It is because of java Object.equals()
	//doesn't work for these arrays. It is exactly TESTNG-359 Issue. But it reported 12/Jan/2010 (exactly
	//one year ago) and didn't fixed yet. So, will remove this workaround after TESTNG-359 will be fixed.

	//Note2: It is Highly Repetitive Code below! The body of int[] template repeated without changes
	//for boolean[], char[], short[], long[], float[] and double[].

	public static void areEqual(int[] expected, int[] actual)
	{
		areEqual(expected, actual, "");
	}

	public static void areEqual(int[] expected, int[] actual, String message)
	{
		Assert.assertTrue(Arrays.equals(expected, actual), message);
	}

	public static void areEqual(boolean[] expected, boolean[] actual)
	{
		areEqual(expected, actual, "");
	}

	public static void areEqual(boolean[] expected, boolean[] actual, String message)
	{
		Assert.assertTrue(Arrays.equals(expected, actual), message);
	}

	public static void areEqual(char[] expected, char[] actual)
	{
		areEqual(expected, actual, "");
	}

	public static void areEqual(char[] expected, char[] actual, String message)
	{
		Assert.assertTrue(Arrays.equals(expected, actual), message);
	}

	public static void areEqual(short[] expected, short[] actual)
	{
		areEqual(expected, actual, "");
	}

	public static void areEqual(short[] expected, short[] actual, String message)
	{
		Assert.assertTrue(Arrays.equals(expected, actual), message);
	}

	public static void areEqual(long[] expected, long[] actual)
	{
		areEqual(expected, actual, "");
	}

	public static void areEqual(long[] expected, long[] actual, String message)
	{
		Assert.assertTrue(Arrays.equals(expected, actual), message);
	}

	public static void areEqual(float[] expected, float[] actual)
	{
		areEqual(expected, actual, "");
	}

	public static void areEqual(float[] expected, float[] actual, String message)
	{
		Assert.assertTrue(Arrays.equals(expected, actual), message);
	}

	public static void areEqual(double[] expected, double[] actual)
	{
		areEqual(expected, actual, "");
	}

	public static void areEqual(double[] expected, double[] actual, String message)
	{
		Assert.assertTrue(Arrays.equals(expected, actual), message);
	}


	public static void areEqual(String[] expected, String[] actual)
	{
		Assert.assertEquals(actual, expected);
	}

	public static void areEqual(String[] expected, String[] actual, String message)
	{
		Assert.assertTrue(Arrays.equals(expected, actual), message);
	}

	//Different object types:
	//for instance, if expected object is int expression, but actual object is double -
	//TestNG assert will fail even if underlying values are equal.
	//It is because of java Double.equals(Object) is always false for non Double objects.
	//The same for Integer.equals(), etc.

	public static void areEqual(int expected, double actual)
	{
		Assert.assertTrue(expected == actual);
	}

	public static void areEqual(int expected, double actual, String message)
	{
		Assert.assertTrue(expected == actual, message);
	}

	public static void areEqual(double expected, int actual)
	{
		Assert.assertTrue(expected == actual);
	}

	public static void areEqual(double expected, int actual, String message)
	{
		Assert.assertTrue(expected == actual, message);
	}

	public static void areEqual(int expected, float actual)
	{
		Assert.assertTrue(expected == actual);
	}

	public static void areEqual(float expected, int actual, String message)
	{
		Assert.assertTrue(expected == actual, message);
	}

	public static void areEqual(int expected, long actual)
	{
		Assert.assertTrue(expected == (int) actual);
	}

	public static void areEqual(long expected, int actual)
	{
		Assert.assertTrue(expected == actual);
	}
	public static void areEqual(long expected, int actual, String message)
	{
		Assert.assertTrue(expected == actual, message);
	}

	public static void areEqual(long expected, long actual)
	{
		Assert.assertEquals(expected, actual);
	}

	// .Net strong autoboxing can silently turn Long to long,
	// but Autoporter will preserve this Long.
	public static void areEqual(long expected, Long actual)
	{
		Assert.assertEquals(expected, actual.longValue());
	}

	public static void areEqual(int expected, short actual)
	{
		Assert.assertTrue(expected == actual);
	}

	public static void areEqual(int expected, byte actual)
	{
		Assert.assertTrue(expected == (actual & 0xFF));
	}

	public static void areEqual(short expected, int actual, String message)
	{
		Assert.assertTrue(expected == actual, message);
	}

	public static void areEqual(float expected, float actual)
	{
		Assert.assertEquals(actual, expected, "\r\nexpected|actual: " + expected + "|" + actual);
	}

	public static void areEqual(float expected, float delta, float actual)
	{
		Assert.assertEquals(actual, expected, delta, "\r\nexpected|actual: " + expected + "|" + actual);
	}

	public static void areEqual(double expected, double delta, double actual)
	{
		Assert.assertEquals(actual, expected, delta, "\r\nexpected|actual: " + expected + "|" + actual);
	}

	public static void areEqual(double expected, double delta, double actual, String message)
	{
		Assert.assertEquals(actual, expected, delta, message);
	}

	public static void areEqual(double expected, int delta, double actual, String message)
	{
		Assert.assertEquals(actual, expected, delta, message);
	}

	public static void areEqual(float expected, double actual)
	{
		Assert.assertTrue(expected == actual, "\r\nexpected|actual: " + expected + "|" + actual);
	}

	public static void areEqual(long expected, long actual, String message)
	{
		Assert.assertEquals(expected , actual, message);
	}

	public static void areEqual(float expected, double actual, String message)
	{
		Assert.assertTrue(expected == actual, message);
	}

	public static void areEqual(String expected, String actual)
	{
		Assert.assertEquals(expected, actual);
	}

	public static void areEqual(String expected, String actual, String message)
	{
		Assert.assertEquals(expected, actual, message);
	}

	public static void areEqual(String expected, String actual, String message, Object... args)
	{
		Assert.assertEquals(expected, actual, msString.format(message, args));
	}

	public static void areEqual(byte expected, byte actual)
	{
		Assert.assertTrue(expected == actual, "\r\nexpected|actual: " + expected + "|" + actual);
	}

	public static void areEqual(int expected, int actual)
	{
		Assert.assertEquals(actual, expected);
	}

	public static void areEqual(int expected, int actual, String message)
	{
		Assert.assertEquals(actual, expected, message);
	}

	public static void areEqual(int expected, int delta, int actual, String message)
	{
		Assert.assertEquals(actual, expected, delta, message);
	}

	//the same as above for Assert.AreNotEqual(string,string)

	public static void areNotEqual(int expected, int actual, String mesage)
	{
		Assert.assertNotEquals(expected, actual, mesage);
	}

	public static void areNotEqual(Object expected, Object actual)
	{
		Assert.assertNotEquals(expected, actual, "Objects '" + expected + "' and '" + actual + "' should not be equal.");
	}

	public static void areNotEqual(String expected, String actual)
	{
		Assert.assertFalse(msString.equals(expected, actual));
	}

	public static void areNotEqual(String expected, String actual, String message)
	{
		Assert.assertFalse(msString.equals(expected, actual), message);
	}

	public static void areSame(Object expected, Object actual)
	{
		Assert.assertTrue(expected == actual);
	}

	public static void areSame(Object expected, Object actual, String message)
	{
		Assert.assertTrue(expected == actual, message);
	}

	public static void areSame(Object expected, Object actual, String message, Object... args)
	{
		Assert.assertTrue(expected == actual, msString.format(message, args));
	}

	public static void areNotSame(Object expected, Object actual)
	{
		Assert.assertNotSame(expected, actual);
	}

	public static void areNotSame(Object expected, Object actual, String message)
	{
		Assert.assertTrue( expected != actual, message);
	}

	public static void areNotSame(Object expected, Object actual, String message, Object... args)
	{
		Assert.assertTrue( expected != actual, msString.format(message, args));
	}

	// meet when compare enums: first arg is (former) enum const, second one is an object
	// (return value of getter) casted to enum (Integer in java).
	public static void areEqual(int expected, Integer actual)
	{
		Assert.assertEquals(expected, actual.intValue());
	}

	// the same as above with reversed arg types.
	public static void areEqual(Integer expected, int actual)
	{
		Assert.assertEquals(expected.intValue(), actual);
	}

	//All others works in TestNG as expected

	public static void areEqual(byte[] expected, byte[] actual)
	{
		Assert.assertEquals(actual, expected);
	}

	public static void areEqual(byte[] expected, byte[] actual, String message)
	{
		Assert.assertEquals(expected, actual, message);
	}

	public static void areEqual(int[] expected, short[] actual)
	{
		areEqual(actual, expected);
	}

	public static void areEqual(short[] expected, int[] actual)
	{
		if (null == expected)
			fail("expected a null array, but not null found. ");

		if (null == actual)
			fail("expected not null array, but null found. ");

		Assert.assertEquals(actual.length, expected.length, "arrays don't have the same size. ");

		for (int i = 0; i < expected.length; i++)
		{
			if (expected[i] != actual[i])
			{
				fail("arrays differ firstly at element [" + i + "]; "
						+ "expected value is <" + expected[i] + "> but was <"
						+ actual[i] + ">. ");
			}
		}
	}

	//a bag in Metaspec or .Net expected behaviour? Assert.AreEqual(int,int), (string,string) is
	//reported as Assert.AreEqual(object,object) if one of argument is expression. All works fine if
	//both arguments are literals, variables or constants.

	public static void areEqual(Object expected, Object actual)
	{
		areEqual(expected, actual,"Expected: " + expected + ". Actual: " + actual);
	}

	public static void areEqual(Object expected, Object actual,String message, Object... args)
	{
		if (args.length > 0)
			message = msString.format(message, args);

        if (expected instanceof Double)
        {
			areEqual(((Double) expected).doubleValue(),actual,message);
        }
		else if (expected instanceof Integer)
		{
			areEqual(((Integer) expected).intValue(),actual,message);
		}
		else if (expected instanceof Long)
		{
			areEqual(((Long) expected).longValue(),actual,message);
		}
		else if (expected instanceof String && !(actual instanceof String))
		{
			areEqual(((String) expected).toCharArray(), (byte[])actual, message);
		}
		else if (expected instanceof char[] && actual instanceof byte[])
		{
			areEqual((char[])expected, (byte[])actual, message);
		}
		else if (expected instanceof String[] && actual instanceof ArrayList)
		{
			areEqual((String[])expected, msArrayList.toArray((ArrayList<String>) actual, new String[]{}), message);
		}
		else if (expected instanceof int[] && actual instanceof ArrayList)
		{
			msCollectionAssert.areEqual((int[])expected, (Collection<Integer>)actual, message);
		}
        else
        {
            Assert.assertEquals(actual, expected, message);
        }
	}

	public static void areEqual(double expected, Object actual, String message)
	{
		double actualDouble = Double.NaN;

		if (actual instanceof Integer)
			actualDouble = ((Integer)actual).doubleValue();
		else if (actual instanceof Double)
			actualDouble = ((Double)actual).doubleValue();
		else if (actual instanceof Float)
			actualDouble = ((Float)actual).doubleValue();
		else if (actual instanceof Long)
			actualDouble = ((Long)actual).doubleValue();
		else if (actual instanceof Short)
			actualDouble = ((Short)actual).doubleValue();
		else if (actual instanceof Byte)
			actualDouble = ((Byte)actual).doubleValue();
		else
			Assert.assertFalse(true, message + "Can't compare expected double with actual " + actual.toString() );

		Assert.assertTrue(Double.compare(actualDouble, expected) == 0, message);
	}

	public static void areEqual(int expected, Object actual, String message)
	{
		int actualInt = 0;

		if (actual instanceof Integer)
			actualInt = ((Integer)actual).intValue();
		else if (actual instanceof Double)
			Assert.assertTrue(Double.compare(((Double)actual).doubleValue(), expected) == 0 ,message);
		else if (actual instanceof Float)
			Assert.assertTrue(Double.compare(((Float)actual).doubleValue(), expected) == 0 ,message);
		else if (actual instanceof Long)
			actualInt = ((Long)actual).intValue();
		else if (actual instanceof Short)
			actualInt = ((Short)actual).intValue();
		else if (actual instanceof Byte)
			actualInt = ((Byte)actual).intValue();
		else
			Assert.assertFalse(true, message + "Can't compare expected int with actual " + actual.toString());

		Assert.assertEquals(actualInt,expected,message);
	}

	public static void areEqual(long expected, Object actual, String message)
	{
		long actualLong = 0L;

		if (actual instanceof Integer)
			actualLong = ((Integer)actual).longValue();
		else if (actual instanceof Double)
			Assert.assertTrue(Double.compare(((Double)actual).doubleValue(), expected) == 0 ,message);
		else if (actual instanceof Float)
			Assert.assertTrue(Double.compare(((Float)actual).doubleValue(), expected) == 0 ,message);
		else if (actual instanceof Long)
			actualLong = ((Long)actual).longValue();
		else if (actual instanceof Short)
			actualLong = ((Short)actual).longValue();
		else if (actual instanceof Byte)
			actualLong = ((Byte)actual).longValue();
		else
			Assert.assertFalse(true, message + "Can't compare expected long with actual " + actual.toString());

		Assert.assertEquals(actualLong,expected,message);
	}

	public static void areEqual(char[] expected, byte[] actual, String message)
	{
		if(expected == null && actual == null)
			Assert.assertTrue(true);
		else if (actual.length == expected.length)
		{
			for(int i = 0; i < actual.length; i++)
			{
				if(actual[i] != expected[i] && expected[i] - 256 != actual[i])
					Assert.assertFalse(true, message);
			}

			Assert.assertTrue(true);
		}
		else
			Assert.assertFalse(true, message);
	}

	public static void isInstanceOf(Class expected, Object actual)
	{
		Assert.assertTrue(expected.isInstance(actual));
	}

	public static void isNotInstanceOf(Class expected, Object actual)
	{
		Assert.assertFalse(expected.isInstance(actual));
	}

	public static void isInstanceOf(Class expected, Object actual, String message)
	{
		Assert.assertTrue(expected.isInstance(actual), message);
	}

	public static void isInstanceOf(Class expected, Object actual, String message, Object... args)
	{
		Assert.assertTrue(expected.isInstance(actual), msString.format(message, args));
	}

	public static void isInstanceOfType(Class expected, Object actual)
	{
		Assert.assertTrue(expected.isInstance(actual));
	}

	public static void isInstanceOfType(Class expected, Object actual, String message)
	{
		Assert.assertTrue(expected.isInstance(actual), message);
	}

	public static void isInstanceOfType(Class expected, Object actual, String message, Object... args)
	{
		Assert.assertTrue(expected.isInstance(actual), msString.format(message, args));
	}

	public static void assertNotNull(Object o)
	{
		Assert.assertNotNull(o);
	}

	public static void assertTrue(boolean condition)
	{
		Assert.assertTrue(condition);
	}

	public static void assertTrue(boolean condition, String message)
	{
		Assert.assertTrue(condition, message);
	}

	public static void startWith(String prefix, String text)
	{
		Assert.assertTrue(text.startsWith(prefix));
	}
	public static void endWith(String prefix, String text)
	{
		Assert.assertTrue(text.endsWith(prefix));
	}

    public static void isInRange(int from, int to, double value)
    {
        Assert.assertTrue(value >= from && value <= to);
    }

    public static void areContainsSubstirng(String expectedText, String text)
    {
        Assert.assertTrue(text.contains(expectedText));
    }
}
