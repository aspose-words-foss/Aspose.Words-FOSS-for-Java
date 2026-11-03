// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/06/2019 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Represents list item with a bullet.
/// </summary>
class HtmlBulletListItem extends HtmlListItemBase
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="listLevelId">List level identifier.</param>
    /// <param name="markerType">Marker type.</param>
    /// <param name="builder">DocumentBuilder.</param>
    /// <param name="formatter">DocumentFormatter.</param>
    /// <param name="numberFormat">Number format.</param>
    /// <param name="numberStyle">Number style.</param>
    /// <param name="fontName">Bullet's font name.</param>
    HtmlBulletListItem(
        HtmlListLevelId listLevelId,
        /*HtmlMarkerType*/int markerType,
        DocumentBuilder builder,
        DocumentFormatter formatter,
        String numberFormat,
        /*NumberStyle*/int numberStyle,
        String fontName)
    {
    	super(listLevelId, markerType, numberFormat);
	
        Debug.assertTrue(builder != null);
        Debug.assertTrue(formatter != null);

        mBuilder = builder;
        mFormatter = formatter;

        setListLevelType(HtmlModelListLevelType.BULLET);
        setNumberFormat(numberFormat);
        setNumberStyle(getListLevelId().getListLevelNumber(), numberStyle);

        mFontName = fontName;
    }

    /*override*/ void postModifyList(HtmlModelList modelList) throws Exception
    {
        HtmlModelListLevel modelListLevel = modelList.getListLevel(getListLevelId().getListLevelNumber());

        switch (getMarkerType())
        {
            case HtmlMarkerType.AW:
            case HtmlMarkerType.HTML:
            {
                modelListLevel.getListLevel().getRunPr().setSymbolFonts(mFontName);
                break;
            }
            case HtmlMarkerType.PSEUDO_ELEMENT:
            {
                mFormatter.switchToPart(HtmlElementPart.BEFORE, false);
                mFormatter.toFont(modelListLevel.getListLevel().getFont(), mBuilder.getCurrentParagraph().getParagraphStyle());
                mFormatter.switchToPart(HtmlElementPart.ELEMENT, false);

                CssComputedDeclaration fontFamilyDeclaration =
                    (CssComputedDeclaration)mFormatter.getBeforePseudoElementDeclarations().get("font-family");
                if (fontFamilyDeclaration == null)
                {
                    // HTML uses the 'Times New Roman' font by default. 
                    modelListLevel.getListLevel().getRunPr().setSymbolFonts("Times New Roman");
                }

                break;
            }
            default:
            {
                // At the moment of writing, there were no other HTML bullet types. We should never get here.
                Debug.fail("Unsupported HTML bullet type");
                break;
            }
        }

        CssDeclaration fontWeightDeclaration = mFormatter.getElementDeclarations().get(HtmlConstants.LIST_LABEL_FONT_WEIGHT);
        if (fontWeightDeclaration != null)
        {
            boolean isBold = fontWeightDeclaration.getValue().equals(CssValue.Bold);
            modelListLevel.getListLevel().getFont().setBold(isBold);
            modelListLevel.getListLevel().getFont().setBoldBi(isBold);
        }
    }

    private /*final*/ DocumentBuilder mBuilder;
    private /*final*/ DocumentFormatter mFormatter;
    private /*final*/ String mFontName;
}

