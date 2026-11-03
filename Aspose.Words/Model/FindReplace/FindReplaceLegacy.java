// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/08/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.RegularExpressions.MatchCollection;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ms.System.Text.RegularExpressions.Group;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


//TODO 2 Allow regex substitution patterns to be used in the replacement text.

//TODO 3 Allow replacing replace paragraph, section and page breaks, maybe even cell breaks.

//TODO 2 Create a sample that shows how to replace using a group name or index.
//TODO 2 Create a sample to include increasing numbers into text.
//TODO 2 Create a sample that shows how to replace including or excluding field codes.
//TODO 2 Create a sample that shows how to replace in body only or in headers only.

/// <summary>
/// Performs replace operations over the document tree.
/// Maybe later can make a public class.
/// </summary>
/// <dev>
/// This is legacy find/replace algorithm, we had to return it after customer complained. See WORDSNET-20007
/// </dev>
class FindReplaceLegacy
{
    FindReplaceLegacy(Node rootNode, Regex pattern, String replacement, IReplacingCallback handler, boolean isForward)
    {
        this.setRootNode(rootNode);
        this.setPattern(pattern);
        this.setReplacement(replacement);
        this.setReplacingCallback(handler);
        this.isForward(isForward);

        mDoc = rootNode.getDocument();
    }

    FindReplaceLegacy(NodeRange nodeRange, Regex pattern, String replacement, IReplacingCallback handler, boolean isForward)
    {
        mNodeRange = nodeRange;
        this.setPattern(pattern);
        this.setReplacement(replacement);
        this.setReplacingCallback(handler);
        this.isForward(isForward);

        mDoc = nodeRange.getDocument();
    }

    FindReplaceLegacy(Node rootNode, String oldValue, String newValue, boolean isMatchCase, boolean isMatchWholeWord)
    {
    	this(rootNode, textToPattern(oldValue, isMatchCase, isMatchWholeWord), newValue, null, false);
	
        //Do nothing.
    }

    FindReplaceLegacy(NodeRange nodeRange, String oldValue, String newValue, boolean isMatchCase, boolean isMatchWholeWord)
    {
    	this(nodeRange, textToPattern(oldValue, isMatchCase, isMatchWholeWord), newValue, null, false);
	
        //Do nothing.
    }

    /// <summary>
    /// Gets or sets the node that is the root for the replace operation.
    /// </summary>
    Node getRootNode() { return mRootNode; }
    void setRootNode(Node value)
    {
        if (value == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "value");
        mRootNode = value;
    }

    /// <summary>
    /// Gets or sets the regular expression used to find matches.
    /// </summary>
    Regex getPattern() { return mPattern; }
    void setPattern(Regex value)
    {
        if (value == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "value");

        if ("".equals(value.toString()))
            throw new IllegalArgumentException("The search string cannot be empty.");

        //No pointing checking for break characters in the regex because the pattern
        //can capture them even if they are not specified explicitly.

        mPattern = value;
    }

    /// <summary>
    /// Gets or sets the replacement string.
    /// </summary>
    /// <remarks>
    /// <p><b>Replacement</b> is used when no custom <b>ReplaceEvaluator</b> is specified.</p>
    /// </remarks>
    String getReplacement() { return mReplacement; }
    void setReplacement(String value)
    {
        if (value == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "value");

        if (msString.indexOfAny(value, G_CANNOT_INSERT_CHARS) != -1)
            throw new IllegalArgumentException("The replace string cannot contain special or break characters.");

        mReplacement = value;
    }

    /// <summary>
    /// Gets or sets the method that will be called for each match, allows the user to control how the replace is made.
    /// </summary>
    /// <remarks>
    /// <p>If null, the <b>Replacement</b> string will be used to replace all found matches.</p>
    /// </remarks>
    IReplacingCallback getReplacingCallback() { return mReplacingCallback; }
    void setReplacingCallback(IReplacingCallback value) { mReplacingCallback = value; }

    /// <summary>
    /// Indicates whether the find and replace will be performed in forward or backward direction inside the <b>RootNode</b>.
    /// </summary>
    /// <remarks>
    /// <p>Default is false.</p>
    /// </remarks>
    boolean isForward() { return mIsForward; }
    void isForward(boolean value) { mIsForward = value; }

    private MatchCollection getMatches()
    {
        if (mNodeRange != null)
        {
            NodeTextCollectorOptions options = new NodeTextCollectorOptions();
            options.setSkipCommentText(false);
            options.setSkipFootnoteText(false);

            return mPattern.matches(NodeTextCollector.getText(mNodeRange, options));
        }

        return mPattern.matches(mRootNode.getText());
    }

    private NodeIndexer getNodeIndexer()
    {
        return mNodeRange == null
            ? new NodeIndexer(mRootNode)
            : new NodeIndexer(mNodeRange);
    }

    private CompositeNode getCommonParent(Node node1, Node node2)
    {
        int nodeDepth1 = NodeUtil.getDepth(node1);
        int nodeDepth2 = NodeUtil.getDepth(node2);

        CompositeNode parent1 = (nodeDepth2 > nodeDepth1)
            ? node1.getParentNode()
            : node2.getParentNode();

        while (parent1 != null)
        {
            CompositeNode parent2 = (nodeDepth2 > nodeDepth1)
                ? node2.getParentNode()
                : node1.getParentNode();

            while (parent2 != null)
            {
                if (parent1 == parent2)
                    return parent1;

                parent2 = parent2.getParentNode();
            }

            parent1 = parent1.getParentNode();
        }

        return mDoc;
    }

    private Node getParentNode()
    {
        return mNodeRange == null
            ? getRootNode()
            : getCommonParent(mNodeRange.getStart().getNode(), mNodeRange.getEnd().getNode());
    }

    /// <summary>
    /// Executes the find and replace operation.
    /// </summary>
    /// <returns>The number of replacements made.</returns>
    int replace() throws Exception
    {
        MatchCollection matches = getMatches();

        NodeIndexer nodeIndexer = getNodeIndexer();
        nodeIndexer.update();

        int replaceCount = 0;

        //This loop works both for forward and backward replace.
        int remainingMatchesCount = matches.getCount();
        int step = (mIsForward) ? 1 : -1;                //Step of the loop, different depending on direction.
        int i = (mIsForward) ? 0 : matches.getCount() - 1;    //Initialize the current match index depending on direction.
        ReplacementInfo lastReplacementInfo = null;      //Information about the last replacement
        int runPosDelta = 0;                             //Total position delta in the last run, in which
                                                         //replacement was. (For several replacements in one run.)
        while (remainingMatchesCount > 0)
        {
            Match match = matches.get(i);

            //I'm not sure why, but some patterns yield matches of zero length.
            //I don't think I need to try to replace them.
            if (match.getLength() > 0)
            {
                //Local position of the match in the first run.
                int matchNodeStart = nodeIndexer.getNodeStartByPosition(match.getIndex());
                int matchOffset = match.getIndex() - matchNodeStart;
                Node matchNode = nodeIndexer.getNodeByPosition(match.getIndex());
                if ((mIsForward) &&
                    (lastReplacementInfo != null) &&
                    (lastReplacementInfo.getLastRun() == matchNode))
                {
                    // The previous replacement was in the same run: the offset should be corrected
                    runPosDelta += lastReplacementInfo.getPosDelta();
                    matchOffset += runPosDelta;
                }
                else
                {
                    runPosDelta = 0;
                }

                // WORDSNET-28070 Added a new replacing arg to get match end node.
                int endIndex = matchOffset + matches.get(i).getLength();
                Node matchEndNode = nodeIndexer.getNodeByPosition(endIndex);

                ReplacingArgs args = new ReplacingArgs(match, matchOffset, matchNode, matchEndNode, mReplacement);
                /*ReplaceAction*/int action = (mReplacingCallback != null) ? doReplacingCallback(args, nodeIndexer) :
                    ReplaceAction.REPLACE;

                switch (action)
                {
                    case ReplaceAction.REPLACE:
                    {
                        lastReplacementInfo = replaceOccurrence(args);

                        if (lastReplacementInfo.getLastRun() != null)
                            replaceCount++;

                        break;
                    }
                    case ReplaceAction.SKIP:
                        break;
                    case ReplaceAction.STOP:
                        return replaceCount;
                    default:
                        throw new IllegalStateException("Unknown replace action.");
                }
            }

            i += step;
            remainingMatchesCount--;
        }

        return replaceCount;
    }

    /// <summary>
    /// Calls replacing callback to get action for a match.
    /// </summary>
    /// <remarks>May rebuild the node index if document tree will be changed in the callback.</remarks>
    private /*ReplaceAction*/int doReplacingCallback(ReplacingArgs args, NodeIndexer nodeIndexer) throws Exception
    {
        int treeChangeCount = mDoc.getTreeChangeCount();

        /*ReplaceAction*/int action = mReplacingCallback.replacing(args);

        if (mDoc.getTreeChangeCount() != treeChangeCount)
        {
            if (!mIsForward)
            {
                // WORDSNET-9902 Should rebuild indexes after document tree was changed.
                nodeIndexer.update();
            }
            else
            {
                // Further replacements are disabled because wrong result may be got.
                throw new IllegalStateException(
                    "Cannot perform further replacements since the document has been changed.");
            }
        }

        return action;
    }

    /// <summary>
    /// Replaces a single occurrence according to the parameters specified in the args.
    /// </summary>
    /// <returns>An object that contains the last modified run and the difference between the number
    /// of inserted and deleted characters in the last run.
    /// If inserted more characters than deleted, the number is positive.</returns>
    private ReplacementInfo replaceOccurrence(ReplacingArgs args) throws Exception
    {
        ReplacementInfo infoToReturn = new ReplacementInfo();

        Group group;

        // FindReplace tracking is quite complex task so postpone it till later and
        // suspend tracking during this operation.
        SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(mDoc);
        try /*JAVA: was using*/
        {
                group = args.getMatchInternal().getGroups().get(args.getGroupIndex());

            if (msString.indexOfAny(group.getValue(), G_CANNOT_REPLACE_CHARS) != -1)
                throw new UnsupportedOperationException("The match includes one or more special or break characters and cannot be replaced.");

            // We shall ignore SpecialChar node.
            if (args.getMatchNode().getNodeType() == NodeType.SPECIAL_CHAR)
                return infoToReturn;

            Run firstRun = (Run)args.getMatchNode();

            // We shall replace CustomXML property content to avoid a false positive replacement of SDT content
            // which will be rolled back by a subsequent UpdateDataBoundContent().
            if (firstRun.getParentNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
            {
                StructuredDocumentTag sdt = (StructuredDocumentTag)firstRun.getParentNode();
                if (!SdtContentHelper.replaceDataBoundContent(sdt, mPattern, mReplacement))
                    return infoToReturn;
            }

            //Keep a counter how much is still to delete.
            int remainingMatchLength = group.getLength();
            //Where the remove starts in the current run.
            int removeStartInCurRun = args.getMatchOffset();
            Run curRun = firstRun;

            //Loop because the match can span more than one node.
            while (remainingMatchLength > 0)
            {
                int availableToRemoveInCurRun = curRun.getTextLength() - removeStartInCurRun;
                int removeCountInCurRun = Math.min(availableToRemoveInCurRun, remainingMatchLength);

                curRun.setText(msString.remove(curRun.getText(), removeStartInCurRun, removeCountInCurRun));

                remainingMatchLength -= removeCountInCurRun;
                removeStartInCurRun = 0;

                infoToReturn.setLastRun(curRun);
                infoToReturn.setPosDelta(-removeCountInCurRun);

                // Select the next node because we might need to remove the current run.
                Node nextNode = curRun;
                do
                {
                    // We could get runs, bookmarks, smart tags etc here. Skip all except runs.
                    nextNode = nextNode.nextPreOrder(getParentNode());
                } while ((nextNode != null) && (nextNode.getNodeType() != NodeType.RUN));

                // We normally delete the run node if it becomes empty, but
                // keep the first run for now since it might have to stay to keep the formatting.
                if ((curRun != firstRun) && ("".equals(curRun.getText())))
                    curRun.remove();

                curRun = (Run)nextNode;
            }

            //Insert new text.
            firstRun.setText(msString.insert(firstRun.getText(), args.getMatchOffset(), args.getReplacement()));
            //Remove the empty first run if it was completely cleared.
            if ("".equals(firstRun.getText()))
                firstRun.remove();

            if (infoToReturn.getLastRun() == firstRun)
            {
                // The replacement is in one run.
                infoToReturn.setPosDelta(args.getReplacement().length() - group.getLength());
            }
        }
        finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }

        return infoToReturn;
    }

    /// <summary>
    /// Creates a regex pattern for a simple text search string.
    /// </summary>
    private static Regex textToPattern(String oldValue, boolean isMatchCase, boolean isMatchWholeWord)
    {
        String pattern = Regex.escape(oldValue);
        if (isMatchWholeWord)
            pattern = "\\b" + pattern + "\\b";    //This means extract at the word boundary.

        /*RegexOptions*/int options = (isMatchCase) ? RegexOptions.NONE : RegexOptions.IGNORE_CASE;

        return new Regex(pattern, options);
    }

    /// <summary>
    /// At the moment cannot replace any of these chars.
    /// </summary>
    private static /*final*/ char[] G_CANNOT_REPLACE_CHARS = new char[]
    {
        ControlChar.PICTURE_CHAR,
        ControlChar.FOOTNOTE_REF_CHAR,
        ControlChar.FOOTNOTE_SEPARATOR_CHAR,
        ControlChar.FOOTNOTE_CONTINUATION_CHAR,
        ControlChar.CELL_CHAR,
        //TODO 2 It is unfortunate that page break and section break symbols are same.
        //This prevents us from letting user to search for page break characters.
        ControlChar.SECTION_BREAK_CHAR,
        ControlChar.PARAGRAPH_BREAK_CHAR,
        ControlChar.FIELD_START_CHAR,
        ControlChar.FIELD_SEPARATOR_CHAR,
        ControlChar.FIELD_END_CHAR
    };

    /// <summary>
    /// Cannot insert any of these chars. Similar to the chars that we cannot replace,
    /// with the exception of page break character (actually same code as section break).
    /// </summary>
    private static /*final*/ char[] G_CANNOT_INSERT_CHARS = new char[]
    {
        ControlChar.PICTURE_CHAR,
        ControlChar.FOOTNOTE_REF_CHAR,
        ControlChar.FOOTNOTE_SEPARATOR_CHAR,
        ControlChar.FOOTNOTE_CONTINUATION_CHAR,
        ControlChar.CELL_CHAR,
        ControlChar.PARAGRAPH_BREAK_CHAR,
        ControlChar.FIELD_START_CHAR,
        ControlChar.FIELD_SEPARATOR_CHAR,
        ControlChar.FIELD_END_CHAR
    };


    private /*final*/ DocumentBase mDoc;
    private Node mRootNode;
    private /*final*/ NodeRange mNodeRange;
    private Regex mPattern;
    private String mReplacement;
    private IReplacingCallback mReplacingCallback;
    private boolean mIsForward;

    /// <summary>
    /// Information about performed replacement.
    /// </summary>
    private static class ReplacementInfo
    {
        /// <summary>
        /// Returns the last run, in which the replacement has been performed.
        /// </summary>
        Run getLastRun() { return mLastRun; }
        void setLastRun(Run value) { mLastRun = value; }

        /// <summary>
        /// Returns position delta for the further replacement in the <see cref="LastRun"/>
        /// </summary>
        int getPosDelta() { return mPosDelta; }
        void setPosDelta(int value) { mPosDelta = value; }

        private Run mLastRun;
        private int mPosDelta;
    }

}

