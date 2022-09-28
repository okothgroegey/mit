package com.example.buupass2

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var edtName1: EditText
    lateinit var edtName2: EditText
    lateinit var edtAdmNo: EditText
    lateinit var buttonSave: Button
    lateinit var buttonView: Button
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtName1 = findViewById(R.id.mEdtName1)
        edtName2 = findViewById(R.id.mEdtName2)
        edtAdmNo = findViewById(R.id.mEdtAdmNo)
        buttonSave = findViewById(R.id.mBtnSave)
        buttonView = findViewById(R.id.mBtnView)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Saving")
        progressDialog.setMessage("Please wait...")

        buttonSave.setOnClickListener {

            val name1 = edtName1.text.toString().trim()
            val name2 = edtName2.text.toString().trim()
            val admNo = edtAdmNo.text.toString().trim()
            val currentTime = System.currentTimeMillis().toString()
            if (name1.isEmpty()){
                edtName1.setError("Please fill this input")
                edtName1.requestFocus()
            }else if (name2.isEmpty()){
                edtName2.setError("Please fill this input")
                edtName2.requestFocus()
            }else if (admNo.isEmpty()) {
                edtAdmNo.setError("Please fill this input")
                edtAdmNo.requestFocus()
            }else{
                val userData = Students(name1,name2,admNo,currentTime)
                val reference = FirebaseDatabase.getInstance().getReference().child("Students/$currentTime")
                progressDialog.show()
                reference.setValue(userData).addOnCompleteListener {
                    task->
                    progressDialog.dismiss()
                    if (task.isSuccessful){
                        Toast.makeText(applicationContext, "Data saved successfully",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(applicationContext, task.exception?.message,Toast.LENGTH_LONG).show()
                    }
                }
        }
        buttonView.setOnClickListener {
            startActivity(Intent(applicationContext,StudentsActivity::class.java))
        }

        }
    }
}