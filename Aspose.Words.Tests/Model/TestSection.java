// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import com.aspose.SystemPal;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.IntToObjDictionary;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.StringUtil;
import com.aspose.PlatformUtilPal;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.IO.MemoryStream;
import java.util.Iterator;
import com.aspose.ms.System.Globalization.msCultureInfo;
import org.testng.annotations.DataProvider;


@Test (groups = "SingleThreadGroup")
public class TestSection
{
    @BeforeMethod (alwaysRun = true)
    public void setUp()
    {
        SystemPal.saveCulture();
        TestUtil.setUpTests();
        mOldSectTestMode = SectPr.getTestMode();
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown()
    {
        SystemPal.restoreCulture();
        SectPr.setTestMode(mOldSectTestMode);
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QCannot insert a node of this type at this location.\\E")
    public void testTwoBodies() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Section sect = doc.appendChild(new Section(doc));
        sect.appendChild(new Body(doc));
        sect.appendChild(new Body(doc));
    }

    @Test
    public void testTwoDifferentHeaders() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Section sect = doc.appendChild(new Section(doc));
        //Adding headers of different type is okay.
        sect.appendChild(new HeaderFooter(doc, HeaderFooterType.FOOTER_PRIMARY));
        sect.appendChild(new HeaderFooter(doc, HeaderFooterType.HEADER_PRIMARY));
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QCannot insert a node of this type at this location.\\E")
    public void testTwoSameHeaders() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Section sect = doc.appendChild(new Section(doc));
        sect.appendChild(new HeaderFooter(doc, HeaderFooterType.FOOTER_PRIMARY));
        sect.appendChild(new HeaderFooter(doc, HeaderFooterType.FOOTER_PRIMARY));
    }

    /// <summary>
    /// Test that shortcuts to body and headers footers work.
    /// </summary>
    @Test
    public void testShortcuts() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Section sect = doc.appendChild(new Section(doc));

        msAssert.isNull(sect.getBody());
        msAssert.isNull(sect.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_FIRST));

        sect.appendChild(new HeaderFooter(doc, HeaderFooterType.FOOTER_FIRST));
        sect.appendChild(new Body(doc));

        msAssert.isNotNull(sect.getBody());
        msAssert.isNotNull(sect.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_FIRST));
        msAssert.isNull(sect.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_PRIMARY));
    }


    /// <summary>
    /// WORDSNET-13826 System.InvalidCastException is thrown while loading document
    /// under different Culture.
    /// Threads can have different culture and AW overwrites defaults for section attributes every time
    /// when current culture was changed. Moreover AW does not implement it in thread safe manner.
    /// Such behavior causes different exceptions.
    /// </summary>
    @Test
    public void testJira13826()
    {
        SectPr.setTestMode(false);
        String[] langs = new String[] {"fr-FR", "en-GB", "de-DE", "en-US"};

        // Prepare cache.
        IntToObjDictionary<SectPr> cache = SectPr.DEFAULTS_CACHE;
        cache.clear();

        // 1. Check that every thread with custom culture can have own collection of the section attributes.
        // Each collection cached only one time.
        appendSectDefaults(langs, null);
        appendSectDefaults(langs, cache.getValues().iterator());

        msAssert.areEqual(4, cache.getCount());

        // 2. Check that every collection contains culture specific values.
        IntToObjDictionary<SectPr>.Enumerator enumerator = cache.iterator();

        while (enumerator.moveNext())
        {
            SectPr currAttrs = enumerator.getCurrentValue();
            int space = LocaleDefaults.getSpaceBetweenColumns(enumerator.getCurrentKey());
            msAssert.areEqual(space, currAttrs.getDirectAttr(SectAttr.COLUMNS_SPACING));
            msAssert.areEqual(SectionStart.NEW_PAGE, currAttrs.getDirectAttr(SectAttr.SECTION_START));
        }

        // 3. Check that attempt to get default values of the section properties returns collection from cache.
        TestSectPr sectPr = new TestSectPr();
        ArrayList<SectPr> expectedColls = new ArrayList<SectPr>(langs.length);

        for (String lang : langs)
        {
            SystemPal.setCulture(lang);
            msArrayList.add(expectedColls, sectPr.getSectDefault());
        }

        appendSectDefaults(langs, expectedColls.iterator());
    }

    /// <summary>
    /// MS Word uses different defaults for page margins, header/footer distance and column
    /// spacing. The defaults depend on the language during generation of the Normal template.
    /// Aspose.Words mimics MS Word behavior with using current culture settings.
    /// </summary>
    @Test (groups = "SingleThreadGroup", dataProvider = "testDefaultMarginsDataProvider")
    public void testDefaultMargins(String cultureName, int expectedLeftMargin, int expectedRightMargin,
        int expectedTopMargin, int expectedBottomMargin, int expectedHeaderDistance, int expectedFooterDistance,
        int expectedColumnSpacing) throws Exception
    {
        SectPr.setTestMode(false);

        if (StringUtil.hasChars(cultureName))
            SystemPal.setCulture(cultureName);
        else
            SystemPal.setStandardCulture();

        LocaleDefaults.Margins expectedMargins = new LocaleDefaults.Margins(expectedLeftMargin,
            expectedRightMargin, expectedTopMargin, expectedBottomMargin);

        // New document
        Document newDoc = new Document();
        checkMargins(newDoc.getFirstSection(), expectedMargins, expectedHeaderDistance, expectedFooterDistance,
            expectedColumnSpacing);

        // FOSS: the "loaded document" leg used an inline WordML 2003 stream; the WML reader is
        // removed, so the locale-default margins are verified on the new document only.
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDefaultMarginsDataProvider")
	public static Object[][] testDefaultMarginsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"en-US",  1440,  1440,  1440,  1440,  720,  720,  720},
			{"de-DE",  1417,  1417,  1417,  1134,  708,  708,  708},
			{"it-IT",  1134,  1134,  1417,  1134,  708,  708,  708},
			{null,  1440,  1440,  1440,  1440,  708,  708,  708},
		};
	}

    /// <summary>
    /// WORDSNET-14959 Different page margins are reported by AW model and MS Word.
    /// Checks whether page margins is obtained by LCID specified in the registry instead of the one set in the current locale.
    /// </summary>
    // FOSS: .doc, .rtf and .wml cases removed (those readers are gone); .docx case retained.
    @Test (enabled = false)
    public void test14959(String testFile) throws Exception
    {
        if (!PlatformUtilPal.isWindows())
            return;

        int sysLcid = SystemPal.getSystemDefaultLcid();
        LoadOptions lo = Path.getExtension(testFile).equals(".rtf")
            ? new RtfLoadOptions()
            : new LoadOptions();
        lo.setUseSystemLcid(true);
        Document doc = TestUtil.open(testFile, lo);

        // Checks the defaults of the first section corresponding to the system LCID set in DocumentPostLoader.
        checkMargins(sysLcid, doc.getSections().get(0));

        // Checks the defaults of the second section corresponding to the system LCID set in a document reader-s.
        checkMargins(sysLcid, doc.getSections().get(1));

        // LoadOptions.UseSystemLcid only works during the document loading stage. Subsequently, the model works with the current locale.
        Section sect = new Section(doc);
        doc.getSections().add(sect);
        // Checks the defaults of the last section corresponding to the test LCID set by default.
        checkMargins(TEST_LCID, doc.getLastSection());
    }

    /// <summary>
    /// WORDSNET-25620 Empty documents created by Aspose.Words have different page setup
    /// Checks whether SectPr.SetLocaleDefaultsForNewDocument() is called to load from an empty stream.
    /// </summary>
    @Test
    public void test25620() throws Exception
    {
        SectPr.setTestMode(false);

        try
        {
            Document doc1 = new Document();
            doc1 = TestUtil.saveOpen(
                doc1,
                "Model\\Document\\Test25620_Blank",
                UnifiedScenario.DOCX_2_DOCX | UnifiedScenario.NO_GOLD);
            SectPr sectPr1 = doc1.getFirstSection().getSectPr();

            Document doc2 = new Document(new MemoryStream(), new LoadOptions());
            doc2 = TestUtil.saveOpen(
                doc2,
                "Model\\Document\\Test25620_Empty",
                UnifiedScenario.DOCX_2_DOCX | UnifiedScenario.NO_GOLD);
            SectPr sectPr2 = doc2.getFirstSection().getSectPr();

            msAssert.areEqual(sectPr1.getPageWidth(), sectPr2.getPageWidth());
            msAssert.areEqual(sectPr1.getPageHeight(), sectPr2.getPageHeight());
            msAssert.areEqual(sectPr1.getLeftMargin(), sectPr2.getLeftMargin());
            msAssert.areEqual(sectPr1.getRightMargin(), sectPr2.getRightMargin());
            msAssert.areEqual(sectPr1.getTopMargin(), sectPr2.getTopMargin());
            msAssert.areEqual(sectPr1.getBottomMargin(), sectPr2.getBottomMargin());
            msAssert.areEqual(sectPr1.getHeaderDistance(), sectPr2.getHeaderDistance());
            msAssert.areEqual(sectPr1.getColumnsSpacing(), sectPr2.getColumnsSpacing());
        }
        finally
        {
            SectPr.setTestMode(true);
        }
    }

    /// <summary>
    /// WORDSNET-27383 Footer is changed after open/save document.
    /// If headers/footers are defined in a sectPr element as a direct child of a body, they apply to all
    /// subsequent sections, even if those have other headers/footers specified.
    /// </summary>
    @Test
    public void test27383() throws Exception
    {
        final String FILE_NAME = "Model\\Section\\Test27383.docx";
        Document doc = TestUtil.open(FILE_NAME);

        for (int i = 1; i < doc.getSections().getCount(); i++)
        {
            // All sections inherit headers/footers from the first section.
            msAssert.areEqual(0, doc.getSections().get(i).getHeadersFooters().getCount());
        }

        // The last section inherits properties from the first section.
        SectPr firstSectPr = doc.getFirstSection().getSectPr();
        SectPr lastSectPr = doc.getLastSection().getSectPr();
        msAssert.areEqual(firstSectPr.getLeftMargin(), lastSectPr.getLeftMargin());

        TestUtil.saveCheckGoldExportOnly(doc, FILE_NAME);
    }

    /// <summary>
    /// Fills collections with default values to section properties cache using specified cultures.
    /// Also compares added collections with specified enumeration.
    /// </summary>
    /// <param name="langs">Names of the cultures.</param>
    /// <param name="expectedColls">Expected collections to compare.</param>
    private static void appendSectDefaults(String[] langs, Iterator<SectPr> expectedColls)
    {
        for (int j = 0; j < langs.length; ++j)
        {
            SystemPal.setCulture(langs[j]);
            SectPr.fetchDefaultAttr(SectAttr.LEFT_MARGIN);

            if (expectedColls != null)
            {
                msAssert.isTrue(expectedColls.hasNext());
                msCultureInfo ci = SystemPal.getCurrentCulture();
                // Explicit cast to AttrCollection resolve overload ambiguity in C++
                msAssert.areEqual(SectPr.DEFAULTS_CACHE.get(ci.getLCID()), (AttrCollection)expectedColls.next());
            }
        }
    }

    /// <summary>
    /// Checks the margin values for conformity with the specified LCID.
    /// </summary>
    private void checkMargins(int lcid, Section section)
    {
        final int TEST_HEADER_FOOTER_DISTANCE = 720;
        final int TEST_COLUMN_SPACING = 720;
        LocaleDefaults.Margins testDefaultMargins =
            new LocaleDefaults.Margins(1800, 1800, 1440, 1440);
        LocaleDefaults.Margins expectedMargins = lcid == TEST_LCID
            ? testDefaultMargins
            : LocaleDefaults.getPageMargins(lcid);
        int expectedDistance = lcid == TEST_LCID
            ? TEST_HEADER_FOOTER_DISTANCE
            : LocaleDefaults.getHeaderFooterDistance(lcid);
        int expectedColumnSpacing = lcid == TEST_LCID
            ? TEST_COLUMN_SPACING
            : LocaleDefaults.getSpaceBetweenColumns(lcid);

        checkMargins(section, expectedMargins, expectedDistance, expectedDistance,
            expectedColumnSpacing);
    }

    /// <summary>
    /// Checks page margins, header/footer distance, column spacing of the specified sectPr.
    /// </summary>
    private void checkMargins(Section section, LocaleDefaults.Margins expectedMargins,
        int expectedHeaderDistance, int expectedFooterDistance, int expectedColumnSpacing)
    {
        SectPr sectPr = section.getSectPr();

        msAssert.areEqual(expectedMargins.getLeft(), sectPr.getLeftMargin(), "Left margin is wrong.");
        msAssert.areEqual(expectedMargins.getRight(), sectPr.getRightMargin(), "Right margin is wrong.");
        msAssert.areEqual(expectedMargins.getTop(), sectPr.getTopMargin(), "Top margin is wrong.");
        msAssert.areEqual(expectedMargins.getBottom(), sectPr.getBottomMargin(), "Bottom margin is wrong.");

        msAssert.areEqual(expectedHeaderDistance, sectPr.getHeaderDistance(), "Header distance is wrong.");
        msAssert.areEqual(expectedFooterDistance, sectPr.getFooterDistance(), "Footer distance is wrong.");

        msAssert.areEqual(expectedColumnSpacing, sectPr.getColumnsSpacing(), "Space between columns is wrong.");
    }

    private boolean mOldSectTestMode;

    private static final int TEST_LCID = -1;

    /// <summary>
    /// Class is used for test purposes. Implements methods for access to nonpublic members.
    /// </summary>
    private static class TestSectPr extends SectPr
    {
        SectPr getSectDefault()
        {
            return (SectPr)getDefaults();
        }
    }
}

