package ai.ftech.babyphoto.screen.home

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.model.Account
import ai.ftech.babyphoto.model.AlbumBaby
import ai.ftech.babyphoto.screen.register.ActivityEnterEmail
import ai.ftech.babyphoto.screen.timeline.TimelineAdapter
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.scale
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import android.icu.number.NumberRangeFormatter
import android.text.format.Time
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson


class BabyHomeAdapter(
    val context: Context,
    private val dataViewBabyHome: MutableList<AlbumBaby> = mutableListOf(),
    private val dataTitle: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val viewTypeAdd: Int = 0;
        private const val viewTypeBaby: Int = 1;
    }

    private lateinit var mListener: onItemClickListenerr

    interface onItemClickListenerr {
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListenerr){
        mListener = listener
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
            view.setOnClickListener {

            }
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
//            val decodedString: ByteArray =
//                Base64.decode(dataViewBabyHome[position].urlimage, Base64.DEFAULT)
//            var decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
//            val height = 150
//            val factor: Float = (height * 1.0 / decodedByte.height).toFloat()
//            decodedByte = Bitmap.createScaledBitmap(
//                decodedByte,
//                (decodedByte.width * factor * 1.0).toInt(), height, true
//            )
//            Glide.with(context).load(dataViewBabyHome[position].urlimage).into(ivHomeViewBaby)
//            Picasso.get()
//                .load(Uri.parse(dataViewBabyHome[position].urlimage))
//                .into(ivHomeViewBaby)
//            ivHomeViewBaby.setImageBitmap(decodedByte)
//            ivHomeViewBaby.setImageURI(null)
//            ivHomeViewBaby.setImageURI(Uri.parse(dataViewBabyHome[position].urlimage))
            Picasso.get().load(dataViewBabyHome[position].urlimage).into(ivHomeViewBaby)
            tvHomeViewBabyName.text = dataViewBabyHome[position].name
            tvHomeViewBabyCountItem.text = dataViewBabyHome[position].amountimage
            tvHomeViewBabyItem.text = "images"
            itemView.setOnClickListener {
//                POSITION0 = position as Int
//                POSITION = dataViewBabyHome[position].idalbum as Int
//                val intent = Intent(this, TimelineAdapter::class.java)
//                intent.putExtra("account", )
//                startActivity(intent)
                if (this@BabyHomeAdapter.mListener !=null){
                    if (position!=RecyclerView.NO_POSITION)
                        this@BabyHomeAdapter.mListener.onItemClick(position)
                }
            }
        }
//        init {
//            itemView.setOnClickListener {
//                listener.onItemClick(adapterPosition)
//            }
//        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return viewTypeAdd
        }
        return viewTypeBaby
    }
}