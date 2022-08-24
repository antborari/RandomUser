package com.randomco.testusers.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.randomco.testusers.databinding.ActivityMainBinding
import com.randomco.testusers.domain.model.User
import com.randomco.testusers.ui.viewmodel.UserViewModel
import com.randomco.testusers.util.TabPositionEnum
import com.randomco.testusers.util.USER_DETAIL_FRAGMENT_TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val userViewModel: UserViewModel by viewModels()
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initializeView()
    }

    private fun initializeView() {
        userViewModel.isLoading.observe(this) { isLoading ->
            binding.pgLoading.isVisible = isLoading
        }

        binding.btnGetUsers.setOnClickListener {
            userViewModel.getUsers(false)
        }

        setUpAdapter()
        setUpTabs()
        setUpSearchView()
    }

    private fun setUpAdapter() {
        adapter = UserAdapter(
            ::favouriteItemClick,
            ::deleteItemClick,
            ::goToDetailUser
        )
        binding.recyclerViewUsers.adapter = adapter
        lifecycleScope.launch {
            userViewModel.userModel.collect {
                resetAdapter()
                binding.pgLoading.isVisible = false
            }
        }
    }

    private fun setUpTabs() {
        binding.tabsContainer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.searchView.apply {
                    setQuery(String(), false)
                    isIconified = true
                    clearFocus()
                }
                tab?.position?.let { resetAdapter(position = it) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setUpSearchView() {
        var lastTextToSearch: String?
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { filter(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                lastTextToSearch = newText
                if (newText.isNullOrEmpty()) {
                    filter()
                } else {
                    lifecycleScope.launch {
                        delay(1000)
                        if (lastTextToSearch == newText) {
                            filter(newText)
                        }
                    }
                }
                return false
            }
        })
    }

    private fun filter(textSearch: String = "") {
        val users = if (textSearch.isEmpty()) null else adapter.currentList
        resetAdapter(users?.filter { item ->
            val itemFilter = if (binding.rgName.isChecked) {
                item.name
            } else if (binding.rgSurname.isChecked) {
                item.surname
            } else {
                item.email
            }
            itemFilter.startsWith(
                textSearch
            )
        })
    }

    private fun submitListAdapter(users: List<User>) {
        adapter.submitList(users)
    }

    private fun favouriteItemClick(user: User) {
        userViewModel.updateUser(user)
        resetAdapter()
    }

    private fun deleteItemClick(user: User) {
        userViewModel.deleteUser(user)
    }

    private fun resetAdapter(
        users: List<User>? = null,
        position: Int = binding.tabsContainer.selectedTabPosition
    ) {
        floatingButtonVisible(false)
        when (position) {
            TabPositionEnum.USERS.position -> {
                floatingButtonVisible(true)
                submitListAdapter(users ?: userViewModel.userModel.value)
            }
            TabPositionEnum.FAVOURITES.position -> {
                submitListAdapter(users ?: userViewModel.getFavourites())
            }
        }
    }

    private fun floatingButtonVisible(isVisible: Boolean) {
        binding.btnGetUsers.isVisible = isVisible
    }

    private fun goToDetailUser(user: User) {
        floatingButtonVisible(false)
        val fragment = UserDetailFragment.newInstance(user)
        UserDetailFragment.closeFromActivity = {
            supportFragmentManager
                .beginTransaction()
                .remove(fragment)
                .commit()
            floatingButtonVisible(true)
        }
        supportFragmentManager
            .beginTransaction()
            .add(
                binding.containerActivity.id,
                fragment,
                USER_DETAIL_FRAGMENT_TAG
            )
            .commit()
    }

}