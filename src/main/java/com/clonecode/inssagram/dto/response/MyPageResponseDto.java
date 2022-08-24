package com.clonecode.inssagram.dto.response;

import com.clonecode.inssagram.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MyPageResponseDto {

    @ApiModelProperty(example = "사용자 정보")
    private UserDetailProfileResponseDto user;

    @ApiModelProperty(example = "사용자가 작성한 총 게시글 수")
    private Long postTotalNum;

    @ApiModelProperty(example = "사용자가 받은 총 하트 수")
    private Long heartTotalNum;

    @ApiModelProperty(example = "사용자가 작성한 게시글 목록")
   private List<PostCreateResponseDto> contentList;

    @Builder
    public MyPageResponseDto(User user, Long postTotalNum, Long heartTotalNum,
                             List<PostCreateResponseDto> contentList) {
        this.user = new UserDetailProfileResponseDto(user);
        this.postTotalNum = postTotalNum;
        this.heartTotalNum = heartTotalNum;
        this.contentList = contentList;
    }


}
