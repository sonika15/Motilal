package com.motilal.project.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.motilal.project.R
import com.motilal.project.activity.DetailActivity
import com.motilal.project.model.TrendingModel


class TrendingAdapter(val list: List<TrendingModel>, var context: Context) :
    RecyclerView.Adapter<TrendingAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = itemView.findViewById(R.id.tvName)
        val fullName: TextView = itemView.findViewById(R.id.tvFullName)
        val desc: TextView = itemView.findViewById(R.id.tvDescription)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data: TrendingModel = list[position]
        holder.name.text = data.name
        holder.fullName.text = data.fullName
        holder.desc.text = data.description

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
             intent.putExtra("description", data.description);
             intent.putExtra("star", data.watchers);
             intent.putExtra("cloneUrl", data.clone_url);
             intent.putExtra("branch", data.default_branch);
            context.startActivity(intent)
        })



    }


}