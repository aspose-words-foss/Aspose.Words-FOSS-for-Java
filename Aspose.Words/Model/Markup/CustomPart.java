// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/05/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.ArrayUtil;


/// <summary>
/// Represents a custom (arbitrary content) part, that is not defined by the ISO/IEC 29500 standard.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-content-control-sdt/">Structured Document Tags or Content Control</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>This class represents an OOXML part that is a target of an "unknown relationship".
/// All relationships not defined within ISO/IEC 29500 are considered "unknown relationships".
/// Unknown relationships are permitted within an Office Open XML document provided that they 
/// conform to relationship markup guidelines.</para>
/// 
/// <para>Microsoft Word preserves custom parts during open/save cycles. Some additional info can be found 
/// here http://blogs.msdn.com/dmahugh/archive/2006/11/25/arbitrary-content-in-an-opc-package.aspx </para>
/// 
/// <para>Aspose.Words also roundtrips custom parts and in addition, allows to programmatically access 
/// such parts via the <see cref="CustomPart"/> and <see cref="CustomPartCollection"/> objects.</para>
/// 
/// <para>Do not confuse custom parts with Custom XML Data. Use <see cref="CustomXmlPart"/> if you need 
/// to access Custom XML Data.</para>
/// 
/// <seealso cref="CustomPartCollection"/>
/// <seealso cref="Document.PackageCustomParts"/>
/// </remarks>
public class CustomPart implements Cloneable
{
    /// <summary>
    /// Gets or sets this part's absolute name within the OOXML package or the target URL.
    /// </summary>
    /// <remarks>
    /// <para>If the relationship target is internal, then this property is the absolute part name within the package.
    /// If the relationship target is external, then this property is the target URL.</para>
    /// 
    /// <para>The default value is an empty string. A valid value must be a non-empty string.</para>
    /// 
    /// <seealso cref="IsExternal"/>
    /// </remarks>
    public String getName() { return mName; }
    public void setName(String value)
    {
        ArgumentUtil.checkNotNull(value, "name");
        mName = value;
    }

    /// <summary>
    /// Gets or sets the relationship type from the parent part to this custom part.
    /// </summary>
    /// <remarks>
    /// <para>The relationship type for a custom part must be "unknown" e.g. a custom relationship type, 
    /// not one of the relationship types defined within ISO/IEC 29500.</para>
    /// 
    /// <para>The default value is an empty string. A valid value must be a non-empty string.</para>
    /// </remarks>
    public String getRelationshipType() { return mRelationshipType; }
    public void setRelationshipType(String value)
    {
        ArgumentUtil.checkNotNull(value, "relationshipType");
        mRelationshipType = value;
    }

    /// <summary>
    /// False if this custom part is stored inside the OOXML package. True if this custom part is an external target.
    /// </summary>
    /// <remarks>
    /// <para>The default value is <c>false</c>.</para>
    /// 
    /// <seealso cref="Name"/>
    /// </remarks>
    public boolean isExternal() { return mIsExternal; }
    public void isExternal(boolean value) { mIsExternal = value; }

    /// <summary>
    /// Specifies the content type of this custom part.
    /// </summary>
    /// <remarks>
    /// <para>This property is applicable only when <see cref="IsExternal"/> is <c>false</c>.</para>
    /// 
    /// <para>The default value is an empty string. A valid value must be a non-empty string.</para>
    /// </remarks>
    public String getContentType() { return mContentType; }
    public void setContentType(String value)
    {
        ArgumentUtil.checkNotNull(value, "contentType");
        mContentType = value;
    }

    /// <summary>
    /// Contains the data of this custom part.
    /// </summary>
    /// <remarks>
    /// <para>This property is applicable only when <see cref="IsExternal"/> is <c>false</c>.</para>
    /// 
    /// <para>The default value is an empty byte array. The value cannot be <c>null</c>.</para>
    /// </remarks>
    
    public byte[] getData() { return mData; }
    
    public void setData(byte[] value)
    {
        ArgumentUtil.checkNotNull(value, "data");
        mData = value;
    }

    /// <summary>
    /// Makes a "deep enough" copy of the object. 
    /// Does not duplicate the bytes of the <see cref="Data"/> value.
    /// </summary>
    public CustomPart deepClone()
    {
        // Shallow copy is enough here.
        return (CustomPart)memberwiseClone();
    }

    // WORDSNET-10389 Custom part can has relationship with another part as its child.
    String getParentPartName() { return mParentPartName; }
    void setParentPartName(String value) { mParentPartName = value; }

    /// <summary>
    /// Custom part original identifier from the package.
    /// WORDSNET-13706 Store the original identifier of the nested custom part and
    /// use it while document saving.
    /// </summary>
    String getOriginalId() { return mOriginalId; }
    void setOriginalId(String value) { mOriginalId = value; }

    private String mOriginalId;
    private String mName = "";
    private String mRelationshipType = "";
    private boolean mIsExternal;

    private String mContentType = "";
    private byte[] mData = ArrayUtil.EMPTY_BYTE_ARRAY;

    private String mParentPartName;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

