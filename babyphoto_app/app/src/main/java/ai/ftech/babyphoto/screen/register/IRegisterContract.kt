package ai.ftech.babyphoto.screen.register


interface IRegisterContract {
    interface View{
        fun onCheckName(state: RegisterState, message: String)
        fun onNextScreen(state: RegisterState, message: String, account: String)
    }
}