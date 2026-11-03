// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/10/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ArrayUtil;
import com.aspose.ImageUtil;


/// <summary>
/// Contains tests which checks how work import of task pane add-ins.
/// </summary>
@Test
public class TestImportWebExtensions
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

    /// <summary>
    /// Related to WORDSNET-18681
    /// Checks reading of task pane add-in without bindings, properties and etc.
    /// </summary>
    @Test
    public void test18681ReadWebExtension() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\WebExtensions\\Test18681ReadWebExtension.docx");

        // Prepare expected task pane.
        TaskPane expectedTaskPane = createTaskPane(6, 662.0d, "{EF15991F-8C78-4838-B0D8-FABD0105D596}");
        // Prepare expected web extension.
        populateExtensionReference(expectedTaskPane.getWebExtension().getReference(), "wa104099688", "1.3.0.0", "en-US");

        // Compare expected and actual value.
        msAssert.areEqual(1, doc.getWebExtensionTaskPanes().getCount());
        assertTaskPane(expectedTaskPane, doc.getWebExtensionTaskPanes().getFirst());
    }

    /// <summary>
    ///  Related to WORDSNET-18681
    ///  Checks case when task pane add-in parts placed in non-standard path.
    /// </summary>
    @Test
    public void test18681ReadWebExtensionDocLevel() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\WebExtensions\\Test18681ReadWebExtensionDocLevel.docx");

        // Prepare expected task pane.
        TaskPane expectedTaskPane = createTaskPane(7, 350.0d, "{6301161A-C1FB-44AB-8365-DE52F792C01D}");

        // Prepare expected web extension. 
        populateExtensionReference(expectedTaskPane.getWebExtension().getReference(), "wa104381269", "1.0.0.4", "en-US");
        addAlternateReference(expectedTaskPane.getWebExtension(), "wa104381269", "1.0.0.4", "wa104381269");

        // Compare expected and actual value.
        assertTaskPane(expectedTaskPane, doc.getWebExtensionTaskPanes().getFirst());
        msAssert.areEqual(1, doc.getWebExtensionTaskPanes().getFirst().getWebExtension().getAlternateReferences().getCount());
    }

    /// <summary>
    /// Related to WORDSNET-18681
    /// Checks case when document contains two task pane add-ins.
    /// </summary>
    @Test
    public void test18681ReadTwoWebExtensions() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\WebExtensions\\Test18681TwoTaskPanes.docx");

        TaskPaneCollection taskPanes = doc.getWebExtensionTaskPanes();
        msAssert.areEqual(2, taskPanes.getCount());

        // Check first web extension.
        TaskPane expectedTaskPane = createTaskPane(6, 350.0d, "{9C7F2463-912E-4103-8978-2276AB15D4AC}");
        populateExtensionReference(expectedTaskPane.getWebExtension().getReference(), "wa102923726", "1.0.0.0", "en-US");
        addAlternateReference(expectedTaskPane.getWebExtension(), "WA102923726", "1.0.0.0", "WA102923726");

        assertTaskPane(expectedTaskPane, taskPanes.getFirst());

        // Check second web extension.
        expectedTaskPane = createTaskPane(7, 350.0d, "{818247EE-A692-4795-AA5B-20ADCA44E6F0}");
        populateExtensionReference(expectedTaskPane.getWebExtension().getReference(), "wa104124372", "1.2.0.0", "en-US");
        addAlternateReference(expectedTaskPane.getWebExtension(), "WA104124372", "1.2.0.0", "WA104124372");

        assertTaskPane(expectedTaskPane, taskPanes.getLast());
    }

    /// <summary>
    /// Related to WORDSNET-18681
    /// Checks reading of "snapshot" element.
    /// </summary>
    @Test
    public void test18681ReadSnapshot() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\WebExtensions\\Test18681ReadSnapshot.docx");
        TaskPaneCollection taskPanes = doc.getWebExtensionTaskPanes();
        DmlBlip blip = taskPanes.getFirst().getWebExtension().getSnapshot();

        msAssert.isNull(blip.getImageLink());
        msAssert.isFalse(blip.hasExtensions());
        msAssert.areEqual(0, blip.getEffects().size());
        msAssert.areEqual(DmlCompressionState.EMAIL, blip.getCompressionState());
        ArrayUtil.isArrayEqual(ImageUtil.getNoImageBytes(), blip.getEmbedImage());
    }

    /// <summary>
    /// Related to WORDSNET-18681
    /// Check reading of custom properties.
    /// </summary>
    @Test
    public void test18681ReadProperties() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira12228.docx");
        WebExtensionPropertyCollection actualProperties = doc.getWebExtensionTaskPanes().getFirst().getWebExtension().getProperties();
        WebExtensionPropertyCollection expectedProperties = createPropertyCollection(
            new WebExtensionProperty[] { new WebExtensionProperty("mailchimpCampaign", "mailchimpCampaign") });

        assertProperties(expectedProperties, actualProperties);
    }




    /// <summary>
    /// Related to WORDSNET-18681
    /// Checks reading of "DockState" attribute.
    /// </summary>
    @Test
    public void test18681ReadDockState() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\WebExtensions\\Test18681ReadDockState.docx");
        msAssert.areEqual(TaskPaneDockState.LEFT, doc.getWebExtensionTaskPanes().getFirst().getDockState());
    }

    /// <summary>
    /// Creates bindings collection and populates it with passed content.
    /// </summary>
    private WebExtensionBindingCollection createBindings(WebExtensionBinding[] bindings)
    {
        WebExtensionBindingCollection collection = new WebExtensionBindingCollection();

        for (WebExtensionBinding binding : bindings)
            collection.add(binding);

        return collection;
    }

    /// <summary>
    /// Creates properties collection and populates it with passed content.
    /// </summary>
    private WebExtensionPropertyCollection createPropertyCollection(WebExtensionProperty[] properties)
    {
        WebExtensionPropertyCollection collection = new WebExtensionPropertyCollection();
        for(WebExtensionProperty property : properties)
            collection.add(property);

        return collection;
    }

    /// <summary>
    /// Adds alternate reference to web extension object.
    /// </summary>
    private void addAlternateReference(WebExtension extension, String id, String version, String store)
    {
        WebExtensionReference reference = new WebExtensionReference();
        populateExtensionReference(reference, id, version, store);

        extension.getAlternateReferences().add(reference);
    }

    /// <summary>
    /// Checks equality of task pane add-ins.
    /// </summary>
    private void assertTaskPane(TaskPane expectedTaskPane, TaskPane actualTaskPane)
    {
        msAssert.areEqual(expectedTaskPane.getRow(), actualTaskPane.getRow());
        msAssert.areEqual(expectedTaskPane.isLocked(), actualTaskPane.isLocked());
        msAssert.areEqual(expectedTaskPane.isVisible(), actualTaskPane.isVisible());
        msAssert.areEqual(expectedTaskPane.getDockState(), actualTaskPane.getDockState());
        msAssert.areEqual(expectedTaskPane.getWidth(), 0.000001, actualTaskPane.getWidth());

        assertWebExtension(expectedTaskPane.getWebExtension(), actualTaskPane.getWebExtension());
    }

    /// <summary>
    /// Checks equality of web extension objects.
    /// </summary>
    private void assertWebExtension(WebExtension expectedWebExtension, WebExtension actualWebExtension)
    {
        msAssert.areEqual(expectedWebExtension.getId(), actualWebExtension.getId());
        msAssert.areEqual(expectedWebExtension.isFrozen(), actualWebExtension.isFrozen());

        assertBindings(expectedWebExtension.getBindings(), actualWebExtension.getBindings());
        assertReference(expectedWebExtension.getReference(), actualWebExtension.getReference());
        assertProperties(expectedWebExtension.getProperties(), actualWebExtension.getProperties());
        assertAlternateReferences(expectedWebExtension.getAlternateReferences(), 
            actualWebExtension.getAlternateReferences());
    }

    /// <summary>
    /// Checks equality of reference objects.
    /// </summary>
    private void assertReference(WebExtensionReference expectedReference, WebExtensionReference actualReference)
    {
        msAssert.areEqual(expectedReference.getId(), actualReference.getId());
        msAssert.areEqual(expectedReference.getStore(), actualReference.getStore());
        msAssert.areEqual(expectedReference.getVersion(), actualReference.getVersion());
        msAssert.areEqual(expectedReference.getStoreType(), actualReference.getStoreType());
    }

    /// <summary>
    /// Checks binding collections equality.
    /// </summary>
    private void assertBindings(WebExtensionBindingCollection expectedBindings,
        WebExtensionBindingCollection actualBindings)
    {
        msAssert.areEqual(expectedBindings.getCount(), actualBindings.getCount());

        for (int i = 0; i < expectedBindings.getCount(); ++i)
            assertBinding(expectedBindings.get(i), actualBindings.get(i));
    }

    /// <summary>
    /// Checks bindings equality.
    /// </summary>
    private void assertBinding(WebExtensionBinding expectedBinding, WebExtensionBinding actualBinding)
    {
        msAssert.areEqual(expectedBinding.getId(), actualBinding.getId());
        msAssert.areEqual(expectedBinding.getAppRef(), actualBinding.getAppRef());
        msAssert.areEqual(expectedBinding.getBindingType(), actualBinding.getBindingType());
    }

    /// <summary>
    /// Checks property collections equality.
    /// </summary>
    private void assertProperties(WebExtensionPropertyCollection expectedProperties,
        WebExtensionPropertyCollection actualProperties)
    {
        msAssert.areEqual(expectedProperties.getCount(), actualProperties.getCount());

        for(int i = 0; i < expectedProperties.getCount(); ++i)
            assertProperty(expectedProperties.get(i), actualProperties.get(i));
    }

    /// <summary>
    /// Checks properties equality.
    /// </summary>
    private void assertProperty(WebExtensionProperty expectedProperty, WebExtensionProperty actualProperty)
    {
        msAssert.areEqual(expectedProperty.getName(), actualProperty.getName());
        msAssert.areEqual(expectedProperty.getValue(), actualProperty.getValue());
    }

    /// <summary>
    /// Checks alternate references equality.
    /// </summary>
    private void assertAlternateReferences(WebExtensionReferenceCollection expectedReferences,
        WebExtensionReferenceCollection actualReferences)
    {
        msAssert.areEqual(expectedReferences.getCount(), actualReferences.getCount());

        for (int i = 0; i < expectedReferences.getCount(); ++i)
            assertReference(expectedReferences.get(i), actualReferences.get(i));
    }

    /// <summary>
    /// Creates a task pane with specified parameters.
    /// </summary>
    private TaskPane createTaskPane(int row, double width, String extensionId)
    {
        TaskPane expectedTaskPane = new TaskPane();
        expectedTaskPane.isVisible(true);
        expectedTaskPane.isLocked(false);

        expectedTaskPane.setRow(row);
        expectedTaskPane.setWidth(width);

        expectedTaskPane.getWebExtension().setId(extensionId);
        expectedTaskPane.getWebExtension().isFrozen(false);

        return expectedTaskPane;
    }

    /// <summary>
    /// Fills reference with specified parameters.
    /// </summary>
    private WebExtensionReference populateExtensionReference(WebExtensionReference reference,
        String id, String version, String store)
    {
        return populateExtensionReference(reference, id, version, store, WebExtensionStoreType.OMEX);
    }

    /// <summary>
    /// Fills reference with specified parameters.
    /// </summary>
    private WebExtensionReference populateExtensionReference(WebExtensionReference reference,
        String id, String store, /*WebExtensionStoreType*/int storeType)
    {
        return populateExtensionReference(reference, id, "1.0.0.0", store, storeType);
    }

    /// <summary>
    /// Fills reference with specified parameters.
    /// </summary>
    private WebExtensionReference populateExtensionReference(WebExtensionReference reference,
        String id, String version, String store, /*WebExtensionStoreType*/int storeType)
    {
        reference.setId(id);
        reference.setStore(store);
        reference.setVersion(version);
        reference.setStoreType(storeType);

        return reference;
    }
}

