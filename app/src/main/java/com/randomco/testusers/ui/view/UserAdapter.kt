package com.randomco.testusers.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.randomco.testusers.R
import com.randomco.testusers.databinding.ItemUserBinding
import com.randomco.testusers.domain.model.User

class UserAdapter(
    private val favouriteItemClick: (user: User) -> Unit,
    private val deleteItemClick: (user: User) -> Unit,
    private val goToDetailUser: (user: User) -> Unit
) :
    ListAdapter<User, UserAdapter.ViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return ViewHolder(view, favouriteItemClick, deleteItemClick, goToDetailUser)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        view: View,
        private val favouriteItemClick: (user: User) -> Unit,
        private val deleteItemClick: (user: User) -> Unit,
        private val goToDetailUser: (user: User) -> Unit,
    ) : RecyclerView.ViewHolder(view) {
        private val binding = ItemUserBinding.bind(view)

        fun bind(user: User) {
            binding.apply {
                tvNameSurname.text = "${user.name} ${user.surname}"
                tvEmail.text = user.email
                tvPhone.text = user.phone
                imgUser.load(user.picture)
                imgUser.setOnClickListener { goToDetailUser(user) }
                setUpFavouriteUser(user, imgBtnIsFavouriteUser)
                imgBtnIsFavouriteUser.setOnClickListener {
                    favouriteItemClick(user.apply { isFavourite = !isFavourite })
                    setUpFavouriteUser(user, imgBtnIsFavouriteUser)
                }
                imgBtnDeleteUser.setOnClickListener {
                    deleteItemClick(user)
                }
            }
        }

        private fun setUpFavouriteUser(user: User, view: ImageButton) {
            val isFavouriteDrawable = if (user.isFavourite)
                ContextCompat.getDrawable(binding.root.context, R.drawable.ic_fav_enable)
            else
                ContextCompat.getDrawable(binding.root.context, R.drawable.ic_fav_disable)
            view.load(isFavouriteDrawable)
        }
    }
}

object DiffUtilCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.uuid == newItem.uuid &&
                oldItem.name == newItem.name &&
                oldItem.surname == newItem.surname &&
                oldItem.email == newItem.email &&
                oldItem.phone == newItem.phone

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem
}