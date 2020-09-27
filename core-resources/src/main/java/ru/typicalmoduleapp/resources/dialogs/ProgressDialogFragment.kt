package ru.typicalmoduleapp.resources.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.RelativeLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import ru.typicalmoduleapp.resources.R
import java.util.*

class ProgressDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        Objects.requireNonNull<Window>(dialog.window)
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.progress_dialog_darkoverlay)
        dialog.setCanceledOnTouchOutside(false)
        dialog.findViewById<RelativeLayout>(R.id.only_progress_back)?.setOnClickListener {
            val intent = Intent()
            intent.putExtra(CANCEL_CLICK, true)
            if (targetFragment != null) targetFragment?.onActivityResult(
                targetRequestCode,
                Activity.RESULT_OK,
                intent
            )
            dismiss()
        }
        return dialog
    }

    companion object {
        const val CANCEL_CLICK: String = "CANCEL_CLICK"
    }
}

fun Fragment.showProgressDialog(show: Boolean, req_id: Int) {
    if (show) {
        val frag =
            ProgressDialogFragment()
        frag.setTargetFragment(this, req_id)
        frag.show(parentFragmentManager, ProgressDialogFragment::class.java.name)
    } else {
        val f = parentFragmentManager.findFragmentByTag(ProgressDialogFragment::class.java.name)
        if (f != null) (f as DialogFragment).dismiss()
    }
}

/**
 *
 * add this in fragment/activity for process clicks
 *
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
when (requestCode){
progressDialogReq -> {
val okclicked = data?.getBooleanExtra(ErrorDialog.OK_CLICK, false)
if (okclicked!!) Toast.makeText(context,"okclicked", Toast.LENGTH_LONG).show()

}
}
}
 *
 */