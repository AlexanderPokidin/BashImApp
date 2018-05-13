package com.pokidin.a.bashimapp.data

object SearchRepositoryProvider{

    fun provideSearchRepository(): SearchRepository{
        return SearchRepository(BashImApiService.create())
    }
}