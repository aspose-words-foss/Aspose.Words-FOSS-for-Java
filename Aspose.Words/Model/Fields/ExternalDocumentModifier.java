// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/06/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntToIntBidirectionalMap;


/// <summary>
/// Used when copying nodes from an external document. Uses <see cref="NodeImporter"/> to import the nodes.
/// </summary>
class ExternalDocumentModifier implements INodeModifier
{
    // WORDSNET-14587 It looks like Word uses UseDestinationStyles mode when updates fields. See for example
    // TestNodeImporter.TestJira14587UpdateField and also some tests in TestLinksAndReferences: TestInclude(), TestIncludeText().
    // So switched to UseDestinationStyles mode by default instead of KeepSourceFormatting.
    ExternalDocumentModifier(DocumentBase sourceDocument, DocumentBase destinationDocument, IntToIntBidirectionalMap importedIstds)
    {
    	this(sourceDocument, destinationDocument, ImportFormatMode.USE_DESTINATION_STYLES, importedIstds);
	
    }

    ExternalDocumentModifier(DocumentBase sourceDocument, DocumentBase destinationDocument, /*ImportFormatMode*/int importFormatMode)
    {
    	this(sourceDocument, destinationDocument, importFormatMode, null);
	
    }

    private ExternalDocumentModifier(DocumentBase sourceDocument, DocumentBase destinationDocument, /*ImportFormatMode*/int importFormatMode, IntToIntBidirectionalMap importedIstds)
    {
        ImportContext context = new ImportContext(sourceDocument, destinationDocument, importFormatMode, null, importedIstds);
        mNodeImporter = new NodeImporter(sourceDocument, destinationDocument, context);
    }

    public Node /*INodeModifier.*/modify(Node referenceNode, Node nodeToModify, boolean modifyChildren, INodeCloningListener cloningListener)
    {
        return mNodeImporter.importNode(nodeToModify, modifyChildren, cloningListener);
    }

    private /*final*/ NodeImporter mNodeImporter;
}

