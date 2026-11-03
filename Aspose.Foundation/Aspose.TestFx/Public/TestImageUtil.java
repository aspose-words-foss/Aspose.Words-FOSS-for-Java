// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/02/2026 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



public /*static*/ class TestImageUtil
{
	/* Simulation of static class by using private constructor */
	private TestImageUtil()
	{}

    public static void verifyFile(String fileNameOut, String fileNameGold, String fileNameSource) throws Exception
    {
        ComparerParams comparerParams = new ComparerParams();
        comparerParams.setFileNameOut(fileNameOut);
        comparerParams.setFileNameGold(fileNameGold);
        comparerParams.setFileNameSrc(fileNameSource);
        ComparerFactoryProducer.getFactory().getImageComparer().execute(comparerParams);
    }
}

