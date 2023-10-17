package com.david.f1stats.ui.ranking.raceResult

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.R
import com.david.f1stats.databinding.ItemFavoriteRaceBinding
import com.david.f1stats.domain.model.Race

class RankingRacesAdapter (
    private val favListener: RankingRacesFavListener,
    private val navigationListener: RankingRacesNavListener,
    private var favoriteRaces: List<Int>)
    : RecyclerView.Adapter<RankingRacesAdapter.RankingRacesViewHolder>() {

    interface RankingRacesFavListener {
        fun onFavClicked(race: Race)
    }

    interface RankingRacesNavListener {
        fun onNavClicked(idRace: Int, country: String)
    }

    private val items = ArrayList<Race>()

    fun setItems(items: ArrayList<Race>) {
        this.items.clear()
        this.items.addAll(items)
        notifyItemRangeChanged(0, items.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFavoriteRaces(newFavoriteRaces: List<Int>) {
        this.favoriteRaces = newFavoriteRaces
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingRacesViewHolder {
        val binding: ItemFavoriteRaceBinding = ItemFavoriteRaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankingRacesViewHolder(binding, favListener, navigationListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RankingRacesViewHolder, position: Int) = holder.bind(items[position])

    inner class RankingRacesViewHolder(
        private val itemBinding: ItemFavoriteRaceBinding,
        private val favListener: RankingRacesFavListener,
        private val navigationListener: RankingRacesNavListener
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.ivFavorite.setOnClickListener {
                favListener.onFavClicked(getCurrentItem())
            }

            itemBinding.baseRaceLayout.ivRightArrow.setOnClickListener {
                val currentItem = getCurrentItem()
                navigationListener.onNavClicked(currentItem.idRace, currentItem.country)
            }
        }

        fun bind(item: Race) {
            itemBinding.apply {
                baseRaceLayout.raceCountry.text = item.country
                baseRaceLayout.raceCompetition.text = item.competition
                baseRaceLayout.raceLapsSeason.text = item.laps
                ivFavorite.setImageResource(setFavoriteIcon(item))
            }
        }

        private fun getCurrentItem(): Race {
            return items[adapterPosition]
        }

        private fun setFavoriteIcon(item: Race): Int {
            return if (favoriteRaces.contains(item.idRace)) {
                R.drawable.icon_favorites
            } else {
                R.drawable.icon_favorites_off
            }
        }
    }
}
