package id.co.mka.teraskill

import com.google.gson.annotations.SerializedName

data class ApiResponse(

    @field:SerializedName("message")
    var msg: String,
)
