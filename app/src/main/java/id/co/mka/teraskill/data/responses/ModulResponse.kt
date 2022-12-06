package id.co.mka.teraskill.data.responses

import android.content.pm.ModuleInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ModulResponse(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("link")
    val link: String?,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("tipe")
    val tipe: String,

    @field:SerializedName("uuid")
    val uuid: String,

    @field:SerializedName("modulId")
    val modulId: Int,

    @field:SerializedName("urutan")
    val urutan: Int,

    @field:SerializedName("content")
    val content: String? ,

    @field:SerializedName("updatedAt")
    val updatedAt: String,

    @field:SerializedName("modul")
    val modul: ModuleInfoItem,

    @field:SerializedName("kelas_user_materis")
    val statusProgress: StatusProgressUser
) {
    data class ModuleInfoItem(
        @field:SerializedName("uuid")
        val uuid: String,

        @field:SerializedName("name")
        val name: String,

        @field:SerializedName("kelas_teraskill")
        val moduleInfo: SingleClassResponse.Response.ModulsItem? = null
    )

    data class StatusProgressUser(

        @field:SerializedName("status")
        val status: String
    )
}



