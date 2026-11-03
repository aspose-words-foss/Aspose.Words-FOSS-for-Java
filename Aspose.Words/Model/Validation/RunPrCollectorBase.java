// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/11/2009 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// This object finds all <see cref="RunPr"/> objects in a document (including in all types of runs, styles, lists,
/// defaults etc) and sends them to the template method so the derived class can handle them in a certain way.
/// </summary>
abstract class RunPrCollectorBase extends DocumentVisitor
{
    public /*override*/ /*VisitorAction*/int visitDocumentEnd(Document doc)
    {
        collectFromDocument(doc);
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitGlossaryDocumentEnd(GlossaryDocument glossary)
    {
        collectFromDocument(glossary);
        return VisitorAction.CONTINUE;
    }

    private void collectFromDocument(DocumentBase doc)
    {
        collectFromStyles(doc.getStyles());
        collectFromLists(doc.getLists());
    }

    private void collectFromStyles(StyleCollection styles)
    {
        doHandleRunPr(styles.getDefaultRunPr());

        for (Style style : styles)
        {
            doHandleRunPr(style.getRunPr());

            switch (style.getType())
            {
                case StyleType.TABLE:
                    collectFromTableStyle((TableStyle)style);
                    break;
                default:
                    // Do nothing. Already collected all runprs.
                    break;
            }
        }
    }

    private void collectFromTableStyle(TableStyle style)
    {
        for (ConditionalStyle conditionalStyle : style.getConditionalStyles().getDefinedStyles())
            doHandleRunPr(conditionalStyle.getRunPr());
    }

    private void collectFromLists(ListCollection lists)
    {
        for (int i = 0; i < lists.getListDefCount(); i++)
        {
            ListDef listDef = lists.getListDefByIndex(i);
            for (ListLevel level : listDef.getLevels())
                doHandleRunPr(level.getRunPr());
        }

        for (List list : lists)
        {
            for (ListLevelOverride levelOverride : list.getOverrides())
            {
                if (levelOverride.IsFormatting)
                    doHandleRunPr(levelOverride.getListLevel().getRunPr());
            }
        }
    }

    public /*override*/ /*VisitorAction*/int visitParagraphStart(Paragraph paragraph)
    {
        doHandleRunPr(paragraph.getParagraphBreakRunPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitCommentStart(Comment comment)
    {
        doHandleRunPr(comment.getRunPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFootnoteStart(Footnote footnote)
    {
        doHandleRunPr(footnote.getRunPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitShapeStart(Shape shape)
    {
        doHandleRunPr(shape.getRunPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitGroupShapeStart(GroupShape groupShape)
    {
        doHandleRunPr(groupShape.getRunPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFormField(FormField formField)
    {
        doHandleRunPr(formField.getRunPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitRun(Run run)
    {
        doHandleRunPr(run.getRunPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldStart(FieldStart fieldStart)
    {
        doHandleRunPr(fieldStart.getRunPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldSeparator(FieldSeparator fieldSeparator)
    {
        doHandleRunPr(fieldSeparator.getRunPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldEnd(FieldEnd fieldEnd)
    {
        doHandleRunPr(fieldEnd.getRunPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitSpecialChar(SpecialChar specialChar)
    {
        doHandleRunPr(specialChar.getRunPr());
        return VisitorAction.CONTINUE;
    }

    protected abstract void doHandleRunPr(RunPr runPr);
}

