// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.DrColor;
import com.aspose.StringUtil;



/// <summary>
/// Writes 'v:stroke' sub-element of v:shape to WordML.
/// </summary>
class VmlStrokeWriter
{
    VmlStrokeWriter(NrxXmlBuilder builder, IVmlShapeWriterContext context)
    {
        mBuilder = builder;
        mContext = context;
    }

    /// <summary>
    /// Add 'v:stroke' related attribute.
    /// </summary>
    /// <param name="key">Attribute key.</param>
    /// <param name="value">Attribute value.</param>
    void addAttribute(int key, Object value)
    {
        mAttrCount++;

        switch (key)
        {
            case ShapeAttr.LINE_ARROW_HEADS_OK:
            {
                // This attribute is written in VmlGeoWriter
                mAttrCount--;
                break;
            }
            case ShapeAttr.LINE_BACK_COLOR:
            {
                mLineBackColor = mContext.colorToVml((DrColor)value);
                break;
            }
            case ShapeAttr.LINE_BACK_COLOR_EXT:
            {
                mLineBackBaseColor = VmlUtil.vmlBaseColor(value);
                break;
            }
            case ShapeAttr.LINE_BACK_COLOR_EXT_MOD:
            {
                mLineBackModifier = VmlUtil.vmlModifier(value);
                break;
            }
            case ShapeAttr.LINE_BACK_COLOR_EXT_K:
            case ShapeAttr.LINE_BACK_COLOR_EXT_CMY:
            {
                // RK Until we support writing an attribute, we should not increment count,
                // otherwise an empty v:stroke element will be written.
                mAttrCount--;
                break;
            }
            case ShapeAttr.LINE_COLOR:
            {
                mLineColor = mContext.colorToVml((DrColor)value);
                mAttrCount--;
                break;
            }
            case ShapeAttr.LINE_COLOR_EXT:
            {
                mLineBaseColor = VmlUtil.vmlBaseColor(value);
                mAttrCount--;
                break;
            }
            case ShapeAttr.LINE_COLOR_EXT_MOD:
            {
                mLineModifier = VmlUtil.vmlModifier(value);
                break;
            }
            case ShapeAttr.LINE_COLOR_EXT_CMY:
            case ShapeAttr.LINE_COLOR_EXT_K:
            case ShapeAttr.LINE_CR_MOD:
            {
                // Not yet supported.
                mAttrCount--;
                break;
            }
            case ShapeAttr.LINE_DASH_STYLE:
            {
                mLineDashing = VmlEnum.dashStyleToVml((/*DashStyle*/Integer)value);
                break;
            }
            case ShapeAttr.LINE_DASH_DATA:
            {
                // Not yet supported.
                mAttrCount--;
                break;
            }
            case ShapeAttr.LINE_END_ARROW:
            {
                mLineEndArrow = VmlEnum.arrowTypeToVml((/*ArrowType*/Integer)value);
                break;
            }
            case ShapeAttr.LINE_END_ARROW_LENGTH:
            {
                mLineEndArrowLength = VmlEnum.arrowLengthToVml((/*ArrowLength*/Integer)value);
                break;
            }
            case ShapeAttr.LINE_END_ARROW_WIDTH:
            {
                mLineEndArrowWidth = VmlEnum.arrowWidthToVml((/*ArrowWidth*/Integer)value);
                break;
            }
            case ShapeAttr.LINE_END_CAP_STYLE:
            {
                mLineEndCapStyle = VmlEnum.endCapToVml((/*EndCap*/Integer)value);
                break;
            }
            case ShapeAttr.LINE_FILL_BLIP_NAME:
            {
                mLineFillBlipName = (String)value;
                break;
            }
            case ShapeAttr.LINE_FILL_BLIP_NAME_FLAGS:
            case ShapeAttr.LINE_FILL_DIMENSION_TYPE:
            case ShapeAttr.LINE_FILL_HEIGHT:
            case ShapeAttr.LINE_FILL_PRESET_TEXTURE:
            case ShapeAttr.LINE_FILL_SHAPE:
            {
                // Not written.
                mAttrCount--;
                break;
            }
            case ShapeAttr.LINE_FILL_TYPE:
            {
                mLineFillType = VmlEnum.lineFillTypeToVml((/*LineFillType*/Integer)value);
                break;
            }
            case ShapeAttr.LINE_FILL_WIDTH:
            case ShapeAttr.LINE_HIT_TEST:
            {
                // Not written.
                mAttrCount--;
                break;
            }
            case ShapeAttr.LINE_IMAGE_BYTES:
            {
                mImageBytes = (byte[])value;
                break;
            }
            case ShapeAttr.LINE_INSET_PEN:
            {
                mLineInsetPen = VmlUtil.boolToVml(value);
                mAttrCount--;
                break;
            }
            case ShapeAttr.LINE_JOIN_STYLE:
            {
                mLineJoinStyle = VmlEnum.joinStyleToVml((/*JoinStyle*/Integer)value);
                break;
            }
            case ShapeAttr.LINE_MITER_LIMIT:
            case ShapeAttr.LINE_NO_LINE_DRAW_DASH:
            {
                // Not written.
                mAttrCount--;
                break;
            }
            case ShapeAttr.LINE_ON:
            {
                mLineOn = VmlUtil.boolToVml(value);
                mAttrCount--;
                break;
            }
            case ShapeAttr.LINE_OPACITY:
            {
                mLineOpacity = VmlUtil.fixedToVml(value);
                break;
            }
            case ShapeAttr.LINE_RECOLOR_FILL_AS_PICTURE:
            {
                // Not written.
                mAttrCount--;
                break;
            }
            case ShapeAttr.LINE_START_ARROW:
            {
                mLineStartArrow = VmlEnum.arrowTypeToVml((/*ArrowType*/Integer)value);
                break;
            }
            case ShapeAttr.LINE_START_ARROW_LENGTH:
            {
                mLineStartArrowLength = VmlEnum.arrowLengthToVml((/*ArrowLength*/Integer)value);
                break;
            }
            case ShapeAttr.LINE_START_ARROW_WIDTH:
            {
                mLineStartArrowWidth = VmlEnum.arrowWidthToVml((/*ArrowWidth*/Integer)value);
                break;
            }
            case ShapeAttr.LINE_STYLE:
            {
                mLineStyle = VmlEnum.shapeLineStyleToVml((/*ShapeLineStyle*/Integer)value);
                break;
            }
            case ShapeAttr.LINE_INSET_PEN_OK:
            case ShapeAttr.LINE_USE_SHAPE_ANCHOR:
            {
                // Not written.
                mAttrCount--;
                break;
            }
            case ShapeAttr.LINE_WIDTH:
            {
                if ((/*int*/Integer)value > 0)
                    mLineWidth = VmlUtil.emuToVmlPoints((/*int*/Integer)value);

                mAttrCount--;
                break;
            }
            default:
                // Do nothing.
                break;
        }
    }

    /// <summary>
    /// Write stroke attributes for shape element.
    /// </summary>
    void writeAttributes()
    {
        mBuilder.writeAttribute("stroked", mLineOn);

        String lineColorStr = mLineColor;
        if (StringUtil.hasChars(mLineBaseColor) && StringUtil.hasChars(mLineModifier))
            lineColorStr = msString.plusEqOperator(lineColorStr, msString.format(" [{0} {1}]", mLineBaseColor, mLineModifier));
        mBuilder.writeAttribute("strokecolor", lineColorStr);

        // TODO 1 Implement strokecolor CMYK.
        //ShapeAttr.LineColorExtCMY:
        //ShapeAttr.LineColorExtK:

        mBuilder.writeAttribute("strokeweight", mLineWidth);
        mBuilder.writeAttribute("insetpen", mLineInsetPen);
    }

    /// <summary>
    /// Write 'v:stroke' element.
    /// </summary>
    void write()
    {
        if (mAttrCount <= 0)
            return;

        mBuilder.startElement("v:stroke");

        mBuilder.writeAttribute(mContext.getImageSrcAttributeName(), mImageName);

        if ((mLineFillBlipName != null) || StringUtil.hasChars(mImageName))
            mBuilder.writeAttributeString("o:title", mLineFillBlipName);

        mBuilder.writeAttribute("joinstyle", mLineJoinStyle);
        mBuilder.writeAttribute("dashstyle", mLineDashing);
        mBuilder.writeAttribute("linestyle", mLineStyle);
        mBuilder.writeAttribute("endcap", mLineEndCapStyle);
        mBuilder.writeAttribute("startarrow", mLineStartArrow);
        mBuilder.writeAttribute("startarrowwidth", mLineStartArrowWidth);
        mBuilder.writeAttribute("startarrowlength", mLineStartArrowLength);
        mBuilder.writeAttribute("endarrow", mLineEndArrow);
        mBuilder.writeAttribute("endarrowwidth", mLineEndArrowWidth);
        mBuilder.writeAttribute("endarrowlength", mLineEndArrowLength);
        mBuilder.writeAttribute("opacity", mLineOpacity);

        String lineBackColorStr = mLineBackColor;
        if (StringUtil.hasChars(mLineBackBaseColor) && StringUtil.hasChars(mLineBackModifier))
            lineBackColorStr = msString.plusEqOperator(lineBackColorStr, msString.format(" [{0} {1}]", mLineBackBaseColor, mLineBackModifier));
        mBuilder.writeAttribute("color2", lineBackColorStr);

        // TODO 1 Implement stroke color2 CMYK.
        //ShapeAttr.LineBackColorExtCMY:
        //ShapeAttr.LineBackColorExtK:

        mBuilder.writeAttribute("filltype", mLineFillType);

        mBuilder.endElement(); //v:stroke
    }

    byte[] getImageBytes() { return mImageBytes; }

    String getImageName() { return mImageName; }
    void setImageName(String value) { mImageName = value; }

    String getLineJoinStyle() { return mLineJoinStyle; }
    void setLineJoinStyle(String value)
    {
        if (!StringUtil.hasChars(mLineJoinStyle) && StringUtil.hasChars(value))
            mAttrCount++;

        mLineJoinStyle = value;
    }

    private /*final*/ NrxXmlBuilder mBuilder;
    private /*final*/ IVmlShapeWriterContext mContext;

    private int mAttrCount = 0;

    private byte[] mImageBytes;
    private String mImageName = null;

    private String mLineColor = null;
    private String mLineOpacity = null;
    private String mLineBackColor = null;
    private String mLineFillType = null;
    private String mLineFillBlipName = null;
    private String mLineWidth = null;
    private String mLineStyle = null;
    private String mLineDashing = null;
    private String mLineStartArrow = null;
    private String mLineEndArrow = null;
    private String mLineStartArrowWidth = null;
    private String mLineStartArrowLength = null;
    private String mLineEndArrowWidth = null;
    private String mLineEndArrowLength = null;
    private String mLineJoinStyle = null;
    private String mLineEndCapStyle = null;
    private String mLineInsetPen = null;
    private String mLineOn = null;
    private String mLineBaseColor = null;
    private String mLineModifier = null;
    private String mLineBackBaseColor = null;
    private String mLineBackModifier = null;


    //on            Yes    defined in shape[stroked] attribute
    //color            Yes    defined in shape[strokecolor] attribute
    //weight        Yes    defined in shape[strokeweight] attribute
    //insetpen        Yes    defined in shape[insetpen] attribute
    //src            Yes    
    //o:title        Yes    
    //joinstyle        Yes    
    //dashstyle        Yes    
    //linestyle        Yes    
    //endcap        Yes    
    //startarrow    Yes    
    //startarrowwidth    Yes    
    //startarrowlength    Yes    
    //endarrow        Yes    
    //endarrowwidth    Yes    
    //endarrowlength    Yes    
    //opacity        Yes    
    //color2        Yes    
    //filltype        Yes    
    //id            No need    Not seen in our test files.
    //miterlimit    No need    Not seen in our test files.
    //imageaspect    No need    Not seen in our test files.
    //imagesize        No need    Not seen in our test files.
    //imagealignshape    No need    Not seen in our test files.
    //o:href        No need    Not seen in our test files.
    //o:althref        No need    Not seen in our test files.
    //o:forcedash    No need    Not seen in our test files.
}

