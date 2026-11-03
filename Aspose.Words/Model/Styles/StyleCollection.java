// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.IO.Stream;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Map;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Collections;
import com.aspose.ArgumentUtil;
import com.aspose.Debug;
import com.aspose.SortedIntegerListGeneric;
import com.aspose.StringUtil;
import com.aspose.StringListGeneric;
import com.aspose.DictionaryExtension;
import java.util.HashMap;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.StringComparison;
import com.aspose.IntToIntDictionary;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ArrayUtil;
import java.util.Comparator;


/// <summary>
/// A collection of <see cref="Style"/> objects that represent both the built-in and user-defined styles in a document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-styles-and-themes/">Working with Styles and Themes</a> documentation article.</para>
/// </summary>
/// <dev>
/// At the moment the styles are identified by istd (style index) that never changes (it only changes
/// when importing a style from another document). Will it be better if I used a string name as an
/// identifier? Note however, it should not change once it has been assigned, otherwise will need to
/// update all references to the style in the model.
/// </dev>
public class StyleCollection implements Iterable<Style>, Cloneable
{
    StyleCollection(DocumentBase doc)
    {
        mDoc = doc;

    }

    /// <summary>
    /// Removes all styles from the Quick Style Gallery panel.
    /// </summary>
    public void clearQuickStyleGallery()
    {
        for (Style style : this)
            if (style.isQuickStyle())
                style.isQuickStyle(false);

        for (int i = 0; i < getLatentStyles().getCount(); i++)
            if (getLatentStyles().get(i).getQuickStyle())
                getLatentStyles().get(i).setQuickStyle(false);
    }

    /// <summary>
    /// Gets the owner document.
    /// </summary>
    public DocumentBase getDocument() { return mDoc; }

    /// <summary>
    /// Gets document default text formatting.
    /// </summary>
    /// <remarks>
    /// <p>Note that document-wide defaults were introduced in Microsoft Word 2007 and are fully supported in OOXML formats (<see cref="LoadFormat.Docx" />) only.
    /// Earlier document formats have limited support for this feature and only font names can be stored.</p>
    /// </remarks>
    public Font getDefaultFont()
    {
        if (mDefaultFont == null)
            mDefaultFont = new Font(getDefaultRunPr(), mDoc);

        return mDefaultFont;
    }

    /// <summary>
    /// Gets document default paragraph formatting.
    /// </summary>
    /// <remarks>
    /// <p>Note that document-wide defaults were introduced in Microsoft Word 2007 and are fully supported in OOXML formats (<see cref="LoadFormat.Docx" />) only.
    /// Earlier document formats have no support for document default paragraph formatting.</p>
    /// </remarks>
    /// <dev>
    /// Default paragraph formatting is copied to all top level styles when it is not supported in target document format.
    /// </dev>
    public ParagraphFormat getDefaultParagraphFormat()
    {
        if (mDefaultParagraphFormat == null)
            mDefaultParagraphFormat = new ParagraphFormat(getDefaultParaPr(), this);

        return mDefaultParagraphFormat;
    }

    /// <summary>
    /// Gets the number of styles in the collection.
    /// </summary>
    public int getCount() { return mStylesByIstd.getCount(); }

    /// <overloads>Retrieves a style from the collection.</overloads>
    /// <summary>
    /// Gets a style by name or alias.
    /// </summary>
    /// <remarks>
    /// <p>Case sensitive, returns <c>null</c> if the style with the given name is not found.</p>
    /// <p>If this is an English name of a built in style that does not yet exist, automatically creates it.</p>
    /// </remarks>
    public Style get(String name) { return getByName(name, true); }

    /// <summary>
    /// Gets a built-in style by its locale independent identifier.
    /// </summary>
    /// <remarks>
    /// <p>When accessing a style that does not yet exist, automatically creates it.</p>
    /// </remarks>
    /// <param name="sti">A <see cref="StyleIdentifier"/> value that specifies the built in style to retrieve.</param>
    public Style getByStyleIdentifier(/*StyleIdentifier*/int sti) { return getBySti(sti, true); }

    /// <summary>
    /// Gets a style by index.
    /// </summary>
    /// <dev>
    /// Gets by index, not by istd.
    /// </dev>
    public Style get(int index) { return mStylesByIstd.getByIndex(index); }


    /// <summary>
    /// Validates this style collection.
    /// </summary>
    void validate()
    {
        fixBalloonStyles();

        // TableNormal MUST be a TableStyle.
        // AM. Seems we need something we do in EnsureMinimum method but many golds have to be re-accepted then.
        // Postpone for a while.
        Style tableNormal = getByName("Table Normal", false);
        if ((tableNormal != null) && (tableNormal.getType() != StyleType.TABLE))
            removeCore(tableNormal);
    }

    /// <summary>
    /// This is the max istd currently in use.
    /// </summary>
    int getMaxIstd() { return (mStylesByIstd.getCount() > 0) ? mStylesByIstd.getKey(mStylesByIstd.getCount() - 1) : -1; }

    /// <summary>
    /// Document default run properties.
    /// </summary>
    RunPr getDefaultRunPr() { return mDefaultRunPr; }
    void setDefaultRunPr(RunPr value) { mDefaultRunPr = value; }

    /// <summary>
    /// Document default paragraph properties.
    /// </summary>
    ParaPr getDefaultParaPr() { return mDefaultParaPr; }

    /// <summary>
    /// Checks that document-wide defaults contain other than standard attributes and
    /// should be expanded.
    /// </summary>
    boolean isExpandDocumentDefaultsNeeded()
    {
        // Expanding is needed for this format in general, now determine if we really
        // need to expand for this document.
        // Expand if we have any default paragraph formatting.
        if (getDefaultParaPr().getCount() > 0)
            return true;

        // Expand if we have default run formatting other than "standard" attributes.
        for (int i = 0; i < getDefaultRunPr().getCount(); i++)
        {
            if (!isIgnorableDefaultRunAttr(getDefaultRunPr().getKey(i)))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Returns true if the attribute is one of the "standard" ones that we think should be ignored during expanding of document defaults.
    /// </summary>
    static boolean isIgnorableDefaultRunAttr(int key)
    {
        return ((key == FontAttr.NAME_ASCII) ||
                (key == FontAttr.NAME_FAR_EAST) ||
                (key == FontAttr.NAME_OTHER) ||
                (key == FontAttr.NAME_BI) ||
                (key == FontAttr.LOCALE_ID) ||
                (key == FontAttr.LOCALE_ID_FAR_EAST) ||
                (key == FontAttr.LOCALE_ID_BI));
    }

    /// <summary>
    /// Gets a singleton collection of all known MS Word 97-2003 built in styles.
    /// Used as prototypes for creating built in styles on demand.
    /// </summary>
    private static StyleCollection getBuiltInStyles2003()
    {
        // Implementing Singleton in C# http://msdn.microsoft.com/en-us/library/ms998558.aspx
        if (gAllStylesCache2003 == null)
        {
            synchronized (G_ALL_STYLES_SYNC_ROOT_2003)
            {
                if (gAllStylesCache2003 == null)
                {
                    gAllStylesCache2003 = readBuiltInStylesResource("Aspose.Words.Resources.AllStyles2003.docx");
                }
            }
        }

        return gAllStylesCache2003.getStyles();
    }

    /// <summary>
    /// Gets a singleton collection of all known MS Word 2007 built in styles.
    /// Used as prototypes for creating built in styles on demand.
    /// </summary>
    private static StyleCollection getBuiltInStyles2007()
    {
        // Implementing Singleton in C# http://msdn.microsoft.com/en-us/library/ms998558.aspx
        if (gAllStylesCache2007 == null)
        {
            synchronized (G_ALL_STYLES_SYNC_ROOT_2007)
            {
                if (gAllStylesCache2007 == null)
                {
                    gAllStylesCache2007 = readBuiltInStylesResource("Aspose.Words.Resources.AllStyles2007.docx");
                }
            }
        }

        return gAllStylesCache2007.getStyles();
    }

    /// <summary>
    /// Gets a singleton collection of all known MS Word 2013 built in styles.
    /// Used as prototypes for creating built in styles on demand.
    /// </summary>
    private static StyleCollection getBuiltInStyles2013()
    {
        // Implementing Singleton in C# http://msdn.microsoft.com/en-us/library/ms998558.aspx
        if (gAllStylesCache2013 == null)
        {
            synchronized (G_ALL_STYLES_SYNC_ROOT_2013)
            {
                if (gAllStylesCache2013 == null)
                {
                    gAllStylesCache2013 = readBuiltInStylesResource("Aspose.Words.Resources.AllStyles2013.docx");
                }
            }
        }

        return gAllStylesCache2013.getStyles();
    }

    /// <summary>
    /// Get BuildIn StyleCollection depending on MS Word version used to create this document or set upon processing.
    /// </summary>
    StyleCollection getBuiltInStyles()
    {
        Document document = (getDocument().getNodeType() == NodeType.DOCUMENT) ? (Document)getDocument() : ((GlossaryDocument)getDocument()).getMainDocument();
        /*MsWordVersionCore*/int mswVersion = document.getCompatibilityOptions().getMswVersion();
        if (mswVersion != MsWordVersionCore.UNSPECIFIED)
        {
            switch (mswVersion)
            {
                case MsWordVersionCore.WORD_1997:
                case MsWordVersionCore.WORD_2000:
                case MsWordVersionCore.WORD_2002:
                case MsWordVersionCore.WORD_2003:
                    return getBuiltInStyles2003();
                case MsWordVersionCore.WORD_2007:
                case MsWordVersionCore.WORD_2010:
                    return getBuiltInStyles2007();
                case MsWordVersionCore.WORD_2013:
                case MsWordVersionCore.WORD_2016:
                case MsWordVersionCore.WORD_2019:
                    return getBuiltInStyles2013();
                default:
                    break;
            }
        }

        return getBuiltInStyles(getLoadFormat());
    }

    /// <summary>
    /// Get BuildIn StyleCollection depending on load format.
    /// </summary>
    static StyleCollection getBuiltInStyles(/*LoadFormat*/int loadFormat)
    {
        switch (loadFormat)
        {
            case LoadFormat.DOCX:
            case LoadFormat.DOCM:
            case LoadFormat.DOTX:
            case LoadFormat.DOTM:
            case LoadFormat.FLAT_OPC:
            case LoadFormat.FLAT_OPC_MACRO_ENABLED:
            case LoadFormat.FLAT_OPC_TEMPLATE:
            case LoadFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED:
                return getBuiltInStyles2007();
            case LoadFormat.DOC:
            default:
                return getBuiltInStyles2003();
        }
    }

    /// <summary>
    /// Read document with styles from resource.
    /// </summary>
    /// <param name="resourceName">Resource name AllStyles2003 or AllStyles2007 or AllStyles2013.</param>
    /// <returns>Document with styles.</returns>
    private static Document readBuiltInStylesResource(String resourceName)
    {
        try
        {
            // Load the document that contains all built-in styles instantiated.
            // Here is a pitfall: See comment in TestDefect10652.
            Stream stream = ResourceUtil.fetchResourceStream(resourceName);
            try /*JAVA: was using*/
            {
                // alexnosk WORDSNET-19906 Specify LoadFormat.Docx explicitly to skip file format detection to minimize document creation time.
                // Since we are sure AllStylesXXXX.docx is not encrypted DOCX file, it is safe to specify it explicitly.
                LoadOptions loadOptions = new LoadOptions();
                loadOptions.setLoadFormat(LoadFormat.DOCX);
                Document doc = new Document(stream, loadOptions, false);
                // This document contains some locale identifiers assigned to the styles that we don't want, remove them.
                doc.getStyles().removeLocaleIdsFromStyles();
                return doc;
            }
            finally { if (stream != null) stream.close(); }
        }
        catch (Exception e)
        {
            // JAVA: Catch and rethrow RuntimeException for java.
            // This needed to not propagate throws exception clause to attribute fetching methods.
            throw new IllegalStateException("Cannot load built in styles from an embedded resource.", e);
        }
    }

    LatentStyles getLatentStyles() { return mLatentStyles; }

    /// <summary>
    /// Return true if this collection is BuiltIn styles template resource
    /// <see cref="gAllStylesCache2003"/> or <see cref="gAllStylesCache2007"/> or <see cref="gAllStylesCache2013"/>.
    /// </summary>
    private boolean isBuiltinStylesTemplate() { return (getDocument() == gAllStylesCache2003) || (getDocument() == gAllStylesCache2007) || (getDocument() == gAllStylesCache2013); }

    /// <summary>
    /// Gets the next free istd. Always returns a value that is greater than fixed-index (reserved) istds.
    /// </summary>
    int getNextFreeIstd()
    {
        // WORDSNET-1389 Too many styles created results in an invalid document that hangs MS Word.
        if (!hasFreeIstd())
        {
            throw new IllegalStateException("There are too many styles in the document.");
        }

        return calculateNextIstd();
    }

    /// <summary>
    /// Returns "true", when there is an available istd.
    /// </summary>
    boolean hasFreeIstd()
    {
        int nextIstd = calculateNextIstd();
        return nextIstd < StyleIndex.LIMIT;
    }

    /// <summary>
    /// Calculates the next istd value.
    /// </summary>
    private int calculateNextIstd()
    {
        // Make sure we return a number that is equal or greater than the max fixed-index (reserved) istd.
        return Math.max(getMaxIstd(), MAX_FIXED_ISTD) + 1;
    }

    /// <summary>
    /// Gets an enumerator object that will enumerate styles in the alphabetical order of their names.
    /// </summary>
    public Iterator<Style> iterator()
    {
        ArrayList<Style> result = new ArrayList<Style>();

        for (Map.Entry<String, Style> entry : (Iterable<Map.Entry<String, Style>>) mStylesByName)
        {
            String name = entry.getKey();
            Style style = entry.getValue();
            if (msString.equals(style.getName(), name))
                msArrayList.add(result, style);
        }

        Collections.sort(result, new ComparerByName());
        return result.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Creates a new user defined style and adds it the collection.
    /// </summary>
    /// <remarks>
    /// <p>You can create character, paragraph or a list style.</p>
    ///
    /// <p>When creating a list style, the style is created with default numbered list formatting (1 \ a \ i).</p>
    ///
    /// <p>Throws an exception if a style with this name already exists.</p>
    /// </remarks>
    /// <param name="type">A <see cref="StyleType"/> value that specifies the type of the style to create.</param>
    /// <param name="name">Case sensitive name of the style to create.</param>
    public Style add(/*StyleType*/int type, String name)
    {
        ArgumentUtil.checkHasChars(name, "name");

        Style style = Style.create(type, getNextFreeIstd(), StyleIdentifier.USER, name);

        // This is a "constructor of a user defined list style".
        // Not sure if here is the best place for it, but no better ideas at the moment.
        if (type == StyleType.LIST)
        {
            // Create a list that defines the list style. Make the list point to the style.
            List list = ListFactory.createList(getDocument().getLists(), ListTemplate.NUMBER_DEFAULT);
            list.getListDef().setListStyleIstd(style.getIstd());
            // Make the list style to point to the list that defines it.
            style.getParaPr().setListId(list.getListId());
        }

        add(style);
        return style;
    }

    /// <summary>
    /// Removes style from styles collection. Does not remove built-in styles.
    /// </summary>
    void remove(String styleName)
    {
        Style removedStyle = getByName(styleName, false);

        if (removedStyle == null)
            throw new IllegalArgumentException("Style not found.");

        Style linkedStyle = removedStyle.getLinkedStyle();
        int linkedStyleLinksCount = 0;

        // We should expand all child of this style, before removing it.
        for (Style style : this)
        {
            if (style.getBasedOnIstd() == removedStyle.getIstd())
            {
                expandStyle(style, removedStyle);

                style.setBasedOnIstd(getDefaultStyleIndex(style.getType()));

                // NextIstd has meaning only for paragraph styles.
                if ((style.getType() == StyleType.PARAGRAPH) && (style.getNextIstd() == removedStyle.getIstd()))
                    style.setNextIstd(style.getIstd());
            }
            else
            {
                if ((style.getType() == StyleType.PARAGRAPH) && (style.getNextIstd() == removedStyle.getIstd()))
                    style.setNextIstd(StyleIndex.NORMAL);
            }

            // WORDSNET-28631 Collect a number of links to the linked style.
            if ((linkedStyle != null) && (style.getLinkedIstd() == linkedStyle.getIstd()))
                linkedStyleLinksCount++;
        }

        // When removing style Word doesn't expand formatting to nodes that style being removed is applied and
        // just removes Istd, so node looses any formatting applied by style.
        updateDomWithIstd(removedStyle, removedStyle.getIstd(), -1);

        if ((removedStyle.hasRevisions()) && (removedStyle.getDocument() instanceof Document)) // remove style with revisions from RevisionCollection.
            ((Document)removedStyle.getDocument()).getRevisions().remove(removedStyle);

        removeCore(removedStyle);

        // WORDSNET-28631 Remove linked style only if there is a single
        // reference to this style is remained in the style collection.
        if ((linkedStyle != null) && (linkedStyleLinksCount == 1))
        {
            // Avoid recursion.
            linkedStyle.setLinkedIstd(StyleIndex.NIL);

            remove(linkedStyle.getName());
        }
    }

    /// <summary>
    /// Removes styles from collection. Does not update any references so document can become invalid.
    /// </summary>
    void removeCore(Style style)
    {
        if (getBuiltInStyles().getByName(style.getName(), false) != null)
            mBuiltInStylesBySti.remove((int)style.getStyleIdentifier());

        mStylesByIstd.remove(style.getIstd());
        removeFromByNameList(style);

        mStyleAliasesCache = null;
    }

    /// <summary>
    /// Copies all styles from the specified template collection into this collection.
    /// </summary>
    void copyStylesFromTemplate(StyleCollection template)
    {
        // Copy document defaults.
        mDefaultRunPr = AttrCollectionExtension.deepClone(template.getDefaultRunPr());
        mDefaultParaPr = AttrCollectionExtension.deepClone(template.getDefaultParaPr());

        copyAll(template, new ImportContext(template, this));
    }

    /// <summary>
    /// Removes all items, which reference to the style, from the mStylesByName list.
    /// </summary>
    private void removeFromByNameList(Style style)
    {
        // mStylesByName may contain aliases of the style
        for (int i = mStylesByName.getCount() - 1; i >= 0; i--)
            if (mStylesByName.getByIndex(i) == style)
                mStylesByName.removeAt(i);
    }

    /// <summary>
    /// Returns default StyleIndex for given styleType.
    /// </summary>
    private static int getDefaultStyleIndex(/*StyleType*/int styleType)
    {
        switch (styleType)
        {
            case StyleType.CHARACTER:
                return StyleIndex.DEFAULT_PARAGRAPH_FONT;
            case StyleType.PARAGRAPH:
                return StyleIndex.NORMAL;
            case StyleType.TABLE:
                return StyleIndex.TABLE_NORMAL;
            default:
                return StyleIndex.NIL;
        }
    }

    private void processTableStyle(int istd, int newIstd)
    {
        for (Row row : (Iterable<Row>) getDocument().getChildNodes(NodeType.ROW, true))
        {
            TablePr tablePr = row.getTablePr();
            if (tablePr.getIstd() == istd)
            {
                if (newIstd == -1)
                    tablePr.remove(TableAttr.ISTD);
                else
                    tablePr.set(TableAttr.ISTD, newIstd);
            }
        }
    }

    private void processListStyle(Style style, int istd, int newIstd)
    {
        for (Paragraph para : (Iterable<Paragraph>) getDocument().getChildNodes(NodeType.PARAGRAPH, true))
        {
            ParaPr paraPr = para.getParaPr();
            if ((paraPr.getListId() != 0) && (para.getListFormat().getList().getStyle() != null) && (para.getListFormat().getList().getStyle().getIstd() == istd))
            {
                if (newIstd == -1)
                {
                    paraPr.remove(ParaAttr.LIST_ID);
                    paraPr.remove(ParaAttr.LIST_LEVEL);
                }
                else
                {
                    paraPr.set(ParaAttr.LIST_ID, style.getList().getListId());
                }
            }
        }
    }

    private void processParaStyle(int istd, int newIstd)
    {
        for (Paragraph para : (Iterable<Paragraph>) getDocument().getChildNodes(NodeType.PARAGRAPH, true))
        {
            ParaPr paraPr = para.getParaPr();
            if (paraPr.getIstd() == istd)
            {
                if (newIstd == -1)
                    paraPr.remove(ParaAttr.ISTD);
                else
                    paraPr.set(ParaAttr.ISTD, newIstd);
            }
        }
    }

    private void processCharStyle(int istd, int newIstd)
    {
        for (Paragraph para : (Iterable<Paragraph>) getDocument().getChildNodes(NodeType.PARAGRAPH, true))
        {
            updateRunPr(para.getParagraphBreakRunPr(), istd, newIstd);

            for (Run run : (Iterable<Run>) para.getRuns())
                updateRunPr(run.getRunPr(), istd, newIstd);
        }
    }

    private static void updateRunPr(RunPr runPr, int istd, int newIstd)
    {
        if (runPr.getIstd() == istd)
        {
            if (newIstd == -1)
                runPr.remove(FontAttr.ISTD);
            else
                runPr.set(FontAttr.ISTD, newIstd);
        }
    }

    /// <summary>
    /// Expand child style.
    /// </summary>
    private static void expandStyle(Style style, Style removedStyle)
    {
        switch (removedStyle.getType())
        {
            case StyleType.CHARACTER:
                style.setRunPr(style.getExpandedRunPr(RunPrExpandFlags.NORMAL));
                break;

            case StyleType.PARAGRAPH:
                style.setRunPr(style.getExpandedRunPr(RunPrExpandFlags.NORMAL));
                style.setParaPr(style.getExpandedParaPr(ParaPrExpandFlags.DOCUMENT_DEFAULTS));
                break;

            case StyleType.TABLE:
            {
                TableStyle tableStyle = (TableStyle)style;
                tableStyle.setRowPr(tableStyle.getExpandedRowPr());
                tableStyle.setCellPr(tableStyle.getExpandedCellPr());
                tableStyle.setTablePr(tableStyle.getExpandedTablePr());
                style.setRunPr(style.getExpandedRunPr(RunPrExpandFlags.NORMAL));
                style.setParaPr(style.getExpandedParaPr(ParaPrExpandFlags.DOCUMENT_DEFAULTS));
                break;
            }
            case StyleType.LIST:
                // Do nothing.
                break;

            default:
                throw new IllegalArgumentException("Unexpected style type.");
        }
    }

    /// <summary>
    /// Adds the style to the collection of styles.
    /// </summary>
    void add(Style style)
    {
        if (style == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "style");
        if (style.getStyles() != null)
            throw new IllegalArgumentException("Style already belongs to another collection of styles.");

        if (mStylesByName.containsKey(style.getName()))
            throw new IllegalArgumentException("Cannot add a style because a style with the same name already exists.");

        if (style.getBuiltIn() && mBuiltInStylesBySti.containsKey((int)style.getStyleIdentifier()))
            throw new IllegalArgumentException("Cannot add a style because a style with this identifier already exists.");

        mStylesByIstd.add(style.getIstd(), style);
        mStylesByName.add(style.getName(), style);
        if (style.getBuiltIn())
            mBuiltInStylesBySti.add((int)style.getStyleIdentifier(), style);

        style.setStyles(this);

        mStyleAliasesCache = null;
    }

    /// <summary>
    /// Update collection cache with new style name.
    /// </summary>
    void updateNameMap(Style newStyle, String oldName, String newName)
    {
        mStylesByName.remove(oldName);

        Style oldStyle = (Style)mStylesByName.get(newName);
        mStylesByName.set(newName, newStyle);
        // WORDSNET-14289 If a replaced style is not an alias, let's also remove all aliases of it.
        if ((oldStyle != null) && (oldStyle != newStyle) && (msString.equals(oldStyle.getName(), newName)))
            removeFromByNameList(oldStyle);

        mStyleAliasesCache = null;
    }

    void updateStiMap(Style style, /*StyleIdentifier*/int oldSti, /*StyleIdentifier*/int newSti)
    {
        Debug.assertTrue(oldSti != newSti);
        if (style.getBuiltIn())
            mBuiltInStylesBySti.remove((int)oldSti);

        if (newSti != StyleIdentifier.USER)
        {
            if (mBuiltInStylesBySti.containsKey((int)newSti))
                mBuiltInStylesBySti.set((int)newSti, style);
            else
                mBuiltInStylesBySti.add((int)newSti, style);
        }
    }

    void updateIstdMap(Style style, int oldIstd, int newIstd)
    {
        mStylesByIstd.remove(oldIstd);
        if (mStylesByIstd.containsKey(newIstd))
            mStylesByIstd.set(newIstd, style);
        else
            mStylesByIstd.add(newIstd, style);

        updateModelWithIstd(style, oldIstd, newIstd);
    }

    /// <summary>
    /// Regenerates the <see cref="mStylesByIstd"/> map. It is executed from <see cref="StyleIstdNormalizer"/> after
    /// generation new <see cref="Style.Istd"/>.
    /// </summary>
    void updateIstdMap()
    {
        SortedIntegerListGeneric<Style> newStylesByIstd = new SortedIntegerListGeneric<Style>(mStylesByIstd.getCount());

        for (int i = 0; i < mStylesByIstd.getCount(); i++)
        {
            Style style = mStylesByIstd.getByIndex(i);
            newStylesByIstd.add(style.getIstd(), style);
        }

        mStylesByIstd = newStylesByIstd;
    }

    private void updateModelWithIstd(Style newStyle, int istd, int newIstd)
    {
        updateStyles(istd, newIstd);
        updateDomWithIstd(newStyle, istd, newIstd);
    }

    /// <summary>
    /// New istd for a style can affect other styles, that rely on its old istd:
    /// - basedOnIstd
    /// - NextIstd
    /// - Linked istd
    /// check these cases and update
    /// </summary>
    private void updateStyles(int istd, int newIstd)
    {
        for (Style style : this)
        {
            if (style.getBasedOnIstd() == istd)
                style.setBasedOnIstd(newIstd);

            if (style.getNextIstd() == istd)
                style.setNextIstd(newIstd);

            if (style.getLinkedIstd() == istd)
                style.setLinkedIstd(newIstd);
        }
    }

    /// <summary>
    /// Updates/removes Istd for all nodes referred to style being removed/modified.
    /// Updates when newIstd != -1, otherwise removes istd from Pr bag.
    /// </summary>
    private void updateDomWithIstd(Style style, int istd, int newIstd)
    {
        switch (style.getType())
        {
            case StyleType.CHARACTER:
                processCharStyle(istd, newIstd);
                break;
            case StyleType.PARAGRAPH:
                processParaStyle(istd, newIstd);
                break;
            case StyleType.LIST:
                processListStyle(style, istd, newIstd);
                break;
            case StyleType.TABLE:
                processTableStyle(istd, newIstd);
                break;
            default:
                throw new IllegalStateException("Unknown style type.");
        }
    }

    /// <summary>
    /// Adds a style. For internal use, called from the file readers, solves the problem
    /// with non-unique style names or style identifiers that sometimes occur in documents.
    /// </summary>
    /// <param name="style"></param>
    /// <param name="aliases">Aliases of the style, can be null.</param>
    void addForLoad(Style style, String[] aliases)
    {
        if (!isValidStyleType(style))
        {
            // AM. Unfortunately TableNormal has been created already as Style object while we need TableStyle.
            // I think best solution here is to instantiate TableNormal style from template.
            // Also I think we can do the same for DefaultParagraphFont and Normal styles.
            getBySti(style.getStyleIdentifier(), true);
            return;
        }

        //If a style with this name already exists, add a number suffix to make a unique style name.
        if (mStylesByName.containsKey(style.getName()))
            style.setNameCore(getUniqueStyleName(style.getName()));

        //If a style with this sti already exists, have to reset it to user style then.
        if (style.getBuiltIn() && (mBuiltInStylesBySti.containsKey((int)style.getStyleIdentifier())))
            style.setStyleIdentifier(StyleIdentifier.USER);

        add(style);

        //Add any style aliases
        if (aliases != null)
        {
            for (String alias : aliases)
            {
                // WORDSNET-19960 Avoid Normal style name for other than Normal style.
                if ((NORMAL_STYLE_NAME.equals(alias)) && (style.getStyleIdentifier() != StyleIdentifier.NORMAL))
                    continue;

                mStylesByName.add(getUniqueStyleName(alias), style);
            }

            mStyleAliasesCache = null;
        }
    }

    /// <summary>
    /// Adds an alias to the specified style. For internal use. The style must belong to this collection.
    /// </summary>
    void addAlias(Style style, String alias)
    {
        Debug.assertTrue(StringUtil.hasChars(alias));
        Debug.assertTrue(style != null);
        Debug.assertTrue(style.getStyles() == this);

        mStylesByName.add(getUniqueStyleName(alias), style);
        mStyleAliasesCache = null;
    }

    /// <summary>
    /// Removes all aliases of the specified style. For internal use. The style must belong to this collection.
    /// </summary>
    void removeAliases(Style style)
    {
        Debug.assertTrue(style != null);
        Debug.assertTrue(style.getStyles() == this);

        // Remove all names that reference this style (including the style itself).
        removeFromByNameList(style);
        // Restore only the style itself.
        mStylesByName.add(style.getName(), style);

        mStyleAliasesCache = null;
    }

    /// <summary>
    /// Appends a suffix number to the name of the style to make the name unique.
    /// </summary>
    String getUniqueStyleName(String name)
    {
        String uniqueName = name;
        int i = 0;
        while (mStylesByName.containsKey(uniqueName))
        {
            uniqueName = msString.format("{0}_{1}", name, i);
            i++;
        }
        return uniqueName;
    }

    /// <summary>
    /// Copies a style into this collection.
    /// </summary>
    /// <param name="style">Style to be copied.</param>
    /// <returns>Copied style ready for usage.</returns>
    /// <remarks>
    /// <para>Style to be copied can belong to the same document as well as to different document.</para>
    /// <para>Linked style is copied.</para>
    /// <para>This method does doesn't copy base styles.</para>
    /// <para>If collection already contains a style with the same name, then new name is
    /// automatically generated by adding "_number" suffix starting from 0 e.g. "Normal_0", "Heading 1_1" etc.
    /// Use <see cref="Style.Name"/> setter for changing the name of the imported style.</para>
    /// </remarks>
    public Style addCopy(Style style)
    {
        return addCopy(style, null);
    }

    /// <summary>
    /// Copies a style into this collection.
    /// </summary>
    /// <remarks>
    /// The <paramref name="context"/> can be <c>null</c>, if we are not in importing process.
    /// Otherwise, if importing in <see cref="ImportFormatMode.KeepDifferentStyles"/> mode,
    /// then direct formatting of the style will not be collapsed over destination attributes,
    /// as it happens in the usual way.
    /// </remarks>
    private Style addCopy(Style style, ImportContext context)
    {
        if (style == null)
            throw new IllegalArgumentException("Style can not be null.");

        Style copy = addCopyCore(style, context); // copy style

        if (style.getLinkedIstd() != StyleIndex.NIL) // copy linked style
        {
            Style linkedStyle = style.getStyles().getByIstd(style.getLinkedIstd(), false);
            if (linkedStyle != null)
            {
                Style linkedCopy = addCopyCore(linkedStyle, context);
                copy.setLinkedIstd(linkedCopy.getIstd());
                linkedCopy.setLinkedIstd(copy.getIstd());
            }
            else
            {
                copy.setLinkedIstd(StyleIndex.NIL);
            }
        }

        return copy;
    }

    /// <summary>
    /// Checks that style has valid style type.
    /// </summary>
    private static boolean isValidStyleType(Style style)
    {
        switch (style.getStyleIdentifier())
        {
            case StyleIdentifier.DEFAULT_PARAGRAPH_FONT:
                return style.getType() == StyleType.CHARACTER;
            case StyleIdentifier.NORMAL:
                return style.getType() == StyleType.PARAGRAPH;
            case StyleIdentifier.TABLE_NORMAL:
                return style.getType() == StyleType.TABLE;
            default:
                return true;
        }
    }

    /// <summary>
    /// Copies a single style into this collection.
    /// </summary>
    private Style addCopyCore(Style style, ImportContext context)
    {
        Style dstStyle = style.deepClone();
        // if added style is not instantiated, retain the original name.
        dstStyle.setNameCore(mStylesByName.containsKey(style.getName()) ? getUniqueStyleName(style.getName()) : style.getName());

        /*StyleIdentifier*/int sti = StyleIndex.builtInStyleNameEnglish(dstStyle.getName());
        boolean istdChanged = false;
        if (sti != StyleIdentifier.USER)
            istdChanged = StyleIndex.convertToBuiltInStyle(dstStyle, sti, null, false);
        else
            dstStyle.setStyleIdentifier(StyleIdentifier.USER);

        if (!istdChanged)
            dstStyle.setIstd(getNextFreeIstd()); // Update istd if we did not change it during ConvertToBuiltInStyle.

        // For simplicity we will only retain source styles for NextIstd and BasedOnIstd only if they are reserved.
        dstStyle.setNextIstd((StyleIndex.isIstdReserved(style.getNextIstd())) ? style.getNextIstd() : dstStyle.getIstd());
        dstStyle.setBasedOnIstd((StyleIndex.isIstdReserved(style.getBasedOnIstd())) ? style.getBasedOnIstd() : StyleIndex.NIL);

        add(dstStyle);

        // We should copy List style in styles of all types except CharacterStyle. DD: I'm not sure this if is 100% correct,
        int listId = (/*int*/Integer)((IParaAttrSource)style).fetchParaAttr(ParaAttr.LIST_ID);
        if ((style.getType() != StyleType.CHARACTER) && (listId > 0))
        {
            List list = style.getDocument().getLists().fetchListByListIdResolveStyleReference(listId);
            List dstList = getDocument().getLists().addCopy(list, false);
            dstStyle.getParaPr().setListId(dstList.getListId());

            for (ListLevel level : dstList.getListLevels()) // correct references in list levels.
                if (level.getParaStyleIstd() == style.getIstd())
                    level.setParaStyleIstd(dstStyle.getIstd());
        }

        if ((dstStyle.hasRevisions()) && (dstStyle.getDocument() instanceof Document))
            ((Document)dstStyle.getDocument()).getRevisions().addStyleRevisions(dstStyle);

        // WORDSNET-20704 We should calculate styles difference at the very end,
        // after all related staff has been imported already.
        Document srcDoc = ms.as(style.getDocument(), Document.class);
        if ((srcDoc != null) && !srcDoc.getStyles().isBuiltinStylesTemplate())
            copyStyleAttributes(style, dstStyle, context);

        return dstStyle;
    }

    private static void copyStyleAttributes(Style srcStyle, Style dstStyle, ImportContext context)
    {
        switch (srcStyle.getType())
        {
            case StyleType.PARAGRAPH:
                copyRunPr(srcStyle, dstStyle, context);
                copyParaPr(srcStyle, dstStyle, context);
                break;
            case StyleType.CHARACTER:
                copyRunPr(srcStyle, dstStyle, context);
                break;
            case StyleType.LIST:
                // Do nothing.
                break;
            case StyleType.TABLE:
                copyTableStyle((TableStyle)srcStyle, (TableStyle)dstStyle, context);
                break;
            default:
                throw new IllegalArgumentException("Unknown style type");
        }
    }

    /// <summary>
    /// Expands paragraph formatting of source style considering inherited difference.
    /// </summary>
    private static void copyParaPr(Style srcStyle, Style dstStyle, ImportContext context)
    {
        final /*ParaPrExpandFlags*/int EXPAND_FLAGS = ParaPrExpandFlags.DOCUMENT_DEFAULTS | ParaPrExpandFlags.GLOBAL_DEFAULTS;

        // IN. Actually, I think we should not expand here a corresponding ListLevel attributes, when style is numbered.
        // We always import a List itself along with such style. So, it can cause a problem if after import
        // we change properties at ListLevel (it will be overridden with old value, which will be set in style after expand).
        // But it looks as a very rare case, so lets postpone it for a while.
        ParaPr expParaPr = srcStyle.getExpandedParaPr(EXPAND_FLAGS);
        ParaPr baseExpParaPr = dstStyle.getExpandedParaPr(EXPAND_FLAGS | ParaPrExpandFlags.NO_DIRECT_FORMATTING);

        // WORDSNET-20704 When we expand destination style with flag 'NoDirectFormatting',
        // the corresponding direct list properties are not expanded as well.
        // So, expand them over the destination attributes forcibly.
        // WORDSNET-21615 When style has numbered style reference, the corresponding referenced style might
        // been not set to the style at this point yet. As we need just appropriate list level formatting to be
        // expanded and it should not be changed during import in any case, we can take it from the source style.
        if (srcStyle.getParaPr().getListId() != 0)
            srcStyle.getDocument().getLists().expandDirectList(srcStyle.getParaPr(), baseExpParaPr);

        // WORDSNET-23910 Don't collapse attributes that were present in direct formatting of source style
        // to not (possible) lose them. This is behavior by design in KeepDifferentStyles mode.
        // WORDSNET-23461 Also don't collapse attributes when 'ForceCopyStyles' option is enabled, by design.
        int[] keysToIgnore = (context != null) &&
                             ((context.getImportFormatMode() == ImportFormatMode.KEEP_DIFFERENT_STYLES) ||
                              context.getImportFormatOptions().getForceCopyStyles())
            ? srcStyle.getParaPr().getKeys()
            : new int[] { };
        expParaPr.collapse(baseExpParaPr, keysToIgnore);

        // Restore some attributes that were imported already or are not collapsible.
        // TODO IN We store 'istd' in paragraph and run properties along with the storing
        // it in a separate field of Style. It would be nice to get rid of storing it in
        // attribute collections, but a lot of golds are failed. See also details in Style.SetIstd().
        // WORDSNET-21296 It is possible that the style is not a list in the source document,
        // but one of its basic styles is a list in the destination document. Because of this,
        // the style also becomes a list in the destination document. In this case we should
        // set this style explicitly not a list item.
        if (expParaPr.isExplicitlyNotListItem())
            dstStyle.getParaPr().set(ParaAttr.LIST_ID, 0);
        dstStyle.getParaPr().mirrorTo(expParaPr, new int[] { ParaAttr.ISTD, ParaAttr.LIST_ID, ParaAttr.RSID_P });
        dstStyle.setParaPr(expParaPr);
    }

    /// <summary>
    /// Expands run formatting of source style considering inherited difference.
    /// </summary>
    private static void copyRunPr(Style srcStyle, Style dstStyle, ImportContext context)
    {
        Theme srcTheme = srcStyle.getDocument().getThemeInternal();
        Theme dstTheme = dstStyle.getDocument().getThemeInternal();

        boolean isThemeFontsDifferent = (!Theme.themeFontsEquals(srcTheme, dstTheme) && !(srcStyle.getDocument() instanceof GlossaryDocument) && !(dstStyle.getDocument() instanceof GlossaryDocument));

        boolean isCharStyleLinkedInSrc =
            (srcStyle.getType() == StyleType.CHARACTER) && (srcStyle.getLinkedIstd() != (int)StyleIdentifier.NIL);
        /*RunPrExpandFlags*/int expandFlags = getRunPrExpandFlags(dstStyle, isCharStyleLinkedInSrc);

        RunPr expRunPr = srcStyle.getExpandedRunPr(expandFlags);

        if (isThemeFontsDifferent)
            Theme.apply(srcTheme, expRunPr);

        // Word does not collapse attributes in 'pure' character styles.
        boolean isCharacterStyle = (dstStyle.getType() == StyleType.CHARACTER) &&
                (dstStyle.getLinkedIstd() == StyleIndex.NIL) && !isCharStyleLinkedInSrc;
        if (!isCharacterStyle)
        {
            RunPr baseRunPr = dstStyle.getExpandedRunPr(expandFlags | RunPrExpandFlags.NO_DIRECT_FORMATTING);

            // WORDSNET-17186 We should not apply Themes to a destination attributes
            // even Themes are different to mimic Word behavior.
            // WORDSNET-23910 Don't collapse attributes that were present in direct formatting of source style
            // to not (possible) lose them. This is behavior by design in KeepDifferentStyles mode.
            // WORDSNET-23461 Also don't collapse attributes when 'ForceCopyStyles' option is enabled, by design.
            int[] keysToIgnore = (context != null) &&
                                 ((context.getImportFormatMode() == ImportFormatMode.KEEP_DIFFERENT_STYLES) ||
                                  context.getImportFormatOptions().getForceCopyStyles())
                ? srcStyle.getRunPr().getKeys()
                : new int[] { };
            expRunPr.collapse(baseRunPr, keysToIgnore);
        }

        // Restore non-collapsible attributes.
        // TODO IN We need to get rid of storing 'istd' in RunPr collection.
        // See more details in CopyParaPr().
        dstStyle.getRunPr().mirrorTo(expRunPr, new int[] { FontAttr.ISTD, FontAttr.RSID_R, FontAttr.RSID_R_PR });
        dstStyle.setRunPr(expRunPr);
    }

    /// <summary>
    /// Retrieves "RunPrExpandFlags" for specified source and destination styles.
    /// </summary>
    private static /*RunPrExpandFlags*/int getRunPrExpandFlags(Style dstStyle, boolean isStyleLinkedInSource)
    {
        /*RunPrExpandFlags*/int expandFlags = RunPrExpandFlags.DOCUMENT_DEFAULTS | RunPrExpandFlags.GLOBAL_DEFAULTS;
        if ((dstStyle.getType() != StyleType.CHARACTER) || (dstStyle.getLinkedIstd() != StyleIndex.NIL))
            return expandFlags;

        // Seems Word does not expand defaults for character styles and styles that have linked style.
        if (!isStyleLinkedInSource)
            expandFlags = RunPrExpandFlags.NORMAL;

        return expandFlags;
    }

    private static void copyTableStyle(TableStyle srcTableStyle, TableStyle dstTableStyle, ImportContext context)
    {
        copyRunPr(srcTableStyle, dstTableStyle, context);
        copyParaPr(srcTableStyle, dstTableStyle, context);

        dstTableStyle.setRowPr(srcTableStyle.getExpandedRowPr());
        dstTableStyle.setCellPr(srcTableStyle.getExpandedCellPr());
        dstTableStyle.setTablePr(srcTableStyle.getExpandedTablePr());

        TableStyle baseStyle = ms.as(dstTableStyle.getBaseStyle(), TableStyle.class);
        if (baseStyle != null)
        {
            // WORDSNET-23910 Don't collapse attributes that were present in direct formatting of source style
            // to not (possible) lose them. This is behavior by design in KeepDifferentStyles mode.
            if ((context != null) && (context.getImportFormatMode() == ImportFormatMode.KEEP_DIFFERENT_STYLES))
            {
                dstTableStyle.getRowPr().collapse(baseStyle.getExpandedRowPr(), srcTableStyle.getRowPr().getKeys());
                dstTableStyle.getCellPr().collapse(baseStyle.getExpandedCellPr(), srcTableStyle.getCellPr().getKeys());
                dstTableStyle.getTablePr().collapse(baseStyle.getExpandedTablePr(), srcTableStyle.getTablePr().getKeys());
            }
            else
            {
                dstTableStyle.getRowPr().collapse(baseStyle.getExpandedRowPr());
                dstTableStyle.getCellPr().collapse(baseStyle.getExpandedCellPr());
                dstTableStyle.getTablePr().collapse(baseStyle.getExpandedTablePr());
            }
        }
    }

    /// <summary>
    /// Makes a deep copy of the styles. Suitable when copying a complete document.
    ///
    /// Note that list definitions that are referenced by the styles are not cloned.
    /// List definitions must be cloned separately using Lists.Clone.
    /// </summary>
    StyleCollection deepClone(DocumentBase dstDoc)
    {
        Debug.assertTrue(dstDoc != mDoc);

        StyleCollection lhs = (StyleCollection)memberwiseClone();

        lhs.mDoc = dstDoc;

        // RK It is safe to simply clone these attribute collections. Because we are copying
        // a complete document and styles and lists will have same ids in the new document.
        lhs.mDefaultRunPr = AttrCollectionExtension.deepClone(mDefaultRunPr);
        lhs.mDefaultParaPr = AttrCollectionExtension.deepClone(mDefaultParaPr);

        lhs.mStylesByIstd = new SortedIntegerListGeneric<Style>();
        lhs.mStylesByName = new StringListGeneric<Style>();
        lhs.mBuiltInStylesBySti = new SortedIntegerListGeneric<Style>();

        // Copy styles.
        for (int i = 0; i < mStylesByIstd.getCount(); i++)
        {
            Style srcStyle = mStylesByIstd.getByIndex(i);
            lhs.add(srcStyle.deepClone());
        }

        // Copy aliases.
        for (Map.Entry<String, Style> entry : (Iterable<Map.Entry<String, Style>>) mStylesByName)
        {
            String name = entry.getKey();
            Style srcStyle = entry.getValue();
            // All styles with their names have already been copied.
            // But if we come across a name that points to a style that does not equal
            // that style's name, it is an alias and we need to copy it.
            if (!msString.equals(name, srcStyle.getName()))
            {
                Style dstStyle = lhs.getByName(srcStyle.getName(), false);
                lhs.mStylesByName.add(name, dstStyle);
            }
        }

        lhs.mLatentStyles = mLatentStyles.deepClone();

        lhs.mStyleAliasesCache = null;

        lhs.mDefaultFont = null;
        lhs.mDefaultParagraphFormat = null;

        return lhs;
    }

    /// <summary>
    /// Returns comma separated list of style aliases optionally including style name.
    /// </summary>
    String getAliases(Style style, boolean isIncludeName)
    {
        if (mStyleAliasesCache == null)
            cacheAliases();

        String aliases = DictionaryExtension.getValueOrNull(mStyleAliasesCache, style);
        if (!StringUtil.hasChars(aliases))
            aliases = "";

        return isIncludeName
            ? StringUtil.appendCommaSeparated(style.getName(), aliases)
            : aliases;
    }

    /// <summary>
    /// Caches comma separated aliases for each style.
    /// </summary>
    private void cacheAliases()
    {
        mStyleAliasesCache = new HashMap<Style, String>(mStylesByName.getCount());

        for (int i = 0; i < mStylesByName.getCount(); i++)
        {
            // If found an entry that points to the same style and
            // it is different from the style name, add it as an alias.
            Style style = mStylesByName.getByIndex(i);
            String name = mStylesByName.getKey(i);
            if (!msString.equals(style.getName(), name))
            {
                String result = DictionaryExtension.getValueOrNull(mStyleAliasesCache, style);
                mStyleAliasesCache.put(style, StringUtil.appendCommaSeparated(result, name));
            }
        }
    }

    /// <summary>
    /// Gets style by istd, returns null if not found.
    /// </summary>
    Style getByIstd(int istd, boolean isAllowAutoCreate)
    {
        Style style = mStylesByIstd.get(istd);

        //If cannot find the style, maybe its a built in style, try to instantiate it on demand.
        if ((style == null) && (isAllowAutoCreate))
        {
            Style prototype = getBuiltInStyles().getByIstd(istd, false);
            if (prototype != null)
                style = importStyle(prototype);
        }

        return style;
    }

    /// <summary>
    /// Gets style by name or alias, returns null if not found.
    /// </summary>
    Style getByName(String name, boolean isAllowAutoCreate)
    {
        // WORDSNET-1431 Allow empty style name as a document in this defect has one.
        ArgumentUtil.checkNotNull(name, "name");

        Style style = (Style)mStylesByName.get(name);

        // If cannot find the style, maybe its a built in style, try to instantiate it on demand.
        if ((style == null) && (isAllowAutoCreate))
        {
            Style prototype = getBuiltInStyles().getByName(name, false);

            if (prototype == null)
            {
                // In cases when MS Word version of the document is not specifies or higher/lower then requested style, lets
                // try to get this style by name from another BuiltInStyles collections (2013, 2007 or 2003).
                prototype = getBuiltInStyles2013().getByName(name, false);
                if (prototype == null)
                    prototype = getBuiltInStyles2007().getByName(name, false);
                if (prototype == null)
                    prototype = getBuiltInStyles2003().getByName(name, false);
            }

            // There are 3 character styles linked to built-in paragraph styles in BuiltInStyles2007 collection
            // and 40 linked character styles in BuiltInStyles2013 collection. Lets check and return only built-in styles.
            if ((prototype != null) && prototype.getBuiltIn())
                style = importStyle(prototype);
        }

        return style;
    }

    /// <summary>
    /// Gets a built in style by sti, returns null if not found.
    /// </summary>
    Style getBySti(/*StyleIdentifier*/int sti, boolean isAllowAutoCreate)
    {
        if (sti == StyleIdentifier.USER)
            throw new IllegalArgumentException("Cannot return user defined styles by style identifier.");

        Style style = mBuiltInStylesBySti.get((int)sti);

        //If the style is not found in the collection, instantiate a built in style on demand.
        if ((style == null) && (isAllowAutoCreate))
        {
            Style prototype = getBuiltInStylePrototype(sti);

            if (prototype != null)
                style = importStyle(prototype);
        }

        return style;
    }

    /// <summary>
    /// Returns style prototype from a BuiltIn style collection.
    /// </summary>
    /// <remarks>
    /// In cases when MS Word version of the document is not specifies or higher/lower then requested style,
    /// tries to get this style from another BuiltInStyles collections (2013, 2007 or 2003).
    /// </remarks>
    private Style getBuiltInStylePrototype(/*StyleIdentifier*/int sti)
    {
        Style prototype = getBuiltInStyles().getBySti(sti, false);

        if (prototype == null)
        {
            prototype = getBuiltInStyles2013().getBySti(sti, false);
            if (prototype == null)
                prototype = getBuiltInStyles2007().getBySti(sti, false);
            if (prototype == null)
                prototype = getBuiltInStyles2003().getBySti(sti, false);
        }

        return prototype;
    }

    /// <summary>
    /// Get load format if it is not a GlossaryDocument.
    /// Need it to determine which BuildIn style collection (Word2003 or Word2007) to use.
    /// </summary>
    private /*LoadFormat*/int getLoadFormat()
    {
        // GlossaryDocument it is a part of DOCX document so we should return LoadFormat.Docx,
        // to use Word2007 BuildIn style collection.
        if (mDoc.getNodeType() == NodeType.GLOSSARY_DOCUMENT)
            return LoadFormat.DOCX;

        return ((Document)mDoc).getOriginalLoadFormat();
    }

    /// <summary>
    /// Determines whether a style with specified <see cref="StyleIdentifier"/> is present in this style collection.
    /// </summary>
    boolean contains(/*StyleIdentifier*/int styleIdentifier)
    {
        return mBuiltInStylesBySti.containsKey((int)styleIdentifier);
    }

    /// <summary>
    /// Fetches a style by istd.
    /// </summary>
    /// <param name="istd">The style to find.</param>
    /// <param name="defaultIstd">If the istd style is not found,
    /// returns the style specified by this parameter.
    /// Throws if the default style is not found.</param>
    Style fetchByIstd(int istd, int defaultIstd)
    {
        //Allow auto create by istd only makes sense if it is one of the fixed istds.
        Style style = getByIstd(istd, (istd <= MAX_FIXED_ISTD));
        if (style != null)
            return style;

        //Allow auto create by istd only makes sense if it is one of the fixed istds.
        style = getByIstd(defaultIstd, (defaultIstd <= MAX_FIXED_ISTD));
        if (style != null)
            return style;

        throw new IllegalStateException("Cannot find a style with this istd.");
    }

    /// <summary>
    /// Gets style by name, attempts to instantiate built in styles, throws if cannot find or auto instantiate.
    /// </summary>
    Style fetchByName(String name)
    {
        Style style = this.get(name);
        if (style == null)
            throw new IllegalStateException(msString.format("Cannot find style '{0}'.", name));
        return style;
    }

    /// <summary>
    /// Gets a style by style identifier, attempts to instantiate built in styles, throws if cannot find or auto instantiate.
    /// </summary>
    Style fetchBySti(/*StyleIdentifier*/int sti)
    {
        Style style = this.getByStyleIdentifier(sti);
        if (style == null)
            throw new IllegalStateException("Cannot find a style with this style identifier.");
        return style;
    }

    /// <summary>
    /// Imports a style during style autocreation.
    /// </summary>
    Style importStyle(Style srcStyle)
    {
        ImportContext context = new ImportContext(srcStyle.getDocument(), getDocument(), ImportFormatMode.USE_DESTINATION_STYLES);
        return importStyle(context, srcStyle);
    }

    /// <summary>
    /// Imports a style and adds it to this document. The exact action depends on the importFormatMode
    /// parameter. In either case imports all styles this style depends on and updates relevant istds.
    ///
    /// This method is too long, refactor.
    /// </summary>
    /// <param name="context">Current import data.</param>
    /// <param name="srcStyle">The style to import. If the style already belongs to this document, does nothing.</param>
    /// <returns>Returns the imported style that was added to this document.</returns>
    Style importStyle(ImportContext context, Style srcStyle)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	if (srcStyle == null)
            	throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "srcStyle");

        	// If the style belongs to this document already, just return it.
        	if (srcStyle.getStyles() == this)
            	return srcStyle;

        	// WORDSNET-16135 Recurse to import base style.
        	if (srcStyle.getBasedOnIstd() != StyleIndex.NIL)
        	{
            	int dstBasedOnIstd = getDstBasedOnIstd(srcStyle, context);
            	if (dstBasedOnIstd == StyleIndex.NIL)
                	importStyle(context, srcStyle.getBaseStyle());
        	}

        	// If the style was already imported, return it.
        	if (context.isImported(srcStyle))
            	return getByIstd(context.getImportedIstds().get(srcStyle.getIstd()), false);

        	// Start style import
        	Style dstStyle;
        	switch (context.getImportFormatMode())
        	{
            	case ImportFormatMode.USE_DESTINATION_STYLES:
            	case ImportFormatMode.KEEP_DIFFERENT_STYLES:
                	dstStyle = importStyleUseDestinationStyles(context, srcStyle);
                	break;
            	case ImportFormatMode.KEEP_SOURCE_FORMATTING:
                	dstStyle = importStyleKeepSourceFormatting(context, srcStyle);
                	break;
            	default:
                	throw new IllegalStateException("Unknown import format action.");
        	}

        	return dstStyle;
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// If the source style is built in style, first tries to get a style by style identifier,
    /// if not found tries by name. For user defined styles just tries by name.
    /// Returns null if not found.
    /// </summary>
    Style findLocaleIndependentMatch(Style srcStyle)
    {
        if (srcStyle.getBuiltIn())
        {
            // WORDSNET-3658 For built in styles we must look by name if cannot find by style identifier
            // because a style with this name might still exist but with a different identifier.
            Style dstStyle = getBySti(srcStyle.getStyleIdentifier(), false);
            if (dstStyle != null)
                return dstStyle;
        }

        return getByName(srcStyle.getName(), false);
    }

    /// <summary>
    /// Returns style that matches a specified style and has the same type.
    /// </summary>
    /// <remarks>
    /// If the source style is built in style, first tries to get a style by style identifier.
    /// If not found, then tries find by name and aliases.
    /// </remarks>
    /// <returns>Style if found, otherwise null.</returns>
    Style findLocaleIndependentMatchSameType(Style srcStyle)
    {
        if (srcStyle.getBuiltIn())
        {
            // Search by style identifier.
            Style dstStyle = getBySti(srcStyle.getStyleIdentifier(), false);
            if ((dstStyle != null) && (dstStyle.getType() == srcStyle.getType()))
                return dstStyle;
        }

        return getByNameSameType(srcStyle);
    }

    /// <summary>
    /// Finds style inside source document and imports it.
    /// KeepSourceFormatting means we must preserve formatting of source document.
    /// </summary>
    private Style importStyleKeepSourceFormatting(ImportContext context, Style srcStyle)
    {
        Style dstStyle;

        // Current implementation just recursively copies the style and all styles it depends on.
        if (StyleIndex.isNonModifiable(srcStyle))
        {
            // Special handling for default paragraph font style and table normal because they cannot be modified in Microsoft Word.
            // These styles are almost always present so the normal scenario is just return the existing style from the destination document.
            // On rare occasions the document does not have this style yet, in this case the code below just imports the style normally.
            dstStyle = getBySti(srcStyle.getStyleIdentifier(), false);
            if (dstStyle != null)
                return dstStyle;
        }

        // Start the importing business.
        dstStyle = srcStyle.deepClone();

        if (context.isThemeFontsDifferent())
            Theme.apply(context.getSrcDoc().getThemeInternal(), dstStyle.getRunPr());
        if (context.isThemeColorsDifferent())
            ThemeColorUpdater.update(dstStyle, context.getDstDoc().getThemeInternal());

        if (findLocaleIndependentMatch(srcStyle) == null)
        {
            // No match is found, no need to change name and sti.
            // Only need to change istd if it is not a fixed one.
            if (srcStyle.getIstd() > MAX_FIXED_ISTD)
                dstStyle.setIstd(getNextFreeIstd());
        }
        else
        {
            // A match is found (either by name or by sti), have to change name, istd and sti to be valid in the destination.
            dstStyle.setNameCore(getUniqueStyleName(srcStyle.getName()));
            dstStyle.setIstd(getNextFreeIstd());
            dstStyle.setStyleIdentifier(StyleIdentifier.USER);
        }

        add(dstStyle);
        context.getImportedIstds().set(srcStyle.getIstd(), dstStyle.getIstd());

        // WORDSNET-24010 Reset all related styles of the importing style.
        dstStyle.setBasedOnIstd(StyleIndex.NIL);
        dstStyle.setNextIstd(StyleIndex.NIL);
        dstStyle.setLinkedIstd(StyleIndex.NIL);

        // And now import all related styles.
        importRelatedStyles(srcStyle, dstStyle, context);

        if (!context.getSrcStyles().isBuiltinStylesTemplate())
            calculateDifference(srcStyle, dstStyle, context);

        return dstStyle;
    }

    /// <summary>
    /// Finds style inside destination document or inside template and imports it.
    /// </summary>
    private Style importStyleUseDestinationStyles(ImportContext context, Style srcStyle)
    {
        Style dstStyle = findLocaleIndependentMatch(srcStyle);

        // Style with such name or sti already exists.
        if (dstStyle != null)
        {
            // We have two options here: UseDestinationStyles and KeepDifferentStyles.
            // If it is UseDestinationStyles, then we just use existing style.
            if (context.getImportFormatMode() == ImportFormatMode.USE_DESTINATION_STYLES)
                return dstStyle;

            // Otherwise, we should check first if there is equal style in this collection to reuse it.
            Style dstStyleImported = importStyleKeepSourceFormatting(context, srcStyle);
            // WORDSNET-21485 Don't search equal styles when 'KeepSourceNumbering' option is enabled
            // in order to always use the imported style.
            if (context.getImportFormatOptions().getKeepSourceNumbering())
                return dstStyleImported;

            dstStyle = findEqualStyle(dstStyleImported);
            // There is no just imported style in destination yet, so let's use it.
            if (dstStyle == null)
                return dstStyleImported;

            // Otherwise remove imported copy and return existing style as the result.
            dstStyleImported.remove();

            // Replace imported style with existing style in cache.
            context.getImportedIstds().set(srcStyle.getIstd(), dstStyle.getIstd());

            // WORDSNET-17179 Update also linked style.
            if (srcStyle.getLinkedIstd() != StyleIndex.NIL)
                context.getImportedIstds().set(srcStyle.getLinkedIstd(), dstStyle.getLinkedIstd());

            return dstStyle;
        }

        return importStyleKeepSourceFormatting(context, srcStyle);
    }

    /// <summary>
    /// Add the imported style to the document and to the cache.
    /// </summary>
    private void importRelatedStyles(Style srcStyle, Style dstStyle, ImportContext context)
    {
        if (srcStyle.getBasedOnIstd() != StyleIndex.NIL)
        {
            // WORDSNET-16135 Set proper base style.
            // Note, at this point all base styles should be already either imported or exist in destination collection.
            int dstBasedOnIstd = getDstBasedOnIstd(srcStyle, context);
            Debug.assertTrue(dstBasedOnIstd != StyleIndex.NIL, msString.format("The base style for '{0}' must be already imported.", dstStyle.getName()));
            dstStyle.setBasedOnIstd(dstBasedOnIstd);
        }

        // Perform style type related import.
        if ((srcStyle.getType() == StyleType.LIST) || (srcStyle.getType() == StyleType.PARAGRAPH))
            importListDefinition(context, srcStyle, dstStyle);

        // Recurse to import next paragraph style.
        if (srcStyle.getNextIstd() != StyleIndex.NIL)
            dstStyle.setNextIstd(importStyle(context, srcStyle.getNextStyle()).getIstd());

        // Recurse to import linked style.
        if (srcStyle.getLinkedIstd() != StyleIndex.NIL)
            dstStyle.setLinkedIstd(importStyle(context, srcStyle.getLinkedStyle()).getIstd());
    }

    /// <summary>
    /// Called during import of paragraph or list styles to import the list definition
    /// referenced by the style (if any).
    /// </summary>
    private static void importListDefinition(ImportContext context, Style srcStyle, Style dstStyle)
    {
        Debug.assertTrue((srcStyle.getType() == StyleType.PARAGRAPH) || (srcStyle.getType() == StyleType.LIST));

        if (srcStyle.getParaPr().getListId() == 0)
            return;

        // Import the list definitions.
        int srcListId = srcStyle.getParaPr().getListId();
        int dstListId = context.getDstLists().importList(context, srcListId);
        dstStyle.getParaPr().setListId(dstListId);

        // For a list style, complete the bidirectional association between
        // the list style and list definition. Store the imported list style istd
        // in the list definition.
        if (dstStyle.getType() == StyleType.LIST)
            dstStyle.getList().getListDef().setListStyleIstd(dstStyle.getIstd());
    }

    /// <summary>
    /// Ensures that we have Normal and DefaultParagraphFont styles.
    /// These are needed for DOCX and WordML export to work properly.
    /// If we don't have these styles, they could end up being auto created after
    /// the styles were written to XML already.
    /// </summary>
    void ensureMinimum()
    {
        ensureReservedValid(StyleIdentifier.NORMAL, "Normal");
        ensureReservedValid(StyleIdentifier.DEFAULT_PARAGRAPH_FONT, "Default Paragraph Font");
    }

    /// <summary>
    /// Corrects the style name case so that it matches the reference "Normal".
    /// Here MS Word behavior in the similar case is copied.
    /// </summary>
    /// <dev>
    /// It is possible to expand the method in the future (for example, to "Default Paragraph Font" and "Table normal" styles)
    /// if the necessity in this becomes clear.
    /// </dev>
    void fixUpCaseIssue()
    {
        Style normalStyle = getByName("Normal", false);
        if ((normalStyle != null) || (getBySti(StyleIdentifier.NORMAL, false) != null))
            return;

        for (Map.Entry<String, Style> entry : (Iterable<Map.Entry<String, Style>>) mStylesByName)
        {
            String name = entry.getKey();
            Style style = entry.getValue();
            if ((style.getBasedOnIstd() == (int)StyleIdentifier.NIL) && (msString.equals(style.getBaseStyleName(), "")) &&
                msString.equals(msString.toUpper(name, msCultureInfo.getInvariantCulture()), "NORMAL", StringComparison.ORDINAL))
            {
                normalStyle = entry.getValue();
                break;
            }
        }

        if (normalStyle != null)
        {
            normalStyle.setNameCore("Normal", true);
            normalStyle.setStyleIdentifier(StyleIdentifier.NORMAL, true);
        }
    }

    /// <summary>
    /// Fixes any problems with the based on style identifiers. Call this from <see cref="DocumentPostLoader"/>.
    /// </summary>
    void fixUpBasedOnStyles()
    {
        // Copy to temp array at first because fixing might create new styles and invalidate iterator.
        ArrayList<Style> temp = new ArrayList<Style>();
        for (Style style : this)
            msArrayList.add(temp, style);

        // It is better to do this separately from fixing circular references, because checking
        // for circular references will traverse the based on chains and might encounter a missing style
        // and I don't want to mix the fixing of two different problems.
        for (Style style : temp)
            style.fixUpBasedOnMissing();

        for (Style style : temp)
            style.fixUpBasedOnCircularReferences();
    }

    /// <summary>
    /// Removes all locale id attributes from all styles in the collection.
    /// This method is actually a hack, needed while we have Blank.doc AllStyles.doc embedded resources as a DOC files.
    /// If we change them to DOCX or WML later, then we will be able to edit them to remove the unneeded language settings.
    /// </summary>
    void removeLocaleIdsFromStyles()
    {
        for (Style style : this)
        {
            RunPr runPr = style.getRunPr();
            runPr.remove(FontAttr.LOCALE_ID);
            runPr.remove(FontAttr.LOCALE_ID_BI);
            runPr.remove(FontAttr.LOCALE_ID_FAR_EAST);
        }
    }

    /// <summary>
    /// Updates stylesheet from given template.
    /// </summary>
    void updateFromTemplate(Document template)
    {
        ImportContext context = new ImportContext(template, getDocument(), ImportFormatMode.USE_DESTINATION_STYLES);

        boolean themesEqual = Theme.themeFontsEquals(mDoc.getThemeInternal(), template.getThemeInternal());

        for (Style dstStyle : getDocument().getStyles())
        {
            StyleCollection styles = template.getStyles();
            Style srcStyle = styles.findLocaleIndependentMatch(dstStyle);
            if (srcStyle == null)
                continue;

            // It seems that Word updates Normal in special way.
            if (dstStyle.getStyleIdentifier() == StyleIdentifier.NORMAL)
            {
                dstStyle.setRunPr(AttrCollectionExtension.deepClone(styles.getDefaultRunPr()));
                dstStyle.setParaPr(AttrCollectionExtension.deepClone(styles.getDefaultParaPr()));

                srcStyle.getRunPr().expandTo(dstStyle.getRunPr());
                srcStyle.getParaPr().expandTo(dstStyle.getParaPr());
            }
            else
            {
                dstStyle.setRunPr(AttrCollectionExtension.deepClone(srcStyle.getRunPr()));
                dstStyle.setParaPr(AttrCollectionExtension.deepClone(srcStyle.getParaPr()));

                if (dstStyle.getParaPr().getListId() != 0)
                    dstStyle.getParaPr().setListId(context.getDstLists().importList(context, srcStyle.getParaPr().getListId()));

                TableStyle dstTableStyle = ms.as(dstStyle, TableStyle.class);
                TableStyle srcTableStyle = ms.as(srcStyle, TableStyle.class);

                // Copy data related to table styles.
                if (dstTableStyle != null && srcTableStyle != null)
                {
                    dstTableStyle.setTablePr(AttrCollectionExtension.deepClone(srcTableStyle.getTablePr()));
                    dstTableStyle.clearConditionalStyles();

                    for (ConditionalStyle conditionalStyle : srcTableStyle.getConditionalStyles().getDefinedStyles())
                        dstTableStyle.addConditionalStyle(conditionalStyle.deepClone());
                }
            }

            if (!themesEqual)
                Theme.apply(template.getThemeInternal(), dstStyle.getRunPr());
        }
    }

    /// <summary>
    /// Copies source style with its Base, Linked and Next styles to this collection.
    /// </summary>
    int copyStyle(Style srcStyle, ImportContext context)
    {
        // First look at already copied styles.
        int copiedIstd = context.getImportedIstds().get(srcStyle.getIstd());
        if (!IntToIntDictionary.isNullSubstitute(copiedIstd))
            return copiedIstd;

        // Copy style properties and add this style to the styles collection.
        Style dstStyle = copyStyleCore(srcStyle, context);
        if (dstStyle == null)
            return StyleIndex.NIL;

        // WORDSNET-24640 Reset all related styles before copying.
        dstStyle.setBasedOnIstd(StyleIndex.NIL);
        dstStyle.setNextIstd(StyleIndex.NIL);
        dstStyle.setLinkedIstd(StyleIndex.NIL);

        // Process base style.
        if (srcStyle.getBasedOnIstd() != StyleIndex.NIL)
            dstStyle.setBasedOnIstd(copyStyle(srcStyle.getBaseStyle(), context));

        // Process linked style.
        if (srcStyle.getLinkedIstd() != StyleIndex.NIL)
            dstStyle.setLinkedIstd(copyStyle(srcStyle.getLinkedStyle(), context));

        // Process next style.
        if (srcStyle.getNextIstd() != StyleIndex.NIL)
            dstStyle.setNextIstd(copyStyle(srcStyle.getNextStyle(), context));

        // Process numbering.
        if ((srcStyle.getType() == StyleType.LIST) || (srcStyle.getType() == StyleType.PARAGRAPH))
        {
            int srcListId = srcStyle.getParaPr().getListId();
            if (srcListId != 0)
                dstStyle.getParaPr().set(ParaAttr.LIST_ID, getDocument().getLists().copyList(srcListId, context));
        }

        return dstStyle.getIstd();
    }

    /// <summary>
    /// Checks that style with given StyleIdentifier has appropriate StyleIndex.
    /// In case of it's not true or style is missing creates it from template.
    /// </summary>
    private void ensureReservedValid(/*StyleIdentifier*/int sti, String styleName)
    {
        Style style = getByName(styleName, false);
        if ((style != null) && (style.getStyleIdentifier() != sti))
        {
            // This means that we have user style with predefined name. Rename it and continue.
            String newName = getUniqueStyleName(styleName);
            style.setNameCore(newName, true);
        }

        int istd = StyleIndex.styleIdentifierToIstd(sti);

        style = getByIstd(istd, true);
        if (style.getStyleIdentifier() != sti)
        {
            // Change style's Istd and update Model.
            style.setIstd(getNextFreeIstd(), true);

            // Create style from template.
            getBySti(sti, true);
        }
    }

    /// <summary>
    /// Checks if styles has zero font size and corrects this.
    /// </summary>
    private static void removeZeroFontSize(RunPr runPr, int key)
    {
        if (runPr.contains(key))
        {
            int value = (/*int*/Integer)runPr.get(key);
            if (value == 0)
                runPr.remove(key);
        }
    }

    /// <summary>
    /// Finds style equal to the specified style.
    /// </summary>
    private Style findEqualStyle(Style srcStyle)
    {
        for (Style style : this)
        {
            // WORDSNET-14587 When import with KeepDifferentStyles mode, we first import style to calculate
            // formatting difference and import related styles properly. Then we need to search for another equal style.
            // So, skip this style itself.
            if (msObject.referenceEquals(style, srcStyle))
                continue;

            if (srcStyle.equals(style))
                return style;
        }

        return null;
    }

    /// <summary>
    /// Copies expanded attributes from the source style to
    /// the destination style and collapses them with expanded
    /// base attributes in destination collection. After that removes
    /// remained extra global defaults from the destination style.
    /// </summary>
    /// <remarks>See details in TestJira14588()</remarks>
    private void calculateDifference(Style srcStyle, Style dstStyle, ImportContext context)
    {
        // Word does not modify 'DefaultParagraphFont' and 'TableNormal' styles.
        if (StyleIndex.isNonModifiable(dstStyle))
            return;

        // Word resets base style to one of the 'TableNormal', 'Normal', 'DefaultParagraphFont',
        // depending on the style type.
        // WORDSNET-15319 First of all we have to reset base style and only then do collapse in CopyStyleAttributes() below.
        Style srcBaseStyle = srcStyle.getBaseStyle();
        if (srcBaseStyle != null)
        {
            // If destination collection contains appropriate style,
            // Word uses it as a new base style.
            Style dstBaseStyle = findLocaleIndependentMatch(srcBaseStyle);
            dstStyle.setBasedOnIstd((dstBaseStyle != null) ? dstBaseStyle.getIstd() : getDefaultStyleIndex(dstStyle.getType()));
        }

        copyStyleAttributes(srcStyle, dstStyle, context);
    }

    /// <summary>
    /// Gets corresponding BasedOnIstd for base style of the specified style in destination collection.
    /// </summary>
    /// <remarks>If there is no appropriate base style in destination collection, then returns StyleIndex.Nil.</remarks>
    private int getDstBasedOnIstd(Style srcStyle, ImportContext context)
    {
        Debug.assertTrue(srcStyle.getBasedOnIstd() != StyleIndex.NIL);

        Style srcBaseStyle = srcStyle.getBaseStyle();

        // First try to find style among imported styles.
        int dstBasedOnIstd = context.getImportedIstds().get(srcBaseStyle.getIstd());
        // If base style was not imported, then try to find same style in destination collection among existing styles.
        if (dstBasedOnIstd == Integer.MIN_VALUE)
        {
            Style dstBaseStyle;
            if (StyleIndex.isNonModifiable(srcBaseStyle))
            {
                dstBaseStyle = getBySti(srcBaseStyle.getStyleIdentifier(), false);
            }
            else
            {
                dstBaseStyle = findLocaleIndependentMatch(srcBaseStyle);
                if ((dstBaseStyle == null) && (context.getImportFormatMode() == ImportFormatMode.KEEP_DIFFERENT_STYLES))
                    dstBaseStyle = findEqualStyle(srcBaseStyle);
            }

            if (dstBaseStyle != null)
                dstBasedOnIstd = dstBaseStyle.getIstd();
        }

        return IntToIntDictionary.isNullSubstitute(dstBasedOnIstd) ? StyleIndex.NIL : dstBasedOnIstd;
    }

    /// <summary>
    /// Copies all styles from the source collection to this collection.
    /// </summary>
    private void copyAll(StyleCollection srcStyles, ImportContext context)
    {
        for (Style srcStyle : srcStyles)
            copyStyle(srcStyle, context);
    }

    /// <summary>
    /// Copies source style to this collection.
    /// </summary>
    private Style copyStyleCore(Style srcStyle, ImportContext context)
    {
        Style dstStyle = findLocaleIndependentMatch(srcStyle);

        while ((dstStyle != null) && (dstStyle.getType() != srcStyle.getType()))
        {
            // We here because source and destination styles have the different types.
            // But there can be a situation when destination style has aliases and some another source style with an
            // appropriate type will match this style. In that case such style will override destination style and then
            // current problematic style can be copied too. So, the easiest way here is just to update name and aliases of
            // the destination style with inappropriate type from the source style that will have the same style and
            // continue copying the problematic style.
            // Another way is to store such problematic styles in some collection of deferred styles and after all styles
            // are imported try to import them again. But such approach is much more complex. For example,
            // it will require to update all styles and lists with new imported istds.
            Style srcStyleSameType = srcStyle.getStyles().findLocaleIndependentMatchSameType(dstStyle);
            if (srcStyleSameType != null)
            {
                updateNameAndAliases(dstStyle, srcStyleSameType);
            }
            else
            {
                // Mimic Word and do not copy style that matches to a destination style with a different type.
                WarningUtil.warnDataLoss(getDocument().getWarningCallback(), WarningSource.UNKNOWN, WarningStrings.CANNOT_COPY_STYLE,
                    srcStyle.getName());

                context.getImportedIstds().set(srcStyle.getIstd(), StyleIndex.NIL);
                return null;
            }

            dstStyle = findLocaleIndependentMatch(srcStyle);
        }

        if (dstStyle != null)
        {
            cloneFormatting(srcStyle, dstStyle);
        }
        else
        {
            dstStyle = srcStyle.deepClone();

            if (mStylesByIstd.containsKey(dstStyle.getIstd()))
            {
                // Destination collection already contains the same istd, so get new one.
                dstStyle.setIstd(getNextFreeIstd());
            }

            add(dstStyle);
        }

        context.getImportedIstds().set(srcStyle.getIstd(), dstStyle.getIstd());
        return dstStyle;
    }

    /// <summary>
    /// Clones formatting from source to destination style.
    /// </summary>
    private static void cloneFormatting(Style srcStyle, Style dstStyle)
    {
        Debug.assertTrue(srcStyle.getType() == dstStyle.getType());
        dstStyle.clearCaches();
        updateNameAndAliases(srcStyle, dstStyle);
        dstStyle.copyGenerics(srcStyle);

        dstStyle.setRunPr(AttrCollectionExtension.deepClone(srcStyle.getRunPr()));
        if (dstStyle.getType() == StyleType.CHARACTER)
            return;

        dstStyle.setParaPr(AttrCollectionExtension.deepClone(srcStyle.getParaPr()));

        if (dstStyle.getType() == StyleType.TABLE)
            TableStyle.cloneProperties((TableStyle)srcStyle, (TableStyle)dstStyle);
    }

    /// <summary>
    /// Updates name and all aliases from the source style.
    /// </summary>
    private static void updateNameAndAliases(Style srcStyle, Style dstStyle)
    {
        StyleCollection dstStyles = dstStyle.getStyles();

        // Remove name and all aliases of the destination style.
        dstStyles.removeFromByNameList(dstStyle);

        // Restore style name from the source style.
        dstStyles.mStylesByName.set(srcStyle.getName(), dstStyle);

        // Add style aliases.
        for (String alias : srcStyle.getAliases())
            dstStyles.mStylesByName.set(alias, dstStyle);

        // Update aliases cache.
        // WORDSNET-18574 Don't update cache if it does not exist.
        if (dstStyles.mStyleAliasesCache != null)
        {
            if (srcStyle.getStyles().mStyleAliasesCache.containsKey(srcStyle))
                dstStyles.mStyleAliasesCache.put(dstStyle, srcStyle.getStyles().mStyleAliasesCache.get(srcStyle));
            else
                msDictionary.remove(dstStyles.mStyleAliasesCache, dstStyle);
        }
    }

    /// <summary>
    /// Gets style of the same type as a specified style by name or alias, returns null if not found.
    /// </summary>
    /// <remarks>Checks also all aliases of a source style.</remarks>
    private Style getByNameSameType(Style srcStyle)
    {
        for (int i = 0; i < mStylesByName.getCount(); i++)
        {
            String styleName = mStylesByName.getKey(i);
            if ((msString.equals(styleName, srcStyle.getName())) || ArrayUtil.contains(srcStyle.getAliases(), styleName))
            {
                Style dstStyle = mStylesByName.getByIndex(i);
                if (dstStyle.getType() == srcStyle.getType())
                    return dstStyle;
            }
        }

        return null;
    }

    /// <summary>
    /// Checks if StyleIdentifier.BalloonText has zero size and corrects it. See WORDSNET-9442
    /// </summary>
    private void fixBalloonStyles()
    {
        Style style = getBySti(StyleIdentifier.BALLOON_TEXT, false);

        if (style == null)
            return;

        removeZeroFontSize(style.getRunPr(), FontAttr.SIZE);
        removeZeroFontSize(style.getRunPr(), FontAttr.SIZE_BI);

        // Also look for linked BalloonTextChar style if exists.
        style = getByIstd(style.getLinkedIstd(), false);

        if (style == null)
            return;

        removeZeroFontSize(style.getRunPr(), FontAttr.SIZE);
        removeZeroFontSize(style.getRunPr(), FontAttr.SIZE_BI);
    }

    private DocumentBase mDoc;
    private RunPr mDefaultRunPr = new RunPr();
    private ParaPr mDefaultParaPr = new ParaPr();
    /// <summary>
    /// A map of styles sorted by istd.
    /// </summary>
    private SortedIntegerListGeneric<Style> mStylesByIstd = new SortedIntegerListGeneric<Style>();

    /// <summary>
    /// A map of styles names to styles. Can have several names (aliases) referring to a single style.
    /// </summary>
    /// <remarks>
    /// WORDSNET-26754 Before the fix this was sorted by names list <see cref="SortedIntegerListGeneric{T}"/>.
    /// </remarks>
    private StringListGeneric<Style> mStylesByName = new StringListGeneric<Style>();

    /// <summary>
    /// The helper class to sort <see cref="StyleCollection.mStylesByName"/> collection by name.
    /// </summary>
    private static class ComparerByName implements Comparator<Style>
    {
        public int compare(Style x, Style y)
        {
            return msString.compare(x.getName(), y.getName(), StringComparison.ORDINAL);
        }
    }

    /// <summary>
    /// A map of styles sorted by style identifier. Note it forces key to be int.
    /// If the key is enum, it becomes memory and performance sucker since .NET starts to use reflection.
    /// </summary>
    private SortedIntegerListGeneric<Style> mBuiltInStylesBySti = new SortedIntegerListGeneric<Style>();
    private LatentStyles mLatentStyles = new LatentStyles();

    /// <summary>
    /// Do not access this variable directly. Access it via the corresponding property.
    /// </summary>
    private static Document gAllStylesCache2003;
    private static /*final*/ Object G_ALL_STYLES_SYNC_ROOT_2003 = new Object();
    private static Document gAllStylesCache2007;
    private static /*final*/ Object G_ALL_STYLES_SYNC_ROOT_2007 = new Object();
    private static Document gAllStylesCache2013;
    private static /*final*/ Object G_ALL_STYLES_SYNC_ROOT_2013 = new Object();

    private Font mDefaultFont;
    private ParagraphFormat mDefaultParagraphFormat;

    /// <summary>
    /// Cached map style to comma separated list of aliases.
    /// </summary>
    private HashMap<Style, String> mStyleAliasesCache;

    /// <summary>
    /// Gets the number of reserved istds.
    /// </summary>
    static final int FIXED_ISTD_COUNT = 15;
    /// <summary>
    /// Gets the maximum reserved istd.
    /// </summary>
    static final int MAX_FIXED_ISTD = FIXED_ISTD_COUNT - 1;

    static final int MAX_KNOWN_STI_2003 = 156;
    static final int MAX_KNOWN_STI_2007 = 267;

    static final int VERSION_OF_BUILT_IN_NAMES_2007 = 7;

    static final String NORMAL_STYLE_NAME = "Normal";

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

