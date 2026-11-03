// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.LocaleConverter;


/// <summary>
/// Common base class for writing mail merge settings to DOCX an WML.
/// </summary>
abstract class NrxMailMergePrWriter
{
    /// <summary>
    /// The concrete implementations must call this method to initiate writing of mail merge settings.
    /// </summary>
    protected void writeCore(MailMergeSettings mailMergeSettings, INrxWriterContext writer) throws Exception
    {
        if (mailMergeSettings.isEmpty())
            return;

        mMailMergeSettings = mailMergeSettings;
        mWriter = writer;

        NrxXmlBuilder builder = mWriter.getBuilder();
        builder.startElement("w:mailMerge");

        builder.writeValRequired("w:mainDocumentType", MailMergeEnum.mailMergeMainDocumentTypeToXml(mMailMergeSettings.getMainDocumentType(), writer.isDocx()));
        builder.writeValIfTrue("w:linkToQuery", mMailMergeSettings.getLinkToQuery());
        builder.writeValRequired("w:dataType", MailMergeEnum.mailMergeDataTypeToXml(mMailMergeSettings.getDataType(), writer.isDocx()));
        builder.writeValEvenIfEmpty("w:connectString", mMailMergeSettings.getConnectString());
        builder.writeVal("w:query", mMailMergeSettings.getQuery());
        doWriteDataSource(mMailMergeSettings.getDataSource());
        doWriteHeaderSource(mMailMergeSettings.getHeaderSource());
        builder.writeValIfTrue("w:doNotSuppressBlankLines", mMailMergeSettings.getDoNotSupressBlankLines());
        builder.writeVal("w:destination", MailMergeEnum.mailMergeDestinationToXml(mMailMergeSettings.getDestination(), writer.isDocx()));
        builder.writeVal("w:addressFieldName", mMailMergeSettings.getAddressFieldName());
        builder.writeVal("w:mailSubject", mMailMergeSettings.getMailSubject());
        builder.writeValIfTrue("w:mailAsAttachment", mMailMergeSettings.getMailAsAttachment());
        builder.writeValIfTrue("w:viewMergedData", mMailMergeSettings.getViewMergedData());
        builder.writeValIfNotDefault("w:activeRecord", mMailMergeSettings.getActiveRecord(), 0);
        builder.writeValIfNotDefault("w:checkErrors", (int)mMailMergeSettings.getCheckErrors(), (int)MailMergeCheckErrors.DEFAULT);
        writeOdso();

        builder.endElement(); //w:mailMerge
    }

    private void writeOdso() throws Exception
    {
        NrxXmlBuilder builder = mWriter.getBuilder();

        builder.startElement("w:odso");

        Odso odso = mMailMergeSettings.getOdso();
        builder.writeVal("w:udl", odso.getUdlConnectString());
        builder.writeVal("w:table", odso.getTableName());

        doWriteOdsoSource(odso.getDataSource());

        int colDelim = odso.getColumnDelimiter(); // RK Conversion to integer is required in order to invoke the correct overload for writing the value.
        builder.writeValIfNotDefault("w:colDelim", colDelim, 0);

        doWriteOdsoTypeAndHeader(odso);

        for (OdsoFieldMapData fieldMapData : odso.getFieldMapDatas())
            writeFieldMapData(fieldMapData, builder);

        doWriteRecipientData(odso);

        builder.endElement();   //w:odso
    }

    private void writeFieldMapData(OdsoFieldMapData fieldMapData, NrxXmlBuilder builder)
    {
        builder.startElement("w:fieldMapData");

        builder.writeVal("w:type", MailMergeEnum.odsoFieldMappingTypeToXml(fieldMapData.getType(), mWriter.isDocx()));
        builder.writeVal("w:name", fieldMapData.getName());
        builder.writeVal("w:mappedName", fieldMapData.getMappedName());
        
        // MS Word seems to write the column index to DOCX only if the mapping is initialized and always writes to WML.
        if (!mWriter.isDocx() || StringUtil.hasChars(fieldMapData.getName()))
            builder.writeVal("w:column", fieldMapData.getColumn());

        String lidTag = mWriter.isDocx()
            ? LocaleConverter.localeToDocxTag((int)fieldMapData.getLanguage())
            : LocaleConverter.localeToWmlTag((int)fieldMapData.getLanguage());

        builder.writeVal("w:lid", lidTag);
        builder.writeValIfTrue("w:dynamicAddress", fieldMapData.getDynamicAddress());

        builder.endElement();   //w:fieldMapData
    }

    // These methods are to be implemented by DOCX and WML because they are different.
    protected abstract void doWriteDataSource(String value) throws Exception;
    protected abstract void doWriteHeaderSource(String value) throws Exception;
    protected abstract void doWriteOdsoSource(String value) throws Exception;
    protected abstract void doWriteRecipientData(Odso odso) throws Exception;
    protected abstract void doWriteOdsoTypeAndHeader(Odso odso) throws Exception;

    private MailMergeSettings mMailMergeSettings;
    private INrxWriterContext mWriter;
}

