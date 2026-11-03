// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/08/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IDisposable;
import com.aspose.NullableBool;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Passes data corresponding to a single field among objects in the field evaluation module. Represents a branch or
/// leaf node of a field update tree. See <see cref="FieldUpdateContextContainer"/> remarks for details.
/// </summary>
final class FieldUpdateContext extends FieldUpdateContextContainer implements IDisposable
{
    /// <summary>
    /// Creates and appends the topmost context to the field update tree of the specified updater.
    /// Returns the created instance or <c>null</c> if the context creating was rejected by some update listener.
    /// </summary>
    static FieldUpdateContext appendTopmostContext(
        Field field,
        FieldUpdater updater,
        /*NullableBool*/byte isTopmostField) throws Exception
    {
        FieldUpdateContext context = insertContext(field, false, updater, null, true, isTopmostField);
        if (context != null)
            context.isSafeReplacingPossible(true);

        return context;
    }

    public void dispose()
    {
        closeResources();
    }

    private void closeResources()
    {
        if (mOldResultRemover != null)
        {
            mOldResultRemover.dispose();
            mOldResultRemover = null;
        }
    }

    /// <summary>
    /// Creates and inserts a child context to the specified parent container before or after the given
    /// reference child context.
    /// Returns the created instance or <c>null</c> if the context creating was rejected by some update listener.
    /// </summary>
    private static FieldUpdateContext insertContext(
        Field field,
        boolean isInFieldResult,
        FieldUpdateContextContainer parent,
        FieldUpdateContext refChild,
        boolean isAfter,
        /*NullableBool*/byte isTopmostField) throws Exception
    {
        FieldUpdater updater = getUpdater(parent);
        Field parentField = isContext(parent)
            ? ((FieldUpdateContext)parent).getField()
            : null;
        FieldUpdateListenerResponse listenerResponse = updater.getListeners().notifyUpdating(field, parentField);

        if (listenerResponse != null)
        {
            field = listenerResponse.getField();
        }
        else
        {
            listenerResponse = FieldUpdateListenerResponse.DEFAULT;
        }

        FieldUpdateContext context = new FieldUpdateContext(
            field,
            isInFieldResult,
            listenerResponse,
            parentField != null ? NullableBool.FALSE : isTopmostField);

        insertChild(parent, refChild, context, isAfter);
        context.registerCleanupActions(listenerResponse.getBaseCleanupActions());

        if (!context.isUpdateSkipped())
        {
            // The following call fills the field update tree downward recursively.
            context.getField().beginUpdate(context);

            if (updater.getDocument().getFieldUpdateProgressContext() != null)
                updater.getDocument().getFieldUpdateProgressContext().onFieldToBeUpdated();
        }

        if (FieldUtil.getEffectiveFieldLocked(field))
            FieldUpdater.insertFieldResultFieldsIfNeeded(context);

        return context;
    }

    /// <summary>
    /// Returns an updater for the specified container.
    /// </summary>
    private static FieldUpdater getUpdater(FieldUpdateContextContainer container)
    {
        while (isContext(container))
            container = container.getParent();

        return (FieldUpdater)container;
    }

    /// <summary>
    /// Returns a value indicating whether the specified container is context.
    /// </summary>
    private static boolean isContext(FieldUpdateContextContainer container)
    {
        return (container.getParent() != null);
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    private FieldUpdateContext(
        Field field,
        boolean isInFieldResult,
        FieldUpdateListenerResponse listenerResponse,
        /*NullableBool*/byte isTopmostField)
    {
        setCleanupActions(FieldUpdateCleanupActions.NONE);
        setUpdateResults(FieldUpdateResults.NONE);

        mField = field;
        mIsInFieldResult = isInFieldResult;
        mRequiresImmediateUpdate = listenerResponse.getRequiresImmediateUpdate();
        mIsTopmostField = isTopmostField;
        isAutoUpdatableBeforeSave(NullableBool.NOT_DEFINED);

        switch (listenerResponse.getPreferredStageStrategy())
        {
            case FieldUpdatePreferredStageStrategy.MAIN_LOOP:
                setStageToDefer(FieldUpdateStage.MAIN_LOOP);
                break;
            case FieldUpdatePreferredStageStrategy.SKIP:
                // WORDSNET-9610 We should keep a context for a field which update is rejected by some listener in field update tree.
                // Otherwise, collisions may take place in case when a nested mail merge region captures an outer field either.
                // The example:
                //
                // [ IF [ MERGEFIELD parentField ] = 1 [ MERGEFIELD TableStart:ChildTable ]
                // [ MERGEFIELD childField ]
                // [ MERGEFIELD TableEnd:ChildTable ]" ]
                //
                // In this case IF's field code is invalidated when parentField is merged. And if we did not keep a context
                // for childField in a tree, then childField would be recognized as newly added field (see NotifyChildFieldEncountered)
                // and merged in the context of parent data source, which is wrong.
                //
                // So instantiate the context but remember that an update of the corresponding field should be skipped.
                mIsUpdateSkipped = true;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /// <summary>
    /// Notifies the context about that the corresponding field's code is about to be parsed.
    /// </summary>
    void notifyFieldCodeParsing()
    {
        // We should recalculate aggregate data, so reset it here.
        resetAggregateData();

        // Deactivate children if any. Deactivation means, that child contexts are unbound from the corresponding
        // parent field arguments. They are not removed from the parent context not to lose child fields' update
        // states. They are to be activated (i.e. bound to some parent field arguments) while parent field code
        // parsing. Inactive child contexts will be removed from the parent context only after the parent field
        // code is completely parsed.
        if (hasChildren())
        {
            for (FieldUpdateContext childContext : (Iterable<FieldUpdateContext>) this)
            {
                childContext.mParentArgument = null;
                childContext.isSafeReplacingPossible(childContext.isInFieldResult());
            }
        }
    }

    /// <summary>
    /// Restores initial state of this context's aggregate data (i.e. when there is no child context detected).
    /// </summary>
    private void resetAggregateData()
    {
        mHasAnyValueProvidedByLayout = FieldUtil.isComputedByLayout(getField().getType());
    }

    /// <summary>
    /// Notifies the context about that the corresponding field's direct child field has been encountered
    /// while field's code parsing.
    /// </summary>
    void notifyChildFieldEncountered(FieldStart start, FieldSeparator separator, FieldEnd end) throws Exception
    {
        Debug.assertTrue(start != null);
        Debug.assertTrue(end != null);
        Debug.assertTrue(end.hasSeparator() == (separator != null));

        if (!tryActivateInactiveChildContext(start))
            tryActivateNewChildContext(start, separator, end);
    }

    /// <summary>
    /// Searches for the corresponding inactive child context and activates it if found. Returns a value indicating
    /// whether the context was found and activated.
    /// </summary>
    private boolean tryActivateInactiveChildContext(FieldStart start)
    {
        // If the context does not have any children or it is being inserted to a field update tree, it definitely
        // does not contain inactive children.
        if (!hasChildren() || getUpdater().isFieldToUpdateInserting())
            return false;

        /*ChildContextActivationStage*/int stage = mLastActivatedChildContext == null
            ? ChildContextActivationStage.SEARCH
            : ChildContextActivationStage.INIT_SEARCH;

        for (FieldUpdateContext childContext : (Iterable<FieldUpdateContext>) this)
        {
            switch (stage)
            {
                case ChildContextActivationStage.INIT_SEARCH:
                {
                    // We should not consider inactive child contexts before the last encountered one,
                    // since child contexts' order is important.
                    if (childContext == mLastActivatedChildContext)
                        stage = ChildContextActivationStage.SEARCH;
                    break;
                }
                case ChildContextActivationStage.SEARCH:
                {
                    if (childContext.getField().getStart() == start)
                    {
                        // Corresponding inactive child context is found. Activate it.
                        activateChildContext(childContext);

                        // If the field requires fields in its result to be updated, search and activate
                        // their contexts either.
                        if (!FieldUtil.requiresUpdateFieldsInResult(childContext.getField().getType()))
                            return true;

                        stage = ChildContextActivationStage.ACTIVATE_DEPENDANTS;
                    }
                    break;
                }
                case ChildContextActivationStage.ACTIVATE_DEPENDANTS:
                {
                    // Quit on the first independent child context encountered.
                    if (!childContext.isInFieldResult())
                        return true;

                    activateChildContext(childContext);
                    break;
                }
                default:
                    throw new IllegalArgumentException();
            }
        }

        // If we have stopped while activating dependent child contexts, then the activation succeeded.
        // Otherwise, corresponding inactive child context was not found.
        return stage == ChildContextActivationStage.ACTIVATE_DEPENDANTS;
    }

    /// <summary>
    /// Tries to create and activate a new child context. Returns a value indicating whether the context
    /// was created and activated.
    /// </summary>
    private void tryActivateNewChildContext(FieldStart start, FieldSeparator separator, FieldEnd end) throws Exception
    {
        Field childField = FieldFactory.createField(start, separator, end);

        // If we have encountered any child context we should add new child context after the last encountered one.
        // Otherwise, we should prepend new child context, since this context can contain inactive children.
        boolean isAfter = mLastActivatedChildContext != null;

        FieldUpdateContext childContext = insertContext(childField, false, this, mLastActivatedChildContext, isAfter, mIsTopmostField);
        if (childContext == null)
            return;

        activateChildContext(childContext);
    }

    /// <summary>
    /// Activates the specified child context (i.e. binds it to the pending parent field argument).
    /// </summary>
    private void activateChildContext(FieldUpdateContext childContext)
    {
        childContext.mParentArgument = G_PENDING_ARGUMENT_PLACEHOLDER;
        if (isInDoubleQuotes())
            childContext.isSafeReplacingPossible(true);

        // Make this context consider the specified child context's aggregate data as well.
        aggregateChildContextData(childContext);

        // Remember the last activated child context so the next one could be inserted after it.
        mLastActivatedChildContext = childContext;
    }

    private void aggregateChildContextData(FieldUpdateContext childContext)
    {
        if (childContext.mHasAnyValueProvidedByLayout)
            mHasAnyValueProvidedByLayout = true;
    }

    /// <summary>
    /// Notifies the context about that the field argument has been created while field code parsing.
    /// </summary>
    void notifyArgumentAdded(FieldArgument argument)
    {
        if (!hasChildren())
            return;

        // Replace pending argument placeholders with the real argument for all child contexts.
        for (FieldUpdateContext childContext : (Iterable<FieldUpdateContext>) this)
        {
            if (childContext.mParentArgument == G_PENDING_ARGUMENT_PLACEHOLDER)
                childContext.mParentArgument = argument;
        }
    }

    /// <summary>
    /// Notifies the context about that the corresponding field's code has been parsed.
    /// </summary>
    void notifyFieldCodeParsed()
    {
        // Reset the last activated child context.
        mLastActivatedChildContext = null;

        if (hasChildren())
        {
            for (FieldUpdateContext childContext : (Iterable<FieldUpdateContext>) this)
            {
                // At this point we should replace all pending argument placeholders with real arguments. If this condition
                // is not met, then we must deal with a field, which does not provide the field's type through its code and
                // hence its first argument is recognized as field type by the field code parser. So we have to capture
                // the whole field in this case to be able to invalidate its cached field type when needed.
                if (childContext.mParentArgument == G_PENDING_ARGUMENT_PLACEHOLDER)
                    childContext.mParentArgument = new UnknownFieldArgument(getField());

                // If the context was populated by children before the field code parse then it can contain child contexts
                // for fields, which were removed from the parent field code (i.e. inactive children). Remove them here.
                if (childContext.mParentArgument == null)
                    removeChild(childContext);
            }
        }

        // If the context is being added to a field update tree, its aggregate data is calculated "automatically".
        // In other cases we should manually recalculate aggregate data for all ancestor contexts.
        if (!getUpdater().isFieldToUpdateInserting())
        {
            for (FieldUpdateContext parentContext = getParentContext();
                 parentContext != null;
                 parentContext = parentContext.getParentContext())
            {
                parentContext.recalcAggregateData();
            }
        }
    }

    private void recalcAggregateData()
    {
        resetAggregateData();

        if (hasChildren())
        {
            for (FieldUpdateContext childContext : (Iterable<FieldUpdateContext>) this)
                aggregateChildContextData(childContext);
        }
    }

    /// <summary>
    /// Creates and inserts a context for the specified field contained in another field's result to a field update
    /// tree after this context.
    /// Returns the created instance or <c>null</c> if the context creating was rejected by some update listener.
    /// </summary>
    /// <remarks>
    /// Contexts corresponding to fields which are located in other fields' results are inserted as siblings to these
    /// fields' contexts because of the way they affect parent fields' codes. Look at an example. Field A contains
    /// field B in its field code. Field B contains field C in its result. When field C is updated it does not affect
    /// field B's field code. But it affects field A's field code in the same way as updating of field B would.
    /// That's why it is simpler and more convenient to consider contexts of field B and field C as siblings rather
    /// than a parent and a child.
    /// </remarks>
    FieldUpdateContext insertFieldResultContextAfter(Field field) throws Exception
    {
        FieldUpdateContext context = insertContext(field, true, getParent(), this, true, mIsTopmostField);
        if (context == null)
            return null;

        context.mParentArgument = mParentArgument;
        context.isSafeReplacingPossible(true); // This is because the field is contained in another field's result.

        // Aggregate new context's data to all ancestor contexts.
        for (FieldUpdateContext childContext = context, parentContext = context.getParentContext();
             parentContext != null;
             childContext = parentContext, parentContext = parentContext.getParentContext())
        {
            parentContext.aggregateChildContextData(childContext);
        }

        return context;
    }

    /// <summary>
    /// Notifies the context about that all of the corresponding field's child fields which should be updated
    /// according to the field's update logic have been updated.
    /// </summary>
    void notifyAllChildFieldsUpdated() throws Exception
    {
        // It is important to calculate the field update stage after all of its child fields are updated,
        // since their results can affect the calculation.
        /*FieldUpdateStage*/int stage = getField().getUpdateStage();
        if (getStageToDefer() < stage)
            setStageToDefer(stage);

        setAreAllChildFieldsUpdated(true);

        notifyUpdateProgressChildrenFieldsAreUpdated();
    }

    void notifyChildFieldUpdated(FieldUpdateContext childContext) throws Exception
    {
        getField().notifyChildFieldUpdated(childContext.getParentArgument());
    }

    /// <summary>
    /// Notifies the context about that the corresponding field has been updated.
    /// </summary>
    void notifyUpdatePerformed()
    {
        isUpdatePerformed(true);
    }

    /// <summary>
    /// Notifies the context about that the corresponding field's area has been changed.
    /// </summary>
    void notifyFieldAreaChanged(/*FieldArea*/int area)
    {
        switch (area)
        {
            case FieldArea.CODE:
                setUpdateResults(getUpdateResults() | FieldUpdateResults.FIELD_CODE_CHANGED);
                break;
            case FieldArea.RESULT:
                setUpdateResults(getUpdateResults() | FieldUpdateResults.FIELD_RESULT_CHANGED);
                break;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "area");
        }

        // Ancestor fields' codes are changed regardless of what area is changed for a descendant field.
        for (FieldUpdateContext parentContext = getParentContext();
             parentContext != null;
             parentContext = parentContext.getParentContext())
        {
            parentContext.setUpdateResults(parentContext.getUpdateResults() | FieldUpdateResults.FIELD_CODE_CHANGED);
        }
    }

    /// <summary>
    /// Registers cleanup actions for the context to be performed when appropriate.
    /// </summary>
    /// <remarks>
    /// Note, that we allow to register cleanup actions for the context, for which this is requested,
    /// regardless of its <see cref="IsCleanable"/> property value. This implements the principle:
    /// if the cleanup is requested then it should be performed as the caller knows, what it is doing.
    /// </remarks>
    void registerCleanupActions(/*FieldUpdateCleanupActions*/int actions)
    {
        setCleanupActions(getCleanupActions() | actions);

        if (((actions & FieldUpdateCleanupActions.REMOVE_CONTAINING_FIELD_CODE) == 0) || !hasParentContext())
        {
            // If the cleanup actions do not affect parent field code, simply apply them as is.
            return;
        }

        setCleanupActions(getCleanupActions() | (actions & FieldUpdateCleanupActions.REMOVE_FIELD_RESULT));

        // Rearrange cleanup actions to apply them to the topmost cleanable context.
        actions ^= FieldUpdateCleanupActions.REMOVE_CONTAINING_FIELD_CODE;
        actions |= FieldUpdateCleanupActions.REMOVE_FIELD_CODE;

        FieldUpdateContext topmostCleanableContext = getTopmostCleanableContextOrSelf();

        boolean applyRemoveFieldResultActionToTopmostContext = applyRemoveFieldResultActionToTopmostContext(actions, topmostCleanableContext.getCleanupActions());

        topmostCleanableContext.setCleanupActions(topmostCleanableContext.getCleanupActions() | actions);
        // WORDSNET-11549 Apply RemoveFieldResult action only if requested by all nested cleanable contexts.
        if (!applyRemoveFieldResultActionToTopmostContext)
            topmostCleanableContext.setCleanupActions(topmostCleanableContext.getCleanupActions() & (~FieldUpdateCleanupActions.REMOVE_FIELD_RESULT));

        // WORDSNET-23511 Apply RemoveContainingParagraphIfEmpty action to all parent contexts.
        bubbleRemoveContainingParagraphIfEmptyAction(actions);
    }

    private void bubbleRemoveContainingParagraphIfEmptyAction(/*FieldUpdateCleanupActions*/int actions)
    {
        if ((actions & FieldUpdateCleanupActions.REMOVE_CONTAINING_PARAGRAPH_IF_EMPTY) == 0)
            return;

        actions &=
            FieldUpdateCleanupActions.REMOVE_FIELD_CODE |
            FieldUpdateCleanupActions.REMOVE_CONTAINING_PARAGRAPH_IF_EMPTY |
            FieldUpdateCleanupActions.REMOVE_CONTAINING_PARAGRAPH_WITH_PUNCTUATION_MARKS;

        if (actions == FieldUpdateCleanupActions.NONE)
            return;

        for (FieldUpdateContext context : getAncestralCleanableContexts())
            context.setCleanupActions(context.getCleanupActions() | actions);
    }

    private Iterable<FieldUpdateContext> getAncestralCleanableContexts()
    {
        ArrayList<FieldUpdateContext> result = new ArrayList<FieldUpdateContext>();

        FieldUpdateContext parentContext = getParentContext();

        while ((parentContext != null) && parentContext.isCleanable())
        {
            msArrayList.add(result, parentContext);
            parentContext = parentContext.getParentContext();
        }

        return result;
    }

    private static boolean applyRemoveFieldResultActionToTopmostContext(/*FieldUpdateCleanupActions*/int actions, /*FieldUpdateCleanupActions*/int topmostContextActions)
    {
        if ((actions & FieldUpdateCleanupActions.REMOVE_FIELD_RESULT) == 0)
            return false;

        return (topmostContextActions & FieldUpdateCleanupActions.REMOVE_FIELD_RESULT) == FieldUpdateCleanupActions.REMOVE_FIELD_RESULT ||
               topmostContextActions == FieldUpdateCleanupActions.NONE;
    }

    void invalidateParentContextFieldCode()
    {
        if (!hasParentContext())
            return;

        getParentContext().invalidateFieldCode();
    }

    void invalidateFieldCode()
    {
        getField().invalidateFieldCodeCache();
        getField().parseFieldCode();

        invalidateParentContextFieldCode();
    }

    private FieldUpdateContext getTopmostCleanableContextOrSelf()
    {
        FieldUpdateContext topmostCleanableContext = this;

        while ((topmostCleanableContext.getParentContext() != null) && topmostCleanableContext.getParentContext().isCleanable())
            topmostCleanableContext = topmostCleanableContext.getParentContext();

        return topmostCleanableContext;
    }

    /// <summary>
    /// Notifies the context about that all of the registered cleanup actions have been performed.
    /// </summary>
    void notifyCleanupPerformed()
    {
        // Theoretically a context subtree can contain multiple contexts considered as topmost cleanup
        // contexts for different descendant contexts and hence the cleanup can be requested multiple
        // times for a single context. We need to ensure, that cleanup is performed only once for every
        // particular context, so we need to reset cleanup actions after the cleanup is performed.
        setCleanupActions(FieldUpdateCleanupActions.NONE);
    }

    IDisposable removeOldResultSafe() throws Exception
    {
        if (mOldResultRemover == null)
        {
            mOldResultRemover = new NodeRemover(getField().getFieldResultRange(),
                FieldUtil.getFieldOldResultRemovalNodeJoinMode(getField()), false);
            mOldResultRemover.removeCore();
        }

        return new FieldOldResultNodesCombiner(this);
    }

    void joinOldResultNodes()
    {
        if (mOldResultRemover != null)
            mOldResultRemover.joinNodes(FieldUtil.getFieldResultRemovalNodeJoinMode(getField()));
        closeResources();
    }

    /// <summary>
    /// Gets the field being updated.
    /// </summary>
    Field getField() { return mField; };

    private Field mField;

    /// <summary>
    /// Gets a value indicating whether the corresponding field's update should be skipped.
    /// </summary>
    boolean isUpdateSkipped() { return mIsUpdateSkipped; };

    private boolean mIsUpdateSkipped;

    /// <summary>
    /// Gets a value indicating whether the corresponding field is contained within another field's result.
    /// </summary>
    boolean isInFieldResult() { return mIsInFieldResult; };

    private boolean mIsInFieldResult;

    /// <summary>
    /// Gets a value indicating whether the corresponding field should be updated on the stage where it is
    /// supposed to regardless of any conditions.
    /// </summary>
    boolean getRequiresImmediateUpdate() { return mRequiresImmediateUpdate; };

    private boolean mRequiresImmediateUpdate;

    /// <summary>
    /// Gets a field update stage where the corresponding field should be updated.
    /// </summary>
    /*FieldUpdateStage*/int getStageToDefer() { return mStageToDefer; }; private void setStageToDefer(/*FieldUpdateStage*/int value) { mStageToDefer = value; };

    private /*FieldUpdateStage*/int mStageToDefer;

    /// <summary>
    /// Gets a value indicating whether the corresponding field is topmost field.
    /// Non topmost context always refers to non topmost field.
    /// But the topmost context may refer to non topmost field in some cases (i.e. if it's updated directly).
    /// </summary>
    /// <seealso cref="Aspose.Words.Fields.Field.IsTopmostField()"/>
    boolean isTopmostField()
    {
        if (hasParentContext())
            return false;

        if (mIsTopmostField == NullableBool.NOT_DEFINED)
        {
            mIsTopmostField = getField().isTopmostField()
                ? NullableBool.TRUE
                : NullableBool.FALSE;
        }

        return mIsTopmostField == NullableBool.TRUE;
    }

    /// <summary>
    /// Gets a value indicating whether this context has parent context. This is <c>false</c> only for
    /// the topmost contexts.
    /// </summary>
    boolean hasParentContext() { return getParent() != null && isContext(getParent()); }

    /// <summary>
    /// Gets the parent context.
    /// </summary>
    FieldUpdateContext getParentContext() { return hasParentContext() ? (FieldUpdateContext)getParent() : null; }

    FieldUpdateContext getTopmostParentContextOrSelf() { return hasParentContext() ? getParentContext().getTopmostParentContextOrSelf() : this; }

    /// <summary>
    /// Gets a value indicating whether this context is the topmost cleanable context when looking upward
    /// starting from the current context.
    /// </summary>
    /// <remarks>
    /// A cleanable context is a context to which cleanup actions can be applied.
    /// </remarks>
    boolean isTopmostCleanableContext() { return ((this == getTopmostCleanableContextOrSelf()) && isCleanable()); }

    /// <summary>
    /// Gets a value indicating whether this context is cleanable.
    /// </summary>
    /// <remarks>
    /// A cleanable context is a context to which cleanup actions can be applied.
    /// </remarks>
    private boolean isCleanable()
    {
        // Cleanup actions were forcibly set for the context, so its cleanability is overridden.
        if (getCleanupActions() != FieldUpdateCleanupActions.NONE)
            return true;

        // Fields without separator are not cleanable. MS Word does it in this way.
        if (FieldUtil.getSeparatorPresence(getField().getType()) == FieldSeparatorPresence.NEVER)
            return false;

        // WORDSNET-27036 MS Word does not cleanup hyperlinks.
        if (getField().getType() == FieldType.FIELD_HYPERLINK)
            return false;

        // We forcibly skip cleanup for fields in header/footer containing child FCL fields at the same time.
        // There is no way to cleanup such fields in AW at the moment.
        // It is possible to implement it, but it is quite cumbersome, so let's wait for a client request.
        return (getRequiresImmediateUpdate() || !isUpdatedByLayoutExternally());
    }

    /// <summary>
    /// Gets a value indicating whether the corresponding field should be updated by layout externally,
    /// i.e. layout should initiate a distinct update session to update the field.
    /// </summary>
    boolean isUpdatedByLayoutExternally() { return (mHasAnyValueProvidedByLayout && getField().isInHeaderFooter() && !getUpdater().isUpdateInitiatedByLayout()); }

    FieldUpdater getUpdater() { return getUpdater(getParent()); }

    /// <summary>
    /// Gets or sets a value indicating whether the current field token encountered while the corresponding field's code
    /// parsing is located in double quotes.
    /// </summary>
    boolean isInDoubleQuotes() { return mIsInDoubleQuotes; }; void isInDoubleQuotes(boolean value) { mIsInDoubleQuotes = value; };

    private boolean mIsInDoubleQuotes;

    /// <summary>
    /// Gets a parent field's argument to which the corresponding field is related.
    /// </summary>
    IFieldArgument getParentArgument() { return (IFieldArgument)mParentArgument; }

    /*FieldUpdateResults*/int getUpdateResults() { return mUpdateResults; }; private void setUpdateResults(/*FieldUpdateResults*/int value) { mUpdateResults = value; };

    private /*FieldUpdateResults*/int mUpdateResults;

    /// <summary>
    /// Gets cleanup actions registered for the context.
    /// </summary>
    /*FieldUpdateCleanupActions*/int getCleanupActions() { return mCleanupActions; }; private void setCleanupActions(/*FieldUpdateCleanupActions*/int value) { mCleanupActions = value; };

    private /*FieldUpdateCleanupActions*/int mCleanupActions;

    /// <summary>
    /// Gets or sets child fields' update stage. This is used only for contexts which corresponding fields
    /// support conditional update.
    /// </summary>
    /*FieldChildUpdateStage*/int getChildUpdateStage() { return mChildUpdateStage; }; void setChildUpdateStage(/*FieldChildUpdateStage*/int value) { mChildUpdateStage = value; };

    private /*FieldChildUpdateStage*/int mChildUpdateStage;

    /*FieldUpdateStrategy*/int getFieldUpdateStrategy() { return mFieldUpdateStrategy; };
    void setFieldUpdateStrategy(/*FieldUpdateStrategy*/int value) { mFieldUpdateStrategy = value; };

    private /*FieldUpdateStrategy*/int mFieldUpdateStrategy;

    /// <summary>
    /// Gets a value indicating whether the corresponding field can be replaced with its result or completely removed
    /// without any harm to the parent field's code (if any). This is true, if the field is enclosed in double quotes
    /// or contained within another field's result.
    /// </summary>
    boolean isSafeReplacingPossible() { return mIsSafeReplacingPossible; }; private void isSafeReplacingPossible(boolean value) { mIsSafeReplacingPossible = value; };

    private boolean mIsSafeReplacingPossible;

    /// <summary>
    /// Gets a value indicating whether all of the corresponding field's child fields which should be updated
    /// according to the field's update logic were updated.
    /// </summary>
    boolean getAreAllChildFieldsUpdated() { return mAreAllChildFieldsUpdated; };
    private void setAreAllChildFieldsUpdated(boolean value) { mAreAllChildFieldsUpdated = value; };

    private boolean mAreAllChildFieldsUpdated;

    /// <summary>
    /// Gets a value indicating whether the corresponding field was updated.
    /// </summary>
    boolean isUpdatePerformed() { return mIsUpdatePerformed; };
    private void isUpdatePerformed(boolean value) { mIsUpdatePerformed = value; };

    private boolean mIsUpdatePerformed;

    /*NullableBool*/byte isAutoUpdatableBeforeSave() { return mIsAutoUpdatableBeforeSave; }; void isAutoUpdatableBeforeSave(/*NullableBool*/byte value) { mIsAutoUpdatableBeforeSave = value; };

    private /*NullableBool*/byte mIsAutoUpdatableBeforeSave;

    void notifyUpdateProgressCurrentFieldIsUpdated()
    {
        if (mIsUpdateProgressNotified)
            return;

        if (!getField().isUpdating())
            return;

        if (getUpdater().getDocument().getFieldUpdateProgressContext() == null)
            return;

        getUpdater().getDocument().getFieldUpdateProgressContext().onFieldUpdated();
        getUpdater().getDocument().getFieldUpdateProgressContext().notifyInternal();

        mIsUpdateProgressNotified = true;
    }

    /// <summary>
    /// Represents a field argument of a field that does not provide its type through its field code.
    /// </summary>
    private static class UnknownFieldArgument implements IFieldArgument
    {
        UnknownFieldArgument(Field field)
        {
            mField = field;
        }

        public void /*IFieldArgument.*/invalidateText()
        {
            // If the field does not provide its type through its field code then the field's argument text is contained
            // in FieldCode.FieldType. So we have to invalidate the whole field code cache to make it refresh on request.
            // Inefficient, but it seems like we have no other options. Moreover, the case is quite rare.
            mField.invalidateFieldCodeCache();
        }

        private /*final*/ Field mField;
    }

    private final static class FieldOldResultNodesCombiner implements IDisposable
    {
        private /*final*/ FieldUpdateContext mFieldUpdateContext;

        public FieldOldResultNodesCombiner(FieldUpdateContext fieldUpdateContext)
        {
            mFieldUpdateContext = fieldUpdateContext;
        }

        public void dispose()
        {
            mFieldUpdateContext.joinOldResultNodes();
        }
    }

    /// <summary>
    /// Specifies a stage of an inactive child context activation process.
    /// </summary>
    private /*enum*/ final class ChildContextActivationStage
    {
        private ChildContextActivationStage(){}
        
        /// <summary>
        /// Specifies that an inactive child context search is about to be started.
        /// </summary>
        public static final int INIT_SEARCH = 0;
        /// <summary>
        /// Specifies that an inactive child context is being searched for.
        /// </summary>
        public static final int SEARCH = 1;
        /// <summary>
        /// Specifies that dependent inactive child contexts are being activated.
        /// </summary>
        public static final int ACTIVATE_DEPENDANTS = 2;

        public static final int length = 3;
    }

    private boolean mHasAnyValueProvidedByLayout;
    private boolean mIsUpdateProgressNotified;
    private NodeRemover mOldResultRemover;
    private FieldUpdateContext mLastActivatedChildContext;
    private Object mParentArgument;
    private /*NullableBool*/byte mIsTopmostField;

    private static /*final*/ Object G_PENDING_ARGUMENT_PLACEHOLDER = new Object();

}

