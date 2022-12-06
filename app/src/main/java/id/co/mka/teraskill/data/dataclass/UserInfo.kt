package id.co.mka.teraskill.data.dataclass

import com.google.gson.annotations.SerializedName

data class UserInfo(

	@field:SerializedName("name")
	var name: String? = null,

	@field:SerializedName("no_hp")
	var no_hp: String? = null,

	@field:SerializedName("email")
	var email: String,

	@field:SerializedName("password")
	var password: String,

	@field:SerializedName("confpassword")
	var confpassword: String? = null,
)
