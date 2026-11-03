// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/03/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.OpcPackageBase;
import com.aspose.OpcPackagePart;


/// <summary>
/// Reads the main document and associated parts from OOXML.
/// </summary>
class DocxDocumentReader extends DocxDocumentReaderBase
{
    DocxDocumentReader(
        OpcPackageBase _package,
        OpcPackagePart documentPart,
        Document doc,
        LoadOptions loadOptions,
        OoxmlComplianceInfo complianceInfo,
        DocxStylesReader stylesReader,
        DocxStoryReader storyReader,
        DocxNumberingReader numberingReader,
        DocxSectPrReader sectPrReader) throws Exception
    {
    	super(_package, documentPart, doc, loadOptions, complianceInfo, stylesReader, storyReader, numberingReader, sectPrReader);
	
        mMainDoc = doc;
        mStoryReader = storyReader;
    }

    /*override*/ boolean isEquationXmlReader() { return false; }

    protected /*override*/ void doRead() throws Exception
    {
        DocxCustomXmlPartReader.read(this, getLoadOptions().getWarningCallback());

        // WORDSNET-14302 Theme should be loaded before main document since it is needed for font resolution that occurs
        // on accessing the Font property of a run. The Font property may be called on reading/parsing fields.
        DocxThemeReader.read(this);

        readDocument();
        resolveMarkup();

        DocxVbaReader.read(this);
        DocxCustomizationsReader.read(this);

        readGlossary();
        validatePageSize();
    }

    /// <summary>
    /// Imports the "Main Document" package part.
    /// </summary>
    private void readDocument() throws Exception
    {
        // WORDSNET-6928 It will be needed to determine whether we should trim leading and trailing white-spaces from runs.
        setPreserveSpace(("preserve".equals(getXmlReader().readAttribute("space", ""))));

        while (getXmlReader().readChild("document"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"background"*/0:
                    DocxBackgroundReader.read(this);
                    break;
                case /*"body"*/1:
                    readBody(mMainDoc);
                    break;
                default:
                    resilientBodyRead();
                    break;
            }
        }
        endSection();
    }

    /// <summary>
    /// Read content outside body. 
    /// <remarks>andrnosk: WORDSNET-5252</remarks>
    /// </summary>
    private void resilientBodyRead() throws Exception
    {
        // Document can contain some content outside body, if such content encountered before body,
        // we should start a new section. 
        startSection(mMainDoc);

        // Read content outside body into the current section.
        mStoryReader.readChild(this);
    }

    private void readGlossary() throws Exception
    {
        OpcPackagePart glossaryPart = getPartByRelationshipType(getDocumentPart(), getRelTypes().getGlossaryDocument());
        if (glossaryPart == null)
            return;

        mMainDoc.setGlossaryDocument(new GlossaryDocument());
        DocxGlossaryReader glossaryReader = DocxReaderFactory.createGlossaryReader(
            getPackage(), glossaryPart, mMainDoc.getGlossaryDocument(), getLoadOptions(), mMainDoc.getComplianceInfo());
        glossaryReader.read();
        glossaryReader.resolveMarkup();
    }

    /// <summary>
    /// Validates page size. It should not exceeds the limit.
    /// </summary>
    private void validatePageSize()
    {
        // WORDSNET-20656 We need to truncate page size if it's too big.
        // The spec says that the maximum page size is 31680 twips, but Word actually
        // truncates it to 61056 twips (3052.8 points), so do we.

        final double MAXIMUM_SIZE = 3052.8;

        for (Section section : (Iterable<Section>) getDocument().getSections())
        {
            if (section.getPageSetup().getPageWidth() > MAXIMUM_SIZE)
            {
                section.getPageSetup().setPageWidth(MAXIMUM_SIZE);
                warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.DOCX, "PageWidth has been truncated.");
            }

            if (section.getPageSetup().getPageHeight() > MAXIMUM_SIZE)
            {
                section.getPageSetup().setPageHeight(MAXIMUM_SIZE);
                warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.DOCX, "PageHeight has been truncated.");
            }
        }
    }

    
    @ReservedForInternalUse @Deprecated
    public /*new*/ Document getDocument() { return mMainDoc; }

    private /*final*/ Document mMainDoc;
    private /*final*/ DocxStoryReader mStoryReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"background",
		"body"
	);

}

