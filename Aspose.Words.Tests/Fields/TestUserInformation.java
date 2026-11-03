// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/10/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests how the user information fields work.
/// </summary>
@Test
public class TestUserInformation extends TestFieldsBase
{
    private static /*final*/ UserInformation G_CURRENT_USER;

    @BeforeMethod (alwaysRun = true)
    public void setupDefaultUser()
    {
        UserInformation.getDefaultUser().setName("John D. Hardy");
        UserInformation.getDefaultUser().setInitials("JDH");
        UserInformation.getDefaultUser().setAddress("2058 Coulter Lane\rRichmond, VA 23223");
    }

    @AfterMethod (alwaysRun = true)
    public void clearDefaultUser()
    {
        UserInformation.getDefaultUser().setName(null);
        UserInformation.getDefaultUser().setInitials(null);
        UserInformation.getDefaultUser().setAddress(null);
    }


    /// <summary>
    /// Tests how the USERINITIALS field is updated.
    /// </summary>
    @Test
    public void testUserInitials() throws Exception
    {
        Document document = TestUtil.open("Fields\\UserInformation\\TestUserInitials.docx");
        document.getFieldOptions().setCurrentUser(G_CURRENT_USER);

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\UserInformation\\TestUserInitials.docx");
    }

    /// <summary>
    /// Tests how the USERADDRESS field is updated.
    /// </summary>
    @Test
    public void testUserAddress() throws Exception
    {
        Document document = TestUtil.open("Fields\\UserInformation\\TestUserAddress.docx");
        document.getFieldOptions().setCurrentUser(G_CURRENT_USER);

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\UserInformation\\TestUserAddress.docx");
    }

    /// <summary>
    /// Tests how the USERNAME field is updated.
    /// </summary>
    @Test
    public void testDefaultUserName() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Field field = builder.insertField("USERNAME");

        field.update();

        msAssert.areEqual("John D. Hardy", field.getResult());
    }

    /// <summary>
    /// Tests how the USERINITIALS field is updated.
    /// </summary>
    @Test
    public void testDefaultUserInitials() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Field field = builder.insertField("USERINITIALS");

        field.update();

        msAssert.areEqual("JDH", field.getResult());
    }

    /// <summary>
    /// Tests how the USERADDRESS field is updated.
    /// </summary>
    @Test
    public void testDefaultUserAddress() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Field field = builder.insertField("USERADDRESS");

        field.update();

        msAssert.areEqual("2058 Coulter Lane\rRichmond, VA 23223", field.getResult());
    }

    static/* TestUserInformation()*/
    {
        G_CURRENT_USER = new UserInformation();
        G_CURRENT_USER.setName("Richard A. Drake");
        G_CURRENT_USER.setInitials("RAD");
        G_CURRENT_USER.setAddress("1112 Travis Street\rPort St Lucie, FL 33452");
    }
}

