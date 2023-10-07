package com.david.f1stats.ui.ranking.teams

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.domain.model.RankingTeam
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.david.f1stats.R
import com.david.f1stats.databinding.ItemRankingTeamBinding

class RankingTeamsAdapter(private val listener: RankingItemListener) : RecyclerView.Adapter<RankingTeamsAdapter.RankingViewHolder>()  {
    interface RankingItemListener {
        fun onClickedRankingTeam(rankingTeamId: Int)
    }

    private val items = ArrayList<RankingTeam>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<RankingTeam>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val binding: ItemRankingTeamBinding = ItemRankingTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankingViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) = holder.bind(items[position])

    inner class RankingViewHolder(private val itemBinding: ItemRankingTeamBinding, private val listener: RankingItemListener) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var rankingTeam: RankingTeam

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: RankingTeam) {
            this.rankingTeam = item

            if(item.position == 1) {
                itemBinding.root.setCardBackgroundColor(itemBinding.root.context.getColor(R.color.dark_grey))
                itemBinding.tvName.setTextColor(itemBinding.root.context.getColor(R.color.white))
                itemBinding.tvPosition.setTextColor(itemBinding.root.context.getColor(R.color.white))
            }
            else {
                itemBinding.root.setCardBackgroundColor(itemBinding.root.context.getColor(R.color.white))
                itemBinding.tvName.setTextColor(itemBinding.root.context.getColor(R.color.black))
                itemBinding.tvPosition.setTextColor(itemBinding.root.context.getColor(R.color.black))
            }

            itemBinding.tvName.text = item.name
            itemBinding.tvPoints.text = item.points
            itemBinding.tvPosition.text = item.position.toString()
        }

        override fun onClick(v: View?) {
            listener.onClickedRankingTeam(rankingTeam.idTeam)
        }
    }
}