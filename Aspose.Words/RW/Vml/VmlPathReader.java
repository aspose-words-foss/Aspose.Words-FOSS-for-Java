// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/01/2007 by Vladimir Averkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.MathUtil;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.FormatterPal;
import java.util.ArrayList;



/// <summary>
/// Reads the 'path' attribute of a VML shape and converts it into the model representation
/// of path commands and path points.
/// 
/// VML description of path is here http://www.w3.org/TR/NOTE-VML#_Toc416858391
/// </summary>
class VmlPathReader
{
    /// <summary>
    /// Ctor. Parses the specified VML path. The results are returned in the <see cref="Points"/> and
    /// <see cref="PathInfos"/> properties.
    /// </summary>
    VmlPathReader(String path)
    {
        parsePath(path.toLowerCase());

        // Build final array of path infos.
        mPathInfos = new PathInfo[mCommands.size()];
        for (int i = 0; i < mCommands.size(); i++)
            mPathInfos[i] = mCommands.get(i);

        // Build final array of points.
        mPoints = new PathPoint[mCoords.size() / 2];
        for (int i = 0; i < mCoords.size() / 2; i++)
            mPoints[i] = new PathPoint(mCoords.get(2 * i), mCoords.get(2 * i + 1));
    }

    /// <summary>
    /// Parses the path into commands and coordinates arrays.
    /// </summary>
    private void parsePath(String path)
    {
        // VA State machine seems to be a best parsing method here.
        // RK Start in the command state because first char must be a command?
        /*PathParserState*/int state = PathParserState.COMMAND;

        for (int i = 0; i < path.length(); i++)
        {
            char c = path.charAt(i);
            if (Character.isDigit(c) || (c == '-'))
            {
                switch (state)
                {
                    case PathParserState.RESTART:
                        state = PathParserState.NUMBER;
                        break;
                    case PathParserState.COMMAND:
                        handlePathCommand();
                        state = PathParserState.NUMBER;
                        break;
                    case PathParserState.REF:
                    case PathParserState.NUMBER:
                        // Keep accumulating.
                        break;
                    default:
                        Debug.fail("Unexpected VML path parser state.");
                        break;
                }

                mBuilder.append(c);
            }
            else if (Character.isLowerCase(c))
            {
                switch (state)
                {
                    case PathParserState.RESTART:
                        handlePathValue();
                        break;
                    case PathParserState.COMMAND:
                        if (isPathCommand(mBuilder.toString()))
                            handlePathCommand();
                        break;
                    case PathParserState.REF:
                    case PathParserState.NUMBER:
                        handlePathValue();
                        break;
                    default:
                        Debug.fail("Unexpected VML path parser state.");
                        break;
                }

                state = PathParserState.COMMAND;
                mBuilder.append(c);
            }
            else if (c == ',')
            {
                switch (state)
                {
                    case PathParserState.COMMAND:
                        handlePathCommand();
                        break;
                    case PathParserState.REF:
                    case PathParserState.NUMBER:
                        // Do nothing, code after switch is executed.
                        break;
                    case PathParserState.RESTART:
                        // RK Do nothing?
                        break;
                    default:
                        Debug.fail("Unexpected VML path parser state.");
                        break;
                }

                handlePathValue();
                state = PathParserState.RESTART;
            }
            else if (c == '@')
            {
                switch (state)
                {
                    case PathParserState.COMMAND:
                        handlePathCommand();
                        break;
                    case PathParserState.REF:
                    case PathParserState.NUMBER:
                        handlePathValue();
                        break;
                    case PathParserState.RESTART:
                        // RK Do nothing?
                        break;
                    default:
                        Debug.fail("Unexpected VML path parser state.");
                        break;
                }

                state = PathParserState.REF;
                mBuilder.append(c);
            }

            // andrnosk: WORDSNET-5188 MSWord simply ignores white spaces, AW should do the same.
            // WORDSNET-13556 Line breaks are ignored as well.
            else if ((c == ' ') || (c == '\r') || (c == '\n'))
            {
                // andrnosk: Do nothing.
            }
            else
            {
                Debug.fail("Unexpected VML path parser state.");
            }
        }

        handlePathEnd(state);
    }

    /// <summary>
    /// Process the end statement of path whatever it is command or value.
    /// </summary>
    private void handlePathEnd(/*PathParserState*/int state)
    {
        // andrnosk: the path can end without 'e' in this case we should handle the last number as pathValue instead of pathCommand.
        switch (state)
        {
            case PathParserState.REF:
            case PathParserState.NUMBER:
                handlePathValue();
                // andrnosk: WORDSNET-5188(part2) - There is a path without 'e' (end) command at the end. AW was unable to handle this.
                // Made code resilient by explicitly adding 'e' at the end if path ends with value.
                mBuilder.append('e');
                break;
            case PathParserState.COMMAND:
            case PathParserState.RESTART:
                // Do nothing.
                break;
            default:
                Debug.fail("Unexpected VML path parser state.");
                break;
        }

        // RK Flush the last one. 
        handlePathCommand();
    }

    /// <summary>
    /// RK Called when the parser reaches an end of a command.
    /// </summary>
    private void handlePathCommand()
    {
        // VA Update the segment count of the previous command 
        // according to the accumulated number of coordinates.
        if (mCommands.size() > 0)
        {
            PathInfo prevPathInfo = mCommands.get(mCommands.size() - 1);
            mCommands.set(mCommands.size() - 1, new PathInfo(
                prevPathInfo.getPathType(),
                getSegmentCount(prevPathInfo, mCurCommandCoordCount / 2)));
        }

        String value = mBuilder.toString();
        mBuilder.setLength(0);

        // Create and add a new path info object. Segment count is not known at this stage yet.
        PathInfo pathInfo = new PathInfo(getPathType(value), 0);
        msArrayList.add(mCommands, pathInfo);

        mIsCurCommentRelative = isPathCommandRelative(value);
        mCurCommandCoordCount = 0;
    }

    /// <summary>
    /// Gets the count of segments given a command type and number of points this command has in VML.
    /// </summary>
    private static int getSegmentCount(PathInfo pathInfo, int pointCount)
    {
        switch (pathInfo.getPathType())
        {
            case PathType.CLOSE:
            {
                // This command seems to be a special case. It has no points, but segment count must be 1.
                return 1;
            }
            default:
            {
                int pointsPerSegment = pathInfo.getPointsPerSegment();
                return (pointsPerSegment > 0) ? pointCount / pointsPerSegment : 0;
            }
        }
    }
    
    /// <summary>
    /// Returns true if the string is a recognized VML path command.
    /// </summary>
    private static boolean isPathCommand(String value)
    {
        return (getPathType(value) != PathType.UNKNOWN);
    }

    /// <summary>
    /// Converts a VML string path command into model enum.
    /// </summary>
    private static /*PathType*/int getPathType(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"r"*/0: // relative line to
                // RK Both relative and absolute commands get converted to absolute.
                return PathType.LINE_TO;
            case /*"v"*/1: // relative curve to
                // RK Both relative and absolute commands get converted to absolute.
                return PathType.CURVE_TO;
            case /*"t"*/2: // relative move to
                // RK Both relative and absolute commands get converted to absolute.
                return PathType.MOVE_TO;
            default:
                return VmlEnum.vmlToPathType(value);
        }
    }

    /// <summary>
    /// Returns true if the VML path command is relative.
    /// </summary>
    private static boolean isPathCommandRelative(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"r"*/0:
            case /*"v"*/1:
            case /*"t"*/2:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// RK Called when the parser reaches an end of a value.
    /// </summary>
    private void handlePathValue()
    {
        String value = mBuilder.toString();
        mBuilder.setLength(0);

        PathValue pathValue = parsePathValue(value);

        // RK If there is an odd number of coordinates in the array, we are processing an Y value.
        boolean isYCoord = MathUtil.isOdd(mCurCommandCoordCount);

        if (mIsCurCommentRelative)
        {
            // RK We have to translate from relative coordinates to absolute because
            // the model follows DOC and RTF and they don't have support for relative coordinates.
            // RK It seems that we don't need to update last point when processing a relative command.
            if (isYCoord)
                pathValue = new PathValue(pathValue.getValue() + mLastY);
            else
                pathValue = new PathValue(pathValue.getValue() + mLastX);
        }
        else
        {
            if (isYCoord)
                mLastY = pathValue.getValue();
            else
                mLastX = pathValue.getValue();
        }

        msArrayList.add(mCoords, pathValue);
        mCurCommandCoordCount++;
    }
    
    /// <summary>
    /// Parses an integer value, optionally prefixed with the '@' character.
    /// </summary>
    static PathValue parsePathValue(String value)
    {
        if (StringUtil.hasChars(value))
        {
            boolean isFormula = false;
            if (msString.startsWith(value, "@", StringComparison.ORDINAL))
            {
                isFormula = true;
                value = msString.trimStart(value, '@');
            }
            return new PathValue(FormatterPal.parseInt(value), isFormula);
        }
        else
        {
            return new PathValue();
        }
    }

    /// <summary>
    /// Info how to connect points is available here after parsing.
    /// </summary>
    PathInfo[] getPathInfos() { return mPathInfos; }

    /// <summary>
    /// Points are available here after parsing.
    /// </summary>
    PathPoint[] getPoints() { return mPoints; }

    private /*enum*/ final class PathParserState
    {
        private PathParserState(){}
        
        public static final int RESTART = 0;
        public static final int COMMAND = 1;
        public static final int REF = 2;
        public static final int NUMBER = 3;

        public static final int length = 4;
    }

    /// <summary>
    /// The builder used for parsing the path string.
    /// </summary>
    private /*final*/ StringBuilder mBuilder = new StringBuilder();
    /// <summary>
    /// The commands extracted from the path. Items are <see cref="PathInfo"/> objects.
    /// </summary>
    private /*final*/ ArrayList<PathInfo> mCommands = new ArrayList<PathInfo>();
    /// <summary>
    /// The coordinates extracted from the path. Items are <see cref="PathPoint"/> objects.
    /// </summary>
    private /*final*/ ArrayList<PathValue> mCoords = new ArrayList<PathValue>();
    /// <summary>
    /// The final array of path infos in the model format. 
    /// </summary>
    private /*final*/ PathInfo[] mPathInfos;
    /// <summary>
    /// The final array of points in the model format.
    /// </summary>
    private /*final*/ PathPoint[] mPoints;
    /// <summary>
    /// Counts the number of coordinates (refs/numbers) in the current command.
    /// </summary>
    private int mCurCommandCoordCount;
    /// <summary>
    /// Signals if the last command read was relative.
    /// </summary>
    private boolean mIsCurCommentRelative;
    /// <summary>
    /// Used in recalculating relative coordinates.
    /// </summary>
    private int mLastX;
    private int mLastY;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"r",
		"v",
		"t"
	);

}

