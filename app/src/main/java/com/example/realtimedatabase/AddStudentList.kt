package com.example.realtimedatabase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.realtimedatabase.StudentListAdapter.StudentListAdapter
import com.example.realtimedatabase.StudentModel.StudentModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddStudentList : AppCompatActivity(){

    private lateinit var addStudentName : AppCompatEditText
    private lateinit var addStudentAge : AppCompatEditText
    private lateinit var addStudentEmail : AppCompatEditText
    private lateinit var addStudentList: AppCompatButton

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student_list)

        addStudentName = findViewById(R.id.add_studentName)
        addStudentEmail = findViewById(R.id.add_studentEmail)
        addStudentAge = findViewById(R.id.add_studentAge)
        addStudentList = findViewById(R.id.add_studentDataButton)

        databaseReference = FirebaseDatabase.getInstance().getReference("Students")

        addStudentList.setOnClickListener {
            addStudentData()
        }

    }

    private fun addStudentData(){
        val studentName = addStudentName.text.toString()
        val studentEmail = addStudentEmail.text.toString()
        val studentAge = addStudentAge.text.toString()

        val studentId = databaseReference.push().key!!

        val studentModel = StudentModel(studentId,studentName, studentEmail, studentAge)

        databaseReference.child(studentId).setValue(studentModel)
            .addOnSuccessListener {
                Toast.makeText(this, "Student data added", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Student data added failed", Toast.LENGTH_SHORT).show()
            }
    }
}

