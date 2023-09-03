package com.david.f1stats.ui.races

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.databinding.RaceItemBinding
import com.david.f1stats.domain.model.Race


class RacesAdapter (private val listener: RaceItemListener) : RecyclerView.Adapter<RacesAdapter.RacesViewHolder>() {

    interface RaceItemListener {
        fun onClickedRace(raceId: Int, country: String)
    }

    private val items = ArrayList<Race>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<Race>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RacesViewHolder {
        val binding: RaceItemBinding = RaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RacesViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RacesViewHolder, position: Int) = holder.bind(items[position])

    inner class RacesViewHolder(private val itemBinding: RaceItemBinding, private val listener: RaceItemListener) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var race: Race

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: Race) {
            this.race = item
            itemBinding.raceName.text = item.competition
            itemBinding.raceCountry.text = item.country
            itemBinding.raceLaps.text = item.laps
            itemBinding.raceDateDay.text = item.day
            itemBinding.raceDateMonth.text = item.month
        }

        override fun onClick(v: View?) {
            listener.onClickedRace(race.id, race.country)
        }
    }
}
