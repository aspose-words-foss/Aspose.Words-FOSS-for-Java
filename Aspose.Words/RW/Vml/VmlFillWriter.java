// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// Writes v:fill sub-element of v:shape to WordML.
/// </summary>
class VmlFillWriter
{
    VmlFillWriter(ShapeBase shape, NrxXmlBuilder builder, IVmlShapeWriterContext context)
    {
        mBuilder = builder;
        mShape = shape;
        mContext = context;
    }

    /// <summary>
    /// Add v:fill related attribute.
    /// </summary>
    /// <param name="key">Attribute key.</param>
    /// <param name="value">Attribute value.</param>
    void addAttribute(int key, Object value)
    {
        mAttrCount++;

        switch (key)
        {
            case ShapeAttr.FILL_ANGLE:
                {
                    mFillAngle = VmlUtil.fixedToVml(value);
                    break;
                }
            case ShapeAttr.FILL_BACK_COLOR:
                {
                    mFillBackColor = mContext.colorToVml((DrColor)value);
                    break;
                }
            case ShapeAttr.FILL_BACK_COLOR_EXT:
                {
                    mBackBaseColor = VmlUtil.vmlBaseColor(value);
                    break;
                }
            case ShapeAttr.FILL_BACK_COLOR_EXT_CMY:
                {
                    break;
                }
            case ShapeAttr.FILL_BACK_COLOR_EXT_K:
                {
                    break;
                }
            case ShapeAttr.FILL_BACK_COLOR_EXT_MOD:
                {
                    mBackModifier = VmlUtil.vmlModifier(value);
                    break;
                }
            case ShapeAttr.FILL_BACK_OPACITY:
                {
                    mFillBackOpacity = VmlUtil.fixedToVml(value);
                    break;
                }
            case ShapeAttr.FILL_BLIP_NAME:
                {
                    mFillBlipName = (String)value;
                    break;
                }
            case ShapeAttr.FILL_BLIP_NAME_FLAGS:
                {
                    break;
                }
            case ShapeAttr.FILL_COLOR:
                {
                    mFillColor = mContext.colorToVml((DrColor)value);
                    mAttrCount--;
                    break;
                }
            case ShapeAttr.FILL_COLOR_EXT:
                {
                    mBaseColor = VmlUtil.vmlBaseColor(value);
                    mAttrCount--;
                    break;
                }
            case ShapeAttr.FILL_COLOR_EXT_CMY:
                {
                    mAttrCount--;
                    break;
                }
            case ShapeAttr.FILL_COLOR_EXT_K:
                {
                    mAttrCount--;
                    break;
                }
            case ShapeAttr.FILL_COLOR_EXT_MOD:
                {
                    mModifier = VmlUtil.vmlModifier(value);
                    break;
                }
            case ShapeAttr.FILL_CR_MOD:
                {
                    break;
                }
            case ShapeAttr.FILL_DIMENSION_TYPE:
                {
                    mFillDimensionType = VmlEnum.dzTypeToVml((/*FillDimensionType*/Integer)value);
                    break;
                }
            case ShapeAttr.FILLED:
                {
                    mFilled = VmlUtil.boolToVml(value);
                    mAttrCount--;
                    break;
                }
            case ShapeAttr.FILL_FOCUS:
                {
                    mFillFocus = VmlUtil.percentsToVml((/*int*/Integer)value);
                    break;
                }
            case ShapeAttr.FILL_HIT_TEST:
                {
                    // Ignored.
                    mAttrCount--;
                    break;
                }
            case ShapeAttr.FILL_IMAGE_BYTES:
                {
                    mImageBytes = (byte[])value;
                    break;
                }
            case ShapeAttr.FILL_NO_FILL_HIT_TEST:
                {
                    mFillNoFillHitTest = VmlUtil.boolToVml(value);
                    break;
                }
            case ShapeAttr.FILL_OPACITY:
                {
                    mFillOpacity = VmlUtil.fixedToVml(value);
                    break;
                }
            case ShapeAttr.FILL_ORIGIN_X:
                {
                    mFillOriginX = VmlUtil.fixedToVml(value);
                    break;
                }
            case ShapeAttr.FILL_ORIGIN_Y:
                {
                    mFillOriginY = VmlUtil.fixedToVml(value);
                    break;
                }
            case ShapeAttr.FILL_PRESET_TEXTURE:
                {
                    break;
                }
            case ShapeAttr.FILL_RECOLOR_AS_PICTURE:
                {
                    if ((/*boolean*/Boolean)value)
                        mFillRecolorAsPicture = VmlUtil.boolToVml(value);
                    else
                        mAttrCount--;
                    break;
                }
            case ShapeAttr.FILL_RECT_BOTTOM:
                {
                    // Ignored.
                    break;
                }
            case ShapeAttr.FILL_RECT_LEFT:
                {
                    // Ignored.
                    break;
                }
            case ShapeAttr.FILL_RECT_RIGHT:
                {
                    // Ignored.
                    break;
                }
            case ShapeAttr.FILL_RECT_TOP:
                {
                    // Ignored.
                    break;
                }
            case ShapeAttr.FILL_SHADE_COLORS:
                {
                    mFillShadeColors = VmlUtil.colorsToVml((GradientColor[])value, mContext);
                    break;
                }
            case ShapeAttr.FILL_SHADE_TYPE:
                {
                    if ((/*int*/Integer)value == (int)VmlEnum.LINEAR_SIGMA_GRADIENT)
                        mFillShadeType = "linear sigma";
                    else
                        mFillShadeType = "none";
                    break;
                }
            case ShapeAttr.FILL_SHAPE:
                {
                    // Ignored.
                    mAttrCount--;
                    break;
                }
            case ShapeAttr.FILL_SHAPE_ORIGIN_X:
                {
                    mFillShapeOriginX = VmlUtil.fixedToVml(value);
                    break;
                }
            case ShapeAttr.FILL_SHAPE_ORIGIN_Y:
                {
                    mFillShapeOriginY = VmlUtil.fixedToVml(value);
                    break;
                }
            case ShapeAttr.FILL_TO_BOTTOM:
                {
                    // Ignored.
                    break;
                }
            case ShapeAttr.FILL_TO_LEFT:
                {
                    mFillToLeft = VmlUtil.fixedToVml((/*int*/Integer)value, false);
                    break;
                }
            case ShapeAttr.FILL_TO_RIGHT:
                {
                    // Ignored.
                    break;
                }
            case ShapeAttr.FILL_TO_TOP:
                {
                    mFillToTop = VmlUtil.fixedToVml((/*int*/Integer)value, false);
                    break;
                }
            case ShapeAttr.FILL_TYPE:
                {
                    mFillType = VmlEnum.fillTypeToVml((/*FillTypeCore*/Integer)value);

                    // andrnosk: WORDSNET-4164 Need to check 'o:fill' Gradient type if 'v:fill' type is ShadeCenter or ShadeUnscale.
                    if ((/*FillTypeCore*/Integer)value == FillTypeCore.SHADE_CENTER)
                        mIsGradientCentered = true;
                    else if ((/*FillTypeCore*/Integer)value == FillTypeCore.SHADE_UNSCALE)
                        mIsGradientUnscaled = true;
                    break;
                }
            case ShapeAttr.FILL_USE_RECT:
                {
                    // Ignored.
                    mAttrCount--;
                    break;
                }
            case ShapeAttr.FILL_USE_SHAPE_ANCHOR:
                {
                    if ((/*boolean*/Boolean)value)
                        mFillUseShapeAnchor = VmlUtil.boolToVml(value);
                    else
                        mAttrCount--;
                    break;
                }
            case ShapeAttr.FILL_WIDTH:
                {
                    break;
                }
            case ShapeAttr.FILL_HEIGHT:
                {
                    break;
                }
            default:
                return;
        }
    }

    /// <summary>
    /// Write fill attributes for shape element.
    /// </summary>
    void writeAttributes()
    {
        if (mShape.getShapeType() != ShapeType.LINE)
            mBuilder.writeAttribute("filled", mFilled);

        writeFillColorAttributes("fillcolor", mFillColor, mBaseColor, mModifier);

        // TODO 1 Implement fillcolor CMYK.
        //ShapeAttr.FillColorExtCMY:
        //ShapeAttr.FillColorExtK:
    }

    /// <summary>
    /// Write 'v:fill' element.
    /// </summary>
    void write()
    {
        if (mAttrCount <= 0)
            return;

        mBuilder.startElement("v:fill");
        mBuilder.writeAttribute(mContext.getImageSrcAttributeName(), mImageName);

        // If fill has image, the "o:title" attribute should be written
        if (mImageName != null)
            mBuilder.writeAttributeString("o:title", mFillBlipName);

        mBuilder.writeAttribute("opacity", mFillOpacity);

        writeFillColorAttributes("color2", mFillBackColor, mBackBaseColor, mBackModifier);

        // TODO 1 Implement fill CMYK backcolor
        //ShapeAttr.FillBackColorExtCMY:
        //ShapeAttr.FillBackColorExtK:

        // Not sure where to get data for 'size' attribute.
        mBuilder.writeAttribute("o:opacity2", mFillBackOpacity);
        mBuilder.writeAttribute("aspect", mFillDimensionType);
        mBuilder.writeSet("origin", mFillOriginX, mFillOriginY);
        mBuilder.writeSet("position", mFillShapeOriginX, mFillShapeOriginY);
        mBuilder.writeAttribute("recolor", mFillRecolorAsPicture);
        mBuilder.writeAttribute("rotate", mFillUseShapeAnchor);
        mBuilder.writeAttribute("angle", mFillAngle);
        mBuilder.writeAttribute("colors", mFillShadeColors);

        if ((mFillToLeft != null) || (mFillToTop != null))
        {
            mBuilder.writeSet("focusposition", mFillToLeft, mFillToTop);
            // Have not seen focussize taking value other than empty string.
            mBuilder.writeAttributeString("focussize", "");
        }

        mBuilder.writeAttribute("method", mFillShadeType);
        mBuilder.writeAttribute("focus", mFillFocus);
        mBuilder.writeAttribute("type", mFillType);
        mBuilder.writeAttribute("o:detectmouseclick", mFillNoFillHitTest);

        // andrnosk: WORDSNET-4146 Write 'o:fill' attribute according to FillType.
        if (mIsGradientCentered || mIsGradientUnscaled)
            mBuilder.writeElementWithAttributes(
                "o:fill",
                "v:ext", "view",
                "type", mIsGradientCentered ? "gradientCenter" : "gradientUnscaled");

        mBuilder.endElement(); //v:fill
    }

    /// <summary>
    /// Write fill color attributes.
    /// </summary>
    private void writeFillColorAttributes(String attributeName, String fillColor, String baseColor, String modifier)
    {
        String fillColorStr = (StringUtil.hasChars(baseColor) && StringUtil.hasChars(modifier))
            ? msString.format("{0} [{1} {2}]", fillColor, mBaseColor, mModifier)
            : fillColor;
        mBuilder.writeAttribute(attributeName, fillColorStr);
    }

    byte[] getImageBytes() { return mImageBytes; }

    String getImageName() { return mImageName; }
    void setImageName(String value) { mImageName = value; }

    private /*final*/ NrxXmlBuilder mBuilder;
    private /*final*/ ShapeBase mShape;
    private /*final*/ IVmlShapeWriterContext mContext;

    private int mAttrCount = 0;

    private byte[] mImageBytes;
    private String mImageName = null;

    private boolean mIsGradientCentered = false;
    private boolean mIsGradientUnscaled = false;

    private String mFillType = null;
    private String mFillColor = null;
    private String mBaseColor = null;
    private String mModifier = null;
    private String mBackBaseColor = null;
    private String mBackModifier = null;

    private String mFillOpacity = null;
    private String mFillBackColor = null;
    private String mFillBackOpacity = null;
    private String mFillBlipName = null;
    private String mFillAngle = null;
    private String mFillFocus = null;
    private String mFillToLeft = null;
    private String mFillToTop = null;
    private String mFillDimensionType = null;
    private String mFillShadeColors = null;
    private String mFillOriginX = null;
    private String mFillOriginY = null;
    private String mFillShapeOriginX = null;
    private String mFillShapeOriginY = null;
    private String mFillShadeType = null;
    private String mFillRecolorAsPicture = null;
    private String mFillUseShapeAnchor = null;
    private String mFilled = null;
    private String mFillNoFillHitTest = null;

    //on        Yes    defined in shape[filled] attribute
    //color        Yes    defined in shape[fillcolor] attribute
    //src        Yes
    //o:title    Yes
    //opacity    Yes
    //color2    Yes
    //o:opacity2    Yes
    //aspect    Yes
    //origin    Yes
    //position    Yes
    //size        Not found    Seen only size="0,0" in TestFillStyle ms.wml(542) and that didn't correspond to any attribute in the model.
    //recolor    Yes
    //rotate    Yes
    //angle        Yes
    //colors    Yes
    //focusposition    Yes
    //focussize    Yes
    //method    Yes
    //focus        Yes
    //type        Yes
    //o:detectmouseclick    Yes
    //o:fill     Yes    sub-element specifying gradientCenter subtype of gradientRadial type
    //href    No need    Not seen in our test files.
    //althref    No need    Not seen in our test files.
}

