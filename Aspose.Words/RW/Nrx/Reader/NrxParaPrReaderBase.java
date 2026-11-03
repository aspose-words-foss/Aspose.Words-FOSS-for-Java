// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/05/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;


/// <summary>
/// Provides methods for reading paragraph properties in DOCX and WML.
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
abstract class NrxParaPrReaderBase
{
    protected NrxParaPrReaderBase(NrxRunPrReaderBase runPrReader,
        NrxSectPrReaderBase sectPrReader) 
    {
        Debug.assertTrue(runPrReader != null);
        Debug.assertTrue(sectPrReader != null);
        mRunPrReader = runPrReader;
        mSectPrReader = sectPrReader;
    }

    /// <summary>
    /// Reads 'w:pPr' element from the specified reader.
    /// </summary>
    /// <param name="reader">Should be positioned on the element start.</param>
    /// <param name="paraPr">Paragraph properties to read into.</param>
    /// <param name="runPr">Normally this is the run properties of the paragraph break to read into. 
    /// But I also found some documents where run properties occur inside pPr and MS Word seems to read them okay. So we do the same.</param>
    boolean read(NrxDocumentReaderBase reader, 
        ParaPr paraPr, 
        RunPr runPr) throws Exception
    {
        if (reader.getLoadOptions().getSkipFormatting())
        {
            // WORDSNET-13301 Text of some headers/footers and text that appears directly in an pPr element
            // were not extracted.
            return readSectPrAndTextIfExist(reader, runPr);
        }

        boolean isSectionBreak = false;
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();

        while (xmlReader.readChild("pPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"pStyle"*/0:
                {
                    // WORDSNET-23667 Use style index of the actual default style by default. 
                    Style defaultStyle = reader.getDefaultStyles().get((int)StyleType.PARAGRAPH);
                    int defaultStyleIndex = (defaultStyle != null) ? defaultStyle.getIstd() : StyleIndex.NORMAL;
                    
                    int istd = reader.resolveStyleIdToIstd(xmlReader.readVal(), defaultStyleIndex);
                    paraPr.setAttr(ParaAttr.ISTD, istd);
                    break;
                }
                case /*"keepNext"*/1:
                    paraPr.setAttr(ParaAttr.KEEP_WITH_NEXT, xmlReader.readBoolVal());
                    break;
                case /*"keepLines"*/2:
                    paraPr.setAttr(ParaAttr.KEEP_TOGETHER, xmlReader.readBoolVal());
                    break;
                case /*"pageBreakBefore"*/3:
                    paraPr.setAttr(ParaAttr.PAGE_BREAK_BEFORE, xmlReader.readBoolVal());
                    break;
                case /*"framePr"*/4:
                    readFramePr(xmlReader, paraPr, complianceInfo);
                    break;
                case /*"widowControl"*/5:
                    paraPr.setAttr(ParaAttr.WIDOW_CONTROL, xmlReader.readBoolVal());
                    break;
                case /*"numPr"*/6:
                case /*"listPr"*/7:  // WML
                    readNumPrWithCollectingListId(reader, paraPr);
                    break;
                case /*"suppressLineNumbers"*/8:
                case /*"supressLineNumbers"*/9:  // WML
                    paraPr.setAttr(ParaAttr.SUPPRESS_LINE_NUMBERS, xmlReader.readBoolVal());
                    break;
                case /*"pBdr"*/10:
                    readBorders(xmlReader, paraPr);
                    break;
                case /*"shd"*/11:
                    paraPr.setAttr(ParaAttr.SHADING, xmlReader.readShading());
                    break;
                case /*"tabs"*/12:
                    readTabs(xmlReader, paraPr, complianceInfo);
                    break;
                case /*"suppressAutoHyphens"*/13:
                    paraPr.setAttr(ParaAttr.SUPPRESS_AUTO_HYPHENS, xmlReader.readBoolVal());
                    break;
                case /*"kinsoku"*/14:
                    paraPr.setAttr(ParaAttr.FAR_EAST_LINE_BREAK_CONTROL, xmlReader.readBoolVal());
                    break;
                case /*"wordWrap"*/15:
                    paraPr.setAttr(ParaAttr.WORD_WRAP, xmlReader.readBoolVal());
                    break;
                case /*"overflowPunct"*/16:
                    paraPr.setAttr(ParaAttr.HANGING_PUNCTUATION, xmlReader.readBoolVal());
                    break;
                case /*"topLinePunct"*/17:
                    paraPr.setAttr(ParaAttr.TOP_LINE_PUNCTUATION, xmlReader.readBoolVal());
                    break;
                case /*"autoSpaceDE"*/18:
                    paraPr.setAttr(ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_ALPHA, xmlReader.readBoolVal());
                    break;
                case /*"autoSpaceDN"*/19:
                    paraPr.setAttr(ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_DIGIT, xmlReader.readBoolVal());
                    break;
                case /*"bidi"*/20:
                    paraPr.setAttr(ParaAttr.BIDI, xmlReader.readBoolVal());
                    break;
                case /*"adjustRightInd"*/21:
                    paraPr.setAttr(ParaAttr.AUTO_ADJUST_RIGHT_INDENT, xmlReader.readBoolVal());
                    break;
                case /*"snapToGrid"*/22:
                    paraPr.setAttr(ParaAttr.SNAP_TO_GRID, xmlReader.readBoolVal());
                    break;
                case /*"spacing"*/23:
                    readSpacing(xmlReader, paraPr, complianceInfo);
                    break;
                case /*"ind"*/24:
                    readIndents(xmlReader, paraPr, complianceInfo);
                    break;
                case /*"contextualSpacing"*/25:
                    paraPr.setAttr(ParaAttr.NO_SPACE_BETWEEN_SAME_STYLE, xmlReader.readBoolVal());
                    break;
                case /*"suppressOverlap"*/26:
                    paraPr.setAttr(ParaAttr.FRAME_SUPPRESS_OVERLAP, xmlReader.readBoolVal());
                    break;
                case /*"jc"*/27:
                    paraPr.setAttr(ParaAttr.ALIGNMENT, NrxParaEnum.xmlToParagraphAlignment(xmlReader.readVal(),
                        complianceInfo));
                    break;
                case /*"textDirection"*/28:
                    paraPr.setAttr(ParaAttr.FRAME_TEXT_ORIENTATION,
                        StyleConvertUtil.xmlToTextOrientation(xmlReader.readVal(), complianceInfo));
                    break;
                case /*"textAlignment"*/29:
                    paraPr.setAttr(ParaAttr.BASELINE_ALIGNMENT, NrxParaEnum.xmlToBaselineAlignment(xmlReader.readVal()));
                    break;
                case /*"outlineLvl"*/30:
                    paraPr.setAttr(ParaAttr.OUTLINE_LEVEL, (/*OutlineLevel*/int)xmlReader.readIntVal());
                    break;
                case /*"divId"*/31:
                    paraPr.setAttr(ParaAttr.HTML_BLOCK_ID, xmlReader.readIntVal());
                    break;
                case /*"rPr"*/32:
                    mRunPrReader.read(reader, runPr);
                    break;
                case /*"sectPr"*/33:
                {
                    readSectPr(reader);
                    isSectionBreak = true;
                    break;
                }
                case /*"mirrorIndents"*/34:
                    paraPr.setAttr(ParaAttr.MIRROR_INDENTS, xmlReader.readBoolVal());
                    break;
                case /*"textboxTightWrap"*/35:
                    paraPr.setAttr(ParaAttr.TEXTBOX_TIGHT_WRAP, NrxParaEnum.xmlToTextboxTightWrap(xmlReader.readVal()));
                    break;
                case /*"cnfStyle"*/36:
                    xmlReader.ignoreElement();
                    break;
                case /*"collapsed"*/37:
                    paraPr.setAttr(ParaAttr.COLLAPSED, xmlReader.readBoolVal());
                    complianceInfo.isDocxExtensions(true);
                    break;
                default:
                    // WORDSNET-7923 If this is an unknown pPr attribute, it could be a rPr attribute and MS Word seems to handle it okay so do we.
                    readFormatSpecific(xmlReader.getLocalName(), reader, paraPr, runPr);
                        mRunPrReader.readChild(reader, runPr, true);
                    break;
            }
        }

        return isSectionBreak;
    }

    protected abstract boolean readFormatSpecific(String localName,
        NrxDocumentReaderBase reader,
        ParaPr paraPr,
        RunPr runPr) throws Exception;

    /// <summary>
    /// Passes through all child nodes of the 'pPr' element and reads the 'sectPr' and text elements if they exist.
    /// </summary>
    /// <param name="reader">Document reader.</param>
    /// <param name="runPr">Properties that will be used for created runs.</param>
    /// <returns>Returns true if the sectPr element has been read.</returns>
    private boolean readSectPrAndTextIfExist(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        boolean isSectionBreak = false;
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("pPr"))
        {
            if ("sectPr".equals(xmlReader.getLocalName()))
            {
                readSectPr(reader);
                isSectionBreak = true;
            }
            else if (("t".equals(xmlReader.getLocalName())) || ("r".equals(xmlReader.getLocalName())))
            {
                // ReadRunPrChild can read the 't' and 'r' elements.
                mRunPrReader.readChild(reader, runPr, false);
            }
            else
            {
                xmlReader.ignoreElementNoWarn();
            }
        }
        return isSectionBreak;
    }

    /// <summary>
    /// Reads the sectPr element (Section Properties).
    /// </summary>
    /// <param name="reader">Document reader.</param>
    private void readSectPr(NrxDocumentReaderBase reader) throws Exception
    {
        // We must be inside the last paragraph of a section.
        Paragraph para = (Paragraph)reader.getCurContainer();

        if ((para != null) && (para.getParentSection() != null))
        {
            reader.GlobalSectPr.expandTo(para.getParentSection().getSectPr());
            mSectPrReader.read(reader, para.getParentSection().getSectPr());

            // Word clears accumulated section properties each time when it encounters SectPr inside ParaPr.
            // However, accumulated properties for very last section must be preserved to use it in case
            // when no new properties for last section is read.
            if (reader.GlobalSectPr.getCount() > 0)
            {
                reader.getLastSectPr().clear();
                reader.GlobalSectPr.expandTo(reader.getLastSectPr());
                reader.GlobalSectPr.clear();
            }
        }
    }

    void readNumPrWithCollectingListId(NrxDocumentReaderBase reader, ParaPr paraPr)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        readNumPr(xmlReader, paraPr);
        // Collect ListId.
        if (paraPr.getListId() != 0)
            msArrayList.add(reader.UsedListIds, paraPr.getListId());
    }

    private static void readFramePr(NrxXmlReader xmlReader, ParaPr paraPr, OoxmlComplianceInfo complianceInfo)
    {
        boolean heightRead = false;
        /*HeightRule*/int hRule = HeightRule.AT_LEAST;
        int val = 0;

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"dropCap"*/38:
                case /*"drop-cap"*/39:    // WML
                    paraPr.setAttr(ParaAttr.DROP_CAP_POSITION, NrxParaEnum.xmlToDropCapPosition(xmlReader.getValue()));
                    break;
                case /*"lines"*/40:
                    paraPr.setAttr(ParaAttr.DROP_CAP_LINES_TO_DROP, xmlReader.getValueAsInt());
                    break;
                case /*"w"*/41:
                    paraPr.setAttr(ParaAttr.FRAME_WIDTH, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"h"*/42:
                    val = xmlReader.getValueAsTwips(complianceInfo);
                    heightRead = true;
                    break;
                case /*"vSpace"*/43:
                case /*"vspace"*/44:  // WML
                    paraPr.setAttr(ParaAttr.FRAME_VERTICAL_DISTANCE_FROM_TEXT, 
                        xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"hSpace"*/45:
                case /*"hspace"*/46:  // WML
                    paraPr.setAttr(ParaAttr.FRAME_HORIZONTAL_DISTANCE_FROM_TEXT, 
                        xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"wrap"*/47:
                    paraPr.setAttr(ParaAttr.FRAME_WRAP_TYPE, NrxParaEnum.xmlToTextFrameWrapType(xmlReader.getValue()));
                    break;
                case /*"hAnchor"*/48:
                case /*"hanchor"*/49: // WML
                    paraPr.setAttr(ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION, NrxParaEnum.xmlToRelativeHorizontalPosition(xmlReader.getValue()));
                    break;
                case /*"vAnchor"*/50:
                case /*"vanchor"*/51: // WML
                    paraPr.setAttr(ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION, NrxParaEnum.xmlToRelativeVerticalPosition(xmlReader.getValue()));
                    break;
                case /*"x"*/52:
                    paraPr.setAttr(ParaAttr.FRAME_LEFT, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"y"*/53:
                    paraPr.setAttr(ParaAttr.FRAME_TOP, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"xAlign"*/54:
                case /*"x-align"*/55: // WML
                    paraPr.setAttr(ParaAttr.FRAME_HORIZONTAL_ALIGNMENT, NrxParaEnum.xmlToHorizontalAlignment(xmlReader.getValue()));
                    break;
                case /*"yAlign"*/56:
                case /*"y-align"*/57: // WML
                    paraPr.setAttr(ParaAttr.FRAME_VERTICAL_ALIGNMENT, NrxParaEnum.xmlToVerticalAlignment(xmlReader.getValue()));
                    break;
                case /*"hRule"*/58:
                case /*"h-rule"*/59:  // WML
                    hRule = NrxParaEnum.xmlToHeightRule(xmlReader.getValue());
                    heightRead = true;
                    break;
                case /*"anchorLock"*/60:
                    paraPr.setAttr(ParaAttr.FRAME_LOCK_ANCHOR, xmlReader.getValueAsBool());
                    break;
                case /*"anchor-lock"*/61: // WML
                    paraPr.setAttr(ParaAttr.FRAME_LOCK_ANCHOR, xmlReader.getValueAsBool());
                    break;
                default:
                    break;
            }
        }

        if (heightRead)
            paraPr.setAttr(ParaAttr.FRAME_HEIGHT, new Height(hRule, val));
    }

    private static void readIndents(NrxXmlReader xmlReader, ParaPr paraPr, OoxmlComplianceInfo complianceInfo)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"left"*/62:
                    paraPr.setAttr(ParaAttr.LEFT_INDENT, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"start"*/63: // iso29500
                    {
                        complianceInfo.markAsIsoTransitional();
                        paraPr.setAttr(ParaAttr.LEFT_INDENT, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                    }
                case /*"right"*/64:
                    paraPr.setAttr(ParaAttr.RIGHT_INDENT, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"end"*/65: // iso29500
                    {
                        complianceInfo.markAsIsoTransitional();
                        paraPr.setAttr(ParaAttr.RIGHT_INDENT, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                    }
                //We can have either "firstLine" attributes or "hanging" attributes.
                case /*"firstLine"*/66:
                case /*"first-line"*/67:  // WML
                    paraPr.setAttr(ParaAttr.FIRST_LINE_INDENT, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"hanging"*/68:
                    // RK I don't think hanging is just a negative of firstLine.
                    // Hanging usually affects left indent too. Need to check in unified tests.
                    paraPr.setAttr(ParaAttr.FIRST_LINE_INDENT, -xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"leftChars"*/69:
                case /*"left-chars"*/70:  // WML
                    paraPr.setAttr(ParaAttr.LEFT_INDENT_UNITS, xmlReader.getValueAsInt());
                    break;
                case /*"startChars"*/71: // iso29500
                    {
                        complianceInfo.markAsIsoTransitional();
                    paraPr.setAttr(ParaAttr.LEFT_INDENT_UNITS, xmlReader.getValueAsInt());
                    break;
                    }
                case /*"rightChars"*/72:
                case /*"right-chars"*/73: // WML
                    paraPr.setAttr(ParaAttr.RIGHT_INDENT_UNITS, xmlReader.getValueAsInt());
                    break;
                case /*"endChars"*/74: // iso29500
                    {
                        complianceInfo.markAsIsoTransitional();
                    paraPr.setAttr(ParaAttr.RIGHT_INDENT_UNITS, xmlReader.getValueAsInt());
                    break;
                    }
                case /*"firstLineChars"*/75:
                case /*"first-line-chars"*/76:    // WML
                    paraPr.setAttr(ParaAttr.FIRST_LINE_INDENT_UNITS, xmlReader.getValueAsInt());
                    break;
                case /*"hangingChars"*/77:
                case /*"hanging-chars"*/78:   // WML
                    paraPr.setAttr(ParaAttr.FIRST_LINE_INDENT_UNITS, -xmlReader.getValueAsInt());
                    break;
                default:
                    xmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.NRX, xmlReader.getLocalName());
                    break;
            }
        }
    }

    private static void readSpacing(NrxXmlReader xmlReader, ParaPr paraPr, OoxmlComplianceInfo complianceInfo)
    {
        LineSpacing lineSpacing = LineSpacing.createDefault();
        boolean hasLineSpacingValue = false;

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"before"*/79:
                    setAttrIfPositive(paraPr, ParaAttr.SPACE_BEFORE, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"beforeLines"*/80:
                case /*"before-lines"*/81:    // WML
                    paraPr.setAttr(ParaAttr.SPACE_BEFORE_UNITS, xmlReader.getValueAsInt());
                    break;
                case /*"beforeAutospacing"*/82:
                case /*"before-autospacing"*/83:  // WML
                    if(NrxXmlUtil.isOnOffValue(xmlReader.getValue()))
                        paraPr.setAttr(ParaAttr.SPACE_BEFORE_AUTO, xmlReader.getValueAsBool());
                    else
                        xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.DOCX,
                                    msString.format(WarningStrings.INVALID_ON_OFF_VALUE, xmlReader.getValue(), xmlReader.getLocalName()));
                    break;
                case /*"after"*/84:
                    setAttrIfPositive(paraPr, ParaAttr.SPACE_AFTER, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"afterLines"*/85:
                case /*"after-lines"*/86: // WML
                    paraPr.setAttr(ParaAttr.SPACE_AFTER_UNITS, xmlReader.getValueAsInt());
                    break;
                case /*"afterAutospacing"*/87:
                case /*"after-autospacing"*/88:   // WML
                    paraPr.setAttr(ParaAttr.SPACE_AFTER_AUTO, xmlReader.getValueAsBool());
                    break;
                case /*"line"*/89:
                    // andrnosk: WORDSNET-7118 According to spec (17.18.81 ST_SignedTwipsMeasure) spacing value can be positive or negative,
                    // but MS Word says LineSpacing value must be between 0pt and 1584pt.
                    // MS Word preserves negative line spacing during open/save to DOCX, 
                    // but after saving to DOC and then back to DOCX, MS Word just converts negative value to absolute value
                    // We should do the same. 
                    lineSpacing.setValue(xmlReader.getValueAsTwips(complianceInfo));
                    hasLineSpacingValue = true;
                    break;
                case /*"lineRule"*/90:
                case /*"line-rule"*/91:   // WML
                    lineSpacing.setRule(NrxParaEnum.xmlToLineSpacingRule(xmlReader.getValue()));
                    break;
                default:
                    break;
            }
        }

        if (hasLineSpacingValue)
        {
            validate(lineSpacing);
            paraPr.set(ParaAttr.LINE_SPACING, lineSpacing);
        }
    }

    /// <summary>
    /// Validates specified LineSpacing.
    /// </summary>
    private static void validate(LineSpacing lineSpacing)
    {
        // WORDSNET-19392 Seems that value should be cast to short.
        // AM. Maybe we need to move validation to LineSpacing class.
        lineSpacing.setValue((short)lineSpacing.getValue());

        // WORDSNET-10830 Negative line spacing value means that rule is Exactly.
        if (lineSpacing.getValue() < 0)
        {
            lineSpacing.setValue(Math.abs(lineSpacing.getValue()));
            lineSpacing.setRule(LineSpacingRule.EXACTLY);
        }

        // WORDSNET-13091 If line spacing rule is Exactly and value is zero, MS Word interprets the rule as At least.
        if ((lineSpacing.getRule() == LineSpacingRule.EXACTLY) && (lineSpacing.getValue() == 0))
            lineSpacing.setRule(LineSpacingRule.AT_LEAST);
    }

    /// <summary>
    /// andrnosk: WORDSNET-5722 According to spec (22.9.2.14 ST_TwipsMeasure) spacing value cannot be negative.
    /// If negative value occurred then ignore.
    /// </summary>
    /// <remarks>
    /// MS Word preserves spacing before/after value="-1" during open/save to DOCX, 
    /// but after saving to DOC and then back to DOCX, MS Word just ignore this attributes, 
    /// and does not write anything. We should do the same. 
    /// It seems in this case "-1" means for MS Word the same as if value is not specified, 
    /// so we just do not read negative value into the model.
    /// </remarks>
    private static void setAttrIfPositive(ParaPr paraPr, int key, int value)
    {
        if (value >= 0)
            paraPr.setAttr(key, value);
    }

    private static void readTabs(NrxXmlReader xmlReader, ParaPr paraPr, OoxmlComplianceInfo complianceInfo)
    {
        TabStopCollection tabStops = new TabStopCollection();

        while (xmlReader.readChild("tabs"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"tab"*/92:
                    // WORDSNET-21091 If 'val' attr is omitted, Word treats it as Left, so default is Left.
                    /*TabAlignment*/int val = TabAlignment.LEFT;
                    /*TabLeader*/int leader = TabLeader.NONE;
                    int pos = 0;

                    while (xmlReader.moveToNextAttribute())
                    {
                        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                        {
                            case /*"val"*/93:
                                val = NrxParaEnum.xmlToTabAlignment(xmlReader.getValue(), complianceInfo);
                                break;
                            case /*"leader"*/94:
                                leader = NrxParaEnum.xmlToTabLeader(xmlReader.getValue());
                                break;
                            case /*"pos"*/95:
                                // WORDSNET-10435 Word truncates tab positions on import to fall into the valid
                                // range: takes only the lower 16 bits of the number and treat them as a signed 
                                // 16-bit integer (the type short in C#).
                                pos = xmlReader.getValueAsTwipsAsShort(complianceInfo);
                                break;
                            default:
                                break;
                        }
                    }

                    tabStops.add(new TabStop(pos, val, leader));
                    break;
                default:
                    break;
            }
        }

        if (tabStops.getCount() > 0)
            paraPr.setAttr(ParaAttr.TAB_STOPS, tabStops);
    }

    private static void readBorders(NrxXmlReader xmlReader, ParaPr paraPr)
    {
        while (xmlReader.readChild("pBdr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"top"*/96:
                    paraPr.setAttr(ParaAttr.BORDER_TOP, xmlReader.readBorder());
                    break;
                case /*"left"*/62:
                    paraPr.setAttr(ParaAttr.BORDER_LEFT, xmlReader.readBorder());
                    break;
                case /*"bottom"*/97:
                    paraPr.setAttr(ParaAttr.BORDER_BOTTOM, xmlReader.readBorder());
                    break;
                case /*"right"*/64:
                    paraPr.setAttr(ParaAttr.BORDER_RIGHT, xmlReader.readBorder());
                    break;
                case /*"between"*/98:
                    paraPr.setAttr(ParaAttr.BORDER_BETWEEN, xmlReader.readBorder());
                    break;
                case /*"bar"*/99:
                    paraPr.setAttr(ParaAttr.BORDER_BAR, xmlReader.readBorder());
                    break;
                default:
                    break;
            }
        }
    }

    void readNumPr(NrxXmlReader xmlReader, ParaPr paraPr)
    {
        String elemName = xmlReader.getLocalName();  // numPr in DOCX, listPr in WML.
        while (xmlReader.readChild(elemName))   
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"ilvl"*/100:
                    int intVal = xmlReader.readIntVal();

                    // andrnosk: WORDSNET-7650 If value is negative MS Word2007/2010 recalculate this value in the following way.
                    if (intVal < 0)
                        intVal = 256 + intVal;

                    paraPr.setAttr(ParaAttr.LIST_LEVEL, intVal);
                    break;
                case /*"numId"*/101:
                case /*"ilfo"*/102:    // WML
                    paraPr.setAttr(ParaAttr.LIST_ID, xmlReader.readIntVal());
                    break;
                case /*"t"*/103:  // WML
                    xmlReader.ignoreElement(WarningType.MINOR_FORMATTING_LOSS, WarningSource.WORD_ML,
                        "Import of 't' element within 'listPr' is not supported in WordML format by Aspose.Words.");
                    break;
                case /*"font"*/104:  // WML
                    xmlReader.ignoreElement(WarningType.MINOR_FORMATTING_LOSS, WarningSource.WORD_ML,
                        "Import of 'font' element within 'listPr' is not supported in WordML format by Aspose.Words.");
                    break;
                default:
                    if (!readFormatSpecificNumPr(xmlReader.getLocalName(), xmlReader, paraPr))
                        xmlReader.ignoreElement();
                    break;
            }
        }
    }

    protected abstract boolean readFormatSpecificNumPr(String localName, NrxXmlReader xmlReader, ParaPr paraPr);

    private /*final*/ NrxRunPrReaderBase mRunPrReader;
    private /*final*/ NrxSectPrReaderBase mSectPrReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"pStyle",
		"keepNext",
		"keepLines",
		"pageBreakBefore",
		"framePr",
		"widowControl",
		"numPr",
		"listPr",
		"suppressLineNumbers",
		"supressLineNumbers",
		"pBdr",
		"shd",
		"tabs",
		"suppressAutoHyphens",
		"kinsoku",
		"wordWrap",
		"overflowPunct",
		"topLinePunct",
		"autoSpaceDE",
		"autoSpaceDN",
		"bidi",
		"adjustRightInd",
		"snapToGrid",
		"spacing",
		"ind",
		"contextualSpacing",
		"suppressOverlap",
		"jc",
		"textDirection",
		"textAlignment",
		"outlineLvl",
		"divId",
		"rPr",
		"sectPr",
		"mirrorIndents",
		"textboxTightWrap",
		"cnfStyle",
		"collapsed",
		"dropCap",
		"drop-cap",
		"lines",
		"w",
		"h",
		"vSpace",
		"vspace",
		"hSpace",
		"hspace",
		"wrap",
		"hAnchor",
		"hanchor",
		"vAnchor",
		"vanchor",
		"x",
		"y",
		"xAlign",
		"x-align",
		"yAlign",
		"y-align",
		"hRule",
		"h-rule",
		"anchorLock",
		"anchor-lock",
		"left",
		"start",
		"right",
		"end",
		"firstLine",
		"first-line",
		"hanging",
		"leftChars",
		"left-chars",
		"startChars",
		"rightChars",
		"right-chars",
		"endChars",
		"firstLineChars",
		"first-line-chars",
		"hangingChars",
		"hanging-chars",
		"before",
		"beforeLines",
		"before-lines",
		"beforeAutospacing",
		"before-autospacing",
		"after",
		"afterLines",
		"after-lines",
		"afterAutospacing",
		"after-autospacing",
		"line",
		"lineRule",
		"line-rule",
		"tab",
		"val",
		"leader",
		"pos",
		"top",
		"bottom",
		"between",
		"bar",
		"ilvl",
		"numId",
		"ilfo",
		"t",
		"font"
	);

}

