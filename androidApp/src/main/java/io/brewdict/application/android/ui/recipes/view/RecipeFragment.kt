package io.brewdict.application.android.ui.recipes.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import io.brewdict.application.android.R
import io.brewdict.application.android.databinding.FragmentViewRecipeBinding
import io.brewdict.application.android.ui.recipes.RecipeComponents.ShortRecipeCard
import io.brewdict.application.android.ui.recipes.RecipeComponents.StyleAccordion
import io.brewdict.application.android.ui.recipes.list.SampleRecipeProvider
import io.brewdict.application.apis.brewdict.BrewdictAPI
import io.brewdict.application.apis.brewdict.models.Fermentation
import io.brewdict.application.apis.brewdict.models.Recipe
import java.text.DecimalFormat

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        viewModel.recipe = requireArguments().getSerializable("recipe") as Recipe?

        viewModel.recipeDeleteResult.observe(viewLifecycleOwner,
            Observer { result ->
                result ?: return@Observer

                result.success?.let{
                    deleteSuccess()
                }

                result.error?.let {
                    deleteFail(it)
                }
            }
        )

        viewModel.fermentationCreateResult.observe(viewLifecycleOwner,
            Observer { result ->
                result ?: return@Observer

                result.success?.let{
                    createFermentationSuccess(it)
                }

                result.error?.let {
                    createFermentationFail(it)
                }
            }
        )
    }

    private fun deleteSuccess() {
        val msg = "Recipe deleted."
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, msg, Toast.LENGTH_LONG).show()

        view?.findNavController()?.navigate(
            R.id.action_delete_recipe_successful
        )
    }

    private fun deleteFail(@StringRes errorString: Int){
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    private fun createFermentationSuccess(fermentation: Fermentation) {
        val msg = "Fermentation created."
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, msg, Toast.LENGTH_LONG).show()

        view?.findNavController()?.navigate(
            R.id.action_create_fermentation_successful,
            bundleOf( "fermentation" to fermentation)
        )
    }

    private fun createFermentationFail(@StringRes errorString: Int){
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    private fun viewInspiration(recipe: Recipe){
        view?.findNavController()?.navigate(
            R.id.action_view_inspiration,
            bundleOf( "recipe" to recipe)
        )
    }

    private fun createRecipe(){
        view?.findNavController()?.navigate(
            R.id.action_create_recipe_from_inspiration,
            bundleOf( "inspiration" to viewModel.recipe)
        )
    }

    private fun editRecipe(){
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
                                text = "${DecimalFormat("#0.0").format(recipe.abv)}%"
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

            }

        }
    }

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    @Composable
    fun Content(){
        val recipe = viewModel.recipe!!

        Column() {
            RecipeCard(recipe)

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Button(
                    onClick = {
                        viewModel.createFermentation(recipe)
                    }
                ) {
                    Text(
                        text = "Create Fermentation From"
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                if (recipe.owner.id == BrewdictAPI.loggedInUser!!.user.id) {
                    Button(
                        onClick = {
                            editRecipe()
                        },
                        modifier = Modifier
                                .padding(end=4.dp)
                    ) {
                        Text(
                            text = "Edit"
                        )
                    }
                    Button(
                        onClick = {
                            viewModel.deleteRecipe(recipe)
                        },
                        colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                backgroundColor = Color.Red
                            ),
                        modifier = Modifier
                            .padding(start=4.dp)
                    ) {
                        Text(
                            text = "Delete"
                        )
                    }
                } else {
                    Button(
                        onClick = {
                            createRecipe()
                        }
                    ) {
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
    @Preview
    @Composable
    fun RecipeCardPreview(@PreviewParameter(SampleRecipeProvider::class) recipe: Recipe){
        Column() {
            RecipeCard(recipe = recipe)
        }
    }
}