// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/04/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.ms;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.Guid;
import com.aspose.Pair;
import com.aspose.Debug;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import java.util.ArrayList;
import com.aspose.StringUtil;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.StreamWriter;
import com.aspose.ms.System.Text.UTF8Encoding;
import com.aspose.HashSetGeneric;
import com.aspose.SortedIntegerListGeneric;
import java.util.Map;
import com.aspose.SortedStringListGeneric;
import com.aspose.DictionaryExtension;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msObject;
import com.aspose.CompressedData;
import com.aspose.FileFormat;
import com.aspose.ImageUtil;
import com.aspose.ImageSizeCore;
import java.util.HashMap;
import com.aspose.ArrayUtil;
import com.aspose.BitUtil;
import com.aspose.Language;
import com.aspose.ms.System.Convert;
import com.aspose.FileSystem;
import com.aspose.ms.System.IO.MemoryStream;
import java.util.Collections;
import com.aspose.ms.System.Collections.SortedList;
import com.aspose.UnicodeBlocks;
import com.aspose.UnicodeUtil;
import com.aspose.StringToObjDictionary;
import com.aspose.ms.System.IO.TextWriter;
import java.util.Comparator;
import com.aspose.StringOrdinalComparer;
import com.aspose.MemoryStorage;
import com.aspose.StreamUtil;
import com.aspose.HashUtil;
import com.aspose.DigestAlgorithm;
import com.aspose.IntList;
import com.aspose.FormatterPal;
import com.aspose.ms.System.msMath;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ConvertUtilCore;
import com.aspose.words.ref.RefInt;


class MsoEnvelope
{
    /*enum*/ final class FlagStatusType
    {
        private FlagStatusType(){}
        
        public static final int NOT_FLAGGED = 0x00;
        public static final int FLAGGED = 0x01;
        public static final int COMPLETED = 0x02;

        public static final int length = 3;
    }

    /*enum*/ final class SensitivityType
    {
        private SensitivityType(){}
        
        public static final int NORMAL = 0x00;
        public static final int PERSONAL = 0x01;
        public static final int PRIVATE = 0x02;
        public static final int CONFIDENTIAL = 0x03;

        public static final int length = 4;
    }

    private /*enum*/ final class PropTagType
    {
        private PropTagType(){}
        
        public static final int LONG = 0x0003;
        public static final int NULL = 0x0001;
        public static final int BOOLEAN = 0x000B;
        public static final int SYSTIME = 0x0040;
        public static final int ERROR = 0x000A;
        public static final int STRING_8 = 0x001E;
        public static final int UNICODE = 0x001F;
        public static final int BINARY = 0x0102;
        public static final int MV_STRING_8 = 0x101E;
        public static final int MV_BINARY = 0x1102;

        public static final int length = 10;
    }

    /*enum*/ final class MsoEnvSecurityFlags
    {
        private MsoEnvSecurityFlags(){}
        
        public static final int NONE = 0x00;
        public static final int SIGNED = 0x01;
        public static final int ENCRYPTED = 0x02;

        public static final int length = 3;
    }

    /*enum*/ final class ImportanceType
    {
        private ImportanceType(){}
        
        public static final int LOW = 0x00;
        public static final int NORMAL = 0x01;
        public static final int HIGH = 0x02;

        public static final int length = 3;
    }

    MsoEnvelope(BinaryReader reader) throws Exception
    {
        Guid clsid = new Guid(reader.readBytes(16));
        int ver = reader.readInt32();

        LastSentTime = readDate(reader);
        FlagStatus = (/*FlagStatusType*/int)reader.readInt32();
        ReplyTime = readDate(reader);

        Request = readString(reader);
        int sentRepresentingEntryIdSize = reader.readInt32();
        SentRepresentingEntryId = reader.readBytes(sentRepresentingEntryIdSize);
        SentRepresentingName = readString(reader);
        InetAcctStamp = readString(reader);
        InetAcctName = readString(reader);

        ExpireTime = readDate(reader);
        DeferredDeliveryTime = readDate(reader);
        DeleteAfterSubmit = reader.readInt32() != 0;
        SecurityFlags = (/*MsoEnvSecurityFlags*/int)reader.readInt32();
        OriginatorDeliveryReportRequested = reader.readInt32() != 0;
        ReadReceiptRequested = reader.readInt32() != 0;

        Categories = readString(reader);
        Sensitivity = (/*SensitivityType*/int)reader.readInt32();
        Importance = (/*ImportanceType*/int)reader.readInt32();

        Subject = readString(reader);
        VotingOptions = readString(reader);

        // ReplyRecipients
        ReplyRecipients = readEnvRecipientCollection(reader);

        // ContactLinkRecipients
        ContactLinkRecipients = readEnvRecipientCollection(reader);

        // Recipients
        Recipients = readEnvRecipientCollection(reader);
    }

    private static Pair[][] readEnvRecipientCollection(BinaryReader reader) throws Exception
    {
        // EnvRecipientCollection
        /*uint*/int recipientCollTag = reader.readUInt32();
        Debug.assertTrue(0xDCCA0123L == (recipientCollTag & 0xFFFFFFFFL));
        int recipientCollVer = reader.readInt32();
        Debug.assertTrue(1 == recipientCollVer);

        int count = reader.readInt32();
        if (count == 0)
            return null;

        Pair[][] mainList = new Pair[count][];

        for (int i = 0; i < count; i++)
        {
            // EnvRecipientProperties
            int count2 = reader.readInt32();
            // Read ignored value.
            reader.readInt32();

            Pair[] childList = new Pair[count2];
            mainList[i] = childList;

            // EnvRecipientProperty
            for (int j = 0; j < count2; j++)
            {
                /*PropTagType*/int propTag = (/*PropTagType*/int)reader.readInt16();
                // Read ignored value.
                reader.readInt16();

                Object value;
                switch (propTag)
                {
                    case PropTagType.LONG:
                    case PropTagType.ERROR:
                        value = reader.readInt32();
                        break;

                    case PropTagType.BOOLEAN:
                        value = reader.readInt16() != 0;
                        break;

                    case PropTagType.UNICODE:
                    {
                        int size = reader.readInt16();
                        byte[] unicode = reader.readBytes(size);
                        value = com.aspose.ms.System.Text.Encoding.getUnicode().getString(unicode);
                        break;
                    }

                    case PropTagType.BINARY:
                    {
                        int size = reader.readInt16();
                        value = reader.readBytes(size);
                        break;
                    }

                    default:
                        throw new IllegalStateException();
                }

                childList[j] = new Pair(propTag, value);
            }
        }

        return mainList;
    }

    private static DateTime readDate(BinaryReader reader) throws Exception
    {
        final int UNDEFINED_TIME = 0x5AE980E0;

        int since = reader.readInt32();

        if (since == UNDEFINED_TIME)
            return DateTime.MinValue;

        DateTime dateTime = new DateTime(1601, 1, 1);
        return dateTime.addMinutes(since);
    }

    private static String readString(BinaryReader reader) throws Exception
    {
        short size = reader.readInt16();
        byte[] bytes = reader.readBytes(size * 2);

        return Encoding.getUnicode().getString(bytes);
    }

    String Subject;
    String Request;
    /*FlagStatusType*/int FlagStatus;

    DateTime LastSentTime;
    DateTime ReplyTime;
    DateTime ExpireTime;
    DateTime DeferredDeliveryTime;

    /*SensitivityType*/int Sensitivity;
    /*ImportanceType*/int Importance;
    /*MsoEnvSecurityFlags*/int SecurityFlags;
    String VotingOptions;

    byte[] SentRepresentingEntryId;

    String SentRepresentingName;
    String InetAcctStamp;
    String InetAcctName;

    boolean DeleteAfterSubmit;

    boolean OriginatorDeliveryReportRequested;
    boolean ReadReceiptRequested;

    String Categories;


    Pair[][] ReplyRecipients;
    Pair[][] ContactLinkRecipients;
    Pair[][] Recipients;
}

/// <summary>
/// Log unique warnings.
/// </summary>
class UniqueWarningCollection implements IWarningCallback, Iterable<WarningInfo>
{
    public void /*IWarningCallback.*/warning(WarningInfo info)
    {
        for (WarningInfo item : mItems)
        {
            if ((item.getWarningType() == info.getWarningType()) &&
                (item.getSource() == info.getSource()) &&
                (msString.equals(item.getDescription(), info.getDescription())))
                return;
        }

        msArrayList.add(mItems, info);
    }

    public Iterator<WarningInfo> iterator()
    {
        return mItems.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    int getCount() { return mItems.size(); }

    private /*final*/ ArrayList<WarningInfo> mItems = new ArrayList<WarningInfo>();
}


/// <summary>
/// Utility class outputs whole document model into file.
/// </summary>
/// <remarks>
/// AM. This is very draft but still useful for me.
/// Model dump shows for example how AW/Word converts document upon re-saving and so on.
/// I'd like this test utility integrated.
/// </remarks>
class ModelDump
{
    private ModelDump()
    {
        // No ctor.
    }

    /// <summary>
    /// Saves document model into specified file.
    /// </summary>
    static void save(String fileName) throws Exception
    {
        gWarningCallback = new UniqueWarningCollection();
        LoadOptions lo = new LoadOptions();
        lo.setWarningCallback(gWarningCallback);

        save(TestUtil.open(fileName, lo), fileName + ".model");
    }

    /// <summary>
    /// Saves document model into specified file.
    /// </summary>
    static void save(String fileName, LoadOptions lo) throws Exception
    {
        gWarningCallback = new UniqueWarningCollection();
        lo.setWarningCallback(gWarningCallback);

        save(TestUtil.open(fileName, lo), fileName + ".model");
    }

    /// <summary>
    /// Saves document model into specified file.
    /// </summary>
    static void save(Document doc, String fileName) throws Exception
    {
        sortHeadersAndFooters(doc);

        if (JoinRuns)
            joinRunsWithSameFormatting(doc);

        if (ShowListLabels)
            doc.updateListLabels();

        if (NoIstdInStyles)
            for (Style style : doc.getStyles())
            {
                style.getRunPr().remove(FontAttr.ISTD);
                style.getParaPr().remove(ParaAttr.ISTD);
            }

        if (GlobalTableAttributes)
            for (Table table : (Iterable<Table>) doc.getChildNodes(NodeType.TABLE, true))
                extractGlobalTableAttrs(table);

        if (UnifyToggles)
            convertToggles(doc);

        if (LocalizeFontNames)
        {
            FontInfoCollection fonts = doc.getFontInfos();

            for (FontInfo fontInfo : fonts)
            {
                if (StringUtil.hasChars(fontInfo.getAltName()))
                    continue;

                // Well-known CJK fonts.
                switch (gStringSwitchMap.of(fontInfo.getName()))
                {
                    case /*"宋体"*/0:
                        fontInfo.setAltName("SimSun");
                        break;

                    case /*"黑体"*/1:
                        fontInfo.setAltName("SimHei");
                        break;
                }
            }

            doLocalizeFontNames(doc.getStyles().getDefaultRunPr(), fonts);
            for (Style style : doc.getStyles())
                doLocalizeFontNames(style.getRunPr(), fonts);

            doLocalizeFontNames(doc, doc.getFontInfos());
        }

        if (LocalizeStyleNames)
            doLocalizeStyleNames(doc);

        refine(doc);

        if (NormalizeHorizontalMerge)
            for (Table table : (Iterable<Table>) doc.getChildNodes(NodeType.TABLE, true))
                table.normalizeHorizontalMerge();

        if (Cleanup)
        {
            CleanupOptions options = new CleanupOptions(); { options.setUnusedBuiltinStyles(true); }
            doc.cleanup(options);
        }

        FileName = fileName;
        FileStream fileStream = File.create(FileName);
        Writer = new StreamWriter(fileStream, new UTF8Encoding(true));

        dumpNode(doc);

        Writer.close();
    }

    private static void doLocalizeFontNames(CompositeNode parentNode, FontInfoCollection fonts)
    {
        for (Node node : (Iterable<Node>) parentNode.getChildNodes(NodeType.ANY, true))
        {
            IInline inline = ms.as(node, IInline.class);
            if (inline != null)
            {
                doLocalizeFontNames(inline.getRunPr_IInline(), fonts);
                // Don't break here, shapes need additional processing.
            }

            switch (node.getNodeType())
            {
                case NodeType.PARAGRAPH:
                    doLocalizeFontNames(((Paragraph)node).getParagraphBreakRunPr(), fonts);
                    break;

                case NodeType.SHAPE:
                    Shape shape = (Shape)node;
                    if (shape.getRunPr().getAlternateContent() != null)
                        doLocalizeFontNames(shape.getRunPr().getAlternateContent().getFallBack(), fonts);
                    break;

                default:
                    break;
            }
        }
    }

    /// <summary>
    /// Replaces non-ASCII font names to ASCII alternative font name.
    /// </summary>
    private static void doLocalizeFontNames(RunPr runPr, FontInfoCollection fonts)
    {
        for (int key : RunPr.FONT_NAME_ATTRIBUTES)
        {
            ComplexFontName font = (ComplexFontName)runPr.get(key);

            if (font == null)
                continue;

            if (font.isThemeFont())
                continue;

            String fontName = font.getName();
            if (isAscii(fontName))
                continue;

            FontInfo fontInfo = fonts.get(fontName);

            for (String altName : fontInfo.getAltNameList())
                if (isAscii(altName))
                {
                    runPr.set(key, ComplexFontName.fromName(altName));
                    break;
                }
        }
    }

    /// <summary>
    /// Sets style names to the English language.
    /// </summary>
    private static void doLocalizeStyleNames(Document doc)
    {
        StyleCollection builtin = StyleCollection.getBuiltInStyles(LoadFormat.DOCX);

        for (Style style : doc.getStyles())
        {
            if (!style.getBuiltIn())
                continue;

            Style builtinStyle = builtin.getBySti(style.getStyleIdentifier(), false);

            if (builtinStyle == null)
                continue;

            style.setNameCore(builtinStyle.getName(), true);

            if (style.getLinkedIstd() != StyleIndex.NIL && style.getType() == StyleType.PARAGRAPH)
            {
                Style linkedStyle = doc.getStyles().getByIstd(style.getLinkedIstd(), false);
                linkedStyle.setNameCore(builtinStyle.getName() + " Char");
            }
        }
    }

    /// <summary>
    /// Checks that given string contains only ASCII characters.
    /// </summary>
    private static boolean isAscii(String text)
    {
        for (int i = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            if (((int)c) > 255)
                return false;
        }

        return true;
    }

    private static void convertToggles(Style style, HashSetGeneric<Style> alreadyProcessed)
    {
        if (alreadyProcessed.contains(style))
            return;

        // Convert base style first.
        if (style.getBaseStyle() != null)
            convertToggles(style.getBaseStyle(), alreadyProcessed);

        for (int key : G_TOGGLE_ATTRS)
        {
            if (style.getRunPr().contains(key))
            {
                AttrBoolEx value = (AttrBoolEx)style.getRunPr().get(key);

                if ((value == AttrBoolEx.TRUE) || (value == AttrBoolEx.FALSE))
                {
                    AttrBoolEx inheritedValue = (AttrBoolEx)((IRunAttrSource)style).fetchInheritedRunAttr(key);

                    style.getRunPr().setAttr(key, (value == inheritedValue)
                        ? AttrBoolEx.SAME
                        : AttrBoolEx.TOGGLE);
                }
            }
        }

        alreadyProcessed.add(style);
    }

    private static void convertToggles(IInline inline)
    {
        for (int key : G_TOGGLE_ATTRS)
        {
            if (inline.getRunPr_IInline().contains(key))
            {
                AttrBoolEx value = (AttrBoolEx)inline.getRunPr_IInline().get(key);

                if ((value == AttrBoolEx.TRUE) || (value == AttrBoolEx.FALSE))
                {
                    AttrBoolEx inheritedValue = (AttrBoolEx)(inline).fetchInheritedRunAttr(key);

                    inline.getRunPr_IInline().setAttr(key, (value == inheritedValue)
                        ? AttrBoolEx.SAME
                        : AttrBoolEx.TOGGLE);
                }
            }
        }
    }

    private static void convertToggles(Paragraph para)
    {
        for (int key : G_TOGGLE_ATTRS)
        {
            if (!para.getParagraphBreakRunPr().contains(key))
                continue;

            AttrBoolEx value = (AttrBoolEx)para.getParagraphBreakRunPr().get(key);

            if ((value == AttrBoolEx.TRUE) || (value == AttrBoolEx.FALSE))
            {
                AttrBoolEx inheritedValue = (AttrBoolEx)((IRunAttrSource)para).fetchInheritedRunAttr(key);

                para.getParagraphBreakRunPr().setAttr(key, (value == inheritedValue)
                    ? AttrBoolEx.SAME
                    : AttrBoolEx.TOGGLE);
            }
        }
    }

    private static void convertToggles(Document doc)
    {
        HashSetGeneric<Style> alreadyProcessed = new HashSetGeneric<Style>();
        for (Style style : doc.getStyles())
            convertToggles(style, alreadyProcessed);

        for (Node node : (Iterable<Node>) doc.getChildNodes(NodeType.ANY, true))
        {
            IInline inline = ms.as(node, IInline.class);

            if (inline != null)
                convertToggles(inline);
            else if (node.getNodeType() == NodeType.PARAGRAPH)
            {
                convertToggles((Paragraph)node);
            }
        }
    }

    private static void sortHeadersAndFooters(Document doc)
    {
        for (Section section : (Iterable<Section>) doc.getSections())
        {
            SortedIntegerListGeneric<HeaderFooter> sortedHeadersFooters = new SortedIntegerListGeneric<HeaderFooter>();
            for (HeaderFooter headerFooter : (Iterable<HeaderFooter>) section.getHeadersFooters())
            {
                sortedHeadersFooters.add(G_HEADER_SORT_ORDER[(int)headerFooter.getHeaderFooterType()], headerFooter);
                headerFooter.remove();
            }

            for (int i = 0; i < sortedHeadersFooters.getCount(); i++)
            {
                HeaderFooter headerFooter = sortedHeadersFooters.getByIndex(i);
                section.insertBefore(headerFooter, section.getBody());
            }
        }
    }

    private static void dumpTheme(Theme theme) throws Exception
    {
        write("\n[Theme]");
        write(msString.format("  [Fonts: {0}]", theme.getFontSchemeName()));
        write("    [Major]");

        dumpThemeFont(theme.getMajorFonts().LatinFontInfo.getName(), "HAnsi");
        dumpThemeFont(theme.getMajorFonts().EastAsianFontInfo.getName(), "EastAsia");
        dumpThemeFont(theme.getMajorFonts().ComplexScriptFontInfo.getName(), "Bidi");
        dumpSupplementalFonts(theme.getMajorFonts().getSupplementalFonts());

        write("    [Minor]");
        dumpThemeFont(theme.getMinorFonts().LatinFontInfo.getName(), "HAnsi");
        dumpThemeFont(theme.getMinorFonts().EastAsianFontInfo.getName(), "EastAsia");
        dumpThemeFont(theme.getMinorFonts().ComplexScriptFontInfo.getName(), "Bidi");
        dumpSupplementalFonts(theme.getMinorFonts().getSupplementalFonts());

        write(msString.format("  [Colors: {0}]", theme.getColors().getName()));

        for (/*ThemeColor*/int themeColor : G_ALL_THEME_COLORS)
            dumpThemeColor(theme, themeColor);

    }

    private static /*final*/ /*ThemeColor*/int[] G_ALL_THEME_COLORS = new /*ThemeColor*/int[]
            {
                ThemeColor.ACCENT_1, ThemeColor.ACCENT_2, ThemeColor.ACCENT_3, ThemeColor.ACCENT_4, ThemeColor.ACCENT_5, ThemeColor.ACCENT_6,
                ThemeColor.BACKGROUND_1, ThemeColor.BACKGROUND_2,
                ThemeColor.DARK_1, ThemeColor.DARK_2,
                ThemeColor.FOLLOWED_HYPERLINK, ThemeColor.HYPERLINK,
                ThemeColor.LIGHT_1, ThemeColor.LIGHT_2,
                ThemeColor.TEXT_1, ThemeColor.TEXT_2
            };

    private static void dumpThemeColor(Theme theme, /*ThemeColor*/int colorName) throws Exception
    {
        DmlColor dmlColor = theme.getColors().getColor(colorName);
        if (dmlColor == null)
            return;

        if (dmlColor.getColorType() == DmlColorType.HEX_RGB_COLOR)
        {
            DmlHexRgbColor hexRgb = (DmlHexRgbColor)dmlColor;
            write(msString.format("    {{{0}: '{1}'}}", colorName, hexRgb.getValue()));
        }
    }

    private static void dumpThemeFont(String fontName, String name) throws Exception
    {
        write(msString.format("      {{{0}: '{1}'}}", name, fontName));
    }

    private static void dumpSupplementalFonts(Map<String, ThemeSupplementalFont> table) throws Exception
    {
        SortedStringListGeneric<Object> sortedKeys = new SortedStringListGeneric<Object>();
        for (String script : table.keySet())
            sortedKeys.add(script, null);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sortedKeys.getCount(); i++)
        {
            String key = sortedKeys.getKey(i);
            ThemeSupplementalFont supplementalFont = DictionaryExtension.getValueOrNull(table, key);
            msStringBuilder.appendFormat(sb, "{0}='{1}', ", supplementalFont.getScript(), supplementalFont.getTypeface());
        }
        write(msString.format("      {{Supplemental: {0}}}", msString.trimEnd(sb.toString(), ' ', ',')));
    }

    private static void collapseExpandableAttrIfNeeded(Object childValue, Object baseValue)
    {
        if (childValue instanceof IExpandableAttr)
        {
            // Reverse merge the tab stop collection.
            ((IExpandableAttr)childValue).collapse((IExpandableAttr)baseValue);
        }
    }

    private static void collapse(AttrCollection baseAttrs, AttrCollection attrs)
    {
        if (baseAttrs == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "baseAttrs");

        for (int baseIndex = 0; baseIndex < baseAttrs.getCount(); baseIndex++)
        {
            int key = baseAttrs.getKey(baseIndex);

            int childIndex = attrs.indexOfKey(key);
            if (childIndex >= 0)
            {
                // The value exists both in the base collection and in the child collection.
                // We must compare the values to decide what to do.
                Object baseValue = baseAttrs.getByIndex(baseIndex);
                Object childValue = attrs.getByIndex(childIndex);
                if (baseValue.equals(childValue))
                {
                    // The value is same in the base collection and in the child collection.
                    // This is a result of expanding, we reverse it by removing the child attribute.
                    attrs.removeAt(childIndex);
                }
                else
                {
                    // The value is different in the base collection and in the child collection.
                    // It means a value is directly specified in the child collection.
                    // For an expandable attribute (such as tab stops), need to collapse it.
                    // For other attributes simply do nothing and the child value will be kept.
                    collapseExpandableAttrIfNeeded(childValue, baseValue);
                }
            }
        }
    }

    private static void refine(Document doc)
    {
        if (!DoRefine)
            return;

        // Collapse Table Normal.
        TableStyle tableNormal = ms.as(doc.getStyles().getBySti(StyleIdentifier.TABLE_NORMAL, false), TableStyle.class);
        for (Row row : (Iterable<Row>) doc.getChildNodes(NodeType.ROW, true))
        {
            TablePr tablePr = row.getTablePr();
            if ((tableNormal != null) && (tablePr.getIstd() == tableNormal.getIstd()))
            {
                collapse(tableNormal.getTablePr(), tablePr);
                if (tablePr.hasFormatRevision() && ((TablePr)tablePr.getFormatRevision().getRevPr()).getIstd() == tableNormal.getIstd())
                {
                    collapse(tableNormal.getTablePr(), tablePr.getFormatRevision().getRevPr());
                }
            }

            // Remove empty row revisions.
            FormatRevision revision = row.getTablePr().getFormatRevision();
            if ((revision != null) && (revision.getRevPr().getCount() == 0))
                row.getTablePr().remove(RevisionAttr.FORMAT_REVISION);
        }

        // Remove default ParaAttr.Istd 0x00 && RunAttr.Istd 0x10.
        for (Paragraph para : (Iterable<Paragraph>) doc.getChildNodes(NodeType.PARAGRAPH, true))
            if (para.getParaPr().getIstd() == 0x00)
                para.getParaPr().remove(ParaAttr.ISTD);

        for (Run run : (Iterable<Run>) doc.getChildNodes(NodeType.RUN, true))
        {
            if (run.getRunPr().getIstd() == 0x10)
                run.getRunPr().remove(FontAttr.ISTD);

            Object runRsid = run.getRunPr().getDirectAttr(FontAttr.RSID_R);
            Object paraRsid = run.getParentParagraph().getParagraphBreakRunPr().getDirectAttr(FontAttr.RSID_R);

            if (msObject.equals(runRsid, paraRsid))
                run.getRunPr().remove(FontAttr.RSID_R);
        }
    }

    /// <summary>
    /// Prints information about custom parts.
    /// </summary>
    /// <param name="doc"></param>
    private static void dumpPackageCustomParts(Document doc) throws Exception
    {
        if (doc.getPackageCustomParts().getCount() == 0)
            return;

        write("");
        write("[Package Custom Parts]");

        for (CustomPart part : doc.getPackageCustomParts())
        {
            write(msString.format("  ['{0}'{1}]", part.getName(), part.isExternal() ? ", External" : ""));
            write(msString.format("    {{ParentName: '{0}'}}", part.getParentPartName()));
            write(msString.format("    {{RelationshipType: '{0}'}}", part.getRelationshipType()));
            write(msString.format("    {{ContentType: {0}}}", part.getContentType()));
            write(msString.format("    {{Data: {0}}}", byteArrayToString(part.getData())));
        }
    }

    private static void dumpSignatures(Document doc) throws Exception
    {
        for (DigitalSignature signature : doc.getDigitalSignatures())
        {
            write(msString.format("\n[{0}, Visible: {1}, Valid: {2}]", signature.getSignatureType(), signature.getVisible(), signature.isValid()));
            IndentLevel++;
            if (signature.getVisible())
            {
                write(msString.format("{{SetupId: {0}}}", signature.getSetupId()));
                write(msString.format("{{ImageBytes: {0}}}", imageBytesToString(signature.getImageBytes())));
                write(msString.format("{{ImageBytesValid: {0}}}", imageBytesToString(signature.getImageBytesValid())));
                write(msString.format("{{ImageBytesInvalid: {0}}}", imageBytesToString(signature.getImageBytesInvalid())));
            }
            if (signature.getSignatureType() == DigitalSignatureType.XML_DSIG)
            {
                write(msString.format("{{ReferencesCount: {0}}}", 0 /* TODO signature.ReferenceCollection.Count */));
            }

            if (StringUtil.hasChars(signature.getComments()))
                write(msString.format("{{Commments: {0}}}", signature.getComments()));

            write(msString.format("{{SignTime: {0}}}", signature.getSignTimeInternal()));

            if (StringUtil.hasChars(signature.getText()))
                write(msString.format("{{Text: {0}}}", signature.getText()));

            IndentLevel--;
        }
    }

    private static String imageBytesToString(byte[] imageBytes) throws Exception
    {
        imageBytes = CompressedData.getData(imageBytes);

        if (imageBytes == null)
            return "null";

        /*FileFormat*/int imageType = ImageUtil.getImageType(imageBytes);
        ImageSizeCore imageSize = ImageUtil.getImageSize(imageBytes);

        return msString.format("{0}, {1}x{2}, {3}", imageType, imageSize.getWidth(), imageSize.getHeight(), byteArrayToString(imageBytes));
    }

    private static void dumpStatistics(Document doc) throws Exception
    {
        write("");
        write("[Statistics]");

        boolean allAttributesSaved = AllAttributes;
        boolean noAttributesSaved = NoAttributes;
        AllAttributes = true;
        NoAttributes = false;

        int paraPrCount = 0;
        int paraPrCapacity = 0;
        int paraPrUsed = 0;

        int runPrCount = 0;
        int runPrCapacity = 0;
        int runPrUsed = 0;

        int tablePrCount = 0;
        int tablePrCapacity = 0;
        int tablePrUsed = 0;

        int cellPrCount = 0;
        int cellPrCapacity = 0;
        int cellPrUsed = 0;

        HashMap<String, Integer> uniqueParaPr = new HashMap<String, Integer>();
        HashMap<String, Integer> uniqueRunPr = new HashMap<String, Integer>();
        HashMap<String, Integer> uniqueTablePr = new HashMap<String, Integer>();
        HashMap<String, Integer> uniqueCellPr = new HashMap<String, Integer>();

        for (Paragraph p : (Iterable<Paragraph>) doc.getChildNodes(NodeType.PARAGRAPH, true))
        {
            // Collect paragraph properties.
            paraPrCount++;
            paraPrCapacity += p.getParaPr().getCapacity();
            paraPrUsed += p.getParaPr().getCount();

            String paraPrText = prToString(p.getParaPr(), ";");
            int oCount = DictionaryExtension.getValueOrDefault(uniqueParaPr, paraPrText, 0);
            uniqueParaPr.put(paraPrText, oCount + 1);

            // Collect paragraph break properties.
            runPrCount++;
            runPrCapacity += p.getParagraphBreakRunPr().getCapacity();
            runPrUsed += p.getParagraphBreakRunPr().getCount();
            String runPrText = prToString(p.getParagraphBreakRunPr(), ";");
            oCount = DictionaryExtension.getValueOrDefault(uniqueRunPr, runPrText, 0);
            uniqueRunPr.put(runPrText, oCount + 1);

            for (Node childNode : (Iterable<Node>) p.getChildNodes(NodeType.ANY, false))
            {
                Inline inline = ms.as(childNode, Inline.class);
                if (inline != null)
                {
                    // Collect run properties.
                    runPrCount++;
                    runPrCapacity += inline.getRunPr().getCapacity();
                    runPrUsed += inline.getRunPr().getCount();
                    runPrText = prToString(inline.getRunPr(), ";");
                    oCount = DictionaryExtension.getValueOrDefault(uniqueRunPr, runPrText, 0);
                    uniqueRunPr.put(runPrText, oCount + 1);
                }
            }
        }

        for (Table table : (Iterable<Table>) doc.getChildNodes(NodeType.TABLE, true))
        {
            for (Row row : (Iterable<Row>) table.getRows())
            {
                // Collect row properties.
                tablePrCount++;
                tablePrCapacity += row.getTablePr().getCapacity();
                tablePrUsed += row.getTablePr().getCount();

                String tablePrText = prToString(row.getTablePr(), ";");
                int oCount = DictionaryExtension.getValueOrDefault(uniqueTablePr, tablePrText, 0);
                uniqueTablePr.put(tablePrText, oCount + 1);

                for (Cell cell : (Iterable<Cell>) row.getCells())
                {
                    // Collect cell properties.
                    cellPrCount++;
                    cellPrCapacity += cell.getCellPr().getCapacity();
                    cellPrUsed += cell.getCellPr().getCount();

                    String cellPrText = prToString(cell.getCellPr(), ";");
                    oCount = DictionaryExtension.getValueOrDefault(uniqueCellPr, cellPrText, 0);
                    uniqueCellPr.put(cellPrText, oCount + 1);
                }
            }
        }

        dumpPrStatistics("ParaPr: ", paraPrCount, uniqueParaPr.size(), paraPrUsed, paraPrCapacity);
        dumpPrStatistics("RunPr:  ", runPrCount, uniqueRunPr.size(), runPrUsed, runPrCapacity);
        dumpPrStatistics("CellPr: ", cellPrCount, uniqueCellPr.size(), cellPrUsed, cellPrCapacity);
        dumpPrStatistics("TablePr:", tablePrCount, uniqueTablePr.size(), tablePrUsed, tablePrCapacity);

        AllAttributes = allAttributesSaved;
        NoAttributes = noAttributesSaved;
    }

    private static void dumpPrStatistics(String name, int total, int unique, int used, int capacity) throws Exception
    {
        write(msString.format("  {{{0} (unique: {1}/{2}/{3}%; usage: {4}/{5}/{6}%)}}",
            name,
            unique, total, total == 0 ? 0 : unique * 100 / total,
            used, capacity, capacity == 0 ? 0 : used * 100 / capacity));
    }

    private static void dumpMailMerge(Document doc) throws Exception
    {
        final String FMT = "  {{{0}}}";

        MailMergeSettings mms = doc.getMailMergeSettings();

        write("");
        write("[MailMerge]");

        write(FMT, msString.format("ActiveRecord: {0}", mms.getActiveRecord()));
        write(FMT, msString.format("AddressFieldName: '{0}'", mms.getAddressFieldName()));
        write(FMT, msString.format("CheckErrors: {0}", mms.getCheckErrors()));
        write(FMT, msString.format("ConnectString: '{0}'", mms.getConnectString()));
        write(FMT, msString.format("DataSource: '{0}'", mms.getDataSource()));
        write(FMT, msString.format("DataType: {0}", mms.getDataType()));
        write(FMT, msString.format("Destination: {0}", mms.getDestination()));

        if (mms.getDoNotSupressBlankLines())
            write(FMT, "DoNotSupressBlankLines");

        write(FMT, msString.format("HeaderSource: '{0}'", mms.getHeaderSource()));
        write(FMT, msString.format("LegacyDataFieldSeparator: {0}", mms.getLegacyDataFieldSeparator()));
        write(FMT, msString.format("LegacyDataRowSeparator: {0}", mms.getLegacyDataRowSeparator()));
        write(FMT, msString.format("LegacyHeaderFieldSeparator: {0}", mms.getLegacyHeaderFieldSeparator()));
        write(FMT, msString.format("LegacyHeaderRowSeparator: {0}", mms.getLegacyHeaderRowSeparator()));

        if (mms.getLinkToQuery())
            write(FMT, "LinkToQuery");

        if (mms.getMailAsAttachment())
            write(FMT, "MailAsAttachment");

        write(FMT, msString.format("MailSubject: '{0}'", mms.getMailSubject()));
        write(FMT, msString.format("MainDocumentType: {0}", mms.getMainDocumentType()));

        write(FMT, msString.format("Query: '{0}'", mms.getQuery()));

        if (mms.getViewMergedData())
            write(FMT, "ViewMergedData");

        write(FMT, msString.format("Odso.ColumnDelimiter: '{0}'", mms.getOdso().getColumnDelimiter()));
        write(FMT, msString.format("Odso.DataSource: '{0}'", mms.getOdso().getDataSource()));
        write(FMT, msString.format("DataSourceType: {0}", mms.getOdso().getDataSourceType()));

        // mms.Odso.FieldMapDatas

        if (mms.getOdso().getFirstRowContainsColumnNames())
            write(FMT, "Odso.FirstRowContainsColumnNames");

        // mms.Odso.RecipientDatas

        write(FMT, msString.format("Odso.TableName: '{0}'", mms.getOdso().getTableName()));
        write(FMT, msString.format("Odso.UdlConnectString: '{0}'", mms.getOdso().getUdlConnectString()));
    }

    private static void dumpFontInfoCollection(Document doc) throws Exception
    {
        write("");
        write("[Fonts]");

        SortedStringListGeneric<FontInfo> sortedFonts = new SortedStringListGeneric<FontInfo>();

        for (FontInfo fontInfo : doc.getFontInfos())
            sortedFonts.add(fontInfo.getName(), fontInfo);

        for (FontInfo fontInfo : sortedFonts.getValues())
        {
            StringBuilder sb = new StringBuilder();
            for (String altName : fontInfo.getAltNameList())
                msStringBuilder.appendFormat(sb, "'{0}', ", altName);

            String altNames = sb.length() > 0
                ? msString.format(", ({0})", msString.trim(sb.toString(), G_LIST_SEPARATOR_CHARS))
                : "";

            write(msString.format("  {{'{0}'{1}, {2} ({3})}}", getPrintableText(fontInfo.getName()), altNames, fontInfo.getFamily(), fontInfo.getCharset()));

            IndentLevel++;
            write(msString.format("{{Panose: {0}}}", rawByteArrayToString(fontInfo.getPanose())));
            if (fontInfo.getPanose() != null)
            {
                for (int i = 0; i < fontInfo.getPanose().length; i++)
                    write(msString.format("  {{{0}: '{1}'}}", G_PANOSE_STRINGS[i], getPanoseValueText(i, (fontInfo.getPanose()[i] & 0xFF))));
            }

            write("{Signature}");
            if (fontInfo.getSig() != null)
            {
                String signature = ArrayUtil.dumpArray(fontInfo.getSig()).replace(" ", "");

                // CodePage ranges
                sb = new StringBuilder();
                sb.setLength(0);
                for (int i = 0; i < FontSignature.CODE_PAGE_BITS.length; i++)
                {
                    int byteNo = 16 + (i / 8);
                    int bitNo = i % 8;

                    if (BitUtil.isSetByte(fontInfo.getSig()[byteNo], (byte)(1 << bitNo)))
                    {
                        if (sb.length() > 0)
                            msStringBuilder.append(sb, ", ");
                        msStringBuilder.append(sb, getCodePageName(FontSignature.CODE_PAGE_BITS[i]));
                    }
                }
                write(msString.format("  {{CodePages ({0}): {1}}}", signature.substring((32), (32) + (16)), sb));
            }

            EmbeddedFont[] embeddedFonts = fontInfo.getEmbeddedFonts();
            if (embeddedFonts != null)
            {
                write("{EmbeddedFonts}");
                for (EmbeddedFont embeddedFont : embeddedFonts)
                {
                    if (embeddedFont == null)
                    {
                        write("  {null}");
                    }
                    else
                    {
                        write(msString.format("  {{{0}, {1}{2}, Data: {3}}}",
                            embeddedFont.getFormat(),
                            embeddedFont.getStyle(),
                            embeddedFont.isSubsetted() ? ", Subsetted" : "",
                            byteArrayToString(embeddedFont.getData())));
                    }
                }
            }

            write("");
            IndentLevel--;
        }
    }

    private static String getCodePageName(int codepage)
    {
        switch (codepage)
        {
            case 1252:
                return "ANSI";
            case 1250:
                return "EastEurope";
            case 1251:
                return "Cyrillic";
            case 1253:
                return "Greek";
            case 1254:
                return "Turkish";
            case 1255:
                return "Hebrew";
            case 1256:
                return "Arabic";
            case 1257:
                return "Baltic";
            case 1258:
                return "Vietnamese";
            case 874:
                return "Thai";
            case 932:
                return "ShiftJIS";
            case 936:
                return "Chinese Simplified";
            case 949:
                return "Korean Wansung";
            case 869:
                return "IBM Greek";
            case 866:
                return "MS-DOS Russian";
            case 865:
                return "MS-DOS Nordic";
            case 864:
                return "Arabic";
            case 863:
                return "MS-DOS Canadian French";
            case 862:
                return "Hebrew";
            case 861:
                return "MS-DOS Icelandic";
            case 860:
                return "MS-DOS Portuguese";
            case 857:
                return "IBM Turkish";
            case 855:
                return "IBM Cyrillic";
            case 852:
                return "Latin 2";
            case 775:
                return "MS-DOS Baltic";
            case 737:
                return "Greek; former 437 G";
            case 708:
                return "Arabic; ASMO 708";
            case 850:
                return "WE/Latin 1";
            case 437:
                return "US";
            case 0:
                return "Default";
            case 950:
                return "ChineseBig5";
            case 1361:
                return "Korean (Johab)";
            case 2:
                return "Symbol";
            default:
                throw new IllegalStateException(msString.format("Codepage: {0}", codepage));
        }
    }

    private static String getPanoseValueText(int index, int value)
    {
        if (index >= 10)
            return "?";

        if (value == 0)
            return "Any";
        if (value == 1)
            return "No fit";
        value -= 2; // -Any -NoFit

        String[] familyStrings = { "Text and Display", "Script", "Decorative", "Pictorial" };
        String[] serifStrings = { "Cove", "Obtuse Cove", "Square Cove", "Obtuse Square Cove", "Square", "Thin", "Bone", "Exaggerated", "Triangle", "Normal Sans", "Obtuse Sans", "Prep Sans", "Flared", "Rounded" };
        String[] weightStrings = { "Very Light", "Light", "Thin", "Book", "Medium", "Demi", "Bold", "Heavy", "Black", "Nord" };
        String[] proportionStrings = { "Old Style", "Modern", "Even Width", "Expanded", "Condensed", "Very Expanded", "Very Condensed", "Monospaced" };
        String[] contrastStrings = { "None", "Very Low", "Low", "Medium Low", "Medium", "Medium High", "High", "Very High" };
        String[] strokeStrings = { "Gradual/Diagonal", "Gradual/Transitional", "Gradual/Vertical", "Gradual/Horizontal", "Rapid/Vertical", "Rapid/Horizontal", "Instant/Vertical" };
        String[] armStrings = { "Straight Arms/Horizontal", "Straight Arms/Wedge", "Straight Arms/Vertical", "Straight Arms/Single-Serif", "Straight Arms/Double-Serif", "Non-Straight Arms/Horizontal", "Non-Straight Arms/Wedge", "Non-Straight Arms/Vertical", "Non-Straight Arms/Single-Serif", "Non-Straight Arms/Double-Serif" };
        String[] letterformStrings = { "Normal/Contact", "Normal/Weighted", "Normal/Boxed", "Normal/Flattened", "Normal/Rounded", "Normal/Off Center", "Normal/Square", "Oblique/Contact", "Oblique/Weighted", "Oblique/Boxed", "Oblique/Flattened", "Oblique/Rounded", "Oblique/Off Center", "Oblique/Square" };
        String[] midlineStrings = { "Standard/Trimmed", "Standard/Pointed", "Standard/Serifed", "High/Trimmed", "High/Pointed", "High/Serifed", "Constant/Trimmed", "Constant/Pointed", "Constant/Serifed", " Low/Trimmed", "Low/Pointed", "Low/Serifed" };
        String[] xheightStrings = { "Constant/Small", "Constant/Standard", "Constant/Large", "Ducking/Small", "Ducking/Standard", "Ducking/Large" };

        String[][] allStrings = { familyStrings, serifStrings, weightStrings, proportionStrings, contrastStrings, strokeStrings, armStrings, letterformStrings, midlineStrings, xheightStrings };

        String[] strings = allStrings[index];
        return (value < strings.length) ? strings[value] : "?";
    }

    private static void dumpDocPr(Document doc) throws Exception
    {
        if (NoDocumentProperties)
            return;

        final String FMT = "  {{{0}}}";
        DocPr docPr = doc.getDocPr();
        write("");
        write("[Properties]");

        if (docPr.RemovePersonalInformation)
            write(FMT, "RemovePersonalInformation");
        if (docPr.PrintPostScriptOverText)
            write(FMT, "PrintPostScriptOverText");
        if (docPr.PrintFractionalCharacterWidth)
            write(FMT, "PrintFractionalCharacterWidth");
        if (docPr.PrintFormsData)
            write(FMT, "PrintFormsData");
        if (docPr.EmbedTrueTypeFonts)
            write(FMT, "EmbedTrueTypeFonts");
        if (docPr.EmbedSystemFonts)
            write(FMT, "EmbedSystemFonts");
        if (docPr.SaveSubsetFonts)
            write(FMT, "SaveSubsetFonts");
        if (docPr.SaveFormsData)
            write(FMT, "SaveFormsData");
        if (docPr.MultiplePages == MultiplePagesType.MIRROR_MARGINS)
            write(FMT, "MirrorMargins");
        if (docPr.AlignBordersAndEdges)
            write(FMT, "AlignBordersAndEdges");
        if (docPr.BordersDoNotSurroundHeader)
            write(FMT, "BordersDoNotSurroundHeader");
        if (docPr.BordersDoNotSurroundFooter)
            write(FMT, "BordersDoNotSurroundFooter");
        if (docPr.GutterAtTop)
            write(FMT, "GutterAtTop");
        if (docPr.isGutterSide())
            write(FMT, "IsGutterSide");
        write(FMT, msString.format("ActiveWritingStyle: '{0}', {1}, {2}, {3}, {4}",
            docPr.ActiveWritingStyle.Lang, docPr.ActiveWritingStyle.DllVersion, docPr.ActiveWritingStyle.VendorId,
                                 docPr.ActiveWritingStyle.NlCheck, docPr.ActiveWritingStyle.OptionSet));
        write(FMT, msString.format("ProofStateSpelling: {0}", docPr.ProofStateSpelling));
        write(FMT, msString.format("AttachedTemplate: '{0}'", docPr.AttachedTemplate));
        if (docPr.LinkStyles)
            write(FMT, "LinkStyles");
        if (docPr.StyleLockQuickFormatSet)
            write(FMT, "StyleLockQuickFormatSet");
        if (docPr.StyleLockTheme)
            write(FMT, "StyleLockTheme");
        if (docPr.StyleLockQuickFormatSet)
            write(FMT, "StyleLockQuickFormatSet");
        write(FMT, msString.format("StylePaneFormatFilterSettings: {0}", formatStylePaneFilterSettings(docPr.StylePaneFormatFilterSettings)));
        write(FMT, msString.format("StylePaneSortMethod: {0}", docPr._StylePaneSortMethod));
        if (docPr.DoNotTrackFormatting)
            write(FMT, "DoNotTrackFormatting");
        if (docPr.DoNotTrackMoves)
            write(FMT, "DoNotTrackMoves");
        write(FMT, msString.format("DocumentType: {0}", docPr.DocumentType));
        if (docPr.TrackRevisions)
            write(FMT, "TrackRevisions");
        if (docPr.ShowMarkup)
            write(FMT, "ShowMarkup");
        if (docPr.ShowAnnotations)
            write(FMT, "ShowAnnotations");
        if (docPr.ShowInsertionsDeletions)
            write(FMT, "ShowInsertionsDeletions");
        if (docPr.ShowFormatting)
            write(FMT, "ShowFormatting");
        if (docPr.ShowInkAnnotations)
            write(FMT, "ShowInkAnnotations");
        write(FMT, msString.format("DocumentProtection: {0}", documentProtectionToString(docPr.DocumentProtection)));
        write(FMT, msString.format("WriteProtection: {0}", writeProtectionToString(docPr.WriteProtection)));
        write(FMT, msString.format("DefaultTabStop: {0}", docPr.DefaultTabStop));
        HyphenationOptions hyphenationOptions = docPr.HyphenationOptions;
        if (hyphenationOptions.getAutoHyphenation())
            write(FMT, "AutoHyphenation");
        write(FMT, msString.format("ConsecutiveHyphenLimit: {0}", hyphenationOptions.getConsecutiveHyphenLimit()));
        write(FMT, msString.format("HyphenationZone: {0}", hyphenationOptions.getHyphenationZone()));
        if (!hyphenationOptions.getHyphenateCaps())
            write(FMT, "DoNotHyphenateCaps");
        if (docPr.ShowEnvelope)
            write(FMT, "ShowEnvelope");
        if (docPr.ReadingModeInkLockDown)
            write(FMT, "ReadingModeInkLockDown");
        if (docPr.RemoveDateAndTime)
            write(FMT, "RemoveDateAndTime");
        write(FMT, msString.format("ClickTypeParaStyleIstd: 0x{0:x2}", docPr.ClickTypeParaStyleIstd));
        write(FMT, msString.format("DefaultTableStyleIstd: 0x{0:x2}", docPr.DefaultTableStyleIstd));
        if (docPr.EvenAndOddHeaders)
            write(FMT, "EvenAndOddHeaders");
        if (docPr.MultiplePages == MultiplePagesType.BOOK_FOLD_PRINTING_REVERSE)
            write(FMT, "BookFoldRevPrinting");
        if (docPr.MultiplePages == MultiplePagesType.BOOK_FOLD_PRINTING)
            write(FMT, "BookFoldPrinting");
        write(FMT, msString.format("BookFoldPrintingSheets: {0}", docPr.BookFoldPrintingSheets));
        write(FMT, msString.format("DrawingGridHorizontalSpacing: {0}", docPr.DrawingGridHorizontalSpacing));
        write(FMT, msString.format("DrawingGridVerticalSpacing: {0}", docPr.DrawingGridVerticalSpacing));
        write(FMT, msString.format("DisplayHorizontalDrawingGridEvery: {0}", docPr.DisplayHorizontalDrawingGridEvery));
        write(FMT, msString.format("DisplayVerticalDrawingGridEvery: {0}", docPr.DisplayVerticalDrawingGridEvery));
        if (docPr.UseMarginsForDrawingGridOrigin)
            write(FMT, "UseMarginsForDrawingGridOrigin");
        write(FMT, msString.format("DrawingGridHorizontalOrigin: {0}", docPr.DrawingGridHorizontalOrigin));
        write(FMT, msString.format("DrawingGridVerticalOrigin: {0}", docPr.DrawingGridVerticalOrigin));
        if (docPr.DoNotShadeFormData)
            write(FMT, "DoNotShadeFormData");
        if (docPr.PunctuationKerning)
            write(FMT, "PunctuationKerning");
        write(FMT, msString.format("CharacterSpacingType: {0}", docPr.CharacterSpacingType));
        if (docPr.MultiplePages == MultiplePagesType.TWO_PAGES_PER_SHEET)
            write(FMT, "PrintTwoOnOne");
        if (docPr.StrictFirstAndLastChars)
            write(FMT, "StrictFirstAndLastChars");
        write(FMT, msString.format("NoLineBreaksAfter: {0}", docPr.NoLineBreaksAfter));
        write(FMT, msString.format("NoLineBreaksBefore: {0}", docPr.NoLineBreaksBefore));
        write(FMT, msString.format("WebPageEncoding: {0}", docPr.WebPageEncoding));
        if (docPr.OptimizeForBrowser)
            write(FMT, "OptimizeForBrowser");
        write(FMT, msString.format("WebTarget: {0}", docPr._WebTarget));
        if (docPr.RelyOnVml)
            write(FMT, "RelyOnVml");
        if (docPr.AllowPng)
            write(FMT, "AllowPng");
        if (docPr.DoNotRelyOnCss)
            write(FMT, "DoNotRelyOnCss");
        if (docPr.DoNotSaveWebPagesAsSingleFile)
            write(FMT, "DoNotSaveWebPagesAsSingleFile");
        if (docPr.DoNotOrganizeInFolder)
            write(FMT, "DoNotOrganizeInFolder");
        if (docPr.DoNotUseLongFileNames)
            write(FMT, "DoNotUseLongFileNames");
        write(FMT, msString.format("PixelsPerInch: {0}", docPr.PixelsPerInch));
        write(FMT, msString.format("TargetScreenSize: {0}", docPr.TargetScreenSize));
        if (docPr.ValidateAgainstSchema)
            write(FMT, "ValidateAgainstSchema");
        if (docPr.SaveInvalidXml)
            write(FMT, "SaveInvalidXml");
        if (docPr.SaveXmlDataOnly)
            write(FMT, "SaveXmlDataOnly");
        if (docPr.ShowXmlTags)
            write(FMT, "ShowXmlTags");
        if (docPr.IgnoreMixedContent)
            write(FMT, "IgnoreMixedContent");
        if (docPr.AlwaysShowPlaceholderText)
            write(FMT, "AlwaysShowPlaceholderText");
        if (docPr.DoNotUnderlineInvalidXml)
            write(FMT, "DoNotUnderlineInvalidXml");
        if (docPr.UseXsltWhenSaving)
            write(FMT, "UseXsltWhenSaving");
        write(FMT, msString.format("SaveThroughXslt: {0}", docPr.SaveThroughXslt));
        if (docPr.AlwaysMergeEmptyNamespace)
            write(FMT, "AlwaysMergeEmptyNamespace");
        if (docPr.DoNotEmbedSmartTags)
            write(FMT, "DoNotEmbedSmartTags");
        if (docPr.SaveSmartTagsAsXml)
            write(FMT, "SaveSmartTagsAsXml");
        if (docPr.DoNotIncludeSubDocsInStats)
            write(FMT, "DoNotIncludeSubDocsInStats");
        if (docPr.NoFitText)
            write(FMT, "NoFitText");

        if (!NoRsids)
        {
            write(FMT, msString.format("RsidRoot: 0x{0:x8}", docPr.RsidRoot));
            IndentLevel++;
            for (int i = 0; i < docPr.Rsids.getCount(); i++)
                write(FMT, msString.format("Rsid: 0x{0:x8}", docPr.Rsids.get(i)));
            IndentLevel--;
        }

        write(FMT, msString.format("CodePageText: {0}", docPr.CodePageText));
        if (docPr.FootnotePr.getCount() > 0)
        {
            write(FMT, "FootnotePr");
            IndentLevel++;
            dumpPr(docPr.FootnotePr);
            IndentLevel--;
        }

        StringBuilder sb = new StringBuilder();
        if (docPr.ViewOptions.getDisplayBackgroundShape())
            msStringBuilder.append(sb, "DisplayBackgroundShape, ");
        if (docPr.ViewOptions.getDoNotDisplayPageBoundaries())
            msStringBuilder.append(sb, "DoNotDisplayPageBoundaries, ");
        if (docPr.ViewOptions.getFormsDesign())
            msStringBuilder.append(sb, "FormsDesign ");

        write(FMT, msString.trim(msString.format("ViewOptions: {0}, {1}, {2}, {3}",
                                 docPr.ViewOptions.getViewType(), docPr.ViewOptions.getZoomPercent(),
                                 docPr.ViewOptions.getZoomType(), sb.toString()), G_LIST_SEPARATOR_CHARS));

        write(FMT, msString.format("ThemeFontLanguages: Latin='{0}', EastAsia='{1}', Bidi='{2}'",
            docPr.ThemeFontLanguages.Latin != Language.NO_PROOF ? Language.toString(docPr.ThemeFontLanguages.Latin) : "",
            docPr.ThemeFontLanguages.EastAsia != Language.NO_PROOF ? Language.toString(docPr.ThemeFontLanguages.EastAsia) : "",
            docPr.ThemeFontLanguages.Bidi != Language.NO_PROOF ? Language.toString(docPr.ThemeFontLanguages.Bidi) : ""));

        if (docPr.XmlNamespaces.getCount() > 0)
        {
            StringBuilder sb2 = new StringBuilder();
            for (XmlNamespace xmlNamespace : docPr.XmlNamespaces)
                msStringBuilder.append(sb2, msString.format("('{0}', '{1}', '{2}', '{3}'), ", xmlNamespace.getUri(), xmlNamespace.getManifestLocation(), xmlNamespace.getSchemaLanguage(), xmlNamespace.getSchemaLocation()));
            write(FMT, msString.format("XmlNamespaces: {0}", msString.trim(sb2.toString(), G_LIST_SEPARATOR_CHARS)));
        }

        if (docPr.XmlSchemaReferences.getCount() > 0)
        {
            StringBuilder sb2 = new StringBuilder();
            for (XmlSchemaReference xmlSchemaReference : docPr.XmlSchemaReferences)
                msStringBuilder.append(sb2, msString.format("('{0}', '{1}'), ", xmlSchemaReference.getUri(), xmlSchemaReference.getLocation()));
            write(FMT, msString.format("XmlSchemaReferences: {0}", msString.trim(sb2.toString(), G_LIST_SEPARATOR_CHARS)));
        }

        write(FMT, msString.format("ShowOutlineLevels: {0}", docPr.ShowOutlineLevels));
        if (docPr.ShowHidden)
            write(FMT, "ShowHidden");
        if (docPr.ShowFieldResults)
            write(FMT, "ShowFieldResults");
        if (docPr.PrintRevisions)
            write(FMT, "PrintRevisions");
        if (docPr.HtmlDoc)
            write(FMT, "HtmlDoc");
        if (docPr.WidowControl)
            write(FMT, "WidowControl");
        if (docPr.ShowGrid)
            write(FMT, "ShowGrid");
        if (docPr.HideLastVersion)
            write(FMT, "HideLastVersion");
        if (docPr.HasVersions)
            write(FMT, "HasVersions");
        if (docPr.AutoVersion)
            write(FMT, "AutoVersion");
    }

    private static String formatStylePaneFilterSettings(StylePaneFormatFilterSettings settings)
    {
        StringBuilder sb = new StringBuilder();

        if (settings.getAllStyles())
            msStringBuilder.append(sb, "AllStyles ");
        if (settings.getAlternateStyleNames())
            msStringBuilder.append(sb, "AlternateStyleNames ");
        if (settings.getClearFormatting())
            msStringBuilder.append(sb, "ClearFormatting ");
        if (settings.getCustomStyles())
            msStringBuilder.append(sb, "CustomStyles ");

        if (settings.getDirectFormattingOnNumbering())
            msStringBuilder.append(sb, "DirectFormattingOnNumbering ");
        if (settings.getDirectFormattingOnParagraphs())
            msStringBuilder.append(sb, "DirectFormattingOnParagraphs ");
        if (settings.getDirectFormattingOnRuns())
            msStringBuilder.append(sb, "DirectFormattingOnRuns ");
        if (settings.getDirectFormattingOnTables())
            msStringBuilder.append(sb, "DirectFormattingOnTables ");

        if (settings.getHeadingStyles())
            msStringBuilder.append(sb, "HeadingStyles ");
        if (settings.getLatentStyles())
            msStringBuilder.append(sb, "LatentStyles ");
        if (settings.getNumberingStyles())
            msStringBuilder.append(sb, "NumberingStyles ");
        if (settings.getStylesInUse())
            msStringBuilder.append(sb, "StylesInUse ");
        if (settings.getTableStyles())
            msStringBuilder.append(sb, "TableStyles ");
        if (settings.getTop3HeadingStyles())
            msStringBuilder.append(sb, "Top3HeadingStyles ");
        if (settings.getVisibleStyles())
            msStringBuilder.append(sb, "VisibleStyles ");

        return msString.trim(sb.toString());
    }

    private static String documentProtectionToString(DocumentProtection docProtection)
    {
        return msString.format("{0}{1}{2}{3}{4}",
            docProtection.getEdit(),
            docProtection.getEnforcement() ? ", Enforcement" : "",
            docProtection.getFormatting() ? ", Formatting" : "",
            docProtection.getSelectFormFieldsOnly() ? ", SelectFormFieldsOnly" : "",
            docProtection.getAutoFormatOverride() ? ", AutoFormatOverride" : "");
    }

    private static String writeProtectionToString(WriteProtection writeProtection)
    {
        if (!writeProtection.isWriteProtected())
            return "No write protection";

        return msString.format("{0}{1}{2}",
            writeProtection.getReadOnlyRecommended() ? "ReadOnly" : "None",
            StringUtil.hasChars(writeProtection.getPassword())
                ? msString.format(", Password: '{0}'", writeProtection.getPassword())
                : "",
            writeProtection.getPasswordHash().Hash != null
                ? msString.format(", Hash: '{0}'", Convert.toBase64String(writeProtection.getPasswordHash().Hash))
                : "");
    }

    private static void dumpBackground(Document doc) throws Exception
    {
        if (doc.getBackgroundShape() == null)
            return;

        write("");
        write("[Background]");
        IndentLevel++;
        dumpNode(doc.getBackgroundShape());
        IndentLevel--;
    }

    private static void dumpHtmlBlockCollection(Document doc) throws Exception
    {
        if (doc.getHtmlBlockCollection().getCount() == 0)
            return;

        write("");
        dumpHtmlBlock(null, doc);
    }

    private static void dumpHtmlBlock(HtmlBlock htmlBlock, Document doc) throws Exception
    {
        if (htmlBlock != null)
        {
            write(msString.format("[{0}, 0x{1:x8}, {2}]", htmlBlock.getHtmlBlockType(), htmlBlock.getId(), htmlBlock.getItap()));
            dumpPr(htmlBlock.getParaPr());
            IndentLevel++;
        }

        for (int i = 0; i < doc.getHtmlBlockCollection().getCount(); i++)
        {
            HtmlBlock childHtmlBlock = doc.getHtmlBlockCollection().getHtmlBlockByIndex(i);

            if (childHtmlBlock.getParentId() == ((htmlBlock == null) ? 0 : htmlBlock.getId()))
                dumpHtmlBlock(childHtmlBlock, doc);
        }

        if (htmlBlock != null)
        {
            IndentLevel--;
        }
    }

    private static void dumpCompatibility(Document doc) throws Exception
    {
        if (NoDocumentProperties)
            return;

        final String CO_FMT = "  {{{0}}}";
        CompatibilityOptions co = doc.getCompatibilityOptions();
        write("");
        write("[Compatibility]");

        if (co.getAdjustLineHeightInTable())
            write(CO_FMT, "AdjustLineHeightInTable");
        if (co.getAlignTablesRowByRow())
            write(CO_FMT, "AlignTablesRowByRow");
        if (co.getAllowSpaceOfSameStyleInTable())
            write(CO_FMT, "AllowSpaceOfSameStyleInTable");
        if (co.getApplyBreakingRules())
            write(CO_FMT, "ApplyBreakingRules");
        if (co.getAutofitToFirstFixedWidthCell())
            write(CO_FMT, "AutofitToFirstFixedWidthCell");
        if (co.getAutoSpaceLikeWord95())
            write(CO_FMT, "AutoSpaceLikeWord95");
        if (co.getBalanceSingleByteDoubleByteWidth())
            write(CO_FMT, "BalanceSingleByteDoubleByteWidth");
        if (co.getCachedColBalance())
            write(CO_FMT, "CachedColBalance");
        if (co.getConvMailMergeEsc())
            write(CO_FMT, "ConvMailMergeEsc");
        if (co.getDisplayHangulFixedWidth())
            write(CO_FMT, "DisplayHangulFixedWidth");
        if (co.getDoNotAutofitConstrainedTables())
            write(CO_FMT, "DoNotAutofitConstrainedTables");
        if (co.getDoNotBreakConstrainedForcedTable())
            write(CO_FMT, "DoNotBreakConstrainedForcedTable");
        if (co.getDoNotBreakWrappedTables())
            write(CO_FMT, "DoNotBreakWrappedTables");
        if (co.getDoNotExpandShiftReturn())
            write(CO_FMT, "DoNotExpandShiftReturn");
        if (co.getDoNotLeaveBackslashAlone())
            write(CO_FMT, "DoNotLeaveBackslashAlone");
        if (co.getDoNotSnapToGridInCell())
            write(CO_FMT, "DoNotSnapToGridInCell");
        if (co.getDoNotSuppressIndentation())
            write(CO_FMT, "DoNotSuppressIndentation");
        if (co.getDoNotSuppressParagraphBorders())
            write(CO_FMT, "DoNotSuppressParagraphBorders");
        if (co.getDoNotUseEastAsianBreakRules())
            write(CO_FMT, "DoNotUseEastAsianBreakRules");
        if (co.getDoNotUseHTMLParagraphAutoSpacing())
            write(CO_FMT, "DoNotUseHTMLParagraphAutoSpacing");
        if (co.getDoNotUseIndentAsNumberingTabStop())
            write(CO_FMT, "DoNotUseIndentAsNumberingTabStop");
        if (co.getDoNotVertAlignCellWithSp())
            write(CO_FMT, "DoNotVertAlignCellWithSp");
        if (co.getDoNotVertAlignInTxbx())
            write(CO_FMT, "DoNotVertAlignInTxbx");
        if (co.getDoNotWrapTextWithPunct())
            write(CO_FMT, "DoNotWrapTextWithPunct");
        if (co.getFootnoteLayoutLikeWW8())
            write(CO_FMT, "FootnoteLayoutLikeWW8");
        if (co.getForgetLastTabAlignment())
            write(CO_FMT, "ForgetLastTabAlignment");
        if (co.getGrowAutofit())
            write(CO_FMT, "GrowAutofit");
        if (co.getLayoutRawTableWidth())
            write(CO_FMT, "LayoutRawTableWidth");
        if (co.getLayoutTableRowsApart())
            write(CO_FMT, "LayoutTableRowsApart");
        if (co.getLineWrapLikeWord6())
            write(CO_FMT, "LineWrapLikeWord6");
        if (co.getMWSmallCaps())
            write(CO_FMT, "MWSmallCaps");
        if (co.getNoColumnBalance())
            write(CO_FMT, "NoColumnBalance");
        if (co.getNoExtraLineSpacing())
            write(CO_FMT, "NoExtraLineSpacing");
        if (co.getNoLeading())
            write(CO_FMT, "NoLeading");
        if (co.getNoSpaceRaiseLower())
            write(CO_FMT, "NoSpaceRaiseLower");
        if (co.getNoTabHangInd())
            write(CO_FMT, "NoTabHangInd");
        if (co.getPrintBodyTextBeforeHeader())
            write(CO_FMT, "PrintBodyTextBeforeHeader");
        if (co.getPrintColBlack())
            write(CO_FMT, "PrintColBlack");
        if (co.getSelectFldWithFirstOrLastChar())
            write(CO_FMT, "SelectFldWithFirstOrLastChar");
        if (co.getShapeLayoutLikeWW8())
            write(CO_FMT, "ShapeLayoutLikeWW8");
        if (co.getShowBreaksInFrames())
            write(CO_FMT, "ShowBreaksInFrames");
        if (co.getSpaceForUL())
            write(CO_FMT, "SpaceForUL");
        if (co.getSpacingInWholePoints())
            write(CO_FMT, "SpacingInWholePoints");
        if (co.getSplitPgBreakAndParaMark())
            write(CO_FMT, "SplitPgBreakAndParaMark");
        if (co.getSubFontBySize())
            write(CO_FMT, "SubFontBySize");
        if (co.getSuppressBottomSpacing())
            write(CO_FMT, "SuppressBottomSpacing");
        if (co.getSuppressSpacingAtTopOfPage())
            write(CO_FMT, "SuppressSpacingAtTopOfPage");
        if (co.getSuppressSpBfAfterPgBrk())
            write(CO_FMT, "SuppressSpBfAfterPgBrk");
        if (co.getSuppressTopSpacing())
            write(CO_FMT, "SuppressTopSpacing");
        if (co.getSuppressTopSpacingWP())
            write(CO_FMT, "SuppressTopSpacingWP");
        if (co.getSwapBordersFacingPgs())
            write(CO_FMT, "SwapBordersFacingPgs");
        if (co.getTransparentMetafiles())
            write(CO_FMT, "TransparentMetafiles");
        if (co.getTruncateFontHeightsLikeWP6())
            write(CO_FMT, "TruncateFontHeightsLikeWP6");
        if (co.getUICompat97To2003())
            write(CO_FMT, "UICompat97To2003");
        if (co.getUlTrailSpace())
            write(CO_FMT, "UlTrailSpace");
        if (co.getUnderlineTabInNumList())
            write(CO_FMT, "UnderlineTabInNumList");
        if (co.getUseAltKinsokuLineBreakRules())
            write(CO_FMT, "UseAltKinsokuLineBreakRules");
        if (co.getUseAnsiKerningPairs())
            write(CO_FMT, "UseAnsiKerningPairs");
        if (co.getUseFELayout())
            write(CO_FMT, "UseFELayout");
        if (co.getUseNormalStyleForList())
            write(CO_FMT, "UseNormalStyleForList");
        if (co.getUsePrinterMetrics())
            write(CO_FMT, "UsePrinterMetrics");
        if (co.getUseSingleBorderforContiguousCells())
            write(CO_FMT, "UseSingleBorderforContiguousCells");
        if (co.getUseWord2002TableStyleRules())
            write(CO_FMT, "UseWord2002TableStyleRules");
        if (co.getUseWord97LineBreakRules())
            write(CO_FMT, "UseWord97LineBreakRules");
        if (co.getWPJustification())
            write(CO_FMT, "WPJustification");
        if (co.getWPSpaceWidth())
            write(CO_FMT, "WPSpaceWidth");
        if (co.getWrapTrailSpaces())
            write(CO_FMT, "WrapTrailSpaces");

        for (CustomCompatibilitySetting ccs : co.getCustomCompatibilitySettings())
            write(CO_FMT, msString.format("{0}, '{1}', {2}", ccs.getName(), ccs.getValue(), ccs.getUri()));
    }

    private static void dumpWarnings() throws Exception
    {
        if (NoWarnings)
            return;

        if ((gWarningCallback != null) && (gWarningCallback instanceof UniqueWarningCollection))
        {
            UniqueWarningCollection warnings = (UniqueWarningCollection)gWarningCallback;

            if (warnings.getCount() > 0)
            {
                write("[Warnings]");
                for (WarningInfo warning : warnings)
                    write(msString.format("  {0}, {1}", warning.getSource(), warning.getDescription()));
            }
        }
    }

    private static void dumpFootnoteSeparators(FootnoteSeparatorCollection footnoteSeparators) throws Exception
    {
        for (FootnoteSeparator footnoteSeparator : footnoteSeparators)
        {
            write("");
            write(msString.format("[{0}]", footnoteSeparator.getSeparatorType()));
            for (Node childNode : (Iterable<Node>) footnoteSeparator)
                dumpNode(childNode);
        }
    }

    private static void dumpVbaProject(Document doc) throws Exception
    {
        write("");
        write("[VBA]");

        write(msString.format("  {{Name: '{0}'}}", doc.getVbaProject().getName()));
        write(msString.format("  {{Description: '{0}'}}", doc.getVbaProject().getDescription()));
        write(msString.format("  {{CodePage: {0}}}", doc.getVbaProject().getCodePage()));
        write(msString.format("  {{Lcid: {0}}}", doc.getVbaProject().getLcid()));
        write(msString.format("  {{LcidInvoke: {0}}}", doc.getVbaProject().getLcidInvoke()));
        write(msString.format("  {{LibFlags: {0}}}", doc.getVbaProject().getLibFlags()));
        write(msString.format("  {{SysKind: {0}}}", doc.getVbaProject().getSysKind()));
        write(msString.format("  {{IsSigned: {0}}}", doc.getVbaProject().isSigned()));
        write(msString.format("  {{VersionMajor: {0}}}", msString.format("0x{0:x8}", doc.getVbaProject().getVersionMajor())));
        write(msString.format("  {{VersionMinor: {0}}}", msString.format("0x{0:x8}", (doc.getVbaProject().getVersionMinor() & 0xFFFF))));
        write(msString.format("  {{Constants: '{0}'}}", doc.getVbaProject().getConstants()));
        write(msString.format("  {{HelpContext: {0}}}", doc.getVbaProject().getHelpContext()));
        write(msString.format("  {{HelpFilePath1: '{0}'}}", doc.getVbaProject().getHelpFilePath1()));
        write(msString.format("  {{HelpFilePath2: '{0}'}}", doc.getVbaProject().getHelpFilePath2()));

        if (doc.getVbaProject().getMacroNames() != null)
        {
            StringBuilder sb = new StringBuilder();
            for (String macroName : doc.getVbaProject().getMacroNames())
                msStringBuilder.appendFormat(sb, "'{0}', ", macroName);
            write(msString.format("  {{MacroNames: {0}}}", msString.trim(sb.toString(), G_LIST_SEPARATOR_CHARS)));
        }

        IndentLevel++;
        for (VbaModule vbaModule : doc.getVbaProject().getModules())
        {
            write(msString.format("[{0}]", vbaModule.getName()));

            write(msString.format("  {{Type: {0}}}", vbaModule.getType()));
            write(msString.format("  {{HelpContext: {0}}}", vbaModule.getHelpContext()));
            write(msString.format("  {{DocString: '{0}'}}", vbaModule.getDocString()));

            if (vbaModule.getFormMemoryStorage() != null)
            {
                FileSystem fs = new FileSystem(vbaModule.getFormMemoryStorage());
                MemoryStream ms = new MemoryStream();
                fs.save(ms);

                write(msString.format("  {{FormMemoryStorage: '{0}'}}", byteArrayToString(ms.toArray())));
            }

            write(msString.format("  {{SourceCode: '{0}'}}", getPrintableText(vbaModule.getSourceCode())));
        }
        IndentLevel--;
    }

    private static boolean isGoBackBookmarkNode(Node node)
    {
        if (node.getNodeType() == NodeType.BOOKMARK_START)
        {
            BookmarkStart bookmarkStart = (BookmarkStart)node;
            if ("_GoBack".equals(bookmarkStart.getName()))
                return true;
        }

        if (node.getNodeType() == NodeType.BOOKMARK_END)
        {
            BookmarkEnd bookmarkEnd = (BookmarkEnd)node;
            if ("_GoBack".equals(bookmarkEnd.getName()))
                return true;
        }

        return false;
    }

    private static boolean skipNode(Node node)
    {
        if (NoGoBackBookmark && isGoBackBookmarkNode(node))
            return true;

        switch (node.getNodeType())
        {
            case NodeType.BOOKMARK_START:
            case NodeType.BOOKMARK_END:
            case NodeType.COMMENT_RANGE_START:
            case NodeType.COMMENT_RANGE_END:
                return SkipCrossStructureAnnotations;

            default:
                return false;
        }
    }

    private static String nodeToString(Node node)
    {
        StringBuilder sb = new StringBuilder();

        msStringBuilder.append(sb, node.getNodeType());
        msStringBuilder.append(sb, " ");

        if (!NoNodeId)
        {
            msStringBuilder.append(sb, node.getNodeId());
            msStringBuilder.append(sb, " ");
        }

        switch (node.getNodeType())
        {
            case NodeType.FIELD_START:
                msStringBuilder.append(sb, "(" + ((FieldStart)node).getField().getType() + ")");
                break;

            case NodeType.FIELD_SEPARATOR:
                msStringBuilder.append(sb, "(" + ((FieldSeparator)node).getField().getType() + ")");
                break;

            case NodeType.FIELD_END:
                msStringBuilder.append(sb, " (" + ((FieldEnd)node).getField().getType() + ")");
                break;

            case NodeType.RUN:
                msStringBuilder.append(sb, getPrintableText(node.getText()).replace(" ", "·"));
                break;

            case NodeType.BOOKMARK_START:
                BookmarkStart bkmkStart = (BookmarkStart)node;
                msStringBuilder.appendFormat(sb, "'{0}'{1}", bkmkStart.getName(), bkmkStart.getDisplacedBy() != DisplacedByType.UNSPECIFIED ? msString.format(", DisplacedBy:{0}", bkmkStart.getDisplacedBy()) : "");
                break;

            case NodeType.BOOKMARK_END:
                BookmarkEnd bkmkEnd = (BookmarkEnd)node;
                msStringBuilder.appendFormat(sb, "'{0}'{1}", bkmkEnd.getName(), bkmkEnd.getDisplacedBy() != DisplacedByType.UNSPECIFIED ? msString.format(", DisplacedBy:{0}", bkmkEnd.getDisplacedBy()) : "");
                break;

            case NodeType.COMMENT:
            {
                Comment comment = (Comment)node;
                msStringBuilder.appendFormat(sb, "Id:{0}, Parent:{1}, Done:{2}",
                    comment.getId(),
                    (comment.getParentId() == -1) ? "None" : Integer.toString(comment.getParentId()),
                    comment.getDone());
                break;
            }

            case NodeType.COMMENT_RANGE_START:
            case NodeType.COMMENT_RANGE_END:
            {
                INodeWithAnnotationId commentRangeNode = (INodeWithAnnotationId)node;
                msStringBuilder.appendFormat(sb, "Id:{0}", commentRangeNode.getIdInternal());
                break;
            }

            case NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START:
            {
                StructuredDocumentTagRangeStart sdtRangeStart = (StructuredDocumentTagRangeStart)node;
                msStringBuilder.appendFormat(sb, "Id:0x{0:x8}", (long)sdtRangeStart.getId());
                break;
            }

            case NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_END:
            {
                StructuredDocumentTagRangeEnd sdtRangeEnd = (StructuredDocumentTagRangeEnd)node;
                msStringBuilder.appendFormat(sb, "Id:0x{0:x8}", (long)sdtRangeEnd.getId());
                break;
            }

            default:
                break;
        }

        return sb.toString();
    }

    private static void dumpNode(Node node) throws Exception
    {
        if (NoDocumentTree && node.getNodeType() != NodeType.DOCUMENT)
            return;

        // Indent sections.
        if ((node.getNodeType() == NodeType.SECTION) && (node.getDocument().getNodeType() == NodeType.DOCUMENT))
            write("");

        if (!skipNode(node))
            write(nodeToString(node));

        // FOSS

        if (!NoRevisionGroups && node.getDocument().getNodeType() == NodeType.DOCUMENT)
        {
            RevisionGroupCollection groups = node.fetchDocument().getRevisions().getGroups();
            for (int i = 0; i < groups.getCount(); i++)
            {
                RevisionGroup group = groups.get(i);

                if (msObject.referenceEquals(group.getStart(), node))
                {
                    String type = "";
                    if (group.getFormatRevision() != null)
                        type = "Formatting";
                    else if (group.getEditRevision() != null)
                        type = group.getEditRevision().getType() == EditRevisionType.DELETION
                            ? "Deletion"
                            : "Insertion";
                    else if (group.getMoveRevision() != null)
                        type = group.getMoveRevision().getType() == MoveRevisionType.MOVE_FROM
                            ? "MoveFrom"
                            : "MoveTo";

                    write(msString.format("  {{RevisionGroup: ({0}, {1}, '{2}')}}", i, type, getPrintableText(groups.get(i).getText())));
                }
            }
        }

        switch (node.getNodeType())
        {
            case NodeType.DOCUMENT:
            {
                dumpWarnings();

                Document doc = (Document)node;

                dumpInternManager(doc);

                dumpMsoEnvelope(doc);

                dumpStatistics(doc);

                if (!NoDocumentProperties)
                {
                    dumpCompatibility(doc);
                    dumpDocPr(doc);
                    dumpCustomization(doc);
                }

                dumpBuiltinDocumentProperties(doc);
                dumpCustomDocumentProperties(doc);
                dumpVariables(doc);

                dumpCustomXmlParts(doc.getCustomXmlParts());

                dumpPackageCustomParts(doc);
                dumpMailMerge(doc);

                if (doc.getVbaProject() != null)
                    dumpVbaProject(doc);

                dumpFontInfoCollection(doc);
                dumpHtmlBlockCollection(doc);

                dumpBackground(doc);
                dumpSignatures(doc);

                if (doc.getThemeInternal() != null)
                    dumpTheme(doc.getThemeInternal());

                dumpStyles(doc);
                dumpLists(doc);

                dumpFootnoteSeparators(doc.getFootnoteSeparators());

                if (doc.getGlossaryDocument() != null)
                {
                    write("");
                    dumpNode(doc.getGlossaryDocument());
                }

                break;
            }
            case NodeType.BOOKMARK_START:
            {
                BookmarkStart bkmkStart = (BookmarkStart)node;

                if (bkmkStart.isColumn())
                {
                    write(msString.format("  {{First column: '{0}'}}", bkmkStart.getFirstColumn()));
                    write(msString.format("  {{Last column: '{0}'}}", bkmkStart.getLastColumn()));
                }
                break;
            }
            case NodeType.HEADER_FOOTER:
            {
                HeaderFooter hf = (HeaderFooter)node;
                write(msString.format("  {{Type: '{0}'}}", hf.getHeaderFooterType()));
                write(msString.format("  {{IsLinkedToPrevious: '{0}'}}", hf.isLinkedToPrevious()));
                break;
            }
            case NodeType.BUILDING_BLOCK:
            {
                BuildingBlock block = (BuildingBlock)node;
                write(msString.format("  {{Name: '{0}'}}", block.getName()));
                write(msString.format("  {{Decorated: {0}}}", block.getDecorated()));
                write(msString.format("  {{Guid: {0}}}", block.getGuidInternal()));
                write(msString.format("  {{Description: '{0}'}}", block.getDescription()));
                write(msString.format("  {{Gallery: {0}}}", block.getGallery()));
                write(msString.format("  {{Category: '{0}'}}", block.getCategory()));
                write(msString.format("  {{Behavior: {0}}}", block.getBehavior()));
                write(msString.format("  {{Style: '{0}'}}", block.getStyle()));
                write(msString.format("  {{Type: {0}}}", block.getType()));
                break;
            }

            case NodeType.OFFICE_MATH:
            {
                OfficeMath officeMath = (OfficeMath)node;

                write(msString.format("  {{Type: {0}}}", officeMath.getMathObject().getMathObjectType()));

                MathObject mathObject = officeMath.getMathObject();
                dumpPr(mathObject);

                if (!NoRunAttributes)
                    dumpPr(((OfficeMath)node).getRunPr());

                if (mathObject.getMathObjectType() == MathObjectType.MATRIX)
                {
                    MathObjectMatrix matrix = (MathObjectMatrix)mathObject;
                    for (int i = 0; i < matrix.getColumnPrCollection().getCount(); i++)
                    {
                        MathMatrixColumnPr columnPr = matrix.getColumnPrCollection().get(i);
                        write(msString.format("  {{Column {0}: {1}}}", i, columnPr.getHorizontalAlignment()));
                    }
                }
                break;
            }

            case NodeType.FIELD_START:
                {
                    FieldStart fieldStart = (FieldStart)node;

                if (fieldStart.getFieldData() != null)
                    write(msString.format("  {{FieldData: {0}}}", byteArrayToString(fieldStart.getFieldData())));

                if (!NoRunAttributes)
                    dumpPr(fieldStart.getRunPr());
                break;
            }

            case NodeType.FIELD_SEPARATOR:
            {
                if (!NoRunAttributes)
                    dumpPr(((FieldSeparator)node).getRunPr());
                break;
            }

            case NodeType.FIELD_END:
            {
                if (!NoRunAttributes)
                    dumpPr(((FieldEnd)node).getRunPr());
                break;
            }

            case NodeType.SMART_TAG:
            {
                SmartTag smartTag = (SmartTag)node;
                for (CustomXmlProperty property : smartTag.getProperties())
                {
                    write(msString.format("  {{Property: {0}:{1}}}", property.getName(), property.getValue()));
                }
                break;
            }
            case NodeType.EDITABLE_RANGE_START:
            {
                EditableRangeStart erStart = (EditableRangeStart)node;
                write(msString.format("  {{Id: {0}}}", erStart.getId()));
                write(msString.format("  {{EditorGroup: '{0}'}}", erStart.getEditorGroup()));
                break;
            }
            case NodeType.EDITABLE_RANGE_END:
            {
                EditableRangeEnd erEnd = (EditableRangeEnd)node;
                write(msString.format("  {{Id: {0}}}", erEnd.getId()));
                break;
            }
            case NodeType.STRUCTURED_DOCUMENT_TAG:
            {
                dumpSdt((StructuredDocumentTag)node);
                break;
            }
            case NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START:
            {
                dumpSdt(((StructuredDocumentTagRangeStart)node).getInternalSdt());
                break;
            }
            case NodeType.PARAGRAPH:
            {
                Paragraph para = (Paragraph)node;

                if (ShowListLabels && (para.isListItemOriginal() || para.isListItemFinal()))
                    write(msString.format("  {{ListLabel: '{0}' -> '{1}'}}",
                        getPrintableText(para.getListLabel().getLabelStringOriginal()),
                        getPrintableText(para.getListLabel().getLabelStringFinal())));

                if (ShowFloating)
                {
                    boolean direct = para.getParaPr().isFloating();
                    boolean inherited = para.getExpandedParaPr(ParaPrExpandFlags.LAYOUT).isFloating();

                    if (inherited && !direct)
                        write("  {Floating: Inherited}");
                    else if (direct)
                        write("  {Floating: Direct}");
                }

                if (ShowParaId)
                {
                    if (para.getParaId() != 0)
                        write(msString.format("  {{ParaId: {0}}}", msString.format("0x{0:x8}", para.getParaId())));

                    if (para.getTextId() != 0)
                        write(msString.format("  {{TextId: {0}}}", msString.format("0x{0:x8}", para.getTextId())));
                }

                if (!NoParagraphAttributes)
                {
                    dumpPr(para.getParaPr(), para);

                    if (ShowExpanded)
                    {
                        write("  >>");
                        dumpPr(para.getExpandedParaPr(ParaPrExpandFlags.LAYOUT), para);
                    }

                    if (!NoAttributes && hasAttrs(para.getParagraphBreakRunPr(), para))
                    {
                        write(".");
                        dumpPr(para.getParagraphBreakRunPr(), para);
                    }
                }

                IndentLevel++;
                if (NoParagraphContent)
                    write(node.getText());
                IndentLevel--;

                break;
            }
            case NodeType.RUN:
                if (!NoRunAttributes)
                {
                    Run run = (Run)node;

                    if (ShowCharacterCategory)
                    {
                        ArrayList<Integer> cats = new ArrayList<Integer>();
                        for (int i = 0; i < run.getText().length(); i++)
                        {
                        	char ch = run.getText().charAt(i);
                            /*CharacterCategory*/int cat = WordUtil.getCharacterCategory((int)ch);
                            msArrayList.add(cats, cat);
                        }

                        StringBuilder sb = new StringBuilder();
                        Collections.sort(cats);
                        for (/*CharacterCategory*/int cat : (Iterable</*CharacterCategory*/Integer>) cats)
                        {
                            if (sb.length() > 0)
                                msStringBuilder.append(sb, ", ");
                            msStringBuilder.append(sb, cat);
                        }
                        write(msString.format("  {{Category: {0}}}", sb.toString()));
                    }

                    if (ShowUnicodeBlocks)
                    {
                        SortedList<Integer, Integer> blocks = new SortedList<Integer, Integer>();
                        for (int i = 0; i < run.getText().length(); i++)
                        {
                        	char ch = run.getText().charAt(i);
                            /*UnicodeBlocks*/int block = UnicodeUtil.getUnicodeBlock(ch);
                            blocks.set(block, 0);
                        }
                        StringBuilder sb = new StringBuilder();
                        for (/*UnicodeBlocks*/int block : (Iterable</*UnicodeBlocks*/Integer>) blocks.getKeys())
                        {
                            if (sb.length() > 0)
                                msStringBuilder.append(sb, ", ");
                            msStringBuilder.append(sb, block);
                        }
                        write(msString.format("  {{Unicode: {0}}}", sb.toString()));
                    }

                    dumpPr(run.getRunPr(), run);
                }
                break;
            case NodeType.TABLE:
                dumpPr(((Table)node).getTablePr());
                if (DumpCellerData)
                    dumpCeller((Table)node);
                break;
            case NodeType.ROW:
                Row row = (Row)node;
                if (ShowParaId)
                {
                    if (row.getParaId() > 0)
                        write(msString.format("  {{ParaId: {0}}}", msString.format("0x{0:x8}", row.getParaId())));

                    if (row.getTextId() > 0)
                        write(msString.format("  {{TextId: {0}}}", msString.format("0x{0:x8}", row.getTextId())));
                }

                if (!NoTableAttributes)
                {
                    int totalWidth = 0;
                    int totalPreferredWidth = 0;
                    boolean allFixed = true;
                    for (int i = 0; i < row.getCells().getCount(); i++)
                    {
                        Cell cell = row.getCells().get(i);
                        totalWidth += cell.getCellPr().getWidth();

                        if (cell.getCellPr().getPreferredWidth().isFixed())
                            totalPreferredWidth += cell.getCellPr().getPreferredWidth().getValueTwips();
                        else
                            allFixed = false;
                    }

                    write(msString.format("  {{TotalWidth: {0}}}", totalWidth));
                    if (allFixed)
                        write(msString.format("  {{TotalPreferredWidth: {0}}}", totalPreferredWidth));

                    dumpPr(row.getTablePr(), row);
                }
                break;
            case NodeType.CELL:
            {
                Cell cell = (Cell)node;
                if (!NoCellAttributes)
                    dumpPr(cell.getCellPr(), node);

                if (DumpCellerData)
                    dumpCeller(cell);

                break;
            }
            case NodeType.SECTION:
                if (!NoSectionAttributes)
                    dumpPr(((Section)node).getSectPr(), node);
                break;
            case NodeType.SHAPE:
            case NodeType.GROUP_SHAPE:
            {
                ShapeBase shape = (ShapeBase)node;
                write(msString.format("  {{Shape.MarkupLanguage: {0}}}", shape.getMarkupLanguage()));
                if (!NoShapeAttributes)
                {
                    dumpShapePr(shape.getShapePr(), shape);
                    dumpPr(shape.getRunPr(), shape);

                }
                if (shape.getMarkupLanguage() == ShapeMarkupLanguage.DML)
                    dumpDmlNode(shape.getDmlNode());

                if (shape.isOle())
                    dumpOle(shape);

                break;
            }
            case NodeType.FORM_FIELD:
            {
                FormField formField = (FormField)node;

                dumpPr(formField.getFormFieldPr());

                if (!NoRunAttributes)
                    dumpPr(formField.getRunPr());


                break;
            }
            case NodeType.FOOTNOTE:
            {
                Footnote footnote = (Footnote)node;

                write(msString.format("  {{{0}, {1}}}",
                                    footnote.getFootnoteType(),
                                    footnote.isAuto() ? "Auto" : msString.format("Custom: '{0}'", getPrintableText(footnote.getReferenceMark()))));
                dumpPr(footnote.getRunPr());
                break;
            }
            case NodeType.SPECIAL_CHAR:
            {
                IndentLevel++;
                write(getPrintableText(node.getText()));
                IndentLevel--;
                if (!NoRunAttributes)
                    dumpPr(((SpecialChar)node).getRunPr());
                break;
            }
            case NodeType.MOVE_FROM_RANGE_START:
            {
                MoveFromRangeStart moveFrom = (MoveFromRangeStart)node;
                write(msString.format("  {{{0}, '{1}', ({2})}}", moveFrom.getId(), moveFrom.getName(),
                    NoRevisionDetails ? "-" : msString.format("{0} {1}", moveFrom.getAuthor(), moveFrom.getDate())));
                break;
            }
            case NodeType.MOVE_FROM_RANGE_END:
            {
                MoveFromRangeEnd moveFrom = (MoveFromRangeEnd)node;
                write(msString.format("  {{{0}}}", moveFrom.getId()));
                break;
            }
            case NodeType.MOVE_TO_RANGE_START:
            {
                MoveToRangeStart moveTo = (MoveToRangeStart)node;
                write(msString.format("  {{{0}, '{1}', ({2})}}", moveTo.getId(), moveTo.getName(),
                    NoRevisionDetails ? "-" : msString.format("{0} {1}", moveTo.getAuthor(), moveTo.getDate())));
                break;
            }
            case NodeType.MOVE_TO_RANGE_END:
            {
                MoveToRangeEnd moveTo = (MoveToRangeEnd)node;
                write(msString.format("  {{{0}}}", moveTo.getId()));
                break;
            }
            case NodeType.SUB_DOCUMENT:
            {
                SubDocument subDoc = (SubDocument)node;
                write(msString.format("  {{{0}}}", subDoc.getFileName()));
                break;
            }
            case NodeType.SYSTEM:
            {
                if (node instanceof SdtMarkerStart)
                {
                    SdtMarkerStart start = (SdtMarkerStart)node;
                    write(msString.format("  {{{0};{1}}}", start.getInternalSdt().getId(), start.getInternalSdt().getLevel()));
                }

                if (node instanceof SdtMarkerEnd)
                {
                    SdtMarkerEnd end = (SdtMarkerEnd)node;
                    write(msString.format("  {{{0}}}", end.Id));
                }

                break;
            }
            default:
                // No additional data from other nodes is required.
                break;
        }

        if (node instanceof CompositeNode)
        {
            if (!((node.getNodeType() == NodeType.PARAGRAPH) && NoParagraphContent))
            {
                for (Node child : (Iterable<Node>) ((CompositeNode)node).getChildNodes(NodeType.ANY, false))
                {
                    if (NoTableBody && (node.getNodeType() == NodeType.TABLE))
                        continue;

                    // Don't indent after Document node.
                    if (node.getNodeType() != NodeType.DOCUMENT)
                        IndentLevel++;

                    dumpNode(child);

                    if (node.getNodeType() != NodeType.DOCUMENT)
                        IndentLevel--;
                }
            }
        }
    }

    private static void dumpSdt(StructuredDocumentTag sdt) throws Exception
    {
        if (NoAttributes)
            return;

        write(msString.format("  {{Level: {0}}}", sdt.getLevel()));
        write(msString.format("  {{Placeholder: {0}}}", sdt.getPlaceholder()));
        write(msString.format("  {{PlaceholderName: '{0}'}}", sdt.getPlaceholderName()));
        write(msString.format("  {{IsShowingPlaceholderText: {0}}}", sdt.isShowingPlaceholderText()));
        write(msString.format("  {{Type: {0}}}", sdt.getSdtType()));
        write(msString.format("  {{Id: {0}}}", sdt.getId()));
        write(msString.format("  {{Tag: '{0}'}}", sdt.getTag()));
        write(msString.format("  {{Title: {0}}}", sdt.getTitle()));
        write(msString.format("  {{IsDocxExtension: {0}}}", Boolean.toString(sdt.isDocxExtension())));

        if (sdt.getSdtType() == SdtType.CHECKBOX)
        {
            write(msString.format("  {{Checked: {0}}}", sdt.getChecked()));
            write(msString.format("  {{ContentChecked: {0}}}", sdt.getContentChecked()));
        }

        if (sdt.getSdtType() == SdtType.DROP_DOWN_LIST)
        {
            IndentLevel++;
            for (SdtListItem item : sdt.getListItems())
                write(msString.format("  {{'{0}': '{1}'}}", item.getValue(), item.getDisplayText()));

            IndentLevel--;
        }

        if (!sdt.getXmlMapping().isEmpty())
            dumpXmlMapping(sdt.getXmlMapping());
        IndentLevel++;
        dumpPr(sdt.getContentsRunPr());
        write(".");
        dumpPr(sdt.getEndCharacterRunPr());
        IndentLevel--;
    }

    private static void dumpCeller(Table table) throws Exception
    {
        try
        {
            CellerTable celler = new CellerTable(table);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < celler.getColumnCount(); i++)
                msStringBuilder.append(sb, celler.getColumnWidth(i) + " ");

            write(msString.format("  {{Celler: ({0})}}", msString.trim(sb.toString())));

        }
        catch (Exception e)
        {
            write(msString.format("  {{Celler: ({0})}}", e.getMessage()));
        }
    }

    private static void dumpCeller(Cell cell) throws Exception
    {
        CellerTable celler = new CellerTable(cell.getParentTable(), CellerTableOptions.GOTO_MERGED_CELLS);

        for (int i = 0; i < cell.getRowIndex(); i++)
            celler.gotoNextRow();

        celler.gotoFirstCell();

        for (int col = 0; col < celler.getColumnCount(); col++)
        {
            if (msObject.referenceEquals(cell, celler.getCurCell().getCellNode()))
                write(msString.format("  {{Celler: ({2}: c{0}, r{1})}}", celler.getCurCell().getColSpan(), celler.getCurCell().getRowSpan(), col));

            celler.gotoNextCell();
        }
    }

    private static String dmlAdjustableCoordinateToString(DmlAdjustableCoordinate coord)
    {
        return msString.format("{0}", coord.getString());
    }

    private static String dmlAdjustableAngleToString(DmlAdjustableAngle angle)
    {
        return msString.format("{0}", angle.getString());
    }

    private static String dmlAdjustablePointToString(DmlAdjustablePoint point)
    {
        return msString.format("X:{0}, Y:{1}", dmlAdjustableCoordinateToString(point.getX()), dmlAdjustableCoordinateToString(point.getY()));
    }

    private static void dumpInternManager(DocumentBase doc) throws Exception
    {
        if (doc.InternManager == null)
            return;

        InternManager intern = doc.InternManager;

        write("\n[InternManager]");

        write(msString.format("  {{Called: {0}}}", intern.CalledCount));
        write(msString.format("  {{Empty: {0}}}", intern.EmptyCount));
        write(msString.format("  {{Repeated: {0}}}", intern.RepeatedCount));
        write(msString.format("  {{Complex: {0}}}", intern.ComplexCount));
        write(msString.format("  {{Interned: {0}}}", intern.InternedCount));
        write(msString.format("  {{Pooled: {0}}}", intern.PooledCount));
        write(msString.format("  {{Uninterned: {0}}}", intern.UnInternedCount));
        write(msString.format("  {{Unpooled: {0}}}", intern.UnPooledCount));

        write(msString.format("  {{Pool: (size: {0})}}", intern.getPool().size()));

        SortedIntegerListGeneric<InternPoolItem> sortedPool = new SortedIntegerListGeneric<InternPoolItem>();
        for (InternPoolItem item : intern.getPool().values())
            sortedPool.add(item.Id, item);

        IndentLevel++;
        for (int i = 0; i < sortedPool.getCount(); i++)
        {
            InternPoolItem item = sortedPool.getByIndex(i);

            write(msString.format("{{0x{0:x4}:{1}}}", item.Id, item.RefCount));

            boolean savedAllAttributes = AllAttributes;
            boolean savedShowIntern = ShowIntern;
            AllAttributes = true;
            dumpPr(item.Pr);
            AllAttributes = savedAllAttributes;
            ShowIntern = savedShowIntern;
        }
        IndentLevel--;
    }

    private static void dumpDmlChartSpace(DmlChartSpace chartSpace) throws Exception
    {
        StringBuilder sb = new StringBuilder();
        if (chartSpace.getAspectRatioLocked())
            msStringBuilder.append(sb, "AspectRatioLocked, ");
        if (chartSpace.getAutoUpdate())
            msStringBuilder.append(sb, "AutoUpdate, ");
        if (chartSpace.getDate1904())
            msStringBuilder.append(sb, "Date1904, ");
        if (chartSpace.getHidden())
            msStringBuilder.append(sb, "Hidden, ");
        if (chartSpace.isChartEx())
            msStringBuilder.append(sb, "IsChartEx, ");
        if (chartSpace.isWord2007OrLower())
            msStringBuilder.append(sb, "IsWord2007OrLower, ");
        if (chartSpace.getRoundedCorners())
            msStringBuilder.append(sb, "RoundedCorners, ");
        if (chartSpace.getUseWord2010Style())
            msStringBuilder.append(sb, "UseWord2010Style, ");
        write("{{DmlChartSpace: {0}}}", msString.trim(sb.toString(), G_LIST_SEPARATOR_CHARS));

        IndentLevel++;
        dumpDmlChartFormat(chartSpace.getChartFormat());
        IndentLevel--;
    }

    private static void dumpDmlChartFormat(DmlChartFormat chartFormat) throws Exception
    {
        StringBuilder sb = new StringBuilder();
        if (chartFormat.getAutoTitleDeleted())
            msStringBuilder.append(sb, "AutoTitleDeleted, ");
        if (chartFormat.getPlotVisOnly())
            msStringBuilder.append(sb, "PlotVisOnly, ");
        if (chartFormat.getShowDLblsOverMax())
            msStringBuilder.append(sb, "ShowDLblsOverMax, ");
        if (chartFormat.getTitleDeleted())
            msStringBuilder.append(sb, "TitleDeleted, ");
        write("{{DmlChartFormat: {0}}}", msString.trim(sb.toString(), G_LIST_SEPARATOR_CHARS));

        IndentLevel++;
        dumpPlotArea(chartFormat.getPlotArea());

        DmlChartTitle chartTitle = chartFormat.getDCTitle();
        if (chartTitle != null)
        {
            write("{ChartTitle}");
            write("  {{Overlay: {0}}}", chartTitle.getOverlay());
            write("  {{Show: {0}}}", chartTitle.getShow());
            write("  {{Text: '{0}'}}", chartTitle.getText());

            if (chartTitle.getTx() != null)
            {
                write("  {DmlChartTx}");
                DmlChartTx chartTx = chartTitle.getTx();
                IndentLevel++;
                write("{{TxType: {0}}}", chartTx.getTxType());
                write("{{PlainText: '{0}'}}", chartTx.getPlainText());
                if (chartTx.getFormula() != null)
                    write("{{Formula: '{0}'}}", chartTx.getFormula());
                write("{{ReachText: '{0}'}}", chartTx.getRichText());
                if (chartTx.getStrRef() != null)
                    write("{{StrRef: '{0}'}}", chartTx.getStrRef());
                IndentLevel--;
            }

            if (chartTitle.getTxPr() != null)
            {
                write("  {DmlChartTxPr}");
                DmlChartTxPr chartTxPr = chartTitle.getTxPr();
                IndentLevel++;
                dumpDmlRunProperties(chartTxPr.getRunPr());
                IndentLevel--;
            }
        }
        IndentLevel--;
    }

    private static void dumpPlotArea(DmlChartPlotArea plotArea) throws Exception
    {
        StringBuilder sb = new StringBuilder();
        if (plotArea.hasAxis())
            msStringBuilder.append(sb, "HasAxis, ");
        if (plotArea.isInner())
            msStringBuilder.append(sb, "IsInner, ");
        if (plotArea.isSurface3D())
            msStringBuilder.append(sb, "HaIsSurface3DsAxis, ");
        if (plotArea.getRenderLegendForDataPoints())
            msStringBuilder.append(sb, "RenderLegendForDataPoints, ");
        write("{{DmlChartPlotArea: {0}}}", msString.trim(sb.toString(), G_LIST_SEPARATOR_CHARS));

        for (DmlChart dmlChart : plotArea.getCharts())
        {
            IndentLevel++;
            dumpDmlChart(dmlChart);
            IndentLevel--;
        }
    }

    private static void dumpDmlChart(DmlChart dmlChart) throws Exception
    {
        StringBuilder sb = new StringBuilder();
        if (dmlChart.getIs3D())
            msStringBuilder.append(sb, "Is3D, ");
        if ((dmlChart instanceof DmlBubbleChart) && ((DmlBubbleChart)dmlChart).getBubble3D())
            msStringBuilder.append(sb, "IsBubble3D, ");
        if (dmlChart.isRadarChart())
            msStringBuilder.append(sb, "IsRadarChart, ");
        if (dmlChart.hasAxis())
            msStringBuilder.append(sb, "HasAxis, ");
        if (dmlChart.getRenderLegendForDataPoints())
            msStringBuilder.append(sb, "RenderLegendForDataPoints, ");
        if (dmlChart.getVaryColors())
            msStringBuilder.append(sb, "VaryColors, ");
        write(msString.format("{{DmlChart: {0}, {1}}}", dmlChart.getChartType(), msString.trim(sb.toString(), G_LIST_SEPARATOR_CHARS)));

        IndentLevel++;
        ChartDataLabelCollection dataLabels = dmlChart.getDataLabels();
        sb = new StringBuilder();
        if (dataLabels.getShowCategoryName())
            msStringBuilder.append(sb, "ShowCategoryName, ");
        if (dataLabels.getShowBubbleSize())
            msStringBuilder.append(sb, "ShowBubbleSize, ");
        if (dataLabels.getShowDataLabelsRange())
            msStringBuilder.append(sb, "ShowDataLabelsRange, ");
        if (dataLabels.getShowLeaderLines())
            msStringBuilder.append(sb, "ShowLeaderLines, ");
        if (dataLabels.getShowLegendKey())
            msStringBuilder.append(sb, "ShowLegendKey, ");
        if (dataLabels.getShowPercentage())
            msStringBuilder.append(sb, "ShowPercentage, ");
        if (dataLabels.getShowSeriesName())
            msStringBuilder.append(sb, "ShowSeriesName, ");
        if (dataLabels.getShowValue())
            msStringBuilder.append(sb, "ShowValue, ");
        write("{{ChartDataLabelCollection: {0}}}", msString.trim(sb.toString(), G_LIST_SEPARATOR_CHARS));

        IndentLevel++;
        for (ChartDataLabel dataLabel : dataLabels)
        {
            sb = new StringBuilder();
            if (dataLabel.getShowCategoryName())
                msStringBuilder.append(sb, "ShowCategoryName, ");
            if (dataLabel.getShowBubbleSize())
                msStringBuilder.append(sb, "ShowBubbleSize, ");
            if (dataLabel.getShowDataLabelsRange())
                msStringBuilder.append(sb, "ShowDataLabelsRange, ");
            if (dataLabel.getShowLeaderLines())
                msStringBuilder.append(sb, "ShowLeaderLines, ");
            if (dataLabel.getShowLegendKey())
                msStringBuilder.append(sb, "ShowLegendKey, ");
            if (dataLabel.getShowPercentage())
                msStringBuilder.append(sb, "ShowPercentage, ");
            if (dataLabel.getShowSeriesName())
                msStringBuilder.append(sb, "ShowSeriesName, ");
            if (dataLabel.getShowValue())
                msStringBuilder.append(sb, "ShowValue, ");
            write("{{ChartDataLabel: {0}}}", msString.trim(sb.toString(), G_LIST_SEPARATOR_CHARS));
        }
        IndentLevel--;

        for (ChartSeries series : dmlChart.getSeries())
        {
            sb = new StringBuilder();
            if (series.hasPoints())
                msStringBuilder.append(sb, "HasValues, ");
            if (series.getHidden())
                msStringBuilder.append(sb, "Hidden, ");
            if (series.getInvertIfNegative())
                msStringBuilder.append(sb, "InvertIfNegative, ");
            if (series.getBubble3D())
                msStringBuilder.append(sb, "Bubble3D, ");
            if (series.getSmooth())
                msStringBuilder.append(sb, "Smooth, ");
            if (series.getSmoothExplicitlySet())
                msStringBuilder.append(sb, "SmoothExplicitlySet, ");
            write("{{ChartSeries: {0}}}", msString.trim(sb.toString(), G_LIST_SEPARATOR_CHARS));

            IndentLevel++;
            dumpDmlChartDataSource(series.getX(), "XValues");
            dumpDmlChartDataSource(series.getY(), "YValues");
            IndentLevel--;
        }

        IndentLevel--;
    }

    private static void dumpDmlChartDataSource(DmlChartDimensionData dimensionData, String name) throws Exception
    {
        StringBuilder sb = new StringBuilder();
        if (dimensionData.isEmpty())
            msStringBuilder.append(sb, "HasValues, ");
        if (dimensionData.isDate())
            msStringBuilder.append(sb, "IsDate, ");
        write(msString.format("{{DmlChartDimensionData.{0}: {1}}}", name, msString.trim(sb.toString(), G_LIST_SEPARATOR_CHARS)));

        if (dimensionData.getData() != null)
        {
            for (int i = 0; i < dimensionData.getData().getValueCount(); i++)
            {
                IndentLevel++;
                DmlChartValue dmlChartValue = dimensionData.getValue(i);

                if (dmlChartValue != null)
                {
                    sb = new StringBuilder();
                    if (dmlChartValue.isVisible())
                        msStringBuilder.append(sb, "IsVisible, ");
                    if (dmlChartValue.isDate())
                        msStringBuilder.append(sb, "IsDate, ");
                    if (dmlChartValue.isNaN())
                        msStringBuilder.append(sb, "IsNaN, ");
                    write(
                        msString.format(
                            "{{DmlChartValue: {0}; {1}; {2}}}",
                            dmlChartValue.getValue(),
                            dmlChartValue.getValueType(),
                            msString.trim(sb.toString(), G_LIST_SEPARATOR_CHARS)));
                }

                IndentLevel--;
            }
        }
    }

    private static void dumpDmlRunProperties(DmlRunProperties runPr) throws Exception
    {
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.ALTERNATIVE_LANGUAGE);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.BOLD);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.BASELINE);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.BOOKMARK_LINK_TARGET);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.CAPITALIZATION);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.IS_DIRTY);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.HAS_SPELLING_ERROR);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.ITALICS);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.KERNING);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.KUMIMOJI);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.LANGUAGE);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.NO_PROOFING);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.NORMALIZE_HEIGHTS);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.SMART_TAGS_CLEAN);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.SMART_TAG_ID);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.SPACING);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.STRIKETHROUGH);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.FONT_SIZE);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.UNDERLINE);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.FILL);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.OUTLINE);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.HIGHLIGHT_COLOR);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.LATIN_FONT);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.EAST_ASIAN_FONT);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.SYMBOL_FONT);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.COMPLEX_SCRIPT_FONT);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.HLINK_CLICK);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.HLINK_HOVER);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.RTL);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.UNDERLINE_FILL);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.UNDERLINE_FILL_TX);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.UNDERLINE_STROKE);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.UNDERLINE_STROKE_TX);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.RIGHT_TO_LEFT_FLOW_DIRECTION);
        dumpDmlRunProperty(runPr, DmlRunPropertiesIds.EXTENSIONS);
    }

    private static void dumpDmlRunProperty(DmlRunProperties runPr, /*DmlRunPropertiesIds*/int key) throws Exception
    {
        if (!runPr.isPropertySpecified(key))
            return;

        switch (key)
        {
            case DmlRunPropertiesIds.LATIN_FONT:
            case DmlRunPropertiesIds.EAST_ASIAN_FONT:
            case DmlRunPropertiesIds.COMPLEX_SCRIPT_FONT:
            case DmlRunPropertiesIds.SYMBOL_FONT:
                DmlFont dmlFont = (DmlFont)runPr.getDirectProperty(key);
                write(msString.format("{{{0}: {1}}}", key, dmlFontToString(dmlFont)));
                break;

            case DmlRunPropertiesIds.KERNING:
            case DmlRunPropertiesIds.FONT_SIZE:
            case DmlRunPropertiesIds.SPACING:
                DmlTextPoints dmlTextPoints = (DmlTextPoints)runPr.getDirectProperty(key);
                write(msString.format("{{{0}: {1}}}", key, dmlTextPoints.getValueInPoints()));
                break;

            default:
                Object val = runPr.getDirectProperty(key);
                write(msString.format("{{{0}: {1}}}", key, val));
                break;
        }
    }

    private static String dmlFontToString(DmlFont dmlFont)
    {
        return msString.format("('{0}', Panose: '{1}', Charset: {2}, SimilarFamily: {3})",
            dmlFont.getTextTypeface(), dmlFont.getPanoseSetting(), dmlFont.getSimilarCharacterSet(), dmlFont.getSimilarFontFamily());
    }

    private static void dumpDmlNode(DmlNode dmlNode) throws Exception
    {
        if (!ShowDmlNode)
            return;

        IndentLevel++;
        write("{DmlNode}");
        write(msString.format("  {{Type: {0}}}", dmlNode.getDmlNodeType()));

        DmlTransform transform = dmlNode.getTransform();
        write(msString.format("  {{Transform: {0}, {1}}}", transform.getX(), transform.getY()));

        IndentLevel++;
        switch (dmlNode.getDmlNodeType())
        {
            case DmlNodeType.CHART:
                dumpDmlChartSpace((DmlChartSpace)dmlNode);
                break;

            case DmlNodeType.PICTURE:
                DmlPicture dmlPicture = (DmlPicture)dmlNode;
                write(msString.format("{{Id: {0}}}", dmlPicture.getNonVisualPr().getNvDrawingProperties().getId()));
                write(msString.format("{{Name: {0}}}", dmlPicture.getNonVisualPr().getNvDrawingProperties().getName()));
                write(msString.format("{{BlipFill.ImageBytes: {0}}}", imageBytesToString(dmlPicture.getBlipFill().getImageBytes())));
                break;

            case DmlNodeType.DIAGRAM:
            {
                DmlDiagram dmlDiagram = (DmlDiagram)dmlNode;
                write(msString.format("{{Title: {0}}}", dmlDiagram.getTitle()));
                write(msString.format("{{Name: {0}}}", dmlDiagram.getName()));
                DmlOutline outline = dmlDiagram.getOutline();
                write("{Outline}");
                write(msString.format("  {{DashType: {0}}}", outline.getDash().getDashType()));
                write(msString.format("  {{DashStyle: {0}}}", outline.getDashStyle()));
                break;
            }

            case DmlNodeType.WORDPROCESSING_SHAPE:
            {
                DmlShape dmlShape = (DmlShape)dmlNode;

                write(msString.format("{{TextWrappingType: {0}}}", dmlShape.getTextBodyPr().getTextWrappingType()));

                DmlFill fill = dmlShape.getFill();
                write(msString.format("{{Fill: {0}/{1}}}", fill.getFillType(), fill.getDmlFillType()));
                if (dmlShape.getFill().getDmlFillType() == DmlFillType.BLIP_FILL)
                {
                    write(msString.format("  {{Blip.EmbedImage: {0}}}", imageBytesToString(((DmlBlipFill)fill).getBlip().getEmbedImage())));
                }

                StringToObjDictionary<DmlExtension> spPrExt = dmlShape.getExtensions();
                if (spPrExt != null)
                    write(msString.format("{{SpPrExtensions: {0}}}", spPrExt.getCount()));

                if (dmlShape.getEffects() != null)
                {
                    write(msString.format("{{DmlShapeEffects: {0}}}", dmlShape.getEffects().getCount()));
                    IndentLevel++;
                    for (DmlShapeEffect effect : dmlShape.getEffects())
                    {
                        write(msString.format("{{Type: {0}}}", DmlShapeEffectType.toString(effect.getEffectType())));
                        IndentLevel++;
                        write(dmlShapeEffectToString(effect));
                        IndentLevel--;
                    }
                    IndentLevel--;
                }
                DmlGeometry geometry = dmlShape.getGeometry();
                write(msString.format("{{Geometry.Paths: {0}}}", geometry.getPaths().size()));
                for (DmlPath path : geometry.getPaths())
                {
                    for (IDmlPathPart pathPart : path.getPathParts())
                    {
                        switch (pathPart.getPathPartType())
                        {
                            case DmlPathPartType.ARC_TO:
                                DmlArcTo arcTo = (DmlArcTo)pathPart;
                                write(msString.format(
                                    "  {{ArcTo (WidthRadius: {0}, HeightRadius: {1}, StartAngle: {2}, SwingAngle: {3})}}",
                                    dmlAdjustableCoordinateToString(arcTo.getWidthRadius()),
                                    dmlAdjustableCoordinateToString(arcTo.getHeightRadius()),
                                    dmlAdjustableAngleToString(arcTo.getStartAngle()),
                                    dmlAdjustableAngleToString(arcTo.getSwingAngle())));
                                break;

                            case DmlPathPartType.CLOSE:
                                write("  {Close}");
                                break;

                            case DmlPathPartType.MOVE_TO:
                                DmlMoveTo moveTo = (DmlMoveTo)pathPart;
                                write(msString.format("  {{MoveTo ({0})}}", dmlAdjustablePointToString(moveTo.getPoint())));
                                break;

                            case DmlPathPartType.LINE_TO:
                                DmlLineTo lineTo = (DmlLineTo)pathPart;
                                write(msString.format("  {{LineTo ({0})}}", dmlAdjustablePointToString(lineTo.getPoint())));
                                break;

                            case DmlPathPartType.QUADRATIC_BEZIER_TO:
                                DmlQuadraticBezierTo bezier = (DmlQuadraticBezierTo)pathPart;
                                write(msString.format("  {{QuadraticBezierTo (ControlPoint: ({0}), EndPoint: ({1}))}}",
                                    dmlAdjustablePointToString(bezier.getControlPoint()),
                                    dmlAdjustablePointToString(bezier.getEndPoint())));
                                break;

                            default:
                                write(msString.format("  {{{0}}}", pathPart.getPathPartType()));
                                break;
                        }
                    }
                }

                ArrayList<DmlAdjustHandle> adjustHandles = dmlShape.getGeometry().getAdjustHandles();
                write(msString.format("  {{Geometry.AdjustHandles: {0}}}", adjustHandles.size()));
                break;
            }

            default:
                break;
        }
        IndentLevel--;
        IndentLevel--;
    }

    private static void dumpCustomXmlParts(CustomXmlPartCollection customXmlParts) throws Exception
    {
        // Dump sorted.
        ArrayList<CustomXmlPart> sortedList = new ArrayList<CustomXmlPart>();
        for (CustomXmlPart xmlPart : customXmlParts)
            msArrayList.add(sortedList, xmlPart);

        Collections.sort(sortedList, new CustomXmlPartComparer());

        for (CustomXmlPart xmlPart : sortedList)
        {
            write(msString.format("\n[CustomXmlPart: {0}]", xmlPart.getId()));

            IndentLevel++;
            for (String schema : xmlPart.getSchemas())
                if (StringUtil.hasChars(schema))
                    write(msString.format("{{Schema: '{0}'}}", schema));

            write(msString.format("{{Data: {0}}}", byteArrayToString(xmlPart.getData())));

            if (DumpCustomXmlPartDocuments)
            {
                try
                {
                    org.w3c.dom.Document xmlDoc = com.aspose.XmlUtilPal.loadXml(new MemoryStream(xmlPart.getData()), false);

                    String inner = xmlDoc.getFirstChild().getTextContent();

                    byte[] innerBytes = Encoding.getUTF8().getBytes(inner);
                    FileFormatDetector detector = new FileFormatDetector();
                    FileFormatInfo fi = detector.detect(new MemoryStream(innerBytes));
                    if (fi.getLoadFormat() != LoadFormat.UNKNOWN)
                    {
                        write(msString.format("{{FileFormat: {0}}}", fi.getLoadFormat()));

                        Document innerDoc = new Document(new MemoryStream(innerBytes));

                        String savedFileName = FileName;
                        TextWriter savedWriter = Writer;

                        String partName = FileName;
                        if (partName.endsWith(".model"))
                            partName = partName.substring((0), (0) + (partName.length() - 6));

                        save(innerDoc, msString.format("{0}.{1}.model", partName, xmlPart.getId()));
                        FileName = savedFileName;
                        Writer = savedWriter;

                        FileStream f = File.create(msString.format("{0}.{1}.xml", partName, xmlPart.getId()));
                        f.write(innerBytes, 0, innerBytes.length);
                        f.close();
                    }
                }
                catch(Exception e)
                {
                }
            }


            IndentLevel--;
        }
    }

    private static class CustomXmlPartComparer implements Comparator<CustomXmlPart>
    {
        public int /*IComparer<CustomXmlPart>.*/compare(CustomXmlPart x, CustomXmlPart y)
        {
            return StringOrdinalComparer.DEFAULT.compare(x.getId(), y.getId());
        }
    }

    private static void dumpXmlMapping(XmlMapping xmlMapping) throws Exception
    {
        write("  {{XmlMapping.IsDocx15Extension: '{0}'}}", Boolean.toString(xmlMapping.isDocx15Extension()));
        write("  {{XmlMapping.StoreItemId: '{0}'}}", xmlMapping.getStoreItemId().toLowerCase());
        write("  {{XmlMapping.XPath: '{0}'}}", xmlMapping.getXPath());
        write("  {{XmlMapping.StoreItemChecksum: '{0}'}}", xmlMapping.getStoreItemChecksum());
    }

    private static void dumpOle(ShapeBase shape) throws Exception
    {
        IEmbeddedObject embeddedObject = (IEmbeddedObject)shape.getShapePr().get(ShapeAttr.OLE_OBJECT);
        if (embeddedObject instanceof Forms2OleControl)
            dumpForms2OleControl((Forms2OleControl)embeddedObject);
        else if (embeddedObject instanceof HtmlOleControl)
            dumpHtmlOleControl((HtmlOleControl)embeddedObject);
        else if (embeddedObject instanceof OleControl)
            dumpOleControl((OleControl)embeddedObject);
        else if (embeddedObject instanceof OleObject)
            dumpOleObject((OleObject)embeddedObject);
        else if (embeddedObject instanceof OoxmlObject)
            dumpOoxmlObject((OoxmlObject)embeddedObject);
        else
            write("{EmbeddedObject.Null}");
    }

    private static void dumpOleControl(OleControl oleControl) throws Exception
    {
        write("{EmbeddedObject.OleControl}");
        dumpEmbeddedObject(oleControl);
    }

    private static void dumpOleObject(OleObject oleObject) throws Exception
    {
        write("{EmbeddedObject.OleObject}");

        dumpEmbeddedObject(oleObject);

        MemoryStorage data = oleObject.getData();
        IndentLevel++;
        for (int i = 0; i < data.getCount(); i++)
        {
            Object child = data.getByIndex(i);
            if (child instanceof MemoryStream)
            {
                String streamName = data.getKey(i);
                MemoryStream stream = (MemoryStream)child;
                write(
                    msString.format(
                        "{{'{0}': {1}}}",
                        getPrintableText(streamName),
                        byteArrayToString(stream.toArray())));
                IndentLevel++;
                dumpOleStream(streamName, data);
                IndentLevel--;
            }
        }
        IndentLevel--;
    }

    private static void dumpOoxmlObject(OoxmlObject ooxmlObject) throws Exception
    {
        write("{EmbeddedObject.OoXmlObject}");

        dumpEmbeddedObject(ooxmlObject);

        IndentLevel++;
        write(msString.format("{{ContentType: {0}}}", ooxmlObject.getContentType()));
        write(msString.format("{{Data: {0}}}", byteArrayToString(((MemoryStream)ooxmlObject.getStream()).toArray())));
        IndentLevel--;
    }

    private static void dumpEmbeddedObject(IEmbeddedObject embeddedObject) throws Exception
    {
        IndentLevel++;
        write(msString.format("{{Name: '{0}'}}", embeddedObject.getName()));
        write(msString.format("{{Clsid: {0}}}", embeddedObject.getClsidInternal()));
        IndentLevel--;
    }

    private static void dumpHtmlOleControl(HtmlOleControl control) throws Exception
    {
        write(msString.format("{{Control: {0}}}", control.getType()));
        write(msString.format("  {{Html: '{0}'}}", ellipseText(control.getHtml())));

        if (control.getType() == HtmlOleControlType.OPTION)
        {
            write(msString.format("  {{Checked: {0}}}", ((HtmlOptionOleControl)control).getChecked()));
        }
    }

    private static void dumpForms2OleControl(Forms2OleControl control) throws Exception
    {
        write(msString.format("{{Control: {0}}}", control.getType()));

        dumpPr(control.Pr);

        if (control instanceof FormControl)
            dumpFormControl((FormControl)control);
    }

    private static void dumpFormControl(FormControl formControl) throws Exception
    {
        for (Forms2OleControl childControl : formControl.getChildNodes())
        {
            IndentLevel++;
            dumpForms2OleControl(childControl);
            IndentLevel--;
        }
    }

    private static void dumpOleStream(String name, MemoryStorage data) throws Exception
    {
        switch (gStringSwitchMap.of(name))
        {
            case/*Ole2StreamBase.OLE_STREAM_NAME*/2:
            {
                OleStream oleStream = OleStream.read(data);
                write(msString.format("ObjectType: {0}", oleStream.getObjectType()));
                write(msString.format("Path: {0}", oleStream.getPath()));
                write(msString.format("ClsId: {0}", oleStream.getClsId()));
                write(msString.format("LocalUpdateTime: {0}", oleStream.getLocalUpdateTime()));
                break;
            }

            case/*Ole2StreamBase.OBJ_INFO_STREAM_NAME*/3:
            {
                ObjInfoStream objInfoStream = ObjInfoStream.read(data);
                write(msString.format("Flags1: {0}", objInfoStream.getFlags1()));
                write(msString.format("Flags2: {0}", objInfoStream.getFlags2()));
                break;
            }

            case/*Ole2StreamBase.COMP_OBJ_STREAM_NAME*/4:
            {
                CompObjStream compObjStream = CompObjStream.read(data);
                write(msString.format("ClsId: {0}", compObjStream.getClsid()));
                write(msString.format("ProgId: {0}", compObjStream.getProgId()));
                write(msString.format("UserType: {0}", compObjStream.getUserType()));
                break;
            }

            case /*Ole2StreamBase.ATTACH_DESC_STREAM_NAME*/5:
            {
                AttachDescStream attachDescStream = AttachDescStream.read(data);
                write(msString.format("FileName: {0}", attachDescStream.getFileName()));
                write(msString.format("Extension: {0}", attachDescStream.getExtension()));
                break;
            }

            case/*Ole2StreamBase.OLE_10_NATIVE_STREAM_NAME*/6:
            {
                OlePackage olePackager = new OlePackage();
                BinaryReader reader = new BinaryReader(data.getStreamSafe(Ole2StreamBase.OLE_10_NATIVE_STREAM_NAME));
                reader.getBaseStream().setPosition(0);

                if (StreamUtil.hasEnoughBytesToRead(reader, 4))
                {
                    int size = reader.readInt32();
                    olePackager.read(reader);
                    write(msString.format("FileName: {0}", olePackager.getFileName()));
                    write(msString.format("DisplayName: {0}", olePackager.getDisplayName()));
                    write(msString.format("TempFileName: {0}", olePackager.TempFileName));
                }
                break;
            }

            default:
                // No additional parsing is required for other streams.
                break;
        }
    }

    private static String ellipseText(String text)
    {
        text = text.replace("\n", "");
        text = text.replace("\r", "");
        final int MAX_LEN = 80;
        if (text.length() < MAX_LEN)
            return text;

        final int PART_LEN = MAX_LEN / 3;
        return msString.format("{0}...{1}", text.substring((0), (0) + (PART_LEN)), text.substring(text.length() - PART_LEN));
    }

    private static void extractGlobalTableAttrs(Table table)
    {
        for (Row row : (Iterable<Row>) table.getRows())
        {
            TablePr tablePr = row.getParentTable().getTablePr();
            TablePr rowPr = row.getTablePr();

            if (!row.getTablePr().hasFormatRevision() && (row.getTablePr().getWidthBefore().getValueRaw() == 0))
                row.getTablePr().remove(TableAttr.WIDTH_BEFORE);

            if (row.isFirstRow())
            {
                for (int key : X)
                    moveAttr(rowPr, tablePr, key);
            }
            else
            {
                rowPr.collapse(tablePr, TableAttr.SYS_TABLE_GRID);
            }
        }
    }

    private static int joinRunsWithSameFormatting(Document doc)
    {
        SuspendMappedCustomXmlUpdateDocument suspendMappedCustomXmlUpdateDocumentResource = new SuspendMappedCustomXmlUpdateDocument(doc);
        try /*JAVA: was using*/
        {
            NodeCollection paragraphs = doc.getChildNodes(NodeType.PARAGRAPH, true);
            StringBuilder sb = new StringBuilder(1024);
            int joinCount = 0;

            for (Paragraph para : (Iterable<Paragraph>) paragraphs)
                joinCount += joinRunsWithSameFormatting(para, sb);

            NodeCollection sdts = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
            for (StructuredDocumentTag sdt : (Iterable<StructuredDocumentTag>) sdts)
                if (sdt.getLevel() == MarkupLevel.INLINE)
                    joinCount += joinRunsWithSameFormatting(sdt, sb);

            return joinCount;
        }
        finally { if (suspendMappedCustomXmlUpdateDocumentResource != null) suspendMappedCustomXmlUpdateDocumentResource.dispose(); }
    }

    private static int joinRunsWithSameFormatting(CompositeNode p, StringBuilder sb)
    {
        Debug.assertTrue((sb != null) && (sb.length() == 0));

        int joinCount = 0;
        Run prevRun = null;

        for (Node curNode = p.getFirstChild(); curNode != null; curNode = curNode.getNextSibling())
        {
            if (curNode.getNodeType() == NodeType.RUN)
            {
                Run curRun = (Run)curNode;

                if (prevRun != null)
                {
                    // Found two adjacent runs. Let's compare their direct properties.
                    // During comparison some attributes should be ignored. See Run.KeysToIgnoreInComparisonOnJoin.
                    // Then collect texts in StringBuilder if properties are equal.
                    // Empty StringBuilder means that it is first join in the sequence.

                    // WORDSNET-4544 We should expand all property of runPrs before comparison runPrs.
                    RunPr expPrevRunPr = new RunPr();
                    // AM. This is workaround to get global defaults. I don't want to make defaults internal for a while.
                    expPrevRunPr.expandTo(expPrevRunPr, true);
                    prevRun.getExpandedRunPr(RunPrExpandFlags.NORMAL).expandTo(expPrevRunPr);

                    RunPr expCurRunPr = new RunPr();
                    expCurRunPr.expandTo(expCurRunPr, true);
                    curRun.getExpandedRunPr(RunPrExpandFlags.NORMAL).expandTo(expCurRunPr);

                    // Quick hack to join runs with edit revisions.
                    if (expPrevRunPr.contains(RevisionAttr.DELETE_REVISION) &&
                        expCurRunPr.contains(RevisionAttr.DELETE_REVISION))
                    {
                        expPrevRunPr.remove(RevisionAttr.DELETE_REVISION);
                        expCurRunPr.remove(RevisionAttr.DELETE_REVISION);
                    }

                    if (expPrevRunPr.contains(RevisionAttr.INSERT_REVISION) &&
                        expCurRunPr.contains(RevisionAttr.INSERT_REVISION))
                    {
                        expPrevRunPr.remove(RevisionAttr.INSERT_REVISION);
                        expCurRunPr.remove(RevisionAttr.INSERT_REVISION);
                    }

                    if (NoAttributes || NoRunAttributes || equals(expPrevRunPr, expCurRunPr))
                    {
                        if (sb.length() == 0)
                            msStringBuilder.append(sb, prevRun.getText());
                        msStringBuilder.append(sb, curRun.getText());

                        ++joinCount;
                        p.removeChild(prevRun);
                    }
                    else
                    {
                        flushJoinedRunsText(prevRun, sb);
                    }
                }

                prevRun = curRun;
            }
            else
            {
                // Flush if any collected and forget last candidate since the sequence is finished.
                flushJoinedRunsText(prevRun, sb);
                prevRun = null;
            }
        }

        // Reached the end of paragraph. Flush if any collected.
        flushJoinedRunsText(prevRun, sb);
        Debug.assertTrue(sb.length() == 0);

        return joinCount;
    }

    /// <summary>
    /// Flushes collected text into the given run and reset StringBuilder.
    /// </summary>
    private static void flushJoinedRunsText(Run run, StringBuilder sb)
    {
        if ((run != null) && (sb.length() != 0))
        {
            run.setText(sb.toString());
            sb.setLength(0);
        }
    }

    private static boolean equals(AttrCollection coll1, AttrCollection coll2)
    {
        int pos1 = 0;
        int pos2 = 0;
        int key1 = 0;
        int key2 = 0;
        boolean result;
        boolean comparing;

        do
        {
            RefInt referenceToPos1 = new RefInt(pos1);
            RefInt referenceToKey1 = new RefInt(key1);
            boolean found1 = findNonIgnoredKey(coll1, /*ref*/ referenceToPos1, /*ref*/ referenceToKey1);
            pos1 = referenceToPos1.get();
            key1 = referenceToKey1.get();
            RefInt referenceToPos2 = new RefInt(pos2);
            RefInt referenceToKey2 = new RefInt(key2);
            boolean found2 = findNonIgnoredKey(coll2, /*ref*/ referenceToPos2, /*ref*/ referenceToKey2);
            pos2 = referenceToPos2.get();
            key2 = referenceToKey2.get();
            comparing = found1 && found2;
            result = comparing ? ((key1 == key2) && coll1.getByIndex(pos1++).equals(coll2.getByIndex(pos2++))) : !(found1 || found2);
        }
        while (result && comparing);

        return result;
    }

    /// <summary>
    /// Finds first non-ignored key starting from pos in collection coll.
    /// If anything is found then returns true and sets pos to that key.
    /// </summary>
    private static boolean findNonIgnoredKey(AttrCollection coll, /*ref*/RefInt pos, /*ref*/RefInt key)
    {
        int collCount = coll.getCount();
        while (pos.get() < collCount)
        {
            key.set(coll.getKey(pos.get()));
            if (!isIgnoreAttr(key.get()))
                return true;

            pos.set(pos.get() + 1)/*++Indexer*/;
        }
        return false;
    }

    private static /*final*/ int[] X = new int[]
    {
        // Table level only
        TableAttr.ISTD, TableAttr.STYLE_OPTIONS, TableAttr.ALLOW_OVERLAP, TableAttr.STYLE_ROW_BAND_SIZE, TableAttr.STYLE_COL_BAND_SIZE, TableAttr.BIDI,
        TableAttr.FRAME_DISTANCE_FROM_LEFT,TableAttr.FRAME_DISTANCE_FROM_RIGHT,TableAttr.FRAME_DISTANCE_FROM_TOP, TableAttr.FRAME_DISTANCE_FROM_BOTTOM,
        TableAttr.RELATIVE_VERTICAL_POSITION, TableAttr.RELATIVE_HORIZONTAL_POSITION,
        TableAttr.FRAME_LEFT, TableAttr.FRAME_TOP,
        TableAttr.HORIZONTAL_ALIGNMENT, TableAttr.VERTICAL_ALIGNMENT,

        // Table level too but never occurred in DOC or RTF.
        TableAttr.TITLE,
        TableAttr.DESCRIPTION,

        TableAttr.BORDER_TOP, TableAttr.BORDER_LEFT, TableAttr.BORDER_RIGHT, TableAttr.BORDER_BOTTOM, TableAttr.BORDER_HORIZONTAL, TableAttr.BORDER_VERTICAL,
        TableAttr.PREFERRED_WIDTH, TableAttr.ALLOW_AUTO_FIT,
        TableAttr.TOP_PADDING, TableAttr.BOTTOM_PADDING, TableAttr.LEFT_PADDING, TableAttr.RIGHT_PADDING,
        TableAttr.LEFT_INDENT,
        TableAttr.ALIGNMENT,
        TableAttr.CELL_SPACING,
        TableAttr.SHADING,
    };

    private static void moveAttr(WordAttrCollection attr1, WordAttrCollection attr2, int key)
    {
        if (attr1.contains(key) && !attr2.contains(key))
        {
            attr2.setAttr(key, attr1.getDirectAttr(key));
            attr1.remove(key);
        }
    }

    private static void dumpCustomization(Document doc) throws Exception
    {
        if (doc.hasAllocatedCommands())
        {
            write("");
            write("[AllocatedCommands]");
            for (AllocatedCommand ac : doc.getAllocatedCommands())
                write(msString.format("  {0}{1}", ac.FciBasedOn,
                                    (ac.ArgValue.length > 0) ?
                                    (", " + ArrayUtil.dumpArray(ac.ArgValue).replace(" ", "")) : ""));
        }

        if (doc.hasKeyMaps())
        {
            write("");
            write("[KeyMap]");
            for (KeyMap keyMap : doc.getKeyMaps())
            {
                StringBuilder sb = new StringBuilder();
                msStringBuilder.appendFormat(sb, "{0}", keyMap.KeymapType);
                switch (keyMap.KeymapType)
                {
                    case KeyMapType.ALLOCATED_COMMAND:
                        msStringBuilder.appendFormat(sb, "{0}", keyMap.AllocatedCommandIndex);
                        break;
                    case KeyMapType.FIXED_COMMAND:
                        msStringBuilder.appendFormat(sb, "{0}, {1}", keyMap.FixedCommandIdentifier, keyMap.FixedCommandArgument);
                        break;
                    case KeyMapType.INSERT_CHARACTER:
                        msStringBuilder.appendFormat(sb, "{0}", keyMap.CharacterCode);
                        break;
                    case KeyMapType.LEGACY_MACRO:
                    case KeyMapType.MACRO:
                        msStringBuilder.appendFormat(sb, "{0}", keyMap.MacroName);
                        break;
                    case KeyMapType.MASK:
                    case KeyMapType.NONE:
                        break;
                    default:
                        throw new IllegalStateException(msString.format("Invalid KeyMapType value {0}.", keyMap.KeymapType));
                }

                write(msString.format("  {0}", sb));
            }
        }

        if (doc.hasAttachedToolbars())
        {
            write("");
            write("[AttachedToolbars]");
            write("  " + byteArrayToString(doc.getAttachedToolbars()));
        }
    }

    private static int getFirstListId(ListDef listDef, ListCollection lists)
    {
        for (int i = 0; i < lists.getCount(); i++)
        {
            if (msObject.referenceEquals(listDef, lists.get(i).getListDef()))
                return i;
        }

        return Integer.MAX_VALUE;
    }

    private static void dumpLists(Document doc) throws Exception
    {
        if (NoLists)
            return;

        SortedIntegerListGeneric<ListDef> sortedListDefs = new SortedIntegerListGeneric<ListDef>();

        SortedIntegerListGeneric<ListDef> orphanListDefs = new SortedIntegerListGeneric<ListDef>();

        for (ListDef listDef : doc.getLists().getListDefs())
        {
            int listId = getFirstListId(listDef, doc.getLists());

            if (listId == Integer.MAX_VALUE)
            {
                // Collect orphan list definitions.
                orphanListDefs.set(listDef.getListDefId(), listDef);
            }
            else
            {
                sortedListDefs.set(listId, listDef);
            }
        }

        for (int l = 0; l < sortedListDefs.getCount(); l++)
        {
            ListDef listDef = sortedListDefs.getByIndex(l);
            dumpListDef(listDef, false);
        }

        for (int l = 0; l < orphanListDefs.getCount(); l++)
        {
            ListDef listDef = orphanListDefs.getByIndex(l);
            dumpListDef(listDef, true);
        }

        for (List list : doc.getLists())
        {
            write("");
            write(msString.format("[List {0} (0x{1:x8})]", list.getListId(), list.getListDefId()));

            IndentLevel++;
            for (int i = 0; i < list.getOverrides().getCount(); i++)
            {
                ListLevelOverride lo = list.getOverrides().get(i);
                StringBuilder sb = new StringBuilder();
                if (lo.IsStartAt)
                    msStringBuilder.appendFormat(sb, "StartAt, ");
                if (lo.IsFormatting)
                    msStringBuilder.appendFormat(sb, "Formatting, ");
                msStringBuilder.appendFormat(sb, "{0}, 0x{1:x8}/0x{2:x8}", lo.getListLevel().getLevelNumber(), lo.StartAtRaw, lo.getStartAtReal());

                write(msString.format("[List Level Override ({0})]", sb));
                IndentLevel++;
                dumpListLevel(lo.getListLevel());
                IndentLevel--;
            }
            IndentLevel--;
        }

        for (int i = 0; i < doc.getLists().getPictureBulletCount(); i++)
        {
            write("");
            write(msString.format("[Picture Bullet: {0}]", i));
            Shape pictureBullet = doc.getLists().getPictureBullet(i);
            IndentLevel++;
            dumpNode(pictureBullet);
            IndentLevel--;
        }
    }

    private static void dumpListDef(ListDef listDef, boolean orphaned) throws Exception
    {
        write("");
        write(msString.format("[List Definition 0x{0:x8} ({6} 0x{1:x2}, {2}{4}, '{3}')]{5}",
            listDef.getListDefId(),
            listDef.getListStyleIstd(),
            listDef.getListType(),
            listDef.getName(),
            (listDef.isRestartAtEachSection() ? "(RestartAtEachSection)" : ""),
            orphaned ? "*" : "",
            listDef.isListStyleReference() ? "reference to" : "definition for"));
        IndentLevel++;
        for (int i = 0; i < listDef.getLevels().getCount(); i++)
        {
            dumpListLevel(listDef.getLevels().get(i));
        }
        IndentLevel--;
    }

    private static void dumpListLevel(ListLevel level) throws Exception
    {
        StringBuilder sb = new StringBuilder();
        if (level.getParaStyleIstd() != StyleIndex.NIL)
            msStringBuilder.appendFormat(sb, "linked style: 0x{0:x2}, ", level.getParaStyleIstd());
        msStringBuilder.appendFormat(sb, "{0}, ", level.getStartAt());
        msStringBuilder.appendFormat(sb, "{0}, ", level.getNumberStyle());
        msStringBuilder.appendFormat(sb, "'{0}', ", getPrintableText(level.getNumberFormat()));
        msStringBuilder.appendFormat(sb, "{0}, ", level.getAlignment());

        if (level.isLegal())
            msStringBuilder.append(sb, "Legal, ");
        if (level.getLegacyPrev())
            msStringBuilder.append(sb, "LegacyPrev, ");
        if (level.getLegacyPrevSpace())
            msStringBuilder.append(sb, "LegacyPrevSpace, ");
        if (level.getLegacy())
            msStringBuilder.append(sb, "Legacy, ");
        if (level.IsTentative)
            msStringBuilder.append(sb, "Tentative, ");

        msStringBuilder.appendFormat(sb, "{0}, ", level.getTrailingCharacter());
        msStringBuilder.appendFormat(sb, "{0}, ", level.getLegacySpace());
        msStringBuilder.appendFormat(sb, "{0}, ", level.getLegacyIndent());

        msStringBuilder.appendFormat(sb, "'{0}'", level.getCustomNumberStyle());

        if (level.hasPictureBullet())
            msStringBuilder.appendFormat(sb, ", PictureBullet: {0}", level.getPictureBulletId());

        write(msString.format("[Level {0} ({1})]", level.getLevelNumber(), sb));

        RunPr runPr = level.getRunPr();
        ParaPr paraPr = level.getParaPr();
        if ((runPr.getCount() > 0) || (paraPr.getCount() > 0))
        {
            dumpPr(runPr);
            dumpPr(paraPr);
        }
    }

    private static void dumpBuiltinDocumentProperties(Document doc) throws Exception
    {
        write("\n[Built-in Document Properties]");

        for (DocumentProperty docProperty : (Iterable<DocumentProperty>) doc.getBuiltInDocumentProperties())
            write(msString.format("  {{'{0}'({1}) = '{2}'{3}}}", docProperty.getName(), docProperty.getType(),
                docProperty.getValue().toString(),
                // Write printable version if different.
                !msString.equals(docProperty.getValue().toString(), getPrintableText(docProperty.getValueInternal().toString()))
                    ? msString.format(" ('{0}')", getPrintableText(docProperty.getValueInternal().toString()))
                    : ""
                ));
    }

    private static void dumpCustomDocumentProperties(Document doc) throws Exception
    {
        write("\n[Custom Document Properties]");

        for (DocumentProperty docProperty : (Iterable<DocumentProperty>) doc.getCustomDocumentProperties())
            write(msString.format("  {{'{0}'({1}) = '{2}'{3}{4}}}", docProperty.getName(), docProperty.getType(),
                docProperty.getValue().toString(),
                // Write printable version if different.
                !msString.equals(docProperty.getValue().toString(), getPrintableText(docProperty.getValueInternal().toString()))
                    ? msString.format(" ('{0}')", getPrintableText(docProperty.getValueInternal().toString()))
                    : "",
                docProperty.isLinkToContent()
                    ? msString.format(", linked to: '{0}'", docProperty.getLinkTarget())
                    : ""));
    }

    private static void dumpVariables(Document doc) throws Exception
    {
        write("\n[Variables]");

        for (Map.Entry<String, String> var : doc.getVariables())
            write(msString.format("  {{'{0}' = '{1}'{2}}}", var.getKey(), var.getValue(),
                // Write printable version if different.
                !msString.equals(var.getValue(), getPrintableText(var.getValue())) ? msString.format(" ('{0}')", getPrintableText(var.getValue())) : ""));
    }

    private static void dumpStyles(Document doc) throws Exception
    {
        if (NoStyles)
            return;

        write("\n[DefaultParaPr]");
        dumpPr(doc.getStyles().getDefaultParaPr(), new ParaPr());

        write("\n[DefaultRunPr]");
        dumpPr(doc.getStyles().getDefaultRunPr(), new RunPr());

        if (!NoLatentStyles)
        {
            write("\n[Latent styles]");
            final String LS_FMT = "  {{{0}}}";
            LatentStyles ls = doc.getStyles().getLatentStyles();

            if (ls.getDefaultLockedState())
                write(LS_FMT, "DefaultLockedState");
            if (ls.getDefaultQuickFormat())
                write(LS_FMT, "DefaultQuickFormat");
            if (ls.getDefaultSemiHidden())
                write(LS_FMT, "DefaultSemiHidden");
            if (ls.getDefaultUnhideWhenUsed())
                write(LS_FMT, "DefaultUnhideWhenUsed");
            write(LS_FMT, msString.format("DefaultUIPriority: 0x{0:x2}", ls.getDefaultUIPriority()));

            IndentLevel++;
            for (int i = 0; i < ls.getCount(); i++)
            {
                LatentStyle latentStyle = ls.get(i);

                StringBuilder sb = new StringBuilder();

                if (latentStyle.getLocked())
                    msStringBuilder.append(sb, " Locked");
                if (latentStyle.getQuickStyle())
                    msStringBuilder.append(sb, " QuickFormat");
                if (latentStyle.getSemiHidden())
                    msStringBuilder.append(sb, " SemiHidden");
                if (latentStyle.getUnhideWhenUsed())
                    msStringBuilder.append(sb, " UnhideWhenUsed");

                write(msString.format("{0:x2}, {1}, Priority: 0x{2:x2}{3}",
                    (int)latentStyle.getStyleIdentifier(), latentStyle.getStyleIdentifier(), latentStyle.getUIPriority(), (sb.length() > 0 ? msString.format(" ({0}) ", msString.trim(sb.toString())) : "")));
            }
            IndentLevel--;
        }

        // Write styles sorted by istd.
        ArrayList<Integer> istds = new ArrayList<Integer>();
        for (Style style : doc.getStyles())
            msArrayList.add(istds, style.getIstd());
        Collections.sort(istds);

        for (int i = 0; i < istds.size(); i++)
        {
            Style style = doc.getStyles().getByIstd(istds.get(i), false);

            StringBuilder sb = new StringBuilder();

            if (style.getLocked())
                msStringBuilder.append(sb, " Locked");
            if (style.isQuickStyle())
                msStringBuilder.append(sb, " QuickFormat");
            if (style.getSemiHidden())
                msStringBuilder.append(sb, " SemiHidden");
            if (style.getUnhideWhenUsed())
                msStringBuilder.append(sb, " UnhideWhenUsed");

            String styleOptions = msString.format("0x{0:x2}{1}", style.getPriority(),
                sb.length() > 0 ? msString.format(" ({0})", msString.trim(sb.toString())) : "");


            write(msString.format("\n[0x{0:x2}({1}), {2}: '{3}'{4}{5}{6}, {7}]",
                                style.getIstd(),
                                style.getStyleIdentifier(),
                                style.getType(),
                                getPrintableText(doc.getStyles().getAliases(style, true)),
                                style.getBasedOnIstd() != StyleIndex.NIL
                                    ? msString.format(", based on '{0}'", style.getBaseStyle().getName()) : "",
                                style.getNextIstd() != StyleIndex.NIL
                                    ? msString.format(", followed by '{0}'", style.getNextStyle().getName()) : "",
                                style.getLinkedIstd() != StyleIndex.NIL
                                    ? msString.format(", linked with '{0}'", style.getLinkedStyle().getName()) : "",
                                styleOptions));

            switch (style.getType())
            {
                case StyleType.CHARACTER:
                    dumpPr(style.getRunPr(), style);
                    break;

                case StyleType.PARAGRAPH:
                    dumpPr(style.getParaPr(), style);

                    if (ShowExpanded)
                    {
                        write("  >>");
                        dumpPr(style.getExpandedParaPr(ParaPrExpandFlags.LAYOUT));
                    }

                    dumpPr(style.getRunPr(), style);
                    break;

                case StyleType.LIST:
                    dumpPr(style.getParaPr());
                    dumpPr(style.getRunPr());
                    break;

                case StyleType.TABLE:
                {
                    TableStyle tableStyle = (TableStyle)style;
                    dumpPr(tableStyle.getTablePr());
                    dumpPr(tableStyle.getRowPr());
                    dumpPr(tableStyle.getCellPr());
                    dumpPr(tableStyle.getParaPr());
                    dumpPr(tableStyle.getRunPr());
                    IndentLevel++;

                    SortedStringListGeneric<ConditionalStyle> sortedList =
                        new SortedStringListGeneric<ConditionalStyle>();
                    for (ConditionalStyle conditionalStyle : tableStyle.getConditionalStyles().getDefinedStyles())
                        sortedList.add(TableStyleOverrideType.toString(conditionalStyle.getOverrideType()), conditionalStyle);

                    for (int k = 0; k < sortedList.getCount(); k++)
                    {
                        ConditionalStyle conditionalStyle = sortedList.getByIndex(k);

                        write(msString.format("[{0}]", conditionalStyle.getOverrideType()));
                        dumpPr(conditionalStyle.getTablePr());
                        dumpPr(conditionalStyle.getRowPr());
                        dumpPr(conditionalStyle.getCellPr());
                        dumpPr(conditionalStyle.getParaPr());
                        dumpPr(conditionalStyle.getRunPr());
                    }
                    IndentLevel--;
                    break;
                }

                default:
                    throw new IllegalStateException(msString.format("Invalid StyleType value {0}.", style.getType()));
            }
        }
    }

    private static String horizontalAlignmentToString(/*HorizontalAlignment*/int value)
    {
        String text = HorizontalAlignment.toString(value);
        if (value == HorizontalAlignment.DEFAULT)
            text = "None";

        return text;
    }

    private static String relativeHorizontalPositionToString(/*RelativeHorizontalPosition*/int value)
    {
        String text = RelativeHorizontalPosition.toString(value);
        if (value == RelativeHorizontalPosition.DEFAULT)
            text = "Column";

        return text;
    }

    private static String verticalAlignmentToString(/*VerticalAlignment*/int value)
    {
        String text = VerticalAlignment.toString(value);
        if (value == VerticalAlignment.DEFAULT)
            text = "None";

        return text;
    }

    private static String relativeVerticalPositionToString(/*RelativeVerticalPosition*/int value)
    {
        String text = RelativeVerticalPosition.toString(value);
        if (value == RelativeVerticalPosition.TABLE_DEFAULT)
            text = "Margin";
        if (value == RelativeVerticalPosition.TEXT_FRAME_DEFAULT)
            text = "Paragraph";

        return text;
    }


    private static String attrBoolExToString(AttrBoolEx boolEx)
    {
        switch (boolEx.getValue())
        {
            case 0:
                return "False";
            case 1:
                return "True";
            case 128:
                return "Same";
            case 129:
                return "Toggle";
            default:
                return "?";
        }
    }

    private static String byteArrayToString(byte[] bytes) throws Exception
    {
        if (bytes == null)
            return "(null)";

        if (bytes.length == 0)
            return "(empty)";

        if (ArrayUtil.compareBytes(ImageUtil.getNoImageBytes(), bytes, bytes.length))
            return "(NoImage)";

        return msString.format("{0} bytes, MD5: {1}", bytes.length, ArrayUtil.dumpArray(HashUtil.computeHash(DigestAlgorithm.MD_5, bytes)).replace(" ", ""));
    }

    private static String rawByteArrayToString(byte[] bytes)
    {
        if (bytes == null)
            return "(null)";

        if (bytes.length == 0)
            return "(empty)";

        return msString.format("{0} bytes, {1}", bytes.length, ArrayUtil.dumpArray(bytes).replace(" ", ""));
    }

    private static String int32ArrayToString(int[] ints)
    {
        if (ints == null)
            return "(null)";

        if (ints.length == 0)
            return "(empty)";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ints.length; i++)
            msStringBuilder.appendFormat(sb, "{0}, ", i);

        return removeTrailingComma(sb);
    }

    static String borderToString(Border border)
    {
        if (border.equals(Border.EMPTY))
            return "(empty)";

        if (border.isNil())
            return "(nil)";

        StringBuilder sb = new StringBuilder();

        msStringBuilder.appendFormat(sb, "{0:x8} {1} {2} {3}{4}{5}",
            border.getColor().getRGB(),
            border.getLineStyle(),
            border.getLineWidth(),
            border.getDistanceFromText(),
            border.getShadow() ? " Shadow" : "",
            border.isInherited() ? " Inherited" : "");

        if (StringUtil.hasChars(border.getThemeColorInternal()))
            msStringBuilder.appendFormat(sb, ", ThemeColor: {0}", border.getThemeColorInternal());
        if (StringUtil.hasChars(border.getThemeShade()))
            msStringBuilder.appendFormat(sb, ", ThemeShade: {0}", border.getThemeShade());
        if (StringUtil.hasChars(border.getThemeTint()))
            msStringBuilder.appendFormat(sb, ", ThemeTint: {0}", border.getThemeTint());

        return msString.format("({0})", sb);
    }

    private static String pathPointArrayToString(PathPoint[] pathPointArray)
    {
        if (pathPointArray == null)
            return "(null)";

        if (pathPointArray.length == 0)
            return "(empty)";

        StringBuilder sb = new StringBuilder();
        for (PathPoint pathPoint : pathPointArray)
            msStringBuilder.appendFormat(sb, "{0},", pathPointToString(pathPoint));

        return removeTrailingComma(sb);
    }

    private static String pathInfoArrayToString(PathInfo[] pathInfoArray)
    {
        if (pathInfoArray == null)
            return "(null)";

        if (pathInfoArray.length == 0)
            return "(empty)";

        StringBuilder sb = new StringBuilder();
        for (PathInfo pathInfo : pathInfoArray)
            msStringBuilder.appendFormat(sb, "{0},", pathInfoToString(pathInfo));

        return removeTrailingComma(sb);
    }

    private static String pathPointToString(PathPoint pathPoint)
    {
        return msString.format("{{{0}:{1}}}", pathPoint.getX(), pathPoint.getY());
    }

    private static String pathInfoToString(PathInfo pathInfo)
    {
        return msString.format("{{{0}:{1}}}", pathInfo.getPathType(), pathInfo.getSegmentCount());
    }

    private static String removeTrailingComma(String text)
    {
        return msString.trimEnd(text, new char[] { ',', ' ' });
    }

    private static String removeTrailingComma(StringBuilder sb)
    {
        return removeTrailingComma(sb.toString());
    }

    static String shadingToString(Shading shading)
    {
        StringBuilder sb = new StringBuilder();

        if (shading != null)
        {
            msStringBuilder.appendFormat(sb, "Back: {0:x8}, Fore: {1:x8}, {2}", shading.getBackgroundPatternColorInternal().toArgb(),
                            shading.getForegroundPatternColorInternal().toArgb(), shading.getTexture());
            if (StringUtil.hasChars(shading.getThemeColor()))
                msStringBuilder.appendFormat(sb, ", ThemeColor: {0}", shading.getThemeColor());
            if (StringUtil.hasChars(shading.getThemeFill()))
                msStringBuilder.appendFormat(sb, ", ThemeFill: {0}", shading.getThemeFill());
            if (StringUtil.hasChars(shading.getThemeFillShade()))
                msStringBuilder.appendFormat(sb, ", ThemeFillShade: {0}", shading.getThemeFillShade());
            if (StringUtil.hasChars(shading.getThemeFillTint()))
                msStringBuilder.appendFormat(sb, ", ThemeFillTint: {0}", shading.getThemeFillTint());
            if (StringUtil.hasChars(shading.getThemeTint()))
                msStringBuilder.appendFormat(sb, ", ThemeTint: {0}", shading.getThemeTint());
        }

        return msString.format("({0})", sb);
    }

    private static String tabStopCollectionToString(TabStopCollection tabs)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tabs.getCount(); i++)
        {
            TabStop tab = tabs.get(i);
            msStringBuilder.appendFormat(sb, "{0}{3}:{1}{2} ",
                tab.getAlignment(),
                tab.getPosition(),
                tab.getToleranceTwips() != 0 ? msString.format("({0})", tab.getToleranceTwips()) : "",
                tab.isLegacyTab() ? "(Legacy)" : "");
        }
        return msString.trim(sb.toString());
    }

    private static String embeddedObjectToString(IEmbeddedObject embeddedObject)
    {
        return msString.format("(ID: {0})", embeddedObject.getId());
    }

    private static String editRevisionToString(EditRevision revision)
    {
        if (NoRevisionDetails)
            return "(-)";

        return msString.format("({0}, {1})", revision.getAuthor(), revision.getDateTime());
    }

    private static String moveRevisionToString(MoveRevision revision)
    {
        if (NoRevisionDetails)
            return "(-)";

        return msString.format("({0}, {1})", revision.getAuthor(), revision.getDateTime());
    }

    private static String numberRevisionToString(ParagraphNumberRevision revision)
    {
        StringBuilder sb = new StringBuilder();

        if (revision.isActive())
            msStringBuilder.append(sb, "Active, ");
        if (revision.isInsertion())
            msStringBuilder.append(sb, "Insertion, ");
        if (revision.isNumbering())
            msStringBuilder.append(sb, "Numbering, ");
        if (revision.getWasNumbered())
            msStringBuilder.append(sb, "WasNumbered, ");

        String flags = msString.trim(sb.toString(), G_LIST_SEPARATOR_CHARS);

        return msString.format("{{{0}, NumberFormat: '{1}', NumberLocations: '{2}'}} ({3}, {4})",
            flags, revision.getNumberFormat(), revision.getNumberLocations(), revision.getAuthor(), revision.getDateTime());
    }

    private static String arrayListToString(CellPrCollection cellPrCollection)
    {
        StringBuilder sb = new StringBuilder();
        for (AttrCollection o : cellPrCollection)
            msStringBuilder.appendFormat(sb, " {0} ", o);

        return msString.format("({0})", msString.trim(sb.toString()));
    }

    private static String int32ListToString(IntList list)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.getCount(); i++)
            msStringBuilder.appendFormat(sb, " {0} ", list.get(i));

        return msString.format("({0})", msString.trim(sb.toString()));
    }

    private static boolean isRunAttribute(int key)
    {
        return (0 <= key && key < 1000 && !isRevisionKey(key));
    }

    private static boolean isParaAttribute(int key)
    {
        return (1000 <= key && key < 2000);
    }

    private static boolean isTableAttribute(int key)
    {
        return (4000 <= key && key < 5200);
    }

    private static boolean isFormFieldAttribute(int key)
    {
        return (21000 <= key && key <= 21170);
    }

    private static boolean isMathAttribute(int key)
    {
        return (15000 <= key && key <= 16000);
    }

    private static boolean isCellAttribute(int key)
    {
        return (3000 <= key && key < 4000);
    }

    private static boolean isForms2Attribute(int key)
    {
        return (31000 <= key && key < 32000);
    }

    private static String getFmt(int key)
    {
        if (isRevisionKey(key))
            return "  {{Revision.{0}: {1}}}";
        if (isRunAttribute(key))
            return "  {{Run.{0}: {1}}}";
        if (isParaAttribute(key))
            return "  {{Para.{0}: {1}}}";
        if (2000 <= key && key < 3000)
            return "  {{Sect.{0}: {1}}}";
        if (isCellAttribute(key))
            return "  {{Cell.{0}: {1}}}";
        if (isTableAttribute(key))
            return "  {{Table.{0}: {1}}}";
        if (isFormFieldAttribute(key))
            return "  {{FormField.{0}: {1}}}";
        if (isMathAttribute(key))
            return "  {{Math.{0}: {1}}}";
        if (isForms2Attribute(key))
            return "  {{Forms2.{0}: {1}}}";

        return "  {{?.{0}: {1}}}";
    }

    private static boolean isRevisionKey(int key)
    {
        return
            (key == RevisionAttr.INSERT_REVISION) ||
            (key == RevisionAttr.DELETE_REVISION) ||
            (key == RevisionAttr.NUMBER_REVISION) ||
            (key == RevisionAttr.MOVE_FROM_REVISION) ||
            (key == RevisionAttr.MOVE_TO_REVISION) ||
            (key == RevisionAttr.FORMAT_REVISION);
    }

    private static String formatAttr(int key, Object value)
    {
        String fmt = getFmt(key);

        switch (key)
        {
            case FormFieldAttr.CALC_ON_EXIT:
                return msString.format(fmt, "CalcOnExit", value);
            case FormFieldAttr.CHECK_BOX_CHECKED:
                return msString.format(fmt, "CheckBoxChecked", value);
            case FormFieldAttr.CHECK_BOX_DEFAULT:
                return msString.format(fmt, "CheckBoxDefault", value);
            case FormFieldAttr.CHECK_BOX_SIZE_AUTO:
                return msString.format(fmt, "CheckBoxSizeAuto", value);
            case FormFieldAttr.CHECK_BOX_SIZE_HALF_POINTS:
                return msString.format(fmt, "CheckBoxSizeHalfPoints", value);
            case FormFieldAttr.DROP_DOWN_DEFAULT:
                return msString.format(fmt, "DropDownDefault", value);
            case FormFieldAttr.DROP_DOWN_ITEMS:
                return msString.format(fmt, "DropDownItems", value);
            case FormFieldAttr.DROP_DOWN_RESULT:
                return msString.format(fmt, "DropDownResult", value);
            case FormFieldAttr.ENABLED:
                return msString.format(fmt, "Enabled", value);
            case FormFieldAttr.ENTRY_MACRO:
                return msString.format(fmt, "EntryMacro", value);
            case FormFieldAttr.EXIT_MACRO:
                return msString.format(fmt, "ExitMacro", value);
            case FormFieldAttr.HELP_TEXT:
                return msString.format(fmt, "HelpText", value);
            case FormFieldAttr.NAME:
                return msString.format(fmt, "Name", value);
            case FormFieldAttr.OWN_HELP_TEXT:
                return msString.format(fmt, "OwnHelpText", value);
            case FormFieldAttr.OWN_STATUS_TEXT:
                return msString.format(fmt, "OwnStatusText", value);
            case FormFieldAttr.STATUS_TEXT:
                return msString.format(fmt, "StatusText", value);
            case FormFieldAttr.TEXT_INPUT_DEFAULT:
                return msString.format(fmt, "TextInputDefault", value);
            case FormFieldAttr.TEXT_INPUT_FORMAT:
                return msString.format(fmt, "TextInputFormat", value);
            case FormFieldAttr.TEXT_INPUT_MAX_LENGTH:
                return msString.format(fmt, "TextInputMaxLength", value);
            case FormFieldAttr.TEXT_INPUT_TYPE:
                return msString.format(fmt, "TextInputType", value);
            //////////////////////
            case TableAttr.SYS_TABLE_GRID_FOR_NEW_ALGORITHM:
                return msString.format(fmt, "_TableGridForNewAlgorithm", int32ListToString(((TableGridColumnsAttr)value).GridColumns));
            case TableAttr.SYS_CELLS:
                return msString.format(fmt, "_Cells", arrayListToString((CellPrCollection)value));
            case TableAttr.SYS_TABLE_GRID:
                return msString.format(fmt, "_TableGrid", int32ListToString((IntList)value));
            case TableAttr.SYS_GRID_BEFORE:
                return msString.format(fmt, "_GridBefore", value);
            case TableAttr.SYS_LEFT_INDENT_97:
                return msString.format(fmt, "_LeftIndent97", value);
            case TableAttr.SYS_GRID_AFTER:
                return msString.format(fmt, "_GridAfter", value);
            case TableAttr.SYS_ALIGNMENT_97:
                return msString.format(fmt, "_Alignment97", value);
            case TableAttr.SYS_BIDI_TABLE_97:
                return msString.format(fmt, "_BidiTable97", value);
            case TableAttr.SYS_DXA_LEFT:
                return msString.format(fmt, "_DxaLeft", value);
            case TableAttr.SYS_DXA_GAP_HALF:
                return msString.format(fmt, "_DxaGapHalf", value);
            //////////////////////
            case RevisionAttr.INSERT_REVISION:
            case RevisionAttr.DELETE_REVISION:
                return msString.format(fmt, ((EditRevision)value).getType(), editRevisionToString((EditRevision)value));
            case RevisionAttr.MOVE_FROM_REVISION:
                return msString.format(fmt, ((MoveRevision)value).getType(), moveRevisionToString((MoveRevision)value));
            case RevisionAttr.MOVE_TO_REVISION:
                return msString.format(fmt, ((MoveRevision)value).getType(), moveRevisionToString((MoveRevision)value));
            case RevisionAttr.NUMBER_REVISION:
                return msString.format(fmt, "NumberRevision", numberRevisionToString((ParagraphNumberRevision)value));
            //////////////////////
            case MathAttr.ACCENT_CHARACTER:
                return msString.format(fmt, "AccentCharacter", value);
            case MathAttr.BASE_JUSTIFICATION:
                return msString.format(fmt, "BaseJustification", value);
            case MathAttr.ROW_SPACING:
                return msString.format(fmt, "RowSpacing", value);
            case MathAttr.ROW_SPACING_RULE:
                return msString.format(fmt, "RowSpacingRule", value);
            case MathAttr.JUSTIFICATION:
                return msString.format(fmt, "Justification", value);
            //////////////////////

            case FontAttr.UNDERLINE_THEME_COLOR:
                return msString.format(fmt, "UnderlineThemeColor", value);
            case FontAttr.UNDERLINE_THEME_SHADE:
                return msString.format(fmt, "UnderlineThemeShade", value);
            case FontAttr.UNDERLINE_THEME_TINT:
                return msString.format(fmt, "UnderlineThemeTint", value);
            case FontAttr.MATH_STYLE:
                return msString.format(fmt, "MathStyle", value);
            case FontAttr.RUBY:
                return msString.format(fmt, "Ruby", rubyToString((Ruby)value));
            case FontAttr.FAR_EAST_LAYOUT:
                return msString.format(fmt, "FarEastLayout", farEastLayoutToString((FarEastLayout)value));
            case FontAttr.SYS_SYMBOL:
                return msString.format(fmt, "_Symbol", value);
            case FontAttr.PICTURE_BULLET_ID:
                return msString.format(fmt, "PictureBulletId", value);
            case FontAttr.PICTURE_BULLET_FLAGS:
                return msString.format(fmt, "PictureBulletFlags", value);
            case FontAttr.BORDER:
                return msString.format(fmt, "Border", borderToString((Border)value));
            case FontAttr.THEME_TINT:
                return msString.format(fmt, "ThemeTint", value);
            case FontAttr.TEXT_EFFECT:
                return msString.format(fmt, "TextEffect", value);
            case FontAttr.OUTLINE:
                return msString.format(fmt, "Outline", value);
            case FontAttr.EMBOSS:
                return msString.format(fmt, "Emboss", value);
            case FontAttr.ENGRAVE:
                return msString.format(fmt, "Engrave", value);
            case FontAttr.EMPHASIS_MARK:
                return msString.format(fmt, "EmphasisMark", value);
            case FontAttr.EFFECT_GLOW:
                return msString.format(fmt, "EffectGlow", dmlShapeEffectToString(value));
            case FontAttr.EFFECT_SHADOW:
                return msString.format(fmt, "EffectShadow", dmlShapeEffectToString(value));
            case FontAttr.EFFECT_REFLECTION:
                return msString.format(fmt, "EffectReflection", dmlShapeEffectToString(value));
            case FontAttr.EFFECT_OUTLINE:
                return msString.format(fmt, "EffectOutline", dmlShapeEffectToString(value));
            case FontAttr.EFFECT_FILL:
                return msString.format(fmt, "EffectFill", dmlShapeEffectToString(value));
            case FontAttr.EFFECT_SCENE_3_D:
                return msString.format(fmt, "EffectScene3D", dmlShapeEffectToString(value));
            case FontAttr.EFFECT_PROPS_3_D:
                return msString.format(fmt, "EffectProps3D", dmlShapeEffectToString(value));
            case FontAttr.OPEN_TYPE_STYLISTIC_SETS:
                return msString.format(fmt, "OpenTypeStylisticSets", value);
            case FontAttr.OPEN_TYPE_LIGATURE:
                return msString.format(fmt, "OpenTypeLigature", value);
            case FontAttr.OPEN_TYPE_NUM_FORM:
                return msString.format(fmt, "OpenTypeNumForm", value);
            case FontAttr.OPEN_TYPE_NUM_SPACING:
                return msString.format(fmt, "OpenTypeNumSpacing", value);
            case FontAttr.OPEN_TYPE_CONTEXTUAL_ALTERNATES:
                return msString.format(fmt, "OpenTypeContextualAlternates", value);
            case FontAttr.ALTERNATE_CONTENT:
                return msString.format(fmt, "AlternateContent", value);
            case FontAttr.ISTD:
                return msString.format(fmt, "Istd", msString.format("0x{0:x2}", value));
            case FontAttr.STRIKE_THROUGH:
                return msString.format(fmt, "StrikeThrough", value);
            case FontAttr.DOUBLE_STRIKE_THROUGH:
                return msString.format(fmt, "DoubleStrikeThrough", value);
            case FontAttr.HYPHEN_RULE:
                return msString.format(fmt, "HyphenRule", value);
            case FontAttr.HYPHEN_CHAR:
                return msString.format(fmt, "HyphenChar", value);
            case FontAttr.SPACING:
                return msString.format(fmt, "Spacing", value);
            case FontAttr.SPECIAL_HIDDEN:
                return msString.format(fmt, "SpecialHidden", attrBoolExToString((AttrBoolEx)value));
            case FontAttr.BOLD:
                return msString.format(fmt, "Bold", attrBoolExToString((AttrBoolEx)value));
            case FontAttr.ITALIC:
                return msString.format(fmt, "Italic", attrBoolExToString((AttrBoolEx)value));
            case FontAttr.ITALIC_BI:
                return msString.format(fmt, "ItalicBi", attrBoolExToString((AttrBoolEx)value));
            case FontAttr.SMALL_CAPS:
                return msString.format(fmt, "SmallCaps", attrBoolExToString((AttrBoolEx)value));
            case FontAttr.BOLD_BI:
                return msString.format(fmt, "BoldBi", attrBoolExToString((AttrBoolEx)value));
            case FontAttr.NAME_ASCII:
                return msString.format(fmt, "NameAscii", value);
            case FontAttr.NAME_FAR_EAST:
                return msString.format(fmt, "NameFarEast", value);
            case FontAttr.NAME_OTHER:
                return msString.format(fmt, "NameOther", value);
            case FontAttr.NAME_BI:
                return msString.format(fmt, "NameBi", value);
            case FontAttr.CHARACTER_CATEGORY_HINT:
                return msString.format(fmt, "CharacterCategoryHint", (/*CharacterCategory*/Integer)value);
            case FontAttr.BIDI:
                return msString.format(fmt, "BiDi", attrBoolExToString((AttrBoolEx)value));
            case FontAttr.COMPLEX_SCRIPT:
                return msString.format(fmt, "ComplexScript", attrBoolExToString((AttrBoolEx)value));
            case FontAttr.RSID_R:
                return msString.format(fmt, "RsidR", msString.format("0x{0:x8}", value));
            case FontAttr.RSID_R_PR:
                return msString.format(fmt, "RsidRPr", msString.format("0x{0:x8}", value));
            case FontAttr.LOCALE_ID_BI:
                return msString.format(fmt, "LocaleIdBi", (/*Language*/Integer)value);
            case FontAttr.LOCALE_ID:
                return msString.format(fmt, "LocaleId", (/*Language*/Integer)value);
            case FontAttr.LOCALE_ID_FAR_EAST:
                return msString.format(fmt, "LocaleIdFarEast", (/*Language*/Integer)value);
            case FontAttr.SIZE:
                return msString.format(fmt, "Size", value);
            case FontAttr.SIZE_BI:
                return msString.format(fmt, "SizeBi", value);
            case FontAttr.THEME_SHADE:
                return msString.format(fmt, "ThemeShade", value);
            case FontAttr.THEME_COLOR:
                return msString.format(fmt, "ThemeColor", value);
            case FontAttr.NO_PROOFING:
                return msString.format(fmt, "NoProofing", attrBoolExToString((AttrBoolEx)value));
            case FontAttr.SHADOW:
                return msString.format(fmt, "Shadow", attrBoolExToString((AttrBoolEx)value));
            case FontAttr.ALL_CAPS:
                return msString.format(fmt, "AllCaps", attrBoolExToString((AttrBoolEx)value));
            case FontAttr.COLOR:
                return msString.format(fmt, "Color", value);
            case FontAttr.UNDERLINE:
                return msString.format(fmt, "Underline", value);
            case FontAttr.KERNING:
                return msString.format(fmt, "Kerning", value);
            case FontAttr.VERTICAL_ALIGNMENT:
                return msString.format(fmt, "VerticalAlignment", value);
            case FontAttr.POSITION:
                return msString.format(fmt, "Position", value);
            case FontAttr.SNAP_TO_GRID:
                return msString.format(fmt, "SnapToGrid", attrBoolExToString((AttrBoolEx)value));
            case FontAttr.HIDDEN:
                return msString.format(fmt, "Hidden", attrBoolExToString((AttrBoolEx)value));
            case FontAttr.WEB_HIDDEN:
                return msString.format(fmt, "WebHidden", attrBoolExToString((AttrBoolEx)value));
            case FontAttr.UNDERLINE_COLOR:
                return msString.format(fmt, "UnderlineColor", value);
            case FontAttr.HIGHLIGHT_COLOR:
                return msString.format(fmt, "HighlightColor", value);
            case FontAttr.SHADING:
                return msString.format(fmt, "Shading", shadingToString((Shading)value));
            case FontAttr.SCALING:
                return msString.format(fmt, "Scaling", value);
            case FontAttr.FIT_TEXT:
                return msString.format(fmt, "FitText", value);
            case FontAttr.MATH_IS_NORMAL_TEXT:
                return msString.format(fmt, "MathIsNormalText", value);
            //////////////////////
            case ParaAttr.MIRROR_INDENTS:
                return msString.format(fmt, "MirrorIndents", value);
            case ParaAttr.TOP_LINE_PUNCTUATION:
                return msString.format(fmt, "TopLinePunctuation", value);
            case ParaAttr.SUPPRESS_LINE_NUMBERS:
                return msString.format(fmt, "SuppressLineNumbers", value);
            case ParaAttr.HTML_MARGIN_LEFT:
                return msString.format(fmt, "HtmlMarginLeft", value);
            case ParaAttr.HTML_MARGIN_RIGHT:
                return msString.format(fmt, "HtmlMarginRight", value);
            case ParaAttr.HTML_MARGIN_TOP:
                return msString.format(fmt, "HtmlMarginTop", value);
            case ParaAttr.HTML_MARGIN_BOTTOM:
                return msString.format(fmt, "HtmlMarginBottom", value);
            case ParaAttr.TEXTBOX_TIGHT_WRAP:
                return msString.format(fmt, "TextTightWrap", value);
            case ParaAttr.FRAME_SUPPRESS_OVERLAP:
                return msString.format(fmt, "FrameSuppressOverlap", value);
            case ParaAttr.DROP_CAP_LINES_TO_DROP:
                return msString.format(fmt, "DropCapLinesToDrop", value);
            case ParaAttr.DROP_CAP_POSITION:
                return msString.format(fmt, "DropCapPosition", value);
            case ParaAttr.FRAME_HEIGHT:
                return msString.format(fmt, "FrameHeight", heightToString((Height)value));
            case ParaAttr.SYS_ANLD:
                return msString.format(fmt, "_Anld", "FOSS");
            case ParaAttr.SYS_LVL_ANM:
                return msString.format(fmt, "_LvlAnm", (/*int*/Integer)value);
            case ParaAttr.RSID_P:
                return msString.format(fmt, "RsidP", msString.format("0x{0:x8}", value));
            case ParaAttr.SHADING:
                return msString.format(fmt, "Shading", shadingToString((Shading)value));
            case ParaAttr.FRAME_VERTICAL_DISTANCE_FROM_TEXT:
                return msString.format(fmt, "FrameVerticalDistanceFromText", value);
            case ParaAttr.FRAME_HORIZONTAL_DISTANCE_FROM_TEXT:
                return msString.format(fmt, "FrameHorizontalDistanceFromText", value);
            case ParaAttr.KEEP_WITH_NEXT:
                return msString.format(fmt, "KeepWithNext", value);
            case ParaAttr.NO_SPACE_BETWEEN_SAME_STYLE:
                return msString.format(fmt, "NoSpaceBetweenSameStyle", value);
            case ParaAttr.SPACE_BEFORE:
                return msString.format(fmt, "SpaceBefore", (/*int*/Integer)value);
            case ParaAttr.SPACE_AFTER:
                return msString.format(fmt, "SpaceAfter", (/*int*/Integer)value);
            case ParaAttr.ALIGNMENT:
                return msString.format(fmt, "Alignment", value);
            case ParaAttr.SYS_LEFT_INDENT_97:
                return msString.format(fmt, "_LeftIndent97", value);
            case ParaAttr.SYS_FIRST_LINE_INDENT_97:
                return msString.format(fmt, "_FirstLineIndent97", value);
            case ParaAttr.SYS_ALIGNMENT_97:
                return msString.format(fmt, "_Alignment97", value);
            case ParaAttr.SYS_RIGHT_INDENT_97:
                return msString.format(fmt, "_RightIndent97", value);
            case ParaAttr.TAB_STOPS:
                return msString.format(fmt, "TabStops", tabStopCollectionToString((TabStopCollection)value));
            case ParaAttr.ISTD:
                return msString.format(fmt, "Istd", msString.format("0x{0:x2}", value));
            case ParaAttr.AUTO_ADJUST_RIGHT_INDENT:
                return msString.format(fmt, "AutoAdjustRightIndent", (/*boolean*/Boolean)value);
            case ParaAttr.WIDOW_CONTROL:
                return msString.format(fmt, "WidowControl", (/*boolean*/Boolean)value);
            case ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_ALPHA:
                return msString.format(fmt, "AddSpaceBetweenFarEastAndAlpha", (/*boolean*/Boolean)value);
            case ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_DIGIT:
                return msString.format(fmt, "AddSpaceBetweenFarEastAndDigit", (/*boolean*/Boolean)value);
            case ParaAttr.LIST_LEVEL:
                return msString.format(fmt, "ListLevel", value);
            case ParaAttr.LIST_ID:
                return msString.format(fmt, "ListId", value);
            case ParaAttr.LINE_SPACING:
                return msString.format(fmt, "LineSpacing", msString.format("({0}, {1})", ((LineSpacing)value).getRule(), ((LineSpacing)value).getValue()));
            case ParaAttr.BIDI:
                return msString.format(fmt, "BiDi", value);
            case ParaAttr.BASELINE_ALIGNMENT:
                return msString.format(fmt, "BaselineAlignment", value);
            case ParaAttr.LEFT_INDENT:
                return msString.format(fmt, "LeftIndent", value);
            case ParaAttr.RIGHT_INDENT:
                return msString.format(fmt, "RightIndent", value);
            case ParaAttr.FIRST_LINE_INDENT:
                return msString.format(fmt, "FirstLineIndent", value);
            case ParaAttr.KEEP_TOGETHER:
                return msString.format(fmt, "KeepTogether", value);
            case ParaAttr.SUPPRESS_AUTO_HYPHENS:
                return msString.format(fmt, "SuppressAutoHyphens", value);
            case ParaAttr.FRAME_TOP:
                return msString.format(fmt, "FrameTop", value);
            case ParaAttr.FRAME_LEFT:
                return msString.format(fmt, "FrameLeft", value);
            case ParaAttr.FRAME_VERTICAL_ALIGNMENT:
                return msString.format(fmt, "FrameVerticalAlignment", verticalAlignmentToString((/*VerticalAlignment*/Integer)value));
            case ParaAttr.FRAME_HORIZONTAL_ALIGNMENT:
                return msString.format(fmt, "FrameHorizontalAlignment", horizontalAlignmentToString((/*HorizontalAlignment*/Integer)value));
            case ParaAttr.FRAME_WIDTH:
                return msString.format(fmt, "FrameWidth", value);
            case ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION:
                return msString.format(fmt, "FrameRelativeHorizontalPosition",
                    relativeHorizontalPositionToString((/*RelativeHorizontalPosition*/Integer)value));
            case ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION:
                return msString.format(fmt, "FrameRelativeVerticalPosition",
                    relativeVerticalPositionToString((/*RelativeVerticalPosition*/Integer)value));
            case ParaAttr.FRAME_WRAP_TYPE:
                return msString.format(fmt, "FrameWrapType", value);
            case ParaAttr.PAGE_BREAK_BEFORE:
                return msString.format(fmt, "PageBreakBefore", value);
            case ParaAttr.OUTLINE_LEVEL:
                return msString.format(fmt, "OutlineLevel", value);
            case ParaAttr.SPACE_AFTER_AUTO:
                return msString.format(fmt, "SpaceAfterAuto", value);
            case ParaAttr.FAR_EAST_LINE_BREAK_CONTROL:
                return msString.format(fmt, "FarEastLineBreakControl", value);
            case ParaAttr.WORD_WRAP:
                return msString.format(fmt, "WordWrap", value);
            case ParaAttr.HANGING_PUNCTUATION:
                return msString.format(fmt, "HangingPunctuation", value);
            case ParaAttr.SNAP_TO_GRID:
                return msString.format(fmt, "SnapToGrid", value);
            case ParaAttr.FRAME_LOCK_ANCHOR:
                return msString.format(fmt, "FrameLockAnchor", value);
            case ParaAttr.SPACE_BEFORE_UNITS:
                return msString.format(fmt, "SpaceBeforeUnits", value);
            case ParaAttr.SPACE_AFTER_UNITS:
                return msString.format(fmt, "SpaceAfterUnits", value);
            case ParaAttr.SPACE_BEFORE_AUTO:
                return msString.format(fmt, "SpaceBeforeAuto", value);
            case ParaAttr.LEFT_INDENT_UNITS:
                return msString.format(fmt, "LeftIndentUnits", value);
            case ParaAttr.RIGHT_INDENT_UNITS:
                return msString.format(fmt, "RightIndentUnits", value);
            case ParaAttr.FIRST_LINE_INDENT_UNITS:
                return msString.format(fmt, "FirstLineIndentUnits", value);
            case ParaAttr.BORDER_LEFT:
                return msString.format(fmt, "BorderLeft", borderToString((Border)value));
            case ParaAttr.BORDER_RIGHT:
                return msString.format(fmt, "BorderRight", borderToString((Border)value));
            case ParaAttr.BORDER_TOP:
                return msString.format(fmt, "BorderTop", borderToString((Border)value));
            case ParaAttr.BORDER_BOTTOM:
                return msString.format(fmt, "BorderBottom", borderToString((Border)value));
            case ParaAttr.BORDER_BETWEEN:
                return msString.format(fmt, "BorderBetween", borderToString((Border)value));
            case ParaAttr.HTML_BLOCK_ID:
                return msString.format(fmt, "HtmlBlockId", msString.format("0x{0:x8}", value));
            //////////////////////
            case CellAttr.SYS_CELL_SPAN:
                return msString.format(fmt, "_CellSpan", value);
            case CellAttr.HIDE_MARK:
                return msString.format(fmt, "HideMark", value);
            case CellAttr.BORDER_BOTTOM:
                return msString.format(fmt, "BorderBottom", borderToString((Border)value));
            case CellAttr.BORDER_TOP:
                return msString.format(fmt, "BorderTop", borderToString((Border)value));
            case CellAttr.BORDER_LEFT:
                return msString.format(fmt, "BorderLeft", borderToString((Border)value));
            case CellAttr.BORDER_RIGHT:
                return msString.format(fmt, "BorderRight", borderToString((Border)value));
            case CellAttr.BORDER_DIAGONAL_DOWN:
                return msString.format(fmt, "BorderDiagonalDown", borderToString((Border)value));
            case CellAttr.BORDER_DIAGONAL_UP:
                return msString.format(fmt, "BorderDiagonalUp", borderToString((Border)value));
            case CellAttr.WIDTH:
                return msString.format(fmt, "Width", value);
            case CellAttr.PREFERRED_WIDTH:
                return msString.format(fmt, "PreferredWidth", value);
            case CellAttr.VERTICAL_ALIGNMENT:
                return msString.format(fmt, "VerticalAlignment", value);
            case CellAttr.WRAP_TEXT:
                return msString.format(fmt, "WrapText", value);
            case CellAttr.VERTICAL_MERGE:
                return msString.format(fmt, "VerticalMerge", value);
            case CellAttr.HORIZONTAL_MERGE:
                return msString.format(fmt, "HorizontalMerge", value);
            case CellAttr.LEFT_PADDING:
                return msString.format(fmt, "LeftPadding", value);
            case CellAttr.RIGHT_PADDING:
                return msString.format(fmt, "RightPadding", value);
            case CellAttr.TOP_PADDING:
                return msString.format(fmt, "TopPadding", value);
            case CellAttr.BOTTOM_PADDING:
                return msString.format(fmt, "BottomPadding", value);
            case CellAttr.FIT_TEXT:
                return msString.format(fmt, "FitText", value);
            case CellAttr.ORIENTATION:
                return msString.format(fmt, "Orientation", value);
            case CellAttr.SHADING:
                return msString.format(fmt, "Shading", shadingToString((Shading)value));
            case CellAttr.BORDER_HORIZONTAL:
                return msString.format(fmt, "BorderHorizontal", borderToString((Border)value));
            case CellAttr.BORDER_VERTICAL:
                return msString.format(fmt, "BorderVertical", borderToString((Border)value));
            //////////////////////
            case TableAttr.HTML_BLOCK_ID:
                return msString.format(fmt, "DivId", value);
            case TableAttr.FRAME_LEFT:
                return msString.format(fmt, "FrameLeft ", value);
            case TableAttr.HORIZONTAL_ALIGNMENT:
                return msString.format(fmt, "HorizontalAlignment ", value);
            case TableAttr.HIDDEN:
                return msString.format(fmt, "Hidden", value);
            case TableAttr.SHADING:
                return msString.format(fmt, "Shading", shadingToString((Shading)value));
            case TableAttr.ISTD:
                return msString.format(fmt, "Istd", msString.format("0x{0:x2}", value));
            case TableAttr.ROW_HEIGHT:
                return msString.format(fmt, "RowHeight", heightToString((Height)value));
            case TableAttr.BORDER_TOP:
                return msString.format(fmt, "BorderTop", borderToString((Border)value));
            case TableAttr.BORDER_BOTTOM:
                return msString.format(fmt, "BorderBottom", borderToString((Border)value));
            case TableAttr.BORDER_LEFT:
                return msString.format(fmt, "BorderLeft", borderToString((Border)value));
            case TableAttr.BORDER_RIGHT:
                return msString.format(fmt, "BorderRight", borderToString((Border)value));
            case TableAttr.LEFT_PADDING:
                return msString.format(fmt, "LeftPadding", (/*int*/Integer)value);
            case TableAttr.TOP_PADDING:
                return msString.format(fmt, "TopPadding", (/*int*/Integer)value);
            case TableAttr.RIGHT_PADDING:
                return msString.format(fmt, "RightPadding", (/*int*/Integer)value);
            case TableAttr.BOTTOM_PADDING:
                return msString.format(fmt, "BottomPadding", (/*int*/Integer)value);
            case TableAttr.LEFT_INDENT:
                return msString.format(fmt, "LeftIndent", (/*int*/Integer)value);
            case TableAttr.WIDTH_BEFORE:
                return msString.format(fmt, "WidthBefore", (PreferredWidth)value);
            case TableAttr.WIDTH_AFTER:
                return msString.format(fmt, "WidthAfter", (PreferredWidth)value);
            case TableAttr.BORDER_HORIZONTAL:
                return msString.format(fmt, "BorderHorizontal", borderToString((Border)value));
            case TableAttr.BORDER_VERTICAL:
                return msString.format(fmt, "BorderVertical", borderToString((Border)value));
            case TableAttr.ALLOW_BREAK_ACROSS_PAGES:
                return msString.format(fmt, "AllowBreakAcrossPages", value);
            case TableAttr.STYLE_OPTIONS:
                return msString.format(fmt, "StyleOptions", value);
            case TableAttr.PREFERRED_WIDTH:
                return msString.format(fmt, "PreferredWidth", value);
            case TableAttr.ALLOW_AUTO_FIT:
                return msString.format(fmt, "AllowAutoFit", value);
            case TableAttr.HEADING_FORMAT:
                return msString.format(fmt, "HeadingFormat", value);
            case TableAttr.RSID_TR:
                return msString.format(fmt, "RsidTr", msString.format("0x{0:x8}", value));
            case TableAttr.ALIGNMENT:
                return msString.format(fmt, "Alignment", value);
            case TableAttr.CELL_SPACING:
                return msString.format(fmt, "CellSpacing", value);
            case TableAttr.RELATIVE_HORIZONTAL_POSITION:
                return msString.format(fmt, "RelativeHorizontalPosition",
                    relativeHorizontalPositionToString((/*RelativeHorizontalPosition*/Integer)value));
            case TableAttr.RELATIVE_VERTICAL_POSITION:
                return msString.format(fmt, "RelativeVerticalPosition",
                    relativeVerticalPositionToString((/*RelativeVerticalPosition*/Integer)value));
            case TableAttr.VERTICAL_ALIGNMENT:
                return msString.format(fmt, "VerticalAlignment", verticalAlignmentToString((/*VerticalAlignment*/Integer)value));
            case TableAttr.FRAME_DISTANCE_FROM_LEFT:
                return msString.format(fmt, "FrameDistanceFromLeft", value);
            case TableAttr.FRAME_DISTANCE_FROM_RIGHT:
                return msString.format(fmt, "FrameDistanceFromRight", value);
            case TableAttr.ALLOW_OVERLAP:
                return msString.format(fmt, "AllowOverlap", value);
            case TableAttr.BIDI:
                return msString.format(fmt, "Bidi", value);
            case TableAttr.FRAME_TOP:
                return msString.format(fmt, "FrameTop", value);
            case TableAttr.STYLE_ROW_BAND_SIZE:
                return msString.format(fmt, "StyleRowBandSize", value);
            case TableAttr.STYLE_COL_BAND_SIZE:
                return msString.format(fmt, "StyleColBandSize", value);
            case TableAttr.FRAME_DISTANCE_FROM_TOP:
                return msString.format(fmt, "FrameDistanceFromTop", value);
            case TableAttr.FRAME_DISTANCE_FROM_BOTTOM:
                return msString.format(fmt, "FrameDistanceFromBottom", value);
            case TableAttr.SYS_CALCULATED_TABLE_GRID:
                return msString.format(fmt, "_CalculatedTableGrid", "TODO");
            //////////////////////
            case SectAttr.RTL_GUTTER:
                return msString.format(fmt, "RtlGutter", value);
            case SectAttr.COLUMNS_EVENLY_SPACED:
                return msString.format(fmt, "ColumnsEvenlySpaced", value);
            case SectAttr.COLUMNS:
                return msString.format(fmt, "Columns", textColumnCollectionInternalToString((TextColumnCollectionInternal)value));
            case SectAttr.SYS_GPRF_IHDT:
                return msString.format(fmt, "_GprfIhdt", value);
            case SectAttr.LINE_PITCH:
                return msString.format(fmt, "LinePitch", value);
            case SectAttr.PAGE_WIDTH:
                return msString.format(fmt, "PageWidth", value);
            case SectAttr.PAGE_HEIGHT:
                return msString.format(fmt, "PageHeight", value);
            case SectAttr.LEFT_MARGIN:
                return msString.format(fmt, "LeftMargin", value);
            case SectAttr.RIGHT_MARGIN:
                return msString.format(fmt, "RightMargin", value);
            case SectAttr.GUTTER:
                return msString.format(fmt, "Gutter", value);
            case SectAttr.HEADER_DISTANCE:
                return msString.format(fmt, "HeaderDistance", value);
            case SectAttr.FOOTER_DISTANCE:
                return msString.format(fmt, "FooterDistance", value);
            case SectAttr.VERTICAL_ALIGNMENT:
                return msString.format(fmt, "VerticalAlignment", value);
            case SectAttr.COLUMNS_COUNT:
                return msString.format(fmt, "ColumnsCount", value);
            case SectAttr.TOP_MARGIN:
                return msString.format(fmt, "TopMargin", value);
            case SectAttr.BOTTOM_MARGIN:
                return msString.format(fmt, "BottomMargin", value);
            case SectAttr.COLUMNS_SPACING:
                return msString.format(fmt, "ColumnsSpacing", value);
            case SectAttr.SECTION_START:
                return msString.format(fmt, "SectionStart", value);
            case SectAttr.FIRST_PAGE_TRAY:
                return msString.format(fmt, "FirstPageTray", value);
            case SectAttr.OTHER_PAGES_TRAY:
                return msString.format(fmt, "OtherPagesTray", value);
            case SectAttr.BORDER_ALWAYS_IN_FRONT:
                return msString.format(fmt, "BorderAlwaysInFront", value);
            case SectAttr.BORDER_DISTANCE_FROM:
                return msString.format(fmt, "BorderDistanceFrom", value);
            case SectAttr.RSID:
                return msString.format(fmt, "Rsid", msString.format("0x{0:x8}", value));
            case SectAttr.FOOTNOTE_LOCATION:
                return msString.format(fmt, "FootnoteLocation", value);
            case SectAttr.FOOTNOTE_NUMBERING_RULE:
                return msString.format(fmt, "FootnoteNumberingRule", value);
            case SectAttr.FOOTNOTE_START_NUMBER:
                return msString.format(fmt, "FootnoteStartNumber", value);
            case SectAttr.BORDER_APPLIES_TO:
                return msString.format(fmt, "BorderAppliesTo", value);
            case SectAttr.FOOTNOTE_NUMBER_STYLE:
                return msString.format(fmt, "FootnoteNumberStyle", value);
            case SectAttr.ENDNOTE_NUMBERING_RULE:
                return msString.format(fmt, "EndnoteNumberingRule", value);
            case SectAttr.ENDNOTE_START_NUMBER:
                return msString.format(fmt, "EndnoteStartNumber", value);
            case SectAttr.ENDNOTE_NUMBER_STYLE:
                return msString.format(fmt, "EndnoteNumberStyle", value);
            case SectAttr.ENDNOTE_LOCATION:
                return msString.format(fmt, "EndnoteLocation", value);
            case SectAttr.PAPER_CODE:
                return msString.format(fmt, "PaperCode", value);
            case SectAttr.SUPPRESS_ENDNOTES:
                return msString.format(fmt, "SuppressEndnotes", value);
            case SectAttr.PAGE_STARTING_NUMBER:
                return msString.format(fmt, "PageStartingNumber", value);
            case SectAttr.DIFFERENT_FIRST_PAGE_HEADER_FOOTER:
                return msString.format(fmt, "DifferentFirstPageHeaderFooter", value);
            case SectAttr.RESTART_PAGE_NUMBERING:
                return msString.format(fmt, "RestartPageNumbering", value);
            case SectAttr.LINE_NUMBER_DISTANCE_FROM_TEXT:
                return msString.format(fmt, "LineNumberDistanceFromText", value);
            case SectAttr.GRID_TYPE:
                return msString.format(fmt, "GridType", value);
            case SectAttr.BIDI:
                return msString.format(fmt, "Bidi", value);
            case SectAttr.TEXT_FLOW:
                return msString.format(fmt, "TextFlow", value);
            case SectAttr.ORIENTATION:
                return msString.format(fmt, "Orientation", value);
            case SectAttr.BORDER_TOP:
                return msString.format(fmt, "BorderTop", borderToString((Border)value));
            case SectAttr.BORDER_BOTTOM:
                return msString.format(fmt, "BorderBottom", borderToString((Border)value));
            case SectAttr.BORDER_RIGHT:
                return msString.format(fmt, "BorderRight", borderToString((Border)value));
            case SectAttr.BORDER_LEFT:
                return msString.format(fmt, "BorderLeft", borderToString((Border)value));
            case SectAttr.PAGE_NUMBER_STYLE:
                return msString.format(fmt, "PageNumberStyle", value);
            case SectAttr.CHAR_SPACE:
                return msString.format(fmt, "CharsLine", value);
            case SectAttr.UNLOCKED:
                return msString.format(fmt, "Unlocked", value);
            //////////////////////
            case Forms2Attr.MULTI_ROW:
                return msString.format(fmt, "MultiRow", value);
            case Forms2Attr.TOOLTIPS:
                return msString.format(fmt, "Tooltips", value);
            case Forms2Attr.NEW_VERSION:
                return msString.format(fmt, "NewVersion", value);
            case Forms2Attr.PROPORTIONAL_THUMB:
                return msString.format(fmt, "ProportionalThumb", value);
            case Forms2Attr.TAKE_FOCUS_ON_CLICK:
                return msString.format(fmt, "TakeFocusOnClick", value);
            case Forms2Attr.AUTO_SIZE:
                return msString.format(fmt, "AutoSize", value);
            case Forms2Attr.POSITION:
                return msString.format(fmt, "Position", value);
            case Forms2Attr.PICTURE_TILING:
                return msString.format(fmt, "PictureTiling", value);
            case Forms2Attr.ID:
                return msString.format(fmt, "ID", value);
            case Forms2Attr.LOGICAL_SIZE:
                return msString.format(fmt, "LogicalSize", value);
            case Forms2Attr.NEXT_AVAILABLE_ID:
                return msString.format(fmt, "NextAvailableID", value);
            case Forms2Attr.BOOLEAN_PROPERTIES:
                return msString.format(fmt, "BooleanProperties", value);
            case Forms2Attr.GROUP_CNT:
                return msString.format(fmt, "GroupCnt", value);
            case Forms2Attr.CYCLE:
                return msString.format(fmt, "Cycle", value);
            case Forms2Attr.FONT:
                return msString.format(fmt, "Font", value);
            case Forms2Attr.ZOOM:
                return msString.format(fmt, "Zoom", value);
            case Forms2Attr.PICTURE_ALIGNMENT:
                return msString.format(fmt, "PictureAlignment", value);
            case Forms2Attr.PICTURE_SIZE_MODE:
                return msString.format(fmt, "PictureSizeMode", value);
            case Forms2Attr.SHAPE_COOKIE:
                return msString.format(fmt, "ShapeCookie", value);
            case Forms2Attr.DRAW_BUFFER:
                return msString.format(fmt, "DrawBuffer", value);
            case Forms2Attr.SCROLL_POSITION:
                return msString.format(fmt, "ScrollPosition", value);
            case Forms2Attr.HELP_CONTEXT_ID:
                return msString.format(fmt, "HelpContextID", value);
            case Forms2Attr.BIT_FLAGS_SITE:
                return msString.format(fmt, "BitFlagsSite", value);
            case Forms2Attr.OBJECT_STREAM_SIZE:
                return msString.format(fmt, "ObjectStreamSize", value);
            case Forms2Attr.TAB_INDEX:
                return msString.format(fmt, "TabIndex", value);
            case Forms2Attr.CLSID_CACHE_INDEX:
                return msString.format(fmt, "ClsidCacheIndex", value);
            case Forms2Attr.GROUP_ID:
                return msString.format(fmt, "GroupID", value);
            case Forms2Attr.NAME:
                return msString.format(fmt, "Name", value);
            case Forms2Attr.TAG:
                return msString.format(fmt, "Tag", value);
            case Forms2Attr.CONTROL_TIP_TEXT:
                return msString.format(fmt, "ControlTipText", value);
            case Forms2Attr.RUNTIME_LIC_KEY:
                return msString.format(fmt, "RuntimeLicKey", value);
            case Forms2Attr.CONTROL_SOURCE:
                return msString.format(fmt, "ControlSource", value);
            case Forms2Attr.ROW_SOURCE:
                return msString.format(fmt, "RowSource", value);
            case Forms2Attr.LIST_INDEX:
                return msString.format(fmt, "ListIndex", value);
            case Forms2Attr.TAB_ORIENATION:
                return msString.format(fmt, "TabOrienation", value);
            case Forms2Attr.TAB_STYLE:
                return msString.format(fmt, "TabStyle", value);
            case Forms2Attr.TAB_FIXED_WIDTH:
                return msString.format(fmt, "TabFixedWidth", value);
            case Forms2Attr.TAB_FIXED_HEIGHT:
                return msString.format(fmt, "TabFixedHeight", value);
            case Forms2Attr.TABS_ALLOCATED:
                return msString.format(fmt, "TabsAllocated", value);
            case Forms2Attr.TAB_DATA:
                return msString.format(fmt, "TabData", value);
            case Forms2Attr.FOREGROUND_COLOR:
                return msString.format(fmt, "ForegroundColor", value);
            case Forms2Attr.BACKGROUND_COLOR:
                return msString.format(fmt, "BackgroundColor", value);
            case Forms2Attr.VARIOUS_PROPERTY_BITS:
                return msString.format(fmt, "VariousPropertyBits", value);
            case Forms2Attr.SIZE:
                return msString.format(fmt, "Size (Displayed size)", value);
            case Forms2Attr.CAPTION:
                return msString.format(fmt, "Caption", msString.format("'{0}'", value));
            case Forms2Attr.BORDER_STYLE:
                return msString.format(fmt, "BorderStyle", value);
            case Forms2Attr.VALUE:
                return msString.format(fmt, "Value", msString.format("'{0}'", value));
            case Forms2Attr.PICTURE_POSITION:
                return msString.format(fmt, "PicturePosition", value);
            case Forms2Attr.MOUSE_POINTER:
                return msString.format(fmt, "MousePointer", value);
            case Forms2Attr.BORDER_COLOR:
                return msString.format(fmt, "BorderColor", value);
            case Forms2Attr.SPECIAL_EFFECT:
                return msString.format(fmt, "SpecialEffect", value);
            case Forms2Attr.PICTURE:
                return msString.format(fmt, "Picture", value);
            case Forms2Attr.ACCELERATOR:
                return msString.format(fmt, "Accelerator", value);
            case Forms2Attr.MOUSE_ICON:
                return msString.format(fmt, "MouseIcon", value);
            case Forms2Attr.MAX_LENGTH:
                return msString.format(fmt, "MaxLength", value);
            case Forms2Attr.SCROLL_BARS:
                return msString.format(fmt, "ScrollBars", value);
            case Forms2Attr.DISPLAY_STYLE:
                return msString.format(fmt, "DisplayStyle", value);
            case Forms2Attr.PASSWORD_CHAR:
                return msString.format(fmt, "PasswordChar", value);
            case Forms2Attr.LIST_WIDTH:
                return msString.format(fmt, "ListWidth", value);
            case Forms2Attr.BOUND_COLUMN:
                return msString.format(fmt, "BoundColumn", value);
            case Forms2Attr.TEXT_COLUMN:
                return msString.format(fmt, "TextColumn", value);
            case Forms2Attr.COLUMN_COUNT:
                return msString.format(fmt, "ColumnCount", value);
            case Forms2Attr.LIST_ROWS:
                return msString.format(fmt, "ListRows", value);
            case Forms2Attr.COLUMN_INFO:
                return msString.format(fmt, "ColumnInfo", value);
            case Forms2Attr.MATCH_ENTRY:
                return msString.format(fmt, "MatchEntry", value);
            case Forms2Attr.LIST_STYLE:
                return msString.format(fmt, "ListStyle", value);
            case Forms2Attr.SHOW_DROP_BUTTON_WHEN:
                return msString.format(fmt, "ShowDropButtonWhen", value);
            case Forms2Attr.DROP_BUTTON_STYLE:
                return msString.format(fmt, "DropButtonStyle", value);
            case Forms2Attr.MULTI_SELECT:
                return msString.format(fmt, "MultiSelect", value);
            case Forms2Attr.MIN:
                return msString.format(fmt, "Min", value);
            case Forms2Attr.MAX:
                return msString.format(fmt, "Max", value);

            case Forms2Attr.SITE_POSITION:
                return msString.format(fmt, "SitePosition", value);
            case Forms2Attr.PREV_ENABLED:
                return msString.format(fmt, "PrevEnabled", value);
            case Forms2Attr.NEXT_ENABLED:
                return msString.format(fmt, "NextEnabled", value);
            case Forms2Attr.SMALL_CHANGE:
                return msString.format(fmt, "SmallChange", value);
            case Forms2Attr.ORIENTATION:
                return msString.format(fmt, "Orientation", value);
            case Forms2Attr.DELAY:
                return msString.format(fmt, "Delay", value);

            case Forms2Attr.GROUP_NAME:
                return msString.format(fmt, "GroupName", msString.format("'{0}'", value));

            default:
                return msString.format(fmt, key, value);
        }
    }

    private static String rubyToString(Ruby ruby)
    {
        StringBuilder sb = new StringBuilder();

        msStringBuilder.appendFormat(sb, "('{0}', '{1}', Base: {2}, Top: {3}, Distance: {4}, {5}, {6})",
                        ruby.getTop().getText(),
                        ruby.getBase().getText(),
                        ruby.BaseSize,
                        ruby.TopSize,
                        ruby.Distance,
                        ruby.Alignment,
                        ruby.Language);

        return sb.toString();
    }

    private static String farEastLayoutToString(FarEastLayout feLayout)
    {
        StringBuilder sb = new StringBuilder();

        msStringBuilder.appendFormat(sb, "({0}{1}{2}FarEastLayoutId: {3}, CombineBrackets: {4})",
            feLayout.getCombine() ? "Combine, " : "",
            feLayout.getVertical() ? "Vertical, " : "",
            feLayout.getVerticalCompress() ? "VerticalCompress, " : "",
            feLayout.getFarEastLayoutId(),
            feLayout.getCombineBrackets());

        return sb.toString();
    }

    private static String textColumnCollectionInternalToString(TextColumnCollectionInternal columns)
    {
        if (columns.getCount() == 0)
            return "(empty)";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.getCount(); i++)
        {
            TextColumn col = columns.get(i);
            msStringBuilder.appendFormat(sb, "(Width: {0}, Space: {1}) ", col.getWidth(), col.getSpaceAfter());
        }

        return sb.toString();
    }

    private static String dmlShapeEffectToString(Object effect)
    {
        if (effect instanceof DmlNullEffect)
            return "NullEffect";

        return effect.toString();
    }

    private static String dmlShapeEffectToString(DmlShapeEffect effect)
    {
        String alpha;
        switch (effect.getEffectType())
        {
            case DmlShapeEffectType.PRESET_SHADOW:
                DmlShapePresetShadowEffect preset = (DmlShapePresetShadowEffect)effect;
                alpha = (preset.getColor().getAlpha() != null) ? FormatterPal.doubleToStr(msMath.round(preset.getColor().getAlpha().getValue(), 1)) : "null";
                return msString.format("(PresetShadow: {0}, Direction: {1:0}, Distance: {2:0}, Color.Alpha: {3})",
                    DmlPresetShadow.toString(preset.getPresetShadow()), preset.getDirection().getValue(), preset.getDistance(), alpha);
            case DmlShapeEffectType.OUTER_SHADOW:
                DmlShapeOuterShadowEffect outer = (DmlShapeOuterShadowEffect)effect;
                alpha = (outer.getColor().getAlpha() != null) ? FormatterPal.doubleToStr(msMath.round(outer.getColor().getAlpha().getValue(), 1)) : "null";
                String colorStr = outer.getColor().getColorType() == DmlColorType.PRESET_COLOR
                    ? ((DmlPresetColor)outer.getColor()).getValue()
                    : ((DmlHexRgbColor)outer.getColor()).getValue();
                return msString.format("(Color: {0}, BlurRadius: {1:0}, Direction: {2:0}, Distance: {3:0}, Alignment: {4}, " +
                    "HorizontalSkew: {5}, VerticalSkew: {6}, HorizontalScale: {7:0.00}, VerticalScale: {8:0.00}, " +
                    "RotateWithShape: {9}, Color.Alpha: {10})", colorStr, outer.getBlurRadius(),
                    outer.getDirection().getValue(), outer.getDistance(), DmlRectangleAlignment.toString(outer.getAlignment()), outer.getHorizontalSkew().getValue(),
                    outer.getVerticalSkew().getValue(), outer.getHorizontalScale(), outer.getVerticalScale(), outer.getRotateWithShape(), alpha);
            case DmlShapeEffectType.INNER_SHADOW:
                DmlShapeInnerShadowEffect inner = (DmlShapeInnerShadowEffect)effect;
                alpha = (inner.getColor().getAlpha() != null) ? FormatterPal.doubleToStr(msMath.round(inner.getColor().getAlpha().getValue(), 1)) : "null";
                return msString.format(
                    "(Color: {0}, BlurRadius: {1:0}, Direction: {2:0}, Distance: {3:0}, Color.Alpha: {4}",
                    ((DmlPresetColor)inner.getColor()).getValue(), inner.getBlurRadius(), inner.getDirection().getValue(), inner.getDistance(), alpha);
        }

        return DmlShapeEffectType.toString(effect.getEffectType());
    }

    private static String pathRectangeArrayToString(PathRectangle[] pathRectangles)
    {
        StringBuilder sb = new StringBuilder();
        for (PathRectangle rect : pathRectangles)
            msStringBuilder.appendFormat(sb, "(Left:{0}, Top:{1}, Right:{2}, Bottom:{3}) ", rect.Left, rect.Top, rect.Right, rect.Bottom);

        return sb.toString();
    }

    private static String connectorRuleToString(ConnectorRule rule)
    {
        return msString.format("({0}:{1} -> {2}:{3})", rule.ShapeAId, rule.ShapeASite, rule.ShapeBId, rule.ShapeBSite);
    }

    private static String heightToString(Height height)
    {
        return height.toString();
    }

    private static String gradientColorToString(GradientColor[] gradients)
    {
        StringBuilder sb = new StringBuilder();

        for (GradientColor gradient : gradients)
            msStringBuilder.appendFormat(sb, "({0}:{1:x8})", gradient.Start, gradient.Color.toArgb());

        return removeTrailingComma(sb);
    }

    private static String formatShapeAttr(int key, Object value) throws Exception
    {
        final String FMT = "  {{Shape.{0}: {1}}}";

        switch (key)
        {
            case ShapeAttr.FILL_ANGLE:
                return msString.format(FMT, "FillAngle", value);
            case ShapeAttr.FILL_FOCUS:
                return msString.format(FMT, "FillFocus", value);
            case ShapeAttr.LINE_OPACITY:
                return msString.format(FMT, "LineOpacity", value);
            case ShapeAttr.FILL_BACK_OPACITY:
                return msString.format(FMT, "FillBackOpacity", value);
            case ShapeAttr.FILL_SHADE_COLORS:
                return msString.format(FMT, "FillShadeColors", gradientColorToString((GradientColor[])value));
            case ShapeAttr.IMAGE_DBL_CR_MOD:
                return msString.format(FMT, "ImageDblCrMod", value);
            case ShapeAttr.IMAGE_SOURCE_FULL_NAME:
                return msString.format(FMT, "ImageSourceFullName", value);
            case ShapeAttr.GRAPHIC_FRAME_EXT_WIDTH:
                return msString.format(FMT, "GraphicFrameExtWidth", value);
            case ShapeAttr.GRAPHIC_FRAME_EXT_HEIGHT:
                return msString.format(FMT, "GraphicFrameExtHeight", value);
            case ShapeAttr.COORD_ORIGIN_X:
                return msString.format(FMT, "CoordOriginX", value);
            case ShapeAttr.COORD_ORIGIN_Y:
                return msString.format(FMT, "CoordOriginY", value);
            case ShapeAttr.LEFT_PERCENT:
                return msString.format(FMT, "LeftPercent", value);
            case ShapeAttr.TOP_PERCENT:
                return msString.format(FMT, "TopPercent", value);
            case ShapeAttr.OLE_SOURCE_FULL_NAME:
                return msString.format(FMT, "OleSourceFullName", value);
            case ShapeAttr.OLE_LOCKED:
                return msString.format(FMT, "OleLocked", value);
            case ShapeAttr.OLE_LINK_TYPE:
                return msString.format(FMT, "OleLinkType", value);
            case ShapeAttr.OLE_FORMAT_UPDATE_TYPE:
                return msString.format(FMT, "OleFormatUpdateType", value);
            case ShapeAttr.CONNECTOR_RULE:
                return msString.format(FMT, "ConnectorRule", connectorRuleToString((ConnectorRule)value));
            case ShapeAttr.GEOMETRY_VERTICES:
                return msString.format(FMT, "GeometryVertices", pathPointArrayToString((PathPoint[])value));
            case ShapeAttr.GEOMETRY_SEGMENT_INFO:
                return msString.format(FMT, "GeometrySegmentInfo", pathInfoArrayToString((PathInfo[])value));
            case ShapeAttr.EDIT_AS:
                return msString.format(FMT, "EditAs", value);
            case ShapeAttr.DIAGRAM_STYLE:
                return msString.format(FMT, "DiagramStyle", value);
            case ShapeAttr.DIAGRAM_RELATIONS_TABLE:
                return msString.format(FMT, "DiagramRelationsTable", value);
            case ShapeAttr.DIAGRAM_SCALE_X:
                return msString.format(FMT, "DiagramScaleX", value);
            case ShapeAttr.DIAGRAM_SCALE_Y:
                return msString.format(FMT, "DiagramScaleY", value);
            case ShapeAttr.SHADOW_HIGHLIGHT:
                return msString.format(FMT, "ShadowHighlight", value);
            case ShapeAttr.SHADOW_SECOND_OFFSET_X:
                return msString.format(FMT, "ShadowSecondOffsetX", value);
            case ShapeAttr.SHADOW_SECOND_OFFSET_Y:
                return msString.format(FMT, "ShadowSecondOffsetY", value);
            case ShapeAttr.SHADOW_SCALE_XTO_X:
                return msString.format(FMT, "ShadowScaleXtoX", value);
            case ShapeAttr.SHADOW_SCALE_XTO_Y:
                return msString.format(FMT, "ShadowScaleXtoY", value);
            case ShapeAttr.SHADOW_SCALE_YTO_X:
                return msString.format(FMT, "ShadowScaleYtoX", value);
            case ShapeAttr.SHADOW_SCALE_YTO_Y:
                return msString.format(FMT, "ShadowScaleYtoY", value);
            case ShapeAttr.SHADOW_ORIGIN_X:
                return msString.format(FMT, "ShadowOriginX", value);
            case ShapeAttr.SHADOW_PERSPECTIVE_X:
                return msString.format(FMT, "ShadowPerspectiveX", value);
            case ShapeAttr.SHADOW_PERSPECTIVE_Y:
                return msString.format(FMT, "ShadowPerspectiveY", value);
            case ShapeAttr.TEXTBOX_FIT_SHAPE_TO_TEXT:
                return msString.format(FMT, "TextboxFitShapeToText", value);
            case ShapeAttr.SHADOW_TYPE:
                return msString.format(FMT, "ShadowTypeCore", value);
            case ShapeAttr.SHADOW_OPACITY:
                return msString.format(FMT, "ShadowOpacity", value);
            case ShapeAttr.SHADOW_OFFSET_X:
                return msString.format(FMT, "ShadowOffsetX", value);
            case ShapeAttr.SHADOW_OFFSET_Y:
                return msString.format(FMT, "ShadowOffsetY", value);
            case ShapeAttr.SHADOW_ORIGIN_Y:
                return msString.format(FMT, "ShadowOriginY", value);
            case ShapeAttr.WIDTH_PERCENT:
                return msString.format(FMT, "WidthPercent", value);
            case ShapeAttr.HEIGHT_PERCENT:
                return msString.format(FMT, "HeightPercent", value);
            case ShapeAttr.RELATIVE_WIDTH:
                return msString.format(FMT, "RelativeWidth", value);
            case ShapeAttr.RELATIVE_HEIGHT:
                return msString.format(FMT, "RelativeHeight", value);
            case ShapeAttr.TEXTBOX_LAYOUT_FLOW:
                return msString.format(FMT, "TextboxLayoutFlow", value);
            case ShapeAttr.LINE_STYLE:
                return msString.format(FMT, "LineStyle", value);
            case ShapeAttr.OLE_AUTO_UPDATE:
                return msString.format(FMT, "OleAutoUpdate", value);
            case ShapeAttr.LINE_END_ARROW_WIDTH:
                return msString.format(FMT, "LineEndArrowWidth", value);
            case ShapeAttr.LINE_END_ARROW_LENGTH:
                return msString.format(FMT, "LineEndArrowLength", value);
            case ShapeAttr.TEXTBOX_ANCHOR:
                return msString.format(FMT, "TextboxAnchor", value);
            case ShapeAttr.TEXTBOX_WRAP_MODE:
                return msString.format(FMT, "TextboxWrapMode", value);
            case ShapeAttr.IMAGE_CROP_TOP:
                return msString.format(FMT, "ImageCropTop", cropToString((/*int*/Integer)value));
            case ShapeAttr.IMAGE_CROP_LEFT:
                return msString.format(FMT, "ImageCropLeft", cropToString((/*int*/Integer)value));
            case ShapeAttr.IMAGE_CROP_RIGHT:
                return msString.format(FMT, "ImageCropRight", cropToString((/*int*/Integer)value));
            case ShapeAttr.IMAGE_CROP_BOTTOM:
                return msString.format(FMT, "ImageCropBottom", cropToString((/*int*/Integer)value));
            case ShapeAttr.FILL_BACK_COLOR:
                return msString.format(FMT, "FillBackColor", value);
            case ShapeAttr.LINE_BACK_COLOR:
                return msString.format(FMT, "LineBackColor", value);
            case ShapeAttr.LINE_JOIN_STYLE:
                return msString.format(FMT, "LineJoinStyle", value);

            case ShapeAttr.TEXTBOX_ROTATE_TEXT:
                return msString.format(FMT, "TextboxRotateText", value);
            case ShapeAttr.HYPERLINK_ADDRESS:
                return msString.format(FMT, "HyperlinkAddress", value);
            case ShapeAttr.SCREEN_TIP:
                return msString.format(FMT, "ScreenTip", value);
            case ShapeAttr.GEOMETRY_CONNECTION_SITE_TYPE:
                return msString.format(FMT, "GeometryConnectionSiteType", value);
            case ShapeAttr.GEOMETRY_CONNECT_LOCS:
                return msString.format(FMT, "GeometryConnectLocs", pathPointArrayToString((PathPoint[])value));
            case ShapeAttr.GEOMETRY_HANDLES:
                return msString.format(FMT, "GeometryHandles", value);
            case ShapeAttr.GEOMETRY_FORMULAS:
                return msString.format(FMT, "GeometryFormulas", value);
            case ShapeAttr.GEOMETRY_ADJUST_1:
            case ShapeAttr.GEOMETRY_ADJUST_2:
            case ShapeAttr.GEOMETRY_ADJUST_3:
            case ShapeAttr.GEOMETRY_ADJUST_4:
            case ShapeAttr.GEOMETRY_ADJUST_5:
            case ShapeAttr.GEOMETRY_ADJUST_6:
            case ShapeAttr.GEOMETRY_ADJUST_7:
            case ShapeAttr.GEOMETRY_ADJUST_8:
            case ShapeAttr.GEOMETRY_ADJUST_9:
            case ShapeAttr.GEOMETRY_ADJUST_10:
                return msString.format(FMT, msString.format("GeometryAdjust{0}", key - ShapeAttr.GEOMETRY_ADJUST_1 + 1), value);
            case ShapeAttr.GEOMETRY_PATH_TEXT_BOX_RECTS:
                return msString.format(FMT, "GeometryPathTextBoxRects", pathRectangeArrayToString((PathRectangle[])value));
            case ShapeAttr.REALLY_HIDDEN:
                return msString.format(FMT, "ReallyHidden", value);
            case ShapeAttr.SCRIPT_ANCHOR:
                return msString.format(FMT, "ScriptAnchor", value);
            case ShapeAttr.IMAGE_GRAY_SCALE:
                return msString.format(FMT, "ImageGrayScale", value);
            case ShapeAttr.IMAGE_BI_LEVEL:
                return msString.format(FMT, "ImageBiLevel", value);
            case ShapeAttr.FILL_COLOR:
                return msString.format(FMT, "FillColor", value);
            case ShapeAttr.FILL_HIT_TEST:
                return msString.format(FMT, "FillHitTest", value);
            case ShapeAttr.FILL_SHAPE:
                return msString.format(FMT, "FillShape", value);
            case ShapeAttr.FILL_USE_RECT:
                return msString.format(FMT, "FillUseRect", value);
            case ShapeAttr.FILL_NO_FILL_HIT_TEST:
                return msString.format(FMT, "FillNoFillHitTest", value);
            case ShapeAttr.LINE_HIT_TEST:
                return msString.format(FMT, "LineHitTest", value);
            case ShapeAttr.LINE_FILL_SHAPE:
                return msString.format(FMT, "LineFillShape", value);
            case ShapeAttr.LINE_NO_LINE_DRAW_DASH:
                return msString.format(FMT, "LineNoLineDrawDash", value);
            case ShapeAttr.SHADOW_ON:
                return msString.format(FMT, "ShadowOn", value);
            case ShapeAttr.SHADOW_OBSCURED:
                return msString.format(FMT, "ShadowObscured", value);
            case ShapeAttr.OLE_ICON:
                return msString.format(FMT, "OleIcon", value);
            case ShapeAttr.WRAP_POLYGON_VERTICES:
                return msString.format(FMT, "WrapPolygonVertices", value);
            case ShapeAttr.DISTANCE_LEFT:
                return msString.format(FMT, "DistanceLeft", value);
            case ShapeAttr.DISTANCE_TOP:
                return msString.format(FMT, "DistanceTop", value);
            case ShapeAttr.DISTANCE_RIGHT:
                return msString.format(FMT, "DistanceRight", value);
            case ShapeAttr.DISTANCE_BOTTOM:
                return msString.format(FMT, "DistanceBottom", value);
            case ShapeAttr.PRINT:
                return msString.format(FMT, "Print", value);
            case ShapeAttr.ONE_D:
                return msString.format(FMT, "OneD", value);
            case ShapeAttr.HIDDEN:
                return msString.format(FMT, "Hidden", value);
            case ShapeAttr.BUTTON:
                return msString.format(FMT, "Button", value);
            case ShapeAttr.EDITED_WRAP:
                return msString.format(FMT, "EditedWrap", value);
            case ShapeAttr.ALLOW_IN_CELL:
                return msString.format(FMT, "AllowInCell", value);
            case ShapeAttr.ON_DBL_CLICK_NOTIFY:
                return msString.format(FMT, "OnDblClickNotify", value);
            case ShapeAttr.OLE_OBJECT:
                return msString.format(FMT, "OleObject", embeddedObjectToString((IEmbeddedObject)value));
            case ShapeAttr.OLE_PROG_ID:
                return msString.format(FMT, "OleProgID", value);
            case ShapeAttr.IMAGE_ACTIVE:
                return msString.format(FMT, "ImageActive", value);
            case ShapeAttr.PREFER_RELATIVE_RESIZE:
                return msString.format(FMT, "PreferRelativeResize", value);
            case ShapeAttr.UNKNOWN_HTML_FLAGS:
                return msString.format(FMT, "UnknownHtmlFlags", value);
            case ShapeAttr.LINE_FILL_PRESET_TEXTURE:
                return msString.format(FMT, "LineFillPresetTexture", value);
            case ShapeAttr.FILL_OPACITY:
                return msString.format(FMT, "FillOpacity", value);
            case ShapeAttr.FILL_RECOLOR_AS_PICTURE:
                return msString.format(FMT, "FillRecolorAsPicture", value);
            case ShapeAttr.FILL_USE_SHAPE_ANCHOR:
                return msString.format(FMT, "FillUseShapeAnchor", value);
            case ShapeAttr.LINE_DASH_STYLE:
                return msString.format(FMT, "LineDashStyle", value);
            case ShapeAttr.LINE_END_CAP_STYLE:
                return msString.format(FMT, "LineEndCapStyle", value);
            case ShapeAttr.LINE_FILL_TYPE:
                return msString.format(FMT, "LineFillType", value);
            case ShapeAttr.LINE_FILL_BLIP_NAME:
                return msString.format(FMT, "LineFillBlipName", value);
            case ShapeAttr.LINE_START_ARROW:
                return msString.format(FMT, "LineStartArrow", value);
            case ShapeAttr.LINE_END_ARROW:
                return msString.format(FMT, "LineEndArrow", value);
            case ShapeAttr.CONNECTOR_TYPE:
                return msString.format(FMT, "ConnectorType", value);
            case ShapeAttr.FILL_BLIP_NAME_FLAGS:
                return msString.format(FMT, "FillBlipNameFlags", value);
            case ShapeAttr.ALLOW_OVERLAP:
                return msString.format(FMT, "AllowOverlap", value);
            case ShapeAttr.FILL_PRESET_TEXTURE:
                return msString.format(FMT, "FillPresetTexture", value);
            case ShapeAttr.FILL_TYPE:
                return msString.format(FMT, "FillType", value);
            case ShapeAttr.LOCK_POSITION:
                return msString.format(FMT, "LockPosition", value);
            case ShapeAttr.FILL_BLIP_NAME:
                return msString.format(FMT, "FillBlipName", value);
            case ShapeAttr.SHAPE_NAME:
                return msString.format(FMT, "ShapeName", value);
            case ShapeAttr.TEXTBOX_LEFT:
                return msString.format(FMT, "TextboxLeft", value);
            case ShapeAttr.TEXTBOX_TOP:
                return msString.format(FMT, "TextboxTop", value);
            case ShapeAttr.TEXTBOX_RIGHT:
                return msString.format(FMT, "TextboxRight", value);
            case ShapeAttr.TEXTBOX_BOTTOM:
                return msString.format(FMT, "TextboxBottom", value);
            case ShapeAttr.TEXTBOX_NEXT_SHAPE_ID:
                return msString.format(FMT, "TextboxNextShapeId", value);
            case ShapeAttr.SYS_TEXTBOX_NEXT_SHAPE_ID_RAW:
                return msString.format(FMT, "_TextboxNextShapeIdRaw", value);
            case ShapeAttr.LINE_COLOR:
                return msString.format(FMT, "LineColor", value);
            case ShapeAttr.LINE_WIDTH:
                return msString.format(FMT, "LineWidth", value);
            case ShapeAttr.GEOMETRY_FILL_OK:
                return msString.format(FMT, "GeometryFillOK", value);
            case ShapeAttr.FILLED:
                return msString.format(FMT, "Filled", value);
            case ShapeAttr.LINE_ARROW_HEADS_OK:
                return msString.format(FMT, "LineArrowHeadsOK", value);
            case ShapeAttr.BEHIND_TEXT:
                return msString.format(FMT, "BehindText", value);
            case ShapeAttr.WRAP_SIDE:
                return msString.format(FMT, "WrapSide", value);
            case ShapeAttr.ANCHOR_LOCKED:
                return msString.format(FMT, "AnchorLocked", value);
            case ShapeAttr.Z_ORDER:
                return msString.format(FMT, "ZOrder", msString.format("0x{0:x8} ({0})", value));
            case ShapeAttr.RELATIVE_HORIZONTAL_POSITION:
                return msString.format(FMT, "RelativeHorizontalPosition", value);
            case ShapeAttr.LOCK_ROTATION:
                return msString.format(FMT, "LockRotation", value);
            case ShapeAttr.LOCK_ASPECT_RATIO:
                return msString.format(FMT, "LockAspectRatio", value);
            case ShapeAttr.LOCK_CROPPING:
                return msString.format(FMT, "LockCropping", value);
            case ShapeAttr.LOCK_AGAINST_GROUPING:
                return msString.format(FMT, "LockAgainstGrouping", value);
            case ShapeAttr.SHAPE_DESCRIPTION:
                return msString.format(FMT, "ShapeDescription", value);
            case ShapeAttr.LOCK_VERTICES:
                return msString.format(FMT, "LockVertices", value);
            case ShapeAttr.LOCK_TEXT:
                return msString.format(FMT, "LockText", value);
            case ShapeAttr.LOCK_ADJUST_HANDLES:
                return msString.format(FMT, "LockAdjustHandles", value);
            case ShapeAttr.SIG_SETUP_ID:
                return msString.format(FMT, "SigSetupId", value);
            case ShapeAttr.SIG_SETUP_PROV_ID:
                return msString.format(FMT, "SigSetupProvId", value);
            case ShapeAttr.SIG_SETUP_SUGG_SIGNER:
                return msString.format(FMT, "SigSetupSuggSigner", value);
            case ShapeAttr.SIG_SETUP_SUGG_SIGNER_2:
                return msString.format(FMT, "SigSetupSuggSigner2", value);
            case ShapeAttr.SIG_SETUP_ADDL_XML:
                return msString.format(FMT, "SigSetupAddlXml", value);
            case ShapeAttr.SIG_SETUP_PROV_URL:
                return msString.format(FMT, "SigSetupProvUrl", value);
            case ShapeAttr.SIG_SETUP_SHOW_SIGN_DATE:
                return msString.format(FMT, "SigSetupShowSignDate", value);
            case ShapeAttr.SIG_SETUP_SIGN_INST:
                return msString.format(FMT, "SigSetupSignInst", value);
            case ShapeAttr.SIG_SETUP_SUGG_SIGNER_EMAIL:
                return msString.format(FMT, "SigSetupSuggSignerEmail", value);
            case ShapeAttr.SIG_SETUP_ALLOW_COMMENTS:
                return msString.format(FMT, "SigSetupAllowComments", value);
            case ShapeAttr.SIG_SETUP_SIGN_INST_SET:
                return msString.format(FMT, "SigSetupSignInstSet", value);
            case ShapeAttr.IS_SIGNATURE_LINE:
                return msString.format(FMT, "IsSignatureLine", value);
            case ShapeAttr.RELATIVE_VERTICAL_POSITION:
                return msString.format(FMT, "RelativeVerticalPosition", value);
            case ShapeAttr.FLIP:
                return msString.format(FMT, "Flip", value);
            case ShapeAttr.WRAP_TYPE:
                return msString.format(FMT, "WrapType", value);
            case ShapeAttr.IMAGE_BYTES:
                return msString.format(FMT, "ImageBytes", imageBytesToString((byte[])value));
            case ShapeAttr.INK_DATA:
                return msString.format(FMT, "InkData", byteArrayToString((byte[])value));
            case ShapeAttr.FILL_IMAGE_BYTES:
                return msString.format(FMT, "FillImageBytes", imageBytesToString((byte[])value));
            case ShapeAttr.LINE_IMAGE_BYTES:
                return msString.format(FMT, "LineImageBytes", imageBytesToString((byte[])value));
            case ShapeAttr.BORDER_TOP:
                return msString.format(FMT, "BorderTop", borderToString((Border)value));
            case ShapeAttr.BORDER_BOTTOM:
                return msString.format(FMT, "BorderBottom", borderToString((Border)value));
            case ShapeAttr.BORDER_LEFT:
                return msString.format(FMT, "BorderLeft", borderToString((Border)value));
            case ShapeAttr.BORDER_RIGHT:
                return msString.format(FMT, "BorderRight", borderToString((Border)value));
            case ShapeAttr.LINE_ON:
                return msString.format(FMT, "LineOn", value);
            case ShapeAttr.IMAGE_TITLE:
                return msString.format(FMT, "ImageTitle", value);
            case ShapeAttr.SHAPE_ID:
                return msString.format(FMT, "ShapeId", value);
            case ShapeAttr.WIDTH:
                return msString.format(FMT, "Width", msMath.round((/*double*/Double)value, 2));
            case ShapeAttr.HEIGHT:
                return msString.format(FMT, "Height", msMath.round((/*double*/Double)value, 2));
            case ShapeAttr.SYS_LAYOUT_WIDTH:
                return msString.format(FMT, "_LayoutWidth", msMath.round((/*double*/Double)value, 2));
            case ShapeAttr.SYS_LAYOUT_HEIGHT:
                return msString.format(FMT, "_LayoutHeight", msMath.round((/*double*/Double)value, 2));
            case ShapeAttr.DIAGRAM_NODE_LAYOUT:
                return msString.format(FMT, "DiagramNodeLayout", value);
            case ShapeAttr.DIAGRAM_NODE_KIND:
                return msString.format(FMT, "DiagramNodeKind", value);
            case ShapeAttr.SHAPE_TYPE:
                return msString.format(FMT, "ShapeType", msString.format("{0} ({1})", value, (/*ShapeType*/Integer)value));
            case ShapeAttr.PSEUDO_INLINE:
                return msString.format(FMT, "PseudoInline", value);
            case ShapeAttr.LEFT:
                return msString.format(FMT, "Left", value);
            case ShapeAttr.TOP:
                return msString.format(FMT, "Top", value);
            case ShapeAttr.DML_EFFECT_EXTENT_LEFT:
                return msString.format(FMT, "DmlEffectExtentLeft", value);
            case ShapeAttr.DML_EFFECT_EXTENT_TOP:
                return msString.format(FMT, "DmlEffectExtentTop", value);
            case ShapeAttr.DML_EFFECT_EXTENT_RIGHT:
                return msString.format(FMT, "DmlEffectExtentRight", value);
            case ShapeAttr.DML_EFFECT_EXTENT_BOTTOM:
                return msString.format(FMT, "DmlEffectExtentBottom", value);
            case ShapeAttr.SHADOW_COLOR:
                return msString.format(FMT, "ShadowColor", value);
            case ShapeAttr.TEXTBOX_TXID:
                return msString.format(FMT, "TextboxTxid", value);
            case ShapeAttr.HORIZONTAL_ALIGNMENT:
                return msString.format(FMT, "HorizontalAlignment", value);
            case ShapeAttr.VERTICAL_ALIGNMENT:
                return msString.format(FMT, "VerticalAlignment", value);
            case ShapeAttr.COORD_SIZE_WIDTH:
                return msString.format(FMT, "CoordSizeWidth", value);
            case ShapeAttr.COORD_SIZE_HEIGHT:
                return msString.format(FMT, "CoordSizeHeight", value);
            case ShapeAttr.TRANSFORM_ROTATION:
                return msString.format(FMT, "TransformRotation", value);
            case ShapeAttr.GEOMETRY_THREE_DOK:
                return msString.format(FMT, "GeometryThreeDOK", value);
            case ShapeAttr.GEOMETRY_LINE_OK:
                return msString.format(FMT, "GeometryLineOK", value);
            case ShapeAttr.LINE_INSET_PEN:
                return msString.format(FMT, "LineInsetPen", value);
            case ShapeAttr.GEOMETRY_CONNECT_ANGLES:
                return msString.format(FMT, "GeometryConnectAngles", int32ArrayToString((int[])value));
            case ShapeAttr.LINE_MITER_LIMIT:
                return msString.format(FMT, "LineMiterLimit", value);
            case ShapeAttr.BW_MODE:
                return msString.format(FMT, "BWMode", value);
            case ShapeAttr.FILL_TO_LEFT:
                return msString.format(FMT, "FillToLeft", value);
            case ShapeAttr.FILL_TO_TOP:
                return msString.format(FMT, "FillToTop", value);
            case ShapeAttr.FILL_TO_RIGHT:
                return msString.format(FMT, "FillToRight", value);
            case ShapeAttr.FILL_TO_BOTTOM:
                return msString.format(FMT, "FillToBottom", value);
            case ShapeAttr.GFX_DATA:
                return msString.format(FMT, "GfxData", byteArrayToString((byte[])value));

            default:
                return msString.format(FMT, key, value);
        }
    }

    private static void dumpMsoEnvelope(DocumentBase doc) throws Exception
    {
        byte[] msoEnvelopeRaw = ((Document)doc).getMsoEnvelope();

        if (msoEnvelopeRaw == null)
            return;

        BinaryReader reader = new BinaryReader(new MemoryStream(msoEnvelopeRaw));

        MsoEnvelope env = new MsoEnvelope(reader);

        write("\n[MsoEnvelope]");

        write(msString.format("  {{Subject: '{0}'}}", env.Subject));
        write(msString.format("  {{Request: '{0}'}}", env.Request));


        write(msString.format("  {{LastSentTime: {0}}}", dumpMsoEnvelopeDate(env.LastSentTime)));
        write(msString.format("  {{ReplyTime: {0}}}", dumpMsoEnvelopeDate(env.ReplyTime)));
        write(msString.format("  {{ExpireTime: {0}}}", dumpMsoEnvelopeDate(env.ExpireTime)));
        write(msString.format("  {{DeferredDeliveryTime: {0}}}", dumpMsoEnvelopeDate(env.DeferredDeliveryTime)));

        write(msString.format("  {{FlagStatus: {0}}}", env.FlagStatus));
        write(msString.format("  {{Importance: {0}}}", env.Importance));
        write(msString.format("  {{Sensitivity: {0}}}", env.Sensitivity));
        write(msString.format("  {{SecurityFlags: {0}}}", env.SecurityFlags));

        write(msString.format("  {{VotingOptions: '{0}'}}", env.VotingOptions));

        write(msString.format("  {{SentRepresentingName: '{0}'}}", env.SentRepresentingName));
        write(msString.format("  {{InetAcctStamp: '{0}'}}", env.InetAcctStamp));
        write(msString.format("  {{InetAcctName: '{0}'}}", env.InetAcctName));
        write(msString.format("  {{Categories: '{0}'}}", env.Categories));

        write(msString.format("  {{DeleteAfterSubmit: {0}}}", env.DeleteAfterSubmit));
        write(msString.format("  {{OriginatorDeliveryReportRequested: {0}}}", env.OriginatorDeliveryReportRequested));
        write(msString.format("  {{ReadReceiptRequested: {0}}}", env.ReadReceiptRequested));

        dumpMsoRecipients("ReplyRecipients", env.ReplyRecipients);
        dumpMsoRecipients("ContactLinkRecipients", env.ContactLinkRecipients);
        dumpMsoRecipients("Recipients", env.Recipients);

        // internal byte[] SentRepresentingEntryId;
    }

    private static String dumpMsoEnvelopeDate(DateTime dateTime)
    {
        return !DateTime.equals(dateTime, DateTime.MinValue)
            ? dateTime.toString(msCultureInfo.getCultureInfo("ru-RU"))
            : "None";
    }

    private static void dumpMsoRecipients(String name, Pair[][] col) throws Exception
    {
        if (col == null)
            return;

        int i = 0;
        for (Pair[] childList : col)
        {
            write(msString.format("  [{0}:{1}]", name, i++));
            IndentLevel++;
            for (Pair pair : childList)
            {
                write(msString.format("{{{0}; {1}}}", pair.getFirst(), pair.getSecond()));
            }
            IndentLevel--;
        }
    }

    private static String cropToString(int value)
    {
        return msString.format("{0}%", msMath.round(ConvertUtilCore.fixedToDouble(value) * 100.0, 2));
    }

    private static void dumpPr(AttrCollection attrs, Object IAttrSource) throws Exception
    {
        String format = "{0}";

        if (NoAttributes)
            return;

        if (attrs == null)
            return;

        if (attrs.getInternState() == InternState.INTERNED)
        {
            String id = msString.format("0x{0:x4}", attrs.getPoolItem().Id);
            if (ShowIntern)
                write(msString.format("  {{Interned: {0}}}", id));
            attrs = attrs.getPoolItem().Pr;
        }
        else if (attrs.getInternState() == InternState.POOLED)
        {
            String id = msString.format("0x{0:x4}", attrs.getPoolItem().Id);
            if (ShowIntern)
                write(msString.format("  {{Pooled: {0}}}", id));
        }

        for (int k = 0; k < attrs.getCount(); k++)
        {
            int key = attrs.getKey(k);
            Object value = attrs.getByIndex(k);

            if (key == RevisionAttr.FORMAT_REVISION)
            {
                FormatRevision formatRevision = (FormatRevision)value;
                if (NoRevisionDetails)
                    write("+ (-)");
                else
                    write(msString.format("+ ({0}, {1})", formatRevision.getAuthor(), formatRevision.getDateTime()));
                dumpPr(formatRevision.getRevPr());
            }
            else if (key == 9999)
            {
                IndentLevel++;
                write("{AttrBackup}");
                AttrCollectionBackup attrBackup = (AttrCollectionBackup)value;
                dumpPr(attrBackup.getPr());
                IndentLevel--;
            }
            else if ((key == FontAttr.ALTERNATE_CONTENT) && DumpAlternateContent)
            {
                IndentLevel++;
                AlternateContent alternateContent = (AlternateContent)value;
                write(msString.format("+ AlternateContent, Requires: '{0}'", alternateContent.getRequires()));
                IndentLevel++;
                if (alternateContent.getFallBack() != null)
                    dumpNode(alternateContent.getFallBack());
                IndentLevel--;
                IndentLevel--;
            }
            else if (key == FontAttr.RUBY)
            {
                Ruby ruby = (Ruby)attrs.get(key);
                write(msString.format("  {{Ruby: {0}}}", rubyToString(ruby)));
                IndentLevel++;
                for (int i = 0; i < ruby.getTop().getCount(); i++)
                {
                    write(msString.format("{{Top: '{0}'}}", ruby.getTop().get(i).Text));
                    dumpPr(ruby.getTop().get(i).RunPr);
                }
                for (int i = 0; i < ruby.getBase().getCount(); i++)
                {
                    write(msString.format("{{Base: '{0}'}}", ruby.getBase().get(i).Text));
                    dumpPr(ruby.getBase().get(i).RunPr);
                }
                IndentLevel--;
            }
            else if (!isIgnoreAttr(key))
            {
                boolean isInherited = isInheritedValue(key, value, IAttrSource);

                if (!(DontShowInherited && isInherited))
                {
                    String formattedValue = msString.format(isInherited ? "{0}*" : format, formatAttr(key, value));
                    if (StringUtil.hasChars(formattedValue))
                        write(formattedValue);
                }
            }
        }
    }

    private static boolean isInheritedValue(int key, Object value, Object IAttrSource)
    {
        // ListId and ListLevel explicitly set is not considred inherited even if they are equal to defaults.
        if (key == ParaAttr.LIST_ID || key == ParaAttr.LIST_LEVEL)
            return false;

        // Protection against fetching unknown defaults,
        try
        {
            boolean result = isInheritedValueCore(key, value, IAttrSource);
            return result;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    private static boolean isInheritedValueCore(int key, Object value, Object IAttrSource)
    {
        if (IAttrSource == null)
            return false;

        Object inhValue = null;

        if (IAttrSource instanceof Section)
            inhValue = SectPr.fetchDefaultAttr(key);
        else if (IAttrSource instanceof Shape)
            inhValue = ((IShapeAttrSource)IAttrSource).fetchInheritedShapeAttr(key);
        else if (isRunAttribute(key))
            inhValue = ((IRunAttrSource)IAttrSource).fetchInheritedRunAttr(key);
        else if (isParaAttribute(key))
            inhValue = ((IParaAttrSource)IAttrSource).fetchInheritedParaAttr(key);
        else if (isTableAttribute(key))
            inhValue = ((IRowAttrSource)IAttrSource).fetchInheritedRowAttr(key);
        else if (isCellAttribute(key))
            inhValue = ((ICellAttrSource)IAttrSource).fetchInheritedCellAttr(key);

        return (msObject.equals(inhValue, value));
    }

    /// <summary>
    /// Returns attribute collection as string.
    /// </summary>
    static String prToString(AttrCollection attrs)
    {
        return prToString(attrs, "");
    }

    /// <summary>
    /// Returns attribute collection as string. Attributes are separated with given separator string.
    /// </summary>
    static String prToString(AttrCollection attrs, String attrSeparator)
    {
        StringBuilder sb = new StringBuilder();

        if (attrs == null)
            return "null";

        for (int k = 0; k < attrs.getCount(); k++)
        {
            int key = attrs.getKey(k);
            Object value = attrs.getByIndex(k);

            if (!isIgnoreAttr(key))
            {
                if (key == RevisionAttr.FORMAT_REVISION)
                {
                    FormatRevision formatRevision = (FormatRevision)value;
                    msStringBuilder.appendFormat(sb, "+ {0} {1}", formatRevision.getAuthor(), formatRevision.getDateTime());
                    msStringBuilder.append(sb, attrSeparator);
                    prToString(formatRevision.getRevPr(), attrSeparator);
                }
                else
                {
                    msStringBuilder.append(sb, msString.trim(formatAttr(key, value)));
                    msStringBuilder.append(sb, attrSeparator);
                }

            }
        }
        return sb.toString();
    }

    /// <summary>
    /// Returns attribute collection as string. Attributes are separated with given separator string.
    /// </summary>
    static String shapePrToString(AttrCollection attrs, String attrSeparator) throws Exception
    {
        StringBuilder sb = new StringBuilder();

        if (attrs == null)
            return "null";

        for (int k = 0; k < attrs.getCount(); k++)
        {
            int key = attrs.getKey(k);
            Object value = attrs.getByIndex(k);

            if (!isIgnoreAttr(key))
            {
                if (key == RevisionAttr.FORMAT_REVISION)
                {
                    FormatRevision formatRevision = (FormatRevision)value;
                    msStringBuilder.appendFormat(sb, "+ {0} {1}", formatRevision.getAuthor(), formatRevision.getDateTime());
                    msStringBuilder.append(sb, attrSeparator);
                    prToString(formatRevision.getRevPr(), attrSeparator);
                }
                else
                {
                    msStringBuilder.append(sb, msString.trim(formatShapeAttr(key, value)));
                    msStringBuilder.append(sb, attrSeparator);
                }

            }
        }
        return sb.toString();
    }

    private static void dumpPr(AttrCollection attrs) throws Exception
    {
        dumpPr(attrs, null);
    }

    private static void dumpShapePr(AttrCollection attrs, Object IAttrSource) throws Exception
    {
        if (NoAttributes)
            return;

        if (attrs == null)
            return;

        for (int k = 0; k < attrs.getCount(); k++)
        {
            int key = attrs.getKey(k);
            Object value = attrs.getByIndex(k);

            if (!isIgnoreShapeAttr(key))
            {
                boolean isInherited = isInheritedValue(key, value, IAttrSource);

                if (!(DontShowInherited && isInherited))
                {
                    String formattedValue = msString.format(isInherited ? "{0}*" : "{0}", formatShapeAttr(key, value));
                    if (StringUtil.hasChars(formattedValue))
                        write(formattedValue);
                }
            }
        }
    }

    private static boolean hasAttrs(WordAttrCollection attrs, Object attrSource)
    {
        for (int k = 0; k < attrs.getCount(); k++)
        {
            int key = attrs.getKey(k);
            Object value = attrs.getByIndex(k);
            if (showAttribute(key, value, attrSource))
                return true;
        }

        return false;
    }

    private static boolean showAttribute(int key, Object value, Object attrSource)
    {
        if (isIgnoreAttr(key))
            return false;

        if (DontShowInherited && isInheritedValue(key, value, attrSource))
            return false;

        return true;
    }

    private static boolean isIgnoreAttr(int key)
    {
        if (AllAttributes)
            return false;

        if (NoSysAttrs && contains(G_SYS_ATTRS, key))
            return true;

        if (TogglesOnly)
            return !contains(G_TOGGLE_RELATED_ATTRS, key);

        if (FloatingOnly)
            return !(contains(G_PARA_FLOATING_ATTRS, key) || contains(G_TABLE_FLOATING_ATTRS, key));

        if (NoFontNames && contains(G_FONT_NAME_ATTRS, key))
            return true;

        if (NoLocales && contains(G_LOCALE_ATTRS, key))
            return true;

        if (NoRsids && contains(G_RSID_ATTRS, key))
            return true;

        if (NoRunAttributes && isRunAttribute(key))
            return true;

        if (NoTableAttributes && (isTableAttribute(key) || isCellAttribute(key)))
            return true;

        return false;
    }

    private static boolean isIgnoreShapeAttr(int key)
    {
        if (ShapeIdsOnly)
            return !contains(G_SHAPE_IDS_ATTRS, key);

        return false;
    }

    private static boolean contains(int[] array, int key)
    {
        for (int i = 0; i < array.length; i++)
            if (array[i] == key)
                return true;

        return false;
    }

    static String getPrintableText(String text)
    {
        if (!StringUtil.hasChars(text))
            return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            if (c == 0x00)
                msStringBuilder.append(sb, "\\0");
            else if (c == 0x07)
                msStringBuilder.append(sb, "\\a");
            else if (c == 0x08)
                msStringBuilder.append(sb, "\\b");
            else if (c == 0x09)
                msStringBuilder.append(sb, "\\t");
            else if (c == 0x0a)
                msStringBuilder.append(sb, "\\n");
            else if (c == 0x0b)
                msStringBuilder.append(sb, "\\v");
            else if (c == 0x0c)
                msStringBuilder.append(sb, "\\f");
            else if (c == 0x0d)
                msStringBuilder.append(sb, "\\r");
            else if (c == '\\')
                msStringBuilder.append(sb, "\\\\");
            else if (c < 0x20)
                msStringBuilder.appendFormat(sb, "\\x{0:x2}", (int)c);
            else if ((c < 0x80) || Character.isLetterOrDigit(c))
                sb.append(c);
            else
                msStringBuilder.appendFormat(sb, "\\x{0:x4}", (int)c);
        }

        return sb.toString();
    }

    private static void write(String format, Object obj) throws Exception
    {
        write(msString.format(format, obj.toString()));
    }

    private static void write(String format, String text) throws Exception
    {
        write(msString.format(format, text));
    }

    private static void write(String text) throws Exception
    {
        for (int i = 0; i < IndentLevel * 4; i++)
            Writer.write(' ');

        Writer.writeLine(text);
    }

    static boolean NoTableBody = false;
    static boolean NoAttributes;
    static boolean NoLists;
    static boolean NoStyles;
    static boolean NoParagraphAttributes;
    static boolean NoRunAttributes;
    static boolean NoSectionAttributes;
    static boolean NoCellAttributes;
    static boolean NoTableAttributes;
    static boolean NoShapeAttributes;
    static boolean ShowParaId = false;
    static boolean NoDocumentProperties;
    static boolean DoRefine;
    static boolean NoParagraphContent;
    static boolean NoWarnings;
    static boolean JoinRuns;
    static boolean NoSysAttrs = true;
    /// <summary>
    /// Performs horizontal merge normalization before Model saving.
    /// </summary>
    static boolean NormalizeHorizontalMerge;
    static boolean NoFontNames;
    static boolean NoThemes = false;
    static boolean NoLocales = false;
    static boolean NoRsids = true;
    static boolean NoLatentStyles = true;
    static boolean GlobalTableAttributes = false;
    static boolean Cleanup = false;
    static boolean NoGoBackBookmark = true;

    static boolean NoNodeId = false;

    static boolean FloatingOnly = false;

    /// <summary>
    /// Dumps only toggle related attributes.
    /// </summary>
    static boolean TogglesOnly = false;

    /// <summary>
    /// Converts toggle attributes to unified model.
    /// </summary>
    static boolean UnifyToggles = false;

    static boolean LocalizeFontNames = false;
    static boolean LocalizeStyleNames = false;

    static boolean ShowListLabels = true;
    static boolean ShowFloating = true;
    static boolean ShowIntern = true;

    static boolean ShowUnicodeBlocks = false;
    static boolean ShowCharacterCategory = false;
    static boolean SkipCrossStructureAnnotations = false;
    static boolean ShapeIdsOnly = false;
    static boolean DumpAlternateContent = true;
    static boolean DumpCellerData = true;
    static boolean ShowDmlNode = true;
    static boolean DontShowInherited = false;
    static boolean NoRevisionDetails = false;
    static boolean NoIstdInStyles = true;
    static boolean NoRevisionGroups = false;

    static boolean AllAttributes = false;
    static boolean NoDocumentTree = false;
    static boolean ShowExpanded = false;
    static boolean ShowLayout = false;

    /// <summary>
    /// Additionally dumps model for document contained in CustomXML storage.
    /// </summary>
    static boolean DumpCustomXmlPartDocuments = false;

    private static IWarningCallback gWarningCallback;
    private static TextWriter Writer;
    private static int IndentLevel;

    private static String FileName = "";

    private static /*final*/ char[] G_LIST_SEPARATOR_CHARS = new char[] { ',', ' ' };

    private static /*final*/ int[] G_SYS_ATTRS = new int[]
        { ParaAttr.SYS_ALIGNMENT_97, ParaAttr.SYS_LEFT_INDENT_97, ParaAttr.SYS_RIGHT_INDENT_97, ParaAttr.SYS_FIRST_LINE_INDENT_97 };

    private static /*final*/ int[] G_FONT_NAME_ATTRS = new int[]
        { FontAttr.NAME_ASCII, FontAttr.NAME_BI, FontAttr.NAME_FAR_EAST, FontAttr.NAME_OTHER };

    private static /*final*/ int[] G_LOCALE_ATTRS = new int[]
        { FontAttr.LOCALE_ID, FontAttr.LOCALE_ID_BI, FontAttr.LOCALE_ID_FAR_EAST };

    private static /*final*/ int[] G_RSID_ATTRS = new int[]
        { ParaAttr.RSID_P, FontAttr.RSID_R, FontAttr.RSID_R_PR, TableAttr.RSID_TR, SectAttr.RSID };

    private static /*final*/ int[] G_PARA_FLOATING_ATTRS = new int[]
        {
            ParaAttr.FRAME_HORIZONTAL_ALIGNMENT,
            ParaAttr.FRAME_HEIGHT,
            ParaAttr.FRAME_HORIZONTAL_DISTANCE_FROM_TEXT,
            ParaAttr.FRAME_LEFT, ParaAttr.FRAME_LOCK_ANCHOR,
            ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION,
            ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION,
            ParaAttr.FRAME_SUPPRESS_OVERLAP,
            ParaAttr.FRAME_TEXT_ORIENTATION, ParaAttr.FRAME_TOP,
            ParaAttr.FRAME_VERTICAL_ALIGNMENT,
            ParaAttr.FRAME_VERTICAL_DISTANCE_FROM_TEXT,
            ParaAttr.FRAME_WIDTH, ParaAttr.FRAME_WRAP_TYPE,
            ParaAttr.ISTD
        };

    private static /*final*/ int[] G_TABLE_FLOATING_ATTRS = new int[]
        {
            TableAttr.FRAME_TOP, TableAttr.FRAME_LEFT,
            TableAttr.HORIZONTAL_ALIGNMENT,
            TableAttr.VERTICAL_ALIGNMENT,
            TableAttr.FRAME_DISTANCE_FROM_TOP,
            TableAttr.FRAME_DISTANCE_FROM_BOTTOM,
            TableAttr.FRAME_DISTANCE_FROM_LEFT,
            TableAttr.FRAME_DISTANCE_FROM_RIGHT,
            TableAttr.RELATIVE_HORIZONTAL_POSITION,
            TableAttr.RELATIVE_VERTICAL_POSITION,
            TableAttr.ISTD, TableAttr.ALLOW_OVERLAP
        };

    private static /*final*/ int[] G_SHAPE_IDS_ATTRS = new int[]
        {
            ShapeAttr.SHAPE_ID,
            ShapeAttr.SHAPE_NAME,
            ShapeAttr.SYS_TEXTBOX_NEXT_SHAPE_ID_RAW,
            ShapeAttr.TEXTBOX_NEXT_SHAPE_ID
        };

    private static /*final*/ int[] G_TOGGLE_ATTRS = new int[]
        {
            FontAttr.BOLD, FontAttr.BOLD_BI, FontAttr.ITALIC, FontAttr.ITALIC_BI, FontAttr.ALL_CAPS, FontAttr.HIDDEN,
            FontAttr.SMALL_CAPS
        };

    private static /*final*/ int[] G_TOGGLE_RELATED_ATTRS = new int[]
    {
        ParaAttr.ISTD, FontAttr.ISTD, FontAttr.BOLD, FontAttr.ITALIC, FontAttr.ALL_CAPS
    };

    private static /*final*/ int[] G_HEADER_SORT_ORDER =
        new int[]
        {
            2, /* HeaderEven */
            1, /* HeaderPrimary */
            5, /* FooterEven */
            4, /* FooterPrimary */
            0, /* HeaderFirst */
            3, /* FooterFirst */
        };

    private static /*final*/ String[] G_PANOSE_STRINGS =
       new String[] { "FamilyType", "SerifStyle", "Weight", "Proportion", "Contrast", "StrokeVariation", "ArmStyle", "Letterform", "Midline", "XHeight" };

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"宋体",
		"黑体",
		"\u0001Ole",
		"\u0003ObjInfo",
		"\u0001CompObj",
		"AttachDesc",
		"\u0001Ole10Native"
	);

}

