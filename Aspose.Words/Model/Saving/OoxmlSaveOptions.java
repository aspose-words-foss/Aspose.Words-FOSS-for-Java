// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/07/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Can be used to specify additional options when saving a document into the <see cref="Words.SaveFormat.Docx"/>,
/// <see cref="Words.SaveFormat.Docm"/>, <see cref="Words.SaveFormat.Dotx"/>, <see cref="Words.SaveFormat.Dotm"/> or
/// <see cref="Words.SaveFormat.FlatOpc"/> format.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/specify-save-options/">Specify Save Options</a> documentation article.</para>
/// </summary>
public class OoxmlSaveOptions extends SaveOptions
{
    /// <summary>
    /// Initializes a new instance of this class that can be used to save a document in the <see cref="Words.SaveFormat.Docx"/> format.
    /// </summary>
    public OoxmlSaveOptions()
    {
    	this(SaveFormat.DOCX);
	
    }

    /// <summary>
    /// Initializes a new instance of this class that can be used to save a document in the <see cref="Words.SaveFormat.Docx"/>,
    /// <see cref="Words.SaveFormat.Docm"/>, <see cref="Words.SaveFormat.Dotx"/>, <see cref="Words.SaveFormat.Dotm"/> or
    /// <see cref="Words.SaveFormat.FlatOpc"/> format.
    /// </summary>
    /// <param name="saveFormat">Can be <see cref="Words.SaveFormat.Docx"/>, <see cref="Words.SaveFormat.Docm"/>,
    /// <see cref="Words.SaveFormat.Dotx"/>, <see cref="Words.SaveFormat.Dotm"/> or <see cref="Words.SaveFormat.FlatOpc"/>.
    /// </param>
    public OoxmlSaveOptions(/*SaveFormat*/int saveFormat)
    {
        setWriteClrSchemeMapping(true);
        setWriteWordCountOption(true);
        setWriteDoNotTrackMovesCorrectly(true);
        setWriteExtendedIds(true);
        setWrite2019LatentStyle(true);
        setWriteLatentStyles(true);
        setCompressionLevel(CompressionLevel.NORMAL);
        setZip64Mode(Zip64Mode.NEVER);

        setSaveFormatCore(saveFormat);
    }

    /// <summary>
    /// Returns options to save to the DOCX format with specifying document compliance.
    /// </summary>
    /// <dev>
    /// We can't use ctor OoxmlSaveOptions(OoxmlComplianceCore) here since it is name conglict with
    /// OoxmlSaveOptions(SaveFormat) ctor after autoporting:
    /// Both SaveFormat and OoxmlComplianceCore enums are autoported as ints so we get two
    /// OoxmlSaveOptions(int) ctors in Java.
    /// </dev>
    static OoxmlSaveOptions docxWithCompliance(/*OoxmlComplianceCore*/int compliance)
    {
        OoxmlSaveOptions options = new OoxmlSaveOptions(SaveFormat.DOCX); { options.setComplianceCore(compliance); }
        return options;
    }

    /// <summary>
    /// Sets options we use for unit testing.
    /// </summary>
    /*override*/ void setTestMode()
    {
        super.setTestMode();

        // We need it to avoid accepting lot of golds.
        setWriteClrSchemeMapping(false);
        setWriteWordCountOption(false);
        setWriteDoNotTrackMovesCorrectly(false);
        setWriteExtendedIds(false);
        setWrite2019LatentStyle(false);
    }

    /// <summary>
    /// Specifies the format in which the document will be saved if this save options object is used.
    /// Can be <see cref="Words.SaveFormat.Docx"/>, <see cref="Words.SaveFormat.Docm"/>,
    /// <see cref="Words.SaveFormat.Dotx"/>, <see cref="Words.SaveFormat.Dotm"/> or <see cref="Words.SaveFormat.FlatOpc"/>.
    /// </summary>
    public /*override*/ /*SaveFormat*/int getSaveFormat() { return mSaveFormat; }
    public /*override*/ void setSaveFormat(/*SaveFormat*/int value) { setSaveFormatCore(value); }

    private void setSaveFormatCore(/*SaveFormat*/int saveFormat)
    {
        switch (saveFormat)
        {
            case SaveFormat.DOCX:
            case SaveFormat.DOCM:
            case SaveFormat.DOTX:
            case SaveFormat.DOTM:
            case SaveFormat.FLAT_OPC:
            case SaveFormat.FLAT_OPC_MACRO_ENABLED:
            case SaveFormat.FLAT_OPC_TEMPLATE:
            case SaveFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED:
                mSaveFormat = saveFormat;
                break;
            default:
                throw new IllegalArgumentException("An invalid SaveFormat for this options type was chosen.");
        }
    }

    /// <summary>
    /// Gets/sets a password to encrypt document using ECMA376 Standard encryption algorithm.
    /// </summary>
    /// <remarks>
    /// <para>In order to save document without encryption this property should be <c>null</c> or empty string.</para>
    /// </remarks>
    public String getPassword() { return mPassword; }; public void setPassword(String value) { mPassword = value; };

    private String mPassword;

    /// <summary>
    /// Specifies the OOXML version for the output document.
    /// The default value is <see cref="OoxmlCompliance.Ecma376_2006"/>.
    /// </summary>
    public /*OoxmlCompliance*/int getCompliance()
    {
        switch (getComplianceCore())
        {
            case OoxmlComplianceCore.ECMA_376:
                return OoxmlCompliance.ECMA_376_2006;
            case OoxmlComplianceCore.ISO_TRANSITIONAL:
                return OoxmlCompliance.ISO_29500_2008_TRANSITIONAL;
            case OoxmlComplianceCore.ISO_STRICT:
                return OoxmlCompliance.ISO_29500_2008_STRICT;
            default:
                throw new IllegalStateException("Unknown OOXML version value.");
        }
    }
    public void setCompliance(/*OoxmlCompliance*/int value)
    {
        switch (value)
        {
            case OoxmlCompliance.ECMA_376_2006:
                setComplianceCore(OoxmlComplianceCore.ECMA_376);
                break;
            case OoxmlCompliance.ISO_29500_2008_TRANSITIONAL:
                setComplianceCore(OoxmlComplianceCore.ISO_TRANSITIONAL);
                break;
            case OoxmlCompliance.ISO_29500_2008_STRICT:
                setComplianceCore(OoxmlComplianceCore.ISO_STRICT);
                break;
            default:
                throw new IllegalStateException("Unknown OOXML version value.");
        }
    }

    /// <summary>
    /// Keeps original representation of legacy control characters.
    /// </summary>
    public boolean getKeepLegacyControlChars() { return mKeepLegacyControlChars; }; public void setKeepLegacyControlChars(boolean value) { mKeepLegacyControlChars = value; };

    private boolean mKeepLegacyControlChars;

    /// <summary>
    /// Specifies the compression level used to save document.
    /// The default value is <see cref="Aspose.Words.Saving.CompressionLevel.Normal"/>.
    /// </summary>
    public /*CompressionLevel*/int getCompressionLevel() { return mCompressionLevel; }; public void setCompressionLevel(/*CompressionLevel*/int value) { mCompressionLevel = value; };

    private /*CompressionLevel*/int mCompressionLevel;

    /// <summary>
    /// Specifies whether or not to use ZIP64 format extensions for the output document.
    /// The default value is <see cref="Saving.Zip64Mode.Never"/>.
    /// </summary>
    /// <seealso cref="Zip64Mode"/>
    public /*Zip64Mode*/int getZip64Mode() { return mZip64Mode; }; public void setZip64Mode(/*Zip64Mode*/int value) { mZip64Mode = value; };

    private /*Zip64Mode*/int mZip64Mode;

    /// <summary>
    /// Gets or sets <see cref="Aspose.Words.Saving.DigitalSignatureDetails"/> object used to sign a document.
    /// </summary>
    public DigitalSignatureDetails getDigitalSignatureDetails() { return mDigitalSignatureDetails; }; public void setDigitalSignatureDetails(DigitalSignatureDetails value) { mDigitalSignatureDetails = value; };

    private DigitalSignatureDetails mDigitalSignatureDetails;

    /// <summary>
    /// Specifies the OOXML version for the output document. This is internal version of
    /// <see cref="Compliance"/> that allows setting officially not supported document format.
    /// Result OOXML version that is used on saving a document is a combination with
    /// <see cref="OoxmlComplianceInfo.Compliance"/>.
    /// </summary>
    /*OoxmlComplianceCore*/int getComplianceCore() { return mComplianceCore; }
    void setComplianceCore(/*OoxmlComplianceCore*/int value)
    {
        mComplianceCore = value;
        setUserSetCompliance(true);
    }

    /// <summary>
    /// Used for unit testing only, not really needed for the users I think.
    /// </summary>
    boolean getConvertDmlPictureToVml() { return mConvertDmlPictureToVml; }; void setConvertDmlPictureToVml(boolean value) { mConvertDmlPictureToVml = value; };

    private boolean mConvertDmlPictureToVml;

    /// <summary>
    /// andrnosk: This property is FALSE for test mode and TRUE for Release.
    /// We need it to avoid accepting lot of golds.
    /// </summary>
    boolean getWriteClrSchemeMapping() { return mWriteClrSchemeMapping; }; void setWriteClrSchemeMapping(boolean value) { mWriteClrSchemeMapping = value; };

    private boolean mWriteClrSchemeMapping;

    /// <summary>
    /// Refers to WORDSNET-6575 Used to avoid accept lot of golds.
    /// </summary>
    boolean getWriteWordCountOption() { return mWriteWordCountOption; }; void setWriteWordCountOption(boolean value) { mWriteWordCountOption = value; };

    private boolean mWriteWordCountOption;

    /// <summary>
    /// Refers to WORDSNET-9070 Used to avoid accept lot of golds.
    /// </summary>
    boolean getWriteDoNotTrackMovesCorrectly() { return mWriteDoNotTrackMovesCorrectly; }; void setWriteDoNotTrackMovesCorrectly(boolean value) { mWriteDoNotTrackMovesCorrectly = value; };

    private boolean mWriteDoNotTrackMovesCorrectly;

    /// <summary>
    /// Refers to WORDSNET-14123 Used to avoid accepting lot of golds.
    /// </summary>
    boolean getWriteExtendedIds() { return mWriteExtendedIds; }; void setWriteExtendedIds(boolean value) { mWriteExtendedIds = value; };

    private boolean mWriteExtendedIds;

    /// <summary>
    /// Refers to WORDSNET-22367 Used to avoid accepting lot of golds.
    /// </summary>
    boolean getWrite2019LatentStyle() { return mWrite2019LatentStyle; }; void setWrite2019LatentStyle(boolean value) { mWrite2019LatentStyle = value; };

    private boolean mWrite2019LatentStyle;

    /// <summary>
    /// Returns true if user has explicitly set <see cref="OoxmlCompliance"/>.
    /// AW can identify compliance by analyzing source DOCX files upon loading, but if user overrides compliance,
    /// AW shall not use its derived information during saving, but shall use user-provided compliance info.
    /// </summary>
    boolean getUserSetCompliance() { return mUserSetCompliance; }; private void setUserSetCompliance(boolean value) { mUserSetCompliance = value; };

    private boolean mUserSetCompliance;

    /// <summary>
    /// Controls whether LatentStyles collection is written to output document file.
    /// </summary>
    /// <remarks>
    /// Relates to WORDSNET-24602
    /// </remarks>
    boolean getWriteLatentStyles() { return mWriteLatentStyles; }; void setWriteLatentStyles(boolean value) { mWriteLatentStyles = value; };

    private boolean mWriteLatentStyles;

    private /*SaveFormat*/int mSaveFormat;
    private /*OoxmlComplianceCore*/int mComplianceCore = OoxmlComplianceCore.ECMA_376;
}

