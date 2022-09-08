package ai.ftech.babyphoto.screen.detailaccount

import ai.ftech.babyphoto.data.model.AccountUpdate

interface IDetailAccountContract {
    interface View {
        fun onUpdateAccount(state: DetailAccountState, message: String)
    }
    interface IPresenter {
        fun updateAccount(account: AccountUpdate)
    }
}