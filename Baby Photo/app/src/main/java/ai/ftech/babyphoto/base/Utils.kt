package ai.ftech.babyphoto.base

import java.util.regex.Pattern

class Utils {
    private val REGEX_EMAIL = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    private val REGEX_NAME = "[a-zA-Z]+"
    fun isValidName(name: String): Boolean {
        return Pattern.matches(REGEX_NAME, name)
    }
    fun isEmail(email: String): Boolean{
        return Pattern.matches(REGEX_EMAIL, email)
    }
}