// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2023 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.AnyXmlBuilder;
import java.util.Map;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;
import com.aspose.IAnyXmlBuilder;
import com.aspose.HashSetGeneric;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import java.util.HashMap;
import com.aspose.AnyXmlReader;
import java.lang.Integer;
import com.aspose.words.ref.Ref;


/// <summary>
/// Builds XML to be transformed with a bibliography style XSLT stylesheet.
/// </summary>
class BibliographyXmlBuilder
{
    BibliographyXmlBuilder(String name, int defaultLcid)
    {
        mName = name;
        mDefaultLcid = defaultLcid;
    }

    /// <summary>
    /// Builds XML.
    /// </summary>
    Stream buildXml() throws Exception
    {
        MemoryStream stream = new MemoryStream();
        AnyXmlBuilder builder = new AnyXmlBuilder(stream, false);

        builder.startDocument(mName);
        builder.writeAttributeString("xmlns:b", com.aspose.words.Bibliography.XML_NAME_SPACE);
        builder.writeAttributeString("xmlns", com.aspose.words.Bibliography.XML_NAME_SPACE);

        appendLocales(builder, mDefaultLcid, selectLocales());

        for (Map.Entry<String, String> element : mOptions.entrySet())
            appendElement(builder, element);

        for (Source source : mSources)
            builder.writeRaw(source.getNormalizedXml());

        builder.endDocument();

        stream.setPosition(0);
        return stream;
    }

    /// <summary>
    /// Adds an option without value if condition is met.
    /// </summary>
    BibliographyXmlBuilder withOptionIf(String name, boolean condition)
    {
        if (condition)
            mOptions.put(name, null);

        return this;
    }

    /// <summary>
    /// Adds an option with value if value is no null.
    /// </summary>
    BibliographyXmlBuilder withOptionIf(String name, String value)
    {
        if (value != null)
            mOptions.put(name, value);

        return this;
    }

    /// <summary>
    /// Adds a source.
    /// </summary>
    BibliographyXmlBuilder withSource(Source source)
    {
        msArrayList.add(mSources, source);
        return this;
    }

    /// <summary>
    /// Adds a sources.
    /// </summary>
    BibliographyXmlBuilder withSources(Iterable<Source> sources)
    {
        msArrayList.addRange(mSources, sources);
        return this;
    }

    private Iterable<Integer> selectLocales()
    {
        ArrayList<Integer> result = new ArrayList<Integer>();

        for (Source source : mSources)
        {
            if (source.hasNormalizedLcid())
                msArrayList.add(result, source.getNormalizedLcid());
        }

        return result;
    }

    private static void appendElement(IAnyXmlBuilder builder, Map.Entry<String, String> element)
    {
        if (element.getValue() != null)
            builder.writeElement(element.getKey(), element.getValue());
        else
            builder.writeEmptyElement(element.getKey());
    }

    private static void appendLocales(IAnyXmlBuilder builder, int defaultLcid, Iterable<Integer> lcids) throws Exception
    {
        builder.startElement("Locals");
        builder.writeElement("DefaultLCID", defaultLcid);

        HashSetGeneric<Integer> uniqueLcids = new HashSetGeneric<Integer>(lcids);
        uniqueLcids.add(defaultLcid);

        for (int lcid : uniqueLcids)
            appendLocale(builder, lcid);

        builder.endElement();
    }

    private static void appendLocale(IAnyXmlBuilder builder, int lcid) throws Exception
    {
        String localeXml = null;
        Ref<String> referenceToLocaleXml = new Ref<String>(localeXml);
        boolean outRefCondition0 = msDictionary.tryGetValue(G_LOCALES, lcid, /*out*/ referenceToLocaleXml);
        localeXml = referenceToLocaleXml.get();
        if (outRefCondition0)
            builder.writeRaw(localeXml);
        else
            Debug.fail(msString.format("Unknown bibliography locale '{0}'", lcid));
    }

    /// <remarks>
    /// See also TestLinksAndReferences.GenerateBibliographyLocales
    /// </remarks>
    private static HashMap<Integer, String> loadLocales()
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	Stream stream = ResourceUtil.fetchResourceStream(LOCALES_RESOURCE_NAME);
        	try /*JAVA: was using*/
        	{
            	AnyXmlReader reader = new AnyXmlReader(stream, true);

            	String rootElement = reader.getLocalName();

            	HashMap<Integer, String> result = new HashMap<Integer, String>();

            	if (reader.readChild(rootElement))
            	{
                	while (!reader.isEndElement(rootElement))
                	{
                    	Debug.assertTrue("Local".equals(reader.getLocalName()));

                    	String localeXml = reader.readOuterXml();
                    	int lcid = readLcid(localeXml);

                    	msDictionary.add(result, lcid, localeXml);
                	}
            	}

            	Debug.assertTrue(result.size() == 258);

            	return result;
        	}
        	finally { if (stream != null) stream.close(); }
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    private static int readLcid(String xml)
    {
        AnyXmlReader reader = new AnyXmlReader(xml, null);
        return Integer.parseInt(reader.readAttribute("LCID", null));
    }

    private /*final*/ ArrayList<Source> mSources = new ArrayList<Source>();
    private /*final*/ HashMap<String, String> mOptions = new HashMap<String, String>();
    private /*final*/ String mName;
    private /*final*/ int mDefaultLcid;

    private static /*final*/ HashMap<Integer, String> G_LOCALES = loadLocales();
    private static final String LOCALES_RESOURCE_NAME = "Aspose.Words.Resources.Bibliography.BibliographyLocales.xml";
}

