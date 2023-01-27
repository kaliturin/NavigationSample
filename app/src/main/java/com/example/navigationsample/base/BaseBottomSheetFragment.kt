package com.example.navigationsample.base

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheetFragment(@LayoutRes contentLayoutId: Int) :
    BottomSheetDialogFragment(contentLayoutId) {

    open val bottomSheetBehaviorState: Int = BottomSheetBehavior.STATE_EXPANDED
    open val bottomSheetBehaviorIsDraggable: Boolean = true
    open val bottomSheetBehaviorSkipCollapsed: Boolean = true
    open val bottomSheetWindowGravity: Int = Gravity.BOTTOM
    open val bottomSheetWindowClearFlagDimBehind: Boolean = true
    open val bottomSheetDesignViewId: Int = com.google.android.material.R.id.design_bottom_sheet

    @DrawableRes
    open val bottomSheetWindowBackgroundDrawableResource: Int = android.R.color.transparent

    protected var bottomSheetBehavior: BottomSheetBehavior<*>? = null
        private set

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.apply {
                setGravity(bottomSheetWindowGravity)
                setBackgroundDrawableResource(bottomSheetWindowBackgroundDrawableResource)
                if (bottomSheetWindowClearFlagDimBehind)
                    clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }

            setOnShowListener {
                val bottomSheet = (it as BottomSheetDialog)
                    .findViewById<View>(bottomSheetDesignViewId) as FrameLayout
                bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet).apply {
                    state = bottomSheetBehaviorState
                    isDraggable = bottomSheetBehaviorIsDraggable
                    skipCollapsed = bottomSheetBehaviorSkipCollapsed
                }
            }
        }
    }
}