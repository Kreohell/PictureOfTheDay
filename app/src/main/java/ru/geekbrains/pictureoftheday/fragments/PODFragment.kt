package ru.geekbrains.pictureoftheday.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.pod_fragment.*
import kotlinx.android.synthetic.main.pod_info_layout.*
import ru.geekbrains.pictureoftheday.R
import ru.geekbrains.pictureoftheday.network.data.PODData
import ru.geekbrains.pictureoftheday.viewmodels.PODViewModel

class PODFragment : Fragment() {

    companion object {
        fun newInstance() = PODFragment()
        private var isMain = true
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val viewModel: PODViewModel by lazy {
        ViewModelProvider(this).get(PODViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.pod_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))

        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })
    }

    private fun renderData(data: PODData) {
        when (data) {
            is PODData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                val descriptionTitleText = serverResponseData.title
                val descriptionText = serverResponseData.explanation
                if (url.isNullOrEmpty()) {
                   toast("Link is empty")
                } else {
                     image_view.load(url) {
                        lifecycle(this@PODFragment)
                        error(R.drawable.ic_baseline_error_24)
                        placeholder(R.drawable.ic_baseline_insert_photo_24)
                    }
                    bottom_sheet_description_header.text = descriptionTitleText
                    bottom_sheet_description.text = descriptionText
                }
            }
            is PODData.Loading -> {
                //showLoading()
            }
            is PODData.Error -> {
                toast(data.error.message)
            }
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.isHideable = false
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

}