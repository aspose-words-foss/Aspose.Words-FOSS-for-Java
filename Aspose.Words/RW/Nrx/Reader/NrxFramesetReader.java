// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/07/2011 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.FormatterPal;


/// <summary>
/// Implements framed document reading.
/// </summary>
/*static*/ class NrxFramesetReader
{
	/* Simulation of static class by using private constructor */
	private NrxFramesetReader()
	{}

    /// <summary>
    /// Reads frameset element.
    /// </summary>
    /// <param name="reader"></param>
    /// <param name="frameset">Frame object being read.</param>
    /// <param name="isDocx">Indicates that DOCX is being read.</param>
    static void read(NrxDocumentReaderBase reader, Frameset frameset, boolean isDocx)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.readChild("frameset"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"framesetSplitbar"*/0:
                    readFramesetSplitBar(reader, frameset);
                    break;
                case /*"frameLayout"*/1:
                    frameset.setLayoutType(StyleConvertUtil.nrxToFrameLayoutType(xmlReader.readVal()));
                    break;
                case /*"sz"*/2:
                    setFrameDividerPosition(frameset, xmlReader.readVal());
                    break;
                case /*"title"*/3: 
                    frameset.setTitle(xmlReader.readVal());
                    break;
                case /*"frame"*/4:
                    Frameset childFrame = new Frameset();
                    frameset.getChildFramesets().add(childFrame);
                    readFrame(reader, childFrame, isDocx);
                    break;
                case /*"frameset"*/5:
                    Frameset childFrameset = new Frameset();
                    frameset.getChildFramesets().add(childFrameset);
                    read(reader, childFrameset, isDocx);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads framesetSplitbar element which specifies frameset border options located in root frame.
    /// </summary>
    /// <param name="reader"></param>
    /// <param name="frame"></param>
    private static void readFramesetSplitBar(NrxDocumentReaderBase reader, Frameset frame)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();

        while (xmlReader.readChild("framesetSplitbar"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"flatBorders"*/6:
                case /*"noBorder"*/7:
                {
                    boolean val = xmlReader.readBoolVal();
                    if (val)
                    {
                        // Although I haven't seen these elements with false value, spec says that it's possible.
                        frame.setFramesetBorderType(StyleConvertUtil.nrxToFramesetBorderType(xmlReader.getLocalName()));
                    }
                    break;
                }
                case /*"color"*/8:
                    frame.setFramesetBorderColor(NrxXmlUtil.xmlToColor(xmlReader.readVal()));
                    break;
                case /*"w"*/9:
                    frame.setFramesetBorderWidth(xmlReader.readValAsTwips(complianceInfo));
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads <see cref="Frameset" /> element.
    /// </summary>
    /// <param name="reader"></param>
    /// <param name="frame"></param>
    /// <param name="isDocx"></param>
    private static void readFrame(NrxDocumentReaderBase reader, Frameset frame, boolean isDocx)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.readChild("frame"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"title"*/3:
                    frame.setTitle(xmlReader.readVal());
                    break;
                case /*"name"*/10:
                    frame.setName(xmlReader.readVal());
                    break;
                case /*"sourceFileName"*/11:
                    frame.setFrameDefaultUrlInternal((isDocx) 
                        ? reader.getRelationshipTarget(xmlReader.readAttribute("id", null)) 
                        : xmlReader.readVal());
                    break;
                case /*"scrollbar"*/12:
                    setFrameScrollType(frame, xmlReader.readVal());
                    break;
                case /*"noResizeAllowed"*/13:
                    frame.setNoResize(xmlReader.readBoolVal());
                    break;
                case /*"linkedToFile"*/14:
                    frame.setIsFrameLinkToFile(xmlReader.readBoolVal());
                    break;
                case /*"sz"*/2:
                    setFrameDividerPosition(frame, xmlReader.readVal());
                    break;
                case /*"marH"*/15:
                    frame.setMarginX(xmlReader.readIntVal());
                    break;
                case /*"marV"*/16:
                    frame.setMarginY(xmlReader.readIntVal());
                    break;
                case /*"longDesc"*/17:  
                    // Present in the ISO29500 spec as (Frame Long Description) §17.15.2.23, 
                    // but ignored by MS Word as per ISO29500 MS Impl Notes.
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Extract <see cref="FrameScrollType"/> value from string.
    /// </summary>
    /// <returns></returns>
    private static void setFrameScrollType(Frameset frame, String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"on"*/18:
                frame.setScrollType(FrameScrollType.YES);
                break;
            case /*"off"*/19:
                frame.setScrollType(FrameScrollType.NO);
                break;
            case /*"auto"*/20:
                frame.setScrollType(FrameScrollType.AUTO);
                break;
            default:
                throw new IllegalStateException(msString.format("Invalid scroll type value {0}.", value));
        }
    }

    /// <summary>
    /// Extracts <see cref="FrameDividerPositionType"/> and FrameDividerPositionValue from string.
    /// </summary>
    /// <param name="frame"></param>
    /// <param name="value"></param>
    private static void setFrameDividerPosition(Frameset frame, String value)
    {
        // MS Impl Notes lays the following restrictions on the values:
        // 17.15.2.40. sz (Frame Size) (a) The standard allows any string for the val attribute.
        // For the val attribute, Word allows an integer between 1 and 32,767 followed by *; an integer between 1 and 500 followed by %
        // or an integer which divided by pixelsPerInch gives a value between 0 and 22.
        // (b) The standard says if this element is omitted, then no information shall be implied about the size of the current frameset.
        // Word considers the default value to be 1*. Same applies to nested frames.

        if (msString.endsWith(value, "%", StringComparison.ORDINAL))
        {
            // Value type is Percentage.
            frame.setDividerPositionType(FrameDividerPositionType.PERCENTAGE);
        }
        else if (msString.endsWith(value, "*", StringComparison.ORDINAL))
        {
            // Value type is Relative.
            frame.setDividerPositionType(FrameDividerPositionType.RELATIVE);
        }
        else
        {
            // Value type is Pixel.
            frame.setDividerPositionType(FrameDividerPositionType.PIXEL);
        }

        value = msString.trim(value, G_DIVIDER_CHARS_TO_TRIM);
        frame.setDividerPositionValue(FormatterPal.tryParseInt(value));
    }

    private static /*final*/ char[] G_DIVIDER_CHARS_TO_TRIM = new char[] { '*', '%' };

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"framesetSplitbar",
		"frameLayout",
		"sz",
		"title",
		"frame",
		"frameset",
		"flatBorders",
		"noBorder",
		"color",
		"w",
		"name",
		"sourceFileName",
		"scrollbar",
		"noResizeAllowed",
		"linkedToFile",
		"marH",
		"marV",
		"longDesc",
		"on",
		"off",
		"auto"
	);

}

