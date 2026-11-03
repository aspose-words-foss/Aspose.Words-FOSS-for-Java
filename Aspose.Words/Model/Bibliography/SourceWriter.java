// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/07/2024 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.AnyXmlBuilder;
import com.aspose.Debug;


/// <summary>
/// <see cref="Source"/> xml serializer.
/// </summary>
/*static*/ class SourceWriter
{
	/* Simulation of static class by using private constructor */
	private SourceWriter()
	{}

    static void write(Source source, AnyXmlBuilder builder)
    {
        builder.startElement("b:Source");

        writeAttributes(source, builder);

        builder.endElement();
    }

    private static void writeAttributes(Source source, AnyXmlBuilder builder)
    {
        builder.writeOptionalElement("b:Tag", source.getTag());
        builder.writeElement("b:SourceType", SourceTypeMapping.enumToString(source.getSourceType()));
        builder.writeOptionalElement("b:LCID", source.getLcid());
        builder.writeOptionalElement("b:Title", source.getTitle());
        builder.writeOptionalElement("b:Year", source.getYear());
        builder.writeOptionalElement("b:City", source.getCity());
        builder.writeOptionalElement("b:Publisher", source.getPublisher());

        writeContributors(source, builder);

        builder.writeOptionalElement("b:StateProvince", source.getStateOrProvince());
        builder.writeOptionalElement("b:CountryRegion", source.getCountryOrRegion());
        builder.writeOptionalElement("b:Volume", source.getVolume());
        builder.writeOptionalElement("b:NumberVolumes", source.getNumberVolumes());
        builder.writeOptionalElement("b:ShortTitle", source.getShortTitle());
        builder.writeOptionalElement("b:StandardNumber", source.getStandardNumber());
        builder.writeOptionalElement("b:Pages", source.getPages());
        builder.writeOptionalElement("b:Edition", source.getEdition());
        builder.writeOptionalElement("b:Comments", source.getComments());
        builder.writeOptionalElement("b:Medium", source.getMedium());
        builder.writeOptionalElement("b:YearAccessed", source.getYearAccessed());
        builder.writeOptionalElement("b:MonthAccessed", source.getMonthAccessed());
        builder.writeOptionalElement("b:DayAccessed", source.getDayAccessed());
        builder.writeOptionalElement("b:URL", source.getUrl());
        builder.writeOptionalElement("b:DOI", source.getDoi());
        builder.writeOptionalElement("b:Guid", source.getGuid());
        builder.writeOptionalElement("b:Department", source.getDepartment());
        builder.writeOptionalElement("b:Institution", source.getInstitution());
        builder.writeOptionalElement("b:ThesisType", source.getThesisType());
        builder.writeOptionalElement("b:CaseNumber", source.getCaseNumber());
        builder.writeOptionalElement("b:Court", source.getCourt());
        builder.writeOptionalElement("b:PeriodicalTitle", source.getPeriodicalTitle());
        builder.writeOptionalElement("b:PublicationTitle", source.getPublicationTitle());
        builder.writeOptionalElement("b:InternetSiteTitle", source.getInternetSiteTitle());
        builder.writeOptionalElement("b:Theater", source.getTheater());
        builder.writeOptionalElement("b:Month", source.getMonth());
        builder.writeOptionalElement("b:Day", source.getDay());
        builder.writeOptionalElement("b:AbbreviatedCaseNumber", source.getAbbreviatedCaseNumber());
        builder.writeOptionalElement("b:AlbumTitle", source.getAlbumTitle());
        builder.writeOptionalElement("b:BookTitle", source.getBookTitle());
        builder.writeOptionalElement("b:Broadcaster", source.getBroadcaster());
        builder.writeOptionalElement("b:BroadcastTitle", source.getBroadcastTitle());
        builder.writeOptionalElement("b:ChapterNumber", source.getChapterNumber());
        builder.writeOptionalElement("b:ConferenceName", source.getConferenceName());
        builder.writeOptionalElement("b:Distributor", source.getDistributor());
        builder.writeOptionalElement("b:Issue", source.getIssue());
        builder.writeOptionalElement("b:JournalName", source.getJournalName());
        builder.writeOptionalElement("b:PatentNumber", source.getPatentNumber());
        builder.writeOptionalElement("b:ProductionCompany", source.getProductionCompany());
        builder.writeOptionalElement("b:RecordingNumber", source.getRecordingNumber());
        builder.writeOptionalElement("b:Reporter", source.getReporter());
        builder.writeOptionalElement("b:Station", source.getStation());
        builder.writeOptionalElement("b:Type", source.getType());
        builder.writeOptionalElement("b:Version", source.getVersion());
        builder.writeOptionalElement("b:RefOrder", source.getRefOrder());
    }

    private static void writeContributors(Source source, AnyXmlBuilder builder)
    {
        if (source.getContributors().isEmpty())
            return;

        builder.startElement("b:Author");

        for (/*ContributorType*/int contributorType : (Iterable</*ContributorType*/Integer>) source.getContributors().getContributorTypes())
        {
            writeContributor(
                source.getContributors().getContributor(contributorType),
                contributorType,
                builder);
        }

        builder.endElement();
    }

    private static void writeContributor(
        Contributor contributor,
        /*ContributorType*/int contributorType,
        AnyXmlBuilder builder)
    {
        builder.startElement("b:" + ContributorTypeMapping.enumToString(contributorType));

        writeContributor(contributor, builder);

        builder.endElement();
    }

    private static void writeContributor(Contributor contributor, AnyXmlBuilder builder)
    {
        Corporate corporate = ms.as(contributor, Corporate.class);
        if (corporate != null)
        {
            writeCorporateContributor(corporate, builder);
            return;
        }

        PersonCollection personCollection = ms.as(contributor, PersonCollection.class);
        if (personCollection != null)
        {
            writePersonCollectionContributor(personCollection, builder);
            return;
        }

        Debug.fail("Unknown contributor type");
    }

    private static void writePersonCollectionContributor(PersonCollection personCollection, AnyXmlBuilder builder)
    {
        builder.startElement("b:NameList");

        for (Person person : personCollection)
            writePersonContributor(person, builder);

        builder.endElement();
    }

    private static void writePersonContributor(Person person, AnyXmlBuilder builder)
    {
        builder.startElement("b:Person");

        builder.writeOptionalElement("b:Last", person.getLast());
        builder.writeOptionalElement("b:First", person.getFirst());
        builder.writeOptionalElement("b:Middle", person.getMiddle());

        builder.endElement();
    }

    private static void writeCorporateContributor(Corporate corporate, AnyXmlBuilder builder)
    {
        builder.writeElement("b:Corporate", corporate.getName());
    }
}

