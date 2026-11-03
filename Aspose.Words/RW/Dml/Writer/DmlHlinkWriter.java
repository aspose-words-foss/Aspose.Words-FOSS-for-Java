// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/07/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;
import com.aspose.UriUtil;
import com.aspose.StringUtil;


/// <summary>
/// Writer for DrawingML Click/Mouse-Over Hyperlink.
/// </summary>
/*static*/ class DmlHlinkWriter
{
	/* Simulation of static class by using private constructor */
	private DmlHlinkWriter()
	{}

    /// <summary>
    /// Writes hlinkClick, hlinkHover, hlinkMouseOver.
    /// 5.1.5.3.5 hlinkClick (Click Hyperlink)
    /// 5.1.2.1.23 hlinkHover (Hyperlink for Hover)
    /// 21.1.2.3.5 hlinkClick (Click Hyperlink)
    /// 21.1.2.3.6 hlinkMouseOver (Mouse-Over Hyperlink)
    /// </summary>
    static void writeHlink(
        String elementName,
        String targetId,
        String targetFrame,
        String tooltip,
        StringToObjDictionary<DmlExtension> extList,
        IDmlShapeWriterContext writer) throws Exception
    {
        // WORDSNET-23935 Hyperlink maximum length, determined experimentally.
        final int HYPERLINK_TARGET_MAX_LENGTH = 2235;

        if (targetId == null)
            return;

        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement(elementName);
        builder.writeAttribute("xmlns:a", DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_MAIN, isIsoStrict));
        // Escaping some characters in Href may cause the string to be extended beyond the maximum value.
        if (UriUtil.escapeHref(UriUtil.unescapeHref(targetId)).length() > HYPERLINK_TARGET_MAX_LENGTH)
            targetId = "";
        // WORDSNET-5640 hLinkAddress can be empty per spec.
        String hyperlinkTarget = StringUtil.hasChars(targetId) ? writer.addHyperlinkRelationship(targetId) : "";
        builder.writeAttributeString("r:id", hyperlinkTarget);
        builder.writeAttribute("xmlns:r", DocxNamespaces.getNamespace(DocxNamespace.RELATIONSHIPS, isIsoStrict));
        builder.writeAttribute("tgtFrame", targetFrame);
        builder.writeAttribute("tooltip", tooltip);
        DmlExtensionListWriter.write(extList, writer);
        builder.endElement();
    }
}

