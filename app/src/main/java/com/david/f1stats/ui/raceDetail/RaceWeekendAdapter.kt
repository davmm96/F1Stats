package com.david.f1stats.ui.raceDetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.databinding.ItemRaceWeekendBinding
import com.david.f1stats.domain.model.RaceDetail
import com.david.f1stats.domain.model.TypeRace

class RaceWeekendAdapter (private val listener: CalendarListener) : RecyclerView.Adapter<RaceWeekendAdapter.RaceWeekendViewHolder>() {

    interface CalendarListener {
        fun onCalendarClicked(title:String, dateCalendar: Long)
    }

    private val items = ArrayList<RaceDetail>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<RaceDetail>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceWeekendViewHolder {
        val binding: ItemRaceWeekendBinding = ItemRaceWeekendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RaceWeekendViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RaceWeekendViewHolder, position: Int) = holder.bind(items[position])

    inner class RaceWeekendViewHolder(private val itemBinding: ItemRaceWeekendBinding, private val listener: CalendarListener) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {
            private lateinit var race: RaceDetail

            init {
                itemBinding.raceWeekendAddToCalendar.setOnClickListener(this)
            }

            fun bind(item: RaceDetail) {
                this.race = item

                if(item.type == TypeRace.NONE)
                    itemBinding.root.visibility = View.GONE

                itemBinding.raceWeekendText.text = item.type.getString(itemBinding.root.context)
                itemBinding.raceWeekendDay.text = item.day
                itemBinding.raceWeekendMonth.text = item.month
                itemBinding.raceWeekendHour.text = item.hour
            }

            override fun onClick(v: View?) {
                listener.onCalendarClicked(race.type.getString(itemBinding.root.context), race.dateCalendar)
            }
    }
}