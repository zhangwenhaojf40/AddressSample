package zz.hao.sample.util

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * DESC:
 * Create By ZWH  On  2019/12/27 0027
 */
object  StreamTools {

    fun getStringJson(fileName: String, context: Context):String {
        val assets = context.assets
        var inputStream = assets.open(fileName)
        val reader=BufferedReader(InputStreamReader(inputStream))
        return reader.readText()

    }
}