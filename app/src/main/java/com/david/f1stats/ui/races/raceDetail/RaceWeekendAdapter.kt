package com.david.f1stats.ui.races.raceDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.databinding.ItemRaceWeekendBinding
import com.david.f1stats.domain.model.RaceDetail
import com.david.f1stats.domain.model.StatusRaceEnum
import com.david.f1stats.domain.model.TypeRaceEnum

class RaceWeekendAdapter (
    private val listener: CalendarListener
)
    : RecyclerView.Adapter<RaceWeekendAdapter.RaceWeekendViewHolder>() {

    interface CalendarListener {
        fun onCalendarClicked(title:String, dateCalendar: Long)
    }

    private val items = ArrayList<RaceDetail>()

    fun setItems(items: ArrayList<RaceDetail>) {
        this.items.clear()
        this.items.addAll(items)
        notifyItemRangeChanged(0, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceWeekendViewHolder {
        val binding: ItemRaceWeekendBinding = ItemRaceWeekendBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return RaceWeekendViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RaceWeekendViewHolder, position: Int) = holder.bind(items[position])

    inner class RaceWeekendViewHolder(
        private val itemBinding: ItemRaceWeekendBinding,
        private val listener: CalendarListener
    )
        :RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.raceWeekendAddToCalendar.setOnClickListener {
                val currentItem = items[adapterPosition]
                val title = currentItem.type.getString(itemBinding.root.context)

                listener.onCalendarClicked(title,currentItem.dateCalendar)
            }
        }

        fun bind(item: RaceDetail) {
            itemBinding.apply {
                if (item.type == TypeRaceEnum.NONE || item.status != StatusRaceEnum.SCHEDULED) {
                    root.visibility = View.GONE
                }
                raceWeekendText.text = item.type.getString(root.context)
                raceWeekendDay.text = item.day
                raceWeekendMonth.text = item.month
                raceWeekendHour.text = item.hour
            }
        }
    }
}