// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/07/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.FormatterPal;


/// <summary>
/// Implements the DATE field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Inserts the current date and time. By default, the Gregorian calendar is used.
/// </remarks>
public class FieldDate extends Field implements IFieldCodeTokenInfoProvider, IFieldWithCalendar
{
    /*override*/ FieldUpdateAction updateCore()
    {
        /*CalendarType*/int calendarType = FieldDateUtils.resolveCalendarType(this);
        return new FieldUpdateActionApplyResult(this, new DateTimeConstant(fetchDocument().getCurrentDateTimeCache(), calendarType));
    }

    /*override*/ String getDefaultDateTimeFormat()
    {
        // Can't use .NET's standard specifier here as Word does not have them.
        return FormatterPal.getShortDatePatternCurrent();
    }

    /// <summary>
    /// Gets or sets whether to use the Hijri Lunar or Hebrew Lunar calendar.
    /// </summary>
    public boolean getUseLunarCalendar() { return getFieldCodeCache().hasSwitch(USE_LUNAR_CALENDAR_SWITCH); }
    public void setUseLunarCalendar(boolean value) throws Exception { getFieldCodeCache().setSwitch(USE_LUNAR_CALENDAR_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to use a format last used by the hosting application when inserting a new DATE field.
    /// </summary>
    public boolean getUseLastFormat() { return getFieldCodeCache().hasSwitch(USE_LAST_FORMAT_SWITCH); }
    public void setUseLastFormat(boolean value) throws Exception { getFieldCodeCache().setSwitch(USE_LAST_FORMAT_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to use the Saka Era calendar.
    /// </summary>
    public boolean getUseSakaEraCalendar() { return getFieldCodeCache().hasSwitch(USE_SAKA_ERA_CALENDAR_SWITCH); }
    public void setUseSakaEraCalendar(boolean value) throws Exception { getFieldCodeCache().setSwitch(USE_SAKA_ERA_CALENDAR_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to use the Um-al-Qura calendar.
    /// </summary>
    public boolean getUseUmAlQuraCalendar() { return getFieldCodeCache().hasSwitch(USE_UM_AL_QURA_CALENDAR_SWITCH); }
    public void setUseUmAlQuraCalendar(boolean value) throws Exception { getFieldCodeCache().setSwitch(USE_UM_AL_QURA_CALENDAR_SWITCH, value); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*USE_LUNAR_CALENDAR_SWITCH*/0:
            case /*USE_LAST_FORMAT_SWITCH*/1:
            case /*USE_SAKA_ERA_CALENDAR_SWITCH*/2:
            case /*USE_UM_AL_QURA_CALENDAR_SWITCH*/3:
            {
                return FieldSwitchType.FLAG;
            }
            default:
            {
                return FieldSwitchType.UNKNOWN;
            }
        }
    }

    private static final String USE_LUNAR_CALENDAR_SWITCH = "\\h";
    private static final String USE_LAST_FORMAT_SWITCH = "\\l";
    private static final String USE_SAKA_ERA_CALENDAR_SWITCH = "\\s";
    private static final String USE_UM_AL_QURA_CALENDAR_SWITCH = "\\u";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\h",
		"\\l",
		"\\s",
		"\\u"
	);

}

