// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/01/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ArgumentUtil;
import com.aspose.Language;
import com.aspose.LocaleClassifier;


/// <summary>
/// Represents a Microsoft Word document field.
/// <para>To learn more, visit the <ms><see href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</see></ms><java><a href="https://docs.aspose.com/words/java/working-with-fields/">Working with Fields</a></java> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>A field in a Word document is a complex structure consisting of multiple nodes that include field start,
/// field code, field separator, field result and field end. Fields can be nested, contain rich content and span
/// multiple paragraphs or sections in a document. The <see cref="Field"/> class is a "facade" object that provides
/// properties and methods that allow to work with a field as a single object. </para>
///
/// <para>The <see cref="Start"/>, <see cref="Separator"/> and <see cref="End"/> properties point to the
/// field start, separator and end nodes of the field respectively.</para>
///
/// <para>The content between the field start and separator is the field code. The content between the
/// field separator and field end is the field result. The field code typically consists of one or more
/// <see cref="Run"/> objects that specify instructions. The processing application is expected to execute
/// the field code to calculate the field result.</para>
///
/// <para>The process of calculating field results is called the field update. Aspose.Words can update field
/// results of most of the field types in exactly the same way as Microsoft Word does it. Most notably,
/// Aspose.Words can calculate results of even the most complex formula fields. To calculate the field
/// result of a single field use the <see cref="Update()"/> method. To update fields in the whole document
/// use <see cref="Aspose.Words.Document.UpdateFields"/>.</para>
///
/// <para>You can get the plain text version of the field code using the <see cref="GetFieldCode(bool)"/> method.
/// You can get and set the plain text version of the field result using the <see cref="Result"/> property.
/// Both the field code and field result can contain complex content, such as nested fields, paragraphs, shapes,
/// tables and in this case you might want to work with the field nodes directly if you need more control.</para>
///
/// <para>You do not create instances of the <see cref="Field"/> class directly.
/// To create a new field use the <see cref="DocumentBuilder.InsertField(string)"/> method.</para>
///
/// </remarks>
public class Field
{
    protected Field()
    {
    }

    Field(FieldBundle bundle)
    {
        mBundle = bundle;
    }

    /// <summary>
    /// Initializes the object. Two stage construction because makes it simpler to implement a field factory.
    /// </summary>
    /*virtual*/ void initialize(FieldStart start, FieldSeparator separator, FieldEnd end)
    {
        mBundle = new FieldBundle(start, separator, end);
    }

    /// <summary>
    /// Parses field code and caches it. Inheritors may add parsing logic if more complex properties should be parsed.
    /// </summary>
    /*virtual*/ void parseFieldCode()
    {
        if (isUpdating())
            mUpdateContext.notifyFieldCodeParsing();

        mFieldCodeCache = new FieldCode(this);

        if (isUpdating())
            mUpdateContext.notifyFieldCodeParsed();
    }

    /// <summary>
    /// Clears the current field code cache making it to refresh on the next demand if any.
    /// </summary>
    void invalidateFieldCodeCache()
    {
        mFieldCodeCache = null;
    }

    /*virtual*/ void notifyChildFieldUpdated(IFieldArgument argument) throws Exception
    {
    }

    /// <summary>
    /// Ensures a field code cache for this field.
    /// </summary>
    void ensureFieldCodeCache()
    {
        if (!hasFieldCodeCache())
            parseFieldCode();
    }

    /// <summary>
    /// Returns text between field start and field separator (or field end if there is no separator).
    /// Both field code and field result of child fields are included.
    /// </summary>
    public String getFieldCode()
    {
        return getFieldCode(true);
    }

    /// <summary>
    /// Returns text between field start and field separator (or field end if there is no separator).
    /// </summary>
    /// <param name="includeChildFieldCodes">
    /// <c>true</c> if child field codes should be included.
    /// </param>
    public String getFieldCode(boolean includeChildFieldCodes)
    {
        return mBundle.getFieldCode(includeChildFieldCodes);
    }

    /// <summary>
    /// Detects field type from field code, unlike <see cref="Type"/> which detects it from a field start.
    /// </summary>
    /// <returns></returns>
    /*FieldType*/int getFieldTypeFromCode()
    {
        return FieldUtil.getFieldType(getFieldCodeCache().getFieldType());
    }

    void setFieldCode(String fieldCode) throws Exception
    {
        removeFieldCode();

        DocumentBuilder builder = new DocumentBuilder(fetchDocument());
        builder.moveTo(getFieldCodeEnd());
        builder.write(fieldCode);
    }

    void setFieldType(/*FieldType*/int fieldType)
    {
        mBundle.setFieldType(fieldType);
    }

    void determineFieldType()
    {
        mBundle.determineFieldType();
    }

    /// <summary>
    /// Removes the field from the document. Returns a node right after the field. If the field's end is the last child
    /// of its parent node, returns its parent paragraph. If the field is already removed, returns <c>null</c>.
    /// </summary>
    public /*virtual*/ Node remove() throws Exception
    {
        return FieldReplacer.remove(this);
    }

    /// <summary>
    /// Removes field code.
    /// </summary>
    void removeFieldCode() throws Exception
    {
        NodeRemover.remove(getStart(), false, getFieldCodeEnd(), false);
    }

    /// <summary>
    /// Removes field result.
    /// </summary>
    void removeFieldResult() throws Exception
    {
        if (!hasSeparator())
            return;

        NodeRemover.remove(getSeparator(), false, getEnd(), false, FieldUtil.getFieldResultRemovalNodeJoinMode(this));
    }

    /// <summary>
    /// Performs the field update. Throws if the field is being updated already.
    /// </summary>
    public void update() throws Exception
    {
        update(false);
    }

    /// <summary>
    /// Performs a field update. Throws if the field is being updated already.
    /// </summary>
    /// <param name="ignoreMergeFormat">
    /// If <c>true</c> then direct field result formatting is abandoned, regardless of the MERGEFORMAT switch, otherwise normal update is performed.
    /// </param>
    public void update(boolean ignoreMergeFormat) throws Exception
    {
        if (isUpdating())
            throw new IllegalStateException("The field is being updated already.");

        boolean mergeFormat = false;
        if (ignoreMergeFormat)
        {
            mergeFormat = getFormat().getGeneralFormats().hasFormat(GeneralFormat.MERGE_FORMAT);
            getFormat().getGeneralFormats().addOrRemove(GeneralFormat.MERGE_FORMAT, false);
        }

        // This update is performed with the topmost context.
        FieldUpdater.updateField(this);

        if (ignoreMergeFormat)
            getFormat().getGeneralFormats().addOrRemove(GeneralFormat.MERGE_FORMAT, mergeFormat);
    }

    /// <summary>
    /// Performs the field unlink.
    /// </summary>
    /// <remarks>
    /// <para>Replaces the field with its most recent result.</para>
    /// <para>Some fields, such as XE (Index Entry) fields and SEQ (Sequence) fields, cannot be unlinked.</para>
    /// </remarks>
    /// <returns>
    /// <c>true</c> if the field has been unlinked, otherwise <c>false</c>.
    /// </returns>
    public boolean unlink() throws Exception
    {
        if (!isUnlinkable())
            return false;

        FieldUnlinker.unlinkField(this);
        return true;
    }

    /// <summary>
    /// Initiates the field's update.
    /// </summary>
    void beginUpdate(FieldUpdateContext context)
    {
        Debug.assertTrue(!isUpdating());

        mUpdateContext = context;
        parseFieldCode();
    }

    /// <summary>
    /// Finalizes the field's update.
    /// </summary>
    /*virtual*/ void endUpdate() throws Exception
    {
        Debug.assertTrue(isUpdating());

        mUpdateContext = null;
    }

    /// <summary>
    /// When overridden in a derived class, returns a value indicating how a child field contained
    /// within the specified argument of this field should be updated at the moment.
    /// </summary>
    /*virtual*/ /*FieldUpdateStrategy*/int getChildFieldsUpdateStrategyInArgument(IFieldArgument argument) throws Exception
    {
        throw new UnsupportedOperationException();
    }

    /// <summary>
    /// Returns the field's update stage.
    /// </summary>
    /*virtual*/ /*FieldUpdateStage*/int getUpdateStage() throws Exception
    {
        return FieldUtil.requiresLayoutDocumentOnUpdate(getType())
            ? FieldUpdateStage.DEFERRED_UPDATE_LAYOUT
            : FieldUpdateStage.MAIN_LOOP;
    }

    /// <summary>
    /// Returns an update action that specifies how to update the field (set result, remove etc) and also
    /// holds all necessary calculated data. To be overridden by specific field types.
    /// </summary>
    /// <remarks>
    /// Any override of this method should return <c>null</c> only if the corresponding field result is not
    /// changed during its update. If the field's result is not changed by some update action (but changed
    /// by the overridden code itself), then you should return <see cref="FieldUpdateActionDoNothing"/> to
    /// indicate this. Otherwise, layout engine will not be able to handle the change properly.
    /// </remarks>
    /*virtual*/ FieldUpdateAction updateCore() throws Exception
    {
        Constant value = getUpdater().getDataProviders().getValue(this);
        return value != null
            ? new FieldUpdateActionApplyResult(this, value)
            : null;
    }

    void unlinkCore() throws Exception
    {
        if (!isUnlinkable())
            return;

        FieldReplacer.replaceWithResult(this, new UnlinkFieldReplaceListener(this), NodeJoinMode.JOIN_TO_NEXT_SIBLING);
    }

    private boolean isUnlinkable()
    {
        switch (getType())
        {
            case FieldType.FIELD_INDEX_ENTRY:
            case FieldType.FIELD_TOC_ENTRY:
            case FieldType.FIELD_REF_DOC:
            case FieldType.FIELD_EQUATION:
            case FieldType.FIELD_SYMBOL:
            case FieldType.FIELD_BARCODE:
            case FieldType.FIELD_FORM_CHECK_BOX:
            case FieldType.FIELD_TOA_ENTRY:
            case FieldType.FIELD_PRIVATE:
            case FieldType.FIELD_ADVANCE:
            case FieldType.FIELD_BIDI_OUTLINE:
                return false;

            default:
                return !isRemoved();
        }
    }

    /*virtual*/ void beforeUnlink() throws Exception
    {
        // Do nothing by default.
    }

    /// <summary>
    /// Returns a displayable result for some fields that actually have no separator (such as SYMBOL,
    /// MACROBUTTON etc).
    /// </summary>
    /*virtual*/ NodeRange getFakeResult() throws Exception
    {
        return null;
    }

    /// <summary>
    /// Returns a node range occupying the whole field.
    /// </summary>
    NodeRange getFieldRange()
    {
        return isRemoved()
            ? NodeRange.VOID
            : new NodeRange(getStart(), getEnd());
    }

    /// <summary>
    /// Returns a node range representing field code.
    /// </summary>
    NodeRange getFieldCodeRange()
    {
        return isRemoved()
            ? NodeRange.VOID
            : new NodeRange(getStart(), false, getFieldCodeEnd(), false);
    }

    /// <summary>
    /// Returns a node range representing field result.
    /// </summary>
    NodeRange getFieldResultRange()
    {
        return isRemoved() || !hasSeparator()
            ? NodeRange.VOID
            : new NodeRange(getSeparator(), false, getEnd(), false);
    }

    /// <summary>
    /// Returns the direction of the paragraphs of the field result, used by field result forming routines
    /// when <see cref="FieldOptions.IsBidiTextSupportedOnUpdate"/> is set to true.
    /// <c>true</c> - right-to-left (RTL), <c>false</c> - left-to-right (LTR).
    /// </summary>
    /*virtual*/ boolean isRtlResultParagraph()
    {
        return getBidiEmbeddingLevel();
    }

    private boolean getBidiEmbeddingLevel()
    {
        // BIDI embedding level of most of the fields depends on field start's RTL attribute.
        // Code snippet for BIDI resolving below is copied from the last version of FieldUpdateCleanupActionMergeTextField
        // class, which is removed for now. However, this code seems to be error prone in case of AttrBoolEx.Same.
        // But there's no more suitable options, so leave as it is.
        // WORDSNET-8416 Some fields use the parent paragraph's BIDI attribute in this case.
        switch (getType())
        {
            case FieldType.FIELD_PAGE:
            case FieldType.FIELD_NUM_PAGES:
                return isInHeaderFooter()
                    ? getPageAndNumPagesBidiEmbeddingLevelSource()
                    : getStart().getParentParagraph().getParagraphFormat().getBidi();
            case FieldType.FIELD_PAGE_REF:
                return getStart().getParentParagraph().getParagraphFormat().getBidi();
            default:
                return (/*boolean*/Boolean)getStart().getRunPr().getBidi().resolveFetchInheritedRunAttrWithNull(getStart(), FontAttr.BIDI);
        }
    }

    private boolean getPageAndNumPagesBidiEmbeddingLevelSource()
    {
        Debug.assertTrue(isInHeaderFooter());
        Debug.assertTrue(getType() == FieldType.FIELD_PAGE || getType() == FieldType.FIELD_NUM_PAGES);

        if (getStart().getFont().getBidi())
            return true;

        if (!getFormat().getGeneralFormats().hasFormat(GeneralFormat.MERGE_FORMAT))
            return false;

        Inline inline = firstOrDefaultInlineNode(getOldResultNodes());
        return inline != null
            ? inline.getFont().getBidi()
            : getStart().getParentParagraph().getParagraphFormat().getBidi();
    }

    private static Inline firstOrDefaultInlineNode(Iterable<Node> nodes)
    {
        if (nodes == null)
            return null;

        for (Node node : nodes)
        {
            Inline inline = ms.as(node, Inline.class);
            if (inline != null)
                return inline;
        }

        return null;
    }

    /// <summary>
    /// Makes sure the field has a separator and otherwise either throws or inserts it.
    /// </summary>
    void ensureSeparator(boolean force)
    {
        if (hasSeparator())
            return;

        if (force || (FieldUtil.getSeparatorPresence(getType()) != FieldSeparatorPresence.NEVER))
        {
            // The separator is missed for some reason, insert it.
            FieldSeparator separator = new FieldSeparator(getEnd().getDocument(), new RunPr(), getStart().getFieldType());
            getEnd().insertPrevious(separator);

            mBundle.setSeparator(separator);
            getEnd().setHasSeparator(true);

            if (isUpdating())
                getUpdateContext().notifyFieldAreaChanged(FieldArea.CODE);
        }
        else
        {
            // This field cannot have a result, throw.
            throw new IllegalStateException("Cannot set result of a field that does not have a separator.");
        }
    }

    /*virtual*/ Section getPageNumberFormatSection() throws Exception
    {
        return null;
    }

    /// <summary>
    /// Returns the default date/time formatting string used when the custom date/time format is omitted.
    /// </summary>
    /*virtual*/ String getDefaultDateTimeFormat()
    {
        return null;
    }

    /// <summary>
    /// Returns the main (not glossary) owner document.
    /// </summary>
    Document fetchDocument()
    {
        return getStart().fetchDocument();
    }

    /// <summary>
    /// Stores the old result nodes into the list.
    /// </summary>
    void storeOldResultNodesIfNeeded()
    {
        mOldResultNodes = needStoreOldResultNodes()
            ? FieldOldResultNodeCollection.create(this)
            : null;

        if (mOldResultNodes != null)
        {
            if (getSeparator() != null && getSeparator().getParentParagraph() != null)
                setOldResultStartParagraph((Paragraph)getSeparator().getParentParagraph().deepClone(false));

            if (getEnd() != null && getEnd().getParentParagraph() != null)
                setOldResultEndParagraph((Paragraph)getEnd().getParentParagraph().deepClone(false));
        }
    }

    /// <summary>
    /// Returns the value, indicating whether the old field result must be stored during a field result update
    /// (for example, to compare the old field result with the new one).
    /// </summary>
    protected /*virtual*/ boolean needStoreOldResultNodes()
    {
        if (FieldUtil.getSeparatorPresence(getType()) == FieldSeparatorPresence.NEVER)
            return false;

        if (getFieldCodeCache().hasParseError())
            return false;

        // At the moment there is only one case, when storing of the field result is necessary.
        // It is the format merging. But may be it would be useful in some other scenarios.
        return getFormat().getGeneralFormats().hasFormat(GeneralFormat.MERGE_FORMAT);
    }

    /// <summary>
    /// Frees the list, containing the old result nodes.
    /// </summary>
    void removeStoredOldResultNodes()
    {
        mOldResultNodes = null;
    }

    /// <summary>
    /// Returns previously collected old result nodes. It is guaranteed, that all of the nodes
    /// do not belong to a document.
    /// </summary>
    Iterable<Node> getOldResultNodes()
    {
        Debug.assertTrue(mOldResultNodes != null);
        return mOldResultNodes.getRange();
    }

    /// <summary>
    /// Returns first paragraph of old result.
    /// It is guaranteed, that node does not belong to a document.
    /// </summary>
    Paragraph getOldResultStartParagraph() { return mOldResultStartParagraph; }; private void setOldResultStartParagraph(Paragraph value) { mOldResultStartParagraph = value; };

    private Paragraph mOldResultStartParagraph;

    /// <summary>
    /// Returns last paragraph of old result.
    /// It is guaranteed, that node does not belong to a document.
    /// </summary>
    Paragraph getOldResultEndParagraph() { return mOldResultEndParagraph; }; private void setOldResultEndParagraph(Paragraph value) { mOldResultEndParagraph = value; };

    private Paragraph mOldResultEndParagraph;

    /// <summary>
    /// Gets a value indicating whether the field supports conditional child fields' update or not.
    /// </summary>
    /// <dev>
    /// This is important to provide this info not through <see cref="FieldUtil"/> method, but through the field object
    /// because of MERGEFIELD surrogates.
    /// </dev>
    /*virtual*/ boolean getSupportsConditionalUpdate() { return false; }

    /// <summary>
    /// Gets whether this field is located in a header/footer.
    /// </summary>
    boolean isInHeaderFooter() { return getStart().getAncestor(NodeType.HEADER_FOOTER) != null; }

    /// <summary>
    /// Gets the node that represents the start of the field.
    /// </summary>
    public FieldStart getStart() { return mBundle.getStart(); }

    /// <summary>
    /// Gets the node that represents the field separator. Can be <c>null</c>.
    /// </summary>
    public FieldSeparator getSeparator()
    {
        if (mBundle.hasSeparator() == getEnd().hasSeparator())
            return mBundle.getSeparator();

        mBundle.setSeparator(getEnd().hasSeparator()
            ? FieldBundle.getFieldBundleNoSeparatorCheck(getStart()).getSeparator()
            : null);

        return mBundle.getSeparator();
    }

    /// <summary>
    /// Gets the node that represents the field end.
    /// </summary>
    public FieldEnd getEnd() { return mBundle.getEnd(); }


    /// <summary>
    /// Gets the Microsoft Word field type.
    /// </summary>
    
    public /*virtual*/ /*FieldType*/int getType() { return getStart().getFieldType(); }

    /// <summary>
    /// Gets or sets text that is between the field separator and field end.
    /// </summary>
    public String getResult() { return getResult(false); }
    public void setResult(String value) throws Exception
    {
        ArgumentUtil.checkNotNull(value, "value");

        ensureSeparator(false);
        storeOldResultNodesIfNeeded();

        FieldResultApplier resultApplier = buildResultApplier(value);
        resultApplier.applyResult();

        removeStoredOldResultNodes();
    }

    /// <remarks>
    /// The method has to be "private protected", but it requires C# 7.2 or greater.
    /// </remarks>
    /*virtual*/ FieldResultApplier buildResultApplier(String result)
    {
        return new TextResultApplier(this, result);
    }

    /// <summary>
    /// Gets the text that represents the displayed field result.
    /// </summary>
    /// <remarks>
    /// The <see cref="Words.Document.UpdateListLabels"/> method must be called to obtain correct value for the
    /// <see cref="FieldListNum"/>, <see cref="FieldAutoNum"/>, <see cref="FieldAutoNumOut"/> and <see cref="FieldAutoNumLgl"/> fields.
    /// </remarks>
    public String getDisplayResult() throws Exception
    {
        NodeRange fakeResult = getFakeResult();

        if (fakeResult != null)
            return NodeTextCollector.getText(fakeResult, true);

        return getResult(true);
    }

    String getResult(boolean isFieldResultMode)
    {
        return hasSeparator()
            ? NodeTextCollector.getText(getSeparator(), false, getEnd(), false, isFieldResultMode)
            : "";
    }

    boolean hasResult()
    {
        NodeRange resultRange = getFieldResultRange();

        if (resultRange.isVoid())
            return false;

        for (Node node : resultRange)
        {
            if (resultRange.getStart().getNode() == node || resultRange.getEnd().getNode() == node)
                continue;

            if (NodeUtil.getLength(node) > 0)
                return true;
        }

        return false;
    }

    /// <summary>
    /// Gets or sets whether the field is locked (should not recalculate its result).
    /// </summary>
    public boolean isLocked() { return mBundle.isLocked(); }
    public void isLocked(boolean value) { mBundle.isLocked(value); }

    /// <summary>
    /// Gets or sets whether the current result of the field is no longer correct (stale) due to other modifications made to the document.
    /// </summary>
    public boolean isDirty() { return mBundle.isDirty(); }
    public void isDirty(boolean value) { mBundle.isDirty(value); }

    /// <summary>
    /// Returns either separator or field end node.
    /// </summary>
    FieldChar getFieldCodeEnd() { return mBundle.getFieldCodeEnd(); }

    /// <summary>
    /// Gets a <see cref="FieldCode"/> cache for this field.
    /// </summary>
    FieldCode getFieldCodeCache()
    {
        ensureFieldCodeCache();

        return mFieldCodeCache;
    }

    /// <summary>
    /// Gets a value indicating whether the field has field code cache.
    /// </summary>
    boolean hasFieldCodeCache() { return mFieldCodeCache != null; }

    /// <summary>
    /// Gets a <see cref="FieldFormat"/> object that provides typed access to field's formatting.
    /// </summary>
    public FieldFormat getFormat() { return (mFieldFormatCache != null ? mFieldFormatCache : (mFieldFormatCache = new FieldFormat(this))); }

    /// <summary>
    /// Gets or sets the LCID of the field.
    /// </summary>
    /// <seealso cref="FieldUpdateCultureSource.FieldCode"/>
    public int getLocaleId()
    {
        return getFieldCodeCache().getBidi()
            ? getFieldCodeCache().getLanguageIdBi()
            : getFieldCodeCache().getLanguageId();
    }

    public void setLocaleId(int value)
    {
        /*Language*/int language = (/*Language*/int)value;
        if ((language == Language.LANGUAGE_NOT_SET) || !LocaleClassifier.isDefined(language))
            throw new IllegalArgumentException("Not a valid LCID: " + value);

        if (LocaleClassifier.isArabic(value) || LocaleClassifier.isHebrew(value))
        {
            getFieldCodeCache().setLanguageIdBi(value);
            getFieldCodeCache().setBidi(true);
        }
        else
        {
            getFieldCodeCache().setLanguageId(value);
            getFieldCodeCache().setBidi(false);
        }
    }

    /// <summary>
    /// Gets whether this field has a separator.
    /// </summary>
    boolean hasSeparator() { return getSeparator() != null; }

    /// <summary>
    /// Gets whether this field is removed from the model.
    /// </summary>
    boolean isRemoved()
    {
        if (getStart().getParentNode() == null)
            return true;

        if (getEnd().getParentNode() == null)
            return true;

        // The field is considered as not removed if there is common start and end ancestor
        // (even if the common ancestor itself is removed), i.e. we can traverse from start to end.
        return getStart().getTopmostAncestor() != getEnd().getTopmostAncestor();
    }

    FieldUpdater getUpdater() { return mUpdateContext.getUpdater(); }

    FieldUpdateContext getUpdateContext() { return mUpdateContext; }

    boolean isUpdating() { return getUpdateContext() != null; }

    DocumentBase getDocument() { return getStart().getDocument(); }

    /// <summary>
    /// Returns a value indicating whether the field is topmost field in the DOM itself
    /// or a part of outer topmost field result (recursively).
    /// </summary>
    boolean isTopmostField()
    {
        return isTopmostField(getEnd());
    }

    /*virtual*/ INodeModifier getResultModifier()
    {
        return null;
    }

    private static boolean isTopmostField(FieldEnd fieldEnd)
    {
        DocumentPosition currentPosition = DocumentPosition.createPositionAfter(fieldEnd);
        FieldCharCounter fieldCharCounter = new FieldCharCounter();

        while (true)
        {
            Node lastNode = currentPosition.getNode();
            do
            {
                if (!currentPosition.move(null, true, true, true, false, false))
                    return true;
            }
            while (currentPosition.getNode() == lastNode);

            Node currentNode = currentPosition.getNode();
            if (FieldCharCounter.isValidFieldChar(currentNode))
            {
                switch (currentNode.getNodeType())
                {
                    case NodeType.FIELD_SEPARATOR:
                        if (!fieldCharCounter.isInFieldCode())
                            return false;

                        break;
                    case NodeType.FIELD_END:
                        if (!fieldCharCounter.isInField())
                        {
                            FieldEnd parentFieldEnd = ((FieldEnd)currentNode);
                            return parentFieldEnd.hasSeparator() && isTopmostField(parentFieldEnd);
                        }

                        break;
                    default:
                        break;
                }
            }

            fieldCharCounter.visitNode(currentNode);
        }
    }

    private FieldBundle mBundle;
    private FieldCode mFieldCodeCache;
    private FieldFormat mFieldFormatCache;
    private FieldUpdateContext mUpdateContext;
    private FieldOldResultNodeCollection mOldResultNodes;

}

