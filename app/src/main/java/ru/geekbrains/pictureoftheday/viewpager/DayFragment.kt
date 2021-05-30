package ru.geekbrains.pictureoftheday.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geekbrains.pictureoftheday.R

private const val URL_KEY = "url"

class DayFragment : Fragment() {
    private var url: String? = null

    companion object {
        @JvmStatic
        fun newInstance(date: String) =
            DayFragment().apply {
                arguments = Bundle().apply {
                    putString(URL_KEY, url)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_day, container, false)
    }
}