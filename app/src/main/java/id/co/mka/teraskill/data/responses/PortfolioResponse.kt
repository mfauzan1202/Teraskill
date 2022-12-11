package id.co.mka.teraskill.data.responses

import com.google.gson.annotations.SerializedName

data class PortfolioResponse(

	@field:SerializedName("deskripsi_diri")
	val deskripsiDiri: String,

	@field:SerializedName("deskripsi_projek")
	val deskripsiProjek: Any,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("is_published")
	val isPublished: Any,

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("posisi")
	val posisi: String,

	@field:SerializedName("project_name")
	val projectName: String,

	@field:SerializedName("uuid")
	val uuid: String,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
