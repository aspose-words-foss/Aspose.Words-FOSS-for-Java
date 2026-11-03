// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.FontStyle;
import com.aspose.StringToObjDictionary;
import com.aspose.Language;


/// <summary>
/// 21.1.2.3.9 rPr (Text Run Properties)
/// This element contains all run level text properties for the text runs within a containing paragraph.
/// </summary>
class DmlRunProperties extends DmlExtensionListSource implements IMathRunPr, Cloneable
{
    DmlRunProperties()
    {
        mPropertyBag.setParentBagProvider(G_DEFAULT_PROVIDER);
    }

    DmlRunProperties deepClone()
    {
        DmlRunProperties lhs = (DmlRunProperties)memberwiseClone();
        copyTo(lhs);

        if (hasExtensions())
            lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    /// <summary>
    /// Copies all defined property values to the specified run properties.
    /// </summary>
    void copyTo(DmlRunProperties properties)
    {
        properties.mPropertyBag = mPropertyBag.deepClone();

        if (mEffects != null)
            properties.mEffects = mEffects.deepClone();

        DmlTextPoints kerning = (DmlTextPoints)getDirectProperty(DmlRunPropertiesIds.KERNING);
        if (kerning != null)
            properties.setProperty(DmlRunPropertiesIds.KERNING, kerning.deepClone());

        DmlTextPoints spacing = (DmlTextPoints)getDirectProperty(DmlRunPropertiesIds.SPACING);
        if (spacing != null)
            properties.setProperty(DmlRunPropertiesIds.SPACING, spacing.deepClone());

        DmlTextPoints fontSize = (DmlTextPoints)getDirectProperty(DmlRunPropertiesIds.FONT_SIZE);
        if (fontSize != null)
            properties.setProperty(DmlRunPropertiesIds.FONT_SIZE, fontSize.deepClone());

        DmlFill fill = (DmlFill)getDirectProperty(DmlRunPropertiesIds.FILL);
        if (fill != null)
            properties.setProperty(DmlRunPropertiesIds.FILL, fill.deepClone());

        DmlOutline outline = (DmlOutline)getDirectProperty(DmlRunPropertiesIds.OUTLINE);
        if (outline != null)
            properties.setProperty(DmlRunPropertiesIds.OUTLINE, outline.deepClone());

        DmlColor color = (DmlColor)getDirectProperty(DmlRunPropertiesIds.HIGHLIGHT_COLOR);
        if (color != null)
            properties.setProperty(DmlRunPropertiesIds.HIGHLIGHT_COLOR, color.deepClone());

        DmlFont font = (DmlFont)getDirectProperty(DmlRunPropertiesIds.LATIN_FONT);
        if (font != null)
            properties.setProperty(DmlRunPropertiesIds.LATIN_FONT, font.deepClone());

        font = (DmlFont)getDirectProperty(DmlRunPropertiesIds.EAST_ASIAN_FONT);
        if (font != null)
            properties.setProperty(DmlRunPropertiesIds.EAST_ASIAN_FONT, font.deepClone());

        font = (DmlFont)getDirectProperty(DmlRunPropertiesIds.SYMBOL_FONT);
        if (font != null)
            properties.setProperty(DmlRunPropertiesIds.SYMBOL_FONT, font.deepClone());

        font = (DmlFont)getDirectProperty(DmlRunPropertiesIds.COMPLEX_SCRIPT_FONT);
        if (font != null)
            properties.setProperty(DmlRunPropertiesIds.COMPLEX_SCRIPT_FONT, font.deepClone());

        DmlHlink hyperlink = (DmlHlink)getDirectProperty(DmlRunPropertiesIds.HLINK_CLICK);
        if (hyperlink != null)
            properties.setProperty(DmlRunPropertiesIds.HLINK_CLICK, hyperlink.deepClone());

        hyperlink = (DmlHlink)getDirectProperty(DmlRunPropertiesIds.HLINK_HOVER);
        if (hyperlink != null)
            properties.setProperty(DmlRunPropertiesIds.HLINK_HOVER, hyperlink.deepClone());

        fill = (DmlFill)getDirectProperty(DmlRunPropertiesIds.UNDERLINE_FILL);
        if (fill != null)
            properties.setProperty(DmlRunPropertiesIds.UNDERLINE_FILL, fill.deepClone());

        outline = (DmlOutline)getDirectProperty(DmlRunPropertiesIds.UNDERLINE_STROKE);
        if (outline != null)
            properties.setProperty(DmlRunPropertiesIds.UNDERLINE_STROKE, outline.deepClone());
    }

    void setParentProperties(DmlRunProperties properties)
    {
        mPropertyBag.setParentBagProvider(new DmlHierarchicalPropertyBagParentContainer(properties.mPropertyBag));
    }

    DmlFont getFont(/*DmlFontType*/int fontType)
    {
        switch (fontType)
        {
            case DmlFontType.LATIN:
                return getLatinFont();
            case DmlFontType.EAST_ASIAN:
                return getEastAsianFont();
            case DmlFontType.SYMBOL:
                return getSymbolFont();
            case DmlFontType.COMPLEX_SCRIPT:
                return getComplexScriptFont();
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "fontType");
        }
    }

    /// <summary>
    /// Returns font that is to be applied to the run.
    /// </summary>
    DmlFont getFont()
    {
        // If one of four fonts is set directly in run properties, return it.
        // Otherwise return Latin font inherited from parent.
        DmlFont latin = (DmlFont)getDirectProperty(DmlRunPropertiesIds.LATIN_FONT);
        if (latin != null)
            return latin;

        DmlFont ea = (DmlFont)getDirectProperty(DmlRunPropertiesIds.EAST_ASIAN_FONT);
        if (ea != null)
            return ea;

        DmlFont cs = (DmlFont)getDirectProperty(DmlRunPropertiesIds.COMPLEX_SCRIPT_FONT);
        if (cs != null)
            return cs;

        DmlFont sym = (DmlFont)getDirectProperty(DmlRunPropertiesIds.SYMBOL_FONT);
        if (sym != null)
            return sym;

        return getLatinFont();
    }

    /*FontStyle*/int getFontStyle()
    {
        /*FontStyle*/int style = FontStyle.REGULAR;
        if (getBold())
            style |= FontStyle.BOLD;
        if (getUnderline() != Underline.NONE)
            style |= FontStyle.UNDERLINE;
        if (getItalics())
            style |= FontStyle.ITALIC;
        if (getStrikethrough() != DmlTextStrike.NO)
            style |= FontStyle.STRIKEOUT;
        return style;
    }

    void setParentProperties(DmlParagraph paragraph, DmlTextListStyles textListStyles)
    {
        mPropertyBag.setParentBagProvider(new DmlRunProperties.DmlTextRunParentBagProvider(paragraph, textListStyles));
    }

    void setProperty(/*DmlRunPropertiesIds*/int propertyId, Object value)
    {
        mPropertyBag.setProperty((int)propertyId, value);
    }

    Object getProperty(/*DmlRunPropertiesIds*/int propertyId)
    {
        return mPropertyBag.getProperty((int)propertyId);
    }

    Object getDirectProperty(/*DmlRunPropertiesIds*/int propertyId)
    {
        return mPropertyBag.getDirectProperty((int)propertyId);
    }

    /// <summary>
    /// Removes value of the specified property (clears the property).
    /// </summary>
    void remove(/*DmlRunPropertiesIds*/int propertyId)
    {
        mPropertyBag.remove((int)propertyId);
    }

    /// <summary>
    /// Removes values of all properties.
    /// </summary>
    void removeAll()
    {
        mPropertyBag.removeAll();
    }

    /// <summary>
    /// Determines whether the mPropertyBag contains the specified property, which was set directly.
    /// </summary>
    /// <param name="propertyId">the property id</param>
    /// <returns>"true", if the  property was set directly, "false" otherwise</returns>
    boolean isPropertySpecified(/*DmlRunPropertiesIds*/int propertyId)
    {
        return mPropertyBag.isPropertySpecified((int)propertyId);
    }

    /// <summary>
    /// Returns <c>true</c> if this property collection redefines any properties of the parent collection.
    /// </summary>
    boolean hasNonDefaultFormatting(int[] attributesToIgnore)
    {
        return mPropertyBag.hasNonDefaultFormatting(attributesToIgnore, null);
    }

    public boolean isDml() { return true; }

    public /*override*/ StringToObjDictionary<DmlExtension> getExtensions() { return (StringToObjDictionary<DmlExtension>)getProperty(DmlRunPropertiesIds.EXTENSIONS); }
    public /*override*/ void setExtensions(StringToObjDictionary<DmlExtension> value) { setProperty(DmlRunPropertiesIds.EXTENSIONS, value); }

    /// <summary>
    /// Specifies the alternate language to use when the generating application is displaying 
    /// the user interface controls. If this attribute is omitted, than the lang attribute is used here.
    /// </summary>
    /*Language*/int getAlternativeLanguage() { return (/*Language*/Integer)getProperty(DmlRunPropertiesIds.ALTERNATIVE_LANGUAGE); }
    void setAlternativeLanguage(/*Language*/int value) { setProperty(DmlRunPropertiesIds.ALTERNATIVE_LANGUAGE, value); }

    /// <summary>
    /// Specifies whether a run of text is formatted as bold text. 
    /// If this attribute is omitted, than a value of 0, or false is assumed.
    /// </summary>
    boolean getBold() { return (/*boolean*/Boolean)getProperty(DmlRunPropertiesIds.BOLD); }
    void setBold(boolean value) { setProperty(DmlRunPropertiesIds.BOLD, value); }

    /// <summary>
    /// Specifies the baseline for both the superscript and subscript fonts. 
    /// The size is specified using a percentage where 1% is equal to 1 percent of the font size and 
    /// 100% is equal to 100 percent font of the font size.
    /// Value is in fraction representation.
    /// </summary>
    double getBaseline() { return (/*double*/Double)getProperty(DmlRunPropertiesIds.BASELINE); }
    void setBaseline(double value) { setProperty(DmlRunPropertiesIds.BASELINE, value); }

    /// <summary>
    /// Specifies the link target name that is used to reference to the proper link properties in a custom XML part within the document.
    /// </summary>
    String getBookmarkLinkTarget() { return (String)getProperty(DmlRunPropertiesIds.BOOKMARK_LINK_TARGET); }
    void setBookmarkLinkTarget(String value) { setProperty(DmlRunPropertiesIds.BOOKMARK_LINK_TARGET, value); }

    /// <summary>
    /// Specifies the capitalization that is to be applied to the text run. 
    /// This is a render-only modification and does not affect the actual characters stored in the text run. 
    /// This attribute is also distinct from the toggle function where the actual characters stored in the text run are changed.
    /// </summary>
    /*DmlCapitalization*/int getCapitalization() { return (/*DmlCapitalization*/Integer)getProperty(DmlRunPropertiesIds.CAPITALIZATION); }
    void setCapitalization(/*DmlCapitalization*/int value) { setProperty(DmlRunPropertiesIds.CAPITALIZATION, value); }

    /// <summary>
    /// Specifies that the content of a text run has changed since the proofing tools have last been run. 
    /// Effectively this flags text that is to be checked again by the generating application 
    /// for mistakes such as spelling, grammar, etc.
    /// </summary>
    boolean isDirty() { return (/*boolean*/Boolean)getProperty(DmlRunPropertiesIds.IS_DIRTY); }
    void isDirty(boolean value) { setProperty(DmlRunPropertiesIds.IS_DIRTY, value); }

    /// <summary>
    /// Specifies that when this run of text was checked for spelling, grammar, etc. 
    /// that a mistake was indeed found. This allows the generating application to effectively save the 
    /// state of the mistakes within the document instead of having to perform 
    /// a full pass check upon opening the document.
    /// </summary>
    boolean hasSpellingError() { return (/*boolean*/Boolean)getProperty(DmlRunPropertiesIds.HAS_SPELLING_ERROR); }
    void hasSpellingError(boolean value) { setProperty(DmlRunPropertiesIds.HAS_SPELLING_ERROR, value); }

    /// <summary>
    /// Specifies whether a run of text is formatted as italic text. 
    /// If this attribute is omitted, than a value of 0, or false is assumed.
    /// </summary>
    boolean getItalics() { return (/*boolean*/Boolean)getProperty(DmlRunPropertiesIds.ITALICS); }
    void setItalics(boolean value) { setProperty(DmlRunPropertiesIds.ITALICS, value); }

    /// <summary>
    /// Specifies the minimum font size at which character kerning occurs for this text run. 
    /// Whole points are specified in increments of 100 starting with 100 being a point size of 1. 
    /// For instance a font point size of 12 would be 1200 and a font point size of 12.5 would be 1250. 
    /// If this attribute is omitted, than kerning occurs for all font sizes down to a 0 point font.
    /// </summary>
    DmlTextPoints getKerning() { return (DmlTextPoints)getProperty(DmlRunPropertiesIds.KERNING); }
    void setKerning(DmlTextPoints value) { setProperty(DmlRunPropertiesIds.KERNING, value); }

    /// <summary>
    /// Specifies whether the numbers contained within vertical text continue vertically with the 
    /// text or whether they are to be displayed horizontally while the surrounding characters 
    /// continue in a vertical fashion. If this attribute is omitted, than a value of 0, or false is assumed.
    /// </summary>
    boolean getKumimoji() { return (/*boolean*/Boolean)getProperty(DmlRunPropertiesIds.KUMIMOJI); }
    void setKumimoji(boolean value) { setProperty(DmlRunPropertiesIds.KUMIMOJI, value); }

    /// <summary>
    /// Specifies the language to be used when the generating application is displaying the user interface controls. 
    /// If this attribute is omitted, than the generating application can select a language of its choice.
    /// </summary>
    /*Language*/int getLanguage() { return (/*Language*/Integer)getProperty(DmlRunPropertiesIds.LANGUAGE); }
    void setLanguage(/*Language*/int value) { setProperty(DmlRunPropertiesIds.LANGUAGE, value); }

    /// <summary>
    /// Specifies that a run of text has been selected by the user to not be checked for mistakes. 
    /// Therefore if there are spelling, grammar, etc mistakes within this text the generating application should ignore them.
    /// </summary>
    boolean getNoProofing() { return (/*boolean*/Boolean)getProperty(DmlRunPropertiesIds.NO_PROOFING); }
    void setNoProofing(boolean value) { setProperty(DmlRunPropertiesIds.NO_PROOFING, value); }

    /// <summary>
    /// Specifies the normalization of height that is to be applied to the text run. 
    /// This is a render-only modification and does not affect the actual characters stored in the text run. 
    /// This attribute is also distinct from the toggle function where the actual characters stored in the text run are changed. 
    /// If this attribute is omitted, than a value of 0, or false is assumed.
    /// </summary>
    boolean getNormalizeHeights() { return (/*boolean*/Boolean)getProperty(DmlRunPropertiesIds.NORMALIZE_HEIGHTS); }
    void setNormalizeHeights(boolean value) { setProperty(DmlRunPropertiesIds.NORMALIZE_HEIGHTS, value); }

    /// <summary>
    /// Specifies a smart tag identifier for a run of text. This ID is unique throughout the presentation 
    /// and is used to reference corresponding auxiliary information about the smart tag.
    /// </summary>
    boolean getSmartTagsClean() { return (/*boolean*/Boolean)getProperty(DmlRunPropertiesIds.SMART_TAGS_CLEAN); }
    void setSmartTagsClean(boolean value) { setProperty(DmlRunPropertiesIds.SMART_TAGS_CLEAN, value); }

    /// <summary>
    /// Specifies a smart tag identifier for a run of text. This ID is unique throughout the presentation 
    /// and is used to reference corresponding auxiliary information about the smart tag.
    /// AW: int.MinValue means that the attribute isn't defined.
    /// SmartTagID should be a positive unsigned integer value.
    /// </summary>
    /*uint*/int getSmartTagID() { return (/*uint*/Integer)getProperty(DmlRunPropertiesIds.SMART_TAG_ID); }
    void setSmartTagID(/*uint*/int value) { setProperty(DmlRunPropertiesIds.SMART_TAG_ID, value); }

    /// <summary>
    /// Specifies the spacing between characters within a text run. 
    /// This spacing is specified numerically and should be consistently applied across the entire run 
    /// of text by the generating application. Whole points are specified in increments of 100 starting 
    /// with 100 being a point size of 1. For instance a font point size of 12 would be 1200 and a font 
    /// point size of 12.5 would be 1250. If this attribute is omitted than a value of 0 or no adjustment is assumed.
    /// </summary>
    DmlTextPoints getSpacing() { return (DmlTextPoints)getProperty(DmlRunPropertiesIds.SPACING); }
    void setSpacing(DmlTextPoints value) { setProperty(DmlRunPropertiesIds.SPACING, value); }

    /// <summary>
    /// Specifies whether a run of text is formatted as strikethrough text. 
    /// If this attribute is omitted, than no strikethrough is assumed.
    /// </summary>
    /*DmlTextStrike*/int getStrikethrough() { return (/*DmlTextStrike*/Integer)getProperty(DmlRunPropertiesIds.STRIKETHROUGH); }
    void setStrikethrough(/*DmlTextStrike*/int value) { setProperty(DmlRunPropertiesIds.STRIKETHROUGH, value); }

    /// <summary>
    /// Specifies the size of text within a text run. Whole points are specified in increments of 
    /// 100 starting with 100 being a point size of 1. For instance a font point size of 12 would 
    /// be 1200 and a font point size of 12.5 would be 1250. If this attribute is omitted, 
    /// than the value in defRPr should be used.
    /// </summary>
    DmlTextPoints getFontSize() { return (DmlTextPoints)getProperty(DmlRunPropertiesIds.FONT_SIZE); }
    void setFontSize(DmlTextPoints value) { setProperty(DmlRunPropertiesIds.FONT_SIZE, value); }

    /// <summary>
    /// Specifies whether a run of text is formatted as underlined text. 
    /// If this attribute is omitted, than no underline is assumed.
    /// </summary>
    /*Underline*/int getUnderline() { return (/*Underline*/Integer)getProperty(DmlRunPropertiesIds.UNDERLINE); }
    void setUnderline(/*Underline*/int value) { setProperty(DmlRunPropertiesIds.UNDERLINE, value); }

    DmlFill getFill() { return (DmlFill)getProperty(DmlRunPropertiesIds.FILL); }
    void setFill(DmlFill value) { setProperty(DmlRunPropertiesIds.FILL, value); }

    DmlOutline getOutline() { return (DmlOutline)getProperty(DmlRunPropertiesIds.OUTLINE); }
    void setOutline(DmlOutline value) { setProperty(DmlRunPropertiesIds.OUTLINE, value); }

    DmlColor getHighlightColor() { return (DmlColor)getProperty(DmlRunPropertiesIds.HIGHLIGHT_COLOR); }
    void setHighlightColor(DmlColor value) { setProperty(DmlRunPropertiesIds.HIGHLIGHT_COLOR, value); }

    DmlFont getLatinFont() { return (DmlFont)getProperty(DmlRunPropertiesIds.LATIN_FONT); }
    void setLatinFont(DmlFont value) { setProperty(DmlRunPropertiesIds.LATIN_FONT, value); }

    DmlFont getEastAsianFont() { return (DmlFont)getProperty(DmlRunPropertiesIds.EAST_ASIAN_FONT); }
    void setEastAsianFont(DmlFont value) { setProperty(DmlRunPropertiesIds.EAST_ASIAN_FONT, value); }

    DmlFont getSymbolFont() { return (DmlFont)getProperty(DmlRunPropertiesIds.SYMBOL_FONT); }
    void setSymbolFont(DmlFont value) { setProperty(DmlRunPropertiesIds.SYMBOL_FONT, value); }

    DmlFont getComplexScriptFont() { return (DmlFont)getProperty(DmlRunPropertiesIds.COMPLEX_SCRIPT_FONT); }
    void setComplexScriptFont(DmlFont value) { setProperty(DmlRunPropertiesIds.COMPLEX_SCRIPT_FONT, value); }

    /// <summary>
    /// Collection of effects applied to the text element.
    /// </summary>
    /// <remarks>
    /// I did not manage to create document where effects were inherited from paragraph level.
    /// For now leave effects collection not inheritable (it is not in DmlHierarchicalPropertyBag).
    /// This means only directly applied effects has effect. 
    /// </remarks>
    DmlShapeEffectsCollection getEffects() { return mEffects; }
    void setEffects(DmlShapeEffectsCollection value) { mEffects = value; }

    /// <summary>
    /// Specifies the on-click hyperlink information to be applied to a run of text. When the hyperlink text is clicked the 
    /// link will be fetched.
    /// </summary>
    DmlHlink getHlinkClick() { return (DmlHlink)getProperty(DmlRunPropertiesIds.HLINK_CLICK); }
    void setHlinkClick(DmlHlink value) { setProperty(DmlRunPropertiesIds.HLINK_CLICK, value); }

    /// <summary>
    /// Specifies the mouse-over hyperlink information to be applied to a run of text. When the mouse is hovered over 
    /// this hyperlink text the link will be fetched.
    /// </summary>
    DmlHlink getHlinkHover() { return (DmlHlink)getProperty(DmlRunPropertiesIds.HLINK_HOVER); }
    void setHlinkHover(DmlHlink value) { setProperty(DmlRunPropertiesIds.HLINK_HOVER, value); }

    /// <summary>
    /// Specifies the fill color of an underline for a run of text.
    /// </summary>
    DmlFill getUnderlineFill() { return (DmlFill)getProperty(DmlRunPropertiesIds.UNDERLINE_FILL); }
    void setUnderlineFill(DmlFill value) { setProperty(DmlRunPropertiesIds.UNDERLINE_FILL, value); }

    /// <summary>
    /// Specifies that the fill color of an underline for a run of text should be of the same color as the text 
    /// run within which it is contained.
    /// </summary>
    boolean getUnderlineFillTx() { return (/*boolean*/Boolean)getProperty(DmlRunPropertiesIds.UNDERLINE_FILL_TX); }
    void setUnderlineFillTx(boolean value) { setProperty(DmlRunPropertiesIds.UNDERLINE_FILL_TX, value); }

    /// <summary>
    /// Specifies the properties for the stroke of the underline that will be present within a run of text.
    /// </summary>
    DmlOutline getUnderlineStroke() { return (DmlOutline)getProperty(DmlRunPropertiesIds.UNDERLINE_STROKE); }
    void setUnderlineStroke(DmlOutline value) { setProperty(DmlRunPropertiesIds.UNDERLINE_STROKE, value); }

    /// <summary>
    /// Specifies that the stroke style of an underline for a run of text should be of the same as the text run 
    /// within which it is contained.
    /// </summary>
    boolean getUnderlineStrokeTx() { return (/*boolean*/Boolean)getProperty(DmlRunPropertiesIds.UNDERLINE_STROKE_TX); }
    void setUnderlineStrokeTx(boolean value) { setProperty(DmlRunPropertiesIds.UNDERLINE_STROKE_TX, value); }

    /// <summary>
    /// Specifies whether the text is right-to-left or left-to-right in its flow direction. 
    /// If this attribute is omitted, then a value of 0, or left-to-right is implied.
    /// </summary>
    boolean getRightToLeftFlowDirection() { return (/*boolean*/Boolean)getProperty(DmlRunPropertiesIds.RIGHT_TO_LEFT_FLOW_DIRECTION); }
    void setRightToLeftFlowDirection(boolean value) { setProperty(DmlRunPropertiesIds.RIGHT_TO_LEFT_FLOW_DIRECTION, value); }

    boolean isEmpty() { return (mPropertyBag.getCount() == 0); }

    /// <summary>
    /// Returns number of properties set explicitly.
    /// </summary>
    public int getCount() { return mPropertyBag.getCount(); }

    private static /*final*/ IDmlHierarchicalPropertyBagParentProvider G_DEFAULT_PROVIDER =
        new DmlHierarchicalPropertyBagParentContainer(DmlRunPropertiesDefaults.getInstance());

    private IDmlHierarchicalPropertyBag mPropertyBag = new DmlHierarchicalPropertyBag();
    private DmlShapeEffectsCollection mEffects;

    static class DmlTextRunParentBagProvider implements IDmlHierarchicalPropertyBagParentProvider, Cloneable
    {
        public DmlTextRunParentBagProvider(DmlParagraph paragraph, DmlTextListStyles textListStyles)
        {
            mParentBag = textListStyles.getTextListStyle(paragraph.getProperties().getLevel()).getDefaultRunProperties().mPropertyBag;
        }

        public IDmlHierarchicalPropertyBag getParentBag() { return mParentBag; }

        public IDmlHierarchicalPropertyBagParentProvider deepClone()
        {
            DmlTextRunParentBagProvider lhs = (DmlTextRunParentBagProvider)memberwiseClone();
            if (mParentBag != null)
                lhs.mParentBag = mParentBag.deepClone();
            return lhs;
        }
        
        private IDmlHierarchicalPropertyBag mParentBag;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

