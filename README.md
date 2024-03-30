# List/detail

- Skills:
  - Room
  - Flow
  - Coroutines
  - Dagger Hilt
  - Jetpack Compose y Layout xml
    - ComposeView into Fragment androidx (1)
    - Animations
    - Recyclerview, ConstraintLayour, CoordinatorLayout (floating btn)


## (1) ComposeView into Fragment androidx

```kotlin

class UserDetailFragment : Fragment() {

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
      ...
    }

}
```

## Screen
<img src="files/screen.gif" width="300">

## License

[MIT](https://choosealicense.com/licenses/mit/)
