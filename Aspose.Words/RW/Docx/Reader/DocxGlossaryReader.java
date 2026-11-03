// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/02/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.OpcPackageBase;
import com.aspose.OpcPackagePart;
import com.aspose.ms.System.Guid;


class DocxGlossaryReader extends DocxDocumentReaderBase
{
    DocxGlossaryReader(
        OpcPackageBase _package,
        OpcPackagePart documentPart,
        GlossaryDocument doc,
        LoadOptions loadOptions,
        OoxmlComplianceInfo complianceInfo,
        DocxStylesReader stylesReader,
        DocxStoryReader storyReader,
        DocxNumberingReader numberingReader,
        DocxSectPrReader sectPrReader) throws Exception
    {
    	super(_package, documentPart, doc, loadOptions, complianceInfo, stylesReader, storyReader, numberingReader, sectPrReader);
	
        mGlossaryDoc = doc;
    }

    /*override*/ boolean isEquationXmlReader() { return false; }

    protected /*override*/ void doRead() throws Exception
    {
        while (getXmlReader().readChild("glossaryDocument"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"background"*/0:
                    DocxBackgroundReader.read(this);
                    break;
                case /*"docParts"*/1:
                    readDocParts();
                    break;
                default:
                    getXmlReader().ignoreElement();
                    break;
            }
        }
    }

    private void readDocParts() throws Exception
    {
        while (getXmlReader().readChild("docParts"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"docPart"*/2:
                    readDocPart();
                    break;
                default:
                    getXmlReader().ignoreElement();
                    break;
            }
        }
    }

    private void readDocPart() throws Exception
    {
        mBlock = new BuildingBlock(mGlossaryDoc);
        addAndPushContainer(mBlock);

        while (getXmlReader().readChild("docPart"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"docPartPr"*/3:
                    readDocPartPr();
                    break;
                case /*"docPartBody"*/4:
                    readBody(mGlossaryDoc);
                    endSection();
                    break;
                default:
                    getXmlReader().ignoreElement();
                    break;
            }
        }

        popContainer(NodeType.BUILDING_BLOCK);
    }

    private void readDocPartPr()
    {
        while (getXmlReader().readChild("docPartPr"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"behaviors"*/5:
                    readBehaviors();
                    break;
                case /*"category"*/6:
                    readCategory();
                    break;
                case /*"description"*/7:
                    mBlock.setDescription(getXmlReader().readVal());
                    break;
                case /*"guid"*/8:
                    mBlock.setGuidInternal(new Guid(getXmlReader().readVal()));
                    break;
                case /*"name"*/9:
                    readName();
                    break;
                case /*"style"*/10:
                    mBlock.setStyle(getXmlReader().readVal());
                    break;
                case /*"types"*/11:
                    readTypes();
                    break;
                default:
                    getXmlReader().ignoreElement();
                    break;
            }
        }
    }

    private void readBehaviors()
    {
        while (getXmlReader().readChild("behaviors"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"behavior"*/12:
                    mBlock.setBehavior(DocxEnum.docxToDocPartBehavior(getXmlReader().readVal()));
                    break;
                default:
                    getXmlReader().ignoreElement();
                    break;
            }
        }
    }

    private void readCategory()
    {
        while (getXmlReader().readChild("category"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"gallery"*/13:
                    mBlock.setGallery(DocxEnum.docxToDocPartGallery(getXmlReader().readVal()));
                    break;
                case /*"name"*/9:
                    mBlock.setCategorySafe(getXmlReader().readVal());
                    break;
                default:
                    getXmlReader().ignoreElement();
                    break;
            }
        }
    }

    private void readName()
    {
        while (getXmlReader().moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"decorated"*/14:
                    mBlock.setDecorated(getXmlReader().getValueAsBool());
                    break;
                case /*"val"*/15:
                    mBlock.setNameSafe(getXmlReader().getValue());
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }
    }

    private void readTypes()
    {
        boolean isAll = false;
        while (getXmlReader().moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"all"*/16:
                    isAll = getXmlReader().getValueAsBool();
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        while (getXmlReader().readChild("types"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"type"*/17:
                    mBlock.setType(DocxEnum.docxToDocPartType(getXmlReader().readVal()));
                    break;
                default:
                    getXmlReader().ignoreElement();
                    break;
            }
        }

        if (isAll)
            mBlock.setType(BuildingBlockType.ALL);
    }

    private /*final*/ GlossaryDocument mGlossaryDoc;
    private BuildingBlock mBlock;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"background",
		"docParts",
		"docPart",
		"docPartPr",
		"docPartBody",
		"behaviors",
		"category",
		"description",
		"guid",
		"name",
		"style",
		"types",
		"behavior",
		"gallery",
		"decorated",
		"val",
		"all",
		"type"
	);

}

