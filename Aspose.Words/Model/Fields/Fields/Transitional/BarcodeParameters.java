// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/03/2015 by Vadim Polienko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Container class for barcode parameters to pass-through to BarcodeGenerator.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// The set of parameters are according to DISPLAYBARCODE field options.
/// See the exact list at <a href="https://msdn.microsoft.com/en-us/library/hh745901(v=office.12).aspx"/>
/// </remarks>
public class BarcodeParameters
{
    /// <summary>
    /// Bar code type.
    /// </summary>
    public String getBarcodeType() { return mBarcodeType; }; public void setBarcodeType(String value) { mBarcodeType = value; };

    private String mBarcodeType;

    /// <summary>
    /// Data to be encoded.
    /// </summary>
    public String getBarcodeValue() { return mBarcodeValue; }; public void setBarcodeValue(String value) { mBarcodeValue = value; };

    private String mBarcodeValue;

    /// <summary>
    /// Bar code image height (in twips - 1/1440 inches)
    /// </summary>
    public String getSymbolHeight() { return mSymbolHeight; }; public void setSymbolHeight(String value) { mSymbolHeight = value; };

    private String mSymbolHeight;

    /// <summary>
    /// Bar code foreground color (0x000000 - 0xFFFFFF)
    /// </summary>
    public String getForegroundColor() { return mForegroundColor; }; public void setForegroundColor(String value) { mForegroundColor = value; };

    private String mForegroundColor;

    /// <summary>
    /// Bar code background color (0x000000 - 0xFFFFFF)
    /// </summary>
    public String getBackgroundColor() { return mBackgroundColor; }; public void setBackgroundColor(String value) { mBackgroundColor = value; };

    private String mBackgroundColor;

    /// <summary>
    /// Rotation of the barcode symbol. Valid values are [0, 3].
    /// </summary>
    public String getSymbolRotation() { return mSymbolRotation; }; public void setSymbolRotation(String value) { mSymbolRotation = value; };

    private String mSymbolRotation;

    /// <summary>
    /// Scaling factor for the symbol. The value is in whole percentage points and the valid values are [10, 1000].
    /// </summary>
    public String getScalingFactor() { return mScalingFactor; }; public void setScalingFactor(String value) { mScalingFactor = value; };

    private String mScalingFactor;

    /// <summary>
    /// Style of a Point of Sale barcode (barcode types UPCA|UPCE|EAN13|EAN8). The valid values (case insensitive) are [STD|SUP2|SUP5|CASE].
    /// </summary>
    public String getPosCodeStyle() { return mPosCodeStyle; }; public void setPosCodeStyle(String value) { mPosCodeStyle = value; };

    private String mPosCodeStyle;

    /// <summary>
    /// Style of a Case Code for barcode type ITF14. The valid values are [STD|EXT|ADD]
    /// </summary>
    public String getCaseCodeStyle() { return mCaseCodeStyle; }; public void setCaseCodeStyle(String value) { mCaseCodeStyle = value; };

    private String mCaseCodeStyle;

    /// <summary>
    /// Error correction level of QR Code. Valid values are [0, 3].
    /// </summary>
    public String getErrorCorrectionLevel() { return mErrorCorrectionLevel; }; public void setErrorCorrectionLevel(String value) { mErrorCorrectionLevel = value; };

    private String mErrorCorrectionLevel;

    /// <summary>
    /// Whether to display barcode data (text) along with image.
    /// </summary>
    public boolean getDisplayText() { return mDisplayText; }; public void setDisplayText(boolean value) { mDisplayText = value; };

    private boolean mDisplayText;

    /// <summary>
    /// Whether to add Start/Stop characters for barcode types NW7 and CODE39.
    /// </summary>
    public boolean getAddStartStopChar() { return mAddStartStopChar; }; public void setAddStartStopChar(boolean value) { mAddStartStopChar = value; };

    private boolean mAddStartStopChar;

    /// <summary>
    /// Whether to fix the check digit if it’s invalid.
    /// </summary>
    public boolean getFixCheckDigit() { return mFixCheckDigit; }; public void setFixCheckDigit(boolean value) { mFixCheckDigit = value; };

    private boolean mFixCheckDigit;

    // Below are parameters for old-fashioned Barcode field.

    /// <summary>
    /// Barcode postal address.
    /// </summary>
    public String getPostalAddress() { return mPostalAddress; }; public void setPostalAddress(String value) { mPostalAddress = value; };

    private String mPostalAddress;

    /// <summary>
    /// Whether <see cref="PostalAddress"/> is the name of a bookmark.
    /// </summary>
    public boolean isBookmark() { return mIsBookmark; }; public void isBookmark(boolean value) { mIsBookmark = value; };

    private boolean mIsBookmark;

    /// <summary>
    /// Type of a Facing Identification Mark (FIM).
    /// </summary>
    public String getFacingIdentificationMark() { return mFacingIdentificationMark; }; public void setFacingIdentificationMark(String value) { mFacingIdentificationMark = value; };

    private String mFacingIdentificationMark;

    /// <summary>
    /// Whether <see cref="PostalAddress"/> is a U.S. postal address.
    /// </summary>
    public boolean isUSPostalAddress() { return mIsUSPostalAddress; }; public void isUSPostalAddress(boolean value) { mIsUSPostalAddress = value; };

    private boolean mIsUSPostalAddress;
}

