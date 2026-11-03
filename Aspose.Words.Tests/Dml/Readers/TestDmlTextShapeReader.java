// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import com.aspose.ms.System.Guid;
import com.aspose.Language;


/// <summary>
/// Tests for <see cref="DmlTextShapeReader"/>.
/// </summary>
@Test
public class TestDmlTextShapeReader
{
  
    @Test
    public void build_BadXml_NullReturned() throws Exception
    {
        // Arrange
        String xml =
            "<a:Badsp xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "</a:Badsp>";
        // Act
        DmlTextShape result = read(xml);
        // Assert
        msAssert.isNull(result);
    }

    @Test
    public void build_UseSpRectAndTransform_PropertiesDefined() throws Exception
    {
        // Arrange
        String xml =
            "<txSp>" +
                "<xfrm>" +
                    "<ext cx=\"12\" cy=\"32\"/>" +
                    "<off x=\"54\" y=\"72\"/>" +
                "</xfrm>" +
                "<useSpRect/>" +
            "</txSp>";
        // Act
        DmlTextShape result = read(xml);
        // Assert
        DmlTransform transform = result.getTransform();
        msAssert.areEqual(12, transform.getWidth());
        msAssert.areEqual(32, transform.getHeight());
        msAssert.areEqual(54, transform.getX());
        msAssert.areEqual(72, transform.getY());
        msAssert.isTrue(result.getUseShapeTextRectangle());
    }

    @Test
    public void build_ParagraphStructure_ObjectsCreated() throws Exception
    {
        // Arrange
        String xml =
            "<txSp>" +
                "<txBody>" +
                    "<p>" +
                        "<r/>" +
                        "<br/>" +
                        "<fld/>" +
                        "<m/>" +
                    "</p>" +
                    "<p/>" +
                "</txBody>" +
            "</txSp>";
        // Act
        DmlTextShape result = read(xml);
        // Assert
        ArrayList<DmlParagraph> paragraphs = result.getTextBody().getParagraphs();
        msAssert.areEqual(2, paragraphs.size());
        DmlParagraph paragraph = (DmlParagraph)paragraphs.get(0);
        msAssert.isTrue(paragraph.getElements().get(0) instanceof DmlRun);
        msAssert.isTrue(paragraph.getElements().get(1) instanceof DmlTextBreakLine);
        msAssert.isTrue(paragraph.getElements().get(2) instanceof DmlTextField);
        msAssert.isTrue(paragraph.getElements().get(3) instanceof DmlTextMath);
    }

    @Test
    public void build_TextBodyProperties_PropertiesReaded() throws Exception
    {
        // Arrange
        String xml =
            "<txSp>" +
                "<txBody>" +
                    "<bodyPr anchor=\"ctr\" anchorCtr=\"1\" lIns=\"1\" tIns=\"2\" rIns=\"3\" bIns=\"4\" "+
                            "compatLnSpc=\"1\" forceAA=\"1\" fromWordArt=\"1\" horzOverflow=\"clip\" "+
                            "numCol=\"4\" rot=\"540000\" rtlCol=\"1\" spcCol=\"3\" spcFirstLastPara=\"1\" "+
                            "upright=\"1\" vert=\"wordArtVertRtl\" vertOverflow=\"clip\" wrap=\"none\">" +
                    "</bodyPr>" +
                "</txBody>" +
            "</txSp>";
        // Act
        DmlTextShape result = read(xml);
        // Assert
        DmlTextBodyProperties props = result.getTextBody().getProperties();
        msAssert.areEqual(DmlTextAnchoringType.CENTER, props.getAnchor());
        msAssert.areEqual(true, props.getAnchorCenter());
        msAssert.areEqual(1, props.getLeftInset());
        msAssert.areEqual(2, props.getTopInset());
        msAssert.areEqual(3, props.getRightInset());
        msAssert.areEqual(4, props.getBottomInset());
        msAssert.areEqual(true, props.getUseCompatibleLineSpacing());
        msAssert.areEqual(true, props.getForceAntiAlias());
        msAssert.areEqual(true, props.getFromWordArt());
        msAssert.areEqual(DmlTextHorizontalOverflowType.CLIP, props.getTextHorizontalOverflow());
        msAssert.areEqual(4, props.getColumnNumber());
        msAssert.areEqual(new DmlAngle(540000.0), props.getRotation());
        msAssert.areEqual(DmlTextColumnOrder.RIGHT_TO_LEFT, props.getColumnOrder());
        msAssert.areEqual(3, props.getSpaceBetweenColumns());
        msAssert.areEqual(true, props.getAreFirstAndLastParagraphsUseSpacing());
        msAssert.areEqual(true, props.isTextUpright());
        msAssert.areEqual(ShapeTextOrientation.WORD_ART_VERTICAL_RIGHT_TO_LEFT, props.getTextOrientation());
        msAssert.areEqual(DmlTextVerticalOverflowType.CLIP, props.getTextVerticalOverflow());
        msAssert.areEqual(TextBoxWrapMode.NONE, props.getTextWrappingType());
    }

    @Test
    public void build_ListStyles_ListStylesReaded() throws Exception
    {
        // Arrange
        String xml =
            "<txSp>" +
                "<txBody>" +
                    "<lstStyle>" +
                        "<defPPr lvl=\"8\"/>" +
                        "<lvl1pPr lvl=\"0\"/>" +
                        "<lvl2pPr lvl=\"1\"/>" +
                        "<lvl3pPr lvl=\"2\"/>" +
                        "<lvl4pPr lvl=\"3\"/>" +
                        "<lvl5pPr lvl=\"4\"/>" +
                        "<lvl6pPr lvl=\"5\"/>" +
                        "<lvl7pPr lvl=\"6\"/>" +
                        "<lvl8pPr lvl=\"7\"/>" +
                        "<lvl9pPr lvl=\"8\"/>" +
                    "</lstStyle>" +
                "</txBody>" +
            "</txSp>";
        // Act
        DmlTextShape result = read(xml);
        // Assert
        DmlTextListStyles styles = result.getTextBody().getTextListStyles();
        msAssert.areEqual(8, styles.getDefaultParagraphProperties().getLevel());
        for (int i = 0; i < 9; i++)
        {
            DmlParagraphProperties props = styles.getTextListStyle(i);
            msAssert.areEqual(i, props.getLevel());
        }

    }

    @Test
    public void build_RunDefined_RunCreated() throws Exception
    {
        // Arrange
        String xml =
            "<txSp>" +
                "<txBody>" +
                    "<p>" +
                        "<r>" +
                            "<rPr>" +
                            "</rPr>" +
                            "<t>" +
                                "Sample text" +
                            "</t>" +
                            "<t>" +
                                "Sample text" +
                            "</t>" +
                        "</r>" +
                    "</p>" +
                "</txBody>" +
            "</txSp>";
        // Act
        DmlTextShape result = read(xml);
        // Assert
        DmlParagraph paragraph = (DmlParagraph)result.getTextBody().getParagraphs().get(0);
        DmlRun run = (DmlRun)paragraph.getElements().get(0);
        msAssert.areEqual("Sample text" + "Sample text", run.getText());
        msAssert.isNotNull(run.getRunProperties());
    }

    @Test
    public void build_BreakLineDefined_BreakLineCreated() throws Exception
    {
        // Arrange
        String xml =
            "<txSp>" +
                "<txBody>" +
                    "<p>" +
                        "<br>" +
                            "<rPr>" +
                            "</rPr>" +
                        "</br>" +
                    "</p>" +
                "</txBody>" +
            "</txSp>";
        // Act
        DmlTextShape result = read(xml);
        // Assert
        DmlParagraph paragraph = (DmlParagraph)result.getTextBody().getParagraphs().get(0);
        DmlTextBreakLine breakLine = (DmlTextBreakLine)paragraph.getElements().get(0);
        msAssert.isNotNull(breakLine.getRunProperties());
    }

    @Test 
    public void build_TextFieldDefined_TextFieldCreated() throws Exception
    {
        // Arrange
        String xml =
            "<txSp>" +
                "<txBody>" +
                    "<p>" +
                        "<fld id=\"{7E6A6A03-33E7-441B-A8C6-65955AE3D3C3}\" type=\"yellow\">" +
                            "<rPr>" +
                            "</rPr>" +
                            "<pPr>" +
                            "</pPr>" +
                            "<t>" +
                                "Sample text" +
                            "</t>" +
                            "<t>" +
                                "Sample text" +
                            "</t>" +
                        "</fld>" +
                    "</p>" +
                "</txBody>" +
            "</txSp>";
        // Act
        DmlTextShape result = read(xml);
        // Assert
        DmlParagraph paragraph = (DmlParagraph)result.getTextBody().getParagraphs().get(0);
        DmlTextField field = (DmlTextField)paragraph.getElements().get(0);
        msAssert.isNotNull(field.getRunProperties());
        msAssert.isNotNull(field.getParagraphProperties());
        msAssert.areEqual("Sample text" + "Sample text", field.getText());
        msAssert.areEqual(new Guid("7E6A6A03-33E7-441B-A8C6-65955AE3D3C3"), field.getId());
        msAssert.areEqual("yellow", field.getType());
    }

    @Test
    public void build_ParagraphProperties_PropertiesReaded() throws Exception
    {
        // Arrange
        String xml =
            "<txSp>" +
                "<txBody>" +
                    "<p>" +
                        "<pPr algn=\"dist\" eaLnBrk=\"0\" defTabSz=\"7\" " +
                             "fontAlgn=\"ctr\" hangingPunct=\"1\" indent=\"8\" latinLnBrk=\"0\" lvl=\"6\" "+
                             "marL=\"5\" marR=\"4\" rtl=\"1\">" +
                             "<defRPr/>" +
                             "<lnSpc><spcPct val=\"101000\"/></lnSpc>" +
                             "<spcAft><spcPts val=\"100\"/></spcAft>" +
                             "<spcBef><spcPts val=\"200\"/></spcBef>" +
                             "<tabLst> + " +
                                "<tab pos = \"82550\" algn = \"dec\" />" +
                                "<tab pos = \"179388\" algn = \"ctr\" /> + " +
                             "</tabLst>" +
                        "</pPr>" +
                    "</p>" +
                "</txBody>" +
            "</txSp>";
        // Act
        DmlTextShape result = read(xml);
        // Assert
        DmlParagraph paragraph = (DmlParagraph)result.getTextBody().getParagraphs().get(0);
        DmlParagraphProperties props = paragraph.getProperties();
        msAssert.areEqual(ParagraphAlignment.DISTRIBUTED, props.getAlignment());
        msAssert.areEqual(false, props.isEastAsianLineBreakAllowed());
        msAssert.areEqual(7, props.getDefaultTabSize());
        msAssert.areEqual(DmlFontAlignment.CENTER, props.getFontAlignment());
        msAssert.areEqual(true, props.isHangingPunctuationAllowed());
        msAssert.areEqual(8, props.getTextIdentation());
        msAssert.areEqual(false, props.isLatinLineBreakAllowed());
        msAssert.areEqual(6, props.getLevel());
        msAssert.areEqual(5, props.getLeftMargin());
        msAssert.areEqual(4, props.getRightMargin());
        msAssert.areEqual(true, props.getRightToLeftFlowDirection());
        msAssert.isNotNull(props.getDefaultRunProperties());
        msAssert.areEqual(DmlPercentageUtil.fromDmlPercent(101000.0), ((DmlPercentageTextSpacing)props.getLineSpacing()).getValue());
        msAssert.areEqual(new DmlTextPoints(100), ((DmlPointsTextSpacing)props.getSpaceAfter()).getValue());
        msAssert.areEqual(new DmlTextPoints(200), ((DmlPointsTextSpacing)props.getSpaceBefore()).getValue());
        msAssert.isNotNull(props.getTabList());
        msAssert.areEqual(2, props.getTabList().getCount());
        msAssert.areEqual(82550, props.getTabList().get(0).getPositionTwips());
        msAssert.areEqual(TabAlignment.DECIMAL, props.getTabList().get(0).getAlignment());
        msAssert.areEqual(179388, props.getTabList().get(1).getPositionTwips());
        msAssert.areEqual(TabAlignment.CENTER, props.getTabList().get(1).getAlignment());

    }

    @Test
    public void build_RunProperties_PropertiesReaded() throws Exception
    {
        // Arrange
        String xml =
            "<txSp>" +
                "<txBody>" +
                    "<p>" +
                        "<r>" +
                            "<rPr altLang=\"ru-RU\" b=\"1\" baseline=\"100\" bmk=\"e\" cap=\"all\" " +
                                "dirty=\"1\" err=\"1\" i=\"true\" kern=\"100\" kumimoji=\"1\" lang=\"ru\" "+
                                "noProof=\"1\" normalizeH=\"1\" smtClean=\"1\" smtId=\"4\" spc=\"101\" "+
                                "strike=\"sngStrike\" sz=\"102\" u=\"dotDotDashHeavy\">" +
                                "<solidFill><schemeClr val=\"accent1\" /></solidFill>"+
                                "<ln/>" +
                                "<highlight><srgbClr val=\"FFFF00\"/></highlight>" +
                                "<uFill><solidFill><prstClr val=\"blue\" /></solidFill></uFill>" +
                                "<uLn><solidFill><prstClr val=\"red\" /></solidFill></uLn>" +
                                "<hlinkClick id=\"rId5\" tgtFrame=\"hlinkClickTgtFrame\" tooltip=\"hlinkClickTooltip\"/>" +
                                "<hlinkMouseOver id=\"rId6\" tgtFrame=\"hoverTgtFrame\" tooltip=\"hoverTooltip\"/>" +
                                "<rtl/>" +
                            "</rPr>" +
                        "</r>" +
                    "</p>" +
                "</txBody>" +
            "</txSp>";
        // Act
        DmlTextShape result = read(xml);
        // Assert
        DmlParagraph paragraph = (DmlParagraph)result.getTextBody().getParagraphs().get(0);
        DmlRun run = (DmlRun)paragraph.getElements().get(0);
        DmlRunProperties props = run.getRunProperties();
        msAssert.areEqual(Language.RUSSIAN_RUSSIA, props.getAlternativeLanguage());
        msAssert.areEqual(true, props.getBold());
        msAssert.areEqual(DmlPercentageUtil.fromDmlPercent(100.0), props.getBaseline());
        msAssert.areEqual("e", props.getBookmarkLinkTarget());
        msAssert.areEqual(DmlCapitalization.ALL, props.getCapitalization());
        msAssert.areEqual(true, props.isDirty());
        msAssert.areEqual(true, props.hasSpellingError());
        msAssert.areEqual(true, props.getItalics());
        msAssert.areEqual(new DmlTextPoints(100), props.getKerning());
        msAssert.areEqual(true, props.getKumimoji());
        msAssert.areEqual(Language.RUSSIAN, props.getLanguage());
        msAssert.areEqual(true, props.getNoProofing());
        msAssert.areEqual(true, props.getNormalizeHeights());
        msAssert.areEqual(true, props.getSmartTagsClean());
        msAssert.areEqual(4, props.getSmartTagID());
        msAssert.areEqual(new DmlTextPoints(101), props.getSpacing());
        msAssert.areEqual(DmlTextStrike.SINGLE, props.getStrikethrough());
        msAssert.areEqual(new DmlTextPoints(102), props.getFontSize());
        msAssert.areEqual(Underline.DOT_DOT_DASH_HEAVY, props.getUnderline());
        msAssert.isTrue(props.getFill() instanceof DmlSolidFill);
        msAssert.isNotNull(props.getOutline());
        msAssert.isTrue(props.getHighlightColor() instanceof DmlHexRgbColor);
        msAssert.isNotNull(props.getHlinkClick());
        DmlHlink hlinkClick = props.getHlinkClick();
        msAssert.areEqual("reltaget/rId5", hlinkClick.getId());
        msAssert.areEqual("hlinkClickTgtFrame", hlinkClick.getTargetFrame());
        msAssert.areEqual("hlinkClickTooltip", hlinkClick.getTooltip());
        DmlHlink hlinkHover = props.getHlinkHover();
        msAssert.areEqual("reltaget/rId6", hlinkHover.getId());
        msAssert.areEqual("hoverTgtFrame", hlinkHover.getTargetFrame());
        msAssert.areEqual("hoverTooltip", hlinkHover.getTooltip());
        msAssert.isTrue(props.getUnderlineFill() instanceof DmlSolidFill);
        msAssert.isTrue(props.getUnderlineStroke().getFill() instanceof DmlSolidFill);
        msAssert.isTrue(props.getRightToLeftFlowDirection());
    }

    @Test
    public void build_RunPropertiesWithEasternAsianFont_FontReaded() throws Exception
    {
        // Arrange
        String xml =
            "<txSp>" +
                "<txBody>" +
                    "<p>" +
                        "<r>" +
                            "<rPr>" +
                                "<ea typeface=\"Sample Font\" charset=\"1\" panose=\"0A\" pitchFamily=\"1\" />" +
                            "</rPr>" +
                        "</r>" +
                    "</p>" +
                "</txBody>" +
            "</txSp>";
        // Act
        DmlTextShape result = read(xml);
        // Assert
        DmlFont font = getFont(result, DmlFontType.EAST_ASIAN);
        checkFontProperties(font);
    }

    @Test
    public void build_RunPropertiesWithLatinFont_FontReaded() throws Exception
    {
        // Arrange
        String xml =
            "<txSp>" +
                "<txBody>" +
                    "<p>" +
                        "<r>" +
                            "<rPr>" +
                                "<latin typeface=\"Sample Font\" charset=\"1\" panose=\"0A\" pitchFamily=\"1\" />" +
                            "</rPr>" +
                        "</r>" +
                    "</p>" +
                "</txBody>" +
            "</txSp>";
        // Act
        DmlTextShape result = read(xml);
        // Assert
        DmlFont font = getFont(result, DmlFontType.LATIN);
        checkFontProperties(font);
    }

    @Test
    public void build_RunPropertiesWithSymbolFont_FontReaded() throws Exception
    {
        // Arrange
        String xml =
            "<txSp>" +
                "<txBody>" +
                    "<p>" +
                        "<r>" +
                            "<rPr>" +
                                "<sym typeface=\"Sample Font\" charset=\"1\" panose=\"0A\" pitchFamily=\"1\"/>" +
                            "</rPr>" +
                        "</r>" +
                    "</p>" +
                "</txBody>" +
            "</txSp>";
        // Act
        DmlTextShape result = read(xml);
        // Assert
        DmlFont font = getFont(result, DmlFontType.SYMBOL);
        checkFontProperties(font);
    }

    @Test
    public void build_RunPropertiesWithComplexScriptFont_FontReaded() throws Exception
    {
        // Arrange
        String xml =
            "<txSp>" +
                "<txBody>" +
                    "<p>" +
                        "<r>" +
                            "<rPr>" +
                                "<cs typeface=\"Sample Font\" charset=\"1\" panose=\"0A\" pitchFamily=\"1\"/>" +
                            "</rPr>" +
                        "</r>" +
                    "</p>" +
                "</txBody>" +
            "</txSp>";
        // Act
        DmlTextShape result = read(xml);
        // Assert
        DmlFont font = getFont(result, DmlFontType.COMPLEX_SCRIPT);
        checkFontProperties(font);
    }

    private DmlFont getFont(DmlTextShape textShape, /*DmlFontType*/int fontType)
    {
        DmlParagraph paragraph = (DmlParagraph)textShape.getTextBody().getParagraphs().get(0);
        DmlRun run = (DmlRun)paragraph.getElements().get(0);
        DmlRunProperties props = run.getRunProperties();
        return props.getFont(fontType);
    }

    private void checkFontProperties(DmlFont font)
    {
        msAssert.areEqual("Sample Font", font.getTextTypeface());
        msAssert.areEqual(1, font.getSimilarCharacterSet());
        msAssert.areEqual("0A", font.getPanoseSetting());
        msAssert.areEqual(1, font.getSimilarFontFamily());
    }

    /// <summary>
    /// WORDSNET-11899 DmlTextShapeReader.ReadFont hanged if there was an unknown attribute in font element.  
    /// WarnUnexcepted should be used instead of WarnUnexceptedAndIgnoreElement in attribute cycle.
    /// </summary>
    @Test
    public void runPropertiesWithFontUnknownAttribute_FontReaded() throws Exception
    {
        // Arrange
        String xml =
            "<txSp>" +
                "<txBody>" +
                    "<p>" +
                        "<r>" +
                            "<rPr>" +
                                "<cs charset1=\"1\" />" +   // unknown attribute charset1
                            "</rPr>" +
                        "</r>" +
                    "</p>" +
                "</txBody>" +
            "</txSp>";
        TestMaxWarningCountCallback warnCounter = new TestMaxWarningCountCallback(1);
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml, warnCounter);
        // Act
        DmlTextShapeReader.read(reader);
        // Assert
        msAssert.areEqual(1, warnCounter.getCount(), "Number of unknown nodes");
    }

    DmlTextShape read(String xml) throws Exception
    {
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        return DmlTextShapeReader.read(reader);
    }
}

