// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2023 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a corporate (an organization) bibliography source contributor.
/// </summary>
public final class Corporate extends Contributor
{
    /// <summary>
    /// Initialize a new instance of the <see cref="Corporate"/> class.
    /// </summary>
    /// <param name="name">The name of an organization.</param>
    public Corporate(String name)
    {
        setName(name);
    }

    /// <summary>
    /// Gets or sets the name of an organization.
    /// </summary>
    public String getName() { return mName; }; public void setName(String value) { mName = value; };

    private String mName;

    /*override*/ Contributor deepClone()
    {
        return new Corporate(getName());
    }
}

