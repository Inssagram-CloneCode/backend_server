package com.clonecode.inssagram.domain;

import com.clonecode.inssagram.dto.request.PostRequestDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();


    public Post(User user, PostRequestDto postRequestDto, List<Image> imageList) {
        this.user = user;
        this.postContents = postRequestDto.getPostContents();
        this.imageList = imageList;
    }

    public void update(PostRequestDto requestDto){
        this.postContents = requestDto.getPostContents();
    }
}