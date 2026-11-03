// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2023 by Edward Voronov

package com.aspose;


import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Xml.XmlTextReader;
import com.aspose.ms.java.IO.NetInputStream;
import com.aspose.ms.java.IO.NetOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;


/// <summary>Transforms XML data using an XSLT style sheet.</summary>
/// <seealso cref="XslCompiledTransform"/>
public final class XslTransform
{
    /// <summary>Compiles the style sheet contained in the <see cref="Stream" /> object.</summary>
    public void load(Stream stylesheet) throws Exception
    {
        TransformerFactory factory = TransformerFactory.newInstance();
        transformer = factory.newTransformer( new StreamSource( new NetInputStream(stylesheet)));
    }

    /// <summary>Executes the transform using the input document specified by the <see cref="Stream" /> object and outputs the results to a stream.</summary>
    public void transformTo(Stream input, Stream result) throws Exception
    {
        StreamSource xmlSource = new StreamSource( new NetInputStream(input));
        StreamResult output = new StreamResult( new NetOutputStream(result));
        transformer.transform(xmlSource, output);
        result.setPosition(0);
    }

    /// <summary>Executes the transform using the input document specified by the <see cref="Stream" /> object and outputs the results to a stream.</summary>
    public Stream transform(Stream input) throws Exception
    {
        Stream result = new MemoryStream();
        transformTo(input, result);
        return result;
    }

    private Transformer transformer;
}

