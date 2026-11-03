// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;


/// <summary>
/// Represents a table cell.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-tables/">Working with Tables</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p><see cref="Cell"/> can only be a child of a <see cref="Row"/>.</p>
/// <p><see cref="Cell"/> can contain block-level nodes <see cref="Paragraph"/> and <see cref="Table"/>.</p>
/// <p>A minimal valid cell needs to have at least one <see cref="Paragraph"/>.</p>
/// </remarks>
public class Cell extends CompositeNode<Node> implements ICellAttrSource, ITrackableNode
{
    /// <summary>
    /// Initializes a new instance of the <see cref="Cell"/> class.
    /// </summary>
    /// <remarks>
    /// <p>When <see cref="Cell"/> is created, it belongs to the specified document, but is not
    /// yet part of the document and <see cref="Node.ParentNode"/> is <c>null</c>.</p>
    /// <p>To append <see cref="Cell"/> to the document use <see cref="CompositeNode.InsertAfter{T}(T, Node)"/> or <see cref="CompositeNode.InsertBefore{T}(T, Node)"/>
    /// on the row where you want the cell inserted.</p>
    /// </remarks>
    /// <param name="doc">The owner document.</param>
    public Cell(DocumentBase doc)
    {
    	this(doc, new CellPr());
	
    }

    Cell(DocumentBase doc, CellPr cellPr)
    {
    	super(doc);
	
        mCellPr = cellPr;
    }

    /// <summary>
    /// Returns <see cref="NodeType.Cell"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.CELL; }

    /// <summary>
    /// Returns a table this cell belongs to.
    /// </summary>
    Table getParentTable() { return getParentRow().getParentTable(); }

    /// <summary>
    /// Gets the next <see cref="Cell"/> node.
    /// </summary>
    /// <remarks>
    /// The method can be used when you need to have typed access to cells of a <see cref="Row"/>. If a
    /// <see cref="StructuredDocumentTag"/> node is found in a row instead of a cell, it is automatically
    /// traversed to get a cell contained within.
    /// </remarks>
    public Cell getNextCell() { return (Cell)getNextNonMarkupCompositeLimited(); }

    /// <summary>
    /// Gets the previous <see cref="Cell"/> node.
    /// </summary>
    /// <remarks>
    /// The method can be used when you need to have typed access to cells of a <see cref="Row"/>. If a
    /// <see cref="StructuredDocumentTag"/> node is found in a row instead of a cell, it is automatically
    /// traversed to get a cell contained within.
    /// </remarks>
    public Cell getPreviousCell() { return (Cell)getPreviousNonMarkupCompositeLimited(); }

    /// <summary>
    /// Returns the parent row of the cell.
    /// </summary>
    /// <dev>
    /// Equivalent to <see cref="Node.FirstNonMarkupParentNode"/> casted to <see cref="Row"/>.
    /// </dev>
    public Row getParentRow() { return (Row)getFirstNonMarkupParentNode(); }

    /// <summary>
    /// Gets the first paragraph among the immediate children.
    /// </summary>
    public Paragraph getFirstParagraph() { return (Paragraph)getChild(NodeType.PARAGRAPH, 0, false); }

    /// <summary>
    /// Gets the last paragraph among the immediate children.
    /// </summary>
    public Paragraph getLastParagraph() { return (Paragraph)getChild(NodeType.PARAGRAPH, -1, false); }

    /// <summary>
    /// True if this is the first cell inside a row; false otherwise.
    /// </summary>
    public boolean isFirstCell() { return ((getParentRow() != null) && (this == getParentRow().getFirstCell())); }

    /// <summary>
    /// True if this is the last cell inside a row; false otherwise.
    /// </summary>
    public boolean isLastCell() { return ((getParentRow() != null) && (this == getParentRow().getLastCell())); }

    /// <summary>
    /// Gets the column index of this cell or -1 if this cell has no parent row.
    /// </summary>
    /// <remarks>
    /// Considers markup parent nodes.
    /// </remarks>
    int getColumnIndex()
    {
        Row parentRow = getParentRow();
        return (parentRow != null) ? parentRow.indexOfChildByDescendant(this, true) : -1;
    }
    
    /// <summary>
    /// Gets the row index of this cell or -1 if this cell has no parent row.
    /// </summary>
    int getRowIndex() { return (getParentRow() != null) ? getParentRow().getRowIndex() : -1; }

    /// <summary>
    /// Provides access to the formatting properties of the cell.
    /// </summary>
    public CellFormat getCellFormat()
    {
        if (mCellFormat == null)
            mCellFormat = new CellFormat(this);
        return mCellFormat;
    }

    /// <summary>
    /// Gets a collection of paragraphs that are immediate children of the cell.
    /// </summary>
    public ParagraphCollection getParagraphs()
    {
        if (mParagraphs == null)
            mParagraphs = new ParagraphCollection(this);
        return mParagraphs;
    }

    /// <summary>
    /// Gets a collection of tables that are immediate children of the cell.
    /// </summary>
    public TableCollection getTables()
    {
        if (mTables == null)
            mTables = new TableCollection(this);
        return mTables;
    }

    /// <summary>
    /// Gets the collection of attributes specified on this cell directly.
    /// </summary>
    CellPr getCellPr() { return mCellPr; }
    void setCellPr(CellPr value)
    {
        mCellPr = value;
    }

    /*override*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        Cell lhs = (Cell)super.deepClone(isCloneChildren, cloningListener);
        CellPr cellPr =  AttrCollectionExtension.deepClone(mCellPr);
        lhs.mCellPr = cellPr;
        lhs.mCellFormat = null;
        lhs.mParagraphs = null;
        lhs.mTables = null;
        return lhs;
    }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Node.Accept"]/*'/>
    /// <remarks>
    /// Calls <see cref="DocumentVisitor.VisitCellStart"/>, then calls <see cref="Node.Accept"/> for all child nodes of the section
    /// and calls <see cref="DocumentVisitor.VisitCellEnd"/> at the end.
    /// </remarks>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return acceptCore(visitor);
    }


    /// <summary>
    /// Accepts a visitor for visiting the start of the cell.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptStart(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitCellStart(this);
    }

    /// <summary>
    /// Accepts a visitor for visiting the end of the cell.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptEnd(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitCellEnd(this);
    }
    
    /// <summary>
    /// If the last child is not a paragraph, creates and appends one empty paragraph.
    /// </summary>
    public void ensureMinimum()
    {
        // WORDSNET-3381 Last node in a cell cannot be a table, it must be a paragraph.
        WordUtil.ensureNonEmptyStory(this);
    }

    /// <summary>
    /// Allows to insert block level elements.
    /// </summary>
    /*override*/ boolean canInsert(Node newChild)
    {
        return NodeUtil.isBlockLevelNode(newChild);
    }

    /// <summary>
    /// Gets the column index of this cell, ignoring cells with <see cref="CellMerge.Previous"/> merge state.
    /// </summary>
    int getMergedColumnIndex()
    {
        Row parentRow = getParentRow();
        if (parentRow == null)
            return -1;

        int index = -1;

        for (Cell cell = parentRow.getFirstCell(); cell != null; cell = cell.getNextCell())
        {
            if (cell.getCellFormat().getHorizontalMerge() != CellMerge.PREVIOUS)
                index++;

            if (cell == this)
                return index;
        }

        return -1;
    }

    //>>>>>>>> #region  ICellAttrSource

    /// <summary>
    /// ICellAttrSource.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Object /*ICellAttrSource.*/getDirectCellAttr(int key)
    {
        return mCellPr.getDirectAttr(key);
    }

    /// <summary>
    /// ICellAttrSource.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Object /*ICellAttrSource.*/fetchCellAttr(int key)
    {
        // SPEED for export to formats that require fully calculated values.
        // Gets the fully calculated value of the attribute.
        // For example, the border could be defined on the cell or inherited from the table or defaults.
        // Note that if the border is inherited, this will return the parent value, so don't modify it.
        Object value = mCellPr.getDirectAttr(key);
        if (value == null)
        {
            // The attribute is not defined on the cell, return the inherited value.
            return ((ICellAttrSource)this).fetchInheritedCellAttr(key);
        }
        else
        {
            IComplexAttr complexAttr = ms.as(value, IComplexAttr.class);
            if ((complexAttr != null) && (complexAttr.isInheritedComplexAttr()))
            {
                // The attribute is complex and it is inherited, return the inherited value.
                return ((ICellAttrSource)this).fetchInheritedCellAttr(key);
            }
            else
            {
                return value;
            }
        }
    }

    /// <summary>
    /// ICellAttrSource.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Object /*ICellAttrSource.*/fetchInheritedCellAttr(int key)
    {
        Object value = getInheritedCellAttr(key);

        if (value != null)
            return value;

        // WORDSNET-1348, ParentRow could be null if the cell is not added to a row.
        if (getParentRow() != null)
        {
            // When requesting a cell border or padding, retrieve them from the table row.
            // Interesting to note that while cell borders can inherit from the table,
            // shading does not inherit. If shading is not defined for the cell,
            // it means no shading, and we should not use the table default shading.
            IRowAttrSource rowAttrs = getParentRow();
            switch (key)
            {
                case CellAttr.TOP_PADDING:
                    return rowAttrs.fetchRowAttr(TableAttr.TOP_PADDING);
                case CellAttr.LEFT_PADDING:
                    return rowAttrs.fetchRowAttr(TableAttr.LEFT_PADDING);
                case CellAttr.BOTTOM_PADDING:
                    return rowAttrs.fetchRowAttr(TableAttr.BOTTOM_PADDING);
                case CellAttr.RIGHT_PADDING:
                    return rowAttrs.fetchRowAttr(TableAttr.RIGHT_PADDING);
                case CellAttr.BORDER_TOP:
                    return rowAttrs.fetchRowAttr(getParentRow().isFirstRow() ? TableAttr.BORDER_TOP : TableAttr.BORDER_HORIZONTAL);
                case CellAttr.BORDER_LEFT:
                    return rowAttrs.fetchRowAttr(isFirstCell() ? TableAttr.BORDER_LEFT : TableAttr.BORDER_VERTICAL);
                case CellAttr.BORDER_BOTTOM:
                    return rowAttrs.fetchRowAttr(getParentRow().isLastRow() ? TableAttr.BORDER_BOTTOM : TableAttr.BORDER_HORIZONTAL);
                case CellAttr.BORDER_RIGHT:
                    return rowAttrs.fetchRowAttr(isLastCell() ? TableAttr.BORDER_RIGHT : TableAttr.BORDER_VERTICAL);
                default:
                    break;
            }
        }

        // Return global default value.
        return CellPr.fetchDefaultAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*ICellAttrSource.*/setCellAttr(int key, Object value)
    {
        mCellPr.setAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*ICellAttrSource.*/clearCellAttrs()
    {
        mCellPr.clear();
    }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets inherited cell attribute. 
    /// Does not fetch global defaults.
    /// Does not inherit table borders or paddings.
    /// </summary>
    /// <remarks>
    /// AM. It seems that we need some flags for ICellAttrSource.FetchCellAttr similar to RunPrExpandFlags then 
    /// we can remove this method and replace it with FetchCellAttr.
    /// Lets postpone this for a while.
    /// </remarks>
    Object getInheritedCellAttr(int key)
    {
        // WORDSNET-16039 For cloned rows ParentRow is not null, but ParentTable is null,
        // if row not yet added to table. Therefore, need to check both.
        if ((getParentRow() == null) || (getParentTable() == null))
            return null;

        TableStyle tableStyle = ms.as(getParentTable().getStyle(), TableStyle.class);
        return (tableStyle != null) ? tableStyle.getCellAttr(key, this) : null;
    }

    @ReservedForInternalUse @Deprecated
    public EditRevision /*ITrackableNode.*/getInsertRevision() { return getCellPr().getInsertRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*ITrackableNode.*/setInsertRevision(EditRevision value) { getCellPr().setInsertRevision(value); }

    @ReservedForInternalUse @Deprecated
    public EditRevision /*ITrackableNode.*/getDeleteRevision() { return getCellPr().getDeleteRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*ITrackableNode.*/setDeleteRevision(EditRevision value) { getCellPr().setDeleteRevision(value); }

    @ReservedForInternalUse @Deprecated
    public MoveRevision /*IMoveTrackableNode.*/getMoveFromRevision() { return getCellPr().getMoveFromRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*IMoveTrackableNode.*/setMoveFromRevision(MoveRevision value) { getCellPr().setMoveFromRevision(value); }

    @ReservedForInternalUse @Deprecated
    public MoveRevision /*IMoveTrackableNode.*/getMoveToRevision() { return getCellPr().getMoveToRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*IMoveTrackableNode.*/setMoveToRevision(MoveRevision value) { getCellPr().setMoveToRevision(value); }

    @ReservedForInternalUse @Deprecated
    public void /*IMoveTrackableNode.*/removeMoveRevisions()
    {
        getCellPr().remove(RevisionAttr.MOVE_FROM_REVISION);
        getCellPr().remove(RevisionAttr.MOVE_TO_REVISION);
    }

    /// <summary>
    /// Creates and returns a fully expanded collection of attributes for this cell.
    /// </summary>
    CellPr getExpandedCellPr(boolean expandTableStyle)
    {
        CellPr cellPr = new CellPr();

        // RK I think only borders and padding are inherited from the table.
        expandAttr(CellAttr.BORDER_LEFT, cellPr);
        expandAttr(CellAttr.BORDER_RIGHT, cellPr);
        expandAttr(CellAttr.BORDER_TOP, cellPr);
        expandAttr(CellAttr.BORDER_BOTTOM, cellPr);

        expandAttr(CellAttr.LEFT_PADDING, cellPr);
        expandAttr(CellAttr.RIGHT_PADDING, cellPr);
        expandAttr(CellAttr.TOP_PADDING, cellPr);
        expandAttr(CellAttr.BOTTOM_PADDING, cellPr);

        // Expand conditional formatting.
        if (expandTableStyle)
        {
            TableStyle tableStyle = ms.as(getParentTable().getStyle(), TableStyle.class);
            if (tableStyle != null)
                tableStyle.expandCellPr(this, cellPr);
        }

        mCellPr.expandTo(cellPr);

        return cellPr;
    }

    private void expandAttr(int key, CellPr dstCellPr)
    {
        dstCellPr.setAttr(key, ((ICellAttrSource)this).fetchInheritedCellAttr(key));
    }

    private CellPr mCellPr;
    private CellFormat mCellFormat;
    private ParagraphCollection mParagraphs;
    private TableCollection mTables;
}

