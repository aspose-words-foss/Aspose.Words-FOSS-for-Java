// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/03/2013 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import com.aspose.HashUtil;
import com.aspose.DigestAlgorithm;
import org.testng.Assert;
import com.aspose.StringUtil;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;


/// <summary>
/// Tests importing documents from Azure storage services.
/// </summary>
@Test
public class TestImportFromAzure
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// WORDSNET-25139 Image is not loaded properly if the document is loaded from LazyLoadingReadOnlyStream.
    /// The check for complete reading of data from LazyLoadingReadOnlyStream has been added.
    /// </summary>
    /// <remarks>
    /// LazyLoadingReadOnlyStream does not return all the data at once if their size exceeds 4Mb.
    /// ZipEntry.GetCompressedBytes() did not check the stream for full reading, which led to the fact that
    /// the buffer that was further used as the basis for all OpcPackagePart streams was filled partially in some cases.
    /// </remarks>
    @Test
    public void test25139() throws Exception
    {
        LazyLoadingReadOnlyStreamMock stream =
            new LazyLoadingReadOnlyStreamMock(TestUtil.buildTestFileName("ImportDocx\\Test25139.docx"));
        try /*JAVA: was using*/
        {
            Document doc = new Document(stream);
            Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
            // Checks the hash of the problematic shape.
            byte[] hash = HashUtil.computeHash(DigestAlgorithm.MD_5, shape.getImageData().getImageBytes());
            msAssert.areEqual("cf06235e0dd07e87e21875582e390cde", StringUtil.bytesToHex(hash).toLowerCase());
        }
        finally { if (stream != null) stream.dispose(); }
    }
}

/// <summary>
/// Helper class that emulates LazyLoadingReadOnlyStream behavior in terms of chunk reading of a file located in Azure Blob storage.
/// </summary>
class LazyLoadingReadOnlyStreamMock extends FileStream
{
    public LazyLoadingReadOnlyStreamMock(String testFile) throws Exception
    {
    	super(testFile, FileMode.OPEN);
	
    }

    public /*override*/ int read(byte[] buffer, int offset, int count)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	int bufferSize = Math.min(count, DEFAULT_AZURE_BLOB_BUFFER_SIZE);
        	return super.read(buffer, offset, bufferSize);
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    private static final int DEFAULT_AZURE_BLOB_BUFFER_SIZE = 4 * 1024 * 1024;
}

