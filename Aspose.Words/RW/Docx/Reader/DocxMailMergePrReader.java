// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/01/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.ms.System.msString;


/// <summary>
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
class DocxMailMergePrReader extends NrxMailMergePrReaderBase
{
    /// <summary>
    /// Reads 2.14.27 recipientData (Reference to Inclusion/Exclusion Data for Data Source)
    /// </summary>
    protected /*override*/ void readRecipientData(NrxDocumentReaderBase reader, Odso odso) throws Exception
    {
        Debug.assertTrue(reader instanceof DocxDocumentReaderBase);
        DocxDocumentReaderBase docReader = (DocxDocumentReaderBase)reader;

        String relId = docReader.getXmlReader().readId();
        docReader.switchToPartReaderByRelId(relId);
        try
        {
            NrxXmlReader xmlReader = docReader.getXmlReader();

            // RK I had a document created with MS Word 2007 Beta and it writes this data in a different format, 
            // let's ignore for now. If someone really wants it we can support it. Reading is not a problem,
            // just need to specify different content type when writing, different namespace and also write hash
            // instead of unique tag.
            if (!msString.equals(xmlReader.getNamespaceURI(), DocxNamespaces.getNamespace(DocxNamespace.MAIN, docReader.getComplianceInfo().isIsoStrict())))
                return;

            while (xmlReader.readChild("recipients"))
            {
                switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                {
                    case /*"recipientData"*/0:
                        super.readRecipientData(docReader, odso);
                        break;
                    default:
                        xmlReader.ignoreElement();
                        break;
                }
            }
        }
        finally
        {
            docReader.restorePartReader();
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"recipientData"
	);

}
