package com.nanodegree.joel.popularmovies.data;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.DefaultValue;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.BLOB;
import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;
import static net.simonvt.schematic.annotation.DataType.Type.REAL;

/**
 * Created by joel on 10/21/15.
 */
public interface MovieColumns {
    @DataType(INTEGER) @PrimaryKey
    String _ID = "_id";

    @DataType(TEXT) @NotNull
    String POSTER_PATH = "poster_path";

    @DataType(REAL)
    String AVERAGE_RATE = "average_rate";

    @DataType(REAL) @NotNull
    String POPULARITY = "popularity";

    @DataType(INTEGER) @DefaultValue("0")
    String IS_FAVORITE = "is_favorite";

    @DataType(TEXT)
    String TITLE = "tile";

    @DataType(TEXT)
    String RELEASE_DATE = "release_date";

    @DataType(TEXT)
    String SYNOPSIS = "synopsis";

    @DataType(TEXT)
    String RUNTIME = "runtime";

    @DataType(BLOB)
    String POSTER_BLOB = "poster_blob";

}
