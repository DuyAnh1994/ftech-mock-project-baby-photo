package ai.ftech.babyphoto.screen.register

import ai.ftech.babyphoto.RegisterActivity
import ai.ftech.babyphoto.base.Utils

class RegisterPresenter(activity: RegisterActivity) {
    private val view = activity
    fun checkName(name: String): Boolean{
        val isName = Utils().isValidName(name)
        return isName
    }
}