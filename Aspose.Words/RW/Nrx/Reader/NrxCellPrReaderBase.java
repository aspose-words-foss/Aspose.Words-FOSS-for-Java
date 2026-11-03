// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;


/// <summary>
/// Provides base methods for reading cell properties from different document parts.
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
abstract class NrxCellPrReaderBase
{
    protected NrxCellPrReaderBase(NrxParaReaderBase paraReader)
    {
        Debug.assertTrue(paraReader != null);
        mParaReader = paraReader;
    }

    /// <summary>
    /// Reads 'w:tcPr' element from the specified reader. Reader should be positioned to element start.
    /// </summary>
    void read(NrxDocumentReaderBase reader, CellPr cellPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        xmlReader.moveToElement();

        if (reader.getLoadOptions().getSkipFormatting())
        {
            // WORDSNET-13301 Text of second and later cells of a merged cell range in fixed table layout was included
            // in extracted text, although it should not be displayed.
            readHMergeOnly(xmlReader, cellPr);
            return;
        }

        String tagName = xmlReader.getLocalName();

        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"cnfStyle"*/0:
                    // This is a calculated "helper" value, ignore.
                    xmlReader.ignoreElementNoWarn();
                    break;
                case /*"tcW"*/1:
                    cellPr.setPreferredWidthAndWidth(xmlReader.readLength(reader.getComplianceInfo()));
                    break;
                case /*"gridSpan"*/2:
                {
                    // WORDSNET-22961 In accordance with [MS-OI29500] Word treats a gridSpan of 0 the same as a grid span of 1.
                    int gridSpan = xmlReader.readIntVal();
                    cellPr.setAttr(CellAttr.SYS_CELL_SPAN, (gridSpan == 0) ? 1 : gridSpan);
                    break;
                }
                case /*"hmerge"*/3: // WML
                case /*"hMerge"*/4:
                    readHMerge(xmlReader, cellPr);
                    break;
                case /*"vmerge"*/5: // WML
                case /*"vMerge"*/6:
                {
                    String vmerge = xmlReader.readVal();

                    if (vmerge == null)
                        vmerge = "continue";

                    cellPr.setAttr(CellAttr.VERTICAL_MERGE, NrxTableEnum.xmlToCellMerge(vmerge));
                    break;
                }
                case /*"tcBorders"*/7:
                    readBorders(reader, cellPr, reader.getComplianceInfo());
                    break;
                case /*"left"*/8:
                case /*"right"*/9:
                case /*"top"*/10:
                case /*"bottom"*/11:
                    // WORDSNET-26675 Cell borders without surrounding tcBorders element.
                    cellPr.setAttr(NrxXmlUtil.borderNameToCellAttr(xmlReader.getLocalName()), xmlReader.readBorder());
                    break;
                case /*"shd"*/12:
                    cellPr.setAttr(CellAttr.SHADING, xmlReader.readShading());
                    break;
                case /*"noWrap"*/13:
                    cellPr.setAttr(CellAttr.WRAP_TEXT, !xmlReader.readBoolVal());
                    break;
                case /*"hideMark"*/14:
                    cellPr.setAttr(CellAttr.HIDE_MARK, xmlReader.readBoolVal());
                    break;
                case /*"tcMar"*/15:
                    readMargins(xmlReader, cellPr, reader.getComplianceInfo());
                    break;
                case /*"textFlow"*/16: // WML
                case /*"textDirection"*/17:
                    cellPr.setAttr(CellAttr.ORIENTATION,
                        StyleConvertUtil.xmlToTextOrientation(xmlReader.readVal(), reader.getComplianceInfo()));
                    break;
                case /*"tcFitText"*/18:
                    cellPr.setAttr(CellAttr.FIT_TEXT, xmlReader.readBoolVal());
                    break;
                case /*"vAlign"*/19:
                    cellPr.setAttr(CellAttr.VERTICAL_ALIGNMENT,
                        NrxTableEnum.xmlToCellVerticalAlignment(xmlReader.readVal()));
                    break;
                case /*"p"*/20:
                {
                    // WORDSNET-14871 Implement reading of the paragraph element from
                    // table cell properties level.
                    mParaReader.read(reader);
                    break;
                }
                case /*"tblHeader"*/21:
                    // WORDSNET-19201 Implement reading of the 'tblHeader' element from
                    // table cell properties level.
                    reader.GlobalRowPr.setAttr(TableAttr.HEADING_FORMAT, xmlReader.readBoolVal());
                    break;
                case /*"tcPr"*/22:
                    // WORDSNET-22773 Implemented reading of a nested 'tcPr' element from a parent 'tcPr'.
                    read(reader, cellPr);
                    break;
                default:
                    if (!readFormatSpecific(xmlReader.getLocalName(), reader, cellPr))
                        xmlReader.ignoreElement();
                    break;
            }
        }

        // WORDSNET-14978 Allow preferred width for first cell in merge only and ignore rest.
        if(cellPr.getHorizontalMerge() == CellMerge.PREVIOUS)
            cellPr.remove(CellAttr.PREFERRED_WIDTH);
    }

    protected abstract boolean readFormatSpecific(String localName, NrxDocumentReaderBase reader, CellPr cellPr) throws Exception;

    /// <summary>
    /// Passes through all children of current XML node and reads the Horizontally Merged Cell element
    /// if it exists.
    /// </summary>
    private static void readHMergeOnly(NrxXmlReader xmlReader, CellPr cellPr)
    {
        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
        {
            if (("hmerge".equals(xmlReader.getLocalName())) || // WML
                ("hMerge".equals(xmlReader.getLocalName())))
                readHMerge(xmlReader, cellPr);
            else
                xmlReader.ignoreElementNoWarn();
        }
    }

    /// <summary>
    /// Reads the Horizontally Merged Cell element.
    /// </summary>
    static void readHMerge(NrxXmlReader xmlReader, CellPr cellPr)
    {
        String hmerge = xmlReader.readVal();

        if (hmerge == null)
            hmerge = "continue";

        cellPr.setAttr(CellAttr.HORIZONTAL_MERGE, NrxTableEnum.xmlToCellMerge(hmerge));
    }

    private static void readBorders(NrxDocumentReaderBase reader, CellPr cellPr, OoxmlComplianceInfo cInfo)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.readChild("tcBorders"))
        {
            // Fix for C++, in C++ order of arguments in the method call is difference,
            // That is why xmlReader.ReadBorder() executes first and as a result xmlReader.LocalName returns incorrect value.
            // Fixed by caching xmlReader.LocalName value.
            String borderName = xmlReader.getLocalName();
            switch (gStringSwitchMap.of(borderName))
            {
                case /*"top"*/10:
                case /*"bottom"*/11:
                case /*"left"*/8:
                case /*"right"*/9:
                case /*"tl2br"*/23:
                case /*"tr2bl"*/24:
                case /*"insideH"*/25:
                case /*"insideV"*/26:
                    cellPr.setAttr(NrxXmlUtil.borderNameToCellAttr(borderName), xmlReader.readBorder());
                    break;
                case /*"start"*/27:   // iso29500
                case /*"end"*/28:     // iso29500
                    {
                        cInfo.markAsIsoTransitional();
                        cellPr.setAttr(NrxXmlUtil.borderNameToCellAttr(borderName), xmlReader.readBorder());
                        break;
                    }
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readMargins(NrxXmlReader xmlReader, CellPr cellPr, OoxmlComplianceInfo cInfo)
    {
        while (xmlReader.readChild("tcMar"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"top"*/10:
                    setAttrMargin(xmlReader, cellPr, CellAttr.TOP_PADDING, cInfo);
                    break;
                case /*"left"*/8:
                    setAttrMargin(xmlReader, cellPr, CellAttr.LEFT_PADDING, cInfo);
                    break;
                case /*"start"*/27: // iso29500
                    {
                        cInfo.markAsIsoTransitional();
                        setAttrMargin(xmlReader, cellPr, CellAttr.LEFT_PADDING, cInfo);
                        break;
                    }
                case /*"bottom"*/11:
                    setAttrMargin(xmlReader, cellPr, CellAttr.BOTTOM_PADDING, cInfo);
                    break;
                case /*"right"*/9:
                    setAttrMargin(xmlReader, cellPr, CellAttr.RIGHT_PADDING, cInfo);
                    break;
                case /*"end"*/28: // iso29500
                    {
                        cInfo.markAsIsoTransitional();
                        setAttrMargin(xmlReader, cellPr, CellAttr.RIGHT_PADDING, cInfo);
                        break;
                    }
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Set margin attribute value for specified attribute collection.
    /// </summary>
    /// <param name="xmlReader"> Provide ability to read "xml" files.</param>
    /// <param name="cellPr"> Cell attribute collection.</param>
    /// <param name="atrKey"> Attribute key.</param>
    /// <param name="cInfo"> OOXML compliance info about the document.</param>
    private static void setAttrMargin(NrxXmlReader xmlReader, CellPr cellPr, int atrKey, OoxmlComplianceInfo cInfo)
    {
        PreferredWidth width = xmlReader.readLength(cInfo);

        // WORDSNET-13442 In case when type of unit measure is "nil" and value does not contain additional info about
        // measure unit such length elements are skipped (same behavior as MSW).
        if (width.getTypeOrig() != 0)
            cellPr.setAttr(atrKey, width.getValueRaw());
    }

    private /*final*/ NrxParaReaderBase mParaReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"cnfStyle",
		"tcW",
		"gridSpan",
		"hmerge",
		"hMerge",
		"vmerge",
		"vMerge",
		"tcBorders",
		"left",
		"right",
		"top",
		"bottom",
		"shd",
		"noWrap",
		"hideMark",
		"tcMar",
		"textFlow",
		"textDirection",
		"tcFitText",
		"vAlign",
		"p",
		"tblHeader",
		"tcPr",
		"tl2br",
		"tr2bl",
		"insideH",
		"insideV",
		"start",
		"end"
	);

}

