// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/06/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for editable ranges.
/// </summary>
public class TestEditableRanges extends UnifiedTestsBase
{
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestEditableRanges(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\EditableRange\\EditableRanges", getNoGoldScenario(lf, sf));

        NodeCollection starts = doc.getChildNodes(NodeType.EDITABLE_RANGE_START, true);
        NodeCollection ends = doc.getChildNodes(NodeType.EDITABLE_RANGE_END, true);

        // Check editable ranges are present.
        msAssert.areEqual(2, starts.getCount());
        msAssert.areEqual(2, ends.getCount());

        // Check editable ranges have Ids.
        EditableRangeStart prStart = (EditableRangeStart)starts.get(0);
        msAssert.areEqual(0, prStart.getId());
        msAssert.isTrue(prStart.getNextSibling().getText().indexOf("starts in this") != -1);

        // Check single user for editable range is specified but editor group is not.
        msAssert.areEqual("WEB1\\Andrey", prStart.getSingleUser());
        msAssert.areEqual(EditorType.UNSPECIFIED, prStart.getEditorGroup());
        msAssert.isTrue(prStart.getPreviousSibling() instanceof Run);
        msAssert.isTrue(prStart.getNextSibling() instanceof Run);

        EditableRangeEnd prEnd = (EditableRangeEnd)ends.get(0);
        msAssert.areEqual(1, prEnd.getId());
        msAssert.isTrue(prEnd.getNextSibling().getText().indexOf("and another") != -1);

        // This editable range is inside editable range 1.
        prStart = (EditableRangeStart)starts.get(1);
        msAssert.areEqual(1, prStart.getId());
        msAssert.areEqual("", prStart.getSingleUser());
        msAssert.areEqual(EditorType.EVERYONE, prStart.getEditorGroup());
        msAssert.areEqual(" paragraph ", prStart.getNextSibling().getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestEditableRangesSourceDataProvider")
	public void unifiedTestEditableRangesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestEditableRanges(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestEditableRangesSourceDataProvider")
	Object[][] unifiedTestEditableRangesSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Test several editable ranges start and end in the same position.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestEditableRangesSamePosition(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\EditableRange\\TestEditableRangesSamePosition", getNoGoldScenario(lf, sf));

        NodeCollection starts = doc.getChildNodes(NodeType.EDITABLE_RANGE_START, true);
        NodeCollection ends = doc.getChildNodes(NodeType.EDITABLE_RANGE_END, true);
        
        // Check editable range are present.
        msAssert.areEqual(3, starts.getCount());
        msAssert.areEqual(3, ends.getCount());

        EditableRangeStart prStart1 = (EditableRangeStart)starts.get(0);
        msAssert.areEqual(0, prStart1.getId());
        EditableRangeStart prStart2 = (EditableRangeStart)starts.get(1);
        msAssert.areEqual(1, prStart2.getId());

        // Start of editableRange1 is right after editableRange2.
        msAssert.isTrue(prStart1.getNextSibling() == prStart2);
        msAssert.isTrue(prStart2.getNextSibling().getText().indexOf("Section1") != -1);

        EditableRangeEnd prEnd2 = (EditableRangeEnd)ends.get(1);
        msAssert.areEqual(1, prEnd2.getId());
        EditableRangeEnd prEnd3 = (EditableRangeEnd)ends.get(2);
        msAssert.areEqual(2, prEnd3.getId());

        // End of editableRange3 is right after end of editableRange1.
        msAssert.isTrue(prEnd2.getNextSibling() == prEnd3);
        msAssert.isTrue(prEnd3.getNextSibling() == null);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestEditableRangesSamePositionSourceDataProvider")
	public void unifiedTestEditableRangesSamePositionSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestEditableRangesSamePosition(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestEditableRangesSamePositionSourceDataProvider")
	Object[][] unifiedTestEditableRangesSamePositionSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Removes a section so only editable range end is available, should work okay and remove the editable range.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestEditableRangeCutStart(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\EditableRange\\TestEditableRangesSamePosition", lf);
        doc.getSections().removeAt(0);

        doc = TestUtil.saveOpen(doc, "Model\\EditableRange\\TestEditableRangeCutStart", getNoGoldScenario(lf, sf));

        NodeCollection starts = doc.getChildNodes(NodeType.EDITABLE_RANGE_START, true);
        NodeCollection ends = doc.getChildNodes(NodeType.EDITABLE_RANGE_END, true);

        //Only one editable range now is left.
        msAssert.areEqual(1, starts.getCount());
        msAssert.areEqual(1, ends.getCount());

        EditableRangeStart prStart = (EditableRangeStart)starts.get(0);
        msAssert.areEqual(0, prStart.getId());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestEditableRangeCutStartSourceDataProvider")
	public void unifiedTestEditableRangeCutStartSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestEditableRangeCutStart(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestEditableRangeCutStartSourceDataProvider")
	Object[][] unifiedTestEditableRangeCutStartSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Removes a section so only editable range start is available, should work okay and remove the editable range.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestEditableRangeCutEnd(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\EditableRange\\TestEditableRangesSamePosition", lf);
        doc.getSections().removeAt(1);

        doc = TestUtil.saveOpen(doc, "Model\\EditableRange\\TestEditableRangeCutEnd", getNoGoldScenario(lf, sf));

        NodeCollection starts = doc.getChildNodes(NodeType.EDITABLE_RANGE_START, true);
        NodeCollection ends = doc.getChildNodes(NodeType.EDITABLE_RANGE_END, true);

        // Only one editable range now is left.
        msAssert.areEqual(1, starts.getCount());
        msAssert.areEqual(1, ends.getCount());

        EditableRangeStart prStart = (EditableRangeStart)starts.get(0);
        msAssert.areEqual(0, prStart.getId());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestEditableRangeCutEndSourceDataProvider")
	public void unifiedTestEditableRangeCutEndSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestEditableRangeCutEnd(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestEditableRangeCutEndSourceDataProvider")
	Object[][] unifiedTestEditableRangeCutEndSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestEditableRangeDuplicate(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\EditableRange\\TestEditableRangesSamePosition", lf);

        // Adds a copy of the last section to the document.
        doc.getFirstSection().appendContent(doc.getSections().get(1).deepClone());

        doc = TestUtil.saveOpen(doc, "Model\\EditableRange\\TestEditableRangeDuplicate", getNoGoldScenario(lf, sf));

        NodeCollection starts = doc.getChildNodes(NodeType.EDITABLE_RANGE_START, true);
        NodeCollection ends = doc.getChildNodes(NodeType.EDITABLE_RANGE_END, true);

        msAssert.areEqual(4, starts.getCount());
        msAssert.areEqual(4, ends.getCount());

        EditableRangeStart prStart1 = (EditableRangeStart)starts.get(0);
        msAssert.areEqual(0, prStart1.getId());
        msAssert.areEqual("andrey@office.net.com", prStart1.getSingleUser());

        EditableRangeStart prStart2 = (EditableRangeStart)starts.get(1);
        msAssert.areEqual(1, prStart2.getId());
        msAssert.areEqual("DnsAdmins@office.net.com", prStart2.getSingleUser());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestEditableRangeDuplicateSourceDataProvider")
	public void unifiedTestEditableRangeDuplicateSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestEditableRangeDuplicate(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestEditableRangeDuplicateSourceDataProvider")
	Object[][] unifiedTestEditableRangeDuplicateSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestEditableRangeSetEditor(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\EditableRange\\TestEditableRangesSamePosition", getNoGoldScenario(lf, sf));

        EditableRangeStart start = (EditableRangeStart)doc.getChild(NodeType.EDITABLE_RANGE_START, 0, true);
        EditableRange editableRange = start.getEditableRange();
        msAssert.areEqual("andrey@office.net.com", editableRange.getSingleUser());
        msAssert.areEqual(EditorType.UNSPECIFIED, editableRange.getEditorGroup());
        start.getEditableRange().setSingleUser("Andrey@aspose.com");

        doc = TestUtil.saveOpen(doc, "Model\\EditableRange\\TestEditableRangeSetEditor", getNoGoldScenario(lf, sf));

        start = (EditableRangeStart)doc.getChild(NodeType.EDITABLE_RANGE_START, 0, true);
        editableRange = start.getEditableRange();
        msAssert.areEqual("Andrey@aspose.com", editableRange.getSingleUser());

        // Single user and editor group cannot be set simultaneously for the specific editable range,
        // if the one is set, the other will be clear.
        editableRange.setEditorGroup(EditorType.ADMINISTRATORS);
        msAssert.isEmpty(editableRange.getSingleUser());
        msAssert.areEqual(EditorType.ADMINISTRATORS, editableRange.getEditorGroup());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestEditableRangeSetEditorSourceDataProvider")
	public void unifiedTestEditableRangeSetEditorSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestEditableRangeSetEditor(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestEditableRangeSetEditorSourceDataProvider")
	Object[][] unifiedTestEditableRangeSetEditorSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Test editable ranges start and end inside/outside table.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestTableColumnEditableRange(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\EditableRange\\TestTableColumnEditableRange", getNoGoldScenario(lf, sf));

        NodeCollection starts = doc.getChildNodes(NodeType.EDITABLE_RANGE_START, true);
        NodeCollection ends = doc.getChildNodes(NodeType.EDITABLE_RANGE_END, true);

        // Check editable range are present.
        msAssert.areEqual(17, starts.getCount());
        msAssert.areEqual(17, ends.getCount());

        EditableRangeStart prStart1 = (EditableRangeStart)starts.get(0);
        msAssert.areEqual(0, prStart1.getId());
        msAssert.areEqual(EditorType.EVERYONE, prStart1.getEditorGroup());

        // Check FirstColumn/LastColumn are correct.
        msAssert.areEqual(1, prStart1.getFirstColumn());
        msAssert.areEqual(1, prStart1.getLastColumn());

        EditableRangeStart prStart2 = (EditableRangeStart)starts.get(1);
        msAssert.areEqual(1, prStart2.getId());
        msAssert.areEqual(EditorType.EVERYONE, prStart2.getEditorGroup());
        msAssert.areEqual(2, prStart2.getFirstColumn());
        msAssert.areEqual(2, prStart2.getLastColumn());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTableColumnEditableRangeSourceDataProvider")
	public void unifiedTestTableColumnEditableRangeSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTableColumnEditableRange(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTableColumnEditableRangeSourceDataProvider")
	Object[][] unifiedTestTableColumnEditableRangeSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Test removing editable range.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestEditableRangeRemove(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\EditableRange\\TestTableColumnEditableRange", getNoGoldScenario(lf, sf));

        NodeCollection starts = doc.getChildNodes(NodeType.EDITABLE_RANGE_START, true);
        NodeCollection ends = doc.getChildNodes(NodeType.EDITABLE_RANGE_END, true);

        // Check editable range are present.
        msAssert.areEqual(17, starts.getCount());
        msAssert.areEqual(17, ends.getCount());

        EditableRange editableRange1 = ((EditableRangeStart)starts.get(0)).getEditableRange();
        msAssert.areEqual(0, editableRange1.getId());
        editableRange1.remove();

        starts = doc.getChildNodes(NodeType.EDITABLE_RANGE_START, true);
        ends = doc.getChildNodes(NodeType.EDITABLE_RANGE_END, true);

        msAssert.areEqual(16, starts.getCount());
        msAssert.areEqual(16, ends.getCount());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestEditableRangeRemoveSourceDataProvider")
	public void unifiedTestEditableRangeRemoveSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestEditableRangeRemove(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestEditableRangeRemoveSourceDataProvider")
	Object[][] unifiedTestEditableRangeRemoveSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Test editable range inside OfficeMath.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestEditableRangeOfficeMath(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        switch (TestUtil.getUnifiedScenario(lf, sf))
        {
            case UnifiedScenario.DOC_2_DOC:
            case UnifiedScenario.DOC_2_RTF:
            case UnifiedScenario.DOC_2_WML:
            case UnifiedScenario.WML_2_WML_NO_GOLD:
            case UnifiedScenario.DOC_2_DOCX:
                break;
            case UnifiedScenario.RTF_2_RTF_NO_GOLD:
            case UnifiedScenario.RTF_2_RTF:
            case UnifiedScenario.DOCX_2_DOCX_NO_GOLD:
            case UnifiedScenario.DOCX_2_DOCX:
            {
                Document doc = TestUtil.openSaveOpen("Model\\EditableRange\\UnifiedTestEditableRangeOfficeMath", getNoGoldScenario(lf, sf));

                NodeCollection starts = doc.getChildNodes(NodeType.EDITABLE_RANGE_START, true);
                NodeCollection ends = doc.getChildNodes(NodeType.EDITABLE_RANGE_END, true);

                // Check editable range are present.
                msAssert.areEqual(3, starts.getCount());
                msAssert.areEqual(3, ends.getCount());

                EditableRange editableRange1 = ((EditableRangeStart)starts.get(0)).getEditableRange();

                // Check parent node is OfficeMath.
                msAssert.areEqual(NodeType.OFFICE_MATH, editableRange1.getEditableRangeStart().getParentNode().getNodeType());
                msAssert.areEqual(NodeType.OFFICE_MATH, editableRange1.getEditableRangeEnd().getParentNode().getNodeType());
                msAssert.areEqual(0, editableRange1.getId());
                msAssert.areEqual(EditorType.EVERYONE, editableRange1.getEditorGroup());
                msAssert.isEmpty(editableRange1.getSingleUser());
                break;
            }
            default:
                throw new IllegalStateException("Unknown file format.");
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestEditableRangeOfficeMathSourceDataProvider")
	public void unifiedTestEditableRangeOfficeMathSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestEditableRangeOfficeMath(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestEditableRangeOfficeMathSourceDataProvider")
	Object[][] unifiedTestEditableRangeOfficeMathSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    private static /*UnifiedScenario*/int getNoGoldScenario(/*LoadFormat*/int lf, /*SaveFormat*/int sf)
    {
        return TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD;
    }
}

