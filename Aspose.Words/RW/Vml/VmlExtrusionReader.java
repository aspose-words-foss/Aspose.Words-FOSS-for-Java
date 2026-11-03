// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/11/2015 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;


class VmlExtrusionReader extends VmlShapeReaderBase
{
    static void readExtrusion(ShapeBase shape, NrxXmlReader reader)
    {
        while (reader.moveToNextAttribute())
        {
            String value = reader.getValue();

            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"ext"*/0: // v:ext
                    // Always "view", ignore.
                    break;
                case /*"specularity"*/1:
                    setFixedAttribute(shape, ShapeAttr.TD_SPECULAR_AMOUNT, value);
                    break;
                case /*"diffusity"*/2:
                    setFixedAttribute(shape, ShapeAttr.TD_DIFFUSE_AMOUNT, value);
                    break;
                case /*"shininess"*/3:
                    shape.setShapeAttrInternal(ShapeAttr.TD_SHININESS, reader.getValueAsInt());
                    break;
                case /*"edge"*/4:
                    setEmusAttribute(shape, ShapeAttr.TD_EDGE_THICKNESS, value);
                    break;
                case /*"foredepth"*/5:
                    setEmusAttribute(shape, ShapeAttr.TD_EXTRUDE_FORWARD, value);
                    break;
                case /*"backdepth"*/6:
                    setEmusAttribute(shape, ShapeAttr.TD_EXTRUDE_BACKWARD, value);
                    break;
                case /*"plane"*/7:
                    shape.setShapeAttrInternal(ShapeAttr.TD_EXTRUDE_PLANE, VmlEnum.vmlToPlaneType(value));
                    break;
                case /*"color"*/8:
                    shape.setShapeAttrInternal(ShapeAttr.TD_EXTRUSION_COLOR, VmlColor.vmlToColor(value));
                    break;
                case /*"on"*/9:
                    setBoolAttribute(shape, ShapeAttr.TD_ON, value);
                    break;
                case /*"metal"*/10:
                    setBoolAttribute(shape, ShapeAttr.TD_METALLIC, value);
                    break;
                case /*"lightface"*/11:
                    setBoolAttribute(shape, ShapeAttr.TD_LIGHT_FACE, value);
                    break;
                case /*"rotationangle"*/12:
                {
                    setRotationAngles(shape, value);
                    break;
                }
                case /*"orientation"*/13:
                    set3DInt(shape, ShapeAttr.TD_ROTATION_AXIS_X, ShapeAttr.TD_ROTATION_AXIS_Y, ShapeAttr.TD_ROTATION_AXIS_Z, value);
                    break;
                case /*"orientationangle"*/14:
                    setFixedDegreesAttribute(shape, ShapeAttr.TD_ROTATION_ANGLE, new VmlQuantity(value));
                    break;
                case /*"rotationcenter"*/15:
                    setRotationCenter(shape, value);
                    break;
                case /*"render"*/16:
                    shape.setShapeAttrInternal(ShapeAttr.TD_RENDER_MODE, VmlEnum.vmlToThreeDRenderMode(value));
                    break;
                case /*"facet"*/17:
                    setFixedAttribute(shape, ShapeAttr.TD_TOLERANCE, value);
                    break;
                case /*"viewpoint"*/18:
                    set3DEmus(shape, ShapeAttr.TD_VIEWPOINT_X, ShapeAttr.TD_VIEWPOINT_Y, ShapeAttr.TD_VIEWPOINT_Z, value);
                    break;
                case /*"viewpointorigin"*/19:
                    set2DFixed(shape, ShapeAttr.TD_ORIGIN_X, ShapeAttr.TD_ORIGIN_Y, value);
                    break;
                case /*"skewangle"*/20:
                    setFixedDegreesAttribute(shape, ShapeAttr.TD_SKEW_ANGLE, new VmlQuantity(value));
                    break;
                case /*"skewamt"*/21:
                    setPercentAttribute(shape, ShapeAttr.TD_SKEW_AMOUNT, value);
                    break;
                case /*"brightness"*/22:
                    setFixedAttribute(shape, ShapeAttr.TD_AMBIENT_INTENSITY, value);
                    break;
                case /*"lightposition"*/23:
                    set3DInt(shape, ShapeAttr.TD_KEY_X, ShapeAttr.TD_KEY_Y, ShapeAttr.TD_KEY_Z, value);
                    break;
                case /*"lightlevel"*/24:
                    setFixedAttribute(shape, ShapeAttr.TD_KEY_INTENSITY, value);
                    break;
                case /*"lightposition2"*/25:
                    set3DInt(shape, ShapeAttr.TD_FILL_X, ShapeAttr.TD_FILL_Y, ShapeAttr.TD_FILL_Z, value);
                    break;
                case /*"lightlevel2"*/26:
                    setFixedAttribute(shape, ShapeAttr.TD_FILL_INTENSITY, value);
                    break;
                case /*"colormode"*/27:
                    // Seems that MS Word does not use this attribute.
                    break;
                case /*"lockrotationcenter"*/28:
                    setBoolAttribute(shape, ShapeAttr.TD_CONSTRAIN_ROTATION, value);
                    break;
                case /*"autorotationcenter"*/29:
                    setBoolAttribute(shape, ShapeAttr.TD_ROTATION_CENTER_AUTO, value);
                    break;
                case /*"type"*/30:
                    if ("perspective".equals(value))
                        shape.setShapeAttrInternal(ShapeAttr.TD_PARALLEL, false);
                    break;
                case /*"lightharsh"*/31:
                    setBoolAttribute(shape, ShapeAttr.TD_KEY_HARSH, value);
                    break;
                case /*"lightharsh2"*/32:
                    // WORDSNET-19213 Added handling of invalid values.
                    setBoolAttributeForce(shape, ShapeAttr.TD_FILL_HARSH, value);
                    break;
                default:
                    reader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                    break;
            }
        }
    }

    private static void setRotationCenter(ShapeBase shape, String value)
    {
        VmlQuantity[] attrValues = vmlToQuantityArray(value);

        setFixedAttribute(shape, ShapeAttr.TD_ROTATION_CENTER_X, attrValues[0]);

        if (attrValues.length > 1)
            setFixedAttribute(shape, ShapeAttr.TD_ROTATION_CENTER_Y, attrValues[1]);

        if (attrValues.length > 2)
            setEmusAttribute(shape, ShapeAttr.TD_ROTATION_CENTER_Z, attrValues[2]);
    }

    /// <summary>
    /// Sets rotation x and y angles.
    /// </summary>
    static void setRotationAngles(ShapeBase shape, String value)
    {
        VmlQuantity[] attrValues = vmlToQuantityArray(value);

        setFixedDegreesAttribute(shape, ShapeAttr.TD_ROTATION_ANGLE_X, attrValues[0]);

        if (attrValues.length > 1)
            setFixedDegreesAttribute(shape, ShapeAttr.TD_ROTATION_ANGLE_Y, attrValues[1]);
    }

    private static void set3DInt(ShapeBase shape, int attrX, int attrY, int attrZ, String value)
    {
        String[] attrValues = msString.split(value, ',');

        setIntAttribute(shape, attrX, attrValues[0]);

        if (attrValues.length > 1)
            setIntAttribute(shape, attrY, attrValues[1]);

        if (attrValues.length > 2)
            setIntAttribute(shape, attrZ, attrValues[2]);
    }

    private static void set3DEmus(ShapeBase shape, int attrX, int attrY, int attrZ, String value)
    {
        VmlQuantity[] attrValues = vmlToQuantityArray(value);

        setEmusAttribute(shape, attrX, attrValues[0]);

        if (attrValues.length > 1)
            setEmusAttribute(shape, attrY, attrValues[1]);

        if (attrValues.length > 2)
            setEmusAttribute(shape, attrZ, attrValues[2]);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"ext",
		"specularity",
		"diffusity",
		"shininess",
		"edge",
		"foredepth",
		"backdepth",
		"plane",
		"color",
		"on",
		"metal",
		"lightface",
		"rotationangle",
		"orientation",
		"orientationangle",
		"rotationcenter",
		"render",
		"facet",
		"viewpoint",
		"viewpointorigin",
		"skewangle",
		"skewamt",
		"brightness",
		"lightposition",
		"lightlevel",
		"lightposition2",
		"lightlevel2",
		"colormode",
		"lockrotationcenter",
		"autorotationcenter",
		"type",
		"lightharsh",
		"lightharsh2"
	);

}

