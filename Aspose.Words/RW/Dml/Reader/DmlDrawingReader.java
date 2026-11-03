// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/02/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.Drawing.msSize;
import com.aspose.Debug;
import com.aspose.MathUtil;
import com.aspose.PointList;
import com.aspose.ms.System.Drawing.msPoint;
import com.aspose.FormatterPal;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Reads a DrawingML drawing.
/// </summary>
class DmlDrawingReader
{
    /// <summary>
    /// The main method to read a DrawingML object.
    /// Call this when the reader is at the beginning of a "w:drawing" element.
    /// </summary>
    ShapeBase readDrawing(DocxDocumentReaderBase reader) throws Exception
    {
        mReader = reader;
        mXmlReader = reader.getXmlReader();

        mShapePr = new ShapePr();
        mDrawingML = null;

        while (mXmlReader.readChild("drawing"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"anchor"*/0:
                    readDrawingCore(WrapType.NONE);
                    break;
                case /*"inline"*/1:
                    readDrawingCore(WrapType.INLINE);
                    break;
                default:
                    mXmlReader.ignoreElement();
                    break;
            }
        }

        if (mDrawingML != null)
        {
            // Fill DrawingML ShapePr from DmlNode.
            mShapePr.expandTo(mDrawingML.getShapePr(), false);

            if (mShapePr.contains(ShapeAttr.Z_ORDER))
                mReader.addToZOrderList(mDrawingML);
        }

        return mDrawingML;
    }

    private void readDrawingCore(/*WrapType*/int wrapType) throws Exception
    {
        mShapePr.setAttr(ShapeAttr.WRAP_TYPE, wrapType);

        // Specifies that this object shall be positioned using the positioning
        // information in the simplePos child element (§5.5.2.13).
        boolean isSimplePos = false;

        // Read attributes.
        while (mXmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"allowOverlap"*/2:
                    // anchor
                    mShapePr.setAttr(ShapeAttr.ALLOW_OVERLAP, mXmlReader.getValueAsBool());
                    break;
                case /*"behindDoc"*/3:
                    // anchor
                    mShapePr.setAttr(ShapeAttr.BEHIND_TEXT, mXmlReader.getValueAsBool());
                    break;
                case /*"distB"*/4:
                    // anchor
                    // inline
                    mShapePr.setAttr(ShapeAttr.DISTANCE_BOTTOM, parseDistance(mXmlReader.getValue()));
                    break;
                case /*"distL"*/5:
                    // anchor
                    // inline
                    mShapePr.setAttr(ShapeAttr.DISTANCE_LEFT, parseDistance(mXmlReader.getValue()));
                    break;
                case /*"distR"*/6:
                    // anchor
                    // inline
                    mShapePr.setAttr(ShapeAttr.DISTANCE_RIGHT, parseDistance(mXmlReader.getValue()));
                    break;
                case /*"distT"*/7:
                    // anchor
                    // inline
                    mShapePr.setAttr(ShapeAttr.DISTANCE_TOP, parseDistance(mXmlReader.getValue()));
                    break;
                case /*"hidden"*/8:
                    // The 'hidden' attribute is not read/written because MS Word ignores it, see the chapter
                    // 2.1.1345 of [MS-OI29500].
                    break;
                case /*"layoutInCell"*/9:
                    // anchor
                    mShapePr.setAttr(ShapeAttr.ALLOW_IN_CELL, mXmlReader.getValueAsBool());
                    break;
                case /*"locked"*/10:
                    // anchor
                    mShapePr.setAttr(ShapeAttr.ANCHOR_LOCKED, mXmlReader.getValueAsBool());
                    break;
                case /*"relativeHeight"*/11:
                {
                    // anchor
                    int zIndex = mXmlReader.getValueAsInt();
                    // DrawingML z-index is based as in front of text.
                    mShapePr.setAttr(ShapeAttr.Z_ORDER, ZOrderUtil.zIndexToZOrder(zIndex, false));
                    break;
                }
                case /*"simplePos"*/12:
                    // anchor
                    isSimplePos = mXmlReader.getValueAsBool();
                    break;
                case /*"editId"*/13:
                case /*"anchorId"*/14:
                    // Word 2010. Ignore for now.
                    break;
                default:
                    mXmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DRAWING_ML, msString.format(WarningStrings.UNEXPECTED_TAG_OR_ATTRIBUTE, mXmlReader.getLocalName()));
                    break;
            }
        }

        mXmlReader.moveToElement();
        String tagName = mXmlReader.getLocalName();
        while (mXmlReader.readChild(tagName))
            readDrawingCoreChild(isSimplePos);
    }

    /// <summary>
    /// Reads a child of an wp:inline or wp:anchor element.
    /// </summary>
    private void readDrawingCoreChild(boolean isSimplePos) throws Exception
    {
        switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
        {
            case /*"cNvGraphicFramePr"*/15:
                readCNvGraphicFramePr();
                break;
            case /*"docPr"*/16:
                readDocPr();
                break;
            case /*"effectExtent"*/17:
                readEffectExtent();
                break;
            case /*"extent"*/18:
            {
                /*Size*/long extent = DmlBasicsReader.readCxCy(mXmlReader, mReader.getComplianceInfo());
                mShapePr.setAttr(ShapeAttr.WIDTH, ConvertUtilCore.emuToPoint(msSize.getWidth(extent)));
                mShapePr.setAttr(ShapeAttr.HEIGHT, ConvertUtilCore.emuToPoint(msSize.getHeight(extent)));
                break;
            }
            case /*"graphic"*/19:
                mDrawingML = DmlGraphicsReader.read(mReader);
                if (mDocPrExtensions != null)
                    mDrawingML.getDmlNode().setDocPrExtensions(mDocPrExtensions);
                break;
            case /*"positionH"*/20:
                readPositionH();
                break;
            case /*"positionV"*/21:
                readPositionV();
                break;
            case /*"simplePos"*/12:
            {
                if (isSimplePos)
                    readSimplePos();
                else
                    mXmlReader.ignoreElementNoWarn();
                break;
            }
            case /*"wrapNone"*/22:
                readWrap(WrapType.NONE);
                break;
            case /*"wrapSquare"*/23:
                readWrap(WrapType.SQUARE);
                break;
            case /*"wrapThrough"*/24:
                readWrap(WrapType.THROUGH);
                break;
            case /*"wrapTight"*/25:
                readWrap(WrapType.TIGHT);
                break;
            case /*"wrapTopAndBottom"*/26:
                readWrap(WrapType.TOP_BOTTOM);
                break;
             case /*"sizeRelH"*/27:
                readSizeRelH();
                break;
             case /*"sizeRelV"*/28:
                readSizeRelV();
                break;
            case /*"AlternateContent"*/29:
                readAlternateContent();
                break;
            default:
                Debug.fail(mXmlReader.getLocalName());
                mXmlReader.ignoreElement();
                break;
        }
    }

    /// <summary>
    /// Reads M.7.3.4.2 AlternateContent (Alternate Content block).
    /// An alternate content block allows for an alternative representation of information.
    /// </summary>
    private void readAlternateContent()
    {
        while (mXmlReader.readChild("AlternateContent"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                // Read only Choice to the model, Fallback values will be generated during validation.
                // Please see UpdateSizeAndPositionFromRelative method.
                case /*"Choice"*/30:
                    readAlternateContentCore();
                    break;
                case /*"Fallback"*/31:
                    mXmlReader.ignoreElement();
                    break;
                default:
                    Debug.fail(mXmlReader.getLocalName());
                    mXmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads M.7.3.4.2.1 AlternateContent Syntax
    /// </summary>
    private void readAlternateContentCore()
    {
        String tagName = mXmlReader.getLocalName();

        while (mXmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"positionV"*/21:
                    readPositionV();
                    break;
                case /*"positionH"*/20:
                    readPositionH();
                    break;
                default:
                    Debug.fail(mXmlReader.getLocalName());
                    mXmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 5.5.2.6 effectExtent (Object Extents Including Effects).
    /// This element specifies the additional extent which shall be added to each edge
    /// of the image (top, bottom, left, right) in order to compensate for any drawing
    /// effects applied to the DrawingML object.
    /// </summary>
    private void readEffectExtent()
    {
        OoxmlComplianceInfo complianceInfo = mReader.getComplianceInfo();
        while (mXmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"b"*/32:
                    mShapePr.setAttr(ShapeAttr.DML_EFFECT_EXTENT_BOTTOM,
                        MathUtil.doubleToInt(mXmlReader.getValueAsEmus(complianceInfo)));
                    break;
                case /*"l"*/33:
                    mShapePr.setAttr(ShapeAttr.DML_EFFECT_EXTENT_LEFT,
                        MathUtil.doubleToInt(mXmlReader.getValueAsEmus(complianceInfo)));
                    break;
                case /*"r"*/34:
                    mShapePr.setAttr(ShapeAttr.DML_EFFECT_EXTENT_RIGHT,
                        MathUtil.doubleToInt(mXmlReader.getValueAsEmus(complianceInfo)));
                    break;
                case /*"t"*/35:
                    mShapePr.setAttr(ShapeAttr.DML_EFFECT_EXTENT_TOP,
                        MathUtil.doubleToInt(mXmlReader.getValueAsEmus(complianceInfo)));
                    break;
                default:
                    mXmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DRAWING_ML, msString.format(WarningStrings.UNEXPECTED_TAG_OR_ATTRIBUTE, mXmlReader.getLocalName()));
                    break;
            }
        }
    }

    /// <summary>
    /// 5.5.2.16 wrapPolygon (Wrapping Polygon)
    /// This element specifies the wrapping polygon which shall be used to determine the extents
    /// to which text may wrap around the specified object in the document.
    /// </summary>
    private void readWrapPolygon()
    {
        PointList points = new PointList();

        // Has elements only.
        while (mXmlReader.readChild("wrapPolygon"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"start"*/36:
                case /*"lineTo"*/37:
                    points.add(DmlBasicsReader.readXY(mXmlReader, mReader.getComplianceInfo()));
                    break;
                default:
                    Debug.fail(mXmlReader.getLocalName());
                    mXmlReader.ignoreElement();
                    break;
            }
        }

        // OOXML might have last segment that runs back to the start point, we don't want it in the model.
        /*Point*/long first = points.get(0);
        /*Point*/long last = points.get(points.getCount() - 1);
        if (msPoint.equals(first, last))
            points.removeAt(points.getCount() - 1);

        // Convert to path point objects.
        PathPoint[] wrapPolygon = new PathPoint[points.getCount()];
        for (int i = 0; i < points.getCount(); i++)
            wrapPolygon[i] = new PathPoint(points.get(i));

        mShapePr.setAttr(ShapeAttr.WRAP_POLYGON_VERTICES, wrapPolygon);
    }

    /// <summary>
    /// 5.5.2.4 cNvGraphicFramePr (Common DrawingML Non-Visual Properties)
    /// This element specifies common non-visual DrawingML object properties for
    /// the parent DrawingML object. These properties are specified as child elements
    /// of this element.
    /// </summary>
    private void readCNvGraphicFramePr()
    {
        // Has elements only.
        while (mXmlReader.readChild("cNvGraphicFramePr"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"graphicFrameLocks"*/38:
                    DmlBasicsReader.readLocks(mXmlReader, mShapePr);
                    break;
                case /*"extLst"*/39:
                default:
                    Debug.fail(mXmlReader.getLocalName());
                    mXmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 5.5.2.5 docPr (Drawing Object Non-Visual Properties).
    /// </summary>
    private void readDocPr() throws Exception
    {
        // Read attributes.
        while (mXmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"descr"*/40:
                    mShapePr.setAttr(ShapeAttr.SHAPE_DESCRIPTION, mXmlReader.getValue());
                    break;
                case /*"hidden"*/8:
                    // WORDSNET-25714 Ignore hidden attribute for inline DML shapes.
                    if(mShapePr.getWrapType() != WrapType.INLINE)
                        mShapePr.setAttr(ShapeAttr.HIDDEN, mXmlReader.getValueAsBool());
                    break;
                case /*"id"*/41:
                    mShapePr.setAttr(ShapeAttr.SHAPE_ID, mXmlReader.getValueAsInt());
                    break;
                case /*"name"*/42:
                    mShapePr.setAttr(ShapeAttr.SHAPE_NAME, mXmlReader.getValue());
                    break;
                case /*"title"*/43:
                    mShapePr.setAttr(ShapeAttr.SHAPE_TITLE, mXmlReader.getValue());
                    break;
                default:
                    mXmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DRAWING_ML, msString.format(WarningStrings.UNEXPECTED_TAG_OR_ATTRIBUTE, mXmlReader.getLocalName()));
                    break;
            }
        }

        // Read elements.
        while (mXmlReader.readChild("docPr"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"hlinkClick"*/44:
                {
                    DmlHlink hlink = DmlHlinkReader.read(mReader);

                    mShapePr.setAttrIfNotNull(ShapeAttr.HYPERLINK_ADDRESS, hlink.getId());
                    mShapePr.setAttrIfNotNull(ShapeAttr.HYPERLINK_TARGET, hlink.getTargetFrame());
                    mShapePr.setAttrIfNotNull(ShapeAttr.SCREEN_TIP, hlink.getTooltip());
                    break;
                }
                case /*"extLst"*/39:
                    mDocPrExtensions = DmlExtensionListReader.read(mReader);
                    break;
                case /*"hlinkHover"*/45:
                default:
                    Debug.fail(mXmlReader.getLocalName());
                    mXmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 5.5.2.10 positionH (Horizontal Positioning)
    ///
    /// This element specifies the horizontal positioning of a floating DrawingML object
    /// within a WordprocessingML document.
    /// </summary>
    private void readPositionH()
    {
        // Read attributes.
        while (mXmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"relativeFrom"*/46:
                    mShapePr.setAttr(ShapeAttr.RELATIVE_HORIZONTAL_POSITION, DmlEnum.dmlToRelativeHorizontalPosition(mXmlReader.getValue()));
                    break;
                default:
                    mXmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DRAWING_ML, msString.format(WarningStrings.UNEXPECTED_TAG_OR_ATTRIBUTE, mXmlReader.getLocalName()));
                    break;
            }
        }

        // Read elements.
        while (mXmlReader.readChild("positionH"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"align"*/47:
                    mShapePr.setAttr(ShapeAttr.HORIZONTAL_ALIGNMENT, DmlEnum.dmlToHorizontalAlignment(mXmlReader.readString()));
                    break;
                case /*"posOffset"*/48:
                    mShapePr.setAttr(ShapeAttr.LEFT, ConvertUtilCore.emuToPoint(mXmlReader.readStringAsInt()));
                    break;
                // MSWord 2010 An ST_Percentage element that specifies the horizontal offset.
                case /*"pctPosHOffset"*/49:
                    mShapePr.setAttr(ShapeAttr.LEFT_PERCENT, readPercentageAsVml());
                    break;
                default:
                    Debug.fail(mXmlReader.getLocalName());
                    mXmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads value from element of percentage type and returns it converted to VML percent value.
    /// </summary>
    private int readPercentageAsVml()
    {
        double asFraction =
            DmlPercentageUtil.fromPercentOrDmlPercent(mXmlReader.readString(), mReader.getComplianceInfo());
        return DmlPercentageUtil.dmlToVmlPercent(DmlPercentageUtil.toDmlPercent(asFraction));
    }

    /// <summary>
    /// 5.5.2.11 positionV (Vertical Positioning)
    ///
    /// This element specifies the vertical positioning of a floating DrawingML object
    /// within a WordprocessingML document.
    /// </summary>
    private void readPositionV()
    {
        // Read attributes.
        while (mXmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"relativeFrom"*/46:
                    mShapePr.setAttr(ShapeAttr.RELATIVE_VERTICAL_POSITION, DmlEnum.dmlToRelativeVerticalPosition(mXmlReader.getValue()));
                    break;
                default:
                    mXmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DRAWING_ML, msString.format(WarningStrings.UNEXPECTED_TAG_OR_ATTRIBUTE, mXmlReader.getLocalName()));
                    break;
            }
        }

        // Read elements.
        while (mXmlReader.readChild("positionV"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"align"*/47:
                    mShapePr.setAttr(ShapeAttr.VERTICAL_ALIGNMENT, DmlEnum.dmlToVerticalAlignment(mXmlReader.readString()));
                    break;
                case /*"posOffset"*/48:
                    mShapePr.setAttr(ShapeAttr.TOP, ConvertUtilCore.emuToPoint(mXmlReader.readStringAsInt()));
                    break;
                // MSWord 2010 An ST_Percentage element as specified in [ISO/IEC29500-4:2011] section 12.1.2.2 and
                // [ISO/IEC29500-1:2011] section 20.1.10.40 that specifies the vertical offset.
                case /*"pctPosVOffset"*/50:
                    mShapePr.setAttr(ShapeAttr.TOP_PERCENT, readPercentageAsVml());
                    break;
                default:
                    Debug.fail(mXmlReader.getLocalName());
                    mXmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// 5.5.2.13 simplePos (Simple Positioning Coordinates)
    ///
    /// This element specifies the coordinates at which a DrawingML object shall be positioned relative
    /// to the top-left edge of its page, when the simplePos attribute is specified on the anchor
    /// element (§5.5.2.3).
    /// </summary>
    private void readSimplePos()
    {
        mShapePr.setAttr(ShapeAttr.RELATIVE_HORIZONTAL_POSITION, RelativeHorizontalPosition.PAGE);
        mShapePr.setAttr(ShapeAttr.HORIZONTAL_ALIGNMENT, HorizontalAlignment.NONE);

        mShapePr.setAttr(ShapeAttr.RELATIVE_VERTICAL_POSITION, RelativeVerticalPosition.PAGE);
        mShapePr.setAttr(ShapeAttr.VERTICAL_ALIGNMENT, VerticalAlignment.NONE);

        /*Point*/long pos = DmlBasicsReader.readXY(mXmlReader, mReader.getComplianceInfo());
        mShapePr.setAttr(ShapeAttr.LEFT, ConvertUtilCore.emuToPoint(msPoint.getX(pos)));
        mShapePr.setAttr(ShapeAttr.TOP, ConvertUtilCore.emuToPoint(msPoint.getY(pos)));
    }

    /// <summary>
    /// Reads any of the following
    /// 5.5.2.15 wrapNone (No Text Wrapping)
    /// 5.5.2.17 wrapSquare (Square Wrapping)
    /// 5.5.2.18 wrapThrough (Through Wrapping)
    /// 5.5.2.19 wrapTight (Tight Wrapping)
    /// 5.5.2.20 wrapTopAndBottom (Top and Bottom Wrapping)
    /// </summary>
    private void readWrap(/*WrapType*/int wrapType)
    {
        // WORDSNET-19671 If 'drawing' has got an 'anchor' element, then any of the 'wrap' sub-elements is possible.
        // But if there is an 'inline' element in 'drawing', then any of the 'wrap' sub-elements indicates
        // incorrect markup. MS Word ignores such 'wrap' sub-elements. We must do the same.
        //
        if (mShapePr.getWrapType() == WrapType.INLINE)
            return;

        mShapePr.setAttr(ShapeAttr.WRAP_TYPE, wrapType);

        // Read attributes.
        while (mXmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"distB"*/4:
                    mShapePr.setAttr(ShapeAttr.DISTANCE_BOTTOM, mXmlReader.getValueAsInt());
                    break;
                case /*"distL"*/5:
                    mShapePr.setAttr(ShapeAttr.DISTANCE_LEFT, mXmlReader.getValueAsInt());
                    break;
                case /*"distR"*/6:
                    mShapePr.setAttr(ShapeAttr.DISTANCE_RIGHT, mXmlReader.getValueAsInt());
                    break;
                case /*"distT"*/7:
                    mShapePr.setAttr(ShapeAttr.DISTANCE_TOP, mXmlReader.getValueAsInt());
                    break;
                case /*"wrapText"*/51:
                    mShapePr.setAttr(ShapeAttr.WRAP_SIDE, DmlEnum.dmlToWrapSide(mXmlReader.getValue()));
                    break;
                default:
                    mXmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DRAWING_ML, msString.format(WarningStrings.UNEXPECTED_TAG_OR_ATTRIBUTE, mXmlReader.getLocalName()));
                    break;
            }
        }

        // Read children elements of any wrap element.
        mXmlReader.moveToElement();
        String tagName = mXmlReader.getLocalName();
        while (mXmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"effectExtent"*/17:
                    readEffectExtent();
                    break;
                case /*"wrapPolygon"*/52:
                    readWrapPolygon();
                    break;
                default:
                    Debug.fail(mXmlReader.getLocalName());
                    mXmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// 2.19.1.3 sizeRelH
    ///
    /// If present, this element specifies that the horizontal size (width) is relative.
    /// If absent, the horizontal size is absolute.
    /// </summary>
    private void readSizeRelH()
    {
        while (mXmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"relativeFrom"*/46:
                    mShapePr.setAttr(ShapeAttr.RELATIVE_WIDTH, DmlEnum.dmlToRelativeWidth(mXmlReader.getValue()));
                    break;
                default:
                    mXmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DRAWING_ML, msString.format(WarningStrings.UNEXPECTED_TAG_OR_ATTRIBUTE, mXmlReader.getLocalName()));
                    break;
            }
        }
        // Read elements.
        while (mXmlReader.readChild("sizeRelH"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"pctWidth"*/53:
                    mShapePr.setAttr(ShapeAttr.WIDTH_PERCENT, readPercentageAsVml());
                    break;
                default:
                    Debug.fail(mXmlReader.getLocalName());
                    mXmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// 2.19.1.4 sizeRelV
    ///
    /// If present, this element specifies that the vertical size (height) is relative.
    /// If absent, the vertical size is absolute.
    /// </summary>
    private void readSizeRelV()
    {
        while (mXmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"relativeFrom"*/46:
                    mShapePr.setAttr(ShapeAttr.RELATIVE_HEIGHT, DmlEnum.dmlToRelativeHeight(mXmlReader.getValue()));
                    break;
                default:
                    mXmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DRAWING_ML, msString.format(WarningStrings.UNEXPECTED_TAG_OR_ATTRIBUTE, mXmlReader.getLocalName()));
                    break;
            }
        }
        // Read elements.
        while (mXmlReader.readChild("sizeRelV"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"pctHeight"*/54:
                    mShapePr.setAttr(ShapeAttr.HEIGHT_PERCENT, readPercentageAsVml());
                    break;
                default:
                    Debug.fail(mXmlReader.getLocalName());
                    mXmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Parses and validates the distance value, mimic MS Word behavior.
    /// </summary>
    /// <remarks>
    /// andrnosk: WORDSNET-7439 MS Word 2010 crashes when distance is negative.
    /// </remarks>
    private int parseDistance(String value)
    {
        long result = FormatterPal.parseInt64(value);

        // MS Word limits value of distance as 1584pt or 20116800 emus, all values outside the range MS Word interprets as zero.
        if (result > ConvertUtilCore.MAX_SIZE_EMUS || result < 0)
        {
            mXmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.DRAWING_ML,
            msString.format("The distance value '{0}' is outside of the range and will be interpreted as zero.\r\n                The measurement must be between 0 and 1584pt", result));
            return 0;
        }
        return (int)result;
    }

    private DocxDocumentReaderBase mReader;
    private NrxXmlReader mXmlReader;

    // We collect all shape positioning properties here during loading.
    private ShapePr mShapePr;
    private ShapeBase mDrawingML;

    private StringToObjDictionary<DmlExtension> mDocPrExtensions;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"anchor",
		"inline",
		"allowOverlap",
		"behindDoc",
		"distB",
		"distL",
		"distR",
		"distT",
		"hidden",
		"layoutInCell",
		"locked",
		"relativeHeight",
		"simplePos",
		"editId",
		"anchorId",
		"cNvGraphicFramePr",
		"docPr",
		"effectExtent",
		"extent",
		"graphic",
		"positionH",
		"positionV",
		"wrapNone",
		"wrapSquare",
		"wrapThrough",
		"wrapTight",
		"wrapTopAndBottom",
		"sizeRelH",
		"sizeRelV",
		"AlternateContent",
		"Choice",
		"Fallback",
		"b",
		"l",
		"r",
		"t",
		"start",
		"lineTo",
		"graphicFrameLocks",
		"extLst",
		"descr",
		"id",
		"name",
		"title",
		"hlinkClick",
		"hlinkHover",
		"relativeFrom",
		"align",
		"posOffset",
		"pctPosHOffset",
		"pctPosVOffset",
		"wrapText",
		"wrapPolygon",
		"pctWidth",
		"pctHeight"
	);

}

