package com.jbarcelona.jobboardapp.ui.adapter

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jbarcelona.jobboardapp.R
import com.jbarcelona.jobboardapp.network.model.Job


class JobAdapter(
    var jobList: List<Job>,
    val listener: OnJobClickListener?
) : RecyclerView.Adapter<JobAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.job_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jobItem = jobList[position]
        holder.apply {
            tvTitle.text = jobItem.title
            tvDescription.text = jobItem.description
            btnApply.setOnClickListener {
                listener?.onApplyJob(jobItem)
            }
            ibMenu.setOnClickListener {
                val popup = PopupMenu(it.context, it)
                popup.inflate(R.menu.menu_more_action)

                popup.setOnMenuItemClickListener { item: MenuItem? ->
                    when (item?.itemId) {
                        R.id.update -> {
                            listener?.onUpdateJob(jobItem)
                        }
                        R.id.delete -> {
                            listener?.onDeleteJob(jobItem)
                        }
                    }

                    true
                }
                popup.show()
            }
        }
    }

    override fun getItemCount() = jobList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvDescription: TextView = view.findViewById(R.id.tv_description)
        val btnApply: Button = view.findViewById(R.id.btn_apply)
        val ibMenu: ImageButton = view.findViewById(R.id.ib_menu)
    }

    interface OnJobClickListener {
        fun onApplyJob(job: Job)
        fun onUpdateJob(job: Job)
        fun onDeleteJob(job: Job)
    }
}