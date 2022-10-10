package com.clonecode.inssagram.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeartResponseDto {
    @ApiModelProperty(example = "좋아요 수")
    private Long heartNum;
    @ApiModelProperty(example = "현재 좋아요 상태")
    private Long isHeart;
}
