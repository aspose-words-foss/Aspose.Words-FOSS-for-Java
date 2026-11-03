// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/10/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Helper class for Dml/Vml attributes comparing.
/// </summary>
class DmlVmlTestUtil
{
    DmlVmlTestUtil(Shape shape, Shape drawingMl)
    {
        mShape = shape;
        mDrawingMl = drawingMl;
    }

    void check(int key)
    {
        Object vmlVal = mShape.fetchShapeAttrInternal(key);
        Object dmlVal = mDrawingMl.fetchShapeAttrInternal(key);

        if (key == ShapeAttr.TRANSFORM_ROTATION)
        {
            // Before compare Dml and Vml angle we have to normalize them.
            msAssert.areEqual(normalizeAngle((/*int*/Integer)vmlVal), 1, normalizeAngle((/*int*/Integer)dmlVal), messageFiledKey(key));
            return;
        }

        if ((key == ShapeAttr.FILL_SHADE_COLORS) && (mShape.getShapePr().containsKey(ShapeAttr.FILL_SHADE_COLORS)))
        {
            // Currently we check only Length of the array, because the color and position is little bit different in Dml.
            msAssert.areEqual(((GradientColor[])vmlVal).length, ((GradientColor[])dmlVal).length, messageFiledKey(key));
            return;
        }

        msAssert.areEqual(vmlVal, dmlVal, messageFiledKey(key));
    }

    ShapePr getVmlShapePr() { return mShape.getShapePr(); }

    ShapePr getDmlShapePr() { return mDrawingMl.getShapePr(); }

    private static String messageFiledKey(int key)
    {
        return msString.format("Filed ShapeAttr key = {0}", key);
    }

    /// <summary>
    /// Normalizes specified angle so it becomes greater or equal to zero and less then 360 degrees.
    /// In Vml one degree equals 65536 (0x10000).
    /// </summary>
    private double normalizeAngle(int value)
    {
        final int ANGLE_360 = 360 * 0x10000;

        while (value < 0)
            value += ANGLE_360;

        if (value > ANGLE_360)
            value %= ANGLE_360;

        return value;
    }

    private /*final*/ Shape mShape;
    private /*final*/ Shape mDrawingMl;
}

