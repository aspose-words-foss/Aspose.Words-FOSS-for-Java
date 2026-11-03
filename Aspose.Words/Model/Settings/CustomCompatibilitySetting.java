// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/02/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Custom compatibility settings. 
/// The semantics for this element are implementation defined by the creator.
/// </summary>
/// <remarks>Defined in ISO 29500 spec, Part 1, §17.15.3.4</remarks>
class CustomCompatibilitySetting implements Cloneable
{
    CustomCompatibilitySetting(String name, String uri, String value)
    {
        mName = name;
        mUri = uri;
        mValue = value;
    }
    
    /// <summary>
    /// Specifies the name of a custom compatibility setting.
    /// </summary>
    String getName() { return mName; }
    
    /// <summary>
    /// Specifies the namespace under which the compatibility setting is defined.
    /// </summary>
    String getUri() { return mUri; }


    /// <summary>
    /// Specifies the value of a custom compatibility setting.
    /// This value is interpreted using the implementation-defined behavior 
    /// published by the creator of this property.
    /// </summary>
    String getValue() { return mValue; }
    void setValue(String value) { mValue = value; }

    CustomCompatibilitySetting deepClone()
    {
        return (CustomCompatibilitySetting) this.memberwiseClone();
    }

    private /*final*/ String mName;
    private /*final*/ String mUri;
    private String mValue;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

