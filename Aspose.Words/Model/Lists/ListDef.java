// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msInt;
import com.aspose.HashSetGeneric;
import com.aspose.Pair;
import com.aspose.ms.System.msNumber;
import com.aspose.Debug;


/// <summary>
/// A definition of a list. These definitions will not be directly used in the document.
/// Instead, they will be referenced by list elements.
/// </summary>
class ListDef implements Comparable<ListDef>, Cloneable
{
    /// <summary>
    /// Ctor. Used when loading a document from RTF.
    /// Note: Found only in RtfListTableHandler.cs. Maybe it's good to refactor to using only one ctor.
    /// </summary>
    /// <param name="document">Need to know the parent document because list definition is valid in a context of a document.</param>
    ListDef(DocumentBase document)
    {
        mDocument = document;
        mLevels = new ListLevelCollection();
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="document">Need to know the parent document because list definition is valid in a context of a document.</param>
    /// <param name="listDefId">Once assigned, list definition id should never change, so assign at ctor.</param>
    /// <param name="listType">Once assigned, list type should never change.</param>
    /// <param name="templateCode">We do not change template code after construction too.</param>
    ListDef(DocumentBase document, int listDefId, /*ListType*/int listType, int templateCode)
    {
        mDocument = document;
        mListDefId = listDefId;
        mListType = listType;
        mTemplateCode = templateCode;
        mLevels = new ListLevelCollection(document, listType);
    }

    public int compareTo(ListDef other)
    {
        return msInt.compareTo(mListDefId, other.getListDefId());
    }

    /// <summary>
    /// Makes a deep copy of the list definition.
    /// Note that the list style and paragraph styles referenced by this object
    /// are not copied.
    /// </summary>
    /// <param name="document">Destination document, required to make a valid clone.</param>
    /// <param name="listDefId">New list definition id, required to make a valid clone.</param>
    /*virtual*/ ListDef deepClone(DocumentBase document, int listDefId)
    {
        ListDef lhs = (ListDef)memberwiseClone();
        lhs.mDocument = document;
        lhs.mListDefId = listDefId;
        lhs.mLevels = mLevels.deepClone(document);
        return lhs;
    }

    /// <summary>
    /// Creates collection of blank levels of the desired list type.
    /// </summary>
    void purgeLevels()
    {
        mLevels = new ListLevelCollection(mDocument, mListType);
    }

    /// <summary>
    /// Compares with the specified ListDef.
    /// </summary>
    public boolean equals(ListDef listDef)
    {
        return equalsCore(listDef, new HashSetGeneric<Pair>());
    }

    /// <summary>
    /// Compares with the specified ListDef.
    /// <param name="listDef">ListDef that will be compared with this ListDef.</param>
    /// <param name="alreadyComparedLinkedStyles">HashSet for collecting already compared styles to avoid dead loops.</param>
    /// </summary>
    boolean equalsCore(ListDef listDef, HashSetGeneric<Pair> alreadyComparedLinkedStyles)
    {
        if (listDef == null)
            return false;

        if (getListType() != listDef.getListType())
            return false;

        if (getLevels().getCount() != listDef.getLevels().getCount())
            return false;

        if (isListStyleDefinition() != listDef.isListStyleDefinition())
            return false;

        if (isListStyleReference() != listDef.isListStyleReference())
            return false;

        Style style = getStyle();
        if ((style != null) && (!style.equals(listDef.getStyle(), alreadyComparedLinkedStyles)))
            return false;

        for (int i = 0; i < getLevels().getCount(); i++)
            if (!getLevels().get(i).equalsCore(listDef.getLevels().get(i), alreadyComparedLinkedStyles))
                return false;

        return true;
    }

    /// <summary>
    /// Calculates hash code for this object.
    /// </summary>
    /// <dev>
    /// To be compatible with the <see cref="Equals(ListDef)"/> method, only properties that affect visual
    /// representation of the list should be included into the calculation. List definition ID, name and similar
    /// properties are ignored.
    /// </dev>
    public /*override*/ int hashCode()
    {
        int hashCode = /*enum.hashCode()=(int)value*/getListType();
        hashCode = (hashCode * 397) ^ msNumber.booleanHashCode(isListStyleDefinition());
        hashCode = (hashCode * 397) ^ msNumber.booleanHashCode(isListStyleReference());

        for (ListLevel level : getLevels())
            hashCode = (hashCode * 397) ^ level.hashCode();

        return hashCode;
    }

    /// <summary>
    /// Used when loading a document from RTF and DOCX (if nsid is omitted in list definition).
    /// Don't use this method if list is already added to list collection.
    /// </summary>
    /// <param name="listDefId"></param>
    void setListDefId(int listDefId)
    {
        mListDefId = listDefId;
    }

    /// <summary>
    /// Used when loading a document from RTF, ODT.
    /// </summary>
    /// <param name="listType"></param>
    void setListType(/*ListType*/int listType)
    {
        mListType = listType;
    }

    /// <summary>
    /// Used when loading a document from RTF.
    /// </summary>
    /// <param name="templateCode"></param>
    void setTemplateCode(int templateCode)
    {
        mTemplateCode = templateCode;
    }

    /// <summary>
    /// Finds the terminal list definition in the chain of referencing.
    /// When ListDef is a reference the list levels should be accessed through
    /// the referenced ListDef. WORDSNET-13530.
    /// Maybe we can also refactor <see cref="IsListStyleDefinition" /> and <see cref="IsListStyleReference" />
    /// using this method but in the current implementation they both require Style != null.
    /// </summary>
    ListDef getTerminalListDef()
    {
        // WORDSNET-17658 Restored recursion.
        if (isListStyleReference())
            return getStyle().getList().getListDef().getTerminalListDef();

        // WORDSNET-20030 Return NoList when ListLevels is empty.
        return (getLevels().getCount() > 0)
            ? this
            : mDocument.getStyles().getByIstd(StyleIndex.NO_LIST, false).getList().getListDef();
    }

    /// <summary>
    /// Returns <see cref="ParagraphNumberRevision"/> for a specified list level.
    /// </summary>
    ParagraphNumberRevision createNumberRevision(int listLevel, EditSession session)
    {
        Debug.assertTrue((listLevel >= 0) && (listLevel < ListLevel.MAX_LEVELS));

        if (listLevel >= getLevels().getCount())
            return null;

        ParagraphNumberRevision revision = new ParagraphNumberRevision();
        revision.setAuthor(session.getAuthor());
        revision.setDateTime(session.getDateTime());

        revision.setWasNumbered(true);
        revision.setNumberFormat(mLevels.get(listLevel).getNumberFormat());

        for (int i = 0; i <= listLevel; i++)
        {
            ListLevel level = mLevels.get(i);
            revision.getNumberStyles()[i] = level.getNumberStyle();
            revision.getNumberValues()[i] = level.getStartAt();
        }

        return revision;
    }

    /// <summary>
    /// Unique List definition ID.
    ///
    /// In a DOC file, this is actually lsid. In WordML this is listDef.lsid
    /// ListDefId in WordML is something different.
    /// </summary>
    int getListDefId() { return mListDefId; }

    /// <summary>
    /// Specifies the list type.
    /// </summary>
    /*ListType*/int getListType() { return mListType; }

    /// <summary>
    /// This seems to be random in WordML.
    /// </summary>
    int getTemplateCode() { return mTemplateCode; }

    /// <summary>
    /// Represents the list's name. I think it is related to the name that can be used in LISTNUM fields.
    /// </summary>
    String getName() { return mName; }
    void setName(String value) { mName = value; }

    /// <summary>
    /// Indicates that list should restart at each section.
    /// </summary>
    /// <remarks>
    /// AM. This option is supported only RTF, DOC and DOCX document formats.
    /// </remarks>
    boolean isRestartAtEachSection() { return mIsRestartAtEachSection; }
    void isRestartAtEachSection(boolean value) { mIsRestartAtEachSection = value; }

    /// <summary>
    /// Defines levels in this list. This provides direct access to "raw" data defined for list definition.
    /// You normally want to use this when loading/saving to MS Word formats, but for other purposes,
    /// such as rendering see <see cref="GetTerminalListDef"/>.
    /// </summary>
    ListLevelCollection getLevels() { return mLevels; }

    /// <summary>
    /// Returns true if this list definition is a definition of a list style.
    /// </summary>
    boolean isListStyleDefinition()
    {
        // This object is a list style definition when the list style references this list definition.
        Style style = this.getStyle();
        return (style != null) && (style.getList().getListDef() == this);
    }

    /// <summary>
    /// Returns true if this list definition is a reference to a list style.
    /// </summary>
    boolean isListStyleReference()
    {
        // This object is a list style reference when the list style references another list definition.
        Style style = this.getStyle();
        return (style != null) && (style.getList().getListDef() != this);
    }

    /// <summary>
    /// The istd of the list style that this list defines or references.
    /// Corresponds to the w:styleLink and w:listStyleLink in WordML.
    /// </summary>
    int getListStyleIstd() { return mListStyleIstd; }
    void setListStyleIstd(int value)
    {
        mListStyleIstd = value;

        // WORDSNET-1098 Version 3.7 creates documents that crash Word2002.
        // We should never write StyleIndex.Nil as this crashes MS Word 2002.
        if (mListStyleIstd == StyleIndex.NIL)
            mListStyleIstd = StyleIndex.NO_LIST;
    }

    /// <summary>
    /// Gets the list style that this list defines or references.
    /// If the list does not define or reference a list style this property returns null.
    /// </summary>
    Style getStyle()
    {
        if ((mListStyleIstd == StyleIndex.NIL) || (mListStyleIstd == StyleIndex.NO_LIST))
            return null;

        Style style = mDocument.getStyles().getByIstd(mListStyleIstd, true);
        if ((style != null) && (style.getType() == StyleType.LIST))
            return style;
        else
            return null;
    }

    /// <summary>
    /// Resolves list levels for an empty ListStyleReference listDef, otherwise gets own list levels.
    /// </summary>
    ListLevelCollection getListLevels()
    {
        return isListStyleReference() && (getLevels().getCount() == 0)
            ? getTerminalListDef().getLevels()
            : getLevels();
    }

    /// <summary>
    /// Returns the owner document.
    /// </summary>
    DocumentBase getDocument() { return mDocument; }

    private DocumentBase mDocument;
    private int mListDefId;
    /// <summary>
    /// I think this is a correct default according to the RTF spec.
    /// </summary>
    private /*ListType*/int mListType = ListType.MULTI_LEVEL;
    private int mTemplateCode;
    private String mName;
    private ListLevelCollection mLevels;
    private int mListStyleIstd = StyleIndex.NO_LIST;
    /// <summary>
    /// Default value is <c>false</c>.
    /// </summary>
    private boolean mIsRestartAtEachSection;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }

}

