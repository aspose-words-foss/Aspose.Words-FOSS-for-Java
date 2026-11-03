// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This element specifies the phantom object. This object has two primary uses: adding the spacing of the phantom
/// base element e without displaying that base; and suppressing part of the glyph for spacing considerations.
/// </summary>
class MathObjectPhantom extends MathObject
{
    /*override*/ /*MathObjectType*/int getMathObjectType() { return MathObjectType.PHANTOM; }

    /// <summary>
    /// Specifies the show property of the phantom phant. 
    /// When false, the phantom base "e" <see cref="MathObjectArgumentBase"/> is hidden. 
    /// Default: true.
    /// </summary>
    boolean isShown() { return (/*boolean*/Boolean)fetchAttr(MathAttr.IS_SHOWN); }
    void isShown(boolean value) { setAttr(MathAttr.IS_SHOWN, value, !value); }
    
    /// <summary>
    /// Specifies that the phantom is transparent for spacing. 
    /// Default: false.
    /// </summary>
    /// <remarks>
    /// This means that if the contents of the
    /// phantom are belonging to a special spacing class (such as binary operators, relational operators, differentials,
    /// etc.), the contents of that phantom are taken into consideration when laying out text. 
    /// If transparency is turned off, then the contents of the phantom are ignored during layout. 
    /// </remarks>
    boolean isTransparent() { return (/*boolean*/Boolean)fetchAttr(MathAttr.IS_TRANSPARENT); }
    void isTransparent(boolean value) { setAttr(MathAttr.IS_TRANSPARENT, value, value); }
    
    /// <summary>
    /// Specifies that the phantom has zero ascent.
    /// If true then ascent of the contents of the phantom is not taken into account during layout.
    /// Default: false.
    /// </summary>
    boolean isZeroAscent() { return (/*boolean*/Boolean)fetchAttr(MathAttr.IS_ZERO_ASCENT); }
    void isZeroAscent(boolean value) { setAttr(MathAttr.IS_ZERO_ASCENT, value, value); }
    
    /// <summary>
    /// Specifies that the phantom has zero descent. 
    /// If true, then descent of the contents of the phantom is not taken into account during layout.
    /// Default: false.
    /// </summary>
    boolean isZeroDescent() { return (/*boolean*/Boolean)fetchAttr(MathAttr.IS_ZERO_DESCENT); }
    void isZeroDescent(boolean value) { setAttr(MathAttr.IS_ZERO_DESCENT, value, value); }
    
    /// <summary>
    /// Specifies that the phantom has zero width. 
    /// IF true, then width of the contents of the phantom is not taken into account during layout.
    /// Default: false.
    /// </summary>
    boolean isZeroWidth() { return (/*boolean*/Boolean)fetchAttr(MathAttr.IS_ZERO_WIDTH); }
    void isZeroWidth(boolean value) { setAttr(MathAttr.IS_ZERO_WIDTH, value, value); }
}

