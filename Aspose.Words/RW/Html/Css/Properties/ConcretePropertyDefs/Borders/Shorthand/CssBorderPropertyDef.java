// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.words.ref.RefInt;

/// <summary>
/// Implements 'border' CSS property.
/// </summary>
/// <remarks>
/// The 'border' property is a shorthand property for setting the same width, color, and style for all four borders of a box.
/// http://www.w3.org/TR/CSS2/box.html#propdef-border
/// </remarks>
class CssBorderPropertyDef extends CssOrderInsensitiveShorthandPropertyDef
{
    CssBorderPropertyDef()
    {
    	super("border", false);
	
        // Empty constructor.
    }

    /*override*/ void reduce(CssDeclarationCollectionBuilder declarationsBuilder)
    {
        // Border declarations are already reduced to border-(top|right|bottom|left) shorthand properties before
        // because CssShorthandPropertyDef.Reduce() reduces them first.
        CssDeclaration borderTopDeclaration = declarationsBuilder.get("border-top");
        CssDeclaration borderRightDeclaration = declarationsBuilder.get("border-right");
        CssDeclaration borderBottomDeclaration = declarationsBuilder.get("border-bottom");
        CssDeclaration borderLeftDeclaration = declarationsBuilder.get("border-left");

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
        // border: [border-style] [border-width] [border-color]|initial|inherit;
        if (cssValues.getCount() == 0)
            return null;

        CssDeclarationCollectionBuilder result = new CssDeclarationCollectionBuilder();
        CssValueList cssValuesCopy = new CssValueList(cssValues);

        CssDeclarationCollection styleDeclarations = getPropertyDeclarations(
            CssPropertyDefFactory.getIndividualPropertyDef("border-top-style"),
            CssPropertyDefFactory.getIndividualPropertyDef("border-right-style"),
            CssPropertyDefFactory.getIndividualPropertyDef("border-bottom-style"),
            CssPropertyDefFactory.getIndividualPropertyDef("border-left-style"),
            cssValuesCopy,
            important,
            isInQuirksMode);
        if (styleDeclarations.getCount() != 0)
            result.addOrReplace(styleDeclarations);

        // WORDSNET-14913 Unlike individual "border-xxx-width" properties, the shorthand "border" property
        // does not accept quirky length values (unitless number values). That's why here we always create border width
        // declarations in the Standards mode and ignore the "is in Quirks mode" flag.
        CssDeclarationCollection widthDeclarations = getPropertyDeclarations(
            CssPropertyDefFactory.getIndividualPropertyDef("border-top-width"),
            CssPropertyDefFactory.getIndividualPropertyDef("border-right-width"),
            CssPropertyDefFactory.getIndividualPropertyDef("border-bottom-width"),
            CssPropertyDefFactory.getIndividualPropertyDef("border-left-width"),
            cssValuesCopy,
            important,
            false);
        if (widthDeclarations.getCount() != 0)
            result.addOrReplace(widthDeclarations);

        CssDeclarationCollection colorDeclarations = getPropertyDeclarations(
            CssPropertyDefFactory.getIndividualPropertyDef("border-top-color"),
            CssPropertyDefFactory.getIndividualPropertyDef("border-right-color"),
            CssPropertyDefFactory.getIndividualPropertyDef("border-bottom-color"),
            CssPropertyDefFactory.getIndividualPropertyDef("border-left-color"),
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
            new ShorthandPropertyPart("border-top-style", true),
            new ShorthandPropertyPart("border-right-style", true),
            new ShorthandPropertyPart("border-bottom-style", true),
            new ShorthandPropertyPart("border-left-style", true),
            new ShorthandPropertyPart("border-top-width", false),
            new ShorthandPropertyPart("border-right-width", false),
            new ShorthandPropertyPart("border-bottom-width", false),
            new ShorthandPropertyPart("border-left-width", false),
            new ShorthandPropertyPart("border-top-color", false),
            new ShorthandPropertyPart("border-right-color", false),
            new ShorthandPropertyPart("border-bottom-color", false),
            new ShorthandPropertyPart("border-left-color", false)
        };
        return mIndividualProperties;
    }

    private ShorthandPropertyPart[] mIndividualProperties;
}

