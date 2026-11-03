// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msMath;
import com.aspose.ArgumentUtil;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.Drawing.msSizeF;
import com.aspose.ms.System.Drawing.msSize;
import com.aspose.ms.System.Collections.SortedList;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.HashMap;


/// <summary>
/// Represents the page setup properties of a section.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-sections/">Working with Sections</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p><see cref="PageSetup"/> object contains all the page setup attributes of a section
/// (left margin, bottom margin, paper size, and so on) as properties.</p>
/// </remarks>
/// <dev>
/// This is a presentation object only, does not store any model data.
/// </dev>
public class PageSetup implements IBorderAttrSource
{
    /// <summary>
    /// Ctor for tests only: styles are not specified.
    /// </summary>
    /// <param name="parent">Source of page setup properties.</param>
    /// <param name="docPr">Needed to access some global page setup properties.</param>
    PageSetup(ISectionAttrSource parent, DocPr docPr)
    {
    	this(parent, docPr, null, null);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="parent">Source of page setup properties.</param>
    /// <param name="docPr">Needed to access some global page setup properties.</param>
    /// <param name="styles">Needed to access Normal style to calculate <see cref="CharactersPerLine"/>.</param>
    /// <param name="firstSectionAttrSource">Needed to calculate content width/height and margins.</param>
    PageSetup(ISectionAttrSource parent, DocPr docPr, StyleCollection styles,
        ISectionAttrSource firstSectionAttrSource)
    {
        Debug.assertTrue(parent != null);
        Debug.assertTrue(docPr != null);

        mDocument = (styles != null) && (styles.getDocument() instanceof Document)
            ? styles.getDocument().fetchDocument()
            : null;
        mParent = parent;
        mDocPr = docPr;
        mStyles = styles;
        // If the first section attribute source is not specified, expect that 'parent' is attribute
        // source of the first/only section.
        mFirstSectionAttrSource = (firstSectionAttrSource != null) ? firstSectionAttrSource : parent;
    }

    /// <summary>
    /// Resets page setup to default paper size, margins and orientation.
    /// </summary>
    public void clearFormatting()
    {
        mParent.clearSectionAttrs();
    }

    /// <summary>
    /// True if the document has different headers and footers for odd-numbered and even-numbered pages.
    /// </summary>
    /// <remarks>
    /// Note, changing this property affects all sections in the document.
    /// </remarks>
    public boolean getOddAndEvenPagesHeaderFooter() { return mDocPr.EvenAndOddHeaders; }
    public void setOddAndEvenPagesHeaderFooter(boolean value) { mDocPr.EvenAndOddHeaders = value; }

    /// <summary>
    /// True if a different header or footer is used on the first page.
    /// </summary>
    public boolean getDifferentFirstPageHeaderFooter() { return (/*boolean*/Boolean)fetchAttr(SectAttr.DIFFERENT_FIRST_PAGE_HEADER_FOOTER); }
    public void setDifferentFirstPageHeaderFooter(boolean value) { setAttr(SectAttr.DIFFERENT_FIRST_PAGE_HEADER_FOOTER, value); }

    /// <summary>
    /// For multiple page documents, gets or sets how a document is printed or rendered so that it can be bound as a booklet.
    /// </summary>
    public /*MultiplePagesType*/int getMultiplePages() { return mDocPr.MultiplePages; }
    public void setMultiplePages(/*MultiplePagesType*/int value) { mDocPr.MultiplePages = value; }

    /// <summary>
    /// Returns or sets the number of pages to be included in each booklet.
    /// </summary>
    public int getSheetsPerBooklet() { return mDocPr.BookFoldPrintingSheets; }
    public void setSheetsPerBooklet(int value) { mDocPr.BookFoldPrintingSheets = value; }

    /// <summary>
    /// Returns or sets the type of section break for the specified object.
    /// </summary>
    public /*SectionStart*/int getSectionStart() { return (/*SectionStart*/Integer)fetchAttr(SectAttr.SECTION_START); }
    public void setSectionStart(/*SectionStart*/int value) { setAttr(SectAttr.SECTION_START, value); }

    /// <summary>
    /// True if endnotes are printed at the end of the next section that doesn't suppress endnotes.
    /// Suppressed endnotes are printed before the endnotes in that section.
    /// </summary>
    public boolean getSuppressEndnotes() { return (/*boolean*/Boolean)fetchAttr(SectAttr.SUPPRESS_ENDNOTES); }
    public void setSuppressEndnotes(boolean value) { setAttr(SectAttr.SUPPRESS_ENDNOTES, value); }

    /// <summary>
    /// Returns or sets the vertical alignment of text on each page in a document or section.
    /// </summary>
    public /*PageVerticalAlignment*/int getVerticalAlignment() { return (/*PageVerticalAlignment*/Integer)fetchAttr(SectAttr.VERTICAL_ALIGNMENT); }
    public void setVerticalAlignment(/*PageVerticalAlignment*/int value) { setAttr(SectAttr.VERTICAL_ALIGNMENT, value); }

    /// <summary>
    /// Specifies that this section contains bidirectional (complex scripts) text.
    /// </summary>
    /// <remarks>
    /// <p>When <c>true</c>, the columns in this section are laid out from right to left.</p>
    /// </remarks>
    public boolean getBidi() { return (/*boolean*/Boolean)fetchAttr(SectAttr.BIDI); }
    public void setBidi(boolean value) { setAttr(SectAttr.BIDI, value); }

    /// <summary>
    /// Gets or sets the layout mode of this section.
    /// </summary>
    public /*SectionLayoutMode*/int getLayoutMode() { return (/*SectionLayoutMode*/Integer)fetchAttr(SectAttr.GRID_TYPE); }
    public void setLayoutMode(/*SectionLayoutMode*/int value) { setAttr(SectAttr.GRID_TYPE, (int)value); }

    /// <summary>
    /// Gets or sets the number of characters per line in the document grid.
    /// </summary>
    /// <remarks>
    /// <para>Minimum value of the property is 1. Maximum value depends on page width and font size of the Normal
    /// style. Minimum character pitch is 90 percent of the font size. For example, maximum number of characters
    /// per line of a Letter page with one-inch margins is 43.</para>
    /// <para>By default, the property has a value, on which character pitch equals to font size of the Normal
    /// style.</para>
    /// </remarks>
    public int getCharactersPerLine()
    {
        Debug.assertTrue(mStyles != null);
        Style normalStyle = mStyles.getByStyleIdentifier(StyleIdentifier.NORMAL);

        double pitch = normalStyle.getFont().getSize() + getCharSpace() / 4096d;

        // MS Word calculates average column width with using space that is set in the 'cols' element, it does
        // not calculate sum of all column spaces: do the same.
        return (int)msMath.truncate(getTextColumns().getWidth() / pitch);
    }
    public void setCharactersPerLine(int value)
    {
        if (getCharactersPerLine() != value)
        {
            Debug.assertTrue(mStyles != null);
            Style normalStyle = mStyles.getByStyleIdentifier(StyleIdentifier.NORMAL);

            // Looks like MS Word uses the 0.9 factor without any depending on font face.
            int maxValue = (int)msMath.truncate(getTextColumns().getWidth() / (normalStyle.getFont().getSize() * 0.9));

            ArgumentUtil.checkRangeInclusive(value, 1, maxValue, "CharsPerLine");

            double pitch = getTextColumns().getWidth() / value;
            // We need to round to less integer to make the getter return the same value.
            setCharSpace((int)msMath.truncate((pitch - normalStyle.getFont().getSize()) * 4096.0));
        }
    }

    /// <summary>
    /// Gets or sets the number of lines per page in the document grid.
    /// </summary>
    /// <remarks>
    /// <para>Minimum value of the property is 1. Maximum value depends on page height and font size of the Normal
    /// style. Minimum line pitch is 136 percent of the font size. For example, maximum number of lines per page of
    /// a Letter page with one-inch margins is 39.</para>
    /// <para>By default, the property has a value, on which line pitch is in 1.5 times greater than font size of
    /// the Normal style.</para>
    /// </remarks>
    public int getLinesPerPage() { return (int)msMath.truncate(getContentHeightInTextDirection() / getLinePitch()); }
    public void setLinesPerPage(int value)
    {
        if (getLinesPerPage() != value)
        {
            Debug.assertTrue(mStyles != null);
            Style normalStyle = mStyles.getByStyleIdentifier(StyleIdentifier.NORMAL);

            // Looks like MS Word uses the 1.36 factor without any depending on font face.
            int maxValue = (int)msMath.truncate(getContentHeightInTextDirection() / normalStyle.getFont().getSize() / 1.36);
            ArgumentUtil.checkRangeInclusive(value, 1, maxValue, "LinesPerPage");

            // We need to round to less integer to make the getter return the same value.
            setAttr(SectAttr.LINE_PITCH,
                (int)msMath.truncate(getContentHeightInTextDirection() / value * ConvertUtilCore.TWIPS_PER_POINT));
        }
    }

    /// <summary>
    /// Returns or sets the width of the page in points.
    /// </summary>
    public double getPageWidth() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchAttr(SectAttr.PAGE_WIDTH)); }
    public void setPageWidth(double value) { setAttr(SectAttr.PAGE_WIDTH, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Returns or sets the height of the page in points.
    /// </summary>
    public double getPageHeight() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchAttr(SectAttr.PAGE_HEIGHT)); }
    public void setPageHeight(double value) { setAttr(SectAttr.PAGE_HEIGHT, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Returns or sets preset <see cref="Aspose.Words.Margins"/> of the page.
    /// </summary>
    public /*Margins*/int getMargins()
    {
        /*Margins*/int[] marginTypes = new /*Margins*/int[]
        {
            Margins.NORMAL, Margins.NARROW, Margins.MODERATE, Margins.WIDE, Margins.MIRRORED
        };

        for (/*Margins*/int margins : marginTypes)
        {
            LocaleDefaults.Margins marginValues = getMarginValues(margins);

            if ((marginValues.getLeft() == (/*int*/Integer)fetchAttr(SectAttr.LEFT_MARGIN)) &&
                (marginValues.getRight() == (/*int*/Integer)fetchAttr(SectAttr.RIGHT_MARGIN)) &&
                (marginValues.getTop() == (/*int*/Integer)fetchAttr(SectAttr.TOP_MARGIN)) &&
                (marginValues.getBottom() == (/*int*/Integer)fetchAttr(SectAttr.BOTTOM_MARGIN)) &&
                ((margins != Margins.MIRRORED) || (mDocPr.MultiplePages == MultiplePagesType.MIRROR_MARGINS)))
                return margins;
        }

        return Margins.CUSTOM;
    }

    public void setMargins(/*Margins*/int value)
    {
        LocaleDefaults.Margins marginValues = getMarginValues(value);
        setAttr(SectAttr.LEFT_MARGIN, marginValues.getLeft());
        setAttr(SectAttr.RIGHT_MARGIN, marginValues.getRight());
        setAttr(SectAttr.TOP_MARGIN, marginValues.getTop());
        setAttr(SectAttr.BOTTOM_MARGIN, marginValues.getBottom());

        if (value == Margins.MIRRORED)
            mDocPr.MultiplePages = MultiplePagesType.MIRROR_MARGINS;
    }

    private static LocaleDefaults.Margins getMarginValues(/*Margins*/int margins)
    {
        if (margins == Margins.NORMAL)
            return LocaleDefaults.getPageMargins(SectPr.getCurrentLcid());

        return G_PAGE_MARGINS_PRESETS.get(margins);
    }

    /*SizeF*/long getPageSize() { return msSizeF.ctor((float)getPageWidth(), (float)getPageHeight()); }

    /// <summary>
    /// True if the gutter setting is applicable and it is a left gutter.
    /// </summary>
    boolean isLeftGutter() { return (mDocPr.isGutterSide() && !getRtlGutter()); }

    /// <summary>
    /// True if the gutter setting is applicable and it is a right gutter.
    /// </summary>
    boolean isRightGutter() { return (mDocPr.isGutterSide() && getRtlGutter()); }

    /// <summary>
    /// Gets distance of the left side of content area of page from the left page bound.
    /// </summary>
    /// <remarks>
    /// Note that the returned value may be not correct for even pages of the section since margins and gutter
    /// position may be different on odd and even pages depending on value of the <see cref="MultiplePages"/>
    /// property. Also MS Word may calculate margin size including gutter and page content size differently
    /// for different features/aspects (e.g. for column dimension, shape positioning, header/footer distance).
    /// </remarks>
    float getContentLeft()
    {
        PageMarginCalculator calculator = createPageMarginCalculator();
        return (float)ConvertUtilCore.twipToPoint(calculator.getMarginWithGutter(PageSide.LEFT, true));
    }

    /// <summary>
    /// Gets distance of the right side of content area of page from the left page bound.
    /// </summary>
    /// <remarks>
    /// Note that the returned value may be not correct for even pages of the section since margins and gutter
    /// position may be different on odd and even pages depending on value of the <see cref="MultiplePages"/>
    /// property. Also MS Word may calculate margin size including gutter and page content size differently
    /// for different features/aspects (e.g. for column dimension, shape positioning, header/footer distance).
    /// </remarks>
    float getContentRight() { return getContentLeft() + getContentWidth(); }

    /// <summary>
    /// Gets distance of the top side of content area of page from the top page bound.
    /// </summary>
    /// <remarks>
    /// Note that the returned value may be not correct for even pages of the section since margins and gutter
    /// position may be different on odd and even pages depending on value of the <see cref="MultiplePages"/>
    /// property. Also MS Word may calculate margin size including gutter and page content size differently
    /// for different features/aspects (e.g. for column dimension, shape positioning, header/footer distance).
    /// </remarks>
    float getContentTop()
    {
        PageMarginCalculator calculator = createPageMarginCalculator();
        return (float)ConvertUtilCore.twipToPoint(calculator.getMarginWithGutter(PageSide.TOP, true));
    }

    /// <summary>
    /// Gets distance of the bottom side of content area of page from the top page bound.
    /// </summary>
    /// <remarks>
    /// Note that the returned value may be not correct for even pages of the section since margins and gutter
    /// position may be different on odd and even pages depending on value of the <see cref="MultiplePages"/>
    /// property. Also MS Word may calculate margin size including gutter and page content size differently
    /// for different features/aspects (e.g. for column dimension, shape positioning, header/footer distance).
    /// </remarks>
    float getContentBottom() { return getContentTop() + getContentHeight(); }

    /// <summary>
    /// Returns page width minus left and right margins and minus gutter (if it is on the left or right side).
    /// </summary>
    /// <remarks>
    /// Note that MS Word may calculate margin size including gutter and page content size differently for
    /// different features/aspects (e.g. for column dimension, shape positioning, header/footer distance).
    /// So, the returned value may be not entirely correct in some cases.
    /// </remarks>
    float getContentWidth()
    {
        PageMarginCalculator calculator = createPageMarginCalculator();
        int leftMarginTwips = calculator.getMarginWithGutter(PageSide.LEFT, true);
        int rightMarginTwips = calculator.getMarginWithGutter(PageSide.RIGHT, true);

        return (float)(getPageWidth() - ConvertUtilCore.twipToPoint(leftMarginTwips + rightMarginTwips));
    }

    /// <summary>
    /// Returns page height minus top and bottom margins and minus gutter (if it is on the top or bottom side).
    /// </summary>
    /// <remarks>
    /// Note that MS Word may calculate margin size including gutter and page content size differently for
    /// different features/aspects (e.g. for column dimension, shape positioning, header/footer distance).
    /// So, the returned value may be not entirely correct in some cases. Using <see cref="PageMarginCalculator"/>
    /// may solve such a problem.
    /// </remarks>
    float getContentHeight()
    {
        PageMarginCalculator calculator = createPageMarginCalculator();
        int topMarginTwips = calculator.getMarginWithGutter(PageSide.TOP, true);
        int bottomMarginTwips = calculator.getMarginWithGutter(PageSide.BOTTOM, true);

        return (float)(getPageHeight() - ConvertUtilCore.twipToPoint(topMarginTwips + bottomMarginTwips));
    }

    /// <summary>
    /// Returns content width depending on text direction.
    /// </summary>
    double getContentWidthInTextDirection() { return isHorizontalTextLine() ? getContentWidth() : getContentHeight(); }

    /// <summary>
    /// Returns content height depending on text direction.
    /// </summary>
    double getContentHeightInTextDirection() { return isHorizontalTextLine() ? getContentHeight() : getContentWidth(); }

    /// <summary>
    /// Returns or sets the paper size.
    /// </summary>
    /// <remarks>
    /// <para>Setting this property updates <see cref="PageWidth"/> and <see cref="PageHeight"/> values.
    /// Setting this value to <see cref="AsposePaperSize.Custom"/> does not change existing values.</para>
    /// </remarks>
    public /*PaperSize*/int getPaperSize()
    {
        return WordUtil.sizeToPaperSize((/*int*/Integer)fetchAttr(SectAttr.PAGE_WIDTH), (/*int*/Integer)fetchAttr(SectAttr.PAGE_HEIGHT), isLandscape());
    }
    public void setPaperSize(/*PaperSize*/int value)
    {
        // WORDSNET-1474 DV Setting PaperSize to Custom should not change the existing page width and height.
        if (value == PaperSize.CUSTOM)
            return;

        /*Size*/long size = WordUtil.paperSizeToSize(value);
        if (isLandscape())
        {
            // Flip width and height when in landscape mode.
            setAttr(SectAttr.PAGE_WIDTH, msSize.getHeight(size));
            setAttr(SectAttr.PAGE_HEIGHT, msSize.getWidth(size));
        }
        else
        {
            setAttr(SectAttr.PAGE_WIDTH, msSize.getWidth(size));
            setAttr(SectAttr.PAGE_HEIGHT, msSize.getHeight(size));
        }
    }

    private boolean isLandscape() { return (getOrientation() == Orientation.LANDSCAPE); }

    /// <summary>
    /// Returns or sets the orientation of the page.
    /// </summary>
    /// <remarks>
    /// <p>Changing <see cref="Orientation"/> swaps <see cref="PageWidth"/> and <see cref="PageHeight"/>.</p>
    /// </remarks>
    public /*Orientation*/int getOrientation() { return (/*Orientation*/Integer)fetchAttr(SectAttr.ORIENTATION); }
    public void setOrientation(/*Orientation*/int value)
    {
        if (getOrientation() != value)
        {
            setAttr(SectAttr.ORIENTATION, value);
            double temp = getPageWidth();
            setPageWidth(getPageHeight());
            setPageHeight(temp);

            // AM. Should we flip margins as well?
        }
    }

    /// <summary>
    /// Returns or sets the distance (in points) between the left edge of the page and the left boundary of the body text.
    /// </summary>
    public double getLeftMargin() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchAttr(SectAttr.LEFT_MARGIN)); }
    public void setLeftMargin(double value) { setAttr(SectAttr.LEFT_MARGIN, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Returns or sets the distance (in points) between the right edge of the page and the right boundary of the body text.
    /// </summary>
    public double getRightMargin() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchAttr(SectAttr.RIGHT_MARGIN)); }
    public void setRightMargin(double value) { setAttr(SectAttr.RIGHT_MARGIN, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Returns or sets the distance (in points) between the top edge of the page and the top boundary of the body text.
    /// </summary>
    public double getTopMargin() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchAttr(SectAttr.TOP_MARGIN)); }
    public void setTopMargin(double value) { setAttr(SectAttr.TOP_MARGIN, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Returns or sets the distance (in points) between the bottom edge of the page and the bottom boundary of the body text.
    /// </summary>
    public double getBottomMargin() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchAttr(SectAttr.BOTTOM_MARGIN)); }
    public void setBottomMargin(double value) { setAttr(SectAttr.BOTTOM_MARGIN, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Returns or sets the distance (in points) between the header and the top of the page.
    /// </summary>
    public double getHeaderDistance() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchAttr(SectAttr.HEADER_DISTANCE)); }
    public void setHeaderDistance(double value) { setAttr(SectAttr.HEADER_DISTANCE, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Returns or sets the distance (in points) between the footer and the bottom of the page.
    /// </summary>
    public double getFooterDistance() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchAttr(SectAttr.FOOTER_DISTANCE)); }
    public void setFooterDistance(double value) { setAttr(SectAttr.FOOTER_DISTANCE, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Gets or sets the amount of extra space added to the margin for document binding.
    /// </summary>
    public double getGutter() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchAttr(SectAttr.GUTTER)); }
    public void setGutter(double value) { setAttr(SectAttr.GUTTER, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Gets or sets the paper tray (bin) to use for the first page of a section.
    /// The value is implementation (printer) specific.
    /// </summary>
    public int getFirstPageTray() { return (/*int*/Integer)fetchAttr(SectAttr.FIRST_PAGE_TRAY); }
    public void setFirstPageTray(int value) { setAttr(SectAttr.FIRST_PAGE_TRAY, value); }

    /// <summary>
    /// Gets or sets the paper tray (bin) to be used for all but the first page of a section.
    /// The value is implementation (printer) specific.
    /// </summary>
    public int getOtherPagesTray() { return (/*int*/Integer)fetchAttr(SectAttr.OTHER_PAGES_TRAY); }
    public void setOtherPagesTray(int value) { setAttr(SectAttr.OTHER_PAGES_TRAY, value); }

    /// <summary>
    /// Gets or sets the heading level style that is applied to the chapter titles in the document.
    /// </summary>
    /// <remarks>
    /// <p>Can be a number from 0 through 9. 0 means no chapter number if applied to page number.</p>
    /// <p>Before you can create page numbers that include chapter numbers, the document headings must have a numbered outline format applied.</p>
    /// </remarks>
    public int getHeadingLevelForChapter() { return (/*int*/Integer)fetchAttr(SectAttr.HEADING_LEVEL_FOR_CHAPTER); }
    public void setHeadingLevelForChapter(int value) { setAttr(SectAttr.HEADING_LEVEL_FOR_CHAPTER, value); }

    /// <summary>
    /// Gets or sets the separator character that appears between the chapter number and the page number.
    /// </summary>
    /// <remarks>
    /// <p>Before you can create page numbers that include chapter numbers, the document headings must have a numbered outline format applied.</p>
    /// </remarks>
    public /*ChapterPageSeparator*/int getChapterPageSeparator() { return (/*ChapterPageSeparator*/Integer)fetchAttr(SectAttr.CHAPTER_PAGE_SEPARATOR); }
    public void setChapterPageSeparator(/*ChapterPageSeparator*/int value) { setAttr(SectAttr.CHAPTER_PAGE_SEPARATOR, value); }

    /// <summary>
    /// Gets or sets the page number format.
    /// </summary>
    public /*NumberStyle*/int getPageNumberStyle() { return (/*NumberStyle*/Integer)fetchAttr(SectAttr.PAGE_NUMBER_STYLE); }
    public void setPageNumberStyle(/*NumberStyle*/int value) { setAttr(SectAttr.PAGE_NUMBER_STYLE, value); }

    /// <summary>
    /// True if page numbering restarts at the beginning of the section.
    /// </summary>
    /// <remarks>
    /// If set to <c>false</c>, the <see cref="RestartPageNumbering"/> property will override the
    /// <see cref="PageStartingNumber"/> property so that page numbering can continue from the previous section.
    /// </remarks>
    public boolean getRestartPageNumbering() { return (/*boolean*/Boolean)fetchAttr(SectAttr.RESTART_PAGE_NUMBERING); }
    public void setRestartPageNumbering(boolean value) { setAttr(SectAttr.RESTART_PAGE_NUMBERING, value); }

    /// <summary>
    /// Gets or sets the starting page number of the section.
    /// </summary>
    /// <remarks>
    /// The <see cref="RestartPageNumbering"/> property, if set to <c>false</c>, will override the
    /// <see cref="PageStartingNumber"/> property so that page numbering can continue from the previous section.
    /// </remarks>
    public int getPageStartingNumber() { return (/*int*/Integer)fetchAttr(SectAttr.PAGE_STARTING_NUMBER); }
    public void setPageStartingNumber(int value) { setAttr(SectAttr.PAGE_STARTING_NUMBER, value); }

    /// <summary>
    /// Gets or sets the way line numbering runs  that is, whether it starts over at the beginning of a new
    /// page or section or runs continuously.
    /// </summary>
    public /*LineNumberRestartMode*/int getLineNumberRestartMode() { return (/*LineNumberRestartMode*/Integer)fetchAttr(SectAttr.LINE_NUMBER_RESTART_MODE); }
    public void setLineNumberRestartMode(/*LineNumberRestartMode*/int value) { setAttr(SectAttr.LINE_NUMBER_RESTART_MODE, value); }

    /// <summary>
    /// Returns or sets the numeric increment for line numbers.
    /// </summary>
    public int getLineNumberCountBy() { return (/*int*/Integer)fetchAttr(SectAttr.LINE_NUMBER_COUNT_BY); }
    public void setLineNumberCountBy(int value) { setAttr(SectAttr.LINE_NUMBER_COUNT_BY, value); }

    /// <summary>
    /// Gets or sets distance between the right edge of line numbers and the left edge of the document.
    /// </summary>
    /// <remarks>
    /// Set this property to zero for automatic distance between the line numbers and text of the document.
    /// </remarks>
    public double getLineNumberDistanceFromText() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchAttr(SectAttr.LINE_NUMBER_DISTANCE_FROM_TEXT)); }
    public void setLineNumberDistanceFromText(double value) { setAttr(SectAttr.LINE_NUMBER_DISTANCE_FROM_TEXT, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Gets or sets the starting line number.
    /// </summary>
    public int getLineStartingNumber() { return (/*int*/Integer)fetchAttr(SectAttr.LINE_STARTING_NUMBER); }
    public void setLineStartingNumber(int value) { setAttr(SectAttr.LINE_STARTING_NUMBER, value); }

    /// <summary>
    /// Returns a collection that represents the set of text columns.
    /// </summary>
    public TextColumnCollection getTextColumns()
    {
        if (mTextColumnsCache == null)
            mTextColumnsCache = new TextColumnCollection(this);
        return mTextColumnsCache;
    }

    /// <summary>
    /// Gets or sets whether Microsoft Word uses gutters for the section based on a right-to-left language or a left-to-right language.
    /// </summary>
    public boolean getRtlGutter() { return (/*boolean*/Boolean)fetchAttr(SectAttr.RTL_GUTTER); }
    public void setRtlGutter(boolean value) { setAttr(SectAttr.RTL_GUTTER, value); }

    /// <summary>
    /// Specifies where the page border is positioned relative to intersecting texts and objects.
    /// </summary>
    public boolean getBorderAlwaysInFront() { return (/*boolean*/Boolean)fetchAttr(SectAttr.BORDER_ALWAYS_IN_FRONT); }
    public void setBorderAlwaysInFront(boolean value) { setAttr(SectAttr.BORDER_ALWAYS_IN_FRONT, value); }

    /// <summary>
    /// Gets or sets a value that indicates whether the specified page border is measured from the edge of the page or from the text it surrounds.
    /// </summary>
    public /*PageBorderDistanceFrom*/int getBorderDistanceFrom() { return (/*PageBorderDistanceFrom*/Integer)fetchAttr(SectAttr.BORDER_DISTANCE_FROM); }
    public void setBorderDistanceFrom(/*PageBorderDistanceFrom*/int value) { setAttr(SectAttr.BORDER_DISTANCE_FROM, value); }

    /// <summary>
    /// Specifies which pages the page border is printed on.
    /// </summary>
    public /*PageBorderAppliesTo*/int getBorderAppliesTo() { return (/*PageBorderAppliesTo*/Integer)fetchAttr(SectAttr.BORDER_APPLIES_TO); }
    public void setBorderAppliesTo(/*PageBorderAppliesTo*/int value) { setAttr(SectAttr.BORDER_APPLIES_TO, value); }

    /// <summary>
    /// Specifies whether the page border includes or excludes the header.
    /// </summary>
    /// <remarks>
    /// Note, changing this property affects all sections in the document.
    /// </remarks>
    public boolean getBorderSurroundsHeader() { return !mDocPr.BordersDoNotSurroundHeader; }
    public void setBorderSurroundsHeader(boolean value) { mDocPr.BordersDoNotSurroundHeader = !value; }

    /// <summary>
    /// Specifies whether the page border includes or excludes the footer.
    /// </summary>
    /// <remarks>
    /// Note, changing this property affects all sections in the document.
    /// </remarks>
    public boolean getBorderSurroundsFooter() { return !mDocPr.BordersDoNotSurroundFooter; }
    public void setBorderSurroundsFooter(boolean value) { mDocPr.BordersDoNotSurroundFooter = !value; }

    /// <summary>
    /// Gets a collection of the page borders.
    /// </summary>
    public BorderCollection getBorders()
    {
        if (mBordersCache == null)
            mBordersCache = new BorderCollection(this);
        return mBordersCache;
    }

    /// <summary>
    /// Provides options that control numbering and positioning of footnotes in this section.
    /// </summary>
    public FootnoteOptions getFootnoteOptions()
    {
        if (mFootnoteOptionsCache == null)
            mFootnoteOptionsCache = new FootnoteOptions(mParent);
        return mFootnoteOptionsCache;
    }

    /// <summary>
    /// Provides options that control numbering and positioning of endnotes in this section.
    /// </summary>
    public EndnoteOptions getEndnoteOptions()
    {
        if (mEndnoteOptionsCache == null)
            mEndnoteOptionsCache = new EndnoteOptions(mParent);
        return mEndnoteOptionsCache;
    }

    /// <summary>
    /// Allows to specify <see cref="TextOrientation"/> for the whole page.
    /// Default value is <see cref="Aspose.Words.TextOrientation.Horizontal"/>
    /// </summary>
    /// <remarks>This property is only supported for MS Word native formats DOCX, WML, RTF and DOC.</remarks>
    public /*TextOrientation*/int getTextOrientation() { return TextFlowOrientationConverter.fromTextFlow(getTextFlow()); }
    public void setTextOrientation(/*TextOrientation*/int value) { setTextFlow(TextFlowOrientationConverter.fromTextOrientation(value)); }

    /// <summary>
    /// Gets or sets space between characters that allows defining the number of characters per line for a document.
    /// docGrid/@char-space
    /// </summary>
    /// <remarks>
    /// This attribute's value shall be specified by multiplying the difference between the desired character
    /// pitch and the character pitch for that character in the font size of the Normal font by 4096. If this
    /// attribute is omitted, the default value is zero.
    /// </remarks>
    int getCharSpace() { return (/*int*/Integer)fetchAttr(SectAttr.CHAR_SPACE); }
    void setCharSpace(int value) { setAttr(SectAttr.CHAR_SPACE, value); }

    /// <summary>
    /// Gets or sets the line pitch and space between lines. The number of lines
    /// per page will automatically be adjusted to fit the space between the lines.
    /// docGrid/@line-pitch
    /// </summary>
    double getLinePitch() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchAttr(SectAttr.LINE_PITCH)); }
    void setLinePitch(double value) { setAttr(SectAttr.LINE_PITCH, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Specifies the text direction within this section.
    /// </summary>
    /*TextFlow*/int getTextFlow() { return (/*TextFlow*/Integer)fetchAttr(SectAttr.TEXT_FLOW); }
    void setTextFlow(/*TextFlow*/int value) { setAttr(SectAttr.TEXT_FLOW, value); }

    /// <summary>
    /// Gets or sets internal paper code to ensure the proper type is chosen if size matches size
    /// of multiple paper types supported by your printer.
    ///
    /// Somewhat obscure field, does not look like PaperSize anyway.
    /// pgSz/@code in WordML.
    /// </summary>
    int getPaperCode() { return (/*int*/Integer)fetchAttr(SectAttr.PAPER_CODE); }
    void setPaperCode(int value) { setAttr(SectAttr.PAPER_CODE, value); }

    /// <summary>
    /// True when this section is unprotected in a protected document.
    /// </summary>
    boolean getUnlocked() { return (/*boolean*/Boolean)fetchAttr(SectAttr.UNLOCKED); }
    void setUnlocked(boolean value) { setAttr(SectAttr.UNLOCKED, value); }

    ISectionAttrSource getParent() { return mParent; }

    /// <summary>
    /// Gets a flag indicating that text line is oriented horizontally.
    /// </summary>
    private boolean isHorizontalTextLine()
    {
        return getTextOrientation() == TextOrientation.HORIZONTAL ||
            getTextOrientation() == TextOrientation.HORIZONTAL_ROTATED_FAR_EAST;
    }

    private Object fetchAttr(int key)
    {
        if ((mDocument != null) && (mDocument.getRevisionsView() == RevisionsView.FINAL))
            return fetchAttr(key, RevisionsView.FINAL);

        return mParent.fetchSectionAttr(key);
    }

    private Object fetchAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return mParent.fetchSectionAttr(key, revisionsView);
    }

    private void setAttr(int key, Object value)
    {
        if ((mDocument != null) && (mDocument.getRevisionsView() == RevisionsView.FINAL))
            setAttr(key, value, RevisionsView.FINAL);
        else
            mParent.setSectionAttr(key, value);
    }

    private void setAttr(int key, Object value, /*RevisionsView*/int revisionsView)
    {
        mParent.setSectionAttr(key, value, revisionsView);
    }

    /// <summary>
    /// Creates an instance of <see cref="PageMarginCalculator"/> that allows calculation of page margins and
    /// other metrics for different multiple page setups.
    /// </summary>
    private PageMarginCalculator createPageMarginCalculator()
    {
        return new PageMarginCalculator(mParent, mDocPr,
            (/*Orientation*/Integer)mFirstSectionAttrSource.fetchSectionAttr(SectAttr.ORIENTATION));
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IBorderAttrSource.*/getDirectBorderAttr(int key)
    {
        return mParent.getDirectSectionAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IBorderAttrSource.*/fetchInheritedBorderAttr(int key)
    {
        return mParent.fetchInheritedSectionAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IBorderAttrSource.*/setBorderAttr(int key, Object value)
    {
        mParent.setSectionAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public SortedList<Integer, Integer> /*IBorderAttrSource.*/getPossibleBorderKeys() { return G_PAGE_BORDERS; }

    private /*final*/ Document mDocument;
    private /*final*/ ISectionAttrSource mParent;
    private /*final*/ DocPr mDocPr;
    private /*final*/ StyleCollection mStyles;
    private /*final*/ ISectionAttrSource mFirstSectionAttrSource;
    private TextColumnCollection mTextColumnsCache;
    private BorderCollection mBordersCache;
    private FootnoteOptions mFootnoteOptionsCache;
    private EndnoteOptions mEndnoteOptionsCache;

    /// <summary>
    /// Map of possible page border types to attribute names.
    /// </summary>
    private static /*final*/ SortedList<Integer, Integer> G_PAGE_BORDERS = new SortedList<Integer, Integer>();

    /// <summary>
    /// Map of page margins presets.
    /// </summary>
    private static /*final*/ HashMap<Integer, LocaleDefaults.Margins> G_PAGE_MARGINS_PRESETS = new HashMap<Integer, LocaleDefaults.Margins>();

    /// <summary>
    /// Static ctor.
    /// </summary>
    static/* PageSetup()*/
    {
        G_PAGE_BORDERS.add(BorderType.TOP, SectAttr.BORDER_TOP);
        G_PAGE_BORDERS.add(BorderType.LEFT, SectAttr.BORDER_LEFT);
        G_PAGE_BORDERS.add(BorderType.BOTTOM, SectAttr.BORDER_BOTTOM);
        G_PAGE_BORDERS.add(BorderType.RIGHT, SectAttr.BORDER_RIGHT);

        msDictionary.add(G_PAGE_MARGINS_PRESETS, Margins.NARROW, new LocaleDefaults.Margins(720, 720, 720, 720));
        msDictionary.add(G_PAGE_MARGINS_PRESETS, Margins.MODERATE, new LocaleDefaults.Margins(1080, 1080, 1440, 1440));
        msDictionary.add(G_PAGE_MARGINS_PRESETS, Margins.WIDE, new LocaleDefaults.Margins(2880, 2880, 1440, 1440));
        msDictionary.add(G_PAGE_MARGINS_PRESETS, Margins.MIRRORED, new LocaleDefaults.Margins(1800, 1440, 1440, 1440));
    }
}

