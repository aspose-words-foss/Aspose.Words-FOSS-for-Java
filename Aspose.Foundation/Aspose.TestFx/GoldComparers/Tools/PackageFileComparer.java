// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2016 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import java.util.ArrayList;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;
import com.aspose.ms.System.Collections.msArrayList;


public abstract class PackageFileComparer extends AbstractGoldComparer
{
    public /*override*/ void execute(ComparerParams comparerParams) throws Exception
    {
        execute(
            comparerParams.getTitle(),
            comparerParams.getFileNameSrc(),
            comparerParams.getFileNameOut(),
            comparerParams.getFileNameGold(),
            comparerParams.getFileNameMs());
    }

    public /*override*/ boolean executeForm(ComparerParams comparerParams) throws Exception
    {
        return executeForm(
            comparerParams.getTitle(),
            comparerParams.getFileNameSrc(),
            comparerParams.getFileNameOut(),
            comparerParams.getFileNameGold(),
            comparerParams.getFileNameOriginalGold(),
            comparerParams.getFileNameMs());
    }

    /// <summary>
    /// Compares the generated package with the gold file and an "MS" file.
    /// Brings up the user interface when there is a difference.
    /// </summary>
    /// <param name="title">Title for the dialog box.</param>
    /// <param name="fileNameSrc">Original DOC file name.</param>
    /// <param name="fileNameOut">Name of the generated file.</param>
    /// <param name="fileNameGold">Name of the gold file.</param>
    /// <param name="fileNameMS">Name of the file created by MS Word.</param>
    public void execute(
        String title,
        String fileNameSrc,
        String fileNameOut,
        String fileNameGold,
        String fileNameMS) throws Exception
    {
        execute(title, fileNameSrc, fileNameOut, fileNameGold, fileNameMS, null);
    }

    /// <summary>
    /// Compares the generated package with the gold file and an "MS" file.
    /// Brings up the user interface when there is a difference.
    /// Uses custom <see cref="IPackagePartComparer"/> implementation.
    /// </summary>
    /// <param name="title">Title for the dialog box.</param>
    /// <param name="fileNameSrc">Original DOC file name.</param>
    /// <param name="fileNameOut">Name of the generated file.</param>
    /// <param name="fileNameGold">Name of the gold file.</param>
    /// <param name="fileNameMS">Name of the file created by MS Word.</param>
    /// <param name="partComparer">Custom comparer of package parts.</param>
    public void execute(
        String title,
        String fileNameSrc,
        String fileNameOut,
        String fileNameGold,
        String fileNameMS,
        IPackagePartComparer partComparer) throws Exception
    {
        // Sometime Java has it's own version of gold file. Comparer form needs in former .Net gold too.
        String fileNameOriginalGold = fileNameGold;
        fileNameGold = TestFxUtil.getReplacementGoldIfExists(fileNameGold);

        // RK We do not pass MS file name here because at the moment we only need to compare OUT vs GOLD.
        PackageComparisonInfo info = createPackageComparisonInfo(fileNameSrc, fileNameOut, fileNameGold);
        try
        {
            info.updateStatus(partComparer);
            if (info.getGoldStatus() != ComparisonStatus.OK)
                throw new GoldDifferenceException(fileNameOut, fileNameGold);

            if (TestSettings.getShowCompareAlways())
                throw new Exception("Showing results even if there is no difference");
        }
        catch (Exception e)
        {
            boolean passed = e instanceof GoldDifferenceException && tryPassAutomatically(fileNameOut, fileNameGold);

            // Throw immediately if images are different and silent mode testing is selected.
            // RK We DO pass the MS file name here because in the UI form we want to compare OUT vs GOLD and OUT vs MS.
            if (!passed && !executeForm(title, fileNameSrc, fileNameOut, fileNameGold, fileNameOriginalGold, fileNameMS))
            {
                saveGoldTestInfoIfNeeded(title, fileNameSrc, fileNameOut, fileNameGold, fileNameMS);
                throw e;
            }
        }
    }

    protected abstract PackageComparisonInfo createPackageComparisonInfo(
        String fileNameSrc,
        String fileNameOut,
        String fileNameGold);

    protected abstract /*ComparerFormResult*/int executePackageTestUI(
        String title,
        String fileNameOut,
        String fileNameGold,
        String fileNameOriginalGold,
        String fileNameSrc,
        String fileNameMS) throws Exception;

    protected abstract void saveGoldTestInfoIfNeeded(
        String title,
        String fileNameSrc,
        String fileNameOut,
        String fileNameGold,
        String fileNameMS) throws Exception;

    private boolean executeForm(
        String title,
        String fileNameSrc,
        String fileNameOut,
        String fileNameGold,
        String fileNameOriginalGold,
        String fileNameMS) throws Exception
    {
        if (TestSettings.getDontShowCompareForm())
            return false;

        /*ComparerFormResult*/int userChoice = executePackageTestUI(title, fileNameOut, fileNameGold, fileNameOriginalGold, fileNameSrc, fileNameMS);

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
    /// Returns True if xps files have same page images and the rest of markup binary equal
    /// </summary>
    public static boolean checkXpsMarkupAndPageImagesEqual(PackageComparisonInfo info) throws Exception
    {
        Debug.assertTrue(msString.endsWith(info.getFilenameOut(), ".xps", StringComparison.ORDINAL_IGNORE_CASE));

        // either out or gold are missing
        if (info.getOutStatus() != ComparisonStatus.OK || info.getGoldStatus() == ComparisonStatus.MISSING)
            return false;

        // this method should not be called when packages match
        if (info.getGoldStatus() == ComparisonStatus.OK)
            return true;

        Debug.assertTrue(info.getOutStatus() == ComparisonStatus.OK && info.getGoldStatus() == ComparisonStatus.DIFFERENT);

        // we know that packages are different, thus some pages are different and/or some other parts are different
        // if other parts are different we return False, since its a significant difference
        // if some pages are diffrent we may return True where test settings tell us to pass

        boolean passPixels = TestSettings.getPassWhenImagesPixelsMatch();
        boolean passLabel = TestSettings.getPassWhenImagesDifferenceLabelsMatch();

        // if test settings do not tell us to pass pixel matching or label matching pages we can fail now
        // this is because we know that there is a difference in markup or pages
        // and if its in pages then they are either pixel or label matching, both of which we do not compare
        if (!passPixels && !passLabel)
            return false;

        ArrayList<String> nonMatchingPageNumbers = new ArrayList<String>();
        for (PackagePartComparisonInfo pci : info.getPartComparisonInfos())
        {
            // Part is missing in out compared to gold. A significant difference.
            if (pci.getOutStatus() != ComparisonStatus.OK)
                return false;

            Match m = Regex.match(pci.getName(), "Pages/(\\d+)\\.fpage$", RegexOptions.IGNORE_CASE);
            if (m.getSuccess())
            {
                // Missing gold counterpart
                if (pci.getGoldStatus() == ComparisonStatus.MISSING)
                    return false;

                // NOTE here we assume that 1.fpage is 1st page
                // in reality part name is not directly related to page number
                // it is theoretically possible that we generated images of wrong pages
                if (pci.getGoldStatus() == ComparisonStatus.DIFFERENT)
                    msArrayList.add(nonMatchingPageNumbers, m.getGroups().get(1).getValue());
            }
            else if (pci.getGoldStatus() != ComparisonStatus.OK)
            {
                // Not a page part which is not equal to gold. A significant difference.
                return false;
            }
        }

        Debug.assertTrue(nonMatchingPageNumbers.size() > 0);

        // We can generate images from Xps, not from individual parts inside of it.
        // However we can specify for which pages we want images generated.
        String[] labels = passLabel ? TestSettings.getLabels() : new String[0];
        BasePathUtil.DeletePathOnDispose tmp = BasePathUtil.useTempFolderPath();
        try /*JAVA: was using*/
        {
            String tempPath = tmp.getTmpPath();
            String pages = String.join(",", nonMatchingPageNumbers);
            XpsToPngClient.ConvertSourceParam outFile = new XpsToPngClient.ConvertSourceParam();
            outFile.InputFile = info.getFilenameOut();
            outFile.Pages = pages;
            XpsToPngClient.ConvertSourceParam goldFile = new XpsToPngClient.ConvertSourceParam();
            goldFile.InputFile = info.getFilenameGold();
            goldFile.Pages = pages;

            com.aspose.XpsToPngClient.convert(tempPath, new XpsToPngClient.ConvertSourceParam[] { outFile, goldFile });

            // since we saved non-matching images only then no point in binary compare, we already know they are not equal
            // we pass folder name only, the diff will compute all corresponding differences and place them alongside
            DiffClient.Result[] results = com.aspose.DiffClient.generate(tempPath, tempPath, null, null, passLabel, false);

            return checkResult(passPixels, passLabel, labels, results);
        }
        finally { if (tmp != null) tmp.dispose(); }
    }

    private static boolean checkResult(boolean passPixels, boolean passLabel, String[] labels, DiffClient.Result[] results)
    {
        for (DiffClient.Result d : results)
            if (!msString.isNullOrEmpty(d.Label) &&   // empty label is 'other' difference
                            ((passPixels && msString.equals(d.Label, DiffClient.PIXEL, StringComparison.ORDINAL_IGNORE_CASE)) ||
                             (passLabel && DiffClient.isMatching(labels, d.Label))))
                continue;
            else
                return false;
        return true;
    }
}

