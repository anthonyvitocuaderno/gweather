package com.vitocuaderno.gweather.core.exceptions

sealed class Failure {
    object NetworkConnection : Failure()

    object ServerError : Failure()

    object InvalidEmail : Failure()

    object InvalidPassword : Failure()

    object EmailAlreadyExists : Failure()

    object PasswordsDoNotMatch : Failure()

    /** * Extend this class for feature specific failures.* */
    abstract class FeatureFailure : Failure()
}
