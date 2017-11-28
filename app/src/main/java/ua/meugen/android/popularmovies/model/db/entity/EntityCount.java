package ua.meugen.android.popularmovies.model.db.entity;

import android.arch.persistence.room.ColumnInfo;

/**
 * Created by meugen on 28.11.2017.
 */

public class EntityCount {

    @ColumnInfo(name = "c")
    public int count;
}
