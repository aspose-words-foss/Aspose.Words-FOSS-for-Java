// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/04/2005 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;


/// <summary>
/// A collection of <see cref="TextColumn"/> objects that represent all the columns of text in a section of a document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-sections/">Working with Sections</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>Use <see cref="SetCount"/> to set the number of text columns.</p>
/// <p>To make all columns equal width and spaced evenly, set <see cref="EvenlySpaced"/> to <c>true</c>
/// and specify the amount of space between the columns in <see cref="Spacing"/>. MS Word will
/// automatically calculate column widths.</p>
/// <p>If you have <see cref="EvenlySpaced"/> set to <c>false</c>, you need to specify width and spacing for each
/// column individually. Use the indexer to access individual <see cref="TextColumn"/> objects.</p>
/// <p>When using custom column widths, make sure the sum of all column widths and spacings between them
/// equals page width minus left and right page margins.</p>
/// <seealso cref="PageSetup"/>
/// <seealso cref="Section"/>
/// </remarks>
/// <dev>
/// This is a pure presentation class. Does not store any model data, just provides friendly
/// access to section attributes that are related to managing text columns.
/// </dev>
public class TextColumnCollection
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="pageSetup">Essentially we just need an attribute source,
    /// but we use page width and margins in some calculations here so it is just convenient
    /// to know the page setup object and we can obtain attribute source from it too.</param>
    TextColumnCollection(PageSetup pageSetup)
    {
        mPageSetup = pageSetup;
    }

    /// <summary>
    /// Arranges text into the specified number of text columns.
    /// </summary>
    /// <remarks>
    /// <p>When <see cref="EvenlySpaced"/> is <c>false</c> and you increase the number of columns,
    /// new <see cref="TextColumn"/> objects are created with zero width and spacing.
    /// You need to set width and spacing for the new columns.</p>
    /// </remarks>
    /// <param name="newCount">The number of columns the text is to be arranged into.</param>
    public void setCount(int newCount)
    {
        if ((newCount < MIN_COLUMNS) || (newCount > MAX_COLUMNS))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "newCount");

        setAttr(SectAttr.COLUMNS_COUNT, newCount);
    }

    /// <summary>
    /// True if text columns are of equal width and evenly spaced.
    /// </summary>
    public boolean getEvenlySpaced() { return (/*boolean*/Boolean)fetchAttr(SectAttr.COLUMNS_EVENLY_SPACED); }
    public void setEvenlySpaced(boolean value) { setAttr(SectAttr.COLUMNS_EVENLY_SPACED, value); }

    /// <summary>
    /// When columns are evenly spaced, gets or sets the amount of space between each column in points.
    /// </summary>
    /// <remarks>
    /// Has effect only when <see cref="EvenlySpaced"/> is set to <c>true</c>.
    /// </remarks>
    public double getSpacing() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchAttr(SectAttr.COLUMNS_SPACING)); }
    public void setSpacing(double value) { setAttr(SectAttr.COLUMNS_SPACING, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// When columns are evenly spaced, gets the width of the columns.
    /// </summary>
    /// <remarks>
    /// <p>Has effect only when <see cref="EvenlySpaced"/> is set to <c>true</c>.</p>
    /// </remarks>
    public double getWidth()
    {
        double totalColumnsWidth = mPageSetup.getContentWidthInTextDirection() - getSpacing() * (getCount() - 1);
        return totalColumnsWidth / getCount();
    }

    /// <summary>
    /// When <c>true</c>, adds a vertical line between columns.
    /// </summary>
    public boolean getLineBetween() { return (/*boolean*/Boolean)fetchAttr(SectAttr.COLUMNS_LINE_BETWEEN); }
    public void setLineBetween(boolean value) { mPageSetup.getParent().setSectionAttr(SectAttr.COLUMNS_LINE_BETWEEN, value); }

    /// <summary>
    /// Gets the number of columns in the section of a document.
    /// </summary>
    public int getCount() { return getColumnsCount(); }

    /// <summary>
    /// Returns a text column at the specified index.
    /// </summary>
    public TextColumn get(int index) { return getColumns().get(index); }

    /// <summary>
    /// Gets the attribute from the collection or from default attributes, throws if cannot get it.
    /// </summary>
    private Object fetchAttr(int key)
    {
        return mPageSetup.getParent().fetchSectionAttr(key);
    }

    /// <summary>
    /// Gets or sets "SectAttr.ColumnsCount".
    /// </summary>
    private int getColumnsCount() { return (/*int*/Integer)fetchAttr(SectAttr.COLUMNS_COUNT); }
    private void setColumnsCount(int value) { mPageSetup.getParent().setSectionAttr(SectAttr.COLUMNS_COUNT, value); }

    /// <summary>
    /// Gets or sets the model data that represents custom column widths.
    /// Automatically creates the columns in the model if they are not yet there.
    /// If the custom columns were automatically created, their width and spacing will be all zeroes.
    /// </summary>
    /// <remarks>This method creates column attribute automatically.</remarks>
    private TextColumnCollectionInternal getColumns()
    {
        //<<GetOrCreateComplexAttr>> pattern
        TextColumnCollectionInternal columns = (TextColumnCollectionInternal)mPageSetup.getParent().getDirectSectionAttr(SectAttr.COLUMNS);
        if (null == columns)
        {
            columns = new TextColumnCollectionInternal();
            columns.setCount(getColumnsCount());
            mPageSetup.getParent().setSectionAttr(SectAttr.COLUMNS, columns);
        }
        return columns;
    }

    static final int MIN_COLUMNS = 1;
    static final int MAX_COLUMNS = 45;      // As per Word 2003.

    private /*final*/ PageSetup mPageSetup;

    /// <summary>
    /// This method is compiled in Aspose.Words.
    /// Sets value of an attribute.
    /// </summary>
    private void setAttr(int key, Object value)
    {
        if (key == SectAttr.COLUMNS_COUNT)
        {
            int newCount = (/*int*/Integer)value;

            // Keep the column counter and the actual number of columns in sync.
            setColumnsCount(newCount);
            getColumns().setCount(newCount);
        }
        else
        {
            mPageSetup.getParent().setSectionAttr(key, value);
        }
    }
}

