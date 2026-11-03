// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/06/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.NullableBool;
import com.aspose.Debug;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.SystemPal;
import com.aspose.LocaleConverter;
import com.aspose.ms.System.msChar;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msArray;


/// <summary>
/// Updates single or multiple fields in a single session. Represents a root node of a field update tree.
/// See <see cref="FieldUpdateContextContainer"/> remarks for details.
/// </summary>
/// <remarks>
/// Fields are updated from the bottom to the top traversing the field update tree.
/// </remarks>
final class FieldUpdater extends FieldUpdateContextContainer
{
    /// <summary>
    /// Creates a <see cref="FieldUpdater"/> instance for a field update session which is considered not to be
    /// initiated by layout.
    /// </summary>
    private FieldUpdater(Document document)
    {
    	this(document, (FieldDisplayContext)null, null);
	
    }

    private FieldUpdater(Document document, FieldDisplayContext displayContext)
    {
    	this(document, displayContext, null);
	
    }

    FieldUpdater(Document document, BookmarkCache bookmarkCache, IFieldCleaner fieldCleaner)
    {
    	this (document, (FieldDisplayContext)null, fieldCleaner);
	
        mBookmarkCache = bookmarkCache;
        mInternalCreatedBookmarkCache = false;
    }

    private FieldUpdater(Document document, boolean updateBeforeSave, boolean updateDirtyFields)
    {
    	this (document);
	
        mIsUpdateBeforeSave = updateBeforeSave;
        mIsUpdateDirtyFields = updateDirtyFields;
    }

    private FieldUpdater(Document document, FieldDisplayContext displayContext, IFieldCleaner fieldCleaner)
    {
        setCurrentStage(FieldUpdateStage.MAIN_LOOP);
        mDocument = document;
        mBookmarkCache = new BookmarkCache(getDocument());
        mFieldCleaner = (fieldCleaner != null) ? fieldCleaner : new DefaultFieldCleaner();
        mInternalCreatedBookmarkCache = true;
        setDisplayContext(displayContext);
        mIsUpdateInitiatedByLayout = (displayContext != null);
        mListeners = new FieldUpdateListenerCollection();
        mSession = displayContext != null && displayContext.getFieldUpdateSession() != null
                       ? displayContext.getFieldUpdateSession()
                       : new FieldUpdateSession(document);
        mDataProviders = new FieldUpdateDataProvider(this, mSession.getDataProviderCollection());
    }

    void ensureFieldUpdateProgressContext()
    {
        if (getDocument().getFieldUpdateProgressContext() == null)
        {
            getDocument().setFieldUpdateProgressContext(new FieldUpdateProgressContext(
                getDocument().getFieldOptions().getFieldUpdatingProgressCallback(),
                this));
        }
    }

    private void releaseFieldUpdateProgressContext()
    {
        if (getDocument().getFieldUpdateProgressContext().getOwner() != this)
            return;

        getDocument().getFieldUpdateProgressContext().onUpdateCompleted();
        getDocument().setFieldUpdateProgressContext(null);
    }

    /// <summary>
    /// Updates the given field and its child fields if any in a single session. The given field is considered
    /// to be topmost by the updater.
    /// </summary>
    static void updateField(Field field) throws Exception
    {
        updateFieldFullCycle(field, null, NullableBool.NOT_DEFINED);
    }

    private static /*FieldUpdateResults*/int updateFieldFullCycle(
        Field field,
        FieldDisplayContext displayContext,
        /*NullableBool*/byte isTopmostField) throws Exception
    {
        // The field should not be updated already.
        Debug.assertTrue(!field.isUpdating());

        FieldUpdater updater = new FieldUpdater(field.fetchDocument(), displayContext);
        updater.ensureFieldUpdateProgressContext();
        FieldUpdateContext context = updater.addTopmostFieldToUpdate(field, isTopmostField);
        try /*JAVA: was using*/
        {
            if (context == null)
            {
                updater.releaseFieldUpdateProgressContext();
                return FieldUpdateResults.NONE;
            }

            updater.updateFieldsByStages(FieldUpdateStage.MAIN_LOOP, FieldUpdateStage.DEFERRED_UPDATE_LAYOUT);
            updater.finalizeUpdateFields();

            return context.getUpdateResults();
        }
        finally { if (context != null) context.dispose(); }
    }

    /// <summary>
    /// Updates fields contained in the given node.
    /// </summary>
    static void updateFields(Node node) throws Exception
    {
        FieldUpdater updater = new FieldUpdater(node.fetchDocument());
        updater.ensureFieldUpdateProgressContext();
        updateFields(node, updater);
    }

    /// <summary>
    /// Updates fields contained in the given node range.
    /// </summary>
    static void updateFields(NodeRange nodeRange) throws Exception
    {
        ArrayList<Field> fieldColl = FieldExtractor.extractToCollection(nodeRange);
        for (Field field : fieldColl)
            updateField(field);
    }

    /// <summary>
    /// Updates given fields.
    /// </summary>
    static void updateFields(ArrayList<Field> fields) throws Exception
    {
        if (fields.size() == 0)
            return;

        FieldUpdater updater = new FieldUpdater(fields.get(0).fetchDocument());
        updater.ensureFieldUpdateProgressContext();
        for (Field field : fields)
            updater.addTopmostFieldToUpdate(field, NullableBool.NOT_DEFINED);

        updateFields(updater);
    }

    static void updateFieldsBeforeSave(Node node) throws Exception
    {
        FieldUpdater updater = new FieldUpdater(node.fetchDocument(), true, false);
        updater.ensureFieldUpdateProgressContext();
        updateFields(node, updater);
    }

    static void updateDirtyFields(Node node) throws Exception
    {
        FieldUpdater updater = new FieldUpdater(node.fetchDocument(), false, true);
        updater.ensureFieldUpdateProgressContext();
        updateFields(node, updater);
    }

    private static void updateFields(Node node, FieldUpdater updater) throws Exception
    {
        updater.addTopmostFieldsToUpdate(node);
        updateFields(updater);
    }

    private static void updateFields(FieldUpdater updater) throws Exception
    {
        updater.updateFieldsByStages(FieldUpdateStage.MAIN_LOOP, FieldUpdateStage.DEFERRED_UPDATE_LAYOUT);
        updater.finalizeUpdateFields();
    }

    /// <summary>
    /// Updates fields contained in the given nodes. Allows calling multiple times because does not update deferred fields.
    /// </summary>
    void updateFieldsRepeatedly(ArrayList<Node> nodes) throws Exception
    {
        addTopmostFieldsToUpdate(nodes);
        updateFieldsByStages(FieldUpdateStage.MAIN_LOOP, FieldUpdateStage.MAIN_LOOP);
        removeDeferredFields();
    }

    /// <summary>
    /// Updates deferred fields and finalizes field update. Should be called after last call of
    /// <see cref="UpdateFieldsRepeatedly"/> is done.
    /// </summary>
    void updateFieldsRepeatedlyFinalize() throws Exception
    {
        updateFieldsByStages(FieldUpdateStage.DEFERRED_UPDATE_REF, FieldUpdateStage.DEFERRED_UPDATE_LAYOUT);
        finalizeUpdateFields();
    }

    void removeTopmostFieldContexts(ArrayList<Node> nodes)
    {
        RemoveTopmostFieldHelper helper = new RemoveTopmostFieldHelper();
        helper.extract(nodes);
        Iterator<FieldUpdateContext> contextEnumerator = iterator();
        {
            while (helper.getFieldStarts().size() != 0 && contextEnumerator.hasNext())
            {
                FieldUpdateContext context = contextEnumerator.next();
                int fieldStartIndex = helper.getFieldStarts().indexOf(context.getField().getStart());
                if (fieldStartIndex == -1)
                    continue;

                removeChild(context);
                helper.getFieldStarts().remove(fieldStartIndex);
            }
        }
    }

    private void addTopmostFieldsToUpdate(Node node)
    {
        /*NullableBool*/byte isTopmost = node instanceof DocumentBase ? NullableBool.TRUE : NullableBool.NOT_DEFINED;
        AddTopmostFieldHelper helper = new AddTopmostFieldHelper(this, isTopmost);
        helper.extract(node);
    }

    private void addTopmostFieldsToUpdate(ArrayList<Node> nodes)
    {
        AddTopmostFieldHelper helper = new AddTopmostFieldHelper(this, NullableBool.NOT_DEFINED);
        helper.extract(nodes);
    }

    private FieldUpdateContext addTopmostFieldToUpdate(Field field, /*NullableBool*/byte isTopmostField) throws Exception
    {
        beginInsertFieldToUpdate();
        FieldUpdateContext context = FieldUpdateContext.appendTopmostContext(field, this, isTopmostField);
        endInsertFieldToUpdate(context);

        return context;
    }

    private void beginInsertFieldToUpdate()
    {
        isFieldToUpdateInserting(true);
    }

    private void endInsertFieldToUpdate(FieldUpdateContext context)
    {
        isFieldToUpdateInserting(false);
        if (context == null)
            return;

        // FOSS
    }

    /// <summary>
    /// Updates collected fields by stages from the specified range.
    /// </summary>
    private void updateFieldsByStages(/*FieldUpdateStage*/int startStage, /*FieldUpdateStage*/int endStage) throws Exception
    {
        for (int stage = (int)startStage; stage <= (int)endStage; stage++)
        {
            setCurrentStage((/*FieldUpdateStage*/int)stage);
            getExternalActions().perform(getCurrentStage());
            updateFieldsOnCurrentStage(this);
        }
    }

    /// <summary>
    /// Tries to update fields (which should be updated on the current stage) corresponding to child contexts
    /// of the specified container. Returns <c>false</c> if update of any field in the list is skipped. Returns
    /// <c>true</c> if all of the fields in the list are updated or their updates are deferred.
    /// </summary>
    private boolean updateFieldsOnCurrentStage(FieldUpdateContextContainer container) throws Exception
    {
        if (!container.hasChildren())
            return true;

        boolean result;
        do
        {

            result = true;

            for (FieldUpdateContext context : container)
            {
                // If the field's update is skipped or the field is updated already, move to the next one.
                if (context.isUpdateSkipped() || context.isUpdatePerformed())
                    continue;

                // Update child fields first and the field itself then.
                updateChildFieldsOnCurrentStage(context);
                if (!updateFieldOnCurrentStage(context))
                    result = false;
            }
        }
        while (!finalizeUpdateFieldsOnCurrentStage(container));

        return result;
    }

    /// <summary>
    /// Checks whether fields' update on the current stage can be finalized for the specified container.
    /// Returns <c>true</c> if the check is succeeded. Returns <c>false</c> to indicate, that the update
    /// should be continued.
    /// </summary>
    private boolean finalizeUpdateFieldsOnCurrentStage(FieldUpdateContextContainer container)
    {
        // The purpose of this method is to ensure that parent field code cache (if any) was not invalidated
        // upon child fields' update. This typically happens when a child field not enclosed in double quotes
        // is removed while updating. If the parent field code cache was invalidated, we have to reparse it
        // and perform child fields' update one more time. Note, that fields which are already updated will
        // not be updated once more because of FieldUpdateContext.IsUpdatePerformed check.
        //
        // If the specified container is the updater, then it does not correspond to any field in a document.
        if (container == this)
            return true;

        // If the container is context and its field has valid field code cache, then child fields' update
        // can be finalized.
        FieldUpdateContext context = (FieldUpdateContext)container;
        if (context.getField().hasFieldCodeCache())
            return true;

        // The parent field code chache is invalid. Reparse it and make child fields' update be continued.
        context.getField().parseFieldCode();
        return false;
    }

    private void updateChildFieldsOnCurrentStage(FieldUpdateContext context) throws Exception
    {
        if (context.getAreAllChildFieldsUpdated())
            return;

        boolean isUpdateDeferred = getFieldUpdateStrategyByAncestor(context) != FieldUpdateStrategy.UPDATE;

        // Update child fields by stages, if it is required.
        //
        // Note, that if the field's update is deferred, then it does not make any sense to update its child fields
        // by stages, because only immediate child fields will be updated in this case regardless of any conditions.
        // See GetUpdateStrategyOnCurrentStage for details.
        //
        // This also guarantees that Field.DefineChildFieldUpdateConditions is called only when it is really needed.
        if (!isUpdateDeferred && context.getField().getSupportsConditionalUpdate())
        {
            context.setChildUpdateStage(FieldChildUpdateStage.PERMANENT);
            updateFieldsOnCurrentStage(context);
            context.setChildUpdateStage(FieldChildUpdateStage.CONDITIONAL);
        }

        if (!updateFieldsOnCurrentStage(context))
            return;

        // If the field's update is deferred, then its child fields' update is deferred either and hence we should not
        // consider them as updated in this case.
        if (!isUpdateDeferred)
            context.notifyAllChildFieldsUpdated();
    }

    /// <summary>
    /// Returns a field update startegy of the specified field accordingly to ancestor fields conditional updates of its child fields.
    /// </summary>
    private static /*FieldUpdateStrategy*/int getFieldUpdateStrategyByAncestor(FieldUpdateContext context) throws Exception
    {
        for (FieldUpdateContext childContext = context, parentContext = context.getParentContext();
             parentContext != null;
             childContext = parentContext, parentContext = parentContext.getParentContext())
        {
            if (parentContext.getField().getSupportsConditionalUpdate())
            {
                parentContext.getField().ensureFieldCodeCache();
                /*FieldUpdateStrategy*/int strategy = parentContext.getField().getChildFieldsUpdateStrategyInArgument(childContext.getParentArgument());
                if (strategy != FieldUpdateStrategy.UPDATE)
                {
                    return strategy;
                }
            }
        }

        return FieldUpdateStrategy.UPDATE;
    }

    /// <summary>
    /// Tries to update the specified field on the current stage. Returns <c>true</c> if the field's update is performed or
    /// deferred. Returns <c>false</c> if its update is skipped.
    /// </summary>
    private boolean updateFieldOnCurrentStage(FieldUpdateContext context) throws Exception
    {
        FieldUpdater updater = context.getUpdater();
        /*FieldUpdateStrategy*/int strategy = getUpdateStrategyOnCurrentStage(context);

        // SPEED: Cache strategy to use during parent context update strategy calculating.
        context.setFieldUpdateStrategy(strategy);

        try
        {
            switch (strategy)
            {
                case FieldUpdateStrategy.UPDATE:
                    updateField(context);
                    break;
                case FieldUpdateStrategy.SKIP:
                    return false;
                case FieldUpdateStrategy.DEFER:
                case FieldUpdateStrategy.REJECT:
                    context.notifyUpdateProgressCurrentFieldIsUpdated();
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        finally
        {
            updater.getListeners().notifyUpdated(context, strategy);
        }

        return true;
    }

    private /*FieldUpdateStrategy*/int getUpdateStrategyOnCurrentStage(FieldUpdateContext context) throws Exception
    {
        // The order of the following checks is very important. Do not change it, unless you really understand what you
        // are doing.

        if (FieldUtil.getEffectiveFieldLocked(context.getField()))
            return FieldUpdateStrategy.DEFER;

        if (mIsUpdateDirtyFields && !context.getField().isDirty())
            return FieldUpdateStrategy.DEFER;

        /*FieldUpdateStrategy*/int strategyByAncestor = getFieldUpdateStrategyByAncestor(context);

        // 0. The field's update can be rejected by its ancestor field.
        if (!context.getRequiresImmediateUpdate() && (strategyByAncestor == FieldUpdateStrategy.REJECT || strategyByAncestor == FieldUpdateStrategy.SKIP))
            return strategyByAncestor;

        // 1. If the field update is deferred to a later stage, skip it. This also handles those fields, which should be
        //    updated immediately but are deferred by the field update listener at the same time.
        //
        // We can encounter a field which update is deferred to an earlier stage. This can happen in the following cases:
        //   - the field's parent supports conditional update and this field is updated on conditional stage.
        //   - a child field of this field was deferred to this stage but the field itself is deferred to an earlier stage.
        // We should allow update of such fields either.
        if (context.getStageToDefer() > getCurrentStage())
            return FieldUpdateStrategy.SKIP;

        // 2. If the field requires an immediate update, do it right away. If the field contains any child fields, which
        // should be updated on later stages, then they are not to be updated at this point.
        //
        // Theoretically we may deal with something like { MERGEFIELD "person{ REF num }" }. This means that the field
        // which should be updated immediately contains a field which should be updated on a later stage. This conflict
        // can not be resolved easily, so let's just ignore this case at the moment. It seems to have a little significance
        // in practice anyway.
        if (context.getRequiresImmediateUpdate())
            return FieldUpdateStrategy.UPDATE;

        // 3. If a common field should be updated externally by the layout, skip it, since the layout will update it anyway.
        if (context.isUpdatedByLayoutExternally())
            return FieldUpdateStrategy.SKIP;

        // 4. Skip update, if any child fields update skipped (WORDSNET-12155).
        if (isAnyChildFieldsUpdateSkipped(context))
            return FieldUpdateStrategy.SKIP;

        // 5. The field's update can be deferred by its ancestor field.
        if (strategyByAncestor == FieldUpdateStrategy.DEFER)
            return FieldUpdateStrategy.DEFER;

        // 6. Common fields should be updated after all of its children.
        if (!context.getAreAllChildFieldsUpdated())
            return FieldUpdateStrategy.SKIP;

        return FieldUpdateStrategy.UPDATE;
    }

    /// <summary>
    /// Indicates whether updating of any child field of specified context update is skipped.
    /// </summary>
    private static boolean isAnyChildFieldsUpdateSkipped(FieldUpdateContext context)
    {
        // WORDSNET-14390 return if all child fields are updated.
        if (context.getAreAllChildFieldsUpdated())
            return false;

        for (FieldUpdateContext childContext : (Iterable<FieldUpdateContext>) context)
        {
            if (childContext.getFieldUpdateStrategy() == FieldUpdateStrategy.SKIP)
                return true;

            if (isAnyChildFieldsUpdateSkipped(childContext))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Updates the field either through layout or in a common way.
    /// </summary>
    private void updateField(FieldUpdateContext context) throws Exception
    {
        // FOSS

        // Request the field's update through layout ...
        if (needRequestUpdateFieldByLayout(context) && getDisplayContext().updateField(context.getField(), context.isTopmostField()))
        {
            reflowLayoutOrDefer(context.getField());
            return;
        }

        // ... if it is rejected, update the field in a common way.
        /*FieldUpdateResults*/int results = updateFieldCore(context);
        notifyLayoutFieldResultChanged(context, results);
    }

    private void notifyLayoutFieldResultChanged(FieldUpdateContext context, /*FieldUpdateResults*/int updateResults)
    {
        // FOSS
    }

    private boolean needRequestUpdateFieldByLayout(FieldUpdateContext context)
    {
        // No display context - can not request an update through layout.
        if (getDisplayContext() == null)
            return false;

        // If the update session is initiated by layout, then the topmost fields are updated by layout from the beginning,
        // so do not request their update through layout one more time.
        return (!isUpdateInitiatedByLayout() || context.hasParentContext() || context.isInFieldResult() || FieldUtil.requiresUpdateFieldsInResult(context.getField().getType()));
    }

    /// <summary>
    /// Updates the specified field in a common way.
    /// </summary>
    private static /*FieldUpdateResults*/int updateFieldCore(FieldUpdateContext context) throws Exception
    {
        if (context.getUpdater().getDocument().getFieldUpdateProgressContext() != null)
            context.getUpdater().getDocument().getFieldUpdateProgressContext().notifyInternal();

        if (context.getField().isRemoved())
        {
            context.notifyUpdateProgressCurrentFieldIsUpdated();

            return FieldUpdateResults.NONE;
        }

        FieldUpdater updater = context.getUpdater();

        if (!updater.needSkipFieldUpdate(context))
        {
            context.getField().storeOldResultNodesIfNeeded();

            raiseFieldUpdatingEvent(context.getField(), updater);

            if (updater.getListeners().notifyUpdate(context) || updateFieldCultureAware(context.getField()))
                updater.processFieldResultChange(context, true);

            raiseFieldUpdatedEvent(context.getField(), updater);

            context.getField().removeStoredOldResultNodes();
        }

        context.notifyUpdateProgressCurrentFieldIsUpdated();

        // If the field requires fields in its result to be updated, update them regardless of whether
        // the field itself was updated or not.
        if (insertFieldResultFieldsIfNeeded(context))
            updater.processFieldResultChange(context, context.getField().isInHeaderFooter());

        endUpdateField(context, updater);

        return context.getUpdateResults();
    }

    private boolean needSkipFieldUpdate(FieldUpdateContext context)
    {
        return needSkipFieldUpdateInHeaderFooter(context) || needSkipFieldUpdateBeforeSave(context);
    }

    private boolean needSkipFieldUpdateInHeaderFooter(FieldUpdateContext context)
    {
        // Some fields located in header/footer can not be updated if the update was not initiated by layout.
        // Skip them here.
        if (context.getField().isInHeaderFooter() &&
            !isUpdateInitiatedByLayout() &&
            !FieldUtil.isUpdatedWithoutLayoutInHeaderFooter(context.getField().getType()))
        {
            return true;
        }

        // WORDSNET-5457 Do not update fields that MS Word does not update.
        return (isUpdateInitiatedByLayout() && !isUpdatedWithLayoutInHeaderFooter(context));
    }

    private static boolean isUpdatedWithLayoutInHeaderFooter(FieldUpdateContext context)
    {
        if (FieldUtil.isUpdatedWithLayoutInHeaderFooter(context.getField().getType()))
            return true;

        if (context.hasChildren())
        {
            for (FieldUpdateContext childContext : (Iterable<FieldUpdateContext>) context)
            {
                if (isUpdatedWithLayoutInHeaderFooter(childContext))
                    return true;
            }
        }

        return false;
    }

    private boolean needSkipFieldUpdateBeforeSave(FieldUpdateContext context)
    {
        if (!mIsUpdateBeforeSave)
            return false;

        return !FieldsAutoUpdateUtils.isFieldUpdatedBeforeSave(context);
    }

    /// <summary>
    /// Updates the specified field considering that it may require using of a culture, other than the current
    /// thread's one. Returns a value indicating whether the field's result was changed during the update.
    /// </summary>
    private static boolean updateFieldCultureAware(Field field) throws Exception
    {
        FieldOptions options = field.fetchDocument().getFieldOptions();

        String fieldUpdateCultureName = getFieldUpdateCultureName(options, field);
        if (fieldUpdateCultureName == null)
            return updateFieldUsingCurrentCulture(field);

        msCultureInfo culture = null;
        if (options.getFieldUpdateCultureProvider() != null)
            culture = msCultureInfo.fromJava(options.getFieldUpdateCultureProvider().getCulture(fieldUpdateCultureName, field));

        if (culture == null)
            culture = SystemPal.getCulture(fieldUpdateCultureName);

        msCultureInfo currentCulture = SystemPal.getCurrentCulture();
        try
        {
            SystemPal.setCulture(culture);
            return updateFieldUsingCurrentCulture(field);
        }
        finally
        {
            SystemPal.setCulture(currentCulture);
        }
    }

    /// <summary>
    /// Returns culture name that should be used while updating of the specified field. Returns <c>null</c> to
    /// indicate that the current thread's culture should be used.
    /// </summary>
    private static String getFieldUpdateCultureName(FieldOptions options, Field field)
    {
        if (options.getFieldUpdateCultureSource() == FieldUpdateCultureSource.CURRENT_THREAD)
            return null;

        // It looks like MS Word uses field's language for explicit date formatting only, except of date/time fields.
        // WORDSNET-6333 Use field's language for cardinal and ordinal number formatting
        if (ignoreFieldLocale(field))
            return null;

        // WORDSNET-4241 Use the culture specified in the first character of the field code.
        int languageId = field.getLocaleId();
        if (languageId == RunPr.PROCESS_OR_USER_DEFAULT_LANGUAGE_ID)
            return null;

        // RK I convert LCID to culture name here at this level because such conversion is not easily available
        // in Java at the framework level.
        return LocaleConverter.localeToDocxTag(languageId);
    }

    private static boolean ignoreFieldLocale(Field field)
    {
        if (field.getFieldCodeCache().hasSwitch("\\@"))
            return false;

        if (FieldUtil.alwaysConsidersLocale(field.getType()))
            return false;

        /*GeneralFormat*/int numericFormat = field.getFormat().getGeneralFormats().getNumericFormat();
        switch (numericFormat)
        {
               case GeneralFormat.CARD_TEXT:
               case GeneralFormat.ORD_TEXT:
               case GeneralFormat.ORDINAL:
               case GeneralFormat.DOLLAR_TEXT:
                   return false;
               default:
                   return true;
        }
    }

    /// <summary>
    /// Performs actual update of the specified field's result using the current thread's culture.
    /// Returns a value indicating whether the field's result was changed during the update.
    /// </summary>
    private static boolean updateFieldUsingCurrentCulture(Field field) throws Exception
    {
        // WORDSNET-10517, WORDSNET-21236 MS Word keeps bookmark nodes within field result during field update.
        FieldResultBookmarsPreserver bookmarksPreserver = new FieldResultBookmarsPreserver(field);

        TocEntriesBookmarkDeferredRemover bookmarkDeferredRemover = field.getUpdater().mTocEntriesBookmarkDeferredRemover;
        bookmarkDeferredRemover.freezeBookmarks(field);

        FieldUpdateAction action = field.getFieldCodeCache().hasParseError()
            ? new FieldUpdateActionInsertErrorMessage(field, field.getFieldCodeCache().getParseErrorMessage())
            : field.updateCore();

        // If the field result's removal is required, we should not postpone it, since the field result
        // affects the parent field's code and hence its result either. Moreover, it does not make any
        // sense to update the field result in this case.
        //
        // Other cleanup actions can be safely postponed to reduce parent field code reparsings.
        //
        if ((field.getUpdateContext().getCleanupActions() & FieldUpdateCleanupActions.REMOVE_FIELD_RESULT) != 0)
        {
            // If the field does not have any result, do nothing.
            if (!field.hasSeparator() || (field.getSeparator().getNextSibling() == field.getEnd()))
                return false;

            field.removeFieldResult();
            return true;
        }

        if (action == null)
            return false;

        action.perform();

        // WORDSNET-10517, WORDSNET-21236 MS Word keeps bookmark nodes within field result during field update.
        bookmarksPreserver.restore();

        // WORDSNET-13586 Restore bookmarks in field result used by PAGEREF fields in TOC entries which update is deferred.
        bookmarkDeferredRemover.restoreFrozenBookmarks(field);

        field.isDirty(false);

        return true;
    }

    private static void raiseFieldUpdatingEvent(Field field, FieldUpdater updater)
    {
        IFieldUpdatingCallback callback = updater.getDocument().getFieldOptions().getFieldUpdatingCallback();
        if (callback == null)
            return;

        callback.fieldUpdating(field);
    }

    private static void raiseFieldUpdatedEvent(Field field, FieldUpdater updater)
    {
        IFieldUpdatingCallback callback = updater.getDocument().getFieldOptions().getFieldUpdatingCallback();
        if (callback == null)
            return;

        callback.fieldUpdated(field);
    }

    private void processFieldResultChange(FieldUpdateContext context, boolean notifyFieldResultChanged) throws Exception
    {
        reflowLayoutOrDefer(context.getField());

        // Notify the context.
        if (notifyFieldResultChanged)
            context.notifyFieldAreaChanged(FieldArea.RESULT);

        if (context.hasParentContext())
        {
            // If the field is nested to another field's code, then its result affects string representation of
            // the corresponding field argument. Invalidate it, it to be re-read on the next demand if any.
            if (!FieldUtil.isFieldResultInvisible(context.getField()))
                context.getParentArgument().invalidateText();

            context.getParentContext().notifyChildFieldUpdated(context);
        }
    }

    private void reflowLayoutOrDefer(Field field)
    {
        // FOSS
    }

    private static boolean requiresImmediateReflowLayout(Field field)
    {
        if (field.getType() != FieldType.FIELD_INDEX)
            return false;

        NodeRange result = field.getFieldResultRange();
        for (Node node : result)
        {
            if (node.getNodeType() == NodeType.SECTION)
                return true;
        }

        return false;
    }

    static boolean insertFieldResultFieldsIfNeeded(FieldUpdateContext sourceContext) throws Exception
    {
        if (!FieldUtil.requiresUpdateFieldsInResult(sourceContext.getField().getType()))
            return false;

        InsertFieldResultFieldHelper helper = new InsertFieldResultFieldHelper(sourceContext);
        helper.extract(sourceContext.getField().getFieldResultRange());
        return true;
    }

    private FieldUpdateContext insertFieldResultField(FieldUpdateContext sourceContext, Field field) throws Exception
    {
        beginInsertFieldToUpdate();
        FieldUpdateContext context = sourceContext.insertFieldResultContextAfter(field);
        endInsertFieldToUpdate(context);

        return context;
    }

    /// <summary>
    /// Performs finalization steps of the specified field's update process.
    /// </summary>
    private static void endUpdateField(FieldUpdateContext context, FieldUpdater updater) throws Exception
    {
        // Notify the context.
        context.notifyUpdatePerformed();

        performCleanup(context, updater);

        // If the context is topmost, then everything is done at this point and we can safely remove all of
        // the references to the context and its children, so GC could eat them.
        if (!context.hasParentContext())
        {
            releaseContextReferences(context);
            removeChild(context);
        }
    }

    private static void releaseContextReferences(FieldUpdateContext context) throws Exception
    {
        if (context.hasChildren())
        {
            for (FieldUpdateContext childContext : (Iterable<FieldUpdateContext>) context)
                releaseContextReferences(childContext);
        }

        if (!context.isUpdateSkipped())
            context.getField().endUpdate();
    }

    private static void performCleanup(FieldUpdateContext context, FieldUpdater fieldUpdater) throws Exception
    {
        // Retrieve paragraph before replace field with its result.
        Paragraph containingParagraph = context.getField().getStart().getParentParagraph() == context.getField().getEnd().getParentParagraph()
            ? context.getField().getStart().getParentParagraph()
            : null;

        boolean removeFieldCodePerformed = performRemoveFieldCode(context, fieldUpdater);

        if (needRemoveEmptyParagraph(containingParagraph, context))
        {
            if (fieldUpdater.mFieldCleaner.removeEmptyParagraph(containingParagraph))
                context.invalidateParentContextFieldCode();
        }

        if (removeFieldCodePerformed)
            context.notifyCleanupPerformed();
    }

    private static boolean needRemoveEmptyParagraph(Paragraph paragraph, FieldUpdateContext context)
    {
        if (paragraph == null)
            return false;

        if ((context.getCleanupActions() & FieldUpdateCleanupActions.REMOVE_CONTAINING_PARAGRAPH_IF_EMPTY) == 0)
            return false;

        if (!isCleanableParagraph(paragraph, context))
            return false;

        if (!WordUtil.paragraphCanBeRemoved(paragraph))
            return false;

        return true;
    }

    private static boolean isCleanableParagraph(Paragraph paragraph, FieldUpdateContext context)
    {
        boolean punctuationMarksAllowed = (context.getCleanupActions() & FieldUpdateCleanupActions.REMOVE_CONTAINING_PARAGRAPH_WITH_PUNCTUATION_MARKS) != 0;
        for (Node childNode = paragraph.getFirstChild(); childNode != null; childNode = childNode.getNextSibling())
        {
            if (NodeUtil.isWhitespace(childNode))
                continue;

            if (!punctuationMarksAllowed)
                return false;

            if (childNode.getNodeType() != NodeType.RUN)
                return false;

            String childText = childNode.getText();
            for (int i = 0; i < childText.length(); i++)
            {
            	char c = childText.charAt(i);
                if (msChar.isWhiteSpace(c))
                    continue;

                if (StringUtil.isPunctuationMark(c))
                    continue;

                return false;
            }
        }

        return true;
    }

    private static boolean performRemoveFieldCode(FieldUpdateContext context, FieldUpdater fieldUpdater) throws Exception
    {
        if (context.isTopmostCleanableContext())
            return performPostponedRemoveFieldCode(context, fieldUpdater);

        // WORDSNET-12486 Perform field code removing only for field with empty result. Otherwise we need invalidate parent field code.
        // But it makes mail merge extremely slow.
        if ((msString.equals(context.getField().getResult(), "")) && removeFieldCode(context, fieldUpdater))
            return true;

        return false;
    }

    private static boolean performPostponedRemoveFieldCode(FieldUpdateContext context, FieldUpdater fieldUpdater) throws Exception
    {
        if (removeFieldCode(context, fieldUpdater))
            return true;

        if (context.hasChildren())
        {
            for (FieldUpdateContext childContext : (Iterable<FieldUpdateContext>) context)
                performPostponedRemoveFieldCode(childContext, fieldUpdater);
        }

        return false;
    }

    private static boolean removeFieldCode(FieldUpdateContext context, FieldUpdater fieldUpdater) throws Exception
    {
        if ((context.getCleanupActions() & FieldUpdateCleanupActions.REMOVE_FIELD_CODE) != 0)
        {
            return fieldUpdater.mFieldCleaner.removeFieldCode(context);
        }

        return false;
    }

    /// <summary>
    /// Notifies the updater about that the specified field updated by it is replaced with its result
    /// or completely removed.
    /// </summary>
    void notifyFieldReplaced(Field field, boolean isFieldResultRemoved) throws Exception
    {
        // Notify the context.
        FieldUpdateContext context = field.getUpdateContext();
        context.notifyFieldAreaChanged(FieldArea.CODE);

        if (isFieldResultRemoved)
            processFieldResultChange(context, true);

        // If the field could not be replaced safely (see FieldUpdateContext.IsSafeReplacingPossible for details),
        // then its replacing invalidates its parent field's code (if any).
        if (context.hasParentContext() && !context.isSafeReplacingPossible())
            context.getParentContext().getField().invalidateFieldCodeCache();
    }

    /// <summary>
    /// Returns display range of the field. This method is called by layout whenever it needs to know visible field result.
    /// </summary>
    /// <returns>An object containing display range and its meaning or null if the existing field range
    /// should be displayed.</returns>
    static FieldDisplayResult getFieldDisplayResult(
        Field field,
        FieldDisplayContext displayContext,
        /*NullableBool*/byte isTopmostField) throws Exception
    {
        FieldDisplayResult result = getFieldDisplayResultCore(field, displayContext, isTopmostField);

        // Forcibly call EndFieldUpdate if it was not called inside the invocation above.
        // This can happen if the fake result was used or the field does not have a separator.
        if (field.isUpdating())
            endUpdateField(field.getUpdateContext(), field.getUpdater());

        return result;
    }

    private static FieldDisplayResult getFieldDisplayResultCore(
        Field field,
        FieldDisplayContext displayContext,
        /*NullableBool*/byte isTopmostField) throws Exception
    {
        Debug.assertTrue(displayContext != null);

        // WORDSNET-18421 This handles situation when a FWR field contains a FCL field.
        /*FieldUpdateResults*/int updateResults = updateField(field, displayContext, isTopmostField);

        return getFieldDisplayResultCore(field, updateResults);
    }

    private static FieldDisplayResult getFieldDisplayResultCore(Field field, /*FieldUpdateResults*/int updateResults) throws Exception
    {
        NodeRange fakeResult = field.getFakeResult();
        if (fakeResult != null)
            return new FieldDisplayResult(fakeResult, FieldDisplayRangeType.FAKE_RESULT);

        // WORDSNET-6779 Ignore field without separator that somehow reached this point.
        if (!field.hasSeparator())
            return null;

        // If there was no actual update, return null to signalize no nodes were changed.
        if (updateResults == FieldUpdateResults.NONE)
            return null;

        if (FieldUtil.isFieldResultInvisible(field))
            return null;

        if (field.isRemoved() || ((updateResults & FieldUpdateResults.FIELD_CODE_CHANGED) != 0))
            return new FieldDisplayResult(field.getFieldRange(), FieldDisplayRangeType.WHOLE_FIELD);

        return new FieldDisplayResult(field.getFieldResultRange(), FieldDisplayRangeType.FIELD_RESULT);
    }

    /// <summary>
    /// Updates the specified field using the given display context. Updates the field in a full cycle if it is
    /// not being updated already. Otherwise, updates the field in a common way.
    /// </summary>
    private static /*FieldUpdateResults*/int updateField(Field field, FieldDisplayContext displayContext, /*NullableBool*/byte isTopmostField) throws Exception
    {
        // Ensure display context, it can not be null at this point.
        Debug.assertTrue(displayContext != null);

        // Only fields located in header/footer can be updated in a full cycle at this point.
        Debug.assertTrue(field.isUpdating() || field.isInHeaderFooter());

        if (!field.isUpdating())
            return updateFieldFullCycle(field, displayContext, isTopmostField);

        // If the field is being updated already, then the same display context should be passed during
        // its roundtrip. Ensure this.
        Debug.assertTrue(field.getUpdater().getDisplayContext() == displayContext);

        return updateFieldCore(field.getUpdateContext());
    }

    /// <summary>
    /// Performs finalization steps for a field update session.
    /// </summary>
    private void finalizeUpdateFields() throws Exception
    {
        mTocEntriesBookmarkDeferredRemover.removeFrozenBookmarks();
        mFieldCleaner.finalizeCleanup();
        removeDeferredFields();
        clearBookmarkCache();
        reflowLayoutIfNeeded();
        notifyUpdateProgressChildrenFieldsAreUpdated();
        releaseFieldUpdateProgressContext();
    }

    /// <summary>
    /// Removes fields which removal was previously deferred.
    /// </summary>
    private void removeDeferredFields() throws Exception
    {
        if ((mDeferredFieldsToRemove == null) || (mDeferredFieldsToRemove.size() == 0))
            return;

        for (Field field : mDeferredFieldsToRemove)
            field.remove();

        mDeferredFieldsToRemove.clear();
    }

    /// <summary>
    /// Clears the bookmark cache.
    /// </summary>
    private void clearBookmarkCache()
    {
        if (!mInternalCreatedBookmarkCache)
            return;

        getBookmarkCache().clear();
    }

    /// <summary>
    /// Reflows document layout if it is present and this action was requested previously.
    /// </summary>
    private void reflowLayoutIfNeeded()
    {
        if (!mNeedReflowLayout)
            return;

        reflowLayout();
    }

    void reflowLayout()
    {
        // FOSS

        mNeedReflowLayout = false;
    }

    /// <summary>
    /// Requests an external action to perform immediately.
    /// </summary>
    void requestExternalAction(IExternalAction action) throws Exception
    {
        requestExternalAction(action, getCurrentStage());
    }

    /// <summary>
    /// Requests an external action to perform on the specified field update stage.
    /// </summary>
    void requestExternalAction(IExternalAction action, /*FieldUpdateStage*/int stage) throws Exception
    {
        if (getExternalActions().add(stage, action) && (stage == getCurrentStage()))
            action.perform();
    }

    /// <summary>
    /// Defers the specified field's removal until all of the fields being updated in this session
    /// are completely updated.
    /// </summary>
    void deferRemove(Field field)
    {
        // Deferred removal is available only for fields which results are invisible.
        // Otherwise, it would corrupt the layout.
        Debug.assertTrue(FieldUtil.isFieldResultInvisible(field));

        // Create on the first demand.
        if (mDeferredFieldsToRemove == null)
            mDeferredFieldsToRemove = new ArrayList<Field>();

        msArrayList.add(mDeferredFieldsToRemove, field);
    }

    /// <summary>
    /// Gets a bookmark for the bookmark start from the cache.
    /// </summary>
    Bookmark getCachedBookmark(String bookmarkName) throws Exception
    {
        return getBookmarkCache().get(bookmarkName);
    }

    /// <summary>
    /// Removes fields of the specified types contained within the given node.
    /// </summary>
    static void removeFields(Node node, /*params*/ /*FieldType*/int... fieldTypes) throws Exception
    {
        if ((fieldTypes == null) || (fieldTypes.length == 0))
            return;

        // We should build an update tree before fields' removal, FieldReplacer to be capable to determine
        // whether a particular field should be escaped while its removal.
        FieldUpdater updater = new FieldUpdater(node.fetchDocument());
        updater.addTopmostFieldsToUpdate(node);
        removeFields(updater, fieldTypes);
    }

    private static void removeFields(FieldUpdateContextContainer container, /*FieldType*/int[] fieldTypes) throws Exception
    {
        for (FieldUpdateContext context : container)
        {
            if (msArray.indexOf((fieldTypes), (context.getField().getType()), 0, (fieldTypes).length) != -1)
            {
                // Remove the field. Do not process child contexts since their fields are to be removed either.
                context.getField().remove();
            }
            else
            {
                // Process child contexts.
                removeFields(context, fieldTypes);
            }
        }
    }

    /// <summary>
    /// Gets a value indicating whether this update session is initiated by layout.
    /// </summary>
    boolean isUpdateInitiatedByLayout() { return mIsUpdateInitiatedByLayout; };

    private boolean mIsUpdateInitiatedByLayout;

    /// <summary>
    /// Gets display context defined for this session.
    /// </summary>
    FieldDisplayContext getDisplayContext() { return mDisplayContext; }; private void setDisplayContext(FieldDisplayContext value) { mDisplayContext = value; };

    private FieldDisplayContext mDisplayContext;

    /// <summary>
    /// Gets the current update stage.
    /// </summary>
    /*FieldUpdateStage*/int getCurrentStage() { return mCurrentStage; };
    private void setCurrentStage(/*FieldUpdateStage*/int value) { mCurrentStage = value; };

    private /*FieldUpdateStage*/int mCurrentStage;

    /// <summary>
    /// Gets a value indicating whether some field is being inserted to the field update tree at the moment.
    /// </summary>
    boolean isFieldToUpdateInserting() { return mIsFieldToUpdateInserting; }; private void isFieldToUpdateInserting(boolean value) { mIsFieldToUpdateInserting = value; };

    private boolean mIsFieldToUpdateInserting;

    /// <summary>
    /// Gets a collection of <see cref="IFieldUpdateListener"/> objects attached to this instance.
    /// </summary>
    FieldUpdateListenerCollection getListeners() { return mListeners; };

    private FieldUpdateListenerCollection mListeners;

    /// <summary>
    /// Gets a collection of <see cref="IFieldUpdateDataProvider"/> objects attached to this instance.
    /// </summary>
    FieldUpdateDataProvider getDataProviders() { return mDataProviders; };

    private FieldUpdateDataProvider mDataProviders;

    BookmarkCache getBookmarkCache() { return mBookmarkCache; };

    private BookmarkCache mBookmarkCache;

    Document getDocument() { return mDocument; };

    private Document mDocument;

    private ExternalActionCollection getExternalActions() { return mSession.getExternalActions(); }

    HiddenAttributeCache getHiddenAttributeCache() { return mSession.getHiddenAttributeCache(); }

    ImportedStylesIstdsCollector getImportedStylesIstdsCollector() { return mSession.getImportedStylesIstdsCollector(); }

    AutoTextEntryExtractor getAutoTextEntryExtractor() { return mSession.getAutoTextEntryExtractor(); }

    void freezeTocEntryBookmark(String bookmarkName)
    {
        mTocEntriesBookmarkDeferredRemover.registerBookmark(bookmarkName);
    }

    /// <summary>
    /// Helps to add a range of topmost fields to update them by the updater.
    /// </summary>
    private static class AddTopmostFieldHelper extends FieldExtractor
    {
        AddTopmostFieldHelper(FieldUpdater updater, /*NullableBool*/byte isTopmostField)
        {
            mUpdater = updater;
            mIsTopmostField = isTopmostField;
        }

        protected /*override*/ void onFieldExtracted() throws Exception
        {
            if (!isInField())
                mUpdater.addTopmostFieldToUpdate(getCurrentField(), mIsTopmostField);
        }

        private /*final*/ FieldUpdater mUpdater;
        private /*final*/ /*NullableBool*/byte mIsTopmostField;
    }

    /// <summary>
    /// Helps to remove a range of topmost fields to prevent update them by the updater.
    /// </summary>
    private static class RemoveTopmostFieldHelper extends FieldExtractor
    {
        RemoveTopmostFieldHelper()
        {
            mFieldStarts = new ArrayList<FieldStart>();
        }

        protected /*override*/ void onFieldExtracted()
        {
            if (!isInField())
                msArrayList.add(getFieldStarts(), getCurrentField().getStart());
        }

        public ArrayList<FieldStart> getFieldStarts() { return mFieldStarts; };

        private ArrayList<FieldStart> mFieldStarts;
    }

    /// <summary>
    /// Helps to add topmost fields contained in another field's result to update them by the updater.
    /// </summary>
    private static class InsertFieldResultFieldHelper extends FieldExtractor
    {
        InsertFieldResultFieldHelper(FieldUpdateContext sourceContext)
        {
            mSourceContext = sourceContext;
        }

        protected /*override*/ void onFieldExtracted() throws Exception
        {
            if (isInField())
                return;

            FieldUpdateContext newContext = mSourceContext.getUpdater().insertFieldResultField(mSourceContext, getCurrentField());
            if (newContext != null)
                mSourceContext = newContext;
        }

        private FieldUpdateContext mSourceContext;
    }

    private /*final*/ IFieldCleaner mFieldCleaner;
    private /*final*/ boolean mInternalCreatedBookmarkCache;
    private /*final*/ TocEntriesBookmarkDeferredRemover mTocEntriesBookmarkDeferredRemover = new TocEntriesBookmarkDeferredRemover();

    private /*final*/ boolean mIsUpdateBeforeSave;
    private ArrayList<Field> mDeferredFieldsToRemove;
    private /*final*/ FieldUpdateSession mSession;
    private boolean mNeedReflowLayout;
    private /*final*/ boolean mIsUpdateDirtyFields;

}

