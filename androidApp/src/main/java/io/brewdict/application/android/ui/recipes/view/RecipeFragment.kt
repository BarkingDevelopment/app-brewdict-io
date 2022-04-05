package io.brewdict.application.android.ui.recipes.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Range
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import io.brewdict.application.android.R
import io.brewdict.application.android.databinding.FragmentViewRecipeBinding
import io.brewdict.application.android.ui.recipes.RecipeComponents
import io.brewdict.application.android.ui.recipes.RecipeComponents.ShortRecipeCard
import io.brewdict.application.android.ui.recipes.RecipeComponents.StyleAccordion
import io.brewdict.application.android.ui.recipes.list.SampleRecipeProvider
import io.brewdict.application.apis.brewdict.BrewdictAPI
import io.brewdict.application.apis.brewdict.models.Recipe
import io.brewdict.application.apis.brewdict.models.Style
import kotlin.math.ceil
import kotlin.math.floor

class RecipeFragment : Fragment() {

    private var _binding: FragmentViewRecipeBinding? = null
    private lateinit var viewModel: RecipeViewModel

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
        viewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        viewModel.recipe = requireArguments().getSerializable("recipe") as Recipe?

        _binding = DataBindingUtil.inflate<FragmentViewRecipeBinding>(
            inflater,
            R.layout.fragment_view_recipe,
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

    fun viewInspiration(recipe: Recipe){
        view?.findNavController()?.navigate(
            R.id.action_view_inspiration,
            bundleOf( "recipe" to recipe)
        )
    }

    fun createRecipe(){
        view?.findNavController()?.navigate(
            R.id.action_create_recipe_from_inspiration,
            bundleOf( "inspiration" to viewModel.recipe)
        )
    }

    fun editRecipe(){
        view?.findNavController()?.navigate(
            R.id.action_edit_recipe,
            bundleOf( "recipe" to viewModel.recipe)
        )
    }

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    @Composable
    fun RecipeCard(recipe: Recipe){
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
        ){
            // Intro?
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column() {
                    Text(
                        text = recipe.name,
                        fontWeight = FontWeight.Bold
                    )
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
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(8.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(
                                        recipe.colour()
                                    )
                            )
                        }
                    }
                }
            }

            // Description
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Description",
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = recipe.description
                    )
                }
            }

            // Style
            StyleAccordion(style = recipe.style)

            // Inspiration
            Row{
                Column {
                    recipe.inspiration?.let { it ->
                        Text(
                            text = "Inspiration",
                            fontWeight = FontWeight.SemiBold
                        )

                        ShortRecipeCard(recipe = it){
                            viewInspiration(it)
                        }
                    }
                }
            }

            Row{
                if(recipe.owner.id == BrewdictAPI.loggedInUser!!.user.id){
                    Button(
                        onClick = {
                            editRecipe()
                        }
                    ){
                        Text(
                            text = "Edit"
                        )
                    }
                } else {
                    Button(
                        onClick = {
                            createRecipe()
                        }
                    ){
                        Text(
                            text = "Create Recipe From"
                        )
                    }
                }
            }

        }
    }

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    @Composable
    fun Content(){
        RecipeCard(viewModel.recipe!!)
    }

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    @Preview
    @Composable
    fun RecipeCardPreview(@PreviewParameter(SampleRecipeProvider::class) recipe: Recipe){
        Column() {
            RecipeCard(recipe = recipe)
        }
    }
}