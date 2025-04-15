package com.example.studentsmanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var edtId: EditText
    private lateinit var btnAdd: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private val studentList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtName = findViewById(R.id.edtName)
        edtId = findViewById(R.id.edtId)
        btnAdd = findViewById(R.id.btnAdd)
        recyclerView = findViewById(R.id.recyclerView)

        adapter = StudentAdapter(studentList) { position ->
            studentList.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnAdd.setOnClickListener {
            val name = edtName.text.toString().trim()
            val id = edtId.text.toString().trim()

            if (name.isNotEmpty() && id.isNotEmpty()) {
                val student = Student(id, name)
                studentList.add(0, student)
                adapter.notifyItemInserted(0)
                recyclerView.scrollToPosition(0)

                edtName.text.clear()
                edtId.text.clear()
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
