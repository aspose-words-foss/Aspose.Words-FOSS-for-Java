// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.EncodingUtil;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ArgumentUtil;
import com.aspose.UriUtil;
import com.aspose.ms.System.IO.Path;
import com.aspose.StringUtil;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.IO.MemoryStream;
import java.util.ArrayList;
import com.aspose.ms.System.IO.Directory;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.DateTime;
import com.aspose.SystemPal;
import com.aspose.Debug;
import com.aspose.FileSystem;
import com.aspose.ZipUtilPal;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.DateTimeUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.msArray;
import com.aspose.ms.System.Convert;
import com.aspose.StringToObjDictionary;
import com.aspose.MemoryStorage;
import com.aspose.DictionaryExtension;
import java.util.HashMap;


/// <summary>
/// Represents a Word document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-document/">Working with Document</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>The <see cref="Document"/> is a central object in the Aspose.Words library.</p>
///
/// <p>To load an existing document in any of the <see cref="LoadFormat"/> formats, pass a file name
/// or a stream into one of the <see cref="Document"/> constructors. To create a blank document, call the
/// constructor without parameters.</p>
///
/// <p>Use one of the Save method overloads to save the document in any of the
/// <see cref="SaveFormat"/> formats.</p>
///
/// <p><see cref="Document"/> stores document-wide information such as <see cref="DocumentBase.Styles"/>,
/// <see cref="BuiltInDocumentProperties"/>, <see cref="CustomDocumentProperties"/>, lists and macros.
/// Most of these objects are accessible via the corresponding properties of the <see cref="Document"/>.</p>
///
/// <p>The <see cref="Document"/> is a root node of a tree that contains all other nodes of the document.
/// The tree is a Composite design pattern and in many ways similar to XmlDocument.
/// The content of the document can be manipulated freely programmatically:</p>
/// <list type="bullet">
/// <item>The nodes of the document can be accessed via typed collections, for example <see cref="Sections"/>,
/// <see cref="ParagraphCollection"/> etc.</item>
/// <item>The nodes of the document can be selected by their node type using
/// <see cref="CompositeNode.GetChildNodes(Words.NodeType, bool)"/>
/// or using an XPath query with <see cref="CompositeNode.SelectNodes"/> or <see cref="CompositeNode.SelectSingleNode"/>.</item>
/// <item>Content nodes can be added or removed from anywhere in the document using
/// <see cref="CompositeNode.InsertBefore{T}(T, Node)"/>, <see cref="CompositeNode.InsertAfter{T}(T, Node)"/>,
/// <see cref="CompositeNode.RemoveChild{T}"/> and other
/// methods provided by the base class <see cref="CompositeNode"/>.</item>
/// <item>The formatting attributes of each node can be changed via the properties of that node.</item>
/// </list>
///
/// <p>Consider using <see cref="DocumentBuilder"/> that simplifies the task of programmatically creating
/// or populating the document tree.</p>
///
/// <p>The <see cref="Document"/> can contain only <see cref="Section"/> objects.</p>
///
/// <p>In Microsoft Word, a valid document needs to have at least one section.</p>
/// </remarks>
public class Document extends DocumentBase<Node> implements ISectionAttrSource, IWatermarkProvider
{

    /// <summary>
    /// Ctor that allows to create a completely empty document node or load a blank document.
    /// </summary>
    Document(/*DocumentCtorMode*/int documentCtorMode) throws Exception
    {
    	this(documentCtorMode, null);
	
    }

    /// <summary>
    /// Ctor that allows to create a completely empty document node or load a blank document.
    /// </summary>
    Document(/*DocumentCtorMode*/int documentCtorMode, LoadOptions loadOptions) throws Exception
    {
        switch (documentCtorMode)
        {
            case DocumentCtorMode.BLANK_DOCUMENT_NODE:
            {
                loadBlank(null);
                setLocaleDefaultsForNewDocument();
                break;
            }

            case DocumentCtorMode.EMPTY_DOCUMENT_NODE:
            {
                if (loadOptions != null)
                    postLoadTasks(loadOptions);

                break;
            }

            default:
                break;
        }
    }


    /// <summary>
    /// Opens an existing document from a stream.
    /// This ctor for internal use only, it is forbidden to use public ctors inside code to avoid extra credit billings.
    /// </summary>
    /// <param name="stream">Stream where to load the document from.</param>
    /// <param name="loadOptions">Additional options to use when loading a document. Can be <c>null</c>.</param>
    /// <param name="increaseCredit">Increase credit if it is needed (for example when <see cref="PlainTextDocument"/> document is created)</param>
    Document(Stream stream, LoadOptions loadOptions, boolean increaseCredit) throws Exception
    {
    	this(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
	
        if (stream == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "stream");

        load(stream, loadOptions);

        // FOSS
    }

    /// <overloads>Creates or loads a document.</overloads>
    /// <summary>
    /// Creates a blank Word document.
    /// </summary>
    /// <remarks>
    /// <p>A blank document is retrieved from resources, and by default, the resulting document looks more like created by <see cref="MsWordVersion.Word2007"/>.
    /// This blank document contains a default fonts table, minimal default styles, and latent styles.</p>
    /// <p><see cref="CompatibilityOptions.OptimizeFor"/> method can be used to optimize the document contents as well as default Aspose.Words behavior to a particular version of MS Word.</p>
    /// <p>The document paper size is Letter by default. If you want to change page setup, use
    /// <see cref="Section.PageSetup"/>.</p>
    /// <p>After creation, you can use <see cref="DocumentBuilder"/> to add document content easily.</p>
    /// </remarks>
    public Document() throws Exception
    {
    	this(DocumentCtorMode.BLANK_DOCUMENT_NODE);
	
        // Please NOTE - It is forbidden to use public ctors inside code to avoid extra credit billings.
        // Use internal Document ctors instead.

        // FOSS
    }

    /// <summary>
    /// Opens an existing document from a file. Automatically detects the file format.
    /// </summary>
    /// <param name="fileName">File name of the document to open.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Document.LoadFileExceptions"]/*'/>
    public Document( String fileName) throws Exception
    {
    	this(fileName, null);
	
        // Please NOTE - It is forbidden to use public ctors inside code to avoid extra credit billings.
        // Use internal Document ctors instead.
    }

    /// <summary>
    /// Opens an existing document from a file. Allows to specify additional options such as an encryption password.
    /// </summary>
    /// <param name="fileName">File name of the document to open.</param>
    /// <param name="loadOptions">Additional options to use when loading a document. Can be <c>null</c>.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Document.LoadFileExceptions"]/*'/>
    public Document(String fileName, LoadOptions loadOptions) throws Exception
    {
    	this(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
	
        // Please NOTE - It is forbidden to use public ctors inside code to avoid extra credit billings.
        // Use internal Document ctors instead.

        ArgumentUtil.checkHasChars(fileName, "fileName");

        mOriginalFileName = fileName;
        mBaseUri = getBaseUriFromFileName(fileName);

        IResourceLoadingCallback resourceLoadingCallback = (loadOptions != null)
            ? loadOptions.getResourceLoadingCallback()
            : null;

        Stream stream = openDocumentStream(fileName, resourceLoadingCallback);
        if (stream == null)
            return;

        try /*JAVA: was using*/
        {
            load(stream, loadOptions);
        }
        finally { if (stream != null) stream.close(); }

        // FOSS

        attachMappedCustomXmlUpdater();
    }

    private static String getBaseUriFromFileName(String fileName) throws Exception
    {
        boolean hasFileScheme = UriUtil.hasFileScheme(fileName);

        if (hasFileScheme)
        {
            fileName = UriUtil.removeFileSchemePrefix(fileName);
        }

        // WORDSNET-26735 Correctly extract base URI in case when a document is loaded from a URI.
        if (UriUtil.isHrefWithScheme(fileName))
        {
            return UriUtil.getDirectoryHrefWithScheme(fileName);
        }

        String directoryName = Path.getDirectoryName(fileName);

        if (hasFileScheme)
        {
            directoryName = UriUtil.addFileSchemePrefix(directoryName);
        }

        return directoryName;
    }

    private static String getBaseUriFromLoadOptions(LoadOptions loadOptions, String originalFileName)
    {
        if (!StringUtil.hasChars(loadOptions.getBaseUri()))
        {
            return "";
        }

        if (StringUtil.hasChars(originalFileName))
        {
            if (UriUtil.hasFileScheme(originalFileName))
            {
                originalFileName = UriUtil.removeFileSchemePrefix(originalFileName);
            }

            // WORDSNET-26575 If a document is loaded from a URI and base URI specified in load options
            // doesn't have a scheme and document's URI has a scheme, we append that scheme to base URI.
            if (UriUtil.isHrefWithScheme(originalFileName) && !UriUtil.isHrefWithScheme(loadOptions.getBaseUri()))
            {
                String scheme = UriUtil.getScheme(originalFileName);
                return scheme + "://" + loadOptions.getBaseUri();
            }
        }

        return loadOptions.getBaseUri();
    }

    /// <summary>
    /// Opens an existing document from a stream. Automatically detects the file format.
    /// </summary>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Document.OpenStreamCommon"]/*'/>
    /// <param name="stream">Stream where to load the document from.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Document.LoadStreamExceptions"]/*'/>
    ///
    /// <javaName>Document(java.io.InputStream stream)</javaName>
    // JAVA: the first public api change map will be used: Stream -> java.io.InputStream
    //JAVA: ctor is internalized
	Document( Stream stream) throws Exception
    {
    	this(stream, null);
	
        // Please NOTE - It is forbidden to use public ctors inside code to avoid extra credit billings.
        // Use internal Document ctors instead.
    }

	//JAVA-added public wrapper for internalized ctor
	public Document( java.io.InputStream stream) throws Exception
	{
		this(Stream.fromJavaInputStream(stream));
	}

    /// <summary>
    /// Opens an existing document from a stream. Allows to specify additional options such as an encryption password.
    /// </summary>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Document.OpenStreamCommon"]/*'/>
    /// <param name="stream">The stream where to load the document from.</param>
    /// <param name="loadOptions">Additional options to use when loading a document. Can be <c>null</c>.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Document.LoadStreamExceptions"]/*'/>
    ///
    /// <javaName>Document(java.io.InputStream stream, com.aspose.words.LoadOptions loadOptions)</javaName>
    // JAVA: the first public api change map will be used: Stream -> java.io.InputStream
    //JAVA: ctor is internalized
	Document( Stream stream, LoadOptions loadOptions) throws Exception
    {
    	this(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
	
        // Please NOTE - It is forbidden to use public ctors inside code to avoid extra credit billings.
        // Use internal Document ctors instead.

        if (stream == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "stream");

        load(stream, loadOptions);

        attachMappedCustomXmlUpdater();

        // FOSS
    }

	//JAVA-added public wrapper for internalized ctor
	public Document( java.io.InputStream stream,LoadOptions loadOptions) throws Exception
	{
		this(Stream.fromJavaInputStream(stream), loadOptions);
	}

    /// <summary>
    /// Gets or sets the full path of the template attached to the document.
    /// </summary>
    /// <remarks>
    /// <p>Empty string means the document is attached to the Normal template.</p>
    /// <seealso cref="Properties.BuiltInDocumentProperties.Template"/>
    /// </remarks>
    /// <exception cref="ArgumentNullException">Throws if you attempt to set to a <c>null</c> value.</exception>
    public String getAttachedTemplate() { return getDocPr().AttachedTemplate; }
    public void setAttachedTemplate(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        getDocPr().AttachedTemplate = value;
    }

    /// <summary>
    /// Gets or sets a flag indicating whether the styles in the document are updated to match the styles in the
    /// attached template each time the document is opened in MS Word.
    /// </summary>
    public boolean getAutomaticallyUpdateStyles() { return getDocPr().LinkStyles; }
    public void setAutomaticallyUpdateStyles(boolean value) { getDocPr().LinkStyles = value; }

    /// <summary>
    /// Specifies whether to turn on the gray shading on form fields.
    /// </summary>
    public boolean getShadeFormData() { return !getDocPr().DoNotShadeFormData; }
    public void setShadeFormData(boolean value) { getDocPr().DoNotShadeFormData = !value; }

    /// <summary>
    /// True if changes are tracked when this document is edited in Microsoft Word.
    /// </summary>
    /// <remarks>
    /// <p>Setting this option only instructs Microsoft Word whether the track changes
    /// is turned on or off. This property has no effect on changes to the document that you make
    /// programmatically via Aspose.Words.</p>
    /// <p>If you want to automatically track changes as they are made programmatically by Aspose.Words
    /// to this document use the <see cref="Document.StartTrackRevisions(string, DateTime)" /> method.</p>
    /// </remarks>
    public boolean getTrackRevisions() { return getDocPr().TrackRevisions; }
    public void setTrackRevisions(boolean value) { getDocPr().TrackRevisions = value; }

    /// <summary>
    /// Specifies whether to display grammar errors in this document.
    /// </summary>
    public boolean getShowGrammaticalErrors() { return !getDocPr().HideGrammaticalErrors; }
    public void setShowGrammaticalErrors(boolean value) { getDocPr().HideGrammaticalErrors = !value; }

    /// <summary>
    /// Specifies whether to display spelling errors in this document.
    /// </summary>
    public boolean getShowSpellingErrors() { return !getDocPr().HideSpellingErrors; }
    public void setShowSpellingErrors(boolean value) { getDocPr().HideSpellingErrors = !value; }

    /// <summary>
    /// Returns <c>true</c> if the document has been checked for spelling.
    /// </summary>
    /// <remarks>
    /// To recheck the spelling in the document, set this property to <c>false</c>.
    /// </remarks>
    public boolean getSpellingChecked() { return getDocPr().ProofStateSpelling != ProofState.NONE; }
    public void setSpellingChecked(boolean value)
    {
        getDocPr().ProofStateSpelling = value
            ? ProofState.CLEAN
            : ProofState.NONE;
    }

    /// <summary>
    /// Returns <c>true</c> if the document has been checked for grammar.
    /// </summary>
    /// <remarks>
    /// To recheck the grammar in the document, set this property to <c>false</c>.
    /// </remarks>
    public boolean getGrammarChecked() { return getDocPr().ProofStateGrammar != ProofState.NONE; }
    public void setGrammarChecked(boolean value)
    {
        getDocPr().ProofStateGrammar = value
            ? ProofState.CLEAN
            : ProofState.NONE;
    }

    /// <summary>
    /// Specifies whether kerning applies to both Latin text and punctuation.
    /// </summary>
    public boolean getPunctuationKerning() { return getDocPr().PunctuationKerning; }
    public void setPunctuationKerning(boolean value) { getDocPr().PunctuationKerning = value; }

    /// <summary>
    /// Returns <see cref="NodeType.Document"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.DOCUMENT; }

    /// <summary>
    /// Returns a collection that represents all the built-in document properties of the document.
    /// </summary>
    public BuiltInDocumentProperties getBuiltInDocumentProperties() { return mBuiltInDocumentProperties; }

    /// <summary>
    /// Returns a collection that represents a list of task pane add-ins.
    /// </summary>
    public TaskPaneCollection getWebExtensionTaskPanes() { return mWebExtensionTaskPanes; }

    /// <summary>
    /// Returns a collection that represents all the custom document properties of the document.
    /// </summary>
    public CustomDocumentProperties getCustomDocumentProperties()
    {
        if (mCustomDocumentProperties == null)
            mCustomDocumentProperties = new CustomDocumentProperties(this);

        return mCustomDocumentProperties;
    }

    /// <summary>
    /// Gets the currently active document protection type.
    /// </summary>
    /// <remarks>
    /// <p>This property allows to retrieve the currently set document protection type.
    /// To change the document protection type use the <see cref="Protect(Aspose.Words.ProtectionType,string)"/>
    /// and <see cref="Unprotect()"/> methods.</p>
    ///
    /// <p>When a document is protected, the user can make only limited changes,
    /// such as adding annotations, making revisions, or completing a form.</p>
    ///
    /// <para>Note that document protection is different from write protection.
    /// Write protection is specified using the <see cref="WriteProtection"/></para>
    ///
    /// <seealso cref="Protect(Aspose.Words.ProtectionType,string)"/>
    /// <seealso cref="Unprotect()"/>
    /// <seealso cref="WriteProtection"/>
    /// </remarks>
    public /*ProtectionType*/int getProtectionType() { return getDocPr().DocumentProtection.getEnforcedProtectionType(); }

    /// <summary>
    /// Exposes document protection settings for testing purposes.
    /// </summary>
    DocumentProtection getDocumentProtection() { return getDocPr().DocumentProtection; }

    /// <summary>
    /// Returns a collection that represents all sections in the document.
    /// </summary>
    public SectionCollection getSections()
    {
        if (mSectionsCache == null)
            mSectionsCache = new SectionCollection(this);
        return mSectionsCache;
    }

    /// <summary>
    /// Gets the first section in the document.
    /// </summary>
    /// <remarks>
    /// Returns <c>null</c> if there are no sections.
    /// </remarks>
    public Section getFirstSection() { return (Section)getChild(NodeType.SECTION, 0, false); }

    /// <summary>
    /// Gets the last section in the document.
    /// </summary>
    /// <remarks>
    /// Returns <c>null</c> if there are no sections.
    /// </remarks>
    public Section getLastSection() { return (Section)getChild(NodeType.SECTION, -1, false); }

    /// <summary>
    /// Provides options to control how the document is displayed in Microsoft Word.
    /// </summary>
    public ViewOptions getViewOptions() { return getDocPr().ViewOptions; }

    /// <summary>
    /// Provides access to the document write protection options.
    /// </summary>
    public WriteProtection getWriteProtection() { return getDocPr().WriteProtection; }

    /// <summary>
    /// Provides access to document compatibility options (that is, the user preferences entered on the <b>Compatibility</b>
    /// tab of the <b>Options</b> dialog in Word).
    /// </summary>
    public CompatibilityOptions getCompatibilityOptions() { return getDocPr().CompatibilityOptions; }

    /// <summary>
    /// Gets or sets the object that contains all of the mail merge information for a document.
    /// </summary>
    /// <remarks>
    /// <para>You can use this object to specify a mail merge data source for a document and this information
    /// (along with the available data fields) will appear in Microsoft Word when the user opens this document.
    /// Or you can use this object to query mail merge settings that the user has specified in Microsoft Word
    /// for this document.</para>
    ///
    /// <para>This object is never <c>null</c>.</para>
    /// </remarks>
    public MailMergeSettings getMailMergeSettings() { return getDocPr().MailMergeSettings; }
    public void setMailMergeSettings(MailMergeSettings value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        getDocPr().MailMergeSettings = value;
    }

    /// <summary>
    /// Provides access to document hyphenation options.
    /// </summary>
    public HyphenationOptions getHyphenationOptions() { return getDocPr().HyphenationOptions; }

    /// <summary>
    /// Returns <c>true</c> if the document has any tracked changes.
    /// </summary>
    /// <remarks>
    /// This property is a shortcut for comparing <see cref="RevisionCollection.Count"/> to zero.
    /// </remarks>
    public boolean hasRevisions() { return getRevisions().getCount() != 0; }

    /// <summary>
    /// Returns <c>true</c> if the document has a VBA project (macros).
    /// </summary>
    /// <seealso cref="RemoveMacros"/>
    public boolean hasMacros() { return (mVbaProject != null); }

    /// <summary>
    /// Provides access to the document watermark.
    /// </summary>
    public Watermark getWatermark()
    {
        if (mWatermark == null)
            mWatermark = new Watermark(this, this);

        return mWatermark;
    }

    /// <summary>
    /// Returns <c>true</c> if the document has toolbar or command customizations.
    /// </summary>
    boolean hasCustomizations() { return (hasAttachedToolbars() || hasAllocatedCommands() || hasKeyMaps()); }

    boolean hasAttachedToolbars() { return (mAttachedToolbars != null) && (mAttachedToolbars.length > 0); }

    boolean hasAllocatedCommands() { return (mAllocatedCommands != null) && (mAllocatedCommands.size() > 0); }

    boolean hasKeyMaps() { return (mKeyMaps != null) && (mKeyMaps.size() > 0); }

    /// <summary>
    /// Gets the number of document versions that was stored in the DOC document.
    /// </summary>
    /// <remarks>
    /// <p>Versions in Microsoft Word are accessed via the File/Versions menu. Microsoft Word supports
    /// versions only for DOC files.</p>
    ///
    /// <p>This property allows to detect if there were document versions stored in this document
    /// before it was opened in Aspose.Words. Aspose.Words provides no other support for document versions.
    /// If you save this document using Aspose.Words, the document will be saved without versions.</p>
    /// </remarks>
    public int getVersionsCount() { return getDocPr().VersionsCount; }

    /// <summary>
    /// Gets or sets the interval (in points) between the default tab stops.
    /// </summary>
    /// <seealso cref="TabStopCollection"/><seealso cref="TabStop"/>
    public double getDefaultTabStop() { return ConvertUtilCore.twipToPoint(getDocPr().DefaultTabStop); }
    public void setDefaultTabStop(double value)
    {
        // According to 2.15.1.24 (defaultTabStop) of the specification, the default tab stop should be a positive
        // whole number. But it looks like MS Word also supports and uses zero as default tab stop.
        if (value < 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        getDocPr().DefaultTabStop = ConvertUtilCore.pointToTwip(value);
    }

    /// <summary>
    /// Gets the <see cref="Theme"/> object for this document.
    /// </summary>
    public Theme getTheme()
    {
        initializeThemeIfNeeded();
        return mTheme;
    }

    private void initializeThemeIfNeeded()
    {
        if (getThemeInternal() == null)
            setThemeInternal(Theme.getBuiltInTheme().deepClone());
    }

    /*override*/ Theme getThemeInternal()
    {
        return mTheme;
    }

    void setThemeInternal(Theme theme)
    {
        mTheme = theme;
        // WORDSNET-15915 Attach document to update colors when theme is changed.
        if (mTheme != null)
            mTheme.attach(this);
    }

    /// <summary>
    /// Gets or sets the collection of Custom XML Data Storage Parts.
    /// </summary>
    /// <remarks>
    /// <para>Aspose.Words loads and saves Custom XML Parts into OOXML and DOC documents only.</para>
    ///
    /// <para>This property cannot be <c>null</c>.</para>
    ///
    /// <seealso cref="CustomXmlPart"/>
    /// </remarks>
    public CustomXmlPartCollection getCustomXmlParts() { return mCustomXmlParts; }
    public void setCustomXmlParts(CustomXmlPartCollection value)
    {
        ArgumentUtil.checkNotNull(value, "customXmlParts");
        mCustomXmlParts = value;
    }

    /// <summary>
    /// Gets or sets the collection of custom parts (arbitrary content) that are linked to the OOXML package using "unknown relationships".
    /// </summary>
    /// <remarks>
    /// <para>Do not confuse these custom parts with Custom XML Data. If you need to access Custom XML parts,
    /// use the <see cref="CustomXmlParts"/> property.</para>
    ///
    /// <para>This collection contains OOXML parts whose parent is the OOXML package and they targets are of an "unknown relationship".
    /// For more information see <see cref="CustomPart"/>.</para>
    ///
    /// <para>Aspose.Words loads and saves custom parts into OOXML documents only.</para>
    ///
    /// <para>This property cannot be <c>null</c>.</para>
    ///
    /// <seealso cref="CustomPart"/>
    /// </remarks>
    public CustomPartCollection getPackageCustomParts() { return mPackageCustomParts; }
    public void setPackageCustomParts(CustomPartCollection value)
    {
        ArgumentUtil.checkNotNull(value, "packageCustomParts");
        mPackageCustomParts = value;
    }

    byte[] getDropdownStrings() { return mDropdownStrings; }
    void setDropdownStrings(byte[] value) { mDropdownStrings = value; }

    /// <summary>
    /// Corresponds to the CompObj stream sometimes present in the document. Can be <c>null</c>.
    /// </summary>
    MemoryStream getCompObj() { return mCompObj; }
    void setCompObj(MemoryStream value) { mCompObj = value; }

    /// <summary>
    /// MsoEnvelope data. See [MS-OSHARED] section 2.3.8.1.
    /// </summary>
    /// <remarks>
    /// Customer just wants this data to be preserved so don't waste time to parse it for a while.
    /// </remarks>
    byte[] getMsoEnvelope() { return mMsoEnvelope; }
    void setMsoEnvelope(byte[] value) { mMsoEnvelope = value; }

    /// <summary>
    /// Contains the binary data with the custom toolbars.
    /// Can be null.
    /// </summary>
    byte[] getAttachedToolbars() { return mAttachedToolbars; }
    void setAttachedToolbars(byte[] value) { mAttachedToolbars = value; }

    /// <summary>
    /// Contains <see cref="AllocatedCommand"/> objects.
    /// The order is important because it matches the order in the binary DOC.
    /// Do not delete or insert items here unless you are loading a document.
    /// Can be null.
    /// </summary>
    ArrayList<AllocatedCommand> getAllocatedCommands() { return mAllocatedCommands; }
    void setAllocatedCommands(ArrayList<AllocatedCommand> value) { mAllocatedCommands = value; }

    /// <summary>
    /// Contains <see cref="KeyMap"/> objects.
    /// Can be null.
    /// </summary>
    ArrayList<KeyMap> getKeyMaps() { return mKeyMaps; }
    void setKeyMaps(ArrayList<KeyMap> value) { mKeyMaps = value; }

    /// <summary>
    /// Gets or sets the flags that specify what document events are active.
    /// When an event is turned on, the corresponding macro must exist in the VBA project.
    /// </summary>
    /*VbaDocumentEvents*/int getVbaDocumentEvents() { return mVbaDocumentEvents; }
    void setVbaDocumentEvents(/*VbaDocumentEvents*/int value) { mVbaDocumentEvents = value; }

    /// <summary>
    /// Returns the collection of variables added to a document or template.
    /// </summary>
    public /*new*/ VariableCollection getVariables() { return super.getVariables(); }

    /// <summary>
    /// Gets or sets the glossary document within this document or template. A glossary document is a storage
    /// for AutoText, AutoCorrect and Building Block entries defined in a document.
    /// </summary>
    /// <remarks>
    /// <para>This property returns <c>null</c> if the document does not have a glossary document.</para>
    /// <para>You can add a glossary document to a document by creating a
    /// <see cref="Aspose.Words.BuildingBlocks.GlossaryDocument"/> object and assigning to this property.</para>
    ///
    /// <seealso cref="Aspose.Words.BuildingBlocks.GlossaryDocument"/>
    /// </remarks>
    public GlossaryDocument getGlossaryDocument() { return mGlossary; }
    public void setGlossaryDocument(GlossaryDocument value)
    {
        mGlossary = value;
        Document mainDocument = this;
        mGlossary.setMainDocument(mainDocument);
        mGlossary = mGlossary;
    }

    /// <summary>
    /// Base URI of the document.
    /// </summary>
    /// <remarks>
    /// <p>The <b>BaseUri</b> property is used to resolve relative URI of images when importing or
    /// inserting HTML and also when exporting to HTML or PDF.</p>
    /// <p>Base URI can have optional protocol, optional host name, optional port and required path parts.</p>
    /// <p>If the document was created blank or loaded from a stream, then <b>BaseUri</b> will have the <c>null</c> value.</p>
    /// <p>If the document was loaded from a local file, then <b>BaseUri</b> will store the name of the folder
    /// that contains the file.</p>
    /// </remarks>
    String getBaseUri() { return mBaseUri; }
    void setBaseUri(String value) { mBaseUri = value; }

    /// <summary>
    /// Gets the original file name of the document.
    /// </summary>
    /// <remarks>
    /// <p>Returns <c>null</c> if the document was loaded from a stream or created blank.</p>
    /// </remarks>
    public String getOriginalFileName() { return mOriginalFileName; }

    String getSavedFileName() { return mSavedFileName; }

    /// <summary>
    /// Gets the format of the original document that was loaded into this object.
    /// </summary>
    /// <remarks>
    /// <para>If you created a new blank document, returns the <see cref="LoadFormat.Doc"/> value.</para>
    /// </remarks>
    public /*LoadFormat*/int getOriginalLoadFormat() { return mOriginalLoadFormat; }

    /// <summary>
    /// Gets the OOXML compliance version determined from the loaded document content.
    /// Makes sense only for OOXML documents.
    /// </summary>
    /// <remarks>
    /// <para>If you created a new blank document or load non OOXML document
    /// returns the <see cref="OoxmlCompliance.Ecma376_2006"/> value.</para>
    /// </remarks>
    public /*OoxmlCompliance*/int getCompliance()
    {
        if (getComplianceInfo() == null)
            return OoxmlCompliance.ECMA_376_2006;

        switch (getComplianceInfo().getCompliance())
        {
            case OoxmlComplianceCore.ECMA_376:
                return OoxmlCompliance.ECMA_376_2006;
            case OoxmlComplianceCore.ISO_TRANSITIONAL:
                return OoxmlCompliance.ISO_29500_2008_TRANSITIONAL;
            case OoxmlComplianceCore.ISO_STRICT:
                return OoxmlCompliance.ISO_29500_2008_STRICT;
            default:
                return OoxmlCompliance.ECMA_376_2006;
        }
    }

    /// <summary>
    /// Gets the collection of digital signatures for this document and their validation results.
    /// </summary>
    /// <remarks>
    /// <para>This collection contains digital signatures that were loaded from the original document.
    /// These digital signatures will not be saved when you save this <see cref="Aspose.Words.Document"/> object
    /// into a file or stream because saving or converting will produce a document that is different from the
    /// original and the original digital signatures will no longer be valid.</para>
    ///
    /// <para>This collection is never <c>null</c>. If the document is not signed, it will contain zero elements.</para>
    /// </remarks>
    public DigitalSignatureCollection getDigitalSignatures() { return mDigitalSignatures; }

    /// <summary>
    /// Gets or sets document font settings.
    /// </summary>
    /// <remarks>
    /// <para>This property allows to specify font settings per document. If set to <c>null</c>, default static font settings
    /// <see cref="Aspose.Words.Fonts.FontSettings.DefaultInstance"/> will be used.</para>
    ///
    /// <para>The default value is <c>null</c>.</para>
    /// </remarks>
    public FontSettings getFontSettings() { return mFontSettings; }
    public void setFontSettings(FontSettings value) { mFontSettings = value; }

    /// <summary>
    /// Returns the effective font settings instance.
    /// </summary>
    FontSettings getEffectiveFontSettings() { return mFontSettings != null ? mFontSettings : FontSettings.getDefaultInstance(); }

    /// <summary>
    /// Helps to resolve fonts in this document.
    /// </summary>
    /*override*/ DocumentFontProvider getFontProvider()
    {
        if (mFontProvider == null)
            mFontProvider = new DocumentFontProvider(this);
        return mFontProvider;
    }

    /// <summary>
    /// Gets the <see cref="Bibliography"/> object that represents the list of sources available in the document.
    /// </summary>
    public Bibliography getBibliography() throws Exception
    {
        return (mBibliography != null ? mBibliography : (mBibliography = new Bibliography(this)));
    }

    /// <summary>
    /// Returns a <see cref="Frameset"/> instance if this document represents a frames page.
    /// </summary>
    /// <remarks>
    /// If the document is not framed, the property has the <c>null</c> value.
    /// </remarks>
    /// <dev>
    /// Not sure that it is good decision. The main idea that this Frame object represents outermost frame i.e document itself and it
    /// implements proposed public API method Frame.NewAt(NewAtType) which splits this frame into two frames vertically or horizontally.
    /// In details, this frame becomes frameset and two new child frames are created, one is clone of this frameset and second is new one.
    /// Next, each frame can be split again and so on. This behavior is close to MS Word (it has buttons "New frame at top", "New frame at left", etc).
    /// This API guarantees that we will have correct frames tree before saving document. In contrary, if user will has ordinal API like new Frameset, new Frame, AddFrame
    /// we should check that frames tree is correct (there is no frameset with less than two child for example and so on) upon saving.
    ///
    /// This idea has several disadvantages though. For example, Document.Frame members is meaningless if document is not framed document and it can confuse user.
    /// </dev>
    public Frameset getFrameset() { return mFrame; }
    void setFrameset(Frameset value) { mFrame = value; }

    /// <summary>
    /// Specifies whether to include textboxes, footnotes and endnotes in word count statistics.
    /// </summary>
    public boolean getIncludeTextboxesFootnotesEndnotesInStat() { return !getDocPr().DoNotIncludeSubDocsInStats; }
    public void setIncludeTextboxesFootnotesEndnotesInStat(boolean value) { getDocPr().DoNotIncludeSubDocsInStats = !value; }

    int getNextTocEntryBookmarkIndex()
    {
        return mTocEntryBookmarkIndex++;
    }

    /*override*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        Document lhs = (Document)super.deepClone(isCloneChildren, cloningListener);

        // Clone only objects that can be changed during the document lifetime.
        lhs.mBuiltInDocumentProperties = (BuiltInDocumentProperties)mBuiltInDocumentProperties.deepClone();

        lhs.mCustomDocumentProperties = (CustomDocumentProperties)getCustomDocumentProperties().deepClone();
        lhs.mCustomDocumentProperties.setDocument(lhs);

        if (mGlossary != null)
            lhs.setGlossaryDocument((GlossaryDocument)mGlossary.deepClone(true, cloningListener));

        if (mTheme != null)
            lhs.mTheme = mTheme.deepClone();

        if (getComplianceInfo() != null)
            lhs.setComplianceInfo(getComplianceInfo().deepClone());

        lhs.mCustomXmlParts = mCustomXmlParts.deepClone();
        lhs.mPackageCustomParts = mPackageCustomParts.deepClone();

        if (mBibliography != null)
            lhs.mBibliography = mBibliography.deepClone(lhs);

        // WORDSNET-28870 We need to create a copy of CompObj to save a cloned document in a thread-safe manner.
        if (mCompObj != null)
            cloneCompObj(lhs);

        // These objects we do not change therefore we do not deep copy them.
        //
        // DigitalSignatures
        // Frames
        //
        // DropdownStrings
        // Cmds
        // CompObj
        //
        // AttachedToolbars
        // AllocatedCommands
        //
        // OriginalFileName and BaseUri are immutable and don't need to be copied.

        if (mVbaProject != null)
            lhs.mVbaProject = mVbaProject.deepClone();

        // Facade objects are reset to null.
        lhs.mSectionsCache = null;
        lhs.mFootnoteOptionsCache = null;
        lhs.mEndnoteOptionsCache = null;
        lhs.mRevisionsCache = null;
        lhs.mFontProvider = null;

        lhs.mFieldNumListLabels = null;

        if (mEditSession != null)
            lhs.mEditSession = new EditSession(mEditSession.getAuthor(), mEditSession.getDateTime());

        // SDT nodes are cloned before cloning custom XML parts, so we need reassign parts here.
        lhs.resolveSdtXmlMapping();

        lhs.getDocPr().Rsids = getDocPr().Rsids.deepClone();

        if (lhs.mMappedCustomXmlUpdater != null)
            lhs.attachMappedCustomXmlUpdater();

        return lhs;
    }

    private void cloneCompObj(Document lhs)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	lhs.mCompObj = new MemoryStream();
        	mCompObj.writeTo(lhs.mCompObj);
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Performs a deep copy of the <see cref="Document"/>.
    /// </summary>
    /// <returns>The cloned document.</returns>
    /// <dev>Kept to remain compatible with the old API.</dev>
    public Document deepClone()
    {
        Document newDoc = (Document)deepClone(true);
        return newDoc;
    }

    /// <summary>
    /// Reassigns custom XML parts in SDT XML mapping.
    /// </summary>
    private void resolveSdtXmlMapping()
    {
        NodeCollection sdts = getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        for (StructuredDocumentTag sdt : (Iterable<StructuredDocumentTag>) sdts)
            sdt.getXmlMapping().resolveCustomXmlPart();
    }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Node.Accept"]/*'/>
    /// <remarks>
    /// Calls <see cref="DocumentVisitor.VisitDocumentStart"/>, then calls <see cref="Node.Accept"/> for all child nodes of the document
    /// and calls <see cref="DocumentVisitor.VisitDocumentEnd"/> at the end.
    /// </remarks>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return acceptCore(visitor);
    }

    /// <summary>
    /// Accepts a visitor for visiting the start of the document.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptStart(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitDocumentStart(this);
    }

    /// <summary>
    /// Accepts a visitor for visiting the end of the document.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptEnd(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitDocumentEnd(this);
    }

    /// <summary>
    /// Appends the specified document to the end of this document.
    /// </summary>
    /// <param name="srcDoc">The document to append.</param>
    /// <param name="importFormatMode">Specifies how to merge style formatting that clashes.</param>
    public void appendDocument(Document srcDoc, /*ImportFormatMode*/int importFormatMode)
    {
        appendDocument(srcDoc, importFormatMode, null);
    }

    /// <summary>
    /// Appends the specified document to the end of this document.
    /// </summary>
    /// <param name="srcDoc">The document to append.</param>
    /// <param name="importFormatMode">Specifies how to merge style formatting that clashes.</param>
    /// <param name="importFormatOptions">Allows to specify options that affect formatting of a result document.</param>
    public void appendDocument(Document srcDoc, /*ImportFormatMode*/int importFormatMode, ImportFormatOptions importFormatOptions)
    {
        // WORDSNET-22103 Reworked to use DocumentInserter instead of pure NodeImporter for appending documents.
        DocumentInserter documentInserter = new DocumentInserter(this);
        documentInserter.appendDocument(srcDoc, importFormatMode, importFormatOptions);
    }

    /// <summary>
    /// Merges the specified documents to the end of this document.
    /// </summary>
    /// <remarks>
    /// Tries to keep the documents as they looked before the merge.
    /// </remarks>
    void mergeDocuments(/*params*/ Document... srcDocs) throws Exception
    {
        DocumentMerger.mergeDocuments(this, srcDocs);
    }

    /// <overloads>Saves the document.</overloads>
    /// <summary>
    /// Saves the document to a file. Automatically determines the save format from the extension.
    /// </summary>
    /// <param name="fileName">The name for the document. If a document with the
    /// specified file name already exists, the existing document is overwritten.</param>
    /// <returns>Additional information that you can optionally use.</returns>
    public SaveOutputParameters save(String fileName) throws Exception
    {
        return save(fileName, null);
    }

    /// <summary>
    /// Saves the document to a file in the specified format.
    /// </summary>
    /// <param name="fileName">The name for the document. If a document with the
    /// specified file name already exists, the existing document is overwritten.</param>
    /// <param name="saveFormat">The format in which to save the document.</param>
    /// <returns>Additional information that you can optionally use.</returns>
    public SaveOutputParameters save(String fileName, /*SaveFormat*/int saveFormat) throws Exception
    {
        return save(fileName, SaveOptions.createSaveOptions(saveFormat));
    }

    /// <summary>
    /// Saves the document to a file using the specified save options.
    /// </summary>
    /// <param name="fileName">The name for the document. If a document with the
    /// specified file name already exists, the existing document is overwritten.</param>
    /// <param name="saveOptions">Specifies the options that control how the document is saved. Can be <c>null</c>.</param>
    /// <returns>Additional information that you can optionally use.</returns>
    public SaveOutputParameters save(String fileName, SaveOptions saveOptions) throws Exception
    {
        if (saveOptions != null && !saveOptions.isMultipleMainPartsAllowed())
            ArgumentUtil.checkHasChars(fileName, "fileName");

        return save(null, fileName, saveOptions);
    }

    /// <summary>
    /// Saves the document to a stream or file using the specified save options.
    /// </summary>
    /// <param name="stream">Stream where to save the document.
    /// Can be <c>null</c>.</param>
    /// <param name="fileName">The name for the document. If a document with the
    /// specified file name already exists, the existing document is overwritten.
    /// Can be <c>null</c>.</param>
    /// <param name="saveOptions">Specifies the options that control how the document is saved. Can be <c>null</c>.</param>
    /// <returns>Additional information that you can optionally use.</returns>
    private SaveOutputParameters save(Stream stream, String fileName, SaveOptions saveOptions) throws Exception
    {
        if (saveOptions == null)
        {
            saveOptions = (fileName == null)
                ? new OoxmlSaveOptions()
                : SaveOptions.createSaveOptions(fileName);
        }

        SaveInfo saveInfo = new SaveInfo(this, stream, fileName, saveOptions);

        // FOSS

        DocumentValidator validator = null;
        if (saveOptions.isFlowFormat())
        {
            // We call validator here only when saving into flow formats because for
            // fixed page formats the validator has already been called by the layout engine.
            validator = new DocumentValidator();
            validator.execute(saveInfo);

            saveInfo.setShapeToConvertedOfficeMath(validator.getShapeToConvertedOfficeMath());
        }

        SaveOutputParameters result;
        if (!saveOptions.isMultipleMainPartsAllowed())
        {
            // "Classic" mode with one main output file.
            if (stream == null && !StringUtil.hasChars(fileName))
                throw new IllegalArgumentException("The arguments Stream and fileName cannot be null or empty string.");

            if (stream == null)
            {
                String directory = Path.getDirectoryName(fileName);
                if (StringUtil.hasChars(directory) && !Directory.exists(directory))
                {
                    Directory.createDirectory(directory);
                }

                // FOSS
                {
                    Stream streamResource = stream = File.create(fileName);
                    try /*JAVA: was using*/
                    {
                        saveInfo.Stream = stream;
                        result = saveCore(saveInfo);
                    }
                    finally { if (streamResource != null) streamResource.close(); }
                }
            }
            else
            {
                result = saveCore(saveInfo);
            }
        }
        else
        {
            result = saveCore(saveInfo);
        }

        if (validator != null)
            validator.revert();

        return result;
    }

    private void setupLoadWarningCallback(LoadOptions loadOptions)
    {
        if (loadOptions == null)
            return;

        // INSP DD This code deserves a comment.
        if (loadOptions.getWarningCallback() != null)
            setWarningCallback(loadOptions.getWarningCallback());
        else if (getWarningCallback() != null)
            loadOptions.setWarningCallback(getWarningCallback());
    }

    /// <summary>
    /// Saves the document to a stream using the specified format.
    /// </summary>
    /// <param name="stream">Stream where to save the document.</param>
    /// <param name="saveFormat">The format in which to save the document.</param>
    /// <returns>Additional information that you can optionally use.</returns>
    /// <javaName>void save(java.io.OutputStream outputStream, int saveFormat)</javaName>
    //JAVA-added public wrapper for internalized member
    public SaveOutputParameters save(java.io.OutputStream stream, /*SaveFormat*/int saveFormat) throws Exception
    {
        MemoryStream tempStream = new MemoryStream();
        SaveOutputParameters result = save(tempStream, saveFormat);
        tempStream.setPosition(0);
        com.aspose.ms.java.IO.JavaOnlyStreamUtil.copyStream(tempStream, stream);
        return result;
    }
    SaveOutputParameters save( Stream stream, /*SaveFormat*/int saveFormat) throws Exception
    {
        return save(stream, SaveOptions.createSaveOptions(saveFormat));
    }

    /// <summary>
    /// Saves the document to a stream using the specified save options.
    /// </summary>
    /// <param name="stream">Stream where to save the document.</param>
    /// <param name="saveOptions">Specifies the options that control how the document is saved. Can be <c>null</c>.
    /// If this is <c>null</c>, the document will be saved in the binary DOC format.</param>
    /// <returns>Additional information that you can optionally use.</returns>
    /// <javaName>void save(java.io.OutputStream outputStream, com.aspose.words.SaveOptions saveOptions)</javaName>
    //JAVA-added public wrapper for internalized member
    public SaveOutputParameters save(java.io.OutputStream stream, SaveOptions saveOptions) throws Exception
    {
        MemoryStream tempStream = new MemoryStream();
        SaveOutputParameters result = save(tempStream, saveOptions);
        tempStream.setPosition(0);
        com.aspose.ms.java.IO.JavaOnlyStreamUtil.copyStream(tempStream, stream);
        return result;
    }
    SaveOutputParameters save( Stream stream, SaveOptions saveOptions) throws Exception
    {
        if (!saveOptions.isMultipleMainPartsAllowed() && stream == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "stream");

        return save(stream, null, saveOptions);
    }

    // RK There is no HttpResponse in .NET Framework Client Profile.

    /// <summary>
    /// If the document contains no sections, creates one section with one paragraph.
    /// </summary>
    public void ensureMinimum()
    {
        getStyles().ensureMinimum();

        // Ensure a section.
        Section section = getFirstSection();
        if (section == null)
            section = (Section)appendChild(new Section(this));

        section.ensureMinimum();
    }

    /// <summary>
    /// Accepts all tracked changes in the document.
    /// </summary>
    /// <remarks>This method is a shortcut for <see cref="RevisionCollection.AcceptAll"/>.</remarks>
    public void acceptAllRevisions() throws Exception
    {
        getRevisions().acceptAll();
    }

    /// <overloads>Protects the document from changes.</overloads>
    /// <summary>
    /// Protects the document from changes without changing the existing password or assigns a random password.
    /// </summary>
    /// <remarks>
    /// <p>When a document is protected, the user can make only limited changes,
    /// such as adding annotations, making revisions, or completing a form.</p>
    ///
    /// <p>When you protect a document, and the document already has a protection password,
    /// the existing protection password is not changed.</p>
    ///
    /// <p>When you protect a document, and the document does not have a protection password,
    /// this method assigns a random password that makes it impossible to unprotect the document
    /// in Microsoft Word, but you still can unprotect the document in Aspose.Words as it does not
    /// require a password when unprotecting.</p>
    /// </remarks>
    /// <param name="type">Specifies the protection type for the document.</param>
    public void protect(/*ProtectionType*/int type)
    {
        getDocPr().DocumentProtection.protectOld(type);
        removeSectionUnlock(type);
        autoUpdateTrackRevisions();
    }

    /// <summary>
    /// Protects the document from changes and optionally sets a protection password.
    /// </summary>
    /// <remarks>
    /// <p>When a document is protected, the user can make only limited changes,
    /// such as adding annotations, making revisions, or completing a form.</p>
    ///
    /// <para>Note that document protection is different from write protection.
    /// Write protection is specified using the <see cref="WriteProtection"/>.</para>
    /// </remarks>
    /// <param name="type">Specifies the protection type for the document.</param>
    /// <param name="password">The password to protect the document with.
    /// Specify <c>null</c> or empty string if you want to protect the document without a password.</param>
    public void protect(/*ProtectionType*/int type, String password)
    {
        getDocPr().DocumentProtection.protect(type, password);
        removeSectionUnlock(type);
        autoUpdateTrackRevisions();
    }

    /// <overloads>Removes protection from the document.</overloads>
    /// <summary>
    /// Removes protection from the document regardless of the password.
    /// </summary>
    /// <remarks>
    /// <para>This method unprotects the document even if it has a protection password.</para>
    ///
    /// <para>Note that document protection is different from write protection.
    /// Write protection is specified using the <see cref="WriteProtection"/>.</para>
    /// </remarks>
    public void unprotect()
    {
        getDocPr().DocumentProtection.unprotect();
        autoUpdateTrackRevisions();
    }

    /// <summary>
    /// Removes protection from the document if a correct password is specified.
    /// </summary>
    /// <remarks>
    /// <para>This method unprotects the document only if a correct password is specified.</para>
    ///
    /// <para>Note that document protection is different from write protection.
    /// Write protection is specified using the <see cref="WriteProtection"/>.</para>
    /// </remarks>
    /// <param name="password">The password to unprotect the document with.</param>
    /// <returns><c>true</c> if a correct password was specified and the document was unprotected.</returns>
    public boolean unprotect(String password)
    {
        boolean isPasswordValid = getDocPr().DocumentProtection.validatePassword(password);
        if (isPasswordValid)
            unprotect();
        return isPasswordValid;
    }

    /// <summary>
    /// WORDSNET-5515 When turning on revisions only protection, MS Word also automatically turns on track revisions,
    /// so do we now. Otherwise it was possible to freely edit the document.
    /// </summary>
    private void autoUpdateTrackRevisions()
    {
        // Do not turn off tracking if it already is turned on.
        if (!getTrackRevisions())
            setTrackRevisions((getProtectionType() == ProtectionType.ALLOW_ONLY_REVISIONS));
    }

    /// <summary>
    /// Implements an earlier approach to table column widths re-calculation that has known issues.
    /// </summary>
    /// <remarks>
    /// The method is deprecated and it will be removed in a few releases.
    /// </remarks>
    @Deprecated () // 2022-03-11 WORDSNET-23539
    public void updateTableLayout()
    {
        // WORDSNET-749 Word seems to store incorrect width values if the preferred widths
        // of the cells are percents. In this case, we should recalculate the widths.
        NodeCollection tables = new NodeCollection(this, NodeType.TABLE, true);
        for (Table table : (Iterable<Table>) tables)
        {
            // Only invoke topmost tables because nested tables are handled by their parent tables.
            if (!table.isNested())
                table.updateLayout();
        }
    }

    /// <summary>
    /// Updates list labels for all list items in the document.
    /// </summary>
    /// <remarks>
    /// <para>This method updates list label properties such as <see cref="ListLabel.LabelValue"/> and
    /// <see cref="ListLabel.LabelString"/> for each <see cref="Paragraph.ListLabel"/> object in the document.</para>
    /// <para>Also, this method is sometimes implicitly called when updating fields in the document. This is required
    /// because some fields that may reference list numbers (such as TOC or REF) need them be up-to-date.</para>
    /// </remarks>
    public void updateListLabels() throws Exception
    {
        ListLabelUpdater.updateListLabels(this);
    }

    /// <summary>
    /// Updates the <see cref="Footnote.ActualReferenceMark"/> property of all footnotes and endnotes in the document.
    /// </summary>
    /// <remarks>
    /// Updating fields (<see cref="Document.UpdateFields"/>) may be necessary to get the correct result.
    /// </remarks>
    public void updateActualReferenceMarks() throws Exception
    {
        FootnoteIndexUpdater.execute(this);
    }

    /// <summary>
    /// Removes all macros (the VBA project) as well as toolbars and command customizations from the document.
    /// </summary>
    /// <remarks>
    /// <p>By removing all macros from a document you can ensure the document contains no macro viruses.</p>
    /// </remarks>
    public void removeMacros()
    {
        // RK We always had this behavior for removing toolbar and command customizations too,
        // but now we parse customizations separately we can provide a separate method to users if needed.
        mAttachedToolbars = null;
        mAllocatedCommands = null;
        mKeyMaps = null;

        mVbaProject = null;
        mVbaDocumentEvents = 0;
    }

    /// <summary>
    /// Updates the values of fields in the whole document.
    /// </summary>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="UpdateFields"]/*'/>
    ///
    /// <para>Use the <see cref="NormalizeFieldTypes"/> method before fields updating if there were document changes that affected field types.</para>
    /// <para>To update fields in a specific part of the document use <see cref="Range.UpdateFields"/>.</para>
    /// </remarks>
    public void updateFields() throws Exception
    {
        // Field engine tracking is quite complex task so postpone it till later and
        // suspend tracking during this operation.
        SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(this);
        try /*JAVA: was using*/
        {
        SuspendMappedCustomXmlUpdateDocument suspendMappedCustomXmlUpdateDocumentResource = new SuspendMappedCustomXmlUpdateDocument(this);
        try /*JAVA: was using*/
        {
            getRange().updateFields();
        }
        finally { if (suspendMappedCustomXmlUpdateDocumentResource != null) suspendMappedCustomXmlUpdateDocumentResource.dispose(); }
        }
        finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }
    }

    /// <summary>
    /// Unlinks fields in the whole document.
    /// </summary>
    /// <remarks>
    /// <para>Replaces all the fields in the whole document with their most recent results.</para>
    /// <para>To unlink fields in a specific part of the document use <see cref="Range.UnlinkFields"/>.</para>
    /// </remarks>
    public void unlinkFields() throws Exception
    {
        SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(this);
        try /*JAVA: was using*/
        {
            getRange().unlinkFields();
        }
        finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }
    }

    /// <summary>
    /// Changes field type values <see cref="FieldChar.FieldType"/> of <see cref="FieldStart"/>, <see cref="FieldSeparator"/>, <see cref="FieldEnd"/>
    /// in the whole document so that they correspond to the field types contained in the field codes.
    /// </summary>
    /// <remarks>
    /// <para>Use this method after document changes that affect field types.</para>
    /// <para>To change field type values in a specific part of the document use <see cref="Range.NormalizeFieldTypes"/>.</para>
    /// </remarks>
    public void normalizeFieldTypes()
    {
        getRange().normalizeFieldTypes();
    }

    /// <summary>
    /// Joins runs with same formatting in all paragraphs of the document.
    /// </summary>
    /// <remarks>
    /// <p>This is an optimization method. Some documents contain adjacent runs with same formatting.
    /// Usually this occurs if a document was intensively edited manually.
    /// You can reduce the document size and speed up further processing by joining these runs.</p>
    ///
    /// <p>The operation checks every <see cref="Paragraph"/> node in the document for adjacent <see cref="Run"/>
    /// nodes having identical properties. It ignores unique identifiers used to track editing sessions of run
    /// creation and modification. First run in every joining sequence accumulates all text. Remaining
    /// runs are deleted from the document.</p>
    /// </remarks>
    /// <returns>Number of joins performed. When <b>N</b> adjacent runs are being joined they count as <b>N - 1</b> joins.</returns>
    public int joinRunsWithSameFormatting()
    {
        // WORDSNET-27660 Suspend XML update during this run join.
        SuspendMappedCustomXmlUpdateDocument suspendMappedCustomXmlUpdateDocumentResource = new SuspendMappedCustomXmlUpdateDocument(this);
        try /*JAVA: was using*/
        {
            NodeCollection paragraphs = getChildNodes(NodeType.PARAGRAPH, true);
            StringBuilder sb = new StringBuilder(1024);
            int joinCount = 0;

            for (Paragraph para : (Iterable<Paragraph>) paragraphs)
                joinCount += para.joinRunsWithSameFormatting(sb, null);

            return joinCount;
        }
        finally { if (suspendMappedCustomXmlUpdateDocumentResource != null) suspendMappedCustomXmlUpdateDocumentResource.dispose(); }
    }

    /// <summary>
    /// Converts formatting specified in table styles into direct formatting on tables in the document.
    /// </summary>
    /// <remarks>
    /// <para>This method exists because this version of Aspose.Words provides only limited support for
    /// table styles (see below). This method might be useful when you load a DOCX or WordprocessingML
    /// document that contains tables formatted with table styles and you need to query formatting of
    /// tables, cells, paragraphs or text.</para>
    ///
    /// <para>This version of Aspose.Words provides limited support for table styles as follows:</para>
    /// <list type="bullet">
    ///
    /// <item>Table styles defined in DOCX or WordprocessingML documents are preserved as table styles
    /// when saving the document as DOCX or WordprocessingML.</item>
    ///
    /// <item>Table styles defined in DOCX or WordprocessingML documents are automatically converted
    /// to direct formatting on tables when saving the document into any other format,
    /// rendering or printing.</item>
    ///
    /// <item>Table styles defined in DOC documents are preserved as table styles when
    /// saving the document as DOC only.</item>
    /// </list>
    /// </remarks>
    public void expandTableStylesToDirectFormatting() throws Exception
    {
        TableFormattingExpander expander = new TableFormattingExpander();
        for (Table table : (Iterable<Table>) getChildNodes(NodeType.TABLE, true))
            expander.expand(table);
    }

    /// <summary>
    /// Cleans unused styles and lists from the document.
    /// </summary>
    public void cleanup() throws Exception
    {
        CleanupOptions options = new CleanupOptions();
        cleanup(options);
    }

    /// <summary>
    /// Cleans unused styles and lists from the document depending on given <see cref="CleanupOptions" />.
    /// </summary>
    public void cleanup(CleanupOptions options) throws Exception
    {
        DocumentCleaner.execute(this, options);

        if (getGlossaryDocument() != null)
            DocumentCleaner.execute(getGlossaryDocument(), options);
    }

    /// <summary>
    /// Removes external XML schema references from this document.
    /// </summary>
    public void removeExternalSchemaReferences()
    {
        getXmlNamespaces().clear();
        getXmlSchemaReferences().clear();
    }

    /// <summary>
    /// Starts automatically marking all further changes you make to the document programmatically as revision changes.
    /// </summary>
    /// <remarks>
    /// <p>If you call this method and then make some changes to the document programmatically,
    /// save the document and later open the document in MS Word you will see these changes as revisions.</p>
    ///
    /// <p>Currently Aspose.Words supports tracking of node insertions and deletions only. Formatting changes are not
    /// recorded as revisions.</p>
    ///
    /// <p>Automatic tracking of changes is supported both when modifying this document through node manipulations
    ///  as well as when using <see cref="DocumentBuilder" /></p>
    ///
    /// <p>This method does not change the <see cref="Document.TrackRevisions" /> option and does not use its value
    /// for the purposes of revision tracking.</p>
    /// </remarks>
    /// <param name="author">Initials of the author to use for revisions.</param>
    /// <param name="dateTime">The date and time to use for revisions.</param>
    /// <seealso cref="StopTrackRevisions"/>
    //JAVA: method is internalized
	void startTrackRevisionsInternal(String author, DateTime dateTime)
    {
        mTrackRevisionsEnabled = true;
        mEditSession = new EditSession(author, dateTime);
    }

	//JAVA-added public wrapper for internalized method
	public void startTrackRevisions(String author, java.util.Date dateTime)
	{
		startTrackRevisionsInternal(author, DateTime.fromJava(dateTime));
	}

    /// <summary>
    /// Starts automatically marking all further changes you make to the document programmatically as revision changes.
    /// </summary>
    /// <remarks>
    /// <p>If you call this method and then make some changes to the document programmatically,
    /// save the document and later open the document in MS Word you will see these changes as revisions.</p>
    ///
    /// <p>Currently Aspose.Words supports tracking of node insertions and deletions only. Formatting changes are not
    /// recorded as revisions.</p>
    ///
    /// <p>Automatic tracking of changes is supported both when modifying this document through node manipulations
    ///  as well as when using <see cref="DocumentBuilder" /></p>
    ///
    /// <p>This method does not change the <see cref="Document.TrackRevisions" /> option and does not use its value
    /// for the purposes of revision tracking.</p>
    /// </remarks>
    /// <param name="author">Initials of the author to use for revisions.</param>
    /// <seealso cref="StopTrackRevisions"/>
    public void startTrackRevisions(String author)
    {
        startTrackRevisionsInternal(author, DateTime.getNow());
    }

    /// <summary>
    /// Stops automatic marking of document changes as revisions.
    /// </summary>
    /// <seealso cref="StartTrackRevisions(string,DateTime)"/>
    public void stopTrackRevisions()
    {
        mTrackRevisionsEnabled = false;
    }

    /// <summary>
    /// Copies styles from the specified template to a document.
    /// </summary>
    /// <remarks>
    /// When styles are copied from a template to a document,
    /// like-named styles in the document are redefined to match the style descriptions in the template.
    /// Unique styles from the template are copied to the document. Unique styles in the document remain intact.
    /// </remarks>
    /// <dev>
    /// AM. This is experimental method for WORDSNET-16783
    /// Customer trying to copy all styles but we don't fully understand his requirements.
    /// He wants preserve style hierarchy and this is not possible if copy styles one by one as
    /// we resolve style in destination collection and update links to based and linked styles
    /// accordingly.
    /// </dev>
    public void copyStylesFromTemplate(String template) throws Exception
    {
        // WORDSNET-20391 This resource is defined by user, there is no need to use IResourceLoadingCallback.
        Stream stream = SystemPal.openStreamFromHref(template);
        try /*JAVA: was using*/
        {
            Document templateDoc = new Document(stream, null, false);
            copyStylesFromTemplate(templateDoc);
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Copies styles from the specified template to a document.
    /// </summary>
    /// <remarks>
    /// When styles are copied from a template to a document,
    /// like-named styles in the document are redefined to match the style descriptions in the template.
    /// Unique styles from the template are copied to the document. Unique styles in the document remain intact.
    /// </remarks>
    public void copyStylesFromTemplate(Document template)
    {
        getStyles().copyStylesFromTemplate(template.getStyles());
    }

    /// <summary>
    /// A collection of <see cref="XmlNamespace"/> objects that represents the entire collection of schemas in the Schema Library.
    /// </summary>
    XmlNamespaceCollection getXmlNamespaces() { return getDocPr().XmlNamespaces; }

    /// <summary>
    /// A collection of XMLSchemaReference objects that represent the unique namespaces that are attached to a document.
    /// </summary>
    XmlSchemaReferenceCollection getXmlSchemaReferences() { return getDocPr().XmlSchemaReferences; }

    /// <summary>
    /// Current edit session.
    /// </summary>
    EditSession getEditSession() { return mEditSession; }
    void setEditSession(EditSession value) { mEditSession = value; }

    /// <summary>
    /// Maps filename to the base folder (folder of the original document file name).
    /// </summary>
    /// <param name="fileName">Can be relative or absolute path. When this is
    /// absolute path, then it is returned without change.
    /// This also works okay when the file name is a UNC path.</param>
    String mapFileName(String fileName) throws Exception
    {
        String baseFolder = (mBaseUri != null) ? mBaseUri : "";
        //Path.Combine returns fileName without changes if it is an absolute path.
        return Path.getFullPath(Path.combine(baseFolder, fileName));
    }

    /// <summary>
    /// Can only add Section nodes to Document.
    /// </summary>
    /*override*/ boolean canInsert(Node newChild)
    {
        return (newChild.getNodeType() == NodeType.SECTION);
    }

    /// <summary>
    /// Detects file format and loads the document from a stream.
    /// </summary>
    void load(Stream stream, LoadOptions loadOptions) throws Exception
    {
        Debug.assertTrue(stream != null);

        // andrnosk: WORDSNET-8678 Some of customers are complaining because AW cannot load zero size file
        // (Empty docx-files can be created by right-clicking and chose -> New Microsoft Word Document),
        // that is why if the stream is empty we have to load blank document and return.
        if (stream.getLength() <= 0)
        {
            loadBlank(loadOptions);
            setLocaleDefaultsForNewDocument();
            return;
        }

        // If the user gives us no load options, use default.
        if (loadOptions == null)
            loadOptions = new LoadOptions();

        // Setup warning callback.
        if (loadOptions.getWarningCallback() != null)
            setWarningCallback(loadOptions.getWarningCallback());

        if (loadOptions.getLoadFormat() != LoadFormat.AUTO)
        {
            // The user has specified the format explicitly. Probably he is hoping to win some time by
            // avoiding auto detection. We do what he wants and attempt to load the format explicitly.
            // But the user might be fooled by the document's extension and specify wrong format
            // and loading will fail. It is too hard to explain to the user he is wrong, so we just
            // silence all exceptions if this attempt fails and perform a proper auto detection.
            long savedPos = stream.getPosition();
            try
            {
                loadCore(stream, loadOptions);
                return;
            }
            catch(Exception e)
            {
                // Silence all exceptions. Let the auto-detection proceed.

                // Original LoadOption object must not be changed during loading.
                loadOptions = loadOptions.deepClone();
                loadOptions.setLoadFormat(LoadFormat.AUTO);
                stream.setPosition(savedPos);

                // WORDSNET-22014 Remove loaded nodes to avoid content duplication.
                resetState();
            }
        }

        // Load with auto detection.
        loadCore(stream, loadOptions);
    }

    private void loadCore(Stream stream, LoadOptions loadOptions) throws Exception
    {
        Debug.assertTrue(stream != null);
        Debug.assertTrue(loadOptions != null);

        try
        {
            // Auto-recover from one frequent user's mistake - attempt to load from a memory stream
            // that was written to just before. Its position will be at the end, reset to start.
            if (stream.getPosition() == stream.getLength())
                stream.setPosition(0);

            // The lines copy the data we need from loadOptions into this document instance.
            setupLoadWarningCallback(loadOptions);

            // FOSS

            // andrnosk: WORDSNET-927 Use ResourceLoadingCallback specified in LoadOptions.
            setResourceLoadingCallback(loadOptions.getResourceLoadingCallback());
            setFontSettings(loadOptions.getFontSettings());

            String baseUriFromLoadOptions = getBaseUriFromLoadOptions(loadOptions, mOriginalFileName);
            if (StringUtil.hasChars(baseUriFromLoadOptions))
            {
                mBaseUri = baseUriFromLoadOptions;
            }

            // Perform file format detection if required.
            FileFormatInfo fileFormatInfo = null;
            if (loadOptions.getLoadFormat() == LoadFormat.AUTO)
            {
                FileFormatDetector detector = new FileFormatDetector();
                fileFormatInfo = detector.detect(stream, loadOptions.getEncodingInternal());

                // WORDSNET-26415 Fallback into WordDocument stream to get actual document content.
                if (fileFormatInfo.EmbeddedInWordDocument)
                {
                    FileSystem fs = new FileSystem(stream);
                    stream = (Stream)fs.getRoot().get("WordDocument");
                }

                // WORDSNET-24408 Fallback unknown format to TXT depending on input file extension.
                // WORDSNET-24982, 25109. Word does not fallback to TXT for ZIP-archives.
                // WORDSNET-27211 Don't fallback to TXT when load format specified explicitly.
                if ((fileFormatInfo.getLoadFormat() == LoadFormat.UNKNOWN) && !ZipUtilPal.isZipFile(stream) &&
                    (mOriginalLoadFormat == LoadFormat.AUTO))
                {
                    // WORDSNET-24603, 24609. Fallback unknown format to TXT for all streams, but not only FileStream.
                    FileStream fileStream = ms.as(stream, FileStream.class);
                    String ext = (fileStream != null)
                        ? Path.getExtension(fileStream.getName()).toLowerCase()
                        : "";

                    // IN. Actually, .sxw format is opened successfully with Word,
                    // and just cannot be opened in LibreOffice. But I left it for backward compatibility,
                    // as we have corresponding test for that in TestOdtContentReader.TestOpenOfficeWriter().
                    if ((!".docx".equals(ext)) && (!".odt".equals(ext)) && (!".sxw".equals(ext)))
                    {
                        fileFormatInfo.setLoadFormat(LoadFormat.TEXT);
                        fileFormatInfo.setEncoding(detector.getEncoding());
                    }
                }

                mOriginalLoadFormat = fileFormatInfo.getLoadFormat();
            }
            else
            {
                // WORDSNET-27211 We here because customer set load format explicitly. Let's just use it.
                mOriginalLoadFormat = loadOptions.getLoadFormat();
            }

            ProgressUtils.warnWhenProgressUnsupported(
                mOriginalLoadFormat, loadOptions.getWarningCallback(), loadOptions.getProgressCallback());

            // FOSS

            IDocumentReader documentReader = ReaderFactory.createReader(stream, loadOptions,
                mOriginalLoadFormat, fileFormatInfo, this);

            SuspendMappedCustomXmlUpdateDocument suspendMappedCustomXmlUpdateDocumentResource = new SuspendMappedCustomXmlUpdateDocument(this);
            try /*JAVA: was using*/
            {

                if (documentReader.isEncrypted())
                {
                    Stream decryptedStream = documentReader.decrypt();
                    // The easiest way to proceed here is to recurse.
                    loadCore(decryptedStream, loadOptions);
                    // Return, to avoid executing the common code below second time.
                    return;
                }

                // FOSS

                documentReader.read();

                if (FileFormatUtil.isPageSetupApplicable(mOriginalLoadFormat) &&
                    (loadOptions.getLanguagePreferences().getDefaultEditingLanguage() != EditingLanguage.ENGLISH_US))
                    getFirstSection().setPaperSizeByLanguage(loadOptions.getLanguagePreferences().getDefaultEditingLanguage());

                // FOSS

                postLoadTasks(loadOptions);

                if (loadOptions.getUpdateDirtyFields())
                    FieldUpdater.updateDirtyFields(this);
            }
            finally { if (suspendMappedCustomXmlUpdateDocumentResource != null) suspendMappedCustomXmlUpdateDocumentResource.dispose(); }
        }
        catch (Exception e)
        {
            // Cancellation was requested from the progress callback.
            CancellationException cancellationException = ms.as(e, CancellationException.class);
            if ((cancellationException != null) && (cancellationException.getCause() != null))
                throw (Exception)cancellationException.getCause();

            throw FileFormatUtil.convertLoadException(e);
        }
    }

    /// <summary>
    /// Performs post-loading useful tasks such as untangling bookmarks.
    /// </summary>
    private void postLoadTasks(LoadOptions loadOptions) throws Exception
    {
        DocumentPostLoader postLoader = new DocumentPostLoader();
        postLoader.execute(this, loadOptions, mOriginalLoadFormat);
    }

    /// <summary>
    /// Opens a document stream from the given file path or URI. Uses <see cref="IResourceLoadingCallback"/>,
    /// if specified. Can return <c>null</c>.
    /// </summary>
    /// <remarks>
    /// This overload uses <see cref="IResourceLoadingCallback"/> only if the given file path or URI represents
    /// a href with scheme.
    /// </remarks>
    static Stream openDocumentStream(String pathOrUri, IResourceLoadingCallback resourceLoadingCallback) throws Exception
    {
        return openDocumentStream(pathOrUri, resourceLoadingCallback, true);
    }

    /// <summary>
    /// Opens a document stream from the given file path or URI. Uses <see cref="IResourceLoadingCallback"/>,
    /// if specified. Can return <c>null</c>.
    /// </summary>
    /// <remarks>
    /// This overload can optionally use <see cref="IResourceLoadingCallback"/> every time it is present or only if
    /// the given file path or URI represents a href with scheme.
    /// </remarks>
    static Stream openDocumentStream(
        String pathOrUri,
        IResourceLoadingCallback resourceLoadingCallback,
        boolean useCallbackIfHrefWithSchemeOnly) throws Exception
    {
        if (!useCallbackIfHrefWithSchemeOnly || UriUtil.isHrefWithScheme(pathOrUri))
        {
            if (resourceLoadingCallback != null)
            {
                ResourceLoadingArgs args = new ResourceLoadingArgs("", pathOrUri, ResourceType.DOCUMENT);
                switch (resourceLoadingCallback.resourceLoading(args))
                {
                    case ResourceLoadingAction.DEFAULT:
                        return SystemPal.openStreamFromHref(pathOrUri);
                    case ResourceLoadingAction.SKIP:
                        return null;
                    case ResourceLoadingAction.USER_PROVIDED:
                        return !args.isDataEmpty()
                            ? new MemoryStream(args.getData())
                            : null;
                    default:
                        Debug.fail("Never invoked!");
                        return null;
                }
            }

            return SystemPal.openStreamFromHref(pathOrUri);
        }

        return File.openRead(pathOrUri);
    }

    /// <summary>
    /// Loads a blank document from the embedded resource.
    ///
    /// We load a blank document from a resource so the resulting document looks more like created by MS Word.
    /// The blank document resource includes a default fonts table, minimum default styles and also latent styles.
    /// </summary>
    void loadBlank(LoadOptions origLoadOptions) throws Exception
    {
        Debug.assertTrue(!hasChildNodes());
        final String RESOURCE_NAME = "Aspose.Words.Resources.Blank.docx";
        Stream stream = ResourceUtil.fetchResourceStream(RESOURCE_NAME);
        try /*JAVA: was using*/
        {
            // alexnosk WORDSNET-19906 Specify LoadFormat.Docx explicitly to skip file format detection to minimize blank document creation time.
            // Since we are sure Blank.docx is not encrypted, it is safe to specify it explicitly.
            LoadOptions blankDocLoadOptions = new LoadOptions();
            blankDocLoadOptions.setLoadFormat(LoadFormat.DOCX);
            blankDocLoadOptions.isLoadingBlankDocument(true);

            // WORDSNET-23836 Notify about progress when blank document is loading.
            blankDocLoadOptions.setProgressCallback((origLoadOptions != null) ? origLoadOptions.getProgressCallback() : null);

            load(stream, blankDocLoadOptions);
        }
        finally { if (stream != null) stream.close(); }

        // Make sure the new document does not have some rubbish in the properties.
        getBuiltInDocumentProperties().setAuthor("");
        getBuiltInDocumentProperties().setCompany("");
        getBuiltInDocumentProperties().setCreatedTimeInternal(DateTime.MinValue);
        getBuiltInDocumentProperties().setLastSavedBy("");
        getBuiltInDocumentProperties().setLastSavedTimeInternal(DateTime.MinValue);
        // WORDSNET-969 Platform-specific Creator tag in PDF tests causes separate golds for Java. Use Family instead of Product.
        getBuiltInDocumentProperties().setNameOfApplication(AssemblyConstants.FAMILY);
        getBuiltInDocumentProperties().setRevisionNumber(1);

        // Blank.docx contains some locale identifiers assigned to the styles that we don't want, remove them.
        getStyles().removeLocaleIdsFromStyles();

        // WORDSNET-6478 We should set this props to false, because we load Doc file, where this prop is true.
        getDocPr().CompatibilityOptions.setUICompat97To2003(false);

        // WORDSNET-16234 Resource file blank.doc has this property set to true, so reset it to false by design.
        // Note, we should reset it in two places so they are synchronized.
        getFontInfos().setEmbedSystemFonts(false);
        getDocPr().EmbedSystemFonts = false;
    }

    /// <summary>
    /// Save implementation.
    /// </summary>
    /// <param name="saveInfo"></param>
    private SaveOutputParameters saveCore(SaveInfo saveInfo) throws Exception
    {
        SaveOptions saveOptions = saveInfo.SaveOptions;
        Debug.assertTrue(saveOptions != null);

        // FOSS
        Debug.assertTrue((saveInfo.Stream != null) || saveOptions.isMultipleMainPartsAllowed());

        ProgressUtils.warnWhenProgressUnsupported(saveInfo.getSaveFormat(), getWarningCallback(), saveOptions.getProgressCallback());

        mSavedFileName = saveInfo.getFileName();

        // WORDSNET-19842 Use UTC time to update properties.
        if (saveOptions.getUpdateLastSavedTimeProperty())
            getBuiltInDocumentProperties().setLastSavedTimeInternal(DateTimeUtil.toUniversalTime(DateTimeUtil.getNow()));

        if (saveOptions.getUpdateLastPrintedProperty())
            getBuiltInDocumentProperties().setLastPrintedInternal(DateTimeUtil.toUniversalTime(DateTimeUtil.getNow()));

        if (saveOptions.getUpdateCreatedTimeProperty())
            getBuiltInDocumentProperties().setCreatedTimeInternal(DateTimeUtil.toUniversalTime(DateTimeUtil.getNow()));

        if (saveOptions.getSetBuiltInThemeIfNull())
        {
            // WORDSNET-6886 Use built-in theme if theme is null.
            initializeThemeIfNeeded();
        }

        if (!saveOptions.isFlowFormat() && saveOptions.getUpdateFields())
            updateFieldsBeforeSave();

        if (mBibliography != null)
            mBibliography.save();

        IDocumentWriter writer = WriterFactory.createWriter(saveOptions.getSaveFormat());

        // FOSS

        SaveOutputParameters result = writer.saveToStream(saveInfo);

        return result;
    }

    private void updateFieldsBeforeSave() throws Exception
    {
        SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(this);
        try /*JAVA: was using*/
        {
            FieldUpdater.updateFieldsBeforeSave(this);
        }
        finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }
    }

    /// <summary>
    /// Suspends revision tracking.
    /// </summary>
    /*override*/ void suspendTrackRevisions(/*SuspendedRevisionTypes*/int revisionTypes)
    {
        switch (revisionTypes)
        {
            case SuspendedRevisionTypes.ALL:
                mRevisionTrackingLockCount++;
                break;
            case SuspendedRevisionTypes.MOVE:
                mMoveRevisionTrackingLockCount++;
                break;
            default:
                throw new IllegalStateException();
        }
    }

    /// <summary>
    /// Resumes revision tracking.
    /// </summary>
    /*override*/ void resumeTrackRevisions(/*SuspendedRevisionTypes*/int revisionTypes)
    {
        switch (revisionTypes)
        {
            case SuspendedRevisionTypes.ALL:
                mRevisionTrackingLockCount--;
                break;
            case SuspendedRevisionTypes.MOVE:
                mMoveRevisionTrackingLockCount--;
                break;
            default:
                throw new IllegalStateException();
        }
    }

    /// <summary>
    /// Updates styles from attached template or path provided in load options.
    /// </summary>
    void updateStyles(SaveOptions so) throws Exception
    {
        String path = StringUtil.hasChars(so.getDefaultTemplate()) ? so.getDefaultTemplate() : getDocPr().AttachedTemplate;

        if (!StringUtil.hasChars(path))
        {
            WarningUtil.warn(getWarningCallback(), WarningType.DATA_LOSS,
                WarningSource.VALIDATOR, WarningStrings.EMPTY_TEMPLATE_PATH);
            return;
        }

        Document template = null;
        try
        {
            // Resiliently open template to update styles from.
            Stream stream = openDocumentStream(path, getResourceLoadingCallback());
            try /*JAVA: was using*/
            {
                FileFormatDetector detector = new FileFormatDetector();
                FileFormatInfo fi = detector.detect(stream);

                // Log warning when template type is not allowed.
                if (!fi.isDocumentTemplate())
                    throw new IllegalStateException();

                template = new Document(stream, null, false);
            }
            finally { if (stream != null) stream.close(); }
        }
        catch(Exception e)
        {
            WarningUtil.warn(getWarningCallback(), WarningType.DATA_LOSS,
                             WarningSource.VALIDATOR, WarningStrings.FAILED_TEMPLATE_LOAD, path);
        }

        if (template != null)
            getStyles().updateFromTemplate(template);
    }

    /// <summary>
    /// Indicates that node changes should be marked instead of real deletion/insertion.
    /// </summary>
    /*override*/ boolean isTrackRevisionsEnabled() { return (mRevisionTrackingLockCount == 0) && mTrackRevisionsEnabled; }

    /// <summary>
    /// Indicates whether node movements are tracked as move revisions. If it is <c>false</c>, the movements are
    /// represented as <see cref="RevisionType.Deletion"/> and <see cref="RevisionType.Insertion"/> revisions.
    /// </summary>
    /*override*/ boolean isMoveRevisionsTracked() { return isTrackRevisionsEnabled() && (mMoveRevisionTrackingLockCount == 0); }

    /// <summary>
    /// Returns Node by its id, used for debugging.
    /// </summary>
    /// <param name="id">Node string id</param>
    /// <returns></returns>
    Node getNodeById(String id)
    {
        String[] path = msString.split(id, '.');
        msArray.reverse(path);
        CompositeNode node = this;

        for (int i = 0; i < path.length - 1; i++)
            node = (CompositeNode)node.getChildNodes(NodeType.ANY, false).get(Convert.toInt32(path[i]));

        return node.getChildNodes(NodeType.ANY, false).get(Convert.toInt32(path[path.length - 1]));
    }

    /// <summary>
    /// Is created on demand, makes sense only for OOXML documents. For other documents it is <c>null</c>.
    /// </summary>
    OoxmlComplianceInfo getComplianceInfo() { return mComplianceInfo; }; void setComplianceInfo(OoxmlComplianceInfo value) { mComplianceInfo = value; };

    private OoxmlComplianceInfo mComplianceInfo;

    /// <summary>
    /// Sets defaults, that depend on current locale, to all sections.
    /// </summary>
    private void setLocaleDefaultsForNewDocument()
    {
        for (Section section : (Iterable<Section>) getSections())
            section.getSectPr().setLocaleDefaultsForNewDocument();
    }

    /// <summary>
    /// Removes Unlock from the single-sectioned document.
    /// </summary>
    private void removeSectionUnlock(/*ProtectionType*/int type)
    {
        if (type != ProtectionType.ALLOW_ONLY_FORM_FIELDS)
            return;

        // MSW unconditionally removes Unlocked attribute for single section document only.
        if (getSections().getCount() == 1)
            getFirstSection().getSectPr().remove(SectAttr.UNLOCKED);
    }

    /// <summary>
    /// Returns value indicating whether the document has cover page or not.
    /// </summary>
    boolean hasCoverPage()
    {
        NodeCollection stds = getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        for (StructuredDocumentTag std : (Iterable<StructuredDocumentTag>) stds)
        {
            if (!std.isSdtDocPart())
                continue;

            if (std.getControlProperties() == null)
                continue;

            if (!StructuredDocumentTag.COVER_PAGE_BUILDING_BLOCK_GALLERY.equals(std.getBuildingBlockGallery()))
                continue;

            return true;
        }

        return false;
    }

    //>>>>>>>> #region  Rendering

    /// <summary>
    /// A cache for graphics context.
    /// While rendering complex 3D effects using <see cref="Dml3DEffectsRenderingMode.Advanced"/>,
    /// the graphics context is taken from this cache, if it is not here, a new one is created.
    /// For 1 document, we must have 1 graphic context.
    /// </summary>
    StringToObjDictionary<Object> getOpenGLGraphicsContextCache()
    {
        if (mOpenGLGraphicsContextCache == null)
            mOpenGLGraphicsContextCache = new StringToObjDictionary<Object>();

        return mOpenGLGraphicsContextCache;
    }

    /// <summary>
    /// Gets a collection of revisions (tracked changes) that exist in this document.
    /// </summary>
    /// <remarks>
    /// <para>The returned collection is a "live" collection, which means if you remove parts of a document that contain
    /// revisions, the deleted revisions will automatically disappear from this collection.</para>
    /// </remarks>
    public RevisionCollection getRevisions()
    {
        if (mRevisionsCache == null)
            mRevisionsCache = new RevisionCollection(this);

        return mRevisionsCache;
    }

    /// <summary>
    /// Gets or sets a value indicating whether to work with the original or revised version of a document.
    /// </summary>
    /// <remarks>
    /// The default value is <b><see cref="Aspose.Words.RevisionsView.Original"/></b>.
    /// </remarks>
    /// <dev>
    /// Currently this option is supported for <see cref="ListLabel.LabelString"/>,
    /// <see cref="ListFormat.ListLevel"/> and <see cref="ListFormat.ListLevelNumber"/> only.
    /// </dev>
    public /*RevisionsView*/int getRevisionsView() { return mRevisionsView; }
    public void setRevisionsView(/*RevisionsView*/int value) { mRevisionsView = value; }

    /// <summary>
    /// Gets or sets the character spacing adjustment of a document.
    /// </summary>
    public /*JustificationMode*/int getJustificationMode() { return getDocPr().CharacterSpacingType; }
    public void setJustificationMode(/*JustificationMode*/int value) { getDocPr().CharacterSpacingType = value; }

    /// <inheritdoc />
    @ReservedForInternalUse @Deprecated
    public void /*IWatermarkProvider.*/add(Shape watermark) throws Exception
    {
        // If a section has no headers, it will be linked with the headers of the first section.
        // So, the first section should have headers, and other sections may not have headers.
        getFirstSection().addWatermark(watermark, true);
        for (int i = 1; i < getSections().getCount(); i++)
            getSections().get(i).addWatermark(watermark, false);

        // FOSS
    }

    /// <inheritdoc />
    @ReservedForInternalUse @Deprecated
    public Shape /*IWatermarkProvider.*/get()
    {
        // WORDSNET-28260 Resilience against completely empty document.
        if (getFirstSection() == null)
            return null;

        // WORDSNET-24516 Resilience when primary header is missing.
        HeaderFooter headerPrimary = getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY);
        if (headerPrimary == null)
            return null;

        ShapeCollection shapes = headerPrimary.getShapes();
        for (Shape shape : (Iterable<Shape>) shapes)
        {
            if (shape.isWatermark())
                return shape;
        }

        return null;
    }

    /// <inheritdoc />
    @ReservedForInternalUse @Deprecated
    public void /*IWatermarkProvider.*/remove()
    {
        for (Section section : (Iterable<Section>) getSections())
        {
            section.removeWatermark();
            // FOSS
        }
    }

    //<<<<<<<< #endregion  Rendering

    /// <summary>
    /// Attaches an instance of <see cref="MappedCustomXmlUpdater"/> as a node changing callback.
    /// </summary>
    private void attachMappedCustomXmlUpdater()
    {
        mMappedCustomXmlUpdater = new MappedCustomXmlUpdater(this);
        addInternalNodeChangingCallback(mMappedCustomXmlUpdater);
    }

    //>>>>>>>> #region  Footnote and Endnote Options

    /// <summary>
    /// Provides options that control numbering and positioning of footnotes in this document.
    /// </summary>
    public FootnoteOptions getFootnoteOptions()
    {
        if (mFootnoteOptionsCache == null)
            mFootnoteOptionsCache = new FootnoteOptions(this);
        return mFootnoteOptionsCache;
    }

    /// <summary>
    /// Provides options that control numbering and positioning of endnotes in this document.
    /// </summary>
    public EndnoteOptions getEndnoteOptions()
    {
        if (mEndnoteOptionsCache == null)
            mEndnoteOptionsCache = new EndnoteOptions(this);
        return mEndnoteOptionsCache;
    }

    /// <summary>
    /// We implement ISectionAttrSource to expose footnote and endnote options in the public API nicely.
    /// These options are ugly in MS Word because global options in docpr are for Word97 documents only.
    /// Options for newer documents are in properties of each section.
    ///
    /// The scenarios we want are as follows:
    ///
    /// 1. Opening and saving without modification preserves all document-wide and section-wide
    /// footnote and endnote options always.
    ///
    /// 2. Opening a document and quering document-wide properties returns document-wide properties.
    /// Modifying document-wide properties modifies both document-wide and properties in each section.
    /// At least this seems to be what MS Word is doing.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Object /*ISectionAttrSource.*/getDirectSectionAttr(int key)
    {
        return getDocPr().FootnotePr.getDirectSectionAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*ISectionAttrSource.*/getDirectSectionAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return getDocPr().FootnotePr.getDirectSectionAttr(key, revisionsView);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*ISectionAttrSource.*/fetchInheritedSectionAttr(int key)
    {
        return SectPr.fetchDefaultAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*ISectionAttrSource.*/fetchSectionAttr(int key)
    {
        Object value = ((ISectionAttrSource)this).getDirectSectionAttr(key);
        return (value != null) ? value : ((ISectionAttrSource)this).fetchInheritedSectionAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*ISectionAttrSource.*/fetchSectionAttr(int key, /*RevisionsView*/int revisionsView)
    {
        Object value = ((ISectionAttrSource)this).getDirectSectionAttr(key, revisionsView);
        return (value != null) ? value : ((ISectionAttrSource)this).fetchInheritedSectionAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*ISectionAttrSource.*/setSectionAttr(int key, Object value)
    {
        getDocPr().FootnotePr.setSectionAttr(key, value);

        for (Section section : (Iterable<Section>) getSections())
            ((ISectionAttrSource)section).setSectionAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*ISectionAttrSource.*/setSectionAttr(int key, Object value, /*RevisionsView*/int revisionsView)
    {
        getDocPr().FootnotePr.setAttr(key, value, revisionsView);

        for (Section section : (Iterable<Section>) getSections())
            ((ISectionAttrSource)section).setSectionAttr(key, value, revisionsView);
    }

    @ReservedForInternalUse @Deprecated
    public void /*ISectionAttrSource.*/clearSectionAttrs()
    {
        getDocPr().FootnotePr.clearSectionAttrs();

        for (Section section : (Iterable<Section>) getSections())
            ((ISectionAttrSource)section).clearSectionAttrs();
    }

    //<<<<<<<< #endregion  Footnote and Endnote Options

    //>>>>>>>> #region  Fields

    /// <summary>
    /// Gets a <see cref="Fields.FieldOptions"/> object that represents options to control field handling in the document.
    /// </summary>
    public FieldOptions getFieldOptions()
    {
        if (mFieldOptionsCache == null)
            mFieldOptionsCache = new FieldOptions(this);
        return mFieldOptionsCache;
    }

    /// <summary>
    /// Gets or sets a flag indicating that Microsoft Word will remove all user information from comments, revisions and
    /// document properties upon saving the document.
    /// </summary>
    public boolean getRemovePersonalInformation() { return getDocPr().RemovePersonalInformation; }
    public void setRemovePersonalInformation(boolean value) { getDocPr().RemovePersonalInformation = value; }

    /// <summary>
    /// Gets or sets a <see cref="VbaProject" />.
    /// </summary>
    public VbaProject getVbaProject() { return mVbaProject; }
    public void setVbaProject(VbaProject value) { mVbaProject = value; }

    void readVbaProject(MemoryStorage storage, byte[] signature) throws Exception
    {
        if (storage != null)
        {
            mVbaProject = new VbaProject(storage);
            mVbaProject.setSignature(signature);
        }
    }

    /// <summary>
    /// Gets previously set <see cref="FieldNumListLabel"/> instance for the given field start.
    /// </summary>
    /// <param name="fieldStart"></param>
    /// <returns></returns>
    FieldNumListLabel getFieldNumListLabel(FieldStart fieldStart)
    {
        if (mFieldNumListLabels == null)
            return null;

        FieldNumListLabel value = DictionaryExtension.getValueOrNull(mFieldNumListLabels, fieldStart);
        return value;
    }

    /// <summary>
    /// Sets the specified <see cref="FieldNumListLabel"/> instance for the given field start.
    /// </summary>
    /// <param name="fieldStart"></param>
    /// <param name="label"></param>
    void setFieldNumListLabel(FieldStart fieldStart, FieldNumListLabel label)
    {
        // Create on the first demand.
        if (mFieldNumListLabels == null)
            mFieldNumListLabels = new HashMap<FieldStart, FieldNumListLabel>();

        mFieldNumListLabels.put(fieldStart, label);
    }

    /// <summary>
    /// Forgets all of the previously set <see cref="FieldNumListLabel"/> instances for every field start
    /// within the document.
    /// </summary>
    void clearFieldNumListLabels()
    {
        mFieldNumListLabels = null;
    }

    DateTime getCurrentDateTimeCache()
    {
        if (DateTime.equals(mCurrentDateTimeCache, DateTime.MinValue))
            mCurrentDateTimeCache = DateTimeUtil.getNow();

        return mCurrentDateTimeCache;
    }

    FieldUpdateProgressContext getFieldUpdateProgressContext() { return mFieldUpdateProgressContext; }; void setFieldUpdateProgressContext(FieldUpdateProgressContext value) { mFieldUpdateProgressContext = value; };

    private FieldUpdateProgressContext mFieldUpdateProgressContext;
    //<<<<<<<< #endregion 

    // RK It is interesting to note that if you clone a document and attempt to save both documents
    // using different threads, it could screw up because some of these unparsed structures are
    // memory streams and they have position, etc that are not safe for multithreading.
    // The risk of this is small, but if this ever happens, I need to store this data in a better way.

    // Word document fields
    private BuiltInDocumentProperties mBuiltInDocumentProperties = new BuiltInDocumentProperties();
    private CustomDocumentProperties mCustomDocumentProperties;
    /// <summary>
    /// Can be null.
    /// </summary>
    private GlossaryDocument mGlossary;
    /// <summary>
    /// Can be null.
    /// </summary>
    private Theme mTheme;
    private CustomXmlPartCollection mCustomXmlParts = new CustomXmlPartCollection();
    private CustomPartCollection mPackageCustomParts = new CustomPartCollection();

    /// <summary>
    /// AM. I avoided to use late creation of signature collection to simplify reader's code.
    /// </summary>
    private /*final*/ DigitalSignatureCollection mDigitalSignatures = new DigitalSignatureCollection();

    /// <summary>
    /// Represents root frame object of this document. Can be null.
    /// </summary>
    private Frameset mFrame;

    // Some unparsed data.
    private byte[] mDropdownStrings;
    private MemoryStream mCompObj;
    private byte[] mMsoEnvelope;

    // MS Word UI customization related.
    private byte[] mAttachedToolbars;
    private ArrayList<AllocatedCommand> mAllocatedCommands;
    private ArrayList<KeyMap> mKeyMaps;

    // VBA related.
    private /*VbaDocumentEvents*/int mVbaDocumentEvents;
    private VbaProject mVbaProject;

    /// <summary>
    /// The original name of the file from which the document was loaded. Can be null.
    /// </summary>
    private /*final*/ String mOriginalFileName;
    private String mSavedFileName;
    private /*LoadFormat*/int mOriginalLoadFormat;
    /// <summary>
    /// Supposed to be the folder where the document is stored. Can be null.
    /// </summary>
    private String mBaseUri;

    /// <summary>
    /// Facades, created on demand.
    /// </summary>
    private SectionCollection mSectionsCache;
    private StringToObjDictionary<Object> mOpenGLGraphicsContextCache;
    private FootnoteOptions mFootnoteOptionsCache;
    private EndnoteOptions mEndnoteOptionsCache;
    private RevisionCollection mRevisionsCache;
    private HashMap<FieldStart, FieldNumListLabel> mFieldNumListLabels;
    private int mTocEntryBookmarkIndex = 256000000;
    private FieldOptions mFieldOptionsCache;

    /// <summary>
    /// It seems that in DOC (and in the model) there is an 8 byte prefix before the signature.
    /// 4 bytes length of the signature without the prefix.
    /// 4 bytes - the value 8. Not sure what it is. Maybe length of the prefix.
    /// </summary>
    static final int VBA_SIGNATURE_PREFIX_LENGTH = 8;

    // Shows how long writing to fixed page format takes.
    // This is needed for performance tests.
    long FixedPageFormatStoringMilliseconds = 0;

    private boolean mTrackRevisionsEnabled;
    private int mRevisionTrackingLockCount;
    private int mMoveRevisionTrackingLockCount;
    private EditSession mEditSession;

    // Initialize DateTime for Java.
    private DateTime mCurrentDateTimeCache = DateTime.MinValue;

    private FontSettings mFontSettings;
    private DocumentFontProvider mFontProvider;

    private /*RevisionsView*/int mRevisionsView = RevisionsView.ORIGINAL;

    private /*final*/ TaskPaneCollection mWebExtensionTaskPanes = new TaskPaneCollection();

    private Watermark mWatermark;

    private MappedCustomXmlUpdater mMappedCustomXmlUpdater;
    private Bibliography mBibliography;
    /// <summary>
    /// Static ctor.
    /// </summary>
    static/* Document()*/
    {
        // Register encodings supported in the desktop .NET Framework but not in .NET Standard.
        EncodingUtil.registerEncodings();
    }
}

