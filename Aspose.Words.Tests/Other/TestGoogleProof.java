// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.util.ArrayList;
import java.util.HashMap;
import com.aspose.BytesHash;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.File;
import com.aspose.StreamUtil;
import com.aspose.HashUtil;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ms.System.IO.Directory;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.TimeSpan;
import com.aspose.ms.System.msNumber;
import com.aspose.ms.System.IO.Path;
import com.aspose.StringUtil;
import com.aspose.ms.System.Guid;
 


@Test
public class TestGoogleProof
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test (enabled = false, description = "GoogleProof")
    public void testGoogle() throws Exception
    {
        testGoogleDirectory("E:\\GoogleProof\\Doc", LoadFormat.DOC);
    }

    @Test (enabled = false, description = "GoogleProof")
    public void testDeleteRepeatingFiles() throws Exception
    {
        Deleter d = new Deleter();
        d.execute("F:\\GoogleProof\\Doc\\8441");
    }

    private static class Deleter
    {
        private ArrayList<String> mAllFiles;

        void execute(String dir) throws Exception
        {
            mAllFiles = new ArrayList<String>();
            collectAllFiles(dir);

            HashMap<BytesHash, String> guids = new HashMap<BytesHash, String>();

            for (String fileName : mAllFiles)
            {
                byte[] bytes;
                Stream stream = File.openRead(fileName);
                try /*JAVA: was using*/
            	{
                    bytes = StreamUtil.copyStreamToByteArray(stream);
            	}
                finally { if (stream != null) stream.close(); }

                BytesHash guid = HashUtil.getSHA512Hash(bytes);
                if (guids.containsKey(guid))
                {
                    Debug.writeLine("Deleting " + fileName);
                    File.delete(fileName);
                }
                else
                {
                    msDictionary.add(guids, guid, fileName);
                }
            }
        }

        private void collectAllFiles(String dir) throws Exception
        {
            String[] files = Directory.getFiles(dir);
            for (String file : files)
                msArrayList.add(mAllFiles, file);

            String[] subDirs = Directory.getDirectories(dir);
            for (String subDir : subDirs)
                collectAllFiles(subDir);
        }
    }

    private static void testGoogleDirectory(String dir, /*LoadFormat*/int loadFormat) throws Exception
    {
        Debug.writeLine("");
        Debug.writeLine("Reading directory {0}.", dir);

        String[] files = Directory.getFiles(dir);
        for (String file : files)
            testGoogleFile(file, loadFormat);

        String[] subDirs = Directory.getDirectories(dir);
        for (String subDir : subDirs)
            testGoogleDirectory(subDir, loadFormat);
    }

    private static void testGoogleFile(String fileName, /*LoadFormat*/int loadFormat) throws Exception
    {
        try
        {
            Debug.write(fileName);
            DateTime start = DateTime.getNow();
            
            Document doc = new Document(fileName);

            if (doc.getOriginalLoadFormat() != loadFormat)
            {
                String dstDir = "F:\\GoogleProof\\111_" + FileFormatUtil.loadFormatToExtension(doc.getOriginalLoadFormat()).substring(1);
                if (!Directory.exists(dstDir))
                    Directory.createDirectory(dstDir);

                moveFile(
                    fileName, 
                    dstDir, 
                    FileFormatUtil.loadFormatToExtension(doc.getOriginalLoadFormat()), 
                    msString.format("Not an {0} file.", FileFormatUtil.loadFormatToExtension(loadFormat)));
            }
            else
            {
                TimeSpan elapsed = DateTime.subtract(DateTime.getNow(), start);
                Debug.writeLine(" " + msNumber.formatDouble(elapsed.getTotalSeconds(), "F2") + "s.");
                if (elapsed.getTotalSeconds() > 3)
                    Debug.writeLine("*** SLOW ***");
            }
        }
        catch (UnsupportedFileFormatException e)
        {
            moveFile(fileName, "F:\\GoogleProof\\111_Unsupported", null, e.getMessage());
        }
        catch (Exception e)
        {
            Debug.writeLine("");
            Debug.writeLine("File '" + fileName + "'. " + e.toString());
        }
    }

    private static void moveFile(String fileName, String newDir, String newExtension, String reason) throws Exception
    {
        String dstFileName = Path.combine(newDir, Path.getFileName(fileName));

        if (StringUtil.hasChars(newExtension))
            dstFileName = Path.changeExtension(dstFileName, newExtension);

        if (File.exists(dstFileName))
            dstFileName = dstFileName + Guid.newGuid().toString() + Path.getExtension(dstFileName);

        Debug.writeLine("");
        Debug.writeLine(msString.format("Moving file {0} to {1}. {2}", fileName, dstFileName, reason));
        File.move(fileName, dstFileName);
    }

    @Test (enabled = false, description = "GoogleProof")
    public void tesXXX() throws Exception
    {
        TestUtil.open("E:\\GoogleProof\\Doc\\mazda\\39.doc");
    }
}

