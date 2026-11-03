// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/04/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.StringUtil;
import com.aspose.CantCreateBitmapException;
import com.aspose.ImageSizeCore;
import com.aspose.ImageUtil;


class FieldIncludePictureUpdater
{
    static FieldUpdateAction update(Field field) throws Exception
    {
        FieldIncludePictureUpdater updater = new FieldIncludePictureUpdater(field, (IFieldIncludePictureCode)field);
        return updater.update();
    }

    private FieldIncludePictureUpdater(Field field, IFieldIncludePictureCode fieldCode)
    {
        mField = field;
        mFieldCode = fieldCode;
    }

    private FieldUpdateAction update() throws Exception
    {
        if (!StringUtil.hasChars(mFieldCode.getSourceFullName()))
            return new FieldUpdateActionInsertErrorMessage(mField, INVALID_FILE_NAME_ERROR_MESSAGE);

        Shape shape = new Shape(mField.getDocument(), ShapeType.IMAGE);
        setDefaultImageSize(shape);
        shape.setWrapType(WrapType.INLINE);
        shape.getImageData().setSourceFullName(mFieldCode.getSourceFullName());

        // And try to load it to know image dimensions.
        byte[] imageBytes = shape.getImageData().loadImageBytes();
        if (imageBytes != null)
        {
            Size imageSize = getImageSize(imageBytes);
            shape.setWidthSafe(imageSize.getWidth());
            shape.setHeightSafe(imageSize.getHeight());

            if (!mFieldCode.isLinked())
            {
                try
                {
                    shape.getImageData().setImageBytes(imageBytes);
                }
                catch (CantCreateBitmapException e)
                {
                    if (mField.hasResult())
                        return new FieldUpdateActionDoNothing(mField);

                    setDefaultImageSize(shape);
                }
            }
        }
        else if (!mFieldCode.isLinked())
        {
            return new FieldUpdateActionInsertErrorMessage(mField, INVALID_FILE_NAME_ERROR_MESSAGE);
        }

        return new FieldUpdateActionApplyResult(mField, new NodeRangeFieldResult(new NodeRange(shape, shape)));
    }

    private static void setDefaultImageSize(Shape shape)
    {
        // Word shows empty 3in square picture if the linked image is not found.
        final double DEFAULT_IMAGE_DIMENSION = 72 * 3;

        shape.setWidthSafe(DEFAULT_IMAGE_DIMENSION);
        shape.setHeightSafe(DEFAULT_IMAGE_DIMENSION);
    }

    private Size getImageSize(byte[] imageBytes) throws Exception
    {
        ImageSizeCore sizeCore = ImageUtil.getImageSize(imageBytes);
        Size size = new Size(sizeCore.getWidthPoints(), sizeCore.getHeightPoints());
        return adjustImageSize(size);
    }

    private Size adjustImageSize(Size imageSize)
    {
        imageSize = adjustImageSizeToShape(imageSize);
        imageSize = adjustImageSizeToFrame(imageSize);
        imageSize = adjustImageSizeToTableCell(imageSize);
        imageSize = adjustImageSizeToPage(imageSize);

        return imageSize;
    }

    private Size adjustImageSizeToShape(Size imageSize)
    {
        Shape shape = ms.as(mField.getStart().getParentParagraph().getParentNode(), Shape.class);
        if (shape == null)
            return imageSize;

        return fitWidthAndHeight(imageSize, shape.getWidth(), shape.getHeight());
    }

    private Size adjustImageSizeToFrame(Size imageSize)
    {
        Paragraph paragraph = mField.getStart().getParentParagraph();
        ParaPr paraPr = paragraph.getParaPr();

        if (!paraPr.hasFrameAttributes())
            return imageSize;

        return paraPr.hasFrameWidth()
            ? adjustImageSizeToFrameWidth(paragraph.getParagraphFormat(), imageSize)
            : adjustImageSizeToFrameHeight(paragraph.getParagraphFormat(), imageSize);
    }

    private static Size adjustImageSizeToFrameWidth(ParagraphFormat format, Size imageSize)
    {
        switch (format.getFrameHeightRule())
        {
            case HeightRule.EXACTLY:
                return fitToFrameSize(format, imageSize);
            case HeightRule.AT_LEAST:
            case HeightRule.AUTO:
                return fitToFrameWidth(format, imageSize);
            default:
                throw new IllegalArgumentException();
        }
    }

    private static Size adjustImageSizeToFrameHeight(ParagraphFormat format, Size imageSize)
    {
        // MS Word behaviour is more difficult. Picture size increases from very small
        // up to source image size or frame height (depend on frame height rule) each time field is updated.
        switch (format.getFrameHeightRule())
        {
            case HeightRule.EXACTLY:
                return fitToFrameHeight(format, imageSize);
            case HeightRule.AT_LEAST:
            case HeightRule.AUTO:
                return imageSize;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static Size fitToFrameSize(ParagraphFormat paraPr, Size imageSize)
    {
        return fitWidthAndHeight(imageSize, paraPr.getFrameWidth(), paraPr.getFrameHeight());
    }

    private static Size fitToFrameWidth(ParagraphFormat paraPr, Size imageSize)
    {
        double frameWidth = paraPr.getFrameWidth();
        return fitWidth(imageSize, frameWidth);
    }

    private static Size fitToFrameHeight(ParagraphFormat paraPr, Size imageSize)
    {
        double frameHeight = paraPr.getFrameHeight();
        return fitHeight(imageSize, frameHeight);
    }

    private Size adjustImageSizeToTableCell(Size imageSize)
    {
        Cell cell = mField.getStart().getParentParagraph().getParentCell();
        if (cell == null)
            return imageSize;

        Table table = cell.getParentRow().getParentTable();
        if (table.getAllowAutoFit())
            return imageSize;

        CellFormat cellFormat = cell.getCellFormat();
        double width = cellFormat.getWidth() - cellFormat.getLeftPadding() - cellFormat.getRightPadding();
        return fitWidth(imageSize, width);
    }

    private Size adjustImageSizeToPage(Size imageSize)
    {
        Section section = (Section)mField.getStart().getAncestor(NodeType.SECTION);
        return fitWidthAndHeight(imageSize, section.getPageSetup().getContentWidth(), section.getPageSetup().getContentHeight());
    }

    private static Size fitHeight(Size imageSize, double height)
    {
        if (imageSize.getHeight() < height)
            return imageSize;

        double scale = height / imageSize.getHeight();

        return imageSize.scale(scale);
    }

    private static Size fitWidth(Size imageSize, double width)
    {
        if (imageSize.getWidth() < width)
            return imageSize;

        double scale = width / imageSize.getWidth();

        return imageSize.scale(scale);
    }

    private static Size fitWidthAndHeight(Size imageSize, double width, double height)
    {
        // MS Word behaviour is more difficult. It calculates picture size in same way, but with small deviations (+/- 1 pt).
        if (imageSize.getWidth() < width && imageSize.getHeight() < height)
            return imageSize;

        double scale = Math.min(width / imageSize.getWidth(), height / imageSize.getHeight());

        return imageSize.scale(scale);
    }

    private static final String INVALID_FILE_NAME_ERROR_MESSAGE = "Error! Filename not specified.";

    private /*final*/ Field mField;
    private /*final*/ IFieldIncludePictureCode mFieldCode;

    private static class Size
    {
        public Size(double width, double height)
        {
            mWidth = width;
            mHeight = height;
        }

        public double getWidth() { return mWidth; };

        private  double mWidth;

        public double getHeight() { return mHeight; };

        private  double mHeight;

        public Size scale(double scale)
        {
            return new Size(getWidth() * scale, getHeight() * scale);
        }
    }
}

