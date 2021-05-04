package ru.geekbrains.pictureoftheday.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.pod_fragment.*
import kotlinx.android.synthetic.main.pod_info_layout.*
import ru.geekbrains.pictureoftheday.R
import ru.geekbrains.pictureoftheday.activities.MainActivity
import ru.geekbrains.pictureoftheday.network.data.PODData
import ru.geekbrains.pictureoftheday.viewmodels.PODViewModel
import ru.geekbrains.pictureoftheday.viewpager.Date
import ru.geekbrains.pictureoftheday.viewpager.ViewPagerAdapter

class PODFragment : Fragment() {

    companion object {
        fun newInstance() = PODFragment()
        private var isMain = true
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
//    private lateinit var textDate: TextView

    private val viewModel: PODViewModel by lazy {
        ViewModelProvider(this).get(PODViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.pod_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view_pager.adapter = ViewPagerAdapter(childFragmentManager)
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))

        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }

        setBottomAppBar(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val currentData = Date()

        var date = currentData.today

        view_pager.currentItem = 2
        view_pager.addOnPageChangeListener(object  : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> date = currentData.theDayBeforeYesterday
                    1 -> date = currentData.yesterday
                    2 -> date = currentData.today
                }
                viewModel.getData(date).observe(viewLifecycleOwner,  { renderData(it) })
//                textDate.text = date
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
        viewModel.getData(date).observe(viewLifecycleOwner, { renderData(it) })
//        textDate.text = date
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.app_bar_settings -> activity?.
            supportFragmentManager?.
            beginTransaction()?.
            replace(R.id.container, SettingsFragment.newInstance())?.
            addToBackStack(null)?.commit()
        }
        return super.onOptionsItemSelected(item)
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
                    val imageView = view?.findViewById<ImageView>(R.id.image_view_day);
                    imageView?.load(url) {
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

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
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