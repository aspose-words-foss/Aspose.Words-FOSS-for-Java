// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This element specifies the bar function, consisting of a base argument and an overbar or underbar.
/// </summary>
class MathObjectBar extends MathObject
{
    /*override*/ /*MathObjectType*/int getMathObjectType() { return MathObjectType.BAR; }

    /// <summary>
    /// Specifies the position of the bar in the parent object.
    /// Default: <see cref="MathPosition.Default"/>
    /// </summary>
    /*MathPosition*/int getPosition() { return (/*MathPosition*/Integer)fetchAttr(MathAttr.POSITION); }
    void setPosition(/*MathPosition*/int value) { setAttr(MathAttr.POSITION, value, value != MathPosition.DEFAULT); }
}

