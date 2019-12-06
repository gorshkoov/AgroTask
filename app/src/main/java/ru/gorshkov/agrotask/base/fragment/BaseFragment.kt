package ru.gorshkov.agrotask.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    private var rootView: View? = null
    protected abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater, root: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(layoutRes, root, false)
        return rootView
    }
}