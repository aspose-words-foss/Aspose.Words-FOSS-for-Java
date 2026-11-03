// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/10/2019 by Alexander Sevidov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a list of constants, which are used in a VbaProject import/export.
/// </summary>
class VbaRecord
{
    //>>>>>>>> #region  VbaModule
    static final /*ushort*/short MODULE_NAME = (short) 0x19;
    static final /*ushort*/short MODULE_NAME_UNICODE = (short) 0x47;
    static final /*ushort*/short STREAM_NAME = (short) 0x1A;
    static final /*ushort*/short STREAM_NAME_UNICODE = (short) 0x32;
    static final /*ushort*/short DOC_STRING = (short) 0x1C;
    static final /*ushort*/short DOC_STRING_UNICODE = (short) 0x48;

    static final /*ushort*/short OFFSET = (short) 0x31;
    static final /*ushort*/short MODULE_HELP_CONTEXT = (short) 0x1e;

    static final /*ushort*/short MODULE_COOKIE_RECORD_ID = (short) 0x2c;

    static final /*ushort*/short PROCEDURAL_MODULE = (short) 0x0021;
    static final /*ushort*/short NON_PROCEDURAL_MODULE = (short) 0x0022;

    static final /*ushort*/short TERMINATOR = (short) 0x2b;
    //<<<<<<<< #endregion 

    //>>>>>>>> #region  VbaProject
    static final /*ushort*/short SYS_KIND = (short) 0x01;
    static final /*ushort*/short LCID = (short) 0x02;
    static final /*ushort*/short LCID_INVOKE = (short) 0x14;
    static final /*ushort*/short CODE_PAGE = (short) 0x03;
    
    static final /*ushort*/short NAME = (short) 0x04;
    static final /*ushort*/short DESCRIPTION = (short) 0x05;
    static final /*ushort*/short DESCRIPTION_UNICODE = (short) 0x40;
    static final /*ushort*/short HELP_FILE_PATH_1 = (short) 0x06;

    static final /*ushort*/short HELP_FILE_PATH_2 = (short) 0x3d;
    static final /*ushort*/short HELP_FILE_PATH_2_OBSOLETE = (short) 0x49;
    
    static final /*ushort*/short PROJECT_HELP_CONTEXT = (short) 0x07;
    static final /*ushort*/short LIB_FLAGS = (short) 0x08;
    static final /*ushort*/short PROJECT_VERSION = (short) 0x09;

    static final /*ushort*/short CONSTANTS = (short) 0x0c;
    static final /*ushort*/short CONSTANTS_UNICODE = (short) 0x3c;

    static final /*ushort*/short MODULES_COUNT = (short) 0x0f;

    static final /*ushort*/short PROJECT_COOKIE_RECORD_ID = (short) 0x13;
    static final /*ushort*/short DIR_TERMINATOR = (short) 0x0010;
    //<<<<<<<< #endregion 

    //>>>>>>>> #region  VbaReference
    static final /*ushort*/short REFERENCE_NAME = (short) 0x16;
    static final /*ushort*/short REFERENCE_NAME_UNICODE = (short) 0x3e;
    static final /*ushort*/short REFERENCE_CONTROL_ID = (short) 0x2f;
    static final /*ushort*/short REFERENCE_RESERVED = (short) 0x0030; 
    //<<<<<<<< #endregion 

    static final /*ushort*/short COOKIE_RECORD = (short) 0xFFFF;

    //>>>>>>>> #region  String constants
    static final String PROJECT_STREAM_NAME = "PROJECT";
    static final String DIR_STREAM_NAME = "dir";
    static final String CACHE_STREAM_NAME = "_VBA_PROJECT";
    static final String VBA_STORAGE_NAME = "VBA";

    static final String PROTECTION_STATE = "CMG";
    static final String PASSWORD = "DPB";
    static final String VISIBILITY_STATE = "GC";
    static final String ID = "ID";

    //<<<<<<<< #endregion 
}

