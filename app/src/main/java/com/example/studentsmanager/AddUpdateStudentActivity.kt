package com.example.studentsmanager

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddUpdateStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update_student)

        val etName: EditText = findViewById(R.id.etName)
        val etStudentId: EditText = findViewById(R.id.etStudentId)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPhone: EditText = findViewById(R.id.etPhone)
        val btnSave: Button = findViewById(R.id.btnSave)

        val student = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("student", Student::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("student")
        }
        val position = intent.getIntExtra("position", -1)

        student?.let {
            etName.setText(it.name)
            etStudentId.setText(it.studentId)
            etEmail.setText(it.email)
            etPhone.setText(it.phone)
        }

        btnSave.setOnClickListener {
            val newStudent = Student(
                name = etName.text.toString(),
                studentId = etStudentId.text.toString(),
                email = etEmail.text.toString(),
                phone = etPhone.text.toString()
            )
            intent.putExtra("student", newStudent)
            intent.putExtra("position", position)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}