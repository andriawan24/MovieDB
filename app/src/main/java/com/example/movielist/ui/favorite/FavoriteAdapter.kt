package com.example.movielist.ui.favorite

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
import com.example.movielist.db.model.FavoriteModel
import com.example.movielist.db.model.ResultMovie
import com.example.movielist.db.model.ResultMovieNowPlaying
import com.example.movielist.db.model.ResultMovieTopRated
import com.example.movielist.ui.detailMovie.DetailMovieActivity

class FavoriteAdapter(val context: Context?, val dataFavorite: ArrayList<FavoriteModel>): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return dataFavorite.size
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
       val dataFavoriteMovies = dataFavorite[position]

        holder.favoriteImage?.let {
            context?.let { context->
                Glide.with(context)
                        .load(BuildConfig.BASE_URL_IMAGE + dataFavoriteMovies.image)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.ic_baseline_signal_cellular_connected_no_internet_4_bar_250)
                        .into(it)

            }
        }

        holder.titleFavorite?.text = dataFavoriteMovies.name
        holder.releaseFavorite?.text= dataFavoriteMovies.releaseMovie
        holder.describeFavorite?.text = dataFavoriteMovies.description
        holder.cvFavorite?.setOnClickListener(View.OnClickListener {
            var i = Intent(context, DetailMovieActivity::class.java)
            i.putExtra("idMovie", dataFavoriteMovies.idMovie.toString())
            i.putExtra("imageMovie", dataFavoriteMovies.image.toString())
            i.putExtra("nameMovie", dataFavoriteMovies.name.toString())
            i.putExtra("releaseMovie", dataFavoriteMovies.releaseMovie.toString())
            i.putExtra("describeMovie", dataFavoriteMovies.description.toString())
            context?.startActivity(i)
        })
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val favoriteImage: ImageView? = itemView.findViewById(R.id.ivFavoriteImage)
        val titleFavorite: TextView? = itemView.findViewById(R.id.titleFavorite)
        val releaseFavorite: TextView? = itemView.findViewById(R.id.tvReleaseFavorite)
        val cvFavorite: CardView? = itemView.findViewById(R.id.cvFavorite)
        val describeFavorite: TextView? = itemView.findViewById(R.id.describeFavorite)
    }


}