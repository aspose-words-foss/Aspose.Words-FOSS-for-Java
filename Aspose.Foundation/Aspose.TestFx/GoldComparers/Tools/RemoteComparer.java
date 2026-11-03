// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2016 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



class RemoteComparer extends AbstractGoldComparer
{
    private /*final*/ /*DocumentType*/int mType;

    public RemoteComparer(/*DocumentType*/int type)
    {
        mType = type;
    }

    public /*override*/ void execute(ComparerParams comparerParams) throws Exception
    {
        StringToStringDictionary httpParameters = comparerParams.toParamMap();
        httpParameters.add("TYPE", Integer.toString(((int)mType)));
        HttpClientPal.doPost(G_SERVER_URL, httpParameters);
    }

    public /*override*/ void verifyConformance(ComparerParams comparerParams) throws Exception
    {
        StringToStringDictionary httpParameters = comparerParams.toParamMap();
        httpParameters.add("TYPE", Integer.toString(((int)mType)));
        httpParameters.add("CONF", "true");
        HttpClientPal.doPost(G_SERVER_URL, httpParameters);
    }

    public /*override*/ boolean executeForm(ComparerParams comparerParams)
    {
        // Always empty
        return false;
    }

    private static /*final*/ String G_SERVER_URL = "http://" + TestEnvironment.getRemoteComparerIP() +
                                                ":" + TestEnvironment.getRemoteComparerPort();
}

