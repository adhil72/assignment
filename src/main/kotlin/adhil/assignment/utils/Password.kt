package adhil.assignment.utils

import adhil.assignment.exceptions.PasswordException

fun validatePassword(password: String): Boolean {
    if (password.length < 8) throw PasswordException("Password must be at least 8 characters long")
    if (!password.any { it.isUpperCase() }) throw PasswordException("Password must contain at least one uppercase letter")
    if (!password.any { it.isLowerCase() }) throw PasswordException("Password must contain at least one lowercase letter")
    if (!password.any { it.isDigit() }) throw PasswordException("Password must contain at least one digit")
    return true
}