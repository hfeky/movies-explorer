package com.husseinelfeky.moviesexplorer.ui.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.husseinelfeky.moviesexplorer.databinding.FragmentMasterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MasterFragment : Fragment() {

    private val viewModel: MasterViewModel by viewModel()

    private lateinit var binding: FragmentMasterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMasterBinding.inflate(inflater, container, false)
        return binding.root
    }
}
