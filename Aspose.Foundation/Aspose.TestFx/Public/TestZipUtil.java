// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2016 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



public /*static*/ class TestZipUtil
{
	/* Simulation of static class by using private constructor */
	private TestZipUtil()
	{}

    public static void verifyFile(
        String title,
        String fileNameSrc,
        String fileNameOut,
        String fileNameGold,
        String fileNameMs) throws Exception
    {
        ComparerParams comparerParams = new ComparerParams();
        comparerParams.setTitle(title);
        comparerParams.setFileNameSrc(fileNameSrc);
        comparerParams.setFileNameOut(fileNameOut);
        comparerParams.setFileNameGold(fileNameGold);
        comparerParams.setFileNameMs(fileNameMs);
        ComparerFactoryProducer.getFactory().getZipComparer().execute(comparerParams);
    }
}

