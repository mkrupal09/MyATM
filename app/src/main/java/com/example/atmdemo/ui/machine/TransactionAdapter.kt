package com.example.atmdemo.ui.machine

import com.example.atmdemo.R
import com.example.atmdemo.data.Transaction
import com.example.atmdemo.databinding.ItemTransactionBinding
import easyadapter.dc.com.library.EasyAdapter

class TransactionAdapter :
    EasyAdapter<Transaction, ItemTransactionBinding>(R.layout.item_transaction) {
    override fun onBind(binding: ItemTransactionBinding, model: Transaction) {
        binding.tvAmount.text = "Rs. ${model.amount}"

        binding.apply {
            tvHundred.text = model.hundred.toString()
            tvTwoHundred.text = model.twoHundreds.toString()
            tvFiveHundred.text = model.fiveHundreds.toString()
            tvTwoThousand.text = model.twoThousands.toString()
        }
    }

}