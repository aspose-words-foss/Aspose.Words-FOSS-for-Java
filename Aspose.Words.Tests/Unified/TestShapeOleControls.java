// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ImageUtil;
import com.aspose.FileFormat;
import com.aspose.NullableBool;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ArrayUtil;
import com.aspose.ms.System.Drawing.msColor;
import java.awt.Color;
import com.aspose.ms.System.msString;
import com.aspose.StreamUtil;
import org.testng.annotations.DataProvider;


@Test
public class TestShapeOleControls
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests command button OLE control.
    /// </summary>
    @Test
    public void testCommandButton() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Ole\\ActiveX\\TestCommandButton.docm");

        // Test public access.
        Forms2OleControl control = getForms2OleControl(doc, 0);
        testPublicAccess(control, Forms2OleControlType.COMMAND_BUTTON, "Run macro", "");

        // Test internals.
        CommandButtonControl btn1 = (CommandButtonControl)control;
        msAssert.areEqual("Run macro", btn1.getCaption());
        msAssert.isTrue(btn1.getEnabled());

        control = getForms2OleControl(doc, 1);
        testPublicAccess(control, Forms2OleControlType.COMMAND_BUTTON, "Disabled button", "");

        CommandButtonControl btn2 = (CommandButtonControl)control;
        msAssert.areEqual("Disabled button", btn2.getCaption());
        msAssert.isFalse(btn2.getEnabled());
    }

    /// <summary>
    /// Tests image OLE control.
    /// </summary>
    @Test
    public void testImage() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Ole\\ActiveX\\TestImage.docm");

        Forms2OleControl control = getForms2OleControl(doc, 0);
        testPublicAccess(control, Forms2OleControlType.IMAGE, "", "");

        ImageControl image1 = (ImageControl)control;
        msAssert.isNotNull(image1.getPicture());
        msAssert.areEqual(FileFormat.JPEG, ImageUtil.getImageType(image1.getPicture()));
    }

    /// <summary>
    /// Tests OLE frame control and child controls reading.
    /// </summary>
    @Test
    public void testFrame() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Ole\\ActiveX\\TestFrame.docm");

        Forms2OleControl control = getForms2OleControl(doc, 0);
        testPublicAccess(control, Forms2OleControlType.FRAME, "Preferred programming language", "");
        msAssert.areEqual(3, control.getChildNodes().getCount());

        FrameControl frame = (FrameControl)control;
        msAssert.areEqual(3, frame.getChildNodes().getCount());

        OptionButtonControl option1 = (OptionButtonControl)frame.getChildNodes().get(0);
        msAssert.areEqual("C#", option1.getCaption());
        msAssert.areEqual(NullableBool.TRUE, option1.getSelectedInternal());

        OptionButtonControl option2 = (OptionButtonControl)frame.getChildNodes().get(1);
        msAssert.areEqual("Visual Basic", option2.getCaption());
        msAssert.areEqual(NullableBool.FALSE, option2.getSelectedInternal());

        OptionButtonControl option3 = (OptionButtonControl)frame.getChildNodes().get(2);
        msAssert.areEqual("Delphi", option3.getCaption());
        msAssert.areEqual(NullableBool.FALSE, option3.getSelectedInternal());
    }

    /// <summary>
    /// Tests OLE check box control reading.
    /// </summary>
    @Test
    public void testCheckBoxControl() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Ole\\ActiveX\\TestCheckbox.docx");

        Forms2OleControl control = getForms2OleControl(doc, 0);
        testPublicAccess(control, Forms2OleControlType.CHECK_BOX, "Первый", "0");

        CheckBoxControl check1 = (CheckBoxControl)control;
        msAssert.areEqual("Первый", check1.getCaption());
        msAssert.areEqual(NullableBool.FALSE, check1.getCheckedInternal());

        control = getForms2OleControl(doc, 1);
        testPublicAccess(control, Forms2OleControlType.CHECK_BOX, "Второй", "1");

        CheckBoxControl check2 = (CheckBoxControl)control;
        msAssert.areEqual("Второй", check2.getCaption());
        msAssert.areEqual(NullableBool.TRUE, check2.getCheckedInternal());

        control = getForms2OleControl(doc, 2);
        testPublicAccess(control, Forms2OleControlType.CHECK_BOX, "Третий", "1");

        CheckBoxControl check3 = (CheckBoxControl)control;
        msAssert.areEqual("Третий", check3.getCaption());
        msAssert.areEqual(NullableBool.TRUE, check3.getCheckedInternal());
    }

    /// <summary>
    /// Tests OLE option button reading.
    /// </summary>
    @Test
    public void testOptionButton() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Ole\\ActiveX\\TestOptionButton.docx");

        Forms2OleControl control = getForms2OleControl(doc, 0);
        testPublicAccess(control, Forms2OleControlType.OPTION_BUTTON, "Are you happy?", "1");

        // Check internals.
        OptionButtonControl option = (OptionButtonControl)control;
        msAssert.areEqual("Are you happy?", option.getCaption());
        msAssert.areEqual(NullableBool.TRUE, option.getSelectedInternal());
    }

    /// <summary>
    /// Tests OLE tab strip reading.
    /// </summary>
    @Test
    public void testTabStripControl() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Ole\\ActiveX\\TestTabStrip.docx");

        Forms2OleControl control = getForms2OleControl(doc, 0);
        testPublicAccess(control, Forms2OleControlType.TAB_STRIP, "", "");

        TabStripControl tabStrip = (TabStripControl)control;

        // ListIndex is zero based.
        msAssert.areEqual(1, tabStrip.getListIndex());
    }

    /// <summary>
    /// Tests many controls reading.
    /// </summary>
    @Test
    public void testManyControls() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Ole\\ActiveX\\TestManyControls.docx");

        Forms2OleControl control = getForms2OleControl(doc, 0);
        testPublicAccess(control, Forms2OleControlType.CHECK_BOX, "Check this", "0");

        // Test internals.
        CheckBoxControl checkbox = (CheckBoxControl)control;
        msAssert.areEqual(NullableBool.FALSE, checkbox.getCheckedInternal());
        msAssert.isTrue(checkbox.getEnabled());

        control = getForms2OleControl(doc, 1);
        testPublicAccess(control, Forms2OleControlType.TEXTBOX, "", "Enter max 30 chars text");

        TextBoxControl textBox = (TextBoxControl)control;
        msAssert.areEqual("Enter max 30 chars text", textBox.getText());
        msAssert.isTrue(textBox.getEnabled());
    }

    /// <summary>
    /// Tests MultiPageControl reading.
    /// </summary>
    @Test
    public void testMulitPageControl() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Ole\\ActiveX\\TestMultiPage.docm");

        Forms2OleControl control = getForms2OleControl(doc, 0);
        testPublicAccess(control, Forms2OleControlType.MULTI_PAGE, "", "");
        msAssert.areEqual(3, control.getChildNodes().getCount());
        testPublicAccess(control.getChildNodes().get(0), Forms2OleControlType.TAB_STRIP, "", "");
        testPublicAccess(control.getChildNodes().get(1), Forms2OleControlType.FORM, "", "");
        testPublicAccess(control.getChildNodes().get(2), Forms2OleControlType.FORM, "", "");

        // Check internals.
        MultiPageControl multiPage = (MultiPageControl)control;

        // First is always TabStripControl.
        msAssert.areEqual(Forms2OleControlType.TAB_STRIP, multiPage.getChildNodes().get(0).getType());

        // Then few FormControls.
        msAssert.areEqual(Forms2OleControlType.FORM, multiPage.getChildNodes().get(1).getType());
        msAssert.areEqual(Forms2OleControlType.FORM, multiPage.getChildNodes().get(2).getType());

        // Check state of control on second page.
        OptionButtonControl option = (OptionButtonControl)((FormControl)multiPage.getChildNodes().get(2)).getChildNodes().get(0);

        msAssert.areEqual("OptionButton1", option.getCaption());
        msAssert.areEqual(NullableBool.TRUE, option.getSelectedInternal());
    }

    /// <summary>
    /// Tests how controls at few inner levels are read.
    /// </summary>
    @Test
    public void testInnerFrames() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Ole\\ActiveX\\TestInnerFrames.docx");
        Forms2OleControl topFrame = getForms2OleControl(doc, 0);
        testPublicAccess(topFrame, Forms2OleControlType.FRAME, "Frame1", "");
        msAssert.areEqual(15, topFrame.getChildNodes().getCount());

        testPublicAccess(topFrame.getChildNodes().get(0), Forms2OleControlType.LABEL, "Label1", "");
        testPublicAccess(topFrame.getChildNodes().get(1), Forms2OleControlType.TEXTBOX, "", "");
        testPublicAccess(topFrame.getChildNodes().get(2), Forms2OleControlType.COMBO_BOX, "", "");
        testPublicAccess(topFrame.getChildNodes().get(3), Forms2OleControlType.LIST_BOX, "", "");
        testPublicAccess(topFrame.getChildNodes().get(4), Forms2OleControlType.CHECK_BOX, "CheckBox1", "0");
        testPublicAccess(topFrame.getChildNodes().get(5), Forms2OleControlType.OPTION_BUTTON, "OptionButton1", "0");
        testPublicAccess(topFrame.getChildNodes().get(6), Forms2OleControlType.TOGGLE_BUTTON, "ToggleButton1", "0");
        testPublicAccess(topFrame.getChildNodes().get(7), Forms2OleControlType.FRAME, "Frame1", "");
        testPublicAccess(topFrame.getChildNodes().get(8), Forms2OleControlType.COMMAND_BUTTON, "CommandButton1", "");
        testPublicAccess(topFrame.getChildNodes().get(9), Forms2OleControlType.TAB_STRIP, "", "");
        testPublicAccess(topFrame.getChildNodes().get(10), Forms2OleControlType.MULTI_PAGE, "", "");
        testPublicAccess(topFrame.getChildNodes().get(11), Forms2OleControlType.SCROLL_BAR, "", "");
        testPublicAccess(topFrame.getChildNodes().get(12), Forms2OleControlType.SPIN_BUTTON, "", "");
        testPublicAccess(topFrame.getChildNodes().get(13), Forms2OleControlType.IMAGE, "", "");
        testPublicAccess(topFrame.getChildNodes().get(14), Forms2OleControlType.CHECK_BOX, "CheckBox3", "0");

        Forms2OleControl multiPage = topFrame.getChildNodes().get(10);
        msAssert.areEqual(3, multiPage.getChildNodes().getCount());
        testPublicAccess(multiPage.getChildNodes().get(0), Forms2OleControlType.TAB_STRIP, "", "");
        testPublicAccess(multiPage.getChildNodes().get(1), Forms2OleControlType.FORM, "", "");
        testPublicAccess(multiPage.getChildNodes().get(2), Forms2OleControlType.FORM, "", "");
    }

    /// <summary>
    /// WORDSNET-12855 Support creation of HTML Controls.
    /// Added support for creation this group of OLE controls.
    /// </summary>
    @Test
    public void testJira12855() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Stream noImage = ImageUtil.getNoImageStream();
        try /*JAVA: was using*/
        {
            final String HTML_1 = "<INPUT TYPE=\"radio\" NAME=\"FuelLine\" VALUE=\"Y\">";
            builder.insertHtmlOleControl(new HtmlOleControl("DefOcxName1", HtmlOleControlType.OPTION, HTML_1), noImage);

            final String HTML_2 = "<INPUT TYPE=\"radio\" CHECKED NAME=\"FuelLine\" VALUE=\"Y\">";
            builder.insertHtmlOleControl(new HtmlOleControl("DefOcxName2", HtmlOleControlType.OPTION, HTML_2), noImage);

            doc = TestUtil.saveOpen(doc, "Model\\Shape\\Ole\\ActiveX\\TestJira12855.docx", null, false);   // FOSS: was .doc

            // Test first control.
            Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
            msAssert.areEqual(ShapeType.OLE_CONTROL, shape.getShapeType());

            HtmlOleControl htmlOleControl = (HtmlOleControl)shape.getOleFormat().getOleControl();
            msAssert.areEqual(HtmlOleControlType.OPTION, htmlOleControl.getType());
            msAssert.areEqual("<INPUT TYPE=\"radio\" NAME=\"FuelLine\" VALUE=\"Y\">", htmlOleControl.getHtml());

            // Verify control internals.
            OleObject oleObject = ((IEmbeddedObject)htmlOleControl).getOleObject();
            ObjInfoStream objInfo = ObjInfoStream.read(oleObject.getData());
            msAssert.areEqual(OdtPersist1.RECOMPOSE_ON_RESIZE | OdtPersist1.OCX | OdtPersist1.STREAM, objInfo.getFlags1());

            OcxNameStream ocxName = OcxNameStream.read(oleObject.getData());
            msAssert.areEqual("DefOcxName1", ocxName.getValue());
        }
        finally { if (noImage != null) noImage.close(); }
    }

    /// <summary>
    /// WORDSNET-14293 System.InvalidCastException is thrown while convert Docx to Html.
    /// OleFormat.EmbeddedObject can be of two types: OleObject and OoxmlObject.
    /// Should check actual type of OleFormat.EmbeddedObject before casting it
    /// to OleObject in OleFormat.OleControl property.
    /// </summary>
    @Test
    public void testJira14293() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Ole\\ActiveX\\TestJira14293.docx");
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);

        msAssert.isNull(shape.getGraphicData().getOleFormat().getOleControl());
    }

    // FOSS: TestJira17047 removed — it verified cold-rendered predefined images for HTML OLE radio
    // buttons (via the Pdf validator), checked by image-byte hashes; ActiveX/OLE control image
    // rendering is gone.

    /// <summary>
    /// Tests Forms2Ole controls creation and round-trip to different formats with default values.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf/WordML save were removed.
    @Test (dataProvider = "testForms2ControlsCreationDataProvider")
    public void testForms2ControlsCreation(/*SaveFormat*/int sf) throws Exception
    {
        for (/*Forms2OleControlType*/int type : G_ALL_CONTROL_TYPES)
        {
            Document doc = new Document();
            insertControl(doc, type, "ControlName");

            doc = TestUtil.saveOpen(doc, getTestName(type, sf, "Def"), SaveOptions.createSaveOptions(sf), false);
            Forms2OleControl control = getForms2OleControl(doc, 0);

            // Verify type and name of the control.
            checkTypeAndName(control, type, "ControlName");
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testForms2ControlsCreationDataProvider")
	public static Object[][] testForms2ControlsCreationDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Tests Forms2Ole controls modification and round-trip to different formats.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf/WordML save were removed.
    @Test (dataProvider = "testForms2ControlsModificationDataProvider")
    public void testForms2ControlsModification(/*SaveFormat*/int sf) throws Exception
    {
        for (/*Forms2OleControlType*/int type : G_ALL_CONTROL_TYPES)
        {
            Document doc = new Document();
            Forms2OleControl originalControl = insertControl(doc, type, "original name");

            // Change some control properties.
            G_CONTROL_PROPS.copyTo(originalControl.Pr);
            originalControl.setName("NewName");

            // Round-trip modified control and check properties.
            doc = TestUtil.saveOpen(doc, getTestName(type, sf, "Mod"), SaveOptions.createSaveOptions(sf), false);
            Forms2OleControl resavedControl = getForms2OleControl(doc, 0);

            // Verify type and name of the modified control.
            checkTypeAndName(resavedControl, type, "NewName");
            // Verify additional common properties of the modified control.
            checkCommonProps(resavedControl);
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testForms2ControlsModificationDataProvider")
	public static Object[][] testForms2ControlsModificationDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Tests Frame control creation and round-trip to different formats.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf/WordML save were removed.
    @Test (dataProvider = "testFrameCreationDataProvider")
    public void testFrameCreation(/*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        FrameControl frame = (FrameControl)insertControl(doc, Forms2OleControlType.FRAME, "Frame1");
        CheckBoxControl checkBox = new CheckBoxControl("CheckBox1", "CheckBox caption.", NullableBool.TRUE);
        frame.getChildNodes().add(checkBox);

        String testName = getTestName(Forms2OleControlType.FRAME, sf, "");
        doc = TestUtil.saveOpen(doc, testName, SaveOptions.createSaveOptions(sf), false);

        frame = (FrameControl)getForms2OleControl(doc, 0);
        msAssert.areEqual("Frame1", frame.getName());

        checkBox = (CheckBoxControl)frame.getChildNodes().get(0);
        msAssert.areEqual(NullableBool.TRUE, checkBox.getCheckedInternal());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFrameCreationDataProvider")
	public static Object[][] testFrameCreationDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Tests TabStrip control creation and round-trip to different formats.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf/WordML save were removed.
    @Test (dataProvider = "testTabStripCreationDataProvider")
    public void testTabStripCreation(/*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        TabStripControl control = (TabStripControl)insertControl(doc, Forms2OleControlType.TAB_STRIP, "TabStrip00001");
        control.getTabs().add(new TabStripTab("first"));

        control.getTabs().add(new TabStripTab("second"));
        control.getTabs().get(1).setEnabled(false);

        control.getTabs().add(new TabStripTab("third"));
        control.getTabs().get(2).setVisible(false);
        control.getTabs().get(2).setEnabled(false);

        String testName = getTestName(Forms2OleControlType.TAB_STRIP, sf, "");
        doc = TestUtil.saveOpen(doc, testName, SaveOptions.createSaveOptions(sf), false);

        control = (TabStripControl)getForms2OleControl(doc, 0);

        msAssert.areEqual("TabStrip00001", control.getName());

        msAssert.areEqual(3, control.getTabs().size());

        checkTab(control.getTabs().get(0), true, true, "first");
        checkTab(control.getTabs().get(1), true, false, "second");
        checkTab(control.getTabs().get(2), false, false, "third");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTabStripCreationDataProvider")
	public static Object[][] testTabStripCreationDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Tests CheckBox control creation and round-trip to different formats.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf/WordML save were removed.
    @Test (dataProvider = "testCheckBoxCreationDataProvider")
    public void testCheckBoxCreation(/*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        CheckBoxControl control = (CheckBoxControl)insertControl(doc, Forms2OleControlType.CHECK_BOX, "ChkBox01");

        control.setCheckedInternal(NullableBool.TRUE);

        String testName = getTestName(Forms2OleControlType.CHECK_BOX, sf, "");
        doc = TestUtil.saveOpen(doc, testName, SaveOptions.createSaveOptions(sf), false);

        control = (CheckBoxControl)getForms2OleControl(doc, 0);

        msAssert.areEqual("ChkBox01", control.getName());
        msAssert.areEqual(NullableBool.TRUE, control.getCheckedInternal());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testCheckBoxCreationDataProvider")
	public static Object[][] testCheckBoxCreationDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Tests Image control creation and round-trip to different formats.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf/WordML save were removed.
    @Test (dataProvider = "testImageCreationDataProvider")
    public void testImageCreation(/*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        ImageControl control = (ImageControl)insertControl(doc, Forms2OleControlType.IMAGE, "Img01");

        control.setPicture(G_PICTURE);
        control.setPictureAlignment(PictureAlignment.BOTTOM_RIGHT);
        control.setPictureSizeMode(PictureSizeMode.ZOOM);
        control.setPictureTiling(true);

        String testName = getTestName(Forms2OleControlType.IMAGE, sf, "");
        doc = TestUtil.saveOpen(doc, testName, SaveOptions.createSaveOptions(sf), false);

        control = (ImageControl)getForms2OleControl(doc, 0);

        msAssert.areEqual("Img01", control.getName());
        msAssert.isTrue(ArrayUtil.isArrayEqual(control.getPicture(), G_PICTURE));
        msAssert.areEqual(PictureAlignment.BOTTOM_RIGHT, control.getPictureAlignment());
        msAssert.areEqual(PictureSizeMode.ZOOM, control.getPictureSizeMode());
        msAssert.isTrue(control.getPictureTiling());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testImageCreationDataProvider")
	public static Object[][] testImageCreationDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Tests OptionButton control creation and round-trip to different formats.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf/WordML save were removed.
    @Test (dataProvider = "testOptionButtonCreationDataProvider")
    public void testOptionButtonCreation(/*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        OptionButtonControl control = (OptionButtonControl)insertControl(doc, Forms2OleControlType.OPTION_BUTTON, "OptBtn01");

        control.setSelectedInternal(NullableBool.TRUE);
        control.setGroupName("Group1");

        String testName = getTestName(Forms2OleControlType.OPTION_BUTTON, sf, "");
        doc = TestUtil.saveOpen(doc, testName, SaveOptions.createSaveOptions(sf), false);

        control = (OptionButtonControl)getForms2OleControl(doc, 0);

        msAssert.areEqual("OptBtn01", control.getName());
        msAssert.areEqual(NullableBool.TRUE, control.getSelectedInternal());
        msAssert.areEqual("Group1", control.getGroupName());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testOptionButtonCreationDataProvider")
	public static Object[][] testOptionButtonCreationDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Tests TextBox control creation and round-trip to different formats.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf/WordML save were removed.
    @Test (dataProvider = "testTextBoxCreationDataProvider")
    public void testTextBoxCreation(/*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        TextBoxControl control = (TextBoxControl)insertControl(doc, Forms2OleControlType.TEXTBOX, "Text001");

        control.setText("Test text.");
        control.setBackColor(msColor.getAliceBlue());
        control.setForeColor(msColor.getBlueViolet());

        control.setWidth(144.5);
        control.setHeight(72.3);

        String testName = getTestName(Forms2OleControlType.TEXTBOX, sf, "");
        doc = TestUtil.saveOpen(doc, testName, SaveOptions.createSaveOptions(sf), false);

        control = (TextBoxControl)getForms2OleControl(doc, 0);

        msAssert.areEqual("Text001", control.getName());
        msAssert.areEqual("Test text.", control.getText());
        msAssert.areEqual(msColor.getAliceBlue().getRGB(), control.getBackColor().getRGB());
        msAssert.areEqual(msColor.getBlueViolet().getRGB(), control.getForeColor().getRGB());

        msAssert.areEqual(144.5, 0.1, control.getWidth());
        msAssert.areEqual(72.3, 0.1, control.getHeight());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTextBoxCreationDataProvider")
	public static Object[][] testTextBoxCreationDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Tests SpinButton control creation and round-trip to different formats.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf/WordML save were removed.
    @Test (dataProvider = "testSpinButtonCreationDataProvider")
    public void testSpinButtonCreation(/*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        SpinButtonControl control = (SpinButtonControl)insertControl(doc, Forms2OleControlType.SPIN_BUTTON, "SpIn B T N 9");

        control.setMin(150);
        control.setMax(1000);
        control.setPosition(999);

        String testName = getTestName(Forms2OleControlType.SPIN_BUTTON, sf, "");
        doc = TestUtil.saveOpen(doc, testName, SaveOptions.createSaveOptions(sf), false);

        control = (SpinButtonControl)getForms2OleControl(doc, 0);

        msAssert.areEqual("SpIn B T N 9", control.getName());
        msAssert.areEqual(150, control.getMin());
        msAssert.areEqual(1000, control.getMax());
        msAssert.areEqual(999, control.getPosition());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSpinButtonCreationDataProvider")
	public static Object[][] testSpinButtonCreationDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Tests ScrollBar control creation and round-trip to different formats.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf/WordML save were removed.
    @Test (dataProvider = "testScrollBarCreationDataProvider")
    public void testScrollBarCreation(/*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        ScrollBarControl control = (ScrollBarControl)insertControl(doc, Forms2OleControlType.SCROLL_BAR, "ScrL00101");

        control.setMin(100);
        control.setMax(200);
        control.setPosition(140);

        String testName = getTestName(Forms2OleControlType.SCROLL_BAR, sf, "");
        doc = TestUtil.saveOpen(doc, testName, SaveOptions.createSaveOptions(sf), false);

        control = (ScrollBarControl)getForms2OleControl(doc, 0);

        msAssert.areEqual("ScrL00101", control.getName());
        msAssert.areEqual(100, control.getMin());
        msAssert.areEqual(200, control.getMax());
        msAssert.areEqual(140, control.getPosition());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testScrollBarCreationDataProvider")
	public static Object[][] testScrollBarCreationDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Tests various properties of a scrollable control.
    /// </summary>
    @Test
    public void testScrollableControlVariousProperties() throws Exception
    {
        Document doc = new Document();
        ScrollableControlBase control = (ScrollableControlBase)insertControl(doc, Forms2OleControlType.SPIN_BUTTON, "");

        // Check default values.
        msAssert.areEqual(0, control.getMin());
        msAssert.areEqual(100, control.getMax());
        msAssert.areEqual(0, control.getPosition());

        // It is allowed to set min > max, the same as in Word.
        control.setMin(150);
        msAssert.areEqual(150, control.getMin());
        msAssert.areEqual(100, control.getMax());
        // Position is adjusted automatically to fit in [Min, Max] range.
        msAssert.areEqual(100, control.getPosition());

        control.setMax(1000);
        // Position is adjusted automatically to fit in [Min, Max] range.
        msAssert.areEqual(150, control.getPosition());
    }

    /// <summary>
    /// Tests exception is thrown when Position property is set out of [Min, Max] range.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testScrollableControlPositionOutOfRangeException() throws Exception
    {
        Document doc = new Document();
        ScrollableControlBase control = (ScrollableControlBase)insertControl(doc, Forms2OleControlType.SPIN_BUTTON, "");

        // Check default values.
        msAssert.areEqual(0, control.getMin());
        msAssert.areEqual(100, control.getMax());

        // Throws an exception.
        control.setPosition(101);
    }

    /// <summary>
    /// Tests ListBox control creation and round-trip to different formats.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf/WordML save were removed.
    @Test (dataProvider = "testListBoxCreationDataProvider")
    public void testListBoxCreation(/*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        ListBoxControl control = (ListBoxControl)insertControl(doc, Forms2OleControlType.LIST_BOX, "list1");

        control.setValue("item");
        control.setWidth(300.0);
        control.setHeight(450.0);

        String testName = getTestName(Forms2OleControlType.LIST_BOX, sf, "");
        doc = TestUtil.saveOpen(doc, testName, SaveOptions.createSaveOptions(sf), false);

        control = (ListBoxControl)getForms2OleControl(doc, 0);

        msAssert.areEqual("list1", control.getName());
        msAssert.areEqual("item", control.getSelectedValue());
        msAssert.areEqual(300, 0.1, control.getWidth());
        msAssert.areEqual(450, 0.1, control.getHeight());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testListBoxCreationDataProvider")
	public static Object[][] testListBoxCreationDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Tests ComboBox control creation and round-trip to different formats.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf/WordML save were removed.
    @Test (dataProvider = "testComboBoxCreationDataProvider")
    public void testComboBoxCreation(/*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        ComboBoxControl control = (ComboBoxControl)insertControl(doc, Forms2OleControlType.COMBO_BOX, "cmb00001");

        control.setValue("item");
        control.setWidth(116.45);
        control.setHeight(75.88);

        String testName = getTestName(Forms2OleControlType.COMBO_BOX, sf, "");
        doc = TestUtil.saveOpen(doc, testName, SaveOptions.createSaveOptions(sf), false);

        control = (ComboBoxControl)getForms2OleControl(doc, 0);

        msAssert.areEqual("cmb00001", control.getName());
        msAssert.areEqual("item", control.getSelectedValue());
        msAssert.areEqual(116.45, 0.1, control.getWidth());
        msAssert.areEqual(75.88, 0.1, control.getHeight());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testComboBoxCreationDataProvider")
	public static Object[][] testComboBoxCreationDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Tests MultiPage control creation and round-trip to different formats.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf/WordML save were removed.
    @Test (dataProvider = "testMultiPageCreationDataProvider")
    public void testMultiPageCreation(/*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        MultiPageControl control = (MultiPageControl)insertControl(doc, Forms2OleControlType.MULTI_PAGE, "m p 01");
        control.setCaption("MP");
        control.setBackColor(msColor.getBisque());
        control.setWidth(222.0);
        control.setHeight(111.0);

        // Round-trip control.
        String testName = getTestName(Forms2OleControlType.MULTI_PAGE, sf, "");
        doc = TestUtil.saveOpen(doc, testName, SaveOptions.createSaveOptions(sf), false);

        control = (MultiPageControl)getForms2OleControl(doc, 0);

        msAssert.areEqual("m p 01", control.getName());
        msAssert.areEqual("MP", control.getCaption());
        msAssert.areEqual(msColor.getBisque().getRGB(), control.getBackColor().getRGB());

        msAssert.areEqual(222, 0.1, control.getWidth());
        msAssert.areEqual(111, 0.1, control.getHeight());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testMultiPageCreationDataProvider")
	public static Object[][] testMultiPageCreationDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Tests MultiPage control simple page manipulations with round-trip to different formats.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf/WordML save were removed.
    @Test (dataProvider = "testMultiPageManipulationDataProvider")
    public void testMultiPageManipulation(/*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        MultiPageControl control = (MultiPageControl)insertControl(doc, Forms2OleControlType.MULTI_PAGE, "MultiPage1");
        control.setWidth(200.0);
        control.setHeight(200.0);

        FormControl page1 = control.getPages().add(new FormControl("Page with CheckBox"));
        FormControl page2 = control.getPages().add(new FormControl("Page with Label"));

        CheckBoxControl checkBox = new CheckBoxControl("CheckBox01", "Check me.", NullableBool.TRUE);
        checkBox.setBackStyle(BackStyle.TRANSPARENT);
        checkBox.setTop(90.0);
        checkBox.setLeft(110.0);

        LabelControl label = new LabelControl("Label01");
        label.setCaption("Some text.");
        label.setTop(150.0);
        label.setLeft(40.0);

        page1.getChildNodes().add(checkBox);
        page2.getChildNodes().add(label);

        control.getPages().add(new FormControl("Some extra page."));
        msAssert.areEqual(3, control.getPages().getCount());

        control.getPages().remove(control.getPages().get(control.getPages().getCount() - 1));
        msAssert.areEqual(2, control.getPages().getCount());

        // Round-trip control.
        String testName = getTestName(Forms2OleControlType.MULTI_PAGE, sf, "Manipulation");
        doc = TestUtil.saveOpen(doc, testName, SaveOptions.createSaveOptions(sf));

        control = (MultiPageControl)getForms2OleControl(doc, 0);

        msAssert.areEqual(2, control.getPages().getCount());
        msAssert.areEqual(OlePosition.fromPoints(90.0, 110.0), control.getPages().get(0).getChildNodes().get(0).getSitePosition());
        msAssert.areEqual(OlePosition.fromPoints(150.0, 40.0), control.getPages().get(1).getChildNodes().get(0).getSitePosition());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testMultiPageManipulationDataProvider")
	public static Object[][] testMultiPageManipulationDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Tests MultiPage control nested.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf/WordML save were removed.
    @Test (dataProvider = "testMultiPageNestedDataProvider")
    public void testMultiPageNested(/*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Ole\\ActiveX\\TestMultiPageNested", LoadFormat.DOCX, sf);

        MultiPageControl multiPageControl = (MultiPageControl)getForms2OleControl(doc, 0);
        // Three children: [0] - TabStrip, [1]- Page1, [2] - Page2.
        msAssert.areEqual(3, multiPageControl.getChildNodes().getCount());

        // Get second page, that contains inner MultiPage control.
        FormControl page = (FormControl)multiPageControl.getChildNodes().get(2);

        // This is inner MultiPage control, that resides on the second page of the parent MultiPage control.
        multiPageControl = (MultiPageControl)page.getChildNodes().get(0);
        // Three children: [0] - TabStrip, [1]- Page1, [2] - Page2.
        msAssert.areEqual(3, multiPageControl.getChildNodes().getCount());

        // Get first page of inner MultiPage control.
        page = (FormControl)multiPageControl.getChildNodes().get(1);

        // This TextBox resides on the first page of the inner MultiPage control.
        TextBoxControl textBox = (TextBoxControl)page.getChildNodes().get(0);
        msAssert.areEqual("TextBox1", textBox.getName());

        // Get second page of inner MultiPage control, that is empty.
        page = (FormControl)multiPageControl.getChildNodes().get(2);
        msAssert.areEqual(0, page.getChildNodes().getCount());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testMultiPageNestedDataProvider")
	public static Object[][] testMultiPageNestedDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Tests Label control creation and round-trip to different formats.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf/WordML save were removed.
    @Test (dataProvider = "testLabelCreationDataProvider")
    public void testLabelCreation(/*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        LabelControl control = (LabelControl)insertControl(doc, Forms2OleControlType.LABEL, "lbl01");
        control.setCaption("L a B e L caption");
        control.setForeColor(msColor.getChocolate());

        // Round-trip control.
        String testName = getTestName(Forms2OleControlType.LABEL, sf, "");
        doc = TestUtil.saveOpen(doc, testName, SaveOptions.createSaveOptions(sf), false);

        control = (LabelControl)getForms2OleControl(doc, 0);

        msAssert.areEqual("lbl01", control.getName());
        msAssert.areEqual("L a B e L caption", control.getCaption());
        msAssert.areEqual(msColor.getChocolate().getRGB(), control.getForeColor().getRGB());

        // This is default Label control size.
        msAssert.areEqual(72, 0.1, control.getWidth());
        msAssert.areEqual(18, 0.1, control.getHeight());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testLabelCreationDataProvider")
	public static Object[][] testLabelCreationDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Tests CommandButton control creation and round-trip to different formats.
    /// </summary>
    // FOSS: only Docx survives — Doc/Rtf/WordML save were removed.
    @Test (dataProvider = "testCommandButtonCreationDataProvider")
    public void testCommandButtonCreation(/*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        CommandButtonControl control = (CommandButtonControl)insertControl(doc, Forms2OleControlType.COMMAND_BUTTON, "cmnd0001");
        control.setCaption("C o m m a BTN");
        control.setAccelerator('S');
        control.setBackColor(msColor.getChartreuse());
        control.setBackStyle(BackStyle.TRANSPARENT);
        control.setWidth(251.25);
        control.setHeight(84.0);
        control.setLocked(true);
        // Round-trip control.
        String testName = getTestName(Forms2OleControlType.COMMAND_BUTTON, sf, "");
        doc = TestUtil.saveOpen(doc, testName, SaveOptions.createSaveOptions(sf), false);

        control = (CommandButtonControl)getForms2OleControl(doc, 0);

        msAssert.areEqual("cmnd0001", control.getName());
        msAssert.areEqual("C o m m a BTN", control.getCaption());
        msAssert.areEqual('S', control.getAccelerator());
        msAssert.areEqual(msColor.getChartreuse().getRGB(), control.getBackColor().getRGB());
        msAssert.areEqual(BackStyle.TRANSPARENT, control.getBackStyle());
        msAssert.areEqual(251.25, 0.1, control.getWidth());
        msAssert.areEqual(84, 0.1, control.getHeight());
        msAssert.isTrue(control.getLocked());
        // We did not set it, but this is 'true' by default.
        msAssert.isTrue(control.getTakeFocusOnClick());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testCommandButtonCreationDataProvider")
	public static Object[][] testCommandButtonCreationDataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX},
		};
	}

    /// <summary>
    /// Tests many complex Forms2Ole controls round-trip to different formats.
    /// </summary>
    // FOSS: only Docx2Docx survives — Doc/Rtf/WordML load+save were removed.
    @Test (dataProvider = "testManyControlsComplexDataProvider")
    public void testManyControlsComplex(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Shape\\Ole\\ActiveX\\TestManyControls", scenario);
        msAssert.areEqual(13, doc.getFirstSection().getBody().getShapes().getCount());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testManyControlsComplexDataProvider")
	public static Object[][] testManyControlsComplexDataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.DOCX_2_DOCX},
		};
	}

    /// <summary>
    /// Tests OLE controls with triple states.
    /// </summary>
    @Test
    public void testTripleStateControls() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Ole\\ActiveX\\TestTripleStateControls.docx");

        // Check original state of the first checkbox.
        Forms2OleControl control = getForms2OleControl(doc, 0);
        testPublicAccess(control, Forms2OleControlType.CHECK_BOX, "Первый", "1");
        CheckBoxControl checkBoxControl = (CheckBoxControl)control;
        msAssert.areEqual(NullableBool.TRUE, checkBoxControl.getCheckedInternal());
        // Change state.
        checkBoxControl.setCheckedInternal(NullableBool.NOT_DEFINED);

        // Check original state of the second checkbox.
        control = getForms2OleControl(doc, 1);
        testPublicAccess(control, Forms2OleControlType.CHECK_BOX, "Второй", "2");
        checkBoxControl = (CheckBoxControl)control;
        msAssert.areEqual(NullableBool.NOT_DEFINED, checkBoxControl.getCheckedInternal());
        // Change state.
        checkBoxControl.setCheckedInternal(NullableBool.FALSE);

        // Check original state of the first option button.
        control = getForms2OleControl(doc, 2);
        testPublicAccess(control, Forms2OleControlType.OPTION_BUTTON, "Опция1", "1");
        OptionButtonControl optionButtonControl = (OptionButtonControl)control;
        msAssert.areEqual(NullableBool.TRUE, optionButtonControl.getSelectedInternal());
        // Change state.
        optionButtonControl.setSelectedInternal(NullableBool.NOT_DEFINED);

        // Check original state of the second option button.
        control = getForms2OleControl(doc, 3);
        testPublicAccess(control, Forms2OleControlType.OPTION_BUTTON, "Опция2", "2");
        optionButtonControl = (OptionButtonControl)control;
        msAssert.areEqual(NullableBool.NOT_DEFINED, optionButtonControl.getSelectedInternal());
        // Change state.
        optionButtonControl.setSelectedInternal(NullableBool.FALSE);

        // Check original state of the first toggle button.
        control = getForms2OleControl(doc, 4);
        testPublicAccess(control, Forms2OleControlType.TOGGLE_BUTTON, "Кнопка1", "1");
        ToggleButtonControl toggleButtonControl = (ToggleButtonControl)control;
        msAssert.areEqual(NullableBool.TRUE, toggleButtonControl.getChecked());
        // Change state.
        toggleButtonControl.setChecked(NullableBool.NOT_DEFINED);

        // Check original state of the second toogle button.
        control = getForms2OleControl(doc, 5);
        testPublicAccess(control, Forms2OleControlType.TOGGLE_BUTTON, "Кнопка2", "2");
        toggleButtonControl = (ToggleButtonControl)control;
        msAssert.areEqual(NullableBool.NOT_DEFINED, toggleButtonControl.getChecked());
        // Change state.
        toggleButtonControl.setChecked(NullableBool.FALSE);

        // Round-trip the document.
        /*UnifiedScenario*/int scenario = TestUtil.buildScenario(LoadFormat.DOCX, SaveFormat.DOCX, false);
        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Ole\\ActiveX\\TestTripleStateControls", scenario);

        // Check new states of the all controls.
        checkBoxControl = (CheckBoxControl)getForms2OleControl(doc, 0);
        msAssert.areEqual(NullableBool.NOT_DEFINED, checkBoxControl.getCheckedInternal());

        checkBoxControl = (CheckBoxControl)getForms2OleControl(doc, 1);
        msAssert.areEqual(NullableBool.FALSE, checkBoxControl.getCheckedInternal());

        optionButtonControl = (OptionButtonControl)getForms2OleControl(doc, 2);
        msAssert.areEqual(NullableBool.NOT_DEFINED, optionButtonControl.getSelectedInternal());

        optionButtonControl = (OptionButtonControl)getForms2OleControl(doc, 3);
        msAssert.areEqual(NullableBool.FALSE, optionButtonControl.getSelectedInternal());

        toggleButtonControl = (ToggleButtonControl)getForms2OleControl(doc, 4);
        msAssert.areEqual(NullableBool.NOT_DEFINED, toggleButtonControl.getChecked());

        toggleButtonControl = (ToggleButtonControl)getForms2OleControl(doc, 5);
        msAssert.areEqual(NullableBool.FALSE, toggleButtonControl.getChecked());
    }

    /// <summary>
    /// WORDSNET-25165 Allow accessing OLE Control Name and GroupName.
    /// The corresponding properties are made public.
    /// </summary>
    @Test
    public void test25165() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Ole\\ActiveX\\Test25165.docx");
        Forms2OleControl control = (Forms2OleControl)doc.getFirstSection().getBody().getShapes().get(0).getOleFormat().getOleControl();

        msAssert.areEqual("OptionButton1", control.getName());
        msAssert.areEqual("TestGroup", control.getGroupName());

        // Change properties values.
        control.setName("MyOption1");
        msAssert.areEqual("MyOption1", control.getName());

        control.setGroupName("MyGroup");
        msAssert.areEqual("MyGroup", control.getGroupName());

        // Round-trip the document and check values again.
        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Ole\\ActiveX\\Test25165", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        control = (OptionButtonControl)doc.getFirstSection().getBody().getShapes().get(0).getOleFormat().getOleControl();

        msAssert.areEqual("MyOption1", control.getName());
        msAssert.areEqual("MyGroup", control.getGroupName());

        // Check all related types and their properties are exposed publicly.
    }

    /// <summary>
    /// Relates to WORDSNET-25165
    /// Tests default value and setting null value.
    /// </summary>
    @Test (expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "\\QValue cannot be null.\r\nParameter name: GroupName\\E")
    public void test25165NullGroupName() throws Exception
    {
        Document doc = new Document();
        Forms2OleControl control = insertControl(doc, Forms2OleControlType.OPTION_BUTTON, "MyControl");

        // Check default value.
        msAssert.areEqual("", control.getGroupName());

        // Try to set null.
        control.setGroupName(null);
        msAssert.isNotNull(control.getGroupName());
    }



    /// <summary>
    /// WORDSNET-13551 Provide ability to modify value of radio button ActiveX control.
    /// Added public property <see cref="OptionButtonControl.Selected"/>.
    /// </summary>
    @Test
    public void test13551() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Ole\\ActiveX\\Test13551.docx");
        OptionButtonControl optionButton1 = (OptionButtonControl)doc.getFirstSection().getBody().getShapes().get(0).getOleFormat().getOleControl();
        OptionButtonControl optionButton2 = (OptionButtonControl)doc.getFirstSection().getBody().getShapes().get(1).getOleFormat().getOleControl();
        OptionButtonControl optionButton3 = (OptionButtonControl)doc.getFirstSection().getBody().getShapes().get(2).getOleFormat().getOleControl();

        msAssert.isFalse(optionButton1.getSelected());
        msAssert.isFalse(optionButton2.getSelected());
        msAssert.isTrue(optionButton3.getSelected());

        // Select another one option button, so that there will be two selected items at the same time.
        optionButton2.setSelected(true);
        msAssert.isFalse(optionButton1.getSelected());
        msAssert.isTrue(optionButton2.getSelected());
        msAssert.isTrue(optionButton3.getSelected());

        // Deselect previously selected item.
        optionButton3.setSelected(false);

        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Ole\\ActiveX\\Test13551", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        optionButton1 = (OptionButtonControl)doc.getFirstSection().getBody().getShapes().get(0).getOleFormat().getOleControl();
        msAssert.isFalse(optionButton1.getSelected());
        optionButton2 = (OptionButtonControl)doc.getFirstSection().getBody().getShapes().get(1).getOleFormat().getOleControl();
        msAssert.isTrue(optionButton2.getSelected());
        optionButton3 = (OptionButtonControl)doc.getFirstSection().getBody().getShapes().get(2).getOleFormat().getOleControl();
        msAssert.isFalse(optionButton3.getSelected());

    }

    /// <summary>
    /// WORDSNET-12826 Provide ability to set a given ActiveX checkbox to checked or unchecked.
    /// Added public property <see cref="CheckBoxControl.Checked"/>.
    /// </summary>
    @Test
    public void test12826() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Ole\\ActiveX\\Test12826.docx");
        CheckBoxControl checkBoxControl = (CheckBoxControl)doc.getFirstSection().getBody().getShapes().get(0).getOleFormat().getOleControl();

        msAssert.isFalse(checkBoxControl.getChecked());

        checkBoxControl.setChecked(true);
        msAssert.isTrue(checkBoxControl.getChecked());

        // Check roundtrip.
        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Ole\\ActiveX\\Test12826", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        checkBoxControl = (CheckBoxControl)doc.getFirstSection().getBody().getShapes().get(0).getOleFormat().getOleControl();
        msAssert.isTrue(checkBoxControl.getChecked());

        checkBoxControl.setChecked(false);
        msAssert.isFalse(checkBoxControl.getChecked());

        // Check also undefined state.
        checkBoxControl.setCheckedInternal(NullableBool.NOT_DEFINED);
        msAssert.isFalse(checkBoxControl.getChecked());

    }

    /// <summary>
    /// WORDSNET-16356 Add feature to create CommandButton ActiveX.
    /// <see cref="DocumentBuilder.InsertForms2OleControl"/> is made public.
    /// </summary>
    @Test
    public void test16356() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        CommandButtonControl button1 = new CommandButtonControl(); { button1.setCaption("AAA"); }
        builder.insertForms2OleControl(button1);
        msAssert.areEqual("AAA", button1.getCaption());

        CommandButtonControl button2 = new CommandButtonControl(); { button2.setCaption("BBB"); }
        builder.insertForms2OleControl(button2);
        msAssert.areEqual("BBB", button2.getCaption());

        final String OUT_FILE = "Model\\Shape\\Ole\\ActiveX\\Test16356";
        Document doc = TestUtil.saveOpen(builder.getDocument(), OUT_FILE, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        button1 = (CommandButtonControl)doc.getFirstSection().getBody().getShapes().get(0).getOleFormat().getOleControl();
        msAssert.areEqual("AAA", button1.getCaption());

        button2 = (CommandButtonControl)doc.getFirstSection().getBody().getShapes().get(1).getOleFormat().getOleControl();
        msAssert.areEqual("BBB", button2.getCaption());

    }

    /// <summary>
    /// WORDSNET-27435 Add possibility to set caption of CommandButton ActiveX control.
    /// <see cref="Forms2OleControl.Caption"/> setter is made public.
    /// </summary>
    @Test
    public void test27435() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        CommandButtonControl button = new CommandButtonControl(); { button.setCaption("AAA"); }
        builder.insertForms2OleControl(button);
        msAssert.areEqual("AAA", button.getCaption());

    }


    /// <summary>
    /// Checks some common for all types of controls properties set from <see cref="gControlProps"/>:
    /// </summary>
    private static void checkCommonProps(Forms2OleControl control)
    {
        msAssert.areEqual(G_BACKGROUND_COLOR, OleColor.fromColor(control.getBackColor()), msString.format("{0}: BackColor", control.getType()));
        msAssert.areEqual(G_SIZE, control.getSize(), msString.format("{0}: Size", control.getType()));
        msAssert.areEqual(MousePointer.CUSTOM, control.getMousePointer(), msString.format("{0}: MousePointer", control.getType()));
        msAssert.isTrue(ArrayUtil.isArrayEqual(G_MOUSE_ICON, control.getMouseIcon()), msString.format("{0}: MouseIcon", control.getType()));
    }

    /// <summary>
    /// Gets test name depending on Forms2OleControl type relative to "Model\Shape\Ole\ActiveX\" path.
    /// </summary>
    private static String getTestName(/*Forms2OleControlType*/int controlType, /*SaveFormat*/int sf, String suffix)
    {
        final String TEST_FOLDER = "Model\\Shape\\Ole\\ActiveX\\";

        String ext = FileFormatUtil.saveFormatToExtension(sf);
        String fileName = msString.format("{0}Control", OleUtil.toString(controlType));

        return msString.format("{0}{1}{2}{3}", TEST_FOLDER, fileName, suffix, ext);
    }

    /// <summary>
    /// Creates and inserts Forms2OleControl into a document.
    /// </summary>
    private static Forms2OleControl insertControl(Document doc, /*Forms2OleControlType*/int type, String name)
    {
        DocumentBuilder builder = new DocumentBuilder(doc);
        Forms2OleControl forms2OleControl = Forms2OleControl.create(type, name);
        builder.insertForms2OleControl(forms2OleControl);

        return forms2OleControl;
    }

    /// <summary>
    /// Checks type and name of Forms2OleControl.
    /// </summary>
    private static void checkTypeAndName(Forms2OleControl control, /*Forms2OleControlType*/int expectedType, String expectedName)
    {
        msAssert.areEqual(expectedType, control.getType(), "Type");
        msAssert.areEqual(expectedName, control.getName(), "Name");
    }

    /// <summary>
    /// Returns Nth Forms2OleControl from document.
    /// </summary>
    private static Forms2OleControl getForms2OleControl(Document doc, int idx) throws Exception
    {
        Shape shape = (Shape)doc.getFirstSection().getBody().getChild(NodeType.SHAPE, idx, true);

        OleControl oleControl = shape.getOleFormat().getOleControl();
        msAssert.isTrue(oleControl instanceof Forms2OleControl);

        return (Forms2OleControl)oleControl;
    }

    /// <summary>
    /// Checks publicly accessed attributes of Forms2OleControl.
    /// </summary>
    private static void testPublicAccess(Forms2OleControl forms2Ole, /*Forms2OleControlType*/int type, String caption,
        String value)
    {
        msAssert.areEqual(type, forms2Ole.getType());
        msAssert.areEqual(caption, forms2Ole.getCaption());
        msAssert.areEqual(value, forms2Ole.getValue());
    }

    /// <summary>
    /// Checks Tab of TabStripControl.
    /// </summary>
    private static void checkTab(TabStripTab tab, boolean expectedVisible, boolean expectedEnabled, String expectedCaption)
    {
        msAssert.areEqual(expectedVisible, tab.getVisible(), "Visible");
        msAssert.areEqual(expectedEnabled, tab.getEnabled(), "Enabled");
        msAssert.areEqual(expectedCaption, tab.getCaption(), "Caption");
    }

    /// <summary>
    /// Predefined set of Forms2OleControl properties.
    /// </summary>
    private static /*final*/ Forms2Pr G_CONTROL_PROPS;
    private static /*final*/ byte[] G_MOUSE_ICON;
    private static /*final*/ byte[] G_PICTURE;
    private static /*final*/ OleColor G_BACKGROUND_COLOR = OleColor.fromColor(msColor.getGreen());
    private static /*final*/ OleColor G_FORE_COLOR = OleColor.fromColor(Color.RED);
    private static /*final*/ OleSize G_SIZE = OleSize.fromPoints(233.0, 115.0);

    /// <summary>
    /// An array of Forms2OleControl types that will be tested.
    /// </summary>
    private static /*final*/ /*Forms2OleControlType*/int[] G_ALL_CONTROL_TYPES = new /*Forms2OleControlType*/int[]
    {
        Forms2OleControlType.OPTION_BUTTON,
        Forms2OleControlType.LABEL,
        Forms2OleControlType.TEXTBOX,
        Forms2OleControlType.CHECK_BOX,
        Forms2OleControlType.TOGGLE_BUTTON,
        Forms2OleControlType.SPIN_BUTTON,
        Forms2OleControlType.COMBO_BOX,
        Forms2OleControlType.FRAME,
        Forms2OleControlType.MULTI_PAGE,
        Forms2OleControlType.TAB_STRIP,
        Forms2OleControlType.COMMAND_BUTTON,
        Forms2OleControlType.IMAGE,
        Forms2OleControlType.SCROLL_BAR,
        Forms2OleControlType.LIST_BOX
    };

    static/* TestShapeOleControls()*/
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	G_MOUSE_ICON = StreamUtil.copyFileToByteArray(TestUtil.buildTestFileName("Model\\Drawing\\Core\\icon_02.ico"));
        	G_PICTURE = StreamUtil.copyFileToByteArray(TestUtil.buildTestFileName("Model\\Shape\\Ole\\InsertOleObject\\Image.bmp"));

        	G_CONTROL_PROPS = new Forms2Pr();
        	G_CONTROL_PROPS.add(Forms2Attr.BACKGROUND_COLOR, G_BACKGROUND_COLOR);
        	G_CONTROL_PROPS.add(Forms2Attr.FOREGROUND_COLOR, G_FORE_COLOR);
        	G_CONTROL_PROPS.add(Forms2Attr.SIZE, G_SIZE);
        	G_CONTROL_PROPS.add(Forms2Attr.MOUSE_ICON, G_MOUSE_ICON);
        	G_CONTROL_PROPS.add(Forms2Attr.MOUSE_POINTER, MousePointer.CUSTOM);
        	G_CONTROL_PROPS.add(Forms2Attr.PICTURE, G_PICTURE);
        	G_CONTROL_PROPS.add(Forms2Attr.PICTURE_POSITION, PicturePosition.RIGHT_TOP);
        	G_CONTROL_PROPS.add(Forms2Attr.VALUE, "1");
        	G_CONTROL_PROPS.add(Forms2Attr.CAPTION, "The Caption!");
        	G_CONTROL_PROPS.add(Forms2Attr.GROUP_NAME, "Group");
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }
}

