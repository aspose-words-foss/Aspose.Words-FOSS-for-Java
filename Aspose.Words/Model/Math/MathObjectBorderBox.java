// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This element specifies the Border Box object, consisting of a border drawn around an instance of mathematical 
/// text (such as a formula or equation).
/// </summary>
/// <remarks>
/// If borderBoxPr is omitted then the default behavior of borderBox is a rectangular border.</remarks>
class MathObjectBorderBox extends MathObject
{
    /*override*/ /*MathObjectType*/int getMathObjectType() { return MathObjectType.BORDER_BOX; }

     /// <summary>
    /// Specifies the hidden or shown state of the bottom edge of this borderBox.
    /// </summary>
    boolean getHideBottomEdge() { return (/*boolean*/Boolean)fetchAttr(MathAttr.HIDE_BOTTOM); }
    void setHideBottomEdge(boolean value) { setAttr(MathAttr.HIDE_BOTTOM, value, value); }
    
    /// <summary>
    /// Specifies the hidden or shown state of the top edge of this borderBox.
    /// Default: false
    /// </summary>
    boolean getHideTopEdge() { return (/*boolean*/Boolean)fetchAttr(MathAttr.HIDE_TOP); }
    void setHideTopEdge(boolean value) { setAttr(MathAttr.HIDE_TOP, value, value); }
    
    /// <summary>
    /// Specifies the hidden or shown state of the left edge of this borderBox.
    /// Default: false
    /// </summary>
    boolean getHideLeftEdge() { return (/*boolean*/Boolean)fetchAttr(MathAttr.HIDE_LEFT); }
    void setHideLeftEdge(boolean value) { setAttr(MathAttr.HIDE_LEFT, value, value); }
    
    /// <summary>
    /// Specifies the hidden or shown state of the left edge of this borderBox.
    /// Default: false
    /// </summary>
    boolean getHideRightEdge() { return (/*boolean*/Boolean)fetchAttr(MathAttr.HIDE_RIGHT); }
    void setHideRightEdge(boolean value) { setAttr(MathAttr.HIDE_RIGHT, value, value); }
    
    /// <summary>
    /// Specifies the hidden or shown state of a strikethrough diagonal line from the bottom-left corner to
    /// the top-right corner of this borderBox.
    /// When set to false, the strikethrough is not drawn.
    /// Default: false
    /// </summary>
    boolean getStrikeBLTR() { return (/*boolean*/Boolean)fetchAttr(MathAttr.STRIKE_BLTR); }
    void setStrikeBLTR(boolean value) { setAttr(MathAttr.STRIKE_BLTR, value, value); }
    
    /// <summary>
    /// Specifies the hidden or shown state of a strikethrough horizontal line in this borderBox.
    /// When set to false, the strikethrough is not drawn.
    /// Default: false
    /// </summary>
    boolean getStrikeH() { return (/*boolean*/Boolean)fetchAttr(MathAttr.STRIKE_H); }
    void setStrikeH(boolean value) { setAttr(MathAttr.STRIKE_H, value, value); }
    
    /// <summary>
    /// Specifies the hidden or shown state of a strikethrough vertical line in this borderBox.
    /// When set to false, the strikethrough is not drawn.
    /// Default: false
    /// </summary>
    boolean getStrikeV() { return (/*boolean*/Boolean)fetchAttr(MathAttr.STRIKE_V); }
    void setStrikeV(boolean value) { setAttr(MathAttr.STRIKE_V, value, value); }

    /// <summary>
    /// Specifies the hidden or shown state of a strikethrough diagonal line from the top-left corner to 
    /// the bottom-right corner of this borderBox.
    /// When set to false, the strikethrough is not drawn.
    /// Default: false
    /// </summary>
    boolean getStrikeTLBR() { return (/*boolean*/Boolean)fetchAttr(MathAttr.STRIKE_TLBR); }
    void setStrikeTLBR(boolean value) { setAttr(MathAttr.STRIKE_TLBR, value, value); }
}

