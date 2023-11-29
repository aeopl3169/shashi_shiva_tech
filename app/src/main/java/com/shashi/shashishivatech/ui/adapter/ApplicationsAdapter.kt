package com.shashi.shashishivatech.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shashi.shashishivatech.R
import com.shashi.shashishivatech.data.models.ApplicationsResponse
import com.shashi.shashishivatech.databinding.ApplicationsItemBinding

class ApplicationsAdapter(
    private var applicationsList: List<ApplicationsResponse.Data.App>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun getItemCount(): Int {
        return applicationsList.size
    }

    class ItemViewHolder(var viewBinding: ApplicationsItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ApplicationsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as ItemViewHolder

        itemViewHolder.viewBinding.apply {

            appName.setText(applicationsList.get(position).app_name)

            appImage.load(applicationsList.get(position).app_icon) {
                placeholder(R.drawable.ic_launcher_background)
            }

            switchCompat.isChecked = applicationsList.get(position).status.equals("Active", true)

        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun filterList(filterlist: ArrayList<ApplicationsResponse.Data.App>) {

        applicationsList = filterlist
        notifyDataSetChanged()
    }


}