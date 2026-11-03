// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/06/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;


/// <summary>
/// Allows to control what list formatting is applied to a paragraph.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-lists/">Working with Lists</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>A paragraph in a Microsoft Word document can be bulleted or numbered.
/// When a paragraph is bulleted or numbered, it is said that list formatting
/// is applied to the paragraph.</p>
///
/// <p>You do not create objects of the <see cref="ListFormat"/> class directly.
/// You access <see cref="ListFormat"/> as a property of another object that can
/// have list formatting associated with it. At the moment the objects that can
/// have list formatting are: <see cref="Aspose.Words.Paragraph"/>,
/// <see cref="Aspose.Words.Style"/> and <see cref="Aspose.Words.DocumentBuilder"/>.</p>
///
/// <p><see cref="ListFormat"/> of a <see cref="Aspose.Words.Paragraph"/> specifies
/// what list formatting and list level is applied to that particular paragraph.</p>
///
/// <p><see cref="ListFormat"/> of a <see cref="Aspose.Words.Style"/> (applicable
/// to paragraph styles only) allows to specify what list formatting and list level
/// is applied to all paragraphs of that particular style.</p>
///
/// <p><see cref="ListFormat"/> of a <see cref="Aspose.Words.DocumentBuilder"/>
/// provides access to the list formatting at the current cursor position
/// inside the <see cref="Aspose.Words.DocumentBuilder"/>.</p>
///
/// <p>The list formatting itself is stored inside a <see cref="Lists.List"/>
/// object that is stored separately from the paragraphs. The list objects
/// are stored inside a <see cref="ListCollection"/> collection. There is a single
/// <see cref="ListCollection"/> collection per <see cref="Aspose.Words.Document"/>.</p>
///
/// <p>The paragraphs do not physically belong to a list. The paragraphs just
/// reference a particular list object via the <see cref="List"/> property
/// and a particular level in the list via the <see cref="ListLevelNumber"/> property.
/// By setting these two properties you control what bullets and numbering is
/// applied to a paragraph.</p>
/// </remarks>
public class ListFormat
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="parentParaAttrs">The object that provides list paragraph formatting attributes.</param>
    /// <param name="parentRunAttrs">The object that provides list run formatting attributes.</param>
    /// <param name="lists">Lists of the document.</param>
    ListFormat(IParaAttrSource parentParaAttrs, IRunAttrSource parentRunAttrs, ListCollection lists)
    {
        mParentParaAttrs = parentParaAttrs;
        mParentRunAttrs = parentRunAttrs;
        mLists = lists;
    }

    /// <summary>
    /// Starts a new default bulleted list and applies it to the paragraph.
    /// </summary>
    /// <remarks>
    /// <p>This is a shortcut method that creates a new list using the default bulleted
    /// template, applies it to the paragraph and selects the 1st list level.</p>
    ///
    /// <seealso cref="List"/>
    /// <seealso cref="RemoveNumbers"/>
    /// <seealso cref="ListLevelNumber"/>
    /// </remarks>
    public void applyBulletDefault()
    {
        if(mLists.getCount() > MAX_LIST_COUNT)
            WarningUtil.warnDataLoss(mLists.getDocument().getWarningCallback(), WarningSource.VALIDATOR, WarningStrings.LIST_COUNT_EXCEED_LIMIT);

        // Set current paragraph ilvl and ilfo.
        setListId(mLists.add(ListTemplate.BULLET_DEFAULT).getListId());
        setListLevelNumber(0);
        invalidate();
    }

    /// <summary>
    /// Starts a new default numbered list and applies it to the paragraph.
    /// </summary>
    /// <remarks>
    /// <p>This is a shortcut method that creates a new list using the default numbered
    /// template, applies it to the paragraph and selects the 1st list level.</p>
    ///
    /// <seealso cref="List"/>
    /// <seealso cref="RemoveNumbers"/>
    /// <seealso cref="ListLevelNumber"/>
    /// </remarks>
    public void applyNumberDefault()
    {
        if (mLists.getCount() > MAX_LIST_COUNT)
            WarningUtil.warnDataLoss(mLists.getDocument().getWarningCallback(), WarningSource.VALIDATOR, WarningStrings.LIST_COUNT_EXCEED_LIMIT);

        setListId(mLists.add(ListTemplate.NUMBER_DEFAULT).getListId());
        setListLevelNumber(0);
        invalidate();
    }

    /// <summary>
    /// Removes numbers or bullets from the current paragraph and sets list level to zero.
    /// </summary>
    /// <remarks>
    /// <p>Calling this method is equivalent to setting the <see cref="ListFormat.List"/> property to <c>null</c>.</p>
    /// </remarks>
    public void removeNumbers()
    {
        setList(null);
        invalidate();
    }

    /// <summary>
    /// Increases the list level of the current paragraph by one level.
    /// </summary>
    /// <remarks>
    /// <p>This method changes the list level and applies formatting properties of the new level.</p>
    ///
    /// <p>In Word documents, lists may consist of up to nine levels. List formatting
    /// for each level specifies what bullet or number is used, left indent, space between
    /// the bullet and text etc.</p>
    /// </remarks>
    public void listIndent() throws Exception
    {
        if (getListLevelNumberOriginal() < ListLevel.MAX_LEVELS - 1)
        {
            setListLevelNumber(getListLevelNumberOriginal() + 1);
            invalidate();
        }
    }

    /// <summary>
    /// Decreases the list level of the current paragraph by one level.
    /// </summary>
    /// <remarks>
    /// <p>This method changes the list level and applies formatting properties of the new level.</p>
    ///
    /// <p>In Word documents, lists may consist of up to nine levels. List formatting
    /// for each level specifies what bullet or number is used, left indent, space between
    /// the bullet and text etc.</p>
    /// </remarks>
    public void listOutdent() throws Exception
    {
        if (getListLevelNumberOriginal() > 0)
        {
            setListLevelNumber(getListLevelNumberOriginal() - 1);
            invalidate();
        }
    }

    /// <summary>
    /// Gets or sets the list level number (0 to 8) for the paragraph.
    /// </summary>
    /// <remarks>
    /// <p>In Word documents, lists may consist of 1 or 9 levels, numbered 0 to 8.</p>
    ///
    /// <p>Has effect only when the <see cref="List"/> property is set to reference a valid list.</p>
    ///
    /// <seealso cref="List"/>
    /// </remarks>
    public int getListLevelNumber()
    {
        // WORDSNET-21552 Return "final" value when selected revised version of a document.
        Document doc = ms.as(mLists.getDocument(), Document.class);
        if ((doc != null) && (doc.getRevisionsView() == RevisionsView.FINAL))
            return getListLevelNumberFinal();

        return getListLevelNumberOriginal();
    }
    public void setListLevelNumber(int value)
    {
        mParentParaAttrs.setParaAttr(ParaAttr.LIST_LEVEL, value);
        invalidate();
    }

    int getListLevelNumberOriginal()
    {
        return (/*int*/Integer)mParentParaAttrs.fetchParaAttr(ParaAttr.LIST_LEVEL);
    }

    int getListLevelNumberFinal()
    {
        Paragraph paragraph = ms.as(mParentParaAttrs, Paragraph.class);
        if (paragraph != null)
            return (/*int*/Integer)paragraph.fetchParaAttr(ParaAttr.LIST_LEVEL, RevisionsView.FINAL);

        return getListLevelNumberOriginal();
    }

    /// <summary>
    /// True when the paragraph has bulleted or numbered formatting applied to it.
    /// </summary>
    public boolean isListItem() { return (getListId() != 0); }

    /// <summary>
    /// Gets or sets the list this paragraph is a member of.
    /// </summary>
    /// <remarks>
    /// <p>The list that is being assigned to this property must belong to the current document.</p>
    ///
    /// <p>The list that is being assigned to this property must not be a list style definition.</p>
    ///
    /// <p>Setting this property to <c>null</c> removes bullets and numbering from the paragraph
    /// and sets the list level number to zero. Setting this property to <c>null</c> is equivalent
    /// to calling <see cref="RemoveNumbers"/>.</p>
    ///
    /// <seealso cref="ListLevelNumber"/>
    /// <seealso cref="RemoveNumbers"/>
    /// </remarks>
    public List getList()
    {
        int listId = getListId();
        return (listId != 0) ? mLists.fetchListByListId(listId) : null;
    }
    public void setList(List value)
    {
        if (value == null)
        {
            setListId(0);
            setListLevelNumber(0);
        }
        else
        {
            if (value.getDocument() != mLists.getDocument())
                throw new IllegalArgumentException("The list belongs to a different document.");

            if (value.isListStyleDefinition())
                throw new IllegalArgumentException("The list is a definition of a list style.");

            setListId(value.getListId());
        }

        invalidate();
    }

    List getListFinal()
    {
        int listId = getListIdFinal();
        return (listId != 0) ? mLists.fetchListByListId(listId) : null;
    }

    /// <summary>
    /// Returns the list level formatting plus any formatting overrides applied to the current paragraph.
    /// </summary>
    
    public ListLevel getListLevel()
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	Debug.assertTrue(mLists != null);

        	Document doc = ms.as(mLists.getDocument(), Document.class);

        	if ((doc != null) && (doc.getRevisionsView() == RevisionsView.FINAL))
            	return getListLevelFinal();

        	return getListLevelOriginal();
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Returns original list level formatting plus any formatting overrides applied to the current paragraph.
    /// </summary>
    
    ListLevel getListLevelOriginal()
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	if (mCachedListLevel == null)
        	{
            	List list = getList();
            	ListLevel listLevel = (list != null) ? list.getListLevelOverrideAware(getListLevelNumberOriginal()) : null;

            	// AM. Here we have to create inherited copy of ListLevel.
            	// This copy gets all ListLevel properties from parent ListLevel and 
            	// gets formatting missed in parent ListLevel from parent IRunAttrSource.
            	mCachedListLevel = listLevel != null ? new ListLevel(listLevel, mParentRunAttrs) : null;
        	}

        	return mCachedListLevel;
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Returns final list level formatting plus any formatting overrides applied to the current paragraph.
    /// </summary>
    ListLevel getListLevelFinal()
    {
        if (mCachedListLevelFinal == null)
        {
            List list = getListFinal();
            ListLevel listLevel = (list != null) ? list.getListLevelOverrideAware(getListLevelNumberFinal()) : null;

            // AM. Here we have to create inherited copy of ListLevel.
            // This copy gets all ListLevel properties from parent ListLevel and 
            // gets formatting missed in parent ListLevel from parent IRunAttrSource.
            mCachedListLevelFinal = listLevel != null ? new ListLevel(listLevel, mParentRunAttrs) : null;
        }

        return mCachedListLevelFinal;
    }

    int getListId() { return (/*int*/Integer)mParentParaAttrs.fetchParaAttr(ParaAttr.LIST_ID); }
    void setListId(int value)
    {
        Object leftIndentAttrValue = mParentParaAttrs.getDirectParaAttr(ParaAttr.LEFT_INDENT);
        int leftIndent = (leftIndentAttrValue != null) ? (/*int*/Integer)leftIndentAttrValue : 0;

        // Word updates direct left indent when list is applied. We do the same, but if the current paragraph
        // already has applied list, at first let's subtract indent that is related to the old list.
        int oldListLeftIndent = 0;
        if ((leftIndent != 0) && (value != 0) && (getListLevel() != null))
        {
            oldListLeftIndent = getListLevel().getParaPr().getLeftIndent() + getListLevel().getParaPr().getFirstLineIndent();
            // Do not subtract if paragraph indent is not related to the old list indent.
            if (leftIndent < oldListLeftIndent)
                oldListLeftIndent = 0;
        }

        mParentParaAttrs.setParaAttr(ParaAttr.LIST_ID, value);
        invalidate();

        // If explicitly no list is set or there is no direct left indent or list level is not specified, exit.
        if ((value == 0) || (leftIndentAttrValue == null) || (getListLevel() == null))
            return;

        // Word updates direct left indent when list is applied.
        if (leftIndent == 0)
        {
            // It seems that Word always remove zero left indent even if it overrides style. 
            mParentParaAttrs.removeParaAttr(ParaAttr.LEFT_INDENT);
        }
        else
        {
            // Otherwise it updates direct left indent by (LeftIndent + FirstLineIndent) from being applied list.
            int newLeftIndent = leftIndent - oldListLeftIndent + 
                getListLevel().getParaPr().getLeftIndent() + getListLevel().getParaPr().getFirstLineIndent();
            mParentParaAttrs.setParaAttr(ParaAttr.LEFT_INDENT, newLeftIndent);
        }
    }

    int getListIdFinal()
    {
        Paragraph paragraph = ms.as(mParentParaAttrs, Paragraph.class);
        if (paragraph != null)
            return (/*int*/Integer)paragraph.fetchParaAttr(ParaAttr.LIST_ID, RevisionsView.FINAL);

        return getListId();
    }

    /// <summary>
    /// Invalidates cached list level.
    /// </summary>
    private void invalidate()
    {
        mCachedListLevel = null;
    }

    private /*final*/ IParaAttrSource mParentParaAttrs;
    private /*final*/ IRunAttrSource mParentRunAttrs;
    private /*final*/ ListCollection mLists;
    private ListLevel mCachedListLevel;
    private ListLevel mCachedListLevelFinal;

    /// <summary>
    /// Maximum allowed list count.
    /// </summary>
    /// <remarks>
    /// For DOC format there are following ranges defined:
    /// 0x0000, 0xf801 - no list
    /// 0x0001 - 0x07FE - normal lists
    /// 0xF802 - 0xFFFF - lists whose left indentation should be ignored.
    /// For DOCX format there is no limitation specified by specs but it seems that Word has 
    /// limited slots for list definitions and stops adding new lists after list count reaches 2046.
    /// </remarks>
    private static final int MAX_LIST_COUNT = 0x7FE;
}

