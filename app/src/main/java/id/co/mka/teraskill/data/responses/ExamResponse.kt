package id.co.mka.teraskill.data.responses

import com.google.gson.annotations.SerializedName

data class ExamResponse(

	@field:SerializedName("soal")
	val soal: String? = null,

	@field:SerializedName("kelas_teraskill")
	val kelasTeraskill: KelasTeraskill? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("option_exams")
	val optionExams: List<OptionExamsItem>,

	@field:SerializedName("uuid")
	val uuid: String? = null
){
	data class OptionExamsItem(

		@field:SerializedName("createdAt")
		val createdAt: String? = null,

		@field:SerializedName("soalExamId")
		val soalExamId: Int? = null,

		@field:SerializedName("correct_answer")
		val correctAnswer: Boolean? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("uuid")
		val uuid: String? = null,

		@field:SerializedName("urutan")
		val urutan: Int? = null,

		@field:SerializedName("content")
		val content: String? = null,

		@field:SerializedName("updatedAt")
		val updatedAt: String? = null
	)

	data class KelasTeraskill(

		@field:SerializedName("name")
		val name: String? = null
	)
}

