// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Convert;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.UriUtil;
import com.aspose.LocaleConverter;
import com.aspose.Language;


/// <summary>
/// Base class, helps to read mail merge settings from DOCX and WML documents.
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
abstract class NrxMailMergePrReaderBase
{
    /// <summary>
    /// Reads 2.14.20 mailMerge (Mail Merge Settings)
    /// </summary>
    void read(NrxDocumentReaderBase reader, MailMergeSettings mailMergeSettings) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        // The spec says this is the default value, but I think this is the default value only when the mail merge settings are present.
        // When mail merge settings are not specified, then the default value is NotAMergeDocument. So I have to set this value as a default
        // when encounter mail merge settings to read.
        mailMergeSettings.setMainDocumentType(MailMergeMainDocumentType.FORM_LETTERS);

        while (xmlReader.readChild("mailMerge"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"activeRecord"*/0:    // docx and wml
                    mailMergeSettings.setActiveRecord(xmlReader.readIntVal());
                    break;
                case /*"addressFieldName"*/1:    // docx and wml
                    mailMergeSettings.setAddressFieldName(xmlReader.readVal());
                    break;
                case /*"checkErrors"*/2: // docx and wml
                    mailMergeSettings.setCheckErrors((/*MailMergeCheckErrors*/int)xmlReader.readIntVal());
                    break;
                case /*"connectString"*/3: // docx and wml
                {
                    String connectString = xmlReader.readVal();

                    if (!msString.isNullOrEmpty(connectString))
                        mailMergeSettings.setConnectString(connectString);
                    break;
                }
                case /*"dataSource"*/4:  // docx and wml
                    mailMergeSettings.setDataSource(reader.isDocx()
                        ? reader.getRelationshipTarget(xmlReader.readId())
                        : xmlReader.readVal());
                    break;
                case /*"dataType"*/5:    // docx and wml
                    mailMergeSettings.setDataType(MailMergeEnum.xmlToMailMergeDataType(xmlReader.readVal()));
                    break;
                case /*"destination"*/6: // docx and wml
                    mailMergeSettings.setDestination(MailMergeEnum.xmlToMailMergeDestination(xmlReader.readVal()));
                    break;
                case /*"doNotSuppressBlankLines"*/7: // docx and wml
                    mailMergeSettings.setDoNotSupressBlankLines(xmlReader.readBoolVal());
                    break;
                case /*"headerSource"*/8:    // docx and wml
                    mailMergeSettings.setHeaderSource(reader.isDocx()
                        ? reader.getRelationshipTarget(xmlReader.readId())
                        : xmlReader.readVal());
                    break;
                case /*"linkToQuery"*/9: // docx and wml
                    mailMergeSettings.setLinkToQuery(xmlReader.readBoolVal());
                    break;
                case /*"mailAsAttachment"*/10:    // docx and wml
                    mailMergeSettings.setMailAsAttachment(xmlReader.readBoolVal());
                    break;
                case /*"mailSubject"*/11: // docx and wml
                    mailMergeSettings.setMailSubject(xmlReader.readVal());
                    break;
                case /*"mainDocumentType"*/12:    // docx and wml
                    mailMergeSettings.setMainDocumentType(MailMergeEnum.xmlToMailMergeMainDocumentType(xmlReader.readVal()));
                    break;
                case /*"odso"*/13:    // docx and wml
                    readOdso(reader, mailMergeSettings.getOdso());
                    break;
                case /*"query"*/14:   // docx and wml
                    mailMergeSettings.setQuery(xmlReader.readVal());
                    break;
                case /*"viewMergedData"*/15:  // docx and wml
                    mailMergeSettings.setViewMergedData(xmlReader.readBoolVal());
                    break;
                // This is WML only. Ignore for now.
                case /*"defaultSQL"*/16:
                    xmlReader.ignoreElement();
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    protected /*virtual*/ void readRecipientData(NrxDocumentReaderBase reader, Odso odso) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OdsoRecipientData result = new OdsoRecipientData();

        while (xmlReader.readChild("recipientData"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"active"*/17:
                    result.setActive(xmlReader.readBoolVal());
                    break;
                case /*"column"*/18:
                    result.setColumn(xmlReader.readIntVal());
                    break;
                case /*"uniqueTag"*/19:
                    // Word 2007 in DOCX writes base64 encoded, but for WordML this seems to be just plain bytes.
                    if (reader.isDocx())
                        result.setUniqueTag(Convert.fromBase64String(xmlReader.readVal()));
                    else
                        result.setUniqueTag(Encoding.getUnicode().getBytes(xmlReader.readVal()));
                    break;
                case /*"hash"*/20: // wml only
                    result.setHash(xmlReader.readIntVal());
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        odso.getRecipientDatas().add(result);
    }

    /// <summary>
    /// Reads 2.14.25 odso (Office Data Source Object Settings)
    /// </summary>
    private void readOdso(NrxDocumentReaderBase reader, Odso odso) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("odso"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"colDelim"*/21:    // docx and wml
                    odso.setColumnDelimiter((char)xmlReader.readIntVal());
                    break;
                case /*"fHdr"*/22:    // docx and wml
                    odso.setFirstRowContainsColumnNames(xmlReader.readBoolVal());
                    break;
                case /*"fieldMapData"*/23:    // docx and wml
                    readFieldMapData(reader, odso);
                    break;
                case /*"recipientData"*/24:   // docx and wml
                {
                    readRecipientData(reader, odso);
                    break;
                }
                case /*"src"*/25: // docx and wml
                    // RK When reading from DOCX force unescaping because it might an incomplete path
                    // and does not get unescaped by the lower level functions.
                    odso.setDataSource(reader.isDocx()
                        ? UriUtil.unescapeHref(reader.getRelationshipTarget(xmlReader.readId()))
                        : xmlReader.readVal());
                    break;
                case /*"table"*/26:   // docx and wml
                    odso.setTableName(xmlReader.readVal());
                    break;
                case /*"type"*/27:    // docx only
                case /*"jdsoType"*/28:    // wml only
                    odso.setDataSourceType(MailMergeEnum.xmlToOdsoDataSourceType(xmlReader.readVal()));
                    break;
                case /*"udl"*/29: // docx and wml
                    odso.setUdlConnectString(xmlReader.readVal());
                    break;

                // These are WML only. Let's ignore for now.
                case /*"filter"*/30:
                case /*"sort"*/31:
                    xmlReader.ignoreElement();
                    break;

                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 2.14.15 fieldMapData (External Data 1 Source to Merge Field Mapping).
    /// </summary>
    private static void readFieldMapData(NrxDocumentReaderBase reader, Odso odso)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OdsoFieldMapData result = new OdsoFieldMapData();

        while (xmlReader.readChild("fieldMapData"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"column"*/18:  // docx and wml
                    result.setColumnSafe(xmlReader.readIntVal());
                    break;
                case /*"dynamicAddress"*/32:  // docx and wml
                    result.setDynamicAddress(xmlReader.readBoolVal());
                    break;
                case /*"lid"*/33: // docx and wml
                {
                    String lid = xmlReader.readVal();
                    int language = reader.isDocx()
                        ? LocaleConverter.docxTagToLocale(lid)
                        : LocaleConverter.wmlTagToLocale(lid);

                    result.setLanguage((/*Language*/int)language);
                    break;
                }
                case /*"mappedName"*/34:  // docx and wml
                    result.setMappedName(xmlReader.readVal());
                    break;
                case /*"name"*/35:    // docx and wml
                    result.setName(xmlReader.readVal());
                    break;
                case /*"type"*/27:    // docx and wml
                    result.setType(MailMergeEnum.xmlToOdsoFieldMappingType(xmlReader.readVal()));
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        odso.getFieldMapDatas().add(result);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"activeRecord",
		"addressFieldName",
		"checkErrors",
		"connectString",
		"dataSource",
		"dataType",
		"destination",
		"doNotSuppressBlankLines",
		"headerSource",
		"linkToQuery",
		"mailAsAttachment",
		"mailSubject",
		"mainDocumentType",
		"odso",
		"query",
		"viewMergedData",
		"defaultSQL",
		"active",
		"column",
		"uniqueTag",
		"hash",
		"colDelim",
		"fHdr",
		"fieldMapData",
		"recipientData",
		"src",
		"table",
		"type",
		"jdsoType",
		"udl",
		"filter",
		"sort",
		"dynamicAddress",
		"lid",
		"mappedName",
		"name"
	);

}

