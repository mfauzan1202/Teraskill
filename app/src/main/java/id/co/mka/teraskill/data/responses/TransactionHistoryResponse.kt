package id.co.mka.teraskill.data.responses

import com.google.gson.annotations.SerializedName

data class TransactionHistoryResponse(

	@field:SerializedName("kelasUser")
	val kelasUser: List<KelasUserItem>
)

data class KelasTeraskill(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("uuid")
	val uuid: String? = null,

	@field:SerializedName("type")
	val type: String,
)

data class KelasUserItem(

	@field:SerializedName("metode_pembayaran")
	val metodePembayaran: String? = null,

	@field:SerializedName("kelas_teraskill")
	val kelasTeraskill: KelasTeraskill? = null,

	@field:SerializedName("nominal")
	val nominal: Int? = null,

	@field:SerializedName("is_paid")
	val isPaid: Boolean? = null,

	@field:SerializedName("tanggal_pembayaran")
	val tanggalPembayaran: String? = null,

	@field:SerializedName("bukti_pembayaran")
	val buktiPembayaran: Any? = null,

	@field:SerializedName("status")
	val status: String? = null
)
