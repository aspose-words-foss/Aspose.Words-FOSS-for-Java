// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import com.aspose.ms.System.Text.msStringBuilder;
import java.util.ArrayList;


/// <summary>
/// The base class for markdown container blocks.
/// </summary>
abstract class ContainerBlock extends Block implements Iterable<Block>
{
    /// <summary>
    /// Writes the block into a model using specified context.
    /// </summary>
    /*override*/ void write(MarkdownReaderContext context) throws Exception
    {
        context.open(this);

        for (Block childBlock : mChildBlocks)
            childBlock.write(context);

        context.close(this);
    }

    /// <summary>
    /// Tries to append a specified block to this block.
    /// </summary>
    /// <remarks>
    /// We try to append a specified block to a last opened child block of the container.
    /// If it cannot be appended, then we close that last child block and iterate to
    /// a next last opened child.
    /// </remarks>
    /*override*/ boolean tryAppend(Block block)
    {
        Block lastOpenedChildBlock = getDeepestOpenedChildBlock();
        while (lastOpenedChildBlock != null)
        {
            boolean isAppended = lastOpenedChildBlock.tryAppend(block);

            if (lastOpenedChildBlock.getAction() == BlockAction.REMOVE)
                lastOpenedChildBlock.remove();

            if (block.getAction() == BlockAction.EXCLUDE)
                return isAppended;

            if (isAppended)
                return true;

            lastOpenedChildBlock.close();

            // EOLs in our markdown tree are used as helper blocks that allow to append other blocks.
            // So, no need to keep closed EOLs in the markdown tree.
            if (lastOpenedChildBlock.getType() == BlockType.END_OF_LINE)
                lastOpenedChildBlock.remove();

            lastOpenedChildBlock = getDeepestOpenedChildBlock();
        }

        return false;
    }

    /// <summary>
    /// Adds child block.
    /// </summary>
    Block add(Block block)
    {
        msArrayList.add(mChildBlocks, block);
        block.setParent(this);

        return block;
    }

    /// <summary>
    /// Removes child block.
    /// </summary>
    void remove(Block block)
    {
        mChildBlocks.remove(block);
        block.setParent(null);
    }

    /// <summary>
    /// Closes all child blocks.
    /// </summary>
    void closeChildren()
    {
        Block lastOpenedChildBlock = getDeepestOpenedChildBlock();
        while (lastOpenedChildBlock != null)
        {
            lastOpenedChildBlock.close();
            lastOpenedChildBlock = getDeepestOpenedChildBlock();
        }
    }

    /// <summary>
    /// Closes all child blocks non-including the specified types.
    /// </summary>
    void closeChildrenNonInclusive(/*params*/ /*BlockType*/byte... typesToExclude)
    {
        for (Block childBlock : mChildBlocks)
        {
            ContainerBlock containerBlock = ms.as(childBlock, ContainerBlock.class);
            if (containerBlock != null)
                containerBlock.closeChildrenNonInclusive(typesToExclude);

            if (!childBlock.hasType(typesToExclude))
                childBlock.close();
        }
    }

    /// <summary>
    /// Returns deepest opened child block of a specified type.
    /// </summary>
    /// <remarks>
    /// If a specified type is <see cref="BlockType.Document"/>, then searches an opened block of any type.
    /// </remarks>
    Block getDeepestOpenedChildBlock()
	{
		return getDeepestOpenedChildBlock(BlockType.DOCUMENT);
	}

    /// <summary>
    /// Returns deepest opened child block of a specified type.
    /// </summary>
    /// <remarks>
    /// If a specified type is <see cref="BlockType.Document"/>, then searches an opened block of any type.
    /// </remarks>
    Block getDeepestOpenedChildBlock(/*BlockType*/byte type)
    {
        for (int i = (mChildBlocks.size() - 1); i >= 0; i--)
        {
            Block childBlock = mChildBlocks.get(i);

            ContainerBlock containerBlock = ms.as(childBlock, ContainerBlock.class);
            if (containerBlock != null)
            {
                // The child block is a container, so first try to search inside it.
                Block lastOpenedInContainer = containerBlock.getDeepestOpenedChildBlock(type);
                if (lastOpenedInContainer != null)
                    return lastOpenedInContainer;
            }

            if (((childBlock.getType() == type) || (type == BlockType.DOCUMENT)) && childBlock.isOpened())
                return childBlock;
        }

        return null;
    }

    /// <summary>
    /// Returns deepest opened child block of a specified type.
    /// </summary>
    Block getDeepestOpenedChildBlock(/*params*/ /*BlockType*/byte... types)
    {
        for (int i = (mChildBlocks.size() - 1); i >= 0; i--)
        {
            Block childBlock = mChildBlocks.get(i);

            ContainerBlock containerBlock = ms.as(childBlock, ContainerBlock.class);
            if (containerBlock != null)
            {
                // The child block is a container, so first try to search inside it.
                Block lastOpenedInContainer = containerBlock.getDeepestOpenedChildBlock(types);
                if (lastOpenedInContainer != null)
                    return lastOpenedInContainer;
            }

            if (childBlock.hasType(types) && childBlock.isOpened())
                return childBlock;
        }

        return null;
    }

    //>>>>>>>> #region  IEnumerable implementation
    public Iterator<Block> iterator()
    {
        return mChildBlocks.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()
    //<<<<<<<< #endregion 


    /// <summary>
    /// Gets child block at a specified position.
    /// </summary>
    Block get(int index) { return mChildBlocks.get(index); }

    /// <summary>
    /// Gets last child block.
    /// </summary>
    Block getLast() { return (mChildBlocks.size() > 0) ? mChildBlocks.get(mChildBlocks.size() - 1) : null; }

    /// <summary>
    /// Gets a boolean value indicating whether the container is empty.
    /// </summary>
    boolean isEmpty() { return (mChildBlocks.size() == 0); }

    /// <summary>
    /// Gets block text.
    /// </summary>
    /*override*/ String getText()
    {
        StringBuilder sb = new StringBuilder();

        for (Block childBlock : mChildBlocks)
            msStringBuilder.append(sb, childBlock.getText());

        return sb.toString();
    }

    /// <summary>
    /// Gets count of child blocks.
    /// </summary>
    int getCount() { return mChildBlocks.size(); }

    /// <summary>
    /// A collection of child blocks.
    /// </summary>
    private /*final*/ ArrayList<Block> mChildBlocks = new ArrayList<Block>();
}

