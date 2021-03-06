package org.wit.sites.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_site.view.*
import org.wit.sites.R
import org.wit.sites.models.SiteModel

interface SiteListener {
    fun onSiteClick(site: SiteModel)
}

class SiteAdapter constructor(
    private var sites: List<SiteModel>,
    private val listener: SiteListener
) : RecyclerView.Adapter<SiteAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_site, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val site = sites[holder.adapterPosition]
        holder.bind(site, listener)
    }

    override fun getItemCount(): Int = sites.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(site: SiteModel, listener: SiteListener) {
            itemView.siteTitle.text = site.title
            itemView.description.text = site.description
            Glide.with(itemView.context).load(site.image1).into(itemView.imageIcon);
            itemView.setOnClickListener { listener.onSiteClick(site) }
        }
    }
}