// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2016 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.Debug;
import com.aspose.StringUtil;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Collections;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.RegularExpressions.MatchCollection;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.BidiCharacterType;
import com.aspose.UnicodeCharacterDataResolver;
import com.aspose.ms.System.msChar;
import com.aspose.HashSetGeneric;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;
import java.util.Comparator;


/// <summary>
/// Implements advanced find/replace operations.
/// </summary>
/// <remarks>
/// Supports to match and replace with breaks. Customer can specify formatting of replacement.
/// </remarks>
class FindReplace extends DocumentVisitor
{
    FindReplace(Node root, String pattern, String replacement, FindReplaceOptions options)
    {
    	this(root, textToRegex(pattern, options), replacement, options);
	
    }

    FindReplace(NodeRange nodeRange, String pattern, String replacement, FindReplaceOptions options)
    {
    	this(nodeRange, textToRegex(pattern, options), replacement, options);
	
    }

    FindReplace(NodeRange nodeRange, Regex regex, String replacement, FindReplaceOptions options)
    {
        Debug.assertTrue(regex != null);

        mOptions = options;
        mNodeRange = nodeRange;
        mDoc = nodeRange.getDocument();

        String pattern = replaceMetaCharacters(regex.toString());
        if (!StringUtil.hasChars(pattern))
            throw new IllegalArgumentException("The search string cannot be null or empty.");
        mPattern = new Regex(pattern, regex.getOptions());
        mReplaceWith = replaceMetaCharacters(replacement);
    }

    FindReplace(Node root, Regex regex, String replacement, FindReplaceOptions options)
    {
        Debug.assertTrue(regex != null);

        mOptions = options;
        mRootNode = root;
        mDoc = root.getDocument();

        String pattern = replaceMetaCharacters(regex.toString());
        if (!StringUtil.hasChars(pattern))
            throw new IllegalArgumentException("The search string cannot be null or empty.");
        mPattern = new Regex(pattern, regex.getOptions());
        mReplaceWith = replaceMetaCharacters(replacement);
    }

    int replace() throws Exception
    {
        SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(mDoc, SuspendedRevisionTypes.MOVE);
        try /*JAVA: was using*/
        {
        SuspendMappedCustomXmlUpdateDocument suspendMappedCustomXmlUpdateDocumentResource = new SuspendMappedCustomXmlUpdateDocument(mDoc);
        try /*JAVA: was using*/
        {
            return replaceCore(mRootNode);
        }
        finally { if (suspendMappedCustomXmlUpdateDocumentResource != null) suspendMappedCustomXmlUpdateDocumentResource.dispose(); }
        }
        finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }
    }

    /// <summary>
    /// Sorts nodes of the pending list to be in order that they are represented in visual structure of document.
    /// </summary>
    /// <dev>
    /// Headers/footers are sorted to be in correct order. Note that now body nodes are processed before
    /// the pending list, for example, before headers.
    /// </dev>
    static void sortPendingList(ArrayList<Node> list)
    {
        // Headers/footers are only sorted. Let's keep order of other nodes unchanged.
        // Headers/footers of a section occupies continuous range of list items because of FindReplaceIndexer
        // behavior. Let's use this assumption to simplify sorting code.

        ArrayList<Node> oldOrder = new ArrayList<Node>();
        msArrayList.addRange(oldOrder, list);
        list.clear();

        ArrayList<Node> headerFooters = new ArrayList<Node>();
        Section section = null;
        int startIndex = -1;

        for (int i = 0; i < oldOrder.size(); i++)
        {
            Node node = oldOrder.get(i);
            if (node.getNodeType() == NodeType.HEADER_FOOTER)
            {
                HeaderFooter headerFooter = (HeaderFooter)node;
                if (section != null && headerFooter.getParentSection() != section)
                {
                    Collections.sort(headerFooters, new HeaderFooterComparer(section.getPageSetup().getDifferentFirstPageHeaderFooter()));
                    msArrayList.addRange(list, headerFooters);
                    addNodes(oldOrder, startIndex + 1, i - 1, list, NodeType.HEADER_FOOTER);

                    headerFooters.clear();
                    startIndex = i;
                }
                else
                {
                    if (startIndex < 0)
                        startIndex = i;
                }

                msArrayList.add(headerFooters, headerFooter);
                section = headerFooter.getParentSection();
            }

            if (section == null)
                msArrayList.add(list, node);
        }

        if (headerFooters.size() > 0)
        {
            Debug.assertTrue(section != null);

            Collections.sort(headerFooters, new HeaderFooterComparer(section.getPageSetup().getDifferentFirstPageHeaderFooter()));
            msArrayList.addRange(list, headerFooters);
            addNodes(oldOrder, startIndex + 1, oldOrder.size() - 1, list, NodeType.HEADER_FOOTER);
        }
    }

    /// <summary>
    /// Finds and replaces in one story.
    /// </summary>
    private int replaceCore(Node root) throws Exception
    {
        // WORDSNET-14691 Replace content of CustomXML/Core properties if SDT is data bound.
        // WORDSNET-24241 Added new option to control the behavior.
        if ((root != null) && !mOptions.getIgnoreStructuredDocumentTags() && (root.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG))
        {
            StructuredDocumentTag sdt = (StructuredDocumentTag)root;
            SdtContentHelper.replaceDataBoundContent(sdt, mPattern, mReplaceWith);
        }

        mIndexer = mNodeRange == null
            ? new FindReplaceIndexer(root, mOptions)
            : new FindReplaceIndexer(mNodeRange, root, mOptions);

        int replaceCount = 0;

        // WORDSNET-24783 There can be situation when Indexer text is empty, but PendingList is not. So, the check when
        // nothing to replace is separated for text and pending list to avoid possible exceptions.
        if (mIndexer.getText().getLength() != 0)
        {
            // WORDSNET-20212 Split text to lines manually when regex option Multiline is enabled to allow
            // anchors (^ and $) work correctly.
            // WORDSNET-21588 Split text only when there are any of the anchors (^ or $) specified in pattern.
            if (isMultiline() && (msString.indexOfAny(mPattern.toString(), G_ANCHORS) != -1))
            {
                int start = 0;
                for (int i = 0; i < mIndexer.getSpecialPositions().getCount(); i++)
                {
                    int breakPosition = mIndexer.getSpecialPositions().get(i);
                    String text = mIndexer.getText().substring(start, breakPosition - start + 1);
                    replaceCount += replaceLine(text, start);

                    start = breakPosition + 1;
                }
            }
            else
            {
                replaceCount = replaceLine(mIndexer.getText().getAsString(), 0);
            }
        }

        // Process headers/footers/textboxes/comments/cells content separately from document content.
        // There are many reasons for it, for example, section breaks insertion/deletion causes headers to move
        // within textual presentation and it's hard to detect.
        // But most major problem is that regex can match partially in one part and partially in another part and we should avoid it.
        if (mIndexer.getPendingList().size() != 0)
        {
            ArrayList<Node> pendingList = mIndexer.getPendingList();
            sortPendingList(pendingList);
            for (Node node : pendingList)
                replaceCount += replaceCore(node);
        }

        return replaceCount;
    }

    /// <summary>
    /// Finds and replaces text in a specified line considering its offset within whole text.
    /// </summary>
    private int replaceLine(String line, int lineStart) throws Exception
    {
        MatchCollection matches = mPattern.matches(line);

        int step = (isForward()) ? 1 : -1;
        int i = (isForward()) ? 0 : matches.getCount() - 1;
        int remainingMatches = matches.getCount();

        int replaceCount = 0;

        while (remainingMatches > 0)
        {
            /*ReplaceAction*/int action = ReplaceAction.REPLACE;
            String replaceWith = mReplaceWith;

            // WORDSNET-21588 We have to use match offset relative to the whole text instead of the current line.
            int curMatchIndex = matches.get(i).getIndex() + lineStart;

            // WORDSNET-23258 Check option before call to ReplacingCallback to avoid calling it for matches
            // that are not permit 'WholeWordsOnly' option restriction.
            if (isFindWholeWordsOnly() && !isWordBoundary(matches.get(i), lineStart))
                action = ReplaceAction.SKIP;

            if ((getReplacingCallback() != null) && (action == ReplaceAction.REPLACE))
            {
                Node matchNode = mIndexer.getNodeByPosition(curMatchIndex);
                int matchOffset = curMatchIndex - mIndexer.getNodeStartByPosition(curMatchIndex);

                // WORDSNET-28070 Added a new replacing arg to get match end node.
                int endIndex = curMatchIndex + matches.get(i).getLength();
                Node matchEndNode = mIndexer.getNodeByPosition(endIndex);

                ReplacingArgs args = new ReplacingArgs(matches.get(i), matchOffset, matchNode, matchEndNode, mReplaceWith);
                action = getReplacingCallback().replacing(args);
                replaceWith = replaceMetaCharacters(args.getReplacement());
            }

            if (action == ReplaceAction.STOP)
                break;

            if (action != ReplaceAction.SKIP)
            {
                // WORDSNET-19913 The new option is introduced to recognize substitutions within replacing string.
                if (isUseSubstitutions())
                    replaceWith = matches.get(i).result(replaceWith);

                if (replaceSubstring(replaceWith, curMatchIndex, matches.get(i).getLength()))
                    replaceCount++;
            }

            remainingMatches--;
            i += step;
        }

        return replaceCount;
    }

    /// <summary>
    /// Checks left and right word boundary for FindWholeWordsOnly option.
    /// </summary>
    private boolean isWordBoundary(Match match, int lineStart)
    {
        int pos = match.getIndex() + lineStart;

        if ((pos > 0) && (!isWordBoundary(mIndexer.getText().get(pos - 1))))
            return false;

        // WORDSNET-21639 Check the position is less than length of text.
        pos += match.getLength();
        if ((pos < mIndexer.getText().getLength()) && !isWordBoundary(mIndexer.getText().get(pos)))
            return false;

        return true;
    }

    /// <summary>
    /// Checks that symbol can be a word boundary.
    /// </summary>
    /// <remarks>
    /// Tab, Space, Paragraph Break, Section Break, Cell Break should exist regardless of other defined word boundary symbols.
    /// </remarks>
    private static boolean isWordBoundary(char c)
    {
        return (c == ' ') ||
               (c == '\t') ||
               (c == FindReplaceIndexer.PARAGRAPH_BREAK_CHAR) ||
               (c == FindReplaceIndexer.SECTION_BREAK_CHAR) ||
               (c == FindReplaceIndexer.CELL_BREAK_CHAR) ||
               // WORDSNET-25588 Consider column break as word boundary.
               (c == ControlChar.COLUMN_BREAK_CHAR) ||
               (WORD_BOUNDARY_SYMBOLS.contains(Character.toString(c)));
    }

    /// <summary>
    /// Adds nodes from the source list to the destination list except nodes of the filteredNodeType type.
    /// </summary>
    private static void addNodes(ArrayList<Node> sourceList, int fromIndex, int toIndex,
        ArrayList<Node> destinationList, /*NodeType*/int filteredNodeType)
    {
        for (int i = fromIndex; i <= toIndex; i++)
        {
            if (sourceList.get(i).getNodeType() != filteredNodeType)
                msArrayList.add(destinationList, sourceList.get(i));
        }
    }

    /// <summary>
    /// Replaces substring of the indexer text at a specified offset and length.
    /// </summary>
    private boolean replaceSubstring(String replacement, int offset, int length) throws Exception
    {
        // This is first node which will be deleted during the replacing process.
        Node startNode = mIndexer.getNodeByPosition(offset);

        // We mimic Word and do not replace shapes.
        if (startNode.getNodeType() == NodeType.SHAPE)
            return false;

        // Word expands SmartTags if any text within it is replaced.
        if (startNode.getParentNode() instanceof SmartTag)
            expandSmartTagToPara(startNode);

        // The text of the run may be longer than the text we have to replace.
        if (startNode.getNodeType() == NodeType.RUN)
            splitRun(offset, (Run)startNode);

        // Word applies formatting to the inserted content from the node where the new content will be inserted.
        // This is actually first node that will be deleted upon replace process.
        // Also customer can specify additional formatting to apply to the inserted content.
        updateRefFormatting(offset);

        String textToDelete = mIndexer.getText().substring(offset, length);

        // WORDSNET-26924, 23740 Changed the order of deletion/insertion.
        // This is the index in text where a new content will be inserted.
        int insertIndex = offset + textToDelete.length();

        // This is last node which will be deleted during the replacing process.
        Node endNode = null;
        // WORDSNET-28047 Check index greater 0 to avoid possible exception.
        if (insertIndex > 0)
            endNode = mIndexer.getNodeByPosition(insertIndex - 1);

        // WORDSNET-28047 There can be previously removed nodes after which we are going
        // to insert a new replacement string. Check here Parent instead of IsRemoved,
        // as it doesn't work correctly in SDTs.
        if ((endNode == null) || (endNode.getParentNode() == null))
        {
            // This is a node before we insert replacement.
            Node refNode = mIndexer.getNodeByPosition(insertIndex);
            if ((refNode == null) || (refNode.getParentNode() == null))
                return false;

            if (refNode.getNodeLevel() == NodeLevel.INLINE)
            {
                Run run = new Run(mDoc, replacement, ((Inline)refNode).getRunPr());
                refNode.insertPrevious(run);
                return true;
            }

            return false;
        }

        // And this should be split the same as first replacing node above.
        if (endNode.getNodeType() == NodeType.RUN)
            splitRun(insertIndex, (Run)endNode);

        // Word expands SmartTags if any text within it is replaced.
        if (endNode.getParentNode() instanceof SmartTag)
            expandSmartTagToPara(endNode);

        // First we insert new text.
        splitAndInsert(insertIndex, replacement);

        // Then delete old text.
        splitAndDelete(offset, textToDelete);

        return true;
    }

    /// <summary>
    /// Splits a specified Run at a specified offset.
    /// </summary>
    /// <remarks>
    /// The specified Run becomes right part of the split Run.
    /// The corresponding indexes in <see cref="mIndexer"/> will be also updated.
    /// </remarks>
    private void splitRun(int offset, Run run)
    {
        // In case we are in the middle of run split it.
        int nodeStart = mIndexer.getNodeStartByPosition(offset);
        int localStart = offset - nodeStart;

        if ((localStart <= 0) || (localStart >= run.getText().length()))
            return;

        Run newRun;

        // See "TestFindReplaceB".
        SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(mDoc);
        try /*JAVA: was using*/
        {
            newRun = run.splitBefore(localStart);
        }
        finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }

        // Update modified entry without rebuilding whole index.
        // This greatly increases performance and helps to isolate from callback changes.
        mIndexer.replace(nodeStart, nodeStart, newRun);
        mIndexer.add(nodeStart + localStart, run);
    }

    /// <summary>
    /// Updates formatting of the reference <see cref="mRunPr"/> and <see cref="mParaPr"/>
    /// taking it from the node at a specified <see cref="mIndexer"/> offset considering
    /// <see cref="FindReplaceOptions.ApplyRunPr"/> and <see cref="FindReplaceOptions.ApplyParaPr"/>.
    /// </summary>
    private void updateRefFormatting(int offset)
    {
        mRunPr.clear();
        mParaPr.clear();

        Node node = mIndexer.getNodeByPosition(offset);
        IInline inline = ms.as(node, IInline.class);
        if (inline != null)
        {
            inline.getRunPr_IInline().expandTo(mRunPr);
            // WORDSNET-28047 The node might have been removed on previous replacement.
            // In this case we cannot get its parent formatting.
            if (inline.getParentParagraph_IInline() != null)
                inline.getParentParagraph_IInline().getParaPr().expandTo(mParaPr);
        }
        else if (NodeUtil.isInlineLevelNode(node))
        {
            Paragraph parentPara = (Paragraph)node.getAncestor(NodeType.PARAGRAPH);
            if (parentPara != null)
            {
                parentPara.getParagraphBreakRunPr().expandTo(mRunPr);
                parentPara.getParaPr().expandTo(mParaPr);
            }
        }
        else if (node.getNodeType() == NodeType.PARAGRAPH)
        {
            ((Paragraph)node).getParagraphBreakRunPr().expandTo(mRunPr);
            ((Paragraph)node).getParaPr().expandTo(mParaPr);
        }

        // Consider also optional formatting.
        if (mOptions == null)
            return;

        if (mOptions.getApplyRunPr() != null)
        {
            mOptions.getApplyRunPr().expandTo(mRunPr);

            // WORDSNET-25418 Remove theme color, when customer sets only Color,
            // as ThemeColor has precedence over the Color.
            if (mOptions.getApplyRunPr().contains(FontAttr.COLOR) && !mOptions.getApplyRunPr().contains(FontAttr.THEME_COLOR))
                mRunPr.remove(FontAttr.THEME_COLOR);
        }

        if (mOptions.getApplyParaPr() != null)
            mOptions.getApplyParaPr().expandTo(mParaPr);
    }

    /// <summary>
    /// Processes text insertion while breaks it at special chars.
    /// </summary>
    /// <param name="pos">The position in <see cref="mIndexer"/> where to insert a specified <paramref name="text"/>.</param>
    /// <param name="text">The text to insert at a specified <paramref name="pos"/>.</param>
    private void splitAndInsert(int pos, String text)
    {
        // Reference node to insert content after.
        Node refNode = mIndexer.getNodeByPosition(pos - 1);

        StringBuilder chunk = new StringBuilder(text.length());

        for (int i = 0; i < text.length(); i++)
        {
        	char ch = text.charAt(i);
            if (isSpecialChar(ch))
            {
                if (chunk.length() > 0)
                {
                    refNode = insertText(refNode, chunk.toString(), pos);
                    chunk.setLength(0);
                }

                refNode = insertSpecialChar(refNode, ch);
            }
            else
            {
                chunk.append(ch);
            }
        }

        if (chunk.length() > 0)
            insertText(refNode, chunk.toString(), pos);
    }

    /// <summary>
    /// Inserts text after given node.
    /// </summary>
    private Run insertText(Node refNode, String text, int pos)
    {
        RunPr runPr = AttrCollectionExtension.deepClone(mRunPr);

        // WORDSNET-15621 It seems that we need to set BiDi for strong RTL replacement text.
        if (isStrongRtlOrWhiteSpaces(text))
            runPr.setAttr(FontAttr.BIDI, AttrBoolEx.TRUE);

        // WORDSNET-20007 Preserve existing run on replacement.
        if ((refNode.getNodeType() == NodeType.RUN) && (refNode == mRootNode))
        {
            Run prevRun;
            Run curRun;

            SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(mDoc);
            try /*JAVA: was using*/
            {
                prevRun = (Run)refNode.insertPrevious(refNode.deepClone(false));
                curRun = (Run)refNode;
                curRun.setText(text);
                curRun.setRunPr(runPr);
            }
            finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }

            // We need to manually mark existing run as inserted if tracking is on.
            if (mDoc.isTrackRevisionsEnabled())
            {
                EditSession editSession = mDoc.fetchDocumentOrGlossaryMain().getEditSession();
                RevisionTrackingUtil.addInsertRevision(curRun, editSession);
            }

            int prevRunPos = pos - prevRun.getText().length();
            mIndexer.replace(prevRunPos, prevRunPos, prevRun);

            return curRun;
        }
        else
        {
            Run newRun = new Run(mDoc, text, runPr);

            if (refNode.getNodeLevel() == NodeLevel.INLINE)
            {
                // Just insert after inline node.
                refNode.insertNext(newRun);
            }
            else if (refNode.getNodeType() == NodeType.PARAGRAPH)
            {
                // Insertion after paragraph break means that we need to insert into beginning of next para.
                Paragraph para = (Paragraph)refNode;
                Paragraph nextPara = (Paragraph)para.nextPreOrderOfType(mDoc, NodeType.PARAGRAPH);
                if (nextPara == null)
                    nextPara = (Paragraph)para.insertNext(new Paragraph(mDoc, AttrCollectionExtension.deepClone(mParaPr), AttrCollectionExtension.deepClone(mRunPr)));

                nextPara.insertBefore(newRun, nextPara.getFirstChild());
            }
            else
            {
                // Unexpected node type.
                Debug.assertTrue(false);
            }

            return newRun;
        }
    }

    private static boolean isStrongRtlOrWhiteSpaces(String text)
    {
        boolean hasRTLChar = false;

        for (int i = 0; i < text.length(); i++)
        {
        	char c = text.charAt(i);
            /*BidiCharacterType*/int characterType = UnicodeCharacterDataResolver.getBidiCharacterType(c);
            boolean isStrongRtlCharacter = (characterType == BidiCharacterType.R) || (characterType == BidiCharacterType.AL);
            hasRTLChar |= isStrongRtlCharacter;

            if (isStrongRtlCharacter)
                continue;

            if (msChar.isWhiteSpace(c))
                continue;

            return false;
        }

        // WORDSNET-16031 Set "BiDi" attribute forcibly, when text of the run contains only RTL chars or
        // RTL chars with spaces.
        return hasRTLChar;
    }

    /// <summary>
    /// Inserts a special char after given node.
    /// </summary>
    private Node insertSpecialChar(Node node, char ch)
    {
        switch (ch)
        {
            case FindReplaceIndexer.PARAGRAPH_BREAK_CHAR:
                return insertParagraphBreak(node);

            case FindReplaceIndexer.SECTION_BREAK_CHAR:
                return insertSectionBreak(node);

            default:
                // Unexpected special character.
                Debug.assertTrue(false);
                break;
        }

        return node;
    }

    /// <summary>
    /// Inserts paragraph break after given node.
    /// </summary>
    private Node insertParagraphBreak(Node nodeBefore)
    {
        if (!canInsertParagraphBreak(nodeBefore))
            return nodeBefore;

        Paragraph newPara = new Paragraph(mDoc, AttrCollectionExtension.deepClone(mParaPr), AttrCollectionExtension.deepClone(mRunPr));

        switch (nodeBefore.getNodeLevel())
        {
            case NodeLevel.INLINE:
            {
                Paragraph paraAfter = ((Run)nodeBefore).getParentParagraph();
                paraAfter.insertPrevious(newPara);
                SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(mDoc);
                try /*JAVA: was using*/
                {
                    newPara.insertBefore(paraAfter.getFirstChild(), nodeBefore.getNextSibling(), null);
                }
                finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }

                break;
            }

            case NodeLevel.BLOCK:
            {
                Paragraph paraBefore = (Paragraph)nodeBefore;
                paraBefore.insertNext(newPara);

                break;
            }

            default:
                // Unexpected node level.
                Debug.assertTrue(false);
                return nodeBefore;
        }

        return newPara;
    }

    /// <summary>
    /// Expand the SmartTag into the parent paragraph.
    /// </summary>
    /// <remarks>
    /// Once expanded, the all contents of the SmartTag will be moved to the parent paragraph,
    /// and the SmartTag will be removed. Supports nesting.
    /// </remarks>
    private static void expandSmartTagToPara(Node childSmartTag)
    {
        while (childSmartTag.getParentNode().getNodeType() != NodeType.PARAGRAPH)
        {
            CompositeNode smartTag = childSmartTag.getParentNode();
            CompositeNode parentSmartTag = smartTag.getParentNode();

            parentSmartTag.insertBefore(smartTag.getFirstChild(), null, smartTag.getNextSibling());
            smartTag.remove();
        }
    }

    /// <summary>
    /// Inserts section break before given node.
    /// </summary>
    private Node insertSectionBreak(Node node)
    {
        if (!canInsertSectionBreak(node))
            return node;

        Section newSect = new Section(mDoc);
        newSect.appendChild(new Body(mDoc));

        Node newPara = insertParagraphBreak(node);
        // Check new paragraph has been actually inserted.
        if (newPara == node)
            return node;

        Section curSect = (Section)node.getAncestor(NodeType.SECTION);

        // WORDSNET-22353 Make Word-like section break insertion.
        newSect.setSectPr(AttrCollectionExtension.deepClone(curSect.getSectPr()));
        while (curSect.getHeadersFooters().getCount() > 0)
            newSect.appendChild(curSect.getHeadersFooters().get(0));

        // If we are at the very end of section, then just append a new section.
        if (newPara.isLastChild())
        {
            curSect.insertNext(newSect);
            newSect.getBody().appendChild(newPara);
            return newPara;
        }

        // Otherwise, we should leave the rest of the section content (after the
        // new inserted paragraph break) inside the current old section.
        curSect.insertPrevious(newSect);
        newSect.getBody().insertBefore(curSect.getBody().getFirstChild(), newPara.getNextSibling(), null);
        return ((Paragraph)newPara).getCount() == 0 ? newPara : ((Paragraph)newPara).getLastChild();
    }

    /// <summary>
    /// Returns true if paragraph break can be inserted before or after the node.
    /// </summary>
    /// <remarks>
    /// AM. So far only inline SDTs are prevented to insert paragraph break into.
    /// </remarks>
    private static boolean canInsertParagraphBreak(Node node)
    {
        Node parentNode = node.getParentNode();

        while (parentNode != null)
        {
            switch (parentNode.getNodeType())
            {
                case NodeType.STRUCTURED_DOCUMENT_TAG:
                    StructuredDocumentTag sdt = (StructuredDocumentTag)parentNode;
                    if (sdt.getLevel() == MarkupLevel.INLINE)
                        return false;
                    parentNode = parentNode.getParentNode();
                    break;

                default:
                    parentNode = parentNode.getParentNode();
                    break;
            }
        }

        return true;
    }

    /// <summary>
    /// Returns true if section break can be inserted before the node.
    /// </summary>
    private static boolean canInsertSectionBreak(Node before)
    {
        Node parentNode = before.getParentNode();

        while (parentNode != null)
        {
            switch (parentNode.getNodeType())
            {
                case NodeType.CELL:
                case NodeType.HEADER_FOOTER:
                case NodeType.SHAPE:
                case NodeType.GROUP_SHAPE:
                case NodeType.COMMENT:
                case NodeType.STRUCTURED_DOCUMENT_TAG:
                    return false;

                default:
                    parentNode = parentNode.getParentNode();
                    break;
            }
        }

        return true;
    }

    /// <summary>
    /// Processes text deletion while breaks it at special chars.
    /// </summary>
    private void splitAndDelete(int start, String text) throws Exception
    {
        StringBuilder chunk = new StringBuilder(text.length());

        int chunkStart = start;

        for (int i = 0; i < text.length(); i++)
        {
            char ch = text.charAt(i);

            if (isSpecialChar(ch))
            {
                if (chunk.length() > 0)
                {
                    deleteText(chunkStart, chunk.length());
                }

                deleteSpecialChar(chunkStart + chunk.length(), ch);
                chunkStart += chunk.length() + 1;
                chunk.setLength(0);
            }
            else
            {
                chunk.append(ch);
            }
        }

        if (chunk.length() > 0)
        {
            deleteText(chunkStart, chunk.length());
        }
    }

    /// <summary>
    /// Deletes text portion from given position.
    /// </summary>
    private void deleteText(int start, int len) throws Exception
    {
        // The collection of bound StructuredDocumentTags, whose content will be deleted.
        HashSetGeneric<StructuredDocumentTag> boundSdts = new HashSetGeneric<StructuredDocumentTag>();

        int remains = len;
        while (remains > 0)
        {
            Run run = (Run)mIndexer.getNodeByPosition(start);

            // WORDSNET-24345 Collect affected parent bound StructuredDocumentTags to update them accordingly.
            if (mOptions.getIgnoreStructuredDocumentTags())
            {
                StructuredDocumentTag sdt = (StructuredDocumentTag)run.getAncestor(NodeType.STRUCTURED_DOCUMENT_TAG);
                if ((sdt != null) && sdt.getXmlMapping().isMapped())
                    boundSdts.add(sdt);
            }

            if (run.getTextLength() <= remains)
            {
                remains -= run.getTextLength();
                start += run.getTextLength();
                run.setText("");

                SmartTag parentSmartTag = ms.as(run.getParentNode(), SmartTag.class);
                run.remove();

                if (parentSmartTag != null && parentSmartTag.getCount() == 0)
                    parentSmartTag.remove();
            }
            else
            {
                Run newRun;

                if (run.getParentNode() instanceof SmartTag)
                    expandSmartTagToPara(run);

                SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(mDoc);
                try /*JAVA: was using*/
                {
                    newRun = run.splitBefore(remains);
                }
                finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }

                // Update modified entry without rebuilding whole index.
                // This greatly increases performance and helps to isolate from callback changes.
                mIndexer.replace(start, start, newRun);
                mIndexer.add(start + remains, run);

                newRun.remove();
                remains = 0;
            }
        }

        // WORDSNET-24345 Update bound StructuredDocumentTags by its remained content.
        for (StructuredDocumentTag boundSdt : boundSdts)
        {
            String content = boundSdt.getText();

            if (content.length() == 0)
                boundSdt.remove();
            else
                boundSdt.getXmlMapping().setValue(content);
        }
    }

    /// <summary>
    /// Deletes one of special chars.
    /// </summary>
    private void deleteSpecialChar(int start, char ch)
    {
        Node node = mIndexer.getNodeByPosition(start);

        switch (ch)
        {
            case FindReplaceIndexer.PARAGRAPH_BREAK_CHAR:
                deleteParagraphBreak(node);
                break;

            case FindReplaceIndexer.SECTION_BREAK_CHAR:
                deleteSectionBreak(node);
                break;

            case FindReplaceIndexer.CELL_BREAK_CHAR:
                // Do nothing.
                break;

            case FindReplaceIndexer.SHAPE_CHAR:
                // Do nothing.
                break;

            default:
                // Unexpected special character.
                Debug.assertTrue(false);
                break;
        }
    }

    /// <summary>
    /// Deletes paragraph break.
    /// </summary>
    private void deleteParagraphBreak(Node node)
    {
        // We need to get next paragraph and move all child
        Paragraph current = (Paragraph)node;

        // Just mark the paragraph as deleted if tracking is on.
        if (mDoc.isTrackRevisionsEnabled())
        {
            // If there is a table after the deleting paragraph, when accepting revisions, MS Word places
            // not deleted text of the paragraph into the table. So, when revision tracking is enabled, the
            // mOptions.SmartParagraphBreakReplacement mode is implicitly used.
            EditSession editSession = mDoc.fetchDocumentOrGlossaryMain().getEditSession();
            RevisionTrackingUtil.addDeleteRevision(current, editSession);
            return;
        }

        // WORDSNET-21329 We introduced a new option that allows to move child nodes into the NextPreOrder paragraph.
        Paragraph nextParagraph = (mOptions.getSmartParagraphBreakReplacement())
            ? (Paragraph)current.nextPreOrderOfType(current.getDocument(), NodeType.PARAGRAPH)
            : ms.as(node.getNextNonAnnotationSibling(), Paragraph.class);

        // See Test18. Word also leaves this paragraph break but reports that replace operation is successful.
        if (nextParagraph == null)
            return;

        // Move annotations between paragraphs into the second paragraph.
        Node nextNonAnnotation = node.getNextNonAnnotationSibling();
        if (current.getNextSibling() != nextNonAnnotation)
            nextParagraph.insertBefore(current.getNextSibling(), nextNonAnnotation.getPreviousSibling(), nextParagraph.getFirstChild());

        // Move child nodes from the current paragraph to the next one.
        nextParagraph.insertBefore(current.getFirstChild(), null, nextParagraph.getFirstChild());

        current.remove();
    }

    /// <summary>
    /// Deletes section break.
    /// </summary>
    private void deleteSectionBreak(Node node)
    {
        Paragraph sectBreak = (Paragraph)node;

        Section curSect = (Section)sectBreak.getAncestor(NodeType.SECTION);
        Section nextSect = (Section)curSect.getNextSibling();

        nextSect.getBody().insertBefore(curSect.getBody().getFirstChild(), null, nextSect.getBody().getFirstChild());
        curSect.remove();

        // Delete section break paragraph.
        deleteParagraphBreak(sectBreak);
    }

    private static boolean isSpecialChar(char ch)
    {
        switch (ch)
        {
            case FindReplaceIndexer.PARAGRAPH_BREAK_CHAR:
            case FindReplaceIndexer.SECTION_BREAK_CHAR:
            case FindReplaceIndexer.CELL_BREAK_CHAR:
            case FindReplaceIndexer.SHAPE_CHAR:
                return true;

            default:
                return false;
        }
    }

    /// <summary>
    /// Creates a regex pattern for a simple text search string.
    /// </summary>
    private static Regex textToRegex(String what, FindReplaceOptions options)
    {
        String pattern = what;

        pattern = Regex.escape(pattern);

        // WORDSNET-22269 Consider curly and double quotes as equal characters to mimic MS Word.
        pattern = pattern.replace("\"", "[\"“”]");

        /*RegexOptions*/int regexOptions = (options.getMatchCase()) ? RegexOptions.NONE : RegexOptions.IGNORE_CASE;
        return new Regex(pattern, regexOptions);
    }

    private static String replaceMetaCharacters(String text)
    {
        boolean amp = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < text.length(); i++)
        {
        	char c = text.charAt(i);
            switch (c)
            {
                case '&':
                    if (amp)
                        sb.append('&');
                    amp = !amp;
                    break;

                case 'b':
                case 'p':
                case 'm':
                case 'l':
                    sb.append(amp ? getMetaChar(c) : c);
                    amp = false;
                    break;

                default:
                    if (amp)
                        sb.append('&');
                    sb.append(c);
                    amp = false;
                    break;
            }
        }

        // Add trailing ampersand.
        if (amp)
            sb.append('&');

        return sb.toString();
    }

    /// <summary>
    /// Returns meta character replacement.
    /// </summary>
    private static char getMetaChar(char c)
    {
        switch (c)
        {
            case 'b':
                return FindReplaceIndexer.SECTION_BREAK_CHAR;
            case 'p':
                return FindReplaceIndexer.PARAGRAPH_BREAK_CHAR;
            case 'm':
                return ControlChar.PAGE_BREAK_CHAR;
            case 'l':
                return ControlChar.LINE_BREAK_CHAR;
            case '&':
                return '&'; // & is meta for itself
            default:
                return c;
        }
    }

    /// <summary>
    /// Represents a comparer to sort headers/footers in order of their visual representation in a document.
    /// </summary>
    private static class HeaderFooterComparer implements Comparator<Node>
    {
        /// <summary>
        /// Ctor with an option defined whether separate first header and footer are present.
        /// </summary>
        HeaderFooterComparer(boolean differentFirstPageHeaderFooter)
        {
            mDifferentFirstPageHeaderFooter = differentFirstPageHeaderFooter;
        }

        /// <summary>
        /// Compares two headers/footers.
        /// </summary>
        public int compare(Node x, Node y)
        {
            HeaderFooter headerFooterX = ms.as(x, HeaderFooter.class);
            HeaderFooter headerFooterY = ms.as(y, HeaderFooter.class);
            if ((headerFooterX == null) || (headerFooterY == null))
                throw new IllegalStateException("Wrong object type.");

            return getOrder(headerFooterX) - getOrder(headerFooterY);
        }

        /// <summary>
        /// Gets order index for the specified header/footer.
        /// </summary>
        private int getOrder(HeaderFooter headerFooter)
        {
            switch (headerFooter.getHeaderFooterType())
            {
                case HeaderFooterType.HEADER_FIRST:
                    return 0;
                case HeaderFooterType.FOOTER_FIRST:
                    return 1;
                case HeaderFooterType.HEADER_EVEN:
                    return 2;
                case HeaderFooterType.FOOTER_EVEN:
                    return 3;
                case HeaderFooterType.HEADER_PRIMARY:
                    return mDifferentFirstPageHeaderFooter ? 4 : 0;
                case HeaderFooterType.FOOTER_PRIMARY:
                    return mDifferentFirstPageHeaderFooter ? 5 : 1;
                default:
                    throw new IllegalStateException("Unknown type of the header or footer.");
            }
        }

        private /*final*/ boolean mDifferentFirstPageHeaderFooter;
    }

    /// <summary>
    /// Gets a replacing callback method.
    /// </summary>
    private IReplacingCallback getReplacingCallback() { return (mOptions != null) ? mOptions.getReplacingCallback() : null; }

    /// <summary>
    /// Gets a boolean value indicating either to move forward.
    /// </summary>
    private boolean isForward() { return ((mOptions == null) || (mOptions.getDirection() == FindReplaceDirection.FORWARD)); }

    /// <summary>
    /// Gets a boolean value indicating either to find a whole words only.
    /// </summary>
    private boolean isFindWholeWordsOnly() { return ((mOptions != null) && (mOptions.getFindWholeWordsOnly())); }

    /// <summary>
    /// Gets a boolean value indicating either to recognize and use substitutions within replacement patterns.
    /// </summary>
    private boolean isUseSubstitutions() { return ((mOptions != null) && (mOptions.getUseSubstitutions())); }

    /// <summary>
    /// Gets a boolean value indicating the regex option Multiline is enabled.
    /// </summary>
    private boolean isMultiline() { return (((mPattern != null) && (mPattern.getOptions() & RegexOptions.MULTILINE) != 0)); }

    private FindReplaceIndexer mIndexer;

    private /*final*/ DocumentBase mDoc;
    private /*final*/ Node mRootNode;
    private /*final*/ NodeRange mNodeRange;
    private /*final*/ String mReplaceWith;
    private /*final*/ Regex mPattern;

    private /*final*/ FindReplaceOptions mOptions;
    private static final String WORD_BOUNDARY_SYMBOLS = "`~!@#$%^&*()-=+[{]}\\|;:'\",.<>/?\u000b";
    private static /*final*/ char[] G_ANCHORS = new char[] { '^', '$' };

    static boolean LogSwitch = false;

    /// <summary>
    /// The reference formatting to apply to the inserted content.
    /// </summary>
    private /*final*/ RunPr mRunPr = new RunPr();

    /// <summary>
    /// The reference formatting to apply to the inserted content.
    /// </summary>
    private /*final*/ ParaPr mParaPr = new ParaPr();
}

