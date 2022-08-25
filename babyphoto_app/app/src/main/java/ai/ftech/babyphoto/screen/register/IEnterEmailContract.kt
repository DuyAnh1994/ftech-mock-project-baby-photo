package ai.ftech.babyphoto.screen.register

interface IEnterEmailContract {
    interface View{
        fun onCheckMail(state: RegisterState, message: String)
        fun onNextScreen(state: RegisterState, message: String, account: String)
    }
}