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
        return createView(position, convertView, parent).apply {
            setOnClickListener { getItem(position)?.let(itemClickListener) }
        }
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_season, parent, false)
        val viewHolder = view.tag as? SeasonViewHolder ?: SeasonViewHolder(view).also { view.tag = it }
        viewHolder.tvSeason.text = getItem(position)?.season
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
