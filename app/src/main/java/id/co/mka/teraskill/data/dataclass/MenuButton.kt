package id.co.mka.teraskill.data.dataclass

import com.google.gson.annotations.SerializedName

data class MenuButton(
    @field:SerializedName("title")
    var title: String,

    @field:SerializedName("drawable")
    var drawable: Int,
)