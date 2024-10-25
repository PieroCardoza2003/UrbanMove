package com.capstone.urbanmove.utils

class VerifyEmail {
    companion object {
        private val EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        fun isValid(email: String): Boolean {
            return EMAIL_REGEX.matches(email)
        }
    }
}