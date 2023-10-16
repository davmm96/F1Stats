package com.david.f1stats.ui.ranking.drivers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.R
import com.david.f1stats.databinding.ItemRankingDriverBinding
import com.david.f1stats.domain.model.RankingDriver
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
        notifyDataSetChanged()
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
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var rankingDriver: RankingDriver
        private val context = itemBinding.root.context

        init {itemBinding.root.setOnClickListener(this)}

        fun bind(item: RankingDriver) {
            this.rankingDriver = item
            setStylingBasedOnPosition(item)
            itemBinding.tvName.text = item.name
            itemBinding.tvTeam.text = item.team
            itemBinding.tvPoints.text = item.points
            itemBinding.tvPosition.text = item.position.toString()
            itemBinding.verticalSeparator.setBackgroundColor(context.getColor(getColor(item.idTeam)))
        }

        private fun setStylingBasedOnPosition(item: RankingDriver) {
            if(item.position == 1) {
                itemBinding.root.setCardBackgroundColor(context.getColor(R.color.dark_grey))
                itemBinding.tvName.setTextColor(context.getColor(R.color.white))
                itemBinding.tvTeam.setTextColor(context.getColor(R.color.white))
                itemBinding.tvPosition.setTextColor(context.getColor(R.color.white))
                itemBinding.ivArrow.setImageResource(R.drawable.arrow_right_white)
                itemBinding.tvName.textSize = FIRST_POSITION_SIZE
            }
            else {
                itemBinding.root.setCardBackgroundColor(context.getColor(R.color.white))
                itemBinding.tvName.setTextColor(context.getColor(R.color.black))
                itemBinding.tvTeam.setTextColor(context.getColor(R.color.dark_grey))
                itemBinding.tvPosition.setTextColor(context.getColor(R.color.black))
                itemBinding.ivArrow.setImageResource(R.drawable.arrow_right)
                itemBinding.tvName.textSize = 14f
            }
        }

        override fun onClick(v: View?) {
            listener.onClickedDriver(rankingDriver.idDriver)
        }
    }
}