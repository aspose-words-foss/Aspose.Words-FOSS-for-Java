// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2016 by Michael Morozoff

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.FontStyle;
import com.aspose.ms.System.Drawing.msSizeF;
import com.aspose.ms.System.msMath;
import com.aspose.ms.System.MidpointRounding;


/// <summary>
/// Use this class instead of GDI+ Font to make code autoportable to Java.
///
/// Why do we have this class instead of implementing ms.System.Drawing.Font?
/// .NET's Font is very tightly related to GDI+ behavior, for example it only allows to use fonts properly installed in Windows.
/// But we need to support Java and Mono and using a class that depends on GDI+ is not possible. Hence an automatically portable abstraction.
/// </summary>
public class DrFont
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="sizePoints">Size (points).</param>
    /// <param name="requestedStyle">Font style.</param>
    /// <param name="trueTypeFont">Base TrueType font.</param>
    /// <param name="isVertical">Indicates whether vertical metrics should be used.</param>
    public DrFont(float sizePoints,
        /*FontStyle*/int requestedStyle,
        TTFont trueTypeFont,
        boolean isVertical)
    {
    	this(sizePoints, requestedStyle, trueTypeFont, isVertical, false, false, false);
	
    }
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="sizePoints">Size (points).</param>
    /// <param name="requestedStyle">Font style.</param>
    /// <param name="trueTypeFont">Base TrueType font.</param>
    /// <param name="isVertical">Indicates whether vertical metrics should be used.</param>
    /// <param name="isSmallCaps">Indicates whether font size should be calculated for small capitals.</param>
    /// <param name="isSuperscript">Indicates whether the font size should be calculated for superscript text.</param>
    /// <param name="isSubscript">Indicates whether font size should be calculated for subscript text.</param>
    public DrFont(float sizePoints,
        /*FontStyle*/int requestedStyle,
        TTFont trueTypeFont,
        boolean isVertical,
        boolean isSmallCaps,
        boolean isSuperscript,
        boolean isSubscript)
    {
        if (trueTypeFont == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "trueTypeFont");

        mTTFont = trueTypeFont;
        mRequestedStyle = requestedStyle;
        mIsVertical = isVertical;
        mHalfAvgCharWidthToSpaceWidth = (mTTFont.getAvgCharWidth() / 2f) / mTTFont.getCharAdvanceWidthDesignUnits(SPACE_CHAR_CODE);

        mSizePoints = (isSmallCaps || isSuperscript || isSubscript) ?
            getRenderSize(sizePoints, isSmallCaps, isSuperscript, isSubscript) :
            sizePoints;
    }

    /// <summary>
    /// Gets font metrics used by this instance. This is normally <see cref="TTFontMetrics"/> but on .NET can also be set to printer metrics.
    /// </summary>
    /// <remarks>
    /// This property has been introduced to allow using font metrics other than TTF ones when
    /// the "Use printer metrics to lay out document" compatibility option is enabled for document.
    /// </remarks>
    private IDrFontMetrics getMetrics()
    {
        // If no metrics has been assigned, default to the TTF metrics.
        if (mMetrics == null)
        {
            mMetrics = mIsVertical
                ? (IDrFontMetrics)new TTFontVerticalMetrics(mTTFont, mSizePoints)
                : new TTFontHorizontalMetrics(mTTFont, mSizePoints);
        }

        return mMetrics;
    }

    /// <summary>
    /// The requested font style. It may not be equal to the underlying <see cref="TTFont"/> style.
    /// </summary>
    public /*FontStyle*/int getRequestedStyle() { return mRequestedStyle; }

    /// <summary>
    /// Returns true if bold style simulation should be performed.
    /// </summary>
    public boolean isSimulateBold() { return ((mRequestedStyle & FontStyle.BOLD) != 0 && !mTTFont.isBold()); }

    /// <summary>
    /// Gets name of this font.
    /// </summary>
    public String getFamilyName() { return mTTFont.getFamilyName(); }

    /// <summary>
    /// Gets size of this font (points).
    /// </summary>
    public float getSizePoints() { return mSizePoints; }

    /// <summary>
    /// Returns the cell ascent in points.
    /// </summary>
    public float getAscentPoints() { return getMetrics().getAscentPoints(); }

    /// <summary>
    /// Returns the cell descent in points.
    /// </summary>
    public float getDescentPoints() { return getMetrics().getDescentPoints(); }

    /// <summary>
    /// Returns the cell original ascent in points.
    /// </summary>
    public float getAscentRawPoints() { return getMetrics().getAscentRawPoints(); }

    /// <summary>
    /// Returns the cell original descent in points.
    /// </summary>
    public float getDescentRawPoints() { return getMetrics().getDescentRawPoints(); }

    /// <summary>
    /// Shortcut for <see cref="AscentPoints"/> + <see cref="DescentPoints"/>.
    /// </summary>
    public float getCellHeightPoints() { return getAscentPoints() + getDescentPoints(); }

    /// <summary>
    /// Returns the internal leading (points).
    /// </summary>
    public float getInternalLeadingPoints() { return (getAscentPoints() + getDescentPoints()) - getSizePoints(); }

    /// <summary>
    /// Returns the ascent with the suppressed top spacing (points).
    /// </summary>
    /// <remarks>
    /// This kind of ascent is used when the "Suppress extra line spacing the way WordPerfect 5.x does" compatibility option is enabled.
    /// </remarks>
    public float getAscentSuppressedTopSpacingWPPoints() { return getSizePoints() + 2f - getDescentPoints(); }

    /// <summary>
    /// Returns cell spacing of this font (lis).
    /// This is a vertical distance between baselines of the two glyphs.
    /// </summary>
    public int getLineSpacingLis()
    {
        if (mLineSpacingLis == -1)
            mLineSpacingLis = ConvertUtilCore.pointToLi(getLineSpacingPoints());

        return mLineSpacingLis;
    }

    /// <summary>
    /// Returns cell spacing of this font (points).
    /// This is a vertical distance between baselines of the two glyphs.
    /// </summary>
    public float getLineSpacingPoints() { return getMetrics().getLineSpacingPoints(); }

    /// <summary>
    /// Returns width of the character (points).
    /// </summary>
    public float getCharWidthPoints(int c)
    {
        return getMetrics().getCharWidthPoints(c, mSizePoints);
    }

    public float getTextWidthPoints(String text)
    {
        return getMetrics().getTextWidthPoints(text, mSizePoints);
    }

    /// <summary>
    /// Returns measurement text box of the text in points.
    /// </summary>
    public /*SizeF*/long getTextSizePoints(String text)
    {
        return msSizeF.ctor(getTextWidthPoints(text), getCellHeightPoints());
    }

    /// <summary>
    /// Cell ascent of this font (lis).
    /// This is a vertical distance from cell top to cell baseline.
    /// </summary>
    /// <remarks>This value is also called <b>cell baseline</b>.</remarks>
    public int getAscentLis()
    {
        if (mAscentLis == -1)
            mAscentLis = ConvertUtilCore.pointToLi(getAscentPoints());

        return mAscentLis;
    }

    /// <summary>
    /// Cell descent of this font (lis).
    /// This is a vertical distance from cell bottom to cell baseline.
    /// </summary>
    public int getDescentLis()
    {
        if (mDescentLis == -1)
            mDescentLis = ConvertUtilCore.pointToLi(getDescentPoints());

        return mDescentLis;
    }

    /// <summary>
    /// Returns cell height of this font (lis).
    /// This is a shortcut for <see cref="AscentLis"/> + <see cref="DescentLis"/>.
    /// </summary>
    public int getCellHeightLis()
    {
        if (mCellHeightLis == -1)
            mCellHeightLis = getAscentLis() + getDescentLis();

        return mCellHeightLis;
    }

    /// <summary>
    /// Ratio of half of the average character width to the space width.
    /// </summary>
    /// <remarks>
    /// Ratio used for the space shrinkability calculation.
    /// </remarks>>
    public float getHalfAvgCharWidthToSpaceWidth() { return mHalfAvgCharWidthToSpaceWidth; }

    /// <summary>
    /// Indicates whether at least one of the CJK Unicode ranges is considered functional in this font.
    /// </summary>
    /// <remarks>
    /// This property is mainly used to detect whether the font is Asian.
    /// </remarks>
    public boolean isCjkSupported() { return mTTFont.isCjkMetrics(); }

    private float getSubscriptFactor() { return (float)getSubscriptSize() / mTTFont.getEmHeight(); }

    private float getSupersciptFactor() { return (float)getSuperscriptSize() / mTTFont.getEmHeight(); }

    private int getSuperscriptSize() { return mTTFont.getSuperscriptSize(); }

    private int getSubscriptSize() { return mTTFont.getSubscriptSize(); }

    private int getSuperscriptOffset() { return mTTFont.getSuperscriptOffset(); }

    private int getSubscriptOffset() { return mTTFont.getSubscriptOffset(); }

    /// <summary>
    /// Returns font size which depends on case, caps and script of span. This is size
    /// of the font which is used for span rendering, not for measurements.
    /// </summary>
    /// <remarks>
    /// The actual size could be smaller than the specified font size for small caps
    /// and/or subscript/superscript font.
    /// </remarks>
    private float getRenderSize(float sizePoints, boolean isSmallCaps, boolean isSuperscript, boolean isSubscript)
    {
        // 1) Normal | (SmallCaps & !isLower)                   = 1
        // 2) SmallCaps & Lower                                 = *SmallCapsSizeFactor
        // 3) Subscript | Superscript                           = *SubscriptSizeFactor
        // 4) SmallCaps & Lower & (Subscript | Superscript)     = *SmallCapsSizeFactor *SubscriptSizeFactor

        // We use half points because Word rounds this way.
        float result = ConvertUtilCore.pointToHalfPoint(sizePoints);

        float superscriptFactor = getSupersciptFactor();
        float subscriptFactor = getSubscriptFactor();

        if (isSuperscript || isSubscript)
        {
            if (isRegularScriptScalingApplicable((int)result))
            {
                if (isSuperscript)
                    result = result * superscriptFactor;
                else if (isSubscript)
                    result = result * subscriptFactor;
            }
            else
            {
                result = result * SCRIPT_FACTOR;
                if (result < MIN_FONT_SIZE_HALF_POINTS)
                    result = MIN_FONT_SIZE_HALF_POINTS;
            }
        }

        if (isSmallCaps)
            result = result * SMALL_CAPS_SIZE_FACTOR;

        // WORDSNET-3751 It seems that MS Word performs rounding to half points after applying size factor.
        return (float)ConvertUtilCore.halfPointToPoint((int)msMath.round(result, 0, MidpointRounding.AWAY_FROM_ZERO));
    }

    /// <summary>
    /// Indicates whether superscript and subscript font sizes should be based on the OS/2 table data.
    /// </summary>
    /// <param name="fontSize">The font size in half points.</param>
    private boolean isRegularScriptScalingApplicable(int fontSize)
    {
        int oneHalf = fontSize / 2;
        int oneForth = fontSize / 4;
        int threeForth = oneHalf + oneForth;
        int oneThird = fontSize / 3 + 1;

        int superscriptSize = designUnitsToHalfPointsRounded(getSuperscriptSize(), fontSize);
        int subscriptSize = designUnitsToHalfPointsRounded(getSubscriptSize(), fontSize);
        int superscriptOffset = designUnitsToHalfPointsRounded(getSuperscriptOffset(), fontSize);
        int subscriptOffset = designUnitsToHalfPointsRounded(getSubscriptOffset(), fontSize);

        return (oneThird <= superscriptSize && superscriptSize <= fontSize) &&
               (oneThird <= subscriptSize && subscriptSize <= fontSize) &&
               (0 <= subscriptOffset && subscriptOffset <= oneHalf) &&
               (oneForth <= superscriptOffset && superscriptOffset <= threeForth);
    }

    private int designUnitsToHalfPointsRounded(int designUnits, int halfPoints)
    {
        return (int)msMath.round(mTTFont.designUnitsToPoints(designUnits, halfPoints), 0, MidpointRounding.AWAY_FROM_ZERO);
    }

    /// <summary>
    /// This is additional magnification factor used for lowercase case small which is
    /// at the same time subscript or superscript.
    /// </summary>
    private static final float SMALL_CAPS_SIZE_FACTOR = 0.80f;

    /// <summary>
    /// This factor is used for superscript and subscript when conditions in <see cref="IsRegularScriptScalingApplicable(int)"/> are not met.
    /// </summary>
    private static final float SCRIPT_FACTOR = 0.6f;

    private static final int MIN_FONT_SIZE_HALF_POINTS = 2;

    private /*final*/ TTFont mTTFont;
    private /*final*/ /*FontStyle*/int mRequestedStyle;
    private /*final*/ float mSizePoints;
    private IDrFontMetrics mMetrics;
    /// <summary>
    /// Indicates whether vertical metrics should be used instead of horizontal metrics,
    /// i.e. advance height is used instead of advance width.
    /// </summary>
    private /*final*/ boolean mIsVertical;
    private int mCellHeightLis = -1;
    private int mLineSpacingLis = -1;
    private int mAscentLis = -1;
    private int mDescentLis = -1;
    private /*final*/ float mHalfAvgCharWidthToSpaceWidth;

    private static final int SPACE_CHAR_CODE = 32;
}

