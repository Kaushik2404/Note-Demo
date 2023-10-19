package com.ak.notedemo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class Note(

    @PrimaryKey(autoGenerate = true)
    val noteId :Int?=null,

    @ColumnInfo
    val noteTitle:String?=null,

    @ColumnInfo
    val noteData:String?=null,

    @ColumnInfo
    val noteDataAddDate:String?=null,

    @ColumnInfo
    val noteUpdateDate:String?=null,

    @ColumnInfo
    val color:Int?=null,

    @ColumnInfo
    val view:String?="ON"

)


