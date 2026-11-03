// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2015 by Alexander Zhiltsov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msString;
import com.aspose.FormatterPal;
import com.aspose.ConvertUtilCore;
import com.aspose.MathUtil;
import com.aspose.Debug;
import com.aspose.ImageSizeCore;


/// <summary>
/// This class provides implementation for shape sizing and size validation logic 
/// to use in the <see cref="ShapeBase"/> and <see cref="Shape"/> classes.
/// </summary>
class ShapeSizeValidationHelper
{
    /// <summary>
    /// Validates that the shape width or height is > 0 and less than the maximum value in points for top 
    /// level shapes. Zero width or height is somewhat ugly, but it is valid (for horizontal line shape height 
    /// for example) so we allow it.
    /// </summary>
    /// <param name="shape">The shape to validate.</param>
    /// <param name="value">The value to validate.</param>
    /// <param name="isThrow">Whether to throw or return a maximum/minimum valid value.</param>
    /// <param name="dimension">The "width" or "height" word.</param>
    static double validateDimension(ShapeBase shape, double value, boolean isThrow, String dimension)
    {
        if (value < 0)
        {
            if (isThrow)
                throw new IllegalArgumentException(msString.format("Shape {0} cannot be less than 0.",
                        dimension) + "\r\nParameter name: " + "value");
            else
                return 0.0;
        }
        else if ((value > MAX_SHAPE_SIZE) && shape.isTopLevel())
        {
            // RK While the shape is not added to the model we cannot really validate properly, therefore 
            // we allow any values. It's okay, they will be validated by DocumentValidator on save.
            if (shape.getParentNode() == null)
                return value;

            if (isThrow)
            {
                throw new IllegalArgumentException(msString.format("Shape {0} cannot be greater than {1} points.", dimension,
                            FormatterPal.doubleToStr(MAX_SHAPE_SIZE)) + "\r\nParameter name: " + "value");
            }
            else
            {
                return MIN_SHAPE_SIZE;
            }
        }
        else
            return value;
    }

    /// <summary>
    /// Returns size of image scaled to either parent container or <see cref="MaxShapeSize"/> if needed.
    /// </summary>
    static SizeD getScaledImageSize(ShapeBase shape, double width, double height, SizeD imageSize, double rotationAng) throws Exception
    {
       imageSize = getShapeImageSize(shape, width, height, imageSize);

        boolean isScaleRequired = isScaleRequired(shape, width, height, imageSize);
        SizeD containerSize = (isScaleRequired)
            ? getContainerSize(shape, (Paragraph)shape.getAncestor(NodeType.PARAGRAPH))
            : new SizeD(MAX_SHAPE_SIZE, MAX_SHAPE_SIZE);

        imageSize = scaleToFitContainer(imageSize, containerSize, rotationAng);

        return imageSize;
    }

    /// <summary>
    /// Checks if specified width and height exceeds <see cref="MaxShapeSize"/>.
    /// </summary>
    private static boolean isExceedsMaxShapeSize(SizeD size)
    {
        return ((size.getWidth() >= MAX_SHAPE_SIZE) || (size.getHeight() >= MAX_SHAPE_SIZE));
    }

    /// <summary>
    /// Returns minimal size of page content. Considers page text columns.
    /// </summary>
    private static SizeD getPageMinContentSize(ShapeBase shape, Paragraph parentParagraph)
    {
        Section parentSection = (Section)parentParagraph.getAncestor(NodeType.SECTION);
        if (parentSection == null)
            return new SizeD();

        PageSetup ps = parentSection.getPageSetup();

        double leftOnPage = getLeftRelatedToPage(shape, ps);
        double topOnPage = getTopRelatedToPage(shape, ps);

        double tolerance = ConvertUtilCore.mmToPoint(1.0); // 1 mm
        boolean isOutsideMargins = ps.getContentLeft() - leftOnPage > tolerance || ps.getContentTop() - topOnPage > tolerance;

        // WORDSNET-12462 If a shape is placed inside page margins, scale it to be inside them,
        // if a shape is placed outside page margins, scale it to be inside page.
        double allowedWidth = isOutsideMargins ? ps.getPageWidth() - leftOnPage : ps.getContentRight() - leftOnPage;
        double allowedHeight = isOutsideMargins ? ps.getPageHeight() - topOnPage : ps.getContentBottom() - topOnPage;

        TextColumnCollectionInternal textColumns = (TextColumnCollectionInternal)parentSection.getSectPr().get(SectAttr.COLUMNS);
        if (textColumns != null)
            for (int i = 0; i < textColumns.getCount(); i++)
            {
                double colWidth = textColumns.get(i).getWidth();
                if (!MathUtil.isZero(colWidth))
                    allowedWidth = Math.min(allowedWidth, colWidth);
            }

        return new SizeD(allowedWidth, allowedHeight);
    }

    /// <summary>
    /// Returns value of the Left property that is related to the left bound of the page.
    /// </summary>
    private static double getLeftRelatedToPage(ShapeBase shape, PageSetup pageSetup)
    {
        // Same behavior as in Layout dialog of MS Word
        switch (shape.getRelativeHorizontalPosition())
        {
            case RelativeHorizontalPosition.MARGIN:
                return shape.getLeft() + pageSetup.getContentLeft();
            case RelativeHorizontalPosition.PAGE:
            case RelativeHorizontalPosition.LEFT_MARGIN:
            case RelativeHorizontalPosition.INSIDE_MARGIN:
                return shape.getLeft();
            case RelativeHorizontalPosition.COLUMN:
            case RelativeHorizontalPosition.CHARACTER:
                return shape.getLeft() + pageSetup.getContentLeft(); // unknown now
            case RelativeHorizontalPosition.RIGHT_MARGIN:
            case RelativeHorizontalPosition.OUTSIDE_MARGIN:
                return shape.getLeft() + pageSetup.getContentRight();
            default:
                Debug.assertTrue(false);
                return shape.getLeft() + pageSetup.getContentLeft();
        }
    }

    /// <summary>
    /// Returns value of the Top property that is related to the top bound of the page.
    /// </summary>
    private static double getTopRelatedToPage(ShapeBase shape, PageSetup pageSetup)
    {
        // Same behavior as in Layout dialog of MS Word
        switch (shape.getRelativeVerticalPosition())
        {
            case RelativeVerticalPosition.MARGIN:
                return shape.getTop() + pageSetup.getContentTop();
            case RelativeVerticalPosition.PAGE:
            case RelativeVerticalPosition.TOP_MARGIN:
            case RelativeVerticalPosition.INSIDE_MARGIN:
                return shape.getTop();
            case RelativeVerticalPosition.PARAGRAPH:
            case RelativeVerticalPosition.LINE:
                return shape.getTop() + pageSetup.getContentTop(); // unknown now
            case RelativeVerticalPosition.BOTTOM_MARGIN:
            case RelativeVerticalPosition.OUTSIDE_MARGIN:
                return shape.getTop() + pageSetup.getContentBottom();
            default:
                Debug.assertTrue(false);
                return shape.getTop() + pageSetup.getContentTop();
        }
    }

    /// <summary>
    /// Checks if we need to scale the shape. 
    /// Returns true if shape size is not set explicitly
    /// and it is not exceeds the maximum shape size or
    /// parent shape size or parent cell size or maximum page content size.
    /// </summary>
    private static boolean isScaleRequired(ShapeBase shape, double width, double height, SizeD imageSize)
    {
        // If width and height is more than zero, then size was requested explicitly
        // and we should use this explicitly specified size for image
        // and shouldn't scale it to fit in container.
        if ((width > 0) && (height > 0))
            return false;

        // WORDSNET-21405 Requires scaling if the container is a textbox.
        Shape parentShape = ms.as(shape.getAncestor(NodeType.SHAPE), Shape.class);
        if ((parentShape != null) && parentShape.isTextBox())
            return true;

        // Otherwise we should use size from the image data and check if scale is required for it.
        boolean scaleRequired = false;

        // Since to scale the shape we need to know the container size, we should make sure shape has a parent.
        if (shape.getParentNode() != null)
        {
            Cell parentCell = (Cell)shape.getAncestor(NodeType.CELL);
            if (isExceedsMaxShapeSize(imageSize))
            {
                // WORDSNET-20267 If table width is Auto then there is no container width to scale.
                if ((parentCell == null) || !parentCell.getParentTable().getAllowAutoFit())
                    scaleRequired = true;
            }
            else
            {
                // andrnosk: WORDSNET-10174 MS Word scales large images to parent cells.
                // We do the same but only when PreferredWidth is set.
                if (parentCell != null)
                {
                    if (!parentCell.getParentTable().getAllowAutoFit())
                    {
                        PreferredWidth preferredCellWidth = parentCell.getCellFormat().getPreferredWidth();
                        if (preferredCellWidth.isFixed() && (imageSize.getWidth() > preferredCellWidth.getValue()))
                            scaleRequired = true;
                    }
                }
                else
                {
                    // WORDSNET-10718 MS Word scales large images to fit on page.
                    // It scales to the narrowest column regardless in which column this shape resides. 
                    // WORDSNET-18016 Shape might be inside SDT.
                    SizeD pageMinContentSize = getPageMinContentSize(shape, (Paragraph)shape.getAncestor(NodeType.PARAGRAPH));
                    if (imageSize.isExceeds(pageMinContentSize) && !pageMinContentSize.isEmpty())
                        scaleRequired = true;
                }
            }
        }

        return scaleRequired;
    }

    /// <summary>
    /// Returns size of the shape to occupy entire container (cell, textbox or page) size. 
    /// Keeps width/height ratio. 
    /// </summary>
    /// <param name="imageSize">Size of the image inside shape, it can be empty (0,0) for chart which does not have image data.</param>
    /// <param name="containerSize">Size of the container.</param>
    /// <param name="rotationAng">Rotation angle.</param>
    /// <returns></returns>
    static SizeD scaleToFitContainer(SizeD imageSize, SizeD containerSize, double rotationAng)
    {
        // Check container size is valid.
        if (containerSize.isEmpty())
            return imageSize;

        // If imageSize is empty - it is Chart, lets take default chart size and scale it to fit container.
        if (imageSize.isEmpty())
            imageSize = new SizeD(DEFAULT_CHART_WIDTH, DEFAULT_CHART_HEIGHT);
        // If image dimensions do not exceed container dimensions, then nothing to scale.
        else if ((imageSize.getWidth() <= containerSize.getWidth()) && (imageSize.getHeight() <= containerSize.getHeight()))
            return imageSize;

        SizeD scaledSize = new SizeD();

        int angle = MathUtil.doubleToInt(MathUtil.normalizeAngle(rotationAng));
        double containerHeigth = containerSize.getHeight();
        double containerWidth = containerSize.getWidth();

        if ((angle == 90) || (angle == 270))
        {
            containerHeigth = containerSize.getWidth();
            containerWidth = containerSize.getHeight();
        }

        double ratioWidth = imageSize.getWidth() / containerWidth;
        double ratioHeight = imageSize.getHeight() / containerHeigth;

        if (ratioWidth > ratioHeight)
        {
            scaledSize.setHeight((containerHeigth * (ratioHeight / ratioWidth)));
            scaledSize.setWidth(containerWidth);
        }
        else
        {
            scaledSize.setWidth((containerWidth * (ratioWidth / ratioHeight)));
            scaledSize.setHeight(containerHeigth);
        }

        return scaledSize;
    }

    /// <summary>
    /// Returns container size for the specified shape.
    /// </summary>
    static SizeD getContainerSize(ShapeBase shape, Paragraph parentParagraph)
    {
        SizeD targetSize = new SizeD();

        // MS Word scales large images to parent shape or page size.
        // So first try to get parent shape (TextBox).
        Shape parentShape = (Shape)parentParagraph.getAncestor(NodeType.SHAPE);
        Cell parentCell = (Cell)parentParagraph.getAncestor(NodeType.CELL);
        if (parentShape != null)
        {
            targetSize.setWidth(parentShape.getWidth());
            targetSize.setHeight(parentShape.getHeight());
        }
        else if (parentCell != null)
        {
            // andrnosk: WORDSNET-10174 MS Word scales large images to parent cells.
            // We do the same but only when PreferredWidth is set. See the IsScaleRequired() method.
            CellFormat cellFormat = parentCell.getCellFormat();
            if (cellFormat.getPreferredWidth().isFixed())
                targetSize.setWidth(cellFormat.getPreferredWidth().getValue() - cellFormat.getLeftPadding() - cellFormat.getRightPadding());
            else
                targetSize.setWidth(parentCell.getCellFormat().getWidth());

            targetSize.setHeight(MAX_SHAPE_SIZE);
        }
        else
        {
            // WORDSNET-10718 MS Word scales to the narrowest column regardless in which column this shape resides. 
            SizeD pageMinContentSize = getPageMinContentSize(shape, parentParagraph);
            if (!pageMinContentSize.isEmpty())
                targetSize = pageMinContentSize;
        }

        return targetSize;
    }

    /// <summary>
    /// Returns size from the specified width and height,
    /// replacing it with dimensions from the shape's image, if it is less than zero or equals zero.
    /// </summary>
    private static SizeD getShapeImageSize(ShapeBase shape, double width, double height, SizeD imageSize) throws Exception
    {
        if ((width > 0) && (height > 0))
            return new SizeD(width, height);

        if (imageSize != null)
            return imageSize;

        ImageSizeCore imageSizeCore = shape.getShapeImageSize();
        double imageWidth = (width <= 0) ? imageSizeCore.getWidthPoints() : width;
        double imageHeight = (height <= 0) ? imageSizeCore.getHeightPoints() : height;

        return new SizeD(imageWidth, imageHeight);
    }

    /// <summary>
    /// Word cannot accept shape sizes more than this value in points. But it is not easy to validate here.
    /// </summary>
    static final double MAX_SHAPE_SIZE = ConvertUtilCore.MAX_SIZE_POINT;

    /// <summary>
    /// Word set this value to shape dimensional in case they are greater than <see cref="MaxShapeSize" />.
    /// </summary>
    static final double MIN_SHAPE_SIZE = ConvertUtilCore.MIN_SIZE_POINT;

    private static final double DEFAULT_CHART_WIDTH = 432.0;
    private static final double DEFAULT_CHART_HEIGHT = 252.0;
}

