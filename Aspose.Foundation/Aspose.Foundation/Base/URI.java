// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/09/2024 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;

/// <summary>
/// The lightweight version of the <see cref="System.Uri"/> class.
/// </summary>
public class URI
{
    /// <summary>
    /// Initialize a new instance of the <see cref="URI"/> class
    /// with address and subaddress extracted from specified uri.
    /// </summary>
    public URI(String uri)
    {
    	this(UriUtil.getAddress(uri), UriUtil.getSubAddress(uri));
	
    }

    /// <summary>
    /// Initialize a new instance of the <see cref="URI"/> class with specified address and subaddress.
    /// </summary>
    public URI(String address, String subAddress)
    {
        mAddress = (address != null ? address : "");
        mSubAddress = (subAddress != null ? subAddress : "");
    }

    /// <summary>
    /// Gets the address part (before "#") of the current uri.
    /// </summary>
    public String getAddress() { return mAddress; };

    private  String mAddress;

    /// <summary>
    /// Gets the subaddress part (after "#") of the current uri.
    /// </summary>
    public String getSubAddress() { return mSubAddress; };

    private  String mSubAddress;

    /// <summary>
    /// Gets the value indicating the current uri has only subaddress part (after "#").
    /// </summary>
    public boolean isSubAddressOnly() { return msString.isNullOrEmpty(getAddress()); }

    /// <summary>
    /// Gets the value indicating the current uri has scheme.
    /// </summary>
    public boolean isHrefWithScheme() { return UriUtil.isHrefWithScheme(getAddress()); }

    /// <summary>
    /// Gets the value indicating the current is an absolute local file path or UNC path.
    /// </summary>
    public boolean isFilePath() { return UriUtil.isFilePath(getAddress()); }

    /// <summary>
    /// Returns full uri path from provided base uri and the current relative uri.
    /// </summary>
    public URI toAbsoluteUri(String baseUri)
    {
        return new URI(UriUtil.constructAbsoluteUri(baseUri, getAddress()), getSubAddress());
    }

    /// <summary>
    /// Returns current uri with the file scheme prefix "file:///".
    /// </summary>
    public URI withFileSchemePrefix()
    {
        return new URI(UriUtil.addFileSchemePrefix(getAddress()), getSubAddress());
    }

    /// <summary>
    /// Returns concatenated address and subaddress with optional escaping.
    /// </summary>
    public String toString(boolean escape)
    {
        return escape
            ? getFullUriEscaped()
            : getFullUri();
    }

    private String getFullUri()
    {
        return (mFullUri != null ? mFullUri : (mFullUri = UriUtil.appendSubAddress(getAddress(), getSubAddress())));
    }

    private String getFullUriEscaped()
    {
        if (mFullUriEscaped == null)
        {
            if (UriUtil.isHrefThatNeedsEscaping(getAddress()) || UriUtil.isHrefThatNeedsEscaping(getSubAddress()))
            {
                mFullUriEscaped = UriUtil.appendSubAddress(
                    UriUtil.escapeHrefAnyway(getAddress()),
                    UriUtil.escapeHrefAnyway(getSubAddress()));
            }
            else
            {
                mFullUriEscaped = getFullUri();
            }
        }

        return mFullUriEscaped;
    }

    private String mFullUri;
    private String mFullUriEscaped;
}

