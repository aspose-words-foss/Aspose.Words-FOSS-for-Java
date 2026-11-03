// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/03/2021 by Dmitry Sokolov.

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


@Test
public class TestBigArray
{
    @Test (dataProvider = "testIndexerDataProvider")
    public void testIndexer(int length)
    {
        IntBigArray bigArray = new IntBigArray(length);

        // Setter.
        for (int i = 0; i < length; ++i)
            bigArray.set(i, i + 1);

        // Getter.
        for (int i = 0; i < length; ++i)
            msAssert.areEqual(i + 1, bigArray.get(i));

        msAssert.areEqual(length, bigArray.getLength());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testIndexerDataProvider")
	public static Object[][] testIndexerDataProvider()
	{
		return new Object[][]
		{
			{1},
			{65536},
			{65000},
		};
	}

    @Test (expectedExceptions = IllegalArgumentException.class, dataProvider = "testWrongSizeDataProvider")
    public void testWrongSize(int length)
    {
        new IntBigArray(length);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testWrongSizeDataProvider")
	public static Object[][] testWrongSizeDataProvider()
	{
		return new Object[][]
		{
			{0},
			{-1},
		};
	}
}

