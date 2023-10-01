package com.david.f1stats.ui.circuits

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.f1stats.databinding.CircuitItemBinding
import com.david.f1stats.domain.model.Circuit

class CircuitsAdapter (private val listener: CircuitItemListener) : RecyclerView.Adapter<CircuitsAdapter.CircuitViewHolder>() {

    interface CircuitItemListener {
        fun onClickedCircuit(circuitId: Int)
    }

    private val items = ArrayList<Circuit>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<Circuit>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircuitViewHolder {
        val binding: CircuitItemBinding = CircuitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CircuitViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CircuitViewHolder, position: Int) = holder.bind(items[position])

    inner class CircuitViewHolder(private val itemBinding: CircuitItemBinding, private val listener: CircuitItemListener) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var circuit: Circuit

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: Circuit) {
            this.circuit = item
            itemBinding.tvName.text = item.name
            itemBinding.tvCountry.text = item.country
        }

        override fun onClick(v: View?) {
            listener.onClickedCircuit(circuit.id)
        }
    }
}