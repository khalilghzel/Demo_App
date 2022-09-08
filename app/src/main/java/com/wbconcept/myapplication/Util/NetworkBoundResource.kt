package com.wbconcept.myapplication.Util

import kotlinx.coroutines.flow.*
inline fun <DB, REMOTE> networkBoundResource(
    crossinline fetchFromLocal: () -> Flow<DB>,
    crossinline shouldFetchFromRemote: (DB?) -> Boolean = { true },
    crossinline fetchFromRemote: () -> Flow<ApiResponse<REMOTE>>,
    crossinline processRemoteResponse: (response: ApiSuccessResponse<REMOTE>) -> Unit = { Unit },
    crossinline saveRemoteData: (REMOTE) -> Unit = { Unit },
    crossinline onFetchFailed: (errorBody: String?, statusCode: Int) -> Unit = { _: String?, _: Int -> Unit }
) = flow<Rezources<DB>> {

    emit(Rezources.loading(null))

    val localData = fetchFromLocal().first()

    if (shouldFetchFromRemote(localData)) {

        emit(Rezources.loading(localData))

        fetchFromRemote().collect { apiResponse ->
            when (apiResponse) {
                is ApiSuccessResponse -> {
                    processRemoteResponse(apiResponse)
                    apiResponse.body?.let { saveRemoteData(it) }
                    emitAll(fetchFromLocal().map { dbData ->
                        Rezources.success(dbData)
                    })
                }

                is ApiErrorResponse -> {
                    onFetchFailed(apiResponse.errorMessage, apiResponse.statusCode)
                    emitAll(fetchFromLocal().map {
                        Rezources.error(
                            apiResponse.errorMessage,
                            it
                        )
                    })
                }
                else -> {
                    emitAll(fetchFromLocal().map { Rezources.success(it) })
                }
            }
        }
    } else {
        emitAll(fetchFromLocal().map { Rezources.success(it) })
    }
}