package com.randomco.testusers.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.randomco.testusers.domain.model.User
import com.randomco.testusers.util.USER_DETAIL_EXTRA_KEY
import com.randomco.testusers.util.dateFormat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserDetailFragment : Fragment() {

    companion object {
        fun newInstance(user: User, closeCallback: () -> Unit = {}) = UserDetailFragment().apply {
            closeFromActivity = closeCallback
            arguments =
                bundleOf().apply {
                    putParcelable(USER_DETAIL_EXTRA_KEY, user)
                }
        }

        lateinit var closeFromActivity: () -> Unit
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                arguments?.getParcelable<User>(USER_DETAIL_EXTRA_KEY)?.let {
                    DetailUser(it)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun DetailUser(user: User) {
        var cardIsVisible by remember { mutableStateOf(false) }

        AnimatedVisibility(visible = cardIsVisible, enter = fadeIn(), exit = fadeOut()) {
            Surface(
                color = Color.Black.copy(alpha = 0.6f),
                modifier = Modifier.fillMaxSize(),
                onClick = {
                    cardIsVisible = false
                    lifecycleScope.launch {
                        delay(120)
                        closeFromActivity()
                    }
                }
            ) {}
        }

        Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxHeight()) {
            AnimatedVisibility(
                cardIsVisible,
                enter = slideInVertically(initialOffsetY = { 600 }),
                exit = slideOutVertically(targetOffsetY = { 1800 }),
            ) {
                Card(
                    modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp)),
                    contentColor = Color.DarkGray,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = user.picture,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.height(60.dp)
                                .width(60.dp)
                                .clip(CircleShape)
                        )
                        Spacer(Modifier.height(28.dp))
                        Text("${user.name} ${user.surname}")
                        Spacer(Modifier.height(8.dp))
                        Text(user.date.dateFormat())
                        Spacer(Modifier.height(8.dp))
                        Text(user.gender)
                        Spacer(Modifier.height(8.dp))
                        Text(user.email)
                        Spacer(Modifier.height(8.dp))
                        Text(user.phone)
                        Spacer(Modifier.height(8.dp))
                        Text(user.street)
                        Spacer(Modifier.height(8.dp))
                        Text(user.city)
                        Spacer(Modifier.height(8.dp))
                        Text(user.state)
                    }
                }
            }
        }

        if (!cardIsVisible)
            lifecycleScope.launch {
                delay(100)
                cardIsVisible = true
            }
    }

}