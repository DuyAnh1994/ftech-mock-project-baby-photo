package ai.ftech.babyphoto.adapter

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.model.Relation
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RelationAdapter(private val dataSet: MutableList<Relation>) :
    RecyclerView.Adapter<RelationAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivCheckBox: ImageView
        val tvName: TextView

        init {
            ivCheckBox = view.findViewById(R.id.ivRelationCheckBox)
            tvName = view.findViewById(R.id.tvRelationName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dialog_relation_fragment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val arrayRelation = dataSet[position]
        holder.ivCheckBox.setImageResource(R.drawable.ic_checkbox_24px)
        holder.tvName.text = arrayRelation.name
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}