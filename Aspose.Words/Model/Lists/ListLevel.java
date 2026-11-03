// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ImageUtil;
import com.aspose.ms.System.msString;
import com.aspose.HashSetGeneric;
import com.aspose.Pair;
import com.aspose.ms.System.msNumber;
import com.aspose.MathUtil;
import com.aspose.ArgumentUtil;
import com.aspose.StringUtil;
import com.aspose.ConvertUtilCore;
import com.aspose.DrColor;


/// <summary>
/// Defines formatting for a list level.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-lists/">Working with Lists</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>You do not create objects of this class. List level objects are created automatically
/// when a list is created. You access <see cref="ListLevel"/> objects via the
/// <see cref="ListLevelCollection"/> collection.</p>
///
/// <p>Use the properties of <see cref="ListLevel"/> to specify list formatting
/// for individual list levels.</p>
///
/// </remarks>
/// <dev>
/// Not sure where lvlElt.tplc is stored in the DOC file.
/// Sounds like it is a template code for individual list levels.
/// VA: All tplc are stored in a separate table in a document.
///     They can be seen in binary going one after another.
///     Not sure how to locate them in the document structure though.
/// </dev>
public class ListLevel implements IRunAttrSource, Cloneable
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="document">Need to know the parent document so ListLevel.Font.Style can work.</param>
    /// <param name="levelNumber">Zero based level number.</param>
    ///
    /// <dev>
    /// It seems that ListLevel should also be IParaAttrSource but lets postpone for a while.
    /// </dev>
    ListLevel(DocumentBase document, int levelNumber)
    {
        mDocument = document;

        mLevelNumber = makeLevelNumberValid(levelNumber);

        // Default is to restart on the previous level.
        mRestartAfterLevel = levelNumber - 1;
    }

    /// <summary>
    /// Constructor to create "proxy" facade object.
    /// </summary>
    /// <remarks>
    /// AM. This object proxies all interaction to parent ListLevel object
    /// but additionally holds IRunAttrSource of object for which ListFormat was requested.
    /// This allow to obtain attributes which are not set directly in this ListLevel from parent AttrSource.
    /// </remarks>
    ListLevel(ListLevel parentListLevel, IRunAttrSource parentRunAttrs)
    {
        mDocument = parentListLevel.mDocument;
        mParentListLevel = parentListLevel;
        mParentRunAttrSource = parentRunAttrs;
    }

    /// <summary>
    /// Creates picture bullet shape for the current list level.
    /// </summary>
    /// <remarks>Please note, <see cref="NumberStyle"/> will be set to <see cref="NumberStyle.Bullet"/> and
    /// <see cref="NumberFormat"/> to "\xF0B7" to properly display picture bullet.
    /// Red cross image will be set as picture bullet image upon creating.
    /// To change it please use <see cref="ImageData"/>.</remarks>
    public void createPictureBullet() throws Exception
    {
        Shape shape = new Shape(mDocument, ShapeType.IMAGE);

        // Add picture bullet shape to the collection of picture bullets available in this document.
        mDocument.getLists().addPictureBullet(shape);

        // Set picture bullet Id.
        setPictureBulletId(mDocument.getLists().getPictureBulletCount() - 1);

        setNumberStyle(NumberStyle.BULLET);
        setNumberFormat("\uF0B7");

        // Set "NoImage.png" as picture bullet image.
        shape.getImageData().setImage(ImageUtil.getNoImageStream());
    }

    /// <summary>
    /// Deletes picture bullet for the current list level.
    /// </summary>
    /// <remarks>Default bullet will be shown after deleting.</remarks>
    public void deletePictureBullet()
    {
        if (hasPictureBullet())
        {
            setPictureBulletId(-1);

            // Apply default bullet parameters.
            setNumberStyle(NumberStyle.BULLET);
            setNumberFormat("\uF0B7");
            getRunPr().setName("Symbol");
        }
    }

    /// <summary>
    /// Reports the string representation of the <see cref="ListLevel"/> object for the specified index
    /// of the list item. Parameters specify the <see cref="Words.NumberStyle"/> and an optional format string
    /// used when <see cref="Aspose.Words.NumberStyle.Custom"/> is specified.
    /// </summary>
    /// <param name="index">The index of the list item (must be in the range from 1 to 32767).</param>
    /// <param name="numberStyle">
    /// The <see cref="Words.NumberStyle"/> of the <see cref="ListLevel"/> object.
    /// </param>
    /// <param name="customNumberStyleFormat">
    /// The optional format string used when <see cref="Aspose.Words.NumberStyle.Custom"/> is specified (e.g. "a, ç, ĝ, ...").
    /// In other cases, this parameter must be <c>null</c> or empty.
    /// </param>
    /// <returns>
    /// The string representation of the <see cref="ListLevel"/> object, described by the <paramref name="numberStyle"/> parameter and
    /// the <paramref name="customNumberStyleFormat"/> parameter, in the list item at the position determined by the <paramref name="index"/> parameter.
    /// </returns>
    /// <exception cref="ArgumentException">
    /// <paramref name="customNumberStyleFormat"/> is <c>null</c> or empty when the <paramref name="numberStyle"/> is custom.-or-
    /// <paramref name="customNumberStyleFormat"/> is not <c>null</c> or empty when the <paramref name="numberStyle"/> is non-custom.-or-
    /// <paramref name="customNumberStyleFormat"/> is invalid.
    /// </exception>
    /// <exception cref="ArgumentOutOfRangeException">index is out of range.</exception>
    public static String getEffectiveValue(int index, /*NumberStyle*/int numberStyle, String customNumberStyleFormat)
    {
        final int MIN_INDEX = 1;
        final int MAX_INDEX = 32767;
        final String CUSTOM_NUMBER_STYLE_FORMAT_ARG_NAME = "customNumberStyleFormat";

        if ((index < MIN_INDEX) || (index > MAX_INDEX))
        {
            throw new IllegalArgumentException(msString.format("Specified argument must be in the range from {0} to {1}.", MIN_INDEX, MAX_INDEX) + "\r\nParameter name: " + "index" + "\r\nParameter value: " + index);
        }

        if (msString.isNullOrEmpty(customNumberStyleFormat) && (numberStyle == NumberStyle.CUSTOM))
        {
            throw new IllegalArgumentException("Specified argument must not be null or empty if the number style is custom." + "\r\nParameter name: " + CUSTOM_NUMBER_STYLE_FORMAT_ARG_NAME);
        }

        if (!msString.isNullOrEmpty(customNumberStyleFormat) && (numberStyle != NumberStyle.CUSTOM))
        {
            throw new IllegalArgumentException("For this number style, the specified argument must be null or empty." + "\r\nParameter name: " + CUSTOM_NUMBER_STYLE_FORMAT_ARG_NAME);
        }

        return NumberConverter.numberToString(index, numberStyle, customNumberStyleFormat, true);
    }

    /// <summary>
    /// Makes a deep copy of the list level.
    /// Note that the paragraph style that might be linked to this level is not copied.
    /// </summary>
    ListLevel deepClone(DocumentBase dstDocument)
    {
        ListLevel lhs = (ListLevel)memberwiseClone();
        lhs.mDocument = dstDocument;
        lhs.mParaPr = AttrCollectionExtension.deepClone(mParaPr);
        lhs.mRunPr = AttrCollectionExtension.deepClone(mRunPr);
        lhs.mFont = null;
        return lhs;
    }

    /// <summary>
    /// Compares with the specified ListLevel.
    /// </summary>
    public boolean equals(ListLevel level)
    {
        return equalsCore(level, new HashSetGeneric<Pair>());
    }

    /// <summary>
    /// Compares with the specified <see cref="ListLevel"/>.
    /// </summary>
    /// <param name="level"><see cref="ListLevel"/> that will be compared with this <see cref="ListLevel"/>.</param>
    /// <param name="alreadyComparedLinkedStyles">HashSet for collecting already compared linked styles to avoid dead loops.</param>
    boolean equalsCore(ListLevel level, HashSetGeneric<Pair> alreadyComparedLinkedStyles)
    {
        // Verify list-level generic attributes.
        if (!genericEquals(level))
            return false;

        // Compare run properties.
        if (!this.getRunPr().equals(level.getRunPr(), Style.COMPARISON_IGNORABLE_KEYS))
            return false;

        // Compare paragraph properties.
        if (!this.getParaPr().equals(level.getParaPr(), Style.COMPARISON_IGNORABLE_KEYS))
            return false;

        // Compare formatting of linked styles.
        // WORDSNET-19497 Compare linked styles from existing ones.
        Style linkedStyle = mDocument.getStyles().getByIstd(getParaStyleIstd(), false);
        Style levelLinkedStyle = level.mDocument.getStyles().getByIstd(level.getParaStyleIstd(), false);

        if (!Style.areEqual(linkedStyle, levelLinkedStyle, alreadyComparedLinkedStyles))
            return false;

        return true;
    }

    /// <summary>
    /// Calculates hash code for this object.
    /// </summary>
    /// <dev>
    /// To be compatible with the <see cref="Equals(ListLevel)"/> method, only properties that affect visual
    /// representation of the list are included into the calculation. Object ID and similar properties should
    /// be ignored.
    /// </dev>
    /// <javaName>int hashCode()</javaName>
    public /*override*/ int hashCode()
    {
        int hashCode = /*enum.hashCode()=(int)value*/getNumberStyle();
        if (getNumberFormat() != null)
            hashCode = (hashCode * 397) ^ getNumberFormat().hashCode();
        hashCode = (hashCode * 397) ^ /*enum.hashCode()=(int)value*/getAlignment();
        hashCode = (hashCode * 397) ^ /*enum.hashCode()=(int)value*/getLabelCharacterCategory();
        hashCode = (hashCode * 397) ^ msNumber.booleanHashCode(isLegal());
        hashCode = (hashCode * 397) ^ (/*int.hashCode()*/getRestartAfterLevel());
        hashCode = (hashCode * 397) ^ msNumber.booleanHashCode(isRestartAfterLevelCustom());
        hashCode = (hashCode * 397) ^ /*enum.hashCode()=(int)value*/getTrailingCharacter();
        hashCode = (hashCode * 397) ^ (/*int.hashCode()*/getPictureBulletId());
        hashCode = (hashCode * 397) ^ (/*int.hashCode()*/getLevelNumber());
        hashCode = (hashCode * 397) ^ msNumber.doubleHashCode(getTabPosition());
        hashCode = (hashCode * 397) ^ msNumber.doubleHashCode(getNumberPosition());
        hashCode = (hashCode * 397) ^ msNumber.doubleHashCode(getTextPosition());

        hashCode = (hashCode * 397) ^ getRunPr().hashCode(Style.COMPARISON_IGNORABLE_KEYS);
        hashCode = (hashCode * 397) ^ getParaPr().hashCode(Style.COMPARISON_IGNORABLE_KEYS);

        if (getParaStyleIstd() != StyleIndex.NIL)
            hashCode = (hashCode * 397) ^ 1;

        return hashCode;
    }

    /// <summary>
    /// Sets start at value only if it is in the valid range. Use it during loading from a file.
    /// </summary>
    void setStartAtSafe(int value)
    {
        if (isStartAtValid(value))
            getGetInstance().mStartAt = value;
    }

    /// <summary>
    /// Sets a restart after level value safely. Use it during loading from a file.
    /// </summary>
    void setRestartAfterLevelSafe(int value)
    {
        if (isRestartAfterLevelValid(value))
            getGetInstance().mRestartAfterLevel = value;
    }

    /// <summary>
    /// Sets the number format value safely. Use it during loading from a file.
    /// </summary>
    void setNumberFormatSafe(String value)
    {
        if (isNumberFormatValid(value))
            getGetInstance().mNumberFormat = value;
    }

    /// <summary>
    /// Verifies string is valid for list level number format.
    /// </summary>
    private static boolean isNumberFormatValid(String value)
    {
        return isNumberFormatLengthValid(value) && isNumberFormatCharsValid(value);
    }

    /// <summary>
    /// Returns true if string contains only allowed for list level number format characters.
    /// </summary>
    private static boolean isNumberFormatCharsValid(String value)
    {
        return (value != null) && (value.indexOf('\uffff') < 0);
    }

    /// <summary>
    /// Returns true if string has allowed for list level number format length.
    /// </summary>
    private static boolean isNumberFormatLengthValid(String value)
    {
        return (value != null) && (value.length() <= MAX_NUMBER_FORMAT_LENGTH);
    }

    /// <summary>
    /// Indicates that given char is a placeholder character in <see cref="ListLevel.NumberFormat"/>.
    /// </summary>
    static boolean isPlaceholder(char c)
    {
        return (c >= '\u0000') && (c <= '\u0008');
    }

    /// <summary>
    /// Number format must not contain top-level placeholders.
    /// </summary>
    static boolean isPlaceholderValid(String numberFormat, int levelNumber)
    {
        for (int i = 0; i < numberFormat.length(); i++)
        {
            char c = numberFormat.charAt(i);
            if (PLACE_HOLDERS_STRING.indexOf(c) > levelNumber)
                return false;
        }

        return true;
    }

    static boolean isLegacySpaceValid(int legacySpace)
    {
        return (legacySpace >= -31680) && (legacySpace <= 31680);
    }

    /// <summary>
    /// Compares generic ListLevel attributes.
    /// </summary>
    private boolean genericEquals(ListLevel level)
    {
        return ((getNumberStyle() == level.getNumberStyle()) &&
                (msString.equals(getNumberFormat(), level.getNumberFormat())) &&
                (getAlignment() == level.getAlignment()) &&
                (getLabelCharacterCategory() == level.getLabelCharacterCategory()) &&
                (isLegal() == level.isLegal()) &&
                (getRestartAfterLevel() == level.getRestartAfterLevel()) &&
                (isRestartAfterLevelCustom() == level.isRestartAfterLevelCustom()) &&
                (getTrailingCharacter() == level.getTrailingCharacter()) &&
                (getPictureBulletId() == level.getPictureBulletId()) &&
                (getPictureBullet() == level.getPictureBullet()) &&
                (getLevelNumber() == level.getLevelNumber()) &&
                MathUtil.areEqual(getTabPosition(), level.getTabPosition()) &&
                MathUtil.areEqual(getNumberPosition(), level.getNumberPosition()) &&
                MathUtil.areEqual(getTextPosition(), level.getTextPosition()));
    }

    /// <summary>
    /// Returns or sets the starting number for this list level.
    /// </summary>
    /// <remarks>
    /// <p>Default value is 1.</p>
    /// </remarks>
    public int getStartAt() { return getGetInstance().mStartAt; }
    public void setStartAt(int value)
    {
        if (!isStartAtValid(value))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        getGetInstance().mStartAt = value;
    }

    /// <summary>
    /// Returns or sets the number style for this list level.
    /// </summary>
    public /*NumberStyle*/int getNumberStyle() { return getGetInstance().mNumberStyle; }
    public void setNumberStyle(/*NumberStyle*/int value)
    {
        if (value != NumberStyle.CUSTOM)
            getGetInstance().mCustomNumberStyle = "";
        getGetInstance().mNumberStyle = value;
    }

    /// <summary>
    /// Gets or sets the custom number style format for this list level. For example: "a, ç, ĝ, ...".
    /// </summary>
    public String getCustomNumberStyleFormat() { return getCustomNumberStyle(); }
    public void setCustomNumberStyleFormat(String value)
    {
        ArgumentUtil.checkNotNull(value, "CustomNumberStyleFormat");

        setCustomNumberStyle(value);
    }

    /// <summary>
    /// Gets or sets the custom number style for this list level. For example: a, ç, ĝ, ...
    /// </summary>
    String getCustomNumberStyle()
    {
        if (getGetInstance().getNumberStyle() == NumberStyle.CUSTOM)
            return getGetInstance().mCustomNumberStyle;
        else
            return "";
    }
    void setCustomNumberStyle(String value)
    {
        if (StringUtil.hasChars(value))
            getGetInstance().setNumberStyle(NumberStyle.CUSTOM);
        getGetInstance().mCustomNumberStyle = value;
    }

    /// <summary>
    /// Gets label character category to format the label with.
    /// </summary>
    /// <remarks>
    /// This is a simplification. Actually, list label can consist of characters with different categories.
    /// However, currently SpanList only supports one span per list label.
    /// TODO Above statement is not True. Span list handles labels consisting of multiple spans. And it does not use this method at all.
    /// </remarks>
    /*CharacterCategory*/int getLabelCharacterCategory()
    {
        switch (getNumberStyle())
        {
            case NumberStyle.AIUEO:
            case NumberStyle.AIUEO_HALF_WIDTH:
            case NumberStyle.IROHA:
            case NumberStyle.IROHA_HALF_WIDTH:
            case NumberStyle.KANJI:
            case NumberStyle.KANJI_DIGIT:
            case NumberStyle.KANJI_TRADITIONAL:
            case NumberStyle.KANJI_TRADITIONAL_2:
            // WORDSNET-5985 encircled numbers are displayed in Far Eastern font by MS Word when used in list labels.
            case NumberStyle.NUMBER_IN_CIRCLE:
                // Add other eastern styles as needed.
                return CharacterCategory.FAR_EAST;
            // WORDSNET-6256 Font specified for complex script is used for rendering Hebrew labels.
            case NumberStyle.HEBREW_1:
            case NumberStyle.HEBREW_2:
                return CharacterCategory.COMPLEX_SCRIPT;
            default:
                return CharacterCategory.ASCII;
        }
    }

    /// <summary>
    /// Returns or sets the number format for the list level.
    /// </summary>
    /// <remarks>
    /// <p>Among normal text characters, the string can contain placeholder characters \x0000 to \x0008
    /// representing the numbers from the corresponding list levels.</p>
    ///
    /// <p>For example, the string "\x0000.\x0001)" will generate a list label
    /// that looks something like "1.5)". The number "1" is the current number from
    /// the 1st list level, the number "5" is the current number from the 2nd list level.</p>
    ///
    /// <p>Null is not allowed, but an empty string meaning no number is valid.</p>
    /// </remarks>
    public String getNumberFormat() { return getGetInstance().mNumberFormat; }
    public void setNumberFormat(String value)
    {
        if (!isNumberFormatValid(value))
            throw new IllegalArgumentException("value");

        getGetInstance().mNumberFormat = value;
    }

    /// <summary>
    /// Gets or sets the justification of the actual number of the list item.
    /// </summary>
    /// <remarks>
    /// <p>The list label is justified relative to the <see cref="NumberPosition"/> property.</p>
    /// </remarks>
    public /*ListLevelAlignment*/int getAlignment() { return getGetInstance().mAlignment; }
    public void setAlignment(/*ListLevelAlignment*/int value) { getGetInstance().mAlignment = value; }

    /// <summary>
    /// True if the level turns all inherited numbers to Arabic, false if it preserves their number style.
    /// </summary>
    public boolean isLegal() { return getGetInstance().mIsLegal; }
    public void isLegal(boolean value) { getGetInstance().mIsLegal = value; }

    /// <summary>
    /// Sets or returns the list level that must appear before the specified list level restarts numbering.
    /// </summary>
    /// <remarks>
    /// <para>The value of -1 means the numbering will continue.</para>
    /// </remarks>
    public int getRestartAfterLevel() { return getGetInstance().mRestartAfterLevel; }
    public void setRestartAfterLevel(int value)
    {
        if (!isRestartAfterLevelValid(value))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        getGetInstance().mRestartAfterLevel = value;
    }

    /// <summary>
    /// Returns or sets the character inserted after the number for the list level.
    /// </summary>
    public /*ListTrailingCharacter*/int getTrailingCharacter() { return getGetInstance().mTrailingCharacter; }
    public void setTrailingCharacter(/*ListTrailingCharacter*/int value) { getGetInstance().mTrailingCharacter = value; }

    /// <summary>
    /// Specifies character formatting used for the list label.
    /// </summary>
    public Font getFont()
    {
        if (mFont == null)
            mFont = new Font(this, mDocument);

        return mFont;
    }

    /// <summary>
    /// Returns or sets the tab position (in points) for the list level.
    /// </summary>
    /// <remarks>
    /// <p>Has effect only when <see cref="TrailingCharacter"/> is a tab.</p>
    ///
    /// <seealso cref="NumberPosition"/>
    /// <seealso cref="TextPosition"/>
    /// </remarks>
    public double getTabPosition()
    {
        if ((getParaPr().getTabStops() != null) && (getParaPr().getTabStops().getCount() > 0))
            return getParaPr().getTabStops().get(0).getPosition();
        return 0.0;
    }
    public void setTabPosition(double value)
    {
        if (getParaPr().getTabStops() == null)
            getParaPr().setTabStops(new TabStopCollection());

        getParaPr().getTabStops().clear();

        getParaPr().getTabStops().add(value, TabAlignment.LIST, TabLeader.NONE);
    }

    /// <summary>
    /// Returns or sets the position (in points) of the number or bullet for the list level.
    /// </summary>
    /// <remarks>
    /// <p><see cref="NumberPosition"/> corresponds to LeftIndent plus FirstLineIndent of the paragraph.</p>
    ///
    /// <seealso cref="TextPosition"/>
    /// <seealso cref="TabPosition"/>
    /// </remarks>
    public double getNumberPosition() { return ConvertUtilCore.twipToPoint(getParaPr().getLeftIndent() + getParaPr().getFirstLineIndent()); }
    public void setNumberPosition(double value)
    {
        // Lets' say left indent is 100 and the user sets a new number position 80.
        // New left indent should become -20.
        getParaPr().setFirstLineIndent(ConvertUtilCore.pointToTwip(value) - getParaPr().getLeftIndent());
    }

    /// <summary>
    /// Returns or sets the position (in points) for the second line of wrapping text for the list level.
    /// </summary>
    /// <remarks>
    /// <p><see cref="TextPosition"/> corresponds to LeftIndent of the paragraph.</p>
    ///
    /// <seealso cref="NumberPosition"/>
    /// <seealso cref="TabPosition"/>
    /// </remarks>
    public double getTextPosition() { return ConvertUtilCore.twipToPoint(getParaPr().getLeftIndent()); }
    public void setTextPosition(double value)
    {
        // TextPosition corresponds to LeftIndent of the paragraph, but
        // NumberPosition is derived from LeftIndent and FirstLineIndent.
        // Therefore, changing TextPosition will change NumberPosition and we don't want that.
        // So remember the old NumberPosition and reapply it after LeftIndent was modified.
        double oldNumberPosition = getNumberPosition();
        getParaPr().setLeftIndent(ConvertUtilCore.pointToTwip(value));
        setNumberPosition(oldNumberPosition);
    }

    /// <summary>
    /// Gets or sets the paragraph style that is linked to this list level.
    /// </summary>
    /// <remarks>
    /// <p>This property is <c>null</c> when the list level is not linked to a paragraph style.
    /// This property can be set to <c>null</c>.</p>
    /// </remarks>
    public Style getLinkedStyle()
    {
        if (getParaStyleIstd() == StyleIndex.NIL)
            return null;
        else
            return mDocument.getStyles().getByIstd(getParaStyleIstd(), true);
    }
    public void setLinkedStyle(Style value)
    {
        if (value == null)
        {
            setParaStyleIstd(StyleIndex.NIL);
        }
        else
        {
            if (value.getType() != StyleType.PARAGRAPH)
                throw new IllegalArgumentException("The style must be a paragraph style.");

            if (value.getDocument() != mDocument)
                throw new IllegalArgumentException("The style must belong to this document.");

            setParaStyleIstd(value.getIstd());
        }
    }

    /// <summary>
    /// Returns image data of the picture bullet shape for the current list level.
    /// </summary>
    /// <remarks>
    /// If this level doesn't define picture bullet returns <c>null</c>.
    /// Before setting new image for non picture bullet shape, please use <see cref="CreatePictureBullet()"/> method first.
    /// </remarks>
    public ImageData getImageData()
    {
        if (getPictureBullet() != null)
            return getPictureBullet().getImageData();
        return null;
    }

    static boolean isStartAtValid(int value)
    {
        // This is what MS Word limits the value to.
        return ((value >= 0) && (value <= 32767));
    }

    static boolean isRestartAfterLevelValid(int value)
    {
        return ((value >= -1) && (value < MAX_LEVELS));
    }

    static boolean isLevelNumberValid(int levelNumber)
    {
        return levelNumber >= MIN_LEVEL && levelNumber < MAX_LEVELS;
    }

    static int makeLevelNumberValid(int levelNumber)
    {
        return isLevelNumberValid(levelNumber) ? levelNumber : MIN_LEVEL;
    }

    static boolean isNumberStyleValid(/*NumberStyle*/int numberStyle)
    {
        return (NumberStyle.ARABIC <= numberStyle && numberStyle <= NumberStyle.UPPERCASE_RUSSIAN)
               || (numberStyle == NumberStyle.NONE) || (numberStyle == NumberStyle.CUSTOM);
    }

    static boolean isAlignmentValid(/*ListLevelAlignment*/int alignment)
    {
        return (ListLevelAlignment.LEFT <= alignment) && (alignment <= ListLevelAlignment.RIGHT);
    }

    static boolean isTrailingCharacterValid(/*ListTrailingCharacter*/int trailingChar)
    {
        return (ListTrailingCharacter.TAB <= trailingChar) && (trailingChar <= ListTrailingCharacter.NOTHING);
    }

    /// <summary>
    /// Returns picture bullet shape for current list level.
    /// </summary>
    /// <remarks>
    /// Shape is returned unscaled. If this level doesn't define picture bullet returns null.
    /// </remarks>
    Shape getPictureBullet()
    {
        if ((getPictureBulletId() >= 0) && (getPictureBulletId() < mDocument.getLists().getPictureBulletCount()))
            return mDocument.getLists().getPictureBullet(getPictureBulletId());
        return null;
    }

    /// <summary>
    /// Istd of the paragraph style to be applied to list items of this level.
    /// </summary>
    int getParaStyleIstd() { return getGetInstance().mParaStyleIstd; }
    void setParaStyleIstd(int value) { getGetInstance().mParaStyleIstd = value; }

    /// <summary>
    /// Paragraph formatting for the list paragraph.
    /// Can contain any combination of three paragraph properties:
    /// left indents, first line left indents, and tabs.
    /// </summary>
    ParaPr getParaPr() { return getGetInstance().mParaPr; }

    /// <summary>
    /// Contains font formatting for the list number.
    /// Can contain any character properties (all of which affect all text for that level).
    /// </summary>
    RunPr getRunPr() { return getGetInstance().mRunPr; }

    /// <summary>
    /// Gets or sets the picture bullet id. The picture bullets themselves are stored in <see cref="ListCollection"/>.
    ///
    /// Negative value means there is no picture bullet.
    ///
    /// Setting this property does not validate that picture bullet with this id exists because
    /// picture bullets might be loaded after list levels. Therefore, this id might be invalid.
    /// </summary>
    int getPictureBulletId() { return getGetInstance().mPictureBulletId; }
    void setPictureBulletId(int value) { getGetInstance().mPictureBulletId = value; }

    /// <summary>
    /// RK It is better to check for null because it safely covers scenarios where bullet id is set,
    /// but bullet itself is not available.
    /// </summary>
    boolean hasPictureBullet() { return (getPictureBullet() != null); }

    /// <summary>
    /// Zero based 'nesting level' of this list level. Set only during create/load.
    /// </summary>
    int getLevelNumber() { return getGetInstance().mLevelNumber; }

    /// <summary>
    /// True when restart after level is not at one level higher.
    /// </summary>
    boolean isRestartAfterLevelCustom() { return getGetInstance().mRestartAfterLevel != getGetInstance().mLevelNumber - 1; }

    /// <summary>
    /// Do NOT use this when getting/calculating list formatting.
    /// Legacy formatting is converted into normal formatting in <see cref="Aspose.Words.Validation.DocumentValidator"/>.
    ///
    /// Specifies whether the legacy numbering properties present for this numbering level shall
    /// be used to format the numbering for any paragraph which references it.
    /// </summary>
    boolean getLegacy() { return getGetInstance().mLegacy; }
    void setLegacy(boolean value) { getGetInstance().mLegacy = value; }

    /// <summary>
    /// Do NOT use this when getting/calculating list formatting.
    /// This value only exists in DOC and we just preserve it.
    /// </summary>
    boolean getLegacyPrev() { return getGetInstance().mLegacyPrev; }
    void setLegacyPrev(boolean value) { getGetInstance().mLegacyPrev = value; }

    /// <summary>
    /// Do NOT use this when getting/calculating list formatting.
    /// This value only exists in DOC and we just preserve it.
    /// </summary>
    boolean getLegacyPrevSpace() { return getGetInstance().mLegacyPrevSpace; }
    void setLegacyPrevSpace(boolean value) { getGetInstance().mLegacyPrevSpace = value; }

    /// <summary>
    /// Do NOT use this when getting/calculating list formatting.
    /// Legacy formatting is converted into normal formatting in <see cref="Aspose.Words.Validation.DocumentValidator"/>.
    ///
    /// According to my experiments this value specifies the distance between the end of the list label text and
    /// beginning of the paragraph text.
    /// </summary>
    /// <dev>
    /// Specs say:
    /// Specifies the indentation which shall be applied between a legacy numbering symbol and the accompanying text
    /// of the associated paragraph in the document. This value is specified in twentieths of a point.
    /// </dev>
    int getLegacySpace() { return getGetInstance().mLegacySpace; }
    void setLegacySpace(int value) { getGetInstance().mLegacySpace = value; }

    /// <summary>
    /// Do NOT use this when getting/calculating list formatting.
    /// Legacy formatting is converted into normal formatting in <see cref="Aspose.Words.Validation.DocumentValidator"/>.
    ///
    /// According to my experiments this value specifies the distance between the beginning of the list label
    /// and beginning of the paragraph text.
    /// </summary>
    /// <dev>
    /// Specs say:
    /// Specifies the indentation which shall be applied to a legacy numbering symbol from the text margin of the document.
    /// This value is specified in twentieths of a point.
    /// </dev>
    int getLegacyIndent() { return getGetInstance().mLegacyIndent; }
    void setLegacyIndent(int value) { getGetInstance().mLegacyIndent = value; }

    /// <summary>
    /// Returns true if expected ListLevel equal actual ListLevel.
    /// </summary>
    static boolean areSameLegacyListLevels(ListLevel expected, ListLevel actual)
    {
        return
            ((expected.getLegacy() == actual.getLegacy()) &&
            (expected.getNumberStyle() == actual.getNumberStyle()) &&
            (msString.equals(expected.getNumberFormat(), actual.getNumberFormat())) &&
            (msString.equals(expected.getRunPr().getNameAscii(), actual.getRunPr().getNameAscii())) &&
            (msString.equals(expected.getRunPr().getNameOther(), actual.getRunPr().getNameOther())) &&
            (expected.getLegacyIndent() == actual.getLegacyIndent()) &&
            (expected.getLegacySpace() == actual.getLegacySpace()) &&
            (expected.getLegacyPrev() == actual.getLegacyPrev()) &&
            (expected.getStartAt() == actual.getStartAt()) &&
            (DrColor.equals(expected.getRunPr().getColor(), actual.getRunPr().getColor())));
    }

    /// <summary>
    /// Returns either parent ListLevel if this ListLevel is inherited or returns ListLevel itself.
    /// </summary>
    /// <remarks>
    /// AM. Inherited ListLevel acts like proxy. All set operation are proxied to parent ListLevel.
    /// Getters are also proxied to parent ListLevel except FetchInheritedRunAttr which gets attribute value
    /// from parent attribute source if attribute is missing in ListLevel.
    /// </remarks>
    private ListLevel getGetInstance()
    {
        if (mParentListLevel != null)
        {
            return mParentListLevel;
        }

        return this;
    }

    /// <summary>
    /// I'm not fully sure, but looks like corresponds to WordML lvlElt.tentative.
    /// I think it is used by MS Word to indicate levels that are not yet used in the document.
    /// I don't maintain this value.
    /// </summary>
    boolean IsTentative;

    private DocumentBase mDocument;
    private /*final*/ int mLevelNumber;
    private int mStartAt = 1;
    private /*NumberStyle*/int mNumberStyle;
    private String mCustomNumberStyle = "";
    private String mNumberFormat = "";
    private /*ListLevelAlignment*/int mAlignment;
    private boolean mIsLegal;
    private int mRestartAfterLevel;
    private /*ListTrailingCharacter*/int mTrailingCharacter;
    private ParaPr mParaPr = new ParaPr();
    private RunPr mRunPr = new RunPr();
    private int mPictureBulletId = -1;

    private boolean mLegacy;
    private int mLegacySpace;
    private int mLegacyIndent;
    private boolean mLegacyPrevSpace;
    private boolean mLegacyPrev;
    // WORDSNET-3189 Added the default value, otherwise it was referring to the Normal style.
    private int mParaStyleIstd = StyleIndex.NIL;

    /// <summary>
    /// This is a facade object. Null until the Font property is accessed.
    /// </summary>
    private Font mFont;

    private /*final*/ IRunAttrSource mParentRunAttrSource;

    private /*final*/ ListLevel mParentListLevel;

    /// <summary>
    /// MS Word seems to allow 31 chars for number format only.
    /// </summary>
    static final int MAX_NUMBER_FORMAT_LENGTH = 31;
    /// <summary>
    /// Minimum list level.
    /// </summary>
    static final int MIN_LEVEL = 0;
    /// <summary>
    /// Maximum number of list levels. Valid value of list level
    /// is from MinLevel to (MaxLevels - 1) inclusively.
    /// </summary>
    static final int MAX_LEVELS = 9;
    /// <summary>
    /// Left indent which MS Word adds for every list level.
    /// </summary>
    static final int LEFT_INDENT = 36;
    /// <summary>
    /// List number hanging which MS Word uses for lists by default.
    /// </summary>
    static final int DEFAULT_NUMBER_HANGING = 18;

    private static final String PLACE_HOLDERS_STRING = "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\u0008";

    //>>>>>>>> #region  IRunAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key)
    {
        return ((IRunAttrSource)this).getDirectRunAttr(key, RevisionsView.ORIGINAL);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key, /*RevisionsView*/int revisionsView)
    {
        // Use RunPr instead of mRunPr for inheritance worked.
        return getRunPr().get(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/fetchInheritedRunAttr(int key)
    {
        if (mParentRunAttrSource == null)
            return RunPr.getDefaultAttr(key);

        // AM. According to RK comment in ListLabel.FetchInheritedRunAttr don't take decoration from paragraph.
        // WORDSNET-15727 ListFormat.ListLevel.Font does not report bold attribute properly.
        // Bold or Italic attributes have to be get from Paragraph if ListLevel is not bullet.
        if (((key == FontAttr.BOLD) || (key == FontAttr.ITALIC)) && (getNumberStyle() == NumberStyle.BULLET))
            return RunPr.getDefaultAttr(key);

        return InlineHelper.fetchAttr(mParentRunAttrSource, key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/setRunAttr(int key, Object value)
    {
        getRunPr().setAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/removeRunAttr(int key)
    {
        getRunPr().remove(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/clearRunAttrs()
    {
        getRunPr().clear();
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }

    //<<<<<<<< #endregion 
}

