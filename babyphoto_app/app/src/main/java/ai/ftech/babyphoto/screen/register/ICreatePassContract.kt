package ai.ftech.babyphoto.screen.register

interface ICreatePassContract {
    interface View{
        fun onInsertAccount(
            state: RegisterState,
            message: String,
            email: String,
        )
        fun onCheckPass(state: RegisterState, message: String)
    }
}