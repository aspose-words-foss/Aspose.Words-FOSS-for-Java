// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/07/2024 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.AnyXmlReader;
import com.aspose.words.ref.RefInt;


/// <summary>
/// <see cref="Source"/> xml deserializer.
/// </summary>
/*static*/ class SourceReader
{
	/* Simulation of static class by using private constructor */
	private SourceReader()
	{}

    static void read(Source source, String xml)
    {
        AnyXmlReader reader = new AnyXmlReader(xml, null);

        while (reader.readChild("Source"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"AbbreviatedCaseNumber"*/0:
                    source.setAbbreviatedCaseNumber(reader.readString());
                    break;
                case /*"AlbumTitle"*/1:
                    source.setAlbumTitle(reader.readString());
                    break;
                case /*"BookTitle"*/2:
                    source.setBookTitle(reader.readString());
                    break;
                case /*"Broadcaster"*/3:
                    source.setBroadcaster(reader.readString());
                    break;
                case /*"BroadcastTitle"*/4:
                    source.setBroadcastTitle(reader.readString());
                    break;
                case /*"CaseNumber"*/5:
                    source.setCaseNumber(reader.readString());
                    break;
                case /*"ChapterNumber"*/6:
                    source.setChapterNumber(reader.readString());
                    break;
                case /*"City"*/7:
                    source.setCity(reader.readString());
                    break;
                case /*"Comments"*/8:
                    source.setComments(reader.readString());
                    break;
                case /*"ConferenceName"*/9:
                    source.setConferenceName(reader.readString());
                    break;
                case /*"CountryRegion"*/10:
                    source.setCountryOrRegion(reader.readString());
                    break;
                case /*"Court"*/11:
                    source.setCourt(reader.readString());
                    break;
                case /*"Day"*/12:
                    source.setDay(reader.readString());
                    break;
                case /*"DayAccessed"*/13:
                    source.setDayAccessed(reader.readString());
                    break;
                case /*"Department"*/14:
                    source.setDepartment(reader.readString());
                    break;
                case /*"Distributor"*/15:
                    source.setDistributor(reader.readString());
                    break;
                case /*"Edition"*/16:
                    source.setEdition(reader.readString());
                    break;
                case /*"Guid"*/17:
                    source.setGuid(reader.readString());
                    break;
                case /*"Institution"*/18:
                    source.setInstitution(reader.readString());
                    break;
                case /*"InternetSiteTitle"*/19:
                    source.setInternetSiteTitle(reader.readString());
                    break;
                case /*"Issue"*/20:
                    source.setIssue(reader.readString());
                    break;
                case /*"JournalName"*/21:
                    source.setJournalName(reader.readString());
                    break;
                case /*"LCID"*/22:
                    source.setLcid(reader.readString());
                    break;
                case /*"Medium"*/23:
                    source.setMedium(reader.readString());
                    break;
                case /*"Month"*/24:
                    source.setMonth(reader.readString());
                    break;
                case /*"MonthAccessed"*/25:
                    source.setMonthAccessed(reader.readString());
                    break;
                case /*"NumberVolumes"*/26:
                    source.setNumberVolumes(reader.readString());
                    break;
                case /*"Pages"*/27:
                    source.setPages(reader.readString());
                    break;
                case /*"PatentNumber"*/28:
                    source.setPatentNumber(reader.readString());
                    break;
                case /*"PeriodicalTitle"*/29:
                    source.setPeriodicalTitle(reader.readString());
                    break;
                case /*"ProductionCompany"*/30:
                    source.setProductionCompany(reader.readString());
                    break;
                case /*"PublicationTitle"*/31:
                    source.setPublicationTitle(reader.readString());
                    break;
                case /*"Publisher"*/32:
                    source.setPublisher(reader.readString());
                    break;
                case /*"RecordingNumber"*/33:
                    source.setRecordingNumber(reader.readString());
                    break;
                case /*"RefOrder"*/34:
                    source.setRefOrder(reader.readString());
                    break;
                case /*"Reporter"*/35:
                    source.setReporter(reader.readString());
                    break;
                case /*"ShortTitle"*/36:
                    source.setShortTitle(reader.readString());
                    break;
                case /*"StandardNumber"*/37:
                    source.setStandardNumber(reader.readString());
                    break;
                case /*"StateProvince"*/38:
                    source.setStateOrProvince(reader.readString());
                    break;
                case /*"Station"*/39:
                    source.setStation(reader.readString());
                    break;
                case /*"Tag"*/40:
                    source.setTag(reader.readString());
                    break;
                case /*"Theater"*/41:
                    source.setTheater(reader.readString());
                    break;
                case /*"ThesisType"*/42:
                    source.setThesisType(reader.readString());
                    break;
                case /*"Title"*/43:
                    source.setTitle(reader.readString());
                    break;
                case /*"Type"*/44:
                    source.setType(reader.readString());
                    break;
                case /*"URL"*/45:
                    source.setUrl(reader.readString());
                    break;
                case /*"Version"*/46:
                    source.setVersion(reader.readString());
                    break;
                case /*"Volume"*/47:
                    source.setVolume(reader.readString());
                    break;
                case /*"Year"*/48:
                    source.setYear(reader.readString());
                    break;
                case /*"YearAccessed"*/49:
                    source.setYearAccessed(reader.readString());
                    break;
                case /*"DOI"*/50:
                    source.setDoi(reader.readString());
                    break;

                case /*"SourceType"*/51:
                    source.setSourceType(SourceTypeMapping.stringToEnum(reader.readString()));
                    break;
                case /*"Author"*/52:
                    readContributors(reader, source.getContributors());
                    break;

                default:
                    break;
            }
        }
    }

    private static void readContributors(AnyXmlReader reader, ContributorCollection contributors)
    {
        while (reader.readChild("Author"))
        {
            /*ContributorType*/int type = 0;
            RefInt referenceToType = new RefInt(type);
            boolean outRefCondition0 = ContributorTypeMapping.tryStringToEnum(reader.getLocalName(), /*out*/ referenceToType);
            type = referenceToType.get();
            if (outRefCondition0)
            {
                Contributor contributor = readContributor(reader);
                if (contributor != null)
                    contributors.setContributor(type, contributor);
            }
        }
    }

    private static Contributor readContributor(AnyXmlReader reader)
    {
        Contributor contributor = null;

        String localName = reader.getLocalName();
        while (reader.readChild(localName))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"NameList"*/53:
                    contributor = readPersons(reader);
                    break;
                case /*"Corporate"*/54:
                    contributor = readCorporate(reader);
                    break;
                default:
                    break;
            }
        }

        return contributor;
    }

    private static PersonCollection readPersons(AnyXmlReader reader)
    {
        PersonCollection persons = new PersonCollection();

        while (reader.readChild("NameList"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"Person"*/55:
                    persons.add(readPerson(reader));
                    break;
                default:
                    break;
            }
        }

        return persons;
    }

    private static Person readPerson(AnyXmlReader reader)
    {
        String first = null;
        String last = null;
        String middle = null;
        while (reader.readChild("Person"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"Last"*/56:
                    last = reader.readString();
                    break;
                case /*"First"*/57:
                    first = reader.readString();
                    break;
                case /*"Middle"*/58:
                    middle = reader.readString();
                    break;
                default:
                    break;
            }
        }

        return new Person(last, first, middle);
    }

    private static Corporate readCorporate(AnyXmlReader reader)
    {
        return new Corporate(reader.readString());
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"AbbreviatedCaseNumber",
		"AlbumTitle",
		"BookTitle",
		"Broadcaster",
		"BroadcastTitle",
		"CaseNumber",
		"ChapterNumber",
		"City",
		"Comments",
		"ConferenceName",
		"CountryRegion",
		"Court",
		"Day",
		"DayAccessed",
		"Department",
		"Distributor",
		"Edition",
		"Guid",
		"Institution",
		"InternetSiteTitle",
		"Issue",
		"JournalName",
		"LCID",
		"Medium",
		"Month",
		"MonthAccessed",
		"NumberVolumes",
		"Pages",
		"PatentNumber",
		"PeriodicalTitle",
		"ProductionCompany",
		"PublicationTitle",
		"Publisher",
		"RecordingNumber",
		"RefOrder",
		"Reporter",
		"ShortTitle",
		"StandardNumber",
		"StateProvince",
		"Station",
		"Tag",
		"Theater",
		"ThesisType",
		"Title",
		"Type",
		"URL",
		"Version",
		"Volume",
		"Year",
		"YearAccessed",
		"DOI",
		"SourceType",
		"Author",
		"NameList",
		"Corporate",
		"Person",
		"Last",
		"First",
		"Middle"
	);

}

