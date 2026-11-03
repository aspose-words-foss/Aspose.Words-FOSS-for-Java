// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/06/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.MathUtil;
import com.aspose.Debug;
import com.aspose.EnumUtilPal;


/// <summary>
/// At the moment this is a hack/experimental way to deal with table styles for exporters
/// that do not support table styles.
/// Say if we read from DOCX (where we parse table styles) and then save to DOC, HTML etc
/// where we don't support table styles, we call this to expand all table style formatting
/// and permanently modify formatting in the tables in the document.
/// </summary>
class TableFormattingExpander extends DocumentVisitor
{
    /// <summary>
    /// Restores direct attributes of nodes affected by table formatting expander.
    /// </summary>
    void restoreDirectAttrs()
    {
        for(Node n : mAffectedNodes)
        {
            switch (n.getNodeType())
            {
                case NodeType.ROW:
                    // WORDSNET-20118 Added Clone() here and below to get unique objects after the first expand/restore.
                    Row row = (Row)n;
                    TablePr savedRowAttributes = (TablePr)AttrCollectionExtension.deepClone(((Row)n).getTablePr().getSysDirectAttrs());
                    // Dmatv: as a side effect of restoring the attributes saved before validator,
                    // attributes set during table grid calculation are discarded.
                    // The code below works around the issue.

                    // Save direct auto-fit attributes, they may have been updated by auto-fit grid calculation in layout.
                    Object[] directAutoFitRowAttrs = saveDirectAutoFitCalculatedAttrs(row.getTablePr(), G_AUTO_FIT_GRID_CALCULATED_ROW_ATTRS);

                    row.setTablePr(savedRowAttributes);

                    // Overwrite attributes saved before layout update with the current values.
                    restoreDirectAutoFitCalculatedAttrs(row.getTablePr(), directAutoFitRowAttrs, G_AUTO_FIT_GRID_CALCULATED_ROW_ATTRS);

                    break;

                case NodeType.CELL:
                    Cell cell = (Cell) n;
                    // Dmatv: as a side effect of restoring the attributes saved before validator,
                    // attributes set during table grid calculation are discarded.
                    // The code below works around the issue.
                    CellPr savedAttributes = (CellPr)AttrCollectionExtension.deepClone(cell.getCellPr().getSysDirectAttrs());

                    // Save direct auto-fit attributes, they may have been updated by auto-fit grid calculation in layout.
                    Object[] directAutoFitAttrs = saveDirectAutoFitCalculatedAttrs(cell.getCellPr(), G_AUTO_FIT_GRID_CALCULATED_CELL_ATTRS);

                    cell.setCellPr(savedAttributes);

                    // Overwrite attributes saved before layout update with the current values.
                    restoreDirectAutoFitCalculatedAttrs(cell.getCellPr(), directAutoFitAttrs, G_AUTO_FIT_GRID_CALCULATED_CELL_ATTRS);

                    break;

                case NodeType.PARAGRAPH:
                    ((Paragraph)n).setParaPr((ParaPr)AttrCollectionExtension.deepClone(((Paragraph)n).getParaPr().getSysDirectAttrs()));
                    ((Paragraph)n).setParagraphBreakRunPr((RunPr)AttrCollectionExtension.deepClone(((Paragraph)n).getParagraphBreakRunPr().getSysDirectAttrs()));
                    break;

                case NodeType.RUN:
                    ((Run)n).setRunPr((RunPr)AttrCollectionExtension.deepClone(((Run)n).getRunPr().getSysDirectAttrs()));
                    break;

                default:
                    break;
            }
        }
        mAffectedNodes.clear();
    }

    /// <summary>
    /// Expands formatting in the specified table (but not in the nested tables).
    /// </summary>
    void expand(Table table) throws Exception
    {
        mTable = table;
        mTheme = mTable.getDocument().getThemeInternal();
        table.accept(this);
    }

    public /*override*/ /*VisitorAction*/int visitFieldStart(FieldStart fieldStart) throws Exception
    {
        if (fieldStart.getFieldType() == FieldType.FIELD_TOC)
            mIsInTocField = true;

        return super.visitFieldStart(fieldStart);
    }

    public /*override*/ /*VisitorAction*/int visitFieldEnd(FieldEnd fieldEnd) throws Exception
    {
        if (fieldEnd.getFieldType() == FieldType.FIELD_TOC)
            mIsInTocField = false;

        return super.visitFieldEnd(fieldEnd);
    }

    public /*override*/ /*VisitorAction*/int visitTableStart(Table table)
    {
        mHeadingRowCount = 1;
        mIsBodyRowOccurred = false;

        // It could be a nested table, skip it because we just want to expand one table at a time.
        if (table != mTable)
            return VisitorAction.SKIP_THIS_NODE;

        // Use the first row to get the table formatting, because in the current model, the
        // table formatting is said to be "stored" in the first row.
        Row row = table.getFirstRow();
        if (row == null)
            return VisitorAction.SKIP_THIS_NODE;

        // Expand table style properties that are for the whole table.
        // Use dynamic casting for safety because in invalid documents it might return a paragraph style for example.
        TableStyle tableStyle = ms.as(row.getDocument().getStyles().fetchByIstd(row.getTablePr().getIstd(), StyleIndex.TABLE_NORMAL), TableStyle.class);
        if (tableStyle == null)
            return VisitorAction.SKIP_THIS_NODE;

        mStyleTablePr = tableStyle.getExpandedTablePr();
        mStyleRowPr = tableStyle.getExpandedRowPr();
        mStyleCellPr = tableStyle.getExpandedCellPr();
        mStyleParaPr = tableStyle.getExpandedParaPr(ParaPrExpandFlags.NORMAL);
        mStyleRunPr = tableStyle.getExpandedRunPr(RunPrExpandFlags.NORMAL);

        // Collect types of defined conditional styles.
        ArrayList<Integer> definedOverrideTypes = new ArrayList<Integer>();
        TableStyle style = tableStyle;
        while (style != null)
        {
            for (ConditionalStyle conditionalStyle : style.getConditionalStyles().getDefinedStyles())
            {
                if (!definedOverrideTypes.contains(conditionalStyle.getOverrideType()))
                    msArrayList.add(definedOverrideTypes, conditionalStyle.getOverrideType());
            }

            style = ms.as(style.getBaseStyle(), TableStyle.class);
        }

        // Build a hashtable of conditional formatting so we can find needed table style prs fast.
        mConditionalStyles = new ConditionalStyle[G_TABLE_STYLE_OVERRIDE_TYPE_COUNT];
        for (/*TableStyleOverrideType*/int type : (Iterable</*TableStyleOverrideType*/Integer>) definedOverrideTypes)
            mConditionalStyles[(/*int*/Integer)type] = tableStyle.getExpandedConditionalStyle(type);

        mRowBandSize = mStyleTablePr.getStyleRowBandSize();
        mColBandSize = mStyleTablePr.getStyleColBandSize();
        mIsHeadingRow = row.isHeadingRow() && (getConditionalStyle(TableStyleOverrideType.FIRST_ROW) != null);

        calcPartsToApply(row.getTablePr().getStyleOptions());

        mRowIndex = 0;

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitRowStart(Row row) throws Exception
    {
        // Table can have more than one heading row but they must be in continuous range.
        mIsFirstRow = !mIsBodyRowOccurred && ((/*boolean*/Boolean)((IRowAttrSource)row).fetchRowAttr(TableAttr.HEADING_FORMAT) || row.isFirstRow());
        mIsLastRow = row.isLastRow();

        if (!isFirstRow())
            mIsBodyRowOccurred = true;

        if (mIsFirstRow)
            mHeadingRowCount = mRowIndex + 1;

        if (mIsApplyRowBand)
        {
            mIsFirstRowInBand = isFirstInBand(getDataRowIndex(), mRowBandSize);
            mIsLastRowInBand = isLastInBand(getDataRowIndex(), mRowBandSize);
        }

        mCellIndex = 0;
        mIsFirstCol = false;
        mIsLastCol = false;

        TablePr newRowPr = new TablePr();

        // Whole table formatting.
        mStyleTablePr.expandTo(newRowPr);
        mStyleRowPr.expandTo(newRowPr);

        // Conditional formatting.
        mRowPrExpander.expand(this, newRowPr);

        // It seems that this attribute is not inheritable.
        newRowPr.remove(TableAttr.HIDDEN);

        // Direct formatting.
        row.getTablePr().expandTo(newRowPr);

        newRowPr.setSysDirectAttrs(AttrCollectionExtension.deepClone(row.getTablePr()));
        msArrayList.add(mAffectedNodes, row);

        row.setTablePr(newRowPr);
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitRowEnd(Row row)
    {
        mRowIndex++;
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitCellStart(Cell cell) throws Exception
    {
        mIsFirstCol = cell.isFirstCell();
        mIsLastCol = cell.isLastCell();

        if (mIsApplyColBand)
        {
            mIsFirstColInBand = isFirstInBand(getDataColIndex(), mColBandSize);
            mIsLastColInBand = isLastInBand(getDataColIndex(), mColBandSize);
        }

        CellPr newCellPr = new CellPr();

        // Whole table formatting.
        mStyleCellPr.expandTo(newCellPr);

        // WORDSNET-28945 Override inherited cell borders by table borders.
        overrideInheritedCellBorders(cell, newCellPr);

        // Conditional formatting.
        mCellPrExpander.expand(this, newCellPr);

        // Direct formatting.
        cell.getCellPr().expandTo(newCellPr);

        newCellPr.setSysDirectAttrs(AttrCollectionExtension.deepClone(cell.getCellPr()));
        msArrayList.add(mAffectedNodes, cell);

        cell.setCellPr(newCellPr);
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitCellEnd(Cell cell)
    {
        mCellIndex++;
        return VisitorAction.CONTINUE;
    }

    private static boolean isFirstInBand(int index, int bandSize)
    {
        int bandIndex = index / bandSize;
        return ((bandIndex * bandSize) == index);
    }

    private static boolean isLastInBand(int index, int bandSize)
    {
        int bandIndex = index / bandSize;
        return (((bandIndex * bandSize + bandSize - 1) == index));
    }

    public /*override*/ /*VisitorAction*/int visitParagraphStart(Paragraph para) throws Exception
    {
        // I think paragraph formatting from the table style should apply only to the paragraphs
        // that are inside the cell and not to paragraphs that are inside comments or footnotes
        // that might occur in a table.
        if (para.getFirstNonMarkupParentNode().getNodeType() != NodeType.CELL)
            return VisitorAction.SKIP_THIS_NODE;

        expandParaPr(para);
        expandParagraphBreakRunPr(para);

        return VisitorAction.CONTINUE;
    }

    private void expandParaPr(Paragraph para) throws Exception
    {
        ParaPr newParaPr = new ParaPr();

        // Whole table formatting.
        mStyleParaPr.expandTo(newParaPr);

        // Conditional formatting.
        mParaPrExpander.expand(this, newParaPr);

        // Save direct attrs.
        para.getParaPr().setSysDirectAttrs(AttrCollectionExtension.deepClone(para.getParaPr()));
        msArrayList.add(mAffectedNodes, para);

        // This is the "normal" expansion procedure for a paragraph that is not in a table.
        para.expandParaPr(newParaPr, ParaPrExpandFlags.NORMAL);

        para.setParaPr(newParaPr);
    }

    /// <summary>
    /// RK It's a pity this code is almost a complete duplication of <see cref="ExpandRunPr"/> below,
    /// but at the moment it seems like too hard to refactor this while making it easier to read.
    /// </summary>
    private void expandParagraphBreakRunPr(Paragraph para) throws Exception
    {
        RunPr newRunPr = AttrCollectionExtension.deepClone(para.getDocument().getStyles().getDefaultRunPr());

        // Remove attributes which are inherited from global defaults to reduce affected golds.
        newRunPr.removeGlobalDefaults();

        // Whole table formatting.
        mStyleRunPr.expandTo(newRunPr);

        // Conditional formatting.
        mRunPrExpander.expand(this, newRunPr);

        // Save direct attrs. Don't add this para to ExpandedNodes array because it was added in ExpandParaPr.
        para.getParagraphBreakRunPr().setSysDirectAttrs(AttrCollectionExtension.deepClone(para.getParagraphBreakRunPr()));

        // WORDSNET-24396 When there is no revision in direct properties, it still can be found in
        // inherited ones. This means we should use Revised flag, otherwise we will get wrong
        // attributes from the original properties.
        /*RunPrExpandFlags*/int expFlag = RunPrExpandFlags.NO_TABLE_STYLE;
        if (!para.getParagraphBreakRunPr().hasFormatRevision())
            expFlag |= RunPrExpandFlags.REVISED;

        // Paragraph and character styles and direct formatting.
        // Table style is already expanded so use appropriate flag.
        para.expandParagraphBreakRunPr(newRunPr, expFlag);

        // WORDSNET-24396 Apply formatting revision just as we do it already
        // in the related WORDSNET-23975 and WORDSNET-24237 in ExpandRunPr().
        if (newRunPr.hasFormatRevision())
        {
            RunPr revRunPr = (RunPr)newRunPr.getFormatRevision().getRevPr();
            para.expandParagraphBreakRunPr(revRunPr, expFlag | RunPrExpandFlags.REVISED);
            revRunPr.collapse(para.getParagraphBreakRunPr());
            revRunPr.removeIgnorableOnCollapseValues();
        }

        para.setParagraphBreakRunPr(newRunPr);
    }

    private void expandRunPr(Run run) throws Exception
    {
        RunPr newRunPr = AttrCollectionExtension.deepClone(run.getDocument().getStyles().getDefaultRunPr());

        // Remove attributes which are inherited from global defaults to reduce affected golds.
        newRunPr.removeGlobalDefaults();

        // Whole table formatting.
        mStyleRunPr.expandTo(newRunPr);

        // Conditional formatting.
        mRunPrExpander.expand(this, newRunPr);

        // Save direct attrs.
        run.getRunPr().setSysDirectAttrs(AttrCollectionExtension.deepClone(run.getRunPr()));
        msArrayList.add(mAffectedNodes, run);

        // Paragraph and character styles and direct formatting.
        // Table style is already expanded so use appropriate flag.
        /*RunPrExpandFlags*/int expFlag = RunPrExpandFlags.NO_TABLE_STYLE;
        // WORDSNET-16276 Word ignores the "Hyperlink" style when the run locates inside TOC field.
        if (mIsInTocField)
            expFlag |= RunPrExpandFlags.IGNORE_HYPERLINK_CHAR_STYLE;

        // WORDSNET-24396 When there is no revision in direct properties, it still can be found in
        // inherited ones. This means we should use Revised flag, otherwise we will get wrong
        // attributes from the original properties.
        if (!run.getRunPr().hasFormatRevision())
            expFlag |= RunPrExpandFlags.REVISED;

        run.expandRunPr(newRunPr, expFlag);

        // WORDSNET-23975 Apply formatting revision.
        if (newRunPr.hasFormatRevision())
        {
            RunPr revRunPr = (RunPr)newRunPr.getFormatRevision().getRevPr();
            run.expandRunPr(revRunPr, expFlag | RunPrExpandFlags.REVISED);

            // WORDSNET-24237 Need to collapse revised properties here.
            revRunPr.collapse(run.getRunPr());
            revRunPr.removeIgnorableOnCollapseValues();
        }

        run.setRunPr(newRunPr);
    }

    /// <summary>
    /// Calculates which parts of table the style should be applied to. Init related flag variables.
    /// </summary>
    private void calcPartsToApply(/*TableStyleOptions*/int options)
    {
        mIsApplyFirstRow = ((options & TableStyleOptions.FIRST_ROW) != 0);
        mIsApplyLastRow = ((options & TableStyleOptions.LAST_ROW) != 0);
        mIsApplyFirstCol = ((options & TableStyleOptions.FIRST_COLUMN) != 0);
        mIsApplyLastCol = ((options & TableStyleOptions.LAST_COLUMN) != 0);
        mIsApplyRowBand = ((options & TableStyleOptions.ROW_BANDS) != 0) && (mRowBandSize > 0);
        mIsApplyColBand = ((options & TableStyleOptions.COLUMN_BANDS) != 0) && (mColBandSize > 0);

        // WORDSNET-27004 Despite of table can have StyleLook with FirstRow, LastRow, etc turned on
        // table style itself can have no formatting defined for these conditions.
        // Word treats it as formatting is off for the condition.
        // Note we should apply corner cells as recently i.e relay on TableStyleLook.

        // First preserve "corners" look. It should be applied regardless conditional formatting are present for FirstRow, FirstCol etc.
        mIsApplyNW = mIsApplyFirstRow && mIsApplyFirstCol;
        mIsApplyNE = mIsApplyFirstRow && mIsApplyLastCol;
        mIsApplySW = mIsApplyLastRow && mIsApplyFirstCol;
        mIsApplySE = mIsApplyLastRow && mIsApplyLastCol;

        // WORDSNET-21775 If 'Use Word 2002 Table Style Rules' option is on,
        // table style for the nwCell should not be applied,
        // despite the firstRow and the firstColumn is enabled in the tblLook.
        if (mTable.getDocument().fetchDocumentOrGlossaryMain().getCompatibilityOptions().getUseWord2002TableStyleRules())
            mIsApplyNW = false;

        // Then consider presence of conditional formatting defined.
        mIsApplyFirstRow &= getConditionalStyle(TableStyleOverrideType.FIRST_ROW) != null;
        mIsApplyLastRow &= getConditionalStyle(TableStyleOverrideType.LAST_ROW) != null;
        mIsApplyFirstCol &= getConditionalStyle(TableStyleOverrideType.FIRST_COLUMN) != null;
        mIsApplyLastCol &= getConditionalStyle(TableStyleOverrideType.LAST_COLUMN) != null;
    }

    public /*override*/ /*VisitorAction*/int visitRun(Run run) throws Exception
    {
        expandRunPr(run);
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Means that it is a first column now and we need to apply first column formatting.
    /// </summary>
    boolean isFirstColNow() { return mIsApplyFirstCol && mIsFirstCol; }

    boolean isLastColNow() { return mIsApplyLastCol && mIsLastCol; }

    boolean isFirstRowNow() { return mIsApplyFirstRow && mIsFirstRow; }

    boolean isLastRowNow() { return mIsApplyLastRow && mIsLastRow; }

    boolean isRowBandNow() { return mIsApplyRowBand && !isFirstRowNow() && !isLastRowNow(); }

    boolean isColBandNow() { return mIsApplyColBand && !isFirstColNow() && !isLastColNow(); }

    boolean isNWCellNow() { return mIsApplyNW && isFirstRow() && isFirstCol(); }

    boolean isNECellNow() { return mIsApplyNE && isFirstRow() && isLastCol(); }

    boolean isSWCellNow() { return mIsApplySW && isLastRow() && isFirstCol(); }

    boolean isSECellNow() { return mIsApplySE && isLastRow() && isLastCol(); }

    /// <summary>
    /// True when we are in the first column.
    /// </summary>
    boolean isFirstCol() { return mIsFirstCol; }

    boolean isLastCol() { return mIsLastCol; }

    boolean isFirstRow() { return mIsFirstRow; }

    boolean isLastRow() { return mIsLastRow; }


    boolean isFirstRowInBand() { return mIsFirstRowInBand; }

    boolean isLastRowInBand() { return mIsLastRowInBand; }

    boolean isFirstColInBand() { return mIsFirstColInBand; }

    boolean isLastColInBand() { return mIsLastColInBand; }


    int getRowBandSize() { return mRowBandSize; }

    int getColBandSize() { return mColBandSize; }


    /// <summary>
    /// Gets the row index excluding header rows.
    /// </summary>
    private int getDataRowIndex() { return (mIsApplyFirstRow || mIsHeadingRow) ? mRowIndex - mHeadingRowCount : mRowIndex; }

    private int getDataColIndex() { return (mIsApplyFirstCol) ? mCellIndex - 1 : mCellIndex; }


    /*TableStyleOverrideType*/int getRowBandNow()
    {
        boolean isOdd = MathUtil.isOdd(getDataRowIndex() / mRowBandSize);
        return (isOdd) ? TableStyleOverrideType.EVEN_ROW_BANDING : TableStyleOverrideType.ODD_ROW_BANDING;
    }

    /*TableStyleOverrideType*/int getColBandNow()
    {
        boolean isOdd = MathUtil.isOdd(getDataColIndex() / mColBandSize);
        return (isOdd) ? TableStyleOverrideType.EVEN_COLUMN_BANDING : TableStyleOverrideType.ODD_COLUMN_BANDING;
    }


    ConditionalStyle getConditionalStyle(/*TableStyleOverrideType*/int type)
    {
        return mConditionalStyles[(int)type];
    }

    Theme getTheme() { return mTheme; }

    /// <summary>
    /// Gets an array of attribute values with the keys from <see cref="gAutoFitGridCalculatedCellAttrs"/>
    /// </summary>
    private Object[] saveDirectAutoFitCalculatedAttrs(WordAttrCollection cellPr, int[] attrKeys)
    {
        Object[] savedAttrs = new Object[attrKeys.length];

        for (int i = 0; i < attrKeys.length; ++i)
            savedAttrs[i] = cellPr.getDirectAttr(attrKeys[i]);

        return savedAttrs;
    }

    /// <summary>
    /// Writes the given values of cell attributes to the cell properties.
    /// </summary>
    /// <remarks>
    /// The given values must match the keys in <see cref="gAutoFitGridCalculatedCellAttrs"/>;
    /// </remarks>
    private static void restoreDirectAutoFitCalculatedAttrs(WordAttrCollection modelAttrs, Object[] savedAttrs, int[] savedAttrKeys)
    {
        Debug.assertTrue(savedAttrs != null && savedAttrs.length == savedAttrKeys.length);

        for (int i = 0; i < savedAttrKeys.length; ++i)
        {
            int attrKey = savedAttrKeys[i];
            Object attrValue = savedAttrs[i];
            if (attrValue == null)
                modelAttrs.remove(attrKey);
            else
                modelAttrs.setAttr(attrKey, attrValue);
        }
    }

    /// <summary>
    /// Overrides inherited cell borders by table borders.
    /// </summary>
    private static void overrideInheritedCellBorders(Cell cell, CellPr newCellPr)
    {
        Row row = cell.getParentRow();

        // Directly specified table borders are backed up at this moment.
        TablePr tablePr = (TablePr)row.getTablePr().getSysDirectAttrs();

        overrideInheritedCellBorder(tablePr.get(row.isFirstRow() ? TableAttr.BORDER_TOP : TableAttr.BORDER_HORIZONTAL), newCellPr, CellAttr.BORDER_TOP);
        overrideInheritedCellBorder(tablePr.get(row.isLastRow() ? TableAttr.BORDER_BOTTOM : TableAttr.BORDER_HORIZONTAL), newCellPr, CellAttr.BORDER_BOTTOM);
        overrideInheritedCellBorder(tablePr.get(cell.isFirstCell() ? TableAttr.BORDER_LEFT : TableAttr.BORDER_VERTICAL), newCellPr, CellAttr.BORDER_LEFT);
        overrideInheritedCellBorder(tablePr.get(cell.isLastCell() ? TableAttr.BORDER_RIGHT : TableAttr.BORDER_VERTICAL), newCellPr, CellAttr.BORDER_RIGHT);
    }

    /// <summary>
    /// Overrides inherited cell border by according table border.
    /// </summary>
    private static void overrideInheritedCellBorder(Object tableBorder, CellPr cellPr, int cellKey)
    {
        if(tableBorder == null)
            return;

        // It looks that MS Word only overrides existing cell borders.
        Border cellBorder = (Border)cellPr.get(cellKey);
        if (cellBorder == null)
            return;

        cellPr.set(cellKey, ((Border)tableBorder).deepClone());
    }

    /// <summary>
    /// Cell attributes that may be changed during auto-fit grid calculation.
    /// </summary>
    /// <remarks>
    /// Auto-fit table grids are calculated during layout updates, because content metrics are only available during layout update.
    /// As a result of grid re-calculation, different cell grid span and cell values may be computed and applied to the model.
    /// These values should not be overwritten with values saved before layout update.
    /// </remarks>
    private static /*final*/ int[] G_AUTO_FIT_GRID_CALCULATED_CELL_ATTRS = new int[] { CellAttr.SYS_CELL_SPAN, CellAttr.WIDTH, };
    private static /*final*/ int[] G_AUTO_FIT_GRID_CALCULATED_ROW_ATTRS =
        new int[] { TableAttr.SYS_GRID_BEFORE, TableAttr.WIDTH_BEFORE, TableAttr.SYS_GRID_AFTER, TableAttr.WIDTH_AFTER, };

    private Table mTable;
    private Theme mTheme;

    private TablePr mStyleTablePr;
    private TablePr mStyleRowPr;
    private CellPr mStyleCellPr;
    private ParaPr mStyleParaPr;
    private RunPr mStyleRunPr;

    private ConditionalStyle[] mConditionalStyles;

    private int mRowBandSize;
    private int mColBandSize;

    private boolean mIsApplyFirstRow;
    private boolean mIsApplyLastRow;
    private boolean mIsApplyFirstCol;
    private boolean mIsApplyLastCol;
    private boolean mIsApplyRowBand;
    private boolean mIsApplyColBand;
    private boolean mIsHeadingRow;

    /// <summary>
    /// Corner cells.
    /// </summary>
    private boolean mIsApplyNW;
    private boolean mIsApplyNE;
    private boolean mIsApplySW;
    private boolean mIsApplySE;

    private int mRowIndex;
    private boolean mIsFirstRow;
    private boolean mIsLastRow;
    private boolean mIsFirstRowInBand;
    private boolean mIsLastRowInBand;

    private int mCellIndex;
    private boolean mIsFirstCol;
    private boolean mIsLastCol;
    private boolean mIsFirstColInBand;
    private boolean mIsLastColInBand;

    /// <summary>
    /// Heading rows count in current table.
    /// </summary>
    private int mHeadingRowCount = 1;

    /// <summary>
    /// Indicates that at least one body row was processed.
    /// </summary>
    private boolean mIsBodyRowOccurred = false;

    /// <summary>
    /// Indicates that currently the visitor running inside TOC field.
    /// </summary>
    private boolean mIsInTocField;

    private /*final*/ CnfExpanderRowPr mRowPrExpander = new CnfExpanderRowPr();
    private /*final*/ CnfExpanderCellPr mCellPrExpander = new CnfExpanderCellPr();
    private /*final*/ CnfExpanderParaPr mParaPrExpander = new CnfExpanderParaPr();
    private /*final*/ CnfExpanderRunPr mRunPrExpander = new CnfExpanderRunPr();

    /// <summary>
    /// Provides quick access to nodes affected by this expander.
    /// </summary>
    private /*final*/ ArrayList<Node> mAffectedNodes = new ArrayList<Node>();
    private static /*final*/ int G_TABLE_STYLE_OVERRIDE_TYPE_COUNT =
        EnumUtilPal.getEffectiveArrayLength((14));
}

