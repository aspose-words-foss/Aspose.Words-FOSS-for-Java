// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2016 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.Stream;


/// <summary>
/// Provides a method to compare two text files, launch difference viewers and accept new gold or skip changes.
/// </summary>
public class TextFileComparer extends AbstractGoldComparer
{
    public /*override*/ void execute(ComparerParams comparerParams) throws Exception
    {
        TextFileComparer comparer = new TextFileComparer();
        comparer.setComparerParams(comparerParams);
        comparer.executeCore(comparerParams.getTitle(),
                            comparerParams.getFileNameOut(),
                            comparerParams.getFileNameGold(),
                            comparerParams.getFileNameMs(),
                            comparerParams.getFileNameSrc());
    }

    public /*override*/ boolean executeForm(ComparerParams comparerParams) throws Exception
    {
        return executeForm(
              comparerParams.getTitle(),
              comparerParams.getFileNameOut(),
              comparerParams.getFileNameGold(),
              comparerParams.getFileNameOriginalGold(),
              comparerParams.getFileNameMs(),
              comparerParams.getFileNameSrc()
              );
    }

    /// <summary>
    /// Compares user text file with the gold file. Brings up the user interface when there is a difference.
    /// </summary>
    public static void execute(
        String title,
        String fileNameOut,
        String fileNameGold,
        String fileNameMs,
        String fileNameSource) throws Exception
    {
        TextFileComparer comparer = new TextFileComparer();
        comparer.executeCore(title, fileNameOut, fileNameGold, fileNameMs, fileNameSource);
    }

    protected void executeCore(
        String title,
        String fileNameOut,
        String fileNameGold,
        String fileNameMS,
        String fileNameSource) throws Exception
    {
        // Sometime Java has it's own version of gold file. Comparer form needs in former .Net gold too.
        String fileNameOriginalGold = fileNameGold;
        fileNameGold = TestFxUtil.getReplacementGoldIfExists(fileNameGold);
        try
        {
            doCompareFiles(fileNameOut, fileNameGold);
            if (TestSettings.getShowCompareAlways())
                throw new Exception("Showing results even if there is no difference");
        }
        catch (Exception e)
        {
            boolean passed = e instanceof GoldDifferenceException && tryPassAutomatically(fileNameOut, fileNameGold);

            // Throw immediately if images are different and silent mode testing is selected.
            if (!passed && !executeForm(title, fileNameOut, fileNameGold, fileNameOriginalGold, fileNameMS, fileNameSource))
            {
                FailedGoldTestCollector.saveTxtGoldTestInfoIfNeeded(title, fileNameOut, fileNameGold, fileNameMS, fileNameSource);
                throw e;
            }
        }
    }

    private boolean executeForm(
        String title,
        String fileNameOut,
        String fileNameGold,
        String fileNameOriginalGold,
        String fileNameMS,
        String fileNameSource) throws Exception
    {
        if (TestSettings.getDontShowCompareForm())
            return false;

        boolean topmost = (getComparerParams() != null) ? getComparerParams().getDialogTopMost() : false;
        boolean restorePosition = (getComparerParams() != null) ? getComparerParams().getDialogRestorePosition() : false;
        // The compare did not go well, bring up the user interface.
        /*ComparerFormResult*/int userChoice = AsposeComparer.executeTextTestUI(title, fileNameOut, fileNameGold,
              fileNameOriginalGold, fileNameSource, fileNameMS,
              topmost, restorePosition);

        // If the user clicks Accept we override the gold file, otherwise propagate exception to the caller.
        switch (userChoice)
        {
            case ComparerFormResult.ACCEPT:
                TestFxUtil.acceptNewGold(fileNameOut, fileNameGold);
                return true;
            case ComparerFormResult.SKIP_AND_THROW:
                return false;
            default:
                throw new IllegalStateException("Unexpected result from the comparer form.");
        }
    }

    /// <summary>
    /// This is a template method that derived text comparers need to override.
    /// </summary>
    protected /*virtual*/ void doCompareFiles(String fileName1, String fileName2) throws Exception
    {
        compareTextFiles(fileName1, fileName2);
    }

    /// <summary>
    /// Utility method to compare two text files. 
    /// Throws an exception with detailed error message if they don't match.
    /// </summary>
    static void compareTextFiles(String fileName1, String fileName2) throws Exception
    {
        if (!File.exists(fileName1) || !File.exists(fileName2))
            throw new GoldDifferenceException(fileName1, fileName2);

        Stream stream1 = File.openRead(fileName1);
        try /*JAVA: was using*/
        {
            Stream stream2 = File.openRead(fileName2);
            try /*JAVA: was using*/
            {
                compareTextFiles(stream1, stream2, fileName1, fileName2);
            }
            finally { if (stream2 != null) stream2.close(); }
        }
        finally { if (stream1 != null) stream1.close(); }
    }

    /// <summary>
    /// Utility method to compare two text files in streams. 
    /// Has additional parameters which helps to identify stream being compared.
    /// Throws an exception with detailed error message if they don't match.
    /// </summary>
    /// <param name='userStream'>User stream compared</param>
    /// <param name='goldStream'>Gold stream compared</param>
    /// <param name='userFileName'>String (typically user filename) which helps to identify stream compared</param>
    /// <param name='goldFileName'>String (typically gold filename) which helps to identify stream compared</param>
    protected static void compareTextFiles(Stream userStream, Stream goldStream, String userFileName, String goldFileName) throws Exception
    {
        // On Java we compare XML using XmlDiff so indentation etc will pass on Java.
        // If the XML size is greater than 30 MB, testing will take about 2 hours.
        if (ComparisonInfo.isXml(userFileName) && userStream.getLength() < 30000000L)
        {
            if (XmlComparer.compareXmlStreams(userStream, goldStream))
                return;
            else
                throw new GoldDifferenceException(userFileName, goldFileName);
        }
        // This is the fast compare that we should normally use.
        if (!TestFxUtil.compareStreams(userStream, goldStream))
            throw new GoldDifferenceException(userFileName, goldFileName);
    }

    ComparerParams getComparerParams() { return mComparerParams; }; void setComparerParams(ComparerParams value) { mComparerParams = value; };

    private ComparerParams mComparerParams;
}

