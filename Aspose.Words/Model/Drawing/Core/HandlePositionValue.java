// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2010 by Dmitry Burov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Describes a positioning of adjust handle along the specific coordinate axis.
/// </summary>
class HandlePositionValue
{
    HandlePositionValue()
    {
    	this(HandlePositionType.UNKNOWN, 0);
	
    }

    HandlePositionValue(/*HandlePositionType*/int type, int value)
    {
        mType = type;
        mValue = value;
    }

    /// <summary>
    /// Expands the structure from a packed binary value.
    /// Please see MS-ODRAW p. 2.2.57 for details (apX and apY props).
    /// </summary>
    HandlePositionValue(int packedValue)
    {
        mValue = 0;
        switch (packedValue)
        {
            case 0x00000000:
            {
                mType = HandlePositionType.LEFT_TOP;
                return;
            }
            case 0x00000001:
            {
                mType = HandlePositionType.RIGHT_BOTTOM;
                return;
            }
            case 0x00000002:
            {
                mType = HandlePositionType.CENTER;
                return;
            }
            default:
            {
                // Formula index
                if (packedValue >= 0x00000003 && packedValue <= 0x00000084)
                {
                    mType = HandlePositionType.FORMULA;
                    mValue = packedValue - 0x00000003;
                    return;
                }

                // Adjust value
                Debug.assertTrue(packedValue >= 0x00000100 && packedValue <= 0x00000107);
                mType = HandlePositionType.ADJUST;
                mValue = packedValue - 0x00000100;
                return;
            }
        }
    }

    /// <summary>
    /// Gets a packed binary representation of the structure according to MS-ODRAW p. 2.2.57.
    /// </summary>
    int getPackedValue()
    {
        switch (mType)
        {
            case HandlePositionType.LEFT_TOP:
                return 0x00000000;
            case HandlePositionType.RIGHT_BOTTOM:
                return 0x00000001;
            case HandlePositionType.CENTER:
                return 0x00000002;
            case HandlePositionType.FORMULA:
                return 0x00000003 + mValue;
            case HandlePositionType.ADJUST:
                return 0x00000100 + mValue;
            default:
                return mValue;
        }
    }

    /// <summary>
    /// Type of positioning.
    /// Please see (MS-ODRAW p 2.2.57 ADJH) for further details.
    /// </summary>
    /*HandlePositionType*/int getType() { return mType; }

    /// <summary>
    /// Type dependent value.
    /// Please see (MS-ODRAW p 2.2.57 ADJH) for further details.
    /// </summary>
    int getValue() { return mValue; }

    private /*final*/ /*HandlePositionType*/int mType;
    private /*final*/ int mValue;
}

