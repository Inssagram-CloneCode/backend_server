package com.clonecode.inssagram.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class PostRequestDto {
    @ApiModelProperty(example = "게시물 글내용")
    private String postContents;
}
