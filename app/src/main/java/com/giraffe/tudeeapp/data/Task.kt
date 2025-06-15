package com.giraffe.tudeeapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task (
    @PrimaryKey val uid: Int,

)