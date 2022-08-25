package ai.ftech.babyphoto.screen.login

interface ILoginContract {
    interface View {
        fun onLogin(state: LoginState, message: String)
        fun onValidAccount(state: LoginState, message: String)
        fun onCheckMailNull(state: LoginState, message: String)
    }
}