package com.david.f1stats.ui.races

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.databinding.ItemRaceBinding
import com.david.f1stats.domain.model.Race


class RacesAdapter (private val listener: RaceItemListener) : RecyclerView.Adapter<RacesAdapter.RacesViewHolder>() {

    interface RaceItemListener {
        fun onClickedRace(idCompetition: Int, country: String, idRace: Int)
    }

    private val items = ArrayList<Race>()

    fun setItems(items: ArrayList<Race>) {
        this.items.clear()
        this.items.addAll(items)
        notifyItemRangeChanged(0, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RacesViewHolder {
        val binding: ItemRaceBinding = ItemRaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RacesViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RacesViewHolder, position: Int) = holder.bind(items[position])

    inner class RacesViewHolder(private val itemBinding: ItemRaceBinding, private val listener: RaceItemListener) :
        RecyclerView.ViewHolder(itemBinding.root){

        init {
            itemBinding.root.setOnClickListener{
                val currentItem = items[adapterPosition]
                listener.onClickedRace(currentItem.idCompetition, currentItem.country, currentItem.idRace)
            }
        }

        fun bind(item: Race) {
            itemBinding.apply {
                baseRaceLayout.raceCountry.text = item.country
                baseRaceLayout.raceCompetition.text = item.competition
                baseRaceLayout.raceLapsSeason.text = item.laps
                raceDateDay.text = item.dayInterval
                raceDateMonth.text = item.month
            }
        }
    }
}
