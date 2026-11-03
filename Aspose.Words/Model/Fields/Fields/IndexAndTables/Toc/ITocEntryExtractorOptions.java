// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/11/2014 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Interface used to provide a <see cref="TocEntryExtractor"/> with options, needed to extract TOC entries.
/// </summary>
interface ITocEntryExtractorOptions
{
    public 
    boolean isBookmarkRangeSpecified() throws Exception;

    public 
    boolean getIncludeTocEntryFields() throws Exception;

    public 
    boolean isEntryLevelRangeSpecified() throws Exception;

    public 
    boolean isTableOfFigures() throws Exception;

    public 
    boolean isHeadingLevelRangeSpecified() throws Exception;

    public 
    boolean getAreCustomStylesSpecified() throws Exception;

    public 
    boolean getUseParagraphOutlineLevel() throws Exception;

    public 
    String getTableOfFiguresLabel() throws Exception;

    public LevelRange getTocEntryLevelRange();

    public LevelRange getHeadingLevelRange();

    public 
    String getCaptionlessTableOfFiguresLabel() throws Exception;

    public Bookmark getRangeBookmark() throws Exception;

    public int getEntryType();

    public int getLevelForCustomStyle(Paragraph paragraph, Style style) throws Exception;

    public FieldEnd getEnd();

    public FieldStart getStart();

    public boolean getSkipTables();

    public boolean getIncludeRefDocFields();
}

