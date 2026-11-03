// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.UriUtil;
import com.aspose.StringUtil;
import com.aspose.words.ref.Ref;


/// <summary>
/// Writes mail merge settings to DOCX.
/// </summary>
class DocxMailMergePrWriter extends NrxMailMergePrWriter
{
    /// <summary>
    /// Writes the specified settings to the specified writer.
    /// </summary>
    void write(MailMergeSettings mailMergeSettings, DocxDocumentWriterBase writer) throws Exception
    {
        mWriter = writer;
        writeCore(mailMergeSettings, writer);
    }

    protected /*override*/ void doWriteDataSource(String value)
    {
        writeSource("w:dataSource", mWriter.getRelTypes().getMailMergeDataSource(), value);
    }

    protected /*override*/ void doWriteHeaderSource(String value)
    {
        writeSource("w:headerSource", mWriter.getRelTypes().getMailMergeHeaderSource(), value);
    }

    protected /*override*/ void doWriteOdsoSource(String value)
    {
        // RK Force escaping ODSO source because it might be a file path and will not be escaped by the packaging code.
        writeSource("w:src", mWriter.getRelTypes().getMailMergeDataSource(), UriUtil.escapeHref(value));
    }

    private void writeSource(String elemName, String relationshipType, String value)
    {
        if (StringUtil.hasChars(value))
        {
            DocxBuilder builder = mWriter.getCurrentBuilder();
            builder.writeRelationshipId(elemName, builder.getPart().getRels().add(relationshipType, value, true));
        }
    }

    protected /*override*/ void doWriteRecipientData(Odso odso) throws Exception
    {
        String relId = writeRecipientDataPart(odso);
        if (StringUtil.hasChars(relId))
            mWriter.getCurrentBuilder().writeRelationshipId("w:recipientData", relId);
    }

    protected /*override*/ void doWriteOdsoTypeAndHeader(Odso odso)
    {
        mWriter.getCurrentBuilder().writeVal("w:type", MailMergeEnum.odsoDataSourceTypeToXml(odso.getDataSourceType(), true));
        mWriter.getCurrentBuilder().writeValIfTrue("w:fHdr", odso.getFirstRowContainsColumnNames());
    }

    private String writeRecipientDataPart(Odso odso) throws Exception
    {
        if (odso.getRecipientDatas().getCount() == 0)
            return null;

        // This creates a child part of the settings.xml part.
        String relId = null;
        Ref<String> referenceToRelId = new Ref<String>(relId);
        DocxBuilder builder = mWriter.createChildPartAndBuilder(
            mWriter.getCurrentBuilder().getPart(),
            "recipientData.xml",
            DocxContentType.MAIL_MERGE_RECIPIENT_DATA,
            mWriter.getRelTypes().getMailMergeRecipientData(),
            /*out*/ referenceToRelId);
        relId = referenceToRelId.get();

        builder.startDocumentWithStandardNamespaces("w:recipients");

        for (OdsoRecipientData recipientData : odso.getRecipientDatas())
        {
            builder.startElement("w:recipientData");
            builder.writeVal("w:active", recipientData.getActive());
            builder.writeVal("w:column", recipientData.getColumn());         // required
            builder.writeVal("w:uniqueTag", recipientData.getUniqueTag());   // required
            builder.endElement();   //w:recipientData
        }

        builder.endDocument();   //w:recipients

        return relId;
    }

    private DocxDocumentWriterBase mWriter;
}

