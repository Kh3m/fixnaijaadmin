package com.khemportfolio.fixnaijateam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khemportfolio.fixnaijateam.R

class ProblemAdapter(val data: List<Problem>) : RecyclerView.Adapter<ProblemAdapter.ProblemViewHolder>() {

    inner class ProblemViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.rv_problem, parent, false)
        return ProblemViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ProblemViewHolder, position: Int) {
        bind(holder, position)
    }

    private fun bind(holder: ProblemViewHolder, position: Int) {
        holder.rootView.findViewById<TextView>(R.id.date).text = data[position].date
        holder.rootView.findViewById<TextView>(R.id.problem_details).text = data[position].problemDetails
        holder.rootView.findViewById<TextView>(R.id.phone).text = data[position].phone
        holder.rootView.findViewById<TextView>(R.id.email).text = data[position].email
    }

    override fun getItemCount(): Int {
        return data.size
    }
}