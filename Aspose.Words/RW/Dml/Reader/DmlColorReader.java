// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Represents a class building DmlColor objects from xml.
/// </summary>
class DmlColorReader extends DmlReaderBase
{
    private DmlColorReader(NrxXmlReader reader, OoxmlComplianceInfo complianceInfo)
    {
        mReader = reader;
        mComplianceInfo = complianceInfo;
    }

    /// <summary>
    /// Reads CT_Color complex type value of the DrawingML element from the XML reader.
    /// Returns the color value of the latest child element in the current element.
    /// </summary>
    public static DmlColor readColor(NrxXmlReader reader, OoxmlComplianceInfo complianceInfo)
    {
        String tagName = reader.getLocalName();
        DmlColor result = null;
        while (reader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"hslClr"*/0:
                case /*"prstClr"*/1:
                case /*"schemeClr"*/2:
                case /*"scrgbClr"*/3:
                case /*"srgbClr"*/4:
                case /*"sysClr"*/5:
                    // If the property is already initialized
                    // then overwrite it. It gives a possibility to
                    // use color defined by the latest color tag.
                    DmlColor tmp = read(reader, complianceInfo);
                    if (tmp != null)
                        result = tmp;
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(reader);
                    break;
            }
        }
        return result;
    }

    /// <summary>
    /// Builds a color by specified XML reader.
    /// </summary>
    public static DmlColor read(NrxXmlReader reader, OoxmlComplianceInfo complianceInfo)
    {
        DmlColorReader colorReader = new DmlColorReader(reader, complianceInfo);
        return colorReader.readCore();
    }

    private DmlColor readCore()
    {
        switch (gStringSwitchMap.of(mReader.getLocalName()))
        {
            case /*"hslClr"*/0:
                return readHslColor();
            case /*"prstClr"*/1:
                return readPresetColor();
            case /*"schemeClr"*/2:
                return readSchemeColor();
            case /*"scrgbClr"*/3:
                return readPercentageRgbColor();
            case /*"srgbClr"*/4:
                return readHexRgbColor();
            case /*"sysClr"*/5:
                return readSystemColor();
            default:
                warnUnexpectedAndIgnoreElement(mReader);
                return null;
        }
    }

    private DmlColor readSystemColor()
    {
        DmlSystemColor color = new DmlSystemColor();
        color.setValue(mReader.readAttribute(""));
        color.setLastColor(mReader.readAttribute("lastClr", ""));
        color.setColorModifiers(readModifiers());
        return color;
    }

    /// <summary>
    /// Reads color modifiers.
    /// </summary>
    static ArrayList<IDmlColorModifier> readModifiers(NrxXmlReader reader, OoxmlComplianceInfo complianceInfo)
    {
        DmlColorReader colorReader = new DmlColorReader(reader, complianceInfo);
        return colorReader.readModifiers();
    }

    private ArrayList<IDmlColorModifier> readModifiers()
    {
        ArrayList<IDmlColorModifier> modifiers = new ArrayList<IDmlColorModifier>();
        String tagName = mReader.getLocalName();
        while (mReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(mReader.getLocalName()))
            {
                case /*"alpha"*/6:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlAlpha()));
                    break;
                case /*"alphaMod"*/7:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlAlphaModulation()));
                    break;
                case /*"alphaOff"*/8:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlAlphaOffset()));
                    break;
                case /*"blue"*/9:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlBlue()));
                    break;
                case /*"blueMod"*/10:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlBlueModulation()));
                    break;
                case /*"blueOff"*/11:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlBlueOffset()));
                    break;
                case /*"comp"*/12:
                    msArrayList.add(modifiers, new DmlComplement());
                    break;
                case /*"gamma"*/13:
                    msArrayList.add(modifiers, new DmlGamma());
                    break;
                case /*"gray"*/14:
                    msArrayList.add(modifiers, new DmlGray());
                    break;
                case /*"green"*/15:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlGreen()));
                    break;
                case /*"greenMod"*/16:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlGreenModulation()));
                    break;
                case /*"greenOff"*/17:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlGreenOffset()));
                    break;
                case /*"hue"*/18:
                    readHue(modifiers);
                    break;
                case /*"hueMod"*/19:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlHueModulation()));
                    break;
                case /*"hueOff"*/20:
                    readHueOffset(modifiers);
                    break;
                case /*"inv"*/21:
                    msArrayList.add(modifiers, new DmlInverse());
                    break;
                case /*"invGamma"*/22:
                    msArrayList.add(modifiers, new DmlInverseGamma());
                    break;
                case /*"lum"*/23:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlLuminance()));
                    break;
                case /*"lumMod"*/24:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlLuminanceModulation()));
                    break;
                case /*"lumOff"*/25:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlLuminanceOffset()));
                    break;
                case /*"red"*/26:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlRed()));
                    break;
                case /*"redMod"*/27:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlRedModulation()));
                    break;
                case /*"redOff"*/28:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlRedOffset()));
                    break;
                case /*"sat"*/29:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlSaturation()));
                    break;
                case /*"satMod"*/30:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlSaturationModulation()));
                    break;
                case /*"satOff"*/31:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlSaturationOffset()));
                    break;
                case /*"shade"*/32:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlShade()));
                    break;
                case /*"tint"*/33:
                    msArrayList.add(modifiers, readPercentageBasedModifier(new DmlTint()));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mReader);
                    break;
            }
        }
        return modifiers;
    }

    private DmlPercentageBasedColorModifier readPercentageBasedModifier(DmlPercentageBasedColorModifier modifier)
    {
        modifier.setValue(DmlPercentageUtil.fromPercentOrDmlPercent(mReader.readAttribute(""), mComplianceInfo));
        return modifier;
    }

    private void readHueOffset(ArrayList<IDmlColorModifier> colorModifiers)
    {
        DmlHueOffset modificator = new DmlHueOffset();
        modificator.setValue(new DmlAngle(mReader.readDoubleAttribute(0.0)));
        colorModifiers.add(modificator);
    }

    private void readHue(ArrayList<IDmlColorModifier> colorModifiers)
    {
        DmlHue modificator = new DmlHue();
        modificator.setValue(new DmlAngle(mReader.readDoubleAttribute(0.0)));
        colorModifiers.add(modificator);
    }

    private DmlColor readHexRgbColor()
    {
        DmlHexRgbColor color = new DmlHexRgbColor();
        color.setValue(mReader.readAttribute(""));
        color.setColorIndex(mReader.readAttribute("legacySpreadsheetColorIndex", null));
        color.setColorModifiers(readModifiers());
        return color;
    }

    private DmlColor readPercentageRgbColor()
    {
        DmlPercentageRgbColor color = new DmlPercentageRgbColor();
        color.setR(DmlPercentageUtil.fromPercentOrDmlPercent(mReader.readAttribute("r", ""), mComplianceInfo));
        color.setG(DmlPercentageUtil.fromPercentOrDmlPercent(mReader.readAttribute("g", ""), mComplianceInfo));
        color.setB(DmlPercentageUtil.fromPercentOrDmlPercent(mReader.readAttribute("b", ""), mComplianceInfo));
        color.setColorModifiers(readModifiers());
        return color;
    }

    private DmlColor readSchemeColor()
    {
        String value = mReader.readAttribute("");
        if ("phClr".equals(value))
        {
            // This color can appear in theme.
            DmlPlaceholderColor placeholderColor = new DmlPlaceholderColor();
            placeholderColor.setColorModifiers(readModifiers());
            return placeholderColor;
        }

        DmlSchemeColor color = new DmlSchemeColor();
        color.setValue(ThemeColorConverter.fromString(value));
        color.setColorModifiers(readModifiers());
        return color;
    }

    private DmlColor readPresetColor()
    {
        DmlPresetColor color = new DmlPresetColor();
        color.setValue(mReader.readAttribute(""));
        color.setColorModifiers(readModifiers());
        return color;
    }

    private DmlColor readHslColor()
    {
        DmlHslColor color = new DmlHslColor();
        color.setHue(new DmlAngle(mReader.readIntAttribute("hue", 0)));
        color.setLuminance(DmlPercentageUtil.fromPercentOrDmlPercent(mReader.readAttribute("lum", ""), mComplianceInfo));
        color.setSaturation(DmlPercentageUtil.fromPercentOrDmlPercent(mReader.readAttribute("sat", ""), mComplianceInfo));
        color.setColorModifiers(readModifiers());
        return color;
    }

    private /*final*/ NrxXmlReader mReader;
    private /*final*/ OoxmlComplianceInfo mComplianceInfo;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"hslClr",
		"prstClr",
		"schemeClr",
		"scrgbClr",
		"srgbClr",
		"sysClr",
		"alpha",
		"alphaMod",
		"alphaOff",
		"blue",
		"blueMod",
		"blueOff",
		"comp",
		"gamma",
		"gray",
		"green",
		"greenMod",
		"greenOff",
		"hue",
		"hueMod",
		"hueOff",
		"inv",
		"invGamma",
		"lum",
		"lumMod",
		"lumOff",
		"red",
		"redMod",
		"redOff",
		"sat",
		"satMod",
		"satOff",
		"shade",
		"tint"
	);

}

