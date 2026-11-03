// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/12/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Implements an algorithm selecting a chapter title paragraph used to generate a chapter number
/// for the specified PAGE or PAGEREF field considering differences in behavior of MS Word 2013
/// and earlier versions.
/// </summary>
class ChapterTitleParagraphFinder extends FieldParagraphFinder
{
    /// <summary>
    /// Returns a chapter title paragraph used to generate a chapter number for the specified PAGE or PAGEREF field
    /// in the context of the given section.
    /// </summary>
    static Paragraph findChapterTitleParagraph(Field field, Section formatSection) throws Exception
    {
        // Ensure that the field is being updated.
        Debug.assertTrue(field.isUpdating());

        ChapterTitleParagraphFinder finder = new ChapterTitleParagraphFinder(field, formatSection);
        return finder.findChapterTitleParagraph();
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    private ChapterTitleParagraphFinder(Field field, Section formatSection)
    {
        mField = field;
        mFormatSection = formatSection;
    }

    /// <summary>
    /// Returns a chapter title paragraph used to generate a chapter number for the specified PAGE or PAGEREF field.
    /// </summary>
    private Paragraph findChapterTitleParagraph() throws Exception
    {
        Node startNode = getStartNode();
        if (startNode == null)
            return null;

        return (startNode.getAncestor(NodeType.HEADER_FOOTER) != null)
            ? findChapterTitleParagraphHeaderFooter()
            : findChapterTitleParagraphMainText(startNode);
    }

    /// <summary>
    /// Returns a node which is the starting point to seek for a chapter title paragraph according to the given field type.
    /// </summary>
    private Node getStartNode() throws Exception
    {
        switch (mField.getType())
        {
            case FieldType.FIELD_PAGE:
                return mField.getStart();
            case FieldType.FIELD_PAGE_REF:
                return ((FieldPageRef)mField).getChapterTitleNode();
            default:
                return null;
        }
    }

    /// <summary>
    /// Returns a chapter title paragraph for the given PAGE field located in header/footer.
    /// </summary>
    private Paragraph findChapterTitleParagraphHeaderFooter() throws Exception
    {
        // MS Word does not consider bookmarks in headers/footers while PAGEREF fields' updating.
        if (mField.getType() == FieldType.FIELD_PAGE_REF)
            return null;

        // MS Word 2013 and earlier versions use different algorithms of chapter title paragraph selecting
        // for headers/footers. Respect the difference.
        if (mField.fetchDocument().getCompatibilityOptions().getMswVersion() < MsWordVersionCore.WORD_2013)
            return findParagraphHeaderFooter(mField);

        Paragraph chapterTitleParagraph = null;

        // MS Word 2013 works here as follows.
        //
        // 1. Get the first paragraph in the parent section's body considering nested (i.e. inside tables, etc.)
        //    paragraphs. If it is a chapter title, return it.
        Body body = mFormatSection.getBody();
        if ((body != null) && body.hasChildNodes())
        {
            Node candidateNode = body.getChild(NodeType.PARAGRAPH, 0, true);
            chapterTitleParagraph = confirmParagraphNode(candidateNode);
        }

        // 2. If the first paragraph of the parent section's body is not a chapter title, seek for a chapter title
        //    paragraph backward starting from the parent section exclusively (i.e. before its first body paragraph).

        // 3. If nothing is found, seek for a chapter title paragraph forward starting from the parent section
        //    inclusively, since its body may contain chapter title paragraphs after its first paragraph.

        return ((chapterTitleParagraph != null ? chapterTitleParagraph : findParagraph(mFormatSection, false, false)) != null ? (chapterTitleParagraph != null ? chapterTitleParagraph : findParagraph(mFormatSection, false, false)) : findParagraph(mFormatSection, true, true));
    }

    /// <summary>
    /// Returns a chapter title paragraph for the given PAGE field located in body or PAGEREF field which bookmark
    /// is located in body.
    /// </summary>
    private Paragraph findChapterTitleParagraphMainText(Node startNode) throws Exception
    {
        // MS Word works here as follows.
        //
        // 1. Seek backward from the start node.
        // 2. If nothing is found, seek forward from the start node.
        return (findParagraph(startNode, false, true) != null ? findParagraph(startNode, false, true) : findParagraph(startNode, true, false));
    }

    /*override*/ Paragraph confirmParagraph(Paragraph candidateParagraph)
    {
        if (candidateParagraph == null)
            return null;

        return isChapterTitle(candidateParagraph) ? candidateParagraph : null;
    }

    /// <summary>
    /// Returns a value indicating whether the given paragraph can be treated as a chapter title in the context of
    /// the specified section.
    /// </summary>
    private boolean isChapterTitle(Paragraph paragraph)
    {
        // MS Word considers a paragraph to be a chapter title in the context of a concrete section,
        // if it satisfies the following conditions.
        //
        // 1. The paragraph and the section should belong to the same document.
        Node document = paragraph.getAncestor(NodeType.DOCUMENT);
        if ((document == null) || (document != mFormatSection.getAncestor(NodeType.DOCUMENT)))
            return false;

        // 2. The paragraph should be located directly in a body story. Even paragraphs in body textboxes
        //    are not considered.
        if (paragraph.getStoryAncestor(NodeType.BODY) == null)
            return false;

        // 3. The paragraph should have a heading style applied.
        int istd = paragraph.getParaPr().getIstd();
        if (!StyleIndex.isHeadingIstd(istd))
            return false;

        // 4. The paragraph's style should correspond to the section's chapter heading level.
        if (mFormatSection.getSectPr().getHeadingLevelForChapterFinal() != istd)
            return false;

        // 5. The paragraph should be a list item.
        if (!paragraph.isListItemFinal())
            return false;

        // 6. The paragraph's style should correspond to a style defined for the paragraph's list level.
        //    It seems to be a useless check but TestIndexAndTables.TestJira6554 fails without it.
        //    Use ListLabel.ListLevel to handle revisions properly.
        if (paragraph.getListLabel().getListLevelFinal().getParaStyleIstd() != istd)
            return false;

        // WORDSNET-11170
        // 7. List item should has numberstate. For example, in some cases numberstate can be null because paragraph contains only page break
        if (paragraph.getListLabel().getNumberStateFinal() == null)
            return false;

        // 8. The paragraph's list level number format should contain any level number placeholder.
        return numberFormatHasAnyLevelNumber(paragraph);
    }

    /// <summary>
    /// Returns a value indicating whether the number format of the given paragraph's list label list level contains
    /// any level number placeholder.
    /// </summary>
    private static boolean numberFormatHasAnyLevelNumber(Paragraph paragraph)
    {
        // Use ListLabel.ListLevel to handle revisions properly.
        String numberFormat = paragraph.getListLabel().getListLevelFinal().getNumberFormat();

        for (int i = 0; i < numberFormat.length(); i++)
        {
        	char c = numberFormat.charAt(i);
            if (ListLevel.isLevelNumberValid(c))
                return true;
        }

        return false;
    }

    /*override*/ boolean isForwardPageScan() { return false; }

    private /*final*/ Field mField;
    private /*final*/ Section mFormatSection;
}

