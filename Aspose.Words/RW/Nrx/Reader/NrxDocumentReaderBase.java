// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/09/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import java.util.ArrayList;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.HashSetGeneric;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.DictionaryExtension;
import com.aspose.SortedIntegerListGeneric;
import java.util.Map;
import com.aspose.BytesHash;
import com.aspose.HashUtil;
import com.aspose.ms.System.StringComparison;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.IntToObjDictionary;
import com.aspose.words.ref.Ref;


/// <summary>
/// Base class for reading DOCX or WordML document.
/// </summary>
abstract class NrxDocumentReaderBase implements INrxStyleIdToIstd, IVmlShapeReaderContext
{
    protected NrxDocumentReaderBase(DocumentBase doc,
        LoadOptions loadOptions,
        /*WarningSource*/int warningSource,
        OoxmlComplianceInfo complianceInfo,
        NrxStoryReaderBase storyReader,
        NrxSectPrReaderBase sectPrReader) throws Exception
    {
        Debug.assertTrue(doc != null);
        Debug.assertTrue(storyReader != null);
        Debug.assertTrue(sectPrReader != null);

        mDoc = doc;
        mCurContainer = doc;
        mMarkupResolver = new MarkupResolver(loadOptions, warningSource);
        mLoadOptions = loadOptions;
        mComplianceInfo = complianceInfo;
        mStoryReader = storyReader;
        mSectPrReader = sectPrReader;
        StoryRevisionStack = new StoryRevisionStack(loadOptions.getWarningCallback(), warningSource);
        // WORDSNET-14959. MS Word versions 2007 - 2019 get LCID from Windows registry.
        if (loadOptions.getUseSystemLcid())
            GlobalSectPr.updateDefaultsByLcid();
    }

    protected void updateZOrders()
    {
        updateZOrders(mZOrderListHeader);
        updateZOrders(mZOrderListMain);
    }

    /// <summary>
    /// Updates ZOrder of the document shapes because z-index in VML is not same as ZOrder in the model.
    /// Should be called after the document is fully read.
    /// </summary>
    private static void updateZOrders(ArrayList<ShapeBase> zOrderList)
    {
        if (zOrderList.size() == 0)
            return;

        ShapeZOrderSorter.sort(zOrderList);

        for (int i = 0; i < zOrderList.size(); i++)
        {
            ShapeBase shape = zOrderList.get(i);
            shape.setZOrder(i);
        }
    }

    /// <summary>
    /// Resolves duplicate values of the <see cref="ShapeBase.Id"/> property of VML shapes.
    /// </summary>
    /// <remarks>
    /// WORDSNET-27369 When a VML shape is read, numeric part of its string ID is parsed and set to the
    /// <see cref="ShapeBase.Id"/> property. However, some two shapes may have different string IDs with the same
    /// numeric part, for example, '_x0000_i1027' and '_x0000_s1027', so these shapes get the same IDs in the model,
    /// which can cause errors in the document. This method assigns unique numeric IDs to shapes.
    /// </remarks>
    protected void resolveDuplicateShapeIds()
    {
        HashMap<Integer, ShapeBase> idMap = new HashMap<Integer, ShapeBase>(mShapeOriginalIdsMap.size());
        ArrayList<ShapeBase> duplicateIdShapes = new ArrayList<ShapeBase>();

        for (ShapeBase shape : mAllShapes)
        {
            if (idMap.containsKey(shape.getId()))
                msArrayList.add(duplicateIdShapes, shape);
            else
                msDictionary.add(idMap, shape.getId(), shape);
        }

        for (ShapeBase duplicateIdShape : duplicateIdShapes)
        {
            setShapeUniqueId(duplicateIdShape, idMap);
            msDictionary.add(idMap, duplicateIdShape.getId(), duplicateIdShape);
        }
    }

    /// <summary>
    /// Gets an unique shape ID using the specified shape ID map and assigns it to the shape.
    /// </summary>
    private static void setShapeUniqueId(ShapeBase shape, HashMap<Integer, ShapeBase> idMap)
    {
        // Shape ID should be in some particular range to be correctly processed in the document (see the method
        // DocumentBase.HaveSameIdMap).
        int id = shape.getId() / DocumentBase.MAP_DIVIDER * DocumentBase.MAP_DIVIDER;

        while (idMap.containsKey(id))
            id++;

        shape.setId(id);
    }

    /// <summary>
    /// Updates TextboxNextShapeId with Shape.Id used in the model.
    /// andrnosk: WORDSNET-7399 Use temporary TextboxNextShapeIdRaw to get appropriate shape Id.
    /// </summary>
    protected void updateTextboxNextShapeIds()
    {
        // Collect link target to avoid shape to be multiple link target.
        HashSetGeneric<Integer> linkTargetIds = new HashSetGeneric<Integer>();

        for (ShapeBase shape : mAllShapes)
        {
            String nextShapeId = (String)shape.getShapePr().get(ShapeAttr.SYS_TEXTBOX_NEXT_SHAPE_ID_RAW);

            if (StringUtil.hasChars(nextShapeId))
            {
                // Shape refers to itself, do nothing.
                if (msString.equals(shape.getName(), nextShapeId))
                    continue;

                ShapeBase nextShape = DictionaryExtension.getValueOrNull(mShapeOriginalIdsMap, nextShapeId);
                if ((nextShape != null) && !linkTargetIds.contains(nextShape.getId()))
                {
                    shape.setShapeAttrInternal(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID, nextShape.getId());
                    if(shape.getId() != nextShape.getId())
                        linkTargetIds.add(nextShape.getId());
                }

                // If no appropriate shape found the link will be broken, i.e no NextShapeId will be written.
            }
        }
    }

    /// <summary>
    /// Calculate and set ShapeId and TextboxNextShapeId in drawingML ShapePr.
    /// Also we have to change these values in fallback shape too.
    /// </summary>
    protected void updateDmlNextShapeIds()
    {
        // WORDSNET-13866 Processing textbox identifiers algorithm was changed.
        // Assign unique identifiers and try to link shapes by index.
        DrawingMLIdValidator.processLinkedTxbxId(mDmlTextBoxes, mDmlLinkedTextboxes);

        for (SortedIntegerListGeneric<ShapeBase> lnkItems : (Iterable<SortedIntegerListGeneric<ShapeBase>>) mDmlLinkedTextboxes.values())
        {
            // If linked textboxes collection exist then it is mean that collection
            // contains at least one value, so take it.
            Shape firstLnk = (Shape)lnkItems.getByIndex(0);
            int prevTxbxId = firstLnk.getLinkedTextboxId();

            ShapeBase txbx = mDmlTextBoxes.get(prevTxbxId);
            if (txbx == null)
               processBrokenTxbxChain(lnkItems, prevTxbxId);
        }
    }

    /// <summary>
    /// Updates shapes with connector rules.
    /// </summary>
    protected void updateConnectors()
    {
        for (Map.Entry<String, ConnectorRule> entry : ConnectorRules.entrySet())
        {
            String idref = entry.getKey();
            ShapeBase ruleShape = DictionaryExtension.getValueOrNull(mShapeOriginalIdsMap, idref.substring(1));

            // Shape is connector, assign rule to it.
            if (ruleShape != null)
            {
                ConnectorRule rule = entry.getValue();

                // Rule can be defined for shape names so update them as well.
                ShapeBase shapeA = DictionaryExtension.getValueOrNull(mShapeOriginalIdsMap, rule.ShapeAIdRaw.substring(1));
                if (shapeA != null)
                    rule.ShapeAId = shapeA.getId();

                ShapeBase shapeB = DictionaryExtension.getValueOrNull(mShapeOriginalIdsMap, rule.ShapeBIdRaw.substring(1));
                if (shapeB != null)
                    rule.ShapeBId = shapeB.getId();

                ruleShape.getShapePr().setAttr(ShapeAttr.CONNECTOR_RULE, entry.getValue());
            }
        }
    }

    /// <summary>
    /// Resolves stored ID of Click and Type and default table styles and writes them into document properties.
    /// </summary>
    protected void resolveDocPrStyles()
    {
        if (StringUtil.hasChars(ClickAndTypeStyleId))
        {
            int istd = resolveStyleIdToIstd(ClickAndTypeStyleId, -1);
            if (istd >= 0)
                getDocument().getDocPr().ClickTypeParaStyleIstd = istd;
        }
        if (StringUtil.hasChars(DefaultTableStyleId))
        {
            int istd = resolveStyleIdToIstd(DefaultTableStyleId, -1);
            if (istd >= 0)
                getDocument().getDocPr().DefaultTableStyleIstd = istd;
        }
    }

    /// <summary>
    /// Try to relate between linked textboxes chain and imported textboxes by index.
    /// </summary>
    /// <param name="lnkItems">Collection of linked textboxes of one chain.</param>
    /// <param name="lnkTxbxId">Linked textbox identifier for passed items.</param>
    private void processBrokenTxbxChain(SortedIntegerListGeneric<ShapeBase> lnkItems, int lnkTxbxId)
    {
        Debug.assertTrue(lnkItems != null);

        if (mDmlTxbxByIndex.size() > lnkTxbxId)
        {
            Shape txbx = mDmlTxbxByIndex.get(lnkTxbxId);
            // Identifiers were not changed for current shape, so do it.
            DrawingMLIdValidator.updateTxbxId(txbx, lnkItems, getDocument());
        }
    }

    /// <summary>
    /// If the same binary data is already in the cache, returns the object from the cache.
    /// If this binary data is unique, adds it to the cache and returns it.
    /// </summary>
    byte[] getNonDuplicatedBytes(byte[] binData)
    {
        BytesHash guid = HashUtil.getSHA512Hash(binData);
        byte[] cachedBinData = DictionaryExtension.getValueOrNull(mImageBytesByGuidTable, guid);
        if (cachedBinData == null)
        {
            cachedBinData = binData;
            msDictionary.add(mImageBytesByGuidTable, guid, cachedBinData);
        }

        return cachedBinData;
    }

    /// <summary>
    /// Adds new node to the current container and makes new container current
    /// so further nodes will be added as children of it.
    /// Use this method instead of adding nodes to the model directly.
    /// </summary>
    void addAndPushContainer(CompositeNode newContainer)
    {
        mCurContainer.appendChildForLoad(newContainer);
        mCurContainer = newContainer;
    }

    /// <summary>
    /// Restores previous container.
    /// </summary>
    void popContainer(/*NodeType*/int expectedNodeType)
    {
        if (mCurContainer.getNodeType() != expectedNodeType)
            throw new IllegalStateException(msString.format("End of node is out of sync. Expected {0} but got {1}.", expectedNodeType, mCurContainer.getNodeType()));

        // WORDSNET-19626 the problem occurred because we lost inline content at the end of the document
        // if inline content is direct child of body.
        // Fixed by adding this content to the last paragraph of Body once we finished reading Body.
        if ((mCurContainer.getNodeType() == NodeType.BODY) &&
            ((mPendingInlines.size() > 0) || (mPendingCrossStructureAnnotations.size() > 0)))
        {
            ensureBodyEnd((Body)mCurContainer);
        }

        mCurContainer = mCurContainer.getParentNode();
    }

    /// <summary>
    /// Ensures that the body ends with a paragraph. If not, adds an empty one and flushes the pending inline
    /// content to it.
    /// </summary>
    void ensureBodyEnd(Body body)
    {
        Node lastComposite = body.getLastNonAnnotationChild();
        if (lastComposite instanceof Paragraph && (mPendingInlines.size() == 0) && (mPendingCrossStructureAnnotations.size() == 0))
            return;

        addAndPushContainer(new Paragraph(body.getDocument()));

        flushPendingInlines();
        flushPendingCrossStructureAnnotations();

        popContainer(NodeType.PARAGRAPH);
    }

    /// <summary>
    /// You must call this method when adding a bookmark or a comment start/end to the model.
    /// Such nodes are "cross-structure annotation markers" according to OOXML and can occur
    /// at the inline level, story level and also inside table or row elements, but in AW model
    /// they can only exist inside a paragraph or Office Math at the moment.
    /// This method queues all such markers so they can be added when the next paragraph starts.
    /// </summary>
    void addCrossStructureAnnotation(Node node)
    {
        if (mCurContainer.canInsert(node))
        {
            addChild(node);

            // A block level SDT may be started/ended with an annotation, not only with a paragraph.
            if (node.getNodeLevel() != NodeLevel.INLINE)
            {
                flushPendingMarkup(node);

                getXmlReader().setPossibleSdtEnd(node);
            }
        }
        else
        {
            // WORDSNET-16477 If the node is a bookmark that is displaced by a previous SDT, move it to real position
            // inside the SDT in disabled block bookmarks mode.
            if (!insertAnnotationDisplacedByPreviousSdt(node))
                msArrayList.add(mPendingCrossStructureAnnotations, node);
        }
    }

    /// <summary>
    /// Flushes accumulated annotations.
    /// </summary>
    void flushPendingCrossStructureAnnotations()
    {

        if (mPendingCrossStructureAnnotations.size() == 0)
            return;

        // If current container is paragraph we can flush annotations there,
        // otherwise we try to get a last paragraph inside current container to flush.
        Paragraph para = (mCurContainer.getNodeType() == NodeType.PARAGRAPH)
                             ? (Paragraph)mCurContainer
                             : (Paragraph)mCurContainer.getChild(NodeType.PARAGRAPH, -1, false);

        flushPendingCrossStructureAnnotationsCore(para);
    }

    /// <summary>
    /// Flushes accumulated annotations into the specified <paramref name="para"/>.
    /// </summary>
    private void flushPendingCrossStructureAnnotationsCore(Paragraph para)
    {
        // andrnosk: WORDSNET-5758 We should make sure that there is paragraph
        // where we can flush pending inline content.
        if (para == null)
        {
            para = new Paragraph(getDocument());
            mCurContainer.appendChild(para);
        }

        for (Node node : mPendingCrossStructureAnnotations)
        {
            resetDisplacedByIfMovedIntoSdt(node);

            para.appendChildForLoad(node);
        }

        mPendingCrossStructureAnnotations.clear();
    }

    /// <summary>
    /// Resets value of the DisplacedBy property of the specified annotation node if the value becomes wrong after
    /// moving the node to inline level.
    /// </summary>
    private void resetDisplacedByIfMovedIntoSdt(Node annotationNode)
    {
        // Resetting is needed:
        // 1) if an annotation with DisplacedBy == DisplacedByType.Next that is located before its SDT is moved
        // into the SDT;
        // 2) if an annotation with DisplacedBy == DisplacedByType.Prev that is direct child of its SDT is moved
        // to inline level.
        // Some cases are processed by FlashDisplacedCrossStructureAnnotationsOnSdtEnd and
        // InsertBookmarkDisplacedByPreviousSdt.

        IDisplaceableByCustomXml displaceableAnnotationNode = ms.as(annotationNode, IDisplaceableByCustomXml.class);

        if (displaceableAnnotationNode == null || !mMarkupResolver.isInStartingMarkup())
            return;

        displaceableAnnotationNode.setDisplacedByCustomXml(DisplacedByType.UNSPECIFIED);
    }

    /// <summary>
    /// Appends inline level node to pending list to be flushed at first appropriate container.
    /// </summary>
    void addPendingInline(Node inline, Node ancestor)
    {
        if (ancestor == null)
            ancestor = mCurContainer;

        Node story = NodeUtil.isStoryNodeType(ancestor) ? ancestor : ancestor.getStoryAncestor(NodeType.ANY);
        InlineInfo info = new InlineInfo(inline, story);

        msArrayList.add(mPendingInlines, info);
    }

    /// <summary>
    /// Appends block level node to pending list to be flushed at the specified container or a story.
    /// </summary>
    void addPendingBlock(Node node, CompositeNode pendingBlockDestination)
    {
        // The pending blocks are block nodes that are unexpectedly located within another block node in the document
        // being read. pendingBlockDestination is expected to be a correct parent for the pending blocks, to which
        // the pending blocks will be appended at the appropriate moment.
        // Currently we expect that there can be only one nesting level of such incorrectly defined block nodes.
        Debug.assertTrue((mPendingBlockDestination == null) || (mPendingBlockDestination == pendingBlockDestination));
        mPendingBlockDestination = pendingBlockDestination;

        msArrayList.add(mPendingBlocks, node);
    }

    /// <summary>
    /// Appends paragraph to the collection which will be flushed at first appropriate container.
    /// </summary>
    void addPendingParagraph(Paragraph para)
    {
        msArrayList.add(mPendingParagraphs, para);
    }

    /// <summary>
    /// Flushes pending paragraphs nodes to specified container.
    /// </summary>
    /// <param name="container">Container to flush.</param>
    void flushPendingParagraphs(CompositeNode container)
    {
        if ((container == null) ||
            (mPendingParagraphs.size() == 0) ||
            ((container.getNodeType() != NodeType.ROW) && (container.getNodeType() != NodeType.CELL)))
        {
            return;
        }

        Cell cellToFlush = (container.getNodeType() == NodeType.CELL) ? (Cell)container : new Cell(container.getDocument());

        for (Paragraph para : mPendingParagraphs)
            cellToFlush.prependChild(para);

        if (cellToFlush.getParentNode() == null)
            container.appendChild(cellToFlush);

        mPendingParagraphs.clear();
    }

    /// <summary>
    /// Flushes pending inline level nodes to current paragraph.
    /// </summary>
    void flushPendingInlines()
    {
        Paragraph para = ms.as(mCurContainer, Paragraph.class);

        if (para == null)
            return;

        // Return if the current container is a temporary paragraph.
        Node story = para.getStoryAncestor(NodeType.ANY);
        if (story == null)
            return;

        // WORDSNET-22783 Flush only inlines that can be inserted into current paragraph.
        for (int i = 0; i < mPendingInlines.size(); /* updated within loop */)
        {
            Node node = mPendingInlines.get(i).getInline();
            if ((mPendingInlines.get(i).getStory() == story) && para.canInsert(node))
            {
                para.appendChild(node);
                mPendingInlines.remove(i);
            }
            else
                i++;
        }
    }

    /// <summary>
    /// Flushes pending block level nodes to the stored destination node or to the current story.
    /// </summary>
    void flushPendingBlocks()
    {
        if (mPendingBlocks.size() == 0)
            return;

        if ((mCurContainer != mPendingBlockDestination) && !(mCurContainer instanceof Story))
            return;

        Paragraph lastPara = (Paragraph)mCurContainer.getChild(NodeType.PARAGRAPH, -1, false);

        // It seems that Word inserts content of first paragraph into last paragraph and copy formatting.
        if ((lastPara != null) && (((Node)mPendingBlocks.get(0))).getNodeType() == NodeType.PARAGRAPH)
        {
            Paragraph pendingPara = (Paragraph)mPendingBlocks.get(0);
            while (pendingPara.hasChildNodes())
                lastPara.appendChild(pendingPara.getFirstChild());

            pendingPara.getParaPr().expandTo(lastPara.getParaPr());
            pendingPara.getParagraphBreakRunPr().expandTo(lastPara.getParagraphBreakRunPr());
            mPendingBlocks.remove(0);
        }

        // Other paragraphs is just appended.
        for (Node node : mPendingBlocks)
            mCurContainer.appendChild(node);

        mPendingBlocks.clear();
        mPendingBlockDestination = null;
    }

    /// <summary>
    /// Inserts header/footer into section. If header/footer of this type exists then merges them together.
    /// </summary>
    void insertHeaderFooter(Section section, HeaderFooter headerFooter)
    {
        // andrnosk: WORDSNET-5307 There are several header/footer of the same type,
        // we need to concatenate them like MS Word does.
        if (section.getHeadersFooters().getByHeaderFooterType(headerFooter.getHeaderFooterType()) == null)
        {
            // Insert the new header footer only if the section does not have a header footer of the same type already.
            insertChildBefore(section, headerFooter, section.getBody());
        }
        else
        {
            // Get existing HeaderFooter of the same type.
            HeaderFooter existingHeaderFooter = section.getHeadersFooters().getByHeaderFooterType(headerFooter.getHeaderFooterType());

            // Add empty paragraph like MS Word does.
            Paragraph paragraph = new Paragraph(existingHeaderFooter.getDocument());
            paragraph.getParaPr().set(ParaAttr.ISTD, section.getDocument().getStyles().getBySti(StyleIdentifier.NORMAL, true).getIstd());

            // WORDSNET-24181 Insert empty paragraph before a first child (not a paragraph due to it may be null).
            existingHeaderFooter.insertBefore(paragraph, existingHeaderFooter.getFirstChild());

            // Insert nodes of current HeaderFooter, before nodes of the exiting HeaderFooter of the same type.
            while (headerFooter.hasChildNodes())
                existingHeaderFooter.insertBefore(headerFooter.getLastChild(), existingHeaderFooter.getFirstChild());
        }
    }

    /// <summary>
    /// Adds a node to the current container.
    /// Use this method instead of adding nodes to the model directly.
    /// </summary>
    void addChild(Node node)
    {
        insertChildAfter(mCurContainer, node, mCurContainer.getLastChild());
    }

    /// <summary>
    /// Inserts a node before the reference specified node.
    /// Use this method instead of adding nodes to the model directly.
    /// </summary>
    void insertChildBefore(CompositeNode parent, Node newChild, Node refChild)
    {
        insertChildCore(parent, newChild, refChild, false);
    }

    /// <summary>
    /// Inserts a node after the reference node.
    /// Use this method instead of adding nodes to the model directly.
    /// </summary>
    void insertChildAfter(CompositeNode parent, Node newChild, Node refChild)
    {
        insertChildCore(parent, newChild, refChild, true);
    }

    private void insertChildCore(CompositeNode parent, Node newChild, Node refChild, boolean isAfter)
    {
        // Add the new node to the tree.
        if (isAfter)
        {
            // RK This is a speed optimization. Normally during load, we should call
            // AppendChildForLoad due to speed, but when we have to insert in some other place,
            // we have to use normal (slow) insert methods.
            if (refChild == parent.getLastChild())
                parent.appendChildForLoad(newChild);
            else
                parent.insertAfter(newChild, refChild);
        }
        else
        {
            // RK This is quite slow, but not used often. Only for synthesized field nodes.
            parent.insertBefore(newChild, refChild);
        }

        // Apply any current delete or insert revision.
        IInline inline = ms.as(newChild, IInline.class);
        if (inline != null)
        {
            RunPr runPr = inline.getRunPr_IInline();

            StoryRevisionStack.apply(runPr, newChild);
        }
    }

    /// <summary>
    /// INrxStyleIdToIstd.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public int resolveStyleIdToIstd(Object styleId, int defaultIstd)
    {
        return DictionaryExtension.getValueOrDefault(mStyleIdToIstd, styleId, defaultIstd);
    }

    /// <summary>
    /// INrxStyleIdToIstd.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void addStyleIdToIstdMapping(Object styleId, int istd)
    {
        mStyleIdToIstd.put(styleId, istd);
    }

    /// <summary>
    /// IVmlShapeReaderContext
    /// The document object that we read into.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public DocumentBase getDocument() { return mDoc; }

    /// <summary>
    /// IVmlShapeReaderContext
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public abstract NrxXmlReader getXmlReader();

    public 
    int /*IVmlShapeReaderContext.*/getGroupNestingLevel() { return mGroupNestingLevel; }; public void /*IVmlShapeReaderContext.*/setGroupNestingLevel(int value) { mGroupNestingLevel = value; };

    private int /*IVmlShapeReaderContext.*/ mGroupNestingLevel;

    /// <summary>
    /// IVmlShapeReaderContext
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public abstract IEmbeddedObject getEmbeddedObject(String id) throws Exception;

    /// <summary>
    /// IVmlShapeReaderContext
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public abstract byte[] getBinData(String src) throws Exception;

    /// <summary>
    /// IVmlShapeReaderContext
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public abstract String getRelationshipTarget(String relId);

    /// <summary>
    /// IVmlShapeReaderContext
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public abstract boolean isExternalImage(String src);

    /// <summary>
    /// IVmlShapeReaderContext
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void addToZOrderList(ShapeBase shape)
    {
        if (mIsInHeaderFooter)
            msArrayList.add(mZOrderListHeader, shape);
        else
            msArrayList.add(mZOrderListMain, shape);
    }

    public void /*IVmlShapeReaderContext.*/addToShapeMap(String shapeId, ShapeBase shape)
    {
        mShapeOriginalIdsMap.put(shapeId, shape);

        // Collect all shapes in order to iterate through them regardless duplicate shape names.
        msArrayList.add(mAllShapes, shape);
    }

    void addToDmlTextboxesMap(ShapeBase dml)
    {
        if (dml == null)
            return;

        if(dml.getDmlNode().getDmlNodeType() != DmlNodeType.WORDPROCESSING_SHAPE)
            return;

        // Some textbox does not contain "TextboxId".
        Shape shape = (Shape)dml;
        if ((shape.getTextBox() != null) && (dml.getLinkedTextboxId() == 0))
            mDmlTxbxByIndex.put(mDmlTxbxByIndex.size(), shape);

        if (dml.getTextboxId() > 0)
            mDmlTextBoxes.set(dml.getTextboxId(), dml);

        if (dml.getLinkedTextboxId() > 0)
        {
            if (!mDmlLinkedTextboxes.containsKey(dml.getLinkedTextboxId()))
                mDmlLinkedTextboxes.put(dml.getLinkedTextboxId(), new SortedIntegerListGeneric<ShapeBase>());

            SortedIntegerListGeneric<ShapeBase> linkedTextboxes = DictionaryExtension.getValueOrNull(mDmlLinkedTextboxes, dml.getLinkedTextboxId());
            linkedTextboxes.set(dml.getLinkedTextboxSeq(), dml);
        }
    }

    public ShapeBase /*IVmlShapeReaderContext.*/getShapeById(String shapeId)
    {
        return DictionaryExtension.getValueOrNull(mShapeOriginalIdsMap, shapeId);
    }

    public void /*IVmlShapeReaderContext.*/addShapeType(String id, ShapePr shapePr)
    {
        if(StringUtil.hasChars(id))
            mShapeTemplates.put(id, shapePr);
    }

    public ShapePr /*IVmlShapeReaderContext.*/getShapeTypePr(String id)
    {
        if (msString.startsWith(id, "#", StringComparison.ORDINAL))
            id = id.substring(1);

        return (StringUtil.hasChars(id) && mShapeTemplates.containsKey(id))
            ? mShapeTemplates.get(id)
            : null;
    }

    public void /*IVmlShapeReaderContext.*/markMissingSource(Shape shape, String src)
    {
        msDictionary.add(mMissingShapeSources, shape, src);
    }

    public String /*IVmlShapeReaderContext.*/getMissingSource(Shape shape)
    {
        String src = null;
        Ref<String> referenceToSrc = new Ref<String>(src);
        msDictionary.tryGetValue(mMissingShapeSources, shape, /*out*/ referenceToSrc);
        src = referenceToSrc.get();
        return src;
    }

    /// <summary>
    /// IVmlShapeReaderContext
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public abstract void readBinData() throws Exception;

    /// <summary>
    /// IVmlShapeReaderContext
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public abstract boolean readTextboxContent(ShapeBase shape) throws Exception;

    /// <summary>
    /// Needed for WML only.
    /// </summary>
    abstract boolean isSmartTag();

    abstract boolean isCustomXmlTag();

    /// <summary>
    /// Needed for DOCX only.
    /// Gets next not-used footnote or endnote by an ID. Returns null if the node is not found. Raises an exception
    /// if all footnotes with the specified ID are already inserted into a document. Allows correctly process
    /// documents that has several footnotes with same ID: next not-used footnote/endnote is returned.
    /// </summary>
    abstract Footnote getFootnoteById(/*FootnoteType*/int footnoteType, int id);

    /// <summary>
    /// Needed for DOCX only.
    /// Called when each comment is read from the comments part is read.
    /// The implementation needs to add these to a hashtable to later insert into the document
    /// in the appropriate places.
    /// </summary>
    abstract void addComment(Comment comment);

    /// <summary>
    /// Needed for DOCX only.
    /// Gets a comment by an id. Returns null if the node is not found.
    /// </summary>
    abstract Comment getCommentById(int id);

    abstract boolean isDocx();

    CompositeNode getCurContainer() { return mCurContainer; }
    void setCurContainer(CompositeNode value) { mCurContainer = value; }

    void isInHeaderFooter(boolean value) { mIsInHeaderFooter = value; }

    /// <summary>
    /// Gets dictionary used for getting bookmark name by its id.
    /// AW model does not deal with ids, bookmark start/end nodes are identified by name.
    /// In WordML bookmark start elements have both 'w:name' and 'aml:id' attributes set.
    /// Bookmark end elements have no 'w:name' attribute, only 'aml:id'.
    /// So we need to memorize (id, name) pairs when reading bookmark start elements
    /// and use this information later to retrieve names for bookmark end elements.
    /// </summary>
    Map<Integer, String> getIdToBookmarkName() { return mIdToBookmarkName; }

    Map<Integer, ListDef> getAbstractNumIdToListDef() { return mAbstractNumIdToListDef; }

    /// <summary>
    /// Indicates that we are currently reading AlternateContent section.
    /// </summary>
    boolean IsInAlternateContent = false;

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    void warn(/*WarningType*/int warningType, /*WarningSource*/int source, String description)
    {
        if (mLoadOptions.getWarningCallback() != null)
            mLoadOptions.getWarningCallback().warning(new WarningInfo(warningType, source, description));
    }

    /// <summary>
    /// Logs a "UnexpectedContent" warning to the user-provided warning callback.
    /// </summary>
    void warnUnexpected(String description)
    {
        warn(WarningType.UNEXPECTED_CONTENT, WarningSource.NRX, description);
    }

    void markupStart(StructuredDocumentTag sdt)
    {
        if (sdt.getLevel() == MarkupLevel.INLINE)
            addAndPushContainer(sdt);

        mMarkupResolver.pendingStart(sdt);
    }

    void flushPendingMarkup(Node node)
    {
        mMarkupResolver.flushPendingStarts(node);
    }

    void markupEnd(StructuredDocumentTag sdt, Node node)
    {
        if (sdt.getLevel() == MarkupLevel.INLINE)
            popContainer(sdt.getNodeType());

        mMarkupResolver.processEnd(sdt, node);

        if (sdt.getLevel() != MarkupLevel.INLINE)
            flushDisplacedCrossStructureAnnotationsOnSdtEnd(sdt);
    }

    /// <summary>
    /// Flushes pending annotations, which are children of their displaced SDT, and which DisplacedBy property has
    /// the value <see cref="DisplacedByType.Next"/>, into the last paragraph of the read displaced SDT.
    /// </summary>
    /// <remarks>
    /// Value of the DisplacedBy property is preserved since a bookmark node is in correct position for displacing.
    /// </remarks>
    private void flushDisplacedCrossStructureAnnotationsOnSdtEnd(StructuredDocumentTag sdt)
    {
        // Collect annotations to flush.
        ArrayList<Node> displacedAnnotations = new ArrayList<Node>();
        for (int i = 0; i < mPendingCrossStructureAnnotations.size(); i++)
        {
            Node node = mPendingCrossStructureAnnotations.get(i);

            IDisplaceableByCustomXml displaceableNode = ms.as(node, IDisplaceableByCustomXml.class);
            if ((displaceableNode != null) &&
                (displaceableNode.getDisplacedByCustomXml() == DisplacedByType.NEXT))
                msArrayList.add(displacedAnnotations, node);
        }

        if (displacedAnnotations.size() == 0)
            return;

        // Get paragraph (last) to flush to.
        CompositeNode composite = getLastParagraphOfSdt(sdt);

        // Flush.
        if (composite != null)
        {
            for (Node node : displacedAnnotations)
            {
                composite.appendChild(node);
                mPendingCrossStructureAnnotations.remove(node);
            }
        }
    }

    /// <summary>
    /// Gets last paragraph of the specified pending structured document tag.
    /// </summary>
    private CompositeNode getLastParagraphOfSdt(IMarkupNode sdt)
    {
        ArrayList<Node> sdtChildren = mMarkupResolver.getReadMarkupRange(sdt);
        CompositeNode composite = null;
        for (int i = sdtChildren.size() - 1; i >= 0; i--)
        {
            if (sdtChildren.get(i).isComposite())
            {
                composite = (CompositeNode)sdtChildren.get(i);
                while ((composite != null) && (composite.getNodeType() != NodeType.PARAGRAPH))
                    composite = composite.getLastNonMarkupCompositeDescendant();
                break;
            }
        }

        return composite;
    }

    /// <summary>
    /// Moves pending annotation node, which DisplacedBy property has <see cref="DisplacedByType.Prev"/> value, into
    /// a sibling structured document tag if it is present.
    /// </summary>
    private boolean insertAnnotationDisplacedByPreviousSdt(Node node)
    {
        IDisplaceableByCustomXml displaceableNode = ms.as(node, IDisplaceableByCustomXml.class);
        if ((displaceableNode == null) ||
            (displaceableNode.getDisplacedByCustomXml() != DisplacedByType.PREV) ||
            mMarkupResolver.isInStartingMarkup())
            return false;

        if ((getCurContainer().getLastChild() == null) || (getCurContainer().getLastChild() != mMarkupResolver.getEndNodeOfLastMarkup()))
            return false;

        CompositeNode composite = getLastParagraphOfSdt(mMarkupResolver.getLastMarkup());
        if (composite != null)
        {
            composite.appendChild(node);
            // Need to reset DisplacedBy since node place is incorrect for displacing.
            displaceableNode.setDisplacedByCustomXml(DisplacedByType.UNSPECIFIED);
            return true;
        }

        return false;
    }

    void resolveMarkup() throws Exception
    {
        mMarkupResolver.resolve();
    }

    /// <summary>
    /// Reads 'w:sectPr' element into <see cref="GlobalSectPr"/>.
    /// Reader should be positioned to element start.
    /// </summary>
    /// <param name="isLastSectPr">Indicates that we read last SectPr. See <see cref="IsLastSectPrRead"/> for details.</param>
    void readGlobalSectPr(boolean isLastSectPr) throws Exception
    {
        mSectPrReader.read(this, GlobalSectPr);

        if (isLastSectPr)
            mIsLastSectPrRead = true;

        if (getCurContainer().getNodeType() == NodeType.BODY)
            isBlockLevelGlobalSectPr(true);
    }

    /// <summary>
    /// Reads all children of the current element into the specified node.
    /// </summary>
    void readStory(CompositeNode story) throws Exception
    {
        // Flush pending annotations to the current story before started
        // to read new one (this is the end of previous store).
        flushPendingCrossStructureAnnotations();

        // We are saving and restoring the current container, rather than just adding the story
        // straight away to the document tree because story nodes are usually added to the document later.
        // For example, comments are read first from the comments part into a hashtable, but they are
        // inserted into the document tree when inline nodes of the main document part are processed.
        CompositeNode savedContainer = getCurContainer();
        setCurContainer(story);

        mStoryReader.readChildren(this);

        // Flush pending annotations to the current story as we finished
        // to read it here (this is the end of current store).
        flushPendingCrossStructureAnnotations();

        // Restore container.
        setCurContainer(savedContainer);
    }

    /// <summary>
    /// Ends current section and starts new one.
    /// </summary>
    /// <remarks>The current reader container mast be a Body.</remarks>
    void startNewSection()
    {
        // End current section.
        popContainer(NodeType.BODY);
        popContainer(NodeType.SECTION);

        // Start new section.
        addAndPushContainer(new Section(getDocument()));
        addAndPushContainer(new Body(getDocument()));
    }

    /// <summary>
    /// Updates table when TableNormal style is missing.
    /// </summary>
    void fixMissingTableNormalIfNeeded(Table table) throws Exception
    {
        if (getDocument().getStyles().contains(StyleIdentifier.TABLE_NORMAL))
            return;

        if((table.getIstd() == StyleIndex.TABLE_NORMAL))
        {
            // When TableNormal style is missing Word updates table rows with fixed direct padding.
            for (Row row : (Iterable<Row>) table.getRows())
            {
                TablePr rowPr = row.getTablePr();
                rowPr.setAttrNoOverride(TableAttr.LEFT_PADDING, 10);
                rowPr.setAttrNoOverride(TableAttr.RIGHT_PADDING, 10);
                rowPr.setAttrNoOverride(TableAttr.TOP_PADDING, 0);
                rowPr.setAttrNoOverride(TableAttr.BOTTOM_PADDING, 0);
            }
        }

        // Need to fix indentation only for previous versions (see TestDefect3309_109) or when version is unspecified.
        Document doc = ms.as(getDocument(), Document.class);
        if ((doc != null) && (doc.getCompatibilityOptions().getMswVersion() < MsWordVersionCore.WORD_2013) && table.hasCells())
            fixUpTableIndentation(table);
    }

    private static void fixUpTableIndentation(Table table) throws Exception
    {
        Debug.assertTrue(table != null);

        // First row may be empty at this code point.
        Cell cell = table.getFirstRow().getFirstCell();
        if (cell == null)
            return;

        TablePr tablePr = table.getFirstRow().getTablePr();
        StyleCollection styles = table.getDocument().getStyles();
        TableStyle tblStyle = ms.as(styles.getByIstd(tablePr.getIstd(), false), TableStyle.class);

        boolean hasIndentation = (tablePr.get(TableAttr.LEFT_INDENT) != null) ||
            ((tblStyle != null) && NrxTableUtil.hasAttrInTableStyles(cell.getDocument(), tablePr, TableAttr.LEFT_INDENT));

        // Looks like the Word update table indentation only when "TableNormal" style is missing and the indentation
        // is not specified in the hierarchy.
        // The style "TableNormal" have been appeared since the Word 2003. According to tests the Word detects cases
        // when a document generated by an old version and does not apply changes for indentation. XML documents also
        // introduced in the Word 2003, so for all XML documents this fix should be applied.
        if (hasIndentation || styles.contains(StyleIdentifier.TABLE_NORMAL))
            return;

        // WORDSNET-23747 Update table left indentation from cell padding.
        int leftPadding = (/*int*/Integer)((ICellAttrSource)cell).fetchCellAttr(CellAttr.LEFT_PADDING);
        if (leftPadding > 0)
           table.setLeftIndent(ConvertUtilCore.twipToPoint(leftPadding));

        // Need to remove style if it was added during fetching of padding it may affect reading of other tables.
        // See Test7128 for details.
        if ((tblStyle == null) &&
            (tablePr.getIstd() <= StyleCollection.MAX_FIXED_ISTD) &&
            (styles.getByIstd(tablePr.getIstd(), false) != null))
        {
            styles.removeCore(table.getStyle());
        }

        // "TableNormal" should not appear after the indentation fixing.
        Debug.assertTrue(!styles.contains(StyleIdentifier.TABLE_NORMAL));
    }

    /// <summary>
    /// Checks that all used lists are exist and creates missed lists from predefined template.
    /// </summary>
    protected void ensureUsedListsExist()
    {
        for (int i = 0; i < UsedListIds.size(); i++)
        {
            int listId = UsedListIds.get(i);

            if (getDocument().getLists().getListByListId(listId) == null)
            {
                addPredefinedList(listId);
            }
        }
    }

    /// <summary>
    /// Adds predefined list and list definition.
    /// </summary>
    private void addPredefinedList(int listId)
    {
        List list = new List(getDocument(), listId);

        // All missed lists refers to one predefined list definition. Build it at first time.
        if (mMissedListsListDef == null)
        {
            int listDefId = getDocument().getLists().getNextAvailableListDefId();
            mMissedListsListDef = new ListDef(getDocument(), listDefId, ListType.HYBRID_MULTI_LEVEL, listDefId);
            getDocument().getLists().addListDef(mMissedListsListDef);

            setupLevel(mMissedListsListDef.getLevels().get(0), "\u0000.", 36, 720, -720);
            setupLevel(mMissedListsListDef.getLevels().get(1), "\u0001.", 72, 1440, -720);
            setupLevel(mMissedListsListDef.getLevels().get(2), "\u0002.", 108, 2160, -720);
            setupLevel(mMissedListsListDef.getLevels().get(3), "\u0003.", 144, 2880, -720);
            setupLevel(mMissedListsListDef.getLevels().get(4), "\u0004.", 180, 3600, -720);
            setupLevel(mMissedListsListDef.getLevels().get(5), "\u0005.", 216, 4320, -720);
            setupLevel(mMissedListsListDef.getLevels().get(6), "\u0006.", 252, 5040, -720);
            setupLevel(mMissedListsListDef.getLevels().get(7), "\u0007.", 288, 5760, -720);
            setupLevel(mMissedListsListDef.getLevels().get(8), "\b.", 324, 6480, -720);
        }
        else
        {
            // MS Word restarts each next list from '1' with list levels overrides.
            setupListOverrides(list);
        }

        list.setListDefId(mMissedListsListDef.getListDefId());
        getDocument().getLists().addList(list);
    }

    /// <summary>
    /// Helper method to easy setup list definition.
    /// </summary>
    private static void setupLevel(ListLevel level, String numberFormat, int tabStop, int leftIndent, int firstLineIndent)
    {
        level.setNumberStyle(NumberStyle.ARABIC);
        level.setNumberFormat(numberFormat);
        TabStopCollection tabStops = new TabStopCollection();
        tabStops.add(new TabStop(tabStop * 20, TabAlignment.LIST, TabLeader.NONE));
        level.getParaPr().setTabStops(tabStops);
        level.getParaPr().setLeftIndent(leftIndent);
        level.getParaPr().setFirstLineIndent(firstLineIndent);
    }

    /// <summary>
    /// Creates list levels overrides with <see cref="ListLevelOverride.IsStartAt"/>
    /// equal to 'true' and adds it to the specified list.
    /// </summary>
    private static void setupListOverrides(List list)
    {
        for (int i = 0; i < 9; i++)
        {
            ListLevelOverride levelOverride = new ListLevelOverride(list.getDocument(), i);
            levelOverride.IsStartAt = true;
            list.getOverrides().add(levelOverride);
        }
    }

    /// <summary>
    /// Collects styles which needs to be updated to avoid possible toggle issues.
    /// </summary>
    /// <remarks>
    /// We haven't choose toggle model yet but customer is impatient and I decided to make this little solution.
    /// Actually this is small step towards "DOC" model (model with Toggle/Same values).
    ///
    /// Not ready to make full solution, process only limited cases.
    ///
    /// If this solution considered acceptable, we don't need this detection at all and just need to update all styles.
    /// </remarks>
    void detectToggleIssue(Run run)
    {
        for (int key : G_TOGGLE_KEYS)
        {
            StyleCollection styles = run.getDocument().getStyles();

            // Do not update if run contains direct formatting.
            if (run.getRunPr().contains(key))
                continue;

            // Do not update if run has no parent.
            Paragraph parentParagraph = run.getParentParagraph();
            if (parentParagraph == null)
                continue;

            // Do not update if paragraph style missing.
            Style paraStyle = styles.getByIstd(parentParagraph.getParaPr().getIstd(), false);
            if (paraStyle == null)
                continue;

            // Do not update if paragraph style has no toggle attribute set.
            Object paraValue = paraStyle.getFontAttr(key, false);
            if (paraValue == null)
                continue;

            // Check if we in table having other than TableNormal style.
            Table table = parentParagraph.getParentTable();
            boolean canHaveTableStyleFormatting = (table != null) && (table.getIstd() != StyleIndex.TABLE_NORMAL);

            if (canHaveTableStyleFormatting)
            {
                // Do not investigate table style for toggles, just update paragraph style to avoid possible issues.
                mToggleIssueStyles.add(paraStyle.getIstd());

                Style charStyle = styles.getByIstd(run.getRunPr().getIstd(), false);
                if (charStyle == null)
                    continue;

                Object charValue = charStyle.getFontAttr(key, false);
                if (charValue == null)
                    continue;

                mToggleIssueStyles.add(charStyle.getIstd());
            }
            else
            {
                // Outside of tables update only if both character and paragraph styles have toggle attribute set.
                Style charStyle = styles.getByIstd(run.getRunPr().getIstd(), false);
                if (charStyle == null)
                    continue;

                Object charValue = charStyle.getFontAttr(key, false);
                if (charValue == null)
                    continue;

                mToggleIssueStyles.add(paraStyle.getIstd());
                mToggleIssueStyles.add(charStyle.getIstd());
            }
        }
    }

    /// <summary>
    /// Applies default style of a specified type to the specified attributes collection.
    /// </summary>
    /// <dev>
    /// In accordance with ISO29500-1, 17.7.4.17 style (Style Definition):
    /// ...The paragraph style with the default attribute set is the paragraph style applied
    /// to all paragraphs which do not explicitly reference a paragraph style using the pStyle element...
    /// </dev>
    void applyDefaultStyle(AttrCollection attrs, /*StyleType*/int type)
    {
        int key;
        /*StyleIdentifier*/int styleIdentifier;
        switch (type)
        {
            case StyleType.PARAGRAPH:
            {
                key = ParaAttr.ISTD;
                styleIdentifier = StyleIdentifier.NORMAL;
                break;
            }
            case StyleType.TABLE:
            {
                key = TableAttr.ISTD;
                styleIdentifier = StyleIdentifier.TABLE_NORMAL;
                break;
            }
            default:
                throw new IllegalArgumentException(null + "\r\nParameter name: " + "type" + "\r\nParameter value: " + type);
        }

        if (!attrs.contains(key))
        {
            Style defaultStyle = getDefaultStyles().get((int)type);
            if ((defaultStyle != null) && (defaultStyle.getStyleIdentifier() != styleIdentifier))
                attrs.setAttr(key, defaultStyle.getIstd());
        }
    }

    /// <summary>
    /// Updates styles that caused toggle issue.
    /// </summary>
    /// <remarks>
    /// See <see cref="DetectToggleIssue"/> for details.
    /// </remarks>
    protected void resolveToggleIssues()
    {
        for(int key : G_TOGGLE_KEYS)
            for (int istd : mToggleIssueStyles)
            {
                Style style = getDocument().getStyles().getByIstd(istd, false);

                convertToggles(style, key);

                Style baseStyle = style.getBaseStyle();
                if (baseStyle != null)
                    convertToggles(baseStyle, key);
            }
    }

    /// <summary>
    /// Converts toggle attributes to Toggle/Same model.
    /// </summary>
    private static void convertToggles(Style style, int key)
    {
        AttrBoolEx value = (AttrBoolEx)style.getRunPr().get(key);

        if ((value == AttrBoolEx.TRUE) || (value == AttrBoolEx.FALSE))
        {
            AttrBoolEx inheritedValue = (AttrBoolEx)((IRunAttrSource)style).fetchInheritedRunAttr(key);
            style.getRunPr().setAttr(key, (value == inheritedValue) ? AttrBoolEx.SAME : AttrBoolEx.TOGGLE);
        }
    }

    /// <summary>
    /// Indicate whether leading and trailing white-spaces in w:t element must be preserved.
    /// Set upon reading WML document.
    /// </summary>
    boolean getPreserveSpace() { return mPreserveSpace; }
    void setPreserveSpace(boolean value) { mPreserveSpace = value; }

    LoadOptions getLoadOptions() { return mLoadOptions; }

    OoxmlComplianceInfo getComplianceInfo() { return mComplianceInfo; }

    /// <summary>
    /// Content resolver for inline SDT's.
    /// </summary>
    InlineMarkupResolver getInlineResolver() { return mMarkupResolver.getInlineResolver(); }

    /// <summary>
    /// Gets/sets an encoding that is detected on reading a math from equation XML.
    /// </summary>
    public Encoding getDetectedEncoding() { return mDetectedEncoding; }
    public void setDetectedEncoding(Encoding value) { mDetectedEncoding = value; }

    /// <summary>
    /// Currently started customXml revision.
    /// For a moment it is used for SDT's revisions only.
    /// </summary>
    /// <remarks>
    /// IN. MS Word wraps into customXmlRangeStart / customXmlRangeEnd starting and ending SDT's tags.
    /// So, these pair acts as starting and ending 'marker' for SDT's revision. As starting 'marker' occurs
    /// before any SDT is read, we need to store it in this temporary storage.
    /// Later, when we read SDT itself, we apply this revision to it.
    /// For details see ISO29500-1, 17.13.5.4 customXmlDelRangeEnd, 17.13.5.5 customXmlDelRangeStart,
    /// 17.13.5.6 customXmlInsRangeEnd, 17.13.5.7 customXmlInsRangeStart.
    /// </remarks>
    RevisionBase getCurrentCustomXmlRevision() { return mCurrentCustomXmlRevision; }
    void setCurrentCustomXmlRevision(RevisionBase value) { mCurrentCustomXmlRevision = value; }

    abstract boolean isEquationXmlReader();

    /// <summary>
    /// Indicates that SectPr for last section is read.
    /// Word distinguishes section properties for very last section and for all others.
    /// It treats section properties as properties for very last section when they
    /// are located not inside ParaPr. For example, Word stops to read headers and
    /// footers when last SectPr is read (even if it is completely empty).
    /// Actually, Word starts to read them into footnote.
    /// </summary>
    boolean isLastSectPrRead() { return mIsLastSectPrRead; }

    /// <summary>
    /// The properties for very last section.
    /// </summary>
    SectPr getLastSectPr() { return mLastSectPr; }

    /// <summary>
    /// Keeps last added to the collection default style of a certain type.
    /// The key is <see cref="StyleType"/>, the value is <see cref="Style"/>.
    /// </summary>
    IntToObjDictionary<Style> getDefaultStyles() { return mDefaultStyles; }

    StoryRevisionStack StoryRevisionStack;

    /// <summary>
    /// Collects all ListId used in document.
    /// The order of ListIds is important, because MS Word creates missed lists
    /// for very first list in a different way with all other lists.
    /// So, do not sort collected ListIds.
    /// </summary>
    /*final*/ ArrayList<Integer> UsedListIds = new ArrayList<Integer>();

    /// <summary>
    /// Collection of tables with missed TableGrid.
    /// </summary>
    /*final*/ ArrayList<Table> TablesWithMissedTableGrid = new ArrayList<Table>();

    /// <summary>
    /// Collects information about connector rules. Keys are shape name and values are connector rules.
    /// </summary>
    /*final*/ HashMap<String, ConnectorRule> ConnectorRules = new HashMap<String, ConnectorRule>();

    /// <summary>
    /// Section properties read at document body level. See TestJira10940 for details.
    /// </summary>
    /*final*/ SectPr GlobalSectPr = new SectPr();

    /// <summary>
    /// A flag indicating whether a 'sectPr' element located in a body as a direct child has been read.
    /// If such an element is present in a document, headers/footers of subsequent 'sectPr' elements are ignored.
    /// </summary>
    boolean isBlockLevelGlobalSectPr() { return mIsBlockLevelGlobalSectPr; }; private void isBlockLevelGlobalSectPr(boolean value) { mIsBlockLevelGlobalSectPr = value; };

    private boolean mIsBlockLevelGlobalSectPr;

    /// <summary>
    /// Collects paragraph formatting specified outside of paragraph element.
    /// </summary>
    /*final*/ ParaPr GlobalParaPr = new ParaPr();

    /// <summary>
    /// Collects table row formatting specified outside of the 'tr' element.
    /// </summary>
    /*final*/ TablePr GlobalRowPr = new TablePr();

    /// <summary>
    /// Collects paragraph break formatting specified outside of paragraph element.
    /// </summary>
    /*final*/ RunPr GlobalParagraphBreakRunPr = new RunPr();

    /// <summary>
    /// Click and Type paragraph style ID is stored here before document styles are read.
    /// </summary>
    String ClickAndTypeStyleId;
    /// <summary>
    /// Default table style ID is stored here before document styles are read.
    /// </summary>
    String DefaultTableStyleId;

    /// <summary>
    /// Collects styles caused AttrBoolEx issue. See <see cref="DetectToggleIssue"/>
    /// </summary>
    private /*final*/ HashSetGeneric<Integer> mToggleIssueStyles = new HashSetGeneric<Integer>();

    private /*final*/ LoadOptions mLoadOptions;
    /// <summary>
    /// The document we are building.
    /// </summary>
    private /*final*/ DocumentBase mDoc;
    /// <summary>
    /// The current container node in the document tree to which we are adding child nodes.
    /// </summary>
    private CompositeNode mCurContainer;
    /// <summary>
    /// True if the current story is header or footer.
    /// </summary>
    private boolean mIsInHeaderFooter;

    private /*final*/ ArrayList<Node> mPendingCrossStructureAnnotations = new ArrayList<Node>();

    /// <summary>
    /// Collects inline level nodes read outside of paragraph.
    /// </summary>
    private /*final*/ ArrayList<NrxDocumentReaderBase.InlineInfo> mPendingInlines = new ArrayList<NrxDocumentReaderBase.InlineInfo>();

    /// <summary>
    /// Collects block nodes read outside of story.
    /// </summary>
    private /*final*/ ArrayList<Node> mPendingBlocks = new ArrayList<Node>();

    /// <summary>
    /// Collects paragraph nodes read on unexpected levels.
    /// </summary>
    private /*final*/ ArrayList<Paragraph> mPendingParagraphs = new ArrayList<Paragraph>();

    /// <summary>
    /// Used by ShapeReader only. Maybe move there?
    /// </summary>
    private /*final*/ ArrayList<ShapeBase> mZOrderListHeader = new ArrayList<ShapeBase>();
    /// <summary>
    /// Used by ShapeReader only. Maybe move there?
    /// </summary>
    private /*final*/ ArrayList<ShapeBase> mZOrderListMain = new ArrayList<ShapeBase>();

    /// <summary>
    /// Maintains relationship between shape id (e.g. "_x0000_i1030") and shape object in the model.
    /// Used to retrieve shapes by id to assign them OLE data.
    /// (OLE data is defined separately from shapes in WordML.)
    /// </summary>
    private /*final*/ HashMap<String, ShapeBase> mShapeOriginalIdsMap = new HashMap<String, ShapeBase>();

    // Collects all shape in document.
    private /*final*/ ArrayList<ShapeBase> mAllShapes = new ArrayList<ShapeBase>();

    // Collects all DrawingML textboxes in document.
    private /*final*/ IntToObjDictionary<ShapeBase> mDmlTextBoxes = new IntToObjDictionary<ShapeBase>();

    // Collects all linked DrawingML textboxes in document.
    private /*final*/ HashMap<Integer, SortedIntegerListGeneric<ShapeBase>> mDmlLinkedTextboxes =
        new HashMap<Integer, SortedIntegerListGeneric<ShapeBase>>();

    /// <summary>
    /// Collects linked textboxes drawingMls with preserved sequence of reading shapes.
    /// </summary>
    private /*final*/ HashMap<Integer, Shape> mDmlTxbxByIndex = new HashMap<Integer, Shape>();

    /// <summary>
    /// Key is style id string. Value is istd integer.
    /// </summary>
    private /*final*/ HashMap<Object, Integer> mStyleIdToIstd = new HashMap<Object, Integer>();
    /// <summary>
    /// Key is bookmark id integer from XML. Value is string bookmark name.
    /// </summary>
    private /*final*/ HashMap<Integer, String> mIdToBookmarkName = new HashMap<Integer, String>();
    /// <summary>
    /// Key is abstract num id. Value is a list definition object.
    /// </summary>
    private /*final*/ HashMap<Integer, ListDef> mAbstractNumIdToListDef = new HashMap<Integer, ListDef>();

    /// <summary>
    /// Key is MD4 Guid hash of the image bytes. Value is the image bytes.
    /// </summary>
    private /*final*/ HashMap<BytesHash, byte[]> mImageBytesByGuidTable = new HashMap<BytesHash, byte[]>();
    private /*final*/ MarkupResolver mMarkupResolver;
    private boolean mPreserveSpace;

    /// <summary>
    /// Key is string id of shape type. Value is ShapePr.
    /// See [ECMA-376] M.5.4 ShapeType Element.
    /// </summary>
    private /*final*/ HashMap<String, ShapePr> mShapeTemplates = new HashMap<String, ShapePr>();
    private /*final*/ OoxmlComplianceInfo mComplianceInfo;
    private /*final*/ NrxStoryReaderBase mStoryReader;
    private /*final*/ NrxSectPrReaderBase mSectPrReader;
    private Encoding mDetectedEncoding;

    /// <summary>
    /// Predefined list definition used for missed lists.
    /// </summary>
    private ListDef mMissedListsListDef;

    /// <summary>
    /// Currently started customXml revision.
    /// For a moment it is used for SDT's revisions only.
    /// </summary>
    private RevisionBase mCurrentCustomXmlRevision;

    private /*final*/ SectPr mLastSectPr = new SectPr();
    private boolean mIsLastSectPrRead;

    /// <summary>
    /// Enumerates toggle keys used to resolve toggle collision issue.
    /// </summary>
    /// <remarks>
    /// AM. So far this set is incomplete mostly because fix is experimental.
    /// </remarks>
    private static /*final*/ int[] G_TOGGLE_KEYS = new int[]
        {
            FontAttr.BOLD,
            FontAttr.ALL_CAPS,
            FontAttr.STRIKE_THROUGH,
            FontAttr.HIDDEN,
            FontAttr.ITALIC,
            FontAttr.ITALIC_BI,
            FontAttr.SMALL_CAPS
        };

    /// <summary>
    /// Stores inline node and its parent story.
    /// </summary>
    private static class InlineInfo
    {
        InlineInfo(Node inline, Node story)
        {
            mInline = inline;
            mStory = story;
        }

        Node getInline() { return mInline; };

        private Node mInline;
        Node getStory() { return mStory; };

        private Node mStory;
    }

    /// <summary>
    /// Keeps last added to the collection default style of a certain type.
    /// The key is <see cref="StyleType"/>, the value is <see cref="Style"/>.
    /// </summary>
    private /*final*/ IntToObjDictionary<Style> mDefaultStyles = new IntToObjDictionary<Style>(4);

    private CompositeNode mPendingBlockDestination;

    private /*final*/ HashMap<Shape, String> mMissingShapeSources = new HashMap<Shape, String>();
}

