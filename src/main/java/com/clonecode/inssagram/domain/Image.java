package com.clonecode.inssagram.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "postId")
    private Post post;

    @NotNull
    private String imageUrl;

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}