// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/07/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FileFormatCore;
import com.aspose.ms.System.IO.Path;
import java.util.TimeZone;
import com.aspose.ms.System.Threading.CurrentThread;


/// <summary>
/// This is an abstract base class for classes that allow the user to specify additional
/// options when saving a document into a particular format.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/specify-save-options/">Specify Save Options</a> documentation article.</para>
/// </summary>
/// <remarks>
/// An instance of the <see cref="SaveOptions"/> class or any derived class is passed to the stream <see cref="Document.Save(Stream,SaveOptions)"/>
/// or string <see cref="Document.Save(string,SaveOptions)"/> overloads for the user to define custom options when saving a document.
/// </remarks>
public abstract class SaveOptions
{
    /// <summary>
    /// </summary>
    protected SaveOptions()
    {
    }

    /// <summary>
    /// Sets options we use for unit testing.
    /// </summary>
    /*virtual*/ void setTestMode()
    {
        isTestMode(true);
        // Pretty format makes it easier to visually compare differences with gold.
        setPrettyFormat(true);
        // Do not write generator name, otherwise all golds will have to change with every release.
        setExportGeneratorName(false);
        // Do not write rsid table in test mode to avoid accept too many golds.
        setWriteRsidTable(false);
        // Do not update lastprinted property in test mode to avoid accept too many golds.
        setUpdateLastPrintedProperty(false);
        // Do not use builtin theme in test mode to avoid accept too many golds (1K+).
        setSetBuiltInThemeIfNull(false);
        // Do not write w14:docId in test mode to avoid accept too many golds.
        setWriteW14DocId(false);
    }

    /// <summary>
    /// The property helps to branch production and test code.
    ///
    /// Some gold tests demand simplified repeatable output: fixed (not current) date, time, guid, etc.
    /// So we have to branch between test and production code in such cases: test brunch should initialize
    /// the field by fixed value, but production brunch - by the current, non-fixed one.
    ///
    /// But we can't do the branching using DEBUG or TEST preprocessor constants that wrap just few lines of code:
    /// this construction is non-autoportable and causes tricky bugs in java.
    ///
    /// Note: SetTestMode() sets the property to true. TestUtil.Save() sets the property to true too.
    /// </summary>
    boolean isTestMode() { return mIsTestMode; }; void isTestMode(boolean value) { mIsTestMode = value; };

    private boolean mIsTestMode;

    /// <summary>
    /// Specifies the format in which the document will be saved if this save options object is used.
    /// </summary>
    public abstract /*SaveFormat*/int getSaveFormat();
    public abstract void setSaveFormat(/*SaveFormat*/int value);

    /// <summary>
    /// When saving to HTML and document splitting is requested we need to slightly change logic:
    /// we allow unspecified file name or stream. So callback interface will give them on demand.
    /// </summary>
    /*virtual*/ boolean isMultipleMainPartsAllowed() { return false; }

    /// <summary>
    /// The property must return true if this is a flow document format. False if this is a fixed page (layout and rendering) format.
    /// Most or the formats are flow formats. So let's override in some derived classed for layout-oriented formats.
    /// </summary>
    /*virtual*/ boolean isFlowFormat() { return true; }

    /// <summary>
    /// When <c>true</c>, causes the name and version of Aspose.Words to be embedded into produced files.
    /// Default value is <c>true</c>.
    /// </summary>
    /// <dev>
    /// Set to <c>false</c> when testing against gold files so don't have different output when version number changes.
    /// </dev>
    public boolean getExportGeneratorName() { return mExportGeneratorName; }; public void setExportGeneratorName(boolean value) { mExportGeneratorName = value; };

    private boolean mExportGeneratorName = true;

    /// <summary>
    /// Specifies the folder for temporary files used when saving to a DOC or DOCX file.
    /// By default, this property is <c>null</c> and no temporary files are used.
    /// </summary>
    /// <remarks>
    /// <para>When Aspose.Words saves a document, it needs to create temporary internal structures. By default,
    /// these internal structures are created in memory and the memory usage spikes for a short period while
    /// the document is being saved. When saving is complete, the memory is freed and reclaimed by the garbage collector.</para>
    ///
    /// <para>Specifying a temporary folder using <see cref="TempFolder"/> will cause Aspose.Words to keep the internal structures in
    /// temporary files instead of memory. It reduces the memory usage during saving, but will decrease the save performance.</para>
    ///
    /// <para>The folder must exist and be writable, otherwise an exception will be thrown.</para>
    ///
    /// <para>Aspose.Words automatically deletes all temporary files when saving is complete.</para>
    /// </remarks>
    /// <exception cref="OutOfMemoryException">Throw if you are saving a very large document (thousands of pages) and/or processing many documents at the same time.
    /// The memory spike during saving can be significant enough to cause the exception.</exception>
    public String getTempFolder() { return mTempFolder; }; public void setTempFolder(String value) { mTempFolder = value; };

    private String mTempFolder;

    /// <summary>
    /// Gets or sets a value determining whether OLE controls presentation image will be updated.
    /// </summary>
    /// <dev>
    /// Actually it looks that MS Word always updates presentation image for OLE controls but
    /// rendering team is not sure that our OLE control rendering feature is ready to do the same.
    ///
    /// This is the reason we make it optional and disable it by default.
    /// </dev>
    public boolean getUpdateOleControlImages() { return mUpdateOleControlImages; }; public void setUpdateOleControlImages(boolean value) { mUpdateOleControlImages = value; };

    private boolean mUpdateOleControlImages;

    /// <summary>
    /// When <c>true</c>, pretty formats output where applicable.
    /// Default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <p>Set to <c>true</c> to make HTML, MHTML, EPUB, WordML, RTF, DOCX and ODT output human readable.
    /// Useful for testing or debugging.</p>
    /// </remarks>
    public boolean getPrettyFormat() { return mPrettyFormat; }; public void setPrettyFormat(boolean value) { mPrettyFormat = value; };

    private boolean mPrettyFormat;

    /// <summary>
    /// Gets or sets a value determining whether or not to use anti-aliasing for rendering.
    /// </summary>
    /// <remarks>
    /// <para>
    /// The default value is <c>false</c>. When this value is set to <c>true</c> anti-aliasing is
    /// used for rendering.
    /// </para>
    /// <para>This property is used when the document is exported to the following formats:
    /// <see cref="SaveFormat.Tiff"/>, <see cref="SaveFormat.Png"/>, <see cref="SaveFormat.Bmp"/>,
    /// <see cref="SaveFormat.Jpeg"/>, <see cref="SaveFormat.Emf"/>. When the document is exported to the
    /// <see cref="SaveFormat.Html"/>, <see cref="SaveFormat.Mhtml"/>,
    /// <see cref="SaveFormat.Epub"/>, <see cref="SaveFormat.Azw3"/>
    /// or <see cref="SaveFormat.Mobi"/> formats this option is used for raster images.</para>
    /// </remarks>
    public boolean getUseAntiAliasing() { return mUseAntiAliasing; }; public void setUseAntiAliasing(boolean value) { mUseAntiAliasing = value; };

    private boolean mUseAntiAliasing;

    /// <summary>
    /// Gets or sets a value determining whether or not to use high quality (i.e. slow) rendering algorithms.
    /// </summary>
    /// <remarks>
    /// The default value is <c>false</c>.
    /// <para>This property is used when the document is exported to image formats:
    /// <see cref="SaveFormat.Tiff"/>, <see cref="SaveFormat.Png"/>, <see cref="SaveFormat.Bmp"/>,
    /// <see cref="SaveFormat.Jpeg"/>, <see cref="SaveFormat.Emf"/>.</para>
    /// </remarks>
    public boolean getUseHighQualityRendering() { return mUseHighQualityRendering; }; public void setUseHighQualityRendering(boolean value) { mUseHighQualityRendering = value; };

    private boolean mUseHighQualityRendering = false;

    /// <summary>
    /// Gets or sets a value determining how DrawingML shapes are rendered.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="DmlRenderingMode.Fallback"/>.
    /// <para>This property is used when the document is exported to fixed page formats.</para>
    /// </remarks>
    public /*DmlRenderingMode*/int getDmlRenderingMode() { return mDmlRenderingMode; }
    public void setDmlRenderingMode(/*DmlRenderingMode*/int value) { mDmlRenderingMode = value; }
    private /*DmlRenderingMode*/int mDmlRenderingMode = DmlRenderingMode.DRAWING_ML;

    /// <summary>
    /// Gets or sets a value determining how DrawingML effects are rendered.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="DmlEffectsRenderingMode.Simplified"/>.
    /// <para>This property is used when the document is exported to fixed page formats.</para>
    /// </remarks>
    public /*virtual*/ /*DmlEffectsRenderingMode*/int getDmlEffectsRenderingMode() { return mDmlEffectsRenderingMode; }
    public /*virtual*/ void setDmlEffectsRenderingMode(/*DmlEffectsRenderingMode*/int value) { mDmlEffectsRenderingMode = value; }
    private /*DmlEffectsRenderingMode*/int mDmlEffectsRenderingMode = DmlEffectsRenderingMode.SIMPLIFIED;

    /// <summary>
    /// Gets or sets a value determining how ink (InkML) objects are rendered.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="ImlRenderingMode.InkML"/>.
    /// <para>This property is used when the document is exported to fixed page formats.</para>
    /// </remarks>
    public /*ImlRenderingMode*/int getImlRenderingMode() { return mImlRenderingMode; }
    public void setImlRenderingMode(/*ImlRenderingMode*/int value) { mImlRenderingMode = value; }
    private /*ImlRenderingMode*/int mImlRenderingMode = ImlRenderingMode.INK_ML;

    /// <summary>
    /// Gets or sets path to default template (including filename).
    /// Default value for this property is <b>empty string</b><ms> (<see cref="string.Empty"/>)</ms>.
    /// </summary>
    /// <remarks>If specified, this path is used to load template when <see cref="Document.AutomaticallyUpdateStyles" /> is <c>true</c>,
    ///  but <see cref="Document.AttachedTemplate" /> is empty.</remarks>
    public String getDefaultTemplate() { return mDefaultTemplate; }
    public void setDefaultTemplate(String value) { mDefaultTemplate = value; }
    private String mDefaultTemplate = "";

    /// <summary>
    /// Gets or sets a value determining if fields of certain types should be updated before saving the document to a fixed page format.
    /// Default value for this property is <c>true</c>.
    /// </summary>
    /// <remarks>
    /// Allows to specify whether to mimic or not MS Word behavior.
    /// </remarks>
    public boolean getUpdateFields() { return mUpdateFields; }; public void setUpdateFields(boolean value) { mUpdateFields = value; };

    private boolean mUpdateFields = true;

    /// <summary>
    /// Gets or sets a value determining whether the <see cref="BuiltInDocumentProperties.LastSavedTime" /> property is updated before saving.
    /// </summary>
    public boolean getUpdateLastSavedTimeProperty() { return mUpdateLastSavedTimeProperty; }; public void setUpdateLastSavedTimeProperty(boolean value) { mUpdateLastSavedTimeProperty = value; };

    private boolean mUpdateLastSavedTimeProperty;

    /// <summary>
    /// Gets or sets a value determining whether the <see cref="BuiltInDocumentProperties.LastPrinted" /> property is updated before saving.
    /// </summary>
    public boolean getUpdateLastPrintedProperty() { return mUpdateLastPrintedProperty; }; public void setUpdateLastPrintedProperty(boolean value) { mUpdateLastPrintedProperty = value; };

    private boolean mUpdateLastPrintedProperty;

    /// <summary>
    /// Gets or sets a value determining whether the <see cref="BuiltInDocumentProperties.CreatedTime" /> property is updated before saving.
    /// Default value is <c>false</c>;
    /// </summary>
    public boolean getUpdateCreatedTimeProperty() { return mUpdateCreatedTimeProperty; }; public void setUpdateCreatedTimeProperty(boolean value) { mUpdateCreatedTimeProperty = value; };

    private boolean mUpdateCreatedTimeProperty = false;

    /// <summary>
    /// Gets or sets value determining if memory optimization should be performed before saving the document.
    /// Default value for this property is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// Setting this option to <c>true</c> can significantly decrease memory consumption while saving large documents at the cost of slower saving time.
    /// </remarks>
    /// <dev>
    /// So far only attribute interning procedure is bound to this property but I think we can make more memory optimization in the future.
    /// That's why I decided to use more general property name.
    /// </dev>
    public boolean getMemoryOptimization() { return mMemoryOptimization; }; public void setMemoryOptimization(boolean value) { mMemoryOptimization = value; };

    private boolean mMemoryOptimization = false;

    /// <summary>
    /// Determines whether the font attributes will be changed according to the character code being used.
    /// </summary>
    public boolean getUpdateAmbiguousTextFont() { return mUpdateAmbiguousTextFont; }; public void setUpdateAmbiguousTextFont(boolean value) { mUpdateAmbiguousTextFont = value; };

    private boolean mUpdateAmbiguousTextFont = false;

    /// <overloads>Creates a save options object of a correct class.</overloads>
    /// <summary>
    /// Creates a save options object of a class suitable for the specified save format.
    /// </summary>
    /// <param name="saveFormat">The save format for which to create a save options object.</param>
    /// <returns>An object of a class that derives from <see cref="SaveOptions"/>.</returns>
    public static SaveOptions createSaveOptions(/*SaveFormat*/int saveFormat)
    {
        switch (saveFormat)
        {
            case SaveFormat.DOC:
            case SaveFormat.DOT:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.DOCX:
            case SaveFormat.DOCM:
            case SaveFormat.DOTX:
            case SaveFormat.DOTM:
            case SaveFormat.FLAT_OPC:
            case SaveFormat.FLAT_OPC_MACRO_ENABLED:
            case SaveFormat.FLAT_OPC_TEMPLATE:
            case SaveFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED:
                return new OoxmlSaveOptions(saveFormat);
            case SaveFormat.RTF:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.WORD_ML:
                return new WordML2003SaveOptions();
            case SaveFormat.PDF:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.PS:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.PCL:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.XPS:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.OPEN_XPS:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.XAML_FIXED:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.SVG:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.HTML_FIXED:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.HTML:
            case SaveFormat.MHTML:
            case SaveFormat.EPUB:
            case SaveFormat.AZW_3:
            case SaveFormat.MOBI:
                return new HtmlSaveOptions(saveFormat);
            case SaveFormat.ODT:
            case SaveFormat.OTT:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.TEXT:
                return new TxtSaveOptions();
            case SaveFormat.XAML_FLOW:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.XAML_FLOW_PACK:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.MARKDOWN:
                return new MarkdownSaveOptions();
            case SaveFormat.EMF:
            case SaveFormat.TIFF:
            case SaveFormat.PNG:
            case SaveFormat.BMP:
            case SaveFormat.JPEG:
            case SaveFormat.GIF:
            case SaveFormat.EPS:
            case SaveFormat.WEB_P:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.XLSX:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.DOCLING:
                throw new UnsupportedOperationException("FOSS");

            default:
                throw new IllegalArgumentException("Invalid save format requested.");
        }
    }

    /// <summary>
    /// Creates a save options object of a class suitable for the file extension specified in the given file name.
    /// </summary>
    /// <param name="fileName">The extension of this file name determines the class of the save options object to create.</param>
    /// <returns>An object of a class that derives from <see cref="SaveOptions"/>.</returns>
    public static SaveOptions createSaveOptions(String fileName) throws Exception
    {
        // Internal methods are used instead of FileFormatUtil.ExtensionToSaveFormat
        // to get SaveFormat.Unknown instead of an exception.
        return createSaveOptions(FileFormatUtil.toSaveFormat(FileFormatCore.fromExt(Path.getExtension(fileName))));
    }

    /// <summary>
    /// Gets or sets a value determining how 3D effects are rendered.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="Dml3DEffectsRenderingMode.Basic"/>.
    /// </remarks>
    public /*Dml3DEffectsRenderingMode*/int getDml3DEffectsRenderingMode() { return mDml3DEffectsRenderingMode; }
    public void setDml3DEffectsRenderingMode(/*Dml3DEffectsRenderingMode*/int value) { mDml3DEffectsRenderingMode = value; }
    private /*Dml3DEffectsRenderingMode*/int mDml3DEffectsRenderingMode = Dml3DEffectsRenderingMode.BASIC;

    /// <summary>
    /// Called during saving a document and accepts data about saving progress.
    /// </summary>
    /// <remarks>
    /// <para>
    /// Progress is reported when saving to <see cref="SaveFormat.Docx"/>, <see cref="SaveFormat.FlatOpc"/>,
    /// <see cref="SaveFormat.Docm"/>, <see cref="SaveFormat.Dotm"/>, <see cref="SaveFormat.Dotx"/>,
    /// <see cref="SaveFormat.Doc"/>, <see cref="SaveFormat.Dot"/>,
    /// <see cref="SaveFormat.Html"/>, <see cref="SaveFormat.Mhtml"/>, <see cref="SaveFormat.Epub"/>,
    /// <see cref="SaveFormat.XamlFlow"/>, or <see cref="SaveFormat.XamlFlowPack"/>.
    /// </para>
    /// </remarks>
    public IDocumentSavingCallback getProgressCallback() { return mProgressCallback; }; public void setProgressCallback(IDocumentSavingCallback value) { mProgressCallback = value; };

    private IDocumentSavingCallback mProgressCallback;

    /// <summary>
    /// Gets or sets a boolean value indicating whether to allow embedding fonts with PostScript outlines
    /// when embedding TrueType fonts in a document upon it is saved.
    /// The default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>Note, Word does not embed PostScript fonts, but can open documents with embedded fonts of this type.</para>
    /// <para>This option only works when <see cref="FontInfoCollection.EmbedTrueTypeFonts"/> of the
    /// <see cref="DocumentBase.FontInfos"/> property is set to <c>true</c>.</para>
    /// </remarks>
    public boolean getAllowEmbeddingPostScriptFonts() { return mAllowEmbeddingPostScriptFonts; }; public void setAllowEmbeddingPostScriptFonts(boolean value) { mAllowEmbeddingPostScriptFonts = value; };

    private boolean mAllowEmbeddingPostScriptFonts;

    /// <summary>
    /// Gets or sets custom local time zone used for date/time fields.
    /// </summary>
    /// <remarks>
    /// <para>This option is available in either .Net framework starting from 3.5 version or .Net Standard.</para>
    /// <para>By default, Aspose.Words uses system local time zone when writes date/time fields, this option allows to set custom value.</para>
    /// </remarks>
    /// <dev>
    /// So far this option used in SDT content updating only. Please see WORDSNET-20863 for details.
    /// We can use it for other document date/time values later.
    /// </dev>
    public TimeZone getCustomTimeZoneInfo() { return mCustomTimeZoneInfo; }
    public void setCustomTimeZoneInfo(TimeZone value) { mCustomTimeZoneInfo = value; }
    private TimeZone mCustomTimeZoneInfo = CurrentThread.getTimeZone();

    /// <summary>
    /// Determines whether build-in theme should be written if there is no theme in the document.
    /// Default value is true.
    /// </summary>
    boolean getSetBuiltInThemeIfNull() { return mSetBuiltInThemeIfNull; }; void setSetBuiltInThemeIfNull(boolean value) { mSetBuiltInThemeIfNull = value; };

    private boolean mSetBuiltInThemeIfNull = true;

    /// <summary>
    /// Determines whether rsid table should be written. Default value is true.
    /// </summary>
    boolean getWriteRsidTable() { return mWriteRsidTable; }; void setWriteRsidTable(boolean value) { mWriteRsidTable = value; };

    private boolean mWriteRsidTable = true;

    /// <summary>
    /// Determines whether w14:docId should be written in settings part. Default value is true.
    /// </summary>
    boolean getWriteW14DocId() { return mWriteW14DocId; }; void setWriteW14DocId(boolean value) { mWriteW14DocId = value; };

    private boolean mWriteW14DocId = true;

    /// <summary>
    /// Controls whether we need to update XmlMapping before saving.
    /// </summary>
    boolean getUpdateXmlMapping() { return mUpdateXmlMapping; }; void setUpdateXmlMapping(boolean value) { mUpdateXmlMapping = value; };

    private boolean mUpdateXmlMapping = true;
}

