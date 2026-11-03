// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'text-align' CSS property.
/// </summary>
/// <remarks>CSS text align is applied to a model format with <see cref="CssTextAlignStyleConverter"/></remarks>
class CssTextAlignPropertyDef extends CssIndividualSimplePropertyDef
{
    CssTextAlignPropertyDef()
    {
    	super(
            "text-align",
            true,
            CssValue.AwStart,
            // This property also accepts custom alignment values ('-aw-start', '-aw-match-parent', '-aw-left', '-aw-right',
            // and '-aw-center'), but they are used internally and are not recognized in document CSS.
            CssValueFilter.values(
                CssValue.Left,
                CssValue.Right,
                CssValue.Center,
                CssValue.Justify));
	
        // Empty constructor.
    }

    protected /*override*/ CssPropertyValue toComputedValueCore(CssPropertyValue specifiedValue, CssCascadeContext cssContex)
    {
        CssPropertyValue computedValue = specifiedValue;
        if (specifiedValue.equals(CssValue.AwMatchParent))
        {
            CssDeclaration parentTextAlignDeclaration = cssContex.getParentElementDeclarations().get("text-align");
            if ((parentTextAlignDeclaration == null) || (parentTextAlignDeclaration.getValue().equals(CssValue.AwStart)))
            {
                /*CssDirection*/int parentDirection = CssUtil.getDirection(cssContex.getParentElementDeclarations());
                computedValue = CssUtil.getEffectiveAwStartValue(parentDirection);
            }
            else
            {
                computedValue = parentTextAlignDeclaration.getValue();
            }
        }
        return computedValue;
    }
}

