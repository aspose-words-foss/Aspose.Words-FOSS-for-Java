// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ArgumentUtil;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.Drawing.FontStyle;
import java.awt.Color;
import com.aspose.DrColor;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.MathUtil;
import com.aspose.ms.System.Drawing.msColor;
import com.aspose.DrFont;
import com.aspose.ms.System.msString;
import com.aspose.Language;
import com.aspose.UnicodeBlocks;
import com.aspose.UnicodeUtil;
import com.aspose.ms.System.Collections.SortedList;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Contains font attributes (font name, font size, color, and so on) for an object.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fonts/">Working with Fonts</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>You do not create instances of the <see cref="Font"/> class directly. You just use
/// <see cref="Font"/> to access the font properties of the various objects such as <see cref="Run"/>,
/// <see cref="Paragraph"/>, <see cref="Aspose.Words.Style"/>, <see cref="DocumentBuilder"/>.</p>
/// </remarks>
public class Font implements IBorderAttrSource, IShadingAttrSource, IFillable
{

    /// <summary>
    /// Ctor for some unit testing. Creates a <see cref="Font"/> object on a detached collection of attributes.
    /// </summary>
    static Font makeFont()
    {
        return makeFont(new RunPr(), null);
    }

    /// <summary>
    /// Ctor for some unit testing. Creates a <see cref="Font"/> object on a detached collection of attributes.
    /// </summary>
    static Font makeFont(IRunAttrSource parent)
    {
        return makeFont(parent, null);
    }

    /// <summary>
    /// Ctor for some unit testing. Creates a <see cref="Font"/> object on a detached collection of attributes.
    /// </summary>
    static Font makeFont(IRunAttrSource parent, DocumentBase doc)
    {
        if (parent == null)
        {
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "parent");
        }
        Font font = new Font(parent, doc);
        font = font;
        return font;
    }

    /// <summary>
    /// Ctor for normal use.
    /// </summary>
    /// <param name="parent">The object that provides the attributes.</param>
    /// <param name="doc">Used to retrieve style and theme, can be null.</param>
    Font(IRunAttrSource parent, DocumentBase doc)
    {
        mParent = parent;
        mDoc = doc;
    }

    /// <summary>
    /// Resets to default font formatting.
    /// </summary>
    /// <remarks>
    /// <p>Removes all font formatting specified explicitly on the object from which
    /// <see cref="Font"/> was obtained so the font formatting will be inherited from
    /// the appropriate parent.</p>
    /// </remarks>
    public void clearFormatting()
    {
        mParent.clearRunAttrs();
    }

    /// <summary>
    /// Gets or sets the name of the font.
    /// </summary>
    /// <remarks>
    /// <p>When getting, returns <see cref="NameAscii"/>.</p>
    /// <p>When setting, sets <see cref="NameAscii"/>, <see cref="NameBi"/>, <see cref="NameFarEast"/>
    /// and <see cref="NameOther"/> to the specified value.</p>
    /// </remarks>
    public String getName()
    {
        /*CharacterCategory*/int characterCategory = getCharacterCategory();
        switch (characterCategory)
        {
            case CharacterCategory.OTHER:
                return getNameOther();

            case CharacterCategory.FAR_EAST:
                return getNameFarEast();

            case CharacterCategory.COMPLEX_SCRIPT:
                return getNameBi();

            default:
                return getNameAscii();
        }
    }
    public void setName(String value)
    {
        ArgumentUtil.checkHasChars(value, "value");
        // Have to be on separate lines for autoporting to work.
        setNameAscii(value);
        setNameBi(value);
        setNameFarEast(value);
        setNameOther(value);
    }

    /// <summary>
    /// Returns or sets the font used for Latin text (characters with character codes from 0 (zero) through 127).
    /// </summary>
    /// <seealso cref="Name"/>
    public String getNameAscii() { return ComplexFontName.resolve(fetchAttr(FontAttr.NAME_ASCII), getTheme()); }
    public void setNameAscii(String value)
    {
        ArgumentUtil.checkHasChars(value, "value");
        mParent.setRunAttr(FontAttr.NAME_ASCII, ComplexFontName.fromName(value));
    }

    /// <summary>
    /// Returns or sets the name of the font in a right-to-left language document.
    /// </summary>
    /// <seealso cref="Name"/>
    public String getNameBi() { return ComplexFontName.resolve(fetchAttr(FontAttr.NAME_BI), getTheme()); }
    public void setNameBi(String value)
    {
        ArgumentUtil.checkHasChars(value, "value");
        mParent.setRunAttr(FontAttr.NAME_BI, ComplexFontName.fromName(value));
    }

    /// <summary>
    /// Returns or sets an East Asian font name.
    /// </summary>
    /// <seealso cref="Name"/>
    public String getNameFarEast() { return ComplexFontName.resolve(fetchAttr(FontAttr.NAME_FAR_EAST), getTheme()); }
    public void setNameFarEast(String value)
    {
        ArgumentUtil.checkHasChars(value, "value");
        mParent.setRunAttr(FontAttr.NAME_FAR_EAST, ComplexFontName.fromName(value));
    }

    /// <summary>
    /// Returns or sets the font used for characters with character codes from 128 through 255.
    /// </summary>
    /// <seealso cref="Name"/>
    public String getNameOther() { return ComplexFontName.resolve(fetchAttr(FontAttr.NAME_OTHER), getTheme()); }
    public void setNameOther(String value)
    {
        ArgumentUtil.checkHasChars(value, "value");
        mParent.setRunAttr(FontAttr.NAME_OTHER, ComplexFontName.fromName(value));
    }

    /// <summary>
    /// Gets or sets the theme font in the applied font scheme that is associated with this <see cref="Font"/> object.
    /// </summary>
    /// <dev>
    /// <p>When getting, returns <see cref="ThemeFont"/> depending on character category of the text.</p>
    /// <p>When setting, sets <see cref="ThemeFontAscii"/>, <see cref="ThemeFontBi"/>,
    /// <see cref="ThemeFontFarEast"/> and <see cref="ThemeFontOther"/> to the specified value.</p>
    /// </dev>
    public /*ThemeFont*/int getThemeFont()
    {
        /*CharacterCategory*/int characterCategory = getCharacterCategory();
        switch (characterCategory)
        {
            case CharacterCategory.OTHER:
                return getThemeFontOther();

            case CharacterCategory.FAR_EAST:
                return getThemeFontFarEast();

            case CharacterCategory.COMPLEX_SCRIPT:
                return getThemeFontBi();

            default:
                return getThemeFontAscii();
        }
    }
    public void setThemeFont(/*ThemeFont*/int value)
    {
        // Seems Word logic is quite complex here,
        // but we just apply the value with a corresponding region for a while.
        setThemeFontAscii(value);
        setThemeFontBi(value);
        setThemeFontFarEast(value);
        setThemeFontOther(value);
    }

    /// <summary>
    /// Gets or sets the theme font used for Latin text (characters with character codes from 0 (zero) through 127)
    /// in the applied font scheme that is associated with this <see cref="Font"/> object.
    /// </summary>
    public /*ThemeFont*/int getThemeFontAscii() { return ((ComplexFontName)fetchAttr(FontAttr.NAME_ASCII)).getThemeFont(); }
    public void setThemeFontAscii(/*ThemeFont*/int value)
    {
        mParent.setRunAttr(FontAttr.NAME_ASCII, (value == ThemeFont.NONE)
            ? ComplexFontName.fromName(getNameAscii())
            // Seems Word applies HAnsi here, but such logic is not entirely clear,
            // so we apply Ascii, for a while.
            : ComplexFontName.fromTheme(value, ThemeFontCore.ASCII));
    }

    /// <summary>
    /// Gets or sets the East Asian theme font in the applied font scheme that is associated with this <see cref="Font"/> object.
    /// </summary>
    public /*ThemeFont*/int getThemeFontFarEast() { return ((ComplexFontName)fetchAttr(FontAttr.NAME_FAR_EAST)).getThemeFont(); }
    public void setThemeFontFarEast(/*ThemeFont*/int value)
    {
        mParent.setRunAttr(FontAttr.NAME_FAR_EAST, (value == ThemeFont.NONE)
            ? ComplexFontName.fromName(getNameFarEast())
            : ComplexFontName.fromTheme(value, ThemeFontCore.EAST_ASIA));
    }

    /// <summary>
    /// Gets or sets the theme font used for characters with character codes from 128 through 255
    /// in the applied font scheme that is associated with this <see cref="Font"/> object.
    /// </summary>
    public /*ThemeFont*/int getThemeFontOther() { return ((ComplexFontName)fetchAttr(FontAttr.NAME_OTHER)).getThemeFont(); }
    public void setThemeFontOther(/*ThemeFont*/int value)
    {
        mParent.setRunAttr(FontAttr.NAME_OTHER, (value == ThemeFont.NONE)
            ? ComplexFontName.fromName(getNameOther())
            : ComplexFontName.fromTheme(value, ThemeFontCore.H_ANSI));
    }

    /// <summary>
    /// Gets or sets the theme font in the applied font scheme that is associated with this <see cref="Font"/> object
    /// in a right-to-left language document.
    /// </summary>
    public /*ThemeFont*/int getThemeFontBi() { return ((ComplexFontName)fetchAttr(FontAttr.NAME_BI)).getThemeFont(); }
    public void setThemeFontBi(/*ThemeFont*/int value)
    {
        mParent.setRunAttr(FontAttr.NAME_BI, (value == ThemeFont.NONE)
                ? ComplexFontName.fromName(getNameBi())
                : ComplexFontName.fromTheme(value, ThemeFontCore.BIDI));
    }

    /// <summary>
    /// Gets the font type which shall be used to format any ambiguous characters in the current run.
    /// </summary>
    /*CharacterCategory*/int getCharacterCategoryHint() { return (/*CharacterCategory*/Integer)fetchAttr(FontAttr.CHARACTER_CATEGORY_HINT); }

    /// <summary>
    /// Gets or sets the font size in points.
    /// </summary>
    public double getSize() { return ConvertUtilCore.halfPointToPoint((/*int*/Integer)fetchAttr(FontAttr.SIZE)); }
    public void setSize(double value) { mParent.setRunAttr(FontAttr.SIZE, ConvertUtilCore.pointToHalfPoint(value)); }

    /// <summary>
    /// Gets or sets the font size in points used in a right-to-left document.
    /// </summary>
    public double getSizeBi() { return ConvertUtilCore.halfPointToPoint((/*int*/Integer)fetchAttr(FontAttr.SIZE_BI)); }
    public void setSizeBi(double value) { mParent.setRunAttr(FontAttr.SIZE_BI, ConvertUtilCore.pointToHalfPoint(value)); }

    /// <summary>
    /// True if the font is formatted as bold.
    /// </summary>
    public boolean getBold() { return getBool(FontAttr.BOLD); }
    public void setBold(boolean value) { setBool(FontAttr.BOLD, value); }

    /// <summary>
    /// True if the right-to-left text is formatted as bold.
    /// </summary>
    public boolean getBoldBi() { return getBool(FontAttr.BOLD_BI); }
    public void setBoldBi(boolean value) { setBool(FontAttr.BOLD_BI, value); }

    /// <summary>
    /// True if the font is formatted as italic.
    /// </summary>
    public boolean getItalic() { return getBool(FontAttr.ITALIC); }
    public void setItalic(boolean value) { setBool(FontAttr.ITALIC, value); }

    /// <summary>
    /// True if the right-to-left text is formatted as italic.
    /// </summary>
    public boolean getItalicBi() { return getBool(FontAttr.ITALIC_BI); }
    public void setItalicBi(boolean value) { setBool(FontAttr.ITALIC_BI, value); }

    /// <summary>
    /// Gets font style.
    /// </summary>
    /// <remarks>
    /// This property concerns only left-to-right text.
    /// </remarks>
    /*FontStyle*/int getFontStyle()
    {
        /*FontStyle*/int result = FontStyle.REGULAR;
        if (getBold())
            result |= FontStyle.BOLD;
        if (getItalic())
            result |= FontStyle.ITALIC;

        return result;
    }

    /// <summary>
    /// Gets or sets the color of the font.
    /// </summary>
    public Color getColor() { return getColorInternal().toNativeColor(); }
    public void setColor(Color value) { setColorInternal(DrColor.fromNativeColor(value)); }

    /// <summary>
    /// Gets or sets the theme color in the applied color scheme that is associated with this <see cref="Font"/> object.
    /// </summary>
    public /*ThemeColor*/int getThemeColor()
    {
        return ThemeColorConverter.fromString((String)fetchAttr(FontAttr.THEME_COLOR));
    }
    public void setThemeColor(/*ThemeColor*/int value)
    {
        // Remove theme tint and shade to mimic Word VBA.
        mParent.removeRunAttr(FontAttr.THEME_TINT);
        mParent.removeRunAttr(FontAttr.THEME_SHADE);

        if (value == ThemeColor.NONE)
        {
            mParent.removeRunAttr(FontAttr.THEME_COLOR);
        }
        else
        {
            mParent.setRunAttr(FontAttr.THEME_COLOR, ThemeColorConverter.toString(value));

            // Setting ThemeColor in Word VBA also sets a corresponding resolved RGB color,
            // but we decided just to remove non-theme color for a while.
            mParent.removeRunAttr(FontAttr.COLOR);
        }
    }

    /// <summary>
    /// Gets or sets a double value that lightens or darkens a color.
    /// </summary>
    /// <remarks>
    /// <para> The allowed values are in range from -1 (darkest) to 1 (lightest) for this property.</para>
    /// <para>Zero (0) is neutral.</para>
    /// </remarks>
    /// <exception cref="ArgumentOutOfRangeException">Throw if set this property to a value less than -1 or more than 1.</exception>
    /// <exception cref="InvalidOperationException">Throw if set this property for <see cref="Font"/> object with non-theme colors.</exception>
    public double getTintAndShade()
    {
        // Note, Word stores inverted value (1 - tint) in the document.
        String value = (String)fetchAttr(FontAttr.THEME_TINT);
        if (StringUtil.hasChars(value))
            return 1.0d - ((double)FormatterPal.parseHex(value) / 255.0);

        // Note, Word stores inverted value (1 + shade) in the document.
        value = (String)fetchAttr(FontAttr.THEME_SHADE);
        if (StringUtil.hasChars(value))
            return -1.0d - ((double)FormatterPal.parseHex(value) / -255);

        return 0.0d;
    }
    public void setTintAndShade(double value)
    {
        // Despite https://docs.microsoft.com/en-us/office/vba/api/excel.font.tintandshade
        // says that "This property works for both theme colors and non-theme colors.",
        // in fact it does not work in Word VBA for non-theme colors.
        if (getThemeColor() == ThemeColor.NONE)
            throw new IllegalStateException("TintAndShade cannot be applied to a non-theme color.");

        ArgumentUtil.checkRangeInclusive(value, -1.0d, 1.0d, "TintAndShade");

        if (MathUtil.isZero(value))
        {
            mParent.removeRunAttr(FontAttr.THEME_TINT);
            mParent.removeRunAttr(FontAttr.THEME_SHADE);
        }
        else
        {
            if (value > 0)
            {
                // Note, Word stores inverted value (1 - tint) in the document.
                String tint = FormatterPal.intToStrX2((int)((1.0 - value) * 255.0));
                mParent.setRunAttr(FontAttr.THEME_TINT, tint);
                mParent.removeRunAttr(FontAttr.THEME_SHADE);
            }
            else
            {
                // Note, Word stores inverted value (1 + shade) in the document.
                String shade = FormatterPal.intToStrX2((int)((-1 - value) * -255));
                mParent.setRunAttr(FontAttr.THEME_SHADE, shade);
                mParent.removeRunAttr(FontAttr.THEME_TINT);
            }
        }
        // Word also always updates a corresponding RGB color by resolving theme color with the new TintAndShade,
        // but we decided to remove the corresponding RGB color for a while, so nothing to update.
    }

    /// <summary>
    /// Returns the present calculated color of the text (black or white) to be used for 'auto color'.
    /// If the color is not 'auto' then returns <see cref="Color"/>.
    /// </summary>
    /// <remarks>
    /// <p>
    /// When text has 'automatic color', the actual color of text is calculated automatically
    /// so that it is readable against the background color. As you change the background color,
    /// the text color will automatically switch to black or white in MS Word to maximize legibility.</p>
    /// </remarks>
    public Color getAutoColor()
    {
        // If color is not 'auto', then simply return it.
        if (getColor() != msColor.Empty)
            return getColor();

        Shading shading = InlineHelper.topVisibleShading(mParent);

        if (shading == null)
        {
            // WORDSNET-26032 Word returns color of style font reference inside Dml shape.
            IInline inline = ms.as(mParent, IInline.class);
            if ((inline != null) && (inline.getParentParagraph_IInline() != null))
            {
                Shape parentShape = ms.as(inline.getParentParagraph_IInline().getFirstNonMarkupParentNode(), Shape.class);
                if ((parentShape != null) && parentShape.isDmlShape())
                {
                    if ((parentShape.getDmlShape().getStyle() != null) && !parentShape.getDmlShape().getStyle().getFontReference().getColor().isEmpty())
                    {
                        DrColor drColor = parentShape.getDmlShape().getStyle().getFontReference().getColor().createDrColor(getTheme(), null);
                        return drColor.toNativeColor();
                    }
                }
            }

            return Color.BLACK;
        }

        return (DrColor.equals(FontColorResolver.resolveAutoFontColorOnShading(shading), DrColor.BLACK))
                   ? Color.BLACK
                   : Color.WHITE;
    }

    DrColor getColorInternal() { return (DrColor)fetchAttr(FontAttr.COLOR); }
    void setColorInternal(DrColor value)
    {
        mParent.setRunAttr(FontAttr.COLOR, value);

        // Remove theme color when customer applies basic color.
        if (mParent.getDirectRunAttr(FontAttr.THEME_COLOR) != null)
            mParent.removeRunAttr(FontAttr.THEME_COLOR);
        if (mParent.getDirectRunAttr(FontAttr.THEME_SHADE) != null)
            mParent.removeRunAttr(FontAttr.THEME_SHADE);
        if (mParent.getDirectRunAttr(FontAttr.THEME_TINT) != null)
            mParent.removeRunAttr(FontAttr.THEME_TINT);
    }

    /// <summary>
    /// True if the font is formatted as strikethrough text.
    /// </summary>
    public boolean getStrikeThrough() { return getBool(FontAttr.STRIKE_THROUGH); }
    public void setStrikeThrough(boolean value) { setBool(FontAttr.STRIKE_THROUGH, value); }

    /// <summary>
    /// True if the font is formatted as double strikethrough text.
    /// </summary>
    public boolean getDoubleStrikeThrough() { return getBool(FontAttr.DOUBLE_STRIKE_THROUGH); }
    public void setDoubleStrikeThrough(boolean value) { setBool(FontAttr.DOUBLE_STRIKE_THROUGH, value); }

    /// <summary>
    /// True if the font is formatted as shadowed.
    /// </summary>
    public boolean getShadow() { return getBool(FontAttr.SHADOW); }
    public void setShadow(boolean value) { setBool(FontAttr.SHADOW, value); }

    /// <summary>
    /// True if the font is formatted as outline.
    /// </summary>
    public boolean getOutline() { return getBool(FontAttr.OUTLINE); }
    public void setOutline(boolean value) { setBool(FontAttr.OUTLINE, value); }

    /// <summary>
    /// True if the font is formatted as embossed.
    /// </summary>
    public boolean getEmboss() { return getBool(FontAttr.EMBOSS); }
    public void setEmboss(boolean value) { setBool(FontAttr.EMBOSS, value); }

    /// <summary>
    /// True if the font is formatted as engraved.
    /// </summary>
    public boolean getEngrave() { return getBool(FontAttr.ENGRAVE); }
    public void setEngrave(boolean value) { setBool(FontAttr.ENGRAVE, value); }

    /// <summary>
    /// True if the font is formatted as superscript.
    /// </summary>
    public boolean getSuperscript() { return (getVerticalAlignment() == RunVerticalAlignment.SUPERSCRIPT); }
    public void setSuperscript(boolean value)
    {
        // WORDSNET-2004 We shouldn't set Superscript to false if current vertical alignment is subscript,
        // because by doing so we would change subscript to baseline and reset the Subscript property by modifying
        // the Superscript property. This behavior is confusing and it is better to keep vertical alignment unchanged.
        if (!value && getSubscript())
            return;

        setVerticalAlignment((value) ? RunVerticalAlignment.SUPERSCRIPT : RunVerticalAlignment.BASELINE);
    }

    /// <summary>
    /// True if the font is formatted as subscript.
    /// </summary>
    public boolean getSubscript() { return (getVerticalAlignment() == RunVerticalAlignment.SUBSCRIPT); }
    public void setSubscript(boolean value)
    {
        // WORDSNET-2004 We shouldn't set Subscript to false if current vertical alignment is superscript,
        // because by doing so we would change superscript to baseline and reset the Superscript property by modifying
        // the Subscript property. This behavior is confusing and it is better to keep vertical alignment unchanged.
        if (!value && getSuperscript())
            return;

        setVerticalAlignment((value) ? RunVerticalAlignment.SUBSCRIPT : RunVerticalAlignment.BASELINE);
    }

    /// <summary>
    /// True if the font is formatted as small capital letters.
    /// </summary>
    public boolean getSmallCaps() { return getBool(FontAttr.SMALL_CAPS); }
    public void setSmallCaps(boolean value) { setBool(FontAttr.SMALL_CAPS, value); }

    /// <summary>
    /// True if the font is formatted as all capital letters.
    /// </summary>
    public boolean getAllCaps() { return getBool(FontAttr.ALL_CAPS); }
    public void setAllCaps(boolean value) { setBool(FontAttr.ALL_CAPS, value); }

    /// <summary>
    /// True if the font is formatted as hidden text.
    /// </summary>
    public boolean getHidden() { return getBool(FontAttr.HIDDEN); }
    public void setHidden(boolean value) { setBool(FontAttr.HIDDEN, value); }

    /// <summary>
    /// Gets or sets the type of underline applied to the font.
    /// </summary>
    public /*Underline*/int getUnderline() { return (/*Underline*/Integer)fetchAttr(FontAttr.UNDERLINE); }
    public void setUnderline(/*Underline*/int value) { mParent.setRunAttr(FontAttr.UNDERLINE, value); }

    /// <summary>
    /// Gets or sets the color of the underline applied to the font.
    /// </summary>
    public Color getUnderlineColor() { return getUnderlineColorInternal().toNativeColor(); }
    public void setUnderlineColor(Color value) { setUnderlineColorInternal(DrColor.fromNativeColor(value)); }

    /// <summary>
    /// Gets or sets the spacing type of the numeral being displayed.
    /// </summary>
    public /*NumSpacing*/int getNumberSpacing() { return (/*NumSpacing*/Integer)fetchAttr(FontAttr.OPEN_TYPE_NUM_SPACING); }
    public void setNumberSpacing(/*NumSpacing*/int value) { mParent.setRunAttr(FontAttr.OPEN_TYPE_NUM_SPACING, value); }

    DrColor getUnderlineColorInternal() { return (DrColor)fetchAttr(FontAttr.UNDERLINE_COLOR); }
    void setUnderlineColorInternal(DrColor value) { mParent.setRunAttr(FontAttr.UNDERLINE_COLOR, value); }

    /// <summary>
    /// Gets or sets character width scaling in percent.
    /// </summary>
    public int getScaling() { return (/*int*/Integer)fetchAttr(FontAttr.SCALING); }
    public void setScaling(int value) { mParent.setRunAttr(FontAttr.SCALING, value); }

    /// <summary>
    /// Returns or sets the spacing (in points) between characters .
    /// </summary>
    public double getSpacing() { return ConvertUtilCore.twipToPoint(getSpacingRaw()); }
    public void setSpacing(double value) { setSpacingRaw(ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Returns or sets the spacing (in twips) between characters .
    /// </summary>
    int getSpacingRaw() { return (/*int*/Integer)fetchAttr(FontAttr.SPACING); }
    void setSpacingRaw(int value) { mParent.setRunAttr(FontAttr.SPACING, value); }

    /// <summary>
    /// Returns line spacing of this font (in points).
    /// </summary>
    public double getLineSpacing()
    {
        /*CharacterCategory*/int characterCategory = getCharacterCategory();
        /*FontStyle*/int fontStyle = getFontStyle(characterCategory);
        float size = getBidi() ? (float)this.getSizeBi() : (float)this.getSize();

        // Line spacing depends from font family, font style (see method "FontFamily.GetLineSpacing"
        // in the "System.Drawing") and font size (see "DesignUnitsToPoints" method of the "TTFont" class).
        DrFont font = getFontProvider().fetchDrFont(this.getName(), size, fontStyle);

        // Line spacing depends from the font metrics. "PrinterMetrics" has to be used according
        // to compatibility option.
        // FOSS

        return font.getLineSpacingPoints();
    }

    /// <summary>
    /// Gets or sets the position of text (in points) relative to the base line.
    /// A positive number raises the text, and a negative number lowers it.
    /// </summary>
    public double getPosition() { return ConvertUtilCore.halfPointToPoint((/*int*/Integer)fetchAttr(FontAttr.POSITION)); }
    public void setPosition(double value) { mParent.setRunAttr(FontAttr.POSITION, ConvertUtilCore.pointToHalfPoint(value)); }

    /// <summary>
    /// Gets or sets the font size at which kerning starts.
    /// </summary>
    public double getKerning() { return ConvertUtilCore.halfPointToPoint((/*int*/Integer)fetchAttr(FontAttr.KERNING)); }
    public void setKerning(double value) { mParent.setRunAttr(FontAttr.KERNING, ConvertUtilCore.pointToHalfPoint(value)); }

    /// <summary>
    /// Gets or sets the highlight (marker) color.
    /// </summary>
    public Color getHighlightColor() { return getHighlightColorInternal().toNativeColor(); }
    public void setHighlightColor(Color value) { setHighlightColorInternal(DrColor.fromNativeColor(value)); }

    DrColor getHighlightColorInternal() { return (DrColor)fetchAttr(FontAttr.HIGHLIGHT_COLOR); }
    void setHighlightColorInternal(DrColor value) { mParent.setRunAttr(FontAttr.HIGHLIGHT_COLOR, value); }

    /// <summary>
    /// Gets or sets the font animation effect.
    /// </summary>
    public /*TextEffect*/int getTextEffect() { return (/*TextEffect*/Integer)fetchAttr(FontAttr.TEXT_EFFECT); }
    public void setTextEffect(/*TextEffect*/int value) { mParent.setRunAttr(FontAttr.TEXT_EFFECT, value); }

    /// <summary>
    /// Gets fill formatting for the <see cref="Font"/>.
    /// </summary>
    public Fill getFill()
    {
        if (mFill == null)
            mFill = new Fill(this);

        return mFill;
    }

    /// <summary>
    /// Checks if particular DrawingML text effect is applied.
    /// </summary>
    /// <param name="dmlEffectType">DrawingML text effect type.</param>
    /// <returns><c>true</c> if particular DrawingML text effect is applied.</returns>
    public boolean hasDmlEffect(/*TextDmlEffect*/int dmlEffectType)
    {
        switch (dmlEffectType)
        {
            case TextDmlEffect.GLOW:
                return hasDirectAttr(FontAttr.EFFECT_GLOW);
            case TextDmlEffect.FILL:
                return hasDirectAttr(FontAttr.EFFECT_FILL);
            case TextDmlEffect.SHADOW:
                return hasDirectAttr(FontAttr.EFFECT_SHADOW);
            case TextDmlEffect.OUTLINE:
                return hasDirectAttr(FontAttr.EFFECT_OUTLINE);
            case TextDmlEffect.EFFECT_3_D:
                return (hasDirectAttr(FontAttr.EFFECT_PROPS_3_D) || hasDirectAttr(FontAttr.EFFECT_SCENE_3_D));
            case TextDmlEffect.REFLECTION:
                return hasDirectAttr(FontAttr.EFFECT_REFLECTION);
            default:
                throw new IllegalStateException("Unexpected TextDmlEffect.");
        }
    }

    /// <summary>
    /// Specifies whether the contents of this run shall have right-to-left characteristics.
    /// </summary>
    /// <remarks>
    /// <para>This property, when on, shall not be used with strongly left-to-right text. Any behavior under that condition is unspecified.
    /// This property, when off, shall not be used with strong right-to-left text. Any behavior under that condition is unspecified.</para>
    ///
    /// <para>When the contents of this run are displayed, all characters shall be treated as complex script characters for formatting
    /// purposes. This means that <see cref="BoldBi"/>, <see cref="ItalicBi"/>, <see cref="SizeBi"/> and a corresponding font name
    /// will be used when rendering this run.</para>
    ///
    /// <para>Also, when the contents of this run are displayed, this property acts as a right-to-left override for characters
    /// which are classified as "weak types" and "neutral types".</para>
    /// </remarks>
    public boolean getBidi() { return getBool(FontAttr.BIDI); }
    public void setBidi(boolean value) { setBool(FontAttr.BIDI, value); }

    /// <summary>
    /// Specifies whether the contents of this run shall be treated as complex script text regardless
    /// of their Unicode character values when determining the formatting for this run.
    /// </summary>
    public boolean getComplexScript() { return getBool(FontAttr.COMPLEX_SCRIPT); }
    public void setComplexScript(boolean value) { setBool(FontAttr.COMPLEX_SCRIPT, value); }

    /// <summary>
    /// True when the formatted characters are not to be spell checked.
    /// </summary>
    public boolean getNoProofing() { return getBool(FontAttr.NO_PROOFING); }
    public void setNoProofing(boolean value) { setBool(FontAttr.NO_PROOFING, value); }

    /// <summary>
    /// Gets or sets the locale identifier (language) of the formatted characters.
    /// </summary>
    /// <remarks>
    /// For the list of locale identifiers see https://msdn.microsoft.com/en-us/library/cc233965.aspx
    /// </remarks>
    public int getLocaleId() { return (/*int*/Integer)fetchAttr(FontAttr.LOCALE_ID); }
    public void setLocaleId(int value) { mParent.setRunAttr(FontAttr.LOCALE_ID, value); }

    /// <summary>
    /// Gets or sets the locale identifier (language) of the formatted right-to-left characters.
    /// </summary>
    /// <remarks>
    /// For the list of locale identifiers see https://msdn.microsoft.com/en-us/library/cc233965.aspx
    /// </remarks>
    public int getLocaleIdBi() { return (/*int*/Integer)fetchAttr(FontAttr.LOCALE_ID_BI); }
    public void setLocaleIdBi(int value) { mParent.setRunAttr(FontAttr.LOCALE_ID_BI, value); }

    /// <summary>
    /// Gets or sets the locale identifier (language) of the formatted Asian characters.
    /// </summary>
    /// <remarks>
    /// For the list of locale identifiers see https://msdn.microsoft.com/en-us/library/cc233965.aspx
    /// </remarks>
    public int getLocaleIdFarEast() { return (/*int*/Integer)fetchAttr(FontAttr.LOCALE_ID_FAR_EAST); }
    public void setLocaleIdFarEast(int value) { mParent.setRunAttr(FontAttr.LOCALE_ID_FAR_EAST, value); }

    /// <summary>
    /// Returns a <see cref="Aspose.Words.Border"/> object that specifies border for the font.
    /// </summary>
    public Border getBorder()
    {
        //<<GetOrCreateComplexAttr>> pattern
        Border border = (Border)mParent.getDirectRunAttr(FontAttr.BORDER);
        if (border == null)
        {
            Font parent = this;
            border = new Border(parent, FontAttr.BORDER);
            border = border;
            mParent.setRunAttr(FontAttr.BORDER, border);
        }
        return border;
    }

    /// <summary>
    /// Returns a <see cref="Aspose.Words.Shading"/> object that refers to the shading formatting for the font.
    /// </summary>
    public Shading getShading()
    {
        //<<GetOrCreateComplexAttr>> pattern
        Shading shading = (Shading)mParent.getDirectRunAttr(FontAttr.SHADING);
        if (shading == null)
        {
            Font parent = this;
            shading = new Shading(this, FontAttr.SHADING);
            shading = shading;
            mParent.setRunAttr(FontAttr.SHADING, shading);
        }
        return shading;
    }

    /// <summary>
    /// Gets or sets the character style applied to this formatting.
    /// </summary>
    public Style getStyle() { return getStyles().fetchByIstd(getIstd(), StyleIndex.DEFAULT_PARAGRAPH_FONT); }
    public void setStyle(Style value)
    {
        if (value == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "value");

        if ((getStyles() != null) && (value.getDocument() != getStyles().getDocument()))
            throw new IllegalArgumentException("This style belongs to a different document.");

        if (value.getType() != StyleType.CHARACTER)
            throw new IllegalArgumentException("This style is not a character style.");

        setIstd(value.getIstd());
    }

    /// <summary>
    /// Gets or sets the name of the character style applied to this formatting.
    /// </summary>
    public String getStyleName() { return getStyle().getName(); }
    public void setStyleName(String value) { setStyle(getStyles().fetchByName(value)); }

    /// <summary>
    /// Gets or sets the locale independent style identifier of the character style applied to this formatting.
    /// </summary>
    public /*StyleIdentifier*/int getStyleIdentifier() { return getStyle().getStyleIdentifier(); }
    public void setStyleIdentifier(/*StyleIdentifier*/int value) { setStyle(getStyles().fetchBySti(value)); }

    /// <summary>
    /// Specifies whether the current font should use the document grid characters per line settings
    /// when laying out.
    /// </summary>
    public boolean getSnapToGrid() { return getBool(FontAttr.SNAP_TO_GRID); }
    public void setSnapToGrid(boolean value) { setBool(FontAttr.SNAP_TO_GRID, value); }

    /// <summary>
    /// Gets or sets the emphasis mark applied to this formatting.
    /// </summary>
    public /*EmphasisMark*/int getEmphasisMark() { return (/*EmphasisMark*/Integer)fetchAttr(FontAttr.EMPHASIS_MARK); }
    public void setEmphasisMark(/*EmphasisMark*/int value) { mParent.setRunAttr(FontAttr.EMPHASIS_MARK, value); }

    int getIstd()
    {
        Object istd = mParent.getDirectRunAttr(FontAttr.ISTD);
        //When getting the style of the run, don't try to retrieve the value from the parent
        //since the parent is the style and it will result in an endless loop.
        return (istd != null) ? (/*int*/Integer)istd : (/*int*/Integer)RunPr.fetchDefaultAttr(FontAttr.ISTD);
    }
    void setIstd(int value)
    {
        mParent.setRunAttr(FontAttr.ISTD, value);
    }

    /*RunVerticalAlignment*/int getVerticalAlignment() { return (/*RunVerticalAlignment*/Integer)fetchAttr(FontAttr.VERTICAL_ALIGNMENT); }
    void setVerticalAlignment(/*RunVerticalAlignment*/int value) { mParent.setRunAttr(FontAttr.VERTICAL_ALIGNMENT, value); }

    /// <summary>
    /// Returns the font name to use for the specified character category.
    /// </summary>
    String getFontName(/*CharacterCategory*/int category)
    {
        //Duplication of WordUtil.GetFontNameByCategory() - code moved here for speed.
        switch (category)
        {
            case CharacterCategory.ASCII:
                return getNameAscii();
            case CharacterCategory.COMPLEX_SCRIPT:
                return getNameBi();
            case CharacterCategory.FAR_EAST:
                return getNameFarEast();
            case CharacterCategory.OTHER:
            default:
                return getNameOther();
        }
    }

    /// <summary>
    /// Returns locale ID for the specified character category.
    /// </summary>
    int getLocaleId(/*CharacterCategory*/int category)
    {
        switch (category)
        {
            case CharacterCategory.COMPLEX_SCRIPT:
                return getLocaleIdBi();
            case CharacterCategory.FAR_EAST:
                return getLocaleIdFarEast();
            default:
                return getLocaleId();
        }
    }

    /// <summary>
    /// Indicates that parent inline has character category hint set.
    /// </summary>
    boolean hasCharacterCategoryHint() { return InlineHelper.hasCharacterCategoryHint(mParent); }

    /// <summary>
    /// Gets the actual size of the font as it appears in the layout.
    /// The actual size could be smaller than the specified font size
    /// for small caps and/or subscript/superscript font.
    /// </summary>
    /// <param name="isIgnoreSmallCaps">We sometimes need to ignore the fact that the font is small caps.
    /// This is the case for uppercase characters as well as for calculating underlines.</param>
    float getActualSize(boolean isIgnoreSmallCaps)
    {
        double result = getSize();

        if (!isIgnoreSmallCaps && getSmallCaps())
            result *= SMALL_CAPS_SIZE_FACTOR;

        if (getSubscript() || getSuperscript())
            result *= SUBSCRIPT_SIZE_FACTOR;

        return (float)result;
    }

    /// <summary>
    /// Gets the attribute from the attribute collection or from one of the parents.
    /// RK I'm not sure this should actually be in Font. But where else?
    /// </summary>
    Object fetchAttr(int key)
    {
        Document doc = (mDoc != null) ? mDoc.fetchDocumentOrGlossaryMain() : null;

        /*RevisionsView*/int view = (doc != null)
            ? doc.getRevisionsView()
            : RevisionsView.ORIGINAL;

        // MF Refactored to let AE access this method.
        return InlineHelper.fetchAttr(mParent, key, view);
    }

    /// <summary>
    /// Checks whether direct attribute is specified in the collection
    /// </summary>
    boolean hasDirectAttr(int key)
    {
        Object value = mParent.getDirectRunAttr(key);
        return value != null;
    }

    /// <summary>
    /// Returns <see cref="FontStyle"/> considering <paramref name="charCategory"/>.
    /// </summary>
    /*FontStyle*/int getFontStyle(/*CharacterCategory*/int charCategory)
    {
        boolean isBold;
        boolean isItalic;

        if ((charCategory == CharacterCategory.FAR_EAST) ||
            ((charCategory == CharacterCategory.OTHER) && (getCharacterCategoryHint() == CharacterCategory.FAR_EAST)))
        {
            isBold = getBoldBi();
            isItalic = getItalicBi();
        }
        else
        {
            isBold = getBold();
            isItalic = getItalic();
        }

        /*FontStyle*/int result;
        if (isBold)
        {
            result = FontStyle.BOLD;
            if (isItalic)
                result |= FontStyle.ITALIC;
        }
        else
        {
            result = isItalic ? FontStyle.ITALIC : FontStyle.REGULAR;
        }

        return result;
    }

    private boolean getBool(int key)
    {
        return InlineHelper.getBool(mParent, key);
    }

    private void setBool(int key, boolean value)
    {
        mParent.setRunAttr(key, AttrBoolEx.fromBool(value));
    }

    /// <summary>
    /// Determines current character category.
    /// [MS-OE376]: Office Implementation Information for ECMA-376 Standards Support.
    /// 2.1.89 Part 4 Section 2.3.2.24, rFonts (Run Fonts).
    /// </summary>
    /*CharacterCategory*/int getCharacterCategory()
    {
        Run run = getParentRun();
        if ((run == null) || !StringUtil.hasChars(run.getText()))
            return CharacterCategory.ASCII;

        if (getComplexScript() || getBidi())
            return CharacterCategory.COMPLEX_SCRIPT;

        // Special case from spec.
        if ((msString.equals(getNameAscii(), getNameOther())) && ("Times New Roman".equals(getNameFarEast())))
            return CharacterCategory.ASCII;

        /*CharacterCategory*/int hint = (run.getRunPr().get(FontAttr.CHARACTER_CATEGORY_HINT) != null)
            ? (/*CharacterCategory*/Integer)run.getRunPr().get(FontAttr.CHARACTER_CATEGORY_HINT)
            : CharacterCategory.OTHER;
        /*Language*/int langEastAsia = (/*Language*/int)getLocaleIdFarEast();

        int ch = run.getText().charAt(0);
        /*UnicodeBlocks*/int block = UnicodeUtil.getUnicodeBlock(ch);

        if ((block == UnicodeBlocks.BASIC_LATIN) || (block == UnicodeBlocks.ARABIC_PRESENTATION_FORMS_A) ||
            (block == UnicodeBlocks.ARABIC_PRESENTATION_FORMS_B) || (block == UnicodeBlocks.HEBREW) ||
            (block == UnicodeBlocks.ARABIC) || (block == UnicodeBlocks.SYRIAC) ||
            (block == UnicodeBlocks.ARABIC_SUPPLEMENT) || (block == UnicodeBlocks.THAANA) ||
            ((block == UnicodeBlocks.ALPHABETIC_PRESENTATION_FORMS) && (ch >= 0xfb1d)))
            return CharacterCategory.ASCII;

        if ((hint == CharacterCategory.FAR_EAST) && (block == UnicodeBlocks.LATIN_1_SUPPLEMENT) &&
                 ((ch == 0x00a1) || (ch == 0x00a4) || (ch == 0x00a7) || (ch == 0x00a8) || (ch == 0x00aa) ||
                  (ch == 0x00ad) || (ch == 0x00af) || (ch == 0x00d7) || (ch == 0x00f7) ||
                  ((ch >= 0x00b0) && (ch <= 0x00b4) || ((ch >= 0x00b6) && (ch <= 0x00ba)) ||
                   ((ch >= 0x00bc) && (ch <= 0x00bf)))))
            return CharacterCategory.FAR_EAST;

        if ((hint == CharacterCategory.FAR_EAST) &&
                 ((langEastAsia == Language.CHINESE_TRADITIONAL) || (langEastAsia == Language.CHINESE_SIMPLIFIED)) &&
                 ((block == UnicodeBlocks.LATIN_EXTENDED_ADDITIONAL) ||
                  ((block == UnicodeBlocks.LATIN_1_SUPPLEMENT) && (ch == 0x00e0) || (ch == 0x00e1) || (ch == 0x00ec) ||
                  (ch == 0x00ed) || (ch == 0x00f2) || (ch == 0x00f3) || (ch == 0x00f9) ||
                  (ch == 0x00fa) || (ch == 0x00fc) || ((ch >= 0x00e8) && (ch <= 0x00ea)))))
            return CharacterCategory.FAR_EAST;

        if ((hint == CharacterCategory.FAR_EAST) &&
                  ((block == UnicodeBlocks.LATIN_EXTENDED_A) || (block == UnicodeBlocks.LATIN_EXTENDED_B) ||
                   (block == UnicodeBlocks.GENERAL_PUNCTUATION) || (block == UnicodeBlocks.SUPERSCRIPTS_AND_SUBSCRIPTS) ||
                   (block == UnicodeBlocks.SPACING_MODIFIER_LETTERS) || (block == UnicodeBlocks.COMBINING_DIACRITICAL_MARKS) ||
                   (block == UnicodeBlocks.CURRENCY_SYMBOLS) || (block == UnicodeBlocks.LETTERLIKE_SYMBOLS) ||
                   (block == UnicodeBlocks.NUMBER_FORMS) || (block == UnicodeBlocks.ARROWS) ||
                   (block == UnicodeBlocks.MATHEMATICAL_OPERATORS) || (block == UnicodeBlocks.MISCELLANEOUS_TECHNICAL) ||
                   (block == UnicodeBlocks.CONTROL_PICTURES) || (block == UnicodeBlocks.OPTICAL_CHARACTER_RECOGNITION) ||
                   (block == UnicodeBlocks.ENCLOSED_ALPHANUMERICS) || (block == UnicodeBlocks.BOX_DRAWING) ||
                   (block == UnicodeBlocks.BLOCK_ELEMENTS) || (block == UnicodeBlocks.GEOMETRIC_SHAPES) ||
                   (block == UnicodeBlocks.MISCELLANEOUS_SYMBOLS) || (block == UnicodeBlocks.DINGBATS) ||
                   (block == UnicodeBlocks.CJK_RADICALS_SUPPLEMENT) || (block == UnicodeBlocks.PRIVATE_USE_AREA) ||
                   (block == UnicodeBlocks.CYRILLIC) || (block == UnicodeBlocks.IPA_EXTENSIONS) ||
                   ((block == UnicodeBlocks.GREEK_AND_COPTIC) && (ch <= 0x03cf))))
            return CharacterCategory.FAR_EAST;

        if ((block == UnicodeBlocks.HANGUL_JAMO) || (block == UnicodeBlocks.KANGXI_RADICALS) ||
                 (block == UnicodeBlocks.IDEOGRAPHIC_DESCRIPTION_CHARACTERS) || (block == UnicodeBlocks.CJK_SYMBOLS_AND_PUNCTUATION) ||
                 (block == UnicodeBlocks.HIRAGANA) || (block == UnicodeBlocks.KATAKANA) ||
                 (block == UnicodeBlocks.BOPOMOFO) || (block == UnicodeBlocks.HANGUL_COMPATIBILITY_JAMO) ||
                 (block == UnicodeBlocks.KANBUN) || (block == UnicodeBlocks.ENCLOSED_CJK_LETTERS_AND_MONTHS) ||
                 (block == UnicodeBlocks.CJK_COMPATIBILITY) || (block == UnicodeBlocks.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) ||
                 (block == UnicodeBlocks.YI_SYLLABLES) || (block == UnicodeBlocks.YI_RADICALS) ||
                 (block == UnicodeBlocks.HANGUL_SYLLABLES) || (block == UnicodeBlocks.HIGH_SURROGATES) ||
                 (block == UnicodeBlocks.HIGH_PRIVATE_USE_SURROGATES) || (block == UnicodeBlocks.LOW_SURROGATES) ||
                 (block == UnicodeBlocks.CJK_COMPATIBILITY_IDEOGRAPHS) || (block == UnicodeBlocks.CJK_COMPATIBILITY_FORMS) ||
                 (block == UnicodeBlocks.SMALL_FORM_VARIANTS) || (block == UnicodeBlocks.HALFWIDTH_AND_FULLWIDTH_FORMS) ||
                 ((block == UnicodeBlocks.CJK_UNIFIED_IDEOGRAPHS) && (ch <= 0x9faf)) ||
                 ((block == UnicodeBlocks.ALPHABETIC_PRESENTATION_FORMS) && (ch <= 0xfb1c)))
            return CharacterCategory.FAR_EAST;

        return CharacterCategory.OTHER;
    }

    /// <summary>
    /// Retrieves run attrs source.
    /// TODO: Alternative is to implement IRunAttrSource in this class. I'm not sure it's better.
    /// </summary>
    IRunAttrSource getParent() { return mParent; }

    private Run getParentRun() { return ms.as(mParent, Run.class); }

    @ReservedForInternalUse @Deprecated
    public Object /*IBorderAttrSource.*/getDirectBorderAttr(int key)
    {
        return mParent.getDirectRunAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IBorderAttrSource.*/fetchInheritedBorderAttr(int key)
    {
        return mParent.fetchInheritedRunAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IBorderAttrSource.*/setBorderAttr(int key, Object value)
    {
        mParent.setRunAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public SortedList<Integer, Integer> /*IBorderAttrSource.*/getPossibleBorderKeys()
    {
        Debug.fail("The property is used in BorderCollection. Not supported for Font.");
        return null;
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IShadingAttrSource.*/fetchInheritedShadingAttr(int key)
    {
        return mParent.fetchInheritedRunAttr(key);
    }

    //>>>>>>>> #region  IFillable implementation
    /// <summary>
    /// Changes type of the fill to Solid.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/solid()
    {
        DmlFill fill = getFill(false);
        if ((fill == null) || (fill.getDmlFillType() != DmlFillType.SOLID_FILL))
        {
            fill = createSolidFill();
            // Word VBA sets fully opaque black color to the new Solid fill.
            fill.setColorInternal(DrColor.BLACK);
        }
    }

    /// <summary>
    /// Gets a PresetTexture for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*PresetTexture*/int /*IFillable.*/getPresetTexture()
    {
        throw new IllegalStateException(ERROR_PRESET_TEXTURED);
    }

    /// <summary>
    /// Gets a PatternType for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*PatternType*/int /*IFillable.*/getPatternType()
    {
        throw new IllegalStateException(ERROR_PATTERNED);
    }

    /// <summary>
    /// Changes type of the fill to preset texture.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/presetTextured(/*PresetTexture*/int presetTexture)
    {
        throw new IllegalStateException(ERROR_PRESET_TEXTURED);
    }

    /// <summary>
    /// Changes type of the fill to a pattern.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/patterned(/*PatternType*/int patternType)
    {
        throw new IllegalStateException(ERROR_PATTERNED);
    }

    /// <summary>
    /// Changes type of the fill to gradient.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/twoColorGradient(/*GradientStyle*/int style, /*GradientVariant*/int variant)
    {
        Theme theme = (getTheme() == null) ? Theme.getBuiltInTheme() : getTheme();

        DmlFill curFill = getFill(false);
        DmlColor color1 = ((curFill != null) && (curFill.getDmlColorInternal() != null))
            ? curFill.getDmlColorInternal().deepClone()
            // Word VBA sets fully opaque black color to the new Gradient fill.
            : DmlColor.createFromDrColor(DrColor.BLACK);

        DmlColor color2 = ((curFill != null) && (curFill.getDmlColor2Internal() != null))
            ? curFill.getDmlColor2Internal().deepClone()
            // Word VBA sets fully opaque black color to the new Gradient fill.
            : DmlColor.createFromDrColor(DrColor.BLACK);

        DmlFill gradientFill = new DmlGradientFill(color1, color2, style, variant, theme);
        ((IFillable)this).setFill(gradientFill);
    }

    /// <summary>
    /// Changes type of the fill to gradient.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/oneColorGradient(/*GradientStyle*/int style, /*GradientVariant*/int variant, double degree)
    {
        Theme theme = (getTheme() == null) ? Theme.getBuiltInTheme() : getTheme();

        DmlFill curFill = getFill(false);
        DmlColor color1 = ((curFill != null) && (curFill.getDmlColorInternal() != null))
            ? curFill.getDmlColorInternal().deepClone()
            // Word VBA sets fully opaque black color to the new Gradient fill.
            : DmlColor.createFromDrColor(DrColor.BLACK);

        DmlColor color2 = color1.deepClone();

        // Check either we need luminance modifier.
        if (!MathUtil.areEqual(degree, 0.5))
        {
            DmlLuminanceModulation lumMod = new DmlLuminanceModulation();
            DmlLuminanceOffset lumOff = new DmlLuminanceOffset();
            if (MathUtil.isLessOrEqual(degree, 0.5))
            {
                lumMod.setValue(degree * 2.0);
                lumOff.setValue(0.0);
            }
            else
            {
                lumMod.setValue((1.0 - degree) * 2.0);
                lumOff.setValue(1.0 - lumMod.getValue());
            }

            msArrayList.add(color2.getColorModifiers(), lumMod);
            msArrayList.add(color2.getColorModifiers(), lumOff);
        }

        DmlFill gradientFill = new DmlGradientFill(color1, color2, style, variant, theme);
        ((IFillable)this).setFill(gradientFill);
    }

    /// <summary>
    /// Changes the fill type to single image.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setImage(byte[] imageBytes)
    {
        throw new IllegalStateException("SetImage cannot be applied to Font.");
    }

    /// <summary>
    /// Sets specified fill to this object.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFill(IFill fill)
    {
        DmlFill dmlFill = ms.as(fill, DmlFill.class);
        if ((dmlFill == null) ||
            ((dmlFill.getDmlFillType() != DmlFillType.SOLID_FILL) &&
             (dmlFill.getDmlFillType() != DmlFillType.GRADIENT_FILL) &&
             (dmlFill.getDmlFillType() != DmlFillType.NO_FILL)))
            throw new IllegalStateException("Invalid fill type for this object.");

        dmlFill.setParent(this);
        mParent.setRunAttr(FontAttr.EFFECT_FILL, dmlFill);
    }

    /// <summary>
    /// Returns a double value representing transparency of a specified color.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getTransparency(DmlColor color)
    {
        if (color.getAlpha() == null)
            return 0.0;

        return color.getAlpha().getValue();
    }

    /// <summary>
    /// Sets a specified value to a transparency of specified color .
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setTransparency(DmlColor color, double value)
    {
        color.updateAlpha(value);
    }

    //>>>>>>>> #region  The public properties of old Fill object for compatibility.

    /// <summary>
    /// Gets or sets a Color object that represents the foreground color for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Color /*IFillable.*/getFilledColor() { return ((IFillable)this).getFillableForeColor(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFilledColor(Color value) { ((IFillable)this).setFillableForeColor(value); }

    /// <summary>
    /// Gets or sets a boolean value indicating whether a Fill applied to an object is visible.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean /*IFillable.*/getOldOn() { return ((IFillable)this).getFillableVisible(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setOldOn(boolean value) { ((IFillable)this).setFillableVisible(value); }

    /// <summary>
    /// Gets or sets a double value between 0.0 (clear) and 1.0 (opaque) representing the degree
    /// of opacity of the specified fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getOldOpacity() { return 1.0 - ((IFillable)this).getFillableTransparency(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setOldOpacity(double value) { ((IFillable)this).setFillableTransparency(1.0 - value); }

    /// <summary>
    /// Gets the raw bytes of the fill texture or pattern.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>null</c>.</p>
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public byte[] /*IFillable.*/getFillableImageBytes() { return null; }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets or sets the alignment for tile texture fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*TextureAlignment*/int /*IFillable.*/getTextureAlignment() { return TextureAlignment.NONE; }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setTextureAlignment(/*TextureAlignment*/int value) { throw new IllegalStateException(ERROR_TEXTURE_ALIGNMENT); }

    /// <summary>
    /// Gets or sets the angle of the gradient fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getGradientAngle()
    {
        DmlFill fill = getFill(false);
        if (fill == null)
            return 0.0;

        return fill.getAngle();
    }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setGradientAngle(double value)
    {
        DmlFill fill = getFill(false);
        if (fill != null)
            fill.setAngle(value);
    }

    /// <summary>
    /// Gets the gradient variant for the fill as an integer value from 1 to 4 for most gradient fills.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*GradientVariant*/int /*IFillable.*/getGradientVariant()
    {
        DmlFill fill = getFill(false);
        if (fill == null)
            return GradientVariant.NONE;

        return fill.getGradientVariant();
    }

    /// <summary>
    /// Gets the gradient style for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*GradientStyle*/int /*IFillable.*/getGradientStyle()
    {
        DmlFill fill = getFill(false);
        if (fill == null)
            return GradientStyle.NONE;

        return fill.getGradientStyle();
    }

    /// <summary>
    /// Gets the gradient stops collection for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public GradientStopCollection /*IFillable.*/getGradientStops()
    {
        DmlGradientFill fill = ms.as(getFill(false), DmlGradientFill.class);
        if (fill == null)
            throw new IllegalStateException(INVALID_ACTION);

        return fill.getGradientStopCollection();
    }

    /// <summary>
    /// Gets or sets a Color object that represents the foreground color for the fill.
    /// </summary>
    /// <dev>
    /// Note, Word VBA always changes the type of fill to Solid when setting a new ForeColor
    /// but we do it only when current fill is NoFill for a while. Also Word VBA resets transparency
    /// to a fully opaque color, but we are not.
    /// </dev>
    @ReservedForInternalUse @Deprecated
    public Color /*IFillable.*/getFillableForeColor()
    {
        DmlFill fill = getFill(false);
        if (fill == null)
            return msColor.Empty;

        return fill.getColorInternal().toColorFixAlpha().toNativeColor();
    }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableForeColor(Color value)
    {
        DmlFill fill = getFill(true);
        // Word VBA allows to set up color for NoFill and changes the type of fill to Solid implicitly.
        if (fill.getDmlFillType() == DmlFillType.NO_FILL)
            fill = createSolidFill();

        fill.setColorInternal(DrColor.fromNativeColor(value));
    }

    /// <summary>
    /// Gets a Color object that represents the base foreground color without modifiers for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Color /*IFillable.*/getFillableBaseForeColor()
    {
        DmlFill fill = getFill(false);
        if (fill == null)
            return msColor.Empty;

        return fill.getColorInternalUnmodified().toColorFixAlpha().toNativeColor();
    }

    /// <summary>
    /// Gets or sets a Color object that represents the background color for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Color /*IFillable.*/getFillableBackColor()
    {
        DmlFill fill = getFill(false);
        if (fill == null)
            return msColor.Empty;

        return fill.getColor2Internal().toColorFixAlpha().toNativeColor();
    }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableBackColor(Color value)
    {
        DmlFill fill = getFill(true);
        // Word VBA does not throw, but also does not change the color in 'none' fill.
        if (fill.getDmlFillType() == DmlFillType.NO_FILL)
            return;

        // Seems the behavior for gradient in Word VBA depends on how we do it. If we set BackColor within
        // 'With' block, then the color is reset to black. But if we do it outside of 'With' block,
        // then the color is not changed. For a moment, there are no any reasons to follow such behavior.
        // So, let's set background color in gradient fill the same way, as we do it for shapes for uniformity.
        fill.setColor2Internal(DrColor.fromNativeColor(value));
    }

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the foreground color for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*ThemeColor*/int /*IFillable.*/getFillableForeThemeColor() { return getThemeColor(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableForeThemeColor(/*ThemeColor*/int value) { setThemeColor(value); }

    /// <summary>
    /// Gets or sets a ThemeColor object that represents the background color for the fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*ThemeColor*/int /*IFillable.*/getFillableBackThemeColor() { return ThemeColor.NONE; }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableBackThemeColor(/*ThemeColor*/int value) { throw new IllegalStateException("Cannot set BackThemeColor to this Fill."); }

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the foreground color.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getFillableForeTintAndShade() { return getTintAndShade(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableForeTintAndShade(double value) { setTintAndShade(value); }

    /// <summary>
    /// Gets or sets a double value that lightens or darkens the background color.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getFillableBackTintAndShade() { return 0.0d; }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableBackTintAndShade(double value) { throw new IllegalStateException("Cannot apply BackTintAndShade to this Fill."); }

    /// <summary>
    /// Gets or sets a boolean value indicating whether a Fill applied to an object is visible.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean /*IFillable.*/getFillableVisible()
    {
        DmlFill fill = getFill(false);
        if (fill == null)
            return true;

        return fill.getOn();
    }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableVisible(boolean value)
    {
        DmlFill fill = getFill(true);
        fill.setOn(value);
    }

    /// <summary>
    /// Gets or sets a double value between 0.0 (opaque) and 1.0 (clear) representing the degree
    /// of transparency of the specified fill.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public double /*IFillable.*/getFillableTransparency()
    {
        DmlFill fill = getFill(false);
        if (fill == null)
            return 0.0;

        // Word VBA returns 1.0 for NoFill.
        if (fill.getDmlFillType() == DmlFillType.NO_FILL)
            return 1.0;

        // DmlFill.Opacity in case of missing alpha returns the value (1.0) that is intended for the shape.
        // However, in text it should be inverted to (0.0), so check this special case here directly.
        if ((fill.getDmlColorInternal() == null) || (fill.getDmlColorInternal().getAlpha() == null))
            return 0.0;

        return fill.getOpacity();
    }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setFillableTransparency(double value)
    {
        DmlFill fill = getFill(true);

        // Word VBA allows to set up opacity (transparency) for NoFill and
        // changes the type of fill to Solid implicitly.
        if (fill.getDmlFillType() == DmlFillType.NO_FILL)
            fill = createSolidFill();

        fill.setOpacity(value);
    }

    /// <summary>
    /// Gets or sets a boolean value indicating whether the fill rotates with the specified object.
    /// </summary>
    /// <dev>
    /// This property is applicable to shape objects only.
    /// When accessing this property in VBA for text an exception is thrown.
    /// </dev>
    @ReservedForInternalUse @Deprecated
    public boolean /*IFillable.*/getRotateWithObject() { throw new IllegalStateException(DmlFill.MSG_INVALID_ACTION); }
    @ReservedForInternalUse @Deprecated
    public void /*IFillable.*/setRotateWithObject(boolean value) { throw new IllegalStateException(DmlFill.MSG_INVALID_ACTION); }

    /// <summary>
    /// Gets fill type.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*FillTypeCore*/int /*IFillable.*/getFillType()
    {
        DmlFill fill = getFill(false);
        if (fill == null)
            return FillTypeCore.SOLID;

        return fill.getFillType();
    }

    /// <summary>
    /// Gets IThemeProvider object.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public IThemeProvider /*IFillable.*/getFillableThemeProvider() { return getTheme(); }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Creates a new Solid fill in this <see cref="Font"/> object.
    /// </summary>
    private DmlFill createSolidFill()
    {
        DmlFill solidFill = new DmlSolidFill();
        mParent.setRunAttr(FontAttr.EFFECT_FILL, solidFill);

        solidFill.setParent(this);

        return solidFill;
    }

    /// <summary>
    /// Gets fill of this <see cref="Font"/> object.
    /// </summary>
    /// <param name="isAllowAutoCreate"> Indicates whether it is allowed to create
    /// default Solid fill when there is no fill set. </param>
    private DmlFill getFill(boolean isAllowAutoCreate)
    {
        DmlFill value = (DmlFill)mParent.getDirectRunAttr(FontAttr.EFFECT_FILL);
        if (value == null)
            return (isAllowAutoCreate) ? createSolidFill() : null;

        value.setParent(this);
        return value;
    }

    private StyleCollection getStyles()
    {
        return mDoc != null
            ? mDoc.getStyles()
            : null;
    }

    private Theme getTheme()
    {
        return mDoc != null
            ? mDoc.getThemeInternal()
            : null;
    }

    private DocumentFontProvider getFontProvider()
    {
        return mDoc != null
            ? mDoc.getFontProvider()
            : null;
    }


    private /*final*/ IRunAttrSource mParent;

    private /*final*/ DocumentBase mDoc;

    private Fill mFill;

    private static final float SMALL_CAPS_SIZE_FACTOR = 0.80f;
    private static final float SUBSCRIPT_SIZE_FACTOR = 0.62f;

    private static final String ERROR_PRESET_TEXTURED = "Preset texture cannot be applied to Font.";
    private static final String ERROR_TEXTURE_ALIGNMENT = "TextureAlignment cannot be applied to Font.";
    private static final String ERROR_PATTERNED = "Pattern cannot be applied to Font.";
    private static final String INVALID_ACTION = "Object doesn't support this action.";
}

