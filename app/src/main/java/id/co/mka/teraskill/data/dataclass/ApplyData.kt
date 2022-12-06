package id.co.mka.teraskill.data.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApplyData(

    @field:SerializedName("accountName")
    var accountName: String,

    @field:SerializedName("birthDate")
    var birthData: String,

    @field:SerializedName("address")
    var address: String,

    @field:SerializedName("job")
    var job: String,

    @field:SerializedName("agency")
    var agency: String,

    @field:SerializedName("bankName")
    var bankName: String,

    @field:SerializedName("accountNumber")
    var accountNumber: String,
): Parcelable
