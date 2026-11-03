// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/08/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.IDisposable;
import com.aspose.Debug;
import com.aspose.StackExtension;
import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// A complex implementation of field code tokenizer that iterates over a field code represented by nodes.
/// </summary>
final class NodeRangeFieldCodeTokenizer implements IFieldCodeTokenizer, IDisposable
{
    private NodeRangeFieldCodeTokenizer(Field field)
    {
    	this(field, false);
	
    }

    public void dispose()
    {
        if (mNodeEnumerator != null)
            mNodeEnumerator.dispose();
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    NodeRangeFieldCodeTokenizer(Field field, boolean moveToFirstToken)
    {
        // This should be placed here because of Java.
        mExternalObjectInteractionStrategy = new FieldInteractionStrategy(field);

        initialize(field.getStart(), field.getFieldCodeRange(), moveToFirstToken, field.isUpdating() ? field.getUpdater().getHiddenAttributeCache() : null);
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    NodeRangeFieldCodeTokenizer(FieldArgument argument, boolean moveToFirstToken)
    {
        Debug.assertTrue(argument.getField() != null);

        // This should be placed here because of Java.
        mExternalObjectInteractionStrategy = new FieldArgumentInteractionStrategy(argument);

        initialize(argument.getField().getStart(), argument.getRange(), moveToFirstToken,
            argument.getField().isUpdating() ? argument.getField().getUpdater().getHiddenAttributeCache() : null);

        // If the argument refers to a standalone field, then its range corresponds to the field result.
        // So we need to set the tokenizer's state as if it encountered a field separator at this point.
        if (argument.isSingleFieldResult())
            mChildFieldNestingLevel++;
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    NodeRangeFieldCodeTokenizer(FieldStart fieldStart, FieldChar fieldCodeEnd, boolean moveToFirstToken)
    {
        // This should be placed here because of Java.
        mExternalObjectInteractionStrategy = null;

        initialize(fieldStart, new NodeRange(fieldStart, false, fieldCodeEnd, false), moveToFirstToken, null);
    }

    private void initialize(FieldStart fieldStart, NodeRange range, boolean moveToFirstToken, HiddenAttributeCache hiddenAttributeCache)
    {
        mHiddenAttributeCache = (hiddenAttributeCache != null)
            ? hiddenAttributeCache
            : new HiddenAttributeCache();
        mAllowHiddenText = isHidden(fieldStart);
        mNodeEnumerator = new NodeEnumerator(range);
        if (moveToFirstToken)
            moveToNextToken();
    }

    /// <summary>
    /// Returns field code as a plain string.
    /// </summary>
    static String getCodeAsString(Field field)
    {
        StringBuilder builder = new StringBuilder();
        NodeRangeFieldCodeTokenizer tokenizer = new NodeRangeFieldCodeTokenizer(field);
        try /*JAVA: was using*/
        {
            while (tokenizer.moveToNextToken())
            {
                switch (tokenizer.getCurrentToken())
                {
                    case FieldCodeToken.TEXT_CHAR:
                        if (!tokenizer.isEndOfToken())
                            builder.append(tokenizer.getCurrentChar());
                        break;
                    case FieldCodeToken.PARAGRAPH:
                    case FieldCodeToken.SECTION:
                    case FieldCodeToken.NON_TEXT_NODE:
                    case FieldCodeToken.CHILD_FIELD_START:
                    case FieldCodeToken.CHILD_FIELD_SEPARATOR:
                    case FieldCodeToken.CHILD_FIELD_END:
                        // Skip these.
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }
        finally { if (tokenizer != null) tokenizer.dispose(); }

        return builder.toString();
    }

    public boolean moveToNextToken()
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	// WORDSNET-6971 Don't exit on grandchild fields.
        	if (isBeyondChildField() &&
            	(getCurrentToken() == FieldCodeToken.CHILD_FIELD_SEPARATOR) &&
            	(getCurrentNode().getNodeType() == NodeType.FIELD_END))
        	{
            	// We must return a field end token on a next demand for a field which result is forcibly
            	// considered to be empty.
            	setCurrentToken(FieldCodeToken.CHILD_FIELD_END);
            	return true;
        	}

        	// WORDSNET-13861 Tables are required to be enclosed in double quotes when used as IF arguments.
        	// However, do not allow crash when encountering one.
        	Node unquotedTable = (getCurrentNode() != null) && (getCurrentNode().getNodeType() == NodeType.TABLE) && !isInDoubleQuotes()
            	? getCurrentNode()
            	: null;

        	do
        	{
            	if (!moveToNextNode())
            	{
                	isEof(true);
                	return false;
            	}
        	}
        	while (!setTokenFromNode(unquotedTable));

        	return true;
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// WORDSNET-13263 Moves to next node, but skip hidden inline nodes, if field start is not hidden.
    /// </summary>
    private boolean moveToNextNode()
    {
        while (true)
        {
            if (!mNodeEnumerator.moveToNextNode(true, isByNode()))
                return false;

            if (mAllowHiddenText)
                return true;

            if (getCurrentNode() instanceof FieldChar)
                return true;

            Inline inlineNode = ms.as(getCurrentNode(), Inline.class);
            if (inlineNode == null)
                return true;

            if (!isHidden(inlineNode))
                return true;
        }
    }

    /// <summary>
    /// Sets current token from the current node.
    /// Returns true if successful, false to move to next node.
    /// </summary>
    private boolean setTokenFromNode(Node unquotedTable) throws Exception
    {
        // WORDSNET-20330 Don't set token from current node if it's child of unquoted table, but extract nested fields.
        boolean isChildOfUnquotedTable = (unquotedTable != null) && getCurrentNode().isAncestorNode(unquotedTable);

        if (getCurrentNode() instanceof FieldChar)
            return setTokenFromFieldChar() && !isChildOfUnquotedTable;

        if (isChildOfUnquotedTable)
            return false;

        if (isLocked())
            return false;

        return setTokenFromNonFieldChar();
    }

    private boolean setTokenFromFieldChar() throws Exception
    {
        switch (getCurrentNode().getNodeType())
        {
            case NodeType.FIELD_START:
            {
                mLockCount++;
                mChildFieldNestingLevel++;
                mFieldStarts.push((FieldStart)getCurrentNode());

                // WORDSNET-6971 Don't exit on grandchild fields.
                if (isInTopLevelChildField())
                {
                    mTopLevelChildFieldStart = getCurrentNode();
                    setCurrentToken(FieldCodeToken.CHILD_FIELD_START);

                    return !isHiddenField(getCurrentNode());
                }

                break;
            }
            case NodeType.FIELD_SEPARATOR:
            {
                if (isInTopLevelChildField())
                    mTopLevelChildFieldSeparator = getCurrentNode();

                FieldSeparator fieldSeparator = (FieldSeparator)getCurrentNode();

                // If a field result is invisible we must not unlock it on a separator reach.
                if (FieldUtil.isFieldResultInvisible(fieldSeparator))
                    return false;

                FieldStart fieldStart = StackExtension.top(mFieldStarts);
                if (isHiddenFieldRevision(fieldStart, fieldSeparator, null))
                    return false;

                mLockCount--;

                // WORDSNET-6971 Don't exit on grandchild fields.
                if (isInTopLevelChildField())
                {
                    setCurrentToken(FieldCodeToken.CHILD_FIELD_SEPARATOR);

                    return !isHiddenField(getCurrentNode());
                }

                break;
            }
            case NodeType.FIELD_END:
            {
                if (isInTopLevelChildField())
                {
                    // Process encountered top level child field if needed.
                    onChildFieldEncountered();

                    mTopLevelChildFieldStart = null;
                    mTopLevelChildFieldSeparator = null;
                }

                mChildFieldNestingLevel--;

                FieldStart fieldStart = mFieldStarts.getCount() > 0 ? mFieldStarts.pop() : null;
                FieldEnd fieldEnd = (FieldEnd)getCurrentNode();

                boolean forceEmptyResult = !fieldEnd.hasSeparator() ||
                                        FieldUtil.isFieldResultInvisible(fieldEnd) ||
                                        isHiddenFieldRevision(fieldStart, null, fieldEnd);

                if (forceEmptyResult)
                {
                    // If a field result is forced to be empty we must unlock it on this stage to designate its presence.
                    mLockCount--;
                }

                // WORDSNET-6971 Don't exit on grandchild fields.
                if (isBeyondChildField())
                {
                    // If a field result is forced to be empty then corresponding field separator token was not returned.
                    // So return it here to specify that its position matches corresponding field end token's position.
                    // This prevents the field result from being included to a parent field result.
                    setCurrentToken(forceEmptyResult ? FieldCodeToken.CHILD_FIELD_SEPARATOR : FieldCodeToken.CHILD_FIELD_END);

                    return !isHiddenField(getCurrentNode());
                }

                break;
            }
            default:
                throw new IllegalArgumentException();
        }

        return false;
    }

    private boolean isHiddenFieldRevision(FieldStart fieldStart, FieldSeparator fieldSeparator, FieldEnd fieldEnd)
    {
        if (fieldStart == null)
            return false;

        // EV: Don't use the FieldChar.GetField method because of performance reasons.
        if (isHidden(fieldStart))
            return true;

        if (fieldEnd == null)
        {
            Debug.assertTrue(fieldSeparator != null);

            FieldBundle fieldBundle = new FieldBundle();
            fieldBundle.fillFieldBundleOneWay(fieldSeparator, true);
            fieldEnd = fieldBundle.getEnd();

            Debug.assertTrue(fieldEnd != null);
        }

        if (isHidden(fieldEnd))
            return true;

        return false;
    }

    private static boolean isHiddenField(Node node)
    {
        FieldChar fieldChar = (FieldChar)node;
        return FieldUtil.isDead(fieldChar.getFieldType());
    }

    private void onChildFieldEncountered() throws Exception
    {
        if (mExternalObjectInteractionStrategy != null)
            mExternalObjectInteractionStrategy.notifyChildFieldEncountered(this);
    }

    private boolean setTokenFromNonFieldChar()
    {
        switch (getCurrentNode().getNodeType())
        {
            case NodeType.RUN:
            {
                Run run = (Run)getCurrentNode();

                // Skip empty runs.
                if (run.getText().length() == 0)
                    return false;

                // Set the current text.
                setCurrentToken(FieldCodeToken.TEXT_CHAR);
                break;
            }
            case NodeType.PARAGRAPH:
                setCurrentToken(FieldCodeToken.PARAGRAPH);
                break;
            case NodeType.SECTION:
                setCurrentToken(FieldCodeToken.SECTION);
                break;
            case NodeType.BOOKMARK_START:
            case NodeType.BOOKMARK_END:
            case NodeType.COMMENT_RANGE_START:
            case NodeType.COMMENT_RANGE_END:
            case NodeType.MOVE_FROM_RANGE_START:
            case NodeType.MOVE_FROM_RANGE_END:
            case NodeType.MOVE_TO_RANGE_START:
            case NodeType.MOVE_TO_RANGE_END:
            case NodeType.SMART_TAG:
            case NodeType.STRUCTURED_DOCUMENT_TAG:
            case NodeType.FORM_FIELD:
                // These should be skipped.
                return false;
            default:
                setCurrentToken(FieldCodeToken.NON_TEXT_NODE);
                break;
        }

        return true;
    }

    private boolean isByNode()
    {
        if (getCurrentNode() == null)
            return false;

        switch (getCurrentNode().getNodeType())
        {
            case NodeType.FIELD_START:
            case NodeType.FIELD_SEPARATOR:
            case NodeType.FIELD_END:
            case NodeType.BOOKMARK_START:
            case NodeType.BOOKMARK_END:
            case NodeType.COMMENT_RANGE_START:
            case NodeType.COMMENT_RANGE_END:
            case NodeType.SMART_TAG:
            case NodeType.STRUCTURED_DOCUMENT_TAG:
                return true;
            default:
                return false;
        }
    }

    public DocumentPosition getCurrentPosition()
    {
        switch (getCurrentToken())
        {
            case FieldCodeToken.CHILD_FIELD_SEPARATOR:
                return DocumentPosition.createPositionAfter(getCurrentNode());
            case FieldCodeToken.CHILD_FIELD_START:
            case FieldCodeToken.CHILD_FIELD_END:
                return DocumentPosition.createPositionBefore(getCurrentNode());
            default:
                // Do nothing.
                break;
        }

        if (!getCurrentNode().isComposite() || (getCurrentNode().getNodeType() == NodeType.SHAPE))
        {
            return getCurrentDocumentPosition().deepClone();
        }
        else
        {
            return getCurrentDocumentPosition().isStart()
                ? getCurrentNode().getBeforeDocumentPosition()
                : getCurrentNode().getAfterDocumentPosition();
        }
    }

    /// <summary>
    /// Gets the current position.
    /// </summary>
    private DocumentPosition getCurrentDocumentPosition() { return mNodeEnumerator.getCurrentPosition(); }

    public Node getCurrentNode() { return mNodeEnumerator.getCurrentNode(); }

    private boolean isLocked() { return mLockCount > 0; }

    private boolean isInTopLevelChildField() { return mChildFieldNestingLevel == 1; }

    private boolean isBeyondChildField() { return mChildFieldNestingLevel == 0; }

    public /*FieldCodeToken*/int getCurrentToken() { return mCurrentToken; }; private void setCurrentToken(/*FieldCodeToken*/int value) { mCurrentToken = value; };

    private /*FieldCodeToken*/int mCurrentToken;

    public boolean isEndOfToken() { return getCurrentDocumentPosition().isEnd(); }

    public char getCurrentChar() { return ((Run)getCurrentNode()).getText().charAt(getCurrentDocumentPosition().getOffset()); }

    public boolean isEof() { return mIsEof; }; private void isEof(boolean value) { mIsEof = value; };

    private boolean mIsEof;

    public int getCurrentLocaleId()
    {
        Inline currentInlineNode = ms.as(getCurrentNode(), Inline.class);
        // Return LocaleId attribute of the current Inline node if it is defined.
        return currentInlineNode != null
            ? currentInlineNode.getFont().getLocaleId()
            : RunPr.PROCESS_OR_USER_DEFAULT_LANGUAGE_ID;
    }

    public int getCurrentLocaleIdFarEast()
    {
        Inline currentInlineNode = ms.as(getCurrentNode(), Inline.class);
        // Return LocaleIdFarEast attribute of the current Inline node if it is defined.
        return currentInlineNode != null
            ? currentInlineNode.getFont().getLocaleIdFarEast()
            : RunPr.PROCESS_OR_USER_DEFAULT_LANGUAGE_ID;
    }

    public int getCurrentLocaleIdBi()
    {
        Inline currentInlineNode = ms.as(getCurrentNode(), Inline.class);
        // Return LocaleIdBi attribute of the current Inline node if it is defined.
        return currentInlineNode != null
            ? currentInlineNode.getFont().getLocaleIdBi()
            : RunPr.PROCESS_OR_USER_DEFAULT_LANGUAGE_ID;
    }

    public boolean getCurrentBidi()
    {
        Inline currentInlineNode = ms.as(getCurrentNode(), Inline.class);
        // Return Bidi attribute of the current Inline node if it is defined.
        return (currentInlineNode != null) && currentInlineNode.getFont().getBidi();
    }

    public String getCurrentFontName()
    {
        Inline currentInlineNode = ms.as(getCurrentNode(), Inline.class);
        return currentInlineNode != null
            ? currentInlineNode.getFont().getName()
            : null;
    }

    private boolean isInDoubleQuotes() { return ((mExternalObjectInteractionStrategy != null) && mExternalObjectInteractionStrategy.isInDoubleQuotes()); }

    private boolean isHidden(Inline inline)
    {
        return mHiddenAttributeCache.getHiddenAttribute(inline);
    }

    /// <summary>
    /// When implemented, defines how the tokenizer interacts with an external object.
    /// </summary>
    private interface IExternalObjectInteractionStrategy
    {
        /// <summary>
        /// Notifies an external object about that a top level child field has been encountered while field code parsing.
        /// </summary>
        public void notifyChildFieldEncountered(NodeRangeFieldCodeTokenizer tokenizer) throws Exception;
        /// <summary>
        /// Gets a value indicating whether the current token is enclosed in double quotes.
        /// </summary>
        public boolean isInDoubleQuotes();
    }

    /// <summary>
    /// Defines how the tokenizer interacts with a field.
    /// </summary>
    private static class FieldInteractionStrategy implements IExternalObjectInteractionStrategy
    {
        FieldInteractionStrategy(Field field)
        {
            mField = field;
        }

        public void /*IExternalObjectInteractionStrategy.*/notifyChildFieldEncountered(NodeRangeFieldCodeTokenizer tokenizer) throws Exception
        {
            if (mField.isUpdating())
            {
                mField.getUpdateContext().notifyChildFieldEncountered(
                    (FieldStart)tokenizer.mTopLevelChildFieldStart,
                    (FieldSeparator)tokenizer.mTopLevelChildFieldSeparator,
                    (FieldEnd)tokenizer.getCurrentNode());
            }
        }

        public boolean /*IExternalObjectInteractionStrategy.*/isInDoubleQuotes() { return mField.isUpdating() && mField.getUpdateContext().isInDoubleQuotes(); }

        private /*final*/ Field mField;
    }

    /// <summary>
    /// Defines how the tokenizer interacts with a field argument.
    /// </summary>
    private static class FieldArgumentInteractionStrategy implements IExternalObjectInteractionStrategy
    {
        FieldArgumentInteractionStrategy(FieldArgument argument)
        {
            mArgument = argument;
        }

        public void /*IExternalObjectInteractionStrategy.*/notifyChildFieldEncountered(NodeRangeFieldCodeTokenizer tokenizer)
        {
            // Do nothing.
        }

        public boolean /*IExternalObjectInteractionStrategy.*/isInDoubleQuotes() { return mArgument.isInDoubleQuotes(); }

        private /*final*/ FieldArgument mArgument;
    }

    private boolean mAllowHiddenText;
    private /*final*/ IExternalObjectInteractionStrategy mExternalObjectInteractionStrategy;
    private NodeEnumerator mNodeEnumerator;
    private int mLockCount;
    private int mChildFieldNestingLevel;
    private Node mTopLevelChildFieldStart;
    private Node mTopLevelChildFieldSeparator;
    private HiddenAttributeCache mHiddenAttributeCache;
    private /*final*/ msStack<FieldStart> mFieldStarts = new msStack<FieldStart>();
}

