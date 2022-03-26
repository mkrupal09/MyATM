package com.example.atmdemo.ui.machine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.atmdemo.MainApplication
import com.example.atmdemo.data.MyRoomDatabase
import com.example.atmdemo.data.Transaction
import com.example.atmdemo.ui.core.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

    private val atmBalance = Transaction(0, 50000, 20, 10, 40, 13)

    private val _atmBalanceLiveData = MutableLiveData<Transaction>()
    val atmBalanceLiveData: LiveData<Transaction>
        get() = _atmBalanceLiveData

    private val _lastTransactionLiveData = MutableLiveData<Transaction>()
    val lastTransactionLiveData: LiveData<Transaction?>
        get() = _lastTransactionLiveData



    fun fetchData() {
        _atmBalanceLiveData.postValue(atmBalance)
        _lastTransactionLiveData.postValue(null)
    }

    fun fetchTransactions(): LiveData<List<Transaction>> {
        return getDatabase().transactionDao().getAll()
    }

    fun withdrawMoney(amount: String) {
        val amountToWithdraw = amount.toIntOrNull() ?: 0
        if (amountToWithdraw <= 0) {
            showMessage("Please enter amount more than 0")
        } else if (amountToWithdraw > 50000) {
            showMessage("Available limit for withdrawal is 50000 maximum in one time")
        } else if (amountToWithdraw > atmBalance.amount) {
            showMessage("This ATM doesn't have enough money. please try again")
        } else if (amountToWithdraw % 100 != 0) {
            showMessage("Please enter amount in multiplication of 100")
        } else {
            var amountLng = amountToWithdraw
            val transaction = Transaction(
                transactionId = 0,
                amount = amountToWithdraw.toLong(),
                hundred = 0,
                fiveHundreds = 0,
                twoHundreds = 0,
                twoThousands = 0
            )

            while (amountLng >= 2000 && atmBalance.twoThousands >= 1) {
                transaction.twoThousands++
                atmBalance.twoThousands--
                amountLng -= 2000
            }

            while (amountLng >= 500 && atmBalance.fiveHundreds >= 1) {
                transaction.fiveHundreds++
                atmBalance.fiveHundreds--
                amountLng -= 500
            }

            while (amountLng >= 200 && atmBalance.twoHundreds >= 1) {
                transaction.twoHundreds++
                atmBalance.twoHundreds--
                amountLng -= 200
            }

            while (amountLng >= 100 && atmBalance.hundred >= 1) {
                transaction.hundred++
                atmBalance.hundred--
                amountLng -= 100
            }
            val validate =
                ((transaction.hundred * 100) + (transaction.twoHundreds * 200) + (transaction.fiveHundreds * 500) + (transaction.twoThousands * 2000)) == amountToWithdraw

            if (validate) {
                atmBalance.amount -= amountToWithdraw
                _atmBalanceLiveData.postValue(atmBalance)
                _lastTransactionLiveData.postValue(transaction)
                insertTransaction(transaction)
                messageLiveData.postValue("Please collect your cash")
            } else {
                val availableNominaters = StringBuilder()
                if (atmBalance.twoThousands > 1)
                    availableNominaters.append("2000")
                if (atmBalance.fiveHundreds > 1)
                    availableNominaters.append("500")
                if (atmBalance.twoHundreds > 1)
                    availableNominaters.append("200")
                if (atmBalance.hundred > 1)
                    availableNominaters.append("100")
                showMessage("Only following notes are available [$availableNominaters]")
            }

        }
    }

    private fun insertTransaction(t: Transaction) {
        viewModelScope.launch {
            getDatabase().transactionDao().insertAll(t)
        }
    }

    private fun getDatabase(): MyRoomDatabase {
        return MainApplication.application.database
    }
}