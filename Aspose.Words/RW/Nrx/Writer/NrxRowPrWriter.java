// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/07/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.FormatterPal;
import com.aspose.ms.System.msString;


/// <summary>
/// Writes table/row properties to DOCX or WordML.
/// </summary>
/*static*/ class NrxRowPrWriter
{
	/* Simulation of static class by using private constructor */
	private NrxRowPrWriter()
	{}

    /// <summary>
    /// Writes tblPr or tblPrEx for a table or style.
    /// </summary>
    /// <param name="attrs">Can be null. If null, writes nothing.</param>
    /// <param name="context"></param>
    /// <param name="isTblPr">True for writing table properties. False for writing row properties.</param>
    /// <param name="isStyle">True when writing properties of a table style; false when writing properties of a table.</param>
    static void write(
        TablePr attrs,
        boolean isTblPr,
        boolean isStyle,
        INrxWriterContext context) throws Exception
    {
        if (attrs == null)
            return;

        NrxXmlBuilder builder = context.getBuilder();

        NrxRowPrAttrContainer attrContainer = new NrxRowPrAttrContainer(getAfterChanges(attrs), isStyle, context);

        TablePr beforeChanges = getBeforeChanges(attrs);

        // AM. Here I handle AllowAutoFit difference between model and DOCX default in revision.
        // We surely have to put unified support for such handling somewhere.
        if (beforeChanges != null)
        {
            if ((attrContainer.AllowAutoFit) != null && (/*boolean*/Boolean)attrContainer.AllowAutoFit)
                beforeChanges.setAllowAutoFit(true);
        }

        if (isTblPr)
        {
            writeTablePr(attrContainer, beforeChanges, builder, isStyle, context, true);
        }
        else
        {
            if (attrContainer.IsDocx)
            {
                // We are writing row properties.
                builder.writeAttribute("w:rsidR", attrContainer.rsidR);
                builder.writeAttribute("w:rsidTr", attrContainer.rsidTr);
            }

            writeTablePrEx(attrContainer, beforeChanges, builder, isStyle, context, true);

            writeRowPr(attrContainer, beforeChanges, builder, isStyle, context, true);
        }
    }

    /// <summary>
    /// Returns TablePr bag after changes, accepted TablePr bag.
    /// </summary>
    private static TablePr getAfterChanges(TablePr attrs)
    {
        TablePr afterChanges = AttrCollectionExtension.deepClone(attrs);
        afterChanges.acceptFormatRevision();

        // AcceptFormatRevision above does not handle the case
        // when an attribute is present in the original version,
        // and is removed in the revised version.
        // This is a straightforward workaround that fixes the problem for TestJira6363.
        // WithBefore present in the revision is removed in the final version.
        clearMissingFinalAttr(attrs, afterChanges, TableAttr.WIDTH_BEFORE);
        clearMissingFinalAttr(attrs, afterChanges, TableAttr.WIDTH_AFTER);

        return afterChanges;
    }

    private static void clearMissingFinalAttr(TablePr source, TablePr dest, int attr)
    {
        if (null == source.getRevisedPr().getDirectAttr(attr, RevisionsView.FINAL))
            dest.remove(attr);
    }

    /// <summary>
    /// Returns delta between attributes after accepting changes and attributes before changes.
    /// Used to write revision.
    /// Return null if there is no revision or delta is empty.
    /// </summary>
    private static TablePr getBeforeChanges(TablePr attrs)
    {
        if (attrs.hasFormatRevision())
        {
            // Calculate delta between accepted revision and formatting before changes.
            // If nothing was changed do not write format revision.
            WordAttrCollection rejectedDelta = AttrCollectionExtension.deepClone(attrs);
            WordAttrCollection accepted = getAfterChanges(attrs);

            rejectedDelta.removeEquals(accepted);

            // We do not need format revision because we already calculated delta.
            rejectedDelta.remove(RevisionAttr.FORMAT_REVISION);

            // Do not write format revision if there is nothing to write.
            if (rejectedDelta.getCount() > 0)
            {
                // If there were insert or delete revisions they would be preserved.
                rejectedDelta.setFormatRevision(attrs.getFormatRevision());
                return (TablePr)rejectedDelta;
            }
        }
        else if (attrs.hasDeleteRevision() || attrs.hasInsertRevision())
        {
            return attrs;
        }
        return null;
    }

    /// <summary>
    /// Write TablePr attributes for table.
    /// </summary>
    private static void writeTablePr(
        NrxRowPrAttrContainer attrContainer,
        TablePr beforeChanges,
        NrxXmlBuilder builder,
        boolean isStyle,
        INrxWriterContext context,
        boolean notWmlAnnotation) throws Exception
    {
        // We are writing table properties. Start writing tblPr.
        // Do not write  'w:tblPr' for Wml annotations according to spec.
        if (notWmlAnnotation)
            builder.startElement("w:tblPr");

        writeTableAttributes(attrContainer, builder, context);

        // Write common attributes for 'w:tblPr'.
        if (attrContainer.TblPrExCount > 0)
            writeCommonTableAttributes(attrContainer, builder, isStyle);

        writeRevisions("w:tblPrChange", beforeChanges, builder, isStyle, context, context.isDocx());

        if (notWmlAnnotation)
            builder.endElement();
    }

    /// <summary>
    /// Write TablePr attributes for row.
    /// </summary>
    private static void writeRowPr(
        NrxRowPrAttrContainer attrContainer,
        TablePr beforeChanges,
        NrxXmlBuilder builder,
        boolean isStyle,
        INrxWriterContext context,
        boolean notWmlAnnotation) throws Exception
    {
        // Start writing 'w:trPr' if there are attributes in it or if there is Insert or delete revision.
        if ((attrContainer.TrPrCount > 0) || (beforeChanges != null) && (beforeChanges.hasDeleteRevision() || beforeChanges.hasInsertRevision()))
        {
            boolean isIsoStrict = context.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

            // Do not write  'w:trPr' for Wml annotations.
            if (notWmlAnnotation)
                builder.startElement("w:trPr");

            builder.writeVal("w:divId", attrContainer.htmlBlockId);

            if (attrContainer.GridBefore != 0)
                builder.writeVal("w:gridBefore", FormatterPal.intToXml(attrContainer.GridBefore));

            if (attrContainer.GridAfter > 0)
                builder.writeVal("w:gridAfter", FormatterPal.intToXml(attrContainer.GridAfter));

            // It appears that MS Word does not write zero values, regardless of the type.
            if (PreferredWidth.hasPositiveValue(attrContainer.WidthBefore))
                builder.writeLength("w:wBefore", attrContainer.WidthBefore, isIsoStrict);

            // It appears that MS Word does not write zero values, regardless of the type.
            if (PreferredWidth.hasPositiveValue(attrContainer.WidthAfter))
                builder.writeLength("w:wAfter", attrContainer.WidthAfter, isIsoStrict);

            // Note WordML doesn't support this attribute.
            if (attrContainer.Hidden)
                builder.writeEmptyElement("w:hidden");

            // andrnosk: WORDSNET-9806 There can be explicitly set "w:cantSplit" attribute with zero value.
            // In this case we have to preserve this values as is, like MS Word does.
            if (attrContainer.AllowBreakAcrossPages != null)
            {
                if ((/*boolean*/Boolean)attrContainer.AllowBreakAcrossPages)
                    builder.writeVal("w:cantSplit", 0);
                else
                    builder.writeEmptyElement("w:cantSplit");
            }

            builder.writeElementWithAttributes(
                "w:trHeight",
                (attrContainer.IsDocx) ? "w:hRule" : "w:h-rule", attrContainer.wRowHeightRule,
                "w:val", attrContainer.wRowHeight);

            if (attrContainer.wHeader != null)
            {
                if ((/*boolean*/Boolean)attrContainer.wHeader)
                    builder.writeEmptyElement("w:tblHeader");
                else
                    builder.writeVal("w:tblHeader", 0);
            }

            builder.writeLength("w:tblCellSpacing", attrContainer.wCellSpacing, isIsoStrict);
            builder.writeVal("w:jc", attrContainer.wAlignment);

            writeRevisions("w:trPrChange", beforeChanges, builder, isStyle, context, context.isDocx());

            if (beforeChanges != null)
            {
                // Write row delete revision.
                if (beforeChanges.hasDeleteRevision())
                {
                    builder.writeRevisionStart(beforeChanges.getDeleteRevision(), context.getNextAnnotationId());
                    builder.writeRevisionEnd();
                }

                // Write row insert revision.
                if (beforeChanges.hasInsertRevision())
                {
                    builder.writeRevisionStart(beforeChanges.getInsertRevision(), context.getNextAnnotationId());
                    builder.writeRevisionEnd();
                }
            }

            if (notWmlAnnotation)
                builder.endElement();
        }
    }

    /// <summary>
    /// Write TablePr attributes for table-level property exceptions (TablePrEx).
    /// </summary>
    private static void writeTablePrEx(
        NrxRowPrAttrContainer attrContainer,
        TablePr beforeChanges,
        NrxXmlBuilder builder,
        boolean isStyle,
        INrxWriterContext context,
        boolean notWmlAnnotation) throws Exception
    {
        // Start writing 'w:tblPrEx' if there are attributes in it.
        // WORDSNET-14969 Style can not contain "w:tblPrEx" element according to OOXML spec.
        if (isStyle || (attrContainer.TblPrExCount <= 0))
            return;

        // Do not write 'w:tblPrEx' for Wml annotations.
        if (notWmlAnnotation)
            builder.startElement("w:tblPrEx");

        // Write common attributes for 'w:tblPrEx'
        writeCommonTableAttributes(attrContainer, builder, isStyle);

        writeRevisions("w:tblPrExChange", beforeChanges, builder, isStyle,
            context, context.isDocx());

        if (notWmlAnnotation)
            builder.endElement();
    }

    /// <summary>
    /// Write revisions 'w:tblPrChange', 'w:tblPrExChange', 'w:trPrChange' for Docx, or 'aml:content' for Wml.
    /// </summary>
    private static void writeRevisions(String tagName,
        TablePr beforeChanges,
        NrxXmlBuilder builder,
        boolean isStyle,
        INrxWriterContext context,
        boolean notWmlAnnotation) throws Exception
    {
        // There can be Delete or Insert revisions that is why we need to check HasFormatRevision before writing.
        if ((beforeChanges != null) && beforeChanges.hasFormatRevision())
        {
            NrxRowPrAttrContainer attrContainer = new NrxRowPrAttrContainer(beforeChanges, isStyle, context);

            switch (gStringSwitchMap.of(tagName))
            {
                case /*"w:tblPrChange"*/0:
                    if (attrContainer.TblPrExCount > 0)
                    {
                        builder.writeRevisionStart(beforeChanges.getFormatRevision(), tagName, context.getNextAnnotationId());
                        writeTablePr(attrContainer, null, builder, isStyle, context, notWmlAnnotation);
                        builder.writeRevisionEnd();
                    }
                    break;
                case /*"w:tblPrExChange"*/1:
                    if (attrContainer.TblPrExCount > 0)
                    {
                        builder.writeRevisionStart(beforeChanges.getFormatRevision(), tagName, context.getNextAnnotationId());
                        writeTablePrEx(attrContainer, null, builder, isStyle, context, notWmlAnnotation);
                        builder.writeRevisionEnd();
                    }
                    break;
                case /*"w:trPrChange"*/2:
                    // Do not write revision if there are no changed attributes.
                    if (attrContainer.TrPrCount > 0)
                    {
                        builder.writeRevisionStart(beforeChanges.getFormatRevision(), tagName, context.getNextAnnotationId());
                        writeRowPr(attrContainer, null, builder, isStyle, context, notWmlAnnotation);
                        builder.writeRevisionEnd();
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected tag name.");
            }
        }
    }

    /// <summary>
    /// Write elements and attributes which does not exist inside 'w:tblPrEx'.
    /// </summary>
    private static void writeTableAttributes(NrxRowPrAttrContainer attrContainer, NrxXmlBuilder builder,
        INrxWriterContext context)
    {
        builder.writeVal("w:tblStyle", attrContainer.styleId);
        builder.writeVal("w:tblStyleRowBandSize", attrContainer.styleRowBandSize);
        builder.writeVal("w:tblStyleColBandSize", attrContainer.styleColBandSize);

        if (attrContainer.IsDocx && (context.getCompliance() != OoxmlComplianceCore.ECMA_376))
        {

            // WORDSNET-22105 Word does not open document with table title and compatibilityMode < 12 (12 is Word2007).
            /*MsWordVersionCore*/int mswVersion = context.getDocument().fetchDocumentOrGlossaryMain().getCompatibilityOptions().getMswVersion();
            boolean writeTableCaption = ((mswVersion == MsWordVersionCore.UNSPECIFIED) || (mswVersion > MsWordVersionCore.WORD_2003));

            // we're writing ISO29500 compliant document
            if (writeTableCaption)
                builder.writeVal("w:tblCaption", attrContainer.wTblCaption);
            builder.writeVal("w:tblDescription", attrContainer.wTblDescription);
        }

        builder.writeElementWithAttributes(
            "w:tblpPr",
            "w:leftFromText", attrContainer.wDistanceFromLeft,
            "w:rightFromText", attrContainer.wDistanceFromRight,
            "w:topFromText", attrContainer.wDistanceFromTop,
            "w:bottomFromText", attrContainer.wDistanceFromBottom,
            "w:vertAnchor", attrContainer.wVertAnchor,
            "w:horzAnchor", attrContainer.wHorzAnchor,
            "w:tblpXSpec", attrContainer.wXSpec,
            "w:tblpX", attrContainer.wX,
            "w:tblpYSpec", attrContainer.wYSpec,
            "w:tblpY", attrContainer.wY);

        if (!attrContainer.wAllowOverlap)
            builder.writeVal("w:tblOverlap", (attrContainer.IsDocx) ? "never" : "Never");

        if (attrContainer.wBidi)
            builder.writeEmptyElement("w:bidiVisual");
    }

    /// <summary>
    /// Write common table attributes.
    /// </summary>
    private static void writeCommonTableAttributes(NrxRowPrAttrContainer attrContainer, NrxXmlBuilder builder, boolean isStyle) throws Exception
    {
        boolean isIsoStrict = attrContainer.IsDocx && (attrContainer.Compliance == OoxmlComplianceCore.ISO_STRICT);

        builder.writeLength("w:tblW", attrContainer.wPrefferedWidth, isIsoStrict);
        builder.writeVal("w:jc", attrContainer.wAlignment);
        builder.writeLength("w:tblCellSpacing", attrContainer.wCellSpacing, isIsoStrict);
        builder.writeLength("w:tblInd", attrContainer.wLeftIndent, isIsoStrict);

        builder.writeBorders("w:tblBorders",
                             "w:top", attrContainer.wBorderTop,
                             (isIsoStrict) ? "w:start" : "w:left", attrContainer.wBorderLeft,
                             "w:bottom", attrContainer.wBorderBottom,
                             (isIsoStrict) ? "w:end" : "w:right", attrContainer.wBorderRight,
                             "w:insideH", attrContainer.wBorderHorizontal,
                             "w:insideV", attrContainer.wBorderVertical);

        builder.writeShd(attrContainer.wShading);

        if (!isStyle && !(/*boolean*/Boolean)attrContainer.AllowAutoFit)
            builder.writeElementWithAttributes("w:tblLayout", "w:type", (attrContainer.IsDocx) ? "fixed" : "Fixed");

        builder.writeMargins("w:tblCellMar", attrContainer.marginTop, attrContainer.marginLeft,
            attrContainer.marginBottom, attrContainer.marginRight, isIsoStrict);

        writeTableStyleLook(attrContainer, builder, isStyle);
    }

    private static void writeTableStyleLook(NrxRowPrAttrContainer attrContainer, NrxXmlBuilder builder,
        boolean isStyle)
    {
        // Table style options default for WML is different than in Model.
        if (!attrContainer.IsDocx && !isStyle)
        {
            if (attrContainer.wStyleLook == null)
            {
                // In case explicit table style option is missed write model default.
                builder.writeVal("w:tblLook",
                    FormatterPal.intToStrX4(NrxTableUtil.tableStyleOptionsToNrx(TableStyleOptions.DEFAULT)));
            }
            else if (!msString.equals(attrContainer.wStyleLook,
                     FormatterPal.intToStrX4(NrxTableUtil.tableStyleOptionsToNrx(TableStyleOptions.DEFAULT_2003))))
            {
                // Word doesn't write format default value. So we write only non-default too.
                builder.writeVal("w:tblLook", attrContainer.wStyleLook);
            }
        }
        else
        {
            // DOCX default is the same as Model default so write as usual.

            if (attrContainer.wStyleLook == null)
                return;

            if ((attrContainer.IsDocx) &&
                (attrContainer.Compliance == OoxmlComplianceCore.ISO_STRICT))
            {
                /*TableStyleOptions*/int options =
                    NrxTableUtil.nrxToTableStyleOptions(NrxXmlUtil.hexToInt(attrContainer.wStyleLook));
                writeIsoStrictTableStyleLook(options, builder);
            }
            else
            {
                builder.writeVal("w:tblLook", attrContainer.wStyleLook);
            }
        }
    }

    private static void writeIsoStrictTableStyleLook(/*TableStyleOptions*/int options, NrxXmlBuilder builder)
    {
        if (options == TableStyleOptions.NONE)
            return;

        // WORDSNET-19326 Write turned off values due to default value is turned on.
        builder.startElement("w:tblLook");
        builder.writeAttribute("w:firstColumn", (options & TableStyleOptions.FIRST_COLUMN) != 0);
        builder.writeAttribute("w:firstRow", (options & TableStyleOptions.FIRST_ROW) != 0);
        builder.writeAttribute("w:lastColumn", (options & TableStyleOptions.LAST_COLUMN) != 0);
        builder.writeAttribute("w:lastRow", (options & TableStyleOptions.LAST_ROW) != 0);
        // In the model this is inverted.
        builder.writeAttribute("w:noHBand", (options & TableStyleOptions.ROW_BANDS) == 0);
        // In the model this is inverted.
        builder.writeAttribute("w:noVBand", (options & TableStyleOptions.COLUMN_BANDS) == 0);
        builder.endElement();
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"w:tblPrChange",
		"w:tblPrExChange",
		"w:trPrChange"
	);

}

