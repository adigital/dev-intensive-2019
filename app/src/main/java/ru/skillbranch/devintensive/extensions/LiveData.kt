package ru.skillbranch.devintensive.extensions

import androidx.lifecycle.MediatorLiveData

fun <T> mutableLiveData(defaultValue: T? = null): MediatorLiveData<T> {
    val data = MediatorLiveData<T>()

    if (defaultValue != null) {
        data.value = defaultValue
    }

    return  data
}