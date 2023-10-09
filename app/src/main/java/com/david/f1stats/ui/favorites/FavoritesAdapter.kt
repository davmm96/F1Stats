package com.david.f1stats.ui.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.data.model.favoriteRace.FavoriteRace
import com.david.f1stats.databinding.ItemFavoriteRaceBinding

class FavoritesAdapter (private val listener: FavoriteItemListener) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    interface FavoriteItemListener {
        fun removeFavorite(idRace: Int)
    }

    private val items = ArrayList<FavoriteRace>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<FavoriteRace>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding: ItemFavoriteRaceBinding = ItemFavoriteRaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) = holder.bind(items[position])

    inner class FavoritesViewHolder(private val itemBinding: ItemFavoriteRaceBinding, private val listener: FavoriteItemListener) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var race: FavoriteRace

        init {
            itemBinding.ivFavorite.setOnClickListener(this)
        }

        fun bind(item: FavoriteRace) {
            this.race = item
            itemBinding.raceName.text = item.competition
            itemBinding.raceCountry.text = item.country
            itemBinding.raceLaps.text = item.laps
            itemBinding.raceDateDay.text = item.dayInterval
            itemBinding.raceDateMonth.text = item.month
        }

        override fun onClick(v: View?) {
            listener.removeFavorite(race.id)
        }
    }
}