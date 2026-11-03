// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/12/2023 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents bibliography source types.
/// </summary>
public /*enum*/ final class SourceType
{
    private SourceType(){}
    
    /// <summary>
    /// Specifies the article in a periodical source.
    /// </summary>
    public static final int ARTICLE_IN_A_PERIODICAL = 0;

    /// <summary>
    /// Specifies the book source.
    /// </summary>
    public static final int BOOK = 1;

    /// <summary>
    /// Specifies the book section source.
    /// </summary>
    public static final int BOOK_SECTION = 2;

    /// <summary>
    /// Specifies the journal article source.
    /// </summary>
    public static final int JOURNAL_ARTICLE = 3;

    /// <summary>
    /// Specifies the conference proceedings source.
    /// </summary>
    public static final int CONFERENCE_PROCEEDINGS = 4;

    /// <summary>
    /// Specifies the reporter source.
    /// </summary>
    public static final int REPORT = 5;

    /// <summary>
    /// Specifies the sound recording source.
    /// </summary>
    public static final int SOUND_RECORDING = 6;

    /// <summary>
    /// Specifies the performance source.
    /// </summary>
    public static final int PERFORMANCE = 7;

    /// <summary>
    /// Specifies the art source.
    /// </summary>
    public static final int ART = 8;

    /// <summary>
    /// Specifies the document from internet site source.
    /// </summary>
    public static final int DOCUMENT_FROM_INTERNET_SITE = 9;

    /// <summary>
    /// Specifies the internet site source.
    /// </summary>
    public static final int INTERNET_SITE = 10;

    /// <summary>
    /// Specifies the film source.
    /// </summary>
    public static final int FILM = 11;

    /// <summary>
    /// Specifies the interview source.
    /// </summary>
    public static final int INTERVIEW = 12;

    /// <summary>
    /// Specifies the patent source.
    /// </summary>
    public static final int PATENT = 13;

    /// <summary>
    /// Specifies the electronic source.
    /// </summary>
    public static final int ELECTRONIC = 14;

    /// <summary>
    /// Specifies the case source.
    /// </summary>
    public static final int CASE = 15;

    /// <summary>
    /// Specifies the miscellaneous source.
    /// </summary>
    public static final int MISC = 16;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SourceType value.
    /// </summary>
    public static String getName(/*SourceType*/int sourceType)
    {
    	switch (sourceType)
    	{
    		case ARTICLE_IN_A_PERIODICAL: return "ARTICLE_IN_A_PERIODICAL";
    		case BOOK: return "BOOK";
    		case BOOK_SECTION: return "BOOK_SECTION";
    		case JOURNAL_ARTICLE: return "JOURNAL_ARTICLE";
    		case CONFERENCE_PROCEEDINGS: return "CONFERENCE_PROCEEDINGS";
    		case REPORT: return "REPORT";
    		case SOUND_RECORDING: return "SOUND_RECORDING";
    		case PERFORMANCE: return "PERFORMANCE";
    		case ART: return "ART";
    		case DOCUMENT_FROM_INTERNET_SITE: return "DOCUMENT_FROM_INTERNET_SITE";
    		case INTERNET_SITE: return "INTERNET_SITE";
    		case FILM: return "FILM";
    		case INTERVIEW: return "INTERVIEW";
    		case PATENT: return "PATENT";
    		case ELECTRONIC: return "ELECTRONIC";
    		case CASE: return "CASE";
    		case MISC: return "MISC";
    		default: return "Unknown SourceType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SourceType value.
    /// </summary>
    public static String toString(/*SourceType*/int sourceType)
    {
    	switch (sourceType)
    	{
    		case ARTICLE_IN_A_PERIODICAL: return "ArticleInAPeriodical";
    		case BOOK: return "Book";
    		case BOOK_SECTION: return "BookSection";
    		case JOURNAL_ARTICLE: return "JournalArticle";
    		case CONFERENCE_PROCEEDINGS: return "ConferenceProceedings";
    		case REPORT: return "Report";
    		case SOUND_RECORDING: return "SoundRecording";
    		case PERFORMANCE: return "Performance";
    		case ART: return "Art";
    		case DOCUMENT_FROM_INTERNET_SITE: return "DocumentFromInternetSite";
    		case INTERNET_SITE: return "InternetSite";
    		case FILM: return "Film";
    		case INTERVIEW: return "Interview";
    		case PATENT: return "Patent";
    		case ELECTRONIC: return "Electronic";
    		case CASE: return "Case";
    		case MISC: return "Misc";
    		default: return "Unknown SourceType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SourceType name.
    /// </summary>
    public static /*SourceType*/int fromName(String sourceTypeName)
    {
    	if ("ARTICLE_IN_A_PERIODICAL".equals(sourceTypeName)) return ARTICLE_IN_A_PERIODICAL;
    	if ("BOOK".equals(sourceTypeName)) return BOOK;
    	if ("BOOK_SECTION".equals(sourceTypeName)) return BOOK_SECTION;
    	if ("JOURNAL_ARTICLE".equals(sourceTypeName)) return JOURNAL_ARTICLE;
    	if ("CONFERENCE_PROCEEDINGS".equals(sourceTypeName)) return CONFERENCE_PROCEEDINGS;
    	if ("REPORT".equals(sourceTypeName)) return REPORT;
    	if ("SOUND_RECORDING".equals(sourceTypeName)) return SOUND_RECORDING;
    	if ("PERFORMANCE".equals(sourceTypeName)) return PERFORMANCE;
    	if ("ART".equals(sourceTypeName)) return ART;
    	if ("DOCUMENT_FROM_INTERNET_SITE".equals(sourceTypeName)) return DOCUMENT_FROM_INTERNET_SITE;
    	if ("INTERNET_SITE".equals(sourceTypeName)) return INTERNET_SITE;
    	if ("FILM".equals(sourceTypeName)) return FILM;
    	if ("INTERVIEW".equals(sourceTypeName)) return INTERVIEW;
    	if ("PATENT".equals(sourceTypeName)) return PATENT;
    	if ("ELECTRONIC".equals(sourceTypeName)) return ELECTRONIC;
    	if ("CASE".equals(sourceTypeName)) return CASE;
    	if ("MISC".equals(sourceTypeName)) return MISC;
    	throw new IllegalArgumentException("Unknown SourceType name.");
    }
    
    /// <summary>
    /// Returns array of SourceType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ARTICLE_IN_A_PERIODICAL,
    		BOOK,
    		BOOK_SECTION,
    		JOURNAL_ARTICLE,
    		CONFERENCE_PROCEEDINGS,
    		REPORT,
    		SOUND_RECORDING,
    		PERFORMANCE,
    		ART,
    		DOCUMENT_FROM_INTERNET_SITE,
    		INTERNET_SITE,
    		FILM,
    		INTERVIEW,
    		PATENT,
    		ELECTRONIC,
    		CASE,
    		MISC,
    	};
    }

    public static final int length = 17;
}

