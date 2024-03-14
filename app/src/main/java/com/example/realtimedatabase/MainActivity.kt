package com.example.realtimedatabase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimedatabase.StudentListAdapter.StudentListAdapter
import com.example.realtimedatabase.StudentModel.StudentModel
import com.example.realtimedatabase.StudentUpdate.StudentUpdate
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity(),StudentListAdapter.OnClickListener {
    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var studentList : ArrayList<StudentModel>

    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentRecyclerView = findViewById(R.id.studentList_recyclerView)

        studentRecyclerView.layoutManager = LinearLayoutManager(this)

        studentList = ArrayList<StudentModel>()

        getStudentList()

    }

    private fun getStudentList(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Students")

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                studentList.clear()

                if (snapshot.exists()){
                    for (studentSnap in snapshot.children){
                        val studentData = studentSnap.getValue(StudentModel::class.java)
                        studentList.add(studentData!!)
                    }

                    val studentAdapter = StudentListAdapter(this@MainActivity, studentList, this@MainActivity)
                    studentRecyclerView.adapter = studentAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onUpdateStudentData(position: Int) {
        val intent = Intent(this, StudentUpdate::class.java)
        intent.putExtra("id_key", studentList[position].id)
        intent.putExtra("studentName", studentList[position].studentName)
        intent.putExtra("studentEmail", studentList[position].studentEmail)
        intent.putExtra("studentAge", studentList[position].studentAge)
        startActivity(intent)
    }

    override fun onDeleteStudentData(position: Int) {
        val studentId = studentList[position].id
        databaseReference.child(studentId!!).removeValue()
            .addOnFailureListener {
                Toast.makeText(this, "Student data delete successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            }
            .addOnFailureListener {
                Toast.makeText(this, "Student data delete failed", Toast.LENGTH_SHORT).show()
            }
    }
}

