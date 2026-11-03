// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/12/2023 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents bibliography source contributor types.
/// </summary>
/*enum*/ final class ContributorType
{
    private ContributorType(){}
    
    /// <summary>
    /// Specifies the artist contributor.
    /// </summary>
    public static final int ARTIST = 0;

    /// <summary>
    /// Specifies the author contributor.
    /// </summary>
    public static final int AUTHOR = 1;

    /// <summary>
    /// Specifies the book author contributor.
    /// </summary>
    public static final int BOOK_AUTHOR = 2;

    /// <summary>
    /// Specifies the compiler contributor.
    /// </summary>
    public static final int COMPILER = 3;

    /// <summary>
    /// Specifies the composer contributor.
    /// </summary>
    public static final int COMPOSER = 4;

    /// <summary>
    /// Specifies the conductor contributor.
    /// </summary>
    public static final int CONDUCTOR = 5;

    /// <summary>
    /// Specifies the counsel contributor.
    /// </summary>
    public static final int COUNSEL = 6;

    /// <summary>
    /// Specifies the director contributor.
    /// </summary>
    public static final int DIRECTOR = 7;

    /// <summary>
    /// Specifies the editor contributor.
    /// </summary>
    public static final int EDITOR = 8;

    /// <summary>
    /// Specifies the interviewee contributor.
    /// </summary>
    public static final int INTERVIEWEE = 9;

    /// <summary>
    /// Specifies the interviewer contributor.
    /// </summary>
    public static final int INTERVIEWER = 10;

    /// <summary>
    /// Specifies the inventor contributor.
    /// </summary>
    public static final int INVENTOR = 11;

    /// <summary>
    /// Specifies the performer contributor.
    /// </summary>
    public static final int PERFORMER = 12;

    /// <summary>
    /// Specifies the producer name contributor.
    /// </summary>
    public static final int PRODUCER = 13;

    /// <summary>
    /// Specifies the translator contributor.
    /// </summary>
    public static final int TRANSLATOR = 14;

    /// <summary>
    /// Specifies the writer contributor.
    /// </summary>
    public static final int WRITER = 15;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ContributorType value.
    /// </summary>
    public static String getName(/*ContributorType*/int contributorType)
    {
    	switch (contributorType)
    	{
    		case ARTIST: return "ARTIST";
    		case AUTHOR: return "AUTHOR";
    		case BOOK_AUTHOR: return "BOOK_AUTHOR";
    		case COMPILER: return "COMPILER";
    		case COMPOSER: return "COMPOSER";
    		case CONDUCTOR: return "CONDUCTOR";
    		case COUNSEL: return "COUNSEL";
    		case DIRECTOR: return "DIRECTOR";
    		case EDITOR: return "EDITOR";
    		case INTERVIEWEE: return "INTERVIEWEE";
    		case INTERVIEWER: return "INTERVIEWER";
    		case INVENTOR: return "INVENTOR";
    		case PERFORMER: return "PERFORMER";
    		case PRODUCER: return "PRODUCER";
    		case TRANSLATOR: return "TRANSLATOR";
    		case WRITER: return "WRITER";
    		default: return "Unknown ContributorType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ContributorType value.
    /// </summary>
    public static String toString(/*ContributorType*/int contributorType)
    {
    	switch (contributorType)
    	{
    		case ARTIST: return "Artist";
    		case AUTHOR: return "Author";
    		case BOOK_AUTHOR: return "BookAuthor";
    		case COMPILER: return "Compiler";
    		case COMPOSER: return "Composer";
    		case CONDUCTOR: return "Conductor";
    		case COUNSEL: return "Counsel";
    		case DIRECTOR: return "Director";
    		case EDITOR: return "Editor";
    		case INTERVIEWEE: return "Interviewee";
    		case INTERVIEWER: return "Interviewer";
    		case INVENTOR: return "Inventor";
    		case PERFORMER: return "Performer";
    		case PRODUCER: return "Producer";
    		case TRANSLATOR: return "Translator";
    		case WRITER: return "Writer";
    		default: return "Unknown ContributorType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ContributorType name.
    /// </summary>
    public static /*ContributorType*/int fromName(String contributorTypeName)
    {
    	if ("ARTIST".equals(contributorTypeName)) return ARTIST;
    	if ("AUTHOR".equals(contributorTypeName)) return AUTHOR;
    	if ("BOOK_AUTHOR".equals(contributorTypeName)) return BOOK_AUTHOR;
    	if ("COMPILER".equals(contributorTypeName)) return COMPILER;
    	if ("COMPOSER".equals(contributorTypeName)) return COMPOSER;
    	if ("CONDUCTOR".equals(contributorTypeName)) return CONDUCTOR;
    	if ("COUNSEL".equals(contributorTypeName)) return COUNSEL;
    	if ("DIRECTOR".equals(contributorTypeName)) return DIRECTOR;
    	if ("EDITOR".equals(contributorTypeName)) return EDITOR;
    	if ("INTERVIEWEE".equals(contributorTypeName)) return INTERVIEWEE;
    	if ("INTERVIEWER".equals(contributorTypeName)) return INTERVIEWER;
    	if ("INVENTOR".equals(contributorTypeName)) return INVENTOR;
    	if ("PERFORMER".equals(contributorTypeName)) return PERFORMER;
    	if ("PRODUCER".equals(contributorTypeName)) return PRODUCER;
    	if ("TRANSLATOR".equals(contributorTypeName)) return TRANSLATOR;
    	if ("WRITER".equals(contributorTypeName)) return WRITER;
    	throw new IllegalArgumentException("Unknown ContributorType name.");
    }
    
    /// <summary>
    /// Returns array of ContributorType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ARTIST,
    		AUTHOR,
    		BOOK_AUTHOR,
    		COMPILER,
    		COMPOSER,
    		CONDUCTOR,
    		COUNSEL,
    		DIRECTOR,
    		EDITOR,
    		INTERVIEWEE,
    		INTERVIEWER,
    		INVENTOR,
    		PERFORMER,
    		PRODUCER,
    		TRANSLATOR,
    		WRITER,
    	};
    }

    public static final int length = 16;
}

