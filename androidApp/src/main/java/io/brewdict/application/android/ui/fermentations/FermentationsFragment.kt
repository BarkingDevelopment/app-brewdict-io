package io.brewdict.application.android.ui.fermentations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.brewdict.application.android.R
import io.brewdict.application.android.databinding.FragmentFermentationsBinding

class FermentationsFragment : Fragment() {

    private var _binding: FragmentFermentationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(FermentationViewModel::class.java)

        _binding = DataBindingUtil.inflate<FragmentFermentationsBinding>(
            inflater,
            R.layout.fragment_fermentations,
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
    fun Layout(){

    }
}