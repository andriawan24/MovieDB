package aish.android.countries.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movielist.db.model.Movie
import com.example.movielist.db.model.ResultMovie

@Dao
interface MoviesPopulerDao {

    @Query("SELECT * FROM MoviePopuler")
    fun findAll(): List<ResultMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(movie: List<ResultMovie>)
}