// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2010 by Denis Darkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// This element specifies an n-ary object, consisting of an n-ary object, a base (or operand), 
/// and optional upper and lower limits. 
/// Examples of n-ary objects are integrals, sum signs etc.
/// </summary>
class MathObjectNAry extends MathObject
{
    /*override*/ boolean canInsert(Node node)
    {
        if (super.canInsert(node))
            return true;
            
        if ((node.getNodeType() == NodeType.OFFICE_MATH))
        {
            // This object can host arguments and also special objects subscript part and
            // superscript part.
            /*MathObjectType*/int type = ((OfficeMath)node).getMathObject().getMathObjectType();
            return (type == MathObjectType.SUBSCRIPT_PART) || (type == MathObjectType.SUPERSCRIPT_PART);
        }

        return false;
    }

    /*override*/ /*MathObjectType*/int getMathObjectType() { return MathObjectType.N_ARY; }

    /// <summary>
    /// Specifies the character to be attached to this N-Ary object.
    /// Default is Unicode character U+222B (INTEGRAL)
    /// </summary>
    char getCharacter() { return (/*char*/Character)fetchAttr(MathAttr.NARY_CHAR); }
    void setCharacter(char value) { setAttr(MathAttr.NARY_CHAR, value, value != DEFAULT_CHARACTER); }

    /// <summary>
    /// Specifies the growth property of this n-ary operator. 
    /// When false, n-ary operators such as integrals and summations do not grow to match the size of their operand height. 
    /// When true, the operator grows vertically to match its operand height. 
    /// Default: false.
    /// </summary>
    boolean getGrowToMatchOperand() { return (/*boolean*/Boolean)fetchAttr(MathAttr.GROW_OPERAND); }
    void setGrowToMatchOperand(boolean value) { setAttr(MathAttr.GROW_OPERAND, value, value); }
    
    /// <summary>
    /// Specifies the location of limits in n-ary operators.
    /// There is no default value, so if this value is not read from OOXML, then it's "default" is
    /// <see cref="MathLimitLocation.Undefined"/>, meaning that its value must be obtained from Office Math document-wide options
    /// <see cref="MathProperties.NaryLimitLocation"/> and <see cref="MathProperties.IntegralLimitLocation"/>
    /// </summary>
    /*MathLimitLocation*/int getLimitLocation() { return (/*MathLimitLocation*/Integer)fetchAttr(MathAttr.LIMIT_LOCATION); }
    void setLimitLocation(/*MathLimitLocation*/int value) { setAttr(MathAttr.LIMIT_LOCATION, value, value != MathLimitLocation.UNDEFINED); }
    
    /// <summary>
    /// Specifies the n-ary Hide Superscript property
    /// Default: false.
    /// </summary>
    boolean isHideSubscript() { return (/*boolean*/Boolean)fetchAttr(MathAttr.IS_HIDE_SUBSCRIPT); }
    void isHideSubscript(boolean value) { setAttr(MathAttr.IS_HIDE_SUBSCRIPT, value, value); }
    
    /// <summary>
    /// Specifies the n-ary Hide Subscript property.
    /// Default: false.
    /// </summary>
    boolean isHideSuperscript() { return (/*boolean*/Boolean)fetchAttr(MathAttr.IS_HIDE_SUPERSCRIPT); }
    void isHideSuperscript(boolean value) { setAttr(MathAttr.IS_HIDE_SUPERSCRIPT, value, value); }

    static final char DEFAULT_CHARACTER = '\u222B';
}

