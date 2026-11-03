// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/01/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Xml.XmlTextReader;
import com.aspose.ms.System.Xml.XmlNodeType;
import com.aspose.ms.System.msString;


/// <summary>
/// Base class, helps to read a hyperlink DOCX or WML element.
/// </summary>
abstract class NrxHyperlinkReaderBase
{
    /// <summary>
    /// Reads a hyperlink element.
    /// RK Hyperlink in DOCX is very different from hlink in WordML.
    /// </summary>
    abstract void read(NrxDocumentReaderBase reader) throws Exception;

    void setInlineReader(NrxInlineReaderBase reader)
    {
        Debug.assertTrue(reader != null);
        inlineReader = reader;
    }

    protected void readChildren(NrxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        xmlReader.moveToElement();

        String tagName = xmlReader.getLocalName();
        int parentDepth = xmlReader.getUnderlyingReader().getDepth();
        XmlTextReader underlyingReader = xmlReader.getUnderlyingReader();

        while (true)
        {
            boolean hasMoreChildren = xmlReader.readChild(tagName);

            // WORDSNET-22087 Do not stop reading children when occurs child with same name as parent.
            // Hyperlink may contain nested "hlink"/"hyperlink" elements for WML/DOCX format.
            if (!hasMoreChildren)
            {
                if (underlyingReader.getDepth() == parentDepth)
                    break;

                if (!moveToNextElement(underlyingReader, tagName, parentDepth))
                    break;
            }

            inlineReader.readChild(reader);
        }
    }

    /// <summary>
    /// Moves to the start of the next element in the stream. 
    /// </summary>
    /// <param name="reader">Xml reader.</param>
    /// <param name="parentName">Tag name of the parent element.</param>
    /// <param name="parentDepth">Parent depth.</param>
    /// <returns>Returns true if the element was read. Returns false if reached the end of the specified element or the end of file.</returns>
    private boolean moveToNextElement(XmlTextReader reader, String parentName, int parentDepth)
    {
        reader.moveToElement();

        while (reader.read())
        {
            switch (reader.getNodeType())
            {
                case XmlNodeType.ELEMENT:
                {
                    // Found an element, return it to the caller.
                    return true; 
                }
                case XmlNodeType.END_ELEMENT:
                {
                    // If reached the end of the parent element, signal the caller to stop.
                    if ((msString.equals(reader.getLocalName(), parentName)) && (reader.getDepth() == parentDepth))
                        return false;
                    break;
                }
                default:
                    break;
            }
        }

        // End of file.
        return false;
    }

    protected NrxInlineReaderBase inlineReader;
}

