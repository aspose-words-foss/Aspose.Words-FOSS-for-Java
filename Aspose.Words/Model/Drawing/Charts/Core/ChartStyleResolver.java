// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/01/2025 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.Debug;
import java.util.HashMap;
import java.util.Map;
import com.aspose.words.ref.RefInt;


/// <summary>
/// Contains maps related to chart styles.
/// </summary>
/*static*/ class ChartStyleResolver
{
	/* Simulation of static class by using private constructor */
	private ChartStyleResolver()
	{}

    /// <summary>
    /// Gets a style ID for the specified chart type and chart style.
    /// </summary>
    static int getStyleId(/*ChartType*/int chartType, /*ChartStyle*/int chartStyle)
    {
        int mapKey = getKey(chartType, chartStyle);
        int styleId = 0;
        RefInt referenceToStyleId = new RefInt(styleId);
        boolean outRefCondition0 = !msDictionary.tryGetValue(G_CHART_TYPE_AND_STYLE_TO_STYLE_ID_MAP, mapKey, /*out*/ referenceToStyleId);
        styleId = referenceToStyleId.get();
        if (outRefCondition0)
            styleId = G_CHART_STYLE_TO_DEFAULT_STYLE_ID_MAP.get(chartStyle);

        Debug.assertTrue(styleId > 0);
        return styleId;
    }

    /// <summary>
    /// Gets a <see cref="ChartStyle"/> item which the style with the specified ID represents.
    /// </summary>
    static /*ChartStyle*/int getChartStyle(int styleId)
    {
        /*ChartStyle*/int chartStyle = 0;

        RefInt referenceToChartStyle = new RefInt(chartStyle);
        boolean outRefCondition1 = msDictionary.tryGetValue(G_STYLE_ID_TO_CHART_STYLE_MAP, styleId, /*out*/ referenceToChartStyle);
        chartStyle = referenceToChartStyle.get();
        if (outRefCondition1)
            return chartStyle;
        else
            return ChartStyle.NORMAL;
    }

    /// <summary>
    /// Creates and fills a map of a chart type and chart style to a style ID.
    /// </summary>
    private static HashMap<Integer, Integer> getStyleIdMap()
    {
        // MS Word 2019 allows specifying the following styles in UI.
        // The commented lines relate to styles that are currently not supported in Aspose.Words.

        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

        msDictionary.add(map, getKey(ChartType.AREA, ChartStyle.NORMAL), 276); // Style 1
        //map.Add(GetKey(ChartType.Area, ChartStyle.), 277); // Style 2
        msDictionary.add(map, getKey(ChartType.AREA, ChartStyle.SHADED), 278); // Style 3
        msDictionary.add(map, getKey(ChartType.AREA, ChartStyle.GREY), 279); // Style 4
        msDictionary.add(map, getKey(ChartType.AREA, ChartStyle.MUTED), 280); // Style 5
        msDictionary.add(map, getKey(ChartType.AREA, ChartStyle.SATURATED), 281); // Style 6
        msDictionary.add(map, getKey(ChartType.AREA, ChartStyle.SHADED_PLOT), 282); // Style 7
        msDictionary.add(map, getKey(ChartType.AREA, ChartStyle.BLACK), 283); // Style 8
        //map.Add(GetKey(ChartType.Area, ChartStyle.), 284); // Style 9
        msDictionary.add(map, getKey(ChartType.AREA, ChartStyle.SHADOWED), 346); // Style 10

        msDictionary.add(map, getKey(ChartType.AREA_3_D, ChartStyle.NORMAL), 276); // Style 1
        msDictionary.add(map, getKey(ChartType.AREA_3_D, ChartStyle.TRANSPARENT_2), 312); // Style 2
        msDictionary.add(map, getKey(ChartType.AREA_3_D, ChartStyle.SHADED), 313); // Style 3
        msDictionary.add(map, getKey(ChartType.AREA_3_D, ChartStyle.GREY), 314); // Style 4
        msDictionary.add(map, getKey(ChartType.AREA_3_D, ChartStyle.MUTED), 280); // Style 5
        msDictionary.add(map, getKey(ChartType.AREA_3_D, ChartStyle.SATURATED), 281); // Style 6
        msDictionary.add(map, getKey(ChartType.AREA_3_D, ChartStyle.SHADED_PLOT), 282); // Style 7
        msDictionary.add(map, getKey(ChartType.AREA_3_D, ChartStyle.BLACK), 315); // Style 8
        msDictionary.add(map, getKey(ChartType.AREA_3_D, ChartStyle.SHADOWED), 350); // Style 9

        msDictionary.add(map, getKey(ChartType.BAR, ChartStyle.NORMAL), 216); // Style 1
        msDictionary.add(map, getKey(ChartType.BAR, ChartStyle.SHADED), 217); // Style 2
        msDictionary.add(map, getKey(ChartType.BAR, ChartStyle.GREY), 218); // Style 3
        msDictionary.add(map, getKey(ChartType.BAR, ChartStyle.MUTED), 219); // Style 4
        msDictionary.add(map, getKey(ChartType.BAR, ChartStyle.SATURATED), 220); // Style 5
        msDictionary.add(map, getKey(ChartType.BAR, ChartStyle.SHADED_PLOT), 221); // Style 6
        msDictionary.add(map, getKey(ChartType.BAR, ChartStyle.BLACK), 222); // Style 7
        msDictionary.add(map, getKey(ChartType.BAR, ChartStyle.GRADIENT), 223); // Style 8
        //map.Add(GetKey(ChartType.Bar, ChartStyle.), 224); // Style 9
        msDictionary.add(map, getKey(ChartType.BAR, ChartStyle.FLAT), 225); // Style 10
        msDictionary.add(map, getKey(ChartType.BAR, ChartStyle.OUTLINE_BLACK), 339); // Style 11
        msDictionary.add(map, getKey(ChartType.BAR, ChartStyle.SHADOWED), 341); // Style 12

        msDictionary.add(map, getKey(ChartType.BAR_STACKED, ChartStyle.NORMAL), 297); // Style 1
        msDictionary.add(map, getKey(ChartType.BAR_STACKED, ChartStyle.ORIGINAL), 298); // Style 2
        msDictionary.add(map, getKey(ChartType.BAR_STACKED, ChartStyle.SHADED), 299); // Style 3
        msDictionary.add(map, getKey(ChartType.BAR_STACKED, ChartStyle.GREY), 300); // Style 4
        msDictionary.add(map, getKey(ChartType.BAR_STACKED, ChartStyle.MUTED), 301); // Style 5
        msDictionary.add(map, getKey(ChartType.BAR_STACKED, ChartStyle.SATURATED), 302); // Style 6
        msDictionary.add(map, getKey(ChartType.BAR_STACKED, ChartStyle.SHADED_PLOT), 303); // Style 7
        msDictionary.add(map, getKey(ChartType.BAR_STACKED, ChartStyle.BLACK), 304); // Style 8
        //map.Add(GetKey(ChartType.BarStacked, ChartStyle.), 305); // Style 9
        msDictionary.add(map, getKey(ChartType.BAR_STACKED, ChartStyle.FLAT), 306); // Style 10
        msDictionary.add(map, getKey(ChartType.BAR_STACKED, ChartStyle.SHADOWED), 348); // Style 11

        msDictionary.add(map, getKey(ChartType.BAR_3_D, ChartStyle.NORMAL), 286); // Style 1
        msDictionary.add(map, getKey(ChartType.BAR_3_D, ChartStyle.SHADED), 287); // Style 2
        msDictionary.add(map, getKey(ChartType.BAR_3_D, ChartStyle.GREY), 288); // Style 3
        msDictionary.add(map, getKey(ChartType.BAR_3_D, ChartStyle.MUTED), 289); // Style 4
        msDictionary.add(map, getKey(ChartType.BAR_3_D, ChartStyle.SATURATED), 290); // Style 5
        //map.Add(GetKey(ChartType.Bar3D, ChartStyle.), 291); // Style 6
        msDictionary.add(map, getKey(ChartType.BAR_3_D, ChartStyle.TRANSPARENT_2), 292); // Style 7
        msDictionary.add(map, getKey(ChartType.BAR_3_D, ChartStyle.GRADIENT), 349); // Style 8
        msDictionary.add(map, getKey(ChartType.BAR_3_D, ChartStyle.BLACK), 294); // Style 9
        msDictionary.add(map, getKey(ChartType.BAR_3_D, ChartStyle.FLAT), 296); // Style 10
        //map.Add(GetKey(ChartType.Bar3D, ChartStyle.), 347); // Style 11

        msDictionary.add(map, getKey(ChartType.BAR_3_D_STACKED, ChartStyle.NORMAL), 286); // Style 1
        msDictionary.add(map, getKey(ChartType.BAR_3_D_STACKED, ChartStyle.SHADED), 299); // Style 2
        //map.Add(GetKey(ChartType.Bar3DStacked, ChartStyle.), 310); // Style 3
        msDictionary.add(map, getKey(ChartType.BAR_3_D_STACKED, ChartStyle.MUTED), 289); // Style 4
        msDictionary.add(map, getKey(ChartType.BAR_3_D_STACKED, ChartStyle.SATURATED), 290); // Style 5
        msDictionary.add(map, getKey(ChartType.BAR_3_D_STACKED, ChartStyle.BLACK), 294); // Style 6
        msDictionary.add(map, getKey(ChartType.BAR_3_D_STACKED, ChartStyle.FLAT), 296); // Style 7
        //map.Add(GetKey(ChartType.Bar3DStacked, ChartStyle.), 347); // Style 8

        msDictionary.add(map, getKey(ChartType.BUBBLE, ChartStyle.NORMAL), 269); // Style 1
        msDictionary.add(map, getKey(ChartType.BUBBLE, ChartStyle.SHADED), 270); // Style 2
        msDictionary.add(map, getKey(ChartType.BUBBLE, ChartStyle.OUTLINE_BLACK), 271); // Style 3
        msDictionary.add(map, getKey(ChartType.BUBBLE, ChartStyle.GREY), 272); // Style 4
        msDictionary.add(map, getKey(ChartType.BUBBLE, ChartStyle.MUTED), 246); // Style 5
        msDictionary.add(map, getKey(ChartType.BUBBLE, ChartStyle.SATURATED), 242); // Style 6
        msDictionary.add(map, getKey(ChartType.BUBBLE, ChartStyle.SHADED_PLOT), 273); // Style 7
        msDictionary.add(map, getKey(ChartType.BUBBLE, ChartStyle.BLACK), 248); // Style 8
        //map.Add(GetKey(ChartType.Bubble, ChartStyle.), 274); // Style 9
        msDictionary.add(map, getKey(ChartType.BUBBLE, ChartStyle.BLUE), 275); // Style 10
        msDictionary.add(map, getKey(ChartType.BUBBLE, ChartStyle.SHADOWED), 343); // Style 11

        msDictionary.add(map, getKey(ChartType.BUBBLE_3_D, ChartStyle.NORMAL), 269); // Style 1
        msDictionary.add(map, getKey(ChartType.BUBBLE_3_D, ChartStyle.SHADED), 270); // Style 2
        msDictionary.add(map, getKey(ChartType.BUBBLE_3_D, ChartStyle.GREY), 272); // Style 3
        msDictionary.add(map, getKey(ChartType.BUBBLE_3_D, ChartStyle.MUTED), 246); // Style 4
        msDictionary.add(map, getKey(ChartType.BUBBLE_3_D, ChartStyle.SATURATED), 242); // Style 5
        msDictionary.add(map, getKey(ChartType.BUBBLE_3_D, ChartStyle.SHADED_PLOT), 273); // Style 6
        msDictionary.add(map, getKey(ChartType.BUBBLE_3_D, ChartStyle.BLACK), 248); // Style 7
        msDictionary.add(map, getKey(ChartType.BUBBLE_3_D, ChartStyle.BLUE), 275); // Style 8
        msDictionary.add(map, getKey(ChartType.BUBBLE_3_D, ChartStyle.SHADOWED), 343); // Style 9

        msDictionary.add(map, getKey(ChartType.COLUMN, ChartStyle.NORMAL), 201); // Style 1
        msDictionary.add(map, getKey(ChartType.COLUMN, ChartStyle.ORIGINAL), 202); // Style 2
        msDictionary.add(map, getKey(ChartType.COLUMN, ChartStyle.SHADED), 203); // Style 3
        msDictionary.add(map, getKey(ChartType.COLUMN, ChartStyle.GREY), 205); // Style 4
        msDictionary.add(map, getKey(ChartType.COLUMN, ChartStyle.MUTED), 206); // Style 5
        msDictionary.add(map, getKey(ChartType.COLUMN, ChartStyle.SATURATED), 207); // Style 6
        msDictionary.add(map, getKey(ChartType.COLUMN, ChartStyle.SHADED_PLOT), 208); // Style 7
        msDictionary.add(map, getKey(ChartType.COLUMN, ChartStyle.BLACK), 209); // Style 8
        msDictionary.add(map, getKey(ChartType.COLUMN, ChartStyle.GRADIENT), 210); // Style 9
        msDictionary.add(map, getKey(ChartType.COLUMN, ChartStyle.OUTLINE), 211); // Style 10
        msDictionary.add(map, getKey(ChartType.COLUMN, ChartStyle.FLAT), 212); // Style 11
        msDictionary.add(map, getKey(ChartType.COLUMN, ChartStyle.OUTLINE_BLACK), 213); // Style 12
        msDictionary.add(map, getKey(ChartType.COLUMN, ChartStyle.TRANSPARENT_1), 215); // Style 13
        msDictionary.add(map, getKey(ChartType.COLUMN, ChartStyle.SHADOWED), 340); // Style 14

        // Column3D has the same styles as Bar3D in MS Word except the style 8.
        msDictionary.add(map, getKey(ChartType.COLUMN_3_D, ChartStyle.GRADIENT), 293); // Style 8

        msDictionary.add(map, getKey(COMBO_CHART, ChartStyle.NORMAL), 201); // Style 1
        msDictionary.add(map, getKey(COMBO_CHART, ChartStyle.SHADED), 323); // Style 2
        msDictionary.add(map, getKey(COMBO_CHART, ChartStyle.MUTED), 325); // Style 3
        msDictionary.add(map, getKey(COMBO_CHART, ChartStyle.SATURATED), 326); // Style 4
        msDictionary.add(map, getKey(COMBO_CHART, ChartStyle.SHADED_PLOT), 221); // Style 5
        msDictionary.add(map, getKey(COMBO_CHART, ChartStyle.BLACK), 328); // Style 6
        msDictionary.add(map, getKey(COMBO_CHART, ChartStyle.FLAT), 225); // Style 7
        msDictionary.add(map, getKey(COMBO_CHART, ChartStyle.SHADOWED), 352); // Style 8

        msDictionary.add(map, getKey(ChartType.DOUGHNUT, ChartStyle.NORMAL), 251); // Style 1
        msDictionary.add(map, getKey(ChartType.DOUGHNUT, ChartStyle.SHADED), 252); // Style 2
        msDictionary.add(map, getKey(ChartType.DOUGHNUT, ChartStyle.GREY), 253); // Style 3
        msDictionary.add(map, getKey(ChartType.DOUGHNUT, ChartStyle.MUTED), 254); // Style 4
        msDictionary.add(map, getKey(ChartType.DOUGHNUT, ChartStyle.SATURATED), 255); // Style 5
        msDictionary.add(map, getKey(ChartType.DOUGHNUT, ChartStyle.SHADED_PLOT), 256); // Style 6
        msDictionary.add(map, getKey(ChartType.DOUGHNUT, ChartStyle.BLACK), 257); // Style 7
        //map.Add(GetKey(ChartType.Doughnut, ChartStyle.), 258); // Style 8
        msDictionary.add(map, getKey(ChartType.DOUGHNUT, ChartStyle.BLUE), 260); // Style 9
        //map.Add(GetKey(ChartType.Doughnut, ChartStyle.), 261); // Style 10

        msDictionary.add(map, getKey(ChartType.STOCK, ChartStyle.NORMAL), 322); // Style 1
        msDictionary.add(map, getKey(ChartType.STOCK, ChartStyle.SHADED), 323); // Style 2
        msDictionary.add(map, getKey(ChartType.STOCK, ChartStyle.GREY), 324); // Style 3
        msDictionary.add(map, getKey(ChartType.STOCK, ChartStyle.MUTED), 325); // Style 4
        msDictionary.add(map, getKey(ChartType.STOCK, ChartStyle.SATURATED), 326); // Style 5
        msDictionary.add(map, getKey(ChartType.STOCK, ChartStyle.SHADED_PLOT), 327); // Style 6
        msDictionary.add(map, getKey(ChartType.STOCK, ChartStyle.BLACK), 328); // Style 7
        //map.Add(GetKey(ChartType.Stock, ChartStyle.), 329); // Style 8
        msDictionary.add(map, getKey(ChartType.STOCK, ChartStyle.BLUE), 330); // Style 9
        //map.Add(GetKey(ChartType.Stock, ChartStyle.), 331); // Style 10
        msDictionary.add(map, getKey(ChartType.STOCK, ChartStyle.SHADOWED), 352); // Style 11

        msDictionary.add(map, getKey(ChartType.LINE, ChartStyle.NORMAL), 227); // Style 1
        msDictionary.add(map, getKey(ChartType.LINE, ChartStyle.GREY), 228); // Style 2
        msDictionary.add(map, getKey(ChartType.LINE, ChartStyle.MUTED), 230); // Style 3
        msDictionary.add(map, getKey(ChartType.LINE, ChartStyle.SATURATED), 231); // Style 4
        msDictionary.add(map, getKey(ChartType.LINE, ChartStyle.SHADED_PLOT), 232); // Style 5
        msDictionary.add(map, getKey(ChartType.LINE, ChartStyle.BLACK), 233); // Style 6
        //map.Add(GetKey(ChartType.Line, ChartStyle.), 234); // Style 7
        msDictionary.add(map, getKey(ChartType.LINE, ChartStyle.FLAT), 235); // Style 8
        msDictionary.add(map, getKey(ChartType.LINE, ChartStyle.OUTLINE_BLACK), 236); // Style 9
        msDictionary.add(map, getKey(ChartType.LINE, ChartStyle.SHADED), 237); // Style 10
        msDictionary.add(map, getKey(ChartType.LINE, ChartStyle.ORIGINAL), 239); // Style 11
        //map.Add(GetKey(ChartType.Line, ChartStyle.), 332); // Style 12
        msDictionary.add(map, getKey(ChartType.LINE, ChartStyle.SHADOWED), 342); // Style 13

        msDictionary.add(map, getKey(ChartType.LINE_3_D, ChartStyle.NORMAL), 307); // Style 1
        msDictionary.add(map, getKey(ChartType.LINE_3_D, ChartStyle.MUTED), 311); // Style 2
        msDictionary.add(map, getKey(ChartType.LINE_3_D, ChartStyle.SATURATED), 308); // Style 3
        msDictionary.add(map, getKey(ChartType.LINE_3_D, ChartStyle.BLACK), 309); // Style 4

        msDictionary.add(map, getKey(ChartType.PIE, ChartStyle.NORMAL), 251); // Style 1
        msDictionary.add(map, getKey(ChartType.PIE, ChartStyle.SHADED), 252); // Style 2
        msDictionary.add(map, getKey(ChartType.PIE, ChartStyle.GREY), 253); // Style 3
        msDictionary.add(map, getKey(ChartType.PIE, ChartStyle.MUTED), 254); // Style 4
        msDictionary.add(map, getKey(ChartType.PIE, ChartStyle.SATURATED), 255); // Style 5
        msDictionary.add(map, getKey(ChartType.PIE, ChartStyle.SHADED_PLOT), 256); // Style 6
        msDictionary.add(map, getKey(ChartType.PIE, ChartStyle.BLACK), 257); // Style 7
        //map.Add(GetKey(ChartType.Pie, ChartStyle.), 258); // Style 8
        msDictionary.add(map, getKey(ChartType.PIE, ChartStyle.ORIGINAL), 259); // Style 9
        msDictionary.add(map, getKey(ChartType.PIE, ChartStyle.BLUE), 260); // Style 10
        //map.Add(GetKey(ChartType.Pie, ChartStyle.), 261); // Style 11
        msDictionary.add(map, getKey(ChartType.PIE, ChartStyle.SHADOWED), 344); // Style 12

        msDictionary.add(map, getKey(ChartType.PIE_OF_PIE, ChartStyle.NORMAL), 333); // Style 1
        msDictionary.add(map, getKey(ChartType.PIE_OF_PIE, ChartStyle.SHADED), 252); // Style 2
        msDictionary.add(map, getKey(ChartType.PIE_OF_PIE, ChartStyle.GREY), 334); // Style 3
        msDictionary.add(map, getKey(ChartType.PIE_OF_PIE, ChartStyle.MUTED), 335); // Style 4
        msDictionary.add(map, getKey(ChartType.PIE_OF_PIE, ChartStyle.SATURATED), 336); // Style 5
        msDictionary.add(map, getKey(ChartType.PIE_OF_PIE, ChartStyle.SHADED_PLOT), 337); // Style 6
        msDictionary.add(map, getKey(ChartType.PIE_OF_PIE, ChartStyle.BLACK), 338); // Style 7
        //map.Add(GetKey(ChartType.PieOfPie, ChartStyle.), 258); // Style 8
        msDictionary.add(map, getKey(ChartType.PIE_OF_PIE, ChartStyle.ORIGINAL), 259); // Style 9
        msDictionary.add(map, getKey(ChartType.PIE_OF_PIE, ChartStyle.BLUE), 260); // Style 10
        //map.Add(GetKey(ChartType.PieOfPie, ChartStyle.), 261); // Style 11
        msDictionary.add(map, getKey(ChartType.PIE_OF_PIE, ChartStyle.SHADOWED), 344); // Style 12

        msDictionary.add(map, getKey(ChartType.PIE_3_D, ChartStyle.NORMAL), 262); // Style 1
        //map.Add(GetKey(ChartType.Pie3D, ChartStyle.), 263); // Style 2
        msDictionary.add(map, getKey(ChartType.PIE_3_D, ChartStyle.GREY), 264); // Style 3
        msDictionary.add(map, getKey(ChartType.PIE_3_D, ChartStyle.MUTED), 265); // Style 4
        msDictionary.add(map, getKey(ChartType.PIE_3_D, ChartStyle.SATURATED), 266); // Style 5
        msDictionary.add(map, getKey(ChartType.PIE_3_D, ChartStyle.SHADED_PLOT), 267); // Style 6
        msDictionary.add(map, getKey(ChartType.PIE_3_D, ChartStyle.BLACK), 268); // Style 7
        msDictionary.add(map, getKey(ChartType.PIE_3_D, ChartStyle.ORIGINAL), 259); // Style 8
        //map.Add(GetKey(ChartType.Pie3D, ChartStyle.), 261); // Style 9
        msDictionary.add(map, getKey(ChartType.PIE_3_D, ChartStyle.SHADOWED), 345); // Style 10

        msDictionary.add(map, getKey(ChartType.RADAR, ChartStyle.NORMAL), 317); // Style 1
        msDictionary.add(map, getKey(ChartType.RADAR, ChartStyle.SHADED), 318); // Style 2
        msDictionary.add(map, getKey(ChartType.RADAR, ChartStyle.MUTED), 206); // Style 3
        msDictionary.add(map, getKey(ChartType.RADAR, ChartStyle.TRANSPARENT_2), 319); // Style 4
        //map.Add(GetKey(ChartType.Radar, ChartStyle.), 320); // Style 5
        msDictionary.add(map, getKey(ChartType.RADAR, ChartStyle.SATURATED), 207); // Style 6
        msDictionary.add(map, getKey(ChartType.RADAR, ChartStyle.OUTLINE_BLACK), 321); // Style 7
        msDictionary.add(map, getKey(ChartType.RADAR, ChartStyle.SHADOWED), 351); // Style 8

        msDictionary.add(map, getKey(ChartType.SCATTER, ChartStyle.NORMAL), 240); // Style 1
        msDictionary.add(map, getKey(ChartType.SCATTER, ChartStyle.ORIGINAL), 241); // Style 2
        msDictionary.add(map, getKey(ChartType.SCATTER, ChartStyle.SATURATED), 242); // Style 3
        //map.Add(GetKey(ChartType.Scatter, ChartStyle.), 243); // Style 4
        //map.Add(GetKey(ChartType.Scatter, ChartStyle.), 244); // Style 5
        //map.Add(GetKey(ChartType.Scatter, ChartStyle.), 245); // Style 6
        msDictionary.add(map, getKey(ChartType.SCATTER, ChartStyle.MUTED), 246); // Style 7
        msDictionary.add(map, getKey(ChartType.SCATTER, ChartStyle.BLUE), 247); // Style 8
        msDictionary.add(map, getKey(ChartType.SCATTER, ChartStyle.BLACK), 248); // Style 9
        //map.Add(GetKey(ChartType.Scatter, ChartStyle.), 249); // Style 10
        msDictionary.add(map, getKey(ChartType.SCATTER, ChartStyle.SHADED_PLOT), 250); // Style 11
        msDictionary.add(map, getKey(ChartType.SCATTER, ChartStyle.SHADOWED), 343); // Style 12

        msDictionary.add(map, getKey(ChartType.BOX_AND_WHISKER, ChartStyle.NORMAL), 406); // Style 1
        msDictionary.add(map, getKey(ChartType.BOX_AND_WHISKER, ChartStyle.SHADED), 407); // Style 2
        msDictionary.add(map, getKey(ChartType.BOX_AND_WHISKER, ChartStyle.GREY), 408); // Style 3
        msDictionary.add(map, getKey(ChartType.BOX_AND_WHISKER, ChartStyle.BLACK), 409); // Style 4
        //map.Add(GetKey(ChartType.BoxAndWhisker, ChartStyle.), 373); // Style 5
        msDictionary.add(map, getKey(ChartType.BOX_AND_WHISKER, ChartStyle.FLAT), 374); // Style 6

        msDictionary.add(map, getKey(ChartType.FUNNEL, ChartStyle.NORMAL), 419); // Style 1
        msDictionary.add(map, getKey(ChartType.FUNNEL, ChartStyle.SHADED), 423); // Style 2
        msDictionary.add(map, getKey(ChartType.FUNNEL, ChartStyle.GREY), 424); // Style 3
        msDictionary.add(map, getKey(ChartType.FUNNEL, ChartStyle.MUTED), 425); // Style 4
        msDictionary.add(map, getKey(ChartType.FUNNEL, ChartStyle.SATURATED), 426); // Style 5
        msDictionary.add(map, getKey(ChartType.FUNNEL, ChartStyle.BLACK), 427); // Style 6
        //map.Add(GetKey(ChartType.Funnel, ChartStyle.), 428); // Style 7
        msDictionary.add(map, getKey(ChartType.FUNNEL, ChartStyle.BLUE), 429); // Style 8
        //map.Add(GetKey(ChartType.Funnel, ChartStyle.), 430); // Style 9

        msDictionary.add(map, getKey(ChartType.HISTOGRAM, ChartStyle.NORMAL), 366); // Style 1
        msDictionary.add(map, getKey(ChartType.HISTOGRAM, ChartStyle.SHADED), 367); // Style 2
        msDictionary.add(map, getKey(ChartType.HISTOGRAM, ChartStyle.GREY), 368); // Style 3
        msDictionary.add(map, getKey(ChartType.HISTOGRAM, ChartStyle.MUTED), 369); // Style 4
        msDictionary.add(map, getKey(ChartType.HISTOGRAM, ChartStyle.BLACK), 370); // Style 5
        msDictionary.add(map, getKey(ChartType.HISTOGRAM, ChartStyle.BLUE), 371); // Style 6

        msDictionary.add(map, getKey(ChartType.SUNBURST, ChartStyle.NORMAL), 381); // Style 1
        msDictionary.add(map, getKey(ChartType.SUNBURST, ChartStyle.SHADED), 382); // Style 2
        msDictionary.add(map, getKey(ChartType.SUNBURST, ChartStyle.GREY), 383); // Style 3
        msDictionary.add(map, getKey(ChartType.SUNBURST, ChartStyle.MUTED), 384); // Style 4
        msDictionary.add(map, getKey(ChartType.SUNBURST, ChartStyle.SATURATED), 385); // Style 5
        msDictionary.add(map, getKey(ChartType.SUNBURST, ChartStyle.SHADED_PLOT), 386); // Style 6
        msDictionary.add(map, getKey(ChartType.SUNBURST, ChartStyle.BLACK), 387); // Style 7
        //map.Add(GetKey(ChartType.Sunburst, ChartStyle.), 388); // Style 8

        msDictionary.add(map, getKey(ChartType.TREEMAP, ChartStyle.NORMAL), 410); // Style 1
        msDictionary.add(map, getKey(ChartType.TREEMAP, ChartStyle.GREY), 411); // Style 2
        msDictionary.add(map, getKey(ChartType.TREEMAP, ChartStyle.MUTED), 412); // Style 3
        msDictionary.add(map, getKey(ChartType.TREEMAP, ChartStyle.SATURATED), 413); // Style 4
        msDictionary.add(map, getKey(ChartType.TREEMAP, ChartStyle.SHADED_PLOT), 414); // Style 5
        msDictionary.add(map, getKey(ChartType.TREEMAP, ChartStyle.BLACK), 415); // Style 6
        msDictionary.add(map, getKey(ChartType.TREEMAP, ChartStyle.ORIGINAL), 416); // Style 7
        //map.Add(GetKey(ChartType.Treemap, ChartStyle.), 417); // Style 8
        msDictionary.add(map, getKey(ChartType.TREEMAP, ChartStyle.SHADED), 418); // Style 9

        msDictionary.add(map, getKey(ChartType.WATERFALL, ChartStyle.NORMAL), 395); // Style 1
        msDictionary.add(map, getKey(ChartType.WATERFALL, ChartStyle.SHADED), 396); // Style 2
        msDictionary.add(map, getKey(ChartType.WATERFALL, ChartStyle.GREY), 397); // Style 3
        msDictionary.add(map, getKey(ChartType.WATERFALL, ChartStyle.MUTED), 398); // Style 4
        msDictionary.add(map, getKey(ChartType.WATERFALL, ChartStyle.SATURATED), 399); // Style 5
        //map.Add(GetKey(ChartType.Waterfall, ChartStyle.), 400); // Style 6
        msDictionary.add(map, getKey(ChartType.WATERFALL, ChartStyle.BLACK), 372); // Style 7
        msDictionary.add(map, getKey(ChartType.WATERFALL, ChartStyle.FLAT), 389); // Style 8

        // Special definitions for styles that are not available in MS Word to replace the default style defined
        // in GetDefaultStyleIdMap.
        msDictionary.add(map, getKey(ChartType.BOX_AND_WHISKER, ChartStyle.SATURATED), 3851);
        msDictionary.add(map, getKey(ChartType.LINE_3_D, ChartStyle.SHADED), 299);
        msDictionary.add(map, getKey(ChartType.PIE_3_D, ChartStyle.SHADED), 299);
        msDictionary.add(map, getKey(ChartType.BOX_AND_WHISKER, ChartStyle.SHADOWED), 343);
        msDictionary.add(map, getKey(ChartType.BAR_STACKED, ChartStyle.GRADIENT), 223);
        msDictionary.add(map, getKey(ChartType.BAR_3_D_STACKED, ChartStyle.GRADIENT), 349);
        msDictionary.add(map, getKey(ChartType.FUNNEL, ChartStyle.GRADIENT), 223);
        msDictionary.add(map, getKey(ChartType.PIE_3_D, ChartStyle.GRADIENT), 349);
        msDictionary.add(map, getKey(ChartType.BAR_3_D_STACKED, ChartStyle.ORIGINAL), 298); // BarStacked
        msDictionary.add(map, getKey(ChartType.DOUGHNUT, ChartStyle.ORIGINAL), 310); // Bar3DStacked
        msDictionary.add(map, getKey(ChartType.SUNBURST, ChartStyle.ORIGINAL), 416); // Treemap
        msDictionary.add(map, getKey(ChartType.BAR_3_D_STACKED, ChartStyle.GREY), 288); // Bar 3D
        msDictionary.add(map, getKey(ChartType.LINE_3_D, ChartStyle.GREY), 288); // Bar 3D
        msDictionary.add(map, getKey(ChartType.RADAR, ChartStyle.GREY), 288); // Bar 3D
        msDictionary.add(map, getKey(ChartType.SCATTER, ChartStyle.GREY), 288); // Bar 3D
        msDictionary.add(map, getKey(ChartType.SURFACE_3_D, ChartStyle.GREY), 314); // Area 3D
        msDictionary.add(map, getKey(ChartType.SUNBURST, ChartStyle.BLUE), 260); // Doughnut
        msDictionary.add(map, getKey(ChartType.BOX_AND_WHISKER, ChartStyle.BLUE), 247); // Scatter
        msDictionary.add(map, getKey(ChartType.WATERFALL, ChartStyle.BLUE), 247); // Scatter
        msDictionary.add(map, getKey(ChartType.SURFACE, ChartStyle.BLUE), 275); // Bubble
        msDictionary.add(map, getKey(ChartType.SURFACE_3_D, ChartStyle.BLUE), 275); // Bubble
        msDictionary.add(map, getKey(ChartType.HISTOGRAM, ChartStyle.SHADED_PLOT), 386); // Sunburst
        msDictionary.add(map, getKey(ChartType.BOX_AND_WHISKER, ChartStyle.SHADED_PLOT), 386); // Sunburst
        msDictionary.add(map, getKey(ChartType.FUNNEL, ChartStyle.SHADED_PLOT), 386); // Sunburst
        msDictionary.add(map, getKey(ChartType.WATERFALL, ChartStyle.SHADED_PLOT), 386); // Sunburst

        return map;
    }

    /// <summary>
    /// Gets a combined chart type and chart style value to use as a key in maps.
    /// </summary>
    private static int getKey(/*ChartType*/int chartType, /*ChartStyle*/int chartStyle)
    {
        // The following chart types have the same set of styles in MS Word.
        switch (chartType)
        {
            case ChartType.AREA_STACKED:
            case ChartType.AREA_PERCENT_STACKED:
                chartType = ChartType.AREA;
                break;
            case ChartType.AREA_3_D_STACKED:
            case ChartType.AREA_3_D_PERCENT_STACKED:
                chartType = ChartType.AREA_3_D;
                break;
            case ChartType.BAR_PERCENT_STACKED:
                chartType = ChartType.BAR_STACKED;
                break;
            case ChartType.BAR_3_D_PERCENT_STACKED:
                chartType = ChartType.BAR_3_D_STACKED;
                break;
            case ChartType.COLUMN_STACKED:
            case ChartType.COLUMN_PERCENT_STACKED:
                chartType = (chartStyle != ChartStyle.GRADIENT) ? ChartType.BAR_STACKED : ChartType.COLUMN_STACKED;
                break;
            case ChartType.COLUMN_3_D:
            case ChartType.COLUMN_3_D_CLUSTERED:
                chartType = (chartStyle != ChartStyle.GRADIENT) ? ChartType.BAR_3_D : ChartType.COLUMN_3_D;
                break;
            case ChartType.COLUMN_3_D_STACKED:
            case ChartType.COLUMN_3_D_PERCENT_STACKED:
                chartType = (chartStyle != ChartStyle.GRADIENT) ? ChartType.BAR_3_D_STACKED : ChartType.COLUMN_3_D_STACKED;
                break;
            case ChartType.LINE_STACKED:
            case ChartType.LINE_PERCENT_STACKED:
                chartType = ChartType.LINE;
                break;
            case ChartType.PIE_OF_BAR:
                chartType = ChartType.PIE_OF_PIE;
                break;
            case ChartType.PARETO:
                chartType = ChartType.HISTOGRAM;
                break;
        }

        return ((int)chartType << 16) + (int)chartStyle;
    }

    /// <summary>
    /// Creates and fills a map of a chart style to its default style ID.
    /// </summary>
    private static HashMap<Integer, Integer> getDefaultStyleIdMap()
    {
        // These style IDs are used when there is no direct mapping from chart type and chart style to style ID.
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        msDictionary.add(map, ChartStyle.NORMAL, 216); // Bar
        msDictionary.add(map, ChartStyle.MUTED, 206); // Column
        msDictionary.add(map, ChartStyle.SATURATED, 220); // Bar
        msDictionary.add(map, ChartStyle.SHADED, 252); // Pie
        msDictionary.add(map, ChartStyle.FLAT, 212); // Column
        msDictionary.add(map, ChartStyle.SHADOWED, 346); // Area
        msDictionary.add(map, ChartStyle.GRADIENT, 210); // Column
        msDictionary.add(map, ChartStyle.ORIGINAL, 239); // Line
        msDictionary.add(map, ChartStyle.TRANSPARENT_1, 215); // Column
        msDictionary.add(map, ChartStyle.TRANSPARENT_2, 312); // Area 3D
        msDictionary.add(map, ChartStyle.OUTLINE, 224); // Bar
        msDictionary.add(map, ChartStyle.OUTLINE_BLACK, 213); // Column
        msDictionary.add(map, ChartStyle.BLACK, 283); // Area
        msDictionary.add(map, ChartStyle.GREY, 205); // Column
        msDictionary.add(map, ChartStyle.BLUE, 2601); // Pie with updated dataPoint3D
        msDictionary.add(map, ChartStyle.SHADED_PLOT, 208); // Column
        return map;
    }

    /// <summary>
    /// Creates and fills a map of a style ID to a chart style.
    /// </summary>
    private static HashMap<Integer, Integer> getChartStyleMap(
        HashMap<Integer, Integer> styleIdMap, HashMap<Integer, Integer> chartStyleMap)
    {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

        for (Map.Entry<Integer, Integer> pair : styleIdMap.entrySet())
        {
            if (map.containsKey(pair.getValue()))
                continue;

            /*ChartStyle*/int style = (/*ChartStyle*/int)(pair.getKey() & 0xffff);
            msDictionary.add(map, pair.getValue(), style);
        }

        for (Map.Entry<Integer, Integer> pair : chartStyleMap.entrySet())
        {
            if (map.containsKey(pair.getValue()))
                continue;

            msDictionary.add(map, pair.getValue(), pair.getKey());
        }

        return map;
    }

    private static /*final*/ HashMap<Integer, Integer> G_CHART_TYPE_AND_STYLE_TO_STYLE_ID_MAP;
    private static /*final*/ HashMap<Integer, Integer> G_CHART_STYLE_TO_DEFAULT_STYLE_ID_MAP;
    private static /*final*/ HashMap<Integer, Integer> G_STYLE_ID_TO_CHART_STYLE_MAP;

    private static final /*ChartType*/int COMBO_CHART = (/*ChartType*/int)(-1);

    static/* ChartStyleResolver()*/
    {
        G_CHART_TYPE_AND_STYLE_TO_STYLE_ID_MAP = getStyleIdMap();
        G_CHART_STYLE_TO_DEFAULT_STYLE_ID_MAP = getDefaultStyleIdMap();
        G_STYLE_ID_TO_CHART_STYLE_MAP = getChartStyleMap(G_CHART_TYPE_AND_STYLE_TO_STYLE_ID_MAP, G_CHART_STYLE_TO_DEFAULT_STYLE_ID_MAP);
    }
}

