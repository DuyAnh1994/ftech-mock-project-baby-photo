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

    fun isEmail(email: String): Boolean {
        return Pattern.matches(REGEX_EMAIL, email)
    }

    fun isValidName(name: String?, name2: String?): Boolean {
        return Pattern.matches("[a-zA-Z]+", name) && Pattern.matches("[a-zA-Z]+", name2)
    }
    fun checkNull(firstName: String?, lastName: String?): Boolean {
        return firstName == "" || lastName == ""
    }

    fun isValidPassCharacter(pass: String): Boolean {
        return Pattern.matches("[a-zA-Z]+", pass)
    }

    fun isValidPassCount(pass: String): Boolean {
        return pass.length in (6..8)
    }

    fun isMatchPass(pass: String, rePass: String): Boolean {
        return Pattern.matches(pass, rePass)
    }
    fun isMatchEmail(email: String, email1: String): Boolean{
        return Pattern.matches(email, email1)
    }
}