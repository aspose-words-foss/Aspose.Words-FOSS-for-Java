// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/01/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;


class DocxSectPrReader extends NrxSectPrReaderBase
{
    void setAnnotationReader(DocxAnnotationReader annotationReader)
    {
        Debug.assertTrue(annotationReader != null);
        mAnnotationReader = annotationReader;
    }

    protected /*override*/ void readFormatSpecific(String localName, 
        NrxDocumentReaderBase reader, SectPr sectPr, 
        boolean hasBlockLevelSectPr, boolean hasHeadersFooters) throws Exception
    {
        switch (gStringSwitchMap.of(localName))
        {
            // Reads header/footer from DOCX.
            case /*"headerReference"*/0:
            case /*"footerReference"*/1:
            {
                if (!hasBlockLevelSectPr && !hasHeadersFooters)
                    DocxHeaderFooterReader.read(reader);
                break;
            }
            case /*"footnotePr"*/2:
            case /*"endnotePr"*/3:
            {
                DocxSettingsReader.readFootnotePr((DocxDocumentReaderBase)reader, localName, sectPr);
                break;
            }
            case /*"sectPrChange"*/4:
            {
                mAnnotationReader.readSectPrRevision(reader, sectPr);
                break;
            }
            default:
            {
                // Ignore.
                break;
            }
        }
    }

    private DocxAnnotationReader mAnnotationReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"headerReference",
		"footerReference",
		"footnotePr",
		"endnotePr",
		"sectPrChange"
	);

}

