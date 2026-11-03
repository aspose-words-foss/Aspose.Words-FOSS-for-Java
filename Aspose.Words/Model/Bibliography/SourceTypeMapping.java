// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/07/2024 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToIntBidirectionalMap;


/// <summary>
/// Serializes and deserializes <see cref="SourceType"/> values.
/// </summary>
/*static*/ class SourceTypeMapping
{
	/* Simulation of static class by using private constructor */
	private SourceTypeMapping()
	{}


    static String enumToString(/*SourceType*/int sourceType)
    {
        return G_MAP.getValue((int)sourceType);
    }

    static /*SourceType*/int stringToEnum(String sourceType)
    {
        return (/*SourceType*/int)G_MAP.getValue(sourceType, (int)SourceType.MISC);
    }

    private static /*final*/ StringToIntBidirectionalMap G_MAP = new StringToIntBidirectionalMap();
    static/* SourceTypeMapping()*/
    {
        G_MAP.addEntry("ArticleInAPeriodical", (int)SourceType.ARTICLE_IN_A_PERIODICAL);
        G_MAP.addEntry("Book", (int)SourceType.BOOK);
        G_MAP.addEntry("BookSection", (int)SourceType.BOOK_SECTION);
        G_MAP.addEntry("JournalArticle", (int)SourceType.JOURNAL_ARTICLE);
        G_MAP.addEntry("ConferenceProceedings", (int)SourceType.CONFERENCE_PROCEEDINGS);
        G_MAP.addEntry("Report", (int)SourceType.REPORT);
        G_MAP.addEntry("SoundRecording", (int)SourceType.SOUND_RECORDING);
        G_MAP.addEntry("Performance", (int)SourceType.PERFORMANCE);
        G_MAP.addEntry("Art", (int)SourceType.ART);
        G_MAP.addEntry("DocumentFromInternetSite", (int)SourceType.DOCUMENT_FROM_INTERNET_SITE);
        G_MAP.addEntry("InternetSite", (int)SourceType.INTERNET_SITE);
        G_MAP.addEntry("Film", (int)SourceType.FILM);
        G_MAP.addEntry("Interview", (int)SourceType.INTERVIEW);
        G_MAP.addEntry("Patent", (int)SourceType.PATENT);
        G_MAP.addEntry("ElectronicSource", (int)SourceType.ELECTRONIC);
        G_MAP.addEntry("Case", (int)SourceType.CASE);
        G_MAP.addEntry("Misc", (int)SourceType.MISC);
    }
}

