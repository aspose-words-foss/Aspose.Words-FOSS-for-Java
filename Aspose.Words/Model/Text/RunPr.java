// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.StringUtil;


/// <summary>
/// Provides typed access to run attributes.
/// </summary>
class RunPr extends WordAttrCollection implements IRunAttrSource, IMathRunPr
{
    /*override*/ void acceptFormatRevision()
    {
        acceptRunSpecificFormatRevision(false);
    }

    /// <summary>
    /// If isSymbolicRun is true accepts format revision for RunPr that is originating from w:sym (or alike) element and contains non-Unicode symbol,
    /// otherwise When processing run properties we preserve only insert and delete revisions.
    /// </summary>
    /// <remarks>
    /// WORDSNET-7098
    /// </remarks>
    void acceptRunSpecificFormatRevision(boolean isSymbolicRun)
    {
        acceptFormatRevisionCore(FontAttr.ISTD, (isSymbolicRun) ? G_REVISION_PRESERVED_SYMBOL_ATTRIBUTES : G_REVISION_PRESERVED_RUN_ATTRIBUTES);
    }

    Object fetchAttr(int key, /*RunPrExpandFlags*/int flags)
    {
        return getAttr(key, toValueVersion(flags), true);
    }

    Object getDirectAttr(int key, /*RunPrExpandFlags*/int flags)
    {
        return getAttr(key, toValueVersion(flags), false);
    }

    //>>>>>>>> #region  IRunAttrSource

    public Object /*IRunAttrSource.*/getDirectRunAttr(int key)
    {
        return getDirectAttr(key);
    }

    public Object /*IRunAttrSource.*/getDirectRunAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return getDirectAttr(key, revisionsView);
    }

    public Object /*IRunAttrSource.*/fetchInheritedRunAttr(int key)
    {
        return fetchInheritedAttr(key);
    }

    public void /*IRunAttrSource.*/setRunAttr(int key, Object value)
    {
        setAttr(key, value);
    }

    public void /*IRunAttrSource.*/removeRunAttr(int key)
    {
        remove(key);
    }

    public void /*IRunAttrSource.*/clearRunAttrs()
    {
        clear();
    }

    //<<<<<<<< #endregion 

    static Object fetchDefaultAttr(int key)
    {
        return G_DEFAULTS.fetchAttr(key);
    }

    static Object getDefaultAttr(int key)
    {
        return G_DEFAULTS.getDirectAttr(key);
    }

    /// <summary>
    /// Fix ThemeColor inheritance problem.
    /// </summary>
    /// <remarks>
    /// This is just temporary workaround and relates to WORDSNET-3312
    /// Color, ThemeColor, ThemeShade, ThemeTint should be combined into one complex attribute.
    /// </remarks>
    void themeColorInheritanceHack(AttrCollection dstAttrs)
    {
        boolean hasThemeColor = contains(FontAttr.THEME_COLOR);

        // WORDSNET-14587 When we expand with global defaults and there is no ThemeColor
        // in styles hierarchy and in direct attributes, then we will get this global
        // ThemeColor in dstAttrs. So, remove global default ThemeColor from destination
        // collection to eliminate possible difference in comparisons.
        if ((!hasThemeColor && contains(FontAttr.COLOR)) || dstAttrs.isDefaultValue(FontAttr.THEME_COLOR))
            dstAttrs.remove(FontAttr.THEME_COLOR);

        // "ThemeShade", "ThemeTint" have meaningful when "ThemeColor" is specified.
        if (!hasThemeColor)
            return;

        // WORDSNET-14174 Replace value of the "ThemeShape" attribute with global default value
        // to fix inheritance logic. “ThemeColor”, “ThemeShade”, “ThemeTint” attributes MSW uses like
        // a single complex attribute.
        if (dstAttrs.contains(FontAttr.THEME_SHADE) && !contains(FontAttr.THEME_SHADE))
            dstAttrs.setAttr(FontAttr.THEME_SHADE, "");

        if (dstAttrs.contains(FontAttr.THEME_TINT) && !contains(FontAttr.THEME_TINT))
            dstAttrs.setAttr(FontAttr.THEME_TINT, "");
    }

    private static /*ValueVersion*/int toValueVersion(/*RunPrExpandFlags*/int flags)
    {
        if ((flags & RunPrExpandFlags.AFTER_CHANGES) != 0)
            return ValueVersion.AFTER_CHANGES;
        if ((flags & RunPrExpandFlags.REVISED | flags & RunPrExpandFlags.DOCUMENT_DEFAULTS) != 0)
            return ValueVersion.FINAL;

        return ValueVersion.ORIGINAL;
    }

    /// <summary>
    /// Returns the static collection of default attributes.
    /// </summary>
    protected /*override*/ AttrCollection getDefaults()
    {
        return G_DEFAULTS;
    }

    public boolean isDml() { return false; }

    //JAVA-deleted Namesake Explicit Interface Implementation property:
    //int IMathRunPr.Count

    DrColor getHighlightColor() { return (DrColor)fetchAttr(FontAttr.HIGHLIGHT_COLOR); }
    void setHighlightColor(DrColor value) { setAttr(FontAttr.HIGHLIGHT_COLOR, value); }

    int getRsidRPr() { return (/*int*/Integer)fetchAttr(FontAttr.RSID_R_PR); }
    void setRsidRPr(int value) { setAttr(FontAttr.RSID_R_PR, value); }

    int getRsidR() { return (/*int*/Integer)fetchAttr(FontAttr.RSID_R); }
    void setRsidR(int value) { setAttr(FontAttr.RSID_R, value); }

    /*LineBreakClear*/int getLineBreakClear() { return (/*LineBreakClear*/Integer)fetchAttr(FontAttr.LINE_BREAK_CLEAR); }
    void setLineBreakClear(/*LineBreakClear*/int value) { setAttr(FontAttr.LINE_BREAK_CLEAR, value); }

    /// <summary>
    /// Style index.
    /// </summary>
    int getIstd() { return (/*int*/Integer)fetchAttr(FontAttr.ISTD); }
    void setIstd(int value) { setAttr(FontAttr.ISTD, value); }

    AttrBoolEx getBold() { return (AttrBoolEx)fetchAttr(FontAttr.BOLD); }
    void setBold(AttrBoolEx value) { setAttr(FontAttr.BOLD, value); }

    AttrBoolEx getItalic() { return (AttrBoolEx)fetchAttr(FontAttr.ITALIC); }
    void setItalic(AttrBoolEx value) { setAttr(FontAttr.ITALIC, value); }

    AttrBoolEx getStrikeThrough() { return (AttrBoolEx)fetchAttr(FontAttr.STRIKE_THROUGH); }
    void setStrikeThrough(AttrBoolEx value) { setAttr(FontAttr.STRIKE_THROUGH, value); }

    AttrBoolEx getOutline() { return (AttrBoolEx)fetchAttr(FontAttr.OUTLINE); }
    void setOutline(AttrBoolEx value) { setAttr(FontAttr.OUTLINE, value); }

    AttrBoolEx getShadow() { return (AttrBoolEx)fetchAttr(FontAttr.SHADOW); }
    void setShadow(AttrBoolEx value) { setAttr(FontAttr.SHADOW, value); }

    AttrBoolEx getSmallCaps() { return (AttrBoolEx)fetchAttr(FontAttr.SMALL_CAPS); }
    void setSmallCaps(AttrBoolEx value) { setAttr(FontAttr.SMALL_CAPS, value); }

    AttrBoolEx getAllCaps() { return (AttrBoolEx)fetchAttr(FontAttr.ALL_CAPS); }
    void setAllCaps(AttrBoolEx value) { setAttr(FontAttr.ALL_CAPS, value); }

    AttrBoolEx getHidden() { return (AttrBoolEx)fetchAttr(FontAttr.HIDDEN); }
    void setHidden(AttrBoolEx value) { setAttr(FontAttr.HIDDEN, value); }

    AttrBoolEx getWebHidden() { return (AttrBoolEx)fetchAttr(FontAttr.WEB_HIDDEN); }
    void setWebHidden(AttrBoolEx value) { setAttr(FontAttr.WEB_HIDDEN, value); }

    /*Underline*/int getUnderline() { return (/*Underline*/Integer)fetchAttr(FontAttr.UNDERLINE); }
    void setUnderline(/*Underline*/int value) { setAttr(FontAttr.UNDERLINE, value); }

    int getSpacing() { return (/*int*/Integer)fetchAttr(FontAttr.SPACING); }
    void setSpacing(int value) { setAttr(FontAttr.SPACING, value); }

    DrColor getColor() { return (DrColor)fetchAttr(FontAttr.COLOR); }
    void setColor(DrColor value) { setAttr(FontAttr.COLOR, value); }

    AttrBoolEx getEmboss() { return (AttrBoolEx)fetchAttr(FontAttr.EMBOSS); }
    void setEmboss(AttrBoolEx value) { setAttr(FontAttr.EMBOSS, value); }

    AttrBoolEx getEngrave() { return (AttrBoolEx)fetchAttr(FontAttr.ENGRAVE); }
    void setEngrave(AttrBoolEx value) { setAttr(FontAttr.ENGRAVE, value); }

    int getSize() { return (/*int*/Integer)fetchAttr(FontAttr.SIZE); }
    void setSize(int value) { setAttr(FontAttr.SIZE, value); }

    int getPosition() { return (/*int*/Integer)fetchAttr(FontAttr.POSITION); }
    void setPosition(int value) { setAttr(FontAttr.POSITION, value); }

    /*RunVerticalAlignment*/int getVerticalAlignment() { return (/*RunVerticalAlignment*/Integer)fetchAttr(FontAttr.VERTICAL_ALIGNMENT); }
    void setVerticalAlignment(/*RunVerticalAlignment*/int value) { setAttr(FontAttr.VERTICAL_ALIGNMENT, value); }

    int getKerning() { return (/*int*/Integer)fetchAttr(FontAttr.KERNING); }
    void setKerning(int value) { setAttr(FontAttr.KERNING, value); }

    String getName() { return getNameAscii(); }
    void setName(String value)
    {
        // Have to be on separate lines for autoporting.
        setNameAscii(value);
        setNameBi(value);
        setNameFarEast(value);
        setNameOther(value);
    }

    /// <summary>
    /// Font name for ASCII text.
    /// Note getter throws if font is defined by theme font,
    /// so use corresponding <see cref="ComplexNameAscii"/> property to get more control.
    /// </summary>
    String getNameAscii() { return ((ComplexFontName)fetchAttr(FontAttr.NAME_ASCII)).getName(); }
    void setNameAscii(String value) { setAttr(FontAttr.NAME_ASCII, ComplexFontName.fromName(value)); }

    /// <summary>
    /// Font name for Far East Asian text.
    /// Note getter throws if font is defined by theme font,
    /// so use corresponding <see cref="ComplexNameFarEast"/> property to get more control.
    /// </summary>
    String getNameFarEast() { return ((ComplexFontName)fetchAttr(FontAttr.NAME_FAR_EAST)).getName(); }
    void setNameFarEast(String value) { setAttr(FontAttr.NAME_FAR_EAST, ComplexFontName.fromName(value)); }

    /// <summary>
    /// Font name for other text.
    /// Note getter throws if font is defined by theme font,
    /// so use corresponding <see cref="ComplexNameOther"/> property to get more control.
    /// </summary>
    String getNameOther() { return ((ComplexFontName)fetchAttr(FontAttr.NAME_OTHER)).getName(); }
    void setNameOther(String value) { setAttr(FontAttr.NAME_OTHER, ComplexFontName.fromName(value)); }

    /// <summary>
    /// Font name for bi-directional text.
    /// Note getter throws if font is defined by theme font,
    /// so use corresponding <see cref="ComplexNameBi"/> property to get more control.
    /// </summary>
    String getNameBi() { return ((ComplexFontName)fetchAttr(FontAttr.NAME_BI)).getName(); }
    void setNameBi(String value) { setAttr(FontAttr.NAME_BI, ComplexFontName.fromName(value)); }

    /// <summary>
    /// Theme font for ASCII text.
    /// Note getter throws if font is defined not by theme font,
    /// so use corresponding <see cref="ComplexNameAscii"/> property to get more control.
    /// </summary>
    /*ThemeFontCore*/int getThemeAscii() { return ((ComplexFontName)fetchAttr(FontAttr.NAME_ASCII)).getThemeFontCore(); }
    void setThemeAscii(/*ThemeFontCore*/int value) { setAttr(FontAttr.NAME_ASCII, ComplexFontName.fromTheme(value)); }

    /// <summary>
    /// Theme font for Far East Asian text.
    /// Note getter throws if font is defined not by theme font,
    /// so use corresponding <see cref="ComplexNameFarEast"/> property to get more control.
    /// </summary>
    /*ThemeFontCore*/int getThemeFarEast() { return ((ComplexFontName)fetchAttr(FontAttr.NAME_FAR_EAST)).getThemeFontCore(); }
    void setThemeFarEast(/*ThemeFontCore*/int value) { setAttr(FontAttr.NAME_FAR_EAST, ComplexFontName.fromTheme(value)); }

    /// <summary>
    /// Theme font for other text.
    /// Note getter throws if font is defined not by theme font,
    /// so use corresponding <see cref="ComplexNameOther"/> property to get more control.
    /// </summary>
    /*ThemeFontCore*/int getThemeOther() { return ((ComplexFontName)fetchAttr(FontAttr.NAME_OTHER)).getThemeFontCore(); }
    void setThemeOther(/*ThemeFontCore*/int value) { setAttr(FontAttr.NAME_OTHER, ComplexFontName.fromTheme(value)); }

    /// <summary>
    /// Theme font for bi-directional text.
    /// Note getter throws if font is defined not by theme font,
    /// so use corresponding <see cref="ComplexNameBi"/> property to get more control.
    /// </summary>
    /*ThemeFontCore*/int getThemeBi() { return ((ComplexFontName)fetchAttr(FontAttr.NAME_BI)).getThemeFontCore(); }
    void setThemeBi(/*ThemeFontCore*/int value) { setAttr(FontAttr.NAME_BI, ComplexFontName.fromTheme(value)); }

    ComplexFontName getComplexNameAscii() { return (ComplexFontName)fetchAttr(FontAttr.NAME_ASCII); }
    void setComplexNameAscii(ComplexFontName value) { setAttr(FontAttr.NAME_ASCII, value); }

    ComplexFontName getComplexNameFarEast() { return (ComplexFontName)fetchAttr(FontAttr.NAME_FAR_EAST); }
    void setComplexNameFarEast(ComplexFontName value) { setAttr(FontAttr.NAME_FAR_EAST, value); }

    ComplexFontName getComplexNameOther() { return (ComplexFontName)fetchAttr(FontAttr.NAME_OTHER); }
    void setComplexNameOther(ComplexFontName value) { setAttr(FontAttr.NAME_OTHER, value); }

    ComplexFontName getComplexNameBi() { return (ComplexFontName)fetchAttr(FontAttr.NAME_BI); }
    void setComplexNameBi(ComplexFontName value) { setAttr(FontAttr.NAME_BI, value); }

    AttrBoolEx getBoldBi() { return (AttrBoolEx)fetchAttr(FontAttr.BOLD_BI); }
    void setBoldBi(AttrBoolEx value) { setAttr(FontAttr.BOLD_BI, value); }

    AttrBoolEx getItalicBi() { return (AttrBoolEx)fetchAttr(FontAttr.ITALIC_BI); }
    void setItalicBi(AttrBoolEx value) { setAttr(FontAttr.ITALIC_BI, value); }

    AttrBoolEx getBidi() { return (AttrBoolEx)fetchAttr(FontAttr.BIDI); }
    void setBidi(AttrBoolEx value) { setAttr(FontAttr.BIDI, value); }

    AttrBoolEx getComplexScript() { return (AttrBoolEx)fetchAttr(FontAttr.COMPLEX_SCRIPT); }
    void setComplexScript(AttrBoolEx value) { setAttr(FontAttr.COMPLEX_SCRIPT, value); }

    int getScaling() { return (/*int*/Integer)fetchAttr(FontAttr.SCALING); }
    void setScaling(int value) { setAttr(FontAttr.SCALING, value); }

    AttrBoolEx getDoubleStrikeThrough() { return (AttrBoolEx)fetchAttr(FontAttr.DOUBLE_STRIKE_THROUGH); }
    void setDoubleStrikeThrough(AttrBoolEx value) { setAttr(FontAttr.DOUBLE_STRIKE_THROUGH, value); }

    /*TextEffect*/int getTextEffect() { return (/*TextEffect*/Integer)fetchAttr(FontAttr.TEXT_EFFECT); }
    void setTextEffect(/*TextEffect*/int value) { setAttr(FontAttr.TEXT_EFFECT, value); }

    AttrBoolEx getSnapToGrid() { return (AttrBoolEx)fetchAttr(FontAttr.SNAP_TO_GRID); }
    void setSnapToGrid(AttrBoolEx value) { setAttr(FontAttr.SNAP_TO_GRID, value); }

    int getLocaleIdBi() { return (/*int*/Integer)fetchAttr(FontAttr.LOCALE_ID_BI); }
    void setLocaleIdBi(int value) { setAttr(FontAttr.LOCALE_ID_BI, value); }

    int getSizeBi() { return (/*int*/Integer)fetchAttr(FontAttr.SIZE_BI); }
    void setSizeBi(int value) { setAttr(FontAttr.SIZE_BI, value); }

    Border getBorder() { return (Border)fetchAttr(FontAttr.BORDER); }
    void setBorder(Border value) { setAttr(FontAttr.BORDER, value); }

    Shading getShading() { return (Shading)fetchAttr(FontAttr.SHADING); }
    void setShading(Shading value) { setAttr(FontAttr.SHADING, value); }

    int getLocaleId() { return (/*int*/Integer)fetchAttr(FontAttr.LOCALE_ID); }
    void setLocaleId(int value) { setAttr(FontAttr.LOCALE_ID, value); }

    int getLocaleIdFarEast() { return (/*int*/Integer)fetchAttr(FontAttr.LOCALE_ID_FAR_EAST); }
    void setLocaleIdFarEast(int value) { setAttr(FontAttr.LOCALE_ID_FAR_EAST, value); }

    /*CharacterCategory*/int getCharacterCategoryHint() { return (/*CharacterCategory*/Integer)fetchAttr(FontAttr.CHARACTER_CATEGORY_HINT); }
    void setCharacterCategoryHint(/*CharacterCategory*/int value) { setAttr(FontAttr.CHARACTER_CATEGORY_HINT, value); }

    AttrBoolEx getNoProofing() { return (AttrBoolEx)fetchAttr(FontAttr.NO_PROOFING); }
    void setNoProofing(AttrBoolEx value) { setAttr(FontAttr.NO_PROOFING, value); }

    DrColor getUnderlineColor() { return (DrColor)fetchAttr(FontAttr.UNDERLINE_COLOR); }
    void setUnderlineColor(DrColor value) { setAttr(FontAttr.UNDERLINE_COLOR, value); }

    /*HyphenRule*/int getHyphenRule() { return (/*HyphenRule*/Integer)fetchAttr(FontAttr.HYPHEN_RULE); }
    void setHyphenRule(/*HyphenRule*/int value) { setAttr(FontAttr.HYPHEN_RULE, value); }

    int getHyphenChar() { return (/*int*/Integer)fetchAttr(FontAttr.HYPHEN_CHAR); }
    void setHyphenChar(int value) { setAttr(FontAttr.HYPHEN_CHAR, value); }

    /// <summary>
    /// Specific for <see cref="OfficeMath"/> instances, don't set this property for regular <see cref="Run"/> instances.
    /// Specifies that the characters  in the run are literal; that is, they are to be interpreted literally and
    /// not be built up based on any implied mathematical meaning.
    /// </summary>
    boolean getMathIsLiteral() { return (/*boolean*/Boolean)fetchAttr(FontAttr.MATH_IS_LITERAL); }
    void setMathIsLiteral(boolean value) { setAttr(FontAttr.MATH_IS_LITERAL, value, value); }

    /// <summary>
    /// Specific for <see cref="OfficeMath"/> instances, don't set this property for regular <see cref="Run"/> instances.
    /// Specifies that the run is normal text,  i.e., math italics and math spacing are not applied.
    /// </summary>
    /// <remarks>
    /// In a normal text run, no characters will trigger reformatting of a linear expression into a two-dimensional expression.
    /// </remarks>
    boolean getMathIsNormalText() { return (/*boolean*/Boolean)fetchAttr(FontAttr.MATH_IS_NORMAL_TEXT); }
    void setMathIsNormalText(boolean value) { setAttr(FontAttr.MATH_IS_NORMAL_TEXT, value, value); }

    /// <summary>
    /// Specific for <see cref="OfficeMath"/> instances, don't set this property for regular <see cref="Run"/> instances.
    /// Describes the script applied to the characters in the run of mathematical text.
    /// </summary>
    /// <remarks>
    /// The XML includes the Unicode value of the character (between U+0000 and U+007F),  along with the script of the character.
    /// The application maps the value and script type to the appropriate Math Alphanumeric Unicode.
    /// </remarks>
    /*MathScript*/int getMathScript() { return (/*MathScript*/Integer)fetchAttr(FontAttr.MATH_SCRIPT); }
    void setMathScript(/*MathScript*/int value) { setAttr(FontAttr.MATH_SCRIPT, value, value != MathScript.DEFAULT); }

    /// <summary>
    /// Specific for <see cref="OfficeMath"/> instances, don't set this property for regular <see cref="Run"/> instances.
    /// Describes the script applied to the characters in the run.
    /// </summary>
    /// <remarks>
    /// The XML includes the Unicode value of the character along with the style of the character.
    /// The application maps the value and style to the appropriate Math Alphanumeric Unicode range.
    /// </remarks>
    /*MathStyle*/int getMathStyle() { return (/*MathStyle*/Integer)fetchAttr(FontAttr.MATH_STYLE); }
    void setMathStyle(/*MathStyle*/int value) { setAttr(FontAttr.MATH_STYLE, value, value != MathStyle.DEFAULT); }

    /// <summary>
    /// Specific for <see cref="OfficeMath"/> instances, don't set this property for regular <see cref="Run"/> instances.
    /// Specifies whether there is a manual line break at the start of a math run.
    /// Can be null, meaning that no line break is associated with this run.
    /// </summary>
    /// <remarks>
    /// Word ignores LineBreak if it is specified for a math run which does not begin with an operator.
    /// </remarks>
    MathLineBreak getMathLineBreak() { return (MathLineBreak)getDirectAttr(FontAttr.MATH_LINE_BREAK); }
    void setMathLineBreak(MathLineBreak value) { setAttr(FontAttr.MATH_LINE_BREAK, value, value != null); }

    /// <summary>
    /// Specific for <see cref="OfficeMath"/> instances, don't set this property for regular <see cref="Run"/> instances.
    /// Specifies the alignment property on the box object. It is utilized only when the box is designated as
    /// an operator emulator. When true, this operator emulator serves as an alignment point; that is, designated
    /// alignment points in other equations can be aligned with it.
    /// </summary>
    boolean getMathIsAlignmentPoint() { return (/*boolean*/Boolean)fetchAttr(FontAttr.MATH_IS_ALIGNMENT_POINT); }
    void setMathIsAlignmentPoint(boolean value) { setAttr(FontAttr.MATH_IS_ALIGNMENT_POINT, value, value); }

    /// <summary>
    /// Returns true if this RunPr instance has properties specific for a <see cref="OfficeMath"/>.
    /// </summary>
    boolean isMathRunPr()
    {
        for (int i = 0; i < G_MATH_KEYS_TO_CHECK.length; i++)
        {
            if (getDirectAttr(G_MATH_KEYS_TO_CHECK[i]) != null)
                return true;
        }

        return false;
    }

    /// <summary>
    /// Mysterious property that MS Words sometimes inserts and sometimes doesn't.
    /// Although it has very defined meaning in iso 29500 (see. 17.3.2.22 oMath (Office Open XML Math))
    /// We don't insert it anywhere except if required by roundtripping.
    /// </summary>
    boolean isOMath() { return (/*boolean*/Boolean)fetchAttr(FontAttr.MATH_IS_O_MATH); }
    void isOMath(boolean value) { setAttr(FontAttr.MATH_IS_O_MATH, value, value); }

    /// <summary>
    /// Gets the object that represents info about field numbering revision.
    /// </summary>
    FieldNumberRevision getNumberRevision() { return (FieldNumberRevision)getDirectAttr(RevisionAttr.NUMBER_REVISION); }
    void setNumberRevision(FieldNumberRevision value) { setAttr(RevisionAttr.NUMBER_REVISION, value); }

    boolean hasNumberRevision() { return getNumberRevision() != null; }

    /// <summary>
    /// Specifies <see cref="EmphasisMark" /> type for text.
    /// </summary>
    /*EmphasisMark*/int getEmphasisMark() { return (/*EmphasisMark*/Integer)fetchAttr(FontAttr.EMPHASIS_MARK); }
    void setEmphasisMark(/*EmphasisMark*/int value) { setAttr(FontAttr.EMPHASIS_MARK, value); }

    /// <summary>
    /// Specifies <see cref="FarEastLayout" /> option for text.
    /// </summary>
    /// <remarks>Can be null.</remarks>
    FarEastLayout getFarEastLayout() { return (FarEastLayout)getDirectAttr(FontAttr.FAR_EAST_LAYOUT); }
    void setFarEastLayout(FarEastLayout value) { setAttr(FontAttr.FAR_EAST_LAYOUT, value); }

    /// <summary>
    /// Container to store FallBack of AlternateContent and Requires attribute when Choice matched and can be stored in the model.
    /// </summary>
    AlternateContent getAlternateContent() { return (AlternateContent)getDirectAttr(FontAttr.ALTERNATE_CONTENT); }
    void setAlternateContent(AlternateContent value) { setAttr(FontAttr.ALTERNATE_CONTENT, value); }

    /// <summary>
    /// Returns true if any theme font is specified.
    /// </summary>
    boolean hasThemeFont()
    {
        return getComplexNameAscii().isThemeFont() || getComplexNameBi().isThemeFont() ||
            getComplexNameFarEast().isThemeFont() || getComplexNameOther().isThemeFont();
    }

    /// <summary>
    /// Returns true if all ComplexFontName are represented as ThemeFont.
    /// </summary>
    boolean getAreAllComplexFontNamesThemeFont()
    {
        return getComplexNameAscii().isThemeFont() && getComplexNameBi().isThemeFont() &&
               getComplexNameFarEast().isThemeFont() && getComplexNameOther().isThemeFont();
    }

    /// <summary>
    /// Sets font names for symbols.
    /// </summary>
    /// <param name="symbolFontName">Name of symbol's font</param>
    void setSymbolFonts(String symbolFontName)
    {
        if (StringUtil.hasChars(symbolFontName))
        {
            // RK By looking at MS gold files, it seems only these two attributes must be set when reading symbol font.
            setNameOther(symbolFontName);
            setNameAscii(symbolFontName);

            // WORDSNET-28407 Set symbolic font to format revision as well.
            if (getFormatRevision() != null)
            {
                RunPr revPr = (RunPr)getFormatRevision().getRevPr();
                revPr.setNameOther(symbolFontName);
                revPr.setNameAscii(symbolFontName);
            }
        }
    }

    /// <summary>
    /// Sets complex font names for symbols.
    /// </summary>
    /// <param name="symbolFontName">Name of symbol's complex font</param>
    void setSymbolFonts(ComplexFontName symbolFontName)
    {
        if (symbolFontName.isThemeFont())
        {
            setComplexNameAscii(symbolFontName);
            setComplexNameOther(symbolFontName);
        }
        else
        {
            setSymbolFonts(symbolFontName.getName());
        }
    }

    /// <summary>
    /// Returns property collection depending on passed flags.
    /// </summary>
    /// <remarks>
    /// AM. This code almost duplicates ParaPr.GetSourceParaPr method
    /// but I don't see the good way to move it into base AttrCollection class.
    /// </remarks>
    RunPr getSourceRunPr(/*RunPrExpandFlags*/int flags)
    {
        RunPr sourcePr = this;

        if (getFormatRevision() != null)
        {
            if ((flags & RunPrExpandFlags.REVISED) != 0)
            {
                sourcePr = AttrCollectionExtension.deepClone(this);
                sourcePr.acceptFormatRevision();
            }
            else if ((flags & RunPrExpandFlags.AFTER_CHANGES) != 0)
            {
                sourcePr = (RunPr)getFormatRevision().getRevPr();
            }
        }

        return sourcePr;
    }

    
    protected /*override*/ int getDefaultCapacity()
    {
        // WORDSNET-21593 Reduce default capacity to optimize memory consumption.
        return 4;
    }

    /// <summary>
    /// Performs comparison of runPrs according to BiDi formatting.
    /// Returns true, if runPrs are the same, false - otherwise.
    /// </summary>
    static boolean isSameFormatting(RunPr runPrA, RunPr runPrB)
    {
        // WORDSNET-14279 Ignore keys depending on BiDi formatting.
        int[] keysToIgnore = runPrB.getBidi().toBool()
            ? Run.KEYS_TO_IGNORE_IN_COMPARISON_ON_JOIN_RTL_RUNS
            : Run.KEYS_TO_IGNORE_IN_COMPARISON_ON_JOIN_LTR_RUNS;

        return isSameFormatting(runPrA, runPrB, keysToIgnore);
    }

    static boolean isSameFormatting(RunPr runPrA, RunPr runPrB, int[] keysToIgnore)
    {
        return runPrB.equals(runPrA, keysToIgnore);
    }

    /// <summary>
    /// At the moment I don't define default values for complex attributes that cannot be inherited such
    /// as text columns or formatting revisions. There is no point in defining them since they cannot be inherited.
    ///
    /// Also I don't define default values for some undocumented attributes such as timestamp etc.
    /// This is because such attributes should not be inherited (we don't know what they are).
    ///
    /// So basically, if the attribute is not to be inherited and therefore not to be resolved up the
    /// inheritance chain to the default attributes - there is no point in defining it here.
    /// It can be defined and it will not hurt, but I think it should not be.
    /// </summary>
    private static RunPr initDefaults()
    {
        RunPr defaults = new RunPr();

        defaults.add(FontAttr.NAME_ASCII, ComplexFontName.fromName(TIMES_NEW_ROMAN));
        defaults.add(FontAttr.NAME_BI, ComplexFontName.fromName(TIMES_NEW_ROMAN));
        defaults.add(FontAttr.NAME_FAR_EAST, ComplexFontName.fromName(TIMES_NEW_ROMAN));
        defaults.add(FontAttr.NAME_OTHER, ComplexFontName.fromName(TIMES_NEW_ROMAN));

        defaults.add(FontAttr.LINE_BREAK_CLEAR, LineBreakClear.NONE);

        defaults.add(FontAttr.SIZE, 20);                   // half points
        defaults.add(FontAttr.SIZE_BI, 20);                 // half points
        defaults.add(FontAttr.BOLD, AttrBoolEx.FALSE);
        defaults.add(FontAttr.BOLD_BI, AttrBoolEx.FALSE);
        defaults.add(FontAttr.ITALIC, AttrBoolEx.FALSE);
        defaults.add(FontAttr.ITALIC_BI, AttrBoolEx.FALSE);

        defaults.add(FontAttr.COLOR, DrColor.EMPTY);
        defaults.add(FontAttr.ISTD, StyleIndex.DEFAULT_PARAGRAPH_FONT);

        // These are complex attributes. Be careful not to modify the actual values in them.
        defaults.add(FontAttr.BORDER, new Border());
        defaults.add(FontAttr.SHADING, new Shading());

        defaults.add(FontAttr.STRIKE_THROUGH, AttrBoolEx.FALSE);
        defaults.add(FontAttr.DOUBLE_STRIKE_THROUGH, AttrBoolEx.FALSE);
        defaults.add(FontAttr.SHADOW, AttrBoolEx.FALSE);
        defaults.add(FontAttr.OUTLINE, AttrBoolEx.FALSE);
        defaults.add(FontAttr.EMBOSS, AttrBoolEx.FALSE);
        defaults.add(FontAttr.ENGRAVE, AttrBoolEx.FALSE);

        defaults.add(FontAttr.VERTICAL_ALIGNMENT, RunVerticalAlignment.BASELINE);
        defaults.add(FontAttr.SMALL_CAPS, AttrBoolEx.FALSE);
        defaults.add(FontAttr.ALL_CAPS, AttrBoolEx.FALSE);
        defaults.add(FontAttr.HIDDEN, AttrBoolEx.FALSE);
        defaults.add(FontAttr.WEB_HIDDEN, AttrBoolEx.FALSE);
        defaults.add(FontAttr.SPECIAL_HIDDEN, AttrBoolEx.FALSE);
        defaults.add(FontAttr.UNDERLINE, Underline.NONE);
        defaults.add(FontAttr.UNDERLINE_COLOR, DrColor.EMPTY);

        defaults.add(FontAttr.SCALING, 100);
        defaults.add(FontAttr.SPACING, 0);
        defaults.add(FontAttr.POSITION, 0);
        defaults.add(FontAttr.KERNING, 0);

        defaults.add(FontAttr.HIGHLIGHT_COLOR, DrColor.EMPTY);
        defaults.add(FontAttr.TEXT_EFFECT, TextEffect.NONE);
        defaults.add(FontAttr.NO_PROOFING, AttrBoolEx.FALSE);
        defaults.add(FontAttr.BIDI, AttrBoolEx.FALSE);
        defaults.add(FontAttr.COMPLEX_SCRIPT, AttrBoolEx.FALSE);
        defaults.add(FontAttr.SNAP_TO_GRID, AttrBoolEx.TRUE);

        defaults.add(FontAttr.LOCALE_ID, PROCESS_OR_USER_DEFAULT_LANGUAGE_ID);
        defaults.add(FontAttr.LOCALE_ID_BI, PROCESS_OR_USER_DEFAULT_LANGUAGE_ID);
        defaults.add(FontAttr.LOCALE_ID_FAR_EAST, PROCESS_OR_USER_DEFAULT_LANGUAGE_ID);

        defaults.add(FontAttr.CHARACTER_CATEGORY_HINT, CharacterCategory.OTHER);
        // See "MS-DOC chapter 2.6.1 Character Properties". Used only in WML and DOC formats.
        defaults.add(FontAttr.HYPHEN_RULE, HyphenRule.NORMAL);
        defaults.add(FontAttr.HYPHEN_CHAR, 0);

        defaults.add(FontAttr.THEME_COLOR, "");
        defaults.add(FontAttr.THEME_SHADE, "");
        defaults.add(FontAttr.THEME_TINT, "");
        defaults.add(FontAttr.UNDERLINE_THEME_COLOR, "");
        defaults.add(FontAttr.UNDERLINE_THEME_SHADE, "");
        defaults.add(FontAttr.UNDERLINE_THEME_TINT, "");

        defaults.add(FontAttr.MATH_IS_LITERAL, false);
        defaults.add(FontAttr.MATH_IS_NORMAL_TEXT, false);
        defaults.add(FontAttr.MATH_SCRIPT, MathScript.DEFAULT);
        defaults.add(FontAttr.MATH_STYLE, MathStyle.DEFAULT);
        defaults.add(FontAttr.MATH_IS_ALIGNMENT_POINT, false);

        defaults.add(FontAttr.MATH_IS_O_MATH, false);
        defaults.add(FontAttr.EMPHASIS_MARK, EmphasisMark.NONE);

        defaults.add(FontAttr.EFFECT_GLOW, getDmlNullEffect());
        defaults.add(FontAttr.EFFECT_SHADOW, getDmlNullEffect());
        defaults.add(FontAttr.EFFECT_REFLECTION, getDmlNullEffect());
        defaults.add(FontAttr.EFFECT_OUTLINE, getDmlNullEffect());
        defaults.add(FontAttr.EFFECT_FILL, getDmlNullEffect());
        defaults.add(FontAttr.EFFECT_SCENE_3_D, getDmlNullEffect());
        defaults.add(FontAttr.EFFECT_PROPS_3_D, getDmlNullEffect());
        defaults.add(FontAttr.OPEN_TYPE_LIGATURE, Ligature.NONE);
        defaults.add(FontAttr.OPEN_TYPE_NUM_FORM, NumForm.DEFAULT);
        defaults.add(FontAttr.OPEN_TYPE_NUM_SPACING, NumSpacing.DEFAULT);
        defaults.add(FontAttr.OPEN_TYPE_STYLISTIC_SETS, StylisticSets.DEFAULT);
        defaults.add(FontAttr.OPEN_TYPE_CONTEXTUAL_ALTERNATES, false);

        defaults.add(FontAttr.FAR_EAST_LAYOUT, new FarEastLayout());
        defaults.add(FontAttr.FIT_TEXT, new FitText(0, 0));

        defaults.add(FontAttr.RSID_R_PR, 0);
        defaults.add(FontAttr.RSID_R, 0);

        return defaults;
    }

    static String getDefaultNameAscii() { return ((ComplexFontName)fetchDefaultAttr(FontAttr.NAME_ASCII)).getName(); }

    static String getDefaultNameBi() { return ((ComplexFontName)fetchDefaultAttr(FontAttr.NAME_BI)).getName(); }

    static final int PROCESS_OR_USER_DEFAULT_LANGUAGE_ID = 0x400;

    static /*ThemeFontCore*/int[] ThemeFontAttrs = new /*ThemeFontCore*/int[] {
        ThemeFontCore.MINOR_ASCII,
        ThemeFontCore.MINOR_H_ANSI,
        ThemeFontCore.MINOR_EAST_ASIA,
        ThemeFontCore.MINOR_BIDI,
        ThemeFontCore.MAJOR_ASCII,
        ThemeFontCore.MAJOR_H_ANSI,
        ThemeFontCore.MAJOR_EAST_ASIA,
        ThemeFontCore.MAJOR_BIDI
    };

    private static DmlNullEffect getDmlNullEffect()
    {
        if (gDmlNullEffect == null)
            gDmlNullEffect = new DmlNullEffect();

        return gDmlNullEffect;
    }

    private static DmlNullEffect gDmlNullEffect;

    /// <summary>
    /// Keys to detect that RunPr has math-specific properties.
    /// </summary>
    private static /*final*/ int[] G_MATH_KEYS_TO_CHECK = new int[] {
        FontAttr.MATH_IS_LITERAL,
        FontAttr.MATH_IS_NORMAL_TEXT,
        FontAttr.MATH_LINE_BREAK,
        FontAttr.MATH_SCRIPT,
        FontAttr.MATH_STYLE,
        FontAttr.MATH_IS_ALIGNMENT_POINT };

    /// <summary>
    /// Specifies what attributes to preserve during <see cref="AcceptFormatRevision"/>
    /// in presence of changed style when dealing with regular runs.
    /// </summary>
    private static /*final*/ int[] G_REVISION_PRESERVED_RUN_ATTRIBUTES = new int[] {
        RevisionAttr.MOVE_FROM_REVISION,
        RevisionAttr.MOVE_TO_REVISION,
        RevisionAttr.INSERT_REVISION,
        RevisionAttr.DELETE_REVISION };

    /// <summary>
    /// Specifies all font name keys.
    /// </summary>
    static /*final*/ int[] FONT_NAME_ATTRIBUTES = new int[] {
        FontAttr.NAME_ASCII,
        FontAttr.NAME_BI,
        FontAttr.NAME_FAR_EAST,
        FontAttr.NAME_OTHER };

    /// <summary>
    /// Specifies all locale keys.
    /// </summary>
    static /*final*/ int[] LOCALE_ATTRIBUTES = new int[] {
        FontAttr.LOCALE_ID,
        FontAttr.LOCALE_ID_BI,
        FontAttr.LOCALE_ID_FAR_EAST };

    /// <summary>
    /// Non-collapsible font attributes, <see cref="AttrCollection.IsIgnoredOnCollapse"/>.
    /// </summary>
    static /*final*/ int[] NON_COLLAPSIBLE_FONT_KEYS = { FontAttr.RSID_R_PR, FontAttr.RSID_R, FontAttr.SYS_SYMBOL };

    /// <summary>
    /// Toggle properties, as specified in ECMA-376-1:2016, section 17.7.3.
    /// </summary>
    static /*final*/ int[] TOGGLE_ATTRIBUTES =
    {
        FontAttr.BOLD,
        FontAttr.BOLD_BI,
        FontAttr.ALL_CAPS,
        FontAttr.EMBOSS,
        FontAttr.ITALIC,
        FontAttr.ITALIC_BI,
        FontAttr.ENGRAVE,
        FontAttr.OUTLINE,
        FontAttr.SHADOW,
        FontAttr.SMALL_CAPS,
        FontAttr.STRIKE_THROUGH,
        FontAttr.HIDDEN
    };

    /// <summary>
    /// Specifies what attributes to preserve during <see cref="AcceptFormatRevision"/>
    /// in presence of changed style when dealing with symbolic runs.
    /// </summary>
    private static /*final*/ int[] G_REVISION_PRESERVED_SYMBOL_ATTRIBUTES = new int[] {
        RevisionAttr.MOVE_FROM_REVISION,
        RevisionAttr.MOVE_TO_REVISION,
        RevisionAttr.INSERT_REVISION,
        RevisionAttr.DELETE_REVISION,
        FontAttr.NAME_ASCII,
        FontAttr.NAME_OTHER };

    private static /*final*/ RunPr G_DEFAULTS = initDefaults();

    private static final String TIMES_NEW_ROMAN = "Times New Roman";
}

