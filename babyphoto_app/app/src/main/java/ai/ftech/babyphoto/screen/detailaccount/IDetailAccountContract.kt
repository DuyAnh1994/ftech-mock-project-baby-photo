package ai.ftech.babyphoto.screen.detailaccount

interface IDetailAccountContract {
    interface View {
        fun onUpdateAccount(state: DetailAccountState, message: String)
    }
}