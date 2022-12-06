package id.co.mka.teraskill.data.responses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "learning_path")
data class LearningPathResponse(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String,

	@PrimaryKey
	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("uuid")
	val uuid: String,

	@field:SerializedName("desc")
	val desc: String? = null
)
