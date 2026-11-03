//Konstantin Sidorenko 22/01/2011

package com.aspose.ms;

import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * NUnit.Framework.Assert.AreEqual(Object,Object) properly works with arrays of primitive types.
 * But java's TestNG Assert.assertEquals() - doesn't. The class tests our workaround.
 */
@Test
public class TestAssertAreEqualObject
{
	//int[]
	@Test
	public void TestAreEqualIntPass()
	{
		int[] a1 = new int[] {1, 2, 3, 4, 5};
		int[] a2 = new int[] {1, 2, 3, 4, 5};

		msAssert.areEqual(a1, a2);
		msAssert.areEqual(a1, a2, "should be equal");
	}

	@Test (expectedExceptions = java.lang.AssertionError.class)
	public void TestAreEqualIntFail1()
	{
		int[] a1 = new int[] {1, 2, 3, 4, 5};
		int[] a2 = new int[] {5, 4, 3, 2, 1};

		msAssert.areEqual(a1, a2);
	}

	@Test (expectedExceptions = java.lang.AssertionError.class)
	public void TestAreEqualIntFail2()
	{
		int[] a1 = new int[] {1, 2, 3, 4, 5};
		int[] a2 = new int[] {5, 4, 3, 2, 1};

		msAssert.areEqual(a1, a2, "should be equal");
	}

	//short[]

	@Test
	public void TestAreEqualShortPass()
	{
		short[] a1 = new short[] {1, 2, 3, 4, 5};
		short[] a2 = new short[] {1, 2, 3, 4, 5};

		msAssert.areEqual(a1, a2);
		msAssert.areEqual(a1, a2, "should be equal");
	}

	@Test (expectedExceptions = java.lang.AssertionError.class)
	public void TestAreEqualShortFail1()
	{
		short[] a1 = new short[] {1, 2, 3, 4, 5};
		short[] a2 = new short[] {5, 4, 3, 2, 1};

		msAssert.areEqual(a1, a2);
	}

	@Test (expectedExceptions = java.lang.AssertionError.class)
	public void TestAreEqualShortFail2()
	{
		short[] a1 = new short[] {1, 2, 3, 4, 5};
		short[] a2 = new short[] {5, 4, 3, 2, 1};

		msAssert.areEqual(a1, a2, "should be equal");
	}

	//long[]

	@Test
	public void TestAreEqualLongPass()
	{
		long[] a1 = new long[] {1, 2, 3, 4, 5};
		long[] a2 = new long[] {1, 2, 3, 4, 5};

		msAssert.areEqual(a1, a2);
		msAssert.areEqual(a1, a2, "should be equal");
	}

	@Test (expectedExceptions = java.lang.AssertionError.class)
	public void TestAreEqualLongFail1()
	{
		long[] a1 = new long[] {1, 2, 3, 4, 5};
		long[] a2 = new long[] {5, 4, 3, 2, 1};

		msAssert.areEqual(a1, a2);
	}

	@Test (expectedExceptions = java.lang.AssertionError.class)
	public void TestAreEqualLongFail2()
	{
		long[] a1 = new long[] {1, 2, 3, 4, 5};
		long[] a2 = new long[] {5, 4, 3, 2, 1};

		msAssert.areEqual(a1, a2, "should be equal");
	}

	//char[]

	@Test
	public void TestAreEqualCharPass()
	{
		char[] a1 = new char[] {1, 2, 3, 4, 5};
		char[] a2 = new char[] {1, 2, 3, 4, 5};

		msAssert.areEqual(a1, a2);
		msAssert.areEqual(a1, a2, "should be equal");
	}

	@Test (expectedExceptions = java.lang.AssertionError.class)
	public void TestAreEqualCharFail1()
	{
		char[] a1 = new char[] {1, 2, 3, 4, 5};
		char[] a2 = new char[] {5, 4, 3, 2, 1};

		msAssert.areEqual(a1, a2);
	}

	@Test (expectedExceptions = java.lang.AssertionError.class)
	public void TestAreEqualCharFail2()
	{
		char[] a1 = new char[] {1, 2, 3, 4, 5};
		char[] a2 = new char[] {5, 4, 3, 2, 1};

		msAssert.areEqual(a1, a2, "should be equal");
	}

	//byte[]
	//byte array is a special case: it works in TestNG as expected.
	@Test
	public void TestAreEqualBytePass()
	{
		byte[] a1 = new byte[] {1, 2, 3, 4, 5};
		byte[] a2 = new byte[] {1, 2, 3, 4, 5};

		msAssert.areEqual(a1, a2);
		msAssert.areEqual(a1, a2, "should be equal");
	}

	@Test (expectedExceptions = java.lang.AssertionError.class)
	public void TestAreEqualByteFail1()
	{
		byte[] a1 = new byte[] {1, 2, 3, 4, 5};
		byte[] a2 = new byte[] {5, 4, 3, 2, 1};

		msAssert.areEqual(a1, a2);
	}

	@Test (expectedExceptions = java.lang.AssertionError.class)
	public void TestAreEqualByteFail2()
	{
		byte[] a1 = new byte[] {1, 2, 3, 4, 5};
		byte[] a2 = new byte[] {5, 4, 3, 2, 1};

		msAssert.areEqual(a1, a2, "should be equal");
	}

	//Object

	@Test
	public void TestAreEqualObjectPass()
	{
		Object a1 = "a";
		Object a2 = "a";

		msAssert.areEqual(a1, a2);
		msAssert.areEqual(a1, a2, "should be equal");
	}

	@Test (expectedExceptions = java.lang.AssertionError.class)
	public void TestAreEqualObjectFail1()
	{
		Object a1 = "a";
		Object a2 = "b";

		msAssert.areEqual(a1, a2);
	}

	@Test (expectedExceptions = java.lang.AssertionError.class)
	public void TestAreEqualObjectFail2()
	{
		Object a1 = "a";
		Object a2 = "b";

		msAssert.areEqual(a1, a2, "should be equal");
	}

	//int vs double

	@Test
	public void TestIntEqualsDouble()
	{
		int i = 3 * 5;
		double d = 15.0;

		Assert.assertTrue(i == d);
		msAssert.areEqual(i, d);
		msAssert.areEqual(3 * 5, d);
	}
}
