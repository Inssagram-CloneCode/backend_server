package com.clonecode.inssagram.domain;

import com.clonecode.inssagram.dto.request.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Entity
@Getter
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postContents;

 //양방향으로 묶고 싶지 않은데 transient?
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL)
    private List<Image> imgList = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();


    public Post(User user, PostRequestDto postRequestDto, List<Image> imgUrlList) {
        this.user = user;
        this.postContents = postRequestDto.getPostContents();
        this.imgList = imgUrlList;
    }

    public void update(PostRequestDto requestDto){
        this.postContents = requestDto.getPostContents();
    }
}