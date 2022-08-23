package com.clonecode.inssagram.domain;

import com.clonecode.inssagram.dto.request.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Entity
@Getter
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postContents;

    private String imgUrl = "none";
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private Long heartNum;

    public Post(User user, PostRequestDto postRequestDto) {
        this.user = user;
        this.postContents = postRequestDto.getPostContents();
    }

    public void count(Long heartNum) {
        this.heartNum = heartNum;
    }

}