package ru.geekbrains.pictureoftheday.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.ChipGroup
import ru.geekbrains.pictureoftheday.R

class SettingsFragment : Fragment() {

    private val NAME_SHARED_PREFERENCE = "LOGIN"
    private val APP_THEME = "APP_THEME"
    private val MOON_THEME = 0
    private val GRASS_THEME = 1

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity?.setTheme(getAppTheme(R.style.Theme_PictureOfTheDay_Moon))
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChipGroup()
    }

    private fun getAppTheme(codeStyle: Int): Int {
        return codeStyleToStyleId(getCodeStyle(codeStyle))
    }


    private fun codeStyleToStyleId(codestyle: Int): Int {
        return when (codestyle) {
            MOON_THEME -> R.style.Theme_PictureOfTheDay_Moon
            GRASS_THEME -> R.style.Theme_PictureOfTheDay_Grass
            else -> R.style.Theme_PictureOfTheDay_Moon
        }
    }

    private fun getCodeStyle(codestyle: Int): Int {
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(NAME_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        return sharedPref?.getInt(APP_THEME, codestyle)!!
    }

    private fun initChipGroup() {
        clickedChip(view?.findViewById(R.id.moonTheme), MOON_THEME)
        clickedChip(view?.findViewById(R.id.grassTheme), GRASS_THEME)
        val chipGroup = view?.findViewById<ChipGroup>(R.id.chip_group)
        chipGroup?.isSelectionRequired = true;
    }

    private fun clickedChip(chip: View?, codestyle: Int) {
        chip?.setOnClickListener {
            setAppTheme(codestyle)
            activity?.recreate()
        }
    }

    private fun setAppTheme(codestyle: Int) {
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(NAME_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.putInt(APP_THEME, codestyle)
        editor?.apply()
    }
}