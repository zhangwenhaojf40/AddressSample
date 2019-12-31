package zz.hao.sample.dialog

import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.view.Gravity
import android.view.View
import zz.hao.sample.R

/**
 * DESC:
 * Create By ZWH  On  2019/12/31 0031
 */
class DialogBottom {
    companion object{

    fun showDialog(act:Activity){
        val dialog = Dialog(act, R.style.ActionSheetDialogStyle)
        val view = View.inflate(act, R.layout.dialog_photo, null)
        dialog.setContentView(view)
        val window=dialog.window!!
        val params= window.attributes
        params.width=ActionBar.LayoutParams.MATCH_PARENT
        window.attributes=params
        window.setGravity(Gravity.BOTTOM)
        dialog.show()
      }
    }
}