// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2023 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import java.util.HashMap;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.msString;
import com.aspose.XslTransform;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.Map;
import com.aspose.words.ref.Ref;


/// <summary>
/// Represents the list of bibliography sources available in the document.
/// </summary>
public final class Bibliography
{
    Bibliography(Document document) throws Exception
    {
        mSources = new ArrayList<Source>();
        mDocument = document;
        mCustomXmlPart = BibliographyReader.read(this, document);

        mVersions = BibliographyVersion.calculate(this);
    }

    private Bibliography(Document document, CustomXmlPart customXmlPart, int version)
    {
        mSources = new ArrayList<Source>();
        mDocument = document;
        mCustomXmlPart = customXmlPart;
        mVersions = version;
    }

    /// <summary>
    /// Transforms bibliography or citation xml with specified in <see cref="BibliographyStyle"/> active style.
    /// </summary>
    Stream transform(Stream xml) throws Exception
    {
        if (!ensureTransform())
            throw new IllegalStateException();

        return transformSafe(xml);
    }

    /// <summary>
    /// Ensures XSL transformation.
    /// </summary>
    boolean ensureTransform() throws Exception
    {
        if (mIsTransformInitialized)
            return mTransform != null;

        mIsTransformInitialized = true;

        if (msString.isNullOrEmpty(getBibliographyStyle()))
            return false;

        Stream style = loadStyle(getBibliographyStyle(), mDocument);
        try /*JAVA: was using*/
        {
            if (style == null)
                return false;

            mTransform = new XslTransform();
            mTransform.load(style);
        }
        finally { if (style != null) style.close(); }

        return true;
    }

    private Stream transformSafe(Stream xml) throws Exception
    {
        try
        {
            return mTransform.transform(xml);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    private static Stream loadStyle(String style, Document document) throws Exception
    {
        return (loadUserProvidedStyle(style, document) != null ? loadUserProvidedStyle(style, document) : loadBuiltInStyle(style));
    }

    private static Stream loadUserProvidedStyle(String style, Document document) throws Exception
    {
        IBibliographyStylesProvider provider = document.getFieldOptions().getBibliographyStylesProvider();
        return provider != null
            ? Stream.fromJavaInputStream(provider.getStyle(style))
            : null;
    }

    private static BibliographyStyleInfo getBuiltInStyleInfo(String style)
    {
        String invariantStyle = msString.toLowerInvariant(msString.trimStart(style, '\\'));
        BibliographyStyleInfo result = null;
        Ref<BibliographyStyleInfo> referenceToResult = new Ref<BibliographyStyleInfo>(result);
        msDictionary.tryGetValue(G_BUILT_IN_STYLES, invariantStyle, /*out*/ referenceToResult);
        result = referenceToResult.get();
        return result;
    }

    private static Stream loadBuiltInStyle(String style)
    {
        String invariantStyle = msString.toLowerInvariant(msString.trimStart(style, '\\'));
        return G_BUILT_IN_STYLES.containsKey(invariantStyle)
            ? ResourceUtil.fetchResourceStream("Aspose.Words.Resources.Bibliography." + invariantStyle)
            : null;
    }

    /// <summary>
    /// Gets or sets a string that represents the name of the active style to use for a bibliography.
    /// </summary>
    public String getBibliographyStyle() { return mBibliographyStyle; }
    public void setBibliographyStyle(String value)
    {
        if (msString.equals(mBibliographyStyle, value))
            return;

        mBibliographyStyle = value;

        BibliographyStyleInfo bibliographyStyleInfo = mBibliographyStyle != null
            ? getBuiltInStyleInfo(mBibliographyStyle)
            : null;

        if (bibliographyStyleInfo != null)
        {
            setStyleName(bibliographyStyleInfo.getName());
            setVersion(bibliographyStyleInfo.getVersion());
        }
        else
        {
            setStyleName(null);
            setVersion(null);
        }
    }

    String getStyleName() { return mStyleName; }; void setStyleName(String value) { mStyleName = value; };

    private String mStyleName;

    String getVersion() { return mVersion; }; void setVersion(String value) { mVersion = value; };

    private String mVersion;

    /// <summary>
    /// Gets a collection that represents all the sources contained in a bibliography.
    /// </summary>
    public ArrayList<Source> getSources() { return mSources; };

    private ArrayList<Source> mSources;

    void save() throws Exception
    {
        int version = BibliographyVersion.calculate(this);
        if (mVersions == version)
            return;

        mCustomXmlPart = BibliographyWriter.write(this, mDocument, mCustomXmlPart);
        mVersions = version;
    }

    Bibliography deepClone(Document document)
    {
        CustomXmlPart customXmlPart = mCustomXmlPart != null
            ? document.getCustomXmlParts().getById(mCustomXmlPart.getId())
            : null;

        Bibliography lhs = new Bibliography(document, customXmlPart, mVersions);

        lhs.setBibliographyStyle(getBibliographyStyle());
        lhs.setStyleName(getStyleName());
        lhs.setVersion(getVersion());

        for (Source source : getSources())
            lhs.getSources().add(source.deepClone());

        return lhs;
    }

    private XslTransform mTransform;
    private boolean mIsTransformInitialized;
    private CustomXmlPart mCustomXmlPart;
    private String mBibliographyStyle;
    private int mVersions;

    private /*final*/ Document mDocument;

    private static /*final*/ Map<String, Bibliography.BibliographyStyleInfo> G_BUILT_IN_STYLES;

    static final String XML_NAME_SPACE = "http://schemas.openxmlformats.org/officeDocument/2006/bibliography";

    /// <summary>
    /// Provides static information about bibliography style.
    /// </summary>
    private static class BibliographyStyleInfo
    {
        BibliographyStyleInfo(String name, String version)
        {
            mName = name;
            mVersion = version;
        }

        String getName() { return mName; };

        private String mName;
        String getVersion() { return mVersion; };

        private String mVersion;
    }

    static/* Bibliography()*/
    {
        G_BUILT_IN_STYLES = new HashMap<String, Bibliography.BibliographyStyleInfo>();
        {
            G_BUILT_IN_STYLES.put( "ieee2006officeonline.xsl", new BibliographyStyleInfo("IEEE", "2006"));
        }
    }
}

