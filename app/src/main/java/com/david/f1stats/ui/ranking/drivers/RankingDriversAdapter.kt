package com.david.f1stats.ui.ranking.drivers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.R
import com.david.f1stats.databinding.ItemRankingDriverBinding
import com.david.f1stats.domain.model.RankingDriver
import com.david.f1stats.utils.Constants.DEFAULT_POSITION_SIZE
import com.david.f1stats.utils.Constants.FIRST_POSITION_SIZE
import com.david.f1stats.utils.getColor

class RankingDriversAdapter (private val listener: RankingItemListener) : RecyclerView.Adapter<RankingDriversAdapter.RankingViewHolder>() {

    interface RankingItemListener {
        fun onClickedDriver(driverId: Int)
    }

    private val items = ArrayList<RankingDriver>()


    fun setItems(items: ArrayList<RankingDriver>) {
        this.items.clear()
        this.items.addAll(items)
        notifyItemRangeChanged(0, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val binding: ItemRankingDriverBinding = ItemRankingDriverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankingViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) = holder.bind(items[position])

    inner class RankingViewHolder(
        private val itemBinding: ItemRankingDriverBinding,
        private val listener: RankingItemListener) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private val context = itemBinding.root.context

        init {
            itemBinding.root.setOnClickListener {
                val currentItem = items[adapterPosition]
                listener.onClickedDriver(currentItem.idDriver)
            }
        }

        fun bind(item: RankingDriver) {
            itemBinding.apply {
                setStyle(item)
                tvName.text = item.name
                tvTeam.text = item.team
                tvPoints.text = item.points
                tvPosition.text = item.position.toString()
                verticalSeparator.setBackgroundColor(context.getColor(getColor(item.idTeam)))
            }
        }

        private fun setStyle(item: RankingDriver) {
            if (item.position == 1) {
                setFirstPositionStyle()
            } else {
                setDefaultPositionStyle()
            }
        }

        private fun setFirstPositionStyle() {
            itemBinding.apply {
                root.setCardBackgroundColor(context.getColor(R.color.dark_grey))
                tvName.setTextColor(context.getColor(R.color.white))
                tvTeam.setTextColor(context.getColor(R.color.white))
                tvPosition.setTextColor(context.getColor(R.color.white))
                ivArrow.setImageResource(R.drawable.arrow_right_white)
                tvName.textSize = FIRST_POSITION_SIZE
            }
        }

        private fun setDefaultPositionStyle() {
            itemBinding.apply {
                root.setCardBackgroundColor(context.getColor(R.color.white))
                tvName.setTextColor(context.getColor(R.color.black))
                tvTeam.setTextColor(context.getColor(R.color.dark_grey))
                tvPosition.setTextColor(context.getColor(R.color.black))
                ivArrow.setImageResource(R.drawable.arrow_right)
                tvName.textSize = DEFAULT_POSITION_SIZE
            }
        }
    }
}