// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ArgumentUtil;
import com.aspose.Debug;


/// <summary>
/// Represents a table row.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-tables/">Working with Tables</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p><see cref="Row"/> can only be a child of a <see cref="Table"/>.</p>
/// <p><see cref="Row"/> can contain one or more <see cref="Cell"/> nodes.</p>
/// <p>A minimal valid row needs to have at least one <see cref="Cell"/>.</p>
/// </remarks>
public class Row extends CompositeNode<Cell> implements IRowAttrSource, ITrackableNode
{
    /// <summary>
    /// Initializes a new instance of the <see cref="Row"/> class.
    /// </summary>
    /// <remarks>
    /// <p>When <see cref="Row"/> is created, it belongs to the specified document, but is not
    /// yet part of the document and <see cref="Node.ParentNode"/> is <c>null</c>.</p>
    /// <p>To append <see cref="Row"/> to the document use <see cref="CompositeNode.InsertAfter{T}(T, Node)"/> or <see cref="CompositeNode.InsertBefore{T}(T, Node)"/>
    /// on the table where you want the row inserted.</p>
    /// </remarks>
    /// <param name="doc">The owner document.</param>
    public Row(DocumentBase doc)  // It seems a good idea to make the table look like created in MS Word when user creates it via the public ctor.
    {  // It seems a good idea to make the table look like created in MS Word when user creates it via the public ctor.
    	this(
        doc,
        TablePr.createMSWordLooking());
	
    }

    Row(DocumentBase doc, TablePr rowPr)
    {
    	super(doc);
	
        mTablePr = rowPr;
    }

    /// <summary>
    /// Returns <see cref="NodeType.Row"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.ROW; }

    /// <summary>
    /// Returns the immediate parent table of the row.
    /// </summary>
    /// <dev>
    /// Equivalent to <see cref="Node.FirstNonMarkupParentNode"/> casted to <see cref="Table"/>.
    /// </dev>
    public Table getParentTable() { return (Table)getFirstNonMarkupParentNode(); }

    /// <summary>
    /// True if this is the first row in a table; false otherwise.
    /// </summary>
    public boolean isFirstRow() { return (this == getParentTable().getFirstRow()); }

    /// <summary>
    /// True if this is the last row in a table; false otherwise.
    /// </summary>
    public boolean isLastRow() { return (this == getParentTable().getLastRow()); }

    /// <summary>
    /// True if this is the heading row of a table; false otherwise.
    /// </summary>
    /// <remarks>
    /// The first N rows which contains TableAttr.HeadingFormat attribute, are treated as heading
    /// rows. A conditional style with TableStyleOverrideType.FirstRow applies to these N rows.
    /// </remarks>
    boolean isHeadingRow()
    {
        if (!getRowFormat().getHeadingFormat())
            return false;

        return isFirstRow() || getPreviousRow().isHeadingRow();
    }

    /// <summary>
    /// Gets the next <see cref="Row"/> node.
    /// </summary>
    /// <remarks>
    /// The method can be used when you need to have typed access to table rows. If a
    /// <see cref="StructuredDocumentTag"/> node is found in a table instead of a row,
    /// it is automatically traversed to get a row contained within.
    /// </remarks>
    public Row getNextRow() { return (Row)getNextNonMarkupCompositeLimited(); }

    /// <summary>
    /// Gets the previous <see cref="Row"/> node.
    /// </summary>
    /// <remarks>
    /// The method can be used when you need to have typed access to table rows. If a
    /// <see cref="StructuredDocumentTag"/> node is found in a table instead of a row,
    /// it is automatically traversed to get a row contained within.
    /// </remarks>
    public Row getPreviousRow() { return (Row)getPreviousNonMarkupCompositeLimited(); }

    /// <summary>
    /// Gets the index of this row in the parent collection or -1 if this row has no parent table..
    /// </summary>
    /// <remarks>
    /// Considers markup parent nodes.
    /// </remarks>
    int getRowIndex()
    {
        Table parentTable = getParentTable();

        // WORDSNET-28406 Multiple markup nesting prevents proper RoweIndex calculation.
        if (parentTable != null)
        {
            for (int i = 0; i < parentTable.getRows().getCount(); i++)
                if (this == parentTable.getRows().get(i))
                    return i;
        }

        return -1;
    }

    /// <summary>
    /// Returns the first <see cref="Cell"/> in the row.
    /// </summary>
    public Cell getFirstCell() { return (Cell)getFirstNonMarkupCompositeDescendant(); }

    /// <summary>
    /// Returns the last <see cref="Cell"/> in the row.
    /// </summary>
    public Cell getLastCell() { return (Cell)getLastNonMarkupCompositeDescendant(); }

    /// <summary>
    /// Provides typed access to the <see cref="Cell"/> child nodes of the row.
    /// </summary>
    public CellCollection getCells()
    {
        if (mCells == null)
            mCells = new CellCollection(this);
        return mCells;
    }

    /// <summary>
    /// Provides access to the formatting properties of the row.
    /// </summary>
    public RowFormat getRowFormat()
    {
        if (mRowFormat == null)
            mRowFormat = new RowFormat(this);
        return mRowFormat;
    }

    /// <summary>
    /// Gets or sets a flag indicating whether this row is hidden or not.
    /// </summary>
    /// <remarks>
    /// Hidden row is not supported for WordML and ODT documents.
    /// </remarks>
    /// <dev>
    /// Corresponds to the 17.4.20 hidden attribute (Hidden Table Row Marker) [ISO/IEC 29500].
    /// </dev>
    public boolean getHidden() { return mTablePr.getHidden(); }
    public void setHidden(boolean value)
    {
        mTablePr.setHidden(value);
        // MS Word hides table row only if the cells contents and end-of-paragraph mark are also hidden.
        setRowParagraphHidden(value);
    }

    /// <summary>
    /// Hide or unhide all content in a row including paragraph.
    /// </summary>
    private void setRowParagraphHidden(boolean isHidden)
    {
        for (Cell cell : (Iterable<Cell>) mCells)
        {
            for (Paragraph para : (Iterable<Paragraph>) cell.getParagraphs())
            {
                // Hide row paragraph.
                para.getParagraphBreakRunPr().setHidden(isHidden ? AttrBoolEx.TRUE : AttrBoolEx.FALSE);

                // Hide all content in the cell.
                for (Run run : (Iterable<Run>) para.getRuns())
                    run.getFont().setHidden(isHidden);
            }
        }
    }

    TablePr getTablePr() { return mTablePr; }
    void setTablePr(TablePr value)
    {
        mTablePr = value;
    }

    /// <summary>
    /// Indicates if the row has a valid cell spacing setting.
    /// </summary>
    /// <remarks>
    /// Spacing 0 and no spacing should be treated differently
    /// as tables with 0 spacing specified have layouts different from tables with no spacing.
    ///
    /// MS Word allows different spacing settings on the rows of the same tables.
    /// In the UI this can be the result of removing contents between tables with different spacing settings.
    ///
    /// Though spacing settings in oxml has type (similar to tcw),
    /// MS Word seems to disregard spacing settings with auto or pct types.
    /// This property reports false if such  spacing with a type other than Fixed is in the model.
    /// </remarks>
    boolean hasCellSpacing()
    {
        // Treat Auto and Pct spacing as no spacing.
        PreferredWidth spacingRaw = getCellSpacingRaw();
        return (spacingRaw != null) && spacingRaw.isFixed();
    }

    /// <summary>
    /// Gets cell spacing value specified for the row, in twips.
    /// </summary>
    /// <remarks>
    /// The value is actually half of the spacing reported by MS Word UI as both MS Word and AW
    /// store half of the spacing in the model.
    ///
    /// The property should be used in conjunction with <see cref="HasCellSpacing"/>,
    /// to proper interpret 0 value.
    ///
    /// Negative spacings are interpreted as 0 spacing is specified, mimicking MS Word behavior.
    /// </remarks>
    int getCellSpacing()
    {
        // Treat negative spacing as 0.
        return hasCellSpacing()
            ? Math.max(getCellSpacingRaw().getValueTwips(), 0)
            : 0;
    }

    /// <summary>
    /// Implements cell spacing attributes interpretation, the comments in <see cref="HasCellSpacing"/> and <see cref="CellSpacing"/>.
    /// </summary>
    private PreferredWidth getCellSpacingRaw()
    {
        // Do not use FetchRowAttr() or row, it reverts to defaults in absence of direct value,
        // and the defaults have 0 spacing defined for some reason (which is different from absence of the attribute).
        PreferredWidth spacingRaw = (PreferredWidth)((IRowAttrSource)this).getDirectRowAttr(TableAttr.CELL_SPACING);
        // TODO consider removing the default.

        if (spacingRaw == null)
        {
            TableStyle tableStyle = ms.as(getParentTable().getStyle(), TableStyle.class);
            while(tableStyle != null)
            {
                spacingRaw = (PreferredWidth)((IRowAttrSource)tableStyle).getDirectRowAttr(TableAttr.CELL_SPACING);
                if (spacingRaw != null)
                    break;

                tableStyle = ms.as(tableStyle.getBaseStyle(), TableStyle.class);
            }
        }

        return spacingRaw;
    }

    /// <summary>
    /// Attempts to fix invalid table style identifier.
    /// </summary>
    void ensureTableStyleIsValid()
    {
        if (getTablePr().contains(TableAttr.ISTD))
        {
            Style style = getDocument().getStyles().getByIstd(getTablePr().getIstd(), false);

            if ((style == null) || (style.getType() != StyleType.TABLE))
            {
                // Use default Istd.
                getTablePr().setIstd(StyleIndex.TABLE_NORMAL);
            }
        }
    }

    /*override*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        Row lhs = (Row)super.deepClone(isCloneChildren, cloningListener);
        lhs.mTablePr = AttrCollectionExtension.deepClone(mTablePr);
        lhs.mRowFormat = null;
        lhs.mCells = null;
        return lhs;
    }

    /// <summary>
    /// Returns true if either row or inner paragraph properties have same absolute positioning for two rows.
    /// </summary>
    /// <remarks>
    /// AM. DOC spec says:
    /// 2.4.3 Overview of Tables
    /// If neither table row specifies nondefault values for the preceding table position and wrapping properties,
    /// then two adjacent table rows of the same table depth are considered different tables if the first paragraphs
    /// of the first cells of the rows differ in any of the paragraph frame properties.
    /// </remarks>
    boolean isSameRowFloatingPositioning(Row rhs)
    {
        if (!getTablePr().isSameFloatingPositioning(rhs.getTablePr()))
            return false;

        // WORDSNET-21068 Do not compare floating of paragraphs in nested tables.
        if (!(getTablePr().isFloating() || getParentTable().isNested()))
        {
            Cell cell1 = getFirstCell();
            Cell cell2 = rhs.getFirstCell();

            if (ArgumentUtil.bothAreNotNull(cell1, cell2))
            {
                Paragraph para1 = (Paragraph) cell1.getChild(NodeType.PARAGRAPH, 0, true);
                Paragraph para2 = (Paragraph) cell2.getChild(NodeType.PARAGRAPH, 0, true);

                if (ArgumentUtil.bothAreNotNull(para1, para2))
                {
                    ParaPr expParaPr1 = para1.getExpandedParaPr(ParaPrExpandFlags.LAYOUT);
                    ParaPr expParaPr2 = para2.getExpandedParaPr(ParaPrExpandFlags.LAYOUT);

                    return expParaPr1.isSameFloatingPositioning(expParaPr2);
                }
            }
        }

        return true;
    }

    /// <summary>
    /// Breaks the table (that is the parent of the specified row) into two tables
    /// and moves all rows starting from the specified row into the new table.
    /// </summary>
    void breakTableBefore()
    {
        Table table = getParentTable();

        // Create a new table and insert it after the existing table.
        Table newTable = new Table(table.getDocument());
        table.insertNext(newTable);

        // Move all rows starting from the specified row to the new table.
        Node curRow = this;
        while (curRow != null)
        {
            Node nextRow = curRow.getNextSibling();
            curRow.remove();
            newTable.appendChildForLoad(curRow);
            curRow = nextRow;
        }
    }


    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Node.Accept"]/*'/>
    /// <remarks>
    /// Calls <see cref="DocumentVisitor.VisitRowStart"/>, then calls <see cref="Node.Accept"/> for all child nodes of the section
    /// and calls <see cref="DocumentVisitor.VisitRowEnd"/> at the end.
    /// </remarks>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return acceptCore(visitor);
    }

    /// <summary>
    /// Accepts a visitor for visiting the start of the row.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptStart(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitRowStart(this);
    }

    /// <summary>
    /// Accepts a visitor for visiting the end of the row.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptEnd(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitRowEnd(this);
    }

    /// <summary>
    /// Gets the text of all cells in this row including the end of row character.
    /// </summary>
    ///<remarks>
    ///<p>Returns concatenated text of all child nodes with the end of row character
    /// <see cref="ControlChar.Cell"/> appended at the end.</p>
    ///<p>The returned string includes all control and special characters as described in <see cref="ControlChar"/>.</p>
    ///</remarks>
    public /*override*/ String getText()
    {
        //Overridden here only so I can provide detailed API documentation.
        return super.getText();
    }

    /// <summary>
    /// If the <see cref="Row"/> has no cells, creates and appends one <see cref="Cell"/>.
    /// </summary>
    public void ensureMinimum()
    {
        Cell cell = getFirstCell();
        if (cell == null)
            cell = (Cell)appendChild(new Cell(getDocument()));

        cell.ensureMinimum();
    }

    /// <summary>
    /// I decided to keep MS Word-like behavior and represent end of row as \x0007,
    /// but I don't bother using \r for nested cells (that's MS Word's own hack anyway).
    ///
    /// The main reason I add end of row character is to allow to position the cursor using a flat
    /// integer position. If the end of row did not have a char - it would be impossible to distinguish
    /// from cursor inside the row at the end of it or at the beginning of the next row or paragraph.
    /// </summary>
    /*override*/ String getEndText()
    {
        return ControlChar.CELL;
    }

    /// <summary>
    /// Allows only cell-level elements to be inserted.
    /// </summary>
    /*override*/ boolean canInsert(Node newChild)
    {
        return NodeUtil.isCellLevelNode(newChild);
    }

    /// <summary>
    /// Copies <see cref="CellPr"/> from each <see cref="Cell"/> of <see cref="Cells"/> of this row
    /// to a corresponding cell in the collection passed.
    /// </summary>
    void copyCellPrTo(CellCollection cells)
    {
        Debug.assertTrue(getCells().getCount() == cells.getCount());
        for (int j = 0; j < cells.getCount(); j++)
        {
            CellPr source = getCells().get(j).getCellPr();
            if (source != null)
                cells.get(j).setCellPr(AttrCollectionExtension.deepClone(source));
        }
    }

    /// <summary>
    /// Removes floating attributes from all paragraphs within the row.
    /// </summary>
    /// <remarks>
    /// AM. DOC/RTF have paragraph floating attributes which repeat corresponding floating attributes of table.
    /// If we leave them we should change them along with table attributes otherwise output is not OK.
    /// I decided just remove them upon DOC/RTF import if paragraph is in floating table, this is the way floating table stored in DOCX, so
    /// we get more unified Model regardless format.
    /// Output is seems to be good.
    /// </remarks>
    void removeParagraphFloatingAttributes()
    {
        for (Paragraph paragraph : (Iterable<Paragraph>) getChildNodes(NodeType.PARAGRAPH, true))
            paragraph.getParaPr().removeFloatingAttrs();
    }

    //>>>>>>>> #region  IRowAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*IRowAttrSource.*/getDirectRowAttr(int key)
    {
        return mTablePr.getDirectAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRowAttrSource.*/fetchRowAttr(int key)
    {
        Object value = mTablePr.get(key);
        return (value != null) ? value : ((IRowAttrSource)this).fetchInheritedRowAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRowAttrSource.*/fetchInheritedRowAttr(int key)
    {
        if (getParentTable() != null)
        {
            TableStyle tableStyle = ms.as(getParentTable().getStyle(), TableStyle.class);
            if (tableStyle != null)
                return ((IRowAttrSource)tableStyle).fetchRowAttr(key);
        }
        return TablePr.fetchDefaultAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRowAttrSource.*/setRowAttr(int key, Object value)
    {
        mTablePr.setAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRowAttrSource.*/clearRowAttrs()
    {
        mTablePr.clear();
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRowAttrSource.*/resetToDefaultAttrs() throws Exception
    {
        ((IRowAttrSource)mTablePr).resetToDefaultAttrs();
    }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets a boolean value indicating if the row has the given attribute specified directly or via table style.
    /// </summary>
    boolean hasAttribute(int key)
    {
        Object value = ((IRowAttrSource)this).getDirectRowAttr(key);
        if (value != null)
            return true;

        TableStyle tableStyle = ms.as(getParentTable().getStyle(), TableStyle.class);
        if (tableStyle == null)
            return false;

        value = ((IRowAttrSource)tableStyle).getDirectRowAttr(key);

        return (value != null);
    }

    /// <summary>
    /// Removes indent for aligned row.
    /// </summary>
    /// <remarks>
    /// See WORDSNET-25666.
    /// </remarks>
    void validateAlignedIndent()
    {
        if (!getDocument().getDocPr().CompatibilityOptions.isWord2013OrLaterCompatible())
            return;

        if (getTablePr().getAlignment() != TableAlignment.LEFT)
            getTablePr().remove(TableAttr.LEFT_INDENT);

        if (getTablePr().hasFormatRevision())
        {
            TablePr revPr = (TablePr)getTablePr().getFormatRevision().getRevPr();

            /*TableAlignment*/int revAlignment = revPr.containsKey(TableAttr.ALIGNMENT)
                ? revPr.getAlignment()
                : getTablePr().getAlignment();

            if (revAlignment != TableAlignment.LEFT)
                revPr.remove(TableAttr.LEFT_INDENT);
        }
    }

    /// <summary>
    /// Specifies an identifier for the row that is unique within the document part.
    /// </summary>
    int getParaId() { return mParaId; }
    void setParaId(int value) { mParaId = value; }

    /// <summary>
    /// Specifies version identifier for the row.
    /// </summary>
    int getTextId() { return mTextId; }
    void setTextId(int value) { mTextId = value; }

    @ReservedForInternalUse @Deprecated
    public EditRevision /*ITrackableNode.*/getInsertRevision() { return getTablePr().getInsertRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*ITrackableNode.*/setInsertRevision(EditRevision value) { getTablePr().setInsertRevision(value); }

    @ReservedForInternalUse @Deprecated
    public EditRevision /*ITrackableNode.*/getDeleteRevision() { return getTablePr().getDeleteRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*ITrackableNode.*/setDeleteRevision(EditRevision value) { getTablePr().setDeleteRevision(value); }

    @ReservedForInternalUse @Deprecated
    public MoveRevision /*IMoveTrackableNode.*/getMoveFromRevision() { return getTablePr().getMoveFromRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*IMoveTrackableNode.*/setMoveFromRevision(MoveRevision value) { getTablePr().setMoveFromRevision(value); }

    @ReservedForInternalUse @Deprecated
    public MoveRevision /*IMoveTrackableNode.*/getMoveToRevision() { return getTablePr().getMoveToRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*IMoveTrackableNode.*/setMoveToRevision(MoveRevision value) { getTablePr().setMoveToRevision(value); }

    @ReservedForInternalUse @Deprecated
    public void /*IMoveTrackableNode.*/removeMoveRevisions()
    {
        getTablePr().remove(RevisionAttr.MOVE_FROM_REVISION);
        getTablePr().remove(RevisionAttr.MOVE_TO_REVISION);
    }

    /// <summary>
    /// Specifies an identifier for the row that is unique within the document part. See [MS-DOCX] 2.4.1 paraId.
    /// </summary>
    /// <remarks>
    /// AM. See remarks in Paragraph.mParaId
    /// </remarks>
    private int mParaId;

    /// <summary>
    /// Specifies version identifier for the row.
    /// </summary>
    private int mTextId;

    private TablePr mTablePr;
    private RowFormat mRowFormat;
    private CellCollection mCells;
}

