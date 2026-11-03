// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/09/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msObject;


/// <summary>
/// Represents a container for the footnote/endnote separator and continuation content of a document.
/// </summary>
/// <remarks>
/// <p><see cref="FootnoteSeparator"/> can contain <see cref="Paragraph"/> and <see cref="Tables.Table">Table</see> child nodes.</p>
///
/// <p>There can only be one <see cref="FootnoteSeparator"/> of each <see cref="FootnoteSeparatorType"/> in a document.</p>
/// </remarks>
public class FootnoteSeparator extends Story
{
    /// <summary>
    /// Creates a new footnote/endnote separator of the specified type.
    /// </summary>
    FootnoteSeparator(DocumentBase doc, /*FootnoteSeparatorType*/int separatorType)
    {
    	super(doc, WordUtil.footnoteSeparatorTypeToStoryType(separatorType));
	
        mSeparatorType = separatorType;
    }

    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return acceptChildren(visitor);
    }

    public /*override*/ /*VisitorAction*/int acceptStart(DocumentVisitor visitor)
    {
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int acceptEnd(DocumentVisitor visitor)
    {
        return VisitorAction.CONTINUE;
    }

    /*override*/ boolean canInsert(Node newChild)
    {
        return true;
    }

    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.SYSTEM; }

    public /*FootnoteSeparatorType*/int getSeparatorType() { return mSeparatorType; };

    private /*FootnoteSeparatorType*/int mSeparatorType;

    /// <summary>
    /// Indicates that separator has default formatting.
    /// </summary>
    /// <remarks>
    /// Main purpose of this property is to reduce number of golds affected.
    /// </remarks>
    boolean isDefault()
    {
        // Default footnote/endnote separator seems to be single paragraph.
        if (getChildNodes(NodeType.ANY, false).getCount() > 1)
            return false;

        Paragraph para = ms.as(getChildNodes(NodeType.ANY, false).get(0), Paragraph.class);
        if (para == null)
            return false;

        ParaPr paraPr = AttrCollectionExtension.deepClone(para.getParaPr());
        // WORDSNET-24128 Use final formatting.
        paraPr.acceptFormatRevision();

        // Remove inherited attributes.
        for (int i = 0; i < paraPr.getCount(); /* */)
        {
            int key = paraPr.getKey(i);

            // WORDSNET-29083 Ignore number revision attr since it cannot be inherited and not accepted above.
            // AM. This is quick fix, I think whole method should be simplified somehow.
            if (key == RevisionAttr.NUMBER_REVISION)
            {
                i++;
                continue;
            }

            Object inheritedValue = ((IParaAttrSource)para).fetchInheritedParaAttr(key);
            Object thisValue = paraPr.get(key);

            if (msObject.equals(thisValue, inheritedValue))
                paraPr.removeAt(i);
            else
                i++;
        }

        ParaPr testPr = new ParaPr();
        testPr.setAttr(ParaAttr.ISTD, 0);
        testPr.setAttr(ParaAttr.SPACE_AFTER, 0);
        testPr.setAttr(ParaAttr.LINE_SPACING, new LineSpacing(240, LineSpacingRule.MULTIPLE));

        // Ignore system and rsid attributes.
        paraPr.removeEquals(testPr);
        paraPr.remove(ParaAttr.RSID_P);
        paraPr.remove(ParaAttr.SYS_ALIGNMENT_97);
        paraPr.remove(ParaAttr.SYS_LEFT_INDENT_97);
        paraPr.remove(ParaAttr.SYS_RIGHT_INDENT_97);

        if (paraPr.getCount() > 0)
            return false;

        return true;
    }

    /// <summary>
    /// Returns true, if this <see cref="FootnoteSeparator"/> equals to a specified one.
    /// </summary>
    boolean equals(FootnoteSeparator other)
    {
        if (other == null)
            return false;

        if (getSeparatorType() != other.getSeparatorType())
            return false;

        NodeCollection paragraphs = getChildNodes(NodeType.PARAGRAPH, false);
        NodeCollection otherParagraphs = other.getChildNodes(NodeType.PARAGRAPH, false);

        if (paragraphs.getCount() != otherParagraphs.getCount())
            return false;

        for (int i = 0; i < paragraphs.getCount(); i++)
        {
            Paragraph paragraph = (Paragraph)paragraphs.get(i);
            Paragraph otherParagraph = (Paragraph)otherParagraphs.get(i);

            if (!paragraph.getParaPr().equals(otherParagraph.getParaPr(), G_PARA_PR_IGNORABLE_KEYS))
                return false;

            if (!paragraph.getParagraphBreakRunPr().equals(otherParagraph.getParagraphBreakRunPr(), G_RUN_PR_IGNORABLE_KEYS))
                return false;
        }

        return true;
    }

    /// <summary>
    /// These keys will be ignored in properties comparison.
    /// </summary>
    private static /*final*/ int[] G_PARA_PR_IGNORABLE_KEYS = new int[]
    {
        ParaAttr.RSID_P, ParaAttr.SYS_ALIGNMENT_97, ParaAttr.SYS_LEFT_INDENT_97, ParaAttr.SYS_RIGHT_INDENT_97,
        ParaAttr.SYS_FIRST_LINE_INDENT_97
    };

    /// <summary>
    /// These keys will be ignored in properties comparison.
    /// </summary>
    private static /*final*/ int[] G_RUN_PR_IGNORABLE_KEYS = new int[]
    {
        FontAttr.RSID_R_PR, FontAttr.RSID_R, ParaAttr.RSID_P
    };
}

