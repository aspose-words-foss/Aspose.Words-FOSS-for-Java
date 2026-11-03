// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/01/2012 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Drawing.msSize;
import com.aspose.ms.System.Drawing.msPoint;
import java.awt.Color;
import com.aspose.ms.System.Drawing.msColor;
import com.aspose.StreamUtil;
import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.StringUtil;
import com.aspose.MathUtil;
import java.util.HashMap;
import com.aspose.ConvertUtilCore;
import org.testng.annotations.DataProvider;



/// <summary>
/// Basic DrawingML functionality tests.
/// </summary>
@Test
public class TestDrawingML
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @BeforeMethod (alwaysRun = true)
    public void init()
    {
        RandomUtil.reset();
    }

    @Test
    public void testDmlPictureAlternativeText() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestDmlPictureAlternativeText.docx");
        Shape node = (Shape)doc.selectSingleNode("//Shape[1]");
        msAssert.areEqual("ALTERNATIVE TEXT", node.getAlternativeText());
        node.setAlternativeText("ALTERNATIVE TEXT CHANGED");

        doc = TestUtil.saveOpen(doc, "Model\\DrawingML\\TestDmlPictureAlternativeText.docx");
        node = (Shape)doc.selectSingleNode("//Shape[1]");
        msAssert.areEqual("ALTERNATIVE TEXT CHANGED", node.getAlternativeText());
    }





    /// <summary>
    /// WORDSNET-6958 Make sure charts are rendered upon converting to flow formats.
    /// </summary>
    // FOSS: TestJira6958 removed — it verified DrawingML image export to Doc/WordML/Rtf/Dot, all removed.




    /// <summary>
    /// WORDSNET-5608 DrawingML does not get rendered to flow formats other than DOCX.
    /// The problem occurred because DocumentValidator skipped rendering lockedCanvas DrawingMLs.
    /// Fixed by adding the appropriate condition.
    /// </summary>
    // FOSS: TestJira5083 removed — it verified DrawingML renders to non-DOCX flow formats (Doc/WordML/Rtf/Dot), all removed.


    /// <summary>
    /// WORDSNET-9666 Linked image does not update in output documents.
    /// The problem occurs upon converting DrawingML to Shape.
    /// Fixed by checking if shape has both embedded and linked images set, we have to try loading linked image first.
    /// </summary>
    // FOSS: TestJira9666 removed — it verified linked-image update on DrawingML export to WordML/Rtf, both removed.

    /// <summary>
    /// WORDSNET-8911Implement interface IShapeAttrSource in DrawingML.
    /// Test FetchInheritedShapeAttr method works correctly for DML/VML shapes.
    /// </summary>
    @Test
    public void testJira8911() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestJira8911.docx");

        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        Shape drawingMl = (Shape)doc.getChild(NodeType.SHAPE, 1, true);

        msAssert.areEqual(shape.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_DASH_STYLE), drawingMl.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_DASH_STYLE));
        msAssert.areEqual(shape.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_DASH_DATA), drawingMl.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_DASH_DATA));
        msAssert.areEqual(shape.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_STYLE), drawingMl.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_STYLE));
        msAssert.areEqual(shape.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_END_CAP_STYLE), drawingMl.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_END_CAP_STYLE));
        msAssert.areEqual(shape.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_START_ARROW), drawingMl.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_START_ARROW));
        msAssert.areEqual(shape.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_START_ARROW_LENGTH), drawingMl.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_START_ARROW_LENGTH));
        msAssert.areEqual(shape.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_START_ARROW_WIDTH), drawingMl.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_START_ARROW_WIDTH));
        msAssert.areEqual(shape.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_END_ARROW), drawingMl.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_END_ARROW));
        msAssert.areEqual(shape.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_END_ARROW_LENGTH), drawingMl.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_END_ARROW_LENGTH));
        msAssert.areEqual(shape.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_END_ARROW_WIDTH), drawingMl.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_END_ARROW_WIDTH));
        msAssert.areEqual(shape.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_INSET_PEN), drawingMl.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_INSET_PEN));

        // It seems default LineWidth is different for DML and VML shapes.
        // 9525EMUs = 0.75pt, 12700Emus = 1pt.
        msAssert.areEqual(9525, shape.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_WIDTH));
        msAssert.areEqual(9525, drawingMl.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_WIDTH));

        // LineWidth is specified directly inside shape that is why use FetchShapeAttr.
        msAssert.areEqual(shape.fetchShapeAttrInternal(ShapeAttr.LINE_WIDTH), drawingMl.fetchInheritedShapeAttrInternal(ShapeAttr.LINE_WIDTH));
    }




    @Test
    public void testDmlShapeType() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestDmlShapeType.docx");
        Node[] shapes = doc.getChildNodes(NodeType.SHAPE, true).toArray();

        for (Node shapeNode : shapes)
        {
            // JAVA-added explicit casting to Shape.
            Shape shape = (Shape)shapeNode;

            if (shape.getFallbackShape() != null)
            {
                if (DmlEnum.hasVmlPresetGeometry(shape.getShapeType()))
                    msAssert.areEqual(shape.getFallbackShape().getShapeType(), shape.getShapeType());

                msAssert.isFalse(shape.isImage());
            }
            else
            {
                msAssert.areEqual(ShapeType.IMAGE, shape.getShapeType());
                msAssert.isTrue(shape.isImage());
            }

            msAssert.isFalse(shape.isHorizontalRule());
            msAssert.isFalse(shape.isHorizontalRule());
            msAssert.isTrue(shape.isTopLevel());
        }
    }



    @Test
    public void testDmlIsGroup() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestDmlIsGroup.docx");

        Node[] groupShapes = doc.getChildNodes(NodeType.GROUP_SHAPE, true).toArray();

        msAssert.areEqual(3, groupShapes.length);

        GroupShape wpGroupShape = (GroupShape)groupShapes[0];
        msAssert.areEqual(ShapeType.GROUP, wpGroupShape.getShapeType());
        msAssert.isTrue(wpGroupShape.isGroup());
        msAssert.isTrue(wpGroupShape.isTopLevel());
        msAssert.areEqual(NodeType.GROUP_SHAPE, wpGroupShape.getNodeType());
        msAssert.areEqual(DmlNodeType.WORDPROCESSING_GROUP_SHAPE, wpGroupShape.getDmlNode().getDmlNodeType());

        wpGroupShape = (GroupShape)groupShapes[1];
        msAssert.areEqual(ShapeType.GROUP, wpGroupShape.getShapeType());
        msAssert.isTrue(wpGroupShape.isTopLevel());
        msAssert.isTrue(wpGroupShape.isGroup());
        msAssert.areEqual(NodeType.GROUP_SHAPE, wpGroupShape.getNodeType());
        msAssert.areEqual(DmlNodeType.WORDPROCESSING_CANVAS, wpGroupShape.getDmlNode().getDmlNodeType());

        Node[] shapes = doc.getChildNodes(NodeType.SHAPE, true).toArray();
        msAssert.areEqual(6, shapes.length);

        Shape shape = (Shape)shapes[0];
        msAssert.areEqual(ShapeType.TEXT_BOX, shape.getShapeType());

        //This shape is inside group.
        msAssert.isFalse(shape.isTopLevel());

        msAssert.isFalse(shape.isGroup());
        msAssert.areEqual(NodeType.SHAPE, shape.getNodeType());
        msAssert.areEqual(DmlNodeType.WORDPROCESSING_SHAPE, shape.getDmlNode().getDmlNodeType());

        shapes = doc.getChildNodes(NodeType.SHAPE, false).toArray();
        msAssert.areEqual(0, shapes.length);
    }

    @Test
    public void testDmlCoordSize() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestDmlIsGroup.docx");

        GroupShape groupShape = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 0, true);

        msAssert.areEqual(ShapeType.GROUP, groupShape.getShapeType());
        msAssert.areEqual(132, 0.1, groupShape.getWidth());
        msAssert.areEqual(63, 0.1, groupShape.getHeight());

        msAssert.areEqual(1152525, groupShape.getCoordSizeWidth());
        msAssert.areEqual(581025, groupShape.getCoordSizeHeight());

        // Set double CoordSize.
        groupShape.setCoordSizeInternal(msSize.ctor(groupShape.getCoordSizeWidth() * 2, groupShape.getCoordSizeHeight() * 2));

        doc = TestUtil.saveOpen(doc, "Model\\DrawingML\\TestDmlCoordSize.docx");

        groupShape = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 0, true);

        msAssert.areEqual(132, 0.1, groupShape.getWidth());
        msAssert.areEqual(63, 0.1, groupShape.getHeight());

        msAssert.areEqual(2305050, groupShape.getCoordSizeWidth());
        msAssert.areEqual(1162050, groupShape.getCoordSizeHeight());
    }

    @Test
    public void testDmlCoordOrigin() throws Exception
    {
        final String FILE_NAME = "Model\\DrawingML\\TestDmlIsGroup.docx";
        Document doc = TestUtil.open(FILE_NAME);

        GroupShape groupShape = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 0, true);

        msAssert.areEqual(ShapeType.GROUP, groupShape.getShapeType());
        msAssert.areEqual(132, 0.1, groupShape.getWidth());
        msAssert.areEqual(63, 0.1, groupShape.getHeight());

        msAssert.areEqual(0, msPoint.getX(groupShape.getCoordOriginInternal()));
        msAssert.areEqual(0, msPoint.getY(groupShape.getCoordOriginInternal()));

        // Set half CoordSize as CoordOrigin.
        groupShape.setCoordOriginInternal(msPoint.ctor(groupShape.getCoordSizeWidth() / 2, groupShape.getCoordSizeHeight() / 2));

        doc = TestUtil.saveOpen(doc, "Model\\DrawingML\\TestDmlCoordOrigin.docx");

        groupShape = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 0, true);

        msAssert.areEqual(132, 0.1, groupShape.getWidth());
        msAssert.areEqual(63, 0.1, groupShape.getHeight());

        msAssert.areEqual(groupShape.getCoordSizeWidth() / 2, groupShape.getCoordOriginX());
        msAssert.areEqual(groupShape.getCoordSizeHeight() / 2, groupShape.getCoordOriginY());
    }

    @Test
    public void testDmlFlipOrientation() throws Exception
    {
        final String FILE_NAME = "Model\\DrawingML\\TestDmlFlipOrientation.docx";
        Document doc = TestUtil.open(FILE_NAME);

        Node[] shapes = doc.getChildNodes(NodeType.SHAPE, true).toArray();
        ((Shape)shapes[0]).setFlipOrientation(FlipOrientation.VERTICAL);
        ((Shape)shapes[1]).setFlipOrientation(FlipOrientation.BOTH);
        ((Shape)shapes[2]).setFlipOrientation(FlipOrientation.VERTICAL);
        ((Shape)shapes[4]).setFlipOrientation(FlipOrientation.HORIZONTAL);

        doc = TestUtil.saveOpen(doc, "Model\\DrawingML\\TestDmlFlipOrientation.docx");

        shapes = doc.getChildNodes(NodeType.SHAPE, true).toArray();
        msAssert.areEqual(FlipOrientation.VERTICAL, ((Shape)shapes[0]).getFlipOrientation());
        msAssert.areEqual(FlipOrientation.BOTH, ((Shape)shapes[1]).getFlipOrientation());
        msAssert.areEqual(FlipOrientation.VERTICAL, ((Shape)shapes[2]).getFlipOrientation());
        msAssert.areEqual(FlipOrientation.NONE, ((Shape)shapes[3]).getFlipOrientation());
        msAssert.areEqual(FlipOrientation.HORIZONTAL, ((Shape)shapes[4]).getFlipOrientation());
    }

    /// <summary>
    /// Test how Rotation works for Dml shape.
    /// </summary>
    @Test
    public void testDmlRotation() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestDmlFlipOrientation.docx");

        Node[] shapes = doc.getChildNodes(NodeType.SHAPE, true).toArray();

        ((Shape)shapes[0]).setRotation(30.0);
        ((Shape)shapes[1]).setRotation(50.0);
        ((Shape)shapes[2]).setRotation(70.0);
        ((Shape)shapes[4]).setRotation(90.0);

        doc = TestUtil.saveOpen(doc, "Model\\DrawingML\\TestDmlRotation.docx");

        shapes = doc.getChildNodes(NodeType.SHAPE, true).toArray();
        msAssert.areEqual(30, ((Shape)shapes[0]).getRotation());
        msAssert.areEqual(50, ((Shape)shapes[1]).getRotation());
        msAssert.areEqual(70, ((Shape)shapes[2]).getRotation());
        msAssert.areEqual(0, ((Shape)shapes[3]).getRotation());
        msAssert.areEqual(90, ((Shape)shapes[4]).getRotation());
    }

    /// <summary>
    /// Test ShadowEnabled and ExtrusionEnabled properties.
    /// </summary>
    @Test
    public void testDmlShadow() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestDmlShadow.docx");
        Node[] shapes = doc.getChildNodes(NodeType.SHAPE, true).toArray();

        msAssert.isFalse(((Shape)shapes[0]).getShadowEnabled());
        msAssert.isTrue(((Shape)shapes[0]).getExtrusionEnabled());

        msAssert.isFalse(((Shape)shapes[1]).getExtrusionEnabled());
        msAssert.isFalse(((Shape)shapes[1]).getShadowEnabled());

        msAssert.isTrue(((Shape)shapes[2]).getExtrusionEnabled());

        msAssert.isTrue(((Shape)shapes[3]).getExtrusionEnabled());

        msAssert.isTrue(((Shape)shapes[4]).getExtrusionEnabled());
        msAssert.isTrue(((Shape)shapes[4]).getShadowEnabled());
    }

    /// <summary>
    /// WORDSNET-10837 Test Dml shape fill Color, Opacity and On properties.
    /// </summary>
    @Test
    public void testDmlShapeFillColor() throws Exception
    {
        final String PATH_NAME = "Model\\DrawingML\\TestDmlShapeFillColor.docx";
        Document sourceDoc = TestUtil.open(PATH_NAME);
        Shape dmlShape = (Shape)sourceDoc.getChild(NodeType.SHAPE, 0, true);
        Fill fill = dmlShape.getFill();

        msAssert.isTrue(fill.getVisible());
        msAssert.areEqual(FillType.SOLID, fill.getFillType());

        int expected = /*unchecked*/((int)(int) 0x8000b050L);
        msAssert.areEqual(expected, fill.getColor().getRGB());
        msAssert.areEqual(expected, dmlShape.getFillColor().getRGB());

        msAssert.areEqual(0.5, 0.01, fill.getOpacity());

        // Set other solid color.
        fill.setForeColor(Color.RED);

        msAssert.areEqual(Color.RED.getRGB(), fill.getForeColor().getRGB());
        msAssert.areEqual(1.0, 0.01, fill.getOpacity());

        // Set other opacity.
        fill.setOpacity(0.7);

        msAssert.areEqual(0.7, 0.01, fill.getOpacity());
        msAssert.isTrue(fill.getVisible());
        msAssert.isTrue(dmlShape.getFilled());

        Document resultDoc = TestUtil.saveOpen(sourceDoc, PATH_NAME);
        dmlShape = (Shape)resultDoc.getChild(NodeType.SHAPE, 0, true);

        msAssert.isTrue(dmlShape.getFilled());
        expected = /*unchecked*/((int)(int) 0xb2ff0000L);
        msAssert.areEqual(expected, dmlShape.getFillColor().getRGB());
        msAssert.areEqual(0.7, 0.01, fill.getOpacity());
    }

    /// <summary>
    /// WORDSNET-12672 On loading the normAutofit element, fontScale="92500" attribute is not loaded
    /// correctly: the font scale becomes 0% that is not allowed in MS Word.
    /// </summary>
    @Test
    public void testJira12672() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestJira12672.docx");
        DmlTextBody shapeTextBody = getChildShapeTextBody(doc, 0);
        DmlNormalAutoFitMode autoFit = (DmlNormalAutoFitMode)shapeTextBody.getProperties().getAutoFitMode();

        msAssert.areEqual(0.925, autoFit.getFontScale(), "Loaded font scale value is wrong.");
    }

    @Test
    public void testDmlShapePatternFill() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestDmlShapeFillColor.docx");
        Shape dmlShape = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        Fill fill = dmlShape.getFill();
        msAssert.isTrue(fill.getVisible());
        msAssert.areEqual(FillType.PATTERNED, fill.getFillType());

        msAssert.areEqual(Color.RED.getRGB(), fill.getForeColor().getRGB());
        msAssert.areEqual(Color.YELLOW.getRGB(), fill.getBackColor().getRGB());

        // Set other colors.
        fill.setForeColor(msColor.getGreen());
        fill.setBackColor(Color.BLACK);

        msAssert.areEqual(msColor.getGreen().getRGB(), fill.getForeColor().getRGB());
        msAssert.areEqual(Color.BLACK.getRGB(), fill.getBackColor().getRGB());

        // Set other opacity.
        fill.setOpacity(0.3);
        dmlShape.getFillCore().setOpacity2(0.4);

        msAssert.areEqual(0.3, 0.01, fill.getOpacity());
        msAssert.areEqual(0.4, 0.01, dmlShape.getFillCore().getOpacity2());
    }

    @Test
    public void testDmlShapeBlipFill() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestDmlShapeFillColor.docx");
        Shape dmlShape = (Shape)doc.getChild(NodeType.SHAPE, 2, true);
        Fill fill = dmlShape.getFill();
        msAssert.isTrue(fill.getVisible());
        msAssert.areEqual(FillType.PICTURE, fill.getFillType());

        msAssert.areEqual(9509, fill.getImageBytes().length);
        byte[] imageBytes = StreamUtil.copyFileToByteArray(TestUtil.buildTestFileName("DocBuilder\\Image\\TestPng.png"));

        // Set new blip fill image.
        dmlShape.getFillCore().setImageBytes(imageBytes);
        msAssert.areEqual(29414, fill.getImageBytes().length);

        fill.setVisible(false);
        msAssert.areEqual(FillType.SOLID, fill.getFillType()); // We do not have NoFill type in Vml.
    }

    @Test
    public void testDmlOutlineClone() throws Exception
    {
        final String PATH_NAME = "Model\\DrawingML\\TestDmlOutline.docx";
        Document sourceDoc = TestUtil.open(PATH_NAME);

        NodeCollection shapes = sourceDoc.getChildNodes(NodeType.SHAPE, true);

        Shape shape0 = (Shape)shapes.get(0);
        Stroke beforeSave = shape0.getStroke();

        for (Shape shape : (Iterable<Shape>) shapes)
        {
            Stroke stroke = shape.getStroke();
            Shape clonedShape = (Shape)shape.deepClone(true);
            Stroke clonedStroke = clonedShape.getStroke();

            // Compare stroke params after cloning shape.
            compareStroke(stroke, clonedStroke);
        }

        Document resultDoc = TestUtil.saveOpen(sourceDoc, PATH_NAME);
        shapes = resultDoc.getChildNodes(NodeType.SHAPE, true);
        shape0 = (Shape)shapes.get(0);

        Stroke afterSave = shape0.getStroke();

        // Compare stroke params after save.
        compareStroke(beforeSave, afterSave);
    }

    @Test
    public void testDmlOutlineOn() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestDmlOutline.docx");

        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);

        Shape shape0 = (Shape)shapes.get(0);
        msAssert.isTrue(shape0.getStroke().getOn());
        shape0.getStroke().setOn(false);

        Shape shape1 = (Shape)shapes.get(1);
        msAssert.isTrue(shape1.getStroke().getOn());
        shape1.getStroke().setOn(false);

        Shape shape2 = (Shape)shapes.get(2);
        msAssert.isTrue(shape2.getStroke().getOn());
        shape2.getStroke().setOn(false);

        Shape shape3 = (Shape)shapes.get(3);
        msAssert.isFalse(shape3.getStroke().getOn());
        shape3.getStroke().setOn(true);

        TestUtil.saveOpen(doc, "Model\\DrawingML\\TestDmlOutlineOn.docx");
    }

    @Test
    public void testDmlOutlineParams() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestDmlOutline.docx");

        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);

        for (Shape shape : (Iterable<Shape>) shapes)
        {
            Stroke stroke = shape.getStroke();
            stroke.setOn(true);
            stroke.setWeight(5.0);
            stroke.setColor(Color.RED);
            stroke.setColor2(Color.BLUE);
            stroke.setDashStyle(DashStyle.SHORT_DASH_DOT_DOT);
            stroke.setOpacity(0.3);
            stroke.setJoinStyle(JoinStyle.MITER);
            stroke.setEndCap(EndCap.SQUARE);
            stroke.setLineStyle(ShapeLineStyle.TRIPLE);

            stroke.setStartArrowType(ArrowType.DIAMOND);
            stroke.setEndArrowType(ArrowType.DIAMOND);

            stroke.setStartArrowWidth(ArrowWidth.WIDE);
            stroke.setEndArrowWidth(ArrowWidth.NARROW);

            stroke.setStartArrowLength(ArrowLength.LONG);
            stroke.setEndArrowLength(ArrowLength.SHORT);
        }

        TestUtil.saveOpen(doc, "Model\\DrawingML\\TestDmlOutlineParams.docx");
    }

    /// <summary>
    /// WORDSNET-10987 Expose Font property for DrawingML same as Shape.Font.
    /// </summary>
    @Test
    public void testJira10987() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestJira10987.docx");

        // Get Dml shape and check its font attrs.
        Shape dml = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        Font dmlFont = dml.getFont();
        msAssert.isTrue(dmlFont.getParent() instanceof IShapeAttrSource);
        msAssert.areEqual("Blackoak Std", dmlFont.getName());
        msAssert.areEqual(-16732080, dmlFont.getColor().getRGB());
        msAssert.isTrue(dmlFont.getBold());
        msAssert.areEqual(Underline.SINGLE, dmlFont.getUnderline());

        // Get Vml shape and check its font attrs.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        Font shapeFont = shape.getFont();
        msAssert.areEqual("Adobe Fan Heiti Std B", shapeFont.getName());
        msAssert.areEqual(-16732080, shapeFont.getColor().getRGB());
        msAssert.isTrue(shapeFont.getBold());
        msAssert.areEqual(Underline.SINGLE, shapeFont.getUnderline());
    }





    /// <summary>
    /// WORDSNET-11673 Stroke.Color throws System.NullReferenceException.
    /// andrnosk: The problem occurs because we don't have default DmlOutline fill.
    /// Fixed by specifying DmlNoFill as default DmlOutline fill. <see cref="DmlOutlinePropertiesDefaults"/>
    /// </summary>
    @Test
    public void testJira11673() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertImage(TestUtil.buildTestFileName("DocBuilder\\Image\\TestPng.png"));
        Stroke stroke = shape.getStroke();
        stroke.setOn(true);
        stroke.setColor(msColor.getGreen());

        doc = TestUtil.saveOpen(doc, "Model\\DrawingML\\TestJira11673.docx", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(msColor.getGreen().getRGB(), shape.getStroke().getColor().getRGB());
    }



    /// <summary>
    /// WORDSNET-11789 Crop of images seems to be broken since 15.2.
    /// andrnosk: The problem occurs because starting from 15.2 AW inserts images as Dml.
    /// Fixed by creating <see cref="VmlToDmlAttrUtil"/> class to correctly process ImageData attrs.
    /// </summary>
    @Test
    public void testJira11789() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.insertParagraph();

        Shape shape = builder.insertImage(TestUtil.buildTestFileName("DocBuilder\\Image\\TestJira11789.png"));
        ImageData imageData = shape.getImageData();

        msAssert.areEqual(0, imageData.getCropTop());
        msAssert.areEqual(0, imageData.getCropBottom());
        msAssert.areEqual(0, imageData.getCropLeft());
        msAssert.areEqual(0, imageData.getCropRight());

        imageData.setCropTop(0.2);
        imageData.setCropBottom(0.2);
        imageData.setCropLeft(0.2);
        imageData.setCropRight(0.2);

        doc = TestUtil.saveOpen(doc, "Model\\DrawingML\\TestJira11789.docx");
        shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        imageData = shape.getImageData();

        msAssert.areEqual(0.2, imageData.getCropTop());
        msAssert.areEqual(0.2, imageData.getCropBottom());
        msAssert.areEqual(0.2, imageData.getCropLeft());
        msAssert.areEqual(0.2, imageData.getCropRight());
    }

    /// <summary>
    /// WORDSNET-11864 Shape fill color is not set correctly using Color.FromArgb
    /// andrnosk: The problem occurs because of converting System.Color to DmlPercentageRgbColor,
    /// fixed by converting System.Color to DmlHexRgbColor.
    /// </summary>
    @Test
    public void testJira11864() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertImage(TestUtil.buildTestFileName("DocBuilder\\Image\\TestPng.png"));
        Color color = new Color((102), (29), (98));
        shape.getFill().setColor(color);

        doc = TestUtil.saveOpen(doc, "Model\\DrawingML\\TestJira11864.docx");

        shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);

        msAssert.areEqual(color, shape.getFill().getColor());
    }

    /// <summary>
    /// WORDSNET-11856 Shape.Name returns empty string for shapes inside GroupShape.
    /// andrnosk: The problem occurs because inside DML shape located in group, Name and Description stored
    /// in wps:cNvPr (Non-Visual Drawing Properties) instead of wp:docPr.
    /// </summary>
    @Test
    public void testJira11856() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestJira11856.docx");

        GroupShape groupShape = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 0, true);

        msAssert.areEqual("Group 2", groupShape.getName());
        msAssert.areEqual("Main Group", groupShape.getAlternativeText());

        Node[] shapes = doc.getChildNodes(NodeType.SHAPE, true).toArray();

        Shape shape1 = (Shape)shapes[0];
        Shape shape2 = (Shape)shapes[1];

        msAssert.areEqual("Pie 1", shape1.getName());
        msAssert.areEqual("Drawing Shape", shape1.getAlternativeText());

        msAssert.areEqual("Text Box 2", shape2.getName());
        msAssert.areEqual("Text Box", shape2.getAlternativeText());

        groupShape.setName("NewGroupName");
        groupShape.setAlternativeText("NewGroupAlternativeText");

        int i = 0;
        for (Node shapeNode : shapes)
        {
            Shape shape = (Shape)shapeNode;
            shape.setName(msString.format("NewName{0}", i++));
            shape.setAlternativeText(msString.format("NewAlternativeText{0}", i++));
        }

        doc = TestUtil.saveOpen(doc, "Model\\DrawingML\\TestJira11856.docx", UnifiedScenario.DOCM_2_DOCM_NO_GOLD);

        groupShape = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 0, true);

        msAssert.areEqual("NewGroupName", groupShape.getName());
        msAssert.areEqual("NewGroupAlternativeText", groupShape.getAlternativeText());

        shapes = doc.getChildNodes(NodeType.SHAPE, true).toArray();

        shape1 = (Shape)shapes[0];
        shape2 = (Shape)shapes[1];

        msAssert.areEqual("NewName0", shape1.getName());
        msAssert.areEqual("NewAlternativeText1", shape1.getAlternativeText());

        msAssert.areEqual("NewName2", shape2.getName());
        msAssert.areEqual("NewAlternativeText3", shape2.getAlternativeText());
    }

    /// <summary>
    /// WORDSNET-11777 Image is rotated if we convert DOCX to other formats.
    /// andrnosk: The problem occurs because VML does not support 3D effects, so to correctly convert this document to WML
    /// or any other formats we have to render this shape.
    /// </summary>
    // FOSS: TestJira11777 removed — it converted a 3D DML shape to WordML by rendering it to a shape; WordML save
    // and shape rendering are both removed.


    /// <summary>
    /// WORDSNET-12083 Hyperlinks of GroupShape are lost after conversion from Docx to Doc/Pdf
    /// Copy hyperlink from DML when it is replaced by Fallback.
    /// </summary>
    // FOSS: TestJira12083 removed — it relied on the DML->VML Fallback path (triggered by validating to a
    // fixed/non-DML format) to copy a group-shape hyperlink; that fallback/rendering path is removed.

    /// <summary>
    /// WORDSNET-12025 ImageData.GrayScale not taking any effect.
    /// The problem occurs because DmlShapePrFiller and VmlToDmlAttrUtil do not procces Dml shape GrayScale attr.
    /// </summary>
    @Test
    public void testJira12025() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertImage(TestUtil.buildTestFileName("DocBuilder\\Image\\TestPng.png"));

        msAssert.isFalse(shape.getImageData().getGrayScale());

        shape.getImageData().setGrayScale(true);
        doc = TestUtil.saveOpen(doc, "Model\\DrawingML\\TestJira12025", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);

        msAssert.isTrue(shape.getImageData().getGrayScale());

        shape.getImageData().setGrayScale(false);
        msAssert.isFalse(shape.getImageData().getGrayScale());
    }

    @Test
    public void testLuminanceEffect() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertImage(TestUtil.buildTestFileName("DocBuilder\\Image\\TestPng.png"));
        ImageData imageData = shape.getImageData();

        msAssert.areEqual(0.5, imageData.getBrightness());
        msAssert.areEqual(0.5, imageData.getContrast());

        imageData.setBrightness(0.9);
        imageData.setContrast(0.9);

        msAssert.areEqual(0.9, 0.01, imageData.getBrightness());
        msAssert.areEqual(0.9, 0.01, imageData.getContrast());

        doc = TestUtil.saveOpen(doc, "Model\\DrawingML\\TestLuminanceEffect", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        imageData = shape.getImageData();

        msAssert.areEqual(0.9, 0.01, imageData.getBrightness());
        msAssert.areEqual(0.9, 0.01, imageData.getContrast());

        imageData.setBrightness(0.1);
        imageData.setContrast(0.1);

        msAssert.areEqual(0.1, 0.01, imageData.getBrightness());
        msAssert.areEqual(0.1, 0.01, imageData.getContrast());
    }

    @Test
    public void testBiLevelEffect() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertImage(TestUtil.buildTestFileName("DocBuilder\\Image\\TestPng.png"));

        msAssert.isFalse(shape.getImageData().getBiLevel());

        shape.getImageData().setBiLevel(true);
        doc = TestUtil.saveOpen(doc, "Model\\DrawingML\\TestBiLevelEffect", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);

        msAssert.isTrue(shape.getImageData().getBiLevel());

        shape.getImageData().setBiLevel(false);
        msAssert.isFalse(shape.getImageData().getBiLevel());
    }



    /// <summary>
    /// WORDSNET-12752 Unable to open cloned document in MS Office.
    /// The problem occurs because there is grpSp (Group shape) with cNvPr (Non-Visual Drawing Properties) without Id.
    /// The input doc does not have cNvPr inside grpSp at all. Fixed mechanism of writing cNvPr.
    /// </summary>
    @Test
    public void testJira12752() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestJira12752.docx");
        Document docClone = (Document)doc.deepClone(true);
        doc = TestUtil.saveOpen(docClone, "Model\\DrawingML\\TestJira12752", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        GroupShape groupShape = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 1, true);

        msAssert.isNull(groupShape.getDmlNode().getNonVisualPr().getNvDrawingProperties());
    }

    // FOSS: TestJira12918 removed — it verified the older-format (WordML) merge of Title+AltText into
    // "Title: {0} - Description: {1}"; DOCX keeps Title and AlternativeText as separate native fields,
    // and the WordML save that produced the merge is removed.




    /// <summary>
    /// WORDSNET-13508 Document.Save throws System.NullReferenceException while re-saving Docx.
    /// Some effects implementations have not default values for properties, but the specification describes it.
    /// Attempt to access to properties members of the effect failed with exception because properties values
    /// are not initialized.
    /// </summary>
    @Test
    public void testJira13508()
    {
        // Check that optional properties with reference type and default values are initialized.
        DmlShapePresetShadowEffect ePS = new DmlShapePresetShadowEffect();
        msAssert.areEqual(0.0d, ePS.getDirection().getValue());

        DmlShapeReflectionEffect eR = new DmlShapeReflectionEffect();
        msAssert.areEqual(0.0d, eR.getVerticalSkew().getValue());
        msAssert.areEqual(0.0d, eR.getHorizontalSkew().getValue());
        msAssert.areEqual(0.0d, eR.getDirection().getValue());
        msAssert.areEqual(5400000.0d, eR.getFadeDirection().getValue());

        DmlShapeOuterShadowEffect eOS = new DmlShapeOuterShadowEffect();
        msAssert.areEqual(0.0d, eOS.getDirection().getValue());
        msAssert.areEqual(0.0d, eOS.getVerticalSkew().getValue());
        msAssert.areEqual(0.0d, eOS.getHorizontalSkew().getValue());

        DmlShapeInnerShadowEffect eIS = new DmlShapeInnerShadowEffect();
        msAssert.areEqual(0.0d, eIS.getDirection().getValue());

        DmlShapeFillOverlayEffect eFO = new DmlShapeFillOverlayEffect();
        msAssert.isNotNull(eFO.getFillOverlay());
    }


    // FOSS: TestJira13314 removed — it converted DOCX->DOC to force DML->VML conversion in the shape
    // validator and checked the resulting warning; DOC save and that conversion path are removed.

    // FOSS: TestJira13423 removed — it loads a binary .doc (removed) and asserts all shapes are VML, then
    // checks VML<->DML markup across Word-version optimization (needs the removed DML<->VML conversion).


    private void compareStroke(Stroke stroke, Stroke strokeToCompare) throws Exception
    {
        msAssert.areNotSame(stroke, strokeToCompare);

        msAssert.areEqual(stroke.getOn(), strokeToCompare.getOn());
        msAssert.areEqual(stroke.getWeight(), strokeToCompare.getWeight());
        msAssert.areEqual(stroke.getColor(), strokeToCompare.getColor());
        msAssert.areEqual(stroke.getColor2(), strokeToCompare.getColor2());
        msAssert.areEqual(stroke.getDashStyle(), strokeToCompare.getDashStyle());
        msAssert.areEqual(stroke.getJoinStyle(), strokeToCompare.getJoinStyle());
        msAssert.areEqual(stroke.getEndCap(), strokeToCompare.getEndCap());
        msAssert.areEqual(stroke.getLineStyle(), strokeToCompare.getLineStyle());
        msAssert.areEqual(stroke.getStartArrowType(), strokeToCompare.getStartArrowType());
        msAssert.areEqual(stroke.getEndArrowType(), strokeToCompare.getEndArrowType());
        msAssert.areEqual(stroke.getStartArrowWidth(), strokeToCompare.getStartArrowWidth());
        msAssert.areEqual(stroke.getStartArrowLength(), strokeToCompare.getStartArrowLength());
        msAssert.areEqual(stroke.getEndArrowWidth(), strokeToCompare.getEndArrowWidth());
        msAssert.areEqual(stroke.getEndArrowLength(), strokeToCompare.getEndArrowLength());
        msAssert.areEqual(stroke.getOpacity(), strokeToCompare.getOpacity());
        msAssert.areEqual(stroke.getImageBytes(), strokeToCompare.getImageBytes());
    }


    /// <summary>
    /// WORDSNET-14145 Unsupported DML element cxnLst.
    /// Implement reading of the connection sites to the model and writing to output file.
    /// </summary>
    @Test
    public void testJira14145() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\DrawingML\\Shapes\\TestJira14145", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        GroupShape grShape = (GroupShape)doc.getFirstSection().getBody().getFirstParagraph().getChild(NodeType.GROUP_SHAPE, 0, false);
        DmlShape shape = (DmlShape)((Shape)grShape.getFirstChild()).getDmlNode();

        ArrayList<DmlConnectionSite> sites = shape.getGeometry().getConnectionSites();
        msAssert.areEqual(4, sites.size());

        DmlConnectionSite site = sites.get(0);
        msAssert.areEqual("myAngle", site.getAngle().getString());
        msAssert.areEqual("myXGuide", site.getCoordinates().getX().getString());
        msAssert.areEqual("507786", site.getCoordinates().getY().getString());

        site = sites.get(1);
        msAssert.areEqual("16200000", site.getAngle().getString());
        msAssert.areEqual("connsiteX0", site.getCoordinates().getX().getString());
        msAssert.areEqual("connsiteY0", site.getCoordinates().getY().getString());

        site = sites.get(2);
        msAssert.areEqual("0", site.getAngle().getString());
        msAssert.areEqual("connsiteX1", site.getCoordinates().getX().getString());
        msAssert.areEqual("connsiteY1", site.getCoordinates().getY().getString());

        site = sites.get(3);
        msAssert.areEqual("0", site.getAngle().getString());
        msAssert.areEqual("connsiteX2", site.getCoordinates().getX().getString());
        msAssert.areEqual("connsiteY2", site.getCoordinates().getY().getString());
    }

    /// <summary>
    /// WORDSNET-14146 Unsupported attributes of the path element (DML).
    /// The extrusionOk (3D Extrusion Allowed) and fill (Path Fill) attributes of the
    /// 20.1.9.15 path (Shape Path) element are lost after re-saving a document.
    /// </summary>
    @Test
    public void testJira14146() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\DrawingML\\Shapes\\TestJira14146", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        Paragraph par = doc.getFirstSection().getBody().getFirstParagraph();

        DmlShape fShape = (DmlShape)((Shape)par.getChild(NodeType.SHAPE, 0, false)).getDmlNode();
        DmlShape sShape = (DmlShape)((Shape)par.getChild(NodeType.SHAPE, 1, false)).getDmlNode();

        DmlPath path = fShape.getGeometry().getPaths().get(0);
        msAssert.isFalse(path.getExtrusionOk());

        path = sShape.getGeometry().getPaths().get(0);
        msAssert.isTrue(path.getExtrusionOk());
    }

    /// <summary>
    /// WORDSNET-14147 Unsupported elements of chart shape properties.
    /// Currently does not support the following subelements of the 21.2.2.197 spPr (Shape Properties) element:
    /// scene3d, sp3d, extLst (e.g. hiddenFill extension).
    /// </summary
    @Test
    public void testJira14147() throws Exception
    {
        final String PATH = "Model\\DrawingML\\Shapes\\TestJira14147";

        Document doc = TestUtil.openSaveOpen(PATH, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        Shape chart = (Shape)doc.getFirstSection().getBody().getFirstParagraph().getChild(NodeType.SHAPE, 0, false);
        ChartDataPointCollection dpCol = chart.getChart().getSeries().get(0).getDataPoints();

        DmlChartSpPr pr = dpCol.get(0).getSpPr();
        msAssert.areEqual(2, pr.getExtensions().getCount());
        msAssert.isNull(pr.getScene3DProp());
        msAssert.isNotNull(pr.getShape3DProp());

        pr = dpCol.get(1).getSpPr();
        DmlScene3DProperties scene3D = pr.getScene3DProp();
        DmlShape3DProperties shape3D = pr.getShape3DProp();

        msAssert.isNull(pr.getExtensions());
        msAssert.areEqual(DmlPresetCameraType.ORTHOGRAPHIC_FRONT, scene3D.getCamera().getPresetCameraType());
        msAssert.areEqual(DmlLightRigType.THREE_PT, scene3D.getLightRig().getLightRigType());
        msAssert.areEqual(DmlPresetMaterialType.TRANSLUCENT_POWDER, shape3D.getPresetMaterial());
        msAssert.areEqual(DmlBevelPresetType.ART_DECO, shape3D.getBevelTop().getBevelPresetType());
        msAssert.areEqual(DmlColorType.SCHEME_COLOR, shape3D.getContourColor().getColorType());
        msAssert.areEqual(25400, shape3D.getContourWidth());

        pr = dpCol.get(2).getSpPr();
        msAssert.isNull(pr.getExtensions());
        msAssert.isNull(pr.getScene3DProp());
        msAssert.isNotNull(pr.getShape3DProp());

        pr = dpCol.get(3).getSpPr();
        msAssert.isNull(pr.getExtensions());
        msAssert.isNull(pr.getScene3DProp());
        msAssert.isNotNull(pr.getShape3DProp());
    }

    /// <summary>
    /// WORDSNET-14128 Unsupported [MS-ODRAWXML] element 20.1.2.2.18 graphicFrame (Graphic Frame).
    /// Currently does not implement reading and writing graphic frame element which
    /// is included into DrawingML shape.
    /// </summary>
    @Test
    public void testJira14128() throws Exception
    {
        final String PATH = "Model\\DrawingML\\Shapes\\TestJira14128";
        final String STRICT_PATH = "Model\\DrawingML\\Shapes\\TestJira14128_Strict";

        Document doc = TestUtil.openSaveOpen(PATH, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        GroupShape group = (GroupShape)doc.getFirstSection().getBody().getFirstParagraph().getChild(NodeType.GROUP_SHAPE, 0, false);
        NodeCollection groups = group.getChildNodes(NodeType.GROUP_SHAPE, false);

        // Get the graphic frame from group on the second level of nesting.
        group = (GroupShape)((GroupShape)groups.get(0)).getChild(NodeType.GROUP_SHAPE, 0, false);
        DmlGroupShape dmlGroup = (DmlGroupShape)group.getDmlNode();
        checkGraphicFramePr(dmlGroup, 8, 20326.0, 9509.0, 21031.0, 15972.0);
        // Check that frame contains the chart.
        msAssert.areEqual(DmlNodeType.CHART, ((Shape)group.getFirstChild()).getDmlNode().getDmlNodeType());

        group = (GroupShape)groups.get(1);
        dmlGroup = (DmlGroupShape)group.getDmlNode();
        checkGraphicFramePr(dmlGroup, 9, -60, 15422.0, 20847.0, 13594.0);
        // Check that frame contains the chart.
        msAssert.areEqual(DmlNodeType.CHART, ((Shape)group.getFirstChild()).getDmlNode().getDmlNodeType());

        // Roundtrip for strict format and compare with gold.
        doc = TestUtil.open(STRICT_PATH, LoadFormat.DOCX);
        OoxmlSaveOptions so = new OoxmlSaveOptions(SaveFormat.DOCX);
        so.setTestMode();
        so.setCompliance(OoxmlCompliance.ISO_29500_2008_STRICT);
        TestUtil.saveOpen(doc, STRICT_PATH, UnifiedScenario.DOCX_2_DOCX, so);
    }

    /// <summary>
    /// WORDSNET-14128 for details see test "TestJira14128".
    /// Test checks reading and writing extensions of the graphic frame.
    /// </summary>
    @Test
    public void testJira14128ExtensionReading() throws Exception
    {
        // Prepare input data.
        String xml =
        "<wp:wgp" +
        " xmlns:wp=\"http://purl.oclc.org/ooxml/drawingml/wordprocessingDrawing\"" +
        " xmlns:a=\"http://purl.oclc.org/ooxml/drawingml/main\">" +
            "<wp:graphicFrame>" +
                "<wp:cNvPr id=\"8\" name=\"Diagram 6\">" +
                    "<a:extLst>" +
                       "<a:ext uri=\"{77A0092B-C50C-777-A947-70E740481C1C}\">" +
                       "</a:ext>" +
                    "</a:extLst>" +
                "</wp:cNvPr>" +
                "<wp:cNvFrPr>" +
                    "<a:graphicFrameLocks/>" +
                    "<a:extLst>" +
                           "<a:ext uri=\"{78A0092B-C50C-777-A947-70E740481C1C}\">" +
                           "</a:ext>" +
                    "</a:extLst>" +
                "</wp:cNvFrPr>" +
                "<a:extLst>" +
                    "<a:ext uri=\"{79A0092B-C50C-777-A947-70E740481C1C}\">" +
                    "</a:ext>" +
                "</a:extLst>" +
            "</wp:graphicFrame>" +
        "</wp:wgp>";
        // Perform reading (check that graphic frame extensions can be read).
        DocxDocumentReaderStub reader = new DocxDocumentReaderStub(xml);
        GroupShape stubGroup = DmlCompositeNodeReader.readGroup(reader);

        // Prepare document for export.
        final String PATH = "Model\\DrawingML\\Shapes\\TestJira14128";
        Document doc = TestUtil.open(PATH, LoadFormat.DOCX);

        GroupShape group, groupForReplace;
        group = (GroupShape)doc.getFirstSection().getBody().getFirstParagraph().getChild(NodeType.GROUP_SHAPE, 0, false);
        NodeCollection groups = group.getChildNodes(NodeType.GROUP_SHAPE, false);
        groupForReplace = (GroupShape)groups.get(1);

        // Replace graphic frame properties with properties which were read from the xml above.
        group = (GroupShape)stubGroup.getFirstChild();
        groupForReplace.getDmlNode().setNonVisualPr(group.getDmlNode().getNonVisualPr());
        ((DmlGroupShape)groupForReplace.getDmlNode()).setExtensions(((DmlGroupShape)group.getDmlNode()).getExtensions());

        // Perform export (writing) of the prepared document.
        DocxExportContext exportHelper = new DocxExportContext(doc, OoxmlComplianceCore.ISO_STRICT);

        // Get attributes by name, in C++ namespace attribute is returned as the first.
        String attrName = "uri";
        String graphicFrameExt = exportHelper.getXmlNode("//wp:graphicFrame/a:extLst").getFirstChild().getAttributes().getNamedItem(attrName).getNodeValue();
        String cNvPrExt = exportHelper.getXmlNode("//wp:graphicFrame/wp:cNvPr/a:extLst").getFirstChild().getAttributes().getNamedItem(attrName).getNodeValue();
        String cNvFrPrExt = exportHelper.getXmlNode("//wp:graphicFrame/wp:cNvFrPr/a:extLst").getFirstChild().getAttributes().getNamedItem(attrName).getNodeValue();

        msAssert.isTrue(StringUtil.contains(cNvPrExt, "77A0092B", true));
        msAssert.isTrue(StringUtil.contains(cNvFrPrExt, "78A0092B", true));
        msAssert.isTrue(StringUtil.contains(graphicFrameExt, "79A0092B", true));
    }

    // FOSS: TestJira13972 removed — it checked shape BWMode after a DOCX->DOC roundtrip; DOC save is removed.


    /// <summary>
    /// Tests checks behavior of the <see cref="DocxDocumentWriterBase.GetChoiceRequires"/> method for DML shape
    /// with type <see cref="DmlNodeType.GraphicFrame"/>.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QUnexpected DrawingML type.\\E")
    public void testGraphicFrameChoiceRequires() throws Exception
    {
        GroupShape gf = new GroupShape(new Document(), ShapeMarkupLanguage.DML);
        gf.setDmlNode(new DmlGroupShape(DmlNodeType.GRAPHIC_FRAME));

        DocxDocumentWriterBase.getChoiceRequires(gf);
    }

    /// <summary>
    /// Test checks behavior of the <see cref="DocxDocumentWriterBase.GetChoiceRequires"/> method for DML shape
    /// with type <see cref="DmlNodeType.GraphicFrame"/>.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QUnexpected DrawingML type.\\E")
    public void testGetNamespaceGraphicFrame()
    {
        DmlNamespaceUtil.getNamespace(DmlNodeType.GRAPHIC_FRAME, false);
    }

    /// <summary>
    /// Test checks ability to insert new DML nodes into shape when destination or new node
    /// is graphic frame.
    /// </summary>
    @Test
    public void testGraphicFrameCanInsertIntoShape() throws Exception
    {
        Document doc = new Document();

        Shape destShape = new Shape(doc, ShapeMarkupLanguage.DML);
        Shape newShape = new Shape(doc, ShapeMarkupLanguage.DML);

        GroupShape grFrame = new GroupShape(doc, ShapeMarkupLanguage.DML);
        grFrame.setDmlNode(new DmlGroupShape(DmlNodeType.GRAPHIC_FRAME));

        // 1. Case when destination DML shape is not composite node and new shape is graphic frame.
        destShape.setDmlNode(new DmlShape(DmlNodeType.WORDPROCESSING_SHAPE));
        msAssert.isFalse(destShape.canInsert(grFrame));

        // 2. Case when destination shape contains reference to outer xml (chart) and new shape is
        // graphic frame.
        destShape.setDmlNode(new DmlChartSpace());
        msAssert.isTrue(destShape.canInsert(grFrame));

        // 3. Case when destination shape is graphic frame and new node does not contain
        // reference to outer xml.
        destShape.setDmlNode(new DmlGroupShape(DmlNodeType.GRAPHIC_FRAME));
        newShape.setDmlNode(new DmlShape(DmlNodeType.WORDPROCESSING_SHAPE));
        msAssert.isFalse(destShape.canInsert(newShape));

        // 4. Case when destination shape is graphic frame and new node is chart
        newShape.setDmlNode(new DmlChartSpace());
        msAssert.isTrue(destShape.canInsert(newShape));

        // 5. Case when destination shape is graphic frame and new node is diagram.
        newShape.setDmlNode(new DmlDiagram());
        msAssert.isTrue(destShape.canInsert(newShape));

        // 6. Case when destination shape is group shape and new child node is graphic frame.
        destShape.setDmlNode(new DmlGroupShape(DmlNodeType.WORDPROCESSING_GROUP_SHAPE));
        msAssert.isTrue(destShape.canInsert(grFrame));

        // 7. Case when destination shape is nested group shape and new child node is graphic frame.
        destShape.setDmlNode(new DmlGroupShape(DmlNodeType.GROUP_SHAPE));
        msAssert.isTrue(destShape.canInsert(grFrame));

        // 7. Case when destination shape is nested group shape and new child node is graphic frame.
        destShape.setDmlNode(new DmlGroupShape(DmlNodeType.GROUP_SHAPE));
        msAssert.isTrue(destShape.canInsert(grFrame));

        // 8. Case when destination shape is canvas shape and new child node is graphic frame.
        destShape.setDmlNode(new DmlLockedCanvas(DmlNodeType.WORDPROCESSING_CANVAS));
        msAssert.isFalse(destShape.canInsert(grFrame));

        // 9. Case when destination shape is locked canvas shape and new child node is graphic frame.
        destShape.setDmlNode(new DmlLockedCanvas(DmlNodeType.LOCKED_CANVAS));
        msAssert.isFalse(destShape.canInsert(grFrame));
    }

    /// <summary>
    /// Test checks ability to insert new DML nodes into shape when destination or new node
    /// is graphic frame.
    /// </summary>
    @Test
    public void testGrFrameCanInsertIntoGroupShape() throws Exception
    {
        Document doc = new Document();

        GroupShape destShape = new GroupShape(doc, ShapeMarkupLanguage.DML);
        GroupShape newShape = new GroupShape(doc, ShapeMarkupLanguage.DML);

        // 1. Case when destination shape is group shape and new shape is graphic frame.
        destShape.setDmlNode(new DmlGroupShape(DmlNodeType.WORDPROCESSING_GROUP_SHAPE));
        newShape.setDmlNode(new DmlGroupShape(DmlNodeType.GRAPHIC_FRAME));
        msAssert.isTrue(destShape.canInsert(newShape));

        // 2. Case when destination shape is nested group shape and new shape is graphic frame.
        destShape.setDmlNode(new DmlGroupShape(DmlNodeType.GROUP_SHAPE));
        msAssert.isTrue(destShape.canInsert(newShape));

        // 3. Case when destination shape is chart and new shape is graphic frame.
        destShape.setDmlNode(new DmlChartSpace());
        msAssert.isTrue(destShape.canInsert(newShape));

        // 4. Case when destination shape is canvas and new shape is graphic frame.
        destShape.setDmlNode(new DmlLockedCanvas(DmlNodeType.WORDPROCESSING_CANVAS));
        msAssert.isFalse(destShape.canInsert(newShape));

        // 5. Case when destination shape is nested canvas and new shape is graphic frame.
        // AM. It seems that GroupShape can be child node of LockedCanvas. See WORDSNET-15844
        destShape.setDmlNode(new DmlLockedCanvas(DmlNodeType.LOCKED_CANVAS));
        msAssert.isTrue(destShape.canInsert(newShape));

        // 6. Case when destination shape is graphic frame and new shape is chart.
        destShape.setDmlNode(new DmlGroupShape(DmlNodeType.GRAPHIC_FRAME));
        newShape.setDmlNode(new DmlChartSpace());
        msAssert.isTrue(destShape.canInsert(newShape));

        // 7. Case when destination shape is graphic frame and new shape is diagram.
        newShape.setDmlNode(new DmlDiagram());
        msAssert.isTrue(destShape.canInsert(newShape));

        // 8. Case when destination shape is graphic frame and new shape is graphic frame.
        newShape.setDmlNode(new DmlGroupShape(DmlNodeType.GRAPHIC_FRAME));
        msAssert.isFalse(destShape.canInsert(newShape));

        // 9. Case when destination shape is graphic frame and new shape is canvas.
        newShape.setDmlNode(new DmlLockedCanvas(DmlNodeType.WORDPROCESSING_CANVAS));
        msAssert.isFalse(destShape.canInsert(newShape));

        // 9. Case when destination shape is graphic frame and new shape is group shape.
        newShape.setDmlNode(new DmlGroupShape(DmlNodeType.WORDPROCESSING_GROUP_SHAPE));
        msAssert.isFalse(destShape.canInsert(newShape));

        // 10. Case when destination shape is graphic frame and new shape is not group shape.
        newShape.setDmlNode(new DmlShape(DmlNodeType.WORDPROCESSING_SHAPE));
        msAssert.isFalse(destShape.canInsert(newShape));
    }

    /// <summary>
    /// WORDSNET-14547 NullReferenceException occurs when calling Shape.AlternativeText.
    /// Non-visual properties for diagrams and charts are not initialized. Some public properties of the
    /// DML shape takes from non-visual properties and it is cause "NullReferenceException".
    /// </summary>
    @Test
    public void testJira14547() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestJira14547", LoadFormat.DOCX);
        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);

        for (Shape shape : (Iterable<Shape>) shapes)
        {
            // Ignore shapes exclude diagram and chart.
            if (shape.getDmlNode().getDmlNodeType() == DmlNodeType.WORDPROCESSING_SHAPE)
                continue;

            // Check reading.
            msAssert.isTrue(!StringUtil.hasChars(shape.getAlternativeText()));
            msAssert.isTrue(!StringUtil.hasChars(shape.getTitle()));
            msAssert.isTrue(!StringUtil.hasChars(shape.getName()));
            msAssert.isFalse(shape.getAspectRatioLocked());

            // Check writing.
            shape.setAspectRatioLocked(true);
            shape.setAlternativeText("1");
            shape.setTitle("1");
            shape.setName("1");
        }
    }

    /// <summary>
    /// Test concerned with issue WORDSNET-14542 and checks processing of the user shapes
    /// on writing output document. This shapes have to be skipped while writing chart.
    /// </summary>
    @Test
    public void testGrFrameHasChartWithUserShapes() throws Exception
    {
        final String FILE_NAME = "Model\\DrawingML\\TestGrFrameHasChartWithUserShapes";

        Document doc = TestUtil.open(FILE_NAME, LoadFormat.DOCX);

        // Check that top level group has not fallback shape.
        GroupShape group = (GroupShape)doc.getFirstSection().getBody().getFirstParagraph().getChildNodes(NodeType.ANY, false).get(1);
        msAssert.isNull(group.getFallbackShape());

        // Use gold file to check generated fallback and output XML.
        doc = TestUtil.saveOpen(doc, FILE_NAME, UnifiedScenario.DOCX_2_DOCX);

        group = (GroupShape)doc.getFirstSection().getBody().getFirstParagraph().getChildNodes(NodeType.ANY, false).get(1);
        GroupShape grFrame = (GroupShape)group.getFirstChild();

        msAssert.areEqual(DmlNodeType.GRAPHIC_FRAME, grFrame.getDmlNode().getDmlNodeType());
        msAssert.areEqual(1, grFrame.getChildNodes(NodeType.ANY, false).getCount());

        // Check that chart has one user shape.
        msAssert.areEqual(1, ((Shape)grFrame.getFirstChild()).getChildNodes(NodeType.ANY, false).getCount());
    }

    /// <summary>
    /// Checks graphic frame properties.
    /// </summary>
    /// <param name="dmlGroup">Graphic frame shape for check.</param>
    /// <param name="id">Unique identifier for the current DrawingML object within the document.</param>
    /// <param name="xOffset">Coordinate on the x-axis.</param>
    /// <param name="yOffset">Coordinate on the y-axis.</param>
    /// <param name="width">Width of the extents rectangle in EMUs.</param>
    /// <param name="length">Length of the extents rectangle in EMUs.</param>
    private static void checkGraphicFramePr(
        DmlGroupShape dmlGroup,
        int id,
        double xOffset,
        double yOffset,
        double width,
        double length)
    {
        msAssert.areEqual(DmlNodeType.GRAPHIC_FRAME, dmlGroup.getDmlNodeType());

        DmlTransform transform = dmlGroup.getTransformInternal();

        DmlNvPrGraphicFrame grFrPr = (DmlNvPrGraphicFrame)dmlGroup.getNonVisualPr();
        DmlCnvPrGraphicFrame cnvFrPr = (DmlCnvPrGraphicFrame)grFrPr.getCNvProperties();
        DmlNvDrawingProperties nvPr = (DmlNvDrawingProperties)grFrPr.getNvDrawingProperties();

        msAssert.areEqual(DmlNvHolder.GRAPHIC_FRAME, grFrPr.getHolder());

        msAssert.isTrue(MathUtil.areEqual(xOffset, transform.getXOffset()));
        msAssert.isTrue(MathUtil.areEqual(yOffset, transform.getYOffset()));
        msAssert.isTrue(MathUtil.areEqual(width, transform.getWidth()));
        msAssert.isTrue(MathUtil.areEqual(length, transform.getHeight()));

        msAssert.isNotNull(cnvFrPr.getLocks());
        msAssert.areEqual(DmlNvHolder.GRAPHIC_FRAME, cnvFrPr.getHolder());

        msAssert.areEqual(id, nvPr.getId());
        msAssert.isTrue(!msString.isNullOrEmpty(nvPr.getName()));
    }

    /// <summary>
    /// Test checks reading and writing of the hidden line extension.
    /// </summary>
    @Test
    public void testReadingWritingHiddenLineExtension() throws Exception
    {
        final String PATH = "Model\\DrawingML\\Shapes\\TestReadingWritingHiddenLineExt";

        Document doc = TestUtil.openSaveOpen(PATH, UnifiedScenario.DOCX_2_DOCX);
        NodeCollection docShapes = doc.getFirstSection().getBody().getChildNodes(NodeType.SHAPE, true);

        DmlTailLineEndStyle tailEndStyle = new DmlTailLineEndStyle();
        tailEndStyle.setLength(ArrowLength.LONG);
        tailEndStyle.setWidth(ArrowWidth.WIDE);
        tailEndStyle.setType(ArrowType.ARROW);

        DmlHeadLineEndStyle headEndStyle = new DmlHeadLineEndStyle();
        headEndStyle.setLength(ArrowLength.MEDIUM);
        headEndStyle.setWidth(ArrowWidth.MEDIUM);
        headEndStyle.setType(ArrowType.OVAL);

        checkHiddenLineExtension(docShapes, null, 0, DmlDashType.PRESET_DASH, DashStyle.DASH,
            "1F4D78", 69850.0, 8.0, null, null);
        checkHiddenLineExtension(docShapes, null, 1, DmlDashType.PRESET_DASH, DashStyle.SHORT_DOT,
            "7030A0", 41275.0, 8.0, tailEndStyle, headEndStyle);
        checkHiddenLineExtension(docShapes, null, 2, DmlDashType.PRESET_DASH, DashStyle.DEFAULT,
             "000000", 9525.0, 8.0, null, null);
        checkHiddenLineExtension(docShapes, null, 3, DmlDashType.PRESET_DASH, DashStyle.DEFAULT,
             "FF0000", 101600.0, 8.0, null, null);
        checkHiddenLineExtension(docShapes, null, 4, DmlDashType.PRESET_DASH, DashStyle.LONG_DASH,
             "000000", 98425.0, 8.0, null, null);
        checkHiddenLineExtension(docShapes, null, 5, DmlDashType.PRESET_DASH, DashStyle.DEFAULT,
             "92D050", 34925.0, 8.0, null, null);

        // Check that "fallback" of the shape contains expected extension.
        Shape shape = (Shape)doc.getFirstSection().getHeadersFooters().get(1).getFirstParagraph().getFirstChild();
        DmlExtension ext = ((DmlShape)shape.getFallbackShape().getDmlNode()).getSpPrExtensions().get(DmlExtensionUri.HIDDEN_LINE);

        checkHiddenLineExtension(null, ext, 0, DmlDashType.PRESET_DASH, DashStyle.DEFAULT,
            "92D050", 34925.0, 0.0, null, null);
    }




    /// <summary>
    /// WORDSNET-16362 Shape is not resized properly.
    /// MSW automation resets relative size while absolute width changing. Do the same to fix the problem.
    /// </summary>
    @Test
    public void testJira16362() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\Shapes\\TestJira16362.docx");
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);

        // Check original relative size.
        checkRelativeSize(shape, 0, 200);

        shape.getTextBox().setFitShapeToText(false);
        shape.setAspectRatioLocked(false);
        shape.setHeight(200.0);

        // Relative height has to be reset.
        checkRelativeSize(shape, 0, 0);

        shape.setWidth(400.0);
        shape.setRotation(30.0);

        doc = TestUtil.saveOpen(doc, "Model\\DrawingML\\Shapes\\TestJira16362", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        checkRelativeSize(doc.getFirstSection().getBody().getShapes().get(0), 0, 0);
    }

    /// <summary>
    /// Relates to WORDSNET-16362
    /// Checks how changes of the absolute sizes values are affected to relative sizes.
    /// </summary>
    @Test
    public void testJira16362RelSizes() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\Shapes\\TestJira16362RelSizes.docx");
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();

        // Document contains two shapes VML and DML. Check behavior for both types.
        for (Shape shape : (Iterable<Shape>) shapes)
        {
            // If there is not relative width attributes, then after changing of the absolute value nothing to change.
            shape.getShapePr().remove(ShapeAttr.RELATIVE_WIDTH);
            shape.getShapePr().remove(ShapeAttr.WIDTH_PERCENT);
            shape.setWidth(400.0);
            checkRelativeSize(shape, null, 200);

            // Initialize relative width and check current values.
            shape.getShapePr().set(ShapeAttr.RELATIVE_WIDTH, RelativeHorizontalSize.MARGIN);
            shape.getShapePr().set(ShapeAttr.WIDTH_PERCENT, 200);
            checkRelativeSize(shape, 200, 200);

            // Check that relative width will be reset.
            shape.setWidth(400.0);
            checkRelativeSize(shape, 0, 200);

            // Check that relative height will be reset.
            shape.setHeight(200.0);
            checkRelativeSize(shape, 0, 0);

            // If there is not relative height attributes, then after changing of the absolute value nothing to change.
            shape.getShapePr().remove(ShapeAttr.RELATIVE_HEIGHT);
            shape.getShapePr().remove(ShapeAttr.HEIGHT_PERCENT);
            shape.setHeight(400.0);
            checkRelativeSize(shape, 0, null);
        }

        msAssert.areEqual(ShapeMarkupLanguage.DML, shapes.get(0).getMarkupLanguage());
        msAssert.areEqual(ShapeMarkupLanguage.VML, shapes.get(1).getMarkupLanguage());
    }



    // FOSS: TestJira16359Vml removed — it loads and round-trips a WordML (.wml) VML shape; WordML load+save removed.


    /// <summary>
    /// Related to WORDSNET-16976
    /// Issue with hidden property, which returns invalid value for nested shapes.
    /// </summary>
    @Test
    public void testJira16976Hidden() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestJira16976Hidden.docx");
        Shape shape = (Shape)doc.getFirstSection().getBody().getChild(NodeType.SHAPE, 0, true);
        msAssert.isTrue(shape.getHidden());

        shape = (Shape)doc.getFirstSection().getBody().getChild(NodeType.SHAPE, 1, true);
        msAssert.isFalse(shape.getHidden());

        // Check, that setter works without errors.
        shape.setHidden(true);
        msAssert.isTrue(shape.getHidden());
    }


    /// <summary>
    /// Relates to WORDSNET-13967
    /// Checks, that shape constructor raises exception for SingleCornerSnipped shape type.
    /// </summary>
    @Test (expectedExceptions = UnsupportedOperationException.class, expectedExceptionsMessageRegExp = "\\QCannot create shapes of this type.\\E")
    public void testJira13967SetShapeType2() throws Exception
    {
        new Shape(new Document(), ShapeType.SINGLE_CORNER_SNIPPED);
    }


    /// <summary>
    /// Realted to  WORDSNET-17530
    /// Checks case when document contains textbox.
    /// </summary>
    @Test
    public void testJira17530Txbx() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestJira17530Txbx.docx");
        msAssert.areEqual(ShapeType.TEXT_BOX, doc.getFirstSection().getBody().getShapes().get(0).getShapeType());
    }

    /// <summary>
    /// WORDSNET-17672 Document.UpdatePageLayout throws System.NullReferenceException.
    /// AW does not read "nvGraphicFramePr" element it is caused the issue. Read the non visual
    /// graphic frame properties to fix the problem.
    /// </summary>
    @Test
    public void testJira17672() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestJira17672.docx");
        GroupShape lockedCanvas = (GroupShape)doc.getFirstSection().getBody().getChild(NodeType.GROUP_SHAPE, 0, true);
        msAssert.areEqual(DmlNodeType.LOCKED_CANVAS, lockedCanvas.getDmlNode().getDmlNodeType());

        GroupShape graphicFrame = (GroupShape)lockedCanvas.getFirstChild();
        DmlGroupShape dmlGraphicFrame = (DmlGroupShape)graphicFrame.getDmlNode();
        msAssert.areEqual(DmlNodeType.GRAPHIC_FRAME, dmlGraphicFrame.getDmlNodeType());
        msAssert.isTrue(dmlGraphicFrame.isParentLockedCanvas());

        msAssert.isNotNull(dmlGraphicFrame.getNonVisualPr());
        msAssert.areEqual("6 Gráfico", dmlGraphicFrame.getNonVisualPr().getNvDrawingProperties().getName());

        // Check client case.
        // No update page layout in FOSS.

        // Check resiliency which was added for "hidden" attribute.
        dmlGraphicFrame.setNonVisualPr(null);
        msAssert.isFalse(dmlGraphicFrame.getHidden());
    }

    /// <summary>
    /// Related to WORDSNET-17672
    /// Checks sequence of child nodes of the graphic frame placed into the locked canvas.
    /// </summary>
    @Test
    public void testJira17672MarkUp() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestJira17672.docx");
        DocxExportContext exportContext = new DocxExportContext(doc);

        org.w3c.dom.Node graphicFrameElement = exportContext.getXmlNode("descendant::a:graphicFrame");
        org.w3c.dom.NodeList xmlNodes = graphicFrameElement.getChildNodes();
        msAssert.areEqual("a:nvGraphicFramePr", xmlNodes.item(0).getNodeName());
        // The sequence of these nodes is important. Otherwise Word can not open document.
        // Also there are cases when sequence must be reversed (TestJira14128, TestGrFrameHasChartWithUserShapes).
        msAssert.areEqual("a:graphic", xmlNodes.item(1).getNodeName());
        msAssert.areEqual("a:xfrm", xmlNodes.item(2).getNodeName());
    }








    /// <summary>
    /// Related with WORDSNET-21498
    /// The case when link is reset for a group.
    /// <see cref="DmlNvPrBase.NvDrawingProperties"/> is not changed at a this case.
    /// </summary>
    @Test
    public void test21498Group() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\Test21498Group", LoadFormat.DOCX);
        GroupShape group = (GroupShape)doc.getFirstSection().getBody().getChild(NodeType.GROUP_SHAPE, 0, true);

        String url = "https://www.aspose.com/";

        msAssert.areEqual(url, group.getShapePr().get(ShapeAttr.HYPERLINK_ADDRESS));
        msAssert.isNull(group.getDmlNode().getNonVisualPr().getNvDrawingProperties());

        // Act.
        group.setHRef("");

        // Assert.
        msAssert.isNull(group.getShapePr().get(ShapeAttr.HYPERLINK_ADDRESS));
        msAssert.isNull(group.getDmlNode().getNonVisualPr().getNvDrawingProperties());
    }

    /// <summary>
    /// Related with WORDSNET-21498
    /// The case when link is reset for a rectangle.
    /// <see cref="DmlNvPrBase.NvDrawingProperties"/> is not changed at a this case.
    /// </summary>
    @Test
    public void test21498Rect() throws Exception
    {
        Shape shape = TestUtil.open("Model\\DrawingML\\Test21498Rect", LoadFormat.DOCX).getFirstSection().getBody().getShapes().get(0);

        msAssert.isNull(shape.getShapePr().get(ShapeAttr.HYPERLINK_ADDRESS));
        msAssert.isNull(shape.getDmlNode().getNonVisualPr().getNvDrawingProperties());
        String url = "https://www.google.com/";

        // Act.
        shape.setHRef(url);

        // Assert.
        msAssert.areEqual(url, shape.getShapePr().get(ShapeAttr.HYPERLINK_ADDRESS));
        msAssert.isNull(shape.getDmlNode().getNonVisualPr().getNvDrawingProperties());
    }


    /// <summary>
    /// Relates to WORDSNET-21597
    /// Tests whether Decorative is preserved during the roundtrip.
    /// </summary>
    // FOSS: only the OOXML roundtrip survives — Doc/Rtf/Wml load+save were removed.
    @Test (dataProvider = "test21597DataProvider")
    public void test21597(/*UnifiedScenario*/int scenario) throws Exception
    {
        final String FILE_NAME = "Model\\DrawingML\\Test21597";

        Document doc;
        if (scenario == UnifiedScenario.RTF_2_DOCX)
        {
            doc = TestUtil.open(FILE_NAME, LoadFormat.RTF);
            OoxmlSaveOptions options = new OoxmlSaveOptions(SaveFormat.DOCX);
            options.setCompliance(OoxmlCompliance.ISO_29500_2008_STRICT);
            doc = TestUtil.saveOpen(doc, FILE_NAME, options, false);
        }
        else
            doc = TestUtil.openSaveOpen(FILE_NAME, scenario | UnifiedScenario.NO_GOLD);

        Shape shape1 = doc.getFirstSection().getBody().getShapes().get(0);
        Shape shape2 = doc.getFirstSection().getBody().getShapes().get(1);

        msAssert.isTrue(shape1.getGraphicData().getDecorative());
        msAssert.isFalse(shape2.getGraphicData().getDecorative());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test21597DataProvider")
	public static Object[][] test21597DataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.DOCX_2_DOCX_NO_GOLD},
		};
	}

    /// <summary>
    /// WORDSNET-21649 Bookmarks are lost after initiating LayoutEnumerator.
    /// Removes fallback if alternate content for choice and fallback is different.
    /// </summary>
    @Test
    public void test21649() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Textbox\\Test21649.docx");

        TestUtil.executeValidator(doc, SaveFormat.DOCX);   // FOSS: was a raw validator run with null SaveOptions (now asserts).

        int bookMarkStarts = doc.getChildNodes(NodeType.BOOKMARK_START, true).getCount();
        int bookMarkEnds = doc.getChildNodes(NodeType.BOOKMARK_END, true).getCount();

        msAssert.areEqual(77, bookMarkStarts);
        msAssert.areEqual(77, bookMarkEnds);
    }



    /// <summary>
    /// Gets <see cref="DmlDiagramDataModel"/> instance of a diagram of a child shape of the specified node.
    /// </summary>
    private static DmlDiagramDataModel getDiagramDataModel(CompositeNode parent, int diagramShapeIndex)
    {
        Shape shape = (Shape)parent.getChild(NodeType.SHAPE, diagramShapeIndex, true);
        return ((DmlDiagram)shape.getDmlNode()).getData();
    }

    /// <summary>
    /// Gets <see cref="DmlTextBody"/> instance of a child shape of the specified node.
    /// </summary>
    private static DmlTextBody getChildShapeTextBody(CompositeNode parent, int shapeIndex)
    {
        Shape shape = (Shape)parent.getChild(NodeType.SHAPE, shapeIndex, true);
        return shape.getDmlShape().getTextShape().getTextBody();
    }

    /// <summary>
    /// Checks space after the specified DML paragraph.
    /// </summary>
    private static void checkSpaceAfter(DmlParagraph paragraph, int expectedValue, String expectedParagraphText)
    {
        // Check text to make sure this is the expected paragraph.
        msAssert.areEqual(expectedParagraphText, paragraph.getRunText());

        DmlPointsTextSpacing spaceAfter =
            (DmlPointsTextSpacing)paragraph.getProperties().getDirectProperty(DmlParagraphPropertiesIds.SPACE_AFTER);

        msAssert.isNotNull(spaceAfter);
        msAssert.areEqual(expectedValue, spaceAfter.getValue().getValue());
    }

    /// <summary>
    /// Sequentially sets passed shape types for specified shapes.
    /// </summary>
    private static void checkShapeTypeSetter(Shape shape, /*ShapeType*/int[] shapeTypes, HashMap<Integer, Object> exclusions)
    {
        for (/*ShapeType*/int shapeType : shapeTypes)
        {
            try
            {
                shape.setShapeType(shapeType);
                msAssert.areEqual(shapeType, shape.getShapePr().get(ShapeAttr.SHAPE_TYPE));
            }
            catch (IllegalStateException e)
            {
                if ((exclusions != null) && !exclusions.containsKey(shapeType))
                    throw e;
            }
        }
    }

    /// <summary>
    /// Checks opacity value for specified shape, takes in attention the precision while converting double to fixed.
    /// </summary>
    private static void checkVmlOpacity(Shape shape, double opacity)
    {
        int fixedOpacity = ConvertUtilCore.doubleToFixed(opacity);
        msAssert.isTrue(MathUtil.areEqual(ConvertUtilCore.fixedToDouble(fixedOpacity), shape.getFill().getOpacity()));
    }

    /// <summary>
    /// Checks shape relative sizes values according to specified values.
    /// </summary>
    private static void checkRelativeSize(ShapeBase shape, Object relWidth, Object relHeight)
    {
        if (relWidth != null)
        {
            msAssert.areEqual(RelativeHorizontalSize.MARGIN, shape.getShapePr().get(ShapeAttr.RELATIVE_WIDTH));
            msAssert.areEqual((/*int*/Integer)relWidth, (/*int*/Integer)shape.getWidthPercent());
        }
        else
        {
            msAssert.isNull(shape.getWidthPercent());
            msAssert.isNull(shape.getShapePr().get(ShapeAttr.RELATIVE_WIDTH));
        }

        if (relHeight != null)
        {
            msAssert.areEqual(RelativeVerticalSize.MARGIN, shape.getShapePr().get(ShapeAttr.RELATIVE_HEIGHT));
            msAssert.areEqual((/*int*/Integer)relHeight, (/*int*/Integer)shape.getHeightPercent());
        }
        else
        {
            msAssert.isNull(shape.getHeightPercent());
            msAssert.isNull(shape.getShapePr().get(ShapeAttr.RELATIVE_HEIGHT));
        }
    }

    /// <summary>
    /// Checks content of the hidden line extension  according to specified arguments.
    /// Method can check extension of the shape which stores in the collection or
    /// extension can be passed explicitly.
    /// </summary>
    /// <param name="shapes">Collection of the shapes which holds extensions.</param>
    /// <param name="ext">Extension for check.</param>
    /// <param name="shapeIndex">Index of the shape in the collection.</param>
    /// <param name="DmlDashType">Dashing scheme.</param>
    /// <param name="DashStyle">Dashed line style.</param>
    /// <param name="color">Value of the "val" attribute of the color.</param>
    /// <param name="width">Width of the outline.</param>
    /// <param name="lineMiterLimit">Value of the miter for line.</param>
    /// <param name="tailExpEndStyle">Tail line end style.</param>
    /// <param name="headExpEndStyle">Line Head/End Style.</param>
    private static void checkHiddenLineExtension(
        NodeCollection shapes,
        DmlExtension extension,
        int shapeIndex,
        /*DmlDashType*/int dashType,
        /*DashStyle*/int dashStyle,
        String color,
        double width,
        double lineMiterLimit,
        DmlTailLineEndStyle tailExpEndStyle,
        DmlHeadLineEndStyle headExpEndStyle)
    {
        DmlExtension ext = (extension == null) ?
         ((DmlShape)(((Shape)shapes.get(shapeIndex)).getDmlNode())).getSpPrExtensions().get(DmlExtensionUri.HIDDEN_LINE) : extension;

        msAssert.areEqual(ext.getUri(), DmlExtensionUri.HIDDEN_LINE);

        if (!MathUtil.areEqual(0.0, width))
            msAssert.isTrue(MathUtil.areEqual(width, ext.getOutlinePr().getWidthInEmus()));

        DmlFill fill = ext.getOutlinePr().getFill();

        msAssert.areEqual(DmlFillType.SOLID_FILL, fill.getDmlFillType());
        msAssert.areEqual(color, ((DmlHexRgbColor)fill.getDmlColorInternal()).getValue());

        msAssert.areEqual(dashStyle, ext.getOutlinePr().getDashStyle());
        msAssert.areEqual(dashType, ext.getOutlinePr().getDash().getDashType());
        msAssert.isTrue(MathUtil.areEqual(lineMiterLimit, ext.getOutlinePr().getLineMiterLimit()));

        DmlTailLineEndStyle tailEndStyle = ext.getOutlinePr().getTailLineEndStyle();

        if (tailExpEndStyle != null)
        {
            msAssert.areEqual(tailExpEndStyle.getLength(), tailEndStyle.getLength());
            msAssert.areEqual(tailExpEndStyle.getWidth(), tailEndStyle.getWidth());
            msAssert.areEqual(tailExpEndStyle.getType(), tailEndStyle.getType());
        }

        DmlHeadLineEndStyle headEndStyle = ext.getOutlinePr().getHeadLineEndStyle();

        // TODO looks like a misake, maybe headEndStyle should be used below?
        if (headExpEndStyle != null)
        {
            msAssert.areEqual(headExpEndStyle.getLength(), headExpEndStyle.getLength());
            msAssert.areEqual(headExpEndStyle.getWidth(), headExpEndStyle.getWidth());
            msAssert.areEqual(headExpEndStyle.getType(), headExpEndStyle.getType());
        }
    }
}

