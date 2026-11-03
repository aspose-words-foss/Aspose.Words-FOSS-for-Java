// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.MathUtil;


/// <summary>
/// Represents a custom shape.
/// </summary>
/// <remarks>
/// 20.1.2.2.33 sp (Shape)
/// This element specifies the existence of a single shape. A shape can either be a preset 
/// or a custom geometry, defined using the DrawingML framework. In addition to a geometry 
/// each shape can have both visual and non-visual properties attached. Text and corresponding 
/// styling information can also be attached to a shape. This shape is specified along with all 
/// other shapes within either the shape tree or group shape elements.
/// </remarks>
class DmlShape extends DmlShapeBase
{
    DmlShape(/*DmlNodeType*/int shapeType)
    {
        if (shapeType != DmlNodeType.SHAPE &&
            shapeType != DmlNodeType.WORDPROCESSING_SHAPE &&
            shapeType != DmlNodeType.CONNECTOR_SHAPE)
            throw new IllegalArgumentException("DmlShape cannot be created with the specified DmlNodeType");

        mShapeType = shapeType;
    }

    /*override*/ DmlNode deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        DmlShape lhs = (DmlShape)super.deepClone(isCloneChildren, cloningListener);

        if (mTextShape != null)
            lhs.mTextShape = mTextShape.deepClone();

        return lhs;
    }

    /*override*/ /*DmlNodeType*/int getDmlNodeType() { return mShapeType; }

    /*override*/ boolean shouldBuildUsingSqueezedSize()
    {
        // Originally this returned true only for 'trapezoid' preset geometry (see TestJira8595).
        // In WORDSNET-13127  the same problem occurs with 'homePlate'.
        // Further testing show that size must be recalculated almost for all geometries (see TestDmlShapeSqueezedSize).
        // So return true for all geometries here, except custom drawings (preset geometry is not set).
        // WORDSNET-15182 Also return true if shape is textbox, even if it has custom geometry.
        return StringUtil.hasChars(getGeometry().getPresetName()) || getDml().hasTextbox();
    }

    DmlTextShape getTextShape() { return mTextShape; }
    void setTextShape(DmlTextShape value) { mTextShape = value; }

    /// <summary>
    /// Shortcut to body properties of <see cref="TextShape"/>.
    /// If there is no <see cref="TextShape"/> returns new instance of <see cref="DmlTextBodyProperties"/>.
    /// </summary>
    DmlTextBodyProperties getTextBodyPr() { return (getTextShape() != null) ? getTextShape().getTextBody().getProperties() : new DmlTextBodyProperties(); }


    /*override*/ boolean isUpdateDimensionsFromRelative()
    {
        // Always update dimensions from relative if shape does not have textbox.
        if (!getDml().hasTextbox())
            return true;

        double angle = getTransform().getRotation().getValueInDegrees();
        angle = MathUtil.normalizeAngle(angle);

        // WORDSNET-12642 MS Word seems do not use updated size when textbox shape is 45, 90 or 270 degrees rotated.
        // Have no idea why these angles only, but do the same.
        boolean isExceptionAngle = (MathUtil.areEqual(angle, 45.0d) ||
                                 MathUtil.areEqual(angle, 90.0d) ||
                                 MathUtil.areEqual(angle, 270.0d));

        // When text is upright (i.e. id not rotated with shape) size is always updated from percent.
        return !isExceptionAngle || getTextBodyPr().isTextUpright();
    }

    /// <summary>
    /// Returns true if shape must grow horizontally to fit the text box content.
    /// </summary>
    boolean getAutoWidth() { return (getTextBodyPr().getTextWrappingType() == TextBoxWrapMode.NONE); }

    /// <summary>
    /// Returns true if shape must grow vertically to fit the text box content.
    /// </summary>
    boolean getAutoHeight() { return (getTextBodyPr().getAutoFitMode() instanceof DmlShapeAutoFitMode); }

    private DmlTextShape mTextShape;
    private /*final*/ /*DmlNodeType*/int mShapeType;
}

