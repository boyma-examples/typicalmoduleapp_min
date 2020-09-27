package ru.typicalmoduleapp.startpoint.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.main_activity.view.*
import ru.typicalmoduleapp.R
import ru.typicalmoduleapp.appcore.common.auth.AuthManager
import ru.typicalmoduleapp.databinding.MainActivityBinding
import ru.typicalmoduleapp.startpoint.di.createMainActivityComponent
import ru.typicalmoduleapp.utils.di.DaggerViewModelFactory
import ru.typicalmoduleapp.utils.kotlin.forceExhaustive
import ru.typicalmoduleapp.utils.livedata.observeNonNull
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val nav
        get() = findNavController(R.id.main_navigation_fragment)

    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory<MainActivityViewModelImpl>
    private lateinit var viewModel: MainActivityViewModelImpl

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        createMainActivityComponent(this).inject(this)
        super.onCreate(savedInstanceState)
        initViewModels()
        initBinding()
        initUI()
        initAuthNavigation()
    }

    private fun initViewModels() {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainActivityViewModelImpl::class.java)
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initUI() {
        binding.root.bottomNavigation.setupWithNavController(nav)
        supportActionBar?.hide()
    }

    private fun initAuthNavigation() {
        nav.addOnDestinationChangedListener { _, destination, _ ->
            onDestinationChanged(destination)
        }

        observeNonNull(viewModel.authEvents) {
            when (it) {
                AuthManager.AuthEvent.ExplicitLogOut -> onExplicitLogout()
                AuthManager.AuthEvent.AuthDeath -> onAuthDeath()
                AuthManager.AuthEvent.NewLogIn -> onNewLogin()
                AuthManager.AuthEvent.ReLogIn -> onReLogin()
            }.forceExhaustive
        }
    }

    private fun onDestinationChanged(destination: NavDestination) {
        if (!viewModel.isLoggedIn() && !isLoginDest(destination))
            goToLogin()
        viewModel.showBottomNavigationMenu.value = isMainDests(destination)
    }

    private fun isMainDests(destination: NavDestination?): Boolean {
        var parent = destination?.parent
        while (parent != null) {
            if (parent.id == R.id.firsttab_navigation || parent.id == R.id.secondtab_navigation)
                return true
            parent = parent.parent
        }
        return false
    }

    private fun onAuthDeath() {
        goToLogin()
    }

    private fun onNewLogin() {
        nav.navigate(
            R.id.firsttab_navigation, null, NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setPopUpTo(R.id.app_navigation, true)
                .build()
        )
    }

    private fun onExplicitLogout() {
        nav.popBackStack(R.id.app_navigation, true) // pop whole back stack
        goToLogin()
    }

    private fun onReLogin() {
        /*nav.popBackStack(R.id.login_graph, true) // pop login fragment

        // if no fragments were in back stack
        if (nav.currentDestination == null)
            onNewLogin()*/
    }

    /** @return true if current destination is located in sub-graph of login navigation */
    private fun isLoginDest(destination: NavDestination?): Boolean {
        /*var parent = destination?.parent
        while (parent != null) {
            if (parent.id == R.id.login_graph)
                return true
            parent = parent.parent
        }*/
        return false
    }

    private fun goToLogin() {
        /*if (!isLoginDest(nav.currentDestination))
            nav.navigate(R.id.login_graph)*/
    }
}
