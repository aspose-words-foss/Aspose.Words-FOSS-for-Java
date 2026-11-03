// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/11/2015 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.DrColor;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.ms.System.StringComparison;


class VmlFillReader extends VmlShapeReaderBase
{
    static void readFill(ShapeBase shape, IVmlShapeReaderContext context)
    {
        NrxXmlReader reader = context.getXmlReader();

        boolean checkGradient = false;

        while (reader.moveToNextAttribute())
        {
            String value = reader.getValue();

            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"id"*/0:  // r:id DOCX.
                case /*"src"*/1: // WordML.
                {
                    // WORDSNET-22742 Some bindings are unavailable at the time the markup is parsed.
                    // So memorize the fill source for deferred binding.
                    context.getXmlReader().getFillSourceMap().put(shape, value);
                    break;
                }
                case /*"title"*/2: // o:title
                    if (!shape.getShapePr().contains(ShapeAttr.FILL_BLIP_NAME))
                        shape.setShapeAttrInternal(ShapeAttr.FILL_BLIP_NAME, value);
                    break;
                case /*"opacity"*/3:
                    setFixedAttribute(shape, ShapeAttr.FILL_OPACITY, value);
                    break;
                case /*"color"*/4:
                    // WORDSNET-11137 Seems, MS Word reads this attribute, but does not write this again to fill element upon resaving.
                    // Instead, it overrides 'fillcolor' attribute with this color.
                    shape.setShapeAttrInternal(ShapeAttr.FILL_COLOR, VmlColor.vmlToColor(value));
                    break;
                case /*"color2"*/5:
                    shape.setShapeAttrInternal(ShapeAttr.FILL_BACK_COLOR, VmlColor.vmlToColor(value));

                    DrColor baseColor = VmlColor.getBaseColor(value);
                    if (baseColor != null)
                        shape.setShapeAttrInternal(ShapeAttr.FILL_BACK_COLOR_EXT, baseColor);
                    int colorModifier = VmlColor.getColorModifier(value);
                    if (colorModifier != 0)
                        shape.setShapeAttrInternal(ShapeAttr.FILL_BACK_COLOR_EXT_MOD, colorModifier);
                    break;
                case /*"opacity2"*/6: // o:opacity2
                    setFixedAttribute(shape, ShapeAttr.FILL_BACK_OPACITY, value);
                    break;
                case /*"aspect"*/7:
                    shape.setShapeAttrInternal(ShapeAttr.FILL_DIMENSION_TYPE, VmlEnum.vmlToDzType(value));
                    break;
                case /*"origin"*/8:
                    set2DFixed(shape, ShapeAttr.FILL_ORIGIN_X, ShapeAttr.FILL_ORIGIN_Y, value);
                    break;
                case /*"position"*/9:
                    set2DFixed(shape, ShapeAttr.FILL_SHAPE_ORIGIN_X, ShapeAttr.FILL_SHAPE_ORIGIN_Y, value);
                    break;
                case /*"size"*/10:
                    // v:fill[size] Seen only as size="0,0" in TestFillStyle ms.wml(542)
                    // and that didn't correspond to any attribute. Need to check. Ignored now.
                    break;
                case /*"recolor"*/11:
                    setBoolAttribute(shape, ShapeAttr.FILL_RECOLOR_AS_PICTURE, value);
                    break;
                case /*"rotate"*/12:
                    setBoolAttribute(shape, ShapeAttr.FILL_USE_SHAPE_ANCHOR, value);
                    break;
                case /*"angle"*/13:
                    setFixedAttribute(shape, ShapeAttr.FILL_ANGLE, value);
                    break;
                case /*"colors"*/14:
                    Object gradientColorsValue = vmlToGradientColors(value);
                    // VmlToGradientColors can return null, do not set ShapeAttr in this case for stability.
                    if (gradientColorsValue != null)
                        shape.setShapeAttrInternal(ShapeAttr.FILL_SHADE_COLORS, gradientColorsValue);
                    break;
                case /*"focusposition"*/15:
                {
                    set2DFixed(shape, ShapeAttr.FILL_TO_LEFT, ShapeAttr.FILL_TO_TOP, value);
                    set2DFixed(shape, ShapeAttr.FILL_TO_RIGHT, ShapeAttr.FILL_TO_BOTTOM, value);
                    break;
                }
                case /*"focussize"*/16:
                    // Have not seen 'focussize' taking values other than empty string. Hence, ignored.
                    break;
                case /*"method"*/17:
                    if ("linear sigma".equals(value))
                        shape.setShapeAttrInternal(ShapeAttr.FILL_SHADE_TYPE, VmlEnum.LINEAR_SIGMA_GRADIENT);
                    else
                        shape.setShapeAttrInternal(ShapeAttr.FILL_SHADE_TYPE, GradientType.NONE);
                    break;
                case /*"focus"*/18:
                    setPercentAttribute(shape, ShapeAttr.FILL_FOCUS, value);
                    break;
                case /*"type"*/19:
                    shape.setShapeAttrInternal(ShapeAttr.FILL_TYPE, VmlEnum.vmlToFillType(value));

                    if ("gradientRadial".equals(value) || "gradient".equals(value))
                        checkGradient = true;
                    break;
                case /*"detectmouseclick"*/20: // o:detectmouseclick
                    setBoolAttribute(shape, ShapeAttr.FILL_NO_FILL_HIT_TEST, value);
                    break;
                case /*"href"*/21:
                    // Never seen. Ignored.
                    reader.warn(WarningType.DATA_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                    break;
                case /*"althref"*/22:
                    // Never seen. Ignored.
                    reader.warn(WarningType.DATA_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                    break;
                case /*"on"*/23:
                    shape.setShapeAttrInternal(ShapeAttr.FILLED, reader.getValueAsBool());
                    break;
                default:
                    reader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                    break;
            }
        }

        // Read 'o:fill' child element, if exists, to check if the FillType is ShadeCenter.

        if (checkGradient)
        {
            while (reader.readChild("fill")) // v:fill
            {
                // andrnosk: WORDSNET-4164 Check 'o:fill' element to get the FillType
                // which overrides the value of the type attribute in the parent fill element.
                String gradientType = reader.readAttribute("type", "");
                switch (gStringSwitchMap.of(gradientType))
                {
                    case /*"gradientCenter"*/24:
                        shape.setShapeAttrInternal(ShapeAttr.FILL_TYPE, FillTypeCore.SHADE_CENTER);
                        break;
                    case /*"gradientUnscaled"*/25:
                        shape.setShapeAttrInternal(ShapeAttr.FILL_TYPE, FillTypeCore.SHADE_UNSCALE);
                        break;
                    default:
                        break;
                }

            }
        }
    }

    /// <summary>
    /// Parses gradient color array string. Does not throw on invalid values, just returns null.
    /// </summary>
    private static GradientColor[] vmlToGradientColors(String value)
    {
        // WORDSNET-266817 Removes the description of an empty trailing gradient. MS Word ignores such values.
        String[] startColorPairs = (StringUtil.hasChars(value) && msString.endsWith(value, ";", StringComparison.ORDINAL))
                ? msString.split(msString.remove(value, value.length() - 1), ';')
                : msString.split(value, ';');
        GradientColor[] result = new GradientColor[startColorPairs.length];

        for (int i = 0; i < startColorPairs.length; i++)
        {
            String[] startColor = msString.split(startColorPairs[i], ' ');
            VmlQuantity start = new VmlQuantity(startColor[0]);

            if (!start.isFixed())
                return null;

            GradientColor gradientColor = new GradientColor();
            gradientColor.Start = start.toFixed();
            gradientColor.Color = VmlColor.vmlToColor(startColor[1]);

            result[i] = gradientColor;
        }

        return result;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"id",
		"src",
		"title",
		"opacity",
		"color",
		"color2",
		"opacity2",
		"aspect",
		"origin",
		"position",
		"size",
		"recolor",
		"rotate",
		"angle",
		"colors",
		"focusposition",
		"focussize",
		"method",
		"focus",
		"type",
		"detectmouseclick",
		"href",
		"althref",
		"on",
		"gradientCenter",
		"gradientUnscaled"
	);

}

