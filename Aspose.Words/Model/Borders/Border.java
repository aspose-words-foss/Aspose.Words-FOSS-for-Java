// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2005 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.Debug;
import com.aspose.MathUtil;
import com.aspose.ConvertUtilCore;
import java.awt.Color;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msNumber;
import com.aspose.IntToObjDictionary;
import com.aspose.words.ref.RefBoolean;


/// <summary>
/// Represents a border of an object.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/programming-with-documents/">Programming with Documents</a> documentation article.</para>
/// </summary>
///    <remarks>
///    <p>Borders can be applied to various document elements including paragraph,
///    run of text inside a paragraph or a table cell.</p>
///    </remarks>
/// <dev>
/// This is a model and also presentation class that allows to see and modify border attributes.
/// </dev>
public class Border extends InternableComplexAttr implements IComplexAttr, Cloneable
{
    /// <summary>
    /// Creates a non-inherited border.
    /// </summary>
    Border()
    {
        clearFormatting();
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="lineStyle">The style of the line.</param>
    /// <param name="rawLineWidth">Width of the line in 1/8pt point.</param>
    /// <param name="color">Color of the line.</param>
    Border(/*LineStyle*/int lineStyle, int rawLineWidth, DrColor color)
    {
        Debug.assertTrue(color != null);
        mLineStyle = lineStyle;
        mRawLineWidth = rawLineWidth;
        mColor = color;
    }

    /// <summary>
    /// Creates an inherited border.
    /// </summary>
    Border(IBorderAttrSource parent, int key)
    {
        mParent = parent;
        mKey = key;
    }

    /// <summary>
    /// Resets border properties to default values.
    /// </summary>
    /// <remarks>
    /// When border properties are reset to default values, the border is invisible.
    /// </remarks>
    public void clearFormatting()
    {
        beforeChange();

        mParent = null;
        mLineStyle = LineStyle.NONE;
        mRawLineWidth = 0;
        mColor = DrColor.EMPTY;
        mRawDistanceFromText = 0;
        mShadow = false;
        mFrame = false;
        mThemeColor = null;
        mThemeShade = null;
        mThemeTint = null;
    }

    /// <summary>
    /// Gets or sets the border style.
    /// </summary>
    /// <remarks>
    /// <para>If you set line style to none, then line width is automatically changed to zero.</para>
    /// </remarks>
    /// <dev>
    /// Currently AW distinguish Nil and None borders.
    /// This public setter is used by customer and always creates Nil border for <see cref="LineStyle.None"/>
    /// Readers and internal code should use <see cref="LineStyleInternal" /> setter with <see cref="LineStyle.None"/> to create None.Border and
    /// <see cref="CreateNilBorder()" /> method to create Nil border.
    /// </dev>
    public /*LineStyle*/int getLineStyle() { return (isInherited()) ? getInherited().getLineStyle() : mLineStyle; }
    public void setLineStyle(/*LineStyle*/int value)
    {
        setLineStyleInternal(value);

        // andrnosk: WORDSNET-7706 Mimic MS Word behavior, assign minimal (1/4pt) width in case when border style is specified
        // and line widths equals zero.
        if ((value != LineStyle.NONE) && MathUtil.isZero(getLineWidth()))
            setLineWidth(0.25);

        // This setter is used by customer and always creates Nil border for LineStyle.None.
        mIsNil = (mLineStyle == LineStyle.NONE);
    }

    /// <summary>
    /// Sets the border style.
    /// </summary>
    void setLineStyleInternal(/*LineStyle*/int value)
    {
        beforeChange();
        mLineStyle = value;
        // WORDSNET-1133 Setting Border.LineStyle to LineStyle.None without explicitly setting
        // Border.LineWidth to 0 creates documents that crash MS Word 2000 and MS Word 2002.
        if (value == LineStyle.NONE)
            setLineWidth(0.0);

        mIsNil = false;
    }

    /// <summary>
    /// Gets or sets the border width in points.
    /// </summary>
    /// <remarks>
    /// <p>If you set line width greater than zero when line style is none, the line style is
    /// automatically changed to single line.</p>
    /// </remarks>
    public double getLineWidth()
    {
        if (isInherited())
            return getInherited().getLineWidth();

        // When border is page border art, raw line width is in points already.
        // I don't like such logic, but have no better ideas at this stage.
        return (isPageBorderArt()) ? mRawLineWidth : ConvertUtilCore.eightsPointToPoint(mRawLineWidth);
    }
    public void setLineWidth(double value)
    {
        setLineWidthCore(value, true);

        validateNoneBorder();
    }


    /// <summary>
    /// Validates border having line width greater than zero cannot be None border.
    /// </summary>
    /// <dev>
    /// AM. Actually combination of LineStyle.None and non zero line width is allowed in Word.
    /// I made fix for WORDSNET-1133 at the logical level while should do it at DOC export level.
    /// Unfortunately, now some code relies on this logic and that worse seems it commonly used public API behavior.
    /// That's why I decided to make only partial solution to fix WORDSNET-25597 only and
    /// try to preserve other usages.
    /// </dev>
    void validateNoneBorder()
    {
        // WORDSNET-1133 Creating a border with non zero width but line style none crashes MS Word 2000.
        if ((getLineWidth() > 0) && (getLineStyle() == LineStyle.NONE))
            setLineStyleInternal(LineStyle.SINGLE);
    }

    /// <summary>
    /// Gets the border line width in points.
    /// Currently needed to export to several formats.
    /// MS Words shows borders if line style is anything but None even if width is zero.
    /// </summary>
    double getLineWidthZeroAware()
    {
        double originalLineWidth = getLineWidth();
        if ((originalLineWidth != 0) || !isVisible())
            return originalLineWidth;

        final double LINE_WIDTH_OUTPUT_BY_DEFAULT = 0.25;
        return LINE_WIDTH_OUTPUT_BY_DEFAULT;
    }

    /// <summary>
    /// Returns <c>true</c> if this border style is page border art.
    /// The line style property should actually be cast to the PageBorderArt enum.
    /// </summary>
    boolean isPageBorderArt() { return (int)getLineStyle() >= (int)PageBorderArt.APPLES; }

    /// <summary>
    /// Returns <c>true</c> if the <see cref="LineStyle"/> is not <see cref="LineStyle.None"/>.
    /// </summary>
    public boolean isVisible() { return (getLineStyle() != LineStyle.NONE); }

    /// <summary>
    /// Returns <c>true</c> if the line style is not LineStyle.None and line width
    /// is greater than zero or distance is greater that zero.
    /// </summary>
    /// <remarks>
    /// 1. Moved from AE.
    /// 2. Dependence on "distance" is included by Jira7344.
    /// </remarks>
    static boolean getIsVisible(/*LineStyle*/int lineStyle, float lineWidth, float distance)
    {
        return ((lineStyle != LineStyle.NONE) && (lineWidth > 0)) || distance > 0;
    }

    /// <summary>
    /// Returns the actual width (in points) of the border.
    ///
    /// This method correctly calculates actual width of multipart borders.
    /// For example, the "line width" could be 0.5pt, but the total
    /// width of say 4 parts will be 2.5pt.
    ///
    /// Does not include shadow and distance from text.
    ///
    /// Returns zero if the line style is none.
    /// </summary>
    float getBorderWidth() { return getActualWidth(getLineStyle(), (float)getLineWidthZeroAware()); }

    /// <summary>
    /// Returns the total width of the border (in points) including shadow and distance from text.
    /// </summary>
    float getTotalWidthAndDistance()
    {
        if (isVisible())
        {
            float result = getBorderWidth();

            if (getShadow())
                result *= 2f;

            result += (float)getDistanceFromText();

            return result;
        }

        return 0f;
    }

    /// <summary>
    ///  Gets or sets the border color.
    /// </summary>
    public Color getColor() { return getColorInternal().toNativeColor(); }
    public void setColor(Color value)
    {
        setColorInternal(DrColor.fromNativeColor(value));

        // WORDSNET-15143 Reset theme color when RGB color is set.
        mThemeColor = null;
        mThemeShade = null;
        mThemeTint = null;
    }

    DrColor getColorInternal() { return (isInherited()) ? getInherited().getColorInternal() : mColor; }
    void setColorInternal(DrColor value)
    {
        Debug.assertTrue(value != null);
        beforeChange();
        mColor = value;
    }

    /// <summary>
    /// Gets or sets distance of the border from text or from the page edge in points.
    /// </summary>
    /// <remarks>
    /// Has no effect and will be automatically reset to zero for borders of table cells.
    ///
    /// <seealso cref="PageSetup.BorderDistanceFrom"/>
    /// </remarks>
    public double getDistanceFromText() { return (isInherited()) ? getInherited().getDistanceFromText() : mRawDistanceFromText; }
    public void setDistanceFromText(double value) { setDistanceFromTextCore(value, true); }

    /// <summary>
    /// Gets or sets a value indicating whether the border has a shadow.
    /// </summary>
    /// <remarks>
    /// <p>In Microsoft Word, for a border to have a shadow, the borders on all four sides
    /// (left, top, right and bottom) should be of the same type, width, color and all should have
    /// the Shadow property set to <c>true</c>.</p>
    /// </remarks>
    public boolean getShadow() { return (isInherited()) ? getInherited().getShadow() : mShadow; }
    public void setShadow(boolean value)
    {
        beforeChange();
        mShadow = value;
    }

    /// <summary>
    /// Gets or sets the theme color in the applied color scheme that is associated with this Border object.
    /// </summary>
    public /*ThemeColor*/int getThemeColor()
    {
        return ThemeColorConverter.fromString(mThemeColor);
    }
    public void setThemeColor(/*ThemeColor*/int value)
    {
        mThemeColor = ThemeColorConverter.toString(value);
        // WORDSNET-24325 Reset RGB color when theme color is set.
        mColor = DrColor.EMPTY;
    }

    /// <summary>
    /// Gets or sets a double value that lightens or darkens a color.
    /// </summary>
    /// <remarks>
    /// <para> The allowed values are in the range from -1 (the darkest) to 1 (the lightest) for this property.
    /// Zero (0) is neutral.</para>
    /// </remarks>
    /// <exception cref="ArgumentOutOfRangeException">Throws if you attempt to set this property to a value less than -1 or more than 1.</exception>
    /// <exception cref="InvalidOperationException">Throws if setting this property for Border object with non-theme colors.</exception>
    public double getTintAndShade()
    {
        // Note, Word stores inverted values (1 - tint) and (1 + shade) in the document.
        return StringUtil.hasChars(mThemeTint)
            ? 1.0d - ((double)FormatterPal.parseHex(mThemeTint) / 255.0)
            : StringUtil.hasChars(mThemeShade)
                ? -1.0d - ((double)FormatterPal.parseHex(mThemeShade) / -255)
                : 0.0d;
    }
    public void setTintAndShade(double value)
    {
        // Despite https://docs.microsoft.com/en-us/office/vba/api/excel.font.tintandshade
        // says that "This property works for both theme colors and non-theme colors.",
        // in fact it does not work in Word VBA for non-theme colors.
        if (!StringUtil.hasChars(getThemeColorInternal()))
            throw new IllegalStateException("TintAndShade cannot be applied to a non-theme color.");

        ArgumentUtil.checkRangeInclusive(value, -1.0d, 1.0d, "TintAndShade");

        setThemeTint(null);
        setThemeShade(null);
        // Note, Word stores inverted value (1 - tint) in the document.
        if (value > 0)
            setThemeTint(FormatterPal.intToStrX2((int)((1.0 - value) * 255.0)));
        // Note, Word stores inverted value (1 + shade) in the document.
        if (value < 0)
            setThemeShade(FormatterPal.intToStrX2((int)((-1 - value) * -255)));
    }

    /// <summary>
    /// Gets or sets a value indicating whether to create a frame effect by reversing the border.
    /// </summary>
    boolean getFrame() { return (isInherited()) ? getInherited().getFrame() : mFrame; }
    void setFrame(boolean value)
    {
        beforeChange();
        mFrame = value;
    }

    /// <summary>
    /// At the moment just plain unparsed value from DOCX.
    /// Specifies the base theme color used to generate the border color. The border color is the
    /// RGB value associated with themeColor as further transformed by themeTint or themeShade (if one is present),
    /// else the background color is the RGB value associated with themeColor.
    /// </summary>
    String getThemeColorInternal() { return mThemeColor; }
    void setThemeColorInternal(String value) { mThemeColor = value; }

    /// <summary>
    /// At the moment just plain unparsed value from DOCX.
    /// Specifies the shade value applied to the supplied theme color (if any) for this border
    /// instance. If the themeColor attribute is not present, then this attribute shall not be used.
    /// </summary>
    String getThemeShade() { return mThemeShade; }
    void setThemeShade(String value) { mThemeShade = value; }

    /// <summary>
    /// At the moment just plain unparsed value from DOCX.
    /// </summary>
    String getThemeTint() { return mThemeTint; }
    void setThemeTint(String value) { mThemeTint = value; }

    /// <summary>
    /// Gets/sets line width in "raw" units (as stored in DOC or in WordML).
    /// For normal borders the value is in 1/8th of a point.
    /// For page border art, the value is in points.
    /// </summary>
    int getRawLineWidth() { return mRawLineWidth; }
    void setRawLineWidth(int value)
    {
        mRawLineWidth = value;
    }

    /// <summary>
    /// Gets/sets space between border and text in points?
    /// </summary>
    int getRawDistanceFromText() { return mRawDistanceFromText; }
    void setRawDistanceFromText(int value)
    {
        mRawDistanceFromText = value;
    }

    /// <summary>
    /// Returns the number of parts this border consists of.
    /// </summary>
    int getPartsCount() { return getPartsCount(getLineStyle()); }

    void setLineWidthSafe(double lineWidth)
    {
        setLineWidthCore(lineWidth, false);
    }

    /// <summary>
    /// Note this is a new design pattern for setting values.
    /// A public property should call this method with isThrow = true so it throws when user sets invalid value.
    /// But importers should call this method with isThrow = false so it sets the value safely even
    /// when the document contains an invalid value.
    /// </summary>
    private void setLineWidthCore(double lineWidth, boolean isThrow)
    {
        double lineWidthSafe = MathUtil.fitToRange(lineWidth, 0.0, MAX_LINE_WIDTH);

        if ((lineWidthSafe != lineWidth) && isThrow)
                throw new IllegalArgumentException(msString.format("Must be non-negative and less than or equal to {0}.", MAX_LINE_WIDTH) + "\r\nParameter name: " + "lineWidth");

        beforeChange();

        // When border is page border art, raw line width is in points already.
        // TODO The problem here is that LineWidth property depends on LineStyle.
        // If we need to set both then we have to follow the order: first set Style then Set width.
        // But then if width > 0 and Style is None then Style gets changes to Single. Nasty!
        setRawLineWidth((isPageBorderArt())
            ? MathUtil.doubleToInt(lineWidthSafe)
            : ConvertUtilCore.pointToEightsPoint(lineWidthSafe));
    }

    void setDistanceFromTextSafe(double distanceFromText)
    {
        setDistanceFromTextCore(distanceFromText, false);
    }

    private void setDistanceFromTextCore(double distanceFromText, boolean isThrow)
    {
        if (distanceFromText < 0)
        {
            if (isThrow)
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "distanceFromText");
            else
                distanceFromText = 0.0;
        }
        else if (distanceFromText > MAX_DISTANCE_FROM_TEXT)
        {
            if (isThrow)
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "distanceFromText");
            else
                distanceFromText = MAX_DISTANCE_FROM_TEXT;
        }

        beforeChange();
        setRawDistanceFromText((int)distanceFromText);
    }

    /// <summary>
    /// Determines whether the specified border is equal in value to the current border.
    /// </summary>
    public boolean equals(Border rhs)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	// Generated by ReSharper.
        	if (msObject.referenceEquals(null, rhs))
            	return false;
        	if (msObject.referenceEquals(this, rhs))
            	return true;

        	return
            	(getLineStyle() == rhs.getLineStyle()) &&
            	(getLineWidth() == rhs.getLineWidth()) &&
            	(getColorInternal().equals(rhs.getColorInternal())) &&
            	(getDistanceFromText() == rhs.getDistanceFromText()) &&
            	(getFrame() == rhs.getFrame()) &&
            	(getShadow() == rhs.getShadow()) &&
            	(msString.equals(getThemeColorInternal(), rhs.getThemeColorInternal())) &&
            	(msString.equals(getThemeShade(), rhs.getThemeShade())) &&
            	(msString.equals(getThemeTint(), rhs.getThemeTint())) &&
            	(isNil() == rhs.isNil());
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Determines whether the specified object is equal in value to the current object.
    /// </summary>
    public /*override*/ boolean equals(Object obj)
    {
        // Generated by ReSharper.
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (obj.getClass() != Border.class)
            return false;
        return equals((Border)obj);
    }

    /// <summary>
    /// Serves as a hash function for this type.
    /// </summary>
    /// <javaName>int hashCode()</javaName>
    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            int result = (int)mLineStyle;
            result = (result * 397) ^ mRawLineWidth;
            result = (result * 397) ^ (mColor != null ? mColor.hashCode() : 0);
            result = (result * 397) ^ mRawDistanceFromText;
            result = (result * 397) ^ msNumber.booleanHashCode(mShadow);
            result = (result * 397) ^ msNumber.booleanHashCode(mFrame);
            result = (result * 397) ^ (mThemeColor != null ? mThemeColor.hashCode() : 0);
            result = (result * 397) ^ (mThemeShade != null ? mThemeShade.hashCode() : 0);
            result = (result * 397) ^ (mThemeTint != null ? mThemeTint.hashCode() : 0);
            result = (result * 397) ^ (isNil() ? 1 : 0);
            return result;
        }
    }

    /// <summary>
    /// Returns true if this border can be prolonged (drawn without a break) with the specified border.
    ///</summary>
    ///<remarks>
    /// RK In MS Word a border of the same width and pattern, but different color will be drawn without
    /// a break, but I don't know how to do that. If the borders have different color, we draw them as
    /// separate lines.
    /// </remarks>
    boolean canContinue(Border rhs)
    {
        return (isVisible() && equals(rhs));
    }

    /// <summary>
    /// Detects if this border has the reverse order of parts that the specified border has.
    ///
    /// Some double borders look like mirrored versions of other double borders, for example ThinThickSmallGap and
    /// ThickThinSmallGap. These borders can be connected so we must be able to determine the mirrored borders.
    /// </summary>
    boolean isMirroredWith(Border rhs)
    {
        return getIsMirrored(getLineStyle(), rhs.getLineStyle());
    }

    /// <summary>
    /// Returns True if second line style is mirrored with first one.
    /// </summary>
    /// <remarks>Moved from AE.</remarks>
    static boolean getIsMirrored(/*LineStyle*/int lineStyle1, /*LineStyle*/int lineStyle2)
    {
        /*LineStyle*/int mirroredLineStyle = getMirroredLineStyle(lineStyle1);
        if ((mirroredLineStyle != LineStyle.NONE) && (mirroredLineStyle == lineStyle2))
            return true;

        mirroredLineStyle = getMirroredLineStyle(lineStyle2);
        if ((mirroredLineStyle != LineStyle.NONE) && (mirroredLineStyle == lineStyle1))
            return true;

        return false;
    }

    /// <summary>
    /// Some double borders look like mirrored versions of other double borders, for example ThinThickSmallGap and
    /// ThickThinSmallGap. These borders can be connected so we must be able to determine pairs of mirrored borders.
    /// This method maps line styles of such borders.
    /// </summary>
    private static /*LineStyle*/int getMirroredLineStyle(/*LineStyle*/int lineStyle)
    {
        switch (lineStyle)
        {
            case LineStyle.THIN_THICK_SMALL_GAP: return LineStyle.THICK_THIN_SMALL_GAP;
            case LineStyle.THIN_THICK_MEDIUM_GAP: return LineStyle.THICK_THIN_MEDIUM_GAP;
            case LineStyle.THIN_THICK_LARGE_GAP: return LineStyle.THICK_THIN_LARGE_GAP;
            default: return LineStyle.NONE;
        }
    }

    /// <summary>
    /// Returns true if this border can be connected (so that the joining is drawn using a special way)
    /// with the specified border.
    /// </summary>
    /// <param name="rhs"></param>
    /// <param name="isMirrored">True if the connection is mirrored.</param>
    /// <returns></returns>
    boolean canConnect(Border rhs, /*out*/RefBoolean isMirrored)
    {
        // Invisible borders cannot connect with other borders. Single part borders does not require connections.
        // This is not noticeable for simple borders but ornate borders like waves look corrupted.
        if ((!isVisible()) || (getPartsCount() == 1))
        {
            isMirrored.set(false);
            return false;
        }

        isMirrored.set(isMirroredWith(rhs));

        return
            (getLineStyle() == rhs.getLineStyle() || isMirrored.get()) &&
            getLineWidth() == rhs.getLineWidth() &&
            getFrame() == rhs.getFrame();
    }

    /// <summary>
    /// Gets the border that we are inheriting from.
    /// </summary>
    private Border getInherited() { return (Border)mParent.fetchInheritedBorderAttr(mKey); }

    /// <summary>
    /// Reserved for system use. IComplexAttr.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public 
    boolean /*IComplexAttr.*/isInheritedComplexAttr() { return isInherited(); }

    /// <summary>
    /// Reserved for system use. IComplexAttr.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    
    public IComplexAttr /*IComplexAttr.*/deepCloneComplexAttr()
    {
        return deepClone();
    }

    boolean isInherited() { return (mParent != null); }

    Border deepClone()
    {
        // Not expected to create copies of inherited borders because inherited borders
        // are essentially presentation objects only and created on demand.
        if (isInherited())
            throw new IllegalStateException("Cannot clone an inherited attribute.");
        return (Border)memberwiseClone();
    }

    void copyFrom(Border src)
    {
        if (src == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "src");

        mKey = src.mKey;
        mLineStyle = src.getLineStyle();
        mRawLineWidth = src.getRawLineWidth();
        mColor = src.getColorInternal();
        mRawDistanceFromText = src.getRawDistanceFromText();
        mShadow = src.getShadow();
        mFrame = src.getFrame();
        mThemeColor = src.getThemeColorInternal();
        mThemeShade = src.getThemeShade();
        mThemeTint = src.getThemeTint();
        mIsNil = src.mIsNil;
        mParent = src.mParent;
    }

    /// <summary>
    /// Returns an array of values that represents length of on and off runs of a dashed line.
    /// 1 unit equals line width. If the line is not dashed, returns null.
    /// </summary>
    static float[] getDashPattern(/*LineStyle*/int lineStyle, float lineWidth)
    {
        float[] constDashLengths = G_DASH_PATTERN_MAP.get((int)lineStyle);

        if (constDashLengths == null)
            return null;

        // Clone so we don't modify the original.
        float[] dashLengths = new float[constDashLengths.length];
        System.arraycopy(constDashLengths, 0, dashLengths, 0, constDashLengths.length);

        for (int i = 0; i < dashLengths.length; i++)
            dashLengths[i] *= lineWidth;

        return dashLengths;
    }

    /// <summary>
    /// Gets the array of widths of individual lines and gaps between them.
    ///
    /// The result is scaled to the specified "line width".
    /// </summary>
    static float[] getPartWidths(/*LineStyle*/int lineStyle, float lineWidth)
    {
        float[] constPartWidths = G_PART_WIDTH_MAP.get((int)lineStyle);

        // Single part border.
        if (constPartWidths == null)
            return new float[] {lineWidth};

        // Clone so we don't modify the original.
        float[] partWidths = new float[constPartWidths.length];
        System.arraycopy(constPartWidths, 0, partWidths, 0, constPartWidths.length);

        for (int i = 0; i < partWidths.length; i++)
        {
            if (partWidths[i] >= 0)
                partWidths[i] *= lineWidth;
            else
                partWidths[i] = Math.abs(partWidths[i]);
        }

        return partWidths;
    }

    /// <summary>
    /// Returns the number of parts for the specified line style.
    /// </summary>
    /// <remarks>Moved from AE.</remarks>
    static int getPartsCount(/*LineStyle*/int lineStyle)
    {
        float[] partWidths = G_PART_WIDTH_MAP.get((int)lineStyle);
        return (partWidths == null) ? 1 : partWidths.length;
    }

    /// <summary>
    /// Returns actual width of the border for the specified "line width".
    ///
    /// This method correctly calculates actual width of multipart borders.
    /// For example, the "line width" could be 0.5pt, but the total
    /// width of say 4 parts will be 2.5pt.
    ///
    /// Returns zero if the line style is none.
    /// </summary>
    static float getActualWidth(/*LineStyle*/int lineStyle, float lineWidth)
    {
        switch (lineStyle)
        {
            case LineStyle.NONE:
                return 0f;

            case LineStyle.SINGLE:
            case LineStyle.DOT:
            case LineStyle.DOT_DASH:
            case LineStyle.DOT_DOT_DASH:
            case LineStyle.DASH_LARGE_GAP:
            case LineStyle.DASH_SMALL_GAP:
                return lineWidth;

            case LineStyle.DOUBLE:
            case LineStyle.TRIPLE:
            case LineStyle.THIN_THICK_SMALL_GAP:
            case LineStyle.THICK_THIN_SMALL_GAP:
            case LineStyle.THIN_THICK_THIN_SMALL_GAP:
            case LineStyle.THIN_THICK_MEDIUM_GAP:
            case LineStyle.THICK_THIN_MEDIUM_GAP:
            case LineStyle.THIN_THICK_THIN_MEDIUM_GAP:
            case LineStyle.THIN_THICK_LARGE_GAP:
            case LineStyle.THICK_THIN_LARGE_GAP:
            case LineStyle.THIN_THICK_THIN_LARGE_GAP:
            case LineStyle.EMBOSS_3_D:
            case LineStyle.ENGRAVE_3_D:
            {
                float[] partWidths = getPartWidths(lineStyle, lineWidth);

                float totalWidth = 0f;
                for (float partWidth : partWidths)
                    totalWidth += partWidth;

                return totalWidth;
            }
            case LineStyle.THICK:
            case LineStyle.HAIRLINE:
                return 0.75f;
            case LineStyle.WAVE:
                return getWaveWidth(lineWidth);
            case LineStyle.DOUBLE_WAVE:
                return getDoubleWaveWidth(lineWidth);
            case LineStyle.DASH_DOT_STROKER:
            case LineStyle.OUTSET:
                return lineWidth;

            default:
                // This seems to suit other cases (page border art).
                return lineWidth;
        }
    }

    private static float getWaveWidth(float lineWidth)
    {
        return (lineWidth < 1) ? 3f : 3.75f;
    }

    private static float getDoubleWaveWidth(float lineWidth)
    {
        return 6.75f * lineWidth;
    }

    /// <summary>
    /// Gets border number for resolving border conflicts
    /// according to Normative Variations 2.1.169 Part 4 Section 2.4.63, tcBorders (Table Cell Borders).
    /// </summary>
    int getBorderNumber() { return getBorderNumber(getLineStyle()); }

    /// <summary>
    /// Returns border number for resolving border conflicts according to ECMA $17.4.67.
    /// </summary>
    private static int getBorderNumber(/*LineStyle*/int lineStyle)
    {
        switch (lineStyle)
        {
            case LineStyle.SINGLE: return 1;
            case LineStyle.THICK: return 2;
            case LineStyle.DOUBLE: return 3;
            case LineStyle.DOT: return 4;
            case LineStyle.DASH_LARGE_GAP: return 5;
            case LineStyle.DOT_DASH: return 8;
            case LineStyle.DOT_DOT_DASH: return 9;
            case LineStyle.TRIPLE: return 10;
            case LineStyle.THIN_THICK_SMALL_GAP: return 11;
            case LineStyle.THICK_THIN_SMALL_GAP: return 12;
            case LineStyle.THIN_THICK_THIN_SMALL_GAP: return 13;
            case LineStyle.THIN_THICK_MEDIUM_GAP: return 14;
            case LineStyle.THICK_THIN_MEDIUM_GAP: return 15;
            case LineStyle.THIN_THICK_THIN_MEDIUM_GAP: return 16;
            case LineStyle.THIN_THICK_LARGE_GAP: return 17;
            case LineStyle.THICK_THIN_LARGE_GAP: return 18;
            case LineStyle.THIN_THICK_THIN_LARGE_GAP: return 19;
            case LineStyle.WAVE: return 20;
            case LineStyle.DOUBLE_WAVE: return 21;
            case LineStyle.DASH_SMALL_GAP: return 22;
            case LineStyle.DASH_DOT_STROKER: return 23;
            case LineStyle.EMBOSS_3_D: return 24;
            case LineStyle.ENGRAVE_3_D: return 25;
            case LineStyle.OUTSET: return 26;
            case LineStyle.INSET: return 27;
            // not in ECMA $17.4.67. Hairline made equivalent to Single,
            // conflicts are resolved by LineWidth.
            case LineStyle.NONE: return 0;
            case LineStyle.HAIRLINE: return 1;
            default: return 0;
        }
    }

    /// <summary>
    /// Gets border weight for resolving border conflicts
    /// according to Normative Variations 2.1.169 Part 4 Section 2.4.63, tcBorders (Table Cell Borders).
    /// </summary>
    int getWeight()
    {
        // The borders with dotted and dashed styles shall be assigned the weight 1 regardless of the border width and number.
        return ((getLineStyle() != LineStyle.DOT) && (getLineStyle() != LineStyle.DASH_LARGE_GAP))
            ? getBorderNumber() * getRawLineWidth()
            : 1;
    }

    /// <summary>
    /// Creates instance of Nil border.
    /// </summary>
    /// <returns></returns>
    static Border createNilBorder()
    {
        Border border = new Border();
        border.mIsNil = true;
        return border;
    }

    /// <summary>
    /// Makes given border to be Nil border and returns it.
    /// </summary>
    static Border createNilBorder(Border border)
    {
        border.clearFormatting();
        border.mIsNil = true;
        return border;
    }

    /// <summary>
    /// Indicates that the border defines explicitly no border.
    /// </summary>
    boolean isNil() { return mIsNil; }
    void isNil(boolean value) { mIsNil = value; }

    /// <summary>
    /// Returns the winning border in case of border conflict, according to ECMA $17.4.67 with
    /// Normative Variations 2.1.169 Part 4 Section 2.4.63, tcBorders (Table Cell Borders).
    /// See http://msdn.microsoft.com/en-us/library/ff529927%28v=office.12%29.aspx.
    /// </summary>
    /// <remarks>
    /// If the borders are identical, first border wins. According to ECMA this must be "the first border in the reading order".
    /// So the order of parameters is significant, for identical style and color.
    /// </remarks>
    static Border getWinningBorder(Border a, Border b)
    {
        if (a == null)
            return b;

        if (b == null)
            return a;

        if (a.getWeight() != b.getWeight())
            return (a.getWeight() > b.getWeight()) ? a : b;

        int aBorderNumber = a.getBorderNumber();
        int bBorderNumber = b.getBorderNumber();
        if (aBorderNumber != bBorderNumber)
            return (aBorderNumber > bBorderNumber) ? a : b;

        // Styles are identical. Consider brightness. Lesser brightness wins.
        int aBrightness = a.getBrightness0();
        int bBrightness = b.getBrightness0();
        if (aBrightness != bBrightness)
            return (aBrightness < bBrightness) ? a : b;

        aBrightness = a.getBrightness1();
        bBrightness = b.getBrightness1();
        if (aBrightness != bBrightness)
            return (aBrightness < bBrightness) ? a : b;

        // For identical brightness, first border wins.
        return (a.getBrightness2() <= b.getBrightness2()) ? a : b;
    }

    /// <summary>
    /// Brightness according to step 3 of ECMA $17.4.67.
    /// </summary>
    private int getBrightness0() { return (getColor().getRed() & 0xFF) + (getColor().getBlue() & 0xFF) + 2 * (getColor().getGreen() & 0xFF); }

    /// <summary>
    /// Brightness according to step 4 of ECMA $17.4.67.
    /// </summary>
    private int getBrightness1() { return (getColor().getBlue() & 0xFF) + 2 * (getColor().getGreen() & 0xFF); }

    /// <summary>
    /// Brightness according to step 5 of ECMA $17.4.67.
    /// </summary>
    private int getBrightness2() { return (getColor().getGreen() & 0xFF); }

    /// <summary>
    /// Checks if LineStyle is of a valid value.
    /// </summary>
    /// <returns></returns>
    boolean hasValidLineStyle()
    {
        return (getLineStyle() >= LineStyle.NONE && getLineStyle() <= LineStyle.INSET);
    }

    /// <summary>
    /// Sets parent.
    /// </summary>
    void setParent(IBorderAttrSource parent)
    {
        mParent = parent;
    }

    /// <summary>
    /// Copies inherited border values into this border and stops inheriting.
    /// </summary>
    private void beforeChange()
    {
        notifyChanging();

        if (isInherited())
        {
            //Copy all attrs from the inherited border.
            copyFrom(getInherited());
            mParent = null;
        }
    }

    /// <summary>
    /// Only set for an inherited border.
    /// </summary>
    private IBorderAttrSource mParent;
    /// <summary>
    /// The key used to retrieve the parent border object we are inheriting from.
    /// Only used for an inherited border.
    /// </summary>
    private int mKey;
    private /*LineStyle*/int mLineStyle;
    /// <summary>
    /// Gets/sets line width in "raw" units (as stored in DOC or in WordML).
    /// For normal borders the value is in 1/8th of a point.
    /// For page border art, the value is in points.
    ///
    /// It is quite ugly from the model point of view that I store line width and space
    /// in non-standard for the model (twips) measurements. Maybe can rework later,
    /// but watch out for the performance since there could be many borders in big documents with tables
    /// and there will be a lot of unneeded conversions taking place both on load and on save.
    /// </summary>
    private int mRawLineWidth;
    private DrColor mColor = DrColor.EMPTY;
    /// <summary>
    /// Spacing between border and the text in points?
    /// </summary>
    private int mRawDistanceFromText;
    private boolean mShadow;
    private boolean mFrame;

    private String mThemeColor;
    private String mThemeShade;
    private String mThemeTint;

    private boolean mIsNil;

    /// <summary>
    /// Map of dashed line styles to on/off widths. 1 unit equals line width.
    /// </summary>
    private static /*final*/ IntToObjDictionary<float[]> G_DASH_PATTERN_MAP;
    /// <summary>
    /// Map of LineStyle to float[] that indicate widths of individual lines and gaps
    /// of a border that consists of several lines.
    /// Positive value indicates width that is scaled according to the line width.
    /// Negative value indicates a value that is not scaled, just use the absolute value.
    /// </summary>
    private static /*final*/ IntToObjDictionary<float[]> G_PART_WIDTH_MAP;

    static final int MAX_LINE_WIDTH = 31;
    static final int MAX_DISTANCE_FROM_TEXT = 31;

    /// <summary>
    /// A valid border that is not visible. You can use this instead of using null borders.
    /// </summary>
    static /*final*/ Border EMPTY = new Border();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }

    static/* Border()*/
    {
        G_DASH_PATTERN_MAP = new IntToObjDictionary<float[]>();
        G_DASH_PATTERN_MAP.add((int)LineStyle.DOT, new float[] {1f, 1f});
        G_DASH_PATTERN_MAP.add((int)LineStyle.DASH_SMALL_GAP, new float[] {4f, 1f});
        G_DASH_PATTERN_MAP.add((int)LineStyle.DASH_LARGE_GAP, new float[] {4f, 4f});
        G_DASH_PATTERN_MAP.add((int)LineStyle.DOT_DASH, new float[] {7f, 3f, 3f, 3f});
        G_DASH_PATTERN_MAP.add((int)LineStyle.DOT_DOT_DASH, new float[] {6f, 2f, 2f, 2f, 2f, 2f});

        G_PART_WIDTH_MAP = new IntToObjDictionary<float[]>();
        G_PART_WIDTH_MAP.add((int)LineStyle.DOUBLE, new float[] {1f, 1f, 1f});
        G_PART_WIDTH_MAP.add((int)LineStyle.TRIPLE, new float[] {1f, 1f, 1f, 1f, 1f});
        G_PART_WIDTH_MAP.add((int)LineStyle.THIN_THICK_SMALL_GAP, new float[] {1f, -0.75f, -0.75f});
        G_PART_WIDTH_MAP.add((int)LineStyle.THICK_THIN_SMALL_GAP, new float[] {-0.75f, -0.75f, 1f});
        G_PART_WIDTH_MAP.add((int)LineStyle.THIN_THICK_MEDIUM_GAP, new float[] {1f, 0.5f, 0.5f});
        G_PART_WIDTH_MAP.add((int)LineStyle.THICK_THIN_MEDIUM_GAP, new float[] {0.5f, 0.5f, 1f});
        G_PART_WIDTH_MAP.add((int)LineStyle.THIN_THICK_LARGE_GAP, new float[] {-1.5f, 1f, -0.75f});
        G_PART_WIDTH_MAP.add((int)LineStyle.THICK_THIN_LARGE_GAP, new float[] {-0.75f, 1f, -1.5f});
        G_PART_WIDTH_MAP.add((int)LineStyle.THIN_THICK_THIN_SMALL_GAP, new float[] {-0.75f, -0.75f, 1f, -0.75f, -0.75f});
        G_PART_WIDTH_MAP.add((int)LineStyle.THIN_THICK_THIN_MEDIUM_GAP, new float[] {0.5f, 0.5f, 1f, 0.5f, 0.5f});
        G_PART_WIDTH_MAP.add((int)LineStyle.THIN_THICK_THIN_LARGE_GAP, new float[] {-0.75f, 1f, -1.5f, 1f, -0.75f});
        // This is the trick used to draw 3D borders. Basically, these are the only borders that stand out for
        // the way of rendering. I have made them multipart and their parts have different colors (pens). Although
        // this is a rough approach, it works. Maybe I need to make it a bit sharper.
        G_PART_WIDTH_MAP.add((int)LineStyle.EMBOSS_3_D, new float[] {0.25f, 0f, 1f, 0f, 0.25f});
        G_PART_WIDTH_MAP.add((int)LineStyle.ENGRAVE_3_D, new float[] {0.25f, 0f, 1f, 0f, 0.25f});
    }
}

