// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2020 by Artem Shabarshin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.words.ref.RefInt;

/// <summary>
/// Implements '-aw-border' CSS extended border property.
/// </summary>
/// <remarks>
/// The '-aw-border' extension property is a shorthand property for setting the same width and style
/// for all four borders of a box.
/// </remarks>
class CssAWBorderPropertyDef extends CssOrderInsensitiveShorthandPropertyDef
{
    CssAWBorderPropertyDef()
    {
    	super(HtmlConstants.ASPOSE_BORDER, false);
	
        // Empty constructor.
    }

    /*override*/ void reduce(CssDeclarationCollectionBuilder declarationsBuilder)
    {
        // AWBorder declarations are already reduced to -aw-border-(top|right|bottom|left) shorthand properties before
        // because CssShorthandPropertyDef.Reduce() reduces them first.
        CssDeclaration borderTopDeclaration = declarationsBuilder.get(HtmlConstants.ASPOSE_BORDER_TOP);
        CssDeclaration borderRightDeclaration = declarationsBuilder.get(HtmlConstants.ASPOSE_BORDER_RIGHT);
        CssDeclaration borderBottomDeclaration = declarationsBuilder.get(HtmlConstants.ASPOSE_BORDER_BOTTOM);
        CssDeclaration borderLeftDeclaration = declarationsBuilder.get(HtmlConstants.ASPOSE_BORDER_LEFT);

        if ((borderTopDeclaration != null) &&
            borderTopDeclaration.equals(borderRightDeclaration) &&
            borderTopDeclaration.equals(borderBottomDeclaration) &&
            borderTopDeclaration.equals(borderLeftDeclaration))
        {
            declarationsBuilder.remove(borderTopDeclaration.getProperty());
            declarationsBuilder.remove(borderRightDeclaration.getProperty());
            declarationsBuilder.remove(borderBottomDeclaration.getProperty());
            declarationsBuilder.remove(borderLeftDeclaration.getProperty());

            declarationsBuilder.addOrReplace(new CssSpecifiedDeclaration(
                getName(),
                borderTopDeclaration.getValue(),
                borderTopDeclaration.getImportant()));
        }
    }

    protected /*override*/ CssDeclarationCollection createIndividualDeclarations(
        CssValueList cssValues,
        boolean important,
        boolean isInQuirksMode)
    {
        // -aw-border: [-aw-border-style] [-aw-border-width] [-aw-border-color];
        if (cssValues.getCount() == 0)
            return null;

        CssDeclarationCollectionBuilder result = new CssDeclarationCollectionBuilder();
        CssValueList cssValuesCopy = new CssValueList(cssValues);

        CssDeclarationCollection styleDeclarations = getPropertyDeclarations(
            CssPropertyDefFactory.getIndividualPropertyDef(HtmlConstants.ASPOSE_BORDER_TOP_STYLE),
            CssPropertyDefFactory.getIndividualPropertyDef(HtmlConstants.ASPOSE_BORDER_RIGHT_STYLE),
            CssPropertyDefFactory.getIndividualPropertyDef(HtmlConstants.ASPOSE_BORDER_BOTTOM_STYLE),
            CssPropertyDefFactory.getIndividualPropertyDef(HtmlConstants.ASPOSE_BORDER_LEFT_STYLE),
            cssValuesCopy,
            important,
            isInQuirksMode);
        if (styleDeclarations.getCount() != 0)
            result.addOrReplace(styleDeclarations);

        CssDeclarationCollection widthDeclarations = getPropertyDeclarations(
            CssPropertyDefFactory.getIndividualPropertyDef(HtmlConstants.ASPOSE_BORDER_TOP_WIDTH),
            CssPropertyDefFactory.getIndividualPropertyDef(HtmlConstants.ASPOSE_BORDER_RIGHT_WIDTH),
            CssPropertyDefFactory.getIndividualPropertyDef(HtmlConstants.ASPOSE_BORDER_BOTTOM_WIDTH),
            CssPropertyDefFactory.getIndividualPropertyDef(HtmlConstants.ASPOSE_BORDER_LEFT_WIDTH),
            cssValuesCopy,
            important,
            isInQuirksMode);
        if (widthDeclarations.getCount() != 0)
            result.addOrReplace(widthDeclarations);

        CssDeclarationCollection colorDeclarations = getPropertyDeclarations(
            CssPropertyDefFactory.getIndividualPropertyDef(HtmlConstants.ASPOSE_BORDER_TOP_COLOR),
            CssPropertyDefFactory.getIndividualPropertyDef(HtmlConstants.ASPOSE_BORDER_RIGHT_COLOR),
            CssPropertyDefFactory.getIndividualPropertyDef(HtmlConstants.ASPOSE_BORDER_BOTTOM_COLOR),
            CssPropertyDefFactory.getIndividualPropertyDef(HtmlConstants.ASPOSE_BORDER_LEFT_COLOR),
            cssValuesCopy,
            important,
            isInQuirksMode);
        if (colorDeclarations.getCount() != 0)
            result.addOrReplace(colorDeclarations);

        return (cssValuesCopy.getCount() == 0)
            ? result.getDeclarations()
            : null;
    }

    private static CssDeclarationCollection getPropertyDeclarations(
        CssIndividualPropertyDef topPropertyDef,
        CssIndividualPropertyDef rightLeftPropertyDefs,
        CssIndividualPropertyDef bottomPropertyDefs,
        CssIndividualPropertyDef leftPropertyDefs,
        CssValueList cssValues,
        boolean important,
        boolean isInQuirksMode)
    {
        CssDeclarationCollectionBuilder declarationBuilder = new CssDeclarationCollectionBuilder();

        int valueIndex = 0;
        while (valueIndex < cssValues.getCount())
        {
            int affectedValuesCount = 0;
            RefInt referenceToAffectedValuesCount = new RefInt(affectedValuesCount);
            CssDeclaration topDeclaration = topPropertyDef.createIndividualDeclaration(
                cssValues,
                valueIndex,
                important,
                isInQuirksMode,
                /*out*/ referenceToAffectedValuesCount);
            affectedValuesCount = referenceToAffectedValuesCount.get();
            if (topDeclaration == null)
            {
                valueIndex++;
                continue;
            }

            CssValueList affectedValues = new CssValueList();
            while (affectedValuesCount != 0)
            {
                affectedValues.add(cssValues.get(valueIndex));
                cssValues.removeAt(valueIndex);
                affectedValuesCount--;
            }

            declarationBuilder.addOrReplace(topDeclaration);
            for (CssIndividualPropertyDef edgePropertyDef :
                new CssIndividualPropertyDef[] { rightLeftPropertyDefs, bottomPropertyDefs, leftPropertyDefs })
            {
                declarationBuilder.addOrReplace(edgePropertyDef.createDeclarations(
                    affectedValues,
                    important,
                    isInQuirksMode));
            }

            return declarationBuilder.getDeclarations();
        }

        for (CssIndividualPropertyDef edgePropertyDef :
            new CssIndividualPropertyDef[] { topPropertyDef, rightLeftPropertyDefs, bottomPropertyDefs, leftPropertyDefs })
        {
            CssDeclaration initialDeclaration = edgePropertyDef.createInitialDeclaration(important);
            if (initialDeclaration != null)
                declarationBuilder.addOrReplace(initialDeclaration);
        }

        return declarationBuilder.getDeclarations();
    }

    protected /*override*/ CssPropertyValue createShorthandValue(CssDeclarationCollection individualDeclarations)
    {
        Debug.assertTrue(false, "Should not be called.");
        return null;
    }

    protected /*override*/ ShorthandPropertyPart[] getIndividualProperties()
    {
        if (mIndividualProperties != null)
            return mIndividualProperties;

        mIndividualProperties = new ShorthandPropertyPart[]
        {
            new ShorthandPropertyPart(HtmlConstants.ASPOSE_BORDER_TOP_STYLE, true),
            new ShorthandPropertyPart(HtmlConstants.ASPOSE_BORDER_RIGHT_STYLE, true),
            new ShorthandPropertyPart(HtmlConstants.ASPOSE_BORDER_BOTTOM_STYLE, true),
            new ShorthandPropertyPart(HtmlConstants.ASPOSE_BORDER_LEFT_STYLE, true),
            new ShorthandPropertyPart(HtmlConstants.ASPOSE_BORDER_TOP_WIDTH, false),
            new ShorthandPropertyPart(HtmlConstants.ASPOSE_BORDER_RIGHT_WIDTH, false),
            new ShorthandPropertyPart(HtmlConstants.ASPOSE_BORDER_BOTTOM_WIDTH, false),
            new ShorthandPropertyPart(HtmlConstants.ASPOSE_BORDER_LEFT_WIDTH, false),
            new ShorthandPropertyPart(HtmlConstants.ASPOSE_BORDER_TOP_COLOR, false),
            new ShorthandPropertyPart(HtmlConstants.ASPOSE_BORDER_RIGHT_COLOR, false),
            new ShorthandPropertyPart(HtmlConstants.ASPOSE_BORDER_BOTTOM_COLOR, false),
            new ShorthandPropertyPart(HtmlConstants.ASPOSE_BORDER_LEFT_COLOR, false)
        };
        return mIndividualProperties;
    }

    private ShorthandPropertyPart[] mIndividualProperties;
}

