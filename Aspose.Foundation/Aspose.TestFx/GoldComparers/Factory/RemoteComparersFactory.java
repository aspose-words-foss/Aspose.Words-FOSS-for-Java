// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/12/2016 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



class RemoteComparersFactory extends AbstractComparerFactory
{
    public /*override*/ AbstractGoldComparer getZipComparer() { return G_ZIP_COMPARER; }
    public /*override*/ AbstractGoldComparer getXpsComparer() { return G_XPS_COMPARER; }
    public /*override*/ AbstractGoldComparer getTextComparer() { return G_TXT_COMPARER; }
    public /*override*/ AbstractGoldComparer getEmfComparer() { return G_EMF_COMPARER; }
    public /*override*/ AbstractGoldComparer getMhtmlComparer() { return G_MHTML_COMPARER; }
    public /*override*/ AbstractGoldComparer getImageComparer() { return G_IMG_COMPARER; }
    public /*override*/ AbstractGoldComparer getWoffComparer() { return G_WOFF_COMPARER; }
    public /*override*/ AbstractGoldComparer getEpsComparer() { return G_EPS_COMPARER; }

    // FOSS

    private static /*final*/ AbstractGoldComparer G_ZIP_COMPARER = new RemoteComparer(RemoteComparer.DocumentType.ZIP);
    private static /*final*/ AbstractGoldComparer G_XPS_COMPARER = new RemoteComparer(RemoteComparer.DocumentType.XPS);
    private static /*final*/ AbstractGoldComparer G_TXT_COMPARER = new RemoteComparer(RemoteComparer.DocumentType.TXT);
    private static /*final*/ AbstractGoldComparer G_EMF_COMPARER = new RemoteComparer(RemoteComparer.DocumentType.EMF);
    private static /*final*/ AbstractGoldComparer G_MHTML_COMPARER = new RemoteComparer(RemoteComparer.DocumentType.MHTML);
    private static /*final*/ AbstractGoldComparer G_IMG_COMPARER = new RemoteComparer(RemoteComparer.DocumentType.IMG);
    private static /*final*/ AbstractGoldComparer G_WOFF_COMPARER = new RemoteComparer(RemoteComparer.DocumentType.WOFF);
    private static /*final*/ AbstractGoldComparer G_EPS_COMPARER = new RemoteComparer(RemoteComparer.DocumentType.EPS);
    // FOSS
}

