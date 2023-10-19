package com.ak.notedemo.presentation.ui.adddata

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ak.notedemo.RandomColors
import com.ak.notedemo.data.Note
import com.ak.notedemo.databinding.ActivityMainBinding
import com.ak.notedemo.presentation.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    private val viewModel: MainViewModal by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.insertNotes.setOnClickListener {

            if(binding.notesTitle.text.isNotEmpty() && binding.notesEdt.text.isNotEmpty()){

                val noteTitle=binding.notesTitle.text.toString()
                val noteEdt=binding.notesEdt.text.toString()
                val time = Calendar.getInstance().time
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
                val current = formatter.format(time)
//
//            val rnd = Random()
//            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
//            view.setBackgroundColor(color)

                val color=RandomColors().getColor()

                val noteData=Note(null, noteTitle, noteEdt, current,null,color,"ON")
                viewModel.insert(noteData)

                binding.notesTitle.text.clear()
                binding.notesEdt.text.clear()

                val intent=Intent(this@MainActivity,HomeActivity::class.java)
                startActivity(intent)
                finish()
            }

        }

        binding.showNotes.setOnClickListener {

            val intent=Intent(this@MainActivity,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

}