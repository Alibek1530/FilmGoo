package com.ali.filmgokino


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter
import github.chenupt.multiplemodel.viewpager.PagerModelManager
import github.chenupt.springindicator.SpringIndicator
import kotlinx.android.synthetic.main.fragment_new.*
import java.util.ArrayList


class NewFragment(var list: ArrayList<ArrayList<ModelVideo>>, var listName: ArrayList<String>) :
    Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //   pagerr.setPageTransformer(true, Zoom())

        val manager = PagerModelManager()
        manager.addCommonFragment(FragmentMain::class.java, getBgRes(), getTitles())
        val adapter = ModelPagerAdapter(fragmentManager, manager)
        pagerr.adapter = adapter
        indicatorr.setViewPager(pagerr)

    }


    private fun getTitles(): ArrayList<String> {
        return listName
    }

    private fun getBgRes(): ArrayList<ArrayList<ModelVideo>> {
        return list
    }
}