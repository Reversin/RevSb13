package com.example.revsb_11.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import com.example.revsb_11.R
import com.example.revsb_11.contracts.SecondFragmentContract
import com.example.revsb_11.databinding.FragmentSecondBinding
import com.example.revsb_11.presenters.FirstFragmentPresenter
import com.example.revsb_11.presenters.SecondFragmentPresenter

class SecondFragment : Fragment(), SecondFragmentContract.View {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var secondPresenter: SecondFragmentContract.Presenter
    private val args: SecondFragmentArgs by navArgs()

    

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPresenters()
        secondPresenter.secondFragmentInitialized(args)
        view.findViewById<ImageButton>(R.id.backArrow)?.setOnClickListener {
            findNavController().popBackStack()
        }

    }
    private fun initPresenters() {
        secondPresenter = SecondFragmentPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        requireActivity().title = activity?.getString(R.string.sTitle_name)
    }
    
    override fun setText(dataFragmentArgument: SecondFragmentArgs) {
        val filePath = dataFragmentArgument.secondFragmentArgument
        binding.editFileTextView.text = filePath
    }
}