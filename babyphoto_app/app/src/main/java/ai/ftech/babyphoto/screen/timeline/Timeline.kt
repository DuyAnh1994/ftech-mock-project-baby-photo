package ai.ftech.babyphoto.screen.timeline

import ai.ftech.babyphoto.R
import ai.ftech.babyphoto.base.service.APIService
import ai.ftech.babyphoto.model.Image
import ai.ftech.babyphoto.model.ResponseModel
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_timeline.*
import kotlinx.android.synthetic.main.create_album_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList


class Timeline : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    private var index1: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        val bundle: Bundle? = intent.extras
        val idalbum = bundle?.get("idalbum")
        val nameAlbum = bundle?.get("nameAlbum")
        val idAlbum = bundle?.get("idAlbum")
        val urlimage = bundle?.get("urlimage")
        var rvTimelineViewImage: RecyclerView = findViewById(R.id.rvTimelineViewImage)

        // on below line we are
        // initializing our list
        var lImage: MutableList<Image> = ArrayList()

        // on below line we are initializing our adapter
        var timelineAdapter = TimelineAdapter(this, lImage)


        // on below line we are setting layout manager for our recycler view
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rvTimelineViewImage.layoutManager = staggeredGridLayoutManager

        // on below line we are setting
        // adapter to our recycler view.
        rvTimelineViewImage.adapter = timelineAdapter

        // on below line we are adding data to our list
//        lImage.add(TimelineViewModel("https://videocdn.geeksforgeeks.org/geeksforgeeks/2DTranslationinComputerGraphics/2DTranslationinComputerGraphics20220628122713-small.png"))
//        lImage.add(TimelineViewModel("https://videocdn.geeksforgeeks.org/geeksforgeeks/PythonProgramforFibonacciSeries/FibonacciseriesinPython20220627183541-small.png"))
//        lImage.add(TimelineViewModel("https://pbs.twimg.com/media/FV6-TWhUsAY92R_.jpg"))
//        lImage.add(TimelineViewModel("https://videocdn.geeksforgeeks.org/geeksforgeeks/PerformCRUDOperationusingFirebaseinFlutter/PerformCRUDOperationusingFirebaseinFlutter20220627152121-small.png"))
//        lImage.add(TimelineViewModel("https://videocdn.geeksforgeeks.org/geeksforgeeks/CProgramtoConvertLowercasetoUppercaseviceversa/CProgramtoConvertLowercasetoUppercase20220627145001-small.png"))
//        lImage.add(TimelineViewModel("https://videocdn.geeksforgeeks.org/geeksforgeeks/OptimalPageReplacementAlgorithminOS/OptimalPageReplacement20220627124822-small.png"))
//        lImage.add(TimelineViewModel("https://videocdn.geeksforgeeks.org/geeksforgeeks/JavaProgramtoFindQuotientRemainder/JavaProgramtoFindQuotientandRemainder20220626125601-small.png"))
//        lImage.add(TimelineViewModel("https://videocdn.geeksforgeeks.org/geeksforgeeks/FirstandFollowinCompilerDesign/FirstFollowinCompilerDesign20220624172015-small.png"))
//        lImage.add(TimelineViewModel("https://media.geeksforgeeks.org/wp-content/uploads/geeksforgeeks-25.png"))
//        lImage.add(TimelineViewModel("https://media.geeksforgeeks.org/wp-content/uploads/20220531202857/photo6102488593462309569.jpg"))
//        lImage.add(TimelineViewModel("https://practice.geeksforgeeks.org/_next/image?url=https%3A%2F%2Fmedia.geeksforgeeks.org%2Fimg-practice%2Fbanner%2Fdsa-self-paced-thumbnail.png%3Fv%3D19171&w=1920&q=75"))
//        lImage.add(TimelineViewModel("https://practice.geeksforgeeks.org/_next/image?url=https%3A%2F%2Fmedia.geeksforgeeks.org%2Fimg-practice%2Fbanner%2Fcompetitive-programming-live-thumbnail.png%3Fv%3D19171&w=1920&q=75"))

        // on below line we are notifying adapter
        // that data has been updated.
        timelineAdapter.notifyDataSetChanged()

        APIService().base().getImageId(idalbum).enqueue(
            object : Callback<ResponseModel<List<Image>>> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(
                    call: Call<ResponseModel<List<Image>>>,
                    response: Response<ResponseModel<List<Image>>>
                ) {
                    val lImage: MutableList<Image> = ArrayList()
//                    val lImage: MutableList<AlbumBaby> = ArrayList()
                    val res = response.body() as ResponseModel<List<Image>>
//                    print(res.data)
                    lImage.addAll(res.data)

                    rvTimelineViewImage.adapter =
                        TimelineAdapter(this@Timeline, lImage)
                    lImage.forEachIndexed { index, timeline ->
                        if (timeline.idalbum == idAlbum)
                            index1 = index
                    }
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    var timeline  = lImage[index1].timeline
                    val dt = LocalDate.parse(timeline, formatter)
                    tvTimeLineCountYear.text = (dt.until(LocalDate.now()).years).toString()
                    tvTimeLineCountMonth.text = (dt.until(LocalDate.now()).months).toString()
                    tvTimeLineCountDay.text = (dt.until(LocalDate.now()).days).toString()
                    val format = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
                    tvTimeLineItemDateStart.text = (dt.format(format)).toString()
                    tvTimeLineItemDateEnd.text = (LocalDate.now().format(format)).toString()
                    tvTimelineItemTitle.text = nameAlbum.toString()
                    Picasso.get()
                        .load(Uri.parse(urlimage.toString()))
                        .into(civTimeLineAvatarCirCle)
//                    val manager = GridLayoutManager(this@Home, 2, GridLayoutManager.VERTICAL, false)
//                    recycleBaby.layoutManager = manager
                }

                override fun onFailure(call: Call<ResponseModel<List<Image>>>, t: Throwable) {
                    Toast.makeText(this@Timeline, "Get album failed", Toast.LENGTH_SHORT).show()
                }

            }
        )
        ibTimeLineBack.setOnClickListener {
            finish()
        }
//        println(dt.format(format))
//        println(DateFormat.getDateInstance(DateFormat.LONG).format(dt))

        

    }
}