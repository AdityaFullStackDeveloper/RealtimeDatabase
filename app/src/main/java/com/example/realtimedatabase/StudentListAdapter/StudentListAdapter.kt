package com.example.realtimedatabase.StudentListAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimedatabase.R
import com.example.realtimedatabase.StudentModel.StudentModel

class StudentListAdapter(private var context: Context, private var studentList: List<StudentModel>, private var onClickListener:OnClickListener) :
    RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>() {

        class StudentViewHolder(studentView: View):RecyclerView.ViewHolder(studentView){
            val studentName : TextView = studentView.findViewById(R.id.student_name)
            val studentEmail : TextView = studentView.findViewById(R.id.student_email)
            val studentAge : TextView = studentView.findViewById(R.id.student_age)
            val studentListUpdate : ImageView = studentView.findViewById(R.id.student_listUpdate)
            val studentListDelete : ImageView = studentView.findViewById(R.id.student_listDelete)
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentViewHolder {
        val studentView = LayoutInflater.from(context).inflate(R.layout.student_list, parent, false)
        return StudentViewHolder(studentView)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.studentName.text = studentList[position].studentName
        holder.studentEmail.text = studentList[position].studentEmail
        holder.studentAge.text = studentList[position].studentAge

        holder.studentListUpdate.setOnClickListener {
            onClickListener.onUpdateStudentData(position)
        }
        holder.studentListDelete.setOnClickListener {
            onClickListener.onDeleteStudentData(position)
        }
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    interface OnClickListener{
        fun onUpdateStudentData(position: Int)
        fun onDeleteStudentData(position: Int)
    }
}