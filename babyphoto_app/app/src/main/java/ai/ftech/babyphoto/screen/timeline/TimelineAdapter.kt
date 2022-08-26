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
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class TimelineAdapter(
    private var context: Context
) : RecyclerView.Adapter<TimelineAdapter.ViewHolder>() {


    var dataImage: MutableList<Image> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    var callBack: ICallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timeline_view_album, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataImage[position]
//        Picasso.get()
//            .load(item.urlimage)
//            .into(holder.ivTimeLineViewImage)

        try {
            Glide.with(context)
                .load(item.urlimage)
//                .error("https://images.ctfassets.net/hrltx12pl8hq/7yQR5uJhwEkRfjwMFJ7bUK/dc52a0913e8ff8b5c276177890eb0129/offset_comp_772626-opt.jpg?fit=fill&w=800&h=300")
                .placeholder(R.drawable.humo)
                .into(holder.ivTimeLineViewImage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return dataImage.size
    }

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val ivTimeLineViewImage: ImageView = itemView.findViewById(R.id.ivTimeLineViewImage)

        init {
            itemView.setOnClickListener {
                callBack?.onClick()
            }
        }
    }

    interface ICallBack{
        fun onClick()
    }
}


