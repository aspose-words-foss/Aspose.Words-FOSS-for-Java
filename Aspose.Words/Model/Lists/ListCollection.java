// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import java.util.Iterator;
import com.aspose.IntToIntDictionary;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;
import com.aspose.StringUtil;


/// <summary>
/// Stores and manages formatting of bulleted and numbered lists used in a document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-lists/">Working with Lists</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>A list in a Microsoft Word document is a set of list formatting properties.
/// The formatting of the lists is stored in the <see cref="ListCollection"/> collection separately
/// from the paragraphs of text.</p>
///
/// <p>You do not create objects of this class. There is always only one <see cref="ListCollection"/>
/// object per document and it is accessible via the <see cref="Aspose.Words.DocumentBase.Lists"/> property.</p>
///
/// <p>To create a new list based on a predefined list template or based on a list style,
/// use the <see cref="Add(Style)"/> method.</p>
///
/// <p>To create a new list with formatting identical to an existing list,
/// use the <see cref="AddCopy(List)"/> method.</p>
///
/// <p>To make a paragraph bulleted or numbered, you need to apply list formatting
/// to a paragraph by assigning a <see cref="List"/> object to the
/// <see cref="ListFormat.List"/> property of <see cref="ListFormat"/>.</p>
///
/// <p>To remove list formatting from a paragraph, use the <see cref="ListFormat.RemoveNumbers"/>
/// method.</p>
///
/// <p>If you know a bit about WordprocessingML, then you might know it defines separate concepts
/// for "list" and "list definition". This exactly corresponds to how list formatting is stored
/// in a Microsoft Word document at the low level. List definition is like a "schema" and
/// list is like an instance of a list definition.</p>
///
/// <p>To simplify programming model, Aspose.Words hides the distinction between list and list
/// definition in much the same way like Microsoft Word hides this in its user interface.
/// This allows you to concentrate more on how you want your document to look like, rather than
/// building low-level objects to satisfy requirements of the Microsoft Word file format.</p>
///
/// <p>It is not possible to delete lists once they are created in the current version of Aspose.Words.
/// This is similar to Microsoft Word where user does not have explicit control over list definitions.</p>
///
/// <seealso cref="List"/>
/// <seealso cref="ListLevel"/>
/// <seealso cref="ListFormat"/>
/// </remarks>
public class ListCollection implements Iterable<List>, Cloneable
{
    /// <summary>
    /// Ctor.
    /// </summary>
    ListCollection(DocumentBase document)
    {
        Debug.assertTrue(document != null);
        mDocument = document;
    }

    /// <summary>
    /// Gets the enumerator object that will enumerate lists in the document.
    /// </summary>
    public Iterator<List> iterator()
    {
        return mLists.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <overloads>Creates a new list and adds it to the collection of lists in the document.</overloads>
    /// <summary>
    /// Creates a new list based on a predefined template and adds it to the collection of lists in the document.
    /// </summary>
    /// <param name="listTemplate">The template of the list.</param>
    /// <returns>The newly created list.</returns>
    /// <remarks>
    /// <p>Aspose.Words list templates correspond to the 21 list templates available
    /// in the Bullets and Numbering dialog box in Microsoft Word 2003.</p>
    ///
    /// <p>All lists created using this method have 9 list levels.</p>
    /// </remarks>
    public List add(/*ListTemplate*/int listTemplate)
    {
        return ListFactory.createList(this, listTemplate);
    }

    /// <summary>
    /// Creates a new single level list based on the predefined template and adds it to the list collection in the document.
    /// </summary>
    public List addSingleLevelList(/*ListTemplate*/int listTemplate)
    {
        return ListFactory.createSingleLevelList(this, listTemplate);
    }

    /// <summary>
    /// Creates a new list that references a list style and adds it to the collection of lists in the document.
    /// </summary>
    /// <param name="listStyle">The list style.</param>
    /// <returns>The newly created list.</returns>
    /// <remarks>
    /// <p>The newly created list references the list style. If you change the properties of the list
    /// style, it is reflected in the properties of the list. Vice versa, if you change the properties
    /// of the list, it is reflected in the properties of the list style.</p>
    /// </remarks>
    public List add(Style listStyle)
    {
        if (listStyle == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "listStyle");
        if (listStyle.getDocument() != getDocument())
            throw new IllegalArgumentException("The list style belongs to a different document.");
        if (listStyle.getType() != StyleType.LIST)
            throw new IllegalArgumentException("The style is not a list style.");

        // This creates a copy of a list style that does not reference the original list style.
        List newList = addCopy(listStyle.getList());

        // By restoring the reference to the list style we now have got a list definition
        // that reference the list style.
        newList.getListDef().setListStyleIstd(listStyle.getIstd());

        return newList;
    }

    /// <summary>
    /// Creates a new list by copying the specified list and adding it to the collection of lists in the document.
    /// </summary>
    /// <param name="srcList">The source list to copy from.</param>
    /// <returns>The newly created list.</returns>
    /// <remarks>
    /// <p>The source list can be from any document. If the source list belongs to a different document,
    /// a copy of the list is created and added to the current document.</p>
    ///
    /// <p>If the source list is a reference to or a definition of a list style,
    /// the newly created list is not related to the original list style.</p>
    /// </remarks>
    public List addCopy(List srcList)
    {
        return addCopy(srcList, true);
    }

    /// <summary>
    /// Creates a new list by copying the specified list and adding it to the collection of lists in the document.
    /// </summary>
    /// <remarks>
    /// When AW copies only style from other document, AW shouldn't import all dependent styles.
    /// </remarks>
    List addCopy(List srcList, boolean isImport)
    {
        if (srcList == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "srcList");

        List newList;

        boolean isSameDocument = (getDocument() == srcList.getDocument());
        if (isSameDocument || !isImport)
        {
            // WORDSNET-14595 If we copy lists between different documents,
            // then first try to use existing list with the same 'ListDefId'.
            // These ids are random generated so chances they match
            // for different styles are near zero.
            // Additional information can be found in similar WORDSNET-4174
            if (!isSameDocument)
            {
                newList = findListWithEqualDefIdAndOverrides(srcList);
                if (newList != null)
                    return newList;
            }

            // Clone and add the list.
            newList = srcList.deepClone(mDocument, getNextAvailableListId());
            addList(newList);

            // Clone and add the list definition.
            ListDef newListDef = srcList.getListDef().deepClone(getDocument(), getNextAvailableListDefId());
            addListDef(newListDef);
            newList.setListDefId(newListDef.getListDefId());
        }
        else
        {
            // When the list is from a different document, import it because it might reference paragraph styles.
            ImportContext context = new ImportContext(srcList.getDocument(), getDocument(), ImportFormatMode.USE_DESTINATION_STYLES);

            // Avoid reusing the list definition of this list
            // because we want to add copy in this method by design.
            context.setNonReusableListDefId(srcList.getListDefId());

            newList = fetchListByListId(importList(context, srcList.getListId(), false));
        }

        // If the list was a definition of a list style or a reference to a list style,
        // break it off because this function creates a standalone copy of a list.
        // It also means there is no hassle when importing the list style from a different document.
        newList.getListDef().setListStyleIstd(StyleIndex.NIL);

        return newList;
    }

    /// <summary>
    /// Gets a list by a list identifier.
    /// </summary>
    /// <param name="listId">The list identifier.</param>
    /// <returns>Returns the list object. Returns <c>null</c> if a list with the specified identifier was not found.</returns>
    /// <remarks>
    /// <p>You don't normally need to use this method. Most of the time you apply list formatting
    /// to paragraphs just by settings the <see cref="ListFormat.List"/> property
    /// of the <see cref="ListFormat"/> object.</p>
    /// </remarks>
    public List getListByListId(int listId)
    {
        int translatedListId = mListIdTranslationTable.get(listId);

        if (IntToIntDictionary.isNullSubstitute(translatedListId))
            return null;

        int listIndex = translatedListId - 1;

        // To the user, this function is to return null if the list with this list id is not found.
        // It does not matter that at the moment list id is actually an index, it might change later.
        if ((listIndex < 0) || (listIndex >= getCount()))
            return null;
        else
            return this.get(listIndex);
    }

    /// <summary>
    /// Gets a list by its unique identifier safely.
    /// In some corrupted documents I get list identifiers (ilfos) that refers to non-existent list,
    /// in this case we create a new list with default properties.
    /// </summary>
    List fetchListByListId(int listId)
    {
        // If there are no lists and the user requests one, we have to create one.
        if (getCount() == 0)
            addEmpty(ListType.HYBRID_MULTI_LEVEL);

        int translatedListId = mListIdTranslationTable.get(listId);

        int listIndex = IntToIntDictionary.isNullSubstitute(translatedListId) ? listId - 1 : translatedListId - 1;

        // If the list index is out of the valid range, bring it back into the range.
        // This could make the paragraph to refer to a different list, but its acceptable.
        if (listIndex < 0)
            listIndex = 0;
        else if (listIndex >= getCount())
            listIndex = getCount() - 1;

        return this.get(listIndex);
    }

    /// <summary>
    /// Gets a list by its unique identifier safely.
    ///
    /// If the list is a list style reference, resolves it into the corresponding list style definition.
    /// The resolution is needed to properly calculate full formatting attributes.
    ///
    /// The thing is that while DOC stores a copy of all attributes in the list style reference,
    /// DOCX and WordML do not store anything in the list style reference.
    /// </summary>
    List fetchListByListIdResolveStyleReference(int listId)
    {
        List list = fetchListByListId(listId);
        if (list.isListStyleReference())
        {
            // The list is a list style reference, return the list style definition.
            return list.getStyle().getList();
        }
        else
        {
            // Most likely, the list is not a list style reference, therefore return it.
            return list;
        }
    }

    /// <summary>
    /// Creates a new list and a list definition and returns the list.
    /// The list definition has 9 levels, but formatting for the levels is empty.
    /// </summary>
    List addEmpty(/*ListType*/int listType)
    {
        ListDef listDef = addEmptyListDef(listType, getNextAvailableListDefId());

        return addList(listDef);
    }

    /// <summary>
    /// Creates a new list with provided list definition.
    /// </summary>
    private List addList(ListDef listDef)
    {
        List list = new List(mDocument, getNextAvailableListId());
        list.setListDefId(listDef.getListDefId());
        addList(list);

        return list;
    }

    /// <summary>
    /// Creates a new list definition.
    /// The list definition has 9 levels, but formatting for the levels is empty.
    /// </summary>
    private ListDef addEmptyListDef(/*ListType*/int listType, int listDefId)
    {
        // Template code seems to be just random. I think it is enough to make it same as listDefId.
        int templateCode = listDefId;
        ListDef listDef = new ListDef(mDocument, listDefId, listType, templateCode);
        addListDef(listDef);
        return listDef;
    }

    /// <summary>
    /// Adds a list to the collection. The list's owner document must be this document.
    /// </summary>
    void addList(List list)
    {
        Debug.assertTrue((list != null) && (list.getDocument() == this.getDocument()));

        if (!mListIdTranslationTable.containsKey(list.getListId()))
        {
            msArrayList.add(mLists, list);
            mListIdTranslationTable.add(list.getListId(), mLists.size());
        }
        else
        {
            // WARN. Non-unique ListId.
        }
    }

    /// <summary>
    /// Adds a list definition to the collection. The list definition's owner document must be this document.
    /// </summary>
    void addListDef(ListDef listDef)
    {
        Debug.assertTrue((listDef != null) && (listDef.getDocument() == this.getDocument()));
        msArrayList.add(mListDefs, listDef);
    }

    /// <summary>
    /// Gets a list definition by a zero based index.
    /// </summary>
    ListDef getListDefByIndex(int index)
    {
        return mListDefs.get(index);
    }

    /// <summary>
    /// Gets a list definition by a listDefId. Returns null if not found.
    /// </summary>
    ListDef getListDefByListDefId(int listDefId)
    {
        int defIdIdx = getIndexOfListDefByListDefId(listDefId);
        return (defIdIdx != -1) ? mListDefs.get(defIdIdx) : null;
    }

    /// <summary>
    /// Safely gets a list definition by a list definition id.
    ///
    /// SLOW At the moment linear search, don't use too often.
    /// </summary>
    ListDef fetchListDefByListDefId(int listDefId)
    {
        int i = getIndexOfListDefByListDefId(listDefId);

        if (i >= 0)
        {
            return getListDefByIndex(i);
        }
        else
        {
            // RK A non existent list definition was requested. MS Word seems to default to the first
            // list definition and so do we (but only if it does exist). Otherwise create and return
            // an empty ListDef.
            if (getListDefCount() > 0)
                return getListDefByIndex(0);
            else
                return addEmptyListDef(ListType.HYBRID_MULTI_LEVEL, listDefId);
        }
    }

    /// <summary>
    /// Gets the index of a list definition by list definition id or negative value if not found.
    ///
    /// SLOW At the moment linear search, don't use too often.
    /// </summary>
    int getIndexOfListDefByListDefId(int listDefId)
    {
        for (int i = 0; i < getListDefCount(); i++)
        {
            if (getListDefByIndex(i).getListDefId() == listDefId)
                return i;
        }
        return -1;
    }

    /// <summary>
    /// Creates a deep copy of all lists. The new lists will have same ids as the originals.
    /// Suitable only when copying a complete document.
    /// </summary>
    ListCollection deepClone(DocumentBase dstDocument, INodeCloningListener nodeCloningListener)
    {
        Debug.assertTrue((dstDocument != null) && (dstDocument != mDocument));

        ListCollection lhs = (ListCollection)memberwiseClone();

        lhs.mDocument = dstDocument;

        // Since we are copying all lists, it means all list ids, list def ids, picture bullet ids
        // in the destination document will remain same as in the source document and therefore
        // we simply clone without importing and changes to the ids.

        lhs.mListIdTranslationTable = new IntToIntDictionary();

        // Copy lists.
        lhs.mLists = new ArrayList<List>();
        for (List list : mLists)
            lhs.addList(list.deepClone(dstDocument, list.getListId()));

        // Copy list definitions.
        lhs.mListDefs = new ArrayList<ListDef>();
        for (ListDef listDef : mListDefs)
            lhs.addListDef(listDef.deepClone(dstDocument, listDef.getListDefId()));

        // Copy list bullets.
        lhs.mPictureBullets = new ArrayList<Shape>();
        for (Shape shape : mPictureBullets)
        {
            // We do not import but clone because cloning a complete document.
            Shape dstShape = (Shape)shape.deepClone(true, nodeCloningListener);
            dstShape.setDocument(dstDocument);
            lhs.addPictureBullet(dstShape);
        }

        return lhs;
    }

    /// <summary>
    /// Copies the list referenced by the specified list id in the source document into
    /// this document and returns the list id of the newly created list.
    /// Also imports the referenced list style and linked paragraph styles (if any).
    /// </summary>
    int importList(ImportContext context, int srcListId)
    {
        return importList(context, srcListId, true);
    }

    /// <summary>
    /// Copies list referenced by the specified list id in the source collection into this collection.
    /// </summary>
    /// <remarks>
    /// It does not import referenced list style and picture bullets.
    /// But it copies level-linked paragraph styles.
    /// </remarks>
    /// <returns>Imported list id.</returns>
    int copyList(int srcListId, ImportContext context)
    {
        List srcList = context.getSrcLists().getListByListId(srcListId);
        if (srcList == null)
            return 0;

        // We must override list definition with the same id, so remove existing one from the destination collection.
        int dstIdx = getIndexOfListDefByListDefId(srcList.getListDefId());
        // WORDSNET-17878 Skip removing of the list definition when it was imported previously from the source.
        if ((dstIdx != -1) && !context.getImportedListDefIds().containsKey(srcList.getListDefId()))
            getListDefs().remove(dstIdx);

        // ListDefId that equals to some id in source collection was removed from the destination collection above,
        // so there is nothing to reuse. But let's set this id to non-reusable value to speed up import.
        context.setNonReusableListDefId(srcList.getListDefId());

        boolean isImported = importListCore(context, srcList);
        List dstList = context.getDstLists().getListByListId(context.getImportedListIds().get(srcListId));

        if (!isImported)
            return dstList.getListId();

        // If the list that is being copied is a definition or a reference to a list style, copy the list style.
        if (srcList.getStyle() != null)
            dstList.getListDef().setListStyleIstd(context.getDstStyles().copyStyle(srcList.getStyle(), context));

        // Perform per-level copying tasks.
        ListLevelCollection srcLevels = srcList.getListDef().getLevels();
        ListLevelCollection dstLevels = dstList.getListDef().getLevels();
        for (int i = 0; i < srcLevels.getCount(); i++)
        {
            ListLevel srcLevel = srcLevels.get(i);
            ListLevel dstLevel = dstLevels.get(i);
            copyLinkedParagraphStyle(context, srcLevel, dstLevel);
        }

        return dstList.getListId();
    }

    /// <summary>
    ///  Generates unique random ListDefId.
    /// </summary>
    int makeUniqueListDefId()
    {
        int newListDefId = UniqueIdManager.generateInteger();

        // Do not generate zero nsids to avoid collisions with non-existent list definitions that might be set for list.
        while ((newListDefId == 0) || (getListDefByListDefId(newListDefId) != null))
            newListDefId = UniqueIdManager.generateInteger();

        return newListDefId;
    }

    /// <summary>
    /// Imports the list referenced by the specified list id in the source document into
    /// this document and returns the list id of the newly created list.
    /// Imports the paragraph styles linked to list levels (if any).
    /// Optionally imports the referenced list style.
    /// </summary>
    private int importList(ImportContext context, int srcListId, boolean isImportListStyle)
    {
        Debug.assertTrue(context != null);

        // WORDSNET-1462 System.ArgumentException during list import.
        // Paragraph style references a non-existing list, this was causing import of the last
        // list twice. Fixed to return 0 and therefore imported paragraph style becomes non list.
        List srcList = context.getSrcLists().getListByListId(srcListId);
        if (srcList == null)
            return 0;

        boolean isImported = importListCore(context, srcList);
        List dstList = context.getDstLists().getListByListId(context.getImportedListIds().get(srcListId));

        if (!isImported)
            return dstList.getListId();

        // If the list that is being imported is a definition or a reference to a list style, import the list style.
        if (isImportListStyle && (srcList.getStyle() != null))
        {
            Style dstStyle = context.getDstStyles().importStyle(context, srcList.getStyle());

            if (srcList.getListDef().isListStyleDefinition() && !context.isImported(srcList.getStyle()))
            {
                // WORDSNET-11757 We must preserve IsListStyleDefinition on import - use dstStyle.List.ListDef.
                dstList.setListDefId(dstStyle.getList().getListDefId());
                context.getImportedListDefIds().set(srcList.getListDefId(), dstList.getListDefId());
                return dstList.getListId();
            }

            dstList.getListDef().setListStyleIstd(dstStyle.getIstd());
        }

        // Perform per-level importing tasks.
        // WORDSNET-26460 There is some problem when going through List.GetListLevels because it goes via "terminal list def",
        // which goes into the list style and so on. I don't understand "terminal list def" fully so I think when importing
        // we need to deal with direct data, hence get the list, list def and list levels directly.
        ListLevelCollection srcLevels = srcList.getListDef().getLevels();
        ListLevelCollection dstLevels = dstList.getListDef().getLevels();
        for (int i = 0; i < srcLevels.getCount(); i++)
        {
            ListLevel srcLevel = srcLevels.get(i);
            ListLevel dstLevel = dstLevels.get(i);
            importLinkedParagraphStyle(context, srcLevel, dstLevel);
            importPictureBullet(context, srcLevel, dstLevel);
        }

        return dstList.getListId();
    }

    /// <summary>
    /// Core method for importing lists.
    /// </summary>
    /// <returns>
    /// <c>true</c>, if a list and its definition was actually imported. Otherwise, if it was imported earlier or just reused,
    /// it returns <c>false</c>.
    /// </returns>
    private boolean importListCore(ImportContext context, List srcList)
    {
        Debug.assertTrue(context != null);
        Debug.assertTrue(srcList != null);

        // If the list was already imported, just return.
        if (context.getImportedListIds().containsKey(srcList.getListId()))
            return false;

        List dstList;
        // WORDSNET-4174 MS Word reuses lists with equal DefIds and equal Overrides.
        if (context.canReuseListDefId(srcList.getListDefId()))
        {
            dstList = findListWithEqualDefIdAndOverrides(srcList);

            // WORDSNET-15788 Mimic MSW and re-use only lists which initially were placed in the destination.
            if ((dstList != null) && !context.getImportedListDefIds().containsValue(dstList.getListDefId()))
            {
                context.getImportedListIds().add(srcList.getListId(), dstList.getListId());
                return false;
            }
        }

        // Clone and add the list object.
        dstList = srcList.deepClone(mDocument, getNextAvailableListId());
        addList(dstList);
        context.getImportedListIds().add(srcList.getListId(), dstList.getListId());

        // Deal with the list definition.
        ListDef srcListDef = srcList.getListDef();
        // If the list definition was already imported, just return.
        if (context.getImportedListDefIds().containsKey(srcListDef.getListDefId()))
        {
            dstList.setListDefId(context.getImportedListDefIds().get(srcListDef.getListDefId()));
            dstList.clearListDefCache();
            return false;
        }

        // First try to reuse existing ListDef with the same Id.
        ListDef dstListDef = getListDefByListDefId(srcListDef.getListDefId());
        if ((dstListDef != null) && context.canReuseListDefId(srcListDef.getListDefId()))
        {
            context.getImportedListDefIds().set(srcListDef.getListDefId(), dstListDef.getListDefId());
            return false;
        }

        // Clone and add the list definition object.
        // Generate new ID if same ID already exists in destination collection,
        // otherwise use source ListDefId.
        int dstListDefId = (dstListDef == null) ? srcListDef.getListDefId() : makeUniqueListDefId();

        dstListDef = srcListDef.deepClone(mDocument, dstListDefId);
        addListDef(dstListDef);

        // Add imported list definition id to the cache so it will not be imported again.
        context.getImportedListDefIds().set(srcListDef.getListDefId(), dstListDefId);

        // Apply imported list definition to the list.
        dstList.setListDefId(dstListDefId);

        return true;
    }

    /// <summary>
    /// Imports a paragraph style if one is linked to the list level.
    /// </summary>
    private static void importLinkedParagraphStyle(ImportContext context, ListLevel srcLevel, ListLevel dstLevel)
    {
        Style srcLinkedStyle = srcLevel.getLinkedStyle();
        if (srcLinkedStyle != null)
        {
            // WORDSNET-RESILIENCY 3321 - A document seems to have a list linked to a character style.
            // MS Word does not complain and show the style, but I think this is wrong.
            // Added resiliency to avoid importing linked style if it is not a paragraph style.
            Style dstLinkedStyle = srcLinkedStyle.getType() == StyleType.PARAGRAPH
                ? context.getDstStyles().importStyle(context, srcLinkedStyle)
                : null;

            dstLevel.setLinkedStyle(dstLinkedStyle);
        }
    }

    /// <summary>
    /// Copies linked style from a source to destination list level.
    /// </summary>
    private static void copyLinkedParagraphStyle(ImportContext context, ListLevel srcLevel, ListLevel dstLevel)
    {
        Style srcLinkedStyle = srcLevel.getLinkedStyle();
        if (srcLinkedStyle != null)
        {
            int dstLinkedStyleId = context.getDstStyles().copyStyle(srcLinkedStyle, context);
            dstLevel.setLinkedStyle(context.getDstStyles().getByIstd(dstLinkedStyleId, false));
        }
    }

    /// <summary>
    /// Imports a picture bullet if there is one specified for the list level.
    /// </summary>
    private static void importPictureBullet(ImportContext context, ListLevel srcLevel, ListLevel dstLevel)
    {
        if (!srcLevel.hasPictureBullet())
            return;

        int dstPictureBulletId = context.getImportedPictureBulletIds().get(srcLevel.getPictureBulletId());
        if (!IntToIntDictionary.isNullSubstitute(dstPictureBulletId))
        {
            // The picture bullet was already imported, don't import it again.
            dstLevel.setPictureBulletId(dstPictureBulletId);
        }
        else
        {
            // The picture bullet was not imported. Import it and add to the document and to the context.
            Shape srcShape = srcLevel.getPictureBullet();
            Shape dstShape = (Shape)context.getDstDoc().importNode(srcShape, true);
            dstLevel.setPictureBulletId(context.getDstLists().addPictureBullet(dstShape));
            context.getImportedPictureBulletIds().add(srcLevel.getPictureBulletId(), dstLevel.getPictureBulletId());
        }
    }

    /// <summary>
    /// List definitions are sorted by ListDefId, so selecting the biggest
    /// and adding one will generate next id.
    /// </summary>
    int getNextAvailableListDefId()
    {
        if (mMaxUsedListDefId == 0)
        {
            for (ListDef listDef : mListDefs)
                mMaxUsedListDefId = Math.max(mMaxUsedListDefId, listDef.getListDefId());
        }
        return ++mMaxUsedListDefId;
    }

    /// <summary>
    /// At the moment ListId is a 1-based index into the lists collection.
    /// </summary>
    int getNextAvailableListId()
    {
        // andrnosk: WORDSNET-8699 To get correct next available ListId,
        // we have to find out current maximum ListId and increase it.
        if (mMaxUsedListId == 0)
        {
            for (List list : mLists)
                mMaxUsedListId = Math.max(mMaxUsedListId, list.getListId());
        }
        return ++mMaxUsedListId;
    }

    /// <summary>
    /// Clears cached list definition with a specified DefId.
    /// </summary>
    void clearCachedListDef(int defId)
    {
        for (List list : mLists)
        {
            if (list.getListDefId() == defId)
                list.clearListDefCache();
        }
    }

    /// <summary>
    /// Returns list with equal to the specified list's <see cref="List.ListDefId"/> and <see cref="List.Overrides"/>.
    /// </summary>
    private List findListWithEqualDefIdAndOverrides(List refList)
    {
        List foundList = null;

        for (List list : mLists)
        {
            if ((list.getListDefId() == refList.getListDefId()) && list.getOverrides().equals(refList.getOverrides()))
            {
                // Prefer a list with the same DurableId to return.
                if (list.getDurableId() == refList.getDurableId())
                    return list;

                foundList = list;
            }
        }

        return foundList;
    }

    /// <summary>
    /// Gets the count of numbered and bulleted lists in the document.
    /// </summary>
    public int getCount() { return mLists.size(); }

    /// <summary>
    /// Gets a list by index.
    /// </summary>
    public List get(int index) { return mLists.get(index); }

    /// <summary>
    /// Gets a list by <see cref="ListDef"/> name. Returns <c>null</c> if not found.
    /// </summary>
    /// <remarks>
    /// MS Word does not give an ability to create more than one list based on named <see cref="ListDef"/>
    /// through its GUI and AW does not set <see cref="ListDef.Name"/> anywhere outside of file reading
    /// routines so <see cref="ListDef"/> name can be the unique identifier for a list.
    /// </remarks>
    /// <param name="listDefName"></param>
    /// <returns></returns>
    List get(String listDefName)
    {
        if (!StringUtil.hasChars(listDefName))
            return null;

        // An enumeration through mListDefs will not create unnecessary ListDef instances.
        for (ListDef listDef : mListDefs)
        {
            if (StringUtil.equalsIgnoreCase(listDef.getName(), listDefName))
                return ensureListByListDef(listDef);
        }

        return null;
    }

    private List getListByListDef(ListDef listDef)
    {
        for (List list : mLists)
        {
            // A comparison by ListDefId will not cache unnecessary ListDef instances.
            if (list.getListDefId() == listDef.getListDefId())
                return list;
        }

        return null;
    }

    private List ensureListByListDef(ListDef listDef)
    {
        return (getListByListDef(listDef) != null ? getListByListDef(listDef) : addList(listDef));
    }

    /// <summary>
    /// Gets the owner document.
    /// </summary>
    public DocumentBase getDocument() { return mDocument; }

    int getListDefCount() { return mListDefs.size(); }

    int getPictureBulletCount() { return mPictureBullets.size(); }

    /// <summary>
    /// This value specifies that all abstract numbering definitions with an abstractNumId value higher
    /// than the NumIdMacAtCleanup value have not yet been reviewed by 3rd party software.
    /// </summary>
    /// <remarks>
    /// This property doesn't affect document view. We just preserves its value during conversion.
    /// </remarks>
    Object getNumIdMacAtCleanup() { return mNumIdMacAtCleanup; }; void setNumIdMacAtCleanup(Object value) { mNumIdMacAtCleanup = value; };

    private Object mNumIdMacAtCleanup;

    /// <summary>
    /// Returns ListId to 1-based index translation table.
    /// </summary>
    /// <remarks>Exposed for validation purposes only.</remarks>
    IntToIntDictionary getListIdTranslationTable() { return mListIdTranslationTable; }

    /// <summary>
    /// Returns list definition collection.
    /// </summary>
    /// <remarks>Exposed for validation purposes only.</remarks>
    ArrayList<ListDef> getListDefs() { return mListDefs; }

    /// <summary>
    /// Adds a picture bullet shape to the collection of picture bullets available in this document.
    /// </summary>
    int addPictureBullet(Shape shape)
    {
        if (shape == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "shape");

        if (shape.getParentNode() != null)
            throw new IllegalArgumentException("The picture bullet shape must have no parent node.");

        if (shape.getDocument() != mDocument)
            throw new IllegalArgumentException("The picture bullet shape must belong to this document.");

        shape.isPictureBullet(true);

        msArrayList.add(mPictureBullets, shape);
        return mPictureBullets.size() - 1;
    }

    /// <summary>
    /// Gets a picture bullet by index.
    /// </summary>
    Shape getPictureBullet(int index)
    {
        return mPictureBullets.get(index);
    }

    /// <summary>
    /// Sets a picture bullet by index.
    /// </summary>
    void setPictureBullet(int index, Shape value)
    {
        if (mPictureBullets.size() == index)
            msArrayList.add(mPictureBullets, value);
        else
            mPictureBullets.set(index, value);
    }

    /// <summary>
    /// Expands directly applied list into destination collection.
    /// </summary>
    void expandDirectList(ParaPr srcParaPr, ParaPr dstParaPr)
    {
        // Expand list formatting of the list applied to the paragraph directly.
        ListLevel listLevel = getDirectListLevel(srcParaPr);
        if (listLevel != null)
        {
            clearFirstListTabStop(dstParaPr);
            listLevel.getParaPr().expandTo(dstParaPr);

            List list = fetchListByListId(srcParaPr.getListId());
            ListLevelOverride over = list.getFormattingOverride(srcParaPr.getListLevel());
            if (over != null)
                over.getListLevel().getParaPr().expandTo(dstParaPr);
        }
        else
        {
            // WORDSNET-1545, 625 - Microsoft Word seems to be using zero indent for a paragraph that
            // is not bulleted, but formatted with a style that has bullets. We hack to mimic the same.
            if (srcParaPr.isExplicitlyNotListItem())
            {
                dstParaPr.setFirstLineIndent(0);
                dstParaPr.setLeftIndent(0);

                // Clear List tab stops.
                if (dstParaPr.contains(ParaAttr.TAB_STOPS))
                {
                    for (int i = 0; i < dstParaPr.getTabStops().getCount(); i++)
                    {
                        TabStop tabStop = dstParaPr.getTabStops().get(i);
                        if (tabStop.getAlignment() == TabAlignment.LIST)
                            tabStop.setAlignment(TabAlignment.CLEAR);
                    }
                    dstParaPr.getTabStops().removeClearingWithTolerance();
                }
            }
        }
    }

    /// <summary>
    /// Gets list level that is specified directly in the paragraph properties (not in a paragraph style).
    /// Returns null if the paragraph is not a list item.
    /// </summary>
    ListLevel getDirectListLevel(ParaPr paraPr)
    {
        return getDirectListLevel(paraPr, RevisionsView.ORIGINAL);
    }

    ListLevel getDirectListLevel(ParaPr paraPr, /*RevisionsView*/int revisionsView)
    {
        Object listId = paraPr.getDirectAttr(ParaAttr.LIST_ID, revisionsView);
        if (listId == null)
            return null;

        if ((/*int*/Integer)listId == 0)
            return null;

        List list = fetchListByListIdResolveStyleReference((/*int*/Integer)listId);

        Object listLevel = paraPr.getDirectAttr(ParaAttr.LIST_LEVEL, revisionsView);
        return list.getListLevelOverrideAware(listLevel != null ? (/*int*/Integer)listLevel : 0);
    }

    /// <summary>
    /// Removes list from collection. Does not update any references so document can become invalid.
    /// </summary>
    void removeCore(int listId)
    {
        IntToIntDictionary newTranslationTable = new IntToIntDictionary(mListIdTranslationTable.getCount() - 1);

        int removedListIndex = mListIdTranslationTable.get(listId);
        if (IntToIntDictionary.isNullSubstitute(removedListIndex))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "listId");

        mListIdTranslationTable.remove(listId);

        // Update translation table.
        IntToIntDictionary.Enumerator enumerator = mListIdTranslationTable.iterator();
        while (enumerator.moveNext())
        {
            int key = enumerator.getCurrentKey();
            int value = enumerator.getCurrentValue();

            if(value < removedListIndex)
                newTranslationTable.set(key, value);
            else if(value > removedListIndex)
                newTranslationTable.set(key, value - 1);
        }

        mLists.remove(removedListIndex - 1);
        mListIdTranslationTable = newTranslationTable;
    }

    /// <summary>
    /// Returns translated ListId value.
    /// </summary>
    int getTranslatedListId(int listId)
    {
        int newListId = mListIdTranslationTable.get(listId);

        return (IntToIntDictionary.isNullSubstitute(newListId))
            ? listId
            : newListId;
    }

    /// <summary>
    /// Makes 1-1 translation table which actually means translation absence.
    /// </summary>
    void removeTranslation()
    {
        for (int i = 0; i < mLists.size(); i++)
            this.get(i).setListId(i + 1);

        // Make 1to1 translation table so lists will be sorted by ListId.
        mListIdTranslationTable.clear();
        for (int i = 0; i < mLists.size(); i++)
            mListIdTranslationTable.add(i + 1, i + 1);
    }

    /// <summary>
    /// Clears list levels for a ListStyleReferences.
    /// </summary>
    void clearListStyleReferencesLevels()
    {
        for (ListDef listDef : mListDefs)
            if (listDef.isListStyleReference())
                listDef.getLevels().clear();
    }

    /// <summary>
    /// Fixes circular references listStyles.
    /// </summary>
    void fixUpListsWithCircularReferences()
    {
        ArrayList<Style> circularStyles = new ArrayList<Style>();
        ArrayList<ListDef> listDefsToFix = new ArrayList<ListDef>();
        for (ListDef listDef : mListDefs)
        {
            if (circularStyles.contains(listDef.getStyle()))
            {
                removeListDefFromCircularReference(listDef, circularStyles);
            }
            else
            {
                listDefsToFix.clear();
                listDefsToFix = getListDefsWithCircularReferences(listDefsToFix, listDef);

                for (ListDef listDefToFix : listDefsToFix)
                    removeListDefFromCircularReference(listDefToFix, circularStyles);
            }
        }
    }

    /// <summary>
    /// Sets value of the latest used list definition identifier value.
    /// </summary>
    /// <param name="value">New value for the last used list definition identifier.</param>
    void setMaxListDefId(int value)
    {
        mMaxUsedListDefId = value;
    }

    /// <summary>
    /// Updates ListDef in order to fix up circular reference.
    /// </summary>
    private static void removeListDefFromCircularReference(ListDef listDef, ArrayList<Style> circularStyles)
    {
        Style listStyle = listDef.getStyle();
        if (!circularStyles.contains(listStyle))
            msArrayList.add(circularStyles, listStyle);
        listStyle.getParaPr().remove(ParaAttr.LIST_ID);
        listDef.setListStyleIstd(StyleIndex.NO_LIST);
        listDef.purgeLevels();
    }

    /// <summary>
    /// Determines circular references listStyle collection, and returns ListDefs for such collection.
    /// </summary>
    private static ArrayList<ListDef> getListDefsWithCircularReferences(ArrayList<ListDef> listDefs, ListDef listDef)
    {
        if ((listDef == null) || (listDef.getStyle() == null) || listDef.isListStyleDefinition())
        {
            listDefs.clear();
            return listDefs;
        }

        if (listDefs.contains(listDef))
        {
            for (ListDef item : listDefs)
            {
                if (item == listDef)
                    break;
                listDefs.remove(item);
            }
            return listDefs;
        }

        msArrayList.add(listDefs, listDef);

        return getListDefsWithCircularReferences(listDefs, listDef.getStyle().getList().getListDef());
    }

    /// <summary>
    /// Change first occurred List tab stop to clear tab stop.
    /// </summary>
    private static void clearFirstListTabStop(ParaPr paraPr)
    {
        if (!paraPr.contains(ParaAttr.TAB_STOPS))
            return;

        TabStopCollection clearTabStops = new TabStopCollection();
        for (int i = 0; i < paraPr.getTabStops().getCount(); i++)
        {
            if (paraPr.getTabStops().get(i).getAlignment() == TabAlignment.CLEAR)
                clearTabStops.add(paraPr.getTabStops().get(i).deepClone());
        }

        for (int i = 0; i < paraPr.getTabStops().getCount(); i++)
            if (paraPr.getTabStops().get(i).getAlignment() == TabAlignment.LIST)
            {
                // andrnosk: WORDSNET-8769 We have to prevent clearing the same TabStop more then one time.
                if (clearTabStops.getByPositionTwips(paraPr.getTabStops().get(i).getPositionTwips()) != null)
                    continue;

                paraPr.getTabStops().add(new TabStop(paraPr.getTabStops().get(i).getPosition(), TabAlignment.CLEAR, TabLeader.NONE));
                break;
            }
    }

    private DocumentBase mDocument;

    /// <summary>
    /// Contains instantiated lists (corresponds to LFO structures in binary DOC).
    /// Paragraphs in the document reference lists in this array by index.
    /// The lists are never to be deleted from this array because it will invalidate list id indexes in the model.
    /// The lists in this array reference list definitions.
    /// </summary>
    private ArrayList<List> mLists = new ArrayList<List>();

    /// <summary>
    /// Contains list definitions (corresponds to LST structures in binary DOC).
    ///
    /// In a correct document list definitions are most likely sorted by their id so it is easy to find a
    /// list definition quickly. Maybe MS Word spec confirms list defs are sorted, but I certainly had
    /// corrupted documents that have duplicate ids and not sorted.
    /// So I decided to keep it as array for resiliency.
    /// </summary>
    private ArrayList<ListDef> mListDefs = new ArrayList<ListDef>();

    /// <summary>
    /// Contains shapes that represent picture bullets.
    /// Index of the array element is the picture bullet id.
    /// </summary>
    private ArrayList<Shape> mPictureBullets = new ArrayList<Shape>();

    /// <summary>
    /// Used to translate ListId to 1-based index in list array.
    /// </summary>
    /// <remarks>
    /// AM. In common ListId equal to 1-based index i.e element with ListId = 1 is read first and therefore placed first and so on.
    /// But there are few files that have lists unsorted and even more not 1-indexed array (I've seen file where just two lists with ListId equal 16792839 and 57)
    /// and we should keep this information because there can be paragraph which refers to such list by ListId.
    /// </remarks>
    private IntToIntDictionary mListIdTranslationTable = new IntToIntDictionary();

    private int mMaxUsedListId;
    private int mMaxUsedListDefId;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

