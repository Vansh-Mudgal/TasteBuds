package com.example.tastebuds.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tastebuds.Notifications
import com.example.tastebuds.R
import com.example.tastebuds.adapter.NotificationAdapter
import com.example.tastebuds.databinding.FragmentNotificationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotificationFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater,container,false)

        val notificationAdapter = NotificationAdapter(Notifications.getData())
        binding.notificationRecView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.notificationRecView.adapter = notificationAdapter

        return binding.root
    }

    companion object {

    }
}