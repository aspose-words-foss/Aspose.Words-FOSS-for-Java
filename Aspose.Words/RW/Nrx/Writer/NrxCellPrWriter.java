// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Writes cell properties to DOCX or WordML.
/// This class is static.
/// </summary>
/*static*/ class NrxCellPrWriter
{
	/* Simulation of static class by using private constructor */
	private NrxCellPrWriter()
	{}

    /// <summary>
    /// Writes cell properties for DOCX or WML.
    /// </summary>
    /// <param name="cellPr">Can be null. If null, writes nothing.</param>
    /// <param name="context"></param>
    static void write(
        CellPr cellPr, 
        INrxWriterContext context) throws Exception
    {
        if (cellPr == null)
            return;

        if (cellPr.getCount() == 0)
            return;

        WordAttrCollection attrs = cellPr;
        NrxXmlBuilder builder = context.getBuilder();

        builder.startElement("w:tcPr");

        if (attrs.getFormatRevision() != null)
        {
            // Calculate and write the AfterChanges attribute collection.
            WordAttrCollection afterChanges = AttrCollectionExtension.deepClone(attrs);
            afterChanges.acceptFormatRevision();
            writeProps(afterChanges, context);

            // IN. It seems that MS Word writes edit revision for cells twice.
            // One time in cellPr and second time in revised cellPr.
            EditRevision editRevision = (attrs.getDeleteRevision() != null) ? attrs.getDeleteRevision() : attrs.getInsertRevision();
            if (editRevision != null)
                builder.writeCellRevision(editRevision, context.getNextAnnotationId());

            // Write the revision
            builder.writeRevisionStart(attrs.getFormatRevision(), "w:tcPrChange", context.getNextAnnotationId());

            // Write the BeforeChanges attribute collection.
            if (context.isDocx())
                builder.startElement("w:tcPr");

            writeProps(attrs, context);

            if (editRevision != null)
                builder.writeCellRevision(editRevision, context.getNextAnnotationId());

            if (context.isDocx())
                builder.endElement();   // w:tcPr

            builder.writeRevisionEnd();
        }
        else
        {
            EditRevision editRevision = (attrs.getDeleteRevision() != null) ? attrs.getDeleteRevision() : attrs.getInsertRevision();
            if (editRevision != null)
                builder.writeCellRevision(editRevision, context.getNextAnnotationId());

            writeProps(attrs, context);
        }

        builder.endElement();
    }

    private static void writeProps(
        AttrCollection attrs, 
        INrxWriterContext context) throws Exception
    {
        boolean isDocx = context.isDocx();
        boolean isIsoStrict = isDocx && (context.getCompliance() == OoxmlComplianceCore.ISO_STRICT);
        NrxXmlBuilder builder = context.getBuilder();

        PreferredWidth preferredWidth = null;

        String mHMerge = null;
        String mVMerge = null;

        Border bdrTop = null;
        Border bdrLeft = null;
        Border bdrBottom = null;
        Border bdrRight = null;
        Border bdrTlbr = null;
        Border bdrTrbl = null;
        Border bdrInsideH = null;
        Border bdrInsideV = null;

        Shading mShading = null;
        Object mNoWrap = null;

        PreferredWidth marginTop = null;
        PreferredWidth marginLeft = null;
        PreferredWidth marginBottom = null;
        PreferredWidth marginRight = null;

        String textDirection = null;
        Object fitText = null;
        String vAlign = null;
        Object hideMark = null;

        Object gridSpan = null;

        // This is the main loop to collect the properties.
        for (int k = 0; k < attrs.getCount(); k++)
        {
            int key = attrs.getKey(k);
            Object value = attrs.getByIndex(k);

            switch (key)
            {
                case CellAttr.PREFERRED_WIDTH:
                    preferredWidth = (PreferredWidth)value;
                    break;
                case CellAttr.HORIZONTAL_MERGE:
                    mHMerge = NrxTableEnum.cellMergeToXml((/*CellMerge*/Integer)value);
                    break;
                case CellAttr.VERTICAL_MERGE:
                    mVMerge = NrxTableEnum.cellMergeToXml((/*CellMerge*/Integer)value);
                    break;
                case CellAttr.BORDER_TOP:
                    bdrTop = (Border)value;
                    break;
                case CellAttr.BORDER_LEFT:
                    bdrLeft = (Border)value;
                    break;
                case CellAttr.BORDER_BOTTOM:
                    bdrBottom = (Border)value;
                    break;
                case CellAttr.BORDER_RIGHT:
                    bdrRight = (Border)value;
                    break;
                case CellAttr.BORDER_DIAGONAL_DOWN:
                    bdrTlbr = (Border)value;
                    break;
                case CellAttr.BORDER_DIAGONAL_UP:
                    bdrTrbl = (Border)value;
                    break;
                case CellAttr.BORDER_HORIZONTAL:
                    bdrInsideH = (Border)value;
                    break;
                case CellAttr.BORDER_VERTICAL:
                    bdrInsideV = (Border)value;
                    break;
                case CellAttr.SHADING:
                    mShading = (Shading)value;
                    break;
                case CellAttr.TOP_PADDING:
                    marginTop = PreferredWidth.fromTwipsSafe((/*int*/Integer)value);
                    break;
                case CellAttr.LEFT_PADDING:
                    marginLeft = PreferredWidth.fromTwipsSafe((/*int*/Integer)value);
                    break;
                case CellAttr.BOTTOM_PADDING:
                    marginBottom = PreferredWidth.fromTwipsSafe((/*int*/Integer)value);
                    break;
                case CellAttr.RIGHT_PADDING:
                    marginRight = PreferredWidth.fromTwipsSafe((/*int*/Integer)value);
                    break;
                case CellAttr.WRAP_TEXT:
                    mNoWrap = !(/*boolean*/Boolean)value;
                    break;
                case CellAttr.HIDE_MARK:
                    if ((/*boolean*/Boolean)value)
                        hideMark = true;
                     break;
                case CellAttr.FIT_TEXT:
                    fitText = (/*boolean*/Boolean)value;
                    break;
                case CellAttr.ORIENTATION:
                    textDirection = StyleConvertUtil.textOrientationToXml((/*TextOrientation*/Integer)value, isDocx, 
                        context.getCompliance());
                    break;
                case CellAttr.VERTICAL_ALIGNMENT:
                    vAlign = NrxTableEnum.cellVerticalAlignmentToXml((/*CellVerticalAlignment*/Integer)value);
                    break;
                case RevisionAttr.FORMAT_REVISION:
                    // RK It looks like we are not writing table revisions yet?
                    break;
                case CellAttr.SYS_CELL_SPAN:
                    gridSpan = (/*int*/Integer) value;
                    break;
                default:
                    break;
            }
        }

        // RK We do not write w:cnfStyle. It is a calculated attribute to help consumers.

        builder.writeLength("w:tcW", preferredWidth, isIsoStrict);

        // RK We do not output both gridSpan and hmerge on the same cell because is is wrong.
        // andrnosk: WORDSNET-4633 Do not output gridspan if hmerge is restart. 
        // Such situation occurs when read ODT with horizontally merged cells. 
        if ((!"restart".equals(mHMerge)) && (gridSpan != null) && ((/*int*/Integer)gridSpan > 1))
            builder.writeVal("w:gridSpan", gridSpan);

        String hMergeElem = isDocx ? "w:hMerge" : "w:hmerge";
        if ("continue".equals(mHMerge))
            builder.writeEmptyElement(hMergeElem);
        else
            builder.writeVal(hMergeElem, mHMerge);

        String vMergeElem = isDocx ? "w:vMerge" : "w:vmerge";
        if ("continue".equals(mVMerge))
            builder.writeEmptyElement(vMergeElem);
        else
            builder.writeVal(vMergeElem, mVMerge);

        builder.writeBorders("w:tcBorders",
            "w:top", bdrTop,
            (isIsoStrict) ? "w:start" : "w:left", bdrLeft,
            "w:bottom", bdrBottom,
            (isIsoStrict) ? "w:end" : "w:right", bdrRight,
            "w:insideH", bdrInsideH,
            "w:insideV", bdrInsideV,
            "w:tl2br", bdrTlbr,
            "w:tr2bl", bdrTrbl);

        builder.writeShd(mShading);

        builder.writeVal("w:noWrap", mNoWrap);
        builder.writeMargins("w:tcMar", marginTop, marginLeft, marginBottom, marginRight, isIsoStrict);
        builder.writeVal(isDocx ? "w:textDirection" : "w:textFlow", textDirection);
        builder.writeVal("w:tcFitText", fitText);
        builder.writeVal("w:vAlign", vAlign);
        builder.writeVal("w:hideMark", hideMark);
    }
}

