package com.ak.notedemo.presentation.ui.recyclebin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ak.notedemo.R
import com.ak.notedemo.adapter.NotesAdapter
import com.ak.notedemo.adapter.RecycleAdapter
import com.ak.notedemo.data.Note
import com.ak.notedemo.databinding.ActivityRecycleBinding
import com.ak.notedemo.databinding.ActivityUpdateBinding
import com.ak.notedemo.listner.OnClickNotes
import com.ak.notedemo.presentation.ui.home.HomeViewModal
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecycleActivity : AppCompatActivity() {
    lateinit var binding: ActivityRecycleBinding

    private val viewModel: RecycleViewModal by viewModels()

    lateinit var list: MutableList<Note>

    lateinit var adapter: RecycleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecycleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = arrayListOf()

        viewModel.fetchData("OFF")
        setData()


    }

    private fun setData() {


        GlobalScope.launch(Dispatchers.Main) {

            viewModel.allNotes.observe(this@RecycleActivity, Observer {

                list.clear()

                if (it.isEmpty()) {
                    binding.RecycleBinView.visibility = View.GONE
                    binding.notetagRecycle.visibility = View.VISIBLE

                } else {
                    Log.d("Test", "--------$list")
                    list = it as MutableList<Note>

                    if (list.isEmpty()) {
                        binding.RecycleBinView.visibility = View.GONE
                        binding.notetagRecycle.visibility = View.VISIBLE
                    } else {
                        Log.d("Test", list.toString())
                        binding.RecycleBinView.layoutManager =
                            LinearLayoutManager(this@RecycleActivity)
                        binding.RecycleBinView.setHasFixedSize(true)
                        adapter = RecycleAdapter(this@RecycleActivity, list, object : OnClickNotes {
                            override fun clicknotes(note: Note) {

                                val builder = AlertDialog.Builder(this@RecycleActivity)
                                builder.setCancelable(false)
                                builder.setIcon(R.drawable.baseline_folder_delete_24)
                                builder.setTitle("Delete Data !")
                                builder.setMessage("If you Restore this note To click Yes")
                                builder.setPositiveButton("yes") { dialog, which ->
                                    val rnote = Note(
                                        note.noteId,
                                        note.noteTitle,
                                        note.noteData,
                                        note.noteDataAddDate,
                                        note.noteUpdateDate,
                                        note.color,
                                        "ON"
                                    )
                                    viewModel.update(rnote)
                                    list.removeAt(list.indexOf(note))
                                    adapter.notifyDataSetChanged()

                                }
                                builder.setNegativeButton("NO") { dialog, which ->
                                    viewModel.delete(note.noteId!!)
                                    Log.d("Test1", list.toString())
                                    list.removeAt(list.indexOf(note))
                                    if (list.size > 0) {

                                    } else {
                                        binding.RecycleBinView.visibility = View.GONE
                                        binding.notetagRecycle.visibility = View.VISIBLE
                                    }
                                    Log.d("Test2", list.toString())
                                }
                                val dialog = builder.create()
                                dialog.show()

                            }
                        })
                        binding.RecycleBinView.adapter = adapter

                        adapter.notifyDataSetChanged()
                    }

                }
            })


        }
    }
}