// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/05/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.RandomUtil;
import com.aspose.DrColor;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.FormatterPal;
import java.util.HashMap;
import com.aspose.EnumUtilPal;


/// <summary>
/// Allows to handle various tasks related to supporting of placeholder life cycle, including:
/// - creation of different placeholder building blocks into the document gallery;
/// - Reuse of placeholders if possible the same way MS Word does;
/// - counts references of placeholders and deletes the unused ones upon request.
/// </summary>
class SdtPlaceholderManager
{
    /// <summary>
    /// ctor
    /// </summary>
    SdtPlaceholderManager(DocumentBase doc)
    {
        if (doc instanceof Document)
        {
            mMainDocument = (Document)doc;
            mGlossary = mMainDocument.getGlossaryDocument();
        }
        else
        {
            mGlossary = (GlossaryDocument)doc;
            mMainDocument = null;
        }

        if (!isGlossaryEmpty())
            populatePlaceholderCacheFromGlossary();
    }

    /// <summary>
    /// Returns new or reuses existing placeholder, which is actually a building block in the gallery.
    /// Can return null if placeholder does not make sense for given SDT.
    /// </summary>
    /// <remarks>
    /// 1. Placeholders are reused between different levels of markup.
    /// 2. Generation of default SdtContent is different from generation of placeholder.
    /// </remarks>
    BuildingBlock fetchPlaceholderByType(/*SdtType*/int type)
    {
        BuildingBlock placeholder = mTypeCache[(int)type];
        return (placeholder != null) ? placeholder : createPlaceholder(type);
    }

    /// <summary>
    /// Searches for existing placeholder in the glossarydoc. If not found then either creates
    /// a new one (if isCreateNewPlaceholder == true), or returns null (if isCreateNewPlaceholder == false)
    /// </summary>
    BuildingBlock findPlaceholder(StructuredDocumentTag tag, boolean isCreateNewPlaceholder)
    {
        BuildingBlock placeholder = null;
        if (!"".equals(tag.getPlaceholderName()))
            placeholder = getPlaceholderByName(tag);

        // WORDSNET-15953 System.InvalidOperationException throws StructuredDocumentTag.PlaceholderName.
        // If SdtPlaceholderManager doesn't contain placeholder then try to find it in GlossaryDocument
        if (placeholder == null)
        {
            for (BuildingBlock buildingBlock : (Iterable<BuildingBlock>) getGlossary().getBuildingBlocks())
            {
                if (msString.equals(buildingBlock.getName(), tag.getPlaceholderName(), StringComparison.ORDINAL))
                {
                    placeholder = buildingBlock;
                    break;
                }
            }
        }

        if ((isCreateNewPlaceholder) && (placeholder == null))
            placeholder = fetchPlaceholderByType(tag.getSdtType());

        return placeholder;
    }

    /// <summary>
    /// Initialized reference counter to all zeros
    /// </summary>
    void startReferenceCounter()
    {
        mPlaceholderCounter.reset();
    }

    /// <summary>
    /// Increments placeholder reference count if this placeholder is used by sdt.
    /// </summary>
    void incrementReference(StructuredDocumentTag sdt)
    {
        if (sdt.getPlaceholder() != null)
            mPlaceholderCounter.incrementReference(sdt.getPlaceholder().getName());
    }

    /// <summary>
    /// Used to merge reference counters from document and glossary document.
    /// </summary>
    void appendPlaceholderReferences(Iterable<Object> referencedObjects)
    {
        for (Object r : referencedObjects)
            mPlaceholderCounter.incrementReference(r);
    }

    /// <summary>
    /// Removes unused placeholders from the document's glossary.
    /// </summary>
    boolean removeUnusedPlaceholders()
    {
        boolean result = false;
        for (BuildingBlock block : (Iterable<BuildingBlock>) getGlossary().getBuildingBlocks())
        {
            if ((block.getParentNode() != null) && isSdtPlaceholder(block) && !mPlaceholderCounter.isReferenced(block.getName()))
            {
                block.remove();
                result = true;
            }
        }

        return result;
    }

    /// <summary>
    /// Checks whether placeholder name is one of MS predefined placeholder name.
    /// </summary>
    static boolean isPredefinedPlaceholderName(String placeholderName)
    {
        switch (gStringSwitchMap.of(placeholderName))
        {
            case /*TEXT_PLACEHOLDER_NAME*/0:
            case /*LISTBOX_PLACEHOLDER_NAME*/1:
            case /*DATE_PLACEHOLDER_NAME*/2:
            case /*BUILDING_BLOCK_PLACEHOLDER_NAME*/3:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Creates custom Placeholder with the specified placeholder run text and returns its name.
    /// </summary>
    String createCustomPlaceholder(String placeholderRunText)
    {
        Debug.assertTrue(StringUtil.hasChars(placeholderRunText));
        return createPlaceholderCore(getUnusedCustomPlaceholderName(), placeholderRunText).getName();
    }

    /// <summary>
    /// Searches placeholder by name and returns it. If not found returns null.
    /// </summary>
    private BuildingBlock getPlaceholderByName(StructuredDocumentTag tag)
    {
        String name = tag.getPlaceholderName();
        BuildingBlock result = (mNameCache.containsKey(name))
            ? mNameCache.get(name)
            : null;
        if (result == null)
        {
            // possible case where non-MS Word names are used, so we have to look some more.
            result = getGlossary().getBuildingBlock(BuildingBlockGallery.STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT, null, name);
            if (result != null)
            {
                // some non MSWord documents do not reuse placeholders for same sdt types, so we might encounter different names
                // for the same type of sdt placeholder.
                addCompatiblePlaceholderToCache(name, result);
                addCompatiblePlaceholderToCache(tag.getSdtType(), result);
            }
        }

        return result;
    }

    /// <summary>
    /// Search for MS-style placeholders e.g. those that have
    /// </summary>
    private void populatePlaceholderCacheFromGlossary()
    {
        for (BuildingBlock block : (Iterable<BuildingBlock>) getGlossary())
        {
            if (block.getGallery() == BuildingBlockGallery.STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT)
            {
                switch (gStringSwitchMap.of(block.getName()))
                {
                    case /*TEXT_PLACEHOLDER_NAME*/0:
                        addTextPlaceholderToCahce(block);
                        break;
                    case /*DATE_PLACEHOLDER_NAME*/2:
                        addDatePlaceholderToCache(block);
                        break;
                    case /*LISTBOX_PLACEHOLDER_NAME*/1:
                        addListboxPlaceholderToCache(block);
                        break;
                    case /*BUILDING_BLOCK_PLACEHOLDER_NAME*/3:
                        addBuildingBlockPlaceholderToCache(block);
                        break;
                    default:
                        // there exist blocks with non-MS default, we will deal with them in GetPlaceholderByName function.
                        break;
                }
            }
        }
    }

    private void addTextPlaceholderToCahce(BuildingBlock placeholder)
    {
        addCompatiblePlaceholderToCache(SdtType.RICH_TEXT, placeholder);
        addCompatiblePlaceholderToCache(SdtType.PLAIN_TEXT, placeholder);
        addCompatiblePlaceholderToCache(SdtType.GROUP, placeholder); // MS Word uses text-placeholder for Group sdt, although it seems silly.

        addCompatiblePlaceholderToCache(TEXT_PLACEHOLDER_NAME, placeholder);
    }

    private void addListboxPlaceholderToCache(BuildingBlock placeholder)
    {
        addCompatiblePlaceholderToCache(SdtType.COMBO_BOX, placeholder);
        addCompatiblePlaceholderToCache(SdtType.DROP_DOWN_LIST, placeholder);

        addCompatiblePlaceholderToCache(LISTBOX_PLACEHOLDER_NAME, placeholder);
    }

    private void addDatePlaceholderToCache(BuildingBlock placeholder)
    {
        mTypeCache[(int)SdtType.DATE] = placeholder;
        mNameCache.put(DATE_PLACEHOLDER_NAME, placeholder);
    }

    private void addBuildingBlockPlaceholderToCache(BuildingBlock placeholder)
    {
        mTypeCache[(int)SdtType.BUILDING_BLOCK_GALLERY] = placeholder;
        mNameCache.put(BUILDING_BLOCK_PLACEHOLDER_NAME, placeholder);
    }

    /// <summary>
    /// Creates different placeholders depending on type.
    /// </summary>
    private BuildingBlock createPlaceholder(/*SdtType*/int type)
    {
        BuildingBlock placeholder;
        switch (type)
        {
            case SdtType.RICH_TEXT:
            case SdtType.PLAIN_TEXT:
            case SdtType.GROUP:
            {
                placeholder = createPlaceholderCore(TEXT_PLACEHOLDER_NAME, "Click here to enter text.");
                addTextPlaceholderToCahce(placeholder);
                break;
            }
            case SdtType.COMBO_BOX:
            case SdtType.DROP_DOWN_LIST:
            {
                placeholder = createPlaceholderCore(LISTBOX_PLACEHOLDER_NAME, "Choose an item.");
                addListboxPlaceholderToCache(placeholder);
                break;
            }
            case SdtType.DATE:
            {
                placeholder = createPlaceholderCore(DATE_PLACEHOLDER_NAME, "Click here to enter a date.");
                addDatePlaceholderToCache(placeholder);
                break;
            }
            case SdtType.BUILDING_BLOCK_GALLERY:
            {
                placeholder = createPlaceholderCore(BUILDING_BLOCK_PLACEHOLDER_NAME, "Choose a building block.");
                addBuildingBlockPlaceholderToCache(placeholder);
                break;
            }
            default:
            {
                placeholder = null; // all other Sdts either don't require placeholder, or are not allowed for public creation.
                break;
            }
        }

        return placeholder;
    }

    private BuildingBlock createPlaceholderCore(String placehoderName, String placeholderRunText)
    {
        BuildingBlock block = createBuildingBlock(placehoderName);
        getGlossary().appendChild(block);
        Section s = new Section(getGlossary());
        block.appendChild(s);

        Body b = new Body(getGlossary());
        s.appendChild(b);

        Paragraph para = new Paragraph(getGlossary());
        b.appendChild(para);

        Run run = new Run(getGlossary(), placeholderRunText);
        run.getRunPr().setAttr(FontAttr.ISTD, mStyle.getIstd());
        para.appendChild(run);

        return block;
    }

    /// <summary>
    /// All building block for different sdts share the same properties, and can be created by one function.
    /// </summary>
    /// <returns></returns>
    private BuildingBlock createBuildingBlock(String blockName)
    {
        ensureStyleCreation();

        BuildingBlock block = new BuildingBlock(getGlossary());
        block = block;
        block.setCategory(BUILDING_BLOCK_CATEGORY);
        block.setGallery(BuildingBlockGallery.STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT);
        block.setType(BuildingBlockType.STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT);
        block.setBehavior(BuildingBlockBehavior.CONTENT);
        block.setGuidInternal(RandomUtil.newGuid(blockName));
        block.setName(blockName);

        return block;
    }

    /// <summary>
    /// Create style to be used for placeholder runs, reuse one if already created.
    /// </summary>
    private void ensureStyleCreation()
    {
        if (mStyle == null)
        {
            getGlossary().getStyles().ensureMinimum();

            mStyle = getGlossary().getStyles().getByName("Placeholder Text", false);
            if (mStyle == null)
            {
                mStyle = getGlossary().getStyles().add(StyleType.CHARACTER, "Placeholder Text");
                mStyle.setBasedOnIstd(StyleIndex.DEFAULT_PARAGRAPH_FONT);
                mStyle.setPriority(99);
                mStyle.setSemiHidden(true);
                mStyle.getRunPr().setColor(DrColor.fromArgb(0, 0x80, 0x80, 0x80));
            }
        }
    }

    /// <summary>
    /// When placeholders are used for compatible SDTs e.g. combo list vs. dropdown list, we have to make sure to check if one of them is already in cache
    /// due to being already loaded from a document.
    /// </summary>
    private void addCompatiblePlaceholderToCache(String name, BuildingBlock placeholder)
    {
        if (!mNameCache.containsKey(name))
            msDictionary.add(mNameCache, name, placeholder);
    }

    /// <summary>
    /// When placeholders are used for compatible SDTs e.g. combo list vs. dropdown list, we have to make sure to check if one of them is already in cache
    /// due to being already loaded from a document.
    /// </summary>
    private void addCompatiblePlaceholderToCache(/*SdtType*/int type, BuildingBlock placeholder)
    {
        if (mTypeCache[(int)type] == null)
            mTypeCache[(int)type] = placeholder;
    }

    /// <summary>
    /// Returns true if given block has properties sufficient to consider it to be a placeholder.
    /// </summary>
    /// <remarks>There might be other non-MS compliant placeholders that don't follow this criteria.</remarks>
    private static boolean isSdtPlaceholder(BuildingBlock block)
    {
        return (block.getGallery() == BuildingBlockGallery.STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT) &&
               (block.getType() == BuildingBlockType.STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT);
    }

    /// <summary>
    /// Gets the unused custom placeholder name in the "CustomPlaceholder_1" style. 
    /// </summary>
    private String getUnusedCustomPlaceholderName()
    {
        final String NAME_START = "CustomPlaceholder_";

        long number;
        long customPlaceholderNameCount = 0;
        for (BuildingBlock block : (Iterable<BuildingBlock>) getGlossary())
        {
            if (msString.startsWith(block.getName(), NAME_START, StringComparison.ORDINAL))
            {
                number = FormatterPal.tryParseLong(block.getName().substring(NAME_START.length()));
                if (customPlaceholderNameCount < number)
                    customPlaceholderNameCount = number;
            }
        }
        return msString.format("{0}{1}", NAME_START, ++customPlaceholderNameCount);
    }

    /// <summary>
    /// Collection of placeholder reference counts showing which placeholders are used by SDTS in the parent document.
    /// </summary>
    Iterable<Object> getPlaceholdersUsed() { return mPlaceholderCounter.getReferencedObjects(); }

    /// <summary>
    /// Returns true if this glossary reference stored in this manager is an empty entity containing no useful data.
    /// This usually happens when there is no SDT nodes in the document or when these nodes don't require placeholders.
    /// </summary>
    boolean isGlossaryEmpty() { return (mGlossary == null); }

    /// <summary>
    /// Used only internally by SdtPlaceholderManager to create placeholder blocks.
    /// In new AW documents Glossary is null, so SdtPlaceholderManager might have to create one, that is why we store main doc in the mMainDocument.
    /// On the other hand, we don't always want to create glossary in cases when no SDTs are used in the document.
    /// </summary>
    private GlossaryDocument getGlossary()
    {
        if (mGlossary == null) // delay creation of GlossaryDocument until it is actually requested by this SdtPlaceholderManager
        {
            if (mMainDocument != null)
            {
                if (mMainDocument.getGlossaryDocument() == null)
                    mMainDocument.setGlossaryDocument(new GlossaryDocument());

                mGlossary = mMainDocument.getGlossaryDocument();
            }
            else
            {
                throw new IllegalStateException("Please report exception.");
            }
        }

        return mGlossary;
    }

    /// <summary>
    /// Default style for all placeholders, created upon demand.
    /// </summary>
    private Style mStyle;

    private GlossaryDocument mGlossary;

    /// <summary>
    /// Reference to main doc is needed in order to do delayed creation of Glossary in case if it was null initially.
    /// </summary>
    private /*final*/ Document mMainDocument;

    /// <summary>
    /// Stores sdt type to building block mapping;
    /// used in model when new SDTs are created.
    /// </summary>
    private /*final*/ BuildingBlock[] mTypeCache = new BuildingBlock[G_SDT_TYPE_COUNT];

    /// <summary>
    /// Stores placeholder name to building block mapping,
    /// used during placeholder lookup.
    /// </summary>
    private /*final*/ HashMap<String, BuildingBlock> mNameCache = new HashMap<String, BuildingBlock>();

    private /*final*/ ReferenceCounter mPlaceholderCounter = new ReferenceCounter();

    private static /*final*/ int G_SDT_TYPE_COUNT = EnumUtilPal.getEffectiveArrayLength((17));

    /// <summary>
    /// Exposed as internal for testing purposes.
    /// </summary>
    static final String BUILDING_BLOCK_CATEGORY = "General";

    // MS Word always uses these fancy names for placeholders of particular sdt types.
    // Exposed as internal for testing purposes.
    static final String TEXT_PLACEHOLDER_NAME = "DefaultPlaceholder_22675703";
    static final String LISTBOX_PLACEHOLDER_NAME = "DefaultPlaceholder_22675704";
    static final String DATE_PLACEHOLDER_NAME = "DefaultPlaceholder_22675705";
    static final String BUILDING_BLOCK_PLACEHOLDER_NAME = "DefaultPlaceholder_22675706";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"DefaultPlaceholder_22675703",
		"DefaultPlaceholder_22675704",
		"DefaultPlaceholder_22675705",
		"DefaultPlaceholder_22675706"
	);

}

