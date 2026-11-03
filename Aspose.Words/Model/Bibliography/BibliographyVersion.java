// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/07/2024 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;

/// <summary>
/// Calculates <see cref="Bibliography"/> or <see cref="Source"/> version (hashcode).
/// </summary>
/*static*/ class BibliographyVersion
{
	/* Simulation of static class by using private constructor */
	private BibliographyVersion()
	{}

    static int calculate(Bibliography bibliography)
    {
        int version = 0;

        version = calculate(version, bibliography.getBibliographyStyle());

        for (Source source : bibliography.getSources())
            version = calculate(version, calculate(source));

        return version;
    }

    static int calculate(Source source)
    {
        int version = 0;
        version = calculate(version, source.getLcid());
        version = calculate(version, (int)source.getSourceType());
        version = calculate(version, source.getAbbreviatedCaseNumber());
        version = calculate(version, source.getAlbumTitle());
        version = calculate(version, source.getBookTitle());
        version = calculate(version, source.getBroadcaster());
        version = calculate(version, source.getBroadcastTitle());
        version = calculate(version, source.getCaseNumber());
        version = calculate(version, source.getChapterNumber());
        version = calculate(version, source.getCity());
        version = calculate(version, source.getComments());
        version = calculate(version, source.getConferenceName());
        version = calculate(version, source.getCountryOrRegion());
        version = calculate(version, source.getCourt());
        version = calculate(version, source.getDay());
        version = calculate(version, source.getDayAccessed());
        version = calculate(version, source.getDepartment());
        version = calculate(version, source.getDistributor());
        version = calculate(version, source.getEdition());
        version = calculate(version, source.getGuid());
        version = calculate(version, source.getInstitution());
        version = calculate(version, source.getInternetSiteTitle());
        version = calculate(version, source.getIssue());
        version = calculate(version, source.getJournalName());
        version = calculate(version, source.getMedium());
        version = calculate(version, source.getMonth());
        version = calculate(version, source.getMonthAccessed());
        version = calculate(version, source.getNumberVolumes());
        version = calculate(version, source.getPages());
        version = calculate(version, source.getPatentNumber());
        version = calculate(version, source.getPeriodicalTitle());
        version = calculate(version, source.getProductionCompany());
        version = calculate(version, source.getPublicationTitle());
        version = calculate(version, source.getPublisher());
        version = calculate(version, source.getRecordingNumber());
        version = calculate(version, source.getRefOrder());
        version = calculate(version, source.getReporter());
        version = calculate(version, source.getShortTitle());
        version = calculate(version, source.getStandardNumber());
        version = calculate(version, source.getStateOrProvince());
        version = calculate(version, source.getStation());
        version = calculate(version, source.getTag());
        version = calculate(version, source.getTheater());
        version = calculate(version, source.getThesisType());
        version = calculate(version, source.getTitle());
        version = calculate(version, source.getType());
        version = calculate(version, source.getUrl());
        version = calculate(version, source.getVersion());
        version = calculate(version, source.getVolume());
        version = calculate(version, source.getYear());
        version = calculate(version, source.getYearAccessed());
        version = calculate(version, source.getDoi());

        for (Contributor contributor : source.getContributors())
            version = calculate(version, calculate(contributor));

        return version;
    }

    private static int calculate(Contributor contributor)
    {
        Corporate corporate = ms.as(contributor, Corporate.class);
        if (corporate != null)
            return calculate(corporate);

        PersonCollection personCollection = ms.as(contributor, PersonCollection.class);
        if (personCollection != null)
            return calculate(personCollection);

        Debug.fail("Unknown contributor type");
        return 0;
    }

    private static int calculate(Corporate corporate)
    {
        return calculate(0, corporate.getName());
    }

    private static int calculate(PersonCollection contributor)
    {
        int version = 0;

        for (Person person : contributor)
            version = calculate(version, calculate(person));

        return version;
    }

    private static int calculate(Person person)
    {
        int version = 0;

        version = calculate(version, person.getLast());
        version = calculate(version, person.getFirst());
        version = calculate(version, person.getMiddle());

        return version;
    }

    private static int calculate(int version, String value)
    {
        return calculate(version, value != null ? value.hashCode() : 0);
    }

    private static int calculate(int version, int value)
    {
        /*unchecked*/
        {
            return (version * 397) ^ value;
        }
    }
}

