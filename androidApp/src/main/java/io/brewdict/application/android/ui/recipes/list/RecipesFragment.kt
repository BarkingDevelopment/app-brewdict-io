package io.brewdict.application.android.ui.recipes.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.brewdict.application.android.R
import io.brewdict.application.android.databinding.FragmentRecipesBinding
import io.brewdict.application.android.ui.recipes.RecipeComponents.ShortRecipeCard
import io.brewdict.application.android.utils.SharedComponents.MultiToggleButton
import io.brewdict.application.apis.brewdict.models.Recipe

class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private lateinit var viewModel: RecipesViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<FragmentRecipesBinding>(
            inflater,
            R.layout.fragment_recipes,
            container,
            false
        ).apply {
            composeView.setContent {
                MaterialTheme {
                    Content()
                }
            }
        }
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(RecipesViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun createRecipe(){
        view?.findNavController()?.navigate(
            R.id.action_new_recipe
        )
    }

    fun viewRecipe(recipe: Recipe){
        view?.findNavController()?.navigate(
            R.id.action_view_recipe,
            bundleOf( "recipe" to recipe)
        )
    }


    // TODO: Extract to index class. SAme as StylesFragment Sorting().
    @Composable
    fun Sorting(){
        val expanded = remember { mutableStateOf(false) }

        val items = viewModel.sortingFields

        val selectedField: RecipeSortingFieldEnum by viewModel.currentSortingField.collectAsState()
        val ascending: Boolean by viewModel.isSortingAscending.collectAsState()

        val sortOrderIcon = if(ascending) R.drawable.ic_baseline_arrow_upward_24
            else R.drawable.ic_baseline_arrow_downward_24

        Row (
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ){
            Box {
                Column(
                ) {
                    Button(
                        onClick = {
                            expanded.value = true
                        },
                        content = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Text(
                                    text = selectedField.acronym
                                )

                                Icon(
                                    painter = painterResource(R.drawable.ic_baseline_arrow_drop_down_24),
                                    contentDescription = null
                                )
                            }
                        },
                    )

                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false },
                        /*
                        modifier = Modifier
                            .background( Color.White, )
                            .border(BorderStroke(width = 1.dp,color = Color.Red))
                            .width(200.dp),

                         */
                    ) {
                        items.forEachIndexed { index, s ->
                            DropdownMenuItem(
                                onClick = {
                                    expanded.value = false

                                    viewModel.changeSortingField(items[index])
                                }
                            ) {
                                Text(
                                    text = s.acronym
                                )
                            }
                        }
                    }
                }
            }

            Button(
                onClick = {
                    viewModel.invertSortingDirection()
                },
                content = {
                    Icon(
                        painter = painterResource(sortOrderIcon),
                        contentDescription = null,
                    )
                }
            )
        }
    }

    // TODO: Extract to index class. SAme as StylesFragment Searchbar().
    @Composable
    fun SearchBar(){
        val searchText: String by viewModel.filterString.collectAsState()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) {
            OutlinedTextField (
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_search_24),
                        contentDescription = null,
                    )
                },
                label = {
                    Text("Filter by:")
                },
                singleLine = true,
                value = searchText,
                onValueChange = {
                    viewModel.updateFilterString(it)
                },
                modifier = Modifier
                    .weight(1.0f)
                    .padding(end = 8.dp)
            )

            Sorting()
        }
    }

    @Composable
    fun Content(){
        val recipes: List<Recipe> by viewModel.values.observeAsState(listOf())
        val isRefreshing by viewModel.isRefreshing.collectAsState()

        Column (
            modifier = Modifier
                .padding(all = 8.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                MultiToggleButton(RecipeOwnerMultiToggleEnum.values(), RecipeOwnerMultiToggleEnum.ALL) {
                    when(it){
                        RecipeOwnerMultiToggleEnum.ALL -> viewModel.toggleShowPublicRecipes(true)
                        RecipeOwnerMultiToggleEnum.PRIVATE -> viewModel.toggleShowPublicRecipes(false)
                    }
                }

                Icon(
                    painter = painterResource(R.drawable.ic_baseline_add_24),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(
                            shape = CircleShape,
                        )
                        .clickable {
                            createRecipe()
                        }
                        .background(
                            // FIXME Replace colours.
                            Color.Magenta
                        )
                        .padding(
                            vertical = 12.dp,
                            horizontal = 12.dp,
                        )
                )
            }


            SearchBar()

            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 1.dp
            )

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = {
                    viewModel.refreshList()
                },
            ) {
                LazyColumn(
                    content = {
                        items(
                            items = recipes,
                            itemContent = { it ->
                                ShortRecipeCard(it){
                                    viewRecipe(it)
                                }
                            }
                        )
                    }
                )
            }
        }
    }

    @Preview
    @Composable
    fun RecipeCardPreview(@PreviewParameter(SampleRecipeProvider::class) recipe: Recipe){
        ShortRecipeCard(recipe = recipe){
            viewRecipe(it)
        }
    }

    @Preview
    @Composable
    fun SortingPreview(){
        Sorting()
    }

    @Preview
    @Composable
    fun SearchBarPreview(){
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        ) {
            Row {
                MultiToggleButton(RecipeOwnerMultiToggleEnum.values(), RecipeOwnerMultiToggleEnum.ALL) {
                    when(it){
                        RecipeOwnerMultiToggleEnum.ALL -> viewModel.toggleShowPublicRecipes(true)
                        RecipeOwnerMultiToggleEnum.PRIVATE -> viewModel.toggleShowPublicRecipes(false)
                    }
                }

                Icon(
                    painter = painterResource(R.drawable.ic_baseline_add_24),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(
                            shape = CircleShape,
                        )
                        .clickable {

                        }
                        .background(
                            // FIXME Replace colours.
                            Color.Magenta
                        )
                        .padding(
                            vertical = 12.dp,
                            horizontal = 12.dp,
                        )

                )
            }


            SearchBar()
        }
    }
}