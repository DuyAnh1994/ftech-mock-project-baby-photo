package ai.ftech.babyphoto.screen.timeline

import ai.ftech.babyphoto.model.Image

interface ITimelineContract {
    interface View{
        fun onGetImage(state: TimelineState, message: String, lImage: MutableList<Image>)
    }
}