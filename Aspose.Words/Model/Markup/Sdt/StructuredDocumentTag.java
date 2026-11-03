// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/06/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.ArgumentUtil;
import com.aspose.ms.System.DateTime;
import java.awt.Color;
import com.aspose.DrColor;
import com.aspose.ms.System.msString;


/// <summary>
/// Represents a structured document tag (SDT or content control) in a document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-content-control-sdt/">Structured Document Tags or Content Control</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>Structured document tags (SDTs) allow to embed customer-defined semantics as well as its
/// behavior and appearance into a document.</para>
///
/// <para>In this version Aspose.Words provides a number of public methods and properties to
/// manipulate the behavior and content of <see cref="StructuredDocumentTag"/>.
/// Mapping of SDT nodes to custom XML packages within a document can be performed with using
/// the <see cref="XmlMapping"/> property.</para>
///
/// <para><see cref="StructuredDocumentTag"/> can occur in a document in the following places:</para>
/// <list type="bullet">
/// <item>Block-level - Among paragraphs and tables, as a child of a <see cref="Body"/>, <see cref="HeaderFooter"/>,
/// <see cref="Comment"/>, <see cref="Footnote"/> or a <see cref="Aspose.Words.Drawing.Shape"/> node.</item>
/// <item>Row-level - Among rows in a table, as a child of a <see cref="Aspose.Words.Tables.Table"/> node.</item>
/// <item>Cell-level - Among cells in a table row, as a child of a <see cref="Aspose.Words.Tables.Row"/> node.</item>
/// <item>Inline-level - Among inline content inside, as a child of a <see cref="Paragraph"/>.</item>
/// <item>Nested inside another <see cref="StructuredDocumentTag"/>.</item>
/// </list>
///
/// </remarks>
public class StructuredDocumentTag extends CompositeNode<Node> implements IMarkupNode, ITrackableNode, IRunAttrSource, IStructuredDocumentTag
{
    /// <summary>
    /// Initializes a new instance of the <b>Structured document tag</b> class.
    /// </summary>
    /// <remarks>
    /// <para>The following types of SDT can be created:</para>
    /// <list type="bullet">
    /// <item><see cref="Markup.SdtType.Checkbox"/></item>
    /// <item><see cref="Markup.SdtType.DropDownList"/></item>
    /// <item><see cref="Markup.SdtType.ComboBox"/></item>
    /// <item><see cref="Markup.SdtType.Date"/></item>
    /// <item><see cref="Markup.SdtType.BuildingBlockGallery"/></item>
    /// <item><see cref="Markup.SdtType.Group"/></item>
    /// <item><see cref="Markup.SdtType.Picture"/></item>
    /// <item><see cref="Markup.SdtType.RichText"/></item>
    /// <item><see cref="Markup.SdtType.PlainText"/></item>
    /// </list>
    /// </remarks>
    /// <param name="doc">The owner document.</param>
    /// <param name="type">Type of SDT node.</param>
    /// <param name="level">Level of SDT node within the document.</param>
    public StructuredDocumentTag(DocumentBase doc, /*SdtType*/int type, /*MarkupLevel*/int level)
    {
    	this(doc, level);
	
        setControlProperties(create(type, level));
        createPlaceholderIfNeeded();
        SdtContentHelper.insertDefaultContent(this, false);
        mId = doc.getSdtIdManager().getUniqueId();
    }

    /// <summary>
    /// internal AW ctor.
    /// </summary>
    /// <remarks>
    /// By default this ctor does not define things like placeholder, id, control properties etc.
    /// This is the responsibility of the caller, so you should not use this ctor unless you know what you're doing.
    /// </remarks>
    StructuredDocumentTag(DocumentBase doc, /*MarkupLevel*/int level)
    {
    	super(doc);
	
        mLevel = level;
        mControlProperties = new SdtEmpty();
        mContentsRunPr = new RunPr();
        mEndCharRunPr = new RunPr();
        mXmlMapping = new XmlMapping(this);
    }

    /// <summary>
    /// Sets the symbol used to represent the checked state of a check box content control.
    /// </summary>
    /// <param name="characterCode">The character code for the specified symbol.</param>
    /// <param name="fontName">The name of the font that contains the symbol.</param>
    /// <remarks>
    /// <para>Accessing this method will only work for <see cref="Markup.SdtType.Checkbox"/> SDT types.</para>
    /// <para>For all other SDT types exception will occur.</para>
    /// </remarks>
    public void setCheckedSymbol(int characterCode, String fontName) throws Exception
    {
        setSymbol(characterCode, fontName, true);
    }

    /// <summary>
    /// Sets the symbol used to represent the unchecked state of a check box content control.
    /// </summary>
    /// <param name="characterCode">The character code for the specified symbol.</param>
    /// <param name="fontName">The name of the font that contains the symbol.</param>
    /// <remarks>
    /// <para>Accessing this method will only work for <see cref="Markup.SdtType.Checkbox"/> SDT types.</para>
    /// <para>For all other SDT types exception will occur.</para>
    /// </remarks>
    public void setUncheckedSymbol(int characterCode, String fontName) throws Exception
    {
        setSymbol(characterCode, fontName, false);
    }

    /*override*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        StructuredDocumentTag lhs = (StructuredDocumentTag)super.deepClone(isCloneChildren, cloningListener);

        lhs.mControlProperties = mControlProperties.deepClone();

        // AM. Temporarily make link to parent SDT in simplest way.
        // Later we need to think how it can be refactored.
        if (lhs.mControlProperties instanceof SdtDropDownList || lhs.mControlProperties instanceof SdtComboBox)
            ((SdtDropDownListBase)lhs.mControlProperties).getListItems().setParentSdt(lhs);

        lhs.mContentsRunPr = AttrCollectionExtension.deepClone(mContentsRunPr);
        lhs.mEndCharRunPr = AttrCollectionExtension.deepClone(mEndCharRunPr);

        if (mPlaceholder != null) // we reuse blocks, not clone.
            lhs.mPlaceholder = mPlaceholder;

        if (mId != UNUSED_SDT_ID) // happens during testing
            lhs.mId = lhs.getDocument().getSdtIdManager().addUniqueId(mId);

        lhs.mXmlMapping = mXmlMapping.deepClone(lhs);

        lhs.mContentsFont = null; // created on demand

        return lhs;
    }

    /// <summary>
    /// Removes just this SDT node itself, but keeps the content of it inside the document tree.
    /// </summary>
    public void removeSelfOnly() throws Exception
    {
        removeSelfOnly(true);
    }

    /// <summary>
    /// Removes just this SDT node itself, but keeps the content of it inside the document tree.
    /// </summary>
    void removeSelfOnly(boolean isUpdateDataBoundContent) throws Exception
    {
        // WORDSNET-13527 Update data bound content before removing this sdt.
        if (isUpdateDataBoundContent)
            getXmlMapping().updateContent();

        NodeUtil.resetDisplacedAnnotationReferences(this, true);

        coreRemoveSelfOnly();
    }

    /// <include file='..\..\Docs\Text.xml' path='Topics/Topic[@name="Node.Accept"]/*'/>
    /// <remarks>
    /// Calls <see cref="DocumentVisitor.VisitStructuredDocumentTagStart"/>, then calls <see cref="Node.Accept"/> for all
    /// child nodes of the smart tag and calls <see cref="DocumentVisitor.VisitStructuredDocumentTagEnd"/> at the end.
    /// </remarks>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return acceptCore(visitor);
    }

    /// <summary>
    /// Clears contents of this structured document tag and displays a placeholder if it is defined.
    /// </summary>
    /// <remarks>
    /// <para>It is not possible to clear contents of a structured document tag if it has revisions.</para>
    /// <para>If this structured document tag is mapped to custom XML (with using the <see cref="XmlMapping"/>
    /// property), the referenced XML node is cleared.</para>
    /// </remarks>
    public void clear() throws Exception
    {
        SuspendMappedCustomXmlUpdateDocument suspendMappedCustomXmlUpdateDocumentResource = new SuspendMappedCustomXmlUpdateDocument(getDocument());
        try /*JAVA: was using*/
        {
            SdtContentHelper.clear(this);
        }
        finally { if (suspendMappedCustomXmlUpdateDocumentResource != null) suspendMappedCustomXmlUpdateDocumentResource.dispose(); }
    }

    /// <summary>
    /// Accepts a visitor for visiting the start of the StructuredDocumentTag.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptStart(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitStructuredDocumentTagStart(this);
    }

    /// <summary>
    /// Accepts a visitor for visiting the end of the StructuredDocumentTag.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptEnd(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitStructuredDocumentTagEnd(this);
    }

    /// <summary>
    /// Allows to insert inline-level, cell-level, row-level or block-level nodes depending on
    /// the value of the <see cref="Level"/> property.
    /// </summary>
    /*override*/ boolean canInsert(Node newChild)
    {
        return NodeUtil.canInsertIntoMarkupNode(this, newChild);
    }

    /// <summary>
    /// Update this SDT id to a unique value within document.
    /// Used in <see cref="DocumentPostLoader"/> to maintain validity of the model.
    /// </summary>
    void updateId()
    {
        if (mId == UNUSED_SDT_ID)
            mId = getDocument().getSdtIdManager().getUniqueId();
    }

    /// <summary>
    /// Internal setter that also validates uniqueness of this id across all SDTs in the parent document.
    /// </summary>
    void setId(int id)
    {
        mId = getDocument().getSdtIdManager().addUniqueId(id);
    }

    void setIdExplicitly(int id)
    {
        mId = id;
    }

    /// <summary>
    /// Finds placeholder BuildingBlock in the glossary of the document and stores reference to it for use in <see cref="Placeholder"/>.
    /// Creates placeholder if needed.
    /// </summary>
    /// <remarks>
    /// If forcePlaceholderLookup set to true, then we will find and attach a new placeholder
    /// even if mPlaceholder is not null which is used during import.
    /// </remarks>
    void updatePlaceholderReference(boolean forcePlaceholderLookup)
    {
        // Find building block corresponding to this placeholder, if not found create a new one.
        if ((forcePlaceholderLookup || (mPlaceholder == null)) && StringUtil.hasChars(getPlaceholderName()))
            mPlaceholder = getDocument().getSdtPlaceholderManager().findPlaceholder(this, true);

        getDocument().getSdtPlaceholderManager().incrementReference(this);
    }

    /// <summary>
    /// Used  when we don't want to search for BuildingBlock.
    /// This happens during read operation when we don't yet have access to fully constructed glossary
    /// and thus can not use <see cref="PlaceholderName"/>
    /// </summary>
    void setPlaceholderNameCore(String value)
    {
        if (StringUtil.hasChars(value))
            mPlaceholderName = value;
    }

    /// <summary>
    /// Sets the symbol used to represent the checked/unchecked state of a check box content control.
    /// </summary>
    private void setSymbol(int characterCode, String fontName, boolean isCheckedSymbol) throws Exception
    {
        validateUsage(getSdtType() == SdtType.CHECKBOX,
            (isCheckedSymbol) ? ERROR_CHECKBOX_SET_CHECKED_SYMBOL : ERROR_CHECKBOX_SET_UNCHECKED_SYMBOL);

        SdtCheckBox sdtCheckbox = (SdtCheckBox)getControlProperties();

        if (isCheckedSymbol)
            sdtCheckbox.setCheckedStateInfo(new SdtCheckBoxStateInfo(fontName, characterCode));
        else
            sdtCheckbox.setUncheckedStateInfo(new SdtCheckBoxStateInfo(fontName, characterCode));

        updateCheckboxContent(isCheckedSymbol);
    }

    /// <summary>
    /// Updates the checkbox content.
    /// </summary>
    private void updateCheckboxContent(boolean isChecked) throws Exception
    {
        SdtContentHelper.updateCheckboxContent(this);

        if ((getXmlMapping() == null) || getXmlMapping().isEmpty())
            return;

        String stringValue = getXmlMapping().getValue();

        // WORDSNET-20775 XmlMapping can be not null and not empty, but without related custom xml.
        // In this case we should skip it - impossible to update the non existing xml.
        if (stringValue == null)
            return;

        // Update data bound XML if value changed.
        boolean boundValue = (("1".equals(stringValue)) || ("true".equals(stringValue)));

        if (boundValue != isChecked)
            getXmlMapping().setValue(isChecked ? "true" : "false");
    }

    /// <summary>
    /// Validates the usage.
    /// </summary>
    private void validateUsage(boolean validCondition, String errorMessage)
    {
        assertNonEmptyControlProperties();

        if (!validCondition)
            throw new IllegalStateException(errorMessage);
    }

    /// <summary>
    /// Indicates that we support update of given SdtType.
    /// </summary>
    boolean isUpdateable()
    {
        switch (getSdtType())
        {
            case SdtType.PLAIN_TEXT:
            case SdtType.RICH_TEXT:
            case SdtType.DATE:
            case SdtType.DROP_DOWN_LIST:
            case SdtType.COMBO_BOX:
            case SdtType.PICTURE:
            case SdtType.REPEATING_SECTION:
            case SdtType.CHECKBOX:
                return true;

            default:
                return false;
        }
    }

    /// <summary>
    /// Returns true when the given SDT has to be ignored while updating.
    /// </summary>
    boolean canBeUpdated()
    {
        // WORDSNET-20475 Do not update data bound SDT in AlternateContent i.e outside of Document.
        Node doc = getAncestor(NodeType.DOCUMENT);
        if (doc == null)
            return false;

        return getDocument().getNodeType() != NodeType.GLOSSARY_DOCUMENT;
    }

    /// <summary>
    /// Returns sdtContent displayed text, hidden text and text marked for deletion are ignored.
    /// </summary>
    String getVisibleText()
    {
        StringBuilder visibleText = new StringBuilder();

        NodeCollection sdtContent = getChildNodes(NodeType.RUN, true);
        for (Run run : (Iterable<Run>) sdtContent)
        {
            // Do not count hidden text.
            if (InlineHelper.fetchAttr(run, FontAttr.HIDDEN) == AttrBoolEx.TRUE)
                continue;

            // WORDSNET-24644 Do not count text marked for deletion.
            if(run.isDeleteRevision())
                continue;

            msStringBuilder.append(visibleText, run.getText());
        }

        return visibleText.toString();
    }

    /// <summary>
    /// Sets the level at which this <b>SDT</b> occurs in the document tree.
    /// </summary>
    void setLevel(/*MarkupLevel*/int value)
    {
        StructuredDocumentTag sdtToCheck = new StructuredDocumentTag(getDocument(), value);
        if ((getParentNode() != null) && !getParentNode().canInsert(sdtToCheck))
            throw new IllegalStateException("Cannot change markup level.");

        NodeCollection nodes= getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);

        for (StructuredDocumentTag sdt : (Iterable<StructuredDocumentTag>) nodes)
            checkAndSet(value, sdtToCheck, sdt);

        // The same for this node.
        checkAndSet(value, sdtToCheck, this);
    }

    private static void checkAndSet(/*MarkupLevel*/int value, StructuredDocumentTag sdtToCheck, StructuredDocumentTag sdt)
    {
        for (Node node : (Iterable<Node>) sdt.getChildNodes(NodeType.ANY, false))
        {
            if ((node.getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG) && !sdtToCheck.canInsert(node))
                throw new IllegalStateException("Cannot change markup level.");
        }

        sdt.mLevel = value;
    }

    /// <summary>
    /// Inserts default contents and sets the <see cref="IsShowingPlaceholderText"/> flag if the SDT is empty.
    /// </summary>
    void insertDefaultContentIfEmpty()
    {
        if (!getXmlMapping().isEmpty())
            return;

        // Implemented for Block and Inline SDTs only now.
        Debug.assertTrue(getLevel() == MarkupLevel.INLINE || getLevel() == MarkupLevel.BLOCK);

        CompositeNode compositeChild = getFirstNonMarkupCompositeDescendant();
        if (compositeChild != null)
        {
            // Exit if the SDT contains a non-empty paragraph or a table.
            if ((compositeChild != getLastNonMarkupCompositeDescendant()) ||
                (compositeChild.getFirstNonAnnotationChild() != null) ||
                (compositeChild.getNodeType() != NodeType.PARAGRAPH))
                return;
        }
        else
        {
            // Exit if the SDT contains inlines.
            if (hasNonMarkupNonAnnotationDescendant())
                return;
        }

        isShowingPlaceholderText(true);
        if ((getPlaceholder() != null) || (getSdtType() == SdtType.PICTURE) || (getSdtType() == SdtType.CHECKBOX))
        {
            if (compositeChild != null)
            {
                // Keep annotations.
                insertBefore(compositeChild.getFirstChild(), null, compositeChild);
                compositeChild.remove();
            }

            SdtContentHelper.insertDefaultContent(this, true);
        }
        else
        {
            SdtContentHelper.insertContentForEmptySdt(
                (compositeChild != null) ? compositeChild : this,
                getContentsRunPr(),
                (getLevel() == MarkupLevel.BLOCK) && (compositeChild == null),
                getEndCharacterRunPr());
        }
    }

    /// <summary>
    /// Checks that given range is valid to place ranged SDT.
    /// </summary>
    static boolean isRangeValid(Node rangeStart, Node rangeEnd)
    {
        // Make severe restrictions for range location for a while. Later we can weaken it if needed.

        if (rangeStart.getParentNode().getNodeType() != NodeType.BODY)
            return false;

        if (rangeEnd.getParentNode().getNodeType() != NodeType.BODY)
            return false;

        return true;
    }

    /// <summary>
    /// Converts all (includes <paramref name="node"/>) outer SDTs to ranged STDs.
    /// </summary>
    static void convertOuterSdtsToRanges(Node node)
    {
        ArrayList<StructuredDocumentTag> outerSdts = new ArrayList<StructuredDocumentTag>();

        // Collect all STD ancestors in topmost-first order.
        Node curNode = node;
        while (curNode != null)
        {
            if(curNode.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
                outerSdts.add(0, (StructuredDocumentTag)curNode);

            curNode = curNode.getParentNode();
        }

        // And convert them all to ranged SDTs.
        for(StructuredDocumentTag sdt : outerSdts)
            sdt.convertToRange(sdt.getLastChild(), true);
    }

    /// <summary>
    /// Helper method to convert the SDT to ranged SDT.
    /// </summary>
    /// <remarks>
    /// This proposed to be public API but at first stage we just handle round-trip.
    /// </remarks>
    StructuredDocumentTagRangeStart convertToRange(Node refRangeEnd, boolean isAfter)
    {
        StructuredDocumentTagRangeStart rangeStart = new StructuredDocumentTagRangeStart(getDocument(), this);

        // Insert range right after original SDT.
        getParentNode().insertAfter(rangeStart, this);

        // Move out content of original SDT.
        Node refNode = rangeStart;
        while (getChildNodes(NodeType.ANY, false).getCount() > 0)
            refNode = refNode.insertNext(getFirstChild());

        // Now original SDT can be removed from document tree.
        remove();

        StructuredDocumentTagRangeEnd rangeEnd = new StructuredDocumentTagRangeEnd(rangeStart.getDocument(), rangeStart.getId());
        refRangeEnd.getParentNode().insert(rangeEnd, refRangeEnd, isAfter);

        return rangeStart;
    }

    /// <summary>
    /// Converts current inline SDT to block SDT.
    /// Moves content before and after STD to individual paragraphs if any.
    /// </summary>
    void convertToBlock()
    {
        Debug.assertTrue(getLevel() == MarkupLevel.INLINE);

        Paragraph parentParagraph = (Paragraph)getAncestor(NodeType.PARAGRAPH);

        // Check if any content before SDT.
        if (!isFirstOrBookmarksBefore(this))
        {
            // Extract part before SDT to separate paragraph.
            Paragraph before = new Paragraph(parentParagraph.getDocument());
            while (!isFirstChild())
                before.appendChild(parentParagraph.getFirstChild());

            parentParagraph.insertPrevious(before);
        }

        // Check if any content after SDT.
        if (!isLastOrBookmarksAfter(this))
        {
            // Extract part after SDT to separate paragraph.
            Paragraph after = (Paragraph)parentParagraph.deepClone(false);
            while (!isLastChild())
                after.appendChild(getNextSibling());

            parentParagraph.insertNext(after);
        }

        // Here we can have only bookmarks before SDT, move them.
        while (!isFirstChild())
            parentParagraph.insertPrevious(parentParagraph.getFirstChild());

        // Here we can have only bookmarks after SDT, move them.
        Node nodeAfter = parentParagraph;
        while (!isLastChild())
            nodeAfter = parentParagraph.getParentNode().insertAfter(getNextSibling(), nodeAfter);

        // Change SDT level, we have to remove SDT to do this.
        parentParagraph.insertAfter(getFirstChild(), null, null);
        remove();
        setLevel(MarkupLevel.BLOCK);

        parentParagraph.insertPrevious(this);
        appendChild(parentParagraph);
    }

    /// <summary>
    /// Checks that given SDT is a first child or only bookmarks are before.
    /// </summary>
    private static boolean isFirstOrBookmarksBefore(StructuredDocumentTag sdt)
    {
        if (sdt.isFirstChild())
            return true;

        Node node = sdt.getPreviousSibling();
        while (node != null)
        {
            if (!NodeUtil.isBookmarkNode(node))
                return false;

            node = node.getPreviousSibling();
        }

        return true;
    }

    /// <summary>
    /// Checks that given SDT is a last child or only bookmarks are after.
    /// </summary>
    private static boolean isLastOrBookmarksAfter(StructuredDocumentTag sdt)
    {
        if (sdt.isLastChild())
            return true;

        Node node = sdt.getNextSibling();
        while (node != null)
        {
            if (!NodeUtil.isBookmarkNode(node))
                return false;

            node = node.getNextSibling();
        }

        return true;
    }

    @ReservedForInternalUse @Deprecated
    public boolean /*IStructuredDocumentTag.*/isMultiSection() {return false; }

    @ReservedForInternalUse @Deprecated
    public Node /*IStructuredDocumentTag.*/getNode() { return this; }

    /// <summary>
    /// Returns true if this SdtType can be created through public API, false otherwise.
    /// </summary>
    /// <remarks>
    /// DD: This method can be useful for public API, RK to review and include if needed.
    /// </remarks>
    private static boolean isTypeAllowedForCreation(/*SdtType*/int type)
    {
        switch (type)
        {
            case SdtType.CHECKBOX:
            case SdtType.DROP_DOWN_LIST:
            case SdtType.COMBO_BOX:
            case SdtType.DATE:
            case SdtType.BUILDING_BLOCK_GALLERY:
            case SdtType.GROUP:
            case SdtType.PICTURE:
            case SdtType.RICH_TEXT:
            case SdtType.PLAIN_TEXT:
            case SdtType.REPEATING_SECTION:
            case SdtType.REPEATING_SECTION_ITEM:
            case SdtType.CITATION:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns true if this node type can be created at this markup level as per MS Word implementation.
    /// </summary>
    private static boolean isMarkupLevelAllowedForCreation(/*SdtType*/int type, /*MarkupLevel*/int level)
    {
        boolean result;
        switch (level)
        {
            case MarkupLevel.INLINE:
                result =
                    (type != SdtType.REPEATING_SECTION) &&
                    (type != SdtType.REPEATING_SECTION_ITEM);
                break;
            case MarkupLevel.BLOCK:
            case MarkupLevel.CELL:
                result = true;
                break;
            case MarkupLevel.ROW:
                result =
                    (type == SdtType.RICH_TEXT) ||
                    (type == SdtType.REPEATING_SECTION) ||
                    (type == SdtType.REPEATING_SECTION_ITEM) ||
                    (type == SdtType.GROUP);
                break;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "level");
        }

        return result;
    }

    /// <summary>
    /// If Sdt needs placeholder then request a placeholder (new or reused) from manager.
    /// </summary>
    private void createPlaceholderIfNeeded()
    {
        BuildingBlock placeholder = getDocument().getSdtPlaceholderManager().fetchPlaceholderByType(getSdtType());
        if (placeholder != null) // if SDT actually needs placeholder.
        {
            mPlaceholder = placeholder;
            mPlaceholderName = placeholder.getName();

            if (mLevel != MarkupLevel.ROW) // for row-level SDT Word does not show placeholder.
                mIsShowingPlcHdr = true;
        }
    }

    /// <summary>
    /// Used in all user exposed properties to verify existence of custom-property bag.
    /// </summary>
    private void assertNonEmptyControlProperties()
    {
        if (getControlProperties() == null)
            throw new IllegalStateException(ERROR_PLEASE_REPORT);
    }

    /// <summary>
    /// Creates control specific pr bags out of range allowed by <see cref="IsTypeAllowedForCreation"/>
    /// </summary>
    private SdtControlProperties create(/*SdtType*/int type, /*MarkupLevel*/int level)
    {
        if (!isTypeAllowedForCreation(type))
            throw new IllegalArgumentException("Creation of such SdtType is not allowed.");

        if (!isMarkupLevelAllowedForCreation(type, level))
            throw new IllegalArgumentException("Can not create such SdtType at this level.");

        switch (type)
        {
            case SdtType.CHECKBOX:
                return new SdtCheckBox();
            case SdtType.DROP_DOWN_LIST:
            case SdtType.COMBO_BOX:
            {
                SdtDropDownListBase controlProperties = (type == SdtType.DROP_DOWN_LIST)
                    ? (SdtDropDownListBase)new SdtDropDownList()
                    : new SdtComboBox();

                controlProperties.getListItems().setParentSdt(this);
                return controlProperties;
            }
            case SdtType.DATE:
                return new SdtDate();
            case SdtType.BUILDING_BLOCK_GALLERY:
                return new SdtBuildingBlockGallery();
            case SdtType.GROUP:
                return new SdtGroup();
            case SdtType.PICTURE:
                return new SdtPicture();
            case SdtType.RICH_TEXT:
                return new SdtText(true);
            case SdtType.PLAIN_TEXT:
                return new SdtText(false);
            case SdtType.REPEATING_SECTION:
                return new SdtRepeatingSection();
            case SdtType.REPEATING_SECTION_ITEM:
                return new SdtRepeatingSectionItem();
            case SdtType.CITATION:
                return new SdtCitation();
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "type");
        }
    }

    /// <summary>
    /// Returns either style itself if style is character or linked character style. Otherwise returns null.
    /// </summary>
    private static Style getCharacterStyle(Style style)
    {
        if (style.getType() == StyleType.CHARACTER)
            return style;

        Style linkedStyle = style.getStyles().getByIstd(style.getLinkedIstd(), false);

        if ((linkedStyle != null) && (linkedStyle.getType() == StyleType.CHARACTER))
            return linkedStyle;

        return null;
    }

    /// <summary>
    /// Returns <see cref="NodeType.StructuredDocumentTag"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.STRUCTURED_DOCUMENT_TAG; }

    /// <summary>
    /// Gets the <see cref="BuildingBlock"/> containing placeholder text which should be displayed when this SDT run contents are empty,
    /// the associated mapped XML element is empty as specified via the <see cref="XmlMapping"/> element
    /// or the <see cref="IsShowingPlaceholderText"/> element is <c>true</c>.
    /// </summary>
    /// <remarks>Can be <c>null</c>, meaning that the placeholder is not applicable for this Sdt.</remarks>
    public BuildingBlock getPlaceholder() { return mPlaceholder; }

    /// <summary>
    /// <para>Gets or sets Name of the <see cref="BuildingBlock"/> containing placeholder text.</para>
    /// </summary>
    /// <exception cref="InvalidOperationException">Throw if BuildingBlock with this name <see cref="BuildingBlock.Name"/> is not present in <see cref="Document.GlossaryDocument"/>.</exception>
    public String getPlaceholderName() { return mPlaceholderName; }
    public void setPlaceholderName(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        setPlaceholderNameCore(value);
        BuildingBlock attachedBlock = getDocument().getSdtPlaceholderManager().findPlaceholder(this, false);
        if (attachedBlock != null) // found placeholder in the glossary
        {
            mPlaceholder = attachedBlock;

            // populate content from custom placeholder to default text inside Sdt.
            if (isShowingPlaceholderText() || getXmlMapping().isEmpty() || !getXmlMapping().isValid())
            {
                // clean the contents and populate with new content from placeholder.
                removeAllChildren();
                SdtContentHelper.insertDefaultContent(this, false);
            }
        }
        else
            throw new IllegalStateException("BuildingBlock with such Name does not exist in the document glossary.");
    }

    /// <summary>
    /// Gets the level at which this <b>SDT</b> occurs in the document tree.
    /// </summary>
    public /*MarkupLevel*/int getLevel() { return mLevel; }

    /// <summary>
    /// Gets type of this <b>Structured document tag</b>.
    /// </summary>
    public /*SdtType*/int getSdtType()
    {
        Debug.assertTrue(getControlProperties() != null);
        return getControlProperties().getType();
    }

    /// <summary>
    /// <para>Specifies a unique read-only persistent numerical Id for this <b>SDT</b>.</para>
    /// </summary>
    /// <remarks>
    /// <para> Id attribute shall follow these rules:
    /// <list type="bullet">
    /// <item>The document shall retain SDT ids only if the whole document is cloned <see cref="Document.Clone()"/>.</item>
    /// <item>During <see cref="DocumentBase.ImportNode(Aspose.Words.Node,bool)"/>
    /// Id shall be retained if import does not cause conflicts with other SDT Ids in
    /// the target document.</item>
    /// <item>
    /// If multiple SDT nodes specify the same decimal number value for the Id attribute,
    /// then the first SDT in the document shall maintain this original Id,
    /// and all subsequent SDT nodes shall have new identifiers assigned to them when the document is loaded.
    /// </item>
    /// <item>During standalone SDT <see cref="StructuredDocumentTag.Clone"/> operation new unique ID will be generated for the cloned SDT node.</item>
    /// <item>
    /// If Id is not specified in the source document, then the SDT node shall have a new unique identifier assigned
    /// to it when the document is loaded.
    /// </item>
    /// </list>
    /// </para>
    /// </remarks>
    public int getId()
    {
        Debug.assertTrue(mId != UNUSED_SDT_ID);
        return mId;
    }

    /// <summary>
    /// When set to <c>true</c>, this property will prohibit a user from deleting this <b>SDT</b>.
    /// </summary>
    public boolean getLockContentControl() { return mLockContentControl; }
    public void setLockContentControl(boolean value) { mLockContentControl = value; }

    /// <summary>
    /// When set to <c>true</c>, this property will prohibit a user from editing the contents of this <b>SDT</b>.
    /// </summary>
    public boolean getLockContents() { return mLockContents; }
    public void setLockContents(boolean value) { mLockContents = value; }

    /// <summary>
    /// <para>
    /// Specifies whether the content of this <b>SDT</b> shall be interpreted to contain placeholder text
    /// (as opposed to regular text contents within the SDT).
    /// </para>
    /// <para>
    /// if set to <c>true</c>, this state shall be resumed (showing placeholder text) upon opening this document.
    /// </para>
    /// </summary>
    public boolean isShowingPlaceholderText() { return mIsShowingPlcHdr; }
    public void isShowingPlaceholderText(boolean value) { mIsShowingPlcHdr = value; }

    /// <summary>
    /// Specifies a tag associated with the current SDT node.
    /// Can not be <c>null</c>.
    /// </summary>
    /// <remarks> A tag is an arbitrary string which applications can associate with SDT
    ///  in order to identify it without providing a visible friendly name.</remarks>
    public String getTag() { return mTag; }
    public void setTag(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mTag = value;
    }

    /// <summary>
    /// Font formatting that will be applied to text entered into <b>SDT</b>.
    /// </summary>
    public Font getContentsFont()
    {
        if (mContentsFont == null)
            mContentsFont = new Font(this, getDocument());

        return mContentsFont;
    }

    /// <summary>
    /// Font formatting that will be applied to the last character of text entered into <b>SDT</b>.
    /// </summary>
    public Font getEndCharacterFont()
    {
        if (mEndCharacterFont == null)
            mEndCharacterFont = new Font(getEndCharacterRunPr(), getDocument());

        return mEndCharacterFont;
    }

    /// <summary>
    /// Specifies whether this <b>SDT</b> shall be removed from the WordProcessingML document when its contents
    /// are modified.
    /// </summary>
    public boolean isTemporary() { return mIsTemporary; }
    public void isTemporary(boolean value) { mIsTemporary = value; }

    /// <summary>
    /// Specifies the friendly name associated with this <b>SDT</b>.
    /// Can not be <c>null</c>.
    /// </summary>
    public String getTitle() { return mTitle; }
    public void setTitle(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mTitle = value;
    }

    /// <summary>
    /// Gets <see cref="SdtListItemCollection"/> associated with this <b>SDT</b>.
    /// </summary>
    /// <remarks>
    /// <para>Accessing this property will only work for <see cref="Markup.SdtType.ComboBox"/> or <see cref="Markup.SdtType.DropDownList"/>
    /// SDT types.
    /// </para>
    /// <para>For all other SDT types exception will occur.</para>
    /// </remarks>
    public SdtListItemCollection getListItems()
    {
        validateUsage((getSdtType() == SdtType.COMBO_BOX) || (getSdtType() == SdtType.DROP_DOWN_LIST), ERROR_LIST_ITEMS);
        return ((SdtDropDownListBase)getControlProperties()).getListItems();
    }

    /// <summary>
    /// Gets/Sets current state of the Checkbox <b>SDT</b>.
    /// Default value for this property is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>Accessing this property will only work for <see cref="Markup.SdtType.Checkbox"/>
    /// SDT types.
    /// </para>
    /// <para>For all other SDT types exception will occur.</para>
    /// </remarks>
    public boolean getChecked()
    {
        validateUsage(getSdtType() == SdtType.CHECKBOX, ERROR_CHECKBOX_CHECKED);
        return ((SdtCheckBox)getControlProperties()).getChecked();
    }
    public void setChecked(boolean value) throws Exception
    {
        validateUsage(getSdtType() == SdtType.CHECKBOX, ERROR_CHECKBOX_CHECKED);
        ((SdtCheckBox)getControlProperties()).setChecked(value);

        updateCheckboxContent(value);
    }

    /// <summary>
    /// Gets/sets the appearance of a structured document tag.
    /// </summary>
    public /*SdtAppearance*/int getAppearance() { return mAppearance; }
    public void setAppearance(/*SdtAppearance*/int value)
    {
        mAppearance = value;

        SdtContentUpdater.updateNonBoundDataContent(this);
    }

    /// <summary>
    /// Sets appearance without updating SDT content.
    /// </summary>
    void setAppearanceInternal(/*SdtAppearance*/int appearance)
    {
        mAppearance = appearance;
    }

    /// <summary>
    /// Gets current state of the content of the Checkbox <b>SDT</b>.
    /// </summary>
    /// <remarks>
    /// <para>Accessing this property will only work for <see cref="Markup.SdtType.Checkbox"/> SDT types.</para>
    /// <para>For all other SDT types exception will occur.</para>
    /// </remarks>
    boolean getContentChecked()
    {
        validateUsage(getSdtType() == SdtType.CHECKBOX, ERROR_CHECKBOX_CONTENT_CHECKED);

        Run firstRun = ms.as(getChild(NodeType.RUN, 0, true), Run.class);
        if (firstRun != null)
        {
            String characterText = firstRun.getText();
            if (characterText.length() == 1)
            {
                int characterCode = characterText.charAt(0);
                SdtCheckBox checkBox = (SdtCheckBox)getControlProperties();

                if (characterCode == checkBox.getCheckedStateInfo().getCharacterCode())
                    return true;

                if (characterCode == checkBox.getUncheckedStateInfo().getCharacterCode())
                    return false;
            }
        }

        // AM. I think it'd better do not throw here.
        return false;
    }

    /// <summary>
    /// Allows to set/get the language format for the date displayed in this <b>SDT</b>.
    /// </summary>
    /// <remarks>
    /// <para>Accessing this property will only work for <see cref="Markup.SdtType.Date"/> SDT type.
    /// </para>
    /// <para>For all other SDT types exception will occur.</para>
    /// </remarks>
    public int getDateDisplayLocale()
    {
        validateUsage(getSdtType() == SdtType.DATE, ERROR_DATE_DISPLAY_LOCALE);
        return ((SdtDate)getControlProperties()).getLid();
    }
    public void setDateDisplayLocale(int value)
    {
        validateUsage(getSdtType() == SdtType.DATE, ERROR_DATE_DISPLAY_LOCALE);
        ((SdtDate)getControlProperties()).setLid(value);

        SdtContentUpdater.updateNonBoundDataContent(this);
    }

    /// <summary>
    /// String that represents the format in which dates are displayed.
    /// </summary>
    /// <remarks>
    /// <para>Can not be <c>null</c>.</para>
    /// <para>The dates for English (U.S.) is "mm/dd/yyyy"</para>
    /// <para>Accessing this property will only work for <see cref="Markup.SdtType.Date"/> SDT type.</para>
    /// <para>For all other SDT types exception will occur.</para>
    /// </remarks>
    public String getDateDisplayFormat()
    {
        validateUsage(getSdtType() == SdtType.DATE, ERROR_DATE_DISPLAY_FORMAT);
        return ((SdtDate)getControlProperties()).getDateFormat();
    }
    public void setDateDisplayFormat(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        validateUsage(getSdtType() == SdtType.DATE, ERROR_DATE_DISPLAY_FORMAT);
        ((SdtDate)getControlProperties()).setDateFormat(value);

        SdtContentUpdater.updateNonBoundDataContent(this);
    }

    /// <summary>
    /// Specifies the full date and time last entered into this <b>SDT</b>.
    /// </summary>
    /// <remarks>
    /// <para>Accessing this property will only work for <see cref="Markup.SdtType.Date"/> SDT type.
    /// </para>
    /// <para>For all other SDT types exception will occur.</para>
    /// </remarks>
    //JAVA: property accessor is internalized
	DateTime getFullDateInternal()
    {
        validateUsage(getSdtType() == SdtType.DATE, ERROR_FULL_DATE);
        return ((SdtDate)getControlProperties()).getFullDate();
    }
	//JAVA-added public wrapper for internalized property accessor
	public java.util.Date getFullDate() { return DateTime.toJava(getFullDateInternal()); }
    //JAVA: property accessor is internalized
	void setFullDateInternal(DateTime value)
    {
        validateUsage(getSdtType() == SdtType.DATE, ERROR_FULL_DATE);
        ((SdtDate)getControlProperties()).setFullDate(value);

        SdtContentUpdater.updateNonBoundDataContent(this);
    }
	//JAVA-added public wrapper for internalized property accessor
	public void setFullDate(java.util.Date value) { setFullDateInternal(DateTime.fromJava(value)); }

    /// <summary>
    /// Gets/sets format in which the date for a date SDT is stored when the <b>SDT</b> is bound to an XML node in the document's data store.
    /// Default value is <see cref="SdtDateStorageFormat.DateTime"/>
    /// </summary>
    /// <remarks>
    /// <para>Accessing this property will only work for <see cref="Markup.SdtType.Date"/> SDT type.
    /// </para>
    /// <para>For all other SDT types exception will occur.</para>
    /// </remarks>
    public /*SdtDateStorageFormat*/int getDateStorageFormat()
    {
        validateUsage(getSdtType() == SdtType.DATE, ERROR_DATE_STORAGE_FORMAT);
        return ((SdtDate)getControlProperties()).getStoreMappedDataAs();
    }
    public void setDateStorageFormat(/*SdtDateStorageFormat*/int value)
    {
        validateUsage(getSdtType() == SdtType.DATE, ERROR_DATE_STORAGE_FORMAT);
        ((SdtDate)getControlProperties()).setStoreMappedDataAs(value);

        SdtContentUpdater.updateNonBoundDataContent(this);
    }

    /// <summary>
    /// Specifies the type of calendar for this <b>SDT</b>.
    /// Default is <see cref="SdtCalendarType.Default"/>
    /// </summary>
    /// <remarks>
    /// <para>Accessing this property will only work for <see cref="Markup.SdtType.Date"/> SDT type.
    /// </para>
    /// <para>For all other SDT types exception will occur.</para>
    /// </remarks>
    public /*SdtCalendarType*/int getCalendarType()
    {
        validateUsage(getSdtType() == SdtType.DATE, ERROR_CALENDAR_TYPE);
        return ((SdtDate)getControlProperties()).getCalendarType();
    }
    public void setCalendarType(/*SdtCalendarType*/int value)
    {
        validateUsage(getSdtType() == SdtType.DATE, ERROR_CALENDAR_TYPE);
        ((SdtDate)getControlProperties()).setCalendarType(value);

        SdtContentUpdater.updateNonBoundDataContent(this);
    }

    /// <summary>
    /// Specifies type of building block for this <b>SDT</b>.
    /// Can not be <c>null</c>.
    /// </summary>
    /// <remarks>
    /// <para>Accessing this property will only work for <see cref="Markup.SdtType.BuildingBlockGallery"/> and
    /// <see cref="Markup.SdtType.DocPartObj"/> SDT types. It is read-only for <b>SDT</b> of the document part type.
    /// </para>
    /// <para>For all other SDT types exception will occur.</para>
    /// </remarks>
    public String getBuildingBlockGallery()
    {
        // WORDSNET-15972 Provide ability to obtain document part gallery filter from "docPartObj" SDT.
        validateUsage(isSdtDocPart(), ERROR_BUILDING_BLOCK_TYPE);
        return ((SdtDocPart)getControlProperties()).getBuildingBlockType();
    }
    public void setBuildingBlockGallery(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        validateUsage(getSdtType() == SdtType.BUILDING_BLOCK_GALLERY, ERROR_BUILDING_BLOCK_TYPE);
        ((SdtDocPart)getControlProperties()).setBuildingBlockType(value);
    }

    /// <summary>
    /// Specifies category of building block for this <b>SDT</b> node.
    /// Can not be <c>null</c>.
    /// </summary>
    /// <remarks>
    /// <para>Accessing this property will only work for <see cref="Markup.SdtType.BuildingBlockGallery"/> and
    /// <see cref="Markup.SdtType.DocPartObj"/> SDT types. It is read-only for <b>SDT</b> of the document part type.
    /// </para>
    /// <para>For all other SDT types exception will occur.</para>
    /// </remarks>
    public String getBuildingBlockCategory()
    {
        validateUsage(isSdtDocPart(), ERROR_BUILDING_BLOCK_CATEGORY);
        return ((SdtDocPart)getControlProperties()).getBuildingBlockCategory();
    }
    public void setBuildingBlockCategory(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        validateUsage(getSdtType() == SdtType.BUILDING_BLOCK_GALLERY, ERROR_BUILDING_BLOCK_CATEGORY);
        ((SdtDocPart)getControlProperties()).setBuildingBlockCategory(value);
    }

    /// <summary>
    /// Specifies whether this <b>SDT</b> allows multiple lines of text.
    /// </summary>
    /// <remarks>
    /// <para>Accessing this property will only work for <see cref="Markup.SdtType.RichText"/> and <see cref="Markup.SdtType.PlainText"/> SDT type.
    /// </para>
    /// <para>For all other SDT types exception will occur.</para>
    /// </remarks>
    public boolean getMultiline()
    {
        validateUsage((getSdtType() == SdtType.RICH_TEXT) || (getSdtType() == SdtType.PLAIN_TEXT), ERROR_MULTILINE);
        return ((SdtText)getControlProperties()).isMultiline();
    }
    public void setMultiline(boolean value)
    {
        validateUsage((getSdtType() == SdtType.RICH_TEXT) || (getSdtType() == SdtType.PLAIN_TEXT), ERROR_MULTILINE);
        ((SdtText)getControlProperties()).isMultiline(value);
    }

    /// <summary>
    /// Gets or sets the color of the structured document tag.
    /// </summary>
    public Color getColor()
    {
        return (getBaseColor() != null) ? getBaseColor().toNativeColor(): (DrColor.EMPTY).toNativeColor();
    }
    public void setColor(Color value)
    {
        setBaseColor(DrColor.fromNativeColor(value));
    }

    /// <summary>
    /// Gets or sets the Style of the structured document tag.
    /// </summary>
    /// <remarks>
    /// Only <see cref="StyleType.Character" /> style or <see cref="StyleType.Paragraph" /> style with linked character style can be set.
    /// </remarks>
    /// <dev>
    /// It's ridiculous that Word uses character styles internally but exposes
    /// linked paragraph styles in GUI and VBA. Anyway, we do the same.
    /// </dev>
    public Style getStyle()
    {
        Style style = getDocument().getStyles().fetchByIstd(mContentsRunPr.getIstd(), StyleIndex.DEFAULT_PARAGRAPH_FONT);
        if (style.getIstd() == StyleIndex.DEFAULT_PARAGRAPH_FONT)
            return style;

        Style linkedStyle = style.getStyles().getByIstd(style.getLinkedIstd(), false);

        if (linkedStyle != null && linkedStyle.getType() == StyleType.PARAGRAPH)
            return linkedStyle;

        if (linkedStyle == null && style.getType() == StyleType.CHARACTER)
            return style;

        return null;
    }
    public void setStyle(Style value)
    {
        if (value == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "value");

        if (value.getDocument() != getDocument())
            throw new IllegalArgumentException("This style belongs to a different document.");

        Style characterStyle = getCharacterStyle(value);
        if (characterStyle == null)
            throw new IllegalStateException(
                "Cannot apply this style to the SDT: Style should be a character or a linked one.");

        int styleIstd = characterStyle.getIstd();

        // Setting DefaultParagraphFont in Word removes the info about the Style.
        if (characterStyle.getIstd() == StyleIndex.DEFAULT_PARAGRAPH_FONT)
            styleIstd = StyleIndex.NIL;

        mContentsRunPr.setIstd(styleIstd);
    }

    /// <summary>
    /// Gets or sets the name of the style applied to the structured document tag.
    /// </summary>
    public String getStyleName() { return getStyle().getName(); }
    public void setStyleName(String value) { setStyle(getDocument().getStyles().fetchByName(value)); }

    /// <summary>
    /// Gets/sets the color on which to base the visual elements of a structured document tag.
    /// </summary>
    DrColor getBaseColor() { return mBaseColor; }
    void setBaseColor(DrColor value) { mBaseColor = value; }

    /// <summary>
    /// Gets/sets the theme color on which to base the visual elements of a structured document tag.
    /// </summary>
    String getThemeColor() { return mThemeColor; }
    void setThemeColor(String value) { mThemeColor = value; }

    /// <summary>
    /// Gets/sets the shade value applied to the supplied <see cref="ThemeColor"/>.
    /// </summary>
    String getThemeShade() { return mThemeShade; }
    void setThemeShade(String value) { mThemeShade = value; }

    /// <summary>
    /// Gets/sets the tint value applied to the supplied <see cref="ThemeColor"/>.
    /// </summary>
    String getThemeTint() { return mThemeTint; }
    void setThemeTint(String value) { mThemeTint = value; }

    /// <summary>
    /// Specifies that the parent structured document tag is a repeated section.
    /// </summary>
    boolean isRepeatingSection() { return (getSdtType() == SdtType.REPEATING_SECTION); }

    /// <summary>
    /// Specifies that the parent structured document tag is a repeated section item.
    /// </summary>
    boolean isRepeatingSectionItem() { return (getSdtType() == SdtType.REPEATING_SECTION_ITEM); }

    /// <summary>
    /// Specifies a relationship between the structured document tag and an Office Web Extension (webExtension):
    /// if the structured document tag was created by, and/or is bound to, at least one webExtension.
    /// </summary>
    /// <dev>
    /// §2.5.1.12 webExtensionCreated and §2.5.1.13 webExtensionLinked of [MS-DOCX].
    /// </dev>
    /*SdtWebExtensionRelationship*/int getWebExtensionRelationship() { return mWebExtensionRelationship; }
    void setWebExtensionRelationship(/*SdtWebExtensionRelationship*/int value) { mWebExtensionRelationship = value; }

    /// <summary>
    /// Specifies the label identifier associated with the current structured document tag.
    /// </summary>
    /// <remarks>
    /// The contents of the structured document tag resolved by a Label unique identifier
    /// shall be used as the label content.</remarks>
    int getLabel() { return mLabel; }
    void setLabel(int value)
    {
        mLabel = value;
        mIsLabelDefined = true;
    }

    /// <summary>
    /// Returns <c>true</c> if <see cref="Label"/> is defined for this <b>SDT</b>.
    /// </summary>
    boolean isLabelDefined() { return mIsLabelDefined; }

    /// <summary>
    /// Specifies the position of the current structured document tag in the navigation (tab) order used in the document.
    /// </summary>
    /// <remarks>
    /// <para>TabIndex shall is analogous to the tabIndex attribute in HTML.</para>
    /// <para>Objects that support tab index shall be navigated by consumers in the following order:
    /// <list type="bullet">
    /// <item><description>
    /// Objects for which the XML specifies a non-zero TabIndex value are navigated first.
    /// Navigation proceeds with the element with the lowest resolved value of tabIndex to the element with the highest
    /// resolved value of tabIndex.
    /// </description></item>
    /// <item><description>
    /// Objects that specify identical resolved values of TabIndex is navigated in the lexical order in which
    /// the elements appear in the underlying WordprocessingML.
    /// </description></item>
    /// <item><description>
    /// Objects for which the XML does not specify an index or objects for which the XML specifies a resolved
    /// TabIndex value of 0 are navigated last. These objects are navigated in the lexical order in which they
    /// appear in the underlying WordprocessingML.
    /// </description></item>
    /// </list>
    /// </para>
    /// </remarks>
    int getTabIndex() { return mTabIndex; }
    void setTabIndex(int value) { mTabIndex = value; }

    /// <summary>
    /// Run Properties For Structured Document Tag Contents. Specifies the set of run properties which shall be applied
    /// to the text entered into the parent structured document tag in replacement of placeholder text.
    /// </summary>
    RunPr getContentsRunPr() { return mContentsRunPr; }
    void setContentsRunPr(RunPr value) { mContentsRunPr = value; }

    /// <summary>
    /// This element specifies the set of run properties which shall be applied to the character present to delimit the
    /// end of the structured document tag's contents. When these properties are applied, they shall be applied in
    /// addition to the run properties specified for the entire structured document tag via the <see cref="ContentsRunPr"/>
    /// element stored in the tag's main property container.
    /// </summary>
    RunPr getEndCharacterRunPr() { return mEndCharRunPr; }
    void setEndCharacterRunPr(RunPr value) { mEndCharRunPr = value; }

    /// <summary>
    /// Gets an object that represents the mapping of this structured document tag to XML data
    /// in a custom XML part of the current document.
    /// </summary>
    /// <remarks>
    /// You can use the <see cref="Markup.XmlMapping.SetMapping(CustomXmlPart,string,string)"/> method of this object to map
    /// a structured document tag to XML data.
    /// </remarks>
    /// <dev>
    /// If this element is present and the parent Sdt is not of a rich text type, then the current
    /// value of the Sdt shall be determined by finding the XML element (if any) which is
    /// determined by the attributes on this element.
    /// See Iso29500, chapter 1, 17.5.2.6 dataBinding (XML Mapping).
    /// If DataBinding information does not result in an XML element, then the
    /// application can use any algorithm desired to find the closest available match. If this information does result in an
    /// XML element, then the contents of that element shall be used to replace the current run content within the
    /// document.
    /// </dev>
    public XmlMapping getXmlMapping() { return mXmlMapping; }

    /// <summary>
    /// Gets a string that represents the XML contained within the node in the <see cref="SaveFormat.FlatOpc"/> format.
    /// </summary>
    public String getWordOpenXML() throws Exception
    {
        OpcDocumentFragmentWriter writer = new OpcDocumentFragmentWriter();
        return writer.saveToString(this);
    }

    /// <summary>
    /// Gets a string that represents the XML contained within the node in the <see cref="SaveFormat.FlatOpc"/> format.
    ///
    /// Unlike the <see cref="WordOpenXML"/> property, this method generates a stripped-down document that excludes any non-content-related parts.
    /// </summary>
    /// <dev>
    /// This is experimental API to understand uncertain needs of important customer.
    /// Later we could try rework it using Node.ToString() pattern.
    /// </dev>
    public String getWordOpenXMLMinimal() throws Exception
    {
        OpcDocumentFragmentWriter writer = new OpcDocumentFragmentWriter(true);
        return writer.saveToString(this);
    }

    /// <summary>
    /// Encapsulates all differences between different type of SDT-based content controls.
    /// </summary>
    SdtControlProperties getControlProperties() { return mControlProperties; }
    void setControlProperties(SdtControlProperties value) { mControlProperties = value; }

    /// <summary>
    /// Returns <c>true</c>, if content control was modified and should be updated.
    /// </summary>
    boolean getNeedToUpdateContent() { return ((getSdtType() == SdtType.DATE) && ((SdtDate)getControlProperties()).getNeedToUpdateContent()); }

    /// <summary>
    /// Returns <c>true</c>, if this SDT uses extensions defined in [MS-DOCX] Word Extensions to the Office Open XML
    /// (.docx) File Format.
    /// </summary>
    boolean isDocxExtension()
    {
        return
            (getSdtType() == SdtType.CHECKBOX) ||
            (getSdtType() == SdtType.ENTITY_PICKER) ||
            isRepeatingSection() ||
            isRepeatingSectionItem() ||
            (getAppearance() != SdtAppearance.DEFAULT) ||
            (getWebExtensionRelationship() != SdtWebExtensionRelationship.NONE) ||
            (getBaseColor() != null) ||
            (getThemeColor() != null) ||
            getXmlMapping().isDocx15Extension();
    }

    /// <summary>
    /// Adds an empty para to SDT content if needed to make document valid from MSW point of view.
    /// </summary>
    void ensureCorrectLastChild()
    {
        Node lastChild = getLastNonAnnotationChild();

        // WORDSNET-18147 A rare case when a SDT content's last child is a specific table.
        if ((lastChild != null) && (lastChild.getNodeType() == NodeType.TABLE))
        {
            Table table = (Table)lastChild;

            if ((table.getRows().getCount() > 0) && (table.getLastRow().getCells().getCount() == 1) &&
                (table.getLastRow().getFirstCell().getCellPr().isMergedToPrevious()))
            {
                appendChild(new Paragraph(getDocument()));
            }
        }
    }

    /// <summary>
    /// Returns <c>true</c> if this SDT contains any non-markup (<see cref="NodeUtil.IsMarkupNode(Words.NodeType)"/>)
    /// non-annotation (<see cref="NodeUtil.IsCrossStructureAnnotation(Node)"/>) descendant.
    /// </summary>
    private boolean hasNonMarkupNonAnnotationDescendant()
    {
        Node node = nextPreOrder(this);
        while ((node != null) &&
            (NodeUtil.isMarkupNode(node) || NodeUtil.isCrossStructureAnnotation(node)))
            node = node.nextPreOrder(this);

        return node != null;
    }

    @ReservedForInternalUse @Deprecated
    public /*MarkupLevel*/int /*IMarkupNode.*/getLevel_IMarkupNode() { return getLevel(); }

    @ReservedForInternalUse @Deprecated
    public EditRevision /*ITrackableNode.*/getInsertRevision() { return getContentsRunPr().getInsertRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*ITrackableNode.*/setInsertRevision(EditRevision value) { getContentsRunPr().setInsertRevision(value); }

    @ReservedForInternalUse @Deprecated
    public EditRevision /*ITrackableNode.*/getDeleteRevision() { return getContentsRunPr().getDeleteRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*ITrackableNode.*/setDeleteRevision(EditRevision value) { getContentsRunPr().setDeleteRevision(value); }

    @ReservedForInternalUse @Deprecated
    public MoveRevision /*IMoveTrackableNode.*/getMoveFromRevision() { return getContentsRunPr().getMoveFromRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*IMoveTrackableNode.*/setMoveFromRevision(MoveRevision value) { getContentsRunPr().setMoveFromRevision(value); }

    @ReservedForInternalUse @Deprecated
    public MoveRevision /*IMoveTrackableNode.*/getMoveToRevision() { return getContentsRunPr().getMoveToRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*IMoveTrackableNode.*/setMoveToRevision(MoveRevision value) { getContentsRunPr().setMoveToRevision(value); }

    @ReservedForInternalUse @Deprecated
    public void /*IMoveTrackableNode.*/removeMoveRevisions()
    {
        getContentsRunPr().remove(RevisionAttr.MOVE_FROM_REVISION);
        getContentsRunPr().remove(RevisionAttr.MOVE_TO_REVISION);
    }

    /// <summary>
    /// Indicates that current SDT is the document part type or the document part gallery type.
    /// </summary>
    boolean isSdtDocPart() { return (getSdtType() == SdtType.BUILDING_BLOCK_GALLERY) || (getSdtType() == SdtType.DOC_PART_OBJ); }

    /// <summary>
    /// Returns value of GetText() method without breaks.
    /// </summary>
    String getContentValue() { return msString.trim(getText(), G_BREAKS); }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key)
    {
        return mContentsRunPr.get(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return mContentsRunPr.getDirectAttr(key, revisionsView);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/fetchInheritedRunAttr(int key)
    {
        return mContentsRunPr.fetchInheritedAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/setRunAttr(int key, Object value)
    {
        mContentsRunPr.setAttr(key, value);

        // WORDSNET-15602 Sets attribute for both RunPr collections - SDT and SDT Content.
        if (getSdtType() == SdtType.CHECKBOX)
        {
            setCheckboxRunAttr(key, value);
        }
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/removeRunAttr(int key)
    {
        mContentsRunPr.remove(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/clearRunAttrs()
    {
        mContentsRunPr.clear();
    }

    private void setCheckboxRunAttr(int key, Object value)
    {
        Run curRun = (Run)getChild(NodeType.RUN, 0, true);
        if (curRun == null)
        {
            SdtContentHelper.insertDefaultContent(this, false);
            curRun = (Run)getChild(NodeType.RUN, 0, true);
        }
        curRun.getRunPr().setAttr(key, value);
        SdtCheckBox checkBox = (SdtCheckBox)getControlProperties();
        curRun.getRunPr().setName(checkBox.getChecked() ?
            checkBox.getCheckedStateInfo().getFontName() :
            checkBox.getUncheckedStateInfo().getFontName());
    }

    private String mPlaceholderName;

    private /*MarkupLevel*/int mLevel;
    private SdtControlProperties mControlProperties;
    private XmlMapping mXmlMapping;

    /// <summary>
    /// See Iso29500, chapter 1, 17.5.2.19 label (Structured Document Tag Label)
    /// </summary>
    private int mLabel;
    private boolean mIsLabelDefined;

    /// <summary>
    /// See Iso29500, chapter 1, 17.5.2.18 id (Unique ID)
    /// </summary>
    private int mId = UNUSED_SDT_ID;

    /// <summary>
    /// See Iso29500, chapter 1, 17.5.2.1 alias (Friendly Name)
    /// </summary>
    private String mTitle = "";

    /// <summary>
    /// See Iso29500, chapter 1, 17.5.2.39 showingPlcHdr (Current Contents Are Placeholder Text)
    /// </summary>
    private boolean mIsShowingPlcHdr;

    /// <summary>
    /// See Iso29500, chapter 1, 17.5.2.41 tabIndex (Structured Document Tag Navigation Order Index)
    /// </summary>
    private int mTabIndex;

    /// <summary>
    /// See Iso29500, chapter 1, 17.5.2.42 tag (Programmatic Tag)
    /// </summary>
    private String mTag = "";

    /// <summary>
    /// See Iso29500, chapter 1, 17.5.2.43 temporary (Remove Structured Document Tag When Contents Are Edited)
    /// </summary>
    private boolean mIsTemporary;

    /// <summary>
    /// See Iso29500, chapter 1, 17.5.2.27 rPr (Run Properties For Structured Document Tag Contents)
    /// </summary>
    private RunPr mContentsRunPr;

    /// <summary>
    ///  See Iso29500, chapter 1, 17.5.2.28 rPr (Structured Document Tag End Character Run Properties)
    /// </summary>
    private RunPr mEndCharRunPr;

    /// <summary>
    ///  See Iso29500, chapter 1, 17.5.2.25 placeholder (Structured Document Tag Placeholder Text)
    /// </summary>
    private BuildingBlock mPlaceholder;

    private boolean mLockContentControl;

    private boolean mLockContents;

    private Font mContentsFont;

    private Font mEndCharacterFont;

    private DrColor mBaseColor;
    private String mThemeColor;
    private String mThemeShade;
    private String mThemeTint;

    private /*SdtAppearance*/int mAppearance;
    private /*SdtWebExtensionRelationship*/int mWebExtensionRelationship;

    static final String COVER_PAGE_BUILDING_BLOCK_GALLERY = "Cover Pages";

    /// <summary>
    /// By default we will use this as indicator that Id has not been set for SDT Id <see cref="Id"/>.
    /// </summary>
    /// <remarks>There </remarks>
    private static final int UNUSED_SDT_ID = -1;

    private static final String SDT_TYPE = " SDT type.";
    private static final String SDT_TYPES = " SDT types.";
    private static final String ACCESSIBLE_FOR = " is only accessible for ";
    private static final String ERROR_DATE = ACCESSIBLE_FOR + "Date" + SDT_TYPE;
    private static final String ERROR_CHECKBOX = ACCESSIBLE_FOR + "Checkbox" + SDT_TYPE;
    private static final String ERROR_BUILDING_BLOCK = ACCESSIBLE_FOR + "BuildingBlockGallery" + SDT_TYPE;

    private static final String ERROR_FULL_DATE = "FullDate" + ERROR_DATE;
    private static final String ERROR_CALENDAR_TYPE = "CalendarType" + ERROR_DATE;
    private static final String ERROR_DATE_DISPLAY_LOCALE = "DateDisplayLocale" + ERROR_DATE;
    private static final String ERROR_DATE_DISPLAY_FORMAT = "DateDisplayFormat" + ERROR_DATE;
    private static final String ERROR_DATE_STORAGE_FORMAT = "DateStorageFormat" + ERROR_DATE;
    private static final String ERROR_BUILDING_BLOCK_TYPE = "BuildingBlockType" + ERROR_BUILDING_BLOCK;
    private static final String ERROR_BUILDING_BLOCK_CATEGORY = "BuildingBlockCategory" + ERROR_BUILDING_BLOCK;
    private static final String ERROR_CHECKBOX_CHECKED = "Checked" + ERROR_CHECKBOX;
    private static final String ERROR_CHECKBOX_CONTENT_CHECKED = "ContentChecked" + ERROR_CHECKBOX;
    private static final String ERROR_CHECKBOX_SET_CHECKED_SYMBOL = "SetCheckedSymbol" + ERROR_CHECKBOX;
    private static final String ERROR_CHECKBOX_SET_UNCHECKED_SYMBOL = "SetUncheckedSymbol" + ERROR_CHECKBOX;

    private static final String ERROR_LIST_ITEMS = "ListItems" + ACCESSIBLE_FOR + "ComboBox or DropDownList" + SDT_TYPES;
    private static final String ERROR_MULTILINE = "Multiline" + ACCESSIBLE_FOR + "Richtext and Plaintext" + SDT_TYPES;

    private static final String ERROR_PLEASE_REPORT = "Please report exception.";


    private static /*final*/ char[] G_BREAKS = new char[]
        { ControlChar.CELL_CHAR, ControlChar.PARAGRAPH_BREAK_CHAR, ControlChar.SECTION_BREAK_CHAR };
}

