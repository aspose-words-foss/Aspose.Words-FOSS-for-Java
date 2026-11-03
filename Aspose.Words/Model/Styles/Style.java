// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.Debug;
import com.aspose.ms.System.StringSplitOptions;
import com.aspose.ArgumentUtil;
import com.aspose.HashSetGeneric;
import com.aspose.Pair;
import com.aspose.ms.System.msObject;


/// <summary>
/// Represents a single built-in or user-defined style.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-styles-and-themes/">Working with Styles and Themes</a> documentation article.</para>
/// </summary>
public class Style implements IParaAttrSource, IRunAttrSource, Cloneable
{
    /// <summary>
    /// Factory method.
    /// </summary>
    static Style create(/*StyleType*/int styleType)
    {
        switch (styleType)
        {
            case StyleType.CHARACTER:
            case StyleType.PARAGRAPH:
            case StyleType.LIST:
                return new Style(styleType);
            case StyleType.TABLE:
                return new TableStyle();
            default:
                throw new IllegalStateException("Unknown style type.");
        }
    }

    /// <summary>
    /// Factory method.
    /// </summary>
    static Style create(/*StyleType*/int styleType, int istd, /*StyleIdentifier*/int sti, String name)
    {
        Style style = create(styleType);
        style.mIstd = istd;
        style.mSti = sti;
        style.mName = name;
        style.mNextIstd = istd;
        return style;
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    protected Style(/*StyleType*/int styleType)
    {
        mType = styleType;
    }

    /// <summary>
    /// Gets or sets the name of the style.
    /// </summary>
    /// <remarks>
    /// <p>Can not be empty string.</p>
    /// <p>If there already is a style with such name in the collection, then this style will override it. All affected nodes will reference new style.</p>
    /// </remarks>
    public String getName() { return mName; }
    public void setName(String value)
    {
        if (!StringUtil.hasChars(value))
            throw new IllegalArgumentException("Style name can not be empty.");

        if (msString.equals(value, mName)) // if renaming to same name, skip unnecessary operations.
            return;

        Debug.assertTrue(getStyles() != null);

        // Check if old or new name value belongs to built-in styles.
        // Some istd are reserved for built-in styles, if the new name is reserved for built-in style,
        // or existing style was built-in, we have to tweak related
        // style properties including sti and istd.

        Style oldStyle = mStyles.getByName(value, false);
        /*StyleIdentifier*/int sti = StyleIndex.builtInStyleNameEnglish(value);
        if (sti != StyleIdentifier.USER) // 1) new name = built-in. we have to make a built-in style out of original.
        {
            StyleIndex.convertToBuiltInStyle(this, sti, oldStyle, true);
        }
        else if (getBuiltIn()) // 2) old name = built-in style, new name = regular.
        {
            StyleIndex.convertToRegularStyle(this, oldStyle);
        }
        else
        {
            if (oldStyle != null) // if style with such name exists, we override old style with new one.
            {
                // Todo DD: Would be nice to refactor/rework this along with the code in StyleCollection.AddCopyCore
                if ((getType() != StyleType.CHARACTER) && (mParaPr.getListId() > 0))
                {
                    List list = getDocument().getLists().fetchListByListId(mParaPr.getListId());
                    for (ListLevel level : list.getListLevels())
                        if (level.getParaStyleIstd() == getIstd())
                            level.setParaStyleIstd(oldStyle.getIstd());
                }

                setIstd(oldStyle.getIstd(), true); // use istd of old style for new style, update collection maps.
            }
        }

        setNameCore(value, true);
    }

    /// <summary>
    /// Get the name of the style, including aliases (like "Heading 1,Heading 1#").
    /// </summary>
    String getNameWithAliases()
    {
        String aliases = this.getStyles().getAliases(this, false);
        if (!StringUtil.hasChars(aliases))
            return this.getName();
        return msString.format("{0},{1}", this.getName(), aliases);
    }

    /// <summary>
    /// Gets style aliases.
    /// </summary>
    String[] getAliasesInternal()
    {
        return msString.split(this.getStyles().getAliases(this, false), new char[] { ',' }, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
    }

    /// <summary>
    /// Sets style name and updates mapping in StyleCollection.
    /// Used in renaming through <see cref="Name"/> setter and <see cref="StyleCollection.AddCopy(Style)"/> methods.
    /// </summary>
    void setNameCore(String value, boolean updateStyleCollection)
    {
        if (updateStyleCollection)
            mStyles.updateNameMap(this, mName, value);

        mName = value;
    }

    /// <summary>
    /// Left for document construction purposes, use with care.
    /// </summary>
    void setNameCore(String value)
    {
        setNameCore(value, false);
    }

    /// <summary>
    /// Adds an alias to this style. For internal use.
    /// </summary>
    void addAlias(String alias)
    {
        mStyles.addAlias(this, alias);
    }

    /// <summary>
    /// Gets the locale independent style identifier for a built-in style.
    /// </summary>
    /// <remarks>
    /// <para>For user defined (custom) styles, this property returns <see cref="Words.StyleIdentifier.User"/>.</para>
    /// <seealso cref="Name"/>
    /// </remarks>
    public /*StyleIdentifier*/int getStyleIdentifier() { return mSti; }

    /// <summary>
    /// Gets the ID of the style from which this style is cloned.
    /// It is used in WORDSNET-21097, to revert Hyperlink style to the default ID inside TOC.
    /// </summary>
    /*StyleIdentifier*/int getClonedFromStyleIdentifier() { return mClonedFromSti; }

    /// <summary>
    /// Gets all aliases of this style. If style has no aliases then empty array of string is returned.
    /// </summary>
    
    public String[] getAliases()
    {
        return getAliasesInternal();
    }

    /// <summary>
    /// True when the style is one of the built-in Heading styles.
    /// </summary>
    public boolean isHeading()
    {
        return ((getStyleIdentifier() >= StyleIdentifier.HEADING_1) &&
            (getStyleIdentifier() <= StyleIdentifier.HEADING_9));
    }

    /// <summary>
    /// Gets the style type (paragraph or character).
    /// </summary>
    
    public /*StyleType*/int getType() { return mType; }

    /// <summary>
    /// Gets the owner document.
    /// </summary>
    public DocumentBase getDocument() { return (mStyles != null) ? mStyles.getDocument() : null; }

    /// <summary>
    /// Gets/sets the name of the <see cref="Style"/> linked to this one. Returns empty string if no styles are linked.
    /// </summary>
    /// <remarks>
    /// <para>It is only allowed to link the paragraph style to the character style and vice versa.</para>
    /// <para>Setting LinkedStyleName for the current style automatically leads to setting LinkedStyleName for the linked style.</para>
    /// <para>Assigning the empty string is equivalent to unlinking the previously linked style.</para>
    /// </remarks>
    public String getLinkedStyleName()
    {
        String result = "";
        if (getLinkedIstd() != StyleIndex.NIL)
        {
            Style style = getDocument().getStyles().getByIstd(getLinkedIstd(), false);
            Debug.assertTrue(style != null);
            if (style != null)
                result = style.getName();
        }

        return result;
    }
    public void setLinkedStyleName(String value)
    {
        if (value == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "value");

        if (msString.equals(value, ""))
        {
            updateLinkedStyleLinks(null);
            setLinkedIstd(StyleIndex.NIL);
        }
        else
        {
            Style proposedLinkedStyle = mStyles.fetchByName(value);

            if (!((mType == StyleType.PARAGRAPH) && (proposedLinkedStyle.getType() == StyleType.CHARACTER) ||
                (mType == StyleType.CHARACTER) && (proposedLinkedStyle.getType() == StyleType.PARAGRAPH)))
                throw new IllegalStateException("Only the paragraph and the character styles can be linked.");

            if ((proposedLinkedStyle.getStyleIdentifier() == StyleIdentifier.DEFAULT_PARAGRAPH_FONT) ||
                (getStyleIdentifier() == StyleIdentifier.DEFAULT_PARAGRAPH_FONT))
                throw new IllegalStateException("Default paragraph font cannot be linked style.");

            updateLinkedStyleLinks(proposedLinkedStyle);
            setLinkedIstd(proposedLinkedStyle.getIstd());
        }
    }

    /// <summary>
    /// Gets/sets the name of the style this style is based on.
    /// </summary>
    /// <remarks>
    /// This will be an empty string if the style is not based on any other style and it can be set
    /// to an empty string.
    /// </remarks>
    public String getBaseStyleName()
    {
        Style baseStyle = getBaseStyle();
        return (baseStyle != null) ? baseStyle.getName() : "";
    }
    public void setBaseStyleName(String value)
    {
        if (value == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "value");

        // andrnosk: WORDSNET-7403 Add StyleType.Table to this condition, because BaseStyleName is allowed for table styles.
        if ((mType != StyleType.CHARACTER) && (mType != StyleType.PARAGRAPH) && (mType != StyleType.TABLE))
            throw new IllegalStateException("This property is only allowed for character, paragraph and table styles.");

        if ((mSti == StyleIdentifier.NORMAL) || (mSti == StyleIdentifier.DEFAULT_PARAGRAPH_FONT))
            throw new IllegalStateException("The built-in styles Normal and Default Paragraph Font cannot be based on any style.");

        if ("".equals(value))
        {
            mBasedOnIstd = StyleIndex.NIL;
        }
        else
        {
            Style proposedBaseStyle = mStyles.fetchByName(value);
            verifySuitableAsBaseStyle(proposedBaseStyle, true);
            mBasedOnIstd = proposedBaseStyle.getIstd();
        }
    }

    /// <summary>
    /// Gets/sets the name of the style to be applied automatically to a new paragraph inserted after a
    /// paragraph formatted with the specified style.
    /// </summary>
    /// <remarks>
    /// This property is not used by Aspose.Words. The next paragraph style will only
    /// be applied automatically when you edit the document in MS Word.
    /// </remarks>
    public String getNextParagraphStyleName()
    {
        Style nextStyle = getNextStyle();
        return (nextStyle != null) ? nextStyle.getName() : "";
    }
    public void setNextParagraphStyleName(String value)
    {
        if (mType != StyleType.PARAGRAPH)
            throw new IllegalStateException("This property is allowed only for paragraph styles.");

        ArgumentUtil.checkHasChars(value, "value");

        Style style = mStyles.fetchByName(value);
        mNextIstd = style.getIstd();
    }

    /// <summary>
    /// True if this style is one of the built-in styles in MS Word.
    /// </summary>
    public boolean getBuiltIn() { return (mSti != StyleIdentifier.USER); }

    /// <summary>
    /// Gets the character formatting of the style.
    /// </summary>
    /// <remarks>
    /// <para>For list styles this property returns <c>null</c>.</para>
    /// </remarks>
    public Font getFont()
    {
        if (mType == StyleType.LIST)
            return null;

        if (mFontCache == null)
            mFontCache = new Font(this, mStyles.getDocument());
        return mFontCache;
    }

    /// <summary>
    /// Gets the paragraph formatting of the style.
    /// </summary>
    /// <remarks>
    /// <para>For character and list styles this property returns <c>null</c>.</para>
    /// </remarks>
    public ParagraphFormat getParagraphFormat()
    {
        if ((mType == StyleType.CHARACTER) || (mType == StyleType.LIST))
            return null;

        if (mParagraphFormatCache == null)
            mParagraphFormatCache = new ParagraphFormat(this, getStyles());
        return mParagraphFormatCache;
    }

    /// <summary>
    /// Gets/sets whether the style hides from the Styles gallery and from the Styles task pane.
    /// </summary>
    public boolean getSemiHidden() { return mSemiHidden; }
    public void setSemiHidden(boolean value) { mSemiHidden = value; }

    /// <summary>
    /// Gets/sets whether the style used in the current document unhides from the Styles gallery and from the Styles task pane.
    /// True when the used style should be shown in the Styles gallery.
    /// </summary>
    public boolean getUnhideWhenUsed() { return mUnhideWhenUsed; }
    public void setUnhideWhenUsed(boolean value) { mUnhideWhenUsed = value; }

    /// <summary>
    /// Gets/sets the integer value that represents the priority for sorting the styles in the Styles task pane.
    /// </summary>
    public int getPriority() { return mUIPriority; }
    public void setPriority(int value) { mUIPriority = value; }

    /// <summary>
    /// Gets the frame related formatting of the style.
    /// </summary>
    /// <remarks>
    /// <para>For character and list styles this property returns <c>null</c>.</para>
    /// </remarks>
    /// TODO AM, DD: Exposing nulls in public API is bad, especially when we say in Paragraph class that FrameFormat is always created.
    ///  This is a big inconsistency. Lets keep this internal until we fix this somehow.
    FrameFormat getFrameFormat()
    {
        if ((mType == StyleType.CHARACTER) || (mType == StyleType.LIST))
            return null;

        if (mFrameFormatCache == null)
            mFrameFormatCache = new FrameFormat(this);
        return mFrameFormatCache;
    }

    /// <summary>
    /// Gets the list that defines formatting of this list style.
    /// </summary>
    /// <remarks>
    /// <para>This property is only valid for list styles.
    /// For other style types this property returns <c>null</c>.</para>
    /// </remarks>
    public List getList()
    {
        if (mType != StyleType.LIST)
            return null;

        return (getDocument() != null) ? getDocument().getLists().fetchListByListId(mParaPr.getListId()) : null;
    }

    /// <summary>
    /// Provides access to the list formatting properties of a paragraph style.
    /// </summary>
    /// <remarks>
    /// <para>This property is only valid for paragraph styles.
    /// For other style types this property returns <c>null</c>.</para>
    /// </remarks>
    public ListFormat getListFormat()
    {
        if (mType != StyleType.PARAGRAPH)
            return null;

        if (getDocument() == null)
            return null;

        if (mListFormatCache == null)
            mListFormatCache = new ListFormat(this, this, getDocument().getLists());

        return mListFormatCache;
    }

    /// <summary>
    /// Specifies whether this style is shown in the Quick Style gallery inside MS Word UI.
    /// </summary>
    public boolean isQuickStyle() { return mIsQuickStyle; }
    public void isQuickStyle(boolean value) { mIsQuickStyle = value; }

    /// <summary>
    /// Specifies whether this style is automatically redefined based on the appropriate value.
    /// </summary>
    /// <remarks>
    /// <para>If the property value is set to true, MS Word automatically redefines the current style when
    /// the appropriate paragraph formatting has been changed.</para>
    /// <para>AutomaticallyUpdate property is applicable to paragraph styles only.</para>
    /// <para>The default value is <c>false</c>.</para>
    /// </remarks>
    public boolean getAutomaticallyUpdate() { return mAutomaticallyUpdate; }
    public void setAutomaticallyUpdate(boolean value) { mAutomaticallyUpdate = value; }

    /// <summary>
    /// Specifies whether this style is locked.
    /// </summary>
    public boolean getLocked() { return mLocked; }
    public void setLocked(boolean value) { mLocked = value; }

    /// <summary>
    /// This is used to identify the style in the model.
    /// Every style has a unique istd and objects refer to styles using istd.
    ///
    /// Istd historically came from the DOC format where it means "index of style definition".
    /// </summary>
    int getIstd() { return mIstd; }

    /// <summary>
    /// Style index of the based on style. Can be StyleIndex.Nil that means the style is not based on another style.
    /// Setting this property corrects any circular references by setting the offending reference to nil style.
    /// </summary>
    int getBasedOnIstd()
    {
        // WORDSNET-23283 DefaultParagraphFont style cannot be based on another style.
        return (getIstd() == StyleIndex.DEFAULT_PARAGRAPH_FONT)
            ? StyleIndex.NIL
            : mBasedOnIstd;
    }
    void setBasedOnIstd(int value)
    {
        mBasedOnIstd = value;
        fixUpShallowCircularReferences();
    }

    /// <summary>
    /// Style index of the next paragraph style. By default same as the istd of this style.
    /// Not expected to be StyleIndex.Nil.
    /// Setting this property performs no validation.
    /// </summary>
    int getNextIstd() { return mNextIstd; }
    void setNextIstd(int value) { mNextIstd = value; }

    /// <summary>
    /// Style index of the linked style. A paragraph style can link a character style and vice versa.
    /// </summary>
    int getLinkedIstd() { return mLinkedIstd; }
    void setLinkedIstd(int value)
    {
        mLinkedIstd = value;
        fixUpShallowCircularReferences();
    }

    /// <summary>
    /// Rsid of the style.
    /// </summary>
    int getRsid() { return mRsid; }
    void setRsid(int value) { mRsid = value; }

    /// <summary>
    /// Hidden from UI.
    /// RK I cannot find how to set this in MS Word.
    /// </summary>
    boolean getHidden() { return mHidden; }
    void setHidden(boolean value) { mHidden = value; }

    /// <summary>
    /// HTML Threading compose style.
    /// </summary>
    boolean getPersonalCompose() { return mPersonalCompose; }
    void setPersonalCompose(boolean value) { mPersonalCompose = value; }

    /// <summary>
    /// HTML Threading reply style.
    /// </summary>
    boolean getPersonalReply() { return mPersonalReply; }
    void setPersonalReply(boolean value) { mPersonalReply = value; }

    /// <summary>
    /// HTML Threading - another user's personal style.
    /// </summary>
    boolean getPersonal() { return mPersonal; }
    void setPersonal(boolean value) { mPersonal = value; }

    /// <summary>
    /// RK DOC use only. Not needed in other formats. Not critical in DOC too, but needed
    /// to make low-level binary writer tests pass (preserves a low-level binary flag).
    /// </summary>
    boolean getLidsSet() { return mLidsSet; }
    void setLidsSet(boolean value) { mLidsSet = value; }

    /// <summary>
    /// Indicates the style is top level paragraph style.
    /// Used in DocumentValidator when expanding document defaults.
    /// </summary>
    boolean isTopLevelParaStyle() { return (getType() == StyleType.PARAGRAPH) && (getBasedOnIstd() == StyleIndex.NIL); }

    /// <summary>
    /// Gets the collection of styles this style belongs to.
    /// </summary>
    public StyleCollection getStyles() { return mStyles; }

    /// <summary>
    /// Removes the specified style from the document.
    /// </summary>
    /// <remarks>
    /// Style removal has following effects on the document model:
    /// <list type="bullet">
    /// <item>All references to the style are removed from corresponding paragraphs, runs and tables.</item>
    /// <item>If base style is removed its formatting is moved to child styles.</item>
    /// <item>If style to be deleted has a linked style, then both of these are deleted.</item>
    /// </list>
    /// </remarks>
    public void remove()
    {
        getStyles().remove(getName());
    }

    ParaPr getParaPr() { return mParaPr; }
    void setParaPr(ParaPr value) { mParaPr = value; }

    RunPr getRunPr() { return mRunPr; }
    void setRunPr(RunPr value) { mRunPr = value; }

    boolean hasRevisions() { return getRunPr().hasFormatRevision() || getParaPr().hasFormatRevision(); }

    /// <summary>
    /// Returns true if paragraph and run properties have not attribute in the formatting revision.
    /// </summary>
    boolean hasEmptyFormatRevision() { return getRunPr().hasEmptyFormatRevision() || getParaPr().hasEmptyFormatRevision(); }

    /// <summary>
    /// Makes a deep copy of the style.
    /// Note:
    /// 1. The returned object's parent Styles collection is null.
    /// 2. The returned object is valid in the context of the original document only.
    /// To use a style in a different document, you must call ImportStyle.
    ///
    /// Note that list definitions that are referenced by the style are not cloned.
    /// </summary>
    /*virtual*/ Style deepClone()
    {
        Style lhs = (Style)memberwiseClone();

        // RK See comments for RawExtraBytes2 why we clone it by value.

        ParaPr paraPr = AttrCollectionExtension.deepClone(mParaPr);
        lhs.mParaPr = paraPr;

        RunPr runPr = AttrCollectionExtension.deepClone(mRunPr);
        lhs.mRunPr = runPr;

        lhs.mStyles = null;

        lhs.clearCaches();

        // WORDSNET-21097 Keep track of the source style ID. It is used to revert Hyperlink style to the default ID inside TOC.
        lhs.mClonedFromSti = this.mSti;

        return lhs;
    }

    /// <summary>
    /// Clears caches.
    /// </summary>
    void clearCaches()
    {
        mFontCache = null;
        mParagraphFormatCache = null;
        mFrameFormatCache = null;
        mListFormatCache = null;
    }

    /// <summary>
    /// Compares with the specified style.
    /// Styles Istds are compared for built-in styles only.
    /// Styles defaults are not included in comparison.
    /// Base style, linked style and next paragraph style are recursively compared.
    /// </summary>
    public boolean equals(Style style)
    {
        return equals(style, new HashSetGeneric<Pair>());
    }

    /// <summary>
    /// Compares with the specified style considering base, linked and next paragraph styles.
    /// Uses HashSet to check already compared styles to avoid dead loops in linked and next styles.
    /// </summary>
    boolean equals(Style style, HashSetGeneric<Pair> alreadyCompared)
    {
        if (style == null)
            return false;

        // Same instance.
        if (msObject.referenceEquals(this, style))
            return true;

        if (alreadyCompared.contains(new Pair(this, style)))
            return true;

        if (!equalsCore(style, alreadyCompared))
            return false;

        // Verifying base styles.
        if (!areEqual(getBaseStyle(), style.getBaseStyle(), alreadyCompared))
            return false;

        // Verifying linked styles.
        if (!areEqual(getLinkedStyle(), style.getLinkedStyle(), alreadyCompared))
            return false;

        // Verifying next styles.
        if (!areEqual(getNextStyle(), style.getNextStyle(), alreadyCompared))
            return false;

        return true;
    }

    /// <summary>
    /// Compares two styles considering base, linked and next paragraph styles.
    /// Uses HashSet to check already compared styles to avoid dead loops in linked and next styles.
    /// </summary>
    static boolean areEqual(Style styleA, Style styleB, HashSetGeneric<Pair> alreadyCompared)
    {
        if (ArgumentUtil.oneIsNull(styleA, styleB))
            return false;

        if (ArgumentUtil.bothAreNull(styleA, styleB))
            return true;

        return styleA.equals(styleB, alreadyCompared);
    }

    /// <summary>
    /// Sets the owner collection of styles.
    /// </summary>
    /// <param name="styles"></param>
    void setStyles(StyleCollection styles)
    {
        mStyles = styles;
    }

    /// <summary>
    /// Sets the style identifier. Style identifier does not change under normal circumstances.
    /// You should set identifier only during load or import. Make sure you know what you are doing.
    /// The type of the style should be already set before calling this method.
    /// </summary>
    void setIstd(int istd)
    {
        setIstd(istd, false);
    }

    /// <summary>
    /// Set Istd and update <see cref="StyleCollection"/> mappings if required.
    /// </summary>
    /// <param name="istd"></param>
    /// <param name="updateStyleCollection">Set to true for styles that are attached to collection, this is needed to update mapping in the collection itself.
    /// </param>
    void setIstd(int istd, boolean updateStyleCollection)
    {
        if (updateStyleCollection)
            mStyles.updateIstdMap(this, getIstd(), istd);

        mIstd = istd;
        fixUpShallowCircularReferences();

        // Unfortunately we have a bit of duplication. The style identifier is also stored in the
        // paragraph or character properties appropriately. It could be a good idea to get rid
        // of the separate istd field, but I'm not sure how it will work with table styles.
        switch (mType)
        {
            case StyleType.PARAGRAPH:
            case StyleType.LIST:
                mParaPr.setIstd(istd);
                break;
            case StyleType.CHARACTER:
                mRunPr.setIstd(istd);
                break;
            default:
                // Do nothing.
                break;
        }
    }

    void setStyleIdentifier(/*StyleIdentifier*/int sti)
    {
        setStyleIdentifier(sti, false);
    }

    /// <param name="sti"></param>
    /// <param name="updateStyleCollection">
    /// Set to true for styles that are attached to collection, this is needed to update mapping in the collection itself.
    /// </param>
    void setStyleIdentifier(/*StyleIdentifier*/int sti, boolean updateStyleCollection)
    {
        if (updateStyleCollection)
            mStyles.updateStiMap(this, getStyleIdentifier(), sti);

        mSti = sti;
    }

    /// <summary>
    /// Gets the based on style. Returns <c>null</c> if it is set to StyleIndex.Nil.
    /// </summary>
    Style getBaseStyle()
    {
        fixUpShallowCircularReferences();

        // WORDSNET-23283 DefaultParagraphFont style cannot be based on another style.
        if (getIstd() == StyleIndex.DEFAULT_PARAGRAPH_FONT)
            return null;

        if (mBasedOnIstd == StyleIndex.NIL)
            return null;

        // WORDSNET-27108 Check that BaseStyle exists in the style collection.
        if (mBasedOnIstd > StyleCollection.MAX_FIXED_ISTD)
            if (mStyles.getByIstd(mBasedOnIstd, false) == null)
                return null;

        return mStyles.fetchByIstd(mBasedOnIstd, StyleIndex.NIL);
    }

    /// <summary>
    /// Gets the next paragraph style. Returns <c>null</c> if it is set to StyleIndex.Nil.
    /// </summary>
    Style getNextStyle()
    {
        // RESILIENCY WORDSNET-5736 The problem occurred because mNextIstd refers to non-existing style
        // In this case we tried to get the default style, but since we used StyleIndex.Nil as default we got nothing.
        // Fixed by using the current style istd as default style.
        return (mNextIstd != StyleIndex.NIL) ? mStyles.fetchByIstd(mNextIstd, mIstd) : null;
    }

    /// <summary>
    /// Gets the linked paragraph style. Returns <c>null</c> if it is set to StyleIndex.Nil.
    /// </summary>
    Style getLinkedStyle()
    {
        fixUpShallowCircularReferences();
        return (mLinkedIstd != StyleIndex.NIL) ? mStyles.fetchByIstd(mLinkedIstd, StyleIndex.NIL) : null;
    }

    /// <summary>
    /// Returns true if there is at least one formatting attribute specified.
    /// </summary>
    /*virtual*/ boolean hasFormatting()
    {
        if (mParaPr.getCount() > 0)
            return true;
        if (mRunPr.getCount() > 0)
            return true;
        return false;
    }

    /// <summary>
    /// Resiliency method, enforces the rule that the style cannot be based on itself and cannot be linked to itself.
    /// Call this whenever you set istd, based on or linked istd using internal methods.
    /// Breaks the loop by setting the offending reference to nil.
    /// </summary>
    private void fixUpShallowCircularReferences()
    {
        if (mBasedOnIstd == mIstd)
            mBasedOnIstd = StyleIndex.NIL;

        if (mLinkedIstd == mIstd)
            mLinkedIstd = StyleIndex.NIL;
    }

    /// <summary>
    /// WORDSNET-16077 Verifies that the based on style exists, otherwise set it to nil.
    /// Call this from <see cref="StyleCollection.FixUpBasedOnStyles"/>.
    /// Do not call this directly when iterating over the styles collection because this method might auto create more styles and invalidate iteration.
    /// </summary>
    void fixUpBasedOnMissing()
    {
        boolean allowAutoCreate = mBasedOnIstd < StyleCollection.MAX_FIXED_ISTD;

        if ((mBasedOnIstd != StyleIndex.NIL) && (mStyles.getByIstd(mBasedOnIstd, allowAutoCreate) == null))
            mBasedOnIstd = StyleIndex.NIL;
    }

    /// <summary>
    /// Call this from <see cref="StyleCollection.FixUpBasedOnStyles"/>.
    /// Do not call this directly when iterating over the styles collection because this method might auto create more styles and invalidate iteration.
    /// </summary>
    void fixUpBasedOnCircularReferences()
    {
        if (!verifySuitableAsBaseStyle(getBaseStyle(), false))
            mBasedOnIstd = StyleIndex.NIL;
    }

    /// <summary>
    /// Walks all base styles starting from the proposed base style to verify there will be no circular references to this style.
    /// </summary>
    private boolean verifySuitableAsBaseStyle(Style proposedBaseStyle, boolean isThrow)
    {
        while (proposedBaseStyle != null)
        {
            // Verify that we are basing on the style of the same type.
            if (proposedBaseStyle.mType != mType)
            {
                if (isThrow)
                    throw new IllegalStateException("Cannot base a style on a style of different type.");

                return false;
            }

            // Verify that setting the new base style will not create a circular dependency.
            if (proposedBaseStyle.mIstd == mIstd)
            {
                if (isThrow)
                    throw new IllegalStateException("This operation will create a circular reference between styles.");

                return false;
            }

            proposedBaseStyle = proposedBaseStyle.getBaseStyle();
        }
        return true;
    }

    /// <summary>
    /// Updates the symmetrical links for linked styles.
    /// </summary>
    private void updateLinkedStyleLinks(Style linkedStyle)
    {
        if (getLinkedIstd() != StyleIndex.NIL)
        {
            Style currentLinkedStyle = mStyles.fetchByIstd(getLinkedIstd(), mIstd);
            currentLinkedStyle.setLinkedIstd(StyleIndex.NIL);
        }

        if (linkedStyle == null)
            return;

        if (linkedStyle.getLinkedIstd() != StyleIndex.NIL)
        {
            Style currentLinkedStyle = mStyles.fetchByIstd(linkedStyle.getLinkedIstd(), mIstd);
            currentLinkedStyle.setLinkedIstd(StyleIndex.NIL);
        }

        linkedStyle.setLinkedIstd(mIstd);
    }

    /// <summary>
    /// Recursively expands paragraph properties of this style and all based on styles into direct formatting.
    /// </summary>
    void expandParaPr(ParaPr dstParaPr, /*ParaPrExpandFlags*/int flags)
    {
        Style baseStyle = getBaseStyle();

        if ((baseStyle != null) && isInheritRunAndParaFormatting())
        {
            baseStyle.expandParaPr(dstParaPr, flags & ~ParaPrExpandFlags.NO_DIRECT_FORMATTING);
        }
        else
        {
            // If we are at top of style hierarchy then expand defaults if needed.
            expandParaPrDefaults(dstParaPr, flags);
        }

        ParaPr sourceParaPr = mParaPr.getSourceParaPr(flags);

        if ((flags & ParaPrExpandFlags.NO_DIRECT_FORMATTING) == 0)
        {
            // WORDSNET-18079 It looks like list id and list level need to be expanded "out of band" because in some
            // cases list id can be specified in the based on style and list level in the derived style.
            // If we don't do that, then we will not be able to find the correct list formatting to apply.
            //
            // 1. Get the list id and/or list level attributes that are specified by this style
            Object thisStyleListId = sourceParaPr.getDirectAttr(ParaAttr.LIST_ID);
            Object thisStyleListLevel = sourceParaPr.getDirectAttr(ParaAttr.LIST_LEVEL);

            // 2. and expand them into the destination collection.
            dstParaPr.setAttrIfNotNull(ParaAttr.LIST_ID, thisStyleListId);
            dstParaPr.setAttrIfNotNull(ParaAttr.LIST_LEVEL, thisStyleListLevel);

            // WORDSNET-28427 In this test we have a derived style that does not specify list formatting
            // and had to add this check to make sure we don't expand list formatting again in the derived style.
            if ((thisStyleListId != null) || (thisStyleListLevel != null))
            {
                // 3. Now we need to expand the list formatting and we need to use the already expanded list id and list level values.
                getDocument().getLists().expandDirectList(dstParaPr, dstParaPr);
            }

            // Last expand formatting specified in this style.
            sourceParaPr.expandTo(dstParaPr);
        }

        sourceParaPr.frameInheritanceHack(dstParaPr);

        if ((flags & ParaPrExpandFlags.REMOVE_CLEAR_TAB_STOPS) != 0)
            dstParaPr.removeClearTabStops();
    }

    /// <summary>
    /// Recursively expands paragraph properties of this style and all based on styles into direct formatting.
    /// </summary>
    ParaPr getExpandedParaPr(/*ParaPrExpandFlags*/int flags)
    {
        ParaPr dstParaPr = new ParaPr();
        expandParaPr(dstParaPr, flags);
        return dstParaPr;
    }

    /// <summary>
    /// Recursively expands font properties of this style and all based on styles into direct formatting.
    /// </summary>
    void expandRunPr(RunPr dstRunPr, /*RunPrExpandFlags*/int flags)
    {
        Style baseStyle = getBaseStyle();

        if ((baseStyle != null) && isInheritRunAndParaFormatting())
        {
            baseStyle.expandRunPr(dstRunPr, flags & ~RunPrExpandFlags.NO_DIRECT_FORMATTING);
        }
        else
        {
            // If we are at top of style hierarchy then expand defaults if needed.
            expandRunPrDefaults(dstRunPr, flags);
        }

        // WORDSNET-10695 Word ignores any formatting in "Default Paragraph Font" style.
        if (getStyleIdentifier() == StyleIdentifier.DEFAULT_PARAGRAPH_FONT)
        {
            // AM. Since this style has no base style we can return immediately.
            // To avoid many affected golds lets copy Istd attribute which can be present.
            mRunPr.mirrorTo(dstRunPr, FontAttr.ISTD);
        }
        else
        {
            RunPr sourceRunPr = mRunPr.getSourceRunPr(flags);

            // AM. This code might look strange but the reason is that we should not inherit
            // category hint from styles (see WORDSNET-16263) except the case when global defaults are
            // expanded which is needed for document comparison feature.
            // I don't want to make any side effects by deleting attribute directly from style in this method
            // so one extra clone is needed. This case is rare and I think it's acceptable solution.
            if (sourceRunPr.contains(FontAttr.CHARACTER_CATEGORY_HINT))
            {
                sourceRunPr = AttrCollectionExtension.deepClone(sourceRunPr);
                sourceRunPr.remove(FontAttr.CHARACTER_CATEGORY_HINT);
            }

            sourceRunPr.themeColorInheritanceHack(dstRunPr);

            // Sometimes we shouldn't expand font size from the 'Normal' style.
            // See InlineHelper.IsIgnoreParaStyleFontSize() for details.
            if ((mSti == StyleIdentifier.NORMAL) && ((flags & RunPrExpandFlags.IGNORE_NORMAL_FONT_SIZE) != 0))
            {
                sourceRunPr = AttrCollectionExtension.deepClone(sourceRunPr);

                // WORDSNET-16374 Word does not ignore font size specified in the "Normal" style, when actual attributes
                // contain size equals to 10 pt.
                if ((sourceRunPr.getSize() == 24) && (dstRunPr.getSize() != 20))
                    sourceRunPr.remove(FontAttr.SIZE);
                if ((sourceRunPr.getSizeBi() == 24) && (dstRunPr.getSizeBi() != 20))
                    sourceRunPr.remove(FontAttr.SIZE_BI);
            }

            // Formatting specified in this style override formatting specified in the base styles.
            if ((flags & RunPrExpandFlags.NO_DIRECT_FORMATTING) == 0)
                sourceRunPr.expandTo(dstRunPr);
        }
    }

    /// <summary>
    /// Recursively expands font properties of this style and all based on styles into direct formatting.
    /// </summary>
    RunPr getExpandedRunPr(/*RunPrExpandFlags*/int flags)
    {
        RunPr dstRunPr = new RunPr();
        expandRunPr(dstRunPr, flags);
        return dstRunPr;
    }

    /// <summary>
    /// Gets a font attribute from this style, one of the parent styles, stylesheet defaults or global defaults.
    /// Note that getting from default values is only performed when isAllowDefault is true.
    ///
    /// If the attribute is not found and isAllowDefault = false, then returns <c>null</c>,
    /// because this is the behavior we expect from character styles: if a font attribute was not found,
    /// might need to look up the attribute in the paragraph style of the run.
    ///
    /// However, if the attribute is not found and isAllowDefault = true, then throws an exception
    /// because it is a programmer's error that a default value is not defined anywhere.
    /// </summary>
    Object getFontAttr(int key, boolean isAllowDefault)
    {
        // Word ignores all formatting from DefaultParagraphFont.
        if (mSti != StyleIdentifier.DEFAULT_PARAGRAPH_FONT)
        {
            Object value = mRunPr.getDirectAttr(key);
            if (value != null)
            {
                //The value is specified directly, but might need to be resolved using parent values.
                if (value instanceof AttrBoolEx)
                {
                    return ((AttrBoolEx)value).resolveFetchInheritedRunAttr(this, key);
                }
                else
                {
                    //The value is specified and does not need any special handling.
                    return value;
                }
            }
        }

        //The value is not specified in this style, get it from parents.
        return getInheritedFontAttr(key, isAllowDefault);
    }

    void validate(IWarningCallback warningCallback)
    {
        removeRunPrIstdThatPointsToParaStyle(warningCallback);
        fixUpNonExistentLinkedStyle(warningCallback);
        removeNumberRevision();
        removeMagicFontSize();
        checkReservedStyleIndex(warningCallback);
        validateTableNormal();

        // WORDSNET-9944 If OutlineLevel was not read for style IDs from Heading1 through Heading9,
        // then sets it to a corresponding value between 1 and 9, as MS Word behaves.
        fixUpOutlineLevel();
        validateOutlineLevel(warningCallback);
    }

    /// <summary>
    /// Copies generic styles attributes.
    /// </summary>
    void copyGenerics(Style srcStyle)
    {
        isQuickStyle(srcStyle.isQuickStyle());
        setAutomaticallyUpdate(srcStyle.getAutomaticallyUpdate());
        setLocked(srcStyle.getLocked());
        setUnhideWhenUsed(srcStyle.getUnhideWhenUsed());
        setPersonalCompose(srcStyle.getPersonalCompose());
        setPersonalReply(srcStyle.getPersonalReply());
        setPersonal(srcStyle.getPersonal());
        setLidsSet(srcStyle.getLidsSet());
    }

    /// <summary>
    /// Indicates that style inherits run and para attributes from base style.
    /// </summary>
    /// <remarks>
    /// AM. Per WORDSNET-8011 run and para formatting inheritance from base table style was disabled.
    /// I tested and found that run and para formatting are inherited from base table style only if table style has at least one run or para attribute.
    /// It looks like a weird Word issue but lets mimic it.
    /// </remarks>
    private boolean isInheritRunAndParaFormatting()
    {
        TableStyle tableStyle = ms.as(this, TableStyle.class);

        // This logic is applied for table styles only.
        if (tableStyle == null)
            return true;

        // If any of paragraph or text formatting specified for whole table.
        if ((tableStyle.getParaPr().getCount() > 0) || (tableStyle.getRunPr().getCount() > 0))
            return true;

        for (ConditionalStyle conditionalStyle : tableStyle.getConditionalStyles().getDefinedStyles())
        {
            // Or any of paragraph or text formatting specified for conditional formatting.
            if ((conditionalStyle.getParaPr().getCount() > 0) || (conditionalStyle.getRunPr().getCount() > 0))
                return true;

        }

        // Otherwise for unknown reason base table styles formatting is not inherited.
        return false;
    }

    /// <summary>
    /// Expands document-wide and/or global defaults depending on given flags.
    /// </summary>
    private void expandRunPrDefaults(RunPr dstRunPr, /*RunPrExpandFlags*/int flags)
    {
        boolean global = (flags & RunPrExpandFlags.GLOBAL_DEFAULTS) != 0;
        boolean documentWide = (flags & RunPrExpandFlags.DOCUMENT_DEFAULTS) != 0;

        RunPr runPr = documentWide ? getStyles().getDefaultRunPr() : G_EMPTY_RUN_PR;

        runPr.expandTo(dstRunPr, global);
    }

    /// <summary>
    /// Expands document-wide and/or global defaults depending on given flags.
    /// </summary>
    private void expandParaPrDefaults(ParaPr dstParaPr, /*ParaPrExpandFlags*/int flags)
    {
        boolean global = (flags & ParaPrExpandFlags.GLOBAL_DEFAULTS) != 0;
        boolean documentWide = (flags & ParaPrExpandFlags.DOCUMENT_DEFAULTS) != 0;

        ParaPr paraPr = documentWide ? getStyles().getDefaultParaPr() : G_EMPTY_PARA_PR;

        paraPr.expandTo(dstParaPr, global);
    }

    /// <summary>
    /// Preserves only acceptable values for Style.ParaPr.OutlineLevel
    /// </summary>
    private void validateOutlineLevel(IWarningCallback warningCallback)
    {
        // WORDSNET-10141 According to MSW behavior, all incorrect values of OutlineLevel just removed from document.
        /*OutlineLevel*/int outlineLevel = getParaPr().getOutlineLevel();
        if ((outlineLevel < OutlineLevel.LEVEL_1) || (OutlineLevel.BODY_TEXT < outlineLevel))
        {
            getParaPr().remove(ParaAttr.OUTLINE_LEVEL);
            warnUnexpected(warningCallback, WarningStrings.INVALID_STYLE_OUTLINE_LEVEL, getName());
        }
    }

    /// <summary>
    /// Sometimes paragraph Style.RunPr.Istd points to that Style.Istd. This is no good.
    /// We detect this situation and remove the erroneous istd.
    /// </summary>
    private void removeRunPrIstdThatPointsToParaStyle(IWarningCallback warningCallback)
    {
        if ((getType() == StyleType.PARAGRAPH) && (getRunPr().getIstd() == getIstd()))
        {
            warnUnexpected(warningCallback, WarningStrings.INVALID_PARAGRAPH_STYLE_ISTD, getName());
            getRunPr().remove(FontAttr.ISTD);
        }
    }

    private void fixUpNonExistentLinkedStyle(IWarningCallback warningCallback)
    {
        if (getLinkedIstd() != StyleIndex.NIL)
            if (getStyles().getByIstd(getLinkedIstd(), false) == null)
            {
                // WORDSNET-4382 Style.LinkedIstd points to non existent style. This makes MS Word 2007 crash on open.
                warnUnexpected(warningCallback, WarningStrings.STYLE_INVALID_REFERENCE, getName());
                setLinkedIstd(StyleIndex.NIL);
            }
    }

    /// <summary>
    /// Styles are not expected to have any numbering revisions, but I've seen some.
    /// </summary>
    private void removeNumberRevision()
    {
        getParaPr().remove(RevisionAttr.NUMBER_REVISION);
    }

    /// <summary>
    /// In glossary documents I see the Normal style can have 3276 font size value.
    /// Not sure what it is, let's just remove it.
    /// </summary>
    private void removeMagicFontSize()
    {
        removeMagicFontSize(getRunPr(), FontAttr.SIZE);
        removeMagicFontSize(getRunPr(), FontAttr.SIZE_BI);
    }

    private static void removeMagicFontSize(RunPr runPr, int key)
    {
        Object sizeObj = runPr.getDirectAttr(key);
        if ((sizeObj != null) && ((/*int*/Integer)sizeObj == 3276))
            runPr.remove(key);
    }

    /// <summary>
    /// Checks that StyleIndex corresponds to proper StyleIdentifier for Normal and DefaultParagraphFont styles.
    /// </summary>
    private void checkReservedStyleIndex(IWarningCallback warningCallback)
    {
        // Word fix it when resaves, for the speed reason we don't fix it immediately because whole DOM update is needed.
        // So log warnings only and update styles in DocumentValidator.
        if ((getIstd() == StyleIndex.NORMAL) && (getStyleIdentifier() != StyleIdentifier.NORMAL))
            warn(warningCallback, WarningType.UNEXPECTED_CONTENT, WarningSource.VALIDATOR,
                msString.format(WarningStrings.RESERVED_STYLE_INDEX_USED, "Normal", getName()));

        if ((getIstd() == StyleIndex.DEFAULT_PARAGRAPH_FONT) && (getStyleIdentifier() != StyleIdentifier.DEFAULT_PARAGRAPH_FONT))
            warn(warningCallback, WarningType.UNEXPECTED_CONTENT, WarningSource.VALIDATOR,
                msString.format(WarningStrings.RESERVED_STYLE_INDEX_USED, "DefaultParagraphFont", getName()));
    }

    /// <summary>
    /// andrnosk: WORDSNET-8643 According to MS Word behavior style with StyleIdentifier equals TableNormal
    /// cannot contain RunPr and ParaPr, that is why we have to remove RunPr/ParaPr from this style.
    /// </summary>
    /// <remarks>
    /// Upon saving document to WML using MS Word 2013, its writes additional runPr to TableNormal style,
    /// and this runPr contains only wx:font and w:lang properties.
    /// These properties do not have any affect on the document, that is why I have decided do not write them to WML too.
    /// Note: previous versions MSW do not write rPr/pPr for TableNormal style too.
    /// </remarks>
    private void validateTableNormal()
    {
        if (getStyleIdentifier() == StyleIdentifier.TABLE_NORMAL)
        {
            getRunPr().clear();
            getParaPr().clear();

            // WORDSNET-23617 Force TableNormal to have defaults.
            TableStyle tableNormal = (TableStyle)this;

            TablePr tablePr = tableNormal.getTablePr();

            // WORDSNET-27641 Remove borders and conditional formatting from TableNormal style.
            // AM. Actually Word always makes TableNormal style to have defaults only but this causes
            // many golds affected, lets remove problematic attributes only for a while.
            for (int key : TablePr.POSSIBLE_BORDER_KEYS.getValues())
                tablePr.remove(key);

            tableNormal.getConditionalStyles().clear();

            tablePr.setAttr(TableAttr.LEFT_PADDING, 108);
            tablePr.setAttr(TableAttr.RIGHT_PADDING, 108);
            tablePr.setAttr(TableAttr.TOP_PADDING, 0);
            tablePr.setAttr(TableAttr.BOTTOM_PADDING, 0);
            tablePr.setAttr(TableAttr.LEFT_INDENT, 0);
        }
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    private static void warn(IWarningCallback warningCallback, /*WarningType*/int warningType, /*WarningSource*/int warningSource, String description)
    {
        if (warningCallback != null)
            warningCallback.warning(new WarningInfo(warningType, warningSource, description));
    }

    private static void warnUnexpected(IWarningCallback warningCallback, String description, /*params*/ Object... args)
    {
        WarningUtil.warnUnexpected(warningCallback, description, args);
    }

    /// <summary>
    /// Gets a font attribute from one of the parent styles, stylesheet defaults or global defaults.
    /// See GetFontAttr for more info as these two functions work together.
    /// </summary>
    Object getInheritedFontAttr(int key, boolean isAllowDefault)
    {
        Style baseStyle = getBaseStyle();
        if (baseStyle != null)
        {
            //Parent style is available, get the value from it.
            return baseStyle.getFontAttr(key, isAllowDefault);
        }
        else
        {
            //No more parent styles, look up in defaults if allowed.
            if (isAllowDefault)
                return mStyles.getDefaultRunPr().fetchAttr(key);
            else
                return null;
        }
    }

    /// <summary>
    /// Sets OutlineLevel for style IDs from Heading1 through Heading9 to a corresponding value between 1 and 9.
    /// </summary>
    private void fixUpOutlineLevel()
    {
        if (!isHeading())
            return;

        int currentOutlineLvl = (int)(getStyleIdentifier() - StyleIdentifier.HEADING_1);
        if ((getParaPr().get(ParaAttr.OUTLINE_LEVEL) == null) || ((/*int*/Integer)getParaPr().get(ParaAttr.OUTLINE_LEVEL) != currentOutlineLvl))
            getParaPr().setOutlineLevel((/*OutlineLevel*/int)currentOutlineLvl);
    }

    Object getParaAttr(int key, /*RevisionsView*/int view)
    {
        Object value = mParaPr.getDirectAttr(key, view);
        return (value != null) ? value : getInheritedParaAttr(key, view);
    }

    /// <summary>
    /// Fetches inherited attribute value.
    /// </summary>
    private Object getInheritedParaAttr(int key, /*RevisionsView*/int view)
    {
        // Gets a paragraph attribute from one of the parent styles or global defaults.
        // Style can refer to a list. Check this first.
        // But there could be some really rare cases like in 18079 where the list id is specified in a based on style but list level
        // is specified in a derived style. I don't think it is possible to correctly resolve at all using this bottom-up method.
        // However it is handled correctly in the full top-bottom expansion and that's important for rendering.
        Object value = getAttrFromReferredList(key);

        if (value != null)
            return value;

        Style baseStyle = getBaseStyle();
        return (baseStyle != null) ? baseStyle.getParaAttr(key, view) : null;
    }

    /// <summary>
    /// Style can refer to a list. Check this first.
    /// </summary>
    Object getAttrFromReferredList(int key)
    {
        // WORDSNET-11258 Obviously, the following attributes cannot be defined in ListLevel.
        // So, let's exit if one of them is requested to prevent from infinite loop
        // when this method is involved in recursion.
        if ((key == ParaAttr.LIST_ID) || (key == ParaAttr.LIST_LEVEL))
            return null;

        // WORDSNET-14454 Should get attribute from the list only if this style has it in a direct attribute collection.
        // We try to get List and corresponding ListLevel from the chain of the base styles,
        // because ListId and ListLevel can be defined in a different base styles (see more details in GetInheritedParaAttr()).
        // Otherwise, there can be a situation when the requested attribute in direct attributes of some base style
        // is occurred earlier then in list. And therefore, we will skip it in GetInheritedParaAttr() as
        // code for getting direct attributes of base style will not be reached.
        if (mParaPr.contains(ParaAttr.LIST_ID) || mParaPr.contains(ParaAttr.LIST_LEVEL))
        {
            int listId = (/*int*/Integer)((IParaAttrSource)this).fetchParaAttr(ParaAttr.LIST_ID);
            if (listId != 0)
            {
                List list = getDocument().getLists().fetchListByListIdResolveStyleReference(listId);

                int level = (/*int*/Integer)((IParaAttrSource)this).fetchParaAttr(ParaAttr.LIST_LEVEL);
                ListLevel listLevel = list.getListLevelOverrideAware(level);

                return listLevel.getParaPr().getDirectAttr(key);
            }
            else if (mParaPr.isExplicitlyNotListItem())
            {
                // WORDSNET-5623 Consider explicitly no list attribute.
                if (ParaPr.isNoListAttr(key))
                    return 0;
            }
        }

        return null;
    }

    /// <summary>
    /// Returns style's List or <c>null</c>.
    /// </summary>
    List getListInternal()
    {
        if (mParaPr.get(ParaAttr.LIST_ID) != null)
        {
            int listId = (/*int*/Integer)mParaPr.get(ParaAttr.LIST_ID);

            if ((listId != 0) && (getDocument() != null))
                return getDocument().getLists().getListByListId(listId);
        }

        return null;
    }

    //>>>>>>>> #region  IParaAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*IParaAttrSource.*/getDirectParaAttr(int key)
    {
        return mParaPr.getDirectAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IParaAttrSource.*/getDirectParaAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return mParaPr.getDirectAttr(key, revisionsView);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IParaAttrSource.*/fetchParaAttr(int key)
    {
        Object value = mParaPr.get(key);
        return (value != null) ? value : ((IParaAttrSource)this).fetchInheritedParaAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IParaAttrSource.*/fetchInheritedParaAttr(int key)
    {
        Object value = getInheritedParaAttr(key, RevisionsView.ORIGINAL);
        return (value != null) ? value : getStyles().getDefaultParaPr().fetchAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IParaAttrSource.*/setParaAttr(int key, Object value)
    {
        mParaPr.setAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IParaAttrSource.*/removeParaAttr(int key)
    {
        mParaPr.remove(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IParaAttrSource.*/clearParaAttrs()
    {
        mParaPr.clear();
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IRunAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key)
    {
        return mRunPr.getDirectAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return mRunPr.getDirectAttr(key, revisionsView);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/fetchInheritedRunAttr(int key)
    {
        return getInheritedFontAttr(key, true);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/setRunAttr(int key, Object value)
    {
        mRunPr.setAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/removeRunAttr(int key)
    {
        mRunPr.remove(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/clearRunAttrs()
    {
        mRunPr.clear();
    }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Core method for comparing with the specified style.
    /// Compares style specific attributes, direct style formatting and list formatting.
    /// Does not compare base, linked and next paragraph styles for the specified style.
    /// </summary>
    private boolean equalsCore(Style style, HashSetGeneric<Pair> alreadyCompared)
    {
        if (style == null)
            return false;

        alreadyCompared.add(new Pair(this, style));

        // We always treat non-modifiable styles as equal if their style identifiers are equal.
        // For example, see TestStyleImport.TestJira11294_1().
        if (StyleIndex.isNonModifiable(style) && (this.getStyleIdentifier() == style.getStyleIdentifier()))
            return true;

        // Compare built-in reserved styles.
        if (!builtinReservedEquals(style))
            return false;

        // Compare style specific properties.
        if (!genericEquals(style))
            return false;

        // Compare direct style formatting.
        if (!directFormattingEquals(style))
            return false;

        // Compare list formatting.
        List listA = getListInternal();
        List listB = style.getListInternal();

        if (ArgumentUtil.oneIsNull(listA, listB))
            return false;

        if (!ArgumentUtil.bothAreNull(listA, listB) && !listA.equalsCore(listB, alreadyCompared))
            return false;

        return true;
    }

    /// <summary>
    /// Checks equality of built-in styles with reserved Istds.
    /// </summary>
    private boolean builtinReservedEquals(Style style)
    {
        if ((this.getBuiltIn() && style.getBuiltIn()) &&
            ((StyleIndex.isIstdReserved(this.getIstd()) || StyleIndex.isIstdReserved(style.getIstd())) &&
             (this.getIstd() != style.getIstd())))
            return false;

        return true;
    }

    /// <summary>
    /// Compares generic styles attributes.
    /// </summary>
    private boolean genericEquals(Style style)
    {
        // Ignore Istd, Rsid, UIPriority, Hidden, SemiHidden, IsHidden, Name and NextParagraphName, Identifier.
        return ((getType() == style.getType()) &&
                (isHeading() == style.isHeading()) &&
                (isQuickStyle() == style.isQuickStyle()) &&
                (getAutomaticallyUpdate() == style.getAutomaticallyUpdate()) &&
                (getLocked() == style.getLocked()) &&
                (getUnhideWhenUsed() == style.getUnhideWhenUsed()) &&
                (getPersonalCompose() == style.getPersonalCompose()) &&
                (getPersonalReply() == style.getPersonalReply()) &&
                (getPersonal() == style.getPersonal()) &&
                (getLidsSet() == style.getLidsSet()) &&
                (isTopLevelParaStyle() == style.isTopLevelParaStyle()) &&
                (hasRevisions() == style.hasRevisions()));
    }

    /// <summary>
    /// Compares direct styles formatting.
    /// </summary>
    private boolean directFormattingEquals(Style style)
    {
        switch (getType())
        {
            case StyleType.CHARACTER:
                return this.getRunPr().equals(style.getRunPr(), COMPARISON_IGNORABLE_KEYS);

            case StyleType.PARAGRAPH:
                return this.getRunPr().equals(style.getRunPr(), COMPARISON_IGNORABLE_KEYS) &&
                       this.getParaPr().equals(style.getParaPr(), COMPARISON_IGNORABLE_KEYS);

            case StyleType.TABLE:
                TableStyle tableStyleA = (TableStyle)this;
                TableStyle tableStyleB = (TableStyle)style;
                ConditionalStyleCollection conditionalStylesA = tableStyleA.getConditionalStyles();
                ConditionalStyleCollection conditionalStylesB = tableStyleB.getConditionalStyles();

                /*ConditionalStyleType*/int[] types = (/*ConditionalStyleType*/int[])ConditionalStyleType.getValues();
                for (/*ConditionalStyleType*/int type : types)
                {
                    if (!conditionalStylesA.containsConditionalStyle(type) &&
                        !conditionalStylesB.containsConditionalStyle(type))
                        continue;

                    if (!conditionalStylesA.getByConditionalStyleType(type).equals(conditionalStylesB.getByConditionalStyleType(type)))
                        return false;
                }

                return tableStyleA.getRunPr().equals(tableStyleB.getRunPr(), COMPARISON_IGNORABLE_KEYS) &&
                       tableStyleA.getParaPr().equals(tableStyleB.getParaPr(), COMPARISON_IGNORABLE_KEYS) &&
                       tableStyleA.getCellPr().equals(tableStyleB.getCellPr(), COMPARISON_IGNORABLE_KEYS) &&
                       tableStyleA.getRowPr().equals(tableStyleB.getRowPr(), COMPARISON_IGNORABLE_KEYS) &&
                       tableStyleA.getTablePr().equals(tableStyleB.getTablePr(), COMPARISON_IGNORABLE_KEYS);

            case StyleType.LIST:
                // Lists are compared in special way separately in EqualsCore().
                return true;

            default:
                throw new IllegalStateException(msString.format("Unexpected style type '{0}'.", getType()));
        }
    }


    /// <summary>
    /// Only needed for DOC format TestStyleFiler to pass.
    /// </summary>
    boolean RawInvalidHeight;
    /// <summary>
    /// Only needed for DOC format TestStyleFiler to pass.
    /// </summary>
    boolean RawHasUpe;
    /// <summary>
    /// Only needed for DOC format TestStyleFiler to pass.
    /// </summary>
    boolean RawInternalUse;

    /// <summary>
    /// These keys will be ignored in properties comparison.
    /// </summary>
    static /*final*/ int[] COMPARISON_IGNORABLE_KEYS = new int[]
    {
        FontAttr.RSID_R_PR, FontAttr.RSID_R, FontAttr.ISTD, ParaAttr.ISTD, ParaAttr.LIST_ID, ParaAttr.RSID_P,
        // WORDSNET-17538 These unwanted attributes appears in the imported style while copying source formatting to
        // destination. Process of copying formatting is complex and uses expand and collapse operations. As the result,
        // initially equal source and destination styles become different during comparison.
        ParaAttr.SYS_ALIGNMENT_97, ParaAttr.SYS_LEFT_INDENT_97,  ParaAttr.SYS_RIGHT_INDENT_97, ParaAttr.SYS_FIRST_LINE_INDENT_97,
        TableAttr.ISTD, TableAttr.RSID_TR
    };

    private int mIstd = StyleIndex.NIL;
    /// <summary>
    /// sti (style identifier) of the style.
    /// </summary>
    private /*StyleIdentifier*/int mSti = StyleIdentifier.NIL;
    private /*StyleIdentifier*/int mClonedFromSti = StyleIdentifier.NIL;
    private /*StyleType*/int mType;
    private int mBasedOnIstd = StyleIndex.NIL;
    private int mNextIstd = StyleIndex.NIL;

    private boolean mAutomaticallyUpdate;
    private boolean mHidden;
    private boolean mSemiHidden;
    private boolean mLocked;
    private boolean mUnhideWhenUsed;
    private boolean mIsQuickStyle;
    private int mUIPriority;

    private boolean mPersonalCompose;
    private boolean mPersonalReply;
    private boolean mPersonal;

    private boolean mLidsSet;
    private int mLinkedIstd = StyleIndex.NIL;
    private int mRsid;
    private String mName;
    private ParaPr mParaPr = new ParaPr();
    private RunPr mRunPr = new RunPr();

    /// <summary>
    /// Pointer to the parent collection of styles.
    /// </summary>
    private StyleCollection mStyles;
    /// <summary>
    /// Facade object to access run attributes.
    /// </summary>
    private Font mFontCache;

    /// <summary>
    /// Facade object to access paragraph attributes.
    /// </summary>
    private ParagraphFormat mParagraphFormatCache;

    /// <summary>
    /// Facade object to access paragraph frame attributes.
    /// </summary>
    private FrameFormat mFrameFormatCache;

    /// <summary>
    /// Facade object to access list formatting attributes.
    /// </summary>
    private ListFormat mListFormatCache;

    // Dummy empty attributes collection.
    private static /*final*/ RunPr G_EMPTY_RUN_PR = new RunPr();
    private static /*final*/ ParaPr G_EMPTY_PARA_PR = new ParaPr();

    static final int MAX_UI_PRIORITY = 99;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

