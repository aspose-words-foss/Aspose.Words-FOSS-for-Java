// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;
import com.aspose.StringUtil;
import com.aspose.DictionaryExtension;


/// <summary>
/// Provides methods for reading "Numbering" package part.
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
class DocxNumberingReader
{
    DocxNumberingReader(DocxParaPrReader paraPrReader,
        DocxRunPrReader runPrReader,
        INrxVmlReader vmlReader)
    {
        Debug.assertTrue(paraPrReader != null);
        Debug.assertTrue(runPrReader != null);
        mParaPrReader = paraPrReader;
        mRunPrReader = runPrReader;
        mVmlReader = vmlReader;
    }

    /// <summary>
    /// Reads "Numbering" package part.
    /// </summary>
    /// <param name="reader">DocxDocumentBaseReader to read from. Should be positioned to the element start.</param>
    void read(DocxDocumentReaderBase reader) throws Exception
    {
        if (reader.getLoadOptions().getSkipFormatting())
            return;

        NrxXmlReader xmlReader = reader.switchToPartReaderByRelType(reader.getRelTypes().getNumbering());
        if (xmlReader == null)
            return;

        while (xmlReader.readChild("numbering"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"numPicBullet"*/0:
                    readNumPicBullet(reader);
                    break;
                case /*"abstractNum"*/1:
                    readAbstractNum(reader);
                    break;
                case /*"num"*/2:
                    readNum(reader);
                    break;
                case /*"numIdMacAtCleanup"*/3:
                    reader.getDocument().getLists().setNumIdMacAtCleanup(xmlReader.readIntAttribute("val", 0));
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        reader.restorePartReader();
    }

    /// <summary>
    /// Reads 'w:numPicBullet' element from 'w:numbering' element children.
    /// </summary>
    private void readNumPicBullet(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        // RK In DOC and RTF picture bullet ids are simple indexes into an array of picture bullets.
        // In WML and DOCX picture bullet ids can probably be anything and in theory can be
        // out of sequence, with gaps and not sorted. To support this requires extra work.
        // I think we are not likely to get out of sequence picture bullet ids, therefore
        // just throw if we get this. We will support this if required by users later.
        int listPicBulletId = xmlReader.readIntAttribute("numPicBulletId", 0);

        msArrayList.add(reader.getPictureBulletTranslation(), listPicBulletId);

        ArrayList<ShapeBase> shapes = readShapes(reader);
        for (ShapeBase shape : shapes)
            reader.getDocument().getLists().addPictureBullet((Shape)shape);
    }

    /// <summary>
    /// Reads picture bullet shapes.
    /// </summary>
    private ArrayList<ShapeBase> readShapes(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        String tagName = xmlReader.getLocalName();
        ArrayList<ShapeBase> shapes = new ArrayList<ShapeBase>();

        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"pict"*/4:
                {
                    if (mVmlReader == null)
                        xmlReader.ignoreElement();
                    else
                        msArrayList.addRange(shapes, mVmlReader.read(reader));
                    break;
                }
                case /*"AlternateContent"*/5:
                    {
                        Paragraph tmpContainer = new Paragraph(reader.getDocument());
                        reader.addAndPushContainer(tmpContainer);

                        DocxReaderFactory.getRunReader().readAlternateContent(reader, new RunPr());

                        reader.popContainer(NodeType.PARAGRAPH);
                        for (Node node : (Iterable<Node>) tmpContainer.getChildNodes(NodeType.ANY, false))
                        {
                            Shape shape = ms.as(node, Shape.class);
                            if (shape != null)
                            {
                                node.remove();
                                msArrayList.add(shapes, shape);
                            }
                        }
                        tmpContainer.remove();
                        break;
                    }
                case /*"drawing"*/6:
                    {
                        ShapeBase shape = ((INrxDmlReader)reader).readDrawing(new RunPr());
                        reader.getDocument().removeChild(shape); // shape is inserting into document during reading
                        msArrayList.add(shapes, shape);
                        break;
                    }
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        return shapes;
    }

    /// <summary>
    /// Reads 'w:listDef' element from 'w:lists' children.
    /// </summary>
    private void readAbstractNum(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        DocumentBase doc = reader.getDocument();

        int abstractNumId = 0;
        boolean lstidSpecified = false;
        boolean restartNumberingAfterBreak = false;
        int lsid = 0;
        // Let's default to multiple level for resiliency (in case list type is not specified),
        // but all nine levels are defined.
        /*ListType*/int listType = ListType.HYBRID_MULTI_LEVEL;
        int templateCode = 0;
        String name = null;
        String sti = null;

        ListDef listDef = null;

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"abstractNumId"*/7:
                {
                    abstractNumId = xmlReader.getValueAsTruncatedInt();

                    // WORDSNET-13607 Very strange MS Word behavior.
                    // It ignores list definitions with identical 'abstractNumId',
                    // but only when definition with 'abstractNumId = 0' exists.
                    if (containsAbstractNumId(reader, abstractNumId))
                        return;

                    break;
                }
                // w15:restartNumberingAfterBreak
                // Element that specifies that list numbering restarts at each section.
                case /*"restartNumberingAfterBreak"*/8:
                {
                    reader.getComplianceInfo().markAsHasDocxExtensionsOf(MsWordVersionCore.WORD_2013);
                    restartNumberingAfterBreak = xmlReader.getValueAsBool();
                    break;
                }
                default:
                    // Do nothing.
                    break;
            }
        }

        int lvlOrderNumber = 0;
        while (xmlReader.readChild("abstractNum"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"nsid"*/9:
                    lstidSpecified = true;
                    lsid = NrxXmlUtil.tryHexToInt(xmlReader.readVal());

                    if (lsid != Integer.MIN_VALUE)
                    {
                        // WORDSNET-25569 Handle w:nsid element located at the end of numbering definition.
                        if (listDef != null)
                            listDef.setListDefId(lsid);
                    }

                    break;
                case /*"multiLevelType"*/10:
                    listType = DocxNumberingEnum.docxToListType(xmlReader.readVal());
                    break;
                case /*"tmpl"*/11:
                    templateCode = NrxXmlUtil.hex8ToInt(xmlReader.readVal());
                    break;
                case /*"name"*/12:
                    name = xmlReader.readVal();
                    break;
                case /*"styleLink"*/13:
                    sti = xmlReader.readVal();
                    break;
                case /*"numStyleLink"*/14:
                    sti = xmlReader.readVal();
                    break;
                case /*"lvl"*/15:
                    if (listDef == null)
                    {
                        listDef = new ListDef(doc, lsid, listType, templateCode);
                        listDef.setName(name);
                    }

                    int ilvl = xmlReader.readIntAttribute("ilvl", 0);
                    // WORDSNET-12762 Mimic MS Word behavior: if type of numbering definition is 'singleLevel'
                    // and numbering XML contains several levels, all levels except a level of index 0 are ignored.
                    if ((listType == ListType.SINGLE_LEVEL) && (ilvl > 0))
                        break;

                    // andrnosk: WORDSNET-7639 Mimic MS Word behavior, if value equals -1 then clear formatting
                    // of the first level numbering and do not read the formatting of the current level.
                    // We should consider refactoring WmlListReader and DocxNumberingReader there are lots of duplications.
                    if (ilvl == -1 && lvlOrderNumber > 0)
                    {
                        listDef.getLevels().get(0).setNumberStyle(NumberStyle.NONE);
                        listDef.getLevels().get(0).setNumberFormat("");
                        listDef.getLevels().get(0).getParaPr().clear();
                        reader.getXmlReader().ignoreElement();
                    }
                    else
                    {
                        // If ilvl of the first is invalid, just ignore and read first NumLevel as usual.
                        if ((ilvl == -1) || (ilvl >= listDef.getLevels().getCount()))
                            ilvl = 0;

                        readNumLevel(listDef.getLevels().get(ilvl), reader);
                    }

                    lvlOrderNumber++;
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        // If ListDef with this nsid is duplicated in document, then MS Word reuses it.
        ListDef existingListDef = doc.getLists().getListDefByListDefId(lsid);
        if (existingListDef != null)
        {
            reader.getAbstractNumIdToListDef().put(abstractNumId, existingListDef);
        }
        else
        {
            if (listDef == null)
            {
                listDef = new ListDef(doc, lsid, listType, templateCode);
                listDef.setName(name);
            }

            // WORDSNET-20828 nsid can be omitted in abstractNum definition.
            // In this case we set default lsid, this caused incorrect numbering in the resulting document.
            // To fix the problem added flag that indicates whether nsid is specified, if not we use abstractNumId as lsid.
            // WORDSNET-4174 MS Word generates random nsids. We should do the same to avoid merging lists with missed nsids.
            // See details in ListCollection.ImportList() comments.
            if (!lstidSpecified)
            {
                lsid = doc.getLists().makeUniqueListDefId();
                listDef.setListDefId(lsid);
            }

            if (sti != null)
                listDef.setListStyleIstd(reader.resolveStyleIdToIstd(sti, StyleIndex.NO_LIST));

            listDef.isRestartAtEachSection(restartNumberingAfterBreak);

            doc.getLists().addListDef(listDef);
            reader.getAbstractNumIdToListDef().put(abstractNumId, listDef);
        }
    }

    /// <summary>
    /// Reads 'w:lvl' element from 'w:listDef' children.
    /// </summary>
    private void readNumLevel(ListLevel level, DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        boolean isTentative = false;

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"tplc"*/16:
                    // We can extract 'tplc' now without using it as it is not in the model yet.
                    NrxXmlUtil.hex8ToInt(xmlReader.getValue());
                    break;
                case /*"tentative"*/17:
                    isTentative = xmlReader.getValueAsBool();
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }
        xmlReader.moveToElement();

        // TODO 3 w:tplc attribute - not found in DOC
        level.IsTentative = isTentative;
        // We have to set this because default value is 1 in the model, but 0 in the OOXML standard.
        level.setStartAt(0);

        readLevelChildren(level, reader);
    }

    private void readLevelChildren(ListLevel level, DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        String tagName = xmlReader.getLocalName();

        mIsLevelStartRead = false;
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"start"*/18:
                {
                    level.setStartAtSafe(xmlReader.readIntVal());
                    mIsLevelStartRead = true;
                    break;
                }
                case /*"numFmt"*/19:
                    level.setNumberStyle(DocxEnum.docxToNumberStyle(xmlReader.readVal()));
                    if (level.getNumberStyle() == NumberStyle.CUSTOM)
                        level.setCustomNumberStyle(xmlReader.readAttribute("format", ""));
                    break;
                case /*"lvlRestart"*/20:
                    // The value is 1-based in WordML but 0 based in the model.
                    level.setRestartAfterLevelSafe(xmlReader.readIntVal() - 1);
                    break;
                case /*"pStyle"*/21:
                    level.setParaStyleIstd(reader.resolveStyleIdToIstd(xmlReader.readVal(), StyleIndex.NIL));
                    break;
                case /*"isLgl"*/22:
                    level.isLegal(xmlReader.readBoolVal());
                    break;
                case /*"suff"*/23:
                    level.setTrailingCharacter(DocxNumberingEnum.docxToListTrailingCharacter(xmlReader.readVal()));
                    break;
                case /*"lvlText"*/24:
                    while (xmlReader.moveToNextAttribute())
                    {
                        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                        {
                            case /*"val"*/30:
                                level.setNumberFormatSafe(NrxXmlUtil.xmlToListLevel(xmlReader.getValue()));
                                break;
                            case /*"null"*/31:
                                if (xmlReader.getValueAsBool())
                                    level.setNumberFormatSafe("");
                                break;
                            default:
                                // Do nothing.
                                break;
                        }
                    }
                    break;
                case /*"lvlPicBulletId"*/25:
                    level.setPictureBulletId(xmlReader.readIntVal());

                    // Translate picture bullet ids to zero base.
                    int translatedId = reader.getPictureBulletTranslation().indexOf(level.getPictureBulletId());
                    if (translatedId >= 0)
                        level.setPictureBulletId(translatedId);
                    break;
                case /*"legacy"*/26:
                    while (xmlReader.moveToNextAttribute())
                    {
                        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                        {
                            case /*"legacy"*/26:
                                level.setLegacy(xmlReader.getValueAsBool());
                                break;
                            case /*"legacySpace"*/32:
                                level.setLegacySpace(xmlReader.getValueAsTwips(complianceInfo));
                                break;
                            case /*"legacyIndent"*/33:
                                level.setLegacyIndent(xmlReader.getValueAsTwips(complianceInfo));
                                break;
                            default:
                                Debug.fail(xmlReader.getLocalName());
                                break;
                        }
                    }
                    break;
                case /*"lvlJc"*/27:
                    level.setAlignment(DocxNumberingEnum.docxToListLevelAlignment(xmlReader.readVal(), complianceInfo));
                    break;
                case /*"pPr"*/28:
                    mParaPrReader.read(reader, level.getParaPr(), level.getRunPr());
                    break;
                case /*"rPr"*/29:
                    mRunPrReader.read(reader, level.getRunPr());
                    break;
                case /*"AlternateContent"*/5:
                    readAlternateContent(level, reader);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads an alternate content block. Now it can contain only the numFmt element.
    /// </summary>
    private void readAlternateContent(ListLevel level, DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        boolean choiceWasRead = false;

        while (xmlReader.readChild("AlternateContent"))
        {
            // Choice and Fallback, are used to provide alternates for specified content.
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"Choice"*/34:
                    choiceWasRead = readChoice(level, reader);
                    break;
                case /*"Fallback"*/35:
                {
                    if (!choiceWasRead)
                        readLevelChildren(level, reader);
                    else
                        xmlReader.ignoreElementNoWarn();
                    break;
                }
                default:
                {
                    xmlReader.ignoreElement(WarningType.UNEXPECTED_CONTENT, WarningSource.DOCX,
                        xmlReader.getLocalName());
                    break;
                }
            }
        }
    }

    /// <summary>
    /// Reads elements of the choice part of an alternate content block.
    /// </summary>
    private boolean readChoice(ListLevel level, DocxDocumentReaderBase reader) throws Exception
    {
        boolean choiceMatched = false;
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"Requires"*/36:
                {
                    // The Requires attribute specifies a set of space-delimited namespaces that must be
                    // understood in order to select that choice.
                    String prefix = xmlReader.getValue();
                    choiceMatched = StringUtil.hasChars(prefix) &&
                        NrxRunReaderBase.isAllowedChoiceRequirement(xmlReader.lookupNamespace(prefix));
                    break;
                }
                default:
                    xmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DOCX, xmlReader.getLocalName());
                    break;
            }
        }
        xmlReader.moveToElement();

        if (choiceMatched)
            readLevelChildren(level, reader);
        else
            xmlReader.ignoreElement();

        return choiceMatched;
    }

    /// <summary>
    /// Reads 'w:list' element from 'w:lists' children.
    /// </summary>
    private void readNum(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        DocumentBase doc = reader.getDocument();

        int ilfo = xmlReader.readIntAttribute("numId", 0);

        // WORDSNET-28555 If a list with the specified ListId already exists, it shall be used.
        List list = doc.getLists().getListByListId(ilfo);
        if (list == null)
            list = new List(doc, ilfo);

        list.setDurableId(xmlReader.readIntAttribute("durableId", 0));

        while (xmlReader.readChild("num"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"abstractNumId"*/7:
                {
                    ListDef listDef = DictionaryExtension.getValueOrNull(reader.getAbstractNumIdToListDef(), xmlReader.readIntVal());
                    if (listDef != null)
                        list.setListDefId(listDef.getListDefId());
                    break;
                }
                case /*"lvlOverride"*/37:
                    {
                        int ilvl = xmlReader.readIntAttribute("ilvl", 0);
                        ListLevelOverride levelOverride = new ListLevelOverride(doc, ilvl);
                        levelOverride.IsStartAt = false;
                        levelOverride.IsFormatting = false;
                        while (xmlReader.readChild("lvlOverride"))
                        {
                            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                            {
                                case /*"startOverride"*/38:
                                    levelOverride.IsStartAt = true;
                                    levelOverride.StartAtRaw = xmlReader.readIntVal();
                                    break;
                                case /*"lvl"*/15:
                                    levelOverride.IsFormatting = true;
                                    readNumLevel(levelOverride.getListLevel(), reader);
                                    break;
                                default:
                                    xmlReader.ignoreElement();
                                    break;
                            }
                        }

                        // WORDSNET-21002 Try to set missing level start from the 'lvlOverride' tag.
                        // WORDSNET-21125 Do not set value, that is not fit ListLevel.IsStartAtValid.
                        if (!mIsLevelStartRead && levelOverride.IsStartAt && ListLevel.isStartAtValid(levelOverride.StartAtRaw))
                            levelOverride.getListLevel().setStartAt(levelOverride.StartAtRaw);

                        list.getOverrides().add(levelOverride);
                        break;
                    }
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        validateOverrides(list);

        doc.getLists().addList(list);
    }

    /// <summary>
    /// Returns true if <see cref="NrxDocumentReaderBase.AbstractNumIdToListDef"/> contains specified
    /// <paramref name="abstractNumId"/> and 'abstractNumId' equal to 0 already exists or passed now.
    /// </summary>
    private static boolean containsAbstractNumId(DocxDocumentReaderBase reader, int abstractNumId)
    {
        return (abstractNumId == 0) &&
               reader.getAbstractNumIdToListDef().containsKey(abstractNumId);
    }

    /// <summary>
    /// Validates list level overrides.
    /// </summary>
    /// <remarks>
    /// WORDSNET-14514 If list contains at least one <see cref="ListLevelOverride.IsStartAt"/>
    /// set to 'true', then MS Word sets 0 for 'StartOverride' for all level overrides
    /// that were not explicitly read in DOCX.
    /// </remarks>
    private static void validateOverrides(List list)
    {
        if (!hasOverriddenStartAt(list))
            return;

        for (ListLevelOverride levelOverride : list.getOverrides())
        {
            if (!levelOverride.IsStartAt)
            {
                levelOverride.StartAtRaw = 0;
                levelOverride.IsStartAt = true;
            }
        }
    }

    /// <summary>
    /// Returns true if list contains at least one <see cref="ListLevelOverride.IsStartAt"/> set to true.
    /// </summary>
    private static boolean hasOverriddenStartAt(List list)
    {
        for (ListLevelOverride levelOverride : list.getOverrides())
        {
            if (levelOverride.IsStartAt)
                return true;
        }

        return false;
    }

    private /*final*/ DocxParaPrReader mParaPrReader;
    private /*final*/ DocxRunPrReader mRunPrReader;
    private /*final*/ INrxVmlReader mVmlReader;

    private boolean mIsLevelStartRead;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"numPicBullet",
		"abstractNum",
		"num",
		"numIdMacAtCleanup",
		"pict",
		"AlternateContent",
		"drawing",
		"abstractNumId",
		"restartNumberingAfterBreak",
		"nsid",
		"multiLevelType",
		"tmpl",
		"name",
		"styleLink",
		"numStyleLink",
		"lvl",
		"tplc",
		"tentative",
		"start",
		"numFmt",
		"lvlRestart",
		"pStyle",
		"isLgl",
		"suff",
		"lvlText",
		"lvlPicBulletId",
		"legacy",
		"lvlJc",
		"pPr",
		"rPr",
		"val",
		"null",
		"legacySpace",
		"legacyIndent",
		"Choice",
		"Fallback",
		"Requires",
		"lvlOverride",
		"startOverride"
	);

}

