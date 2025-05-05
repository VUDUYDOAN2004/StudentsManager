package com.example.studentsmanager

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(
    val name: String,
    val studentId: String,
    val email: String,
    val phone: String
) : Parcelable