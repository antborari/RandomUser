# Aplicación android

- UI lista/detalle.
- Uso de:
  - Room
  - Flow
  - Coroutines
  - Dagger Hilt
  - Jetpack Compose y Layout xml
    - Compose como view en un Fragment de androidx (1)
    - Animaciones
    - Recyclerview, ConstraintLayour, CoordinatorLayout (floating btn)


## (1) Compose view en Fragment de androidx

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
<img src="files/screen.gif" width="200">

## License

[MIT](https://choosealicense.com/licenses/mit/)
