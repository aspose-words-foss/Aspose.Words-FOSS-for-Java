// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/04/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.words.ref.RefInt;

/// <summary>
/// Implements base class for shorthands properties related to edges of a box, 
/// like border-style, margin or padding, always use a consistent 1-to-4-value syntax representing edges.
/// </summary>
/// <remarks>
/// The top, right, bottom, and left values can be changed independently using separate properties. 
/// Syntax:
///   name: width{1,4} | inherit
/// </remarks>
abstract class CssShorthand14PropertyDefBase extends CssShorthandPropertyDef
{
    protected CssShorthand14PropertyDefBase(
        String name,
        String topPropertyName,
        String rightPropertyName,
        String bottomPropertyName,
        String leftPropertyName)
    {
    	super(name, false);
	
        Debug.assertTrue(StringUtil.hasChars(topPropertyName));
        Debug.assertTrue(StringUtil.hasChars(rightPropertyName));
        Debug.assertTrue(StringUtil.hasChars(bottomPropertyName));
        Debug.assertTrue(StringUtil.hasChars(leftPropertyName));

        Debug.assertTrue(StringUtil.isAsciiLowerCase(topPropertyName));
        Debug.assertTrue(StringUtil.isAsciiLowerCase(rightPropertyName));
        Debug.assertTrue(StringUtil.isAsciiLowerCase(bottomPropertyName));
        Debug.assertTrue(StringUtil.isAsciiLowerCase(leftPropertyName));

        mTopPropertyName = topPropertyName;
        mRightPropertyName = rightPropertyName;
        mBottomPropertyName = bottomPropertyName;
        mLeftPropertyName = leftPropertyName;
    }

    protected /*override*/ CssPropertyValue createShorthandValue(CssDeclarationCollection individualDeclarations)
    {
        Debug.assertTrue(individualDeclarations.getCount() == 4);

        return new CssShorthand14PropertyValue(individualDeclarations.get(getTopPropertyName()).getValue(),
                                               individualDeclarations.get(getRightPropertyName()).getValue(),
                                               individualDeclarations.get(getBottomPropertyName()).getValue(),
                                               individualDeclarations.get(getLeftPropertyName()).getValue());
    }

    protected /*override*/ CssDeclarationCollection createIndividualDeclarations(
        CssValueList cssValues,
        boolean important,
        boolean isInQuirksMode)
    {
        Debug.assertTrue(cssValues != null);
        if ((cssValues.getCount() == 0) || (cssValues.getCount() > 4))
            return null;

        CssDeclarationCollectionBuilder result = new CssDeclarationCollectionBuilder();

        ShorthandPropertyPart[] individualProperties = getIndividualProperties();
        Debug.assertTrue(individualProperties.length == 4);

        CssValueList values = new CssValueList(cssValues);
        if (values.getCount() == 1)
            values.add(values.get(0));
        if (values.getCount() == 2)
            values.add(values.get(0));
        if (values.getCount() == 3)
            values.add(values.get(1));

        for (int i = 0; i < 4; i++)
        {
            int affectedValues = 0;
            RefInt referenceToAffectedValues = new RefInt(affectedValues);
            CssDeclarationCollection individualDeclarations = individualProperties[i].createIndividualDeclarations(
                values,
                i,
                important,
                isInQuirksMode,
                /*out*/ referenceToAffectedValues);
            affectedValues = referenceToAffectedValues.get();
            if (individualDeclarations == null)
                return null;

            result.addOrReplace(individualDeclarations);
        }

        return result.getDeclarations();
    }

    protected /*override*/ ShorthandPropertyPart[] getIndividualProperties()
    {
        if (mIndividualProperties != null)
            return mIndividualProperties;

        mIndividualProperties = new ShorthandPropertyPart[]
        {
            new ShorthandPropertyPart(getTopPropertyName(), true),
            new ShorthandPropertyPart(getRightPropertyName(), true),
            new ShorthandPropertyPart(getBottomPropertyName(), true),
            new ShorthandPropertyPart(getLeftPropertyName(), true)
        };
        return mIndividualProperties;
    }

    protected String getTopPropertyName() { return mTopPropertyName; };

    private  String mTopPropertyName;

    protected String getRightPropertyName() { return mRightPropertyName; };

    private  String mRightPropertyName;

    protected String getBottomPropertyName() { return mBottomPropertyName; };

    private  String mBottomPropertyName;

    protected String getLeftPropertyName() { return mLeftPropertyName; };

    private  String mLeftPropertyName;

    private ShorthandPropertyPart[] mIndividualProperties;
}

