package pl.szczodrzynski.minecraftmapy.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.hadilq.liveevent.LiveEvent

abstract class BaseViewModel : ViewModel() {
    val navCommand = LiveEvent<NavDirections>()

    protected fun navigate(navDirections: NavDirections) {
        navCommand.value = navDirections
    }
}
