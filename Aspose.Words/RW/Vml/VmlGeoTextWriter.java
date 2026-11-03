// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/09/2006 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;


/// <summary>
/// Writes v:textpath subelement of v:shape to WordML.
/// </summary>
class VmlGeoTextWriter 
{
    VmlGeoTextWriter(NrxXmlBuilder builder)
    {
        mBuilder = builder;
        mVmlBuilder = new VmlBuilder(builder);
    }

    /// <summary>
    /// Add v:textpath related attribute.
    /// </summary>
    /// <param name="key">Attribute key.</param>
    /// <param name="value">Attribute value.</param>
    void addAttribute(int key, Object value)
    {
        mAttrCount++;

        switch (key)
        {
            case ShapeAttr.GEO_TEXT_ON:
            {
                mOn = VmlUtil.boolToVml(value);
                break;
            }
            case ShapeAttr.GEO_TEXT_STRETCH:
            {
                mFitShape = VmlUtil.boolToVml(value);
                break;
            }
            case ShapeAttr.GEO_TEXT_SHRINK_FIT:
            {
                mTrim = VmlUtil.boolToVml(value);
                break;
            }
            case ShapeAttr.GEO_TEXT_BEST_FIT:
            {
                mFitPath = VmlUtil.boolToVml(value);
                break;
            }
            case ShapeAttr.GEO_TEXT_DX_MEASURE:
            {
                mXScale = VmlUtil.boolToVml(value);
                break;
            }
            case ShapeAttr.GEO_TEXT_TEXT:
            {
                // WORDSNET-9375 According to MSW behavior, we should use LF instead of CRLF.
                mString = ((String)value).replace("\r\n", "\n");
                break;
            }
            case ShapeAttr.GEO_TEXT_FONT:
            {
                mFontFamily = (String)value;
                break;
            }
            case ShapeAttr.GEO_TEXT_SIZE:
            {
                mFontSize = VmlUtil.fixedToVml((/*int*/Integer)value) + "pt";
                break;
            }
            case ShapeAttr.GEO_TEXT_ITALIC:
            {
                if ((/*boolean*/Boolean)value)
                    mFontStyle = "italic";
                break;
            }
            case ShapeAttr.GEO_TEXT_BOLD:
            {
                if ((/*boolean*/Boolean)value)
                    mFontWeight = "bold";
                break;
            }
            case ShapeAttr.GEO_TEXT_SMALL_CAPS:
            {
                if ((/*boolean*/Boolean)value)
                    mFontVariant = "small-caps";
                break;
            }
            case ShapeAttr.GEO_TEXT_UNDERLINE:
            {
                if ((/*boolean*/Boolean)value)
                    mTextDecoration = "underline";
                break;
            }
            case ShapeAttr.GEO_TEXT_STRIKE_THROUGH:
            {
                if ((/*boolean*/Boolean)value)
                    mTextDecoration = "line-through";
                break;
            }
            case ShapeAttr.GEO_TEXT_SHADOW:
            {
                if ((/*boolean*/Boolean)value)
                    mMsoTextShadow = "auto";
                break;
            }
            case ShapeAttr.GEO_TEXT_ALIGN:
            {
                mVTextAlign = VmlEnum.textPathAlignmentToVml((/*TextPathAlignment*/Integer)value);
                break;
            }
            case ShapeAttr.GEO_TEXT_TIGHT:
            {
                if ((/*boolean*/Boolean)value)
                    mVTextSpacingMode = "tightening";
                break;
            }
            case ShapeAttr.GEO_TEXT_SPACING:
            {
                mVTextSpacing = VmlUtil.fixedToVml((/*int*/Integer)value);
                break;
            }
            case ShapeAttr.GEO_TEXT_KERNING:
            {
                mVTextKern = VmlUtil.boolToVml(value);
                break;
            }
            case ShapeAttr.GEO_TEXT_REVERSE_ROWS:
            {
                mVTextReverse = VmlUtil.boolToVml(value);
                break;
            }
            case ShapeAttr.GEO_TEXT_NORMALIZE:
            {
                mVSameLetterHeights = VmlUtil.boolToVml(value);
                break;
            }
            case ShapeAttr.GEO_TEXT_VERTICAL:
            {
                mVRotateLetters = VmlUtil.boolToVml(value);
                break;
            }
            default:
                return;
        }
    }

    /// <summary>
    /// Write 'v:textpath' element.
    /// </summary>
    void write()
    {
        if (mAttrCount > 0)
        {
            mBuilder.startElement("v:textpath");

            mVmlBuilder.writeVmlAttributeIfNotDefault("on", mOn, "t");

            VmlCssStyleBuilder style = new VmlCssStyleBuilder();

            // Do not write if we don't have the value because on Java it might appear as "null".
            if (StringUtil.hasChars(mFontFamily))
                style.addFontFamily(mFontFamily);

            style.add("font-size", mFontSize);
            style.add("font-weight", mFontWeight);
            style.add("font-style", mFontStyle);
            style.add("font-variant", mFontVariant);
            style.add("v-text-align", mVTextAlign);
            style.add("v-rotate-letters", mVRotateLetters);
            style.add("v-same-letter-heights", mVSameLetterHeights);
            style.add("v-text-kern", mVTextKern);
            style.add("v-text-reverse", mVTextReverse);
            style.add("v-text-spacing", mVTextSpacing);
            style.add("v-text-spacing-mode", mVTextSpacingMode);
            style.add("mso-text-shadow", mMsoTextShadow);
            style.add("text-decoration", mTextDecoration);
            mBuilder.writeAttribute("style", style.toCss());

            mVmlBuilder.writeVmlAttributeIfNotDefault("fitshape", mFitShape, "t");
            mVmlBuilder.writeVmlAttribute("trim", mTrim);
            mVmlBuilder.writeVmlAttribute("fitpath", mFitPath);
            mVmlBuilder.writeVmlAttribute("xscale", mXScale);
            mVmlBuilder.writeVmlAttribute("string", mString);

            mBuilder.endElement(); //v:textpath
        }
    }

    private /*final*/ NrxXmlBuilder mBuilder;
    private /*final*/ VmlBuilder mVmlBuilder;

    private int mAttrCount = 0;

    private Object mOn;
    private String mFitShape;
    private String mTrim;
    private String mFitPath;
    private String mXScale;
    private String mString;

    // style attributes:
    private String mFontFamily;
    private String mFontSize;
    private String mFontWeight;
    private String mFontStyle;
    private String mFontVariant;
    private String mVTextAlign;
    private String mVTextSpacing;
    private String mVTextReverse;
    private String mVRotateLetters;
    private String mVTextKern;
    private String mVTextSpacingMode;
    private String mVSameLetterHeights;
    private String mMsoTextShadow;
    private String mTextDecoration;

    // <v:textpath
    // style="
    //   font-family:&quot Arial Black&quot
    //   font-size:20pt
    //   font-weight:bold
    //   font-style:italic
    //   font-variant:small-caps
    //   v-text-align:letter-justify
    //   v-text-spacing:1.5
    //   v-text-reverse:t
    //   v-rotate-letters:t
    //   v-text-kern:t
    //   v-text-spacing-mode:tightening
    //   v-same-letter-heights:t
    //   mso-text-shadow:auto
    //   text-decoration:line-through"
    // fitshape="f"
    // trim="t"
    // fitpath="t"
    // xscale="f"
    // string="Your Text Here" />
    
    //    on
    //    style
    //        font-family
    //        font-size
    //        font-style
    //            normal (default) 
    //            italic
    //        font-weight
    //            normal (default)
    //            bold
    //        font-variant
    //            normal (default)
    //            small-caps
    //        text-decoration
    //            none (default)
    //            underline
    //            line-through
    //        mso-text-shadow
    //            auto
    //        v-text-align
    //            left (default)
    //            right
    //            center
    //            justify
    //            letter-justify
    //            stretch-justify
    //        v-text-spacing-mode
    //            tracking (default)
    //            tightening
    //        v-text-spacing
    //        v-text-kern
    //        v-text-reverse
    //        v-same-letter-heights
    //        v-rotate-letters
    //    fitshape
    //    trim
    //    fitpath
    //    xscale
    //    string
}

