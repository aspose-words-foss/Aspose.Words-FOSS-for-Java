// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/05/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests methods of the <see cref="DocxNamespaces"/> class.
/// </summary>
@Test
public class TestDocxNamespaces
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests namespace conversions performed by the methods <see cref="DocxNamespaces.ToIsoStrict"/> and
    /// <see cref="DocxNamespaces.ToIsoTransitional"/>.
    /// </summary>
    @Test (dataProvider = "testDocxNamespaceConversionDataProvider") // Casting for C++.
    public void testDocxNamespaceConversion(int value)
    {
        String strictNamespace = DocxNamespaces.getNamespace((/*DocxNamespace*/int)value, true);
        String transitionalNamespace = DocxNamespaces.getNamespace((/*DocxNamespace*/int)value, false);
        msAssert.areEqual(strictNamespace, DocxNamespaces.toIsoStrict(transitionalNamespace));
        msAssert.areEqual(transitionalNamespace, DocxNamespaces.toIsoTransitional(strictNamespace));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDocxNamespaceConversionDataProvider")
	public static Object[][] testDocxNamespaceConversionDataProvider()
	{
		return new Object[][]
		{
			{(int)DocxNamespace.MAIN},
			{(int)DocxNamespace.RELATIONSHIPS},
		};
	}
}

