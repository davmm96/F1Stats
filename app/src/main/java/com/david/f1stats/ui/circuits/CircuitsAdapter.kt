package com.david.f1stats.ui.circuits

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.R
import com.david.f1stats.databinding.ItemCircuitBinding
import com.david.f1stats.databinding.ItemCircuitInfoBinding
import com.david.f1stats.domain.model.Circuit

class CircuitsAdapter (private val listener: CircuitItemListener) : RecyclerView.Adapter<CircuitsAdapter.CircuitViewHolder>() {

    interface CircuitItemListener {
        fun onClickedCircuit(imageUrl: String)
    }

    private val items = ArrayList<Circuit>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<Circuit>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircuitViewHolder {
        val binding: ItemCircuitBinding = ItemCircuitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CircuitViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CircuitViewHolder, position: Int) = holder.bind(items[position])

    inner class CircuitViewHolder(private val itemBinding: ItemCircuitBinding, private val listener: CircuitItemListener) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var circuit: Circuit

        init {
            itemBinding.btnAction.setOnClickListener(this)
        }

        fun bind(item: Circuit) {
            this.circuit = item
            itemBinding.tvName.text = item.name
            itemBinding.tvCountry.text = item.country

            setSectionData(itemBinding.circuitLengthSection, itemBinding.root.context.getString(R.string.circuit_length_label), item.length)
            setSectionData(itemBinding.lapsSection, itemBinding.root.context.getString(R.string.laps_laps), item.laps)
            setSectionData(itemBinding.firstGPSection, itemBinding.root.context.getString(R.string.first_grand_prix_laps), item.firstGP)

            itemBinding.tvLapRecordTime.text = item.lapRecordTime
            itemBinding.tvLapRecordDriver.text = item.lapRecordDriver
        }

        private fun setSectionData(section: ItemCircuitInfoBinding, label: String, value: String) {
            val tvLabel = section.tvLabel
            val tvValue = section.tvValue

            tvLabel.text = label
            tvValue.text = value
        }



        override fun onClick(v: View?) {
            listener.onClickedCircuit(circuit.imageURL)
        }
    }
}