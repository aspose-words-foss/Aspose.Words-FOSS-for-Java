// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/09/2012 by Andrey Noskov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Convert;


/// <summary>
/// Test suite to verify how ResourceLoadingCallback works.
/// </summary>
@Test
public class TestResourceLoadingCallback
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



    // FOSS: TestBanNetworkAccess removed — its input is a .doc (Doc reader removed) with an
    // INCLUDEPICTURE field to an external image and no .docx sibling. The network-ban callback
    // path stays covered by SkipLoadingDrawingMlExternalImage (a DrawingML external image).


    /// <summary>
    /// WORDSNET-20391 Make sure all external resources are importing via IResourceLoadingCallback.
    /// This case is about loading external template during UpdateStyles.
    /// There is no way to set AttachedTemplate as web link through the MS Word UI but it can be done manually unpacking DOCX zip,
    /// or using AW.
    /// So, it needs to be cover using IResourceLoadingCallback.
    /// </summary>
    @Test
    public void test20391_UpdateStyles() throws Exception
    {
        LoadOptions loadOptions = new LoadOptions();
        SkipLoadingTemplateCallback skipLoad = new SkipLoadingTemplateCallback();
        loadOptions.setResourceLoadingCallback(skipLoad);

        msAssert.areEqual(0, skipLoad.getCallCount());

        // Try to open document with Document.AutomaticallyUpdateStyles turned on.
        Document doc = TestUtil.open("Model\\Other\\ExternalAttachedTemplate.docx", loadOptions);
        msAssert.isTrue(doc.getAutomaticallyUpdateStyles());

        // Resource loading callback not called because we do not update styles on loading for a while.
        msAssert.areEqual(0, skipLoad.getCallCount());
        msAssert.areEqual("https://github.com/aspose-words/template.docx", doc.getDocPr().AttachedTemplate);

        // FOSS: Pdf save removed; the attached-template load during style update runs on the
        // OOXML save path too.
        doc.save(new MemoryStream(), SaveFormat.DOCX);

        // Resource loading callback called.
        msAssert.areEqual(1, skipLoad.getCallCount());
    }

    /// <summary>
    /// Skip loading template from external resources.
    /// </summary>
    private static class SkipLoadingTemplateCallback implements IResourceLoadingCallback
    {
        public /*ResourceLoadingAction*/int resourceLoading(ResourceLoadingArgs args)
        {
            if (args.getResourceType() == ResourceType.DOCUMENT)
            {
                setCallCount(getCallCount() + 1)/*Property++*/;
                return ResourceLoadingAction.SKIP;
            }

            return ResourceLoadingAction.DEFAULT;
        }

        int getCallCount() { return mCallCount; }; private void setCallCount(int value) { mCallCount = value; };

        private int mCallCount;
    }

    private static class BanNetworkAccessHandler implements IResourceLoadingCallback
    {
        public /*ResourceLoadingAction*/int resourceLoading(ResourceLoadingArgs args)
        {
            if (args.getResourceType() == ResourceType.IMAGE)
                return ResourceLoadingAction.SKIP;
         
            return ResourceLoadingAction.DEFAULT;
        }
    }

    private static class InsertBase64ImageHandler implements IResourceLoadingCallback
    {
        public /*ResourceLoadingAction*/int resourceLoading(ResourceLoadingArgs args)
        {
            if (args.getResourceType() == ResourceType.IMAGE)
            {
                String image64 = args.getOriginalUri();
                byte[] imageBytes = Convert.fromBase64String(image64);
                args.setData(imageBytes);
                return ResourceLoadingAction.USER_PROVIDED;
            }
            else
            {
                return ResourceLoadingAction.DEFAULT;
            }
        }
    }
}

