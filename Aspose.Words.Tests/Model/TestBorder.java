// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;


/// <summary>
/// Tests Border type.
/// </summary>
@Test
public class TestBorder
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests border number according to ECMA $17.4.67 with
    /// Normative Variations 2.1.169 Part 4 Section 2.4.63, tcBorders (Table Cell Borders).
    /// </summary>
    @Test
    public void testBorderNumber()
    {
        msAssert.areEqual(1, getBorderNumber(LineStyle.SINGLE));
        msAssert.areEqual(2, getBorderNumber(LineStyle.THICK));
        msAssert.areEqual(3, getBorderNumber(LineStyle.DOUBLE));
        msAssert.areEqual(4, getBorderNumber(LineStyle.DOT));
        msAssert.areEqual(5, getBorderNumber(LineStyle.DASH_LARGE_GAP));
        msAssert.areEqual(8, getBorderNumber(LineStyle.DOT_DASH));
        msAssert.areEqual(9, getBorderNumber(LineStyle.DOT_DOT_DASH));
        msAssert.areEqual(10, getBorderNumber(LineStyle.TRIPLE));
        msAssert.areEqual(11, getBorderNumber(LineStyle.THIN_THICK_SMALL_GAP));
        msAssert.areEqual(12, getBorderNumber(LineStyle.THICK_THIN_SMALL_GAP));
        msAssert.areEqual(13, getBorderNumber(LineStyle.THIN_THICK_THIN_SMALL_GAP));
        msAssert.areEqual(14, getBorderNumber(LineStyle.THIN_THICK_MEDIUM_GAP));
        msAssert.areEqual(15, getBorderNumber(LineStyle.THICK_THIN_MEDIUM_GAP));
        msAssert.areEqual(16, getBorderNumber(LineStyle.THIN_THICK_THIN_MEDIUM_GAP));
        msAssert.areEqual(17, getBorderNumber(LineStyle.THIN_THICK_LARGE_GAP));
        msAssert.areEqual(18, getBorderNumber(LineStyle.THICK_THIN_LARGE_GAP));
        msAssert.areEqual(19, getBorderNumber(LineStyle.THIN_THICK_THIN_LARGE_GAP));
        msAssert.areEqual(20, getBorderNumber(LineStyle.WAVE));
        msAssert.areEqual(21, getBorderNumber(LineStyle.DOUBLE_WAVE));
        msAssert.areEqual(22, getBorderNumber(LineStyle.DASH_SMALL_GAP));
        msAssert.areEqual(23, getBorderNumber(LineStyle.DASH_DOT_STROKER));
        msAssert.areEqual(24, getBorderNumber(LineStyle.EMBOSS_3_D));
        msAssert.areEqual(25, getBorderNumber(LineStyle.ENGRAVE_3_D));
        msAssert.areEqual(26, getBorderNumber(LineStyle.OUTSET));
        msAssert.areEqual(27, getBorderNumber(LineStyle.INSET));

        msAssert.areEqual(0, getBorderNumber(LineStyle.NONE));

        // This is not in the ECMA for some reason. Looks like 1 is OK for Hairline.
        msAssert.areEqual(1, getBorderNumber(LineStyle.HAIRLINE));

        // Now a border that does not exist.
        msAssert.areEqual(0, getBorderNumber((/*LineStyle*/int)123456));
    }

    private static int getBorderNumber(/*LineStyle*/int lineStyle)
    {
        Border border = new Border();
        border.setLineStyle(lineStyle);
        return border.getBorderNumber();
    }

    /// <summary>
    /// Tests border weight according to according to ECMA $17.4.67 with
    /// Normative Variations 2.1.169 Part 4 Section 2.4.63, tcBorders (Table Cell Borders).
    /// </summary>
    @Test
    public void testBorderConflictWeight()
    {
        Border a = new Border();
        a.setLineStyle(LineStyle.TRIPLE);
        msAssert.areEqual(a.getRawLineWidth() * a.getBorderNumber(), a.getWeight());

        Border b = new Border();
        b.setLineStyle(LineStyle.WAVE);
        b.setLineWidth(a.getLineWidth()/2.0);
        // Wave border number is two times triple border number.
        msAssert.areEqual(b.getRawLineWidth() * b.getBorderNumber(), b.getWeight());

        // The winning border is taken by border number in case of equal weight.
        msAssert.areEqual(a.getWeight(), b.getWeight());
        msAssert.areSame(b, Border.getWinningBorder(a, b));
        msAssert.areSame(b, Border.getWinningBorder(b, a));
    }

    private static Border makeBorderWithLineStyle(/*LineStyle*/int lineStyle)
    {
        Border border = new Border();
        border.setLineWidth(1d);
        border.setLineStyle(lineStyle);
        return border;
    }

    /// <summary>
    /// Tests that Hairline behaves like Single.
    /// </summary>
    @Test
    public void testBorderWeightHairline()
    {
        Border none = makeBorderWithLineStyle(LineStyle.NONE);
        Border hairline = makeBorderWithLineStyle(LineStyle.HAIRLINE);
        Border single = makeBorderWithLineStyle(LineStyle.SINGLE);

        msAssert.areSame(hairline, Border.getWinningBorder(none, hairline));
        // We only differ Hairline from single by width. There is no Hairline in ECMA.
        msAssert.areSame(single, Border.getWinningBorder(single, hairline));
        msAssert.areSame(hairline, Border.getWinningBorder(hairline, single));
    }

    /// <summary>
    /// Test that Dashed and Dotted have weight == 1.
    /// </summary>
    @Test
    public void testBorderWeightDashedDotted()
    {
        Border none = makeBorderWithLineStyle(LineStyle.NONE);
        Border dotted = makeBorderWithLineStyle(LineStyle.DOT);
        Border dashed = makeBorderWithLineStyle(LineStyle.DASH_LARGE_GAP);
        Border single = makeBorderWithLineStyle(LineStyle.SINGLE);

        msAssert.areEqual(1, dotted.getWeight());
        msAssert.areEqual(1, dashed.getWeight());

        msAssert.areSame(dotted, Border.getWinningBorder(none, dotted));
        msAssert.areSame(dashed, Border.getWinningBorder(dotted, dashed));
        msAssert.areSame(single, Border.getWinningBorder(dashed, single));
    }

    /// <summary>
    /// Tests border conflict resolved via brightness.
    /// </summary>
    @Test
    public void testBorderConflictBrightness()
    {
        Border red = makeBorderWithColor(new Color((255), (0), (0)));
        Border green = makeBorderWithColor(new Color((0), (255), (0)));
        Border blue = makeBorderWithColor(new Color((0), (0), (255)));

        msAssert.areSame(blue, Border.getWinningBorder(blue, green));
        msAssert.areSame(red, Border.getWinningBorder(blue, red));

        Border redder = makeBorderWithColor(new Color((101), (100), (100)));
        Border bluer = makeBorderWithColor(new Color((100), (100), (101)));
        msAssert.areSame(redder, Border.getWinningBorder(bluer, redder));

        Border greenish = makeBorderWithColor(new Color((10), (200), (12)));
        Border greener = makeBorderWithColor(new Color((10), (201), (10)));
        msAssert.areSame(greenish, Border.getWinningBorder(greener, greenish));
    }

    private static Border makeBorderWithColor(Color color)
    {
        Border border = new Border();
        border.setLineStyle(LineStyle.SINGLE);
        border.setLineWidth(1d);
        border.setColor(color);
        return border;
    }

    /// <summary>
    /// Tests that first parameter wins for identical borders.
    /// </summary>
    @Test
    public void testBorderConflictOrder()
    {
        Border first = makeBorderWithColor(Color.RED);
        Border second = makeBorderWithColor(Color.RED);

        msAssert.areNotSame(first, second);
        msAssert.areSame(first, Border.getWinningBorder(first, second));
        msAssert.areSame(second, Border.getWinningBorder(second, first));
    }

    /// <summary>
    /// Tests that null and None loose in case of conflict.
    /// </summary>
    @Test
    public void testBorderConflictNull()
    {
        Border borderNull = null;
        Border borderNone = makeBorderWithLineStyle(LineStyle.NONE);
        Border borderNone2 = makeBorderWithLineStyle(LineStyle.NONE);

        msAssert.areSame(borderNone, Border.getWinningBorder(borderNull, borderNone));
        msAssert.areSame(borderNone, Border.getWinningBorder(borderNone, borderNull));
        msAssert.areSame(borderNone, Border.getWinningBorder(borderNone, borderNone2));
        msAssert.areSame(borderNone2, Border.getWinningBorder(borderNone2, borderNone));
    }

    /// <summary>
    /// WORDSNET-7706 Paragraph/Text Border is missing when saved to PDF, TIFF, XPS.
    /// The problem occurs because there is border with zero width.
    /// MS Word silently fixes borders with line style specified and zero widths, by assigning them the minimal widths of 1/4 pt.
    /// andrnosk: Fixed by assigning minimal (1/4pt) width in case when border style is specified and line widths equals zero.
    /// </summary>
    @Test
    public void testJira7706() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.getParagraphFormat().getBorders().getLeft().setLineStyle(LineStyle.SINGLE);
        builder.writeln("Hello World!");
        msAssert.areEqual(2, doc.getFirstSection().getBody().getFirstParagraph().getParagraphFormat().getBorders().getLeft().getWeight());
    }




    /// <summary>
    /// WORDSNET-11182 Table borders are missing after re-saving Docx
    /// Duplicates WORDSNET-11539
    /// </summary>
    @Test
    public void testJira11182() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Table\\TestJira11182.docx");

        // Verify that none of cells have Nil border.
        Table table = doc.getFirstSection().getBody().getTables().get(0);
        for (Row row : (Iterable<Row>) table.getRows())
            for(Cell cell : (Iterable<Cell>) row.getCells())
                for (int borderKey : CellPr.POSSIBLE_BORDER_KEYS.getValues())
                {
                    Border border = (Border)cell.getCellPr().get(borderKey);
                    if (border != null)
                    {
                        msAssert.isFalse(border.isNil());
                    }
                }
    }


    /// <summary>
    /// WORDSNET-4796 Table style cell borders override direct table formatting borders
    /// Duplicates WORDSNET-11539
    /// </summary>
    @Test
    public void testJira4796() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Table\\TestJira4796.docx");
    }

}

