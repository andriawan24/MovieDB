package com.example.movielist.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movielist.BuildConfig
import com.example.movielist.R
import com.example.movielist.db.model.ResultMovie
import com.example.movielist.db.model.ResultMovieTopRated
import com.example.movielist.ui.detailMovie.DetailMovieActivity

class TopRatedAdapter(val context: Context?, val dataTopRated: ArrayList<ResultMovieTopRated>): RecyclerView.Adapter<TopRatedAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_toprated_movie, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return dataTopRated.size
    }

    override fun onBindViewHolder(holder: TopRatedAdapter.ViewHolder, position: Int) {
       val dataTopRatedMovies = dataTopRated[position]

        holder.imageTopRated?.let {
            context?.let { context->
                Glide.with(context)
                        .load(BuildConfig.BASE_URL_IMAGE + dataTopRatedMovies.backdrop_path)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.ic_baseline_signal_cellular_connected_no_internet_4_bar_250)
                        .into(it)

            }
        }

        holder.tvToprated?.text = dataTopRatedMovies.title
        holder.tvReleaseTopRated?.text= dataTopRatedMovies.release_date
        holder.topRated?.setOnClickListener(View.OnClickListener {
            var i = Intent(context, DetailMovieActivity::class.java)
            i.putExtra("idMovie", dataTopRatedMovies.id.toString())
            i.putExtra("imageMovie", dataTopRatedMovies.backdrop_path.toString())
            i.putExtra("nameMovie", dataTopRatedMovies.title.toString())
            i.putExtra("releaseMovie", dataTopRatedMovies.release_date.toString())
            i.putExtra("describeMovie", dataTopRatedMovies.overview.toString())
            context?.startActivity(i)
        })
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageTopRated: ImageView? = itemView.findViewById(R.id.ivImageTopRatedMovie)
        val tvToprated: TextView? = itemView.findViewById(R.id.tvTitleTopRatedMovie)
        val tvReleaseTopRated: TextView? = itemView.findViewById(R.id.tvReleaseTopRatedMovie)
        val topRated: CardView? = itemView.findViewById(R.id.cvTopRated)
    }
}