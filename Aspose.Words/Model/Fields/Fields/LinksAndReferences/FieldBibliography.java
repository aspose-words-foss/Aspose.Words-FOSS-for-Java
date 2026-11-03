// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.Collection;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.StreamReader;
import java.util.ArrayList;
import com.aspose.NullableInt32;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;
import com.aspose.NullableExtension;
import com.aspose.ms.System.msArray;


/// <summary>
/// Implements the BIBLIOGRAPHY field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>Inserts the contents of the document's Bibliography part in a bibliographic style.</remarks>
public class FieldBibliography extends Field implements IFieldCodeTokenInfoProvider
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        Document document = fetchDocument();
        Bibliography bibliography = document.getBibliography();

        if (!bibliography.ensureTransform())
            return null;

        if (bibliography.getSources().size() == 0)
            return new FieldUpdateActionInsertErrorMessage(this, MISSED_SOURCES_ERROR_MESSAGE);

        Collection<Source> sources = collectSources(bibliography.getSources());
        if (sources.size() == 0)
            return new FieldUpdateActionInsertErrorMessage(this, MISSED_SOURCES_ERROR_MESSAGE);

        Stream xml = buildBibliographyXml(sources);
        try /*JAVA: was using*/
        {
            if (xml == null)
                return new FieldUpdateActionInsertErrorMessage(this, MISSED_SOURCES_ERROR_MESSAGE);

            // FOSS This is the case where HTML has to be processed.
            throw new UnsupportedOperationException("FOSS");
        }
        finally { if (xml != null) xml.close(); }
    }

    private static String transformBibliographyXml(Stream xml, Bibliography bibliography) throws Exception
    {
        Stream result = bibliography.transform(xml);
        try /*JAVA: was using*/
        {
            if (result == null)
                return null;

            StreamReader reader = new StreamReader(result);
            try /*JAVA: was using*/
        	{
                return reader.readToEnd();
        	}
            finally { if (reader != null) reader.close(); }
        }
        finally { if (result != null) result.close(); }
    }

    private Stream buildBibliographyXml(Iterable<Source> sources) throws Exception
    {
        return new BibliographyXmlBuilder("Bibliography", getEffectiveDefaultLcid())
            .withSources(sources)
            .buildXml();
    }

    private Collection<Source> collectSources(Collection<Source> sources)
    {
        Iterable<String> sourceTags = getSourceTags();
        if ( sourceTags != null )
            return collectSourcesByTags(sources, sourceTags);
        else
            return collectSourcesByFilter(sources);
    }

    private Collection<Source> collectSourcesByFilter(Collection<Source> sources)
    {
        ArrayList<Source> result = new ArrayList<Source>();
        NullableInt32 filterLanguageId = getEffectiveFilterLcid();

        for (Source source : sources)
        {
            if (filterSource(source, filterLanguageId))
                msArrayList.add(result, source);
        }

        return result;
    }

    private static Collection<Source> collectSourcesByTags(Collection<Source> sources, Iterable<String> sourceTags)
    {
        ArrayList<Source> result = new ArrayList<Source>();

        for (String sourceTag : sourceTags)
        {
            Source source = getFirstSourceByTag(sources, sourceTag);
            if (source != null)
                msArrayList.add(result, source);
        }

        return result;
    }

    private static Source getFirstSourceByTag(Iterable<Source> sources, String sourceTag)
    {
        for (Source source : sources)
        {
            if (msString.equals(source.getTag(), sourceTag))
                return source;
        }

        return null;
    }

    private static boolean filterSource(Source source, NullableInt32 languageId)
    {
        if (!languageId.hasValue())
            return true;

        return source.getNormalizedLcid() == languageId.getValue();
    }

    private int getEffectiveDefaultLcid()
    {
        String lcid = getDefaultLcid();
        return FieldBibliographyUtils.getEffectiveLcid(lcid);
    }

    private String getDefaultLcid()
    {
        String filterLanguageId = getFilterLanguageId();
        if (!msString.isNullOrEmpty(filterLanguageId))
            return filterLanguageId;

        String formatLanguageId = getFormatLanguageId();
        if (!msString.isNullOrEmpty(formatLanguageId))
            return formatLanguageId;

        return null;
    }

    private NullableInt32 getEffectiveFilterLcid()
    {
        if (!getFieldCodeCache().hasSwitch(FILTER_LANGUAGE_ID_SWITCH))
            return NullableInt32.NULL;

        String filterLanguageId = getFilterLanguageId();

        if (msString.equals(filterLanguageId, ""))
            return G_UNKNOWN_LCID;

        if (msString.isNullOrEmpty(filterLanguageId))
            return NullableExtension.asNullable(FieldBibliographyUtils.getCurrentCultureLcid());

        return NullableExtension.asNullable(FieldBibliographyUtils.getEffectiveLcid(filterLanguageId));
    }

    private Iterable<String> getSourceTags()
    {
        if (!getFieldCodeCache().hasSwitch(SOURCE_TAG_SWITCH))
            return null;

        ArrayList<String> sourceTags = getFieldCodeCache().getSwitchArgumentsAsStrings(SOURCE_TAG_SWITCH);
        if (sourceTags.size() <= MAX_SOURCE_TAG_SWITCHES_COUNT)
            return sourceTags;

        String[] top10SourceTags = new String[MAX_SOURCE_TAG_SWITCHES_COUNT];
        for (int i = 0; i < top10SourceTags.length; i++)
            top10SourceTags[i] = sourceTags.get(i);

        return msArray.iterableArray(top10SourceTags);
    }

    /// <summary>
    /// Gets or sets the language ID that is used to format the bibliographic sources in the document.
    /// </summary>
    public String getFormatLanguageId() { return getFieldCodeCache().getSwitchArgumentAsString(FORMAT_LANGUAGE_ID_SWITCH); }
    public void setFormatLanguageId(String value) throws Exception { getFieldCodeCache().setSwitch(FORMAT_LANGUAGE_ID_SWITCH, value); }

    /// <summary>
    /// Gets or sets the language ID that is used to filter the bibliographic data to only the sources in the document that
    /// use that language.
    /// </summary>
    public String getFilterLanguageId() { return getFieldCodeCache().getSwitchArgumentAsString(FILTER_LANGUAGE_ID_SWITCH); }
    public void setFilterLanguageId(String value) throws Exception { getFieldCodeCache().setSwitch(FILTER_LANGUAGE_ID_SWITCH, value); }

    /// <summary>
    /// Gets or sets a value so that only the sources with matching Tag element value are displayed in the bibliography.
    /// </summary>
    public String getSourceTag() { return getFieldCodeCache().getSwitchArgumentAsString(SOURCE_TAG_SWITCH); }
    public void setSourceTag(String value) throws Exception { getFieldCodeCache().setSwitch(SOURCE_TAG_SWITCH, value); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*FORMAT_LANGUAGE_ID_SWITCH*/0:
            case /*FILTER_LANGUAGE_ID_SWITCH*/1:
            case /*SOURCE_TAG_SWITCH*/2:
                return FieldSwitchType.HAS_ARGUMENT;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    private static final String FORMAT_LANGUAGE_ID_SWITCH = "\\l";
    private static final String FILTER_LANGUAGE_ID_SWITCH = "\\f";
    private static final String SOURCE_TAG_SWITCH = "\\m";
    private static final String MISSED_SOURCES_ERROR_MESSAGE = "There are no sources in the current document.";

    private static final int MAX_SOURCE_TAG_SWITCHES_COUNT = 10;

    private static /*final*/ NullableInt32 G_UNKNOWN_LCID = new NullableInt32(-1);

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\l",
		"\\f",
		"\\m"
	);

}

