package ai.ftech.babyphoto.screen.home

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.model.AlbumBaby
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class BabyHomeAdapter(
    val context: Context,
    private val dataViewBabyHome: MutableList<AlbumBaby>,
    private val dataTitle: String,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val viewTypeAdd: Int = 0;
        private const val viewTypeBaby: Int = 1;
    }

    //create item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == viewTypeAdd) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_add_baby_layout, parent, false)
            return ViewTitle(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_view_baby_layout, parent, false)
            return ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.apply {
            when (holder) {
                is ViewTitle -> holder.bind(dataTitle)
                is ViewHolder -> holder.bind(position - 1)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataViewBabyHome.count() + 1
    }

    inner class ViewTitle(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtTitle1: TextView = itemView.findViewById(R.id.tvHomeAddBaby)
        fun bind(title: String) {
            txtTitle1.text = title
            txtTitle1.setOnClickListener {
                Toast.makeText(context, "this is title", Toast.LENGTH_LONG).show()
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivHomeViewBaby: ImageView = view.findViewById(R.id.ivHomeViewBaby)
        private val tvHomeViewBabyName: TextView = view.findViewById(R.id.tvHomeViewBabyName)
        private val tvHomeViewBabyCountItem: TextView =
            view.findViewById(R.id.tvHomeViewBabyCountItem)
        private val tvHomeViewBabyItem: TextView = view.findViewById(R.id.tvHomeViewBabyItem)

        fun bind(position: Int) {
            ivHomeViewBaby.setImageURI(Uri.parse(dataViewBabyHome[position].urlimage))
            tvHomeViewBabyName.text = dataViewBabyHome[position].name
            tvHomeViewBabyCountItem.text = dataViewBabyHome[position].amountimage
            tvHomeViewBabyItem.text = "images"
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return viewTypeAdd
        }
        return viewTypeBaby
    }
}