package com.example.noteitapp.adapter


import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteitapp.R
import com.example.noteitapp.model.DataClass
import com.example.noteitapp.viewmodel.ViewModel
import kotlinx.android.synthetic.main.list_item.view.*
import kotlin.random.Random


class DataAdapter() : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    private var noteList= emptyList<DataClass>()

    //lambda function for item click
    var onItemClick:((DataClass) -> Unit)?=null


    private lateinit var mUserViewModel: ViewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapter.ViewHolder  {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DataAdapter.ViewHolder, position: Int) {
        val currentItem=noteList[position]

        //set random color to title
        val r = Random
        val red = r.nextInt(255 - 0 + 1) + 0
        val green = r.nextInt(255 - 0 + 1) + 0
        val blue = r.nextInt(255 - 0 + 1) + 0

        val draw = GradientDrawable()
        draw.shape = GradientDrawable.RECTANGLE
        draw.setColor(Color.rgb(red, green, blue))

        //to set data to current item
        holder.itemView.text_view_title.text = currentItem.title.toString()
        holder.itemView.text_view_title.background=draw
        holder.itemView.text_view_note.text = currentItem.description.toString()

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(noteList[position])
        }


    }


    override fun getItemCount(): Int {
       return noteList.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    // set data to list
    fun setData(user:List<DataClass>){
        this.noteList=user
        notifyDataSetChanged()
    }






}