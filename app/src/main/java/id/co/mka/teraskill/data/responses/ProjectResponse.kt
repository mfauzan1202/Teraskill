package id.co.mka.teraskill.data.responses

import com.google.gson.annotations.SerializedName

data class ProjectResponse(

    @field:SerializedName("soal")
    val soal: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("uuid")
    val uuid: String,

    @field:SerializedName("user_answer")
    val userAnswer: UserAnswerItem? = null,
) {
    data class UserAnswerItem(

        @field:SerializedName("link")
        val link: String? = null,

        @field:SerializedName("uuid")
        val uuid: String
    )
}
//
//sealed class UserAnswer<T>(val value: T) {
//    class DataValue(value: List<ProjectResponse.UserAnswerItem>) :
//        UserAnswer<List<ProjectResponse.UserAnswerItem>>(value)
//
//    class StringValue(value: String) : UserAnswer<String>(value)
//}
