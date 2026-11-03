// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/08/2011 by Dmitry Matveenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.util.ArrayList;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestFieldRemover
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void testFieldRemoving() throws Exception
    {
        final String FILE_NAME = "Model\\Nodes\\TestFieldRemoving.docx";
        Document doc = TestUtil.open(FILE_NAME);

        NodeRange docRange = new NodeRange(doc, doc);
        FieldRemover.replaceFieldsWithResults(docRange, FieldRemoverDefaultFilter.INSTANCE);

        checkNoFieldNodes(doc);

        TestUtil.saveOpen(doc, FILE_NAME.replace(".doc", " Modified.doc"));
    }

    @Test
    public void testBadFieldRemoving() throws Exception
    {
        final String FILE_NAME = "Model\\Nodes\\TestBadFieldRemoving.docx";
        Document doc = TestUtil.open(FILE_NAME);

        // Insert extra separators.
        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        insertExtraSeparator(fields.get(0));
        insertExtraSeparator(fields.get(1));

        // Insert extra field nodes in the beginning.
        FieldEnd extraFieldEnd = new FieldEnd(doc, new RunPr(), FieldType.FIELD_TITLE, false);
        NodeCollection paragraphs = doc.getChildNodes(NodeType.PARAGRAPH, true);
        Paragraph firstParagraph = (Paragraph)paragraphs.get(0);
        firstParagraph.insertBefore(extraFieldEnd, firstParagraph.getFirstChild());
        FieldSeparator extraSeparator = new FieldSeparator(doc, new RunPr(), FieldType.FIELD_TIME);
        firstParagraph.insertAfter(extraSeparator, firstParagraph.getFirstChild());

        // Insert extra field nodes in the beginning.
        extraFieldEnd = new FieldEnd(doc, new RunPr(), FieldType.FIELD_TITLE, false);
        Paragraph lastParagraph = (Paragraph)paragraphs.get(paragraphs.getCount() - 1);
        lastParagraph.insertAfter(extraFieldEnd, lastParagraph.getLastChild());
        extraSeparator = new FieldSeparator(doc, new RunPr(), FieldType.FIELD_TIME);
        lastParagraph.insertAfter(extraSeparator, lastParagraph.getLastChild());

        NodeRange docRange = new NodeRange(doc, doc);
        FieldRemover.replaceFieldsWithResults(docRange, FieldRemoverRetainFwrFieldsFilter.INSTANCE);

        checkNoFieldNodes(doc);

        TestUtil.saveOpen(doc, FILE_NAME.replace(".doc", " Modified.doc"));
    }

    private static void checkNoFieldNodes(Document doc)
    {
        checkNoNodes(doc, NodeType.FIELD_START);
        checkNoNodes(doc, NodeType.FIELD_SEPARATOR);
        checkNoNodes(doc, NodeType.FIELD_END);
    }

    private static void checkNoNodes(Document doc, /*NodeType*/int nodeType)
    {
        msAssert.areEqual(0, doc.getChildNodes(nodeType, true).getCount());
    }


    private static void insertExtraSeparator(Field field)
    {
        FieldSeparator extraSeparator = new FieldSeparator(field.getDocument(), new RunPr(), FieldType.FIELD_FORMULA);
        field.getSeparator().insertNext(extraSeparator);
    }

    @Test
    public void testBoundaryFieldRemoving() throws Exception
    {
        final String FILE_NAME = "Model\\Nodes\\TestBoundaryFieldRemoving.docx";
        Document doc = TestUtil.open(FILE_NAME);

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);

        // Test removing of the first node of the interval.
        Field field = fields.get(0);
        Node rangeStart = field.getStart();
        Node rangeEnd = field.getEnd().getNextSibling();
        Node expectedRangeStart = field.getSeparator().getNextSibling();
        Node expectedRangeEnd = rangeEnd;
        NodeRange newRange = FieldRemover.replaceFieldsWithResults(new NodeRange(rangeStart, rangeEnd), FieldRemoverRetainFwrFieldsFilter.INSTANCE);
        msAssert.areSame(expectedRangeEnd, newRange.getEnd().getNode());
        msAssert.areSame(expectedRangeStart, newRange.getStart().getNode());

        // Test removing of the unfinished field.
        field = fields.get(1);
        rangeStart = field.getStart().getPreviousSibling();
        rangeEnd = field.getSeparator().getPreviousSibling();
        expectedRangeStart = rangeStart;
        expectedRangeEnd = rangeStart;
        newRange = FieldRemover.replaceFieldsWithResults(new NodeRange(rangeStart, rangeEnd), FieldRemoverRetainFwrFieldsFilter.INSTANCE);
        msAssert.areSame(expectedRangeEnd, newRange.getEnd().getNode());
        msAssert.areSame(expectedRangeStart, newRange.getStart().getNode());

        // Test removing of the last node of the interval.
        rangeStart = field.getSeparator();
        field = fields.get(2);
        rangeEnd = field.getEnd();
        expectedRangeStart = rangeStart.getNextSibling();
        expectedRangeEnd = rangeEnd.getPreviousSibling();
        newRange = FieldRemover.replaceFieldsWithResults(new NodeRange(rangeStart, rangeEnd), FieldRemoverRetainFwrFieldsFilter.INSTANCE);
        msAssert.areSame(expectedRangeEnd, newRange.getEnd().getNode());
        msAssert.areSame(expectedRangeStart, newRange.getStart().getNode());

        checkNoFieldNodes(doc);
    }

    @Test
    public void testRemoveAll() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Field fieldWithoutResult = builder.insertField("TC TocEntry");
        msAssert.isFalse(fieldWithoutResult.hasSeparator());

        NodeRange fieldRange = new NodeRange(fieldWithoutResult.getStart(), fieldWithoutResult.getEnd());
        NodeRange nullRange = FieldRemover.replaceFieldsWithResults(fieldRange, FieldRemoverDefaultFilter.INSTANCE);
        msAssert.isNull(nullRange);
    }

    @Test
    public void testNull() throws Exception
    {
        msAssert.isNull(FieldRemover.replaceFieldsWithResults(null, FieldRemoverRetainFwrFieldsFilter.INSTANCE));
    }
}

