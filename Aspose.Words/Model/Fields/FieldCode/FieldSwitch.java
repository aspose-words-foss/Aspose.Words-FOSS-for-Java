// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/08/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;

/// <summary>
/// Represents a switch that may appear in field code.
/// </summary>
class FieldSwitch
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="range"></param>
    /// <param name="name"></param>
    /// <param name="argument"></param>
    FieldSwitch(NodeRange range, String name, FieldArgument argument)
    {
        mRange = range;
        mName = name;
        mArgument = argument;
    }

    /// <summary>
    /// Gets the name of the switch.
    /// </summary>
    String getName() { return mName; };

    private String mName;

    /// <summary>
    /// Gets the invariant (lower case) name of the switch.
    /// </summary>
    String getInvariantName() { return getName().toLowerCase(); }

    /// <summary>
    /// Gets a node range of the field switch (without switch argument).
    /// </summary>
    NodeRange getRange() { return mRange; };

    private NodeRange mRange;

    /// <summary>
    /// Gets the argument of the switch.
    /// </summary>
    FieldArgument getArgument() { return mArgument; };

    private FieldArgument mArgument;

    // WORDSNET-5091 Word works differently for the cases when no switch argument is specified
    // and when an empty switch argument is specified.
    /// <summary>
    /// Gets a boolean value indicating whether switch argument was specified.
    /// </summary>
    boolean hasArgument() { return (getArgument() != null); }

    boolean hasName(String name)
    {
        return StringUtil.equalsIgnoreCase(getName(), name);
    }

    String getArgumentNormalizedText()
    {
        return hasArgument()
            ? getArgument().getNormalizedText()
            : null;
    }

    boolean isPicture()
    {
        return isPictureSwitch(getName());
    }

    boolean isFormatting() { return isGeneralFormatSwitch(getName()); }

    static boolean isPictureSwitch(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case/*FieldFormat.NUMERIC_FORMAT_SWITCH*/0:
            case/*FieldFormat.DATE_TIME_FORMAT_SWITCH*/1:
                return true;
            default:
                return false;
        }
    }

    static boolean isGeneralFormatSwitch(String switchName)
    {
        return FieldFormat.GENERAL_FORMAT_SWITCH.equals(switchName);
    }

    static boolean isFormattingSwitch(String switchName)
    {
        return isPictureSwitch(switchName) || isGeneralFormatSwitch(switchName);
    }

    FieldSwitch deepClone(NodeRange range)
    {
        return new FieldSwitch(range, getName(), getArgument());
    }

    FieldSwitch deepClone(FieldArgument argument)
    {
        return new FieldSwitch(getRange(), getName(), argument);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\#",
		"\\@"
	);

}

