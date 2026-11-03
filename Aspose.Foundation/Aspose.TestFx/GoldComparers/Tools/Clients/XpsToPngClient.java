// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Path;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;


/// <summary>
/// Implements conversion of Xps format to Png files.
/// </summary>
/*static*/ class XpsToPngClient
{
	/* Simulation of static class by using private constructor */
	private XpsToPngClient()
	{}

    /// <summary>
    /// Default path to the server executable.
    /// You can override this using <see cref="ENVAR"/> environment variable.
    /// </summary>
    static final String SERVER = "x:/awnet/aspose.foundation/tools/imageservices/bin/aspose.xpstopng.exe";
    static /*final*/ String PIPE = com.aspose.ms.System.IO.Path.getFileNameWithoutExtension(SERVER) + ".pipe";
    static /*final*/ String MUTEX = com.aspose.ms.System.IO.Path.getFileNameWithoutExtension(SERVER) + ".mutex";
    static /*final*/ String ENVAR = com.aspose.ms.System.IO.Path.getFileNameWithoutExtension(SERVER) + ".path";

    public
        static
        /*struct*/ final class ConvertSourceParam
    {
    	public ConvertSourceParam(){}
    	
        public String InputFile;
        public String Pages;
    }

    /// <summary>
    /// Converts Xps documents to Png images.
    /// </summary>
    public static void convert(String outputFolder, ConvertSourceParam[] args) throws Exception
    {
        if (args.length <= 0)
            throw new IllegalArgumentException("args");

        ArrayList<String> p = new ArrayList<String>();
        msArrayList.add(p, "-o" + outputFolder);
        for (ConvertSourceParam a : args)
        {
            msArrayList.add(p, a.InputFile);
            if (!msString.isNullOrWhiteSpace(a.Pages))
                msArrayList.add(p, "-p" + a.Pages);
        }

        ClientUtil.postServerJobGetResults(MUTEX, PIPE, SERVER, ENVAR, msArrayList.toArray(p, new String[0]));
    }
}

