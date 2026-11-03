// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a constant whose value is aggregate (a list of other constants).
/// </summary>
class AggregateConstant extends Constant
{
    AggregateConstant()
    {
        mValues = new ConstantCollection();
    }

    /// <summary>
    /// Gets a list of contained values.
    /// </summary>
    ConstantCollection getValues() { return mValues; };

    private ConstantCollection mValues;

    /*override*/ /*ConstantType*/int getConstantType() { return ConstantType.AGGREGATE; }
   }

