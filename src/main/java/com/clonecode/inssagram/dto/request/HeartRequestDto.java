package com.clonecode.inssagram.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class HeartRequestDto {
    @ApiModelProperty(example = "현재 좋아요 상태")
    private Long isHeart;
}
