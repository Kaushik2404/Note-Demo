package com.ak.notedemo.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.ak.notedemo.R
import com.ak.notedemo.data.Note
import com.ak.notedemo.listner.OnClickNotes
import com.ak.notedemo.presentation.ui.adddata.MainActivity
import com.ak.notedemo.presentation.ui.update.UpdateActivity


class NotesAdapter(private var context: Context, private var notelist: List<Note>,
                   val onClickNotes: OnClickNotes): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val iteamView =
            LayoutInflater.from(parent.context).inflate(R.layout.note_itean, parent, false)
        return ViewHolder(iteamView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(notelist[position].view=="ON"){

            holder.Title.text = notelist[position].noteTitle.toString()

            holder.Date.text = notelist[position].noteDataAddDate.toString()

            holder.Layout.setBackgroundColor(notelist[position].color!!)

            holder.UpdateNotes.setOnClickListener {
                val intent=Intent(context,UpdateActivity::class.java)
                intent.putExtra("ID",notelist[position].noteId!!)
                intent.putExtra("NOTE",notelist[position].noteData!!)
                intent.putExtra("TITLE",notelist[position].noteTitle!!)
                intent.putExtra("ADDDATE",notelist[position].noteDataAddDate!!)
                intent.putExtra("COLOR",notelist[position].color!!)
                context.startActivity(intent)
                (context as Activity).finish()
            }

            notelist[position].color?.let { holder.Layout.setBackgroundColor(ContextCompat.getColor(context,it)) }

            holder.DeleteNotes.setOnClickListener {
                val oknote=Note(notelist[position].noteId,
                    notelist[position].noteTitle,
                    notelist[position].noteData,
                    notelist[position].noteDataAddDate,
                    null,
                    notelist[position].color
                    ,"ON")
                onClickNotes.clicknotes(oknote)
                notifyItemRemoved(position)
                notifyDataSetChanged()

            }
        }

    }

    override fun getItemCount(): Int {
        return notelist.size
    }
    class ViewHolder(iteamView: View) : RecyclerView.ViewHolder(iteamView) {
        var Title = iteamView.findViewById<TextView>(R.id.noteTitle)
        var Date = iteamView.findViewById<TextView>(R.id.noteDate)
        var Layout =iteamView.findViewById<LinearLayout>(R.id.noteLayout)
        var UpdateNotes =iteamView.findViewById<ImageView>(R.id.updateNote)
        var DeleteNotes =iteamView.findViewById<ImageView>(R.id.deleteNote)

    }


}