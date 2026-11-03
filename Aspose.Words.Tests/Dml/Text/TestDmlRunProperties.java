// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Drawing.FontStyle;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDmlRunProperties
{
    @Test
    public void getFontStyle_BoldProps_BoldFontStyleReturned()
    {
        // Arrange
        DmlRunProperties props = new DmlRunProperties();
        props.setBold(true);
        // Act
        checkFontStyle(FontStyle.BOLD, props);
    }

    @Test
    public void getFontStyle_DefaultProps_RegularFontStyleReturned()
    {
        // Arrange
        DmlRunProperties props = new DmlRunProperties();
        // Act
        checkFontStyle(FontStyle.REGULAR, props);
    }

    @Test
    public void getFontStyle_ItalicProps_ItalicsFontStyleReturned()
    {
        // Arrange
        DmlRunProperties props = new DmlRunProperties();
        props.setItalics(true);
        // Act
        checkFontStyle(FontStyle.ITALIC, props);
    }

    @Test
    public void getFontStyle_TextStrike_StrikeFontStyleReturned()
    {
        // Arrange
        DmlRunProperties props = new DmlRunProperties();
        props.setStrikethrough(DmlTextStrike.SINGLE);
        // Act
        checkFontStyle(FontStyle.STRIKEOUT, props);

        // currently we will render double strike as single strike. Later can be reworked
        // Arrange
        props.setStrikethrough(DmlTextStrike.DOUBLE);
        // Act
        checkFontStyle(FontStyle.STRIKEOUT, props);
    }

    @Test
    public void getFontStyle_UnderlineProps_UnderlineFontStyleReturned()
    {
        // Arrange
        DmlRunProperties props = new DmlRunProperties();
        props.setUnderline(Underline.WAVY_DOUBLE);
        // Act
        checkFontStyle(FontStyle.UNDERLINE, props);
    }

    /// <summary>
    /// WORDSNET-14139 Some DML run properties are not supported.
    /// Read/write was implemented.
    /// </summary>
    @Test
    public void testJira14139() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\DrawingML\\TestJira14139", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        DmlTextShape result = ((DmlShape)shape.getDmlNode()).getTextShape();

        DmlParagraph paragraph = (DmlParagraph)result.getTextBody().getParagraphs().get(0);
        DmlRun run = (DmlRun)paragraph.getElements().get(2);
        DmlRunProperties props = run.getRunProperties();
        msAssert.isTrue(props.getUnderlineFill() instanceof DmlSolidFill);
        msAssert.isTrue(props.getUnderlineStroke().getFill() instanceof DmlSolidFill);
        msAssert.isFalse(props.getUnderlineFillTx());
        msAssert.isFalse(props.getUnderlineStrokeTx());

        msAssert.isTrue(props.getRightToLeftFlowDirection());

        run = (DmlRun)paragraph.getElements().get(1);
        props = run.getRunProperties();
        msAssert.isTrue(props.getUnderlineFill() == null);
        msAssert.isTrue(props.getUnderlineStroke().getFill() instanceof DmlNoFill);
        msAssert.isTrue(props.getUnderlineFillTx());
        msAssert.isTrue(props.getUnderlineStrokeTx());

        msAssert.isFalse(props.getRightToLeftFlowDirection());
    }

    /// <summary>
    /// WORDSNET-15502 Corrupt output DOCX document containing SmartArt.
    /// On writing DmlFont, we must write "typeface" attribute despite the value is empty.
    /// </summary>
    @Test
    public void testJira15502() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\DrawingML\\TestJira15502", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        // The model of the document remains the same after the fix, so the only way to verify the fix
        // is to check content of the problematic package. 
        TestUtil.isWrittenToOpcPackage(doc.getOriginalFileName(), "/word/diagrams/data1.xml", "a:ea typeface");
    }

    private static void checkFontStyle(/*FontStyle*/int expectedFontStyle, DmlRunProperties props)
    {
        /*FontStyle*/int style = props.getFontStyle();
        // Assert
        msAssert.areEqual(expectedFontStyle, style);
    }
}

