package io.brewdict.application.android.ui.recipes.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import io.brewdict.application.android.R
import io.brewdict.application.android.databinding.FragmentAddEditRecipeBinding
import io.brewdict.application.apis.brewdict.models.Recipe
import io.brewdict.application.apis.brewdict.models.Style
import kotlin.math.floor

class RecipeFragment : Fragment() {
    private var _isCreate: Boolean = true

    private var _binding: FragmentAddEditRecipeBinding? = null
    private lateinit var viewModel: RecipeViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<FragmentAddEditRecipeBinding>(
            inflater,
            R.layout.fragment_add_edit_recipe,
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

        if (arguments != null){
            val args = requireArguments()

            if (args.getSerializable("recipe") != null){ // Is Edit
                _isCreate = false
                viewModel.setEditedRecipe(args.getSerializable("recipe") as Recipe)

            }else if (args.getSerializable("inspiration") != null){ // Is Create from parent
                _isCreate = true
                viewModel.inspiration = args.getSerializable("inspiration") as Recipe

            } else if (args.getSerializable("style") != null){ // Set style
                viewModel.recipeStyle.value = args.getSerializable("style") as Style?

            }else throw IllegalArgumentException("Unrecognised argument passed.")
        }else{ // Is Create New
            _isCreate = true
        }

        viewModel.recipeSaveResult.observe(viewLifecycleOwner,
            Observer { result ->
                result ?: return@Observer

                result.success?.let{
                    success()
                }

                result.error?.let {
                    fail(it)
                }
            })
    }

    private fun success() {
        val msg = "Recipe saved."
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, msg, Toast.LENGTH_LONG).show()

        view?.findNavController()?.navigate(
            R.id.action_save_recipe_changes
        )
    }

    private fun fail(@StringRes errorString: Int){
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    private fun selectStyle(){
        view?.findNavController()?.navigate(
            R.id.action_select_style
        )
    }

    @Composable
    fun Content(){
        val name by viewModel.recipeName.observeAsState()
        val desc by viewModel.recipeDesc.observeAsState()
        val style by viewModel.recipeStyle.observeAsState()
        val og by viewModel.recipeOG.observeAsState()
        val fg by viewModel.recipeFG.observeAsState()
        val ibu by viewModel.recipeIbu.observeAsState()
        val srm by viewModel.recipeSrm.observeAsState()

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp)
        ){
            if (viewModel.inspiration != null){
                //RecipeFragment.RecipeCard
            }

            // Name and Description
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                .fillMaxWidth()
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    OutlinedTextField(
                        label = {
                            Text(
                                text = "Name"
                            )
                        },
                        placeholder = {
                            Text(
                                text = "Enter a Recipe Name",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    color = Color.LightGray
                                )
                            )
                        },
                        singleLine = true,
                        value = name.toString(),
                        onValueChange = {
                            viewModel.recipeName.value = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    OutlinedTextField(
                        label = {
                            Text(
                                text = "Description"
                            )
                        },
                        placeholder = {
                            Text(
                                text = "Enter a Recipe Description",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    color = Color.LightGray
                                )
                            )
                        },
                        value = desc.toString(),
                        onValueChange = {
                            viewModel.recipeDesc.value = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            // Style
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                OutlinedTextField(
                    label = {
                        Text(
                            text = "Style"
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Select a Style",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color.LightGray
                            )
                        )
                    },
                    enabled = false,
                    value = if (style != null) style!!.name else "",
                    onValueChange = { },
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .weight(0.5f)
                        .clickable(
                            onClick = {
                                selectStyle()
                            }
                        )
                )

                OutlinedButton(
                    onClick = {
                        selectStyle()
                    },
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .weight(0.5f)
                ) {
                    Text(
                        text = "Select Style"
                    )
                }
            }

            // Attributes
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    // Gravity
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            label = {
                                Text("Original Gravity")
                            },
                            placeholder = {
                                Text(
                                    text = "Enter an OG",
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        color = Color.LightGray
                                    )
                                )
                            },
                            trailingIcon = {
                                Text(
                                    text = "SG"
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            value = og.toString(),
                            onValueChange = {
                                viewModel.recipeOG.value = it.toFloat()
                            },
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .weight(0.5f)
                        )

                        OutlinedTextField(
                            label = {
                                Text("Final Gravity")
                            },
                            placeholder = {
                                Text(
                                    text = "Enter a FG",
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        color = Color.LightGray
                                    )
                                )
                            },
                            trailingIcon = {
                                Text(
                                   text = "SG"
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            value = fg.toString(),
                            onValueChange = {
                                viewModel.recipeFG.value  = it.toFloat()
                            },
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .weight(0.5f)
                        )
                    }

                    Row(){
                        OutlinedTextField(
                            label = {
                                Text("Bitterness")
                            },
                            placeholder = {
                                Text(
                                    text = "Enter an IBU Value",
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        color = Color.LightGray
                                    )
                                )
                            },
                            trailingIcon = {
                                Text(
                                    text = "IBU"
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            value = ibu.toString(),
                            onValueChange = {
                                val int : Int? = it.toIntOrNull()
                                viewModel.recipeIbu.value  = int ?: 0
                            },
                            modifier = Modifier
                                .weight(1f)
                        )
                    }

                    // SRM
                    Row(){
                        Column() {
                            OutlinedTextField(
                                enabled = false,
                                label = {
                                    Text(
                                        text = "Colour"
                                    )
                                },
                                trailingIcon = {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight(1.0f)
                                            .fillMaxWidth(0.2f)
                                            .background(
                                                Recipe.srmToColour(
                                                    floor(srm!!.toDouble()).toInt()
                                                )
                                            )
                                    )
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                value = floor(srm!!.toDouble()).toString(),
                                onValueChange = {
                                    //srm = it.toInt()
                                },
                                modifier = Modifier
                                    .height(IntrinsicSize.Min)
                                    .fillMaxWidth()
                                    .padding(end = 8.dp)
                            )

                            // TODO Bug with importing srm not being editable.
                            Slider(
                                valueRange = 1f..40f,
                                steps = 40,
                                value = srm!!.toFloat(),
                                onValueChange = {
                                    viewModel.recipeSrm.value = it.toInt()
                                }
                            )
                        }
                    }
                }
            }

            Button(
                onClick = {
                    viewModel.save()
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = if (_isCreate) "Create" else "Update"
                )
            }

        }
    }

    @Preview
    @Composable
    fun ContentPreview(){
        Content()
    }
}