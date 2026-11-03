// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/11/2015 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;


class VmlShadowReader extends VmlShapeReaderBase
{
    static void readShadow(ShapeBase shape, NrxXmlReader reader)
    {
        while (reader.moveToNextAttribute())
        {
            String value = reader.getValue();

            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"on"*/0:
                    // andrnosk: WORDSNET-12666 If value of this attribute cannot be read 
                    // then default (true) value will be set instead.
                    setBoolAttribute(shape, ShapeAttr.SHADOW_ON, value, true);
                    break;
                case /*"type"*/1:
                    shape.setShapeAttrInternal(ShapeAttr.SHADOW_TYPE, VmlEnum.vmlToShadowType(value));
                    break;
                case /*"color"*/2:
                    shape.setShapeAttrInternal(ShapeAttr.SHADOW_COLOR, VmlColor.vmlToColor(value));
                    break;
                case /*"color2"*/3:
                    shape.setShapeAttrInternal(ShapeAttr.SHADOW_HIGHLIGHT, VmlColor.vmlToColor(value));
                    break;
                case /*"opacity"*/4:
                    setFixedAttribute(shape, ShapeAttr.SHADOW_OPACITY, value);
                    break;
                case /*"origin"*/5:
                    set2DFixed(shape, ShapeAttr.SHADOW_ORIGIN_X, ShapeAttr.SHADOW_ORIGIN_Y, value);
                    break;
                case /*"offset"*/6:
                    set2DEmus(shape, ShapeAttr.SHADOW_OFFSET_X, ShapeAttr.SHADOW_OFFSET_Y, value);
                    break;
                case /*"offset2"*/7:
                    set2DEmus(shape, ShapeAttr.SHADOW_SECOND_OFFSET_X, ShapeAttr.SHADOW_SECOND_OFFSET_Y, value);
                    break;
                case /*"matrix"*/8:
                    setShadowMatrix(shape, value);
                    break;
                case /*"obscured"*/9:
                    // WORDSNET-19502 Added handling of invalid values.
                    setBoolAttributeForce(shape, ShapeAttr.SHADOW_OBSCURED, value);
                    break;
                default:
                    reader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                    break;
            }
        }
    }

    private static void set2DEmus(ShapeBase shape, int attrX, int attrY, String value)
    {
        VmlQuantity[] attrValues = vmlToQuantityArray(value);

        setEmusAttribute(shape, attrX, attrValues[0]);

        if (attrValues.length > 1)
            setEmusAttribute(shape, attrY, attrValues[1]);
    }

    private static void setShadowMatrix(ShapeBase shape, String matrix)
    {
        String[] values = msString.split(matrix, ',');

        int index = 0;
        int maxIndex = values.length - 1;

        for (int attr = ShapeAttr.SHADOW_SCALE_XTO_X; attr <= ShapeAttr.SHADOW_SCALE_YTO_Y; attr++)
        {
            setFixedAttribute(shape, attr, values[index]);

            index++;

            if (index > maxIndex)
                return;
        }

        for (int attr = ShapeAttr.SHADOW_PERSPECTIVE_X; attr <= ShapeAttr.SHADOW_PERSPECTIVE_Y; attr++)
        {
            String value = values[index];

            if (!"".equals(value))
                shape.setShapeAttrInternal(attr, VmlUtil.vmlToPerspective(value));

            index++;

            if (index > maxIndex)
                return;
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"on",
		"type",
		"color",
		"color2",
		"opacity",
		"origin",
		"offset",
		"offset2",
		"matrix",
		"obscured"
	);

}

