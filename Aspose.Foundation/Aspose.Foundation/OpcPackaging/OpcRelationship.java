// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/07/2007 by Vladimir Averkin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents an OPC relationship.
/// </summary>
public class OpcRelationship implements Cloneable
{
    public OpcRelationship(String id, String type, String target, boolean isExternal)
    {
        // Check that arguments are not empty.
        ArgumentUtil.checkHasChars(id, "id");
        ArgumentUtil.checkHasChars(type, "type");
        ArgumentUtil.checkHasChars(target, "target");

        mId = id;
        mTarget = target;
        mType = type;
        mIsExternal = isExternal;
    }

    /// <summary>
    /// Clones this instance of relationship.
    /// </summary>
    OpcRelationship deepClone()
    {
        return (OpcRelationship)memberwiseClone();
    }

    public String getId() { return mId; }

    public String getType() { return mType; }

    /// <summary>
    /// This value is as read from the package (escaped URI).
    /// 
    /// When the target is internal, can be absolute or relative.
    /// If absolute, then it basically equals the target part name.
    /// If relative, it needs to be converted to absolute to form a complete target part name.
    /// 
    /// When the target is external, this is probably always absolute. 
    /// </summary>
    public String getTarget() { return mTarget; }

    public boolean isExternal() { return mIsExternal; }

    private /*final*/ String mId;
    private /*final*/ String mType;
    private /*final*/ String mTarget;
    private /*final*/ boolean mIsExternal;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

