// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents property value with its optional name.
/// </summary>
public class Property
{
    public Property(int id, String name, Object value)
    {
        mId = id;
        mName = name;
        mValue = value;
    }

    public boolean hasName() { return StringUtil.hasChars(getName()); }

    /// <summary>
    /// Property mId.
    /// </summary>
    public int getId() { return mId; }
    public void setId(int value) { mId = value; }

    /// <summary>
    /// Optional mName of the property. Normally only user defined properties have names
    /// in the structured storage.
    /// </summary>
    public String getName() { return mName; }
    public void setName(String value) { mName = value; }

    /// <summary>
    /// The mValue of the property.
    /// </summary>
    public Object getValue() { return mValue; }
    public void setValue(Object value) { mValue = value; }

    private int mId;
    private String mName;
    private Object mValue;
}

