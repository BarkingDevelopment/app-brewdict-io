package io.brewdict.application.android.ui.fermentations.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import io.brewdict.application.android.R
import io.brewdict.application.android.databinding.FragmentViewFermentationBinding
import io.brewdict.application.android.ui.recipes.RecipeComponents.ShortRecipeCard
import io.brewdict.application.apis.brewdict.models.Fermentation
import io.brewdict.application.apis.brewdict.models.Reading
import io.brewdict.application.apis.brewdict.models.Recipe
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


class FermentationViewFragment : Fragment() {
    private var _binding: FragmentViewFermentationBinding? = null
    private lateinit var viewModel: FermentationViewViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<FragmentViewFermentationBinding>(
            inflater,
            R.layout.fragment_view_fermentation,
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

        viewModel = ViewModelProvider(this)[FermentationViewViewModel::class.java]
        viewModel.getFermentation(requireArguments().getSerializable("fermentation") as Fermentation)

        viewModel.fermentationStartResult.observe(viewLifecycleOwner,
            Observer { result ->
                result ?: return@Observer

                result.success?.let{
                    startSuccess()
                }

                result.error?.let {
                    startFail(it)
                }
            })

        viewModel.fermentationCompleteResult.observe(viewLifecycleOwner,
            Observer { result ->
                result ?: return@Observer

                result.success?.let{
                    completeSuccess()
                }

                result.error?.let {
                    completeFail(it)
                }
            })

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

    private fun startSuccess() {
        val msg = "Fermentation started."
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, msg, Toast.LENGTH_LONG).show()

        recreate()
    }

    private fun startFail(@StringRes errorString: Int){
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    private fun completeSuccess() {
        val msg = "Fermentation completed."
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, msg, Toast.LENGTH_LONG).show()

        recreate()
    }

    private fun completeFail(@StringRes errorString: Int){
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

    private fun recreate(){
        view?.findNavController()?.navigate(
            R.id.action_refresh_fermentation,
            bundleOf( "fermentation" to viewModel.fermentation)
        )
    }

    private fun viewReadings(){
        view?.findNavController()?.navigate(
            R.id.action_view_readings,
            bundleOf( "fermentation" to viewModel.fermentation)
        )
    }

    fun viewRecipe(recipe: Recipe){
        view?.findNavController()?.navigate(
            R.id.action_view_recipe,
            bundleOf( "recipe" to recipe)
        )
    }

    private fun fieldToPoints(field: Fermentation.() -> List<Reading>?, label: String): LineDataSet {
        return if (viewModel.fermentation.field().isNullOrEmpty()) {
            LineDataSet(listOf<Entry>(), label)
        } else {
            val readings: List<Reading> = viewModel.fermentation.field()!!
            val fermStart: LocalDateTime = readings.minOf { it.recordedDatetime }

            val entries: List<Entry> = readings.map {
                Entry(
                    ChronoUnit.HOURS.between(it.recordedDatetime, fermStart).toFloat(),
                    it.value
                )
            }.toList()

            LineDataSet(entries, label)
        }
    }

    @Composable
    private fun readingsChart(){
        AndroidView(
            factory = { ctx ->
                LineChart(ctx).apply {
                    val sg = fieldToPoints({ specificGravities }, "Specific Gravity")
                    sg.color = R.color.colorAccent
                    val temp = fieldToPoints({ temperatures }, "Temperature")
                    temp.color = R.color.colorPrimary

                    val dataSets: List<ILineDataSet> = arrayListOf(sg, temp)
                    this.data = LineData(dataSets)

                    val x = this.xAxis
                    x.axisMinimum = 0f
                    x.setDrawGridLines(true)
                    x.position = XAxis.XAxisPosition.BOTTOM

                    val yleft = this.axisLeft
                    yleft.axisMinimum = 1f
                    yleft.axisMaximum = sg.yMin
                    yleft.spaceTop = 5f
                    yleft.setDrawGridLines(true)

                    val yright = this.axisRight
                    yright.isEnabled = false
                }
            },
            modifier = Modifier
                .fillMaxSize()
        )
    }

    @Composable
    fun Content() {
        val fermentation = viewModel.fermentation

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
        ){
            ShortRecipeCard(viewModel.fermentation.recipe){
                viewRecipe(it)
            }

            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 1.dp
            )

            if (fermentation.isComplete) {
                CompletedBody()
            }else if (fermentation.isStarted){
                StartedBody()
            } else {
                NewBody()
            }
        }
    }

    @Composable
    private fun NewBody() {
        val og = remember { mutableStateOf(TextFieldValue("")) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                trailingIcon = {
                    Text (
                        text = "SG"
                    )
                },
                label = {
                    Text (
                        text = "OG"
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = og.value,
                onValueChange = {
                    og.value = it
                },
                modifier = Modifier
                    .weight(1.0f)
                    .padding(end = 8.dp)
            )

            Button(
                onClick = {
                    viewModel.startFermentation(og.value.text.toFloatOrNull())
                }
            ) {
                Text (
                    text = "Start Fermentation"
                )
            }
        }
    }

    @Composable
    private fun StartedBody(){
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Predicted Results"
                    )
                }

                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth(),
                    thickness = 1.dp
                )

                Row {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            //Predicted FG
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Final Gravity"
                                )
                                Text(
                                    text = "${viewModel.fermentation.fg}"
                                )
                            }

                            //Predicted ABV
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "ABV"
                                )
                                Text(
                                    text = "${viewModel.fermentation.abv}%"
                                )
                            }
                        }
                        //Predicted Time
                        Text(
                            text = "Fermentation Time"
                        )
                        Text(
                            //text = ""
                            text = "${viewModel.predictDuration()} hours"
                        )
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    viewReadings()
                },
            ) {
                Text (
                    text = "View Readings"
                )
            }

            Button(
                onClick = {
                    viewModel.completeFermentation()
                },
            ) {
                Text (
                    text = "Complete Fermentation"
                )
            }
        }


        readingsChart()
    }

    @Composable
    private fun CompletedBody(){
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Results"
                    )
                }

                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth(),
                    thickness = 1.dp
                )

                Row {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            //Predicted FG
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Final Gravity"
                                )
                                Text(
                                    text = "${viewModel.fermentation.fg}"
                                )
                            }

                            //Predicted ABV
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "ABV"
                                )
                                Text(
                                    text = "${viewModel.fermentation.abv}%"
                                )
                            }
                        }
                        //Predicted Time
                        Text(
                            text = "Fermentation Time"
                        )
                        Text(
                            text = "${viewModel.fermentation.fermenetationTime} Days"
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                viewModel.createFermentation(viewModel.fermentation.recipe)
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text (
                text = "Ferment Again"
            )
        }

        readingsChart()
    }

    @Preview
    @Composable
    fun NewBodyPreview() {
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
        ) {
            NewBody()
        }
    }

    @Preview
    @Composable
    fun StartedBodyPreview() {
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
        ) {
            StartedBody()
        }
    }

    @Preview
    @Composable
    fun CompletedBodyPreview() {
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
        ){
            CompletedBody()
        }
    }
}