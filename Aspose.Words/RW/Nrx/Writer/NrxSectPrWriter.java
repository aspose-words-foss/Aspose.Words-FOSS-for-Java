// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/03/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FormatterPal;
import com.aspose.MathUtil;
import com.aspose.ms.System.Convert;


/// <summary>
/// Writes section properties to DOCX and WML.
/// This class is static.
/// </summary>
class NrxSectPrWriter
{
    /// <summary>
    /// No ctor.
    /// </summary>
    private NrxSectPrWriter()
    {
    }

    static void write(Section section, INrxWriterContext writer) throws Exception
    {
        SectPr sectPr = section.getSectPr();
        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement("w:sectPr");

        if (writer.isDocx())
            builder.writeAttribute("w:rsidSect", NrxXmlUtil.intToHex(sectPr.get(SectAttr.RSID)));

        if (sectPr.getFormatRevision() != null)
        {
            // Write header/footer references.
            writeHeadersFooters(section, writer);

            // Write the AfterChanges attribute collection.
            SectPr afterChanges = AttrCollectionExtension.deepClone(sectPr);
            afterChanges.acceptFormatRevision();
            writeProps(afterChanges, false, writer);

            // Write BeforeChanges attribute collection.
            builder.writeRevisionStart(sectPr.getFormatRevision(), "w:sectPrChange", writer.getNextAnnotationId());

            builder.startElement("w:sectPr");
            if (writer.isDocx())
                builder.writeAttribute("w:rsidSect", NrxXmlUtil.intToHex(sectPr.get(SectAttr.RSID)));

            // WORDSNET-25040 Check if we need to additional handling for certain revised attributes.
            SectPr deltaSectPr = calculateBeforeChangesDelta(sectPr, afterChanges);
            if (deltaSectPr.getCount() > 0)
            {
                sectPr = AttrCollectionExtension.deepClone(sectPr);
                deltaSectPr.expandTo(sectPr);
            }

            sectPr = colSpacingHack(sectPr, afterChanges);

            // Save BeforeChanges attribute collection.
            writeProps(sectPr, true, writer);

            builder.endElement();   // w:sectPr

            builder.writeRevisionEnd();
        }
        else
        {
            writeHeadersFooters(section, writer);
            writeProps(sectPr, false, writer);
        }

        builder.endElement("w:sectPr");
    }

    /// <summary>
    /// Removes column spacing if equal column spacing values are in both original and after changes.
    /// </summary>
    /// <remarks>
    /// AM. We used to always write default colSpacing value for Nrx format and this causes roundtrip error, see
    /// UnifiedTestDefect713 for example. Removing default value writing caused hundreds of gold failed.
    /// </remarks>
    private static SectPr colSpacingHack(SectPr sectPr, SectPr afterChanges)
    {
        final int KEY = SectAttr.COLUMNS_SPACING;
        if (sectPr.containsKey(KEY) && afterChanges.containsKey(KEY) && sectPr.get(KEY) == afterChanges.get(KEY))
        {
            sectPr = AttrCollectionExtension.deepClone(sectPr);
            sectPr.remove(KEY);
        }

        return sectPr;
    }

    /// <summary>
    /// Calculates attribute collection delta to handle reading trick.
    /// </summary>
    /// <remarks>
    /// This methods calculates "compensation" delta to properly handle section revision reading logic
    /// found per WORDSNET-25040
    ///
    /// Actually this methods acts very similar to beforeChanges.Collapse(afterChanges) performed for
    /// certain attributes only.
    /// </remarks>
    private static SectPr calculateBeforeChangesDelta(SectPr before, SectPr after)
    {
        SectPr newSectPr = new SectPr();
        int[] keys = new int[]
            {
                SectAttr.SECTION_START,
                SectAttr.ORIENTATION,
                SectAttr.PAGE_WIDTH,
                SectAttr.PAGE_HEIGHT,
                SectAttr.COLUMNS_SPACING
            };

        for (int key : keys)
        {
            Object beforeValue = before.fetchAttr(key);
            Object afterValue = after.fetchAttr(key);

            // Little trick, at this moment newSectPr contains default value for given key.
            Object defaultValue = newSectPr.fetchAttr(key);

            if ((beforeValue != afterValue) && (afterValue != defaultValue) && (beforeValue == defaultValue))
                newSectPr.setAttr(key, defaultValue);
        }

        return newSectPr;
    }

    private static void writeProps(AttrCollection attrs, boolean isChange, INrxWriterContext writer) throws Exception
    {
        // Attributes with null values are not written by the Builder.WriteXXX methods. Null check is performed in the builder.
        NrxXmlBuilder builder = writer.getBuilder();
        boolean isDocx = writer.isDocx();

        String pageWidth = null;
        String pageHeight = null;
        String orientation = null;
        String paperCode = null;

        String marginTop = null;
        String marginRight = null;
        String marginBottom = null;
        String marginLeft = null;
        String marginHeader = null;
        String marginFooter = null;
        String marginGutter = null;

        String trayFirst = null;
        String trayOther = null;

        String bordersZOrder = null;
        String bordersDisplay = null;
        String bordersOffset = null;
        Border borderTop = null;
        Border borderLeft = null;
        Border borderBottom = null;
        Border borderRight = null;

        String lineNumberCountBy = null;
        String lineNumberStart = null;
        String lineNumberDistance = null;
        String lineNumberRestart = null;

        String pageNumberFormat = null;
        String pageNumberStart = null;
        String chapStyle = null;
        String chapSeparator = null;

        Object colEqualWidth = null;
        String colSpace = null;
        String colNum = null;
        Object colSep = null;
        TextColumnCollectionInternal columns = null;

        String gridType = null;
        String linePitch = null;
        String charSpace = null;

        String sectionStart = null;
        String vertAlign = null;

        Object bidi = null;
        Object rtlGutter = null;
        Object titlePg = null;
        Object formProt = null;
        Object noEndnote = null;
        Object textDirection = null;
        Object footnoteColumns = null;

        // This is the main loop to collect the properties.
        for (int k = 0; k < attrs.getCount(); k++)
        {
            int key = attrs.getKey(k);
            Object value = attrs.getByIndex(k);

            switch (key)
            {
                case SectAttr.BIDI:
                    bidi = value;
                    break;
                case SectAttr.RTL_GUTTER:
                    rtlGutter = value;
                    break;
                case SectAttr.DIFFERENT_FIRST_PAGE_HEADER_FOOTER:
                    titlePg = value;
                    break;
                case SectAttr.UNLOCKED:
                    // WORDSNET-20831 Value that should be written to DOCX should be !SectAttr.Unlocked.
                    formProt = !((/*boolean*/Boolean)value);
                    break;
                case SectAttr.SUPPRESS_ENDNOTES:
                    noEndnote = value;
                    break;
                case SectAttr.SECTION_START:
                    sectionStart = NrxSectEnum.sectionStartToXml((/*SectionStart*/Integer)value, isDocx);
                    break;
                case SectAttr.VERTICAL_ALIGNMENT:
                    vertAlign = NrxSectEnum.pageVerticalAlignmentToXml((/*PageVerticalAlignment*/Integer)value);
                    break;
                case SectAttr.TEXT_FLOW:
                    textDirection =
                        StyleConvertUtil.textOrientationToXml(TextFlowOrientationConverter.fromTextFlow((/*TextFlow*/Integer)value),
                            isDocx, writer.getCompliance());
                    break;
                // docGrid attributes
                case SectAttr.GRID_TYPE:
                    gridType = NrxSectEnum.docGridTypeToXml((/*SectionLayoutMode*/Integer)value, isDocx);
                    break;
                case SectAttr.LINE_PITCH:
                    linePitch = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                case SectAttr.CHAR_SPACE:
                    charSpace = FormatterPal.intToXml((/*int*/Integer)value);
                    break;

                // pgSz attributes
                case SectAttr.PAGE_WIDTH:
                    pageWidth = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                case SectAttr.PAGE_HEIGHT:
                    pageHeight = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                case SectAttr.ORIENTATION:
                    orientation = NrxSectEnum.orientationToXml((/*Orientation*/Integer)value);
                    break;
                case SectAttr.PAPER_CODE:
                    paperCode = FormatterPal.intToXml((/*int*/Integer)value);
                    break;

                // pgMar attributes
                case SectAttr.TOP_MARGIN:
                    marginTop = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                case SectAttr.RIGHT_MARGIN:
                    marginRight = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                case SectAttr.BOTTOM_MARGIN:
                    marginBottom = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                case SectAttr.LEFT_MARGIN:
                    marginLeft = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                case SectAttr.HEADER_DISTANCE:
                    // WORDSNET-24518 Write distance as "ushort" value.
                    marginHeader = FormatterPal.intToXml((((/*ushort*/short)MathUtil.castIntToShort((/*int*/Integer)value)) & 0xFFFF));
                    break;
                case SectAttr.FOOTER_DISTANCE:
                    marginFooter = FormatterPal.intToXml((((/*ushort*/short)MathUtil.castIntToShort((/*int*/Integer)value)) & 0xFFFF));
                    break;
                case SectAttr.GUTTER:
                    marginGutter = FormatterPal.intToXml((/*int*/Integer)value);
                    break;

                // paperSrc attributes
                case SectAttr.FIRST_PAGE_TRAY:
                    // Written as int number in WordML.
                    trayFirst = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                case SectAttr.OTHER_PAGES_TRAY:
                    // Written as int number in WordML.
                    trayOther = FormatterPal.intToXml((/*int*/Integer)value);
                    break;

                // pgBorders attributes
                case SectAttr.BORDER_ALWAYS_IN_FRONT:
                    if ((/*boolean*/Boolean)value)
                        bordersZOrder = "front";
                    else
                        bordersZOrder = "back";
                    break;
                case SectAttr.BORDER_APPLIES_TO:
                    bordersDisplay = NrxSectEnum.pageBorderAppliesToToXml((/*PageBorderAppliesTo*/Integer)value, isDocx);
                    break;
                case SectAttr.BORDER_DISTANCE_FROM:
                    bordersOffset = NrxSectEnum.pageBorderDistanceFromToXml((/*PageBorderDistanceFrom*/Integer)value);
                    break;
                case SectAttr.BORDER_TOP:
                    borderTop = (Border)value;
                    break;
                case SectAttr.BORDER_LEFT:
                    borderLeft = (Border)value;
                    break;
                case SectAttr.BORDER_BOTTOM:
                    borderBottom = (Border)value;
                    break;
                case SectAttr.BORDER_RIGHT:
                    borderRight = (Border)value;
                    break;

                // lnNumType attributes
                case SectAttr.LINE_NUMBER_COUNT_BY:
                    lineNumberCountBy = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                case SectAttr.LINE_STARTING_NUMBER:
                    lineNumberStart = FormatterPal.intToXml((/*int*/Integer)value - 1);
                    break;
                case SectAttr.LINE_NUMBER_DISTANCE_FROM_TEXT:
                    lineNumberDistance = FormatterPal.intToXml(Convert.toInt32(value));
                    break;
                case SectAttr.LINE_NUMBER_RESTART_MODE:
                    lineNumberRestart = NrxSectEnum.lineNumberRestartModeToXml((/*LineNumberRestartMode*/Integer)value, isDocx);
                    break;

                // pgNumType attributes
                case SectAttr.PAGE_NUMBER_STYLE:
                    pageNumberFormat = (isDocx) ?
                        DocxEnum.numberStyleToDocx((/*NumberStyle*/Integer)value) :
                        WmlEnum.numberStyleToWml((/*NumberStyle*/Integer)value);
                    break;
                case SectAttr.PAGE_STARTING_NUMBER:
                {
                    boolean isRestart = (/*boolean*/Boolean)attrs.fetchAttr(SectAttr.RESTART_PAGE_NUMBERING);
                    if (isRestart)
                        pageNumberStart = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                }
                case SectAttr.RESTART_PAGE_NUMBERING:
                {
                    final int DEFAULT_RESTART_PAGE_NUMBER = 1;
                    if ((pageNumberStart == null) && (/*boolean*/Boolean)value)
                        pageNumberStart = FormatterPal.intToXml(DEFAULT_RESTART_PAGE_NUMBER);
                    break;
                }
                case SectAttr.HEADING_LEVEL_FOR_CHAPTER:
                    chapStyle = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                case SectAttr.CHAPTER_PAGE_SEPARATOR:
                    chapSeparator = NrxSectEnum.chapterPageSeparatorToXml((/*ChapterPageSeparator*/Integer)value, isDocx);
                    break;

                // cols attributes
                case SectAttr.COLUMNS_EVENLY_SPACED:
                    colEqualWidth = value;
                    break;
                case SectAttr.COLUMNS_SPACING:
                    colSpace = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                case SectAttr.COLUMNS_COUNT:
                    colNum = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                case SectAttr.COLUMNS_LINE_BETWEEN:
                    colSep = value;
                    break;
                case SectAttr.COLUMNS:
                    columns = (TextColumnCollectionInternal)value;
                    break;
                case SectAttr.FOOTNOTE_COLUMNS:
                    footnoteColumns = (/*int*/Integer)value;
                    break;
                default:
                    // Do nothing. Tests show enough for me.
                    break;
            }
        }

        if (!isChange)
        {
            // Seems that MS Word writes default values in WordML even if they are not set explicitly in DOC.
            // But only in the main attribute set. Change should not contain them.
            if (marginHeader == null)
                marginHeader = FormatterPal.intToXml((/*int*/Integer)SectPr.fetchDefaultAttr(SectAttr.HEADER_DISTANCE));

            if (marginFooter == null)
                marginFooter = FormatterPal.intToXml((/*int*/Integer)SectPr.fetchDefaultAttr(SectAttr.FOOTER_DISTANCE));

            if (colSpace == null)
                colSpace = FormatterPal.intToXml((/*int*/Integer)SectPr.fetchDefaultAttr(SectAttr.COLUMNS_SPACING));
        }

        writer.writeFootnotePr(attrs, false);

        builder.writeVal("w:type", sectionStart);

        builder.writeElementWithAttributes(
            "w:pgSz",
            "w:w", pageWidth,
            "w:h", pageHeight,
            "w:orient", orientation,
            "w:code", paperCode);

        builder.writeElementWithAttributes(
            "w:pgMar",
            "w:top", marginTop,
            "w:right", marginRight,
            "w:bottom", marginBottom,
            "w:left", marginLeft,
            "w:header", marginHeader,
            "w:footer", marginFooter,
            "w:gutter", marginGutter);

        builder.writeElementWithAttributes(
            "w:paperSrc",
            "w:first", trayFirst,
            "w:other", trayOther);

        if (borderTop != null || borderLeft != null || borderBottom != null || borderRight != null)
        {
            builder.startElement("w:pgBorders");
            builder.writeAttribute(isDocx ? "w:zOrder" : "w:z-order", bordersZOrder);
            builder.writeAttribute("w:display", bordersDisplay);
            builder.writeAttribute(isDocx ? "w:offsetFrom" : "w:offset-from", bordersOffset);
            builder.writeBorder("w:top", borderTop);
            builder.writeBorder("w:left", borderLeft);
            builder.writeBorder("w:bottom", borderBottom);
            builder.writeBorder("w:right", borderRight);
            builder.endElement(); //w:pgBorders
        }

        builder.writeElementWithAttributes(
            "w:lnNumType",
            isDocx ? "w:countBy" : "w:count-by", lineNumberCountBy,
            "w:start", lineNumberStart,
            "w:distance", lineNumberDistance,
            "w:restart", lineNumberRestart);

        builder.writeElementWithAttributes(
            "w:pgNumType",
            "w:fmt", pageNumberFormat,
            "w:start", pageNumberStart,
            isDocx ? "w:chapStyle" : "w:chap-style", chapStyle,
            isDocx ? "w:chapSep" : "w:chap-sep", chapSeparator);

        // TODO 6139. See "[MS-OI29500] 2.1.212 Part 1 Section 17.6.4, cols (Column Definitions)".
        // Word restricts the value of the num attribute to a value between 1 and 45.
        if (builder.writeElementWithAttributesStart(
            "w:cols",
            "w:num", colNum,
            "w:sep", colSep,
            "w:space", colSpace,
            // WORDSNET-28007 Let's do not write the 'equalWidth' attribute if it contains the default value ('true').
            "w:equalWidth", ((colEqualWidth instanceof /*boolean*/Boolean) && (/*boolean*/Boolean)colEqualWidth) ? null : colEqualWidth))
        {
            if ((columns != null) && columns.hasData())
            {
                for (int i = 0; i < columns.getCount(); i++)
                {
                    builder.writeElementWithAttributes(
                        "w:col",
                        "w:w", (columns.get(i).getRawWidth() > 0) ? (Object)columns.get(i).getRawWidth() : null,
                        // WORDSNET-24120 Google Docs cannot open a document in the edit mode, if the space attribute
                        // is not defined when cols/equalWidth is false. Let's always write the attribute.
                        // ISO/IEC 29500 doesn't specify a default value of the attribute; 0 is the default value
                        // in MS Word ([MS-OI29500]).
                        "w:space", (Object)columns.get(i).getRawSpaceAfter());
                }
            }

            builder.endElement(); //w:cols
        }

        builder.writeVal("w:formProt", formProt);

        builder.writeVal("w:vAlign", vertAlign);

        builder.writeVal("w:noEndnote", noEndnote);

        builder.writeVal("w:titlePg", titlePg);

        builder.writeVal(isDocx ? "w:textDirection" : "w:textFlow", textDirection);

        builder.writeVal("w:bidi", bidi);

        builder.writeVal("w:rtlGutter", rtlGutter);

        builder.writeElementWithAttributes(
            "w:docGrid",
            "w:type", gridType,
            isDocx ? "w:linePitch" : "w:line-pitch", linePitch,
            isDocx ? "w:charSpace" : "w:char-space", charSpace);

        if (isDocx && writer.getCompliance() != OoxmlComplianceCore.ECMA_376)
            builder.writeElementWithAttributes("w15:footnoteColumns", "w:val", footnoteColumns);
    }

    /// <summary>
    /// Write all headers/footers defined for the specified section in the correct order.
    /// </summary>
    private static void writeHeadersFooters(Section section, INrxWriterContext writer) throws Exception
    {
        writeHeaderFooter(section, HeaderFooterType.HEADER_EVEN, writer);
        writeHeaderFooter(section, HeaderFooterType.HEADER_PRIMARY, writer);
        writeHeaderFooter(section, HeaderFooterType.FOOTER_EVEN, writer);
        writeHeaderFooter(section, HeaderFooterType.FOOTER_PRIMARY, writer);
        writeHeaderFooter(section, HeaderFooterType.HEADER_FIRST, writer);
        writeHeaderFooter(section, HeaderFooterType.FOOTER_FIRST, writer);
    }

    private static void writeHeaderFooter(Section section, /*HeaderFooterType*/int headerFooterType, INrxWriterContext writer) throws Exception
    {
        HeaderFooter headerFooter = section.getHeadersFooters().getByHeaderFooterType(headerFooterType);
        if ((headerFooter != null) && (!headerFooter.isLinkedToPrevious()))
            writer.writeHeaderFooter(headerFooter);
    }
}

