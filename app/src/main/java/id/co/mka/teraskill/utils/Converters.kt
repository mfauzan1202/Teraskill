package id.co.mka.teraskill.utils

import androidx.room.TypeConverter
import id.co.mka.teraskill.data.responses.JobTitleResponse
import id.co.mka.teraskill.data.responses.LearningPathResponse
import id.co.mka.teraskill.data.responses.MentorInfoResponse
import org.json.JSONObject


class MentorInfoConverter {
    @TypeConverter
    fun fromSource(source: MentorInfoResponse): String {
        return JSONObject().apply {
            put("id", source.uuid)
            put("name", source.name)
            put("job_title", source.mentorJobTitle.job)
        }.toString()
    }

    @TypeConverter
    fun toSource(source: String): MentorInfoResponse {
        val json = JSONObject(source)
        return MentorInfoResponse(json.get("name").toString(), JobTitleResponse(json.getString("name")), json.getString("id").toString())
    }
}

class LearningPathConverter {
    @TypeConverter
    fun fromSource(source: LearningPathResponse): String {
        return JSONObject().apply {
            put("id", source.uuid)
            put("name", source.name)
        }.toString()
    }

    @TypeConverter
    fun toSource(source: String): LearningPathResponse {
        val json = JSONObject(source)
        return LearningPathResponse(name = json.get("name").toString(), uuid = json.getString("id").toString())
    }
}