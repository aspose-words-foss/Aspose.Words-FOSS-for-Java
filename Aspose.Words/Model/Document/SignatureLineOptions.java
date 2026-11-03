// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/12/2015 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows to specify options for signature line being inserted. Used in <see cref="DocumentBuilder"/>.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-digital-signatures/">Work with Digital Signatures</a> documentation article.</para>
/// </summary>
public class SignatureLineOptions
{
    /// <summary>
    /// Gets or sets suggested signer of the signature line.
    /// Default value for this property is <b>empty string</b><ms> (<see cref="string.Empty"/>)</ms>.
    /// </summary>
    public String getSigner() { return mSigner; }
    public void setSigner(String value) { mSigner = value; }

    /// <summary>
    /// Gets or sets suggested signer's title.
    /// Default value for this property is <b>empty string</b><ms> (<see cref="string.Empty"/>)</ms>.
    /// </summary>
    public String getSignerTitle() { return mSignerTitle; }
    public void setSignerTitle(String value) { mSignerTitle = value; }

    /// <summary>
    /// Gets or sets suggested signer's e-mail address.
    /// Default value for this property is <b>empty string</b><ms> (<see cref="string.Empty"/>)</ms>.
    /// </summary>
    public String getEmail() { return mEmail; }
    public void setEmail(String value) { mEmail = value; }

    /// <summary>
    /// Gets or sets a value indicating that default instructions is shown in the Sign dialog.
    /// Default value for this property is <c>true</c>.
    /// </summary>
    public boolean getDefaultInstructions() { return mDefaultInstructions; }
    public void setDefaultInstructions(boolean value)
    {
        mDefaultInstructions = value;
        if (value)
            mInstructions = "";
    }

    /// <summary>
    /// Gets or sets instructions to the signer that are displayed on signing the signature line.
    /// Default value for this property is <b>empty string</b><ms> (<see cref="string.Empty"/>)</ms>.
    /// </summary>
    public String getInstructions() { return mInstructions; }
    public void setInstructions(String value) { mInstructions = value; }

    /// <summary>
    /// Gets or sets a value indicating that the signer can add comments in the Sign dialog.
    /// Default value for this property is <c>false</c>.
    /// </summary>
    public boolean getAllowComments() { return mAllowComments; }
    public void setAllowComments(boolean value) { mAllowComments = value; }

    /// <summary>
    /// Gets or sets a value indicating that sign date is shown in the signature line.
    /// Default value for this property is <c>true</c>.
    /// </summary>
    public boolean getShowDate() { return mShowDate; }
    public void setShowDate(boolean value) { mShowDate = value; }

    private String mSigner = "";
    private String mSignerTitle = "";
    private String mEmail = "";
    private boolean mDefaultInstructions = true;
    private String mInstructions = "";
    private boolean mAllowComments;
    private boolean mShowDate = true;
}

