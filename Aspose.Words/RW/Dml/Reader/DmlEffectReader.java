// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/11/2011 by Alexey Kachalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/// <summary>
/// Represents a class that builds effects objects from XML.
/// </summary>
class DmlEffectReader extends DmlReaderBase
{
    private DmlEffectReader()
    {
    }

    /// <summary>
    /// Builds an effect object by specified XML reader.
    /// </summary>
    static DmlEffect read(DocxDocumentReaderBase reader) throws Exception
    {
        switch (gStringSwitchMap.of(reader.getXmlReader().getLocalName()))
        {
            case /*"biLevel"*/0:
                return readBiLevelEffect(reader);
            case /*"clrChange"*/1:
                return readColorChangeEffect(reader);
            case /*"grayscl"*/2:
                return readGrayScaleEffect();
            case /*"lum"*/3:
                return readLuminanceEffect(reader);
            case /*"duotone"*/4:
                return readDuotoneEffect(reader);
            case /*"blur"*/5:
                return readBlurEffect(reader);
            case /*"alphaBiLevel"*/6:
                return readAlphaBiLevelEffect(reader);
            case /*"alphaCeiling"*/7:
                return new DmlAlphaCeilingEffect();
            case /*"alphaFloor"*/8:
                return new DmlAlphaFloorEffect();
            case /*"alphaInv"*/9:
                return readAlphaInveseEffect(reader);
            case /*"alphaMod"*/10:
                return readAlphaModulateEffect(reader);
            case /*"alphaModFix"*/11:
                return readAlphaModulateFixedEffect(reader);
            case /*"alphaRepl"*/12:
                return readAlphaReplaceEffect(reader);
            case /*"clrRepl"*/13:
                return readColorReplaceEffect(reader);
            case /*"fillOverlay"*/14:
                return readFillOverlayEffect(reader);
            case /*"hsl"*/15:
                return readHueSaturationLuminanceEffect(reader);
            case /*"tint"*/16:
                return readTintEffect(reader);
            default:
                warnUnexpectedAndIgnoreElement(reader.getXmlReader());
                return null;
        }
    }

    static DmlTintEffect readTintEffect(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        DmlTintEffect tint = new DmlTintEffect();
        tint.setAmount(DmlPercentageUtil.fromPercentOrDmlPercent(xmlReader.readAttribute("amt", ""), complianceInfo));
        tint.setHue(DmlPercentageUtil.fromPercentOrDmlPercent(xmlReader.readAttribute("hue", ""), complianceInfo));
        return tint;
    }

    static DmlHueSaturationLuminanceEffect readHueSaturationLuminanceEffect(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        DmlHueSaturationLuminanceEffect hsl = new DmlHueSaturationLuminanceEffect();
        hsl.setHue(new DmlAngle(xmlReader.readDoubleAttribute("hue", 0.0)));
        hsl.setLuminance(DmlPercentageUtil.fromPercentOrDmlPercent(xmlReader.readAttribute("lum", ""), complianceInfo));
        hsl.setSaturation(DmlPercentageUtil.fromPercentOrDmlPercent(xmlReader.readAttribute("sat", ""), complianceInfo));
        return hsl;
    }

    static DmlFillOverlayEffect readFillOverlayEffect(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        DmlFillOverlayEffect fillOverlay = new DmlFillOverlayEffect();
        fillOverlay.setBlend(DmlEnum.dmlToEffectBlendMode(xmlReader.readAttribute("blend", "")));

        while (xmlReader.readChild("fillOverlay"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"blipFill"*/17:
                case /*"gradFill"*/18:
                case /*"grpFill"*/19:
                case /*"pattFill"*/20:
                case /*"noFill"*/21:
                case /*"solidFill"*/22:
                    fillOverlay.setFill(DmlFillReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return fillOverlay;
    }

    static DmlColorReplaceEffect readColorReplaceEffect(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        DmlColorReplaceEffect colorReplace = new DmlColorReplaceEffect();
        colorReplace.setColor(DmlColorReader.read(xmlReader, complianceInfo));
        return colorReplace;
    }

    static DmlAlphaReplaceEffect readAlphaReplaceEffect(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        DmlAlphaReplaceEffect alphaReplace = new DmlAlphaReplaceEffect();
        alphaReplace.setAlpha(DmlPercentageUtil.fromPercentOrDmlPercent(xmlReader.readAttribute("a", ""), complianceInfo));
        return alphaReplace;
    }

    static DmlAlphaModulateFixedEffect readAlphaModulateFixedEffect(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        DmlAlphaModulateFixedEffect alphaModulateFixed = new DmlAlphaModulateFixedEffect();

        // andrnosk: WORDSNET-11656 Default value for "amt" equals 100000, please see spec ECMA 376 Part4 p.5.1.10.6
        alphaModulateFixed.setAmount(DmlPercentageUtil.fromPercentOrDmlPercent(xmlReader.readAttribute("amt", ""), 1.0, complianceInfo));
        return alphaModulateFixed;
    }

    static DmlAlphaModulateEffect readAlphaModulateEffect(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        DmlAlphaModulateEffect alphaModulate = new DmlAlphaModulateEffect();

        while (xmlReader.readChild("alphaMod"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"cont"*/23:
                {
                    while (xmlReader.readChild("cont"))
                    {
                        // Children tag are effects tag.
                        // According to the specification in the container might be picture effects as well as shape effects,
                        // lets read only picture effects for now.
                        DmlEffect dmlEffect = read(reader);
                        if (dmlEffect != null)
                            alphaModulate.getEffectsContainer().add(dmlEffect);
                    }
                    break;
                }
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return alphaModulate;
    }

    static DmlAlphaInverseEffect readAlphaInveseEffect(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        DmlAlphaInverseEffect alphaInverse = new DmlAlphaInverseEffect();
        alphaInverse.setColor(DmlColorReader.read(xmlReader, complianceInfo));
        return alphaInverse;
    }

    static DmlAlphaBiLevelEffect readAlphaBiLevelEffect(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        DmlAlphaBiLevelEffect alphaBiLevel = new DmlAlphaBiLevelEffect();
        alphaBiLevel.setThreshold(DmlPercentageUtil.fromPercentOrDmlPercent(xmlReader.readAttribute("thresh", ""), complianceInfo));
        return alphaBiLevel;
    }

    static DmlBlurEffect readBlurEffect(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        DmlBlurEffect blurEffect = new DmlBlurEffect();
        blurEffect.setRadius(xmlReader.readDoubleAttribute("rad", 0.0));
        blurEffect.setGrow(xmlReader.readBoolAttribute("grow", true));
        return blurEffect;
    }

    static DmlBiLevelEffect readBiLevelEffect(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        DmlBiLevelEffect biLevelEffect = new DmlBiLevelEffect();
        biLevelEffect.setThreshold(DmlPercentageUtil.fromPercentOrDmlPercent(xmlReader.readAttribute("thresh", ""), complianceInfo));
        return biLevelEffect;
    }

    static DmlColorChangeEffect readColorChangeEffect(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        DmlColorChangeEffect colorChangeEffect = new DmlColorChangeEffect();
        colorChangeEffect.setConsiderAlphaValues(xmlReader.readBoolAttribute("useA", true));
        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"clrFrom"*/24:
                    colorChangeEffect.setSourceColor(DmlColorReader.readColor(xmlReader, complianceInfo));
                    break;
                case /*"clrTo"*/25:
                    colorChangeEffect.setDestinationColor(DmlColorReader.readColor(xmlReader, complianceInfo));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
        return colorChangeEffect;
    }

    static DmlGrayScaleEffect readGrayScaleEffect()
    {
        return new DmlGrayScaleEffect();
    }

    static DmlLuminanceEffect readLuminanceEffect(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        DmlLuminanceEffect luminanceEffect = new DmlLuminanceEffect();
        luminanceEffect.setBrightness(readBrightness(xmlReader, complianceInfo));
        luminanceEffect.setContrast(readContrast(xmlReader, complianceInfo));
        return luminanceEffect;
    }

    static DmlDuotoneEffect readDuotoneEffect(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        DmlDuotoneEffect duotoneEffect = new DmlDuotoneEffect();

        String tagName = xmlReader.getLocalName();

        // Read the first color.
        if (xmlReader.readChild(tagName))
            duotoneEffect.setColor1(DmlColorReader.read(xmlReader, complianceInfo));

        // Read the second color.
        if (xmlReader.readChild(tagName))
            duotoneEffect.setColor2(DmlColorReader.read(xmlReader, complianceInfo));

        return duotoneEffect;
    }

    private static double readContrast(NrxXmlReader xmlReader, OoxmlComplianceInfo complianceInfo)
    {
        // Change contrast ranges from (-1, 1) to (0, 1).
        double percentVal =
            DmlPercentageUtil.fromPercentOrDmlPercent(xmlReader.readAttribute("contrast", ""), complianceInfo);
        return (percentVal + 1.0)/2.0;
    }

    private static double readBrightness(NrxXmlReader xmlReader, OoxmlComplianceInfo complianceInfo)
    {
        // Change brightness ranges from (-1, 1) to (0, 1).
        double percentVal =
            DmlPercentageUtil.fromPercentOrDmlPercent(xmlReader.readAttribute("bright", ""), complianceInfo);
        return (percentVal + 1.0)/2.0;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"biLevel",
		"clrChange",
		"grayscl",
		"lum",
		"duotone",
		"blur",
		"alphaBiLevel",
		"alphaCeiling",
		"alphaFloor",
		"alphaInv",
		"alphaMod",
		"alphaModFix",
		"alphaRepl",
		"clrRepl",
		"fillOverlay",
		"hsl",
		"tint",
		"blipFill",
		"gradFill",
		"grpFill",
		"pattFill",
		"noFill",
		"solidFill",
		"cont",
		"clrFrom",
		"clrTo"
	);


}

