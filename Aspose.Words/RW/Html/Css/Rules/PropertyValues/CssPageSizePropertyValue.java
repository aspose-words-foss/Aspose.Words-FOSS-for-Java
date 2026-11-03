// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.Debug;
import com.aspose.words.ref.RefInt;

/// <summary>
/// Represents a CSS page size property value.
/// </summary>
/// <remarks>
/// http://www.w3.org/TR/css3-page/#page-size-prop
/// Value:    length{1,2} | auto | [ page-size || [ portrait | landscape] ]
/// </remarks>
class CssPageSizePropertyValue extends CssPropertyValue
{
    /// <summary>
    /// Creates an instance with 'auto' page size value.
    /// </summary>
    private CssPageSizePropertyValue(CssValueList values)
    {
    	super(values);
	
        // Empty constructor.
    }

    /// <summary>
    /// Creates page size value with width and height lengths.
    /// </summary>
    static CssPageSizePropertyValue createLength(CssLengthValue width, CssLengthValue height)
    {
        CssPageSizePropertyValue value = new CssPageSizePropertyValue(new CssValueList(width, height));
        value.mWidth = width;
        value.mHeight = height;
        return value;
    }

    /// <summary>
    /// Creates page size value with one length.
    /// </summary>
    static CssPageSizePropertyValue createLength(CssLengthValue width)
    {
        // If only one length value is specified, it sets both the width and height of the page box (i.e., the box is a square).
        CssPageSizePropertyValue value = new CssPageSizePropertyValue(new CssValueList(width));
        value.mWidth = width;
        value.mHeight = width;
        return value;
    }

    /// <summary>
    /// Creates page size value with page-size media name or orientation.
    /// </summary>
    static CssPageSizePropertyValue createPageSize(CssIdentifierValue pageSizeOrOrientationValue)
    {
        CssPageSizePropertyValue propertyValue;
        /*CssPageSize*/int pageSize = 0;
        RefInt referenceToPageSize = new RefInt(pageSize);
        boolean outRefCondition0 = getPageSize(pageSizeOrOrientationValue, /*out*/ referenceToPageSize);
        pageSize = referenceToPageSize.get();
        if (outRefCondition0)
        {
            propertyValue = new CssPageSizePropertyValue(new CssValueList(pageSizeOrOrientationValue));
            propertyValue.mSize = pageSize;
            propertyValue.mOrientation = CssPageOrientation.NOT_SPECIFIED;
        }
        else
        {
            /*CssPageOrientation*/int pageOrientation = 0;
            RefInt referenceToPageOrientation = new RefInt(pageOrientation);
            boolean outRefCondition1 = getPageOrientation(pageSizeOrOrientationValue, /*out*/ referenceToPageOrientation);
            pageOrientation = referenceToPageOrientation.get();
            if (outRefCondition1)
            {
                propertyValue = new CssPageSizePropertyValue(new CssValueList(pageSizeOrOrientationValue));
                // Letter is default page size.
                propertyValue.mSize = CssPageSize.LETTER;
                propertyValue.mOrientation = pageOrientation;
            }
            else
            {
                propertyValue = null;
            }
        }

        return propertyValue;
    }

    /// <summary>
    /// Creates page size value with page-size media name and orientation.
    /// </summary>
    static CssPageSizePropertyValue createPageSize(CssIdentifierValue identifierValue1, CssIdentifierValue identifierValue2)
    {
        CssPageSizePropertyValue value;
        /*CssPageSize*/int pageSize = 0;
        /*CssPageOrientation*/int pageOrientation = 0;

        CssIdentifierValue pageSizeValue;
        CssIdentifierValue pageOrientationValue;

        RefInt referenceToPageSize = new RefInt(pageSize);
        boolean outRefCondition2 = getPageSize(identifierValue1, /*out*/ referenceToPageSize);
        pageSize = referenceToPageSize.get();
        if (outRefCondition2)
        {
            pageSizeValue = identifierValue1;

            RefInt referenceToPageOrientation = new RefInt(pageOrientation);
            boolean outRefCondition3 = !getPageOrientation(identifierValue2, /*out*/ referenceToPageOrientation);
            pageOrientation = referenceToPageOrientation.get();
            if (outRefCondition3)
                return null;

            pageOrientationValue = identifierValue2;
        }
        else
    	{ referenceToPageSize.set(pageSize); boolean outRefCondition4 = getPageSize(identifierValue2, /*out*/ referenceToPageSize); pageSize = referenceToPageSize.get(); if (outRefCondition4)
        {
            pageSizeValue = identifierValue2;

            RefInt referenceToPageOrientation = new RefInt(pageOrientation);
            boolean outRefCondition5 = !getPageOrientation(identifierValue1, /*out*/ referenceToPageOrientation);
            pageOrientation = referenceToPageOrientation.get();
            if (outRefCondition5)
                return null;

            pageOrientationValue = identifierValue1;
        }
        else
        {
            return null;
        }
    	}

        value = new CssPageSizePropertyValue(new CssValueList(pageSizeValue, pageOrientationValue));
        value.mSize = pageSize;
        value.mOrientation = pageOrientation;

        return value;
    }

    /// <summary>
    /// Creates 'auto' page size value.
    /// </summary>
    static CssPageSizePropertyValue createAuto()
    {
        CssPageSizePropertyValue value = new CssPageSizePropertyValue(new CssValueList(CssValue.Auto));
        value.mIsAuto = true;
        return value;
    }

    private static boolean getPageOrientation(CssIdentifierValue cssValue, /*out*/RefInt pageOrientation)
    {
        switch (gStringSwitchMap.of(msString.toLowerInvariant(cssValue.getValue())))
        {
            case /*"landscape"*/0:
                pageOrientation.set(CssPageOrientation.LANDSCAPE);
                return true;
            case /*"portrait"*/1:
                pageOrientation.set(CssPageOrientation.PORTRAIT);
                return true;
            default:
                pageOrientation.set(CssPageOrientation.NOT_SPECIFIED);
                return false;
        }
    }

    private static boolean getPageSize(CssIdentifierValue cssValue, /*out*/RefInt pageSize)
    {
        switch (gStringSwitchMap.of(msString.toLowerInvariant(cssValue.getValue())))
        {
            case /*"a5"*/2:
                pageSize.set(CssPageSize.A5);
                return true;
            case /*"a4"*/3:
                pageSize.set(CssPageSize.A4);
                return true;
            case /*"a3"*/4:
                pageSize.set(CssPageSize.A3);
                return true;
            case /*"b5"*/5:
                pageSize.set(CssPageSize.B5);
                return true;
            case /*"b4"*/6:
                pageSize.set(CssPageSize.B4);
                return true;
            case /*"letter"*/7:
                pageSize.set(CssPageSize.LETTER);
                return true;
            case /*"legal"*/8:
                pageSize.set(CssPageSize.LEGAL);
                return true;
            case /*"ledger"*/9:
                pageSize.set(CssPageSize.LEDGER);
                return true;
            default:
                pageSize.set(CssPageSize.NOT_SPECIFIED);
                return false;
        }
    }

    private CssLengthValue getPageSizeWidth()
    {
        double width;
        /*CssUnit*/int unit = CssUnit.MM;
        switch (mSize)
        {
            case CssPageSize.A5:
                width = 148.0;
                break;
            case CssPageSize.A4:
            case CssPageSize.NOT_SPECIFIED:
                width = 210.0;
                break;
            case CssPageSize.A3:
                width = 297.0;
                break;
            case CssPageSize.B5:
                width = 176.0;
                break;
            case CssPageSize.B4:
                width = 250.0;
                break;
            case CssPageSize.LETTER:
                // According to `size` CSS at-rule decription (https://developer.mozilla.org/en-US/docs/Web/CSS/@page/size)
                // dimensions of letter paper in North America are 8.5in x 11in.
                width = 8.5;
                unit = CssUnit.IN;
                break;
            case CssPageSize.LEGAL:
                // This keyword is a equivalent to the dimensions of legal papers in North America i.e. 8.5in x 14in.
                width = 8.5;
                unit = CssUnit.IN;
                break;
            case CssPageSize.LEDGER:
                // Dimensions of ledger paper in North America are 11in x 17in.
                width = 11.0;
                unit = CssUnit.IN;
                break;
            default:
                Debug.assertTrue(false);
                return G_AUTO_WIDTH_VALUE;
        }

        return new CssLengthValue(width, unit);
    }

    private CssLengthValue getPageSizeHeight()
    {
        double height;
        /*CssUnit*/int unit = CssUnit.MM;
        switch (mSize)
        {
            case CssPageSize.A5:
                height = 210.0;
                break;
            case CssPageSize.A4:
            case CssPageSize.NOT_SPECIFIED:
                height = 297.0;
                break;
            case CssPageSize.A3:
                height = 420.0;
                break;
            case CssPageSize.B5:
                height = 250.0;
                break;
            case CssPageSize.B4:
                height = 353.0;
                break;
            case CssPageSize.LETTER:
                // According to `size` CSS at-rule decription (https://developer.mozilla.org/en-US/docs/Web/CSS/@page/size)
                // dimensions of letter paper in North America are 8.5in x 11in.
                height = 11.0;
                unit = CssUnit.IN;
                break;
            case CssPageSize.LEGAL:
                // This keyword is a equivalent to the dimensions of legal papers in North America i.e. 8.5in x 14in.
                height = 14.0;
                unit = CssUnit.IN;
                break;
            case CssPageSize.LEDGER:
                // Dimensions of ledger paper in North America are 11in x 17in.
                height = 17.0;
                unit = CssUnit.IN;
                break;
            default:
                Debug.assertTrue(false);
                return G_AUTO_HEIGHT_VALUE;
        }

        return new CssLengthValue(height, unit);
    }

    /// <summary>
    /// Page size media name.
    /// </summary>
    /*CssPageSize*/int getSize() { return mSize; }

    /// <summary>
    /// Page orientation.
    /// </summary>
    /*CssPageOrientation*/int getOrientation() { return mOrientation; }

    /// <summary>
    /// Page width.
    /// </summary>
    CssLengthValue getWidth()
    {
        CssLengthValue width;
        if (mWidth != null)
            width = mWidth;
        else if (mIsAuto)
            width = G_AUTO_WIDTH_VALUE;
        else
            width = getPageSizeWidth();
        return width;
    }

    /// <summary>
    /// Page height.
    /// </summary>
    CssLengthValue getHeight()
    {
        CssLengthValue height;
        if (mHeight != null)
            height = mHeight;
        else if (mIsAuto)
            height = G_AUTO_HEIGHT_VALUE;
        else
            height = getPageSizeHeight();
        return height;
    }

    private boolean mIsAuto;
    private CssLengthValue mWidth;
    private CssLengthValue mHeight;
    private /*CssPageSize*/int mSize;
    private /*CssPageOrientation*/int mOrientation;

    // We use the size of ISO Letter media (8.5" wide and 11" high) as the auto value.
    private static /*final*/ CssLengthValue G_AUTO_WIDTH_VALUE = new CssLengthValue(8.5, CssUnit.IN);
    private static /*final*/ CssLengthValue G_AUTO_HEIGHT_VALUE = new CssLengthValue(11.0, CssUnit.IN);

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"landscape",
		"portrait",
		"a5",
		"a4",
		"a3",
		"b5",
		"b4",
		"letter",
		"legal",
		"ledger"
	);

}

