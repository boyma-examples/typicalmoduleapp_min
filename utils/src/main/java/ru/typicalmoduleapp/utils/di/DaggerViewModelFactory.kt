package ru.typicalmoduleapp.utils.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

// https://stackoverflow.com/a/48819177
class DaggerViewModelFactory<T : ViewModel> @Inject constructor(
    private val viewModel: dagger.Lazy<T>
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = viewModel.get() as T
}
