// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/01/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ISetGeneric;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.StackExtension;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.msObject;
import com.aspose.HashSetGeneric;
import com.aspose.StringToObjDictionary;
import java.util.Map;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msStack;
import java.util.ArrayList;
import java.util.Collection;
import com.aspose.CaseInsensitiveHashSet;
import com.aspose.words.ref.Ref;


/// <summary>
/// Used to validate bookmarks (remove nodes of bookmarks with duplicate names and
/// remove bookmarks that have no matching start or end node).
/// </summary>
class BookmarkValidator
{
    /// <summary>
    /// Ctr.
    /// </summary>
    /// <param name="warningCallback">Object which capture warnings that can occur during document loading or saving.</param>
    /// <param name="truncateNames"></param>
    BookmarkValidator(IWarningCallback warningCallback, boolean truncateNames)
    {
        mTruncateNames = truncateNames;
        mWarningCallback = warningCallback;
    }

    void visitBookmarkStart(BookmarkStart bookmarkStart, /*StoryType*/int storyType)
    {
        if (postponeDmlFallbackBookmark(bookmarkStart))
            return;

        onBookmarkStart(bookmarkStart, storyType);
    }

    void visitBookmarkEnd(BookmarkEnd bookmarkEnd, /*StoryType*/int storyType)
    {
        if (postponeDmlFallbackBookmark(bookmarkEnd))
            return;

        onBookmarkEnd(bookmarkEnd, storyType);
    }

    void visitDrawingMLStart(ShapeBase drawingML)
    {
        if (drawingML.getFallbackShape() == null)
            return;

        mDmlStack.push(drawingML);
    }

    void visitDrawingMLEnd(ShapeBase drawingML)
    {
        if (drawingML != getCurrentDml())
            return;

        mDmlStack.pop();
    }

    /// <summary>
    /// Call this at the end. Removes any bookmark orphans that have starts only.
    /// </summary>
    void visitDocumentEnd()
    {
        onDocumentEnd();

        for (DmlBookmarks dmlBookmarks : (Iterable<DmlBookmarks>) mDmlBookmarks.values())
        {
            BookmarkValidator dmlFallbackValidator = new BookmarkValidator(mWarningCallback, mTruncateNames);

            ISetGeneric<String> preservedNames = dmlBookmarks.getPreservedMainBookmarkNames();
            for (Node bookmarkNode : dmlBookmarks.getFallbackBookmarks())
            {
                if (!preservedNames.contains(((IBookmarkNode)bookmarkNode).getName()))
                {
                    bookmarkNode.remove();
                    continue;
                }

                if (bookmarkNode.getNodeType() == NodeType.BOOKMARK_START)
                    dmlFallbackValidator.onBookmarkStart((BookmarkStart)bookmarkNode, StoryType.MAIN_TEXT);
                else
                    dmlFallbackValidator.onBookmarkEnd((BookmarkEnd)bookmarkNode, StoryType.MAIN_TEXT);
            }

            dmlFallbackValidator.onDocumentEnd();
        }
    }

    private boolean postponeDmlFallbackBookmark(Node bookmarkNode)
    {
        ShapeBase dml = getCurrentDml();
        if (dml == null)
            return false;

        DmlBookmarks dmlBookmarks = null;
        Ref<DmlBookmarks> referenceToDmlBookmarks = new Ref<DmlBookmarks>(dmlBookmarks);
        boolean outRefCondition0 = !msDictionary.tryGetValue(mDmlBookmarks, dml, /*out*/ referenceToDmlBookmarks);
        dmlBookmarks = referenceToDmlBookmarks.get();
        if (outRefCondition0)
        {
            dmlBookmarks = new DmlBookmarks();
            msDictionary.add(mDmlBookmarks, dml, dmlBookmarks);
        }

        boolean isFallbackBookmark = (dml.getFallbackShape() != null) && bookmarkNode.isAncestorNode(dml.getFallbackShape());
        dmlBookmarks.addBookmark(bookmarkNode, isFallbackBookmark);

        return isFallbackBookmark;
    }

    private ShapeBase getCurrentDml()
    {
        return StackExtension.top(mDmlStack);
    }

    /// <summary>
    /// Check is it necessary to trim bookmark name.
    /// </summary>
    /// <remarks>
    /// WORDSNET-12492 Allow creation of bookmarks over 40 chars long when the target document has Pdf format.
    /// For resolve this issue does not trim bookmark name while saving document to Pdf format.
    /// Also was removed bookmark name trimming in <see cref="Aspose.Words.Fields.FieldRef"/>
    /// and <see cref="Aspose.Words.Fields.FieldUnknown"/> classes for update field operations.
    /// </remarks>
    /// <param name="bookmarkName">Name of the bookmark.</param>
    /// <returns>Return "True" when bookmark name need to be trimmed.</returns>
    private boolean isNeedTrimBookmarkName(String bookmarkName)
    {
        return (mTruncateNames && (bookmarkName.length() > Bookmark.MAX_NAME_LENGTH));
    }

    private void onBookmarkStart(BookmarkStart bookmarkStart, /*StoryType*/int storyType)
    {
        if (isNeedTrimBookmarkName(bookmarkStart.getName()))
        {
            WarningUtil.warnUnexpected(mWarningCallback, WarningStrings.BOOKMARK_VALIDATOR_TOO_LONG_NAME);
            bookmarkStart.setNameInternal(bookmarkStart.getName().substring((0), (0) + (Bookmark.MAX_NAME_LENGTH)));
        }

        String bookmarkItemKey = BookmarkValidatorItem.getKey(bookmarkStart);
        BookmarkValidatorItem validatorItem = mValidatorItemsByName.get(bookmarkItemKey);
        if (validatorItem == null)
        {
            // A bookmark with this name is encountered for the first time, remember it.
            mValidatorItemsByName.add(bookmarkItemKey, new BookmarkValidatorItem(bookmarkStart, storyType));
        }
        else
        {
            if (validatorItem.hasStartOnly())
            {
                // A bookmark with this name already exists.
                // Delete the extra bookmark node that we just found.
                bookmarkStart.remove();
            }
            else
            {
                // A bookmark with this name already exists.
                // Delete the bookmark nodes that we found before.
                validatorItem.getBookmarkStart().remove();
                validatorItem.getBookmarkEnd().remove();

                mValidatorItemsByName.set(bookmarkItemKey, new BookmarkValidatorItem(bookmarkStart, storyType));
            }

            warn(WarningType.DATA_LOSS, msString.format(WarningStrings.BOOKMARK_VALIDATOR_DUPLICATE_NAME, bookmarkStart.getName()));
        }
    }

    private void onBookmarkEnd(BookmarkEnd bookmarkEnd, /*StoryType*/int storyType)
    {
        if (isNeedTrimBookmarkName(bookmarkEnd.getName()))
        {
            warn(WarningType.DATA_LOSS, WarningStrings.BOOKMARK_VALIDATOR_TOO_LONG_NAME);
            bookmarkEnd.setNameInternal(bookmarkEnd.getName().substring((0), (0) + (Bookmark.MAX_NAME_LENGTH)));
        }

        String bookmarkItemKey = BookmarkValidatorItem.getKey(bookmarkEnd);
        BookmarkValidatorItem validatorItem = mValidatorItemsByName.get(bookmarkItemKey);

        if (isBookmarkEndValid(validatorItem, bookmarkEnd, storyType))
        {
            // We seen bookmark start only and have not seen bookmark end yet, its great.
            validatorItem.setBookmarkEnd(bookmarkEnd);
        }
        else
        {
            // Either got end when have not seen start yet, get end second time or end is in different story.
            // Delete the extra bookmark node that we just found.
            bookmarkEnd.remove();

            warn(WarningType.DATA_LOSS, msString.format(WarningStrings.BOOKMARK_VALIDATOR_NO_START, bookmarkEnd.getName()));
        }
    }

    private void onDocumentEnd()
    {
        for (BookmarkValidatorItem validatorItem : mValidatorItemsByName.getValues())
        {
            if (validatorItem.hasStartOnly())
            {
                warn(WarningType.DATA_LOSS, msString.format(WarningStrings.BOOKMARK_VALIDATOR_NO_END, validatorItem.getBookmarkStart().getName()));
                validatorItem.getBookmarkStart().remove();
            }
        }
    }

    /// <summary>
    /// Checks that BookmarkEnd node is correctly placed and has corresponding BookmarkStart node.
    /// </summary>
    private static boolean isBookmarkEndValid(BookmarkValidatorItem validatorItem, BookmarkEnd bookmarkEnd, /*StoryType*/int bookmarkEndStoryType)
    {
        if (validatorItem == null)
            return false;

        if (!validatorItem.hasStartOnly())
            return false;

        if (validatorItem.getBookmarkStoryType() != bookmarkEndStoryType)
            return false;

        // WORDSNET-19516 Special case for Comment type. Both start and end bookmark nodes must be inside one Comment node.
        if (bookmarkEndStoryType == StoryType.COMMENTS)
        {
            Comment endComment = (Comment)bookmarkEnd.getAncestor(NodeType.COMMENT);
            Comment startComment = (Comment)validatorItem.getBookmarkStart().getAncestor(NodeType.COMMENT);

            if (!msObject.referenceEquals(startComment, endComment))
                return false;
        }

        return true;
    }

    /// <summary>
    /// Returns bookmark names.
    /// </summary>
    ISetGeneric<String> getBookmarkNames()
    {
        return new HashSetGeneric<String>(mValidatorItemsByName.getKeys());
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    private void warn(/*WarningType*/int warningType, String description)
    {
        if (mWarningCallback != null)
            mWarningCallback.warning(new WarningInfo(warningType, WarningSource.VALIDATOR, description));
    }

    /// <summary>
    /// Key is case insensitive bookmark name. Value is a <see cref="BookmarkValidatorItem"/>.
    /// </summary>
    private /*final*/ StringToObjDictionary<BookmarkValidatorItem> mValidatorItemsByName =
        new StringToObjDictionary<BookmarkValidatorItem>(false);

    /// <summary>
    /// Collects bookmarks nodes which are contained in DML nodes preserving order of appearance.
    /// </summary>
    private /*final*/ Map<ShapeBase, BookmarkValidator.DmlBookmarks> mDmlBookmarks = new HashMap<ShapeBase, BookmarkValidator.DmlBookmarks>();

    private /*final*/ msStack<ShapeBase> mDmlStack = new msStack<ShapeBase>();

    private /*final*/ IWarningCallback mWarningCallback;

    /// <summary>
    /// Indicates that bookmark names should be truncated.
    /// </summary>
    private /*final*/ boolean mTruncateNames;

    private static class DmlBookmarks
    {
        DmlBookmarks()
        {
            mFallbackBookmarks = new ArrayList<Node>();
        }

        Collection<Node> getFallbackBookmarks() { return mFallbackBookmarks; };

        private Collection<Node> mFallbackBookmarks;

        void addBookmark(Node node, boolean isFallbackBookmark)
        {
            if (isFallbackBookmark)
                getFallbackBookmarks().add(node);
            else
                mMainBookmarks.add(node);
        }

        ISetGeneric<String> getPreservedMainBookmarkNames()
        {
            CaseInsensitiveHashSet result = new CaseInsensitiveHashSet(mMainBookmarks.size() / 2);
            for (Node bookmark : mMainBookmarks)
            {
                String bookmarkName = ((IBookmarkNode)bookmark).getName();
                if (result.contains(bookmarkName))
                    continue;

                if (bookmark.isRemoved())
                    continue;

                result.add(bookmarkName);
            }

            return result;
        }

        private /*final*/ Collection<Node> mMainBookmarks = new ArrayList<Node>();
    }
}

