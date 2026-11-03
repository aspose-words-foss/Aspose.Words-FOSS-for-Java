// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/03/2018 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import com.aspose.HashSetGeneric;
import org.testng.annotations.DataProvider;


@Test
public class TestFieldCollection extends TestFieldsBase
{
    /// <summary>
    /// Tests <see cref="FieldCollection"/> functionality.
    /// </summary>
    @Test
    public void testFieldCollectionCore() throws Exception
    {
        Document doc = TestUtil.open("Fields\\Other\\TestGetFieldByFieldChar.docx");

        // Check that a non-composite node contains no fields.
        Node run = doc.getChild(NodeType.RUN, 0, true);
        msAssert.areEqual(0, run.getRange().getFields().getCount());

        // Check the initial field count in the document.
        FieldCollection fields = doc.getRange().getFields();
        msAssert.areEqual(45, fields.getCount());

        // Access the first picked field by the indexer and check it.
        Field firstField = fields.get(2);
        msAssert.isNotNull(firstField);
        msAssert.areEqual(FieldType.FIELD_SET, firstField.getType());

        // Access the last field by the indexer and check it.
        Field lastField = fields.get(44);
        msAssert.isNotNull(lastField);
        msAssert.areEqual(FieldType.FIELD_SYMBOL, lastField.getType());

        // Check field access by negative indexes.
        assertAreEqual(firstField, fields.get(-43));
        assertAreEqual(lastField, fields.get(-1));

        // Check field access by out-of-bounds indexes.
        msAssert.isNull(fields.get(45));
        msAssert.isNull(fields.get(-46));

        // Check enumeration.
        // 1. Check that all of the enumerated fields belong to the range.
        // 2. Check that each of them is met once per enumeration.
        // 3. Check their total count.
        ArrayList<Field> fieldsToCheck = FieldExtractor.extractToCollection(doc);
        msAssert.areEqual(45, fieldsToCheck.size());

        // Check field removal.
        Paragraph firstFieldParagraph = firstField.getStart().getParentParagraph();
        String firstFieldCode = firstField.getFieldCode();
        fields.remove(firstField);
        msAssert.areEqual(44, fields.getCount());
        assertAreEqual(lastField, fields.get(43));

        // Check field addition through the model and collection auto-update.
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveTo(firstFieldParagraph);
        firstField = builder.insertField(firstFieldCode);
        msAssert.areEqual(45, fields.getCount());
        assertAreEqual(firstField, fields.get(2));
        assertAreEqual(lastField, fields.get(44));

        // Check field removal by index.
        fields.removeAt(2);
        msAssert.areEqual(44, fields.getCount());
        assertAreEqual(lastField, fields.get(43));

        // Check collection clearing.
        fields.clear();
        msAssert.areEqual(0, fields.getCount());
    }

    @Test
    public void testIndexerAndEnumeratorEquality() throws Exception
    {
        Document document = TestUtil.open("Fields\\Other\\FieldCollection.docx");
        FieldCollection fields = document.getRange().getFields();

        int index = 0;
        for (Field field : fields)
        {
            Field fieldByIndex = fields.get(index);
            msAssert.areSame(field.getStart(), fieldByIndex.getStart());

            index++;
        }

        msAssert.areEqual(fields.getCount(), index);
    }

    @Test (dataProvider = "testLifeIndexerDataProvider")
    public void testLifeIndexer(/*FieldType*/int fieldType, int childFieldCount) throws Exception
    {
        Document document = TestUtil.open("Fields\\Other\\FieldCollection.docx");
        FieldCollection fields = document.getRange().getFields();
        int fieldsCount = fields.getCount();

        HashSetGeneric<FieldStart> iteratedFields = new HashSetGeneric<FieldStart>();
        int i = 0;
        while(i < fields.getCount())
        {
            Field field = fields.get(i);
            msAssert.isNotNull(field);

            msAssert.isFalse(iteratedFields.contains(field.getStart()));
            iteratedFields.add(field.getStart());

            if (field.getType() == fieldType)
            {
                field.remove();
                continue;
            }

            i++;
        }

        msAssert.areEqual(fieldsCount - childFieldCount, iteratedFields.getCount());
        msAssert.areEqual(fieldsCount - childFieldCount - 1, fields.getCount());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testLifeIndexerDataProvider")
	public static Object[][] testLifeIndexerDataProvider() throws Exception
	{
		return new Object[][]
		{
			{FieldType.FIELD_AUTHOR,  0},
			{FieldType.FIELD_IF,  6},
			{FieldType.FIELD_REF,  3},
		};
	}

    @Test (dataProvider = "testLifeEnumeratorDataProvider")
    public void testLifeEnumerator(/*FieldType*/int fieldType, int childFieldCount) throws Exception
    {
        Document document = TestUtil.open("Fields\\Other\\FieldCollection.docx");
        FieldCollection fields = document.getRange().getFields();
        int fieldsCount = fields.getCount();

        HashSetGeneric<FieldStart> iteratedFields = new HashSetGeneric<FieldStart>();
        for (Field field : fields)
        {
            msAssert.isNotNull(field);

            msAssert.isFalse(iteratedFields.contains(field.getStart()));
            iteratedFields.add(field.getStart());

            if (field.getType() == fieldType)
                field.remove();
        }

        msAssert.areEqual(fieldsCount - childFieldCount, iteratedFields.getCount());
        msAssert.areEqual(fieldsCount - childFieldCount - 1, fields.getCount());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testLifeEnumeratorDataProvider")
	public static Object[][] testLifeEnumeratorDataProvider() throws Exception
	{
		return new Object[][]
		{
			{FieldType.FIELD_AUTHOR,  0},
			{FieldType.FIELD_IF,  6},
			{FieldType.FIELD_REF,  3},
		};
	}
}

