// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2005 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import com.aspose.ms.System.msObject;
import java.awt.Color;
import com.aspose.DrColor;
import java.util.Iterator;
import com.aspose.words.ref.RefInt;


/// <summary>
/// A collection of <see cref="Border"/> objects.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/programming-with-documents/">Programming with Documents</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Different document elements have different borders.
/// For example, <see cref="ParagraphFormat"/> has <see cref="Bottom"/>, <see cref="Left"/>, <see cref="Right"/> and <see cref="Top"/> borders.
/// You can specify different formatting for each border independently or
/// enumerate through all borders and apply same formatting.
/// </remarks>
/// <dev>
/// This is a presentation object only, does not store any model data.
/// </dev>
public final class BorderCollection implements Iterable<Border>
{
    BorderCollection(IBorderAttrSource parent)
    {
        mParent = parent;
    }

    /// <summary>
    /// Compares collections of borders.
    /// </summary>
    public boolean equals(BorderCollection brColl) throws Exception
    {
        if (msObject.referenceEquals(this, brColl))
        {
            return true;
        }

        return (getLeft().equals(brColl.getLeft())) &&
               (getRight().equals(brColl.getRight())) &&
               (getTop().equals(brColl.getTop())) &&
               (getBottom().equals(brColl.getBottom())) &&
               (getHorizontal().equals(brColl.getHorizontal())) &&
               (getVertical().equals(brColl.getVertical())) &&
               (getColor().equals(brColl.getColor())) &&
               (getColorInternal().equals(brColl.getColorInternal())) &&
               (((getDistanceFromText()) == (brColl.getDistanceFromText()))) &&
               (((getLineWidth()) == (brColl.getLineWidth()))) &&
               (getLineStyle() == brColl.getLineStyle()) &&
               (getCount() == brColl.getCount()) &&
               (getShadow() == brColl.getShadow()) &&
               (isVisible() == brColl.isVisible());
    }

    /// <overloads>Retrieves a <see cref="Border"/> object.</overloads>
    /// <summary>
    /// Retrieves a <see cref="Border"/> object by border type.
    /// </summary>
    /// <remarks>
    /// <p>Note that not all borders are present for different document elements.
    /// This method throws an exception if you request a border not applicable to the current object.</p>
    /// </remarks>
    /// <param name="borderType">A <see cref="BorderType"/> value
    /// that specifies the type of the border to retrieve.</param>
    public Border getByBorderType(/*BorderType*/int borderType) throws Exception
    {
        int borderKey = 0;
        RefInt referenceToBorderKey = new RefInt(borderKey);
        boolean outRefCondition0 = !mParent.getPossibleBorderKeys().tryGetValue(borderType, /*out*/ referenceToBorderKey);
        borderKey = referenceToBorderKey.get();
        if (outRefCondition0)
            throw new IllegalStateException("The requested border is not available for this object.");

        //<<GetOrCreateComplexAttr>> pattern
        Border border = (Border)mParent.getDirectBorderAttr(borderKey);
        if (border == null)
        {
            //If the border is not specified directly on this object, create an inherited border.
            border = new Border(mParent, borderKey);
            border = border;
            mParent.setBorderAttr(borderKey, border);
        }
        return border;
    }


    /// <summary>
    /// Retrieves a <see cref="Border"/> object by index.
    /// </summary>
    /// <param name="index">Zero-based index of the border to retrieve.</param>
    public Border get(int index) throws Exception
    {
        //Convert the index into border type and retrieve the border by border type.
        /*BorderType*/int borderType = mParent.getPossibleBorderKeys().getKeys().get(index);
        return this.getByBorderType(borderType);
    }

    /// <summary>
    /// Gets the left border.
    /// </summary>
    public Border getLeft() throws Exception { return this.getByBorderType(BorderType.LEFT); }

    /// <summary>
    /// Gets the right border.
    /// </summary>
    public Border getRight() throws Exception { return this.getByBorderType(BorderType.RIGHT); }

    /// <summary>
    /// Gets the top border.
    /// </summary>
    public Border getTop() throws Exception { return this.getByBorderType(BorderType.TOP); }

    /// <summary>
    /// Gets the bottom border.
    /// </summary>
    public Border getBottom() throws Exception { return this.getByBorderType(BorderType.BOTTOM); }

    /// <summary>
    /// Gets the horizontal border that is used between cells or conforming paragraphs.
    /// </summary>
    public Border getHorizontal() throws Exception { return this.getByBorderType(BorderType.HORIZONTAL); }

    /// <summary>
    /// Gets the vertical border that is used between cells.
    /// </summary>
    public Border getVertical() throws Exception { return this.getByBorderType(BorderType.VERTICAL); }

    /// <summary>
    /// Gets the number of borders in the collection.
    /// </summary>
    public int getCount() { return mParent.getPossibleBorderKeys().getCount(); }

    /// <summary>
    /// Gets or sets the border width in points.
    /// </summary>
    /// <remarks>
    /// <p>Returns the width of the first border in the collection.</p>
    /// <p>Sets the width of all borders in the collection excluding diagonal borders.</p>
    /// </remarks>
    public double getLineWidth() throws Exception { return this.get(0).getLineWidth(); }
    public void setLineWidth(double value) throws Exception
    {
        for (/*BorderType*/int borderType : (Iterable</*BorderType*/Integer>) mParent.getPossibleBorderKeys().getKeys())
        {
            if (isCommonBorder(borderType))
                this.getByBorderType(borderType).setLineWidth(value);
        }
    }

    /// <summary>
    /// Gets or sets the border style.
    /// </summary>
    /// <remarks>
    /// <p>Returns the style of the first border in the collection.</p>
    /// <p>Sets the style of all borders in the collection excluding diagonal borders.</p>
    /// </remarks>
    public /*LineStyle*/int getLineStyle() throws Exception { return this.get(0).getLineStyle(); }
    public void setLineStyle(/*LineStyle*/int value) throws Exception
    {
        for (/*BorderType*/int borderType : (Iterable</*BorderType*/Integer>) mParent.getPossibleBorderKeys().getKeys())
        {
            if (isCommonBorder(borderType))
                this.getByBorderType(borderType).setLineStyle(value);
        }
    }

    /// <summary>
    /// Gets or sets the border color.
    /// </summary>
    /// <remarks>
    /// <p>Returns the color of the first border in the collection.</p>
    /// <p>Sets the color of all borders in the collection excluding diagonal borders.</p>
    /// </remarks>
    public Color getColor() throws Exception { return getColorInternal().toNativeColor(); }
    public void setColor(Color value) throws Exception { setColorInternal(DrColor.fromNativeColor(value)); }

    DrColor getColorInternal() throws Exception { return this.get(0).getColorInternal(); }
    void setColorInternal(DrColor value) throws Exception
    {
        for (/*BorderType*/int borderType : (Iterable</*BorderType*/Integer>) mParent.getPossibleBorderKeys().getKeys())
        {
            if (isCommonBorder(borderType))
                this.getByBorderType(borderType).setColorInternal(value);
        }
    }

    boolean getFrame() throws Exception { return this.get(0).getFrame(); }
    void setFrame(boolean value) throws Exception
    {
        for (/*BorderType*/int borderType : (Iterable</*BorderType*/Integer>) mParent.getPossibleBorderKeys().getKeys())
        {
            if (isCommonBorder(borderType))
                this.getByBorderType(borderType).setFrame(value);
        }
    }

    /// <summary>
    /// Returns True if specified border is available in the collection.
    /// </summary>
    boolean isPossible(/*BorderType*/int borderType)
    {
        return mParent.getPossibleBorderKeys().containsKey(borderType);
    }

    /// <summary>
    /// Gets or sets distance of the border from text in points.
    /// </summary>
    /// <remarks>
    /// <p>Gets the distance from text for the first border.</p>
    /// <p>Sets the distance from text for all borders in the collection excluding diagonal borders.</p>
    /// <p>Has no effect and will be automatically reset to zero for borders of table cells.</p>
    /// </remarks>
    public double getDistanceFromText() throws Exception { return this.get(0).getDistanceFromText(); }
    public void setDistanceFromText(double value) throws Exception
    {
        for (/*BorderType*/int borderType : (Iterable</*BorderType*/Integer>) mParent.getPossibleBorderKeys().getKeys())
        {
            if (isCommonBorder(borderType))
                this.getByBorderType(borderType).setDistanceFromText(value);
        }
    }

    /// <summary>
    /// Gets or sets a value indicating whether the border has a shadow.
    /// </summary>
    /// <remarks>
    /// <p>Gets the value from the first border in the collection.</p>
    /// <p>Sets the value for all borders in the collection excluding diagonal borders.</p>
    /// </remarks>
    public boolean getShadow() throws Exception { return this.get(0).getShadow(); }
    public void setShadow(boolean value) throws Exception
    {
        for (/*BorderType*/int borderType : (Iterable</*BorderType*/Integer>) mParent.getPossibleBorderKeys().getKeys())
        {
            if (isCommonBorder(borderType))
                this.getByBorderType(borderType).setShadow(value);
        }
    }

    /// <summary>
    /// Removes all borders of an object.
    /// </summary>
    public void clearFormatting()
    {
        for (Border border : this)
            border.clearFormatting();
    }

    /// <summary>
    /// Returns an enumerator object that can be used to iterate over all borders in the collection.
    /// </summary>
    public Iterator<Border> iterator()
    {
        return new BordersEnumerator(this);
    }

    /// <summary>
    /// Returns an enumerator object that can be used to iterate over all borders in the collection.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Returns true if at least one of the borders is visible.
    /// </summary>
    boolean isVisible()
    {
        for (Border border : this)
        {
            if (border.isVisible())
                return true;
        }
        return false;
    }

    /// <summary>
    /// Returns true if this is not a diagonal border.
    /// </summary>
    private static boolean isCommonBorder(/*BorderType*/int borderType)
    {
        return ((borderType != BorderType.DIAGONAL_DOWN) && (borderType != BorderType.DIAGONAL_UP));
    }

    /// <summary>
    /// Enumerates over the borders of an object. We need to implement this enumerator because:
    /// 1. The borders are not stored in a single collection that I can simply enumerate.
    /// 2. Some borders that might not yet exist and need to be created on demand.
    /// </summary>
    private final static class BordersEnumerator implements Iterator<Border>
    {
        BordersEnumerator(BorderCollection borders)
        {
            mBorders = borders;
            mBorderIndex = -1;
        }

        public void dispose()
        {
            // Nothing to dispose.
        }

        public boolean hasNext()
        {
            if (mBorderIndex >= (mBorders.getCount() - 1))
                return false;

            mBorderIndex++;
            return true;
        }

        public void reset()
        {
            mBorderIndex = -1;
        }

        
        public Border next() { 	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/ 	try 	{ return mBorders.get(mBorderIndex); 	} 	catch (Exception e) 	{ 		throw new RuntimeException(e); 	} }
        
        //JAVA-deleted Namesake Explicit Interface Implementation property:
        //Object IEnumerator.Current

        private /*final*/ BorderCollection mBorders;
        private int mBorderIndex;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }


    private /*final*/ IBorderAttrSource mParent;
}

