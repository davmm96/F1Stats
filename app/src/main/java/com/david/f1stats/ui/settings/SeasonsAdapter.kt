package com.david.f1stats.ui.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.david.f1stats.R
import com.david.f1stats.domain.model.Season

class SeasonsAdapter(
    private val context: Context,
    private var seasons: MutableList<Season>,
    private val itemClickListener: (Season) -> Unit
) : ArrayAdapter<Season>(context, R.layout.item_season, seasons) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: SeasonViewHolder
        val view: View

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_season, parent, false)
            viewHolder = SeasonViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as SeasonViewHolder
        }

        val season = getItem(position)
        viewHolder.tvSeason.text = season?.season

        // Set click listener
        view.setOnClickListener {
            season?.let { itemClickListener(it) }
        }

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: SeasonViewHolder
        val view: View

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_season, parent, false)
            viewHolder = SeasonViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as SeasonViewHolder
        }

        val season = getItem(position)
        viewHolder.tvSeason.text = season?.season

        return view
    }


    private class SeasonViewHolder(view: View) {
        val tvSeason: TextView = view.findViewById(R.id.tvSeason)
    }

    fun setItems(newSeasons: List<Season>) {
        seasons.clear()
        seasons.addAll(newSeasons)
        notifyDataSetChanged()
    }
}


