// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// Represents a feature supported by HTML import.
/// </summary>
class Feature
{
    /// <summary>
    /// Creates a new feature with no specific version.
    /// </summary>
    /// <param name="name">A feature name. Feature names are case-insensitive.</param>
    Feature(String name)
    {
        Debug.assertTrue(StringUtil.hasChars(name));

        mName = msString.toLowerInvariant(name);
    }

    /// <summary>
    /// Creates a new feature and initializes it with specified values.
    /// </summary>
    /// <param name="name">A feature name. Feature names are case-insensitive.</param>
    /// <param name="majorVersion">The major part of a feature version. Must be non-negative.</param>
    /// <param name="minorVersion">The minor part of a feature version. Must be non-negative.</param>
    Feature(String name, int majorVersion, int minorVersion)
    {
    	this(name);
	
        Debug.assertTrue(majorVersion >= 0);
        Debug.assertTrue(minorVersion >= 0);

        mVersion = new NumericVersionVector(majorVersion, minorVersion);
    }

    /// <summary>
    /// Gets the feature name. Names are always in lower case.
    /// </summary>
    String getName() { return mName; }

    /// <summary>
    /// Gets the version of the feature.
    /// </summary>
    NumericVersionVector getVersion() { return mVersion; }

    public /*override*/ String toString()
    {
        return (mVersion != null)
            ? mName + " " + mVersion
            : mName;
    }

    private /*final*/ String mName;
    private /*final*/ NumericVersionVector mVersion;
}

