import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.revsb_11.adapters.SelectedFilesDiffCallback
import com.example.revsb_11.adapters.SelectedFilesViewHolder
import com.example.revsb_11.dataclasses.SelectedFile
import com.example.revsb_11.databinding.ItemLayoutBinding

class SelectedFilesAdapter(
    private val onEditButtonClicked: (SelectedFile) -> Unit,
    private val onSwipeToDelete: (SelectedFile) -> Unit
) : ListAdapter<SelectedFile, SelectedFilesAdapter.ItemViewHolder>(SelectedFilesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewTypr: Int): ItemViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, onEditButtonClicked)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun attachSwipeToDelete(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                removeItem(position)
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun removeItem(position: Int) {
        val updatedList = currentList.toMutableList()
        onSwipeToDelete(currentList[position])
        updatedList.removeAt(position)
        submitList(updatedList)
    }


    class ItemViewHolder(
        binding: ItemLayoutBinding,
        onEditButtonClicked: (SelectedFile) -> Unit
    ) : SelectedFilesViewHolder(binding, onEditButtonClicked)
}