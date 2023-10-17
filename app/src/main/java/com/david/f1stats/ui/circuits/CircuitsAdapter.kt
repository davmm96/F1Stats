package com.david.f1stats.ui.circuits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.R
import com.david.f1stats.databinding.ItemCircuitBinding
import com.david.f1stats.databinding.ItemCircuitInfoBinding
import com.david.f1stats.domain.model.Circuit

class CircuitsAdapter (
    private val listener: CircuitItemListener)
    : RecyclerView.Adapter<CircuitsAdapter.CircuitViewHolder>() {

    interface CircuitItemListener {
        fun onClickedCircuit(imageUrl: String)
    }

    private val items = ArrayList<Circuit>()

    fun setItems(items: ArrayList<Circuit>) {
        this.items.clear()
        this.items.addAll(items)
        notifyItemRangeChanged(0, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircuitViewHolder {
        val binding: ItemCircuitBinding = ItemCircuitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CircuitViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CircuitViewHolder, position: Int) = holder.bind(items[position])

    inner class CircuitViewHolder(
        private val itemBinding: ItemCircuitBinding,
        private val listener: CircuitItemListener) :
        RecyclerView.ViewHolder(itemBinding.root){

        init {
            itemBinding.btnAction.setOnClickListener {
                val circuit = items[adapterPosition]
                listener.onClickedCircuit(circuit.imageURL)
            }
        }

        fun bind(item: Circuit) {
            itemBinding.apply {
                tvName.text = item.name
                tvCountry.text = item.country

                setSectionData(circuitLengthSection, root.context.getString(R.string.circuit_length_label), item.length)
                setSectionData(lapsSection, root.context.getString(R.string.laps_laps), item.laps)
                setSectionData(firstGPSection, root.context.getString(R.string.first_grand_prix_laps), item.firstGP)

                tvLapRecordTime.text = item.lapRecordTime
                tvLapRecordDriver.text = item.lapRecordDriver
            }
        }

        private fun setSectionData(section: ItemCircuitInfoBinding, label: String, value: String) {
            section.tvLabel.text = label
            section.tvValue.text = value
        }
    }
}
