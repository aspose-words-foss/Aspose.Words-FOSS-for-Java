// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/07/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Common ancestor for <see cref="SdtBuildingBlockGallery"/> and <see cref="SdtDocPartObj"/>.
/// </summary>
abstract class SdtDocPart extends SdtControlProperties implements Cloneable
{

    /*override*/ SdtControlProperties deepClone()
    {
        return (SdtDocPart)memberwiseClone();
    }

    /// <summary>
    /// Specifies the category of document parts which shall be used as the filter when determining the
    /// possible choices of document parts which are displayed for insertion into the sdt. 
    /// </summary>
    /// <remarks>
    /// A document part category is a sub-classification within a given document part gallery which can be used to further
    /// categorize the parts in a given gallery.
    /// </remarks>
    String getBuildingBlockCategory() { return mBuildingBlockCategory; }
    void setBuildingBlockCategory(String value) { mBuildingBlockCategory = value; }

    /// <summary>
    /// specifies the gallery of document parts which shall be used as the filter when determining the possible 
    /// choices of document parts which are displayed for insertion into the parent structured document tag.
    /// </summary>
    /// <remarks>
    /// A document part gallery is a classification of document parts, which might then be subdivided into categories.
    /// </remarks>
    String getBuildingBlockType() { return mBuildingBlockType; }
    void setBuildingBlockType(String value) { mBuildingBlockType = value; }

    /// <summary>
    /// Specifies that this structured document tag is being used to encapsulate a built-in document part.
    /// </summary>
    boolean isUnique() { return mIsUnique; }
    void isUnique(boolean value) { mIsUnique = value; }

    /// <summary>
    /// See Iso29500, chapter 1, 17.5.2.14 docPartUnique (Built-In Document Part).
    /// </summary>
    private boolean mIsUnique;

    /// <summary>
    /// See Iso29500, chapter 1, 17.5.2.11 docPartGallery (Document Part Gallery Filter)
    /// </summary>
    private String mBuildingBlockType = "";

    /// <summary>
    /// See Iso29500, chapter 1, 17.5.2.10 docPartCategory (Document Part Category Filter)
    /// </summary>
    private String mBuildingBlockCategory = "";

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

