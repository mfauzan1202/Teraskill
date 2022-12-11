package id.co.mka.teraskill.data.responses

import com.google.gson.annotations.SerializedName

data class WebinarResponse(
	@field:SerializedName("learningPathId")
	val learningPathId: Int,

	@field:SerializedName("image_poster")
	val imagePoster: String,

	@field:SerializedName("is_published")
	val isPublished: Int,

	@field:SerializedName("start")
	val start: String,

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("media")
	val media: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("uuid")
	val uuid: String,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("saran")
	val saran: Any,

	@field:SerializedName("penyelenggara_name")
	val penyelenggaraName: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("image_banner")
	val imageBanner: String,

	@field:SerializedName("end")
	val end: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("tanggal")
	val tanggal: String,

	@field:SerializedName("tipe")
	val tipe: String,

	@field:SerializedName("learning_path")
	val learningPath: LearningPath,

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("day")
	val day: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
){
	data class LearningPath(

		@field:SerializedName("name")
		val name: String,

		@field:SerializedName("uuid")
		val uuid: String
	)

	data class User(

		@field:SerializedName("name")
		val name: String,

		@field:SerializedName("uuid")
		val uuid: String,

		@field:SerializedName("email")
		val email: String
	)

}

