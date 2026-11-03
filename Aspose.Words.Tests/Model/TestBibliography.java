// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2023 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import com.aspose.NullableInt32;
import com.aspose.NullableExtension;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests Bibliography facade.
/// </summary>
@Test
public class TestBibliography
{
    @BeforeClass (alwaysRun = true)
    public void testFixtureSetUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void testBibliographySources() throws Exception
    {
        Document document = TestUtil.open("Model\\Bibliography\\TestBibliographySources.docx");

        msAssert.areEqual(12, document.getBibliography().getSources().size());

        ArrayList<Source> sources = document.getBibliography().getSources();

        assertSource(
            sources.get(0),
            "BookNoLCID",
            "Book 0 (No LCID)",
            null,
            NullableInt32.NULL,
            "Tejeda Roxanne Brielle",
            "Batchelor Kristopher Jaime",
            "Chaffin Tavian Kristopher",
            "Esquivel Dominic Braiden",
            "Brien Debra Janell",
            "David Priscilla Yazmin"
        );
        assertSource(
            sources.get(1),
            "BookSectionRuRu",
            "Book Section 1 (ru-RU)",
            "ru-RU",
            NullableExtension.asNullable(1049),
            "Rossi Jeniffer Daisy",
            "Camp Braxton Julio",
            "Wolford Asia Karleigh",
            "Gooch Carina Jaylen"
        );
        assertSource(sources.get(2), "BookEnUs", "Book 2 (en-US)", "en-US", NullableExtension.asNullable(1033), "Aspose LTD");
        assertSource(sources.get(3), "BookEnNz", "Book 3 (en-NZ)", "en-NZ", NullableExtension.asNullable(5129));
        assertSource(sources.get(4), "Book1049", "Book 4 (1049)", "1049", NullableExtension.asNullable(1049));
        assertSource(sources.get(5), "Book1033", "Book 5 (1033)", "1033", NullableExtension.asNullable(1033));
        assertSource(sources.get(6), "Book5129", "Book 6 (5129)", "5129", NullableExtension.asNullable(5129));
        assertSource(sources.get(7), "BookInvalidStringLCID", "Book 7 (Invalid string LCID)", "Blah", NullableInt32.NULL);
        assertSource(sources.get(8), "BookInvalidNumberLCID", "Book 8 (Invalid number LCID)", "54321",NullableInt32.NULL);
        assertSource(sources.get(9), "BookOverflowNumberLCID", "Book 9 (Overflow number LCID)", "123456", NullableInt32.NULL);
        assertSource(sources.get(10), "BookNegativeNumberLCID", "Book 10 (Negative number LCID)", "-1", NullableInt32.NULL);
    }

    @Test
    public void testBibliographyMultipleCustomParts() throws Exception
    {
        Document document = TestUtil.open("Model\\Bibliography\\TestBibliographyMultipleCustomParts.docx");
        Bibliography bibliography = document.getBibliography();

        msAssert.areEqual("\\MLASeventhEditionOfficeOnline.xsl", bibliography.getBibliographyStyle());
        msAssert.areEqual(1, bibliography.getSources().size());
        assertSource(bibliography.getSources().get(0), "Book2", "Book 2", null, NullableInt32.NULL);
    }

    private static void assertSource(
        Source source,
        String tag,
        String title,
        String lcid,
        NullableInt32 normalizedLcid,
        /*params*/ String... names)
    {
        msAssert.areEqual(tag, source.getTag());
        msAssert.areEqual(title, source.getTitle());
        msAssert.areEqual(lcid, source.getLcid());
        msAssert.areEqual(normalizedLcid.hasValue(), source.hasNormalizedLcid());
        if (normalizedLcid.hasValue())
            msAssert.areEqual(normalizedLcid.getValue(), source.getNormalizedLcid());

        ArrayList<String> actualNames = new ArrayList<String>();
        for (Contributor contributor : source.getContributors())
        {
            Corporate corporate = ms.as(contributor, Corporate.class);
            if (corporate != null)
                msArrayList.add(actualNames, corporate.getName());

            PersonCollection persons = ms.as(contributor, PersonCollection.class);
            if (persons != null)
            {
                for (Person person : persons)
                    msArrayList.add(actualNames, msString.format("{0} {1} {2}", person.getLast(), person.getFirst(), person.getMiddle()));
            }
        }

        msAssert.areEqual(names, actualNames);
    }

    @Test
    public void testBibliographySource() throws Exception
    {
        Document document = TestUtil.open("Model\\Bibliography\\TestBibliographySources.docx");

        Source source = document.getBibliography().getSources().get(11);

        msAssert.areEqual("Aspose", source.getTag());
        msAssert.areEqual("ABC", source.getAbbreviatedCaseNumber());
        msAssert.areEqual("Heart Of Seasons", source.getAlbumTitle());
        msAssert.areEqual("10,000 Hours In View", source.getBookTitle());
        msAssert.areEqual("RadiantEcho TV", source.getBroadcaster());
        msAssert.areEqual("RETV", source.getBroadcastTitle());
        msAssert.areEqual("ABC-2023-456789", source.getCaseNumber());
        msAssert.areEqual("17", source.getChapterNumber());
        msAssert.areEqual("Atlanta", source.getCity());
        msAssert.areEqual("no comments", source.getComments());
        msAssert.areEqual("InnovateCon Global Summit", source.getConferenceName());
        msAssert.areEqual("USA", source.getCountryOrRegion());
        msAssert.areEqual("Liberty Judicial Circuit Court", source.getCourt());
        msAssert.areEqual("3", source.getDay());
        msAssert.areEqual("15", source.getDayAccessed());
        msAssert.areEqual("Strategic Planning and Development Department", source.getDepartment());
        msAssert.areEqual("Pinnacle Distributors, Inc", source.getDistributor());
        msAssert.areEqual("3rd", source.getEdition());
        msAssert.areEqual("{A621825C-D9C4-42A8-A2A2-2BC2C765E417}", source.getGuid());
        msAssert.areEqual("Harmony Institute of Advanced Studies", source.getInstitution());
        msAssert.areEqual("TechInsider Blog", source.getInternetSiteTitle());
        msAssert.areEqual("Volume 25, Number 2", source.getIssue());
        msAssert.areEqual("Scientific Advances", source.getJournalName());
        msAssert.areEqual("en-US", source.getLcid());
        msAssert.areEqual("Document", source.getMedium());
        msAssert.areEqual("November", source.getMonth());
        msAssert.areEqual("6", source.getMonthAccessed());
        msAssert.areEqual("X", source.getNumberVolumes());
        msAssert.areEqual("197", source.getPages());
        msAssert.areEqual("US9876543", source.getPatentNumber());
        msAssert.areEqual("Nature Reviews Neuroscience", source.getPeriodicalTitle());
        msAssert.areEqual("Stellar Studios", source.getProductionCompany());
        msAssert.areEqual("Data Science Today", source.getPublicationTitle());
        msAssert.areEqual("Pantheon Media", source.getPublisher());
        msAssert.areEqual("RD789012", source.getRecordingNumber());
        msAssert.areEqual("1", source.getRefOrder());
        msAssert.areEqual("Alice Johnson", source.getReporter());
        msAssert.areEqual(SourceType.BOOK, source.getSourceType());
        msAssert.areEqual("Manual", source.getShortTitle());
        msAssert.areEqual("0-5245-5105-7", source.getStandardNumber());
        msAssert.areEqual("Georgia", source.getStateOrProvince());
        msAssert.areEqual("TechTalk Radio", source.getStation());
        msAssert.areEqual("Aspose", source.getTag());
        msAssert.areEqual("City Arts Theater", source.getTheater());
        msAssert.areEqual("Ph.D. Thesis", source.getThesisType());
        msAssert.areEqual("Aspose User Manual", source.getTitle());
        msAssert.areEqual("Video Documentary", source.getType());
        msAssert.areEqual("http://aspose.com", source.getUrl());
        msAssert.areEqual("2.1", source.getVersion());
        msAssert.areEqual("VI", source.getVolume());
        msAssert.areEqual("2022", source.getYear());
        msAssert.areEqual("2023", source.getYearAccessed());

        ContributorCollection contributors = source.getContributors();

        assertPersonsContributor(contributors.getArtist(), "Doe John A.");
        assertCorporateContributor(contributors.getAuthor(), "Aspose Inc");
        assertPersonsContributor(contributors.getBookAuthor(), "Smith Emma L.");
        assertPersonsContributor(contributors.getCompiler(), "Johnson Michael R.");
        assertPersonsContributor(contributors.getComposer(), "Williams Christopher S.");
        assertPersonsContributor(contributors.getConductor(), "Anderson David J.");
        assertPersonsContributor(contributors.getCounsel(), "Miller Olivia K.");
        assertPersonsContributor(contributors.getDirector(), "Clark Robert P.");
        assertPersonsContributor(contributors.getEditor(), "Ryder Demophon Tinashe", "Samara Sargon Shahid");
        assertPersonsContributor(contributors.getInterviewee(), "White Jennifer M.");
        assertPersonsContributor(contributors.getInterviewer(), "Johnson Andrew S.");
        assertPersonsContributor(contributors.getInventor(), "Adams Richard E.");
        assertCorporateContributor(contributors.getPerformer(), "Harmony Arts Collective");
        assertPersonsContributor(contributors.getProducer(), "Turner Linda B.");
        assertPersonsContributor(contributors.getTranslator(), "Avellino Chagatai Zoe", "Aliev Deo Vojta", "Hume Otto Somerled");
        assertPersonsContributor(contributors.getWriter(), "Anderson William F.");
    }

    private static void assertCorporateContributor(Contributor contributor, String name)
    {
        Corporate corporate = (Corporate)contributor;
        msAssert.areEqual(name, corporate.getName());
    }

    private static void assertPersonsContributor(Contributor contributor, /*params*/ String... names)
    {
        ArrayList<String> actualNames = new ArrayList<String>();
        PersonCollection persons = (PersonCollection)contributor;
        for (Person person : persons)
            msArrayList.add(actualNames, msString.format("{0} {1} {2}", person.getLast(), person.getFirst(), person.getMiddle()));
        msAssert.areEqual(names, actualNames);
    }

    private static void populateBibliography(Document document) throws Exception
    {
        document.getBibliography().setBibliographyStyle("\\APASixthEditionOfficeOnline.xsl");

        Source source;
        PersonCollection contributor;

        //>>>>>>>> #region  Art

        source = new Source("Art", SourceType.ART);
        source.setLcid("1033");
        source.setCity("Mumbai");
        source.setComments("no comments");
        source.setCountryOrRegion("China");
        source.setDayAccessed("14");
        source.setEdition("1");
        source.setGuid("{2079AD07-875B-47C2-BD12-EF0C2A277A68}");
        source.setInstitution("Louvre Museum");
        source.setMedium("Journal");
        source.setMonthAccessed("September");
        source.setNumberVolumes("IX");
        source.setPages("288");
        source.setPublicationTitle("The Song of the Siren");
        source.setPublisher("Wiley");
        source.setRefOrder("9");
        source.setShortTitle("The Song of the Siren");
        source.setStandardNumber("978-0-307-72037-2");
        source.setStateOrProvince("Michigan");
        source.setTitle("The Song of the Siren");
        source.setUrl("https://www.spotify.com");
        source.setVolume("IX");
        source.setYear("2001");
        source.setYearAccessed("1995");
        source.setDoi("10.9999/ghij.2222");
        contributor = new PersonCollection();
        contributor.add(new Person("Madelaine", "Herbert", "Gwyneth"));
        contributor.add(new Person("Linda", "Wardell", "Wren"));
        source.getContributors().setArtist(contributor);
        document.getBibliography().getSources().add(source);

        //<<<<<<<< #endregion 

        //>>>>>>>> #region  ArticleInAPeriodical

        source = new Source("ArticleInAPeriodical", SourceType.ARTICLE_IN_A_PERIODICAL);
        source.setLcid("1033");
        source.setCity("New York City");
        source.setComments("no comments");
        source.setCountryOrRegion("United States");
        source.setDay("4");
        source.setDayAccessed("5");
        source.setEdition("1");
        source.setGuid("{624F5422-0B8F-4E5B-912A-0BF706D44B37}");
        source.setMedium("Hardcover");
        source.setMonth("June");
        source.setMonthAccessed("January");
        source.setNumberVolumes("I");
        source.setPages("304");
        source.setPeriodicalTitle("The Last Puzzle Piece");
        source.setPublisher("Penguin Random House");
        source.setRefOrder("1");
        source.setShortTitle("The Last Puzzle Piece");
        source.setStandardNumber("978-0-306-40615-7");
        source.setStateOrProvince("California");
        source.setTitle("The Last Puzzle Piece");
        source.setUrl("https://www.example.com");
        source.setVolume("I");
        source.setYear("2005");
        source.setYearAccessed("2005");
        source.setDoi("10.1234/abcd.5678");
        contributor = new PersonCollection();
        contributor.add(new Person("Emma", "Grace", "Smith"));
        contributor.add(new Person("Liam", "Michael", "Johnson"));
        source.getContributors().setArtist(contributor);
        contributor = new PersonCollection();
        contributor.add(new Person("Lanford", "Corey", "Estella"));
        contributor.add(new Person("Acacia", "Lacy", "Legacy"));
        source.getContributors().setAuthor(contributor);
        contributor = new PersonCollection();
        contributor.add(new Person("Anima", "Dax", "Journee"));
        contributor.add(new Person("Denver", "Kaelyn", "Kristine"));
        source.getContributors().setEditor(contributor);
        document.getBibliography().getSources().add(source);

        //<<<<<<<< #endregion 

        //>>>>>>>> #region  Book

        source = new Source("Book", SourceType.BOOK);
        source.setLcid("1033");
        source.setCity("London");
        source.setComments("no comments");
        source.setCountryOrRegion("Canada");
        source.setDayAccessed("17");
        source.setEdition("2");
        source.setGuid("{46464485-34F1-4892-8828-D055C5630471}");
        source.setMedium("Paperback");
        source.setMonthAccessed("February");
        source.setNumberVolumes("II");
        source.setPages("432");
        source.setPublisher("HarperCollins");
        source.setRefOrder("2");
        source.setShortTitle("Whispers of the Moon");
        source.setStandardNumber("978-0-553-38215-9");
        source.setStateOrProvince("New York");
        source.setTitle("Whispers of the Moon");
        source.setUrl("https://www.openai.org");
        source.setVolume("II");
        source.setYear("1998");
        source.setYearAccessed("2012");
        source.setDoi("10.9876/efgh.5432");
        contributor = new PersonCollection();
        contributor.add(new Person("Connor", "Hazel", "Destiny"));
        contributor.add(new Person("Keeley", "Mariah", "Freeman"));
        source.getContributors().setAuthor(contributor);
        contributor = new PersonCollection();
        contributor.add(new Person("Ora", "Breanna", "Amabel"));
        contributor.add(new Person("Rosemary", "Aylmer", "Monday"));
        source.getContributors().setEditor(contributor);
        contributor = new PersonCollection();
        contributor.add(new Person("Suzanna", "Wilkie", "Lita"));
        contributor.add(new Person("Cyrus", "Charmaine", "Levi"));
        source.getContributors().setTranslator(contributor);
        document.getBibliography().getSources().add(source);

        //<<<<<<<< #endregion 
    }

    private static void assertBibliography(Document document) throws Exception
    {
        msAssert.areEqual("\\APASixthEditionOfficeOnline.xsl", document.getBibliography().getBibliographyStyle());
        msAssert.areEqual("APA", document.getBibliography().getStyleName());
        msAssert.areEqual("6", document.getBibliography().getVersion());

        Source source;
        Contributor contributor;
        PersonCollection personCollection;

        source = document.getBibliography().getSources().get(0);
        msAssert.areEqual("Art", source.getTag());
        msAssert.areEqual("1033", source.getLcid());
        msAssert.isNull(source.getAbbreviatedCaseNumber());
        msAssert.isNull(source.getAlbumTitle());
        msAssert.isNull(source.getBookTitle());
        msAssert.isNull(source.getBroadcaster());
        msAssert.isNull(source.getBroadcastTitle());
        msAssert.isNull(source.getCaseNumber());
        msAssert.isNull(source.getChapterNumber());
        msAssert.areEqual("Mumbai", source.getCity());
        msAssert.areEqual("no comments", source.getComments());
        msAssert.isNull(source.getConferenceName());
        msAssert.areEqual("China", source.getCountryOrRegion());
        msAssert.isNull(source.getCourt());
        msAssert.isNull(source.getDay());
        msAssert.areEqual("14", source.getDayAccessed());
        msAssert.isNull(source.getDepartment());
        msAssert.isNull(source.getDistributor());
        msAssert.areEqual("1", source.getEdition());
        msAssert.areEqual("{2079AD07-875B-47C2-BD12-EF0C2A277A68}", source.getGuid());
        msAssert.areEqual("Louvre Museum", source.getInstitution());
        msAssert.isNull(source.getInternetSiteTitle());
        msAssert.isNull(source.getIssue());
        msAssert.isNull(source.getJournalName());
        msAssert.areEqual("Journal", source.getMedium());
        msAssert.isNull(source.getMonth());
        msAssert.areEqual("September", source.getMonthAccessed());
        msAssert.areEqual("IX", source.getNumberVolumes());
        msAssert.areEqual("288", source.getPages());
        msAssert.isNull(source.getPatentNumber());
        msAssert.isNull(source.getPeriodicalTitle());
        msAssert.isNull(source.getProductionCompany());
        msAssert.areEqual("The Song of the Siren", source.getPublicationTitle());
        msAssert.areEqual("Wiley", source.getPublisher());
        msAssert.isNull(source.getRecordingNumber());
        msAssert.areEqual("9", source.getRefOrder());
        msAssert.isNull(source.getReporter());
        msAssert.areEqual("The Song of the Siren", source.getShortTitle());
        msAssert.areEqual("978-0-307-72037-2", source.getStandardNumber());
        msAssert.areEqual("Michigan", source.getStateOrProvince());
        msAssert.isNull(source.getStation());
        msAssert.isNull(source.getTheater());
        msAssert.isNull(source.getThesisType());
        msAssert.areEqual("The Song of the Siren", source.getTitle());
        msAssert.isNull(source.getType());
        msAssert.areEqual("https://www.spotify.com", source.getUrl());
        msAssert.isNull(source.getVersion());
        msAssert.areEqual("IX", source.getVolume());
        msAssert.areEqual("2001", source.getYear());
        msAssert.areEqual("1995", source.getYearAccessed());
        msAssert.areEqual("10.9999/ghij.2222", source.getDoi());
        contributor = source.getContributors().getArtist();
        msAssert.isInstanceOf(PersonCollection.class, contributor);
        personCollection = (PersonCollection)contributor;
        msAssert.areEqual(2, personCollection.getCount());
        msAssert.areEqual("Madelaine", personCollection.get(0).getLast());
        msAssert.areEqual("Herbert", personCollection.get(0).getFirst());
        msAssert.areEqual("Gwyneth", personCollection.get(0).getMiddle());
        msAssert.areEqual("Linda", personCollection.get(1).getLast());
        msAssert.areEqual("Wardell", personCollection.get(1).getFirst());
        msAssert.areEqual("Wren", personCollection.get(1).getMiddle());
        msAssert.isNull(source.getContributors().getAuthor());
        msAssert.isNull(source.getContributors().getBookAuthor());
        msAssert.isNull(source.getContributors().getCompiler());
        msAssert.isNull(source.getContributors().getComposer());
        msAssert.isNull(source.getContributors().getConductor());
        msAssert.isNull(source.getContributors().getCounsel());
        msAssert.isNull(source.getContributors().getDirector());
        msAssert.isNull(source.getContributors().getEditor());
        msAssert.isNull(source.getContributors().getInterviewee());
        msAssert.isNull(source.getContributors().getInterviewer());
        msAssert.isNull(source.getContributors().getInventor());
        msAssert.isNull(source.getContributors().getPerformer());
        msAssert.isNull(source.getContributors().getProducer());
        msAssert.isNull(source.getContributors().getTranslator());
        msAssert.isNull(source.getContributors().getWriter());

        source = document.getBibliography().getSources().get(1);
        msAssert.areEqual("ArticleInAPeriodical", source.getTag());
        msAssert.areEqual("1033", source.getLcid());
        msAssert.isNull(source.getAbbreviatedCaseNumber());
        msAssert.isNull(source.getAlbumTitle());
        msAssert.isNull(source.getBookTitle());
        msAssert.isNull(source.getBroadcaster());
        msAssert.isNull(source.getBroadcastTitle());
        msAssert.isNull(source.getCaseNumber());
        msAssert.isNull(source.getChapterNumber());
        msAssert.areEqual("New York City", source.getCity());
        msAssert.areEqual("no comments", source.getComments());
        msAssert.isNull(source.getConferenceName());
        msAssert.areEqual("United States", source.getCountryOrRegion());
        msAssert.isNull(source.getCourt());
        msAssert.areEqual("4", source.getDay());
        msAssert.areEqual("5", source.getDayAccessed());
        msAssert.isNull(source.getDepartment());
        msAssert.isNull(source.getDistributor());
        msAssert.areEqual("1", source.getEdition());
        msAssert.areEqual("{624F5422-0B8F-4E5B-912A-0BF706D44B37}", source.getGuid());
        msAssert.isNull(source.getInstitution());
        msAssert.isNull(source.getInternetSiteTitle());
        msAssert.isNull(source.getIssue());
        msAssert.isNull(source.getJournalName());
        msAssert.areEqual("Hardcover", source.getMedium());
        msAssert.areEqual("June", source.getMonth());
        msAssert.areEqual("January", source.getMonthAccessed());
        msAssert.areEqual("I", source.getNumberVolumes());
        msAssert.areEqual("304", source.getPages());
        msAssert.isNull(source.getPatentNumber());
        msAssert.areEqual("The Last Puzzle Piece", source.getPeriodicalTitle());
        msAssert.isNull(source.getProductionCompany());
        msAssert.isNull(source.getPublicationTitle());
        msAssert.areEqual("Penguin Random House", source.getPublisher());
        msAssert.isNull(source.getRecordingNumber());
        msAssert.areEqual("1", source.getRefOrder());
        msAssert.isNull(source.getReporter());
        msAssert.areEqual("The Last Puzzle Piece", source.getShortTitle());
        msAssert.areEqual("978-0-306-40615-7", source.getStandardNumber());
        msAssert.areEqual("California", source.getStateOrProvince());
        msAssert.isNull(source.getStation());
        msAssert.isNull(source.getTheater());
        msAssert.isNull(source.getThesisType());
        msAssert.areEqual("The Last Puzzle Piece", source.getTitle());
        msAssert.isNull(source.getType());
        msAssert.areEqual("https://www.example.com", source.getUrl());
        msAssert.isNull(source.getVersion());
        msAssert.areEqual("I", source.getVolume());
        msAssert.areEqual("2005", source.getYear());
        msAssert.areEqual("2005", source.getYearAccessed());
        msAssert.areEqual("10.1234/abcd.5678", source.getDoi());
        contributor = source.getContributors().getArtist();
        msAssert.isInstanceOf(PersonCollection.class, contributor);
        personCollection = (PersonCollection)contributor;
        msAssert.areEqual(2, personCollection.getCount());
        msAssert.areEqual("Emma", personCollection.get(0).getLast());
        msAssert.areEqual("Grace", personCollection.get(0).getFirst());
        msAssert.areEqual("Smith", personCollection.get(0).getMiddle());
        msAssert.areEqual("Liam", personCollection.get(1).getLast());
        msAssert.areEqual("Michael", personCollection.get(1).getFirst());
        msAssert.areEqual("Johnson", personCollection.get(1).getMiddle());
        contributor = source.getContributors().getAuthor();
        msAssert.isInstanceOf(PersonCollection.class, contributor);
        personCollection = (PersonCollection)contributor;
        msAssert.areEqual(2, personCollection.getCount());
        msAssert.areEqual("Lanford", personCollection.get(0).getLast());
        msAssert.areEqual("Corey", personCollection.get(0).getFirst());
        msAssert.areEqual("Estella", personCollection.get(0).getMiddle());
        msAssert.areEqual("Acacia", personCollection.get(1).getLast());
        msAssert.areEqual("Lacy", personCollection.get(1).getFirst());
        msAssert.areEqual("Legacy", personCollection.get(1).getMiddle());
        msAssert.isNull(source.getContributors().getBookAuthor());
        msAssert.isNull(source.getContributors().getCompiler());
        msAssert.isNull(source.getContributors().getComposer());
        msAssert.isNull(source.getContributors().getConductor());
        msAssert.isNull(source.getContributors().getCounsel());
        msAssert.isNull(source.getContributors().getDirector());
        contributor = source.getContributors().getEditor();
        msAssert.isInstanceOf(PersonCollection.class, contributor);
        personCollection = (PersonCollection)contributor;
        msAssert.areEqual(2, personCollection.getCount());
        msAssert.areEqual("Anima", personCollection.get(0).getLast());
        msAssert.areEqual("Dax", personCollection.get(0).getFirst());
        msAssert.areEqual("Journee", personCollection.get(0).getMiddle());
        msAssert.areEqual("Denver", personCollection.get(1).getLast());
        msAssert.areEqual("Kaelyn", personCollection.get(1).getFirst());
        msAssert.areEqual("Kristine", personCollection.get(1).getMiddle());
        msAssert.isNull(source.getContributors().getInterviewee());
        msAssert.isNull(source.getContributors().getInterviewer());
        msAssert.isNull(source.getContributors().getInventor());
        msAssert.isNull(source.getContributors().getPerformer());
        msAssert.isNull(source.getContributors().getProducer());
        msAssert.isNull(source.getContributors().getTranslator());
        msAssert.isNull(source.getContributors().getWriter());

        source = document.getBibliography().getSources().get(2);
        msAssert.areEqual("Book", source.getTag());
        msAssert.areEqual("1033", source.getLcid());
        msAssert.isNull(source.getAbbreviatedCaseNumber());
        msAssert.isNull(source.getAlbumTitle());
        msAssert.isNull(source.getBookTitle());
        msAssert.isNull(source.getBroadcaster());
        msAssert.isNull(source.getBroadcastTitle());
        msAssert.isNull(source.getCaseNumber());
        msAssert.isNull(source.getChapterNumber());
        msAssert.areEqual("London", source.getCity());
        msAssert.areEqual("no comments", source.getComments());
        msAssert.isNull(source.getConferenceName());
        msAssert.areEqual("Canada", source.getCountryOrRegion());
        msAssert.isNull(source.getCourt());
        msAssert.isNull(source.getDay());
        msAssert.areEqual("17", source.getDayAccessed());
        msAssert.isNull(source.getDepartment());
        msAssert.isNull(source.getDistributor());
        msAssert.areEqual("2", source.getEdition());
        msAssert.areEqual("{46464485-34F1-4892-8828-D055C5630471}", source.getGuid());
        msAssert.isNull(source.getInstitution());
        msAssert.isNull(source.getInternetSiteTitle());
        msAssert.isNull(source.getIssue());
        msAssert.isNull(source.getJournalName());
        msAssert.areEqual("Paperback", source.getMedium());
        msAssert.isNull(source.getMonth());
        msAssert.areEqual("February", source.getMonthAccessed());
        msAssert.areEqual("II", source.getNumberVolumes());
        msAssert.areEqual("432", source.getPages());
        msAssert.isNull(source.getPatentNumber());
        msAssert.isNull(source.getPeriodicalTitle());
        msAssert.isNull(source.getProductionCompany());
        msAssert.isNull(source.getPublicationTitle());
        msAssert.areEqual("HarperCollins", source.getPublisher());
        msAssert.isNull(source.getRecordingNumber());
        msAssert.areEqual("2", source.getRefOrder());
        msAssert.isNull(source.getReporter());
        msAssert.areEqual("Whispers of the Moon", source.getShortTitle());
        msAssert.areEqual("978-0-553-38215-9", source.getStandardNumber());
        msAssert.areEqual("New York", source.getStateOrProvince());
        msAssert.isNull(source.getStation());
        msAssert.isNull(source.getTheater());
        msAssert.isNull(source.getThesisType());
        msAssert.areEqual("Whispers of the Moon", source.getTitle());
        msAssert.isNull(source.getType());
        msAssert.areEqual("https://www.openai.org", source.getUrl());
        msAssert.isNull(source.getVersion());
        msAssert.areEqual("II", source.getVolume());
        msAssert.areEqual("1998", source.getYear());
        msAssert.areEqual("2012", source.getYearAccessed());
        msAssert.areEqual("10.9876/efgh.5432", source.getDoi());
        msAssert.isNull(source.getContributors().getArtist());
        contributor = source.getContributors().getAuthor();
        msAssert.isInstanceOf(PersonCollection.class, contributor);
        personCollection = (PersonCollection)contributor;
        msAssert.areEqual(2, personCollection.getCount());
        msAssert.areEqual("Connor", personCollection.get(0).getLast());
        msAssert.areEqual("Hazel", personCollection.get(0).getFirst());
        msAssert.areEqual("Destiny", personCollection.get(0).getMiddle());
        msAssert.areEqual("Keeley", personCollection.get(1).getLast());
        msAssert.areEqual("Mariah", personCollection.get(1).getFirst());
        msAssert.areEqual("Freeman", personCollection.get(1).getMiddle());
        msAssert.isNull(source.getContributors().getBookAuthor());
        msAssert.isNull(source.getContributors().getCompiler());
        msAssert.isNull(source.getContributors().getComposer());
        msAssert.isNull(source.getContributors().getConductor());
        msAssert.isNull(source.getContributors().getCounsel());
        msAssert.isNull(source.getContributors().getDirector());
        contributor = source.getContributors().getEditor();
        msAssert.isInstanceOf(PersonCollection.class, contributor);
        personCollection = (PersonCollection)contributor;
        msAssert.areEqual(2, personCollection.getCount());
        msAssert.areEqual("Ora", personCollection.get(0).getLast());
        msAssert.areEqual("Breanna", personCollection.get(0).getFirst());
        msAssert.areEqual("Amabel", personCollection.get(0).getMiddle());
        msAssert.areEqual("Rosemary", personCollection.get(1).getLast());
        msAssert.areEqual("Aylmer", personCollection.get(1).getFirst());
        msAssert.areEqual("Monday", personCollection.get(1).getMiddle());
        msAssert.isNull(source.getContributors().getInterviewee());
        msAssert.isNull(source.getContributors().getInterviewer());
        msAssert.isNull(source.getContributors().getInventor());
        msAssert.isNull(source.getContributors().getPerformer());
        msAssert.isNull(source.getContributors().getProducer());
        contributor = source.getContributors().getTranslator();
        msAssert.isInstanceOf(PersonCollection.class, contributor);
        personCollection = (PersonCollection)contributor;
        msAssert.areEqual(2, personCollection.getCount());
        msAssert.areEqual("Suzanna", personCollection.get(0).getLast());
        msAssert.areEqual("Wilkie", personCollection.get(0).getFirst());
        msAssert.areEqual("Lita", personCollection.get(0).getMiddle());
        msAssert.areEqual("Cyrus", personCollection.get(1).getLast());
        msAssert.areEqual("Charmaine", personCollection.get(1).getFirst());
        msAssert.areEqual("Levi", personCollection.get(1).getMiddle());
        msAssert.isNull(source.getContributors().getWriter());
    }
}

