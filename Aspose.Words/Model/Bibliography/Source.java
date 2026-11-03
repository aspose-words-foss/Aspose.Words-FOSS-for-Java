// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2023 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.AnyXmlBuilder;
import com.aspose.ms.System.IO.StreamReader;
import com.aspose.NullableInt32;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.SystemPal;


/// <summary>
/// Represents an individual source, such as a book, journal article, or interview.
/// </summary>
public final class Source implements Cloneable
{
    /// <summary>
    /// Initialize a new instance of the <see cref="Source"/> class.
    /// </summary>
    /// <param name="tag">The identifying tag name.</param>
    /// <param name="sourceType">The source type.</param>
    public Source(String tag, /*SourceType*/int sourceType)
    {
    	this();
	
        setTag(tag);
        setSourceType(sourceType);
        mVersions = BibliographyVersion.calculate(this);
    }

    Source(String xml)
    {
    	this();
	
        mXml = xml;
        SourceReader.read(this, xml);
        mVersions = BibliographyVersion.calculate(this);
    }

    private Source()
    {
        hasNormalizedLcid(false);
        setContributors(new ContributorCollection());
    }

    /// <summary>
    /// Returns XML representation of a source with normalized lcid.
    /// </summary>
    String getNormalizedXml() throws Exception
    {
        if (!hasNormalizedLcid())
            return getXml();

        if (msString.equals(Integer.toString(getNormalizedLcid()), getLcid()))
            return getXml();

        return getXml().replace(
            msString.format("<b:LCID>{0}</b:LCID>", getLcid()),
            msString.format("<b:LCID>{0}</b:LCID>", getNormalizedLcid()));
    }

    /// <summary>
    /// Returns all person contributors of a source.
    /// </summary>
    ArrayList<Person> expandPersons()
    {
        ArrayList<Person> result = new ArrayList<Person>();
        for (Contributor author : getContributors())
        {
            PersonCollection persons = ms.as(author, PersonCollection.class);
            if (persons != null)
                msArrayList.addRange(result, persons);
        }

        return result;
    }

    private String getXml() throws Exception
    {
        int version = BibliographyVersion.calculate(this);

        if ((mXml != null) && (mVersions == version))
            return mXml;

        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            AnyXmlBuilder builder = new AnyXmlBuilder(stream, true);
            SourceWriter.write(this, builder);
            builder.flush();

            stream.setPosition(0);

            StreamReader reader = new StreamReader(stream);
            try /*JAVA: was using*/
        	{
                mXml = reader.readToEnd();
        	}
            finally { if (reader != null) reader.close(); }
        }
        finally { if (stream != null) stream.close(); }

        mVersions = version;

        return mXml;
    }

    /// <summary>
    /// Gets or sets the locale ID of a source.
    /// </summary>
    public String getLcid() { return mLcid; }
    public void setLcid(String value)
    {
        if (msString.equals(mLcid, value))
            return;

        mLcid = value;
        normalizeLocale();
    }

    private void normalizeLocale()
    {
        NullableInt32 lcid = FormatterPal.parseNullableInt(getLcid());
        msCultureInfo culture = lcid.hasValue()
            ? SystemPal.tryGetCulture(lcid.getValue())
            : SystemPal.tryGetCulture(getLcid());

        if (culture != null)
        {
            setNormalizedLcid(culture.getLCID());
            hasNormalizedLcid(true);
        }
        else
        {
            hasNormalizedLcid(false);
        }
    }

    /// <summary>
    ///  Gets the parsed locale ID of a source.
    /// </summary>
    int getNormalizedLcid() { return mNormalizedLcid; }; private void setNormalizedLcid(int value) { mNormalizedLcid = value; };

    private int mNormalizedLcid;

    boolean hasNormalizedLcid() { return mHasNormalizedLcid; }; private void hasNormalizedLcid(boolean value) { mHasNormalizedLcid = value; };

    private boolean mHasNormalizedLcid;

    /// <summary>
    /// Gets contributors list (author, editor, writer etc) of a source.
    /// </summary>
    public ContributorCollection getContributors() { return mContributors; }; private void setContributors(ContributorCollection value) { mContributors = value; };

    private ContributorCollection mContributors;

    /// <summary>
    /// Gets or sets the source type of a source.
    /// </summary>
    public /*SourceType*/int getSourceType() { return mSourceType; }; public void setSourceType(/*SourceType*/int value) { mSourceType = value; };

    private /*SourceType*/int mSourceType;

    /// <summary>
    /// Gets or sets the abbreviated case number of a source.
    /// </summary>
    public String getAbbreviatedCaseNumber() { return mAbbreviatedCaseNumber; }; public void setAbbreviatedCaseNumber(String value) { mAbbreviatedCaseNumber = value; };

    private String mAbbreviatedCaseNumber;

    /// <summary>
    /// Gets or sets the album title of a source.
    /// </summary>
    public String getAlbumTitle() { return mAlbumTitle; }; public void setAlbumTitle(String value) { mAlbumTitle = value; };

    private String mAlbumTitle;

    /// <summary>
    /// Gets or sets the book title of a source.
    /// </summary>
    public String getBookTitle() { return mBookTitle; }; public void setBookTitle(String value) { mBookTitle = value; };

    private String mBookTitle;

    /// <summary>
    /// Gets or sets the broadcaster of a source.
    /// </summary>
    public String getBroadcaster() { return mBroadcaster; }; public void setBroadcaster(String value) { mBroadcaster = value; };

    private String mBroadcaster;

    /// <summary>
    /// Gets or sets the broadcast title of a source.
    /// </summary>
    public String getBroadcastTitle() { return mBroadcastTitle; }; public void setBroadcastTitle(String value) { mBroadcastTitle = value; };

    private String mBroadcastTitle;

    /// <summary>
    /// Gets or sets the case number of a source.
    /// </summary>
    public String getCaseNumber() { return mCaseNumber; }; public void setCaseNumber(String value) { mCaseNumber = value; };

    private String mCaseNumber;

    /// <summary>
    /// Gets or sets the chapter number of a source.
    /// </summary>
    public String getChapterNumber() { return mChapterNumber; }; public void setChapterNumber(String value) { mChapterNumber = value; };

    private String mChapterNumber;

    /// <summary>
    /// Gets or sets the city of a source.
    /// </summary>
    public String getCity() { return mCity; }; public void setCity(String value) { mCity = value; };

    private String mCity;

    /// <summary>
    /// Gets or sets the comments of a source.
    /// </summary>
    public String getComments() { return mComments; }; public void setComments(String value) { mComments = value; };

    private String mComments;

    /// <summary>
    /// Gets or sets the conference or proceedings name of a source.
    /// </summary>
    public String getConferenceName() { return mConferenceName; }; public void setConferenceName(String value) { mConferenceName = value; };

    private String mConferenceName;

    /// <summary>
    /// Gets or sets the country or region of a source.
    /// </summary>
    public String getCountryOrRegion() { return mCountryOrRegion; }; public void setCountryOrRegion(String value) { mCountryOrRegion = value; };

    private String mCountryOrRegion;

    /// <summary>
    /// Gets or sets the court of a source.
    /// </summary>
    public String getCourt() { return mCourt; }; public void setCourt(String value) { mCourt = value; };

    private String mCourt;

    /// <summary>
    /// Gets or sets the day of a source.
    /// </summary>
    public String getDay() { return mDay; }; public void setDay(String value) { mDay = value; };

    private String mDay;

    /// <summary>
    /// Gets or sets the day accessed of a source.
    /// </summary>
    public String getDayAccessed() { return mDayAccessed; }; public void setDayAccessed(String value) { mDayAccessed = value; };

    private String mDayAccessed;

    /// <summary>
    /// Gets or sets the department of a source.
    /// </summary>
    public String getDepartment() { return mDepartment; }; public void setDepartment(String value) { mDepartment = value; };

    private String mDepartment;

    /// <summary>
    /// Gets or sets the distributor of a source.
    /// </summary>
    public String getDistributor() { return mDistributor; }; public void setDistributor(String value) { mDistributor = value; };

    private String mDistributor;

    /// <summary>
    /// Gets or sets the editor of a source.
    /// </summary>
    public String getEdition() { return mEdition; }; public void setEdition(String value) { mEdition = value; };

    private String mEdition;

    /// <summary>
    /// Gets or sets the guid of a source.
    /// </summary>
    public String getGuid() { return mGuid; }; public void setGuid(String value) { mGuid = value; };

    private String mGuid;

    /// <summary>
    /// Gets or sets the institution of a source.
    /// </summary>
    public String getInstitution() { return mInstitution; }; public void setInstitution(String value) { mInstitution = value; };

    private String mInstitution;

    /// <summary>
    /// Gets or sets the internet site title of a source.
    /// </summary>
    public String getInternetSiteTitle() { return mInternetSiteTitle; }; public void setInternetSiteTitle(String value) { mInternetSiteTitle = value; };

    private String mInternetSiteTitle;

    /// <summary>
    /// Gets or sets the issue of a source.
    /// </summary>
    public String getIssue() { return mIssue; }; public void setIssue(String value) { mIssue = value; };

    private String mIssue;

    /// <summary>
    /// Gets or sets the journal name of a source.
    /// </summary>
    public String getJournalName() { return mJournalName; }; public void setJournalName(String value) { mJournalName = value; };

    private String mJournalName;

    /// <summary>
    /// Gets or sets the medium of a source.
    /// </summary>
    public String getMedium() { return mMedium; }; public void setMedium(String value) { mMedium = value; };

    private String mMedium;

    /// <summary>
    /// Gets or sets the month of a source.
    /// </summary>
    public String getMonth() { return mMonth; }; public void setMonth(String value) { mMonth = value; };

    private String mMonth;

    /// <summary>
    /// Gets or sets the month accessed of a source.
    /// </summary>
    public String getMonthAccessed() { return mMonthAccessed; }; public void setMonthAccessed(String value) { mMonthAccessed = value; };

    private String mMonthAccessed;

    /// <summary>
    /// Gets or sets the number of volumes of a source.
    /// </summary>
    public String getNumberVolumes() { return mNumberVolumes; }; public void setNumberVolumes(String value) { mNumberVolumes = value; };

    private String mNumberVolumes;

    /// <summary>
    /// Gets or sets the pages of a source.
    /// </summary>
    public String getPages() { return mPages; }; public void setPages(String value) { mPages = value; };

    private String mPages;

    /// <summary>
    /// Gets or sets the patent number of a source.
    /// </summary>
    public String getPatentNumber() { return mPatentNumber; }; public void setPatentNumber(String value) { mPatentNumber = value; };

    private String mPatentNumber;

    /// <summary>
    /// Gets or sets the periodical title of a source.
    /// </summary>
    public String getPeriodicalTitle() { return mPeriodicalTitle; }; public void setPeriodicalTitle(String value) { mPeriodicalTitle = value; };

    private String mPeriodicalTitle;

    /// <summary>
    /// Gets or sets the production company of a source.
    /// </summary>
    public String getProductionCompany() { return mProductionCompany; }; public void setProductionCompany(String value) { mProductionCompany = value; };

    private String mProductionCompany;

    /// <summary>
    /// Gets or sets the publication title of a source.
    /// </summary>
    public String getPublicationTitle() { return mPublicationTitle; }; public void setPublicationTitle(String value) { mPublicationTitle = value; };

    private String mPublicationTitle;

    /// <summary>
    /// Gets or sets the publisher of a source.
    /// </summary>
    public String getPublisher() { return mPublisher; }; public void setPublisher(String value) { mPublisher = value; };

    private String mPublisher;

    /// <summary>
    /// Gets or sets the recording number of a source.
    /// </summary>
    public String getRecordingNumber() { return mRecordingNumber; }; public void setRecordingNumber(String value) { mRecordingNumber = value; };

    private String mRecordingNumber;

    /// <summary>
    /// Gets or sets the reference order of a source.
    /// </summary>
    public String getRefOrder() { return mRefOrder; }; public void setRefOrder(String value) { mRefOrder = value; };

    private String mRefOrder;

    /// <summary>
    /// Gets or sets the reporter of a source.
    /// </summary>
    public String getReporter() { return mReporter; }; public void setReporter(String value) { mReporter = value; };

    private String mReporter;

    /// <summary>
    /// Gets or sets the short title of a source.
    /// </summary>
    public String getShortTitle() { return mShortTitle; }; public void setShortTitle(String value) { mShortTitle = value; };

    private String mShortTitle;

    /// <summary>
    /// Gets or sets the standard number of a source.
    /// </summary>
    public String getStandardNumber() { return mStandardNumber; }; public void setStandardNumber(String value) { mStandardNumber = value; };

    private String mStandardNumber;

    /// <summary>
    /// Gets or sets the state or province of a source.
    /// </summary>
    public String getStateOrProvince() { return mStateOrProvince; }; public void setStateOrProvince(String value) { mStateOrProvince = value; };

    private String mStateOrProvince;

    /// <summary>
    /// Gets or sets the station of a source.
    /// </summary>
    public String getStation() { return mStation; }; public void setStation(String value) { mStation = value; };

    private String mStation;

    /// <summary>
    /// Gets or sets the identifying tag name of a source.
    /// </summary>
    public String getTag() { return mTag; }; public void setTag(String value) { mTag = value; };

    private String mTag;

    /// <summary>
    /// Gets or sets the theater of a source.
    /// </summary>
    public String getTheater() { return mTheater; }; public void setTheater(String value) { mTheater = value; };

    private String mTheater;

    /// <summary>
    /// Gets or sets the thesis type of a source.
    /// </summary>
    public String getThesisType() { return mThesisType; }; public void setThesisType(String value) { mThesisType = value; };

    private String mThesisType;

    /// <summary>
    /// Gets or sets the title of a source.
    /// </summary>
    public String getTitle() { return mTitle; }; public void setTitle(String value) { mTitle = value; };

    private String mTitle;

    /// <summary>
    /// Gets or sets the type of a source.
    /// </summary>
    public String getType() { return mType; }; public void setType(String value) { mType = value; };

    private String mType;

    /// <summary>
    /// Gets or sets the url of a source.
    /// </summary>
    public String getUrl() { return mUrl; }; public void setUrl(String value) { mUrl = value; };

    private String mUrl;

    /// <summary>
    /// Gets or sets the version of a source.
    /// </summary>
    public String getVersion() { return mVersion; }; public void setVersion(String value) { mVersion = value; };

    private String mVersion;

    /// <summary>
    /// Gets or sets the volume of a source.
    /// </summary>
    public String getVolume() { return mVolume; }; public void setVolume(String value) { mVolume = value; };

    private String mVolume;

    /// <summary>
    /// Gets or sets the year of a source.
    /// </summary>
    public String getYear() { return mYear; }; public void setYear(String value) { mYear = value; };

    private String mYear;

    /// <summary>
    /// Gets or sets the year accessed of a source.
    /// </summary>
    public String getYearAccessed() { return mYearAccessed; }; public void setYearAccessed(String value) { mYearAccessed = value; };

    private String mYearAccessed;

    /// <summary>
    /// Gets or sets the digital object identifier.
    /// </summary>
    public String getDoi() { return mDoi; }; public void setDoi(String value) { mDoi = value; };

    private String mDoi;

    Source deepClone()
    {
        Source lhs = (Source)memberwiseClone();

        lhs.setContributors(getContributors().deepClone());

        return lhs;
    }

    private String mLcid;
    private String mXml;
    private int mVersions;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

