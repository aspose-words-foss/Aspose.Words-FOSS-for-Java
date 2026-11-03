// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2016 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



public /*static*/ class TestTxtUtil
{
	/* Simulation of static class by using private constructor */
	private TestTxtUtil()
	{}

    public static void verifyFile(
        String title,
        String fileNameOut,
        String fileNameGold,
        String fileNameMS,
        String fileNameSource) throws Exception
    {
        ComparerParams comparerParams = new ComparerParams();
        comparerParams.setTitle(title);
        comparerParams.setFileNameOut(fileNameOut);
        comparerParams.setFileNameGold(fileNameGold);
        comparerParams.setFileNameMs(fileNameMS);
        comparerParams.setFileNameSrc(fileNameSource);
        ComparerFactoryProducer.getFactory().getTextComparer().execute(comparerParams);

    }

    public static void verifyFile(
        String title,
        String fileNameOut,
        String fileNameGold,
        String fileNameMS) throws Exception
    {
        ComparerParams comparerParams = new ComparerParams();
        comparerParams.setTitle(title);
        comparerParams.setFileNameOut(fileNameOut);
        comparerParams.setFileNameGold(fileNameGold);
        comparerParams.setFileNameMs(fileNameMS);
        ComparerFactoryProducer.getFactory().getTextComparer().execute(comparerParams);
    }
}

