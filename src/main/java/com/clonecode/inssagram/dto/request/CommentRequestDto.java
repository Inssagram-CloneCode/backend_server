package com.clonecode.inssagram.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    @ApiModelProperty(example = "댓글 내용")
    @NotBlank(message = "댓글을 입력해 주세요.")
    private String commentContents;

}
