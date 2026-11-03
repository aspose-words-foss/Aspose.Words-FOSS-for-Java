// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/07/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringToObjDictionary;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Guid;
import java.util.Map;
import com.aspose.Debug;


/// <summary>
/// Writer for DrawingML extensions.
/// </summary>
/*static*/ class DmlExtensionListWriter
{
	/* Simulation of static class by using private constructor */
	private DmlExtensionListWriter()
	{}

    /// <summary>
    /// Writes extensions with prefix 'a'.
    /// </summary>
    static void write(StringToObjDictionary<DmlExtension> extensions, IDmlShapeWriterContext writer) throws Exception
    {
        write("a", extensions, writer);
    }

    /// <summary>
    /// Writes extensions with the specified prefix.
    /// </summary>
    static void write(String prefix, StringToObjDictionary<DmlExtension> extensions,
        IDmlShapeWriterContext writer) throws Exception
    {
        write(prefix, null, extensions, writer);
    }

    /// <summary>
    /// Writes extensions with the specified prefix and namespace.
    /// </summary>
    static void write(String prefix, String elementNamespace,
        StringToObjDictionary<DmlExtension> extensions, IDmlShapeWriterContext writer) throws Exception
    {
        if ((extensions == null) || (extensions.getCount() == 0))
            return;

        String rootTagName = msString.concat(prefix, DmlExtensionName.SEPARATOR, DmlExtensionName.EXT_LST);

        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement(rootTagName);

        if (StringUtil.hasChars(elementNamespace))
            builder.writeAttribute(msString.concat(DmlExtensionName.XMLNS, DmlExtensionName.SEPARATOR, prefix), elementNamespace);

        for (DmlExtension extension : extensions.getValues())
        {
            if (extension == null)
                continue;

            switch (gStringSwitchMap.of(extension.getUri()))
            {
                case/*DmlExtensionUri.NON_VISUAL_PR*/0:
                    writeNvPrExt(writer, extension);
                    break;
                case/*DmlExtensionUri.HIDDEN_FILL*/1:
                    writeFillExt(writer, extension);
                    break;
                case/*DmlExtensionUri.USE_LOCAL_DPI*/2:
                    writeUseLocalDpiExt(writer, extension);
                    break;
                case/*DmlExtensionUri.HIDDEN_LINE*/3:
                    writeHiddenLineExt(writer, extension);
                    break;
                case/*DmlExtensionUri.DATA_LABELS*/4:
                    writeDatalabelsExtension(extension, writer, prefix);
                    break;
                case/*DmlExtensionUri.FILTERING*/5:
                    writeFilteringExtension(extension, writer, prefix);
                    break;
                case/*DmlExtensionUri.UNIQUE_ID*/6:
                    writeUniqueIdExtension(extension, writer, prefix);
                    break;
                case/*DmlExtensionUri.VIDEO_PR*/7:
                    writeVideoPrExtension(extension, writer);
                    break;
                case/*DmlExtensionUri.DATA_MODEL_EXT*/8:
                    writeDataModelExtension(extension, writer);
                    break;
                case/*DmlExtensionUri.SVG_BLIP*/9:
                    writeSvgBlip(extension, writer, prefix);
                    break;
                case/*DmlExtensionUri.DECORATIVE*/10:
                    writeDecorativeExtension(writer, extension);
                    break;
                case /*DmlExtensionUri.INVERT_SOLID_FILL_FMT*/11:
                    writeInvertSolidFillFmtExt(writer, extension, prefix);
                    break;
                default:
                {
                    // If this extension is unknown type or not parsed, we should write it as raw data to preserve it.
                    writeRaw(extension, writer);
                    break;
                }
            }
        }

        builder.endElement(rootTagName);
    }

    /// <summary>
    /// Writes data model extension.
    /// 2.10.1.1 dataModelExt [MS-ODRAWXML].
    /// </summary>
    private static void writeDataModelExtension(DmlExtension ext, IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();

        final String PREFIX = "a";
        final String PREFIX_DSP = "dsp";
        final String EXT_NAME = DmlExtensionName.DATA_MODEL_EXT;
        String prefixDspWithExtName = msString.concat(PREFIX_DSP, DmlExtensionName.SEPARATOR, EXT_NAME);
        String xmlmsWithPrefix = msString.concat(DmlExtensionName.XMLNS, DmlExtensionName.SEPARATOR, PREFIX);
        String prefixExt = msString.concat(PREFIX, DmlExtensionName.SEPARATOR, DmlExtensionName.EXT);
        String xmlmsWithprefixDsp = msString.concat(DmlExtensionName.XMLNS, DmlExtensionName.SEPARATOR, PREFIX_DSP);

        builder.writeElementWithAttributesStart(prefixExt, DmlExtensionName.URI, ext.getUri());

        // MSW always writes not strict namespaces for this extension.
        builder.writeAttribute(xmlmsWithPrefix, DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_MAIN, false));
        builder.startElement(prefixDspWithExtName);
        builder.writeAttributeString("minVer", DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_DIAGRAM, false));
        builder.writeAttributeString("relId", ext.getDrawingRelId());
        builder.writeAttribute(xmlmsWithprefixDsp, DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_DIAGRAM_2008, false));
        builder.endElement(prefixDspWithExtName);
        builder.endElement(prefixExt);
    }

    /// <summary>
    /// Writes extension of web video properties.
    /// 2.20.1.1 webVideoPr [MS-ODRAWXML].
    /// </summary>
    private static void writeVideoPrExtension(DmlExtension ext, IDmlShapeWriterContext writer) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();

        if (ext.getWebVideoPr() != null)
        {
            final String PREFIX = "a";
            final String PREFIX_WP_15 = "wp15";
            String prefixWp15WithExtName = msString.concat(PREFIX_WP_15, DmlExtensionName.SEPARATOR, DmlExtensionName.WEB_VIDEO_PR);
            String xmlmsWithPrefixWp15 = msString.concat(DmlExtensionName.XMLNS, DmlExtensionName.SEPARATOR, PREFIX_WP_15);
            String prefixExt = msString.concat(PREFIX, DmlExtensionName.SEPARATOR, DmlExtensionName.EXT);

            builder.writeElementWithAttributesStart(prefixExt, DmlExtensionName.URI, ext.getUri());

            // Write webVideoPr element that specifies the properties for displaying an online video to the user.
            builder.writeElementWithAttributesStart(prefixWp15WithExtName, xmlmsWithPrefixWp15,
                DmlExtensionsNamespace.WORDPROCESSING_DRAWING_2012);

            DmlWebVideoProperties webVideoPr = ext.getWebVideoPr();

            builder.writeAttributeString(DmlExtensionName.EMBEDDED_HTML, webVideoPr.getEmbedHtml());
            builder.writeAttributeString("h", FormatterPal.doubleToStr(webVideoPr.getFrameHeight()));
            builder.writeAttributeString("w", FormatterPal.doubleToStr(webVideoPr.getFrameWidth()));

            builder.endElement(prefixWp15WithExtName);
            builder.endElement(prefixExt);
        }
        else
        {
            writeRaw(ext, writer);
        }
    }

    private static void writeFilteringExtension(DmlExtension ext, IDmlShapeWriterContext writer, String prefix) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();

        if (ext.getDataLabelsRangeData().getValueRef() == null)
        {
            if (!msString.isNullOrEmpty(ext.getXmlDoc()))
                writeRaw(ext, writer);

            return;
        }

        final String PREFIX_C_15 = "c15";
        final String EXT_NAME = "datalabelsRange";
        final String F_ELEMENT = "f";
        final String DLBL_RNANGE_CHACHE = "dlblRangeCache";
        String prefixC15WithExtName = msString.concat(PREFIX_C_15, DmlExtensionName.SEPARATOR, EXT_NAME);
        String xmlmsWithPrefixC15 = msString.concat(DmlExtensionName.XMLNS, DmlExtensionName.SEPARATOR, PREFIX_C_15);
        String prefixExt = msString.concat(prefix, DmlExtensionName.SEPARATOR, DmlExtensionName.EXT);
        String prefixC15F = msString.concat(PREFIX_C_15, DmlExtensionName.SEPARATOR, F_ELEMENT);
        String prefixDlblRnangeChache = msString.concat(PREFIX_C_15, DmlExtensionName.SEPARATOR, DLBL_RNANGE_CHACHE);


        builder.writeElementWithAttributesStart(prefixExt, DmlExtensionName.URI, ext.getUri());
        builder.writeAttribute(xmlmsWithPrefixC15, DmlExtensionsNamespace.CHART);
        builder.startElement(prefixC15WithExtName);
        builder.writeElement(prefixC15F, ext.getDataLabelsRangeData().getValueRef().getFormula().getValue());
        DmlChartCommonWriter.writeValueCollection(prefixDlblRnangeChache, ext.getDataLabelsRangeData().getValueRef().getValues(),
            writer, false);

        builder.endElement(prefixC15WithExtName);
        builder.endElement(prefixExt);
    }

    /// <summary>
    /// Writes uniqueId (<see cref="DmlExtensionUri.UniqueId"/>) extension.
    /// </summary>
    private static void writeUniqueIdExtension(DmlExtension ext, IDmlShapeWriterContext writer, String prefix) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();

        if (Guid.equals(ext.getDataLabelId(), Guid.Empty))
        {
            if (!msString.isNullOrEmpty(ext.getXmlDoc()))
                writeRaw(ext, writer);

            return;
        }

        final String PREFIX_C_16 = "c16";
        String prefixС16WithExtName = msString.concat(PREFIX_C_16, DmlExtensionName.SEPARATOR, DmlExtensionName.UNIQUE_ID);
        String xmlmsWithPrefixС16 = msString.concat(DmlExtensionName.XMLNS, DmlExtensionName.SEPARATOR, PREFIX_C_16);
        String prefixExt = msString.concat(prefix, DmlExtensionName.SEPARATOR, DmlExtensionName.EXT);

        builder.writeElementWithAttributesStart(prefixExt, DmlExtensionName.URI, ext.getUri());
        builder.writeAttribute(xmlmsWithPrefixС16, DmlExtensionsNamespace.CHART_2014);
        String labelId = msString.toUpperInvariant(ext.getDataLabelId().toString("B"));
        builder.writeElementWithAttributes(prefixС16WithExtName, DmlExtensionName.VAL, labelId);
        builder.endElement(prefixExt);
    }

    /// <summary>
    /// Writes raw xml, creates correct relation ids for embedded images.
    /// </summary>
    private static void writeRaw(DmlExtension ext, IDmlShapeWriterContext writer) throws Exception
    {
        String text = ext.getXmlToWrite(writer.getCompliance());

        for (Map.Entry<String, byte[]> pair : (Iterable<Map.Entry<String, byte[]>>) ext.getEmbeddedImages())
        {
            String oldRelId = msString.format(" r:embed=\"{0}\"", pair.getKey());
            String newRelId;
            if (pair.getValue() == null)
            {
                // The case of the non-valid image data. Remove this relation.
                newRelId = "";
            }
            else
            {
                // Write the corresponding image, and replace the old Id with the correct one.
                newRelId = msString.format(" r:embed=\"{0}\"", writer.writeImageBinData(pair.getValue()));
            }

            text = text.replace(oldRelId, newRelId);
        }

        writer.getBuilder().writeRaw(text);
    }

    /// <summary>
    /// Writes svgBlip drawing extension.
    /// </summary>
    private static void writeSvgBlip(DmlExtension ext, IDmlShapeWriterContext writer, String prefix) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();
        final String PREFIX_ASVG = "asvg";
        String prefixAsvgWithExtName = msString.concat(PREFIX_ASVG, DmlExtensionName.SEPARATOR, DmlExtensionName.SVG_BLIP);
        String xmlmsWithPrefixAsvg = msString.concat(DmlExtensionName.XMLNS, DmlExtensionName.SEPARATOR, PREFIX_ASVG);
        String prefixExt = msString.concat(prefix, DmlExtensionName.SEPARATOR, DmlExtensionName.EXT);

        builder.writeElementWithAttributesStart(prefixExt, DmlExtensionName.URI, ext.getUri());
        builder.startElement(prefixAsvgWithExtName);
        builder.writeAttribute(xmlmsWithPrefixAsvg, DmlExtensionsNamespace.SVG);
        DmlFillWriter.writeBlipImage(ext.getSvgBlip(), writer);
        builder.endElement(prefixAsvgWithExtName);
        builder.endElement(prefixExt);
    }

    /// <summary>
    /// Writes extension of known type, i.e. that was successfully parsed.
    /// </summary>
    private static void writeDatalabelsExtension(DmlExtension ext, IDmlShapeWriterContext writer, String prefix) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();
        String prefixWithExt = msString.concat(prefix, DmlExtensionName.SEPARATOR, DmlExtensionName.EXT);
        final String PREFIX_C_15 = "c15";
        String xmlnsWithPrefixC15 = msString.concat(DmlExtensionName.XMLNS, DmlExtensionName.SEPARATOR, PREFIX_C_15);
        builder.writeElementWithAttributesStart(prefixWithExt, DmlExtensionName.URI, ext.getUri());
        builder.writeAttribute(xmlnsWithPrefixC15, DmlExtensionsNamespace.CHART);
        DmlChartCommonWriter.writeDataLabelExtensionProperties(PREFIX_C_15, ext.getDataLabelPr(), writer);
        builder.endElement(prefixWithExt);
    }

    /// <summary>
    /// Writes "HiddenLine" extension content.
    /// </summary>
    /// <param name="writer">Document writer.</param>
    /// <param name="ext">Object which holds properties of the "hidden line" extension.</param>
    private static void writeHiddenLineExt(IDmlShapeWriterContext writer, DmlExtension ext) throws Exception
    {
        Debug.assertTrue((writer != null) && (ext != null));
        final String PREFIX = "a14";
        String prefixWithExtName = msString.concat(PREFIX, DmlExtensionName.SEPARATOR, DmlExtensionName.HIDDEN_LINE);
        startExtension(writer, ext);
        DmlOutlineWriter.write(prefixWithExtName, ext.getOutlinePr(), writer);
        endExtension(writer);
    }

    /// <summary>
    /// Writes "InvertSolidFillFmt" extension content.
    /// </summary>
    private static void writeInvertSolidFillFmtExt(IDmlShapeWriterContext writer, DmlExtension ext, String prefix) throws Exception
    {
        Debug.assertTrue((writer != null) && (ext != null));

        // The name spaces <a:> and <c:> are declared when the chart space are created.
        final String PREFIX_C_14 = "c14";
        String nameSpaceС14 = DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_2007, false);
        String xmlnsWithPrefC14 = msString.concat(DmlExtensionName.XMLNS, DmlExtensionName.SEPARATOR, PREFIX_C_14);
        String prefixC14WithExtName = msString.concat(PREFIX_C_14, DmlExtensionName.SEPARATOR, DmlExtensionName.INVERT_SOLID_FILL_FMT);
        String prefixExt = msString.concat(prefix, DmlExtensionName.SEPARATOR, DmlExtensionName.EXT);

        NrxXmlBuilder builder = writer.getBuilder();
        builder.writeElementWithAttributesStart(prefixExt, DmlExtensionName.URI, ext.getUri());
        builder.writeAttribute(xmlnsWithPrefC14, nameSpaceС14);
        builder.startElement(prefixC14WithExtName);
        DmlChartCommonWriter.writeShapeProperties(ext.getDmlChartSpPr(), writer, false, PREFIX_C_14);
        builder.endElement(prefixC14WithExtName);
        builder.endElement(prefixExt);
    }

    private static void writeUseLocalDpiExt(IDmlShapeWriterContext writer, DmlExtension ext)
    {
        writeBoolExtension(writer, ext, "a14", DmlExtensionName.USE_LOCAL_DPI, ext.getUseLocalDpi(),
            DmlExtensionsNamespace.DRAWING_MAIN);
    }

    /// <summary>
    /// Writes Decorative extension.
    /// </summary>
    private static void writeDecorativeExtension(IDmlShapeWriterContext writer, DmlExtension ext)
    {
        writeBoolExtension(writer, ext, "adec", DmlExtensionName.DECORATIVE, ext.getDecorative(),
            DmlExtensionsNamespace.DECORATIVE);
    }

    /// <summary>
    /// Writes bool type extension.
    /// </summary>
    private static void writeBoolExtension(IDmlShapeWriterContext writer,
        DmlExtension ext,
        String prefix,
        String extName,
        boolean extValue,
        String nameSpace)
    {
        Debug.assertTrue((writer != null) && (ext != null));

        NrxXmlBuilder builder = writer.getBuilder();
        startExtension(writer, ext);
        String prefixWithExtName = msString.concat(prefix, DmlExtensionName.SEPARATOR, extName);
        String xmlmsWithPrefix = msString.concat(DmlExtensionName.XMLNS, DmlExtensionName.SEPARATOR, prefix);
        builder.writeElementWithAttributesStart(prefixWithExtName, xmlmsWithPrefix, nameSpace);
        builder.writeAttribute(DmlExtensionName.VAL, extValue);
        builder.endElement(prefixWithExtName);
        endExtension(writer);
    }

    /// <summary>
    /// Begins the extension XML element.
    /// </summary>
    /// <param name="writer">Document writer.</param>
    /// <param name="ext">Extension for writing.</param>
    private static void startExtension(IDmlShapeWriterContext writer, DmlExtension ext)
    {
        Debug.assertTrue((writer != null) && (ext != null));
        String prefixExt = msString.concat(DmlExtensionName.A_PREFIX, DmlExtensionName.SEPARATOR, DmlExtensionName.EXT);
        String xmlmsWithPrefix = msString.concat(DmlExtensionName.XMLNS, DmlExtensionName.SEPARATOR, DmlExtensionName.A_PREFIX);
        boolean isStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;
        String nameSpace = DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_MAIN, isStrict);
        writer.getBuilder().writeElementWithAttributesStart(prefixExt, DmlExtensionName.URI, ext.getUri(), xmlmsWithPrefix,
            nameSpace);
    }

    /// <summary>
    /// Closes the extension XML element.
    /// </summary>
    /// <param name="writer">Document writer.</param>
    private static void endExtension(IDmlShapeWriterContext writer)
    {
        Debug.assertTrue(writer != null);
        String prefixWithExt = msString.concat(DmlExtensionName.A_PREFIX, DmlExtensionName.SEPARATOR, DmlExtensionName.EXT);
        writer.getBuilder().endElement(prefixWithExt);
    }

    /// <summary>
    /// Writes extension with fill properties to output document.
    /// </summary>
    /// <param name="writer">Document writer.</param>
    /// <param name="ext">Extension for writing.</param>
    private static void writeFillExt(IDmlShapeWriterContext writer, DmlExtension ext) throws Exception
    {
        Debug.assertTrue((writer != null) && (ext != null));

        if (ext.getFillPr() == null)
            return;

        final String PREFIX = "a14";
        String prefWithExtName = msString.concat(PREFIX, DmlExtensionName.SEPARATOR, DmlExtensionName.HIDDEN_FILL);
        String xmlnsWithPrefix = msString.concat(DmlExtensionName.XMLNS, DmlExtensionName.SEPARATOR, PREFIX);

        DmlFill fill = ext.getFillPr();
        NrxXmlBuilder builder = writer.getBuilder();
        startExtension(writer, ext);
        builder.writeElementWithAttributesStart(prefWithExtName, xmlnsWithPrefix, DmlExtensionsNamespace.DRAWING_MAIN);
        DmlFillWriter.write(fill, writer, false);
        builder.endElement(prefWithExtName);
        endExtension(writer);
    }

    /// <summary>
    /// Writes extension with non-visual drawing properties to output document.
    /// </summary>
    /// <param name="writer">Document writer.</param>
    /// <param name="ext">Extension for writing.</param>
    private static void writeNvPrExt(IDmlShapeWriterContext writer, DmlExtension ext) throws Exception
    {
        Debug.assertTrue((writer != null) && (ext != null));

        if (ext.getNvPr() == null)
            return;

        final String PREFIX = "dgm14";
        String prefWithExtName = msString.concat(PREFIX, DmlExtensionName.SEPARATOR, DmlExtensionName.C_NV_PR);
        String xmlnsWithPrefix = msString.concat(DmlExtensionName.XMLNS, DmlExtensionName.SEPARATOR, PREFIX);
        DmlNvDrawingProperties nvPr = ext.getNvPr();
        startExtension(writer, ext);

        NrxXmlBuilder builder = writer.getBuilder();
        builder.writeElementWithAttributesStart(prefWithExtName, xmlnsWithPrefix, DmlExtensionsNamespace.DIAGRAM_2010);
        builder.writeAttributeString("id", Integer.toString(nvPr.getId()));
        builder.writeAttributeString("name", nvPr.getName());

        DmlHlink hlinkClick = nvPr.getHlinkClick();

        if (hlinkClick != null)
            DmlHlinkWriter.writeHlink("a:hlinkClick", hlinkClick.getId(), hlinkClick.getTargetFrame(), hlinkClick.getTooltip(),
                hlinkClick.getExtensions(), writer);

        DmlHlink hlinkHover = nvPr.getHlinkHover();

        if (hlinkHover != null)
            DmlHlinkWriter.writeHlink("a:hlinkHover", hlinkHover.getId(), hlinkHover.getTargetFrame(), hlinkHover.getTooltip(),
                hlinkHover.getExtensions(), writer);

        builder.endElement(prefWithExtName);
        endExtension(writer);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"{E40237B7-FDA0-4F09-8148-C483321AD2D9}",
		"{909E8E84-426E-40DD-AFC4-6F175D3DCCD1}",
		"{28A0092B-C50C-407E-A947-70E740481C1C}",
		"{91240B29-F687-4F45-9708-019B960494DF}",
		"{CE6537A1-D6FC-4f65-9D91-7224C49458BB}",
		"{02D57815-91ED-43cb-92C2-25804820EDAC}",
		"{C3380CC4-5D6E-409C-BE32-E72D297353CC}",
		"{C809E66F-F1BF-436E-b5F7-EEA9579F0CBA}",
		"http://schemas.microsoft.com/office/drawing/2008/diagram",
		"{96DAC541-7B7A-43D3-8B79-37D633B846F1}",
		"{C183D7F6-B498-43B3-948B-1728B52AA6E4}",
		"{6F2FDCE9-48DA-4B69-8628-5D25D57E5C99}"
	);

}

