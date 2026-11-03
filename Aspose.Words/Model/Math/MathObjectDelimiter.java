// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This element specifies the delimiter object, consisting of opening and closing delimiters (such as parentheses,
/// braces, brackets, and vertical bars), and an element contained inside. The delimiter may have more than one
/// element, with a designated separator character between each element.
/// </summary>
class MathObjectDelimiter extends MathObject
{
    /*override*/ /*MathObjectType*/int getMathObjectType() { return MathObjectType.DELIMITER; }

    /// <summary>
    /// This element specifies the beginning, or opening, delimiter character. Mathematical delimiters are enclosing
    /// characters such as parentheses, brackets, and braces.
    /// Default: Unicode character U+0028 (LEFT PARENTHESIS).
    /// </summary>
    char getBeginningCharacter() { return (/*char*/Character)fetchAttr(MathAttr.BEGIN_CHAR); }
    void setBeginningCharacter(char value) { setAttr(MathAttr.BEGIN_CHAR, value, value != DEFAULT_BEGINNING_CHARACTER); }
    
    /// <summary>
    /// specifies the ending, or closing, delimiter character. Mathematical delimiters are enclosing
    /// characters such as parentheses, brackets, and braces. 
    /// Default: Unicode character U+0029 (RIGHT PARENTHESIS).
    /// </summary>
    char getEndingCharacter() { return (/*char*/Character)fetchAttr(MathAttr.END_CHAR); }
    void setEndingCharacter(char value) { setAttr(MathAttr.END_CHAR, value, value != DEFAULT_ENDING_CHARACTER); }
    
    /// <summary>
    /// Specifies the character that separates base arguments e in the delimiter object
    /// Default: Unicode character U+2502 (BOX DRAWINGS LIGHT VERTICAL).
    /// </summary>
    char getSeparatorCharacter() { return (/*char*/Character)fetchAttr(MathAttr.SEPARATOR_CHAR); }
    void setSeparatorCharacter(char value) { setAttr(MathAttr.SEPARATOR_CHAR, value, value != DEFAULT_SEPARATOR_CHARACTER); }

    /// <summary>
    /// Specifies the growth property of delimiter. 
    /// When false, delimiters do not grow to match the size of their operand height. 
    /// When true, the delimiter grows vertically to match its operand height. 
    /// Default: true.
    /// </summary>
    /// <remarks>
    /// 7.1.2.31 dPr (Delimiter Properties) describes that "grow (n-ary Grow)" (§7.1.2.43) is used. But default value of
    /// "grow (n-ary Grow)" is "false". MS Word violates specification requirements and uses "true" as default value.
    /// Repeat this oddity and use "true" if the direct attribute is not set.
    /// </remarks>
    boolean getGrowToMatchOperand() { return (getDirectAttr(MathAttr.GROW_OPERAND) == null) || (/*boolean*/Boolean)fetchAttr(MathAttr.GROW_OPERAND); }
    void setGrowToMatchOperand(boolean value) { setAttr(MathAttr.GROW_OPERAND, value, !value); }
    
    /// <summary>
    /// Specifies the shape of delimiters in this delimiter object.
    /// Default: <see cref="MathDelimiterShape.Default"/>
    /// </summary>
    /// <remarks>
    /// MS Office will ignore this property is <see cref="GrowToMatchOperand"/> is false.
    /// </remarks>
    /*MathDelimiterShape*/int getDelimiterShape() { return (/*MathDelimiterShape*/Integer)fetchAttr(MathAttr.DELIMITER_SHAPE); }
    void setDelimiterShape(/*MathDelimiterShape*/int value) { setAttr(MathAttr.DELIMITER_SHAPE, value, value != MathDelimiterShape.DEFAULT); }

    /// <summary>
    /// default = '('
    /// </summary>
    static final char DEFAULT_BEGINNING_CHARACTER = '\u0028';

    /// <summary>
    /// default = ')'
    /// </summary>
    static final char DEFAULT_ENDING_CHARACTER = '\u0029';
    
    /// <summary>
    /// default = '|'
    /// </summary>
    static final char DEFAULT_SEPARATOR_CHARACTER = '\u2502';
}

