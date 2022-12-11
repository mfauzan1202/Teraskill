package id.co.mka.teraskill.data.responses

import com.google.gson.annotations.SerializedName

data class CertificateResponse(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("sertifikat")
	val sertifikat: String,

	@field:SerializedName("status")
	val status: String
)
