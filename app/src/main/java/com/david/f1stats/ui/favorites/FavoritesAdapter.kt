package com.david.f1stats.ui.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.data.model.favoriteRace.FavoriteRace
import com.david.f1stats.databinding.ItemFavoriteRaceBinding

class FavoritesAdapter (
    private val favListener: FavoriteItemListener,
    private val navListener: FavoriteNavListener)
    : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    interface FavoriteItemListener {
        fun removeFavorite(idRace: Int)
    }

    interface FavoriteNavListener {
        fun onNavClicked(idRace: Int, country: String)
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
        return FavoritesViewHolder(binding, favListener, navListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) = holder.bind(items[position])

    inner class FavoritesViewHolder(
        private val itemBinding: ItemFavoriteRaceBinding,
        private val favListener: FavoriteItemListener,
        private val navigationListener: FavoriteNavListener):
        RecyclerView.ViewHolder(itemBinding.root){

        private lateinit var race: FavoriteRace

        init {
            itemBinding.ivFavorite.setOnClickListener {
                favListener.removeFavorite(race.id)
            }

            itemBinding.ivRightArrow.setOnClickListener {
                navigationListener.onNavClicked(race.id, race.country)
            }
        }

        fun bind(item: FavoriteRace) {
            this.race = item
            itemBinding.raceName.text = item.competition
            itemBinding.raceCountry.text = item.country
            itemBinding.raceSeason.text = item.season
        }
    }
}