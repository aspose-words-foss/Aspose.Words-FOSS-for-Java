// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/25/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;


/// <summary>
/// Simple open/save/compare tests for DOCX document with charts.
/// Used to make sure charts are written properly from the model.
/// Use test documents from charts features 3D rendering tests.
/// </summary>
@Test
public class TestDmlChartsFeatures3D
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void test3DAxisDepth() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DAxisDepth.docx");
    }

    @Test
    public void test3DAxisRotation() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DAxisRotation.docx");
    }

    @Test
    public void test3DAxisPositionA() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DAxisPositionA.docx");
    }

    @Test
    public void test3DAxisPositionB() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DAxisPositionB.docx");
    }

    @Test
    public void test3DAxisPositionC() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DAxisPositionC.docx");
    }

    @Test
    public void test3DAxisPositionD() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DAxisPositionD.docx");
    }

    @Test
    public void test3DBarDirrection() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DBarDirrection.docx");
    }

    @Test
    public void test3DBarDirrectionAxisRotation() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DBarDirrectionAxisRotation.docx");
    }

    @Test
    public void test3DBarDirrectionAxisPosition() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DBarDirrectionAxisPosition.docx");
    }

    @Test
    public void test3DManualLayout() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DManualLayout.docx");
    }

    @Test
    public void test3DPieChartsRotation() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DPieChartsRotation.docx");
    }

    @Test
    public void test3DPieChartsBorders() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DPieChartsBorders.docx");
    }

    @Test
    public void test3DVerticalAxisLabels() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DVerticalAxisLabels.docx");
    }

    @Test
    public void test3DBandFmts() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DBandFmts.docx");
    }

    @Test
    public void test3DDepthGridLines() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DDepthGridLines.docx");
    }

    @Test
    public void test3DDataLabels() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DDataLabels.docx");
    }

    @Test
    public void test3DDataTable() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DDataTable.docx");
    }
}

