package pl.szczodrzynski.minecraftmapy

import android.view.View
import android.widget.ImageView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation

@BindingAdapter("android:isVisible")
fun bindIsVisible(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}

@BindingAdapter("android:isInvisible")
fun bindIsInvisible(view: View, isInvisible: Boolean) {
    view.isInvisible = isInvisible
}

@BindingAdapter("android:url")
fun bindUrl(imageView: ImageView, url: String?) {
    imageView.load(url) {
        crossfade(true)
    }
}

@BindingAdapter("android:urlCircled")
fun bindUrlCircled(imageView: ImageView, url: String?) {
    imageView.load(url) {
        crossfade(true)
        transformations(CircleCropTransformation())
    }
}
