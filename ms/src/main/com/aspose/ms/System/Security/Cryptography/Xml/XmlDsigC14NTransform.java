// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//07/02/12 by Durin Vyacheslav
package com.aspose.ms.System.Security.Cryptography.Xml;

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.IO.JavaOnlyStreamUtil;
import com.aspose.ms.java.debug.JavaDebug;
import com.aspose.ms.java.logging.Log;

import javax.xml.crypto.OctetStreamData;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.TransformService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Java implementation of
 * http://msdn.microsoft.com/en-us/library/system.security.cryptography.xml.xmldsigc14ntransform.aspx
 */
public class XmlDsigC14NTransform
{
    public void loadInput(Stream xml)
    {
        // transformation without comments
        try
        {
            xml.setPosition(0);
            byte[] xmlBytes = JavaOnlyStreamUtil.copyStreamToByteArray(xml);
            xml.setPosition(0);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlBytes);
            OctetStreamData octetStreamData = new OctetStreamData(byteArrayInputStream);
            TransformService transformService = TransformService.getInstance(CanonicalizationMethod.INCLUSIVE, "DOM");
            OctetStreamData output = (OctetStreamData) transformService.transform(octetStreamData, null);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JavaOnlyStreamUtil.copyStream(output.getOctetStream(), outputStream);
            canonicalXml = outputStream.toByteArray();
        }
        catch (Exception e)
        {
            Log.warn(e);
        }
    }

    public Stream getOutput(Class<MemoryStream> memoryStreamClass)
    {
        return new MemoryStream(canonicalXml);
    }

    private byte[] canonicalXml;
}
