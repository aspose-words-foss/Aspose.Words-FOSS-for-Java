// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/10/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies information about the user.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
public class UserInformation
{

    /// <summary>
    /// Gets or sets the user's name.
    /// </summary>
    public String getName() { return mName; }; public void setName(String value) { mName = value; };

    private String mName;

    /// <summary>
    /// Gets or sets the user's initials.
    /// </summary>
    public String getInitials() { return mInitials; }; public void setInitials(String value) { mInitials = value; };

    private String mInitials;

    /// <summary>
    /// Gets or sets the user's postal address.
    /// </summary>
    public String getAddress() { return mAddress; }; public void setAddress(String value) { mAddress = value; };

    private String mAddress;

    /// <summary>
    /// Default user information.
    /// </summary>
    /// <remarks>
    /// Use the <see cref="FieldOptions.CurrentUser"/> property to specify user information for single document.
    /// </remarks>
    public static UserInformation getDefaultUser() { return mDefaultUser; };

    private static UserInformation mDefaultUser;
    static/* UserInformation()*/
    {
        mDefaultUser = new UserInformation();
    }
}

