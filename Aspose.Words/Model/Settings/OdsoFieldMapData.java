// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/10/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Language;
import com.aspose.ArgumentUtil;


/// <summary>
/// Specifies how a column in the external data source shall be mapped to the predefined merge fields within the document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/mail-merge-and-reporting/">Mail Merge and Reporting</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>Microsoft Word provides some predefined merge field names that it allows to insert into a document as MERGEFIELD or 
/// use in the ADDRESSBLOCK or GREETINGLINE fields. The information specified in <see cref="OdsoFieldMapData"/>
/// allows to map one column in the external data source to a single predefined merge field.</para>
/// 
/// <seealso cref="OdsoFieldMapDataCollection"/>
/// <seealso cref="Odso"/>
/// </remarks>
public class OdsoFieldMapData implements Cloneable
{
    /// <summary>
    /// Returns a deep clone of this object.
    /// </summary>
    public OdsoFieldMapData deepClone()
    {
        return (OdsoFieldMapData)memberwiseClone();
    }

    /// <summary>
    /// Specifies the zero-based index of the column within an external data source which shall be
    /// mapped to the local name of a specific MERGEFIELD field.
    /// The default value is 0.
    /// </summary>
    public int getColumn() { return mColumn; }
    public void setColumn(int value)
    {
        if (!isColumnValid(value))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        mColumn = value;
    }

    void setColumnSafe(int value)
    {
        if (isColumnValid(value))
            mColumn = value;
    }

    private static boolean isColumnValid(int value)
    {
        return (value >= 0);
    }

    /// <summary>
    /// Specifies that the contents of the AddressBlock MERGEFIELD field shall be dynamically ordered based on the country 
    /// associated with the current record or if the country-invariant version of the address field shall be used in its place.
    /// The default value is <c>false</c>.
    /// </summary>
    boolean getDynamicAddress() { return mDynamicAddress; }
    void setDynamicAddress(boolean value) { mDynamicAddress = value; }

    /// <summary>
    /// Specifies the language ID for the language which was used to generate the merge field name.
    /// The default value is <see cref="Aspose.Language.EnglishUS"/>.
    /// </summary>
    /*Language*/int getLanguage() { return mLanguage; }
    void setLanguage(/*Language*/int value) { mLanguage = value; }

    /// <summary>
    /// Specifies the predefined merge field name which shall be mapped to the column number 
    /// specified by the <see cref="Column"/> property within this field mapping.
    /// The default value is an empty string.
    /// </summary>
    public String getMappedName() { return mMappedName; }
    public void setMappedName(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mMappedName = value;
    }

    /// <summary>
    /// Specifies the column name within an external data source for the column whose 
    /// index is specified by the <see cref="Column"/> property.
    /// The default value is an empty string.
    /// </summary>
    public String getName() { return mName; }
    public void setName(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mName = value;
    }

    /// <summary>
    /// Specifies if a given mail merge field has been mapped to a column in the given external data source or not.
    /// The default value is <see cref="OdsoFieldMappingType.Default"/>.
    /// </summary>
    
    public /*OdsoFieldMappingType*/int getType() { return mType; }
    
    public void setType(/*OdsoFieldMappingType*/int value) { mType = value; }

    private int mColumn = 0;
    private boolean mDynamicAddress = false;
    private /*Language*/int mLanguage = Language.ENGLISH_US;
    private String mMappedName = "";
    private String mName = "";
    private /*OdsoFieldMappingType*/int mType = OdsoFieldMappingType.DEFAULT;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

