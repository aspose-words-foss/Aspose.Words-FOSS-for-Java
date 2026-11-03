// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// The base class for testing markdown format import/export.
/// </summary>
public class TestMarkdownBase
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// The helper class for C++ auto-porter.
    /// </summary>
    public static class ExpectedStyleName
    {
        public String getStyleName() { return mStyleName; }; private void setStyleName(String value) { mStyleName = value; };

        private String mStyleName;
        public int getCount() { return mCount; }; private void setCount(int value) { mCount = value; };

        private int mCount;

        public ExpectedStyleName(String styleName, int count)
        {
            setStyleName(styleName);
            setCount(count);
        }
    }

    /// <summary>
    /// Verifies styles of a specified paragraph.
    /// </summary>
    protected static void checkStyles(Paragraph para, /*params*/ String... expectedStyleNames)
    {
        Style style = para.getParagraphStyle();
        for (String expectedStyleName : expectedStyleNames)
        {
            msAssert.areEqual(expectedStyleName, style.getName());
            style = style.getBaseStyle();
        }
    }

    /// <summary>
    /// Verifies paragraph.
    /// </summary>
    protected static void checkParagraph(Paragraph para, String expectedText, /*params*/ String... expectedStyleNames)
    {
        msAssert.isTrue(para.getText().startsWith(expectedText), msString.format("Expected:\t{0}\r\nBut was:\t{1}", expectedText, para.getText()));
        checkStyles(para, expectedStyleNames);
    }

    /// <summary>
    /// Verifies paragraph.
    /// </summary>
    protected static void checkParagraph(Paragraph para, String expectedText, ExpectedStyleName[] expectedStyleNames)
    {
        msAssert.areEqual(expectedText, para.getText());

        Style style = para.getParagraphStyle();
        for (ExpectedStyleName expected : expectedStyleNames)
        {
            for (int j = 0; j < expected.getCount(); j++)
            {
                msAssert.isTrue(style.getName().startsWith(expected.getStyleName()), msString.format("Expected: {0}. But was: {1}", expected.getStyleName(), style.getName()));
                style = style.getBaseStyle();
            }

        }
    }

    /// <summary>
    /// Verifies HorizontalRule.
    /// </summary>
    protected static void checkHorizontalRule(Paragraph para, /*params*/ String... expectedStyleNames)
    {
        checkStyles(para, expectedStyleNames);

        msAssert.areEqual(1, para.getCount());
        msAssert.isTrue(((Shape)para.getFirstChild()).getHorizontalRule().getOn());
    }

    /// <summary>
    /// Verifies list item.
    /// </summary>
    protected static void checkListItem(Paragraph para,
        String expectedText, int expectedListLevel, String expectedLabelString, /*params*/ String... expectedStyleNames)
    {
        checkParagraph(para, expectedText, expectedStyleNames);
        msAssert.areEqual(expectedListLevel, para.getParaPr().get(ParaAttr.LIST_LEVEL));
        msAssert.areEqual(expectedLabelString, para.getListLabel().getLabelString());
    }

    /// <summary>
    /// Verifies bullet list item.
    /// </summary>
    protected static void checkListItem(Paragraph para,
        int expectedListLevel, String expectedLabelString, /*params*/ String... expectedStyleNames)
    {
        checkStyles(para, expectedStyleNames);
        msAssert.areEqual(expectedListLevel, para.getParaPr().get(ParaAttr.LIST_LEVEL));
        msAssert.areEqual(expectedLabelString, para.getListLabel().getLabelString());
    }

    protected static void checkStyleList(Style style, int expectedLevel, int expectedStartAt, char expectedMarker)
    {
        msAssert.areEqual(expectedLevel, style.getParaPr().get(ParaAttr.LIST_LEVEL));

        List list = style.getListInternal();
        ListLevel listLevel = list.getListLevels().get(expectedLevel);
        msAssert.areEqual(expectedStartAt, listLevel.getStartAt());
        msAssert.areEqual(expectedMarker, listLevel.getNumberFormat().charAt(listLevel.getNumberFormat().length() - 1));
    }

    protected static NodeCollection checkFootnotes(CompositeNode compositeNode, /*params*/ String... expectedFootnotesText)
    {
        NodeCollection footnotes = compositeNode.getChildNodes(NodeType.FOOTNOTE, true);
        for (int i = 0; i < footnotes.getCount(); i++)
            msAssert.areEqual(expectedFootnotesText[i], ((Footnote)footnotes.get(i)).getText());

        msAssert.areEqual(expectedFootnotesText.length, footnotes.getCount());

        return footnotes;
    }

    /// <summary>
    /// Verifies run.
    /// </summary>
    protected static void checkRun(Run run, String expectedText, String expectedStyleName)
    {
        msAssert.areEqual(expectedText, run.getText());
        msAssert.areEqual(expectedStyleName, run.getCharacterStyle().getName());
    }

    /// <summary>
    /// Verifies run.
    /// </summary>
    protected static void checkRun(Run run, String expectedText, boolean isExpectedBold, boolean isExpectedItalic)
	{
		checkRun(run, expectedText, isExpectedBold, isExpectedItalic, false);
	}

    /// <summary>
    /// Verifies run.
    /// </summary>
    protected static void checkRun(Run run, String expectedText, boolean isExpectedBold, boolean isExpectedItalic,
        boolean isExpectedStrikeThrough)
    {
        msAssert.areEqual(expectedText, run.getText());
        checkRunAttr(run, isExpectedBold, isExpectedItalic, isExpectedStrikeThrough);
    }

    /// <summary>
    /// Verifies run.
    /// </summary>
    protected static void checkRun(Run run, String expectedText, String expectedStyleName,
        boolean isExpectedBold, boolean isExpectedItalic)
    {
        msAssert.areEqual(expectedText, run.getText());
        msAssert.areEqual(expectedStyleName, run.getCharacterStyle().getName());
        checkRunAttr(run, isExpectedBold, isExpectedItalic);
    }

    /// <summary>
    /// Verifies run.
    /// </summary>
    protected static void checkRunAttr(Run run,
        boolean isExpectedBold, boolean isExpectedItalic)
	{
		checkRunAttr(run, isExpectedBold, isExpectedItalic, false);
	}

    /// <summary>
    /// Verifies run.
    /// </summary>
    protected static void checkRunAttr(Run run,
        boolean isExpectedBold, boolean isExpectedItalic, boolean isExpectedStrikeThrough)
    {
        msAssert.areEqual(isExpectedBold, run.getRunPr().getBold().toBool());
        msAssert.areEqual(isExpectedItalic, run.getRunPr().getItalic().toBool());
        msAssert.areEqual(isExpectedStrikeThrough, run.getRunPr().getStrikeThrough().toBool());
    }

    /// <summary>
    /// Verifies row.
    /// </summary>
    protected static void checkRow(Row row, String[] expectedTextInCells)
    {
        msAssert.areEqual(row.getCount(), expectedTextInCells.length);
        for (int i = 0; i < row.getCells().getCount(); i++)
            msAssert.areEqual(expectedTextInCells[i], row.getCells().get(i).getText());
    }

    /// <summary>
    /// Gets first occurred paragraph with a specified text.
    /// </summary>
    protected static Paragraph getParagraphWithText(Document doc, String text)
    {
        return TestUtil.getParagraphWithText(doc, msString.format("...{0}...", text));
    }

    /// <summary>
    /// Gets run with a specified text.
    /// </summary>
    protected static Run getRunWithText(Paragraph para, String text)
    {
        return TestUtil.getRunWithText(para, msString.format("...{0}...", text));
    }

    /// <summary>
    /// Checks a shape.
    /// </summary>
    protected static void checkShape(Shape shape, String altText, String shapeUri) throws Exception
    {
        checkShape(shape, altText, shapeUri, "");
    }

    /// <summary>
    /// Checks a shape.
    /// </summary>
    protected static void checkShape(Shape shape, String altText, String shapeUri, String title) throws Exception
    {
        msAssert.areEqual(altText, shape.getAlternativeText());
        msAssert.areEqual(shapeUri, shape.getImageData().getSourceFullName());
        msAssert.areEqual(title, shape.getImageData().getTitle());
    }
}

