// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;


/// <summary>
/// Represents a markdown Block.
/// </summary>
abstract class Block
{
    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    abstract boolean tryParse(String txtLine, int start);

    /// <summary>
    /// Tries to append a specified block to this block.
    /// </summary>
    abstract boolean tryAppend(Block block);

    /// <summary>
    /// Closes the block.
    /// </summary>
    void close()
    {
        mIsOpened = false;
    }

    /// <summary>
    /// Opens the block.
    /// </summary>
    void open()
    {
        mIsOpened = true;
    }

    /// <summary>
    /// Removes the block from a parent.
    /// </summary>
    void remove()
    {
        if (getParent() != null)
            getParent().remove(this);
    }

    /// <summary>
    /// Returns first parent block of a specified type.
    /// </summary>
    ContainerBlock getParent(/*BlockType*/byte type)
    {
        ContainerBlock curParent = mParent;
        while (curParent != null)
        {
            if (curParent.getType() == type)
                return curParent;

            curParent = curParent.getParent();
        }

        return null;
    }

    /// <summary>
    /// Returns first parent block of a specified type with a specified opening state.
    /// </summary>
    ContainerBlock getParent(/*BlockType*/byte type, boolean isOpened)
    {
        ContainerBlock curParent = mParent;
        while (curParent != null)
        {
            if ((curParent.getType() == type) && (curParent.isOpened() == isOpened))
                return curParent;

            curParent = curParent.getParent();
        }

        return null;
    }

    /// <summary>
    /// Returns first parent block of a specified type.
    /// </summary>
    ContainerBlock getParent(/*params*/ /*BlockType*/byte... types)
    {
        ContainerBlock curParent = mParent;
        while (curParent != null)
        {
            if (curParent.hasType(types))
                return curParent;

            curParent = curParent.getParent();
        }

        return null;
    }

    /// <summary>
    /// Returns topmost parent block of a specified type with a specified opening state.
    /// </summary>
    ContainerBlock getTopmostParent(/*BlockType*/byte type, boolean isOpened)
    {
        ContainerBlock lastSuccessfull = null;

        ContainerBlock curParent = mParent;
        while (curParent != null)
        {
            if ((curParent.getType() == type) && (curParent.isOpened() == isOpened))
                lastSuccessfull = curParent;

            curParent = curParent.getParent();
        }

        return lastSuccessfull;
    }

    /// <summary>
    /// Opens all parent blocks of a specified type.
    /// </summary>
    void openParents(/*BlockType*/byte type)
    {
        ContainerBlock curParent = mParent;
        while (curParent != null)
        {
            if (curParent.getType() == type)
                curParent.open();

            curParent = curParent.getParent();
        }
    }

    /// <summary>
    /// Closes all parent blocks of a specified type.
    /// </summary>
    void closeParents(/*BlockType*/byte type)
    {
        ContainerBlock curParent = mParent;
        while ((curParent != null) && (curParent.getType() == type))
        {
            curParent.close();
            curParent = curParent.getParent();
        }
    }

    /// <summary>
    /// Inverts opening state of all parent blocks of a specified type.
    /// </summary>
    void invertParents(/*BlockType*/byte type)
    {
        ContainerBlock curParent = mParent;
        while (curParent != null)
        {
            if (curParent.getType() == type)
                curParent.invert();

            curParent = curParent.getParent();
        }
    }

    /// <summary>
    /// Returns true, if this block has one of the specified types.
    /// </summary>
    boolean hasType(/*params*/ /*BlockType*/byte... types)
    {
        for (/*BlockType*/byte type : types)
            if (getType() == type)
                return true;

        return false;
    }

    /// <summary>
    /// Returns previous sibling block of a specified type.
    /// </summary>
    Block getPreviousSibling(/*params*/ /*BlockType*/byte... types)
    {
        Debug.assertTrue(types != null);

        int prevIndex = getIndex() - 1;
        while (prevIndex >= 0)
        {
            Block previousSibling = getParent().get(prevIndex);
            for (/*BlockType*/byte type : types)
            {
                if (previousSibling.getType() == type)
                    return previousSibling;
            }

            prevIndex--;
        }

        return null;
    }

    /// <summary>
    /// Returns previous block of a specified type and opening state deep traversed over all parents.
    /// </summary>
    Block getPreviousDeepTraversed(boolean isOpened, /*params*/ /*BlockType*/byte... types)
    {
        ContainerBlock curParent = getParent();
        while (curParent != null)
        {
            int end = (curParent == getParent()) ? (getIndex() - 1) : (curParent.getCount() - 1);
            for (int i = end; i >= 0; i--)
            {
                Block prevBlock = curParent.get(i);
                if ((prevBlock.hasType(types)) && (prevBlock.isOpened() == isOpened))
                    return prevBlock;
            }

            curParent = curParent.getParent();
        }

        return null;
    }

    /// <summary>
    /// Writes the block into a model using specified context.
    /// </summary>
    /*virtual*/ void write(MarkdownReaderContext context) throws Exception
    {
        if (getText().length() == 0)
            return;

        String text = resolveWhitespacesAndLineBreaks();
        context.writeText(text);
    }

    /// <summary>
    /// Parses text line starting from a specified position onto the following parts:
    /// | mOpeningIndentation | mOpening | mContentLine | mClosing | mClosingIndentation |
    ///  </summary>
    protected boolean parse(String txtLine, int start)
    {
        if (txtLine == null)
            return false;

        mOpeningIndentation = getLeftIndentation(txtLine, start);

        int openingStart = start + mOpeningIndentation.length();
        mOpening = getOpening(txtLine, openingStart);

        int contentStart = openingStart + mOpening.length();

        mClosingIndentation = getRightIndentation(txtLine, contentStart);

        int closingEnd = (txtLine.length() - 1) - mClosingIndentation.length();
        // Every Closing MUST have a corresponding Opening.
        mClosing = (StringUtil.hasChars(mOpening) || getAllowClosingWithoutOpening())
            ? getClosing(txtLine, contentStart, closingEnd)
            : "";

        int contentEnd = closingEnd - mClosing.length();

        // If there is no Closing, then there is no closing indentation
        // and thus whole ending of the text actually belongs to a content of the block.
        if (!StringUtil.hasChars(mClosing))
        {
            mClosingIndentation = "";
            contentEnd = txtLine.length() - 1;
        }

        mContentLine = getContent(txtLine, contentStart, contentEnd);

        return true;
    }

    /// <summary>
    /// Gets an indentation part from a left side of a text line starting at a specified position.
    /// </summary>
    protected /*virtual*/ String getLeftIndentation(String txtLine, int start)
    {
        Debug.assertTrue(txtLine != null);

        int curPosition = start;
        while ((curPosition < txtLine.length()) && isIndentationChar(txtLine.charAt(curPosition)))
            curPosition++;

        int length = curPosition - start;
        return (length > 0) ? txtLine.substring((start), (start) + (length)) : "";
    }

    /// <summary>
    /// Gets an indentation part from a right side of a text line starting at the end
    /// of text up to a specified start position.
    /// </summary>
    protected /*virtual*/ String getRightIndentation(String txtLine, int start)
    {
        Debug.assertTrue(txtLine != null);

        if (start >= txtLine.length())
            return "";

        int curPosition = txtLine.length() - 1;
        while ((curPosition >= start) && isIndentationChar(txtLine.charAt(curPosition)))
            curPosition--;

        int length = (txtLine.length() - 1) - curPosition;
        return (length > 0) ? txtLine.substring((curPosition + 1), (curPosition + 1) + (length)) : "";
    }

    /// <summary>
    /// Gets an opening part from a text line starting at a specified position.
    /// </summary>
    protected /*virtual*/ String getOpening(String txtLine, int start)
    {
        Debug.assertTrue(txtLine != null);

        int curPosition = start;
        int length = 0;
        while ((curPosition < txtLine.length()) && isOpeningChar(txtLine.charAt(curPosition)))
        {
            length += MarkdownUtil.getLength(txtLine.charAt(curPosition));
            curPosition++;

            if (length == getOpeningSearchLimit())
                break;
        }

        return (length > 0) ? txtLine.substring((start), (start) + (curPosition - start)) : "";
    }

    /// <summary>
    /// Gets a closing part from a text line moving from a specified end toward to start position.
    /// </summary>
    protected /*virtual*/ String getClosing(String txtLine, int start, int end)
    {
        Debug.assertTrue(txtLine != null);

        int curPosition = end;

        // It is assumed that a closing sequence of characters is the same as an opening one.
        while ((curPosition >= start) && isOpeningChar(txtLine.charAt(curPosition)))
            curPosition--;

        // There MUST be a whitespace before a closing sequence of characters.
        // Otherwise, we cannot distinguish it from a content tail.
        if ((curPosition < 0) || !StringUtil.isWhiteSpace(txtLine.charAt(curPosition)))
            return "";

        int length = end - curPosition;
        return (length > 0) ? txtLine.substring((curPosition + 1), (curPosition + 1) + (length)) : "";
    }

    /// <summary>
    /// Gets a content part from a text line starting at a specified start position up to the end position.
    /// </summary>
    protected /*virtual*/ String getContent(String txtLine, int start, int end)
    {
        int length = end - start + 1;
        return (length > 0) ? txtLine.substring((start), (start) + (length)) : "";
    }

    /// <summary>
    /// Returns true, if a specified character is allowed in a sequence of indentation characters.
    /// </summary>
    protected /*virtual*/ boolean isIndentationChar(char c)
    {
        return ((c == ' ') || (c == '\t'));
    }

    /// <summary>
    /// Returns true, if a specified character is allowed in a sequence of opening characters.
    /// </summary>
    protected /*virtual*/ boolean isOpeningChar(char c)
    {
        return false;
    }

    /// <summary>
    /// Removes all parsed parts.
    /// </summary>
    /// <remarks>Use this for memory optimization with inline container blocks that are completely parsed.</remarks>
    protected void removeAllParts()
    {
        mOpeningIndentation = null;
        mOpening = null;
        mContentLine = null;
        mClosing = null;
        mClosingIndentation = null;
    }

    /// <summary>
    /// Inverts a block state.
    /// </summary>
    /// <remarks>IN. Made protected due to Java compiler problem.</remarks>
    protected void invert()
    {
        mIsOpened = !mIsOpened;
    }


    /// <summary>
    /// Resolves whitespaces and line breaks by inserting, removing or converting them appropriately.
    /// </summary>
    private String resolveWhitespacesAndLineBreaks()
    {
        // WORDSNET-28255 Added option to allow to specify a soft line break character.
        char softLineBreakCharacter = (getDocument().getLoadOptions() == null)
            ? MarkdownLoadOptions.DEFAULT_SOFT_LINE_BREAK_CHARACTER
            : getDocument().getLoadOptions().getSoftLineBreakCharacter();

        return MarkdownUtil.resolveWhitespacesAndLineBreaks(this, softLineBreakCharacter);
    }

    /// <summary>
    /// Gets block type.
    /// </summary>
    abstract /*BlockType*/byte getType();

    /// <summary>
    /// Gets block level.
    /// </summary>
    abstract /*MarkdownBlockLevel*/int getBlockLevel();

    /// <summary>
    /// Gets or sets a parent block.
    /// </summary>
    ContainerBlock getParent() { return mParent; }
    void setParent(ContainerBlock value) { mParent = value; }

    /// <summary>
    /// Gets a boolean value indicating whether this block is a first child.
    /// </summary>
    boolean isFirstChild()
    {
      return ((getParent() != null) && (getParent().get(0) == this));
    }

    /// <summary>
    /// Gets a boolean value indicating whether this block is a last child.
    /// </summary>
    boolean isLastChild() { return ((getParent() != null) && (getParent().get(getParent().getCount()-1) == this)); }

    /// <summary>
    /// Gets block immediately preceding this block.
    /// </summary>
    Block getPreviousSibling()
    {
        int prevIndex = getIndex() - 1;
        return (prevIndex < 0) ? null : getParent().get(prevIndex);
    }

    /// <summary>
    /// Gets block immediately following this block.
    /// </summary>
    Block getNextSibling()
    {
        int nextIndex = getIndex() + 1;
        return (nextIndex < getParent().getCount()) ? getParent().get(nextIndex) : null;
    }

    /// <summary>
    /// Gets a boolean value indicating the block is inside a Quote block.
    /// </summary>
    boolean isInQuote() { return (getParent(BlockType.QUOTE) != null); }

    /// <summary>
    /// Gets a boolean value indicating the block is inside a ListItem block.
    /// </summary>
    boolean isInList() { return (getParent(BlockType.BULLET_LIST_ITEM, BlockType.ORDERED_LIST_ITEM) != null); }

    /// <summary>
    /// Gets or sets an action that should be performed with the block.
    /// </summary>
    /*BlockAction*/int getAction() { return mAction; }
    void setAction(/*BlockAction*/int value) { mAction = value; }

    /// <summary>
    /// Gets a boolean value indicating an opening state of the block.
    /// </summary>
    boolean isOpened() { return mIsOpened; }

    /// <summary>
    /// Gets or sets an opening indentation part.
    /// </summary>
    String getOpeningIndentation() { return mOpeningIndentation; }
    void setOpeningIndentation(String value) { mOpeningIndentation = value; }

    /// <summary>
    /// Gets an opening part.
    /// </summary>
    String getOpening() { return mOpening; }

    /// <summary>
    /// Gets a closing part.
    /// </summary>
    String getClosing() { return mClosing; }

    /// <summary>
    /// Gets a content line part.
    /// </summary>
    String getContentLine() { return mContentLine; }

    /// <summary>
    /// Gets length of an opening part considering tabs.
    /// </summary>
    int getOpeningLength() { return MarkdownUtil.getLength(mOpening); }

    /// <summary>
    /// Gets length of an opening indentation part considering tabs.
    /// </summary>
    int getOpeningIndentationLength() { return MarkdownUtil.getLength(mOpeningIndentation); }

    /// <summary>
    /// Gets length of a content line part considering tabs.
    /// </summary>
    int getContentLineIndentationLength()
    {
        int nonWhitespaceIndex = StringUtil.indexOfNonWhitespace(mContentLine);
        if (nonWhitespaceIndex == -1)
            return mContentLine.length();

        return MarkdownUtil.getLength(mContentLine.substring((0), (0) + (nonWhitespaceIndex)));
    }

    /// <summary>
    /// Gets total length of all block parts.
    /// </summary>
    /*virtual*/ int getLength()
    {
        return (mOpeningIndentation.length() +
                mOpening.length() +
                mContentLine.length() +
                mClosing.length() +
                mClosingIndentation.length());
    }

    /// <summary>
    /// Gets block text.
    /// </summary>
    /*virtual*/ String getText() { return getContentLine(); }

    /// <summary>
    /// Gets block indentation.
    /// </summary>
    /*virtual*/ int getIndentation() { return getOpeningIndentationLength(); }

    /// <summary>
    /// Gets integer value that limits a number of opening characters to search.
    /// </summary>
    protected /*virtual*/ int getOpeningSearchLimit() { return Integer.MAX_VALUE; }

    /// <summary>
    /// Gets a boolean value indicating either closing sequence is allowed without an opening one.
    /// </summary>
    protected /*virtual*/ boolean getAllowClosingWithoutOpening() { return false; }

    /// <summary>
    /// Gets an index of the block inside a parent block.
    /// </summary>
    protected int getIndex()
    {
        if (getParent() != null)
        {
            for (int i = 0; i < getParent().getCount(); i++)
                if (getParent().get(i) == this)
                    return i;
        }

        return -1;
    }

    /// <summary>
    /// Gets parent document.
    /// </summary>
    protected MarkdownDocument getDocument()
    {
        if (mDocument == null)
            mDocument = (MarkdownDocument)getParent(BlockType.DOCUMENT);

        return mDocument;
    }

    /// <summary>
    /// A maximum allowed length of indentation part of the block.
    /// </summary>
    static final int MAX_INDENTATION_LENGTH = 3;

    /// <summary>
    /// An action to execute over the block.
    /// </summary>
    private /*BlockAction*/int mAction;

    private ContainerBlock mParent;

    private MarkdownDocument mDocument;

    private boolean mIsOpened = true;

    private String mOpeningIndentation = "";
    private String mOpening = "";
    private String mContentLine = "";
    private String mClosing = "";
    private String mClosingIndentation = "";
}

