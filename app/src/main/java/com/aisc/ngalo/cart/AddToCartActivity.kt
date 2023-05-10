package com.aisc.ngalo.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.aisc.ngalo.databinding.ActivityAddtocartBinding
import com.google.firebase.auth.FirebaseAuth

class AddToCartActivity : DialogFragment() {
    private lateinit var binding: ActivityAddtocartBinding
    private lateinit var addToCartViewModel: AddToCartViewModel
    private var count: Int? = null
    private var mUid: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityAddtocartBinding.inflate(inflater, container, false)

        addToCartViewModel = ViewModelProvider(this)[AddToCartViewModel::class.java]
        mUid = FirebaseAuth.getInstance().currentUser!!.uid

        addOne()
        subOne()
        totalCount()

        binding.addTocart.setOnClickListener {
            val bundle: Bundle = requireArguments()
            val id = bundle.getString("id")
            val name = bundle.getString("name")
            val price = bundle.getString("price")
            val imageUrl = bundle.getString("imageUrl")
            val position = bundle.getInt("position")
            val quantity = bundle.getInt("quantity")
            addtoCart(id, position, name, price!!.toInt(), imageUrl, quantity)
        }
        //get bundle from the activity and set items to the dialog
        return binding.root

    }


    private fun addtoCart(
        id: String?,
        position: Int?,
        name: String?,
        price: Int?,
        imageUrl: String?,
        quantity: Int
    ) {
        count = binding.howmany.text.toString().toInt()


        if (count == 0) {
            Toast.makeText(requireContext(), "can't be Zero, add item", Toast.LENGTH_SHORT).show()
        } else {
            //Toast.makeText(requireContext(), count!!, Toast.LENGTH_SHORT).show()
            count = quantity
            addToCartViewModel.addToCart(
                id!!,
                quantity,
                name!!,
                price!!,
                imageUrl!!,
                position!!,
            )
        }

//        val cartCardView = layoutInflater.inflate(R.layout.activity_bar__items, null, false)
//        cartCardView.cartCheckOut.visibility = View.VISIBLE

    }

    private fun subOne() {
        binding.subone.setOnClickListener {
            addToCartViewModel.sub()
            val count = addToCartViewModel.num
            binding.howmany.text = count.toString()

        }
    }

    private fun addOne() {
        binding.addone.setOnClickListener {
            addToCartViewModel.add()
            val count = addToCartViewModel.num
            binding.howmany.text = count.toString()
        }
    }

    private fun totalCount() {
        addToCartViewModel.countTotal()
    }


}

