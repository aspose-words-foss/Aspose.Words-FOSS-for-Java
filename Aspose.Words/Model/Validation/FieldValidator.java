// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/07/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StackExtension;
import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// This class is invoked by <see cref="DocumentValidator"/> to correct any invalid fields before saving a document.
/// As I just have started this class it only contains some basics checks and corrections.
/// Add more checks and corrections here as needed.
/// </summary>
class FieldValidator
{
    FieldValidator(IWarningCallback warningCallback, /*SaveFormat*/int saveFormat)
    {
        mSaveFormat = saveFormat;
        mWarningCallback = warningCallback;
    }

    void visitDocumentEnd()
    {
        // One situation when we can have some field chars in the stack is when a field did not have a field end.
        while (mFieldChars.getCount() > 0)
        {
            FieldBundle bundle = popFieldBundleSafe();

            FieldStart start = bundle.getStart();
            FieldSeparator separator = bundle.getSeparator();
            FieldEnd end = bundle.getEnd();

            if ((start != null) && (end == null))
            {
                // Field has no end. Mmm what should we do.
                if (start.getFieldType() != FieldType.FIELD_NONE)
                {
                    // If field type is valid, there is a possibility we can correct the field.
                    // Let's insert an end node. The most difficult question is where to insert.
                    // Let's insert it at the end of the separator or start node's parent.
                    // Hit by TestDefect12929.
                    boolean hasSeparator = separator != null;
                    CompositeNode parent = hasSeparator ? separator.getParentNode() : start.getFirstNonMarkupParentNode();
                    end = new FieldEnd(start.getDocument(),  AttrCollectionExtension.deepClone(start.getRunPr()), start.getFieldType(), hasSeparator);
                    parent.appendChild(end);
                }
                else
                {
                    // Field type is none. This is some really bugged field, lets delete the field chars.
                    // Any field code or result will remain as plain text which is okay for us.
                    // Hit by TestDefect5221.
                    warn(WarningType.DATA_LOSS, WarningSource.VALIDATOR, WarningStrings.FIELD_VALIDATOR_FIELD_NONE);
                    bundle.removeFieldNodes();
                }
            }
            else if ((start == null) && (end == null))
            {
                // andrnosk: RESILIENCY WORDSNET-5330 Remove standalone FieldSeparators.
                warn(WarningType.DATA_LOSS, WarningSource.VALIDATOR, WarningStrings.FIELD_VALIDATOR_NO_START_END);
                bundle.removeFieldNodes();
            }
        }
    }

    void visitFieldStart(FieldStart fieldStart) throws Exception
    {
        if (needReplaceWithResult(fieldStart))
        {
            FieldBundle bibliographyBundle = FieldBundle.getFieldBundle(fieldStart);
            FieldReplacer.replaceWithResult(new Field(bibliographyBundle));
            return;
        }

        mFieldChars.push(fieldStart);

        if (FieldUtil.isFormField(fieldStart.getFieldType()))
            mFormFieldValidationCounter++;
    }

    void visitFieldSeparator(FieldSeparator fieldSeparator)
    {
        mFieldChars.push(fieldSeparator);
    }

    void visitFormField(FormField formField)
    {
        if (mFormFieldValidationCounter != 0)
            return;

        // WORDSNET-8448 mFormFieldValidationCounter is 0, because there was no FieldStart.
        // We should remove formField, like it happens in MSWord.
        warn(WarningType.DATA_LOSS, WarningSource.VALIDATOR, WarningStrings.FIELD_VALIDATOR_REMOVED_INVALID_FORM_FIELD);
        formField.remove();
    }

    void visitFieldEnd(FieldEnd fieldEnd)
    {
        mFieldChars.push(fieldEnd);

        FieldBundle bundle = popFieldBundleSafe();

        // This is a simplistic validation that deletes field nodes if the field has no start node.
        // If there was any field code or result text, it just remains in the document as plain text.
        // Hit by TestDefect5221.
        if (bundle.getStart() == null)
        {
            warn(WarningType.DATA_LOSS, WarningSource.VALIDATOR, WarningStrings.FIELD_VALIDATOR_NO_START);
            bundle.removeFieldNodes();
        }

        if (FieldUtil.isFormField(fieldEnd.getFieldType()))
            mFormFieldValidationCounter--;

        if ((bundle.getStart() != null) && (bundle.getEnd() != null))
        {
            // WORDSNET-7346 Update dirty/locked flags for all field parts.
            bundle.updateDirtyLocked();

            determineFieldType(bundle);

            removeCrossStoryFieldNodes(bundle);
        }

        // Add more validations here when needed.
    }

    private void removeCrossStoryFieldNodes(FieldBundle bundle)
    {
        // WORDSNET-7641 Field starts in header and ends in body which causes Office Validator complains.
        // AM. It seems that field must not go outside header/footer.
        // Because it's just assumption type is limited to FieldType.None as it is in problematic document.
        // andrnosk: WORDSNET-7910 One more problematic document with FieldType.FieldPage.
        // WORDSNET-7913 Lets do this correction for any field type.
        Node startStory = bundle.getStart().getStoryAncestor(NodeType.ANY);

        // WORDSNET-23439 Document contains field which spreads for two shapes.
        if ((startStory.getNodeType() != NodeType.HEADER_FOOTER) && (startStory.getNodeType() != NodeType.SHAPE))
            return;

        Node endStory = bundle.getEnd().getStoryAncestor(NodeType.ANY);
        if (startStory == endStory)
            return;

        warn(WarningType.DATA_LOSS, WarningSource.VALIDATOR, WarningStrings.FIELD_VALIDATOR_DIFFERENT_STORIES_FIELD);
        bundle.removeFieldNodes();
    }

    private void determineFieldType(FieldBundle bundle)
    {
        bundle.determineFieldType();

        if (mSaveFormat != SaveFormat.DOC)
            return;

        if (bundle.getFieldType() != FieldType.FIELD_NONE)
            return;

        FieldUnknown field = (FieldUnknown)bundle.getField();
        if (!field.isBookmarkRef())
            return;

        bundle.setFieldType(FieldType.FIELD_REF_NO_KEYWORD);
    }

    private FieldBundle popFieldBundleSafe()
    {
        FieldEnd fieldEnd = (FieldEnd)StackExtension.popIfInstanceOf(mFieldChars, FieldEnd.class);
        FieldSeparator fieldSeparator = (FieldSeparator)StackExtension.popIfInstanceOf(mFieldChars, FieldSeparator.class);
        FieldStart fieldStart = (FieldStart)StackExtension.popIfInstanceOf(mFieldChars, FieldStart.class);

        return new FieldBundle(fieldStart, fieldSeparator, fieldEnd);
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    private void warn(/*WarningType*/int type, /*WarningSource*/int source, String description)
    {
        if (mWarningCallback != null)
            mWarningCallback.warning(new WarningInfo(type, source, description));
    }

    /// <summary>
    /// Returns "true" when field must be replaced with it's result.
    /// </summary>
    private boolean needReplaceWithResult(FieldChar fieldChar)
    {
        // WORDSNET-21460 Replace "Bibliography" field with it's result.
        // DS: Strange behavior, but the Word replaces problematic field with the result while saving to DOC/WML.
        // It's better to do it in writers and do not change the model. But lets postpone for a while.
        if (fieldChar.getFieldType() != FieldType.FIELD_BIBLIOGRAPHY)
            return false;

        switch (mSaveFormat)
        {
            case SaveFormat.DOC:
            case SaveFormat.WORD_ML:
                return true;
            default:
                return false;
        }
    }

    private /*final*/ /*SaveFormat*/int mSaveFormat;
    private /*final*/ IWarningCallback mWarningCallback;
    private /*final*/ msStack<FieldChar> mFieldChars = new msStack<FieldChar>();

    /// <summary>
    /// Used to verify that form fields contain FormField node inside.
    /// </summary>
    private int mFormFieldValidationCounter = 0;
}

