package com.example.studentsmanager

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var studentAdapter: StudentAdapter
    private val students = mutableListOf<Student>()

    private val addUpdateStudentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val student = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra("student", Student::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    result.data?.getParcelableExtra("student")
                }
                val position = result.data?.getIntExtra("position", -1) ?: -1
                if (position == -1) {
                    // Thêm mới sinh viên
                    student?.let {
                        students.add(it)
                        studentAdapter.notifyItemInserted(students.size - 1)
                    }
                } else {
                    // Cập nhật sinh viên
                    student?.let {
                        students[position] = it
                        studentAdapter.notifyItemChanged(position)
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val fab: FloatingActionButton = findViewById(R.id.fabAddStudent)

        studentAdapter = StudentAdapter(this, students) { student, action, position ->
            when (action) {
                R.id.menu_update -> openAddUpdateActivity(student, position)
                R.id.menu_delete -> confirmDeleteStudent(student, position)
                R.id.menu_call -> callStudent(student.phone)
                R.id.menu_email -> emailStudent(student.email)
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = studentAdapter

        fab.setOnClickListener {
            openAddUpdateActivity(null, -1)
        }
    }

    private fun openAddUpdateActivity(student: Student?, position: Int) {
        val intent = Intent(this, AddUpdateStudentActivity::class.java)
        intent.putExtra("student", student)
        intent.putExtra("position", position)
        addUpdateStudentLauncher.launch(intent)
    }

    private fun confirmDeleteStudent(student: Student, position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete this student?")
            .setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                students.removeAt(position)
                studentAdapter.notifyItemRemoved(position)
                Toast.makeText(this, "Student deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun callStudent(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        startActivity(intent)
    }

    private fun emailStudent(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
        startActivity(intent)
    }
}