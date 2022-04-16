package io.brewdict.application.android.ui.recipes.styles

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.brewdict.application.android.R
import io.brewdict.application.android.databinding.FragmentStylesBinding
import io.brewdict.application.android.ui.recipes.list.RecipeOwnerMultiToggleEnum
import io.brewdict.application.android.ui.recipes.list.RecipeSortingFieldEnum
import io.brewdict.application.android.utils.SharedComponents
import io.brewdict.application.android.utils.SharedComponents.MultiToggleButton
import io.brewdict.application.apis.brewdict.models.Recipe
import io.brewdict.application.apis.brewdict.models.Style
import kotlin.math.ceil
import kotlin.math.floor

class StylesFragment : Fragment() {
    private var _binding: FragmentStylesBinding? = null
    private lateinit var viewModel: StylesViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @OptIn(
        ExperimentalAnimationApi::class,
        ExperimentalMaterialApi::class
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<FragmentStylesBinding>(
            inflater,
            R.layout.fragment_styles,
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

        viewModel = ViewModelProvider(this).get(StylesViewModel::class.java)
    }

    private fun selectStyle(style: Style) {
        view?.findNavController()?.navigate(
            R.id.action_style_selected,
            bundleOf( "style" to style)
        )
    }

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    @Composable
    fun StyleCard(
        style: Style,
        selectStyle: (style: Style) -> Unit
    ){
        SharedComponents.Accordion(
            header = {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = "${style.category.number}${style.letter}"
                    )
                }

                Text(
                    text = style.name,
                    fontWeight = FontWeight.Bold
                )
            },
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    SharedComponents.Range("Original Gravity", style.minOG, style.maxOG)
                    SharedComponents.Range("Final Gravity", style.minFG, style.maxFG)
                    SharedComponents.Range("Bitterness (IBU)", style.minIBU, style.maxIBU)
                    Row {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Colour (SRM)",
                                fontWeight = FontWeight.Bold
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier
                                    .padding(bottom = 4.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(text = floor(style.minSRM.toDouble()).toInt().toString())
                                Text(text = "-")
                                Text(text = ceil(style.maxSRM.toDouble()).toInt().toString())
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                Recipe.srmToColour(floor(style.minSRM.toDouble()).toInt()),
                                                Recipe.srmToColour(ceil(style.maxSRM.toDouble()).toInt())
                                            )
                                        )
                                    )
                            )
                        }
                    }

                    Button(
                        onClick = {
                            selectStyle(style)
                        }
                    ) {
                        Text(
                            text = "Select Style"
                        )
                    }
                }
            }
        )
    }

    // TODO: Extract to index class. SAme as RecipesFragment Sorting().
    @Composable
    fun Sorting(){
        val expanded = remember { mutableStateOf(false) }

        val items = viewModel.sortingFields

        val selectedField: StyleSortingFieldEnum by viewModel.currentSortingField.collectAsState()
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


    // TODO: Extract to index class. SAme as RecipesFragment Searchbar().
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

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    @Composable
    fun Content(){
        val styles: List<Style> by viewModel.values.observeAsState(listOf())
        val isRefreshing by viewModel.isRefreshing.collectAsState()

        val selectedJournal by viewModel.journal.collectAsState()

        Column(
            modifier = Modifier
                .padding(all = 8.dp)
        ) {
            MultiToggleButton(StyleJournalEnum.values(), selectedJournal) {
                viewModel.changeStyleJournal(it)
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
                            items = styles,
                            itemContent = { it ->
                                StyleCard(it){
                                    selectStyle(it)
                                }
                            }
                        )
                    }
                )
            }
        }
    }
}