// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/01/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import org.testng.annotations.DataProvider;


@Test
public class TestFieldBuilder extends TestFieldBuilderBase
{
    private static /*final*/ ArrayList<Integer> G_UNSUPPORTED_FIELD_TYPES = G_UNSUPPORTED_FIELD_TYPESInitializer();
    /*JAVA-added private static Initializer*/
    private static ArrayList<Integer> G_UNSUPPORTED_FIELD_TYPESInitializer()
    {
        ArrayList<Integer> G_UNSUPPORTED_FIELD_TYPES = new ArrayList<Integer>();
    	{
        	G_UNSUPPORTED_FIELD_TYPES.add(FieldType.FIELD_CANNOT_PARSE);
        	G_UNSUPPORTED_FIELD_TYPES.add(FieldType.FIELD_HTML_ACTIVE_X);
        	G_UNSUPPORTED_FIELD_TYPES.add(FieldType.FIELD_REF_NO_KEYWORD);
    	}
        return G_UNSUPPORTED_FIELD_TYPES;
    }

    private static ArrayList<Integer> supportedFieldTypes()
    {
        ArrayList<Integer> result = new ArrayList<Integer>();

        for (/*FieldType*/int fieldType : FieldType.getValues())
        {
            if (G_UNSUPPORTED_FIELD_TYPES.contains(fieldType))
                continue;

            msArrayList.add(result, fieldType);
        }

        return result;
    }

    private static ArrayList<Integer> unsupportedFieldTypes()
    {
        ArrayList<Integer> result = new ArrayList<Integer>();

        for (/*FieldType*/int fieldType : (Iterable</*FieldType*/Integer>) G_UNSUPPORTED_FIELD_TYPES)
            msArrayList.add(result, fieldType);

        return result;
    }

    //JAVA: method is internalized
	/*@Test*/
    void builderShouldReturnFieldForSupportedTypes(/*FieldType*/int fieldType) throws Exception
    {
        FieldBuilder fieldBuilder = new FieldBuilder(fieldType);

        Field field = buildAndInsert(fieldBuilder);

        FieldAssert.field(field).ofType(fieldType);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "builderShouldReturnFieldForSupportedTypesSourceDataProvider")
	public void builderShouldReturnFieldForSupportedTypesSource(/*FieldType*/int fieldType) throws Exception
	{
		builderShouldReturnFieldForSupportedTypes(fieldType);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "builderShouldReturnFieldForSupportedTypesSourceDataProvider")
	Object[][] builderShouldReturnFieldForSupportedTypesSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = supportedFieldTypes().iterator();
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
	/*@Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "^Field type '.*' is not supported\\.$")*/
    void builderShouldThrowExceptionForUnsupportedTypes(/*FieldType*/int fieldType)
    {
        new FieldBuilder(fieldType);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "builderShouldThrowExceptionForUnsupportedTypesSourceDataProvider", expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "^Field type '.*' is not supported\\.$")
	public void builderShouldThrowExceptionForUnsupportedTypesSource(/*FieldType*/int fieldType)
	{
		builderShouldThrowExceptionForUnsupportedTypes(fieldType);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "builderShouldThrowExceptionForUnsupportedTypesSourceDataProvider")
	Object[][] builderShouldThrowExceptionForUnsupportedTypesSourceDataProvider()
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = unsupportedFieldTypes().iterator();
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
    public void builderShouldAllowMultipleBuilding() throws Exception
    {
        Document document = new Document();
        Paragraph paragraph = document.getFirstSection().getBody().getFirstParagraph();

        FieldBuilder fieldBuilder = new FieldBuilder(FieldType.FIELD_PAGE);

        Field firstField = fieldBuilder.buildAndInsert(paragraph);
        Field secondField = fieldBuilder.buildAndInsert(paragraph);

        msAssert.areNotSame(firstField, secondField);

        FieldAssert.field(firstField)
            .inside(paragraph)
            .between(null, secondField.getStart());

        FieldAssert.field(secondField)
            .inside(paragraph)
            .between(firstField.getEnd(), null);
    }
}

