// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 21.1.2.1.1 bodyPr (Body Properties)
/// This element defines the body properties for the text body within a shape.
/// </summary>
class DmlTextBodyProperties extends DmlExtensionListSource implements Cloneable
{
    DmlTextBodyProperties()
    {
        mPropertyBag.setParentBagProvider(G_DEFAULT_PARENT_BAG_PROVIDER);
    }

    DmlTextBodyProperties deepClone()
    {
        DmlTextBodyProperties lhs = (DmlTextBodyProperties)memberwiseClone();

        if (mPropertyBag != null)
            lhs.mPropertyBag = mPropertyBag.deepClone();

        if (mFlatText != null)
            lhs.mFlatText = mFlatText.deepClone();

        if (mPresetTextWrap != null)
            lhs.mPresetTextWrap = mPresetTextWrap.deepClone();

        if (mAutoFitMode != null)
            lhs.mAutoFitMode = mAutoFitMode.deepClone();

        if (mScene3DProperties != null)
            lhs.mScene3DProperties = mScene3DProperties.deepClone();

        if (mShape3DProperties != null)
            lhs.mShape3DProperties = mShape3DProperties.deepClone();

        if (hasExtensions())
            lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    void setParentProperties(DmlTextBodyProperties parentProperties)
    {
        mPropertyBag.setParentBagProvider(new DmlHierarchicalPropertyBagParentContainer(parentProperties.mPropertyBag));
    }

    private void setProperty(/*DmlTextBodyPropertiesDefaultsIds*/int propertyId, Object value)
    {
        mPropertyBag.setProperty((int)propertyId, value);
    }

    private Object getProperty(/*DmlTextBodyPropertiesDefaultsIds*/int propertyId)
    {
        return mPropertyBag.getProperty((int)propertyId);
    }

    Object getDirectProperty(/*DmlTextBodyPropertiesDefaultsIds*/int propertyId)
    {
        return mPropertyBag.getDirectProperty((int)propertyId);
    }

    /// <summary>
    /// Determines whether the mPropertyBag contains the specified property, which was set directly.
    /// </summary>
    /// <param name="propertyId">the property id</param>
    /// <returns>"true", if the  property was set directly, "false" otherwise</returns>
    boolean isPropertySpecified(/*DmlTextBodyPropertiesDefaultsIds*/int propertyId)
    {
        return mPropertyBag.isPropertySpecified((int)propertyId);
    }

    void remove(/*DmlTextBodyPropertiesDefaultsIds*/int propertyId)
    {
        mPropertyBag.remove((int)propertyId);
    }

    DmlFlatText getFlatText() { return mFlatText; }
    void setFlatText(DmlFlatText value) { mFlatText = value; }

    DmlPresetTextWarp getPresetTextWrap()
    {
        if (mPresetTextWrap == null)
            mPresetTextWrap = new DmlPresetTextWarp();

        return mPresetTextWrap;
    }
    void setPresetTextWrap(DmlPresetTextWarp value) { mPresetTextWrap = value; }

    DmlAutoFitMode getAutoFitMode()
    {
        if (mAutoFitMode == null)
            mAutoFitMode = new DmlNoAutoFitMode();

        return mAutoFitMode;
    }
    void setAutoFitMode(DmlAutoFitMode value) { mAutoFitMode = value; }

    DmlScene3DProperties getScene3DProperties() { return mScene3DProperties; }
    void setScene3DProperties(DmlScene3DProperties value) { mScene3DProperties = value; }

    DmlShape3DProperties getShape3DProperties() { return mShape3DProperties; }
    void setShape3DProperties(DmlShape3DProperties value) { mShape3DProperties = value; }

  
    /// <summary>
    /// Indicates whether insets are specified.
    /// </summary>
    boolean hasInsets()
    {
        return isPropertySpecified(DmlTextBodyPropertiesDefaultsIds.LEFT_INSET) ||
            isPropertySpecified(DmlTextBodyPropertiesDefaultsIds.TOP_INSET) ||
            isPropertySpecified(DmlTextBodyPropertiesDefaultsIds.RIGHT_INSET) ||
            isPropertySpecified(DmlTextBodyPropertiesDefaultsIds.BOTTOM_INSET);
    }

    /// <summary>
    /// Specifies the bottom inset of the bounding rectangle. 
    /// Insets are used just as internal margins for text boxes within shapes. 
    /// If this attribute is omitted, a value of 45720 or 0.05 inches is implied.
    /// </summary>
    int getBottomInset() { return (/*int*/Integer)getProperty(DmlTextBodyPropertiesDefaultsIds.BOTTOM_INSET); }
    void setBottomInset(int value) { setProperty(DmlTextBodyPropertiesDefaultsIds.BOTTOM_INSET, value); }

    /// <summary>
    /// Specifies the top inset of the bounding rectangle. 
    /// Insets are used just as internal margins for text boxes within shapes. 
    /// If this attribute is omitted, then a value of 45720 or 0.05 inches is implied.
    /// </summary>
    int getTopInset() { return (/*int*/Integer)getProperty(DmlTextBodyPropertiesDefaultsIds.TOP_INSET); }
    void setTopInset(int value) { setProperty(DmlTextBodyPropertiesDefaultsIds.TOP_INSET, value); }

    /// <summary>
    /// Specifies the right inset of the bounding rectangle. 
    /// Insets are used just as internal margins for text boxes within shapes. 
    /// If this attribute is omitted, then a value of 91440 or 0.1 inches is implied.
    /// </summary>
    int getRightInset() { return (/*int*/Integer)getProperty(DmlTextBodyPropertiesDefaultsIds.RIGHT_INSET); }
    void setRightInset(int value) { setProperty(DmlTextBodyPropertiesDefaultsIds.RIGHT_INSET, value); }

    /// <summary>
    /// Specifies the left inset of the bounding rectangle. 
    /// Insets are used just as internal margins for text boxes within shapes. 
    /// If this attribute is omitted, then a value of 91440 or 0.1 inches is implied.
    /// </summary>
    int getLeftInset() { return (/*int*/Integer)getProperty(DmlTextBodyPropertiesDefaultsIds.LEFT_INSET); }
    void setLeftInset(int value) { setProperty(DmlTextBodyPropertiesDefaultsIds.LEFT_INSET, value); }

    /// <summary>
    /// Specifies the anchoring position of the txBody within the shape. 
    /// If this attribute is omitted, then a value of t, or top is implied.
    /// </summary>
    /*DmlTextAnchoringType*/int getAnchor() { return (/*DmlTextAnchoringType*/Integer)getProperty(DmlTextBodyPropertiesDefaultsIds.ANCHOR); }
    void setAnchor(/*DmlTextAnchoringType*/int value) { setProperty(DmlTextBodyPropertiesDefaultsIds.ANCHOR, value); }

    /// <summary>
    /// Specifies the centering of the text box. The way it works fundamentally 
    /// is to determine the smallest possible "bounds box" for the text and then 
    /// to center that "bounds box" accordingly. This is different than paragraph alignment, 
    /// which aligns the text within the "bounds box" for the text. This flag is compatible 
    /// with all of the different kinds of anchoring. If this attribute is omitted, 
    /// then a value of 0 or false is implied.
    /// </summary>
    boolean getAnchorCenter() { return (/*boolean*/Boolean)getProperty(DmlTextBodyPropertiesDefaultsIds.ANCHOR_CENTER); }
    void setAnchorCenter(boolean value) { setProperty(DmlTextBodyPropertiesDefaultsIds.ANCHOR_CENTER, value); }

    /// <summary>
    /// Specifies that the line spacing for this text body is decided in a simplistic manner using the font scene. 
    /// If this attribute is omitted, a value of 0 or false is implied.
    /// </summary>
    boolean getUseCompatibleLineSpacing() { return (/*boolean*/Boolean)getProperty(DmlTextBodyPropertiesDefaultsIds.USE_COMPATIBLE_LINE_SPACING); }
    void setUseCompatibleLineSpacing(boolean value) { setProperty(DmlTextBodyPropertiesDefaultsIds.USE_COMPATIBLE_LINE_SPACING, value); }

    /// <summary>
    /// Forces the text to be rendered anti-aliased regardless of the font size. 
    /// Certain fonts can appear grainy around their edges unless they are anti-aliased. 
    /// Therefore this attribute allows for the specifying of which bodies of text should always 
    /// be anti-aliased and which ones should not. If this attribute is omitted, then a value of 0 or false is implied.
    /// </summary>
    boolean getForceAntiAlias() { return (/*boolean*/Boolean)getProperty(DmlTextBodyPropertiesDefaultsIds.FORCE_ANTI_ALIAS); }
    void setForceAntiAlias(boolean value) { setProperty(DmlTextBodyPropertiesDefaultsIds.FORCE_ANTI_ALIAS, value); }

    /// <summary>
    /// Specifies that text within this textbox is converted text from a WordArt object. 
    /// This is more of a backwards compatibility attribute that is useful to the application 
    /// from a tracking perspective. WordArt was the former way to apply text effects and therefore 
    /// this attribute is useful in document conversion scenarios. 
    /// If this attribute is omitted, then a value of 0 or false is implied.
    /// </summary>
    boolean getFromWordArt() { return (/*boolean*/Boolean)getProperty(DmlTextBodyPropertiesDefaultsIds.FROM_WORD_ART); }
    void setFromWordArt(boolean value) { setProperty(DmlTextBodyPropertiesDefaultsIds.FROM_WORD_ART, value); }

    /// <summary>
    /// Determines whether the text can flow out of the bounding box horizontally. 
    /// This is used to determine what happens in the event that the text within a shape is too 
    /// large for the bounding box it is contained within. 
    /// If this attribute is omitted, then a value of overflow is implied.
    /// </summary>
    /*DmlTextHorizontalOverflowType*/int getTextHorizontalOverflow() { return (/*DmlTextHorizontalOverflowType*/Integer)getProperty(DmlTextBodyPropertiesDefaultsIds.TEXT_HORIZONTAL_OVERFLOW); }
    void setTextHorizontalOverflow(/*DmlTextHorizontalOverflowType*/int value) { setProperty(DmlTextBodyPropertiesDefaultsIds.TEXT_HORIZONTAL_OVERFLOW, value); }

    /// <summary>
    /// Specifies the number of columns of text in the bounding rectangle. 
    /// When applied to a text run this property takes the width of the bounding box for the text 
    /// and divides it by the number of columns specified. 
    /// These columns are then treated as overflow containers in that when the previous column 
    /// has been filled with text the next column acts as the repository for additional text. 
    /// When all columns have been filled and text still remains then the overflow properties 
    /// set for this text body are used and the text is reflowed to make room for additional text. 
    /// If this attribute is omitted, then a value of 1 is implied.
    /// </summary>
    int getColumnNumber() { return (/*int*/Integer)getProperty(DmlTextBodyPropertiesDefaultsIds.COLUMN_NUMBER); }
    void setColumnNumber(int value)
    {
        // In error cases we will render in one column
        setProperty(DmlTextBodyPropertiesDefaultsIds.COLUMN_NUMBER, ((value < 1) ? 1 : value));
    }

    /// <summary>
    /// Specifies the rotation that is being applied to the text within the bounding box. 
    /// If it not specified, the rotation of the accompanying shape is used. 
    /// If it is specified, then this is applied independently from the shape. 
    /// That is the shape can have a rotation applied in addition to the text itself having 
    /// a rotation applied to it. 
    /// If this attribute is omitted, then a value of 0, is implied.
    /// </summary>
    DmlAngle getRotation() { return (DmlAngle)getProperty(DmlTextBodyPropertiesDefaultsIds.ROTATION); }
    void setRotation(DmlAngle value) { setProperty(DmlTextBodyPropertiesDefaultsIds.ROTATION, value); }

    /// <summary>
    /// Specifies whether default rotation is specified.
    /// For some reason MS Word 2013 sets axis labels rotation to -1000 degrease by default.
    /// But this value is treated as zero in MS Word. So to fix the problem simply reset value to zero. 
    /// but to preserve this value upon open/save DOCX this flag is introduced.
    /// </summary>
    boolean hasDefaultRotation() { return (/*boolean*/Boolean)getProperty(DmlTextBodyPropertiesDefaultsIds.HAS_DEFAULT_ROTATION); }
    void hasDefaultRotation(boolean value) { setProperty(DmlTextBodyPropertiesDefaultsIds.HAS_DEFAULT_ROTATION, value); }

    /// <summary>
    /// Specifies whether columns are used in a right-to-left or left-to-right order. 
    /// The usage of this attribute only sets the column order that is used to determine 
    /// which column overflow text should go to next. 
    /// If this attribute is omitted, then a value of left-to-right is implied in which 
    /// case text starts in the leftmost column and flow to the right.
    /// </summary>
    /*DmlTextColumnOrder*/int getColumnOrder() { return (/*DmlTextColumnOrder*/Integer)getProperty(DmlTextBodyPropertiesDefaultsIds.COLUMN_ORDER); }
    void setColumnOrder(/*DmlTextColumnOrder*/int value) { setProperty(DmlTextBodyPropertiesDefaultsIds.COLUMN_ORDER, value); }

    /// <summary>
    /// Specifies the space between text columns in the text area. 
    /// This should only apply when there is more than 1 column present. 
    /// If this attribute is omitted, then a value of 0 is implied.
    /// </summary>
    int getSpaceBetweenColumns() { return (/*int*/Integer)getProperty(DmlTextBodyPropertiesDefaultsIds.SPACE_BETWEEN_COLUMNS); }
    void setSpaceBetweenColumns(int value) { setProperty(DmlTextBodyPropertiesDefaultsIds.SPACE_BETWEEN_COLUMNS, value); }

    /// <summary>
    /// Specifies whether the before and after paragraph spacing defined by the user is to be respected. 
    /// While the spacing between paragraphs is helpful, it is additionally useful to be able to set a 
    /// flag as to whether this spacing is to be followed at the edges of the text body, in other words 
    /// the first and last paragraphs in the text body. More precisely since this is a text body level 
    /// property it should only effect the before paragraph spacing of the first paragraph and the after 
    /// paragraph spacing of the last paragraph for a given text body. If this attribute is omitted, 
    /// then a value of 0, or false is implied.
    /// </summary>
    boolean getAreFirstAndLastParagraphsUseSpacing() { return (/*boolean*/Boolean)getProperty(DmlTextBodyPropertiesDefaultsIds.ARE_FIRST_AND_LAST_PARAGRAPHS_USE_SPACING); }
    void setAreFirstAndLastParagraphsUseSpacing(boolean value) { setProperty(DmlTextBodyPropertiesDefaultsIds.ARE_FIRST_AND_LAST_PARAGRAPHS_USE_SPACING, value); }

    /// <summary>
    /// Specifies whether text should remain upright, regardless of the transform applied to it and the 
    /// accompanying shape transform. If this attribute is omitted, then a value of 0, or false is implied.
    /// </summary>
    boolean isTextUpright() { return (/*boolean*/Boolean)getProperty(DmlTextBodyPropertiesDefaultsIds.IS_TEXT_UPRIGHT); }
    void isTextUpright(boolean value) { setProperty(DmlTextBodyPropertiesDefaultsIds.IS_TEXT_UPRIGHT, value); }

    /// <summary>
    /// Determines if the text within the given text body should be displayed vertically. 
    /// If this attribute is omitted, then a value of horz, or no vertical text is implied.
    /// </summary>
    /*ShapeTextOrientation*/int getTextOrientation() { return (/*ShapeTextOrientation*/Integer)getProperty(DmlTextBodyPropertiesDefaultsIds.TEXT_ORIENTATION); }
    void setTextOrientation(/*ShapeTextOrientation*/int value) { setProperty(DmlTextBodyPropertiesDefaultsIds.TEXT_ORIENTATION, value); }

    /// <summary>
    /// Determines whether the text can flow out of the bounding box vertically. 
    /// This is used to determine what happens in the event that the text within a shape is too 
    /// large for the bounding box it is contained within. 
    /// If this attribute is omitted, then a value of overflow is implied.
    /// </summary>
    /*DmlTextVerticalOverflowType*/int getTextVerticalOverflow() { return (/*DmlTextVerticalOverflowType*/Integer)getProperty(DmlTextBodyPropertiesDefaultsIds.TEXT_VERTICAL_OVERFLOW); }
    void setTextVerticalOverflow(/*DmlTextVerticalOverflowType*/int value) { setProperty(DmlTextBodyPropertiesDefaultsIds.TEXT_VERTICAL_OVERFLOW, value); }

    /// <summary>
    /// Specifies the wrapping options to be used for this text body. 
    /// If this attribute is omitted, then a value of square is implied which wraps the text using the bounding text box. 
    /// </summary>
    /*TextBoxWrapMode*/int getTextWrappingType() { return (/*TextBoxWrapMode*/Integer)getProperty(DmlTextBodyPropertiesDefaultsIds.TEXT_WRAPPING_TYPE); }
    void setTextWrappingType(/*TextBoxWrapMode*/int value) { setProperty(DmlTextBodyPropertiesDefaultsIds.TEXT_WRAPPING_TYPE, value); }

    /// <summary>
    /// Returns <c>true</c> if no properties are defined in this collection.
    /// </summary>
    boolean isEmpty()
    {
        return
            (mPropertyBag.getCount() == 0) &&
            (mPropertyBag.getExtensionProperties() == null) &&
            (mAutoFitMode == null) &&
            (mFlatText == null) &&
            !hasInsets() &&
            (mPresetTextWrap == null) &&
            (mScene3DProperties == null) &&
            (mShape3DProperties == null);
    }

    private DmlAutoFitMode mAutoFitMode;
    private DmlFlatText mFlatText;
    private DmlPresetTextWarp mPresetTextWrap;
    private DmlScene3DProperties mScene3DProperties;
    private DmlShape3DProperties mShape3DProperties;

    private IDmlHierarchicalPropertyBag mPropertyBag = new DmlHierarchicalPropertyBag();

    private static /*final*/ IDmlHierarchicalPropertyBagParentProvider G_DEFAULT_PARENT_BAG_PROVIDER =
        new DmlHierarchicalPropertyBagParentContainer(DmlTextBodyPropertiesDefaults.getInstance());

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

