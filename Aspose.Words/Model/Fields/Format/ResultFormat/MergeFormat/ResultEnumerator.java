// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/03/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.EnumeratorWrapperPalGeneric;
import com.aspose.Debug;
import com.aspose.ms.System.msChar;


/// <summary>
/// Enumerates a field result represented by a collection of nodes.
/// </summary>
abstract class ResultEnumerator
{
    /// <summary>
    /// Ctor.
    /// </summary>
    protected ResultEnumerator(Iterable<Node> resultNodes, Paragraph startParagraph)
    {
        mNodeEnumerator = new EnumeratorWrapperPalGeneric<Node>(resultNodes.iterator());
        setCurrentParagraph(startParagraph);
    }

    /// <summary>
    /// Moves to the next token within field result.
    /// </summary>
    /// <returns>A boolean value indicating whether the next token was successfully enumerated.</returns>
    /*virtual*/ boolean moveNext() throws Exception
    {
        if (mIsEof)
            return false;

        if (isInitialized())
        {
            if (isNextNodeParagraph())
            {
                setLastParagraph(getCurrentParagraph());
                setCurrentParagraph((Paragraph)getNextNode());
            }

            if (isNextNodeTable())
            {
                setLastParagraph(getCurrentParagraph());
                setCurrentParagraph(null);
            }

            mCurrentToken = mNextToken;
            applyNext();
        }

        while (moveToNextValidPosition())
        {
            onNextNode();

            switch (getNextNode().getNodeType())
            {
                case NodeType.RUN:
                {
                    char c = getNextRun().getText().charAt(getNextTextPosition());
                    mNextToken = getToken(c);

                    if (mNextToken != ResultToken.WORD_CHARACTER)
                        mCurrentWordHasDigit = false;
                    else if (Character.isDigit(c))
                        mCurrentWordHasDigit = true;

                    if (isEndOfCurrentToken())
                    {
                        // End of current token.
                        // Remember the current character as a part of next token as text position will be moved next time.
                        onNextChar(c);
                        return true;
                    }

                    // Here we must look at the next token in order to decide if the current token is finished.
                    mCurrentToken = mNextToken;
                    // Remember the current character as a part of the current token.
                    onChar(c);

                    break;
                }
                case NodeType.PARAGRAPH:
                {
                    mNextToken = ResultToken.PARGARAPH_BREAK;
                    mCurrentWordHasDigit = false;
                    // There is no sense to stop at undefined token, so let's move to next one.
                    // Note that further single call of MoveNext() always returns true due to its implementation,
                    // as mIsEof is false and mNextToken (which will be applied to current token) is defined at this point.
                    return isCurrentTokenDefined() || moveNext();
                }
                case NodeType.TABLE:
                {
                    mNextToken = ResultToken.TABLE;
                    setCurrentTable((Table)getNextNode());

                    return isCurrentTokenDefined() || moveNext();
                }
                case NodeType.STRUCTURED_DOCUMENT_TAG:
                {
                    // Do nothing
                    break;
                }
                default:
                {
                    // MoveToNextValidPosition() should only give us runs and paragraphs
                    Debug.fail("Invalid mNextNode.NodeType value.");
                    break;
                }
            }
        }

        // End of range reached when parsing the current token. The token may still be not empty.
        return isCurrentTokenDefined();
    }

    /// <summary>
    /// Moves to the next valid node or to the next valid position within the run.
    /// </summary>
    /// <returns></returns>
    private boolean moveToNextValidPosition() throws Exception
    {
        do
        {
            if (isInitialized() && isNextNodeRun() && (getNextTextPosition() < getNextRun().getText().length()))
            {
                setNextTextPosition(getNextTextPosition() + 1)/*Property++*/;
            }
            else if (mNodeEnumerator.moveNext())
            {
                // Successfully moved to the next node.
                setNextNode(mNodeEnumerator.getCurrent());
                setNextTextPosition(0);

                processFieldChar(getNextNode());
            }
            else
            {
                mIsEof = true;
                return false;
            }
        }
        while (!isNextPositionValid());

        // Found node and position are valid.
        return true;
    }

    private /*ResultToken*/int getToken(char c)
    {
        switch (c)
        {
            case '-':
            case '\'':
            case ControlChar.NON_BREAKING_SPACE_CHAR:
            case ControlChar.NON_BREAKING_HYPHEN_CHAR:
                return ResultToken.WORD_CHARACTER;
            case '.':
            case ',':
                return mCurrentWordHasDigit
                    ? ResultToken.WORD_CHARACTER
                    : ResultToken.SEPARATOR;
            default:
                if (Character.isLetterOrDigit(c))
                    return ResultToken.WORD_CHARACTER;
                else if (msChar.isWhiteSpace(c))
                    return ResultToken.SPACE;
                else
                    return ResultToken.SEPARATOR;
        }
    }

    private boolean isEndOfCurrentToken()
    {
        switch (mCurrentToken)
        {
            case ResultToken.WORD_CHARACTER:
                return (mNextToken != ResultToken.WORD_CHARACTER);
            case ResultToken.SPACE:
                return (mNextToken != ResultToken.SPACE);
            case ResultToken.SEPARATOR:
            case ResultToken.PARGARAPH_BREAK:
                return true;
            case ResultToken.UNDEFINED:
                return false;
            case ResultToken.TABLE:
                return true;
            default:
                Debug.fail("Invalid mCurrentToken value.");
                return false;
        }
    }

    /// <summary>
    /// Locks processing of nodes if we are in a field code. Fields may appear in the result, for example, if they were
    /// copied within IF's TrueText or FalseText.
    /// </summary>
    /// <param name="node"></param>
    private void processFieldChar(Node node) throws Exception
    {
        switch (node.getNodeType())
        {
            case NodeType.FIELD_START:
                mLockCount++;
                break;
            case NodeType.FIELD_SEPARATOR:
                mLockCount--;
                onFieldResultBoundary();
                break;
            case NodeType.FIELD_END:
                if (!((FieldEnd)node).hasSeparator())
                    mLockCount--;

                onFieldResultBoundary();
                break;
            default:
                // Do nothing.
                break;
        }
    }

    private boolean isNextPositionValid()
    {
        // We are inside a field code.
        if (isLocked())
            return false;

        // We are inside a table token
        if (getCurrentTable() != null && getNextNode().isAncestorNode(getCurrentTable()))
            return false;

        // We are only interested in non-empty runs and paragraphs.
        switch (getNextNode().getNodeType())
        {
            case NodeType.RUN:
                return (getNextTextPosition() < getNextNode().getText().length());
            case NodeType.PARAGRAPH:
            case NodeType.STRUCTURED_DOCUMENT_TAG:
                return true;
            case NodeType.TABLE:
                return true;
            default:
                return false;
        }
    }

    protected /*virtual*/ void onNextNode()
    {
        // Do nothing.
    }

    protected /*virtual*/ void onFieldResultBoundary() throws Exception
    {
        // Do nothing.
    }

    protected /*virtual*/ void onChar(char c) throws Exception
    {
        // Do nothing.
    }

    protected /*virtual*/ void onNextChar(char c)
    {
        // Do nothing.
    }

    /// <summary>
    /// Performs additional actions relative to switch from current to next token.
    /// </summary>
    protected /*virtual*/ void applyNext() throws Exception
    {
        // Do nothing.
    }

    private boolean isLocked() { return (mLockCount > 0); }

    /// <summary>
    /// Returns value indicating whether current token is paragraph break.
    /// </summary>
    boolean isCurrentTokenParagraphBreak()
    {
        switch (mCurrentToken)
        {
            case ResultToken.PARGARAPH_BREAK:
                return true;
            case ResultToken.WORD_CHARACTER:
            case ResultToken.SEPARATOR:
            case ResultToken.SPACE:
            case ResultToken.TABLE:
                return false;
            default:
                Debug.fail("Ivalid mCurrentToken value.");
                return false;
        }
    }

    boolean isCurrentTokenTable()
    {
        switch (mCurrentToken)
        {
            case ResultToken.TABLE:
                return true;

            case ResultToken.PARGARAPH_BREAK:
            case ResultToken.WORD_CHARACTER:
            case ResultToken.SEPARATOR:
            case ResultToken.SPACE:
                return false;

            default:
                Debug.fail("Ivalid mCurrentToken value.");
                return false;
        }
    }

    /// <summary>
    /// Returns value indicating whether current token has valid value and
    /// is not equal to <see cref="ResultToken.Undefined"/>.
    /// </summary>
    private boolean isCurrentTokenDefined()
    {
        return mCurrentToken != ResultToken.UNDEFINED;
    }

    protected int getNextTextPosition() { return mNextTextPosition; }; private void setNextTextPosition(int value) { mNextTextPosition = value; };

    private int mNextTextPosition;

    protected Node getNextNode() { return mNextNode; }; private void setNextNode(Node value) { mNextNode = value; };

    private Node mNextNode;

    protected Run getNextRun() { return (Run)getNextNode(); }

    boolean isInitialized() { return (getNextNode() != null); }

    Paragraph getCurrentParagraph() { return mCurrentParagraph; }; private void setCurrentParagraph(Paragraph value) { mCurrentParagraph = value; };

    private Paragraph mCurrentParagraph;

    Paragraph getLastParagraph() { return mLastParagraph; }; private void setLastParagraph(Paragraph value) { mLastParagraph = value; };

    private Paragraph mLastParagraph;

    /// <summary>
    /// Returns value indicating whether the next node is a run.
    /// </summary>
    protected boolean isNextNodeRun() { return (getNextNode().getNodeType() == NodeType.RUN); }

    /// <summary>
    /// Returns value indicating whether the next node is a paragraph.
    /// </summary>
    protected boolean isNextNodeParagraph() { return (getNextNode().getNodeType() == NodeType.PARAGRAPH); }

    protected boolean isNextNodeTable() { return (getNextNode().getNodeType() == NodeType.TABLE); }

    Table getCurrentTable() { return mCurrentTable; }; private void setCurrentTable(Table value) { mCurrentTable = value; };

    private Table mCurrentTable;

    /// <summary>
    /// Returns true when the enumerator is in suspended mode, i.e. <see cref="MoveNext"/> always returns true.
    /// </summary>
    abstract boolean isSuspended();

    private /*final*/ EnumeratorWrapperPalGeneric<Node> mNodeEnumerator;
    private /*ResultToken*/int mCurrentToken = ResultToken.UNDEFINED;
    private /*ResultToken*/int mNextToken;
    private boolean mIsEof;
    private int mLockCount;
    private boolean mCurrentWordHasDigit;
}

