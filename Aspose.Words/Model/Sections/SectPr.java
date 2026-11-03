// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.PlatformUtilPal;
import com.aspose.SystemPal;
import com.aspose.Debug;
import com.aspose.ms.System.Drawing.msSize;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.Threading.CurrentThread;
import com.aspose.IntToObjDictionary;


/// <summary>
/// Provides typed access to section attributes.
/// </summary>
class SectPr extends WordAttrCollection implements ISectionAttrSource
{
    void validateColumns(IWarningCallback warningCallback)
    {
        // Resiliency for a document where custom columns are specified, but not correctly defined.
        if (!getColumnsEvenlySpaced())
        {
            // WORDSNET-5054 Document contains definition (both incomplete) for two columns but column count is set to 1.
            // First step is to remove extra columns.
            while ((getColumnsCount() > getColumns().getCount()) && (getColumns().getCount() > 0))
            {
                // Remove extra columns.
                getColumns().removeAt(getColumns().getCount() - 1);
                warnUnexpected(warningCallback, WarningStrings.EXTRA_COLUMN_DEFINITIONS);
            }

            // WORDSNET-5054 Width for first column is missed.
            // AM. Word renders this file OK and shows that column width is 17.09cm (approx 9690 as RawWidth). I tried to understand how this value can be calculated but couldn't get it.
            // I could skip writing width for such columns while saving binary DOC but in this case layout engine is failed to produce good output so I decided to write this value explicitly.
            // We have three defects J5054, J2885 and J5964 with the same problem and all of them is good after this fix is applied.
            for (int i = 0; i < getColumns().getCount(); i++)
                if (getColumns().get(i).getRawWidth() == 0)
                {
                    // Write "magic" column width.
                    getColumns().get(i).setRawWidth(9690);
                    warnUnexpected(warningCallback, WarningStrings.COLUMN_WIDTH_MISSED);
                }

            int customColumnsDefinedCount = getColumns().getCount();

            if (customColumnsDefinedCount == 0)
                setColumnsEvenlySpaced(true);

            // WORDSNET-23855 It appears the number of defined columns should never be less than the number of columns specified,
            // but the other way around seems to occur from time to time in MS Word.
            if (getColumnsCount() > customColumnsDefinedCount)
                setColumnsCount(customColumnsDefinedCount);
        }
    }

    /// <summary>
    /// Updates the cached default values as well as the current culture-specific attribute values in accordance with the system LCID.
    /// </summary>
    void updateDefaultsByLcid() throws Exception
    {
        if (!PlatformUtilPal.isWindows())
            return;

        mSystemLcid = SystemPal.getSystemDefaultLcid();
        SectPr sectPr = addCultureDefaults(mSystemLcid);

        updateAttr(SectAttr.LEFT_MARGIN, sectPr.fetchAttr(SectAttr.LEFT_MARGIN));
        updateAttr(SectAttr.RIGHT_MARGIN, sectPr.fetchAttr(SectAttr.RIGHT_MARGIN));
        updateAttr(SectAttr.TOP_MARGIN, sectPr.fetchAttr(SectAttr.TOP_MARGIN));
        updateAttr(SectAttr.BOTTOM_MARGIN, sectPr.fetchAttr(SectAttr.BOTTOM_MARGIN));
        updateAttr(SectAttr.HEADER_DISTANCE, sectPr.fetchAttr(SectAttr.HEADER_DISTANCE));
        updateAttr(SectAttr.FOOTER_DISTANCE, sectPr.fetchAttr(SectAttr.FOOTER_DISTANCE));
        updateAttr(SectAttr.COLUMNS_SPACING, sectPr.fetchAttr(SectAttr.COLUMNS_SPACING));
    }

    /*NumberStyle*/int getPageNumberStyle() { return (/*NumberStyle*/Integer)fetchAttr(SectAttr.PAGE_NUMBER_STYLE); }
    void setPageNumberStyle(/*NumberStyle*/int value) { setAttr(SectAttr.PAGE_NUMBER_STYLE, value); }

    /*NumberStyle*/int getPageNumberStyleFinal() { return (/*NumberStyle*/Integer)fetchAttr(SectAttr.PAGE_NUMBER_STYLE, RevisionsView.FINAL); }

    /*ChapterPageSeparator*/int getChapterPageSeparator() { return (/*ChapterPageSeparator*/Integer)fetchAttr(SectAttr.CHAPTER_PAGE_SEPARATOR); }
    void setChapterPageSeparator(/*ChapterPageSeparator*/int value) { setAttr(SectAttr.CHAPTER_PAGE_SEPARATOR, value); }

    /*ChapterPageSeparator*/int getChapterPageSeparatorFinal() { return (/*ChapterPageSeparator*/Integer)fetchAttr(SectAttr.CHAPTER_PAGE_SEPARATOR, RevisionsView.FINAL); }

    /*SectionStart*/int getSectionStart() { return (/*SectionStart*/Integer)fetchAttr(SectAttr.SECTION_START); }
    void setSectionStart(/*SectionStart*/int value) { setAttr(SectAttr.SECTION_START, value); }

    boolean getDifferentFirstPageHeaderFooter() { return (/*boolean*/Boolean)fetchAttr(SectAttr.DIFFERENT_FIRST_PAGE_HEADER_FOOTER); }
    void setDifferentFirstPageHeaderFooter(boolean value) { setAttr(SectAttr.DIFFERENT_FIRST_PAGE_HEADER_FOOTER, value); }

    boolean getRestartPageNumbering() { return (/*boolean*/Boolean)fetchAttr(SectAttr.RESTART_PAGE_NUMBERING); }
    void setRestartPageNumbering(boolean value) { setAttr(SectAttr.RESTART_PAGE_NUMBERING, value); }

    boolean getColumnsLineBetween() { return (/*boolean*/Boolean)fetchAttr(SectAttr.COLUMNS_LINE_BETWEEN); }
    void setColumnsLineBetween(boolean value) { setAttr(SectAttr.COLUMNS_LINE_BETWEEN, value); }

    int getFirstPageTray() { return (/*int*/Integer)fetchAttr(SectAttr.FIRST_PAGE_TRAY); }
    void setFirstPageTray(int value) { setAttr(SectAttr.FIRST_PAGE_TRAY, value); }

    int getOtherPagesTray() { return (/*int*/Integer)fetchAttr(SectAttr.OTHER_PAGES_TRAY); }
    void setOtherPagesTray(int value) { setAttr(SectAttr.OTHER_PAGES_TRAY, value); }

    boolean getSuppressEndnotes() { return (/*boolean*/Boolean)fetchAttr(SectAttr.SUPPRESS_ENDNOTES); }
    void setSuppressEndnotes(boolean value) { setAttr(SectAttr.SUPPRESS_ENDNOTES, value); }

    /*LineNumberRestartMode*/int getLineNumberRestartMode() { return (/*LineNumberRestartMode*/Integer)fetchAttr(SectAttr.LINE_NUMBER_RESTART_MODE); }
    void setLineNumberRestartMode(/*LineNumberRestartMode*/int value) { setAttr(SectAttr.LINE_NUMBER_RESTART_MODE, value); }

    int getLineNumberCountBy() { return (/*int*/Integer)fetchAttr(SectAttr.LINE_NUMBER_COUNT_BY); }
    void setLineNumberCountBy(int value) { setAttr(SectAttr.LINE_NUMBER_COUNT_BY, value); }

    Border getBorderTop() { return (Border)fetchAttr(SectAttr.BORDER_TOP); }
    void setBorderTop(Border value) { setAttr(SectAttr.BORDER_TOP, value); }

    Border getBorderLeft() { return (Border)fetchAttr(SectAttr.BORDER_LEFT); }
    void setBorderLeft(Border value) { setAttr(SectAttr.BORDER_LEFT, value); }

    Border getBorderBottom() { return (Border)fetchAttr(SectAttr.BORDER_BOTTOM); }
    void setBorderBottom(Border value) { setAttr(SectAttr.BORDER_BOTTOM, value); }

    Border getBorderRight() { return (Border)fetchAttr(SectAttr.BORDER_RIGHT); }
    void setBorderRight(Border value) { setAttr(SectAttr.BORDER_RIGHT, value); }

    int getCharSpace() { return (/*int*/Integer)fetchAttr(SectAttr.CHAR_SPACE); }
    void setCharSpace(int value) { setAttr(SectAttr.CHAR_SPACE, value); }

    int getLinePitch() { return (/*int*/Integer)fetchAttr(SectAttr.LINE_PITCH); }
    void setLinePitch(int value) { setAttr(SectAttr.LINE_PITCH, value); }

    int getLineStartingNumber() { return (/*int*/Integer)fetchAttr(SectAttr.LINE_STARTING_NUMBER); }
    void setLineStartingNumber(int value) { setAttr(SectAttr.LINE_STARTING_NUMBER, value); }

    int getHeadingLevelForChapter() { return (/*int*/Integer)fetchAttr(SectAttr.HEADING_LEVEL_FOR_CHAPTER); }
    void setHeadingLevelForChapter(int value) { setAttr(SectAttr.HEADING_LEVEL_FOR_CHAPTER, value); }

    int getHeadingLevelForChapterFinal() { return (/*int*/Integer)fetchAttr(SectAttr.HEADING_LEVEL_FOR_CHAPTER, RevisionsView.FINAL); }

    int getPageStartingNumber() { return (/*int*/Integer)fetchAttr(SectAttr.PAGE_STARTING_NUMBER); }
    void setPageStartingNumber(int value) { setAttr(SectAttr.PAGE_STARTING_NUMBER, value); }

    int getPageStartingNumberFinal() { return (/*int*/Integer)fetchAttr(SectAttr.PAGE_STARTING_NUMBER, RevisionsView.FINAL); }

    /*Orientation*/int getOrientation() { return (/*Orientation*/Integer)fetchAttr(SectAttr.ORIENTATION); }
    void setOrientation(/*Orientation*/int value) { setAttr(SectAttr.ORIENTATION, value); }

    /*PageBorderAppliesTo*/int getBorderAppliesTo() { return (/*PageBorderAppliesTo*/Integer)fetchAttr(SectAttr.BORDER_APPLIES_TO); }
    void setBorderAppliesTo(/*PageBorderAppliesTo*/int value) { setAttr(SectAttr.BORDER_APPLIES_TO, value); }

    boolean getBorderAlwaysInFront() { return (/*boolean*/Boolean)fetchAttr(SectAttr.BORDER_ALWAYS_IN_FRONT); }
    void setBorderAlwaysInFront(boolean value) { setAttr(SectAttr.BORDER_ALWAYS_IN_FRONT, value); }

    /*PageBorderDistanceFrom*/int getBorderDistanceFrom() { return (/*PageBorderDistanceFrom*/Integer)fetchAttr(SectAttr.BORDER_DISTANCE_FROM); }
    void setBorderDistanceFrom(/*PageBorderDistanceFrom*/int value) { setAttr(SectAttr.BORDER_DISTANCE_FROM, value); }

    int getPageWidth() { return (/*int*/Integer)fetchAttr(SectAttr.PAGE_WIDTH); }
    void setPageWidth(int value) { setAttr(SectAttr.PAGE_WIDTH, value); }

    int getPageHeight() { return (/*int*/Integer)fetchAttr(SectAttr.PAGE_HEIGHT); }
    void setPageHeight(int value) { setAttr(SectAttr.PAGE_HEIGHT, value); }

    int getLeftMargin() { return (/*int*/Integer)fetchAttr(SectAttr.LEFT_MARGIN); }
    void setLeftMargin(int value) { setAttr(SectAttr.LEFT_MARGIN, value); }

    int getRightMargin() { return (/*int*/Integer)fetchAttr(SectAttr.RIGHT_MARGIN); }
    void setRightMargin(int value) { setAttr(SectAttr.RIGHT_MARGIN, value); }

    int getTopMargin() { return (/*int*/Integer)fetchAttr(SectAttr.TOP_MARGIN); }
    void setTopMargin(int value) { setAttr(SectAttr.TOP_MARGIN, value); }

    int getBottomMargin() { return (/*int*/Integer)fetchAttr(SectAttr.BOTTOM_MARGIN); }
    void setBottomMargin(int value) { setAttr(SectAttr.BOTTOM_MARGIN, value); }

    int getGutter() { return (/*int*/Integer)fetchAttr(SectAttr.GUTTER); }
    void setGutter(int value) { setAttr(SectAttr.GUTTER, value); }

    int getHeaderDistance() { return (/*int*/Integer)fetchAttr(SectAttr.HEADER_DISTANCE); }
    void setHeaderDistance(int value) { setAttr(SectAttr.HEADER_DISTANCE, value); }

    int getFooterDistance() { return (/*int*/Integer)fetchAttr(SectAttr.FOOTER_DISTANCE); }
    void setFooterDistance(int value) { setAttr(SectAttr.FOOTER_DISTANCE, value); }

    /*PageVerticalAlignment*/int getVerticalAlignment() { return (/*PageVerticalAlignment*/Integer)fetchAttr(SectAttr.VERTICAL_ALIGNMENT); }
    void setVerticalAlignment(/*PageVerticalAlignment*/int value) { setAttr(SectAttr.VERTICAL_ALIGNMENT, value); }

    int getColumnsCount() { return (/*int*/Integer)fetchAttr(SectAttr.COLUMNS_COUNT); }
    void setColumnsCount(int value) { setAttr(SectAttr.COLUMNS_COUNT, value); }

    boolean getColumnsEvenlySpaced() { return (/*boolean*/Boolean)fetchAttr(SectAttr.COLUMNS_EVENLY_SPACED); }
    void setColumnsEvenlySpaced(boolean value) { setAttr(SectAttr.COLUMNS_EVENLY_SPACED, value); }

    /// <summary>
    /// Gets total size of the evenly spaced column in section, in twips.
    /// </summary>
    /// <remarks>
    /// The total size is used to determine the size of an individual column.
    /// The columns may not be actually equal because of rounding.
    /// </remarks>
    int getTotalEvenlySpacedColumnSizeTwips(boolean hasSideGutter)
    {
        Debug.assertTrue(getColumnsEvenlySpaced());

        // Width available for columns on the page.
        int availableWidth = getContentWidthTwips(hasSideGutter);

        // In TestDefect23855 ColumnsCount is 0 for some reason.
        int spacingCount = Math.max(getColumnsCount() - 1, 0);
        availableWidth -= spacingCount * getColumnsSpacing();

        return availableWidth;
    }

    int getContentWidthTwips(boolean countGutter)
    {
        int gutter = countGutter ? getGutter() : 0;

        // Width available for columns on the page.
        return getPageWidth() - getLeftMargin() - getRightMargin() - gutter;
    }

    int getColumnsSpacing() { return (/*int*/Integer)fetchAttr(SectAttr.COLUMNS_SPACING); }
    void setColumnsSpacing(int value) { setAttr(SectAttr.COLUMNS_SPACING, value); }

    TextColumnCollectionInternal getColumns() { return (TextColumnCollectionInternal)fetchAttr(SectAttr.COLUMNS); }
    void setColumns(TextColumnCollectionInternal value) { setAttr(SectAttr.COLUMNS, value); }

    boolean getUnlocked() { return (/*boolean*/Boolean)fetchAttr(SectAttr.UNLOCKED); }
    void setUnlocked(boolean value) { setAttr(SectAttr.UNLOCKED, value); }

    int getLineNumberDistanceFromText() { return (/*int*/Integer)fetchAttr(SectAttr.LINE_NUMBER_DISTANCE_FROM_TEXT); }
    void setLineNumberDistanceFromText(int value) { setAttr(SectAttr.LINE_NUMBER_DISTANCE_FROM_TEXT, value); }

    boolean getRtlGutter() { return (/*boolean*/Boolean)fetchAttr(SectAttr.RTL_GUTTER); }
    void setRtlGutter(boolean value) { setAttr(SectAttr.RTL_GUTTER, value); }

    /*SectionLayoutMode*/int getGridType() { return (/*SectionLayoutMode*/Integer)fetchAttr(SectAttr.GRID_TYPE); }
    void setGridType(/*SectionLayoutMode*/int value) { setAttr(SectAttr.GRID_TYPE, value); }

    /*TextFlow*/int getTextFlow() { return (/*TextFlow*/Integer)fetchAttr(SectAttr.TEXT_FLOW); }
    void setTextFlow(/*TextFlow*/int value) { setAttr(SectAttr.TEXT_FLOW, value); }

    boolean getBidi() { return (/*boolean*/Boolean)fetchAttr(SectAttr.BIDI); }
    void setBidi(boolean value) { setAttr(SectAttr.BIDI, value); }

    /*FootnotePosition*/int getFootnotePosition() { return (/*FootnotePosition*/Integer)fetchAttr(SectAttr.FOOTNOTE_LOCATION); }
    void setFootnotePosition(/*FootnotePosition*/int value) { setAttr(SectAttr.FOOTNOTE_LOCATION, value); }

    /*FootnoteNumberingRule*/int getFootnoteNumberingRule() { return (/*FootnoteNumberingRule*/Integer)fetchAttr(SectAttr.FOOTNOTE_NUMBERING_RULE); }
    void setFootnoteNumberingRule(/*FootnoteNumberingRule*/int value) { setAttr(SectAttr.FOOTNOTE_NUMBERING_RULE, value); }

    /*NumberStyle*/int getFootnoteNumberStyle() { return (/*NumberStyle*/Integer)fetchAttr(SectAttr.FOOTNOTE_NUMBER_STYLE); }
    void setFootnoteNumberStyle(/*NumberStyle*/int value) { setAttr(SectAttr.FOOTNOTE_NUMBER_STYLE, value); }

    int getFootnoteStartNumber() { return (/*int*/Integer)fetchAttr(SectAttr.FOOTNOTE_START_NUMBER); }
    void setFootnoteStartNumber(int value) { setAttr(SectAttr.FOOTNOTE_START_NUMBER, value); }

    /// <summary>
    /// Number of footnote columns. If it is zero, then a number of columns on a displayed page is used.
    /// </summary>
    int getFootnoteColumns() { return (/*int*/Integer)fetchAttr(SectAttr.FOOTNOTE_COLUMNS); }
    void setFootnoteColumns(int value) { setAttr(SectAttr.FOOTNOTE_COLUMNS, value); }

    /*EndnotePosition*/int getEndnotePosition() { return (/*EndnotePosition*/Integer)fetchAttr(SectAttr.FOOTNOTE_LOCATION + SectAttr.ENDNOTE_KEY_DELTA); }
    void setEndnotePosition(/*EndnotePosition*/int value) { setAttr(SectAttr.FOOTNOTE_LOCATION + SectAttr.ENDNOTE_KEY_DELTA, value); }

    /*FootnoteNumberingRule*/int getEndnoteNumberingRule() { return (/*FootnoteNumberingRule*/Integer)fetchAttr(SectAttr.FOOTNOTE_NUMBERING_RULE + SectAttr.ENDNOTE_KEY_DELTA); }
    void setEndnoteNumberingRule(/*FootnoteNumberingRule*/int value) { setAttr(SectAttr.FOOTNOTE_NUMBERING_RULE + SectAttr.ENDNOTE_KEY_DELTA, value); }

    /*NumberStyle*/int getEndnoteNumberStyle() { return (/*NumberStyle*/Integer)fetchAttr(SectAttr.FOOTNOTE_NUMBER_STYLE + SectAttr.ENDNOTE_KEY_DELTA); }
    void setEndnoteNumberStyle(/*NumberStyle*/int value) { setAttr(SectAttr.FOOTNOTE_NUMBER_STYLE + SectAttr.ENDNOTE_KEY_DELTA, value); }

    int getEndnoteStartNumber() { return (/*int*/Integer)fetchAttr(SectAttr.FOOTNOTE_START_NUMBER + SectAttr.ENDNOTE_KEY_DELTA); }
    void setEndnoteStartNumber(int value) { setAttr(SectAttr.FOOTNOTE_START_NUMBER + SectAttr.ENDNOTE_KEY_DELTA, value); }

    /// <summary>
    /// Gets or sets Test flag. This flag is needed for large number of old tests.
    /// </summary>
    /// <remarks>
    /// You should mark tests that don't use test mode as NonParallelizable.
    /// </remarks>
    static boolean getTestMode() { return gTestMode; }
    static void setTestMode(boolean value)
    {
        gTestMode = value;
    }

    /// <summary>
    /// ISectionAttrSource.
    /// </summary>
    public Object getDirectSectionAttr(int key)
    {
        return getDirectAttr(key);
    }

    /// <summary>
    /// ISectionAttrSource.
    /// </summary>
    public Object getDirectSectionAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return getDirectAttr(key, revisionsView);
    }

    /// <summary>
    /// ISectionAttrSource.
    /// </summary>
    public Object fetchInheritedSectionAttr(int key)
    {
        return fetchInheritedAttr(key);
    }

    /// <summary>
    /// ISectionAttrSource.
    /// </summary>
    public Object fetchSectionAttr(int key)
    {
        return fetchAttr(key);
    }

    /// <summary>
    /// ISectionAttrSource.
    /// </summary>
    public Object fetchSectionAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return fetchAttr(key, revisionsView);
    }

    /// <summary>
    /// ISectionAttrSource.
    /// </summary>
    public void setSectionAttr(int key, Object value)
    {
        // The PaperCode attribute specifies the producing application’s unique identifier value for current paper size.
        // If Height/Width was changed this code has no meaning.
        if ((key == SectAttr.PAGE_WIDTH) || (key == SectAttr.PAGE_HEIGHT))
            remove(SectAttr.PAPER_CODE);

        setAttr(key, value);
    }

    /// <summary>
    /// ISectionAttrSource.
    /// </summary>
    public void setSectionAttr(int key, Object value, /*RevisionsView*/int revisionsView)
    {
        // The PaperCode attribute specifies the producing application’s unique identifier value for current paper size.
        // If Height/Width was changed this code has no meaning.
        if ((key == SectAttr.PAGE_WIDTH) || (key == SectAttr.PAGE_HEIGHT))
            remove(SectAttr.PAPER_CODE, revisionsView);

        setAttr(key, value, revisionsView);
    }


    /// <summary>
    /// ISectionAttrSource.
    /// </summary>
    public void clearSectionAttrs()
    {
        clear();
    }

    protected /*override*/ AttrCollection getDefaults()
    {
        int lcid = (mSystemLcid != 0)
            ? mSystemLcid
            : getCurrentLcid();

        return addCultureDefaults(lcid);
    }

    /// <summary>
    /// Updates the specified value of an attribute if its value has been previously set explicitly.
    /// </summary>
    private void updateAttr(int key, Object value)
    {
        if (getDirectAttr(key) != null)
            setAttr(key, value);
    }

    static Object fetchDefaultAttr(int key)
    {
        SectPr defaults = addCultureDefaults(getCurrentLcid());

        return defaults.getDirectAttr(key);
    }

    /// <summary>
    /// Checks collection of the attributes existence in the cache and
    /// add it, if it does not exist.
    /// WORDSNET-13826 Every thread with custom culture has own default collection
    /// of the attributes.
    /// </summary>
    /// <returns>Collection with default values of the attributes.</returns>
    private static SectPr addCultureDefaults(int lcid)
    {
        SectPr attrs;

        synchronized (G_DEFAULTS_SYNC)
    	{
            attrs = DEFAULTS_CACHE.get(lcid);
    	}

        // Collection for current culture already exist, so return it.
        if (attrs != null)
            return attrs;

        attrs = new SectPr();
        fillSectPr(attrs, lcid);

        synchronized (G_DEFAULTS_SYNC)
    	{
            DEFAULTS_CACHE.set(lcid, attrs);
    	}

        return attrs;
    }

    /// <summary>
    /// Fills attribute collection with default values.
    /// </summary>
    /// <param name="attrs">Destination collection of the attributes to add values.</param>
    /// <param name="lcid">Culture identifier.</param>
    private static void fillSectPr(SectPr attrs, int lcid)
    {
        // Do not clone to avoid duplicating of the object with reference type.
        for (int i = 0; i < G_DEFAULTS.getCount(); i++)
            attrs.add(G_DEFAULTS.getKey(i), G_DEFAULTS.getByIndex(i));

        // Fill culture depended attributes.
        LocaleDefaults.Margins margins = getDefaultPageMargins(lcid);

        // MS Word uses different page margin defaults depending on the current culture.
        // See X:\awspecs\DOC[MS-DOC].pdf, page 151 sprmSDxaColumns for example.
        // AW mimics this behavior.
        // There might be other values dependent on locale.
        attrs.add(SectAttr.LEFT_MARGIN, margins.getLeft());
        attrs.add(SectAttr.RIGHT_MARGIN, margins.getRight());
        attrs.add(SectAttr.TOP_MARGIN, margins.getTop());
        attrs.add(SectAttr.BOTTOM_MARGIN, margins.getBottom());

        attrs.add(SectAttr.HEADER_DISTANCE, getDefaultHeaderFooterDistance(lcid, false));
        attrs.add(SectAttr.FOOTER_DISTANCE, getDefaultHeaderFooterDistance(lcid, false));

        attrs.remove(SectAttr.COLUMNS_SPACING);

        attrs.add(SectAttr.COLUMNS_SPACING, getDefaultSpaceBetweenColumns(lcid, false));
    }

    private static void warnUnexpected(IWarningCallback warningCallback, String description)
    {
        if (warningCallback != null)
            warningCallback.warning(new WarningInfo(WarningType.UNEXPECTED_CONTENT, WarningSource.UNKNOWN, description));
    }

    /// <summary>
    /// Sets defaults that depend on locale. Changes only properties that are already set.
    /// </summary>
    void setLocaleDefaultsForNewDocument()
    {
        int currentLcid = getCurrentLcid();

        LocaleDefaults.Margins margins = getDefaultPageMargins(currentLcid);

        if (containsKey(SectAttr.LEFT_MARGIN))
            setAttr(SectAttr.LEFT_MARGIN, margins.getLeft());
        if (containsKey(SectAttr.RIGHT_MARGIN))
            setAttr(SectAttr.RIGHT_MARGIN, margins.getRight());
        if (containsKey(SectAttr.TOP_MARGIN))
            setAttr(SectAttr.TOP_MARGIN, margins.getTop());
        if (containsKey(SectAttr.BOTTOM_MARGIN))
            setAttr(SectAttr.BOTTOM_MARGIN, margins.getBottom());

        if (containsKey(SectAttr.HEADER_DISTANCE))
            setAttr(SectAttr.HEADER_DISTANCE, getDefaultHeaderFooterDistance(currentLcid, true));
        if (containsKey(SectAttr.FOOTER_DISTANCE))
            setAttr(SectAttr.FOOTER_DISTANCE, getDefaultHeaderFooterDistance(currentLcid, true));

        if (containsKey(SectAttr.COLUMNS_SPACING))
            setAttr(SectAttr.COLUMNS_SPACING, getDefaultSpaceBetweenColumns(currentLcid, true));
    }

    /// <summary>
    /// Sets default page size that depends on locale.
    /// </summary>
    void setDefaultPageSize()
    {
        int currentLcid = getCurrentLcid();
        /*Size*/long defaultPageSize = LocaleDefaults.getPageSize(currentLcid);
        setAttr(SectAttr.PAGE_WIDTH, msSize.getWidth(defaultPageSize));
        setAttr(SectAttr.PAGE_HEIGHT, msSize.getHeight(defaultPageSize));
    }

    /// <summary>
    /// Gets default page margins dependent on the current locale. Returns predefined value in test mode.
    /// </summary>
    private static LocaleDefaults.Margins getDefaultPageMargins(int currentLcid)
    {
        if (currentLcid != TEST_INDEFINITE_LOCALE)
            return LocaleDefaults.getPageMargins(currentLcid);
        else
            return G_TEST_DEFAULT_MARGINS;
    }

    /// <summary>
    /// Gets default header/footer distance dependent on the current locale. Returns predefined value in test mode.
    /// </summary>
    private static int getDefaultHeaderFooterDistance(int currentLcid, boolean forNewDoc)
    {
        if (currentLcid != TEST_INDEFINITE_LOCALE)
            return LocaleDefaults.getHeaderFooterDistance(currentLcid);
        else
            return forNewDoc ? TEST_HEADER_FOOTER_DISTANCE_FOR_NEW : TEST_HEADER_FOOTER_DISTANCE_FOR_LOAD;
    }

    /// <summary>
    /// Gets default column spacing dependent on the current locale. Returns predefined value in test mode.
    /// </summary>
    private static int getDefaultSpaceBetweenColumns(int currentLcid, boolean forNewDoc)
    {
        if (currentLcid != TEST_INDEFINITE_LOCALE)
            return LocaleDefaults.getSpaceBetweenColumns(currentLcid);
        else
            return forNewDoc ? TEST_COLUMN_SPACING_FOR_NEW : TEST_COLUMN_SPACING_FOR_LOAD;
    }

    /// <summary>
    /// Returns current locale ID or TestIndefiniteLocale value in test mode.
    /// </summary>
    static int getCurrentLcid()
    {
        if (!gTestMode)
        {
            msCultureInfo culture = CurrentThread.getCurrentCulture();
            return culture.getLCID();
        }
        else
        {
            return TEST_INDEFINITE_LOCALE;
        }
    }

    /// <summary>
    /// Stores collections of the default values for attributes.
    /// Appropriate collection can be obtained by culture identifier.
    /// </summary>
    /// <remarks>Access level is internal for test purposes.</remarks>
    static /*final*/ IntToObjDictionary<SectPr> DEFAULTS_CACHE = new IntToObjDictionary<SectPr>();

    /// <summary>
    /// Enumerates all section border key.
    /// </summary>
    static /*final*/ int[] BORDER_ATTRS = new int[]
    {
        SectAttr.BORDER_TOP,
        SectAttr.BORDER_LEFT,
        SectAttr.BORDER_BOTTOM,
        SectAttr.BORDER_RIGHT,
    };

    /// <summary>
    /// Object to synchronize access to cache with default values of the attributes.
    /// </summary>
    private static /*final*/ Object G_DEFAULTS_SYNC = new Object();

    /// <summary>
    /// Collection of the culture independent attributes with default values.
    /// </summary>
    private static /*final*/ SectPr G_DEFAULTS;

    /// <summary>
    /// This static member is used to set defaults of page margin, header/footer distance and column spacing
    /// to old values that is needed for large number of old tests.
    /// </summary>
    private static boolean gTestMode;

    private int mSystemLcid;

    private static /*final*/ LocaleDefaults.Margins G_TEST_DEFAULT_MARGINS =
        new LocaleDefaults.Margins(1800, 1800, 1440, 1440);

    private static final int TEST_INDEFINITE_LOCALE = -1;
    private static final int TEST_HEADER_FOOTER_DISTANCE_FOR_LOAD = 720;
    private static final int TEST_HEADER_FOOTER_DISTANCE_FOR_NEW = 708;
    private static final int TEST_COLUMN_SPACING_FOR_LOAD = 720;
    private static final int TEST_COLUMN_SPACING_FOR_NEW = 708;

    static/* SectPr()*/
    {
        G_DEFAULTS = new SectPr();

        G_DEFAULTS.add(SectAttr.SECTION_START, SectionStart.NEW_PAGE);
        G_DEFAULTS.add(SectAttr.DIFFERENT_FIRST_PAGE_HEADER_FOOTER, false);
        G_DEFAULTS.add(SectAttr.SUPPRESS_ENDNOTES, false);
        G_DEFAULTS.add(SectAttr.VERTICAL_ALIGNMENT, PageVerticalAlignment.TOP);
        G_DEFAULTS.add(SectAttr.TEXT_FLOW, TextFlow.HORIZONTAL);
        G_DEFAULTS.add(SectAttr.BIDI, false);

        G_DEFAULTS.add(SectAttr.PAGE_WIDTH, 12240);
        G_DEFAULTS.add(SectAttr.PAGE_HEIGHT, 15840);
        G_DEFAULTS.add(SectAttr.ORIENTATION, Orientation.PORTRAIT);
        G_DEFAULTS.add(SectAttr.PAPER_CODE, 0);

        G_DEFAULTS.add(SectAttr.GUTTER, 0);

        G_DEFAULTS.add(SectAttr.FIRST_PAGE_TRAY, 0);
        G_DEFAULTS.add(SectAttr.OTHER_PAGES_TRAY, 0);

        G_DEFAULTS.add(SectAttr.CHAPTER_PAGE_SEPARATOR, ChapterPageSeparator.HYPHEN);
        G_DEFAULTS.add(SectAttr.HEADING_LEVEL_FOR_CHAPTER, 0);
        G_DEFAULTS.add(SectAttr.PAGE_NUMBER_STYLE, NumberStyle.ARABIC);
        G_DEFAULTS.add(SectAttr.RESTART_PAGE_NUMBERING, false);
        G_DEFAULTS.add(SectAttr.PAGE_STARTING_NUMBER, 1);

        G_DEFAULTS.add(SectAttr.LINE_NUMBER_RESTART_MODE, LineNumberRestartMode.RESTART_PAGE);
        G_DEFAULTS.add(SectAttr.LINE_NUMBER_COUNT_BY, 0);
        G_DEFAULTS.add(SectAttr.LINE_NUMBER_DISTANCE_FROM_TEXT, 0);
        G_DEFAULTS.add(SectAttr.LINE_STARTING_NUMBER, 1);

        G_DEFAULTS.add(SectAttr.COLUMNS_EVENLY_SPACED, true);
        G_DEFAULTS.add(SectAttr.COLUMNS_COUNT, 1);
        G_DEFAULTS.add(SectAttr.COLUMNS_LINE_BETWEEN, false);
        // This is a complex attribute. Be careful not to modify the actual values in it.
        G_DEFAULTS.add(SectAttr.COLUMNS, new TextColumnCollectionInternal());

        G_DEFAULTS.add(SectAttr.RTL_GUTTER, false);
        G_DEFAULTS.add(SectAttr.UNLOCKED, false);

        // These are complex attributes. Be careful not to modify the actual values in them.
        G_DEFAULTS.add(SectAttr.BORDER_LEFT, new Border());
        G_DEFAULTS.add(SectAttr.BORDER_RIGHT, new Border());
        G_DEFAULTS.add(SectAttr.BORDER_TOP, new Border());
        G_DEFAULTS.add(SectAttr.BORDER_BOTTOM, new Border());

        G_DEFAULTS.add(SectAttr.BORDER_APPLIES_TO, PageBorderAppliesTo.ALL_PAGES);
        G_DEFAULTS.add(SectAttr.BORDER_DISTANCE_FROM, PageBorderDistanceFrom.TEXT);
        G_DEFAULTS.add(SectAttr.BORDER_ALWAYS_IN_FRONT, true);

        G_DEFAULTS.add(SectAttr.CHAR_SPACE, 0);
        G_DEFAULTS.add(SectAttr.LINE_PITCH, 0);
        G_DEFAULTS.add(SectAttr.GRID_TYPE, 0);

        G_DEFAULTS.add(SectAttr.FOOTNOTE_LOCATION, FootnotePosition.BOTTOM_OF_PAGE);
        G_DEFAULTS.add(SectAttr.FOOTNOTE_NUMBER_STYLE, NumberStyle.ARABIC);
        G_DEFAULTS.add(SectAttr.FOOTNOTE_START_NUMBER, 1);
        G_DEFAULTS.add(SectAttr.FOOTNOTE_NUMBERING_RULE, FootnoteNumberingRule.CONTINUOUS);
        G_DEFAULTS.add(SectAttr.FOOTNOTE_COLUMNS, 0);

        G_DEFAULTS.add(SectAttr.ENDNOTE_LOCATION, EndnotePosition.END_OF_DOCUMENT);
        G_DEFAULTS.add(SectAttr.ENDNOTE_NUMBER_STYLE, NumberStyle.LOWERCASE_ROMAN);
        G_DEFAULTS.add(SectAttr.ENDNOTE_START_NUMBER, 1);
        G_DEFAULTS.add(SectAttr.ENDNOTE_NUMBERING_RULE, FootnoteNumberingRule.CONTINUOUS);

        G_DEFAULTS.add(SectAttr.SYS_GPRF_IHDT, GrpfIhdt.NONE);

        G_DEFAULTS.add(SectAttr.RSID, 0);
    }
}

