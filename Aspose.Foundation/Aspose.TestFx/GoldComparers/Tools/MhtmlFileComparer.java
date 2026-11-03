// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2016 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.MemoryStream;
import java.util.ArrayList;
import com.aspose.ms.System.IO.StreamReader;
import com.aspose.ms.System.IO.StreamWriter;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.IO.TextReader;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.Convert;
import com.aspose.words.ref.Ref;


/// <summary>
/// Compares two MHTML files.
/// Files are treated as text but base64 image parts are extracted and compared as images.
/// </summary>
class MhtmlFileComparer extends TextFileComparer
{
    public /*override*/ void execute(ComparerParams comparerParams) throws Exception
    {
        executeInternal(
            comparerParams.getTitle(),
            comparerParams.getFileNameOut(),
            comparerParams.getFileNameGold(),
            comparerParams.getFileNameSrc()
            );
    }

    /// <summary>
    /// Compares user file with the gold file. Brings up the user interface when there is a difference.
    /// </summary>
    /// <param name="title">The dialog title.</param>
    /// <param name="userFileName">The out file.</param>
    /// <param name="goldFileName">The gold file.</param>
    /// <param name="sourceFileName">The source file name.</param>
    private static void executeInternal(
        String title,
        String userFileName,
        String goldFileName,
        String sourceFileName) throws Exception
    {
        MhtmlFileComparer comparer = new MhtmlFileComparer();
        comparer.executeCore(title, userFileName, goldFileName, null, sourceFileName);
    }

    /// <summary>
    /// This is a template method that we overide to compare mhtml files.
    /// </summary>
    protected /*override*/ void doCompareFiles(String fileName1, String fileName2) throws Exception
    {
        compareMhtmlFiles(fileName1, fileName2);
    }

    /// <summary>
    /// Compares everything as text but base64 image parts as images.
    /// </summary>
    private static void compareMhtmlFiles(String userFileName, String goldFileName) throws Exception
    {
        if (!File.exists(userFileName) || !File.exists(goldFileName))
            throw new GoldDifferenceException(userFileName, goldFileName);

        Stream userFileStream = File.openRead(userFileName);
        try /*JAVA: was using*/
        {
            Stream goldFileStream = File.openRead(goldFileName);
            try /*JAVA: was using*/
            {
                MemoryStream userTemplate = new MemoryStream();
                ArrayList<MhtmlFileComparer.MhtmlImagePart> userImages = new ArrayList<MhtmlFileComparer.MhtmlImagePart>();
                splitMhtml(userFileStream, userTemplate, userImages);
                userTemplate.setPosition(0);

                MemoryStream goldTemplate = new MemoryStream();
                ArrayList<MhtmlFileComparer.MhtmlImagePart> goldImages = new ArrayList<MhtmlFileComparer.MhtmlImagePart>();
                splitMhtml(goldFileStream, goldTemplate, goldImages);
                goldTemplate.setPosition(0);

                // This compares the readable part of MHTML.
                compareTextFiles(userTemplate, goldTemplate, userFileName, goldFileName);

                // Compare images. Image count should match at this point
                // since main comparison has succeeded.
                int imageCount = userImages.size();
                Debug.assertTrue(imageCount == goldImages.size());

                for (int i = 0; i < imageCount; ++i)
                {
                    MhtmlImagePart userImage = userImages.get(i);
                    MemoryStream userImageStream = new MemoryStream(userImage.ImageData);

                    MhtmlImagePart goldImage = goldImages.get(i);
                    MemoryStream goldImageStream = new MemoryStream(goldImage.ImageData);

                    // F0SS
                    ImageFileComparer.compareImageFiles(userImageStream, goldImageStream, userImage.ContentLocation, goldImage.ContentLocation);
                }
            }
            finally { if (goldFileStream != null) goldFileStream.close(); }
        }
        finally { if (userFileStream != null) userFileStream.close(); }
    }

    /// <summary>
    /// Splits MHTML archive into main "template" and image subsidiaries.
    /// </summary>
    /// <param name="fileStream">Source file, either user or gold.</param>
    /// <param name="mainTemplate">Writes Main template here.</param>
    /// <param name="imageParts">Array of image data for every binary part.</param>
    private static void splitMhtml(Stream fileStream, Stream mainTemplate, ArrayList<MhtmlFileComparer.MhtmlImagePart> imageParts) throws Exception
    {
        final String BOUNDARY = "--=boundary.Aspose.Words=--";

        // We don't dispose them. Otherwise they will close streams.
        StreamReader sr = new StreamReader(fileStream);
        StreamWriter sw = new StreamWriter(mainTemplate);

        String line = sr.readLine();
        while (line != null)
        {
            sw.writeLine(line);

            if (BOUNDARY.equals(line))
            {
                String contentType = "";
                String contentTransferEncoding = "";
                String contentLocation = "";
                String contentId = "";

                // Remember the lines for the case we'll compare this part as text.
                ArrayList<String> linesToFlush = new ArrayList<String>();

                // This will skip empty line and stop before the base64 data
                while (((line = sr.readLine()) != null) && (line.length() != 0))
                {
                    Ref<String> referenceToContentType = new Ref<String>(contentType);
                    Ref<String> referenceToContentTransferEncoding = new Ref<String>(contentTransferEncoding);
                    Ref<String> referenceToContentLocation = new Ref<String>(contentLocation);
                    Ref<String> referenceToContentId = new Ref<String>(contentId);
                    boolean outRefCondition0 = getValue(line, "Content-Type: ", /*ref*/ referenceToContentType) ||
                        getValue(line, "Content-Transfer-Encoding: ", /*ref*/ referenceToContentTransferEncoding) ||
                        getValue(line, "Content-Location: ", /*ref*/ referenceToContentLocation) ||
                        getValue(line, "Content-ID: ", /*ref*/ referenceToContentId);
                    contentType = referenceToContentType.get();
                    contentTransferEncoding = referenceToContentTransferEncoding.get();
                    contentLocation = referenceToContentLocation.get();
                    contentId = referenceToContentId.get();
                    if (outRefCondition0)
                    {
                        // Found
                    }

                    msArrayList.add(linesToFlush, line);
                }

                if (!"base64".equals(contentTransferEncoding))
                {
                    // It is not a binary. Just write as usual.
                    for (String line2 : linesToFlush)
                        sw.writeLine(line2);
                    sw.writeLine();
                }
                else
                {
                    // It's a binary file. Let's substitute it with placeholder and collect to image parts.
                    if ((contentType.length() != 0) &&
                        (contentLocation.length() != 0 || contentId.length() != 0))
                    {
                        String contentUrl = (contentLocation.length() != 0)
                            ? contentLocation
                            : contentId.substring((1), (1) + (contentId.length() - 2)); // Remove enclosing angle brackets.
                        byte[] data = readBase64Binary(sr);
                        imageParts.add(new MhtmlImagePart(contentType, contentUrl, data));
                        sw.writeLine("<<{0}: {1}>>", contentType, contentUrl);
                    }
                    else
                    {
                        throw new IllegalStateException("Bad subsidiary header.");
                    }
                }
            }

            // Read next line.
            line = sr.readLine();
        }

        sw.flush();
    }

    private static boolean getValue(String line, String prefix, /*ref*/Ref<String> value)
    {
        boolean isFound = line.startsWith(prefix);
        if (isFound)
            value.set(line.substring(prefix.length()));
        return isFound;
    }

    private static byte[] readBase64Binary(TextReader tr) throws Exception
    {
        StringBuilder sb = new StringBuilder(4096);
        String line = tr.readLine();
        while (StringUtil.hasChars(line))
        {
            msStringBuilder.append(sb, line);
            line = tr.readLine();
        }

        line = sb.toString();
        return Convert.fromBase64String(line);
    }

    /// <summary>
    /// One image part extracted from the MHTML
    /// </summary>
    static class MhtmlImagePart
    {
        public MhtmlImagePart(String type, String location, byte[] data)
        {
            ContentType = type;
            ContentLocation = location;
            ImageData = data;
        }

        public /*final*/ String ContentType;
        public /*final*/ String ContentLocation;
        public /*final*/ byte[] ImageData;
    }
}

