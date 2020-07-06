package pl.szczodrzynski.minecraftmapy

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.forEach
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import coil.api.load
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.typeface.library.community.material.CommunityMaterial
import com.mikepenz.iconics.utils.sizeDp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext = Job() + Dispatchers.IO

    private val viewModel: MainViewModel by viewModels()
    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_Dark)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)

        bottomNav.setupWithNavController(navController)
        bottomNav.menu.forEach {
            val icon: IIcon = when (it.itemId) {
                R.id.menu_home -> CommunityMaterial.Icon2.cmd_home_outline
                R.id.menu_browse -> CommunityMaterial.Icon.cmd_folder_search_outline
                R.id.menu_notifications -> CommunityMaterial.Icon.cmd_bell_outline
                R.id.menu_account -> CommunityMaterial.Icon.cmd_account_circle_outline
                else -> CommunityMaterial.Icon2.cmd_help
            }
            it.icon = IconicsDrawable(this, icon).apply {
                sizeDp = 24
            }
        }

        appBarLayout.setExpanded(false, false)
        appBarLayout.updateLayoutParams<CoordinatorLayout.LayoutParams> {
            val behavior = behavior as BlockableAppBarLayoutBehavior
            behavior.isShouldScroll = false
        }

        viewModel.toolbarTitle.observe(this) {
            collapsingToolbar.title = it
        }
        viewModel.toolbarExpandableImage.observe(this) {
            appBarLayout.setExpanded(it != null, true)
            appBarLayout.updateLayoutParams<CoordinatorLayout.LayoutParams> {
                (behavior as BlockableAppBarLayoutBehavior).isShouldScroll = it != null
            }
            if (it != null) {
                toolbarImage.load(it) {
                    crossfade(true)
                    placeholder(R.drawable.cyan_concrete_powder)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
