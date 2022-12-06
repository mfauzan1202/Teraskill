package id.co.mka.teraskill.data.responses

import com.google.gson.annotations.SerializedName

data class MyClassResponse(

	@field:SerializedName("kelas_teraskill")
	val kelasTeraskill: KelasTeraskill,

	@field:SerializedName("star")
	val star: Any,

	@field:SerializedName("progres")
	val progres: Int,

	@field:SerializedName("is_review_posted")
	val isReviewPosted: Boolean,

	@field:SerializedName("no_ref")
	val noRef: Any,

	@field:SerializedName("tanggal_pembayaran")
	val tanggalPembayaran: Any,

	@field:SerializedName("uuid")
	val uuid: String,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("kelasTeraskillId")
	val kelasTeraskillId: Int,

	@field:SerializedName("metode_pembayaran")
	val metodePembayaran: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("nominal")
	val nominal: Int,

	@field:SerializedName("review")
	val review: Any,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("is_paid")
	val isPaid: Boolean,

	@field:SerializedName("user")
	val mentorInfo: MentorInfoResponse,

	@field:SerializedName("bukti_pembayaran")
	val buktiPembayaran: Any,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
