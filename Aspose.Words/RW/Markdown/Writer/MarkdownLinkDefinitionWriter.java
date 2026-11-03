// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/05/2023 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ArrayUtil;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.HashUtil;
import com.aspose.DigestAlgorithm;
import java.util.ArrayList;
import java.util.HashMap;
import com.aspose.words.ref.Ref;


/// <summary>
/// The class responsible for writing link definitions into markdown.
/// </summary>
class MarkdownLinkDefinitionWriter
{
    MarkdownLinkDefinitionWriter(MarkdownWriter writer)
    {
        mWriter = writer;
    }

    /// <summary>
    /// Processes a specified ShapeBase.
    /// </summary>
    void onShape(ShapeBase shapeBase, String shapeDestination) throws Exception
    {
        if (!referenceNeeded(shapeBase))
            return;

        String reference = getReferenceString(shapeBase, shapeDestination);

        String title = shapeBase.getTitle();
        if ((shapeBase.getNodeType() == NodeType.SHAPE) && !StringUtil.hasChars(title))
            title = ((Shape)shapeBase).getImageData().getTitle();

        LinkDefinition linkDefinition = (msString.equals(reference, ""))
            ? getCollapsedLinkDefinition(shapeDestination, title)
            : getFullReferenceLinkDefinition(reference, shapeDestination, title);

        if (!StringUtil.hasChars(reference))
            reference = StringUtil.hasChars(shapeBase.getAlternativeText())
                ? formatReferenceString(shapeBase.getAlternativeText())
                : formatReferenceString(generateShapeReference());

        if (linkDefinition == null)
            msArrayList.add(mLinkDefinitions, new LinkDefinition(reference, shapeDestination, title));
    }

    /// <summary>
    /// Processes a specified FieldCodeHyperlink.
    /// </summary>
    void onHyperlink(FieldCodeHyperlink hyperlink)
    {
        if (!referenceNeeded(hyperlink))
            return;

        String reference = getReferenceString(hyperlink);

        LinkDefinition linkDefinition = (msString.equals(reference, ""))
            ? getCollapsedLinkDefinition(hyperlink.getHRef().toString(false), hyperlink.getScreenTip())
            : getFullReferenceLinkDefinition(reference, hyperlink.getHRef().toString(false), hyperlink.getScreenTip());

        if (linkDefinition == null)
            msArrayList.add(mLinkDefinitions, new LinkDefinition(reference, hyperlink.getHRef().toString(false), hyperlink.getScreenTip()));
        else if (!StringUtil.hasChars(hyperlink.getDocLocation()))
            hyperlink.setDocLocation(linkDefinition.Reference);
    }

    /// <summary>
    /// Writes prepared link definitions to a specified writer.
    /// </summary>
    void writeDefinitions(MarkdownWriter writer) throws Exception
    {
        // Adds a newline if it hasn't been added yet.
        if ((mLinkDefinitions.size() > 0) &&
            (!msString.endsWith(writer.getBuilder().toString(), msString.format("{0}{0}", StringUtil.getNewLine()), StringComparison.INVARIANT_CULTURE)))
            msStringBuilder.appendLine(writer.getBuilder());

        // Writes reference link definitions.
        for (LinkDefinition linkData : (Iterable<LinkDefinition>) mLinkDefinitions)
        {
            String label = linkData.Reference;
            // Spaces in the explicit form or in the form of a Url-code in link definitions are detected as the end of the link.
            // Therefore such link shall be enclosed in angle brackets <...>.
            String hRef = (StringUtil.indexOfWhitespace(linkData.Destination) > -1) ||
                          StringUtil.contains(linkData.Destination, "%20", true)
                ? msString.format("<{0}>", linkData.Destination)
                : linkData.Destination;
            String title = linkData.Title;
            if (title.length() > 0)
                title = msString.format(" \"{0}\"", title);
            msStringBuilder.appendLine(writer.getBuilder(), msString.format("{0}: {1}{2}", label, hRef, title));
        }
    }

    /// <summary>
    /// Returns the reference label for the specified shape.
    /// </summary>
    String getReferenceString(ShapeBase shape, String shapeDestination)
    {
        if (isReference(shape.getName()))
            return shape.getName();

        LinkDefinition linkDefinition = getCollapsedLinkDefinition(shapeDestination, shape.getTitle());
        return (linkDefinition == null) ? "" : linkDefinition.Reference;
    }

    /// <summary>
    /// Returns the reference label for the specified hyperlink.
    /// </summary>
    static String getReferenceString(FieldCodeHyperlink hyperlink)
    {
        return isReference(hyperlink.getDocLocation())
            ? hyperlink.getDocLocation()
            : "";
    }

    /// <summary>
    /// Returns True if the link definition with the specified parameters exists.
    /// </summary>
    boolean hasCollapsedLinkDefinition(String destination, String title)
    {
        return getCollapsedLinkDefinition(destination, title) != null;
    }

    /// <summary>
    /// Returns True if the link definition with the specified parameters exists.
    /// </summary>
    boolean hasFullReferenceLinkDefinition(String reference, String destination, String title)
    {
        return getFullReferenceLinkDefinition(reference, destination, title) != null;
    }

    /// <summary>
    /// Sets the specified reference value to the specified collapsed link definition.
    /// </summary>
    void setReference(String reference, String destination, String title)
    {
        LinkDefinition definition = getCollapsedLinkDefinition(destination, title);
        if (definition != null)
            definition.Reference = reference;
    }

    /// <summary>
    /// Returns true if the current shape is exported as a reference image.
    /// </summary>
    private boolean referenceNeeded(ShapeBase shapeBase) throws Exception
    {
        switch (mWriter.getSaveOptions().getLinkExportMode())
        {
            case MarkdownLinkExportMode.INLINE:
                return false;
            case MarkdownLinkExportMode.REFERENCE:
                return true;
        }

        if (getReferenceString(shapeBase, "").length() > 0)
            return true;

        // The counter of shapes similar to shapeBase.
        int linkCount = 0;

        if (shapeBase.getNodeType() == NodeType.SHAPE)
        {
            Shape shape = (Shape)shapeBase;
            for (Shape shapeNode : (Iterable<Shape>) getShapes())
            {
                if ((!msString.equals(shapeNode.getImageData().getTitle(), shape.getImageData().getTitle())) || (!msString.equals(shapeNode.getTitle(), shape.getTitle())))
                    continue;

                if ((shapeNode.getImageData().getImageBytes() != null) && (shape.getImageData().getImageBytes() != null) &&
                    (shapeNode.getImageData().getImageBytes().length != shape.getImageData().getImageBytes().length))
                    continue;

                if (StringUtil.hasChars(shapeNode.getImageData().getSourceFullName()) &&
                    (msString.equals(shapeNode.getImageData().getSourceFullName(), shape.getImageData().getSourceFullName())))
                    linkCount++;
                else if (!shapeNode.getImageData().isLinkOnly() &&
                    ArrayUtil.isArrayEqual(getShapeBytesHash(shapeNode), getShapeBytesHash(shape)))
                    linkCount++;

                if (linkCount > 1)
                    return true;
            }
        }

        if (shapeBase.getNodeType() == NodeType.GROUP_SHAPE)
        {
            GroupShape group = (GroupShape)shapeBase;
            for (GroupShape groupNode : (Iterable<GroupShape>) getGroupShapes())
            {
                if (!msString.equals(groupNode.getTitle(), group.getTitle()))
                    continue;

                if (ArrayUtil.isArrayEqual(getShapeBytesHash(groupNode), getShapeBytesHash(group)))
                    linkCount++;

                if (linkCount > 1)
                    return true;
            }
        }

        return false;
    }

    /// <summary>
    /// Returns the hash sum for the specified Shape.
    /// </summary>
    private byte[] getShapeBytesHash(ShapeBase shapeBase) throws Exception
    {
        byte[] result = null;
        Ref<byte[]> referenceToResult = new Ref<byte[]>(result);
        boolean outRefCondition0 = msDictionary.tryGetValue(mShapeBytesMap, shapeBase, /*out*/ referenceToResult);
        result = referenceToResult.get();
        if (outRefCondition0)
            return result;

        byte[] imageBytes = (shapeBase.getNodeType() == NodeType.SHAPE)
            ? ((Shape)shapeBase).getImageData().getImageBytes()
            : null;

        // FOSS: non-image shapes were rendered to a PNG to obtain bytes for content comparison.
        // Rendering is unavailable, so treat shapes without image bytes as having no comparable
        // content (empty hash) instead of dereferencing a null byte array.
        result = ((imageBytes != null) && (imageBytes.length != 0))
            ? HashUtil.computeHash(DigestAlgorithm.MD_5, imageBytes)
            : new byte[0];
        msDictionary.add(mShapeBytesMap, shapeBase, result);

        return result;
    }

    /// <summary>
    /// Formats a specified link reference label as a reference string definition.
    /// </summary>
    private static String formatReferenceString(String referenceLabel)
    {
        return msString.format("{0}{1}{2}", LinkTextBlock.OPENING_DELIMITER, referenceLabel, LinkTextBlock.CLOSING_DELIMITER);
    }

    /// <summary>
    /// Returns true if the current hyperlink is exported as a reference link.
    /// </summary>
    private boolean referenceNeeded(FieldCodeHyperlink hyperlink)
    {
        switch (mWriter.getSaveOptions().getLinkExportMode())
        {
            case MarkdownLinkExportMode.REFERENCE:
                return true;

            case MarkdownLinkExportMode.AUTO:
            {
                if (getReferenceString(hyperlink).length() > 0)
                    return true;

                // The counter of links similar to the hyperlink.
                int linkCount = 0;

                Document doc = mWriter.getDocument().fetchDocument();
                for (Field field : doc.getRange().getFields())
                {
                    FieldHyperlink hyperlinkField = ms.as(field, FieldHyperlink.class);
                    if (hyperlinkField == null)
                        continue;

                    if ((msString.equals(hyperlinkField.getAddress(), hyperlink.getHRef().toString(false))) &&
                        (msString.equals(hyperlinkField.getScreenTip(), hyperlink.getScreenTip())))
                        linkCount++;

                    if (linkCount > 1)
                        return true;
                }

                return false;
            }

            case MarkdownLinkExportMode.INLINE:
                return false;

            default:
                throw new IllegalStateException("Unexpected link export mode.");
        }
    }

    /// <summary>
    /// Returns true if a specified string is a link reference string.
    /// </summary>
    private static boolean isReference(String text)
    {
        return (text.length() > 2) &&
                msString.startsWith(text, "[", StringComparison.INVARIANT_CULTURE) &&
                msString.endsWith(text, "]", StringComparison.INVARIANT_CULTURE);
    }

    /// <summary>
    /// Generate a new reference string for a Shape.
    /// </summary>
    private String generateShapeReference()
    {
        mShapeReferenceNameGenerator++;

        for (Shape shape : (Iterable<Shape>) getShapes())
        {
            if (msString.equals(shape.getName(), msString.format("ref{0}", mShapeReferenceNameGenerator), StringComparison.ORDINAL_IGNORE_CASE))
                mShapeReferenceNameGenerator++;
        }

        return msString.format("ref{0}", mShapeReferenceNameGenerator);
    }

    /// <summary>
    /// Returns LinkDefinition with the given destination and title.
    /// </summary>
    private LinkDefinition getCollapsedLinkDefinition(String destination, String title)
    {
        for (LinkDefinition linkDefinition : (Iterable<LinkDefinition>) mLinkDefinitions)
        {
            if ((msString.equals(linkDefinition.Title, title)) &&
                (msString.equals(linkDefinition.Destination, destination)))
                return linkDefinition;
        }

        return null;
    }

    /// <summary>
    /// Returns LinkDefinition with the given destination, title and reference label.
    /// </summary>
    private LinkDefinition getFullReferenceLinkDefinition(String reference, String destination, String title)
    {
        for (LinkDefinition linkDefinition : (Iterable<LinkDefinition>) mLinkDefinitions)
        {
            if ((!msString.equals(reference, "")) && (msString.equals(linkDefinition.Reference, reference)) &&
                (msString.equals(linkDefinition.Title, title)) && (msString.equals(linkDefinition.Destination, destination)))
                return linkDefinition;
        }

        return null;
    }

    /// <summary>
    /// The cached Shape collection of the document.
    /// </summary>
    private NodeCollection getShapes()
    {
        if (mShapeCollection == null)
            mShapeCollection = mWriter.getDocument().getChildNodes(NodeType.SHAPE, true);

        return mShapeCollection;
    }

    /// <summary>
    /// The cached GroupShape collection of the document.
    /// </summary>
    private NodeCollection getGroupShapes()
    {
        if (mGroupShapeCollection == null)
            mGroupShapeCollection = mWriter.getDocument().getChildNodes(NodeType.GROUP_SHAPE, true);

        return mGroupShapeCollection;
    }

    /// <summary>
    /// The class that stores LinkDefinitionBlock string data.
    /// </summary>
    private static class LinkDefinition
    {
        LinkDefinition(String reference, String destination, String title)
        {
            Reference = reference;
            Destination = destination;
            Title = title;
        }

        String Reference;

        /*final*/ String Destination;

        /*final*/ String Title;
    }

    private NodeCollection mShapeCollection;
    private NodeCollection mGroupShapeCollection;

    private /*final*/  ArrayList<MarkdownLinkDefinitionWriter.LinkDefinition> mLinkDefinitions = new ArrayList<MarkdownLinkDefinitionWriter.LinkDefinition>();

    private /*final*/ MarkdownWriter mWriter;

    /// <summary>
    /// The collection of shapes and their hashes.
    /// </summary>
    private /*final*/ HashMap<ShapeBase, byte[]> mShapeBytesMap = new HashMap<ShapeBase, byte[]>();

    private int mShapeReferenceNameGenerator;
}

