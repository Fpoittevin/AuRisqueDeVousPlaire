package com.poittevin.francois.aurisquedevousplaire.ui.message

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.poittevin.francois.aurisquedevousplaire.models.Customer
import com.poittevin.francois.aurisquedevousplaire.repositories.CustomerRepository
import com.poittevin.francois.aurisquedevousplaire.utils.ContactChoice
import com.poittevin.francois.aurisquedevousplaire.utils.MessageUtils

class MessageViewModel(customerRepository: CustomerRepository) : ViewModel() {

    companion object {
        private const val SMS_PRICE = 0.06
    }

    fun initValues() {
        withReductionLiveData.value = false
        fromToNumberOfCards.value?.let {
            minMaxNumberOfCardsLiveData.value = arrayOf(it[0], it[1])
        }
        textMessageLiveData.value = ""
    }

    val textMessageLiveData = MutableLiveData<String>()

    val numberOfCharTotalLiveData = MediatorLiveData<Int>().apply {
        addSource(textMessageLiveData) {
            value = it.length
        }
    }

    val withReductionLiveData = MutableLiveData<Boolean>().apply {
        value = false
    }

    val minMaxNumberOfCardsLiveData = MediatorLiveData<Array<Int?>>().apply {
        value = arrayOf(null, null)

    }

    val fromToNumberOfCards = MediatorLiveData<Array<Int>>().apply {
        value = arrayOf(1, 2)

        addSource(customerRepository.getCustomersList()) { customersList ->
            val fromTo = arrayOf(1, 2)

            for (customer: Customer in customersList) {

                if (customer.cardsNumber < fromTo[0]) {
                    fromTo[0] = customer.cardsNumber
                }
                if (customer.cardsNumber > fromTo[1]) {
                    fromTo[1] = customer.cardsNumber
                }

            }
            value = fromTo
            minMaxNumberOfCardsLiveData.value = arrayOf(value!![0], value!![1])
        }
    }

    val numberOfEmailReceiversLiveData = MediatorLiveData<Int>().apply {
        addSource(
            Transformations.switchMap(withReductionLiveData) { withReduction ->
                minMaxNumberOfCardsLiveData.value?.let { minMaxNumberOfCards ->
                    customerRepository.getNumberOfMessages(
                        withReduction,
                        minMaxNumberOfCards,
                        ContactChoice.EMAIL
                    )
                }
            }
        ) { numberOfEmail ->
            value = numberOfEmail
        }
        addSource(
            Transformations.switchMap(minMaxNumberOfCardsLiveData) { minMaxNumberOfCards ->
                withReductionLiveData.value?.let { withReduction ->
                    customerRepository.getNumberOfMessages(
                        withReduction,
                        minMaxNumberOfCards,
                        ContactChoice.EMAIL
                    )
                }
            }
        ) { numberOfEmail ->
            value = numberOfEmail
        }
    }

    private val numberOfSmsReceiversLiveData = MediatorLiveData<Int>().apply {
        addSource(
            Transformations.switchMap(withReductionLiveData) { withReduction ->
                minMaxNumberOfCardsLiveData.value?.let { minMaxNumberOfCards ->
                    customerRepository.getNumberOfMessages(
                        withReduction,
                        minMaxNumberOfCards,
                        ContactChoice.SMS
                    )
                }
            }
        ) { numberOfSms ->
            value = numberOfSms
        }
        addSource(
            Transformations.switchMap(minMaxNumberOfCardsLiveData) { minMaxNumberOfCards ->
                withReductionLiveData.value?.let { withReduction ->
                    customerRepository.getNumberOfMessages(
                        withReduction,
                        minMaxNumberOfCards,
                        ContactChoice.SMS
                    )
                }
            }
        ) { numberOfSms ->
            value = numberOfSms
        }
    }

    val numberOfReceiversLiveData = MediatorLiveData<Int>().apply {
        addSource(numberOfEmailReceiversLiveData) { nbEmailsReceivers ->
            value = nbEmailsReceivers
            numberOfSmsReceiversLiveData.value?.let { nbSmsReceivers ->
                value = nbEmailsReceivers.plus(nbSmsReceivers)
            }
        }
        addSource(numberOfSmsReceiversLiveData) { nbSmsReceivers ->
            value = nbSmsReceivers
            numberOfEmailReceiversLiveData.value?.let { nbEmailsReceivers ->
                value = nbSmsReceivers.plus(nbEmailsReceivers)
            }
        }
    }

    val numberOfSmsPerPersonLiveData = MediatorLiveData<Int>().apply {
        value = 1
        addSource(numberOfCharTotalLiveData) {
            value = MessageUtils.getNumberOfSmsWithNumberOfChar(it)
        }
    }

    val numberOfSmsLiveData = MediatorLiveData<Int>().apply {
        addSource(numberOfSmsReceiversLiveData) { numberOfSmsReceivers ->
            numberOfSmsPerPersonLiveData.value?.let {
                value = numberOfSmsReceivers.times(it)
            }
        }
        addSource(numberOfSmsPerPersonLiveData) { numberOfSmsPerPerson ->
            numberOfSmsReceiversLiveData.value?.let {
                value = it.times(numberOfSmsPerPerson)
            }
        }
    }

    val priceLiveData = MediatorLiveData<Double>().apply {
        addSource(numberOfSmsLiveData) {
            value = it.times(SMS_PRICE)
        }
    }
}