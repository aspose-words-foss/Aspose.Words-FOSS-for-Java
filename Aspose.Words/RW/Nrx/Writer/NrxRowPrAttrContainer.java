// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/04/2012 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FormatterPal;


/// <summary>
/// Class container to collect table/row properties.
/// </summary>
/// <remarks>>Extracted from <see cref="NrxRowPrWriter"/> during refactoring. Main goal to reduce complexity of static Write method,
/// which was a mega method several screens long.</remarks>
class NrxRowPrAttrContainer
{
    NrxRowPrAttrContainer(TablePr tablePr, boolean isStyle, INrxWriterContext context)
    {
        IsDocx = context.isDocx();
        Compliance = context.getCompliance();

        // Collect existing attributes.
        for (int i = 0; i < tablePr.getCount(); i++)
        {
            int key = tablePr.getKey(i);
            Object value = tablePr.getByIndex(i);
            switch (key)
            {
                case TableAttr.ISTD:
                    if ((/*int*/Integer)value != 0)
                        styleId = context.getStyleId((/*int*/Integer)value);
                    break;
                case TableAttr.HTML_BLOCK_ID:
                    htmlBlockId = FormatterPal.intToXml((/*int*/Integer)value);
                    TrPrCount++;
                    break;
                case TableAttr.STYLE_ROW_BAND_SIZE:
                    styleRowBandSize = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                case TableAttr.STYLE_COL_BAND_SIZE:
                    styleColBandSize = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                case TableAttr.FRAME_DISTANCE_FROM_TOP:
                    wDistanceFromTop = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                case TableAttr.FRAME_DISTANCE_FROM_LEFT:
                    wDistanceFromLeft = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                case TableAttr.FRAME_DISTANCE_FROM_BOTTOM:
                    wDistanceFromBottom = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                case TableAttr.FRAME_DISTANCE_FROM_RIGHT:
                    wDistanceFromRight = FormatterPal.intToXml((/*int*/Integer)value);
                    break;
                case TableAttr.RELATIVE_HORIZONTAL_POSITION:
                {
                    /*RelativeHorizontalPosition*/int relativeHorizontalPosition = (/*RelativeHorizontalPosition*/Integer)value;
                    if (relativeHorizontalPosition != RelativeHorizontalPosition.DEFAULT)
                        wHorzAnchor = StyleConvertUtil.relativeHorizontalPositionToXml(relativeHorizontalPosition);
                    break;
                }
                case TableAttr.RELATIVE_VERTICAL_POSITION:
                {
                    /*RelativeVerticalPosition*/int relativeVerticalPosition = (/*RelativeVerticalPosition*/Integer)value;
                    if (relativeVerticalPosition != RelativeVerticalPosition.TABLE_DEFAULT)
                        wVertAnchor = StyleConvertUtil.relativeVerticalPositionToXml(relativeVerticalPosition);
                    break;
                }
                case TableAttr.HORIZONTAL_ALIGNMENT:
                {
                    // WORDSNET-6139 See "part 1 reference c059575_ISO_IEC_29500-1_2011 22.9.2.18 ST_XAlign (Horizontal Alignment Location)".
                    // Acceptable values: center, inside, left, outside, right
                    /*HorizontalAlignment*/int ha = (/*HorizontalAlignment*/Integer)value;
                    if (ha != HorizontalAlignment.NONE)
                        wXSpec = StyleConvertUtil.horizontalAlignmentToXml(ha);
                    break;
                }
                case TableAttr.VERTICAL_ALIGNMENT:
                {
                    // WORDSNET-6139 See "part 1 reference c059575_ISO_IEC_29500-1_2011 22.9.2.20 ST_YAlign (Vertical Alignment Location)".
                    // Acceptable values: bottom, center, inline, inside, outside, top.
                    /*VerticalAlignment*/int va = (/*VerticalAlignment*/Integer)value;
                    if (va != VerticalAlignment.NONE)
                        wYSpec = StyleConvertUtil.verticalAlignmentToXml(va);
                    break;
                }
                case TableAttr.FRAME_LEFT:
                    // WORDSNET-3623 See test remarks.
                    wX = FormatterPal.intToXml((/*int*/Integer)value + 1);
                    break;
                case TableAttr.FRAME_TOP:
                    // WORDSNET-3623 See test remarks.
                    wY = FormatterPal.intToXml((/*int*/Integer)value + 1);
                    break;
                case TableAttr.ALLOW_OVERLAP:
                    wAllowOverlap = (/*boolean*/Boolean)value;
                    break;
                case TableAttr.BIDI:
                    wBidi = (/*boolean*/Boolean)value;
                    break;
                case TableAttr.PREFERRED_WIDTH:
                    wPrefferedWidth = (PreferredWidth)value;
                    if (IsDocx) // todo 0 rk check if this should be applied to WML too.
                        TblPrExCount++;
                    break;
                case TableAttr.ALIGNMENT:
                    wAlignment = NrxTableEnum.tableAlignmentToXml((/*TableAlignment*/Integer)value, IsDocx,
                        context.getCompliance());
                    TrPrCount++;
                    TblPrExCount++;
                    break;
                case TableAttr.CELL_SPACING:
                    wCellSpacing = (PreferredWidth)value;
                    TrPrCount++;
                    TblPrExCount++;
                    break;
                case TableAttr.LEFT_INDENT:
                    wLeftIndent = PreferredWidth.fromTwipsSafe((/*int*/Integer)value);
                    TblPrExCount++;
                    break;
                case TableAttr.BORDER_TOP:
                    wBorderTop = (Border)value;
                    TblPrExCount++;
                    break;
                case TableAttr.BORDER_LEFT:
                    wBorderLeft = (Border)value;
                    TblPrExCount++;
                    break;
                case TableAttr.BORDER_BOTTOM:
                    wBorderBottom = (Border)value;
                    TblPrExCount++;
                    break;
                case TableAttr.BORDER_RIGHT:
                    wBorderRight = (Border)value;
                    TblPrExCount++;
                    break;
                case TableAttr.BORDER_HORIZONTAL:
                    wBorderHorizontal = (Border)value;
                    TblPrExCount++;
                    break;
                case TableAttr.BORDER_VERTICAL:
                    wBorderVertical = (Border)value;
                    TblPrExCount++;
                    break;
                case TableAttr.ALLOW_AUTO_FIT:
                    // We do not write this in styles.
                    if (!isStyle)
                    {
                        AllowAutoFit = value;
                        TblPrExCount++;
                    }
                    break;
                case TableAttr.TOP_PADDING:
                    marginTop = PreferredWidth.fromTwipsSafe((/*int*/Integer)value);
                    TblPrExCount++;
                    break;
                case TableAttr.LEFT_PADDING:
                    marginLeft = PreferredWidth.fromTwipsSafe((/*int*/Integer)value);
                    TblPrExCount++;
                    break;
                case TableAttr.BOTTOM_PADDING:
                    marginBottom = PreferredWidth.fromTwipsSafe((/*int*/Integer)value);
                    TblPrExCount++;
                    break;
                case TableAttr.RIGHT_PADDING:
                    marginRight = PreferredWidth.fromTwipsSafe((/*int*/Integer)value);
                    TblPrExCount++;
                    break;
                case TableAttr.SHADING:
                    wShading = (Shading)value;
                    TblPrExCount++;
                    break;
                case TableAttr.STYLE_OPTIONS:
                    wStyleLook = FormatterPal.intToStrX4(NrxTableUtil.tableStyleOptionsToNrx((/*TableStyleOptions*/Integer)value));
                    TblPrExCount++;
                    break;
                case TableAttr.ROW_HEIGHT:
                {
                    TrPrCount++;
                    wRowHeight = FormatterPal.intToXml(tablePr.getHeight());
                    /*HeightRule*/int heightRule = tablePr.getHeightRule();
                    if (heightRule != HeightRule.AT_LEAST)
                        wRowHeightRule = NrxTableEnum.heightRuleToXml(heightRule, IsDocx);
                    break;
                }
                case TableAttr.HEADING_FORMAT:
                {
                    wHeader = (/*boolean*/Boolean)value;
                    TrPrCount++;
                    break;
                }
                case TableAttr.ALLOW_BREAK_ACROSS_PAGES:
                {
                    AllowBreakAcrossPages = (/*boolean*/Boolean)value;
                    TrPrCount++;
                    break;
                }
                case TableAttr.RSID_TR:
                    rsidTr = NrxXmlUtil.intToHex((/*int*/Integer)value);
                    break;
                case TableAttr.TITLE:
                    wTblCaption = (String)value;
                    break;
                case TableAttr.DESCRIPTION:
                    wTblDescription = (String)value;
                    break;
                case TableAttr.HIDDEN:
                {
                    // WordML doesn't support this attribute and Word fails on open.
                    if (context.isDocx())
                    {
                        boolean isHidden = (/*boolean*/Boolean)value;
                        Hidden = isHidden;
                        // Do not write 'w:trPr' if the value is false.
                        if (isHidden)
                            TrPrCount++;
                    }
                    break;
                }
                case TableAttr.SYS_GRID_BEFORE:
                    GridBefore = (/*int*/Integer) value;
                    if (GridBefore > 0)
                        TrPrCount++;
                    break;
                case TableAttr.SYS_GRID_AFTER:
                    GridAfter = (/*int*/Integer)value;
                    if (GridAfter > 0)
                        TrPrCount++;
                    break;
                default:
                    break;
            }
        }

        WidthBefore = (PreferredWidth)tablePr.getDirectAttr(TableAttr.WIDTH_BEFORE);
        WidthAfter = (PreferredWidth)tablePr.getDirectAttr(TableAttr.WIDTH_AFTER);

        // Use the original width before and width after value if the new table grid was not calculated successfully.
        // Fixes scenarios like Test22709 (NrxReader uses some naive technique to assign incorrect widthBefore).
        if (!TableGridAttr.isApplied(tablePr.getCalculatedGrid()))
        {
            PreferredWidth originalValue = (PreferredWidth)tablePr.getDirectAttr(TableAttr.WIDTH_BEFORE_ORIGINAL);
            if (PreferredWidth.hasPositiveValue(originalValue))
                WidthBefore = originalValue;

            // Test19759 shows a conversion scenario where new table layout logic is not invoked.
            // The original width after in percent units is lost without this line.
            originalValue = (PreferredWidth)tablePr.getDirectAttr(TableAttr.WIDTH_AFTER_ORIGINAL);
            if (PreferredWidth.hasPositiveValue(originalValue))
                WidthAfter = originalValue;
        }

        if (PreferredWidth.hasPositiveValue(WidthBefore))
            TrPrCount++;
        if (PreferredWidth.hasPositiveValue(WidthAfter))
            TrPrCount++;

        // RK This is handling of an attribute whose default is different between model and DOCX/WML.
        // In the model we have one default value, but DOCX/WML has a different default value.
        // Hence if the attribute is missing - we have to write the default.
        if (!isStyle)
        {
            // If the attribute was not encountered in the loop above, this code below gets the default value from the model.
             if (AllowAutoFit == null)
            {
                 AllowAutoFit = tablePr.getAllowAutoFit();
                TblPrExCount++;
            }

            // But we do not want to write the value if it is true.
             if ((/*boolean*/Boolean)AllowAutoFit)
                TblPrExCount--;
        }
    }

    /*final*/ String styleId;
    /*final*/ String htmlBlockId;

    /*final*/ String styleRowBandSize;
    /*final*/ String styleColBandSize;

    /*final*/ String wDistanceFromTop;
    /*final*/ String wDistanceFromLeft;
    /*final*/ String wDistanceFromBottom;
    /*final*/ String wDistanceFromRight;

    /*final*/ String wVertAnchor;
    /*final*/ String wHorzAnchor;
    /*final*/ String wXSpec;
    /*final*/ String wX;
    /*final*/ String wYSpec;
    /*final*/ String wY;
    /*final*/ boolean wAllowOverlap = true;

    /*final*/ boolean wBidi;
    /*final*/ PreferredWidth wPrefferedWidth;
    /*final*/ String wAlignment;
    /*final*/ PreferredWidth wCellSpacing;
    /*final*/ PreferredWidth wLeftIndent;

    /*final*/ Border wBorderBottom;
    /*final*/ Border wBorderHorizontal;
    /*final*/ Border wBorderLeft;
    /*final*/ Border wBorderRight;
    /*final*/ Border wBorderTop;
    /*final*/ Border wBorderVertical;
    /*final*/ Shading wShading;

    /*final*/ PreferredWidth marginTop;
    /*final*/ PreferredWidth marginLeft;
    /*final*/ PreferredWidth marginBottom;
    /*final*/ PreferredWidth marginRight;

    /*final*/ String wStyleLook;
    /*final*/ String wRowHeight;
    /*final*/ String wRowHeightRule;
    /*final*/ Object wHeader;
    /*final*/ Object AllowBreakAcrossPages;
    /*final*/ Object AllowAutoFit;

    /*final*/ boolean Hidden;

    /*final*/ String wTblCaption;
    /*final*/ String wTblDescription;

    /*final*/ String rsidTr;
    /*final*/ String rsidR = null;

    /*final*/ int TrPrCount;
    /*final*/ int TblPrExCount;

    /*final*/ int GridBefore;
    /*final*/ int GridAfter;

    /*final*/ PreferredWidth WidthBefore;
    /*final*/ PreferredWidth WidthAfter;

    /*final*/ boolean IsDocx;
    /*final*/ /*OoxmlComplianceCore*/int Compliance;
}

