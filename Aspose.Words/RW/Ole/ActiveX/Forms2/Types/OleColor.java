// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;


import com.aspose.ms.System.Drawing.SystemColors;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msString;
import java.awt.Color;
import com.aspose.Debug;


/// <summary>
/// Implements [MS-OFORMS] 2.4.9 OLE_COLOR structure.
/// </summary>
class OleColor
{
    /// <summary>
    /// Ctor.
    /// </summary>
    private OleColor(int red, int green, int blue, /*OleColorType*/int type)
    {
        mRed = red;
        mGreen = green;
        mBlue = blue;

        mOleColorType = type;
    }

    /// <summary>
    /// Determines whether two object instances are equal.
    /// </summary>
    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;

        if (msObject.referenceEquals(this, obj))
            return true;

        if (obj.getClass() != this.getClass())
            return false;

        return equals((OleColor)obj);
    }

    /// <summary>
    /// Gets hash code for this instance.
    /// </summary>
    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            int hashCode = (int)mOleColorType;
            hashCode = (hashCode * 397) ^ (/*byte.hashCode()*/(int) (mRed));
            hashCode = (hashCode * 397) ^ (/*byte.hashCode()*/(int) (mGreen));
            hashCode = (hashCode * 397) ^ (/*byte.hashCode()*/(int) (mBlue));
            return hashCode;
        }
    }

    /// <summary>
    /// Converts the OleColor object to a string.
    /// </summary>
    public /*override*/ String toString()
    {
        return msString.format("Raw:0x{0:X8}, RGB:({1}, R:0x{2:x2}, G:0x{3:x2}, B:0x{4:x2})",
            toRaw(), mOleColorType, (mRed & 0xFF), (mGreen & 0xFF), (mBlue & 0xFF));
    }

    /// <summary>
    /// Creates a OleColor object from a raw value.
    /// </summary>
    static OleColor fromRaw(/*uint*/int raw)
    {
        return new OleColor(
            (int)((raw & 0xFFFFFFFFL) & 0x000000ff),
            (int)(((raw & 0xFFFFFFFFL) & 0x0000ff00) >> 8),
            (int)(((raw & 0xFFFFFFFFL) & 0x00ff0000) >> 16),
            (/*OleColorType*/int)(((raw & 0xFFFFFFFFL) & 0xff000000L) >> 24));
    }

    /// <summary>
    /// Creates a OleColor object from a Color object.
    /// </summary>
    static OleColor fromColor(Color color)
    {
        return new OleColor(color.getRed(), color.getGreen(), color.getBlue(), OleColorType.DEFAULT);
    }

    /// <summary>
    /// Gets raw value from this OleColor instance.
    /// </summary>
    /*uint*/int toRaw()
    {
        /*uint*/int raw = (/*uint*/int)((mRed & 0xFF) | (((/*uint*/int)((mGreen & 0xFF) << 8)) & 0xFFFFFFFFL) | (((/*uint*/int)((mBlue & 0xFF) << 16)) & 0xFFFFFFFFL) | (((/*uint*/int)((((byte)mOleColorType) & 0xFF) << 24)) & 0xFFFFFFFFL));
        return raw;
    }

    /// <summary>
    /// Determines whether two OleColor objects are equal.
    /// </summary>
    private boolean equals(OleColor other)
    {
        Debug.assertTrue(other != null);

        return (mOleColorType == other.mOleColorType) &&
               (mRed == other.mRed) &&
               (mGreen == other.mGreen) &&
               (mBlue == other.mBlue);
    }


    /// <summary>
    /// Gets Color from this OleColor instance.
    /// </summary>
    Color getColor()
    {
        if (mOleColorType == OleColorType.SYSTEM_PALETTE)
            return getSystemPaletteColor((mRed & 0xFF) | ((mGreen & 0xFF) << 8));
        return new Color((mRed), (mGreen), (mBlue));
    }

    private static Color getSystemPaletteColor(int paletteIndex)
    {
        switch (paletteIndex)
                {
                    case 0:
                        return SystemColors.getScrollBar();
                    case 1:
                        return SystemColors.getDesktop();
                    case 2: // Active title bar
                        return SystemColors.getActiveCaption();
                    case 3: // Inactive title bar
                        return SystemColors.getInactiveCaption();
                    case 4:
                        return SystemColors.getMenuBar();
                    case 5:
                        return SystemColors.getWindow();
                    case 6:
                        return SystemColors.getWindowFrame();
                    case 7:
                        return SystemColors.getMenuText();
                    case 8:
                        return SystemColors.getWindowText();
                    case 9: // Active title bar text
                        return SystemColors.getActiveCaptionText();
                    case 10:
                        return SystemColors.getActiveBorder();
                    case 11:
                        return SystemColors.getInactiveBorder();
                    case 12:
                        return SystemColors.getAppWorkspace();
                    case 13:
                        return SystemColors.getHighlight();
                    case 14:
                        return SystemColors.getHighlightText();
                    case 15:
                        return SystemColors.getButtonFace();
                    case 16:
                        return SystemColors.getButtonShadow();
                    case 17: // Disabled text
                        return SystemColors.getGrayText();
                    case 18: // Button text
                        return SystemColors.getControlText();
                    case 19: // Inactive title bar text
                        return SystemColors.getInactiveCaptionText();
                    case 20:
                        return SystemColors.getButtonHighlight();
                    case 21: // Button dark shadow
                        return SystemColors.getControlDark();
                    case 22: // Button light shadow
                        return SystemColors.getControlLight();
                    case 23:
                        return SystemColors.getInfoText();
                    case 24:
                        return SystemColors.getInfo();
                    default:
                        return SystemColors.getDesktop();
                }
    }

    private /*final*/ int mRed;
    private /*final*/ int mGreen;
    private /*final*/ int mBlue;

    private /*final*/ /*OleColorType*/int mOleColorType;
}

