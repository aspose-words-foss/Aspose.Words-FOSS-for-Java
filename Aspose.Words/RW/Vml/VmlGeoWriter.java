// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/08/2006 by Vladimir Averkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FormatterPal;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;


/// <summary>
/// Writes geometry-related attributes and subelements of the shape to WordML (path, v:path, v:formulas, v:handles).
/// </summary>
class VmlGeoWriter
{
    VmlGeoWriter(ShapeBase shape, NrxXmlBuilder builder)
    {
        mBuilder = builder;
        mVmlBuilder = new VmlBuilder(builder);
        mShape = shape;
    }

    /// <summary>
    /// Add geometry-related related attribute.
    /// </summary>
    /// <param name="key">Attribute key.</param>
    /// <param name="value">Attribute value.</param>
    void addAttribute(int key, Object value)
    {
        switch (key)
        {
            case ShapeAttr.LINE_ARROW_HEADS_OK:
                mArrowOk = (/*boolean*/Boolean)value;
                break;
            case ShapeAttr.GEOMETRY_FILL_OK:
                mFillOk = (/*boolean*/Boolean)value;
                break;
            case ShapeAttr.GEOMETRY_LINE_OK:
                mStrokeOk = (/*boolean*/Boolean)value;
                break;
            case ShapeAttr.GEOMETRY_SHADOW_OK:
                mShadowOk = (/*boolean*/Boolean)value;
                break;
            case ShapeAttr.GEOMETRY_THREE_DOK:
                mExtrusionOk = (/*boolean*/Boolean)value;
                break;
            case ShapeAttr.GEOMETRY_FILL_SHADE_SHAPE_OK:
                mGradientShapeOk = (/*boolean*/Boolean)value;
                break;
            case ShapeAttr.GEOMETRY_G_TEXT_OK:
                mTextpathOk = (/*boolean*/Boolean)value;
                break;
            case ShapeAttr.GEOMETRY_X_LIMO:
                mXLimo = (/*int*/Integer)value;
                break;
            case ShapeAttr.GEOMETRY_Y_LIMO:
                mYLimo = (/*int*/Integer)value;
                break;
            case ShapeAttr.GEOMETRY_CONNECTION_SITE_TYPE:
                mConnectType = VmlEnum.connectionSiteTypeToVml((/*ConnectionSiteType*/Integer)value);
                break;
            case ShapeAttr.GEOMETRY_CONNECT_LOCS:
                mConnectLocs = (PathPoint[])value;
                break;
            case ShapeAttr.GEOMETRY_CONNECT_ANGLES:
                mConnectAngles = (int[])value;
                break;
            case ShapeAttr.GEOMETRY_PATH_TEXT_BOX_RECTS:
                mTextboxRects = (PathRectangle[])value;
                break;
            case ShapeAttr.GEOMETRY_SEGMENT_INFO:
                mPathInfos = (PathInfo[])value;
                break;
            case ShapeAttr.GEOMETRY_VERTICES:
                mPoints = (PathPoint[])value;
                break;
            case ShapeAttr.GEOMETRY_FORMULAS:
                mFormulas = (Formula[])value;
                break;
            case ShapeAttr.GEOMETRY_HANDLES:
                mHandles = (Handle[])value;
                break;
            case ShapeAttr.GEOMETRY_ADJUST_1:
            case ShapeAttr.GEOMETRY_ADJUST_2:
            case ShapeAttr.GEOMETRY_ADJUST_3:
            case ShapeAttr.GEOMETRY_ADJUST_4:
            case ShapeAttr.GEOMETRY_ADJUST_5:
            case ShapeAttr.GEOMETRY_ADJUST_6:
            case ShapeAttr.GEOMETRY_ADJUST_7:
            case ShapeAttr.GEOMETRY_ADJUST_8:
            case ShapeAttr.GEOMETRY_ADJUST_9:
            case ShapeAttr.GEOMETRY_ADJUST_10:
                mGeometryAdjust[key - ShapeAttr.GEOMETRY_ADJUST_1] = FormatterPal.intToXml((/*int*/Integer)value);
                break;
            default:
                // Ignore other attributes.
                break;
        }
    }

    /// <summary>
    /// Write 'adj' attribute of 'w:shape' element.
    /// </summary>
    void writeAdjAttribute()
    {
        // Roundrect has adj1, but to VML it is written as arcsize.
        if (mShape.getShapeType() == ShapeType.ROUND_RECTANGLE)
            return;
        
         if (mShape.getShapeType() == ShapeType.CUSTOM_SHAPE)
         {
             if (mGeometryAdjust[0] == null)
                 mGeometryAdjust[0] = "-11796480";
 
             if (mGeometryAdjust[2] == null)
                 mGeometryAdjust[2] = "5400";
         }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <= 9; i++)
        {
            if (mGeometryAdjust[i] != null)
                msStringBuilder.append(sb, mGeometryAdjust[i]);

            sb.append(',');
        }

        mVmlBuilder.writeVmlAttribute("adj", msString.trimEnd(sb.toString(), ','));
    }

    /// <summary>
    /// Write 'path' attribute of 'w:shape' element.
    /// </summary>
    void writePathAttribute()
    {
        if ((mPoints == null) || (mPoints.length == 0))
            return;

        if ((mPathInfos == null) || (mPathInfos.length == 0))
            return;

        StringBuilder sb = new StringBuilder();
        // write path using commands defined in ShapeAttr.GeometrySegmentInfo
        int pointsProcessed = 0; 

        for (int pathInfoIdx = 0; pathInfoIdx < mPathInfos.length; pathInfoIdx++)
        {
            PathInfo pathInfo = mPathInfos[pathInfoIdx];

            msStringBuilder.append(sb, VmlEnum.pathTypeToVml(pathInfo.getPathType()));

            int pointCount = pathInfo.getPointCount();
            for (int i = 0; i < pointCount; i++)
            {
                if (pointsProcessed >= mPoints.length)
                    throw new IllegalStateException("Not enough parameters in geometry path.");

                PathPoint pp = mPoints[pointsProcessed];
                msStringBuilder.append(sb, pathValueToVml(pp.getX()));
                sb.append(',');
                msStringBuilder.append(sb, pathValueToVml(pp.getY()));
                sb.append(',');

                pointsProcessed++;
            }

            if (pointCount > 0)
                sb.delete((sb.length() - 1), (sb.length() - 1) + (1));
        }

        mBuilder.writeAttribute("path", sb.toString());
    }

    private static String pathValueToVml(PathValue pathValue)
    {
        if (pathValue.isFormula())
            return "@" + pathValue.getValue();
        else
            return FormatterPal.intToStrNoZero(pathValue.getValue());
    }

    /// <summary>
    /// Write 'v:formulas' element.
    /// </summary>
    void writeFormulas()
    {
        if (mFormulas != null)
        {
            mBuilder.startElement("v:formulas");

            for (int i = 0; i < mFormulas.length; i++)
            {
                Formula f = mFormulas[i];
                mBuilder.startElement("v:f");
                mBuilder.writeAttributeString("eqn", getFormulaEqn(f));
                mBuilder.endElement(); //v:f
            }

            mBuilder.endElement(); //v:formulas
        }
    }

    /// <summary>
    /// Write 'v:path' element.
    /// </summary>
    void writePath()
    {
        if (hasNotDefaultPathAttrs())
        {
            mBuilder.startElement("v:path");
            mVmlBuilder.writeVmlAttributeIfNotDefault("arrowok", mArrowOk, false);
            mVmlBuilder.writeVmlAttributeIfNotDefault("fillok", mFillOk, true);
            mVmlBuilder.writeVmlAttributeIfNotDefault("strokeok", mStrokeOk, true);
            mVmlBuilder.writeVmlAttributeIfNotDefault("shadowok", mShadowOk, true);
            mVmlBuilder.writeVmlAttributeIfNotDefault("o:extrusionok", mExtrusionOk, true);
            mVmlBuilder.writeVmlAttributeIfNotDefault("gradientshapeok", mGradientShapeOk, false);
            mVmlBuilder.writeVmlAttributeIfNotDefault("textpathok", mTextpathOk, false);

            if (mXLimo != 0 || mYLimo != 0)
                mBuilder.writeAttribute("limo", msString.format("{0},{1}", mXLimo, mYLimo));

            if (mConnectLocs != null && mConnectLocs.length > 0 && mConnectType == null)
                mBuilder.writeAttribute("o:connecttype", "custom");
            else
                mBuilder.writeAttributeIfNotDefault("o:connecttype", mConnectType, "none");

            writeConnectLocs();
            writeConnectAngles();
            writeTextboxRects();
            mBuilder.endElement(); //v:path
        }
    }

    private boolean hasNotDefaultPathAttrs()
    {
        return (mArrowOk || !mFillOk || !mStrokeOk || !mShadowOk || !mExtrusionOk || mGradientShapeOk ||
                mTextpathOk || isNotDefaultConnectType() || hasLocs() || hasAngles() || hasTextboxRects());
    }

    private boolean isNotDefaultConnectType() { return (mConnectType != null && !"none".equals(mConnectType)); }

    private boolean hasLocs() { return (mConnectLocs != null && mConnectLocs.length > 0); }

    private boolean hasAngles() { return (mConnectAngles != null && mConnectAngles.length > 0); }

    private boolean hasTextboxRects() { return (mTextboxRects != null); }

    private void writeConnectLocs()
    {
        if (mConnectLocs != null && mConnectLocs.length > 0)
            mBuilder.writeAttribute("o:connectlocs", VmlUtil.pathPointsToVml(mConnectLocs, ',', ';'));
    }

    private void writeConnectAngles()
    {
        if (mConnectAngles != null && mConnectAngles.length > 0)
        {
            StringBuilder sb = new StringBuilder(32);

            for (int i = 0; i < mConnectAngles.length; i++)
            {
                int angle = mConnectAngles[i];
                msStringBuilder.append(sb, VmlUtil.fixedDegreesToVml(angle));
                sb.append(',');
            }

            sb.delete((sb.length() - 1), (sb.length() - 1) + (1));
            mBuilder.writeAttribute("o:connectangles", sb.toString());
        }
    }

    private void writeTextboxRects()
    {
        if (mTextboxRects != null)
        {
            String rects = getTextBoxRects(mTextboxRects);
            mBuilder.writeAttribute("textboxrect", rects);
        }
        else if (mShape.hasChildNodes() && (mShape.getShapeType() == ShapeType.TEXT_BOX))
        {
            // andrnosk: WORDSNET-7053 Text box rectangle is not specified so return the bounding rectangle of shape.
            String rects = getTextBoxRects(((Shape)mShape).getTextBoxRects());
            mBuilder.writeAttribute("textboxrect", rects);
        }
    }

    /// <summary>
    /// Create Text box rectangle string value.
    /// </summary>
    private static String getTextBoxRects(PathRectangle[] pathRectangle)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pathRectangle.length; i++)
        {
            PathRectangle rect = pathRectangle[i];
            msStringBuilder.append(sb, VmlUtil.pathValueToVml(rect.Left));
            sb.append(',');
            msStringBuilder.append(sb, VmlUtil.pathValueToVml(rect.Top));
            sb.append(',');
            msStringBuilder.append(sb, VmlUtil.pathValueToVml(rect.Right));
            sb.append(',');
            msStringBuilder.append(sb, VmlUtil.pathValueToVml(rect.Bottom));

            if (i < pathRectangle.length - 1)
                msStringBuilder.append(sb, ";");
        }
        return sb.toString();
    }

    /// <summary>
    /// Write 'v:handles' element.
    /// </summary>
    void writeHandles()
    {
        if (mHandles != null && mHandles.length > 0)
        {
            mBuilder.startElement("v:handles");

            for (int i = 0; i < mHandles.length; i++)
            {
                Handle h = mHandles[i];

                mBuilder.startElement("v:h");

                // NOTE: It still is not clear for me if all other handle params like Polar, Map and so on
                //       should use another class for their value. So I decided to leave the procedure intact for now.
                //       Later it should be rewritten using proper types for Handle properties, maybe.
                writeHandlePoint("position",
                    new PathValue(h.PositionX.getPackedValue(), true),
                    new PathValue(h.PositionY.getPackedValue(), true));

                if (h.HasSwitch)
                    mBuilder.writeAttributeString("switch", "");

                if (h.HasPolar)
                    writeHandlePoint("polar", h.PolarX, h.PolarY);

                if (h.HasMap)
                    writeHandlePoint("map", h.PolarX, h.PolarY);

                if (h.HasRadiusRange)
                    writeHandlePoint("radiusrange", h.XMin, h.XMax);

                if (h.HasXYRange)
                {
                    if (!((h.XMin.getValue() == Integer.MIN_VALUE) && (h.XMax.getValue() == Integer.MAX_VALUE)))
                        writeHandlePoint("xrange", h.XMin, h.XMax);

                    if (!((h.YMin.getValue() == Integer.MIN_VALUE) && (h.YMax.getValue() == Integer.MAX_VALUE)))
                        writeHandlePoint("yrange", h.YMin, h.YMax);
                }

                mBuilder.endElement(); //v:h
            }

            mBuilder.endElement(); //v:handles
        }
    }

    private void writeHandlePoint(String attribName, PathValue x, PathValue y)
    {
        mBuilder.writeAttributeString(attribName, buildPoint(getHandleValue(x), getHandleValue(y)));
    }

    private static String buildPoint(String x, String y)
    {
        return msString.format("{0},{1}", x, y);
    }

    private static String getHandleValue(PathValue value)
    {
        if (value.isFormula())
        {
            switch (value.getValue())
            {
                case 0:
                    return "topLeft";
                case 1:
                    return "bottomRight";
                case 2:
                    return "center";
                default:
                    if (value.getValue() < 256)
                        return "@" + (value.getValue() - 3);
                    else
                        return "#" + (value.getValue() - 256);
            }
        }
        else
            return Integer.toString(value.getValue());
    }

    private static String getFormulaEqn(Formula formula)
    {
        if (formula.Operation == Operation.SUM && formula.Param2 == 0 && formula.Param3 == 0)
            return buildEqnString("val", getOperand(formula.Param1, formula.isParam1Calculated()));

        String operation = VmlEnum.operationToVml(formula.Operation);

        switch (formula.Operation)
        {
            case Operation.ABS:
            case Operation.SQRT:
                return buildEqnString(
                    operation, 
                    getOperand(formula.Param1, formula.isParam1Calculated()));
            case Operation.MID:
            case Operation.MIN:
            case Operation.MAX:
            case Operation.ATAN_2:
            case Operation.SIN:
            case Operation.COS:
                return buildEqnString(
                    operation,
                    getOperand(formula.Param1, formula.isParam1Calculated()),
                    getOperand(formula.Param2, formula.isParam2Calculated()));
            default:
                return buildEqnString(
                    operation,
                    getOperand(formula.Param1, formula.isParam1Calculated()),
                    getOperand(formula.Param2, formula.isParam2Calculated()),
                    getOperand(formula.Param3, formula.isParam3Calculated()));
        }
    }

    private static String buildEqnString(/*params*/ String... args)
    {
        StringBuilder sb = new StringBuilder(32);
        msStringBuilder.append(sb, args[0]);

        for (int i = 1; i < args.length; i++)
        {
            sb.append(' ');
            msStringBuilder.append(sb, args[i]);
        }

        return sb.toString();
    }

    private static String getOperand(int value, boolean isRef)
    {
        if (isRef)
        {
            switch (value)
            {
                case 0x140:
                    return "xcenter"; // The x ordinate of the center of coordorigin, coordsize (x+w/2).
                case 0x141:
                    return "ycenter"; // The y ordinate of the center of coordorigin, coordsize (y+h/2).
                case 0x142:
                    return "width";    // The width defined by the coordsize attribute. 
                case 0x143:
                    return "height"; // The height defined by the coordsize attribute.
                case 0x153:
                    return "xlimo"; // The x value of the limo attribute.
                case 0x154:
                    return "ylimo"; // The y value of the limo attribute.
                case 0x1fc:
                    return "lineDrawn";
                case 0x4f7:
                    // The line width in output device pixels. This is used to outset lines from the edge of a rectangle on the assumption that 
                    // the implementation draws to lower right pixel in preference to the upper left pixel when a line is on a pixel boundary.
                    return "pixelLineWidth"; 
                case 0x4f8:
                    return "pixelWidth"; // The width of the shape in device pixels (i.e. the coordsize width transformed into device space).
                case 0x4f9:
                    return "pixelHeight"; // The height of the coordsize in device pixels.
                case 0x4fc:
                    return "emuWidth"; // The width of the coordsize in EMUs.
                case 0x4fd:
                    return "emuHeight"; // The height of the coordsize in EMUs.
                case 0x4fe:
                    return "emuWidth2"; // Half the width of the coordsize in EMUs.
                case 0x4ff:
                    return "emuHeight2"; // Half the height of the coordsize in EMUs.
                default:
                    if (value >= CalculatedParam.FORMULA_REFERENCE_MIN)
                    {
                        // formula reference
                        return "@" + (value - CalculatedParam.FORMULA_REFERENCE_MIN);
                    }
                    else if ((value >= CalculatedParam.ADJUST_1) && (value <= CalculatedParam.ADJUST_8))
                    {
                        // adjustment reference
                        return "#" + (value - CalculatedParam.ADJUST_1);
                    }
                    else
                    {
                        throw new IllegalStateException("Unrecognized operand value in the formula.");
                    }
            }
        }
        else
        {
            return FormatterPal.intToXml(value);
        }
    }

    private /*final*/ NrxXmlBuilder mBuilder;
    private /*final*/ VmlBuilder mVmlBuilder;
    private /*final*/ ShapeBase mShape;

    private boolean mArrowOk;
    private boolean mFillOk = true;
    private boolean mStrokeOk = true;
    private boolean mShadowOk = true;
    private boolean mExtrusionOk = true;
    private boolean mGradientShapeOk;
    private boolean mTextpathOk;
    private int mXLimo;
    private int mYLimo;
    private String mConnectType;
    private PathRectangle[] mTextboxRects;
    private PathPoint[] mConnectLocs;
    private int[] mConnectAngles;
    private PathPoint[] mPoints;
    private PathInfo[] mPathInfos;
    private /*final*/ String[] mGeometryAdjust = new String[10];
    private Formula[] mFormulas;
    private Handle[] mHandles;
}


