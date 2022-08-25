package ai.ftech.babyphoto.screen.login

interface ILoginContract {
    interface View {
        fun onLogin(state: LoginState, message: String, idaccount: Int)
        fun onValidAccount(state: LoginState, message: String, showWarring: Boolean)
        fun onCheckMailNull(state: LoginState, message: String)
    }
}