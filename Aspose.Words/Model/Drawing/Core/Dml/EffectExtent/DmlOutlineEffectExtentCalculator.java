// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/03/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.Debug;
import com.aspose.ms.System.msMath;


/// <summary>
/// Calculates effect extent values which are added with the outline of the shape.
/// All code in this class is experimental.
/// </summary>
class DmlOutlineEffectExtentCalculator
{
    /// <summary>
    /// Updates effect extents of the specified shape according to the change of outline weight. 
    /// Takes in attention current values of effect extents.
    /// </summary>
    static void updateWeightRelatedEffectExtentPart(ShapeBase shape, double curWeight, double newWeight)
    {
        if ((shape == null) || MathUtil.areEqual(curWeight, newWeight))
            return;

        Debug.assertTrue(shape.getMarkupLanguage() == ShapeMarkupLanguage.DML);
        // Currently implemented only calculation of effect extent for shapes with "image" shape type.
        if (shape.isImage())
            DmlPictureOutlineEffectExtentCalculator.updateWeightRelatedEffectExtentPartImage(shape, curWeight, newWeight);
    }

    /// <summary>
    /// Calculates effect extent values which are added with the outline of the picture.
    /// </summary>
    private static class DmlPictureOutlineEffectExtentCalculator
    {
        /// <summary>
        /// Updates effect extents of the passed shape according to change of the image outline weight.
        /// </summary>
        static void updateWeightRelatedEffectExtentPartImage(ShapeBase shape, double curWeight, double newWeight)
        {
            Debug.assertTrue((shape != null) && shape.isImage());

            // This algorithm only for images.
            // Calculate effect extent values for current and new outline weight.
            int[] newWeightExtentValues = calculateEffectExtentsRelatedToWeightPart(newWeight);
            int[] curWeigthExtentValues = calculateEffectExtentsRelatedToWeightPart(curWeight);

            for (int key = ShapeAttr.DML_EFFECT_EXTENT_LEFT; key <= ShapeAttr.DML_EFFECT_EXTENT_BOTTOM; ++key)
            {
                int extent = (/*int*/Integer)shape.fetchShapeAttrInternal(key);
                int extentIndex = key - ShapeAttr.DML_EFFECT_EXTENT_LEFT;
                int weightExtentDelta = newWeightExtentValues[extentIndex] - curWeigthExtentValues[extentIndex];

                int newExtentVal = extent + weightExtentDelta;
                newExtentVal = (newExtentVal < 0) && (newWeightExtentValues[extentIndex] > 0) ?
                    newWeightExtentValues[extentIndex] : newExtentVal;

                // Avoid negative extent values at any case.
                shape.setShapeAttrInternal(key, (newExtentVal < 0) ? 0 : newExtentVal);
            }
        }

        /// <summary>
        /// Calculates amount of effect extent which is added with the outline weight of image.
        /// </summary>
        /// <param name="weight">Weight in points.</param>
        private static int[] calculateEffectExtentsRelatedToWeightPart(double weight)
        {
            if (MathUtil.isZero(weight))
                return G_ZERO_WEIGHT_EFFECT_EXTENTS;

            final double MAX_WEIGHT_WITH_BASE_EXTENT = 2.0;

            if ((weight < MAX_WEIGHT_WITH_BASE_EXTENT) || MathUtil.areEqual(weight, MAX_WEIGHT_WITH_BASE_EXTENT))
                return G_BASE_WEIGHT_EFFECT_EXTENTS;

            final double WEIGHT_INCREMENT_DELTA = 1.5;
            double weightWithAdditionalExtent = weight - MAX_WEIGHT_WITH_BASE_EXTENT;

            int extentIncrementCount = (int)msMath.truncate(weightWithAdditionalExtent / WEIGHT_INCREMENT_DELTA);
            extentIncrementCount += MathUtil.isZero(weightWithAdditionalExtent % WEIGHT_INCREMENT_DELTA) ? 0 : 1;

            final int EXTENT_INCREMENT_DELTA = 19050;
            int additionalExtentValue = extentIncrementCount * EXTENT_INCREMENT_DELTA;

            return new int[] {
            G_BASE_WEIGHT_EFFECT_EXTENTS[0] + additionalExtentValue,
            G_BASE_WEIGHT_EFFECT_EXTENTS[1] + additionalExtentValue,
            G_BASE_WEIGHT_EFFECT_EXTENTS[2] + additionalExtentValue,
            G_BASE_WEIGHT_EFFECT_EXTENTS[3] + additionalExtentValue };
        }

        private static /*final*/ int[] G_BASE_WEIGHT_EFFECT_EXTENTS = new int[] { 19050, 19050, 28575, 19050 };
        private static /*final*/ int[] G_ZERO_WEIGHT_EFFECT_EXTENTS = new int[] { 0, 0, 0, 0 };
    }  
}

