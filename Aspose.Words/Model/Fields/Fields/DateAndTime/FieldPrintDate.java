// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/08/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.DateTimeUtil;


/// <summary>
/// Implements the PRINTDATE field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Retrieves the date and time on which the document was last printed. By default, the Gregorian calendar is used.
/// </remarks>
public class FieldPrintDate extends Field implements IFieldCodeTokenInfoProvider, IFieldWithCalendar
{
    /*override*/ FieldUpdateAction updateCore()
    {
        Document document = fetchDocument();
        /*CalendarType*/int calendarType = FieldDateUtils.resolveCalendarType(this);
        DateTimeConstant value = getResultInternal(document, calendarType);
        return new FieldUpdateActionApplyResult(this, value);
    }

    private static DateTimeConstant getResultInternal(Document document, /*CalendarType*/int calendarType)
    {

        return new DateTimeConstant(
            DateTimeUtil.toLocalTime(document.getBuiltInDocumentProperties().getLastPrintedInternal()),
            calendarType);
    }

    /// <summary>
    /// Gets or sets whether to use the Hijri Lunar or Hebrew Lunar calendar.
    /// </summary>
    public boolean getUseLunarCalendar() { return getFieldCodeCache().hasSwitch(USE_LUNAR_CALENDAR_SWITCH); }
    public void setUseLunarCalendar(boolean value) throws Exception { getFieldCodeCache().setSwitch(USE_LUNAR_CALENDAR_SWITCH, value); }

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
            case /*USE_SAKA_ERA_CALENDAR_SWITCH*/1:
            case /*USE_UM_AL_QURA_CALENDAR_SWITCH*/2:
            {
                return FieldSwitchType.FLAG;
            }
            default:
            {
                return FieldSwitchType.HAS_ARGUMENT;
            }
        }
    }

    private static final String USE_LUNAR_CALENDAR_SWITCH = "\\h";
    private static final String USE_SAKA_ERA_CALENDAR_SWITCH = "\\s";
    private static final String USE_UM_AL_QURA_CALENDAR_SWITCH = "\\u";

    static IFieldInfoResultProvider FieldInfoResultProvider = new PrintDateInfoResultProvider();

    private static class PrintDateInfoResultProvider implements IFieldInfoResultProvider
    {
        public Constant /*IFieldInfoResultProvider.*/getResult(Document document, IFieldCode fieldCode)
        {
            return getResultInternal(document, CalendarType.GREGORIAN);
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\h",
		"\\s",
		"\\u"
	);

}

