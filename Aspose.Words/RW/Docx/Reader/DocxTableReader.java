// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.IntList;



/// <summary>
/// Provides static method for reading 'w:tbl' element.
/// </summary>
/*static*/ class DocxTableReader
{
	/* Simulation of static class by using private constructor */
	private DocxTableReader()
	{}

    /// <summary>
    /// Reads 'w:tbl' element.
    /// </summary>
    static void read(NrxDocumentReaderBase reader) throws Exception
    {
        reader.addAndPushContainer(new Table(reader.getDocument()));

        TablePr tablePr = new TablePr();
        // WORDSNET-9225 This property should be 'true' by default.
        tablePr.setAllowAutoFit(true);

        Table table = (Table)reader.getCurContainer();
        reader.flushPendingMarkup(table);

        readChildren(reader, tablePr);

        reader.popContainer(NodeType.TABLE);

        // WORDSNET-27404 Perform correction of floating table alignment.
        // AM. I do not quite understand MS Word logic here and made fix as narrow as possible.
        if((tablePr.getAlignment() == TableAlignment.CENTER) && tablePr.contains(TableAttr.FRAME_LEFT))
            table.setAttrOnAllRowsNoOverride(TableAttr.HORIZONTAL_ALIGNMENT, HorizontalAlignment.CENTER);

        if (!reader.getLoadOptions().getSkipFormatting())
            reader.fixMissingTableNormalIfNeeded(table);
    }

    static void readChildren(NrxDocumentReaderBase reader, TablePr tablePr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        xmlReader.moveToElement();
        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
            readChild(reader, tablePr);
    }

    /// <summary>
    /// Refactored into a separate internal method to allow reading contents of Row level CustomXmlMarkup elements.
    /// </summary>
    static void readChild(NrxDocumentReaderBase reader, TablePr tablePr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
        {
            case /*"tblPr"*/0:
                DocxTablePrReader.readTblPr(reader, tablePr);
                break;
            case /*"tblGrid"*/1:
                if (reader.getLoadOptions().getSkipFormatting())
                {
                    xmlReader.ignoreElementNoWarn();
                    break;
                }

                // AM. If we read this element I think current container must be Table.
                NrxTableGrid.readTblGrid(xmlReader, ((Table)reader.getCurContainer()).getTablePr(), complianceInfo);
                break;
            case /*"p"*/2:
                readParagraph(reader);
                break;
            case /*"tr"*/3:
                DocxRowReader.read(reader, tablePr);
                break;
            case /*"bookmarkStart"*/4:
                DocxBookmarkReader.readStart(reader);
                break;
            case /*"bookmarkEnd"*/5:
                DocxBookmarkReader.readEnd(reader);
                break;
            case /*"commentRangeStart"*/6:
                DocxCommentsReader.readCommentRangeStart(reader);
                break;
            case /*"commentRangeEnd"*/7:
                DocxCommentsReader.readCommentRangeEnd(reader);
                break;
            case /*"permStart"*/8:
                NrxEditableRangesReader.readEditableRangeStart(reader);
                break;
            case /*"permEnd"*/9:
                NrxEditableRangesReader.readEditableRangeEnd(reader);
                break;
            case /*"customXmlDelRangeStart"*/10:
            case /*"customXmlInsRangeStart"*/11:
                DocxAnnotationReader.readCustomXmlEditRevision(reader);
                break;
            case /*"customXmlMoveFromRangeStart"*/12:
            case /*"customXmlMoveToRangeStart"*/13:
                DocxAnnotationReader.readCustomXmlMoveRevision(reader);
                break;
            case /*"customXmlDelRangeEnd"*/14:
            case /*"customXmlInsRangeEnd"*/15:
            case /*"customXmlMoveFromRangeEnd"*/16:
            case /*"customXmlMoveToRangeEnd"*/17:
                DocxAnnotationReader.clearCustomXmlRevision(reader);
                break;
            case /*"sdt"*/18:
                DocxSdtReader sdtReader = DocxReaderFactory.createRowLevelSdtReader(tablePr);
                sdtReader.read(reader);
                break;
            case /*"customXml"*/19:
                CompositeNode container = reader.getCurContainer();
                int childCount = container.getCount();

                DocxCustomXmlMarkupReader customXmlReader = DocxReaderFactory.createRowLevelCustomXmlReader(tablePr);
                customXmlReader.read(reader);

                // WORDSNET-18395 If a table contains an empty customXml element on row level, MS Word divides the table
                // into two ones and inserts a paragraph between.
                if ((container == reader.getCurContainer()) && (childCount == container.getCount()))
                {
                    Table curTable = (Table)reader.getCurContainer();

                    reader.popContainer(NodeType.TABLE);
                    reader.addAndPushContainer(new Paragraph(reader.getDocument()));
                    reader.popContainer(NodeType.PARAGRAPH);

                    Table newTable = new Table(reader.getDocument());
                    cloneTableAttributes(curTable, newTable);

                    reader.addAndPushContainer(newTable);
                }
                break;
            case /*"moveFromRangeStart"*/20:
                DocxAnnotationReader.readMoveRangeStart(reader, true);
                break;
            case /*"moveFromRangeEnd"*/21:
                DocxAnnotationReader.readMoveRangeEnd(reader, true);
                break;
            case /*"moveToRangeStart"*/22:
                DocxAnnotationReader.readMoveRangeStart(reader, false);
                break;
            case /*"moveToRangeEnd"*/23:
                DocxAnnotationReader.readMoveRangeEnd(reader, false);
                break;
            default:
                // DD: there seem to be other elements that we dont support, e.g. formulas, that require this ReadChildren.
                readChildren(reader, tablePr);
                break;
        }
    }

    /// <summary>
    /// Resiliently reads paragraph element occurred at row level.
    /// </summary>
    private static void readParagraph(NrxDocumentReaderBase reader) throws Exception
    {
        // Break current table.
        reader.popContainer(NodeType.TABLE);

        // Resiliently reads paragraph.
        DocxParaReader paraReader =
            new DocxParaReader(new DocxParaPrReader(new DocxRunPrReader(), new DocxSectPrReader()),
                new DocxInlineReader(new DocxHyperlinkReader(), new DocxSmartTagReader()));
        paraReader.read(reader);

        // And start new table.
        reader.addAndPushContainer(new Table(reader.getDocument()));
    }

    private static void cloneTableAttributes(Table srcTable, Table dstTable)
    {
        final int TABLE_GRID_KEY = TableAttr.SYS_TABLE_GRID;
        final int TABLE_GRID_KEY_2 = TableAttr.SYS_TABLE_GRID_FOR_NEW_ALGORITHM;


        // AM. This should be done by ExpandTo but TableGrid is not IComplexAttr and
        // have to be cloned manually.
        Object srcTableGrid = srcTable.getTablePr().get(TABLE_GRID_KEY);
        if (srcTableGrid != null)
        {
            IntList dstTableGrid = new IntList();
            dstTableGrid.addRange((IntList)srcTableGrid);
            dstTable.getTablePr().setAttr(TABLE_GRID_KEY, dstTableGrid);
        }

        Object srcTableGrid2 = srcTable.getTablePr().get(TABLE_GRID_KEY_2);
        if (srcTableGrid2 != null)
        {
            dstTable.getTablePr().setAttr(TABLE_GRID_KEY_2, ((IComplexAttr)srcTableGrid2).deepCloneComplexAttr());
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"tblPr",
		"tblGrid",
		"p",
		"tr",
		"bookmarkStart",
		"bookmarkEnd",
		"commentRangeStart",
		"commentRangeEnd",
		"permStart",
		"permEnd",
		"customXmlDelRangeStart",
		"customXmlInsRangeStart",
		"customXmlMoveFromRangeStart",
		"customXmlMoveToRangeStart",
		"customXmlDelRangeEnd",
		"customXmlInsRangeEnd",
		"customXmlMoveFromRangeEnd",
		"customXmlMoveToRangeEnd",
		"sdt",
		"customXml",
		"moveFromRangeStart",
		"moveFromRangeEnd",
		"moveToRangeStart",
		"moveToRangeEnd"
	);

}

