package id.co.mka.teraskill.data.responses

import com.google.gson.annotations.SerializedName

data class StatusApplyResponse(

    @field:SerializedName("sertifikat_portofolio_mentor")
    val sertifikatPortofolioMentor: SertifikatPortofolioMentor,

    @field:SerializedName("keterangan")
    val keterangan: String,

    @field:SerializedName("no_rekening")
    val noRekening: String,

    @field:SerializedName("uuid")
    val uuid: String,

    @field:SerializedName("userId")
    val userId: Int,

    @field:SerializedName("status_pendaftaran")
    val statusPendaftaran: String,

    @field:SerializedName("keahlian")
    val keahlian: String,

    @field:SerializedName("alamat")
    val alamat: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("bank")
    val bank: String,

    @field:SerializedName("nama_pemilik_rekening")
    val namaPemilikRekening: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("job")
    val job: String,

    @field:SerializedName("user")
    val user: User,

    @field:SerializedName("tanggal_lahir")
    val tanggalLahir: String,

    @field:SerializedName("instansi")
    val instansi: String,

    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("updatedAt")
    val updatedAt: String
) {
    data class User(

        @field:SerializedName("name")
        val name: String,

        @field:SerializedName("uuid")
        val uuid: String,

        @field:SerializedName("email")
        val email: String
    )


    data class SertifikatPortofolioMentor(

        @field:SerializedName("createdAt")
        val createdAt: String,

        @field:SerializedName("sertifikat")
        val sertifikat: String,

        @field:SerializedName("id")
        val id: Int,

        @field:SerializedName("uuid")
        val uuid: String,

        @field:SerializedName("pendaftaranMentorId")
        val pendaftaranMentorId: Int,

        @field:SerializedName("updatedAt")
        val updatedAt: String
    )
}


