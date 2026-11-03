// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Dmitry Bormashov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DictionaryExtension;
import java.util.HashMap;
import com.aspose.ms.System.IO.StreamReader;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Collections.msDictionary;


class PresetTextWarpXmlRepository implements IPresetTextWarpXmlRepository
{
    public String getPresetTextWarpXml(String presetName)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	return DictionaryExtension.getValueOrNull(getPresetXmls(), presetName);
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    private static HashMap<String, String> loadPresetTextWarpXml() throws Exception
    {
        final String RESOURCE_NAME = "Aspose.Words.Resources.PresetTextWarpDefinitions.xml";

        StreamReader sr = new StreamReader(ResourceUtil.fetchResourceStream(RESOURCE_NAME));
        try /*JAVA: was using*/
        {
            // There are about 188 standard preset shapes.
            HashMap<String, String> result = new HashMap<String, String>(190);

            skipHeader(sr);

            while (true)
            {
                String line = sr.readLine();
                if (line == null)
                    break;

                // We have read last line and finish
                if (msString.startsWith(line, "</presetTextWarpDefinitions>", StringComparison.ORDINAL))
                    break;

                String presetName = readPresetName(line);
                msDictionary.add(result, presetName, line);
            }

            return result;
        }
        finally { if (sr != null) sr.close(); }
    }

    private static String readPresetName(String line)
    {
        int tagStart = msString.indexOf(line, "<", StringComparison.ORDINAL);
        int tagEnd = msString.indexOf(line, ">", StringComparison.ORDINAL);
        return line.substring((tagStart + 1), (tagStart + 1) + (tagEnd - tagStart - 1));
    }

    private static void skipHeader(StreamReader reader) throws Exception
    {
        reader.readLine(); // Skip <?xml...
        reader.readLine(); // Skip <presetShapeDefinitons>
    }

    /// <summary>
    /// Provides access to the XML definitions of preset geometries.
    /// Initialized and loaded on first access.
    /// </summary>
    private static HashMap<String, String> getPresetXmls() throws Exception
    {
        // double-checked locking pattern.
        if (gXmlCache == null)
        {
            synchronized (G_XML_CACHE_SYNC_ROOT)
            {
                if (gXmlCache == null)
                    gXmlCache = loadPresetTextWarpXml();
            }
        }

        return gXmlCache;
    }

    private static /*final*/ Object G_XML_CACHE_SYNC_ROOT = new Object();
    //JAVA: volatile modifier is added to static field with purpose to double-check pattern work in java.
    private static volatile HashMap<String, String> gXmlCache;
}

