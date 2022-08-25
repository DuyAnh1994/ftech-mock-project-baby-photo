package ai.ftech.babyphoto.screen.timeline

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.model.AlbumBaby
import ai.ftech.babyphoto.model.Image
import ai.ftech.babyphoto.screen.createalbum.CreateAlbumActivity
import ai.ftech.babyphoto.screen.home.BabyHomeAdapter
import ai.ftech.babyphoto.screen.listimage.ListImageActivity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.system.Os.bind
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class TimelineAdapter(
    val context: Context,
    private val dataImage: List<Image>,
) : RecyclerView.Adapter<TimelineAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timeline_view_album, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataImage[position]
        Picasso.get()
            .load(Uri.parse(item.urlimage))
            .into(holder.ivTimeLineViewImage)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ListImageActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataImage.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val ivTimeLineViewImage: ImageView = itemView.findViewById(R.id.ivTimeLineViewImage)
    }
}


