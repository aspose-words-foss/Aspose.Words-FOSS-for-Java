// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/06/2023 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DictionaryExtension;
import com.aspose.SystemPal;
import java.util.HashMap;


/// <summary>
/// Allows to get axis default title text.
/// </summary>
class DefaultAxisTitles
{

    /// <summary>
    /// Returns default axis title text dependent on the current culture.
    /// </summary>
    static String getTitle()
    {
        String result = DictionaryExtension.getValueOrNull(G_TITLES, SystemPal.getCurrentCultureName());
        return (result != null ? result : DEFAULT_TITLE_LABEL);
    }

    private static void prepareTitles()
    {
        G_TITLES.put("en", "Axis Title");                  //English
        G_TITLES.put("en-AU", "Axis Title");               //English (Australia)
        G_TITLES.put("en-BZ", "Axis Title");               //English (Belize)
        G_TITLES.put("en-CA", "Axis Title");               //English (Canada)
        G_TITLES.put("en-IN", "Axis Title");               //English (India)
        G_TITLES.put("en-IE", "Axis Title");               //English (Ireland)
        G_TITLES.put("en-JM", "Axis Title");               //English (Jamaica)
        G_TITLES.put("en-MY", "Axis Title");               //English (Malaysia)
        G_TITLES.put("en-NZ", "Axis Title");               //English (New Zealand)
        G_TITLES.put("en-PH", "Axis Title");               //English (Republic of the Philippines)
        G_TITLES.put("en-SG", "Axis Title");               //English (Singapore)
        G_TITLES.put("en-ZA", "Axis Title");               //English (South Africa)
        G_TITLES.put("en-TT", "Axis Title");               //English (Trinidad and Tobago)
        G_TITLES.put("en-GB", "Axis Title");               //English (United Kingdom)
        G_TITLES.put("en-US", "Axis Title");               //English (United States)
        G_TITLES.put("en-ZW", "Axis Title");               //English (Zimbabwe)

        G_TITLES.put("ja", "軸ラベル");                    //Japanese
        G_TITLES.put("ja-JP", "軸ラベル");                 //Japanese (Japan)
        G_TITLES.put("ru", "Название оси");                // Russian
        G_TITLES.put("ru-RU", "Название оси");             // Russian (Russia)
    }

    private static /*final*/ HashMap<String, String> G_TITLES = new HashMap<String, String>();
    private static final String DEFAULT_TITLE_LABEL = "Axis Title";
    static/* DefaultAxisTitles()*/
    {
        prepareTitles();
    }
}

