package tech.central.showcase.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.EpoxyItemSpacingDecorator
import kotlinx.android.synthetic.main.fragment_photo.*
import tech.central.showcase.MainViewModelFactory
import tech.central.showcase.R
import tech.central.showcase.base.BaseFragment
import tech.central.showcase.photo.controller.PhotoController
import javax.inject.Inject
import javax.inject.Provider

class PhotoFragment : BaseFragment() {
    companion object {
        @JvmStatic
        private val TAG = PhotoFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): PhotoFragment {
            val fragment = PhotoFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    //Injection
    @Inject
    lateinit var mMainViewModelFactory: MainViewModelFactory
    @Inject
    lateinit var mPhotoController: PhotoController
    @Inject
    lateinit var mLayoutManagerProvider: Provider<GridLayoutManager>
    @Inject
    lateinit var mItemDecoration: EpoxyItemSpacingDecorator

    //Data Members
    private val mPhotoViewModel by lazy { ViewModelProviders.of(activity, mMainViewModelFactory).get(PhotoViewModel::class.java) }
    private lateinit var mLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        arguments?.apply { }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_photo, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance(view, savedInstanceState)

        //Register ViewModel
        mPhotoViewModel.bindPhotoLiveData()
                .observe(this, Observer {
                    mPhotoController.setData(it)
                })
    }

    private fun initInstance(rootView: View?, savedInstanceState: Bundle?) {
        //Init View instance
        mLayoutManager = mLayoutManagerProvider.get()
        recyclerView.apply {
            adapter = mPhotoController.adapter
            layoutManager = mLayoutManager
            addItemDecoration(mItemDecoration)
        }
    }
}