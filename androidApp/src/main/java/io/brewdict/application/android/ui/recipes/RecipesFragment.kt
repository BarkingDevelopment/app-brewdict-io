package io.brewdict.application.android.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(RecipesViewModel::class.java)

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
    fun Sorting(){
        val items = listOf("ABC", "STYLE", "ABV", "IBU", "SRM")
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
                                    text = items[selectedIndex.value]
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

                                    when(index){
                                        0 -> viewModel.sortUsing({name}, ascending.value)
                                        1 -> viewModel.sortUsing({style.name}, ascending.value)
                                        2 -> viewModel.sortUsing({abv}, ascending.value)
                                        3 -> viewModel.sortUsing({ibu}, ascending.value)
                                        4 -> viewModel.sortUsing({srm}, ascending.value)
                                    }
                                }
                            ) {
                                Text(
                                    text = s
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

                    when(selectedIndex.value){
                        0 -> viewModel.sortUsing({name}, ascending.value)
                        1 -> viewModel.sortUsing({style.name}, ascending.value)
                        2 -> viewModel.sortUsing({abv}, ascending.value)
                        3 -> viewModel.sortUsing({ibu}, ascending.value)
                        4 -> viewModel.sortUsing({srm}, ascending.value)
                    }
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
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) {
            OutlinedTextField (
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_search_24),
                        contentDescription = null,// decorative element
                    )
                },
                label = {
                    Text("Filter by:")
                },
                singleLine = true,
                value = searchText.value,
                onValueChange = { searchText.value = it },
                modifier = Modifier
                    .width(200.dp)
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

        Column {
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
        SearchBar()
    }
}