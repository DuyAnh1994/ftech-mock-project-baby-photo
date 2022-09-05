package ai.ftech.babyphoto.screen.register

import ai.ftech.babyphoto.data.model.IBaseView

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