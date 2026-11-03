// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/07/2006 by Roman Korchagin


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;
import com.aspose.ArgumentUtil;


/// <summary>
/// Defines the text and formatting of the text path (of a WordArt object).
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-shapes/">Working with Shapes</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>Use the <see cref="Shape.TextPath"/> property to access WordArt properties of a shape.
/// You do not create instances of the <see cref="TextPath"/> class directly.</p>
/// 
/// <seealso cref="Shape.TextPath"/>
/// </remarks>
public class TextPath
{
    TextPath(IShapeAttrSource parent)
    {
        mParent = parent;
    }

    /// <summary>
    /// Defines whether the text is displayed. 
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean getOn() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.GEO_TEXT_ON); }
    public void setOn(boolean value) { setAttr(ShapeAttr.GEO_TEXT_ON, value); }

    /// <summary>
    /// Defines whether the text fits the path of a shape.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean getFitPath() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.GEO_TEXT_BEST_FIT); }
    public void setFitPath(boolean value) { setAttr(ShapeAttr.GEO_TEXT_BEST_FIT, value); }

    /// <summary>
    /// Defines whether the text fits bounding box of a shape. 
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean getFitShape() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.GEO_TEXT_STRETCH); }
    public void setFitShape(boolean value) { setAttr(ShapeAttr.GEO_TEXT_STRETCH, value); }

    /// <summary>
    /// Defines the family of the textpath font. 
    /// </summary>
    /// <remarks>
    /// <p>The default value is Arial.</p>
    /// </remarks>
    public String getFontFamily() { return (String)fetchAttr(ShapeAttr.GEO_TEXT_FONT); }
    public void setFontFamily(String value) { setAttr(ShapeAttr.GEO_TEXT_FONT, value); }

    /// <summary>
    /// Defines the size of the font in points.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 36.</p>
    /// </remarks>
    public double getSize() { return ConvertUtilCore.fixedToDouble((/*int*/Integer)fetchAttr(ShapeAttr.GEO_TEXT_SIZE)); }
    public void setSize(double value) { setAttr(ShapeAttr.GEO_TEXT_SIZE, ConvertUtilCore.doubleToFixed(value)); }

    /// <summary>
    /// True if the font is formatted as bold.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean getBold() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.GEO_TEXT_BOLD); }
    public void setBold(boolean value) { setAttr(ShapeAttr.GEO_TEXT_BOLD, value); }

    /// <summary>
    /// True if the font is formatted as italic.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean getItalic() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.GEO_TEXT_ITALIC); }
    public void setItalic(boolean value) { setAttr(ShapeAttr.GEO_TEXT_ITALIC, value); }

    /// <summary>
    /// True if the font is formatted as small capital letters.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean getSmallCaps() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.GEO_TEXT_SMALL_CAPS); }
    public void setSmallCaps(boolean value) { setAttr(ShapeAttr.GEO_TEXT_SMALL_CAPS, value); }

    /// <summary>
    /// Determines whether the letters of the text are rotated. 
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean getRotateLetters() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.GEO_TEXT_VERTICAL); }
    public void setRotateLetters(boolean value) { setAttr(ShapeAttr.GEO_TEXT_VERTICAL, value); }

    /// <summary>
    /// Determines whether extra space is removed above and below the text.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean getTrim() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.GEO_TEXT_SHRINK_FIT); }
    public void setTrim(boolean value) { setAttr(ShapeAttr.GEO_TEXT_SHRINK_FIT, value); }
    
    /// <summary>
    /// Determines whether kerning is turned on.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean getKerning() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.GEO_TEXT_KERNING); }
    public void setKerning(boolean value) { setAttr(ShapeAttr.GEO_TEXT_KERNING, value); }

    /// <summary>
    /// Defines whether a shadow is applied to the text on a text path.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean getShadow() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.GEO_TEXT_SHADOW); }
    public void setShadow(boolean value) { setAttr(ShapeAttr.GEO_TEXT_SHADOW, value); }

    /// <summary>
    /// True if the font is underlined.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean getUnderline() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.GEO_TEXT_UNDERLINE); }
    public void setUnderline(boolean value) { setAttr(ShapeAttr.GEO_TEXT_UNDERLINE, value); }

    /// <summary>
    /// True if the font is formatted as strikethrough text.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean getStrikeThrough() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.GEO_TEXT_STRIKE_THROUGH); }
    public void setStrikeThrough(boolean value) { setAttr(ShapeAttr.GEO_TEXT_STRIKE_THROUGH, value); }

    /// <summary>
    /// Determines whether all letters will be the same height regardless of initial case.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean getSameLetterHeights() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.GEO_TEXT_NORMALIZE); }
    public void setSameLetterHeights(boolean value) { setAttr(ShapeAttr.GEO_TEXT_NORMALIZE, value); }

    /// <summary>
    /// Defines the text of the text path.
    /// </summary>
    /// <remarks>
    /// <p>The default value is an empty string.</p>
    /// </remarks>
    public String getText() { return (String)fetchAttr(ShapeAttr.GEO_TEXT_TEXT); }
    public void setText(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        setAttr(ShapeAttr.GEO_TEXT_TEXT, value);
    }

    /// <summary>
    /// Defines the alignment of text.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="Aspose.Words.Drawing.TextPathAlignment.Center"/>.</p>
    /// </remarks>
    public /*TextPathAlignment*/int getTextPathAlignment() { return (/*TextPathAlignment*/Integer)fetchAttr(ShapeAttr.GEO_TEXT_ALIGN); }
    public void setTextPathAlignment(/*TextPathAlignment*/int value) { setAttr(ShapeAttr.GEO_TEXT_ALIGN, value); }

    /// <summary>
    /// Determines whether the layout order of rows is reversed.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// <p>If <c>true</c>, the layout order of rows is reversed. This attribute is used for vertical text layout.</p>
    /// </remarks>
    public boolean getReverseRows() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.GEO_TEXT_REVERSE_ROWS); }
    public void setReverseRows(boolean value) { setAttr(ShapeAttr.GEO_TEXT_REVERSE_ROWS, value); }

    /// <summary>
    /// Defines the amount of spacing for text. 1 means 100%.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 1.</p>
    /// </remarks>
    public double getSpacing() { return ConvertUtilCore.fixedToDouble((/*int*/Integer)fetchAttr(ShapeAttr.GEO_TEXT_SPACING)); }
    public void setSpacing(double value)
    {
        setAttr(ShapeAttr.GEO_TEXT_SPACING, ConvertUtilCore.doubleToFixed(value));
        setAttr(ShapeAttr.GEO_TEXT_TIGHT, true);
    }

    /// <summary>
    /// Determines whether a straight textpath will be used instead of the shape path.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// <p>If <c>true</c>, the text runs along a path from left to right along the x value of 
    /// the lower boundary of the shape.</p>
    /// </remarks>
    public boolean getXScale() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.GEO_TEXT_DX_MEASURE); }
    public void setXScale(boolean value) { setAttr(ShapeAttr.GEO_TEXT_DX_MEASURE, value); }
    
    private Object fetchAttr(int key)
    {
        return mParent.fetchShapeAttr(key);
    }

    private void setAttr(int key, Object value)
    {
        mParent.setShapeAttr(key, value);
    }

    private /*final*/ IShapeAttrSource mParent;
}

