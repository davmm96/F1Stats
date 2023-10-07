package com.david.f1stats.ui.ranking.drivers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.databinding.ItemRankingDriverBinding
import com.david.f1stats.domain.model.RankingDriver
import com.david.f1stats.utils.RoundedTransformation
import com.squareup.picasso.Picasso

class RankingDriversAdapter (private val listener: RankingItemListener) : RecyclerView.Adapter<RankingDriversAdapter.RankingViewHolder>() {

    interface RankingItemListener {
        fun onClickedDriver(driverId: Int)
    }

    private val items = ArrayList<RankingDriver>()


    @SuppressLint("NotifyDataSetChanged")
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

    inner class RankingViewHolder(private val itemBinding: ItemRankingDriverBinding, private val listener: RankingItemListener) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var rankingDriver: RankingDriver

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: RankingDriver) {
            this.rankingDriver = item
            if(item.position == 1)
                itemBinding.root.setCardBackgroundColor(itemBinding.root.context.getColor(android.R.color.darker_gray))

            itemBinding.tvName.text = item.name
            itemBinding.tvTeam.text = item.team
            Picasso.get()
                .load(item.image)
                .transform( RoundedTransformation(30, 0))
                .into(itemBinding.ivDriver)
        }

        override fun onClick(v: View?) {
            listener.onClickedDriver(rankingDriver.idDriver)
        }
    }
}