// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests the <see cref="Aspose.Words.Tables.PreferredWidth"/> class.
/// </summary>
@Test
public class TestPreferredWidth
{
    @Test
    public void testCreateValid()
    {
        PreferredWidth w1 = PreferredWidth.fromPercent(100.0);
        msAssert.areEqual(PreferredWidthType.PERCENT, w1.getType());
        msAssert.areEqual(100, w1.getValue());

        PreferredWidth w2 = PreferredWidth.fromPoints(10.0);
        msAssert.areEqual(PreferredWidthType.POINTS, w2.getType());
        msAssert.areEqual(10, w2.getValue());
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testPercentNegative()
    {
        PreferredWidth.fromPercent(-0.1);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testPercentTooBig()
    {
        // alexnosk: I changed value from -100.1 here to 600.1 to test whether exception is still thrown.
        // Old value here was negative, that I suppose was a mistake.
        PreferredWidth.fromPercent(600.1);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testPointsNegative()
    {
        PreferredWidth.fromPoints(-1);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testPointsTooBig()
    {
        PreferredWidth.fromPoints(22 * 72 + 1);
    }

    @Test
    public void testEquality()
    {
        // Percent equality.
        PreferredWidth percent1 = PreferredWidth.fromPercent(28.8);    // This will create raw value 1440
        PreferredWidth percent2 = PreferredWidth.fromPercent(28.8);
        msAssert.areNotSame(percent1, percent2);
        msAssert.areEqual(percent1, percent2);
        msAssert.isFalse(percent1 == percent2);

        PreferredWidth percent3 = PreferredWidth.fromPercent(28.9);
        msAssert.areNotEqual(percent1, percent3);


        // Point equality
        PreferredWidth points1 = PreferredWidth.fromPoints(72.0); // This will create raw value 1440
        PreferredWidth points2 = PreferredWidth.fromPoints(72.0);
        msAssert.areNotSame(points1, points2);
        msAssert.areEqual(points1, points2);
        msAssert.isFalse(points1 == points2);

        PreferredWidth points3 = PreferredWidth.fromPoints(73.0);
        msAssert.areNotEqual(points1, points3);


        // When raw value is the same, the objects are not equal because type is different.
        msAssert.areEqual(1440, percent1.getValueRaw());
        msAssert.areEqual(1440, points1.getValueRaw());
        msAssert.areNotEqual(percent1, points1);

        // Auto
        PreferredWidth auto1 = PreferredWidth.AUTO;
        PreferredWidth auto2 = PreferredWidth.AUTO;
        msAssert.areSame(auto1, auto2);
        msAssert.isTrue(auto1 == auto2);
    }

    /// <summary>
    /// WORDSNET-5317 Long hyperlinks broke tables layout upon rendering.
    /// </summary>
    @Test
    public void testJira5317() throws Exception
    {
        DocumentBuilder docBuilder = new DocumentBuilder();

        // begin table
        Table table = docBuilder.startTable();

        // The first row
        docBuilder.getRowFormat().setHeight(0.0);

        // The first cell
        docBuilder.insertCell();

        table.setAllowAutoFit(true);


        docBuilder.getCellFormat().getBorders().setLineStyle(LineStyle.SINGLE);
        docBuilder.getCellFormat().getBorders().setLineWidth(1.0);
        docBuilder.getCellFormat().setPreferredWidth(PreferredWidth.AUTO);

        Style style = docBuilder.getDocument().getStyles().get("Hyperlink");
        Style oldStyle = docBuilder.getFont().getStyle();
        if (style != null)
        {
            docBuilder.getFont().setStyle(style);
        }

        docBuilder.insertHyperlink("text",
                                   "https://www.aspose.com/community/forums/aspose.words-product-family/75/showforum.aspx?PageIndex=1&sb=0&d=1&df=11&uf=0&hrp=0&lf=0",
                                   false);

        if (oldStyle != null)
        {
            docBuilder.getFont().setStyle(oldStyle);
        }


        docBuilder.insertCell();

        docBuilder.getCellFormat().setWidth(300.0);
        docBuilder.getCellFormat().setPreferredWidth(PreferredWidth.fromPoints(300.0));

        docBuilder.write("Lorem ipsum dolor sit amet");

        // The third cell
        docBuilder.insertCell();
        docBuilder.getCellFormat().setPreferredWidth(PreferredWidth.AUTO);

        docBuilder.write("Lorem ipsum dolor sit amet");

        // The fourth cell
        docBuilder.insertCell();
        docBuilder.getCellFormat().setPreferredWidth(PreferredWidth.AUTO);

        docBuilder.write("Lorem ipsum dolor sit amet");


        // End the first row
        docBuilder.endRow();

        // end table
        docBuilder.endTable();

        TestUtil.save(docBuilder.getDocument(), "Model\\Nodes\\TestJira5317.docx");
        // FOSS: Pdf rendering removed; the Docx save above is the no-crash check.
    }

    /// <summary>
    /// WORDSNET-5520 Exception on setting Table.PreferredWidth higher than 100 percent.
    /// </summary>
    @Test
    public void testDefect5520() throws Exception
    {
        final double PREF_WIDTH = 120.0;

        DocumentBuilder builder = new DocumentBuilder();
        Table table = builder.startTable();
        // Build a dummy table.
        for (int rowIdx = 0; rowIdx < 5; rowIdx++)
        {
            for (int cellIdx = 0; cellIdx < 5; cellIdx++)
            {
                builder.insertCell();
                builder.write("test table");
            }
            builder.endRow();
        }
        builder.endTable();

        // Set preferred width more that 100%.
        table.setPreferredWidth(PreferredWidth.fromPercent(PREF_WIDTH));

        // FOSS: Doc writer removed; roundtrip through Docx (no committed gold).
        Document doc = TestUtil.saveOpen(builder.getDocument(), "Model\\Nodes\\TestDefect5520.docx", (SaveOptions)null, false);

        // Make sure preferred width is preserved.
        table = (Table)doc.getChild(NodeType.TABLE, 0, true);
        msAssert.areEqual(PreferredWidth.fromPercent(PREF_WIDTH), table.getPreferredWidth());
    }
}

