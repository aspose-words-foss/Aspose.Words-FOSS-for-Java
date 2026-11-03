// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// The list of active formatting elements, which is used by the HTML tree constructor to handle mis-nested formatting
/// element tags.
/// </summary>
/// <remarks>
/// The most recently opened element comes last.
/// The list contains not only <see cref="HtmlElementNode"/> instances, but also special objects called 'scope markers',
/// which divide formatting elements into groups to prevent formatting from 'leaking' in case of bad HTML markup. The most
/// recent scope is called 'the current scope'. 
/// </remarks>
class HtmlActiveFormattingElementList
{
    /// <summary>
    /// Adds the specified element to the list.
    /// </summary>
    /// <param name="element">An element.</param>
    /// <remarks>
    /// HTML specification limits the number of equal formatting elements that can be placed into one formatting scope.
    /// If the current formatting scope already contains maximum number of elements equal to the one being added, the oldest
    /// such element will be removed from the list as described here:
    /// http://www.w3.org/TR/html5/syntax.html#push-onto-the-list-of-active-formatting-elements
    /// </remarks>
    void add(HtmlElementNode element)
    {
        Debug.assertTrue(element != null);

        // Maximum number of equal formatting elements that can share one formatting context.
        final int MAX_SAME_ELEMENT_COUNT = 3;

        // Search the current formatting context for formatting elements that are equal to the element being added.
        // Count such elements and find the oldest one.
        int sameElementCount = 0;
        HtmlElementNode oldestSameElement = null;
        int elementIndex = mElements.size() - 1;
        while ((elementIndex >= 0) && (mElements.get(elementIndex) != G_SCOPE_MARKER) && (sameElementCount < MAX_SAME_ELEMENT_COUNT))
        {
            HtmlElementNode existingElement = mElements.get(elementIndex);
            boolean same = (msString.equals(existingElement.getName(), element.getName())) &&
                (msString.equals(existingElement.getNamespace(), element.getNamespace())) &&
                (existingElement.getAttributes().equals(element.getAttributes()));
            if (same)
            {
                ++sameElementCount;
                oldestSameElement = existingElement;
            }
            --elementIndex;
        }

        // If the current formatting context contains too many formatting elements that are equal to the element being added,
        // remove the oldest such element.
        if (sameElementCount >= MAX_SAME_ELEMENT_COUNT)
        {
            Debug.assertTrue(oldestSameElement != null);
            mElements.remove(oldestSameElement);
        }

        msArrayList.add(mElements, element);
    }

    /// <summary>
    /// Removes the specified element from the list.
    /// </summary>
    /// <param name="element">An element.</param>
    void remove(HtmlElementNode element)
    {
        Debug.assertTrue(element != null);

        mElements.remove(element);
    }

    /// <summary>
    /// Inserts the specified element into the list at the specified index.
    /// </summary>
    /// <param name="index">An element's index.</param>
    /// <param name="element">An element.</param>
    /// <remarks>
    /// This method does not check the number of equal elements that share one formatting scope.
    /// </remarks>
    void insert(int index, HtmlElementNode element)
    {
        Debug.assertTrue(element != null);

        mElements.add(index, element);
    }

    /// <summary>
    /// Replaces an element at the specified index with the specified element.
    /// </summary>
    /// <param name="index">An element's index.</param>
    /// <param name="element">An element.</param>
    /// <remarks>
    /// This method does not check the number of equal elements that share one formatting scope.
    /// </remarks>
    void replace(int index, HtmlElementNode element)
    {
        Debug.assertTrue(element != null);

        mElements.set(index, element);
    }

    /// <summary>
    /// Gets the index of the specified element.
    /// </summary>
    /// <param name="element">An element.</param>
    /// <returns>
    /// The zero-based index of the element, if the element is in the list. Otherwise, <c>-1</c> is returned.
    /// </returns>
    int indexOf(HtmlElementNode element)
    {
        return mElements.indexOf(element);
    }

    /// <summary>
    /// Opens a new formatting scope.
    /// </summary>
    /// <remarks>
    /// Adds a scope marker to the list.
    /// </remarks>
    void openNewScope()
    {
        msArrayList.add(mElements, G_SCOPE_MARKER);
    }

    /// <summary>
    /// Closes the current formatting scope.
    /// </summary>
    /// <remarks>
    /// Removes all elements from the list up to the last context boundary marker, as described here:
    /// http://www.w3.org/TR/html5/syntax.html#clear-the-list-of-active-formatting-elements-up-to-the-last-marker
    /// If there are no context boundary markers in the list, the method removes all elements from it.
    /// </remarks>
    void closeCurrentScope()
    {
        int lastMarkerIndex = mElements.lastIndexOf(G_SCOPE_MARKER);
        if (lastMarkerIndex >= 0)
        {
            msArrayList.removeRange(mElements, lastMarkerIndex, mElements.size() - lastMarkerIndex);
        }
        else
        {
            mElements.clear();
        }
    }

    /// <summary>
    /// Searches the current formatting scope for an element with the specified name.
    /// </summary>
    /// <param name="elementName">The name of the element.</param>
    /// <returns>
    /// An element with the specified name that is most recently added to the current formatting scope.
    /// If no such element is found, <c>null</c> is returned.
    /// </returns>
    HtmlElementNode findInCurrentScope(String elementName)
    {
        int i = mElements.size() - 1;
        while ((i >= 0) && (mElements.get(i) != G_SCOPE_MARKER))
        {
            HtmlElementNode element = mElements.get(i);
            if (msString.equals(element.getName(), elementName))
            {
                return element;
            }
            --i;
        }
        return null;
    }

    /// <summary>
    /// Gets number of elements in the list (including scope markers).
    /// </summary>
    int getCount() { return mElements.size(); }

    /// <summary>
    /// Gets an element at the specified index in the list.
    /// </summary>
    /// <param name="index">The zero-based index of the element to get.</param>
    /// <returns>
    /// The element at the specified index. If the list contains a scope marker at the specified index,
    /// <c>null</c> is returned.
    /// </returns>
    HtmlElementNode get(int index)
    {
        return (mElements.get(index) != G_SCOPE_MARKER)
            ? mElements.get(index)
            : null;
    }

    /// <summary>
    /// The low-level list that contains elements and scope markers.
    /// </summary>
    private /*final*/ ArrayList<HtmlElementNode> mElements = new ArrayList<HtmlElementNode>();

    /// <summary>
    /// The special value that is added to the list to mark the boundary of formatting scope.
    /// </summary>
    private static /*final*/ HtmlElementNode G_SCOPE_MARKER = new HtmlElementNode("ScopeMarker");
}

