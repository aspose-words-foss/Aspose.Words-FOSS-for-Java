// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/11/2014 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.DrColor;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Represents a function value.
/// The format of a function value is name(argument).
///   name - an CSS identifier value.
///   argument - collection of any supported CSS values. Can be empty.
/// </summary>
class CssFunctionValue extends CssValue
{
    CssFunctionValue(String name, CssValueList arguments)
    {
    	super(CssValueType.FUNCTION, getCss(name, arguments));
	
        Debug.assertTrue(name != null);
        Debug.assertTrue(arguments != null);
        mName = name;
        mArguments = new CssValueList(arguments);
        mValue = getCss(mName, mArguments);
    }

    CssFunctionValue(String name, CssValue value)
    {
    	this(name, new CssValueList(value));
	
        // Empty constructor.
    }

    /*override*/ void toCss(StringBuilder sb)
    {
        toCss(mName, mArguments, sb);
    }

    protected /*override*/ boolean doEquals(CssValue other)
    {
        Debug.assertTrue(other instanceof CssFunctionValue);
        CssFunctionValue otherFunction = (CssFunctionValue)other;
        return StringUtil.equalsIgnoreCase(mName, otherFunction.mName) && mArguments.equals(otherFunction.mArguments);
    }

    /*override*/ DrColor parseAsColor()
    {
        switch (gStringSwitchMap.of(getName()))
        {
            case /*"rgb"*/0:
                return CssColorParser.parseRgbFunctionColor(getArguments());
            case /*"hsl"*/1:
                return CssColorParser.parseHslFunctionColor(getArguments());
            default:
                return null;
        }
    }

    private static void toCss(String name, CssValueList argument, StringBuilder sb)
    {
        msStringBuilder.append(sb, name);
        sb.append('(');
        msStringBuilder.append(sb, argument.toCss());
        sb.append(')');
    }

    private static String getCss(String name, CssValueList argument)
    {
        StringBuilder sb = new StringBuilder();
        toCss(name, argument, sb);
        return sb.toString();
    }

    /*new*/ String getValue() { return mValue; }

    String getName() { return mName; }

    CssValueList getArguments() { return mArguments; }

    private /*final*/ String mName;
    private /*final*/ CssValueList mArguments;
    private /*final*/ String mValue;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"rgb",
		"hsl"
	);

}

