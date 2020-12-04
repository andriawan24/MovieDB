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
import com.example.movielist.db.model.ResultMovieNowPlaying
import com.example.movielist.db.model.ResultMovieTopRated
import com.example.movielist.ui.detailMovie.DetailMovieActivity

class NowPlayingAdapter(val context: Context?, val dataNowPlaying: ArrayList<ResultMovieNowPlaying>): RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_nowplaying_movie, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return dataNowPlaying.size
    }

    override fun onBindViewHolder(holder: NowPlayingAdapter.ViewHolder, position: Int) {
       val dataNowPlayingMovies = dataNowPlaying[position]

        holder.imageNowPlayingMovie?.let {
            context?.let { context->
                Glide.with(context)
                        .load(BuildConfig.BASE_URL_IMAGE + dataNowPlayingMovies.backdrop_path)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.ic_baseline_signal_cellular_connected_no_internet_4_bar_250)
                        .into(it)

            }
        }

        holder.titleNowPlayingMovie?.text = dataNowPlayingMovies.title
        holder.releaseNowPlayingMovie?.text= dataNowPlayingMovies.release_date
        holder.cvNowPlaying?.setOnClickListener(View.OnClickListener {
            var i = Intent(context, DetailMovieActivity::class.java)
            i.putExtra("idMovie", dataNowPlayingMovies.id.toString())
            i.putExtra("imageMovie", dataNowPlayingMovies.backdrop_path.toString())
            i.putExtra("nameMovie", dataNowPlayingMovies.title.toString())
            i.putExtra("releaseMovie", dataNowPlayingMovies.release_date.toString())
            i.putExtra("describeMovie", dataNowPlayingMovies.overview.toString())
            context?.startActivity(i)
        })
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageNowPlayingMovie: ImageView? = itemView.findViewById(R.id.ivImageNowPlayingMovie)
        val titleNowPlayingMovie: TextView? = itemView.findViewById(R.id.tvTitleNowPlayingMovie)
        val releaseNowPlayingMovie: TextView? = itemView.findViewById(R.id.tvReleaseNowPlayingMovie)
        val cvNowPlaying: CardView? = itemView.findViewById(R.id.cvNowPlaying)
    }


}