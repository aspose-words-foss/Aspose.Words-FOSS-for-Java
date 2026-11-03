// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/03/2011 by Denis Darkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;

   
/// <summary>
/// Specifies the document-level properties for all math markup in the document.
/// </summary>
class MathProperties implements Cloneable
{
    MathProperties deepClone()
    {
        return (MathProperties)memberwiseClone();
    }
    
    /// <summary>
    /// Returns true if any property contained here has non-default value.
    /// </summary>
    boolean isNonDefault()
    {
        return ((mBrkBin != MathBreakOnBinary.DEFAULT) ||
                (mBrkBinSub != MathBreakOnBinarySubtraction.DEFAULT) ||
                (mDefJc != OfficeMathJustification.DEFAULT) ||
                !mDispDef ||
                (mIntLim != MathLimitLocation.SUBSCRIPT_SUPERSCRIPT) ||
                (mInterSp != 0) ||
                (mIntraSp != 0) ||
                (mLMargin != 0) || 
                (mRMargin != 0) || 
                (!"".equals(mMathFont)) || 
                (mNaryLim != MathLimitLocation.UNDER_OVER) || 
                (mPostSp != 0) ||
                (mPreSp != 0) ||
                 mSmallFrac ||
                (mWrapIndent != DEFAULT_WRAP_INDENT) || 
                 mWrapRight);
    }
    
    /// <summary>
    /// Specifies how binary operators are treated when they coincide with a line break.
    /// Default: <see cref="MathBreakOnBinary.Default"/>
    /// </summary>
    /*MathBreakOnBinary*/int getBreakOnBinary() { return mBrkBin; }
    void setBreakOnBinary(/*MathBreakOnBinary*/int value) { mBrkBin = value; }
    
    /// <summary>
    /// Specifies how the subtraction operator is treated when it coincides with a line break, when
    /// <see cref="BreakOnBinary"/> is set to repeat. 
    /// By default the subtraction operator is repeated before and after the break.
    /// </summary>
    /*MathBreakOnBinarySubtraction*/int getBreakOnBinarySubtraction() { return mBrkBinSub; }
    void setBreakOnBinarySubtraction(/*MathBreakOnBinarySubtraction*/int value) { mBrkBinSub = value; }
    
    /// <summary>
    /// Specifies the default justification of display math, at the document level. Individual instances of
    /// mathematical text can overrule the default setting.
    /// Default is <see cref="OfficeMathJustification.Default"/>
    /// </summary>
    /*OfficeMathJustification*/int getDefaultJustification() { return mDefJc; }
    void setDefaultJustification(/*OfficeMathJustification*/int value) { mDefJc = value; }
    
    /// <summary>
    /// Specifies the document-level property to overwrite paragraph settings for mathematical text.
    /// Default is true and special math settings are applied.
    /// </summary>
    /// <remarks>
    /// When this value is false, then MS Word ignores the following settings:
    /// <see cref="DefaultJustification"/>, <see cref="RightMargin"/>, <see cref="LeftMargin"/>,
    /// <see cref="WrapIndent"/> and <see cref="WrapRight"/>.
    /// Instead paragraph-level setting are used.
    /// </remarks>
    boolean getUseDisplayMathDefaults() { return mDispDef; }
    void setUseDisplayMathDefaults(boolean value) { mDispDef = value; }
    
    /// <summary>
    /// Specifies spacing between equations, expressions, or other instances of mathematical text within a
    /// display math paragraph, in twips.
    /// Default 0, meaning no spacing applied between equations.
    /// </summary>
    int getInterEquationSpacing() { return mInterSp; }
    void setInterEquationSpacing(int value) { mInterSp = value; }
    
    
    /// <summary>
    /// Specifies the spacing between adjacent display math paragraphs, in twips. 
    /// Default 0, meaning no spacing is applied between adjacent math paragraphs.
    /// </summary>
    int getIntraEquationSpacing() { return mIntraSp; }
    void setIntraEquationSpacing(int value) { mIntraSp = value; }
    
    /// <summary>
    /// Specifies the document setting for the default placement of integral limits, when converted from a
    /// linear form to a two-dimensional output (professional form).
    /// Default: <see cref="MathLimitLocation.SubscriptSuperscript"/>
    /// </summary>
    /*MathLimitLocation*/int getIntegralLimitLocation() { return mIntLim; }
    void setIntegralLimitLocation(/*MathLimitLocation*/int value)
    {
        if (value == MathLimitLocation.UNDEFINED)
            throw new IllegalArgumentException("This enum value is not allowed.");
        mIntLim = value;
    }

    /// <summary>
    /// Specifies the left margin for math, in twips. 
    /// Default: 0.
    /// </summary>
    int getLeftMargin() { return mLMargin; }
    void setLeftMargin(int value) { mLMargin = value; }

    /// <summary>
    /// Specifies the right margin for math, in twips. 
    /// Default is 0.
    /// </summary>
    int getRightMargin() { return mRMargin; }
    void setRightMargin(int value) { mRMargin = value; }
    
    /// <summary>
    /// Specifies the default math font to be used in the document. If not specified, font
    /// substitution should be used to determine the most appropriate font for use throughout the document.
    /// Default: "" - empty string.
    /// </summary>
    /// <remarks>
    /// It seems that for Office documents default font is often"Cambria Math", 
    /// but for now we will consider empty string to be default.
    /// </remarks>
    String getDefaultFont() { return mMathFont; }
    void setDefaultFont(String value)
    {
        if (StringUtil.hasChars(value))
            mMathFont = value;
    }
    
    /// <summary>
    /// Specifies the document setting for the default placement of n-ary limits other than integrals
    /// when converted from a built down form to a two-dimensional output (professional form).
    /// Default: <see cref="MathLimitLocation.UnderOver"/>
    /// </summary>
    /*MathLimitLocation*/int getNaryLimitLocation() { return mNaryLim; }
    void setNaryLimitLocation(/*MathLimitLocation*/int value)
    {
        if (value == MathLimitLocation.UNDEFINED)
            throw new IllegalArgumentException("This enum value is not allowed.");
        mNaryLim = value;
    }
    
    /// <summary>
    /// Specifies the spacing after a math paragraph, in twips. 
    /// Default 0. 
    /// </summary>
    int getPostParagraphSpacing() { return mPostSp; }
    void setPostParagraphSpacing(int value) { mPostSp = value; }
    
    /// <summary>
    /// Specifies the spacing before a math paragraph, in twips. 
    /// Default 0.
    /// </summary>
    int getPreParagraphSpacing() { return mPreSp; }
    void setPreParagraphSpacing(int value) { mPreSp = value; }
    
    /// <summary>
    /// This element specifies a reduced fraction size display math, such that the numerator and denominator are
    /// written in script size instead of at the size of regular text.
    /// Default value: false, meaning that this option is not applied.
    /// </summary>
    boolean isSmallFraction() { return mSmallFrac; }
    void isSmallFraction(boolean value) { mSmallFrac = value; }
    
    /// <summary>
    /// Specifies the indent of the wrapped line of an instance of mathematical text, in twips.
    /// The default: 1440 twips (or 1 inch).
    /// </summary>
    /// <remarks>
    /// The line or lines of a wrapped instance of mathematical text after the line break can either be indented by a
    ///  specified amount from the left margin, or right aligned. 
    /// </remarks>
    int getWrapIndent() { return mWrapIndent; }
    void setWrapIndent(int value) { mWrapIndent = value; }
    
    /// <summary>
    /// Specifies the right justification of the wrapped line of an instance of mathematical text.
    /// Default: false. 
    /// </summary>
    /// <remarks>
    /// The line or lines of a wrapped instance of mathematical text after the line break can 
    /// either be indented by a specified amount from the left margin, or right aligned. 
    /// If this element is present, the continuation is right aligned.
    /// </remarks>
    boolean getWrapRight() { return mWrapRight; }
    void setWrapRight(boolean value) { mWrapRight = value; }

    /// <summary>
    /// (Break on Binary Operators) §22.1.2.16
    /// </summary>
    private /*MathBreakOnBinary*/int mBrkBin = MathBreakOnBinary.DEFAULT;

    /// <summary>
    /// (Break on Binary Subtraction) §22.1.2.17
    /// </summary>
    private /*MathBreakOnBinarySubtraction*/int mBrkBinSub = MathBreakOnBinarySubtraction.DEFAULT;

    // (Default Justification) §22.1.2.25
    private /*OfficeMathJustification*/int mDefJc = OfficeMathJustification.DEFAULT;
    
    /// <summary>
    /// (Use Display Math Defaults) §22.1.2.30
    /// </summary>
    private boolean mDispDef = true;

    /// <summary>
    /// (Integral Limit Locations) §22.1.2.49
    /// </summary>
    private /*MathLimitLocation*/int mIntLim = MathLimitLocation.SUBSCRIPT_SUPERSCRIPT;

    /// <summary>
    /// (Inter-Equation Spacing) §22.1.2.48
    /// </summary>
    private int mInterSp; 

    /// <summary>
    /// (Intra-Equation Spacing) §22.1.2.50
    /// </summary>
    private int mIntraSp;

    /// <summary>
    /// (Left Margin) §22.1.2.59
    /// </summary>
    private int mLMargin;

    /// <summary>
    /// (Right Margin) §22.1.2.90
    /// </summary>
    private int mRMargin;

    /// <summary>
    /// (Math Font) §22.1.2.61
    /// </summary>
    private String mMathFont = "";
    
    /// <summary>
    /// (n-ary Limit Location) §22.1.2.71
    /// </summary>
    private /*MathLimitLocation*/int mNaryLim = MathLimitLocation.UNDER_OVER;

    /// <summary>
    /// (Post-Paragraph Spacing) §22.1.2.85
    /// </summary>
    private int mPostSp;

    /// <summary>
    /// (Pre-Paragraph Spacing) §22.1.2.86
    /// </summary>
    private int mPreSp;

    /// <summary>
    /// (Small Fraction) §22.1.2.98
    /// </summary>
    private boolean mSmallFrac;

    /// <summary>
    /// (Wrap Indent) §22.1.2.120
    /// </summary>
    private int mWrapIndent = DEFAULT_WRAP_INDENT;
    
    /// <summary>
    /// (Wrap Right) §22.1.2.121
    /// </summary>
    private boolean mWrapRight;

    static final int DEFAULT_WRAP_INDENT = 1440;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

