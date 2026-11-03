// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/02/2019 by Alexey Butalov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.IO.FileAccess;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.IO.Directory;
import java.awt.Color;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Utility methods for unit tests.
/// </summary>
public /*static*/ class TestFxUtil
{
	/* Simulation of static class by using private constructor */
	private TestFxUtil()
	{}

    /// <summary>
    /// Performs common setup of test environment.
    /// </summary>
    public static void setUpTests()
    {
        Debug.setTestDefaults();
        DateTimeUtil.setTestMode();
        RandomUtil.setTestMode();

        // Register encodings support if required.
        EncodingUtil.registerEncodings();
    }

    /// <summary>
    /// Compares two streams and returns the result of comparison.
    /// </summary>
    /// <param name="stream1">First stream to compare.</param>
    /// <param name="stream2">Second stream to compare.</param>
    /// <returns>True, if streams have the same content.</returns>
    public static boolean compareStreams(Stream stream1, Stream stream2) throws Exception
    {
        if (stream1.getLength() != stream2.getLength())
            return false;

        final int BUFFER_SIZE = 8192;
        byte[] buffer1 = new byte[BUFFER_SIZE];
        byte[] buffer2 = new byte[BUFFER_SIZE];

        while (true)
        {
            int count1 = stream1.read(buffer1, 0, BUFFER_SIZE);
            int count2 = stream2.read(buffer2, 0, BUFFER_SIZE);

            if (count1 != count2)
                return false;

            // This ends the loop.
            if (count1 == 0)
                return true;

            for (int i = 0; i < count1; i++)
            {
                if (buffer1[i] != buffer2[i])
                    return false;
            }
        }
    }

    /// <summary>
    /// Compares two byte arrays and returns the result of comparison.
    /// </summary>
    /// <param name="buffer1">First byte array to compare.</param>
    /// <param name="buffer2">Second byte array to compare.</param>
    /// <returns>True, if byte arrays have the same content.</returns>
    public static boolean compareBuffers(byte[] buffer1, byte[] buffer2)
    {
        if (buffer1.length != buffer2.length)
            return false;

        for (int i = 0; i < buffer1.length; i++)
        {
            if (buffer1[i] != buffer2[i])
                return false;
        }

        return true;
    }

    /// <summary>
    /// Compares two files and returns the result of comparison.
    /// </summary>
    /// <remarks>
    /// First file should exist in the provided path. Otherwise, an exception is thrown.
    /// The second file may be absent. Returns false if the files are different or the second file is absent.
    /// </remarks>
    /// <param name="filename1">Name of the first file to compare.</param>
    /// <param name="filename2">Name of the second file to compare.</param>
    public static boolean compareFiles(String filename1, String filename2) throws Exception
    {
        // First file must be present. Otherwise, throw an exception.
        if (!File.exists(filename1))
            throw new IllegalStateException(msString.format("File is not found: {0}", filename1));

        // The second file may be absent. Files are considered not equal then.
        if (!File.exists(filename2))
            return false;

        FileStream stream1 = new FileStream(filename1, FileMode.OPEN, FileAccess.READ);
        try /*JAVA: was using*/
        {
            FileStream stream2 = new FileStream(filename2, FileMode.OPEN, FileAccess.READ);
            try /*JAVA: was using*/
            {
                return compareStreams(stream1, stream2);
            }
            finally { if (stream2 != null) stream2.close(); }
        }
        finally { if (stream1 != null) stream1.close(); }
    }

    public static int getFileSize(String fileName) throws Exception
    {
        Stream stream = File.openRead(fileName);
        try /*JAVA: was using*/
    	{
            return (int)stream.getLength();
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Returns TestOutPath + RelativePath.
    /// </summary>
    public static String getInTestOutPath(String relativePath)
    {
        return correctPath(Path.combine(TestOutPath, relativePath));
    }

    /// <summary>
    /// If file name is rooted, returns as is. Otherwise prepends the TestData folder.
    /// </summary>
    public static String buildTestFileName(String fileName) throws Exception
    {
        if (Path.isPathRooted(fileName))
            return fileName;
        else
            return correctPath(Path.combine(TestDataPath, fileName));
    }

    /// <summary>
    /// Prepends root test data folder (i.e. "awnet/TestData") to the relative path.
    /// </summary>
    public static String buildRootTestFileName(String relativePath)
    {
        return correctPath(Path.combine(TestEnvironment.getTestData(), relativePath));
    }

    /// <summary>
    /// Creates a directory for the specified file if the directory does not exist.
    /// </summary>
    public static void ensureDirectoryForFileExists(String fileName)
    {
        String dir = Path.getDirectoryName(fileName);
        if (!Directory.exists(dir))
            Directory.createDirectory(dir);
    }

    public static String buildOutFileName(String testName, String userSuffix, String extension) throws Exception
    {
        String result = buildDerivedFileName(testName, "TestOut", userSuffix + " Out", extension);
        return TestUtilPal.correctOutFileNameIfNeeded(result);
    }

    public static String buildGoldFileName(String testName, String userSuffix, String extension) throws Exception
    {
        String result = buildDerivedFileName(testName, "TestGold", userSuffix + " Gold", extension);
        return TestUtilPal.correctGoldFileNameIfNeeded(result);
    }

    private static String buildDerivedFileName(String testName, String dirReplacement, String fileNameSuffix,
        String extension) throws Exception
    {
        // This makes a full path in case just a relative path was given to us.
        testName = buildTestFileName(testName);

        String dirOriginal = Path.getDirectoryName(testName);
        String dirNew = dirOriginal.replace("TestData", dirReplacement);
        if (msString.equals(dirNew, dirOriginal))
            dirNew = dirOriginal.replace("TestGold", dirReplacement);
        if (msString.equals(dirNew, dirOriginal))
            dirNew = dirOriginal.replace("TestOut", dirReplacement);


        String result = Path.combine(dirNew, Path.getFileNameWithoutExtension(testName));
        result = msString.plusEqOperator(result, fileNameSuffix);
        result = msString.plusEqOperator(result, StringUtil.hasChars(extension) ? extension : Path.getExtension(testName));

        return correctPath(result);
    }

    /// <summary>
    /// Accepts the specified OUT file over the existing GOLD file.
    /// This method is platform-aware and will create Java golds in a separate folder from dot net golds.
    /// </summary>
    public static void acceptNewGold(String fileNameOut, String fileNameGold) throws Exception
    {
        // Accepting on Java needs to create a Java gold to avoid damaging the dotnet gold.
        fileNameGold = TestUtilPal.getMostActualGold(fileNameGold);
        ensureDirectoryForFileExists(fileNameGold);
        File.copy(fileNameOut, fileNameGold, true);
    }

    /// <summary>
    /// Makes the specified path correct for Windows or Linux both for .NET and Java.
    /// On Linux replaces all '\' with '/' and also replaces drive path with the "root" e.g. equivalent of "X:\".
    /// </summary>
    /// <param name="anyPath">Path string with '\' as delimiter.</param>
    public static String correctPath(String anyPath)
    {
        switch (PlatformUtilPal.getPlatform())
        {
            case Platform.WINDOWS:
            {
                // The path is expected to be with '\' already, do nothing.
                return anyPath;
            }
            case Platform.ANDROID:
            case Platform.UNIX:
            case Platform.MAC_OS:
            case Platform.I_OS:
            {
                // If we have something like this:
                // X:\awnet\Aspose.Words\TestData\xxx.doc
                if (UriUtil.isAbsoluteLocalFilePathWindows(anyPath))
                {
                    // We need to convert it to something like this:
                    // /media/sf_F_DRIVE/awnet/Aspose.Words/TestData/xxx.doc

                    // We achieve this by:
                    // 1. Drop the drive letter and the first slash.
                    anyPath = anyPath.substring(3);
                    // 2. Prepend the "project parent" path so it can be any location on Linux.
                    anyPath = Path.combine(TestEnvironment.getRawRoot(), anyPath);
                }

                // Turn slashes into Linux style.
                return msString.replace(anyPath, '\\', Path.DirectorySeparatorChar);
            }
            default:
                throw new IllegalStateException("Unexpected platform.");
        }
    }

    /// <summary>
    /// Checks if gold file path must be replaced with local or platform-specific gold.
    /// If not returns <paramref name="originalGold"/> as is, otherwise return properly adjusted path.
    /// </summary>
    public static String getReplacementGoldIfExists(String originalGold) throws Exception
    {
        // Assumed is that current environment is bound to a single platform, e.g. C++ & Linux
        // thus we may only have original, platform and local gold at most.
        if (TestSettings.getUseLocalGolds())
        {
            String gold = originalGold;
            // This will handle both Aspose.Words and Aspose.Foundation golds.
            // Local golds will have them both in the same directory though.
            // It may lead to a conflict eventually but chances are low.
            final String TEST_GOLD = "\\TestGold\\";
            int testGoldPos = StringUtil.indexOfIgnoreCase(gold, TEST_GOLD);
            if (testGoldPos > 0)
                gold = gold.substring(testGoldPos + TEST_GOLD.length());

            // We first check if local golds should be used, and if one exists.
            // Otherwise default to platform golds if present.
            // Finally use original.

            String localGold = Path.combine(TestEnvironment.getTestGoldLocal(), gold);
            if (File.exists(localGold))
                return localGold;
        }

        return TestUtilPal.getMostActualExistingGold(originalGold);
    }

    /// <summary>
    /// Checks color by ARGB value with diagnostic message.
    /// </summary>
    /// <remarks>
    ///  System.Drawing.Color's Equals() checks all structure fields including Name etc.
    ///  For instance, Color 0xFFFFFFFF has the same A, R, G, B values as Color.White
    ///  but different (null) Name -- so the Colors are not equal.
    ///  This utility checks only ARGB values.
    /// </remarks>
    public static void areEqualArgb(Color expectedColor, Color actualColor)
    {
        int expectedValue = expectedColor.getRGB();
        int actualValue = actualColor.getRGB();
        msAssert.areEqual(expectedValue, actualValue, msString.format("Expected: 0x{0:X8}, Actual: 0x{1:X8}", expectedValue, actualValue));
    }

    /// <summary>
    /// The root folder of all Aspose.Foundation test documents.
    /// </summary>
    static String TestDataPath = TestEnvironment.getFoundationTestData();

    /// <summary>
    /// The root folder of all Aspose.Foundatioin output documents.
    /// </summary>
    static String TestOutPath = TestEnvironment.getFoundationTestOut();
}

