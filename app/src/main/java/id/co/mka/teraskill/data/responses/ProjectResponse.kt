package id.co.mka.teraskill.data.responses

import com.google.gson.annotations.SerializedName

data class ProjectResponse(

	@field:SerializedName("soal")
	val soal: String,

	@field:SerializedName("kelas_teraskill")
	val kelasTeraskill: KelasTeraskill,

	@field:SerializedName("uuid")
	val uuid: String,

	@field:SerializedName("user_aswer_projek")
	val userAswerProjek: UserAswerProjek? = null
){

	data class UserAswerProjek(

		@field:SerializedName("link")
		val link: String,

		@field:SerializedName("uuid")
		val uuid: String,

		@field:SerializedName("user")
		val user: User
	){
		data class User(

			@field:SerializedName("name")
			val name: String,

			@field:SerializedName("uuid")
			val uuid: String
		)
	}

	data class KelasTeraskill(

		@field:SerializedName("name")
		val name: String,

		@field:SerializedName("uuid")
		val uuid: String
	)
}