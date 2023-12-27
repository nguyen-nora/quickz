package vn.doitsolutions.quickz.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Suppress("UNREACHABLE_CODE")
data class Questions(
    @SerializedName("questions")
    var data: ArrayList<Question>,
    @SerializedName("duration")
    val duration: Int,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readArrayList(Question::class.java.classLoader) as ArrayList<Question>,
        parcel.readInt()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeArray(data.toArray())
        p0.writeInt(duration)
    }

    companion object CREATOR : Parcelable.Creator<Questions> {
        override fun createFromParcel(parcel: Parcel): Questions {
            return Questions(parcel)
        }

        override fun newArray(size: Int): Array<Questions?> {
            return arrayOfNulls(size)
        }
    }

    private fun Questions(`in`: Parcel) {
        data = `in`.readArray(Question::class.java.classLoader) as ArrayList<Question>
    }

}
