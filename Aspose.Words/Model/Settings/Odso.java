// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/10/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;

/// <summary>
/// Specifies the Office Data Source Object (ODSO) settings for a mail merge data source.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/mail-merge-and-reporting/">Mail Merge and Reporting</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>ODSO seems to be the "new" way the newer Microsoft Word versions prefer to use when specifying certain 
/// types of data sources for a mail merge document. ODSO probably first appeared in Microsoft Word 2000.</para>
/// 
/// <para>The use of ODSO is poorly documented and the best way to learn how to use the properties of this object 
/// is to create a document with a desired data source manually in Microsoft Word and then open that document using 
/// Aspose.Words and examine the properties of the <see cref="Document.MailMergeSettings"/> and 
/// <see cref="MailMergeSettings.Odso"/> objects. This is a good approach to take if you want to learn how to 
/// programmatically configure a data source, for example.</para>
/// 
/// <para>You do not normally need to create objects of this class directly because ODSO settings
/// are always available via the <see cref="MailMergeSettings.Odso"/> property.</para>
/// 
/// <seealso cref="MailMergeSettings.Odso"/>
/// </remarks>
public class Odso implements Cloneable
{
    /// <summary>
    /// Returns a deep clone of this object.
    /// </summary>
    public Odso deepClone()
    {
        Odso rhs = (Odso)memberwiseClone();

        rhs.mFieldMapDatas = new OdsoFieldMapDataCollection();
        for (OdsoFieldMapData fieldMapData : mFieldMapDatas)
            rhs.mFieldMapDatas.add(fieldMapData.deepClone());

        rhs.mRecipientDatas = new OdsoRecipientDataCollection();
        for (OdsoRecipientData odsoRecipientData : mRecipientDatas)
            rhs.mRecipientDatas.add(odsoRecipientData.deepClone());

        return rhs;
    }

    /// <summary>
    /// Specifies the character which shall be interpreted as the column delimiter used to separate columns within external data sources.
    /// The default value is 0 which means there is no column delimiter defined.
    /// </summary>
    /// <remarks>
    /// <para>RK I have never seen this in use.</para>
    /// </remarks>
    public char getColumnDelimiter() { return mColumnDelimiter; }
    public void setColumnDelimiter(char value) { mColumnDelimiter = value; }

    /// <summary>
    /// Specifies that a hosting application shall treat the first row of data in the specified external data
    /// source as a header row containing the names of each column in the data source.
    /// The default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>RK I have never seen this in use.</para>
    /// </remarks>
    public boolean getFirstRowContainsColumnNames() { return mFirstRowContainsColumnNames; }
    public void setFirstRowContainsColumnNames(boolean value) { mFirstRowContainsColumnNames = value; }

    /// <summary>
    /// Specifies the location of the external data source to be connected to a document to perform the mail merge.
    /// The default value is an empty string.
    /// </summary>
    public String getDataSource() { return mDataSource; }
    public void setDataSource(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mDataSource = value;
    }

    /// <summary>
    /// Specifies the particular set of data that a source shall be connected to within an external data source.
    /// The default value is an empty string.
    /// </summary>
    public String getTableName() { return mTableName; }
    public void setTableName(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mTableName = value;
    }

    /// <summary>
    /// Specifies the type of the external data source to be connected to as part of the ODSO connection information for this mail merge.
    /// The default value is <see cref="OdsoDataSourceType.Default"/>.
    /// </summary>
    /// <remarks>
    /// <para>This setting is purely a suggestion of the data source type that is being used for this mail merge.</para>
    /// </remarks>
    public /*OdsoDataSourceType*/int getDataSourceType() { return mDataSourceType; }
    public void setDataSourceType(/*OdsoDataSourceType*/int value) { mDataSourceType = value; }

    /// <summary>
    /// Specifies the Universal Data Link (UDL) connection string used to connect to an external data source.
    /// The default value is an empty string.
    /// </summary>
    public String getUdlConnectString() { return mUdlConnectString; }
    public void setUdlConnectString(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mUdlConnectString = value;
    }

    /// <summary>
    /// Gets or sets a collection of objects that specify how columns from the external data source 
    /// are mapped to the predefined merge field names in the document.
    /// This object is never <c>null</c>.
    /// </summary>
    public OdsoFieldMapDataCollection getFieldMapDatas() { return mFieldMapDatas; }
    public void setFieldMapDatas(OdsoFieldMapDataCollection value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mFieldMapDatas = value;
    }

    /// <summary>
    /// Gets or sets a collection of objects that specify inclusion/exclusion of individual records in the mail merge.
    /// This object is never <c>null</c>.
    /// </summary>
    public OdsoRecipientDataCollection getRecipientDatas() { return mRecipientDatas; }
    public void setRecipientDatas(OdsoRecipientDataCollection value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mRecipientDatas = value;
    }

    private char mColumnDelimiter;
    private boolean mFirstRowContainsColumnNames = false;
    private String mDataSource = "";
    private String mTableName = "";
    private /*OdsoDataSourceType*/int mDataSourceType = OdsoDataSourceType.DEFAULT;
    private String mUdlConnectString = "";
    private OdsoFieldMapDataCollection mFieldMapDatas = new OdsoFieldMapDataCollection();
    private OdsoRecipientDataCollection mRecipientDatas = new OdsoRecipientDataCollection();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

