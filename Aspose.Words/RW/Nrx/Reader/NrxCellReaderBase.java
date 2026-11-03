// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;


/// <summary>
/// Provides method for parsing 'w:tc' element.
/// NOTE: Derived classes should be immutable (state cannot be modified after it is created).
/// </summary>
abstract class NrxCellReaderBase
{
    protected NrxCellReaderBase(NrxStoryReaderBase storyReader, 
        NrxParaPrReaderBase paraPrReader,
        NrxCellPrReaderBase cellPrReader)
    {
        Debug.assertTrue(storyReader != null);
        Debug.assertTrue(paraPrReader != null);
        Debug.assertTrue(cellPrReader != null);
        mStoryReader = storyReader;
        mParaPrReader = paraPrReader;
        mCellPrReader = cellPrReader;
    }

    /// <summary>
    /// Reads 'w:tc' element.
    /// </summary>
    void read(NrxDocumentReaderBase reader) throws Exception
    {
        Cell cell = new Cell(reader.getDocument());
        reader.addAndPushContainer(cell);

        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("tc"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"tcPr"*/0:
                    mCellPrReader.read(reader, cell.getCellPr());
                    break;
                case /*"pPr"*/1:
                    mParaPrReader.read(reader, reader.GlobalParaPr, reader.GlobalParagraphBreakRunPr);
                    break;
                case /*"tcW"*/2:
                    // WORDSNET-4327 Resiliency. Normally tcW occurs inside tcPr, but we've got one here
                    // and MS Word seems to read it okay. So do we now.
                    cell.getCellPr().setPreferredWidthAndWidth(xmlReader.readLength(reader.getComplianceInfo()));
                    break;
                case /*"hMerge"*/3:
                    // WORDSNET-15721 "Horizontally merged cell" element occurred in unexpected place. 
                    // Implement resiliency and read "hMerge" element from "tc" level. 
                    NrxCellPrReaderBase.readHMerge(xmlReader, cell.getCellPr());
                    break;
                default:
                    // WORDSNET-13301 Text of second and later cells in a merged cell range was included in extracted
                    // text, although Fixed Grid Calculator replaces such cells with grid spans. So, do not read 
                    // children of that cells on fixed table layout (when Table.AllowAutoFit is true). 

                    if (!reader.isDocx() ||
                        !reader.getLoadOptions().getSkipFormatting() ||
                        cell.getParentTable().getAllowAutoFit() ||
                        !isContinueHMergingCell(cell))
                    {
                        mStoryReader.readChild(reader);
                    }
                    break;
            }
        }

        reader.popContainer(NodeType.CELL);
    }

    /// <summary>
    /// Returns flag indicating that the specified cell is second or later cell in a horizontally merging range.
    /// </summary>
    private static boolean isContinueHMergingCell(Cell cell)
    {
        if (cell.getCellPr().getHorizontalMerge() != CellMerge.PREVIOUS)
            return false;

        // MS Word treats CellMerge.Previous as None unless there is a preceding CellMerge.First.
        Cell firstCell = cell.getPreviousCell();
        while ((firstCell != null) && (firstCell.getCellPr().getHorizontalMerge() == CellMerge.PREVIOUS))
            firstCell = firstCell.getPreviousCell();

        return (firstCell != null) && (firstCell.getCellPr().getHorizontalMerge() == CellMerge.FIRST);
    }

    private /*final*/ NrxStoryReaderBase mStoryReader;
    private /*final*/ NrxParaPrReaderBase mParaPrReader;
    private /*final*/ NrxCellPrReaderBase mCellPrReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"tcPr",
		"pPr",
		"tcW",
		"hMerge"
	);

}

