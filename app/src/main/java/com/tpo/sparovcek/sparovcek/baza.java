package com.tpo.sparovcek.sparovcek;

import android.provider.BaseColumns;

public final class baza {
    private baza(){}
    public static class pb implements BaseColumns {
        public static final String TABLE_NAME = "log";
        public static final String COLUMN_NAME_TITLE = "zapis";
    }
}
