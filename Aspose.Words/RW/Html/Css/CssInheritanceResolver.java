// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Resolves CSS declarations using CSS inheritance principle.
/// </summary>
/// <remarks>
/// Each element in an HTML document is part of a tree, and every element except the initial 'html'
/// element has a parent element that encloses it. Whatever styles are applied to that parent element 
/// can be applied to the elements enclosed in it if the properties are inherited.
/// http://www.w3.org/TR/CSS21/cascade.html#inheritance
/// </remarks>
class CssInheritanceResolver
{
    /// <summary>
    /// Updates element's CSS declarations with declarations inherited from the parent element.
    /// </summary>
    /// <param name="elementDeclarations">Element's CSS declaration. This collection will be updated.</param>
    /// <param name="parentDeclarations">Parent element's computed CSS declarations.</param>
    static void resolve(
        CssDeclarationCollectionBuilder elementDeclarations,
        CssDeclarationCollection parentDeclarations)
    {

        // Each property may also have a cascaded value of 'inherit', which means that, for a given element, the property
        // takes as specified value the computed value of the element's parent (see errata in the CSS 2.1 Specification:
        // http://www.w3.org/Style/css2-updates/REC-CSS2-20110607-errata.html).
        // The 'inherit' value can be used to enforce inheritance of values, and it can also be used on properties that
        // are not normally inherited.
        ArrayList<String> inheritedPropertyNames = new ArrayList<String>();
        for (CssDeclaration elementDeclaration : elementDeclarations)
        {
            if (elementDeclaration.getValue().isInherit())
            {
                msArrayList.add(inheritedPropertyNames, elementDeclaration.getProperty());
            }
        }
        for (String propertyName : inheritedPropertyNames)
        {
            elementDeclarations.remove(propertyName);
            CssDeclaration parentDeclaration = parentDeclarations.get(propertyName);
            if ((parentDeclaration != null) && (!parentDeclaration.getValue().isInherit()))
            {
                elementDeclarations.add(parentDeclaration);
                elementDeclarations.copyFlags(propertyName, parentDeclarations);
            }
        }

        // Inherit parent's declarations that are normally inherited and are not overridden by element's declarations.
        for (CssDeclaration parentDeclaration : parentDeclarations)
        {
            CssPropertyDef propertyDef = CssPropertyDefFactory.getPropertyDef(parentDeclaration.getProperty());
            if (propertyDef.getInherited() && (elementDeclarations.get(parentDeclaration.getProperty()) == null))
            {
                elementDeclarations.add(parentDeclaration);
                elementDeclarations.copyFlags(parentDeclaration.getProperty(), parentDeclarations);
            }
        }
    }
}

