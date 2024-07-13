import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.herika.flipkartassignment.DetailActivity
import com.herika.flipkartassignment.R  // Replace with your own package name
import com.herika.flipkartassignment.data.PlayerWithPoints

class PlayerAdapter(private val context: Context) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    private var playersWithPoints: List<PlayerWithPoints> = emptyList()

    fun updateData(newData: List<PlayerWithPoints>) {
        playersWithPoints = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.each_item_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = playersWithPoints[position]
        holder.bind(player)
        holder.itemView.setOnClickListener {
            val playerId = player.id
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("playerId", playerId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return playersWithPoints.size
    }

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewPlayerName: TextView = itemView.findViewById(R.id.textViewName)
        private val textViewPlayerPoints: TextView = itemView.findViewById(R.id.textViewPoints)
        private val imageViewPlayerIcon: ImageView = itemView.findViewById(R.id.imageViewIcon)

        fun bind(player: PlayerWithPoints) {
            textViewPlayerName.text = player.name
            textViewPlayerPoints.text = "${player.points}"

            // Load image using Glide or any other image loading library

            // Load image using Glide with RequestOptions
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background) // Placeholder image if iconUrl is null or loading fails
                .error(R.drawable.ic_launcher_foreground) // Error image if loading fails
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) // Cache strategy
                .dontAnimate() // Disable any animations

            Glide.with(context)
                .load(player.icon)
                .apply(requestOptions)
                .into(imageViewPlayerIcon)
        }
    }
}
