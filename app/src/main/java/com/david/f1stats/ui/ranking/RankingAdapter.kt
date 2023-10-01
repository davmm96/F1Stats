package com.david.f1stats.ui.ranking

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.databinding.RankingDriverItemBinding
import com.david.f1stats.domain.model.RankingDriver

class RankingAdapter (private val listener: RankingItemListener) : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

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
        val binding: RankingDriverItemBinding = RankingDriverItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankingViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) = holder.bind(items[position])

    inner class RankingViewHolder(private val itemBinding: RankingDriverItemBinding, private val listener: RankingItemListener) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var rankingDriver: RankingDriver

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: RankingDriver) {
            this.rankingDriver = item
            itemBinding.tvName.text = item.name
            itemBinding.tvPoints.text = item.points.toString()
            itemBinding.tvTeam.text = item.team
        }

        override fun onClick(v: View?) {
            listener.onClickedDriver(rankingDriver.idDriver)
        }
    }
}