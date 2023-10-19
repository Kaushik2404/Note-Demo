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


class RecycleAdapter(private var context: Context, private var notelist: List<Note>,
                     val onClickNotes: OnClickNotes): RecyclerView.Adapter<RecycleAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val iteamView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycle_itean, parent, false)
        return ViewHolder(iteamView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(notelist[position].view=="OFF"){

            holder.Title.text = notelist[position].noteTitle.toString()

            holder.Date.text = notelist[position].noteDataAddDate.toString()

//            holder.Layout.setBackgroundColor(notelist[position].color!!)


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
        var DeleteNotes =iteamView.findViewById<ImageView>(R.id.deleteNote)

    }


}