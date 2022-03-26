package com.example.atmdemo.ui.machine


import android.annotation.SuppressLint
import android.os.Bundle

import com.example.atmdemo.databinding.ActivityHomeBinding
import com.example.atmdemo.ui.core.BaseVMBindingActivity
import com.example.atmdemo.util.extensions.addDividerDecoration
import com.example.atmdemo.util.extensions.clearText
import com.example.atmdemo.util.extensions.layout
import com.example.atmdemo.util.extensions.setVisible

class MainActivity :
    BaseVMBindingActivity<ActivityHomeBinding, MainViewModel>(MainViewModel::class.java) {

    private var balanceAdapter = TransactionAdapter()
    private var lastTransactionAdapter = TransactionAdapter()
    private var yourTransactionAdapter = TransactionAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindingView(layout.activity_home)

        initViews()
        observeViewModel()

        viewModel.fetchTransactions()
        viewModel.fetchData()
    }

    private fun initViews() {
        binding.rvATMBalance.adapter = balanceAdapter
        binding.rvLastTransactions.adapter = lastTransactionAdapter
        binding.rvYourTransactions.adapter = yourTransactionAdapter

        binding.rvYourTransactions.addDividerDecoration()
        binding.rvLastTransactions.addDividerDecoration()

        binding.btnWithdraw.setOnClickListener {
            viewModel.withdrawMoney(binding.etAmount.text.toString())
            binding.etAmount.clearText()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeViewModel() {
        viewModel.fetchTransactions().observe(this) {
            yourTransactionAdapter.clear(true)
            yourTransactionAdapter.addAll(it, false)
            yourTransactionAdapter.notifyDataSetChanged()
            binding.tvNoYourTransaction.setVisible(it.isEmpty())
        }

        viewModel.atmBalanceLiveData.observe(this) {
            balanceAdapter.clear(true)
            balanceAdapter.addAll(arrayListOf(it), false)
            balanceAdapter.notifyDataSetChanged()
        }

        viewModel.lastTransactionLiveData.observe(this) {
            it?.let {
                lastTransactionAdapter.clear(true)
                lastTransactionAdapter.addAll(arrayListOf(it), false)
                lastTransactionAdapter.notifyDataSetChanged()
                binding.tvNoLastTransaction.setVisible(false)
            } ?: run {
                lastTransactionAdapter.clear(true)
                lastTransactionAdapter.notifyDataSetChanged()
                binding.tvNoLastTransaction.setVisible(true)
            }
        }
    }
}