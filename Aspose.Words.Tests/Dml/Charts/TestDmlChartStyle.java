// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/05/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.File;
import java.util.ArrayList;
import com.aspose.StringToObjDictionary;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Xml.msXmlNode;


/// <summary>
/// Tests color style and style parts of a chart.
/// </summary>
@Test
public class TestDmlChartStyle
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests reading a color style part of a char.
    /// </summary>
    @Test
    public void testReadingColorStyle() throws Exception
    {
        DmlChartColorStyle colorStyle = loadColorStyleFromXml();
        checkColorStyleFromXml(colorStyle);
    }

    /// <summary>
    /// Tests cloning objects of the <see cref="DmlChartColorStyle"/> class.
    /// </summary>
    @Test
    public void testCloningColorStyle() throws Exception
    {
        DmlChartColorStyle colorStyle = loadColorStyleFromXml();
        checkColorStyleFromXml(colorStyle); // check to exclude reader errors
        DmlChartColorStyle clonedColorStyle = colorStyle.deepClone();

        // Changing properties of the source object to check that the cloned object is not changed.
        colorStyle.setMethod("withinLinear");
        colorStyle.setId("50");

        ((DmlSchemeColor)colorStyle.getColors()[0]).setValue(ThemeColor.DARK_1);
        colorStyle.getColors()[1] =  new DmlPresetColor("red");

        DmlChartColorStyleVariation[] variations = colorStyle.getVariations();
        DmlLuminanceModulation luminanceModulation =  new DmlLuminanceModulation();
        luminanceModulation.setValue(0.8d);
        variations[0].getColorModifiers().add(luminanceModulation);
        ((DmlLuminanceModulation)variations[1].getColorModifiers().get(0)).setValue(0.99d);

        variations[2] = new DmlChartColorStyleVariation();

        colorStyle.getExtensions().get(DmlExtensionUri.THEME_FAMILY).setUri("");
        colorStyle.getExtensions().clear();

        checkColorStyleFromXml(clonedColorStyle);
    }

    /// <summary>
    /// Reads color style from the predefined test XML.
    /// </summary>
    private static DmlChartColorStyle loadColorStyleFromXml() throws Exception
    {
        String xml =
            "<cs:colorStyle " +
                "xmlns:cs=\"http://schemas.microsoft.com/office/drawing/2012/chartStyle\" " +
                "xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" meth=\"cycle\" id=\"10\"> " +
                "<a:schemeClr val=\"accent1\"/> " +
                "<a:schemeClr val=\"accent6\"/> " +
                "<cs:variation/> " +
                "<cs:variation> " +
                    "<a:lumMod val=\"60000\"/> " +
                "</cs:variation> " +
                "<cs:variation> " +
                    "<a:lumMod val=\"80000\"/> " +
                    "<a:lumOff val=\"20000\"/> " +
                "</cs:variation> " +
                "<cs:extLst> " +
                    "<a:ext uri=\"{05A4C25C-085E-4340-85A3-A5531E510DB2}\"> " +
                        "<thm15:themeFamily id=\"{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}\" " +
                            "name=\"Office Theme\" vid=\"{4A3C46E8-61CC-4603-A589-7422A47A8E4A}\" " +
                            "xmlns:thm15=\"http://schemas.microsoft.com/office/thememl/2012/main\" /> " +
                    "</a:ext> " +
                "</cs:extLst> " +
            "</cs:colorStyle>";
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        DmlChartColorStyle colorStyle = DmlChartColorStyleReader.read(reader);
        return colorStyle;
    }

    /// <summary>
    /// Checks color style that is loaded by the <see cref="LoadColorStyleFromXml"/> method.
    /// </summary>
    private static void checkColorStyleFromXml(DmlChartColorStyle colorStyle)
    {
        msAssert.isNotNull(colorStyle);
        msAssert.areEqual("cycle", colorStyle.getMethod());
        msAssert.areEqual("10", colorStyle.getId());

        DmlColor[] colors = colorStyle.getColors();
        msAssert.isNotNull(colors);
        msAssert.areEqual(2, colors.length);

        msAssert.areEqual(DmlColorType.SCHEME_COLOR, colors[0].getColorType());
        msAssert.areEqual(ThemeColor.ACCENT_1, ((DmlSchemeColor)colors[0]).getValue());

        msAssert.areEqual(DmlColorType.SCHEME_COLOR, colors[1].getColorType());
        msAssert.areEqual(ThemeColor.ACCENT_6, ((DmlSchemeColor)colors[1]).getValue());

        DmlChartColorStyleVariation[] variations = colorStyle.getVariations();
        msAssert.isNotNull(variations);
        msAssert.areEqual(3, variations.length);

        msAssert.areEqual(0, variations[0].getColorModifiers().size());

        msAssert.areEqual(1, variations[1].getColorModifiers().size());
        DmlColorModifier modifier = (DmlColorModifier)variations[1].getColorModifiers().get(0);
        msAssert.areEqual(DmlColorModifierType.LUMINANCE_MODULATION, modifier.getModifierType());
        msAssert.areEqual(0.6d, ((DmlLuminanceModulation)modifier).getValue());

        msAssert.areEqual(2, variations[2].getColorModifiers().size());
        modifier = (DmlColorModifier)variations[2].getColorModifiers().get(0);
        msAssert.areEqual(DmlColorModifierType.LUMINANCE_MODULATION, modifier.getModifierType());
        msAssert.areEqual(0.8d, ((DmlLuminanceModulation)modifier).getValue());
        modifier = (DmlColorModifier)variations[2].getColorModifiers().get(1);
        msAssert.areEqual(DmlColorModifierType.LUMINANCE_OFFSET, modifier.getModifierType());
        msAssert.areEqual(0.2d, ((DmlLuminanceOffset)modifier).getValue());

        msAssert.isNotNull(colorStyle.getExtensions());
        msAssert.areEqual(1, colorStyle.getExtensions().getCount());
        msAssert.areEqual(DmlExtensionUri.THEME_FAMILY, colorStyle.getExtensions().get(DmlExtensionUri.THEME_FAMILY).getUri());
    }

    /// <summary>
    /// Tests reading a style part of a char.
    /// </summary>
    @Test
    public void testReadingStyle() throws Exception
    {
        DmlChartStyle style = loadStyleFromXml();
        checkStyleFromXml(style);
    }

    /// <summary>
    /// Tests cloning objects of the <see cref="DmlChartStyle"/> class.
    /// </summary>
    @Test
    public void testCloningStyle() throws Exception
    {
        DmlChartStyle style = loadStyleFromXml();
        checkStyleFromXml(style); // check to exclude reader errors
        DmlChartStyle clonedStyle = style.deepClone();

        // Changing properties of the source object to check that the cloned object is not changed.
        DmlChartStyleEntry axisTitle = style.getByDmlChartStyleItem(DmlChartStyleItem.AXIS_TITLE);

        axisTitle.getModifiers()[0] = "";

        axisTitle.getLineReference().setStyleMatrixIndex(axisTitle.getLineReference().getStyleMatrixIndex() * 2);
        axisTitle.setLineWidthScale(axisTitle.getLineWidthScale() * 2.0);
        axisTitle.getFillReference().setStyleMatrixIndex(axisTitle.getFillReference().getStyleMatrixIndex() * 2);
        axisTitle.getEffectReference().setStyleMatrixIndex(axisTitle.getEffectReference().getStyleMatrixIndex() * 2);
        axisTitle.getFontReference().setFontCollectionIndex(DmlFontCollectionIndex.MAJOR);

        ((DmlSolidFill)axisTitle.getShapePr().getOutline().getFill()).getColor().getColorModifiers().clear();

        axisTitle.getDefaultRunPr().setKerning(new DmlTextPoints(0));
        axisTitle.getTextBodyPr().setRightInset(axisTitle.getTextBodyPr().getRightInset() * 2);
        axisTitle.getExtensions().get(DmlExtensionUri.HIDDEN_EFFECTS).setUri("");

        DmlChartStyleReference styleReference = style.getByDmlChartStyleItem(DmlChartStyleItem.CATEGORY_AXIS).getFillReference();
        styleReference.getModifiers()[0] = "none";
        styleReference.setStyleMatrixIndex(styleReference.getStyleMatrixIndex() * 2);
        DmlSchemeColor schemeColor = (DmlSchemeColor)styleReference.getColor();
        ((DmlLuminanceModulation)schemeColor.getColorModifiers().get(0)).setValue(((DmlLuminanceModulation)schemeColor.getColorModifiers().get(0)).getValue() * 2.0);
        schemeColor.getColorModifiers().remove(1);

        DmlChartFontReference fontReference = style.getByDmlChartStyleItem(DmlChartStyleItem.CATEGORY_AXIS).getFontReference();
        fontReference.getModifiers()[0] = "";
        fontReference.setFontCollectionIndex(DmlFontCollectionIndex.MINOR);
        DmlChartStyleColor styleColor = (DmlChartStyleColor)fontReference.getColor();
        styleColor.setValue("0");
        ((DmlLuminanceModulation)styleColor.getColorModifiers().get(0)).setValue(((DmlLuminanceModulation)styleColor.getColorModifiers().get(0)).getValue() + 0.2d);
        styleColor.getColorModifiers().remove(1);

        style.getDataPointMarkerLayout().setSymbol("dash");
        style.getDataPointMarkerLayout().setSize(style.getDataPointMarkerLayout().getSize() * 2);

        for (/*DmlChartStyleItem*/int index : DmlChartStyle.getAllStyleItems())
        {
            style.getByDmlChartStyleItem(index).getLineReference().setStyleMatrixIndex(style.getByDmlChartStyleItem(index).getLineReference().getStyleMatrixIndex() * 2);
            style.getByDmlChartStyleItem(index).getFillReference().setStyleMatrixIndex(style.getByDmlChartStyleItem(index).getFillReference().getStyleMatrixIndex() * 2);
            style.getByDmlChartStyleItem(index).getEffectReference().setStyleMatrixIndex(style.getByDmlChartStyleItem(index).getEffectReference().getStyleMatrixIndex() * 2);
        }

        style.getExtensions().get(DmlExtensionUri.THEME_FAMILY).setUri("");
        style.getExtensions().clear();

        style.setId("1");

        checkStyleFromXml(clonedStyle);
    }

    /// <summary>
    /// Reads a style part from the predefined test XML.
    /// </summary>
    private static DmlChartStyle loadStyleFromXml() throws Exception
    {
        String xml = File.readAllText(TestUtil.getInTestDataPath("ImportDocx\\ChartStylePart.xml"));
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);

        DmlChartStyle style = DmlChartStyleReader.read(reader);
        return style;
    }

    /// <summary>
    /// Checks a style part that is loaded by the <see cref="LoadStyleFromXml"/> method.
    /// </summary>
    private static void checkStyleFromXml(DmlChartStyle style)
    {
        // Test reading properties of the DmlChartStyleEntry class. And DmlChartStyle.AxisTitle.
        DmlChartStyleEntry axisTitle = style.getByDmlChartStyleItem(DmlChartStyleItem.AXIS_TITLE);

        String[] modifiers = axisTitle.getModifiers();
        msAssert.areEqual(2, modifiers.length);
        msAssert.areEqual("allowNoFillOverride", modifiers[0]);
        msAssert.areEqual("allowNoLineOverride", modifiers[1]);

        msAssert.areEqual(1, axisTitle.getLineReference().getStyleMatrixIndex());
        msAssert.areEqual(1.5d, axisTitle.getLineWidthScale());
        msAssert.areEqual(2, axisTitle.getFillReference().getStyleMatrixIndex());
        msAssert.areEqual(3, axisTitle.getEffectReference().getStyleMatrixIndex());
        msAssert.areEqual(DmlFontCollectionIndex.MINOR, axisTitle.getFontReference().getFontCollectionIndex());

        DefaultShapeProperties shapePr = axisTitle.getShapePr();
        msAssert.isNotNull(shapePr);
        msAssert.areEqual(DmlFillType.SOLID_FILL, shapePr.getOutline().getFill().getDmlFillType());
        msAssert.areEqual(2, ((DmlSolidFill)shapePr.getOutline().getFill()).getColor().getColorModifiers().size());

        msAssert.isNotNull(axisTitle.getDefaultRunPr());
        msAssert.areEqual(1100, axisTitle.getDefaultRunPr().getKerning().getValue());

        msAssert.isNotNull(axisTitle.getTextBodyPr());
       // Assert.AreEqual(10, axisTitle.TextBodyPr.Rotation.Value);
        msAssert.areEqual(36576, axisTitle.getTextBodyPr().getRightInset());

        msAssert.isNotNull(axisTitle.getExtensions());
        msAssert.areEqual(DmlExtensionUri.HIDDEN_EFFECTS, axisTitle.getExtensions().get(DmlExtensionUri.HIDDEN_EFFECTS).getUri());

        // Test reading properties of the DmlChartStyleReference class. And DmlChartStyle.CategoryAxis.
        DmlChartStyleReference styleReference = style.getByDmlChartStyleItem(DmlChartStyleItem.CATEGORY_AXIS).getFillReference();

        modifiers = styleReference.getModifiers();
        msAssert.areEqual(2, modifiers.length);
        msAssert.areEqual("ignoreCSTransforms", modifiers[0]);
        msAssert.areEqual("auto", modifiers[1]);

        msAssert.areEqual(1, styleReference.getStyleMatrixIndex());

        msAssert.areEqual(DmlColorType.SCHEME_COLOR, styleReference.getColor().getColorType());
        DmlSchemeColor schemeColor = (DmlSchemeColor)styleReference.getColor();
        ArrayList<IDmlColorModifier> colorModifiers = schemeColor.getColorModifiers();
        msAssert.areEqual(2, colorModifiers.size());
        msAssert.areEqual(DmlColorModifierType.LUMINANCE_MODULATION, ((DmlColorModifier)colorModifiers.get(0)).getModifierType());
        msAssert.areEqual(0.5d, ((DmlLuminanceModulation)colorModifiers.get(0)).getValue());
        msAssert.areEqual(DmlColorModifierType.LUMINANCE_OFFSET, ((DmlColorModifier)colorModifiers.get(1)).getModifierType());
        msAssert.areEqual(0.5d, ((DmlLuminanceOffset)colorModifiers.get(1)).getValue());

        // Test reading properties of the DmlChartFontReference and DmlChartStyleColor classes.
        DmlChartFontReference fontReference = style.getByDmlChartStyleItem(DmlChartStyleItem.CATEGORY_AXIS).getFontReference();

        modifiers = fontReference.getModifiers();
        msAssert.areEqual(2, modifiers.length);
        msAssert.areEqual("auto", modifiers[0]);
        msAssert.areEqual("ignoreCSTransforms", modifiers[1]);

        msAssert.areEqual(DmlFontCollectionIndex.MAJOR, fontReference.getFontCollectionIndex());

        msAssert.areEqual(DmlColorType.CHART_STYLE_COLOR, fontReference.getColor().getColorType());
        DmlChartStyleColor styleColor = (DmlChartStyleColor)fontReference.getColor();
        msAssert.areEqual("auto", styleColor.getValue());
        colorModifiers = styleColor.getColorModifiers();
        msAssert.areEqual(2, colorModifiers.size());
        msAssert.areEqual(DmlColorModifierType.LUMINANCE_MODULATION, ((DmlColorModifier)colorModifiers.get(0)).getModifierType());
        msAssert.areEqual(0.65d, ((DmlLuminanceModulation)colorModifiers.get(0)).getValue());
        msAssert.areEqual(DmlColorModifierType.LUMINANCE_OFFSET, ((DmlColorModifier)colorModifiers.get(1)).getModifierType());
        msAssert.areEqual(0.35d, ((DmlLuminanceOffset)colorModifiers.get(1)).getValue());

        // Test reading properties of the DmlChartMarkerLayout class. And DmlChartStyle.DataPointMarkerLayout.
        DmlChartMarkerLayout markerLayout = style.getDataPointMarkerLayout();
        msAssert.areEqual("circle", markerLayout.getSymbol());
        msAssert.areEqual(5, markerLayout.getSize());

        // Test reading properties of the DmlChartStyle class (except AxisTitle, CategoryAxis and
        // DataPointMarkerLayout that are not tested above).
        msAssert.areEqual(110, style.getByDmlChartStyleItem(DmlChartStyleItem.CHART_AREA).getLineReference().getStyleMatrixIndex());
        msAssert.areEqual(50, style.getByDmlChartStyleItem(DmlChartStyleItem.DATA_LABEL).getFillReference().getStyleMatrixIndex());
        msAssert.areEqual(9, style.getByDmlChartStyleItem(DmlChartStyleItem.DATA_LABEL_CALLOUT).getEffectReference().getStyleMatrixIndex());
        int nextExpectedMatrixIndex = 10;
        for (/*DmlChartStyleItem*/int index : DmlChartStyle.getAllStyleItems())
        {
            if (index >= DmlChartStyleItem.DATA_POINT)
            {
                msAssert.areEqual(nextExpectedMatrixIndex, style.getByDmlChartStyleItem(index).getLineReference().getStyleMatrixIndex());
                nextExpectedMatrixIndex++;
            }
        }

        msAssert.areEqual(1, style.getExtensions().getCount());
        msAssert.areEqual(DmlExtensionUri.THEME_FAMILY, style.getExtensions().get(DmlExtensionUri.THEME_FAMILY).getUri());

        msAssert.areEqual("201", style.getId());
    }

    /// <summary>
    /// Tests writing a color style part of a chart.
    /// </summary>
    @Test
    public void testWritingColorStyle() throws Exception
    {
        Document doc = createDocumetnWithChart();
        DmlChartSpace chartSpace = (DmlChartSpace)doc.getFirstSection().getBody().getShapes().get(0).getDmlNode();

        DmlChartColorStyle colorStyle = new DmlChartColorStyle();
        colorStyle.setId("3");
        colorStyle.setMethod("cycle");

        DmlColor[] colors = new DmlColor[2];
        colors[0] = new DmlSchemeColor(ThemeColor.LIGHT_1);
        colors[1] = new DmlPresetColor("azure");
        colorStyle.setColors(colors);

        DmlChartColorStyleVariation[] variations = new DmlChartColorStyleVariation[3];
        variations[0] = createVariation();
        DmlLuminanceModulation luminanceModifier = new DmlLuminanceModulation();
        luminanceModifier.setValue(0.5d);
        variations[1] = createVariation(luminanceModifier);
        luminanceModifier = new DmlLuminanceModulation();
        luminanceModifier.setValue(0.8d);
        DmlLuminanceOffset luminanceOffset = new DmlLuminanceOffset();
        luminanceOffset.setValue(0.3d);
        variations[2] = createVariation(luminanceModifier, luminanceOffset);
        colorStyle.setVariations(variations);

        colorStyle.setExtensions(new StringToObjDictionary<DmlExtension>());
        // This is not a real extension of a color style.
        colorStyle.getExtensions().add(DmlExtensionUri.THEME_FAMILY, new DmlExtension(DmlExtensionUri.THEME_FAMILY,
            "<a:ext uri=\"{05A4C25C-085E-4340-85A3-A5531E510DB2}\" " +
                "xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "<thm15:themeFamily id=\"{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}\" " +
                    "name=\"Office Theme\" vid=\"{4A3C46E8-61CC-4603-A589-7422A47A8E4A}\" " +
                    "xmlns:thm15=\"http://schemas.microsoft.com/office/thememl/2012/main\" />" +
            "</a:ext> "));

        chartSpace.setColorStyle(colorStyle);

        DocxExportContext context = new DocxExportContext(doc, CHART_COLOR_STYLE_PART,
            OoxmlComplianceCore.ISO_TRANSITIONAL);

        org.w3c.dom.Node styleNode = context.getXmlDocument().getChildNodes().item(0);
        msAssert.areEqual("3", styleNode.getAttributes().getNamedItem("id").getNodeValue());
        msAssert.areEqual("cycle", styleNode.getAttributes().getNamedItem("meth").getNodeValue());

        org.w3c.dom.NodeList styleSubNodes = styleNode.getChildNodes();
        msAssert.areEqual(6, styleSubNodes.getLength());

        msAssert.areEqual("schemeClr", styleSubNodes.item(0).getLocalName());
        msAssert.areEqual("lt1", styleSubNodes.item(0).getAttributes().getNamedItem("val").getNodeValue());

        msAssert.areEqual("prstClr", styleSubNodes.item(1).getLocalName());
        msAssert.areEqual("azure", styleSubNodes.item(1).getAttributes().getNamedItem("val").getNodeValue());

        msAssert.areEqual("variation", styleSubNodes.item(2).getLocalName());
        msAssert.isFalse(styleSubNodes.item(2).hasChildNodes());

        org.w3c.dom.Node variationNode = styleSubNodes.item(3);
        msAssert.areEqual("variation", variationNode.getLocalName());
        org.w3c.dom.NodeList variationChildren = variationNode.getChildNodes();
        msAssert.areEqual(1, variationChildren.getLength());
        msAssert.areEqual("lumMod", variationChildren.item(0).getLocalName());
        msAssert.areEqual("50000", variationChildren.item(0).getAttributes().getNamedItem("val").getNodeValue());

        variationNode = styleSubNodes.item(4);
        msAssert.areEqual("variation", variationNode.getLocalName());
        variationChildren = variationNode.getChildNodes();
        msAssert.areEqual(2, variationChildren.getLength());
        msAssert.areEqual("lumMod", variationChildren.item(0).getLocalName());
        msAssert.areEqual("80000", variationChildren.item(0).getAttributes().getNamedItem("val").getNodeValue());
        msAssert.areEqual("lumOff", variationChildren.item(1).getLocalName());
        msAssert.areEqual("30000", variationChildren.item(1).getAttributes().getNamedItem("val").getNodeValue());

        msAssert.areEqual("extLst", styleSubNodes.item(5).getLocalName());
        msAssert.areEqual("themeFamily", styleSubNodes.item(5).getChildNodes().item(0).getChildNodes().item(0).getLocalName());
    }

    /// <summary>
    /// Creates a color style variation object and puts the specified color modifiers into it.
    /// </summary>
    private DmlChartColorStyleVariation createVariation(/*params*/ IDmlColorModifier... modifiers)
    {
        DmlChartColorStyleVariation result = new DmlChartColorStyleVariation();
        for (IDmlColorModifier modifier : modifiers)
            result.getColorModifiers().add(modifier);
        return result;
    }

    /// <summary>
    /// Tests writing a style part of a chart.
    /// </summary>
    @Test
    public void testWritingStyle() throws Exception
    {
        Document doc = createDocumetnWithChart();

        DmlChartSpace chartSpace = (DmlChartSpace)doc.getFirstSection().getBody().getShapes().get(0).getDmlNode();
        chartSpace.setDmlChartStyle(new DmlChartStyle());
        prepareTestChartStyle(chartSpace.getDmlChartStyle());

        DocxExportContext context = new DocxExportContext(doc, CHART_STYLE_PART,
            OoxmlComplianceCore.ISO_TRANSITIONAL);

        checkChartStylePart(context.getXmlDocument());
    }

    /// <summary>
    /// Puts test data into the specified chart style object.
    /// </summary>
    private static void prepareTestChartStyle(DmlChartStyle style)
    {
        // For testing properties of the DmlChartStyle class.
        style.setId("3");

        style.setByDmlChartStyleItem(DmlChartStyleItem.DATA_LABEL_CALLOUT, new DmlChartStyleEntry());
        style.setDataPointMarkerLayout(new DmlChartMarkerLayout());

        int matrixIndex = 1;
        for (/*DmlChartStyleItem*/int index : DmlChartStyle.getAllStyleItems())
        {
            style.getByDmlChartStyleItem(index).getLineReference().setStyleMatrixIndex(matrixIndex);
            matrixIndex++;
        }

        style.setExtensions(new StringToObjDictionary<DmlExtension>());
        style.getExtensions().add(DmlExtensionUri.THEME_FAMILY, new DmlExtension(DmlExtensionUri.THEME_FAMILY,
            "<a:ext uri=\"{05A4C25C-085E-4340-85A3-A5531E510DB2}\" " +
                "xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"> " +
                "<thm15:themeFamily xmlns:thm15=\"http://schemas.microsoft.com/office/thememl/2012/main\" " +
                    "name=\"Office Theme\" id=\"{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}\" " +
                    "vid=\"{4A3C46E8-61CC-4603-A589-7422A47A8E4A}\"/> " +
            "</a:ext> "));

        // For testing properties of the DmlChartStyleEntry class.
        DmlChartStyleEntry styleEntry = style.getByDmlChartStyleItem(DmlChartStyleItem.CATEGORY_AXIS);

        styleEntry.setLineWidthScale(1.5d);
        styleEntry.getFillReference().setStyleMatrixIndex(100);
        styleEntry.getEffectReference().setStyleMatrixIndex(101);
        styleEntry.getFontReference().setFontCollectionIndex(DmlFontCollectionIndex.NONE);

        styleEntry.setShapePr(new DefaultShapeProperties());
        styleEntry.getShapePr().setFill(new DmlSolidFill(new DmlPresetColor("red")));

        styleEntry.setDefaultRunPr(new DmlRunProperties());
        styleEntry.getDefaultRunPr().setKerning(new DmlTextPoints(1150));

        styleEntry.setTextBodyPr(new DmlTextBodyProperties());
        styleEntry.getTextBodyPr().setRightInset(30000);

        styleEntry.setExtensions(new StringToObjDictionary<DmlExtension>());
        styleEntry.getExtensions().add(DmlExtensionUri.HIDDEN_EFFECTS, new DmlExtension(DmlExtensionUri.HIDDEN_EFFECTS,
            "<a:ext uri=\"{AF507438-7753-43E0-B8FC-AC1667EBCBE1}\" " +
                "xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"> " +
                "<a14:hiddenEffects " +
                    "xmlns:a14=\"http://schemas.microsoft.com/office/drawing/2010/main\"> " +
                    "<a:effectLst> " +
                        "<a:outerShdw algn=\"ctr\" dir=\"2700000\" dist=\"35921\" rotWithShape=\"0\"> " +
                            "<a:schemeClr val=\"bg2\" /> " +
                        "</a:outerShdw> " +
                    "</a:effectLst> " +
                "</a14:hiddenEffects> " +
            "</a:ext>"));

        styleEntry.setModifiers(new String[] { "allowNoFillOverride", "allowNoLineOverride" });

        // For testing properties of the DmlChartStyleReference class.
        DmlChartStyleReference styleReference = style.getByDmlChartStyleItem(DmlChartStyleItem.AXIS_TITLE).getLineReference();
        styleReference.setColor(new DmlPresetColor("aqua"));
        styleReference.setModifiers(new String[] { "ignoreCSTransforms", "auto" });

        // For testing properties of the DmlChartFontReference class.
        DmlChartFontReference fontReference = style.getByDmlChartStyleItem(DmlChartStyleItem.AXIS_TITLE).getFontReference();
        fontReference.setFontCollectionIndex(DmlFontCollectionIndex.MINOR);
        fontReference.setColor(new DmlSchemeColor(ThemeColor.TEXT_1));
        fontReference.setModifiers(new String[] { "ignoreCSTransforms", "auto" });

        // For testing properties of the DmlChartStyleColor class.
        DmlChartStyleColor styleColor = new DmlChartStyleColor();
        styleColor.setValue("auto");

        DmlLuminanceModulation luminanceModulation = new DmlLuminanceModulation();
        luminanceModulation.setValue(0.5d);
        msArrayList.add(styleColor.getColorModifiers(), luminanceModulation);

        DmlLuminanceOffset luminanceOffset = new DmlLuminanceOffset();
        luminanceOffset.setValue(0.8d);
        msArrayList.add(styleColor.getColorModifiers(), luminanceOffset);

        style.getByDmlChartStyleItem(DmlChartStyleItem.AXIS_TITLE).getEffectReference().setColor(styleColor);

        // For testing properties of the DmlChartMarkerLayout class.
        DmlChartMarkerLayout markerLayout = style.getDataPointMarkerLayout();
        markerLayout.setSymbol("cycle");
        markerLayout.setSize(11);
    }

    /// <summary>
    /// Checks XML of a chart style part that is filled by <see cref="PrepareTestChartStyle"/>.
    /// </summary>
    private static void checkChartStylePart(org.w3c.dom.Document xmlDocument)
    {
        org.w3c.dom.Node styleNode = xmlDocument.getChildNodes().item(0);

        // Testing properties of the DmlChartStyle class.
        msAssert.areEqual("3", styleNode.getAttributes().getNamedItem("id").getNodeValue());

        org.w3c.dom.NodeList styleSubNodes = styleNode.getChildNodes();
        msAssert.areEqual(32, styleSubNodes.getLength());

        int expectedId = 1;
        for (int i = 0; i < styleSubNodes.getLength(); i++)
        {
            if ((!"dataPointMarkerLayout".equals(styleSubNodes.item(i).getLocalName())) &&
                (!"extLst".equals(styleSubNodes.item(i).getLocalName())))
            {
                msAssert.areEqual(FormatterPal.intToXml(expectedId), styleSubNodes.item(i).getChildNodes().item(0).getAttributes().getNamedItem("idx").getNodeValue());
                expectedId++;
            }
        }

        org.w3c.dom.Node extensionsNode = msXmlNode.get(styleNode, "cs:extLst");
        msAssert.isNotNull(extensionsNode);
        msAssert.areEqual(DmlExtensionUri.THEME_FAMILY, extensionsNode.getChildNodes().item(0).getAttributes().getNamedItem("uri").getNodeValue());

        // Testing properties of the DmlChartStyleEntry class.
        org.w3c.dom.Node styleEntryNode = msXmlNode.get(styleNode, "cs:categoryAxis");

        msAssert.areEqual("1.5", msXmlNode.get(styleEntryNode, "cs:lineWidthScale").getTextContent());
        msAssert.areEqual("100", msXmlNode.get(styleEntryNode, "cs:fillRef").getAttributes().getNamedItem("idx").getNodeValue());
        msAssert.areEqual("101", msXmlNode.get(styleEntryNode, "cs:effectRef").getAttributes().getNamedItem("idx").getNodeValue());
        msAssert.areEqual("none", msXmlNode.get(styleEntryNode, "cs:fontRef").getAttributes().getNamedItem("idx").getNodeValue());

        org.w3c.dom.Node shapePrFillNode = msXmlNode.get(msXmlNode.get(styleEntryNode, "cs:spPr"), "a:solidFill");
        msAssert.isNotNull(shapePrFillNode);
        msAssert.isNotNull(msXmlNode.get(shapePrFillNode, "a:prstClr"));
        msAssert.areEqual("red", msXmlNode.get(shapePrFillNode, "a:prstClr").getAttributes().getNamedItem("val").getNodeValue());

        org.w3c.dom.Node defaultRunPrNode = msXmlNode.get(styleEntryNode, "cs:defRPr");
        msAssert.isNotNull(defaultRunPrNode);
        msAssert.areEqual("1150", defaultRunPrNode.getAttributes().getNamedItem("kern").getNodeValue());

        org.w3c.dom.Node bodyPrNode = msXmlNode.get(styleEntryNode, "cs:bodyPr");
        msAssert.isNotNull(bodyPrNode);
        msAssert.areEqual("30000", bodyPrNode.getAttributes().getNamedItem("rIns").getNodeValue());

        extensionsNode = msXmlNode.get(styleEntryNode, "cs:extLst");
        msAssert.isNotNull(extensionsNode);
        msAssert.areEqual(DmlExtensionUri.HIDDEN_EFFECTS, extensionsNode.getChildNodes().item(0).getAttributes().getNamedItem("uri").getNodeValue());

        msAssert.areEqual("allowNoFillOverride allowNoLineOverride", styleEntryNode.getAttributes().getNamedItem("mods").getNodeValue());

        // Testing properties of the DmlChartStyleReference class.
        org.w3c.dom.Node axisTitleNode = msXmlNode.get(styleNode, "cs:axisTitle");
        org.w3c.dom.Node styleReferenceNode = msXmlNode.get(axisTitleNode, "cs:lnRef");

        msAssert.areEqual("a:prstClr", styleReferenceNode.getChildNodes().item(0).getNodeName());
        msAssert.areEqual("aqua", styleReferenceNode.getChildNodes().item(0).getAttributes().getNamedItem("val").getNodeValue());

        msAssert.areEqual("ignoreCSTransforms auto", styleReferenceNode.getAttributes().getNamedItem("mods").getNodeValue());

        // Testing properties of the DmlChartFontReference class.
        org.w3c.dom.Node fontReferenceNode = msXmlNode.get(axisTitleNode, "cs:fontRef");

        msAssert.areEqual("a:schemeClr", fontReferenceNode.getChildNodes().item(0).getNodeName());
        msAssert.areEqual("tx1", fontReferenceNode.getChildNodes().item(0).getAttributes().getNamedItem("val").getNodeValue());

        msAssert.areEqual("ignoreCSTransforms auto", styleReferenceNode.getAttributes().getNamedItem("mods").getNodeValue());

        // Testing properties of the DmlChartStyleColor class.
        org.w3c.dom.Node styleColorNode = msXmlNode.get(msXmlNode.get(axisTitleNode, "cs:effectRef"), "cs:styleClr");
        msAssert.areEqual("auto", styleColorNode.getAttributes().getNamedItem("val").getNodeValue());

        msAssert.areEqual(2, styleColorNode.getChildNodes().getLength());
        msAssert.areEqual("a:lumMod", styleColorNode.getChildNodes().item(0).getNodeName());
        msAssert.areEqual("50000", styleColorNode.getChildNodes().item(0).getAttributes().getNamedItem("val").getNodeValue());
        msAssert.areEqual("a:lumOff", styleColorNode.getChildNodes().item(1).getNodeName());
        msAssert.areEqual("80000", styleColorNode.getChildNodes().item(1).getAttributes().getNamedItem("val").getNodeValue());

        // Testing properties of the DmlChartMarkerLayout class.
        org.w3c.dom.Node markerLayoutNode = msXmlNode.get(styleNode, "cs:dataPointMarkerLayout");
        msAssert.areEqual("cycle", markerLayoutNode.getAttributes().getNamedItem("symbol").getNodeValue());
        msAssert.areEqual("11", markerLayoutNode.getAttributes().getNamedItem("size").getNodeValue());
    }

    /// <summary>
    /// Creates a document with a chart.
    /// </summary>
    private static Document createDocumetnWithChart() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder documentBuilder = new DocumentBuilder(doc);

        documentBuilder.insertChart(ChartType.AREA, 200.0, 200.0);

        return doc;
    }

    private static final String CHART_COLOR_STYLE_PART = "word/charts/chart/colors1.xml";
    private static final String CHART_STYLE_PART = "word/charts/chart/style1.xml";
}

