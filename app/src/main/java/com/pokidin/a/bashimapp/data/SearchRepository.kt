package com.pokidin.a.bashimapp.data

class SearchRepository(val apiService: BashImApiService) {

    fun searchQuotes(site: String, name: String): io.reactivex.Observable<List<Quote>> {
        return apiService.searchQuotes(site, name, 50)
    }

    fun searchSourceOfQuotes(): io.reactivex.Observable<List<List<SourceOfQuotes>>> {
        return apiService.searchSources()
    }
}