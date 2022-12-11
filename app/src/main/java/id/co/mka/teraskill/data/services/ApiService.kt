package id.co.mka.teraskill.data.services

import id.co.mka.teraskill.data.dataclass.UserInfo
import id.co.mka.teraskill.data.responses.*
import id.co.mka.teraskill.data.responses.SingleClassResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("user")
    fun registerUser(@Body response: RequestBody): Call<MessageResponse>

    @Headers("Content-Type: application/json")
    @POST("login")
    fun loginUser(@Body response: UserInfo): Call<AuthResponse>

    @POST("user/forget-pw/send/")
    fun sendEmailForgotPass(
        @Body response: HashMap<String, String>
    ): Call<MessageResponse>

    @POST("user/forget-pw/resend")
    fun resendEmailForgotPass(
        @Body response: HashMap<String, String>
    ): Call<MessageResponse>

    @POST("user/forget-pw/verify/")
    fun sendOTPCodeForgotPass(
        @Body response: HashMap<String, Any>
    ): Call<MessageResponse>

    @POST("user/forget-pw")
    fun changePassword(
        @Body response: HashMap<String, String>
    ): Call<MessageResponse>

    @Headers("Content-Type: application/json")
    @PATCH("user/{uuid}")
    fun updateUser(
        @Path("uuid") uuid: String,
        @Body response: UserInfo
    ): Call<MessageResponse>

    @PATCH("user/avatar/{uuid}")
    fun updateUserAvatar(
        @Path("uuid") uuid: String,
        @Body response: RequestBody
    ): Call<AuthResponse.UpdateAvatarResponse>

    @POST("gabung-mentor")
    fun uploadFile(
        @Body body: RequestBody
    ): Call<MessageResponse>

    @GET("kelas-user/progress")
    fun getProgress(): Call<ClassProgressResponse>

    @GET("kelas-user/transaksi")
    fun getHistoryTransaction(): Call<TransactionHistoryResponse>

    @GET("kelas-user/transaksi")
    fun getHistoryTransactionByDate(
        @Query("start") start: String,
        @Query("end") end: String
    ): Call<TransactionHistoryResponse>

    @GET("kelas-user")
    fun getAllUserClass(): Call<List<MyClassResponse>>

    @GET("kelas/pagination")
    suspend fun getAllClass(
        @Query("limit") page: Int,
        @Query("page") size: Int
    ): PaginationClassResponse

    //TODO: jangan lupa dihapus
    @GET("learning-path")
    suspend fun getAllLearningPath(): List<LearningPathResponse>

    @GET("learning-path")
    fun getLearningPath(): Call<List<LearningPathResponse>>

    @GET("kelas/{uuid}")
    fun getClassInfoByID(
        @Path("uuid") uuid: String
    ): Call<SingleClassResponse>

    @GET("kelas/learning-path/{uuid}")
    suspend fun getClassByLearningPath(
        @Path("uuid") uuid: String,
        @Query("limit") page: Int,
        @Query("page") size: Int
    ): PaginationClassResponse

    @GET("materi/modul/{uuid}")
    fun getModulByID(
        @Path("uuid") uuid: String
    ): Call<List<ModulResponse>>

    @GET("materi/{uuid}")
    fun updateStatusChapter(
        @Path("uuid") uuid: String
    ): Call<UpdateProgressResponse>

    @POST("user/email/verify")
    fun verifyEmail(
        @Body response: HashMap<String, Any>
    ): Call<MessageResponse>

    @POST("user/resend-verify-email")
    fun resendVerifyEmail(
        @Body response: HashMap<String, Any>
    ): Call<MessageResponse>

    @POST("kelas-user")
    fun buyClass(
        @Body response: HashMap<String, Int>
    ): Call<SingleClassResponse>

    @PATCH("kelas-user/pay/{uuid}")
    fun uploadReceiptBuyClass(
        @Path("uuid") uuid: String,
        @Body response: RequestBody
    ): Call<SingleClassResponse>

    @GET("soal-exam/kelas/{uuid}")
    fun getExamByClassID(
        @Path("uuid") uuid: String
    ): Call<List<ExamResponse>>

    @POST("exam/{uuid}")
    fun submitExam(
        @Path("uuid") uuid: String,
        @Body response: HashMap<String, Int>
    ): Call<ResultExamResponse>

    @GET("soal/projek/kelas/{uuid}")
    fun getProjectClass(
        @Path("uuid") uuid: String
    ): Call<List<ProjectResponse>>

    @POST("projek/answer/{uuid}")
    fun submitProject(
        @Path("uuid") uuid: String,
        @Body response: HashMap<String, String>
    ): Call<MessageResponse>

    @PATCH("/kelas/review/{uuid}")
    fun submitReview(
        @Path("uuid") uuid: String,
        @Body response: HashMap<String, String>
    ): Call<MessageResponse>

    @GET("portofolio/me")
    fun getPortfolio(): Call<List<PortfolioResponse>>

    @POST("portofolio")
    fun addPortfolio(
        @Body response: RequestBody
    ): Call<MessageResponse>

    @GET("kelas-user/sertifikat/cek/kelas/{uuid}")
    fun getLinkPdfFile(
        @Path("uuid") uuid: String
    ): Call<CertificateResponse>

    @GET
    fun downloadPdfFile(@Url pdfUrl: String): Call<ResponseBody>
}