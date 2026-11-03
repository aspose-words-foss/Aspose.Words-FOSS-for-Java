// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.1.2.4.12 lstStyle (Text List Styles)
/// This element specifies the list of styles associated with this body of text.
/// </summary>
class DmlTextListStyles implements Cloneable
{
    DmlTextListStyles()
    {
        for (int i = 0; i < mListLevelStyles.length; i++)
        {
            mListLevelStyles[i] = new DmlParagraphProperties();
            // List styles usually does not have explicitly specified list level.
            // That is why specify list level using inherited attr.
            DmlParagraphProperties levelProperties = new DmlParagraphProperties();
            mListLevelStyles[i].setParentProperties(levelProperties);
            levelProperties.setLevel(i);
            if (i > 0)
            {
                levelProperties.setParentProperties(mListLevelStyles[i - 1]);
                mListLevelStyles[i].getDefaultRunProperties().setParentProperties(
                    mListLevelStyles[i - 1].getDefaultRunProperties());
            }
        }

        // Create default properties and link properties to them
        mDefaultParagraphProperties = new DmlParagraphProperties();
        // For first level it is not required to set list level because it is zero by default.
        mListLevelStyles[0].setParentProperties(mDefaultParagraphProperties);
        mListLevelStyles[0].getDefaultRunProperties().setParentProperties(
            mDefaultParagraphProperties.getDefaultRunProperties());
    }

    DmlTextListStyles deepClone()
    {
        DmlTextListStyles lhs = (DmlTextListStyles)memberwiseClone();

        lhs.mDefaultParagraphProperties = mDefaultParagraphProperties.deepClone();

        lhs.mListLevelStyles = new DmlParagraphProperties[mListLevelStyles.length];
        for (int i = 0; i < mListLevelStyles.length; i++)
            lhs.mListLevelStyles[i] = mListLevelStyles[i].deepClone();

        return lhs;
    }

    /// <summary>
    /// Get text list style by level.
    /// </summary>
    /// <param name="level">Is 0-based.</param>
    DmlParagraphProperties getTextListStyle(int level)
    {
        return mListLevelStyles[level];
    }

    DmlParagraphProperties getDefaultParagraphProperties() { return mDefaultParagraphProperties; }

    DmlParagraphProperties getListLevel1Style() { return mListLevelStyles[0]; }

    DmlParagraphProperties getListLevel2Style() { return mListLevelStyles[1]; }

    DmlParagraphProperties getListLevel3Style() { return mListLevelStyles[2]; }

    DmlParagraphProperties getListLevel4Style() { return mListLevelStyles[3]; }

    DmlParagraphProperties getListLevel5Style() { return mListLevelStyles[4]; }

    DmlParagraphProperties getListLevel6Style() { return mListLevelStyles[5]; }

    DmlParagraphProperties getListLevel7Style() { return mListLevelStyles[6]; }

    DmlParagraphProperties getListLevel8Style() { return mListLevelStyles[7]; }

    DmlParagraphProperties getListLevel9Style() { return mListLevelStyles[8]; }

    /// <summary>
    /// Returns <c>true</c> if this property collection does not contain any non-empty styles.
    /// </summary>
    boolean isEmpty()
    {
        for (DmlParagraphProperties properties : mListLevelStyles)
        {
            if ((properties != null) && !properties.isEmpty())
                return false;
        }

        return true;
    }

    private DmlParagraphProperties mDefaultParagraphProperties;
    private DmlParagraphProperties[] mListLevelStyles = new DmlParagraphProperties[9];

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

