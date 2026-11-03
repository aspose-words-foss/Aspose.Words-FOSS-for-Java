// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/08/2018 by Victor Moskvitin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.SystemPal;
import com.aspose.ms.System.msString;
import java.util.HashMap;


/*static*/ class DefaultChartTitles
{
	/* Simulation of static class by using private constructor */
	private DefaultChartTitles()
	{}


    /// <summary>
    /// Returns the default chart title text based on the current culture.
    /// </summary>
    static String getTitle()
    {
        String cultureName = SystemPal.getCurrentCultureName().toLowerCase();

        if (cultureName == null)
            return ENGLISH_TITLE_LABEL;

        if (G_TITLES.containsKey(cultureName))
            return G_TITLES.get(cultureName);

        String langOnly = msString.split(cultureName, G_SEPARATOR)[0].toLowerCase();

        if (G_TITLES.containsKey(langOnly))
            return G_TITLES.get(langOnly);

        return ENGLISH_TITLE_LABEL;
    }
   
    private static /*final*/ HashMap<String, String> G_TITLES =
        new HashMap<String, String>();
    private static /*final*/ char[] G_SEPARATOR = new char[] { '-', '_' };
    private static final String ENGLISH_TITLE_LABEL = "Chart Title";
    static/* DefaultChartTitles()*/
    {
        msDictionary.add(G_TITLES, "en", ENGLISH_TITLE_LABEL);    // English
        msDictionary.add(G_TITLES, "ru", "Название диаграммы"); // Russian
        msDictionary.add(G_TITLES, "ja", "グラフ タイトル");     // Japanese
        msDictionary.add(G_TITLES, "zh", "图表标题");           // ChineseChina
        msDictionary.add(G_TITLES, "zh-cn", "图表标题");        // ChineseChina
        msDictionary.add(G_TITLES, "zh-tw", "圖表標題");        // ChineseTaiwan
        msDictionary.add(G_TITLES, "fr", "Titre du graphique");// French
        msDictionary.add(G_TITLES, "es", "Título del gráfico");// Spanish
        msDictionary.add(G_TITLES, "pt", "Título do gráfico"); // Portuguese
    }
}

