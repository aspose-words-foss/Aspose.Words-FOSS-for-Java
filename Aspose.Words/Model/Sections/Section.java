// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.ms.System.msString;


/// <summary>
/// Represents a single section in a document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-sections/">Working with Sections</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p><see cref="Section"/> can have one <see cref="Aspose.Words.Body"/> and maximum one <see cref="HeaderFooter"/>
/// of each <see cref="HeaderFooterType"/>. <see cref="Aspose.Words.Body"/> and <see cref="HeaderFooter"/> nodes
/// can be in any order inside <see cref="Section"/>.</p>
///
/// <p>A minimal valid section needs to have <see cref="Aspose.Words.Body"/> with one <see cref="Paragraph"/>.</p>
///
/// <p>Each section has its own set of properties that specify page size, orientation, margins etc.</p>
/// <p>You can create a copy of a section using <see cref="Node.Clone(bool)"/>. The copy can be inserted into
/// the same or different document.</p>
/// <p>To add, insert or remove a whole section including section break and
/// section properties use methods of the <see cref="Document.Sections"/> object.</p>
/// <p>To copy and insert just content of the section excluding the section break
/// and section properties use <see cref="AppendContent"/> and <see cref="PrependContent"/> methods.</p>
/// </remarks>
public final class Section extends CompositeNode<Node> implements ISectionAttrSource
{
    /// <summary>
    /// Initializes a new instance of the Section class.
    /// </summary>
    /// <remarks>
    /// <p>When the section is created, it belongs to the specified document, but is not
    /// yet part of the document and <see cref="Node.ParentNode"/> is <c>null</c>.</p>
    /// <p>To include <see cref="Section"/> into a document use <see cref="CompositeNode.InsertAfter{T}(T, Node)"/> and
    /// <see cref="CompositeNode.InsertBefore{T}(T, Node)"/> methods of the <see cref="Document"/> OR
    /// <see cref="NodeCollection.Add"/> and <see cref="NodeCollection.Insert"/> methods of the <see cref="Document.Sections"/> property.</p>
    /// </remarks>
    /// <param name="doc">The owner document.</param>
    public Section(DocumentBase doc)
    {
    	this(doc, new SectPr());
	
    }

    Section(DocumentBase doc, SectPr sectPr)
    {
    	super(doc);
	
        mSectPr = sectPr;
    }

    /// <summary>
    /// Returns <see cref="NodeType.Section"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.SECTION; }

    /// <summary>
    /// Returns the <see cref="Aspose.Words.Body"/> child node of the section.
    /// </summary>
    /// <remarks>
    /// <p><see cref="Aspose.Words.Body"/> contains main text of the section.</p>
    /// <p>Returns <c>null</c> if the section does not have a <see cref="Aspose.Words.Body"/> node among its children.</p>
    /// </remarks>
    public Body getBody() { return (Body)getChild(NodeType.BODY, 0, false); }

    /// <summary>
    /// Provides access to the headers and footers nodes of the section.
    /// </summary>
    public HeaderFooterCollection getHeadersFooters()
    {
        if (mHeadersFooters == null)
            mHeadersFooters = new HeaderFooterCollection(this);
        return mHeadersFooters;
    }

    /// <summary>
    /// Returns an object that represents page setup and section properties.
    /// </summary>
    public PageSetup getPageSetup()
    {
        if (mPageSetup == null)
            mPageSetup = new PageSetup(this, getDocument().getDocPr(), getDocument().getStyles(), new FirstSectionAttrSource(this));
        return mPageSetup;
    }

    /// <summary>
    /// True if the section is protected for forms. When a section is protected for forms,
    /// users can select and modify text only in form fields in Microsoft Word.
    /// </summary>
    public boolean getProtectedForForms() { return !getPageSetup().getUnlocked(); }
    public void setProtectedForForms(boolean value) { getPageSetup().setUnlocked(!value); }

    /// <summary>
    /// Gets and sets section properties.
    /// </summary>
    ///<remarks> We don't normally set SectPr, since in Section's lifecycle it is provided upon construction.</remarks>
    SectPr getSectPr() { return mSectPr; }
    void setSectPr(SectPr value)
    {
        Debug.assertTrue(value != null);
        mSectPr = value;
    }

    /// <summary>
    /// Creates a duplicate of this section.
    /// </summary>
    /// <dev>Kept to remain compatible with the old API.</dev>
    public Section deepClone()
    {
        return (Section)deepClone(true);
    }

    /*override*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        Section lhs = (Section)super.deepClone(isCloneChildren, cloningListener);
        SectPr sectPr = AttrCollectionExtension.deepClone(mSectPr);
        lhs.mSectPr = sectPr;

        //Don't clone these because created on demand.
        lhs.mPageSetup = null;
        lhs.mHeadersFooters = null;

        return lhs;
    }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Node.Accept"]/*'/>
    /// <remarks>
    /// Calls <see cref="DocumentVisitor.VisitSectionStart"/>, then calls <see cref="Node.Accept"/> for all child nodes of the section
    /// and calls <see cref="DocumentVisitor.VisitSectionEnd"/> at the end.
    /// </remarks>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return acceptCore(visitor);
    }

    public /*override*/ /*VisitorAction*/int acceptStart(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitSectionStart(this);
    }

    public /*override*/ /*VisitorAction*/int acceptEnd(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitSectionEnd(this);
    }

    /// <summary>
    /// Inserts a copy of content of the source section at the beginning of this section.
    /// </summary>
    /// <remarks>
    /// <p>Only content of <see cref="Body"/> of the source section is copied, page setup,
    /// headers and footers are not copied.</p>
    /// <p>The nodes are automatically imported if the source section belongs to a different document.</p>
    /// <p>No new section is created in the destination document.</p>
    /// </remarks>
    /// <param name="sourceSection">The section to copy content from.</param>
    public void prependContent(Section sourceSection)
    {
        insertContent(sourceSection, false);
    }

    /// <summary>
    /// Inserts a copy of content of the source section at the end of this section.
    /// </summary>
    /// <remarks>
    /// <p>Only content of <see cref="Body"/> of the source section is copied, page setup,
    /// headers and footers are not copied.</p>
    /// <p>The nodes are automatically imported if the source section belongs to a different document.</p>
    /// <p>No new section is created in the destination document.</p>
    /// </remarks>
    /// <param name="sourceSection">The section to copy content from.</param>
    public void appendContent(Section sourceSection)
    {
        insertContent(sourceSection, true);
    }

    /// <summary>
    /// Clears the section.
    /// </summary>
    /// <remarks>
    /// <p>The text of <see cref="Body"/> is cleared, only one empty paragraph is left that represents the section break.</p>
    /// <p>The text of all headers and footers is cleared, but <see cref="HeaderFooter"/> objects themselves are not removed.</p>
    /// </remarks>
    public void clearContent()
    {
        clearHeadersFooters();
        getBody().removeAllChildren();
        getBody().ensureMinimum();
    }

    /// <summary>
    /// Clears the headers and footers of this section.
    /// </summary>
    /// <remarks>
    /// <p>The text of all headers and footers is cleared, but <see cref="HeaderFooter"/> objects themselves are not removed.</p>
    /// <p>This makes headers and footers of this section linked to headers and footers of the previous section.</p>
    /// </remarks>
    public void clearHeadersFooters()
    {
        clearHeadersFooters(false);
    }

    /// <summary>
    /// Clears the headers and footers of this section.
    /// </summary>
    /// <remarks>
    /// <p>The text of all headers and footers is cleared, but <see cref="HeaderFooter"/> objects themselves are not removed.</p>
    /// <p>This makes headers and footers of this section linked to headers and footers of the previous section.</p>
    /// </remarks>
    /// <param name="preserveWatermarks">True if the watermarks shall not be removed.</param>
    public void clearHeadersFooters(boolean preserveWatermarks)
    {
        for (Node child = getFirstChild(); child != null; child = child.getNextSibling())
        {
            if (child.getNodeType() != NodeType.HEADER_FOOTER)
                continue;

            HeaderFooter headerFooter = (HeaderFooter)child;

            ArrayList<Shape> shapes = preserveWatermarks
                ? headerFooter.getChildNodes(NodeType.SHAPE, true).<Shape>toList()
                : EmptyNodeCollection.createEmpty().<Shape>toList();

            headerFooter.removeAllChildren();

            for (Shape shape : shapes)
            {
                if (shape.canBeTextWatermark() || shape.canBeImageWatermark())
                {
                    headerFooter.appendChild(new Paragraph(getDocument()));
                    headerFooter.getFirstParagraph().appendChild(shape);
                }
            }
        }
    }

    /// <summary>
    /// Deletes all shapes (drawing objects) from the headers and footers of this section.
    /// </summary>
    public void deleteHeaderFooterShapes()
    {
        for (Node child = getFirstChild(); child != null; child = child.getNextSibling())
        {
            if (child.getNodeType() == NodeType.HEADER_FOOTER)
                ((HeaderFooter)child).deleteShapes();
        }
    }

    /// <summary>
    /// Ensures that the section has <see cref="Body"/> with one <see cref="Paragraph"/>.
    /// </summary>
    public void ensureMinimum()
    {
        Body body = this.getBody();
        if (body == null)
            body = (Body)appendChild(new Body(getDocument()));

        body.ensureMinimum();
    }

    /// <summary>
    /// Sets <see cref="PaperSize"/> value according to the specified <see cref="EditingLanguage"/>
    /// https://en.wikipedia.org/wiki/Paper_size
    /// </summary>
    void setPaperSizeByLanguage(/*EditingLanguage*/int editingLanguage)
    {

        switch (editingLanguage)
        {
            case EditingLanguage.ENGLISH_CANADA:
            case EditingLanguage.ENGLISH_PHILIPPINES:
            case EditingLanguage.ENGLISH_US:
            case EditingLanguage.SPANISH_MEXICO:
            case EditingLanguage.SPANISH_COSTA_RICA:
            case EditingLanguage.SPANISH_COLOMBIA:
            case EditingLanguage.SPANISH_VENEZUELA:
            case EditingLanguage.SPANISH_CHILE:
                getPageSetup().setPaperSize(PaperSize.LETTER);
                break;
            default:
                getPageSetup().setPaperSize(PaperSize.A4);
                break;
        }
    }

    Story getStory(/*StoryType*/int storyType)
    {
        for (Node node = getFirstChild(); node != null; node = node.getNextSibling())
        {
            if (((Story)node).getStoryType() == storyType)
                return (Story)node;
        }
        return null;
    }

    /// <summary>
    /// Can only insert one body and one only one header footer of each type.
    /// </summary>
    /*override*/ boolean canInsert(Node newChild)
    {
        switch (newChild.getNodeType())
        {
            case NodeType.BODY:
            case NodeType.HEADER_FOOTER:
            {
                /*StoryType*/int newStoryType = ((Story)newChild).getStoryType();
                return (getStory(newStoryType) == null);
            }
            default:
                return false;
        }
    }

    void addWatermark(Shape shape, boolean createHeaders) throws Exception
    {
        addWatermarkIntoHeader(shape, HeaderFooterType.HEADER_FIRST, createHeaders);
        addWatermarkIntoHeader(shape, HeaderFooterType.HEADER_EVEN, createHeaders);
        addWatermarkIntoHeader(shape, HeaderFooterType.HEADER_PRIMARY, createHeaders);
    }

    private void addWatermarkIntoHeader(Shape shape, /*HeaderFooterType*/int headerType, boolean createHeader) throws Exception
    {
        HeaderFooter header = getHeadersFooters().getByHeaderFooterType(headerType);

        if (header == null)
        {
            if (!createHeader)
                return;

            header = new HeaderFooter(getDocument(), headerType);
            getHeadersFooters().add(header);
        }

        // Word inserts a watermark to the first paragraph.
        if (header.getParagraphs().getCount() == 0)
            header.appendChild(new Paragraph(getDocument()));

        Shape shapeToInsert = (Shape)shape.deepClone(true);
        // WORDSNET-23231 Update shape ids to avoid duplicates.
        shapeToInsert.setId(shapeToInsert.getDocument().getNextShapeId());
        shapeToInsert.setName(msString.format(
            "{0}{1}",
            shapeToInsert.hasImage() ? Watermark.IMAGE_NAME_PREFIX : Watermark.TEXT_NAME_PREFIX,
            shapeToInsert.getId()));

        header.getFirstParagraph().appendChild(shapeToInsert);
    }

    boolean removeWatermark()
    {
        boolean watermarkRemoved = false;
        for (HeaderFooter headerFooter : (Iterable<HeaderFooter>) getHeadersFooters())
        {
            if (!headerFooter.isHeader())
                continue;

            // Word only defines the first watermark shape as the watermark in the header,
            // but removes all watermark shapes.
            for (Shape shape : (Iterable<Shape>) headerFooter.getShapes())
            {
                if (shape.isWatermark())
                {
                    shape.remove();
                    watermarkRemoved = true;
                }
            }
        }

        return watermarkRemoved;
    }

    /// <dev>Copies and appends or prepends body of the source section into this section.</dev>
    private void insertContent(Section sourceSection, boolean isAfter)
    {
        if (sourceSection == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "sourceSection");

        Body srcBody = sourceSection.getBody();
        if (srcBody == null)
            return;

        Body dstBody = getBody();
        //Ensure we have a body to copy into.
        if (dstBody == null)
            dstBody = (Body)appendChild(new Body(getDocument()));

        //This makes a copy and ensures we can insert the nodes into this document.
        srcBody = (Body)getDocument().importNode(srcBody, true);

        Node refNode = (isAfter) ? dstBody.getLastParagraph() : null;

        // WORDSNET-22257 Reference node should be immediate child, update if needed.
        while ((refNode != null) && (refNode.getParentNode() != dstBody))
            refNode = refNode.getParentNode();

        dstBody.insertAfter(srcBody.getFirstChild(), null, refNode);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*ISectionAttrSource.*/getDirectSectionAttr(int key)
    {
        return mSectPr.getDirectAttr(key);
    }
    @ReservedForInternalUse @Deprecated
    public Object /*ISectionAttrSource.*/getDirectSectionAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return mSectPr.getDirectAttr(key, revisionsView);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*ISectionAttrSource.*/fetchInheritedSectionAttr(int key)
    {
        // This attribute is only applicable document-wide, get it from docpr.
        if (key == SectAttr.ENDNOTE_LOCATION)
            return getDocument().getDocPr().FootnotePr.fetchAttr(key);
        else
            return com.aspose.words.SectPr.fetchDefaultAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*ISectionAttrSource.*/fetchSectionAttr(int key)
    {
        Object value = ((ISectionAttrSource)this).getDirectSectionAttr(key);
        return (value != null) ? value : ((ISectionAttrSource)this).fetchInheritedSectionAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*ISectionAttrSource.*/fetchSectionAttr(int key, /*RevisionsView*/int revisionsView)
    {
        Object value = ((ISectionAttrSource)this).getDirectSectionAttr(key, revisionsView);
        return (value != null) ? value : ((ISectionAttrSource)this).fetchInheritedSectionAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    
    public void /*ISectionAttrSource.*/setSectionAttr(int key, Object value)
    {
        // andrnosk: WORDSNET-9138 We have to clear PaperCode upon setting PageWidth or PageHeight.
        // The PaperCode attribute specifies the producing application’s unique identifier value for current paper size.
        // If Height/Width was changed this code has no meaning.
        if ((key == SectAttr.PAGE_WIDTH) || (key == SectAttr.PAGE_HEIGHT))
            mSectPr.remove(SectAttr.PAPER_CODE);

        if (key == SectAttr.ENDNOTE_LOCATION)
            getDocument().getDocPr().FootnotePr.setAttr(key, value);
        else
            mSectPr.setAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*ISectionAttrSource.*/setSectionAttr(int key, Object value, /*RevisionsView*/int revisionsView)
    {
        // See appropriate comment above.
        if ((key == SectAttr.PAGE_WIDTH) || (key == SectAttr.PAGE_HEIGHT))
            mSectPr.remove(SectAttr.PAPER_CODE, revisionsView);

        if (key == SectAttr.ENDNOTE_LOCATION)
            getDocument().getDocPr().FootnotePr.setAttr(key, value, revisionsView);
        else
            mSectPr.setAttr(key, value, revisionsView);
    }

    @ReservedForInternalUse @Deprecated
    
    public void /*ISectionAttrSource.*/clearSectionAttrs()
    {
        mSectPr.clear();
    }

    /// <summary>
    /// Returns true if this section is the first section of the document.
    /// </summary>
    boolean isFirstSection() { return (getParentNode().getFirstChild() == this); }

    /// <summary>
    /// Returns true if this section is the last section of the document.
    /// </summary>
    boolean isLastSection() { return (getParentNode().getLastChild() == this); }

    /// <summary>
    /// Returns true if this section is the only section of the document.
    /// </summary>
    boolean isSingleSection() { return (getParentNode().getFirstChild() == getParentNode().getLastChild()); }

    /// <summary>
    /// This class implements the <see cref="ISectionAttrSource"/> interface that allows getting attributes of
    /// the first section of the document. It is needed for the <see cref="Words.PageSetup"/> class to calculate
    /// margins and page content size with taking gutter into account.
    /// </summary>
    /// <remarks>
    /// Note that if the first section cannot be found, attribute of the current section will be returned.
    /// </remarks>
    private static class FirstSectionAttrSource implements ISectionAttrSource
    {
        FirstSectionAttrSource(Section section)
        {
            mSection = section;
        }

        public Object /*ISectionAttrSource.*/getDirectSectionAttr(int key)
        {
            return getFirstOrCurrentSection().getDirectSectionAttr(key);
        }

        public Object /*ISectionAttrSource.*/getDirectSectionAttr(int key, /*RevisionsView*/int revisionsView)
        {
            return getFirstOrCurrentSection().getDirectSectionAttr(key, revisionsView);
        }

        public Object /*ISectionAttrSource.*/fetchInheritedSectionAttr(int key)
        {
            return getFirstOrCurrentSection().fetchInheritedSectionAttr(key);
        }

        public Object /*ISectionAttrSource.*/fetchSectionAttr(int key)
        {
            return getFirstOrCurrentSection().fetchSectionAttr(key);
        }

        public Object /*ISectionAttrSource.*/fetchSectionAttr(int key, /*RevisionsView*/int revisionsView)
        {
            return getFirstOrCurrentSection().fetchSectionAttr(key, revisionsView);
        }

        public void /*ISectionAttrSource.*/setSectionAttr(int key, Object value)
        {
            // Do nothing: not intended for change.
        }

        public void /*ISectionAttrSource.*/setSectionAttr(int key, Object value, /*RevisionsView*/int revisionsView)
        {
            // Do nothing: not intended for change.
        }

        public void /*ISectionAttrSource.*/clearSectionAttrs()
        {
            // Do nothing: not intended for change.
        }

        /// <summary>
        /// Returns the first section if it can be achieved. Otherwise returns the current section.
        /// </summary>
        private ISectionAttrSource getFirstOrCurrentSection()
        {
            Document document = ms.as(mSection.getDocument(), Document.class);
            return ((document != null) && (document.getFirstSection() != null)) ? document.getFirstSection() : mSection;
        }

        private /*final*/ Section mSection;
    }

    private SectPr mSectPr;
    private PageSetup mPageSetup;
    private HeaderFooterCollection mHeadersFooters;

    public /*override*/ String toString()
    {
        // WORDSJAVA-2613 return implementation of toString method
        return msString.format("{0} {1}", super.toString(), SectionStart.toString(getSectPr().getSectionStart()));
    }
}

