package com.nanodegree.joel.popularmovies.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by joel on 10/22/15.
 */
public interface VideoColumns {
    @DataType(INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(INTEGER)
    @References(table = PopMoviesDb.Tables.MOVIES, column = MovieColumns._ID)
    String MOVIE_ID = "movie_id";

    @DataType(TEXT) String KEY = "key";
    @DataType(TEXT) String NAME = "name";
    @DataType(TEXT) String SITE = "site";
}
