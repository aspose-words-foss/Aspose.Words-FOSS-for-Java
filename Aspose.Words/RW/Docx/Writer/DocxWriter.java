// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/05/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.OpcPackage;
import com.aspose.OpcFlatPackage;
import com.aspose.ms.System.msString;
import com.aspose.TempFiler;
import java.util.ArrayList;
import com.aspose.OpcPackagePart;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.OpcRelsWriter;
import com.aspose.OpcContentTypesWriter;
import com.aspose.StringUtil;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.FileSystem;
import com.aspose.ZipCompressionLevel;
import com.aspose.Zip64Option;
import com.aspose.OpcCorePropertiesWriter;
import java.util.Collection;
import com.aspose.ms.System.StringComparison;
import com.aspose.Debug;
import com.aspose.OpcPackageBase;
import com.aspose.StringToIntDictionary;
import com.aspose.HashUtil;
import com.aspose.BytesHashToObjDictionary;


/// <summary>
/// Main starter class for writing a document to OOXML.
/// Also provides services that are global for the whole package, e.g. naming of images or embedded objects.
/// </summary>
class DocxWriter implements IDocumentWriter
{
    DocxWriter()
    {
        setPackage(new OpcPackage());
    }

    public SaveOutputParameters /*IDocumentWriter.*/saveToStream(SaveInfo saveInfo) throws Exception
    {
        setSaveInfo(saveInfo);

        checkCanSaveMacros();

        switch (saveInfo.getSaveFormat())
        {
            case SaveFormat.FLAT_OPC:
            case SaveFormat.FLAT_OPC_TEMPLATE:
            case SaveFormat.FLAT_OPC_MACRO_ENABLED:
            case SaveFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED:
                setPackage(new OpcFlatPackage());
                break;

            case SaveFormat.DOCX:
            case SaveFormat.DOCM:
            case SaveFormat.DOTX:
            case SaveFormat.DOTM:
            {
                // WORDSNET-19478 Use a specific comparator.
                OoxmlSaveOptions saveOptions = (OoxmlSaveOptions)saveInfo.SaveOptions;
                setPackage(new OpcPackage(
                    new OpcPartsComparer(),
                    getCorrespondingCompressionLevel(saveOptions.getCompressionLevel()),
                    getCorrespondingZip64Option(saveOptions.getZip64Mode())));
                break;
            }

            default:
                throw new IllegalStateException(msString.format("Unexpected SaveFormat value {0}.", saveInfo.getSaveFormat()));
        }

        mTempFiler = new TempFiler(saveInfo.SaveOptions.getTempFolder());

        DocxDocumentWriter documentWriter = writeDocument();
        DocxTaskPaneAddinsWriter.write(documentWriter);
        DocxExtentedPropertiesWriter.write(this);
        writeCoreProperties();
        DocxCustomPropertiesWriter.write(this);

        ArrayList<String> nonCustomPartNames = new ArrayList<String>();
        for (OpcPackagePart part : getPackage().getParts())
            msArrayList.add(nonCustomPartNames, part.getName());
        writePackageCustomParts("/", nonCustomPartNames);

        OpcRelsWriter.write(getPackage(), isPrettyFormat());

        if (isWriteContentTypes(saveInfo.getSaveFormat()))
            OpcContentTypesWriter.write((OpcPackage)getPackage(), isPrettyFormat());

        OoxmlSaveOptions so = ms.as(saveInfo.SaveOptions, OoxmlSaveOptions.class);

        // WORDSNET-25896 Implemented SaveOptions.DigitalSignatureDetails option.
        if ((so != null) && (so.getDigitalSignatureDetails() != null) && isDigitalSignatureSupported(saveInfo.getSaveFormat()))
        {
            DigitalSignature signature = so.getDigitalSignatureDetails().getDigitalSignature();
            if (signature != null)
            {
                OpcSignatureWriterDocx signatureWriter = new OpcSignatureWriterDocx((OpcPackage)getPackage());
                signatureWriter.write(signature);
            }
        }

        if((so != null) && (StringUtil.hasChars(so.getPassword())))
        {
            // Write encrypted.
            MemoryStream tempStream = new MemoryStream();
            getPackage().save(tempStream);

            FileSystem fs = DataSpaces.encrypt(tempStream, so.getPassword());
            fs.save(saveInfo.Stream);
        }
        else
        {
            // Write normal.
            getPackage().save(saveInfo.Stream);
        }

        mTempFiler.cleanup();

        return new SaveOutputParameters(getDocumentContentType());
    }

    /// <summary>
    /// Gets a boolean value indicating either <see cref="DigitalSignature"/>
    /// writing is supported for a specified save format.
    /// </summary>
    private static boolean isDigitalSignatureSupported(/*SaveFormat*/int saveFormat)
    {
        return (saveFormat == SaveFormat.DOCX) ||
               (saveFormat == SaveFormat.DOTX) ||
               (saveFormat == SaveFormat.DOCM) ||
               (saveFormat == SaveFormat.DOTM);
    }

    /// <summary>
    /// Gets corresponding Zip.CompressionLevel according to Saving.CompressionLevel
    /// </summary>
    private static /*ZipCompressionLevel*/int getCorrespondingCompressionLevel(/*CompressionLevel*/int compressionLevel)
    {
        switch (compressionLevel)
        {
            case com.aspose.words.CompressionLevel.SUPER_FAST:
                return ZipCompressionLevel.LEVEL_1;
            case com.aspose.words.CompressionLevel.FAST:
                return ZipCompressionLevel.LEVEL_3;
            case com.aspose.words.CompressionLevel.NORMAL:
                return ZipCompressionLevel.LEVEL_5;
            case com.aspose.words.CompressionLevel.MAXIMUM:
                return ZipCompressionLevel.LEVEL_9;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static /*Zip64Option*/int getCorrespondingZip64Option(/*Zip64Mode*/int zip64)
    {
        switch (zip64)
        {
            case Zip64Mode.NEVER:
                return Zip64Option.NEVER;
            case Zip64Mode.IF_NECESSARY:
                return Zip64Option.AS_NECESSARY;
            case Zip64Mode.ALWAYS:
                return Zip64Option.ALWAYS;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static boolean isWriteContentTypes(/*SaveFormat*/int saveFormat)
    {
        // For FlatOpc we do not need to write ContentTypes XML because content type is written as an attribute of each part.
        return
            (saveFormat == SaveFormat.DOCX) ||
            (saveFormat == SaveFormat.DOCM) ||
            (saveFormat == SaveFormat.DOTX) ||
            (saveFormat == SaveFormat.DOTM);
    }

    private void checkCanSaveMacros()
    {
        if (!getSaveInfo().Document.hasMacros())
            return;

        switch (getSaveInfo().getSaveFormat())
        {
            case SaveFormat.DOCX:
            case SaveFormat.DOTX:
            case SaveFormat.FLAT_OPC:
                throw new IllegalStateException(
                    "This document contains macros (VBA project) and you are " +
                    "attempting to save it in a Macro-Free format. " +
                    "Such document will be invalid if created. " +
                    "You need to either save it in a Macro-Enabled format (.DOCM or .DOTM) or " +
                    "remove macros before saving using the Document.RemoveMacros method.");
            case SaveFormat.DOCM:
            case SaveFormat.DOTM:
            case SaveFormat.FLAT_OPC_MACRO_ENABLED:
            case SaveFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED:
                // Do nothing, all ok.
                break;
            default:
                throw new IllegalStateException("Unexpected save format.");
        }
    }

    private String getDocumentContentType()
    {
        switch (getSaveInfo().getSaveFormat())
        {
            case SaveFormat.DOCX:
            case SaveFormat.FLAT_OPC:
                return DocxContentType.DOCX;
            case SaveFormat.DOCM:
            case SaveFormat.FLAT_OPC_MACRO_ENABLED:
                return DocxContentType.DOCM;
            case SaveFormat.DOTX:
            case SaveFormat.FLAT_OPC_TEMPLATE:
                return DocxContentType.DOTX;
            case SaveFormat.DOTM:
            case SaveFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED:
                return DocxContentType.DOTM;
            default:
                throw new IllegalStateException("Unexpected save format.");
        }
    }

    private DocxDocumentWriter writeDocument() throws Exception
    {
        getSaveInfo().makeZIndexDocxStyle();

        DocxDocumentWriter docWriter = new DocxDocumentWriter(this, mTempFiler);
        docWriter.write();

        return docWriter;
    }

    private void writeCoreProperties() throws Exception
    {
        DocxBuilder builder = createPartAndBuilder("/docProps/core.xml", DocxContentType.CORE_PROPERTIES, getRelTypes().getCoreProperties());
        BuiltInDocumentProperties props = getDocument().getBuiltInDocumentProperties();
        OpcCorePropertiesWriter.write(
            builder,
            props.getTitle(),
            props.getSubject(),
            props.getAuthor(),
            props.getKeywords(),
            props.getComments(),
            props.getLastSavedBy(),
            Integer.toString(props.getRevisionNumber()),
            props.getLastPrintedInternal(),
            props.getCreatedTimeInternal(),
            props.getLastSavedTimeInternal(),
            props.getCategory(),
            props.getContentStatus(),
            props.get(PropertyName.LANGUAGE).getValue().toString(),
            props.get(PropertyName.DOC_VERSION).getValue().toString());
    }

    private void writePackageCustomParts(String parentName, Collection<String> nonCustomPartNames) throws Exception
    {
        for (CustomPart cPart : getDocument().getPackageCustomParts())
        {
            if (!msString.equals(cPart.getParentPartName(), parentName))
                continue;

            // WORDSNET-5682 Adds +1 to custom part name if it already exists, mimics MSWord behavior.
            // WORDSNET-22604 We need to check for matches only with non-custom parts.
            if (nonCustomPartNames.contains(cPart.getName()))
            {
                int index = msString.lastIndexOf(cPart.getName(), ".", StringComparison.ORDINAL);
                cPart.setName(msString.insert(cPart.getName(), index, "1"));
            }

            if (cPart.isExternal())
            {
                getPackage().getRels().add(cPart.getRelationshipType(), cPart.getName(), true);
            }
            else
            {
                String pName = cPart.getParentPartName();
                OpcPackagePart pPart = (ROOT_NAME.equals(pName))
                    ? null
                    : getPackage().fetchPartByName(pName);

                // For nested custom parts apply preserved relation identifiers.
                addInnerRelationExplicitly(pPart, cPart);

                // WORDSNET-22604 If OpcPackagePart already exists, just do nothing.
                if (!getPackage().getParts().contains(cPart.getName()))
                {
                    OpcPackagePart opcPart = getPackage().createChildPart(
                        pPart,
                        cPart.getName(),
                        cPart.getContentType(),
                        cPart.getRelationshipType());

                    // It is necessary to use this constructor to avoid UnauthorizedAccessException while writing
                    // resources as base64. Some of constructors do not expose the underlying stream as the result
                    // "GetBuffer" throws UnauthorizedAccessException.
                    opcPart.setStream(new MemoryStream(cPart.getData(), 0, cPart.getData().length, true, true));

                    // WORDSNET-10389 Custom parts can has relationships with another parts recursively.
                    writePackageCustomParts(opcPart.getName(), nonCustomPartNames);
                }
            }
        }
    }

    /// <summary>
    /// Add nested custom part relation data to relationship collection of the parent element.
    /// Method applied only for parts stored in package.
    /// </summary>
    /// <param name="pPart">Parent part which contains target relationship collection.</param>
    /// <param name="cPart">Custom nested part which related with parent.</param>
    private static void addInnerRelationExplicitly(OpcPackagePart pPart, CustomPart cPart)
    {
        Debug.assertTrue(cPart != null);

        if ((pPart == null) || cPart.isExternal())
            return;

        String keyName = OpcPackageBase.makeRelative(pPart.getName(), cPart.getName());
        pPart.getRels().add(cPart.getOriginalId(), cPart.getRelationshipType(), keyName, cPart.isExternal());
    }

    DocxRelationshipTypes getRelTypes()
    {
        return (mRelTypes != null ? mRelTypes : (mRelTypes = new DocxRelationshipTypes(getCompliance() == OoxmlComplianceCore.ISO_STRICT)));
    }

    DocxNamespaces getDocxNamespaces()
    {
        return (mDocxNamespaces != null ? mDocxNamespaces : (mDocxNamespaces = new DocxNamespaces(getCompliance() == OoxmlComplianceCore.ISO_STRICT)));
    }

    /// <summary>
    /// Creates an XML part at the root of the package and creates a builder for it.
    /// </summary>
    DocxBuilder createPartAndBuilder(String partName, String contentType, String relType) throws Exception
    {
        OpcPackagePart part = getPackage().createChildPart(null, partName, contentType, relType);
        return new DocxBuilder(part, isPrettyFormat(), getCompliance(), MsWordVersionCore.UNSPECIFIED, getWarningCallback());
    }

    OpcPackageBase getPackage() { return mPackage; }; private void setPackage(OpcPackageBase value) { mPackage = value; };

    private OpcPackageBase mPackage;

    Document getDocument() { return getSaveInfo().Document; }

    SaveInfo getSaveInfo() { return mSaveInfo; }; void setSaveInfo(SaveInfo value) { mSaveInfo = value; };

    private SaveInfo mSaveInfo;

    private boolean isPrettyFormat() { return getSaveOptions().getPrettyFormat(); }

    /// <summary>
    /// Generated names for embedded parts are sequentially numbered. There is one sequence per relationship type.
    /// One sequence goes across the main and glossary documents.
    /// </summary>
    int getNextEmbeddedPartNumber(String relType)
    {
        int i = mEmbeddedPartNumbers.get(relType);
        if (StringToIntDictionary.isNullSubstitute(i))
            i = 0;

        i++;
        mEmbeddedPartNumbers.set(relType, i);
        return i;
    }

    /// <summary>
    /// If the given image was already written, returns its part name.
    /// Otherwise returns null.
    /// </summary>
    String getImagePartName(byte[] imageBytes)
    {
        // andrnosk: we cannot use byte array as a key, because byte[]{1,2,3} != byte[]{1,2,3},
        // that is why use MD4Hash.
        return mImageBytesTable.get(HashUtil.getSHA512Hash(imageBytes));
    }

    /// <summary>
    /// Adds a mapping between an image that was already written and its part name
    /// so it can be reused if the same image occurs in the document again.
    /// </summary>
    void addImageBytes(byte[] imageBytes, String imagePartName)
    {
        // andrnosk: we cannot use byte array as a key, because byte[]{1,2,3} != byte[]{1,2,3},
        // that is why use MD4Hash.
        mImageBytesTable.add(HashUtil.getSHA512Hash(imageBytes), imagePartName);
    }

    IWarningCallback getWarningCallback() { return getSaveInfo().Document.getWarningCallback(); }

    private OoxmlSaveOptions getSaveOptions() { return (OoxmlSaveOptions)getSaveInfo().SaveOptions; }

    /// <summary>
    /// Gets the OOXML version for the output document.
    /// </summary>
    private /*OoxmlComplianceCore*/int getCompliance() { return OoxmlComplianceInfo.getCompliance(getDocument().getComplianceInfo(), getSaveOptions()); }

    private /*final*/ BytesHashToObjDictionary<String> mImageBytesTable = new BytesHashToObjDictionary<String>();
    /// <summary>
    /// Key is a string that is a relationship type.
    /// Value is an integer number last used to generate a name for an embedded part of that relationship type.
    /// Used to generate names like image1.png, image2.png and so on.
    /// </summary>
    private /*final*/ StringToIntDictionary mEmbeddedPartNumbers = new StringToIntDictionary();

    private TempFiler mTempFiler;
    private DocxRelationshipTypes mRelTypes;
    private DocxNamespaces mDocxNamespaces;

    private static final String ROOT_NAME = "/";
}

