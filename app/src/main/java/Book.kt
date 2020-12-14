import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book (
    val title: String,
    val isbn: String,
    val firstAuthor: String,
    val publishingCompany: String,
    val edition: Int,
    val pages: Int
): Parcelable