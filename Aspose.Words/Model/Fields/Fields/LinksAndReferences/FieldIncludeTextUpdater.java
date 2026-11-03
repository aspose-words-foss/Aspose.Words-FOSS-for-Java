// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/04/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IDisposable;
import com.aspose.ms.System.ms;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.StreamReader;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.StreamWriter;
import com.aspose.XmlUtilPal;
import com.aspose.ms.System.Xml.XmlNodeType;
import com.aspose.StringUtil;
import com.aspose.IntToIntBidirectionalMap;
import com.aspose.XslTransform;
import com.aspose.StringToObjDictionary;


class FieldIncludeTextUpdater
{
    static FieldUpdateAction update(Field field) throws Exception
    {
        FieldIncludeTextUpdater updater = new FieldIncludeTextUpdater(field, (IFieldIncludeTextCode)field);
        return updater.update();
    }

    private FieldIncludeTextUpdater(Field field, IFieldIncludeTextCode fieldCode)
    {
        mField = field;
        mFieldCode = fieldCode;
    }

    private FieldUpdateAction update() throws Exception
    {
        String sourceFileName = getSourceFileName();
        try
        {
            Stream stream = FieldUtil.openStream(sourceFileName, mField.getDocument().getResourceLoadingCallback());
            try /*JAVA: was using*/
            {
                if (stream == null)
                    return createNotValidFileNameErrorMessage(sourceFileName);

                if (hasXPathExpression())
                    return evaluateXPath(stream);

                if (hasXslTransformation())
                    return evaluateXslTransformation(stream, sourceFileName);

                return updateFromSourceDocument(stream, sourceFileName);
            }
            finally { if (stream != null) stream.close(); }
        }
        catch(Exception e)
        {
            return createNotValidFileNameErrorMessage(sourceFileName);
        }
    }

    private FieldUpdateAction createNotValidFileNameErrorMessage(String sourceFileName) throws Exception
    {
        return new FieldUpdateActionInsertErrorMessage(mField, getNotValidFileNameErrorMessage(sourceFileName));
    }

    private String getNotValidFileNameErrorMessage(String sourceFileName) throws Exception
    {
        return hasXPathExpression()
            ? msString.format("Error! Word encountered an error while loading the XML file {0}.", sourceFileName)
            : "Error! Not a valid filename.";
    }

    private FieldUpdateAction updateFromSourceDocument(Stream sourceDocStream, String sourceFileName) throws Exception
    {
        Document sourceDoc = loadSourceDocument(sourceDocStream);
        return updateFromDocument(sourceDoc, sourceFileName);
    }

    private Document loadSourceDocument(Stream stream) throws Exception
    {
        // Only include Aspose.Words documents.
        /*LoadFormat*/int loadFormat = FileFormatUtil.detectFileFormat(stream).getLoadFormat();
        if (loadFormat == LoadFormat.UNKNOWN)
            return null;

        String textConverter = mFieldCode.getTextConverter();
        if (textConverter != null)
            textConverter = textConverter.toLowerCase();

        switch (loadFormat)
        {
            case LoadFormat.HTML:
                if ("xml".equals(textConverter) && !startsWith(stream, "<!doctype"))
                    return loadXmlDocument(stream);

                return loadHtmlDocument(stream);
            case LoadFormat.TEXT:
                return "html".equals(textConverter)
                    ? loadPlainTextAsHtmlDocument(stream)
                    : loadMonospaceTextDocument(stream);
            case LoadFormat.XML:
                return loadXmlContent(stream, textConverter);
            default:
                break;
        }

        return new Document(stream, null, false);
    }

    private static Document loadXmlContent(Stream stream, String textConverter) throws Exception
    {
        // MS Word does not treat document without XML declaration as XML document.
        if (msString.isNullOrEmpty(textConverter) && !startsWith(stream, "<?xml"))
            return loadMonospaceTextDocument(stream);

        return "html".equals(textConverter)
            ? loadHtmlDocument(stream)
            : loadXmlDocument(stream);
    }

    private static boolean startsWith(Stream stream, String value) throws Exception
    {
        try
        {
            StreamReader reader = new StreamReader(stream);
            for (int i = 0; i < value.length(); i++)
            {
            	char c = value.charAt(i);
                if (reader.read() != c)
                    return false;
            }

            return true;
        }
        finally
        {
            stream.setPosition(0);
        }
    }

    private static Document loadPlainTextAsHtmlDocument(Stream stream) throws Exception
    {
        StreamReader reader = new StreamReader(stream);
        String text = reader.readToEnd();
        MemoryStream htmlStream = new MemoryStream();
        try /*JAVA: was using*/
        {
            StreamWriter writer = new StreamWriter(htmlStream);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><html>");
            writer.write(text);
            writer.write("</html>");
            writer.flush();
            htmlStream.setPosition(0);
            return loadHtmlDocument(htmlStream);
        }
        finally { if (htmlStream != null) htmlStream.close(); }
    }

    private static Document loadMonospaceTextDocument(Stream stream) throws Exception
    {
        TxtLoadOptions loadOptions = new TxtLoadOptions();
        loadOptions.setLeadingSpacesOptions(TxtLeadingSpacesOptions.PRESERVE);
        loadOptions.setLoadFormat(LoadFormat.TEXT);
        return new Document(stream, loadOptions, false);
    }

    private static Document loadXmlDocument(Stream stream) throws Exception
    {
        org.w3c.dom.Document xml = XmlUtilPal.loadXml(stream, false);
        DocumentBuilder builder = new DocumentBuilder(new Document(DocumentCtorMode.BLANK_DOCUMENT_NODE));

        loadXmlNode(builder, xml.getDocumentElement(), 0);

        return builder.getDocument();
    }

    private static void loadXmlNode(DocumentBuilder builder, org.w3c.dom.Node node, int depth)
    {
        if (!node.hasChildNodes())
        {
            builder.getCurrentParagraph().getParagraphFormat().setLeftIndent(depth * 18);
            builder.writeln(msString.trim(node.getNodeValue()));
        }
        else
        {
            if (node.getChildNodes().getLength() == 1 && XmlUtilPal.isNodeType(node.getFirstChild(), XmlNodeType.TEXT))
            {
                loadXmlNode(builder, node.getFirstChild(), depth);
                return;
            }

            builder.getCurrentParagraph().getParagraphFormat().setLeftIndent(depth * 18);
            builder.writeln();

            for (int i = 0; i < node.getChildNodes().getLength(); i++)
            {
                org.w3c.dom.Node childNode = node.getChildNodes().item(i);
                if (!XmlUtilPal.isNodeType(childNode, XmlNodeType.WHITESPACE))
                    loadXmlNode(builder, childNode, depth + 1);
            }

            builder.getCurrentParagraph().getParagraphFormat().setLeftIndent(depth * 18);
            builder.writeln();
        }
    }

    private static Document loadHtmlDocument(Stream stream) throws Exception
    {
        HtmlLoadOptions loadOptions = new HtmlLoadOptions();
        loadOptions.setLoadFormat(LoadFormat.HTML);
        return new Document(stream, loadOptions, false);
    }

    private FieldUpdateAction updateFromDocument(Document document, String sourceFileName) throws Exception
    {
        if (document == null)
            return new FieldUpdateActionInsertErrorMessage(mField, "Cannot include document of this type at the moment.");

        /*SectionStart*/int endSectionStart = ((Section)mField.getEnd().getAncestor(NodeType.SECTION)).getPageSetup().getSectionStart();
        NodeRange sourceRange;
        boolean needMergeFirstParagraph = false;

        FieldUpdateActionFormatResult result;

        // Remove the old result.
        IDisposable iDisposableResource = mField.getUpdateContext().removeOldResultSafe();
        try /*JAVA: was using*/
        {
            UnpairedBookmarkNodeRemover listener = null;

            if (!StringUtil.hasChars(mFieldCode.getBookmarkName()))
            {
                // The source range is the whole document.
                Node endNode = document.getLastSection().getBody().getLastChild();
                Node startNode = document.getFirstSection().getBody().getFirstChild();
                needMergeFirstParagraph =
                    document.getFirstSection().getBody().getFirstNonAnnotationChild().getNodeType() == NodeType.PARAGRAPH;
                sourceRange = new NodeRange(startNode, endNode);
            }
            else
            {
                // The source range is a bookmark.
                // SPEED No need to get a bookmark from a source document cache as the cache is not to be used anywhere else.
                Bookmark sourceBookmark = document.getRange().getBookmarks().get(mFieldCode.getBookmarkName());
                if (sourceBookmark == null)
                    return new FieldUpdateActionInsertErrorMessage(mField, Bookmark.ERROR_BOOKMARK_NOT_DEFINED);

                // WORDSNET-11285 MS Work copies text with all related bookmarks
                sourceRange = sourceBookmark.getMostWideNodeRange();
                listener = new UnpairedBookmarkNodeRemover();
            }

            // Updating field result may change formating source node. Preserve it by creating update action beforehand.
            result = new FieldUpdateActionFormatResult(mField);

            ImportedStylesIstdsCollector stylesIstdsCollector = mField.getUpdater().getImportedStylesIstdsCollector();
            IntToIntBidirectionalMap importedIstds = stylesIstdsCollector.getImportedStylesIstds(sourceFileName);

            ExternalDocumentModifier modifier = new ExternalDocumentModifier(document, mField.fetchDocument(), importedIstds);
            NodeCopier.copy(sourceRange, mField.getEnd(), modifier, listener,
                NodeCopierOptions.USE_SOURCE_START_ANCESTOR_PR | NodeCopierOptions.CLONE_NODE |
                    NodeCopierOptions.PROCESS_BOUND_BLOCK_ANNOTATION_AS_INLINE);

            // WORDSNET-11285 If copied range contains unpaired bookmark nodes - remove them.
            if (listener != null)
                listener.removeUnpairedBookmarkNodes();
        }
        finally { if (iDisposableResource != null) iDisposableResource.dispose(); }

        if (needMergeFirstParagraph)
            mergeFirstParagraph();

        updateLastSectionProperties(sourceRange, endSectionStart);

        if (mFieldCode.getLockFields())
            lockChildFieldsInResult();

        return result;
    }

    private FieldUpdateAction evaluateXPath(Stream stream) throws Exception
    {
        org.w3c.dom.Document document = XmlUtilPal.loadXml(stream, true);
        String result = XPathEvaluator.evaluate(document, mFieldCode.getXPath(), mFieldCode.getNamespaceMappings());
        return new FieldUpdateActionApplyResult(mField, result);
    }

    private FieldUpdateAction evaluateXslTransformation(Stream stream, String sourceFileName) throws Exception
    {
        XslTransform transform = new XslTransform();
        transform.load(loadXslTransformation());

        Stream xsltOutput = transform.transform(stream);
        try /*JAVA: was using*/
        {
            return updateFromXslTransformedDocument(xsltOutput, sourceFileName);
        }
        finally { if (xsltOutput != null) xsltOutput.close(); }
    }

    private FieldUpdateAction updateFromXslTransformedDocument(Stream stream, String sourceFileName) throws Exception
    {
        Document document = loadXslTransformedDocument(stream);
        return updateFromDocument(document, sourceFileName);
    }

    private static Document loadXslTransformedDocument(Stream stream) throws Exception
    {
        // Only include Aspose.Words documents.
        /*LoadFormat*/int loadFormat = FileFormatUtil.detectFileFormat(stream).getLoadFormat();
        if (loadFormat == LoadFormat.UNKNOWN)
            return null;

        switch (loadFormat)
        {
            case LoadFormat.HTML:
                return loadHtmlDocument(stream);
            case LoadFormat.TEXT:
                return loadMonospaceTextDocument(stream);
            case LoadFormat.XML:
                return loadXmlDocument(stream);
            default:
                return new Document(stream, null, false);
        }
    }

    private Stream loadXslTransformation() throws Exception
    {
        try
        {
            return FieldUtil.openStream(mFieldCode.getXslTransformation(), mField.getDocument().getResourceLoadingCallback());
        }
        catch(Exception e)
        {
            return null;
        }
    }

    private boolean hasXPathExpression() throws Exception { return !msString.isNullOrEmpty(mFieldCode.getXPath()); }

    private boolean hasXslTransformation() { return !msString.isNullOrEmpty(mFieldCode.getXslTransformation()); }

    private static boolean isFirstChild(Node node, /*NodeType*/int ancestorType)
    {
        if (node.getNodeType() == ancestorType)
            return true;

        if (!node.isFirstChild() &&
            !(node.isComposite() && (node.getPreviousNonAnnotationSibling() == null)))
        {
            return false;
        }

        return isFirstChild(node.getParentNode(), ancestorType);
    }

    private static boolean isLastChild(Node node, /*NodeType*/int ancestorType)
    {
        if (node.getNodeType() == ancestorType)
            return true;

        if (!node.isLastChild() &&
            !(node.isComposite() && (node.getNextNonAnnotationSibling() == null)))
        {
            return false;
        }

        return isLastChild(node.getParentNode(), ancestorType);
    }

    private void updateLastSectionProperties(NodeRange sourceRange, /*SectionStart*/int endSectionStart)
    {
        boolean startIsFirstChild = isFirstChild(mField.getStart(), NodeType.SECTION);
        boolean endIsLastChild = isLastChild(mField.getEnd(), NodeType.SECTION);
        if (startIsFirstChild && endIsLastChild)
        {
            copyLastSectionProperties(sourceRange);
        }
        else
        {
            setLastSectionBreakType(endSectionStart);
        }
    }

    private void copyLastSectionProperties(NodeRange sourceRange)
    {
        // WORDSNET-10770 MS Word additionally copies properties of the last section, if there is no content except field.
        Section fieldEndSection = (Section)mField.getEnd().getAncestor(NodeType.SECTION);
        Section sourceEndSection = (Section)sourceRange.getEnd().getNode().getAncestor(NodeType.SECTION);

        fieldEndSection.setSectPr(AttrCollectionExtension.deepClone(sourceEndSection.getSectPr()));
    }

    private void setLastSectionBreakType(/*SectionStart*/int endSectionStart)
    {
        // WORDSNET-10786, WORDSNET-12104 MS Word retains last section type, if there is any content in source section except INCLUDETEXT field.
        Section fieldEndSection = (Section)mField.getEnd().getAncestor(NodeType.SECTION);
        fieldEndSection.getPageSetup().setSectionStart(endSectionStart);
    }

    private String getSourceFileName() throws Exception
    {
        // WORDSNET-11245 if SourceFullName parameter contains fields we should not normalize text in field argument
        FieldArgument argument = mField.getFieldCodeCache().getArgument(mFieldCode.getSourceFullNameArgumentIndex());
        if (argument == null || !argument.containsChildFields())
            return mFieldCode.getSourceFullName();

        // Mimic MS Word: escape \, if argument contains nested fields
        // This is a HACK. Ideally, we should escape nested fields result, but it is way too complex.
        return msString.trim(argument.getText(), '"').replace("\\\\", "\\");
    }

    private void lockChildFieldsInResult()
    {
        for (Node node : mField.getFieldResultRange())
        {
            if (node.getNodeType() == NodeType.FIELD_START)
                ((FieldStart)node).isLocked(true);
        }
    }

    private void mergeFirstParagraph()
    {
        Paragraph separatorParagraph = mField.getSeparator().getParentParagraph();
        Paragraph firstContentParagraph = ms.as(separatorParagraph.getNextNonAnnotationSibling(), Paragraph.class);

        if (firstContentParagraph != null)
        {
            Node referenceNode = firstContentParagraph.getFirstChild();
            Node nextNode;

            for (Node node = separatorParagraph.getFirstChild(); node != null; node = nextNode)
            {
                nextNode = node.getNextSibling();
                firstContentParagraph.insertBefore(node, referenceNode);
            }

            // Move block-level annotations.
            firstContentParagraph.insertBefore(separatorParagraph.getNextSibling(), firstContentParagraph, referenceNode);

            separatorParagraph.remove();
        }
    }

    private /*final*/ Field mField;
    private /*final*/ IFieldIncludeTextCode mFieldCode;

    private static class UnpairedBookmarkNodeRemover implements INodeCloningListener
    {
        UnpairedBookmarkNodeRemover()
        {
            mStarts = new StringToObjDictionary<Node>();
            mEnds = new StringToObjDictionary<Node>();
        }

        public void notifyNodeCloned(Node source, Node clone)
        {
            if (clone.getNodeType() == NodeType.BOOKMARK_START)
                mStarts.set(((BookmarkStart)clone).getName(), clone);
            else if (clone.getNodeType() == NodeType.BOOKMARK_END)
                mEnds.set(((BookmarkEnd)clone).getName(), clone);
        }

        void removeUnpairedBookmarkNodes()
        {
            for (String key : mStarts.getKeys())
            {
                if (mEnds.containsKey(key))
                    mEnds.remove(key);
                else
                    mStarts.get(key).remove();
            }

            for (Node node : mEnds.getValues())
                node.remove();
        }

        private /*final*/ StringToObjDictionary<Node> mStarts;
        private /*final*/ StringToObjDictionary<Node> mEnds;
    }
}

