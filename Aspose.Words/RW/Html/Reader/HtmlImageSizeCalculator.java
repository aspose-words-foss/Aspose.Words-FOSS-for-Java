// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/09/2023 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.MathUtil;
import com.aspose.ArrayUtil;
import com.aspose.Debug;


/// <summary>
/// Calculates image size by CSS properties, intrinsic and parent container size.
/// </summary>
class HtmlImageSizeCalculator
{
    /// <summary>
    /// Ctor.
    /// </summary>
    HtmlImageSizeCalculator(
        SizeD imageSize,
        SizeD containerSize,
        CssDeclarationCollection declarations,
        boolean preserveAspectRatio)
    {
        mImageSize = (imageSize == null)
            ? new SizeD(-1, -1)
            : imageSize;
        mContainerSize = containerSize;
        mDeclarations = declarations;
        mPreserveAspectRatio = preserveAspectRatio;

        mWidth = readWidth(declarations.get(WIDTH_PROPERTY), -Double.MAX_VALUE);
        mMinWidth = readWidth(declarations.get(MIN_WIDTH_PROPERTY), -Double.MAX_VALUE);
        mMaxWidth = readWidth(declarations.get(MAX_WIDTH_PROPERTY), Double.MAX_VALUE);

        mHeight = readHeight(declarations.get(HEIGHT_PROPERTY), -Double.MAX_VALUE);
        mMinHeight = readHeight(declarations.get(MIN_HEIGHT_PROPERTY), -Double.MAX_VALUE);
        mMaxHeight = readHeight(declarations.get(MAX_HEIGHT_PROPERTY), Double.MAX_VALUE);
    }

    HtmlImageSizeCalculator(
        SizeD imageSize,
        SizeD containerSize,
        CssDeclarationCollection declarations)
    {
    	this(
              imageSize,
              containerSize,
              declarations,
              true);
	
        // Empty constructor.
    }

    SizeD calculate(/*CssDisplayType*/int parentDisplayType)
    {
        double width = mImageSize.getWidth();
        double height = mImageSize.getHeight();

        double ratio = width / height;

        if (ratio <= 0)
            return null;

        if (hasProperty(WIDTH_PROPERTY) && hasProperty(HEIGHT_PROPERTY))
        {
            width = mWidth;
            height = mHeight;
            ratio = width / height;
        }
        else if (hasProperty(WIDTH_PROPERTY))
        {
            width = mWidth;
            height = mPreserveAspectRatio ? mWidth / ratio : mImageSize.getHeight();
        }
        else if (hasProperty(HEIGHT_PROPERTY))
        {
            height = mHeight;
            width = mPreserveAspectRatio ? mHeight * ratio : mImageSize.getWidth();
        }

        if (ratio <= 0)
            return null;

        if (isZeroWidthContainer())
        {
            return new SizeD(width, height);
        }

        if (isAffectedOnly(width, height, MAX_WIDTH_PROPERTY))
        {
            return new SizeD(mMaxWidth, Math.max(mMaxWidth / ratio, mMinHeight));
        }

        // WORDSNET-26855 If a container containing an image has the `display: table` property,
        // then it's not the image that will adopt the size of the container,
        // but the container will adopt the size of the image.
        // The `min-width` property does not work in this case.
        if ((parentDisplayType != CssDisplayType.TABLE) &&
            isAffectedOnly(width, height, MIN_WIDTH_PROPERTY))
        {
            return new SizeD(mMinWidth, Math.min(mMinWidth / ratio, mMaxHeight));
        }

        if (isAffectedOnly(width, height, MAX_HEIGHT_PROPERTY))
        {
            return new SizeD(Math.max(mMaxHeight * ratio, mMinWidth), mMaxHeight);
        }

        if (isAffectedOnly(width, height, MIN_HEIGHT_PROPERTY))
        {
            return new SizeD(Math.min(mMinHeight * ratio, mMaxWidth), mMinHeight);
        }

        if (isAffectedOnly(width, height, MAX_WIDTH_PROPERTY, MAX_HEIGHT_PROPERTY) &&
            ((mMaxWidth / width) <= (mMaxHeight / height)))
        {
            return new SizeD(mMaxWidth, Math.max(mMinHeight, mMaxWidth / ratio));
        }

        if (isAffectedOnly(width, height, MAX_WIDTH_PROPERTY, MAX_HEIGHT_PROPERTY) &&
            ((mMaxWidth / width) > (mMaxHeight / height)))
        {
            return new SizeD(Math.max(mMinWidth, mMaxHeight * ratio), mMaxHeight);
        }

        if (isAffectedOnly(width, height, MIN_WIDTH_PROPERTY, MIN_HEIGHT_PROPERTY) &&
            ((mMinWidth / width) <= (mMinHeight / height)))
        {
            return new SizeD(Math.min(mMaxWidth, mMinHeight * ratio), mMinHeight);
        }

        if (isAffectedOnly(width, height, MIN_WIDTH_PROPERTY, MIN_HEIGHT_PROPERTY) &&
            ((mMinWidth / width) > (mMinHeight / height)))
        {
            return new SizeD(mMinWidth, Math.min(mMaxHeight, mMinWidth / ratio));
        }

        if (isAffectedOnly(width, height, MIN_WIDTH_PROPERTY, MAX_HEIGHT_PROPERTY))
        {
            return new SizeD(mMinWidth, mMaxHeight);
        }

        if (isAffectedOnly(width, height, MAX_WIDTH_PROPERTY, MIN_HEIGHT_PROPERTY))
        {
            return new SizeD(mMaxWidth, mMinHeight);
        }

        if (hasSizeProperties())
        {
            return new SizeD(width, height);
        }

        return null;
    }

    boolean hasAnyCssSizeProperties()
    {
        return hasSizeProperties() ||
               hasProperty(MIN_WIDTH_PROPERTY) || hasProperty(MIN_HEIGHT_PROPERTY) ||
               hasProperty(MAX_WIDTH_PROPERTY) || hasProperty(MAX_HEIGHT_PROPERTY);
    }

    double getCssWidth()
    {
        double width = getsCssWidth();
        return MathUtil.isMinValue(width)
            ? -1
            : width;
    }

    double getCssHeight()
    {
        double height = getsCssHeight();
        return MathUtil.isMinValue(height)
            ? -1
            : height;
    }

    private double readWidth(CssDeclaration cssDeclaration, double defaultValue)
    {
        // WORDSNET-28510 When an image has 'min-width:none' or 'max-width:none', it should not be resized.
        if (isNullOrNone(cssDeclaration))
            return defaultValue;

        return readValue(
            cssDeclaration,
            (mContainerSize != null) ? mContainerSize.getWidth() : 0.0);
    }

    private double readHeight(CssDeclaration cssDeclaration, double defaultValue)
    {
        // WORDSNET-28510 When an image has 'min-height:none' or 'max-height:none', it should not be resized.
        if (isNullOrNone(cssDeclaration))
            return defaultValue;

        return readValue(
            cssDeclaration,
            (mContainerSize != null) ? mContainerSize.getHeight() : 0.0);
    }

    private static boolean isNullOrNone(CssDeclaration cssDeclaration)
    {
        return (cssDeclaration == null) ||
               cssDeclaration.getValue().equals(CssValue.None);
    }

    /// <summary>
    /// Checks that only the specified max/min properties affect the image and other max/min properties don't.
    /// </summary>
    private boolean isAffectedOnly(double width, double height, /*params*/ String... propertiesThatAffect)
    {
        String[] allProperties = new String[] { MAX_WIDTH_PROPERTY, MAX_HEIGHT_PROPERTY, MIN_WIDTH_PROPERTY, MIN_HEIGHT_PROPERTY };
        for (String property : allProperties)
        {
            boolean affects = isAffected(property, width, height);
            boolean shouldAffect = ArrayUtil.contains(propertiesThatAffect, property);

            if (affects != shouldAffect)
            {
                return false;
            }
        }

        return true;
    }

    private boolean isAffected(String property, double width, double height)
    {
        if (mDeclarations.get(property) == null)
            return false;

        switch (gStringSwitchMap.of(property))
        {
            case /*MAX_WIDTH_PROPERTY*/0:
                return width > mMaxWidth;
            case /*MAX_HEIGHT_PROPERTY*/1:
                return height > mMaxHeight;
            case /*MIN_WIDTH_PROPERTY*/2:
                return width < mMinWidth;
            case /*MIN_HEIGHT_PROPERTY*/3:
                return height < mMinHeight;
            default:
                Debug.assertTrue(false);
                return false;
        }
    }

    private static double readValue(CssDeclaration cssDeclaration, double containerSize)
    {
        if (cssDeclaration.hasSingleValueOfType(CssValueType.PERCENTAGE))
        {
            return containerSize / 100.0 * cssDeclaration.getValue().getFirstValue().getDoubleValue();
        }

        return CssUtil.lengthToPoint(cssDeclaration.getValue());
    }

    private double getsCssWidth()
    {
        double width = (mWidth > 0)
            ? mWidth
            : mImageSize.getWidth();

        if ((mMinWidth > 0) && (width < mMinWidth))
            return mMinWidth;
        if ((mMaxWidth > 0) && (width > mMaxWidth))
            return mMaxWidth;

        return mWidth;
    }

    private double getsCssHeight()
    {
        double height = (mHeight > 0)
            ? mHeight
            : mImageSize.getHeight();

        if ((mMinHeight > 0) && (height < mMinHeight))
            return mMinHeight;
        if ((mMaxHeight > 0) && (height > mMaxHeight))
            return mMaxHeight;

        return mHeight;
    }

    private boolean isZeroWidthContainer()
    {

        if ((mContainerSize == null) ||
            (mContainerSize.getWidth() > 0))
        {
            return false;
        }

        CssDeclaration declaration = mDeclarations.get(MAX_WIDTH_PROPERTY);
        return (declaration != null) && declaration.hasSingleValueOfType(CssValueType.PERCENTAGE);
    }

    private boolean hasSizeProperties()
    {
        return hasProperty(WIDTH_PROPERTY) || hasProperty(HEIGHT_PROPERTY);
    }

    private boolean hasProperty(String propName)
    {
        CssDeclaration declaration = mDeclarations.get(propName);
        if (declaration == null)
            return false;
        return declaration.hasSingleValueOfType(CssValueType.NUMBER) ||
               declaration.hasSingleValueOfType(CssValueType.LENGTH) ||
               declaration.hasSingleValueOfType(CssValueType.PERCENTAGE);
    }

    private static final String WIDTH_PROPERTY = "width";
    private static final String MIN_WIDTH_PROPERTY = "min-width";
    private static final String MAX_WIDTH_PROPERTY = "max-width";

    private static final String HEIGHT_PROPERTY = "height";
    private static final String MIN_HEIGHT_PROPERTY = "min-height";
    private static final String MAX_HEIGHT_PROPERTY = "max-height";

    private /*final*/ SizeD mImageSize;
    private /*final*/ SizeD mContainerSize;

    private /*final*/ CssDeclarationCollection mDeclarations;
    private /*final*/ boolean mPreserveAspectRatio;

    private /*final*/ double mWidth;
    private /*final*/ double mMinWidth;
    private /*final*/ double mMaxWidth;
    private /*final*/ double mHeight;
    private /*final*/ double mMinHeight;
    private /*final*/ double mMaxHeight;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"max-width",
		"max-height",
		"min-width",
		"min-height"
	);

}

