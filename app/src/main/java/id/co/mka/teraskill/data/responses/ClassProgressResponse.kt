package id.co.mka.teraskill.data.responses

import com.google.gson.annotations.SerializedName

data class ClassProgressResponse(

    @field:SerializedName("listKelas")
    val listKelas: List<ListKelasItem>
) {
    data class ListKelasItem(

        @field:SerializedName("progress")
        val progress: Int,

        @field:SerializedName("prosentase")
        val prosentase: Int,

        @field:SerializedName("uuid")
        val uuid: String,

        @field:SerializedName("total_materi")
        val totalMateri: Int,

        @field:SerializedName("kelasName")
        val kelasName: String
    )

}