import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aisc.ngalo.models.Category
import java.io.Serializable

@Entity(tableName = "mylobikes")
data class BikePart(
    @PrimaryKey
    val id: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val price: String = "",
    val description: String = "",
    val category: String = ""
) : Serializable {
    constructor() : this(
        id = "",
        imageUrl = "",
        name = "",
        price = "",
        description = "",
        category = ""
    )
}
