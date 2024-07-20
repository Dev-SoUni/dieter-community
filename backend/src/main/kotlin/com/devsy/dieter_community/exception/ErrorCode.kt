package com.devsy.dieter_community.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String,
) {
    USER_EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "해당 이메일은 사용 중입니다."),
    USER_DISABLED(HttpStatus.BAD_REQUEST, "해당 계정은 비활성화 상태입니다."),
    USER_LOCKED(HttpStatus.BAD_REQUEST, "해당 계정은 잠금 상태입니다."),
    USER_BAD_CREDENTIALS(HttpStatus.BAD_REQUEST, "이메일 또는 비밀번호가 일치하지 않습니다."),
    USER_POST_ERROR(HttpStatus.BAD_REQUEST, "회원가입에 실패했습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "해당 토큰은 만료되었습니다."),
    TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "토큰을 처리하는 중 문제가 발생했습니다."),
    TIP_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 꿀팁을 찾을 수 없습니다."),
    TIP_POST_ERROR(HttpStatus.BAD_REQUEST, "꿀팁 등록에 실패했습니다."),
    TIP_PATCH_ERROR(HttpStatus.BAD_REQUEST, "꿀팁 수정에 실패했습니다."),
    TIP_DELETE_ERROR(HttpStatus.BAD_REQUEST, "꿀팁 삭제에 실패했습니다."),
    TIP_LIKE_NOT_FOUND(HttpStatus.BAD_REQUEST, "꿀팁 게시물 좋아요에 대한 정보를 찾을 수 없습니다."),
    TIP_LIKE_POST_ERROR(HttpStatus.BAD_REQUEST, "꿀팁 좋아요 등록에 실패했습니다."),
    TIP_LIKE_DELETE_ERROR(HttpStatus.BAD_REQUEST, "꿀팁 좋아요 삭제에 실패했습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, ""),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생했습니다."),
}