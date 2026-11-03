// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a constant with no value.
/// </summary>
class NullConstant extends Constant
{
    private NullConstant()
    {
        // Hide from using outside.
    }

    /*override*/ /*ConstantType*/int getConstantType() { return ConstantType.NULL; }

    /// <summary>
    /// A singleton instance of the class.
    /// </summary>
    static /*final*/ NullConstant INSTANCE = new NullConstant();
}

