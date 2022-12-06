package id.co.mka.teraskill.utils

sealed class Resource <T>(
    val data: T? = null,
    val message: String? = null,
    val statusCode: Int = 0
){
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(data: T?, message: String,statusCode: Int) : Resource<T>(data, message, statusCode)
    class Loading<T> : Resource<T>()
}