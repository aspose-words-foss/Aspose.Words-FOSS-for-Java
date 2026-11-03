// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/02/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.BitUtil;

/// <summary>
/// Encapsulates different settings that are defined for 
/// stylePaneFormatFilter (Suggested Filtering for List of Document Styles).
/// ISO 29500 feature.
/// </summary>

class StylePaneFormatFilterSettings
{
    /// <summary>
    /// default ctor.
    /// </summary>
    StylePaneFormatFilterSettings()
    {
        mStylePaneFormatFilterFlags = STYLE_PANE_FORMAT_FILTER_DEFAULT;
    }

    StylePaneFormatFilterSettings(int defaultFlags)
    {
        mStylePaneFormatFilterFlags = defaultFlags;
    }
    
    /// <summary>
    /// Returns int32 flag containing all filter settings as bits.
    /// </summary>
    int getData() { return mStylePaneFormatFilterFlags; }
    void setData(int value) { mStylePaneFormatFilterFlags = value; }
    
    /// <summary>
    /// (Display All Styles). Specifies that all styles present in the styles part 
    /// should be displayed in the list of document styles.
    /// </summary>
    boolean getAllStyles() { return BitUtil.isSetInt32(mStylePaneFormatFilterFlags, ALL_STYLES_BIT); }
    void setAllStyles(boolean value) { setFlag(ALL_STYLES_BIT, value); }
    
    /// <summary>
    /// (Display Only Custom Styles). Specifies that only styles with the customStyle 
    /// attribute should be displayed in the list of document styles.
    /// </summary>
    boolean getCustomStyles() { return BitUtil.isSetInt32(mStylePaneFormatFilterFlags,CUSTOM_STYLES_BIT); }
    void setCustomStyles(boolean value) { setFlag(CUSTOM_STYLES_BIT, value); }
    
    /// <summary>
    /// (Display Latent Styles). Specifies that all latent styles should be 
    /// displayed in the list of document styles.
    /// </summary>
    boolean getLatentStyles() { return BitUtil.isSetInt32(mStylePaneFormatFilterFlags, LATENT_STYLES_BIT); }
    void setLatentStyles(boolean value) { setFlag(LATENT_STYLES_BIT, value); }
    
    /// <summary>
    /// (Display Styles in Use). Specifies that only styles used in the 
    /// document should be displayed in the list of document styles.
    /// </summary>
    boolean getStylesInUse() { return BitUtil.isSetInt32(mStylePaneFormatFilterFlags, STYLES_IN_USE_BIT); }
    void setStylesInUse(boolean value) { setFlag(STYLES_IN_USE_BIT, value); }
    
    /// <summary>
    /// (Display Heading Styles). Specifies that heading styles 
    /// (styles with a styleId of Heading1 to Heading9) should be displayed 
    /// in the list of document styles when the previous style is used in the
    /// document and/or is present in the Styles part.
    /// </summary>
    boolean getHeadingStyles() { return BitUtil.isSetInt32(mStylePaneFormatFilterFlags, HEADING_STYLES_BIT); }
    void setHeadingStyles(boolean value) { setFlag(HEADING_STYLES_BIT, value); }
    
    /// <summary>
    /// (Display NumberingStyles). Specifies that numbering styles should be displayed
    /// in the list of document styles.
    /// </summary>
    boolean getNumberingStyles() { return BitUtil.isSetInt32(mStylePaneFormatFilterFlags, NUMBERING_STYLES_BIT); }
    void setNumberingStyles(boolean value) { setFlag(NUMBERING_STYLES_BIT, value); }
    
    /// <summary>
    /// (Display Table Styles). Specifies that table styles should be displayed 
    /// in the list of document styles.
    /// </summary>
    boolean getTableStyles() { return BitUtil.isSetInt32(mStylePaneFormatFilterFlags, TABLE_STYLES_BIT); }
    void setTableStyles(boolean value) { setFlag(TABLE_STYLES_BIT, value); }
    
    /// <summary>
    /// (Display Run Level Direct Formatting). Specifies that all unique forms 
    /// of run-level direct formatting should be displayed in the list of document 
    /// styles as though they were each a unique style.
    /// </summary>
    boolean getDirectFormattingOnRuns() { return BitUtil.isSetInt32(mStylePaneFormatFilterFlags, DIRECT_FORMATTING_ON_RUNS_BIT); }
    void setDirectFormattingOnRuns(boolean value) { setFlag(DIRECT_FORMATTING_ON_RUNS_BIT, value); }
    
    /// <summary>
    /// (Display Paragraph Level Direct Formatting). Specifies that all
    /// unique forms of paragraph-level direct formatting should be displayed 
    /// in the list of document styles as though they were each a unique style.
    /// </summary>
    boolean getDirectFormattingOnParagraphs() { return BitUtil.isSetInt32(mStylePaneFormatFilterFlags, DIRECT_FORMATTING_ON_PARAGRAPHS_BIT); }
    void setDirectFormattingOnParagraphs(boolean value) { setFlag(DIRECT_FORMATTING_ON_PARAGRAPHS_BIT, value); }
    
    /// <summary>
    /// (Display Direct Formatting on Numbering Data). Specifies that all unique forms
    /// of direct formatting of numbering data should be displayed in the list of document 
    /// styles as though they were each a unique style.
    /// </summary>
    boolean getDirectFormattingOnNumbering() { return BitUtil.isSetInt32(mStylePaneFormatFilterFlags, DIRECT_FORMATTING_ON_NUMBERING_BIT); }
    void setDirectFormattingOnNumbering(boolean value) { setFlag(DIRECT_FORMATTING_ON_NUMBERING_BIT, value); }

    /// <summary>
    /// (Display Direct Formatting on Tables). Specifies that all unique forms of direct 
    /// formatting of tables should be displayed in the list of document styles as 
    /// though they were each a unique style.
    /// </summary>
    boolean getDirectFormattingOnTables() { return BitUtil.isSetInt32(mStylePaneFormatFilterFlags, DIRECT_FORMATTING_ON_TABLES_BIT); }
    void setDirectFormattingOnTables(boolean value) { setFlag(DIRECT_FORMATTING_ON_TABLES_BIT, value); }
    
    /// <summary>
    /// (Display Styles to Remove Formatting). Specifies that a style should be
    /// present which removes all formatting and styles from text.
    /// </summary>
    boolean getClearFormatting() { return BitUtil.isSetInt32(mStylePaneFormatFilterFlags, CLEAR_FORMATTING_BIT); }
    void setClearFormatting(boolean value) { setFlag(CLEAR_FORMATTING_BIT, value); }
    
    /// <summary>
    /// (Display Heading 1 through 3).Specifies that heading styles with a 
    /// styleId of Heading1 to Heading3 should always be displayed in the 
    /// list of document styles.
    /// </summary>
    boolean getTop3HeadingStyles() { return BitUtil.isSetInt32(mStylePaneFormatFilterFlags, TOP_3_HEADING_STYLES_BIT); }
    void setTop3HeadingStyles(boolean value) { setFlag(TOP_3_HEADING_STYLES_BIT, value); }
    
    /// <summary>
    /// (Only Show Visible Styles). Specifies that styles should only be 
    /// shown if the semiHidden element (§17.7.4.16) is false and the hidden element 
    /// (§17.7.4.4) is false.
    /// </summary>
    boolean getVisibleStyles() { return BitUtil.isSetInt32(mStylePaneFormatFilterFlags, VISIBLE_STYLES_BIT); }
    void setVisibleStyles(boolean value) { setFlag(VISIBLE_STYLES_BIT, value); }
    
    /// <summary>
    /// (Use the Alternate Style Name). Specifies that primary names for styles should not be shown if 
    /// an alternate name using the name element (§17.7.4.9) exists.
    /// </summary>
    boolean getAlternateStyleNames() { return BitUtil.isSetInt32(mStylePaneFormatFilterFlags, ALTERNATE_STYLE_NAMES_BIT); }
    void setAlternateStyleNames(boolean value) { setFlag(ALTERNATE_STYLE_NAMES_BIT, value); }

    private void setFlag(int flag, boolean value)
    {
        mStylePaneFormatFilterFlags = BitUtil.setBit(mStylePaneFormatFilterFlags, flag, value);
    }

    private int mStylePaneFormatFilterFlags;

    /// <summary>
    /// RK While the spec says default value is 0, it seems MS Word does not write this value to DOCX
    /// when it equals 0x5024, therefore I use it as a default.
    /// </summary>
    static final int STYLE_PANE_FORMAT_FILTER_DEFAULT = 0x5024;

    private static final int ALL_STYLES_BIT = 0x0001;
    private static final int CUSTOM_STYLES_BIT = 0x0002;
    private static final int LATENT_STYLES_BIT = 0x0004;
    private static final int STYLES_IN_USE_BIT = 0x0008;
    private static final int HEADING_STYLES_BIT = 0x0020;
    private static final int NUMBERING_STYLES_BIT = 0x0040;
    private static final int TABLE_STYLES_BIT = 0x0080;
    private static final int DIRECT_FORMATTING_ON_RUNS_BIT = 0x0100;
    private static final int DIRECT_FORMATTING_ON_PARAGRAPHS_BIT = 0x0200;
    private static final int DIRECT_FORMATTING_ON_NUMBERING_BIT = 0x0400;
    private static final int DIRECT_FORMATTING_ON_TABLES_BIT = 0x0800;
    private static final int CLEAR_FORMATTING_BIT = 0x1000;
    private static final int TOP_3_HEADING_STYLES_BIT = 0x2000;
    private static final int VISIBLE_STYLES_BIT = 0x4000;
    private static final int ALTERNATE_STYLE_NAMES_BIT = 0x8000;
}

