package id.co.mka.teraskill.data.responses

import com.google.gson.annotations.SerializedName

data class ResultExamResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("score")
	val score: Int,

	@field:SerializedName("status")
	val status: String
)
