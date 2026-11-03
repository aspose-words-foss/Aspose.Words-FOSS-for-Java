// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/03/2016 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.ArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Represents a group of sequential <see cref="Aspose.Words.Revision" /> objects.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/track-changes-in-a-document/">Track Changes in a Document</a> documentation article.</para>
/// </summary>
public class RevisionGroup
{
    RevisionGroup(ArrayList<Node> nodes, EditRevision revision)
    {
        mRevision = new EditRevision(revision.getType(), revision.getAuthor(), revision.getDateTime());
        mNodes = nodes;
    }

    RevisionGroup(ArrayList<Node> nodes, FormatRevision revision)
    {
        mRevision = new FormatRevision(AttrCollectionExtension.deepClone(revision.getRevPr()), revision.getAuthor(), revision.getDateTime());
        mNodes = nodes;
    }

    RevisionGroup(Style style)
    {
        // Look for any format revision in style.
        FormatRevision revision = style.getRunPr().hasRevisions() ? style.getRunPr().getFormatRevision() : style.getParaPr().getFormatRevision();
        Debug.assertTrue(revision != null);

        // Use mRevision to hold revision details.
        mRevision = new FormatRevision(new RunPr(), revision.getAuthor(), revision.getDateTime());

        mStyle = style;
        mNodes = new ArrayList<Node>(); { mNodes.add(style.getDocument()); }
    }

    /// <summary>
    /// Ctor for a move revision.
    /// </summary>
    RevisionGroup(ArrayList<Node> nodes, MoveRevision revision, String moveRangeName)
    {
        mRevision = new MoveRevision(revision.getType(), revision.getAuthor(), revision.getDateTime());
        mNodes = nodes;
        mMoveRangeName = moveRangeName;
    }

    /// <summary>
    /// Returns inserted/deleted/moved text or description of format change.
    /// </summary>
    public String getText()
    {
        // Table formatted
        if ((getNodes().size() == 1) && getNodes().get(0).getNodeType() == NodeType.TABLE)
            return "Table";

        if (mStyle != null)
        {
            String runText = mStyle.getRunPr().hasRevisions()
                ? FormatRevisionText.getText(mStyle.getRunPr().getFormatRevision(), mStyle.getDocument())
                : "";

            String paraText = mStyle.getParaPr().hasRevisions()
                ? FormatRevisionText.getText(mStyle.getParaPr().getFormatRevision(), mStyle.getDocument())
                : "";

            if (mStyle.getType() == StyleType.PARAGRAPH)
                return msString.format("{0}: {1}{2}{3}",
                    mStyle.getName(), runText, msString.isNullOrEmpty(paraText) ? "" : ", ", paraText);

            if (mStyle.getType() == StyleType.CHARACTER)
                return msString.format("{0}: {1}",
                    mStyle.getName(), runText);

            // Return empty text for other style types for a while.
            return "";
        }

        return getFormatRevision() != null ? FormatRevisionText.getText(getFormatRevision(), getStart().getDocument()) : getVisibleText();
    }

    /// <summary>
    /// Gets the author of this revision group.
    /// </summary>
    public String getAuthor() { return mRevision.getAuthor(); }

    /// <summary>
    /// Gets the type of revisions included in this group.
    /// </summary>
    public /*RevisionType*/int getRevisionType()
    {
        if (mStyle != null)
            return RevisionType.STYLE_DEFINITION_CHANGE;

        if (mRevision instanceof EditRevision)
        {
            switch (((EditRevision)mRevision).getType())
            {
                case EditRevisionType.INSERTION:
                    return RevisionType.INSERTION;
                case EditRevisionType.DELETION:
                    return RevisionType.DELETION;
                default:
                    Debug.assertTrue(false, "Unknown editing revision type.");
                    return RevisionType.INSERTION;
            }
        }

        if (mRevision instanceof FormatRevision)
        {
            return RevisionType.FORMAT_CHANGE;
        }

        if (mRevision instanceof MoveRevision)
        {
            return RevisionType.MOVING;
        }

        {
            Debug.assertTrue(false, "Unknown revision type.");
            return RevisionType.FORMAT_CHANGE;
        }
    }

    /// <summary>
    /// First node of the revision group.
    /// </summary>
    Node getStart() { return mNodes.get(0); }

    /// <summary>
    /// Collection of visible nodes.
    /// </summary>
    /// <remarks>
    /// In some cases Word doesn't show all nodes in revision group.
    /// </remarks>
    ArrayList<Node> getVisibleNodes()
    {
        if (mVisibleNodes == null)
        {
            mVisibleNodes = new ArrayList<Node>();

            for(Node node : mNodes)
            {
                if (node.getNodeType() == NodeType.PARAGRAPH)
                {
                    Paragraph p = (Paragraph)node;

                    // Special handling for tables. Word stops collecting nodes at end of first cell.
                    if (p.isEndOfCell())
                    {
                        // If cell is empty collect cell break node.
                        if (mNodes.get(0) == node)
                            msArrayList.add(mVisibleNodes, node);
                        break;
                    }
                }

                // WORDSNET-16209 Skip SDTs.
                if (node.getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG)
                    msArrayList.add(mVisibleNodes, node);
            }
        }

        return mVisibleNodes;
    }

    String getVisibleText()
    {
        StringBuilder sb = new StringBuilder();
        for (Node node : getVisibleNodes())
            msStringBuilder.append(sb, node.isComposite() ? ((CompositeNode)node).getEndText() : node.getText());

        return sb.toString();
    }

    EditRevision getEditRevision() { return ms.as(mRevision, EditRevision.class); }

    /// <summary>
    /// Returns the move revision if this object represents a move group.
    /// </summary>
    MoveRevision getMoveRevision() { return ms.as(mRevision, MoveRevision.class); }

    FormatRevision getFormatRevision() { return ms.as(mRevision, FormatRevision.class); }

    RevisionBase getRevision() { return mRevision; }

    ArrayList<Node> getNodes() { return mNodes; }

    /// <summary>
    /// Gets name of a move range if this object is a move revision group.
    /// </summary>
    /// <remarks>
    /// The property is used to find a corresponding move-from or move-to group.
    /// </remarks>
    String getMoveRangeName() { return mMoveRangeName; };

    private String mMoveRangeName;

    boolean isFontNameRevision() { return (getFormatRevision() != null) && getFormatRevision().getRevPr().containsAnyKey(G_FONT_NAME_KEYS); }

    /// <summary>
    /// Returns an indication of which attribute classes attributes recorded (non-inherited) in format revision belong to.
    /// </summary>
    int getFormatRevisionAttributeAffiliations()
    {
        int result = 0;
        if (getFormatRevision() != null)
        {
            int[] keys = getFormatRevision().getRevPr().getKeys();
            for (int key : keys)
            {
                if (result == (HAS_TABLE_ATTR | HAS_CELL_ATTR | HAS_SECT_ATTR | HAS_PARA_ATTR | HAS_FONT_ATTR))
                    break;
                if (key < 1000)
                    result |= HAS_FONT_ATTR;
                else if (key < 2000)
                    result |= HAS_PARA_ATTR;
                else if (key < 3000)
                    result |= HAS_SECT_ATTR;
                else if (key < 4000)
                    result |= HAS_CELL_ATTR;
                else if (key < 5000)
                    result |= HAS_TABLE_ATTR;
            }
        }
        return result;
    }

    static final int HAS_FONT_ATTR = 1 << 0;
    private static final int HAS_PARA_ATTR = 1 << 1;
    private static final int HAS_SECT_ATTR = 1 << 2;
    private static final int HAS_CELL_ATTR = 1 << 3;
    private static final int HAS_TABLE_ATTR = 1 << 4;


    private /*final*/ Style mStyle;
    private /*final*/ RevisionBase mRevision;
    private /*final*/ ArrayList<Node> mNodes;
    private ArrayList<Node> mVisibleNodes;
    private static /*final*/ int[] G_FONT_NAME_KEYS = {FontAttr.NAME_ASCII, FontAttr.NAME_BI, FontAttr.NAME_FAR_EAST, FontAttr.NAME_OTHER };
}

