// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/24/2013 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ConvertUtilCore;


class DmlShapeEffectReader extends DmlReaderBase
{
    private DmlShapeEffectReader(DocxDocumentReaderBase reader)
    {
        mDocumentReader = reader;
    }

    /// <summary>
    /// Reads by specified XML reader and returns effects collection.
    /// </summary>
    static DmlShapeEffectsCollection readEffects(DocxDocumentReaderBase reader, boolean isTheme, boolean isEffectDag) throws Exception
    {
        DmlShapeEffectsCollection effects = new DmlShapeEffectsCollection(isTheme, isEffectDag);
        NrxXmlReader xmlReader = reader.getXmlReader();

        String tagName = xmlReader.getLocalName();

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"name"*/0:
                    effects.setDagName(xmlReader.getValue());
                    break;
                case /*"type"*/1:
                    effects.setDagType(DmlEnum.dmlToEffectDagType(xmlReader.getValue()));
                    break;
                default:
                    warnUnexpected(xmlReader);
                    break;
            }
        }

        while (xmlReader.readChild(tagName))
        {
            // Children tag are effects tag.
            // Processing is delegated to the ShapeEffectBuilder.
            DmlShapeEffect dmlEffect = read(reader);
            if (dmlEffect != null)
                effects.addEffect(dmlEffect);
        }

        return effects;
    }

    /// <summary>
    /// Builds an effect object by specified XML reader.
    /// </summary>
    static DmlShapeEffect read(DocxDocumentReaderBase reader) throws Exception
    {
        DmlShapeEffectReader shapeEffectReader = new DmlShapeEffectReader(reader);

        switch (gStringSwitchMap.of(reader.getXmlReader().getLocalName()))
        {
            case /*"blur"*/2:
                return shapeEffectReader.readBlur();
            case /*"fillOverlay"*/3:
                return shapeEffectReader.readFillOverlay();
            case /*"glow"*/4:
                return shapeEffectReader.readGlow();
            case /*"innerShdw"*/5:
                return shapeEffectReader.readInnerShadow();
            case /*"shadow"*/6: // This is shadow definition for text effect shadow.
            case /*"outerShdw"*/7:
                return shapeEffectReader.readOuterShadow();
            case /*"prstShdw"*/8:
                return shapeEffectReader.readPresetShadow();
            case /*"reflection"*/9:
                return shapeEffectReader.readReflection();
            case /*"softEdge"*/10:
                return shapeEffectReader.readSoftEdge();
            default:
                warnUnexpectedAndIgnoreElement(reader.getXmlReader());
                return null;
        }
    }

    private DmlShapeBlurEffect readBlur()
    {
        DmlShapeBlurEffect blurEffect = new DmlShapeBlurEffect();
        // When blur is set in effectsLst radius is set in emus instead of pixels.
        blurEffect.setRadius(ConvertUtilCore.emuToPixel((int)getXmlReader().readDoubleAttribute("rad", 0.0)));
        blurEffect.setGrow(getXmlReader().readBoolAttribute("grow", true));
        return blurEffect;
    }

    private DmlShapeFillOverlayEffect readFillOverlay() throws Exception
    {
        DmlShapeFillOverlayEffect fillOverlayEffect = new DmlShapeFillOverlayEffect();
        fillOverlayEffect.setBlend(DmlEnum.dmlToEffectBlendMode(getXmlReader().readAttribute("blend", "")));

        while (getXmlReader().readChild("fillOverlay"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"blipFill"*/11:
                case /*"gradFill"*/12:
                case /*"grpFill"*/13:
                case /*"pattFill"*/14:
                case /*"noFill"*/15:
                case /*"solidFill"*/16:
                    fillOverlayEffect.setFill(DmlFillReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        return fillOverlayEffect;
    }

    private DmlShapeGlowEffect readGlow()
    {
        DmlShapeGlowEffect glowEffect = new DmlShapeGlowEffect();
        glowEffect.setRadius(validate(getXmlReader().readDoubleAttribute("rad", 0.0), MAX_GLOW_BLUR_VALUE));
        getXmlReader().moveToElement();
        glowEffect.setColor(DmlColorReader.readColor(getXmlReader(), getComplianceInfo()));
        return glowEffect;
    }

    private DmlShapeEffect readInnerShadow()
    {
        DmlShapeInnerShadowEffect innerShadowEffect = new DmlShapeInnerShadowEffect();

        while (getXmlReader().moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"blurRad"*/17:
                    innerShadowEffect.setBlurRadius(validate(getXmlReader().getValueAsDouble(), MAX_SHADOW_BLUR_VALUE));
                    break;
                case /*"dist"*/18:
                    innerShadowEffect.setDistance(validate(getXmlReader().getValueAsDouble(), MAX_SHADOW_DISTANCE_VALUE));
                    break;
                case /*"dir"*/19:
                    innerShadowEffect.setDirection(new DmlAngle(getXmlReader().getValueAsDouble()));
                    break;
                default:
                    warnUnexpected(getXmlReader());
                    break;
            }
        }

        getXmlReader().moveToElement();
        innerShadowEffect.setColor(DmlColorReader.readColor(getXmlReader(), getComplianceInfo()));

        return innerShadowEffect;
    }

    private DmlShapeOuterShadowEffect readOuterShadow()
    {
        DmlShapeOuterShadowEffect outerShadowEffect = new DmlShapeOuterShadowEffect();

        while (getXmlReader().moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"blurRad"*/17:
                    outerShadowEffect.setBlurRadius(validate(getXmlReader().getValueAsDouble(), MAX_SHADOW_BLUR_VALUE));
                    break;
                case /*"dist"*/18:
                    outerShadowEffect.setDistance(validate(getXmlReader().getValueAsDouble(), MAX_SHADOW_DISTANCE_VALUE));
                    break;
                case /*"dir"*/19:
                    outerShadowEffect.setDirection(new DmlAngle(getXmlReader().getValueAsDouble()));
                    break;
                case /*"sx"*/20:
                    outerShadowEffect.setHorizontalScale(validateScaleValue(DmlPercentageUtil.fromPercentOrDmlPercent(getXmlReader().getValue(), getComplianceInfo())));
                    break;
                case /*"sy"*/21:
                    outerShadowEffect.setVerticalScale(validateScaleValue(DmlPercentageUtil.fromPercentOrDmlPercent(getXmlReader().getValue(), getComplianceInfo())));
                    break;
                case /*"kx"*/22:
                    outerShadowEffect.setHorizontalSkew(new DmlAngle(getXmlReader().getValueAsDouble()));
                    break;
                case /*"ky"*/23:
                    outerShadowEffect.setVerticalSkew(new DmlAngle(getXmlReader().getValueAsDouble()));
                    break;
                case /*"algn"*/24:
                    outerShadowEffect.setAlignment(valueToAlignment(getXmlReader().getValue()));
                    break;
                case /*"rotWithShape"*/25:
                    outerShadowEffect.setRotateWithShape(getXmlReader().getValueAsBool());
                    break;
                default:
                    warnUnexpected(getXmlReader());
                    break;
            }
        }

        getXmlReader().moveToElement();

        // WORDSNET-20570 Added resilience to not allow 'null' color for non-empty shadow effect in model.
        DmlColor color = DmlColorReader.readColor(getXmlReader(), getComplianceInfo());
        outerShadowEffect.setColor((color == null) ? DmlColor.EMPTY : color);

        return outerShadowEffect;
    }

    private DmlShapePresetShadowEffect readPresetShadow()
    {
        DmlShapePresetShadowEffect prstShadowEffect = new DmlShapePresetShadowEffect();

        while (getXmlReader().moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"dist"*/18:
                    prstShadowEffect.setDistance(validate(getXmlReader().getValueAsDouble(), MAX_SHADOW_DISTANCE_VALUE));
                    break;
                case /*"dir"*/19:
                    prstShadowEffect.setDirection(new DmlAngle(getXmlReader().getValueAsDouble()));
                    break;
                case /*"prst"*/26:
                    prstShadowEffect.setPresetShadow(DmlEnum.dmlToPresetShadow(getXmlReader().getValue()));
                    break;
                default:
                    warnUnexpected(getXmlReader());
                    break;
            }
        }

        getXmlReader().moveToElement();
        prstShadowEffect.setColor(DmlColorReader.readColor(getXmlReader(), getComplianceInfo()));

        return prstShadowEffect;
    }

    private DmlShapeEffect readReflection()
    {
        DmlShapeReflectionEffect reflectionEffect = new DmlShapeReflectionEffect();

        while (getXmlReader().moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"blurRad"*/17:
                    reflectionEffect.setBlurRadius(validate(getXmlReader().getValueAsDouble(), MAX_SHADOW_BLUR_VALUE));
                    break;
                case /*"stA"*/27:
                    reflectionEffect.setStartAlpha(DmlPercentageUtil.fromPercentOrDmlPercent(getXmlReader().getValue(), getComplianceInfo()));
                    break;
                case /*"stPos"*/28:
                    reflectionEffect.setStartPosition(DmlPercentageUtil.fromPercentOrDmlPercent(getXmlReader().getValue(), getComplianceInfo()));
                    break;
                case /*"endA"*/29:
                    reflectionEffect.setEndAlpha(DmlPercentageUtil.fromPercentOrDmlPercent(getXmlReader().getValue(), getComplianceInfo()));
                    break;
                case /*"endPos"*/30:
                    reflectionEffect.setEndPosition(DmlPercentageUtil.fromPercentOrDmlPercent(getXmlReader().getValue(), getComplianceInfo()));
                    break;
                case /*"dist"*/18:
                    reflectionEffect.setDistance(validate(getXmlReader().getValueAsDouble(), MAX_REFLECTION_DISTANCE_VALUE));
                    break;
                case /*"dir"*/19:
                    reflectionEffect.setDirection(new DmlAngle(getXmlReader().getValueAsDouble()));
                    break;
                case /*"fadeDir"*/31:
                    reflectionEffect.setFadeDirection(new DmlAngle(getXmlReader().getValueAsDouble()));
                    break;
                case /*"sx"*/20:
                    reflectionEffect.setHorizontalScale(validateScaleValue(DmlPercentageUtil.fromPercentOrDmlPercent(getXmlReader().getValue(), getComplianceInfo())));
                    break;
                case /*"sy"*/21:
                    reflectionEffect.setVerticalScale(validateScaleValue(DmlPercentageUtil.fromPercentOrDmlPercent(getXmlReader().getValue(), getComplianceInfo())));
                    break;
                case /*"kx"*/22:
                    reflectionEffect.setHorizontalSkew(new DmlAngle(getXmlReader().getValueAsDouble()));
                    break;
                case /*"ky"*/23:
                    reflectionEffect.setVerticalSkew(new DmlAngle(getXmlReader().getValueAsDouble()));
                    break;
                case /*"algn"*/24:
                    reflectionEffect.setAlignment(valueToAlignment(getXmlReader().getValue()));
                    break;
                case /*"rotWithShape"*/25:
                    reflectionEffect.setRotateWithShape(getXmlReader().getValueAsBool());
                    break;
                default:
                    warnUnexpected(getXmlReader());
                    break;
            }
        }

        getXmlReader().moveToElement();

        return reflectionEffect;
    }

    private static double validate(double srcValue, double maxValue)
    {
        if (srcValue < 0.0)
            srcValue = srcValue * (-1.0);

        if (srcValue > maxValue)
            srcValue = maxValue;

        return srcValue;
    }

    private static double validateScaleValue(double srcValue)
    {
        if (srcValue > MAX_SCALE_VALUE)
            srcValue = 0.0;

        return srcValue;
    }

    private DmlShapeSoftEdgeEffect readSoftEdge()
    {
        DmlShapeSoftEdgeEffect softEdgeEffect = new DmlShapeSoftEdgeEffect();
        softEdgeEffect.setRadius(getXmlReader().readDoubleAttribute("rad", 0.0));
        return softEdgeEffect;
    }

    private static /*DmlRectangleAlignment*/int valueToAlignment(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"tl"*/32:
                return DmlRectangleAlignment.TOP_LEFT;
            case /*"t"*/33:
                return DmlRectangleAlignment.TOP;
            case /*"tr"*/34:
                return DmlRectangleAlignment.TOP_RIGHT;
            case /*"l"*/35:
                return DmlRectangleAlignment.LEFT;
            case /*"ctr"*/36:
                return DmlRectangleAlignment.CENTER;
            case /*"r"*/37:
                return DmlRectangleAlignment.RIGHT;
            case /*"bl"*/38:
                return DmlRectangleAlignment.BOTTOM_LEFT;
            case /*"b"*/39:
                return DmlRectangleAlignment.BOTTOM;
            case /*"br"*/40:
                return DmlRectangleAlignment.BOTTOM_RIGHT;
            case /*"none"*/41: // appears in Dml Text Effects
                return DmlRectangleAlignment.NONE;
            default:
                return DmlRectangleAlignment.TOP_LEFT;
        }
    }

    private NrxXmlReader getXmlReader() { return mDocumentReader.getXmlReader(); }

    private OoxmlComplianceInfo getComplianceInfo() { return mDocumentReader.getComplianceInfo(); }

    private /*final*/ DocxDocumentReaderBase mDocumentReader;

    // Values ​​are taken based on valid values ​​in Word.
    private static final double MAX_SHADOW_BLUR_VALUE = 1270000.0;
    private static final double MAX_GLOW_BLUR_VALUE = 1905000.0;
    private static final double MAX_SHADOW_DISTANCE_VALUE = 2540000.0;
    private static final double MAX_REFLECTION_DISTANCE_VALUE = 1270000.0;
    private static final double MAX_SCALE_VALUE = 2.0;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"name",
		"type",
		"blur",
		"fillOverlay",
		"glow",
		"innerShdw",
		"shadow",
		"outerShdw",
		"prstShdw",
		"reflection",
		"softEdge",
		"blipFill",
		"gradFill",
		"grpFill",
		"pattFill",
		"noFill",
		"solidFill",
		"blurRad",
		"dist",
		"dir",
		"sx",
		"sy",
		"kx",
		"ky",
		"algn",
		"rotWithShape",
		"prst",
		"stA",
		"stPos",
		"endA",
		"endPos",
		"fadeDir",
		"tl",
		"t",
		"tr",
		"l",
		"ctr",
		"r",
		"bl",
		"b",
		"br",
		"none"
	);


}

