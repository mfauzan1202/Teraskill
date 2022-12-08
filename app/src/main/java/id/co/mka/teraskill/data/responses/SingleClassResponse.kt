package id.co.mka.teraskill.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SingleClassResponse(
    val response: Response,
    val status: Status
) {
    data class Response(
        val learningPathId: Int,
        val image: String,
        val jmlMateriVideo: Int,
        val isPublished: Boolean,
        val about: String,
        val imageBg: String,
        val type: String,
        val jmlMateriText: Int,
        val uuid: String,
        val userId: Int,
        val tools: String,
        val createdAt: String,
        val moduls: List<ModulsItem>,
        val linkGrub: String,
        val isPublishedDate: String,
        val price: Int,
        val name: String,
        val jmlMateri: Int,
        val id: Int,
        @field:SerializedName("user")
        val mentorInfo: MentorInfo,
        @field:SerializedName("learning_path")
        val learningPath: LearningPath,
        val status: String,
        val updatedAt: String
    ) {
        data class LearningPath(
            val name: String,
            val uuid: String
        )

        data class MentorInfo(
            val name: String,
            val avatar: String,

            @field:SerializedName("pendaftaran_mentor")
            val mentorJob: MentorJobInfo,
            val uuid: String
        ) {
            data class MentorJobInfo(
                val job: String
            )
        }

        data class ModulsItem(
            val name: String,
            val uuid: String,
            val urutan: Int
        )
    }

    data class Status (
        val pembelian: String,
        val status_beli: String,
        val materi: String,
        val exam: String,
        val exam_lulus: String,
        val review: String
    )
}


