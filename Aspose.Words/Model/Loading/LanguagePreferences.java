// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/04/2018 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.LocaleClassifier;
import com.aspose.HashSetGeneric;


/// <summary>
/// Allows to set up language preferences.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/specify-load-options/">Specify Load Options</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Implements 'Set the Office Language Preferences' dialog in Word.
/// </remarks>
public class LanguagePreferences
{
    /// <summary>
    /// Adds additional editing language.
    /// </summary>
    public void addEditingLanguage(/*EditingLanguage*/int language)
    {
        mEditingLanguages.add(language);
    }

    /// <summary>
    /// Adds additional editing languages.
    /// </summary>
    /// <javaName>void addEditingLanguages(int[] languages)</javaName>
    public void addEditingLanguages(/*EditingLanguage*/int[] languages)
    {
        ArgumentUtil.checkNotNull(languages, "languages");

        for (/*EditingLanguage*/int language : languages)
            addEditingLanguage(language);
    }

    /// <summary>
    /// <para>Gets or sets default editing language.</para>
    /// <para>The default value is <see cref="EditingLanguage.EnglishUS"/>.</para>
    /// </summary>
    public /*EditingLanguage*/int getDefaultEditingLanguage() { return mDefaultEditingLanguage; }
    public void setDefaultEditingLanguage(/*EditingLanguage*/int value) { mDefaultEditingLanguage = value; }

    /// <summary>
    /// Gets or sets Test flag.
    /// </summary>
    /// <remarks>
    /// When it is <c>true</c>, languages are not applying to the defaults, avoiding to fail a large number of old tests.
    /// You should mark tests that don't use test mode as NonParallelizable.
    /// </remarks>
    static boolean getTestMode() { return gTestMode; }
    static void setTestMode(boolean value) { gTestMode = value; }

    /// <summary>
    /// Returns true, if collection of editing languages contains RTL (Right-To-Left) language.
    /// </summary>
    boolean getContainRtlLanguage() { return (containsHebrew() || containsArabic()); }

    /// <summary>
    /// Returns LocaleId depending on contained editing languages.
    /// </summary>
    /*EditingLanguage*/int getLocaleId()
    {
        return (LocaleClassifier.isLatin((int)mDefaultEditingLanguage) ||
            LocaleClassifier.isCyrillic((int)mDefaultEditingLanguage))
            ? mDefaultEditingLanguage
            : EditingLanguage.ENGLISH_US;
    }

    /// <summary>
    /// Returns LocaleIdFarEast depending on contained editing languages.
    /// </summary>
    /*EditingLanguage*/int getLocaleIdFarEast()
    {
        if (LocaleClassifier.isChineseOrJapanese((int)mDefaultEditingLanguage))
            return getLocaleIdFarEastByDefaultLanguage();

        /*EditingLanguage*/int editingLanguage = getLocaleIdFarEastByEditingLanguagesCollection();
        if (editingLanguage != EditingLanguage.ENGLISH_US)
            return editingLanguage;

        // If there are no any Chinese or Japanese languages set as default or added into editing languages,
        // then LocaleIdFarEast is the same as LocaleId.
        return getLocaleId();
    }

    /// <summary>
    /// Gets LocaleIdFarEast by default editing language.
    /// </summary>
    /*EditingLanguage*/int getLocaleIdFarEastByDefaultLanguage()
    {
        if (LocaleClassifier.isChineseSimplified((int)mDefaultEditingLanguage))
            return EditingLanguage.CHINESE_PRC;

        if (LocaleClassifier.isChineseTraditional((int)mDefaultEditingLanguage))
            return EditingLanguage.CHINESE_TAIWAN;

        if (mDefaultEditingLanguage == EditingLanguage.JAPANESE)
            return EditingLanguage.JAPANESE;

        if (mDefaultEditingLanguage == EditingLanguage.KOREAN)
            return EditingLanguage.KOREAN;

        return EditingLanguage.ENGLISH_US;
    }

    /// <summary>
    /// Gets LocaleIdFarEast by editing languages collection.
    /// </summary>
    private /*EditingLanguage*/int getLocaleIdFarEastByEditingLanguagesCollection()
    {
        if (containsChineseSimplified())
            return EditingLanguage.CHINESE_PRC;
        else if (containsChineseTraditional())
            return EditingLanguage.CHINESE_TAIWAN;
        else if (containsJapanese())
            return EditingLanguage.JAPANESE;

        return EditingLanguage.ENGLISH_US;
    }

    /// <summary>
    /// Returns LocaleIdBi depending on contained editing languages.
    /// </summary>
    /*EditingLanguage*/int getLocaleIdBi()
    {
        if (LocaleClassifier.isArabic((int)mDefaultEditingLanguage) || containsArabic())
            return EditingLanguage.ARABIC_SAUDI_ARABIA;
        else if (LocaleClassifier.isHebrew((int)mDefaultEditingLanguage) || containsHebrew())
            return EditingLanguage.HEBREW;
        else
            return EditingLanguage.ARABIC_SAUDI_ARABIA;
    }

    /// <summary>
    /// Returns true, if collection of editing languages contains Chinese Simplified language.
    /// </summary>
    private boolean containsChineseSimplified()
    {
        return (mEditingLanguages.contains(EditingLanguage.CHINESE_PRC) ||
            mEditingLanguages.contains(EditingLanguage.CHINESE_SINGAPORE));
    }

    /// <summary>
    /// Returns true, if collection of editing languages contains Chinese Traditional language.
    /// </summary>
    private boolean containsChineseTraditional()
    {
        return (mEditingLanguages.contains(EditingLanguage.CHINESE_TAIWAN) ||
            mEditingLanguages.contains(EditingLanguage.CHINESE_HONG_KONG) ||
            mEditingLanguages.contains(EditingLanguage.CHINESE_MACAO));
    }

    /// <summary>
    /// Returns true, if collection of editing languages contains Hebrew language.
    /// </summary>
    private boolean containsHebrew()
    {
        return mEditingLanguages.contains(EditingLanguage.HEBREW);
    }

    /// <summary>
    /// Returns true, if collection of editing languages contains Arabic language.
    /// </summary>
    private boolean containsArabic()
    {
        for (/*EditingLanguage*/int editingLanguage : (Iterable</*EditingLanguage*/Integer>) mEditingLanguages)
        {
            if (LocaleClassifier.isArabic((/*int*/Integer)editingLanguage))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Returns true, if collection of editing languages contains Japanese language.
    /// </summary>
    private boolean containsJapanese() { return mEditingLanguages.contains(EditingLanguage.JAPANESE); }

    private /*final*/ HashSetGeneric<Integer> mEditingLanguages = new HashSetGeneric<Integer>();
    private /*EditingLanguage*/int mDefaultEditingLanguage = EditingLanguage.ENGLISH_US;

    private static boolean gTestMode;
}

