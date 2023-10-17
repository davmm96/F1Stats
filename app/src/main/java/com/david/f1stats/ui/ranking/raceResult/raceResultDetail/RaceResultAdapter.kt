package com.david.f1stats.ui.ranking.raceResult.raceResultDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.databinding.ItemRaceResultBinding
import com.david.f1stats.domain.model.RaceResult
import com.david.f1stats.utils.getColor

class RaceResultAdapter : RecyclerView.Adapter<RaceResultAdapter.RaceResultViewHolder>() {

    private val items = ArrayList<RaceResult>()

    fun setItems(items: ArrayList<RaceResult>) {
        this.items.clear()
        this.items.addAll(items)
        notifyItemRangeChanged(0, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceResultViewHolder {
        val binding: ItemRaceResultBinding = ItemRaceResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RaceResultViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RaceResultViewHolder, position: Int) = holder.bind(items[position])

    inner class RaceResultViewHolder(
        private val itemBinding: ItemRaceResultBinding)
        :RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item: RaceResult) {
            itemBinding.apply {
                tvPos.text = item.position
                tvDriver.text = item.driverAbbr
                tvTime.text = item.time
                tvPoints.text = item.points
                verticalSeparator.setBackgroundColor(root.context.getColor(getColor(item.idTeam)))
            }
        }
    }
}