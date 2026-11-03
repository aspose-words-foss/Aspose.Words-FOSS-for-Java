// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/04/2019 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import java.util.Iterator;
import com.aspose.Debug;
import java.util.Collection;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;
import com.aspose.IntToObjDictionary;
import com.aspose.EnumUtilPal;


/// <summary>
/// Represents a collection of <see cref="ConditionalStyle"/> objects.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-tables/">Working with Tables</a> documentation article.</para>
/// </summary>
/// <remarks>
/// It is not possible to add or remove items from this collection. It contains permanent set of items: one item for
/// each value of the <see cref="ConditionalStyleType"/> enumeration type.
/// </remarks>
/// <dev>
/// With using public properties of the class, it is possible to get only conditional styles that correspond to items
/// of the <see cref="ConditionalStyleType"/> enumeration type. But a collection may contain also styles related
/// to <see cref="TableStyleOverrideType.None"/> and <see cref="TableStyleOverrideType.WholeTable"/> values, use the
/// this[TableStyleOverrideType] or <see cref="DefinedStyles"/> properties to get them.
/// </dev>
public final class ConditionalStyleCollection implements Iterable<ConditionalStyle>
{
    ConditionalStyleCollection(TableStyle parentStyle)
    {
        mParentStyle = parentStyle;
    }

    /// <summary>
    /// Clears all conditional styles of the table style.
    /// </summary>
    public void clearFormatting()
    {
        // Let's clear only formatting included in public API, i.e. not clear formatting of whole table and for
        // TableStyleOverrideType.None value that means whole table too.

        ConditionalStyle noneStyle = mItems.get((int)TableStyleOverrideType.NONE);
        ConditionalStyle tableStyle = mItems.get((int)TableStyleOverrideType.WHOLE_TABLE);

        mItems.clear();

        if (noneStyle != null)
            mItems.add((int)TableStyleOverrideType.NONE, noneStyle);
        if (tableStyle != null)
            mItems.add((int)TableStyleOverrideType.WHOLE_TABLE, tableStyle);
    }

    /// <summary>
    /// Returns an enumerator object that can be used to iterate over all conditional styles in the collection.
    /// </summary>
    /// <dev>
    /// Conditional styles related to <see cref="TableStyleOverrideType.None"/> and
    /// <see cref="TableStyleOverrideType.WholeTable"/> values are not included into the enumeration.
    /// </dev>
    public Iterator<ConditionalStyle> iterator()
    {
        return new ConditionalStylesEnumerator(this);
    }

    /// <summary>
    /// Returns an enumerator object that can be used to iterate over all conditional styles in the collection.
    /// </summary>
    /// <dev>
    /// Conditional styles related to <see cref="TableStyleOverrideType.None"/> and
    /// <see cref="TableStyleOverrideType.WholeTable"/> values are not included into the enumeration.
    /// </dev>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Adds a conditional style into the collection.
    /// </summary>
    void add(ConditionalStyle conditionalStyle)
    {
        mItems.add((int)conditionalStyle.getOverrideType(), conditionalStyle);
        conditionalStyle.setParentStyle(mParentStyle);
    }

    /// <summary>
    /// Moves the collection to the default state with no conditional styles defined.
    /// </summary>
    void clear()
    {
        mItems.clear();
    }

    /// <summary>
    /// Returns a flag indicating whether the collection contains a defined conditional style of that type.
    /// </summary>
    boolean containsTableStyleOverride(/*TableStyleOverrideType*/int type)
    {
        return mItems.containsKey((int)type);
    }

    /// <summary>
    /// Returns a flag indicating whether the collection contains a defined conditional style of that type.
    /// </summary>
    boolean containsConditionalStyle(/*ConditionalStyleType*/int type)
    {
        return containsTableStyleOverride(conditionalStyleTypeToOverrideType(type));
    }

    /// <summary>
    /// Converts value of <see cref="ConditionalStyleType"/> type to <see cref="TableStyleOverrideType"/>.
    /// </summary>
    private static /*TableStyleOverrideType*/int conditionalStyleTypeToOverrideType(/*ConditionalStyleType*/int value)
    {
        switch (value)
        {
            case ConditionalStyleType.FIRST_ROW:
                return TableStyleOverrideType.FIRST_ROW;
            case ConditionalStyleType.FIRST_COLUMN:
                return TableStyleOverrideType.FIRST_COLUMN;
            case ConditionalStyleType.LAST_ROW:
                return TableStyleOverrideType.LAST_ROW;
            case ConditionalStyleType.LAST_COLUMN:
                return TableStyleOverrideType.LAST_COLUMN;
            case ConditionalStyleType.ODD_ROW_BANDING:
                return TableStyleOverrideType.ODD_ROW_BANDING;
            case ConditionalStyleType.ODD_COLUMN_BANDING:
                return TableStyleOverrideType.ODD_COLUMN_BANDING;
            case ConditionalStyleType.EVEN_ROW_BANDING:
                return TableStyleOverrideType.EVEN_ROW_BANDING;
            case ConditionalStyleType.EVEN_COLUMN_BANDING:
                return TableStyleOverrideType.EVEN_COLUMN_BANDING;
            case ConditionalStyleType.TOP_LEFT_CELL:
                return TableStyleOverrideType.TOP_LEFT_CELL;
            case ConditionalStyleType.TOP_RIGHT_CELL:
                return TableStyleOverrideType.TOP_RIGHT_CELL;
            case ConditionalStyleType.BOTTOM_LEFT_CELL:
                return TableStyleOverrideType.BOTTOM_LEFT_CELL;
            case ConditionalStyleType.BOTTOM_RIGHT_CELL:
                return TableStyleOverrideType.BOTTOM_RIGHT_CELL;
            default:
            {
                Debug.assertTrue(false);
                return TableStyleOverrideType.NONE;
            }
        }
    }

    /// <overloads>
    /// Retrieves a <see cref="ConditionalStyle"/> object.
    /// </overloads>
    /// <summary>
    /// Retrieves a <see cref="ConditionalStyle"/> object by conditional style type.
    /// </summary>
    public ConditionalStyle getByConditionalStyleType(/*ConditionalStyleType*/int conditionalStyleType)
    {
        /*TableStyleOverrideType*/int type = conditionalStyleTypeToOverrideType(conditionalStyleType);


        return this.getByTableStyleOverrideType(type);
    }

    /// <summary>
    /// Retrieves a <see cref="ConditionalStyle"/> object by table style override type.
    /// </summary>
    ConditionalStyle getByTableStyleOverrideType(/*TableStyleOverrideType*/int overrideType)
    {
        ConditionalStyle result;
        if (!mItems.containsKey((int)overrideType))
        {
            result = new ConditionalStyle(overrideType, mParentStyle);
            mItems.add((int)overrideType, result);
        }
        else
        {
            result = mItems.get((int)overrideType);
        }

        return result;
    }

    /// <summary>
    /// Retrieves a <see cref="ConditionalStyle"/> object by index.
    /// </summary>
    /// <param name="index">Zero-based index of the conditional style to retrieve.</param>
    public ConditionalStyle get(int index)
    {
        /*ConditionalStyleType*/int[] types = (/*ConditionalStyleType*/int[])ConditionalStyleType.getValues();
        return this.getByConditionalStyleType(types[index]);
    }

    /// <summary>
    /// Gets the number of conditional styles in the collection.
    /// </summary>
    public int getCount() { return G_COUNT; }

    /// <summary>
    /// Gets all defined conditional styles of the collection including internal ones (None and WholeTable).
    /// </summary>
    Collection<ConditionalStyle> getDefinedStyles()
    {
        return msArrayList.ctor(mItems.getValues());
    }

    /// <summary>
    /// Gets the first row style.
    /// </summary>
    public ConditionalStyle getFirstRow() { return this.getByConditionalStyleType(ConditionalStyleType.FIRST_ROW); }

    /// <summary>
    /// Gets the first column style.
    /// </summary>
    public ConditionalStyle getFirstColumn() { return this.getByConditionalStyleType(ConditionalStyleType.FIRST_COLUMN); }

    /// <summary>
    /// Gets the last row style.
    /// </summary>
    public ConditionalStyle getLastRow() { return this.getByConditionalStyleType(ConditionalStyleType.LAST_ROW); }

    /// <summary>
    /// Gets the last column style.
    /// </summary>
    public ConditionalStyle getLastColumn() { return this.getByConditionalStyleType(ConditionalStyleType.LAST_COLUMN); }

    /// <summary>
    /// Gets the odd row banding style.
    /// </summary>
    public ConditionalStyle getOddRowBanding() { return this.getByConditionalStyleType(ConditionalStyleType.ODD_ROW_BANDING); }

    /// <summary>
    /// Gets the odd column banding style.
    /// </summary>
    public ConditionalStyle getOddColumnBanding() { return this.getByConditionalStyleType(ConditionalStyleType.ODD_COLUMN_BANDING); }

    /// <summary>
    /// Gets the even row banding style.
    /// </summary>
    public ConditionalStyle getEvenRowBanding() { return this.getByConditionalStyleType(ConditionalStyleType.EVEN_ROW_BANDING); }

    /// <summary>
    /// Gets the even column banding style.
    /// </summary>
    public ConditionalStyle getEvenColumnBanding() { return this.getByConditionalStyleType(ConditionalStyleType.EVEN_COLUMN_BANDING); }

    /// <summary>
    /// Gets the top left cell style.
    /// </summary>
    public ConditionalStyle getTopLeftCell() { return this.getByConditionalStyleType(ConditionalStyleType.TOP_LEFT_CELL); }

    /// <summary>
    /// Gets the top right cell style.
    /// </summary>
    public ConditionalStyle getTopRightCell() { return this.getByConditionalStyleType(ConditionalStyleType.TOP_RIGHT_CELL); }

    /// <summary>
    /// Gets the bottom left cell style.
    /// </summary>
    public ConditionalStyle getBottomLeftCell() { return this.getByConditionalStyleType(ConditionalStyleType.BOTTOM_LEFT_CELL); }

    /// <summary>
    /// Gets the bottom right cell style.
    /// </summary>
    public ConditionalStyle getBottomRightCell() { return this.getByConditionalStyleType(ConditionalStyleType.BOTTOM_RIGHT_CELL); }


    private /*final*/ TableStyle mParentStyle;
    // Dictionary<Enum,ConditionalStyle> is changed to IntToObjDictionary<ConditionalStyle> for Java
    // to get consistent sorting (Enum is autoported as static int constant to Java).
    private /*final*/ IntToObjDictionary<ConditionalStyle> mItems = new IntToObjDictionary<ConditionalStyle>();

    private static /*final*/ int G_COUNT =
        EnumUtilPal.getEffectiveArrayLength((12));

    /// <summary>
    /// Enumerates over the conditional styles of a collection. We need to implement this enumerator because it
    /// should include all possible public conditional styles (without None and WholeTable) even non-created yet.
    /// </summary>
    private final static class ConditionalStylesEnumerator implements Iterator<ConditionalStyle>
    {
        ConditionalStylesEnumerator(ConditionalStyleCollection collection)
        {
            mCollection = collection;
            reset();
        }

        public void dispose()
        {
            // Nothing to dispose.
        }

        public boolean hasNext()
        {
            if (mIndex >= (mCollection.getCount() - 1))
                return false;

            mIndex++;
            return true;
        }

        public void reset()
        {
            mIndex = -1;
        }

        
        public ConditionalStyle next() { 	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/ 	try 	{ return mCollection.get(mIndex); 	} 	catch (Exception e) 	{ 		throw new RuntimeException(e); 	} }

        //JAVA-deleted Namesake Explicit Interface Implementation property:
        //Object IEnumerator.Current

        private /*final*/ ConditionalStyleCollection mCollection;
        private int mIndex;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }
}

