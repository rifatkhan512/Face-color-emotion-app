package com.example.emojiapp

import android.annotation.SuppressLint
import android.content.Intent
import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.emojiapp.databinding.ActivityNewBinding
import java.io.File
import java.io.FileWriter
import java.io.IOException

class NewActivity : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    private var bwList = arrayListOf(11,21,31,41,51,61)
    private var colorList = arrayListOf(12,22,32,42,52,62)
    private var colorBrewList = arrayListOf(13,23,33,43,53,63)
    private var colorBrewMouthList = arrayListOf(14,24,34,44,54,64)
    private var finalList = bwList.shuffled().take(3) + colorList.shuffled().take(3) +
                    colorBrewList.shuffled().take(3) + colorBrewMouthList.shuffled().take(3)
    private val imageMap = mapOf(
        11 to R.drawable.i11,
        12 to R.drawable.i12,
        13 to R.drawable.i13,
        14 to R.drawable.i14,

        21 to R.drawable.i21,
        22 to R.drawable.i22,
        23 to R.drawable.i23,
        24 to R.drawable.i24,

        31 to R.drawable.i31,
        32 to R.drawable.i32,
        33 to R.drawable.i33,
        34 to R.drawable.i34,

        41 to R.drawable.i41,
        42 to R.drawable.i42,
        43 to R.drawable.i43,
        44 to R.drawable.i44,

        51 to R.drawable.i51,
        52 to R.drawable.i52,
        53 to R.drawable.i53,
        54 to R.drawable.i54,

        61 to R.drawable.i61,
        62 to R.drawable.i62,
        63 to R.drawable.i63,
        64 to R.drawable.i64,
    )

    private var current=1
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val b = ActivityNewBinding.inflate(layoutInflater)
        setContentView(b.root)
        val name=intent.getStringExtra("name")
        val age= intent.getIntExtra("age",0)
        val gender= intent.getStringExtra("gender")
        val selectedlist= arrayListOf(0,0,0,0,0,0,0,0,0,0,0,0)
        val confidencelist= arrayListOf(0,0,0,0,0,0,0,0,0,0,0,0)

        imageMap[finalList[0]]?.let { b.image.setImageResource(it) }
        b.HappyButton.setOnClickListener{
            selectButton(b,1)
            selectedlist[current-1]=1
        }
        b.SadButton.setOnClickListener{
            selectButton(b,2)
            selectedlist[current-1]=2
        }
        b.AngryButton.setOnClickListener{
            selectButton(b,3)
            selectedlist[current-1]=3
        }
        b.SurpriseButton.setOnClickListener{
            selectButton(b,4)
            selectedlist[current-1]=4
        }
        b.FearButton.setOnClickListener{
            selectButton(b,5)
            selectedlist[current-1]=5
        }
        b.DisgustButton.setOnClickListener{
            selectButton(b,6)
            selectedlist[current-1]=6
        }
        b.confidence.setOnCheckedChangeListener { _,_ ->
            when(b.confidence.checkedRadioButtonId){
                R.id.confidence1-> confidencelist[current-1]=1

                R.id.confidence2-> confidencelist[current-1]=2

                R.id.confidence3-> confidencelist[current-1]=3
            }
        }

        b.done.setOnClickListener {
            val i=Intent(this,MainActivity2::class.java)
            var data= "$name $age $gender "
            data+= finalList.joinToString (" ",postfix=" ")+
                    selectedlist.joinToString(" ",postfix=" ")+
                    confidencelist.joinToString (" ",postfix="/n")
            if(checkPermission()){
                writeToExternalStorage("raw-data.txt",data)
            }
            else{
                requestPermission()
            }
            startActivity(i)
        }

        var x1 = 0f
        var x2: Float

        b.root.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    x1 = event.x
                    true
                }
                MotionEvent.ACTION_UP -> {
                    x2 = event.x
                    val deltaX = x2 - x1
                    if (deltaX > 100) {
                        // Swipe to the right
                        // Handle your swipe right action here

                        if (current>1) {current-=1}
                    }
                    if (deltaX < -100) {
                        // Swipe to the left, go forward
                        if (current<12 && selectedlist[current-1 ]!=0) {current+=1}

                    }
                    b.page.text= "page: $current/12"
                    imageMap[finalList[current-1]]?.let { b.image.setImageResource(it) }
                    when(confidencelist[current-1]){
                        1-> b.confidence.check(R.id.confidence1)
                        2-> b.confidence.check(R.id.confidence2)
                        3-> b.confidence.check(R.id.confidence3)
                        0-> b.confidence.clearCheck()
                    }
                    selectButton(b,selectedlist[current-1])

                    if (current==12 && selectedlist[11]!=0 && confidencelist[11]!=0){
                        b.done.visibility= View.VISIBLE
                    }

                    true
                }
                else -> false
            }
        }



    }
    private fun selectButton(b: ActivityNewBinding,n:Int) {
        b.HappyButton.setBackgroundColor(getColor(R.color.grey))
        b.SadButton.setBackgroundColor(getColor(R.color.grey))
        b.AngryButton.setBackgroundColor(getColor(R.color.grey))
        b.SurpriseButton.setBackgroundColor(getColor(R.color.grey))
        b.FearButton.setBackgroundColor(getColor(R.color.grey))
        b.DisgustButton.setBackgroundColor(getColor(R.color.grey))
        when(n){
            1-> b.HappyButton.setBackgroundColor(getColor(R.color.teal_200))
            2-> b.SadButton.setBackgroundColor(getColor(R.color.teal_200))
            3-> b.AngryButton.setBackgroundColor(getColor(R.color.teal_200))
            4-> b.SurpriseButton.setBackgroundColor(getColor(R.color.teal_200))
            5-> b.FearButton.setBackgroundColor(getColor(R.color.teal_200))
            6-> b.DisgustButton.setBackgroundColor(getColor(R.color.teal_200))

        }
    }
    private fun writeToExternalStorage(fileName: String, data: String) {
        if (isExternalStorageWritable()) {
            val root = Environment.getExternalStorageDirectory()
            //val dir = File(root.absolutePath + "/rawdata")
            //dir.mkdirs()
            val sdcard= getExternalFilesDir("rawdata")
            val file = File(sdcard, fileName)

            try {
                FileWriter(file, true).use { writer ->
                    writer.append(data)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            // Handle the case when external storage is not available
        }
    }
    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            this@NewActivity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@NewActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(
                this@NewActivity,
                "Write External Storage permission allows us to create files. Please allow this permission in App Settings.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this@NewActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 100
            )
        }
    }
    private fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }
}