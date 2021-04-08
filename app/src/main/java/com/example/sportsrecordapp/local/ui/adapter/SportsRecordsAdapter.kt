package com.example.sportsrecordapp.local.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsrecordapp.databinding.ItemSportEventBinding
import com.example.sportsrecordapp.local.model.SportEventResult

class SportsRecordsAdapter(private val sportEventResults: List<SportEventResult>) :
    RecyclerView.Adapter<SportsRecordsAdapter.ViewHolder>() {

    class ViewHolder(private val itemBinding: ItemSportEventBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(sportEventResult: SportEventResult) {
            with(itemBinding) {
                temporalText.text = sportEventResult.winner
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemSportEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(sportEventResults[position])
    }

    override fun getItemCount() = sportEventResults.size
}
