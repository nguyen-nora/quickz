package vn.doitsolutions.quickz.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.squareup.moshi.Json

data class ExamQuestion(
    @Json(name = "question") var question: Question?,
    @Json(name = "userrep") var userrep: String?,
    @Json(name = "correct") var correct: Boolean?,
    @Json(name = "_id") var _id: String?,
): Parcelable{
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readTypedObject(Question.CREATOR)!!,
        parcel.readString(),
        parcel.readBoolean(),
        parcel.readString()!!
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeTypedObject(question, p1)
        p0.writeString(userrep)
        p0.writeBoolean(correct!!)
        p0.writeString(_id)
    }

    companion object CREATOR : Parcelable.Creator<ExamQuestion> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): ExamQuestion {
            return ExamQuestion(parcel)
        }

        override fun newArray(size: Int): Array<ExamQuestion?> {
            return arrayOfNulls(size)
        }
    }


}

