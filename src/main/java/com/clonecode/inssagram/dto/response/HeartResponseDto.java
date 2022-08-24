package com.clonecode.inssagram.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeartResponseDto {
    private Long heartNum;
    private Long isHeart;
}
