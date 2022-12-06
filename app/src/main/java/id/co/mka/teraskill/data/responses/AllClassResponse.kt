package id.co.mka.teraskill.data.responses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class PaginationClassResponse(

	@field:SerializedName("result")
	val result: List<AllClassResponse>,

	@field:SerializedName("totalPage")
	val totalPage: Int,

	@field:SerializedName("limit")
	val limit: Int,

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("totalRows")
	val totalRows: Int
)

@Entity(tableName = "class")
data class AllClassResponse(

	@PrimaryKey
	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("uuid")
	val uuid: String,

	@field:SerializedName("learningPathId")
	val learningPathId: Int,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("jml__materi_video")
	val jmlMateriVideo: Int,

	@field:SerializedName("is_published")
	val isPublished: Boolean,

	@field:SerializedName("about")
	val about: String,

	@field:SerializedName("image_bg")
	val imageBg: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("jml_Materi_text")
	val jmlMateriText: Int,

	@field:SerializedName("tools")
	val tools: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("link_grub")
	val linkGrub: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("jml_materi")
	val jmlMateri: Int,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
