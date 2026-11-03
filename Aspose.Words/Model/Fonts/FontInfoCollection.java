// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/01/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToIntDictionary;
import java.util.Iterator;
import com.aspose.StringUtil;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ISetGeneric;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.SortedList;
import com.aspose.Debug;
import java.util.Collections;


/// <summary>
/// Represents a collection of fonts used in a document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fonts/">Working with Fonts</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>Items are <see cref="FontInfo"/> objects.</para>
/// 
/// <para>You do not create instances of this class directly. 
/// Use the <see cref="DocumentBase.FontInfos"/> property to access the collection of fonts 
/// defined in the document.</para>
/// 
/// <seealso cref="FontInfo"/>
/// <seealso cref="DocumentBase.FontInfos"/>
/// </remarks>
public class FontInfoCollection implements Iterable<FontInfo>
{
    /// <summary>
    /// Gets the number of elements contained in the collection.
    /// </summary>
    public int getCount() { return mItems.size(); }

    /// <overloads>Provides access to the collection items.</overloads>
    /// <summary>
    /// Gets a font with the specified name.
    /// </summary>
    /// <param name="name">Case-insensitive name of the font to locate.</param>
    public FontInfo get(String name)
    {
        int fontIndex = mFontNameToItemIndex.get(name);
        return StringToIntDictionary.isNullSubstitute(fontIndex) ? null : this.get(fontIndex);
    }


    /// <summary>
    /// Gets a font at the specified index.
    /// </summary>
    /// <param name="index">Zero-based index of the font.</param>
    public FontInfo get(int index) { return mItems.get(index); }

    /// <summary>
    /// Returns an <ms>enumerator</ms><java>iterator</java><cpp>enumerator</cpp> object that can be used to iterate over all items in the collection.
    /// </summary>
    public Iterator<FontInfo> iterator()
    {
        return mItems.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Determines whether the collection contains a font with the given name.
    /// </summary>
    /// <param name="name">Case-insensitive name of the font to locate.</param>
    /// <returns><c>true</c> if the item is found in the collection; otherwise, <c>false</c>.</returns>
    public boolean contains(String name)
    {
        return mFontNameToItemIndex.containsKey(name);
    }

    /// <summary>
    /// Specifies whether or not to embed TrueType fonts in a document when it is saved.
    /// Default value for this property is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// <para>Embedding TrueType fonts allows others to view the document with the same fonts that were used to create it,
    /// but may substantially increase the document size.</para>
    /// <para>This option works for DOC, DOCX and RTF formats only.</para>
    /// </remarks>
    public boolean getEmbedTrueTypeFonts() { return mEmbedTrueTypeFonts; }
    public void setEmbedTrueTypeFonts(boolean value) { mEmbedTrueTypeFonts = value; }

    /// <summary>
    /// <para>Specifies whether or not to embed System fonts into the document.
    /// Default value for this property is <c>false</c>.</para>
    /// <para>This option works only when <see cref="EmbedTrueTypeFonts"/> option is set to <c>true</c>.</para>
    /// </summary>
    /// <remarks>
    /// <para>
    /// Setting this property to <c>true</c> is useful if the user is on an East Asian system
    /// and wants to create a document that is readable by others who do not have fonts for that
    /// language on their system. For example, a user on a Japanese system could choose to embed the
    /// fonts in a document so that the Japanese document would be readable on all systems.
    /// </para>
    /// <para>This option works for DOC, DOCX and RTF formats only.</para>
    /// </remarks>
    public boolean getEmbedSystemFonts() { return mEmbedSystemFonts; }
    public void setEmbedSystemFonts(boolean value) { mEmbedSystemFonts = value; }

    /// <summary>
    /// <para>Specifies whether or not to save a subset of the embedded TrueType fonts with the document.
    /// Default value for this property is <c>false</c>.</para>
    /// <para>This option works only when <see cref="EmbedTrueTypeFonts"/> property is set to <c>true</c>.</para>
    /// </summary>
    /// <remarks>
    /// This option works for DOC, DOCX and RTF formats only.
    /// </remarks>
    public boolean getSaveSubsetFonts() { return mSaveSubsetFonts; }
    public void setSaveSubsetFonts(boolean value) { mSaveSubsetFonts = value; }

    /// <summary>
    /// Don't need public ctor.
    /// </summary>
    FontInfoCollection()
    {
        clear();
    }

    /// <summary>
    /// <para>Returns a font code (index of the font) for the given font name (case insensitive).</para>
    /// <para>Searches through fonts by their main name, creates a font info if does not exist.</para>
    /// </summary>
    int nameToCode(String name)
    {
        int fontCode = mFontNameToItemIndex.get(name);

        if (StringToIntDictionary.isNullSubstitute(fontCode))
            return merge(new FontInfo(name));

        return fontCode;
    }

    /// <summary>
    /// Returns font name for the specified code.
    /// Handles invalid requests safely by limiting the font code to valid range. 
    /// </summary>
    String codeToName(int fontCode)
    {
        // RK If we have an empty font table, we should create a default font.
        if (mItems.size() == 0)
            merge(new FontInfo("Times New Roman"));

        // RK Make the font code valid index.
        if ((fontCode < 0) || (fontCode >= mExternalCodeToFontName.size()))
            fontCode = 0;

        return mExternalCodeToFontName.get(fontCode);
    }

    /// <summary>
    /// <para>Adds clone of <see cref="FontInfo"/> to the collection if there is no <see cref="FontInfo"/>
    /// with the same name or merges information from new <see cref="FontInfo"/> to existing one.</para>
    /// </summary>
    /// <param name="fontInfo"><see cref="FontInfo"/> added to the collection. It's cloned or merged to one
    /// of existing <see cref="FontInfo"/>s.</param>
    /// <returns>Index of new or existing <see cref="FontInfo"/> in the collection.</returns>
    int merge(FontInfo fontInfo)
    {
        int fontIndex = 0;

        if (contains(fontInfo.getName()))
        {
            fontIndex = mFontNameToItemIndex.get(fontInfo.getName());
            mItems.get(fontIndex).merge(fontInfo);
        }
        else if (StringUtil.hasChars(fontInfo.getName()))
        {
            msArrayList.add(mItems, fontInfo.deepClone());
            fontIndex = mItems.size() - 1;
            mFontNameToItemIndex.set(fontInfo.getName(), fontIndex);
        }

        // AS Only font Name is needed during reading of DOC.
        // It's better to remove CodeToName method and mExternalCodeToFontName to DocFontCodeResolver
        // or combine it with RtfFontCodeResolver.
        // mExternalCodeToFontName can contain several records with the same font name.
        msArrayList.add(mExternalCodeToFontName, fontInfo.getName());

        for (String fontAltName : fontInfo.getAltNameList())
        {
            if (!mFontAltNameToItemIndex.containsKey(fontAltName))
                mFontAltNameToItemIndex.set(fontAltName, fontIndex);
        }

        return fontIndex;
    }

    /// <summary>
    /// Calls <see cref="Merge(FontInfo)"/> for all <see cref="FontInfo"/>s of source collection.
    /// </summary>
    /// <param name="srcFontInfoCollection">Can not be <c>null</c>.</param>
    void merge(FontInfoCollection srcFontInfoCollection)
    {
        for (FontInfo srcFontInfo : srcFontInfoCollection)
            merge(srcFontInfo);
    }

    /// <summary>
    /// <para>Update collection of fonts stored in the document according to their usage in used font names.
    /// The following operations are performed:</para>
    /// <list type="">
    /// <item>Filters out all <see cref="FontInfo"/>s which are not in <paramref name="usedFontNames"/> or have duplicated names.</item>
    /// <item>Adds new <see cref="FontInfo"/>s from <paramref name="usedFontNames"/> if they are absent in the collection.</item>
    /// </list>
    /// </summary>
    void updateToUsedFonts(ISetGeneric<String> usedFontNames)
    {
        ArrayList<String> newFontNames = new ArrayList<String>();
        SortedList<Integer, Integer> validFontIndexes = new SortedList<Integer, Integer>();

        findNewAndUsedExistingFonts(usedFontNames, newFontNames, validFontIndexes);

        removeUnusedFonts(validFontIndexes);
        addNewFonts(newFontNames);
    }

    /// <summary>
    /// Makes a deep copy of the collection.
    /// </summary>
    FontInfoCollection deepClone()
    {
        FontInfoCollection lhs = new FontInfoCollection();

        lhs.updateEmbedFontOptions(this);
        
        lhs.merge(this);

        return lhs;
    }

    /// <summary>
    /// Clones without fonts embedding.
    /// </summary>
    FontInfoCollection cloneWithoutEmbeddedFonts()
    {
        FontInfoCollection lhs = deepClone();
        lhs.removeEmbeddedFonts();
        
        return lhs;
    }

    /// <summary>
    /// Removes embedded fonts.
    /// </summary>
    void removeEmbeddedFonts()
    {
        for (FontInfo fontInfo : this)
            fontInfo.removeEmbeddedFonts();
    }

    /// <summary>
    /// Returns true if this font info collection has embedded fonts.
    /// </summary>
    boolean hasEmbeddedFonts()
    {
        for (FontInfo fontInfo : this)
            if (fontInfo.hasEmbeddedFonts())
                return true;

        return false;
    }

    /// <summary>
    /// Removes from <see cref="mItems"/> all fonts except having theirs indexes
    /// in <paramref name="validFontIndexes"/> collection.
    /// </summary>
    void removeUnusedFonts(SortedList<Integer, Integer> validFontIndexes)
    {
        ArrayList<FontInfo> itemsOriginal = mItems;
        clear();
        Debug.assertTrue(itemsOriginal != mItems);

        for (int fontIndex : validFontIndexes.getKeys())
            merge(itemsOriginal.get(fontIndex));
    }
    
    /// <summary>
    /// Sets all font embedding options equal to embedding options in specified <paramref name="docPr"/>.
    /// </summary>
    void updateEmbedFontOptions(DocPr docPr)
    {
        mEmbedTrueTypeFonts = docPr.EmbedTrueTypeFonts;
        mEmbedSystemFonts = docPr.EmbedSystemFonts;
        mSaveSubsetFonts = docPr.SaveSubsetFonts;
    }

    /// <summary>
    /// Sets all font embedding options equal to embedding options in specified <paramref name="fontInfos"/>.
    /// </summary>
    private void updateEmbedFontOptions(FontInfoCollection fontInfos)
    {
        mEmbedTrueTypeFonts = fontInfos.getEmbedTrueTypeFonts();
        mEmbedSystemFonts = fontInfos.getEmbedSystemFonts();
        mSaveSubsetFonts = fontInfos.getSaveSubsetFonts();
    }

    /// <summary>
    /// Clears the collection.
    /// </summary>
    private void clear()
    {
        mItems = new ArrayList<FontInfo>();
        mExternalCodeToFontName = new ArrayList<String>();
        mFontNameToItemIndex = new StringToIntDictionary(false);
        mFontAltNameToItemIndex = new StringToIntDictionary(false);
    }

    /// <summary>
    /// <para>Searches <paramref name="usedFontNames"/> (fonts used in the document) among fonts in the collection and fills two collections:
    /// <paramref name="newFontNames"/> and <paramref name="validFontIndexes"/>.</para>
    /// </summary>
    /// <param name="usedFontNames">Input collection of fonts used in the document.</param>
    /// <param name="newFontNames">Output collection of font names that will be added but not existing
    /// in the collection yet. Must be empty at input.</param>
    /// <param name="validFontIndexes">Output collection of indexes in <see cref="mItems"/> of fonts used in the document
    /// as <paramref name="usedFontNames"/> states. Must be empty at input.</param>
    private void findNewAndUsedExistingFonts(ISetGeneric<String> usedFontNames, 
        ArrayList<String> newFontNames,
        SortedList<Integer, Integer> validFontIndexes)
    {
        // RK Have to sort font names first because otherwise they will affect golds on Java.
        ArrayList<String> sortedFontNames = new ArrayList<String>();
        for (String fontName : usedFontNames)
            msArrayList.add(sortedFontNames, fontName);
        Collections.sort(sortedFontNames);

        for (String fontName : sortedFontNames)
        {
            int fontIndex = mFontNameToItemIndex.get(fontName);
            if (StringToIntDictionary.isNullSubstitute(fontIndex))
                fontIndex = mFontAltNameToItemIndex.get(fontName);

            if (StringToIntDictionary.isNullSubstitute(fontIndex))
            {
                msArrayList.add(newFontNames, fontName);
            }
            else if (!validFontIndexes.containsKey(fontIndex))
            {
                validFontIndexes.add(fontIndex, 0);
            }
        }
    }

    /// <summary>
    /// Adds to the collection new <see cref="FontInfo"/>s by their names collected in <paramref name="newFontNames"/>.
    /// </summary>
    /// <remarks>
    /// It's more correct to collect new fonts information from installed fonts
    /// but currently the function just add stubs for new fonts.
    /// </remarks>
    private void addNewFonts(ArrayList<String> newFontNames)
    {
        for (String fontName : newFontNames)
            merge(new FontInfo(fontName));
    }

    /// <summary>
    /// Contains FontInfo objects in the order they appear in the original document.
    /// </summary>
    private ArrayList<FontInfo> mItems;
    /// <summary>
    /// Key is case insensitive font name. Value is integer font code (index) in <see cref="mItems"/>.
    /// </summary>
    private StringToIntDictionary mFontNameToItemIndex;
    /// <summary>
    /// Keys is external index used in <see cref="CodeToName"/>. Value is index in <see cref="mItems"/>
    /// </summary>
    private ArrayList<String> mExternalCodeToFontName;
    /// <summary>
    /// <para>Key is case insensitive font name among all alternative names of font of the collection.</para>
    /// <para>Value is integer font code (index) in the font info collection.</para>
    /// </summary>
    private StringToIntDictionary mFontAltNameToItemIndex;

    private boolean mEmbedTrueTypeFonts;
    private boolean mEmbedSystemFonts;
    private boolean mSaveSubsetFonts;
}

