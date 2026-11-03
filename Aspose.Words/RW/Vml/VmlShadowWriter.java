// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.msStringBuilder;



/// <summary>
/// Writes 'v:shadow' sub-element of v:shape to WordML.
/// </summary>
class VmlShadowWriter
{
    VmlShadowWriter(NrxXmlBuilder builder, IVmlShapeWriterContext context)
    {
        mBuilder = builder;
        mContext = context;
    }

    /// <summary>
    /// Add 'v:shadow' related attribute.
    /// </summary>
    /// <param name="key">Attribute key.</param>
    /// <param name="value">Attribute value.</param>
    void addAttribute(int key, Object value)
    {
        // Determine if the added attribute is written inside 'v:shadow' element.
        // This is done to avoid writing of the empty 'v:shadow' declaration.
        if (!(value instanceof /*boolean*/Boolean && !((/*boolean*/Boolean)value)))
            mAttrCount++;

        switch (key)
        {
            case ShapeAttr.SHADOW_COLOR:
                mShadowColor = mContext.colorToVml((DrColor)value);
                break;
            case ShapeAttr.SHADOW_COLOR_EXT:
                break;
            case ShapeAttr.SHADOW_COLOR_EXT_CMY:
                break;
            case ShapeAttr.SHADOW_COLOR_EXT_K:
                break;
            case ShapeAttr.SHADOW_COLOR_EXT_MOD:
                break;
            case ShapeAttr.SHADOW_HIGHLIGHT:
                mShadowHighlight = mContext.colorToVml((DrColor)value);
                break;
            case ShapeAttr.SHADOW_HIGHLIGHT_EXT:
                break;
            case ShapeAttr.SHADOW_HIGHLIGHT_EXT_CMY:
                break;
            case ShapeAttr.SHADOW_HIGHLIGHT_EXT_K:
                break;
            case ShapeAttr.SHADOW_HIGHLIGHT_EXT_MOD:
                break;
            case ShapeAttr.SHADOW_OBSCURED:
                mShadowObscured = VmlUtil.boolToVml(value, false);
                break;
            case ShapeAttr.SHADOW_OFFSET_X:
                mShadowOffsetX = VmlUtil.emuToVmlPoints((/*int*/Integer)value);
                break;
            case ShapeAttr.SHADOW_OFFSET_Y:
                mShadowOffsetY = VmlUtil.emuToVmlPoints((/*int*/Integer)value);
                break;
            case ShapeAttr.SHADOW_ON:
                mShadowOn = VmlUtil.boolToVml(value, false);
                break;
            case ShapeAttr.SHADOW_OPACITY:
                mShadowOpacity = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.SHADOW_ORIGIN_X:
                mShadowOriginX = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.SHADOW_ORIGIN_Y:
                mShadowOriginY = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.SHADOW_PERSPECTIVE_X:
                mShadowPerspectiveX = VmlUtil.perspectiveToVml((/*int*/Integer)value);
                break;
            case ShapeAttr.SHADOW_PERSPECTIVE_Y:
                mShadowPerspectiveY = VmlUtil.perspectiveToVml((/*int*/Integer)value);
                break;
            case ShapeAttr.SHADOW_SCALE_XTO_X:
                mShadowScaleXToX = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.SHADOW_SCALE_XTO_Y:
                mShadowScaleXToY = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.SHADOW_SCALE_YTO_X:
                mShadowScaleYToX = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.SHADOW_SCALE_YTO_Y:
                mShadowScaleYToY = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.SHADOW_SECOND_OFFSET_X:
                mShadowSecondOffsetX = VmlUtil.emuToVmlPoints((/*int*/Integer)value);
                break;
            case ShapeAttr.SHADOW_SECOND_OFFSET_Y:
                mShadowSecondOffsetY = VmlUtil.emuToVmlPoints((/*int*/Integer)value);
                break;
            case ShapeAttr.SHADOW_TYPE:
                mShadowType = VmlEnum.shadowTypeToVml((/*ShadowTypeCore*/Integer)value);
                break;
            case ShapeAttr.SHADOW_WEIGHT:
                break;
            default:
                return;
        }
    }

    /// <summary>
    /// Write 'v:shadow' element.
    /// </summary>
    void write()
    {
        if (mAttrCount <= 0)
            return;

        mBuilder.startElement("v:shadow");

        mBuilder.writeAttribute("on", mShadowOn);
        mBuilder.writeAttribute("type", mShadowType);
        mBuilder.writeAttribute("color", mShadowColor);
        mBuilder.writeAttribute("color2", mShadowHighlight);
        mBuilder.writeAttribute("opacity", mShadowOpacity);
        mBuilder.writeSet("origin", mShadowOriginX, mShadowOriginY);
        mBuilder.writeSet("offset", mShadowOffsetX, mShadowOffsetY);
        mBuilder.writeSet("offset2", mShadowSecondOffsetX, mShadowSecondOffsetY);
        mBuilder.writeAttribute("matrix", shadowMatrixToVml());
        mBuilder.writeAttribute("obscured", mShadowObscured);

        mBuilder.endElement(); //v:shadow
    }

    String shadowMatrixToVml()
    {
        StringBuilder sb = new StringBuilder();

        boolean hasValue = false;

        hasValue |= appendIfHasChars(sb, mShadowScaleXToX);
        sb.append(',');
        hasValue |= appendIfHasChars(sb, mShadowScaleYToX);
        sb.append(',');
        hasValue |= appendIfHasChars(sb, mShadowScaleXToY);
        sb.append(',');
        hasValue |= appendIfHasChars(sb, mShadowScaleYToY);
        sb.append(',');
        hasValue |= appendIfHasChars(sb, mShadowPerspectiveX);
        sb.append(',');
        hasValue |= appendIfHasChars(sb, mShadowPerspectiveY);

        // There are 6 elements in the matrix, but yes, MS Word seems to trim commas at the end so there could be only 
        // 4 elements left for example. It works both with trimming and without, but we trim to match MS Word more.
        return (hasValue) ? msString.trimEnd(sb.toString(), ',') : null;
    }

    private static boolean appendIfHasChars(StringBuilder sb, String value)
    {
        if (StringUtil.hasChars(value))
        {
            msStringBuilder.append(sb, value);
            return true;
        }
        else
        {
            return false;
        }
    }

    private /*final*/ NrxXmlBuilder mBuilder;
    private /*final*/ IVmlShapeWriterContext mContext;

    private int mAttrCount = 0;

    private String mShadowType = null;
    private String mShadowColor = null;
    private String mShadowHighlight = null;
    private String mShadowOpacity = null;
    private String mShadowOffsetX = null;
    private String mShadowOffsetY = null;
    private String mShadowSecondOffsetX = null;
    private String mShadowSecondOffsetY = null;
    private String mShadowScaleXToX = null;
    private String mShadowScaleYToX = null;
    private String mShadowScaleXToY = null;
    private String mShadowScaleYToY = null;
    private String mShadowPerspectiveX = null;
    private String mShadowPerspectiveY = null;
    private String mShadowOriginX = null;
    private String mShadowOriginY = null;
    private String mShadowOn = null;
    private String mShadowObscured = null;

    //on        Yes    
    //type         Yes    
    //color        Yes    
    //color2    Yes    
    //opacity    Yes    
    //origin    Yes    
    //offset    Yes    
    //offset2    Yes    
    //matrix    Yes    
    //obscured    Yes    
    //id        No need    Not seen in our test files.
}

