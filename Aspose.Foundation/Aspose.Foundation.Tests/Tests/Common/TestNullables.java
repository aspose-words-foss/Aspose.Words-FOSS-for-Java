// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/06/2020 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


@Test
public class TestNullables
{
    @Test
    public void testHasNotValue()
    {
        NullableInt32 nullable = NullableInt32.NULL;

        msAssert.isFalse(nullable.hasValue());
    }

    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QNullableInt32 doesn't have a value.\\E")
    public void testValueThrowsException()
    {
        NullableInt32 nullable = NullableInt32.NULL;

        int value = nullable.getValue();
    }

    //JAVA: method is internalized
	/*@Test*/
    void testHasValue(int value)
    {
        NullableInt32 nullable = new NullableInt32(value);

        msAssert.isTrue(nullable.hasValue());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "testHasValueSourceDataProvider")
	public void testHasValueSource(int value)
	{
		testHasValue(value);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "testHasValueSourceDataProvider")
	Object[][] testHasValueSourceDataProvider()
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(TEST_CASES_FOR_NULLABLES).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void testValue(int value)
    {
        NullableInt32 nullable = new NullableInt32(value);

        msAssert.areEqual(value, nullable.getValue());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "testValueSourceDataProvider")
	public void testValueSource(int value)
	{
		testValue(value);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "testValueSourceDataProvider")
	Object[][] testValueSourceDataProvider()
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(TEST_CASES_FOR_NULLABLES).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void testGetDefinedValue(int value)
    {
        NullableInt32 nullable = new NullableInt32(value);

        msAssert.areEqual(value, nullable.getValueOrDefault());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "testGetDefinedValueSourceDataProvider")
	public void testGetDefinedValueSource(int value)
	{
		testGetDefinedValue(value);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "testGetDefinedValueSourceDataProvider")
	Object[][] testGetDefinedValueSourceDataProvider()
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(TEST_CASES_FOR_NULLABLES).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    @Test
    public void testGetDefaultValue()
    {
        NullableInt32 nullable = NullableInt32.NULL;

        msAssert.areEqual(0, nullable.getValueOrDefault());
    }

    //JAVA: method is internalized
	/*@Test*/
    void testGetCustomDefaultValue(int value)
    {
        NullableInt32 nullable = NullableInt32.NULL;

        msAssert.areEqual(value, nullable.getValueOrDefault(value));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "testGetCustomDefaultValueSourceDataProvider")
	public void testGetCustomDefaultValueSource(int value)
	{
		testGetCustomDefaultValue(value);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "testGetCustomDefaultValueSourceDataProvider")
	Object[][] testGetCustomDefaultValueSourceDataProvider()
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(TEST_CASES_FOR_NULLABLES).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void testEqualsToPrimitive(int value)
    {
        NullableInt32 nullable = new NullableInt32(value);

        msAssert.isTrue(nullable.equals(value));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "testEqualsToPrimitiveSourceDataProvider")
	public void testEqualsToPrimitiveSource(int value)
	{
		testEqualsToPrimitive(value);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "testEqualsToPrimitiveSourceDataProvider")
	Object[][] testEqualsToPrimitiveSourceDataProvider()
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(TEST_CASES_FOR_NULLABLES).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void testEqualsToNullable(int value)
    {
        NullableInt32 nullable = new NullableInt32(value);
        NullableInt32 otherNullable = new NullableInt32(value);

        msAssert.isTrue(nullable.equals(otherNullable));
        msAssert.isTrue(otherNullable.equals(nullable));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "testEqualsToNullableSourceDataProvider")
	public void testEqualsToNullableSource(int value)
	{
		testEqualsToNullable(value);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "testEqualsToNullableSourceDataProvider")
	Object[][] testEqualsToNullableSourceDataProvider()
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(TEST_CASES_FOR_NULLABLES).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    @Test
    public void testNullEqualsToNull()
    {
        NullableInt32 nullable = NullableInt32.NULL;
        NullableInt32 otherNullable = NullableInt32.NULL;

        msAssert.isTrue(nullable.equals(otherNullable));
        msAssert.isTrue(otherNullable.equals(nullable));
    }

    //JAVA: method is internalized
	/*@Test*/
    void testNotEqualsToPrimitive(int value)
    {
        NullableInt32 nullable = new NullableInt32(value);

        msAssert.isFalse(nullable.equals(7));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "testNotEqualsToPrimitiveSourceDataProvider")
	public void testNotEqualsToPrimitiveSource(int value)
	{
		testNotEqualsToPrimitive(value);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "testNotEqualsToPrimitiveSourceDataProvider")
	Object[][] testNotEqualsToPrimitiveSourceDataProvider()
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(TEST_CASES_FOR_NULLABLES).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void testNotEqualsToNullable(int value)
    {
        NullableInt32 nullable = new NullableInt32(value);
        NullableInt32 otherNullable = new NullableInt32(7);

        msAssert.isFalse(nullable.equals(otherNullable));
        msAssert.isFalse(otherNullable.equals(nullable));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "testNotEqualsToNullableSourceDataProvider")
	public void testNotEqualsToNullableSource(int value)
	{
		testNotEqualsToNullable(value);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "testNotEqualsToNullableSourceDataProvider")
	Object[][] testNotEqualsToNullableSourceDataProvider()
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(TEST_CASES_FOR_NULLABLES).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void testNullNotEqualsToNotNull(int value)
    {
        NullableInt32 nullable = new NullableInt32(value);
        NullableInt32 otherNullable = NullableInt32.NULL;

        msAssert.isFalse(nullable.equals(otherNullable));
        msAssert.isFalse(otherNullable.equals(nullable));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "testNullNotEqualsToNotNullSourceDataProvider")
	public void testNullNotEqualsToNotNullSource(int value)
	{
		testNullNotEqualsToNotNull(value);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "testNullNotEqualsToNotNullSourceDataProvider")
	Object[][] testNullNotEqualsToNotNullSourceDataProvider()
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(TEST_CASES_FOR_NULLABLES).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void testHashCode(int value)
    {
        NullableInt32 nullable = new NullableInt32(value);
        NullableInt32 otherNullable = new NullableInt32(value);

        msAssert.isTrue(nullable.hashCode() == otherNullable.hashCode());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "testHashCodeSourceDataProvider")
	public void testHashCodeSource(int value)
	{
		testHashCode(value);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "testHashCodeSourceDataProvider")
	Object[][] testHashCodeSourceDataProvider()
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(TEST_CASES_FOR_NULLABLES).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    @Test
    public void testNullHashCode()
    {
        NullableInt32 nullable = NullableInt32.NULL;
        NullableInt32 otherNullable = NullableInt32.NULL;

        msAssert.isTrue(nullable.hashCode() == otherNullable.hashCode());
    }

    public static /*final*/ int[] TEST_CASES_FOR_NULLABLES = { Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 95, -32 };
}

