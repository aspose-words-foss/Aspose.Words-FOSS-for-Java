// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.ms;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.Map;
import java.util.HashMap;
import com.aspose.MathUtil;
import com.aspose.Debug;
import com.aspose.ArrayUtil;
import com.aspose.DrColor;
import java.util.ArrayList;
import com.aspose.words.ref.RefInt;
import com.aspose.words.ref.Ref;


/// <summary>
/// Implements the DATABASE field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Inserts the results of a database query into a WordprocessingML table.
/// </remarks>
public class FieldDatabase extends Field implements IFieldCodeTokenInfoProvider
{

    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        IFieldDatabaseProvider provider = fetchDocument().getFieldOptions().getFieldDatabaseProvider();
        if (provider == null)
            return null;

        FieldDatabaseDataTable queryResult = provider.getQueryResult(getFileName(), getConnection(), getQuery(), this);

        if (queryResult == null)
            return new FieldUpdateActionInsertErrorMessage(this, "Error! Cannot open data source.");

        Table table = buildTable(queryResult);
        return table != null
            ? new FieldUpdateActionApplyResult(this, new NodeRangeFieldResult(new NodeRange(table, table)), false)
            : new FieldUpdateActionApplyResult(this, "");
    }

    private Table buildTable(FieldDatabaseDataTable dataTable) throws Exception
    {
        dataTable = trimRecords(dataTable);
        if (dataTable.getRows().size() == 0)
            return null;

        Table table = new Table(getDocument());

        if (getInsertHeadings())
            insertHeadingRow(dataTable, table);

        for (FieldDatabaseDataRow row : dataTable.getRows())
            insertDataRow(row, table);

        formatTable(table);

        return table;
    }

    private FieldDatabaseDataTable trimRecords(FieldDatabaseDataTable dataTable)
    {
        int firstRecord = FormatterPal.tryParseInt(getFirstRecord());
        int lastRecord = FormatterPal.tryParseInt(getLastRecord());
        return FieldDatabaseUtils.trimRecords(dataTable, firstRecord, lastRecord);
    }

    private static void insertHeadingRow(FieldDatabaseDataTable dataTable, Table table)
    {
        insertRow(table, dataTable.getColumnNames());
    }

    private static void insertDataRow(FieldDatabaseDataRow row, Table table)
    {
        insertRow(table, row.getValues());
    }

    private static void insertRow(Table table, String[] cellTexts)
    {
        Row row = new Row(table.getDocument());
        table.getRows().add(row);
        for (String cellText : cellTexts)
        {
            Cell cell = new Cell(table.getDocument());
            row.getCells().add(cell);
            Paragraph paragraph = new Paragraph(cell.getDocument());
            cell.getParagraphs().add(paragraph);
            paragraph.getRuns().add(new Run(cell.getDocument(), (cellText != null ? cellText : "")));
        }
    }

    private void formatTable(Table table) throws Exception
    {
        /*FieldDatabaseTableFormat*/int tableFormat = getTableFormatCore();
        /*FieldDatabaseFormatAttributes*/int attributes = getFormatAttributesCore();

        TableStyle style = null;
        if (attributes != FieldDatabaseFormatAttributes.NONE)
        {
            /*StyleIdentifier*/int styleIdentifier = 0;
            RefInt referenceToStyleIdentifier = new RefInt(styleIdentifier);
            boolean outRefCondition0 = msDictionary.tryGetValue(G_TABLE_FORMAT_TO_STYLE_MAP, tableFormat, /*out*/ referenceToStyleIdentifier);
            styleIdentifier = referenceToStyleIdentifier.get();
            if (outRefCondition0)
                style = (TableStyle)getDocument().getStyles().getBySti(styleIdentifier, true);
        }

        FieldDatabaseTableStyleApplier.apply(table, style, tableFormat, attributes);
    }

    /// <summary>
    /// Gets or sets which attributes of the format are to be applied to the table.
    /// </summary>
    public String getFormatAttributes() { return getFieldCodeCache().getSwitchArgumentAsString(FORMAT_ATTRIBUTES_SWITCH); }
    public void setFormatAttributes(String value) throws Exception { getFieldCodeCache().setSwitchAsInt32(FORMAT_ATTRIBUTES_SWITCH, value); }

    /// <summary>
    /// Gets or sets a connection to the data.
    /// </summary>
    public String getConnection() { return getFieldCodeCache().getSwitchArgumentAsString(CONNECTION_SWITCH); }
    public void setConnection(String value) throws Exception { getFieldCodeCache().setSwitch(CONNECTION_SWITCH, value); }

    /// <summary>
    /// Gets or sets the complete path and file name of the database
    /// </summary>
    public String getFileName() { return getFieldCodeCache().getSwitchArgumentAsString(FILE_NAME_SWITCH); }
    public void setFileName(String value) throws Exception { getFieldCodeCache().setSwitch(FILE_NAME_SWITCH, value); }

    /// <summary>
    /// Gets or sets the integral record number of the first data record to insert.
    /// </summary>
    public String getFirstRecord() { return getFieldCodeCache().getSwitchArgumentAsString(FIRST_RECORD_SWITCH); }
    public void setFirstRecord(String value) throws Exception { getFieldCodeCache().setSwitchAsInt32(FIRST_RECORD_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to insert the field names from the database as column headings in
    /// the resulting table.
    /// </summary>
    public boolean getInsertHeadings() { return getFieldCodeCache().hasSwitch(INSERT_HEADINGS_SWITCH); }
    public void setInsertHeadings(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_HEADINGS_SWITCH, value); }

    /// <summary>
    /// Gets or sets the format that is to be applied to the result of the database query.
    /// </summary>
    public String getTableFormat() { return getFieldCodeCache().getSwitchArgumentAsString(TABLE_FORMAT_SWITCH); }
    public void setTableFormat(String value) throws Exception { getFieldCodeCache().setSwitchAsInt32(TABLE_FORMAT_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to insert data at the beginning of a merge.
    /// </summary>
    public boolean getInsertOnceOnMailMerge() { return getFieldCodeCache().hasSwitch(INSERT_ONCE_ON_MAIL_MERGE_SWITCH); }
    public void setInsertOnceOnMailMerge(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_ONCE_ON_MAIL_MERGE_SWITCH, value); }

    /// <summary>
    /// Gets or sets a set of SQL instructions that query the database.
    /// </summary>
    public String getQuery() { return getFieldCodeCache().getSwitchArgumentAsString(QUERY_SWITCH); }
    public void setQuery(String value) throws Exception { getFieldCodeCache().setSwitch(QUERY_SWITCH, value); }

    /// <summary>
    /// Gets or sets the integral record number of the last data record to insert.
    /// </summary>
    public String getLastRecord() { return getFieldCodeCache().getSwitchArgumentAsString(LAST_RECORD_SWITCH); }
    public void setLastRecord(String value) throws Exception { getFieldCodeCache().setSwitchAsInt32(LAST_RECORD_SWITCH, value); }

    private /*FieldDatabaseFormatAttributes*/int getFormatAttributesCore()
    {
        int formatAttributes = FormatterPal.tryParseInt(getFormatAttributes());
        return formatAttributes == Integer.MIN_VALUE
            ? FieldDatabaseFormatAttributes.NONE
            : (/*FieldDatabaseFormatAttributes*/int)formatAttributes;
    }

    private /*FieldDatabaseTableFormat*/int getTableFormatCore()
    {
        int tableFormat = FormatterPal.tryParseInt(getTableFormat());
        return tableFormat == Integer.MIN_VALUE
            ? FieldDatabaseTableFormat.NONE
            : (/*FieldDatabaseTableFormat*/int)tableFormat;
    }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*INSERT_HEADINGS_SWITCH*/0:
            case /*INSERT_ONCE_ON_MAIL_MERGE_SWITCH*/1:
            {
                return FieldSwitchType.FLAG;
            }
            case /*FORMAT_ATTRIBUTES_SWITCH*/2:
            case /*CONNECTION_SWITCH*/3:
            case /*FILE_NAME_SWITCH*/4:
            case /*FIRST_RECORD_SWITCH*/5:
            case /*TABLE_FORMAT_SWITCH*/6:
            case /*QUERY_SWITCH*/7:
            case /*LAST_RECORD_SWITCH*/8:
            {
                return FieldSwitchType.HAS_ARGUMENT;
            }
            default:
            {
                return FieldSwitchType.UNKNOWN;
            }
        }
    }

    private static final String FORMAT_ATTRIBUTES_SWITCH = "\\b";
    private static final String CONNECTION_SWITCH = "\\c";
    private static final String FILE_NAME_SWITCH = "\\d";
    private static final String FIRST_RECORD_SWITCH = "\\f";
    private static final String INSERT_HEADINGS_SWITCH = "\\h";
    private static final String TABLE_FORMAT_SWITCH = "\\l";
    private static final String INSERT_ONCE_ON_MAIL_MERGE_SWITCH = "\\o";
    private static final String QUERY_SWITCH = "\\s";
    private static final String LAST_RECORD_SWITCH = "\\t";

    private static /*final*/ Map<Integer, Integer> G_TABLE_FORMAT_TO_STYLE_MAP = new HashMap<Integer, Integer>();

    private static class FieldDatabaseTableStyleApplier
    {

        static void apply(Table table, TableStyle style, /*FieldDatabaseTableFormat*/int tableFormat, /*FieldDatabaseFormatAttributes*/int specifiedFormats) throws Exception
        {
            if ((tableFormat == FieldDatabaseTableFormat.NONE)
                || isFormatSpecified(specifiedFormats, FieldDatabaseFormatAttributes.BORDERS)
                || isFormatSpecified(specifiedFormats, FieldDatabaseFormatAttributes.SHADING))
                clearTableStyle(table);

            if (isFormatSpecified(specifiedFormats, FieldDatabaseFormatAttributes.AUTO_FIT))
            {
                table.setPreferredWidth(PreferredWidth.AUTO);
                table.setAllowAutoFit(true);
            }
            else
            {
                table.setPreferredWidth(PreferredWidth.fromPercent(100.0));
                table.setAllowAutoFit(false);
            }

            if (style == null)
                return;

            table.getTablePr().setAttr(TableAttr.STYLE_OPTIONS, (int)specifiedFormats);
            table.setAttrOnAllRows(TableAttr.STYLE_OPTIONS, (int)specifiedFormats);

            copyTableStyle(table, style, tableFormat, specifiedFormats);
        }

        private static void clearTableStyle(Table table)
        {
            table.getTablePr().clear();
            for (Row row : (Iterable<Row>) table.getRows())
            {
                row.getTablePr().clear();
                for (Cell cell : (Iterable<Cell>) row.getCells())
                {
                    cell.getCellPr().clear();
                    for (Paragraph paragraph : (Iterable<Paragraph>) cell.getParagraphs())
                    {
                        paragraph.getParaPr().clear();
                        for (Run run : (Iterable<Run>) paragraph.getRuns())
                            run.getRunPr().clear();
                    }
                }
            }
        }

        private static void copyTableStyle(Table table, TableStyle style, /*FieldDatabaseTableFormat*/int tableFormat, /*FieldDatabaseFormatAttributes*/int specifiedFormats)
        {
            copyTableAttrs(new TableStyleAdapter(style), table, specifiedFormats);

            /*ConditionalStyleType*/int[] tableStyleOverrideTypeOrder = getTableStyleOverrideTypeOrder(tableFormat);

            for (/*ConditionalStyleType*/int tableStyleOverrideType : tableStyleOverrideTypeOrder)
            {
                ConditionalStyle conditionalStyle = style.getConditionalStyles().getByConditionalStyleType(tableStyleOverrideType);
                if (!conditionalStyle.hasFormatting())
                    continue;

                ITableStyle tableStyle = new ConditionalStyleAdapter(conditionalStyle);

                switch (tableStyleOverrideType)
                {
                    case ConditionalStyleType.FIRST_COLUMN:
                    {
                        if (!isFormatSpecified(specifiedFormats, FieldDatabaseFormatAttributes.FIRST_COLUMN))
                            break;

                        for (Row row : (Iterable<Row>) table.getRows())
                        {
                            int[] keysToIgnore = !MathUtil.isOdd(row.getRowIndex()) && ignoreFirstColumnEvenCellRightBorder(tableFormat)
                                ? new int[] { CellAttr.BORDER_RIGHT }
                                : new int[0];
                            copyCellAttrs(tableStyle, row.getFirstCell(), specifiedFormats, keysToIgnore);
                        }
                        break;
                    }
                    case ConditionalStyleType.LAST_COLUMN:
                    {
                        if (!isFormatSpecified(specifiedFormats, FieldDatabaseFormatAttributes.LAST_COLUMN))
                            break;

                        for (Row row : (Iterable<Row>) table.getRows())
                            copyCellAttrs(tableStyle, row.getLastCell(), specifiedFormats);
                        break;
                    }
                    case ConditionalStyleType.FIRST_ROW:
                    {
                        if (!isFormatSpecified(specifiedFormats, FieldDatabaseFormatAttributes.FIRST_ROW))
                            break;

                        copyRowAttrs(tableStyle, table.getFirstRow(), specifiedFormats);
                        break;
                    }
                    case ConditionalStyleType.LAST_ROW:
                    {
                        if (!isFormatSpecified(specifiedFormats, FieldDatabaseFormatAttributes.LAST_ROW))
                            break;

                        copyRowAttrs(tableStyle, table.getLastRow(), specifiedFormats);
                        break;
                    }

                    case ConditionalStyleType.TOP_LEFT_CELL:
                    {
                        if (!isFormatSpecified(specifiedFormats, getCornerFormatAttribute(tableFormat, tableStyleOverrideType)))
                            break;

                        copyCellAttrs(tableStyle, table.getFirstRow().getFirstCell(), specifiedFormats);
                        break;
                    }
                    case ConditionalStyleType.TOP_RIGHT_CELL:
                    {
                        if (!isFormatSpecified(specifiedFormats, getCornerFormatAttribute(tableFormat, tableStyleOverrideType)))
                            break;

                        copyCellAttrs(tableStyle, table.getFirstRow().getLastCell(), specifiedFormats);
                        break;
                    }
                    case ConditionalStyleType.BOTTOM_LEFT_CELL:
                    {
                        if (!isFormatSpecified(specifiedFormats, getCornerFormatAttribute(tableFormat, tableStyleOverrideType)))
                            break;

                        copyCellAttrs(tableStyle, table.getLastRow().getFirstCell(), specifiedFormats);
                        break;
                    }
                    case ConditionalStyleType.BOTTOM_RIGHT_CELL:
                    {
                        if (!isFormatSpecified(specifiedFormats, getCornerFormatAttribute(tableFormat, tableStyleOverrideType)))
                            break;

                        copyCellAttrs(tableStyle, table.getLastRow().getLastCell(), specifiedFormats);
                        break;
                    }

                    case ConditionalStyleType.ODD_ROW_BANDING:
                    {
                        int bandSize = style.getTablePr().getStyleRowBandSize();
                        int rowsCount = table.getRows().getCount();
                        for (int rowIndex = 0; rowIndex < rowsCount; rowIndex++)
                            if (isRowBanded(rowIndex, bandSize, true, rowsCount, specifiedFormats, tableFormat))
                                copyRowAttrs(tableStyle, table.getRows().get(rowIndex), specifiedFormats);
                        break;
                    }
                    case ConditionalStyleType.EVEN_ROW_BANDING:
                    {
                        int bandSize = style.getTablePr().getStyleRowBandSize();
                        int rowsCount = table.getRows().getCount();
                        for (int rowIndex = 0; rowIndex < rowsCount; rowIndex++)
                            if (isRowBanded(rowIndex, bandSize, false, rowsCount, specifiedFormats, tableFormat))
                                copyRowAttrs(tableStyle, table.getRows().get(rowIndex), specifiedFormats);
                        break;
                    }
                    case ConditionalStyleType.ODD_COLUMN_BANDING:
                    {
                        for (Row row : (Iterable<Row>) table.getRows())
                            for (int cellIndex = 0; cellIndex < row.getCells().getCount(); cellIndex += 2)
                                copyCellAttrs(tableStyle, row.getCells().get(cellIndex), specifiedFormats);
                        break;
                    }
                    case ConditionalStyleType.EVEN_COLUMN_BANDING:
                    {
                        for (Row row : (Iterable<Row>) table.getRows())
                            for (int cellIndex = 1; cellIndex < row.getCells().getCount(); cellIndex += 2)
                                copyCellAttrs(tableStyle, row.getCells().get(cellIndex), specifiedFormats);
                        break;
                    }
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }

        private static boolean ignoreFirstColumnEvenCellRightBorder(/*FieldDatabaseTableFormat*/int tableFormat)
        {
            switch (tableFormat)
            {
                case FieldDatabaseTableFormat.THREE_DIMENSIONAL_FX_2:
                case FieldDatabaseTableFormat.THREE_DIMENSIONAL_FX_3:
                    return true;
                default:
                    return false;
            }
        }

        private static boolean isRowBanded(int index, int bandSize, boolean odd, int rowsCount, /*FieldDatabaseFormatAttributes*/int specifiedFormats, /*FieldDatabaseTableFormat*/int tableFormat)
        {
            if ((index == rowsCount - 1) && isFormatSpecified(specifiedFormats, FieldDatabaseFormatAttributes.LAST_ROW))
            {
                switch (tableFormat)
                {
                    case FieldDatabaseTableFormat.LIST_6:
                    case FieldDatabaseTableFormat.LIST_8:
                    case FieldDatabaseTableFormat.CONTEMPORARY:
                    case FieldDatabaseTableFormat.THREE_DIMENSIONAL_FX_2:
                    case FieldDatabaseTableFormat.THREE_DIMENSIONAL_FX_3:
                        break;
                    default:
                        return false;
                }
            }

            return MathUtil.isOdd((index + bandSize - 1)/ bandSize) == odd;
        }

        private static /*ConditionalStyleType*/int[] getTableStyleOverrideTypeOrder(/*FieldDatabaseTableFormat*/int tableFormat)
        {
            switch (tableFormat)
            {
                case FieldDatabaseTableFormat.COLORFUL_2:
                case FieldDatabaseTableFormat.SUBTLE_2:
                    return G_BANDS_COLS_ROWS_CORNERS_ORDER;
                default:
                    return G_BANDS_ROWS_COLS_CORNERS_ORDER;
            }
        }

        private static /*FieldDatabaseFormatAttributes*/int getCornerFormatAttribute(/*FieldDatabaseTableFormat*/int tableFormat, /*ConditionalStyleType*/int tableStyleOverrideType)
        {
            switch (tableFormat)
            {
                case FieldDatabaseTableFormat.COLUMNS_1:
                case FieldDatabaseTableFormat.COLUMNS_2:
                case FieldDatabaseTableFormat.COLUMNS_3:
                case FieldDatabaseTableFormat.GRID_5:
                case FieldDatabaseTableFormat.GRID_6:
                case FieldDatabaseTableFormat.GRID_7:
                case FieldDatabaseTableFormat.THREE_DIMENSIONAL_FX_1:
                case FieldDatabaseTableFormat.THREE_DIMENSIONAL_FX_2:
                case FieldDatabaseTableFormat.THREE_DIMENSIONAL_FX_3:
                case FieldDatabaseTableFormat.SUBTLE_2:
                    return getColumnRelatedCornerFormatAttribute(tableStyleOverrideType);
                case FieldDatabaseTableFormat.CLASSIC_1:
                case FieldDatabaseTableFormat.CLASSIC_2:
                    return getColumnAndRowRelatedCornerFormatAttribute(tableStyleOverrideType);
                default:
                    return getRowRelatedCornerFormatAttribute(tableStyleOverrideType);
            }
        }

        private static /*FieldDatabaseFormatAttributes*/int getColumnRelatedCornerFormatAttribute(/*ConditionalStyleType*/int tableStyleOverrideType)
        {
            switch (tableStyleOverrideType)
            {
                case ConditionalStyleType.TOP_LEFT_CELL:
                case ConditionalStyleType.BOTTOM_LEFT_CELL:
                    return FieldDatabaseFormatAttributes.FIRST_COLUMN;
                case ConditionalStyleType.TOP_RIGHT_CELL:
                case ConditionalStyleType.BOTTOM_RIGHT_CELL:
                    return FieldDatabaseFormatAttributes.LAST_COLUMN;
                default:
                    throw new IllegalArgumentException();
            }
        }

        private static /*FieldDatabaseFormatAttributes*/int getRowRelatedCornerFormatAttribute(/*ConditionalStyleType*/int tableStyleOverrideType)
        {
            switch (tableStyleOverrideType)
            {
                case ConditionalStyleType.TOP_LEFT_CELL:
                case ConditionalStyleType.TOP_RIGHT_CELL:
                    return FieldDatabaseFormatAttributes.FIRST_ROW;
                case ConditionalStyleType.BOTTOM_LEFT_CELL:
                case ConditionalStyleType.BOTTOM_RIGHT_CELL:
                    return FieldDatabaseFormatAttributes.LAST_ROW;
                default:
                    throw new IllegalArgumentException();
            }
        }

        private static /*FieldDatabaseFormatAttributes*/int getColumnAndRowRelatedCornerFormatAttribute(/*ConditionalStyleType*/int tableStyleOverrideType)
        {
            switch (tableStyleOverrideType)
            {
                case ConditionalStyleType.TOP_RIGHT_CELL:
                    return FieldDatabaseFormatAttributes.LAST_COLUMN;
                case ConditionalStyleType.BOTTOM_LEFT_CELL:
                    return FieldDatabaseFormatAttributes.LAST_ROW;
                case ConditionalStyleType.TOP_LEFT_CELL:
                    return FieldDatabaseFormatAttributes.FIRST_ROW;
                case ConditionalStyleType.BOTTOM_RIGHT_CELL:
                    Debug.fail("Should not reach this case");
                    return FieldDatabaseFormatAttributes.NONE;
                default:
                    throw new IllegalArgumentException();
            }
        }

        private static void copyTableAttrs(ITableStyle style, Table table, /*FieldDatabaseFormatAttributes*/int specifiedFormats, /*params*/ int... keysToIgnore)
        {
            copyAttrs(specifiedFormats, table.getTablePr(), style, AttrPrSource.TABLE_PR, keysToIgnore);

            for (Row row : (Iterable<Row>) table.getRows())
            {
                copyAttrs(specifiedFormats, row.getTablePr(), style, AttrPrSource.TABLE_PR, keysToIgnore); // table attributes must be specified for all rows. by design.
                copyRowAttrs(style, row, specifiedFormats, keysToIgnore);
            }
        }

        private static void copyRowAttrs(ITableStyle style, Row row, /*FieldDatabaseFormatAttributes*/int specifiedFormats, /*params*/ int... keysToIgnore)
        {
            for (Cell cell : (Iterable<Cell>) row.getCells())
                copyCellAttrs(style, cell, specifiedFormats, keysToIgnore);
        }

        private static void copyCellAttrs(ITableStyle style, Cell cell, /*FieldDatabaseFormatAttributes*/int specifiedFormats, /*params*/ int... keysToIgnore)
        {
            copyAttrs(specifiedFormats, cell.getCellPr(), style, AttrPrSource.CELL_PR, keysToIgnore);

            for (Paragraph paragraph : (Iterable<Paragraph>) cell.getParagraphs())
                copyParagraphAttrs(style, paragraph, specifiedFormats, keysToIgnore);
        }

        private static void copyParagraphAttrs(ITableStyle style, Paragraph paragraph, /*FieldDatabaseFormatAttributes*/int specifiedFormats, /*params*/ int... keysToIgnore)
        {
            copyAttrs(specifiedFormats, paragraph.getParagraphBreakRunPr(), style, AttrPrSource.RUN_PR, keysToIgnore);

            for (Run run : (Iterable<Run>) paragraph.getRuns())
                copyRunAttrs(style, run, specifiedFormats, keysToIgnore);
        }

        private static void copyRunAttrs(ITableStyle style, Run run, /*FieldDatabaseFormatAttributes*/int specifiedFormats, /*params*/ int... keysToIgnore)
        {
            copyAttrs(specifiedFormats, run.getRunPr(), style, AttrPrSource.RUN_PR, keysToIgnore);
        }

        private static void copyAttrs(/*FieldDatabaseFormatAttributes*/int specifiedFormats, AttrCollection dest, ITableStyle style, /*AttrPrSource*/int attrPrSource, /*params*/ int... keysToIgnore)
        {
            AttrCollection source = style.getByAttrPrSource(attrPrSource);
            if (source == null)
                return;

            for (int key : G_MAPPING.getMappedAttrs(specifiedFormats, attrPrSource))
            {
                if (ArrayUtil.contains(keysToIgnore, key))
                    continue;

                Object value = source.getDirectAttr(key);
                if (value == null)
                    continue;

                if (!isFormatSpecified(specifiedFormats, FieldDatabaseFormatAttributes.COLOR))
                    value = applyGrayScale(value);

                dest.setAttr(key, value);
            }
        }

        private static Object applyGrayScale(Object value)
        {
            return (((applyGrayScale(ms.as(value, DrColor.class)) != null ? applyGrayScale(ms.as(value,DrColor.class)) : applyGrayScale(ms.as(value, Shading.class))) != null ? (applyGrayScale(ms.as(value,DrColor.class)) != null ? applyGrayScale(ms.as(value,DrColor.class)) : applyGrayScale(ms.as(value,Shading.class))) : applyGrayScale(ms.as(value, Border.class))) != null ? ((applyGrayScale(ms.as(value,DrColor.class)) != null ? applyGrayScale(ms.as(value,DrColor.class)) : applyGrayScale(ms.as(value,Shading.class))) != null ? (applyGrayScale(ms.as(value,DrColor.class)) != null ? applyGrayScale(ms.as(value,DrColor.class)) : applyGrayScale(ms.as(value,Shading.class))) : applyGrayScale(ms.as(value,Border.class))) : value);
        }

        private static DrColor applyGrayScale(DrColor value)
        {
            if (value == null)
                return null;

            if (value.isGray())
                return value;

            return isLight(value)
                ? new DrColor(value.getA(), LIGHT, LIGHT, LIGHT)
                : new DrColor(value.getA(), DARK, DARK, DARK);
        }

        private static Shading applyGrayScale(Shading value)
        {
            if (value == null)
                return null;

            if (value.getTexture() == TextureIndex.TEXTURE_SOLID)
            {
                if (isBlackOrWhite(value.getBackgroundPatternColorInternal()) && isBlackOrWhite(value.getForegroundPatternColorInternal()))
                    return value;

                Shading result = value.deepClone();

                result.setTexture(isLight(result.getBackgroundPatternColorInternal()) && isLight(result.getForegroundPatternColorInternal())
                    ? TextureIndex.TEXTURE_30_PERCENT
                    : TextureIndex.TEXTURE_60_PERCENT);

                if (!isBlackOrWhite(result.getBackgroundPatternColorInternal()))
                    result.setBackgroundPatternColorInternal(DrColor.BLACK);

                if (!isBlackOrWhite(result.getForegroundPatternColorInternal()))
                    result.setForegroundPatternColorInternal(DrColor.BLACK);

                return result;
            }
            else
            {
                if (value.getBackgroundPatternColorInternal().isGray() &&
                    value.getForegroundPatternColorInternal().isGray())
                    return value;

                Shading result = value.deepClone();

                result.setBackgroundPatternColorInternal(applyGrayScale(result.getBackgroundPatternColorInternal()));
                result.setForegroundPatternColorInternal(applyGrayScale(result.getForegroundPatternColorInternal()));

                return result;
            }
        }

        private static Border applyGrayScale(Border value)
        {
            if (value == null)
                return null;

            if (value.getColorInternal().isGray())
                return value;

            Border result = value.deepClone();
            result.setColorInternal(applyGrayScale(result.getColorInternal()));
            return result;
        }

        private static boolean isBlackOrWhite(DrColor color)
        {
            return DrColor.equals(color, DrColor.BLACK) || DrColor.equals(color, DrColor.WHITE);
        }

        private static boolean isLight(DrColor color)
        {
            return color.getR() >= LIGHT || color.getG() >= LIGHT || color.getB() >= LIGHT;
        }

        private static boolean isFormatSpecified(/*FieldDatabaseFormatAttributes*/int specifiedFormats, /*FieldDatabaseFormatAttributes*/int format)
        {
            return (specifiedFormats & format) == format;
        }

        private static boolean areAllFormatsSpecified(/*FieldDatabaseFormatAttributes*/int specifiedFormats, /*FieldDatabaseFormatAttributes*/int[] formats)
        {
            for (int i : formats)
            {
                /*FieldDatabaseFormatAttributes*/int format = (/*FieldDatabaseFormatAttributes*/int)i;
                if (!isFormatSpecified(specifiedFormats, format))
                    return false;
            }

            return true;
        }

        private static final int LIGHT = 192;
        private static final int DARK = 128;

        private static /*final*/ StyleMapping G_MAPPING = new StyleMapping();

        private static /*final*/ /*ConditionalStyleType*/int[] G_BANDS_ROWS_COLS_CORNERS_ORDER;
        private static /*final*/ /*ConditionalStyleType*/int[] G_BANDS_COLS_ROWS_CORNERS_ORDER;

        private /*enum*/ final class AttrPrSource
        {
            private AttrPrSource(){}
            
            public static final int TABLE_PR = 0;
            public static final int CELL_PR = 1;
            public static final int RUN_PR = 2;

            public static final int length = 3;
        }

        private static class StyleMapping
        {
            Iterable<Integer> getMappedAttrs(/*FieldDatabaseFormatAttributes*/int specifiedFormats, /*AttrPrSource*/int attrPrSource)
            {
                ArrayList<Integer> result = new ArrayList<Integer>();

                Map<int[], ArrayList<Integer>> attrPrSourceMapping = getAttrPrSourceMapping(attrPrSource);
                for (/*FieldDatabaseFormatAttributes*/int[] formats : (Iterable</*FieldDatabaseFormatAttributes*/int[]>) attrPrSourceMapping.keySet())
                {
                    if (!areAllFormatsSpecified(specifiedFormats, formats))
                        continue;

                    for (int key : attrPrSourceMapping.get(formats))
                        result.add(key);
                }

                return result;
            }

            StyleMapping whenFormatsAreSpecified(/*params*/ /*FieldDatabaseFormatAttributes*/int... formats)
            {
                mLastFormats = formats;
                return this;
            }

            StyleMapping thenCopyTableAttrs(/*params*/ int... keys)
            {
                return mapAttrs(AttrPrSource.TABLE_PR, keys);
            }

            StyleMapping thenCopyCellAttrs(/*params*/ int... keys)
            {
                return mapAttrs(AttrPrSource.CELL_PR, keys);
            }

            StyleMapping thenCopyRunAttrs(/*params*/ int... keys)
            {
                return mapAttrs(AttrPrSource.RUN_PR, keys);
            }

            private StyleMapping mapAttrs(/*AttrPrSource*/int attrPrSource, int[] keys)
            {
                Map<int[], ArrayList<Integer>> attrPrSourceMapping = getAttrPrSourceMapping(attrPrSource);

                ArrayList<Integer> mappedKeys = null;
                Ref<ArrayList<Integer>> referenceToMappedKeys = new Ref<ArrayList<Integer>>(mappedKeys);
                boolean outRefCondition1 = !msDictionary.tryGetValue(attrPrSourceMapping, mLastFormats, /*out*/ referenceToMappedKeys);
                mappedKeys = referenceToMappedKeys.get();
                if (outRefCondition1)
                {
                    mappedKeys = new ArrayList<Integer>();
                    msDictionary.add(attrPrSourceMapping, mLastFormats, mappedKeys);
                }

                for (int key : keys)
                    mappedKeys.add(key);

                return this;
            }

            private Map<int[], ArrayList<Integer>> getAttrPrSourceMapping(/*AttrPrSource*/int attrPrSource)
            {
                switch (attrPrSource)
                {
                    case AttrPrSource.TABLE_PR:
                        return mTableAttrsMapping;
                    case AttrPrSource.CELL_PR:
                        return mCellAttrsMapping;
                    case AttrPrSource.RUN_PR:
                        return mRunAttrsMapping;
                    default:
                        throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "attrPrSource");
                }
            }

            private /*FieldDatabaseFormatAttributes*/int[] mLastFormats;

            private /*final*/ Map<int[], ArrayList<Integer>> mTableAttrsMapping = new HashMap<int[], ArrayList<Integer>>();
            private /*final*/ Map<int[], ArrayList<Integer>> mRunAttrsMapping = new HashMap<int[], ArrayList<Integer>>();
            private /*final*/ Map<int[], ArrayList<Integer>> mCellAttrsMapping = new HashMap<int[], ArrayList<Integer>>();
        }

        private interface ITableStyle
        {
            public AttrCollection getByAttrPrSource(/*AttrPrSource*/int attrPrSource);
        }

        private static class TableStyleAdapter implements ITableStyle
        {
            private /*final*/ TableStyle mTableStyle;

            TableStyleAdapter(TableStyle tableStyle)
            {
                mTableStyle = tableStyle;
            }

            public AttrCollection /*ITableStyle.*/getByAttrPrSource(/*AttrPrSource*/int attrPrSource)
            {
                switch (attrPrSource)
                {
                    case AttrPrSource.TABLE_PR:
                        return mTableStyle.getTablePr();
                    case AttrPrSource.CELL_PR:
                        return mTableStyle.getCellPr();
                    case AttrPrSource.RUN_PR:
                        return mTableStyle.getRunPr();
                    default:
                        throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "attrPrSource");
                }
            }
        }

        private static class ConditionalStyleAdapter implements ITableStyle
        {
            private /*final*/ ConditionalStyle mConditionalStyle;

            ConditionalStyleAdapter(ConditionalStyle conditionalStyle)
            {
                mConditionalStyle = conditionalStyle;
            }

            public AttrCollection /*ITableStyle.*/getByAttrPrSource(/*AttrPrSource*/int attrPrSource)
            {
                switch (attrPrSource)
                {
                    case AttrPrSource.TABLE_PR:
                        return mConditionalStyle.getTablePr();
                    case AttrPrSource.CELL_PR:
                        return mConditionalStyle.getCellPr();
                    case AttrPrSource.RUN_PR:
                        return mConditionalStyle.getRunPr();
                    default:
                        throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "attrPrSource");
                }
            }
        }
        static/* FieldDatabaseTableStyleApplier()*/
        {
            G_MAPPING.whenFormatsAreSpecified((int)FieldDatabaseFormatAttributes.BORDERS)
                .thenCopyTableAttrs(TableAttr.BORDER_TOP, TableAttr.BORDER_LEFT, TableAttr.BORDER_BOTTOM, TableAttr.BORDER_RIGHT, TableAttr.BORDER_HORIZONTAL, TableAttr.BORDER_VERTICAL)
                .thenCopyTableAttrs(TableAttr.LEFT_PADDING, TableAttr.TOP_PADDING, TableAttr.BOTTOM_PADDING, TableAttr.RIGHT_PADDING)
                .thenCopyTableAttrs(TableAttr.LEFT_INDENT)
                .thenCopyTableAttrs(TableAttr.CELL_SPACING)
                .thenCopyCellAttrs(CellAttr.BORDER_TOP, CellAttr.BORDER_LEFT, CellAttr.BORDER_BOTTOM, CellAttr.BORDER_RIGHT, CellAttr.BORDER_HORIZONTAL, CellAttr.BORDER_VERTICAL)
                .thenCopyCellAttrs(CellAttr.BORDER_DIAGONAL_DOWN, CellAttr.BORDER_DIAGONAL_UP);

            G_MAPPING.whenFormatsAreSpecified((int)FieldDatabaseFormatAttributes.SHADING)
                .thenCopyCellAttrs(CellAttr.SHADING);

            G_MAPPING.whenFormatsAreSpecified((int)FieldDatabaseFormatAttributes.FONT)
                .thenCopyRunAttrs(FontAttr.BOLD, FontAttr.ITALIC, FontAttr.BOLD_BI, FontAttr.ITALIC_BI, FontAttr.ALL_CAPS);

            G_MAPPING.whenFormatsAreSpecified((int)FieldDatabaseFormatAttributes.SHADING, (int)FieldDatabaseFormatAttributes.FONT)
                .thenCopyRunAttrs(FontAttr.COLOR);

            G_BANDS_ROWS_COLS_CORNERS_ORDER = new /*ConditionalStyleType*/int[]
            {
                ConditionalStyleType.ODD_ROW_BANDING,
                ConditionalStyleType.EVEN_ROW_BANDING,
                ConditionalStyleType.ODD_COLUMN_BANDING,
                ConditionalStyleType.EVEN_COLUMN_BANDING,

                ConditionalStyleType.FIRST_ROW,
                ConditionalStyleType.LAST_ROW,
                ConditionalStyleType.FIRST_COLUMN,
                ConditionalStyleType.LAST_COLUMN,

                ConditionalStyleType.TOP_LEFT_CELL,
                ConditionalStyleType.TOP_RIGHT_CELL,
                ConditionalStyleType.BOTTOM_LEFT_CELL,
                ConditionalStyleType.BOTTOM_RIGHT_CELL
            };

            G_BANDS_COLS_ROWS_CORNERS_ORDER = new /*ConditionalStyleType*/int[]
            {
                ConditionalStyleType.ODD_ROW_BANDING,
                ConditionalStyleType.EVEN_ROW_BANDING,
                ConditionalStyleType.ODD_COLUMN_BANDING,
                ConditionalStyleType.EVEN_COLUMN_BANDING,

                ConditionalStyleType.FIRST_COLUMN,
                ConditionalStyleType.LAST_COLUMN,
                ConditionalStyleType.FIRST_ROW,
                ConditionalStyleType.LAST_ROW,

                ConditionalStyleType.TOP_LEFT_CELL,
                ConditionalStyleType.TOP_RIGHT_CELL,
                ConditionalStyleType.BOTTOM_LEFT_CELL,
                ConditionalStyleType.BOTTOM_RIGHT_CELL
            };
        }
    }
    static/* FieldDatabase()*/
    {
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.SIMPLE_1, StyleIdentifier.TABLE_SIMPLE_1);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.SIMPLE_2, StyleIdentifier.TABLE_SIMPLE_2);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.SIMPLE_3, StyleIdentifier.TABLE_SIMPLE_3);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.CLASSIC_1, StyleIdentifier.TABLE_CLASSIC_1);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.CLASSIC_2, StyleIdentifier.TABLE_CLASSIC_2);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.CLASSIC_3, StyleIdentifier.TABLE_CLASSIC_3);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.CLASSIC_4, StyleIdentifier.TABLE_CLASSIC_4);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.COLORFUL_1, StyleIdentifier.TABLE_COLORFUL_1);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.COLORFUL_2, StyleIdentifier.TABLE_COLORFUL_2);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.COLORFUL_3, StyleIdentifier.TABLE_COLORFUL_3);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.COLUMNS_1, StyleIdentifier.TABLE_COLUMNS_1);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.COLUMNS_2, StyleIdentifier.TABLE_COLUMNS_2);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.COLUMNS_3, StyleIdentifier.TABLE_COLUMNS_3);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.COLUMNS_4, StyleIdentifier.TABLE_COLUMNS_4);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.COLUMNS_5, StyleIdentifier.TABLE_COLUMNS_5);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.GRID_1, StyleIdentifier.TABLE_GRID_1);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.GRID_2, StyleIdentifier.TABLE_GRID_2);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.GRID_3, StyleIdentifier.TABLE_GRID_3);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.GRID_4, StyleIdentifier.TABLE_GRID_4);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.GRID_5, StyleIdentifier.TABLE_GRID_5);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.GRID_6, StyleIdentifier.TABLE_GRID_6);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.GRID_7, StyleIdentifier.TABLE_GRID_7);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.GRID_8, StyleIdentifier.TABLE_GRID_8);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.LIST_1, StyleIdentifier.TABLE_LIST_1);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.LIST_2, StyleIdentifier.TABLE_LIST_2);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.LIST_3, StyleIdentifier.TABLE_LIST_3);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.LIST_4, StyleIdentifier.TABLE_LIST_4);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.LIST_5, StyleIdentifier.TABLE_LIST_5);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.LIST_6, StyleIdentifier.TABLE_LIST_6);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.LIST_7, StyleIdentifier.TABLE_LIST_7);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.LIST_8, StyleIdentifier.TABLE_LIST_8);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.THREE_DIMENSIONAL_FX_1, StyleIdentifier.TABLE_3_D_EFFECTS_1);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.THREE_DIMENSIONAL_FX_2, StyleIdentifier.TABLE_3_D_EFFECTS_2);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.THREE_DIMENSIONAL_FX_3, StyleIdentifier.TABLE_3_D_EFFECTS_3);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.CONTEMPORARY, StyleIdentifier.TABLE_CONTEMPORARY);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.ELEGANT, StyleIdentifier.TABLE_ELEGANT);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.PROFESSIONAL, StyleIdentifier.TABLE_PROFESSIONAL);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.SUBTLE_1, StyleIdentifier.TABLE_SUBTLE_1);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.SUBTLE_2, StyleIdentifier.TABLE_SUBTLE_2);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.WEB_1, StyleIdentifier.TABLE_WEB_1);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.WEB_2, StyleIdentifier.TABLE_WEB_2);
        G_TABLE_FORMAT_TO_STYLE_MAP.put(FieldDatabaseTableFormat.WEB_3, StyleIdentifier.TABLE_WEB_3);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\h",
		"\\o",
		"\\b",
		"\\c",
		"\\d",
		"\\f",
		"\\l",
		"\\s",
		"\\t"
	);

}

