// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;


/// <summary>
/// Provides static method for parsing 'w:tr' element.
/// </summary>
/*static*/ class DocxRowReader
{
	/* Simulation of static class by using private constructor */
	private DocxRowReader()
	{}

    /// <summary>
    /// Reads 'w:tr' element.
    /// </summary>
    static void read(NrxDocumentReaderBase reader, TablePr tablePr) throws Exception
    {
        TablePr rowPr = AttrCollectionExtension.deepClone(tablePr);
        Row row = new Row(reader.getDocument(), rowPr);
        reader.addAndPushContainer(row);

        // Read attributes.
        if (!reader.getLoadOptions().getSkipFormatting())
        {
            NrxXmlReader xmlReader = reader.getXmlReader();
            while (xmlReader.moveToNextAttribute())
            {
                // The reading code below is not full because not all rsid attributes are in the model now.
                switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                {
                    case /*"rsidTr"*/0:
                    {
                        int rsid = NrxXmlUtil.tryHexToInt(xmlReader.getValue());
                        if (rsid != Integer.MIN_VALUE)
                            rowPr.setAttr(TableAttr.RSID_TR, rsid);
                        break;
                    }

                    case /*"paraId"*/1:
                        row.setParaId(NrxXmlUtil.tryHexToInt(xmlReader.getValue())); 
                        break;

                    case /*"textId"*/2:
                        row.setTextId(NrxXmlUtil.tryHexToInt(xmlReader.getValue())); 
                        break;

                    default:
                        break;
                }
            }
        }

        readChildren(reader, rowPr);
        reader.flushPendingParagraphs(row);

        // WORDSNET-19201 Flush formatting collected outside 'tr' element.
        if (reader.GlobalRowPr.getCount() > 0)
        {
            reader.GlobalRowPr.expandTo(rowPr);
            reader.GlobalRowPr.clear();
        }

        reader.popContainer(NodeType.ROW);

        if (!reader.getLoadOptions().getSkipFormatting())
            NrxTableGrid.tableGridToCellWidths(row, reader.TablesWithMissedTableGrid);
    }

    static void readChildren(NrxDocumentReaderBase reader, TablePr rowPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        xmlReader.moveToElement();
        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
            readChild(reader, rowPr);
    }

    /// <summary>
    /// Refactored into a separate internal method to allow reading contents of Row level CustomXmlMarkup elements. 
    /// </summary>
    static void readChild(NrxDocumentReaderBase reader, TablePr rowPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
        {
            case /*"tblPrEx"*/3:
                DocxTablePrReader.readTblPrEx(reader, rowPr);
                break;
            case /*"trPr"*/4:
                DocxRowPrReader.read(reader, rowPr);
                break;
            case /*"tc"*/5:
            {
                DocxReaderFactory.getCellReader().read(reader);

                // WORDSNET-24470 Flush pending paragraphs to a first occurred cell.
                if ((reader.getCurContainer() != null) && (reader.getCurContainer().getNodeType() == NodeType.ROW))
                    reader.flushPendingParagraphs(((Row)reader.getCurContainer()).getLastCell());
                break;
            }
            case /*"bookmarkStart"*/6:
                DocxBookmarkReader.readStart(reader);
                break;
            case /*"bookmarkEnd"*/7:
                DocxBookmarkReader.readEnd(reader);
                break;
            case /*"commentRangeStart"*/8:
                DocxCommentsReader.readCommentRangeStart(reader);
                break;
            case /*"commentRangeEnd"*/9:
                DocxCommentsReader.readCommentRangeEnd(reader);
                break;
            case /*"permStart"*/10:
                NrxEditableRangesReader.readEditableRangeStart(reader);
                break;
            case /*"permEnd"*/11:
                NrxEditableRangesReader.readEditableRangeEnd(reader);
                break;
            case /*"moveFromRangeStart"*/12:
                DocxAnnotationReader.readMoveRangeStart(reader, true);
                break;
            case /*"moveFromRangeEnd"*/13:
                DocxAnnotationReader.readMoveRangeEnd(reader, true);
                break;
            case /*"moveToRangeStart"*/14:
                DocxAnnotationReader.readMoveRangeStart(reader, false);
                break;
            case /*"moveToRangeEnd"*/15:
                DocxAnnotationReader.readMoveRangeEnd(reader, false);
                break;
            case /*"customXmlDelRangeStart"*/16:
            case /*"customXmlInsRangeStart"*/17:
                DocxAnnotationReader.readCustomXmlEditRevision(reader);
                break;
            case /*"customXmlMoveFromRangeStart"*/18:
            case /*"customXmlMoveToRangeStart"*/19:
                DocxAnnotationReader.readCustomXmlMoveRevision(reader);
                break;
            case /*"customXmlDelRangeEnd"*/20:
            case /*"customXmlInsRangeEnd"*/21:
            case /*"customXmlMoveFromRangeEnd"*/22:
            case /*"customXmlMoveToRangeEnd"*/23:
                DocxAnnotationReader.clearCustomXmlRevision(reader);
                break;
            case /*"sdt"*/24:
            {
                DocxSdtReader sdtReader = DocxReaderFactory.createCellLevelSdtReader(rowPr);
                sdtReader.read(reader);
                break;
            }
            case /*"customXml"*/25:
            {
                DocxCustomXmlMarkupReader customXmlReader = DocxReaderFactory.createCellLevelCustomXmlReader(rowPr);
                customXmlReader.read(reader);
                break;
            }
            case /*"p"*/26:
            {
                // Resiliency.
                readPendingParagraphs(reader);
                break;
            }
            default:
                // DD: there seem to be other elements that we dont support, e.g. formulas, that require this ReadChildren.
                readChildren(reader, rowPr);
                break;
        }
    }

    private static void readPendingParagraphs(NrxDocumentReaderBase reader) throws Exception
    {
        // Read unexpected paragraph from the cell level to the temporary container.
        Cell tmpCell = new Cell(reader.getDocument());
        reader.addAndPushContainer(tmpCell);

        DocxReaderFactory.getStoryReader().readChild(reader);

        reader.popContainer(NodeType.CELL);

        // Just the one paragraph expected.
        Debug.assertTrue(tmpCell.getFirstParagraph() == tmpCell.getLastParagraph());
        reader.addPendingParagraph(tmpCell.getFirstParagraph());
        tmpCell.remove();
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"rsidTr",
		"paraId",
		"textId",
		"tblPrEx",
		"trPr",
		"tc",
		"bookmarkStart",
		"bookmarkEnd",
		"commentRangeStart",
		"commentRangeEnd",
		"permStart",
		"permEnd",
		"moveFromRangeStart",
		"moveFromRangeEnd",
		"moveToRangeStart",
		"moveToRangeEnd",
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
		"p"
	);

}

