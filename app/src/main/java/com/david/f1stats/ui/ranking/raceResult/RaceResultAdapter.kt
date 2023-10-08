package com.david.f1stats.ui.ranking.raceResult

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.databinding.ItemRaceResultBinding
import com.david.f1stats.domain.model.RaceResult

class RaceResultAdapter : RecyclerView.Adapter<RaceResultAdapter.RaceResultViewHolder>() {

    private val items = ArrayList<RaceResult>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<RaceResult>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceResultViewHolder {
        val binding: ItemRaceResultBinding = ItemRaceResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RaceResultViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RaceResultViewHolder, position: Int) = holder.bind(items[position])

    inner class RaceResultViewHolder(private val itemBinding: ItemRaceResultBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {
        private lateinit var race: RaceResult

        fun bind(item: RaceResult) {
            this.race = item
            itemBinding.tvPos.text = item.position
            itemBinding.tvDriver.text = item.driverAbbr
            itemBinding.tvTime.text = item.time
            itemBinding.tvPoints.text = item.points
        }

        override fun onClick(p0: View?) {}
    }
}