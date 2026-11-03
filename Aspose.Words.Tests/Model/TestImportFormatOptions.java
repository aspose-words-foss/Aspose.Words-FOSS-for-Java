// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/12/2016 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests options of the <see cref="ImportFormatOptions"/> class.
/// </summary>
public class TestImportFormatOptions extends TestInsertDocumentBase
{


    /// <summary>
    /// Tests that when 'AdjustSentenceAndWordSpacing' option is enabled and content being inserted ends with SDT,
    /// Word does not merge lists even 'MergePastedLists' option is enabled.
    /// </summary>
    @Test
    public void testMergeListsLastSdt() throws Exception
    {
        DocumentBuilder builder = openDstDocument(TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM);
        Document srcDoc = openSrcDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA);

        // Source document ends with SDT.
        msAssert.areEqual(NodeType.STRUCTURED_DOCUMENT_TAG, srcDoc.getLastSection().getBody().getLastChild().getNodeType());

        ImportFormatOptions options = new ImportFormatOptions();
        options.setMergePastedLists(true);

        // Enable 'AdjustSentenceAndWordSpacing' option.
        options.setAdjustSentenceAndWordSpacing(true);
        Paragraph para = (Paragraph)builder.insertDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES, options);
        msAssert.isFalse(para.isListItem());

        builder = openDstDocument(TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM);

        // Disable 'AdjustSentenceAndWordSpacing' option.
        options.setAdjustSentenceAndWordSpacing(false);
        para = (Paragraph)builder.insertDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES, options);
        msAssert.isTrue(para.isListItem());

        // Append paragraph to the end of source document and check that 'AdjustSentenceAndWordSpacing' option does
        // not longer affect on how lists are merged.
        srcDoc.getLastSection().getBody().appendParagraph("some text");

        builder = openDstDocument(TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM);

        // Enable 'AdjustSentenceAndWordSpacing' option.
        options.setAdjustSentenceAndWordSpacing(true);
        para = (Paragraph)builder.insertDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES, options);
        msAssert.isTrue(para.isListItem());

        builder = openDstDocument(TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM);

        // Disable 'AdjustSentenceAndWordSpacing' option.
        options.setAdjustSentenceAndWordSpacing(false);
        para = (Paragraph)builder.insertDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES, options);
        msAssert.isTrue(para.isListItem());
    }

    /// <summary>
    /// Tests that source content that starts with a paragraph will be merged with a surrounding list only
    /// if it is inserted at the very beginning of a list item.
    /// </summary>
    @Test
    public void testMergeListsParagraph() throws Exception
    {
        ImportFormatOptions options = new ImportFormatOptions();
        options.setMergePastedLists(true);

        for (/*TestInsertDstDocLocation*/int location : getListItemLocations())
        {
            testInsertDocument(TestInsertSrcDocType.ONE_PARA_DOC, location, options);

            // Only inserting at the beginning of list item merges inserted paragraph with a list.
            boolean isListItem = (location == TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM);
            msAssert.areEqual(isListItem, getFirstInsertedPara().isListItem());
        }
    }

    /// <summary>
    /// Tests that source content that starts with a table will be merged with a surrounding list
    /// if it is inserted at any place inside list item. Also, only very first paragraph of inserted table is merged
    /// and all other not.
    /// </summary>
    @Test
    public void testMergeListsTable() throws Exception
    {
        ImportFormatOptions options = new ImportFormatOptions();
        options.setMergePastedLists(true);

        // Inserting at any place of list item merges first paragraph of inserted table with this list.
        for (/*TestInsertDstDocLocation*/int location : getListItemLocations())
        {
            testInsertDocument(TestInsertSrcDocType.TABLE_DOC_STARTS_WITH_TABLE, location, options);

            msAssert.isTrue(getFirstInsertedPara().isListItem());

            Cell secondCell = getFirstInsertedPara().getParentCell().getNextCell();
            msAssert.isFalse(secondCell.getFirstParagraph().isListItem());
        }
    }



    /// <summary>
    /// WORDSNET-24960 Allow ImportFormatOptions.AdjustSentenceAndWordSpacing option in API.
    /// Changed access to <see cref="ImportFormatOptions.AdjustSentenceAndWordSpacing"/> option to public.
    /// </summary>
    @Test (dataProvider = "test24960DataProvider")
    public void test24960(boolean isAdjust, char expectedChar) throws Exception
    {
        // Check the option is exposed publicly.

        // When the option is enabled we have to insert ' ' before inserted content.
        Document srcDoc = new Document();
        Document dstDoc = new Document();

        DocumentBuilder builder = new DocumentBuilder(srcDoc);
        builder.write("Dolor sit amet.");

        builder = new DocumentBuilder(dstDoc);
        builder.write("Lorem ipsum.");

        ImportFormatOptions options = new ImportFormatOptions(); { options.setAdjustSentenceAndWordSpacing(isAdjust); }
        Node node = builder.insertDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES, options);
        String text = node.getText();

        int insertedIdx = msString.indexOf(text, "Dolor sit amet.", StringComparison.INVARIANT_CULTURE);
        msAssert.areEqual(expectedChar, text.charAt(insertedIdx - 1));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test24960DataProvider")
	public static Object[][] test24960DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true,  ' '},
			{false,  '.'},
		};
	}

    /// <summary>
    /// A core method for testing KeepSourceNumbering importing option in <see cref="TestJira17534"/>.
    /// </summary>
    private static void testKeepSourceNumbering(ImportFormatOptions importFormatOptions, String[] expectedListLabels) throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\Nodes\\TestJira17534Src.docx");
        Document dstDoc = TestUtil.open("Model\\Nodes\\TestJira17534Dst.docx");

        NodeImporter importer = new NodeImporter(srcDoc, dstDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING, importFormatOptions);

        ParagraphCollection srcParas = srcDoc.getFirstSection().getBody().getParagraphs();
        for (Paragraph srcPara : (Iterable<Paragraph>) srcParas)
        {
            Node importedNode = importer.importNode(srcPara, false);
            dstDoc.getFirstSection().getBody().appendChild(importedNode);
        }

        dstDoc.updateListLabels();

        ParagraphCollection dstParas = dstDoc.getFirstSection().getBody().getParagraphs();
        for (int i = 0; i < expectedListLabels.length; i++)
            msAssert.areEqual(expectedListLabels[i], dstParas.get(i).getListLabel().getLabelString());
    }
}

