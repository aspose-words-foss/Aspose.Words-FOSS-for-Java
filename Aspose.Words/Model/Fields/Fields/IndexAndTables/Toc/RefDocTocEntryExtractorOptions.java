// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/06/2021 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class RefDocTocEntryExtractorOptions implements ITocEntryExtractorOptions
{
    RefDocTocEntryExtractorOptions(ITocEntryExtractorOptions options)
    {
        mOptions = options;
    }

    public boolean /*ITocEntryExtractorOptions.*/isBookmarkRangeSpecified() { return false; }

    public boolean /*ITocEntryExtractorOptions.*/getIncludeTocEntryFields() throws Exception { return mOptions.getIncludeTocEntryFields(); }

    public boolean /*ITocEntryExtractorOptions.*/isEntryLevelRangeSpecified() throws Exception { return mOptions.isEntryLevelRangeSpecified(); }

    public boolean /*ITocEntryExtractorOptions.*/isTableOfFigures() throws Exception { return mOptions.isTableOfFigures(); }

    public boolean /*ITocEntryExtractorOptions.*/isHeadingLevelRangeSpecified() throws Exception { return mOptions.isHeadingLevelRangeSpecified(); }

    public boolean /*ITocEntryExtractorOptions.*/getAreCustomStylesSpecified() throws Exception { return mOptions.getAreCustomStylesSpecified(); }

    public boolean /*ITocEntryExtractorOptions.*/getUseParagraphOutlineLevel() throws Exception { return mOptions.getUseParagraphOutlineLevel(); }

    public String /*ITocEntryExtractorOptions.*/getTableOfFiguresLabel() throws Exception { return mOptions.getTableOfFiguresLabel(); }

    public LevelRange /*ITocEntryExtractorOptions.*/getTocEntryLevelRange() { return mOptions.getTocEntryLevelRange(); }

    public LevelRange /*ITocEntryExtractorOptions.*/getHeadingLevelRange() { return mOptions.getHeadingLevelRange(); }

    public String /*ITocEntryExtractorOptions.*/getCaptionlessTableOfFiguresLabel() throws Exception { return mOptions.getCaptionlessTableOfFiguresLabel(); }

    public Bookmark /*ITocEntryExtractorOptions.*/getRangeBookmark()
    {
        return null;
    }

    public int /*ITocEntryExtractorOptions.*/getEntryType() { return mOptions.getEntryType(); }

    public int /*ITocEntryExtractorOptions.*/getLevelForCustomStyle(Paragraph paragraph, Style style) throws Exception
    {
        return mOptions.getLevelForCustomStyle(paragraph, style);
    }

    public FieldEnd /*ITocEntryExtractorOptions.*/getEnd() { return null; }

    public FieldStart /*ITocEntryExtractorOptions.*/getStart() { return null; }

    public boolean /*ITocEntryExtractorOptions.*/getSkipTables() { return mOptions.getSkipTables(); }

    public boolean /*ITocEntryExtractorOptions.*/getIncludeRefDocFields() { return false; }

    private /*final*/ ITocEntryExtractorOptions mOptions;
}

