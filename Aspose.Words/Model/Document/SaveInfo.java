// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.IO.Stream;
import com.aspose.Debug;
import com.aspose.HashSetGeneric;
import com.aspose.MemoryStorage;
import com.aspose.SortedStringListGeneric;
import com.aspose.SortedIntegerListGeneric;
import com.aspose.ISetGeneric;
import com.aspose.IntToObjDictionary;
import com.aspose.ObjToIntDictionary;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;
import com.aspose.DictionaryExtension;
import java.util.Map;


/// <summary>
/// Encapsulates parameters passed into a document writer.
/// </summary>
class SaveInfo
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="doc"></param>
    /// <param name="stream">Can be null when not saving, but running document validator. For example during page layout only.</param>
    /// <param name="fileName">Can be null when saving to a stream or when not saving.</param>
    /// <param name="saveOptions"></param>
    SaveInfo(Document doc, Stream stream, String fileName, SaveOptions saveOptions)
    {
        Debug.assertTrue(doc != null);
        Debug.assertTrue(saveOptions != null);

        Document = doc;
        Stream = stream;
        mFileName = fileName;
        SaveOptions = saveOptions;

        for (int i = 0; i < UsedParaIdArray.length; i++)
            UsedParaIdArray[i] = new HashSetGeneric<Integer>();
    }

    /// <summary>
    /// The document being saved.
    /// </summary>
    Document Document;

    /// <summary>
    /// The stream where the document is to be written to.
    /// The writer should not close the stream.
    /// </summary>
    Stream Stream;

    /// <summary>
    /// The name of the file that is being saved to (if known).
    /// If not saving to a file, this will be null.
    /// Used by some writers, for example HTML writer to derive file names for images
    /// as they are written into the same folder as the main document.
    /// </summary>
    String getFileName() { return mFileName; };

    private String mFileName;

    /// <summary>
    /// The format in which the document is to be saved.
    /// </summary>
    /*SaveFormat*/int getSaveFormat() { return SaveOptions.getSaveFormat(); }

    /// <summary>
    /// The options that control how the document is to be saved.
    /// </summary>
    SaveOptions SaveOptions;

    /// <summary>
    /// Gets a boolean value indicating either a document is saving
    /// into one of the legacy formats (DOC, DOT, RTF or WML).
    /// </summary>
    boolean isLegacyFormat()
    {
        return isDocFormat() ||
                (getSaveFormat() == SaveFormat.WORD_ML) ||
                (getSaveFormat() == SaveFormat.RTF);
    }

    /// <summary>
    /// All OLE objects including embedded, linked and controls are collected here.
    /// Needed for writing to the binary DOC object pool.
    /// Key is string, value is MemoryStorage.
    /// </summary>
    MemoryStorage AllOleObjects = new MemoryStorage();

    /// <summary>
    /// Contains only embedded OLE objects (but not controls).
    /// This is needed for writing WordML.
    /// Key is string, value is MemoryStorage.
    /// </summary>
    MemoryStorage EmbeddedOleObjects = new MemoryStorage();

    /// <summary>
    /// Returns true if the document has at least one footnote.
    /// </summary>
    boolean HasFootnotes;

    /// <summary>
    /// Returns true if the document has at least one endnote.
    /// </summary>
    boolean HasEndnotes;

    /// <summary>
    /// Returns true if the document has at least one OLE control.
    /// </summary>
    boolean HasOleControls;

    /// <summary>
    /// Returns true if the document has at least one OLE object.
    /// </summary>
    boolean hasEmbeddedOleObjects() { return (EmbeddedOleObjects.getCount() > 0); }

    /// <summary>
    /// All XML namespaces used by custom XML markup and smart tags in the document are collected here.
    /// The key is a namespace. The value is an integer id.
    /// </summary>
    /*final*/ SortedStringListGeneric<Integer> XmlNamespaces = new SortedStringListGeneric<Integer>();

    /// <summary>
    /// Parallel to <see cref="XmlNamespaces"/> list of boolean values indicating that origin of Uri is SmartTag.
    /// The key is namespace id.
    /// </summary>
    /*final*/ SortedIntegerListGeneric<Boolean> IsSmartTagOriginated = new SortedIntegerListGeneric<Boolean>();

    /// <summary>
    /// All XML namespaces used by custom XML markup that need to be written as attached schemas are collected here.
    /// The key and the value are both the namespace uri.
    /// </summary>
    SortedStringListGeneric<String> AttachedSchemas = new SortedStringListGeneric<String>();

    /// <summary>
    /// All unique full smart tag names are collected here.
    /// The key is a uri+name string. The value is a <see cref="Markup.SmartTag"/> object.
    /// </summary>
    SortedStringListGeneric<SmartTag> SmartTagTypes = new SortedStringListGeneric<SmartTag>();

    /// <summary>
    /// Contains bookmark names. This is used in HTML export.
    /// </summary>
    ISetGeneric<String> BookmarkNamesSet;

    /// <summary>
    /// Contains all shapes of the document.
    /// The key is integer shape id. The value is a shape node.
    /// </summary>
    IntToObjDictionary<ShapeBase> Shapes = new IntToObjDictionary<ShapeBase>();

    /// <summary>
    /// Contains ids of shapes that are linked in textbox chains.
    /// </summary>
    HashSetGeneric<Integer> LinkedShapeIds = new HashSetGeneric<Integer>();

    /// <summary>
    /// Indicates that document contains HTML related information references.
    /// </summary>
    boolean HasHtmlBlockReferences;

    /// <summary>
    /// Indicates that linked textboxes have conflicting names. This means that textboxes MUST be written linked by ShapeId.
    /// </summary>
    boolean LinkedShapeNameConflict;

    /// <summary>
    /// Returns format-specific z-order value for shape.
    /// </summary>
    int getShapeZIndex(ShapeBase shape)
    {
        int zIndex = mZIndexTable.get(shape);
        return ObjToIntDictionary.isNullSubstitute(zIndex) ? 0 : zIndex;
    }

    /// <summary>
    /// Top level shapes are added to a list during document validation because several exporters
    /// need to calculate z-index values for them that are different from ZOrder in the model.
    /// </summary>
    void addToZOrderList(Node shape, StoryTypeStack storyTypeStack)
    {
        if ((shape.getParentNode() == null) || (shape.getParentNode().getNodeType() != NodeType.GROUP_SHAPE))
            msArrayList.add(getZOrderList(storyTypeStack), (ShapeBase)shape);
    }

    private ArrayList<ShapeBase> getZOrderList(StoryTypeStack storyTypeStack)
    {
        if ( (storyTypeStack.isInFooter()) )
            return mZOrderListFooter;
        else
            return (storyTypeStack.isInHeader()) ? mZOrderListHeader : mZOrderListMain;
    }

    /// <summary>
    /// Builds a sequence of z-indexes suitable for exporting to WordML. May be used in other formats.
    /// Shapes above text indexes are 1-based. Shapes below text have negative values up to -1.
    /// </summary>
    void makeZIndexVmlStyle()
    {
        makeZIndexVmlStyle(mZOrderListMain, 1, -1);
        makeZIndexVmlStyle(union(mZOrderListHeader, mZOrderListFooter), 1, -1);
    }

    private void makeZIndexVmlStyle(ArrayList<ShapeBase> zOrderList, int minAboveIndex, int maxBelowIndex)
    {
        if (zOrderList.size() == 0)
            return;

        ShapeZOrderSorter.sort(zOrderList);

        // First pass, we number shapes above text starting from minAboveIndex to (minAboveIndex + NumberOfAboveText).
        int zIndex = minAboveIndex;
        int belowTextShapeCount = 0;
        for (ShapeBase shape : zOrderList)
        {
            if (shape.isInline())
                continue;

            if (!shape.getBehindText())
                mZIndexTable.set(shape, zIndex++);
            else
                belowTextShapeCount++;
        }

        // Second pass, we number shapes below text starting from (maxBelowIndex - NumberOfBelowText + 1) to -1.
        zIndex = maxBelowIndex - belowTextShapeCount + 1;
        for (ShapeBase shape : zOrderList)
        {
            if (shape.isInline())
                continue;

            if (shape.getBehindText())
                mZIndexTable.set(shape, zIndex++);
        }
    }

    private void makeZIndexRtfStyle(ArrayList<ShapeBase> zOrderList)
    {
        if (zOrderList.size() == 0)
            return;

        ShapeZOrderSorter.sort(zOrderList);

        int zIndex = 0;
        for (ShapeBase shape : zOrderList)
        {
            // Pseudoinline shapes in RTF seem to have a z-index, so let them through even if they are inline.
            if (shape.isInline() && !shape.isPseudoInline())
                continue;

            mZIndexTable.set(shape, zIndex++);
        }
    }

    /// <summary>
    /// Builds a sequence of z-indexes for top level shapes. Suitable for use in DOCX export.
    ///
    /// RK This code is experimental. I am trying to figure out how MS Word generates zindex in ooxml.
    /// It seems to increase zindex for every shape by 0x400.
    /// For shapes above text the top byte is 0x0f, for shapes below text the top byte is 0xf1
    /// (which results in a negative value). However, for DrawingML images below text it is still 0x0f.
    ///
    /// Note that if I use 1-based values like in WordML, then MS Word fails to place DrawingML ojects properly.
    /// Apart from difference in actual values, the algorithm is exactly the same as for WML.
    /// We can change WML writer to use this code (but will have to accept all new golds),
    /// but we should not change DOCX writer to use WML zindexes.
    /// </summary>
    void makeZIndexDocxStyle()
    {
        makeZIndexDocxStyle(mZOrderListMain);

        // WORDSNET-21517 Splitting the images in the headers and footers into two independent subgroups.
        // The Z-ordering of the images is generated for each subgroup separately.
        makeZIndexDocxStyle(mZOrderListHeader);
        makeZIndexDocxStyle(mZOrderListFooter);
    }

    private void makeZIndexDocxStyle(ArrayList<ShapeBase> zOrderList)
    {
        if (zOrderList.size() == 0)
            return;

        ShapeZOrderSorter.sort(zOrderList);

        // Number shapes in one pass.
        int zOrder = 0;
        for (ShapeBase shape : zOrderList)
        {
            if (shape.isInline())
                continue;

            mZIndexTable.add(shape, ZOrderUtil.zOrderToZIndex(zOrder, shape.getBehindText() && (shape.getMarkupLanguage() != ShapeMarkupLanguage.DML)));
            zOrder++;
        }
    }

    /// <summary>
    /// Builds a sequence of z-indexes for top level shapes. Suitable for use in HTML export.
    /// </summary>
    void makeZIndexHtmlStyle()
    {
        makeZIndexVmlStyle(mZOrderListMain, 0, -1);
        makeZIndexVmlStyle(union(mZOrderListHeader, mZOrderListFooter), -0x10000, -0x10001);
    }

    /// <summary>
    /// Builds a 0-based sequence of z-indexes for top level shapes. Suitable for use in RTF export.
    /// </summary>
    void makeZIndexRtfStyle()
    {
        makeZIndexRtfStyle(mZOrderListMain);
        makeZIndexRtfStyle(union(mZOrderListHeader, mZOrderListFooter));
    }

    /// <summary>
    /// Returns true on saving to Word format before OOXML ISO Transitional (OOXML ECMA-376, DOC, RTF or WML).
    /// </summary>
    boolean isPreIsoTransitionalWordFormat()
    {
        return (isOoxmlFormat() && (getOoxmlCompliance() == OoxmlComplianceCore.ECMA_376)) || isLegacyFormat();
    }

    /// <summary>
    /// Indicates that comment has comment range.
    /// </summary>
    boolean hasRange(Comment comment)
    {
        return mCommentsWithRange.contains(comment);
    }

    /// <summary>
    /// Flags comment as having range.
    /// </summary>
    void setHasRange(Comment comment)
    {
        mCommentsWithRange.add(comment);
    }

    /// <summary>
    /// Returns the original OfficeMath node that the shape was created from.
    /// </summary>
    OfficeMath getOriginalOfficeMath(ShapeBase shapeBase)
    {
        return (mShapeToConvertedOfficeMath != null)
            ? DictionaryExtension.getValueOrNull(mShapeToConvertedOfficeMath, shapeBase)
            : null;
    }

    /// <summary>
    /// Unions the two specified lists.
    /// </summary>
    private static ArrayList<ShapeBase> union(ArrayList<ShapeBase> list1, ArrayList<ShapeBase> list2)
    {
        Debug.assertTrue((list1 != null) && (list2 != null));

        ArrayList<ShapeBase> union = msArrayList.ctor(list1);
        for (ShapeBase shape : list2)
            msArrayList.add(union, shape);
        return union;
    }

    /// <summary>
    /// Returns true if it is Ooxml format.
    /// </summary>
    /// <remarks>
    /// Docx, Docm, Dotx, Dotm, FlatOpc, FlatOpcMacroEnabled, FlatOpcTemplate, FlatOpcTemplateMacroEnabled.
    /// </remarks>
    boolean isOoxmlFormat()
    {
        return (isDocxFormat() || isFlatOpcFormat());
    }

    /// <summary>
    /// Returns true if it is Docx, Docm, Dotx, Dotm format.
    /// </summary>
    boolean isDocxFormat()
    {
        return ((getSaveFormat() == SaveFormat.DOCX) ||
                (getSaveFormat() == SaveFormat.DOCM) ||
                (getSaveFormat() == SaveFormat.DOTX) ||
                (getSaveFormat() == SaveFormat.DOTM));
    }

    /// <summary>
    /// Returns true if it is FlatOpc, FlatOpcMacroEnabled, FlatOpcTemplate, FlatOpcTemplateMacroEnabled format.
    /// </summary>
    boolean isFlatOpcFormat()
    {
        return ((getSaveFormat() == SaveFormat.FLAT_OPC) ||
                (getSaveFormat() == SaveFormat.FLAT_OPC_MACRO_ENABLED) ||
                (getSaveFormat() == SaveFormat.FLAT_OPC_TEMPLATE) ||
                (getSaveFormat() == SaveFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED));
    }

    /// <summary>
    /// Returns true if it is Doc or Dot format.
    /// </summary>
    boolean isDocFormat()
    {
        return ((getSaveFormat() == SaveFormat.DOC) ||
                (getSaveFormat() == SaveFormat.DOT));
    }

    /// <summary>
    /// Specifies the OOXML version for the output document on saving as DOCX formats.
    /// </summary>
    /*OoxmlComplianceCore*/int getOoxmlCompliance()
    {
        return OoxmlComplianceInfo.getCompliance(Document.getComplianceInfo(), ms.as(SaveOptions, OoxmlSaveOptions.class));
    }

    Map<ShapeBase, OfficeMath> getShapeToConvertedOfficeMath() { return mShapeToConvertedOfficeMath; }
    void setShapeToConvertedOfficeMath(Map<ShapeBase, OfficeMath> value) { mShapeToConvertedOfficeMath = value; }

    /// <summary>
    /// Indicates either there are any paragraphs with font effects in <see cref="Paragraph.ParagraphBreakRunPr"/>
    /// </summary>
    /// <remarks>
    /// This feature available in LibreOffice only and is supported by AW per WORDSNET-17459
    /// </remarks>
    boolean hasParagraphGraphicsExtension() { return mHasParagraphGraphicsExtension; }; void hasParagraphGraphicsExtension(boolean value) { mHasParagraphGraphicsExtension = value; };

    private boolean mHasParagraphGraphicsExtension;

    /// <summary>
    /// Array of connector rules for shapes in body.
    /// </summary>
    /*final*/ ArrayList<ShapeBase> ConnectorsBody = new ArrayList<ShapeBase>();

    /// <summary>
    /// Array of connector rules for shapes in header/footer.
    /// </summary>
    /*final*/ ArrayList<ShapeBase> ConnectorsHdr = new ArrayList<ShapeBase>();

    /// <summary>
    /// Existing ParaId collected during validation stage grouped by story type.
    /// </summary>
    /*final*/ Object[] UsedParaIdArray =
        new Object[(int)StoryType.ENDNOTE_CONTINUATION_SEPARATOR];

    /// <summary>
    /// Existing row ParaId collected during validation stage.
    /// </summary>
    /*final*/ HashSetGeneric<Integer> UsedRowParaIds = new HashSetGeneric<Integer>();

    /// <summary>
    /// Existing comment DurableId collected during validation stage.
    /// </summary>
    /*final*/ HashSetGeneric<Integer> UsedCommentDurableIds = new HashSetGeneric<Integer>();

    private /*final*/ HashSetGeneric<Comment> mCommentsWithRange = new HashSetGeneric<Comment>();

    /// <summary>
    /// Table of format specific z-index values for top level shapes.
    /// </summary>
    private /*final*/ ObjToIntDictionary<ShapeBase> mZIndexTable = new ObjToIntDictionary<ShapeBase>();

    private /*final*/ ArrayList<ShapeBase> mZOrderListMain = new ArrayList<ShapeBase>();
    private /*final*/ ArrayList<ShapeBase> mZOrderListHeader = new ArrayList<ShapeBase>();
    private /*final*/ ArrayList<ShapeBase> mZOrderListFooter = new ArrayList<ShapeBase>();

    /// <summary>
    /// Maps original ShapeBase objects to converted OfficeMath objects
    /// when document is saved to format which doesn't support OMML.
    /// Keys are instances of <see cref="ShapeBase"/>.
    /// Values are instances of <see cref="OfficeMath"/>.
    /// </summary>
    private Map<ShapeBase, OfficeMath> mShapeToConvertedOfficeMath;

    /// <summary>
    /// Collection of SDT forced to write ShowingPlaceholder property.
    /// </summary>
    HashSetGeneric<StructuredDocumentTag> ForceShowingPlaceholder = new HashSetGeneric<StructuredDocumentTag>();
}

