package zz.hao.sample.bean

/**
 * DESC:
 * Create By ZWH  On  2019/12/27 0027
 */
data class AddressBean(
    val `data`: List<Province>,
    val message: String,
    val other: Any,
    val status: Int
)

data class Province(
    val area_id: String,
    val area_name: String,
    val city: List<City>
)

data class City(
    val area_id: String,
    val area_name: String,
    val count: List<Count>
)

data class Count(
    val area_id: String,
    val area_name: String
)