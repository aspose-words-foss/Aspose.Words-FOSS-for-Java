// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/11/2025 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.LanguageOnly;
import com.aspose.ms.System.Threading.CurrentThread;


/*static*/ class FieldDateUtils
{
	/* Simulation of static class by using private constructor */
	private FieldDateUtils()
	{}

    /// <summary>
    /// Resolves calendar type for specified field.
    /// </summary>
    static <T extends Field & IFieldWithCalendar> /*CalendarType*/int resolveCalendarType(T field)
    {
        if (field.getUseLunarCalendar())
        {
            switch (field.fetchDocument().getFieldOptions().getFieldUpdateCultureSource())
            {
                case FieldUpdateCultureSource.CURRENT_THREAD:
                    return getLunarCalendarViaCurrentThread(field);
                case FieldUpdateCultureSource.FIELD_CODE:
                    return getLunarCalendarViaFieldCode(field);
                default:
                    throw new IllegalArgumentException();
            }
        }

        if (field.getUseSakaEraCalendar())
            return CalendarType.SAKA_ERA;

        if (field.getUseUmAlQuraCalendar())
            return CalendarType.UM_AL_QURA;

        return CalendarType.GREGORIAN;
    }

    private static /*CalendarType*/int getLunarCalendarViaFieldCode(Field field)
    {
        int languageId = field.getFieldCodeCache().getLanguageId();
        int languageIdBi = field.getFieldCodeCache().getLanguageIdBi();

        if (!field.getFieldCodeCache().getBidi() && !LanguageOnly.compare(languageId, LanguageOnly.ENGLISH))
            return CalendarType.GREGORIAN;

        if (LanguageOnly.compare(languageIdBi, LanguageOnly.ARABIC))
            return CalendarType.HIJRI;

        if (LanguageOnly.compare(languageIdBi, LanguageOnly.HEBREW))
            return CalendarType.HEBREW;

        return CalendarType.GREGORIAN;
    }

    private static /*CalendarType*/int getLunarCalendarViaCurrentThread(Field field)
    {
        int languageId = CurrentThread.getCurrentCulture().getLCID();
        int languageIdBi = field.getFieldCodeCache().getLanguageIdBi();

        if (LanguageOnly.compare(languageId, LanguageOnly.ARABIC))
            return CalendarType.HIJRI;

        if (LanguageOnly.compare(languageId, LanguageOnly.HEBREW))
            return CalendarType.HEBREW;

        if (!LanguageOnly.compare(languageId, LanguageOnly.ENGLISH))
            return CalendarType.GREGORIAN;

        if (LanguageOnly.compare(languageIdBi, LanguageOnly.ARABIC))
            return CalendarType.HIJRI;

        if (LanguageOnly.compare(languageIdBi, LanguageOnly.HEBREW))
            return CalendarType.HEBREW;

        return CalendarType.GREGORIAN;
    }
}

