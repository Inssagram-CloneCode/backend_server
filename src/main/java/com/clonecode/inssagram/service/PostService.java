package com.clonecode.inssagram.service;

import com.clonecode.inssagram.domain.Comment;
import com.clonecode.inssagram.domain.Image;
import com.clonecode.inssagram.domain.Post;
import com.clonecode.inssagram.domain.User;
import com.clonecode.inssagram.dto.request.PostRequestDto;
import com.clonecode.inssagram.dto.response.*;
import com.clonecode.inssagram.exception.EntityNotFoundException;
import com.clonecode.inssagram.exception.InvalidValueException;
import com.clonecode.inssagram.global.error.ErrorCode;
import com.clonecode.inssagram.repository.CommentRepository;
import com.clonecode.inssagram.repository.ImageRepository;
import com.clonecode.inssagram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;
    private final StorageService storageService;

    @Transactional
    public ResponseDto<?> writePost(User user, PostRequestDto requestDto, List<MultipartFile> imageFileList) {
        List<String> imageUrlList = storageService.uploadFile(imageFileList, "/posts");      //이미지 S3에 업로드
        Post post = new Post(user, requestDto);
        postRepository.save(post);      //게시물 db에 저장
        List<Image> collect = imageUrlList.stream().map(image->new Image(image,post)).collect(Collectors.toList());   //String List를 Image List로 변환
        imageRepository.saveAll(collect);
        Long commentNum = commentRepository.countByPost(post);       //댓글 수 게시물id로 세서 찾기 - 게시물을 저장 먼저 해야 id가 생겨서 count 가능
        return ResponseDto.success(PostCreateResponseDto.builder()      //responseDto 돌려주기
                .post(post)
                .imageUrl(collect.get(0).getImageUrl())
                .heartNum(0L)
                .commentNum(commentNum)
                .build());
    }

    //전체 게시글 조회
    @Transactional
    public ResponseDto<?> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();      //생성시간 늦은 순으로 정렬해서 찾기
        List<PostAllResponseDto> postAllResponseDtoList = new ArrayList<>();    //PostAllResponseDto 빈 array 생성
        for (Post post : posts) {       //각 포스트마다 돌면서
            Long commentNum = commentRepository.countByPost(post);       //댓글 수 게시물id로 세서 찾기
            postAllResponseDtoList.add(PostAllResponseDto.builder()//빈 array에 responseDto 만들어서 넣어주기
                    .isHeart(0L)
                    .post(post)
                    .heartNum(0L)
                    .commentNum(commentNum)
                    .build());
        }
        return ResponseDto.success(postAllResponseDtoList);      //array 돌려주기
    }

    //상세 게시글 조회
    @Transactional
    public ResponseDto<?> getOnePost(Long postId) {
        Post post = postRepository.findById(postId)         //게시물 찾기
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));      //없을 시 찾을 수 없음
        Long commentNum = commentRepository.countByPost(post);       //댓글 수 게시물id로 세서 찾기
        List<Comment> commentList = commentRepository.findAllByPostIdOrderByCreatedAtDesc(postId);      //게시물 id별 댓글 리스트 ->좋아요 높은 순으로 수정
        return ResponseDto.success(PostDetailResponseDto.builder()      //responseDto 돌려주기
                .post(post)
                .heartNum(0L)
                .commentNum(commentNum)
                .isHeart(0L)
                .commentList(commentList)
                .build());
    }

    //게시글 수정
    @Transactional
    public ResponseDto<?> updateOnePost(Long postId, User user, PostRequestDto requestDto) {
        Post post = postRepository.findById(postId)      //게시물 찾기
                .orElseThrow(() ->new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));       //없을 시 찾을 수 없음
        if(!user.getId().equals(post.getUser().getId())){        //로그인한 사용자=게시물 작성자인지 확인
            throw new InvalidValueException(ErrorCode.POST_UNAUTHORIZED);    //아닐 시 권한 없음
        }
        post.update(requestDto);      //맞을 시 업데이트
        return ResponseDto.success(new PostUpdateResponseDto(post));
    }

    //게시글 삭제
    public void deleteOnePost(Long postId, User user) {
        Post post = postRepository.findById(postId)     //게시물 찾기
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));      //없을 시 찾을 수 없음
        if(!user.getId().equals(post.getUser().getId())){        //로그인한 사용자=게시물 작성자인지 확인
            throw new InvalidValueException(ErrorCode.POST_UNAUTHORIZED);   //아닐 시 권한 없음
        }
        postRepository.deleteById(postId);      //맞을 시 삭제
    }
}
