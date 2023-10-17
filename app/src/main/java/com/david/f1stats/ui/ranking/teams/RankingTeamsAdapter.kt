package com.david.f1stats.ui.ranking.teams

import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.domain.model.RankingTeam
import android.view.LayoutInflater
import android.view.ViewGroup
import com.david.f1stats.R
import com.david.f1stats.databinding.ItemRankingTeamBinding
import com.david.f1stats.utils.Constants.FIRST_POSITION_SIZE
import com.david.f1stats.utils.getColor

class RankingTeamsAdapter(private val listener: RankingItemListener) : RecyclerView.Adapter<RankingTeamsAdapter.RankingViewHolder>()  {
    interface RankingItemListener {
        fun onClickedRankingTeam(rankingTeamId: Int)
    }

    private val items = ArrayList<RankingTeam>()

    fun setItems(items: ArrayList<RankingTeam>) {
        this.items.clear()
        this.items.addAll(items)
        notifyItemRangeChanged(0, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val binding: ItemRankingTeamBinding = ItemRankingTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankingViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) = holder.bind(items[position])

    inner class RankingViewHolder(
        private val itemBinding: ItemRankingTeamBinding,
        private val listener: RankingItemListener)
        :RecyclerView.ViewHolder(itemBinding.root){

        init {
            itemBinding.root.setOnClickListener {
                val currentItem = items[adapterPosition]
                listener.onClickedRankingTeam(currentItem.idTeam)
            }
        }

        fun bind(item: RankingTeam) {
            itemBinding.apply {
                setStyle(item)
                tvName.text = item.name
                tvPoints.text = item.points
                tvPosition.text = item.position.toString()
                verticalSeparator.setBackgroundColor(root.context.getColor(getColor(item.idTeam)))
            }
        }

        private fun setStyle(item: RankingTeam) {
            if (item.position == 1) {
                setFirstPositionStyle()
            } else {
                setDefaultPositionStyle()
            }
        }

        private fun setFirstPositionStyle() {
            itemBinding.apply {
                root.setCardBackgroundColor(itemBinding.root.context.getColor(R.color.dark_grey))
                tvName.setTextColor(itemBinding.root.context.getColor(R.color.white))
                tvPosition.setTextColor(itemBinding.root.context.getColor(R.color.white))
                ivArrow.setImageResource(R.drawable.arrow_right_white)
                tvName.textSize = FIRST_POSITION_SIZE
            }
        }

        private fun setDefaultPositionStyle() {
            itemBinding.apply {
                root.setCardBackgroundColor(itemBinding.root.context.getColor(R.color.white))
                tvName.setTextColor(itemBinding.root.context.getColor(R.color.black))
                tvPosition.setTextColor(itemBinding.root.context.getColor(R.color.black))
                ivArrow.setImageResource(R.drawable.arrow_right)
                tvName.textSize = 14f
            }
        }
    }
}
