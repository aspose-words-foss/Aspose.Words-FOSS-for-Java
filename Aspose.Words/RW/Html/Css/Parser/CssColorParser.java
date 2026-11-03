// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/06/2021 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.DrColor;
import com.aspose.StringUtil;
import com.aspose.DrKnownColors;
import com.aspose.MathUtil;
import com.aspose.DoublePal;
import java.awt.Color;
import com.aspose.ms.System.Drawing.msColor;
import com.aspose.ms.System.Drawing.SystemColors;


/// <summary>
/// An auxiliary class that parses CSS colors.
/// </summary>
class CssColorParser
{
    /// <summary>
    /// Parses a CSS color keyword.
    /// </summary>
    /// <returns>
    /// The parsed color value or <c>null</c> if the string is not a valid color keyword.
    /// </returns>
    static DrColor parseColorName(String name)
    {
        String lowerCaseName = StringUtil.asciiLowerCase(name);
        DrColor color = DrKnownColors.fromName(lowerCaseName);
        if (color.isEmpty())
        {
            color = parseSystemColor(lowerCaseName);
        }

        // If the color name is known, return the corresponding color.
        return (!color.isEmpty())
            ? color
            : null;
    }

    /// <summary>
    /// Parses a hex color value: 3 or 6 case insensitive hex digits without the leading hash character ('#').
    /// </summary>
    /// <remarks>
    /// https://www.w3.org/TR/css-color-3/#rgb-color
    /// </remarks>
    /// <returns>
    /// The parsed color value or <c>null</c> if the string is not a valid hex color value.
    /// </returns>
    static DrColor parseHexColor(String value)
    {
        if ((value.length() != 3) && (value.length() != 6))
        {
            return null;
        }

        // Normally, each hex digit encodes a half-byte of the RGB value. In short hex color values, however, each hex digit
        // encodes one byte. #38B means the same as #3388BB.
        int digitShift = 4;
        int digitScale = 1;
        if (value.length() == 3)
        {
            digitShift *= 2;
            digitScale = 0x11;
        }

        // Decode the value digit by digit.
        int rgbValue = 0;
        for (int i = 0; i < value.length(); i++)
        {
        	char c = value.charAt(i);
            int digit;
            if ((c >= '0') && (c <= '9'))
            {
                digit = c - '0';
            }
            else if ((c >= 'A') && (c <= 'F'))
            {
                digit = c - 'A' + 10;
            }
            else if ((c >= 'a') && (c <= 'f'))
            {
                digit = c - 'a' + 10;
            }
            else
            {
                // Error. Not a hex digit.
                return null;
            }

            rgbValue <<= digitShift;
            rgbValue |= digit * digitScale;
        }

        // The parsed color is fully opaque. Its alpha value is 1.
        return new DrColor((255 << 24) + rgbValue);
    }

    /// <summary>
    /// Parses "rgb()" function colors.
    /// </summary>
    /// <remarks>
    /// See https://www.w3.org/TR/css-color-3/#rgb-color
    /// </remarks>
    /// <returns>
    /// Parsed color value or <c>null</c> in case of an error.
    /// </returns>
    static DrColor parseRgbFunctionColor(CssValueList arguments)
    {
        // Three values delimeted by two commas.
        if (arguments.getCount() != 5)
        {
            return null;
        }
        if ((arguments.get(1).getValueType() != CssValueType.COMMA) || (arguments.get(3).getValueType() != CssValueType.COMMA))
        {
            return null;
        }

        int rgbValue = 0;

        // We remember the first value to make sure all RGB components are of the same type: either numbers or
        // percents.
        /*CssValueType*/int firstValueType = arguments.get(0).getValueType();

        // Parse the argument list.
        for (int i = 0; i < 5; i += 2)
        {
            CssValue argument = arguments.get(i);

            // Each argument can be either a number or a percent but types of all arguments must be the same.
            if (((argument.getValueType() != CssValueType.NUMBER) && (argument.getValueType() != CssValueType.PERCENTAGE)) ||
                (argument.getValueType() != firstValueType))
            {
                // Parsing error. Unexpected value type.
                return null;
            }

            double rawComponentValue = argument.getDoubleValue();
            if (argument.getValueType() == CssValueType.PERCENTAGE)
            {
                // Value of 100% means 255 gradations.
                rawComponentValue *= 2.55;
            }
            // Clips out-of-range values as browsers do.
            int componentValue = MathUtil.doubleToInt(MathUtil.fitToRange(rawComponentValue, 0.0, 255.0));

            // Each component encodes a byte of the RGB value.
            rgbValue <<= 8;
            rgbValue |= componentValue;
        }

        // The parsed color is fully opaque. Its alpha value is 1.
        return new DrColor((255 << 24) + rgbValue);
    }

    /// <summary>
    /// Parses "hsl()" function colors.
    /// </summary>
    /// <remarks>
    /// See https://www.w3.org/TR/css-color-3/#hsl-color
    /// </remarks>
    /// <returns>
    /// Parsed color value or <c>null</c> in case of an error.
    /// </returns>
    static DrColor parseHslFunctionColor(CssValueList arguments)
    {
        // Three values delimeted by two commas.
        // The first parameter (hue) is a unitless number.
        // Other parameters (saturation and luminance) are specified in percents.
        if (arguments.getCount() != 5)
        {
            return null;
        }
        if ((arguments.get(0).getValueType() != CssValueType.NUMBER) ||
            (arguments.get(1).getValueType() != CssValueType.COMMA) ||
            (arguments.get(2).getValueType() != CssValueType.PERCENTAGE) ||
            (arguments.get(3).getValueType() != CssValueType.COMMA) ||
            (arguments.get(4).getValueType() != CssValueType.PERCENTAGE))
        {
            return null;
        }
        return hslToRgb(arguments.get(0).getDoubleValue(), arguments.get(2).getDoubleValue(), arguments.get(4).getDoubleValue());
    }

    /// <summary>
    /// Converts a color from the HSL model to RGB.
    /// </summary>
    private static DrColor hslToRgb(double hue, double saturation, double luminance)
    {
        // The algorithm was taken from the CSS specification:
        // https://www.w3.org/TR/css-color-3/#hsl-color

        // The algorithm normalizes the hue value to the [0; 1) range. However, the calculations get simpler
        // if hue is normalized to the [0; 6) range.
        double h = MathUtil.normalizeAngle(hue) / 60.0;
        double s = MathUtil.fitToRange(saturation, 0.0, 100.0) / 100.0;
        double l = MathUtil.fitToRange(luminance, 0.0, 100.0) / 100.0;

        double m2 = (l <= 0.5)
            ? l * (s + 1.0)
            : l + s - l * s;
        double m1 = l * 2.0 - m2;

        int r = hueToRgbByte(m1, m2, h + 2.0);
        int g = hueToRgbByte(m1, m2, h);
        int b = hueToRgbByte(m1, m2, h - 2.0);

        return new DrColor(r, g, b);
    }

    private static int hueToRgbByte(double m1, double m2, double h)
    {
        // Calculate a HSL component value and then scale it to the [0; 255] range.
        double value = hueToRgb(m1, m2, h) * 255.0;
        value = MathUtil.fitToRange(value, 0.0, 255.0);
        return DoublePal.roundToIntUp(value);
    }

    private static double hueToRgb(double m1, double m2, double h)
    {
        if (h < 0)
            h += 6.0;
        if (h > 6)
            h -= 6.0;

        if (h < 1)
            return m1 + (m2 - m1) * h;
        if (h < 3)
            return m2;
        if (h < 4)
            return m1 + (m2 - m1) * (4.0 - h);
        return m1;
    }

    /// <summary>
    /// Supports CSS2 system colors. http://www.w3.org/TR/css3-color/#css2-system
    /// Returns see <see cref="DrColor.Empty"/> if cannot parse as color.
    /// </summary>
    private static DrColor parseSystemColor(String value)
    {
        Color color = msColor.Empty;
        switch (gStringSwitchMap.of(StringUtil.asciiLowerCase(value)))
        {
            case /*"activeborder"*/0:
                color = SystemColors.getActiveBorder();
                break;
            case /*"activecaption"*/1:
            case /*"captiontext"*/2:
                color = SystemColors.getActiveCaption();
                break;
            case /*"appworkspace"*/3:
                color = SystemColors.getAppWorkspace();
                break;
            case /*"background"*/4:
                color = SystemColors.getControl();
                break;
            case /*"buttonface"*/5:
            case /*"threedface"*/6:
                color = SystemColors.getButtonFace();
                break;
            case /*"buttonhighlight"*/7:
                color = SystemColors.getButtonHighlight();
                break;
            case /*"buttonshadow"*/8:
            case /*"threeddarkshadow"*/9:
            case /*"threedhighlight"*/10:
            case /*"threedlightshadow"*/11:
            case /*"threedshadow"*/12:
                color = SystemColors.getButtonShadow();
                break;
            case /*"buttontext"*/13:
                color = SystemColors.getButtonFace();
                break;
            case /*"graytext"*/14:
                color = SystemColors.getGrayText();
                break;
            case /*"highlight"*/15:
                color = SystemColors.getHighlight();
                break;
            case /*"highlighttext"*/16:
                color = SystemColors.getHighlightText();
                break;
            case /*"inactiveborder"*/17:
                color = SystemColors.getInactiveBorder();
                break;
            case /*"inactivecaption"*/18:
                color = SystemColors.getInactiveCaption();
                break;
            case /*"inactivecaptiontext"*/19:
                color = SystemColors.getInactiveCaptionText();
                break;
            case /*"infobackground"*/20:
                color = SystemColors.getControlLight();
                break;
            case /*"infotext"*/21:
                color = SystemColors.getInfoText();
                break;
            case /*"menu"*/22:
                color = SystemColors.getMenu();
                break;
            case /*"menutext"*/23:
                color = SystemColors.getMenuText();
                break;
            case /*"scrollbar"*/24:
                color = SystemColors.getScrollBar();
                break;
            case /*"window"*/25:
                color = SystemColors.getWindow();
                break;
            case /*"windowframe"*/26:
                color = SystemColors.getWindowFrame();
                break;
            case /*"windowtext"*/27:
                color = SystemColors.getWindowText();
                break;
            default:
                return DrColor.EMPTY;
        }

        return DrColor.fromNativeColor(color);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"activeborder",
		"activecaption",
		"captiontext",
		"appworkspace",
		"background",
		"buttonface",
		"threedface",
		"buttonhighlight",
		"buttonshadow",
		"threeddarkshadow",
		"threedhighlight",
		"threedlightshadow",
		"threedshadow",
		"buttontext",
		"graytext",
		"highlight",
		"highlighttext",
		"inactiveborder",
		"inactivecaption",
		"inactivecaptiontext",
		"infobackground",
		"infotext",
		"menu",
		"menutext",
		"scrollbar",
		"window",
		"windowframe",
		"windowtext"
	);

}

