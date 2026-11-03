// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// The token that represents a DOCTYPE node.
/// </summary>
class HtmlDoctypeToken extends HtmlToken
{
    /// <summary>
    /// Constructor.
    /// </summary>
    /// <param name="name">The name of the DOCTYPE.</param>
    /// <param name="publicId">The public identifier.</param>
    /// <param name="systemId">The system identifier.</param>
    /// <param name="forceQuirks">The 'force-quirks' flag value.</param>
    HtmlDoctypeToken(String name, String publicId, String systemId, boolean forceQuirks)
    {
    	super(HtmlTokenType.DOCTYPE);
	
        Debug.assertTrue(name != null);

        mName = name;
        mPublicId = publicId;
        mSystemId = systemId;
        mForceQuirks = forceQuirks;
    }

    /// <summary>
    /// Gets the name of the DOCTYPE.
    /// </summary>
    String getName() { return mName; }

    /// <summary>
    /// Gets the public identifier of the DOCTYPE.
    /// </summary>
    String getPublicId() { return mPublicId; }

    /// <summary>
    /// Gets the system identifier of the DOCTYPE.
    /// </summary>
    String getSystemId() { return mSystemId; }

    /// <summary>
    /// Gets the 'force-quirks' flag value set by the tokenizer.
    /// </summary>
    boolean getForceQuirks() { return mForceQuirks; }

    private /*final*/ String mName;

    private /*final*/ String mPublicId;

    private /*final*/ String mSystemId;

    private /*final*/ boolean mForceQuirks;
}
