package com.typicalmoduleapp.postfeature.posts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.typicalmoduleapp.postfeature.R
import com.typicalmoduleapp.postfeature.databinding.PostsFragmentBinding
import com.typicalmoduleapp.postfeature.posts.di.createPostsComponent
import com.typicalmoduleapp.postfeature.posts.view.PostsFragmentViewModelImpl.Navigate
import ru.typicalmoduleapp.utils.di.DaggerViewModelFactory
import ru.typicalmoduleapp.utils.livedata.observeOnView
import javax.inject.Inject

class PostsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory<PostsFragmentViewModelImpl>
    private lateinit var viewModel: PostsFragmentViewModelImpl

    private var binding: PostsFragmentBinding? = null
    //private lateinit var args: PasswordRecoveryConfirmArgs

    override fun onCreate(savedInstanceState: Bundle?) {
        injectFields()
        super.onCreate(savedInstanceState)
    }

    private fun injectFields() {
        createPostsComponent(this).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModels()
        initData()
        initBinding(inflater, container)
        initUI()
        initViewModelsSubs()
        //viewModel.onCreate(args)
        viewModel.on()
        return binding?.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun initViewModels() {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(PostsFragmentViewModelImpl::class.java)
        //first exec
        /*officeMapSharedViewModel = activity?.run {
            ViewModelProviders.of(this)[OfficeMapSharedViewModel::class.java]
        }!!*/

        //sec exec
        /*activity?.let {
            officeMapSharedViewModel = ViewModelProviders.of(it).get(OfficeMapSharedViewModel::class.java)
        }*/
    }

    private fun initData() {
        //args = arguments?.getSerializable(argsKey) as PostsFragmentArgs
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, R.layout.posts_fragment, container, false)
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.viewModel = viewModel
    }

    private fun initUI() {
        /*binding!!.root.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }*/
    }

    private fun initViewModelsSubs() {
        /*observeOnView(viewModel.command) {
            *//*when(it){
                is Command.FocusCodeInput -> onFocusCodeInputCommand()
            }*//*
        }*/
        observeOnView(viewModel.navigate) {
            when (it) {
                is Navigate.ToSelf -> {
                    findNavController().navigate(
                        R.id.posts_navigation
                    )
                }
            }
        }
    }

}
