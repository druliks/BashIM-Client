package ru.druliks.bashim.data

object SearchRepositoryProvider {
    fun provideSearchRepository():SearchRepository{
        return SearchRepository(BashImApiService.create())
    }
}