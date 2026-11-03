// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/04/2020 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Visits all attribute collections that may contain Istd.
/// </summary>
abstract class IstdVisitor extends DocumentVisitor
{
    /// <summary>
    /// Runs visiting all attribute collections to process style Istds.
    /// </summary>
    protected void run(DocumentBase doc) throws Exception
    {
        onRunPr(doc.getStyles().getDefaultRunPr());
        onParaPr(doc.getStyles().getDefaultParaPr());

        doc.accept(this);

        visitFootnoteSeparators(doc);
        onDocPr(doc.getDocPr());
    }

    /// <summary>
    /// Visits run properties.
    /// </summary>
    protected abstract void onRunPr(RunPr runPr);

    /// <summary>
    /// Visits paragraph properties.
    /// </summary>
    protected abstract void onParaPr(ParaPr paraPr);

    /// <summary>
    /// Visits row properties.
    /// </summary>
    protected abstract void onRowPr(TablePr cellPr);

    /// <summary>
    /// Visits document properties.
    /// </summary>
    protected abstract void onDocPr(DocPr docPr);

    public /*override*/ /*VisitorAction*/int visitParagraphStart(Paragraph paragraph)
    {
        onParaPr(paragraph.getParaPr());
        onRunPr(paragraph.getParagraphBreakRunPr());

        if (paragraph.getParaPr().hasFormatRevision())
            onParaPr((ParaPr)paragraph.getParaPr().getFormatRevision().getRevPr());

        if (paragraph.getParagraphBreakRunPr().hasFormatRevision())
            onRunPr((RunPr)paragraph.getParagraphBreakRunPr().getFormatRevision().getRevPr());

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitRun(Run run)
    {
        onRunPr(run.getRunPr());

        if (run.getRunPr().hasFormatRevision())
            onRunPr((RunPr)run.getRunPr().getFormatRevision().getRevPr());

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitRowStart(Row row)
    {
        onRowPr(row.getTablePr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitSpecialChar(SpecialChar specialChar)
    {
        // WORDSNET-15076 Collect styles from special character nodes to avoid loss of styles.
        onRunPr(specialChar.getRunPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFootnoteStart(Footnote footnote)
    {
        onRunPr(footnote.getRunPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldStart(FieldStart fieldStart)
    {
        onRunPr(fieldStart.getRunPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldSeparator(FieldSeparator fieldSeparator)
    {
        onRunPr(fieldSeparator.getRunPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldEnd(FieldEnd fieldEnd)
    {
        onRunPr(fieldEnd.getRunPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFormField(FormField formField)
    {
        onRunPr(formField.getRunPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitStructuredDocumentTagStart(StructuredDocumentTag sdt)
    {
        onRunPr(sdt.getContentsRunPr());
        onRunPr(sdt.getEndCharacterRunPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitShapeStart(Shape shape)
    {
        onRunPr(shape.getRunPr());
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Visits contents of footnote separators.
    /// </summary>
    private void visitFootnoteSeparators(DocumentBase doc) throws Exception
    {
        for (FootnoteSeparator separator : doc.getFootnoteSeparators())
            separator.accept(this);
    }
}

