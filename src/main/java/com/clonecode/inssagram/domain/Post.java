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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt DESC")
    private List<Comment> commentList = new ArrayList<>();

    @Column
    private Long heartNum;

    public Post(User user, PostRequestDto postRequestDto) {
        this.user = user;
        this.postContents = postRequestDto.getPostContents();
    }

    public void update(PostRequestDto requestDto){
        this.postContents = requestDto.getPostContents();
    }

    public void count(Long heartNum) {
        this.heartNum = heartNum;
    }

}