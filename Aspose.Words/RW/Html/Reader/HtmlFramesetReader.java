// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/06/2015 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ConvertUtilCore;


/// <summary>
/// Helps to import HTML framesets http://www.w3.org/TR/html4/present/frames.html into a Document object.
/// </summary>
class HtmlFramesetReader
{
    HtmlFramesetReader(Document document,
                                String baseUri,
                                HtmlResourceLoader resourceLoader)
    {
        Debug.assertTrue(document != null);
        Debug.assertTrue(resourceLoader != null);
        mDocument = document;
        mBaseUri = baseUri;
        mResourceLoader = resourceLoader;
    }

    /// <summary>
    /// Reads frameset structure declared in the HTML frameset element into <see cref="Document.Frameset"/> object.
    /// </summary>
    /// <param name="framesetNode">HTML frameset element.</param>
    void read(HtmlElementNode framesetNode) throws Exception
    {
        Debug.assertTrue(framesetNode != null);
        Debug.assertTrue("frameset".equals(framesetNode.getName()));

        Frameset rootFrame = new Frameset();
        readFrameset(framesetNode, rootFrame);
        mDocument.setFrameset(rootFrame);
    }

    private void readFrameset(HtmlElementNode framesetNode, Frameset frame) throws Exception
    {
        String[] frameWidths = new String[0];
        String cols = framesetNode.getAttributes().getAttributeValue("cols", "");
        if (!msString.equals(cols, ""))
        {
            frame.setLayoutType(FrameLayoutType.HORIZONTAL);
            frameWidths = msString.split(cols, ',');
        }
        else
        {
            String rows = framesetNode.getAttributes().getAttributeValue("rows", "");
            if (!msString.equals(rows, ""))
            {
                frame.setLayoutType(FrameLayoutType.VERTICAL);
                frameWidths = msString.split(rows, ',');
            }
        }
        // Chrome browser shows only the first frame if there are no cols or rows attributes. We do the same.
        if (frameWidths.length == 0)
            frameWidths = new String[] { "*" };

        int childIndex = 0;
        for (String frameWidth : frameWidths)
        {
            HtmlElementNode frameNode = null;
            while ((childIndex < framesetNode.getChildren().getCount()) && (frameNode == null))
            {
                HtmlElementNode childNode = ms.as(framesetNode.getChildren().get(childIndex), HtmlElementNode.class);
                if ((childNode != null) && (("frame".equals(childNode.getName())) || ("frameset".equals(childNode.getName()))))
                    frameNode = childNode;
                childIndex++;
            }
            if (frameNode == null)
                return;

            Frameset childFrame = new Frameset();
            frame.getChildFramesets().add(childFrame);

            setFrameWidth(frameWidth, childFrame);
            switch (gStringSwitchMap.of(frameNode.getName()))
            {
                case /*"frame"*/0:
                {
                    setFrameProperties(frameNode, childFrame);
                    break;
                }
                case /*"frameset"*/1:
                {
                    readFrameset(frameNode, childFrame);
                    break;
                }
                default:
                {
                    Debug.assertTrue(false);
                    break;
                }
            }
        }
    }

    private void setFrameProperties(HtmlElementNode frameNode, Frameset frame) throws Exception
    {
        frame.setTitle(frameNode.getAttributes().getAttributeValue("title", ""));
        frame.setName(frameNode.getAttributes().getAttributeValue("name", ""));
        frame.setMarginX(frameNode.getAttributes().getAttributeValue("marginwidth", 0));
        frame.setMarginY(frameNode.getAttributes().getAttributeValue("marginheight", 0));
        if (frameNode.getAttributes().getAttributeValue("frameborder", 0) == 1)
        {
            frame.setFramesetBorderType(FramesetBorderType.SIMPLE);
            // In DOCX default width of the splitters is 4.5 points wide.
            frame.setFramesetBorderWidth(ConvertUtilCore.pointToTwip(4.5));
        }
        frame.setNoResize("noresize".equals(frameNode.getAttributes().getAttributeValue("noresize", "")));
        switch (gStringSwitchMap.of(frameNode.getAttributes().getAttributeValue("scrolling", "")))
        {
            case /*"yes"*/2:
                frame.setScrollType(FrameScrollType.YES);
                break;
            case /*"no"*/3:
                frame.setScrollType(FrameScrollType.NO);
                break;
            case /*"auto"*/4:
                frame.setScrollType(FrameScrollType.AUTO);
                break;
            default:
                break;
        }

        String fileName = HtmlUtil.validateUri(frameNode.getAttributes().getAttributeValue("src", ""));
        if (!msString.equals(fileName, ""))
        {
            frame.setFrameDefaultUrlInternal(fileName);
            frame.setFileData(mResourceLoader.loadHtmlDocument(mBaseUri, fileName));
        }
    }

    private static void setFrameWidth(String frameWidthStr, Frameset frame)
    {
        CssValue value = CssParser.parseValue(msString.trim(frameWidthStr));
        if (value == null)
        {
            frame.setDividerPositionType(FrameDividerPositionType.RELATIVE);
        }
        else
        {
            switch (value.getValueType())
            {
                case CssValueType.LENGTH:
                {
                    // It doesn't matter which dimension is used - px, pt or another, Chrome uses px in any case. We do the same.
                    frame.setDividerPositionType(FrameDividerPositionType.PIXEL);
                    frame.setDividerPositionValue((int)((CssLengthValue)value).getDoubleValue());
                    break;
                }
                case CssValueType.NUMBER:
                {
                    frame.setDividerPositionType(FrameDividerPositionType.PIXEL);
                    frame.setDividerPositionValue((int)((CssNumberValue)value).getDoubleValue());
                    break;
                }
                case CssValueType.PERCENTAGE:
                {
                    frame.setDividerPositionType(FrameDividerPositionType.PERCENTAGE);
                    frame.setDividerPositionValue((int)((CssPercentageValue)value).getDoubleValue());
                    break;
                }
                default:
                {
                    frame.setDividerPositionType(FrameDividerPositionType.RELATIVE);
                    break;
                }
            }
        }
    }

    private /*final*/ Document mDocument;
    private /*final*/ String mBaseUri;
    private /*final*/ HtmlResourceLoader mResourceLoader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"frame",
		"frameset",
		"yes",
		"no",
		"auto"
	);

}

