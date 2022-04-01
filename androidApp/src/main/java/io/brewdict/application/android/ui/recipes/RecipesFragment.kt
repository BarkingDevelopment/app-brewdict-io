package io.brewdict.application.android.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
        viewModel.refresh()

        _binding = DataBindingUtil.inflate<FragmentRecipesBinding>(
            inflater,
            R.layout.fragment_recipes,
            container,
            false
        ).apply {
            composeView.setContent {
                MaterialTheme {
                    Layout()
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
    fun Layout(){
        val isRefreshing by viewModel.isRefreshing.collectAsState()
        Column() {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = {
                    viewModel.refresh()
                },
            ) {
                LazyColumn(
                    content = {
                        items(
                            items = viewModel.recipes,
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
}