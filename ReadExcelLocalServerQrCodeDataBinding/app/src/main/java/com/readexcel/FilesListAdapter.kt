package com.readexcel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.readexcel.databinding.ListViewBinding
import kotlinx.android.synthetic.main.list_view.view.*
import java.io.File

class FilesListAdapter (private val context: Context) : RecyclerView.Adapter<FilesListAdapter.MyViewHolder>() {
    private val files: MutableList<ExcellFile> = arrayListOf()
    class MyViewHolder(val binding: ListViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView1: TextView = itemView.tv_title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
         MyViewHolder(
            ListViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun getItemCount() =files.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: ExcellFile = files[position]
        holder.binding.tvTitle.text = currentItem.name
        holder.itemView.setOnClickListener {
            openFile(currentItem.path)

        }
        holder.itemView.ivDelete.setOnClickListener {
            deleteSelectedFile(currentItem.path)
        }
    }
    private fun deleteSelectedFile(filePath: String) {
        val file = File(filePath)
        if (file.exists()) {
            file.delete()
        }
        if (context is FilesList) {
            context.getFiles()
        }
    }

    private fun openFile(path: String) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("excellPath", path)
        context.startActivity(intent)

    }
    fun clear() {
        val oldSize = files.size
        files.clear()
        notifyItemRangeRemoved(0, oldSize)
    }

    fun setData(list: MutableList<ExcellFile>) {
        this.files.addAll(list)
        notifyItemRangeInserted(0, files.size)
    }
}