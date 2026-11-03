// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DictionaryExtension;
import java.util.HashMap;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.StreamReader;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.Map;


class PresetGeometryXmlRepository implements IPresetGeometryXmlRepository
{
    public String getPresetGeometryXml(String presetName)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	return  DictionaryExtension.getValueOrNull(getPresetXmls(), presetName);
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    private static HashMap<String, String> loadPresetGeometriesXml() throws Exception
    {
        final String RESOURCE_NAME = "Aspose.Words.Resources.PresetShapeDefinitions.xml";

        Stream stream = ResourceUtil.fetchResourceStream(RESOURCE_NAME);
        try /*JAVA: was using*/
        {
            StreamReader sr = new StreamReader(stream);
            try /*JAVA: was using*/
            {
                HashMap<String, String> result = new HashMap<String, String>(190); // There are about 188 standard preset shapes.

                skipHeader(sr);

                String line = sr.readLine();
                while (line != null)
                {
                    // We have read last line and finish
                    if (msString.startsWith(line, "</presetShapeDefinitons>", StringComparison.ORDINAL))
                        break;

                    String presetName = readPresetName(line);
                    msDictionary.add(result, presetName, line);

                    line = sr.readLine();
                }

                return result;
            }
            finally { if (sr != null) sr.close(); }
        }
        finally { if (stream != null) stream.close(); }
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
    private static Map<String, String> getPresetXmls() throws Exception
    {
        // double-checked locking pattern.
        if (gXmlCache == null)
        {
            synchronized (G_XML_CACHE_SYNC_ROOT)
            {
                if (gXmlCache == null)
                    gXmlCache = loadPresetGeometriesXml();
            }
        }

        return gXmlCache;
    }

    private static /*final*/ Object G_XML_CACHE_SYNC_ROOT = new Object();
    //JAVA: volatile modifier is added to static field with purpose to double-check pattern work in java.
    private static volatile HashMap<String, String> gXmlCache;
}

