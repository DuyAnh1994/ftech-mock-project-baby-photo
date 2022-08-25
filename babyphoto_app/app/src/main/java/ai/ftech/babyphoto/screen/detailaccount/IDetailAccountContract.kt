package ai.ftech.babyphoto.screen.detailaccount

import ai.ftech.babyphoto.model.Account

interface IDetailAccountContract {
    interface View {
        fun onUpdateAccount(state: DetailAccountState, message: String)
    }
}