// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/09/2010 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.HashSetGeneric;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.StringUtil;


/// <summary>
/// Word Extensions to the Office Open XML.
/// Office2013 SDT type which specifies the properties of a structured document tag (as specified in [ISO/IEC29500-1:2011]
/// section 17.5.2) in the form of a repeated section.
/// </summary>
class SdtRepeatingSection extends SdtControlProperties
{
    /// <summary>
    /// Provides data and methods needed by SDT repeating sections update.
    /// </summary>
    private static class SdtRepeatingSectionContext
    {
        private /*final*/ HashSetGeneric<SdtRepeatingSection> mUpdatedSections = new HashSetGeneric<SdtRepeatingSection>();

        SdtRepeatingSectionContext(String rootXPath)
        {
            mRootXPath = rootXPath;
        }

        HashSetGeneric<SdtRepeatingSection> getUpdatedSections() { return mUpdatedSections; }

        int getCurrentDataItemIndex() { return mCurrentDataItemIndex; }; void setCurrentDataItemIndex(int value) { mCurrentDataItemIndex = value; };

        private int mCurrentDataItemIndex;

        /// <summary>
        /// Used for building child XPaths for repeatingSectionItems.
        /// </summary>
        String getRootXPath() { return mRootXPath; };

        private String mRootXPath;
    }

    /*override*/ /*SdtType*/int getType() { return SdtType.REPEATING_SECTION; }

    /// <summary>
    /// An optional element that specifies the display name of the repeated section.
    /// </summary>
    String getSectionTitle() { return mSectionTitle; }
    void setSectionTitle(String value) { mSectionTitle = value; }

    /// <summary>
    /// An optional element that specifies whether to allow the insertion of new or deletion of old repeatingSectionItems
    /// contained within the structured document tag except when needed to maintain the numerical relation between
    /// the number of elements matched through data binding and the number of repeatingSectionItems contained.
    /// </summary>
    boolean getDoNotAllowInsertDeleteSection() { return mDoNotAllowInsertDeleteSection; }
    void setDoNotAllowInsertDeleteSection(boolean value) { mDoNotAllowInsertDeleteSection = value; }

    /// <summary>
    /// Update content of the repeatingSection element that specifies that the parent structured document tag
    /// is a container for repeated section items.
    /// </summary>
    /// <dev>AM. Still looking for better place for this code.</dev>
    static boolean updateRepeatingSection(StructuredDocumentTag sdt) throws Exception
    {
        if (sdt.getXmlMapping().isEmpty())
            return true;

        if (!sdt.getXmlMapping().isValid())
            return false;

        extractRepeatingSectionItems(sdt);

        SdtRepeatingSectionContext context = new SdtRepeatingSectionContext(sdt.getXmlMapping().getXPath());
        if (sdt.isShowingPlaceholderText() && (sdt.getPlaceholder() == null))
            sdt.isShowingPlaceholderText(false);

        return updateRepeatingSection(sdt, context);
    }

    /// <summary>
    /// Update content of the repeatingSection.
    /// </summary>
    private static boolean updateRepeatingSection(StructuredDocumentTag sdt, SdtRepeatingSectionContext context) throws Exception
    {
        SdtRepeatingSection repeatingSection = (SdtRepeatingSection)sdt.getControlProperties();

        if (context.getUpdatedSections().contains(repeatingSection))
            return true;

        NodeCollection childSdtItems = sdt.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, false);

        for (StructuredDocumentTag childSdtItem : (Iterable<StructuredDocumentTag>) childSdtItems)
        {
            if (childSdtItem.isRepeatingSectionItem())
                updateRepeatingSectionItem(childSdtItem, context);
            else
                childSdtItem.getXmlMapping().updateContent();
        }

        // Set flag which indicates that this RepeatingSection is updated.
        // We need this to avoid updating the same RepeatingSection more then one time, in case of nested RepeatingSection.
        context.getUpdatedSections().add(repeatingSection);

        return true;
    }

    /// <summary>
    /// Update content of the repeatingSectionItem that must be contained within a repeatingSection,
    /// and also can contain repeatingSection.
    /// </summary>
    private static void updateRepeatingSectionItem(StructuredDocumentTag sdt, SdtRepeatingSectionContext context) throws Exception
    {
        // The repeatingSectionItem must be either Block-Level, Row-Level, or Cell-Level.
        switch (sdt.getLevel())
        {
            case MarkupLevel.ROW:
            case MarkupLevel.BLOCK:
            {
                fillWithData(sdt, context);
                break;
            }
            case MarkupLevel.CELL:
            {
                // Cannot create such document in MS Word.
                break;
            }
            default:
                throw new IllegalStateException("Unexpected SDT level.");
        }

        context.setCurrentDataItemIndex(context.getCurrentDataItemIndex() + 1)/*Property++*/;
    }

    /// <summary>
    /// Extracts recursively all repeating section items for the specified <paramref name="repeatingSection"/>.
    /// </summary>
    private static void extractRepeatingSectionItems(StructuredDocumentTag repeatingSection) throws Exception
    {
        // There can be some other types of SDTs inside repeating section,
        // than repeating items. See for example TestSdt.TestJira8888().
        // So, we should calculate repeating items actual count.
        int dataItemCount = !repeatingSection.getXmlMapping().isEmpty()
            ? repeatingSection.getXmlMapping().getMappedNodesCount()
            : 0;

        // WORDSNET-16155 AW must not remove repeating sections, when document contains invalid XPath in SDT.
        // Don't analyze nested sections, at this case order of sections can be wrong (see TestJira16155).
        if (dataItemCount == 0)
            return;

        CompositeNode parentNode = repeatingSection;

        // WORDSNET-24905 RepeatingSectionItem is not immediate child of RepeatingSection.
        if ((repeatingSection.getFirstChild() != null) && (repeatingSection.getFirstChild().getNodeType() == NodeType.TABLE))
            parentNode = ((CompositeNode)repeatingSection.getFirstChild());

        NodeCollection repeatingSectionChildren = parentNode.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, false);

        ArrayList<StructuredDocumentTag> itemsToRemove = new ArrayList<StructuredDocumentTag>();
        StructuredDocumentTag templateItem = null;

        // Go throughout the all children SDT to find nested repeating sections to update them recursively.
        int repeatingItemsCount = 0;
        for (int i = 0; i < repeatingSectionChildren.getCount(); i++)
        {
            StructuredDocumentTag repeatingSectionChild = (StructuredDocumentTag)repeatingSectionChildren.get(i);
            NodeCollection childSdts = repeatingSectionChild.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, false);

            for (int j = 0; j < childSdts.getCount(); j++)
            {
                StructuredDocumentTag childSdt = (StructuredDocumentTag)childSdts.get(j);

                // MS Word moves a repeating section with data binding inside the first repeating item up to
                // the level of a parent repeating section at some cases. See TestSdt.TestNestedRepeatingSection().
                if (repeatingSectionChild.isRepeatingSectionItem() && childSdt.isRepeatingSection() &&
                    (parentNode.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG))
                    moveNestedRepeatingSectionIfNeeded(childSdt, (StructuredDocumentTag)parentNode);
            }

            // Count repeating section items and remember last of them
            // to use as the template for all missed repeating items.
            if (repeatingSectionChild.isRepeatingSectionItem())
            {
                StructuredDocumentTag repeatingItem = repeatingSectionChild;

                if (repeatingItem.hasChildNodes() && (repeatingItemsCount < dataItemCount))
                {
                    repeatingItemsCount++;
                    templateItem = repeatingSectionChild;

                    updateNestedXPath(repeatingSection, repeatingItem, i);
                }
                else
                {
                    if (!itemsToRemove.contains(repeatingItem))
                        msArrayList.add(itemsToRemove, repeatingItem);
                }
            }
        }

        // Remove superfluous repeating section items.
        for (StructuredDocumentTag item : itemsToRemove)
            item.remove();

        if (templateItem == null)
            return;

        // Create missed repeating items.
        for (int i = repeatingItemsCount; i < dataItemCount; i++)
        {
            StructuredDocumentTag newRepeatingItem = (StructuredDocumentTag)templateItem.deepClone(true);

            updateNestedXPath(repeatingSection, newRepeatingItem, i);

            parentNode.insertAfter(newRepeatingItem, templateItem);
        }
    }

    /// <summary>
    /// Ensures child SDT nodes have absolute mapping XPath.
    /// </summary>
    private static void updateNestedXPath(StructuredDocumentTag rs, StructuredDocumentTag rsItem, int index) throws Exception
    {
        for (StructuredDocumentTag childTag : (Iterable<StructuredDocumentTag>) rsItem.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true))
        {
            if (childTag.getXmlMapping().isMapped())
            {
                String parentPath = rs.getXmlMapping().getXPath();
                String childPath = childTag.getXmlMapping().getXPath();

                if (!isRelativeXPath(childPath))
                {
                    // Try to convert absolute XPath to relative.
                    if (msString.startsWith(childPath, parentPath, StringComparison.INVARIANT_CULTURE))
                    {
                        childPath = msString.remove(childPath, 0, parentPath.length());
                        childPath = G_INDEX_REGEX.replace(childPath, "/", 1);
                    }

                }

                if (isRelativeXPath(childPath))
                {
                    String newPath = msString.format("{0}[{1}]/{2}", parentPath, index + 1, msString.trimStart(childPath, '/'));

                    childTag.getXmlMapping().setXPath(newPath);
                }
            }
        }
    }

    /// <summary>
    /// Checks that given XPath is relative.
    /// </summary>
    private static boolean isRelativeXPath(String xPath)
    {
        return msString.startsWith(xPath, "//", StringComparison.ORDINAL);
    }

    /// <summary>
    /// Moves the specified nested repeating section at some cases to be direct child of the parent repeating
    /// section like MS Word does. Such behaviour of MS Word looks as annoying but we have to mimic it.
    /// </summary>
    private static void moveNestedRepeatingSectionIfNeeded(StructuredDocumentTag childRepeatingSection,
        StructuredDocumentTag parentRepeatingSection) throws Exception
    {
        if (childRepeatingSection.getXmlMapping().isEmpty() || parentRepeatingSection.getXmlMapping().isEmpty())
            return;

        int childRecordCount = childRepeatingSection.getXmlMapping().getMappedNodesCount();
        int childRepeatingItemCount = calcChildRepeatingSectionItems(childRepeatingSection);

        if ((childRecordCount == 0) || (childRepeatingItemCount >= childRecordCount))
            return;

        int parentRepeatingItemCount = calcChildRepeatingSectionItems(parentRepeatingSection);
        if (parentRepeatingItemCount == 1)
        {
            // If childRepeatingItemCount > 1 here, MS Word exchanges parent and child repeating sections.
            // Postponed for now (WORDSNET-18023).
            return;
        }

        parentRepeatingSection.insertBefore(childRepeatingSection, childRepeatingSection.getParentNode());
    }

    /// <summary>
    /// Calculates number of child repeating section items related to the specified repeating section.
    /// </summary>
    private static int calcChildRepeatingSectionItems(StructuredDocumentTag repeatingSection)
    {
        int count = 0;

        for (StructuredDocumentTag sdt : (Iterable<StructuredDocumentTag>) repeatingSection.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, false))
        {
            if (sdt.isRepeatingSectionItem())
                count++;
        }

        return count;
    }

    /// <summary>
    /// Fill repeated item with data using RootXPath as a start key.
    /// </summary>
    private static void fillWithData(StructuredDocumentTag sdt, SdtRepeatingSectionContext context) throws Exception
    {
        NodeCollection childTags = sdt.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        for (StructuredDocumentTag childTag : (Iterable<StructuredDocumentTag>) childTags)
            fillWithDataCore(sdt, childTag, context);
    }

    /// <summary>
    /// Core method to fill sdt with data.
    /// </summary>
    private static void fillWithDataCore(StructuredDocumentTag parentSdt, StructuredDocumentTag sdt, SdtRepeatingSectionContext context) throws Exception
    {
        String rootXPath = context.getRootXPath();
        Regex regex = new Regex(Regex.escape(rootXPath) + "\\[(\\d+)\\].*", RegexOptions.IGNORE_CASE);

        if (!sdt.getXmlMapping().isEmpty() && sdt.getXmlMapping().isValid())
        {
            String childXPath = sdt.getXmlMapping().getXPath();
            String newDataIndex = Integer.toString((context.getCurrentDataItemIndex() + 1));

            // Update XPath using rootXPath as 'key'.
            Match match = regex.match(childXPath);
            if (match.getSuccess())
            {
                sdt.getXmlMapping().setXPath(replaceIndex(match, newDataIndex));

                // Recursively update SDT data bound content.
                parentSdt.getXmlMapping().updateContent();
            }
            else
            {
                // WORDSNET-12206 If XPath has no node index, add it
                if (childXPath.length() >= rootXPath.length() &&
                    StringUtil.equalsIgnoreCase(childXPath.substring((0), (0) + (rootXPath.length())), rootXPath) &&
                    (childXPath.length() == rootXPath.length() || childXPath.charAt(rootXPath.length()) == '/'))
                {
                    sdt.getXmlMapping().setXPath(msString.insert(childXPath, rootXPath.length(), "[" + newDataIndex + "]"));

                    // Recursively update SDT data bound content.
                    parentSdt.getXmlMapping().updateContent();
                }
            }
        }
    }

    private static String replaceIndex(Match m, String newIndex)
    {
        String capture = m.getValue();
        capture = msString.remove(capture, m.getGroups().get(1).getIndex(), m.getGroups().get(1).getLength());
        capture = msString.insert(capture, m.getGroups().get(1).getIndex(), newIndex);
        return capture;
    }

    private String mSectionTitle;
    private boolean mDoNotAllowInsertDeleteSection;

    private static /*final*/ Regex G_INDEX_REGEX = new Regex("^\\[\\d+\\]");
}

