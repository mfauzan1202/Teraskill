package id.co.mka.teraskill

import com.google.gson.annotations.SerializedName

data class UserInfo(

	@field:SerializedName("name")
	var name: String,

	@field:SerializedName("no_hp")
	var no_hp: String,

	@field:SerializedName("email")
	var email: String,

	@field:SerializedName("password")
	var password: String,

	@field:SerializedName("confpassword")
	var confpassword: String,
)
