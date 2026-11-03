// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/11/2015 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import java.util.HashMap;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.StringUtil;
import com.aspose.ms.System.Collections.msDictionary;


class HtmlVmlShapeReader
{
    HtmlVmlShapeReader(DocumentBuilder builder, HtmlResourceLoader resourceLoader)
    {
        Debug.assertTrue(builder != null);

        mBuilder = builder;
        mResourceLoader = resourceLoader;
        mNamespaces = new HashMap<String, String>();
        mShapeTemplates = new HashMap<String, ShapePr>();
    }

    /// <summary>
    /// Processes 'html' element.
    /// </summary>
    void handleHtml(HtmlElementNode node)
    {
        Debug.assertTrue("html".equals(node.getName()));

        for (HtmlAttribute attribute : node.getAttributes())
        {
            if ((NrxNamespaces.OFFICE.equals(attribute.getValue())) ||
                (NrxNamespaces.VML.equals(attribute.getValue())) ||
                (NrxNamespaces.WORD.equals(attribute.getValue())))
            {
                final String XMLNS_PREFIX = "xmlns:";
                if (msString.startsWith(attribute.getName(), XMLNS_PREFIX, StringComparison.ORDINAL))
                {
                    String namespacePrefix = attribute.getName().substring(XMLNS_PREFIX.length());
                    if (StringUtil.hasChars(namespacePrefix) && (namespacePrefix.indexOf(':') < 0))
                    {
                        msDictionary.add(mNamespaces, namespacePrefix, attribute.getValue());
                    }
                }
            }
        }
    }

    /*HandleNodeAction*/int handleVml(HtmlElementNode node) throws Exception
    {
        ShapeBase shape;
        try
        {                
            String serializedXml = node.serializeToXml();
            HtmlVmlShapeReaderContext context = new HtmlVmlShapeReaderContext(
                mBuilder,
                mNamespaces,
                mShapeTemplates,
                mResourceLoader,
                serializedXml);

            shape = VmlShapeReader.readShapeLevelElement(context);
        }
        catch(Exception e)
        {
            // WORDSNET-14901, WORDSNET-13555 Ignore invalid VML.
            if (mBuilder.getDocument().getWarningCallback() != null)
            {
                mBuilder.getDocument().getWarningCallback().warning(new WarningInfo(WarningType.DATA_LOSS, WarningSource.HTML,
                    "Malformed VML shape is ignored"));
            }
            return HandleNodeAction.HANDLED_SKIP_CHILDREN;
        }

        if ((shape != null) && (shape.getParentNode() == null))
            mBuilder.insertNode(shape);

        return HandleNodeAction.HANDLED_SKIP_CHILDREN;
    }

    private /*final*/ DocumentBuilder mBuilder;
    private /*final*/ HtmlResourceLoader mResourceLoader;
    private /*final*/ HashMap<String, String> mNamespaces;
    private /*final*/ HashMap<String, ShapePr> mShapeTemplates;
}

