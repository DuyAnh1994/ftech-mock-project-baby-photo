package ai.ftech.babyphoto.screen.adapter

import ai.ftech.babyphoto.R
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView


class PhotoFolderAdapter(private var dataSet: MutableList<String>) :
    RecyclerView.Adapter<PhotoFolderAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivItem: ImageView

        init {
            ivItem = view.findViewById(R.id.ivImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivItem.setImageBitmap(BitmapFactory.decodeFile(dataSet[position]))
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}