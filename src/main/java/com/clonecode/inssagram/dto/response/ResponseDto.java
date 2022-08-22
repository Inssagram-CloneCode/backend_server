package com.clonecode.inssagram.dto.response;

import com.clonecode.inssagram.global.error.ErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
  private boolean success;
  @ApiModelProperty(example = "데이터 교환 성공시 DATA")
  private T data;
  private ErrorCode errorCode;

  public static <T> ResponseDto<T> success(T data) {
    return new ResponseDto<>(true, data, ErrorCode.SUCCESS);
  }

  public static <T> ResponseDto<T> fail(ErrorCode errorCode) {
    return new ResponseDto<>(false, null, errorCode);
  }


}
