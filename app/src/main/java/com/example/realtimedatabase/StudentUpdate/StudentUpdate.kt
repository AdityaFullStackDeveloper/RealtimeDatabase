package com.example.realtimedatabase.StudentUpdate

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.realtimedatabase.MainActivity
import com.example.realtimedatabase.R
import com.example.realtimedatabase.StudentListAdapter.StudentListAdapter
import com.example.realtimedatabase.StudentModel.StudentModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class StudentUpdate : AppCompatActivity() {
    private lateinit var studentUpdateName: AppCompatEditText
    private lateinit var studentUpdateEmail: AppCompatEditText
    private lateinit var studentUpdateAge: AppCompatEditText
    private lateinit var studentUpdateButton: AppCompatButton

    private lateinit var databaseReference: DatabaseReference
    private var studentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_update)

        studentUpdateName = findViewById(R.id.update_studentName)
        studentUpdateEmail = findViewById(R.id.update_studentEmail)
        studentUpdateAge = findViewById(R.id.update_studentAge)

        studentUpdateButton = findViewById(R.id.studentData_updateButton)

        databaseReference = FirebaseDatabase.getInstance().getReference("Students")

        studentId = intent.getStringExtra("id_key")
        val updateName = intent.getStringExtra("studentName")
        val updateEmail = intent.getStringExtra("studentEmail")
        val updateAge = intent.getStringExtra("studentAge")

        studentUpdateName.setText(updateName)
        studentUpdateEmail.setText(updateEmail)
        studentUpdateAge.setText(updateAge)

        studentUpdateButton.setOnClickListener {
            updateData()
        }
    }

    private fun updateData(){
        val updateName = studentUpdateName.text.toString()
        val updateEmail = studentUpdateEmail.text.toString()
        val updateAge = studentUpdateAge.text.toString()

        if (studentId != null){
            val updateStudent = StudentModel(studentId!!, updateName, updateEmail, updateAge)
            databaseReference.child(studentId!!).setValue(updateStudent)
                .addOnSuccessListener {
                    Toast.makeText(this, "Student data update", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Student data failed", Toast.LENGTH_SHORT).show()
                }
        }
    }
}