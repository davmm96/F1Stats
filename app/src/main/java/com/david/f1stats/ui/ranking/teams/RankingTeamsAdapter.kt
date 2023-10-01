package com.david.f1stats.ui.ranking.teams

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.domain.model.RankingTeam
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.david.f1stats.databinding.RankingTeamItemBinding
import com.david.f1stats.utils.RoundedTransformation
import com.squareup.picasso.Picasso

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
        val binding: RankingTeamItemBinding = RankingTeamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankingViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) = holder.bind(items[position])

    inner class RankingViewHolder(private val itemBinding: RankingTeamItemBinding, private val listener: RankingItemListener) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var rankingTeam: RankingTeam

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: RankingTeam) {
            this.rankingTeam = item
            itemBinding.tvName.text = item.name
            itemBinding.tvPoints.text = item.points
            Picasso.get()
                .load(item.image)
                .transform( RoundedTransformation(30, 0))
                .into(itemBinding.ivTeam)
        }

        override fun onClick(v: View?) {
            listener.onClickedRankingTeam(rankingTeam.idTeam)
        }
    }
}