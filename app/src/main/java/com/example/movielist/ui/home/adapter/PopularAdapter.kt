package com.example.movielist.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movielist.BuildConfig
import com.example.movielist.R
import com.example.movielist.db.model.ResultMovie
import com.example.movielist.ui.detailMovie.DetailMovieActivity

class PopularAdapter(val context: Context?, val dataPopular: ArrayList<ResultMovie>): RecyclerView.Adapter<PopularAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_populer_movie, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return dataPopular.size
    }

    override fun onBindViewHolder(holder: PopularAdapter.ViewHolder, position: Int) {
       val dataPopulerMovies = dataPopular[position]

        holder.imagePopuler?.let {
            context?.let { context->
                Glide.with(context)
                        .load(BuildConfig.BASE_URL_IMAGE + dataPopulerMovies.backdrop_path)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.ic_baseline_signal_cellular_connected_no_internet_4_bar_250)
                        .into(it)

            }
        }
        holder.popularMovie?.setOnClickListener(View.OnClickListener {
            var i = Intent(context, DetailMovieActivity::class.java)
            i.putExtra("idMovie", dataPopulerMovies.id.toString())
            i.putExtra("imageMovie", dataPopulerMovies.backdrop_path.toString())
            i.putExtra("nameMovie", dataPopulerMovies.title.toString())
            i.putExtra("releaseMovie", dataPopulerMovies.release_date.toString())
            i.putExtra("describeMovie", dataPopulerMovies.overview.toString())
            context?.startActivity(i)
        })


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagePopuler: ImageView? = itemView.findViewById(R.id.ivImagePopulerMovie)
        val popularMovie: CardView? = itemView.findViewById(R.id.cvPopularMovie)
    }
}