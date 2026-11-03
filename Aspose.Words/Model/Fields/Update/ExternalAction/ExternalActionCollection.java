// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/11/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.EnumUtilPal;



/// <summary>
/// Represents a collection of <see cref="IExternalAction"/> instances distributed by field update stages.
/// </summary>
class ExternalActionCollection
{
    /// <summary>
    /// Appends the specified <see cref="IExternalAction"/> instance for the given field update stage
    /// to the collection. Returns a value indicating whether the external action was added. Its adding
    /// can be rejected by the collection if an instance of <see cref="IExternalAction"/> with the same
    /// <see cref="IExternalAction.Id"/> has been already added to the collection for the same field
    /// update stage.
    /// </summary>
    boolean add(/*FieldUpdateStage*/int stage, IExternalAction action)
    {
        ArrayList<IExternalAction> actions = getActions(stage);
        if (actions == null)
        {
            // Create on the first demand.
            actions = new ArrayList<IExternalAction>();
            setActions(stage, actions);
        }
        else
        {
            for (IExternalAction actionToTest : actions)
            {
                // Do not allow adding of an external action with the same id.
                if (action.getId() == actionToTest.getId())
                    return false;
            }
        }

        msArrayList.add(actions, action);
        return true;
    }

    /// <summary>
    /// Performs all external actions for the specified field update stage in the order of their appearance.
    /// </summary>
    void perform(/*FieldUpdateStage*/int stage) throws Exception
    {
        ArrayList<IExternalAction> actions = getActions(stage);
        if (actions == null)
            return;

        for (IExternalAction action : actions)
            action.perform();
    }

    private ArrayList<IExternalAction> getActions(/*FieldUpdateStage*/int stage)
    {
        return (ArrayList<IExternalAction>)mActionsByStages[(int)stage];
    }

    private void setActions(/*FieldUpdateStage*/int stage, ArrayList<IExternalAction> externalActions)
    {
        mActionsByStages[(int)stage] = externalActions;
    }

    private /*final*/ Object[] mActionsByStages = new Object[G_FIELD_UPDATE_STAGE_COUNT];

    private static /*final*/ int G_FIELD_UPDATE_STAGE_COUNT =
        EnumUtilPal.getEffectiveArrayLength((3));
}

