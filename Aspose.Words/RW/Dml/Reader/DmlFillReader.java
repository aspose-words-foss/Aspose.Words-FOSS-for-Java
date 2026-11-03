// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.StringUtil;


/// <summary>
/// Represents a class building DmlFill objects from xml.
/// </summary>
class DmlFillReader extends DmlReaderBase
{
    private DmlFillReader(DocxDocumentReaderBase reader)
    {
        mDocumentReader = reader;
    }

    /// <summary>
    /// Reads shape fill from a specified reader.
    /// </summary>
    static DmlFill read(DocxDocumentReaderBase reader) throws Exception
    {
        DmlFillReader fillReader = new DmlFillReader(reader);
        return fillReader.readCore();
    }

    /// <summary>
    /// Reads text fill from a specified reader.
    /// </summary>
    static DmlFill readTextFill(DocxDocumentReaderBase reader)
    {
        DmlFillReader fillReader = new DmlFillReader(reader);

        String element = reader.getXmlReader().getLocalName();
        while (reader.getXmlReader().readChild(element))
        {
            DmlFill fill = fillReader.readTextFillCore();

            if (fill != null)
                return fill;
        }

        return null;
        }

    /// <summary>
    /// Reads text outline fill from a specified reader.
    /// </summary>
    static DmlFill readTextOutlineFill(DocxDocumentReaderBase reader)
    {
        DmlFillReader fillReader = new DmlFillReader(reader);
            return fillReader.readTextFillCore();
    }

    /// <summary>
    /// Reads text fill core.
    /// </summary>
    private DmlFill readTextFillCore()
    {
        switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
        {
            case /*"gradFill"*/0:
                return readGradientFill();
            case /*"noFill"*/1:
                return readNoFill();
            case /*"solidFill"*/2:
                return readSolidFill();
            default:
                warnUnexpectedAndIgnoreElement(getXmlReader());
                return null;
        }
    }

    /// <summary>
    /// Reads shape fill core.
    /// </summary>
    private DmlFill readCore() throws Exception
    {
        switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
        {
            case /*"blipFill"*/3:
                return readBlipFill();
            case /*"gradFill"*/0:
                return readGradientFill();
            case /*"grpFill"*/4:
                return readGroupFill();
            case /*"noFill"*/1:
                return readNoFill();
            case /*"pattFill"*/5:
                return readPatternFill();
            case /*"solidFill"*/2:
                return readSolidFill();
            default:
                warnUnexpectedAndIgnoreElement(getXmlReader());
                return null;
        }
    }

    /// <summary>
    /// Reads shape solid fill.
    /// </summary>
    private DmlSolidFill readSolidFill()
    {
        // WORDSNET-10911 If color of solid fill is not specified, treat as there is no fill at all.
        DmlColor fillColor = DmlColorReader.readColor(getXmlReader(), getComplianceInfo());
        if (fillColor == null)
            return null;

        DmlSolidFill fill = new DmlSolidFill();
        fill.setColor(fillColor);
        return fill;
    }

    /// <summary>
    /// Reads patterned fill.
    /// </summary>
    private DmlPatternFill readPatternFill()
    {
        DmlPatternFill fill = new DmlPatternFill();
        fill.setFillPresetPattern(readPatternType());
        while (getXmlReader().readChild("pattFill"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"bgClr"*/6:
                    fill.setBackgroundColor(DmlColorReader.readColor(getXmlReader(), getComplianceInfo())); // Children tags are colors
                    break;
                case /*"fgClr"*/7:
                    fill.setForegroundColor(DmlColorReader.readColor(getXmlReader(), getComplianceInfo())); // Children tags are colors
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
        return fill;
    }

    /// <summary>
    /// Reads shape 'NoFill' fill.
    /// </summary>
    private static DmlNoFill readNoFill()
    {
        return new DmlNoFill();
    }

    /// <summary>
    /// Reads group fill.
    /// </summary>
    private static DmlGroupFill readGroupFill()
    {
        return new DmlGroupFill();
    }

    /// <summary>
    /// Reads shape gradient fill.
    /// </summary>
    private DmlGradientFill readGradientFill()
    {
        DmlGradientFill fill = new DmlGradientFill();
        fill.setTileFlipMode(readTileFlipAttribute());

        // WORDSNET-17952 The default value for "rotate with shape" flag is "true".
        fill.setRotateWithShape(getXmlReader().readBoolAttribute("rotWithShape", true));

        readGradientFillChild(fill);

        return fill;
    }
    /// <summary>
    /// Reads children of 'gradFill' element.
    /// </summary>
    private void readGradientFillChild(DmlGradientFill fill)
    {
        while (getXmlReader().readChild("gradFill"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"gsLst"*/8:
                    fill.setGradientStops(readGradientStops());
                    break;
                case /*"lin"*/9:
                    fill.setGradient(readLinearGradient());
                    break;
                case /*"path"*/10:
                    fill.setGradient(readPathGradient());
                    break;
                case /*"tileRect"*/11:
                    fill.setTileRectangle(readPercentageOffsetRectangle());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
    }

    private DmlPathGradient readPathGradient()
    {
        DmlPathGradient pathGradient = new DmlPathGradient();
        pathGradient.setPath(readPathShadeType());
        while (getXmlReader().readChild("path"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"fillToRect"*/12:
                    pathGradient.setFillToRectangle(readPercentageOffsetRectangle());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
        return pathGradient;
    }

    private DmlPercentageOffsetRectangle readPercentageOffsetRectangle()
    {
        DmlPercentageOffsetRectangle fillToRectangle = new DmlPercentageOffsetRectangle();
        fillToRectangle.setBottomOffset(DmlPercentageUtil.fromPercentOrDmlPercent(getXmlReader().readAttribute("b", ""), getComplianceInfo()));
        fillToRectangle.setTopOffset(DmlPercentageUtil.fromPercentOrDmlPercent(getXmlReader().readAttribute("t", ""), getComplianceInfo()));
        fillToRectangle.setLeftOffset(DmlPercentageUtil.fromPercentOrDmlPercent(getXmlReader().readAttribute("l", ""), getComplianceInfo()));
        fillToRectangle.setRightOffset(DmlPercentageUtil.fromPercentOrDmlPercent(getXmlReader().readAttribute("r", ""), getComplianceInfo()));
        return fillToRectangle;
    }

    private /*DmlPathShadeType*/int readPathShadeType()
    {
        String value = getXmlReader().readAttribute("path", "");
        return DmlEnum.dmlToPathShadeType(value);
    }

    private DmlLinearGradient readLinearGradient()
    {
        DmlLinearGradient fill = new DmlLinearGradient();
        fill.setAngle(new DmlAngle(getXmlReader().readDoubleAttribute("ang", 0.0)));
        fill.isScaled(getXmlReader().readBoolAttribute("scaled", false));
        return fill;
    }

    private ArrayList<DmlGradientStop> readGradientStops()
    {
        ArrayList<DmlGradientStop> gradientStops = new ArrayList<DmlGradientStop>();
        while (getXmlReader().readChild("gsLst"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"gs"*/13:
                    DmlGradientStop gs = readGradientStop();
                    gs.setOriginalOrder(gradientStops.size());
                    msArrayList.add(gradientStops, gs);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        // WORDSNET-9594 Gradient stops are in incorrect order, that causes problems upon creating GDI gradient brush,
        // fixed by sorting stops by position.
        // WORDSNET-22003 Actually, Word VBA uses original order to determine gradient styles and variants and as a result
        // the ForeColor and BackColor. In addition, Word seems always inserts new gradient stops at the very start of
        // gradient stop collection. As the result, it is more convenient and natural to keep in model gradient
        // stops unsorted and sort it only just before Rendering.
        // So, please do not sort gradient stops here!

        return gradientStops;
    }

    private DmlGradientStop readGradientStop()
    {
        DmlGradientStop gradientStop = new DmlGradientStop();
        gradientStop.setPosition(DmlPercentageUtil.fromPercentOrDmlPercent(getXmlReader().readAttribute("pos", ""), getComplianceInfo()));
        gradientStop.setColor(DmlColorReader.readColor(getXmlReader(), getComplianceInfo())); // Children tags are colors
        return gradientStop;
    }

    private /*DmlTileFlipMode*/int readTileFlipAttribute()
    {
        String value = getXmlReader().readAttribute("flip", "");
        return DmlEnum.dmlToTileFlipMode(value);
    }

    private DmlBlipFill readBlipFill() throws Exception
    {
        DmlBlipFill fill = new DmlBlipFill();
        fill.setDotsPerInch(getXmlReader().readUIntAttribute("dpi", 0));
        fill.setRotateWithShape(getXmlReader().readBoolAttribute("rotWithShape", true));

        while (getXmlReader().readChild("blipFill"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"blip"*/14:
                    fill.setBlip(readBlip(mDocumentReader));
                    break;
                case /*"srcRect"*/15:
                    fill.setSourceRectangle(readPercentageOffsetRectangle());
                    break;
                case /*"stretch"*/16:
                    fill.setBlipFillMode(readStretch());
                    break;
                case /*"tile"*/17:
                    fill.setBlipFillMode(readTile());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
        return fill;
    }

    private DmlBlipFillTile readTile()
    {
        DmlBlipFillTile tile = new DmlBlipFillTile();
        tile.setAlignment(readAlignment());
        tile.setTileFlipMode(readTileFlipAttribute());
        tile.setHorizontalRatio(DmlPercentageUtil.fromPercentOrDmlPercent(getXmlReader().readAttribute("sx", ""), 1.0, getComplianceInfo()));
        tile.setVerticalRatio(DmlPercentageUtil.fromPercentOrDmlPercent(getXmlReader().readAttribute("sy", ""), 1.0, getComplianceInfo()));
        tile.setHorizontalOffset(getXmlReader().readAttributeAsEmus("tx", 0.0, getComplianceInfo()));
        tile.setVerticalOffset(getXmlReader().readAttributeAsEmus("ty", 0.0, getComplianceInfo()));
        return tile;
    }

    private /*DmlRectangleAlignment*/int readAlignment()
    {
        return DmlEnum.dmlToRectangleAlignment(getXmlReader().readAttribute("algn", ""));
    }

    private DmlBlipFillStretch readStretch()
    {
        DmlBlipFillStretch blipFillStretch = new DmlBlipFillStretch();
        while (getXmlReader().readChild("stretch"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"fillRect"*/18:
                    blipFillStretch.setFillRectangle(readPercentageOffsetRectangle());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
        return blipFillStretch;
    }

    static DmlBlip readBlip(DocxDocumentReaderBase reader) throws Exception
    {
        DmlBlip blip = new DmlBlip();
        blip.setCompressionState(readCompressionState(reader.getXmlReader()));
        blip.setDocument(reader.getDocument());

        String embed = reader.getXmlReader().readAttribute("embed", "");
        if (StringUtil.hasChars(embed))
            blip.setEmbedImage(reader.getBinData(embed));

        String link = reader.getXmlReader().readAttribute("link", "");
        if (StringUtil.hasChars(link))
            blip.setImageLink(reader.getRelationshipTarget(link));

        readBlipChildren(blip, reader);
        return blip;
    }

    private static void readBlipChildren(DmlBlip blip, DocxDocumentReaderBase reader) throws Exception
    {
        ArrayList<DmlEffect> effects = new ArrayList<DmlEffect>();
        NrxXmlReader xmlReader = reader.getXmlReader();

        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"extLst"*/19:
                    blip.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                case /*"biLevel"*/20:
                    effects.add(DmlEffectReader.readBiLevelEffect(reader));
                    break;
                case /*"clrChange"*/21:
                    effects.add(DmlEffectReader.readColorChangeEffect(reader));
                    break;
                case /*"grayscl"*/22:
                    effects.add(DmlEffectReader.readGrayScaleEffect());
                    break;
                case /*"lum"*/23:
                    effects.add(DmlEffectReader.readLuminanceEffect(reader));
                    break;
                case /*"duotone"*/24:
                    effects.add(DmlEffectReader.readDuotoneEffect(reader));
                    break;
                case /*"blur"*/25:
                    effects.add(DmlEffectReader.readBlurEffect(reader));
                    break;
                case /*"alphaBiLevel"*/26:
                    effects.add(DmlEffectReader.readAlphaBiLevelEffect(reader));
                    break;
                case /*"alphaCeiling"*/27:
                    effects.add(new DmlAlphaCeilingEffect());
                    break;
                case /*"alphaFloor"*/28:
                    effects.add(new DmlAlphaFloorEffect());
                    break;
                case /*"alphaInv"*/29:
                    effects.add(DmlEffectReader.readAlphaInveseEffect(reader));
                    break;
                case /*"alphaMod"*/30:
                    effects.add(DmlEffectReader.readAlphaModulateEffect(reader));
                    break;
                case /*"alphaModFix"*/31:
                    effects.add(DmlEffectReader.readAlphaModulateFixedEffect(reader));
                    break;
                case /*"alphaRepl"*/32:
                    effects.add(DmlEffectReader.readAlphaReplaceEffect(reader));
                    break;
                case /*"clrRepl"*/33:
                    effects.add(DmlEffectReader.readColorReplaceEffect(reader));
                    break;
                case /*"fillOverlay"*/34:
                    effects.add(DmlEffectReader.readFillOverlayEffect(reader));
                    break;
                case /*"hsl"*/35:
                    effects.add(DmlEffectReader.readHueSaturationLuminanceEffect(reader));
                    break;
                case /*"tint"*/36:
                    effects.add(DmlEffectReader.readTintEffect(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        blip.setEffects(effects);
    }

    private static /*DmlCompressionState*/int readCompressionState(NrxXmlReader xmlReader)
    {
        return DmlEnum.dmlToCompressionState(xmlReader.readAttribute("cstate", ""));
    }

    private /*PatternType*/int readPatternType()
    {
        String value = getXmlReader().readAttribute("prst", "");
        return DmlEnum.dmlToPatternType(value);
    }

    private NrxXmlReader getXmlReader() { return mDocumentReader.getXmlReader(); }

    private OoxmlComplianceInfo getComplianceInfo() { return mDocumentReader.getComplianceInfo(); }

    private /*final*/ DocxDocumentReaderBase mDocumentReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"gradFill",
		"noFill",
		"solidFill",
		"blipFill",
		"grpFill",
		"pattFill",
		"bgClr",
		"fgClr",
		"gsLst",
		"lin",
		"path",
		"tileRect",
		"fillToRect",
		"gs",
		"blip",
		"srcRect",
		"stretch",
		"tile",
		"fillRect",
		"extLst",
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
		"tint"
	);

}

