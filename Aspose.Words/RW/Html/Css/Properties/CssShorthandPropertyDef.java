// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Base definition class for CSS shorthand properties.
/// Shorthand properties are CSS properties that let you set the values of several other CSS properties simultaneously.
/// e.g: background: #ffffff url('img_tree.png') no-repeat right top;
/// </summary>
abstract class CssShorthandPropertyDef extends CssPropertyDef
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="name">Property name.</param>
    /// <param name="inherited">Indicates that the property is normally inherited.</param>
    protected CssShorthandPropertyDef(String name, boolean inherited)
    {
    	super(name, inherited);
	
        // Empty constructor.
    }

    /*override*/ CssDeclarationCollection createDeclarations(
        CssValueList cssValues,
        boolean important,
        boolean isInQuirksMode)
    {
        if (cssValues.getCount() == 0)
            return null;

        CssDeclarationCollection result;
        if ((cssValues.getCount() == 1) && cssValues.get(0).equals(CssValue.Inherit))
        {
            CssDeclarationCollectionBuilder inheritDeclarations = new CssDeclarationCollectionBuilder();
            for (ShorthandPropertyPart individualProperty : getIndividualProperties())
            {
                CssDeclarationCollection propertyInheritDeclarations = individualProperty.createInheritDeclarations(important);
                inheritDeclarations.addOrReplace(propertyInheritDeclarations);
            }
            result = inheritDeclarations.getDeclarations();
        }
        else if ((cssValues.getCount() == 1) && cssValues.get(0).equals(CssValue.Initial))
        {
            // The initial CSS keyword applies the initial value of a property to an element. 
            // It is allowed on every CSS property and causes the element for which it is specified to use the initial value of the property.
            CssDeclarationCollectionBuilder initialDeclarations = new CssDeclarationCollectionBuilder();
            for (ShorthandPropertyPart individualProperty : getIndividualProperties())
            {
                CssDeclarationCollection propertyInitialDeclarations = individualProperty.createInitialDeclarations(important);
                if (propertyInitialDeclarations != null)
                    initialDeclarations.addOrReplace(propertyInitialDeclarations);
            }
            result = initialDeclarations.getDeclarations();
        }
        else
        {
            result = createIndividualDeclarations(cssValues, important, isInQuirksMode);
        }

        return ((result != null) && (result.getCount() != 0))
            ? result
            : null;
    }

    /// <summary>
    /// Checks whether any properties in a collection can be reduced to this shorthand.
    /// </summary>
    /*virtual*/ boolean canReduce(CssDeclarationCollection declarationsBuilder)
    {
        ShorthandPropertyPart[] individualProperties = getIndividualProperties();
        boolean foundAnything = false;
        for (ShorthandPropertyPart shorthandPropertyPart : individualProperties)
        {
            if (declarationsBuilder.get(shorthandPropertyPart.getPropertyName()) != null)
            {
                foundAnything = true;
            }
            else if (shorthandPropertyPart.getRequired())
            {
                return false;
            }
        }
        return foundAnything;
    }

    /// <summary>
    /// Reduces appropriate properties in a collection to this shorthand. 
    /// Removes the reduced properties from the collection and adds a shorthand property into it.
    /// </summary>
    /*virtual*/ void reduce(CssDeclarationCollectionBuilder declarationsBuilder)
    {
        ShorthandPropertyPart[] individualProperties = getIndividualProperties();

        ArrayList<CssDeclaration> foundDeclarations = new ArrayList<CssDeclaration>(individualProperties.length);

        int inheritDeclarationsCount = 0;
        int importantCount = 0;
        for (int i = 0; i < individualProperties.length; i++)
        {
            Debug.assertTrue(individualProperties[i].getPropertyName() != null);
            CssDeclaration declaration = declarationsBuilder.get(individualProperties[i].getPropertyName());
            if (declaration == null)
                return;

            msArrayList.add(foundDeclarations, declaration);
            if (declaration.getImportant())
                importantCount++;
            if (declaration.getValue().isInherit())
                inheritDeclarationsCount++;
        }

        // We reduce only two or more individual declarations into a shorthand one. One individual declaration we ignore.
        if ((foundDeclarations.size() > 1) &&
            ((importantCount == 0) || (importantCount == foundDeclarations.size())) &&
            ((inheritDeclarationsCount == 0) || (inheritDeclarationsCount == foundDeclarations.size())))
        {
            CssPropertyValue shorthandValue = createShorthandValue(new CssDeclarationCollection(foundDeclarations));
            if (shorthandValue != null)
            {
                for (CssDeclaration declaration : foundDeclarations)
                {
                    declarationsBuilder.remove(declaration.getProperty());
                }
                declarationsBuilder.addOrReplace(new CssSpecifiedDeclaration(getName(), shorthandValue, importantCount != 0));
            }
        }
    }

    protected abstract CssPropertyValue createShorthandValue(CssDeclarationCollection individualDeclarations);

    /// <summary> 
    /// Implement in derived classes and return CSS declarations if the property can accept the specified values.
    /// </summary>
    /// <remarks>
    /// You don't need to handle 'initial' and 'inherit' CSS values in this function.
    /// </remarks>
    /// <param name="cssValues">CSS values.</param>
    /// <param name="important">Indicates that the declaration should be marked as !important.</param>
    /// <param name="isInQuirksMode">
    /// Indicates whether the CSS document is being processed in the Quirks mode, as opposed to the Standards mode.
    /// </param>
    /// <returns>CSS declarations if the property can accept the specified values; null otherwise.</returns>
    protected abstract CssDeclarationCollection createIndividualDeclarations(
        CssValueList cssValues,
        boolean important,
        boolean isInQuirksMode);

    /// <summary>
    /// Implement in derived classes and return individual properties, which are parts of the shorthand property.
    /// </summary>
    /// <returns>Individual properties array.</returns>
    protected abstract ShorthandPropertyPart[] getIndividualProperties();
}

