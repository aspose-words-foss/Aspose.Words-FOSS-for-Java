// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;


/// <summary>
/// 21.1.2.2.7 pPr (Text Paragraph Properties)
/// This element contains all paragraph level text properties for the containing paragraph. 
/// These paragraph properties should override any and all conflicting properties that are 
/// associated with the paragraph in question.
/// </summary>
class DmlParagraphProperties extends DmlExtensionListSource implements Cloneable
{
    DmlParagraphProperties()
    {
        mPropertyBag.setParentBagProvider(G_DEFAULT_PARENT_BAG_PROVIDER);
    }

    DmlParagraphProperties deepClone()
    {
        DmlParagraphProperties lhs = (DmlParagraphProperties)memberwiseClone();

        if (mPropertyBag != null)
            lhs.mPropertyBag = mPropertyBag.deepClone();

        if (mDefaultRunProperties != null)
            lhs.mDefaultRunProperties = mDefaultRunProperties.deepClone();

        DmlTextBullet bullet = (DmlTextBullet)getDirectProperty(DmlParagraphPropertiesIds.BULLET);
        if (bullet != null)
            lhs.setProperty(DmlParagraphPropertiesIds.BULLET, bullet.deepClone());

        DmlTextBulletColor bulletColor = (DmlTextBulletColor)getDirectProperty(DmlParagraphPropertiesIds.BULLET_COLOR);
        if (bulletColor != null)
            lhs.setProperty(DmlParagraphPropertiesIds.BULLET_COLOR, bulletColor.deepClone());

        DmlTextBulletSize bulletSize = (DmlTextBulletSize)getDirectProperty(DmlParagraphPropertiesIds.BULLET_SIZE);
        if (bulletSize != null)
            lhs.setProperty(DmlParagraphPropertiesIds.BULLET_SIZE, bulletSize.deepClone());

        DmlTextBulletFont bulletFont = (DmlTextBulletFont)getDirectProperty(DmlParagraphPropertiesIds.BULLET_FONT);
        if (bulletFont != null)
            lhs.setProperty(DmlParagraphPropertiesIds.BULLET_FONT, bulletFont.deepClone());

        DmlTextSpacing spacing = (DmlTextSpacing)getDirectProperty(DmlParagraphPropertiesIds.LINE_SPACING);
        if (spacing != null)
            lhs.setProperty(DmlParagraphPropertiesIds.LINE_SPACING, spacing.deepClone());

        spacing = (DmlTextSpacing)getDirectProperty(DmlParagraphPropertiesIds.SPACE_AFTER);
        if (spacing != null)
            lhs.setProperty(DmlParagraphPropertiesIds.SPACE_AFTER, spacing.deepClone());

        spacing = (DmlTextSpacing)getDirectProperty(DmlParagraphPropertiesIds.SPACE_BEFORE);
        if (spacing != null)
            lhs.setProperty(DmlParagraphPropertiesIds.SPACE_BEFORE, spacing.deepClone());

        TabStopCollection tabStops = (TabStopCollection)getDirectProperty(DmlParagraphPropertiesIds.TAB_LIST);
        if (tabStops != null)
            lhs.setProperty(DmlParagraphPropertiesIds.TAB_LIST, tabStops.deepClone());

        if (hasExtensions())
            lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    /// <summary>
    /// Clears the properties so that default formatting is used.
    /// </summary>
    void clear()
    {
        mPropertyBag.removeAll();
        mHasDefaultRunProperties = false;
        setDefaultRunProperties(null);
    }

    Object getDirectProperty(/*DmlParagraphPropertiesIds*/int propertyId)
    {
        return mPropertyBag.getDirectProperty((int)propertyId);
    }

    /// <summary>
    /// Determines whether the mPropertyBag contains the specified property, which was set directly.
    /// </summary>
    /// <param name="attr">the attr</param>
    /// <returns>"true", if the  property was set directly, "false" otherwise</returns>
    boolean isPropertySpecified(/*DmlParagraphPropertiesIds*/int attr)
    {
        return mPropertyBag.isPropertySpecified((int)attr);
    }

    void setParentProperties(DmlTextListStyles textListStyles)
    {
        setParentProperties(new DmlParagraphParentBagProvider(this, textListStyles));
    }

    void setParentProperties(DmlParagraphProperties parentProperties)
    {
        setParentProperties(new DmlHierarchicalPropertyBagParentContainer(parentProperties.mPropertyBag));
    }

    void setParentProperties(DmlParagraphPropertiesDefaults parentProperties)
    {
        setParentProperties(new DmlHierarchicalPropertyBagParentContainer(parentProperties));
    }

    void setParentProperties(IDmlHierarchicalPropertyBagParentProvider parentProvider)
    {
        mPropertyBag.setParentBagProvider(parentProvider);
    }

    private void setProperty(/*DmlParagraphPropertiesIds*/int propertyId, Object value)
    {
        mPropertyBag.setProperty((int)propertyId, value);
    }

    private Object getProperty(/*DmlParagraphPropertiesIds*/int propertyId)
    {
        return mPropertyBag.getProperty((int)propertyId);
    }

    public /*override*/ StringToObjDictionary<DmlExtension> getExtensions() { return (StringToObjDictionary<DmlExtension>)getProperty(DmlParagraphPropertiesIds.EXTENSIONS); }
    public /*override*/ void setExtensions(StringToObjDictionary<DmlExtension> value) { setProperty(DmlParagraphPropertiesIds.EXTENSIONS, value); }

    DmlTextBullet getBullet() { return (DmlTextBullet)getProperty(DmlParagraphPropertiesIds.BULLET); }
    void setBullet(DmlTextBullet value) { setProperty(DmlParagraphPropertiesIds.BULLET, value); }

    DmlTextBulletColor getBulletColor() { return (DmlTextBulletColor)getProperty(DmlParagraphPropertiesIds.BULLET_COLOR); }
    void setBulletColor(DmlTextBulletColor value) { setProperty(DmlParagraphPropertiesIds.BULLET_COLOR, value); }

    DmlTextBulletSize getBulletSize() { return (DmlTextBulletSize)getProperty(DmlParagraphPropertiesIds.BULLET_SIZE); }
    void setBulletSize(DmlTextBulletSize value) { setProperty(DmlParagraphPropertiesIds.BULLET_SIZE, value); }

    DmlTextBulletFont getBulletFont() { return (DmlTextBulletFont)getProperty(DmlParagraphPropertiesIds.BULLET_FONT); }
    void setBulletFont(DmlTextBulletFont value) { setProperty(DmlParagraphPropertiesIds.BULLET_FONT, value); }

    /// <summary>
    /// Specifies the particular level text properties that this paragraph follows. 
    /// The value for this attribute is numerical and formats the text according to 
    /// the corresponding level paragraph properties that are listed within the lstStyle element. 
    /// Since there are nine separate level properties defined, this tag has an effective range of 0-8 = 9 available values.
    /// </summary>
    int getLevel() { return (/*int*/Integer)getProperty(DmlParagraphPropertiesIds.LEVEL); }
    void setLevel(int value) { setProperty(DmlParagraphPropertiesIds.LEVEL, value); }

    /// <summary>
    /// Specifies the alignment that is to be applied to the paragraph. Possible values for this include left, 
    /// right, centered, justified and distributed. If this attribute is omitted, then a value of left is implied.
    /// </summary>
    /*ParagraphAlignment*/int getAlignment() { return (/*ParagraphAlignment*/Integer)getProperty(DmlParagraphPropertiesIds.ALIGNMENT); }
    void setAlignment(/*ParagraphAlignment*/int value) { setProperty(DmlParagraphPropertiesIds.ALIGNMENT, value); }

    /// <summary>
    /// Specifies the default size for a tab character within this paragraph. This attribute should be used to describe 
    /// the spacing of tabs within the paragraph instead of a leading indentation tab. For indentation tabs there are 
    /// the marL and indent attributes to assist with this. 
    /// </summary>
    int getDefaultTabSize() { return (/*int*/Integer)getProperty(DmlParagraphPropertiesIds.DEFAULT_TAB_SIZE); }
    void setDefaultTabSize(int value) { setProperty(DmlParagraphPropertiesIds.DEFAULT_TAB_SIZE, value); }

    /// <summary>
    /// Specifies whether an East Asian word can be broken in half and wrapped onto the next line without 
    /// a hyphen being added. To determine whether an East Asian word can be broken the presentation 
    /// application would use the kinsoku settings here. This attribute is to be used specifically when 
    /// there is a word that cannot be broken into multiple pieces without a hyphen. That is it is not present 
    /// within the existence of normal breakable East Asian words but is when a special case word arises that 
    /// should not be broken for a line break. If this attribute is omitted, then a value of 1 or true is implied.
    /// </summary>
    boolean isEastAsianLineBreakAllowed() { return (/*boolean*/Boolean)getProperty(DmlParagraphPropertiesIds.IS_EAST_ASIAN_LINE_BREAK_ALLOWED); }
    void isEastAsianLineBreakAllowed(boolean value) { setProperty(DmlParagraphPropertiesIds.IS_EAST_ASIAN_LINE_BREAK_ALLOWED, value); }

    /// <summary>
    /// Determines where vertically on a line of text the actual words are positioned. This deals with vertical 
    /// placement of the characters with respect to the baselines. For instance having text anchored to the top 
    /// baseline, anchored to the bottom baseline, centered in between, etc. To understand this attribute and 
    /// it's use it is helpful to understand what baselines are. A diagram describing these different cases 
    /// is shown below. If this attribute is omitted, then a value of base is implied.
    /// </summary>
    /*DmlFontAlignment*/int getFontAlignment() { return (/*DmlFontAlignment*/Integer)getProperty(DmlParagraphPropertiesIds.FONT_ALIGNMENT); }
    void setFontAlignment(/*DmlFontAlignment*/int value) { setProperty(DmlParagraphPropertiesIds.FONT_ALIGNMENT, value); }

    /// <summary>
    /// Specifies whether punctuation is to be forcefully laid out on a line of text or put on a different line of text. 
    /// That is, if there is punctuation at the end of a run of text that should be carried over to a separate line 
    /// does it actually get carried over. A true value allows for hanging punctuation forcing the punctuation to 
    /// not be carried over and a value of false allows the punctuation to be carried onto the next text line. 
    /// If this attribute is omitted, then a value of 0, or false is implied.
    /// </summary>
    boolean isHangingPunctuationAllowed() { return (/*boolean*/Boolean)getProperty(DmlParagraphPropertiesIds.IS_HANGING_PUNCTUATION_ALLOWED); }
    void isHangingPunctuationAllowed(boolean value) { setProperty(DmlParagraphPropertiesIds.IS_HANGING_PUNCTUATION_ALLOWED, value); }

    /// <summary>
    /// Specifies the indent size that is applied to the first line of text in the paragraph. 
    /// An indentation of 0 is considered to be at the same location as marL attribute. 
    /// If this attribute is omitted, then a value of -342900 is implied.
    /// </summary>
    int getTextIdentation() { return (/*int*/Integer)getProperty(DmlParagraphPropertiesIds.TEXT_IDENTATION); }
    void setTextIdentation(int value) { setProperty(DmlParagraphPropertiesIds.TEXT_IDENTATION, value); }

    /// <summary>
    /// Specifies whether a Latin word can be broken in half and wrapped onto the next line without a hyphen being added. 
    /// This attribute is to be used specifically when there is a word that cannot be broken into multiple pieces without 
    /// a hyphen. It is not present within the existence of normal breakable Latin words but is when a special case word 
    /// arises that should not be broken for a line break. If this attribute is omitted, then a value of 1 or true is implied.
    /// </summary>
    boolean isLatinLineBreakAllowed() { return (/*boolean*/Boolean)getProperty(DmlParagraphPropertiesIds.IS_LATIN_LINE_BREAK_ALLOWED); }
    void isLatinLineBreakAllowed(boolean value) { setProperty(DmlParagraphPropertiesIds.IS_LATIN_LINE_BREAK_ALLOWED, value); }

    /// <summary>
    /// Specifies the left margin of the paragraph. This is specified in addition to the text body inset and applies 
    /// only to this text paragraph. That is the text body inset and the marL attributes are additive with respect 
    /// to the text position. If this attribute is omitted, then a value of 347663 is implied.
    /// </summary>
    int getLeftMargin() { return (/*int*/Integer)getProperty(DmlParagraphPropertiesIds.LEFT_MARGIN); }
    void setLeftMargin(int value) { setProperty(DmlParagraphPropertiesIds.LEFT_MARGIN, value); }

    /// <summary>
    /// Specifies the right margin of the paragraph. This is specified in addition to the text body inset and applies 
    /// only to this text paragraph. That is the text body inset and the marR attributes are additive with respect to 
    /// the text position. If this attribute is omitted, then a value of 0 is implied.
    /// </summary>
    int getRightMargin() { return (/*int*/Integer)getProperty(DmlParagraphPropertiesIds.RIGHT_MARGIN); }
    void setRightMargin(int value) { setProperty(DmlParagraphPropertiesIds.RIGHT_MARGIN, value); }

    /// <summary>
    /// Specifies whether the text is right-to-left or left-to-right in its flow direction. 
    /// If this attribute is omitted, then a value of 0, or left-to-right is implied.
    /// </summary>
    boolean getRightToLeftFlowDirection() { return (/*boolean*/Boolean)getProperty(DmlParagraphPropertiesIds.RIGHT_TO_LEFT_FLOW_DIRECTION); }
    void setRightToLeftFlowDirection(boolean value) { setProperty(DmlParagraphPropertiesIds.RIGHT_TO_LEFT_FLOW_DIRECTION, value); }

    DmlRunProperties getDefaultRunProperties()
    {
        if (mDefaultRunProperties == null)
            mDefaultRunProperties = new DmlRunProperties();
        return mDefaultRunProperties;
    }

    void setDefaultRunProperties(DmlRunProperties value) { mDefaultRunProperties = value; }

    /// <summary>
    /// Specifies the vertical line spacing that is to be used within a paragraph. 
    /// This can be specified in two different ways, percentage spacing and font point spacing. 
    /// If this element is omitted then the spacing between two lines of text should be determined by 
    /// the point size of the largest piece of text within a line.
    /// </summary>
    DmlTextSpacing getLineSpacing() { return (DmlTextSpacing)getProperty(DmlParagraphPropertiesIds.LINE_SPACING); }
    void setLineSpacing(DmlTextSpacing value) { setProperty(DmlParagraphPropertiesIds.LINE_SPACING, value); }

    /// <summary>
    /// Specifies the amount of vertical white space that is present after a paragraph. 
    /// </summary>
    DmlTextSpacing getSpaceAfter() { return (DmlTextSpacing)getProperty(DmlParagraphPropertiesIds.SPACE_AFTER); }
    void setSpaceAfter(DmlTextSpacing value) { setProperty(DmlParagraphPropertiesIds.SPACE_AFTER, value); }

    /// <summary>
    /// Specifies the amount of vertical white space that is present before a paragraph.
    /// </summary>
    DmlTextSpacing getSpaceBefore() { return (DmlTextSpacing)getProperty(DmlParagraphPropertiesIds.SPACE_BEFORE); }
    void setSpaceBefore(DmlTextSpacing value) { setProperty(DmlParagraphPropertiesIds.SPACE_BEFORE, value); }

    /// <summary>
    /// Specifies the list of all tab stops that are to be used within a paragraph.
    /// </summary>
    TabStopCollection getTabList() { return (TabStopCollection)getProperty(DmlParagraphPropertiesIds.TAB_LIST); }
    void setTabList(TabStopCollection value) { setProperty(DmlParagraphPropertiesIds.TAB_LIST, value); }

    /// <summary>
    /// Returns number of properties set explicitly.
    /// </summary>
    int getCount() { return mPropertyBag.getCount(); }

    /// <summary>
    /// Returns true if paragraph has bullet.
    /// </summary>
    boolean hasBullet() { return (getBullet() != null) && (getBullet().getBulletType() != DmlTextBulletType.NONE); }

    /// <summary>
    /// Flag indicates that paragraph properties has default run properties set in the document.
    /// Sometimes paragraph properties has empty DefaultRunProperties, that must be preserved.
    /// Flag is used exactly for this.
    /// </summary>
    boolean hasDefaultRunProperties() { return mHasDefaultRunProperties; }
    void hasDefaultRunProperties(boolean value) { mHasDefaultRunProperties = value; }

    /// <summary>
    /// Returns <c>true</c> if no properties are defined in this collection.
    /// </summary>
    boolean isEmpty() { return (getCount() == 0) && !hasDefaultRunProperties() && (getDefaultRunProperties().getCount() == 0); }

    private static /*final*/ IDmlHierarchicalPropertyBagParentProvider G_DEFAULT_PARENT_BAG_PROVIDER =
        new DmlHierarchicalPropertyBagParentContainer(DmlParagraphPropertiesDefaults.getInstance());

    private DmlRunProperties mDefaultRunProperties;
    private boolean mHasDefaultRunProperties;
    private IDmlHierarchicalPropertyBag mPropertyBag = new DmlHierarchicalPropertyBag();

    static class DmlParagraphParentBagProvider implements IDmlHierarchicalPropertyBagParentProvider, Cloneable
    {
        public DmlParagraphParentBagProvider(
            DmlParagraphProperties paragraphProperties, DmlTextListStyles textListStyles)
        {
            mParentBag = textListStyles.getTextListStyle(paragraphProperties.getLevel()).mPropertyBag;
        }

        public IDmlHierarchicalPropertyBag getParentBag() { return mParentBag; }

        public IDmlHierarchicalPropertyBagParentProvider deepClone()
        {
            DmlParagraphParentBagProvider lhs = (DmlParagraphParentBagProvider)memberwiseClone();
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

