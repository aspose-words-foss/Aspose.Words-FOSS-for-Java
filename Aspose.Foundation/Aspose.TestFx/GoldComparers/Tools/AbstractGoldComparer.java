// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2016 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msConsole;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;


public abstract class AbstractGoldComparer
{
    public /*virtual*/ void execute(ComparerParams comparerParams) throws Exception { }

    // Only FailedGoldTestStorageEngine uses this method
    public /*virtual*/ boolean executeForm(ComparerParams comparerParams) throws Exception { return false; }

    public /*virtual*/ void verifyConformance(ComparerParams comparerParams) throws Exception { }

    /// <summary>
    /// When out does not match gold we can ananlyze further and pass the test if conditions are right.
    /// As part of this we can also automatically accept out as new gold.
    ///
    /// If we return False then typically the caller will show compare form.
    /// </summary>
    /// <returns>True if test shall pass, False otherwise.</returns>
    public static boolean tryPassAutomatically(String fileNameOut, String fileNameGold) throws Exception
    {
        boolean acceptAlways = TestSettings.getAcceptGoldAlways();
        boolean passed = false;

        // If we accept any outcome then there is no point in computing that outcome
        // We need to want to compare images in order to want to compute and/or compare labels.
        if (!acceptAlways && TestSettings.getCompareImages())
            passed = ImageFileComparer.compareDocumentsAsImages(fileNameOut, fileNameGold);

        boolean acceptIfPassed = TestSettings.getAcceptGoldWhenPass();
        if (acceptAlways || (acceptIfPassed && passed))
        {
            TestFxUtil.acceptNewGold(fileNameOut, fileNameGold);
            System.out.println(msString.format("Test {0}, accepted new gold '{1}'", passed ? "passed" : "failed", fileNameGold));

            // even if test failed we accepted new gold which means now we treat test as passed
            return true;
        }

        // We do not have to accept new gold when test passed with a difference
        return passed;
    }

    public /*enum*/ final class DocumentType
    {
        private DocumentType(){}
        
        public static final int IMG = 0;
        public static final int PDF = 1;
        public static final int XPS = 2;
        public static final int PS = 4;
        public static final int EMF = 5;
        public static final int TXT = 6;
        public static final int WOFF = 7;
        public static final int ZIP = 8;
        public static final int MHTML = 9;
        public static final int PCL = 10;
        public static final int EPS = 11;
        public static final int MOBI = 12;
        public static final int AZW_3 = 13;

        public static final int length = 13;
    }

    /// <summary>
    /// This class represents Test Tool. 
    /// It keeps the path to the .exe file and keeps command line arguments.
    /// </summary>
    public static class Tool
    {
        public Tool(String toolExe)
        {
        	this(toolExe, null);
	
        }

        public Tool(String toolExe, /*params*/ String... cmd)
        {
            mToolExe = TestEnvironment.getDirTools() + TestEnvironment.normalizePath(toolExe);
            mCmdLine = cmd;
        }

        private /*final*/ String[] mCmdLine;
        private /*final*/ String mToolExe;

        public String getExe() { return mToolExe; }
        public String getCmdLine1() { return mCmdLine[0]; }
        public String getCmdLine2() { return mCmdLine[1]; }

        public String[] getCmdLine() { return mCmdLine; }

        public /*override*/ String toString()
        {
            StringBuilder str = new StringBuilder();
            msStringBuilder.append(str, mToolExe);
            msStringBuilder.append(str, ": ");
            for (String line : getCmdLine())
                msStringBuilder.append(msStringBuilder.append(str, line), " | ");

            return str.toString();
        }
    }
}

