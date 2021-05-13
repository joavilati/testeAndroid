package com.jhonata.catapp.di

import com.jhonata.catapp.repository.CatsRepository
import com.jhonata.catapp.repository.DefaultCatsRepository
import dagger.Binds
import dagger.Module

import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCatRepository(
        defaultCatRepository: DefaultCatsRepository
    ): CatsRepository
}