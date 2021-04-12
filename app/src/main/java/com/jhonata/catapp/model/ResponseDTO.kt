package com.jhonata.catapp.model

class ResponseDTO<T>(
    val statusDTO: StatusDTO,
    val data: T? = null,
    val error: Throwable? = null,
    val message: String? = null
) {
    companion object {
        fun <T> success(data: T) = ResponseDTO(StatusDTO.SUCCESS, data)
        fun <T>error(err: Throwable? = null, message: String? = null) =
            ResponseDTO<T>(statusDTO = StatusDTO.ERROR, error= err, message = message)
        fun <T> loading() = ResponseDTO<T>(StatusDTO.LOADING)
    }
}