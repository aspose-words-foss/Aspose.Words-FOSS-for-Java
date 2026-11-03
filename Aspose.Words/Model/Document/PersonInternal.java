// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Stores contact information for an author of a comment or revision in a document.
/// </summary>
/// <remarks>
/// Using identity information that is stored in the class, MS Word gets and displays additional information about
/// author of a comment/revision.
/// </remarks>
class PersonInternal implements Cloneable
{
    /// <summary>
    /// Makes a deep copy of the person.
    /// </summary>
    PersonInternal deepClone()
    {
        return (PersonInternal)memberwiseClone();
    }

    /// <summary>
    /// Gets/sets author name to which this person is associated.
    /// </summary>
    /// <remarks>
    /// By this property a person is mapped to a comment or revision. It equals to <see cref="Comment.Author"/> or
    /// <see cref="RevisionBase.Author"/>.
    /// </remarks>
    String getAuthor() { return mAuthor; }
    void setAuthor(String value) { mAuthor = value; }

    /// <summary>
    /// Gets/sets an identity provider that produces value for the subsequent <see cref="UserId"/> property.
    /// </summary>
    /*ContactIdentityProvider*/int getIdentityProvider() { return mIdentityProvider; }
    void setIdentityProvider(/*ContactIdentityProvider*/int value) { mIdentityProvider = value; }

    /// <summary>
    /// Gets/sets a unique user ID for a person.
    /// </summary>
    String getUserId() { return mUserId; }
    void setUserId(String value) { mUserId = value; }

    private String mAuthor;
    private /*ContactIdentityProvider*/int mIdentityProvider;
    private String mUserId;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

