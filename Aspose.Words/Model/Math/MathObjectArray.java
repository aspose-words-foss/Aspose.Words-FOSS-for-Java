// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This element specifies the Array object: an object consisting of one or more equations, expressions, or other
/// mathematical text runs that can be vertically justified as a unit with respect to surrounding text on the line.
/// </summary>
/// <remarks>This element is sometimes referred to as "Equation Array", despite its ability to hold
/// mathematical text other than equations.</remarks>
class MathObjectArray extends MathObject
{
    /*override*/ /*MathObjectType*/int getMathObjectType() { return MathObjectType.ARRAY; }

    /// <summary>
    /// Specifies the vertical justification of the array relative to the text outside of the array.
    /// Default: <see cref="MathBaseJustification.Default"/>
    /// </summary>
    /*MathBaseJustification*/int getBaseJustification() { return (/*MathBaseJustification*/Integer)fetchAttr(MathAttr.BASE_JUSTIFICATION); }
    void setBaseJustification(/*MathBaseJustification*/int value) { setAttr(MathAttr.BASE_JUSTIFICATION, value, value != MathBaseJustification.DEFAULT); }
    
    /// <summary>
    /// specifies Array Maximum Distribution. 
    /// When true, the array is spaced to the maximum width of the containing element (page, column, cell, etc.). 
    /// Default: false
    /// </summary>
    /// <remarks>
    /// This property is commonly used with the <see cref="IsObjectDistribution"/> property.
    /// </remarks>
    boolean isMaximumDistribution() { return (/*boolean*/Boolean)fetchAttr(MathAttr.MAX_DIST); }
    void isMaximumDistribution(boolean value) { setAttr(MathAttr.MAX_DIST, value, value); }
    
    /// <summary>
    /// Specifies Array Object Distribution. When true, the contents of the array are spaced to the
    /// maximum width of the array object.
    /// Default: false
    /// </summary>
    /// <remarks>This property is ignored by MS Word if <see cref="IsMaximumDistribution"/> is false.
    /// </remarks>
    boolean isObjectDistribution() { return (/*boolean*/Boolean)fetchAttr(MathAttr.OBJ_DIST); }
    void isObjectDistribution(boolean value) { setAttr(MathAttr.OBJ_DIST, value, value); }
    
    /// <summary>
    /// Specifies the type of vertical spacing between columns in a array. 
    /// Default is <see cref="MathSpacingRule.Default"/>
    /// </summary>
    /*MathSpacingRule*/int getRowSpacingRule() { return (/*MathSpacingRule*/Integer)fetchAttr(MathAttr.ROW_SPACING_RULE); }
    void setRowSpacingRule(/*MathSpacingRule*/int value) { setAttr(MathAttr.ROW_SPACING_RULE, value, value != MathSpacingRule.DEFAULT); }
    
    /// <summary>
    /// This element specifies spacing between rows of an array eqArr; it is used only when 
    /// <see cref="RowSpacingRule"/> is set to <see cref="MathSpacingRule.Exactly"/> or
    /// <see cref="MathSpacingRule.Multiple"/>.
    /// Default value is 0.
    /// </summary>
    int getRowSpacing() { return (/*int*/Integer)fetchAttr(MathAttr.ROW_SPACING); }
    void setRowSpacing(int value) { setAttr(MathAttr.ROW_SPACING, value, value != 0); }
}

