package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AudioSQLhelper(context: Context): SQLiteOpenHelper(context, "AudioSQL", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        // создаем таблицу с полями
        db?.execSQL("create table myaudio ("
                + "_id integer primary key autoincrement,"
                + "name text,"
                + "uri text,"
                + "prio integer" +
                ");");

        db?.execSQL("create table audiodrill ("
                + "_id integer primary key autoincrement,"
                + "audio_id integer,"
                + "start_time integer,"
                + "end_time integer,"
                + "rewinds integer,"
                + "delay integer,"
                + "prio integer" +
                ");");

        db?.execSQL("create table my_settings ("
                + "_id integer primary key autoincrement," +
                ");");


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}