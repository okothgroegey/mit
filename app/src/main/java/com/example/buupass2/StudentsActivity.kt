package com.example.buupass2

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class StudentsActivity : AppCompatActivity() {
    lateinit var listStudents: ListView
    lateinit var adapter: CustomAdapter
    lateinit var Students:ArrayList<Students>
    lateinit var progressDialog: ProgressDialog
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students)
        listStudents = findViewById(R.id.mListStudents)
        Students = ArrayList()
        adapter = CustomAdapter(this,Students)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("loading")
        progressDialog.setMessage("Please Wait..")
        val reference = FirebaseDatabase.getInstance().getReference().child("Students")
        progressDialog.show()
        reference.addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Students.clear()
                for (snap in snapshot.children){
                    var student = snap.getValue(com.example.buupass2.Students::class.java)
                    Students.add(student!!)
                }
                adapter.notifyDataSetChanged()
                progressDialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"DB locked",Toast.LENGTH_LONG).show()
            }
        })
        listStudents.adapter = adapter

        listStudents.setOnItemClickListener { adapterView, view, i, l ->
            val id = Students.get(i).id
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Deleting record!")
            alertDialog.setMessage("Are you sure you want to delete this record?")
            alertDialog.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->

            })
            alertDialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                val deleteRef = FirebaseDatabase.getInstance().getReference().child("Students/$id")
                deleteRef.removeValue().addOnCompleteListener {
                    task->
                    if (task.isSuccessful){
                        Toast.makeText(applicationContext,"Record deleted successfully", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(applicationContext,"Deletion failed", Toast.LENGTH_SHORT).show()
                    }
                }
            })
            alertDialog.create().show()
        }

        }
    }




