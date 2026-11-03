// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/12/2006 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.SortedIntegerListGeneric;
import com.aspose.IntList;


/// <summary>
/// This is the class to use if you need to calculate a "table grid".
///
/// The table grid is a definition of the set of grid columns which define all of the shared vertical
/// edges of the table, as well as default widths for each of these grid columns.
/// </summary>
/// <remarks>
/// dmatv: This class calculates cell grid spans from CellAttr.Width.
/// Though it is still used, the results may not match MS Word when cell width data are missing or not correct for some reason.
/// So for formats that do not store cell width explicitly in the document (such as docx), grid calculation is done by different (new) logic when possible.
/// I renamed the class from TableGrid to TableGridFromCellWidth so that this class does not look like the main table grid entity.
///
/// For binary doc and rtf formats, this class is used both by the new table layout logic as well.
/// Unlike docx/wml, these formats actually store cell widths inside the document.
/// They do not store a tblGrid equivalent, so it must be recreated from cell widths for doc/rtf.
/// </remarks>
class TableGridFromCellWidth
{
    /// <summary>
    /// Create an instance of this class when you visit a table.
    /// </summary>
    TableGridFromCellWidth(Table table) { 	this(table, false);
	 }

    /// <summary>
    /// Creates table grid object for either original or revised table attributes.
    /// </summary>
    TableGridFromCellWidth(Table table, boolean isRevision)
	{
		this(table, isRevision, false);
	}

    /// <summary>
    /// Creates table grid object for either original or revised table attributes.
    /// </summary>
    TableGridFromCellWidth(Table table, boolean isRevision, boolean useSystemAttrs)
    {
        mRevisionsView = isRevision ? RevisionsView.FINAL : RevisionsView.ORIGINAL;
        // Set strategy for getting width before row.
        mWidthBeforeMethod = getWidthBeforeMethod(table, useSystemAttrs);

        calcColumnWidthsAndRowIndents(table);
    }

    /// <summary>
    /// Creates an instance of this class for handling doc/rtf input.
    /// </summary>
    /// <remarks>
    /// The method is used by the "new" table grid logic.
    /// </remarks>
    static TableGridFromCellWidth getDocRtfGridFromAttrs(Table table)
    {
        boolean useFinalRevision = true;
        boolean useSystemAttrs = true;
        return new TableGridFromCellWidth(table, useFinalRevision, useSystemAttrs);
    }

    /// <summary>
    /// Calculate column widths array for the specified table.
    /// </summary>
    private void calcColumnWidthsAndRowIndents(Table table)
    {
        SortedIntegerListGeneric<Object> columnPositions = new SortedIntegerListGeneric<Object>();

        // Calculate column positions and width before rows.
        for (Row row = table.getFirstRow(); row != null; row = row.getNextRow())
        {
            // Add all column positions of all rows to the sorted array as keys.
            // I don't use the values in this sorted array (only use keys), so insert nulls.
            // In the end we get a sorted positions array, which can then be used to calculate widths.

            int position = getWidthBeforeRow(row);
            columnPositions.set(position, null);

            for (Cell cell : (Iterable<Cell>) row.getCells())
            {
                // Use CellPr used by MS Word to render cell to calculate grid.
                int cellWidth = (/*int*/Integer)cell.getCellPr().fetchAttr(CellAttr.WIDTH, mRevisionsView);
                position += cellWidth;
                columnPositions.set(position, null);
            }
        }

        int[] columnWidths = new int[Math.max(columnPositions.getCount() - 1, 0)];
        int leftmostRowPosition = columnPositions.getKey(0);
        int columnLeft = leftmostRowPosition;
        for (int columnIndex = 0; columnIndex < columnWidths.length; columnIndex++)
        {
            int columnRight = columnPositions.getKey(columnIndex + 1);
            columnWidths[columnIndex] = columnRight - columnLeft;
            // Next column.
            columnLeft = columnRight;
        }

        // Save the computed properties.
        mColumnWidths = columnWidths;
        setLeftmostRowPosition(leftmostRowPosition);
        // TestJira15558 demonstrates a case when the leftmost row position is negative.
        // It should not happen when widthBefor is specified explicitly,
        // but when computed from sprmTDxaLeft and sprmTDxaGapHalf it is apparently possible.
        // Remember the value as it is needed to compute the actual width before rows.
    }

    /// <summary>
    /// Determines which way of computing 'width before' rows to use.
    /// </summary>
    private /*WidthBeforeType*/int getWidthBeforeMethod(Table table, boolean useSystemAttrs)
    {
        /*WidthBeforeType*/int method;
        if (useSystemAttrs)
        {
            // There are three different ways do get widthBefore for doc/rtf.
            boolean useWord97Logic = (/*boolean*/Boolean)table.getFirstRow().getTablePr().fetchAttr(TableAttr.SYS_WORD_97_LOGIC);
            if (useWord97Logic)
            {
                // Use the value computed by readers from older Word97 attributes.
                method = WidthBeforeType.FROM_WORD_97_ATTRIBUTES;
            }
            else if (isWidthBeforeApplicable(table))
            {
                // TableAttr.WidthBefore is present, use it.
                method = WidthBeforeType.ATTR_DIRECT;
                // The new algorithm does not handle revisions yet.
            }
            else
            {
                // Compute from doc/rtf specific row attributes.
                method = WidthBeforeType.FROM_DXA_DOC_RTF_ATTRIBUTES;
            }
        }
        else
        {
            // Read from TableAttr.WidthBefore, use revisions view.
            method = WidthBeforeType.ATTR_REVISION_AWARE;
        }
        return method;
    }

    /// <summary>
    /// Determines if taking 'width before' value from TableAttr.WidthBefore is applicable to the table.
    /// </summary>
    /// <remarks>
    /// For doc/rtf in absence of explicitly specified width before values, a method that uses
    /// different attributes is applied.
    /// </remarks>
    private static boolean isWidthBeforeApplicable(Table table)
    {
        // Relying on doc/rtf specific attributes makes no sense for other formats.
        // Always rely on widthBefore for formats other than doc/rtf.
        if (!DocRtfGridHandler.isGridStoredInCellWidths(table))
            return true;

        // Check if width before is (was) actually present for a row at the time of reading.
        // Discarded width before values specified in percent units count as well (Test26203*).
        for (Row row : (Iterable<Row>) table.getRows())
        {
            PreferredWidth widthBefore = (PreferredWidth)row.getTablePr().getDirectAttr(TableAttr.WIDTH_BEFORE);
            if ((widthBefore != null) && !widthBefore.isPercent())
                return true;
        }

        return false;
    }

    /// <summary>
    /// Gets 'width before' value for a row using a method determined earlier for the whole table.
    /// </summary>
    private int getWidthBeforeRow(Row row)
    {
        int widthBefore;
        switch (getWidthBeforeMethod())
        {
            case WidthBeforeType.ATTR_REVISION_AWARE:
                widthBefore = getWidthBeforeFromAttributes(row.getTablePr());
                break;
            case WidthBeforeType.FROM_WORD_97_ATTRIBUTES:
                // Tests like TestJira6544 and TestDefect18346 use values computed by the reader from Word97-specific attributes.
                int baseIndent = (/*int*/Integer)row.getParentTable().getFirstRow().getTablePr().fetchAttr(TableAttr.SYS_LEFT_INDENT_97_BASE);
                // Test18512: for RTL tables, the Sys_LeftIndent97 specifies width after the row.
                boolean isRtl = row.getParentTable().getFirstRow().getTablePr().contains(TableAttr.SYS_BIDI_TABLE_97);
                int indent = isRtl
                    ? 0
                    : (/*int*/Integer)row.getTablePr().fetchAttr(TableAttr.SYS_LEFT_INDENT_97);
                widthBefore = indent - baseIndent;

                // TODO consider moving parallel code from doc and rtf readers it this class or maybe DocRtfGridHandler.
                // Otherwise it is the same attribute as below.
                break;
            case WidthBeforeType.ATTR_DIRECT:
                // Tests like TestCeller1 seem to use the value from the special attribute.
                // SprmCode.TWidthBefore for doc format; equivalent in trwWidthB and trftsWidthB for rtf.
                // Currently the readers are somewhat different in handling the value type.
                // Values in twips should be reliable.
                // If specified, they should be used in favor of explicit cell positions handled below.
                widthBefore = getWidthBeforeFromAttributes(row.getTablePr());
                // However TestJira7090 shows a complex table with inconsistent SprmCode.TWidthBefore.
                // The values are different from the values saved by MS Word to docx,
                // and the values for rows 1 and 2 are different even though MS Word saves the same gridBefore 2 to docx.
                // Perhaps it can be explained by removing an intersection column spanned by width before,
                // but I was not able to reconstruct MS Word computation for 7090 precisely.
                // I think it should be addressed when WidthTolerance is set to 0,
                // which is not possible at the moment as it breaks some tests because of another issue.
                // See comments in WidthTolerance.
                break;
            case WidthBeforeType.FROM_DXA_DOC_RTF_ATTRIBUTES:
                // Use the value computed from the attributes specifying row position explicitly.
                // sprmTDxaLeft and sprmTDxaGapHalf for binary doc.
                // It appears that there is no rtf equivalent (97-mode attributes are used).
                widthBefore = getWidthBeforeFromDxaDocRtfAttributes(row.getTablePr());
                // The logic that falls back here in absence of SprmCode.TWidthBefore looks kind of strange,
                // but actually in the DOC specs TWidthBefore value may indicate that TWidthBefore width value should be ignored.
                // So in case that TWidthBefore is ignored (or absent),
                // it is logical that space before row in a jagged table is specified elsewhere.
                break;
            default:
                throw new IllegalStateException("Unknown widthBefore getting method.");
        }
        return widthBefore;
    }

    /// <summary>
    /// Gets widthBefore in twips from <see cref="TableAttr.Sys_DxaLeft"/> and <see cref="TableAttr.Sys_DxaGapHalf"/>
    /// </summary>
    private static int getWidthBeforeFromDxaDocRtfAttributes(TablePr tablePr)
    {
        int dxaGapHalf = (/*int*/Integer)tablePr.fetchAttr(TableAttr.SYS_DXA_GAP_HALF);
        int dxaLeft = (/*int*/Integer)tablePr.fetchAttr(TableAttr.SYS_DXA_LEFT);
        return dxaLeft - dxaGapHalf;
    }

    /// <summary>
    /// Gets widthBefore in twips from <see cref="TableAttr.WidthBefore"/> and for the value in percent units
    /// falls back to <see cref="GetWidthBeforeFromDxaDocRtfAttributes(TablePr)"/>.
    /// </summary>
    private int getWidthBeforeFromAttributes(TablePr tablePr)
    {
        PreferredWidth attrValue = (PreferredWidth)tablePr.fetchAttr(TableAttr.WIDTH_BEFORE, mRevisionsView);
        // Fall back to dxa attributes for width before in percent units.
        int widthBefore = attrValue.isPercent()
            ? getWidthBeforeFromDxaDocRtfAttributes(tablePr)
            : attrValue.getValueTwipsSafe();
        return widthBefore;
    }

    /// <summary>
    /// Call this when you visit a row. Updates <see cref="Before"/>, <see cref="GridBefore"/>,
    /// <see cref="After"/> and <see cref="GridAfter"/> with values for the current row.
    /// </summary>
    void nextRow(Row row)
    {
        int columnIndex = 0;
        mCellIndex = 0;

        // Calculate before and grid before.
        int widthBefore = getWidthBeforeRow(row);
        // Do not remove gridBefore common for all rows for the "old" grid algorithm to preserve the current behavior.
        // Test21928NotApplied().
        if (getWidthBeforeMethod() != WidthBeforeType.ATTR_REVISION_AWARE)
            widthBefore -= getLeftmostRowPosition();

        int indent = 0;
        while ((indent < widthBefore) && (columnIndex < mColumnWidths.length))
        {
            indent += mColumnWidths[columnIndex];
            columnIndex++;
        }

        mBefore = widthBefore;
        mGridBefore = columnIndex;

        // Calculate grid spans.
        CellCollection cells = row.getCells();
        mGridSpans = new int[cells.getCount()];

        for (int cellIndex = 0; cellIndex < cells.getCount(); cellIndex++)
        {
            int cellWidth = (/*int*/Integer)cells.get(cellIndex).getCellPr().fetchAttr(CellAttr.WIDTH, mRevisionsView);
            int gridSpan = getGridSpan(columnIndex, cellWidth);

            mGridSpans[cellIndex] = gridSpan;
            columnIndex += gridSpan;
        }

        // Calculate after and grid after.
        PreferredWidth widthAfter = (PreferredWidth)row.getTablePr().fetchAttr(TableAttr.WIDTH_AFTER, mRevisionsView);
        mAfter = widthAfter.getValueTwipsSafe();
        mGridAfter = mColumnWidths.length - columnIndex;

        if (mGridAfter == 0)
        {
            // There are no columns after the row in the constructed grid, so there is no width after the row,
            // even if it is specified in the row properties.
            mAfter = 0;
        }
    }

    private int getGridSpan(int columnIndex, int cellWidth)
    {
        int gridSpan = 0;
        int remainingWidth = cellWidth;

        while ((remainingWidth > getWidthTolerance()) && (columnIndex < mColumnWidths.length))
        {
            remainingWidth -= mColumnWidths[columnIndex];
            columnIndex++;
            gridSpan++;
        }

        return gridSpan;
    }

    /// <summary>
    /// Widths of columns in the table grid.
    /// </summary>
    int[] getColumnWidths() { return mColumnWidths; }

    /// <summary>
    /// Call this when you visit a cell. Returns the grid span for the next cell in the current row.
    /// </summary>
    int nextCell()
    {
        return mGridSpans[mCellIndex++];
    }

    /// <summary>
    /// Indicates if the current cell is the last of the grid spans constructed for the current row.
    /// </summary>
    boolean isLastCell()
    {
        return (mGridSpans != null) && (mCellIndex >= mGridSpans.length);
    }

    /// <summary>
    /// Count of grid columns before the first cell in the current row.
    /// </summary>
    int getGridBefore() { return mGridBefore; }

    /// <summary>
    /// Width before the first cell in the current row.
    /// </summary>
    int getBefore() { return mBefore; }

    /// <summary>
    /// Count of grid columns after the last cell in the current row.
    /// </summary>
    int getGridAfter() { return mGridAfter; }

    /// <summary>
    /// Width after the last cell in the current row.
    /// </summary>
    int getAfter() { return mAfter; }

    /// <summary>
    /// Creates a new IntList instance and populates it with the values from ColumnWidths.
    /// </summary>
    IntList toIntList()
    {
        IntList widthList = new IntList(getColumnWidths().length);
        for (int column : getColumnWidths())
            widthList.add(column);

        return widthList;
    }

    // *** These fields are calculated when the grid is created and remain unchanged until the grid is discarded.
    private int[] mColumnWidths;

    // *** This field is used when processing a single cell.
    /// <summary>
    /// Current cell index.
    /// </summary>
    private int mCellIndex;

    // *** These fields are used when processing one row.
    /// <summary>
    /// Current row grid spans. One item per cell node.
    /// </summary>
    private int[] mGridSpans;
    private int mGridBefore;
    private int mBefore;
    private int mGridAfter;
    private int mAfter;

    /// <summary>
    /// Controls should table grid builder get original (before-changes) or final (after-changes) properties.
    /// </summary>
    private /*final*/ /*RevisionsView*/int mRevisionsView;

    static int getWidthTolerance() { return 1; }

    /// <summary>
    /// Default width of a grid column, in twips.
    /// It is the same default MS Word uses when loading a document having cell width not specified in a fixed layout table.
    /// </summary>
    static final int DEFAULT_FIXED_COLUMN_WIDTH_TWIPS = 360;

    /// <summary>
    /// Gets or sets leftmost row position.
    /// </summary>
    /// <remarks>
    /// It may happen that all rows in the table are shifted right,
    /// according to the attributes used to construct the grid.
    /// Space before the leftmost row is not needed in the grid, so this value is used to exclude it.
    /// </remarks>
    private int getLeftmostRowPosition() { return mLeftmostRowPosition; }; private void setLeftmostRowPosition(int value) { mLeftmostRowPosition = value; };

    private int mLeftmostRowPosition;

    /// <summary>
    /// Represents the method of getting width before from row attributes.
    /// </summary>
    private /*enum*/ final class WidthBeforeType
    {
        private WidthBeforeType(){}
        
        /// <summary>
        /// This is the original methods used by the old algorithm
        /// that uses TableAttr.WidthBefore taken from an appropriate revisions view.
        /// </summary>
        public static final int ATTR_REVISION_AWARE = 0;

        // The methods below are used by the new algorithm.
        // Revisions are not handled yet.

        /// <summary>
        /// Use the value computed by doc/rtf readers from older Word97 indent attributes.
        /// </summary>
        public static final int FROM_WORD_97_ATTRIBUTES = 1;

        /// <summary>
        /// Use the value currently stored in <see cref="TableAttr.WidthBefore"/>.
        /// </summary>
        public static final int ATTR_DIRECT = 2;

        /// <summary>
        /// Compute the value from row attributes specific to doc/rtf.
        /// </summary>
        public static final int FROM_DXA_DOC_RTF_ATTRIBUTES = 3;

        public static final int length = 4;
    }

    /// <summary>
    /// Stores the method of getting width before value from row attributes.
    /// </summary>
    private /*WidthBeforeType*/int getWidthBeforeMethod() { return mWidthBeforeMethod; };

    private /*WidthBeforeType*/int mWidthBeforeMethod;
}

