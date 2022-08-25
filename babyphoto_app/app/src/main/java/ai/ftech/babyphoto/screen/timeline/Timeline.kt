package ai.ftech.babyphoto.screen.timeline

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.model.Image
import ai.ftech.babyphoto.screen.listimage.ListImageActivity
import ai.ftech.babyphoto.screen.test.TestActivity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_timeline.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class Timeline : AppCompatActivity(), ITimelineContract.View {
    private lateinit var presenter: TimelinePresenter
    private var idAlbum: String? = "0"
    private var nameAlbum: String? = ""
    private var urlimage: String? = ""
    private var birthday: String? = ""
    private val lImage: MutableList<Image> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_timeline)
        val fabAdd: FloatingActionButton = findViewById(R.id.fabTimeLineAdd)
        presenter = TimelinePresenter(this)

        val bundle: Bundle? = intent.extras
        idAlbum = bundle?.getString("idalbum")
        nameAlbum = bundle?.getString("nameAlbum")
        birthday = bundle?.get("birthday").toString()
        urlimage = bundle?.getString("urlimage")


        Picasso.get()
            .load(Uri.parse(urlimage))
            .into(civTimeLineAvatarCirCle)
        val formatter = DateTimeFormatter.ofPattern("dd/M/yyyy")
        birthday.hashCode()
        var dt = LocalDate.parse("$birthday", formatter)

        tvTimeLineCountYear.text = (dt.until(LocalDate.now()).years).toString()
        tvTimeLineCountMonth.text = (dt.until(LocalDate.now()).months).toString()
        tvTimeLineCountDay.text = (dt.until(LocalDate.now()).days).toString()

        val format = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
        tvTimeLineItemDateStart.text = (dt.format(format)).toString()
        tvTimeLineItemDateEnd.text = (LocalDate.now().format(format)).toString()
        tvTimelineItemTitle.text = nameAlbum.toString()

        val rvTimelineViewImage: RecyclerView = findViewById(R.id.rvTimelineViewImage)
        val timelineAdapter = TimelineAdapter(this, lImage)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        rvTimelineViewImage.layoutManager = staggeredGridLayoutManager
        rvTimelineViewImage.adapter = timelineAdapter

        srlTimeLine.isRefreshing = true
        presenter.getImage(idAlbum)

        srlTimeLine.setOnRefreshListener {
            lImage.clear()
            rvTimelineViewImage.adapter!!.notifyDataSetChanged()
            srlTimeLine.isRefreshing = true
            presenter.getImage(idAlbum)
        }

        fabTimeLineAdd.setOnClickListener {
            var intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }

        fabAdd.setOnClickListener {
            var intent = Intent(this, ListImageActivity::class.java)
            intent.putExtra("idalbum",idAlbum)
            intent.putExtra("nameAlbum",nameAlbum)
            intent.putExtra("birthday",birthday)
            intent.putExtra("urlimage",urlimage)
            startActivity(intent)
        }

        ibTimeLineBack.setOnClickListener {
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onGetImage(state: TimelineState, message: String, lImage: List<Image>) {
        when (state) {
            TimelineState.SUCCESS -> {
                srlTimeLine.isRefreshing = false
                this.lImage.addAll(lImage)

                rvTimelineViewImage.adapter!!.notifyDataSetChanged()

                if (lImage.isEmpty()) return

            }
            TimelineState.GET_IMAGE_FAILED -> {
                Toast.makeText(this, "Get image failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}