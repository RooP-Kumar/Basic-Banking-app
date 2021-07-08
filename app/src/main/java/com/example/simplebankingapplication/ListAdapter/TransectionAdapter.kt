package com.example.simplebankingapplication.ListAdapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplebankingapplication.DB.Transections
import com.example.simplebankingapplication.R

class TransectionAdapter(private val context: Context): RecyclerView.Adapter<TransectionAdapter.MyHolder>() {

    private var lastPosition = -1
    private val transections = ArrayList<Transections>()

    class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val toUser = itemView.findViewById<TextView>(R.id.toUserName)
        val fromUser = itemView.findViewById<TextView>(R.id.fromUserName)
        val sentAmount = itemView.findViewById<TextView>(R.id.sentAmount)
        val changeBackground = itemView.findViewById<RelativeLayout>(R.id.changeBackground)
        val statusText = itemView.findViewById<TextView>(R.id.statusText)
        val transectionCardView = itemView.findViewById<CardView>(R.id.transectionCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.transections_recycler_layout, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.fromUser.text = transections[position].fromname
        holder.toUser.text = transections[position].Toname
        holder.sentAmount.text = "$${transections[position].amount}"

        if (position > lastPosition) {
            holder.transectionCardView.translationX = 700f
            holder.transectionCardView.animate().translationX(0f).setDuration(500).setStartDelay((100 + (position * 50)).toLong()).start()
            lastPosition = position
        }

        if (transections[position].isSuccess) {
            holder.changeBackground.setBackgroundColor(Color.parseColor("#95F195"))
            holder.statusText.text = context.resources.getString(R.string.transectioSuccess)
        } else {
            holder.changeBackground.setBackgroundColor(Color.parseColor("#F9948C"))
            holder.statusText.text = context.resources.getString(R.string.transectionFailed)
        }
    }

    override fun getItemCount(): Int {
        return transections.size
    }

    fun updateTransectionList(newTransections : List<Transections>) {
        transections.clear()
        transections.addAll(newTransections)
        notifyDataSetChanged()
    }
}