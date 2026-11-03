// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/03/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



class TocEntryExtractorOptions implements ITocEntryExtractorOptions
{
    public boolean isBookmarkRangeSpecified() { return false; }

    public boolean getIncludeTocEntryFields() { return false; }

    public boolean isEntryLevelRangeSpecified() { return false; }

    public boolean isTableOfFigures() { return false; }

    public boolean isHeadingLevelRangeSpecified() { return false; }

    public boolean getAreCustomStylesSpecified() { return false; }

    public boolean getUseParagraphOutlineLevel() { return true; }

    public String getTableOfFiguresLabel() { throw new IllegalStateException(); }

    public LevelRange getTocEntryLevelRange() { throw new IllegalStateException(); }

    public LevelRange getHeadingLevelRange() { return LevelRange.MAX_RANGE; }

    public String getCaptionlessTableOfFiguresLabel() { throw new IllegalStateException(); }

    public Bookmark getRangeBookmark()
    {
        throw new IllegalStateException();
    }

    public int getEntryType() { throw new IllegalStateException(); }

    public int getLevelForCustomStyle(Paragraph paragraph, Style style)
    {
        throw new IllegalStateException();
    }

    public FieldEnd getEnd() { return null; }

    public FieldStart getStart() { return null; }

    public boolean getSkipTables() { return mSkipTables; }; public void setSkipTables(boolean value) { mSkipTables = value; };

    private boolean mSkipTables;

    public boolean getIncludeRefDocFields() { return false; }
}

