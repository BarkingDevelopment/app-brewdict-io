package io.brewdict.application.android.ui.recipes.listing

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.brewdict.application.android.R
import io.brewdict.application.android.databinding.FragmentRecipesBinding
import io.brewdict.application.apis.brewdict.models.Recipe

class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private lateinit var viewModel: RecipesViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(RecipesViewModel::class.java)
    }

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
                    RecipesLayout()
                }
            }
        }
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Composable
    fun MultiToggleButton(options: List<String>, default: String, onSelectionChange: (String) -> Unit) {
        var selectedOption by remember { mutableStateOf(default) }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            options.forEach { text ->
                Column(
                    modifier = Modifier
                        .padding(
                            end = 8.dp,
                        ),
                ) {
                    Text(
                        text = text,
                        color = Color.White,
                        modifier = Modifier
                            .clip(
                                shape = RoundedCornerShape(
                                    size = 12.dp,
                                ),
                            )
                            .clickable {
                                selectedOption = text
                                onSelectionChange(text)
                            }
                            .background(
                                // FIXME Replace colours.
                                if (text == selectedOption) {
                                    Color.Magenta
                                } else {
                                    Color.LightGray
                                }
                            )
                            .padding(
                                vertical = 16.dp,
                                horizontal = 16.dp,
                            ),
                    )
                }
            }
        }
    }

    @Composable
    fun Sorting(){
        val items = RecipeSortingField.values()
        val expanded = remember { mutableStateOf(false) }
        val selectedIndex = remember { mutableStateOf(0) }

        val ascending = remember { mutableStateOf(true) }
        val sortOrderIcon = remember { mutableStateOf(R.drawable.ic_baseline_arrow_upward_24) }

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
                                    text = items[selectedIndex.value].acronym
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
                                    selectedIndex.value = index
                                    expanded.value = false

                                    viewModel.sortingField = items[index]

                                    viewModel.updateRecipes()
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
                    ascending.value = !ascending.value
                    sortOrderIcon.value = if(ascending.value) R.drawable.ic_baseline_arrow_upward_24
                        else R.drawable.ic_baseline_arrow_downward_24

                    viewModel.isSortingAscending= ascending.value

                    viewModel.updateRecipes()
                },
                content = {
                    Icon(
                        painter = painterResource(sortOrderIcon.value),
                        contentDescription = null,
                    )
                }
            )
        }
    }

    @Composable
    fun SearchBar(){
        val searchText = remember { mutableStateOf(TextFieldValue()) }

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
                value = searchText.value,
                onValueChange = {
                    searchText.value = it

                    //FIXME Filtering does not respect sorting.
                    viewModel.filterString = searchText.value.text
                    viewModel.updateRecipes()
                },
                modifier = Modifier
                    .weight(1.0f)
                    .padding(end = 8.dp)
            )

            Sorting()
        }
    }

    @Composable
    fun RecipeCard(recipe: Recipe){
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Column() {
                Text(
                    text = recipe.name,
                    fontWeight = FontWeight.Bold
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(recipe.style.name, style = MaterialTheme.typography.body2)
                }
            }

            Column(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
            ) {
                Row {
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .padding(end = 4.dp)
                    ) {
                        Text(
                            text = "${recipe.abv}%"
                        )
                        Text(
                            text = "IBU: ${recipe.ibu}"
                        )
                    }
                    Column() {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(8.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(
                                    Color(recipe.colour())
                                )
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun RecipesLayout(){
        val recipes: List<Recipe> by viewModel.recipes.observeAsState(listOf())
        val isRefreshing by viewModel.isRefreshing.collectAsState()

        Column (
            modifier = Modifier
                .padding(all = 8.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                MultiToggleButton(listOf("Public Recipes", "Your Recipes"), "Public Recipes") {
                    when(it){
                        "Public Recipes" -> viewModel.showPublicRecipes = true
                        "Your Recipes" -> viewModel.showPublicRecipes = false
                        else -> throw IllegalArgumentException("Ruh Roh Raggy. Wtf did you do.")
                    }

                    viewModel.updateRecipes()
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

            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 1.dp
            )

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = {
                    //FIXME: Refresh doesn't automatically sort by current sorting reqs.
                    viewModel.refreshRecipes()
                },
            ) {
                LazyColumn(
                    content = {
                        items(
                            items = recipes,
                            itemContent = {
                                RecipeCard(it)
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
        RecipeCard(recipe = recipe)
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
                MultiToggleButton(listOf("Public Recipes", "Your Recipes"), "Public Recipes") {
                    when (it) {
                        "Public Recipes" -> viewModel.showPublicRecipes = true
                        "Your Recipes" -> viewModel.showPublicRecipes = false
                        else -> throw IllegalArgumentException("Ruh Roh Raggy. Wtf did you do.")
                    }

                    viewModel.updateRecipes()
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