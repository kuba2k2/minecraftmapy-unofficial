package pl.szczodrzynski.minecraftmapy

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.widget.TextView

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

var TextView.drawableTop: Drawable?
    get() = compoundDrawables.getOrNull(1)
    set(value) {
        setCompoundDrawables(null, value, null, null)
    }
