package com.ak.notedemo.presentation.ui.update

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import androidx.room.Update
import com.ak.notedemo.R
import com.ak.notedemo.RandomColors
import com.ak.notedemo.data.Note
import com.ak.notedemo.databinding.ActivityHomeBinding
import com.ak.notedemo.databinding.ActivityUpdateBinding
import com.ak.notedemo.presentation.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
@AndroidEntryPoint
class UpdateActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateBinding
     val viewModal : UpdateViewModal by viewModels()

    var noteid: Int ?=null
    lateinit var noteTitle:String
    lateinit var noteData:String
    lateinit var noteAddDate:String
    lateinit var noteUpdateDate:String
   var color:Int ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setdata()
        onclick()
    }
    private fun setdata() {
        noteid=intent.getIntExtra("ID",0)
        noteTitle=intent.getStringExtra("TITLE").toString()
        noteData=intent.getStringExtra("NOTE").toString()
        noteAddDate=intent.getStringExtra("ADDDATE").toString()
        color=intent.getIntExtra("COLOR",0)

        binding.notesTitleUpdate.setText(noteTitle)
        binding.notesEdtUpdate.setText(noteData)

    }

    private fun onclick() {
        binding.updateNotes.setOnClickListener {

            if(binding.notesTitleUpdate.text.isNotEmpty() && binding.notesEdtUpdate.text.isNotEmpty()){
                noteTitle=binding.notesTitleUpdate.text.toString()
                noteData=binding.notesEdtUpdate.text.toString()

                //update date
                val time = Calendar.getInstance().time
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
                noteUpdateDate = formatter.format(time)
//
//            val rnd = Random()
//            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
//            view.setBackgroundColor(color)

//            color= RandomColors().getColor()

                val noteData=Note(noteid, noteTitle,noteData, noteAddDate,noteUpdateDate,color,"ON")
                viewModal.update(noteData)

                binding.notesTitleUpdate.text.clear()
                binding.notesEdtUpdate.text.clear()

                val intent= Intent(this@UpdateActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()

            }


        }
    }
}