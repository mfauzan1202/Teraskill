package id.co.mka.teraskill.data.responses

import com.google.gson.annotations.SerializedName

data class ClassResponse(
	@field:SerializedName("ClassResponse")
	val classResponse: List<ClassResponseItem>
)

data class LearningPath(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("uuid")
	val uuid: String
)

data class User(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("pendaftaran_mentor")
	val pendaftaranMentor: PendaftaranMentor,

	@field:SerializedName("uuid")
	val uuid: String
)

data class PendaftaranMentor(

	@field:SerializedName("job")
	val job: String
)

data class ClassResponseItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("jml__materi_video")
	val jmlMateriVideo: Int,

	@field:SerializedName("is_published")
	val isPublished: Boolean,

	@field:SerializedName("about")
	val about: String,

	@field:SerializedName("image_bg")
	val imageBg: String? = null,

	@field:SerializedName("jml_Materi_text")
	val jmlMateriText: Int,

	@field:SerializedName("uuid")
	val uuid: String,

	@field:SerializedName("link_grub")
	val linkGrub: Any? = null,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("learning_path")
	val learningPath: LearningPath
)
