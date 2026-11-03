// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.Debug;
import com.aspose.RandomUtil;
import com.aspose.DateTimeUtil;
import com.aspose.SystemPal;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.MemoryStream;
import java.util.ArrayList;
import com.aspose.ms.System.IO.Path;
import com.aspose.TestZipUtil;
import com.aspose.TestImageUtil;
import com.aspose.TestTxtUtil;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.Directory;
import com.aspose.StringUtil;
import com.aspose.TestUtilPal;
import com.aspose.TestFxUtil;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.IO.StreamReader;
import com.aspose.StreamUtil;
import com.aspose.MemoryStorage;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;
import java.util.Map;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.OpcPackage;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.HashUtil;
import com.aspose.CryptoUtilPal;
import com.aspose.DigestAlgorithm;
import com.aspose.ArrayUtil;
import com.aspose.ms.System.IO.FileAccess;
import com.aspose.ms.System.IO.FileShare;
import com.aspose.ZipReaderPal;
import com.aspose.OpcPackagePart;
import com.aspose.ms.System.IO.SearchOption;
import com.aspose.ThreadPal;
import com.aspose.ms.System.Diagnostics.Stopwatch;
import java.lang.Thread;
import com.aspose.ms.System.Collections.msArrayList;
import java.awt.Color;
import com.aspose.TestEnvironment;


/// <summary>
/// Utility functions used by unit tests.
/// </summary>
public /*static*/ class TestUtil
{
	/* Simulation of static class by using private constructor */
	private TestUtil()
	{}

    /// <summary>
    /// Performs common setup of test environment.
    /// </summary>
    public static void setUpTests()
    {
        Debug.setTestDefaults();
        RandomUtil.setTestMode();
        DateTimeUtil.setTestMode();
        OleUtil.setTestMode();
        // FOSS
        SystemPal.setTestMode(true);
        SectPr.setTestMode(true);
        // FOSS
        OpcDocumentFragmentWriter.setTestMode();

        LanguagePreferences.setTestMode(true);

    }

    /// <summary>
    /// Opens a test file relative to the TestData folder or from an explicit path.
    /// </summary>
    /// <param name="fileName">Can be relative to the TestData folder or an explicit path.</param>
    public static Document open(String fileName) throws Exception
    {
        fileName = buildTestFileName(fileName);

        return new Document(fileName);
    }


    /// <summary>
    /// Saves the document relative to the TestData folder or in explicit path.
    /// NO gold check is performed.
    /// </summary>
    /// <param name="doc">The document to save.</param>
    /// <param name="fileName">Can be relative to the TestData folder or an explicit path.
    /// Should not have the "Out" suffix as it is added automatically.</param>
    /// <returns>The full name of the saved file.</returns>
    public static String save(Document doc, String fileName) throws Exception
    {
        return save(doc, fileName, null, false);
    }

    /// <summary>
    /// Saves the document relative to the TestData folder or in explicit path.
    /// Performs a gold compare test with export and import gold comparison level.
    /// </summary>
    public static String saveCheckGold(Document doc, String fileName) throws Exception
    {
        return save(doc, fileName, null, true, GoldLevel.EXPORT_IMPORT);
    }

    /// <summary>
    /// Saves the document relative to the TestData folder or in explicit path.
    /// Performs a gold compare test with export gold comparison level.
    /// </summary>
    public static String saveCheckGoldExportOnly(Document doc, String fileName) throws Exception
    {
        return save(doc, fileName, null, true, GoldLevel.EXPORT_ONLY);
    }

    public static String saveCheckGold(Document doc, String fileName, SaveOptions saveOptions) throws Exception
    {
        return save(doc, fileName, saveOptions, true);
    }

    /// <summary>
    /// Saves the document relative to the TestData folder or in explicit path.
    /// Optionally performs a gold compare test with specified gold comparison level.
    /// </summary>
    public static String save(Document doc, String fileName, SaveOptions saveOptions, boolean isCheckGold, /*GoldLevel*/int goldLevel) throws Exception
    {
        // This is a "fake" src file name, e.g. it does not have to exist. It is used to build the out and gold file names.
        String srcFileName = buildTestFileName(fileName);
        String outFileName = buildOutFileName(srcFileName, "", SaveFormat.UNKNOWN);
        ensureDirectoryForFileExists(outFileName);

        if (saveOptions == null)
        {
            saveOptions = SaveOptions.createSaveOptions(outFileName);
            saveOptions.setTestMode();
        }
        else if (!saveOptions.isTestMode())
        {
            saveOptions.setTestMode();
        }

        doc.save(outFileName, saveOptions);

        if (isCheckGold)
        {
            String goldFileName = buildGoldFileName(srcFileName, "", SaveFormat.UNKNOWN);
            // In this method the "ms" file is usually the original file.
            String msFileName = srcFileName;
            verifyGold(srcFileName, outFileName, goldFileName, msFileName, saveOptions, goldLevel == GoldLevel.EXPORT_ONLY);
        }

        return outFileName;
    }

    /// <summary>
    /// Saves the document relative to the TestData folder or in explicit path.
    /// Optionally performs a gold compare test.
    /// </summary>
    public static String save(Document doc, String fileName, SaveOptions saveOptions, boolean isCheckGold) throws Exception
    {
        return save(doc, fileName, saveOptions, isCheckGold, GoldLevel.EXPORT_IMPORT);
    }

    /// <summary>
    /// 27/07/2012 DV THIS IS A TEMPORARY METHOD. While working on 6554, I noticed that many tests in TestIndexAndTables
    /// started failing permanently. Accepting did not work. This was because of empty w:wPr tag written to the TOC1 style
    /// in DOCX. I notified DD and he promised to resolve it, but since I have to integrate dvorob_6554 to the trunk,
    /// I'm forced to temporarily use the ExportOnly mode.
    /// </summary>
    /// <param name="doc"></param>
    /// <param name="fileName"></param>
    /// <returns></returns>
    public static Document saveOpenDocxExportOnly(Document doc, String fileName) throws Exception
    {
        // Clone. This makes sure cloning code is exercised.
        doc = doc.deepClone();

        // This is a "fake" src file name, e.g. it does not have to exist. It is used to build the out and gold file names.
        String srcFileName = buildTestFileName(fileName);
        String outFileName = buildOutFileName(srcFileName, "", SaveFormat.UNKNOWN);
        ensureDirectoryForFileExists(outFileName);

        SaveOptions saveOptions = SaveOptions.createSaveOptions(outFileName);
        saveOptions.setTestMode();

        doc.save(outFileName, saveOptions);

        String goldFileName = buildGoldFileName(srcFileName, "", SaveFormat.UNKNOWN);
        // In this method the "ms" file is usually the original file.
        String msFileName = srcFileName;
        verifyGoldDocx(srcFileName, outFileName, goldFileName, msFileName, GoldLevel.EXPORT_ONLY, saveOptions);

        // Open again.
        return new Document(outFileName);
    }

    /// <summary>
    /// Saves and then opens a document relative to the TestData folder or in explicit path.
    /// Performs a gold compare test.
    /// </summary>
    /// <param name="doc">The document to save.</param>
    /// <param name="fileName">Can be relative to the TestData folder or an explicit path.
    /// Should not have the "Out" suffix as it is added automatically.</param>
    public static Document saveOpen(Document doc, String fileName) throws Exception
    {
        return saveOpen(doc, fileName, null);
    }

    /// <summary>
    /// Saves and then opens a document relative to the TestData folder or in explicit path.
    /// Performs a gold compare test.
    /// </summary>
    public static Document saveOpen(Document doc, String fileName, SaveOptions saveOptions) throws Exception
    {
        return saveOpen(doc, fileName, saveOptions, true);
    }

    /// <summary>
    /// Saves and then opens a document relative to the TestData folder or in explicit path.
    /// Performs a gold compare test if isCheckGold set as true.
    /// </summary>
    public static Document saveOpen(Document doc, String fileName, SaveOptions saveOptions, boolean isCheckGold) throws Exception
    {
        // Clone. This makes sure cloning code is exercised.
        doc = doc.deepClone();

        // Save and verify gold.
        String outFileName = save(doc, fileName, saveOptions, isCheckGold);

        return new Document(outFileName);
    }

    /// <summary>
    /// Saves and then opens a document in memory. DOES NOT perform a gold compare test.
    /// </summary>
    public static Document saveOpen(Document document, /*SaveFormat*/int sf) throws Exception
    {
        Stream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            document.save(stream, sf);
            return new Document(stream);
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Saves a document to a memory stream and returns it.
    /// </summary>
    public static MemoryStream save(Document document, /*SaveFormat*/int sf) throws Exception
    {
        MemoryStream stream = new MemoryStream();
        document.save(stream, sf);
        stream.setPosition(0);
        return stream;
    }

    /// <summary>
    /// Opens, saves and then opens the file again. File is relative to the TestData folder or in explicit path.
    /// This allows to check import and "limited" export so that we can at least read the files we produce.
    /// </summary>
    /// <param name="fileName">Can be relative to the TestData folder or an explicit path.</param>
    public static Document openSaveOpen(String fileName) throws Exception
    {
        Document doc = open(fileName);
        return saveOpen(doc, fileName);
    }

    /// <summary>
    /// Works exactly like Open but also clears then updates field results throughout the document.
    /// Used by the field evaluation engine tests.
    /// </summary>
    /// <param name="fileName">Can be relative to the TestData folder or an explicit path.</param>
    public static Document openUpdateFields(String fileName) throws Exception
    {
        return openUpdateFields(fileName, null);
    }

    /// <summary>
    /// Works exactly like Open but also clears then updates field results throughout the document.
    /// Used by the field evaluation engine tests.
    /// </summary>
    /// <param name="fileName">Can be relative to the TestData folder or an explicit path.</param>
    /// <param name="lo"></param>
    public static Document openUpdateFields(String fileName, LoadOptions lo) throws Exception
    {
        Document doc = open(fileName, lo);
        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);

        for (Field field : fields)
        {
            if (field.hasSeparator())
                field.setResult("34twrsdgrw546565twetwet");   // Some garbage text that should never appear during update.
        }

        doc.updateFields();

        return doc;
    }

    /// <summary>
    /// Use for unified tests.
    ///
    /// Opens a test file relative to TestData folder or from an explicit path.
    /// </summary>
    /// <param name="fileNameWithoutExtension">Can be relative to TestData folder or
    /// an explicit path. Should not have "ms" or "Out" suffixes as they are added automatically.</param>
    /// <param name="lf">The format of the document. Used to figure extension of the file.</param>
    public static Document open(String fileNameWithoutExtension, /*LoadFormat*/int lf) throws Exception
    {
        return open(fileNameWithoutExtension, lf, null);
    }

    /// <summary>
    /// Opens a test file relative to TestData folder or from an explicit path.
    /// </summary>
    /// <remarks>
    /// Method overload allows to open specified file name with custom load options.
    /// "LoadFormat" is used to detect extension of the file, because in general case
    /// load options can have "auto" load format.
    /// </remarks>
    public static Document open(String fileNameWithoutExtension, /*LoadFormat*/int lf, LoadOptions lo) throws Exception
    {
        String srcFileName = buildSrcFileName(fileNameWithoutExtension, lf);
        if (lo == null)
            lo = new LoadOptions(); { lo.setReadDocTheme(false); }
        return new Document(srcFileName, lo);
    }

    /// <summary>
    /// Opens a test file relative to TestData folder or from an explicit path.
    /// </summary>
    /// <param name="fileName">Can be relative to TestData folder or an explicit path.
    /// Should not have "ms" or "Out" suffixes as they are added automatically.</param>
    /// <param name="lo">Options for document loading.</param>
    public static Document open(String fileName, LoadOptions lo) throws Exception
    {
        String srcFileName = buildTestFileName(fileName);
        return new Document(srcFileName, lo);
    }

    /// <summary>
    /// RK This is the old "classical" method used by the unified tests.
    /// Consider replacing all usages with the other overload that accepts the UnifiedScenario parameter.
    /// </summary>
    public static Document saveOpen(Document doc, String fileNameWithoutExtension, /*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        return saveOpen(doc, fileNameWithoutExtension, getUnifiedScenario(lf, sf));
    }

    /// <summary>
    /// Use for unified tests.
    ///
    /// Performs clone/save/open followed by compare against gold when it is applicable.
    /// </summary>
    /// <param name="doc">Document to operate with</param>
    /// <param name="fileNameWithoutExtension">Can be relative to TestData folder or
    /// an explicit path. Should not have "ms" or "Out" suffixes as they are added automatically.</param>
    /// <param name="lf">This is used to figure out the original file name and also decide
    /// whether to execute gold compare tests or not.</param>
    /// <param name="sf">The format in which to save the document.</param>
    public static Document saveOpen(Document doc, String fileNameWithoutExtension, /*UnifiedScenario*/int scenario) throws Exception
    {
        return saveOpen(doc, fileNameWithoutExtension, scenario, (LoadOptions)null);
    }

    /// <summary>
    /// Performs clone/save/open followed by compare against gold when it is applicable.
    /// </summary>
    /// <remarks>
    /// This method overload allows to specified custom load options for document opening.
    /// </remarks>
    public static Document saveOpen(Document doc, String fileNameWithoutExtension,
        /*UnifiedScenario*/int scenario, LoadOptions loadOptions) throws Exception
    {
        // Most of the time we want things like pretty format and no generator name.
        SaveOptions saveOptions = SaveOptions.createSaveOptions(getSaveFormat(scenario));
        saveOptions.setTestMode();

        if (getLoadFormat(scenario) == (/*LoadFormat*/int)LoadFormatTest.TEST_DOCX_DML)
        {
            // For unified tests that load files with DrawingML pictures we want to convert them to VML.
            // Firstly, this tests our conversion of DrawingML to VML, secondly it is more logical for unified
            // tests because they are a common base and the "common" shapes in Word documents are VML.
            OoxmlSaveOptions ooxmlSaveOptions = (OoxmlSaveOptions)saveOptions;
            ooxmlSaveOptions.setConvertDmlPictureToVml(true);
        }

        return saveOpen(doc, fileNameWithoutExtension, scenario, saveOptions, loadOptions);
    }

    /// <summary>
    /// Used for unified tests.
    ///
    /// See comments above.
    /// This overload accepts custom save options object that allows to control how the document is saved.
    /// </summary>
    public static Document saveOpen(Document doc, String fileNameWithoutExtension,
        /*UnifiedScenario*/int scenario, SaveOptions saveOptions) throws Exception
    {
        return saveOpen(doc, fileNameWithoutExtension, scenario, saveOptions, null);
    }

    /// <summary>
    /// This overload accepts custom save options and load options objects that allows to control how the document is saved.
    /// </summary>
    public static Document saveOpen(Document doc, String fileNameWithoutExtension,
        /*UnifiedScenario*/int scenario, SaveOptions saveOptions, LoadOptions loadOptions) throws Exception
    {
        if (saveOptions == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "saveOptions");

        // Clone. This makes sure cloning code is exercised.
        doc = doc.deepClone();

        /*LoadFormat*/int loadFormat = getLoadFormat(scenario);

        // We need the source file name to build the out and gold file names.
        // Cannot get the source file name from the document because it might have been loaded from a stream.
        String srcFileName = buildSrcFileName(fileNameWithoutExtension, loadFormat);

        // This is related to the growth of unified tests from "doc to docx, rtf, wml" to include other combinations
        // such as "docx to docx, docx to rtf, rtf to docx" and others. We need to distinguish out and gold files
        // for different source formats. In the original "doc to xxx" tests the gold files had simple names.
        // But for the new conversions we add the source format info into the gold file name.
        // For example "TestSizeRelative.docx" processed by a Docx2Rtf unified test will look like "TestSizeRelative.docx Out.rtf"
        boolean needsLoadFormatSuffix = (loadFormat != LoadFormat.DOC) && (loadFormat != (/*LoadFormat*/int)LoadFormatTest.TEST_DOCX_DML);
        String nonDocSuffix = (needsLoadFormatSuffix) ? FileFormatUtil.loadFormatToExtension(loadFormat) : "";

        // Save.
        saveOptions.setSaveFormat(adjustSaveFormatForMacrosInDocx(doc.hasMacros(), saveOptions.getSaveFormat()));
        String outFileName = buildOutFileName(srcFileName, nonDocSuffix, saveOptions.getSaveFormat());
        ensureDirectoryForFileExists(outFileName);
        doc.save(outFileName, saveOptions);

        // Open again.
        // As we open just saved format, then it becomes the load format on opening.
        if (loadOptions == null)
            loadOptions = new LoadOptions(FileFormatUtil.saveFormatToLoadFormat(saveOptions.getSaveFormat()), null, null);
        doc = new Document(outFileName, loadOptions);

        // Gold compare.
        if ((scenario & UnifiedScenario.NO_GOLD) == 0)
        {
            String goldFileName = buildGoldFileName(srcFileName, nonDocSuffix, saveOptions.getSaveFormat());

            // We want to provide an ability for the user to compare against the ms file too.
            // The ms file is located in the same dir as the src file and we are interested in the ms file that matches our save format.
            String msFileName = buildSrcFileName(fileNameWithoutExtension, FileFormatUtil.saveFormatToLoadFormat(saveOptions.getSaveFormat()));

            verifyGold(srcFileName, outFileName, goldFileName, msFileName, saveOptions, ((scenario & UnifiedScenario.EXPORT_ONLY) != 0));
        }

        return doc;
    }

    /// <summary>
    /// RK This is the old "classical" method used by the unified tests.
    /// Consider replacing all usages with the other overload that accepts the UnifiedScenario parameter.
    /// </summary>
    public static Document openSaveOpen(String fileNameWithoutExtension, /*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        return openSaveOpen(fileNameWithoutExtension, getUnifiedScenario(lf, sf));
    }

    /// <summary>
    /// Use for unified tests.
    ///
    /// Performs open/clone/save/open followed by compare against gold when it is applicable.
    /// </summary>
    public static Document openSaveOpen(String fileNameWithoutExtension, /*UnifiedScenario*/int scenario) throws Exception
    {
        return openSaveOpen(fileNameWithoutExtension, scenario, null);
    }

    /// <summary>
    /// Performs open/clone/save/open followed by compare against gold when it is applicable.
    /// </summary>
    /// <remarks>
    /// This overload allows to specified custom load options.
    /// </remarks>
    public static Document openSaveOpen(String fileNameWithoutExtension, /*UnifiedScenario*/int scenario, LoadOptions loadOptions) throws Exception
    {
        Document doc = open(fileNameWithoutExtension, getLoadFormat(scenario), loadOptions);
        return saveOpen(doc, fileNameWithoutExtension, scenario, loadOptions);
    }

    public static void verifyGold(String srcFileName, String outFileName, String goldFileName, String msFileName, SaveOptions saveOptions, boolean exportOnly) throws Exception
    {
        /*SaveFormat*/int sf = FileFormatUtil.extensionToSaveFormat(Path.getExtension(outFileName));
        switch (sf)
        {
            case SaveFormat.DOC:
            case SaveFormat.DOT:
                // We do not do a gold compare for DOC files because they are binary.
                return;
            case SaveFormat.RTF:
            case SaveFormat.MARKDOWN:
            case SaveFormat.TEXT:
            case SaveFormat.DOCLING:
                // RK RTF does not yet support ExportImport gold level, needs more work.
                verifyGoldText(srcFileName, outFileName, goldFileName, msFileName, GoldLevel.EXPORT_ONLY, saveOptions);
                return;
            case SaveFormat.WORD_ML:
            case SaveFormat.FLAT_OPC:
                verifyGoldText(srcFileName, outFileName, goldFileName, msFileName, exportOnly ? GoldLevel.EXPORT_ONLY : GoldLevel.EXPORT_IMPORT, saveOptions);
                return;
            case SaveFormat.DOCX:
            case SaveFormat.DOCM:
            case SaveFormat.DOTX:
            case SaveFormat.DOTM:
                verifyGoldDocx(srcFileName, outFileName, goldFileName, msFileName, exportOnly ? GoldLevel.EXPORT_ONLY : GoldLevel.EXPORT_IMPORT, saveOptions);
                return;
            case SaveFormat.ODT:
            case SaveFormat.OTT:
                // RK This is not a "mainstream" method. It is here because I save to ODT from "my tests" occasionally.
                // The "mainstream" code is in TestGoldOdtBase so I might refactor some day.
                TestZipUtil.verifyFile("EXPORT ODT", srcFileName, outFileName, goldFileName, null);
                return;
            case SaveFormat.XPS:
            case SaveFormat.OPEN_XPS:
                TestZipUtil.verifyFile("EXPORT XPS", srcFileName, outFileName, goldFileName, null);
                return;
            case SaveFormat.HTML:
            case SaveFormat.MHTML:
            case SaveFormat.PDF:
            case SaveFormat.XAML_FLOW:
            case SaveFormat.SVG:
                // This is not the "main steam" method for gold verification of HTML or PDF, but it can be called from some tests to make them "simpler".
                verifyGoldText(srcFileName, outFileName, goldFileName, msFileName, GoldLevel.EXPORT_ONLY, saveOptions);
                return;
            case SaveFormat.MOBI:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.AZW_3:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.EPUB:
                TestZipUtil.verifyFile("EXPORT ePub", srcFileName, outFileName, goldFileName, null);
                break;
            case SaveFormat.XAML_FLOW_PACK:
                TestZipUtil.verifyFile("EXPORT Xaml Package", srcFileName, outFileName, goldFileName, null);
                return;
            case SaveFormat.PNG:
                TestImageUtil.verifyFile(outFileName, goldFileName, srcFileName);
                return;

            default:
                throw new IllegalStateException("Unexpected file format.");
        }
    }

    public static void verifyGoldText(String srcFileName, String outFileName, String goldFileName, String msFileName,
        /*GoldLevel*/int goldLevel, SaveOptions saveOptions) throws Exception
    {
        // This part is the "export" part of the test.
        TestTxtUtil.verifyFile(
            "EXPORT " + Path.getExtension(outFileName),
            outFileName,
            goldFileName,
            msFileName,
            srcFileName);

        if (goldLevel == GoldLevel.EXPORT_IMPORT)
        {
            // This part is the "import" part of the test.
            // It reads a file that we exported above, saves it again and checks gold.
            Document doc = new Document(outFileName);
            doc.save(outFileName, saveOptions);

            TestTxtUtil.verifyFile(
                "EXPORT IMPORT EXPORT " + Path.getExtension(outFileName),
                outFileName,
                goldFileName,
                msFileName,
                srcFileName
                );
        }
    }

    private static void verifyGoldDocx(String srcFileName, String outFileName, String goldFileName, String msFileName, /*GoldLevel*/int goldLevel, SaveOptions saveOptions) throws Exception
    {
        // This part is the "export" test.
        TestZipUtil.verifyFile(
            "EXPORT " + Path.getExtension(outFileName),
            srcFileName,
            outFileName,
            goldFileName,
            msFileName);

        if (goldLevel == GoldLevel.EXPORT_IMPORT)
        {
            // This part is the "import" test.
            // It reads a file that we exported above, saves it again and checks gold.
            Document doc = new Document(outFileName);
            doc.save(outFileName, saveOptions);

            TestZipUtil.verifyFile(
                "IMPORT AFTER EXPORT " + Path.getExtension(outFileName),
                srcFileName,
                outFileName,
                goldFileName,
                msFileName);
        }
    }

    /// <summary>
    /// If the specified file name is absolute, returns it as is.
    /// If the specified file name is relative, returns TestData\fileName.
    /// </summary>
    public static String buildTestFileName(String fileName)
    {
        if (Path.isPathRooted(fileName))
            return fileName;
        else
            return getInTestDataPath(fileName);
    }

    /// <summary>
    /// Appends an ms suffix (if needed) and file extension to the specified file name.
    /// </summary>
    private static String buildSrcFileName(String fileNameWithoutExtension, /*LoadFormat*/int loadFormat)
    {
        String result = fileNameWithoutExtension;

        // Add suffix.
        switch (loadFormat)
        {
            case LoadFormat.RTF:
            case LoadFormat.WORD_ML:
            case LoadFormat.DOCX:
            case LoadFormat.DOCM:
            case LoadFormat.DOTX:
            case LoadFormat.DOTM:
                result = addFileNameSuffix(result, " ms");
                break;
            case (/*LoadFormat*/int)LoadFormatTest.TEST_DOCX_DML:
                result = addFileNameSuffix(result, " dml ms");
                break;
            default:
                // Do nothing.
                break;
        }

        // Add extension.
        result = msString.plusEqOperator(result, FileFormatUtil.loadFormatToExtension(loadFormat));

        // Add path.
        result = buildTestFileName(result);

        // RK This is somewhat controversial. Historically we had only .doc files without the "ms" suffix and generated all other
        // .docx, .rtf, .wml from the .doc files and all generated files had the "ms" suffix. But this is gradually changing
        // because we are having more .docx, .rtf files that were not generated from .doc (because a particular feature is only in Word 2007 .docx).
        // We need to return the correct file name and for such files it will be without the "ms" suffix.
        if (!File.exists(result))
            result = removeFileNameSuffix(result, " ms");

        return result;
    }

    /// <summary>
    /// Creates a directory for the specified file if the directory does not exist.
    /// </summary>
    public static void ensureDirectoryForFileExists(String fileName)
    {
        com.aspose.TestFxUtil.ensureDirectoryForFileExists(fileName);
    }

    /// <summary>
    /// Deletes directory an d all files and folders in the directory.
    /// </summary>
    public static void deleteTestDirectory(String testDirectoryPath) throws Exception
    {
        if (Directory.exists(testDirectoryPath))
            Directory.delete(testDirectoryPath, true);
    }

    /// <summary>
    /// If the file is in TestData, the directory is replaced with TestOut.
    /// Removes all file name suffixes and adds the user and Out suffixes.
    /// Optionally replaces the file extension.
    /// </summary>
    public static String buildOutFileName(String srcFileName, String userSuffix, /*SaveFormat*/int sf)
    {
        String result = buildDerivedFileName(srcFileName, G_TEST_OUT_PATH, StringUtil.nullToEmptyString(userSuffix) + " Out", sf);
        return TestUtilPal.correctOutFileNameIfNeeded(result);
    }

    /// <summary>
    /// If the file is in TestData, the directory is replaced with TestGold.
    /// Removes all file name suffixes and adds the user and Gold suffixes.
    /// Optionally replaces the file extension.
    /// </summary>
    public static String buildGoldFileName(String srcFileName, String userSuffix, /*SaveFormat*/int sf)
    {
        String result = buildDerivedFileName(srcFileName, G_TEST_GOLD_PATH, StringUtil.nullToEmptyString(userSuffix) + " Gold", sf);
        return TestUtilPal.correctGoldFileNameIfNeeded(result);
    }

    private static String buildDerivedFileName(String srcFileName, String testDataDirReplacement, String fileNameSuffix, /*SaveFormat*/int sf)
    {
        String result = replaceDirectory(srcFileName, G_TEST_DATA_PATH, testDataDirReplacement);
        if (msString.equals(result, srcFileName))
            result = replaceDirectory(srcFileName, G_TEST_OUT_PATH, testDataDirReplacement);
        if (msString.equals(result, srcFileName))
            result = replaceDirectory(srcFileName, G_TEST_GOLD_PATH, testDataDirReplacement);

        result = removeAllFileNameSuffixes(result);

        result = addFileNameSuffix(result, fileNameSuffix);

        if (sf != SaveFormat.UNKNOWN)
            result = Path.changeExtension(result, FileFormatUtil.saveFormatToExtension(sf));

        return result;
    }

    /// <summary>
    /// Replaces oldDir with newDir if the oldDir is at the beginning of the file name.
    /// </summary>
    private static String replaceDirectory(String fileName, String oldDir, String newDir)
    {
        oldDir = TestFxUtil.correctPath(oldDir);
        newDir = TestFxUtil.correctPath(newDir);

        if (msString.indexOf(fileName, oldDir, StringComparison.ORDINAL_IGNORE_CASE) == 0)
            fileName = newDir + fileName.substring(oldDir.length());
        return fileName;
    }

    /// <summary>
    /// If we are saving as DOCX and document has macros we have to choose DOCM instead.
    /// </summary>
    private static /*SaveFormat*/int adjustSaveFormatForMacrosInDocx(boolean hasMacros, /*SaveFormat*/int sf)
    {
        switch (sf)
        {
            case SaveFormat.DOCX:
                if (hasMacros)
                    sf = SaveFormat.DOCM;
                break;
            default:
                // Do nothing.
                break;
        }
        return sf;
    }

    private static String removeAllFileNameSuffixes(String fileName)
    {
        fileName = removeFileNameSuffix(fileName, " ms");
        fileName = removeFileNameSuffix(fileName, " Out");
        fileName = removeFileNameSuffix(fileName, " Gold");
        return fileName;
    }

    public static String removeFileNameSuffix(String fileName, String suffix)
    {
        String name = Path.getFileNameWithoutExtension(fileName);
        if (name.endsWith(suffix))
            name = name.substring((0), (0) + (name.length() - suffix.length()));

        return Path.combine(
            Path.getDirectoryName(fileName),
            name + Path.getExtension(fileName));
    }

    public static String addFileNameSuffix(String fileName, String suffix)
    {
        return Path.combine(
            Path.getDirectoryName(fileName),
            Path.getFileNameWithoutExtension(fileName) + suffix + Path.getExtension(fileName));
    }

    /// <summary>
    /// Returns RootPath + RelativePath.
    /// </summary>
    public static String getInRootPath(String relativePath)
    {
        return TestFxUtil.correctPath(Path.combine(G_ROOT_PATH, relativePath));
    }

    /// <summary>
    /// Returns TestDataPath + RelativePath.
    /// </summary>
    public static String getInTestDataPath(String relativePath)
    {
        return TestFxUtil.correctPath(Path.combine(G_TEST_DATA_PATH, relativePath));
    }

    /// <summary>
    /// Returns TestOutPath + RelativePath.
    /// </summary>
    public static String getInTestOutPath(String relativePath)
    {
        return TestFxUtil.correctPath(Path.combine(G_TEST_OUT_PATH, relativePath));
    }

    /// <summary>
    /// Returns TestGoldPath + RelativePath.
    /// </summary>
    public static String getInTestGoldPath(String relativePath)
    {
        return TestFxUtil.correctPath(Path.combine(G_TEST_GOLD_PATH, relativePath));
    }

    /// <summary>
    /// Returns ModelPath + RelativePath.
    /// </summary>
    public static String getInModelPath(String relativePath)
    {
        return TestFxUtil.correctPath(Path.combine(G_MODEL_PATH, relativePath));
    }

    public static /*UnifiedScenario*/int buildScenario(/*LoadFormat*/int lf, /*SaveFormat*/int sf, boolean isNoGold)
    {
        return (/*UnifiedScenario*/int)((int)lf | ((int)sf << 8) | (isNoGold ? (int)UnifiedScenario.NO_GOLD : 0));
    }

    public static /*UnifiedScenario*/int getUnifiedScenario(/*LoadFormat*/int lf, /*SaveFormat*/int sf)
    {
        switch (lf)
        {
            case LoadFormat.DOC:
            {
                return buildScenario(lf, sf, false);
            }
            case (/*LoadFormat*/int)LoadFormatTest.TEST_DOCX_DML:
            {
                return buildScenario(lf, sf, false);
            }
            case LoadFormat.DOCX:
            case LoadFormat.DOTX:
            case LoadFormat.FLAT_OPC:
            case LoadFormat.FLAT_OPC_TEMPLATE:
            {
                switch (sf)
                {
                    case SaveFormat.DOCX:
                        return UnifiedScenario.DOCX_2_DOCX_NO_GOLD;
                    default:
                        return buildScenario(lf, sf, false);
                }
            }
            case LoadFormat.DOCM:
            case LoadFormat.DOTM:
            case LoadFormat.FLAT_OPC_MACRO_ENABLED:
            case LoadFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED:
            {
                switch (sf)
                {
                    case SaveFormat.DOCM:
                        return UnifiedScenario.DOCM_2_DOCM_NO_GOLD;
                    default:
                        return buildScenario(lf, sf, false);
                }
            }
            case LoadFormat.RTF:
            {
                switch (sf)
                {
                    case SaveFormat.RTF:
                        return UnifiedScenario.RTF_2_RTF_NO_GOLD;
                    default:
                        return buildScenario(lf, sf, false);
                }
            }
            case LoadFormat.WORD_ML:
            {
                switch (sf)
                {
                    case SaveFormat.WORD_ML:
                        return UnifiedScenario.WML_2_WML_NO_GOLD;
                    default:
                        return buildScenario(lf, sf, false);
                }
            }
            case LoadFormat.MS_WORKS:
            case LoadFormat.MARKDOWN:
            {
                return buildScenario(lf, sf, false);
            }
            default:
                throw new IllegalStateException("Unknown file format.");
        }
    }
    public static /*LoadFormat*/int getLoadFormat(/*UnifiedScenario*/int scenario)
    {
        return (/*LoadFormat*/int)(scenario & UnifiedScenario.LOAD_FORMAT_MASK);
    }
    public static /*SaveFormat*/int getSaveFormat(/*UnifiedScenario*/int scenario)
    {
        return (/*SaveFormat*/int)((int)(scenario & UnifiedScenario.SAVE_FORMAT_MASK) >> 8);
    }
    public static int getFileSize(String fileName) throws Exception
    {
        return TestFxUtil.getFileSize(fileName);
    }

    /// <summary>
    /// Compares two streams and returns the result of comparison.
    /// </summary>
    /// <param name="stream1">First stream to compare.</param>
    /// <param name="stream2">Second stream to compare.</param>
    /// <returns>True, if streams have the same content.</returns>
    public static boolean compareStreams(Stream stream1, Stream stream2) throws Exception
    {
        return TestFxUtil.compareStreams(stream1, stream2);
    }

    public static String readAllText(String fileName) throws Exception
    {
        StreamReader sr = File.openText(buildTestFileName(fileName));
        try /*JAVA: was using*/
    	{
            return sr.readToEnd();
    	}
        finally { if (sr != null) sr.close(); }
    }

    public static byte[] readAllBytes(String fileName) throws Exception
    {
        return StreamUtil.copyFileToByteArray(buildTestFileName(fileName));
    }

    /// <summary>
    /// Extracts content of MemoryStorage and writes it into specified directory.
    /// </summary>
    public static void extractStorage(MemoryStorage storage, String path) throws Exception
    {
        Directory.createDirectory(path);
        FileStream guid = File.open(Path.combine(path, '{' + storage.getClsid().toString()) + "}.clsid", FileMode.CREATE);
        guid.close();
        for (Map.Entry<String, Object> entry : (Iterable<Map.Entry<String, Object>>) storage)
        {
            String fileName = getPrintableName(entry.getKey());
            if (entry.getValue() instanceof MemoryStorage)
            {
                extractStorage((MemoryStorage)entry.getValue(), Path.combine(path, fileName));
            }
            else
            {
                MemoryStream memoryStream = (MemoryStream)entry.getValue();
                long savedPos = memoryStream.getPosition();
                memoryStream.setPosition(0);
                FileStream fileStream = File.open(Path.combine(path, fileName) + ".bin", FileMode.CREATE);
                StreamUtil.copyStream(memoryStream, fileStream);
                fileStream.close();
                memoryStream.setPosition(savedPos);
            }
        }
    }

    /// <summary>
    /// Looks up for unprintable chars in name and converts them into HEX representation.
    /// </summary>
    public static String getPrintableName(String name)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++)
        {
            if (Character.isLetterOrDigit(name.charAt(i)) || (name.charAt(i) == '_'))
                sb.append(name.charAt(i));
            else
                msStringBuilder.appendFormat(sb, "0x{0:x4}", (int)name.charAt(i));
        }

        return sb.toString();
    }

    /// <summary>
    /// Saves part of Stream to file. Preserves Stream position.
    /// </summary>
    public static void saveStreamBytes(Stream stream, int pos, int len, String filename) throws Exception
    {
        long savedPos = stream.getPosition();

        stream.setPosition(pos);
        byte[] buffer = new byte[len];
        stream.read(buffer, 0, len);

        FileStream f = new FileStream(filename, FileMode.CREATE);
        f.write(buffer, 0, len);
        f.close();

        stream.setPosition(savedPos);
    }

    /// <summary>
    /// Inserts specified document after node in source document. Commonly used test routine.
    /// </summary>
    public static void insertDocument(Node insertAfterNode, Document srcDoc)
    {
        // We need to make sure that the specified node is either paragraph or table.
        if (!((insertAfterNode.getNodeType() == NodeType.PARAGRAPH) || (insertAfterNode.getNodeType() == NodeType.TABLE)))
            throw new IllegalArgumentException("The destination node should be either paragraph or table.");

        // We will be inserting into the parent of the destination paragraph.
        CompositeNode dstStory = insertAfterNode.getParentNode();

        // This object will be translating styles and lists during the import.
        NodeImporter importer = new NodeImporter(srcDoc, insertAfterNode.getDocument(),
                                                 ImportFormatMode.KEEP_SOURCE_FORMATTING);

        // Loop through all sections in the source document.
        for (Section srcSection : (Iterable<Section>) srcDoc.getSections())
        {

            // Loop through all block level nodes (paragraphs and tables) in the body of the section.
            for (Node srcNode : (Iterable<Node>) srcSection.getBody())
            {
                Node newNode;
                if (srcNode.getClass() == Paragraph.class)
                {
                    // Do not insert node if it is a last empty paragraph in the section.
                    Paragraph para = (Paragraph)srcNode;
                    if (para.isEndOfSection() && !para.hasChildNodes())
                    {
                        break;
                    }

                    // This creates a clone of the node, suitable for insertion into the destination document.
                    newNode = importer.importNode(srcNode, true);
                }
                else
                {
                    // This creates a clone of the node, suitable for insertion into the destination document.
                    newNode = importer.importNode(srcNode, true);
                }

                if (newNode != null)
                {
                    // Insert new node after the reference node.
                    dstStory.insertAfter(newNode, insertAfterNode);
                    insertAfterNode = newNode;
                }
            }
        }
    }

    /// <summary>
    /// Checks that certain warning exists in collection.
    /// </summary>
    public static boolean containsWarning(WarningInfoCollection warnings, /*WarningType*/int type, /*WarningSource*/int source, String description)
    {
        for (WarningInfo warning : warnings)
        {
            if ((warning.getWarningType() == type) && (warning.getSource() == source) && (msString.equals(warning.getDescription(), description)))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Checks that certain warning exists in collection.
    /// </summary>
    public static boolean containsWarningBySource(WarningInfoCollection warnings, /*WarningSource*/int source, String description)
    {
        for (WarningInfo warning : warnings)
        {
            if ((warning.getSource() == source) && (msString.equals(warning.getDescription(), description)))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Checks that certain warning exists in collection.
    /// </summary>
    public static boolean containsWarningByType(WarningInfoCollection warnings, /*WarningType*/int type, String description)
    {
        for (WarningInfo warning : warnings)
        {
            if ((warning.getWarningType() == type) && (msString.equals(warning.getDescription(), description)))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Loads a file, and checks if this process hangs.
    /// </summary>
    /// <returns>Document object if loading process does not hang, null otherwise.</returns>
    public static Document loadWithTimeout(String fileName, int timeout) throws Exception
    {
        return HangingDetector.loadWithTimeoutCore(fileName, timeout);
    }

    /// <summary>
    /// Checks that given string is written to OPC package. Used to test DOCX export.
    /// </summary>
    public static boolean isWrittenToOpcPackage(String file, String partName, String text) throws Exception
    {
        return isWrittenToOpcPackageCore(new OpcPackage(file), partName, text);
    }

    /// <summary>
    /// Checks that given string is written to OPC package. Used to test DOCX export.
    /// </summary>
    public static boolean isWrittenToOpcPackage(Stream stream, String partName, String text) throws Exception
    {
        return isWrittenToOpcPackageCore(new OpcPackage(stream), partName, text);
    }

    /// <summary>
    /// Opens specified document and checks warning <see cref="WarningStrings.ViolationI4I"/> is issued.
    /// </summary>
    /// <remarks>WORDSNET-10749 Violation of i4i's patent #5,787,449. Custom xml will no longer be supported.</remarks>
    public static Document openAndVerifyCustomXmlWarning(String fileName) throws Exception
    {
        LoadOptions lo = new LoadOptions();
        WarningInfoCollection warnings = new WarningInfoCollection();
        lo.setWarningCallback(warnings);

        Document doc = open(fileName, lo);

        // Check warning is issued.
        msAssert.isTrue(containsWarningByType(warnings, WarningType.DATA_LOSS, WarningStrings.VIOLATION_I_4_I));

        return doc;
    }

    /// <summary>
    /// Opens specified document and checks warning <see cref="WarningStrings.ViolationI4I"/> is issued.
    /// </summary>
    /// <remarks>WORDSNET-10749 Violation of i4i's patent #5,787,449. Custom xml will no longer be supported.</remarks>
    public static Document openAndVerifyCustomXmlWarning(String fileNameWithoutExtension, /*LoadFormat*/int lf) throws Exception
    {
        String fileName = buildSrcFileName(fileNameWithoutExtension, lf);
        return openAndVerifyCustomXmlWarning(fileName);
    }

    /// <summary>
    /// Executes the document validator without saving a document. Save format should be specified.
    /// </summary>
    public static void executeValidator(Document doc, /*SaveFormat*/int sf) throws Exception
    {
        executeValidator(doc, SaveOptions.createSaveOptions(sf));
    }

    /// <summary>
    /// Executes the document validator without saving a document. Save options should be specified.
    /// </summary>
    static DocumentValidator executeValidator(Document doc, SaveOptions options) throws Exception
    {
        DocumentValidator validator = new DocumentValidator();
        SaveInfo saveInfo = new SaveInfo(doc, null, null, options);
        validator.execute(saveInfo);
        return validator;
    }

    /// <summary>
    /// Verifies MD5 hash value of given data.
    /// </summary>
    public static void verifyHash(String hash, byte[] data)
    {
        byte[] dataHash = HashUtil.computeHash(CryptoUtilPal.createHashAlgorithm(DigestAlgorithm.MD_5), data);
        msAssert.areEqual(hash, ArrayUtil.dumpArray(dataHash).replace(" ", ""));
    }

    /// <summary>
    /// Opens Document from ZIP archive.
    /// </summary>
    public static Document openFromZip(String zipFileName) throws Exception
    {
        FileStream fs = File.open(buildTestFileName(zipFileName), FileMode.OPEN, FileAccess.READ, FileShare.READ);
        try /*JAVA: was using*/
        {
            ZipReaderPal zipReader = new ZipReaderPal(fs);
            zipReader.moveToNextEntry();
            MemoryStream docStream = zipReader.loadEntryToMemory();
            try /*JAVA: was using*/
        	{
                return new Document(docStream);
        	}
            finally { if (docStream != null) docStream.close(); }
        }
        finally { if (fs != null) fs.close(); }
    }

    /// <summary>
    /// Opens Document from ZIP archive.
    /// </summary>
    public static Document openFromZip(String zipFileName, String password) throws Exception
    {
        FileStream fs = File.open(buildTestFileName(zipFileName), FileMode.OPEN, FileAccess.READ, FileShare.READ);
        try /*JAVA: was using*/
        {
            ZipReaderPal zipReader = new ZipReaderPal(fs, password);
            zipReader.moveToNextEntry();
            MemoryStream docStream = zipReader.loadEntryToMemory();
            try /*JAVA: was using*/
        	{
                return new Document(docStream);
        	}
            finally { if (docStream != null) docStream.close(); }
        }
        finally { if (fs != null) fs.close(); }
    }

    /// <summary>
    /// Opens Document from ZIP archive.
    /// </summary>
    public static Document openFromZip(String zipFileName, LoadOptions lo) throws Exception
    {
        FileStream fs = File.open(buildTestFileName(zipFileName), FileMode.OPEN, FileAccess.READ, FileShare.READ);
        try /*JAVA: was using*/
        {
            ZipReaderPal zipReader = new ZipReaderPal(fs, lo.getPassword());
            zipReader.moveToNextEntry();
            MemoryStream docStream = zipReader.loadEntryToMemory();
            try /*JAVA: was using*/
        	{
                return new Document(docStream, lo);
        	}
            finally { if (docStream != null) docStream.close(); }
        }
        finally { if (fs != null) fs.close(); }
    }

    /// <summary>
    /// Checks that given string is written to OPC package. Used to test DOCX export.
    /// </summary>
    private static boolean isWrittenToOpcPackageCore(OpcPackage _package, String partName, String text) throws Exception
    {
        OpcPackagePart part = _package.fetchPartByName(partName);
        StreamReader reader = new StreamReader(part.getStream());
        part.getStream().setPosition(0);
        String content = reader.readToEnd();

        return StringUtil.contains(content, text, true);
    }

    /// <summary>
    /// Gets all tests data files (which are corresponding to pattern) from the root folder (and all its subdirectories) of all test documents.
    /// </summary>
    public static String[] getTestDataFiles(String searchPattern) throws Exception
    {
        return getTestDataFiles(searchPattern, ".");
    }

    /// <summary>
    /// Gets all tests data files (which are corresponding to pattern) from the subdirectory (and all its subdirectories) of the root folder of all test documents.
    /// </summary>
    public static String[] getTestDataFiles(String searchPattern, String subdir) throws Exception
    {
        return Directory.getFiles(Path.combine(G_TEST_DATA_PATH, subdir), searchPattern, SearchOption.ALL_DIRECTORIES);
    }

    /// <summary>
    /// Checks if the specified <see cref="ThreadPal"/> has not completed within the specified timeout.
    /// </summary>
    /// <param name="worker"> THe process that should be checked.</param>
    /// <param name="secondsLimit">Timeout in seconds.</param>
    /// <returns>"True" if the process has not finished, otherwise -"false".</returns>
    public static boolean isHanging(ThreadPal worker, int secondsLimit) throws Exception
    {
        worker.start();
        Stopwatch sw = new Stopwatch();
        sw.start();

        while (worker.isAlive() && (sw.getElapsed().getSeconds() < secondsLimit))
            Thread.sleep(500);

        sw.stop();

        // The process did not completed within the specified timeout.
        if (worker.isAlive())
        {
            worker.abort();
            Assert.fail("Most probably test hangs, please check!");
            return true;
        }

        return false;
    }

    /// <summary>
    /// Gets nodes of specified types with a specified text.
    /// </summary>
    private static ArrayList<Node> getNodesWithText(CompositeNode parent, String text, /*params*/ /*NodeType*/int... nodeTypes)
    {
        final String FUZZY = "...";
        boolean isFuzzyAtStart = text.startsWith(FUZZY);
        boolean isFuzzyAtEnd = text.endsWith(FUZZY);

        String searchText = isFuzzyAtStart ? text.substring(FUZZY.length()) : text;
        if (isFuzzyAtEnd)
            searchText = searchText.substring((0), (0) + (searchText.length() - FUZZY.length()));

        ArrayList<Node> foundNodes = new ArrayList<Node>();
        for (Node node : (Iterable<Node>) parent.getChildNodes(nodeTypes, true))
        {
            if (isFuzzyAtStart && isFuzzyAtEnd)
            {
                if (node.getText().contains(searchText))
                    msArrayList.add(foundNodes, node);
            }
            else if (isFuzzyAtStart)
            {
                if (node.getText().endsWith(searchText))
                    msArrayList.add(foundNodes, node);
            }
            else if (isFuzzyAtEnd)
            {
                if (node.getText().startsWith(searchText))
                    msArrayList.add(foundNodes, node);
            }
            else
            {
                if (node.getText().equals(searchText))
                    msArrayList.add(foundNodes, node);
            }
        }

        return foundNodes;
    }

    /// <summary>
    /// Gets first occurred paragraph with a specified text.
    /// </summary>
    static Paragraph getParagraphWithText(CompositeNode parent, String text)
    {
        ArrayList<Node> paragraphs = getNodesWithText(parent, text, NodeType.PARAGRAPH);
        if (paragraphs.size() > 0)
            return (Paragraph)paragraphs.get(0);

        return null;
    }

    /// <summary>
    /// Gets first occurred run with a specified text.
    /// </summary>
    static Run getRunWithText(CompositeNode parent, String text)
    {
        ArrayList<Node> runs = getNodesWithText(parent, text, NodeType.RUN);
        if (runs.size() > 0)
            return (Run)runs.get(0);

        return null;
    }

    /// <summary>
    /// Checks color with diagnostic message.
    /// </summary>
    static void checkColor(Color expectedColor, Color actualColor)
    {
        int expectedValue = expectedColor.getRGB();
        int actualValue = actualColor.getRGB();
        msAssert.areEqual(expectedValue, actualValue, msString.format("Expected: 0x{0:X8}, Actual: 0x{1:X8}", expectedValue, actualValue));
    }

    /// <summary>
    /// Returns HyphPatternsPath + RelativePath.
    /// </summary>
    public static String getInHyphenationPatternsPath(String relativePath) throws Exception
    {
        return getInTestDataPath(Path.combine("Hyphenation\\Patterns", relativePath));
    }

    /// <summary>
    /// The root folder of the solution.
    /// </summary>
    private static /*final*/ String G_ROOT_PATH = TestEnvironment.getDirAwnet();

    /// <summary>
    /// The root folder of all test documents.
    /// </summary>
    private static /*final*/ String G_TEST_DATA_PATH = TestEnvironment.getTestData();

    /// <summary>
    /// The root folder of the "main" test documents.
    /// </summary>
    private static /*final*/ String G_MODEL_PATH = TestEnvironment.getTestData("Model");

    private static /*final*/ String G_TEST_GOLD_PATH = TestEnvironment.getTestGold();

    private static /*final*/ String G_TEST_OUT_PATH = TestEnvironment.getTestOut();
}

