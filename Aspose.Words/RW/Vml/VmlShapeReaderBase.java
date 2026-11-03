// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/11/2015 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;


class VmlShapeReaderBase
{
    /// <summary>
    /// Sets fixed attribute only if the specified string has a valid value.
    /// </summary>
    static void setFixedAttribute(ShapeBase shape, int shapeAttr, String value)
    {
        setFixedAttribute(shape, shapeAttr, new VmlQuantity(value));
    }

    /// <summary>
    /// Sets fixed attribute only if the specified quantity qualifies as a valid fixed value.
    /// </summary>
    static void setFixedAttribute(ShapeBase shape, int shapeAttr, VmlQuantity quantity)
    {
        if (quantity.isFixed())
            shape.setShapeAttrInternal(shapeAttr, quantity.toFixed());
    }

    /// <summary>
    /// Sets fixed degrees attribute.
    /// </summary>
    /// <remarks>
    /// If units are 'fd' or 'f' then the attr value set as is
    /// If units are not specified then the attr value set multiplied by 0x10000.
    /// If units are not recognized or no valid numeric value is found then the attr value set as 0.
    /// </remarks>
    static void setFixedDegreesAttribute(ShapeBase shape, int shapeAttr, VmlQuantity quantity)
    {
        if (quantity.isDegrees())
            shape.setShapeAttrInternal(shapeAttr, quantity.degreesToFixed());
    }

    /// <summary>
    /// Sets percent attribute only if the specified quantity qualifies as a valid percent value.
    /// </summary>
    static void setPercentAttribute(ShapeBase shape, int shapeAttr, String value)
    {
        VmlQuantity q = new VmlQuantity(value);

        if (q.isPercent())
            shape.setShapeAttrInternal(shapeAttr, q.percentToInt());
    }

    static void set2DFixed(ShapeBase shape, int attrX, int attrY, String value)
    {
        VmlQuantity[] attrValues = vmlToQuantityArray(value);

        setFixedAttribute(shape, attrX, attrValues[0]);

        if (attrValues.length > 1)
            setFixedAttribute(shape, attrY, attrValues[1]);
    }

    /// <summary>
    /// Sets boolean attribute only if the specified string has "t", "true" or "f", "false" value.
    /// </summary>
    static void setBoolAttribute(ShapeBase shape, int shapeAttr, String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            // andrnosk: WORDSNET-8346 (conversion part) According to specification DOCX-ECMA 376 (part4) 6.1.2.14.
            // The possible values for 'strokeok' attribute are defined by the ST_TrueFalse simple type,
            // and for this type: 'true', 'false', 'f', 't' are possible values.
            // WORDSNET-20845 Added resilience for 1 as true and 0 as false.
            case /*"t"*/0:
            case /*"true"*/1:
            case /*"1"*/2:
                shape.setShapeAttrInternal(shapeAttr, true);
                break;
            case /*"f"*/3:
            case /*"false"*/4:
            case /*"0"*/5:
                shape.setShapeAttrInternal(shapeAttr, false);
                break;
            default:
                throw new IllegalStateException("Unexpected ST_TrueFalse value.");
        }
    }

    /// <summary>
    /// Sets the boolean attribute even if the value doesn't match the correct values.
    /// </summary>
    static void setBoolAttributeForce(ShapeBase shape, int shapeAttr, String value)
    {
        setBoolAttribute(shape, shapeAttr, value, !"0".equals(value));
    }

    /// <summary>
    /// Sets boolean attribute only if the specified string has "t", "true" or "f", "false" value
    /// in all other invalid cases set defaultValue.
    /// </summary>
    static void setBoolAttribute(ShapeBase shape, int shapeAttr, String value, boolean defaultValue)
    {
        try
        {
            setBoolAttribute(shape, shapeAttr, value);
        }
        catch (IllegalStateException e)
        {
            shape.setShapeAttrInternal(shapeAttr, defaultValue);
        }
    }

    /// <summary>
    /// Sets boolean attribute only if the specified string has one of the specified true or false string values.
    /// </summary>
    static void setBoolAttribute(ShapeBase shape, int shapeAttr, String trueValue, String falseValue, String value)
    {
        if (msString.equals(value, trueValue))
            shape.setShapeAttrInternal(shapeAttr, true);
        else if (msString.equals(value, falseValue))
            shape.setShapeAttrInternal(shapeAttr, false);
    }

    /// <summary>
    /// Sets EMUs attribute only if the specified string has a valid value.
    /// </summary>
    static void setEmusAttribute(ShapeBase shape, int shapeAttr, String value)
    {
        setEmusAttribute(shape, shapeAttr, new VmlQuantity(value));
    }

    /// <summary>
    /// Sets EMUs attribute only if the specified string has a valid value.
    /// </summary>
    static void setEmusAttribute(ShapeBase shape, int shapeAttr, VmlQuantity quantity)
    {
        if (quantity.isDistance())
            shape.setShapeAttrInternal(shapeAttr, quantity.distanceToEmus());
    }

    /// <summary>
    /// Sets int attribute if the string is not empty.
    /// </summary>
    static void setIntAttribute(ShapeBase shape, int shapeAttr, String value)
    {
        if (StringUtil.hasChars(value))
            shape.setShapeAttrInternal(shapeAttr, FormatterPal.parseInt(value));
    }

    static VmlQuantity[] vmlToQuantityArray(String value)
    {
        return vmlToQuantityArray(value, "");
    }

    /// <summary>
    /// Parses comma-delimited string of quantities (floating values with dimension units).
    /// </summary>
    static VmlQuantity[] vmlToQuantityArray(String value, String defaultUnits)
    {
        String[] strings = msString.split(value, ',');
        VmlQuantity[] quantities = new VmlQuantity[strings.length];

        for (int i = 0; i < strings.length; i++)
            quantities[i] = new VmlQuantity(strings[i], defaultUnits);

        return quantities;
    }

    static final String WARNING_MESSAGE_FORMAT = "Import of element '{0}' is not supported upon VML import by Aspose.Words.";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"t",
		"true",
		"1",
		"f",
		"false",
		"0"
	);

}

