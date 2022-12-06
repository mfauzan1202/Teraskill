package id.co.mka.teraskill.data.responses

import com.google.gson.annotations.SerializedName

data class UpdateProgressResponse(

	@field:SerializedName("response")
	val response: Response
){
	data class Response(

		@field:SerializedName("createdAt")
		val createdAt: String,

		@field:SerializedName("kelas_user_materis")
		val kelasUserMateris: KelasUserMateris,

		@field:SerializedName("link")
		val link: String,

		@field:SerializedName("modul")
		val modul: Modul,

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
		val content: Any,

		@field:SerializedName("updatedAt")
		val updatedAt: String
	){
		data class Modul(

			@field:SerializedName("kelas_teraskill")
			val kelasTeraskill: KelasTeraskill,

			@field:SerializedName("name")
			val name: String,

			@field:SerializedName("uuid")
			val uuid: String
		){
			data class KelasTeraskill(

				@field:SerializedName("name")
				val name: String,

				@field:SerializedName("id")
				val id: Int,

				@field:SerializedName("uuid")
				val uuid: String
			)
		}

		data class KelasUserMateris(

			@field:SerializedName("status")
			val status: String
		)

	}
}



