// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/07/2011 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// Implements framed document writing.
/// </summary>
/*static*/ class NrxFramesetWriter
{
	/* Simulation of static class by using private constructor */
	private NrxFramesetWriter()
	{}

    static void write(Document doc, NrxXmlBuilder builder, boolean isDocx, String docxFramesetRelType)
    {
        // DOCX ISO29500 MS Implementation notes say that:
        // 17.15.2.19. frameset (Root Frameset Definition) (a) The standard does not state the behavior of an empty frameset element.
        // Word treats an empty frameset as though it contains a single frame element (using its default values).
        // same applies to nested framesets, so please rework.
        // 
        // AM. I don't like to write empty Root frameset though. Its unclear for me how frame properties should be applied to Document. 
        // Anyway it can be corrected when AW get frameset public API.
        if ((doc.getFrameset() == null) || doc.getFrameset().getChildFramesets().getCount() == 0)
            return;

        write(builder, doc.getFrameset(), isDocx, true, docxFramesetRelType);
    }

    /// <summary>
    /// Writes frame or frameset element.
    /// </summary>
    /// <remarks>
    /// AM. Althoutgh in DOCX frame and frameset are different elements I use one procedure for writing them both 
    /// because in Model we have one class for frame and frameset and there are several common attributes in both elements.
    /// </remarks>
    /// <param name="builder"></param>
    /// <param name="frame"></param>
    /// <param name="isDocx"></param>
    /// <param name="isRoot"></param>
    /// <param name="docxFramesetRelType"></param>
    private static void write(NrxXmlBuilder builder, Frameset frame, boolean isDocx, boolean isRoot, String docxFramesetRelType)
    {
        builder.startElement(frame.hasChild() ? "w:frameset" : "w:frame");

        if (isRoot)
        {
            // AM. Althought DOCX can have framesetSplitbar element for nested frame, seems Word doens't support it.
            writeFramesetBorder(builder, frame);
        }
        else
        {
            // Write sz for all frame/frameset except root frameset.
            writeSz(builder, frame);
        }

        if (frame.hasChild()) 
        {
            // FrameLayoutType.None is only allowed for frame object i.e empty frameset.
            Debug.assertTrue(frame.getLayoutType() != FrameLayoutType.NONE);
            builder.writeVal("w:frameLayout", StyleConvertUtil.frameLayoutTypeToNrx(frame.getLayoutType()));
        }
        else
        {
            builder.writeValIfPositive("w:MarH", frame.getMarginX());
            builder.writeValIfPositive("w:MarV", frame.getMarginY());

            builder.writeVal("w:name", frame.getName());

            if (frame.getFrameDefaultUrl() != null)
            {
                if (isDocx)
                {
                    String relId =
                        ((DocxBuilder)builder).getPart().getRels().add(docxFramesetRelType, frame.getFrameDefaultUrl(), true);
                    builder.startElement("w:sourceFileName");
                    builder.writeAttributeString("r:id", relId);
                    builder.endElement();
                }
                else
                {
                    builder.writeVal("w:sourceFileName", frame.getFrameDefaultUrl());
                }
            }

            if (frame.getScrollType() != FrameScrollType.AUTO)
                builder.writeVal("w:scrollbar", (frame.getScrollType() == FrameScrollType.YES) ? "on" : "off");
        }

        builder.writeValIfTrue("w:noResizeAllowed", frame.getNoResize());
        builder.writeValIfTrue("w:linkedToFile", frame.isFrameLinkToFile());

        builder.writeVal("w:title", frame.getTitle());

        for (Frameset childFrame : frame.getChildFramesets())
            write(builder, childFrame, isDocx, false, docxFramesetRelType);

        builder.endElement();
    }

    /// <summary>
    /// Writes frameset border element.
    /// </summary>
    /// <remarks>
    /// AM. Althought DOCX can have framesetSplitbar element for nested frame, seems Word doens't support it. 
    /// At least I couldn't create document with the indepenent borders so I write this only for root frame.
    /// </remarks>
    /// <param name="builder"></param>
    /// <param name="frame"></param>
    private static void writeFramesetBorder(NrxXmlBuilder builder, Frameset frame)
    {

        builder.startElement("w:framesetSplitbar");

        builder.writeValIfNotDefault("w:w", frame.getFramesetBorderWidth(), 0);
        builder.writeVal("w:color", frame.getFramesetBorderColor());

        String nrxFramesetBorderType = StyleConvertUtil.framesetBorderTypeToNrx(frame.getFramesetBorderType());
        if(StringUtil.hasChars(nrxFramesetBorderType))
            builder.writeVal(msString.format("w:{0}", nrxFramesetBorderType), true);

        builder.endElement();
    }

    /// <summary>
    /// Writes frame or frameset sz element. Doesn't write default value.
    /// </summary>
    /// <param name="builder"></param>
    /// <param name="frame"></param>
    private static void writeSz(NrxXmlBuilder builder, Frameset frame)
    {
        String sz = "";
        if (frame.getDividerPositionType() == FrameDividerPositionType.PERCENTAGE)
            sz = "%";
        else if (frame.getDividerPositionType() == FrameDividerPositionType.RELATIVE)
            sz = "*";

        sz = msString.format("{0}{1}", frame.getDividerPositionValue(), sz);
        if (!"1*".equals(sz))
            builder.writeVal("w:sz", sz);
    }
}

