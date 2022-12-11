package id.co.mka.teraskill.data.responses

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @field:SerializedName("msg")
    val msg: String
)
