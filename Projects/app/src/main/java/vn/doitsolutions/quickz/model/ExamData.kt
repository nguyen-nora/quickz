package vn.doitsolutions.quickz.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.ToJson


data class ExamData(
    @Json(name = "list")
    var list: ArrayList<ExamQuestion>,
    @Json(name = "user")
    var user: User?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readArrayList(Question::class.java.classLoader) as ArrayList<ExamQuestion>,
        parcel.readTypedObject(User.CREATOR)
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeArray(list.toArray())
        p0.writeTypedObject(user, p1)
    }

    companion object CREATOR : Parcelable.Creator<ExamData> {
        override fun createFromParcel(parcel: Parcel): ExamData {
            return ExamData(parcel)
        }

        override fun newArray(size: Int): Array<ExamData?> {
            return arrayOfNulls(size)
        }
    }


}


class ExamQuestionArrayListMoshiAdapter {
    @ToJson
    fun arrayListToJson(list: ArrayList<ExamQuestion>): List<ExamQuestion> = list

    @FromJson
    fun arrayListFromJson(list: List<ExamQuestion>): ArrayList<ExamQuestion> = ArrayList(list)
}