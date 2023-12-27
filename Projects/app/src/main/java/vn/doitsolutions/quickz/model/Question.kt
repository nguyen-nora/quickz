package vn.doitsolutions.quickz.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Question(
    @Json(name = "question")
    val question: String?,
    @Json( name = "options")
    val answers: List<String>?,
    @Json(name = "correct")
    val correctAnswer: String?,
    @Json(name = "_id")
    var _id: String?

): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question)
        parcel.writeStringList(answers)
        parcel.writeString(correctAnswer)
        parcel.writeString(_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }

}