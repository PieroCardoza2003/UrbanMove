package com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.FragmentMapBinding
import com.capstone.urbanmove.databinding.FragmentRegisterDriverBinding


class RegisterDriverFragment : Fragment() {

    private var _binding: FragmentRegisterDriverBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterDriverBinding.inflate(inflater, container, false)


        return binding.root
    }

}