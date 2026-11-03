// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IDisposable;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.ms;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.Stream;
import java.util.Collection;
import com.aspose.CompositeDisposable;
import java.util.ArrayList;
import com.aspose.ISetGeneric;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.NullableBool;
import com.aspose.NullableBoolUtil;
import com.aspose.StringUtil;
import com.aspose.HashSetGeneric;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.Map;
import com.aspose.words.ref.Ref;


/// <summary>
/// Implements the CITATION field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Inserts the contents of the <b>Source</b> element with a specified <b>Tag</b> element using a bibliographic style.
/// </remarks>
public class FieldCitation extends Field implements IFieldCodeTokenInfoProvider
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        Bibliography bibliography = fetchDocument().getBibliography();

        if (!bibliography.ensureTransform())
            return null;

        String sourceTag = getSourceTag();
        if (msString.isNullOrEmpty(sourceTag))
            return new FieldUpdateActionInsertErrorMessage(this, MISSED_SOURCES_ERROR_MESSAGE);

        Iterable<Stream> xmls = buildCitationsXmls(bibliography.getSources(), sourceTag);
        if (xmls == null)
            return new FieldUpdateActionInsertErrorMessage(this, INVALID_SOURCES_ERROR_MESSAGE);

        Collection<Stream> htmls;
        CompositeDisposable<Stream> compositeDisposableResource = new CompositeDisposable<Stream>(xmls);
        try /*JAVA: was using*/
        {
            htmls = transformCitationsXmls(xmls, bibliography);
        }
        finally { if (compositeDisposableResource != null) compositeDisposableResource.dispose(); }

        if (htmls == null)
            return new FieldUpdateActionInsertErrorMessage(this, INVALID_SOURCES_ERROR_MESSAGE);

        CompositeDisposable<Stream> compositeDisposableResource1 = new CompositeDisposable<Stream>(htmls);
        try /*JAVA: was using*/
        {
        IDisposable iDisposableResource = getUpdateContext().removeOldResultSafe();
        try /*JAVA: was using*/
        {
            appendLeadingSpaceIfNeeded();

            for (Stream html : htmls)
                appendHtml(html);
        }
        finally { if (iDisposableResource != null) iDisposableResource.dispose(); }
        }
        finally { if (compositeDisposableResource1 != null) compositeDisposableResource1.dispose(); }

        return new FieldUpdateActionDoNothing(this);
    }

    private ArrayList<FieldCitation.Citation> collectCitations(
        Collection<Source> sources,
        String sourceTag,
        ISetGeneric<Person> repeatedAuthors,
        ISetGeneric<String> nonUniqueLastNames)
    {
        Citation lastCitation = findCitation(sources, sourceTag, repeatedAuthors, nonUniqueLastNames);
        if (lastCitation == null)
            return null;

        ArrayList<FieldCitation.Citation> result = new ArrayList<FieldCitation.Citation>();
        msArrayList.add(result, lastCitation);

        int switchesCount = 0;
        for (FieldSwitch fieldSwitch : getFieldCodeCache().getSwitches())
        {
            String switchName = fieldSwitch.getInvariantName();
            switch (gStringSwitchMap.of(switchName))
            {
                case /*ANOTHER_SOURCE_TAG_SWITCH*/0:
                    String anotherSourceTag = fieldSwitch.getArgumentNormalizedText();
                    lastCitation = findCitation(sources, anotherSourceTag, repeatedAuthors, nonUniqueLastNames);
                    if (lastCitation == null)
                        return null;

                    msArrayList.add(result, lastCitation);
                    break;
                case /*SUPPRESS_AUTHOR_SWITCH*/1:
                    lastCitation.setSuppressAuthor(true);
                    break;
                case /*SUPPRESS_TITLE_SWITCH*/2:
                    lastCitation.setSuppressTitle(true);
                    break;
                case /*SUPPRESS_YEAR_SWITCH*/3:
                    lastCitation.setSuppressYear(true);
                    break;
                case /*PREFIX_SWITCH*/4:
                    lastCitation.setPrefix(fieldSwitch.getArgumentNormalizedText());
                    break;
                case /*SUFFIX_SWITCH*/5:
                    lastCitation.setSuffix(fieldSwitch.getArgumentNormalizedText());
                    break;
                case /*VOLUME_NUMBER_SWITCH*/6:
                    lastCitation.setVolumeNumber(fieldSwitch.getArgumentNormalizedText());
                    break;
                case /*PAGE_NUMBER_SWITCH*/7:
                    lastCitation.setPageNumber(fieldSwitch.getArgumentNormalizedText());
                    break;
                default:
                    break;
            }

            // MS Word takes into account only first 10 field specific switches.
            if (getSwitchTypeInternal(switchName) != FieldSwitchType.UNKNOWN && (++switchesCount == MAX_EFFECTIVE_SWITCHES_COUNT))
                break;
        }

        return result;
    }

    private static Citation findCitation(
        Collection<Source> sources,
        String sourceTag,
        ISetGeneric<Person> repeatedAuthors,
        ISetGeneric<String> nonUniqueLastNames)
    {
        Source source = getFirstSourceByTag(sources, sourceTag);
        if (source == null)
            return null;

        boolean repeatedAuthor = isRepeatedAuthor(source, repeatedAuthors);
        boolean nonUniqueLastName = isNonUniqueLastName(source, nonUniqueLastNames);

        return new Citation(source, repeatedAuthor, nonUniqueLastName);
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

    private void appendLeadingSpaceIfNeeded()
    {
        if (!needAppendLeadingSpace())
            return;

        getEnd().getParentNode().insertBefore(new Run(getDocument(), " "), getEnd());
    }

    private boolean needAppendLeadingSpace()
    {
        /*NullableBool*/byte leadingSpace = needAppendLeadingSpace(getStart().getPreviousNonAnnotationSibling(), true);
        if (leadingSpace != NullableBool.NOT_DEFINED)
            return leadingSpace == NullableBool.TRUE;

        Paragraph previousPara = ms.as(getStart().getParentParagraph().getPreviousNonAnnotationSibling(), Paragraph.class);
        if (previousPara == null)
            return false;

        leadingSpace = needAppendLeadingSpace(previousPara.getLastNonAnnotationChild(), false);
        return NullableBoolUtil.getValueOrDefault(leadingSpace,false);
    }

    private static /*NullableBool*/byte needAppendLeadingSpace(Node node, boolean appendIfWhitespace)
    {
        while (node != null)
        {
            if (node instanceof FieldChar)
                return NullableBool.TRUE;

            if (node.getTextLength() != 0)
            {
                String text = node.getText();
                boolean isWhitespace = StringUtil.isWhiteSpace(text.charAt(text.length() - 1));
                return NullableBoolUtil.asNullable(isWhitespace == appendIfWhitespace);
            }

            node = node.getPreviousNonAnnotationSibling();
        }

        return NullableBool.NOT_DEFINED;
    }

    private void appendHtml(Stream html) throws Exception
    {
        Document document = new Document(html, new HtmlLoadOptions());

        Section section = document.getFirstSection();
        if (section == null)
            return;

        Body body = section.getBody();
        if (body == null)
            return;

        Paragraph paragraph = body.getFirstParagraph();
        if (paragraph == null)
            return;

        NodeImporter importer = new NodeImporter(document, getDocument(), ImportFormatMode.USE_DESTINATION_STYLES);
        paragraph = (Paragraph)importer.importNode(paragraph, true);

        getEnd().insertPrevious(paragraph.getFirstChild(), null);
    }

    private static Collection<Stream> transformCitationsXmls(
        Iterable<Stream> xmls,
        Bibliography bibliography) throws Exception
    {
        ArrayList<Stream> result = new ArrayList<Stream>();

        for (Stream xml : xmls)
        {
            Stream html = bibliography.transform(xml);
            if (html == null)
                return null;

            msArrayList.add(result, html);
        }

        return result;
    }

    private Collection<Stream> buildCitationsXmls(Collection<Source> sources, String sourceTag) throws Exception
    {
        ArrayList<Stream> result = new ArrayList<Stream>();

        ISetGeneric<Person> repeatedAuthors = findRepeatedAuthors(sources);
        ISetGeneric<String> nonUniqueLastNames = findNonUniqueLastNames(sources);

        ArrayList<FieldCitation.Citation> citations = collectCitations(sources, sourceTag, repeatedAuthors, nonUniqueLastNames);
        if (citations == null)
            return null;

        int defaultLcid = FieldBibliographyUtils.getEffectiveLcid(getFormatLanguageId());

        for (int i = 0; i < citations.size(); i++)
        {
            boolean isFirst = i == 0;
            boolean isLast = i == citations.size() - 1;

            Stream xml = buildCitationXml(citations.get(i), defaultLcid, isFirst, isLast);

            msArrayList.add(result, xml);
        }

        return result;
    }

    private static ISetGeneric<Person> findRepeatedAuthors(Iterable<Source> sources)
    {
        HashSetGeneric<Person> allPersons = new HashSetGeneric<Person>();
        HashSetGeneric<Person> repeatedPersons = new HashSetGeneric<Person>();

        for (Source source : sources)
        {
            ArrayList<Person> persons = source.expandPersons();

            for (Person person : persons)
            {
                if (allPersons.contains(person))
                    repeatedPersons.add(person);
            }

            for (Person person : persons)
                allPersons.add(person);
        }

        return repeatedPersons;
    }

    private static ISetGeneric<String> findNonUniqueLastNames(Iterable<Source> sources)
    {
        HashMap<String, HashSetGeneric<Person>> allLastNames =
            new HashMap<String, HashSetGeneric<Person>>();

        for (Source source : sources)
        {
            for (Person person : source.expandPersons())
            {
                HashSetGeneric<Person> lastName = null;
                Ref<HashSetGeneric<Person>> referenceToLastName = new Ref<HashSetGeneric<Person>>(lastName);
                boolean outRefCondition0 = !msDictionary.tryGetValue(allLastNames, person.getLast(), /*out*/ referenceToLastName);
                lastName = referenceToLastName.get();
                if (outRefCondition0)
                {
                    lastName = new HashSetGeneric<Person>();
                    msDictionary.add(allLastNames, person.getLast(), lastName);
                }

                lastName.add(person);
            }
        }

        HashSetGeneric<String> nonUniqueLastNames = new HashSetGeneric<String>();
        for (Map.Entry<String, HashSetGeneric<Person>> pair : allLastNames.entrySet())
        {
            if (pair.getValue().getCount() > 1)
                nonUniqueLastNames.add(pair.getKey());
        }

        return nonUniqueLastNames;
    }

    private static boolean isRepeatedAuthor(Source source, ISetGeneric<Person> repeatedAuthors)
    {
        for (Person person : source.expandPersons())
        {
            if (repeatedAuthors.contains(person))
                return true;
        }

        return false;
    }

    private static boolean isNonUniqueLastName(Source source, ISetGeneric<String> nonUniqueLastNames)
    {
        for (Person person : source.expandPersons())
        {
            if (nonUniqueLastNames.contains(person.getLast()))
                return true;
        }

        return false;
    }

    private static Stream buildCitationXml(Citation citation, int defaultLcid, boolean isFirst, boolean isLast) throws Exception
    {
        int effectiveDefaultLcid = citation.getSource().hasNormalizedLcid() ? citation.getSource().getNormalizedLcid() : defaultLcid;

        return new BibliographyXmlBuilder("Citation", effectiveDefaultLcid)
            .withOptionIf("FirstAuthor", isFirst)
            .withOptionIf("LastAuthor", isLast)
            .withOptionIf("RepeatedAuthor", citation.getRepeatedAuthor())
            .withOptionIf("NonUniqueLastName", citation.getNonUniqueLastName())
            .withOptionIf("NoAuthor", citation.getSuppressAuthor())
            .withOptionIf("NoTitle", citation.getSuppressTitle())
            .withOptionIf("NoYear", citation.getSuppressYear())
            .withOptionIf("PagePrefix", citation.getPrefix())
            .withOptionIf("PageSuffix", citation.getSuffix())
            .withOptionIf("Volume", citation.getVolumeNumber())
            .withOptionIf("Pages", citation.getPageNumber())
            .withSource(citation.getSource())
            .buildXml();
    }

    /// <summary>
    /// Gets or sets a value that matches the <b>Tag</b> element's value of the source to insert.
    /// </summary>
    public String getSourceTag() { return getFieldCodeCache().getArgumentAsString(SOURCE_TAG_ARGUMENT_INDEX); }
    public void setSourceTag(String value) throws Exception { getFieldCodeCache().setArgument(SOURCE_TAG_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the language ID that is used in conjunction with the specified bibliographic style to format the citation
    /// in the document.
    /// </summary>
    public String getFormatLanguageId() { return getFieldCodeCache().getSwitchArgumentAsString(FORMAT_LANGUAGE_ID_SWITCH); }
    public void setFormatLanguageId(String value) throws Exception { getFieldCodeCache().setSwitch(FORMAT_LANGUAGE_ID_SWITCH, value); }

    /// <summary>
    /// Gets or sets a prefix that is prepended to the citation.
    /// </summary>
    public String getPrefix() { return getFieldCodeCache().getSwitchArgumentAsString(PREFIX_SWITCH); }
    public void setPrefix(String value) throws Exception { getFieldCodeCache().setSwitch(PREFIX_SWITCH, value); }

    /// <summary>
    /// Gets or sets a suffix that is appended to the citation.
    /// </summary>
    public String getSuffix() { return getFieldCodeCache().getSwitchArgumentAsString(SUFFIX_SWITCH); }
    public void setSuffix(String value) throws Exception { getFieldCodeCache().setSwitch(SUFFIX_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether the author information is suppressed from the citation.
    /// </summary>
    public boolean getSuppressAuthor() { return getFieldCodeCache().hasSwitch(SUPPRESS_AUTHOR_SWITCH); }
    public void setSuppressAuthor(boolean value) throws Exception { getFieldCodeCache().setSwitch(SUPPRESS_AUTHOR_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether the title information is suppressed from the citation.
    /// </summary>
    public boolean getSuppressTitle() { return getFieldCodeCache().hasSwitch(SUPPRESS_TITLE_SWITCH); }
    public void setSuppressTitle(boolean value) throws Exception { getFieldCodeCache().setSwitch(SUPPRESS_TITLE_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether the year information is suppressed from the citation.
    /// </summary>
    public boolean getSuppressYear() { return getFieldCodeCache().hasSwitch(SUPPRESS_YEAR_SWITCH); }
    public void setSuppressYear(boolean value) throws Exception { getFieldCodeCache().setSwitch(SUPPRESS_YEAR_SWITCH, value); }

    /// <summary>
    /// Gets or sets a page number associated with the citation.
    /// </summary>
    public String getPageNumber() { return getFieldCodeCache().getSwitchArgumentAsString(PAGE_NUMBER_SWITCH); }
    public void setPageNumber(String value) throws Exception { getFieldCodeCache().setSwitch(PAGE_NUMBER_SWITCH, value); }

    /// <summary>
    /// Gets or sets a volume number associated with the citation.
    /// </summary>
    public String getVolumeNumber() { return getFieldCodeCache().getSwitchArgumentAsString(VOLUME_NUMBER_SWITCH); }
    public void setVolumeNumber(String value) throws Exception { getFieldCodeCache().setSwitch(VOLUME_NUMBER_SWITCH, value); }

    /// <summary>
    /// Gets or sets a value that matches the <b>Tag</b> element's value of another source to be included in the citation.
    /// </summary>
    public String getAnotherSourceTag() { return getFieldCodeCache().getSwitchArgumentAsString(ANOTHER_SOURCE_TAG_SWITCH); }
    public void setAnotherSourceTag(String value) throws Exception { getFieldCodeCache().setSwitch(ANOTHER_SOURCE_TAG_SWITCH, value); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        return getSwitchTypeInternal(switchName);
    }

    private static /*FieldSwitchType*/int getSwitchTypeInternal(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*SUPPRESS_AUTHOR_SWITCH*/1:
            case /*SUPPRESS_TITLE_SWITCH*/2:
            case /*SUPPRESS_YEAR_SWITCH*/3:
                return FieldSwitchType.FLAG;
            case /*FORMAT_LANGUAGE_ID_SWITCH*/8:
            case /*PREFIX_SWITCH*/4:
            case /*SUFFIX_SWITCH*/5:
            case /*PAGE_NUMBER_SWITCH*/7:
            case /*VOLUME_NUMBER_SWITCH*/6:
            case /*ANOTHER_SOURCE_TAG_SWITCH*/0:
                return FieldSwitchType.HAS_ARGUMENT;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    private static final int SOURCE_TAG_ARGUMENT_INDEX = 0;

    private static final String FORMAT_LANGUAGE_ID_SWITCH = "\\l";
    private static final String PREFIX_SWITCH = "\\f";
    private static final String SUFFIX_SWITCH = "\\s";
    private static final String PAGE_NUMBER_SWITCH = "\\p";
    private static final String VOLUME_NUMBER_SWITCH = "\\v";
    private static final String SUPPRESS_AUTHOR_SWITCH = "\\n";
    private static final String SUPPRESS_TITLE_SWITCH = "\\t";
    private static final String SUPPRESS_YEAR_SWITCH = "\\y";
    private static final String ANOTHER_SOURCE_TAG_SWITCH = "\\m";
    private static final String INVALID_SOURCES_ERROR_MESSAGE = "Invalid source specified.";
    private static final String MISSED_SOURCES_ERROR_MESSAGE = "No source specified.";

    private static final int MAX_EFFECTIVE_SWITCHES_COUNT = 10;

    private static class Citation
    {
        Citation(Source source, boolean repeatedAuthor, boolean nonUniqueLastName)
        {
            mSource = source;
            mRepeatedAuthor = repeatedAuthor;
            mNonUniqueLastName = nonUniqueLastName;
        }

        Source getSource() { return mSource; };

        private Source mSource;
        boolean getRepeatedAuthor() { return mRepeatedAuthor; };

        private boolean mRepeatedAuthor;
        boolean getNonUniqueLastName() { return mNonUniqueLastName; };

        private boolean mNonUniqueLastName;
        boolean getSuppressAuthor() { return mSuppressAuthor; }; void setSuppressAuthor(boolean value) { mSuppressAuthor = value; };

        private boolean mSuppressAuthor;
        boolean getSuppressTitle() { return mSuppressTitle; }; void setSuppressTitle(boolean value) { mSuppressTitle = value; };

        private boolean mSuppressTitle;
        boolean getSuppressYear() { return mSuppressYear; }; void setSuppressYear(boolean value) { mSuppressYear = value; };

        private boolean mSuppressYear;
        String getPrefix() { return mPrefix; }; void setPrefix(String value) { mPrefix = value; };

        private String mPrefix;
        String getSuffix() { return mSuffix; }; void setSuffix(String value) { mSuffix = value; };

        private String mSuffix;
        String getVolumeNumber() { return mVolumeNumber; }; void setVolumeNumber(String value) { mVolumeNumber = value; };

        private String mVolumeNumber;
        String getPageNumber() { return mPageNumber; }; void setPageNumber(String value) { mPageNumber = value; };

        private String mPageNumber;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\m",
		"\\n",
		"\\t",
		"\\y",
		"\\f",
		"\\s",
		"\\v",
		"\\p",
		"\\l"
	);

}

