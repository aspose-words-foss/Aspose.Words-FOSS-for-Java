// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/04/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Guid;
import com.aspose.Debug;
import com.aspose.MathUtil;
import com.aspose.ArrayUtil;
import com.aspose.DrColor;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.msString;
import com.aspose.MemoryStorage;
import java.util.HashMap;
import java.util.Iterator;
import com.aspose.StringUtil;
import com.aspose.HashSetGeneric;


/// <summary>
/// This class is invoked by <see cref="DocumentValidator"/> to correct any issues with shapes
/// including DML->VML and VML->DML conversion.
/// </summary>
class ShapeValidator
{
    ShapeValidator(SaveInfo saveInfo,
        /*DocumentValidatorActions*/int saveActions,
        DocumentVisitor validator)
    {
        mSaveInfo = saveInfo;
        mSaveActions = saveActions;
        mValidator = validator;

        mShapeIdGenerator = new ShapeIdGenerator(saveInfo);
        mShapeImageOptimizer = new ShapeImageOptimizer();
    }

    /// <summary>
    /// Performs re-initialization for the main document and for the glossary document.
    /// </summary>
    void documentStart(DocumentBase doc, BookmarkValidator bookmarkValidator)
    {
        mBookmarkValidator = bookmarkValidator;
        mDrawingMLIdValidator = new DrawingMLIdValidator();

        validateBackgroundShape(doc);
    }

    /// <summary>
    /// Sets properties on the document background shape that we know MS Word sets on a background shape in a DOC file.
    /// Not setting this will not crash MS Word, but still do it for the sake of consistency.
    /// </summary>
    private void validateBackgroundShape(DocumentBase doc)
    {
        Shape shape = doc.getBackgroundShape();
        if (shape == null)
            return;

        // FOSS

        shape.setId(1025);  // The background shape has this id in MS Word.
        // It is needed to assign shape for DML fill.
        if ((shape.getMarkupLanguage() == ShapeMarkupLanguage.DML) &&
            (((IDmlCommonShapePrSource)shape.getDmlNode()).getFill() != null))
        {
            ((IDmlCommonShapePrSource)shape.getDmlNode()).getFill().setParent(shape);
        }

        shape.setFilled(true);
        shape.setStrokeWeight(0.0);
        shape.setStroked(false);
        shape.setFlipOrientation(FlipOrientation.NONE);
    }

    /// <summary>
    /// Performs finalization for the main document and for the glossary document.
    /// </summary>
    void documentEnd(DocumentBase doc)
    {
        // FOSS
        mDrawingMLIdValidator.validate();
    }

    /*VisitorAction*/int visitShapeStart(Shape shape, StoryTypeStack storyTypeStack) throws Exception
    {
        if (shape.isOleControl())
            updateOleControlImage(shape);

        // WORDSNET-15796 Modify textbox, which was produced by a customer's code.
        fixTextBoxWithImageData(shape);
        // WORDSNET-12170 Update signature line image with proper signer name.
        SignatureLine signatureLine = shape.getSignatureLine();
        if (signatureLine != null)
        {
            // We treat generated with Guid.NewGuid() method GUIDs as unique. So, we should check uniqueness only those Ids, which were set by the user
            // through SignatureLine.Id property. Therefore, we do not need to add new generated id to hash of used ids and also check their uniqueness.
            if (mUsedSignatureLineIds.contains(signatureLine.getIdInternal()))
            {
                Guid oldId = signatureLine.getIdInternal();
                signatureLine.setIdInternal(Guid.newGuid());

                WarningUtil.warn(getWarningCallback(), WarningType.HINT, WarningSource.SHAPES, WarningStrings.NON_UNIQUE_SIGNATURE_LINE_ID,
                    oldId.toString("B").toUpperCase(), signatureLine.getIdInternal().toString("B").toUpperCase());
            }
            else
            {
                mUsedSignatureLineIds.add(signatureLine.getIdInternal());
            }

            if (!signatureLine.isSigned())
                signatureLine.updateShapeImage();
        }
        ShapeIdGenerator.validateFluently(shape);

        if (shape.getMarkupLanguage() == ShapeMarkupLanguage.DML)
            return visitDrawingMLStart(shape, storyTypeStack);

        validateEquationXML(shape);

        // WORDSNET-17909 If the shape is converted to office math then no further processing is required,
        // because the shape was removed in the "ValidateEquationXML" from the model.
        if (shape.getParentNode() == null)
            return VisitorAction.SKIP_THIS_NODE;

        // FOSS

        addParagraphIfTableIsLastChild(shape);
        visitShapeCore(shape, storyTypeStack);
        validateTextboxAnchor(shape);

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Returns <c>true</c> if the specified VML shape can be converted to DML depending on output format.
    /// </summary>
    private boolean canCovertToDml(Shape shape)
    {
        return
            shape.isTopLevel() &&
            (mSaveInfo.SaveOptions instanceof OoxmlSaveOptions) &&
            (mSaveInfo.getOoxmlCompliance() != OoxmlComplianceCore.ECMA_376);
    }

    /// <summary>
    /// Validates a shape and adds a warning if improper compatibility is set for a LayoutInCell property.
    /// This is needed for Dml shapes only.
    /// </summary>
    private void validateLayoutInCell(ShapeBase shape)
    {
        if (mSaveInfo.isDocxFormat() &&
            (mSaveInfo.Document.getCompatibilityOptions().getMswVersion() > MsWordVersionCore.WORD_2010) &&
            (shape.getMarkupLanguage() == ShapeMarkupLanguage.DML) &&
            isShapeInCell(shape) &&
            !shape.isLayoutInCell())
        {
            warn(WarningType.HINT, WarningSource.VALIDATOR,
                WarningStrings.INVALID_COMPATIBILITY_FOR_LAYOUT_IN_CELL);
        }
    }

    /// <summary>
    /// Updates presentation images for OLE controls.
    /// </summary>
    /// <remarks>
    /// So far just updates image for HTML OLE radio button controls using predefined images.
    /// </remarks>
    private static void updateOleControlImage(Shape shape) throws Exception
    {
        IEmbeddedObject embeddedObject = shape.getEmbeddedObject();
        // Filter out controls other than HtmlOptionControl.
        if ((embeddedObject == null) || (!OleControl.HTML_OPTION_CLSID.equals(embeddedObject.getClsidInternal())))
            return;

        HtmlOptionOleControl htmlOptionOleControl = ms.as(embeddedObject, HtmlOptionOleControl.class);
        if (htmlOptionOleControl == null)
        {
            OleObject oleObject = ms.as(embeddedObject, OleObject.class);
            if ((oleObject != null) && (oleObject.getData() != null))
                htmlOptionOleControl = (HtmlOptionOleControl)OleControl.create(oleObject.getData());
        }

        if (htmlOptionOleControl == null)
            return;

        byte[] imageBytes =
            OleUtil.getImageLibrary().get(htmlOptionOleControl.getChecked() ? "RadioChecked" : "RadioUnchecked");
        shape.getImageData().setImageBytes(imageBytes);
        shape.setWidth(20.25);
        shape.setHeight(18.0);
    }

    /// <summary>
    /// Tests if a shape is contained in a cell.
    /// </summary>
    private static boolean isShapeInCell(ShapeBase shape)
    {
        if (shape.getParentNode() != null)
        {
            Paragraph para = ms.as(shape.getFirstNonMarkupParentNode(), Paragraph.class);

            if ((para != null) && para.isInCell())
                return true;
        }

        return false;
    }

    /// <summary>
    /// Removes image from ImageData, and places it as a background image.
    /// </summary>
    private static void fixTextBoxWithImageData(Shape shape) throws Exception
    {
        if ((shape.getShapeType() == ShapeType.TEXT_BOX) && shape.getImageData().hasImageBytes() && (shape.getFill().getImageBytes() == null))
        {
            // Such shape is a result of customer's code. To prevent AW from creating output which is incorrect and crashes MSW2016,
            // We must remove image bytes from ImageData, and place them into FillImageBytes.
            shape.replaceVmlImageDataWithFill();
        }
    }

    /// <summary>
    /// Updates text box geometry path if it is needed.
    /// </summary>
    /// <param name="shape">Shape which holds text box.</param>
    private void updateTxbxPathRects(ShapeBase shape)
    {
        Debug.assertTrue(shape != null);

        if (shape.getShapeType() != ShapeType.CUSTOM_SHAPE)
            return;

        PathRectangle[] rects = (PathRectangle[])shape.fetchShapeAttrInternal(ShapeAttr.GEOMETRY_PATH_TEXT_BOX_RECTS);
        Document doc = ms.as(shape.getDocument(), Document.class);

        if ((rects != null) || (doc == null))
            return;

        if (FileFormatUtil.isXmlBasedFormat(doc.getOriginalLoadFormat()) && !isSaveFormatXml())
            shape.setShapeAttrInternal(ShapeAttr.GEOMETRY_PATH_TEXT_BOX_RECTS, ((Shape)shape).getTextBoxRects());
    }

    /// <summary>
    /// Updates start and end point of the line when it is necessary.
    /// </summary>
    /// <param name="shape">Shape to process.</param>
    private static void updateLineVertices(ShapeBase shape)
    {
        Debug.assertTrue(shape != null);

        if (shape.getShapeType() != ShapeType.LINE)
            return;

        double[] vertices = getLineVertices(shape, 0.0, 0.0, shape.getCoordSizeWidth(), shape.getCoordSizeHeight());

        if (vertices.length == 0)
            return;

        PathPoint startPoint = new PathPoint(MathUtil.doubleToInt(vertices[0]), MathUtil.doubleToInt(vertices[1]));
        PathPoint endPoint = new PathPoint(MathUtil.doubleToInt(vertices[2]), MathUtil.doubleToInt(vertices[3]));
        shape.setShapeAttrInternal(ShapeAttr.GEOMETRY_VERTICES, new PathPoint[] { startPoint, endPoint });
    }

    /// <summary>
    /// Calculates line vertices coordinates according to geometry data and specified
    /// shape size and positions.
    /// </summary>
    /// <param name="shape">Source shape.</param>
    /// <param name="t">Actual top position.</param>
    /// <param name="l">Actual left position.</param>
    /// <param name="w">Actual width.</param>
    /// <param name="h">Actual height.</param>
    /// <returns>Array with line vertices coordinates.</returns>
    private static double[] getLineVertices(ShapeBase shape, double t, double l, double w, double h)
    {
        Debug.assertTrue(shape != null);

        PathPoint[] vertices = (PathPoint[])shape.fetchShapeAttrInternal(ShapeAttr.GEOMETRY_VERTICES);

        // Expected two PathPoint to process.
        if ((vertices == null) || (vertices.length < 2))
            return new double[0];

        return new double[] { (vertices[0].getX().getValue() > 0) ? l + w : l,
            (vertices[0].getY().getValue() > 0) ? t + h : t,
            (vertices[1].getX().getValue() > 0) ? l + w : l,
            (vertices[1].getY().getValue() > 0) ? t + h : t};
    }

    private static void addParagraphIfTableIsLastChild(CompositeNode story)
    {
        Node lastNode = story.getLastNonAnnotationChild();
        if (lastNode != null)
        {
            if ((lastNode.getNodeType() != NodeType.PARAGRAPH) &&
                (lastNode.getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG))
            {
                story.appendChild(new Paragraph(story.getDocument()));
            }
        }
    }

    /*VisitorAction*/int visitShapeEnd(Shape shape)
    {
        if (shape.getMarkupLanguage() == ShapeMarkupLanguage.DML)
            return visitDrawingMLEnd(shape);
        else
            return VisitorAction.CONTINUE;
    }

    /*VisitorAction*/int visitGroupShapeStart(GroupShape groupShape, StoryTypeStack storyTypeStack) throws Exception
    {
        // andrnosk: WORDSNET-11960 Do not remove empty Dml group shapes.
        if (groupShape.getMarkupLanguage() == ShapeMarkupLanguage.DML)
            return visitDrawingMLStart(groupShape, storyTypeStack);

        // FOSS

        if (groupShape.hasNonMarkupDescendants())
        {
            visitShapeCore(groupShape, storyTypeStack);
            return VisitorAction.CONTINUE;
        }
        else
        {
            // WORDSNET-3882 Remove empty VML group shapes.
            groupShape.remove();
            return VisitorAction.SKIP_THIS_NODE;
        }
    }

    /*VisitorAction*/int visitGroupShapeEnd(GroupShape groupShape)
    {
        if (groupShape.getMarkupLanguage() == ShapeMarkupLanguage.DML)
            return visitDrawingMLEnd(groupShape);

        if (groupShape.hasNonMarkupDescendants())
        {
            return VisitorAction.CONTINUE;
        }
        else
        {
            // WORDSNET-3882 Remove empty VML group shapes.
            groupShape.remove();
            return VisitorAction.SKIP_THIS_NODE;
        }
    }

    /// <summary>
    /// Verifies the shape is allowed in the current story and updates the shape id to make it valid for MS Word.
    /// </summary>
    private void visitShapeCore(ShapeBase shape, StoryTypeStack storyTypeStack) throws Exception
    {
        shape.updateSizeAndPositionFromRelative();

        // WORDSNET-5000 Do not shrink custom shape.
        // WORDSNET-9471 Do not shrink group shape.
        if ((shape.getShapeType() != ShapeType.CUSTOM_SHAPE) && (shape.getNodeType() != NodeType.GROUP_SHAPE))
        {
            // Resiliency: Shape size is limited in MS Word.
            // DS: Looks like a shape size is limited only for updating and few rare cases on reading "old" formats.
            // Old Word versions may also open and re-save document with huge shape sizes.

            // WORDSNET-19128 In some cases, Word doesn't shrink shape.
            // WORDSNET-24026 The Word does not shrink shape to lower size exclude some cases for "old" formats.
            // Also see related tests: TestDefect2749, TestDefect2749.
            shape.validateZeroSizeShape();
        }

        // This is important, give the shape an identifier that is valid for MS Word.
        mShapeIdGenerator.visitShape(shape, storyTypeStack.isInHeaderOrFooter());

        mSaveInfo.addToZOrderList(shape, storyTypeStack);

        mShapeImageOptimizer.removeImageDuplication(shape);

        // Collect OLE objects for writing to DOC and WML.
        if (shape.isOle())
            processOleShape((Shape)shape);

        // Writing a custom shape to RTF without this attribute seems to produce incorrectly drawn shapes.
        // MS Word 2007 export to RTF itself has this problem. This code seems to solve the problem.
        if ((shape.getShapeType() == ShapeType.CUSTOM_SHAPE) &&
            (shape.getShapePr().getDirectAttr(ShapeAttr.GEOMETRY_SEGMENT_INFO) == null))
        {
            shape.getShapePr().setAttr(ShapeAttr.GEOMETRY_SEGMENT_INFO, new PathInfo[0]);
        }

        // dmatv: a fix for WORDSNET-7327 by Andrey Noskov was present here.
        // It did not cover all the cases so I updated the logic and moved it to AttributeConverter per WORDSNET-14632

        if (isSaveAction(DocumentValidatorActions.PREPARE_SHAPES_FOR_RENDERING))
        {
            validateColor(shape.getShapePr(), ShapeAttr.FILL_COLOR);
            validateColor(shape.getShapePr(), ShapeAttr.LINE_COLOR);
        }

        validateImageBytes(shape);

        // WORDSNET-14744 Set value for attribute "GeometryPathTextBoxRects" of the custom shape
        // when document is saved from XML-format to non-XML format. Applies this update only for VML shapes,
        // because MSW can not save the problematic shape which was previously converted to DML.
        updateTxbxPathRects(shape);

        updateLineVertices(shape);
        validateVerticalAlignment(shape);

        validateFloatingStory(shape, storyTypeStack.getCurrent());

        validateTextPlainTextDecoration(shape);
    }

    /// <summary>
    /// Validates vertical position for a shape.
    /// </summary>
    private void validateVerticalAlignment(ShapeBase shape)
    {
        if ((shape.getMarkupLanguage() == ShapeMarkupLanguage.DML) || !shape.isTopLevel())
            return;

        // Take in attention fixed formats to obtain valid output for WORDSNET-17541 issue.
        if (!isSaveFormatXml() && !isSaveAction(DocumentValidatorActions.PREPARE_SHAPES_FOR_RENDERING))
            return;

        // WORDSNET-17541 Word resets top position of the shape when it has "top" value of the relative vertical alignment.
        /*VerticalAlignment*/int vertAlignment = (/*VerticalAlignment*/Integer)shape.fetchShapeAttrInternal(ShapeAttr.VERTICAL_ALIGNMENT);
        if ((VerticalAlignment.TOP == vertAlignment) && !MathUtil.isZero(shape.getTop()))
            shape.setShapeAttrInternal(ShapeAttr.TOP, 0.0);
    }


    /// <summary>
    /// Validates shapes with TextBoxes.
    /// </summary>
    private void validateTextboxAnchor(ShapeBase shape)
    {
        if (shape.hasTextbox() && !getmHasNotDefaultBoxVerticalAnchor())
        {
            Shape castShape = ms.as(shape, Shape.class);
            setmHasNotDefaultBoxVerticalAnchor(getmHasNotDefaultBoxVerticalAnchor() | ((castShape != null) && (castShape.getTextBox().getVerticalAnchor() != TextBoxAnchor.TOP)));
        }
    }

    /// <summary>
    /// Validates shapes with "EquationXML".
    /// </summary>
    private void validateEquationXML(Shape shape)
    {
        // Word tries to convert shape with "EquationXML" into office math object, when destination format is DOCX or ODT.
        // Also OOXML version must be greater then "Ecma" for DOCX, otherwise the Word does not convert shapes
        // to math objects. Looks like Word does not take in attention compatibility options, while deciding to convert
        // shape to math object.
        if ((mSaveInfo.isDocxFormat() && mSaveInfo.getOoxmlCompliance() > OoxmlComplianceCore.ECMA_376) ||
            (mSaveInfo.getSaveFormat() == SaveFormat.ODT) ||
            (mSaveInfo.getSaveFormat() == SaveFormat.OTT))
        {
            OfficeMathUtil.convertShapeToOfficeMath(shape, getWarningCallback());
            return;
        }

        byte[] equationXmlVal = OfficeMathUtil.retrieveEquationXML(shape);

        // There is some restrictions for "EquationXML", when output document in the DOC format.
        if (!mSaveInfo.isDocFormat() || (equationXmlVal == null) || (equationXmlVal.length == 0))
            return;

        // "EquationXML" has to be terminated with "0x00" byte, while saving to DOC format.
        // Otherwise Word raises errors while re-saving produced document to DOCX (see TestJira16540DocIssue).
        if (equationXmlVal[equationXmlVal.length - 1] != 0x00)
            shape.getShapePr().set(ShapeAttr.EQUATION_XML, ArrayUtil.resizeArray(equationXmlVal, equationXmlVal.length + 1));
    }

    /// <summary>
    /// andrnosk: WORDSNET-8761, WORDSNET-11741 It seems special color 'window' it is simple DrColor.White upon rendering.
    /// Previously, we wrote DrColor(0xef, 0x11, 0, 0) like MSWord shows in UI toolbar (but not real shape color).
    /// Also MSWord automation returns white color for this shape too.
    /// </summary>
    private static void validateColor(ShapePr shapePr, int shapeAttr)
    {
        // 'window' color.
        DrColor wColor = DrColor.fromArgb(239, 17, 0, 0);
        if (shapePr.contains(shapeAttr) && (DrColor.equals((DrColor)shapePr.get(shapeAttr), wColor)))
            shapePr.setAttr(shapeAttr, DrColor.WHITE);
    }

    /// <summary>
    /// Make sure that we write to the document only fully supported image formats.
    /// We need to check image bytes, fill image bytes and line image bytes.
    /// </summary>
    private void validateImageBytes(ShapeBase shape) throws Exception
    {
        if (shape.getNodeType() == NodeType.SHAPE)
        {
            Shape imageShape = (Shape)shape;

            for (int key : new int[] { ShapeAttr.FILL_IMAGE_BYTES, ShapeAttr.LINE_IMAGE_BYTES })
            {
                byte[] imageBytes = (byte[])shape.getDirectShapeAttrInternal(key);
                if (imageBytes != null)
                    shape.setShapeAttrInternal(key, ImageDataCore.getImageBytes(imageBytes));
            }

            if (imageShape.hasImageBytes())
            {
                byte[] imageBytes = ImageDataCore.getImageBytes(imageShape.getImageData().getImageBytes());
                imageShape.getImageData().setImageBytes(imageBytes);

                // WORDSNET-12169 Create new picture fill using ImageData.ImageBytes if shape with specified type
                // is supposed to be filled with image. Use this mechanism only upon rendering.
                if (isSaveAction(DocumentValidatorActions.PREPARE_SHAPES_FOR_RENDERING))
                {
                    // Although MS Word has a special shape type for images, it appears that in MS Word documents any single
                    // shape can have an image, but some of them is supposed to be filled with image.
                    if (imageShape.canHaveFillImage() && (imageShape.getShapeType() != ShapeType.RECTANGLE))
                    {
                        imageShape.replaceVmlImageDataWithFill();
                    }
                }
            }
        }
    }

    /// <summary>
    /// Validates relative shape positions.
    /// </summary>
    /// <remarks>
    /// Word behavior depends on compatibility mode setting.
    /// </remarks>
    private void validateRelativePositions(ShapeBase shape)
    {
        Section section = (Section)shape.getAncestor(NodeType.SECTION);
        if (section == null)
            return;

        CustomCompatibilitySetting compatibilityMode =
            mSaveInfo.Document.getCompatibilityOptions().getCustomCompatibilitySettings().get("compatibilityMode");

        // Word converts relative position 'OutsideMargin' to 'Page' when compatibility is '11' (Word2003).
        // Actually, Word do this for vertical position as well and also converts shape to VML in this case.
        // But it is enough to convert only horizontal position for a while and leave shape as DML.
        if ((compatibilityMode != null) && ("11".equals(compatibilityMode.getValue())) &&
            (shape.getRelativeHorizontalPosition() == RelativeHorizontalPosition.OUTSIDE_MARGIN))
        {
            shape.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
            shape.setLeft(shape.getLeft() + ConvertUtilCore.twipToPoint((section.getSectPr().getPageWidth() - section.getSectPr().getRightMargin())));
        }
    }

    /// <summary>
    /// Prepares OLE data to be written.
    /// </summary>
    private void processOleShape(Shape oleShape) throws Exception
    {
        if (mSaveInfo.SaveOptions.isTestMode())
            updateOcxName(oleShape);

        // For speed reason do following conversion only for "old" document formats.
        if (!mSaveInfo.isLegacyFormat())
            return;

        IEmbeddedObject embeddedObject = oleShape.getEmbeddedObject();
        if (embeddedObject == null)
            return;

        String progId = oleShape.getOleFormat().getProgId();
        Guid clsid = new Guid(OleRegistryInfo.getClsId(progId));
        if (Guid.equals(clsid, Guid.Empty))
            warn(WarningType.UNEXPECTED_CONTENT, WarningSource.SHAPES, msString.format(WarningStrings.UNKNOWN_PROG_ID, progId));

        MemoryStorage oleData = null;

        OoxmlObject ooxmlObject = ms.as(embeddedObject, OoxmlObject.class);
        if (ooxmlObject != null)
        {
            if (oleShape.isOoxmlControl())
            {
                warn(WarningType.DATA_LOSS, WarningSource.DOC, WarningStrings.OOXML_CONTROL_NOT_SUPPORTED);
            }
            else
            {
                // Wrap OOXML package to OLE object (note that original OOXML embed left unchanged):
                oleData = OleUtil.wrapOoxmlPackage(oleShape.getOleFormat().getProgId(), ooxmlObject, oleShape.getOleFormat().getOleIcon());
            }
        }
        else
        {
            OleObject oleObject = embeddedObject.getOleObject();
            if (oleObject != null)
            {
                oleData = oleObject.getData();

                // If original document is DOCX the oleObject might miss Clsid but this is essential to be written to DOC/RTF/WML
                // otherwise Word shows error when object is double-clicked.
                if (Guid.equals(oleData.getClsid(), Guid.Empty))
                    oleData.setClsid(clsid);
            }
        }

        if (oleData != null)
        {
            mSaveInfo.AllOleObjects.set(OleUtil.getMsWordId(embeddedObject), oleData);

            switch (oleShape.getOleObjectType())
            {
                case OleObjectType.CONTROL:
                    mSaveInfo.HasOleControls = true;
                    break;
                case OleObjectType.EMBEDDED:
                    mSaveInfo.EmbeddedOleObjects.set(OleUtil.getMsWordId(embeddedObject), oleData);
                    break;
                case OleObjectType.LINKED:
                    // Do nothing.
                    break;
                default:
                    throw new IllegalStateException("Unexpected OLE object type.");
            }
        }
    }

    /// <summary>
    /// Returns <c>true</c> if the specified shape is a chart in ODT format.
    /// </summary>
    private static boolean isOdtChart(Shape shape)
    {
        return shape.isOle() && ("opendocument.ChartDocument.1".equals(shape.getOleFormat().getProgId()));
    }

    /// <summary>
    /// Updates OCXNAME stream with control name.
    /// </summary>
    /// <remarks>
    /// AM. In TestDefect1117.doc I found that Word doesn't update OCXNAME i.e it does not shrink stream length if new name is shorter
    /// that previous so OCXNAME stream contains valid data at start of stream and some "garbage" at end of stream.
    /// This fails our testing because Doc2Docx conversion updates OCXNAME stream and Docx2Docx does not.
    /// So for testing purpose I update this stream always.
    /// </remarks>
    private static void updateOcxName(Shape oleShape) throws Exception
    {
        if (oleShape.isOleControl() && (oleShape.getOleFormat().getOleObject() != null))
        {
            MemoryStorage oleData = oleShape.getOleFormat().getOleObject().getData();

            // Update OCXNAMESTREAM.
            OcxNameStream oldOcxNameStream = OcxNameStream.read(oleData);

            if (oldOcxNameStream != null)
            {
                OcxNameStream newOcxNameStream = new OcxNameStream(oldOcxNameStream.getValue());
                newOcxNameStream.write(oleData);
            }
        }
    }

    private void validateFloatingStory(ShapeBase shape, /*StoryType*/int currentStoryType)
    {
        if (shape.getWrapType() == WrapType.INLINE)
            return;

        // Allow or disallow a shape in a particular story.
        switch (currentStoryType)
        {
            case StoryType.TEXTBOX:
            case StoryType.COMMENTS:
            case StoryType.ENDNOTES:
            case StoryType.FOOTNOTES:
            {
                // It looks like only inline pictures, ole objects and word art are allowed in these stories.
                // But I let all shape types, just force them to become inline.
                warn(WarningType.HINT, WarningSource.VALIDATOR, WarningStrings.UNEXPECTED_STORY_FOR_SHAPE);

                boolean anchoredToPage =
                    (shape.getRelativeHorizontalPosition() == RelativeHorizontalPosition.PAGE) ||
                    (shape.getRelativeVerticalPosition() == RelativeVerticalPosition.PAGE);
                if ((shape.getMarkupLanguage() == ShapeMarkupLanguage.VML) && anchoredToPage)
                {
                    // WORDSNET-20143 Move the floating shape to the end of the body if it's anchored to a page.
                    Document doc = ms.as(shape.getDocument(), Document.class);
                    CompositeNode paragraph = doc.getLastSection().getBody().getLastParagraph();
                    if (paragraph != null)
                        paragraph.appendChild(shape);
                }
                else
                {
                    shape.setWrapType(WrapType.INLINE);
                }

                break;
            }
            default:
                // Do nothing.
                break;
        }
    }

    private void validateTextPlainTextDecoration(ShapeBase shape)
    {
        if ((shape.getShapeType() == ShapeType.TEXT_PLAIN_TEXT) &&
            (shape.getMarkupLanguage() == ShapeMarkupLanguage.VML) &&
            (shape.getShapePr().get(ShapeAttr.GEO_TEXT_UNDERLINE) != null))
            warn(WarningType.MAJOR_FORMATTING_LOSS, WarningSource.SHAPES, WarningStrings.TEXT_PLAIN_TEXT_DECORATION_IN_VML);
    }

    private /*VisitorAction*/int visitDrawingMLStart(ShapeBase drawingML, StoryTypeStack storyTypeStack) throws Exception
    {
        mBookmarkValidator.visitDrawingMLStart(drawingML);

        validateFloatingStory(drawingML, storyTypeStack.getCurrent());

        // WORDSNET-26420 Validate relative positions.
        validateRelativePositions(drawingML);

        // Need to update size and position of drawingML.
        drawingML.updateSizeAndPositionFromRelative();

        // WORDSNET-9493 Make size of DrawingML specified on top level and in Dml node the same.
        updateDmlNodeTransform(drawingML);

        mDrawingMLIdValidator.visitDrawingML(drawingML);

        // Validate only top-level drawingMLs.
        if (!drawingML.isTopLevel())
            return VisitorAction.CONTINUE;

        // WORDSNET-23287 After clone/import, the FallBack node still references the
        // original document. Pin it to the current document's NullNode so its Document
        // accessor returns the right doc.
        drawingML.updateFallBackParent();

        // WORDSNET-12320 When saving to OOXML, a DML shape that uses wp14 drawing
        // extensions (RelativeWidth / RelativeHeight) requires the document to be at
        // least IsoTransitional so the writer emits the wp14 namespace.
        if (mSaveInfo.isOoxmlFormat() && drawingML.hasDrawingExtensions())
            OoxmlComplianceInfo.markAsHasDrawingExtensions(mSaveInfo.Document);

        // FOSS Mirror of master's DmlShapeValidator.ProcessFallback ->
        // ReplaceDmlWithFallBackIfNeeded path. Pure-model operations only:
        // - For Ecma376_2006 (no mc:AlternateContent in that spec): replace the DML
        //   with the loaded VML fallback if one exists. Without a loaded fallback we
        //   leave the DML in place (master would generate one via rendering, which
        //   FOSS cannot do).
        // - For IsoTransitional / IsoStrict: visit the fallback so DrawingMLIdValidator
        //   registers its ids and gives it a unique nvPr.Id distinct from the choice's.
        if (drawingML.getFallbackShape() != null && mSaveInfo.isOoxmlFormat())
        {
            if (mSaveInfo.getOoxmlCompliance() == OoxmlComplianceCore.ECMA_376)
            {
                com.aspose.words.DmlUtil.replaceDmlWithFallBack(drawingML, mValidator);
            }
            else
            {
                drawingML.getFallbackShape().accept(mValidator);
            }
        }

        // Return if drawingML was removed from document.
        if (drawingML.getParentNode() == null)
            return VisitorAction.SKIP_THIS_NODE;

        mSaveInfo.addToZOrderList(drawingML, storyTypeStack);

        validateLayoutInCell(drawingML);

        return VisitorAction.CONTINUE;
    }

    private /*VisitorAction*/int visitDrawingMLEnd(ShapeBase drawingML)
    {
        mBookmarkValidator.visitDrawingMLEnd(drawingML);
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Makes sure size of DrawingML node set in its xfrm.ext is the same as specified in DrawingML extent.
    /// </summary>
    private static void updateDmlNodeTransform(ShapeBase dml)
    {
        // Update size of drawingMl only if it has textbox content.
        // Other drawings are properly scaled using DmlToApsConverterResult.ScaleToSize.
        if (!dml.hasTextbox() || !dml.isTopLevel())
            return;

        // For top level shapes Width is in points, for child shapes Width is in the parent coordinate system.
        double widthEmu = ConvertUtilCore.pointToEmu(dml.getWidth());
        double heightEmu = ConvertUtilCore.pointToEmu(dml.getHeight());

        DmlTransform transform = dml.getDmlNode().getTransform();

        if (!MathUtil.isZero(widthEmu))
            transform.setWidth(widthEmu);

        if (!MathUtil.isZero(heightEmu))
            transform.setHeight(heightEmu);
    }

    /// <summary>
    /// Validates linked textboxes chains.
    /// </summary>
    private void validateLinkedTextboxes()
    {
        HashMap<String, ShapeBase> shapeNames = new HashMap<String, ShapeBase>();

        Iterator<Integer> enumerator = mSaveInfo.LinkedShapeIds.iterator();
        while (enumerator.hasNext())
        {
            ShapeBase shape = getShape(enumerator.next());

            // Check that there is no conflicting name in linked textboxes.
            if (StringUtil.hasChars(shape.getName()))
            {
                if (shapeNames.containsKey(shape.getName()))
                {
                    // Textboxes must be written linked by ShapeId.
                    mSaveInfo.LinkedShapeNameConflict = true;
                }
                else
                {
                    shapeNames.put(shape.getName(), shape);
                }
            }

            // First textbox in chain must contain paragraph.
            if (isFirstInChain(shape))
            {
                if (shape.getChildNodes(NodeType.ANY, false).getCount() == 0)
                    shape.appendChildForLoad(new Paragraph(mSaveInfo.Document));

                // AM. Word moves any content of linked textboxes into first textbox but it's not just add content.
                // Instead it moves content into last composite node i.e paragraph in this case. We can be sure that at least
                // one paragraph exists in first textbox after above validation.
                ShapeBase nextShape = getNextShape(shape);
                while (nextShape != null)
                {
                    if (nextShape.getChildNodes(NodeType.ANY, false).getCount() > 0)
                        moveNodes(nextShape, getLastCompositeChildren(shape));

                    nextShape = getNextShape(nextShape);
                }
            }
        }

        // Check if vertical anchor is lost on saving.
        if (getmHasNotDefaultBoxVerticalAnchor())
        {
            switch (mSaveInfo.getSaveFormat())
            {
                case SaveFormat.DOC:
                case SaveFormat.DOT:
                    warn(WarningType.DATA_LOSS, WarningSource.SHAPES, WarningStrings.TEXTBOX_VERTICAL_ANCHOR_2007_ONLY);
                    break;
                case SaveFormat.WORD_ML:
                    warn(WarningType.DATA_LOSS, WarningSource.SHAPES,
                         msString.format(WarningStrings.TEXTBOX_VERTICAL_ANCHOR_NOT_SUPPORTED));
                    break;
                default:
                    // Textbox vertical alignment will be saved as expected.
                    // Currently AW can't save textbox vertical alignment for RTF and ODT formats.
                    break;
            }
        }
    }

    /// <summary>
    /// Returns shape with given shapeId or null if not found.
    /// </summary>
    private ShapeBase getShape(int shapeId)
    {
        return mSaveInfo.Shapes.get(shapeId);
    }

    /// <summary>
    /// Returns next linked shape.
    /// </summary>
    private ShapeBase getNextShape(ShapeBase shape)
    {
        return getShape(shape.getTextboxNextShapeId());
    }

    /// <summary>
    /// Move nodes from one composite node to another composite node. Removes source composite node.
    /// </summary>
    private static void moveNodes(CompositeNode srcNode, CompositeNode dstNode)
    {
        // Lock node collection.
        Node[] childNodes = srcNode.getChildNodes(NodeType.ANY, false).toArray();

        for (Node node : childNodes)
        {
            if (node.isComposite() && !dstNode.canInsert(node))
            {
                moveNodes((CompositeNode)node, dstNode);
                node.remove();
            }
            else
            {
                // AM. Some nodes can be lost here.
                // Will look how Word moves nodes if such situation is occurred.
                if (dstNode.canInsert(node))
                    dstNode.appendChild(node);
            }
        }
    }

    /// <summary>
    /// Returns last composite child node.
    /// </summary>
    private static CompositeNode getLastCompositeChildren(CompositeNode node)
    {
        Node lastCompositeChildNode = null;

        for (Node childNode : (Iterable<Node>) node.getChildNodes(NodeType.ANY, false))
        {
            if (childNode.isComposite())
                lastCompositeChildNode = childNode;
        }

        return (CompositeNode)lastCompositeChildNode;
    }

    /// <summary>
    /// Checks whether given Shape is first shape in linked textboxes chain.
    /// </summary>
    /// <remarks>
    /// AM. Think that count of linked textboxes is small so do it in straight way.
    /// </remarks>
    private boolean isFirstInChain(ShapeBase shape)
    {
        Iterator<Integer> enumerator = mSaveInfo.LinkedShapeIds.iterator();
        while (enumerator.hasNext())
        {
            if (getShape(enumerator.next()).getTextboxNextShapeId() == shape.getId())
                return false;
        }

        return true;
    }

    /// <summary>
    /// Returns true if all bits of the specified save actions are set in the current save operation.
    /// </summary>
    private boolean isSaveAction(/*DocumentValidatorActions*/int actions)
    {
        return ((mSaveActions & actions) == actions);
    }

    void finish()
    {
        mShapeIdGenerator.endDocument();

        // This should be executed after mShapeIdGenerator finished.
        validateLinkedTextboxes();
    }

    void revert()
    {
        // FOSS
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    private void warn(/*WarningType*/int type, /*WarningSource*/int source, String description)
    {
        if (getWarningCallback() != null)
            getWarningCallback().warning(new WarningInfo(type, source, description));
    }

    private IWarningCallback getWarningCallback() { return mSaveInfo.Document.getWarningCallback(); }

    /// <summary>
    /// Returns "true" when destination document stores as XML.
    /// </summary>
    private boolean isSaveFormatXml() { return mSaveInfo.isOoxmlFormat() || (mSaveInfo.getSaveFormat() == SaveFormat.WORD_ML); }

    /// <summary>
    /// Returns "true" when any shape has not default vertical alignment and can be lost on saving.
    /// </summary>
    private boolean getmHasNotDefaultBoxVerticalAnchor() { return mmHasNotDefaultBoxVerticalAnchor; }; private void setmHasNotDefaultBoxVerticalAnchor(boolean value) { mmHasNotDefaultBoxVerticalAnchor = value; };

    private boolean mmHasNotDefaultBoxVerticalAnchor;

    private /*final*/ SaveInfo mSaveInfo;
    private /*final*/ /*DocumentValidatorActions*/int mSaveActions;
    private /*final*/ DocumentVisitor mValidator;
    private BookmarkValidator mBookmarkValidator;

    private /*final*/ ShapeIdGenerator mShapeIdGenerator;
    private /*final*/ ShapeImageOptimizer mShapeImageOptimizer;
    private DrawingMLIdValidator mDrawingMLIdValidator;

    private /*final*/ HashSetGeneric<Guid> mUsedSignatureLineIds = new HashSetGeneric<Guid>();
}

