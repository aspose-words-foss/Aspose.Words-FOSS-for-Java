// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/07/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Allows to specify additional options (such as password or base URI) when
/// loading a document into a <see cref="Document"/> object.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/specify-load-options/">Specify Load Options</a> documentation article.</para>
/// </summary>
public class LoadOptions
{
    /// <summary>
    /// Initializes a new instance of this class with default values.
    /// </summary>
    public LoadOptions()
    {
    }

    /// <summary>
    /// A shortcut to initialize a new instance of this class with the specified password to load an encrypted document.
    /// </summary>
    /// <param name="password">The password to open an encrypted document. Can be <c>null</c> or empty string.</param>
    public LoadOptions(String password)
    {
        mPassword = password;
    }

    /// <summary>
    /// A shortcut to initialize a new instance of this class with properties set to the specified values.
    /// </summary>
    /// <param name="loadFormat">The format of the document to be loaded.</param>
    /// <param name="password">The password to open an encrypted document. Can be <c>null</c> or empty string.</param>
    /// <param name="baseUri">The string that will be used to resolve relative URIs to absolute. Can be <c>null</c> or empty string.</param>
    public LoadOptions(/*LoadFormat*/int loadFormat, String password, String baseUri)
    {
        mLoadFormat = loadFormat;
        mPassword = password;
        mBaseUri = baseUri;
    }

    /// <summary>
    /// Determines whether the specified object is equal in value to the current object.
    /// </summary>
    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (obj.getClass() == LoadOptions.class)
        {
            LoadOptions other = (LoadOptions)obj;
            return (mLoadFormat == other.mLoadFormat) &&
                (msString.equals(mPassword, other.mPassword)) &&
                (msString.equals(mBaseUri, other.mBaseUri)) &&
                (mEncoding == other.mEncoding) &&
                (mResourceLoadingCallback == other.mResourceLoadingCallback) &&
                (mWarningCallback == other.mWarningCallback) &&
                (mPreserveIncludePictureField == other.mPreserveIncludePictureField) &&
                (mAllowTrailingWhitespaceForListItems == other.mAllowTrailingWhitespaceForListItems) &&
                mFontSettings.equals(other.mFontSettings) &&
                (mLoadMode == other.mLoadMode) &&
                // FOSS
                (mUpdateDirtyFields == other.mUpdateDirtyFields) &&
                (mConvertShapeToOfficeMath == other.mConvertShapeToOfficeMath) &&
                (mMsWordVersion == other.mMsWordVersion) &&
                (mConvertMetafilesToPng == other.mConvertMetafilesToPng) &&
                (getProgressCallback() == other.getProgressCallback()) &&
                (isLoadingBlankDocument() == other.isLoadingBlankDocument());
        }
        if (obj.getClass() == Document.class)
        {
            Document doc = (Document)obj;
            // FOSS
            return (mResourceLoadingCallback == doc.getResourceLoadingCallback()) &&
                (mWarningCallback == doc.getWarningCallback());
        }

        return false;
    }

    /// <summary>
    /// Copy constructor.
    /// </summary>
    LoadOptions(LoadOptions loadOptions)
    {
        if (loadOptions != null)
        {
            mLoadFormat = loadOptions.mLoadFormat;
            mPassword = loadOptions.mPassword;
            mBaseUri = loadOptions.mBaseUri;
            mEncoding = loadOptions.mEncoding;
            mResourceLoadingCallback = loadOptions.mResourceLoadingCallback;
            mWarningCallback = loadOptions.mWarningCallback;
            mPreserveIncludePictureField = loadOptions.mPreserveIncludePictureField;
            mAllowTrailingWhitespaceForListItems = loadOptions.mAllowTrailingWhitespaceForListItems;
            mFontSettings = loadOptions.mFontSettings;
            mLoadMode = loadOptions.mLoadMode;
            // FOSS
            mUpdateDirtyFields = loadOptions.mUpdateDirtyFields;
            mConvertShapeToOfficeMath = loadOptions.mConvertShapeToOfficeMath;
            mMsWordVersion = loadOptions.mMsWordVersion;
            mConvertMetafilesToPng = loadOptions.mConvertMetafilesToPng;
            setProgressCallback(loadOptions.getProgressCallback());
            isLoadingBlankDocument(loadOptions.isLoadingBlankDocument());
            mLanguagePreferences = loadOptions.getLanguagePreferences();
        }
    }

    /*virtual*/ LoadOptions deepClone()
    {
        return new LoadOptions(this);
    }

    /// <summary>
    /// Specifies the format of the document to be loaded.
    /// Default is <see cref="Aspose.Words.LoadFormat.Auto"/>.
    /// </summary>
    /// <remarks>
    /// <para>It is recommended that you specify the <see cref="Aspose.Words.LoadFormat.Auto"/> value and let Aspose.Words detect
    /// the file format automatically. If you know the format of the document you are about to load, you can specify the format
    /// explicitly and this will slightly reduce the loading time by the overhead associated with auto detecting the format.
    /// If you specify an explicit load format and it will turn out to be wrong, the auto detection will be invoked and a second
    /// attempt to load the file will be made.</para>
    /// </remarks>
    public /*LoadFormat*/int getLoadFormat() { return mLoadFormat; }
    public void setLoadFormat(/*LoadFormat*/int value) { mLoadFormat = value; }

    /// <summary>
    /// Gets or sets the password for opening an encrypted document.
    /// Can be <c>null</c> or empty string. Default is <c>null</c>.
    /// </summary>
    /// <remarks>
    /// <para>You need to know the password to open an encrypted document. If the document is not encrypted, set this to <c>null</c> or empty string.</para>
    /// </remarks>
    public String getPassword() { return mPassword; }
    public void setPassword(String value) { mPassword = value; }

    /// <summary>
    /// Gets or sets the string that will be used to resolve relative URIs found in the document into absolute URIs when required.
    /// Can be <c>null</c> or empty string. Default is <c>null</c>.
    /// </summary>
    /// <remarks>
    /// <p>This property is used to resolve relative URIs into absolute in the following cases:</p>
    /// <list type="number">
    /// <item>When loading an HTML document from a stream and the document contains images with
    /// relative URIs and does not have a base URI specified in the BASE HTML element.</item>
    /// <item>When saving a document to PDF and other formats, to retrieve images linked using relative URIs
    /// so the images can be saved into the output document.</item>
    /// </list>
    /// </remarks>
    public String getBaseUri() { return mBaseUri; }
    public void setBaseUri(String value) { mBaseUri = value; }

    /// <summary>
    /// Gets or sets the encoding that will be used to load an HTML, TXT, or CHM document if the encoding is not specified
    /// inside the document.
    /// Can be <c>null</c>. Default is <c>null</c>.
    /// </summary>
    /// <remarks>
    /// <para>This property is used only when loading HTML, TXT, or CHM documents.</para>
    /// <para>If encoding is not specified inside the document and this property is <c>null</c>, then the system will try to
    /// automatically detect the encoding.</para>
    /// </remarks>
    //JAVA: property accessor is internalized
	Encoding getEncodingInternal() { return mEncoding; }
	//JAVA-added public wrapper for internalized property accessor
	public java.nio.charset.Charset getEncoding() { return Encoding.toJava(getEncodingInternal()); }
    //JAVA: property accessor is internalized
	void setEncodingInternal(Encoding value) { mEncoding = value; }
	//JAVA-added public wrapper for internalized property accessor
	public void setEncoding(java.nio.charset.Charset value) { setEncodingInternal(Encoding.fromJava(value)); }

    /// <summary>
    /// Allows to control how external resources (images, style sheets) are loaded when a document is imported from HTML, MHTML.
    /// </summary>
    public IResourceLoadingCallback getResourceLoadingCallback() { return mResourceLoadingCallback; }
    public void setResourceLoadingCallback(IResourceLoadingCallback value) { mResourceLoadingCallback = value; }

    /// <summary>
    /// Called during a load operation, when an issue is detected that might result in data or formatting fidelity loss.
    /// </summary>
    public IWarningCallback getWarningCallback() { return mWarningCallback; }
    public void setWarningCallback(IWarningCallback value) { mWarningCallback = value; }

    /// <summary>
    /// Called during loading a document and accepts data about loading progress.
    /// </summary>
    /// <remarks>
    /// <para><see cref="LoadFormat.Docx"/>, <see cref="LoadFormat.FlatOpc"/>, <see cref="LoadFormat.Docm"/>, <see cref="LoadFormat.Dotm"/>, <see cref="LoadFormat.Dotx"/>, <see cref="LoadFormat.Markdown"/>, <see cref="LoadFormat.Rtf"/>, <see cref="LoadFormat.WordML"/>, <see cref="LoadFormat.Doc"/>, <see cref="LoadFormat.Dot"/>, <see cref="LoadFormat.Odt"/>, <see cref="LoadFormat.Ott"/> formats supported.</para>
    /// </remarks>
    public IDocumentLoadingCallback getProgressCallback() { return mProgressCallback; }
    public void setProgressCallback(IDocumentLoadingCallback value) { mProgressCallback = value; }

    /// <summary>
    /// Gets or sets whether to preserve the INCLUDEPICTURE field when reading Microsoft Word formats.
    /// The default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <p>By default, the INCLUDEPICTURE field is converted into a shape object. You can override that if you need
    /// the field to be preserved, for example, if you wish to update it programmatically. Note however that this
    /// approach is not common for Aspose.Words. Use it on your own risk.</p>
    /// <p>One of the possible use cases may be using a MERGEFIELD as a child field to dynamically change the source path
    /// of the picture. In this case you need the INCLUDEPICTURE to be preserved in the model.</p>
    /// </remarks>
    public boolean getPreserveIncludePictureField() { return mPreserveIncludePictureField; }
    public void setPreserveIncludePictureField(boolean value) { mPreserveIncludePictureField = value; }

    /// <summary>
    /// Gets or sets whether to convert shapes with EquationXML to Office Math objects.
    /// </summary>
    public boolean getConvertShapeToOfficeMath() { return mConvertShapeToOfficeMath; }
    public void setConvertShapeToOfficeMath(boolean value) { mConvertShapeToOfficeMath = value; }

    /// <summary>
    /// Allows to specify document font settings.
    /// </summary>
    /// <remarks>
    /// <para>When loading some formats, Aspose.Words may require to resolve the fonts. For example, when loading HTML documents Aspose.Words
    /// may resolve the fonts to perform font fallback.</para>
    ///
    /// <para>If set to <c>null</c>, default static font settings <see cref="Aspose.Words.Fonts.FontSettings.DefaultInstance"/> will be used.</para>
    ///
    /// <para>The default value is <c>null</c>.</para>
    /// </remarks>
    public FontSettings getFontSettings() { return mFontSettings; }
    public void setFontSettings(FontSettings value) { mFontSettings = value; }

    /// <summary>
    /// Allows to use temporary files when reading document.
    /// By default this property is <c>null</c> and no temporary files are used.
    /// </summary>
    /// <remarks>
    /// <para>The folder must exist and be writable, otherwise an exception will be thrown.</para>
    ///
    /// <para>Aspose.Words automatically deletes all temporary files when reading is complete.</para>
    /// </remarks>
    public String getTempFolder() { return mTempFolder; }
    public void setTempFolder(String value) { mTempFolder = value; }

    /// <summary>
    /// Gets or sets whether to convert metafile(<see cref="FileFormat.Wmf"/> or <see cref="FileFormat.Emf"/>)
    /// images to <see cref="FileFormat.Png"/> image format.
    /// </summary>
    /// <remarks>
    /// Metafiles (<see cref="FileFormat.Wmf"/> or <see cref="FileFormat.Emf"/>)
    /// is an uncompressed image format and sometimes requires to much RAM to hold and process document.
    /// This option allows to convert all metafile images to <see cref="FileFormat.Png"/> on document loading.
    /// Please note - conversion vector graphics to raster decreases quality of the images.
    /// </remarks>
    public boolean getConvertMetafilesToPng() { return mConvertMetafilesToPng; }
    public void setConvertMetafilesToPng(boolean value) { mConvertMetafilesToPng = value; }

    /// <summary>
    /// Enables fast text extraction mode in all flow format readers, which allows to skip formatting and non textual content to improve speed.
    /// Default value is LoadMode.Normal.
    /// </summary>
    /*LoadMode*/int getLoadMode() { return mLoadMode; }
    void setLoadMode(/*LoadMode*/int value) { mLoadMode = value; }

    boolean getSkipFormatting() { return mLoadMode >= LoadMode.SKIP_FORMATTING; }

    /// <summary>
    /// Allows to read theme data from DOC format.
    /// Used to avoid gold tests massive accept.
    /// </summary>
    boolean getReadDocTheme() { return mReadDocTheme; }
    void setReadDocTheme(boolean value) { mReadDocTheme = value; }
    /// <summary>
    /// Allows to specify that the document loading process should match a specific MS Word version.
    /// Default value is <see cref="MsWordVersion.Word2019"/>
    /// </summary>
    /// <remarks>
    /// Different Word versions may handle certain aspects of document content and formatting slightly differently
    /// during the loading process, which may result in minor differences in Document Object Model.
    /// </remarks>
    public /*MsWordVersion*/int getMswVersion() { return mMsWordVersion; }
    public void setMswVersion(/*MsWordVersion*/int value) { mMsWordVersion = value; }

    /// <summary>
    /// Specifies whether to update the fields with the <c>dirty</c> attribute.
    /// </summary>
    public boolean getUpdateDirtyFields() { return mUpdateDirtyFields; }
    public void setUpdateDirtyFields(boolean value) { mUpdateDirtyFields = value; }

    /// <summary>
    /// Specifies whether to ignore the OLE data.
    /// </summary>
    /// <remarks>
    /// <para>Ignoring OLE data may reduce memory consumption and increase performance without data lost in a case when destination format does not support OLE objects.</para>
    /// <para>The default value is <c>false</c>.</para>
    /// </remarks>
    /// <dev>
    /// OLE without data is converted to images on document post-loading stage.
    /// <see cref="DocumentPostLoader.ConvertOleObjectWithoutDataIntoImage"/>.
    /// </dev>
    public boolean getIgnoreOleData() { return mIgnoreOleData; }; public void setIgnoreOleData(boolean value) { mIgnoreOleData = value; };

    private boolean mIgnoreOleData;


    /// <summary>
    /// Gets or sets whether to use LCID value obtained from Windows registry to determine page setup default margins.
    /// </summary>
    /// <remarks>
    /// <para> If set to <c>true</c>, then MS Word behavior is emulated which takes LCID value from Windows registry.</para>
    /// <para>The default value is <c>false</c>.</para>
    /// </remarks>
    public boolean getUseSystemLcid() { return mUseSystemLcid; }; public void setUseSystemLcid(boolean value) { mUseSystemLcid = value; };

    private boolean mUseSystemLcid;

    /// <summary>
    /// Gets language preferences that will be used when document is loading.
    /// </summary>
    public LanguagePreferences getLanguagePreferences() { return mLanguagePreferences; }

    /// <summary>
    /// Defines how the document should be handled if errors occur during loading.
    /// Use this property to specify whether the system should attempt to recover the document
    /// or follow another defined behavior.
    /// The default value is <see cref="DocumentRecoveryMode.TryRecover"/>.
    /// </summary>
    public /*DocumentRecoveryMode*/int getRecoveryMode() { return mRecoveryMode; }
    public void setRecoveryMode(/*DocumentRecoveryMode*/int value) { mRecoveryMode = value; }

    /// <summary>
    /// Allows to control XmlMapping update upon loading.
    /// </summary>
    /// <remarks>
    /// Used for testing purpose only.
    /// Disabling mapped content feature helps to see how MS Word originally updates content.
    /// </remarks>
    boolean getUpdateXmlMapping() { return mUpdateXmlMapping; }
    void setUpdateXmlMapping(boolean value) { mUpdateXmlMapping = value; }

    /// <summary>
    /// Allows to control character units indents update.
    /// </summary>
    /// <remarks>
    /// Used for testing purpose only.
    /// Disabling character units indents update helps to see how MS Word originally updates content.
    /// </remarks>
    boolean getUpdateCharacterUnits() { return mUpdateCharacterUnits; }; void setUpdateCharacterUnits(boolean value) { mUpdateCharacterUnits = value; };

    private boolean mUpdateCharacterUnits = true;

    /// <summary>
    /// Indicates, whether the options is used to load blank document (<see cref="Document.LoadBlank(LoadOptions)"/>).
    /// </summary>
    boolean isLoadingBlankDocument() { return mIsLoadingBlankDocument; }; void isLoadingBlankDocument(boolean value) { mIsLoadingBlankDocument = value; };

    private boolean mIsLoadingBlankDocument;

    private IDocumentLoadingCallback mProgressCallback;
    private /*LoadFormat*/int mLoadFormat = LoadFormat.AUTO;
    private String mPassword;
    private String mBaseUri;
    private Encoding mEncoding;
    private IResourceLoadingCallback mResourceLoadingCallback;
    private IWarningCallback mWarningCallback;
    private boolean mPreserveIncludePictureField;
    private /*final*/ boolean mAllowTrailingWhitespaceForListItems = true;
    private FontSettings mFontSettings;
    private /*LoadMode*/int mLoadMode = LoadMode.NORMAL;
    private boolean mUpdateDirtyFields;
    private boolean mReadDocTheme;
    private String mTempFolder;

    /// <summary>
    /// True, means that shapes with EquationXML will be converted to Office Math objects.
    /// </summary>
    private boolean mConvertShapeToOfficeMath;

    /// <summary>
    /// Specifies which version of MSW must be used to fetch a correct set of defaults.
    /// MSW 2019's set of defaults is used, if this value was not explicitly specified.
    /// </summary>
    private /*MsWordVersion*/int mMsWordVersion = MsWordVersion.WORD_2019;
    private /*final*/ LanguagePreferences mLanguagePreferences = new LanguagePreferences();
    private /*DocumentRecoveryMode*/int mRecoveryMode = DocumentRecoveryMode.TRY_RECOVER;
    private boolean mConvertMetafilesToPng = false;
    private boolean mUpdateXmlMapping = true;
}

