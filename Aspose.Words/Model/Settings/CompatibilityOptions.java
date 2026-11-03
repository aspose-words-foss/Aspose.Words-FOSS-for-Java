// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/05/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msDictionary;
import java.util.HashMap;
import java.util.Map;
import com.aspose.words.ref.RefBoolean;


/// <summary>
/// Contains compatibility options (that is, the user preferences entered on the <b>Compatibility</b>
/// tab of the <b>Options</b> dialog in Microsoft Word).
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/detect-file-format-and-check-format-compatibility/">Detect File Format and Check Format Compatibility</a> documentation article.</para>
/// </summary>
/// <seealso cref="Document"/>
/// <seealso cref="Document.CompatibilityOptions"/>
public class CompatibilityOptions implements Cloneable
{
    /// <summary>
    /// <para>Allows to optimize the document contents as well as default Aspose.Words behavior to a particular versions of MS Word.</para>
    /// <para>Use this method to prevent MS Word from displaying "Compatibility mode" ribbon upon document loading.
    /// (Note that you may also need to set the <see cref="OoxmlSaveOptions.Compliance"/> property to
    /// <see cref="OoxmlCompliance.Iso29500_2008_Transitional"/> or higher.)
    /// </para>
    /// </summary>
    public void optimizeFor(/*MsWordVersion*/int version)
    {
        clear();
        switch (version)
        {
            case MsWordVersion.WORD_2000:
                mMsWordVersion = MsWordVersionCore.WORD_2000;
                initWord2000Options(this);
                break;
            case MsWordVersion.WORD_2002:
                mMsWordVersion = MsWordVersionCore.WORD_2002;
                initWord2002Options(this);
                break;
            case MsWordVersion.WORD_2003:
                mMsWordVersion = MsWordVersionCore.WORD_2003;
                initWord2003Options(this);
                break;
            case MsWordVersion.WORD_2007:
                mMsWordVersion = MsWordVersionCore.WORD_2007;
                // 2007 options already set by default.
                break;
            case MsWordVersion.WORD_2010:
                mMsWordVersion = MsWordVersionCore.WORD_2010;
                initWord2010Options(this);
                break;
            case MsWordVersion.WORD_2013:
                mMsWordVersion = MsWordVersionCore.WORD_2013;
                initWord2013Options(this);
                break;
            case MsWordVersion.WORD_2016:
                mMsWordVersion = MsWordVersionCore.WORD_2016;
                initWord2016Options(this);
                break;
            case MsWordVersion.WORD_2019:
                mMsWordVersion = MsWordVersionCore.WORD_2019;
                initWord2019Options(this);
                break;
            default:
                // This should never happen, so report it if it does.
                throw new UnsupportedOperationException("Please report exception.");
        }

        mIsOptimized = true;
    }

    /// <summary>
    /// Ctor. The options are initialized to Word 2007 default values.
    /// </summary>
    CompatibilityOptions()
    {
        initInheritedCompatibilityOptions();
    }

    private void setProperty(/*Compatibility*/int key, boolean value)
    {
        mCompatibilityOptions.put(key, value);
    }

    private boolean getProperty(/*Compatibility*/int key)
    {
        boolean value = false;

        RefBoolean referenceToValue = new RefBoolean(value);
        boolean outRefCondition0 = msDictionary.tryGetValue(mCompatibilityOptions, key, /*out*/ referenceToValue);
        value = referenceToValue.get();
        if (outRefCondition0)
            return value;

        referenceToValue.set(value);
        boolean outRefCondition1 = (mCompatibilityOptions.size() == 0) &&
            (msDictionary.tryGetValue(mInheritedCompatibilityOptions, key, /*out*/ referenceToValue));
        value = referenceToValue.get();
        if (outRefCondition1)
            return value;

        return false;
    }

    CompatibilityOptions deepClone()
    {
        CompatibilityOptions lhs = (CompatibilityOptions)memberwiseClone();
        lhs.mCustomSettings = mCustomSettings.deepClone();
        return lhs;
    }

    /// <summary>
    /// Do Not Create Custom Tab Stop for Hanging Indent.
    /// </summary>
    public boolean getNoTabHangInd() { return getProperty(Compatibility.NO_TAB_HANG_IND); }
    public void setNoTabHangInd(boolean value) { setProperty(Compatibility.NO_TAB_HANG_IND, value); }

    /// <summary>
    /// Do Not Increase Line Height for Raised/Lowered Text.
    /// </summary>
    public boolean getNoSpaceRaiseLower() { return getProperty(Compatibility.NO_SPACE_RAISE_LOWER); }
    public void setNoSpaceRaiseLower(boolean value) { setProperty(Compatibility.NO_SPACE_RAISE_LOWER, value); }

    /// <summary>
    /// Do Not Use Space Before On First Line After a Page Break.
    /// </summary>
    public boolean getSuppressSpBfAfterPgBrk() { return getProperty(Compatibility.SUPPRESS_SP_BF_AFTER_PG_BRK); }
    public void setSuppressSpBfAfterPgBrk(boolean value) { setProperty(Compatibility.SUPPRESS_SP_BF_AFTER_PG_BRK, value); }

    /// <summary>
    /// Line Wrap Trailing Spaces.
    /// </summary>
    public boolean getWrapTrailSpaces() { return getProperty(Compatibility.WRAP_TRAIL_SPACES); }
    public void setWrapTrailSpaces(boolean value) { setProperty(Compatibility.WRAP_TRAIL_SPACES, value); }

    /// <summary>
    /// Print Colors as Black And White without Dithering.
    /// </summary>
    public boolean getPrintColBlack() { return getProperty(Compatibility.PRINT_COL_BLACK); }
    public void setPrintColBlack(boolean value) { setProperty(Compatibility.PRINT_COL_BLACK, value); }

    /// <summary>
    /// Do Not Balance Text Columns within a Section.
    /// </summary>
    public boolean getNoColumnBalance() { return getProperty(Compatibility.NO_COLUMN_BALANCE); }
    public void setNoColumnBalance(boolean value) { setProperty(Compatibility.NO_COLUMN_BALANCE, value); }

    /// <summary>
    /// Treat Backslash Quotation Delimiter as Two Quotation Marks.
    /// </summary>
    public boolean getConvMailMergeEsc() { return getProperty(Compatibility.CONV_MAIL_MERGE_ESC); }
    public void setConvMailMergeEsc(boolean value) { setProperty(Compatibility.CONV_MAIL_MERGE_ESC, value); }

    /// <summary>
    /// Ignore Minimum and Exact Line Height for First Line on Page.
    /// </summary>
    public boolean getSuppressTopSpacing() { return getProperty(Compatibility.SUPPRESS_TOP_SPACING); }
    public void setSuppressTopSpacing(boolean value) { setProperty(Compatibility.SUPPRESS_TOP_SPACING, value); }

    /// <summary>
    /// Use Simplified Rules For Table Border Conflicts.
    /// </summary>
    public boolean getUseSingleBorderforContiguousCells() { return getProperty(Compatibility.USE_SINGLE_BORDERFOR_CONTIGUOUS_CELLS); }
    public void setUseSingleBorderforContiguousCells(boolean value) { setProperty(Compatibility.USE_SINGLE_BORDERFOR_CONTIGUOUS_CELLS, value); }

    /// <summary>
    /// Specifies not to blank the area behind metafile pictures.
    /// </summary>
    public boolean getTransparentMetafiles() { return getProperty(Compatibility.TRANSPARENT_METAFILES); }
    public void setTransparentMetafiles(boolean value) { setProperty(Compatibility.TRANSPARENT_METAFILES, value); }

    /// <summary>
    /// Display Page/Column Breaks Present in Frames.
    /// </summary>
    public boolean getShowBreaksInFrames() { return getProperty(Compatibility.SHOW_BREAKS_IN_FRAMES); }
    public void setShowBreaksInFrames(boolean value) { setProperty(Compatibility.SHOW_BREAKS_IN_FRAMES, value); }

    /// <summary>
    /// Swap Paragraph Borders on Odd Numbered Pages.
    /// </summary>
    public boolean getSwapBordersFacingPgs() { return getProperty(Compatibility.SWAP_BORDERS_ODD_FACING_PGS); }
    public void setSwapBordersFacingPgs(boolean value) { setProperty(Compatibility.SWAP_BORDERS_ODD_FACING_PGS, value); }

    /// <summary>
    /// Convert Backslash To Yen Sign When Entered.
    /// </summary>
    public boolean getDoNotLeaveBackslashAlone() { return getProperty(Compatibility.DO_NOT_LEAVE_BACKSLASH_ALONE); }
    public void setDoNotLeaveBackslashAlone(boolean value) { setProperty(Compatibility.DO_NOT_LEAVE_BACKSLASH_ALONE, value); }

    /// <summary>
    /// Don't Justify Lines Ending in Soft Line Break.
    /// </summary>
    public boolean getDoNotExpandShiftReturn() { return getProperty(Compatibility.DO_NOT_EXPAND_ON_SHIFT_RETURN); }
    public void setDoNotExpandShiftReturn(boolean value) { setProperty(Compatibility.DO_NOT_EXPAND_ON_SHIFT_RETURN, value); }

    /// <summary>
    /// Underline All Trailing Spaces.
    /// </summary>
    public boolean getUlTrailSpace() { return getProperty(Compatibility.UL_TRAIL_SPACE); }
    public void setUlTrailSpace(boolean value) { setProperty(Compatibility.UL_TRAIL_SPACE, value); }

    /// <summary>
    /// Balance Single Byte and Double Byte Characters.
    /// </summary>
    public boolean getBalanceSingleByteDoubleByteWidth() { return getProperty(Compatibility.BALANCE_SINGLE_BYTE_DOUBLE_BYTE_WIDTH); }
    public void setBalanceSingleByteDoubleByteWidth(boolean value) { setProperty(Compatibility.BALANCE_SINGLE_BYTE_DOUBLE_BYTE_WIDTH, value); }

    /// <summary>
    /// Ignore Minimum Line Height for First Line on Page.
    /// </summary>
    public boolean getSuppressSpacingAtTopOfPage() { return getProperty(Compatibility.SUPPRESS_TOP_SPACING_AT_TOP_OF_PAGE); }
    public void setSuppressSpacingAtTopOfPage(boolean value) { setProperty(Compatibility.SUPPRESS_TOP_SPACING_AT_TOP_OF_PAGE, value); }

    /// <summary>
    /// Only Expand/Condense Text By Whole Points.
    /// </summary>
    public boolean getSpacingInWholePoints() { return getProperty(Compatibility.SPACING_IN_WHOLE_POINTS); }
    public void setSpacingInWholePoints(boolean value) { setProperty(Compatibility.SPACING_IN_WHOLE_POINTS, value); }

    /// <summary>
    /// Print Body Text before Header/Footer Contents.
    /// </summary>
    public boolean getPrintBodyTextBeforeHeader() { return getProperty(Compatibility.PRINT_BODY_TEXT_BEFORE_HEADER); }
    public void setPrintBodyTextBeforeHeader(boolean value) { setProperty(Compatibility.PRINT_BODY_TEXT_BEFORE_HEADER, value); }

    /// <summary>
    /// Do Not Add Leading Between Lines of Text.
    /// </summary>
    public boolean getNoLeading() { return getProperty(Compatibility.NO_LEADING); }
    public void setNoLeading(boolean value) { setProperty(Compatibility.NO_LEADING, value); }

    /// <summary>
    /// Add Additional Space Below Baseline For Underlined East Asian Text.
    /// </summary>
    public boolean getSpaceForUL() { return getProperty(Compatibility.SPACE_FOR_UL); }
    public void setSpaceForUL(boolean value) { setProperty(Compatibility.SPACE_FOR_UL, value); }

    /// <summary>
    /// Emulate Word 5.x for the Macintosh Small Caps Formatting.
    /// </summary>
    public boolean getMWSmallCaps() { return getProperty(Compatibility.MW_SMALL_CAPS); }
    public void setMWSmallCaps(boolean value) { setProperty(Compatibility.MW_SMALL_CAPS, value); }

    /// <summary>
    /// Emulate WordPerfect 5.x Line Spacing.
    /// </summary>
    public boolean getSuppressTopSpacingWP() { return getProperty(Compatibility.SUPPRESS_TOP_LINE_SPACING_WP); }
    public void setSuppressTopSpacingWP(boolean value) { setProperty(Compatibility.SUPPRESS_TOP_LINE_SPACING_WP, value); }

    /// <summary>
    /// Emulate WordPerfect 6.x Font Height Calculation.
    /// </summary>
    public boolean getTruncateFontHeightsLikeWP6() { return getProperty(Compatibility.TRUNCATE_FONT_HEIGHT_LIKE_WP_6); }
    public void setTruncateFontHeightsLikeWP6(boolean value) { setProperty(Compatibility.TRUNCATE_FONT_HEIGHT_LIKE_WP_6, value); }

    /// <summary>
    /// Increase Priority Of Font Size During Font Substitution.
    /// </summary>
    public boolean getSubFontBySize() { return getProperty(Compatibility.SUB_FONT_BY_SIZE); }
    public void setSubFontBySize(boolean value) { setProperty(Compatibility.SUB_FONT_BY_SIZE, value); }

    /// <summary>
    /// Emulate Word 6.0 Line Wrapping for East Asian Text.
    /// </summary>
    public boolean getLineWrapLikeWord6() { return getProperty(Compatibility.LINE_WRAP_LIKE_WORD_6); }
    public void setLineWrapLikeWord6(boolean value) { setProperty(Compatibility.LINE_WRAP_LIKE_WORD_6, value); }

    /// <summary>
    /// Do Not Suppress Paragraph Borders Next To Frames.
    /// </summary>
    public boolean getDoNotSuppressParagraphBorders() { return getProperty(Compatibility.DO_NOT_SUPPRESS_PARAGRAPH_BORDER); }
    public void setDoNotSuppressParagraphBorders(boolean value) { setProperty(Compatibility.DO_NOT_SUPPRESS_PARAGRAPH_BORDER, value); }

    /// <summary>
    /// Do Not Center Content on Lines With Exact Line Height.
    /// </summary>
    public boolean getNoExtraLineSpacing() { return getProperty(Compatibility.NO_EXTRA_LINE_SPACING); }
    public void setNoExtraLineSpacing(boolean value) { setProperty(Compatibility.NO_EXTRA_LINE_SPACING, value); }

    /// <summary>
    /// Ignore Exact Line Height for Last Line on Page.
    /// </summary>
    public boolean getSuppressBottomSpacing() { return getProperty(Compatibility.SUPPRESS_BOTTOM_SPACING); }
    public void setSuppressBottomSpacing(boolean value) { setProperty(Compatibility.SUPPRESS_BOTTOM_SPACING, value); }

    /// <summary>
    /// Specifies whether to set the width of a space as is done in WordPerfect 5.x.
    /// </summary>
    public boolean getWPSpaceWidth() { return getProperty(Compatibility.WP_SPACE_WIDTH); }
    public void setWPSpaceWidth(boolean value) { setProperty(Compatibility.WP_SPACE_WIDTH, value); }

    /// <summary>
    /// Emulate WordPerfect 6.x Paragraph Justification.
    /// </summary>
    public boolean getWPJustification() { return getProperty(Compatibility.WP_JUSTIFICATION); }
    public void setWPJustification(boolean value) { setProperty(Compatibility.WP_JUSTIFICATION, value); }

    /// <summary>
    /// Use Printer Metrics To Display Documents.
    /// </summary>
    /// <remarks>
    /// Printer Metrics may differ depending on drivers used.
    /// For instance, Windows "Microsoft OpenXPS Class Driver 2" and "Microsoft Print to PDF" provide slightly different metrics.
    /// Therefore, the final document's layout may change if this option is enabled.
    /// </remarks>
    public boolean getUsePrinterMetrics() { return getProperty(Compatibility.USE_PRINTER_METRICS); }
    public void setUsePrinterMetrics(boolean value) { setProperty(Compatibility.USE_PRINTER_METRICS, value); }

    /// <summary>
    /// Emulate Word 97 Text Wrapping Around Floating Objects.
    /// </summary>
    public boolean getShapeLayoutLikeWW8() { return getProperty(Compatibility.SHAPE_LAYOUT_LIKE_WW_8); }
    public void setShapeLayoutLikeWW8(boolean value) { setProperty(Compatibility.SHAPE_LAYOUT_LIKE_WW_8, value); }

    /// <summary>
    /// Emulate Word 6.x/95/97 Footnote Placement.
    /// </summary>
    public boolean getFootnoteLayoutLikeWW8() { return getProperty(Compatibility.FOOTNOTE_LAYOUT_LIKE_WW_8); }
    public void setFootnoteLayoutLikeWW8(boolean value) { setProperty(Compatibility.FOOTNOTE_LAYOUT_LIKE_WW_8, value); }

    /// <summary>
    /// Use Fixed Paragraph Spacing for HTML Auto Setting.
    /// </summary>
    public boolean getDoNotUseHTMLParagraphAutoSpacing() { return getProperty(Compatibility.DO_NOT_USE_HTML_PARAGRAPH_AUTO_SPACING); }
    public void setDoNotUseHTMLParagraphAutoSpacing(boolean value) { setProperty(Compatibility.DO_NOT_USE_HTML_PARAGRAPH_AUTO_SPACING, value); }

    /// <summary>
    /// Add Document Grid Line Pitch To Lines in Table Cells.
    /// </summary>
    public boolean getAdjustLineHeightInTable() { return getProperty(Compatibility.ADJUST_LINE_HEIGHT_IN_TABLE); }
    public void setAdjustLineHeightInTable(boolean value) { setProperty(Compatibility.ADJUST_LINE_HEIGHT_IN_TABLE, value); }

    /// <summary>
    /// Ignore Width of Last Tab Stop When Aligning Paragraph If It Is Not Left Aligned.
    /// </summary>
    public boolean getForgetLastTabAlignment() { return getProperty(Compatibility.FORGET_LAST_TAB_ALIGNMENT); }
    public void setForgetLastTabAlignment(boolean value) { setProperty(Compatibility.FORGET_LAST_TAB_ALIGNMENT, value); }

    /// <summary>
    /// Emulate Word 95 Full-Width Character Spacing.
    /// </summary>
    public boolean getAutoSpaceLikeWord95() { return getProperty(Compatibility.AUTO_SPACE_LIKE_WORD_95); }
    public void setAutoSpaceLikeWord95(boolean value) { setProperty(Compatibility.AUTO_SPACE_LIKE_WORD_95, value); }

    /// <summary>
    /// Align Table Rows Independently.
    /// </summary>
    public boolean getAlignTablesRowByRow() { return getProperty(Compatibility.ALIGN_TABLE_ROW_BY_ROW); }
    public void setAlignTablesRowByRow(boolean value) { setProperty(Compatibility.ALIGN_TABLE_ROW_BY_ROW, value); }

    /// <summary>
    /// Ignore Space Before Table When Deciding If Table Should Wrap Floating Object.
    /// </summary>
    public boolean getLayoutRawTableWidth() { return getProperty(Compatibility.LAYOUT_RAW_TABLE_WIDTH); }
    public void setLayoutRawTableWidth(boolean value) { setProperty(Compatibility.LAYOUT_RAW_TABLE_WIDTH, value); }

    /// <summary>
    /// Allow Table Rows to Wrap Inline Objects Independently.
    /// </summary>
    public boolean getLayoutTableRowsApart() { return getProperty(Compatibility.LAYOUT_TABLE_ROWS_APART); }
    public void setLayoutTableRowsApart(boolean value) { setProperty(Compatibility.LAYOUT_TABLE_ROWS_APART, value); }

    /// <summary>
    /// Emulate Word 97 East Asian Line Breaking.
    /// </summary>
    public boolean getUseWord97LineBreakRules() { return getProperty(Compatibility.USE_WORD_97_LINE_BREAK_RULES); }
    public void setUseWord97LineBreakRules(boolean value) { setProperty(Compatibility.USE_WORD_97_LINE_BREAK_RULES, value); }

    /// <summary>
    /// Do Not Allow Floating Tables To Break Across Pages.
    /// </summary>
    public boolean getDoNotBreakWrappedTables() { return getProperty(Compatibility.DO_NOT_BREAK_WRAPPED_TABLES); }
    public void setDoNotBreakWrappedTables(boolean value) { setProperty(Compatibility.DO_NOT_BREAK_WRAPPED_TABLES, value); }

    /// <summary>
    /// Do Not Snap to Document Grid in Table Cells with Objects.
    /// </summary>
    public boolean getDoNotSnapToGridInCell() { return getProperty(Compatibility.DO_NOT_SNAP_TO_GRID_IN_CELL); }
    public void setDoNotSnapToGridInCell(boolean value) { setProperty(Compatibility.DO_NOT_SNAP_TO_GRID_IN_CELL, value); }

    /// <summary>
    /// Select Field When First or Last Character Is Selected.
    /// </summary>
    public boolean getSelectFldWithFirstOrLastChar() { return getProperty(Compatibility.SELECT_FLD_WITH_FIRST_OR_LAST_CHAR); }
    public void setSelectFldWithFirstOrLastChar(boolean value) { setProperty(Compatibility.SELECT_FLD_WITH_FIRST_OR_LAST_CHAR, value); }

    /// <summary>
    /// Use Legacy Ethiopic and Amharic Line Breaking Rules.
    /// </summary>
    public boolean getApplyBreakingRules() { return getProperty(Compatibility.APPLY_BREAKING_RULES); }
    public void setApplyBreakingRules(boolean value) { setProperty(Compatibility.APPLY_BREAKING_RULES, value); }

    /// <summary>
    /// Do Not Allow Hanging Punctuation With Character Grid.
    /// </summary>
    public boolean getDoNotWrapTextWithPunct() { return getProperty(Compatibility.DO_NOT_WRAP_TEXT_WITH_PUNCT); }
    public void setDoNotWrapTextWithPunct(boolean value) { setProperty(Compatibility.DO_NOT_WRAP_TEXT_WITH_PUNCT, value); }

    /// <summary>
    /// Do Not Compress Compressible Characters When Using Document Grid.
    /// </summary>
    public boolean getDoNotUseEastAsianBreakRules() { return getProperty(Compatibility.DO_NOT_USE_EAST_ASIAN_BREAK_RULES); }
    public void setDoNotUseEastAsianBreakRules(boolean value) { setProperty(Compatibility.DO_NOT_USE_EAST_ASIAN_BREAK_RULES, value); }

    /// <summary>
    /// Emulate Word 2002 Table Style Rules.
    /// </summary>
    public boolean getUseWord2002TableStyleRules() { return getProperty(Compatibility.USE_WORD_2002_TABLE_STYLE_RULES); }
    public void setUseWord2002TableStyleRules(boolean value) { setProperty(Compatibility.USE_WORD_2002_TABLE_STYLE_RULES, value); }

    /// <summary>
    /// Allow Tables to AutoFit Into Page Margins.
    /// </summary>
    public boolean getGrowAutofit() { return getProperty(Compatibility.GROW_AUTOFIT); }
    public void setGrowAutofit(boolean value) { setProperty(Compatibility.GROW_AUTOFIT, value); }

    /// <summary>
    /// Do Not Automatically Apply List Paragraph Style To Bulleted/Numbered Text.
    /// </summary>
    public boolean getUseNormalStyleForList() { return getProperty(Compatibility.USE_NORMAL_STYLE_FOR_LIST); }
    public void setUseNormalStyleForList(boolean value) { setProperty(Compatibility.USE_NORMAL_STYLE_FOR_LIST, value); }

    /// <summary>
    /// Ignore Hanging Indent When Creating Tab Stop After Numbering.
    /// </summary>
    public boolean getDoNotUseIndentAsNumberingTabStop() { return getProperty(Compatibility.DO_NOT_USE_INDENT_AS_NUMBERING_TAB_STOP); }
    public void setDoNotUseIndentAsNumberingTabStop(boolean value) { setProperty(Compatibility.DO_NOT_USE_INDENT_AS_NUMBERING_TAB_STOP, value); }

    /// <summary>
    /// Use Alternate Set of East Asian Line Breaking Rules.
    /// </summary>
    public boolean getUseAltKinsokuLineBreakRules() { return getProperty(Compatibility.USE_ALT_KINSOKU_LINE_BREAK_RULES); }
    public void setUseAltKinsokuLineBreakRules(boolean value) { setProperty(Compatibility.USE_ALT_KINSOKU_LINE_BREAK_RULES, value); }

    /// <summary>
    /// Allow Contextual Spacing of Paragraphs in Tables.
    /// </summary>
    public boolean getAllowSpaceOfSameStyleInTable() { return getProperty(Compatibility.ALLOW_SPACE_OF_SAME_STYLE_IN_TABLE); }
    public void setAllowSpaceOfSameStyleInTable(boolean value) { setProperty(Compatibility.ALLOW_SPACE_OF_SAME_STYLE_IN_TABLE, value); }

    /// <summary>
    /// Do Not Ignore Floating Objects When Calculating Paragraph Indentation.
    /// </summary>
    public boolean getDoNotSuppressIndentation() { return getProperty(Compatibility.DO_NOT_SUPPRESS_INDENTATION); }
    public void setDoNotSuppressIndentation(boolean value) { setProperty(Compatibility.DO_NOT_SUPPRESS_INDENTATION, value); }

    /// <summary>
    /// Do Not AutoFit Tables To Fit Next To Wrapped Objects.
    /// </summary>
    public boolean getDoNotAutofitConstrainedTables() { return getProperty(Compatibility.DO_NOT_AUTOFIT_CONSTRAINED_TABLES); }
    public void setDoNotAutofitConstrainedTables(boolean value) { setProperty(Compatibility.DO_NOT_AUTOFIT_CONSTRAINED_TABLES, value); }

    /// <summary>
    /// Allow Table Columns To Exceed Preferred Widths of Constituent Cells.
    /// </summary>
    /// <remarks>
    /// The option is called "Use Word 2003 table autofit rules" in MS Word 2013 user interface.
    /// It actually affects how the grid is calculated for fixed layout tables, too (for some cases).
    /// </remarks>
    public boolean getAutofitToFirstFixedWidthCell() { return getProperty(Compatibility.AUTOFIT_TO_FIRST_FIXED_WIDTH_CELL); }
    public void setAutofitToFirstFixedWidthCell(boolean value) { setProperty(Compatibility.AUTOFIT_TO_FIRST_FIXED_WIDTH_CELL, value); }

    /// <summary>
    /// Underline Following Character Following Numbering.
    /// </summary>
    public boolean getUnderlineTabInNumList() { return getProperty(Compatibility.UNDERLINE_TAB_IN_NUM_LIST); }
    public void setUnderlineTabInNumList(boolean value) { setProperty(Compatibility.UNDERLINE_TAB_IN_NUM_LIST, value); }

    /// <summary>
    /// Always Use Fixed Width for Hangul Characters.
    /// </summary>
    public boolean getDisplayHangulFixedWidth() { return getProperty(Compatibility.DISPLAY_HANGUL_FIXED_WIDTH); }
    public void setDisplayHangulFixedWidth(boolean value) { setProperty(Compatibility.DISPLAY_HANGUL_FIXED_WIDTH, value); }

    /// <summary>
    /// Always Move Paragraph Mark to Page after a Page Break.
    /// </summary>
    public boolean getSplitPgBreakAndParaMark() { return getProperty(Compatibility.SPLIT_PG_BREAK_AND_PARA_MARK); }
    public void setSplitPgBreakAndParaMark(boolean value) { setProperty(Compatibility.SPLIT_PG_BREAK_AND_PARA_MARK, value); }

    /// <summary>
    /// Don't Vertically Align Cells Containing Floating Objects.
    /// </summary>
    public boolean getDoNotVertAlignCellWithSp() { return getProperty(Compatibility.DO_NOT_VERT_ALIGN_CELL_WITH_SP); }
    public void setDoNotVertAlignCellWithSp(boolean value) { setProperty(Compatibility.DO_NOT_VERT_ALIGN_CELL_WITH_SP, value); }

    /// <summary>
    /// Don't Break Table Rows Around Floating Tables.
    /// </summary>
    public boolean getDoNotBreakConstrainedForcedTable() { return getProperty(Compatibility.DO_NOT_BREAK_CONSTRAINED_FORCED_TABLE); }
    public void setDoNotBreakConstrainedForcedTable(boolean value) { setProperty(Compatibility.DO_NOT_BREAK_CONSTRAINED_FORCED_TABLE, value); }

    /// <summary>
    /// Ignore Vertical Alignment in Textboxes.
    /// </summary>
    public boolean getDoNotVertAlignInTxbx() { return getProperty(Compatibility.DO_NOT_VERT_ALIGN_IN_TXBX); }
    public void setDoNotVertAlignInTxbx(boolean value) { setProperty(Compatibility.DO_NOT_VERT_ALIGN_IN_TXBX, value); }

    /// <summary>
    /// Use ANSI Kerning Pairs from Fonts.
    /// </summary>
    public boolean getUseAnsiKerningPairs() { return getProperty(Compatibility.USE_ANSI_KERNING_PAIRS); }
    public void setUseAnsiKerningPairs(boolean value) { setProperty(Compatibility.USE_ANSI_KERNING_PAIRS, value); }

    /// <summary>
    /// Use Cached Paragraph Information for Column Balancing.
    /// </summary>
    public boolean getCachedColBalance() { return getProperty(Compatibility.CACHED_COL_BALANCE); }
    public void setCachedColBalance(boolean value) { setProperty(Compatibility.CACHED_COL_BALANCE, value); }

    /// <summary>
    /// Do Not Bypass East Asian/Complex Script Layout Code.
    /// </summary>
    public boolean getUseFELayout() { return getProperty(Compatibility.USE_FE_LAYOUT); }
    public void setUseFELayout(boolean value) { setProperty(Compatibility.USE_FE_LAYOUT, value); }

    /// <summary>
    /// Specifies how the style hierarchy of the document is evaluated.
    /// </summary>
    public boolean getOverrideTableStyleFontSizeAndJustification() { return getProperty(Compatibility.OVERRIDE_TABLE_STYLE_FONT_SIZE_AND_JUSTIFICATION); }
    public void setOverrideTableStyleFontSizeAndJustification(boolean value) { setProperty(Compatibility.OVERRIDE_TABLE_STYLE_FONT_SIZE_AND_JUSTIFICATION, value); }

    /// <summary>
    /// Specifies to disable OpenType font formatting features.
    /// </summary>
    public boolean getDisableOpenTypeFontFormattingFeatures() { return getProperty(Compatibility.DISABLE_OPEN_TYPE_FONT_FORMATTING_FEATURES); }
    public void setDisableOpenTypeFontFormattingFeatures(boolean value) { setProperty(Compatibility.DISABLE_OPEN_TYPE_FONT_FORMATTING_FEATURES, value); }

    /// <summary>
    /// Specifies to swap inside and outside for mirror indents and relative positioning.
    /// </summary>
    public boolean getSwapInsideAndOutsideForMirrorIndentsAndRelativePositioning() { return getProperty(Compatibility.SWAP_INSIDE_AND_OUTSIDE_FOR_MIRROR_INDENTS_AND_RELATIVE_POSITIONING); }
    public void setSwapInsideAndOutsideForMirrorIndentsAndRelativePositioning(boolean value) { setProperty(Compatibility.SWAP_INSIDE_AND_OUTSIDE_FOR_MIRROR_INDENTS_AND_RELATIVE_POSITIONING, value); }

    /// <summary>
    /// Specifies to use Word2010 table style rules.
    /// </summary>
    public boolean getUseWord2010TableStyleRules() { return getProperty(Compatibility.USE_WORD_2010_TABLE_STYLE_RULES); }
    public void setUseWord2010TableStyleRules(boolean value) { setProperty(Compatibility.USE_WORD_2010_TABLE_STYLE_RULES, value); }

    /// <summary>
    /// True to disable UI functionality which is not compatible with Word97-2003.
    /// Default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// Controls the Word97-2003 compatibility setting that disables UI functionality which
    /// is not compatible with Word97-2003.
    /// When <c>true</c>, 'w:uiCompat97To2003' XML element is written to '\word\settings.xml'
    /// document package part.
    /// Default value is <c>false</c>. When set to <c>false</c>, this element is not written.
    ///
    /// Technically this property is not part of compatibility options, but we have put it here for API convenience.
    /// </remarks>
    public boolean getUICompat97To2003() { return getProperty(Compatibility.UI_COMPAT_97_TO_2003); }
    public void setUICompat97To2003(boolean value) { setProperty(Compatibility.UI_COMPAT_97_TO_2003, value); }

    /// <summary>
    /// Provides access to custom defined compatibility settings.
    /// </summary>
    CustomCompatibilitySettingCollection getCustomCompatibilitySettings() { return mCustomSettings; }

    static CompatibilityOptions createWord97Options()
    {
        CompatibilityOptions result = new CompatibilityOptions();
        initWord97Options(result);
        return result;
    }

    static CompatibilityOptions createWord2000Options()
    {
        CompatibilityOptions result = new CompatibilityOptions();
        initWord2000Options(result);
        return result;
    }

    static CompatibilityOptions createWord2002Options()
    {
        CompatibilityOptions result = new CompatibilityOptions();
        initWord2002Options(result);
        return result;
    }

    static CompatibilityOptions createWord2003Options()
    {
        CompatibilityOptions result = new CompatibilityOptions();
        initWord2003Options(result);
        return result;
    }

    static CompatibilityOptions createWord2007Options()
    {
        return new CompatibilityOptions();
    }

    /// <summary>
    /// Specifies which version of MS Word should be used as an etalon model for all default behavior which is not controlled by other settings or options.
    /// Can be set by user through the public API by using <see cref="OptimizeFor"/>.
    /// Also read by AW from w:compatSetting w:name="compatibilityMode" element of DOCX settings package if present.
    /// All AW behavior including model, layout, conversions etc. should consider this option to meet the user expectations.
    /// </summary>
    /// <remarks>The default value for this shall be <see cref="MsWordVersionCore.Unspecified"/></remarks>
    /*MsWordVersionCore*/int getMswVersion() { return mMsWordVersion; }
    void setMswVersion(/*MsWordVersionCore*/int value) { mMsWordVersion = value; }

    /// <summary>
    /// Indicates that the <see cref="OptimizeFor(MsWordVersion)"/> method was called by user explicitly.
    /// </summary>
    boolean isOptimized() { return mIsOptimized; }
    void isOptimized(boolean value) { mIsOptimized = value; }

    /// <summary>
    /// Gets a boolean value indicating if MS Word would display the document in compatibility mode.
    /// </summary>
    /// <remarks>
    /// When MswVersion is not specified, MS Word still displays the document in compatibility mode.
    /// </remarks>
    boolean isWord2013OrLaterCompatible() { return mMsWordVersion >= MsWordVersionCore.WORD_2013; }

    /// <summary>
    /// Indicates whether <see cref="OptimizeFor(MsWordVersion)"/> method was
    /// called by user explicitly and therefore we should convert some nodes to newer
    /// format starting from <see cref="MsWordVersion.Word2007"/> upon saving document.
    /// </summary>
    /// <remarks>
    /// This is analogue to MS Word option "Maintain compatibility with previous version of Word"
    /// in 'SaveAs' dialog. For example, if this option is set,
    /// MS Word converts shape images from VML to DML format.
    /// </remarks>
    boolean isNeedConvertToNewerVersion() { return mIsOptimized && (mMsWordVersion >= MsWordVersionCore.WORD_2007); }

    /// <summary>
    /// Clear all compatibility options and compatibility custom settings.
    /// </summary>
    private void clear()
    {
         // WORDSNET-27205 Word does not clear all options while resaving to different version,
         // but rather only limited set of options. We do not preserve absolutely all options
         // for the moment, but just only a single one 'ulTrailSpace' for which customer has
         // complained about for a while.
        HashMap<Integer, Boolean> preservedCompatibilityOptions = new HashMap<Integer, Boolean>();
        for (/*Compatibility*/int compatibilityOption : G_PRESERVE_ON_CLEAR_COMPATIBILITY_OPTIONS)
        {
            boolean value = false;
            RefBoolean referenceToValue = new RefBoolean(value);
            boolean outRefCondition2 = msDictionary.tryGetValue(mCompatibilityOptions, compatibilityOption, /*out*/ referenceToValue);
            value = referenceToValue.get();
            if (outRefCondition2)
                preservedCompatibilityOptions.put(compatibilityOption, value);
        }

        mCompatibilityOptions.clear();
        for (Map.Entry<Integer, Boolean> preservedCompatibilityOption : preservedCompatibilityOptions.entrySet())
            mCompatibilityOptions.put(preservedCompatibilityOption.getKey(), preservedCompatibilityOption.getValue());

        mCustomSettings.clear();
    }

    private static void initWord97Options(CompatibilityOptions co)
    {
        initWord2000Options(co);
        co.setAlignTablesRowByRow(true);
        co.setLayoutTableRowsApart(true);
        co.setDoNotUseHTMLParagraphAutoSpacing(true);
        co.setForgetLastTabAlignment(true);
        co.setShapeLayoutLikeWW8(true);
        co.setFootnoteLayoutLikeWW8(true);
        co.setLayoutRawTableWidth(true);
        co.setUseWord97LineBreakRules(true);
    }

    private static void initWord2000Options(CompatibilityOptions co)
    {
        initWord2002Options(co);
        co.setDoNotWrapTextWithPunct(true);
        co.setDoNotBreakWrappedTables(true);
        co.setDoNotSnapToGridInCell(true);
        co.setDoNotUseEastAsianBreakRules(true);
        co.setSelectFldWithFirstOrLastChar(true);
        co.setUnderlineTabInNumList(true);
    }

    private static void initWord2002Options(CompatibilityOptions co)
    {
        initWord2003Options(co);
        co.setGrowAutofit(true);
        co.setUseWord2002TableStyleRules(true);
    }

    private static void initWord2003Options(CompatibilityOptions co)
    {
        co.setAllowSpaceOfSameStyleInTable(true);
        co.setDoNotAutofitConstrainedTables(true);
        co.setDoNotBreakConstrainedForcedTable(true);
        co.setDoNotUseIndentAsNumberingTabStop(true);
        co.setDisplayHangulFixedWidth(true);
        co.setDoNotVertAlignInTxbx(true);
        co.setDoNotVertAlignCellWithSp(true);
        co.setSplitPgBreakAndParaMark(true);
        co.setCachedColBalance(true);
        co.setUseNormalStyleForList(true);
        co.setUseAltKinsokuLineBreakRules(true);
        co.setDoNotSuppressIndentation(true);
        co.setUseAnsiKerningPairs(true);
        co.setAutofitToFirstFixedWidthCell(true);
    }

    /// <summary>
    /// MS Word 2010 specifies the following custom compatibility settings upon creating new document.
    /// </summary>
    private static void initWord2010Options(CompatibilityOptions co)
    {
        co.getCustomCompatibilitySettings().add(new CustomCompatibilitySetting("compatibilityMode", SCHEMA_URI, "14"));
        co.getCustomCompatibilitySettings().add(new CustomCompatibilitySetting("overrideTableStyleFontSizeAndJustification", SCHEMA_URI, VALUE));
        co.getCustomCompatibilitySettings().add(new CustomCompatibilitySetting("enableOpenTypeFeatures", SCHEMA_URI, VALUE));
        co.getCustomCompatibilitySettings().add(new CustomCompatibilitySetting("doNotFlipMirrorIndents", SCHEMA_URI, VALUE));
    }

    /// <summary>
    /// MS Word 2013 specifies the following custom compatibility settings upon creating new document.
    /// </summary>
    private static void initWord2013Options(CompatibilityOptions co)
    {
        initWord2010Options(co);
        co.getCustomCompatibilitySettings().get("compatibilityMode").setValue("15");
        co.getCustomCompatibilitySettings().add(new CustomCompatibilitySetting("differentiateMultirowTableHeaders", SCHEMA_URI, VALUE));
    }

    /// <summary>
    /// MS Word 2016 specifies the following custom compatibility settings upon creating new document.
    /// </summary>
    private static void initWord2016Options(CompatibilityOptions co)
    {
        // No changes in comparison with MS Word 2013.
        initWord2013Options(co);
    }

    /// <summary>
    /// MS Word 2019 specifies the following custom compatibility settings upon creating new document.
    /// </summary>
    private static void initWord2019Options(CompatibilityOptions co)
    {
        // No changes in comparison with MS Word 2013.
        initWord2013Options(co);
    }

    /// <summary>
    /// If CompatibilityOptions are empty, MS Word 2013 uses MSWord2007 CompatibilityOptions with some flags set.
    /// This method inits second level of CompatibilityOptions with these flags set.
    /// They are inherited by document's CompatibilityOptions but not actually present there,
    /// so upon writing we do not write them.
    /// </summary>
    private void initInheritedCompatibilityOptions()
    {
        mInheritedCompatibilityOptions = new HashMap<Integer, Boolean>();
        mInheritedCompatibilityOptions.put(Compatibility.OVERRIDE_TABLE_STYLE_FONT_SIZE_AND_JUSTIFICATION, true);
        mInheritedCompatibilityOptions.put(Compatibility.DISABLE_OPEN_TYPE_FONT_FORMATTING_FEATURES, true);
        mInheritedCompatibilityOptions.put(Compatibility.SWAP_INSIDE_AND_OUTSIDE_FOR_MIRROR_INDENTS_AND_RELATIVE_POSITIONING, true);
        mInheritedCompatibilityOptions.put(Compatibility.USE_WORD_2010_TABLE_STYLE_RULES, true);
    }

    private /*MsWordVersionCore*/int mMsWordVersion = MsWordVersionCore.UNSPECIFIED;

    private CustomCompatibilitySettingCollection mCustomSettings = new CustomCompatibilitySettingCollection();

    private /*final*/ HashMap<Integer, Boolean> mCompatibilityOptions = new HashMap<Integer, Boolean>();
    private HashMap<Integer, Boolean> mInheritedCompatibilityOptions;
    private static final String SCHEMA_URI = "http://schemas.microsoft.com/office/word";
    private static final String VALUE = "1";

    /// <summary>
    /// Indicates whether <see cref="mMsWordVersion"/> was loaded from file,
    /// or was explicitly set by user with <see cref="OptimizeFor"/> method.
    /// </summary>
    private boolean mIsOptimized;

    /// <summary>
    /// The collection of Compatibility options that we should preserve in <see cref="Clear"/> method to mimic Word.
    /// </summary>
    private static /*final*/ /*Compatibility*/int[] G_PRESERVE_ON_CLEAR_COMPATIBILITY_OPTIONS = new /*Compatibility*/int[] { Compatibility.UL_TRAIL_SPACE };

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

