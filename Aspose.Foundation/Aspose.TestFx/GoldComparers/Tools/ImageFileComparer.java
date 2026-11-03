// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2016 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msConsole;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// Compares two picture files and invokes a user interface to view 
/// the difference and to override the gold file with the new output.
/// </summary>
public class ImageFileComparer extends AbstractGoldComparer
{
    public /*override*/ void execute(ComparerParams comparerParams) throws Exception
    {
        execute(comparerParams.getFileNameOut(), comparerParams.getFileNameGold(), comparerParams.getFileNameSrc());
    }

    public /*override*/ boolean executeForm(ComparerParams comparerParams) throws Exception
    {
        return executeForm(
            comparerParams.getFileNameOut(),
            comparerParams.getFileNameGold(),
            comparerParams.getFileNameOriginalGold(),
            comparerParams.getFileNameSrc(),
            comparerParams.getErrorMessage()
            );
    }

    /// <summary>
    /// Compares two image files and brings up a GUI if they do not match.
    /// </summary>
    /// <param name="fileNameOut">The out file.</param>
    /// <param name="fileNameGold">The gold file.</param>
    /// <param name="fileNameSrc">The original file. Can be null or empty string.</param>
    public static void execute(String fileNameOut, String fileNameGold, String fileNameSrc) throws Exception
    {
        // The Java version needs to compare with a file in the TestGoldJava folder if it exists.
        // Let's show original .Net gold too in that case.
        String fileNameOriginalGold = fileNameGold;
        fileNameGold = TestFxUtil.getReplacementGoldIfExists(fileNameGold);

        try
        {
            compareImageFiles(fileNameOut, fileNameGold);
            if (TestSettings.getShowCompareAlways())
                throw new Exception("Showing results even if there is no difference");
        }
        catch (Exception e)
        {
            boolean passed = e instanceof GoldDifferenceException && tryPassAutomatically(fileNameOut, fileNameGold);

            // Throw immediately if images are different and silent mode testing is selected.
            if (!passed && !executeForm(fileNameOut, fileNameGold, fileNameOriginalGold, fileNameSrc, e.getMessage()))
            {
                FailedGoldTestCollector.saveImgGoldTestInfoIfNeeded(fileNameSrc, fileNameOut, fileNameGold);
                throw e;
            }
        }
    }

    private static boolean executeForm(String fileNameOut, String fileNameGold, String fileNameOriginalGold, String fileNameSrc, String errorMessage) throws Exception
    {
        if (TestSettings.getDontShowCompareForm())
            return false;

        /*ComparerFormResult*/int userChoice = AsposeComparer.executeImageTestUI(fileNameOut, fileNameGold, fileNameOriginalGold, fileNameSrc, errorMessage);

        // If the user clicks Accept we override the gold file, otherwise let the difference exception propagate to the caller.
        switch (userChoice)
        {
            case ComparerFormResult.ACCEPT:
                TestFxUtil.acceptNewGold(fileNameOut, fileNameGold);
                return true;
            case ComparerFormResult.SKIP_AND_THROW:
                return false;
            case ComparerFormResult.SKIP_AND_LOG:
                // MF "Skip This" was used. Don't throw, just ignore that images are not equal.
                // RK I think MF did this to allow skipping pages in a multipage test.
                System.out.println(msString.format("Image files are different (throw skipped): \nOut: {0}\nGold: {1}\n", fileNameOut, fileNameGold));
                return true;
            default:
                throw new IllegalStateException("Unexpected result from the comparer form.");
        }
    }

    /// <summary>
    /// A utility method to compares two image files.
    /// </summary>
    public static void compareImageFiles(String fileName1, String fileName2) throws Exception
    {
        if (!File.exists(fileName1) || !File.exists(fileName2))
            throw new GoldDifferenceException(fileName1, fileName2);

        Stream fs1 = File.openRead(fileName1);
        try /*JAVA: was using*/
        {
            Stream fs2 = File.openRead(fileName2);
            try /*JAVA: was using*/
            {
                compareImageFiles(fs1, fs2, fileName1, fileName2);
            }
            finally { if (fs2 != null) fs2.close(); }
        }
        finally { if (fs1 != null) fs1.close(); }
    }

    /// <summary>
    /// A utility method to compares two image files. 
    /// Stream-oriented override with additional argument which helps to identify image name and 
    /// controlled pixel difference count and color tolerance.
    /// </summary>
    /// <remarks>Throw if images are different more than allowed.</remarks>
    /// <param name="fileStream1">First stream being compared.</param>
    /// <param name="fileStream2">Second stream being compared.</param>
    /// <param name="fileName1">String (typically user file name) which helps to identify stream being compared.</param>
    /// <param name="fileName2">String (typically gold file name) which helps to identify stream being compared.</param>
    public static void compareImageFiles(Stream fileStream1, Stream fileStream2, String fileName1, String fileName2) throws Exception
    {
        if (compareFileBytes(fileStream1, fileStream2, fileName1, fileName2))
            return;
        else
            throw new GoldDifferenceException(fileName1, fileName2);
    }

    /// <summary>
    /// Compares two files by lengths and bytes. Returns true if they are the same.
    /// </summary>
    private static boolean compareFileBytes(Stream fileStream1, Stream fileStream2, String fileName1, String fileName2) throws Exception
    {
        if (fileStream1.getLength() != fileStream2.getLength())
            return false;

        byte[] buf1 = StreamUtil.copyStreamToByteArray(fileStream1);
        byte[] buf2 = StreamUtil.copyStreamToByteArray(fileStream2);

        return ArrayUtil.isArrayEqual(buf1, buf2);
    }

    public static class ImageDifference
    {
        public /*enum*/ final class Kind
{
            private Kind(){}
            
            // Image files are binary equal
            public static final int NONE = 0;

            // Image files are not binary equal
            public static final int BINARY_DIFFERENT = 1 << 0;

            // Image pixels are different
            public static final int PIXELS_DIFFERENT = BINARY_DIFFERENT | (1 << 1);

            // Image difference has a label
            public static final int LABELS = BINARY_DIFFERENT | (1 << 2);

            // This is the value to return if difference computation failed completely
            public static final int INVALID = 0xff;

            public static final int length = 5;
}

        public /*Kind*/int getDifference() { return mDifference; };

        private /*Kind*/int mDifference;

        public String getLabel() { return mLabel; };

        private  String mLabel;

        private ImageDifference(/*Kind*/int difference)
        {
            mDifference = difference;
        }

        public ImageDifference(String label)
        {
        	this(Kind.LABELS);
	
            mLabel = label;
        }

        public static /*final*/ ImageDifference NONE = new ImageDifference(Kind.NONE);

        public static /*final*/ ImageDifference BINARY_DIFFERENT = new ImageDifference(Kind.BINARY_DIFFERENT);

        public static /*final*/ ImageDifference PIXELS_DIFFERENT = new ImageDifference(Kind.PIXELS_DIFFERENT);
    }

    /// <summary>
    /// Converts documents to images and compares images. Returns True if images are equal based on the current test settings.
    /// </summary>
    public static boolean compareDocumentsAsImages(String fileOut, String fileGold) throws Exception
    {
        String x1 = msString.toLowerInvariant(Path.getExtension(fileOut));
        Debug.assertTrue(msString.equals(x1, msString.toLowerInvariant(Path.getExtension(fileGold))));

        // These are already images. We came here because image files are different.
        // What we can do is compare pixels and labels.
        if (".png".equals(x1) || ".gif".equals(x1) || ".jpg".equals(x1) || ".jpeg".equals(x1) || ".tiff".equals(x1))
        {
            // We know that files are binary different, let's compare pixels.
            if (TestSettings.getPassWhenImagesPixelsMatch())
            {
                BasePathUtil.DeletePathOnDispose tmp = BasePathUtil.useTempFolderPath();
                try /*JAVA: was using*/
                {
                    String tempFolder = tmp.getTmpPath();
                    // here we have compared images as binary streams already, no sense asking diff to do that
                    DiffClient.Result[] result = DiffClient.generate(tempFolder, fileOut, fileGold, null, TestSettings.getPassWhenImagesDifferenceLabelsMatch(), false);

                    // we compared two files and expect one difference label
                    if (result.length != 1)
                        return false;
                    String label = result[0].Label;

                    // since we know that files are binary different the empty value means significant difference
                    if (!msString.isNullOrEmpty(label))
                    {
                        // matching pixels
                        if (msString.equals(label, DiffClient.PIXEL, StringComparison.ORDINAL_IGNORE_CASE))
                            return true;

                        if (TestSettings.getPassWhenImagesDifferenceLabelsMatch())
                        {
                            String[] labels = TestSettings.getLabels();

                            // matching labels
                            int index = 0;
                            for (String l : labels)
                                if (msString.equals(l, label, StringComparison.ORDINAL_IGNORE_CASE))
                                    break;
                                else
                                    index++;

                            if (labels.length > 0 && index >= 0 && index < labels.length)
                                return true;
                        }
                    }

                    // When learning automatically we can have a label which will be assigned to any significant difference
                    // the test will pass and the difference image will be copied to the dedicated folder
                    if (TestSettings.getPassExplicitLabel())
                    {
                        DiffClient.relocateDiffFile(DiffClient.getDiffImagePath(tempFolder, fileOut, fileGold), TestSettings.getLabels()[0]);
                        return true;
                    }
                }
                finally { if (tmp != null) tmp.dispose(); }
            }

            return false;
        }

        if (".xps".equals(x1))
        {
            PackageComparisonInfo info = new ZipComparisonInfo(null, fileOut, fileGold, null);
            info.updateStatus();
            // The false is returned where either comparisson failed completely or files were not equal
            // The true is returned where all image files were equal based on test settings we have
            // If not true then assume images were different, this is sufficient for now (do not bother with comparisson errors)
            return PackageFileComparer.checkXpsMarkupAndPageImagesEqual(info);
        }

        if (".svg".equals(x1) || ".html".equals(x1) || ".htm".equals(x1) || ".mht".equals(x1))
        {
            // WebToPngClient.Convert didn't work in java, and we desided to manualy check html golds
            return false;
        }

        return false;
    }
}

