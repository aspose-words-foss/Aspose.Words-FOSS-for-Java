// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/05/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;

/// <summary>
/// Specifies all of the mail merge information for a document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/mail-merge-and-reporting/">Mail Merge and Reporting</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>You can use this object to specify a mail merge data source for a document and this information
/// (along with the available data fields) will appear in Microsoft Word when the user opens this document.
/// Or you can use this object to query mail merge settings that the user has specified in Microsoft Word
/// for this document.</para>
///
/// <para>You do not normally need to create objects of this class directly because Mail merge settings
/// of a document are always available via the <see cref="Document.MailMergeSettings"/> property.</para>
///
/// <para>To detect whether this document is a mail merge main document, check the value of the
/// <see cref="MainDocumentType"/> property.</para>
///
/// <para>To remove mail merge settings and data source information from a document you can use the
/// <see cref="Clear"/> method. Aspose.Words will not write mail merge settings to a document if
/// the <see cref="MainDocumentType"/> property is set to <see cref="MailMergeMainDocumentType.NotAMergeDocument"/>
/// or the <see cref="DataType"/> property is set to <see cref="MailMergeDataType.None"/>.</para>
///
/// <para>The best way to learn how to use the properties of this object is to create a document with a desired
/// data source manually in Microsoft Word and then open that document using Aspose.Words and examine the properties
/// of the <see cref="Document.MailMergeSettings"/> and <see cref="MailMergeSettings.Odso"/> objects. This is
/// a good approach to take if you want to learn how to programmatically configure a data source, for example.</para>
///
/// </remarks>
public class MailMergeSettings implements Cloneable
{
    /// <summary>
    /// Returns true if the mail merge settings should not be written to the document when saving.
    /// </summary>
    boolean isEmpty()
    {
        return
            (getMainDocumentType() == MailMergeMainDocumentType.NOT_A_MERGE_DOCUMENT) ||
            (getDataType() == MailMergeDataType.NONE);
    }

    /// <summary>
    /// Clears the mail merge settings in such a way that when the document is saved,
    /// no mail merge settings will be saved and it will become a normal document.
    /// </summary>
    public void clear()
    {
        setMainDocumentType(MailMergeMainDocumentType.NOT_A_MERGE_DOCUMENT);
        setDataType(MailMergeDataType.NONE);
    }

    /// <summary>
    /// Returns a deep clone of this object.
    /// </summary>
    public MailMergeSettings deepClone()
    {
        MailMergeSettings rhs = (MailMergeSettings)memberwiseClone();
        rhs.mOdso = mOdso.deepClone();
        return rhs;
    }

    /// <summary>
    /// Specifies the one-based index of the record from the data source which shall be displayed in Microsoft Word. The default value is 1.
    /// </summary>
    public int getActiveRecord() { return mActiveRecord; }
    public void setActiveRecord(int value) { mActiveRecord = value; }

    /// <summary>
    /// Specifies the column within the data source that contains e-mail addresses. The default value is an empty string.
    /// </summary>
    public String getAddressFieldName() { return mAddressFieldName; }
    public void setAddressFieldName(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mAddressFieldName = value;
    }

    /// <summary>
    /// Specifies the type of error reporting which shall be conducted by Microsoft Word when performing a mail merge.
    /// The default value is <see cref="MailMergeCheckErrors.Default"/>.
    /// </summary>
    public /*MailMergeCheckErrors*/int getCheckErrors() { return mCheckErrors; }
    public void setCheckErrors(/*MailMergeCheckErrors*/int value) { mCheckErrors = value; }

    /// <summary>
    /// Specifies the connection string used to connect to an external data source. The default value is an empty string.
    /// </summary>
    public String getConnectString() { return mConnectString; }
    public void setConnectString(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mConnectString = value;
    }

    /// <summary>
    /// Specifies the path to the mail-merge data source. The default value is an empty string.
    /// </summary>
    public String getDataSource() { return mDataSource; }
    public void setDataSource(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mDataSource = value;
    }

    /// <summary>
    /// Specifies the type of the mail-merge data source and the method of data access.
    /// The default value is <see cref="MailMergeDataType.Default"/>.
    /// </summary>
    public /*MailMergeDataType*/int getDataType() { return mDataType; }
    public void setDataType(/*MailMergeDataType*/int value) { mDataType = value; }

    /// <summary>
    /// Specifies how Microsoft Word will output the results of a mail merge.
    /// The default value is <see cref="MailMergeDestination.Default"/>.
    /// </summary>
    public /*MailMergeDestination*/int getDestination() { return mDestination; }
    public void setDestination(/*MailMergeDestination*/int value) { mDestination = value; }

    /// <summary>
    /// Specifies how an application performing the mail merge shall handle blank lines in the merged documents resulting from the mail merge.
    /// The default value is <c>false</c>.
    /// </summary>
    public boolean getDoNotSupressBlankLines() { return mDoNotSupressBlankLines; }
    public void setDoNotSupressBlankLines(boolean value) { mDoNotSupressBlankLines = value; }

    /// <summary>
    /// Specifies the path to the mail-merge header source.
    /// The default value is an empty string.
    /// </summary>
    public String getHeaderSource() { return mHeaderSource; }
    public void setHeaderSource(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mHeaderSource = value;
    }

    /// <summary>
    /// Not sure about this one.
    /// The Microsoft Word Automation Reference suggests that this specifies that the query is executed every time the document
    /// is opened in Microsoft Word. But the OOXML specification suggests that this specifies that the query contains a reference
    /// to an external query file which contains the actual query.
    /// The default value is <c>false</c>.
    /// </summary>
    public boolean getLinkToQuery() { return mLinkToQuery; }
    public void setLinkToQuery(boolean value) { mLinkToQuery = value; }

    /// <summary>
    /// Specifies that the documents produced during a mail merge operation should be emailed as an attachment rather
    /// than the body of the actual e-mail. The default value is <c>false</c>.
    /// </summary>
    public boolean getMailAsAttachment() { return mMailAsAttachment; }
    public void setMailAsAttachment(boolean value) { mMailAsAttachment = value; }

    /// <summary>
    /// Specifies the text which shall appear in the subject line of the e-mails or faxes produced during mail merge.
    /// The default value is an empty string.
    /// </summary>
    public String getMailSubject() { return mMailSubject; }
    public void setMailSubject(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mMailSubject = value;
    }

    /// <summary>
    /// Specifies the mail-merge main document type.
    /// The default value is <see cref="MailMergeMainDocumentType.Default"/>.
    /// </summary>
    /// <remarks>
    /// <para>The main document is the document that contains information that is the same for each version of the merged document.</para>
    /// </remarks>
    public /*MailMergeMainDocumentType*/int getMainDocumentType() { return mMainDocumentType; }
    public void setMainDocumentType(/*MailMergeMainDocumentType*/int value) { mMainDocumentType = value; }

    /// <summary>
    /// Gets or sets the object that specifies the Office Data Source Object (ODSO) settings.
    /// </summary>
    /// <remarks>
    /// <para>This object is never <c>null</c>.</para>
    /// </remarks>
    public Odso getOdso() { return mOdso; }
    public void setOdso(Odso value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mOdso = value;
    }

    /// <summary>
    /// Contains the Structured Query Language string that shall be run against the specified external data source to
    /// return the set of records which shall be imported into the document when the mail merge operation is performed.
    /// The default value is an empty string.
    /// </summary>
    public String getQuery() { return mQuery; }
    public void setQuery(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mQuery = value;
    }

    /// <summary>
    /// Specifies that Microsoft Word shall display the data from the specified external data source where merge fields
    /// have been inserted (e.g. preview merged data). The default value is <c>false</c>.
    /// </summary>
    public boolean getViewMergedData() { return mViewMergedData; }
    public void setViewMergedData(boolean value) { mViewMergedData = value; }

    /// <summary>
    /// A DOC only property, specifies the token to separate fields in the data file.
    /// The default value is <see cref="MailMergeLegacySeparator.Tab"/>.
    /// </summary>
    /*MailMergeLegacySeparator*/int getLegacyDataFieldSeparator() { return mLegacyDataFieldSeparator; }
    void setLegacyDataFieldSeparator(/*MailMergeLegacySeparator*/int value) { mLegacyDataFieldSeparator = value; }

    /// <summary>
    /// A DOC only property, specifies the token to separate records in the data file.
    /// The default value is <see cref="MailMergeLegacySeparator.Enter"/>.
    /// </summary>
    /*MailMergeLegacySeparator*/int getLegacyDataRowSeparator() { return mLegacyDataRowSeparator; }
    void setLegacyDataRowSeparator(/*MailMergeLegacySeparator*/int value) { mLegacyDataRowSeparator = value; }

    /// <summary>
    /// A DOC only property, specifies the token to separate fields in the header file.
    /// The default value is <see cref="MailMergeLegacySeparator.Tab"/>.
    /// </summary>
    /*MailMergeLegacySeparator*/int getLegacyHeaderFieldSeparator() { return mLegacyHeaderFieldSeparator; }
    void setLegacyHeaderFieldSeparator(/*MailMergeLegacySeparator*/int value) { mLegacyHeaderFieldSeparator = value; }

    /// <summary>
    /// A DOC only property, specifies the token to separate records in the header file.
    /// The default value is <see cref="MailMergeLegacySeparator.Enter"/>.
    /// </summary>
    /*MailMergeLegacySeparator*/int getLegacyHeaderRowSeparator() { return mLegacyHeaderRowSeparator; }
    void setLegacyHeaderRowSeparator(/*MailMergeLegacySeparator*/int value) { mLegacyHeaderRowSeparator = value; }


    private int mActiveRecord = 1;
    private String mAddressFieldName = "";
    private /*MailMergeCheckErrors*/int mCheckErrors = MailMergeCheckErrors.DEFAULT;
    private String mConnectString = "";
    private String mDataSource = "";
    private /*MailMergeDataType*/int mDataType = MailMergeDataType.DEFAULT;
    private /*MailMergeDestination*/int mDestination = MailMergeDestination.DEFAULT;
    private boolean mDoNotSupressBlankLines = false;
    private String mHeaderSource = "";
    private boolean mLinkToQuery = false;
    private boolean mMailAsAttachment = false;
    private String mMailSubject = "";
    private /*MailMergeMainDocumentType*/int mMainDocumentType = MailMergeMainDocumentType.DEFAULT;
    private Odso mOdso = new Odso();
    private String mQuery = "";
    private boolean mViewMergedData = false;
    // WORDSNET-7853 LegacyDataFiledSeparator should be semicolon otherwise Word for Mac fails mail merge.
    private /*MailMergeLegacySeparator*/int mLegacyDataFieldSeparator = MailMergeLegacySeparator.SEMICOLON;
    private /*MailMergeLegacySeparator*/int mLegacyDataRowSeparator = MailMergeLegacySeparator.ENTER;
    private /*MailMergeLegacySeparator*/int mLegacyHeaderFieldSeparator = MailMergeLegacySeparator.TAB;
    private /*MailMergeLegacySeparator*/int mLegacyHeaderRowSeparator = MailMergeLegacySeparator.ENTER;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

