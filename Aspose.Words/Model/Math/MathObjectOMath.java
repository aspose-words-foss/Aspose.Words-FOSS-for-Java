// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This element specifies an instance of mathematical text.
/// </summary>
class MathObjectOMath extends MathObject
{
    /*override*/ /*MathObjectType*/int getMathObjectType() { return MathObjectType.O_MATH; }

    /*override*/ boolean canBeArgument()
    {
        // MS-OI29500, 22.1.2.77.(b) Word fails to open a file with an oMath element inside 
        // a math object argument or inside another oMath element. 
        return false;
    }

    /*override*/ boolean getNeedsMathObjectArgumentWrapper() { return false; }

}

