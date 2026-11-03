// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/07/2024 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToIntBidirectionalMap;
import com.aspose.words.ref.RefInt;


/// <summary>
/// Serializes and deserializes <see cref="ContributorType"/> values.
/// </summary>
/*static*/ class ContributorTypeMapping
{
	/* Simulation of static class by using private constructor */
	private ContributorTypeMapping()
	{}


    static String enumToString(/*ContributorType*/int contributorType)
    {
        return G_MAP.getValue((int)contributorType);
    }

    static boolean tryStringToEnum(String contributorType, /*out*/RefInt result)
    {
        int intResult = G_MAP.tryGetValue(contributorType);
        result.set((/*ContributorType*/int)intResult);
        return !StringToIntBidirectionalMap.isNullSubstitute(intResult);
    }

    private static /*final*/ StringToIntBidirectionalMap G_MAP = new StringToIntBidirectionalMap();
    static/* ContributorTypeMapping()*/
    {
        G_MAP.addEntry("Artist", (int)ContributorType.ARTIST);
        G_MAP.addEntry("Author", (int)ContributorType.AUTHOR);
        G_MAP.addEntry("BookAuthor", (int)ContributorType.BOOK_AUTHOR);
        G_MAP.addEntry("Compiler", (int)ContributorType.COMPILER);
        G_MAP.addEntry("Composer", (int)ContributorType.COMPOSER);
        G_MAP.addEntry("Conductor", (int)ContributorType.CONDUCTOR);
        G_MAP.addEntry("Counsel", (int)ContributorType.COUNSEL);
        G_MAP.addEntry("Director", (int)ContributorType.DIRECTOR);
        G_MAP.addEntry("Editor", (int)ContributorType.EDITOR);
        G_MAP.addEntry("Interviewee", (int)ContributorType.INTERVIEWEE);
        G_MAP.addEntry("Interviewer", (int)ContributorType.INTERVIEWER);
        G_MAP.addEntry("Inventor", (int)ContributorType.INVENTOR);
        G_MAP.addEntry("Performer", (int)ContributorType.PERFORMER);
        G_MAP.addEntry("ProducerName", (int)ContributorType.PRODUCER);
        G_MAP.addEntry("Translator", (int)ContributorType.TRANSLATOR);
        G_MAP.addEntry("Writer", (int)ContributorType.WRITER);
    }
}

