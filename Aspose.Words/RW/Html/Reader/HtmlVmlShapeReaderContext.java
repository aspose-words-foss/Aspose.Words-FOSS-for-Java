// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2015 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Map;
import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


class HtmlVmlShapeReaderContext implements IVmlShapeReaderContext
{
    HtmlVmlShapeReaderContext(
        DocumentBuilder builder,
        Map<String, String> namespaces,
        Map<String, ShapePr> shapeTemplates,
        HtmlResourceLoader resourceLoader,
        String shapeXml)
    {
        Debug.assertTrue(builder != null);
        Debug.assertTrue(namespaces != null);
        Debug.assertTrue(shapeTemplates != null);
        Debug.assertTrue(resourceLoader != null);

        mBuilder = builder;
        mShapeTemplates = shapeTemplates;
        mResourceLoader = resourceLoader;
        mXmlReader = new NrxXmlReader(shapeXml, namespaces);
    }

    public DocumentBase /*IVmlShapeReaderContext.*/getDocument() { return mBuilder.getDocument(); }

    public NrxXmlReader /*IVmlShapeReaderContext.*/getXmlReader() { return mXmlReader; }

    public int /*IVmlShapeReaderContext.*/getGroupNestingLevel() { return mGroupNestingLevel; }; public void /*IVmlShapeReaderContext.*/setGroupNestingLevel(int value) { mGroupNestingLevel = value; };

    private int /*IVmlShapeReaderContext.*/ mGroupNestingLevel;

    public IEmbeddedObject /*IVmlShapeReaderContext.*/getEmbeddedObject(String id)
    {
        return null;
    }

    public byte[] /*IVmlShapeReaderContext.*/getBinData(String src) throws Exception
    {
        return mResourceLoader.loadImage(mBuilder.getDocument().getBaseUri(), src);
    }

    /// <summary>
    /// There are no related parts, just returns the value of the parameter.
    /// </summary>
    public String /*IVmlShapeReaderContext.*/getRelationshipTarget(String relId)
    {
        return relId;
    }

    public boolean /*IVmlShapeReaderContext.*/isExternalImage(String src) throws Exception
    {
        byte[] imageBytes = mResourceLoader.loadImage(mBuilder.getDocument().getBaseUri(), src);
        return imageBytes == null;
    }

    public void /*IVmlShapeReaderContext.*/addToZOrderList(ShapeBase shape)
    {
        // Do nothing.
    }

    public void /*IVmlShapeReaderContext.*/addToShapeMap(String shapeId, ShapeBase shape)
    {
        // Do nothing.
    }

    public ShapeBase /*IVmlShapeReaderContext.*/getShapeById(String shapeId)
    {
        return null;
    }

    public void /*IVmlShapeReaderContext.*/readBinData()
    {
        // Do nothing.
    }

    public boolean /*IVmlShapeReaderContext.*/readTextboxContent(ShapeBase shape) throws Exception
    {
        Debug.assertTrue(shape != null);

        Paragraph para = new Paragraph(mBuilder.getDocument());
        shape.appendChild(para);

        Paragraph currentParagraph = mBuilder.getCurrentParagraph();
        mBuilder.moveTo(para);

        StringBuilder outerHtml = new StringBuilder();
        // Only plain HTML is allowed as textbox content (no nested VML shapes), so we don't write namespace declarations
        // on the root HTML element.
        msStringBuilder.append(outerHtml, "<html>");
        while (!mXmlReader.isEndElement("textbox") && mXmlReader.readChild("textbox"))
        {
            msStringBuilder.append(outerHtml, mXmlReader.readOuterXml());
        }
        msStringBuilder.append(outerHtml, "</html>");

        HtmlReader htmlReader = new HtmlReader(new HtmlReaderSettings(), LoadFormat.HTML, mResourceLoader);
        htmlReader.read(outerHtml.toString(), mBuilder, HtmlInsertOptions.NONE, true);

        mBuilder.moveTo(currentParagraph);

        return false;
    }

    public void /*IVmlShapeReaderContext.*/addShapeType(String id, ShapePr shapePr)
    {
        if (StringUtil.hasChars(id))
            mShapeTemplates.put(id, shapePr);
    }

    public ShapePr /*IVmlShapeReaderContext.*/getShapeTypePr(String id)
    {
        if (msString.startsWith(id, "#", com.aspose.ms.System.StringComparison.ORDINAL))
            id = id.substring(1);

        return (StringUtil.hasChars(id) && mShapeTemplates.containsKey(id))
            ? mShapeTemplates.get(id)
            : null;
    }

    public void /*IVmlShapeReaderContext.*/markMissingSource(Shape shape, String src)
    {
        throw new UnsupportedOperationException("This is intended for NrxDocumentReaderBase only so far.");
    }

    public String /*IVmlShapeReaderContext.*/getMissingSource(Shape shape)
    {
        throw new UnsupportedOperationException("This is intended for NrxDocumentReaderBase only so far.");
    }

    private /*final*/ DocumentBuilder mBuilder;
    private /*final*/ Map<String, ShapePr> mShapeTemplates;
    private /*final*/ HtmlResourceLoader mResourceLoader;
    private /*final*/ NrxXmlReader mXmlReader;
}

