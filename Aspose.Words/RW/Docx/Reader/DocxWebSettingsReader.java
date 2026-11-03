// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/// <summary>
/// Provides static method for reading "Web Settings" package part
/// </summary>
/*static*/ class DocxWebSettingsReader
{
	/* Simulation of static class by using private constructor */
	private DocxWebSettingsReader()
	{}

    /// <summary>
    /// Reads "Web Settings" part for the specified document.
    /// </summary>
    static void read(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.switchToPartReaderByRelType(reader.getRelTypes().getWebSettings());
        if (xmlReader == null)
            return;

        DocumentBase doc = reader.getDocument();
        DocPr docPr = doc.getDocPr();

        while (xmlReader.readChild("webSettings"))    // w:webSettings
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"frameset"*/0:
                {
                    Frameset frameset = new Frameset();
                    // Default value.
                    frameset.setFramesetBorderType(FramesetBorderType.RAISED);
                    ((Document)doc).setFrameset(frameset);
                    NrxFramesetReader.read(reader, frameset, true);
                    break;
                }
                case /*"divs"*/1:
                    NrxSettingsReader.readDivs(reader);
                    break;
                case /*"encoding"*/2:
                    docPr.WebPageEncoding = xmlReader.readVal();
                    break;
                case /*"optimizeForBrowser"*/3:
                    docPr.OptimizeForBrowser = xmlReader.readBoolVal();
                    while(xmlReader.moveToNextAttribute())
                    {
                        if ("target".equals(xmlReader.getLocalName())) // iso29500 specific.
                        {
                            reader.getComplianceInfo().markAsIsoTransitional();
                            docPr._WebTarget = DocxDopEnum.docxToWebTarget(xmlReader.getValue());
                        }
                    }
                    break;
                case /*"relyOnVML"*/4:
                    docPr.RelyOnVml = xmlReader.readBoolVal();
                    break;
                case /*"allowPNG"*/5:
                    docPr.AllowPng = xmlReader.readBoolVal();
                    break;
                case /*"doNotRelyOnCSS"*/6:
                    docPr.DoNotRelyOnCss = xmlReader.readBoolVal();
                    break;
                case /*"doNotSaveAsSingleFile"*/7:
                    docPr.DoNotSaveWebPagesAsSingleFile = xmlReader.readBoolVal();
                    break;
                case /*"doNotOrganizeInFolder"*/8:
                    docPr.DoNotOrganizeInFolder = xmlReader.readBoolVal();
                    break;
                case /*"doNotUseLongFileNames"*/9:
                    docPr.DoNotUseLongFileNames = xmlReader.readBoolVal();
                    break;
                case /*"pixelsPerInch"*/10:
                    docPr.PixelsPerInch = xmlReader.readIntVal();
                    break;
                case /*"targetScreenSz"*/11:
                    docPr.TargetScreenSize = StyleConvertUtil.xmlToScreenSize(xmlReader.readVal());
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        reader.restorePartReader();
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"frameset",
		"divs",
		"encoding",
		"optimizeForBrowser",
		"relyOnVML",
		"allowPNG",
		"doNotRelyOnCSS",
		"doNotSaveAsSingleFile",
		"doNotOrganizeInFolder",
		"doNotUseLongFileNames",
		"pixelsPerInch",
		"targetScreenSz"
	);

}

