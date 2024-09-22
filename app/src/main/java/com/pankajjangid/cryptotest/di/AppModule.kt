package com.pankajjangid.cryptotest.di

import com.pankajjangid.cryptotest.local.db.CurrencyDatabase
import com.pankajjangid.cryptotest.local.repository.CurrencyRepository
import com.pankajjangid.cryptotest.viewmodel.CurrencyViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { CurrencyDatabase.provideDatabase(context = androidContext()) }

    factory { get<CurrencyDatabase>().currencyDao() }
    factory { CurrencyRepository(get()) }
    viewModel() { CurrencyViewModel(get()) }
}