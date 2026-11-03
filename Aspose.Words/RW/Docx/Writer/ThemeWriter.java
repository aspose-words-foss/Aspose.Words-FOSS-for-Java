// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/07/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.AnyXmlBuilder;
import java.util.Map;
import com.aspose.StringUtil;
import com.aspose.AnyXmlReader;
import com.aspose.ms.System.msString;


class ThemeWriter
{
    /// <summary>
    /// Implements writing of document theme.
    /// </summary>
    static void write(DocxDocumentWriterBase writer, Theme theme, boolean isThemeOverride) throws Exception
    {
        AnyXmlBuilder builder = writer.getCurrentBuilder();
        String tagName = isThemeOverride ? "a:themeOverride" : "a:theme";

        builder.startDocument(true);
        builder.startElement(tagName);
        builder.writeAttributeString("xmlns:a", DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_MAIN,
            writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT));

        if (!isThemeOverride)
        {
            builder.writeAttributeString("name", theme.getName());

            builder.startElement("a:themeElements"); // no element in theme override
        }

        // Write color scheme
        writeColorScheme(builder, theme.getColorScheme());

        // Write font scheme
        builder.startElement("a:fontScheme");
        builder.writeAttributeString("name", theme.getFontSchemeName());

        builder.startElement("a:majorFont");
        writeThemeFonts(builder, theme.getMajorFonts());
        builder.endElement("a:majorFont");

        builder.startElement("a:minorFont");
        writeThemeFonts(builder, theme.getMinorFonts());
        builder.endElement("a:minorFont");

        builder.endElement("a:fontScheme");

        // Write format scheme
        writeFmtScheme(writer, theme.getFormatScheme());

        if (!isThemeOverride)
        {
            builder.endElement("a:themeElements");

            writeObjectDefaults(writer, theme.getObjectDefaults());

            writeUnparsedElements(writer, theme);

            DmlExtensionListWriter.write(((IDmlExtensionListSource)theme).getExtensions(), writer);
        }

        builder.endElement(tagName);
        builder.endDocument();
        builder.flush();
    }

    private static void writeColorScheme(AnyXmlBuilder builder, ThemeColors colorScheme)
    {
        builder.startElement("a:clrScheme");
        builder.writeAttributeString("name", colorScheme.getName());

        for (/*ThemeColor*/int themeColor : ThemeColors.ALL_THEME_COLORS)
        {
            if (themeColor == ThemeColor.BACKGROUND_1 || themeColor == ThemeColor.BACKGROUND_2
                || themeColor == ThemeColor.TEXT_1 || themeColor == ThemeColor.TEXT_2)
            {
                continue;
            }

            DmlColor dmlColor = colorScheme.getColor(themeColor);

            if (dmlColor == null)
                continue;

            builder.startElement("a:" + ThemeColorConverter.toString(themeColor));
            switch (dmlColor.getColorType())
            {
                case DmlColorType.HEX_RGB_COLOR:
                    DmlHexRgbColor dmlHexRgbColor = (DmlHexRgbColor)dmlColor;
                    builder.startElement("a:srgbClr");
                    builder.writeAttributeString("val", dmlHexRgbColor.getValue());
                    builder.endElement();
                    break;
                case DmlColorType.SYSTEM_COLOR:
                    DmlSystemColor dmlSystemColor = (DmlSystemColor)dmlColor;
                    builder.startElement("a:sysClr");
                    builder.writeAttributeString("val", dmlSystemColor.getValue());
                    builder.writeAttributeString("lastClr", dmlSystemColor.getLastColor());
                    builder.endElement("a:sysClr");
                    break;
                default:
                    throw new IllegalStateException("Unexpected color type.");
            }
            builder.endElement("a:" + ThemeColorConverter.toString(themeColor));
        }

        builder.endElement("a:clrScheme");
    }

    private static void writeThemeFonts(AnyXmlBuilder builder, ThemeFonts themeFonts)
    {
        builder.startElement("a:latin");
        builder.writeAttributeString("typeface", themeFonts.getLatin());
        builder.endElement("a:latin");
        builder.startElement("a:ea");
        builder.writeAttributeString("typeface", themeFonts.getEastAsian());
        builder.endElement("a:ea");
        builder.startElement("a:cs");
        builder.writeAttributeString("typeface", themeFonts.getComplexScript());
        builder.endElement("a:cs");

        // Write supplemental fonts.
        for (Map.Entry<String, ThemeSupplementalFont> entry : themeFonts.getSupplementalFonts().entrySet())
        {
            String script = entry.getKey();
            ThemeSupplementalFont supplementalFont = entry.getValue();
            String typeface = supplementalFont.getTypeface();

            builder.startElement("a:font");
            builder.writeAttributeString("script", script);
            builder.writeAttributeString("typeface", typeface);
            builder.endElement("a:font");
        }
    }

    private static void writeFmtScheme(DocxDocumentWriterBase writer, FormatScheme formatScheme) throws Exception
    {
        AnyXmlBuilder builder = writer.getCurrentBuilder();

        builder.startElement("a:fmtScheme");
        if (StringUtil.hasChars(formatScheme.getName()))
            builder.writeAttributeString("name", formatScheme.getName());

        builder.startElement("a:fillStyleLst");
        for (int i = 0; i < formatScheme.getFillStyleCount(); i++)
            DmlFillWriter.write(formatScheme.getFillStyle(i), writer, false);
        builder.endElement("a:fillStyleLst");

        builder.startElement("a:lnStyleLst");
        for (int i = 0; i < formatScheme.getLineStyleCount(); i++)
            DmlOutlineWriter.write("a:ln", formatScheme.getLineStyle(i), writer);
        builder.endElement("a:lnStyleLst");

        builder.startElement("a:effectStyleLst");
        for (int i = 0; i < formatScheme.getEffectStyleCount(); i++)
            writeEffectStyle(writer, formatScheme.getEffectStyle(i));
        builder.endElement("a:effectStyleLst");

        builder.startElement("a:bgFillStyleLst");
        for (int i = 0; i < formatScheme.getBackgroundFillStyleCount(); i++)
            DmlFillWriter.write(formatScheme.getBackgroundFillStyle(i), writer, false);
        builder.endElement("a:bgFillStyleLst");

        builder.endElement("a:fmtScheme");
    }

    /// <summary>
    /// Writes 20.1.4.1.11 effectStyle (Effect Style) element.
    /// This element defines a set of effects and 3D properties that can be applied to an object.
    /// </summary>
    private static void writeEffectStyle(DocxDocumentWriterBase writer, EffectStyle effectStyle) throws Exception
    {
        AnyXmlBuilder builder = writer.getCurrentBuilder();

        builder.startElement("a:effectStyle");

        DmlShapeEffectsWriter.write(effectStyle.getEffects(), writer, true);

        if (effectStyle.getScene3DProperties() != null)
            Dml3DPropertiesWriter.writeScene3D("a", effectStyle.getScene3DProperties(), writer, true);

        if (effectStyle.getShape3DProperties() != null)
            Dml3DPropertiesWriter.writeShape3D("a", effectStyle.getShape3DProperties(), writer, true);

        builder.endElement("a:effectStyle");
    }

    /// <summary>
    /// Writes the element 20.1.6.7 objectDefaults (Object Defaults, CT_ObjectStyleDefaults).
    /// </summary>
    private static void writeObjectDefaults(DocxDocumentWriterBase writer, ThemeObjectDefaults objectDefaults) throws Exception
    {
        AnyXmlBuilder builder = writer.getCurrentBuilder();

        builder.startElement("a:objectDefaults");

        if (objectDefaults != null)
        {
            writeShapeDefaults(writer, objectDefaults.getShapeDefaults(), "a:spDef");
            writeShapeDefaults(writer, objectDefaults.getLineDefaults(), "a:lnDef");
            writeShapeDefaults(writer, objectDefaults.getTextDefaults(), "a:txDef");
        }

        builder.endElement("a:objectDefaults");
    }

    /// <summary>
    /// Writes shape definition element (CT_DefaultShapeDefinition).
    /// </summary>
    private static void writeShapeDefaults(DocxDocumentWriterBase writer, DefaultShapeDefinition shapeDefault,
        String tagName) throws Exception
    {
        if (shapeDefault == null)
            return;

        AnyXmlBuilder builder = writer.getCurrentBuilder();

        builder.startElement(tagName);

        if (shapeDefault.getShapePr() != null)
            DmlShapePropertiesWriter.write("a", shapeDefault.getShapePr(), writer);
        if (shapeDefault.getTextBodyPr() != null)
            DmlTextShapeWriter.writeDmlTextShapeBodyPr("a:bodyPr", shapeDefault.getTextBodyPr(), writer);
        if (shapeDefault.getListStyles() != null)
            DmlTextShapeWriter.writeDmlTextListStyles(shapeDefault.getListStyles(), writer);
        if (shapeDefault.getStyle() != null)
            DmlShapeStyleWriter.write("a", shapeDefault.getStyle(), writer);

        builder.endElement(tagName);
    }

    private static void writeUnparsedElements(DocxDocumentWriterBase writer, Theme theme) throws Exception
    {
        AnyXmlBuilder builder = writer.getCurrentBuilder();

        theme.getThemePart().getStream().setPosition(0);
        AnyXmlReader xmlReader = new AnyXmlReader(theme.getThemePart().getStream());

        // We may need to change ISO Transitional namespaces to ISO Strict or backward.
        boolean isSourceIsoStrict =
            msString.equals(xmlReader.getNamespaceURI(), DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_MAIN, true));
        boolean isDestinationIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;
        DmlIsoStrictXmlUpdater xmlUpdater = (isSourceIsoStrict != isDestinationIsoStrict)
            ? new DmlIsoStrictXmlUpdater(isDestinationIsoStrict)
            : null;

        String rootTag = xmlReader.getLocalName();

        xmlReader.readChild(rootTag);
        while (!xmlReader.isEndElement(rootTag))
        {
            String tagName = xmlReader.getLocalName();
            String elementRaw = xmlReader.readOuterXml(xmlUpdater, null);

            if (!"themeElements".equals(tagName) && !"objectDefaults".equals(tagName) && !"extLst".equals(tagName))
                builder.writeRaw(elementRaw, true);
        }
    }

    ///// <summary>
    ///// Possible theme colors in order of appearance.
    ///// </summary>
    //private static readonly ThemeColor[] gAllThemeColors = new ThemeColor[]
    //        {
    //            ThemeColor.Dark1, ThemeColor.Light1, ThemeColor.Dark2, ThemeColor.Light2,
    //            ThemeColor.Accent1, ThemeColor.Accent2, ThemeColor.Accent3, ThemeColor.Accent4, ThemeColor.Accent5, ThemeColor.Accent6,
    //            ThemeColor.Background1, ThemeColor.Background2,
    //            ThemeColor.Hyperlink, ThemeColor.FollowedHyperlink,
    //            ThemeColor.Text1, ThemeColor.Text2
    //        };
}

