package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.loader.content.CursorLoader


class MainActivity : AppCompatActivity() {
    val REQUEST_FILE = 1
    lateinit var newFilePath: Uri
    var audioFileValues : ContentValues = ContentValues()
    lateinit var aDB:SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tbMain = findViewById<Toolbar>(R.id.toolbar1)
        setSupportActionBar(tbMain)

        // Open database
        val MyDBHelper = AudioSQLhelper(this)
        aDB = MyDBHelper.writableDatabase



        // создаем адаптер и настраиваем список
        val aCursor = aDB.query("myaudio", arrayOf("name"), null, null, null, null, "prio", null)
        val scAdapter = SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                aCursor,
                arrayOf("name"),
                intArrayOf(android.R.id.text1),
                0
        )

        val lvData = findViewById<ListView>(R.id.lv1)
        lvData.setAdapter(scAdapter)

        //Add context menu to the list
        registerForContextMenu(lvData)

        // создаем лоадер для чтения данных

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_select_file -> {
                // Select a new audio file
                val audioIntent: Intent = Intent(Intent.ACTION_GET_CONTENT)
                audioIntent.setType("audio/mpeg")
                audioIntent.addCategory(Intent.CATEGORY_OPENABLE)
                startActivityForResult(audioIntent, REQUEST_FILE)
            }
            R.id.action_settings -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == REQUEST_FILE && resultCode == RESULT_OK) {
                newFilePath = data?.data!!
                val newFileName = newFilePath.lastPathSegment?.substringAfterLast('/').toString()

                // Проверяем что добавлено новое значение
//                val alv = findViewById<ListView>(R.id.lv1)

                // метод moveToFirst() вернет  false, если курсор пуст
                if (aDB.query("myaudio", arrayOf("_id"), "name=?", arrayOf(newFileName), null, null, null).moveToFirst()) {
                    val au_text = "Этот файл уже добавлен!"
                    val au_duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, au_text, au_duration)
                    toast.show()
                } else {
                    // Add the file to the database
                    audioFileValues.put("name", newFileName)
                    audioFileValues.put("uri", newFilePath.toString())
                    audioFileValues.put("prio", 1)

                    // Увеличить prio остальных записей в базе на 1 чтобы новая запись была первой
                    aDB.execSQL("UPDATE myaudio SET prio = prio + 1")

                    // Вставить новую запись в таблицу
                    aDB.insert("myaudio", null, audioFileValues)
                }
            }
        }

    class AudioLoader(context: Context) : CursorLoader(context) {
        override fun loadInBackground(): Cursor? {
            return super.loadInBackground()
        }
    }
}



