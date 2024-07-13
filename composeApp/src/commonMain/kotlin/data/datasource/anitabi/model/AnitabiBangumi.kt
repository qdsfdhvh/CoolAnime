package data.datasource.anitabi.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.double
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long

@Serializable(with = AnitabiBangumiSerializer::class)
data class AnitabiBangumi(
  val id: Int,
  val cnTitle: String,
  val enTitle: String,
  val jpTitle: String,
  val city: String,
  val color: String,
  val cover: String,
  val rate: Float,
  val tag: String,
  val latitude: Double,
  val longitude: Double,
  val zoom: Double,
  val modified: Long,
  val points: List<AnitabiPoint>,
  // val num1: Int,
  // val num2: Int,
  val images: AnitabiImages,
)

data class AnitabiPoint(
  val id: String,
  val shortDescription: String,
  val title: String,
  val latitude: Double,
  val longitude: Double,
  // val num1: Int,
  val mid: String,
  // val num3: Int,
  val image: String,
  // val num4: Int,
  val ep: String,
  val s: String,
  val description: String,
  // val num7: Int,
  // val num8: Int,
  val point: String,
)

data class AnitabiImages(
  val path: String,
  val ids: List<String>,
  val modified: Long,
)

@OptIn(ExperimentalSerializationApi::class)
object AnitabiBangumiSerializer : KSerializer<AnitabiBangumi> {

  private val delegateSerializer = ListSerializer(JsonElement.serializer())

  override val descriptor: SerialDescriptor
    get() = SerialDescriptor("AnitabiBangumi", delegateSerializer.descriptor)

  override fun deserialize(decoder: Decoder): AnitabiBangumi {
    val array = decoder.decodeSerializableValue(delegateSerializer)
    return AnitabiBangumi(
      id = array[0].jsonPrimitive.int,
      cnTitle = array[1].jsonPrimitive.content,
      enTitle = if (array[2].jsonPrimitive.isString) {
        array[2].jsonPrimitive.content
      } else {
        ""
      },
      jpTitle = array[3].jsonPrimitive.content,
      city = array[4].jsonPrimitive.content,
      color = array[5].jsonPrimitive.content,
      cover = array[6].jsonPrimitive.content,
      rate = array[7].jsonPrimitive.float,
      tag = array[8].jsonPrimitive.content,
      latitude = array[9].jsonPrimitive.double,
      longitude = array[10].jsonPrimitive.double,
      zoom = array[11].jsonPrimitive.double,
      modified = array[12].jsonPrimitive.long,
      points = array[13].jsonArray.map { item ->
        val itemArray = item.jsonArray
        AnitabiPoint(
          id = itemArray[0].jsonPrimitive.content,
          shortDescription = itemArray[1].jsonPrimitive.content,
          title = itemArray[2].jsonPrimitive.content,
          latitude = itemArray[3].jsonPrimitive.double,
          longitude = itemArray[4].jsonPrimitive.double,
          // num1 = itemArray[5].jsonPrimitive.int,
          mid = if (itemArray[6].jsonPrimitive.isString) {
            itemArray[6].jsonPrimitive.content
          } else {
            ""
          },
          // num3 = itemArray[7].jsonPrimitive.int,
          image = itemArray[8].jsonPrimitive.content,
          // num4 = itemArray[9].jsonPrimitive.int,
          ep = if (itemArray[10] is JsonArray) {
            itemArray[10].jsonArray.joinToString { it.jsonPrimitive.content }
          } else {
            itemArray[10].jsonPrimitive.content
          },
          s = itemArray[11].jsonPrimitive.content,
          description = if (itemArray[12].jsonPrimitive.isString) {
            itemArray[12].jsonPrimitive.content
          } else {
            ""
          },
          // num7 = itemArray[13].jsonPrimitive.int,
          // num8 = itemArray[14].jsonPrimitive.int,
          point = if (itemArray[15].jsonPrimitive.isString) {
            itemArray[15].jsonPrimitive.content
          } else {
            ""
          },
        )
      },
      // num1 = array[14].jsonPrimitive.int,
      // num2 = array[15].jsonPrimitive.int,
      images = if (array[16] is JsonArray) {
        array[16].jsonArray.let { item ->
          AnitabiImages(
            path = item[0].jsonPrimitive.content,
            ids = item[1].jsonArray.map { it.jsonPrimitive.content },
            modified = item[2].jsonPrimitive.long,
          )
        }
      } else {
        AnitabiImages(
          path = "",
          ids = emptyList(),
          modified = 0,
        )
      },
    )
  }

  override fun serialize(encoder: Encoder, value: AnitabiBangumi) {
    error("no support serialize for AnitabiGeoSerializer")
  }
}
