package com.example.noteitapp

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.noteitapp.adapter.DataAdapter
import com.example.noteitapp.model.DataClass
import com.example.noteitapp.viewmodel.ViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_data.*
import kotlinx.android.synthetic.main.edit_data.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity()  {

    private lateinit var mViewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter=DataAdapter()
        recyclerView.adapter=adapter



        recyclerView.layoutManager=GridLayoutManager(this,2)


        //Initialize View model
        mViewModel=ViewModelProvider(this).get(ViewModel::class.java)

        //read all data to recyclerview
        mViewModel.readAllData.observe(this,{user ->
            adapter.setData(user)
        })


        floatingActionButton.setOnClickListener {
            showNoteDialog()
        }

        adapter.onItemClick={notes ->showActionDialog(notes )}
    }

    //Dialog menu for select actions to edit or delete
    private fun showActionDialog(notes: DataClass) {
        val builder=AlertDialog.Builder(this)
        builder.setTitle("Select Action")
        builder.setPositiveButton("Delete"){_,_ ->
           mViewModel.deleteData(notes)
            Toast.makeText(this,"Deleted",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Update"){_,_ ->
            showUpdateDialog(notes)
            Toast.makeText(this,"Update",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNeutralButton("Cancel"){_,_ ->
            Toast.makeText(this,"No Action Take",
                Toast.LENGTH_SHORT).show()
        }

        builder.create().show()


    }

    //Update Notes
    private fun showUpdateDialog(notes: DataClass) {
        val dialog=Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.edit_data)
        dialog.setCancelable(true)

        val layoutParams=WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width=WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT

        val edtDesc:EditText=dialog.edtUpdateDesc
        val edtTitle:EditText=dialog.edtUpdateTitle

        edtTitle.setText(notes.title)
        edtDesc.setText(notes.description)

        dialog.findViewById<Button>(R.id.btnUpdateCancel).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btnUpdate).setOnClickListener {
            if (inputCheck(edtTitle.text.toString(),edtDesc.text.toString())){
                val notes=DataClass(notes.id,edtTitle.text.toString(),edtDesc.text.toString())
                mViewModel.updateData(notes)

                Toast.makeText(this,"Updated Successfully",
                    Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }else{
                Toast.makeText(this,"Please Enter All Field",
                    Toast.LENGTH_SHORT).show()
            }
        }


        dialog.show()
        dialog.window!!.attributes=layoutParams
    }

    //To add data to room db
    private fun showNoteDialog() {
        val dialog=Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.add_data)
        dialog.setCancelable(true)

        val layoutParams=WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width=WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT

        val edtDesc:EditText=dialog.edtDesc
        val edtTitle:EditText=dialog.edtTitle

        dialog.findViewById<Button>(R.id.btnAddCancel).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btnAddAdd).setOnClickListener {
            if (inputCheck(edtTitle.text.toString(),edtDesc.text.toString())){
                val notes=DataClass(0,edtTitle.text.toString(),edtDesc.text.toString())
                mViewModel.addData(notes)

                Toast.makeText(this,"${edtTitle.text.toString()} Successfully added",
                                        Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }else{
                Toast.makeText(this,"Please Enter All Field",
                    Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
        dialog.window!!.attributes=layoutParams

    }

    //Check input from edittext
    private fun inputCheck(tittle: String, desc: String):Boolean{
        return !(TextUtils.isEmpty(tittle)&& TextUtils.isEmpty(desc))
    }

    //menu inflater
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    //for action menu item to delete all data from room db
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.deleteMenu){
            val builder=AlertDialog.Builder(this)
            builder.setTitle("Are You sure You Want Delete All Notes?")
            builder.setPositiveButton("Yes"){_,_ ->
                mViewModel.deleteAllData()
                Toast.makeText(this,"Deleted",
                    Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("No") { _, _ ->

            }
            builder.create().show()
        }
        return super.onOptionsItemSelected(item)
    }

}

