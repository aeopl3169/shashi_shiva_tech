package com.shashi.shashishivatech.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.shashi.shashishivatech.data.models.ApplicationsResponse
import com.shashi.shashishivatech.data.networks.Resource
import com.shashi.shashishivatech.databinding.FragmentApplicationBinding
import com.shashi.shashishivatech.ui.ApplicationsViewModel
import com.shashi.shashishivatech.ui.adapter.ApplicationsAdapter
import com.shashi.shashishivatech.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplicationsFragment : BaseFragment() {
    private lateinit var binding: FragmentApplicationBinding
    private val viewModel by viewModels<ApplicationsViewModel>()
    lateinit var applicationsList: ArrayList<ApplicationsResponse.Data.App>

    lateinit var adapter: ApplicationsAdapter

    // inflate the layout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApplicationBinding.inflate(inflater, container, false)

        viewModel.getApplications(
            "378"
        )


        lifecycleScope.launchWhenStarted {
            viewModel.getApplicationsFlow.collect { it ->
                when (it) {

                    is Resource.Loading -> {
                        showLoading()
                    }

                    is Resource.Error -> {
                        dismissLoading()
                        Snackbar.make(binding.root, it.exception, Snackbar.LENGTH_SHORT).show()
                    }

                    is Resource.Success -> {
                        dismissLoading()
                        if (it.data.success == true) {

                            applicationsList =
                                it.data.data.app_list as ArrayList<ApplicationsResponse.Data.App>
                            adapter = ApplicationsAdapter(
                                it.data.data.app_list,
                            )

                            binding.recyclerView.adapter = adapter
                        } else {
                            Snackbar.make(binding.root, it.data.message, Snackbar.LENGTH_SHORT)
                                .show()
                        }

                    }
                }
            }
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                filter(msg)
                return false
            }
        })

        return binding.root
    }

    private fun filter(text: String) {
        val filteredlist: ArrayList<ApplicationsResponse.Data.App> = ArrayList()

        for (item in applicationsList) {
            if (item.app_name.toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            adapter.filterList(filteredlist)
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    private fun dismissLoading() {
        binding.progressBar.visibility = View.GONE
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    companion object {
        private const val TAG = "ApplicationsFragment"
    }


}
