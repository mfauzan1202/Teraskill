package id.co.mka.teraskill.data.responses

import com.google.gson.annotations.SerializedName

data class JobTitleResponse(

    @field:SerializedName("job")
    val job: String
)
