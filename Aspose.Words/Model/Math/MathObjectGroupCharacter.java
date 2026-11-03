// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This element specifies the Group-Character object, consisting of a character drawn above or below text, often
/// with the purpose of visually grouping items.
/// </summary>
class MathObjectGroupCharacter extends MathObject
{
    /*override*/ /*MathObjectType*/int getMathObjectType() { return MathObjectType.GROUP_CHARACTER; }

    /// <summary>
    /// Specifies the character to be attached to this group char object.
    /// Default: U+23DF (BOTTOM CURLY BRACKET).
    /// </summary>
    char getCharacter() { return (/*char*/Character)fetchAttr(MathAttr.GROUP_CHAR); }
    void setCharacter(char value) { setAttr(MathAttr.GROUP_CHAR, value, value != DEFAULT_CHARACTER);}
    
    /// <summary>
    /// Specifies the position of the bar or group character in the parent object.
    /// Default: <see cref="MathPosition.Default"/>
    /// </summary>
    /*MathPosition*/int getPosition() { return (/*MathPosition*/Integer)fetchAttr(MathAttr.GROUP_CHAR_POSITION); }
    void setPosition(/*MathPosition*/int value) { setAttr(MathAttr.GROUP_CHAR_POSITION, value, value != MathPosition.DEFAULT); }

    /// <summary>
    /// Specifies the vertical layout of the groupChr object.
    /// Default: <see cref="MathVerticalJustification.Default"/>
    /// </summary>
    /*MathVerticalJustification*/int getVerticalJustification() { return (/*MathVerticalJustification*/Integer)fetchAttr(MathAttr.VERTICAL_JUSTIFICATION); }
    void setVerticalJustification(/*MathVerticalJustification*/int value) { setAttr(MathAttr.VERTICAL_JUSTIFICATION, value, value != MathVerticalJustification.DEFAULT); }

    static final char DEFAULT_CHARACTER = '\u23DF';
}

