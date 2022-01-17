import android.os.Parcelable
import com.utechia.data.entity.Error
import com.utechia.data.entity.NotificationCountData
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class NotificationCount(

    val data :@Contextual @RawValue NotificationCountData,
    val error:@Contextual @RawValue Error?

): Parcelable