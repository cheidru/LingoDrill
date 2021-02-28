package com.example.myapplication

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import kotlin.properties.Delegates

class AudioPlay : AppCompatActivity(), View.OnTouchListener {

    var windowwidth = 0
    var windowheight = 0
    var density by Delegates.notNull<Float>()
    lateinit var xVW: TextView
    lateinit var yVW: TextView
    lateinit var myViewGrp: ViewGroup
    var xc :Float = 0F
    var yc :Float = 0F
    var xcoord=""
    var ycoord=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_auvaweform)

//        myViewGrp = findViewById(R.id.root)
        myViewGrp.setOnTouchListener(this)

        fillabels()
    }


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        xc= event?.x!!
        yc= event?.y!!

//        val img = findViewById<ImageView>(R.id.imgVW)
//        val imx = img.marginLeft
//        val imy = img.marginTop

        when(event?.actionIndex) {
            MotionEvent.ACTION_DOWN -> {
                xcoord="Down "+(xc).toString()
                ycoord="Down "+(yc).toString()
            }
            MotionEvent.ACTION_MOVE -> {
                xcoord="Move "+(xc).toString()
                ycoord="Move "+(yc).toString()

            }

            MotionEvent.ACTION_UP -> {
                xcoord="Up "+xc.toString()
                ycoord="Up "+yc.toString()
            }
        }
//        xVW = findViewById<TextView>(R.id.xCoord)
//        yVW = findViewById<TextView>(R.id.yCoord)

        xVW.text=xcoord
        yVW.text=ycoord

//        img.marginLeft = imx+xc.toInt()
//        }
        return true
    }

    fun fillabels(){
//        var lbStart = findViewById<TextView>(R.id.lblStart)
//        var startPan = findViewById<LinearLayout>(R.id.start_time)
    }
}