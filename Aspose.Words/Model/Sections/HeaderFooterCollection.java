// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Provides typed access to <see cref="HeaderFooter"/> nodes of a <see cref="Section"/>.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-headers-and-footers/">Working with Headers and Footers</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>There can be maximum of one <see cref="HeaderFooter"/></p> of each <see cref="HeaderFooterType"/> per
/// <see cref="Section"/>.
/// <p><see cref="HeaderFooter"/> objects can occur in any order in the collection.</p>
/// </remarks>
public class HeaderFooterCollection extends NodeCollection<HeaderFooter>
{
    HeaderFooterCollection(CompositeNode parent)
    {
    	super(parent, NodeType.HEADER_FOOTER, false);
	
        setParentSection((Section)parent);
    }

    /// <summary>
    /// Retrieves a <see cref="HeaderFooter"/> at the given index.
    /// </summary>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="NodeCollection.IndexerCommon"]/*'/>
    /// <param name="index">An index into the collection.</param>
    public /*new*/ HeaderFooter get(int index) { return (HeaderFooter)super.get(index); }

    /// <summary>
    /// Retrieves a <see cref="HeaderFooter"/> of the specified type.
    /// </summary>
    /// <remarks>
    /// Returns <c>null</c> if the header/footer of the specified type is not found.
    /// </remarks>
    /// <param name="headerFooterType">A <see cref="HeaderFooterType"/> value
    /// that specifies the type of the header/footer to retrieve.</param>
    public HeaderFooter getByHeaderFooterType(/*HeaderFooterType*/int headerFooterType)
    {
        for (HeaderFooter headerFooter : (Iterable<HeaderFooter>) this)
        {
            if (headerFooter.getHeaderFooterType() == headerFooterType)
                return headerFooter;
        }
        return null;
    }


    /// <summary>
    /// Links or unlinks all headers and footers to the corresponding
    /// headers and footers in the previous section.
    /// </summary>
    /// <remarks>
    /// <p>If any of the headers or footers do not exist, creates them automatically.</p>
    /// </remarks>
    /// <param name="isLinkToPrevious"><c>true</c> to link the headers and footers to the previous section;
    /// <c>false</c> to unlink them.</param>
    public void linkToPrevious(boolean isLinkToPrevious)
    {
        linkToPrevious(HeaderFooterType.HEADER_PRIMARY, isLinkToPrevious);
        linkToPrevious(HeaderFooterType.HEADER_FIRST, isLinkToPrevious);
        linkToPrevious(HeaderFooterType.HEADER_EVEN, isLinkToPrevious);
        linkToPrevious(HeaderFooterType.FOOTER_PRIMARY, isLinkToPrevious);
        linkToPrevious(HeaderFooterType.FOOTER_FIRST, isLinkToPrevious);
        linkToPrevious(HeaderFooterType.FOOTER_EVEN, isLinkToPrevious);
    }

    /// <summary>
    /// Links or unlinks the specified header or footer to the corresponding
    /// header or footer in the previous section.
    /// </summary>
    /// <remarks>
    /// <p>If the header or footer of the specified type does not exist, creates it automatically.</p>
    /// </remarks>
    /// <param name="headerFooterType">A <see cref="HeaderFooterType"/> value
    /// that specifies the header or footer to link/unlink.</param>
    /// <param name="isLinkToPrevious"><c>true</c> to link the header or footer to the previous section;
    /// <c>false</c> to unlink.</param>
    public void linkToPrevious(/*HeaderFooterType*/int headerFooterType, boolean isLinkToPrevious)
    {
        HeaderFooter headerFooter = this.getByHeaderFooterType(headerFooterType);
        if (headerFooter == null)
        {
            headerFooter = new HeaderFooter(((INodeCollection)this).getContainer().getDocument(), headerFooterType);
            add(headerFooter);
        }

        headerFooter.isLinkedToPrevious(isLinkToPrevious);
    }

    /// <summary>
    /// Copies all <c>HeaderFooter</c>s from the collection to a new array of <c>HeaderFooter</c>s.
    /// </summary>
    /// <returns>An array of <c>HeaderFooter</c>s.</returns>
    public /*new*/ HeaderFooter[] toArray()
    {
        return msArrayList.toArray(this.<HeaderFooter>toList(), new HeaderFooter[0]);
    }

    /// <summary>
    /// Returns true, if collection has any of the Headers.
    /// </summary>
    boolean hasHeaders()
    {
        for (HeaderFooter headerFooter : (Iterable<HeaderFooter>) this)
        {
            if (headerFooter.isHeader())
                return true;
        }

        return false;
    }

    /// <summary>
    /// Returns true, if collection has any of the Footers.
    /// </summary>
    boolean hasFooters()
    {
        for (HeaderFooter headerFooter : (Iterable<HeaderFooter>) this)
        {
            if (!headerFooter.isHeader())
                return true;
        }

        return false;
    }

    /// <summary>
    /// Gets a parent section.
    /// </summary>
    Section getParentSection() { return mParentSection; }; private void setParentSection(Section value) { mParentSection = value; };

    private Section mParentSection;
}

