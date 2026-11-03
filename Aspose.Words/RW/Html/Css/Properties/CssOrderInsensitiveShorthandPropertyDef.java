// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/07/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.words.ref.RefInt;


/// <summary>
/// Base definition class for CSS shorthand properties where order of individual properties isn't important.
/// </summary>
abstract class CssOrderInsensitiveShorthandPropertyDef extends CssShorthandPropertyDef
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="name">Property name.</param>
    /// <param name="inherited">Indicates that the property is normally inherited.</param>
    protected CssOrderInsensitiveShorthandPropertyDef(String name, boolean inherited)
    {
    	super(name, inherited);
	
    }

    protected /*override*/ CssDeclarationCollection createIndividualDeclarations(
        CssValueList cssValues,
        boolean important,
        boolean isInQuirksMode)
    {
        if (cssValues.getCount() == 0)
            return null;

        CssDeclarationCollectionBuilder result = new CssDeclarationCollectionBuilder();
        ArrayList<ShorthandPropertyPart> individualProperties = msArrayList.ctor(getIndividualProperties());

        int valueIndex = 0;
        while (valueIndex < cssValues.getCount())
        {
            boolean propertyFound = false;
            int propertyIndex = 0;
            while ((propertyIndex < individualProperties.size()) && !propertyFound)
            {
                ShorthandPropertyPart individualProperty = individualProperties.get(propertyIndex);
                int affectedValues = 0;
                RefInt referenceToAffectedValues = new RefInt(affectedValues);
                CssDeclarationCollection indivdualDeclarations = individualProperty.createIndividualDeclarations(
                    cssValues,
                    valueIndex,
                    important,
                    isInQuirksMode,
                    /*out*/ referenceToAffectedValues);
                affectedValues = referenceToAffectedValues.get();
                if (indivdualDeclarations != null)
                {
                    propertyFound = true;
                    result.addOrReplace(indivdualDeclarations);
                    individualProperties.remove(propertyIndex);
                    valueIndex += affectedValues;
                }

                propertyIndex++;
            }

            if (!propertyFound)
                return null;
        }

        if (result.getCount() == 0)
            return null;

        for (ShorthandPropertyPart individualProperty : individualProperties)
        {
            if (individualProperty.getRequired())
                return null;

            CssDeclarationCollection initialDeclarations = individualProperty.createInitialDeclarations(important);
            if (initialDeclarations != null)
                result.addOrReplace(initialDeclarations);
        }

        return result.getDeclarations();
    }
}

