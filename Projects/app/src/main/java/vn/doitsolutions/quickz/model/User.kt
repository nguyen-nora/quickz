package vn.doitsolutions.quickz.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json


data class User(
    @Json(name = "username") var username: String?,
    @Json(name = "password") var password: String?,
    @Json(name = "fullname") var fullname: String?,
    @Json(name = "_id") var _id: String?,
    @Json(name = "_rowPosition") var _rowPosition: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeString(username)
        p0.writeString(password)
        p0.writeString(fullname)
        p0.writeString(_id)
        p0.writeInt(_rowPosition)
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

