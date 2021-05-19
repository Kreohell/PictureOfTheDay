package ru.geekbrains.pictureoftheday.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import kotlinx.android.synthetic.main.fragment_recycler.*
import ru.geekbrains.pictureoftheday.R


class RecyclerFragment : Fragment() {

    private lateinit var adapter: RecyclerAdapter

    companion object {
        @JvmStatic
        fun newInstance() = RecyclerFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = mutableListOf(
            Pair(Data("New Note", ""), false))
        data.add(0, Pair(Data("Header", ""), false))

        adapter = RecyclerAdapter(data)
        recyclerView.adapter = adapter
        recyclerActivityFAB.setOnClickListener {
            adapter.appendItem()
        }
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(recyclerView)

    }

}