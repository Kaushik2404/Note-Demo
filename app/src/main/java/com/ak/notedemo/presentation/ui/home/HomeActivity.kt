package com.ak.notedemo.presentation.ui.home

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.MultiAutoCompleteTextView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ak.notedemo.R
import com.ak.notedemo.adapter.NotesAdapter
import com.ak.notedemo.data.Note
import com.ak.notedemo.databinding.ActivityHomeBinding
import com.ak.notedemo.listner.OnClickNotes
import com.ak.notedemo.presentation.ui.adddata.MainActivity
import com.ak.notedemo.presentation.ui.recyclebin.RecycleActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeViewModal by viewModels()

    lateinit var list:MutableList<Note>

    lateinit var adapter:NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list= arrayListOf()

        viewModel.fetchData("ON")
        setData()
        onClick()

    }

    private fun setData() {

        GlobalScope.launch(Dispatchers.Main) {

            viewModel.allNotes.observe(this@HomeActivity,Observer{

                list.clear()

                if(it.isEmpty()){
                    binding.noteView.visibility=View.GONE
                    binding.notetag.visibility=View.VISIBLE
                }
                else{
                    Log.d("Test","--------$list")
                    list= it as MutableList<Note>

                    if(list.isEmpty()){
                        binding.noteView.visibility=View.GONE
                        binding.notetag.visibility=View.VISIBLE
                    }
                    else{
                        Log.d("Test",list.toString())
                        binding.noteView.layoutManager= LinearLayoutManager(this@HomeActivity)
                        binding.noteView.setHasFixedSize(true)
                        adapter=NotesAdapter(this@HomeActivity,list,object : OnClickNotes{

                            override fun clicknotes(note: Note) {

                                val builder=AlertDialog.Builder(this@HomeActivity)
                                builder.setCancelable(false)
                                builder.setIcon(R.drawable.baseline_folder_delete_24)
                                builder.setTitle("Delete Data !")
                                builder.setMessage("If you Store this note RecycleBin To Press Yes")
                                builder.setPositiveButton("yes"){dialog, which->

                                    val rnote=Note(note.noteId,
                                        note.noteTitle,
                                        note.noteData,
                                        note.noteDataAddDate,
                                        note.noteUpdateDate,
                                        note.color,
                                        "OFF")
                                    viewModel.update(rnote)
                                    list.removeAt(list.indexOf(note))
                                    adapter.notifyDataSetChanged()

                                }
                                builder.setNegativeButton("NO"){dialog,which->
                                    viewModel.delete(note.noteId!!)
                                    Log.d("Test1",list.toString())
                                    list.removeAt(list.indexOf(note))
                                    if(list.size>0){

                                    }else{
                                        binding.noteView.visibility=View.GONE
                                        binding.notetag.visibility=View.VISIBLE
                                    }
                                    Log.d("Test2",list.toString())
                                }
                                val dialog=builder.create()
                                dialog.show()

                            }
                        })
                        binding.noteView.adapter=adapter

                        adapter.notifyDataSetChanged()
                    }

                }
            })


        }
    }

    private fun onClick() {
        binding.addNotes.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.headerHome.option.setOnClickListener {
                    performOptionsMenuClick()
        }
    }

    private fun performOptionsMenuClick() {
        val popupMenu = PopupMenu(this,binding.headerHome.option.findViewById(R.id.option))
//         add the menu
        popupMenu.inflate(R.menu.options_menu)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true)
        }
        // implement on menu item click Listener
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.recyclBin -> {
                        val intent=Intent(this@HomeActivity,RecycleActivity::class.java)
                        startActivity(intent)
                        return true
                    }
                }
                    return false

            }
        })
        popupMenu.show()

    }
}
