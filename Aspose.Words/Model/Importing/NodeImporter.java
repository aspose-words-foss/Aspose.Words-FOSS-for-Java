// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/09/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.SortedIntegerListGeneric;
import com.aspose.Debug;
import com.aspose.ms.System.Guid;
import com.aspose.StringUtil;
import java.util.ArrayList;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msString;
import com.aspose.IntToIntDictionary;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.DictionaryExtension;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.HashSetGeneric;
import com.aspose.ArgumentUtil;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.XmlUtilPal;
import com.aspose.StreamUtil;
import com.aspose.CaseInsensitiveHashSet;
import java.util.HashMap;
import com.aspose.IntToObjDictionary;


/// <summary>
/// Allows to efficiently perform repeated import of nodes from one document to another.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/aspose-words-document-object-model/">Aspose.Words Document Object Model (DOM)</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>Aspose.Words provides functionality for easy copying and moving fragments
/// between Microsoft Word documents. This is known as "importing nodes".
/// Before you can insert a fragment from one document into another, you need to "import" it.
/// Importing creates a deep clone of the original node, ready to be inserted into the
/// destination document.</p>
///
/// <p>The simplest way to import a node is to use the <see cref="DocumentBase.ImportNode(Node, bool)"/> method
/// provided by the <see cref="Aspose.Words.DocumentBase"/> object.</p>
///
/// <p>However, when you need to import nodes from one document to another multiple times,
/// it is better to use the <see cref="NodeImporter"/> class. The <see cref="NodeImporter"/>
/// class allows to minimize the number of styles and lists created in the destination document.</p>
///
/// <p>Copying or moving fragments from one Microsoft Word document to another presents a number
/// of technical challenges for Aspose.Words. In a Word document, styles and list formatting
/// are stored centrally, separately from the text of the document. The paragraphs
/// and runs of text merely reference the styles by internal unique identifiers.</p>
///
/// <p>The challenges arise from the fact that styles and lists are different in different documents.
/// For example, to copy a paragraph formatted with the Heading 1 style from one document to another,
/// a number of things must be taken into account: decide whether to copy the Heading 1 style from
/// the source document to the destination document, clone the paragraph, update the cloned
/// paragraph so it refers to the correct Heading 1 style in the destination document.
/// If the style had to be copied, all the styles that it references (based on style
/// and next paragraph style) should be analyzed and possibly copied too and so on.
/// Similar issues exist when copying bulleted or numbered paragraphs because Microsoft Word
/// stores list definitions separately from text.</p>
///
/// <p>The <see cref="NodeImporter"/> class is like a context, that holds the "translation tables"
/// during the import. It correctly translates between styles and lists in the source and
/// destination documents.</p>
///
/// <seealso cref="Document"/>
/// <seealso cref="DocumentBase.ImportNode(Node, bool)"/>
/// </remarks>
public class NodeImporter
{
    /// <summary>
    /// Initializes a new instance of the <see cref="NodeImporter"/> class.
    /// </summary>
    /// <param name="srcDoc">The source document.</param>
    /// <param name="dstDoc">The destination document that will be the owner of imported nodes.</param>
    /// <param name="importFormatMode">Specifies how to merge style formatting that clashes.</param>
    public NodeImporter(DocumentBase srcDoc, DocumentBase dstDoc, /*ImportFormatMode*/int importFormatMode)
    {
    	this (srcDoc, dstDoc, new ImportContext(srcDoc, dstDoc, importFormatMode));
	
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="NodeImporter"/> class.
    /// </summary>
    /// <param name="srcDoc">The source document.</param>
    /// <param name="dstDoc">The destination document that will be the owner of imported nodes.</param>
    /// <param name="importFormatMode">Specifies how to merge style formatting that clashes.</param>
    /// <param name="importFormatOptions">Specifies various options to format imported node.</param>
    public NodeImporter(DocumentBase srcDoc, DocumentBase dstDoc, /*ImportFormatMode*/int importFormatMode,
        ImportFormatOptions importFormatOptions)
    {
    	this (srcDoc, dstDoc, new ImportContext(srcDoc, dstDoc, importFormatMode, importFormatOptions));
	
    }

    NodeImporter(DocumentBase srcDoc, DocumentBase dstDoc, ImportContext context)
    {
        if (srcDoc == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "srcDoc");
        if (dstDoc == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "dstDoc");

        mContext = context;

        copyThemeNoOverride();

        // AS It's used to copy all embedded fonts from srcDoc to dstDoc. Here we can copy fonts which will not be
        // actually used in imported nodes. The main reason of doing so is two stages of importing. When we do
        // ImportNode, it's difficult to determine font that will be used in destination node because the imported
        // node is not bound to exact place in destination document. And when we do AppendChild we would visit all
        // nested nodes of appended node again. I tried these alternates but didn't managed to do it well.
        //
        // - Embedded fonts binary data is not cloned by this operation, so it don't add much overhead.
        // - It would be good to remove extra fonts on saving.
        // - It makes possible to use all fonts of source document during following editing of destination document,
        // even those which could be skipped during importing.
        dstDoc.getFontInfos().merge(srcDoc.getFontInfos());

        mFormattingDifferenceCalculator = new FormattingDifferenceCalculator(mContext);
    }

    /// <summary>
    /// <p>Imports a node from one document into another.</p>
    /// </summary>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Document.ImportNodeCommon"]/*'/>
    /// </remarks>
    /// <param name="srcNode">The node to import.</param>
    /// <param name="isImportChildren"><c>true</c> to import all child nodes recursively; otherwise, <c>false</c>.</param>
    /// <returns>The cloned, imported node. The node belongs to the destination document, but has no parent.</returns>
    public Node importNode(Node srcNode, boolean isImportChildren)
    {
        return importNode(srcNode, isImportChildren, null);
    }

    /// <summary>
    /// <p>Imports a node from one document into another.</p>
    /// </summary>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Document.ImportNodeCommon"]/*'/>
    /// </remarks>
    /// <param name="srcNode">The node to import.</param>
    /// <param name="isImportChildren"><c>true</c> to import all child nodes recursively; otherwise, <c>false</c>.</param>
    /// <param name="nodeCloningListener">The listener to the node cloning events.</param>
    /// <returns>The cloned, imported node. The node belongs to the destination document, but has no parent.</returns>
    Node importNode(Node srcNode, boolean isImportChildren, INodeCloningListener nodeCloningListener)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	mContext.setTopmostNode(srcNode);
        	mIsApplyFormattingDifference = (mContext.getImportFormatMode() == ImportFormatMode.KEEP_SOURCE_FORMATTING) &&
                                       	!mContext.getImportFormatOptions().getForceCopyStyles() &&
                                       	!canPreserveSourceStyles(srcNode);
        	mIsTopmostNodeIncluded = true;

        	SuspendMappedCustomXmlUpdateDocument suspendMappedCustomXmlUpdateDocumentResource = new SuspendMappedCustomXmlUpdateDocument(mContext.getDstDoc());
        	try /*JAVA: was using*/
        	{
            	Node node = importNodeCore(srcNode, isImportChildren, nodeCloningListener);

            	updateShapes();
            	removeGoBackBookmarks();

            	return node;
        	}
        	finally { if (suspendMappedCustomXmlUpdateDocumentResource != null) suspendMappedCustomXmlUpdateDocumentResource.dispose(); }
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Updates shapes imported during last import operation.
    /// Shape Ids must be unique in the document. This was not important before linked textboxes support has been implemented
    /// because AW forces shape Ids to be unique during saving. But textboxes Ids must be updated synchronously in
    /// order to preserve links between them.
    /// </summary>
    /// <remarks>
    /// AM. Called only after externally called import operations (is not called during recursive calls).
    /// This allows to treat externally called import as single operation i.e
    /// distinguish for example section importing which should preserves textboxes links from
    /// single textbox importing which should break links.
    ///
    /// This is intermediate solution covers several importing issue and helps to reduce corrupted document user can get.
    /// WORDSNET-5905 was risen for full support of linked textboxes.
    /// </remarks>
    private void updateShapeIds()
    {
        if (mImportedShapes.getCount() == 0)
            return;

        DocumentBase doc = mContext.getDstDoc();
        // Base index. Every imported shape will be updated to this base.
        int baseShapeId = doc.getNextShapeId();
        // Avoid to put identifiers of the linked shapes to different identifier ranges.
        baseShapeId = DocumentBase.getNearestMapId(baseShapeId);
        // Maximum updated shape Id. Actually it's new base index of this document after import will be completed.
        int maxShapeId = baseShapeId;

        for (ShapeBase shape : mImportedShapes.getValues())
        {
            shape.setId(shape.getId() + baseShapeId);
            // I don't care for a while about Ids can possible go out of range. Just care about they are unique.
            maxShapeId = Math.max(maxShapeId, shape.getId());

            if (shape.getTextboxNextShapeId() != 0)
            {
                if (mImportedShapes.get(shape.getTextboxNextShapeId()) == null)
                {
                    // This means that referred shape has not been imported during last import operation so break link.
                    shape.setTextboxNextShapeId(0);
                }
                else
                {
                    // Referred shape has been imported so update link to new id.
                    shape.setTextboxNextShapeId(shape.getTextboxNextShapeId() + baseShapeId);
                    maxShapeId = Math.max(maxShapeId, shape.getTextboxNextShapeId());
                }
            }
        }

        // Update maximum shape Id used so next import operation will use it as new base.
        mContext.getDstDoc().setNextShapeId(++maxShapeId);
    }

    /// <summary>
    /// Updates drawingMl imported during last import operation.
    /// DrawingMl textboxIds and linkedIds must be unique in the document.
    /// </summary>
    private void updateDmlTextboxIds()
    {
        // WORDSNET-13866 Textbox identifier update algorithm was changed.

        // Go through textboxes and update identifiers.
        for (ShapeBase dml : mDmlTxbxByTxbxId.getValues())
        {
            ShapePr pr = dml.getShapePr();
            DocumentBase doc = mContext.getDstDoc();

            // Try to find appropriate linked textbox chain.
            SortedIntegerListGeneric<ShapeBase> lnkItems = mDmlLinkedTxbxByLnkId.get(dml.getTextboxId());

            int nShapeId = doc.getNextShapeId(dml);
            nShapeId = (lnkItems != null) ? doc.mapShapeToRange(nShapeId, lnkItems.getCount()) : nShapeId;

            // Update shape identifier for unique value in destination document.
            pr.set(ShapeAttr.SHAPE_ID, nShapeId);
            int prevTxbxId = dml.getTextboxId();

            // When textbox has identifier of the next shape but linked textbox chain for this
            // element does not exist then reset next textbox identifier.
            if (lnkItems == null && pr.get(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID) != null)
            {
                dml.setTextboxId(0);
                pr.set(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID, 0);
            }

            // Update drawingMl TextboxId for unique value in destination document.
            if ((dml.getTextboxId() > 0) && (lnkItems != null))
                dml.setTextboxId(doc.getNextDmlTextBoxId());

            // Update linked textboxes identifiers.
            if (lnkItems != null)
            {
                updateTxBxNextShapeId(dml, lnkItems);

                // Textboxes chain was updated, remove for post processing only broken chains.
                if (prevTxbxId > 0)
                    mDmlLinkedTxbxByLnkId.remove(prevTxbxId);
            }
        }

        // Go through linked textboxes which grouped by linked textbox identifier.
        // If group has not appropriate textbox in collection then update
        // liked items as broken.
        for (SortedIntegerListGeneric<ShapeBase> lnkItems : mDmlLinkedTxbxByLnkId.getValues())
            processBrokenTxbxChain(lnkItems);
    }

    /// <summary>
    /// Process skipped linked textboxes.
    /// </summary>
    /// <param name="lnkItems">Collection of linked textboxes of one chain.</param>
    private void processBrokenTxbxChain(SortedIntegerListGeneric<ShapeBase> lnkItems)
    {
        Debug.assertTrue(lnkItems != null);

        // It looks like imported node contains separate linked textbox elements chain
        // without appropriate textbox. Remove links between such items.
        for (int i = 0; i < lnkItems.getCount(); ++i)
        {
            ShapeBase lnkTxBx = lnkItems.getByIndex(i);

            lnkTxBx.setLinkedTextboxId(0);
            lnkTxBx.setLinkedTextboxSeq(0);
            lnkTxBx.getShapePr().set(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID, 0);
            lnkTxBx.getShapePr().set(ShapeAttr.SHAPE_ID, mContext.getDstDoc().getNextShapeId());
        }
    }

    /// <summary>
    /// Updates next shape identifier for textbox and related linked textbox chain.
    /// </summary>
    /// <param name="dml">First shape in the linked textbox chain.</param>
    /// <param name="lnkItems">Collection of linked textboxes of one chain.</param>
    private void updateTxBxNextShapeId(ShapeBase dml, SortedIntegerListGeneric<ShapeBase> lnkItems)
    {
        Debug.assertTrue((dml != null) && (lnkItems != null));

        int lnkCount = lnkItems.getCount();
        int lnkTxbxId = dml.getTextboxId();
        int newNextId = mContext.getDstDoc().getNextShapeId();
        dml.getShapePr().set(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID, newNextId);

        for (int i = 0; i < lnkCount; ++i)
        {
            ShapeBase lnkTxBx = lnkItems.getByIndex(i);

            // Case when textbox sequence number is invalid.
            if (lnkTxBx.getLinkedTextboxSeq() > (i + 1))
            {
                lnkTxbxId = 0;
                lnkTxBx.setLinkedTextboxSeq(0);
                dml.getShapePr().set(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID, 0);
            }

            lnkTxBx.setLinkedTextboxId(lnkTxbxId);
            lnkTxBx.getShapePr().set(ShapeAttr.SHAPE_ID, newNextId);

            if (i < (lnkCount - 1))
            {
                newNextId = (lnkTxbxId > 0) ? mContext.getDstDoc().getNextShapeId() : 0;
                lnkTxBx.getShapePr().set(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID, newNextId);
            }
        }
    }

    /// <summary>
    /// Removes imported _GoBack bookmarks.
    /// </summary>
    private void removeGoBackBookmarks()
    {
        // Do not remove bookmarks if something goes wrong.
        if (mGoBackBookmarkStarts.size() == mGoBackBookmarkEnds.size())
        {
            for (int i = 0; i < mGoBackBookmarkStarts.size(); i++)
            {
                BookmarkStart bookmarkStart = mGoBackBookmarkStarts.get(i);
                BookmarkEnd bookmarkEnd = mGoBackBookmarkEnds.get(i);
                bookmarkStart.remove();
                bookmarkEnd.remove();
            }
        }

        mGoBackBookmarkStarts.clear();
        mGoBackBookmarkEnds.clear();
    }

    private Node importNodeCore(Node srcNode, boolean isImportChildren, INodeCloningListener nodeCloningListener) throws Exception
    {
        if (srcNode == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "srcNode");

        Node dstNode = srcNode.deepClone(false, nodeCloningListener);
        dstNode.setDocument(mContext.getDstDoc());

        // Import children of the source node if needed.
        if (isImportChildren && srcNode.isComposite())
            importAndAppendChildrenCore((CompositeNode)srcNode, (CompositeNode)dstNode, nodeCloningListener);

        importNodeWithoutChildren(srcNode, dstNode);

        // Postconditions of a correct import.
        Debug.assertTrue(dstNode.getDocument() == mContext.getDstDoc());
        Debug.assertTrue(dstNode.getParentNode() == null);

        return dstNode;
    }

    /// <summary>
    /// Imports children from the source container to the destination document and
    /// adds them to the destination container.
    /// </summary>
    void importAndAppendChildren(CompositeNode srcContainer, CompositeNode dstContainer) throws Exception
    {
        mContext.setTopmostNode(srcContainer);
        mIsApplyFormattingDifference = (mContext.getImportFormatMode() == ImportFormatMode.KEEP_SOURCE_FORMATTING) &&
                                       !mContext.getImportFormatOptions().getForceCopyStyles() &&
                                       !canPreserveSourceStyles(srcContainer);
        mIsTopmostNodeIncluded = false;

        importAndAppendChildrenCore(srcContainer, dstContainer, null);
        updateShapes();
        removeGoBackBookmarks();
    }

    /// <summary>
    /// Imports list specified in <paramref name="srcParaPr"/> and applies it to <paramref name="dstParaPr"/>.
    /// </summary>
    static void importList(ParaPr srcParaPr, ParaPr dstParaPr, ImportContext context)
    {
        int srcListId;
        // WORDSNET-27842 Always expand and import listId to direct attributes in Merger.
        if (context.getImportFormatOptions().isMerger())
        {
            srcListId = getListId(srcParaPr, context.getSrcStyles());
            if (srcListId == -1)
                return;
        }
        else
        {
            srcListId = srcParaPr.getListId();
        }

        if (srcListId == 0)
        {
            // WORDSNET-24305 Check that when there is no list defined in direct paragraph`s attributes,
            // but its style is numbered, then corresponding style's list level refers to this style.
            // Otherwise, Word does not show numbering at all and thus forced to set numbering properties
            // into the direct paragraph's attributes.
            Style dstStyle = context.getDstStyles().getByIstd(dstParaPr.getIstd(), false);
            if (dstStyle != null)
            {
                int listId = dstStyle.getParaPr().getListId();
                List dstList = context.getDstLists().getListByListId(listId);
                if (dstList == null)
                    return;

                // WORDSNET-25249 Consider inherited listLevel.
                Object levelObj = dstStyle.getParaAttr(ParaAttr.LIST_LEVEL, RevisionsView.FINAL);
                if (levelObj == null)
                    return;

                int level = (/*int*/Integer)levelObj;
                ListLevel listLevel = dstList.getListLevelOverrideAware(level);
                Style linkedStyle = listLevel.getLinkedStyle();
                if (linkedStyle == null)
                    return;

                if (linkedStyle.getIstd() != dstStyle.getIstd())
                {
                    dstParaPr.set(ParaAttr.LIST_ID, listId);
                    dstParaPr.set(ParaAttr.LIST_LEVEL, level);
                }
            }

            return;
        }

        boolean isSameDoc = (context.getDstDoc() == context.getSrcDoc());

        dstParaPr.set(ParaAttr.LIST_ID, isSameDoc ? srcListId : context.getDstLists().importList(context, srcListId));

        List srcList = context.getSrcLists().getListByListId(srcListId);
        if (srcList == null)
            return;

        // List was imported successfully and neither of the list definitions in the destination were not reused.
        if (isSameDoc || context.getImportedListDefIds().containsKey(srcList.getListDefId()))
            return;

        // Mimic MSW and preserve formatting which implemented in the source definition of the list.
        // Calculate difference between source and destination definitions and apply it for paragraph
        // to complete this task.
        ListLevel dstListLevel = context.getDstLists().getDirectListLevel(dstParaPr);
        ListLevel srcListLevel = context.getSrcLists().getDirectListLevel(srcParaPr);
        ImportParaPrSaver saver = new ImportParaPrSaver(srcListLevel.getParaPr(), dstListLevel.getParaPr());

        // Calculate formatting difference.
        ParaPr srcListLevelParaPr = AttrCollectionExtension.deepClone(srcListLevel.getParaPr());
        srcListLevelParaPr.collapse(dstListLevel.getParaPr());

        // Apply formatting to paragraph properties.
        srcListLevelParaPr.expandTo(dstParaPr, saver);
    }

    /// <summary>
    /// Imports Citation sources.
    /// </summary>
    void importCitationSources()
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	CustomXmlPartCollection srcXmlParts = mContext.getSrcDoc().fetchDocumentOrGlossaryMain().getCustomXmlParts();
        	CustomXmlPart srcSources = srcXmlParts.getCitationSources();
        	if (srcSources == null)
            	return;

        	CustomXmlPartCollection dstXmlParts = mContext.getDstDoc().fetchDocumentOrGlossaryMain().getCustomXmlParts();
        	CustomXmlPart dstSources = dstXmlParts.getCitationSources();
        	if (dstSources != null)
        	{
            	appendCitations(dstSources, srcSources);
            	return;
        	}

        	if (!isEmptyCitationCollection(srcSources))
            	dstXmlParts.add(srcSources.deepClone());
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Update identifiers of the shapes.
    /// </summary>
    private void updateShapes()
    {
        updateShapeIds();
        updateDmlTextboxIds();

        mImportedShapes.clear();
        mDmlTxbxByTxbxId.clear();
        mDmlLinkedTxbxByLnkId.clear();
    }

    /// <summary>
    /// Imports children from the source container to the destination document and
    /// adds them to the destination container.
    /// </summary>
    private void importAndAppendChildrenCore(
        CompositeNode srcContainer,
        CompositeNode dstContainer,
        INodeCloningListener nodeCloningListener) throws Exception
    {
        if (srcContainer == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "srcContainer");
        if (srcContainer.getDocument() != mContext.getSrcDoc())
            throw new IllegalArgumentException("srcContainer should belong to the source document.");

        if (dstContainer == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "dstContainer");
        if (dstContainer.getDocument() != mContext.getDstDoc())
            throw new IllegalArgumentException("dstContainer should belong to the destination document.");

        for (Node child = srcContainer.getFirstChild(); child != null; child = child.getNextSibling())
        {
            dstContainer.appendChild(importNodeCore(child, true, nodeCloningListener));
        }
    }

    /// <summary>
    /// <para>Performs all operations needed to import a node (excluding its children).</para>
    /// </summary>
    private void importNodeWithoutChildren(Node srcNode, Node dstNode) throws Exception
    {
        if (srcNode instanceof ShapeBase)
        {
            importShapeBase((ShapeBase)srcNode, (ShapeBase)dstNode);
        }
        else if (srcNode instanceof IInline)
        {
            mFormattingDifferenceCalculator.visitNode(srcNode);
            importIInline((IInline)srcNode, (IInline)dstNode);
        }
        else if (srcNode instanceof Paragraph)
        {
            importParagraph((Paragraph)srcNode, (Paragraph)dstNode);
        }
        else if (srcNode instanceof Row)
        {
            importRow(dstNode);
        }
        else if (srcNode instanceof Cell)
        {
            importCell(dstNode);
        }
        else if (srcNode instanceof StructuredDocumentTag)
        {
            importStructuredDocumentTag((StructuredDocumentTag)srcNode, (StructuredDocumentTag)dstNode);
        }
        else if ((srcNode instanceof StructuredDocumentTagRangeStart) || (srcNode instanceof StructuredDocumentTagRangeEnd))
        {
            importStructuredDocumentTagRange(srcNode, dstNode);
        }
        else if (srcNode instanceof BookmarkStart)
        {
            if(mContext.getImportFormatOptions().getRenameDuplicateBookmarks())
                updateBookmarkNameIfNeeded((BookmarkStart)srcNode, (BookmarkStart)dstNode);
        }
        else if (srcNode instanceof BookmarkEnd)
        {
            if(mContext.getImportFormatOptions().getRenameDuplicateBookmarks())
                updateBookmarkNameIfNeeded((BookmarkEnd)srcNode, (BookmarkEnd)dstNode);
        }
        if (srcNode instanceof INodeWithAnnotationId)
        {
            importINodeWithCommentId((INodeWithAnnotationId)srcNode, (INodeWithAnnotationId)dstNode);
        }
    }

    private void importShapeBase(ShapeBase srcShape, ShapeBase dstShape)
    {
        if (srcShape.getMarkupLanguage() == ShapeMarkupLanguage.DML)
        {
            importDmlNode(srcShape, dstShape);
        }
        else if (srcShape instanceof Shape)
        {
            int dstShapeId = ((Shape)dstShape).getId();

            if (mImportedShapes.get(dstShapeId) != null)
            {
                // WORDSNET-6171 Source document has non unique shape Ids.
                // This is not a problem till it's not linked textbox. In this case textbox link can be lost.
                // We should warn customer about this but I'm not sure about runtime warning.

                // WARN. Source document has non unique shape Ids. Textbox links can be lost.
            }
            else
            {
                // Collect shapes to update them after import.
                mImportedShapes.add(dstShapeId, dstShape);
            }
        }

        // WORDSNET-23458 Seems Word always makes imported ZOrders greater, than any of the destination ZOrders.
        Object zOrder = dstShape.getShapePr().get(ShapeAttr.Z_ORDER);
        if (zOrder != null)
            dstShape.setZOrder(getMaxDstZOrder() + (/*int*/Integer)zOrder);
    }

    private void importCell(Node dstNode)
    {
        if (mContext.getImportFormatMode() == ImportFormatMode.KEEP_SOURCE_FORMATTING)
        {
            // Remove all theme attributes of table cell to preserve source formatting
            // and avoid its updating from destination document themes.
            ThemeColorRemover.resetCellThemeAttrs((Cell)dstNode);
        }
        else
        {
            // Update direct color formatting of table cell using destination document themes.
            ThemeColorUpdater.update((Cell)dstNode);
        }
    }

    private void importRow(Node dstNode)
    {
        // Note, that at this point dstNode is the same as srcNode.
        Row dstRow = (Row)dstNode;
        Style tableStyle = importStyle(dstRow);
        dstRow.getTablePr().setAttr(TableAttr.ISTD, tableStyle.getIstd());

        if (mContext.getImportFormatMode() == ImportFormatMode.KEEP_SOURCE_FORMATTING)
        {
            // Reset all theme attributes of table row to preserve source formatting
            // and avoid its updating from destination document themes.
            ThemeColorRemover.resetRowThemeAttrs((Row)dstNode);
        }
        else
        {
            // Update direct color formatting of table row using destination document themes.
            ThemeColorUpdater.update((Row)dstNode);
        }
    }

    private void importDmlNode(ShapeBase srcDml, ShapeBase dstDml)
    {
        /*DmlNodeType*/int dmlType = srcDml.getDmlNode().getDmlNodeType();

        // Remove FallbackShape to generate it from scratch upon validation.
        // We need this to correctly update linked textboxes in fallback shape.
        // Graphic frame can not be placed as top level element, so does not have fallback shape.
        if (((dmlType == DmlNodeType.WORDPROCESSING_GROUP_SHAPE) || (dmlType == DmlNodeType.WORDPROCESSING_SHAPE))
          && (dstDml.getFallbackShape() != null))
            dstDml.getRunPr().getAlternateContent().setFallBack(null);

        switch (dstDml.getDmlNode().getDmlNodeType())
        {
            case DmlNodeType.WORDPROCESSING_SHAPE:
                {
                    collectTextBoxes(dstDml);
                    break;
                }
            case DmlNodeType.CHART:
            case DmlNodeType.CHART_EX:
                {
                    DmlChartSpace chartSpace = (DmlChartSpace) dstDml.getDmlNode();
                    if (mContext.getImportFormatMode() == ImportFormatMode.KEEP_SOURCE_FORMATTING)
                    {
                        if ((chartSpace.getThemeOverride() == null) && (srcDml.getDocument().getThemeInternal() != null))
                            chartSpace.setThemeOverride(srcDml.getDocument().getThemeInternal().createThemeOverride());
                    }
                    break;
                }
            default:
                {
                    // Other Dml nodes do not needed additional processing upon importing. So do nothing.
                    break;
                }
        }

        // WORDSNET-22479 Resolve theme colors. Actually, this should be done for all colors in shape,
        // but at the moment it is only done for colors of the shape fills.
        // WORDSNET-28610 Added resolving theme colors for shape Outline.
        if (mIsApplyFormattingDifference && (dstDml.getDmlShape() != null))
        {
            dstDml.getDmlShape().getFill().resolveThemeColors(srcDml.getDocumentTheme());
            dstDml.getDmlShape().getOutline().resolveThemeColors(srcDml.getDocumentTheme(), dstDml.getDmlShape().getStyle());
        }
    }

    /// <summary>
    /// Collects drawingMls to update their linked textboxes after import.
    /// </summary>
    /// <param name="dstDml">Clone of the source shape which will be imported.</param>
    private void collectTextBoxes(ShapeBase dstDml)
    {
        Debug.assertTrue(dstDml != null);

        // Current reading algorithm assigns unique identifiers for textboxes (shape ID and textbox ID),
        // so this method does not implement processing shapes with duplicate identifiers.
        if (dstDml.getLinkedTextboxId() > 0)
            collectLinkedTxbx(dstDml);

        if (dstDml.getTextboxId() > 0)
            mDmlTxbxByTxbxId.add(dstDml.getTextboxId(), dstDml);
    }

    /// <summary>
    /// Insert linked textbox to collection.
    /// </summary>
    /// <param name="lnkTxbx">Linked textbox to insert.</param>
    private void collectLinkedTxbx(ShapeBase lnkTxbx)
    {
        Debug.assertTrue(lnkTxbx != null);

        int lnkTxbxId = lnkTxbx.getLinkedTextboxId();

        if (!mDmlLinkedTxbxByLnkId.containsKey(lnkTxbxId))
            mDmlLinkedTxbxByLnkId.set(lnkTxbxId, new SortedIntegerListGeneric<ShapeBase>());

        mDmlLinkedTxbxByLnkId.get(lnkTxbxId).set(lnkTxbx.getLinkedTextboxSeq(), lnkTxbx);
    }

    /// <summary>
    /// Imports run properties of nodes which implements <see cref="IInline"/>.
    /// </summary>
    private void importIInline(IInline srcInline, IInline dstInline)
    {
        // 1. Word imports styles always using UseDestinationMode first,
        // i.e. it does not override existing styles.
        //  a) original attributes.
        RunPr srcRunPr = srcInline.getRunPr_IInline();
        RunPr dstRunPr = dstInline.getRunPr_IInline();
        importStyleUseDestinationMode(srcRunPr, dstRunPr, FontAttr.ISTD);
        //  b) revised attributes.
        if (srcInline.getRunPr_IInline().hasFormatRevision())
            importStyleUseDestinationMode(srcRunPr.getFormatRevision().getRevPr(), dstRunPr.getFormatRevision().getRevPr(), FontAttr.ISTD);

        if (mIsApplyFormattingDifference && canApplyFormattingDifference(srcInline))
            mFormattingDifferenceCalculator.apply(srcInline, dstInline);
    }

    /// <summary>
    /// Imports paragraph and run properties of <see cref="Paragraph"/>.
    /// </summary>
    private void importParagraph(Paragraph srcPara, Paragraph dstPara)
    {
        if (srcPara.isListItem() && (mContext.getImportedListIds().getCount() == 0))
            mContext.setNonReusableListDefId(getNonReusableListDefId(srcPara));

        // Word creates missed BuiltIn styles instead of importing it inside original revisions.
        boolean isStyleInOriginalRevision = (srcPara.getParaPr().hasFormatRevision() && (srcPara.getParaPr().getIstd() != 0));
        if (isStyleInOriginalRevision)
            createMissingBuiltInParagraphStyles(srcPara);

        // 1. Word imports styles and lists always using UseDestinationMode first,
        // i.e. it does not override existing styles.
        //   a) original attributes.
        ParaPr srcParaPr = srcPara.getParaPr();
        ParaPr dstParaPr = dstPara.getParaPr();
        importStyleUseDestinationMode(srcParaPr, dstParaPr, ParaAttr.ISTD);
        importList(srcParaPr, dstParaPr);
        //   b) revised attributes.
        if (srcParaPr.hasFormatRevision())
        {
            WordAttrCollection srcRevPr = srcParaPr.getFormatRevision().getRevPr();
            WordAttrCollection dstRevPr = dstParaPr.getFormatRevision().getRevPr();
            importStyleUseDestinationMode(srcRevPr, dstRevPr, ParaAttr.ISTD);
            importList((ParaPr)srcRevPr, (ParaPr)dstRevPr);
        }

        // 2. Deal with ParagraphBreakRunPr.
        //   a) original attributes.
        RunPr srcBreakRunPr = srcPara.getParagraphBreakRunPr();
        RunPr dstBreakRunPr = dstPara.getParagraphBreakRunPr();
        importStyleUseDestinationMode(srcBreakRunPr, dstBreakRunPr, FontAttr.ISTD);
        //   b) revised attributes.
        if (srcBreakRunPr.hasFormatRevision())
        {
            WordAttrCollection srcRevPr = srcBreakRunPr.getFormatRevision().getRevPr();
            WordAttrCollection dstRevPr = dstBreakRunPr.getFormatRevision().getRevPr();
            importStyleUseDestinationMode(srcRevPr, dstRevPr, FontAttr.ISTD);
        }

        // WORDSNET-13302 Word removes paragraph break properties equal to its style
        // properties upon importing with UseDestinationStyles option.
        if (mContext.getImportFormatMode() == ImportFormatMode.USE_DESTINATION_STYLES)
            dstBreakRunPr.removeEquals(srcPara.getParagraphStyle().getRunPr());

        // WORDSNET-24437 Word always sets direct relative horizontal position into a non-empty Frame.
        if (dstParaPr.hasFrameAttributes() && !dstParaPr.hasFrameRelativeHorizontalPosition())
        {
            dstParaPr.set(ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION, srcPara.fetchInheritedParaAttr(ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION, RevisionsView.ORIGINAL));
        }

        if (mIsApplyFormattingDifference && canApplyFormattingDifference(srcPara))
            mFormattingDifferenceCalculator.apply(srcPara, dstPara);
    }

    /// <summary>
    /// Imports <see cref="StructuredDocumentTag"/>.
    /// </summary>
    /// <remarks>
    /// RK We don't expand run properties here, I don't think it is needed.
    /// </remarks>
    private void importStructuredDocumentTag(StructuredDocumentTag srcSdt, StructuredDocumentTag dstSdt) throws Exception
    {
        if (srcSdt.getDocument() != dstSdt.getDocument()) // we want to try to maintain ids when importing to new document.
            dstSdt.setId(srcSdt.getId());

        if (!mContext.getImportedSdtIds().containsKey(srcSdt.getId()))
            mContext.getImportedSdtIds().add(srcSdt.getId(), dstSdt.getId());

        if (srcSdt.getContentsRunPr().getCount() != 0) // prevent importing of default styles here.
            importStyleUseDestinationMode(srcSdt.getContentsRunPr(), dstSdt.getContentsRunPr(), FontAttr.ISTD);

        if (srcSdt.getEndCharacterRunPr().getCount() != 0) // prevent importing of default styles here.
            importStyleUseDestinationMode(srcSdt.getEndCharacterRunPr(), dstSdt.getEndCharacterRunPr(), FontAttr.ISTD);

        // WORDSNET-28759 Apply formatting difference to mimic Word.
        if (mIsApplyFormattingDifference && canApplyFormattingDifference(ms.as(srcSdt.getParentNode(), Paragraph.class)))
        {
            StructuredDocumentTagToInlineAdapter srcInlineAdapter = new StructuredDocumentTagToInlineAdapter(srcSdt);
            StructuredDocumentTagToInlineAdapter dstInlineAdapter = new StructuredDocumentTagToInlineAdapter(dstSdt);
            mFormattingDifferenceCalculator.apply(srcInlineAdapter, dstInlineAdapter);
        }

        importPlaceholderIfNeeded(dstSdt, dstSdt.getPlaceholder());
        handleCustomXmlParts(dstSdt);
    }

    /// <summary>
    /// Imports range for ranged structured document tag.
    /// </summary>
    private void importStructuredDocumentTagRange(Node srcSdtRange, Node dstSdtRange) throws Exception
    {
        if (srcSdtRange.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START)
        {
            StructuredDocumentTagRangeStart srcRangeStart = (StructuredDocumentTagRangeStart)srcSdtRange;
            StructuredDocumentTagRangeStart dstRangeStart = (StructuredDocumentTagRangeStart)dstSdtRange;

            dstRangeStart.setInternalSdt((StructuredDocumentTag)srcRangeStart.getInternalSdt().deepClone(false));

            dstRangeStart.setId(dstRangeStart.getInternalSdt().getId());
            // FIX 25758. Check whether this StructuredDocumentTagRangeStart has already been imported before.
            if (mContext.getImportedSdtIds().containsKey(srcRangeStart.getId()))
                mContext.getImportedSdtIds().remove(srcRangeStart.getId());

            mContext.getImportedSdtIds().add(srcRangeStart.getId(), dstRangeStart.getId());

            handleCustomXmlParts(dstRangeStart.getInternalSdt());
        }
        else
        {
            StructuredDocumentTagRangeEnd srcRangeEnd = (StructuredDocumentTagRangeEnd)srcSdtRange;
            StructuredDocumentTagRangeEnd dstRangeEnd = (StructuredDocumentTagRangeEnd)dstSdtRange;

            int newId = mContext.getImportedSdtIds().containsKey(srcRangeEnd.getId())
                ? mContext.getImportedSdtIds().get(srcRangeEnd.getId())
                : srcRangeEnd.getId();

            dstRangeEnd.setIdInternal(newId);
        }
    }

    /// <summary>
    /// SDT placeholder is a reference to a building block. If this reference is non-empty and is not contained in the glossary
    /// of the target document, then import this building block and reassign placeholder reference in dstSdt.
    /// </summary>
    private static void importPlaceholderIfNeeded(StructuredDocumentTag dstSdt, BuildingBlock placeholderReference)
    {
        if (placeholderReference != null)
        {
            GlossaryDocument dstDocumentGlossary = getOrCreateGlossaryDocument(dstSdt.getDocument());

            if (isImportPlaceholder(dstDocumentGlossary, placeholderReference))
            {
                BuildingBlock dstBuildingBlock = (BuildingBlock)dstDocumentGlossary.importNode(placeholderReference, true);
                dstDocumentGlossary.appendChild(dstBuildingBlock);
                dstSdt.updatePlaceholderReference(true);
            }
            else
            {
                dstSdt.updatePlaceholderReference(true);
            }
        }
    }

    /// <summary>
    /// Checks that placeholder should be imported to destination glossary.
    /// </summary>
    private static boolean isImportPlaceholder(GlossaryDocument glossary, BuildingBlock placeholderReference)
    {
        // if placeholder block is not contained in the target doc glossary.
        if (placeholderReference.getDocument() == glossary)
            return false;

        if (placeholderReference.getType() == BuildingBlockType.STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT)
        {
            // Do not import if placeholder's guid already exists.
            for (BuildingBlock buildingBlock : (Iterable<BuildingBlock>) glossary.getBuildingBlocks())
                if (Guid.equals(placeholderReference.getGuidInternal(), buildingBlock.getGuidInternal()))
                    return false;

            // Reuse placeholder with predefined name.
            if (SdtPlaceholderManager.isPredefinedPlaceholderName(placeholderReference.getName()))
                return false;
        }

        return true;
    }

    /// <summary>
    /// Handles custom XML parts collection.
    /// </summary>
    private void handleCustomXmlParts(StructuredDocumentTag sdt) throws Exception
    {
        if (sdt.getXmlMapping().isEmpty() || !StringUtil.hasChars(sdt.getXmlMapping().getStoreItemId()))
            return;

        CustomXmlPartCollection dstCollection = mContext.getDstDoc().fetchDocumentOrGlossaryMain().getCustomXmlParts();
        if (containsId(dstCollection, sdt.getXmlMapping().getStoreItemId()))
            return;

        // Finding the CustomXmlParts with proper structure.
        ArrayList<CustomXmlPart> xmlParts = getXmlPartsWithMatchingXmlMapping(dstCollection, sdt.getXmlMapping());

        // Changing the StoreItemId for the only CustomXmlPart without FlatOPC inside.
        if ((xmlParts != null) && (xmlParts.size() == 1) && !containsFlatOpc(xmlParts.get(0), sdt.getXmlMapping()))
        {
            sdt.getXmlMapping().setStoreItemId(xmlParts.get(0).getId());
            return;
        }

        // Otherwise, copying the CustomXmlPart with proper structure and corresponding StoreItemId.
        CustomXmlPartCollection srcCollection = mContext.getSrcDoc().fetchDocumentOrGlossaryMain().getCustomXmlParts();
        for (CustomXmlPart srcPart : getXmlPartsWithMatchingXmlMapping(srcCollection, sdt.getXmlMapping()))
        {
            if (msObject.referenceEquals(srcPart, srcCollection.getById(sdt.getXmlMapping().getStoreItemId())))
            {
                dstCollection.add(srcPart.deepClone());
                break;
            }
        }
    }

    /// <summary>
    /// Checks that collection contain CustomXmlPart with given Id.
    /// </summary>
    private static boolean containsId(CustomXmlPartCollection collection, String id)
    {
        for (CustomXmlPart part : collection)
            if (msString.equals(part.getId(), id))
                return true;

        return false;
    }

    /// <summary>
    /// Gets non-null document glossary from this DocumentBase, creates one if needed.
    /// </summary>
    private static GlossaryDocument getOrCreateGlossaryDocument(DocumentBase doc)
    {
        GlossaryDocument documentGlossary = (doc.getNodeType() == NodeType.GLOSSARY_DOCUMENT) ? (GlossaryDocument)doc : ((Document)doc).getGlossaryDocument();
        if (documentGlossary == null)
        {
            Document targetDoc = (Document)doc;
            targetDoc.setGlossaryDocument(new GlossaryDocument());
            documentGlossary = targetDoc.getGlossaryDocument();
        }

        return documentGlossary;
    }

    /// <summary>
    /// Imports a comment for comments and comment ranges.
    /// </summary>
    private void importINodeWithCommentId(INodeWithAnnotationId src, INodeWithAnnotationId dst)
    {
        IntToIntDictionary importedCommentIds = mContext.getImportedCommentIds();
        int dstId = importedCommentIds.get(src.getIdInternal());
        if (IntToIntDictionary.isNullSubstitute(dstId))
        {
            dstId = mContext.getDstDoc().getNextAnnotationId();
            importedCommentIds.set(src.getIdInternal(), dstId);
        }
        dst.setIdInternal(dstId);

        // Comment also can have a parent annotation, so we have to update its Id.
        if (src.getParentIdInternal() != Comment.NO_PARENT)
            dst.setParentIdInternal(importedCommentIds.get(src.getParentIdInternal()));
    }

    /// <summary>
    /// Copies the theme if it is present in the source document and non present in the destination document.
    /// This helps to solve some (but not all) formatting loss problems during copying of nodes.
    /// </summary>
    private void copyThemeNoOverride()
    {
        Document srcDoc = ms.as(mContext.getSrcDoc(), Document.class);
        // If src document is not a main document, then there is no theme to copy.
        if (srcDoc == null)
            return;

        // If dst document is not a main document, there is nowhere to copy the theme to.
        Document dstDoc = ms.as(mContext.getDstDoc(), Document.class);
        if (dstDoc == null)
            return;

        if ((srcDoc.getThemeInternal() != null) && (dstDoc.getThemeInternal() == null))
            dstDoc.setThemeInternal(srcDoc.getThemeInternal().deepClone());
    }

    /// <summary>
    /// Renames bookmark start being imported if such bookmark name already exists in document.
    /// </summary>
    private void updateBookmarkNameIfNeeded(BookmarkStart srcBookmarkStart, BookmarkStart dstBookmarkStart)
    {
        // Names of bookmarks of building blocks can be the same as names of bookmarks of other parts of a document.
        // Therefore, if a bookmark is inside a building block that is being imported too, let's assume that bookmark
        // names are correct in it and do not rename them.
        if (isWithinImportingBuildingBlock(srcBookmarkStart))
            return;

        String srcBookmarkName = srcBookmarkStart.getName();

        if (getBookmarkNames().contains(srcBookmarkName))
        {
            if (GO_BACK_BOOKMARK.equals(srcBookmarkStart.getName()))
                msArrayList.add(mGoBackBookmarkStarts, dstBookmarkStart);

            String newName = getNewBookmarkName(srcBookmarkName);
            getBookmarkNames().add(newName);

            // We reset new name of bookmark if encounter bookmark start with the same name without end,
            // this looks like nested bookmarks with the same name. In Ms Word we cannot create such document,
            // only using DocumentBuilder. Anyways this is invalid situation, but we need to avoid exception.
            mBookmarkNamesTranslationTable.put(srcBookmarkName, newName);

            dstBookmarkStart.setNameInternal(newName);
        }
        else
        {
            getBookmarkNames().add(srcBookmarkName);
        }
    }

    /// <summary>
    /// Gets a flag indicating whether the specified node is within an importing building block.
    /// </summary>
    private boolean isWithinImportingBuildingBlock(Node sourceNode)
    {
        if (!(sourceNode.getDocument() instanceof GlossaryDocument))
            return false;

        BuildingBlock buildingBlock = (BuildingBlock)sourceNode.getAncestor(NodeType.BUILDING_BLOCK);
        if (buildingBlock == null)
            return false;

        return
            !mContext.getTopmostNode().isAncestorNode(buildingBlock) &&
            ((mContext.getTopmostNode() != buildingBlock) || mIsTopmostNodeIncluded);
    }

    /// <summary>
    /// Renames bookmark end being imported if corresponding start was renamed.
    /// </summary>
    private void updateBookmarkNameIfNeeded(BookmarkEnd srcBookmarkEnd, BookmarkEnd dstBookmarkEnd)
    {
        String newName = DictionaryExtension.getValueOrNull(mBookmarkNamesTranslationTable, srcBookmarkEnd.getName());
        if (newName != null)
        {
            if (GO_BACK_BOOKMARK.equals(srcBookmarkEnd.getName()))
                msArrayList.add(mGoBackBookmarkEnds, dstBookmarkEnd);

            dstBookmarkEnd.setNameInternal(newName);
            msDictionary.remove(mBookmarkNamesTranslationTable, srcBookmarkEnd.getName());
        }
    }

    /// <summary>
    /// Returns new bookmark name which is unique in destination document.
    /// </summary>
    private String getNewBookmarkName(String name)
    {
        String newName;

        for (int i = 0; /* infinite loop */; i++)
        {
            newName = msString.format("{0}_{1}", name, i);
            if (!getBookmarkNames().contains(newName))
                break;
        }

        return newName;
    }

    /// <summary>
    /// Imports style of the specified row.
    /// </summary>
    private Style importStyle(Row row)
    {
        int istd = (/*int*/Integer)row.getTablePr().fetchAttr(TableAttr.ISTD);

        Style srcStyle = mContext.getSrcStyles().fetchByIstd(istd, StyleIndex.TABLE_NORMAL);
        Style dstStyle = mContext.getDstStyles().importStyle(mContext, srcStyle);

        return dstStyle;
    }

    /// <summary>
    /// Imports style specified in <paramref name="srcAttrs"/> and sets it to the <paramref name="dstAttrs"/>.
    /// It ignores mode in <see cref="mContext"/> and replaces KeepSourceFormatting mode with the
    /// UseDestinationStyles mode during import.
    /// </summary>
    private void importStyleUseDestinationMode(AttrCollection srcAttrs, AttrCollection dstAttrs, int istdKey)
    {
        int srcIstd = (/*int*/Integer)srcAttrs.fetchAttr(istdKey);
        Style srcStyle = mContext.getSrcStyles().getByIstd(srcIstd, false);

        if ((srcStyle != null) && !StyleIndex.isNonModifiable(srcStyle))
        {
            /*ImportFormatMode*/int originalFormatMode = mContext.getImportFormatMode();
            // Force to import with UseDestinationStyles mode.
            // WORDSNET-19268 The new option is introduced that allows to copy styles in KeepSourceFormatting mode.
            // In this case we should not force UseDestinationStyles mode.
            if ((mContext.getImportFormatMode() == ImportFormatMode.KEEP_SOURCE_FORMATTING) &&
                !mContext.getImportFormatOptions().getForceCopyStyles())
            {
                mContext.setImportFormatMode(ImportFormatMode.USE_DESTINATION_STYLES);
            }

            Style dstStyle = mContext.getDstStyles().importStyle(mContext, srcStyle);

            // No need to set istd explicitly for Normal as it is topmost base style.
            // WORDSNET-21101 Should check destination style identifier instead of source one.
            if (dstStyle.getStyleIdentifier() == StyleIdentifier.NORMAL)
                dstAttrs.remove(istdKey);
            else
                dstAttrs.setAttr(istdKey, dstStyle.getIstd());

            mContext.setImportFormatMode(originalFormatMode);
        }
        else
        {
            // No need to set istd explicitly for non-modifiable styles as they are topmost base styles.
            dstAttrs.remove(istdKey);
        }
    }

    /// <summary>
    /// Imports list specified in <paramref name="srcParaPr"/> and applies it to <paramref name="dstParaPr"/> using current import context.
    /// </summary>
    private void importList(ParaPr srcParaPr, ParaPr dstParaPr)
    {
        importList(srcParaPr, dstParaPr, mContext);
    }

    /// <summary>
    /// Creates missing in destination styles paragraph BuiltIn style
    /// for both: original and revised properties.
    /// </summary>
    private void createMissingBuiltInParagraphStyles(Paragraph paragraph)
        {
        // Paragraph style from the original revision.
        Style style = paragraph.getParagraphStyle(RevisionsView.ORIGINAL);
        createMissedBuiltInStyle(style);

        // Paragraph style from the final revision.
        style = paragraph.getParagraphStyle(RevisionsView.FINAL);
        createMissedBuiltInStyle(style);
    }

    /// <summary>
    /// Creates missed built-in style in <see cref="ImportContext.DstStyles"/> collection.
    /// </summary>
    private void createMissedBuiltInStyle(Style builtinStyle)
    {
        if (!builtinStyle.getBuiltIn())
            return;

        StyleCollection dstStyles = mContext.getDstStyles();
        Style existingStyle = dstStyles.findLocaleIndependentMatch(builtinStyle);
        if (existingStyle != null)
            return;

        Style builtInStyle = dstStyles.getBuiltInStyles().getBySti(builtinStyle.getStyleIdentifier(), false);
        dstStyles.addCopy(builtInStyle);
    }

    /// <summary>
    /// Returns ListDefId that cannot be reused during importing or
    /// <see cref="long.MaxValue"/> if there is no such Id.
    /// </summary>
    private long getNonReusableListDefId(Paragraph curPara)
    {
        Debug.assertTrue(curPara != null);

        if (mContext.getImportFormatOptions().getUseExistingLists())
            return Long.MAX_VALUE;

        Paragraph prevPara = getPrevNonEmptyPara(curPara);
        if (prevPara != null)
        {
            int curParaIndent = getEffectiveParaIndent(curPara);
            int prevParaIndent = getEffectiveParaIndent(prevPara);

            // Word does not reuse list definition of very first list in the
            // importing scope of nodes, when indention of the previous not empty
            // paragraph less then indention of the first list item.
            if (prevParaIndent < curParaIndent)
                return curPara.getListFormat().getList().getListDefId();
        }

        return Long.MAX_VALUE;
    }

    /// <summary>
    /// Calculates total indention for specified paragraph.
    /// </summary>
    private static int getEffectiveParaIndent(IParaAttrSource para)
    {
        Debug.assertTrue(para != null);

        int leftIndent = (/*int*/Integer)para.fetchParaAttr(ParaAttr.LEFT_INDENT);
        int firstLineIndent = (/*int*/Integer)para.fetchParaAttr(ParaAttr.FIRST_LINE_INDENT);

        return leftIndent + firstLineIndent;
    }

    /// <summary>
    /// Returns previous paragraph that has non-empty run or any other non-
    /// cross-structure annotation node (<see cref="NodeUtil.IsCrossStructureAnnotation(Node)"/>).
    /// </summary>
    private static Paragraph getPrevNonEmptyPara(Paragraph curPara)
    {
        Node node = curPara.getPreviousSibling();
        while (node != null)
        {
            if (node.getNodeType() == NodeType.PARAGRAPH)
            {
                Paragraph para = (Paragraph)node;

                if (!isContainsOnlyEmptyRunsOrCrossAnnotation(para))
                    return para;
            }

            node = node.getPreviousSibling();
        }

        return null;
    }

    /// <summary>
    /// Returns true, if the specified <paramref name="para"/> contains
    /// only empty runs or bookmark and comment range nodes.
    /// </summary>
    private static boolean isContainsOnlyEmptyRunsOrCrossAnnotation(Paragraph para)
    {
        for (Node node : (Iterable<Node>) para.getChildNodes(NodeType.ANY, false))
        {
            if (!NodeUtil.isCrossStructureAnnotation(node) && (node.getNodeType() != NodeType.RUN))
                return false;

            if (node.getNodeType() == NodeType.RUN)
            {
                Run run = (Run)node;
                if (StringUtil.hasChars(run.getText()))
                    return false;
            }
        }

        return true;
    }

    /// <summary>
    /// Returns true when formatting difference in source and destination documents can be applied to the specified node.
    /// </summary>
    private boolean canApplyFormattingDifference(IInline inline)
    {
        return canApplyFormattingDifference(inline.getParentParagraph_IInline());
    }

    /// <summary>
    /// Returns true when formatting difference in source and destination documents can be applied to the specified node.
    /// </summary>
    private boolean canApplyFormattingDifference(Paragraph para)
    {
        if (para == null)
            return false;

        // According to Word behavior, the attributes cannot be applied when:
        // Paragraph is inside glossary,
        if (para.getDocument() instanceof GlossaryDocument)
            return false;

        Story parentStory = para.getParentStory();

        // Paragraph is not inside Body (only when importing entire document),
        // WORDSNET-19796 OR when importing entire sections as well.
        boolean isDocumentLevelImport = (mContext.getTopmostNode().getNodeType() == NodeType.DOCUMENT) || (mContext.getTopmostNode().getNodeType() == NodeType.SECTION);

        // WORDSNET-20376 Word behavior is different for header/footers and body.
        // Behavior is not to apply formatting difference for headers and footers
        // the IgnoreHeaderFooter option has been added to control that.
        // True is default, this mimics Word behavior and do not apply difference.
        // In other words, when entire document or section imported, copy source formatting depending on import option.
        boolean isHeaderFooter = (parentStory != null) && (parentStory.getStoryType() != StoryType.MAIN_TEXT);

        if (isDocumentLevelImport && isHeaderFooter && mContext.getImportFormatOptions().getIgnoreHeaderFooter())
            return false;

        Node parentNode = para.getParentNode();

        // When importing nodes from the nodes of the following types along with these nodes itself,
        // we should not apply formatting difference.
        if ((parentNode != null) && (NodeUtil.isAncestorOrSelf(parentNode, mContext.getTopmostNode())))
        {
            // Paragraph is inside Footnote,
            if (parentNode.getNodeType() == NodeType.FOOTNOTE)
                return false;

            // Paragraph is inside Comment,
            if (parentNode.getNodeType() == NodeType.COMMENT)
                return false;

            // Paragraph is inside Shape.
            // WORDSNET-18202 Ignore text boxes on option.
            if (para.isInShape() && isIgnoreParaInTextBox(para))
                return false;
        }

        return true;
    }

    /// <summary>
    /// Checks whether a paragraph should be ignored in the text box,
    /// given the <see cref = "ImportFormatOptions.IgnoreTextBoxes" /> option.
    /// </summary>
    private boolean isIgnoreParaInTextBox(Paragraph para)
    {
        if (mContext.getImportFormatOptions().getIgnoreTextBoxes())
            return true;

        Shape shape = ms.as(para.getFirstNonMarkupParentNode(), Shape.class);
        if (shape == null)
            return true;

        return !shape.hasTextbox();
    }

    /// <summary>
    /// Replaces non-ascii font names with their ascii alternates from a specified 'fontInfos'.
    /// </summary>
    private static void replaceWithAsciiFontNames(RunPr runPr, FontInfoCollection fontInfos)
    {
        replaceWithAsciiFontName(runPr, FontAttr.NAME_ASCII, fontInfos);
        replaceWithAsciiFontName(runPr, FontAttr.NAME_FAR_EAST, fontInfos);
        replaceWithAsciiFontName(runPr, FontAttr.NAME_BI, fontInfos);
        replaceWithAsciiFontName(runPr, FontAttr.NAME_OTHER, fontInfos);
    }

    /// <summary>
    /// Replaces non-ascii font name with its ascii alternate from a specified 'fontInfos'.
    /// </summary>
    private static void replaceWithAsciiFontName(RunPr runPr, int key, FontInfoCollection fontInfos)
    {
        ComplexFontName fontName = ms.as(runPr.get(key), ComplexFontName.class);
        if ((fontName == null) || fontName.isThemeFont() || StringUtil.containsOnlyAscii(fontName.getName()))
            return;

        for (FontInfo fontInfo : fontInfos)
        {
            // WORDSNET-25387 Check font alternative name is not empty.
            if ((msString.equals(fontName.getName(), fontInfo.getName())) &&
                (fontInfo.getAltName().length() != 0) && StringUtil.containsOnlyAscii(fontInfo.getAltName()))
            {
                runPr.set(key, ComplexFontName.fromName(fontInfo.getAltName()));
                break;
            }
        }
    }

    /// <summary>
    /// Returns true, if source styles can be preserved when import using KeepSourceFormatting mode.
    /// </summary>
    /// <remarks>
    /// Word tries to preserve styles on the imported nodes. It checks all used inside specified node and all its child
    /// nodes styles for their equality in source and destination documents. If at least one of the styles is different,
    /// then Word expands all importing styles.
    /// </remarks>
    private boolean canPreserveSourceStyles(Node node)
    {
        StyleCollection srcStyles = mContext.getSrcStyles();
        StyleCollection dstStyles = mContext.getDstStyles();

        // Save original defaults and clone them to prepare for comparison.
        RunPr srcDefaultRunPrSaved = srcStyles.getDefaultRunPr();
        srcStyles.setDefaultRunPr(AttrCollectionExtension.deepClone(srcStyles.getDefaultRunPr()));
        RunPr dstDefaultRunPrSaved = dstStyles.getDefaultRunPr();
        dstStyles.setDefaultRunPr(AttrCollectionExtension.deepClone(dstStyles.getDefaultRunPr()));

        Theme srcTheme = mContext.getSrcDoc().getThemeInternal();
        Theme dstTheme = mContext.getDstDoc().getThemeInternal();

        // WORDSNET-23703 Resolve themes in defaults before comparison.
        Theme.apply(srcTheme, srcStyles.getDefaultRunPr());
        Theme.apply(dstTheme, dstStyles.getDefaultRunPr());

        // WORDSNET-25032 Bring font names to Ascii strings before comparison.
        replaceWithAsciiFontNames(srcStyles.getDefaultRunPr(), mContext.getSrcDoc().getFontInfos());
        replaceWithAsciiFontNames(dstStyles.getDefaultRunPr(), mContext.getDstDoc().getFontInfos());

        try
        {
            HashSetGeneric<Integer> srcUsedStyles = getUsedStyles(node);
            for (int srcIstd : srcUsedStyles)
            {
                Style srcStyle = srcStyles.getByIstd(srcIstd, false);
                if ((srcStyle == null) || StyleIndex.isNonModifiable(srcStyle))
                    continue;

                Style dstStyle = dstStyles.findLocaleIndependentMatch(srcStyle);
                if (dstStyle != null)
                {
                    // If documents defaults are equal, then Word just checks attributes equality of all used styles.
                    // Otherwise, it also tries to check if paragraph style overrides defaults
                    // by checking its expanded attributes.
                    /*RunPrExpandFlags*/int runPrExpandFlags = RunPrExpandFlags.GLOBAL_DEFAULTS;
                    /*ParaPrExpandFlags*/int paraPrExpandFlags = ParaPrExpandFlags.GLOBAL_DEFAULTS;
                    // WORDSNET-27457 This case shows, that defaults should be considered even
                    // when they are equal, at least for paragraph styles.
                    if (srcStyle.getType() == StyleType.PARAGRAPH)
                    {
                        runPrExpandFlags |= RunPrExpandFlags.DOCUMENT_DEFAULTS;
                        paraPrExpandFlags |= ParaPrExpandFlags.DOCUMENT_DEFAULTS;
                    }

                    RunPr srcExpandedRunPr = srcStyle.getExpandedRunPr(runPrExpandFlags);
                    RunPr dstExpandedRunPr = dstStyle.getExpandedRunPr(runPrExpandFlags);

                    // WORDSNET-23703 Apply themes before comparison.
                    Theme.apply(srcTheme, srcExpandedRunPr);
                    Theme.apply(dstTheme, dstExpandedRunPr);
                    if (!srcExpandedRunPr.equals(dstExpandedRunPr, Style.COMPARISON_IGNORABLE_KEYS))
                        return false;

                    if (srcStyle.getType() != StyleType.CHARACTER)
                    {
                        ParaPr srcExpandedParaPr = srcStyle.getExpandedParaPr(paraPrExpandFlags);
                        ParaPr dstExpandedParaPr = dstStyle.getExpandedParaPr(paraPrExpandFlags);
                        if (!srcExpandedParaPr.equals(dstExpandedParaPr, Style.COMPARISON_IGNORABLE_KEYS))
                            return false;

                        // WORDSNET-19316 Check also lists formatting of the styles.
                        List srcList = srcStyle.getListInternal();
                        List dstList = dstStyle.getListInternal();

                        if (!areListsEqual(srcList, dstList))
                            return false;
                    }
                }
                else
                {
                    // Word does not preserve original styles if some of used built-in linked styles
                    // did not exist in destination collection originally.
                    if (srcStyle.getBuiltIn() && (srcStyle.getLinkedIstd() != StyleIndex.NIL))
                        return false;
                }
            }
        }
        finally
        {
            // Restore original defaults.
            srcStyles.setDefaultRunPr(srcDefaultRunPrSaved);
            dstStyles.setDefaultRunPr(dstDefaultRunPrSaved);
        }

        return true;
    }

    /// <summary>
    /// Returns true if a specified lists are equal or can be reused in a current importing context.
    /// </summary>
    private boolean areListsEqual(List srcList, List dstList)
    {
        if (ArgumentUtil.oneIsNull(srcList, dstList))
            return false;

        if (ArgumentUtil.bothAreNull(srcList, dstList))
            return true;

        // WORDSNET-19275 Compare list formatting only for list with the same ids.
        if (srcList.getListId() != dstList.getListId() && !mContext.getImportFormatOptions().getKeepSourceNumbering())
           return true;

        // First check, if a list definition can be reused.
        ListDef dstListDef = mContext.getDstLists().getListDefByListDefId(srcList.getListDef().getListDefId());
        if ((dstListDef != null) && mContext.canReuseListDefId(dstListDef.getListDefId()))
            return true;

        return srcList.equals(dstList);
    }

    /// <summary>
    /// Returns used styles.
    /// </summary>
    private static HashSetGeneric<Integer> getUsedStyles(Node node)
    {
        HashSetGeneric<Integer> usedStyleIds = new HashSetGeneric<Integer>();
        collectUsedStyles(node, usedStyleIds);

        // When importing inline node without parent paragraph,
        // we always need also Normal style.
        if (node instanceof IInline)
            usedStyleIds.add(StyleIndex.NORMAL);

        return usedStyleIds;
    }

    /// <summary>
    /// Collects Istd used in this and all child nodes.
    /// </summary>
    private static void collectUsedStyles(Node node, HashSetGeneric<Integer> usedStyleIds)
    {
        if (node.isComposite())
            for (Node childNode : (Iterable<Node>) (CompositeNode)node)
                collectUsedStyles(childNode, usedStyleIds);

        IInline inlineNode = ms.as(node, IInline.class);
        if (inlineNode != null)
            addStyle(usedStyleIds, inlineNode.getRunPr_IInline(), FontAttr.ISTD);
        else if (node.getNodeType() == NodeType.PARAGRAPH)
            addStyle(usedStyleIds, ((Paragraph)node).getParaPr(), ParaAttr.ISTD);
    }

    /// <summary>
    /// Adds original and final istds from the specified collection to the hashSet.
    /// </summary>
    private static void addStyle(HashSetGeneric<Integer> hashSet, WordAttrCollection attrs, int istdKey)
    {
        hashSet.add((/*int*/Integer)attrs.fetchAttr(istdKey, RevisionsView.ORIGINAL));
        hashSet.add((/*int*/Integer)attrs.fetchAttr(istdKey, RevisionsView.FINAL));
    }

    /// <summary>
    /// Gets custom XML parts from the specified collection that contain the XML nodes
    /// that match the specified XmlMapping.
    /// </summary>
    private static ArrayList<CustomXmlPart> getXmlPartsWithMatchingXmlMapping(
        CustomXmlPartCollection dstCollection, XmlMapping xmlMapping) throws Exception
    {
        ArrayList<CustomXmlPart> xmlParts = new ArrayList<CustomXmlPart>();
        for (CustomXmlPart xmlPart : dstCollection)
        {
            if (CustomXmlUtil.extractXmlNodes(
                xmlPart.getData(), xmlMapping.getXPath(), xmlMapping.getPrefixMappings(), null, null).size() > 0)
            {
                msArrayList.add(xmlParts, xmlPart);
            }
        }
        return xmlParts;
    }

    /// <summary>
    /// Returns true if the specified custom XML part with the specified XmlMapping contains XML in FlatOpc format.
    /// </summary>
    private static boolean containsFlatOpc(CustomXmlPart xmlPart, XmlMapping xmlMapping) throws Exception
    {
        ArrayList<org.w3c.dom.Node> xmlNodes =
            CustomXmlUtil.extractXmlNodes(xmlPart.getData(), xmlMapping.getXPath(), xmlMapping.getPrefixMappings(), null, null);
        if (xmlNodes.size() > 0)
        {
            String newContent = CustomXmlUtil.getOverallInnerText(xmlNodes);
            if (StringUtil.hasChars(newContent) && newContent.contains("<pkg:package"))
            {
                MemoryStream stream = new MemoryStream(Encoding.getUTF8().getBytes(newContent));
                try /*JAVA: was using*/
                {
                    /*LoadFormat*/int lf = FileFormatUtil.detectFileFormat(stream).getLoadFormat();
                    return ((lf != LoadFormat.UNKNOWN) && (lf != LoadFormat.TEXT));
                }
                finally { if (stream != null) stream.close(); }
            }
        }
        return false;
    }

    /// <summary>
    /// Appends source citations to the destination citations.
    /// </summary>
    private static void appendCitations(CustomXmlPart dstCitations, CustomXmlPart srcCitations) throws Exception
    {
        MemoryStream dstStream = new MemoryStream(dstCitations.getData());
        try /*JAVA: was using*/
        {
            org.w3c.dom.Document dstXml = XmlUtilPal.loadXml(dstStream, true);
            if ((dstXml.getDocumentElement() == null) ||
                (!CustomXmlPartCollection.CITATION_SOURCES_ELEMENT_NAME.equals(dstXml.getDocumentElement().getNodeName())))
                return;

            MemoryStream srcStream = new MemoryStream(srcCitations.getData());
            try /*JAVA: was using*/
            {
                org.w3c.dom.Document srcXml = XmlUtilPal.loadXml(srcStream, true);
                if ((srcXml.getDocumentElement() == null) ||
                    (!CustomXmlPartCollection.CITATION_SOURCES_ELEMENT_NAME.equals(srcXml.getDocumentElement().getNodeName())))
                    return;

                for (int i = 0; i < srcXml.getDocumentElement().getChildNodes().getLength(); i++)
                {
                    org.w3c.dom.Node srcNode = srcXml.getDocumentElement().getChildNodes().item(i);
                    org.w3c.dom.Node dstNode = dstXml.importNode(srcNode, true);
                    dstXml.getDocumentElement().appendChild(dstNode);
                }

                MemoryStream dstData = new MemoryStream();
                XmlUtilPal.saveXml(dstXml, dstData);
                dstCitations.setData(StreamUtil.copyStreamToByteArray(dstData));
            }
            finally { if (srcStream != null) srcStream.close(); }
        }
        finally { if (dstStream != null) dstStream.close(); }
    }

    /// <summary>
    /// Returns true, if a specified <see cref="CustomXmlPart"/> is empty Citation collection.
    /// </summary>
    private static boolean isEmptyCitationCollection(CustomXmlPart citations) throws Exception
    {
        if (citations == null)
            return true;

        MemoryStream stream = new MemoryStream(citations.getData());
        try /*JAVA: was using*/
        {
            org.w3c.dom.Document xmlDocument = XmlUtilPal.loadXml(stream, true);
            if ((xmlDocument.getDocumentElement() == null) ||
                (!CustomXmlPartCollection.CITATION_SOURCES_ELEMENT_NAME.equals(xmlDocument.getDocumentElement().getNodeName())))
                return true;


            return (xmlDocument.getDocumentElement().getChildNodes().getLength() == 0);
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Returns listId for a specified ParaPr.
    /// If there is no direct listId, then returns inherited one from a specified styles collection.
    /// Returns -1, if not found.
    /// </summary>
    private static int getListId(ParaPr paraPr, StyleCollection styles)
    {
        Object listId = paraPr.get(ParaAttr.LIST_ID);
        if (listId != null)
            return (/*int*/Integer)listId;

        Style style = styles.getByIstd(paraPr.getIstd(), false);
        if (style != null)
        {
            listId = style.getParaAttr(ParaAttr.LIST_ID, RevisionsView.FINAL);
            if (listId != null)
                return (/*int*/Integer)listId;
        }

        return -1;
    }

    /// <summary>
    /// Gets an underlying importing context.
    /// </summary>
    ImportContext getContext() { return mContext; }

    /// <summary>
    /// Collection of bookmark names in destination document.
    /// </summary>
    private CaseInsensitiveHashSet getBookmarkNames()
    {
        if(mBookmarkNamesCache == null)
        {
            // WORDSNET-26114 Compare case insensitive.
            mBookmarkNamesCache = new CaseInsensitiveHashSet();

            // WORDSNET-25817 Improved bookmark handling.
            HashMap<String, BookmarkEnd> bookmarkEnds = new HashMap<String, BookmarkEnd>();
            for(BookmarkEnd bookmarkEnd : (Iterable<BookmarkEnd>) mContext.getDstDoc().getChildNodes(NodeType.BOOKMARK_END, true))
                bookmarkEnds.put(bookmarkEnd.getName(), bookmarkEnd);

            for (BookmarkStart bookmarkStart : (Iterable<BookmarkStart>) mContext.getDstDoc().getChildNodes(NodeType.BOOKMARK_START, true))
            {
                // Do not add incomplete bookmarks.
                if(bookmarkEnds.containsKey(bookmarkStart.getName()))
                    mBookmarkNamesCache.add(bookmarkStart.getName());
            }
        }

        return mBookmarkNamesCache;
    }

    /// <summary>
    /// Gets maximum of the destination ZOrders.
    /// </summary>
    private int getMaxDstZOrder()
    {
        if (mMaxDstZOrder == Integer.MIN_VALUE)
        {
            mMaxDstZOrder = 0;
            NodeCollection shapes = getContext().getDstDoc().getChildNodes(new /*NodeType*/int[] { NodeType.SHAPE, NodeType.GROUP_SHAPE }, true);
            for (Node shape : (Iterable<Node>) shapes)
            {
                int zOrder = ((ShapeBase)shape).getZOrder();
                if (zOrder > mMaxDstZOrder)
                    mMaxDstZOrder = zOrder;
            }
        }

        return mMaxDstZOrder;
    }

    private /*final*/ ImportContext mContext;

    /// <summary>
    /// Collects shapes need to be updated after single import operation.
    /// </summary>
    private /*final*/ IntToObjDictionary<ShapeBase> mImportedShapes = new IntToObjDictionary<ShapeBase>();

    /// <summary>
    /// Collects drawingMls with textboxes which need to be updated after single import operation.
    /// </summary>
    private /*final*/ IntToObjDictionary<ShapeBase> mDmlTxbxByTxbxId = new IntToObjDictionary<ShapeBase>();

    /// <summary>
    /// Collects drawingMls with linked textboxes which need to be updated after single import operation.
    /// </summary>
    private /*final*/ IntToObjDictionary<SortedIntegerListGeneric<ShapeBase>> mDmlLinkedTxbxByLnkId =
        new IntToObjDictionary<SortedIntegerListGeneric<ShapeBase>>();

    private CaseInsensitiveHashSet mBookmarkNamesCache;
    private /*final*/ HashMap<String, String> mBookmarkNamesTranslationTable = new HashMap<String, String>();
    private /*final*/ ArrayList<BookmarkStart> mGoBackBookmarkStarts = new ArrayList<BookmarkStart>();
    private /*final*/ ArrayList<BookmarkEnd> mGoBackBookmarkEnds = new ArrayList<BookmarkEnd>();
    private boolean mIsTopmostNodeIncluded;

    private static final String GO_BACK_BOOKMARK = "_GoBack";

    /// <summary>
    /// True, when source styles should be expanded into direct node properties.
    /// </summary>
    private boolean mIsApplyFormattingDifference;

    private /*final*/ FormattingDifferenceCalculator mFormattingDifferenceCalculator;

    private int mMaxDstZOrder = Integer.MIN_VALUE;
}

