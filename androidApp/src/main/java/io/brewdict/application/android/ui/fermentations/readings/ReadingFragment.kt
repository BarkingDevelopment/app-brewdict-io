package io.brewdict.application.android.ui.fermentations.readings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import io.brewdict.application.android.R
import io.brewdict.application.android.databinding.FragmentReadingsBinding
import io.brewdict.application.apis.brewdict.models.Fermentation
import io.brewdict.application.apis.brewdict.models.Reading
import io.brewdict.application.apis.brewdict.models.ReadingTypeEnum
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ReadingFragment : Fragment() {
    private var _binding: FragmentReadingsBinding? = null

    private lateinit var viewModel: ReadingViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var refreshTimes by mutableStateOf(1)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<FragmentReadingsBinding>(
            inflater,
            R.layout.fragment_readings,
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

        viewModelFactory = ViewModelFactory((requireArguments().getSerializable("fermentation") as Fermentation))
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ReadingViewModel::class.java)

        viewModel.readingCreateResult.observe(viewLifecycleOwner,
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
        val msg = "Reading saved."
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, msg, Toast.LENGTH_LONG).show()

        viewModel.refreshList()
        refreshTimes ++
    }

    private fun fail(@StringRes errorString: Int){
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    @Composable
    private fun AddReading(){
        var expanded by remember { mutableStateOf(false) }
        val items = ReadingTypeEnum.values()
        var selectedType by remember { mutableStateOf(ReadingTypeEnum.SG) }

        val reading = remember { mutableStateOf(TextFieldValue("")) }
        val unit = remember { mutableStateOf(ReadingTypeEnum.SG.unit) }
        val readingDate= remember { mutableStateOf<LocalDate>(LocalDate.now()) }
        val readingTime = remember { mutableStateOf<LocalTime>(LocalTime.now().withSecond(0).withNano(0)) }

        val dateDialogState = rememberMaterialDialogState()
        val timeDialogState = rememberMaterialDialogState()

        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton("Ok")
                negativeButton("Cancel")
            }
        ) {
            datepicker(
                initialDate = readingDate.value
            ) { date ->
                readingDate.value = date
            }
        }

        MaterialDialog(
            dialogState = timeDialogState,
            buttons = {
                positiveButton("Ok")
                negativeButton("Cancel")
            }
        ) {
            timepicker(
                initialTime = readingTime.value
            ) { time ->
                readingTime.value = time
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TextField(
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_edit_24),
                        contentDescription = null,// decorative element
                    )
                },
                label = { Text("Enter Reading Value") },
                placeholder = {
                    Text(
                        text = "Reading Value,",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.LightGray
                        )
                    )
                },
                trailingIcon = {
                    Box {
                        Column {
                            Button(
                                onClick = {
                                    expanded = true
                                },
                                content = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                    ) {
                                        Text(
                                            text = selectedType.unit
                                        )

                                        Icon(
                                            painter = painterResource(R.drawable.ic_baseline_arrow_drop_down_24),
                                            contentDescription = null
                                        )
                                    }
                                },
                            )

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                            ) {
                                items.forEachIndexed { index, s ->
                                    DropdownMenuItem(
                                        onClick = {
                                            expanded = false

                                            selectedType = items[index]
                                            unit.value = selectedType.unit
                                        }
                                    ) {
                                        Text(
                                            text = s.unit
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                value = reading.value,
                onValueChange = { reading.value = it },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_calendar_month_24),
                            contentDescription = null,// decorative element
                        )
                    },
                    label = { Text("Reading Date") },
                    placeholder = {
                        Text(
                            text = "Date",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color.LightGray
                            )
                        )
                    },
                    singleLine = true,
                    value = readingDate.value.toString(),
                    enabled = false,
                    onValueChange = { },
                    modifier = Modifier
                        .weight(0.5f)
                        .clickable {
                            dateDialogState.show()
                        }
                )

                TextField(
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_access_time_24),
                            contentDescription = null,// decorative element
                        )
                    },
                    label = { Text("Reading Time") },
                    placeholder = {
                        Text(
                            text = "Time",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color.LightGray
                            )
                        )
                    },
                    singleLine = true,
                    value = readingTime.value.toString(),
                    enabled = false,
                    onValueChange = { },
                    modifier = Modifier
                        .weight(0.5f)
                        .clickable {
                            timeDialogState.show()
                        }
                )
            }

            Button(
                onClick = {
                    viewModel.createReading(selectedType, reading.value.text.toFloatOrNull(), LocalDateTime.of(readingDate.value, readingTime.value))
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_baseline_add_24),
                        contentDescription = null
                    )

                    Text(
                        text = "Add"
                    )
                }
            }
        }
    }

    @Composable
    private fun ReadingCard(reading: Reading){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = reading.type.title,
                    fontWeight = FontWeight.Bold
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = reading.recordedDatetime.toString(),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
            Column{
                Text(
                    text = "${reading.value} ${reading.units}"
                )
            }
        }
    }

    @Composable
    private fun Content() {
        val readings: List<Reading>? by viewModel.values.observeAsState(listOf())
        val isRefreshing by viewModel.isRefreshing.collectAsState()

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
        ) {

            Row {
                if(refreshTimes > 0) AddReading()
            }

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
                            items = readings?.filter { r -> r.type != ReadingTypeEnum.NONE
                            }?.sortedBy { r -> r.recordedDatetime } ?: listOf(),
                            itemContent = { reading ->
                                ReadingCard(reading = reading)
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxHeight()
                )
            }
        }
    }

    @Preview
    @Composable
    fun AddReadingPreview(){
        AddReading()
    }
}