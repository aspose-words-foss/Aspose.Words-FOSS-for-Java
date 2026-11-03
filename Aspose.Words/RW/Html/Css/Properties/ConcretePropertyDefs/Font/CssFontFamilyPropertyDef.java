// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.Debug;
import com.aspose.words.ref.RefInt;

/// <summary>
/// Implements 'font-family' CSS property.
/// </summary>
class CssFontFamilyPropertyDef extends CssIndividualPropertyDef
{
    CssFontFamilyPropertyDef(String propertyName) // Initial value depends on user agent
    { // Initial value depends on user agent
    	super(propertyName, true, null);
	
        // Empty constructor.
    }

    CssFontFamilyPropertyDef()
    {
    	this("font-family");
	
        // Empty constructor.
    }

    /*override*/ CssDeclaration createIndividualDeclaration(
        CssValueList cssValues,
        int startIndex,
        boolean important,
        boolean isInQuirksMode,
        /*out*/RefInt affectedValues)
    {
        boolean endOfSet = false;
        int valueIndex = startIndex;
        /*CssValueType*/int prevValueType = CssValueType.COMMA;
        boolean wrongValue = false;
        affectedValues.set(0);
        while ((valueIndex < cssValues.getCount()) && !endOfSet && !wrongValue)
        {
            CssValue value = cssValues.get(valueIndex);
            switch (prevValueType)
            {
                case CssValueType.COMMA:
                {
                    if ((value.getValueType() == CssValueType.STRING) || (value.getValueType() == CssValueType.IDENTIFIER))
                        affectedValues.set(affectedValues.get() + 1)/*Indexer++*/;
                    else
                        wrongValue = true;
                    break;
                }
                case CssValueType.IDENTIFIER:
                {
                    if ((value.getValueType() == CssValueType.IDENTIFIER) || (value.getValueType() == CssValueType.COMMA))
                        affectedValues.set(affectedValues.get() + 1)/*Indexer++*/;
                    else
                        endOfSet = true;
                    break;
                }
                case CssValueType.STRING:
                {
                    if (value.getValueType() == CssValueType.COMMA)
                        affectedValues.set(affectedValues.get() + 1)/*Indexer++*/;
                    else
                        endOfSet = true;
                    break;
                }
                default:
                    Debug.assertTrue(false);
                    break;
            }
            prevValueType = value.getValueType();
            valueIndex++;
        }

        if ((affectedValues.get() == 0) || wrongValue || (prevValueType == CssValueType.COMMA))
        {
            affectedValues.set(0);
            return null;
        }

        return createDeclaration(cssValues.getRange(startIndex, affectedValues.get()), important, isInQuirksMode);
    }

    protected /*override*/ CssPropertyValue createPropertyValue(
        CssValueList cssValues,
        boolean isInQuirksMode)
    {
        // [[ <family-name> | <generic-family> ] [, <family-name>| <generic-family>]* ] | inherit
        // Font family names must either be given quoted as strings, or unquoted as a sequence of one or more identifiers. 
        CssValueList fontFamilyValues = new CssValueList();
        boolean legalValues = true;
        /*CssValueType*/int prevValueType = CssValueType.COMMA;
        String familyNameIdentifier = "";
        int i = 0;
        while ((i < cssValues.getCount()) && legalValues)
        {
            CssValue value = cssValues.get(i);
            switch (prevValueType)
            {
                case CssValueType.COMMA:
                {
                    if (value.getValueType() == CssValueType.STRING)
                        fontFamilyValues.add(value);
                    else if (value.getValueType() == CssValueType.IDENTIFIER)
                        // Font family name can be a sequence of one or more identifiers.
                        familyNameIdentifier = ((CssIdentifierValue)value).getValue();
                    else
                        legalValues = false;
                    break;
                }
                case CssValueType.IDENTIFIER:
                {
                    if (value.getValueType() == CssValueType.IDENTIFIER)
                    {
                        if (!msString.equals(familyNameIdentifier, ""))
                            familyNameIdentifier = msString.plusEqOperator(familyNameIdentifier, " ");
                        familyNameIdentifier = msString.plusEqOperator(familyNameIdentifier, ((CssIdentifierValue)value).getValue());
                    }
                    else if (value.getValueType() == CssValueType.COMMA)
                    {
                        Debug.assertTrue(!msString.equals(familyNameIdentifier, ""));
                        CssValue fontFamilyValue = (familyNameIdentifier.contains(" "))
                            ? (CssValue)new CssStringValue(familyNameIdentifier)
                            : new CssIdentifierValue(familyNameIdentifier);
                        fontFamilyValues.add(fontFamilyValue);
                        familyNameIdentifier = "";
                    }
                    else
                    {
                        legalValues = false;
                    }

                    break;
                }
                case CssValueType.STRING:
                    legalValues = value.getValueType() == CssValueType.COMMA;
                    break;
                default:
                    Debug.assertTrue(false);
                    break;
            }
            prevValueType = value.getValueType();
            i++;
        }
        if (legalValues && (!msString.equals(familyNameIdentifier, "")))
        {
            fontFamilyValues.add(CssValue.createFontFamilyValue(familyNameIdentifier));
        }

        return legalValues
            ? new CssFontFamilyPropertyValue(fontFamilyValues)
            : null;
    }
}

