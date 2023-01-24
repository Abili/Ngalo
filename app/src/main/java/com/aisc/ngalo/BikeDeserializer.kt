import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.PropertyName
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

class BikeDeserializer : JsonDeserializer<Bike> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Bike {
        val gson = Gson()
        val jsonObject = json?.asJsonObject
        return gson.fromJson(jsonObject, Bike::class.java)
    }
}

data class Bike(
    @SerializedName("id")
    @PropertyName("id")
    val id: String = "",
    @SerializedName("imageUrl")
    @PropertyName("imageUrl")
    val imageUrl: String = "",
    @SerializedName("name")
    @PropertyName("name")
    val name: String = "",
    @SerializedName("price")
    @PropertyName("price")
    val price: String = ""
)

fun parseBike(dataSnapshot: DataSnapshot): Bike {
    return dataSnapshot.getValue(Bike::class.java)!!
}
