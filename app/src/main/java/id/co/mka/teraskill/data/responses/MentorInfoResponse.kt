package id.co.mka.teraskill.data.responses

import com.google.gson.annotations.SerializedName

data class MentorInfoResponse(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("pendaftaran_mentor")
    val mentorJobTitle: JobTitleResponse,

    @field:SerializedName("uuid")
    val uuid: String
)
