// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/02/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import java.awt.Color;
import com.aspose.ms.System.Drawing.msColor;
import java.util.HashMap;


/// <summary>
/// Provides the abstract base class for a main document and a glossary document of a Word document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/aspose-words-document-object-model/">Aspose.Words Document Object Model (DOM)</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>Aspose.Words represents a Word document as a tree of nodes. <see cref="DocumentBase"/> is a
/// root node of the tree that contains all other nodes of the document.</para>
///
/// <para><see cref="DocumentBase"/> also stores document-wide information such as <see cref="Styles"/> and
/// <see cref="Lists"/> that the tree nodes might refer to.</para>
///
/// <seealso cref="Aspose.Words.Document"/>
/// <seealso cref="Aspose.Words.DocumentBase"/>
/// </remarks>
public abstract class DocumentBase<T extends Node> extends CompositeNode<T>
{
    /// <summary>
    /// Ctor.
    /// </summary>
    protected DocumentBase()
    {
        mNullNode = new NullNode(this);

        // We just need a start to make new shapes unique ids.
        // This number is well above MS Word native shape numbers so its safe.
        mNextShapeId = 100000;

        // According to "Office Drawing Extensions to Office Open XML Structure" textbox 'id' is unsignedShort type value,
        // that is why we use half of unsignedShort as a start unique id (ushort.MaxValue / 2).
        // WORDSNET-22298 Exclude 0x7FFF value from the id's range. In the Word 2019 64 bit this value breaks document layout.
        mNextDmlTextBoxId = 32768;

        mFontInfos = new FontInfoCollection();

        mStyles = new StyleCollection(this);
        mLists = new ListCollection(this);
        mHtmlBlockCollection = new HtmlBlockCollection();
        mPeople = new PersonCollectionInternal();
        mDocPr = new DocPr();
        mVariables = new VariableCollection();
        mFootnoteSeparators = new FootnoteSeparatorCollection();
        // FOSS

        mNodeChangingCallbackChain = new NodeChangingCallbackChain();
    }

    /*override*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {

        // Calling the base clones all child nodes if necessary.
        // We don't need to import child nodes because all styles, lists etc are cloned too
        // and existing ids will work fine.
        DocumentBase lhs = (DocumentBase)super.deepClone(isCloneChildren, cloningListener);

        lhs.mNullNode = new NullNode(lhs);
        lhs.mNodeChangingCallbackChain = new NodeChangingCallbackChain();

        // DD: There is one special effect that happens to this.mSdtIdManager:
        // When we clone the base nodes, in the first line of this function,
        // we perform adding newly cloned SDT nodes and they will get new
        // Ids because lhs.mSdtIdManager is shallow cloned and still references
        // this.mSdtIdManager.
        // We will reset Ids back to their original numbers for lhs by using RestoreSdtIds below,
        // but the source mSdtManager will store references of newIDs during document life cycle.
        // We could add special code to remove these newIDs, but I don't think Clone is done
        // often enough to clutter mSdtIdManager too much with those phantom IDs.
        lhs.mSdtIdManager = null;

        // will have to create new placeholder and look and attach placeholders to SDTs if needed.
        lhs.mSdtPlaceholderManager = null;

        // Copy only objects that can be changed during the document lifetime.
        lhs.mFontInfos = mFontInfos.deepClone();
        lhs.mStyles = mStyles.deepClone(lhs);
        lhs.mLists = mLists.deepClone(lhs, cloningListener);
        lhs.mDocPr = mDocPr.deepClone();
        lhs.mVariables = mVariables.deepClone();
        lhs.mFootnoteSeparators = mFootnoteSeparators.deepClone(lhs, cloningListener);
        lhs.mHtmlBlockCollection = mHtmlBlockCollection.deepClone();
        lhs.mPeople = mPeople.deepClone();
        // FOSS

        if (mBackgroundShape != null)
            lhs.mBackgroundShape = (Shape)lhs.importNode(mBackgroundShape, true, cloningListener);

        if (isCloneChildren)
            restoreSdtIds(lhs);

        if (InternManager != null)
            lhs.InternManager = null;
        // WORDSNET-28264 We shall reset mTemporaryDoc. It is not safe to use it in a cloned copy.
        lhs.mTemporaryDoc = null;

        return lhs;
    }

    /// <summary>
    /// Resets document state as before loading data from a source.
    /// </summary>
    protected void resetState()
    {
        mStyles = new StyleCollection(this);
        removeAllChildren();
    }

    /// <summary>
    /// Cloning of DocumentBase instances needs to keep Ids of Sdt elements <see cref="StructuredDocumentTag"/>> during clone.
    /// This is because cloning the whole documentBase does not interfere with Id uniqueness, so we need to retain Ids since OOXML
    /// spec considers them persistent. See more in <see cref="StructuredDocumentTag.Id"/>
    /// </summary>
    /// <remarks>
    /// RK: This looks a bit ugly to override IDs after cloning. Is there a way to make it nicer?
    /// DD: It is not easy with present cloning technique. If cloning was a a visitor-style operation, then we could set mSdtIdManager to null
    /// before SDT nodes are appended to the cloned DocumentBase and this would have fixed the issue.
    /// </remarks>
    private void restoreSdtIds(DocumentBase lhs)
    {
        NodeCollection sdts = getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        if (sdts.getCount() != 0)
        {
            NodeCollection dstSdts = lhs.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
            Debug.assertTrue(sdts.getCount() == dstSdts.getCount());
            for (int sdtIdx = 0; sdtIdx < dstSdts.getCount(); sdtIdx++)
                ((StructuredDocumentTag)dstSdts.get(sdtIdx)).setId(((StructuredDocumentTag)sdts.get(sdtIdx)).getId());
        }
    }

    /// <overloads>Imports a node from another document to the current document.</overloads>
    /// <summary>
    /// <p>Imports a node from another document to the current document.</p>
    /// </summary>
    /// <remarks>
    /// <p>This method uses the <see cref="ImportFormatMode.UseDestinationStyles"/> option to resolve formatting.</p>
    ///
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Document.ImportNodeCommon"]/*'/>
    ///
    /// <seealso cref="NodeImporter"/>
    /// </remarks>
    /// <param name="srcNode">The node being imported.</param>
    /// <param name="isImportChildren"><c>true</c> to import all child nodes recursively; otherwise, <c>false</c>.</param>
    /// <returns>The cloned node that belongs to the current document.</returns>
    public Node importNode(Node srcNode, boolean isImportChildren)
    {
        return importNode(srcNode, isImportChildren, ImportFormatMode.USE_DESTINATION_STYLES, null);
    }

    /// <summary>
    /// <p>Imports a node from another document to the current document.</p>
    /// </summary>
    /// <remarks>
    /// <p>This method uses the <see cref="ImportFormatMode.UseDestinationStyles"/> option to resolve formatting.</p>
    ///
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Document.ImportNodeCommon"]/*'/>
    ///
    /// <seealso cref="NodeImporter"/>
    /// </remarks>
    /// <param name="srcNode">The node being imported.</param>
    /// <param name="isImportChildren"><c>true</c> to import all child nodes recursively; otherwise, <c>false</c>.</param>
    /// <param name="cloningListener">The listener to the node cloning events.</param>
    /// <returns>The cloned node that belongs to the current document.</returns>
    Node importNode(Node srcNode, boolean isImportChildren, INodeCloningListener cloningListener)
    {
        return importNode(srcNode, isImportChildren, ImportFormatMode.USE_DESTINATION_STYLES, cloningListener);
    }

    /// <summary>
    /// <p>Imports a node from another document to the current document with an option to control formatting.</p>
    /// </summary>
    /// <remarks>
    /// <p>This overload is useful to control how styles and list formatting are imported.</p>
    ///
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Document.ImportNodeCommon"]/*'/>
    ///
    /// <seealso cref="ImportFormatMode"/>
    /// <seealso cref="NodeImporter"/>
    /// </remarks>
    /// <param name="srcNode">The node to imported.</param>
    /// <param name="isImportChildren"><c>true</c> to import all child nodes recursively; otherwise, <c>false</c>.</param>
    /// <param name="importFormatMode">Specifies how to merge style formatting that clashes.</param>
    /// <returns>The cloned, imported node. The node belongs to the destination document, but has no parent.</returns>
    public Node importNode(Node srcNode, boolean isImportChildren, /*ImportFormatMode*/int importFormatMode)
    {
        return importNode(srcNode, isImportChildren, importFormatMode, null);
    }

    /// <summary>
    /// <p>Imports a node from another document to the current document with an option to control formatting.</p>
    /// </summary>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Document.ImportNodeCommon"]/*'/>
    ///
    /// <seealso cref="ImportFormatMode"/>
    /// <seealso cref="NodeImporter"/>
    /// </remarks>
    /// <param name="srcNode">The node to imported.</param>
    /// <param name="isImportChildren"><c>true</c> to import all child nodes recursively; otherwise, <c>false</c>.</param>
    /// <param name="importFormatMode">Specifies how to merge style formatting that clashes.</param>
    /// <param name="cloningListener">The listener to the node cloning events.</param>
    /// <returns>The cloned, imported node. The node belongs to the destination document, but has no parent.</returns>
    private Node importNode(
        Node srcNode,
        boolean isImportChildren,
        /*ImportFormatMode*/int importFormatMode,
        INodeCloningListener cloningListener)
    {
        NodeImporter importer = new NodeImporter(srcNode.getDocument(), this, importFormatMode);
        return importer.importNode(srcNode, isImportChildren, cloningListener);
    }

    /// <summary>
    /// Called before any insert or remove node in the tree takes place.
    /// Marks the document as modified.
    /// Returns an argument object for the node changed event if there are any listeners registered for this event.
    /// </summary>
    NodeChangingArgs internalEvent(Node node, Node oldParent, Node newParent, /*NodeChangingAction*/int action)
    {
        // Indicate the document was modified by the user.
        mTreeChangeCount++;

        return !mNodeChangingCallbackChain.isEmpty()
            ? new NodeChangingArgs(node, oldParent, newParent, action)
            : null;
    }

    /// <summary>
    /// Marks document as modified.
    /// </summary>
    /// <remarks>
    /// This forces auxiliary collections rebuilt.
    /// </remarks>
    void markChanged()
    {
        mTreeChangeCount++;
    }

    void beforeEvent(NodeChangingArgs args)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	if (mNodeChangingCallbackChain.isEmpty())
            	return;

        	switch (args.getAction())
        	{
            	case NodeChangingAction.INSERT:
                	mNodeChangingCallbackChain.nodeInserting(args);
                	break;
            	case NodeChangingAction.REMOVE:
                	mNodeChangingCallbackChain.nodeRemoving(args);
                	break;
            	default:
                	throw new IllegalStateException("Unknown node changed action.");
        	}
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    void afterEvent(NodeChangingArgs args)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	if (mNodeChangingCallbackChain.isEmpty())
            	return;

        	switch (args.getAction())
        	{
            	case NodeChangingAction.INSERT:
                	mNodeChangingCallbackChain.nodeInserted(args);
                	break;
            	case NodeChangingAction.REMOVE:
                	mNodeChangingCallbackChain.nodeRemoved(args);
                	break;
            	default:
                	throw new IllegalStateException("Unknown node changed action.");
        	}
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Adds the specified <see cref="INodeChangingCallback"/> instance to the end of the invocation chain.
    /// </summary>
    void addInternalNodeChangingCallback(INodeChangingCallback callback)
    {
        mNodeChangingCallbackChain.addInternalCallback(callback);
    }

    /// <summary>
    /// Removes the specified <see cref="INodeChangingCallback"/> instance from the invocation chain
    /// if the callback is contained in it. Returns <c>true</c> if the callback was removed.
    /// </summary>
    boolean removeInternalNodeChangingCallback(INodeChangingCallback callback)
    {
        return mNodeChangingCallbackChain.removeInternalCallback(callback);
    }

    abstract void suspendTrackRevisions(/*SuspendedRevisionTypes*/int revisionTypes);
    abstract void resumeTrackRevisions(/*SuspendedRevisionTypes*/int revisionTypes);

    void suspendMappedCustomXmlUpdate()
    {
        mSuspendMappedCustomXmlUpdate++;
    }

    void resumeMappedCustomXmlUpdate()
    {
        mSuspendMappedCustomXmlUpdate--;
    }

    /// <summary>
    /// Indicates whether mapped CustomXML runtime update is enabled.
    /// </summary>
    boolean isMappedCustomXmlUpdateSuspended() { return mSuspendMappedCustomXmlUpdate > 0; }

    abstract boolean isTrackRevisionsEnabled();

    /// <summary>
    /// Indicates whether node movements are tracked as move revisions. If it is <c>false</c>, the movements are
    /// represented as <see cref="RevisionType.Deletion"/> and <see cref="RevisionType.Insertion"/> revisions.
    /// </summary>
    abstract boolean isMoveRevisionsTracked();

    /// <summary>
    /// Gets this instance.
    /// </summary>
    public /*override*/ DocumentBase getDocument() { return this; }

    /// <summary>
    /// Called when a node is inserted or removed in the document.
    /// </summary>
    public INodeChangingCallback getNodeChangingCallback() { return mNodeChangingCallbackChain.getUserCallback(); }
    public void setNodeChangingCallback(INodeChangingCallback value) { mNodeChangingCallbackChain.setUserCallback(value); }

    /// <summary>
    /// Allows to control how external resources are loaded.
    /// </summary>
    public IResourceLoadingCallback getResourceLoadingCallback() { return mResourceLoadingCallback; }
    public void setResourceLoadingCallback(IResourceLoadingCallback value) { mResourceLoadingCallback = value; }

    /// <summary>
    /// Incremented each time a node is inserted or removed. Used as a version to invalidate live node collections.
    /// </summary>
    int getTreeChangeCount() { return mTreeChangeCount; }

    Node getNullNode() { return mNullNode; }
    /// <summary>
    /// Provides access to properties of fonts used in this document.
    /// </summary>
    /// <remarks>
    /// <para>This collection of font definitions is loaded as is from the document.
    /// Font definitions might be optional, missing or incomplete in some documents.</para>
    ///
    /// <para>Do not rely on this collection to ascertain that a particular font is used in the document.
    /// You should only use this collection to get information about fonts that might be used in the document.</para>
    ///
    /// <seealso cref="FontInfoCollection"/>
    /// <seealso cref="FontInfo"/>
    /// </remarks>
    public FontInfoCollection getFontInfos() { return mFontInfos; }

    /// <summary>
    /// Returns a collection of styles defined in the document.
    /// </summary>
    /// <remarks>
    /// <p>For more information see the description of the <see cref="StyleCollection"/> class.</p>
    ///
    /// <seealso cref="StyleCollection"/>
    /// <seealso cref="Style"/>
    /// </remarks>
    public StyleCollection getStyles() { return mStyles; }

    /// <summary>
    /// Provides access to the list formatting used in the document.
    /// </summary>
    /// <remarks>
    /// <p>For more information see the description of the <see cref="ListCollection"/> class.</p>
    ///
    /// <seealso cref="ListCollection"/>
    /// <seealso cref="List"/>
    /// <seealso cref="ListFormat"/>
    /// </remarks>
    public ListCollection getLists() { return mLists; }

    /// <summary>
    /// Returns a collection of paragraph group properties in the document.
    /// </summary>
    HtmlBlockCollection getHtmlBlockCollection() { return mHtmlBlockCollection; }

    /// <summary>
    /// Returns a collection of objects that represent contact information for authors of comments and revisions
    /// in the document.
    /// </summary>
    PersonCollectionInternal getPeople() { return mPeople; }

    /// <summary>
    /// Called during various document processing procedures when an issue is detected that might result
    /// in data or formatting fidelity loss.
    /// </summary>
    /// <remarks>
    /// </remarks>
    public IWarningCallback getWarningCallback() { return mWarningCallback; }
    public void setWarningCallback(IWarningCallback value) { mWarningCallback = value; }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    void warn(/*WarningType*/int type, /*WarningSource*/int source, String description)
    {
        if (getWarningCallback() != null)
            getWarningCallback().warning(new WarningInfo(type, source, description));
    }

    int getNextShapeId()
    {
        // WORDSNET-14012 Pre-increment have to be used to avoid duplicate shape identifiers.
        return ++mNextShapeId;
    }

    /// <summary>
    /// Get next identifier of the shape.
    /// This overload for enable testing purpose.
    /// </summary>
    /// <param name="shape">Shape which requested new identifier.</param>
    /// <returns>Identifier for shape.</returns>
    /*virtual*/ int getNextShapeId(ShapeBase shape)
    {
        return getNextShapeId();
    }

    void setNextShapeId(int value)
    {
        mNextShapeId = value;
    }

    /// <summary>
    /// Return new value for shape identifier in case when linked shapes placed
    /// in different identifier range.
    /// </summary>
    /// <param name="currentId">First shape identifier in the linked shape sequence.</param>
    /// <param name="lnkItemsCount">Count of the inked shapes.</param>
    /// <returns>Mapped identifier.</returns>
    /*virtual*/ int mapShapeToRange(int currentId, int lnkItemsCount)
    {
        int lastChainId = currentId + lnkItemsCount;

        // Textboxes from one linked chain have to be placed in one shape identifier range.
        if (!haveSameIdMap(currentId, lastChainId))
        {
            currentId = getNearestMapId(currentId);
            setNextShapeId(currentId);
            currentId = getNextShapeId();
        }

        return currentId;
    }

    /// <summary>
    /// Returns true if both given identifiers belong to the same drawing idmap.
    /// </summary>
    /// <remarks>
    /// AM. Frankly we should analyze idmaps deeper because more than one range can be allocated to drawing but
    /// lets postpone for a while.
    /// </remarks>
    static boolean haveSameIdMap(int id1, int id2)
    {
        int spidmap1 = id1 / MAP_DIVIDER;
        int spidmap2 = id2 / MAP_DIVIDER;

        return (spidmap1 == spidmap2);
    }

    /// <summary>
    /// Get nearest from right side initial value for a new range of the identifiers.
    /// </summary>
    /// <param name="id">current identifier.</param>
    /// <returns>Nearest beginning of the identifiers range.</returns>
    static int getNearestMapId(int id)
    {
        return /*unchecked*/(((id / MAP_DIVIDER) + (((id % MAP_DIVIDER) > 0) ? 1 : 0)) * MAP_DIVIDER);
    }

    int getNextDmlTextBoxId()
    {
        return mNextDmlTextBoxId++;
    }

    void resetNextAnnotationId()
    {
        mNextAnnotationId = 0;
    }

    int getNextAnnotationId()
    {
        return mNextAnnotationId++;
    }

    /// <summary>
    /// Updates NextAnnotationId to max identifier of existing comments.
    /// </summary>
    void updateAnnotationId()
    {
        for (Comment comment : (Iterable<Comment>) getChildNodes(NodeType.COMMENT, true))
            mNextAnnotationId = Math.max(mNextAnnotationId, comment.getId() + 1);
    }

    DocPr getDocPr() { return mDocPr; }

    /// <summary>
    /// Returns the collection of variables added to a document or template.
    /// </summary>
    VariableCollection getVariables() { return mVariables; }

    /// <summary>
    /// Provides access to the footnote/endnote separators defined in the document.
    /// </summary>
    public FootnoteSeparatorCollection getFootnoteSeparators() { return mFootnoteSeparators; }

    /// <summary>
    /// Gets or sets the background shape of the document. Can be <c>null</c>.
    /// </summary>
    /// <remarks>
    /// <para>Microsoft Word allows only a shape that has its <see cref="ShapeBase.ShapeType"/> property equal
    /// to <see cref="ShapeType.Rectangle"/> to be used as a background shape for a document.</para>
    ///
    /// <para>Microsoft Word supports only the fill properties of a background shape. All other properties
    /// are ignored.</para>
    ///
    /// <para>Setting this property to a non-null value will also set the <see cref="ViewOptions.DisplayBackgroundShape"/> to <c>true</c>.</para>
    ///
    /// <seealso cref="ViewOptions.DisplayBackgroundShape"/>
    /// <seealso cref="PageColor"/>
    /// </remarks>
    public Shape getBackgroundShape() { return mBackgroundShape; }
    public void setBackgroundShape(Shape value)
    {
        if (value != null)
        {
            if (value.getDocument() != this)
                throw new IllegalArgumentException("The shape was created from a different document.");

            if (value.getParentNode() != null)
                throw new IllegalArgumentException("The shape is a child of another node.");

            if (value.getShapeType() != ShapeType.RECTANGLE)
                throw new IllegalArgumentException("Only a rectangle shape can be set as a document background.");
        }

        mBackgroundShape = value;
        mDocPr.ViewOptions.setDisplayBackgroundShape((mBackgroundShape != null));
    }

    /// <summary>
    /// Use this during document loading.
    /// </summary>
    void setBackgroundShapeSafe(Shape shape)
    {
        mBackgroundShape = shape;
    }

    /// <summary>
    /// Creates a temporary document, for rendering text of Dml txShape and for Math.
    /// </summary>
    Document createTempDocument() throws Exception
    {
        // Clone source document without children, we will use this document as temporary document,
        // for layout text in shape. Do not create new document here to keep using styles and themes from source one.
        Document tmpDoc = ms.as(deepClone(false), Document.class); // Removed redundant 'this' for C++.
        if (tmpDoc == null)
            tmpDoc = new Document(DocumentCtorMode.BLANK_DOCUMENT_NODE);

        tmpDoc.ensureMinimum();

        // Fonts have to be added to RequestedFontsCache.
        tmpDoc.getFontProvider().setRequestedFontsCache(getFontProvider().getRequestedFontsCache());

        return tmpDoc;
    }

    /// <summary>
    /// Gets a temporary document. Used to render text for various DML objects.
    /// </summary>
    /// <param name="clear">Whether to remove all content.</param>
    /// <returns>The cached temporary document. Creates a new one if it doesn't exist.</returns>
    Document getTemporaryDoc(boolean clear) throws Exception
    {
        if (mTemporaryDoc == null)
            mTemporaryDoc = createTempDocument();

        if (clear)
        {
            mTemporaryDoc.removeAllChildren();
            mTemporaryDoc.ensureMinimum();
        }

        return mTemporaryDoc;
    }

    /// <summary>
    /// Gets or sets the page color of the document. This property is a simpler version of <see cref="BackgroundShape"/>.
    /// </summary>
    /// <remarks>
    /// <para>This property provides a simple way to specify a solid page color for the document.
    /// Setting this property creates and sets an appropriate <see cref="BackgroundShape"/>.</para>
    ///
    /// <para>If the page color is not set (e.g. there is no background shape in the document) returns
    /// <ms><see cref="Color.Empty"/></ms><java>a zero color</java><cpp><see cref="Color.Empty"/></cpp>.</para>
    ///
    /// <seealso cref="BackgroundShape"/>
    /// </remarks>
    public Color getPageColor() { return (getBackgroundShape() != null) ? getBackgroundShape().getFillColor() : msColor.Empty; }
    public void setPageColor(Color value)
    {
        // WORDSNET-20068 Color.Empty should remove BackgroundShape.
        if (msColor.isEmpty(value))
        {
            setBackgroundShape(null);
            return;
        }

        setBackgroundShape(new Shape(this, ShapeType.RECTANGLE));
        getBackgroundShape().setFillColor(value);

        // WORDSNET-9995 The background shape should not have a visible border.
        getBackgroundShape().getStroke().setOn(false);
    }

    /// <summary>
    /// Handles all placeholder-specific tasks for Sdts in this document.
    /// </summary>
    SdtPlaceholderManager getSdtPlaceholderManager()
    {
        if (mSdtPlaceholderManager == null)
            mSdtPlaceholderManager = new SdtPlaceholderManager(this);

        return mSdtPlaceholderManager;
    }

    /// <summary>
    /// Handles all id-related tasks for Sdts in this document.
    /// </summary>
    UniqueIdManager getSdtIdManager()
    {
        if (mSdtIdManager == null)
            mSdtIdManager = new UniqueIdManager();

        return mSdtIdManager;
    }

    /// <summary>
    /// False for normal documents.
    /// Can be true for documents that were loaded from clipboard data.
    /// When true, indicated that the last section (paragraph) break only exists for the purpose
    /// to make the document valid, but it was not in the user's selection.
    /// </summary>
    boolean isIgnoreLastSectionBreak() { return mIsIgnoreLastSectionBreak; }
    void isIgnoreLastSectionBreak(boolean value) { mIsIgnoreLastSectionBreak = value; }

    /// <summary>
    /// A cache for calculated image transparency flag.
    /// The type of a key is <see cref="int"/> (image bytes hash code), the type of a value is <see cref="bool"/>.
    /// </summary>
    HashMap<Integer, Boolean> getImageTransparencyFlagCache()
    {
        if (mImageTransparencyFlagCache == null)
            mImageTransparencyFlagCache = new HashMap<Integer, Boolean>();

        return mImageTransparencyFlagCache;
    }

    /// <summary>
    /// Helps to resolve fonts in this document.
    /// </summary>
    abstract DocumentFontProvider getFontProvider();

    /// <summary>
    /// If the document implementation has a theme, it should return it. Can return null.
    /// </summary>
    /// <dev>
    /// Renamed to GetThemeInternal() to resolve name conflict with Document.Theme property on Java.
    /// </dev>
    abstract Theme getThemeInternal();

    InternManager InternManager;

    //Tree fields
    /// <summary>
    /// A special proxy node that serves as a parent for nodes that are not part of a document tree yet.
    /// </summary>
    private Node mNullNode;
    private NodeChangingCallbackChain mNodeChangingCallbackChain;
    private int mTreeChangeCount;

    /// <summary>
    /// The identifier that will be assigned to next generated shape. Do not use this number directly when
    /// exporting, it is just a unique number, not necessarily valid from MS Word point of view.
    /// </summary>
    private int mNextShapeId;

    /// <summary>
    /// The identifier that will be assigned to next generated linked Dml textbox. Do not use this number directly when
    /// exporting, it is just a unique number, not necessarily valid from MS Word point of view.
    /// </summary>
    private int mNextDmlTextBoxId;

    /// <summary>
    /// The identifier that will be assigned to a next created comment/editableRange.
    /// Do not use this directly when exporting, it is just a unique number valid within the model.
    /// </summary>
    private int mNextAnnotationId;

    /// <summary>
    /// Handles uniqueness of Ids across SDTs in the document descendants. This applies both to Document and GlossaryDocument, as they both can host SDTs.
    /// </summary>
    private UniqueIdManager mSdtIdManager;

    /// <summary>
    /// Handles placeholders for SDTs in the document descendants. This applies both to Document and GlossaryDocument, as they both can host SDTs.
    /// </summary>
    private SdtPlaceholderManager mSdtPlaceholderManager;

    private boolean mIsIgnoreLastSectionBreak;

    // Word document fields
    private FontInfoCollection mFontInfos;
    private StyleCollection mStyles;
    private ListCollection mLists;
    private HtmlBlockCollection mHtmlBlockCollection;
    private PersonCollectionInternal mPeople;
    private DocPr mDocPr;
    private VariableCollection mVariables;
    private FootnoteSeparatorCollection mFootnoteSeparators;
    /// <summary>
    /// Can be null.
    /// </summary>
    private Shape mBackgroundShape;
    private IResourceLoadingCallback mResourceLoadingCallback;
    private IWarningCallback mWarningCallback;
    private Document mTemporaryDoc;
    protected HashMap<Integer, Boolean> mImageTransparencyFlagCache;


    private int mSuspendMappedCustomXmlUpdate;

    /// <summary>
    /// Value divides shape identifiers to different ranges.
    /// </summary>
    static final int MAP_DIVIDER = 1024;
}

