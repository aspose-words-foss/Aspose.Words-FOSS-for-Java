// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/05/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import java.util.ArrayList;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Extracts auto text building block from templates document.
/// </summary>
class AutoTextEntryExtractor
{
    AutoTextEntryExtractor(Document document)
    {
        mDocument = document;
    }

    BuildingBlock extract(String entryName) throws Exception
    {
        ensureGlossaryDocuments();

        for (GlossaryDocument document : mGlossaryDocuments)
        {
            BuildingBlock buildingBlock = extract(document, entryName);
            if (buildingBlock != null)
                return buildingBlock;
        }

        return null;
    }

    private static BuildingBlock extract(GlossaryDocument document, String entryName)
    {
        for (BuildingBlock buildingBlock : (Iterable<BuildingBlock>) document.getBuildingBlocks())
        {
            // NOTE: ECMA $17.16.5.5 restricts gallery attribute to autotext, but MS Word does not after the Insert (Tab) -> Text (Group) -> Quick Parts menu is opened.
            if (buildingBlock.getGallery() == BuildingBlockGallery.NO_GALLERY)
                continue;

            if (!StringUtil.equalsIgnoreCase(buildingBlock.getName(), entryName))
                continue;

            return buildingBlock;
        }

        return null;
    }

    private void ensureGlossaryDocuments() throws Exception
    {
        if (mGlossaryDocuments != null)
            return;

        mGlossaryDocuments = new ArrayList<GlossaryDocument>(mDocument.getFieldOptions().getBuiltInTemplatesPaths().length + 2);

        addGlossaryDocument(mDocument);
        tryLoadTemplateDocument(mDocument.getAttachedTemplate());
        for (String path : mDocument.getFieldOptions().getBuiltInTemplatesPaths())
            tryLoadTemplateDocument(path);
    }

    private void tryLoadTemplateDocument(String path) throws Exception
    {
        if (!StringUtil.hasChars(path))
            return;

        try
        {
            Stream stream = Document.openDocumentStream(path, mDocument.getResourceLoadingCallback());
            try /*JAVA: was using*/
            {
                Document document = new Document(stream, null, false);
                addGlossaryDocument(document);
            }
            finally { if (stream != null) stream.close(); }
        }
        catch(Exception e)
        {
            // Suppress any exeptions
        }
    }

    private void addGlossaryDocument(Document document)
    {
        if (document.getGlossaryDocument() != null)
            msArrayList.add(mGlossaryDocuments, document.getGlossaryDocument());
    }

    private /*final*/ Document mDocument;

    private ArrayList<GlossaryDocument> mGlossaryDocuments;
}

