// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/08/2007 by Vladimir Averkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.SortedIntegerListGeneric;


/// <summary>
/// Latent styles provide a way to store style behavior properties which must be specified for all styles
/// known to an application without requiring the store of the formatting properties.
///
/// Latent styles refer to any set of style definitions known to an application
/// which have not been included in the current document.
///
/// In particular is needed for correct display of styles quick format list in Word 2007.
/// We do not know where this properties are stored in DOC but they are present in DOCX and WordML.
///
/// Specifies the properties and exceptions which shall be applied to a set of latent styles for
/// this document.
/// </summary>
class LatentStyles implements Cloneable
{
    // Example:
    //    <w:latentStyles w:defLockedState="0" w:defUIPriority="0" w:defSemiHidden="0" w:defUnhideWhenUsed="0" w:defQFormat="0" w:count="267">

    /// <summary>
    /// Makes a deep clone.
    /// </summary>
    LatentStyles deepClone()
    {
        LatentStyles latentStyles = (LatentStyles)memberwiseClone();

        latentStyles.mItems = new SortedIntegerListGeneric<LatentStyle>();

        for (int i = 0; i < mItems.getCount(); i++)
        {
            LatentStyle latentStyle = mItems.getByIndex(i);
            latentStyles.mItems.add(mItems.getKey(i), latentStyle.deepClone());
        }

        return latentStyles;
    }

    /// <summary>
    /// Returns <c>true</c> if the specified latent style exception has the same properties as default.
    /// </summary>
    public boolean equalsDefault(LatentStyle latentStyle)
    {
        return
            (latentStyle.getLocked() == getDefaultLockedState()) &&
            (latentStyle.getQuickStyle() == getDefaultQuickFormat()) &&
            (latentStyle.getSemiHidden() == getDefaultSemiHidden()) &&
            (latentStyle.getUIPriority() == getDefaultUIPriority()) &&
            (latentStyle.getUnhideWhenUsed() == getDefaultUnhideWhenUsed());
    }

    /// <summary>
    /// Specifies the default setting for the locked element (§2.7.3.7) which shall be applied to
    /// any style made available by the hosting application which is not explicitly defined in the
    /// current document. This setting shall be overridden for every style for which a latent style
    /// exception (§2.7.3.8) exists.
    /// </summary>
    boolean getDefaultLockedState() { return mDefaultLockedState; }
    void setDefaultLockedState(boolean value) { mDefaultLockedState = value; }

    /// <summary>
    /// Specifies the default setting for the qFormat element.
    /// </summary>
    boolean getDefaultQuickFormat() { return mDefaultQuickFormat; }
    void setDefaultQuickFormat(boolean value) { mDefaultQuickFormat = value; }

    /// <summary>
    /// Specifies the default setting for the semiHidden element.
    /// </summary>
    boolean getDefaultSemiHidden() { return mDefaultSemiHidden; }
    void setDefaultSemiHidden(boolean value) { mDefaultSemiHidden = value; }

    /// <summary>
    /// Specifies the default setting for the uiPriority element.
    /// </summary>
    int getDefaultUIPriority() { return mDefaultUIPriority; }
    void setDefaultUIPriority(int value) { mDefaultUIPriority = value; }

    /// <summary>
    /// Specifies the default setting for the unhideWhenUsed element.
    /// </summary>
    boolean getDefaultUnhideWhenUsed() { return mDefaultUnhideWhenUsed; }
    void setDefaultUnhideWhenUsed(boolean value) { mDefaultUnhideWhenUsed = value; }

    /// <summary>
    /// Specifies the number of known styles which shall be initialized to the current latent style
    /// defaults when this document is first processed.
    /// </summary>
    int getKnownStylesCount() { return mKnownStylesCount; }
    void setKnownStylesCount(int value) { mKnownStylesCount = value; }

    /// <summary>
    /// Returns <c>true</c> if LatentStyles differs from the initial state.
    /// </summary>
    boolean isCustomized()
    {
        return !isWord2003Default() &&
               !isWord2007Default() &&
               !isWord2013Default() &&
               !isWord2019Default();
    }

    private boolean isWord2003Default()
    {
        LatentStyles defLatentStyles = new LatentStyles();
        defLatentStyles.initToWord2003Default();

        return equals(defLatentStyles);
    }

    private boolean isWord2007Default()
    {
        LatentStyles defLatentStyles = new LatentStyles();
        defLatentStyles.initToWord2007Default();

        return equals(defLatentStyles);
    }

    private boolean isWord2013Default()
    {
        LatentStyles defLatentStyles = new LatentStyles();
        defLatentStyles.initToWord2013Default();

        return equals(defLatentStyles);
    }

    private boolean isWord2019Default()
    {
        LatentStyles defLatentStyles = new LatentStyles();
        defLatentStyles.initToWord2019Default();

        return equals(defLatentStyles);
    }

    /// <summary>
    /// Creates default set of latent styles exceptions as in Word 2003.
    /// </summary>
    void initToWord2003Default()
    {
        mDefaultUIPriority = 0;
        mDefaultSemiHidden = false;
        mDefaultUnhideWhenUsed = false;

        mItems.clear();

        addDefault(StyleIdentifier.NORMAL, 0, false, false, true);
        addDefault(StyleIdentifier.HEADING_1, 0, false, false, true);
        addDefault(StyleIdentifier.HEADING_2, 0, true, true, true);
        addDefault(StyleIdentifier.HEADING_3, 0, true, true, true);
        addDefault(StyleIdentifier.HEADING_4, 0, true, true, true);
        addDefault(StyleIdentifier.HEADING_5, 0, true, true, true);
        addDefault(StyleIdentifier.HEADING_6, 0, true, true, true);
        addDefault(StyleIdentifier.HEADING_7, 0, true, true, true);
        addDefault(StyleIdentifier.HEADING_8, 0, true, true, true);
        addDefault(StyleIdentifier.HEADING_9, 0, true, true, true);
        addDefault(StyleIdentifier.CAPTION, 0, true, true, true);
        addDefault(StyleIdentifier.TITLE, 0, false, false, true);
        addDefault(StyleIdentifier.SUBTITLE, 0, false, false, true);
        addDefault(StyleIdentifier.STRONG, 0, false, false, true);
        addDefault(StyleIdentifier.EMPHASIS, 0, false, false, true);

        addCommonDefaults();
    }

    /// <summary>
    /// Creates default set of latent styles exceptions for Word 2007.
    /// </summary>
    void initToWord2007Default()
    {
        mItems.clear();

        addDefault(StyleIdentifier.NORMAL, 0, false, false, true);
        addDefault(StyleIdentifier.HEADING_1, 9, false, false, true);
        addDefault(StyleIdentifier.HEADING_2, 9, true, true, true);
        addDefault(StyleIdentifier.HEADING_3, 9, true, true, true);
        addDefault(StyleIdentifier.HEADING_4, 9, true, true, true);
        addDefault(StyleIdentifier.HEADING_5, 9, true, true, true);
        addDefault(StyleIdentifier.HEADING_6, 9, true, true, true);
        addDefault(StyleIdentifier.HEADING_7, 9, true, true, true);
        addDefault(StyleIdentifier.HEADING_8, 9, true, true, true);
        addDefault(StyleIdentifier.HEADING_9, 9, true, true, true);
        addDefault(StyleIdentifier.TOC_1, 39, true, true, false);
        addDefault(StyleIdentifier.TOC_2, 39, true, true, false);
        addDefault(StyleIdentifier.TOC_3, 39, true, true, false);
        addDefault(StyleIdentifier.TOC_4, 39, true, true, false);
        addDefault(StyleIdentifier.TOC_5, 39, true, true, false);
        addDefault(StyleIdentifier.TOC_6, 39, true, true, false);
        addDefault(StyleIdentifier.TOC_7, 39, true, true, false);
        addDefault(StyleIdentifier.TOC_8, 39, true, true, false);
        addDefault(StyleIdentifier.TOC_9, 39, true, true, false);
        addDefault(StyleIdentifier.CAPTION, 35, true, true, true);
        addDefault(StyleIdentifier.TITLE, 10, false, false, true);
        addDefault(StyleIdentifier.DEFAULT_PARAGRAPH_FONT, 1, true, true, false);
        addDefault(StyleIdentifier.SUBTITLE, 11, false, false, true);
        addDefault(StyleIdentifier.STRONG, 22, false, false, true);
        addDefault(StyleIdentifier.EMPHASIS, 20, false, false, true);
        addDefault(StyleIdentifier.TABLE_GRID, 59, false, false, false);

        addCommonDefaults();
    }

    /// <summary>
    /// Safely adds a latent style exception. 
    /// If another item with the same style identifier exists, overwrites it.
    /// </summary>
    void add(LatentStyle latentStyle)
    {
        mItems.set((int)latentStyle.getStyleIdentifier(), latentStyle);
    }

    /// <summary>
    /// Gets a latent style exception by style identifier.
    /// </summary>
    LatentStyle getByStyleIdentifier(/*StyleIdentifier*/int styleIdenitifier) { return mItems.get((int)styleIdenitifier); }

    /// <summary>
    /// Gets a latent style exception by index.
    /// </summary>
    LatentStyle get(int index) { return mItems.getByIndex(index); }

    int getCount() { return mItems.getCount(); }

    private void initToWord2013Default()
    {
        mItems.clear();

        addDefault(StyleIdentifier.NORMAL, 0, false, false, true);
        addDefault(StyleIdentifier.HEADING_1, 9, false, false, true);
        addDefault(StyleIdentifier.HEADING_2, 9, true, true, true);
        addDefault(StyleIdentifier.HEADING_3, 9, true, true, true);
        addDefault(StyleIdentifier.HEADING_4, 9, true, true, true);
        addDefault(StyleIdentifier.HEADING_5, 9, true, true, true);
        addDefault(StyleIdentifier.HEADING_6, 9, true, true, true);
        addDefault(StyleIdentifier.HEADING_7, 9, true, true, true);
        addDefault(StyleIdentifier.HEADING_8, 9, true, true, true);
        addDefault(StyleIdentifier.HEADING_9, 9, true, true, true);
        addDefault(StyleIdentifier.INDEX_1, 39, true, true, false);
        addDefault(StyleIdentifier.INDEX_2, 39, true, true, false);
        addDefault(StyleIdentifier.INDEX_3, 39, true, true, false);
        addDefault(StyleIdentifier.INDEX_4, 39, true, true, false);
        addDefault(StyleIdentifier.INDEX_5, 39, true, true, false);
        addDefault(StyleIdentifier.INDEX_6, 39, true, true, false);
        addDefault(StyleIdentifier.INDEX_7, 39, true, true, false);
        addDefault(StyleIdentifier.INDEX_8, 39, true, true, false);
        addDefault(StyleIdentifier.INDEX_9, 39, true, true, false);
        addDefault(StyleIdentifier.TOC_1, 35, true, true, true);
        addDefault(StyleIdentifier.TOC_2, 10, false, false, true);
        addDefault(StyleIdentifier.TOC_3, 1, true, true, false);
        addDefault(StyleIdentifier.TOC_4, 11, false, false, true);
        addDefault(StyleIdentifier.TOC_5, 22, false, false, true);
        addDefault(StyleIdentifier.TOC_6, 20, false, false, true);
        addDefault(StyleIdentifier.TOC_7, 39, false, false, false);
        addDefault(StyleIdentifier.TOC_8, 99, true, false, false);
        addDefault(StyleIdentifier.TOC_9, 1, false, false, true);
        addDefault(StyleIdentifier.NORMAL_INDENT, 60, true, true, false);
        addDefault(StyleIdentifier.FOOTNOTE_TEXT, 61, true, true, false);
        addDefault(StyleIdentifier.COMMENT_TEXT, 62, true, true, false);
        addDefault(StyleIdentifier.HEADER, 63, true, true, false);
        addDefault(StyleIdentifier.FOOTER, 64, true, true, false);
        addDefault(StyleIdentifier.INDEX_HEADING, 65, true, true, false);
        addDefault(StyleIdentifier.CAPTION, 66, true, true, false);
        addDefault(StyleIdentifier.TABLE_OF_FIGURES, 67, true, true, false);
        addDefault(StyleIdentifier.ENVELOPE_ADDRESS, 68, true, true, false);
        addDefault(StyleIdentifier.ENVELOPE_RETURN, 69, true, true, false);
        addDefault(StyleIdentifier.FOOTNOTE_REFERENCE, 70, true, true, false);
        addDefault(StyleIdentifier.COMMENT_REFERENCE, 71, true, true, false);
        addDefault(StyleIdentifier.LINE_NUMBER, 72, true, true, false);
        addDefault(StyleIdentifier.PAGE_NUMBER, 73, true, true, false);
        addDefault(StyleIdentifier.ENDNOTE_REFERENCE, 60, true, true, false);
        addDefault(StyleIdentifier.ENDNOTE_TEXT, 61, true, true, false);
        addDefault(StyleIdentifier.TABLE_OF_AUTHORITIES, 62, true, true, false);
        addDefault(StyleIdentifier.MACRO, 63, true, true, false);
        addDefault(StyleIdentifier.TOA_HEADING, 64, true, true, false);
        addDefault(StyleIdentifier.LIST, 65, true, true, false);
        addDefault(StyleIdentifier.LIST_BULLET, 99, true, false, false);
        addDefault(StyleIdentifier.LIST_NUMBER, 34, false, false, true);
        addDefault(StyleIdentifier.LIST_2, 29, false, false, true);
        addDefault(StyleIdentifier.LIST_3, 30, false, false, true);
        addDefault(StyleIdentifier.LIST_4, 66, true, true, false);
        addDefault(StyleIdentifier.LIST_5, 67, true, true, false);
        addDefault(StyleIdentifier.LIST_BULLET_2, 68, true, true, false);
        addDefault(StyleIdentifier.LIST_BULLET_3, 69, true, true, false);
        addDefault(StyleIdentifier.LIST_BULLET_4, 70, true, true, false);
        addDefault(StyleIdentifier.LIST_BULLET_5, 71, true, true, false);
        addDefault(StyleIdentifier.LIST_NUMBER_2, 72, true, true, false);
        addDefault(StyleIdentifier.LIST_NUMBER_3, 73, true, true, false);
        addDefault(StyleIdentifier.LIST_NUMBER_4, 60, true, true, false);
        addDefault(StyleIdentifier.LIST_NUMBER_5, 61, true, true, false);
        addDefault(StyleIdentifier.TITLE, 62, true, true, false);
        addDefault(StyleIdentifier.CLOSING, 63, true, true, false);
        addDefault(StyleIdentifier.SIGNATURE, 64, true, true, false);
        addDefault(StyleIdentifier.DEFAULT_PARAGRAPH_FONT, 65, true, true, false);
        addDefault(StyleIdentifier.BODY_TEXT, 66, true, true, false);
        addDefault(StyleIdentifier.BODY_TEXT_IND, 67, true, true, false);
        addDefault(StyleIdentifier.LIST_CONTINUE, 68, true, true, false);
        addDefault(StyleIdentifier.LIST_CONTINUE_2, 69, true, true, false);
        addDefault(StyleIdentifier.LIST_CONTINUE_3, 70, true, true, false);
        addDefault(StyleIdentifier.LIST_CONTINUE_4, 71, true, true, false);
        addDefault(StyleIdentifier.LIST_CONTINUE_5, 72, true, true, false);
        addDefault(StyleIdentifier.MESSAGE_HEADER, 73, true, true, false);
        addDefault(StyleIdentifier.SUBTITLE, 60, true, true, false);
        addDefault(StyleIdentifier.SALUTATION, 61, true, true, false);
        addDefault(StyleIdentifier.DATE, 62, true, true, false);
        addDefault(StyleIdentifier.BODY_TEXT_1_I, 63, true, true, false);
        addDefault(StyleIdentifier.BODY_TEXT_1_I_2, 64, true, true, false);
        addDefault(StyleIdentifier.NOTE_HEADING, 65, true, true, false);
        addDefault(StyleIdentifier.BODY_TEXT_2, 66, true, true, false);
        addDefault(StyleIdentifier.BODY_TEXT_3, 67, true, true, false);
        addDefault(StyleIdentifier.BODY_TEXT_IND_2, 68, true, true, false);
        addDefault(StyleIdentifier.BODY_TEXT_IND_3, 69, true, true, false);
        addDefault(StyleIdentifier.BLOCK_TEXT, 70, true, true, false);
        addDefault(StyleIdentifier.HYPERLINK, 71, true, true, false);
        addDefault(StyleIdentifier.FOLLOWED_HYPERLINK, 72, true, true, false);
        addDefault(StyleIdentifier.STRONG, 73, true, true, false);
        addDefault(StyleIdentifier.EMPHASIS, 60, true, true, false);
        addDefault(StyleIdentifier.DOCUMENT_MAP, 61, true, true, false);
        addDefault(StyleIdentifier.PLAIN_TEXT, 62, true, true, false);
        addDefault(StyleIdentifier.EMAIL_SIGNATURE, 63, true, true, false);
        addDefault(StyleIdentifier.HTML_TOP_OF_FORM, 64, true, true, false);
        addDefault(StyleIdentifier.HTML_BOTTOM_OF_FORM, 65, true, true, false);
        addDefault(StyleIdentifier.NORMAL_WEB, 66, true, true, false);
        addDefault(StyleIdentifier.HTML_ACRONYM, 67, true, true, false);
        addDefault(StyleIdentifier.HTML_ADDRESS, 68, true, true, false);
        addDefault(StyleIdentifier.HTML_CITE, 69, true, true, false);
        addDefault(StyleIdentifier.HTML_CODE, 70, true, true, false);
        addDefault(StyleIdentifier.HTML_DEFINITION, 71, true, true, false);
        addDefault(StyleIdentifier.HTML_KEYBOARD, 72, true, true, false);
        addDefault(StyleIdentifier.HTML_PREFORMATTED, 73, true, true, false);
        addDefault(StyleIdentifier.HTML_SAMPLE, 60, true, true, false);
        addDefault(StyleIdentifier.HTML_TYPEWRITER, 61, true, true, false);
        addDefault(StyleIdentifier.HTML_VARIABLE, 62, true, true, false);
        addDefault(StyleIdentifier.TABLE_NORMAL, 63, true, true, false);
        addDefault(StyleIdentifier.COMMENT_SUBJECT, 64, true, true, false);
        addDefault(StyleIdentifier.NO_LIST, 65, true, true, false);
        addDefault(StyleIdentifier.OUTLINE_LIST_1, 66, true, true, false);
        addDefault(StyleIdentifier.OUTLINE_LIST_2, 67, true, true, false);
        addDefault(StyleIdentifier.OUTLINE_LIST_3, 68, true, true, false);
        addDefault(StyleIdentifier.TABLE_SIMPLE_1, 69, true, true, false);
        addDefault(StyleIdentifier.TABLE_SIMPLE_2, 70, true, true, false);
        addDefault(StyleIdentifier.TABLE_SIMPLE_3, 71, true, true, false);
        addDefault(StyleIdentifier.TABLE_CLASSIC_1, 72, true, true, false);
        addDefault(StyleIdentifier.TABLE_CLASSIC_2, 73, true, true, false);
        addDefault(StyleIdentifier.TABLE_CLASSIC_3, 60, true, true, false);
        addDefault(StyleIdentifier.TABLE_CLASSIC_4, 61, true, true, false);
        addDefault(StyleIdentifier.TABLE_COLORFUL_1, 62, true, true, false);
        addDefault(StyleIdentifier.TABLE_COLORFUL_2, 63, true, true, false);
        addDefault(StyleIdentifier.TABLE_COLORFUL_3, 64, true, true, false);
        addDefault(StyleIdentifier.TABLE_COLUMNS_1, 65, true, true, false);
        addDefault(StyleIdentifier.TABLE_COLUMNS_2, 66, true, true, false);
        addDefault(StyleIdentifier.TABLE_COLUMNS_3, 67, true, true, false);
        addDefault(StyleIdentifier.TABLE_COLUMNS_4, 68, true, true, false);
        addDefault(StyleIdentifier.TABLE_COLUMNS_5, 69, true, true, false);
        addDefault(StyleIdentifier.TABLE_GRID_1, 70, true, true, false);
        addDefault(StyleIdentifier.TABLE_GRID_2, 71, true, true, false);
        addDefault(StyleIdentifier.TABLE_GRID_3, 72, true, true, false);
        addDefault(StyleIdentifier.TABLE_GRID_4, 73, true, true, false);
        addDefault(StyleIdentifier.TABLE_GRID_5, 19, false, false, true);
        addDefault(StyleIdentifier.TABLE_GRID_6, 21, false, false, true);
        addDefault(StyleIdentifier.TABLE_GRID_7, 31, false, false, true);
        addDefault(StyleIdentifier.TABLE_GRID_8, 32, false, false, true);
        addDefault(StyleIdentifier.TABLE_LIST_1, 33, false, false, true);
        addDefault(StyleIdentifier.TABLE_LIST_2, 37, true, true, false);
        addDefault(StyleIdentifier.TABLE_LIST_3, 39, true, true, true);
        addDefault(StyleIdentifier.TABLE_LIST_4, 41, false, false, false);
        addDefault(StyleIdentifier.TABLE_LIST_5, 42, false, false, false);
        addDefault(StyleIdentifier.TABLE_LIST_6, 43, false, false, false);
        addDefault(StyleIdentifier.TABLE_LIST_7, 44, false, false, false);
        addDefault(StyleIdentifier.TABLE_LIST_8, 45, false, false, false);
        addDefault(StyleIdentifier.TABLE_3_D_EFFECTS_1, 40, false, false, false);
        addDefault(StyleIdentifier.TABLE_3_D_EFFECTS_2, 46, false, false, false);
        addDefault(StyleIdentifier.TABLE_3_D_EFFECTS_3, 47, false, false, false);
        addDefault(StyleIdentifier.TABLE_CONTEMPORARY, 48, false, false, false);
        addDefault(StyleIdentifier.TABLE_ELEGANT, 49, false, false, false);
        addDefault(StyleIdentifier.TABLE_PROFESSIONAL, 50, false, false, false);
        addDefault(StyleIdentifier.TABLE_SUBTLE_1, 51, false, false, false);
        addDefault(StyleIdentifier.TABLE_SUBTLE_2, 52, false, false, false);
        addDefault(StyleIdentifier.TABLE_WEB_1, 46, false, false, false);
        addDefault(StyleIdentifier.TABLE_WEB_2, 47, false, false, false);
        addDefault(StyleIdentifier.TABLE_WEB_3, 48, false, false, false);
        addDefault(StyleIdentifier.BALLOON_TEXT, 49, false, false, false);
        addDefault(StyleIdentifier.TABLE_GRID, 50, false, false, false);
        addDefault(StyleIdentifier.TABLE_THEME, 51, false, false, false);
        addDefault(StyleIdentifier.PLACEHOLDER_TEXT, 52, false, false, false);
        addDefault(StyleIdentifier.NO_SPACING, 46, false, false, false);
        addDefault(StyleIdentifier.LIGHT_SHADING, 47, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST, 48, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID, 49, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1, 50, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2, 51, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1, 52, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_2, 46, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1, 47, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2, 48, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3, 49, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST, 50, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING, 51, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST, 52, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID, 46, false, false, false);
        addDefault(StyleIdentifier.LIGHT_SHADING_ACCENT_1, 47, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST_ACCENT_1, 48, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID_ACCENT_1, 49, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_1, 50, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2_ACCENT_1, 51, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1_ACCENT_1, 52, false, false, false);
        addDefault(StyleIdentifier.REVISION, 46, false, false, false);
        addDefault(StyleIdentifier.LIST_PARAGRAPH, 47, false, false, false);
        addDefault(StyleIdentifier.QUOTE, 48, false, false, false);
        addDefault(StyleIdentifier.INTENSE_QUOTE, 49, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_2_ACCENT_1, 50, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1_ACCENT_1, 51, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2_ACCENT_1, 52, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3_ACCENT_1, 46, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST_ACCENT_1, 47, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING_ACCENT_1, 48, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST_ACCENT_1, 49, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID_ACCENT_1, 50, false, false, false);
        addDefault(StyleIdentifier.LIGHT_SHADING_ACCENT_2, 51, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST_ACCENT_2, 52, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID_ACCENT_2, 46, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_2, 47, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2_ACCENT_2, 48, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1_ACCENT_2, 49, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_2_ACCENT_2, 50, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1_ACCENT_2, 51, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2_ACCENT_2, 52, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3_ACCENT_2, 46, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST_ACCENT_2, 47, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING_ACCENT_2, 48, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST_ACCENT_2, 49, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID_ACCENT_2, 50, false, false, false);
        addDefault(StyleIdentifier.LIGHT_SHADING_ACCENT_3, 51, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST_ACCENT_3, 52, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID_ACCENT_3, 46, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_3, 47, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2_ACCENT_3, 48, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1_ACCENT_3, 49, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_2_ACCENT_3, 50, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1_ACCENT_3, 51, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2_ACCENT_3, 52, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3_ACCENT_3, 46, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST_ACCENT_3, 47, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING_ACCENT_3, 48, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST_ACCENT_3, 49, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID_ACCENT_3, 50, false, false, false);
        addDefault(StyleIdentifier.LIGHT_SHADING_ACCENT_4, 51, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST_ACCENT_4, 52, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID_ACCENT_4, 46, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_4, 47, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2_ACCENT_4, 48, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1_ACCENT_4, 49, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_2_ACCENT_4, 50, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1_ACCENT_4, 51, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2_ACCENT_4, 52, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3_ACCENT_4, 46, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST_ACCENT_4, 47, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING_ACCENT_4, 48, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST_ACCENT_4, 49, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID_ACCENT_4, 50, false, false, false);
        addDefault(StyleIdentifier.LIGHT_SHADING_ACCENT_5, 51, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST_ACCENT_5, 52, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID_ACCENT_5, 46, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_5, 47, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2_ACCENT_5, 48, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1_ACCENT_5, 49, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_2_ACCENT_5, 50, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1_ACCENT_5, 51, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2_ACCENT_5, 52, false, false, false);
    }

    private void initToWord2019Default()
    {
        mItems.clear();

        addDefault(StyleIdentifier.NORMAL, 0, false, false, true);
        addDefault(StyleIdentifier.HEADING_1, 0, false, false, true);
        addDefault(StyleIdentifier.HEADING_2, 0, true, true, true);
        addDefault(StyleIdentifier.HEADING_3, 0, true, true, true);
        addDefault(StyleIdentifier.HEADING_4, 0, true, true, true);
        addDefault(StyleIdentifier.HEADING_5, 0, true, true, true);
        addDefault(StyleIdentifier.HEADING_6, 0, true, true, true);
        addDefault(StyleIdentifier.HEADING_7, 0, true, true, true);
        addDefault(StyleIdentifier.HEADING_8, 0, true, true, true);
        addDefault(StyleIdentifier.HEADING_9, 0, true, true, true);
        addDefault(StyleIdentifier.INDEX_1, 0, false, false, false);
        addDefault(StyleIdentifier.INDEX_2, 0, false, false, false);
        addDefault(StyleIdentifier.INDEX_3, 0, false, false, false);
        addDefault(StyleIdentifier.INDEX_4, 0, false, false, false);
        addDefault(StyleIdentifier.INDEX_5, 0, false, false, false);
        addDefault(StyleIdentifier.INDEX_6, 0, false, false, false);
        addDefault(StyleIdentifier.INDEX_7, 0, false, false, false);
        addDefault(StyleIdentifier.INDEX_8, 0, false, false, false);
        addDefault(StyleIdentifier.INDEX_9, 0, false, false, false);
        addDefault(StyleIdentifier.TOC_1, 0, false, false, false);
        addDefault(StyleIdentifier.TOC_2, 0, false, false, false);
        addDefault(StyleIdentifier.TOC_3, 0, false, false, false);
        addDefault(StyleIdentifier.TOC_4, 0, false, false, false);
        addDefault(StyleIdentifier.TOC_5, 0, false, false, false);
        addDefault(StyleIdentifier.TOC_6, 0, false, false, false);
        addDefault(StyleIdentifier.TOC_7, 0, false, false, false);
        addDefault(StyleIdentifier.TOC_8, 0, false, false, false);
        addDefault(StyleIdentifier.TOC_9, 0, false, false, false);
        addDefault(StyleIdentifier.NORMAL_INDENT, 0, false, false, false);
        addDefault(StyleIdentifier.FOOTNOTE_TEXT, 0, false, false, false);
        addDefault(StyleIdentifier.COMMENT_TEXT, 0, false, false, false);
        addDefault(StyleIdentifier.HEADER, 0, false, false, false);
        addDefault(StyleIdentifier.FOOTER, 0, false, false, false);
        addDefault(StyleIdentifier.INDEX_HEADING, 0, false, false, false);
        addDefault(StyleIdentifier.CAPTION, 0, true, true, true);
        addDefault(StyleIdentifier.TABLE_OF_FIGURES, 0, false, false, false);
        addDefault(StyleIdentifier.ENVELOPE_ADDRESS, 0, false, false, false);
        addDefault(StyleIdentifier.ENVELOPE_RETURN, 0, false, false, false);
        addDefault(StyleIdentifier.FOOTNOTE_REFERENCE, 0, false, false, false);
        addDefault(StyleIdentifier.COMMENT_REFERENCE, 0, false, false, false);
        addDefault(StyleIdentifier.LINE_NUMBER, 0, false, false, false);
        addDefault(StyleIdentifier.PAGE_NUMBER, 0, false, false, false);
        addDefault(StyleIdentifier.ENDNOTE_REFERENCE, 0, false, false, false);
        addDefault(StyleIdentifier.ENDNOTE_TEXT, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_OF_AUTHORITIES, 0, false, false, false);
        addDefault(StyleIdentifier.MACRO, 0, false, false, false);
        addDefault(StyleIdentifier.TOA_HEADING, 0, false, false, false);
        addDefault(StyleIdentifier.LIST, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_BULLET, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_NUMBER, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_2, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_3, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_4, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_5, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_BULLET_2, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_BULLET_3, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_BULLET_4, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_BULLET_5, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_NUMBER_2, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_NUMBER_3, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_NUMBER_4, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_NUMBER_5, 0, false, false, false);
        addDefault(StyleIdentifier.TITLE, 0, false, false, true);
        addDefault(StyleIdentifier.CLOSING, 0, false, false, false);
        addDefault(StyleIdentifier.SIGNATURE, 0, false, false, false);
        addDefault(StyleIdentifier.DEFAULT_PARAGRAPH_FONT, 0, false, false, false);
        addDefault(StyleIdentifier.BODY_TEXT, 0, false, false, false);
        addDefault(StyleIdentifier.BODY_TEXT_IND, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_CONTINUE, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_CONTINUE_2, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_CONTINUE_3, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_CONTINUE_4, 0, false, false, false);
        addDefault(StyleIdentifier.LIST_CONTINUE_5, 0, false, false, false);
        addDefault(StyleIdentifier.MESSAGE_HEADER, 0, false, false, false);
        addDefault(StyleIdentifier.SUBTITLE, 0, false, false, true);
        addDefault(StyleIdentifier.SALUTATION, 0, false, false, false);
        addDefault(StyleIdentifier.DATE, 0, false, false, false);
        addDefault(StyleIdentifier.BODY_TEXT_1_I, 0, false, false, false);
        addDefault(StyleIdentifier.BODY_TEXT_1_I_2, 0, false, false, false);
        addDefault(StyleIdentifier.NOTE_HEADING, 0, false, false, false);
        addDefault(StyleIdentifier.BODY_TEXT_2, 0, false, false, false);
        addDefault(StyleIdentifier.BODY_TEXT_3, 0, false, false, false);
        addDefault(StyleIdentifier.BODY_TEXT_IND_2, 0, false, false, false);
        addDefault(StyleIdentifier.BODY_TEXT_IND_3, 0, false, false, false);
        addDefault(StyleIdentifier.BLOCK_TEXT, 0, false, false, false);
        addDefault(StyleIdentifier.HYPERLINK, 0, false, false, false);
        addDefault(StyleIdentifier.FOLLOWED_HYPERLINK, 0, false, false, false);
        addDefault(StyleIdentifier.STRONG, 0, false, false, true);
        addDefault(StyleIdentifier.EMPHASIS, 0, false, false, true);
        addDefault(StyleIdentifier.DOCUMENT_MAP, 0, false, false, false);
        addDefault(StyleIdentifier.PLAIN_TEXT, 0, false, false, false);
        addDefault(StyleIdentifier.EMAIL_SIGNATURE, 0, false, false, false);
        addDefault(StyleIdentifier.HTML_TOP_OF_FORM, 0, false, false, false);
        addDefault(StyleIdentifier.HTML_BOTTOM_OF_FORM, 0, false, false, false);
        addDefault(StyleIdentifier.NORMAL_WEB, 0, false, false, false);
        addDefault(StyleIdentifier.HTML_ACRONYM, 0, false, false, false);
        addDefault(StyleIdentifier.HTML_ADDRESS, 0, false, false, false);
        addDefault(StyleIdentifier.HTML_CITE, 0, false, false, false);
        addDefault(StyleIdentifier.HTML_CODE, 0, false, false, false);
        addDefault(StyleIdentifier.HTML_DEFINITION, 0, false, false, false);
        addDefault(StyleIdentifier.HTML_KEYBOARD, 0, false, false, false);
        addDefault(StyleIdentifier.HTML_PREFORMATTED, 0, false, false, false);
        addDefault(StyleIdentifier.HTML_SAMPLE, 0, false, false, false);
        addDefault(StyleIdentifier.HTML_TYPEWRITER, 0, false, false, false);
        addDefault(StyleIdentifier.HTML_VARIABLE, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_NORMAL, 0, false, false, false);
        addDefault(StyleIdentifier.COMMENT_SUBJECT, 0, false, false, false);
        addDefault(StyleIdentifier.NO_LIST, 0, false, false, false);
        addDefault(StyleIdentifier.OUTLINE_LIST_1, 0, false, false, false);
        addDefault(StyleIdentifier.OUTLINE_LIST_2, 0, false, false, false);
        addDefault(StyleIdentifier.OUTLINE_LIST_3, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_SIMPLE_1, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_SIMPLE_2, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_SIMPLE_3, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_CLASSIC_1, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_CLASSIC_2, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_CLASSIC_3, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_CLASSIC_4, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_COLORFUL_1, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_COLORFUL_2, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_COLORFUL_3, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_COLUMNS_1, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_COLUMNS_2, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_COLUMNS_3, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_COLUMNS_4, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_COLUMNS_5, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_GRID_1, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_GRID_2, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_GRID_3, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_GRID_4, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_GRID_5, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_GRID_6, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_GRID_7, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_GRID_8, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_LIST_1, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_LIST_2, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_LIST_3, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_LIST_4, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_LIST_5, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_LIST_6, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_LIST_7, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_LIST_8, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_3_D_EFFECTS_1, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_3_D_EFFECTS_2, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_3_D_EFFECTS_3, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_CONTEMPORARY, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_ELEGANT, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_PROFESSIONAL, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_SUBTLE_1, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_SUBTLE_2, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_WEB_1, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_WEB_2, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_WEB_3, 0, false, false, false);
        addDefault(StyleIdentifier.BALLOON_TEXT, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_GRID, 0, false, false, false);
        addDefault(StyleIdentifier.TABLE_THEME, 0, false, false, false);
        addDefault(StyleIdentifier.PLACEHOLDER_TEXT, 99, true, false, false);
        addDefault(StyleIdentifier.NO_SPACING, 1, false, false, true);
        addDefault(StyleIdentifier.LIGHT_SHADING, 60, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST, 61, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID, 62, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1, 63, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2, 64, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1, 65, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_2, 66, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1, 67, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2, 68, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3, 69, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST, 70, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING, 71, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST, 72, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID, 73, false, false, false);
        addDefault(StyleIdentifier.LIGHT_SHADING_ACCENT_1, 60, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST_ACCENT_1, 61, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID_ACCENT_1, 62, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_1, 63, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2_ACCENT_1, 64, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1_ACCENT_1, 65, false, false, false);
        addDefault(StyleIdentifier.REVISION, 99, true, false, false);
        addDefault(StyleIdentifier.LIST_PARAGRAPH, 34, false, false, true);
        addDefault(StyleIdentifier.QUOTE, 29, false, false, true);
        addDefault(StyleIdentifier.INTENSE_QUOTE, 30, false, false, true);
        addDefault(StyleIdentifier.MEDIUM_LIST_2_ACCENT_1, 66, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1_ACCENT_1, 67, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2_ACCENT_1, 68, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3_ACCENT_1, 69, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST_ACCENT_1, 70, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING_ACCENT_1, 71, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST_ACCENT_1, 72, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID_ACCENT_1, 73, false, false, false);
        addDefault(StyleIdentifier.LIGHT_SHADING_ACCENT_2, 60, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST_ACCENT_2, 61, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID_ACCENT_2, 62, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_2, 63, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2_ACCENT_2, 64, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1_ACCENT_2, 65, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_2_ACCENT_2, 66, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1_ACCENT_2, 67, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2_ACCENT_2, 68, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3_ACCENT_2, 69, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST_ACCENT_2, 70, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING_ACCENT_2, 71, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST_ACCENT_2, 72, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID_ACCENT_2, 73, false, false, false);
        addDefault(StyleIdentifier.LIGHT_SHADING_ACCENT_3, 60, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST_ACCENT_3, 61, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID_ACCENT_3, 62, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_3, 63, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2_ACCENT_3, 64, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1_ACCENT_3, 65, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_2_ACCENT_3, 66, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1_ACCENT_3, 67, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2_ACCENT_3, 68, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3_ACCENT_3, 69, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST_ACCENT_3, 70, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING_ACCENT_3, 71, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST_ACCENT_3, 72, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID_ACCENT_3, 73, false, false, false);
        addDefault(StyleIdentifier.LIGHT_SHADING_ACCENT_4, 60, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST_ACCENT_4, 61, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID_ACCENT_4, 62, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_4, 63, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2_ACCENT_4, 64, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1_ACCENT_4, 65, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_2_ACCENT_4, 66, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1_ACCENT_4, 67, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2_ACCENT_4, 68, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3_ACCENT_4, 69, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST_ACCENT_4, 70, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING_ACCENT_4, 71, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST_ACCENT_4, 72, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID_ACCENT_4, 73, false, false, false);
        addDefault(StyleIdentifier.LIGHT_SHADING_ACCENT_5, 60, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST_ACCENT_5, 61, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID_ACCENT_5, 62, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_5, 63, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2_ACCENT_5, 64, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1_ACCENT_5, 65, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_2_ACCENT_5, 66, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1_ACCENT_5, 67, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2_ACCENT_5, 68, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3_ACCENT_5, 69, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST_ACCENT_5, 70, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING_ACCENT_5, 71, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST_ACCENT_5, 72, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID_ACCENT_5, 73, false, false, false);
        addDefault(StyleIdentifier.LIGHT_SHADING_ACCENT_6, 60, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST_ACCENT_6, 61, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID_ACCENT_6, 62, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_6, 63, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2_ACCENT_6, 64, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1_ACCENT_6, 65, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_2_ACCENT_6, 66, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1_ACCENT_6, 67, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2_ACCENT_6, 68, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3_ACCENT_6, 69, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST_ACCENT_6, 70, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING_ACCENT_6, 71, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST_ACCENT_6, 72, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID_ACCENT_6, 73, false, false, false);
        addDefault(StyleIdentifier.SUBTLE_EMPHASIS, 19, false, false, true);
        addDefault(StyleIdentifier.INTENSE_EMPHASIS, 21, false, false, true);
        addDefault(StyleIdentifier.SUBTLE_REFERENCE, 31, false, false, true);
        addDefault(StyleIdentifier.INTENSE_REFERENCE, 32, false, false, true);
        addDefault(StyleIdentifier.BOOK_TITLE, 33, false, false, true);
        addDefault(StyleIdentifier.BIBLIOGRAPHY, 37, true, true, false);
        addDefault(StyleIdentifier.TOC_HEADING, 39, true, true, true);
    }

    private void addCommonDefaults()
    {
        addDefault(StyleIdentifier.PLACEHOLDER_TEXT, 99, true, false, false);
        addDefault(StyleIdentifier.NO_SPACING, 1, false, false, true);
        addDefault(StyleIdentifier.LIGHT_SHADING, 60, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST, 61, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID, 62, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1, 63, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2, 64, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1, 65, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_2, 66, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1, 67, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2, 68, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3, 69, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST, 70, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING, 71, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST, 72, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID, 73, false, false, false);
        addDefault(StyleIdentifier.LIGHT_SHADING_ACCENT_1, 60, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST_ACCENT_1, 61, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID_ACCENT_1, 62, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_1, 63, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2_ACCENT_1, 64, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1_ACCENT_1, 65, false, false, false);
        addDefault(StyleIdentifier.REVISION, 99, true, false, false);
        addDefault(StyleIdentifier.LIST_PARAGRAPH, 34, false, false, true);
        addDefault(StyleIdentifier.QUOTE, 29, false, false, true);
        addDefault(StyleIdentifier.INTENSE_QUOTE, 30, false, false, true);
        addDefault(StyleIdentifier.MEDIUM_LIST_2_ACCENT_1, 66, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1_ACCENT_1, 67, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2_ACCENT_1, 68, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3_ACCENT_1, 69, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST_ACCENT_1, 70, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING_ACCENT_1, 71, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST_ACCENT_1, 72, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID_ACCENT_1, 73, false, false, false);
        addDefault(StyleIdentifier.LIGHT_SHADING_ACCENT_2, 60, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST_ACCENT_2, 61, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID_ACCENT_2, 62, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_2, 63, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2_ACCENT_2, 64, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1_ACCENT_2, 65, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_2_ACCENT_2, 66, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1_ACCENT_2, 67, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2_ACCENT_2, 68, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3_ACCENT_2, 69, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST_ACCENT_2, 70, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING_ACCENT_2, 71, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST_ACCENT_2, 72, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID_ACCENT_2, 73, false, false, false);
        addDefault(StyleIdentifier.LIGHT_SHADING_ACCENT_3, 60, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST_ACCENT_3, 61, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID_ACCENT_3, 62, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_3, 63, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2_ACCENT_3, 64, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1_ACCENT_3, 65, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_2_ACCENT_3, 66, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1_ACCENT_3, 67, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2_ACCENT_3, 68, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3_ACCENT_3, 69, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST_ACCENT_3, 70, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING_ACCENT_3, 71, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST_ACCENT_3, 72, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID_ACCENT_3, 73, false, false, false);
        addDefault(StyleIdentifier.LIGHT_SHADING_ACCENT_4, 60, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST_ACCENT_4, 61, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID_ACCENT_4, 62, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_4, 63, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2_ACCENT_4, 64, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1_ACCENT_4, 65, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_2_ACCENT_4, 66, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1_ACCENT_4, 67, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2_ACCENT_4, 68, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3_ACCENT_4, 69, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST_ACCENT_4, 70, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING_ACCENT_4, 71, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST_ACCENT_4, 72, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID_ACCENT_4, 73, false, false, false);
        addDefault(StyleIdentifier.LIGHT_SHADING_ACCENT_5, 60, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST_ACCENT_5, 61, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID_ACCENT_5, 62, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_5, 63, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2_ACCENT_5, 64, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1_ACCENT_5, 65, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_2_ACCENT_5, 66, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1_ACCENT_5, 67, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2_ACCENT_5, 68, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3_ACCENT_5, 69, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST_ACCENT_5, 70, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING_ACCENT_5, 71, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST_ACCENT_5, 72, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID_ACCENT_5, 73, false, false, false);
        addDefault(StyleIdentifier.LIGHT_SHADING_ACCENT_6, 60, false, false, false);
        addDefault(StyleIdentifier.LIGHT_LIST_ACCENT_6, 61, false, false, false);
        addDefault(StyleIdentifier.LIGHT_GRID_ACCENT_6, 62, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_6, 63, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_SHADING_2_ACCENT_6, 64, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_1_ACCENT_6, 65, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_LIST_2_ACCENT_6, 66, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_1_ACCENT_6, 67, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_2_ACCENT_6, 68, false, false, false);
        addDefault(StyleIdentifier.MEDIUM_GRID_3_ACCENT_6, 69, false, false, false);
        addDefault(StyleIdentifier.DARK_LIST_ACCENT_6, 70, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_SHADING_ACCENT_6, 71, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_LIST_ACCENT_6, 72, false, false, false);
        addDefault(StyleIdentifier.COLORFUL_GRID_ACCENT_6, 73, false, false, false);
        addDefault(StyleIdentifier.SUBTLE_EMPHASIS, 19, false, false, true);
        addDefault(StyleIdentifier.INTENSE_EMPHASIS, 21, false, false, true);
        addDefault(StyleIdentifier.SUBTLE_REFERENCE, 31, false, false, true);
        addDefault(StyleIdentifier.INTENSE_REFERENCE, 32, false, false, true);
        addDefault(StyleIdentifier.BOOK_TITLE, 33, false, false, true);
        addDefault(StyleIdentifier.BIBLIOGRAPHY, 37, true, true, false);
        addDefault(StyleIdentifier.TOC_HEADING, 39, true, true, true);
    }

    private boolean equals(LatentStyles defLatentStyles)
    {
        if (defLatentStyles.getCount() != getCount())
            return false;

        for (int i = 0; i < getCount(); i++)
        {
            if (!defLatentStyles.get(i).equals(this.get(i)))
                return false;
        }

        return true;
    }

    private void addDefault(/*StyleIdentifier*/int sti, int uiPriority, boolean semiHidden, boolean unhideWhenUsed, boolean quickFormat)
    {
        add(new LatentStyle(sti, false, quickFormat, semiHidden, uiPriority, unhideWhenUsed));
    }

    /// <summary>
    /// Key is <see cref="StyleIdentifier"/>, item is <see cref="LatentStyle"/>.
    /// </summary>
    private SortedIntegerListGeneric<LatentStyle> mItems = new SortedIntegerListGeneric<LatentStyle>();

    private boolean mDefaultLockedState;
    private boolean mDefaultQuickFormat;
    private boolean mDefaultSemiHidden = true;
    private int mDefaultUIPriority = 99;
    private boolean mDefaultUnhideWhenUsed = true;
    private int mKnownStylesCount = StyleCollection.MAX_KNOWN_STI_2007;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

